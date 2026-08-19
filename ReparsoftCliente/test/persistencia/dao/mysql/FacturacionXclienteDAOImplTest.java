package persistencia.dao.mysql;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
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

import dto.FacturacionXclienteDTO;
import persistencia.conexion.Conexion;

/**
 * Valida FacturacionXclienteDAOImp.readAll(int anio) sin base de datos
 * (Conexion.getConexion static mockeado + cadena JDBC mocks).
 */
public class FacturacionXclienteDAOImplTest {

    private Conexion conexion;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private MockedStatic<Conexion> mockedStatic;
    private FacturacionXclienteDAOImp dao;

    @Before
    public void setUp() throws SQLException {
        conexion = mock(Conexion.class);
        conn = mock(Connection.class);
        stmt = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        mockedStatic = mockStatic(Conexion.class);
        mockedStatic.when(() -> Conexion.getConexion("Bariloche")).thenReturn(conexion);
        when(conexion.getSQLConexion()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);

        dao = new FacturacionXclienteDAOImp("Bariloche");
    }

    @After
    public void tearDown() {
        mockedStatic.close();
    }

    @Test
    public void readAll_mapeaFilasPorIndice() throws SQLException {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt(1)).thenReturn(1, 2);
        when(rs.getString(2)).thenReturn("Cliente A", "Cliente B");
        when(rs.getDouble(3)).thenReturn(12345.67, 500.0);

        List<FacturacionXclienteDTO> lista = dao.readAll(2025);

        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getIdCliente());
        assertEquals("Cliente A", lista.get(0).getNombreCliente());
        assertEquals(12345.67, lista.get(0).getFacturacion(), 0.001);
        assertEquals(2, lista.get(1).getIdCliente());
        assertEquals("Cliente B", lista.get(1).getNombreCliente());
        assertEquals(500.0, lista.get(1).getFacturacion(), 0.001);
    }

    @Test
    public void readAll_vinculaElAnioAlParametro() throws SQLException {
        when(rs.next()).thenReturn(false);

        dao.readAll(2025);

        verify(stmt).setInt(eq(1), eq(2025));
    }

    @Test
    public void readAll_sinResultados_devuelveListaVacia() throws SQLException {
        when(rs.next()).thenReturn(false);

        List<FacturacionXclienteDTO> lista = dao.readAll(2026);

        assertTrue(lista.isEmpty());
    }

    @Test
    public void readAll_anteExcepcionSQL_devuelveListaVacia() throws SQLException {
        when(conn.prepareStatement(anyString())).thenThrow(new SQLException("sin conexion"));

        List<FacturacionXclienteDTO> lista = dao.readAll(2025);

        assertTrue(lista.isEmpty());
    }

    @Test
    public void readAll_noUsaLosFlagsBooleanosDeLaFila() throws SQLException {
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt(1)).thenReturn(7);
        when(rs.getString(2)).thenReturn("Cliente C");
        when(rs.getDouble(3)).thenReturn(99.0);

        dao.readAll(2025);

        verify(rs).getInt(eq(1));
        verify(rs).getString(eq(2));
        verify(rs).getDouble(eq(3));
        assertFalse(rs.wasNull());
    }
}