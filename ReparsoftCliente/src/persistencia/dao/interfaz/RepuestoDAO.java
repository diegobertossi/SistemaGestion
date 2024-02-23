package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import dto.RepuestosDTO;


public interface RepuestoDAO 
{
	
	public boolean insert(RepuestosDTO Repuesto);

	public boolean delete(RepuestosDTO Repuesto_a_eliminar);
	
	public List<RepuestosDTO> readAll();
	
	public List<RepuestosDTO> obtenerRepuestosXels(Integer i);
	
	public boolean edit(RepuestosDTO Repuesto);

	public void ListarRepuestos(JComboBox<?> box);

	public void ListarRepuestosReemplazo(JComboBox<?> comboCompReemplazo);
	
	
}
