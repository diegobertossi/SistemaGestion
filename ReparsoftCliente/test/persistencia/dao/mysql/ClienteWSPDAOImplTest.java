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

import dto.ClienteWSPDTO;
import persistencia.conexion.Conexion;

/**
 * Valida ClienteWSPDAOImpl (contactos de WhatsApp) mockeando la cadena JDBC.
 */
public class ClienteWSPDAOImplTest {

    private static final String INSERT_SQL = "INSERT INTO ClienteWSP(idClienteWSP,organizacion,nombreWSP,TelefonoWSP) VALUES(?,?,?,?)";
    private static final String DELETE_SQL = "DELETE FROM ClienteWSP WHERE idClienteWSP = ?";
    private static final String UPDATE_SQL = "UPDATE ClienteWSP SET organizacion = ?, nombreWSP = ?, TelefonoWSP = ? WHERE idClienteWSP = ?";
    private static final String ID_POR_NOMBRE = "SELECT idClienteWSP FROM ClienteWSP WHERE nombreWSP = ?";

    private Conexion mockConexion;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private MockedStatic<Conexion> mockedStatic;

    private ClienteWSPDTO cliente;

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

        cliente = new ClienteWSPDTO(4, "Empresa SA", "Juan Perez", "2944000000");
    }

    @After
    public void tearDown() {
        mockedStatic.close();
    }

    @Test
    public void insert_vinculaTodosLosParametros() throws SQLException {
        boolean resultado = new ClienteWSPDAOImpl("Bariloche").insert(cliente);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(INSERT_SQL));
        verify(mockStmt).setInt(1, 4);
        verify(mockStmt).setString(2, "Empresa SA");
        verify(mockStmt).setString(3, "Juan Perez");
        verify(mockStmt).setString(4, "2944000000");
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void insert_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new ClienteWSPDAOImpl("Bariloche").insert(cliente));
    }

    @Test
    public void edit_vinculaTodosLosParametrosIncluidoId() throws SQLException {
        boolean resultado = new ClienteWSPDAOImpl("Bariloche").edit(cliente);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(UPDATE_SQL));
        verify(mockStmt).setString(1, "Empresa SA");
        verify(mockStmt).setString(2, "Juan Perez");
        verify(mockStmt).setString(3, "2944000000");
        verify(mockStmt).setInt(4, 4);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void delete_vinculaIdYDevuelveTrue() throws SQLException {
        boolean resultado = new ClienteWSPDAOImpl("Bariloche").delete(cliente);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(DELETE_SQL));
        verify(mockStmt).setInt(1, 4);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void readAll_mapeaTodasLasFilas() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("idClienteWSP")).thenReturn(4, 5);
        when(mockRs.getString("organizacion")).thenReturn("Empresa SA", "Otra SA");
        when(mockRs.getString("nombreWSP")).thenReturn("Juan Perez", "Maria Lopez");
        when(mockRs.getString("TelefonoWSP")).thenReturn("2944000000", "2944000001");

        List<ClienteWSPDTO> clientes = new ClienteWSPDAOImpl("Bariloche").readAll();

        assertEquals(2, clientes.size());
        assertEquals("Juan Perez", clientes.get(0).getNombreWSP());
        assertEquals("2944000000", clientes.get(0).getTelefonoWSP());
        assertEquals("Otra SA", clientes.get(1).getOrganizacion());
    }

    @Test
    public void readAll_sinFilasDevuelveListaVacia() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertTrue(new ClienteWSPDAOImpl("Bariloche").readAll().isEmpty());
    }

    @Test
    public void obtenerIDporNombreWSP_consultaPorColumnaNombreWSP() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("idClienteWSP")).thenReturn(4);

        ClienteWSPDAOImpl dao = new ClienteWSPDAOImpl("Bariloche");
        int id = dao.obtenerIDporNombreWSP("Juan Perez");

        assertEquals(4, id);
        verify(mockConn).prepareStatement(eq(ID_POR_NOMBRE));
        verify(mockStmt).setString(1, "Juan Perez");
    }

    @Test
    public void obtenerIDporNombreWSP_sinResultadosDevuelveCero() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertEquals(0, new ClienteWSPDAOImpl("Bariloche").obtenerIDporNombreWSP("Inexistente"));
    }

    @Test
    public void obtenerIDclienteWSP_devuelveElMaximo() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(9);
        assertEquals(9, new ClienteWSPDAOImpl("Bariloche").obtenerIDclienteWSP());
    }

    @Test
    public void obtenerNumeroPorCliente_devuelveElTelefono() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString(1)).thenReturn("2944000000");
        assertEquals("2944000000", new ClienteWSPDAOImpl("Bariloche").obtenerNumeroPorCliente("Juan Perez"));
    }

    @Test
    public void obtenerNumeroPorCliente_sinResultadosDevuelveVacio() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertEquals("", new ClienteWSPDAOImpl("Bariloche").obtenerNumeroPorCliente("Nadie"));
    }

    @Test
    public void obtenetTelefonoXcontacto_devuelveElTelefono() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString(1)).thenReturn("2944000000");
        assertEquals("2944000000", new ClienteWSPDAOImpl("Bariloche").obtenetTelefonoXcontacto("Juan Perez"));
    }
}