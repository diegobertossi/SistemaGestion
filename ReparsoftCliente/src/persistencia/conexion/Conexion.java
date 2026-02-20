package persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Conexion {

    private static Conexion instancia;
    private Connection conexion;
    private static Properties props = new Properties();
    private String ubicacionActual;

    static {
        // CONFIGURACIÓN FIJA PARA LOCALHOST (MySQL 8.4 LTS)
        props.setProperty("db.host", "localhost");
        props.setProperty("db.port", "3306"); // ⚠️ Puerto MySQL 8.4 en paralelo
        props.setProperty("db.user", "root");
        props.setProperty("db.password", "root");

        // Opciones compatibles con MySQL Connector/J 8.4
        props.setProperty(
        	    "db.options",
        	    "useUnicode=true" +
        	    "&characterEncoding=UTF-8" +
        	    "&connectionCollation=utf8mb4_unicode_ci" +
        	    "&serverTimezone=UTC" +
        	    "&useSSL=false" +
        	    "&allowPublicKeyRetrieval=true"
        	);

        System.out.println("ℹ️ Usando configuración local fija para MySQL 8.4 LTS.");
    }
    
    // VERIFICAR PARA MYSQL 5.7 32 BITS Y 8.4 64 BITS
    
//    static {
//        props.setProperty("db.host", "localhost");
//        props.setProperty("db.port", "3306"); // 🔥 UNIFICADO
//        props.setProperty("db.user", "root");
//        props.setProperty("db.password", "root");
//
//        props.setProperty(
//            "db.options",
//            "useUnicode=true" +
//            "&characterEncoding=UTF-8" +
//            "&serverTimezone=UTC" +
//            "&useSSL=false" +
//            "&allowPublicKeyRetrieval=true"
//        );
//
//        System.out.println("ℹ️ Usando configuración compatible MySQL 5.7 / 8.4.");
//    }

//    try (java.sql.Statement stmt = conexion.createStatement()) {
//        stmt.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
//        stmt.execute("SET CHARACTER SET utf8mb4");
//    }

    

    private Conexion(String ubicacion) {
        this.ubicacionActual = ubicacion;

        try {
            // Driver MySQL 8.x
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver JDBC MySQL 8.x");
            JOptionPane.showMessageDialog(
                null,
                "No se pudo cargar el driver JDBC MySQL.\n" + e.getMessage(),
                "Error de Driver",
                JOptionPane.ERROR_MESSAGE
            );
        }

        establecerConexion(ubicacion);
    }

    private void establecerConexion(String ubicacion) {
        String nombreBase;

        if (ubicacion.equalsIgnoreCase("Bariloche")) {
            nombreBase = "ordenesbrc";
        } else if (ubicacion.equalsIgnoreCase("Buenos Aires")) {
            nombreBase = "ordenesbsas";
        } else {
            nombreBase = ubicacion.toLowerCase().replaceAll("\\s+", "");
        }

        try {
            String host = props.getProperty("db.host");
            String port = props.getProperty("db.port");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            String options = props.getProperty("db.options");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + nombreBase + "?" + options;

            conexion = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con MySQL 8.4");
            System.err.println("Detalles: " + e.getMessage());

            JOptionPane.showMessageDialog(
                null,
                "Error al conectar con la base de datos:\n\n" + e.getMessage() +
                "\n\nLa aplicación se cerrará.",
                "Error de Conexión",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Devuelve la instancia singleton según la ubicación.
     */
    public static Conexion getConexion(String ubicacion) {
        if (instancia != null && !instancia.ubicacionActual.equalsIgnoreCase(ubicacion)) {
            instancia.cerrarConexion();
            instancia = null;
        }

        if (instancia == null) {
            instancia = new Conexion(ubicacion);
        }

        return instancia;
    }

    /**
     * Devuelve la conexión SQL activa.
     */
    public Connection getSQLConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                return conexion;
            } else if (conexion != null && conexion.isClosed()) {
                establecerConexion(ubicacionActual);
                return conexion;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar conexión: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * Cierra la conexión actual.
     */
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
            }
        }
        instancia = null;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public boolean isConexionActiva() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
