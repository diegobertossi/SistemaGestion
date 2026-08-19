package persistencia.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dto.ReparacionDTO;
import persistencia.conexion.Conexion;

/**
 * Valida ReparacionQueryManager (insert/delete/updates/consultas) sin base de
 * datos: la Conexion se mockea y se inyecta por constructor.
 */
public class ReparacionQueryManagerTest {

    private Conexion conexion;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private ReparacionQueryManager manager;

    @Before
    public void setUp() throws SQLException {
        conexion = mock(Conexion.class);
        conn = mock(Connection.class);
        stmt = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        when(conexion.getSQLConexion()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);

        manager = new ReparacionQueryManager(conexion);
    }

    private ReparacionDTO reparacionBasica() {
        ReparacionDTO r = new ReparacionDTO(1234, 0);
        r.setFecha_Entrada("2026-07-01");
        r.setFalla("No enciende");
        r.setEstadoFisico("Regular");
        r.setEstadoTecnico("En Reparación");
        r.setEstadoComercial("Aceptado");
        r.setRemitoCliente("RC-001");
        r.setIDEquipo(77);
        r.setIDTecnico(5);
        r.setAgregadoaremito(true);
        r.setRemitoGenerado(false);
        r.setCodigoRemito(88);
        r.setFecha_Salida("2026-07-20 00:00:00");
        r.setInformecliente("Informe completo");
        r.setPrecioPeso(1234.56);
        r.setPrecioDolar(100.50);
        r.setPresupuestoGenerado(true);
        r.setPresupuestoEnviado(false);
        r.setWordGenerado(true);
        r.setWordEnviado(false);
        r.setFechAceptacion("20260715");
        r.setPago(500.0);
        return r;
    }

    private void reglarMapeoResultSet() throws SQLException {
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
    }

    // ---------------------------------------------------------------- insert

    @Test
    public void insert_vinculaLos10Parametros() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        boolean ok = manager.insert(reparacionBasica());

        assertTrue(ok);
        verify(conn).prepareStatement(eq(SQLQueries.INSERT));
        verify(stmt).setInt(eq(1), eq(1234));
        verify(stmt).setString(eq(2), eq("2026-07-01"));
        verify(stmt).setString(eq(3), eq("No enciende"));
        verify(stmt).setString(eq(4), eq("Regular"));
        verify(stmt).setString(eq(5), eq("En Reparación"));
        verify(stmt).setString(eq(6), eq("Aceptado"));
        verify(stmt).setString(eq(7), eq("RC-001"));
        verify(stmt).setInt(eq(8), eq(77));
        verify(stmt).setInt(eq(9), eq(5));
        verify(stmt).setString(eq(10), isNull());
    }

    @Test
    public void insert_anteExcepcionSQL_devuelveFalse() throws SQLException {
        when(stmt.executeUpdate()).thenThrow(new SQLException("tabla inexistente"));

        assertFalse(manager.insert(reparacionBasica()));
    }

    @Test
    public void delete_vinculaElELSComoString() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        assertTrue(manager.delete(reparacionBasica()));

        verify(conn).prepareStatement(eq(SQLQueries.DELETE));
        verify(stmt).setString(eq(1), eq("1234"));
    }

    @Test
    public void delete_anteExcepcionSQL_devuelveFalse() throws SQLException {
        when(stmt.executeUpdate()).thenThrow(new SQLException("sin conexion"));

        assertFalse(manager.delete(reparacionBasica()));
    }

    // ------------------------------------------------------------- consultas

    @Test
    public void obtenerReparacionXELS_mapeaLaFilaEncontrada() throws SQLException {
        when(rs.next()).thenReturn(true);
        reglarMapeoResultSet();

        ReparacionDTO dto = manager.obtenerReparacionXELS(1234);

        verify(stmt).setInt(eq(1), eq(1234));
        assertEquals(1234, dto.getELS());
        assertEquals("No enciende", dto.getFalla());
        assertEquals("Aceptado", dto.getEstadoComercial());
        assertEquals("Mostrador", dto.getLugarDeIngreso());
    }

    @Test
    public void obtenerReparacionXELS_sinFila_devuelveNull() throws SQLException {
        when(rs.next()).thenReturn(false);

        assertNull(manager.obtenerReparacionXELS(9999));
    }

    @Test
    public void obtenerReparacionXELS_anteExcepcionSQL_devuelveNull() throws SQLException {
        when(rs.next()).thenThrow(new SQLException("base caida"));

        assertNull(manager.obtenerReparacionXELS(1234));
    }

    @Test
    public void obtenerReparacionXSerie_mapeaLaFilaEncontrada() throws SQLException {
        when(rs.next()).thenReturn(true);
        reglarMapeoResultSet();

        ReparacionDTO dto = manager.obtenerReparacionXSerie("SN-123");

        verify(stmt).setString(eq(1), eq("SN-123"));
        assertEquals(1234, dto.getELS());
    }

    @Test
    public void contarReparaciones_devuelveElConteo() throws SQLException {
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(250);

        assertEquals(250, manager.contarReparaciones());
    }

    @Test
    public void contarReparaciones_sinFila_devuelveCero() throws SQLException {
        when(rs.next()).thenReturn(false);

        assertEquals(0, manager.contarReparaciones());
    }

    @Test
    public void obtenerMaximoELS_devuelveElMaximo() throws SQLException {
        when(rs.next()).thenReturn(true);
        when(rs.getInt("MAX(ELS)")).thenReturn(5000);

        assertEquals(5000, manager.obtenerMaximoELS());
    }

    @Test
    public void obtenerMaximoELS_sinReparaciones_devuelve987() throws SQLException {
        when(rs.next()).thenReturn(false);

        assertEquals(987, manager.obtenerMaximoELS());
    }

    @Test
    public void obtenerMaximoELSBSAS_sinReparaciones_devuelve16549() throws SQLException {
        when(rs.next()).thenReturn(false);

        assertEquals(16549, manager.obtenerMaximoELSBSAS());
    }

    @Test
    public void readAllPaginado_vinculaLimiteYOffset() throws SQLException {
        when(rs.next()).thenReturn(false);

        List<ReparacionDTO> lista = manager.readAllPaginado(50, 100);

        assertTrue(lista.isEmpty());
        verify(conn).prepareStatement(eq(SQLQueries.READ_ALL_PAGINADO));
        verify(stmt).setInt(eq(1), eq(50));
        verify(stmt).setInt(eq(2), eq(100));
    }

    // --------------------------------------------------------------- updates

    @Test
    public void update_devuelveTrueCuandoActualiza() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        assertTrue(manager.update(reparacionBasica()));
        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_REPARACION));
    }

    @Test
    public void update_devuelveFalseCuandoNoActualiza() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(0);

        assertFalse(manager.update(reparacionBasica()));
    }

    @Test
    public void updateEquipo_conFechaFabr_usaElQueryConFecha() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        ReparacionDTO r = reparacionBasica();
        r.setFechaFabr("2025-01-01");
        assertTrue(manager.updateEquipo(r));

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_EQUIPO));
    }

    @Test
    public void updateEquipo_sinFechaFabr_usaElQuerySinFecha() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        ReparacionDTO r = reparacionBasica();
        r.setFechaFabr("");
        assertTrue(manager.updateEquipo(r));

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_EQUIPO_SIN_FECHA));
    }

    @Test
    public void updateAgregarRemito_vinculaLos4Parametros() throws SQLException {
        when(stmt.executeUpdate()).thenReturn(1);

        manager.updateAgregarRemito(reparacionBasica());

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_AGREGAR_REMITO));
        verify(stmt).setBoolean(eq(1), eq(true));
        verify(stmt).setBoolean(eq(2), eq(false));
        verify(stmt).setInt(eq(3), eq(88));
        verify(stmt).setInt(eq(4), eq(1234));
    }

    @Test
    public void updateMarcarEnviados_vinculaLos3Parametros() throws SQLException {
        manager.updateMarcarEnviados(reparacionBasica());

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_MARCAR_ENVIADOS));
        verify(stmt).setString(eq(1), eq("Regular"));
        verify(stmt).setString(eq(2), eq("2026-07-20 00:00:00"));
        verify(stmt).setInt(eq(3), eq(1234));
    }

    @Test
    public void updateAnularRemito_vinculaLos5Parametros() throws SQLException {
        manager.updateAnularRemito(reparacionBasica());

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_ANULAR_REMITO));
        verify(stmt).setString(eq(1), eq("Regular"));
        verify(stmt).setBoolean(eq(2), eq(true));
        verify(stmt).setBoolean(eq(3), eq(false));
        verify(stmt).setInt(eq(4), eq(88));
        verify(stmt).setInt(eq(5), eq(1234));
    }

    @Test
    public void updatePresupuesto_vinculaTodosLosParametros() throws SQLException {
        manager.updatePresupuesto(reparacionBasica());

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_PRESUPUESTO));
        verify(stmt).setString(eq(1), eq("Informe completo"));
        verify(stmt).setBigDecimal(eq(2), eq(new BigDecimal(1234.56)));
        verify(stmt).setBigDecimal(eq(3), eq(new BigDecimal(100.50)));
        verify(stmt).setBoolean(eq(4), eq(true));
        verify(stmt).setBoolean(eq(5), eq(false));
        verify(stmt).setBoolean(eq(6), eq(true));
        verify(stmt).setBoolean(eq(7), eq(false));
        verify(stmt).setInt(eq(8), eq(1234));
    }

    @Test
    public void updatePresupuesto_conMontosNull_usaSetNull() throws SQLException {
        ReparacionDTO r = reparacionBasica();
        r.setPrecioPeso(null);
        r.setPrecioDolar(null);

        manager.updatePresupuesto(r);

        verify(stmt).setNull(eq(2), eq(Types.DECIMAL));
        verify(stmt).setNull(eq(3), eq(Types.DECIMAL));
    }

    @Test
    public void updateAceptacion_conFechaConvierteAyyyMMdd() throws SQLException {
        manager.updateAceptacion(reparacionBasica());

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_ACEPTACION));
        verify(stmt).setTimestamp(eq(1), eq(Timestamp.valueOf("2026-07-15 00:00:00")));
        verify(stmt).setString(eq(2), eq("Aceptado"));
        verify(stmt).setInt(eq(3), eq(1234));
    }

    @Test
    public void updateAceptacion_sinFecha_usaSetNull() throws SQLException {
        ReparacionDTO r = reparacionBasica();
        r.setFechAceptacion(null);

        manager.updateAceptacion(r);

        verify(stmt).setNull(eq(1), eq(Types.TIMESTAMP));
    }

    @Test
    public void updatePago_vinculaLos5Parametros() throws SQLException {
        manager.updatePago(reparacionBasica());

        verify(conn).prepareStatement(eq(SQLQueries.UPDATE_PAGO));
        verify(stmt).setBigDecimal(eq(1), eq(new BigDecimal(1234.56)));
        verify(stmt).setBigDecimal(eq(2), eq(new BigDecimal(100.50)));
        verify(stmt).setBigDecimal(eq(3), eq(new BigDecimal(500.0)));
        verify(stmt).setString(eq(4), eq("Aceptado"));
        verify(stmt).setInt(eq(5), eq(1234));
    }

    @Test
    public void updatePago_conPagoNull_usaSetNull() throws SQLException {
        ReparacionDTO r = reparacionBasica();
        r.setPago(null);

        manager.updatePago(r);

        verify(stmt).setNull(eq(3), eq(Types.DECIMAL));
    }

    // --------------------------------------------------- historial de precios

    @Test
    public void buscarHistorialPrecios_vinculaElTextoBuscado() throws SQLException {
        when(rs.next()).thenReturn(false);

        List<ReparacionDTO> lista = manager.buscarHistorialPrecios("MARCA", "LG");

        assertTrue(lista.isEmpty());
        verify(stmt).setString(eq(1), eq("%LG%"));
    }

    @Test
    public void buscarHistorialPrecios_anteExcepcionSQL_devuelveListaVacia() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("sin conexion"));

        List<ReparacionDTO> lista = manager.buscarHistorialPrecios("MODELO", "X");

        assertTrue(lista.isEmpty());
    }
}