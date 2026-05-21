package persistencia.dao.mysql;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogDAO {

    private static final Logger LOGGER = Logger.getLogger(LogDAO.class.getName());

    static {
        LOGGER.setLevel(Level.INFO);
    }

    public static void error(String mensaje, Throwable e) {
        LOGGER.severe(mensaje + ": " + e.getMessage());
    }

    public static void info(String mensaje) {
        LOGGER.info(mensaje);
    }

    public static void warning(String mensaje) {
        LOGGER.warning(mensaje);
    }
}
