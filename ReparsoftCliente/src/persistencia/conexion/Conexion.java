package persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Conexion {

    private static volatile Conexion instancia;
    private Connection conexion;
    private static Properties props = new Properties();
    private String ubicacionActual;
    private boolean esBaseAntigua;

    private static boolean modoAntiguaGlobal = false;

    static {
        props.setProperty("db.host", "localhost");
        props.setProperty("db.port", "3306");
        props.setProperty("db.user", "root");
        props.setProperty("db.password", "root");

        props.setProperty(
            "db.options",
            "useUnicode=true" +
            "&characterEncoding=UTF-8" +
            "&connectionCollation=utf8mb4_unicode_ci" +
            "&serverTimezone=UTC" +
            "&useSSL=false" +
            "&allowPublicKeyRetrieval=true" +
            "&connectTimeout=5000" +
            "&socketTimeout=120000"
        );

        System.out.println("ℹ️ Usando configuración local fija para MySQL 8.4 LTS.");
    }

    // ── Constructor privado ───────────────────────────────────────────────────
    // Class.forName ya NO está aquí: lo precarga Main.java al arranque.
    private Conexion(String ubicacion, boolean esAntigua) {
        this.ubicacionActual = ubicacion;
        this.esBaseAntigua   = esAntigua;
        establecerConexion(ubicacion, esAntigua);
    }

    // ── Método estático para precargar el driver desde cualquier punto ────────
    // Main.java lo llama al inicio. Queda aquí como utilidad por si se necesita
    // en tests u otros contextos sin pasar por Main.
    public static void precargarDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver JDBC precargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver JDBC MySQL 8.x");
            JOptionPane.showMessageDialog(null,
                "No se pudo cargar el driver JDBC MySQL.\n" + e.getMessage(),
                "Error de Driver", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Conexión a la base ────────────────────────────────────────────────────
    private void establecerConexion(String ubicacion, boolean esAntigua) {
        String nombreBase;

        if (ubicacion.equalsIgnoreCase("Bariloche")) {
            nombreBase = esAntigua ? "ordenesbrcantiguas" : "ordenesbrc";
        } else if (ubicacion.equalsIgnoreCase("Buenos Aires")) {
            nombreBase = esAntigua ? "ordenesbsasantiguas" : "ordenesbsas";
        } else {
            nombreBase = ubicacion.toLowerCase().replaceAll("\\s+", "");
            if (esAntigua) nombreBase += "antiguas";
        }

        try {
            String host     = props.getProperty("db.host");
            String port     = props.getProperty("db.port");
            String user     = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            String options  = props.getProperty("db.options");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + nombreBase + "?" + options;

            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conectado correctamente a: " + nombreBase +
                             (esAntigua ? " (BASE ANTIGUA)" : " (BASE NORMAL)"));

        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con MySQL 8.4");
            System.err.println("Base intentada: " + nombreBase);
            System.err.println("Detalles: " + e.getMessage());

            JOptionPane.showMessageDialog(null,
                "Error al conectar con la base de datos:\n\n" +
                "Base: " + nombreBase + "\nError: " + e.getMessage(),
                "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Singleton ─────────────────────────────────────────────────────────────
    public static Conexion getConexion(String ubicacion) {
        return getConexion(ubicacion, modoAntiguaGlobal);
    }

    public static synchronized Conexion getConexion(String ubicacion, boolean esAntigua) {
        if (instancia != null &&
            (!instancia.ubicacionActual.equalsIgnoreCase(ubicacion) ||
              instancia.esBaseAntigua != esAntigua)) {

            instancia.cerrarConexion();
            instancia = null;
        }

        if (instancia == null) {
            instancia = new Conexion(ubicacion, esAntigua);
        }

        modoAntiguaGlobal = esAntigua;
        return instancia;
    }

    // ── Utilidades estáticas ──────────────────────────────────────────────────
    public static String getUbicacionActualStatic() {
        return (instancia != null) ? instancia.ubicacionActual : null;
    }

    public static boolean isModoAntigua() {
        return modoAntiguaGlobal;
    }

    // ── Instancia ─────────────────────────────────────────────────────────────
    public synchronized Connection getSQLConexion() {
        try {
            if (conexion != null && !conexion.isClosed() && conexion.isValid(2)) {
                return conexion;
            }
            // Conexión cerrada o muerta: reconectar
            establecerConexion(ubicacionActual, esBaseAntigua);
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar/reconectar: " + e.getMessage());
        }
        return conexion;
    }

    public synchronized void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("🔌 Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
            }
        }
        instancia = null;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public boolean isBaseAntigua() {
        return esBaseAntigua;
    }

    public boolean isConexionActiva() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}