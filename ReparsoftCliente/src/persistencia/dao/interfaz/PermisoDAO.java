package persistencia.dao.interfaz;

import java.util.List;

import dto.PermisoDTO;


public interface PermisoDAO {

	public boolean insert(PermisoDTO permiso);

	public boolean delete(PermisoDTO permiso_a_eliminar);
	
	public boolean edit(PermisoDTO permiso_a_editar);
	
	public List<PermisoDTO> readAllHijos(Integer idRol,String nombrePantallaPadre);
	
	public List<PermisoDTO> readAllPadres(Integer idRol);

	List<PermisoDTO> readAll(Integer idRol);

	public List<PermisoDTO> readFaltantes(int idRol);
	
}
