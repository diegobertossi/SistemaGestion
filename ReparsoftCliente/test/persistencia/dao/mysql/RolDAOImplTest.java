package persistencia.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

import dto.RolDTO;
import persistencia.conexion.Conexion;

/**
 * Valida RolDAOImpl (roles de usuario) mockeando la cadena JDBC.
 */
public class RolDAOImplTest {

    private static final String INSERT_SQL = "INSERT INTO rol(idRol,nombre) VALUES(?,?)";
    private static final String DELETE_SQL = "DELETE FROM rol WHERE idRol = ?";
    private static final String UPDATE_SQL = "UPDATE rol SET nombre = ? WHERE idRol = ?";

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
    }

    @After
    public void tearDown() {
        mockedStatic.close();
    }

    @Test
    public void insert_vinculaIdYNombre() throws SQLException {
        RolDTO rol = new RolDTO(3, "Tecnico");

        boolean resultado = new RolDAOImpl("Bariloche").insert(rol);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(INSERT_SQL));
        verify(mockStmt).setInt(1, 3);
        verify(mockStmt).setString(2, "Tecnico");
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void insert_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new RolDAOImpl("Bariloche").insert(new RolDTO(3, "Tecnico")));
    }

    @Test
    public void edit_vinculaNombreYId() throws SQLException {
        RolDTO rol = new RolDTO(3, "Administrador");

        boolean resultado = new RolDAOImpl("Bariloche").edit(rol);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(UPDATE_SQL));
        verify(mockStmt).setString(1, "Administrador");
        verify(mockStmt).setInt(2, 3);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void delete_vinculaIdYDevuelveTrue() throws SQLException {
        RolDTO rol = new RolDTO(3, "Tecnico");

        boolean resultado = new RolDAOImpl("Bariloche").delete(rol);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(DELETE_SQL));
        verify(mockStmt).setInt(1, 3);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void delete_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new RolDAOImpl("Bariloche").delete(new RolDTO(3, "Tecnico")));
    }

    @Test
    public void readAll_mapeaTodasLasFilas() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("idRol")).thenReturn(1, 2);
        when(mockRs.getString("nombre")).thenReturn("Admin", "Tecnico");

        List<RolDTO> roles = new RolDAOImpl("Bariloche").readAll();

        assertEquals(2, roles.size());
        assertEquals("Admin", roles.get(0).getNombre());
        assertEquals(1, roles.get(0).getIdRol());
        assertEquals("Tecnico", roles.get(1).getNombre());
    }

    @Test
    public void readAll_sinFilasDevuelveListaVacia() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertTrue(new RolDAOImpl("Bariloche").readAll().isEmpty());
    }

    @Test
    public void readAllxid_devuelveElNombreDelRol() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("nombre")).thenReturn("Tecnico");

        assertEquals("Tecnico", new RolDAOImpl("Bariloche").readAllxid(2));
    }

    @Test
    public void readAllxid_sinResultadosDevuelveVacio() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertEquals("", new RolDAOImpl("Bariloche").readAllxid(999));
    }

    @Test
    public void readAllxid_conExcepcionSQLDevuelveVacio() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertEquals("", new RolDAOImpl("Bariloche").readAllxid(2));
    }
}