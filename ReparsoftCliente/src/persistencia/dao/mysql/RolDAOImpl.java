package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RolDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RolDAO;

public class RolDAOImpl implements RolDAO {

    private static final String INSERT = "INSERT INTO rol(idRol,nombre) VALUES(?,?)";
    private static final String DELETE = "DELETE FROM rol WHERE idRol = ?";
    private static final String READ_ALL = "SELECT * FROM rol";
    private static final String READ_BY_ID = "SELECT nombre FROM rol WHERE idRol = ?";
    private static final String UPDATE_ROL = "UPDATE rol SET nombre = ? WHERE idRol = ?";

    private Conexion conexion;

    public RolDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(RolDTO rol) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, rol.getIdRol());
            stmt.setString(2, rol.getNombre());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar rol: " + rol.getIdRol(), e);
            return false;
        }
    }

    @Override
    public boolean delete(RolDTO rol) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, rol.getIdRol());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar rol: " + rol.getIdRol(), e);
            return false;
        }
    }

    @Override
    public boolean edit(RolDTO rol) {
        String sql = UPDATE_ROL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, rol.getNombre());
            stmt.setInt(2, rol.getIdRol());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al editar rol: " + rol.getIdRol(), e);
            return false;
        }
    }

    @Override
    public List<RolDTO> readAll() {
        List<RolDTO> roles = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roles.add(mapearRol(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todos los roles", e);
        }
        return roles;
    }

    @Override
    public String readAllxid(int id) {
        String sql = READ_BY_ID;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer rol por ID: " + id, e);
        }
        return "";
    }

    private RolDTO mapearRol(ResultSet rs) throws SQLException {
        return new RolDTO(rs.getInt("idRol"), rs.getString("nombre"));
    }
}
