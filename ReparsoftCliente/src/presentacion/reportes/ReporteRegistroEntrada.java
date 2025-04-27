package presentacion.reportes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import dto.RegistroEntradaReporteDTO;


public class ReporteRegistroEntrada
{
	private static JasperReport reporte;
	private static JasperViewer reporteViewer;
	private static JasperPrint	reporteLleno;
	
	//Recibe la lista de personas para armar el reporte
    public ReporteRegistroEntrada(RegistroEntradaReporteDTO reparacion,  List<RegistroEntradaReporteDTO> reparaciones)
   
    {
    	
    	
    	//Hardcodeado
    	
    	java.util.Date fecha = new Date();
    	SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
		String fechas ="";
		try {
			fecha = dateFormat1.parse(reparacion.getFecha_Entrada());

		
		fechas = dateFormat2.format(fecha);
    	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    	
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("fechaEntrada", fechas);
		//parametersMap.put("cliente", reparacion.getCliente());
    	
		try		{
    		
			ReporteRegistroEntrada.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reportes\\ReporteRegistroEntrada2.jasper" );
			ReporteRegistroEntrada.reporteLleno = JasperFillManager.fillReport(ReporteRegistroEntrada.reporte, parametersMap, new JRBeanCollectionDataSource(reparaciones, false));
		}
		catch( JRException ex ) 
		{
			ex.printStackTrace();
		}
    }       
    
    public void mostrar()
	{
    	
    	
    
    	
    	ReporteRegistroEntrada.reporteViewer = new JasperViewer(ReporteRegistroEntrada.reporteLleno,false);
    	ReporteRegistroEntrada.reporteViewer.setVisible(true);
    	
	}
   
}	