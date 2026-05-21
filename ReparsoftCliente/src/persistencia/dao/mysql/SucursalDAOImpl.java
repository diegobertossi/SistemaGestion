package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.SucursalDAO;
import dto.ReparacionDTO;
import dto.SucursalDTO;

public class SucursalDAOImpl implements SucursalDAO {

    private static final String INSERT = "INSERT INTO Sucursal(IdSucursal,NombreSucursal,idCliente,DomicilioSucursal,ContactoSucursal,TelefonoSucursal,CorreoElectronico) VALUES(?,?,?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM Sucursal WHERE IdSucursal = ?";
    private static final String READ_ALL = "SELECT * FROM Sucursal";
    private static final String MAXIMO_ID = "SELECT MAX(idsucursal) FROM Sucursal";
    private static final String CANTIDAD_SUC_X_CLIENTE = "SELECT COUNT(*) as total FROM Sucursal WHERE idCliente = ?";
    private static final String CANTIDAD_REP_X_SUC = "SELECT COUNT(*) as total FROM Sucursal INNER JOIN Equipos ON Sucursal.idsucursal = Equipos.IdSucursal WHERE Sucursal.idsucursal = ?";
    private static final String READ_BY_CLIENTE = "SELECT * FROM Sucursal WHERE idCliente = ? ORDER BY NombreSucursal ASC";
    private static final String READ_NOMBRES = "SELECT NombreSucursal FROM Sucursal GROUP BY NombreSucursal";
    private static final String ID_POR_NOMBRE = "SELECT IdSucursal FROM Sucursal WHERE NombreSucursal = ? AND idCliente = ?";
    private static final String UPDATE_SUCURSAL = "UPDATE Sucursal SET NombreSucursal = ?, idCliente = ?, DomicilioSucursal = ?, ContactoSucursal = ?, TelefonoSucursal = ?, CorreoElectronico = ? WHERE IdSucursal = ?";

    private Conexion conexion;

    public SucursalDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(SucursalDTO sucursal) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, sucursal.getIdSucursal());
            stmt.setString(2, sucursal.getNombreSucursal());
            stmt.setInt(3, sucursal.getIdClientesuc());
            stmt.setString(4, sucursal.getDomicilioSucursal());
            stmt.setString(5, sucursal.getContactoSucursal());
            stmt.setString(6, sucursal.getTelefonoSucursal());
            stmt.setString(7, sucursal.getCorreoElectronico());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar sucursal: " + sucursal.getIdSucursal(), e);
            return false;
        }
    }

    @Override
    public boolean delete(SucursalDTO sucursal) {
        String sql = DELETE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, sucursal.getIdSucursal());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al eliminar sucursal: " + sucursal.getIdSucursal(), e);
            return false;
        }
    }

    @Override
    public List<SucursalDTO> readAll() {
        List<SucursalDTO> sucursales = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sucursales.add(mapearSucursal(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todas las sucursales", e);
        }
        return sucursales;
    }

    @Override
    public List<SucursalDTO> obtenerSucursalXidCliente(Integer idCliente) {
        List<SucursalDTO> sucursales = new ArrayList<>();
        String sql = READ_BY_CLIENTE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sucursales.add(mapearSucursal(rs));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer sucursales por cliente: " + idCliente, e);
        }
        return sucursales;
    }

    @Override
    public int obtenerIDsucursal() {
        String sql = MAXIMO_ID;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener maximo ID sucursal", e);
        }
        return 0;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarSucursalesxCliente(JComboBox box, int idCliente) {
        String sql = READ_BY_CLIENTE;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        box.setModel(model);

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.addElement(new SucursalDTO(
                        rs.getInt("IdSucursal"),
                        rs.getString("NombreSucursal"),
                        rs.getInt("idCliente"),
                        rs.getString("DomicilioSucursal"),
                        rs.getString("ContactoSucursal"),
                        rs.getString("TelefonoSucursal"),
                        rs.getString("CorreoElectronico")
                    ));
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar sucursales por cliente: " + idCliente, e);
        }
    }

    @Override
    public int obtenercantidaddeSucursales(int idCliente) {
        String sql = CANTIDAD_SUC_X_CLIENTE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al contar sucursales por cliente: " + idCliente, e);
        }
        return 0;
    }

    @Override
    public boolean edit(SucursalDTO sucursal) {
        String sql = UPDATE_SUCURSAL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, sucursal.getNombreSucursal());
            stmt.setInt(2, sucursal.getIdClientesuc());
            stmt.setString(3, sucursal.getDomicilioSucursal());
            stmt.setString(4, sucursal.getContactoSucursal());
            stmt.setString(5, sucursal.getTelefonoSucursal());
            stmt.setString(6, sucursal.getCorreoElectronico());
            stmt.setInt(7, sucursal.getIdSucursal());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al editar sucursal: " + sucursal.getIdSucursal(), e);
            return false;
        }
    }

    @Override
    public boolean obtenerReparacionxIDsSuc(int idSucursal) {
        String sql = CANTIDAD_REP_X_SUC;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setInt(1, idSucursal);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al verificar reparaciones por sucursal: " + idSucursal, e);
        }
        return false;
    }

    @Override
    public int obtenerIDporNombre(String nombreSucursal, int idCliente) {
        String sql = ID_POR_NOMBRE;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, nombreSucursal);
            stmt.setInt(2, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdSucursal");
                }
            }
        } catch (SQLException e) {
            LogDAO.error("Error al obtener ID por nombre: " + nombreSucursal, e);
        }
        return 0;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void ListarSucursales(JComboBox box) {
        String sql = READ_NOMBRES;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        box.setModel(model);

        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addElement(new ReparacionDTO(rs.getString(1)));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al listar sucursales", e);
        }
    }

    private SucursalDTO mapearSucursal(ResultSet rs) throws SQLException {
        return new SucursalDTO(
            rs.getInt("IdSucursal"),
            rs.getString("NombreSucursal"),
            rs.getInt("idCliente"),
            rs.getString("DomicilioSucursal"),
            rs.getString("ContactoSucursal"),
            rs.getString("TelefonoSucursal"),
            rs.getString("CorreoElectronico")
        );
    }
}
