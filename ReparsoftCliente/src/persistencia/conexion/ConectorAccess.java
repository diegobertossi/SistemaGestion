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
		String sql1 = "SELECT r.ELS, e.Nombre, e.Marca, e.Modelo, e.NumeroDeSerie, r.Solucion, r.PrecioPeso, r.PrecioDolar "
				+ "FROM reparaciones r LEFT JOIN Equipos e ON r.IDEquipo = e.IdEquipo "
				+ "WHERE r.ELS BETWEEN 1 AND 977 ORDER BY r.ELS ASC";
		
		
		final String READ_ALL = 
			    "SELECT " +
			    "O.ELS, O.FechaEntrada, O.FechadeDiagnostico, O.Falla, O.Solucion, O.Informecliente, " +
			    "O.AvisoEnviado, O.PresupuestoEnviado, O.EstadoFisico, O.EstadoTecnico, O.EstadoComercial, " +
			    "O.RemitoCliente, O.OrdendeCompra, O.PrecioPeso, O.PrecioDolar, O.Pago, " +
			    "O.lugar_de_ingreso, O.NroFactura, O.FechaSalida, " +
			    "C.Cliente AS nombreCliente, C.IdCliente, " +
			    "E.Nombre AS nombreEquipo, E.Marca, E.Modelo, E.NumeroDeSerie, " +
			    "S.NombreSucursal, " +
			    "U.nombre AS nombreTecnico " +
			    "FROM ((((reparaciones AS O " +
			    "LEFT JOIN Cliente AS C ON O.idCliente = C.IdCliente) " +
			    "LEFT JOIN Equipos AS E ON O.idEquipo = E.IdEquipo) " +
			    "LEFT JOIN Sucursal AS S ON O.idSucursal = S.IdSucursal) " +
			    "LEFT JOIN Usuarios AS U ON O.idUsuario = U.idUsuario) " +
			    "WHERE O.ELS >= 1 AND O.ELS <= 977 " +
			    "ORDER BY O.ELS ASC";
        
        
        
		String sql = "SELECT r.ELS, e.Nombre, e.Marca, e.Modelo " +
	             "FROM reparaciones AS r " +
	             "LEFT JOIN Equipos AS e ON CLng(r.idEquipo) = CLng(e.IdEquipo) " +
	             "WHERE r.ELS BETWEEN 1 AND 977 " +
	             "ORDER BY r.ELS ASC";
		
		
		String sql2 = "SELECT " +
	             "r.ELS, e.Nombre, e.Marca, e.Modelo, " +
	             "c.Razon_Social, s.NombreSucursal " + 
	             "FROM (((reparaciones AS r " +
	             "INNER JOIN Equipos AS e ON CLng(r.IDEquipo) = CLng(e.IdEquipo)) " +
	             "LEFT JOIN Cliente AS c ON CLng(e.IDCliente) = CLng(c.Id)) " +
	             "LEFT JOIN Sucursal AS s ON CLng(e.IDSuc) = CLng(s.IdSucursal)) " +
	             "WHERE r.ELS BETWEEN 1 AND 977 " +
	             "ORDER BY r.ELS ASC";
		
		
		
		
		
		String sql3 = "SELECT " +
			    "C.id, C.Razon_Social, C.CUIT, C.Domicilio, C.TelefonoEmpresa, C.Contacto, C.TelefonoContacto, C.CorreoElectronico, " +
			    "R.ELS, R.[Fecha Entrada], R.[Fecha de reparacion],FORMAT(R.[Fecha Entrada], 'dd/mm/yyyy') AS FechaEntradaFormateada, R.Falla, R.Solucion, R.[Informe cliente], " +
			    "R.IDTecnico AS reparaciones_IDTecnico, R.EstadoTecnicoClave, R.EstadoComercialClave, R.EstadoFisicoClave, " +
			    "R.[Estado Fisico], R.[Estado Tecnico], R.[Estado Comercial], R.[Remito Cliente], R.[Orden de Compra], " +
			    "R.[Agregado a remito], R.[Remito Generado], R.IDEquipo AS reparaciones_IDEquipo, R.CodigoRemito, " +
			    "R.InformeEnviado, R.FechAceptacion, R.PrecioPeso, R.PrecioDolar, R.PresupuestoGenerado, R.Enviado, " +
			    "R.Pago, R.PresupuestoEnviado, " +
			    "T.IdTecnico, T.Nombre, T.Correo, " +
			    "E.IdEquipo, E.Nombre, E.Modelo, E.Marca, E.NumeroDeSerie, E.Aviso, E.[Cliente/Cliente], E.RemitoCliente, E.IDCliente, E.IDSuc, E.FechaFabricacion, " +
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
                els.setEquipo(rs.getString("Nombre"));
                els.setMarca(rs.getString("Marca"));
                els.setModelo(rs.getString("Modelo"));
                els.setInformeCliente(rs.getString("Informe cliente"));
                els.setDiagnostico(rs.getString("Solucion"));
                els.setPrecioPesos(rs.getDouble("PrecioPeso"));
                els.setPrecioDolar(rs.getDouble("PrecioDolar"));
                
                
                
                
                
//                els.setNroSerie(rs.getString("NumeroDeSerie"));
//                els.setDiagnostico(rs.getString("Diagnostico"));
//                els.setPrecioPesos(rs.getDouble("PrecioPesos"));
//                els.setPrecioDolar(rs.getDouble("PrecioDolar"));
                // ... (mapear resto de campos según nombres exactos en Access)
                lista.add(els);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}