package presentacion.controlador;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Valida la lectura de la contraseña del formulario de usuarios: el placeholder
 * visual de seis puntos ("••••••") NO es una contraseña real. Si se lo trata
 * como vacío, no se crean usuarios con contraseña literal de puntos ni se pisa
 * la contraseña existente en una edición.
 */
public class ControladorUsuariosTest {

    @Test
    public void passwordReal_vacia_devuelveVacio() {
        assertEquals("", ControladorUsuarios.passwordReal(""));
    }

    @Test
    public void passwordReal_null_devuelveVacio() {
        assertEquals("", ControladorUsuarios.passwordReal(null));
    }

    @Test
    public void passwordReal_soloEspacios_devuelveVacio() {
        assertEquals("", ControladorUsuarios.passwordReal("   "));
    }

    @Test
    public void passwordReal_placeholderSePuntos_devuelveVacio() {
        assertEquals("", ControladorUsuarios.passwordReal("••••••"));
    }

    @Test
    public void passwordReal_contraseniaReal_laConserva() {
        assertEquals("abc123", ControladorUsuarios.passwordReal("abc123"));
    }

    @Test
    public void passwordReal_contraseniaConEspaciosLaterales_recorta() {
        assertEquals("abc123", ControladorUsuarios.passwordReal("  abc123  "));
    }

    /** Un único punto u otros bullets no disfrazados no son placeholder. */
    @Test
    public void passwordReal_otroTextoConPuntos_noSeTrataComoPlaceholder() {
        assertEquals("•••••", ControladorUsuarios.passwordReal("•••••"));   // 5 puntos
        assertEquals("*", ControladorUsuarios.passwordReal("*"));
    }
}
