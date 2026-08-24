package util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Rutina de normalizacion de "mojibake" historico.
 *
 * Sintoma: texto como "Climatizaci├│n" o "ÔÇ£...ÔÇØ". Eso ocurre cuando bytes
 * UTF-8 originales fueron leidos como CP850 y guardados nuevamente en UTF-8
 * (doble codificacion). La reparacion inversa es segura cuando el texto actual
 * es 100% representable en CP850 y al decodificarlo de vuelta como UTF-8 no
 * produce caracteres de reemplazo ni nuevos artefactos:
 *
 *   sano = new String(danado.getBytes(CP850), UTF_8)
 *
 * Uso:
 *   dry-run (no escribe nada, solo reporta):
 *     java util.NormalizadorMojibake jdbc:mysql://host:3306/base user pass
 *   apply (aplica los UPDATE):
 *     java util.NormalizadorMojibake jdbc:mysql://host:3306/base user pass --apply
 */
public class NormalizadorMojibake {

    private static final Charset CP850 = Charset.forName("Cp850");

    // Sintomas de mojibake: caracteres de dibujo de cajas (tipicos cuando UTF-8
    // se leyo como CP850) o secuencias frecuentes de doble-codificacion.
    private static final Pattern SINTOMAS = Pattern.compile(
            "[\u2500-\u257F]|" +          // box drawing ├ │ ┬ ┤ ┌ ┐ └ ┘ ┼ ─
            "\u00D4\u00C7|" +             // "ÔÇ" (prefijo de comillas tipograficas)
            "\u00E2\u20AC|" +             // "â€" (doble-codificacion estilo cp1252)
            "\u00C3[\u0081\u0089\u008D\u0091\u0093\u009A\u00A0\u00A1\u00A3\u00A9\u00AD\u00B1\u00B3\u00BA]"
            // "Ã" + letra: caso tipico "Ã±" "Ã©" "Ã³"...
    );

    private static final Pattern BASURA_POST_REPARACION = Pattern.compile(
            "[\u2500-\u257F\uFFFD]|\u00C3[\u0081\u0089\u0091\u0093\u00AD\u00B3\u00BA]"
    );

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Uso: NormalizadorMojibake <jdbc-url> <user> <pass> [--apply]");
            System.out.println("  Sin --apply solo REPORTA (dry-run).");
            return;
        }
        String url = args[0];
        String user = args[1];
        String pass = args[2];
        boolean apply = args.length > 3 && "--apply".equals(args[3]);

        System.out.println((apply ? "MODO APPLY" : "MODO DRY-RUN") + " sobre " + url);

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            Statement st = conn.createStatement();
            st.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");

            String base = conn.getCatalog();
            List<String> tablas = new ArrayList<>();
            try (ResultSet rs = st.executeQuery("SHOW TABLES")) {
                while (rs.next()) tablas.add(rs.getString(1));
            }
            System.out.println("Tablas encontradas: " + tablas.size() + " en " + base);

            int totalCeldasDanadas = 0;
            int totalUpdates = 0;

            for (String tabla : tablas) {
                List<ColumnaTexto> columnas = columnasDeTexto(conn, tabla);
                if (columnas.isEmpty()) continue;

                List<String> pkCols = columnasPk(conn, tabla);

                for (ColumnaTexto col : columnas) {
                    String select = "SELECT " + selectPk(pkCols) + "`" + col.nombre + "` FROM `" + tabla + "`";
                    List<Cambio> cambios = new ArrayList<>();

                    try (PreparedStatement ps = conn.prepareStatement(select);
                         ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            String valor = rs.getString(col.nombre);
                            String reparado = reparar(valor);
                            if (reparado != null && !reparado.equals(valor)) {
                                cambios.add(new Cambio(pkDeFila(rs, pkCols), valor, reparado));
                            }
                        }
                    }

                    if (!cambios.isEmpty()) {
                        totalCeldasDanadas += cambios.size();
                        System.out.println("  " + tabla + "." + col.nombre + ": " + cambios.size() + " valores danados");
                        int muestras = Math.min(3, cambios.size());
                        for (int i = 0; i < muestras; i++) {
                            Cambio c = cambios.get(i);
                            System.out.println("     [" + c.pkTexto + "] " + acortar(c.antes) + "  ==>  " + acortar(c.despues));
                        }
                        if (cambios.size() > muestras) {
                            System.out.println("     ... y " + (cambios.size() - muestras) + " mas");
                        }

                        if (apply) {
                            if (pkCols.isEmpty()) {
                                System.out.println("     (sin PK unica: se omite UPDATE por seguridad)");
                            } else {
                                String update = "UPDATE `" + tabla + "` SET `" + col.nombre + "` = ? WHERE "
                                        + wherePk(pkCols);
                                try (PreparedStatement ups = conn.prepareStatement(update)) {
                                    for (Cambio c : cambios) {
                                        ups.setString(1, c.despues);
                                        Object[] pk = c.pkValores;
                                        for (int k = 0; k < pk.length; k++) ups.setObject(2 + k, pk[k]);
                                        ups.executeUpdate();
                                        totalUpdates++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            System.out.println();
            System.out.println("RESUMEN " + base + ": " + totalCeldasDanadas + " celdas danadas"
                    + (apply ? " | aplicadas: " + totalUpdates : " | (dry-run, nada escrito)"));
        }
    }

    /**
     * Devuelve el texto reparado si estaba danado; el original si no hay dano
     * o si la reparacion no es segura.
     */
    static String reparar(String valor) {
        if (valor == null || valor.isEmpty()) return valor;
        if (!SINTOMAS.matcher(valor).find()) return valor;

        // Solo proceder si todo el texto es representable en CP850
        // (si hay caracteres que CP850 no conoce, no era un mojibake puro).
        Charset cp850 = CP850;
        if (!cp850.newEncoder().canEncode(valor)) return valor;

        String reparado = new String(valor.getBytes(cp850), StandardCharsets.UTF_8);

        // La reparacion no debe producir reemplazos ni persistir sintomas.
        if (BASURA_POST_REPARACION.matcher(reparado).find()) return valor;

        return reparado;
    }

    // ---------- infraestructura ----------

    private static class ColumnaTexto {
        final String nombre;
        ColumnaTexto(String n) { this.nombre = n; }
    }

    private static class Cambio {
        final Object[] pkValores;
        final String pkTexto;
        final String antes;
        final String despues;
        Cambio(Object[] pk, String antes, String despues) {
            this.pkValores = pk;
            this.antes = antes;
            this.despues = despues;
            StringBuilder t = new StringBuilder();
            for (int i = 0; i < pk.length; i++) {
                if (i > 0) t.append(",");
                t.append(pk[i]);
            }
            this.pkTexto = t.toString();
        }
    }

    private static List<ColumnaTexto> columnasDeTexto(Connection conn, String tabla) throws SQLException {
        List<ColumnaTexto> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? "
                        + "AND DATA_TYPE IN ('char','varchar','tinytext','text','mediumtext','longtext')")) {
            ps.setString(1, conn.getCatalog());
            ps.setString(2, tabla);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(new ColumnaTexto(rs.getString(1)));
            }
        }
        return lista;
    }

    private static List<String> columnasPk(Connection conn, String tabla) throws SQLException {
        List<String> pk = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT COLUMN_NAME FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? "
                        + "AND INDEX_NAME = 'PRIMARY' ORDER BY SEQ_IN_INDEX")) {
            ps.setString(1, conn.getCatalog());
            ps.setString(2, tabla);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) pk.add(rs.getString(1));
            }
        }
        return pk;
    }

    private static String selectPk(List<String> pk) {
        StringBuilder sb = new StringBuilder();
        for (String c : pk) sb.append("`").append(c).append("`, ");
        return sb.toString();
    }

    private static String wherePk(List<String> pk) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pk.size(); i++) {
            if (i > 0) sb.append(" AND ");
            sb.append("`").append(pk.get(i)).append("` = ?");
        }
        return sb.toString();
    }

    private static Object[] pkDeFila(ResultSet rs, List<String> pkCols) throws SQLException {
        Object[] pk = new Object[pkCols.size()];
        for (int i = 0; i < pkCols.size(); i++) pk[i] = rs.getObject(pkCols.get(i));
        return pk;
    }

    private static String acortar(String s) {
        if (s == null) return "null";
        String limpio = s.replace('\n', ' ').replace('\r', ' ');
        return limpio.length() > 60 ? limpio.substring(0, 60) + "..." : limpio;
    }
}
