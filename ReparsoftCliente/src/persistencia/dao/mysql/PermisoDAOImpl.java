package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PermisoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PermisoDAO;

public class PermisoDAOImpl implements PermisoDAO {

    private static final String INSERT = "INSERT INTO permisos(idPermiso,idRol,idPantalla) VALUES(0,?,?)";
    private static final String DELETE = "DELETE FROM permisos WHERE idPermiso = ?";
    private static final String READ_ALL = "SELECT permisos.*,ph.nombre,IFNULL(pd.nombre,'Menu Principal') as padre FROM permisos "
            + "INNER JOIN rol ON rol.idRol = permisos.idRol "
            + "INNER JOIN pantalla ph ON ph.idPantalla = permisos.idPantalla "
            + "LEFT JOIN pantalla pd ON pd.idPantalla = ph.idPantPadre "
            + "WHERE permisos.idRol = ?";
    private static final String READ_FALTANTES = "SELECT ph.idPantalla,ph.nombre,IFNULL(pd.nombre,'Menu Principal') as padre FROM pantalla ph "
            + "LEFT JOIN pantalla pd ON pd.idPantalla = ph.idPantPadre "
            + "WHERE ph.idPantalla NOT IN (SELECT DISTINCT permisos.idPantalla FROM permisos INNER JOIN rol ON rol.idRol = permisos.idRol WHERE rol.idRol = ?)";
    private static final String READ_PADRES = "SELECT permisos.*,pantalla.nombre FROM permisos "
            + "INNER JOIN rol ON rol.idRol = permisos.idRol "
            + "INNER JOIN pantalla ON pantalla.idPantalla = permisos.idPantalla "
            + "WHERE permisos.idRol = ? AND IFNULL(pantalla.idPantPadre,0) = 0";
    private static final String READ_HIJOS = "SELECT permisos.*,ph.nombre FROM permisos "
            + "INNER JOIN rol ON rol.idRol = permisos.idRol "
            + "INNER JOIN pantalla ph ON ph.idPantalla = permisos.idPantalla "
            + "INNER JOIN pantalla pd ON pd.idPantalla = ph.idPantPadre "
            + "WHERE permisos.idRol = ? AND pd.nombre = ?";

    private Conexion conexion;

    public PermisoDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(PermisoDTO permiso) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, permiso.getIdRol());
            stmt.setInt(2, permiso.getIdPantalla());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar permiso", e);
            return false;
        }
    }

    @Override
    public boolean delete(PermisoDTO permiso) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, permiso.getIdPermiso());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar permiso: " + permiso.getIdPermiso(), e);
            return false;
        }
    }

    @Override
    public boolean edit(PermisoDTO permiso) {
        return false;
    }

    @Override
    public List<PermisoDTO> readAll(Integer idRol) {
        List<PermisoDTO> permisos = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    permisos.add(new PermisoDTO(
                        rs.getInt("idPermiso"),
                        rs.getInt("idRol"),
                        rs.getInt("idPantalla"),
                        rs.getString("nombre"),
                        rs.getString("padre")
                    ));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer permisos por rol: " + idRol, e);
        }
        return permisos;
    }

    @Override
    public List<PermisoDTO> readAllPadres(Integer idRol) {
        List<PermisoDTO> permisos = new ArrayList<>();
        String sql = READ_PADRES;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    permisos.add(new PermisoDTO(
                        rs.getInt("idPermiso"),
                        rs.getInt("idRol"),
                        rs.getInt("idPantalla"),
                        rs.getString("nombre")
                    ));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer permisos padres por rol: " + idRol, e);
        }
        return permisos;
    }

    @Override
    public List<PermisoDTO> readAllHijos(Integer idRol, String nombrePantallaPadre) {
        List<PermisoDTO> permisos = new ArrayList<>();
        String sql = READ_HIJOS;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            stmt.setString(2, nombrePantallaPadre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    permisos.add(new PermisoDTO(
                        rs.getInt("idPermiso"),
                        rs.getInt("idRol"),
                        rs.getInt("idPantalla"),
                        rs.getString("nombre")
                    ));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer permisos hijos: " + nombrePantallaPadre, e);
        }
        return permisos;
    }

    @Override
    public List<PermisoDTO> readFaltantes(int idRol) {
        List<PermisoDTO> permisos = new ArrayList<>();
        String sql = READ_FALTANTES;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    permisos.add(new PermisoDTO(
                        0, idRol,
                        rs.getInt("idPantalla"),
                        rs.getString("nombre"),
                        rs.getString("padre")
                    ));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer permisos faltantes para rol: " + idRol, e);
        }
        return permisos;
    }
}
