package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RolDTO;
import dto.UsuarioDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RolDAO;

public class RolDAOImpl implements RolDAO {
	private static final String insert = "INSERT INTO rol(idRol, nombre) VALUES(?, ?)";
	private static final String delete = "DELETE FROM rol WHERE idRol = ?";
	private static final String readall = "SELECT * FROM rol";
	private static final String readallxid = "SELECT nombre FROM rol WHERE idRol = ?";
	private static final Conexion conexion = Conexion.getConexion();

	public boolean insert(RolDTO rol) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, rol.getIdRol());
			statement.setString(2, rol.getNombre());

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	public boolean delete(RolDTO rol_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(rol_a_eliminar.getIdRol()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	public boolean edit(RolDTO rol_a_editar) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("UPDATE rol SET idRol='" + rol_a_editar.getIdRol() + "' , " + "nombre = '"
							+ rol_a_editar.getNombre() + "'" + "WHERE idRol = " + rol_a_editar.getIdRol() + "");

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	public List<RolDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<RolDTO> roles = new ArrayList<RolDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				roles.add(new RolDTO(resultSet.getInt("idRol"), resultSet.getString("nombre")));
				System.out.println(resultSet.getInt("idRol") + " " + resultSet.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return roles;
	}

	@Override
	public String readAllxid(int id) {
		
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String nombre = "";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxid);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				nombre = resultSet.getString("nombre");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return nombre;
		
		
		

	}

}
