package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import dto.RemitoDTO;
import dto.ReparacionDTO;
import presentacion.controlador.gestores.GestorEstadosPresupuestos;

/**
 * Pruebas del flujo completo de vida de una reparación en el centro de servicio:
 * 1. Ingreso del equipo y datos iniciales.
 * 2. Diagnóstico técnico e informe ("AVISO DE INFORME").
 * 3. Generación y envío del presupuesto (WhatsApp / Email).
 * 4. Aceptación del cliente y notificación interna ("RESPUESTA AL TÉCNICO").
 * 5. Finalización de la reparación ("EQUIPO TERMINADO").
 * 6. Confección del remito de salida.
 * 7. Entrega final y cambio de Estado Físico a "ENVIADO".
 */
public class FlujoCompletoReparacionTest {

    private GestorEstadosPresupuestos gestorEstados;
    private DateTimeFormatter fmtDate;

    @Before
    public void setUp() {
        gestorEstados = new GestorEstadosPresupuestos();
        fmtDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testFlujoCompletoCicloVidaReparacion() {
        String hoy = LocalDate.now().format(fmtDate);

        // =========================================================================
        // PASO 1: Ingreso del equipo al centro de reparaciones
        // =========================================================================
        ReparacionDTO reparacion = new ReparacionDTO(
                15001,                 // ELS
                hoy,                   // FechaEntrada
                null,                  // FechadeDiagnostico
                "No enciende, luz parpadea", // Falla
                null,                  // Solucion
                null,                  // Informecliente
                10,                    // IDTecnico (Juan Perez)
                "EN REPARACIÓN",       // EstadoFisico inicial
                "EN ESPERA",           // EstadoTecnico inicial
                "PENDIENTE PRESUPUESTO", // EstadoComercial inicial
                "REM-IN-445",          // RemitoCliente
                "OC-2026-99",          // OrdendeCompra
                false,                 // Agregadoaremito
                false,                 // RemitoGenerado
                501,                   // IDEquipo
                0,                     // CodigoRemito
                0.0,                   // PrecioPeso
                0.0,                   // PrecioDolar
                null,                  // FechAceptacion
                false,                 // PresupuestoGenerado
                0.0,                   // Pago
                false,                 // PresupuestoEnviado
                "Osciloscopio Digital",// Nombre equipo
                "contacto@telecom.com",// Correo
                "TBS-1052B",           // Modelo
                "Tektronix",           // Marca
                "SN-TK-98765",         // NumeroDeSerie
                "Sin aviso",           // Aviso
                "Telecom Argentina",   // ClienteCliente
                102,                   // IDCliente
                1,                     // IDSuc
                "Telecom Argentina S.A.", // Cliente
                "Bariloche",           // Sucursal
                "Juan Perez",          // NombreUsuario (Técnico asignado)
                15001,                 // Codigo
                0,                     // NumeroRemitoSalida
                "2023",                // FechaFabr
                false,                 // AvisoEnviado
                false,                 // WordGenerado
                false,                 // WordEnviado
                "Recepcion Central",   // LugarDeIngreso
                null,                  // NroFactura
                null                   // fecha_Salida
        );

        // Verificaciones del estado inicial
        assertEquals(15001, reparacion.getELS());
        assertEquals("EN REPARACIÓN", reparacion.getEstadoFisico());
        assertEquals("EN ESPERA", reparacion.getEstadoTecnico());
        assertEquals("PENDIENTE PRESUPUESTO", reparacion.getEstadoComercial());
        assertFalse("El presupuesto no debe estar generado al ingresar", reparacion.getPresupuestoGenerado());
        assertFalse("El remito no debe estar generado al ingresar", reparacion.getRemitoGenerado());
        assertFalse("El aviso no debe haberse enviado", reparacion.getAvisoEnviado());

        // =========================================================================
        // PASO 2: Diagnóstico técnico y notificación "AVISO DE INFORME"
        // =========================================================================
        reparacion.setFechadereparacion(hoy);
        reparacion.setFalla("Fuente conmutada primaria en cortocircuito (IC U101 dañado)");
        reparacion.setSolucion("Reemplazo de controlador PWM y MOSFET de potencia en fuente aux.");
        reparacion.setInformecliente("Equipo diagnosticado. Se requiere reemplazo de etapa primaria de alimentación.");
        reparacion.setPrecioPeso(185000.0);
        reparacion.setPrecioDolar(185.0);
        reparacion.setEstadoTecnico("DIAGNOSTICADO");

        // El técnico avisa enviando correo con el botón "AVISO DE INFORME"
        reparacion.setAvisoEnviado(true);

        assertEquals("DIAGNOSTICADO", reparacion.getEstadoTecnico());
        assertTrue("El informe al cliente debe ser registrado", reparacion.getInformecliente().contains("diagnosticado"));
        assertTrue("Debe marcarse como Aviso Enviado tras presionar 'AVISO DE INFORME'", reparacion.getAvisoEnviado());
        assertEquals(185000.0, reparacion.getPrecioPeso(), 0.001);

        // =========================================================================
        // PASO 3: Generación del presupuesto y envío al cliente
        // =========================================================================
        reparacion.setPresupuestoGenerado(true);
        reparacion.setPresupuestoEnviado(true); // Enviado por WhatsApp o Email

        assertTrue("El presupuesto debe marcarse como generado", reparacion.getPresupuestoGenerado());
        assertTrue("El presupuesto debe marcarse como enviado", reparacion.getPresupuestoEnviado());
        
        // Regla de negocio: Estado presupuestario con el gestor
        String estPresupuestoIni = gestorEstados.verificarEstadoPresupuesto(reparacion.getEstadoComercial());
        assertEquals("Presupuesto Pendiente", estPresupuestoIni);

        // =========================================================================
        // PASO 4: Aceptación del cliente (externa) y botón "RESPUESTA AL TÉCNICO"
        // =========================================================================
        // Se anota manualmente el estado comercial correspondiente al recibir la respuesta
        reparacion.setEstadoComercial("APROBADO");
        reparacion.setFechAceptacion(hoy);

        String estPresupuestoAprob = gestorEstados.verificarEstadoPresupuesto(reparacion.getEstadoComercial());
        assertEquals("Presupuesto Aprobado", estPresupuestoAprob);

        // Se verifica que haya un técnico asignado para enviarle la notificación "RESPUESTA AL TÉCNICO"
        assertNotNull("Debe haber un técnico asignado para la notificación", reparacion.getNombreUsuario());
        assertEquals("Juan Perez", reparacion.getNombreUsuario());

        // =========================================================================
        // PASO 5: Reparación efectuada y aviso "EQUIPO TERMINADO"
        // =========================================================================
        reparacion.setEstadoTecnico("EQUIPO LISTO");
        assertEquals("EQUIPO LISTO", reparacion.getEstadoTecnico());

        // Regla de negocio: verificar si se puede facturar
        boolean facturable = gestorEstados.puedeFacturarse("Pendiente Entrega", estPresupuestoAprob);
        assertTrue("Un equipo reparado con presupuesto aprobado debe ser facturable", facturable);

        // =========================================================================
        // PASO 6: Generación del Remito de Salida
        // =========================================================================
        RemitoDTO remitoSalida = new RemitoDTO(1, 7001, 7001);

        reparacion.setCodigoRemito(remitoSalida.getIdRemito());
        reparacion.setNumeroRemitoSalida(remitoSalida.getNumeroRemitoSalida());
        reparacion.setRemitoGenerado(true);
        reparacion.setAgregadoaremito(true);

        assertTrue("El remito debe estar generado", reparacion.getRemitoGenerado());
        assertTrue("La reparación debe estar agregada al remito", reparacion.getAgregadoaremito());
        assertEquals(7001, reparacion.getNumeroRemitoSalida());

        // =========================================================================
        // PASO 7: Entrega del equipo y cambio de Estado Físico a "ENVIADO"
        // =========================================================================
        reparacion.setEstadoFisico("ENVIADO");
        reparacion.setFecha_Salida(hoy);

        // Verificación final del ciclo completo
        assertEquals("ENVIADO", reparacion.getEstadoFisico());
        assertEquals("EQUIPO LISTO", reparacion.getEstadoTecnico());
        assertEquals("APROBADO", reparacion.getEstadoComercial());
        assertNotNull("Debe registrarse la fecha de salida", reparacion.getFecha_Salida());
        assertTrue("Aviso enviado", reparacion.getAvisoEnviado());
        assertTrue("Presupuesto enviado", reparacion.getPresupuestoEnviado());
        assertTrue("Remito generado", reparacion.getRemitoGenerado());
    }

    @Test
    public void testFlujoCompletoConRechazoDePresupuesto() {
        String hoy = LocalDate.now().format(fmtDate);

        ReparacionDTO reparacion = new ReparacionDTO(
                15002, hoy, null, "Sin encendido", null, null,
                5, "EN REPARACIÓN", "EN ESPERA", "PENDIENTE PRESUPUESTO",
                null, null, false, false, 502, 0, 0.0, 0.0, null,
                false, 0.0, false, "Fuente Industrial", "cliente@empresa.com",
                "PSU-300", "Siemens", "SN-SI-111", "Sin aviso", "Empresa S.A.",
                103, 1, "Empresa S.A.", "Buenos Aires", "Carlos Lopez", 15002, 0,
                "2022", false, false, false, "Recepcion", null, null
        );

        // Diagnóstico
        reparacion.setFechadereparacion(hoy);
        reparacion.setPrecioPeso(500000.0);
        reparacion.setEstadoTecnico("DIAGNOSTICADO");
        reparacion.setAvisoEnviado(true);

        // Presupuesto
        reparacion.setPresupuestoGenerado(true);
        reparacion.setPresupuestoEnviado(true);

        // Cliente rechaza el presupuesto
        reparacion.setEstadoComercial("RECHAZADO");
        reparacion.setFechAceptacion(hoy);

        String estPresupuesto = gestorEstados.verificarEstadoPresupuesto(reparacion.getEstadoComercial());
        assertEquals("Presupuesto Rechazado", estPresupuesto);

        // Devolución sin reparar con remito
        reparacion.setEstadoTecnico("DEVUELTO SIN REPARAR");
        reparacion.setRemitoGenerado(true);
        reparacion.setEstadoFisico("ENVIADO");
        reparacion.setFecha_Salida(hoy);

        assertEquals("ENVIADO", reparacion.getEstadoFisico());
        assertEquals("Presupuesto Rechazado", estPresupuesto);
        assertEquals("DEVUELTO SIN REPARAR", reparacion.getEstadoTecnico());
    }
}
