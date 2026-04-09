package vista.migracion;

/**
 * ConfigMigracion.java
 *
 * POJO inmutable que encapsula toda la configuración necesaria
 * para ejecutar el proceso de migración:
 *   - Ruta del archivo Access (.accdb)
 *   - Rango de ELS a migrar
 *   - Datos de conexión a la BD Staging (reparsoft_staging)
 *   - Datos de conexión a la BD Destino (ordenesbrc / ordenesbsas)
 *
 * Se construye desde VentanaMigracion y se pasa al MigracionController.
 */
public class ConfigMigracion {

    // ── Archivo de origen ───────────────────────────────────────────────────
    private final String rutaAccdb;

    // ── Rango de ELS ────────────────────────────────────────────────────────
    private final int elsDesde;
    private final int elsHasta;

    // ── Conexión Staging ────────────────────────────────────────────────────
    private final String stagingHost;
    private final String stagingPort;
    private final String stagingDB;
    private final String stagingUser;
    private final String stagingPass;

    // ── Conexión Destino ────────────────────────────────────────────────────
    private final String destinoHost;
    private final String destinoPort;
    private final String destinoDB;
    private final String destinoUser;
    private final String destinoPass;

    // ── Constructor ─────────────────────────────────────────────────────────
    public ConfigMigracion(
            String rutaAccdb,
            int elsDesde,
            int elsHasta,
            String stagingHost, String stagingPort, String stagingDB,
            String stagingUser, String stagingPass,
            String destinoHost, String destinoPort, String destinoDB,
            String destinoUser, String destinoPass) {

        this.rutaAccdb    = rutaAccdb;
        this.elsDesde     = elsDesde;
        this.elsHasta     = elsHasta;
        this.stagingHost  = stagingHost;
        this.stagingPort  = stagingPort;
        this.stagingDB    = stagingDB;
        this.stagingUser  = stagingUser;
        this.stagingPass  = stagingPass;
        this.destinoHost  = destinoHost;
        this.destinoPort  = destinoPort;
        this.destinoDB    = destinoDB;
        this.destinoUser  = destinoUser;
        this.destinoPass  = destinoPass;
    }

    // ── URLs de conexión JDBC ───────────────────────────────────────────────

    /**
     * URL para conectarse al archivo .accdb via UCanAccess
     */
    public String getUrlAccess() {
        return "jdbc:ucanaccess://" + rutaAccdb + ";newDatabaseVersion=V2010";
    }

    /**
     * URL JDBC para la BD Staging (MySQL)
     */
    public String getUrlStaging() {
        return "jdbc:mysql://" + stagingHost + ":" + stagingPort + "/" + stagingDB
                + "?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires"
                + "&allowPublicKeyRetrieval=true&characterEncoding=utf8";
    }

    /**
     * URL JDBC para la BD Destino (MySQL)
     */
    public String getUrlDestino() {
        return "jdbc:mysql://" + destinoHost + ":" + destinoPort + "/" + destinoDB
                + "?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires"
                + "&allowPublicKeyRetrieval=true&characterEncoding=utf8";
    }

    // ── Getters ─────────────────────────────────────────────────────────────
    public String getRutaAccdb()   { return rutaAccdb; }
    public int    getElsDesde()    { return elsDesde; }
    public int    getElsHasta()    { return elsHasta; }

    public String getStagingHost() { return stagingHost; }
    public String getStagingPort() { return stagingPort; }
    public String getStagingDB()   { return stagingDB; }
    public String getStagingUser() { return stagingUser; }
    public String getStagingPass() { return stagingPass; }

    public String getDestinoHost() { return destinoHost; }
    public String getDestinoPort() { return destinoPort; }
    public String getDestinoDB()   { return destinoDB; }
    public String getDestinoUser() { return destinoUser; }
    public String getDestinoPass() { return destinoPass; }

    @Override
    public String toString() {
        return "ConfigMigracion{" +
               "accdb='" + rutaAccdb + '\'' +
               ", ELS=[" + elsDesde + "-" + elsHasta + "]" +
               ", staging=" + stagingDB + "@" + stagingHost +
               ", destino=" + destinoDB + "@" + destinoHost +
               '}';
    }
}
