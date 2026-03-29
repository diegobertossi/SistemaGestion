package VistaPropias;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import persistencia.conexion.Conexion;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para actualizar la columna NroFactura en la tabla reparaciones
 * a partir de un archivo Excel generado por EscanerFacturasPDF.
 *
 * ACTUALIZACIÓN: La columna ELS del Excel puede contener uno o varios
 * números de ELS separados por coma (ej: "29,30" o "32,43,44,45,46").
 * En ese caso, la misma factura se asigna a todos los ELS listados.
 *
 * Estructura actual del Excel (columnas):
 *   0: N°
 *   1: Nombre Archivo
 *   2: Punto de Venta
 *   3: Número Comprobante
 *   4: N° Factura Completo
 *   5: CUIT Emisor
 *   6: Razón Social Emisor
 *   7: CUIT Destinatario
 *   8: Razón Social Destinatario
 *   9: Fecha Emisión
 *  10: ELS  (puede ser "8" o "29,30" o "32,43,44,45")
 *  11: Monto Total
 */
public class ActualizadorFacturasDB {

    // Query para actualizar solo el número de factura
    private static final String UPDATE_NRO_FACTURA =
        "UPDATE reparaciones SET NroFactura = ? WHERE ELS = ?";

    // Query para verificar si existe el ELS
    private static final String VERIFICAR_ELS =
        "SELECT ELS FROM reparaciones WHERE ELS = ?";

    // Índice de la columna ELS en el Excel (base 0)
    private static final int COL_ELS             = 10;
    private static final int COL_NRO_FACTURA     = 4;
    private static final int COL_PUNTO_VENTA     = 2;
    private static final int COL_NRO_COMPROBANTE = 3;

    // -------------------------------------------------------------------------
    // Clase interna: ahora almacena UNA LISTA de ELS por fila del Excel
    // -------------------------------------------------------------------------
    public static class DatoFacturaExcel {
        private String numeroFacturaCompleto;
        /** Cadena original tal como aparece en el Excel (puede ser "29,30") */
        private String elsRaw;
        /** Lista de ELS individuales ya parseados */
        private List<Integer> elsIds;
        private String puntoVenta;
        private String numeroComprobante;

        public DatoFacturaExcel(String numeroFacturaCompleto, String elsRaw,
                                String puntoVenta, String numeroComprobante) {
            this.numeroFacturaCompleto = numeroFacturaCompleto;
            this.elsRaw                = elsRaw;
            this.puntoVenta            = puntoVenta;
            this.numeroComprobante     = numeroComprobante;
            this.elsIds                = parsearEls(elsRaw);
        }

        /**
         * Parsea la cadena de ELS separados por coma a una lista de enteros.
         * Ignora tokens que no sean numéricos válidos.
         */
        private static List<Integer> parsearEls(String elsRaw) {
            List<Integer> lista = new ArrayList<>();
            if (elsRaw == null || elsRaw.trim().isEmpty()) return lista;

            String[] partes = elsRaw.split(",");
            for (String parte : partes) {
                String token = parte.trim();
                // Eliminar decimales residuales que POI puede agregar (ej: "29.0")
                if (token.contains(".")) {
                    token = token.substring(0, token.indexOf('.'));
                }
                try {
                    int id = Integer.parseInt(token);
                    if (id > 0) lista.add(id);
                } catch (NumberFormatException ignored) {
                    // token no numérico → ignorar
                }
            }
            return lista;
        }

        public String getNumeroFacturaCompleto() { return numeroFacturaCompleto; }
        public String getElsRaw()                { return elsRaw; }
        public List<Integer> getElsIds()         { return elsIds; }
        public String getPuntoVenta()            { return puntoVenta; }
        public String getNumeroComprobante()     { return numeroComprobante; }

        /** Cantidad total de ELS que contiene esta fila */
        public int cantidadEls() { return elsIds.size(); }
    }

    // -------------------------------------------------------------------------
    // Método público principal
    // -------------------------------------------------------------------------
    public static void actualizarDesdeExcel(JFrame parent, Connection conexion) {
        if (conexion == null) {
            JOptionPane.showMessageDialog(parent,
                "No hay conexión a la base de datos.",
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo Excel con facturas");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));
        chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) return;

        List<DatoFacturaExcel> datosFacturas = leerExcel(chooser.getSelectedFile(), parent);

        if (datosFacturas == null || datosFacturas.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "No se encontraron datos válidos en el archivo Excel.",
                "Sin datos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calcular total de actualizaciones individuales (1 fila puede tener N ELS)
        int totalActualizaciones = datosFacturas.stream().mapToInt(DatoFacturaExcel::cantidadEls).sum();

        int confirmar = JOptionPane.showConfirmDialog(parent,
            "Se encontraron " + datosFacturas.size() + " facturas con " +
            totalActualizaciones + " ELS para actualizar.\n\n" +
            "¿Desea continuar con la actualización de la base de datos?",
            "Confirmar actualización",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirmar != JOptionPane.YES_OPTION) return;

        actualizarBaseDatos(conexion, datosFacturas, parent);
    }

    // -------------------------------------------------------------------------
    // Lectura del Excel
    // -------------------------------------------------------------------------
    private static List<DatoFacturaExcel> leerExcel(File archivoExcel, JFrame parent) {
        List<DatoFacturaExcel> datos = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(archivoExcel);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Saltar fila de encabezados (fila 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String numeroFacturaCompleto = getCellValueAsString(row.getCell(COL_NRO_FACTURA));
                String elsRaw                = getCellValueAsString(row.getCell(COL_ELS));
                String puntoVenta            = getCellValueAsString(row.getCell(COL_PUNTO_VENTA));
                String numeroComprobante     = getCellValueAsString(row.getCell(COL_NRO_COMPROBANTE));

                // Necesitamos al menos el ELS
                if (elsRaw == null || elsRaw.trim().isEmpty()) continue;

                String nroFactura = numeroFacturaCompleto;

                // Si no hay columna "N° Factura Completo" pero sí punto de venta y comprobante,
                // intentar armar el número a partir de esos campos
                if ((nroFactura == null || nroFactura.isEmpty()) &&
                    puntoVenta != null && !puntoVenta.isEmpty() &&
                    numeroComprobante != null && !numeroComprobante.isEmpty()) {
                    nroFactura = puntoVenta + "-" + numeroComprobante;
                }

                if (nroFactura != null && !nroFactura.isEmpty()) {
                    DatoFacturaExcel dato = new DatoFacturaExcel(
                        nroFactura, elsRaw, puntoVenta, numeroComprobante);

                    // Solo agregar si hay al menos un ELS válido
                    if (!dato.getElsIds().isEmpty()) {
                        datos.add(dato);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                "Error al leer el archivo Excel:\n" + e.getMessage(),
                "Error de lectura",
                JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return datos;
    }

    // -------------------------------------------------------------------------
    // Conversión de celda a String
    // -------------------------------------------------------------------------
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double valor = cell.getNumericCellValue();
                    if (valor == (long) valor) {
                        return String.valueOf((long) valor);
                    } else {
                        return String.valueOf(valor);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    // Intentar obtener el valor cacheado como string primero
                    try {
                        return cell.getStringCellValue().trim();
                    } catch (Exception ignored) {}
                    double v = cell.getNumericCellValue();
                    return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
                } catch (Exception e) {
                    return cell.getCellFormula();
                }
            default:
                return null;
        }
    }

    // -------------------------------------------------------------------------
    // Actualización en base de datos
    // -------------------------------------------------------------------------
    /**
     * Actualiza la base de datos.
     * Cada fila del Excel puede tener N ELS: se actualiza cada uno por separado
     * con el mismo número de factura.
     */
    private static void actualizarBaseDatos(Connection conexion,
                                            List<DatoFacturaExcel> datos,
                                            JFrame parent) {

        // Calcular total de actualizaciones para la barra de progreso
        int totalOperaciones = datos.stream().mapToInt(DatoFacturaExcel::cantidadEls).sum();

        JDialog progresoDialog = new JDialog(parent, "Actualizando base de datos", true);
        JProgressBar progressBar = new JProgressBar(0, totalOperaciones);
        JLabel lblEstado = new JLabel("Iniciando actualización...");
        JTextArea logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        progresoDialog.setLayout(new BoxLayout(progresoDialog.getContentPane(), BoxLayout.Y_AXIS));
        progresoDialog.add(lblEstado);
        progresoDialog.add(progressBar);
        progresoDialog.add(scrollPane);
        progresoDialog.setSize(500, 400);
        progresoDialog.setLocationRelativeTo(parent);

        int actualizados   = 0;
        int noEncontrados  = 0;
        int errores        = 0;
        int operacion      = 0;

        StringBuilder log = new StringBuilder();
        log.append("=== INICIO DE ACTUALIZACIÓN ===\n");
        log.append("Fecha: ").append(new java.util.Date()).append("\n\n");

        try {
            conexion.setAutoCommit(false);

            try (PreparedStatement pstmtUpdate    = conexion.prepareStatement(UPDATE_NRO_FACTURA);
                 PreparedStatement pstmtVerificar = conexion.prepareStatement(VERIFICAR_ELS)) {

                for (DatoFacturaExcel dato : datos) {
                    String nroFactura = dato.getNumeroFacturaCompleto();

                    for (int els : dato.getElsIds()) {
                        operacion++;
                        progressBar.setValue(operacion);
                        lblEstado.setText("Procesando ELS: " + els);

                        // Verificar si existe el ELS en la tabla
                        pstmtVerificar.setInt(1, els);
                        try (ResultSet rs = pstmtVerificar.executeQuery()) {
                            if (rs.next()) {
                                // Actualizar número de factura
                                pstmtUpdate.setString(1, nroFactura);
                                pstmtUpdate.setInt(2, els);
                                int filas = pstmtUpdate.executeUpdate();

                                if (filas > 0) {
                                    actualizados++;
                                    log.append("✓ ACTUALIZADO  - ELS: ").append(els)
                                       .append(" -> NroFactura: ").append(nroFactura).append("\n");
                                } else {
                                    errores++;
                                    log.append("✗ ERROR        - No se pudo actualizar ELS: ").append(els).append("\n");
                                }
                            } else {
                                noEncontrados++;
                                log.append("⚠ NO ENCONTRADO - ELS: ").append(els)
                                   .append(" (Factura: ").append(nroFactura).append(")\n");
                            }
                        }

                        // Refrescar log cada 10 operaciones
                        if (operacion % 10 == 0 || operacion == totalOperaciones) {
                            logArea.setText(log.toString());
                            logArea.setCaretPosition(logArea.getDocument().getLength());
                        }
                    }
                }

                conexion.commit();
                log.append("\n=== TRANSACCIÓN CONFIRMADA ===\n");

            } catch (SQLException e) {
                conexion.rollback();
                log.append("\n!!! ERROR - Transacción revertida: ").append(e.getMessage()).append("\n");
                e.printStackTrace();
                errores++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.append("\n!!! ERROR DE CONEXIÓN: ").append(e.getMessage()).append("\n");
            errores++;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Resumen final
        log.append("\n=== RESUMEN DE ACTUALIZACIÓN ===\n");
        log.append("Facturas procesadas:           ").append(datos.size()).append("\n");
        log.append("ELS actualizados correctamente:").append(actualizados).append("\n");
        log.append("ELS no encontrados:            ").append(noEncontrados).append("\n");
        log.append("Errores:                       ").append(errores).append("\n");

        logArea.setText(log.toString());

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> progresoDialog.dispose());
        progresoDialog.add(btnCerrar);
        progresoDialog.setVisible(true);

        JOptionPane.showMessageDialog(parent,
            "Actualización completada:\n\n" +
            "✓ ELS actualizados: " + actualizados + "\n" +
            "⚠ ELS no encontrados: " + noEncontrados + "\n" +
            "✗ Errores: " + errores + "\n\n" +
            "Revise el log para más detalles.",
            "Resultado de actualización",
            actualizados > 0 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    // -------------------------------------------------------------------------
    // Métodos de utilidad reutilizables
    // -------------------------------------------------------------------------

    /**
     * Actualiza el número de factura para un único ELS.
     */
    public static boolean actualizarUnicoRegistro(Connection conexion, int els, String nroFactura) {
        if (conexion == null || els <= 0 || nroFactura == null || nroFactura.isEmpty()) {
            return false;
        }
        try (PreparedStatement pstmt = conexion.prepareStatement(UPDATE_NRO_FACTURA)) {
            pstmt.setString(1, nroFactura);
            pstmt.setInt(2, els);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Devuelve la lista de ELS que no tienen número de factura asignado.
     */
    public static List<Integer> obtenerELSSinFactura(Connection conexion) {
        List<Integer> elsSinFactura = new ArrayList<>();
        String query = "SELECT ELS FROM reparaciones WHERE NroFactura IS NULL OR NroFactura = '' ORDER BY ELS";

        try (PreparedStatement pstmt = conexion.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                elsSinFactura.add(rs.getInt("ELS"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elsSinFactura;
    }

    // -------------------------------------------------------------------------
    // main() — herramienta independiente con UI
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Actualizador de Facturas - Prueba");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(550, 400);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel lblTitulo = new JLabel("Actualizador de Número de Factura en Base de Datos");
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblInfo = new JLabel("Seleccione ubicación y archivo Excel para actualizar la columna NroFactura");
            lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel panelUbicacion = new JPanel(new FlowLayout());
            panelUbicacion.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblUbicacion = new JLabel("Seleccionar ubicación:");
            String[] ubicaciones = {"Bariloche", "Buenos Aires"};
            JComboBox<String> cmbUbicacion = new JComboBox<>(ubicaciones);
            JButton btnConectar = new JButton("Conectar");

            panelUbicacion.add(lblUbicacion);
            panelUbicacion.add(cmbUbicacion);
            panelUbicacion.add(btnConectar);

            JButton btnSeleccionar = new JButton("Seleccionar archivo Excel y actualizar");
            btnSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnSeleccionar.setEnabled(false);

            JButton btnVerificarSinFactura = new JButton("Verificar ELS sin número de factura");
            btnVerificarSinFactura.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnVerificarSinFactura.setEnabled(false);

            JTextArea txtLog = new JTextArea(10, 45);
            txtLog.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(txtLog);

            final Conexion[] conexionObj = {null};

            // Conectar
            btnConectar.addActionListener(e -> {
                try {
                    String ubicacion = (String) cmbUbicacion.getSelectedItem();
                    txtLog.append("Conectando a base de datos: " + ubicacion + "...\n");

                    conexionObj[0] = Conexion.getConexion(ubicacion);

                    if (conexionObj[0] != null && conexionObj[0].isConexionActiva()) {
                        txtLog.append("✓ Conexión exitosa a '" +
                            (ubicacion.equalsIgnoreCase("Bariloche") ? "ordenesbrc" : "ordenesbsas") + "'\n");
                        btnSeleccionar.setEnabled(true);
                        btnVerificarSinFactura.setEnabled(true);
                        btnConectar.setEnabled(false);
                        cmbUbicacion.setEnabled(false);
                    } else {
                        txtLog.append("✗ Error: No se pudo establecer la conexión\n");
                    }
                } catch (Exception ex) {
                    txtLog.append("✗ Error al conectar: " + ex.getMessage() + "\n");
                    ex.printStackTrace();
                }
            });

            // Verificar ELS sin factura
            btnVerificarSinFactura.addActionListener(e -> {
                if (conexionObj[0] == null || !conexionObj[0].isConexionActiva()) {
                    JOptionPane.showMessageDialog(frame,
                        "No hay conexión activa.\nPrimero conéctese.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                txtLog.append("\n=== BUSCANDO ELS SIN FACTURA ===\n");
                Connection conn = conexionObj[0].getSQLConexion();
                List<Integer> elsSinFactura = obtenerELSSinFactura(conn);

                if (elsSinFactura.isEmpty()) {
                    txtLog.append("✓ Todos los registros tienen número de factura asignado.\n");
                } else {
                    txtLog.append("ELS sin número de factura: " + elsSinFactura.size() + "\n");
                    txtLog.append("------------------------------------------------\n");
                    for (int i = 0; i < Math.min(elsSinFactura.size(), 30); i++) {
                        txtLog.append("ELS: " + elsSinFactura.get(i) + "\n");
                    }
                    if (elsSinFactura.size() > 30) {
                        txtLog.append("... y " + (elsSinFactura.size() - 30) + " más\n");
                    }
                }
                txtLog.append("====================================\n\n");
                txtLog.setCaretPosition(txtLog.getDocument().getLength());
            });

            // Seleccionar archivo y actualizar
            btnSeleccionar.addActionListener(e -> {
                if (conexionObj[0] == null || !conexionObj[0].isConexionActiva()) {
                    JOptionPane.showMessageDialog(frame,
                        "No hay conexión activa.\nPrimero conéctese.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Seleccionar archivo Excel con facturas");
                chooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));
                chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));

                if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) return;

                File archivoExcel = chooser.getSelectedFile();
                txtLog.append("\n=== PROCESANDO ARCHIVO ===\n");
                txtLog.append("Archivo: " + archivoExcel.getName() + "\n");

                List<DatoFacturaExcel> datosFacturas = leerExcel(archivoExcel, frame);

                if (datosFacturas == null || datosFacturas.isEmpty()) {
                    txtLog.append("✗ No se encontraron datos válidos en el archivo Excel.\n");
                    return;
                }

                int totalEls = datosFacturas.stream().mapToInt(DatoFacturaExcel::cantidadEls).sum();
                txtLog.append("✓ Se encontraron " + datosFacturas.size() +
                              " facturas con " + totalEls + " ELS en total.\n");

                // Mostrar los primeros 10
                txtLog.append("\n--- PRIMEROS 10 REGISTROS ---\n");
                for (int i = 0; i < Math.min(datosFacturas.size(), 10); i++) {
                    DatoFacturaExcel d = datosFacturas.get(i);
                    txtLog.append("ELS: [" + d.getElsRaw() + "] -> Factura: " +
                                  d.getNumeroFacturaCompleto() + "\n");
                }
                if (datosFacturas.size() > 10) {
                    txtLog.append("... y " + (datosFacturas.size() - 10) + " más\n");
                }

                int confirmar = JOptionPane.showConfirmDialog(frame,
                    "Se actualizarán " + totalEls + " ELS en la base de datos.\n\n" +
                    "¿Desea continuar?",
                    "Confirmar actualización",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

                if (confirmar != JOptionPane.YES_OPTION) {
                    txtLog.append("❌ Actualización cancelada por el usuario.\n");
                    return;
                }

                Connection conn = conexionObj[0].getSQLConexion();
                actualizarBaseDatos(conn, datosFacturas, frame);

                txtLog.append("\n✓ Proceso completado.\n");
                txtLog.setCaretPosition(txtLog.getDocument().getLength());
            });

            panel.add(Box.createVerticalStrut(10));
            panel.add(lblTitulo);
            panel.add(Box.createVerticalStrut(10));
            panel.add(lblInfo);
            panel.add(Box.createVerticalStrut(15));
            panel.add(panelUbicacion);
            panel.add(Box.createVerticalStrut(15));
            panel.add(btnSeleccionar);
            panel.add(Box.createVerticalStrut(10));
            panel.add(btnVerificarSinFactura);
            panel.add(Box.createVerticalStrut(15));
            panel.add(new JLabel("Log de operaciones:"));
            panel.add(scrollPane);

            frame.add(panel);
            frame.setVisible(true);

            txtLog.append("=== ACTUALIZADOR DE FACTURAS ===\n");
            txtLog.append("1. Seleccione la ubicación (Bariloche o Buenos Aires)\n");
            txtLog.append("2. Haga clic en 'Conectar'\n");
            txtLog.append("3. Puede verificar ELS sin factura antes de actualizar\n");
            txtLog.append("4. Seleccione el archivo Excel y confirme la actualización\n\n");
            txtLog.append("Nota: un ELS puede tener múltiples valores separados por coma.\n");
            txtLog.append("Esperando conexión...\n");
        });
    }
}