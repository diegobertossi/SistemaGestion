package presentacion.reportes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.SwingUtilities;

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
import persistencia.dao.mysql.LogDAO;
import util.RutasSistema;

@SuppressWarnings("deprecation")
public class ReporteRemitoSalida {
	private static final ConcurrentHashMap<String, JasperReport> cache = new ConcurrentHashMap<>();

	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
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

	public ReporteRemitoSalida(RemitoDTO remito, List<RemitoDTO> Remito, Agenda agenda) {
		CodigoRemito = remito.getCodigoUbicacion();

		if (CodigoRemito == 2 || CodigoRemito == 5 || CodigoRemito == 6 || CodigoRemito == 7) {
			reportFileName = "reportes\\RemitoPreImpreso.jasper";
		} else {
			reportFileName = "reportes\\RemitoComun.jasper";
		}

		switch (CodigoRemito) {
		case 2: ubicacionRemito = "MDP"; break;
		case 5: ubicacionRemito = "CABA"; break;
		case 6: ubicacionRemito = "BRC"; break;
		case 7: ubicacionRemito = "MDP Avellaneda"; break;
		case 1000: ubicacionRemito = "COMUN CABA"; break;
		case 2000: ubicacionRemito = "COMUN MDP"; break;
		case 3000: ubicacionRemito = "COMUN BRC"; break;
		}

		this.agenda = agenda;
		NombreCliente = remito.getCliente();
		DireccionCliente = remito.getDomicilio();
		CuitCliente = remito.getCuit();
		parametersMap.put("cuit", CuitCliente);
		parametersMap.put("domicilio", DireccionCliente);
		NumeroRemito = remito.getRemitoConformado().toString().split("-")[1].trim();
		fecha = new java.text.SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES")).format(new java.util.Date());

		try {
			this.reporte = getCachedReport(reportFileName);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
					new JRBeanCollectionDataSource(Remito, false));
		} catch (JRException ex) {
			LogDAO.error("Error al llenar reporte de remito: " + NumeroRemito, ex);
		}
	}

	private static JasperReport getCachedReport(String path) throws JRException {
		JasperReport report = cache.get(path);
		if (report == null) {
			report = (JasperReport) JRLoader.loadObjectFromFile(path);
			cache.put(path, report);
		}
		return report;
	}

	public static void precargar() {
		String[] paths = { "reportes\\RemitoPreImpreso.jasper", "reportes\\RemitoComun.jasper" };
		for (String path : paths) {
			try {
				JasperReport reporte = getCachedReport(path);
				JasperFillManager.fillReport(reporte, new HashMap<String, Object>(),
						new JRBeanCollectionDataSource(new ArrayList<Object>(), false));
			} catch (JRException e) {
			}
		}
	}

	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);

		SwingUtilities.invokeLater(() -> {
			reporteViewer.toFront();
			reporteViewer.repaint();
			reporteViewer.requestFocus();
		});
	}

	public boolean isViewerVisible() {
		return this.reporteViewer != null && this.reporteViewer.isVisible();
	}

	public long getTiempoApertura() {
		return this.reporteViewer != null && this.reporteViewer.isVisible() ? System.currentTimeMillis() : 0;
	}

	@SuppressWarnings("rawtypes")
	public boolean guardar() {
		nombreArchivoPDF = NumeroRemito + "-" + ubicacionRemito + "_" + NombreCliente + ".pdf";
		String ubicacionSistema = agenda.getUbicacionBase();

		if (ubicacionSistema.compareTo("Bariloche") == 0) {
			if (reportFileName.compareTo("reportes\\RemitoComun.jasper") == 0) {
				outFileName = RutasSistema.adaptar("F:\\ELS\\Bariloche\\Administracion\\Sistema\\Remitos PDF\\Remitos Comunes\\") + nombreArchivoPDF;
			}
			if (reportFileName.compareTo("reportes\\RemitoPreImpreso.jasper") == 0) {
				outFileName = RutasSistema.adaptar("F:\\ELS\\Bariloche\\Administracion\\Sistema\\Remitos PDF\\Remitos PreImpresos\\") + nombreArchivoPDF;
			}
		} else if (ubicacionSistema.compareTo("Buenos Aires") == 0) {
			if (reportFileName.compareTo("reportes\\RemitoComun.jasper") == 0) {
				outFileName = RutasSistema.adaptar("F:\\ELS\\Administracion\\Sistema\\Remitos PDF\\Remitos Comunes\\") + nombreArchivoPDF;
			}
			if (reportFileName.compareTo("reportes\\RemitoPreImpreso.jasper") == 0) {
				outFileName = RutasSistema.adaptar("F:\\ELS\\Administracion\\Sistema\\Remitos PDF\\Remitos PreImpresos\\") + nombreArchivoPDF;
			}
		}

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFileName));

		try {
			exporter.exportReport();
			return true;
		} catch (JRException e) {
			LogDAO.error("Error al exportar remito PDF: " + nombreArchivoPDF, e);
			return false;
		}
	}

	public String getPdfGuardado() {
		return outFileName;
	}
}