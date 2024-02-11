package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RepuestoDAO;
import dto.ClienteDTO;
import dto.RepuestosDTO;

public class RepuestosDAOImpl implements RepuestoDAO {
	
	
	public static String ubicacion;
	private Conexion conexion;
	
	private static final String insert = "INSERT INTO reemplazos(ELS,ref, original,reemplazo,notas) VALUES(? , ? , ? , ? , ?)";
	private static final String delete = "DELETE FROM reemplazos where idReemplazos = ?";
	private static final String readall = "SELECT * FROM reemplazos";
	private static final String readallxELS = "SELECT reemplazos.idReemplazos, reemplazos.ELS, reemplazos.ref, reemplazos.original, reemplazos.reemplazo,reemplazos.notas FROM reemplazos WHERE ELS = ?";
	
	
	private static final String readallOriginal = "SELECT distinct * FROM reemplazos group by reemplazos.original";
	private static final String readallReemplazo = "SELECT distinct * FROM reemplazos group by reemplazos.reemplazo";

	
	
	public RepuestosDAOImpl(String ubicacionBase) {
		
		
		final String insert = "INSERT INTO reemplazos(ELS,ref, original,reemplazo,notas) VALUES(? , ? , ? , ? , ?)";
		final String delete = "DELETE FROM reemplazos where idReemplazos = ?";
		final String readall = "SELECT * FROM reemplazos";
		final String readallxELS = "SELECT reemplazos.idReemplazos, reemplazos.ELS, reemplazos.ref, reemplazos.original, reemplazos.reemplazo,reemplazos.notas FROM reemplazos WHERE ELS = ?";
		
		
		final String readallOriginal = "SELECT distinct * FROM reemplazos group by reemplazos.original";
		final String readallReemplazo = "SELECT distinct * FROM reemplazos group by reemplazos.reemplazo";
		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);
		
	}

	
	
	public boolean insert(RepuestosDTO Repuestos) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, Repuestos.getELS());
			statement.setString(2, Repuestos.getRef());
			statement.setString(3, Repuestos.getOriginal());
			statement.setString(4, Repuestos.getReemplazo());
			statement.setString(5, Repuestos.getNotas());

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

	public boolean delete(RepuestosDTO repuesto_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(repuesto_a_eliminar.getIdRepuesto()));
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

	public List<RepuestosDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<RepuestosDTO> Repuestos = new ArrayList<RepuestosDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Repuestos.add(new RepuestosDTO(resultSet.getInt("idReemplazos"), resultSet.getInt("ELS"),
						resultSet.getString("ref"), resultSet.getString("original"), resultSet.getString("reemplazo"),
						resultSet.getString("notas")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Repuestos;
	}

	public List<RepuestosDTO> obtenerRepuestosXels(Integer i) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<RepuestosDTO> Repuestos = new ArrayList<RepuestosDTO>();
		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxELS);
			statement.setInt(1, i);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Repuestos.add(new RepuestosDTO(resultSet.getInt("idReemplazos"), resultSet.getInt("ELS"),
						resultSet.getString("ref"), resultSet.getString("original"), resultSet.getString("reemplazo"),
						resultSet.getString("notas")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Repuestos;
	}

	public boolean edit(RepuestosDTO repuesto_a_editar) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion()
					.prepareStatement("UPDATE reemplazos SET idReemplazos = '" + repuesto_a_editar.getIdRepuesto()
							+ "' , " + "ELS = '" + repuesto_a_editar.getELS() + "' ," + "ref = '"
							+ repuesto_a_editar.getRef() + "' ," + "original = '" + repuesto_a_editar.getOriginal()
							+ "' ," + "reemplazo = '" + repuesto_a_editar.getReemplazo() + "' ," + "notas = '"
							+ repuesto_a_editar.getNotas() + "'" + " WHERE idReemplazos = "
							+ repuesto_a_editar.getIdRepuesto() + "");

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
	public void ListarRepuestos(JComboBox box) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query

		try {
			statement = conexion.getSQLConexion().prepareStatement(readallOriginal);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			box.setModel(value);

			while (resultSet.next()) {

				value.addElement(resultSet.getString(4));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{

			conexion.cerrarConexion();
		}

	}

	@Override
	public void ListarRepuestosReemplazo(JComboBox comboCompReemplazo) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query

		try {
			statement = conexion.getSQLConexion().prepareStatement(readallReemplazo);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboCompReemplazo.setModel(value);

			while (resultSet.next()) {

				value.addElement(resultSet.getString(5));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

}
