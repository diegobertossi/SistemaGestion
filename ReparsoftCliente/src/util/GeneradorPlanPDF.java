package util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class GeneradorPlanPDF {

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK);
    private static final Font SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(0, 102, 153));
    private static final Font SUBTITULO2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
    private static final Font NEGRITA = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
    private static final Font ITALICA = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.DARK_GRAY);
    private static final Font HEADER_TABLA = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
    private static final Font CELDA = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);

    public static void main(String[] args) throws Exception {
        String archivo = "Plan_FacturaSoft_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

        Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(doc, new FileOutputStream(archivo));
        doc.open();

        agregarTitulo(doc, "PLAN DE DESARROLLO");
        agregarTitulo(doc, "FacturaSoft v1.0");

        Paragraph fecha = new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()), ITALICA);
        fecha.setSpacingBefore(5);
        fecha.setSpacingAfter(20);
        doc.add(fecha);

        agregarSeccion(doc, "OBJETIVO", "Sistema de facturación independiente que gestione la contabilidad "
                + "del taller y emita comprobantes electrónicos via ARCA (Administración Federal de Ingresos Públicos).");

        agregarSeccion(doc, "TECNOLOGÍAS", "Hereda el stack tecnológico de ReparSoft:");
        agregarLista(doc, "Java 8 + Swing",
                "MySQL 8.x",
                "JasperReports 6.21.3",
                "iText 2.1.7 / OpenPDF 1.3.30",
                "JavaMail (javax.mail)",
                "BouncyCastle (firma digital CMS)");

        agregarSeccion(doc, "BASE DE DATOS", "");
        agregarSubSeccion(doc, "1. facturacion_db (NUEVA - gestión propia)");
        agregarTabla(doc, new String[][]{
            {"Tabla", "Descripción"},
            {"caja_movimientos", "Registros de cobros y pagos de caja"},
            {"gastos", "Gastos mensuales por categoría"},
            {"categorias_gastos", "Definición de categorías de gastos"},
            {"comprobantes", "Facturas A/B/C, notas crédito/débito"},
            {"cuit_certificados", "CUIT + certificados .p12 + punto de venta"},
            {"token_cache", "Tokens ARCA en memoria con timestamp"},
            {"configuraciones", "Parámetros generales del sistema"}
        });

        agregarSubSeccion(doc, "2. ordenesbrc / ordenesbsas (EXISTENTE - solo lectura)");
        agregarLista(doc,
                "ConexionReparsoft (Singleton - solo SELECT)",
                "ReparacionLecturaDAO: buscar por ELS");

        agregarSeccion(doc, "CRONOGRAMA (13 semanas)", "");
        agregarTabla(doc, new String[][]{
            {"Fase", "Semanas", "Descripción", "Entregable"},
            {"1", "1-2", "Proyecto Base", "Estructura, conexiones, AGENTS.md"},
            {"2", "3-6", "Módulo ARCA", "WSAA, FECAESolicitar, facturas"},
            {"3", "7-10", "Migración Excel", "Caja, Gastos, datos 2026"},
            {"4", "11-12", "Reportes + Email", "JasperReports PDF, JavaMail"},
            {"5", "13", "Integración ReparSoft", "Botón facturar en ReparSoft"}
        });

        agregarSeccion(doc, "FASE 1: Proyecto Base (Semanas 1-2)", "");
        agregarTabla2Col(doc, new String[][]{
            {"Tarea", "Descripción"},
            {"1.1", "Crear estructura FacturaSoft/ con src/, lib/, AGENTS.md"},
            {"1.2", "ConexionFacturacion.java (Singleton → facturacion_db)"},
            {"1.3", "ConexionReparsoft.java (Singleton solo lectura)"},
            {"1.4", "Copiar libs necesarios de ReparSoft"},
            {"1.5", "AGENTS.md con convenciones y patrones"}
        });

        agregarSeccion(doc, "FASE 2: Módulo ARCA - Facturación (Semanas 3-6)", "[CRÍTICO - Prioritario]");
        agregarTabla2Col(doc, new String[][]{
            {"Tarea", "Descripción"},
            {"2.1", "ABM CUITs con carga de certificados .p12"},
            {"2.2", "ServicioWSAA: firma CMS con BouncyCastle, obtiene TokenAcceso"},
            {"2.3", "TokenCache: guarda token + timestamp, renueva si <10 min para vencer"},
            {"2.4", "FECAESolicitar: armar请求, enviar a ARCA, obtener CAE"},
            {"2.5", "FECompConsultar: consultar estado de comprobantes"},
            {"2.6", "VentanaFacturacion: formulario emisión con pre-carga desde ELS"},
            {"2.7", "VentanaComprobantes: historial y búsqueda"},
            {"2.8", "VentanaConfigCertificados: gestión multi-CUIT"}
        });

        agregarSeccion(doc, "FASE 3: Migración Excel + Caja + Gastos (Semanas 7-10)", "");
        agregarTabla2Col(doc, new String[][]{
            {"Tarea", "Descripción"},
            {"3.1", "ReparBRC_Mysql.xlsx → BD propia (ódenes 2026)"},
            {"3.2", "Caja BRC.xlsx → caja_movimientos (cobros/pagos/ICBC)"},
            {"3.3", "Detalle gastos 2026.xls → gastos / categorias_gastos"},
            {"3.4", "VentanaCaja: registro y vista de movimientos"},
            {"3.5", "VentanaGastos: gestión mensual de gastos"},
            {"3.6", "Reportes de caja y gastos"}
        });

        agregarSeccion(doc, "FASE 4: Reportes + Email (Semanas 11-12)", "");
        agregarTabla2Col(doc, new String[][]{
            {"Tarea", "Descripción"},
            {"4.1", "JasperReports: Factura PDF con QR ARCA"},
            {"4.2", "JavaMail: SMTP configurable, adjuntar PDF"},
            {"4.3", "Reporte mensual de caja"},
            {"4.4", "Reporte anual de gastos"}
        });

        agregarSeccion(doc, "FASE 5: Integración ReparSoft (Semana 13)", "");
        agregarTabla2Col(doc, new String[][]{
            {"Tarea", "Descripción"},
            {"5.1", "Botón 'Facturar' en ReparSoft → abre FacturaSoft con ELS"},
            {"5.2", "Lectura de datos de reparación via ConexionReparsoft"},
            {"5.3", "Opcional: escribir nro factura en ReparSoft"}
        });

        agregarSeccion(doc, "RESPUESTAS CONFIRMADAS", "");
        agregarTabla(doc, new String[][]{
            {"Característica", "Valor"},
            {"Multi-empresa", "SÍ (varios CUITs con certificados propios)"},
            {"ARCA", "PRIORITARIO (Fase 2)"},
            {"Datos históricos", "Solo 2026"},
            {"PDF facturas", "JasperReports"},
            {"Email", "CRÍTICO (envío de facturas a clientes)"}
        });

        agregarSeccion(doc, "PREGUNTAS PENDIENTES", "");
        agregarNumerada(doc,
                "¿Entorno ARCA testing (wsfehomo) o producción (wsfe)?",
                "¿Certificados .p12 disponibles para cada CUIT?",
                "¿PDF formato A4 o ticket (78mm)?",
                "¿Login compartido con ReparSoft o independiente?",
                "¿Dirección de email SMTP configurada?");

        agregarSeccion(doc, "PRÓXIMOS PASOS", "");
        agregarNumerada(doc,
                "1. Responder las 5 preguntas pendientes",
                "2. Crear proyecto FacturaSoft/ en Eclipse",
                "3. Configurar conexión a facturacion_db",
                "4. Definir scripts SQL de tablas iniciales",
                "5. Iniciar con FASE 1");

        doc.close();
        System.out.println("✅ PDF generado: " + archivo);
    }

    private static void agregarTitulo(Document doc, String texto) throws Exception {
        Paragraph p = new Paragraph(texto, TITULO);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingBefore(10);
        doc.add(p);
    }

    private static void agregarSeccion(Document doc, String titulo, String contenido) throws Exception {
        Paragraph p = new Paragraph(titulo, SUBTITULO);
        p.setSpacingBefore(15);
        p.setSpacingAfter(5);
        doc.add(p);
        if (contenido != null && !contenido.isEmpty()) {
            Paragraph c = new Paragraph(contenido, NORMAL);
            c.setSpacingAfter(5);
            doc.add(c);
        }
    }

    private static void agregarSubSeccion(Document doc, String texto) throws Exception {
        Paragraph p = new Paragraph(texto, SUBTITULO2);
        p.setSpacingBefore(10);
        p.setSpacingAfter(5);
        doc.add(p);
    }

    private static void agregarLista(Document doc, String... items) throws Exception {
        for (String item : items) {
            Paragraph p = new Paragraph("• " + item, NORMAL);
            p.setSpacingBefore(2);
            p.setSpacingAfter(2);
            p.setIndentationLeft(15);
            doc.add(p);
        }
    }

    private static void agregarNumerada(Document doc, String... items) throws Exception {
        for (int i = 0; i < items.length; i++) {
            Paragraph p = new Paragraph((i + 1) + ". " + items[i], NORMAL);
            p.setSpacingBefore(2);
            p.setSpacingAfter(2);
            p.setIndentationLeft(15);
            doc.add(p);
        }
    }

    private static void agregarTabla(Document doc, String[][] datos) throws Exception {
        PdfPTable tabla = new PdfPTable(datos[0].length);
        tabla.setWidthPercentage(100);

        for (int i = 0; i < datos[0].length; i++) {
            tabla.setWidthPercentage(i == 0 ? 25 : 75);
        }

        PdfPCell header = new PdfPCell(new Phrase(datos[0][0], HEADER_TABLA));
        header.setBackgroundColor(new Color(0, 102, 153));
        header.setPadding(5);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(header);

        for (int col = 1; col < datos[0].length; col++) {
            PdfPCell h = new PdfPCell(new Phrase(datos[0][col], HEADER_TABLA));
            h.setBackgroundColor(new Color(0, 102, 153));
            h.setPadding(5);
            h.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(h);
        }

        for (int row = 1; row < datos.length; row++) {
            boolean fondo = (row % 2) == 0;
            Color bg = fondo ? new Color(240, 248, 255) : Color.WHITE;

            for (int col = 0; col < datos[row].length; col++) {
                PdfPCell cell = new PdfPCell(new Phrase(datos[row][col], CELDA));
                cell.setBackgroundColor(bg);
                cell.setPadding(4);
                if (col == 0) {
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else {
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                }
                tabla.addCell(cell);
            }
        }

        tabla.setSpacingBefore(5);
        tabla.setSpacingAfter(10);
        doc.add(tabla);
    }

    private static void agregarTabla2Col(Document doc, String[][] datos) throws Exception {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{2f, 8f});

        for (int row = 0; row < datos.length; row++) {
            boolean esHeader = row == 0;
            boolean fondo = esHeader || (row % 2) == 0;
            Color bg = esHeader ? new Color(0, 102, 153) :
                     (fondo ? new Color(240, 248, 255) : Color.WHITE);
            Font font = esHeader ? HEADER_TABLA : CELDA;
            Color textColor = esHeader ? Color.WHITE : Color.BLACK;

            PdfPCell c1 = new PdfPCell(new Phrase(datos[row][0], font));
            c1.setBackgroundColor(bg);
            c1.setPadding(4);
            tabla.addCell(c1);

            PdfPCell c2 = new PdfPCell(new Phrase(datos[row][1], font));
            c2.setBackgroundColor(bg);
            c2.setPadding(4);
            tabla.addCell(c2);
        }

        tabla.setSpacingBefore(5);
        tabla.setSpacingAfter(10);
        doc.add(tabla);
    }
}
