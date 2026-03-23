package VistaPropias;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ExtractorFacturaPDF
 * Extrae datos de facturas AFIP en formato PDF.
 *
 * Uso desde el botón "COPIAR FACTURA" en VentanaVisualizarEquipos:
 *
 *   btnCopiarFactura.addActionListener(e -> {
 *       DatosFactura datos = ExtractorFacturaPDF.extraerDesdeSelector(ventana);
 *       if (datos != null) {
 *           datos.imprimirEnConsola();
 *           // usar datos.getELS(), datos.getNumeroComprobante(), etc.
 *       }
 *   });
 */
public class ExtractorFacturaPDF {

    // =====================================================
    // CLASE INTERNA: DatosFactura
    // =====================================================

    public static class DatosFactura {
        private String numeroComprobante;
        private String fechaEmision;
        private String razonSocialEmisor;
        private String razonSocialDestinatario;
        private String cuitDestinatario;
        private List<String> itemsFacturados;
        private Integer els;

        public DatosFactura() {
            this.itemsFacturados = new ArrayList<>();
        }

        // ---- Getters y Setters ----

        public String getNumeroComprobante()        { return numeroComprobante; }
        public void setNumeroComprobante(String v)  { this.numeroComprobante = v; }

        public String getFechaEmision()             { return fechaEmision; }
        public void setFechaEmision(String v)       { this.fechaEmision = v; }

        public String getRazonSocialEmisor()        { return razonSocialEmisor; }
        public void setRazonSocialEmisor(String v)  { this.razonSocialEmisor = v; }

        public String getRazonSocialDestinatario()       { return razonSocialDestinatario; }
        public void setRazonSocialDestinatario(String v) { this.razonSocialDestinatario = v; }

        public String getCuitDestinatario()         { return cuitDestinatario; }
        public void setCuitDestinatario(String v)   { this.cuitDestinatario = v; }

        public List<String> getItemsFacturados()        { return itemsFacturados; }
        public void addItem(String item)                { this.itemsFacturados.add(item); }

        public Integer getEls()                     { return els; }
        public void setEls(Integer v)               { this.els = v; }

        /** Imprime todos los datos extraídos en consola */
        public void imprimirEnConsola() {
            System.out.println("========== DATOS DE FACTURA EXTRAÍDOS ==========");
            System.out.println("Comp. Nro         : " + nvl(numeroComprobante));
            System.out.println("Fecha de Emisión  : " + nvl(fechaEmision));
            System.out.println("Razón Social (Emisor)      : " + nvl(razonSocialEmisor));
            System.out.println("Razón Social (Destinatario): " + nvl(razonSocialDestinatario));
            System.out.println("CUIT Destinatario : " + nvl(cuitDestinatario));
            System.out.println("ELS encontrado    : " + (els != null ? els : "(no encontrado)"));
            System.out.println("--- Ítems facturados ---");
            if (itemsFacturados.isEmpty()) {
                System.out.println("  (ninguno)");
            } else {
                for (int i = 0; i < itemsFacturados.size(); i++) {
                    System.out.println("  [" + (i + 1) + "] " + itemsFacturados.get(i));
                }
            }
            System.out.println("=================================================");
        }

        private String nvl(String s) { return s != null ? s : "(no encontrado)"; }
    }

    // =====================================================
    // MÉTODO PRINCIPAL: abre selector y extrae datos
    // =====================================================

    
    
    public static DatosFactura extraerDesdeSelector(java.awt.Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar factura PDF");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));
        chooser.setAcceptAllFileFilterUsed(false);
        
        // Establecer el directorio por defecto usando File.separator para compatibilidad multiplataforma
        String rutaDirectorio = "F:" + File.separator + "Trabajo" + File.separator + "Monotributo";
        File directorioDefault = new File(rutaDirectorio);
        
        if (directorioDefault.exists() && directorioDefault.isDirectory()) {
            chooser.setCurrentDirectory(directorioDefault);
        } else {
            // Fallback al escritorio del usuario
            chooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
            JOptionPane.showMessageDialog(parent, 
                "El directorio F:\\Trabajo\\Monotributo no existe.\nSe abrirá en el Escritorio.", 
                "Directorio no encontrado", 
                JOptionPane.WARNING_MESSAGE);
        }
        
        int resultado = chooser.showOpenDialog(parent);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        
        File archivoPDF = chooser.getSelectedFile();
        return extraerDesdePDF(archivoPDF);
    }
    
    

    /**
     * Extrae datos de un archivo PDF de factura AFIP.
     *
     * @param archivoPDF Archivo PDF a procesar
     * @return DatosFactura con los campos extraídos, o null si hubo error
     */
    /**
     * Extrae datos de un archivo PDF de factura AFIP.
     *
     * @param archivoPDF Archivo PDF a procesar
     * @return DatosFactura con los campos extraídos, o null si hubo error
     */
    public static DatosFactura extraerDesdePDF(File archivoPDF) {
        if (archivoPDF == null || !archivoPDF.exists()) {
            System.err.println("[ExtractorFacturaPDF] Archivo no encontrado: " + archivoPDF);
            return null;
        }

        try (PDDocument documento = PDDocument.load(archivoPDF)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(documento);

//            System.out.println("[ExtractorFacturaPDF] Texto extraído del PDF:");
//            System.out.println("------- INICIO TEXTO PDF -------");
//            System.out.println(texto);
//            System.out.println("------- FIN TEXTO PDF -------");

            DatosFactura datos = parsearTexto(texto);
            
         // Buscar el patrón "Punto de Venta: Comp. Nro:" con formato especial
            Pattern patronCompleto = Pattern.compile("Punto de Venta:\\s*Comp\\.\\s*Nro:(\\d{5})\\s+(\\d{8})", Pattern.CASE_INSENSITIVE);
            Matcher matcherCompleto = patronCompleto.matcher(texto);

            if (matcherCompleto.find()) {
                String puntoVenta = matcherCompleto.group(1);
                String numeroComprobante = matcherCompleto.group(2);
                String numeroCompleto = puntoVenta + "-" + numeroComprobante;
                datos.setNumeroComprobante(numeroCompleto);
                System.out.println("[ExtractorFacturaPDF] Número completo capturado: " + numeroCompleto);
                System.out.println("[ExtractorFacturaPDF] Punto de venta: " + puntoVenta);
                System.out.println("[ExtractorFacturaPDF] Número comprobante: " + numeroComprobante);
            }
            
            
            
            return datos;

        } catch (Exception e) {
            System.err.println("[ExtractorFacturaPDF] Error al leer el PDF: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al leer el PDF:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // =====================================================
    // PARSEO DEL TEXTO EXTRAÍDO
    // =====================================================

    private static DatosFactura parsearTexto(String texto) {
        DatosFactura datos = new DatosFactura();
        String[] lineas = texto.split("\\r?\\n");

        // --- Número de comprobante ---
        // Patrón: "Comp. Nro: 00000001"  o  "Punto de Venta: 0001 Comp. Nro: 00000001"
        datos.setNumeroComprobante(extraerPatron(texto,
                "Comp\\.\\s*Nro[:\\s]+([\\d\\-]+)"));

        // --- Fecha de emisión ---
        // Patrón: "Fecha de Emisión: 05/12/2017"
        datos.setFechaEmision(extraerPatron(texto,
                "Fecha de Emisi[oó]n[:\\s]+([\\d]{2}/[\\d]{2}/[\\d]{4})"));

        // --- Razón Social del EMISOR ---
        // En facturas AFIP la razón social del emisor aparece como título grande
        // o luego de "Razón Social:"
        String razonEmisor = extraerPatron(texto, "Raz[oó]n Social[:\\s]+([^\\n\\r]+)");
        if (razonEmisor == null) {
            // Fallback: buscar el nombre antes de "Cédula" o primera línea relevante
            razonEmisor = extraerPrimeraLineaSignificativa(lineas);
        }
        datos.setRazonSocialEmisor(limpiar(razonEmisor));

        // --- CUIT del destinatario ---
        // Patrón: "CUIT: 30706433585"  (puede haber varios, tomamos el que aparece
        // en la sección del destinatario, generalmente el segundo CUIT mencionado)
        List<String> cuits = extraerTodos(texto, "CUIT[:\\s]+([\\d]{11})");
        if (cuits.size() >= 2) {
            datos.setCuitDestinatario(cuits.get(1)); // el segundo es el destinatario
        } else if (cuits.size() == 1) {
            datos.setCuitDestinatario(cuits.get(0));
        }

        // --- Razón Social del DESTINATARIO ---
        // Patrón: "Apellido y Nombre / Razón Social: LUCERO ASCENSORES SRL"
        String razonDest = extraerPatron(texto,
                "Apellido y Nombre\\s*/\\s*Raz[oó]n Social[:\\s]+([^\\n\\r]+)");
        datos.setRazonSocialDestinatario(limpiar(razonDest));

        // --- Ítems facturados + ELS ---
        extraerItems(texto, datos);

        return datos;
    }

    /**
     * Extrae los ítems facturados buscando el bloque entre encabezados de tabla
     * y el subtotal.
     */
    private static void extraerItems(String texto, DatosFactura datos) {
        // Buscamos el área entre la línea de encabezado de tabla y "Subtotal:"
        // En el PDF del ejemplo, cada ítem tiene formato multilínea.
        // Estrategia: buscar todo texto entre "Subtotal" y el bloque de columnas.

        // Patrón para capturar líneas de descripción de producto/servicio.
        // En AFIP, el bloque del ítem empieza después de las columnas del encabezado.
        Pattern patItemBloque = Pattern.compile(
                "(?:Código\\s+Producto\\s*/\\s*Servicio[^\\n]*\\n)(.*?)(?:Subtotal:|$)",
                Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

        Matcher mBloque = patItemBloque.matcher(texto);

        if (mBloque.find()) {
            String bloqueItems = mBloque.group(1).trim();

            // Cada ítem es varias líneas antes de encontrar el patrón de cantidad/precio.
            // Separamos por líneas y juntamos hasta encontrar una línea con números
            // que corresponda a cantidad + precio.
            String[] lineas = bloqueItems.split("\\r?\\n");
            StringBuilder itemActual = new StringBuilder();

            for (String linea : lineas) {
                String l = linea.trim();
                if (l.isEmpty()) continue;

                // Si la línea tiene formato de cantidad + precio (ej: "1,00 unidades 9500,00 ...")
                // significa que terminó la descripción del ítem
                if (l.matches("^[\\d]+[,.]?[\\d]*\\s+\\w+.*[\\d.,]+.*")) {
                    // guardar ítem previo si existe
                    if (itemActual.length() > 0) {
                        String itemTexto = itemActual.toString().trim();
                        datos.addItem(itemTexto);
                        extraerELS(itemTexto, datos);
                        itemActual.setLength(0);
                    }
                } else {
                    // Acumular líneas de descripción
                    if (itemActual.length() > 0) itemActual.append(" ");
                    itemActual.append(l);
                }
            }

            // Guardar último ítem acumulado
            if (itemActual.length() > 0) {
                String itemTexto = itemActual.toString().trim();
                datos.addItem(itemTexto);
                extraerELS(itemTexto, datos);
            }
        }

        // Si no se encontraron ítems con el patrón anterior,
        // buscar directamente en el texto completo cualquier mención de ELS
        if (datos.getEls() == null) {
            extraerELS(texto, datos);
        }

        // Si tampoco hay ítems, agregar una descripción genérica buscando
        // líneas con contenido entre "unidades" y "Subtotal"
        if (datos.getItemsFacturados().isEmpty()) {
            Pattern patItem2 = Pattern.compile(
                    "([A-Za-záéíóúÁÉÍÓÚñÑ][^\\n]{10,}?)(?=\\s*[\\d]+[,.]?[\\d]*\\s+unidades)",
                    Pattern.DOTALL);
            Matcher m2 = patItem2.matcher(texto);
            while (m2.find()) {
                String item = m2.group(1).trim().replaceAll("\\s+", " ");
                if (!item.isEmpty()) {
                    datos.addItem(item);
                    extraerELS(item, datos);
                }
            }
        }
    }

    /**
     * Extrae el número ELS de un texto (busca "ELS XXXX" o "ELS: XXXX").
     * Solo setea si todavía no se encontró un ELS.
     */
    private static void extraerELS(String texto, DatosFactura datos) {
        if (datos.getEls() != null) return; // ya encontrado

        Pattern patELS = Pattern.compile(
                "\\bELS[:\\s]+([\\d]+)\\b",
                Pattern.CASE_INSENSITIVE);
        Matcher m = patELS.matcher(texto);
        if (m.find()) {
            try {
                datos.setEls(Integer.parseInt(m.group(1).trim()));
            } catch (NumberFormatException e) {
                System.err.println("[ExtractorFacturaPDF] ELS no numérico: " + m.group(1));
            }
        }
    }

    // =====================================================
    // UTILIDADES DE REGEXP
    // =====================================================

    /** Extrae el primer grupo capturado de un patrón regex, o null si no hay match. */
    private static String extraerPatron(String texto, String patron) {
        Pattern p = Pattern.compile(patron, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(texto);
        return m.find() ? m.group(1).trim() : null;
    }

    /** Extrae todos los grupos capturados de un patrón regex. */
    private static List<String> extraerTodos(String texto, String patron) {
        List<String> resultados = new ArrayList<>();
        Pattern p = Pattern.compile(patron, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(texto);
        while (m.find()) {
            resultados.add(m.group(1).trim());
        }
        return resultados;
    }

    /** Busca la primera línea no vacía y con texto significativo como razón social del emisor. */
    private static String extraerPrimeraLineaSignificativa(String[] lineas) {
        for (String linea : lineas) {
            String l = linea.trim();
            if (l.length() > 3
                    && !l.equalsIgnoreCase("ORIGINAL")
                    && !l.equalsIgnoreCase("DUPLICADO")
                    && !l.equalsIgnoreCase("TRIPLICADO")
                    && !l.matches(".*FACTURA.*")
                    && !l.matches(".*Punto de Venta.*")) {
                return l;
            }
        }
        return null;
    }

    /** Limpia espacios extra y caracteres indeseados. */
    private static String limpiar(String s) {
        if (s == null) return null;
        return s.replaceAll("\\s+", " ").trim();
    }
}
