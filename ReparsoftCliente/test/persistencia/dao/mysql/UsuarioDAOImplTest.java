package persistencia.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentCaptor.forClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.ArgumentCaptor;

import dto.UsuarioDTO;
import persistencia.conexion.Conexion;
import util.CryptoUtil;

/**
 * Valida UsuarioDAOImpl (autenticación y resolución de id por nombre)
 * con el singleton Conexion y la cadena JDBC mockeadas.
 */
public class UsuarioDAOImplTest {

    private static final String PASS_SECRETO = "secreto";
    private static final String PASS_ENCRYPTED = CryptoUtil.encrypt(PASS_SECRETO);

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
        reset(mockConexion, mockConn, mockStmt, mockRs);
    }

    private void stubMediaUsuario() throws SQLException {
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("idUsuario")).thenReturn(9);
        when(mockRs.getInt("idRol")).thenReturn(2);
        when(mockRs.getInt("dni")).thenReturn(12345678);
        when(mockRs.getString("nombre")).thenReturn("Juan");
        when(mockRs.getString("apellido")).thenReturn("Perez");
        when(mockRs.getString("telefono")).thenReturn("444");
        when(mockRs.getString("email")).thenReturn("juan@test.com");
        when(mockRs.getString("login")).thenReturn("jperez");
        when(mockRs.getString("pass")).thenReturn(PASS_ENCRYPTED);
    }

    /** readUsuLogin verifica la contraseña desencriptada AES y mapea el usuario autenticado. */
    @Test
    public void readUsuLogin_autenticaUsuario() throws SQLException {
        reset(mockStmt, mockRs);
        stubMediaUsuario();
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");

        UsuarioDTO usuario = dao.readUsuLogin("jperez", "secreto");

        assertEquals(9, usuario.getIdUsuario());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Perez", usuario.getApellido());
        assertEquals("jperez", usuario.getLogin());
        // La contraseña almacenada está encriptada AES (Base64)
        assertTrue(usuario.getPass().startsWith("eyJ") || usuario.getPass().length() > 20); // Base64 típico
        verify(mockStmt).setString(1, "jperez");
    }

    /** Credenciales incorrectas: devuelve null (sin excepción). */
    @Test
    public void readUsuLogin_credencialesInvalidasDevuelvenNull() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("pass")).thenReturn(PASS_ENCRYPTED);
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");
        assertNull(dao.readUsuLogin("jperez", "incorrecta"));
    }

    /** obtenerIDporNombre con nombre vacío no toca la BD y devuelve 1 (admin). */
    @Test
    public void obtenerIDporNombre_vacioDevuelveAdminSinTocarBD() throws SQLException {
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");
        assertEquals(1, dao.obtenerIDporNombre(null));
        assertEquals(1, dao.obtenerIDporNombre(""));
        assertEquals(1, dao.obtenerIDporNombre("   "));
        verify(mockConn, never()).prepareStatement(anyString());
    }

    /** Nombre de una sola palabra no se puede separar: devuelve 1 sin tocar BD. */
    @Test
    public void obtenerIDporNombre_sinApellidoDevuelveAdmin() throws SQLException {
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");
        assertEquals(1, dao.obtenerIDporNombre("Juan"));
        verify(mockConn, never()).prepareStatement(anyString());
    }

    /** "Nombre Apellido" se separa y busca; con fila devuelve el id real. */
    @Test
    public void obtenerIDporNombre_divideNombreYApellido() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("idUsuario")).thenReturn(42);
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");

        assertEquals(42, dao.obtenerIDporNombre("Juan Perez"));

        verify(mockStmt).setString(1, "Juan");
        verify(mockStmt).setString(2, "Perez");
    }

    /** Sin filas coincidentes devuelve el default 1 (comportamiento actual documentado). */
    @Test
    public void obtenerIDporNombre_sinResultadosDevuelveAdmin() throws SQLException {
        when(mockRs.next()).thenReturn(false);
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");
        assertEquals(1, dao.obtenerIDporNombre("Juan Perez"));
    }

    /** insert encripta la contraseña AES y vincula los 9 parámetros. */
    @Test
    public void insert_vinculaTodosLosParametros() throws SQLException {
        UsuarioDTO usuario = new UsuarioDTO(9, 2, 12345678, "Juan", "Perez",
                "444", "juan@test.com", "jperez", "secreto");
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");

        assertTrue(dao.insert(usuario));

        verify(mockStmt).setInt(1, 9);
        verify(mockStmt).setInt(2, 2);
        verify(mockStmt).setInt(3, 12345678);
        verify(mockStmt).setString(4, "Juan");
        verify(mockStmt).setString(5, "Perez");
        verify(mockStmt).setString(6, "444");
        verify(mockStmt).setString(7, "juan@test.com");
        verify(mockStmt).setString(8, "jperez");
        // Verifica que se almacena un hash AES válido (Base64)
        ArgumentCaptor<String> passCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockStmt).setString(eq(9), passCaptor.capture());
        String storedPass = passCaptor.getValue();
        // AES encriptado en Base64 tiene longitud > 20 y no empieza con $2a$
        assertTrue("Password debe ser AES encriptado (Base64)", 
            storedPass != null && storedPass.length() > 20 && !storedPass.startsWith("$2a$"));
    }

    /** Ante excepción SQL, readUsuLogin devuelve null (login no rompe la app). */
    @Test
    public void readUsuLogin_conExcepcionSQLDevuelveNull() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("corte de red"));
        UsuarioDAOImpl dao = new UsuarioDAOImpl("Bariloche");
        assertNull(dao.readUsuLogin("jperez", "secreto"));
    }
}