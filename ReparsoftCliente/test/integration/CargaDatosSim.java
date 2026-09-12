package integration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.ClienteDTO;
import dto.RegistroEntradaReporteDTO;
import dto.RegistroPresupuestoDTO;
import dto.RemitoDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.SucursalDTO;

/**
 * Factoría de datos marcados para la simulación de concurrencia.
 * Todo lo generado lleva la marca SIM-W{worker}C{ciclo} para identificarlo,
 * verificarlo y limpiarlo después. No toca la BD: solo construye DTOs.
 */
public final class CargaDatosSim {

    public static final String ESTADO_TEC_SIN_REVISAR = "Sin Revisar";
    public static final String ESTADO_COM_ESPERA = "A la Espera de Aceptación";
    public static final String ESTADO_COM_ACEPTADO = "Aceptado";
    public static final String ESTADO_COM_NO_ACEPTADO = " NO Aceptado";
    public static final String ESTADO_COM_GARANTIA = "Garantía";
    public static final String ESTADO_FISICO_ENVIADO = "Enviado";
    public static final String LUGAR_INGRESO = "BRC";

    /** IdUbicacion/Codigo de BRC según seed de UbicacionRemitos. */
    public static final int ID_UBICACION_BRC = 7;
    public static final int CODIGO_BRC = 6;

    private CargaDatosSim() {
    }

    public static String hoy() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String tag(int worker, int ciclo) {
        return "SIM-W" + worker + "C" + ciclo;
    }

    public static ClienteDTO cliente(int idCliente, String tag) {
        return new ClienteDTO(idCliente, "Cliente " + tag, "SIM-CUIT-" + tag,
                "Domicilio " + tag, "1000000", "Contacto " + tag, "2000000",
                "sim" + tag.toLowerCase() + "@test.com", "CUIT", "Responsable Inscripto", "empresa");
    }

    public static SucursalDTO sucursal(int idSucursal, int idCliente, String tag) {
        return new SucursalDTO(idSucursal, "Suc " + tag, idCliente,
                "Dom Suc " + tag, "Cont Suc " + tag, "3000000", "suc" + tag.toLowerCase() + "@test.com");
    }

    public static ReparacionDTO altaEquipo(int els, int idEquipo, int idCliente, int idSucursal,
            String cliente, String sucursal, String tag, int idUsuario) {
        return new ReparacionDTO(els, hoy(), "SIM falla " + tag, LUGAR_INGRESO,
                ESTADO_TEC_SIN_REVISAR, ESTADO_COM_ESPERA, "SIM-REM-" + tag, idEquipo,
                idUsuario, "EquipoSIM", "ModSIM", "MarcaSIM", "SIMSN-" + tag,
                "SIM", "CliCli " + tag, idCliente, idSucursal, hoy(), LUGAR_INGRESO);
    }

    public static RegistroEntradaReporteDTO registro(int els, int idEquipo, int idCliente, int idSucursal,
            String cliente, String sucursal, String tag) {
        return new RegistroEntradaReporteDTO(els, hoy(), "SIM falla " + tag, LUGAR_INGRESO,
                ESTADO_TEC_SIN_REVISAR, "SIM-REM-" + tag, idEquipo, "EquipoSIM", "ModSIM",
                "MarcaSIM", "SIMSN-" + tag, "SIM", "CliCli " + tag, idCliente, idSucursal,
                cliente, sucursal);
    }

    public static RegistroPresupuestoDTO presupuesto(int els, String cliente, String sucursal, String tag) {
        return new RegistroPresupuestoDTO(els, "SIM informe " + tag,
                "SIM-REM-" + tag, 10000.0, 10.0, "EquipoSIM", "ModSIM", "MarcaSIM",
                "SIMSN-" + tag, "SIM", "CliCli " + tag, cliente, sucursal);
    }

    public static List<RegistroPresupuestoDTO> listaPresupuesto(RegistroPresupuestoDTO dto) {
        List<RegistroPresupuestoDTO> lista = new ArrayList<>();
        lista.add(dto);
        return lista;
    }

    public static RepuestosDTO repuesto(int els, String tag, int n) {
        return new RepuestosDTO(els, "SIM-REF-" + tag + "-" + n,
                "SIM-ORIG-" + n, "SIM-REEMP-" + n, "SIM notas");
    }

    public static ReparacionDTO aceptacion(int els, String estadoComercial) {
        return new ReparacionDTO(els, hoy(), estadoComercial);
    }

    public static ReparacionDTO pago(int els, double precioPeso, double precioDolar, double pago,
            String estadoComercial) {
        return new ReparacionDTO(els, precioPeso, precioDolar, pago, estadoComercial);
    }

    public static ReparacionDTO lineaRemito(int els, int idRemito) {
        return new ReparacionDTO(els, true, true, idRemito);
    }

    public static ReparacionDTO entrega(int els) {
        return new ReparacionDTO(els, ESTADO_FISICO_ENVIADO, true, hoy());
    }

    public static String numeroConformado(int codigo, int numero) {
        String n = String.valueOf(numero);
        String ceros;
        if (numero < 10) {
            ceros = "0000000";
        } else if (numero < 100) {
            ceros = "000000";
        } else if (numero < 1000) {
            ceros = "00000";
        } else if (numero < 10000) {
            ceros = "0000";
        } else {
            ceros = "";
        }
        return codigo + " - " + ceros + n;
    }

    public static RemitoDTO remitoTabla(int idUbicacion, int numero, int idRemito) {
        return new RemitoDTO(idUbicacion, numero, idRemito);
    }

    public static RemitoDTO remitoCompleto(int idUbicacion, int codigo, int idRemito, int numero,
            String cliente, String cuit, String domicilio) {
        List<String> descripcion = new ArrayList<>();
        descripcion.add("SIM remito " + idRemito + "\n\n");
        return new RemitoDTO(idUbicacion, codigo, idRemito, numero, descripcion,
                cliente, numeroConformado(codigo, numero), 1, cuit, domicilio);
    }

    public static List<RemitoDTO> listaRemito(RemitoDTO dto) {
        List<RemitoDTO> lista = new ArrayList<>();
        lista.add(dto);
        return lista;
    }
}
