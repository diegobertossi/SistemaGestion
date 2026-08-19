package persistencia.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import persistencia.conexion.Conexion;

/**
 * Valida el cache de sesión del combo de ELS (D1):
 * listarELS consulta la BD solo la primera vez y reutiliza la lista;
 * invalidarCacheELS obliga a re-consultar tras insertar/borrar reparaciones.
 */
public class ReparacionComboManagerTest {

    private Conexion mockConexion;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;

    @Before
    public void setUp() throws SQLException {
        mockConexion = mock(Conexion.class);
        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockRs = mock(ResultSet.class);

        when(mockConexion.getSQLConexion()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
    }

    /** El combo se puebla con los ELS devueltos por la consulta. */
    @Test
    public void listarELS_populaElComboBoxConLosDatos() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, true, false);
        when(mockRs.getString(1)).thenReturn("1001", "1002", "1003");

        ReparacionComboManager manager = new ReparacionComboManager(mockConexion);
        JComboBox<String> combo = new JComboBox<>();
        manager.listarELS(combo);

        assertEquals(3, combo.getItemCount());
        assertEquals("1001", combo.getItemAt(0));
        assertEquals("1002", combo.getItemAt(1));
        assertEquals("1003", combo.getItemAt(2));
    }

    /** La lista de ELS se cachea: la segunda llamada no vuelve a tocar la BD. */
    @Test
    public void listarELS_consultaLaBaseUnaSolaVez() throws SQLException {
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getString(1)).thenReturn("1001");

        ReparacionComboManager manager = new ReparacionComboManager(mockConexion);
        manager.listarELS(new JComboBox<>());
        manager.listarELS(new JComboBox<>());
        manager.listarELS(new JComboBox<>());

        verify(mockConn, times(1)).prepareStatement(eq(SQLQueries.READ_ALL_ELS));
    }

    /** Al invalidar el cache (insert/delete) se vuelve a consultar la BD. */
    @Test
    public void invalidarCacheELS_obligaANuevaConsulta() throws SQLException {
        ResultSet rs1 = mock(ResultSet.class);
        ResultSet rs2 = mock(ResultSet.class);
        when(mockStmt.executeQuery()).thenReturn(rs1, rs2);
        when(rs1.next()).thenReturn(true, false);
        when(rs1.getString(1)).thenReturn("1001");
        when(rs2.next()).thenReturn(true, false);
        when(rs2.getString(1)).thenReturn("2001");

        ReparacionComboManager manager = new ReparacionComboManager(mockConexion);

        JComboBox<String> combo1 = new JComboBox<>();
        manager.listarELS(combo1);
        assertEquals("1001", combo1.getItemAt(0));

        manager.invalidarCacheELS();

        JComboBox<String> combo2 = new JComboBox<>();
        manager.listarELS(combo2);
        assertEquals("2001", combo2.getItemAt(0));

        verify(mockConn, times(2)).prepareStatement(eq(SQLQueries.READ_ALL_ELS));
    }

    /** La consulta del combo usa exactamente la constante READ_ALL_ELS. */
    @Test
    public void listarELS_usaLaConstanteReadAllEls() throws SQLException {
        when(mockRs.next()).thenReturn(false);

        ReparacionComboManager manager = new ReparacionComboManager(mockConexion);
        manager.listarELS(new JComboBox<>());

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockConn).prepareStatement(sqlCaptor.capture());
        assertEquals(SQLQueries.READ_ALL_ELS, sqlCaptor.getValue());
    }

    /** Sin filas en la BD el combo queda vacío (sin excepción). */
    @Test
    public void listarELS_conBaseVaciaDejaElComboVacio() throws SQLException {
        when(mockRs.next()).thenReturn(false);

        ReparacionComboManager manager = new ReparacionComboManager(mockConexion);
        JComboBox<String> combo = new JComboBox<>();
        manager.listarELS(combo);

        assertEquals(0, combo.getItemCount());
    }

    /** Ante una excepción SQL la lista cacheada queda vacía (sin crash). */
    @Test
    public void listarELS_conExcepcionSQLNoRevienta() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("BD caída"));

        ReparacionComboManager manager = new ReparacionComboManager(mockConexion);
        JComboBox<String> combo = new JComboBox<>();
        manager.listarELS(combo);

        assertTrue(combo.getModel() instanceof DefaultComboBoxModel);
    }
}
