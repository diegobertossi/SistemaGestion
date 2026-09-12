package integration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.Config;

/**
 * Verifica la integridad de una corrida de {@link SimuladorConcurrencia}.
 * Solo lee (SQL + conteo de archivos). No modifica nada.
 *
 * Uso:
 *   java ... integration.VerificadorSim <ciclosEsperados>
 *
 * Sale con código 0 si todo pasa, 1 si hay alguna anomalía.
 */
public class VerificadorSim {

    private static final List<String> fallos = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        int esperados = Integer.parseInt(args[0]);
        String db = Config.get("db.database.bariloche", "");
        if (db == null || !db.toLowerCase().contains("sim")) {
            System.out.println("VERIF|FALLO guardarraíl: db.database.bariloche no es SIM (" + db + ")");
            System.exit(1);
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://" + Config.get("db.host", "localhost") + ":"
                + Config.get("db.port", "3306") + "/" + db + "?" + Config.get("db.options", "");
        try (Connection conn = DriverManager.getConnection(url,
                Config.get("db.user", "root"), Config.get("db.password", "root"));
                Statement st = conn.createStatement()) {

            check("duplicados ELS", scalar(st,
                    "SELECT COUNT(*) FROM (SELECT ELS FROM reparaciones WHERE Falla LIKE 'SIM%' "
                            + "GROUP BY ELS HAVING COUNT(*)>1) t") == 0);
            check("duplicados idCliente", scalar(st,
                    "SELECT COUNT(*) FROM (SELECT idCliente FROM cliente "
                            + "GROUP BY idCliente HAVING COUNT(*)>1) t") == 0);
            check("duplicados IdSucursal", scalar(st,
                    "SELECT COUNT(*) FROM (SELECT IdSucursal FROM sucursal "
                            + "GROUP BY IdSucursal HAVING COUNT(*)>1) t") == 0);
            check("duplicados idRemito", scalar(st,
                    "SELECT COUNT(*) FROM (SELECT idRemito FROM Remitos WHERE idRemito<>0 "
                            + "GROUP BY idRemito HAVING COUNT(*)>1) t") == 0);
            check("duplicados numero-remito-por-ubicacion", scalar(st,
                    "SELECT COUNT(*) FROM (SELECT IdUbicacion, NumeroRemitoSalida FROM Remitos "
                            + "WHERE idRemito<>0 GROUP BY IdUbicacion, NumeroRemitoSalida "
                            + "HAVING COUNT(*)>1) t") == 0);
            check("equipos huérfanos SIM", scalar(st,
                    "SELECT COUNT(*) FROM Equipos e LEFT JOIN reparaciones r ON r.idEquipo=e.IdEquipo "
                            + "WHERE r.ELS IS NULL AND e.Nombre='EquipoSIM'") == 0);
            check("clientes SIM sin sucursal", scalar(st,
                    "SELECT COUNT(*) FROM cliente c LEFT JOIN sucursal s ON s.idCliente=c.idCliente "
                            + "WHERE s.IdSucursal IS NULL AND c.nombre LIKE 'SIM%'") == 0);
            check("reparaciones SIM sin remito", scalar(st,
                    "SELECT COUNT(*) FROM reparaciones WHERE Falla LIKE 'SIM%' "
                            + "AND (idRemito IS NULL OR idRemito=0)") == 0);

            int completos = scalar(st,
                    "SELECT COUNT(*) FROM reparaciones WHERE Falla LIKE 'SIM%' "
                            + "AND EstadoFisico='Enviado'");
            check("ciclos completos (esperados=" + esperados + ", reales=" + completos + ")",
                    completos == esperados);

            int repuestos = scalar(st,
                    "SELECT COUNT(*) FROM reemplazos WHERE ref LIKE 'SIM%'");
            check("repuestos (esperados=" + (2 * esperados) + ", reales=" + repuestos + ")",
                    repuestos == 2 * esperados);

            try (ResultSet rs = st.executeQuery(
                    "SELECT EstadoComercial, COUNT(*) FROM reparaciones WHERE Falla LIKE 'SIM%' "
                            + "GROUP BY EstadoComercial")) {
                System.out.println("VERIF|distribucion comercial:");
                while (rs.next()) {
                    System.out.println("VERIF|  [" + rs.getString(1) + "] = " + rs.getInt(2));
                }
            }

            // Archivos: se verifican las rutas EXACTAS logueadas por los workers
            // (FILE <tipo> <path>), no por glob: los nombres de registro no llevan
            // marca SIM y en las carpetas puede haber archivos reales parecidos.
            java.util.Map<String, List<String>> porTipo = new java.util.HashMap<>();
            File logDir = new File(System.getenv("TEMP") != null
                    ? System.getenv("TEMP") + "\\sim_reparsoft" : "sim_reparsoft");
            File[] logs = logDir.listFiles((d, n) -> n.startsWith("worker") && n.endsWith(".log"));
            if (logs != null) {
                // Los workers escriben en el charset default de Windows (los System.out
                // de Conexion incluyen tildes): leer en windows-1252, no UTF-8.
                java.nio.charset.Charset cs = java.nio.charset.Charset.forName("windows-1252");
                for (File lf : logs) {
                    for (String linea : java.nio.file.Files.readAllLines(lf.toPath(), cs)) {
                        int i = linea.indexOf("FILE ");
                        if (i >= 0) {
                            String[] partes = linea.substring(i + 5).trim().split(" ", 2);
                            if (partes.length == 2) {
                                porTipo.computeIfAbsent(partes[0], k -> new ArrayList<>()).add(partes[1]);
                            }
                        }
                    }
                }
            }
            for (String tipo : new String[] { "registro", "presupuesto", "remito" }) {
                List<String> rutas = porTipo.getOrDefault(tipo, new ArrayList<>());
                int ok = 0;
                for (String ruta : rutas) {
                    if (!ruta.isEmpty() && new File(ruta).exists()) {
                        ok++;
                    }
                }
                check("PDFs " + tipo + " (esperados=" + esperados + ", logueados=" + rutas.size()
                        + ", existentes=" + ok + ")",
                        rutas.size() == esperados && ok == esperados);
            }
        }

        if (fallos.isEmpty()) {
            System.out.println("VERIF|TODO OK");
            System.exit(0);
        }
        System.out.println("VERIF|FALLOS=" + fallos.size());
        System.exit(1);
    }

    private static int scalar(Statement st, String sql) throws Exception {
        try (ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    private static void check(String nombre, boolean ok) {
        System.out.println("VERIF|" + (ok ? "OK " : "FALLO ") + nombre);
        if (!ok) {
            fallos.add(nombre);
        }
    }
}
