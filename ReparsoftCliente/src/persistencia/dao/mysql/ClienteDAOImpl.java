package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteDAO;
import dto.ClienteDTO;

public class ClienteDAOImpl implements ClienteDAO {

    private static final String INSERT = "INSERT INTO Cliente(idCliente,nombre,CUIT,Domicilio,TelefonoEmpresa,Contacto,TelefonoContacto,CorreoElectronico,tipo_documento,condicion_iva,tipo_persona) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM Cliente WHERE idCliente = ?";
    private static final String READ_ALL = "SELECT * FROM Cliente ORDER BY Nombre ASC";
    private static final String READ_ALL_V = "SELECT * FROM Cliente";
    private static final String MAXIMO_ID = "SELECT MAX(idCliente) FROM Cliente";
    private static final String ID_POR_NOMBRE = "SELECT idCliente FROM Cliente WHERE nombre = ?";
    private static final String CUIT_POR_ID = "SELECT CUIT FROM Cliente WHERE idCliente = ?";
    private static final String POR_RAZON_SOCIAL = "SELECT CUIT, Domicilio FROM Cliente WHERE nombre = ?";
    private static final String CONTACTO_POR_NOMBRE = "SELECT Contacto FROM Cliente WHERE nombre = ?";
    private static final String EMAIL_POR_NOMBRE = "SELECT CorreoElectronico FROM Cliente WHERE nombre = ?";
    private static final String TELEFONO_POR_NOMBRE = "SELECT TelefonoContacto FROM Cliente WHERE nombre = ?";
    private static final String CANTIDAD_REPARACIONES = "SELECT COUNT(*) FROM Cliente INNER JOIN Equipos ON Cliente.idCliente = Equipos.idCliente WHERE Cliente.idCliente = ?";
    private static final String READ_ALL_PAGINADO = "SELECT * FROM Cliente ORDER BY Nombre ASC LIMIT ? OFFSET ?";
    private static final String COUNT_ALL = "SELECT COUNT(*) FROM Cliente";

    private Conexion conexion;

    public ClienteDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(ClienteDTO cliente) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getRazon_Social());
            stmt.setString(3, cliente.getCUIT());
            stmt.setString(4, cliente.getDomicilio());
            stmt.setString(5, cliente.getTelefonoEmpresa());
            stmt.setString(6, cliente.getContacto());
            stmt.setString(7, cliente.getTelefonoContacto());
            stmt.setString(8, cliente.getCorreoElectronico());
            stmt.setString(9, cliente.getTipoDocumento() != null ? cliente.getTipoDocumento() : "CUIT");
            stmt.setString(10, cliente.getCondicionIva() != null ? cliente.getCondicionIva() : "");
            stmt.setString(11, cliente.getTipoPersona() != null ? cliente.getTipoPersona() : "empresa");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar cliente: " + cliente.getId(), e);
            return false;
        }
    }

    @Override
    public boolean delete(ClienteDTO cliente) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar cliente: " + cliente.getId(), e);
            return false;
        }
    }

    @Override
    public List<ClienteDTO> readAll() {
        List<ClienteDTO> clientes = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todos los clientes", e);
        }
        return clientes;
    }

    @Override
    public List<ClienteDTO> readAllPaginado(int limit, int offset) {
        List<ClienteDTO> clientes = new ArrayList<>();
        String sql = READ_ALL_PAGINADO;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer clientes paginados", e);
        }
        return clientes;
    }

    @Override
    public int contarClientes() {
        String sql = COUNT_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LogDAO.error("Error al contar clientes", e);
        }
        return 0;
    }

    @Override
    public ClienteDTO obtenerPorRazonSocial(String nombre) {
        String sql = POR_RAZON_SOCIAL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ClienteDTO(0, nombre, rs.getString("CUIT"), rs.getString("Domicilio"),
                            null, null, null, null);
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener cliente por razon social: " + nombre, e);
        }
        return null;
    }

    @Override
    public int obtenerIDcliente() {
        String sql = MAXIMO_ID;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener maximo ID cliente", e);
        }
        return 0;
    }

    @Override
    public boolean edit(ClienteDTO cliente) {
        String sql = "UPDATE Cliente SET nombre = ?, CUIT = ?, Domicilio = ?, TelefonoEmpresa = ?, Contacto = ?, TelefonoContacto = ?, CorreoElectronico = ?, tipo_documento = ?, condicion_iva = ?, tipo_persona = ? WHERE idCliente = ?";
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, cliente.getRazon_Social());
            stmt.setString(2, cliente.getCUIT());
            stmt.setString(3, cliente.getDomicilio());
            stmt.setString(4, cliente.getTelefonoEmpresa());
            stmt.setString(5, cliente.getContacto());
            stmt.setString(6, cliente.getTelefonoContacto());
            stmt.setString(7, cliente.getCorreoElectronico());
            stmt.setString(8, cliente.getTipoDocumento() != null ? cliente.getTipoDocumento() : "CUIT");
            stmt.setString(9, cliente.getCondicionIva() != null ? cliente.getCondicionIva() : "");
            stmt.setString(10, cliente.getTipoPersona() != null ? cliente.getTipoPersona() : "empresa");
            stmt.setInt(11, cliente.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al editar cliente: " + cliente.getId(), e);
            return false;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void ListarClientes(JComboBox box) {
        String sql = READ_ALL_V;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        box.setModel(model);
        model.addElement(new ClienteDTO(0, "-- Seleccionar Cliente --", "", "", "", "", "", "", "CUIT", "", "empresa"));

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(mapearCliente(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar clientes en combo", e);
        }
    }

    @Override
    public int obtenerIDporNombre(String nombreCliente) {
        String sql = ID_POR_NOMBRE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idCliente");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener ID por nombre: " + nombreCliente, e);
        }
        return 0;
    }

    @Override
    public String obtenerContactoPorCliente(String nombreCliente) {
        String sql = CONTACTO_POR_NOMBRE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Contacto");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener contacto: " + nombreCliente, e);
        }
        return "";
    }

    @Override
    public String obtenerEmailPorCliente(String nombreCliente) {
        String sql = EMAIL_POR_NOMBRE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("CorreoElectronico");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener email: " + nombreCliente, e);
        }
        return "";
    }

    @Override
    public boolean obtenerReparacionxIDCliente(int idCliente) {
        String sql = CANTIDAD_REPARACIONES;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al verificar reparaciones por cliente: " + idCliente, e);
        }
        return false;
    }

    @Override
    public String obtenerTelefonoPorCliente(String nombreCliente) {
        String sql = TELEFONO_POR_NOMBRE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("TelefonoContacto");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener telefono: " + nombreCliente, e);
        }
        return "";
    }

    @Override
    public String dameCuitPorIdCliente(int idCliente) {
        String sql = CUIT_POR_ID;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("CUIT");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener CUIT por ID: " + idCliente, e);
        }
        return "";
    }

    private ClienteDTO mapearCliente(ResultSet rs) throws SQLException {
        String tipoDoc = null;
        String condIva = null;
        String tipoPer = null;
        try { tipoDoc = rs.getString("tipo_documento"); } catch (SQLException e) {}
        try { condIva = rs.getString("condicion_iva"); } catch (SQLException e) {}
        try { tipoPer = rs.getString("tipo_persona"); } catch (SQLException e) {}
        return new ClienteDTO(
            rs.getInt("idCliente"),
            rs.getString("nombre"),
            rs.getString("CUIT"),
            rs.getString("Domicilio"),
            rs.getString("TelefonoEmpresa"),
            rs.getString("Contacto"),
            rs.getString("TelefonoContacto"),
            rs.getString("CorreoElectronico"),
            tipoDoc != null ? tipoDoc : "CUIT",
            condIva != null ? condIva : "",
            tipoPer != null ? tipoPer : "empresa"
        );
    }
}
