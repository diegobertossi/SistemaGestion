Drop DATABASE if exists `ordenesbsas`;

CREATE DATABASE IF NOT EXISTS `ordenesbsas`;
USE `ordenesbsas`;

#ALTER DATABASE ordenesbrc DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
#
# Table structure for table 'Cliente'
#


DROP TABLE IF EXISTS `Cliente`;

CREATE TABLE `Cliente` (
  `idCliente` INTEGER NOT NULL, 
  `nombre` VARCHAR(255), 
  `CUIT` VARCHAR(50), 
  `Domicilio` LONGTEXT, 
  `TelefonoEmpresa` VARCHAR(50), 
  `Contacto` LONGTEXT, 
  `TelefonoContacto` VARCHAR(50), 
  `CorreoElectronico` VARCHAR(255), 
  PRIMARY KEY (`idCliente`)
) ;



#
# Dumping data for table 'Cliente'
#

INSERT INTO `Cliente` (`idCliente`, `nombre`, `CUIT`, `Domicilio`, `TelefonoEmpresa`, `Contacto`, `TelefonoContacto`, `CorreoElectronico`) VALUES (1, 'Ascensores Lucero', '30706433585', 'Las Violetas 1215, San Carlos de Bariloche, RÃ­o Negro', 542944425706, 'Ricardo Lucero', 2944602590, 'luceroasc@speedy.com.ar');
INSERT INTO `Cliente` (`idCliente`, `nombre`, `CUIT`, `Domicilio`, `TelefonoEmpresa`, `Contacto`, `TelefonoContacto`, `CorreoElectronico`) VALUES (2, 'Felipe', NULL, 'Personal', 0, NULL, 2944685047, NULL);
INSERT INTO `Cliente` (`idCliente`, `nombre`, `CUIT`, `Domicilio`, `TelefonoEmpresa`, `Contacto`, `TelefonoContacto`, `CorreoElectronico`) VALUES (3, 'Total Clima', '30708448202', 'Elordi 368 (8400), San Carlos de Bariloche. Rio Negro, Argentina', 2944431070, 'HÃ©ctor Spirito', 2944487014, 'info@totalclimaonline.com.ar');



DROP TABLE IF EXISTS `ClienteWSP`;

CREATE TABLE `ClienteWSP` (
  `idClienteWSP` INT NOT NULL AUTO_INCREMENT, 
  `organizacion`VARCHAR(255), 
  `nombreWSP` VARCHAR(255), 
  `TelefonoWSP` VARCHAR(50), 
   PRIMARY KEY (`idClienteWSP`)
) ;

INSERT INTO `ClienteWSP` (`idClienteWSP`,  `organizacion`,`nombreWSP`, `TelefonoWSP`) VALUES (1,'DiegoOrg' ,'Diego', '5491137688372');
INSERT INTO `ClienteWSP` (`idClienteWSP`,  `organizacion`,`nombreWSP`, `TelefonoWSP`) VALUES (2,'Total Clima' ,'Hector Spirito', '5491137688372');




DROP TABLE IF EXISTS `Sucursal`;

CREATE TABLE `Sucursal` (
  `IdSucursal` INTEGER NOT NULL , 
  `NombreSucursal` VARCHAR(255), 
  `idCliente` INTEGER, 
  `DomicilioSucursal` VARCHAR(255), 
  `ContactoSucursal` VARCHAR(255), 
  `TelefonoSucursal`  VARCHAR(50), 
  `CorreoElectronico` VARCHAR(255), 
  FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente),
  PRIMARY KEY (`IdSucursal`)
) ;

#
# Dumping data for table 'Sucursal'
#

#INSERT INTO `Sucursal` (`IdSucursal`, `NombreSucursal`, `idCliente`, `DomicilioSucursal`, `ContactoSucursal`, `TelefonoSucursal`, `CorreoElectronico`) VALUES (5, 'CABA', 9, NULL, NULL, NULL, NULL);
#INSERT INTO `Sucursal` (`IdSucursal`, `NombreSucursal`, `idCliente`, `DomicilioSucursal`, `ContactoSucursal`, `TelefonoSucursal`, `CorreoElectronico`) VALUES (6, 'MDP', 9, NULL, NULL, NULL, NULL);
#INSERT INTO `Sucursal` (`IdSucursal`, `NombreSucursal`, `idCliente`, `DomicilioSucursal`, `ContactoSucursal`, `TelefonoSucursal`, `CorreoElectronico`) VALUES (7, 'BRC', 9, NULL, NULL, NULL, NULL);
INSERT INTO `Sucursal` (`IdSucursal`, `NombreSucursal`, `idCliente`, `DomicilioSucursal`, `ContactoSucursal`, `TelefonoSucursal`, `CorreoElectronico`) VALUES (0, '', 1, NULL, NULL, NULL, NULL);
INSERT INTO `Sucursal` (`IdSucursal`, `NombreSucursal`, `idCliente`, `DomicilioSucursal`, `ContactoSucursal`, `TelefonoSucursal`, `CorreoElectronico`) VALUES (1, '', 2, NULL, NULL, NULL, NULL);
INSERT INTO `Sucursal` (`IdSucursal`, `NombreSucursal`, `idCliente`, `DomicilioSucursal`, `ContactoSucursal`, `TelefonoSucursal`, `CorreoElectronico`) VALUES (2, '', 3, NULL, NULL, NULL, NULL);



# 16 records

#
# Table structure for table 'Equipos'
#

DROP TABLE IF EXISTS `Equipos`;

CREATE TABLE `Equipos` (
  `IdEquipo` INTEGER NOT NULL DEFAULT 0, 
  `Nombre` VARCHAR(255), 
  `Modelo` VARCHAR(255), 
  `Marca` VARCHAR(255), 
  `NumeroDeSerie` VARCHAR(255),
  `FechaFabr` DATETIME DEFAULT null,
  `Aviso` VARCHAR(255), 
  `ClienteCliente` VARCHAR(255), 
  `RemitoCliente` VARCHAR(255), 
  `idCliente` INTEGER DEFAULT 0, 
  `IdSucursal` INTEGER, 
  FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente),
  FOREIGN KEY (IdSucursal) REFERENCES Sucursal(IdSucursal),
  PRIMARY KEY (`IdEquipo`)
) ;

#
# Dumping data for table 'Equipos'
#

 #INSERT INTO `Equipos` (`IdEquipo`, `Nombre`, `Modelo`, `Marca`, `NumeroDeSerie`,`FechaFabr`, `Aviso`, `ClienteCliente`, `RemitoCliente`, `idCliente`, `IdSucursal`) VALUES (1, 'Control de Puerta 220vac/250W', 'VVVF5', 'Fermator', '10/48306', NULL,"", NULL, NULL, 1,0 );
# INSERT INTO `Equipos` (`IdEquipo`, `Nombre`, `Modelo`, `Marca`, `NumeroDeSerie`,`FechaFabr`,`Aviso`, `ClienteCliente`, `RemitoCliente`, `idCliente`, `IdSucursal`) VALUES (2, 'Control de Puerta 220vac/250W', 'VVVF5', 'Fermator', '10/5613', NULL,"", NULL, NULL, 1, 0);
# INSERT INTO `Equipos` (`IdEquipo`, `Nombre`, `Modelo`, `Marca`, `NumeroDeSerie`,`FechaFabr`, `Aviso`, `ClienteCliente`, `RemitoCliente`, `idCliente`, `IdSucursal`) VALUES (3, 'Control de Puerta 220vac/250W', 'VVVF5', 'Fermator', '1/269271',NULL, "", NULL, NULL, 1, 0);




# Table structure for table 'Remitos'
#


DROP TABLE IF EXISTS `UbicacionRemitos`;

CREATE TABLE `UbicacionRemitos` (
  `IdUbicacion` INTEGER NOT NULL AUTO_INCREMENT, 
  `Ubicacion` VARCHAR(255), 
  `Codigo` INTEGER, 
  INDEX (`IdUbicacion`), 
  PRIMARY KEY (`IdUbicacion`)
) ;

#
# Dumping data for table 'UbicacionRemitos'
#

INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (1, 'CABA', 5);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (2, 'MDP', 2);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (3, 'COMUN CABA', 1000);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (4, 'COMUN MDP', 2000);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (5, 'COMUN BRC', 3000);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (6, NULL, NULL);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (7, 'BRC', 6);
# 7 records


DROP TABLE IF EXISTS `Remitos`;

CREATE TABLE `Remitos` (
  `idRemito` INTEGER NOT NULL DEFAULT 0, 
  `NumeroRemitoSalida` INTEGER, 
  `IdUbicacion` INTEGER DEFAULT 0, 
  FOREIGN KEY (IdUbicacion) REFERENCES UbicacionRemitos(IdUbicacion),
  PRIMARY KEY (`idRemito`)
) ;

#
# Dumping data for table 'Remitos'
#

INSERT INTO `Remitos` (`idRemito`, `NumeroRemitoSalida`, `IdUbicacion`) VALUES (0, null, 6);
#INSERT INTO `Remitos` (`idRemito`, `NumeroRemitoSalida`, `IdUbicacion`) VALUES (1, 1, 5);
#INSERT INTO `Remitos` (`idRemito`, `NumeroRemitoSalida`, `IdUbicacion`) VALUES (2, 2, 5);

# 31 records


DROP TABLE IF EXISTS `rol`;

create table rol(
idRol 		INT NOT NULL DEFAULT 0,
nombre 		VARCHAR(50),
PRIMARY KEY (idRol)
);

INSERT INTO rol SELECT 0,'Default';
INSERT INTO rol SELECT 1,'Administrador Programador';
INSERT INTO rol SELECT 2,'Tecnico';
INSERT INTO rol SELECT 3,'Contable';
INSERT INTO rol SELECT 4,'Tecnico Contable';




DROP TABLE IF EXISTS `usuario`;

create table usuario(
idUsuario INT NOT NULL AUTO_INCREMENT,
idRol				INT,
dni					INT,
nombre 				VARCHAR(50),
apellido 			VARCHAR(50),
telefono 			VARCHAR(50),
email 				VARCHAR(50),
login 				VARCHAR(50),
pass 				VARCHAR(50),
FOREIGN KEY (idRol) REFERENCES rol(idRol),
PRIMARY KEY (idUsuario)
) ;


INSERT INTO usuario SELECT 0,1,0,'','','','','','';
INSERT INTO usuario SELECT 0,1,30925503,'Diego','Bertossi','1137688372','diego.bertossi@elsweb.com.ar','diego','1234';
# INSERT INTO usuario SELECT 0,3,30925501,'Juan','Perez','1137688372','diego.bertossi@elsweb.com.ar','juan','0000';
# INSERT INTO usuario SELECT 0,4,30925505,'Pedro','Zerez','1137688372','diego.bertossi@elsweb.com.ar','pedro','0000';




#
# Table structure for table 'reparaciones'
#

DROP TABLE IF EXISTS `reparaciones`;

CREATE TABLE `reparaciones` (
  `ELS` INTEGER NOT NULL DEFAULT 0, 
  `FechaEntrada` DATETIME DEFAULT null ,  
  `FechadeDiagnostico` DATETIME DEFAULT null, 
  `Falla` VARCHAR(1000), 
  `Solucion` LONGTEXT, 
  `Informecliente` LONGTEXT, 
  `idUsuario` INTEGER DEFAULT 0, 
  `NombreUsuario` VARCHAR(255), 
  `EstadoFisico` VARCHAR(255), 
  `EstadoTecnico` VARCHAR(255), 
  `EstadoComercial` VARCHAR(255), 
  `RemitoCliente` VARCHAR(255), 
  `OrdendeCompra` VARCHAR(255), 
  `Agregadoaremito` TINYINT(1) DEFAULT 0, 
  `RemitoGenerado` TINYINT(1) DEFAULT 0, 
  `idEquipo` INTEGER DEFAULT 0, 
  `idRemito` INTEGER DEFAULT 0, 
  `PrecioPeso` DECIMAL(19,4) DEFAULT 0, 
  `PrecioDolar` DECIMAL(19,4) DEFAULT 0, 
  `InformeEnviado` TINYINT(1) DEFAULT 0, 
  `FechAceptacion` DATETIME DEFAULT null, 
  `PresupuestoGenerado` TINYINT(1) DEFAULT 0, 
  `PresupuestoEnviado` TINYINT(1) DEFAULT 0, 
  `WordGenerado` TINYINT(1) DEFAULT 0, 
  `WordEnviado` TINYINT(1) DEFAULT 0, 
  `Enviado` TINYINT(1) DEFAULT 0, 
  `AvisoEnviado` TINYINT(1) DEFAULT 0,
  `Pago` DECIMAL(19,4) DEFAULT 0, 
 
  FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario),
  FOREIGN KEY (idEquipo) REFERENCES Equipos(idEquipo),
  FOREIGN KEY (idRemito) REFERENCES Remitos(idRemito),
  
  PRIMARY KEY (`ELS`)
) ;

#
# Dumping data for table 'reparaciones'
#

#INSERT INTO `reparaciones` (`ELS`, `FechaEntrada`, `FechadeDiagnostico`, `Falla`, `Solucion`, `Informecliente`, `idUsuario`,`NombreUsuario`, `EstadoFisico`, `EstadoTecnico`, `EstadoComercial`, `RemitoCliente`, `OrdendeCompra`, `Agregadoaremito`, `RemitoGenerado`, `idEquipo`, `idRemito`, `PrecioPeso`, `PrecioDolar`, `InformeEnviado`, `FechAceptacion`, `PresupuestoGenerado`,`PresupuestoEnviado`,`WordGenerado`,`WordEnviado`, `Enviado`,`AvisoEnviado`, `Pago`) VALUES (1, '2017-09-06 00:00:00', '2017-09-11 00:00:00', '', '', '', 1,'Diego', 'BRC', 'Reparado', 'Aceptado', NULL, '00001', 0, 0, 1, 0, 100, 0, 1, '2017-09-12 00:00:00', 0,0,0,0, 0, 0,50);
#INSERT INTO `reparaciones` (`ELS`, `FechaEntrada`, `FechadeDiagnostico`, `Falla`, `Solucion`, `Informecliente`, `idUsuario`, `EstadoFisico`, `EstadoTecnico`, `EstadoComercial`, `RemitoCliente`, `OrdendeCompra`, `Agregadoaremito`, `RemitoGenerado`, `idEquipo`, `idRemito`, `PrecioPeso`, `PrecioDolar`, `InformeEnviado`, `FechAceptacion`, `PresupuestoGenerado`,`PresupuestoEnviado`, `Enviado`,`AvisoEnviado`, `Pago`) VALUES (2, '2017-09-06 00:00:00', '2017-09-11 00:00:00', 'No especifica', 'Tiene en corto un ULN2004 y eso ponÃ­a en corto varios puntos de la alimentaciÃ³n. Se reemplazÃ³ por el mismo sacado de otro equipo.', 'El equipo presenta fallas en la etapa de alimentaciÃ³n a la lÃ³gica de control, en la protecciÃ³n contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes daÃ±ados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\r\nSu reparaciÃ³n es posible.\r\nSe reemplazarÃ¡n los componentes daÃ±ados y el equipo serÃ¡ probado en banco bajo condiciones de laboratorio.', 3,  'BRC', 'Reparado', 'Aceptado', NULL, '00001', 0, 0, 2, 0, 1500, 0, 1, '2017-09-12 00:00:00', 1,0, 0,0, 1500);
#INSERT INTO `reparaciones` (`ELS`, `FechaEntrada`, `FechadeDiagnostico`, `Falla`, `Solucion`, `Informecliente`, `idUsuario`, `EstadoFisico`, `EstadoTecnico`, `EstadoComercial`, `RemitoCliente`, `OrdendeCompra`, `Agregadoaremito`, `RemitoGenerado`, `idEquipo`, `idRemito`, `PrecioPeso`, `PrecioDolar`, `InformeEnviado`, `FechAceptacion`, `PresupuestoGenerado`, `PresupuestoEnviado`,`Enviado`,`AvisoEnviado`, `Pago`) VALUES (3, '2017-09-06 00:00:00', '2017-09-11 00:00:00', 'No especifica', 'Tiene daÃ±ada una protecciÃ³n gaseosa y evidencias de una explosiÃ³n que afectÃ³ al circuito impreso.El mismo\r\ndebe ser reconstuÃ­do y se debe limpiar la zonaafectada. Se reemplazarÃ¡ el componente daÃ±ado por el mismo sacado de otra placa. ELS: 0004', 'El equipo presenta fallas en la etapa de alimentaciÃ³n a la lÃ³gica de control, en la protecciÃ³n contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes daÃ±ados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\r\nSu reparaciÃ³n es posible.\r\nSe reemplazarÃ¡n los componentes daÃ±ados y el equipo serÃ¡ probado en banco bajo condiciones de laboratorio.', 4, 'BRC', 'Reparado', 'Aceptado', NULL, NULL, 0, 0, 3, 0, 1500, 0, 1, '2017-12-21 00:00:00', 1,0, 0, 0,0);

# 80 records
#


#
# Table structure for table 'reemplazos'
#

DROP TABLE IF EXISTS `reemplazos`;

CREATE TABLE `reemplazos` (
  `idReemplazos` INTEGER NOT NULL AUTO_INCREMENT,
  `ELS` INTEGER, 
  `ref` VARCHAR(100), 
  `original` VARCHAR(100), 
  `reemplazo` VARCHAR(100), 
  `notas` VARCHAR(100), 
  FOREIGN KEY (ELS) REFERENCES reparaciones(ELS),
  PRIMARY KEY (`idReemplazos`)
) ;

#INSERT INTO `reemplazos`(`idReemplazos`, `ELS`, `ref`, `original`, `reemplazo`, `notas`) VALUES (1, 1,"L23","LM24","L24","");

# Table structure for table 'Sucursal'
#

#DROP TABLE IF EXISTS `Tecnicos`;

#CREATE TABLE `Tecnicos` (
#  `IdTecnico` INTEGER NOT NULL DEFAULT 0, 
#  `NombreTecnico` VARCHAR(255) NOT NULL, 
#  `Correo` VARCHAR(255), 
#  INDEX (`IdTecnico`), 
#  PRIMARY KEY (`IdTecnico`)
#) ENGINE=innodb DEFAULT CHARSET=utf8;

#SET autocommit=1;

#
# Dumping data for table 'Tecnicos'
#

#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (0, '-', '-');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (1, 'Diego Bertossi', 'diego.bertossi@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (2, 'Sergio Fernandez', 'sergio.fernandez@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (3, 'Ignacio Pintos', 'diego.bertossi@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (4, 'Bernardo Alvarez', 'bernardo.alvarez@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (6, 'Gustavo Lauro', 'gustavo@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (7, 'MatÃ­as', 'matias@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (8, 'Agustin Ortiz', 'agustin@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (9, 'Kathleen', 'kathleen@elsweb.com.ar');
#INSERT INTO `Tecnicos` (`IdTecnico`, `NombreTecnico`, `Correo`) VALUES (10, 'Natalia Seip', 'natalia.seip@fibertel.com.ar');
# 10 records

#
# Table structure for table 'UbicacionRemitos'
#


#insert into permisos select 0,2,idPantalla from pantalla ;





drop table if exists pantalla;

create table if not exists `pantalla` (
  `idPantalla` int(11) NOT NULL AUTO_INCREMENT,
  `idPantPadre` int(11) NULL,
  `nombre` VARCHAR(50) NOT NULL UNIQUE ,
  PRIMARY KEY (`idPantalla`),
	FOREIGN KEY (`idPantPadre`) REFERENCES `pantalla` (`idPantalla`)
);



create table if not exists `permisos` (
  `idPermiso` 	int(11) NOT NULL AUTO_INCREMENT,
  `idRol` 		int(11) NOT NULL ,
  `idPantalla` 	int(11) NOT NULL ,
  PRIMARY KEY (`idPermiso`),
  KEY `idRol` (`idRol`),
  KEY `idPantalla` (`idPantalla`),
  CONSTRAINT `permisos_fk1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`),
  CONSTRAINT `permisos_fk2` FOREIGN KEY (`idPantalla`) REFERENCES `pantalla` (`idPantalla`)
) ;



insert into pantalla select 0,null,'Equipos';
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Equipos';
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Salidas' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Listados' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Busquedas' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Clientes' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Presupuestos' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Usuarios' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'BackUp' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Programador'),'Configuracion' ;
insert into pantalla select 0,null,'Salidas' ;
insert into pantalla select 0,null,'Listados' ;
insert into pantalla select 0,null,'Busquedas' ;
insert into pantalla select 0,null,'Clientes' ;
insert into pantalla select 0,null,'Presupuestos' ;
insert into pantalla select 0,null,'Usuarios' ;
insert into pantalla select 0,null,'BackUp' ;
insert into pantalla select 0,null,'Configuracion' ;

#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Tecnico'),'Equipos';
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Tecnico'),'Salidas' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Tecnico'),'Listados' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Tecnico'),'Busquedas' ;
#insert into pantalla select 0,(select idPantalla from pantalla where nombre = 'Tecnico'),'Clientes' ;
insert into permisos select 0,1,idPantalla from pantalla ;