package persistencia.conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.ELSAnterior;

public class ConectorAccess {
    private String rutaArchivo;

    public ConectorAccess(String ruta) {
        this.rutaArchivo = ruta;
    }

    private Connection conectar() throws SQLException {
        String url = "jdbc:ucanaccess://" + rutaArchivo;
        return DriverManager.getConnection(url);
    }

    public List<ELSAnterior> obtenerRegistrosELS() {
        List<ELSAnterior> lista = new ArrayList<>();
        // Query que une reparaciones y equipos según la estructura detectada
	
		
		
		String sql3 = "SELECT " +
			    "C.id, C.Razon_Social, C.CUIT, C.Domicilio, C.TelefonoEmpresa, C.Contacto, C.TelefonoContacto, C.CorreoElectronico, " +
			    "R.ELS, FORMAT(R.[Fecha de reparacion], 'dd/mm/yyyy') AS FechaReparacionFormateada, FORMAT(R.[Fecha Entrada], 'dd/mm/yyyy') AS FechaEntradaFormateada, R.Falla, R.Solucion, R.[Informe cliente], " +
			    "R.IDTecnico AS reparaciones_IDTecnico, R.EstadoTecnicoClave, R.EstadoComercialClave, R.EstadoFisicoClave, " +
			    "R.[Estado Fisico], R.[Estado Tecnico], R.[Estado Comercial], R.[Remito Cliente], R.[Orden de Compra], " +
			    "R.[Agregado a remito], R.[Remito Generado], R.IDEquipo AS reparaciones_IDEquipo, R.CodigoRemito, " +
			    "R.InformeEnviado,FORMAT(R.[FechAceptacion], 'dd/mm/yyyy') AS FechaAceptacionFormateada, R.PrecioPeso, R.PrecioDolar, R.PresupuestoGenerado, R.Enviado, " +
			    "R.Pago, R.PresupuestoEnviado, " +
			    "T.IdTecnico, T.Nombre AS NombreTecnico, T.Correo, " +  // <-- alias aquí
			    "E.IdEquipo, E.Nombre AS NombreEquipo, E.Modelo, E.Marca, E.NumeroDeSerie, E.Aviso, E.[Cliente/Cliente], E.RemitoCliente, E.IDCliente, E.IDSuc, E.FechaFabricacion, " +  // <-- alias aquí
			    "S.NombreSucursal, " +
			    "REM.NumeroRemitoSalida, " +
			    "U.Ubicacion, U.Codigo, U.IdUbicacion " +
			    "FROM (((((reparaciones AS R " +
			    "INNER JOIN Equipos AS E ON R.IDEquipo = E.IdEquipo) " +
			    "INNER JOIN Cliente AS C ON E.IDCliente = C.id) " +
			    "INNER JOIN Sucursal AS S ON E.IDSuc = S.IdSucursal) " +
			    "INNER JOIN Tecnicos AS T ON R.IDTecnico = T.IdTecnico) " +
			    "INNER JOIN Remitos AS REM ON R.CodigoRemito = REM.CodigoRemito) " +
			    "INNER JOIN UbicacionRemitos AS U ON REM.IdUbicacion = U.IdUbicacion " +
			    "WHERE R.ELS BETWEEN 1 AND 977 " +
			    "ORDER BY R.ELS ASC";
		

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql3)) {
            while (rs.next()) {
                ELSAnterior els = new ELSAnterior();
                els.setEls("ELS " + rs.getString("ELS"));
                els.setFechaEntrada(rs.getString("FechaEntradaFormateada"));
                els.setCliente(rs.getString("Razon_Social"));
                els.setSucursal(rs.getString("NombreSucursal"));
                els.setEquipo(rs.getString("NombreEquipo"));
                els.setMarca(rs.getString("Marca"));
                els.setModelo(rs.getString("Modelo"));
                els.setInformeCliente(rs.getString("Informe cliente"));
                els.setDiagnostico(rs.getString("Solucion"));
                els.setPrecioPesos(rs.getDouble("PrecioPeso"));
                els.setPrecioDolar(rs.getDouble("PrecioDolar"));
                els.setTecnico(rs.getString("NombreTecnico"));
                els.setNroSerie(rs.getString("NumeroDeSerie"));
                els.setAvisoCliente(rs.getString("Aviso"));
                els.setEstadoFisico(rs.getString("Estado Fisico"));
                els.setEstadoTecnico(rs.getString("Estado Tecnico"));
                els.setEstadoComercial(rs.getString("Estado Comercial"));
                els.setFechaReparacion(rs.getString("FechaReparacionFormateada"));
                els.setRemitoSalida(rs.getString("NumeroRemitoSalida"));
                els.setFechaRespuesta(rs.getString("FechaAceptacionFormateada"));
                els.setRepuestos("..."); // Aquí podrías agregar lógica para obtener los repuestos si es necesario
                els.setPago(rs.getString("Pago"));
                els.setOrdenCompra(rs.getString("Orden de Compra"));
                els.setClienteCliente(rs.getString("Cliente/Cliente"));
                els.setRemitoCliente(rs.getString("RemitoCliente"));
                els.setFalla("falla");
       
 

                
                lista.add(els);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}