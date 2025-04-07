package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteDAO;
import dto.ClienteDTO;


public class ClienteDAOImpl implements ClienteDAO {
	
	
	
	private static String insert = "";
	private static String delete = "";
	private static String readall = "";
	private static String maximoIDcliente = "";
	private static String IDporNombre = "";
	private static String ContactoPorNombre = "";
	private static String EmailPorNombre = "";
	private static String TelefonolPorNombre = "";
	private static String cantidadReparacionesxCliente = ""; 
	public static String ubicacion;
	private Conexion conexion;
	
	
	
	public ClienteDAOImpl(String ubicacionBase) {
		// TODO Auto-generated constructor stub
		
		insert = "INSERT INTO Cliente(idCliente,nombre, CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico) VALUES(? , ? , ? , ? , ? , ? , ? , ?)";
		delete = "DELETE FROM Cliente WHERE idCliente = ?";
		readall = "SELECT * FROM Cliente ORDER BY Nombre ASC";
		maximoIDcliente = "Select MAX(idCliente) from Cliente";
		IDporNombre = "Select idCliente from Cliente where nombre =? ";
		ContactoPorNombre = "Select Contacto from Cliente where nombre =? ";
		EmailPorNombre = "Select CorreoElectronico from Cliente where nombre =? ";
		TelefonolPorNombre = "Select TelefonoContacto from Cliente where nombre =? ";
		cantidadReparacionesxCliente = "Select count(*) as total from Cliente INNER JOIN Equipos ON Cliente.idCliente = Equipos.idCliente where Cliente.idCliente = ?";
				
		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);
	
		
	}	
	
	
	
	
	



	public boolean insert(ClienteDTO Cliente) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, Cliente.getId());
			statement.setString(2, Cliente.getRazon_Social());
			statement.setString(3, Cliente.getCUIT());
			statement.setString(4, Cliente.getDomicilio());
			statement.setString(5, Cliente.getTelefonoEmpresa());
			statement.setString(6, Cliente.getContacto());
			statement.setString(7, Cliente.getTelefonoContacto());
			statement.setString(8, Cliente.getCorreoElectronico());

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

	public boolean delete(ClienteDTO cliente_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(cliente_a_eliminar.getId()));
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

	public List<ClienteDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Clientes.add(new ClienteDTO(resultSet.getInt("idCliente"), resultSet.getString("nombre"),
						resultSet.getString("CUIT"), resultSet.getString("Domicilio"),
						resultSet.getString("TelefonoEmpresa"), resultSet.getString("Contacto"),
						resultSet.getString("TelefonoContacto"), resultSet.getString("CorreoElectronico")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Clientes;
	}

	@Override
	public int obtenerIDcliente() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idcliente = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(maximoIDcliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idcliente = resultSet.getInt("MAX(idcliente)");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idcliente;
	}

	public boolean edit(ClienteDTO Cliente_a_editar) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("UPDATE Cliente SET idCliente = '" + Cliente_a_editar.getId() + "' , "
							+ "nombre = '" + Cliente_a_editar.getRazon_Social() + "' ," + "CUIT = '"
							+ Cliente_a_editar.getCUIT() + "' ," + "Domicilio = '" + Cliente_a_editar.getDomicilio()
							+ "' ," + "TelefonoEmpresa = '" + Cliente_a_editar.getTelefonoEmpresa() + "' ,"
							+ "Contacto = '" + Cliente_a_editar.getContacto() + "' ," + "TelefonoContacto = '"
							+ Cliente_a_editar.getTelefonoContacto() + "'  ," + " CorreoElectronico = '"
							+ Cliente_a_editar.getCorreoElectronico() + "' "

							+ " WHERE idCliente = " + Cliente_a_editar.getId() + "");

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

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public void ListarClientes(JComboBox box) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			box.setModel(value);

			value.addElement(new ClienteDTO(0, "-- Seleccionar Cliente --", "", "", "", "", "", ""));

			while (resultSet.next()) {

				value.addElement(new ClienteDTO(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getString("CUIT"), resultSet.getString("Domicilio"),
						resultSet.getString("TelefonoEmpresa"), resultSet.getString("Contacto"),
						resultSet.getString("TelefonoContacto"), resultSet.getString("CorreoElectronico")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public int obtenerIDporNombre(String nombreCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idcliente = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(IDporNombre);
			statement.setString(1, nombreCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idcliente = resultSet.getInt("idCliente");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idcliente;
	}

	@Override
	public String obtenerContactoPorCliente(String nombreCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String Contacto = "";
		try {
			statement = conexion.getSQLConexion().prepareStatement(ContactoPorNombre);
			statement.setString(1, nombreCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Contacto = resultSet.getString("Contacto");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return Contacto;
	}

	@Override
	public String obtenerEmailPorCliente(String nombreCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String email = "";
		try {
			statement = conexion.getSQLConexion().prepareStatement(EmailPorNombre);
			statement.setString(1, nombreCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				email = resultSet.getString("CorreoElectronico");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return email;
	}

	@Override
	public boolean obtenerReparacionxIDCliente(int idCliente) {
		int cantidad = 0;
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query

		try {
			statement = conexion.getSQLConexion().prepareStatement(cantidadReparacionesxCliente);
			statement.setInt(1, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidad = Integer.parseInt(resultSet.getString("total"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		if (cantidad == 0)
			return false;
		else
			return true;
	}

	@Override
	public String obtenerTelefonoPorCliente(String orgCliente) {
		
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		String telefono = "";
		try {
			statement = conexion.getSQLConexion().prepareStatement(TelefonolPorNombre);
			statement.setString(1, orgCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				telefono = resultSet.getString("TelefonoContacto");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return telefono;
	}

	
	

}
