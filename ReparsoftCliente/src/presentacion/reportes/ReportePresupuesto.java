package presentacion.reportes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.fonts.FontExtensionsRegistry;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import net.sf.jasperreports.engine.JasperReportsContext;



import dto.RegistroPresupuestoDTO;
import modelo.Agenda;


@SuppressWarnings("deprecation")
public class ReportePresupuesto {
	private static JasperReport reporte;
	private static JasperViewer reporteViewer;
	private static JasperPrint reporteLleno;
	private Map<String, Object> parametersMap = new HashMap<String, Object>();
	private String reportFileName = "";
	private String nombreArchivoPDF = "";
	private String outFileName = "";

	private int ELS;
	private String Cliente = "";
	private Agenda agenda;

	// Recibe la lista de PRESUPUESTOS para armar el reporte
	public ReportePresupuesto(RegistroPresupuestoDTO reparacion, List<RegistroPresupuestoDTO> Presupuesto, Agenda agenda)

	{
		reportFileName = "reportes\\Presupuesto.jasper";
		ELS = reparacion.getELS();
		Cliente = reparacion.getCliente();
		this.agenda = agenda;
		
		JTextField imagePathField = new JTextField(30);
        imagePathField.setEditable(false);
        JTextField imagePathField2 = new JTextField(30);
        imagePathField2.setEditable(false);
        JTextField imagePathField3 = new JTextField(30);
        imagePathField3.setEditable(false);
        JTextField imagePathField4 = new JTextField(30);
        imagePathField4.setEditable(false);
        JTextField imagePathField5 = new JTextField(30);
        imagePathField5.setEditable(false);
        JTextField imagePathField6 = new JTextField(30);
        imagePathField6.setEditable(false);

		try {
			
			//imagePathField.setText("img\\anterior.png");
			ReportePresupuesto.reporte = (JasperReport) JRLoader.loadObjectFromFile(reportFileName);
			parametersMap.put("imagePath", imagePathField.getText().isEmpty()? null : imagePathField.getText());
			parametersMap.put("imagePath2", imagePathField2.getText().isEmpty()? null : imagePathField2.getText());
			parametersMap.put("imagePath3", imagePathField3.getText().isEmpty()? null : imagePathField3.getText());
			parametersMap.put("imagePath4", imagePathField4.getText().isEmpty()? null : imagePathField4.getText());
			parametersMap.put("imagePath5", imagePathField5.getText().isEmpty()? null : imagePathField5.getText());
			parametersMap.put("imagePath6", imagePathField6.getText().isEmpty()? null : imagePathField6.getText());
			ReportePresupuesto.reporteLleno = JasperFillManager.fillReport(ReportePresupuesto.reporte, parametersMap,

					new JRBeanCollectionDataSource(Presupuesto, false));

		} catch (JRException ex) {
			ex.printStackTrace();
		}

	}

	public void mostrar() {

		ReportePresupuesto.reporteViewer = new JasperViewer(ReportePresupuesto.reporteLleno, false);
		ReportePresupuesto.reporteViewer.setVisible(true);
	}

	/**
	 * Método que verifica si un archivo está en uso/bloqueado
	 * @param archivo El archivo a verificar
	 * @return true si el archivo está en uso, false si está disponible
	 */
	private boolean archivoEnUso(File archivo) {
		if (!archivo.exists()) {
			return false; // Si no existe, no está en uso
		}
		
		// Intentar abrir el archivo en modo escritura
		try (FileOutputStream fos = new FileOutputStream(archivo, true)) {
			// Si se puede abrir, el archivo NO está en uso
			return false;
		} catch (IOException e) {
			// Si falla, el archivo ESTÁ en uso
			return true;
		}
	}

	@SuppressWarnings("rawtypes")
	public void guardar() {

		nombreArchivoPDF = "Presupuesto ELS_" + ELS + "_" + Cliente + ".pdf";

		
		if (agenda.getUbicacionBase().compareTo("Bariloche") == 0) {

			outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Presupuestos PDF\\" + nombreArchivoPDF;
			

		} else if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0) {

			outFileName = "F:\\ELS\\Administracion\\Sistema\\Presupuestos PDF\\" + nombreArchivoPDF;

		}
		
		// VERIFICAR ANTES si el archivo está en uso
		File archivoPDF = new File(outFileName);
		if (archivoEnUso(archivoPDF)) {
			JOptionPane.showMessageDialog(null, 
				"El archivo PDF está abierto en otro programa.\n" +
				"Por favor, cierre el archivo:\n'" + nombreArchivoPDF + "'\ne intente nuevamente.", 
				"Archivo en Uso", 
				JOptionPane.WARNING_MESSAGE);
			return; // Salir del método sin generar el reporte
		}
		
				
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFileName));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		
		
		// Configurar la incrustación de fuentes y otros parámetros
		SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
		reportConfig.setSizePageToContent(true);
		reportConfig.setForceLineBreakPolicy(false);

		// Si quieres asegurarte de que las fuentes se incrusten
		exporter.setConfiguration(reportConfig);


//		// Agregar la fuente al compilador
//		JasperReportsContext context = new DefaultJasperReportsContext();
//		FontExtensionsRegistry registry = context.getExtensionsRegistry();
//		registry.addFont(fontname, true, inputStream); // fontname es el nombre de la fuente, inputStream es el stream de la fuente

		

		try {

			//JasperExportManager.exportReportToPdfFile(reporteLleno, outFileName);
			exporter.exportReport();
			
			Object mje = "Se ha generado el: "+ nombreArchivoPDF;
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		
		} catch (JRException e) {
			// Si aún así ocurre un error
			JOptionPane.showMessageDialog(null, 
				"Error al exportar el reporte: " + e.getMessage(), 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}