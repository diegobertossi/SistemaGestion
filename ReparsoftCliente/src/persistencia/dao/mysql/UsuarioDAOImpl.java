package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.UsuarioDAO;
import dto.UsuarioDTO;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String INSERT = "INSERT INTO usuario(idUsuario,idRol,dni,nombre,apellido,telefono,email,login,pass) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM usuario WHERE idUsuario = ?";
    private static final String READ_ALL = "SELECT * FROM usuario WHERE dni <> 0";
    private static final String READ_LOGIN = "SELECT * FROM usuario WHERE login = ? AND pass = ?";
    private static final String READ_ALL_TECNICO = "SELECT DISTINCT usuario.nombre, usuario.apellido FROM usuario WHERE usuario.idUsuario <> 1";
    private static final String READ_ALL_TECNICO_VISUALIZACION = "SELECT DISTINCT usuario.nombre, usuario.apellido FROM usuario";
    private static final String ID_POR_NOMBRE = "SELECT idUsuario FROM usuario WHERE nombre = ? AND apellido = ?";
    private static final String CORREO_POR_NOMBRE = "SELECT email FROM usuario WHERE nombre = ? AND apellido = ?";
    private static final String READ_BY_DNI = "SELECT * FROM usuario WHERE dni = ?";
    private static final String READ_ALL_BY_ROL = "SELECT * FROM usuario WHERE idRol = ?";
    private static final String READ_ALL_PAGINADO = "SELECT * FROM usuario WHERE dni <> 0 ORDER BY nombre ASC LIMIT ? OFFSET ?";
    private static final String COUNT_ALL = "SELECT COUNT(*) FROM usuario WHERE dni <> 0";
    private static final String UPDATE_USUARIO = "UPDATE usuario SET idRol = ?, dni = ?, nombre = ?, apellido = ?, telefono = ?, email = ?, login = ?, pass = ? WHERE idUsuario = ?";

    private Conexion conexion;

    public UsuarioDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(UsuarioDTO usuario) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setInt(2, usuario.getIdRol());
            stmt.setInt(3, usuario.getDni());
            stmt.setString(4, usuario.getNombre());
            stmt.setString(5, usuario.getApellido());
            stmt.setString(6, usuario.getTelefono());
            stmt.setString(7, usuario.getEmail());
            stmt.setString(8, usuario.getLogin());
            stmt.setString(9, usuario.getPass());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar usuario: " + usuario.getIdUsuario(), e);
            return false;
        }
    }

    @Override
    public boolean delete(UsuarioDTO usuario) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar usuario: " + usuario.getIdUsuario(), e);
            return false;
        }
    }

    @Override
    public boolean edit(UsuarioDTO usuario) {
        String sql = UPDATE_USUARIO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdRol());
            stmt.setInt(2, usuario.getDni());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getEmail());
            stmt.setString(7, usuario.getLogin());
            stmt.setString(8, usuario.getPass());
            stmt.setInt(9, usuario.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al editar usuario: " + usuario.getIdUsuario(), e);
            return false;
        }
    }

    @Override
    public List<UsuarioDTO> readAll() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todos los usuarios", e);
        }
        return usuarios;
    }

    @Override
    public List<UsuarioDTO> readAllPaginado(int limit, int offset) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = READ_ALL_PAGINADO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer usuarios paginados", e);
        }
        return usuarios;
    }

    @Override
    public int contarUsuarios() {
        String sql = COUNT_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LogDAO.error("Error al contar usuarios", e);
        }
        return 0;
    }

    @Override
    public UsuarioDTO obtenerMedico(int dni) {
        String sql = READ_BY_DNI;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener usuario por DNI: " + dni, e);
        }
        return null;
    }

    @Override
    public List<UsuarioDTO> readAllXRol(int idRol) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = READ_ALL_BY_ROL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer usuarios por rol: " + idRol, e);
        }
        return usuarios;
    }

    @Override
    public UsuarioDTO readUsuLogin(String login, String pass) {
        String sql = READ_LOGIN;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al autenticar usuario: " + login, e);
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void comboFiltroTecnicosV(JComboBox combo) {
        String sql = READ_ALL_TECNICO_VISUALIZACION;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        combo.setModel(model);
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(new UsuarioDTO(rs.getString("nombre"), rs.getString("apellido")));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al cargar combo tecnicos visualizacion", e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void comboFiltroTecnicos(JComboBox combo) {
        String sql = READ_ALL_TECNICO;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        combo.setModel(model);
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(new UsuarioDTO(rs.getString("nombre"), rs.getString("apellido")));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al cargar combo tecnicos", e);
        }
    }

    @Override
    public int obtenerIDporNombre(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return 1;
        }
        String sql = ID_POR_NOMBRE;
        String[] partes = nombreCompleto.trim().split(" ", 2);
        if (partes.length < 2) {
            return 1;
        }
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, partes[0]);
            stmt.setString(2, partes[1]);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idUsuario");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener ID por nombre: " + nombreCompleto, e);
        }
        return 1;
    }

    @Override
    public String correoPorNombre(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return null;
        }
        String sql = CORREO_POR_NOMBRE;
        String[] partes = nombreCompleto.trim().split(" ", 2);
        if (partes.length < 2) {
            return null;
        }
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, partes[0]);
            stmt.setString(2, partes[1]);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener correo: " + nombreCompleto, e);
        }
        return null;
    }

    private UsuarioDTO mapearUsuario(ResultSet rs) throws SQLException {
        return new UsuarioDTO(
            rs.getInt("idUsuario"),
            rs.getInt("idRol"),
            rs.getInt("dni"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("telefono"),
            rs.getString("email"),
            rs.getString("login"),
            rs.getString("pass")
        );
    }
}
