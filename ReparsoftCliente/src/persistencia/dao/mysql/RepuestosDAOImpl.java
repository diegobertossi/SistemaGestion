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
import dto.RepuestosDTO;

public class RepuestosDAOImpl implements RepuestoDAO {

    private static final String INSERT = "INSERT INTO reemplazos(ELS,ref,original,reemplazo,notas) VALUES(?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM reemplazos WHERE idReemplazos = ?";
    private static final String READ_ALL = "SELECT * FROM reemplazos";
    private static final String READ_BY_ELS = "SELECT idReemplazos,ELS,ref,original,reemplazo,notas FROM reemplazos WHERE ELS = ?";
    private static final String READ_ORIGINAL = "SELECT DISTINCT original FROM reemplazos GROUP BY original";
    private static final String READ_REEMPLAZO = "SELECT DISTINCT reemplazo FROM reemplazos GROUP BY reemplazo";
    private static final String UPDATE_REEMPLAZO = "UPDATE reemplazos SET ELS = ?, ref = ?, original = ?, reemplazo = ?, notas = ? WHERE idReemplazos = ?";

    private Conexion conexion;

    public RepuestosDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(RepuestosDTO repuesto) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, repuesto.getELS());
            stmt.setString(2, repuesto.getRef());
            stmt.setString(3, repuesto.getOriginal());
            stmt.setString(4, repuesto.getReemplazo());
            stmt.setString(5, repuesto.getNotas());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar repuesto: " + repuesto.getIdRepuesto(), e);
            return false;
        }
    }

    @Override
    public boolean delete(RepuestosDTO repuesto) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, repuesto.getIdRepuesto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar repuesto: " + repuesto.getIdRepuesto(), e);
            return false;
        }
    }

    @Override
    public List<RepuestosDTO> readAll() {
        List<RepuestosDTO> repuestos = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                repuestos.add(mapearRepuesto(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todos los repuestos", e);
        }
        return repuestos;
    }

    @Override
    public List<RepuestosDTO> obtenerRepuestosXels(Integer els) {
        List<RepuestosDTO> repuestos = new ArrayList<>();
        String sql = READ_BY_ELS;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, els);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    repuestos.add(mapearRepuesto(rs));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer repuestos por ELS: " + els, e);
        }
        return repuestos;
    }

    @Override
    public boolean edit(RepuestosDTO repuesto) {
        String sql = UPDATE_REEMPLAZO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, repuesto.getELS());
            stmt.setString(2, repuesto.getRef());
            stmt.setString(3, repuesto.getOriginal());
            stmt.setString(4, repuesto.getReemplazo());
            stmt.setString(5, repuesto.getNotas());
            stmt.setInt(6, repuesto.getIdRepuesto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al editar repuesto: " + repuesto.getIdRepuesto(), e);
            return false;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarRepuestos(JComboBox box) {
        String sql = READ_ORIGINAL;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        box.setModel(model);

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(rs.getString("original"));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar repuestos", e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarRepuestosReemplazo(JComboBox combo) {
        String sql = READ_REEMPLAZO;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        combo.setModel(model);

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(rs.getString("reemplazo"));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar repuestos reemplazo", e);
        }
    }

    private RepuestosDTO mapearRepuesto(ResultSet rs) throws SQLException {
        return new RepuestosDTO(
            rs.getInt("idReemplazos"),
            rs.getInt("ELS"),
            rs.getString("ref"),
            rs.getString("original"),
            rs.getString("reemplazo"),
            rs.getString("notas")
        );
    }
}
