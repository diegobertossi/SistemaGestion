package presentacion.reportes;

import java.io.File;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.conexion.Conexion;
import dto.RegistroPresupuestoDTO;
import dto.RegistroEntradaReporteDTO;
import dto.ReparacionDTO;
//import com.lowagie.text.pdf.FopGlyphProcessor;


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

		try {

			ReportePresupuesto.reporte = (JasperReport) JRLoader.loadObjectFromFile(reportFileName);
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
			outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Presupuestos PDF\\" + nombreArchivoPDF;

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, reporteLleno);
		


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