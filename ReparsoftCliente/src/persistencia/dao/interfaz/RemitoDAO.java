package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import dto.RemitoDTO;


public interface RemitoDAO 
{
	
	public boolean insert(RemitoDTO Remito);
	
	public boolean edit(RemitoDTO Remito);

	public boolean delete(int ID_remito);
	
	public List<RemitoDTO> readAll();
	
	public void ListarUbicacion(JComboBox<?> box);

	public int obtenerNumeroRemito(int codigo);
	
	public int obtenerIDRemito();

	public void ListarRemitoPorUbicacion(JComboBox<?> box, int id);

	public int idRemitoXubicacionNumero(int iDubicacion, int numero);
	

	

}
