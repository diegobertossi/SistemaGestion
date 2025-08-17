package persistencia.conexion;

import java.io.FileInputStream;
import java.io.IOException;
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
         //CONFIGURACIÓN FIJA PARA LOCALHOST - Ignora archivo externo por ahora
        props.setProperty("db.host", "localhost");
        props.setProperty("db.port", "3306");
        props.setProperty("db.user", "root");
        props.setProperty("db.password", "root");
        props.setProperty("db.options", "serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10");
        
        System.out.println("ℹ️ Usando configuración local fija (localhost) para compatibilidad.");
        System.out.println("📁 Para usar archivo externo, modificar el código en el bloque static.");
        
        
        // DESCOMENTA ESTE BLOQUE SI QUIERES USAR ARCHIVO EXTERNO:
//        try {
//            FileInputStream fis = new FileInputStream("resources/db_config.properties");
//            Properties fileProps = new Properties();
//            fileProps.load(fis);
//            fis.close();
//            
//            // Verificar que sea configuración localhost
//            String hostFromFile = fileProps.getProperty("db.host", "localhost");
//            if (hostFromFile.equals("localhost")) {
//                props = fileProps; // Usar configuración del archivo
//                System.out.println("✅ Configuración local cargada desde archivo.");
//            } else {
//                System.out.println("⚠️ Archivo apunta a servidor remoto. Usando localhost por seguridad.");
//            }
//            
//        } catch (IOException e) {
//            System.out.println("ℹ️ No se encontró archivo de configuración. Usando localhost por defecto.");
//        }
       
    }

    @SuppressWarnings("deprecation")
    private Conexion(String ubicacion) {
        this.ubicacionActual = ubicacion;
        
        try {
            String controlador = "com.mysql.cj.jdbc.Driver";
            Class.forName(controlador).newInstance();
        } catch (Exception e) {
            System.err.println("❌ Error al cargar el controlador JDBC de MySQL.");
            JOptionPane.showMessageDialog(null, "Error al cargar el controlador: " + e.getMessage());
        }

        establecerConexion(ubicacion);
    }

    private void establecerConexion(String ubicacion) {
        String nombreBase = "";
        
        // Determinar el nombre de la base de datos según la ubicación
        if (ubicacion.compareToIgnoreCase("Bariloche") == 0) {
            nombreBase = "ordenesbrc";
        } else if (ubicacion.compareToIgnoreCase("Buenos Aires") == 0) {
            nombreBase = "ordenesbsas";
        } else {
            // Si no es una ubicación conocida, usar la ubicación como nombre de BD
            nombreBase = ubicacion.toLowerCase().replaceAll("\\s+", "");
        }

        try {
            String host = props.getProperty("db.host", "localhost");
            String port = props.getProperty("db.port", "3306");
            String user = props.getProperty("db.user", "root");
            String password = props.getProperty("db.password", "root");
            String options = props.getProperty("db.options", "serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");

            String url = String.format("jdbc:mysql://%s:%s/%s?%s", host, port, nombreBase, options);

            conexion = DriverManager.getConnection(url, user, password);
            
//            System.out.println("✅ Conexión exitosa a la base de datos: " + nombreBase);
//            System.out.println("📍 Ubicación configurada: " + ubicacion);
            
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos.");
            System.err.println("Detalles del error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error al realizar la conexión\n" + e.toString() + 
                "\n\n------------\nEsta ventana se cerrará....");
        }
    }

    /**
     * Obtiene la instancia singleton de la conexión para la ubicación especificada.
     * Si ya existe una instancia para otra ubicación, la cierra y crea una nueva.
     * 
     * @param ubicacion La ubicación para determinar qué base de datos usar
     * @return La instancia de Conexion
     */
    public static Conexion getConexion(String ubicacion) {
        // Si hay una instancia existente pero es para otra ubicación, cerrarla
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
     * Obtiene la conexión SQL activa.
     * Verifica si la conexión sigue siendo válida antes de devolverla.
     * 
     * @return Connection activa o null si hay error
     */
    public Connection getSQLConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                return conexion;
            } else if (conexion != null && conexion.isClosed()) {
                // Si la conexión se cerró, intentar reconectar
//                System.out.println("🔄 Reconectando a la base de datos...");
                establecerConexion(ubicacionActual);
                return conexion;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar el estado de la conexión: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * Cierra la conexión y resetea la instancia singleton.
     */
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
//                System.out.println("🔌 Conexión cerrada correctamente para: " + ubicacionActual);
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
        instancia = null;
    }

    /**
     * Obtiene la ubicación actual configurada.
     * 
     * @return La ubicación actual
     */
    public String getUbicacionActual() {
        return ubicacionActual;
    }

    /**
     * Verifica si la conexión está activa.
     * 
     * @return true si la conexión está activa, false en caso contrario
     */
    public boolean isConexionActiva() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}