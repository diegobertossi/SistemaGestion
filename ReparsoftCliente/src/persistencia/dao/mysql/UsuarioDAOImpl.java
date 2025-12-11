package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import dto.UsuarioDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.UsuarioDAO;

public class UsuarioDAOImpl implements UsuarioDAO {
	private static final String insert = "INSERT INTO usuario(idUsuario, idRol, dni, nombre, apellido, telefono, email,login,pass) VALUES(?, ?, ?, ?, ?, ?, ?,?,?)";
	private static final String delete = "DELETE FROM usuario WHERE idUsuario = ?";
	private static final String readall = "SELECT * FROM usuario WHERE dni <> 0 ";
	private static final String readLogin = "SELECT * FROM usuario where login = ? AND pass = ? ";
	private static final String readallTecnico = "SELECT usuario.nombre, usuario.apellido FROM usuario where usuario.idUsuario != '1' group by usuario.apellido";
	private static final String readallTecnicoVisualizacion = "SELECT usuario.nombre, usuario.apellido FROM usuario group by usuario.apellido";
	private static final String IDporNombre = "Select idUsuario from usuario where nombre =? and apellido =?";
	private static final String correoPorNombre = "SELECT email FROM usuario WHERE nombre = ? AND apellido = ?";

	public static String ubicacion;
	private Conexion conexion;;

	@SuppressWarnings("unused")
	public UsuarioDAOImpl(String ubicacionBase) {

		final String insert = "INSERT INTO usuario(idUsuario, idRol, dni, nombre, apellido, telefono, email,login,pass) VALUES(?, ?, ?, ?, ?, ?, ?,?,?)";
		final String delete = "DELETE FROM usuario WHERE idUsuario = ?";
		final String readall = "SELECT * FROM usuario WHERE dni <> 0 ";
		final String readLogin = "SELECT * FROM usuario where login = ? AND pass = ? ";
		final String readallTecnico = "SELECT usuario.nombre FROM usuario group by usuario.nombre";
		final String IDporNombre = "Select idUsuario from usuario where nombre =?";
		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);
	}

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

	public boolean delete(UsuarioDTO user_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(user_a_eliminar.getIdUsuario()));
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
			// No cerrar la conexión aquí - se maneja en el singleton
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
			// No cerrar la conexión aquí - se maneja en el singleton
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
			// No cerrar la conexión aquí - se maneja en el singleton
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
			// No cerrar la conexión aquí - se maneja en el singleton
		}
		return usuarios;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void comboFiltroTecnicosV(JComboBox comboFiltroTecnico) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<UsuarioDTO> Usuarios = new ArrayList<UsuarioDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallTecnicoVisualizacion);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboFiltroTecnico.setModel(value);

			while (resultSet.next()) {

				value.addElement(new UsuarioDTO(resultSet.getString(1), resultSet.getString(2)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			// No cerrar la conexión aquí - se maneja en el singleton
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

				value.addElement(new UsuarioDTO(resultSet.getString(1), resultSet.getString(2)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			// No cerrar la conexión aquí - se maneja en el singleton
		}

	}

	@Override
	public int obtenerIDporNombre(String nombreTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idUsuario = 0;
				
		if (nombreTecnico.compareTo("") > 0 &&  nombreTecnico.compareTo(" ") > 0 ) {
			String[] partes = nombreTecnico.split(" ");
			String nombre = partes[0];
			String apellido = partes[1];

			try {
				statement = conexion.getSQLConexion().prepareStatement(IDporNombre);
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				resultSet = statement.executeQuery();

				while (resultSet.next()) {
					idUsuario = resultSet.getInt("idUsuario");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally // Se ejecuta siempre
			{
				// No cerrar la conexión aquí - se maneja en el singleton
			}
		} else
			idUsuario = 1;

		return idUsuario;

	}

	/**
	* Obtiene el correo electrónico de un usuario por su nombre y apellido
	 * @param nombreCompleto Nombre completo del usuario en formato "Nombre Apellido"
	 * @return String con el email del usuario, o null si no se encuentra
	 */
	@Override
	public String correoPorNombre(String nombreCompleto) {
	    PreparedStatement statement;
	    ResultSet resultSet;
	    String email = null;
	    
	    // Validar que el parámetro no esté vacío
	    if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
	        return null;
	    }
	    
	    try {
	        // Dividir el nombre completo en nombre y apellido
	        String[] partes = nombreCompleto.trim().split(" ", 2); // Limitar a 2 partes
	        
	        if (partes.length < 2) {
	            System.out.println("Formato incorrecto. Se esperaba 'Nombre Apellido'");
	            return null;
	        }
	        
	        String nombre = partes[0];
	        String apellido = partes[1];
	        
	        // Preparar y ejecutar la consulta
	        statement = conexion.getSQLConexion().prepareStatement(correoPorNombre);
	        statement.setString(1, nombre);
	        statement.setString(2, apellido);
	        resultSet = statement.executeQuery();
	        
	        // Obtener el resultado
	        if (resultSet.next()) {
	            email = resultSet.getString("email");
	        }
	        
	    } catch (SQLException e) {
	        System.err.println("Error al obtener correo del usuario: " + nombreCompleto);
	        e.printStackTrace();
	    } finally {
	        // No cerrar la conexión aquí - se maneja en el singleton
	    }
	    
	    return email;
	}
	
}
