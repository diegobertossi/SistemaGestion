package persistencia.dao.interfaz;

import java.util.List;

import dto.PantallaDTO;

public interface PantallaDAO {

	public boolean insert(PantallaDTO permiso);

	public boolean delete(PantallaDTO permiso_a_eliminar);
	
	public boolean edit(PantallaDTO permiso_a_editar);
	
	public List<PantallaDTO> readAll(String nombrePantallaPadre);
	
	public List<PantallaDTO> readAllPadres();
	
}
