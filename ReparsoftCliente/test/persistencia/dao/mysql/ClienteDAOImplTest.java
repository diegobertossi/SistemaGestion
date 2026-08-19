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

import dto.ClienteDTO;
import persistencia.conexion.Conexion;

/**
 * Valida ClienteDAOImpl mockeando el singleton Conexion y toda la cadena
 * JDBC (Connection/PreparedStatement/ResultSet). Sin base de datos real.
 */
public class ClienteDAOImplTest {

    private static final String INSERT_SQL =
        "INSERT INTO Cliente(idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico,tipo_documento,condicion_iva,tipo_persona) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_SQL =
        "UPDATE Cliente SET nombre = ?, CUIT = ?, Domicilio = ?, TelefonoEmpresa = ?, Contacto = ?, TelefonoContacto = ?, CorreoElectronico = ?, tipo_documento = ?, condicion_iva = ?, tipo_persona = ? WHERE idCliente = ?";

    private static final String DELETE_SQL = "DELETE FROM Cliente WHERE idCliente = ?";

    private Conexion mockConexion;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private MockedStatic<Conexion> mockedStatic;

    private ClienteDTO cliente;

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

        cliente = new ClienteDTO(15, "Cliente SA", "30-11111111-1", "Calle 1",
                "444-4444", "Juan", "555-5555", "cli@test.com");
    }

    @After
    public void tearDown() {
        mockedStatic.close();
    }

    /** El insert usa el SQL esperado, vincula los 11 parámetros y devuelve true. */
    @Test
    public void insert_vinculaTodosLosParametros() throws SQLException {
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");

        boolean resultado = dao.insert(cliente);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(INSERT_SQL));
        verify(mockStmt).setInt(1, 15);
        verify(mockStmt).setString(2, "Cliente SA");
        verify(mockStmt).setString(3, "30-11111111-1");
        verify(mockStmt).setString(4, "Calle 1");
        verify(mockStmt).setString(5, "444-4444");
        verify(mockStmt).setString(6, "Juan");
        verify(mockStmt).setString(7, "555-5555");
        verify(mockStmt).setString(8, "cli@test.com");
        verify(mockStmt).setString(9, "CUIT");
        verify(mockStmt).setString(10, "");
        verify(mockStmt).setString(11, "empresa");
        verify(mockStmt).executeUpdate();
    }

    /** Si executeUpdate devuelve 0 el insert informa false. */
    @Test
    public void insert_sinFilasAfectadasDevuelveFalse() throws SQLException {
        when(mockStmt.executeUpdate()).thenReturn(0);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertFalse(dao.insert(cliente));
    }

    /** Ante una excepción SQL el insert no revienta: devuelve false. */
    @Test
    public void insert_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertFalse(dao.insert(cliente));
    }

    /** readAll mapea todas las filas del ResultSet. */
    @Test
    public void readAll_mapeaTodasLasFilas() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("idCliente")).thenReturn(1, 2);
        when(mockRs.getString("nombre")).thenReturn("Cliente A", "Cliente B");
        when(mockRs.getString("CUIT")).thenReturn("30-1", "30-2");
        when(mockRs.getString("Domicilio")).thenReturn("Dom A", "Dom B");
        when(mockRs.getString("TelefonoEmpresa")).thenReturn("444", "555");
        when(mockRs.getString("Contacto")).thenReturn("C A", "C B");
        when(mockRs.getString("TelefonoContacto")).thenReturn("666", "777");
        when(mockRs.getString("CorreoElectronico")).thenReturn("a@a.com", "b@b.com");

        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        List<ClienteDTO> clientes = dao.readAll();

        assertEquals(2, clientes.size());
        assertEquals("Cliente A", clientes.get(0).getRazon_Social());
        assertEquals("30-1", clientes.get(0).getCUIT());
        assertEquals("Cliente B", clientes.get(1).getRazon_Social());
        assertEquals("CUIT", clientes.get(0).getTipoDocumento());
        assertEquals("empresa", clientes.get(0).getTipoPersona());
    }

    /** Con ResultSet vacío readAll devuelve lista vacía. */
    @Test
    public void readAll_sinFilasDevuelveListaVacia() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertTrue(dao.readAll().isEmpty());
    }

    /** obtenerIDporNombre vincula el nombre y devuelve el id de la fila. */
    @Test
    public void obtenerIDporNombre_devuelveIdDeLaFila() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("idCliente")).thenReturn(42);

        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        int id = dao.obtenerIDporNombre("Cliente SA");

        assertEquals(42, id);
        verify(mockStmt).setString(1, "Cliente SA");
    }

    /** Sin filas coincidentes devuelve 0 (comportamiento actual). */
    @Test
    public void obtenerIDporNombre_sinResultadosDevuelveCero() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertEquals(0, dao.obtenerIDporNombre("Inexistente"));
    }

    /** contarClientes lee el count(*) de la primera columna. */
    @Test
    public void contarClientes_devuelveElConteo() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(7);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertEquals(7, dao.contarClientes());
    }

    /** dameCuitPorIdCliente mapea la columna CUIT. */
    @Test
    public void dameCuitPorIdCliente_devuelveCuit() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("CUIT")).thenReturn("30-99999999-9");
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertEquals("30-99999999-9", dao.dameCuitPorIdCliente(15));
    }

    /** obtenerContactoPorCliente sin resultados devuelve string vacío. */
    @Test
    public void obtenerContactoPorCliente_sinResultadosDevuelveVacio() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertEquals("", dao.obtenerContactoPorCliente("Nadie"));
    }

    /** obtenerPorRazonSocial consulta por nombre y mapea CUIT + Domicilio (sin leer toda la tabla). */
    @Test
    public void obtenerPorRazonSocial_mapeaCuitYDomicilio() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("CUIT")).thenReturn("30-11111111-1");
        when(mockRs.getString("Domicilio")).thenReturn("Calle 1");

        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        ClienteDTO clienteEncontrado = dao.obtenerPorRazonSocial("Cliente SA");

        assertEquals("Cliente SA", clienteEncontrado.getRazon_Social());
        assertEquals("30-11111111-1", clienteEncontrado.getCUIT());
        assertEquals("Calle 1", clienteEncontrado.getDomicilio());
    }

    /** obtenerPorRazonSocial sin coincidencias devuelve null (sin excepción). */
    @Test
    public void obtenerPorRazonSocial_sinResultadosDevuelveNull() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertEquals(null, dao.obtenerPorRazonSocial("Inexistente"));
    }

    /** obtenerPorRazonSocial ante excepción SQL devuelve null (sin reventar). */
    @Test
    public void obtenerPorRazonSocial_conExcepcionSQLDevuelveNull() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertEquals(null, dao.obtenerPorRazonSocial("Cliente SA"));
    }

    /** obtenerReparacionxIDCliente informa true cuando el COUNT es > 0. */
    @Test
    public void obtenerReparacionxIDCliente_detectaReparaciones() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(0);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertFalse(dao.obtenerReparacionxIDCliente(15));

        when(mockRs.getInt(1)).thenReturn(3);
        assertTrue(dao.obtenerReparacionxIDCliente(15));
    }

    /** edit usa el UPDATE esperado, vincula los 11 parámetros y devuelve true. */
    @Test
    public void edit_vinculaTodosLosParametros() throws SQLException {
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");

        boolean resultado = dao.edit(cliente);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(UPDATE_SQL));
        verify(mockStmt).setString(1, "Cliente SA");
        verify(mockStmt).setString(2, "30-11111111-1");
        verify(mockStmt).setString(3, "Calle 1");
        verify(mockStmt).setString(4, "444-4444");
        verify(mockStmt).setString(5, "Juan");
        verify(mockStmt).setString(6, "555-5555");
        verify(mockStmt).setString(7, "cli@test.com");
        verify(mockStmt).setString(8, "CUIT");
        verify(mockStmt).setString(9, "");
        verify(mockStmt).setString(10, "empresa");
        verify(mockStmt).setInt(11, 15);
        verify(mockStmt).executeUpdate();
    }

    /** edit usa defaults cuando tipo_documento/condicion_iva/tipo_persona son null. */
    @Test
    public void edit_aplicaDefaultsCuandoCamposSonNull() throws SQLException {
        ClienteDTO c = new ClienteDTO(7, "Sin datos", null, null, null, null, null, null, null, null, null);

        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        boolean resultado = dao.edit(c);

        assertTrue(resultado);
        verify(mockStmt).setString(8, "CUIT");
        verify(mockStmt).setString(9, "");
        verify(mockStmt).setString(10, "empresa");
        verify(mockStmt).setInt(11, 7);
    }

    /** Si executeUpdate devuelve 0 el edit informa false. */
    @Test
    public void edit_sinFilasAfectadasDevuelveFalse() throws SQLException {
        when(mockStmt.executeUpdate()).thenReturn(0);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertFalse(dao.edit(cliente));
    }

    /** Ante una excepción SQL el edit no revienta: devuelve false. */
    @Test
    public void edit_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertFalse(dao.edit(cliente));
    }

    /** delete usa el DELETE esperado, vincula el id y devuelve true. */
    @Test
    public void delete_vinculaIdYDevuelveTrue() throws SQLException {
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");

        boolean resultado = dao.delete(cliente);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(DELETE_SQL));
        verify(mockStmt).setInt(1, 15);
        verify(mockStmt).executeUpdate();
    }

    /** Si executeUpdate devuelve 0 el delete informa false. */
    @Test
    public void delete_sinFilasAfectadasDevuelveFalse() throws SQLException {
        when(mockStmt.executeUpdate()).thenReturn(0);
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertFalse(dao.delete(cliente));
    }

    /** Ante una excepción SQL el delete no revienta: devuelve false. */
    @Test
    public void delete_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        ClienteDAOImpl dao = new ClienteDAOImpl("Bariloche");
        assertFalse(dao.delete(cliente));
    }
}
