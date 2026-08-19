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
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import dto.RemitoDTO;
import persistencia.conexion.Conexion;

/**
 * Valida RemitoDAOImpl (numeración, inserción y listados de remitos)
 * mockeando la cadena JDBC.
 */
public class RemitoDAOImplTest {

    private static final String INSERT_SQL = "INSERT INTO Remitos(idRemito,NumeroRemitoSalida,IdUbicacion) VALUES(?,?,?)";
    private static final String DELETE_SQL = "DELETE FROM Remitos WHERE idRemito = ?";

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
    public void insert_vinculaIdRemitoNumeroYUbicacion() throws SQLException {
        RemitoDTO remito = new RemitoDTO(3, 25, 42);

        boolean resultado = new RemitoDAOImpl("Bariloche").insert(remito);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(INSERT_SQL));
        verify(mockStmt).setInt(1, 42);
        verify(mockStmt).setInt(2, 25);
        verify(mockStmt).setInt(3, 3);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void insert_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new RemitoDAOImpl("Bariloche").insert(new RemitoDTO(3, 25, 42)));
    }

    @Test
    public void delete_vinculaIdRemitoYDevuelveTrue() throws SQLException {
        boolean resultado = new RemitoDAOImpl("Bariloche").delete(42);

        assertTrue(resultado);
        verify(mockConn).prepareStatement(eq(DELETE_SQL));
        verify(mockStmt).setInt(1, 42);
        verify(mockStmt).executeUpdate();
    }

    @Test
    public void delete_conExcepcionSQLDevuelveFalse() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("error de red"));
        assertFalse(new RemitoDAOImpl("Bariloche").delete(42));
    }

    @Test
    public void obtenerNumeroRemito_devuelveElMaximoPorCodigo() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(25);

        RemitoDAOImpl dao = new RemitoDAOImpl("Bariloche");
        assertEquals(25, dao.obtenerNumeroRemito(7));
        verify(mockStmt).setInt(1, 7);
    }

    @Test
    public void obtenerNumeroRemito_sinRemitosDevuelveCero() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertEquals(0, new RemitoDAOImpl("Bariloche").obtenerNumeroRemito(7));
    }

    @Test
    public void obtenerIDRemito_devuelveElMaximoId() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(100);
        assertEquals(100, new RemitoDAOImpl("Bariloche").obtenerIDRemito());
    }

    @Test
    public void idRemitoXubicacionNumero_devuelveElIdCoincidente() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("idRemito")).thenReturn(42);

        RemitoDAOImpl dao = new RemitoDAOImpl("Bariloche");
        assertEquals(42, dao.idRemitoXubicacionNumero(3, 25));
        verify(mockStmt).setInt(1, 3);
        verify(mockStmt).setInt(2, 25);
    }

    @Test
    public void idRemitoXubicacionNumero_sinResultadosDevuelveCero() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        assertEquals(0, new RemitoDAOImpl("Bariloche").idRemitoXubicacionNumero(3, 25));
    }

    /** Códigos especiales (2/5/6/7) se formatean con ceros a la izquierda. */
    @Test
    public void listarUbicacion_formateaCodigosEspecialesConCeros() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, true, false);
        when(mockRs.getString("Codigo")).thenReturn("2", "3", "7");
        when(mockRs.getInt("Codigo")).thenReturn(2, 3, 7);
        when(mockRs.getString("Ubicacion")).thenReturn("Bariloche", "Buenos Aires", "Neuquen");

        JComboBox<Object> box = new JComboBox<>();
        new RemitoDAOImpl("Bariloche").ListarUbicacion(box);

        DefaultComboBoxModel<Object> model = (DefaultComboBoxModel<Object>) box.getModel();
        assertEquals(4, model.getSize());
        assertEquals("--Seleccionar Ubicación--", model.getElementAt(0));
        assertEquals("0002 - Bariloche", model.getElementAt(1));
        assertEquals("3 - Buenos Aires", model.getElementAt(2));
        assertEquals("0007 - Neuquen", model.getElementAt(3));
    }

    /** Codigos nulos se omiten del combo. */
    @Test
    public void listarUbicacion_omiteCodigosNull() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getString("Codigo")).thenReturn("3", null);
        when(mockRs.getInt("Codigo")).thenReturn(3, 5);

        JComboBox<Object> box = new JComboBox<>();
        new RemitoDAOImpl("Bariloche").ListarUbicacion(box);

        DefaultComboBoxModel<Object> model = (DefaultComboBoxModel<Object>) box.getModel();
        assertEquals(2, model.getSize());
        assertEquals("--Seleccionar Ubicación--", model.getElementAt(0));
        assertEquals("3 - null", model.getElementAt(1));
    }

    /** La descripción del remito se conserva tal cual en el DTO. */
    @Test
    public void remitoCompleto_conservaDescripcion() {
        RemitoDTO remito = new RemitoDTO(3, 7, 42, 25, Arrays.asList("TV 32", "Mouse"), "Cliente SA",
                "CONFORMADO", 2, "30-11111111-1", "Calle 1");

        assertEquals(Arrays.asList("TV 32", "Mouse"), remito.getDescripcion());
        assertEquals(2, remito.getCantBultos());
        assertEquals("Calle 1", remito.getDomicilio());
    }
}