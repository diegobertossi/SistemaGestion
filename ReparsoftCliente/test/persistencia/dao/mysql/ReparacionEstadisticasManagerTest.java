package persistencia.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import persistencia.conexion.Conexion;

/**
 * Valida la consolidación de estadísticas por año (C1):
 * obtenerTotalesPorAnio debe ejecutar exactamente 3 consultas
 * (INGRESOS_POR_ANIO + RESUMEN_DIAGNOSTICOS_POR_ANIO + RESUMEN_ACEPTACION_POR_ANIO)
 * y mapear cada columna de las consultas consolidadas a su contador.
 */
public class ReparacionEstadisticasManagerTest {

    private Conexion mockConexion;
    private Connection mockConn;
    private PreparedStatement mockStmt;

    @Before
    public void setUp() throws SQLException {
        mockConexion = mock(Conexion.class);
        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);

        when(mockConexion.getSQLConexion()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
    }

    /** Consulta 1 = INGRESOS_POR_ANIO (getInt), consulta 2 = diagnostico (10 columnas), consulta 3 = aceptacion (2). */
    private void stubResultSets(ResultSet rsIngresos, ResultSet rsDiag, ResultSet rsAcep) throws SQLException {
        when(mockStmt.executeQuery()).thenReturn(rsIngresos, rsDiag, rsAcep);
    }

    /**
     * Orden de las columnas de RESUMEN_DIAGNOSTICOS_POR_ANIO:
     * [0] diagnosticos, [1] reparados, [2] sinFallas, [3] enGtia, [4] enRep,
     * [5] ventas, [6] sinRep, [7] repAcep, [8] repNoAcep, [9] repEspera
     */
    @Test
    public void obtenerTotalesPorAnio_mapeaTodasLasColumnasConsolidadas() throws SQLException {
        ResultSet rsIngresos = mock(ResultSet.class);
        ResultSet rsDiag = mock(ResultSet.class);
        ResultSet rsAcep = mock(ResultSet.class);
        stubResultSets(rsIngresos, rsDiag, rsAcep);

        when(rsIngresos.next()).thenReturn(true);
        when(rsIngresos.getInt(1)).thenReturn(123);

        when(rsDiag.next()).thenReturn(true);
        when(rsDiag.getDouble(1)).thenReturn(10.0);
        when(rsDiag.getDouble(2)).thenReturn(9.0);
        when(rsDiag.getDouble(3)).thenReturn(14.0);
        when(rsDiag.getDouble(4)).thenReturn(1.0);
        when(rsDiag.getDouble(5)).thenReturn(2.0);
        when(rsDiag.getDouble(6)).thenReturn(3.0);
        when(rsDiag.getDouble(7)).thenReturn(4.0);
        when(rsDiag.getDouble(8)).thenReturn(5.0);
        when(rsDiag.getDouble(9)).thenReturn(6.0);
        when(rsDiag.getDouble(10)).thenReturn(7.0);

        when(rsAcep.next()).thenReturn(true);
        when(rsAcep.getDouble(1)).thenReturn(500.25);
        when(rsAcep.getDouble(2)).thenReturn(60.5);

        ReparacionEstadisticasManager manager = new ReparacionEstadisticasManager(mockConexion);
        ReparacionEstadisticasManager.TotalesPorAnio totales = manager.obtenerTotalesPorAnio(2025);

        assertEquals(123, totales.ingresos);
        assertEquals(10, totales.diagnosticos);
        assertEquals(9, totales.reparados);
        assertEquals(14, totales.sinFallas);
        assertEquals(1, totales.enGtia);
        assertEquals(2, totales.enRep);
        assertEquals(3, totales.ventas);
        assertEquals(4, totales.sinRep);
        assertEquals(5, totales.repAcep);
        assertEquals(6, totales.repNoAcep);
        assertEquals(7, totales.repEspera);
        assertEquals(500.25, totales.facturacionPeso, 0.001);
        assertEquals(60.5, totales.facturacionDolar, 0.001);
    }

    /** Debe ejecutar exactamente las 3 consultas consolidadas con el año como parámetro. */
    @Test
    public void obtenerTotalesPorAnio_usaLasTresQueriesConsolidadas() throws SQLException {
        ResultSet rsConFila = mock(ResultSet.class);
        stubResultSets(rsConFila, rsConFila, rsConFila);
        when(rsConFila.next()).thenReturn(true);
        when(rsConFila.getInt(1)).thenReturn(5);

        ReparacionEstadisticasManager manager = new ReparacionEstadisticasManager(mockConexion);
        manager.obtenerTotalesPorAnio(2025);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockConn, times(3)).prepareStatement(sqlCaptor.capture());
        List<String> sqls = sqlCaptor.getAllValues();

        assertEquals(3, sqls.size());
        assertEquals(SQLQueries.INGRESOS_POR_ANIO, sqls.get(0));
        assertEquals(SQLQueries.RESUMEN_DIAGNOSTICOS_POR_ANIO, sqls.get(1));
        assertEquals(SQLQueries.RESUMEN_ACEPTACION_POR_ANIO, sqls.get(2));

        verify(mockStmt, times(3)).setInt(eq(1), eq(2025));
        verify(mockStmt, times(3)).executeQuery();
    }

    /** Sin filas en el ResultSet todos los totales quedan en cero (sin NPE). */
    @Test
    public void obtenerTotalesPorAnio_conResultSetVacioDevuelveCeros() throws SQLException {
        ResultSet rsVacio = mock(ResultSet.class);
        stubResultSets(rsVacio, rsVacio, rsVacio);
        when(rsVacio.next()).thenReturn(false);

        ReparacionEstadisticasManager manager = new ReparacionEstadisticasManager(mockConexion);
        ReparacionEstadisticasManager.TotalesPorAnio totales = manager.obtenerTotalesPorAnio(2025);

        assertEquals(0, totales.ingresos);
        assertEquals(0, totales.diagnosticos);
        assertEquals(0, totales.reparados);
        assertEquals(0, totales.sinFallas);
        assertEquals(0, totales.enGtia);
        assertEquals(0, totales.enRep);
        assertEquals(0, totales.ventas);
        assertEquals(0, totales.sinRep);
        assertEquals(0, totales.repAcep);
        assertEquals(0, totales.repNoAcep);
        assertEquals(0, totales.repEspera);
        assertEquals(0.0, totales.facturacionPeso, 0.001);
        assertEquals(0.0, totales.facturacionDolar, 0.001);
    }

    /** Un valor SQL NULL en una suma se traduce a 0.0 (wasNull). */
    @Test
    public void obtenerTotalesPorAnio_conSumaNullDevuelveCero() throws SQLException {
        ResultSet rsIngresos = mock(ResultSet.class);
        ResultSet rsDiag = mock(ResultSet.class);
        ResultSet rsAcep = mock(ResultSet.class);
        stubResultSets(rsIngresos, rsDiag, rsAcep);

        when(rsIngresos.next()).thenReturn(true);
        when(rsIngresos.getInt(1)).thenReturn(10);

        when(rsDiag.next()).thenReturn(true);
        when(rsDiag.getDouble(1)).thenReturn(1.0);
        when(rsDiag.getDouble(2)).thenReturn(1.0);
        when(rsDiag.getDouble(3)).thenReturn(1.0);
        when(rsDiag.getDouble(4)).thenReturn(1.0);
        when(rsDiag.getDouble(5)).thenReturn(1.0);
        when(rsDiag.getDouble(6)).thenReturn(1.0);
        when(rsDiag.getDouble(7)).thenReturn(1.0);
        when(rsDiag.getDouble(8)).thenReturn(1.0);
        when(rsDiag.getDouble(9)).thenReturn(1.0);
        when(rsDiag.getDouble(10)).thenReturn(1.0);

        // facturación peso = 12345.67 pero wasNull=true → debe quedar 0.0; dolar = 88.5
        when(rsAcep.next()).thenReturn(true);
        when(rsAcep.getDouble(1)).thenReturn(12345.67);
        when(rsAcep.getDouble(2)).thenReturn(88.5);
        when(rsAcep.wasNull()).thenReturn(true, false);

        ReparacionEstadisticasManager manager = new ReparacionEstadisticasManager(mockConexion);
        ReparacionEstadisticasManager.TotalesPorAnio totales = manager.obtenerTotalesPorAnio(2025);

        assertEquals(0.0, totales.facturacionPeso, 0.001);
        assertEquals(88.5, totales.facturacionDolar, 0.001);
        assertEquals(1, totales.reparados);
        assertEquals(10, totales.ingresos);
    }

    /** Ante una excepción SQL el resumen no revienta: devuelve totales en cero. */
    @Test
    public void obtenerTotalesPorAnio_conExcepcionSQLDevuelveCeros() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("BD caída"));

        ReparacionEstadisticasManager manager = new ReparacionEstadisticasManager(mockConexion);
        ReparacionEstadisticasManager.TotalesPorAnio totales = manager.obtenerTotalesPorAnio(2025);

        assertEquals(0, totales.ingresos);
        assertEquals(0, totales.reparados);
        assertEquals(0.0, totales.facturacionPeso, 0.001);
        assertTrue(totales.diagnosticos >= 0);
    }
}