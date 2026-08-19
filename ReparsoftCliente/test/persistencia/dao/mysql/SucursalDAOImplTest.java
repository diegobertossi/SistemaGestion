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

import dto.SucursalDTO;
import persistencia.conexion.Conexion;

/**
 * Valida SucursalDAOImpl mockeando el singleton Conexion y la cadena JDBC.
 */
public class SucursalDAOImplTest {

    private static final String INSERT_SQL = "INSERT INTO Sucursal(IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico) VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE Sucursal SET NombreSucursal = ?, idCliente = ?, DomicilioSucursal = ?, ContactoSucursal = ?, TelefonoSucursal = ?, CorreoElectronico = ? WHERE IdSucursal = ?";
    private static final String DELETE_SQL = "DELETE FROM Sucursal WHERE IdSucursal = ?";

    private Conexion mockConexion;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private MockedStatic<Conexion> mockedStatic;

    private SucursalDTO sucursal;

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

        sucursal = new SucursalDTO(5, "Sucursal Norte", 15, "Calle 1", "Juan", "444-4444", "suc@test.com");
    }

    @After
    public void tearDown() {
        mockedStatic.close();
    }

    @Test
    public void insert_vinculaTodosLosParametros() throws SQLException {
        SucursalDAOImpl dao = new SucursalDAOImpl("Bariloche");

        boolean resultado = dao.insert(sucursal);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(INSERT_SQL));
        verify(mockStmt).setInt(1, 5);
        verify(mockStmt).setString(2, "Sucursal Norte");
        verify(mockStmt).setInt(3, 15);
        verify(mockStmt).setString(4, "Calle 1");
        verify(mockStmt).setString(5, "Juan");
        verify(mockStmt).setString(6, "444-4444");
        verify(mockStmt).setString(7, "suc@test.com");
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void insert_sinFilasAfectadasDevuelveFalse() throws SQLException {
        when(mockStmt.executeUpdate()).thenReturn(0);
        assertFalse(new SucursalDAOImpl("Bariloche").insert(sucursal));
    }

    @Test
    public void insert_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new SucursalDAOImpl("Bariloche").insert(sucursal));
    }

    @Test
    public void edit_vinculaTodosLosParametros() throws SQLException {
        SucursalDAOImpl dao = new SucursalDAOImpl("Bariloche");

        boolean resultado = dao.edit(sucursal);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(UPDATE_SQL));
        verify(mockStmt).setString(1, "Sucursal Norte");
        verify(mockStmt).setInt(2, 15);
        verify(mockStmt).setString(3, "Calle 1");
        verify(mockStmt).setString(4, "Juan");
        verify(mockStmt).setString(5, "444-4444");
        verify(mockStmt).setString(6, "suc@test.com");
        verify(mockStmt).setInt(7, 5);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void edit_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new SucursalDAOImpl("Bariloche").edit(sucursal));
    }

    @Test
    public void delete_vinculaIdYDevuelveTrue() throws SQLException {
        SucursalDAOImpl dao = new SucursalDAOImpl("Bariloche");

        boolean resultado = dao.delete(sucursal);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(DELETE_SQL));
        verify(mockStmt).setInt(1, 5);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void delete_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new SucursalDAOImpl("Bariloche").delete(sucursal));
    }

    @Test
    public void readAll_mapeaTodasLasFilas() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("IdSucursal")).thenReturn(5, 6);
        when(mockRs.getString("NombreSucursal")).thenReturn("Norte", "Sur");
        when(mockRs.getInt("idCliente")).thenReturn(15, 15);
        when(mockRs.getString("DomicilioSucursal")).thenReturn("C1", "C2");
        when(mockRs.getString("ContactoSucursal")).thenReturn("J", "M");
        when(mockRs.getString("TelefonoSucursal")).thenReturn("444", "555");
        when(mockRs.getString("CorreoElectronico")).thenReturn("a@a.com", "b@b.com");

        List<SucursalDTO> sucursales = new SucursalDAOImpl("Bariloche").readAll();

        assertEquals(2, sucursales.size());
        assertEquals("Norte", sucursales.get(0).getNombreSucursal());
        assertEquals(5, sucursales.get(0).getIdSucursal());
        assertEquals(15, sucursales.get(0).getIdClientesuc());
        assertEquals("C1", sucursales.get(0).getDomicilioSucursal());
        assertEquals("Sur", sucursales.get(1).getNombreSucursal());
    }

    @Test
    public void readAll_sinFilasDevuelveListaVacia() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertTrue(new SucursalDAOImpl("Bariloche").readAll().isEmpty());
    }

    @Test
    public void obtenerSucursalXidCliente_filtraPorCliente() throws SQLException {
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("IdSucursal")).thenReturn(5);
        when(mockRs.getString("NombreSucursal")).thenReturn("Norte");

        SucursalDAOImpl dao = new SucursalDAOImpl("Bariloche");
        List<SucursalDTO> sucursales = dao.obtenerSucursalXidCliente(15);

        assertEquals(1, sucursales.size());
        verify(mockStmt).setInt(1, 15);
    }

    @Test
    public void obtenerIDsucursal_devuelveElMaximo() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(9);
        assertEquals(9, new SucursalDAOImpl("Bariloche").obtenerIDsucursal());
    }

    @Test
    public void obtenercantidaddeSucursales_devuelveElConteo() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("total")).thenReturn(3);
        assertEquals(3, new SucursalDAOImpl("Bariloche").obtenercantidaddeSucursales(15));
    }

    @Test
    public void obtenerReparacionxIDsSuc_detectaReparaciones() throws SQLException {
        when(mockRs.next()).thenReturn(true);

        when(mockRs.getInt("total")).thenReturn(0);
        assertFalse(new SucursalDAOImpl("Bariloche").obtenerReparacionxIDsSuc(5));

        when(mockRs.getInt("total")).thenReturn(2);
        assertTrue(new SucursalDAOImpl("Bariloche").obtenerReparacionxIDsSuc(5));
    }

    @Test
    public void obtenerIDporNombre_devuelveIdDeLaFila() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("IdSucursal")).thenReturn(5);

        SucursalDAOImpl dao = new SucursalDAOImpl("Bariloche");
        assertEquals(5, dao.obtenerIDporNombre("Norte", 15));
        verify(mockStmt).setString(1, "Norte");
        verify(mockStmt).setInt(2, 15);
    }

    @Test
    public void obtenerIDporNombre_sinResultadosDevuelveCero() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertEquals(0, new SucursalDAOImpl("Bariloche").obtenerIDporNombre("Inexistente", 15));
    }
}