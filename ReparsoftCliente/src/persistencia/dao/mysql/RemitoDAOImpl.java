package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RemitoDAO;
import dto.RemitoDTO;

public class RemitoDAOImpl implements RemitoDAO {
	private static final String insert = "INSERT INTO Remitos(idRemito,NumeroRemitoSalida, IdUbicacion) VALUES(? , ? , ? )";
	// private static final String delete = "DELETE FROM Cliente WHERE idCliente =
	// ?";
	@SuppressWarnings("unused")
	private static final String readall = "SELECT * FROM Remitos";
	private static final String readallUbicacion = "SELECT Codigo, Ubicacion FROM UbicacionRemitos order by Codigo";
	private static final String numeroRemito = "SELECT MAX(Remitos.NumeroRemitoSalida) FROM Remitos join UbicacionRemitos on UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion where UbicacionRemitos.Codigo=?";
	private static final String maximoIDremito = "Select MAX(idRemito) from Remitos";
	private static final String delete = "DELETE FROM Remitos WHERE idRemito = ?";

	private static final String buscarIDdRemitoXubicacionNumero = "SELECT * FROM Remitos where IdUbicacion = ? and NumeroRemitoSalida = ?";
	
	private static final String readallxUbicacion = "SELECT * FROM Remitos where IdUbicacion = ?";
	public static String ubicacion;
	private Conexion conexion;
	
	

	

	@SuppressWarnings("unused")
	public RemitoDAOImpl(String ubicacionBase) {
		
		
		final String insert = "INSERT INTO Remitos(idRemito,NumeroRemitoSalida, IdUbicacion) VALUES(? , ? , ? )";
		// private static final String delete = "DELETE FROM Cliente WHERE idCliente =
		// ?";
		final String readall = "SELECT * FROM Remitos";
		final String readallUbicacion = "SELECT Codigo, Ubicacion FROM UbicacionRemitos order by Codigo";
		final String numeroRemito = "SELECT MAX(Remitos.NumeroRemitoSalida) FROM Remitos join UbicacionRemitos on UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion where UbicacionRemitos.Codigo=?";
		final String maximoIDremito = "Select MAX(idRemito) from Remitos";
		final String delete = "DELETE FROM Remitos WHERE idRemito = ?";

		final String buscarIDdRemitoXubicacionNumero = "SELECT * FROM Remitos where IdUbicacion = ? and NumeroRemitoSalida = ?";
		
		final String readallxUbicacion = "SELECT * FROM Remitos where IdUbicacion = ?";
		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);
		
	}
	
	

	public boolean insert(RemitoDTO Remito) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, Remito.getIdRemito());
			statement.setInt(2, Remito.getNumeroRemitoSalida());
			statement.setInt(3, Remito.getIdUbicacion());

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

	public boolean delete(int  remito_a_eliminar) {
		 PreparedStatement statement;
		 int chequeoUpdate=0;
		 try
		 {
		 statement = conexion.getSQLConexion().prepareStatement(delete);
		 statement.setString(1, Integer.toString(remito_a_eliminar));
		 chequeoUpdate = statement.executeUpdate();
		 if(chequeoUpdate > 0) //Si se ejecutó devuelvo true
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
	public List<RemitoDAO> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public void ListarUbicacion(JComboBox box) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<String> Ubicacion = new ArrayList<String>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallUbicacion);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			box.setModel(value);

			value.addElement("--Seleccionar Ubicación--");
			
			while (resultSet.next()) {

				if (resultSet.getString("Codigo") != null) {
					if (resultSet.getInt("Codigo") == 2 || resultSet.getInt("Codigo") == 5
							|| resultSet.getInt("Codigo") == 6) {

						value.addElement("000" + resultSet.getInt("Codigo") + " - " + resultSet.getString("Ubicacion"));
					} else
						value.addElement(resultSet.getInt("Codigo") + " - " + resultSet.getString("Ubicacion"));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public int obtenerNumeroRemito(int codigo) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query

		int numerodeRemito = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(numeroRemito);
			statement.setInt(1, codigo);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				numerodeRemito = resultSet.getInt(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return numerodeRemito;
	}

	@Override
	public boolean edit(RemitoDTO Remito) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int obtenerIDRemito() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idRemito = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(maximoIDremito);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idRemito = resultSet.getInt("MAX(idRemito)");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idRemito;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
	public void ListarRemitoPorUbicacion(JComboBox box, int id) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<String> numeroRemito = new ArrayList<String>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxUbicacion);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			box.setModel(value);

			// value.addElement(new SucursalDTO(0, "-- Seleccionar Sucursal --",0,"","", 0,
			// ""));
			
			
			while (resultSet.next()) {

				value.addElement(String.format("%05d", resultSet.getInt("NumeroRemitoSalida")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public int idRemitoXubicacionNumero(int ubicacion, int numero) {
		
		
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idRemito = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(buscarIDdRemitoXubicacionNumero);
			statement.setInt(1, ubicacion);
			statement.setInt(2, numero);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idRemito = resultSet.getInt("idRemito");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idRemito;
		
		
		
		
		
		
		
	}

}
