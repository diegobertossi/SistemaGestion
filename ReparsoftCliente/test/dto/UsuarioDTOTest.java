package dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Valida el mapeo idRol -> nombre de rol y los constructores de UsuarioDTO.
 */
public class UsuarioDTOTest {

    private final UsuarioDTO dto = new UsuarioDTO(1, 2, 12345678, "Juan", "Perez",
            "4444444", "juan@test.com", "jperez", "secreto");

    /** Cada idRol conocido tiene su nombre de rol. */
    @Test
    public void getNombreRol_mapeaLosCuatroRoles() {
        assertEquals("Administrador Programador", dto.getNombreRol(1));
        assertEquals("Tecnico", dto.getNombreRol(2));
        assertEquals("Contable", dto.getNombreRol(3));
        assertEquals("Tecnico Contable", dto.getNombreRol(4));
    }

    /** Un idRol desconocido devuelve "Desconocido" (incluidos negativos). */
    @Test
    public void getNombreRol_idDesconocidoDevuelveDesconocido() {
        assertEquals("Desconocido", dto.getNombreRol(0));
        assertEquals("Desconocido", dto.getNombreRol(99));
        assertEquals("Desconocido", dto.getNombreRol(-1));
    }

    /** El constructor completo guarda login y pass. */
    @Test
    public void constructorCompleto_guardaLoginYPass() {
        assertEquals("jperez", dto.getLogin());
        assertEquals("secreto", dto.getPass());
        assertEquals(1, dto.getIdUsuario());
        assertEquals(2, dto.getIdRol());
        assertEquals(Integer.valueOf(12345678), dto.getDni());
    }

    /** El constructor sin login/pass los deja nulos. */
    @Test
    public void constructorSinCredenciales_dejaLoginYPassNulos() {
        UsuarioDTO sinCred = new UsuarioDTO(5, 1, 111, "Ana", "Gomez", "555", "ana@test.com");
        assertNull(sinCred.getLogin());
        assertNull(sinCred.getPass());
        assertEquals("Ana", sinCred.getNombre());
    }

    /** toString combina nombre y apellido. */
    @Test
    public void toString_combinaNombreYApellido() {
        assertEquals("Juan Perez", dto.toString());
    }
}
