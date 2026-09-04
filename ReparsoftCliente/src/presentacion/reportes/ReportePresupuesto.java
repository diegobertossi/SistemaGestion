package presentacion.reportes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import dto.RegistroPresupuestoDTO;
import modelo.Agenda;
import persistencia.dao.mysql.LogDAO;
import util.RutasSistema;

@SuppressWarnings("deprecation")
public class ReportePresupuesto {
	private static final ConcurrentHashMap<String, JasperReport> cache = new ConcurrentHashMap<>();

	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
	private Map<String, Object> parametersMap = new HashMap<String, Object>();
	private String reportFileName = "reportes\\Presupuesto.jasper";
	private String nombreArchivoPDF = "";
	private String outFileName = "";
	private Agenda agenda;

	private int ELS;
	private String Cliente = "";

	public ReportePresupuesto(RegistroPresupuestoDTO reparacion, List<RegistroPresupuestoDTO> Presupuesto, Agenda agenda) {
		ELS = reparacion.getELS();
		Cliente = reparacion.getCliente();
		this.agenda = agenda;

		try {
			this.reporte = getCachedReport(reportFileName);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
					new JRBeanCollectionDataSource(Presupuesto, false));
		} catch (JRException ex) {
			LogDAO.error("Error al llenar reporte de presupuesto ELS_" + ELS, ex);
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
		try {
			JasperReport reporte = getCachedReport("reportes\\Presupuesto.jasper");
			JasperFillManager.fillReport(reporte, new HashMap<String, Object>(),
					new JRBeanCollectionDataSource(new java.util.ArrayList<Object>(), false));
		} catch (JRException e) {
		}
	}

	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		VisorReportes.configurar(this.reporteViewer, "Presupuesto ELS " + ELS + " - " + Cliente);
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

	private boolean archivoEnUso(File archivo) {
		if (!archivo.exists()) return false;
		try (FileOutputStream fos = new FileOutputStream(archivo, true)) {
			return false;
		} catch (IOException e) {
			return true;
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean guardar() {
		nombreArchivoPDF = "Presupuesto ELS_" + ELS + "_" + Cliente + ".pdf";

		if (agenda.getUbicacionBase().compareTo("Bariloche") == 0) {
			outFileName = RutasSistema.adaptar("F:\\ELS\\Bariloche\\Administracion\\Sistema\\Presupuestos PDF\\") + nombreArchivoPDF;
		} else if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0) {
			outFileName = RutasSistema.adaptar("F:\\ELS\\Administracion\\Sistema\\Presupuestos PDF\\") + nombreArchivoPDF;
		}

		File archivoPDF = new File(outFileName);
		if (archivoEnUso(archivoPDF)) {
			JOptionPane.showMessageDialog(null,
				"El archivo PDF est\u00e1 abierto en otro programa.\n" +
				"Por favor, cierre el archivo:\n'" + nombreArchivoPDF + "'\ne intente nuevamente.",
				"Archivo en Uso", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFileName));
		SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
		reportConfig.setSizePageToContent(true);
		reportConfig.setForceLineBreakPolicy(false);
		exporter.setConfiguration(reportConfig);

		try {
			exporter.exportReport();
			return true;
		} catch (JRException e) {
			JOptionPane.showMessageDialog(null,
				"Error al exportar el reporte: " + e.getMessage(),
				"Error", JOptionPane.ERROR_MESSAGE);
			LogDAO.error("Error al exportar presupuesto PDF", e);
			return false;
		}
	}

	public String getPdfGuardado() {
		return outFileName;
	}
}