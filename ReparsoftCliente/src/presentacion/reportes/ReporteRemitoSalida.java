package presentacion.reportes;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import dto.RemitoDTO;

@SuppressWarnings("deprecation")
public class ReporteRemitoSalida {
	private static JasperReport reporte;
	private static JasperViewer reporteViewer;
	private static JasperPrint reporteLleno;
	private Map<String, Object> parametersMap = new HashMap<String, Object>();
	private String reportFileName = "";
	private String nombreArchivoPDF = "";
	private String outFileName = "";

	private String Cliente = "";
	private String NumeroRemito = "";
	private String fecha = "";

	// Recibe la lista de PRESUPUESTOS para armar el reporte
	public ReporteRemitoSalida(RemitoDTO remito, List<RemitoDTO> Remito)

	{
		reportFileName = "reportes\\RemitoComun.jasper";

		Cliente = remito.getCliente();
		NumeroRemito =remito.getRemitoConformado().toString();
		fecha = new java.text.SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES")).format(new java.util.Date());

		try {

			ReporteRemitoSalida.reporte = (JasperReport) JRLoader.loadObjectFromFile(reportFileName);
			ReporteRemitoSalida.reporteLleno = JasperFillManager.fillReport(ReporteRemitoSalida.reporte, parametersMap,

					new JRBeanCollectionDataSource(Remito, false));

		} catch (JRException ex) {
			ex.printStackTrace();
		}

	}

	public void mostrar() {

		ReporteRemitoSalida.reporteViewer = new JasperViewer(ReporteRemitoSalida.reporteLleno, false);
		ReporteRemitoSalida.reporteViewer.setVisible(true);
	}

	@SuppressWarnings("rawtypes")
	public void guardar() {

		nombreArchivoPDF = NumeroRemito + "_" + Cliente + "_" + fecha + ".pdf";

		File fRutaE = new File("E:\\Sistema\\ELS\\Bariloche\\Administracion\\Remitos PDF\\Remitos Comunes");
		File fRutaD = new File("D:\\Sistema\\ELS\\Bariloche\\Administracion\\Remitos PDF\\Remitos Comunes");
		File fRutaF = new File("F:\\ELS\\Bariloche\\Administracion\\Sistema\\Remitos PDF\\Remitos Comunes");

		if (fRutaD.isDirectory())
			outFileName = "D:\\Sistema\\ELS\\Bariloche\\Administracion\\Remitos PDF\\Remitos Comunes\\"
					+ nombreArchivoPDF;
		else if (fRutaE.isDirectory())
			outFileName = "E:\\Sistema\\ELS\\Bariloche\\Administracion\\Remitos PDF\\Remitos Comunes\\"
					+ nombreArchivoPDF;
		else if (fRutaF.isDirectory())
			outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Remitos PDF\\Remitos Comunes\\"
					+ nombreArchivoPDF;

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, reporteLleno);

		try {

			exporter.exportReport();


		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}