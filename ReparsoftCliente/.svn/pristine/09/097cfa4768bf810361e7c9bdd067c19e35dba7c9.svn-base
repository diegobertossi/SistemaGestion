package modelo;

import java.util.ArrayList;
import java.util.List;

import dto.PermisoDTO;
import dto.UsuarioDTO;
import persistencia.dao.interfaz.PermisoDAO;
import persistencia.dao.interfaz.UsuarioDAO;
import persistencia.dao.mysql.PermisoDAOImpl;
import persistencia.dao.mysql.UsuarioDAOImpl;

public class Permisos {

	
	private PermisoDAO permiso;
	private UsuarioDAO usuario;
	
	
	public Permisos()
	{
		permiso = new PermisoDAOImpl();
		usuario = new UsuarioDAOImpl();
	}
	
	public List<PermisoDTO> damePermisosPadres(Integer idRol)
	{
		return permiso.readAllPadres(idRol);
	}
	
	public List<PermisoDTO> damePermisosHijosD(Integer idRol,String nombre)
	{
		return permiso.readAllHijos(idRol,nombre);
	}
	
	public List<PermisoDTO> damePermisos(Integer idRol)
	{
		return permiso.readAll(idRol);
	}
	
	

	public UsuarioDTO dameUsuario(String login, String pass) {
		
		return usuario.readUsuLogin(login,pass);
	}

	public List<PermisoDTO> damePermisosFaltantes(int idRol) {
		// TODO Auto-generated method stub
		return permiso.readFaltantes(idRol);
	}

	public void agregarPermiso(PermisoDTO permisoDTO) {
		// TODO Auto-generated method stub
		permiso.insert(permisoDTO);
	}

	public void borrarPermiso(PermisoDTO permisoDTO) {
		// TODO Auto-generated method stub
		permiso.delete(permisoDTO);
	}

	public void actualizarUsuario(UsuarioDTO usu_login) {
		// TODO Auto-generated method stub
		usuario.edit(usu_login);
	}
}
