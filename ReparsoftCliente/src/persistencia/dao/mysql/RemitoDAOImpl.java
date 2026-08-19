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

    private static final String INSERT = "INSERT INTO Remitos(idRemito,NumeroRemitoSalida,IdUbicacion) VALUES(?,?,?)";
    private static final String DELETE = "DELETE FROM Remitos WHERE idRemito = ?";
    private static final String READ_ALL = "SELECT * FROM Remitos";
    private static final String READ_UBICACIONES = "SELECT Codigo, Ubicacion FROM UbicacionRemitos ORDER BY Codigo";
    private static final String NUMERO_REMITO = "SELECT MAX(Remitos.NumeroRemitoSalida) FROM Remitos JOIN UbicacionRemitos ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion WHERE UbicacionRemitos.Codigo = ?";
    private static final String MAXIMO_ID = "SELECT MAX(idRemito) FROM Remitos";
    private static final String READ_BY_UBICACION_NUMERO = "SELECT * FROM Remitos WHERE IdUbicacion = ? AND NumeroRemitoSalida = ?";
    private static final String READ_BY_UBICACION = "SELECT * FROM Remitos WHERE IdUbicacion = ?";

    private static final String UPDATE_REMITO = "UPDATE Remitos SET NumeroRemitoSalida = ?, IdUbicacion = ? WHERE idRemito = ?";

    private Conexion conexion;

    public RemitoDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(RemitoDTO remito) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, remito.getIdRemito());
            stmt.setInt(2, remito.getNumeroRemitoSalida());
            stmt.setInt(3, remito.getIdUbicacion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar remito: " + remito.getIdRemito(), e);
            return false;
        }
    }

    @Override
    public boolean delete(int idRemito) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idRemito);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar remito: " + idRemito, e);
            return false;
        }
    }

    @Override
    public List<RemitoDTO> readAll() {
        List<RemitoDTO> remitos = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                remitos.add(new RemitoDTO(
                    rs.getInt("IdUbicacion"),
                    rs.getInt("NumeroRemitoSalida"),
                    rs.getInt("idRemito")
                ));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todos los remitos", e);
        }
        return remitos;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarUbicacion(JComboBox box) {
        String sql = READ_UBICACIONES;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        box.setModel(model);
        model.addElement("--Seleccionar Ubicación--");

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                if (rs.getString("Codigo") != null) {
                    int codigo = rs.getInt("Codigo");
                    if (codigo == 2 || codigo == 5 || codigo == 6 || codigo == 7) {
                        model.addElement("000" + codigo + " - " + rs.getString("Ubicacion"));
                    } else {
                        model.addElement(codigo + " - " + rs.getString("Ubicacion"));
                    }
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar ubicaciones", e);
        }
    }

    @Override
    public int obtenerNumeroRemito(int codigo) {
        String sql = NUMERO_REMITO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener numero remito: " + codigo, e);
        }
        return 0;
    }

    @Override
    public boolean edit(RemitoDTO remito) {
        String sql = UPDATE_REMITO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, remito.getNumeroRemitoSalida());
            stmt.setInt(2, remito.getIdUbicacion());
            stmt.setInt(3, remito.getIdRemito());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al editar remito: " + remito.getIdRemito(), e);
            return false;
        }
    }

    @Override
    public int obtenerIDRemito() {
        String sql = MAXIMO_ID;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener maximo ID remito", e);
        }
        return 0;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarRemitoPorUbicacion(JComboBox box, int idUbicacion) {
        String sql = READ_BY_UBICACION;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        box.setModel(model);

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idUbicacion);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.addElement(String.format("%05d", rs.getInt("NumeroRemitoSalida")));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar remitos por ubicacion: " + idUbicacion, e);
        }
    }

    @Override
    public int idRemitoXubicacionNumero(int idUbicacion, int numero) {
        String sql = READ_BY_UBICACION_NUMERO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idUbicacion);
            stmt.setInt(2, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idRemito");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al buscar remito por ubicacion y numero", e);
        }
        return 0;
    }
}
