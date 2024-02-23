package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import dto.AgendaDTO;
import dto.PermisoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PermisoDAO;

public class PermisoDAOImpl implements PermisoDAO{
	
	private static final String insert = "INSERT INTO permisos(idPermiso,idRol,idPantalla) VALUES(0,?,?)";	
	private static final String delete = "DELETE FROM permisos WHERE idPermiso = ?";
	private static final String readall = "SELECT permisos.*,ph.nombre,IFNULL(pd.nombre,'Menu Principal') as padre FROM permisos inner join rol on rol.idRol = permisos.idRol inner join pantalla ph on ph.idPantalla = permisos.idPantalla  left join pantalla pd on pd.idPantalla = ph.idPantPadre"
			+ " WHERE permisos.idRol = ? ";
	
	private static final String readFaltantes = "SELECT ph.idPantalla,ph.nombre,IFNULL(pd.nombre,'Menu Principal') as padre FROM pantalla  ph left join pantalla pd on pd.idPantalla = ph.idPantPadre WHERE ph.idPantalla not in (SELECT DISTINCT permisos.idPantalla FROM permisos INNER JOIN rol ON rol.idRol = permisos.idRol where rol.idRol = ?) ";
	
	private static final String readallPadres = "SELECT permisos.*,pantalla.nombre FROM permisos inner join rol on rol.idRol = permisos.idRol inner join pantalla on pantalla.idPantalla = permisos.idPantalla "
			+ " WHERE permisos.idRol = ? AND IFNULL(pantalla.idPantPadre,0) = 0 ";
	private static final String readallHijos= "SELECT permisos.*,ph.nombre FROM permisos inner join rol on rol.idRol = permisos.idRol inner join pantalla ph on ph.idPantalla = permisos.idPantalla "
			+ " inner join pantalla pd on pd.idPantalla = ph.idPantPadre "
			+ " WHERE permisos.idRol = ? AND pd.nombre = ?";
	
	public static String ubicacion;
	private Conexion conexion;
	
	@SuppressWarnings("unused")
	public PermisoDAOImpl(String ubicacionBase) {
		
		final String insert = "INSERT INTO permisos(idPermiso,idRol,idPantalla) VALUES(0,?,?)";	
		final String delete = "DELETE FROM permisos WHERE idPermiso = ?";
		final String readall = "SELECT permisos.*,ph.nombre,IFNULL(pd.nombre,'Menu Principal') as padre FROM permisos inner join rol on rol.idRol = permisos.idRol inner join pantalla ph on ph.idPantalla = permisos.idPantalla  left join pantalla pd on pd.idPantalla = ph.idPantPadre"
				+ " WHERE permisos.idRol = ? ";
		
		final String readFaltantes = "SELECT ph.idPantalla,ph.nombre,IFNULL(pd.nombre,'Menu Principal') as padre FROM pantalla  ph left join pantalla pd on pd.idPantalla = ph.idPantPadre WHERE ph.idPantalla not in (SELECT DISTINCT permisos.idPantalla FROM permisos INNER JOIN rol ON rol.idRol = permisos.idRol where rol.idRol = ?) ";
		
		final String readallPadres = "SELECT permisos.*,pantalla.nombre FROM permisos inner join rol on rol.idRol = permisos.idRol inner join pantalla on pantalla.idPantalla = permisos.idPantalla "
				+ " WHERE permisos.idRol = ? AND IFNULL(pantalla.idPantPadre,0) = 0 ";
		final String readallHijos= "SELECT permisos.*,ph.nombre FROM permisos inner join rol on rol.idRol = permisos.idRol inner join pantalla ph on ph.idPantalla = permisos.idPantalla "
				+ " inner join pantalla pd on pd.idPantalla = ph.idPantPadre "
				+ " WHERE permisos.idRol = ? AND pd.nombre = ?";
		
		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);
		
		
		
	}
	
	


	@Override
	public boolean insert(PermisoDTO permiso) {
		PreparedStatement statement;
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, permiso.getIdRol());
			statement.setInt(2, permiso.getIdPantalla());


	
			if(statement.executeUpdate() > 0) //Si se ejecut� devuelvo true
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally //Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean delete(PermisoDTO permiso_a_eliminar) {
		// TODO Auto-generated method stub
				PreparedStatement statement;
				try 
				{
					statement = conexion.getSQLConexion().prepareStatement(delete);
					//statement.setInt(1, newAgenda.getIdAgenda());
					statement.setInt(1, permiso_a_eliminar.getIdPermiso());
					
					if(statement.executeUpdate() > 0) //Si se ejecut� devuelvo true
						return true;
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				finally //Se ejecuta siempre
				{
					conexion.cerrarConexion();
				}
				return false;
	}

	@Override
	public boolean edit(PermisoDTO permiso_a_editar) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	public List<PermisoDTO> readAll(Integer idRol) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PermisoDTO> obrasSociales = new ArrayList<PermisoDTO>();
		String query ="";
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall );
			statement.setInt(1, idRol);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				obrasSociales.add(new PermisoDTO(resultSet.getInt("idPermiso"),resultSet.getInt("idRol"),resultSet.getInt("idPantalla"),resultSet.getString("nombre"),resultSet.getString("padre")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally //Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return obrasSociales;	
	}

	@SuppressWarnings("unused")
	@Override
	public List<PermisoDTO> readAllPadres(Integer idRol) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PermisoDTO> obrasSociales = new ArrayList<PermisoDTO>();
		String query ="";
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readallPadres );
			statement.setInt(1, idRol);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				obrasSociales.add(new PermisoDTO(resultSet.getInt("idPermiso"),resultSet.getInt("idRol"),resultSet.getInt("idPantalla"),resultSet.getString("nombre")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally //Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return obrasSociales;	
	}

	@SuppressWarnings("unused")
	@Override
	public List<PermisoDTO> readAllHijos(Integer idRol, String nombrePantallaPadre) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PermisoDTO> obrasSociales = new ArrayList<PermisoDTO>();
		String query ="";
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readallHijos);
			statement.setInt(1, idRol);
			statement.setString(2, nombrePantallaPadre);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				obrasSociales.add(new PermisoDTO(resultSet.getInt("idPermiso"),resultSet.getInt("idRol"),resultSet.getInt("idPantalla"),resultSet.getString("nombre")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally //Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return obrasSociales;	
	}

	@SuppressWarnings("unused")
	@Override
	public List<PermisoDTO> readFaltantes(int idRol) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PermisoDTO> obrasSociales = new ArrayList<PermisoDTO>();
		String query ="";
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readFaltantes );
			statement.setInt(1, idRol);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				obrasSociales.add(new PermisoDTO(0,idRol,resultSet.getInt("idPantalla"),resultSet.getString("nombre"),resultSet.getString("padre")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally //Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return obrasSociales;	
	}

}
