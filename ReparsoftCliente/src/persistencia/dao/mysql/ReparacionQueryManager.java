package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.ReparacionDTO;
import persistencia.conexion.Conexion;

public class ReparacionQueryManager {

    private Conexion conexion;
    private SimpleDateFormat inputFormat;
    private SimpleDateFormat outputFormat;

    public ReparacionQueryManager(Conexion conexion) {
        this.conexion = conexion;
        this.inputFormat = new SimpleDateFormat("yyyyMMdd");
        this.outputFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * Inserta una nueva reparación
     */
    public boolean insert(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.INSERT);
            
            statement.setInt(1, reparacion.getELS());
            statement.setString(2, reparacion.getFecha_Entrada());
            statement.setString(3, reparacion.getFalla());
            statement.setString(4, reparacion.getEstadoFisico());
            statement.setString(5, reparacion.getEstadoTecnico());
            statement.setString(6, reparacion.getEstadoComercial());
            statement.setString(7, reparacion.getRemitoCliente());
            statement.setInt(8, reparacion.getIDEquipo());
            statement.setInt(9, reparacion.getidUsuario());
            statement.setString(10, reparacion.getLugarDeIngreso());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Inserta un nuevo equipo
     */
    public boolean insertEquipo(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.INSERT_EQUIPO);
            
            statement.setInt(1, reparacion.getIDEquipo());
            statement.setString(2, reparacion.getNombreEquipo());
            statement.setString(3, reparacion.getModelo());
            statement.setString(4, reparacion.getMarca());
            statement.setString(5, reparacion.getNumeroDeSerie());
            statement.setString(6, reparacion.getFechaFabr());
            statement.setString(7, reparacion.getAviso());
            statement.setString(8, reparacion.getClienteCliente());
            statement.setString(9, reparacion.getRemitoCliente());
            statement.setInt(10, reparacion.getIDCliente());
            statement.setInt(11, reparacion.getIDSuc());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Elimina una reparación por ELS
     */
    public boolean delete(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.DELETE);
            statement.setString(1, Integer.toString(reparacion.getELS()));
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Obtiene todas las reparaciones
     */
    public List<ReparacionDTO> readAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToReparacionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene una reparación por ELS
     */
    public ReparacionDTO obtenerReparacionXELS(Integer els) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_X_ELS);
            statement.setInt(1, els);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return ReparacionMapper.mapToReparacionDTO(resultSet);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene una reparación por número de serie
     */
    public ReparacionDTO obtenerReparacionXSerie(String serie) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_X_SERIE);
            statement.setString(1, serie);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return ReparacionMapper.mapToReparacionDTO(resultSet);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene el máximo ELS
     */
    public int obtenerMaximoELS() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.MAXIMO_ELS);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int els = resultSet.getInt("MAX(ELS)");
                return els == 0 ? 987 : els; // Valor por defecto si no hay registros
            }
            return 987;
        } catch (SQLException e) {
            e.printStackTrace();
            return 987;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene el máximo ELS para BSAS
     */
    public int obtenerMaximoELSBSAS() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.MAXIMO_ELS);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int els = resultSet.getInt("MAX(ELS)");
                return els == 0 ? 24899 : els; // Valor por defecto si no hay registros
            }
            return 24899;
        } catch (SQLException e) {
            e.printStackTrace();
            return 24899;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene el máximo ID de equipo
     */
    public int obtenerMaximoIDEquipo() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.MAXIMO_ID_EQUIPO);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("MAX(IdEquipo)");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Actualiza una reparación completa
     */
    public boolean update(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_REPARACION);
            
            setUpdateParameters(statement, reparacion);
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza un equipo
     */
    public boolean updateEquipo(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            
            if (reparacion.getFechaFabr() != null) {
                statement = conn.prepareStatement(SQLQueries.UPDATE_EQUIPO);
                setEquipoParameters(statement, reparacion, true);
            } else {
                statement = conn.prepareStatement(SQLQueries.UPDATE_EQUIPO_SIN_FECHA);
                setEquipoParameters(statement, reparacion, false);
            }
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza para agregar remito
     */
    public void updateAgregarRemito(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_AGREGAR_REMITO);
            
            statement.setBoolean(1, reparacion.getAgregadoaremito());
            statement.setBoolean(2, reparacion.getRemitoGenerado());
            statement.setInt(3, reparacion.getidRemito());
            statement.setInt(4, reparacion.getELS());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza para marcar como enviados
     */
    public void updateMarcarEnviados(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_MARCAR_ENVIADOS);
            
            statement.setString(1, reparacion.getEstadoFisico());
            setTimestampParameter(statement, 2, reparacion.getFecha_Salida());
            statement.setInt(3, reparacion.getELS());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza para anular remito
     */
    public void updateAnularRemito(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_ANULAR_REMITO);
            
            statement.setString(1, reparacion.getEstadoFisico());
            statement.setBoolean(2, reparacion.getAgregadoaremito());
            statement.setBoolean(3, reparacion.getRemitoGenerado());
            statement.setInt(4, reparacion.getidRemito());
            statement.setInt(5, reparacion.getELS());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza presupuesto
     */
    public void updatePresupuesto(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_PRESUPUESTO);
            
            statement.setString(1, reparacion.getInformecliente());
            setBigDecimalParameter(statement, 2, reparacion.getPrecioPeso());
            setBigDecimalParameter(statement, 3, reparacion.getPrecioDolar());
            setBooleanParameter(statement, 4, reparacion.getPresupuestoGenerado());
            setBooleanParameter(statement, 5, reparacion.getPresupuestoEnviado());
            setBooleanParameter(statement, 6, reparacion.getWORDgenerado());
            setBooleanParameter(statement, 7, reparacion.getWORDenviado());
            statement.setInt(8, reparacion.getELS());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza aceptación
     */
    public void updateAceptacion(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_ACEPTACION);
            
            setTimestampParameter(statement, 1, reparacion.getFechAceptacion());
            statement.setString(2, reparacion.getEstadoComercial());
            statement.setInt(3, reparacion.getELS());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza pago
     */
    public void updatePago(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_PAGO);
            
            setBigDecimalParameter(statement, 1, reparacion.getPrecioPeso());
            setBigDecimalParameter(statement, 2, reparacion.getPrecioDolar());
            setBigDecimalParameter(statement, 3, reparacion.getPago());
            statement.setString(4, reparacion.getEstadoComercial());
            statement.setInt(5, reparacion.getELS());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Obtiene reparaciones por ID de cliente y sucursal
     */
    public List<ReparacionDTO> readAllXIDClienteIDSucursal(Integer idCliente, Integer idSucursal) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_X_ID_CLIENTE_ID_SUCURSAL);
            statement.setInt(1, idCliente);
            statement.setInt(2, idSucursal);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToReparacionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene reparaciones por ID de remito
     */
    public List<ReparacionDTO> readAllXIDRemito(int idRemito) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_X_ID_REMITO);
            statement.setInt(1, idRemito);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToReparacionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene reparaciones por componente original
     */
    public List<ReparacionDTO> readAllXComponenteOriginal(String componente) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_X_COMP_ORIGINAL);
            statement.setString(1, componente);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToComponenteReparacionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene reparaciones por componente reemplazo
     */
    public List<ReparacionDTO> readAllXComponenteReemplazo(String componente) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_X_COMP_REEMPLAZO);
            statement.setString(1, componente);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToComponenteReparacionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene listado para marcar aceptaciones
     */
    public List<ReparacionDTO> readAllListadoMarcarAceptaciones() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_LISTADO_MARCAR_ACEPTACIONES);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToBasicReparacionList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Búsqueda por campo y texto
     */
    public List<Integer> buscarEnCampos(String campo, String texto) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            if (!ReparacionMapper.esCampoValido(campo)) {
                throw new IllegalArgumentException("Campo no válido: " + campo);
            }

            String query = String.format(SQLQueries.BUSQUEDA_POR_CAMPO_Y_TEXTO, campo);
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            statement.setString(1, "%" + texto + "%");
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToIntegerList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    // ========== MÉTODOS PRIVADOS AUXILIARES ==========

    private void setUpdateParameters(PreparedStatement statement, ReparacionDTO reparacion) throws SQLException {
        int paramIndex = 1;
        
        setTimestampParameter(statement, paramIndex++, reparacion.getFecha_Entrada());
        setTimestampParameter(statement, paramIndex++, reparacion.getFechadereparacion());
        statement.setString(paramIndex++, reparacion.getFalla());
        statement.setString(paramIndex++, reparacion.getSolucion());
        statement.setString(paramIndex++, reparacion.getInformecliente());
        statement.setInt(paramIndex++, reparacion.getidUsuario());
        statement.setString(paramIndex++, reparacion.getNombreUsuario());
        statement.setString(paramIndex++, reparacion.getEstadoFisico());
        statement.setString(paramIndex++, reparacion.getEstadoTecnico());
        statement.setString(paramIndex++, reparacion.getEstadoComercial());
        statement.setString(paramIndex++, reparacion.getRemitoCliente());
        statement.setString(paramIndex++, reparacion.getOrdendeCompra());
        setBooleanParameter(statement, paramIndex++, reparacion.getAgregadoaremito());
        setBooleanParameter(statement, paramIndex++, reparacion.getRemitoGenerado());
        statement.setInt(paramIndex++, reparacion.getIDEquipo());
        statement.setInt(paramIndex++, reparacion.getidRemito());
        setBigDecimalParameter(statement, paramIndex++, reparacion.getPrecioPeso());
        setBigDecimalParameter(statement, paramIndex++, reparacion.getPrecioDolar());
        setTimestampParameter(statement, paramIndex++, reparacion.getFechAceptacion());
        setBooleanParameter(statement, paramIndex++, reparacion.getPresupuestoGenerado());
        setBooleanParameter(statement, paramIndex++, reparacion.getPresupuestoEnviado());
        setBooleanParameter(statement, paramIndex++, reparacion.getWORDgenerado());
        setBooleanParameter(statement, paramIndex++, reparacion.getWORDenviado());
        setBooleanParameter(statement, paramIndex++, reparacion.getAvisoEnviado());
        setBigDecimalParameter(statement, paramIndex++, reparacion.getPago());
        setTimestampParameter(statement, paramIndex++, reparacion.getFecha_Salida());
        statement.setString(paramIndex++, reparacion.getNrofactura());
        statement.setString(paramIndex++, reparacion.getLugarDeIngreso());
        
        
        // Llave primaria
        statement.setInt(paramIndex, reparacion.getELS());
    }

    private void setEquipoParameters(PreparedStatement statement, ReparacionDTO reparacion, boolean conFecha) throws SQLException {
        int paramIndex = 1;
        
        statement.setString(paramIndex++, reparacion.getNombreEquipo());
        statement.setString(paramIndex++, reparacion.getModelo());
        statement.setString(paramIndex++, reparacion.getMarca());
        statement.setString(paramIndex++, reparacion.getNumeroDeSerie());
        statement.setString(paramIndex++, reparacion.getAviso());
        statement.setString(paramIndex++, reparacion.getClienteCliente());
        statement.setString(paramIndex++, reparacion.getRemitoCliente());
        statement.setInt(paramIndex++, reparacion.getIDCliente());
        statement.setInt(paramIndex++, reparacion.getIDSuc());
        
        if (conFecha) {
            statement.setString(paramIndex++, reparacion.getFechaFabr());
        }
        
        statement.setInt(paramIndex, reparacion.getIDEquipo());
    }

    private void setTimestampParameter(PreparedStatement statement, int index, String fecha) throws SQLException {
        if (fecha != null) {
            try {
                Date parsedDate = inputFormat.parse(fecha);
                String formattedDate = outputFormat.format(parsedDate) + " 00:00:00";
                statement.setTimestamp(index, Timestamp.valueOf(formattedDate));
            } catch (Exception e) {
                throw new SQLException("Error al convertir fecha: " + fecha, e);
            }
        } else {
            statement.setNull(index, java.sql.Types.TIMESTAMP);
        }
    }

    private void setBigDecimalParameter(PreparedStatement statement, int index, Double valor) throws SQLException {
        if (valor != null) {
            statement.setBigDecimal(index, new BigDecimal(valor));
        } else {
            statement.setNull(index, java.sql.Types.DECIMAL);
        }
    }

    private void setBooleanParameter(PreparedStatement statement, int index, Boolean valor) throws SQLException {
        if (valor != null) {
            statement.setBoolean(index, valor);
        } else {
            statement.setNull(index, java.sql.Types.BOOLEAN);
        }
    }

    private void closeResources(PreparedStatement statement, ResultSet resultSet, Connection conn) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conexion.cerrarConexion();
            }
        }
    }

    public List<ReparacionDTO> buscarHistorialPrecios(String criterio, String texto) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            // Mapear código interno → columna SQL válida
            String columna;
            switch (criterio) {
                case "MARCA":         columna = "Equipos.Marca";   break;
                case "MODELO":        columna = "Equipos.Modelo";  break;
                case "NOMBRE_EQUIPO":
                default:              columna = "Equipos.Nombre";  break;
            }

            if (!ReparacionMapper.CAMPOS_PERMITIDOS_HISTORIAL.contains(columna)) {
                throw new IllegalArgumentException("Columna no válida: " + columna);
            }

            String query = String.format(SQLQueries.BUSQUEDA_HISTORIAL_PRECIOS, columna);
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            statement.setString(1, "%" + texto + "%");
            resultSet = statement.executeQuery();

            return ReparacionMapper.mapToHistorialPreciosList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }
}