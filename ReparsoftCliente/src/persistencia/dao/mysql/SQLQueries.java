package persistencia.dao.mysql;

import java.util.Arrays;
import java.util.List;

public class SQLQueries {
    
    // Consultas básicas CRUD
    public static final String INSERT = 
        "INSERT INTO reparaciones(ELS,FechaEntrada,Falla, EstadoFisico, EstadoTecnico,EstadoComercial, RemitoCliente, idEquipo, idUsuario, lugar_de_ingreso) VALUES( ? , ? ,? , ? , ?,? , ? , ?,?,?)";
    
    public static final String INSERT_EQUIPO = 
        "INSERT INTO Equipos (IdEquipo, Nombre, Modelo, Marca, NumeroDeSerie, FechaFabr,Aviso, ClienteCliente, RemitoCliente, idCliente, idSucursal ) VALUES(? , ? ,? , ? , ? , ? , ? , ? , ?, ?,?)";
    
    public static final String DELETE = 
        "DELETE FROM reparaciones WHERE ELS = ?";
    
    public static final String UPDATE_REPARACION = 
        "UPDATE reparaciones SET " +
        "FechaEntrada = ?, FechadeDiagnostico = ?, Falla = ?, " +
        "Solucion = ?, Informecliente = ?, idUsuario = ?, " +
        "NombreUsuario = ?, EstadoFisico = ?, EstadoTecnico = ?, " +
        "EstadoComercial = ?, RemitoCliente = ?, OrdendeCompra = ?, " +
        "Agregadoaremito = ?, RemitoGenerado = ?, idEquipo = ?, " +
        "idRemito = ?, PrecioPeso = ?, PrecioDolar = ?, " +
        "FechAceptacion = ?, PresupuestoGenerado = ?, " +
        "PresupuestoEnviado = ?, WordGenerado = ?, WordEnviado = ?, " +
        "AvisoEnviado = ?, Pago = ?, FechaSalida = ?, " +
        "lugar_de_ingreso = ? " +
        "WHERE ELS = ?";
    
    public static final String UPDATE_EQUIPO = 
        "UPDATE Equipos SET Nombre = ?, Modelo = ?, Marca = ?, " +
        "NumeroDeSerie = ?, Aviso = ?, ClienteCliente = ?, " +
        "RemitoCliente = ?, idCliente = ?, IdSucursal = ?, " +
        "FechaFabr = ? WHERE IdEquipo = ?";
    
    public static final String UPDATE_EQUIPO_SIN_FECHA = 
        "UPDATE Equipos SET Nombre = ?, Modelo = ?, Marca = ?, " +
        "NumeroDeSerie = ?, Aviso = ?, ClienteCliente = ?, " +
        "RemitoCliente = ?, idCliente = ?, IdSucursal = ? " +
        "WHERE IdEquipo = ?";
    
    public static final String UPDATE_AGREGAR_REMITO = 
        "UPDATE reparaciones SET Agregadoaremito = ?, " +
        "RemitoGenerado = ?, idRemito = ? WHERE ELS = ?";
    
    public static final String UPDATE_MARCAR_ENVIADOS = 
        "UPDATE reparaciones SET EstadoFisico = ?, FechaSalida = ? WHERE ELS = ?";
    
    public static final String UPDATE_ANULAR_REMITO = 
        "UPDATE reparaciones SET EstadoFisico = ?, Agregadoaremito = ?, " +
        "RemitoGenerado = ?, idRemito = ? WHERE ELS = ?";
    
    public static final String UPDATE_PRESUPUESTO = 
        "UPDATE reparaciones SET Informecliente = ?, PrecioPeso = ?, " +
        "PrecioDolar = ?, PresupuestoGenerado = ?, PresupuestoEnviado = ?, " +
        "WordGenerado = ?, WordEnviado = ? WHERE ELS = ?";
    
    public static final String UPDATE_ACEPTACION = 
        "UPDATE reparaciones SET FechAceptacion = ?, EstadoComercial = ? WHERE ELS = ?";
    
    public static final String UPDATE_PAGO = 
        "UPDATE reparaciones SET PrecioPeso = ?, PrecioDolar = ?, " +
        "Pago = ?, EstadoComercial = ? WHERE ELS = ?";

    // Consultas de selección principales
    public static final String READ_ALL = 
        "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa, " +
        "Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, " +
        "reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, " +
        "reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente, reparaciones.AvisoEnviado,reparaciones.PresupuestoEnviado, " +
        "reparaciones.WordGenerado,reparaciones.WordEnviado, reparaciones.idUsuario,reparaciones.NombreUsuario, reparaciones.EstadoFisico, " +
        "reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, reparaciones.OrdendeCompra, " +
        "reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, reparaciones.idRemito, reparaciones.idUsuario, " +
        "DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.Pago, " +
        "usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, " +
        "DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, " +
        "Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, " +
        "Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, " +
        "UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, DATE_FORMAT(FechaSalida,'%Y%m%d') as FechaSalida " +
        "FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) " +
        "INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON " +
        "Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON " +
        "UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion " +
        "WHERE (((Cliente.idCliente)=Equipos.idCliente)) and ((Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "And ((usuario.IdUsuario)=reparaciones.idUsuario) ORDER BY reparaciones.ELS ASC";

    public static final String READ_ALL_X_ELS = 
        "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa, " +
        "Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, " +
        "reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, " +
        "reparaciones.Falla, reparaciones.Solucion, reparaciones.AvisoEnviado,reparaciones.PresupuestoEnviado, " +
        "reparaciones.WordGenerado,reparaciones.WordEnviado, reparaciones.Informecliente, reparaciones.idUsuario, " +
        "reparaciones.NombreUsuario,reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, " +
        "reparaciones.RemitoCliente, reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, " +
        "reparaciones.idEquipo, reparaciones.idRemito, reparaciones.idUsuario, DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, " +
        "usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, " +
        "DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, " +
        "Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, " +
        "Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, " +
        "UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, DATE_FORMAT(FechaSalida,'%Y%m%d') as FechaSalida " +
        "FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) " +
        "INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON " +
        "Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON " +
        "UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion " +
        "WHERE (((Cliente.idCliente)=Equipos.idCliente)) and ((Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "And ((usuario.IdUsuario)=reparaciones.idUsuario) and ELS = ?";

    public static final String READ_ALL_X_SERIE = 
        "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa, " +
        "Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, " +
        "reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, " +
        "reparaciones.Falla, reparaciones.Solucion,reparaciones.AvisoEnviado,reparaciones.PresupuestoEnviado, " +
        "reparaciones.WordGenerado,reparaciones.WordEnviado,reparaciones.Informecliente, reparaciones.idUsuario, " +
        "reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, " +
        "reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, " +
        "reparaciones.idRemito, reparaciones.idUsuario, DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, " +
        "usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, " +
        "DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, " +
        "Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, " +
        "Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, " +
        "UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, DATE_FORMAT(FechaSalida,'%Y%m%d') as FechaSalida " +
        "FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) " +
        "INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON " +
        "Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON " +
        "UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion " +
        "WHERE ((Cliente.idCliente)=Equipos.idCliente) and ((Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "and ((usuario.IdUsuario)=reparaciones.idUsuario) and Equipos.NumeroDeSerie = ?";

    public static final String READ_ALL_X_ID_CLIENTE_ID_SUCURSAL = 
        "SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa, " +
        "Cliente.Contacto, Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, " +
        "reparaciones.ELS, DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, " +
        "reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente,reparaciones.AvisoEnviado, " +
        "reparaciones.PresupuestoEnviado, reparaciones.WordGenerado,reparaciones.WordEnviado,reparaciones.idUsuario, " +
        "reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, " +
        "reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, " +
        "reparaciones.idRemito, reparaciones.idUsuario, DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, " +
        "usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, " +
        "DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, " +
        "Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, " +
        "Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, " +
        "UbicacionRemitos.IdUbicacion, reparaciones.Pago, reparaciones.lugar_de_ingreso, DATE_FORMAT(FechaSalida,'%Y%m%d') as FechaSalida " +
        "FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) " +
        "INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON " +
        "Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON " +
        "UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion " +
        "WHERE (((Cliente.idCliente)=Equipos.idCliente) And (Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "and ((usuario.IdUsuario)=reparaciones.idUsuario) and (reparaciones.EstadoComercial='Aceptado' || " +
        "reparaciones.EstadoComercial='NO Aceptado' ) and reparaciones.EstadoFisico != 'Enviado' and " +
        "reparaciones.Agregadoaremito != 1 and Cliente.idCliente = ? and Sucursal.IdSucursal = ? order by ELS";

    public static final String READ_ALL_X_ID_REMITO = 
        "SELECT * FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON " +
        "Cliente.IdCliente = Sucursal.idCliente) INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) " +
        "INNER JOIN (reparaciones INNER JOIN usuario) ON Equipos.IdEquipo=reparaciones.idEquipo) ON " +
        "Remitos.idRemito=reparaciones.idRemito) ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion " +
        "WHERE ((Cliente.idCliente)=Equipos.idCliente) And ((Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "and ((usuario.IdUsuario)=reparaciones.idUsuario) and Remitos.idRemito = ?";

    public static final String READ_ALL_LISTADO_MARCAR_ACEPTACIONES = 
        "SELECT reparaciones.ELS, Equipos.Aviso, Cliente.nombre, Sucursal.NombreSucursal, Equipos.Nombre, " +
        "Equipos.Modelo, Equipos.Marca, Equipos.NumeroDeSerie, reparaciones.EstadoTecnico, reparaciones.EstadoComercial " +
        "FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) " +
        "INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON " +
        "Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON " +
        "UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion " +
        "WHERE (((Cliente.idCliente)=Equipos.idCliente)) And ((Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "and reparaciones.EstadoComercial = 'A la Espera de Aceptación' and PresupuestoGenerado = true and " +
        "((usuario.IdUsuario)=reparaciones.idUsuario) ORDER BY reparaciones.ELS ASC";

    // Consultas de componentes
    public static final String READ_ALL_X_COMP_ORIGINAL = 
        "SELECT reemplazos.original,reemplazos.reemplazo, Cliente.idCliente, Cliente.nombre, Cliente.CUIT, " +
        "Cliente.Domicilio, Cliente.TelefonoEmpresa, Cliente.Contacto, Cliente.TelefonoContacto, " +
        "Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, " +
        "DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, " +
        "reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente, reparaciones.idUsuario, " +
        "reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, " +
        "reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, " +
        "reparaciones.idRemito, reparaciones.idUsuario, DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, " +
        "usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, " +
        "DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, " +
        "Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, " +
        "reparaciones.PresupuestoGenerado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, " +
        "UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, " +
        "reparaciones.lugar_de_ingreso, DATE_FORMAT(FechaSalida,'%Y%m%d') as FechaSalida " +
        "FROM (UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) " +
        "INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON " +
        "Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON " +
        "UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion ) INNER JOIN reemplazos ON reparaciones.ELS = reemplazos.ELS " +
        "WHERE ((Cliente.idCliente)=Equipos.idCliente) And ((Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "and ((usuario.IdUsuario)=reparaciones.idUsuario) and reemplazos.original = ?";

    public static final String READ_ALL_X_COMP_REEMPLAZO = 
        "SELECT reemplazos.original, reemplazos.reemplazo, Cliente.idCliente, Cliente.nombre, Cliente.CUIT, " +
        "Cliente.Domicilio, Cliente.TelefonoEmpresa, Cliente.Contacto, Cliente.TelefonoContacto, " +
        "Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS, " +
        "DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico, " +
        "reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente, reparaciones.idUsuario, " +
        "reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, " +
        "reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo, " +
        "reparaciones.idRemito, reparaciones.idUsuario, DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion, " +
        "usuario.idUsuario, usuario.nombre, Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, " +
        "DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr, Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, " +
        "Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso, reparaciones.PrecioDolar, " +
        "reparaciones.PresupuestoGenerado, Equipos.idSucursal, usuario.email, Remitos.NumeroRemitoSalida, " +
        "UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, reparaciones.Pago, " +
        "reparaciones.lugar_de_ingreso, DATE_FORMAT(FechaSalida,'%Y%m%d') as FechaSalida " +
        "FROM (UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) " +
        "INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) ON " +
        "Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) ON " +
        "UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion ) INNER JOIN reemplazos ON reparaciones.ELS = reemplazos.ELS " +
        "WHERE ((Cliente.idCliente)=Equipos.idCliente) and ((Sucursal.IdSucursal)=Equipos.idSucursal) " +
        "and ((usuario.IdUsuario)=reparaciones.idUsuario) and reemplazos.reemplazo = ?";

    // Consultas de máximos
    public static final String MAXIMO_ELS = 
        "Select MAX(ELS) from reparaciones";
    
    public static final String MAXIMO_ID_EQUIPO = 
        "Select MAX(IdEquipo) from Equipos";

    // Consultas para combos
    public static final String READ_ALL_NOMBRE_EQUIPO = 
        "SELECT DISTINCT * FROM Equipos group by Equipos.Nombre";
    
    public static final String READ_ALL_MARCA = 
        "SELECT Equipos.Marca FROM Equipos group by Equipos.Marca";
    
    public static final String READ_ALL_MODELO = 
        "SELECT Equipos.Modelo FROM Equipos group by Equipos.Modelo";
    
    public static final String READ_ALL_ELS = 
        "SELECT reparaciones.ELS FROM reparaciones group by reparaciones.ELS ORDER BY reparaciones.ELS ASC";
    
    public static final String READ_ALL_SERIE = 
        "SELECT DISTINCT Equipos.NumeroDeSerie FROM Equipos ORDER BY Equipos.NumeroDeSerie ASC";
    
    public static final String READ_ALL_AVISO = 
        "SELECT Equipos.Aviso FROM Equipos group by Equipos.Aviso";
    
    public static final String READ_ALL_ESTADO_COM = 
        "SELECT reparaciones.EstadoComercial FROM reparaciones group by reparaciones.EstadoComercial";
    
    public static final String READ_ALL_ESTADO_FIS = 
        "SELECT reparaciones.EstadoFisico FROM reparaciones group by reparaciones.EstadoFisico";
    
    public static final String READ_ALL_ESTADO_TEC = 
        "SELECT reparaciones.EstadoTecnico FROM reparaciones group by reparaciones.EstadoTecnico";
    
    public static final String READ_ALL_MODELO_X_MARCA = 
        "SELECT Equipos.Modelo FROM Equipos where Equipos.Marca = ? group by Equipos.Modelo";
    
    public static final String READ_ALL_SERIE_X_MODELO = 
        "SELECT Equipos.NumeroDeSerie FROM Equipos where Equipos.Modelo = ? group by Equipos.NumeroDeSerie";

    // Consultas de búsqueda
    public static final String BUSQUEDA_POR_CAMPO_Y_TEXTO = 
        "SELECT reparaciones.ELS FROM reparaciones WHERE %s LIKE ?";

    // Consultas estadísticas generales
    public static final String INGRESOS_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechaEntrada) = ?";
    
    public static final String DIAGNOSTICOS_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico != 'Sin Revisar'";
    
    public static final String FACTURACION_PESOS_POR_ANIO = 
        "select SUM(PrecioPeso) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo " +
        "where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.EstadoComercial = 'Aceptado'";
    
    public static final String FACTURACION_DOLAR_POR_ANIO = 
        "select SUM(PrecioDolar) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo " +
        "where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.EstadoComercial = 'Aceptado'";
    
    public static final String REPARADOS_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación')";
    
    public static final String SIN_FALLAS_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Falla'";
    
    public static final String REP_EN_GTIA_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado en Garantía'";
    
    public static final String EN_REP_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'En Reparación'";
    
    public static final String VENTAS_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Vendido'";
    
    public static final String SIN_REP_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Reparación'";
    
    public static final String REP_ACEP_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado'";
    
    public static final String REP_NO_ACEP_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado'";
    
    public static final String REP_ESPERA_POR_ANIO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación'";

    // Consultas estadísticas por mes
    public static final String INGRESOS_POR_ANIO_X_MES = 
        "select MONTH(reparaciones.FechaEntrada), count(*) from reparaciones where YEAR(FechaEntrada) = ? group by MONTH(FechaEntrada)";
    
    public static final String DIAGNOSTICO_POR_ANIO_X_MES = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.EstadoTecnico != 'Sin Revisar' group by MONTH(FechadeDiagnostico)";
    
    public static final String FACTURACION_POR_ANIO_X_MES = 
        "select MONTH(reparaciones.FechAceptacion), SUM(PrecioPeso) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(FechAceptacion)";

    // Consultas estadísticas por técnico
    public static final String DIAGNOSTICO_POR_ANIO_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico != 'Sin Revisar' group by MONTH(FechadeDiagnostico)";
    
    public static final String FACTURACION_POR_ANIO_X_TECNICO = 
        "select MONTH(reparaciones.FechAceptacion), SUM(PrecioPeso) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.idUsuario =? and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(FechAceptacion)";
    
    public static final String ACEPTACIONES_POR_ANIO_X_TECNICO = 
        "select MONTH(reparaciones.FechAceptacion), count(*) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'Vendido') and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(FechAceptacion)";
    
    public static final String FACTURACION_DOLAR_POR_ANIO_X_TECNICO_X_MES = 
        "select MONTH(reparaciones.FechAceptacion), SUM(PrecioDolar) from reparaciones where YEAR(FechAceptacion) = ? and reparaciones.idUsuario =? and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(FechAceptacion)";

    // Consultas estadísticas por cliente
    public static final String INGRESOS_X_ANIO_X_CLIENTE = 
        "select MONTH(reparaciones.FechaEntrada),count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechaEntrada) = ? and Equipos.idCliente = ? group by MONTH(FechaEntrada)";
    
    public static final String ACEPTACIONES_POR_ANIO_X_CLIENTE = 
        "select MONTH(reparaciones.FechAceptacion), count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ? and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(reparaciones.FechAceptacion)";
    
    public static final String FACTURACION_POR_ANIO_X_CLIENTE = 
        "select MONTH(reparaciones.FechAceptacion), SUM(PrecioPeso) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ? and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(reparaciones.FechAceptacion)";

    // Consultas estadísticas totales por cliente
    public static final String TOTAL_INGRESOS_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechaEntrada) = ? and Equipos.idCliente = ?";
    
    public static final String TOTAL_REPARADOS_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación') and Equipos.idCliente = ?";
    
    public static final String TOTAL_REP_EN_GTIA_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado en Garantía' and Equipos.idCliente = ?";
    
    public static final String TOTAL_SIN_FALLA_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Falla' and Equipos.idCliente = ?";
    
    public static final String TOTAL_EN_REP_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'En Reparación' and Equipos.idCliente = ?";
    
    public static final String TOTAL_VENTAS_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Vendido' and Equipos.idCliente = ?";
    
    public static final String TOTAL_SIN_REP_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Sin Reparación' and Equipos.idCliente = ?";
    
    public static final String TOTAL_REP_ACEP_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado' and Equipos.idCliente = ?";
    
    public static final String TOTAL_REP_NO_ACEP_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado' and Equipos.idCliente = ?";
    
    public static final String TOTAL_REP_ESPERA_X_ANIO_X_CLIENTE = 
        "select count(*) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechadeDiagnostico) = ? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación' and Equipos.idCliente = ?";

    // Consultas de facturación por cliente
    public static final String FACTURACION_PESO_POR_ANIO_POR_CLIENTE = 
        "select SUM(PrecioPeso) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ? and reparaciones.EstadoComercial = 'Aceptado'";
    
    public static final String FACTURACION_DOLAR_POR_ANIO_POR_CLIENTE = 
        "select SUM(PrecioDolar) from reparaciones INNER JOIN Equipos ON reparaciones.idEquipo = Equipos.idEquipo where YEAR(reparaciones.FechAceptacion) = ? and Equipos.idCliente = ? and reparaciones.EstadoComercial = 'Aceptado'";

    // Consultas de facturación por técnico
    public static final String FACTURACION_DOLAR_POR_ANIO_POR_TECNICO = 
        "select SUM(PrecioDolar) from reparaciones where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.idUsuario =? and reparaciones.EstadoComercial = 'Aceptado'";
    
    public static final String FACTURACION_PESO_POR_ANIO_POR_TECNICO = 
        "select SUM(PrecioPeso) from reparaciones where YEAR(reparaciones.FechAceptacion) = ? and reparaciones.idUsuario =? and reparaciones.EstadoComercial = 'Aceptado'";

    // Consultas estadísticas totales por técnico
    public static final String TOTAL_REP_ESPERA_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación'";
    
    public static final String TOTAL_REP_NO_ACEP_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado'";
    
    public static final String TOTAL_REP_ACEP_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado'";
    
    public static final String TOTAL_SIN_REP_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Reparación'";
    
    public static final String TOTAL_VENTAS_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Vendido'";
    
    public static final String TOTAL_EN_REP_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'En Reparación'";
    
    public static final String TOTAL_REP_EN_GTIA_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado en Garantía'";
    
    public static final String TOTAL_SIN_FALLA_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Falla'";
    
    public static final String TOTAL_REPARADOS_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación')";
    
    public static final String TOTAL_DIAGNOSTICOS_X_ANIO_X_TECNICO = 
        "select count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico != 'Sin Revisar'";

    // Consultas estadísticas por mes y técnico
    public static final String REPARADOS_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) group by MONTH(FechadeDiagnostico)";
    
    public static final String SIN_REP_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Reparación' group by MONTH(FechadeDiagnostico)";
    
    public static final String VENTAS_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Vendido' group by MONTH(FechadeDiagnostico)";
    
    public static final String EN_REP_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'En Reparación' group by MONTH(FechadeDiagnostico)";
    
    public static final String SIN_FALLA_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Sin Falla' group by MONTH(FechadeDiagnostico)";
    
    public static final String EN_GTIA_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado en Garantía' group by MONTH(FechadeDiagnostico)";
    
    public static final String REP_ESPERA_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico),count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'A la Espera de Aceptación' group by MONTH(FechadeDiagnostico)";
    
    public static final String REP_NO_ACEP_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico),count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and (reparaciones.EstadoTecnico = 'Reparado' or reparaciones.EstadoTecnico = 'No Aceptaron Reparación' ) and reparaciones.EstadoComercial = 'NO Aceptado' group by MONTH(FechadeDiagnostico)";
    
    public static final String REP_ACEP_X_MES_X_TECNICO = 
        "select MONTH(reparaciones.FechadeDiagnostico), count(*) from reparaciones where YEAR(FechadeDiagnostico) = ? and reparaciones.idUsuario =? and reparaciones.EstadoTecnico = 'Reparado' and reparaciones.EstadoComercial = 'Aceptado' group by MONTH(FechadeDiagnostico)";

    // Campos permitidos para búsqueda
    public static final List<String> CAMPOS_PERMITIDOS_BUSQUEDA = 
        Arrays.asList("Falla", "Solucion", "Informecliente");
}