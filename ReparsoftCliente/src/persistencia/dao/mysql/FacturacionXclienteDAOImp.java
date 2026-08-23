package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.FacturacionXclienteDAO;
import dto.FacturacionXclienteDTO;

public class FacturacionXclienteDAOImp implements FacturacionXclienteDAO {

    private static final String READ_ALL = "SELECT Cliente.idCliente, Cliente.nombre, SUM(PrecioPeso) "
            + "FROM reparaciones "
            + "INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo "
            + "INNER JOIN Cliente ON Equipos.idCliente = Cliente.idCliente "
            + "WHERE reparaciones.FechAceptacion >= MAKEDATE(?, 1) AND reparaciones.FechAceptacion < MAKEDATE(?, 1) + INTERVAL 1 YEAR AND reparaciones.EstadoComercial = 'Aceptado' "
            + "GROUP BY Equipos.idCliente ORDER BY SUM(PrecioPeso) DESC";

    private Conexion conexion;

    public FacturacionXclienteDAOImp(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public List<FacturacionXclienteDTO> readAll(int anio) {
        List<FacturacionXclienteDTO> lista = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, anio);
            stmt.setInt(2, anio);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new FacturacionXclienteDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3)
                    ));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer facturacion por cliente: anio=" + anio, e);
        }
        return lista;
    }
}
