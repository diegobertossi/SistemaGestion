package dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Valida los constructores de ReparacionDTO usados por los mapeos
 * de ReparacionMapper y por las ventanas de listado.
 */
public class ReparacionDTOTest {

    /** Constructor de un argumento (combos). */
    @Test
    public void constructorUnArgumento_guardaNombreEquipo() {
        ReparacionDTO dto = new ReparacionDTO("Monitor Samsung");
        assertEquals("Monitor Samsung", dto.getNombreEquipo());
    }

    /** Constructor básico de listado (10 argumentos). */
    @Test
    public void constructorBasico_guardaCamposDelListado() {
        ReparacionDTO dto = new ReparacionDTO(100, "Sin aviso", "Cliente A", "Bariloche",
                "Impresora", "HP", "LaserJet", "SN-1", "Sin Falla", "Aceptado");
        assertEquals(100, dto.getELS());
        assertEquals("Sin aviso", dto.getAviso());
        assertEquals("Cliente A", dto.getCliente());
        assertEquals("Bariloche", dto.getSucursal());
        assertEquals("Impresora", dto.getNombreEquipo());
        assertEquals("HP", dto.getMarca());
        assertEquals("LaserJet", dto.getModelo());
        assertEquals("SN-1", dto.getNumeroDeSerie());
        assertEquals("Sin Falla", dto.getEstadoTecnico());
        assertEquals("Aceptado", dto.getEstadoComercial());
    }

    /** Constructor de componentes (original/reemplazo). */
    @Test
    public void constructorComponentes_guardaOriginalYReemplazo() {
        ReparacionDTO dto = new ReparacionDTO(200, "2026-01-01", "Cliente B", "Bs As",
                "Motherboard", "Gigabyte", "B450", "Condensador 470uF", "Condensador 1000uF");
        assertEquals(200, dto.getELS());
        assertEquals("Condensador 470uF", dto.getComponenteOriginal());
        assertEquals("Condensador 1000uF", dto.getComponenteReemplazo());
        assertEquals("Cliente B", dto.getCliente());
    }

    /** Constructor de historial de precios (7 argumentos). */
    @Test
    public void constructorHistorial_guardaPrecios() {
        ReparacionDTO dto = new ReparacionDTO(300, "Notebook Dell", "Dell", "Inspiron 15",
                "2026-05-01", 500000.0, 600.0);
        assertEquals(300, dto.getELS());
        assertEquals("Notebook Dell", dto.getNombreEquipo());
        assertEquals("Dell", dto.getMarca());
        assertEquals("Inspiron 15", dto.getModelo());
        assertEquals(500000.0, dto.getPrecioPeso(), 0.001);
        assertEquals(600.0, dto.getPrecioDolar(), 0.001);
    }

    /** Constructor de pago (5 argumentos). */
    @Test
    public void constructorPago_guardaMontosYEstado() {
        ReparacionDTO dto = new ReparacionDTO(400, 10000.0, 200.0, 5000.0, "Parcial");
        assertEquals(400, dto.getELS());
        assertEquals(10000.0, dto.getPrecioPeso(), 0.001);
        assertEquals(200.0, dto.getPrecioDolar(), 0.001);
        assertEquals(5000.0, dto.getPago(), 0.001);
        assertEquals("Parcial", dto.getEstadoComercial());
    }

    /** Constructor de aceptación (3 argumentos). */
    @Test
    public void constructorAceptacion_guardaEstadoYFecha() {
        ReparacionDTO dto = new ReparacionDTO(500, "2026-07-15", "Aceptado");
        assertEquals(500, dto.getELS());
        assertEquals("Aceptado", dto.getEstadoComercial());
        assertNull(dto.getInformecliente());
    }

    /**
     * Constructor de informe (8 argumentos). Los flags de Word se guardan
     * (sin getters públicos, ver ANALISIS_PROYECTO.md riesgo M3) y se
     * verifican solo los campos con getter.
     */
    @Test
    public void constructorInforme_guardaCampos() {
        ReparacionDTO dto = new ReparacionDTO(600, "Informe del cliente", 1234.0, 50.0,
                true, false, true, false);
        assertEquals(600, dto.getELS());
        assertEquals("Informe del cliente", dto.getInformecliente());
        assertEquals(1234.0, dto.getPrecioPeso(), 0.001);
        assertEquals(50.0, dto.getPrecioDolar(), 0.001);
        assertEquals(Boolean.TRUE, dto.getPresupuestoGenerado());
        assertEquals(Boolean.FALSE, dto.getPresupuestoEnviado());
    }
}
