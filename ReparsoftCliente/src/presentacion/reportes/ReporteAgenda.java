package presentacion.reportes;

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
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import dto.UsuarioDTO;
import persistencia.dao.mysql.LogDAO;

public class ReporteAgenda {
	private static final ConcurrentHashMap<String, JasperReport> cache = new ConcurrentHashMap<>();

	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;

	public ReporteAgenda(List<UsuarioDTO> personas) {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		try {
			this.reporte = getCachedReport("reportes\\ReporteAgenda.jasper");
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap,
					new JRBeanCollectionDataSource(personas));
		} catch (JRException ex) {
			LogDAO.error("Error al llenar reporte de agenda", ex);
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
			getCachedReport("reportes\\ReporteAgenda.jasper");
		} catch (JRException e) {
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
}