package util;

import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rutas de guardado de reportes, excels y backups segun el modo de operacion
 * elegido con el boton PRUEBA/PRODUCCION de la ventana principal:
 *   - PRUEBA (default): se guarda en ...\Administracion\Sistema Reparsoft Pruebas\Sistema\...
 *   - PRODUCCION: se guarda en ...\Administracion\Sistema\...
 * Las bases de datos MySQL no cambian; solo cambian estas rutas de guardado.
 * El modo elegido se persiste en Preferences (en Windows: HKCU\Software\JavaSoft\Prefs\reparsoft)
 * para que el sistema recuerde el estado al cerrarse y reabrirse.
 */
public class RutasSistema {

    public static final String CARPETA_PRUEBAS = "Sistema Reparsoft Pruebas";

    private static final Pattern PATRON_SISTEMA = Pattern.compile("(?i)Sistema(?=(\\\\|/|$))");

    private static final String PREF_NODO = "reparsoft";
    private static final String PREF_MODO_PRUEBA = "modoPrueba";

    private static boolean modoPrueba = cargarModoGuardado();

    private RutasSistema() {
    }

    public static boolean esModoPrueba() {
        return modoPrueba;
    }

    public static void setModoPrueba(boolean activo) {
        modoPrueba = activo;
        guardarModo(activo);
    }

    private static boolean cargarModoGuardado() {
        try {
            return Preferences.userRoot().node(PREF_NODO).getBoolean(PREF_MODO_PRUEBA, true);
        } catch (Exception e) {
            return true;
        }
    }

    private static void guardarModo(boolean activo) {
        try {
            Preferences.userRoot().node(PREF_NODO).putBoolean(PREF_MODO_PRUEBA, activo);
        } catch (Exception e) {
            // Si no se puede persistir, el modo igualmente queda activo en esta sesion
        }
    }

    /**
     * Devuelve la ruta base de guardado para la ubicacion dada (termina en
     * separador), segun el modo activo.
     */
    public static String getBase(String ubicacion) {
        String base = "F:\\els\\";
        if (ubicacion != null && ubicacion.equalsIgnoreCase("Bariloche")) {
            base += "Bariloche\\";
        }
        base += "Administracion\\Sistema\\";
        return adaptar(base);
    }

    /**
     * En modo PRUEBA inserta la carpeta "Sistema Reparsoft Pruebas" antes de
     * "Sistema" respetando el separador de la ruta. En modo PRODUCCION devuelve
     * la ruta sin cambios.
     */
    public static String adaptar(String ruta) {
        if (!modoPrueba || ruta == null || ruta.isEmpty()) {
            return ruta;
        }
        Matcher m = PATRON_SISTEMA.matcher(ruta);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String sep = m.group(1);
            String sepNueva = sep.isEmpty() ? "\\" : sep;
            m.appendReplacement(sb, Matcher.quoteReplacement(CARPETA_PRUEBAS + sepNueva + "Sistema"));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
