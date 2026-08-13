package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencia.conexion.Conexion;

public class ReparacionEstadisticasManager {

    private Conexion conexion;

    public ReparacionEstadisticasManager(Conexion conexion) {
        this.conexion = conexion;
    }

    // ========== ESTADÍSTICAS GENERALES POR AÑO ==========

    public int ingresosPorAnio(int anio) {
        return executeCountQuery(SQLQueries.INGRESOS_POR_ANIO, anio);
    }

    public int diagnosticosPorAnio(int anio) {
        return executeCountQuery(SQLQueries.DIAGNOSTICOS_POR_ANIO, anio);
    }

    public double facturacionPesoPorAnio(int anio) {
        return executeSumQuery(SQLQueries.FACTURACION_PESOS_POR_ANIO, anio);
    }

    public double facturacionDolarPorAnio(int anio) {
        return executeSumQuery(SQLQueries.FACTURACION_DOLAR_POR_ANIO, anio);
    }

    public int reparadosPorAnio(int anio) {
        return executeCountQuery(SQLQueries.REPARADOS_POR_ANIO, anio);
    }

    public int sinFallasPorAnio(int anio) {
        return executeCountQuery(SQLQueries.SIN_FALLAS_POR_ANIO, anio);
    }

    public int enGtiaPorAnio(int anio) {
        return executeCountQuery(SQLQueries.REP_EN_GTIA_POR_ANIO, anio);
    }

    public int enRepPorAnio(int anio) {
        return executeCountQuery(SQLQueries.EN_REP_POR_ANIO, anio);
    }

    public int ventasPorAnio(int anio) {
        return executeCountQuery(SQLQueries.VENTAS_POR_ANIO, anio);
    }

    public int sinRepPorAnio(int anio) {
        return executeCountQuery(SQLQueries.SIN_REP_POR_ANIO, anio);
    }

    public int repAcepPorAnio(int anio) {
        return executeCountQuery(SQLQueries.REP_ACEP_POR_ANIO, anio);
    }

    public int repNoAcepPorAnio(int anio) {
        return executeCountQuery(SQLQueries.REP_NO_ACEP_POR_ANIO, anio);
    }

    public int repEsperaPorAnio(int anio) {
        return executeCountQuery(SQLQueries.REP_ESPERA_POR_ANIO, anio);
    }

    // ========== ESTADÍSTICAS POR CLIENTE ==========

    public int ingresosXanioXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_INGRESOS_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int reparadosXanioXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_REPARADOS_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int sinFallaXanioXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_SIN_FALLA_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int gtiaXanioXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_REP_EN_GTIA_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int enRepXanioXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_EN_REP_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int ventasXanioXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_VENTAS_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int sinRepXanioXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_SIN_REP_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int repAcepXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_REP_ACEP_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int repNoAcepXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_REP_NO_ACEP_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public int repEsperaXcliente(int anio, int idCliente) {
        return executeCountQuery(SQLQueries.TOTAL_REP_ESPERA_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public double facturacionPesoPorAnioPorCliente(int anio, int idCliente) {
        return executeSumQuery(SQLQueries.FACTURACION_PESO_POR_ANIO_POR_CLIENTE, anio, idCliente);
    }

    public double facturacionDolarPorAnioPorCliente(int anio, int idCliente) {
        return executeSumQuery(SQLQueries.FACTURACION_DOLAR_POR_ANIO_POR_CLIENTE, anio, idCliente);
    }

    // ========== ESTADÍSTICAS POR TÉCNICO ==========

    public int diagnosticosXanioXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_DIAGNOSTICOS_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int reparadosXanioXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_REPARADOS_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int sinFallaXanioXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_SIN_FALLA_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int gtiaXanioXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_REP_EN_GTIA_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int enRepXanioXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_EN_REP_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int ventasXanioXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_VENTAS_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int sinRepXanioXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_SIN_REP_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int repAcepXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_REP_ACEP_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int repNoAcepXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_REP_NO_ACEP_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public int repEsperaXtecnico(int anio, int idTecnico) {
        return executeCountQuery(SQLQueries.TOTAL_REP_ESPERA_X_ANIO_X_TECNICO, anio, idTecnico);
    }

    public double facturacionPesoPorAnioPorTecnico(int anio, int idTecnico) {
        return executeSumQuery(SQLQueries.FACTURACION_PESO_POR_ANIO_POR_TECNICO, anio, idTecnico);
    }

    public double facturacionDolarPorAnioPorTecnico(int anio, int idTecnico) {
        return executeSumQuery(SQLQueries.FACTURACION_DOLAR_POR_ANIO_POR_TECNICO, anio, idTecnico);
    }

    // ========== ESTADÍSTICAS POR MES - GENERALES ==========

    public List<Integer> ingresosPorAnioPorMes(int anio) {
        return executeMonthlyCountQuery(SQLQueries.INGRESOS_POR_ANIO_X_MES, anio);
    }

    public List<Integer> diagnosticoPorAnioPorMes(int anio) {
        return executeMonthlyCountQuery(SQLQueries.DIAGNOSTICO_POR_ANIO_X_MES, anio);
    }

    public List<Double> facturacionPorAnioPorMes(int anio) {
        return executeMonthlySumQuery(SQLQueries.FACTURACION_POR_ANIO_X_MES, anio);
    }

    // ========== ESTADÍSTICAS POR MES Y TÉCNICO ==========

    public List<Integer> diagnosticoPorAnioPorTecnico(int anio, int tecnico) {
        return executeMonthlyCountQuery(SQLQueries.DIAGNOSTICO_POR_ANIO_X_TECNICO, anio, tecnico);
    }

    public List<Integer> aceptacionesPorAnioPorTecnico(int anio, int tecnico) {
        return executeMonthlyCountQuery(SQLQueries.ACEPTACIONES_POR_ANIO_X_TECNICO, anio, tecnico);
    }

    public List<Double> facturacionPorAnioPorTecnico(int anio, int tecnico) {
        return executeMonthlySumQuery(SQLQueries.FACTURACION_POR_ANIO_X_TECNICO, anio, tecnico);
    }

    public List<Double> facturacionDolaresPorAnioPorTecnico(int anio, int idTecnico) {
        return executeMonthlySumQuery(SQLQueries.FACTURACION_DOLAR_POR_ANIO_X_TECNICO_X_MES, anio, idTecnico);
    }

    public List<Integer> reparadosXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.REPARADOS_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> enGtiaXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.EN_GTIA_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> sinFallaXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.SIN_FALLA_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> enRepXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.EN_REP_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> ventasXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.VENTAS_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> sinRepXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.SIN_REP_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> repAcepXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.REP_ACEP_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> repNoAcepXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.REP_NO_ACEP_X_MES_X_TECNICO, anio, idTecnico);
    }

    public List<Integer> esperaRepXmesXtecnico(int anio, int idTecnico) {
        return executeMonthlyCountQuery(SQLQueries.REP_ESPERA_X_MES_X_TECNICO, anio, idTecnico);
    }

    // ========== ESTADÍSTICAS POR MES Y CLIENTE ==========

    public List<Integer> ingresosPorAnioPorCliente(int anio, int idCliente) {
        return executeMonthlyCountQuery(SQLQueries.INGRESOS_X_ANIO_X_CLIENTE, anio, idCliente);
    }

    public List<Double> facturacionPorAnioPorCliente(int anio, int idCliente) {
        return executeMonthlySumQuery(SQLQueries.FACTURACION_POR_ANIO_X_CLIENTE, anio, idCliente);
    }

    public List<Integer> aceptacionesPorAnioPorCliente(int anio, int idCliente) {
        return executeMonthlyCountQuery(SQLQueries.ACEPTACIONES_POR_ANIO_X_CLIENTE, anio, idCliente);
    }

    // ========== MÉTODOS PRIVADOS AUXILIARES ==========

    private int executeCountQuery(String query, int... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            setParameters(statement, params);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToSingleInteger(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    private double executeSumQuery(String query, int... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            setParameters(statement, params);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToSingleDouble(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    private List<Integer> executeMonthlyCountQuery(String query, int... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            setParameters(statement, params);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToMonthlyIntegerList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return initializeMonthlyIntegerList();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    private List<Double> executeMonthlySumQuery(String query, int... params) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            setParameters(statement, params);
            resultSet = statement.executeQuery();
            
            return ReparacionMapper.mapToMonthlyDoubleList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return initializeMonthlyDoubleList();
        } finally {
            closeResources(statement, resultSet, conn);
        }
    }

    private void setParameters(PreparedStatement statement, int[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setInt(i + 1, params[i]);
        }
    }

    private List<Integer> initializeMonthlyIntegerList() {
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            lista.add(0);
        }
        return lista;
    }

    private List<Double> initializeMonthlyDoubleList() {
        List<Double> lista = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            lista.add(0.0);
        }
        return lista;
    }

    private void closeResources(PreparedStatement statement, ResultSet resultSet, Connection conn) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}