USE `ordenesbrc`;
SELECT Cliente.idCliente, Cliente.nombre, Cliente.CUIT, Cliente.Domicilio, Cliente.TelefonoEmpresa,Cliente.Contacto,
 Cliente.TelefonoContacto, Cliente.CorreoElectronico,Sucursal.IdSucursal, Sucursal.NombreSucursal, reparaciones.ELS,
 DATE_FORMAT(FechaEntrada,'%Y%m%d') as FechaEntrada, DATE_FORMAT(FechadeDiagnostico,'%Y%m%d') as FechadeDiagnostico,
 reparaciones.Falla, reparaciones.Solucion, reparaciones.Informecliente, reparaciones.AvisoEnviado,
 reparaciones.PresupuestoEnviado,reparaciones.WordGenerado,reparaciones.WordEnviado,
 reparaciones.EstadoFisico, reparaciones.EstadoTecnico, reparaciones.EstadoComercial, reparaciones.RemitoCliente, 
 reparaciones.OrdendeCompra, reparaciones.Agregadoaremito, reparaciones.RemitoGenerado, reparaciones.idEquipo,
 reparaciones.idRemito, reparaciones.InformeEnviado,  DATE_FORMAT(FechAceptacion,'%Y%m%d') as FechAceptacion,
 reparaciones.PrecioPeso, reparaciones.PrecioDolar, reparaciones.Pago, reparaciones.NombreUsuario,usuario.idUsuario, usuario.nombre,
 Equipos.IdEquipo, Equipos.Nombre, Equipos.Modelo, Equipos.Marca, DATE_FORMAT(FechaFabr,'%Y%m%d') as FechaFabr,
 Equipos.NumeroDeSerie, Equipos.Aviso, Equipos.ClienteCliente, Equipos.RemitoCliente, Equipos.idCliente, reparaciones.PrecioPeso,
 reparaciones.PrecioDolar, reparaciones.PresupuestoGenerado, reparaciones.Enviado, Equipos.idSucursal, usuario.email, 
 Remitos.NumeroRemitoSalida, UbicacionRemitos.Ubicacion, UbicacionRemitos.Codigo, UbicacionRemitos.IdUbicacion, 
 reparaciones.Pago 
 FROM UbicacionRemitos INNER JOIN (Remitos INNER JOIN (((Cliente INNER JOIN Sucursal ON Cliente.IdCliente = Sucursal.idCliente) 
 INNER JOIN Equipos ON Cliente.idCliente=Equipos.idCliente) INNER JOIN (reparaciones INNER JOIN usuario) 
 ON Equipos.IdEquipo=reparaciones.idEquipo) ON Remitos.idRemito=reparaciones.idRemito) 
 ON UbicacionRemitos.IdUbicacion=Remitos.IdUbicacion 
 WHERE (((Cliente.idCliente)=Equipos.idCliente)) and ((Sucursal.IdSucursal)=Equipos.idSucursal) and  usuario.nombre != ''
 ORDER BY reparaciones.ELS ASC;