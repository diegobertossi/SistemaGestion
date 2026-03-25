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
 * a partir de un archivo Excel generado por EscanerFacturasPDF
 */
public class ActualizadorFacturasDB {
    
    // Query para actualizar solo el número de factura
    private static final String UPDATE_NRO_FACTURA = 
        "UPDATE reparaciones SET NroFactura = ? WHERE ELS = ?";
    
    // Query para verificar si existe el ELS
    private static final String VERIFICAR_ELS = 
        "SELECT ELS FROM reparaciones WHERE ELS = ?";
    
    // Query para obtener todos los ELS existentes (opcional)
    private static final String OBTENER_TODOS_ELS = 
        "SELECT ELS, NroFactura FROM reparaciones ORDER BY ELS";
    
    // Clase interna para almacenar datos del Excel
    public static class DatoFacturaExcel {
        private String numeroFacturaCompleto;
        private String els;
        private String puntoVenta;
        private String numeroComprobante;
        
        public DatoFacturaExcel(String numeroFacturaCompleto, String els, 
                                String puntoVenta, String numeroComprobante) {
            this.numeroFacturaCompleto = numeroFacturaCompleto;
            this.els = els;
            this.puntoVenta = puntoVenta;
            this.numeroComprobante = numeroComprobante;
        }
        
        public String getNumeroFacturaCompleto() { return numeroFacturaCompleto; }
        public String getEls() { return els; }
        public String getPuntoVenta() { return puntoVenta; }
        public String getNumeroComprobante() { return numeroComprobante; }
        
        public int getElsAsInt() {
            try {
                return Integer.parseInt(els);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }
    
    /**
     * Método principal: selecciona el archivo Excel y actualiza la base de datos
     */
    public static void actualizarDesdeExcel(JFrame parent, Connection conexion) {
        if (conexion == null) {
            JOptionPane.showMessageDialog(parent, 
                "No hay conexión a la base de datos.", 
                "Error de conexión", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Seleccionar archivo Excel
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo Excel con facturas");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));
        chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        
        int resultado = chooser.showOpenDialog(parent);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File archivoExcel = chooser.getSelectedFile();
        
        // Leer datos del Excel
        List<DatoFacturaExcel> datosFacturas = leerExcel(archivoExcel, parent);
        
        if (datosFacturas == null || datosFacturas.isEmpty()) {
            JOptionPane.showMessageDialog(parent, 
                "No se encontraron datos válidos en el archivo Excel.", 
                "Sin datos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar actualización
        int confirmar = JOptionPane.showConfirmDialog(parent,
            "Se encontraron " + datosFacturas.size() + " registros para actualizar.\n\n" +
            "¿Desea continuar con la actualización de la base de datos?",
            "Confirmar actualización",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Actualizar base de datos
        actualizarBaseDatos(conexion, datosFacturas, parent);
    }
    
    /**
     * Lee el archivo Excel y extrae los datos de factura
     */
    private static List<DatoFacturaExcel> leerExcel(File archivoExcel, JFrame parent) {
        List<DatoFacturaExcel> datos = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(archivoExcel);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            // Saltar fila de encabezados (fila 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                // Obtener datos según la estructura del Excel generado:
                // Columna 0: N°
                // Columna 1: Nombre Archivo
                // Columna 2: Punto de Venta
                // Columna 3: Número Comprobante
                // Columna 4: N° Factura Completo
                // Columna 5: CUIT Emisor
                // Columna 6: CUIT Destinatario
                // Columna 7: Fecha Emisión
                // Columna 8: ELS
                // Columna 9: Monto Total
                
                String numeroFacturaCompleto = getCellValueAsString(row.getCell(4));
                String els = getCellValueAsString(row.getCell(8));
                String puntoVenta = getCellValueAsString(row.getCell(2));
                String numeroComprobante = getCellValueAsString(row.getCell(3));
                
                // Validar datos mínimos
                if (els != null && !els.isEmpty() && 
                    numeroFacturaCompleto != null && !numeroFacturaCompleto.isEmpty()) {
                    
                    datos.add(new DatoFacturaExcel(numeroFacturaCompleto, els, puntoVenta, numeroComprobante));
                } else if (els != null && !els.isEmpty()) {
                    // Si no hay número de factura completo pero sí ELS, intentar formatear
                    if (puntoVenta != null && !puntoVenta.isEmpty() && 
                        numeroComprobante != null && !numeroComprobante.isEmpty()) {
                        String formateado = puntoVenta + "-" + numeroComprobante;
                        datos.add(new DatoFacturaExcel(formateado, els, puntoVenta, numeroComprobante));
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
    
    /**
     * Obtiene el valor de una celda como String
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Para números que pueden ser ELS o números de factura
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
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getCellFormula();
                }
            default:
                return null;
        }
    }
    
    /**
     * Actualiza la base de datos con los números de factura
     */
    private static void actualizarBaseDatos(Connection conexion, 
                                            List<DatoFacturaExcel> datos, 
                                            JFrame parent) {
        // Crear diálogo de progreso
        JDialog progresoDialog = new JDialog(parent, "Actualizando base de datos", true);
        JProgressBar progressBar = new JProgressBar(0, datos.size());
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
        
        // Contadores
        int actualizados = 0;
        int noEncontrados = 0;
        int errores = 0;
        
        StringBuilder log = new StringBuilder();
        log.append("=== INICIO DE ACTUALIZACIÓN ===\n");
        log.append("Fecha: ").append(new java.util.Date()).append("\n\n");
        
        try {
            conexion.setAutoCommit(false);
            
            try (PreparedStatement pstmtUpdate = conexion.prepareStatement(UPDATE_NRO_FACTURA);
                 PreparedStatement pstmtVerificar = conexion.prepareStatement(VERIFICAR_ELS)) {
                
                for (int i = 0; i < datos.size(); i++) {
                    DatoFacturaExcel dato = datos.get(i);
                    int els = dato.getElsAsInt();
                    String nroFactura = dato.getNumeroFacturaCompleto();
                    
                    // Actualizar progreso
                    progressBar.setValue(i + 1);
                    lblEstado.setText("Procesando ELS: " + els);
                    
                    // Verificar si existe el ELS
                    pstmtVerificar.setInt(1, els);
                    ResultSet rs = pstmtVerificar.executeQuery();
                    
                    if (rs.next()) {
                        // Actualizar número de factura
                        pstmtUpdate.setString(1, nroFactura);
                        pstmtUpdate.setInt(2, els);
                        int filas = pstmtUpdate.executeUpdate();
                        
                        if (filas > 0) {
                            actualizados++;
                            log.append("✓ ACTUALIZADO - ELS: ").append(els)
                               .append(" -> NroFactura: ").append(nroFactura).append("\n");
                        } else {
                            errores++;
                            log.append("✗ ERROR - No se pudo actualizar ELS: ").append(els).append("\n");
                        }
                    } else {
                        noEncontrados++;
                        log.append("⚠ NO ENCONTRADO - ELS: ").append(els)
                           .append(" no existe en la tabla reparaciones\n");
                    }
                    
                    rs.close();
                    
                    // Actualizar log cada cierto número de registros
                    if ((i + 1) % 10 == 0 || i == datos.size() - 1) {
                        logArea.setText(log.toString());
                        logArea.setCaretPosition(logArea.getDocument().getLength());
                    }
                }
                
                // Confirmar transacción
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
        log.append("Total procesados: ").append(datos.size()).append("\n");
        log.append("Actualizados correctamente: ").append(actualizados).append("\n");
        log.append("ELS no encontrados: ").append(noEncontrados).append("\n");
        log.append("Errores: ").append(errores).append("\n");
        
        logArea.setText(log.toString());
        
        // Botón para cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> progresoDialog.dispose());
        progresoDialog.add(btnCerrar);
        
        progresoDialog.setVisible(true);
        
        // Mostrar resumen final en diálogo
        JOptionPane.showMessageDialog(parent,
            "Actualización completada:\n\n" +
            "✓ Actualizados: " + actualizados + "\n" +
            "⚠ No encontrados: " + noEncontrados + "\n" +
            "✗ Errores: " + errores + "\n\n" +
            "Revise el log para más detalles.",
            "Resultado de actualización",
            actualizados > 0 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Método para actualizar un solo registro (reutilizable)
     */
    public static boolean actualizarUnicoRegistro(Connection conexion, int els, String nroFactura) {
        if (conexion == null || els <= 0 || nroFactura == null || nroFactura.isEmpty()) {
            return false;
        }
        
        try (PreparedStatement pstmt = conexion.prepareStatement(UPDATE_NRO_FACTURA)) {
            pstmt.setString(1, nroFactura);
            pstmt.setInt(2, els);
            int filas = pstmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método para obtener todos los registros que no tienen número de factura
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
    
    
    
    /**
     * Método main para pruebas independientes usando la clase Conexion existente
     * Permite seleccionar el archivo Excel y actualizar la base de datos sin necesidad de un botón
     */
    public static void main(String[] args) {
        // Configurar look and feel para mejor apariencia
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Actualizador de Facturas - Prueba");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(550, 350);
            frame.setLocationRelativeTo(null);
            
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel lblTitulo = new JLabel("Actualizador de Número de Factura en Base de Datos");
            //lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblInfo = new JLabel("Seleccione ubicación y archivo Excel para actualizar la columna NroFactura");
            lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Panel para seleccionar ubicación
            JPanel panelUbicacion = new JPanel();
            panelUbicacion.setLayout(new FlowLayout());
            panelUbicacion.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel lblUbicacion = new JLabel("Seleccionar ubicación:");
            String[] ubicaciones = {"Bariloche", "Buenos Aires"};
            JComboBox<String> cmbUbicacion = new JComboBox<>(ubicaciones);
            JButton btnConectar = new JButton("Conectar");
            
            panelUbicacion.add(lblUbicacion);
            panelUbicacion.add(cmbUbicacion);
            panelUbicacion.add(btnConectar);
            
            // Botones principales
            JButton btnSeleccionar = new JButton("Seleccionar archivo Excel y actualizar");
            btnSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnSeleccionar.setEnabled(false);
            
            JButton btnVerificarSinFactura = new JButton("Verificar ELS sin número de factura");
            btnVerificarSinFactura.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnVerificarSinFactura.setEnabled(false);
            
            // Área de log
            JTextArea txtLog = new JTextArea(10, 45);
            txtLog.setEditable(false);
            //txtLog.setFont(new Font("Monospaced", Font, 11));
            JScrollPane scrollPane = new JScrollPane(txtLog);
            
            // Variable para almacenar la conexión
            final Conexion[] conexionObj = {null};
            
            // Botón conectar
            btnConectar.addActionListener(e -> {
                try {
                    String ubicacion = (String) cmbUbicacion.getSelectedItem();
                    txtLog.append("Conectando a base de datos: " + ubicacion + "...\n");
                    
                    conexionObj[0] = Conexion.getConexion(ubicacion);
                    
                    if (conexionObj[0] != null && conexionObj[0].isConexionActiva()) {
                        txtLog.append("✓ Conexión exitosa a la base de datos '" + 
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
            
            // Botón verificar ELS sin factura
            btnVerificarSinFactura.addActionListener(e -> {
                if (conexionObj[0] == null || !conexionObj[0].isConexionActiva()) {
                    JOptionPane.showMessageDialog(frame, 
                        "No hay conexión activa a la base de datos.\nPrimero conéctese.", 
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                txtLog.append("\n=== BUSCANDO ELS SIN FACTURA ===\n");
                Connection conn = conexionObj[0].getSQLConexion();
                List<Integer> elsSinFactura = obtenerELSSinFactura(conn);
                
                if (elsSinFactura.isEmpty()) {
                    txtLog.append("✓ Todos los registros tienen número de factura asignado.\n");
                } else {
                    txtLog.append("ELS sin número de factura encontrados: " + elsSinFactura.size() + "\n");
                    txtLog.append("------------------------------------------------\n");
                    for (int i = 0; i < Math.min(elsSinFactura.size(), 30); i++) {
                        txtLog.append("ELS: " + elsSinFactura.get(i) + "\n");
                    }
                    if (elsSinFactura.size() > 30) {
                        txtLog.append("... y " + (elsSinFactura.size() - 30) + " más\n");
                    }
                }
                txtLog.append("====================================\n\n");
                
                // Mover scroll al final
                txtLog.setCaretPosition(txtLog.getDocument().getLength());
            });
            
            // Botón seleccionar archivo y actualizar
            btnSeleccionar.addActionListener(e -> {
                if (conexionObj[0] == null || !conexionObj[0].isConexionActiva()) {
                    JOptionPane.showMessageDialog(frame, 
                        "No hay conexión activa a la base de datos.\nPrimero conéctese.", 
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Seleccionar archivo Excel con facturas");
                chooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));
                chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
                
                int resultado = chooser.showOpenDialog(frame);
                if (resultado != JFileChooser.APPROVE_OPTION) {
                    return;
                }
                
                File archivoExcel = chooser.getSelectedFile();
                txtLog.append("\n=== PROCESANDO ARCHIVO ===\n");
                txtLog.append("Archivo: " + archivoExcel.getName() + "\n");
                
                // Leer datos del Excel
                List<DatoFacturaExcel> datosFacturas = leerExcel(archivoExcel, frame);
                
                if (datosFacturas == null || datosFacturas.isEmpty()) {
                    txtLog.append("✗ No se encontraron datos válidos en el archivo Excel.\n");
                    return;
                }
                
                txtLog.append("✓ Se encontraron " + datosFacturas.size() + " registros para procesar.\n");
                
                // Mostrar resumen de los primeros registros
                txtLog.append("\n--- PRIMEROS 10 REGISTROS A ACTUALIZAR ---\n");
                for (int i = 0; i < Math.min(datosFacturas.size(), 10); i++) {
                    DatoFacturaExcel d = datosFacturas.get(i);
                    txtLog.append("ELS: " + d.getEls() + " -> Factura: " + d.getNumeroFacturaCompleto() + "\n");
                }
                if (datosFacturas.size() > 10) {
                    txtLog.append("... y " + (datosFacturas.size() - 10) + " más\n");
                }
                
                // Confirmar actualización
                int confirmar = JOptionPane.showConfirmDialog(frame,
                    "Se encontraron " + datosFacturas.size() + " registros para actualizar.\n\n" +
                    "¿Desea continuar con la actualización de la base de datos?",
                    "Confirmar actualización",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (confirmar != JOptionPane.YES_OPTION) {
                    txtLog.append("❌ Actualización cancelada por el usuario.\n");
                    return;
                }
                
                // Actualizar base de datos
                Connection conn = conexionObj[0].getSQLConexion();
                actualizarBaseDatos(conn, datosFacturas, frame);
                
                txtLog.append("\n✓ Proceso completado. Revise el resumen arriba.\n");
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
            
            // Mensaje inicial
            txtLog.append("=== ACTUALIZADOR DE FACTURAS ===\n");
            txtLog.append("1. Seleccione la ubicación (Bariloche o Buenos Aires)\n");
            txtLog.append("2. Haga clic en 'Conectar'\n");
            txtLog.append("3. Una vez conectado, puede verificar ELS sin factura\n");
            txtLog.append("4. Seleccione el archivo Excel y confirme la actualización\n\n");
            txtLog.append("Esperando conexión...\n");
        });
    }
    
    
    
}