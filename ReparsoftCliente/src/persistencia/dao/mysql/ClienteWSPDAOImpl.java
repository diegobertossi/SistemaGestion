package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;


//import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteWSPDAO;
import dto.ClienteWSPDTO;

public class ClienteWSPDAOImpl implements ClienteWSPDAO {
	private static final String insert = "INSERT INTO ClienteWSP(idClienteWSP,organizacion, nombreWSP,TelefonoWSP) VALUES(? , ? , ? , ? )";
	private static final String delete = "DELETE FROM ClienteWSP WHERE idClienteWSP = ?";
	private static final String readall = "SELECT * FROM ClienteWSP";
	@SuppressWarnings("unused")
	private static final String maximoIDcliente = "Select MAX(idClienteWSP) from ClienteWSP";
	@SuppressWarnings("unused")
	private static final String IDporNombre = "Select idClienteWSP from ClienteWSP where nombre =? ";
	private static final String readallContactoXorganizacion = "Select nombreWSP from ClienteWSP where organizacion =? ";
	private static final String readallTelefonoXContacto = "Select TelefonoWSP from ClienteWSP where nombreWSP =? ";
	
	private static final String readallSinRepetidos = "SELECT DISTINCTROW  organizacion FROM ClienteWSP"; 
	// private static final String ContactoPorNombre = "Select Contacto from Cliente
	// where nombre =? ";
	// private static final String EmailPorNombre = "Select CorreoElectronico from
	// Cliente where nombre =? ";
	
	private static  String ubicacion;
	private Conexion conexion;

	

	@SuppressWarnings("unused")
	public ClienteWSPDAOImpl(String ubicacionBase) {
		final String insert = "INSERT INTO ClienteWSP(idClienteWSP,organizacion, nombreWSP,TelefonoWSP) VALUES(? , ? , ? , ? )";
		final String delete = "DELETE FROM ClienteWSP WHERE idClienteWSP = ?";
		final String readall = "SELECT * FROM ClienteWSP";
		final String maximoIDcliente = "Select MAX(idClienteWSP) from ClienteWSP";
		final String IDporNombre = "Select idClienteWSP from ClienteWSP where nombre =? ";
		final String readallContactoXorganizacion = "Select nombreWSP from ClienteWSP where organizacion =? ";
		final String readallTelefonoXContacto = "Select TelefonoWSP from ClienteWSP where nombreWSP =? ";
		final String readallSinRepetidos = "SELECT DISTINCTROW  organizacion FROM ClienteWSP";
		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);
		
	}

	
	

	
	
	@Override
	public boolean insert(ClienteWSPDTO ClienteWSP) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, ClienteWSP.getIdClienteWSP());
			statement.setString(2, ClienteWSP.getOrganizacion());
			statement.setString(3, ClienteWSP.getNombreWSP());
			statement.setString(4, ClienteWSP.getTelefonoWSP());

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

	@Override
	public boolean edit(ClienteWSPDTO Cliente_a_editar) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("UPDATE ClienteWSP SET idClienteWSP = '" + Cliente_a_editar.getIdClienteWSP() + "' , "
							+ "organizacion = '" + Cliente_a_editar.getOrganizacion() + "' ," + "nombreWSP = '" + Cliente_a_editar.getNombreWSP()
							+ "' ," + "TelefonoWSP = '" + Cliente_a_editar.getTelefonoWSP()  + "' " + " WHERE idClienteWSP = "
							+ Cliente_a_editar.getIdClienteWSP() + "");

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
	public boolean delete(ClienteWSPDTO Cliente_a_eliminar) {
		
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(Cliente_a_eliminar.getIdClienteWSP()));
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

	@Override
	public List<ClienteWSPDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ClienteWSPDTO> ClientesWSP = new ArrayList<ClienteWSPDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ClientesWSP.add(new ClienteWSPDTO(resultSet.getInt("idClienteWSP"), resultSet.getString("organizacion"),
						resultSet.getString("nombreWSP"), resultSet.getString("TelefonoWSP")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return ClientesWSP;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public void ListarClientesWSP(JComboBox box) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ClienteWSPDTO> ClientesWSP = new ArrayList<ClienteWSPDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallSinRepetidos);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			box.setModel(value);

			value.addElement(new ClienteWSPDTO(0, "-- Seleccionar Cliente --", "", ""));

			while (resultSet.next()) {

				value.addElement(
						new ClienteWSPDTO( resultSet.getString("organizacion")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public int obtenerIDclienteWSP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtenerIDporNombreWSP(String nombreCliente) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String obtenerNumeroPorCliente(String nombreClienteWSP) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarContactoxOrganizacion(JComboBox comboNombreBuscado, String organizacionWSP) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<SucursalDTO> Sucursal = new ArrayList<SucursalDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallContactoXorganizacion);
			statement.setString(1, organizacionWSP);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboNombreBuscado.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ClienteWSPDTO(resultSet.getString(1)));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public String obtenetTelefonoXcontacto(String nombreBuscado) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query

		String telefonoString = "";

		try {
			statement = conexion.getSQLConexion().prepareStatement(readallTelefonoXContacto);
			statement.setString(1, nombreBuscado);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				telefonoString = resultSet.getString(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return telefonoString;

	}

}
