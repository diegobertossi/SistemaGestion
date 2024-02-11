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
import persistencia.dao.interfaz.SucursalDAO;
import dto.ClienteDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.SucursalDTO;

public class SucursalDAOImpl implements SucursalDAO {
	private static final String insert = "INSERT INTO Sucursal(IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico) VALUES(? , ? , ? , ? , ? , ? , ? )";
	private static final String delete = "DELETE FROM Sucursal WHERE IdSucursal = ?";
	private static final String readall = "SELECT * FROM Sucursal";
	private static final String maximoIDsucursal = "Select MAX(idsucursal) from Sucursal";
	private static final String cantidadSucxCliente = "Select count(*) as total from Sucursal where idCliente = ?";

	private static final String cantidadReparacionesxSuc = "Select count(*) as total from Sucursal INNER JOIN Equipos ON Sucursal.idsucursal = Equipos.IdSucursal where Sucursal.idsucursal = ?";

	private static final String readallxCliente = "SELECT * FROM Sucursal where idCliente = ?";
	private static final String readallSucursal = "SELECT Sucursal.NombreSucursal FROM Sucursal group by Sucursal.NombreSucursal";

	private static final String IDporNombre = "Select IdSucursal from Sucursal where NombreSucursal =? and idCliente=? ";
	
	public static String ubicacion;
	private Conexion conexion;
	
	
	public SucursalDAOImpl(String ubicacionBase) {
		// TODO Auto-generated constructor stub
	
	final String insert = "INSERT INTO Sucursal(IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico) VALUES(? , ? , ? , ? , ? , ? , ? )";
	final String delete = "DELETE FROM Sucursal WHERE IdSucursal = ?";
	final String readall = "SELECT * FROM Sucursal";
	final String maximoIDsucursal = "Select MAX(idsucursal) from Sucursal";
	final String cantidadSucxCliente = "Select count(*) as total from Sucursal where idCliente = ?";

	final String cantidadReparacionesxSuc = "Select count(*) as total from Sucursal INNER JOIN Equipos ON Sucursal.idsucursal = Equipos.IdSucursal where Sucursal.idsucursal = ?";

	final String readallxCliente = "SELECT * FROM Sucursal where idCliente = ?";
	final String readallSucursal = "SELECT Sucursal.NombreSucursal FROM Sucursal group by Sucursal.NombreSucursal";

	final String IDporNombre = "Select IdSucursal from Sucursal where NombreSucursal =? and idCliente=? ";
	ubicacion = ubicacionBase;
	conexion = Conexion.getConexion(ubicacion);
	
	
	
	}




	public boolean insert(SucursalDTO Sucursal) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, Sucursal.getIdSucursal());
			statement.setString(2, Sucursal.getNombreSucursal());
			statement.setInt(3, Sucursal.getIdClientesuc());
			statement.setString(4, Sucursal.getDomicilioSucursal());
			statement.setString(5, Sucursal.getContactoSucursal());
			statement.setString(6, Sucursal.getTelefonoSucursal());
			statement.setString(7, Sucursal.getCorreoElectronico());

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

	public boolean delete(SucursalDTO Sucursal_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(Sucursal_a_eliminar.getIdSucursal()));
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

	public List<SucursalDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<SucursalDTO> Sucursal = new ArrayList<SucursalDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Sucursal.add(new SucursalDTO(resultSet.getInt("IdSucursal"), resultSet.getString("NombreSucursal"),
						resultSet.getInt("idCliente"), resultSet.getString("DomicilioSucursal"),
						resultSet.getString("ContactoSucursal"), resultSet.getString("TelefonoSucursal"),
						resultSet.getString("CorreoElectronico")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Sucursal;
	}

	@Override
	public List<SucursalDTO> obtenerSucursalXidCliente(Integer i) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<SucursalDTO> Sucursal = new ArrayList<SucursalDTO>();
		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxCliente);
			statement.setInt(1, i);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Sucursal.add(new SucursalDTO(resultSet.getInt("IdSucursal"), resultSet.getString("NombreSucursal"),
						resultSet.getInt("idCliente"), resultSet.getString("DomicilioSucursal"),
						resultSet.getString("ContactoSucursal"), resultSet.getString("TelefonoSucursal"),
						resultSet.getString("CorreoElectronico")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Sucursal;
	}

	@Override
	public int obtenerIDsucursal() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idsucursal = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(maximoIDsucursal);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idsucursal = resultSet.getInt("MAX(idsucursal)");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idsucursal;
	}

	public void ListarSucursalesxCliente(JComboBox box, int id) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<SucursalDTO> Sucursal = new ArrayList<SucursalDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxCliente);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			box.setModel(value);

			// value.addElement(new SucursalDTO(0, "-- Seleccionar Sucursal --",0,"","", 0,
			// ""));

			while (resultSet.next()) {
				value.addElement(new SucursalDTO(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
						resultSet.getString(7)));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public int obtenercantidaddeSucursales(int idcliente) {

		int cantidad = 0;
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query

		try {
			statement = conexion.getSQLConexion().prepareStatement(cantidadSucxCliente);
			statement.setInt(1, idcliente);
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
		return cantidad;
	}

	public boolean edit(SucursalDTO Sucursal_a_editar) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("UPDATE Sucursal SET IdSucursal = '" + Sucursal_a_editar.getIdSucursal() + "' , "
							+ "NombreSucursal = '" + Sucursal_a_editar.getNombreSucursal() + "' ," + "idCliente = '"
							+ Sucursal_a_editar.getIdClientesuc() + "' ," + "DomicilioSucursal = '"
							+ Sucursal_a_editar.getDomicilioSucursal() + "' ," + "ContactoSucursal = '"
							+ Sucursal_a_editar.getContactoSucursal() + "' ," + "TelefonoSucursal = '"
							+ Sucursal_a_editar.getTelefonoSucursal() + "' ," + "CorreoElectronico = '"
							+ Sucursal_a_editar.getCorreoElectronico() + "' "

							+ " WHERE IdSucursal = " + Sucursal_a_editar.getIdSucursal() + "");

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

	@Override
	public boolean obtenerReparacionxIDsSuc(int idsucu) {

		int cantidad = 0;
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query

		try {
			statement = conexion.getSQLConexion().prepareStatement(cantidadReparacionesxSuc);
			statement.setInt(1, idsucu);
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
	public int obtenerIDporNombre(String nombreSucursal, int IDCliente) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idsucursal = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(IDporNombre);
			statement.setString(1, nombreSucursal);
			statement.setInt(2, IDCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idsucursal = resultSet.getInt("IdSucursal");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idsucursal;
	}

	@Override
	public void ListarSucursales(JComboBox ComboSucursales) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallSucursal);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			ComboSucursales.setModel(value);



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



}
