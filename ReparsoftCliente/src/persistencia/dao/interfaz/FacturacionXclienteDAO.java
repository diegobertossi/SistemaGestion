package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import dto.FacturacionXclienteDTO;
import dto.RepuestosDTO;


public interface FacturacionXclienteDAO 
{
	
	
	
	public List<FacturacionXclienteDTO> readAll(int anio);
	

	
	
}
