package integration;

import java.io.File;
import java.util.List;
import java.util.Random;

import dto.ClienteDTO;
import dto.RegistroEntradaReporteDTO;
import dto.RegistroPresupuestoDTO;
import dto.RemitoDTO;
import dto.ReparacionDTO;
import dto.SucursalDTO;
import modelo.Agenda;
import persistencia.conexion.Conexion;
import presentacion.reportes.ReportePresupuesto;
import presentacion.reportes.ReporteRegistroEntrada;
import presentacion.reportes.ReporteRemitoSalida;
import util.Config;
import util.ReintentoAlta;

/**
 * Worker de simulación de concurrencia: UN proceso = UN "usuario".
 * Ejecuta ciclos de vida completos (alta cliente/sucursal, alta equipo,
 * diagnóstico, repuestos, presupuesto+PDF, aceptación/no-aceptación/garantía,
 * pago, remito+PDF, entrega, lecturas) contra la BD de simulación, con pausas
 * de "tiempo humano" entre pasos.
 *
 * Uso:
 *   java ... integration.SimuladorConcurrencia <workerId> <ciclos> <humano|rapido>
 *
 * Requiere CWD=ReparsoftCliente (reportes/*.jasper, config.properties) y la
 * clave db.database.bariloche apuntando a la BD SIM. Aborta si la BD no
 * contiene "sim" en el nombre. NUNCA muestra ventanas ni envía mails.
 */
public class SimuladorConcurrencia {

    private final int worker;
    private final boolean humano;
    private final Random random;
    private final Agenda agenda;
    private int fallos;

    public SimuladorConcurrencia(int worker, boolean humano) {
        this.worker = worker;
        this.humano = humano;
        this.random = new Random(1000 + worker);
        Conexion.precargarDriver();
        this.agenda = new Agenda("Bariloche");
        // Guardarrail: solo correr si el override apunta a una BD de simulacion.
        // (getNombreBaseActual no refleja el override: se chequea la clave directa.)
        String override = Config.get("db.database.bariloche", "");
        if (override == null || !override.toLowerCase().contains("sim")) {
            throw new IllegalStateException(
                    "GUARDARRAIL: db.database.bariloche debe apuntar a una BD SIM (actual: '" + override
                            + "'). Abortando para no tocar datos reales.");
        }
        log("inicio bdsim=" + override);
    }

    public static void main(String[] args) throws Exception {
        int worker = Integer.parseInt(args[0]);
        int ciclos = Integer.parseInt(args[1]);
        boolean humano = args.length < 3 || !"rapido".equalsIgnoreCase(args[2]);
        SimuladorConcurrencia sim = new SimuladorConcurrencia(worker, humano);
        for (int c = 1; c <= ciclos; c++) {
            try {
                sim.ciclo(c);
            } catch (Exception e) {
                sim.fallos++;
                sim.log("ciclo " + c + " FALLO excepción: " + e);
            }
        }
        sim.log("fin ciclos=" + ciclos + " fallos=" + sim.fallos);
        System.out.println("SIM_DONE worker=" + worker + " fallos=" + sim.fallos);
        System.exit(sim.fallos > 0 ? 1 : 0);
    }

    private void log(String msg) {
        System.out.println("SIM|W" + worker + "|" + msg);
    }

    private void think() {
        try {
            Thread.sleep(humano ? 200 + random.nextInt(1800) : 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private long ahora() {
        return System.currentTimeMillis();
    }

    private void ciclo(int c) {
        String tag = CargaDatosSim.tag(worker, c);

        // ---- 1. Alta cliente + sucursal (replica kernel ControladorCliente) ----
        long t0 = ahora();
        final ClienteDTO[] cliH = new ClienteDTO[1];
        final SucursalDTO[] sucH = new SucursalDTO[1];
        final int[] intCli = new int[1];
        boolean altaOk = ReintentoAlta.reintentar(3, () -> {
            intCli[0]++;
            int idC = agenda.dameIDcliente() + 1;
            int idS = agenda.dameIDsucursal() + 1;
            ClienteDTO cli = CargaDatosSim.cliente(idC, tag);
            SucursalDTO suc = CargaDatosSim.sucursal(idS, idC, tag);
            boolean okC = agenda.agregarClientes(cli);
            boolean okS = okC && agenda.agregarSucursal(suc);
            if (!okS) {
                if (okC) {
                    agenda.borrarCliente(cli);
                }
                return false;
            }
            cliH[0] = cli;
            sucH[0] = suc;
            return true;
        });
        if (!altaOk) {
            throw new IllegalStateException("alta cliente+sucursal agotó reintentos");
        }
        int idCliente = cliH[0].getId();
        int idSucursal = sucH[0].getIdSucursal();
        log("ciclo " + c + " paso alta-cliente ok idC=" + idCliente + " idS=" + idSucursal
                + " intentos=" + intCli[0] + " ms=" + (ahora() - t0));
        think();

        // ---- 2. Alta equipo (replica kernel guardarEquipo) ----
        t0 = ahora();
        final ReparacionDTO[] dtoH = new ReparacionDTO[1];
        final int[] intEq = new int[1];
        boolean equipoOk = ReintentoAlta.reintentar(3, () -> {
            intEq[0]++;
            int els = agenda.dameNumeroELS() + 1;
            int idEquipo = agenda.dameIDequipo() + 1;
            ReparacionDTO dto = CargaDatosSim.altaEquipo(els, idEquipo, idCliente, idSucursal,
                    cliH[0].getRazon_Social(), sucH[0].getNombreSucursal(), tag, 1);
            boolean okEq = agenda.agregarEquipoR(dto);
            boolean okRep = okEq && agenda.agregarSoloReparacion(dto);
            if (!okRep) {
                if (okEq) {
                    agenda.borraEquipo(dto.getIDEquipo());
                }
                return false;
            }
            dtoH[0] = dto;
            return true;
        });
        if (!equipoOk) {
            throw new IllegalStateException("alta equipo agotó reintentos");
        }
        int els = dtoH[0].getELS();
        log("ciclo " + c + " paso alta-equipo ok ELS=" + els + " intentos=" + intEq[0]
                + " ms=" + (ahora() - t0));
        think();

        // ---- 3. Registro de ingreso PDF ----
        t0 = ahora();
        RegistroEntradaReporteDTO reg = CargaDatosSim.registro(els, dtoH[0].getIDEquipo(),
                idCliente, idSucursal, cliH[0].getRazon_Social(), sucH[0].getNombreSucursal(), tag);
        ReporteRegistroEntrada repReg = new ReporteRegistroEntrada(reg,
                java.util.Collections.singletonList(reg), agenda);
        repReg.guardar();
        exigirArchivo(repReg.getPdfGuardado(), "registro");
        log("ciclo " + c + " paso registro-pdf ok ms=" + (ahora() - t0));
        log("FILE registro " + repReg.getPdfGuardado());
        think();

        // ---- 4. Diagnóstico + estados ----
        t0 = ahora();
        ReparacionDTO diag = agenda.dameReparacionXels(els);
        if (diag == null) {
            throw new IllegalStateException("no se lee el ELS recién creado: " + els);
        }
        diag.setSolucion("SIM solucion " + tag);
        diag.setInformecliente("SIM informe tecnico " + tag);
        diag.setPrecioPeso(10000.0);
        diag.setPrecioDolar(10.0);
        diag.setEstadoTecnico("Diagnosticado");
        agenda.editarReparacionR(diag);
        agenda.agregarRepuesto(CargaDatosSim.repuesto(els, tag, 1));
        agenda.agregarRepuesto(CargaDatosSim.repuesto(els, tag, 2));
        log("ciclo " + c + " paso diagnostico ok ms=" + (ahora() - t0));
        think();

        // ---- 5. Presupuesto + PDF ----
        t0 = ahora();
        ReparacionDTO pre = agenda.dameReparacionXels(els);
        pre.setInformecliente("SIM informe " + tag);
        pre.setPrecioPeso(10000.0);
        pre.setPrecioDolar(10.0);
        pre.setPresupuestoGenerado(true);
        pre.setPresupuestoEnviado(true);
        agenda.editarReparacionPresupuesto(pre);
        RegistroPresupuestoDTO dtoPres = CargaDatosSim.presupuesto(els,
                cliH[0].getRazon_Social(), sucH[0].getNombreSucursal(), tag);
        ReportePresupuesto repPres = new ReportePresupuesto(dtoPres,
                CargaDatosSim.listaPresupuesto(dtoPres), agenda);
        if (!repPres.guardar()) {
            throw new IllegalStateException("no se pudo guardar PDF presupuesto ELS=" + els);
        }
        exigirArchivo(repPres.getPdfGuardado(), "presupuesto");
        log("ciclo " + c + " paso presupuesto-pdf ok ms=" + (ahora() - t0));
        log("FILE presupuesto " + repPres.getPdfGuardado());
        think();

        // ---- 6. Aceptación / no-aceptación / garantía + pago ----
        t0 = ahora();
        double r = random.nextDouble();
        String estadoCom;
        double pago;
        if (r < 0.7) {
            estadoCom = CargaDatosSim.ESTADO_COM_ACEPTADO;
            pago = 10000.0;
        } else if (r < 0.9) {
            estadoCom = CargaDatosSim.ESTADO_COM_NO_ACEPTADO;
            pago = 0.0;
        } else {
            estadoCom = CargaDatosSim.ESTADO_COM_GARANTIA;
            pago = 0.0;
        }
        agenda.editarReparacionAceptacion(CargaDatosSim.aceptacion(els, estadoCom));
        agenda.editarReparacionPago(CargaDatosSim.pago(els, 10000.0, 10.0, pago, estadoCom));
        log("ciclo " + c + " paso aceptacion=" + estadoCom.trim() + " ok ms=" + (ahora() - t0));
        think();

        // ---- 7. Remito + PDF (replica kernel generarRemito) ----
        t0 = ahora();
        final int[] idRemH = new int[1];
        final int[] nroRemH = new int[1];
        final int[] intRem = new int[1];
        boolean remOk = ReintentoAlta.reintentar(3, () -> {
            intRem[0]++;
            int numero = agenda.obtenerNumeroRemito(CargaDatosSim.CODIGO_BRC) + 1;
            int idRemito = agenda.dameIDRemito() + 1;
            RemitoDTO cab = CargaDatosSim.remitoTabla(CargaDatosSim.ID_UBICACION_BRC, numero, idRemito);
            if (!agenda.agregarRemito(cab)) {
                return false;
            }
            idRemH[0] = idRemito;
            nroRemH[0] = numero;
            return true;
        });
        if (!remOk) {
            throw new IllegalStateException("cabecera remito agotó reintentos");
        }
        int idRemito = idRemH[0];
        int numero = nroRemH[0];
        RemitoDTO completo = CargaDatosSim.remitoCompleto(CargaDatosSim.ID_UBICACION_BRC,
                CargaDatosSim.CODIGO_BRC, idRemito, numero,
                cliH[0].getRazon_Social(), cliH[0].getCUIT(), cliH[0].getDomicilio());
        ReporteRemitoSalida repRem = new ReporteRemitoSalida(completo,
                CargaDatosSim.listaRemito(completo), agenda);
        if (!repRem.guardar()) {
            throw new IllegalStateException("no se pudo guardar PDF remito id=" + idRemito);
        }
        exigirArchivo(repRem.getPdfGuardado(), "remito");
        agenda.editarReparacionAgregarRemito(CargaDatosSim.lineaRemito(els, idRemito));
        log("ciclo " + c + " paso remito ok id=" + idRemito + " nro=" + numero
                + " intentos=" + intRem[0] + " ms=" + (ahora() - t0));
        log("FILE remito " + repRem.getPdfGuardado());
        think();

        // ---- 8. Entrega ----
        t0 = ahora();
        agenda.editarReparacionMarcarEnviados(CargaDatosSim.entrega(els));
        log("ciclo " + c + " paso entrega ok ms=" + (ahora() - t0));
        think();

        // ---- 9. Lecturas concurrentes (carga de listados/estadísticas) ----
        t0 = ahora();
        int anio = java.time.LocalDate.now().getYear();
        agenda.dameIngresosPorAnio(anio);
        agenda.obtenerReparacion();
        agenda.dameReparacionXels(els);
        log("ciclo " + c + " paso lecturas ok ms=" + (ahora() - t0));

        log("ciclo " + c + " COMPLETO ELS=" + els);
    }

    private void exigirArchivo(String path, String que) {
        if (path == null || path.isEmpty() || !new File(path).exists()) {
            throw new IllegalStateException("falta archivo " + que + ": " + path);
        }
    }
}
