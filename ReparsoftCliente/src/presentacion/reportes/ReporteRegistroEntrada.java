package presentacion.reportes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import net.sf.jasperreports.view.JasperViewer;
import dto.RegistroEntradaReporteDTO;
import modelo.Agenda;

public class ReporteRegistroEntrada {
	private static final ConcurrentHashMap<String, JasperReport> cache = new ConcurrentHashMap<>();

	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;

	private String reportFileName = "reportes\\ReporteRegistroEntrada2.jasper";
	private String nombreArchivoPDF = "";
	private String outFileName = "";
	private Agenda agenda;
	private int ELS;
	private long tiempoApertura = 0;

	public ReporteRegistroEntrada(RegistroEntradaReporteDTO reparacion, List<RegistroEntradaReporteDTO> reparaciones,
			Agenda agenda) {
		java.util.Date fecha = new Date();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
		String fechas = "";
		try {
			fecha = dateFormat1.parse(reparacion.getFecha_Entrada());
			fechas = dateFormat2.format(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ELS = reparacion.getELS();
		this.agenda = agenda;

		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fechaEntrada", fechas);

		try {
			this.reporte = getCachedReport(reportFileName);
			this.reporteLleno = JasperFillManager.fillReport(this.reporte,
					parametersMap, new JRBeanCollectionDataSource(reparaciones, false));
		} catch (JRException ex) {
			ex.printStackTrace();
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

	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.tiempoApertura = System.currentTimeMillis();
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
		return this.tiempoApertura;
	}

	public void guardar() {
		nombreArchivoPDF = "ELS_" + ELS + ".pdf";

		if (agenda.getUbicacionBase().compareTo("Bariloche") == 0) {
			outFileName = "F:\\ELS\\Bariloche\\Administracion\\Sistema\\Registros de Ingreso\\" + nombreArchivoPDF;
		} else if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0) {
			outFileName = "F:\\ELS\\Administracion\\Sistema\\Registros de Ingreso\\" + nombreArchivoPDF;
		}

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(reporteLleno));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFileName));

		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}