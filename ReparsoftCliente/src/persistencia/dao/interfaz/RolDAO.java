package persistencia.dao.interfaz;

import java.util.List;

import dto.RolDTO;

public interface RolDAO
{
	public boolean insert(RolDTO rol);

	public boolean delete(RolDTO rol_a_eliminar);
	
	public boolean edit(RolDTO rol_a_editar);
	
	public List<RolDTO> readAll();

	public String readAllxid(int id);
}
