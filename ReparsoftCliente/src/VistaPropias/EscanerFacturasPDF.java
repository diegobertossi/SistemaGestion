package VistaPropias;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
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
        private String razonSocialEmisor;
        private String cuitDestinatario;
        private String razonSocialDestinatario;
        private String fechaEmision;
        private String els; // Múltiples ELS separados por coma
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
        
        public String getRazonSocialEmisor() { return razonSocialEmisor; }
        public void setRazonSocialEmisor(String razonSocialEmisor) { this.razonSocialEmisor = razonSocialEmisor; }
        
        public String getCuitDestinatario() { return cuitDestinatario; }
        public void setCuitDestinatario(String cuitDestinatario) { this.cuitDestinatario = cuitDestinatario; }
        
        public String getRazonSocialDestinatario() { return razonSocialDestinatario; }
        public void setRazonSocialDestinatario(String razonSocialDestinatario) { this.razonSocialDestinatario = razonSocialDestinatario; }
        
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
            
            System.out.println("\n[EscanerFacturasPDF] Procesando: " + archivoPDF.getName());
            
            // =====================================================
            // 1. CUIT EMISOR (Algoritmo mejorado - busca el primer CUIT suelto)
            // =====================================================
            Pattern cuitLibre = Pattern.compile("\\b\\d{11}\\b");
            Matcher mCuitLibre = cuitLibre.matcher(texto);
            
            List<String> todosCuits = new ArrayList<>();
            while (mCuitLibre.find()) {
                todosCuits.add(mCuitLibre.group());
            }
            
            if (!todosCuits.isEmpty()) {
                datos.setCuitEmisor(todosCuits.get(0));
            }
            System.out.println("  CUIT Emisor: " + datos.getCuitEmisor());
            
         // =====================================================
         // 2. DOCUMENTO DESTINATARIO (CUIT / CUIL / DNI)
         // =====================================================
         Pattern cuitDest = Pattern.compile(
                 "(?i)(CUIT|CUIL|DNI)[:\\s]+([0-9\\.]{7,11})"
         );

         Matcher mCuitDest = cuitDest.matcher(texto);

         if (mCuitDest.find()) {
             String tipoDoc = mCuitDest.group(1).toUpperCase();
             String numero = mCuitDest.group(2).replaceAll("\\.", ""); // limpia puntos en DNI

             datos.setCuitDestinatario(numero);

             System.out.println("  " + tipoDoc + " Destinatario: " + numero);
         } else {
             System.out.println("  Documento Destinatario: no encontrado");
         }
            
            // =====================================================
            // 3. RAZON SOCIAL EMISOR (Algoritmo mejorado - funciona con ambos formatos)
            // =====================================================
            String razonEmisor = null;
            
            // Intentar patrón 1: "Razón Social: NOMBRE" (formato estándar) - hasta que encuentre Domicilio o salto de línea
            Pattern razonSocial = Pattern.compile("Raz[oó]n Social:\\s*([^\\n\\r]+?)(?:\\s+Domicilio|\\n|$)", Pattern.CASE_INSENSITIVE);
            Matcher mRazonSocial = razonSocial.matcher(texto);
            if (mRazonSocial.find()) {
                razonEmisor = mRazonSocial.group(1).trim();
                // Limpiar si contiene "Domicilio:" residual
                if (razonEmisor.contains("Domicilio:")) {
                    razonEmisor = razonEmisor.substring(0, razonEmisor.indexOf("Domicilio:")).trim();
                }
            }
            
            // Si no se encontró con el patrón estándar, intentar patrón 2: "ORIGINAL NOMBRE" seguido de dirección
            if (razonEmisor == null || razonEmisor.isEmpty()) {
                // Buscar después de "ORIGINAL" hasta encontrar una dirección (Cedro, 9 De Julio, Domicilio, etc.)
                Pattern razonOriginal = Pattern.compile("ORIGINAL\\s+([A-ZÁÉÍÓÚÑ\\s]+?)(?=\\s+(?:Cedro|9\\s+De\\s+Julio|Domicilio|CUIT|\\n|COD))", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
                Matcher mRazonOriginal = razonOriginal.matcher(texto);
                if (mRazonOriginal.find()) {
                    razonEmisor = mRazonOriginal.group(1).trim();
                }
            }
            
            // Si aún no se encontró, buscar después de "ORIGINAL" hasta el primer salto de línea
            if (razonEmisor == null || razonEmisor.isEmpty()) {
                Pattern razonFallback = Pattern.compile("ORIGINAL\\s+([A-ZÁÉÍÓÚÑ\\s]+?)(?:\\n|COD)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
                Matcher mRazonFallback = razonFallback.matcher(texto);
                if (mRazonFallback.find()) {
                    razonEmisor = mRazonFallback.group(1).trim();
                }
            }
            
            if (razonEmisor != null && !razonEmisor.isEmpty()) {
                // Limpiar espacios extras y eliminar cualquier palabra residual
                razonEmisor = razonEmisor.replaceAll("\\s+", " ").trim();
                // Eliminar "Domicilio:" si quedó
                razonEmisor = razonEmisor.replaceAll("Domicilio:.*$", "").trim();
                datos.setRazonSocialEmisor(razonEmisor);
                System.out.println("  Razón Social Emisor: " + razonEmisor);
            } else {
                System.out.println("  Razón Social Emisor: no encontrado");
            }
            
         // =====================================================
            // 4. RAZON SOCIAL DESTINATARIO (Fuerza Bruta de Palabras Clave)
            // =====================================================
            String razonSocialEncontrada = "";
            String[] lineas = texto.split("\\r?\\n");

            for (int i = 0; i < lineas.length; i++) {

                String linea = lineas[i].toUpperCase();

                // Detecta la etiqueta de forma robusta (con o sin acentos)
                if (linea.matches(".*APELLIDO.*(RAZON|RAZÓN).*SOCIAL.*")) {

                    // Buscamos hacia abajo el primer valor válido
                    for (int j = i + 1; j < lineas.length && j < i + 10; j++) {

                        String candidata = lineas[j].trim();
                        String upper = candidata.toUpperCase();

                        if (candidata.isEmpty()) continue;

                        // ❌ Filtramos basura típica
                        if (
                            upper.matches("\\d{2}/\\d{2}/\\d{4}.*") || // fechas
                            upper.matches("\\d{11}") ||               // CUIT
                            upper.contains("DOMICILIO") ||
                            upper.contains("CONDICION") ||
                            upper.contains("IVA") ||
                            upper.contains("CUIT") ||
                            upper.contains("INGRESOS") ||
                            upper.contains("FECHA")
                        ) {
                            continue;
                        }

                        // ✅ Primera línea válida = razón social
                        razonSocialEncontrada = candidata;
                        break;
                    }

                    if (!razonSocialEncontrada.isEmpty()) break;
                }
            }

            // Limpieza final
            razonSocialEncontrada = razonSocialEncontrada
                    .replaceAll("\\s{2,}", " ")
                    .trim();

            if (razonSocialEncontrada.length() > 2) {
                datos.setRazonSocialDestinatario(razonSocialEncontrada);
                System.out.println("  Razón Social Destinatario: " + razonSocialEncontrada);
            } else {
                System.out.println("  Razón Social Destinatario: no encontrado");
            }
            // =====================================================
            // 5. FECHA DE EMISIÓN (Algoritmo mejorado)
            // =====================================================
            Pattern fecha = Pattern.compile("(\\d{2}/\\d{2}/\\d{4})");
            Matcher mFecha = fecha.matcher(texto);
            if (mFecha.find()) {
                datos.setFechaEmision(mFecha.group(1));
                System.out.println("  Fecha Emisión: " + datos.getFechaEmision());
            } else {
                System.out.println("  Fecha Emisión: no encontrado");
            }
            
            // =====================================================
            // 6. NUMERO FACTURA (Mantengo el método original)
            // =====================================================
            Pattern num = Pattern.compile("Comp\\.\\s*Nro:(\\d{5})\\s+(\\d{8})");
            Matcher mNum = num.matcher(texto);
            if (mNum.find()) {
                datos.setPuntoVenta(mNum.group(1));
                datos.setNumeroComprobante(mNum.group(2));
                datos.setNumeroFacturaCompleto(mNum.group(1) + "-" + mNum.group(2));
                System.out.println("  N° Factura: " + datos.getNumeroFacturaCompleto());
            }
            
            // =====================================================
            // 7. ELS MULTIPLES (Extrae TODOS los ELS en formatos "ELS XXXX" o "ELS: XXXX")
            // =====================================================
            Pattern els = Pattern.compile("ELS[:\\s]+(\\d+)", Pattern.CASE_INSENSITIVE);
            Matcher mEls = els.matcher(texto);
            
            List<String> listaEls = new ArrayList<>();
            while (mEls.find()) {
                String elsValue = mEls.group(1);
                if (!listaEls.contains(elsValue)) {
                    listaEls.add(elsValue);
                }
            }
            
            if (!listaEls.isEmpty()) {
                String elsConcatenados = String.join(",", listaEls);
                datos.setEls(elsConcatenados);
                System.out.println("  ELS encontrados: " + elsConcatenados);
            } else {
                System.out.println("  ELS encontrados: no encontrado");
            }
            
            // =====================================================
            // 8. MONTO TOTAL (Mantengo el método original)
            // =====================================================
         // Buscar específicamente el monto en la línea de "Importe Total"
            Pattern totalPattern = Pattern.compile(
                "Importe\\s+Total[^\\d]*([\\d]{1,3}(?:[\\d]{3})*[,\\.][\\d]{2})",
                Pattern.CASE_INSENSITIVE
            );
            Matcher mTotal = totalPattern.matcher(texto);

            if (mTotal.find()) {
                String montoTotal = mTotal.group(1)
                    .replace(".", "")
                    .replace(",", ".");
                datos.setMontoTotal(montoTotal);
                System.out.println("  Monto Total: " + montoTotal);
            } else {
                // Fallback: buscar patrón de monto argentino (con o sin separador de miles)
                Pattern montos = Pattern.compile(
                    "\\b\\d{1,3}(?:\\.\\d{3})*,\\d{2}\\b|\\b\\d+,\\d{2}\\b"
                );
                Matcher mMontos = montos.matcher(texto);

                String ultimo = null;
                while (mMontos.find()) {
                    ultimo = mMontos.group();
                }

                if (ultimo != null) {
                    String montoTotal = ultimo.replace(".", "").replace(",", ".");
                    datos.setMontoTotal(montoTotal);
                    System.out.println("  Monto Total (fallback): " + montoTotal);
                } else {
                    System.out.println("  Monto Total: no encontrado");
                }
            }

            System.out.println("  ----------------------------------------");
            
            // Validar que al menos tengamos datos mínimos
            if (datos.getNumeroFacturaCompleto() == null && datos.getEls() == null) {
                System.err.println("[EscanerFacturasPDF] No se extrajeron datos del archivo: " + archivoPDF.getName());
                return null;
            }
            
            return datos;
            
        } catch (Exception e) {
            System.err.println("[EscanerFacturasPDF] Error al leer PDF: " + archivoPDF.getName() + " - " + e.getMessage());
            e.printStackTrace();
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
            "Razón Social Emisor",
            "CUIT Destinatario",
            "Razón Social Destinatario",
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
        
        // Ajustar anchos específicos
        sheet.setColumnWidth(1, 6000);  // Nombre Archivo
        sheet.setColumnWidth(6, 8000);  // Razón Social Emisor
        sheet.setColumnWidth(8, 8000);  // Razón Social Destinatario
        sheet.setColumnWidth(10, 4000); // ELS
        sheet.setColumnWidth(11, 4000); // Monto Total
        
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
            row.createCell(6).setCellValue(d.getRazonSocialEmisor() != null ? d.getRazonSocialEmisor() : "");
            row.createCell(7).setCellValue(d.getCuitDestinatario() != null ? d.getCuitDestinatario() : "");
            row.createCell(8).setCellValue(d.getRazonSocialDestinatario() != null ? d.getRazonSocialDestinatario() : "");
            row.createCell(9).setCellValue(d.getFechaEmision() != null ? d.getFechaEmision() : "");
            row.createCell(10).setCellValue(d.getEls() != null ? d.getEls() : "");
            
            // Monto Total como número si es posible
            String montoStr = d.getMontoTotal() != null ? d.getMontoTotal() : "";
            Cell cellMonto = row.createCell(11);
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