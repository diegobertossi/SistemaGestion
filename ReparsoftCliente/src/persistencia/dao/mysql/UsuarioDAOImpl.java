package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import dto.ReparacionDTO;
import dto.UsuarioDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.UsuarioDAO;

public class UsuarioDAOImpl implements UsuarioDAO {
	private static final String insert = "INSERT INTO usuario(idUsuario, idRol, dni, nombre, apellido, telefono, email,login,pass) VALUES(?, ?, ?, ?, ?, ?, ?,?,?)";
	private static final String delete = "DELETE FROM usuario WHERE idUsuario = ?";
	private static final String readall = "SELECT * FROM usuario where idRol !=1 ";
	private static final String readLogin = "SELECT * FROM usuario where login = ? AND pass = ? ";
	private static final String readallTecnico = "SELECT usuario.nombre FROM usuario group by usuario.nombre";
	private static final String IDporNombre = "Select idUsuario from usuario where nombre =?";
	private static final Conexion conexion = Conexion.getConexion();

	public boolean insert(UsuarioDTO user) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, user.getIdUsuario());
			statement.setInt(2, user.getIdRol());
			statement.setInt(3, user.getDni());
			statement.setString(4, user.getNombre());
			statement.setString(5, user.getApellido());
			statement.setString(6, user.getTelefono());
			statement.setString(7, user.getEmail());
			statement.setString(8, user.getLogin());
			statement.setString(9, user.getPass());

			if (statement.executeUpdate() > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	public boolean delete(UsuarioDTO user_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(user_a_eliminar.getIdUsuario()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	public boolean edit(UsuarioDTO user_a_editar) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("UPDATE usuario SET idUsuario = '" + user_a_editar.getIdUsuario() + "' , "
							+ "idRol = '" + user_a_editar.getIdRol() + "' ," + "dni = '" + user_a_editar.getDni()
							+ "' ," + "nombre = '" + user_a_editar.getNombre() + "' ," + "apellido = '"
							+ user_a_editar.getApellido() + "' ," + "telefono = '" + user_a_editar.getTelefono() + "' ,"
							+ "email = '" + user_a_editar.getEmail() + "'  ," + " login = '" + user_a_editar.getLogin()
							+ "' ," + " pass = '" + user_a_editar.getPass() + "' " + " WHERE idUsuario = "
							+ user_a_editar.getIdUsuario() + "");

			if (statement.executeUpdate() > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	public List<UsuarioDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				usuarios.add(new UsuarioDTO(resultSet.getInt("idUsuario"), resultSet.getInt("idRol"),
						resultSet.getInt("dni"), resultSet.getString("nombre"), resultSet.getString("apellido"),
						resultSet.getString("telefono"), resultSet.getString("email"), resultSet.getString("login"),
						resultSet.getString("pass")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	public UsuarioDTO obtenerMedico(int dni) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement("select * from usuario where dni=" + dni + ";");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				usuarios.add(new UsuarioDTO(resultSet.getInt("idUsuario"), resultSet.getInt("idRol"),
						resultSet.getInt("dni"), resultSet.getString("nombre"), resultSet.getString("apellido"),
						resultSet.getString("telefono"), resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return usuarios.get(0);
	}

	@Override
	public List<UsuarioDTO> obtenerMedicoXEspecialidad(int idEspecialidad) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(
					"select * from usuario inner join prof_x_esp on usuario.idUsuario = prof_x_esp.idUsuario "
							+ "where idEspecialidad =" + idEspecialidad + ";");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				usuarios.add(new UsuarioDTO(resultSet.getInt("idUsuario"), resultSet.getInt("idRol"),
						resultSet.getInt("dni"), resultSet.getString("nombre"), resultSet.getString("apellido"),
						resultSet.getString("telefono"), resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	@Override
	public List<UsuarioDTO> obtenerMedicoXEspecialidadXAgenda(Integer id, int selectedIndex) {
		// TODO Auto-generated method stub
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		try {
			String query = "select distinct usuario.* from usuario inner join prof_x_esp on usuario.idUsuario = prof_x_esp.idUsuario "
					+ "inner join agenda on prof_x_esp.idProfEsp = agenda.idProfEsp"
					+ " where prof_x_esp.idEspecialidad =" + id;
			String dias = "";
			switch (selectedIndex) {
			case 1:
				dias = " and trabLun = true";
				break;
			case 2:
				dias = " and trabMar = true";
				break;
			case 3:
				dias = " and trabMie = true";
				break;
			case 4:
				dias = " and trabJue = true";
				break;
			case 5:
				dias = " and trabVie = true";
				break;
			case 6:
				dias = " and trabSab = true";
				break;
			case 7:
				dias = " and trabDom = true";
				break;
			}

			query += dias;
			statement = conexion.getSQLConexion().prepareStatement(query + ";");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				usuarios.add(new UsuarioDTO(resultSet.getInt("idUsuario"), resultSet.getInt("idRol"),
						resultSet.getInt("dni"), resultSet.getString("nombre"), resultSet.getString("apellido"),
						resultSet.getString("telefono"), resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	@Override
	public UsuarioDTO obtenerMedicoPorID(int id) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement("select * from usuario where idUsuario=" + id + ";");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				usuarios.add(new UsuarioDTO(resultSet.getInt("idUsuario"), resultSet.getInt("idRol"),
						resultSet.getInt("dni"), resultSet.getString("nombre"), resultSet.getString("apellido"),
						resultSet.getString("telefono"), resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return usuarios.get(0);
	}

	public List<UsuarioDTO> readAllXRol(int idRol) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement("select * from usuario where idRol=" + idRol + ";");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				usuarios.add(new UsuarioDTO(resultSet.getInt("idUsuario"), resultSet.getInt("idRol"),
						resultSet.getInt("dni"), resultSet.getString("nombre"), resultSet.getString("apellido"),
						resultSet.getString("telefono"), resultSet.getString("email"), resultSet.getString("login"),
						""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	@Override
	public UsuarioDTO readUsuLogin(String login, String pass) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		UsuarioDTO usuarios = null;
		try {
			statement = conexion.getSQLConexion().prepareStatement(readLogin);
			statement.setString(1, login);
			statement.setString(2, pass);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				usuarios = new UsuarioDTO(resultSet.getInt("idUsuario"), resultSet.getInt("idRol"),
						resultSet.getInt("dni"), resultSet.getString("nombre"), resultSet.getString("apellido"),
						resultSet.getString("telefono"), resultSet.getString("email"), resultSet.getString("login"),
						resultSet.getString("pass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	@Override
	public void comboFiltroTecnicos(JComboBox comboFiltroTecnico) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallTecnico);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboFiltroTecnico.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public int obtenerIDporNombre(String nombreTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idUsuario = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(IDporNombre);
			statement.setString(1, nombreTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idUsuario = resultSet.getInt("idUsuario");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idUsuario;

	}

}
