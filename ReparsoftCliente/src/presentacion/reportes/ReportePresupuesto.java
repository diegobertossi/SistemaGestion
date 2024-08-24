package presentacion.reportes;

import java.io.File;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
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

	// Recibe la lista de PRESUPUESTOS para armar el reporte
	public ReportePresupuesto(RegistroPresupuestoDTO reparacion, List<RegistroPresupuestoDTO> Presupuesto)

	{
		reportFileName = "reportes\\Presupuesto.jasper";
		ELS = reparacion.getELS();
		Cliente = reparacion.getCliente();
		
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

	@SuppressWarnings("rawtypes")
	public void guardar() {

		nombreArchivoPDF = "Presupuesto ELS_" + ELS + "_" + Cliente + ".pdf";
		//nombreArchivoPDF = "Presupuesto ELS_";

		File fRutaE = new File("E:\\Sistema\\ELS\\Bariloche\\Administracion\\Presupuestos PDF");
		File fRutaD = new File("D:\\Sistema\\ELS\\Bariloche\\Administracion\\Presupuestos PDF");
		File fRutaF = new File("F:\\ELS\\Bariloche\\Administracion\\Sistema\\Presupuestos PDF");

		if (fRutaD.isDirectory())
			outFileName = "D:\\Sistema\\ELS\\Bariloche\\Administracion\\Presupuestos PDF\\" + nombreArchivoPDF;
		else if (fRutaE.isDirectory())
			outFileName = "E:\\Sistema\\ELS\\Bariloche\\Administracion\\Presupuestos PDF\\" + nombreArchivoPDF;
		else if (fRutaF.isDirectory())
			//outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Presupuestos PDF\\" + nombreArchivoPDF;
			outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Presupuestos PDF\\" + nombreArchivoPDF;

//		JRExporter exporter = new JRPdfExporter();
//		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, reporteLleno);
		
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}