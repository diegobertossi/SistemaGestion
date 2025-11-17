package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import dto.ReparacionDTO;
import persistencia.conexion.Conexion;

public class ReparacionComboManager {

    private Conexion conexion;

    public ReparacionComboManager(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Llena un JComboBox con nombres de equipos
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarEquipo(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_NOMBRE_EQUIPO);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con marcas
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarMarca(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_MARCA);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con modelos
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarModelos(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_MODELO);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con ELS
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarELS(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_ELS);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con números de serie
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarSerie(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_SERIE);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con avisos
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarAviso(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_AVISO);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con estados comerciales
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarEstadoCom(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_ESTADO_COM);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con estados físicos
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarEstadoFis(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_ESTADO_FIS);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con estados técnicos
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarEstadoTec(JComboBox comboBox) {
        List<String> datos = executeComboQuery(SQLQueries.READ_ALL_ESTADO_TEC);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con modelos filtrados por marca
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarModelosXMarca(JComboBox comboBox, String marca) {
        List<String> datos = executeComboQueryWithParam(SQLQueries.READ_ALL_MODELO_X_MARCA, marca);
        populateComboBox(comboBox, datos);
    }

    /**
     * Llena un JComboBox con series filtradas por modelo
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listarSerieXModelo(JComboBox comboBox, String modelo) {
        List<String> datos = executeComboQueryWithParam(SQLQueries.READ_ALL_SERIE_X_MODELO, modelo);
        populateComboBox(comboBox, datos);
    }

    // ========== MÉTODOS PRIVADOS AUXILIARES ==========

    /**
     * Ejecuta una consulta simple para combos (sin parámetros)
     */
    private List<String> executeComboQuery(String query) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        List<String> datos = new ArrayList<>();
        
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();
            
            // Leer TODOS los datos ANTES de cerrar la conexión
            while (resultSet.next()) {
                datos.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, resultSet, conn);
        }
        
        return datos;
    }

    /**
     * Ejecuta una consulta para combos con un parámetro
     */
    private List<String> executeComboQueryWithParam(String query, String parametro) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        List<String> datos = new ArrayList<>();
        
        try {
            conn = conexion.getSQLConexion();
            statement = conn.prepareStatement(query);
            statement.setString(1, parametro);
            resultSet = statement.executeQuery();
            
            // Leer TODOS los datos ANTES de cerrar la conexión
            while (resultSet.next()) {
                datos.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, resultSet, conn);
        }
        
        return datos;
    }

    /**
     * Pobla el JComboBox con los datos obtenidos
     * SOLUCIÓN DEFINITIVA: Primero leer todos los datos, luego cerrar recursos, luego poblar el combo
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void populateComboBox(JComboBox comboBox, List<String> datos) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        // Agregar todos los datos al modelo
        for (String dato : datos) {
            model.addElement(new ReparacionDTO(dato));
        }
        
        // Establecer el modelo en el comboBox
        comboBox.setModel(model);
    }

    /**
     * Cierra recursos de forma segura
     */
    private void closeResources(PreparedStatement statement, ResultSet resultSet, Connection conn) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conexion.cerrarConexion();
            }
        }
    }
}