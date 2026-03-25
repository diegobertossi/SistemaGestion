package VistaPropias;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase para escanear una carpeta de facturas PDF, extraer datos y generar un Excel
 */
public class EscanerFacturasPDF {
    
    // Clase interna para almacenar los datos extraídos de cada factura
    public static class DatosFacturaExtraidos {
        private String puntoVenta;
        private String numeroComprobante;
        private String numeroFacturaCompleto;
        private String cuitEmisor;
        private String cuitDestinatario;
        private String fechaEmision;
        private String els;
        private String montoTotal;
        private String nombreArchivo;
        
        // Getters y Setters
        public String getPuntoVenta() { return puntoVenta; }
        public void setPuntoVenta(String puntoVenta) { this.puntoVenta = puntoVenta; }
        
        public String getNumeroComprobante() { return numeroComprobante; }
        public void setNumeroComprobante(String numeroComprobante) { this.numeroComprobante = numeroComprobante; }
        
        public String getNumeroFacturaCompleto() { return numeroFacturaCompleto; }
        public void setNumeroFacturaCompleto(String numeroFacturaCompleto) { this.numeroFacturaCompleto = numeroFacturaCompleto; }
        
        public String getCuitEmisor() { return cuitEmisor; }
        public void setCuitEmisor(String cuitEmisor) { this.cuitEmisor = cuitEmisor; }
        
        public String getCuitDestinatario() { return cuitDestinatario; }
        public void setCuitDestinatario(String cuitDestinatario) { this.cuitDestinatario = cuitDestinatario; }
        
        public String getFechaEmision() { return fechaEmision; }
        public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }
        
        public String getEls() { return els; }
        public void setEls(String els) { this.els = els; }
        
        public String getMontoTotal() { return montoTotal; }
        public void setMontoTotal(String montoTotal) { this.montoTotal = montoTotal; }
        
        public String getNombreArchivo() { return nombreArchivo; }
        public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    }
    
    /**
     * Método principal para seleccionar carpeta y procesar facturas
     */
    public static void procesarCarpetaFacturas(JFrame parent) {
        // Seleccionar carpeta
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar carpeta de facturas PDF");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new File("F:/Trabajo/Monotributo/Facturas"));
        
        int resultado = chooser.showOpenDialog(parent);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File carpeta = chooser.getSelectedFile();
        
        // Mostrar diálogo de progreso
        JDialog progresoDialog = new JDialog(parent, "Procesando facturas", true);
        JProgressBar progressBar = new JProgressBar();
        JLabel lblEstado = new JLabel("Iniciando escaneo...");
        
        progresoDialog.setLayout(new BoxLayout(progresoDialog.getContentPane(), BoxLayout.Y_AXIS));
        progresoDialog.add(lblEstado);
        progresoDialog.add(progressBar);
        progresoDialog.setSize(400, 100);
        progresoDialog.setLocationRelativeTo(parent);
        
        // Procesar en un hilo separado
        SwingWorker<List<DatosFacturaExtraidos>, String> worker = new SwingWorker<List<DatosFacturaExtraidos>, String>() {
            @Override
            protected List<DatosFacturaExtraidos> doInBackground() throws Exception {
                List<DatosFacturaExtraidos> resultados = new ArrayList<>();
                
                // Obtener todos los archivos PDF
                File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
                
                if (archivos == null || archivos.length == 0) {
                    publish("No se encontraron archivos PDF en la carpeta seleccionada.");
                    return resultados;
                }
                
                progressBar.setMaximum(archivos.length);
                int contador = 0;
                
                for (File archivo : archivos) {
                    contador++;
                    publish("Procesando: " + archivo.getName() + " (" + contador + "/" + archivos.length + ")");
                    progressBar.setValue(contador);
                    
                    DatosFacturaExtraidos datos = extraerDatosDePDF(archivo);
                    if (datos != null) {
                        resultados.add(datos);
                    }
                }
                
                return resultados;
            }
            
            @Override
            protected void process(List<String> chunks) {
                String ultimo = chunks.get(chunks.size() - 1);
                lblEstado.setText(ultimo);
            }
            
            @Override
            protected void done() {
                progresoDialog.dispose();
                try {
                    List<DatosFacturaExtraidos> resultados = get();
                    if (!resultados.isEmpty()) {
                        generarExcel(resultados, parent);
                    } else {
                        JOptionPane.showMessageDialog(parent, 
                            "No se pudieron extraer datos de ningún archivo PDF.", 
                            "Sin datos", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(parent, 
                        "Error al procesar archivos: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        worker.execute();
        progresoDialog.setVisible(true);
    }
    
    /**
     * Extrae datos de un archivo PDF
     */
    private static DatosFacturaExtraidos extraerDatosDePDF(File archivoPDF) {
        DatosFacturaExtraidos datos = new DatosFacturaExtraidos();
        datos.setNombreArchivo(archivoPDF.getName());
        
        try (PDDocument documento = PDDocument.load(archivoPDF)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(documento);
            
            // Extraer Punto de Venta y Número de Comprobante
            // Formato: "Punto de Venta: Comp. Nro:00001 00000214"
            Pattern patronNumero = Pattern.compile("Punto de Venta:\\s*Comp\\.\\s*Nro:(\\d{5})\\s+(\\d{8})", Pattern.CASE_INSENSITIVE);
            Matcher matcherNumero = patronNumero.matcher(texto);
            if (matcherNumero.find()) {
                String puntoVenta = matcherNumero.group(1);
                String numeroComprobante = matcherNumero.group(2);
                datos.setPuntoVenta(puntoVenta);
                datos.setNumeroComprobante(numeroComprobante);
                datos.setNumeroFacturaCompleto(puntoVenta + "-" + numeroComprobante);
            }
            
            // Extraer CUIT Emisor (primer CUIT que aparece)
            Pattern patronCuit = Pattern.compile("CUIT[:\\s]+(\\d{11})", Pattern.CASE_INSENSITIVE);
            Matcher matcherCuit = patronCuit.matcher(texto);
            List<String> cuits = new ArrayList<>();
            while (matcherCuit.find()) {
                cuits.add(matcherCuit.group(1));
            }
            if (cuits.size() >= 1) {
                datos.setCuitEmisor(cuits.get(0));
            }
            if (cuits.size() >= 2) {
                datos.setCuitDestinatario(cuits.get(1));
            }
            
            // Extraer Fecha de Emisión
            Pattern patronFecha = Pattern.compile("Fecha de Emisi[oó]n[:\\s]+([\\d]{2}/[\\d]{2}/[\\d]{4})", Pattern.CASE_INSENSITIVE);
            Matcher matcherFecha = patronFecha.matcher(texto);
            if (matcherFecha.find()) {
                datos.setFechaEmision(matcherFecha.group(1));
            }
            
            // Extraer ELS (formato: ELS XXXX o ELS: XXXX)
            Pattern patronELS = Pattern.compile("\\bELS[:\\s]+(\\d+)\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcherELS = patronELS.matcher(texto);
            if (matcherELS.find()) {
                datos.setEls(matcherELS.group(1));
            }
            
            // Extraer Monto Total
            // Patrones comunes para el total en facturas AFIP
            Pattern patronTotal = Pattern.compile(
                "(?:Total|TOTAL)\\s*[:\\s]*[$]?\\s*([\\d.,]+)",
                Pattern.CASE_INSENSITIVE);
            Matcher matcherTotal = patronTotal.matcher(texto);
            if (matcherTotal.find()) {
                String total = matcherTotal.group(1);
                // Limpiar formato
                total = total.replace(".", "").replace(",", ".");
                datos.setMontoTotal(total);
            } else {
                // Buscar otro formato: "Importe Total: $ 1.234,56"
                Pattern patronTotal2 = Pattern.compile(
                    "Importe\\s+Total[:\\s]+[$]?\\s*([\\d.,]+)",
                    Pattern.CASE_INSENSITIVE);
                Matcher matcherTotal2 = patronTotal2.matcher(texto);
                if (matcherTotal2.find()) {
                    String total = matcherTotal2.group(1);
                    total = total.replace(".", "").replace(",", ".");
                    datos.setMontoTotal(total);
                }
            }
            
            // Validar que al menos tengamos datos mínimos
            if (datos.getNumeroFacturaCompleto() == null && datos.getEls() == null) {
                System.err.println("[EscanerFacturasPDF] No se extrajeron datos del archivo: " + archivoPDF.getName());
                return null;
            }
            
            return datos;
            
        } catch (Exception e) {
            System.err.println("[EscanerFacturasPDF] Error al leer PDF: " + archivoPDF.getName() + " - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Genera archivo Excel con los datos extraídos
     */
    private static void generarExcel(List<DatosFacturaExtraidos> datos, JFrame parent) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Facturas");
        
        // Crear estilos
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        
        // Crear encabezados
        String[] columnas = {
            "N°", 
            "Nombre Archivo", 
            "Punto de Venta", 
            "Número Comprobante", 
            "N° Factura Completo",
            "CUIT Emisor", 
            "CUIT Destinatario", 
            "Fecha Emisión", 
            "ELS", 
            "Monto Total"
        };
        
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 5000);
        }
        
        // Llenar datos
        int rowNum = 1;
        for (DatosFacturaExtraidos d : datos) {
            Row row = sheet.createRow(rowNum);
            
            row.createCell(0).setCellValue(rowNum);
            row.createCell(1).setCellValue(d.getNombreArchivo() != null ? d.getNombreArchivo() : "");
            row.createCell(2).setCellValue(d.getPuntoVenta() != null ? d.getPuntoVenta() : "");
            row.createCell(3).setCellValue(d.getNumeroComprobante() != null ? d.getNumeroComprobante() : "");
            row.createCell(4).setCellValue(d.getNumeroFacturaCompleto() != null ? d.getNumeroFacturaCompleto() : "");
            row.createCell(5).setCellValue(d.getCuitEmisor() != null ? d.getCuitEmisor() : "");
            row.createCell(6).setCellValue(d.getCuitDestinatario() != null ? d.getCuitDestinatario() : "");
            row.createCell(7).setCellValue(d.getFechaEmision() != null ? d.getFechaEmision() : "");
            row.createCell(8).setCellValue(d.getEls() != null ? d.getEls() : "");
            
            // Monto Total como número si es posible
            String montoStr = d.getMontoTotal() != null ? d.getMontoTotal() : "";
            Cell cellMonto = row.createCell(9);
            try {
                if (!montoStr.isEmpty()) {
                    double monto = Double.parseDouble(montoStr);
                    cellMonto.setCellValue(monto);
                } else {
                    cellMonto.setCellValue("");
                }
            } catch (NumberFormatException e) {
                cellMonto.setCellValue(montoStr);
            }
            cellMonto.setCellStyle(dataStyle);
            
            // Aplicar estilo a todas las celdas
            for (int i = 0; i < columnas.length; i++) {
                if (row.getCell(i) == null) {
                    row.createCell(i).setCellValue("");
                }
                row.getCell(i).setCellStyle(dataStyle);
            }
            
            rowNum++;
        }
        
        // Guardar archivo
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new Date());
            String rutaSalida = System.getProperty("user.home") + "/Desktop/Facturas_Exportadas_" + timestamp + ".xlsx";
            
            try (FileOutputStream fileOut = new FileOutputStream(rutaSalida)) {
                workbook.write(fileOut);
            }
            
            workbook.close();
            
            int confirm = JOptionPane.showConfirmDialog(parent,
                "¡Proceso completado!\n\n" +
                "Se procesaron " + datos.size() + " facturas.\n" +
                "Archivo guardado en:\n" + rutaSalida + "\n\n" +
                "¿Desea abrir la carpeta donde se guardó el archivo?",
                "Exportación completada",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Abrir la carpeta donde se guardó el archivo
                String comando;
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    comando = "explorer.exe /select,\"" + rutaSalida.replace("/", "\\") + "\"";
                } else {
                    comando = "open \"" + new File(rutaSalida).getParent() + "\"";
                }
                Runtime.getRuntime().exec(comando);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                "Error al guardar el archivo Excel:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Método main para pruebas independientes
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Escáner de Facturas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null);
            
            JButton btnProcesar = new JButton("Seleccionar carpeta de facturas");
            btnProcesar.addActionListener(e -> procesarCarpetaFacturas(frame));
            
            JPanel panel = new JPanel();
            panel.add(btnProcesar);
            frame.add(panel);
            frame.setVisible(true);
        });
    }
}