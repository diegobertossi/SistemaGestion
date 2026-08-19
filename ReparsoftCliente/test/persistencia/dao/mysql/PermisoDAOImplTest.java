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

import dto.PermisoDTO;
import persistencia.conexion.Conexion;

/**
 * Valida PermisoDAOImpl (permisos por rol y pantalla) mockeando la cadena JDBC.
 */
public class PermisoDAOImplTest {

    private static final String INSERT_SQL = "INSERT INTO permisos(idPermiso,idRol,idPantalla) VALUES(0,?,?)";
    private static final String DELETE_SQL = "DELETE FROM permisos WHERE idPermiso = ?";

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
    public void insert_vinculaRolYPantalla() throws SQLException {
        PermisoDTO permiso = new PermisoDTO(0, 3, 7, "Clientes");

        boolean resultado = new PermisoDAOImpl("Bariloche").insert(permiso);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(INSERT_SQL));
        verify(mockStmt).setInt(1, 3);
        verify(mockStmt).setInt(2, 7);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void insert_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new PermisoDAOImpl("Bariloche").insert(new PermisoDTO(0, 3, 7, "Clientes")));
    }

    @Test
    public void delete_vinculaIdPermisoYDevuelveTrue() throws SQLException {
        PermisoDTO permiso = new PermisoDTO(12, 3, 7, "Clientes");

        boolean resultado = new PermisoDAOImpl("Bariloche").delete(permiso);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(DELETE_SQL));
        verify(mockStmt).setInt(1, 12);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void readAll_mapeaNombreYPadreFiltradoPorRol() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("idPermiso")).thenReturn(1, 2);
        when(mockRs.getInt("idRol")).thenReturn(3, 3);
        when(mockRs.getInt("idPantalla")).thenReturn(7, 8);
        when(mockRs.getString("nombre")).thenReturn("Clientes", "Equipos");
        when(mockRs.getString("padre")).thenReturn("Menu Principal", "Clientes");

        PermisoDAOImpl dao = new PermisoDAOImpl("Bariloche");
        List<PermisoDTO> permisos = dao.readAll(3);

        assertEquals(2, permisos.size());
        verify(mockStmt).setInt(1, 3);
        assertEquals("Clientes", permisos.get(0).getNombrePantalla());
        assertEquals("Menu Principal", permisos.get(0).getNombrePantallaPadre());
        assertEquals("Equipos", permisos.get(1).getNombrePantalla());
        assertEquals("Clientes", permisos.get(1).getNombrePantallaPadre());
    }

    @Test
    public void readAll_sinFilasDevuelveListaVacia() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertTrue(new PermisoDAOImpl("Bariloche").readAll(3).isEmpty());
    }

    @Test
    public void readAllPadres_mapeaSoloLosPadresDelRol() throws SQLException {
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("idPermiso")).thenReturn(5);
        when(mockRs.getInt("idRol")).thenReturn(3);
        when(mockRs.getInt("idPantalla")).thenReturn(1);
        when(mockRs.getString("nombre")).thenReturn("Menu Principal");

        PermisoDAOImpl dao = new PermisoDAOImpl("Bariloche");
        List<PermisoDTO> permisos = dao.readAllPadres(3);

        assertEquals(1, permisos.size());
        verify(mockStmt).setInt(1, 3);
        assertEquals("Menu Principal", permisos.get(0).getNombrePantalla());
    }

    @Test
    public void readAllHijos_filtraPorRolYPantallaPadre() throws SQLException {
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("idPermiso")).thenReturn(6);
        when(mockRs.getInt("idRol")).thenReturn(3);
        when(mockRs.getInt("idPantalla")).thenReturn(8);
        when(mockRs.getString("nombre")).thenReturn("Equipos");

        PermisoDAOImpl dao = new PermisoDAOImpl("Bariloche");
        List<PermisoDTO> permisos = dao.readAllHijos(3, "Clientes");

        assertEquals(1, permisos.size());
        verify(mockStmt).setInt(1, 3);
        verify(mockStmt).setString(2, "Clientes");
        assertEquals("Equipos", permisos.get(0).getNombrePantalla());
    }

    @Test
    public void readFaltantes_mapeaConIdPermisoCero() throws SQLException {
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("idPantalla")).thenReturn(9);
        when(mockRs.getString("nombre")).thenReturn("Backup");
        when(mockRs.getString("padre")).thenReturn("Menu Principal");

        PermisoDAOImpl dao = new PermisoDAOImpl("Bariloche");
        List<PermisoDTO> permisos = dao.readFaltantes(3);

        assertEquals(1, permisos.size());
        verify(mockStmt).setInt(1, 3);
        assertEquals(Integer.valueOf(0), permisos.get(0).getIdPermiso());
        assertEquals("Backup", permisos.get(0).getNombrePantalla());
        assertEquals("Menu Principal", permisos.get(0).getNombrePantallaPadre());
    }

    @Test
    public void readAll_conExcepcionSQLDevuelveListaVacia() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertTrue(new PermisoDAOImpl("Bariloche").readAll(3).isEmpty());
    }
}
