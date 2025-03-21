package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ReparacionDAO;
import presentacion.vista.VentanaVisualizarEquipos;
import dto.ReparacionDTO;

public class ReparacionDAOImpl implements ReparacionDAO {

	private static final String insert = "INSERT INTO reparaciones(ELS,FechaEntrada,Falla, EstadoFisico, EstadoTecnico,EstadoComercial, RemitoCliente, idEquipo, idUsuario, lugar_de_ingreso) VALUES( ? , ? ,? , ? , ?,? , ? , ?,?,?)";

	private static final String insertEquipo = "INSERT INTO Equipos (IdEquipo, Nombre, Modelo, Marca, NumeroDeSerie, FechaFabr,Aviso, ClienteCliente, RemitoCliente, idCliente, idSucursal ) VALUES(? , ? ,? , ? , ? , ? , ? , ? , ?, ?,?)";

	private static final String delete = "DELETE FROM reparaciones WHERE ELS = ?";

	private static final String readallListadoMarcarAceptaciones = "SELECT  reparaciones.ELS, Equipos.Aviso, Cliente.nombre, Sucursal.NombreSucursal, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, Equipos.NumeroDeSerie, reparaciones.EstadoTecnico, reparaciones.EstadoComercial"
			+ " FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion  "
			+ " WHERE (((Cliente.idCliente)=Equipos.idCliente)) And ((Sucursal.IdSucursal)=Equipos.idSucursal) and reparaciones.EstadoComercial = 'A la Espera de Aceptación' and PresupuestoGenerado = true and ((usuario.IdUsuario)=reparaciones.idUsuario)  ORDER BY reparaciones.ELS ASC";

	private static final String readall = "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa,"
			+ " Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente, reparaciones.AvisoEnviado,reparaciones.PresupuestoEnviado,reparaciones.WordGenerado,reparaciones.WordEnviado, reparaciones.idUsuario,reparaciones.NombreUsuario, reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, reparaciones.idRemito, reparaciones.InformeEnviado, reparaciones.idUsuario,  DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.Pago, usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, reparaciones.Enviado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, reparaciones.FechaSalida"
			+ " FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion  "
			+ " WHERE (((Cliente.idCliente)=Equipos.idCliente)) and ((Sucursal.IdSucursal)=Equipos.idSucursal) And ((usuario.IdUsuario)=reparaciones.idUsuario)  ORDER BY reparaciones.ELS ASC";

	private static final String readallNombreEquipo = "SELECT DISTINCT * FROM Equipos group by Equipos.Nombre";

	private static final String readallMarca = "SELECT Equipos.Marca FROM Equipos group by Equipos.Marca";

	private static final String readallModelo = "SELECT Equipos.Modelo FROM Equipos group by Equipos.Modelo";

	private static final String readallELS = "SELECT reparaciones.ELS FROM reparaciones group by reparaciones.ELS ORDER BY reparaciones.ELS ASC";

	private static final String readallSerie = "SELECT DISTINCT Equipos.NumeroDeSerie FROM Equipos ORDER BY Equipos.NumeroDeSerie ASC";

	private static final String readallAviso = "SELECT Equipos.Aviso FROM Equipos group by Equipos.Aviso";
	private static final String readallEstadoCom = "SELECT reparaciones.EstadoComercial FROM reparaciones group by reparaciones.EstadoComercial";
	private static final String readallEstadoFis = "SELECT reparaciones.EstadoFisico FROM reparaciones group by reparaciones.EstadoFisico";
	private static final String readallEstadoTec = "SELECT reparaciones.EstadoTecnico FROM reparaciones group by reparaciones.EstadoTecnico";

	private static final String readallModeloxMarca = "SELECT Equipos.Modelo FROM Equipos where Equipos.Marca = ? group by Equipos.Modelo";

	private static final String readallSeriexModelo = "SELECT Equipos.NumeroDeSerie FROM Equipos where Equipos.Modelo = ? group by Equipos.NumeroDeSerie";

	private static final String readallxELS = "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa,"
			+ " Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, reparaciones.Falla, reparaciones.Solucion, reparaciones.AvisoEnviado,reparaciones.PresupuestoEnviado,reparaciones.WordGenerado,reparaciones.WordEnviado, reparaciones.Informecliente, reparaciones.idUsuario, reparaciones.NombreUsuario,reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, reparaciones.idRemito, reparaciones.InformeEnviado, reparaciones.idUsuario,  DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca,DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, reparaciones.Enviado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, reparaciones.fechaSalida"
			+ " FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion  "
			+ " WHERE (((Cliente.idCliente)=Equipos.idCliente)) and ((Sucursal.IdSucursal)=Equipos.idSucursal) And ((usuario.IdUsuario)=reparaciones.idUsuario)  and ELS = ?";

	private static final String readallxIDClienteIDSucursal = "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa,"
			+ " Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente,reparaciones.AvisoEnviado,reparaciones.PresupuestoEnviado, reparaciones.WordGenerado,reparaciones.WordEnviado,reparaciones.idUsuario, reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, reparaciones.idRemito, reparaciones.InformeEnviado, reparaciones.idUsuario,  DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca,DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, reparaciones.Enviado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, reparaciones.fechaSalida"
			+ " FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion  "
			+ " WHERE (((Cliente.idCliente)=Equipos.idCliente) And (Sucursal.IdSucursal)=Equipos.idSucursal) and ((usuario.IdUsuario)=reparaciones.idUsuario) and (reparaciones.EstadoComercial='Aceptado' || reparaciones.EstadoComercial='NO Aceptado' ) and reparaciones.EstadoFisico != 'ENVIADO' and reparaciones.Agregadoaremito != 1 and Cliente.idCliente = ? and Sucursal.IdSucursal = ? order by ELS";

	private static final String readallxSerie = "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa,"
			+ " Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, reparaciones.Falla, reparaciones.Solucion,reparaciones.AvisoEnviado,reparaciones.PresupuestoEnviado, reparaciones.WordGenerado,reparaciones.WordEnviado,reparaciones.Informecliente, reparaciones.idUsuario, reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, reparaciones.idRemito, reparaciones.InformeEnviado, reparaciones.idUsuario,  DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca,DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, reparaciones.Enviado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, reparaciones.fechaSalida"
			+ " FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion  "
			+ " WHERE ((Cliente.idCliente)=Equipos.idCliente) and ((Sucursal.IdSucursal)=Equipos.idSucursal) and ((usuario.IdUsuario)=reparaciones.idUsuario) and Equipos.NumeroDeSerie = ?";

	private static final String readallxIDremito = "SELECT * FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion  "
			+ " WHERE ((Cliente.idCliente)=Equipos.idCliente) And ((Sucursal.IdSucursal)=Equipos.idSucursal) and ((usuario.IdUsuario)=reparaciones.idUsuario) and Remitos.idRemito = ?";

	private static final String maximoELS = "Select MAX(ELS) from reparaciones";

	private static final String maximoIDequipo = "Select MAX(IdEquipo) from Equipos";

	private static final String readallxCompOriginal = "SELECT reemplazos.original,reemplazos.reemplazo, Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa,"
			+ " Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente, reparaciones.idUsuario, reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, reparaciones.idRemito, reparaciones.InformeEnviado, reparaciones.idUsuario,  DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca,DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, reparaciones.Enviado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, reparaciones.fechaSalida"
			+ " FROM (UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion ) INNER JOIN reemplazos ON reparaciones.ELS = reemplazos.ELS"
			+ " WHERE ((Cliente.idCliente)=Equipos.idCliente) And ((Sucursal.IdSucursal)=Equipos.idSucursal)and ((usuario.IdUsuario)=reparaciones.idUsuario) and reemplazos.original = ?";

	private static final String readallxCompReemplazado = "SELECT reemplazos.original, reemplazos.reemplazo, Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa,"
			+ " Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente, reparaciones.idUsuario, reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, reparaciones.idRemito, reparaciones.InformeEnviado, reparaciones.idUsuario,  DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca,DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, reparaciones.Enviado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, reparaciones.fechaSalida"
			+ " FROM (UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion ) INNER JOIN reemplazos ON reparaciones.ELS = reemplazos.ELS"
			+ " WHERE ((Cliente.idCliente)=Equipos.idCliente) and ((Sucursal.IdSucursal)=Equipos.idSucursal) and ((usuario.IdUsuario)=reparaciones.idUsuario) and reemplazos.reemplazo = ?";

	public static String ubicacion;
	private Conexion conexion;

	private static final String ingresosPorAnio = "select count(*) from reparaciones where YEAR(FechaEntrada) = ?";
	private static final String diagnosticosPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico != 'Sin Revisar'";

	private static final String facturacionPesosPorAnio = "select SUM(PrecioPeso) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.EstadoComercial = 'Aceptado'";
	private static final String facturacionDolarPorAnio = "select SUM(PrecioDolar) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.EstadoComercial = 'Aceptado'";

	private static final String reparadosPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación')";
	private static final String sinFallasPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Falla'";
	private static final String repEnGtiaPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado en Garantía'";
	private static final String enRepPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'En Reparación'";
	private static final String ventasPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Vendido'";
	private static final String sinRepPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Reparación'";
	private static final String repAcepPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado'";
	private static final String repNoAcepPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado'";
	private static final String RepEsperaPorAnio = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación'";

	private static final String ingresosPorAnioxMes = "select MONTH(reparaciones.FechaEntrada), count(*) from reparaciones where YEAR(FechaEntrada) = ? group by MONTH(FechaEntrada)";
	private static final String diagnosticoPorAnioxMes = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico != 'Sin Revisar' group by MONTH(FechadeDiagnostico)";
	private static final String facturacionoPorAnioxMes = "select MONTH(reparaciones.FechAceptacion), SUM(PrecioPeso) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.EstadoComercial = 'Aceptado'  group by MONTH(FechAceptacion)";

	private static final String diagnosticoPorAnioxTecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico != 'Sin Revisar' group by MONTH(FechadeDiagnostico)";
	private static final String facturacionoPorAnioxTecnico = "select MONTH(reparaciones.FechAceptacion), SUM(PrecioPeso) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.idUsuario =? and reparaciones.EstadoComercial = 'Aceptado'  group by MONTH(FechAceptacion);";
	private static final String aceptacionesPorAnioxTecnico = "select MONTH(reparaciones.FechAceptacion), count(*) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'Vendido') and reparaciones.EstadoComercial = 'Aceptado'  group by MONTH(FechAceptacion)";

	private static final String ingresosXanioXcliente = "select MONTH(reparaciones.FechaEntrada),count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechaEntrada) = ? and Equipos.idCliente = ? group by MONTH(FechaEntrada)";
	private static final String aceptacionesPorAnioxCliente = "select MONTH(reparaciones.FechAceptacion), count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ?  and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(reparaciones.FechAceptacion)";
	private static final String facturacionoPorAnioxCliente = "select MONTH(reparaciones.FechAceptacion), SUM(PrecioPeso) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ?  and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(reparaciones.FechAceptacion)";

	private static final String totalIngresosXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechaEntrada) = ? and Equipos.idCliente = ?";
	private static final String totalReparadosXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación') and Equipos.idCliente = ?";
	private static final String totalRepEnGtiaXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado en Garantía' and Equipos.idCliente = ?";
	private static final String totalSinFallaXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Falla' and Equipos.idCliente = ?";
	private static final String totalEnRepXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'En Reparación' and Equipos.idCliente = ?";
	private static final String totalVentasXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Vendido' and Equipos.idCliente = ?";
	private static final String totalSinRepXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Reparación' and Equipos.idCliente = ?";
	private static final String totalRepAcepXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado' and Equipos.idCliente = ?";
	private static final String totalRepNoAcepXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado' and Equipos.idCliente = ?";
	private static final String totalRepEsperaXanioXcliente = "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación' and Equipos.idCliente = ?";

	private static final String facturacionPesoPorAnioPorCliente = "select SUM(PrecioPeso) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ?  and reparaciones.EstadoComercial = 'Aceptado'";
	private static final String facturacionDolarPorAnioPorCliente = "select SUM(PrecioDolar) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ?  and reparaciones.EstadoComercial = 'Aceptado' ";

	private static final String facturacionDolarPorAnioPorTecnico = "select SUM(PrecioDolar) from reparaciones where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.idUsuario =?  and reparaciones.EstadoComercial = 'Aceptado'";
	private static final String facturacionPesoPorAnioPorTecnico = "select SUM(PrecioPeso) from reparaciones where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.idUsuario =?  and reparaciones.EstadoComercial = 'Aceptado'";

	private static final String totalRepEsperaXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación'";
	private static final String totalRepNoAcepXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado'";
	private static final String totalRepAcepXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado'";
	private static final String totalSinRepXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Reparación'";
	private static final String totalVentasXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Vendido'";
	private static final String totalEnRepXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'En Reparación'";
	private static final String totalRepEnGtiaXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado en Garantía'";
	private static final String totalSinFallaXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Falla'";
	private static final String totalReparadosXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación')";
	private static final String totalDiagnosticosXanioXtecnico = "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico != 'Sin Revisar'";

	private static final String reparadosXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) group by MONTH(FechadeDiagnostico)";
	private static final String sinRepXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Reparación' group by MONTH(FechadeDiagnostico)";
	private static final String ventasXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Vendido' group by MONTH(FechadeDiagnostico)";
	private static final String enRepXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'En Reparación' group by MONTH(FechadeDiagnostico)";
	private static final String sinFallaXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Falla' group by MONTH(FechadeDiagnostico)";
	private static final String enGtiaXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado en Garantía' group by MONTH(FechadeDiagnostico)";

	private static final String repEsperaXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico),count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación' group by MONTH(FechadeDiagnostico)";
	private static final String repNoAcepXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico),count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado' group by MONTH(FechadeDiagnostico)";
	private static final String repAcepXmesXtecnico = "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(FechadeDiagnostico)";

	private static final String facturacionDolarPorAnioxTecnicoXmes = "select MONTH(reparaciones.FechAceptacion), SUM(PrecioDolar) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.idUsuario =? and reparaciones.EstadoComercial = 'Aceptado'  group by MONTH(FechAceptacion);";

	private static final String busquePorCampoYtexto = "SELECT reparaciones.ELS FROM reparaciones WHERE %s LIKE ?";

	@SuppressWarnings("unused")
	public ReparacionDAOImpl(String ubicacionBase) {

		ubicacion = ubicacionBase;
		conexion = Conexion.getConexion(ubicacion);

	}

	public boolean insert(ReparacionDTO Reparaciones) {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, Reparaciones.getELS());
			statement.setString(2, Reparaciones.getFecha_Entrada());
			statement.setString(3, Reparaciones.getFalla());
			statement.setString(4, Reparaciones.getEstadoFisico());
			statement.setString(5, Reparaciones.getEstadoTecnico());
			statement.setString(6, Reparaciones.getEstadoComercial());
			statement.setString(7, Reparaciones.getRemitoCliente());
			statement.setInt(8, Reparaciones.getIDEquipo());
			statement.setInt(9, Reparaciones.getidUsuario());
			statement.setString(10, Reparaciones.getLugarDeIngreso());

			if (statement.executeUpdate() > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;

	}

	public boolean insertEquipo(ReparacionDTO Reparaciones) {
		PreparedStatement statement;
		try {

			statement = conexion.getSQLConexion().prepareStatement(insertEquipo);
			statement.setInt(1, Reparaciones.getIDEquipo());
			statement.setString(2, Reparaciones.getNombreEquipo());
			statement.setString(3, Reparaciones.getModelo());
			statement.setString(4, Reparaciones.getMarca());
			statement.setString(5, Reparaciones.getNumeroDeSerie());
			statement.setString(6, Reparaciones.getFechaFabr());
			statement.setString(7, Reparaciones.getAviso());
			statement.setString(8, Reparaciones.getClienteCliente());
			statement.setString(9, Reparaciones.getRemitoCliente());
			statement.setInt(10, Reparaciones.getIDCliente());
			statement.setInt(11, Reparaciones.getIDSuc());

			if (statement.executeUpdate() > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;

	}

	public boolean delete(ReparacionDTO reparacion_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(reparacion_a_eliminar.getELS()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	@Override
	public ReparacionDTO obtenerReparacionXels(Integer i) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ReparacionDTO Reparacion = null;

		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxELS);
			statement.setInt(1, i);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparacion = new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("FechaEntrada"),
						resultSet.getString("FechadeDiagnostico"), resultSet.getString("Falla"),
						resultSet.getString("Solucion"), resultSet.getString("Informecliente"),
						resultSet.getInt("idUsuario"), resultSet.getString("EstadoFisico"),
						resultSet.getString("EstadoTecnico"), resultSet.getString("EstadoComercial"),
						resultSet.getString("RemitoCliente"), resultSet.getString("OrdendeCompra"),
						resultSet.getBoolean("Agregadoaremito"), resultSet.getBoolean("RemitoGenerado"),
						resultSet.getInt("idEquipo"), resultSet.getInt("idRemito"), resultSet.getDouble("PrecioPeso"),
						resultSet.getDouble("PrecioDolar"), resultSet.getBoolean("InformeEnviado"),
						resultSet.getString("FechAceptacion"), resultSet.getBoolean("PresupuestoGenerado"),
						resultSet.getBoolean("Enviado"), resultSet.getDouble("Pago"),
						resultSet.getBoolean("PresupuestoEnviado"), resultSet.getString("Equipos.Nombre"),
						resultSet.getString("email"), resultSet.getString("Modelo"), resultSet.getString("Marca"),
						resultSet.getString("NumeroDeSerie"), resultSet.getString("Aviso"),
						resultSet.getString("ClienteCliente"), resultSet.getInt("idCliente"),
						resultSet.getInt("idSucursal"), resultSet.getString("nombre"),
						resultSet.getString("NombreSucursal"), resultSet.getString("NombreUsuario"),
						resultSet.getInt("Codigo"), resultSet.getInt("NumeroRemitoSalida"),
						resultSet.getString("FechaFabr"), resultSet.getBoolean("AvisoEnviado"),
						resultSet.getBoolean("WordGenerado"), resultSet.getBoolean("WordEnviado"),
						resultSet.getString("lugar_de_ingreso"), resultSet.getString("FechaSalida"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparacion;
	}

	@Override
	public ReparacionDTO obtenerReparacionXserie(String serie) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ReparacionDTO Reparacion = null;

		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxSerie);
			statement.setString(1, serie);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparacion = new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("FechaEntrada"),
						resultSet.getString("FechadeDiagnostico"), resultSet.getString("Falla"),
						resultSet.getString("Solucion"), resultSet.getString("Informecliente"),
						resultSet.getInt("idUsuario"), resultSet.getString("EstadoFisico"),
						resultSet.getString("EstadoTecnico"), resultSet.getString("EstadoComercial"),
						resultSet.getString("RemitoCliente"), resultSet.getString("OrdendeCompra"),
						resultSet.getBoolean("Agregadoaremito"), resultSet.getBoolean("RemitoGenerado"),
						resultSet.getInt("idEquipo"), resultSet.getInt("idRemito"), resultSet.getDouble("PrecioPeso"),
						resultSet.getDouble("PrecioDolar"), resultSet.getBoolean("InformeEnviado"),
						resultSet.getString("FechAceptacion"), resultSet.getBoolean("PresupuestoGenerado"),
						resultSet.getBoolean("Enviado"), resultSet.getDouble("Pago"),
						resultSet.getBoolean("PresupuestoEnviado"), resultSet.getString("Equipos.Nombre"),
						resultSet.getString("email"), resultSet.getString("Modelo"), resultSet.getString("Marca"),
						resultSet.getString("NumeroDeSerie"), resultSet.getString("Aviso"),
						resultSet.getString("ClienteCliente"), resultSet.getInt("idCliente"),
						resultSet.getInt("idSucursal"), resultSet.getString("nombre"),
						resultSet.getString("NombreSucursal"), resultSet.getString("usuario.nombre"),
						resultSet.getInt("Codigo"), resultSet.getInt("NumeroRemitoSalida"),
						resultSet.getString("FechaFabr"), resultSet.getBoolean("AvisoEnviado"),
						resultSet.getBoolean("WordGenerado"), resultSet.getBoolean("WordEnviado"),
						resultSet.getString("lugar_de_ingreso"), resultSet.getString("FechaSalida"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparacion;
	}

	public List<ReparacionDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReparacionDTO> Reparaciones = new ArrayList<ReparacionDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparaciones.add(new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("FechaEntrada"),
						resultSet.getString("FechadeDiagnostico"), resultSet.getString("Falla"),
						resultSet.getString("Solucion"), resultSet.getString("Informecliente"),
						resultSet.getInt("idUsuario"), resultSet.getString("EstadoFisico"),
						resultSet.getString("EstadoTecnico"), resultSet.getString("EstadoComercial"),
						resultSet.getString("RemitoCliente"), resultSet.getString("OrdendeCompra"),
						resultSet.getBoolean("Agregadoaremito"), resultSet.getBoolean("RemitoGenerado"),
						resultSet.getInt("idEquipo"), resultSet.getInt("idRemito"), resultSet.getDouble("PrecioPeso"),
						resultSet.getDouble("PrecioDolar"), resultSet.getBoolean("InformeEnviado"),
						resultSet.getString("FechAceptacion"), resultSet.getBoolean("PresupuestoGenerado"),
						resultSet.getBoolean("Enviado"), resultSet.getDouble("Pago"),
						resultSet.getBoolean("PresupuestoEnviado"), resultSet.getString("Equipos.Nombre"),
						resultSet.getString("email"), resultSet.getString("Modelo"), resultSet.getString("Marca"),
						resultSet.getString("NumeroDeSerie"), resultSet.getString("Aviso"),
						resultSet.getString("ClienteCliente"), resultSet.getInt("idCliente"),
						resultSet.getInt("idSucursal"), resultSet.getString("nombre"),
						resultSet.getString("NombreSucursal"), resultSet.getString("NombreUsuario"),
						resultSet.getInt("Codigo"), resultSet.getInt("NumeroRemitoSalida"),
						resultSet.getString("FechaFabr"), resultSet.getBoolean("AvisoEnviado"),
						resultSet.getBoolean("WordGenerado"), resultSet.getBoolean("WordEnviado"),
						resultSet.getString("lugar_de_ingreso"), resultSet.getString("FechaSalida")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparaciones;
	}

	@Override
	public int obtenerNumeroELSels() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int ELS = 0;

		try {
			statement = conexion.getSQLConexion().prepareStatement(maximoELS);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ELS = resultSet.getInt("MAX(ELS)");

			}
			if (ELS == 0) {

				ELS = 987;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return ELS;
	}

	@Override
	public int obtenerNumeroELSbsas() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int ELS = 0;

		try {
			statement = conexion.getSQLConexion().prepareStatement(maximoELS);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ELS = resultSet.getInt("MAX(ELS)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return ELS;
	}

	@Override
	public int obtenerIDequipo() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int idEquipo = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(maximoIDequipo);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				idEquipo = resultSet.getInt("MAX(IdEquipo)");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return idEquipo;
	}

	public int ingresosPorAnio(int anio) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantEquiposxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(ingresosPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantEquiposxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantEquiposxAnio;
	}

	@Override
	public int diagnosticosPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantDiagnosticosxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(diagnosticosPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantDiagnosticosxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantDiagnosticosxAnio;
	}

	@Override
	public double FacturacionPesoPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		double facturacionPesosxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionPesosPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				facturacionPesosxAnio = resultSet.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return facturacionPesosxAnio;
	}

	@Override
	public double FacturacionDolarPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		double facturacionDolarxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionDolarPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				facturacionDolarxAnio = resultSet.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return facturacionDolarxAnio;
	}

	@Override
	public double FacturacionPesoPorAnioPorCliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		double facturacionDolarxAnioPorCliente = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionPesoPorAnioPorCliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				facturacionDolarxAnioPorCliente = resultSet.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return facturacionDolarxAnioPorCliente;
	}

	@Override
	public double FacturacionDolarPorAnioPorCliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		double facturacionDolarxAnioPorCliente = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionDolarPorAnioPorCliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				facturacionDolarxAnioPorCliente = resultSet.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return facturacionDolarxAnioPorCliente;
	}

	@Override
	public int reparadosPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantReparadosxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(reparadosPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantReparadosxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantReparadosxAnio;
	}

	@Override
	public int sinFallasPorAnio(int anio) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantSinFallasxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(sinFallasPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantSinFallasxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantSinFallasxAnio;

	}

	@Override
	public int enGtiaPorAnio(int anio) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantEnGtiaxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(repEnGtiaPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantEnGtiaxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantEnGtiaxAnio;

	}

	@Override
	public int EnRepPorAnio(int anio) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantEnRepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(enRepPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantEnRepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantEnRepxAnio;
	}

	@Override
	public int ventasPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantVentasxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(ventasPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantVentasxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantVentasxAnio;
	}

	@Override
	public int SinRepPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantSinRepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(sinRepPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantSinRepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantSinRepxAnio;
	}

	@Override
	public int RepAcepPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantRepAcepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(repAcepPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantRepAcepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantRepAcepxAnio;
	}

	@Override
	public int RepNoAcepPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantRepNoAcepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(repNoAcepPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantRepNoAcepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantRepNoAcepxAnio;
	}

	@Override
	public int RepEsperaPorAnio(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantRepEsperaxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(RepEsperaPorAnio);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantRepEsperaxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantRepEsperaxAnio;
	}

	@Override
	public int IngresosXanioXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantIngresosxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalIngresosXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantIngresosxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantIngresosxAnio;
	}

	@Override
	public int ReparadosXanioXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantReparadosxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalReparadosXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantReparadosxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantReparadosxAnio;
	}

	@Override
	public int SinFallaXanioXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantSinFallasxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalSinFallaXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantSinFallasxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantSinFallasxAnio;
	}

	@Override
	public int GtiaXanioXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantGtiaxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepEnGtiaXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantGtiaxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantGtiaxAnio;
	}

	@Override
	public int EnRepXanioXclientecliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantEnRepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalEnRepXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantEnRepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantEnRepxAnio;
	}

	@Override
	public int VentasXanioXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantVentasxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalVentasXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantVentasxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantVentasxAnio;
	}

	@Override
	public int SinRepXanioXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantSinRepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalSinRepXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantSinRepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantSinRepxAnio;
	}

	@Override
	public int RepAcepXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantAcepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepAcepXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantAcepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantAcepxAnio;
	}

	@Override
	public int RepNoAcepXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantReoNoAcepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepNoAcepXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantReoNoAcepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantReoNoAcepxAnio;
	}

	@Override
	public int RepEsperaXcliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantEsperaxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepEsperaXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantEsperaxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantEsperaxAnio;
	}

	@Override
	public int DiagnosticosXanioXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantDiagnosticosxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalDiagnosticosXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantDiagnosticosxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantDiagnosticosxAnio;
	}

	@Override
	public int ReparadosXanioXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantReparadosxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalReparadosXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantReparadosxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantReparadosxAnio;
	}

	@Override
	public int SinFallaXanioXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantSinFallasxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalSinFallaXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantSinFallasxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantSinFallasxAnio;
	}

	@Override
	public int GtiaXanioXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantGtiaxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepEnGtiaXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantGtiaxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantGtiaxAnio;
	}

	@Override
	public int EnRepXanioXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantEnRepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalEnRepXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantEnRepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantEnRepxAnio;
	}

	@Override
	public int VentasXanioXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantVentasxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalVentasXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantVentasxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantVentasxAnio;
	}

	@Override
	public int SinRepXanioXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantSinRepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalSinRepXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantSinRepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantSinRepxAnio;
	}

	@Override
	public int RepAcepXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantAcepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepAcepXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantAcepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantAcepxAnio;
	}

	@Override
	public int RepNoAcepXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantReoNoAcepxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepNoAcepXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantReoNoAcepxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantReoNoAcepxAnio;
	}

	@Override
	public int RepEsperaXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		int cantEsperaxAnio = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(totalRepEsperaXanioXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				cantEsperaxAnio = resultSet.getInt("count(*)");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantEsperaxAnio;
	}

	@Override
	public double FacturacionPesoPorAnioPorTecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		double facturacionPesoxAnioPorTecnico = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionPesoPorAnioPorTecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				facturacionPesoxAnioPorTecnico = resultSet.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return facturacionPesoxAnioPorTecnico;
	}

	@Override
	public double FacturacionDolarPorAnioPorTecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		double facturacionDolarxAnioPorTecnico = 0;
		try {

			statement = conexion.getSQLConexion().prepareStatement(facturacionDolarPorAnioPorTecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				facturacionDolarxAnioPorTecnico = resultSet.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return facturacionDolarxAnioPorTecnico;
	}

	public List<Integer> ingresosPorAnioPorMes(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(ingresosPorAnioxMes);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	public List<Integer> diagnosticoPorAnioPorMes(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(diagnosticoPorAnioxMes);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	public List<Double> facturacionPorAnioPorMes(int anio) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Double> sumadPorMes = new ArrayList<Double>();

		for (int i = 0; i < 12; i++) {

			sumadPorMes.add(0.0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionoPorAnioxMes);
			statement.setInt(1, anio);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				sumadPorMes.add(resultSet.getInt(1) - 1, resultSet.getDouble(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return sumadPorMes;
	}

	public List<Integer> diagnosticoPorAnioPorTecnico(int anio, int tecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(diagnosticoPorAnioxTecnico);
			statement.setInt(1, anio);
			statement.setInt(2, tecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	public List<Integer> aceptacionesPorAnioPorTecnico(int anio, int tecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(aceptacionesPorAnioxTecnico);
			statement.setInt(1, anio);
			statement.setInt(2, tecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	public List<Double> facturacionPorAnioPorTecnico(int anio, int tecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Double> sumadPorMes = new ArrayList<Double>();

		for (int i = 0; i < 12; i++) {

			sumadPorMes.add(0.0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionoPorAnioxTecnico);
			statement.setInt(1, anio);
			statement.setInt(2, tecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				sumadPorMes.add(resultSet.getInt(1) - 1, resultSet.getDouble(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return sumadPorMes;
	}

	@Override
	public List<Double> FacturacionDolaresPorAnioPorTecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Double> sumadPorMes = new ArrayList<Double>();

		for (int i = 0; i < 12; i++) {

			sumadPorMes.add(0.0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionDolarPorAnioxTecnicoXmes);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				sumadPorMes.add(resultSet.getInt(1) - 1, resultSet.getDouble(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return sumadPorMes;
	}

	@Override
	public List<Integer> ReparadosXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(reparadosXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> EnGtiaXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(enGtiaXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> SinFallaXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(sinFallaXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> EnRepXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(enRepXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> VentasXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(ventasXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> SinRepXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(sinRepXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> RepAcepXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(repAcepXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> RepNoAcepXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(repNoAcepXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> EsperaRepXmesXtecnico(int anio, int idTecnico) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(repEsperaXmesXtecnico);
			statement.setInt(1, anio);
			statement.setInt(2, idTecnico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Integer> ingresosPorAnioPorCliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(ingresosXanioXcliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	@Override
	public List<Double> facturacionPorAnioPorCliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Double> sumadPorMes = new ArrayList<Double>();

		for (int i = 0; i < 12; i++) {

			sumadPorMes.add(0.0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(facturacionoPorAnioxCliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				sumadPorMes.add(resultSet.getInt(1) - 1, resultSet.getDouble(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return sumadPorMes;
	}

	@Override
	public List<Integer> aceptacionesPorAnioPorCliente(int anio, int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<Integer> cantidadPorMes = new ArrayList<Integer>();

		for (int i = 0; i < 12; i++) {

			cantidadPorMes.add(0);

		}

		try {
			statement = conexion.getSQLConexion().prepareStatement(aceptacionesPorAnioxCliente);
			statement.setInt(1, anio);
			statement.setInt(2, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				cantidadPorMes.add(resultSet.getInt(1) - 1, resultSet.getInt(2));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

		return cantidadPorMes;
	}

	public boolean editEquipo(ReparacionDTO reparacion_a_editar) {
		PreparedStatement statement;
		try {
			if (reparacion_a_editar.getFechaFabr() != null) {
				statement = conexion.getSQLConexion().prepareStatement("UPDATE Equipos SET Nombre = '"
						+ reparacion_a_editar.getNombreEquipo() + "' ," + "Modelo = '" + reparacion_a_editar.getModelo()
						+ "' ," + "Marca = '" + reparacion_a_editar.getMarca() + "' ," + "NumeroDeSerie = '"
						+ reparacion_a_editar.getNumeroDeSerie() + "' ," + "Aviso = '" + reparacion_a_editar.getAviso()
						+ "' ," + "ClienteCliente = '" + reparacion_a_editar.getClienteCliente() + "' ,"
						+ "RemitoCliente = '" + reparacion_a_editar.getRemitoCliente() + "' ," + "idCliente = '"
						+ reparacion_a_editar.getIDCliente() + "' ," + "IdSucursal = '" + reparacion_a_editar.getIDSuc()
						+ "' ," + "FechaFabr = '" + reparacion_a_editar.getFechaFabr() + "'" + "WHERE IdEquipo = "
						+ reparacion_a_editar.getIDEquipo() + "");
			}

			else {
				statement = conexion.getSQLConexion().prepareStatement("UPDATE Equipos SET Nombre = '"
						+ reparacion_a_editar.getNombreEquipo() + "' ," + "Modelo = '" + reparacion_a_editar.getModelo()
						+ "' ," + "Marca = '" + reparacion_a_editar.getMarca() + "' ," + "NumeroDeSerie = '"
						+ reparacion_a_editar.getNumeroDeSerie() + "' ," + "Aviso = '" + reparacion_a_editar.getAviso()
						+ "' ," + "ClienteCliente = '" + reparacion_a_editar.getClienteCliente() + "' ,"
						+ "RemitoCliente = '" + reparacion_a_editar.getRemitoCliente() + "' ," + "idCliente = '"
						+ reparacion_a_editar.getIDCliente() + "' ," + "IdSucursal = '" + reparacion_a_editar.getIDSuc()
						+ "'" + "WHERE IdEquipo = " + reparacion_a_editar.getIDEquipo() + "");

			}

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	public boolean edit(ReparacionDTO reparacion_a_editar) {
		PreparedStatement statement = null;
		int enviado = 0;
		int PresupGenerado = 0;
		int PresupEnviado = 0;
		int informeWordGenerado = 0;
		int informeWordEnviado = 0;
		int AvisoEnviado = 0;

		try {

			if (reparacion_a_editar.getEnviado() == null) {
				enviado = 0;
			} else if (reparacion_a_editar.getEnviado() == true) {
				enviado = 1;
			} else if (reparacion_a_editar.getEnviado() == false) {
				enviado = 0;
			}

			if (reparacion_a_editar.getPresupuestoGenerado() == null) {
				PresupGenerado = 0;
			} else if (reparacion_a_editar.getPresupuestoGenerado() == true) {
				PresupGenerado = 1;
			} else if (reparacion_a_editar.getPresupuestoGenerado() == false) {
				PresupGenerado = 0;
			}

			if (reparacion_a_editar.getPresupuestoEnviado() == null) {
				PresupEnviado = 0;
			} else if (reparacion_a_editar.getPresupuestoEnviado() == true) {
				PresupEnviado = 1;
			} else if (reparacion_a_editar.getPresupuestoEnviado() == false) {
				PresupEnviado = 0;
			}

			if (reparacion_a_editar.getWORDgenerado() == null) {
				informeWordGenerado = 0;
			} else if (reparacion_a_editar.getWORDgenerado() == true) {
				informeWordGenerado = 1;
			} else if (reparacion_a_editar.getWORDgenerado() == false) {
				informeWordGenerado = 0;
			}

			if (reparacion_a_editar.getWORDenviado() == null) {
				informeWordEnviado = 0;
			} else if (reparacion_a_editar.getWORDenviado() == true) {
				informeWordEnviado = 1;
			} else if (reparacion_a_editar.getWORDenviado() == false) {
				informeWordEnviado = 0;
			}

			if (reparacion_a_editar.getAvisoEnviado() == null) {
				AvisoEnviado = 0;
			} else if (reparacion_a_editar.getAvisoEnviado() == true) {
				AvisoEnviado = 1;
			} else if (reparacion_a_editar.getAvisoEnviado() == false) {
				AvisoEnviado = 0;
			}

			if (reparacion_a_editar.getFechadereparacion() != null && reparacion_a_editar.getFechAceptacion() != null
					&& reparacion_a_editar.getFecha_Entrada() != null && reparacion_a_editar.getFecha_Salida() == null ) {
				statement = conexion.getSQLConexion().prepareStatement("UPDATE reparaciones SET FechaEntrada = '"
						+ reparacion_a_editar.getFecha_Entrada() + "' , " + "FechadeDiagnostico = '"
						+ reparacion_a_editar.getFechadereparacion() + "' ," + "Falla = '"
						+ reparacion_a_editar.getFalla() + "' ," + "Solucion = '" + reparacion_a_editar.getSolucion()
						+ "' ," + "idUsuario = '" + reparacion_a_editar.getidUsuario() + "' ," + "NombreUsuario = '"
						+ reparacion_a_editar.getNombreUsuario() + "' ," + "Enviado = '" + enviado + "' ,"
						+ "Informecliente = '" + reparacion_a_editar.getInformecliente() + "' ," + "EstadoFisico = '"
						+ reparacion_a_editar.getEstadoFisico() + "' ," + "EstadoTecnico = '"
						+ reparacion_a_editar.getEstadoTecnico() + "' ," + "Pago = '" + reparacion_a_editar.getPago()
						+ "' ," + "PrecioDolar = '" + reparacion_a_editar.getPrecioDolar() + "' ," + "PrecioPeso = '"
						+ reparacion_a_editar.getPrecioPeso() + "' ," + "EstadoComercial = '"
						+ reparacion_a_editar.getEstadoComercial() + "' ," + "lugar_de_ingreso = '"
						+ reparacion_a_editar.getLugarDeIngreso() + "' ," + "RemitoCliente = '"
						+ reparacion_a_editar.getRemitoCliente() + "' ," + "PresupuestoGenerado = '" + PresupGenerado
						+ "' ," + "OrdendeCompra = '" + reparacion_a_editar.getOrdendeCompra() + "' ,"
						+ "AvisoEnviado = '" + AvisoEnviado + "' ," + "PresupuestoEnviado = '" + PresupEnviado + "' ,"
						+ "FechAceptacion = '" + reparacion_a_editar.getFechAceptacion() + "'" + "WHERE ELS = "
						+ reparacion_a_editar.getELS() + "");

				System.out.println("0");

			} else if (reparacion_a_editar.getFechadereparacion() != null
					&& reparacion_a_editar.getFechAceptacion() == null
					&& reparacion_a_editar.getFecha_Entrada() != null) {
				statement = conexion.getSQLConexion().prepareStatement("UPDATE reparaciones SET FechaEntrada = '"
						+ reparacion_a_editar.getFecha_Entrada() + "' , " + "FechadeDiagnostico = '"
						+ reparacion_a_editar.getFechadereparacion() + "' ," + "Falla = '"
						+ reparacion_a_editar.getFalla() + "' ," + "Solucion = '" + reparacion_a_editar.getSolucion()
						+ "' ," + "OrdendeCompra = '" + reparacion_a_editar.getOrdendeCompra() + "' ," + "idUsuario = '"
						+ reparacion_a_editar.getidUsuario() + "' ," + "NombreUsuario = '"
						+ reparacion_a_editar.getNombreUsuario() + "' ," + "Enviado = '" + enviado + "' ,"
						+ "Informecliente = '" + reparacion_a_editar.getInformecliente() + "' ," + "PrecioDolar = '"
						+ reparacion_a_editar.getPrecioDolar() + "' ," + "PrecioPeso = '"
						+ reparacion_a_editar.getPrecioPeso() + "' ," + "Pago = '" + reparacion_a_editar.getPago()
						+ "' ," + "PresupuestoGenerado = '" + PresupGenerado + "' ," + "EstadoFisico = '"
						+ reparacion_a_editar.getEstadoFisico() + "' ," + "EstadoTecnico = '"
						+ reparacion_a_editar.getEstadoTecnico() + "' ," + "EstadoComercial = '"
						+ reparacion_a_editar.getEstadoComercial() + "' ," + "lugar_de_ingreso = '"
						+ reparacion_a_editar.getLugarDeIngreso() + "' ," + "RemitoCliente = '"
						+ reparacion_a_editar.getRemitoCliente() + "' ," + "FechAceptacion = null " + "WHERE ELS = "
						+ reparacion_a_editar.getELS() + "");

				System.out.println("1");

			}

			else if (reparacion_a_editar.getFechadereparacion() == null
					&& reparacion_a_editar.getFechAceptacion() != null
					&& reparacion_a_editar.getFecha_Entrada() != null
					) {

				statement = conexion.getSQLConexion().prepareStatement("UPDATE reparaciones SET FechaEntrada = '"
						+ reparacion_a_editar.getFecha_Entrada() + "' ," + "FechadeDiagnostico = null ," + "Falla = '"
						+ reparacion_a_editar.getFalla() + "' ," + "Solucion = '" + reparacion_a_editar.getSolucion()
						+ "' ," + "Enviado = '" + enviado + "' ," + "idUsuario = '" + reparacion_a_editar.getidUsuario()
						+ "' ," + "Informecliente = '" + reparacion_a_editar.getInformecliente() + "' ,"
						+ "EstadoFisico = '" + reparacion_a_editar.getEstadoFisico() + "' ," + "EstadoTecnico = '"
						+ reparacion_a_editar.getEstadoTecnico() + "' ," + "EstadoComercial = '"
						+ reparacion_a_editar.getEstadoComercial() + "' ," + "lugar_de_ingreso = '"
						+ reparacion_a_editar.getLugarDeIngreso() + "' ," + "' ," + "RemitoCliente = '"
						+ reparacion_a_editar.getRemitoCliente() + "' ," + "FechAceptacion = '"
						+ reparacion_a_editar.getFechAceptacion() + "'" + "WHERE ELS = " + reparacion_a_editar.getELS()
						+ "");

				System.out.println("2");

			}

			else if (reparacion_a_editar.getFechadereparacion() == null
					&& reparacion_a_editar.getFechAceptacion() == null
					&& reparacion_a_editar.getFecha_Entrada() != null) {
				statement = conexion.getSQLConexion().prepareStatement("UPDATE reparaciones SET FechaEntrada = '"
						+ reparacion_a_editar.getFecha_Entrada() + "' ," + "FechadeDiagnostico = null ," + "Falla = '"
						+ reparacion_a_editar.getFalla() + "' ," + "Solucion = '" + reparacion_a_editar.getSolucion()
						+ "' ," + "Enviado = '" + enviado + "' ," + "idUsuario = '" + reparacion_a_editar.getidUsuario()
						+ "' ," + "NombreUsuario = '" + reparacion_a_editar.getNombreUsuario() + "' ,"
						+ "Informecliente = '" + reparacion_a_editar.getInformecliente() + "' ," + "EstadoFisico = '"
						+ reparacion_a_editar.getEstadoFisico() + "' ," + "EstadoTecnico = '"
						+ reparacion_a_editar.getEstadoTecnico() + "' ," + "EstadoComercial = '"
						+ reparacion_a_editar.getEstadoComercial() + "' ," + "lugar_de_ingreso = '"
						+ reparacion_a_editar.getLugarDeIngreso() + "' ," + "RemitoCliente = '"
						+ reparacion_a_editar.getRemitoCliente() + "' ," + "FechAceptacion = null " + "WHERE ELS = "
						+ reparacion_a_editar.getELS() + "");

				System.out.println("3");
			}

			else if (reparacion_a_editar.getFechadereparacion() == null
					&& reparacion_a_editar.getFechAceptacion() == null && reparacion_a_editar.getFecha_Entrada() == null
					&& reparacion_a_editar.getNombreEquipo() == null
					&& reparacion_a_editar.getAgregadoaremito() == null) {

				if (reparacion_a_editar.getPrecioPeso() != null && reparacion_a_editar.getInformecliente() != null) {

					statement = conexion.getSQLConexion().prepareStatement("UPDATE reparaciones SET Informecliente = '"
							+ reparacion_a_editar.getInformecliente() + "' ," + "PrecioPeso = '"
							+ reparacion_a_editar.getPrecioPeso() + "' ," + "PresupuestoEnviado = '" + PresupEnviado
//								+ "' ," + "Pago = '"
//								+ reparacion_a_editar.getPago()
							+ "' ," + "PresupuestoGenerado = '" + PresupGenerado + "' ," + "PrecioDolar = '"
							+ reparacion_a_editar.getPrecioDolar() + "' ," + "WordGenerado = '" + informeWordGenerado
							+ "' ," + "WordEnviado = '" + informeWordEnviado + "'" + "WHERE ELS = "
							+ reparacion_a_editar.getELS() + "");

					System.out.println("4");
				}

				else if (reparacion_a_editar.getidUsuario() == 1) {

					statement = conexion.getSQLConexion().prepareStatement(
							"UPDATE reparaciones SET idUsuario = '" + reparacion_a_editar.getidUsuario() + "'"
									+ "WHERE ELS = " + reparacion_a_editar.getELS() + "");

					System.out.println("4.1.1");

				}
				
				else if (reparacion_a_editar.getFechadereparacion() != null && reparacion_a_editar.getFechAceptacion() != null
						&& reparacion_a_editar.getFecha_Entrada() != null && reparacion_a_editar.getFecha_Salida() != null) {
					statement = conexion.getSQLConexion().prepareStatement("UPDATE reparaciones SET FechaEntrada = '"
							+ reparacion_a_editar.getFecha_Entrada() + "' , " + "FechadeDiagnostico = '"
							+ reparacion_a_editar.getFechadereparacion() + "' ," + "Falla = '"
							+ reparacion_a_editar.getFalla() + "' ," + "Solucion = '" + reparacion_a_editar.getSolucion()
							+ "' ," + "idUsuario = '" + reparacion_a_editar.getidUsuario() + "' ," + "NombreUsuario = '"
							+ reparacion_a_editar.getNombreUsuario() + "' ," + "Enviado = '" + enviado + "' ,"
							+ "Informecliente = '" + reparacion_a_editar.getInformecliente() + "' ," + "EstadoFisico = '"
							+ reparacion_a_editar.getEstadoFisico() + "' ," + "EstadoTecnico = '"
							+ reparacion_a_editar.getEstadoTecnico() + "' ," + "Pago = '" + reparacion_a_editar.getPago()
							+ "' ," + "PrecioDolar = '" + reparacion_a_editar.getPrecioDolar() + "' ," + "PrecioPeso = '"
							+ reparacion_a_editar.getPrecioPeso() + "' ," + "EstadoComercial = '"
							+ reparacion_a_editar.getEstadoComercial() + "' ," + "lugar_de_ingreso = '"
							+ reparacion_a_editar.getLugarDeIngreso() + "' ," + "RemitoCliente = '"
							+ reparacion_a_editar.getRemitoCliente() + "' ," + "PresupuestoGenerado = '" + PresupGenerado
							+ "' ," + "OrdendeCompra = '" + reparacion_a_editar.getOrdendeCompra() + "' ,"

							+ "FechaSalida = '" + reparacion_a_editar.getFecha_Salida() + "' ,"

							+ "AvisoEnviado = '" + AvisoEnviado + "' ," + "PresupuestoEnviado = '" + PresupEnviado + "' ,"
							+ "FechAceptacion = '" + reparacion_a_editar.getFechAceptacion() + "'" + "WHERE ELS = "
							+ reparacion_a_editar.getELS() + "");

					System.out.println("4.1.1.2");
					}

				else {

					statement = conexion.getSQLConexion().prepareStatement("UPDATE reparaciones SET EstadoComercial = '"
							+ reparacion_a_editar.getEstadoComercial() + "' ," + "PrecioDolar = '"
							+ reparacion_a_editar.getPrecioDolar() + "' ," + "PrecioPeso = '"
							+ reparacion_a_editar.getPrecioPeso() + "' ," + "Pago = '" + reparacion_a_editar.getPago()
							+ "'" + "WHERE ELS = " + reparacion_a_editar.getELS() + "");

					System.out.println("4.2");

				}
			}

			else if (reparacion_a_editar.getFechAceptacion() != null
					&& reparacion_a_editar.getEstadoComercial() != null) {

				statement = conexion.getSQLConexion().prepareStatement(

						"UPDATE reparaciones SET EstadoComercial = '" + reparacion_a_editar.getEstadoComercial() + "' ,"
								+ "FechaSalida = '" + reparacion_a_editar.getFecha_Salida() + "' ,"
								+ "FechAceptacion = '" + reparacion_a_editar.getFechAceptacion() + "'" + "WHERE ELS = "
								+ reparacion_a_editar.getELS() + "");

				System.out.println("4.9");

			}

			else if (reparacion_a_editar.getAgregadoaremito() == true && reparacion_a_editar.getEnviado() == null
					&& reparacion_a_editar.getidRemito() != 0) {

				statement = conexion.getSQLConexion()
						.prepareStatement("UPDATE reparaciones SET Agregadoaremito = '" + 1 + "' ," + "idRemito = '"
								+ reparacion_a_editar.getidRemito() + "'" + "WHERE ELS = "
								+ reparacion_a_editar.getELS() + "");

				System.out.println("5");

			}

			else if (reparacion_a_editar.getEnviado() != null && reparacion_a_editar.getEstadoFisico() == "Enviado") {
				statement = conexion.getSQLConexion()
						.prepareStatement("UPDATE reparaciones SET EstadoFisico = '"
								+ reparacion_a_editar.getEstadoFisico() + "'," + "Enviado = '" + enviado + "'"
								+ "WHERE ELS = " + reparacion_a_editar.getELS() + "");

				System.out.println("6");

			}

			else if (reparacion_a_editar.getAgregadoaremito() == true && reparacion_a_editar.getRemitoGenerado() == true
					&& reparacion_a_editar.getidRemito() == 0) {

				statement = conexion.getSQLConexion()
						.prepareStatement("UPDATE reparaciones SET Agregadoaremito = '" + 0 + "' ," + "idRemito = '"
								+ reparacion_a_editar.getidRemito() + "' ," + "EstadoFisico = '"
								+ reparacion_a_editar.getEstadoFisico() + "'," + "Enviado = '" + enviado + "'"
								+ "WHERE ELS = " + reparacion_a_editar.getELS() + "");

				System.out.println("7");

			}

			if (statement.executeUpdate() > 0) // Si se ejecut� devuelvo true

				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarEquipo(JComboBox box) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallNombreEquipo);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			box.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(2)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarMarca(JComboBox comboMarca) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallMarca);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboMarca.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarModelosxMarca(JComboBox comboModelos, String marca) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<SucursalDTO> Sucursal = new ArrayList<SucursalDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallModeloxMarca);
			statement.setString(1, marca);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboModelos.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarSeriexModelo(JComboBox comboSerie, String modelo) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<SucursalDTO> Sucursal = new ArrayList<SucursalDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallSeriexModelo);
			statement.setString(1, modelo);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboSerie.setModel(value);

			// value.addElement(new ReparacionDTO("-- Seleccionar N� de Serie
			// --"));

			while (resultSet.next()) {
				// value.addElement(new SucursalDTO(resultSet.getInt(1),
				// resultSet.getString(2), resultSet.getString(3),
				// resultSet.getString(3), resultSet.getInt(4),
				// resultSet.getString(5)));

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarModelos(JComboBox comboModelos) {

		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallModelo);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboModelos.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarEstadoCom(JComboBox comboFiltroEstadoCom) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallEstadoCom);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboFiltroEstadoCom.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void ListarEstadoFis(JComboBox comboFiltroEstadoFis) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallEstadoFis);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboFiltroEstadoFis.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void comboFiltroEstadoTec(JComboBox comboFiltroEstadoTec) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallEstadoTec);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboFiltroEstadoTec.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void comboFiltroAviso(JComboBox comboFiltroAviso) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallAviso);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboFiltroAviso.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void comboFiltroELS(JComboBox comboFiltroELS) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallELS);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboFiltroELS.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void comboSerie(JComboBox comboSerie) {
		DefaultComboBoxModel value;

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		// ArrayList<ClienteDTO> Clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallSerie);
			resultSet = statement.executeQuery();
			value = new DefaultComboBoxModel();
			comboSerie.setModel(value);

			while (resultSet.next()) {

				value.addElement(new ReparacionDTO(resultSet.getString(1)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	@Override
	public List<ReparacionDTO> readAllXIDclienteIDSucursal(Integer IDCliente, Integer IDSucursal) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReparacionDTO> Reparaciones = new ArrayList<ReparacionDTO>();

		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxIDClienteIDSucursal);
			statement.setInt(1, IDCliente);
			statement.setInt(2, IDSucursal);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparaciones.add(new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("FechaEntrada"),
						resultSet.getString("FechadeDiagnostico"), resultSet.getString("Falla"),
						resultSet.getString("Solucion"), resultSet.getString("Informecliente"),
						resultSet.getInt("idUsuario"), resultSet.getString("EstadoFisico"),
						resultSet.getString("EstadoTecnico"), resultSet.getString("EstadoComercial"),
						resultSet.getString("RemitoCliente"), resultSet.getString("OrdendeCompra"),
						resultSet.getBoolean("Agregadoaremito"), resultSet.getBoolean("RemitoGenerado"),
						resultSet.getInt("idEquipo"), resultSet.getInt("idRemito"), resultSet.getDouble("PrecioPeso"),
						resultSet.getDouble("PrecioDolar"), resultSet.getBoolean("InformeEnviado"),
						resultSet.getString("FechAceptacion"), resultSet.getBoolean("PresupuestoGenerado"),
						resultSet.getBoolean("Enviado"), resultSet.getDouble("Pago"),
						resultSet.getBoolean("PresupuestoEnviado"), resultSet.getString("Equipos.Nombre"),
						resultSet.getString("email"), resultSet.getString("Modelo"), resultSet.getString("Marca"),
						resultSet.getString("NumeroDeSerie"), resultSet.getString("Aviso"),
						resultSet.getString("ClienteCliente"), resultSet.getInt("idCliente"),
						resultSet.getInt("idSucursal"), resultSet.getString("nombre"),
						resultSet.getString("NombreSucursal"), resultSet.getString("usuario.nombre"),
						resultSet.getInt("Codigo"), resultSet.getInt("NumeroRemitoSalida"),
						resultSet.getString("FechaFabr"), resultSet.getBoolean("AvisoEnviado"),
						resultSet.getBoolean("WordGenerado"), resultSet.getBoolean("WordEnviado"),
						resultSet.getString("lugar_de_ingreso"), resultSet.getString("FechaSalida")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparaciones;
	}

	@Override
	public List<ReparacionDTO> readAllXIDremito(int iDremito) {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReparacionDTO> Reparaciones = new ArrayList<ReparacionDTO>();

		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxIDremito);
			statement.setInt(1, iDremito);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparaciones.add(new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("FechaEntrada"),
						resultSet.getString("FechadeDiagnostico"), resultSet.getString("Falla"),
						resultSet.getString("Solucion"), resultSet.getString("Informecliente"),
						resultSet.getInt("idUsuario"), resultSet.getString("EstadoFisico"),
						resultSet.getString("EstadoTecnico"), resultSet.getString("EstadoComercial"),
						resultSet.getString("RemitoCliente"), resultSet.getString("OrdendeCompra"),
						resultSet.getBoolean("Agregadoaremito"), resultSet.getBoolean("RemitoGenerado"),
						resultSet.getInt("idEquipo"), resultSet.getInt("idRemito"), resultSet.getDouble("PrecioPeso"),
						resultSet.getDouble("PrecioDolar"), resultSet.getBoolean("InformeEnviado"),
						resultSet.getString("FechAceptacion"), resultSet.getBoolean("PresupuestoGenerado"),
						resultSet.getBoolean("Enviado"), resultSet.getDouble("Pago"),
						resultSet.getBoolean("PresupuestoEnviado"), resultSet.getString("Equipos.Nombre"),
						resultSet.getString("email"), resultSet.getString("Modelo"), resultSet.getString("Marca"),
						resultSet.getString("NumeroDeSerie"), resultSet.getString("Aviso"),
						resultSet.getString("ClienteCliente"), resultSet.getInt("idCliente"),
						resultSet.getInt("idSucursal"), resultSet.getString("nombre"),
						resultSet.getString("NombreSucursal"), resultSet.getString("usuario.nombre"),
						resultSet.getInt("Codigo"), resultSet.getInt("NumeroRemitoSalida"),
						resultSet.getString("FechaFabr"), resultSet.getBoolean("AvisoEnviado"),
						resultSet.getBoolean("WordGenerado"), resultSet.getBoolean("WordEnviado"),
						resultSet.getString("lugar_de_ingreso"), resultSet.getString("FechaSalida")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparaciones;

	}

	@Override
	public List<ReparacionDTO> readAllxComponenteOriginal(String componente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReparacionDTO> Reparaciones = new ArrayList<ReparacionDTO>();

		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxCompOriginal);
			statement.setString(1, componente);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparaciones.add(new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("FechaEntrada"),
						resultSet.getString("Cliente.nombre"), resultSet.getString("NombreSucursal"),
						resultSet.getString("Equipos.Nombre"), resultSet.getString("Marca"),
						resultSet.getString("Modelo"), resultSet.getString("original"),
						resultSet.getString("reemplazo")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparaciones;
	}

	@Override
	public List<ReparacionDTO> readAllxComponenteReemplazo(String componente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReparacionDTO> Reparaciones = new ArrayList<ReparacionDTO>();

		// String query = "select * from reparacionesT where ELS = ?";
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallxCompReemplazado);
			statement.setString(1, componente);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparaciones.add(new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("FechaEntrada"),
						resultSet.getString("Cliente.nombre"), resultSet.getString("NombreSucursal"),
						resultSet.getString("Equipos.Nombre"), resultSet.getString("Marca"),
						resultSet.getString("Modelo"), resultSet.getString("original"),
						resultSet.getString("reemplazo")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparaciones;
	}

	@Override
	public List<ReparacionDTO> readAllListadoMarcarAceptaciones() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ReparacionDTO> Reparaciones = new ArrayList<ReparacionDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallListadoMarcarAceptaciones);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Reparaciones.add(new ReparacionDTO(resultSet.getInt("ELS"), resultSet.getString("Aviso"),
						resultSet.getString("nombre"), resultSet.getString("NombreSucursal"),
						resultSet.getString("Equipos.Nombre"), resultSet.getString("Marca"),
						resultSet.getString("Modelo"), resultSet.getString("NumeroDeSerie"),
						resultSet.getString("EstadoTecnico"), resultSet.getString("EstadoComercial")

				));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return Reparaciones;
	}

	@Override

	public List<Integer> buscarEnCampos(String campo, String texto) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Integer> numeroELS = new ArrayList<>();

		try {
			// Validar que el nombre del campo sea seguro
			if (!esCampoValido(campo)) {
				throw new IllegalArgumentException("Campo no válido: " + campo);
			}

			// Crear la consulta dinámica con el campo
			String query = String.format(busquePorCampoYtexto, campo);

			statement = conexion.getSQLConexion().prepareStatement(query);

			// Agregar los comodines para la búsqueda con LIKE
			statement.setString(1, "%" + texto + "%");

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				numeroELS.add(resultSet.getInt("ELS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			conexion.cerrarConexion();
		}

		return numeroELS;
	}

	/**
	 * Verifica que el nombre del campo sea válido y seguro. Evita inyección SQL.
	 */
	private boolean esCampoValido(String campo) {
		// Lista de campos permitidos en la consulta
		List<String> camposPermitidos = Arrays.asList("Falla", "Solucion", "Informecliente" // Agrega aquí los campos
																							// válidos
		);
		return camposPermitidos.contains(campo);
	}

}
