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

import dto.RepuestosDTO;
import persistencia.conexion.Conexion;

/**
 * Valida RepuestosDAOImpl (repuestos de equipos) mockeando la cadena JDBC.
 */
public class RepuestosDAOImplTest {

    private static final String INSERT_SQL = "INSERT INTO reemplazos(ELS,ref,original,reemplazo,notas) VALUES(?,?,?,?,?)";
    private static final String DELETE_SQL = "DELETE FROM reemplazos WHERE idReemplazos = ?";
    private static final String READ_BY_ELS = "SELECT idReemplazos,ELS,ref,original,reemplazo,notas FROM reemplazos WHERE ELS = ?";
    private static final String UPDATE_SQL = "UPDATE reemplazos SET ELS = ?, ref = ?, original = ?, reemplazo = ?, notas = ? WHERE idReemplazos = ?";

    private Conexion mockConexion;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private MockedStatic<Conexion> mockedStatic;

    private RepuestosDTO repuesto;

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

        repuesto = new RepuestosDTO(10, 333, "R-001", "Original A", "Reemplazo B", "Nota extra");
    }

    @After
    public void tearDown() {
        mockedStatic.close();
    }

    @Test
    public void insert_vinculaTodosLosParametros() throws SQLException {
        RepuestosDTO nuevo = new RepuestosDTO(333, "R-001", "Original A", "Reemplazo B", "Nota extra");

        boolean resultado = new RepuestosDAOImpl("Bariloche").insert(nuevo);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(INSERT_SQL));
        verify(mockStmt).setInt(1, 333);
        verify(mockStmt).setString(2, "R-001");
        verify(mockStmt).setString(3, "Original A");
        verify(mockStmt).setString(4, "Reemplazo B");
        verify(mockStmt).setString(5, "Nota extra");
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void insert_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new RepuestosDAOImpl("Bariloche").insert(repuesto));
    }

    @Test
    public void edit_vinculaTodosLosParametrosIncluidoId() throws SQLException {
        boolean resultado = new RepuestosDAOImpl("Bariloche").edit(repuesto);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(UPDATE_SQL));
        verify(mockStmt).setInt(1, 333);
        verify(mockStmt).setString(2, "R-001");
        verify(mockStmt).setString(3, "Original A");
        verify(mockStmt).setString(4, "Reemplazo B");
        verify(mockStmt).setString(5, "Nota extra");
        verify(mockStmt).setInt(6, 10);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void delete_vinculaIdYDevuelveTrue() throws SQLException {
        boolean resultado = new RepuestosDAOImpl("Bariloche").delete(repuesto);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(DELETE_SQL));
        verify(mockStmt).setInt(1, 10);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void readAll_mapeaTodasLasFilas() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("idReemplazos")).thenReturn(10, 11);
        when(mockRs.getInt("ELS")).thenReturn(333, 334);
        when(mockRs.getString("ref")).thenReturn("R-001", "R-002");
        when(mockRs.getString("original")).thenReturn("Orig A", "Orig B");
        when(mockRs.getString("reemplazo")).thenReturn("Reem A", "Reem B");
        when(mockRs.getString("notas")).thenReturn("n1", "n2");

        List<RepuestosDTO> repuestos = new RepuestosDAOImpl("Bariloche").readAll();

        assertEquals(2, repuestos.size());
        assertEquals("R-001", repuestos.get(0).getRef());
        assertEquals("Orig A", repuestos.get(0).getOriginal());
        assertEquals(334, repuestos.get(1).getELS());
    }

    @Test
    public void obtenerRepuestosXels_filtraPorEquipo() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("idReemplazos")).thenReturn(10, 11);
        when(mockRs.getInt("ELS")).thenReturn(333, 333);
        when(mockRs.getString("ref")).thenReturn("R-001", "R-002");
        when(mockRs.getString("original")).thenReturn("Orig A", "Orig B");
        when(mockRs.getString("reemplazo")).thenReturn("Reem A", "Reem B");
        when(mockRs.getString("notas")).thenReturn("n1", "n2");

        RepuestosDAOImpl dao = new RepuestosDAOImpl("Bariloche");
        List<RepuestosDTO> repuestos = dao.obtenerRepuestosXels(333);

        assertEquals(2, repuestos.size());
        verify(mockConn).prepareStatement(eq(READ_BY_ELS));
        verify(mockStmt).setInt(1, 333);
    }

    @Test
    public void obtenerRepuestosXels_sinFilasDevuelveListaVacia() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertTrue(new RepuestosDAOImpl("Bariloche").obtenerRepuestosXels(999).isEmpty());
    }

    @Test
    public void obtenerRepuestosXels_conExcepcionSQLDevuelveListaVacia() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertTrue(new RepuestosDAOImpl("Bariloche").obtenerRepuestosXels(333).isEmpty());
    }
}