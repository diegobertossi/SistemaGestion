package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Cargador centralizado de configuración desde config.properties.
 * Busca el archivo en: 1) classpath, 2) directorio de trabajo, 3) junto al jar.
 */
public class Config {

    private static final Properties PROPS = new Properties();
    private static boolean cargado = false;

    private Config() {}

    private static synchronized void cargar() {
        if (cargado) return;
        String[] rutas = {
            "config.properties",
            "ReparsoftCliente/config.properties",
            System.getProperty("user.dir") + "/config.properties"
        };
        for (String ruta : rutas) {
            try (InputStream is = new FileInputStream(ruta)) {
                PROPS.load(is);
                System.out.println("✅ Config cargada desde: " + ruta);
                cargado = true;
                return;
            } catch (IOException ignored) {}
        }
        System.err.println("⚠️ config.properties no encontrado; usando defaults hardcodeados");
        cargado = true;
    }

    public static String get(String key) {
        if (!cargado) cargar();
        return PROPS.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String v = get(key);
        return (v != null && !v.isEmpty()) ? v : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}