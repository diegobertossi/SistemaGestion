package persistencia.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import dto.ReparacionDTO;
import persistencia.conexion.Conexion;

/**
 * Valida la cadena ReparacionDAOImpl -> ReparacionQueryManager ->
 * ReparacionMapper con el singleton Conexion y JDBC mockeados.
 * Sin base de datos real.
 */
public class ReparacionDAOImplTest {

    private Conexion mockConexion;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private MockedStatic<Conexion> mockedStatic;

    @Before
    public void setUp() throws SQLException {
        mockConexion = mock(Conexion.class);
        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockRs = mock(ResultSet.class);

        when(mockConexion.getSQLConexion()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockStmt.executeUpdate()).thenReturn(1);

        mockedStatic = mockStatic(Conexion.class);
        mockedStatic.when(() -> Conexion.getConexion("Bariloche")).thenReturn(mockConexion);
        mockedStatic.when(() -> Conexion.getConexion("Buenos Aires")).thenReturn(mockConexion);
    }

    @After
    public void tearDown() {
        mockedStatic.close();
    }

    /** obtenerReparacionXELS recorre fachada -> manager -> mapper y mapea el DTO. */
    @Test
    public void obtenerReparacionXELS_cadenaCompleta() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("ELS")).thenReturn(987);
        when(mockRs.getString("Falla")).thenReturn("Pantalla rota");
        when(mockRs.getString("Solucion")).thenReturn("Reemplazo de panel");
        when(mockRs.getString("Equipos.Nombre")).thenReturn("TV Samsung 50");
        when(mockRs.getString("Marca")).thenReturn("Samsung");
        when(mockRs.getString("Modelo")).thenReturn("UE50");
        when(mockRs.getString("EstadoTecnico")).thenReturn("En Reparación");
        when(mockRs.getString("EstadoComercial")).thenReturn("Pendiente");
        when(mockRs.getDouble("PrecioPeso")).thenReturn(250000.0);
        when(mockRs.getDouble("PrecioDolar")).thenReturn(300.0);

        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        ReparacionDTO dto = dao.obtenerReparacionXels(987);

        assertEquals(987, dto.getELS());
        assertEquals("Pantalla rota", dto.getFalla());
        assertEquals("Reemplazo de panel", dto.getSolucion());
        assertEquals("TV Samsung 50", dto.getNombreEquipo());
        assertEquals("Samsung", dto.getMarca());
        assertEquals("UE50", dto.getModelo());
        assertEquals(250000.0, dto.getPrecioPeso(), 0.001);
        assertEquals(300.0, dto.getPrecioDolar(), 0.001);
        verify(mockStmt).setInt(1, 987);
    }

    /** Sin filas en el ResultSet devuelve null (ELS inexistente). */
    @Test
    public void obtenerReparacionXELS_sinResultadosDevuelveNull() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertNull(dao.obtenerReparacionXels(999999));
    }

    /** contarReparaciones devuelve el count(*) de la primera columna. */
    @Test
    public void contarReparaciones_devuelveElConteo() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(1234);
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertEquals(1234, dao.contarReparaciones());
    }

    /** Sin filas el conteo es 0. */
    @Test
    public void contarReparaciones_sinFilasDevuelveCero() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertEquals(0, dao.contarReparaciones());
    }

    /** obtenerNumeroELSels: con MAX(ELS)=0 devuelve el default 987 de Bariloche. */
    @Test
    public void obtenerNumeroELSels_conMaximoCeroDevuelveDefault() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("MAX(ELS)")).thenReturn(0);
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertEquals(987, dao.obtenerNumeroELSels());
    }

    /** obtenerNumeroELSels con máximo real lo devuelve tal cual. */
    @Test
    public void obtenerNumeroELSels_devuelveMaximoReal() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("MAX(ELS)")).thenReturn(1250);
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertEquals(1250, dao.obtenerNumeroELSels());
    }

    /** obtenerNumeroELSbsas usa su propio default 16549. */
    @Test
    public void obtenerNumeroELSbsas_sinDatosDevuelveDefaultBsAs() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Buenos Aires");
        assertEquals(16549, dao.obtenerNumeroELSbsas());
    }

    /** Ante excepción SQL, obtenerReparacionXels devuelve null (sin crash). */
    @Test
    public void obtenerReparacionXELS_conExcepcionSQLDevuelveNull() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("BD caída"));
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertNull(dao.obtenerReparacionXels(1));
    }

    /** La query resumida del remito mapea las 9 columnas de la grilla (sin JOIN de Equipos.Nombre). */
    @Test
    public void readAllXIDclienteIDSucursalResumido_mapeaColumnasDeGrilla() throws SQLException {
        when(mockRs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockRs.getInt("ELS")).thenReturn(101).thenReturn(102);
        when(mockRs.getString("Nombre")).thenReturn("TV Samsung 50").thenReturn("PC HP");
        when(mockRs.getString("Marca")).thenReturn("Samsung").thenReturn("HP");
        when(mockRs.getString("Modelo")).thenReturn("UE50").thenReturn("Pavilion");
        when(mockRs.getString("NumeroDeSerie")).thenReturn("SN-1").thenReturn("SN-2");
        when(mockRs.getString("Aviso")).thenReturn("Lunes").thenReturn("Viernes");
        when(mockRs.getString("EstadoTecnico")).thenReturn("En Reparación").thenReturn("Terminado");
        when(mockRs.getString("EstadoComercial")).thenReturn("Aceptado").thenReturn("NO Aceptado");
        when(mockRs.getBoolean("Agregadoaremito")).thenReturn(false).thenReturn(false);

        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        List<ReparacionDTO> lista = dao.readAllXIDclienteIDSucursalResumido(7, 3);

        assertEquals(2, lista.size());
        assertEquals(101, lista.get(0).getELS());
        assertEquals("TV Samsung 50", lista.get(0).getNombreEquipo());
        assertEquals("Samsung", lista.get(0).getMarca());
        assertEquals("UE50", lista.get(0).getModelo());
        assertEquals("SN-1", lista.get(0).getNumeroDeSerie());
        assertEquals("Lunes", lista.get(0).getAviso());
        assertEquals("En Reparación", lista.get(0).getEstadoTecnico());
        assertEquals("Aceptado", lista.get(0).getEstadoComercial());
        assertFalse(lista.get(0).getAgregadoaremito());
        assertEquals(102, lista.get(1).getELS());
        assertEquals("PC HP", lista.get(1).getNombreEquipo());
        verify(mockStmt).setInt(1, 7);
        verify(mockStmt).setInt(2, 3);
    }

    /** Sin filas, la query resumida devuelve lista vacía (no null). */
    @Test
    public void readAllXIDclienteIDSucursalResumido_sinResultadosDevuelveListaVacia() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertEquals(0, dao.readAllXIDclienteIDSucursalResumido(7, 3).size());
    }

    /** reasignarReparacionesDeUsuario ejecuta UPDATE con ambos ids y devuelve filas afectadas. */
    @Test
    public void reasignarReparacionesDeUsuario_ejecutaUpdateConParametros() throws SQLException {
        when(mockStmt.executeUpdate()).thenReturn(3);

        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        int actualizadas = dao.reasignarReparacionesDeUsuario(1, 9);

        assertEquals(3, actualizadas);
        verify(mockStmt).setInt(1, 1);   // idUsuario nuevo
        verify(mockStmt).setInt(2, 9);   // idUsuario anterior
        verify(mockStmt).executeUpdate();
    }

    /** Ante excepción SQL, devuelve 0 sin propagar el fallo. */
    @Test
    public void reasignarReparacionesDeUsuario_conErrorDevuelveCero() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("BD caída"));
        ReparacionDAOImpl dao = new ReparacionDAOImpl("Bariloche");
        assertEquals(0, dao.reasignarReparacionesDeUsuario(1, 9));
    }
}
