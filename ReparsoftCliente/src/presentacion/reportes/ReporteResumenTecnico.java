package presentacion.reportes;

import java.io.File;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

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
import dto.RegistroPresupuestoDTO;
import dto.RegistroResumenTecnicoDTO;

@SuppressWarnings("deprecation")
public class ReporteResumenTecnico {
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
	public ReporteResumenTecnico(RegistroResumenTecnicoDTO resumeDeDatos, List<RegistroResumenTecnicoDTO> resumen)

	{
		reportFileName = "reportes\\ResumenTecnico.jasper";
//		ELS = reparacion.getELS();
//		Cliente = reparacion.getCliente();

		try {

			ReporteResumenTecnico.reporte = (JasperReport) JRLoader.loadObjectFromFile(reportFileName);
			ReporteResumenTecnico.reporteLleno = JasperFillManager.fillReport(ReporteResumenTecnico.reporte,
					parametersMap,

					new JRBeanCollectionDataSource(resumen, false));

		} catch (JRException ex) {
			ex.printStackTrace();
		}

	}

	public void mostrar() {

		ReporteResumenTecnico.reporteViewer = new JasperViewer(ReporteResumenTecnico.reporteLleno, false);
		ReporteResumenTecnico.reporteViewer.setVisible(true);
	}

	
}