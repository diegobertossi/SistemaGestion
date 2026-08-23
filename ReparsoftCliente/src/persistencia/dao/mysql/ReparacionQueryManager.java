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
            LogDAO.error("Error en insert", e);
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
            LogDAO.error("Error en insertEquipo", e);
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
            LogDAO.error("Error en delete", e);
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
            LogDAO.error("Error en readAll", e);
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene una página de reparaciones (paginación server-side).
     * @param limit  cantidad de registros por página
     * @param offset desde qué registro empezar (página * limit)
     */
    // NUEVO: paginación server-side
    public List<ReparacionDTO> readAllPaginado(int limit, int offset) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.READ_ALL_PAGINADO);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            resultSet = statement.executeQuery();

            return ReparacionMapper.mapToReparacionList(resultSet);
        } catch (SQLException e) {
            LogDAO.error("Error en readAllPaginado", e);
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Cuenta el total de reparaciones para calcular páginas.
     * Usa el mismo JOIN/WHERE que READ_ALL para ser consistente.
     */
    // NUEVO: conteo total para paginación
    public int contarReparaciones() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.COUNT_REPARACIONES);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            LogDAO.error("Error en contarReparaciones", e);
            return 0;
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
            LogDAO.error("Error en obtenerReparacionXELS", e);
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
            LogDAO.error("Error en obtenerReparacionXSerie", e);
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
                return els == 0 ? 987 : els;
            }
            return 987;
        } catch (SQLException e) {
            LogDAO.error("Error en obtenerMaximoELS", e);
            return 987;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene el máximo ELS para Buenos Aires
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
                return els == 0 ? 16549 : els;
            }
            return 16549;
        } catch (SQLException e) {
            LogDAO.error("Error en obtenerMaximoELSBSAS", e);
            return 16549;
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
            LogDAO.error("Error en obtenerMaximoIDEquipo", e);
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
            LogDAO.error("Error en update", e);
            return false;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza los datos del equipo con fecha
     */
    public boolean updateEquipo(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            String fechaFabr = reparacion.getFechaFabr();
            boolean conFecha = (fechaFabr != null && !fechaFabr.isEmpty());
            String query = conFecha ? SQLQueries.UPDATE_EQUIPO : SQLQueries.UPDATE_EQUIPO_SIN_FECHA;
            statement = conn.prepareStatement(query);
            setEquipoParameters(statement, reparacion, conFecha);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error en updateEquipo", e);
            return false;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Agrega remito a una reparación
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
            LogDAO.error("Error en updateAgregarRemito", e);
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Marca reparaciones como enviadas
     */
    public void updateMarcarEnviados(ReparacionDTO reparacion) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_MARCAR_ENVIADOS);
            statement.setString(1, reparacion.getEstadoFisico());
            statement.setString(2, reparacion.getFecha_Salida());
            statement.setInt(3, reparacion.getELS());
            statement.executeUpdate();
        } catch (SQLException e) {
            LogDAO.error("Error en updateMarcarEnviados", e);
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Anula el remito de una reparación
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
            LogDAO.error("Error en updateAnularRemito", e);
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Reasigna todas las reparaciones de un usuario a otro en una sola
     * operación (ej. al eliminar un técnico). Evita cargar el READ_ALL
     * completo para hacer un update fila por fila.
     * @return cantidad de filas actualizadas
     */
    public int updateReasignarUsuario(int idUsuarioNuevo, int idUsuarioAnterior) {
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.UPDATE_REASIGNAR_USUARIO);
            statement.setInt(1, idUsuarioNuevo);
            statement.setInt(2, idUsuarioAnterior);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LogDAO.error("Error en updateReasignarUsuario", e);
            return 0;
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza datos de presupuesto
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
            LogDAO.error("Error en updatePresupuesto", e);
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza la aceptación de una reparación
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
            LogDAO.error("Error en updateAceptacion", e);
        } finally {
            closeResources(statement, null, conn);
        }
    }

    /**
     * Actualiza el pago de una reparación
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
            LogDAO.error("Error en updatePago", e);
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
            LogDAO.error("Error en readAllXIDClienteIDSucursal", e);
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene reparaciones por ID de cliente y sucursal con las columnas mínimas
     * que muestra la tabla de equipos del remito (mismas filas, menos datos transferidos).
     */
    public List<ReparacionDTO> readAllXIDClienteIDSucursalResumido(Integer idCliente, Integer idSucursal) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(SQLQueries.REPARACIONES_X_CLIENTE_SUCURSAL_RESUMIDO);
            statement.setInt(1, idCliente);
            statement.setInt(2, idSucursal);
            resultSet = statement.executeQuery();

            List<ReparacionDTO> reparaciones = new ArrayList<>();
            while (resultSet.next()) {
                reparaciones.add(new ReparacionDTO(
                        resultSet.getInt("ELS"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Marca"),
                        resultSet.getString("Modelo"),
                        resultSet.getString("NumeroDeSerie"),
                        resultSet.getString("Aviso"),
                        resultSet.getString("EstadoTecnico"),
                        resultSet.getString("EstadoComercial"),
                        resultSet.getBoolean("Agregadoaremito")));
            }
            return reparaciones;
        } catch (SQLException e) {
            LogDAO.error("Error en readAllXIDClienteIDSucursalResumido", e);
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
            LogDAO.error("Error en readAllXIDRemito", e);
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
            LogDAO.error("Error en readAllXComponenteOriginal", e);
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    /**
     * Obtiene reparaciones por componente de reemplazo
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
            LogDAO.error("Error en readAllXComponenteReemplazo", e);
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
            LogDAO.error("Error en readAllListadoMarcarAceptaciones", e);
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
            LogDAO.error("Error en buscarEnCampos", e);
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
            LogDAO.error("Error en closeResources", e);
        }
    }

    public List<ReparacionDTO> buscarHistorialPrecios(String criterio, String texto) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
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
            LogDAO.error("Error en buscarHistorialPrecios", e);
            return new ArrayList<>();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }
}
