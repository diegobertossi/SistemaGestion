package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rutas de guardado de reportes, excels y backups segun el modo de operacion
 * elegido en la ventana "UBICACION DEL SISTEMA":
 *   - PRUEBA (default): se guarda en ...\Administracion\Sistema Reparsoft Pruebas\Sistema\...
 *   - PRODUCCION: se guarda en ...\Administracion\Sistema\...
 * Las bases de datos MySQL no cambian; solo cambian estas rutas de guardado.
 */
public class RutasSistema {

    public static final String CARPETA_PRUEBAS = "Sistema Reparsoft Pruebas";

    private static final Pattern PATRON_SISTEMA = Pattern.compile("(?i)Sistema(?=(\\\\|/|$))");

    private static boolean modoPrueba = true;

    private RutasSistema() {
    }

    public static boolean esModoPrueba() {
        return modoPrueba;
    }

    public static void setModoPrueba(boolean activo) {
        modoPrueba = activo;
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
