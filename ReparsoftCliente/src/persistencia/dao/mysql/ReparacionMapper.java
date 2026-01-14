package persistencia.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ReparacionDTO;

public class ReparacionMapper {

    /**
     * Mapea un ResultSet a un objeto ReparacionDTO completo
     */
    public static ReparacionDTO mapToReparacionDTO(ResultSet resultSet) throws SQLException {
        return new ReparacionDTO(
            resultSet.getInt("ELS"),
            resultSet.getString("FechaEntrada"),
            resultSet.getString("FechadeDiagnostico"),
            resultSet.getString("Falla"),
            resultSet.getString("Solucion"),
            resultSet.getString("Informecliente"),
            resultSet.getInt("idUsuario"),
            resultSet.getString("EstadoFisico"),
            resultSet.getString("EstadoTecnico"),
            resultSet.getString("EstadoComercial"),
            resultSet.getString("RemitoCliente"),
            resultSet.getString("OrdendeCompra"),
            resultSet.getBoolean("Agregadoaremito"),
            resultSet.getBoolean("RemitoGenerado"),
            resultSet.getInt("idEquipo"),
            resultSet.getInt("idRemito"),
            resultSet.getDouble("PrecioPeso"),
            resultSet.getDouble("PrecioDolar"),
            resultSet.getString("FechAceptacion"),
            resultSet.getBoolean("PresupuestoGenerado"),
            resultSet.getDouble("Pago"),
            resultSet.getBoolean("PresupuestoEnviado"),
            resultSet.getString("Equipos.Nombre"),
            resultSet.getString("email"),
            resultSet.getString("Modelo"),
            resultSet.getString("Marca"),
            resultSet.getString("NumeroDeSerie"),
            resultSet.getString("Aviso"),
            resultSet.getString("ClienteCliente"),
            resultSet.getInt("idCliente"),
            resultSet.getInt("idSucursal"),
            resultSet.getString("nombre"),
            resultSet.getString("NombreSucursal"),
            resultSet.getString("NombreUsuario"),
            resultSet.getInt("Codigo"),
            resultSet.getInt("NumeroRemitoSalida"),
            resultSet.getString("FechaFabr"),
            resultSet.getBoolean("AvisoEnviado"),
            resultSet.getBoolean("WordGenerado"),
            resultSet.getBoolean("WordEnviado"),
            resultSet.getString("lugar_de_ingreso"),
            resultSet.getString("FechaSalida")
        );
    }

    /**
     * Mapea un ResultSet a un objeto ReparacionDTO básico para listados
     */
    public static ReparacionDTO mapToBasicReparacionDTO(ResultSet resultSet) throws SQLException {
        return new ReparacionDTO(
            resultSet.getInt("ELS"),
            resultSet.getString("Aviso"),
            resultSet.getString("nombre"),
            resultSet.getString("NombreSucursal"),
            resultSet.getString("Equipos.Nombre"),
            resultSet.getString("Marca"),
            resultSet.getString("Modelo"),
            resultSet.getString("NumeroDeSerie"),
            resultSet.getString("EstadoTecnico"),
            resultSet.getString("EstadoComercial")
        );
    }

    /**
     * Mapea un ResultSet a un objeto ReparacionDTO para componentes
     */
    public static ReparacionDTO mapToComponenteReparacionDTO(ResultSet resultSet) throws SQLException {
        return new ReparacionDTO(
            resultSet.getInt("ELS"),
            resultSet.getString("FechaEntrada"),
            resultSet.getString("Cliente.nombre"),
            resultSet.getString("NombreSucursal"),
            resultSet.getString("Equipos.Nombre"),
            resultSet.getString("Marca"),
            resultSet.getString("Modelo"),
            resultSet.getString("original"),
            resultSet.getString("reemplazo")
        );
    }

    /**
     * Mapea un ResultSet a un objeto ReparacionDTO simple para combos
     */
    public static ReparacionDTO mapToComboReparacionDTO(ResultSet resultSet) throws SQLException {
        return new ReparacionDTO(resultSet.getString(1));
    }

    /**
     * Mapea una lista completa de ResultSet a List<ReparacionDTO>
     */
    public static List<ReparacionDTO> mapToReparacionList(ResultSet resultSet) throws SQLException {
        List<ReparacionDTO> reparaciones = new ArrayList<>();
        while (resultSet.next()) {
            reparaciones.add(mapToReparacionDTO(resultSet));
        }
        return reparaciones;
    }

    /**
     * Mapea una lista básica de ResultSet a List<ReparacionDTO>
     */
    public static List<ReparacionDTO> mapToBasicReparacionList(ResultSet resultSet) throws SQLException {
        List<ReparacionDTO> reparaciones = new ArrayList<>();
        while (resultSet.next()) {
            reparaciones.add(mapToBasicReparacionDTO(resultSet));
        }
        return reparaciones;
    }

    /**
     * Mapea una lista de componentes de ResultSet a List<ReparacionDTO>
     */
    public static List<ReparacionDTO> mapToComponenteReparacionList(ResultSet resultSet) throws SQLException {
        List<ReparacionDTO> reparaciones = new ArrayList<>();
        while (resultSet.next()) {
            reparaciones.add(mapToComponenteReparacionDTO(resultSet));
        }
        return reparaciones;
    }

    /**
     * Mapea un ResultSet a una lista de enteros (para búsquedas)
     */
    public static List<Integer> mapToIntegerList(ResultSet resultSet) throws SQLException {
        List<Integer> numerosELS = new ArrayList<>();
        while (resultSet.next()) {
            numerosELS.add(resultSet.getInt("ELS"));
        }
        return numerosELS;
    }

    /**
     * Mapea un ResultSet a una lista de strings (para combos)
     */
    public static List<String> mapToStringList(ResultSet resultSet) throws SQLException {
        List<String> datos = new ArrayList<>();
        while (resultSet.next()) {
            datos.add(resultSet.getString(1));
        }
        return datos;
    }

    /**
     * Mapea un ResultSet a un valor entero único (para estadísticas)
     */
    public static int mapToSingleInteger(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    /**
     * Mapea un ResultSet a un valor double único (para estadísticas)
     */
    public static double mapToSingleDouble(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0.0;
    }

    /**
     * Mapea un ResultSet a una lista de enteros por mes (para estadísticas)
     */
    public static List<Integer> mapToMonthlyIntegerList(ResultSet resultSet) throws SQLException {
        List<Integer> cantidadPorMes = initializeMonthlyList(0);
        
        while (resultSet.next()) {
            int mes = resultSet.getInt(1) - 1; // Ajustar a índice 0-based
            int cantidad = resultSet.getInt(2);
            if (mes >= 0 && mes < 12) {
                cantidadPorMes.set(mes, cantidad);
            }
        }
        return cantidadPorMes;
    }

    /**
     * Mapea un ResultSet a una lista de doubles por mes (para estadísticas)
     */
    public static List<Double> mapToMonthlyDoubleList(ResultSet resultSet) throws SQLException {
        List<Double> sumaPorMes = initializeMonthlyDoubleList(0.0);
        
        while (resultSet.next()) {
            int mes = resultSet.getInt(1) - 1; // Ajustar a índice 0-based
            double suma = resultSet.getDouble(2);
            if (mes >= 0 && mes < 12) {
                sumaPorMes.set(mes, suma);
            }
        }
        return sumaPorMes;
    }

    /**
     * Inicializa una lista mensual con valores enteros
     */
    private static List<Integer> initializeMonthlyList(int defaultValue) {
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            lista.add(defaultValue);
        }
        return lista;
    }

    /**
     * Inicializa una lista mensual con valores double
     */
    private static List<Double> initializeMonthlyDoubleList(double defaultValue) {
        List<Double> lista = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            lista.add(defaultValue);
        }
        return lista;
    }

    /**
     * Verifica si un campo es válido para búsqueda (seguridad contra SQL injection)
     */
    public static boolean esCampoValido(String campo) {
        return SQLQueries.CAMPOS_PERMITIDOS_BUSQUEDA.contains(campo);
    }
}