package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteWSPDAO;
import dto.ClienteWSPDTO;

public class ClienteWSPDAOImpl implements ClienteWSPDAO {

    private static final String INSERT = "INSERT INTO ClienteWSP(idClienteWSP,organizacion,nombreWSP,TelefonoWSP) VALUES(?,?,?,?)";
    private static final String DELETE = "DELETE FROM ClienteWSP WHERE idClienteWSP = ?";
    private static final String READ_ALL = "SELECT * FROM ClienteWSP";
    private static final String MAXIMO_ID = "SELECT MAX(idClienteWSP) FROM ClienteWSP";
    private static final String ID_POR_NOMBRE = "SELECT idClienteWSP FROM ClienteWSP WHERE nombre = ?";
    private static final String CONTACTO_X_ORGANIZACION = "SELECT nombreWSP FROM ClienteWSP WHERE organizacion = ?";
    private static final String TELEFONO_X_CONTACTO = "SELECT TelefonoWSP FROM ClienteWSP WHERE nombreWSP = ?";
    private static final String SIN_REPETIDOS = "SELECT DISTINCTROW organizacion FROM ClienteWSP";
    private static final String UPDATE_CLIENTE_WSP = "UPDATE ClienteWSP SET organizacion = ?, nombreWSP = ?, TelefonoWSP = ? WHERE idClienteWSP = ?";

    private Conexion conexion;

    public ClienteWSPDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(ClienteWSPDTO clienteWSP) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, clienteWSP.getIdClienteWSP());
            stmt.setString(2, clienteWSP.getOrganizacion());
            stmt.setString(3, clienteWSP.getNombreWSP());
            stmt.setString(4, clienteWSP.getTelefonoWSP());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar cliente WSP: " + clienteWSP.getIdClienteWSP(), e);
            return false;
        }
    }

    @Override
    public boolean edit(ClienteWSPDTO clienteWSP) {
        String sql = UPDATE_CLIENTE_WSP;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, clienteWSP.getOrganizacion());
            stmt.setString(2, clienteWSP.getNombreWSP());
            stmt.setString(3, clienteWSP.getTelefonoWSP());
            stmt.setInt(4, clienteWSP.getIdClienteWSP());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al editar cliente WSP: " + clienteWSP.getIdClienteWSP(), e);
            return false;
        }
    }

    @Override
    public boolean delete(ClienteWSPDTO clienteWSP) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, clienteWSP.getIdClienteWSP());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar cliente WSP: " + clienteWSP.getIdClienteWSP(), e);
            return false;
        }
    }

    @Override
    public List<ClienteWSPDTO> readAll() {
        List<ClienteWSPDTO> clientes = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapearClienteWSP(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todos los clientes WSP", e);
        }
        return clientes;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarClientesWSP(JComboBox box) {
        String sql = SIN_REPETIDOS;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        box.setModel(model);
        model.addElement(new ClienteWSPDTO(0, "-- Seleccionar Cliente --", "", ""));

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(new ClienteWSPDTO(rs.getString("organizacion")));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar clientes WSP", e);
        }
    }

    @Override
    public int obtenerIDclienteWSP() {
        String sql = MAXIMO_ID;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener maximo ID cliente WSP", e);
        }
        return 0;
    }

    @Override
    public int obtenerIDporNombreWSP(String nombreCliente) {
        String sql = ID_POR_NOMBRE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idClienteWSP");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener ID por nombre WSP: " + nombreCliente, e);
        }
        return 0;
    }

    @Override
    public String obtenerNumeroPorCliente(String nombreCliente) {
        String sql = TELEFONO_X_CONTACTO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener numero por cliente WSP: " + nombreCliente, e);
        }
        return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarContactoxOrganizacion(JComboBox combo, String organizacion) {
        String sql = CONTACTO_X_ORGANIZACION;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        combo.setModel(model);

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, organizacion);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.addElement(new ClienteWSPDTO(rs.getString(1)));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar contactos por organizacion: " + organizacion, e);
        }
    }

    @Override
    public String obtenetTelefonoXcontacto(String nombreBuscado) {
        String sql = TELEFONO_X_CONTACTO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreBuscado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener telefono por contacto: " + nombreBuscado, e);
        }
        return "";
    }

    private ClienteWSPDTO mapearClienteWSP(ResultSet rs) throws SQLException {
        return new ClienteWSPDTO(
            rs.getInt("idClienteWSP"),
            rs.getString("organizacion"),
            rs.getString("nombreWSP"),
            rs.getString("TelefonoWSP")
        );
    }
}
