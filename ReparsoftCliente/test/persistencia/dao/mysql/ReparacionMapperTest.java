package persistencia.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import dto.ReparacionDTO;

/**
 * Valida el mapeo ResultSet -> ReparacionDTO sin necesidad de base de datos
 * (ResultSet mockeado con Mockito).
 */
public class ReparacionMapperTest {

    private ResultSet mockResultSet() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("ELS")).thenReturn(1234);
        when(rs.getString("FechaEntrada")).thenReturn("2026-07-01");
        when(rs.getString("FechadeDiagnostico")).thenReturn("2026-07-10");
        when(rs.getString("Falla")).thenReturn("No enciende");
        when(rs.getString("Solucion")).thenReturn("Se reemplazó fuente");
        when(rs.getString("Informecliente")).thenReturn("Informe completo");
        when(rs.getInt("idUsuario")).thenReturn(5);
        when(rs.getString("EstadoFisico")).thenReturn("Regular");
        when(rs.getString("EstadoTecnico")).thenReturn("En Reparación");
        when(rs.getString("EstadoComercial")).thenReturn("Aceptado");
        when(rs.getString("RemitoCliente")).thenReturn("RC-001");
        when(rs.getString("OrdendeCompra")).thenReturn("OC-999");
        when(rs.getBoolean("Agregadoaremito")).thenReturn(true);
        when(rs.getBoolean("RemitoGenerado")).thenReturn(false);
        when(rs.getInt("idEquipo")).thenReturn(77);
        when(rs.getInt("idRemito")).thenReturn(88);
        when(rs.getDouble("PrecioPeso")).thenReturn(1234.56);
        when(rs.getDouble("PrecioDolar")).thenReturn(100.50);
        when(rs.getString("FechAceptacion")).thenReturn("2026-07-15");
        when(rs.getBoolean("PresupuestoGenerado")).thenReturn(true);
        when(rs.getDouble("Pago")).thenReturn(500.0);
        when(rs.getBoolean("PresupuestoEnviado")).thenReturn(true);
        when(rs.getString("Equipos.Nombre")).thenReturn("Monitor LG 24");
        when(rs.getString("email")).thenReturn("cliente@test.com");
        when(rs.getString("Modelo")).thenReturn("24MK600");
        when(rs.getString("Marca")).thenReturn("LG");
        when(rs.getString("NumeroDeSerie")).thenReturn("SN-123");
        when(rs.getString("Aviso")).thenReturn("Aviso de prueba");
        when(rs.getString("ClienteCliente")).thenReturn("Cliente SA");
        when(rs.getInt("idCliente")).thenReturn(42);
        when(rs.getInt("idSucursal")).thenReturn(3);
        when(rs.getString("nombre")).thenReturn("Diego");
        when(rs.getString("NombreSucursal")).thenReturn("Bariloche");
        when(rs.getString("NombreUsuario")).thenReturn("Técnico 1");
        when(rs.getInt("Codigo")).thenReturn(9);
        when(rs.getInt("NumeroRemitoSalida")).thenReturn(456);
        when(rs.getString("FechaFabr")).thenReturn("2025-01-01");
        when(rs.getBoolean("AvisoEnviado")).thenReturn(false);
        when(rs.getBoolean("WordGenerado")).thenReturn(true);
        when(rs.getBoolean("WordEnviado")).thenReturn(false);
        when(rs.getString("lugar_de_ingreso")).thenReturn("Mostrador");
        when(rs.getString("NroFactura")).thenReturn("A-0001-00001234");
        when(rs.getString("FechaSalida")).thenReturn("2026-07-20");
        return rs;
    }

    /** Verifica el mapeo completo de las 44 columnas del SELECT principal. */
    @Test
    public void mapToReparacionDTO_mapeaTodasLasColumnas() throws SQLException {
        ResultSet rs = mockResultSet();
        ReparacionDTO dto = ReparacionMapper.mapToReparacionDTO(rs);

        assertEquals(1234, dto.getELS());
        assertEquals("No enciende", dto.getFalla());
        assertEquals("Se reemplazó fuente", dto.getSolucion());
        assertEquals("Informe completo", dto.getInformecliente());
        assertEquals("En Reparación", dto.getEstadoTecnico());
        assertEquals("Aceptado", dto.getEstadoComercial());
        assertEquals("Monitor LG 24", dto.getNombreEquipo());
        assertEquals("LG", dto.getMarca());
        assertEquals("24MK600", dto.getModelo());
        assertEquals("SN-123", dto.getNumeroDeSerie());
        assertEquals("Diego", dto.getCliente());
        assertEquals("Cliente SA", dto.getClienteCliente());
        assertEquals("Bariloche", dto.getSucursal());
        assertEquals("Técnico 1", dto.getNombreUsuario());
        assertEquals("cliente@test.com", dto.getCorreo());
        assertEquals(1234.56, dto.getPrecioPeso(), 0.001);
        assertEquals(100.50, dto.getPrecioDolar(), 0.001);
        assertEquals("Aviso de prueba", dto.getAviso());
    }

    /** El DTO básico de listado mapea los 10 campos del SELECT básico. */
    @Test
    public void mapToBasicReparacionDTO_mapeaLosCamposDelListado() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("ELS")).thenReturn(7);
        when(rs.getString("Aviso")).thenReturn("Sin aviso");
        when(rs.getString("nombre")).thenReturn("Cliente Listado");
        when(rs.getString("NombreSucursal")).thenReturn("Bs As");
        when(rs.getString("Equipos.Nombre")).thenReturn("Impresora HP");
        when(rs.getString("Marca")).thenReturn("HP");
        when(rs.getString("Modelo")).thenReturn("DeskJet 2700");
        when(rs.getString("NumeroDeSerie")).thenReturn("SN-ABC");
        when(rs.getString("EstadoTecnico")).thenReturn("Sin Falla");
        when(rs.getString("EstadoComercial")).thenReturn("Pendiente");

        ReparacionDTO dto = ReparacionMapper.mapToBasicReparacionDTO(rs);

        assertEquals(7, dto.getELS());
        assertEquals("Sin aviso", dto.getAviso());
        assertEquals("Cliente Listado", dto.getCliente());
        assertEquals("Bs As", dto.getSucursal());
        assertEquals("Impresora HP", dto.getNombreEquipo());
        assertEquals("HP", dto.getMarca());
        assertEquals("DeskJet 2700", dto.getModelo());
        assertEquals("SN-ABC", dto.getNumeroDeSerie());
        assertEquals("Sin Falla", dto.getEstadoTecnico());
        assertEquals("Pendiente", dto.getEstadoComercial());
    }

    /** El mapeo de componentes (búsqueda original/reemplazo) conserva ambos campos. */
    @Test
    public void mapToComponenteReparacionDTO_mapeaOriginalYReemplazo() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("ELS")).thenReturn(55);
        when(rs.getString("FechaEntrada")).thenReturn("2026-01-01");
        when(rs.getString("Cliente.nombre")).thenReturn("Cliente Comp");
        when(rs.getString("NombreSucursal")).thenReturn("Bariloche");
        when(rs.getString("Equipos.Nombre")).thenReturn("Motherboard");
        when(rs.getString("Marca")).thenReturn("Gigabyte");
        when(rs.getString("Modelo")).thenReturn("B450M");
        when(rs.getString("original")).thenReturn("Condensador 470uF");
        when(rs.getString("reemplazo")).thenReturn("Condensador 1000uF");

        ReparacionDTO dto = ReparacionMapper.mapToComponenteReparacionDTO(rs);

        assertEquals(55, dto.getELS());
        assertEquals("Cliente Comp", dto.getCliente());
        assertEquals("Motherboard", dto.getNombreEquipo());
        assertEquals("Condensador 470uF", dto.getComponenteOriginal());
        assertEquals("Condensador 1000uF", dto.getComponenteReemplazo());
    }

    /** El mapeo de combo toma el primer string de la fila. */
    @Test
    public void mapToComboReparacionDTO_tomaLaColumnaUno() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString(1)).thenReturn("Equipo del combo");

        ReparacionDTO dto = ReparacionMapper.mapToComboReparacionDTO(rs);

        assertEquals("Equipo del combo", dto.getNombreEquipo());
    }

    /** La lista completa itera todas las filas del ResultSet. */
    @Test
    public void mapToReparacionList_mapeaTodasLasFilas() throws SQLException {
        ResultSet rs = mockResultSet();
        when(rs.next()).thenReturn(true, true, false);

        List<ReparacionDTO> lista = ReparacionMapper.mapToReparacionList(rs);

        assertEquals(2, lista.size());
        assertEquals(1234, lista.get(0).getELS());
        assertEquals(1234, lista.get(1).getELS());
    }

    /** La lista de enteros se arma a partir de la columna ELS. */
    @Test
    public void mapToIntegerList_mapeaCadaELSFilaPorFila() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("ELS")).thenReturn(10, 20);

        List<Integer> lista = ReparacionMapper.mapToIntegerList(rs);

        assertEquals(2, lista.size());
        assertEquals(Integer.valueOf(10), lista.get(0));
        assertEquals(Integer.valueOf(20), lista.get(1));
    }

    /** La lista de strings toma la columna 1 de cada fila. */
    @Test
    public void mapToStringList_mapeaLaColumnaUno() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString(1)).thenReturn("Marca A", "Marca B");

        List<String> lista = ReparacionMapper.mapToStringList(rs);

        assertEquals(2, lista.size());
        assertEquals("Marca A", lista.get(0));
        assertEquals("Marca B", lista.get(1));
    }

    /** Con una fila devuelve el entero; sin filas devuelve 0 (comportamiento actual). */
    @Test
    public void mapToSingleInteger_conYsinFila() throws SQLException {
        ResultSet conFila = mock(ResultSet.class);
        when(conFila.next()).thenReturn(true);
        when(conFila.getInt(1)).thenReturn(99);
        assertEquals(99, ReparacionMapper.mapToSingleInteger(conFila));

        ResultSet sinFilas = mock(ResultSet.class);
        when(sinFilas.next()).thenReturn(false);
        assertEquals(0, ReparacionMapper.mapToSingleInteger(sinFilas));
    }

    /** Con una fila devuelve el double; sin filas devuelve 0.0. */
    @Test
    public void mapToSingleDouble_conYsinFila() throws SQLException {
        ResultSet conFila = mock(ResultSet.class);
        when(conFila.next()).thenReturn(true);
        when(conFila.getDouble(1)).thenReturn(12345.67);
        assertEquals(12345.67, ReparacionMapper.mapToSingleDouble(conFila), 0.001);

        ResultSet sinFilas = mock(ResultSet.class);
        when(sinFilas.next()).thenReturn(false);
        assertEquals(0.0, ReparacionMapper.mapToSingleDouble(sinFilas), 0.001);
    }

    /**
     * La lista mensual de enteros: el mes viene 1-based desde SQL y debe quedar
     * 0-based; los meses fuera de rango se ignoran; siempre devuelve 12 posiciones.
     */
    @Test
    public void mapToMonthlyIntegerList_convierteMesYCompletaLos12() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, true, false);
        when(rs.getInt(1)).thenReturn(1, 12, 13);
        when(rs.getInt(2)).thenReturn(5, 8, 999);

        List<Integer> lista = ReparacionMapper.mapToMonthlyIntegerList(rs);

        assertEquals(12, lista.size());
        assertEquals(Integer.valueOf(5), lista.get(0));
        assertEquals(Integer.valueOf(8), lista.get(11));
        for (int i = 1; i < 11; i++) {
            assertEquals(Integer.valueOf(0), lista.get(i));
        }
    }

    /** ResultSet vacío en la lista mensual produce 12 ceros (sin NPE). */
    @Test
    public void mapToMonthlyIntegerList_vacioDevuelve12Ceros() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);

        List<Integer> lista = ReparacionMapper.mapToMonthlyIntegerList(rs);

        assertEquals(12, lista.size());
        for (Integer valor : lista) {
            assertEquals(Integer.valueOf(0), valor);
        }
    }

    /** Misma lógica mensual aplicada a montos double; mes 0 queda fuera de rango y se ignora. */
    @Test
    public void mapToMonthlyDoubleList_convierteMesYCompletaLos12() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt(1)).thenReturn(6, 0);
        when(rs.getDouble(2)).thenReturn(1500.25, 700.5);

        List<Double> lista = ReparacionMapper.mapToMonthlyDoubleList(rs);

        assertEquals(12, lista.size());
        assertEquals(1500.25, lista.get(5), 0.001);
        assertEquals(0.0, lista.get(0), 0.001);
    }

    /** Solo los campos de la whitelist son válidos para búsqueda (anti SQL injection). */
    @Test
    public void esCampoValido_aceptaSoloLaWhitelist() {
        assertTrue(ReparacionMapper.esCampoValido("Falla"));
        assertTrue(ReparacionMapper.esCampoValido("Solucion"));
        assertTrue(ReparacionMapper.esCampoValido("Informecliente"));
        assertFalse(ReparacionMapper.esCampoValido("idUsuario"));
        assertFalse(ReparacionMapper.esCampoValido("ELS"));
        assertFalse(ReparacionMapper.esCampoValido("Falla; DROP TABLE reparaciones"));
        assertFalse(ReparacionMapper.esCampoValido(""));
        assertFalse(ReparacionMapper.esCampoValido(null));
    }

    /** La whitelist de historial de precios contiene exactamente los 3 campos de equipos. */
    @Test
    public void camposPermitidosHistorial_contieneLosCamposDeEquipo() {
        assertEquals(3, ReparacionMapper.CAMPOS_PERMITIDOS_HISTORIAL.size());
        assertTrue(ReparacionMapper.CAMPOS_PERMITIDOS_HISTORIAL.contains("Equipos.Nombre"));
        assertTrue(ReparacionMapper.CAMPOS_PERMITIDOS_HISTORIAL.contains("Equipos.Marca"));
        assertTrue(ReparacionMapper.CAMPOS_PERMITIDOS_HISTORIAL.contains("Equipos.Modelo"));
    }

    /** El mapeo de historial de precios conserva el último precio de cada consulta. */
    @Test
    public void mapToHistorialPreciosList_mapeaPreciosYEquipo() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("ELS")).thenReturn(100, 101);
        when(rs.getString("Nombre")).thenReturn("Notebook Dell");
        when(rs.getString("Marca")).thenReturn("Dell");
        when(rs.getString("Modelo")).thenReturn("Inspiron 15");
        when(rs.getString("FechaDiag")).thenReturn("2026-05-01");
        when(rs.getDouble("PrecioPeso")).thenReturn(500000.0);
        when(rs.getDouble("PrecioDolar")).thenReturn(600.0);

        List<ReparacionDTO> lista = ReparacionMapper.mapToHistorialPreciosList(rs);

        assertEquals(2, lista.size());
        ReparacionDTO dto = lista.get(0);
        assertEquals(100, dto.getELS());
        assertEquals("Notebook Dell", dto.getNombreEquipo());
        assertEquals("Dell", dto.getMarca());
        assertEquals("Inspiron 15", dto.getModelo());
        assertEquals(500000.0, dto.getPrecioPeso(), 0.001);
        assertEquals(600.0, dto.getPrecioDolar(), 0.001);
        assertNull(dto.getAviso());
    }
}
