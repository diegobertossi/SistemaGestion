package vista.migracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Valida la construcción de URLs JDBC de ConfigMigracion (lógica pura, sin BD).
 */
public class ConfigMigracionTest {

    private ConfigMigracion crearConfig() {
        return new ConfigMigracion(
                "C:/datos/els.accdb",
                1, 977,
                "localhost", "3306", "reparsoft_staging", "root", "pass1",
                "localhost", "3306", "ordenesbrc", "root", "pass2");
    }

    /** La URL de Access usa UCanAccess con la versión de BD forzada. */
    @Test
    public void getUrlAccess_construyeUrlUCanAccess() {
        assertEquals("jdbc:ucanaccess://C:/datos/els.accdb;newDatabaseVersion=V2010",
                crearConfig().getUrlAccess());
    }

    /** La URL de staging apunta a la BD intermedia con los parámetros de conexión. */
    @Test
    public void getUrlStaging_construyeUrlMysql() {
        String url = crearConfig().getUrlStaging();
        assertTrue(url.startsWith("jdbc:mysql://localhost:3306/reparsoft_staging"));
        assertTrue(url.contains("useSSL=false"));
        assertTrue(url.contains("serverTimezone=America/Argentina/Buenos_Aires"));
        assertTrue(url.contains("allowPublicKeyRetrieval=true"));
        assertTrue(url.contains("characterEncoding=utf8"));
    }

    /** La URL de destino apunta a la BD final de reparaciones. */
    @Test
    public void getUrlDestino_construyeUrlMysql() {
        String url = crearConfig().getUrlDestino();
        assertTrue(url.startsWith("jdbc:mysql://localhost:3306/ordenesbrc"));
    }

    /** toString NO debe exponer contraseñas (seguridad). */
    @Test
    public void toString_noExponeContrasenas() {
        String descripcion = crearConfig().toString();
        assertFalse(descripcion.contains("pass1"));
        assertFalse(descripcion.contains("pass2"));
        assertFalse(descripcion.contains("root"));
        assertTrue(descripcion.contains("reparsoft_staging"));
    }

    /** Los getters devuelven la configuración cargada. */
    @Test
    public void getters_devuelvenConfiguracion() {
        ConfigMigracion config = crearConfig();
        assertEquals("C:/datos/els.accdb", config.getRutaAccdb());
        assertEquals(1, config.getElsDesde());
        assertEquals(977, config.getElsHasta());
        assertEquals("root", config.getStagingUser());
        assertEquals("pass1", config.getStagingPass());
        assertEquals("ordenesbrc", config.getDestinoDB());
        assertEquals("pass2", config.getDestinoPass());
    }
}
