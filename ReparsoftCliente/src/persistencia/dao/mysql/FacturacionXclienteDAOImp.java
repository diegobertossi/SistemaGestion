package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.FacturacionXclienteDAO;
import persistencia.dao.interfaz.RepuestoDAO;
import dto.FacturacionXclienteDTO;
import dto.RepuestosDTO;

public class FacturacionXclienteDAOImp implements FacturacionXclienteDAO {
	
	
	public static String ubicacion;
	private Conexion conexion;
	
	
	private static final String readall = "select Cliente.idCliente, Cliente.nombre, SUM(PrecioPeso) from reparaciones INNER JOIN (Equipos INNER JOIN Cliente ON Equipos.idCliente = Cliente.idCliente)  ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.EstadoComercial = 'Aceptado' group by Equipos.idCliente";
	
	
	@SuppressWarnings("unused")
	public FacturacionXclienteDAOImp(String ubicacionBase) {
		
		
		
		
		final String readallxCliente = "SELECT reemplazos.idReemplazos, reemplazos.ELS, reemplazos.ref, reemplazos.original, reemplazos.reemplazo,reemplazos.notas FROM reemplazos WHERE ELS = ?";
				
		
		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);
		
	}

	
	
	
	public List<FacturacionXclienteDTO> readAll(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<FacturacionXclienteDTO> listaClientes = new ArrayList<FacturacionXclienteDTO>();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				listaClientes.add(new FacturacionXclienteDTO(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return listaClientes;
	}

	

	

}