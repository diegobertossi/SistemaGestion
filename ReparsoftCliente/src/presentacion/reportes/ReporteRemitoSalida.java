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
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import dto.RemitoDTO;
import modelo.Agenda;

@SuppressWarnings("deprecation")
public class ReporteRemitoSalida {
	private static JasperReport reporte;
	private static JasperViewer reporteViewer;
	private static JasperPrint reporteLleno;
	private Map<String, Object> parametersMap = new HashMap<String, Object>();
	private String reportFileName = "";
	private String nombreArchivoPDF = "";
	private String outFileName = "";

	private String NombreCliente = "";
	private String DireccionCliente = "";
	private String CuitCliente = "";
	private String NumeroRemito = "";
	private String ubicacionRemito = "";
	private int CodigoRemito;
	private String fecha = "";
	private Agenda agenda;

	// Recibe la lista de PRESUPUESTOS para armar el reporte
	public ReporteRemitoSalida(RemitoDTO remito, List<RemitoDTO> Remito, Agenda agenda)

	{

		CodigoRemito = remito.getCodigoUbicacion();

		if (CodigoRemito == 2 || CodigoRemito == 5 || CodigoRemito == 6 || CodigoRemito == 7) {

			reportFileName = "reportes\\RemitoPreImpreso.jasper";

		} else {

			reportFileName = "reportes\\RemitoComun.jasper";

		}

		switch (CodigoRemito) {
		case 2:
			ubicacionRemito = "MDP";
			break;
		case 5:
			ubicacionRemito = "CABA";
			break;
		case 6:
			ubicacionRemito = "BRC";
			break;
		case 7:
			ubicacionRemito = "MDP Avellaneda";
			break;
		case 1000:
			ubicacionRemito = "COMUN CABA";
			break;
		case 2000:
			ubicacionRemito = "COMUN MDP";
			break;
		case 3000:
			ubicacionRemito = "COMUN BRC";
			break;

		default:
			break;
		}

		this.agenda = agenda;

		NombreCliente = remito.getCliente();
		DireccionCliente = remito.getDomicilio();
		CuitCliente = remito.getCuit();

		parametersMap.put("cuit", CuitCliente); // Usa el valor del CUIT del cliente
		parametersMap.put("domicilio", DireccionCliente); // Usa el valor del domicilio del cliente

		NumeroRemito = remito.getRemitoConformado().toString().split("-")[1].trim();;


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

		nombreArchivoPDF = NumeroRemito + "-" + ubicacionRemito + "_" + NombreCliente + ".pdf";
		String ubicacionSistema = agenda.getUbicacionBase();

		if (ubicacionSistema.compareTo("Bariloche") == 0) {

			if (reportFileName.compareTo("reportes\\RemitoComun.jasper") == 0) {

				outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Remitos PDF\\Remitos Comunes\\"
						+ nombreArchivoPDF;
			}
			if (reportFileName.compareTo("reportes\\RemitoPreImpreso.jasper") == 0) {

				outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Remitos PDF\\Remitos PreImpresos\\"
						+ nombreArchivoPDF;

			}

		} else if (ubicacionSistema.compareTo("Buenos Aires") == 0) {

			if (reportFileName.compareTo("reportes\\RemitoComun.jasper") == 0) {

				outFileName = "F:\\ELS\\Administracion\\Sistema\\Remitos PDF\\Remitos Comunes\\" + nombreArchivoPDF;
			}
			if (reportFileName.compareTo("reportes\\RemitoPreImpreso.jasper") == 0) {

				outFileName = "F:\\ELS\\Administracion\\Sistema\\Remitos PDF\\Remitos PreImpresos\\" + nombreArchivoPDF;

			}

		}

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFileName));

		try {

			exporter.exportReport();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}