package persistencia.dao.interfaz;

import java.util.List;

import javax.swing.JComboBox;

import dto.UsuarioDTO;

public interface UsuarioDAO
{
	public boolean insert(UsuarioDTO usuario);

	public boolean delete(UsuarioDTO usuario_a_eliminar);
	
	public boolean edit(UsuarioDTO usuario_a_editar);
	
	public List<UsuarioDTO> readAll();
	
	public UsuarioDTO obtenerMedico(int dni);

	public List<UsuarioDTO> obtenerMedicoXEspecialidad(int idEspecialidad);

	public List<UsuarioDTO> obtenerMedicoXEspecialidadXAgenda(Integer id, int selectedIndex);

	public UsuarioDTO obtenerMedicoPorID(int id);
	
	public List<UsuarioDTO> readAllXRol(int idRol);

	public UsuarioDTO readUsuLogin(String login, String pass);

	public void comboFiltroTecnicos(JComboBox comboFiltroTecnico);

	public int obtenerIDporNombre(String nombreTecnico);
}
