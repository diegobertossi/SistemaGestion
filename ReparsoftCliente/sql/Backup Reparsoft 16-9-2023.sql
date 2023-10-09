-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: ordenesbrc
-- ------------------------------------------------------
-- Server version	5.5.62

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `ordenesbrc`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ordenesbrc` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `ordenesbrc`;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `CUIT` varchar(50) DEFAULT NULL,
  `Domicilio` longtext,
  `TelefonoEmpresa` varchar(50) DEFAULT NULL,
  `Contacto` longtext,
  `TelefonoContacto` varchar(50) DEFAULT NULL,
  `CorreoElectronico` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Ascensores Lucero','30706433585','Las Violetas 1215, San Carlos de Bariloche, RÃ­o Negro','542944425706','Ricardo Lucero','2944602590','luceroasc@speedy.com.ar'),(2,'Felipe',NULL,'Personal','0',NULL,'2944685047',NULL),(3,'Total Clima','30708448202','Elordi 368 (8400), San Carlos de Bariloche. Rio Negro, Argentina','2944431070','HÃ©ctor Spirito','2944487014','info@totalclimaonline.com.ar'),(4,'Ariel (Técnico Portero)','1','','','','2944315784','');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientewsp`
--

DROP TABLE IF EXISTS `clientewsp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientewsp` (
  `idClienteWSP` int(11) NOT NULL AUTO_INCREMENT,
  `organizacion` varchar(255) DEFAULT NULL,
  `nombreWSP` varchar(255) DEFAULT NULL,
  `TelefonoWSP` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idClienteWSP`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientewsp`
--

LOCK TABLES `clientewsp` WRITE;
/*!40000 ALTER TABLE `clientewsp` DISABLE KEYS */;
INSERT INTO `clientewsp` VALUES (1,'DiegoOrg','Diego','5491137688372'),(2,'Total Clima','Hector Spirito','5491137688372');
/*!40000 ALTER TABLE `clientewsp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipos`
--

DROP TABLE IF EXISTS `equipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipos` (
  `IdEquipo` int(11) NOT NULL DEFAULT '0',
  `Nombre` varchar(255) DEFAULT NULL,
  `Modelo` varchar(255) DEFAULT NULL,
  `Marca` varchar(255) DEFAULT NULL,
  `NumeroDeSerie` varchar(255) DEFAULT NULL,
  `FechaFabr` datetime DEFAULT NULL,
  `Aviso` varchar(255) DEFAULT NULL,
  `ClienteCliente` varchar(255) DEFAULT NULL,
  `RemitoCliente` varchar(255) DEFAULT NULL,
  `idCliente` int(11) DEFAULT '0',
  `IdSucursal` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdEquipo`),
  KEY `idCliente` (`idCliente`),
  KEY `IdSucursal` (`IdSucursal`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipos`
--

LOCK TABLES `equipos` WRITE;
/*!40000 ALTER TABLE `equipos` DISABLE KEYS */;
INSERT INTO `equipos` VALUES (1,'Control de Puerta 220vac/250W','VVVF5','Fermator','10/48306',NULL,'','','',1,0),(2,'Control de Puerta 220vac/250W','VVVF5','Fermator','10/5613',NULL,'','','',1,0),(3,'Control de Puerta 220vac/250W','VVVF5','Fermator','1/269271',NULL,'','','',1,0),(4,'Control de Puerta 220vac/250W','VVVF5','Fermator','10/612',NULL,'','','',1,0),(5,'Control de Puerta 220vac/250W','VVVF4+','Fermator','2/84490',NULL,'','','',1,0),(6,'Control de Puerta 220vac/250W','VVVF4+','Fermator','5/35572',NULL,'','','',1,0),(7,'Control de Puerta 220vac/250W','VVVF4+','Fermator','8/107277',NULL,'','','',1,0),(8,'Variador de velocidad 30 KW','ATV58HD33N4','Telemecanique','0033000009',NULL,'','','',1,0),(9,'Monitor 19\"','Synmaster S19B300','Samsung','S041H9D209970A',NULL,'','','',2,1),(10,'Placa de caldera','ECIINT00(CS0116C-LS)','Baxi (Honeywell)','5653891',NULL,'','','',4,3);
/*!40000 ALTER TABLE `equipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pantalla`
--

DROP TABLE IF EXISTS `pantalla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pantalla` (
  `idPantalla` int(11) NOT NULL AUTO_INCREMENT,
  `idPantPadre` int(11) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idPantalla`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `idPantPadre` (`idPantPadre`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pantalla`
--

LOCK TABLES `pantalla` WRITE;
/*!40000 ALTER TABLE `pantalla` DISABLE KEYS */;
INSERT INTO `pantalla` VALUES (1,NULL,'Equipos'),(2,NULL,'Salidas'),(3,NULL,'Listados'),(4,NULL,'Busquedas'),(5,NULL,'Clientes'),(6,NULL,'Presupuestos'),(7,NULL,'Usuarios'),(8,NULL,'BackUp'),(9,NULL,'Configuracion');
/*!40000 ALTER TABLE `pantalla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos`
--

DROP TABLE IF EXISTS `permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permisos` (
  `idPermiso` int(11) NOT NULL AUTO_INCREMENT,
  `idRol` int(11) NOT NULL,
  `idPantalla` int(11) NOT NULL,
  PRIMARY KEY (`idPermiso`),
  KEY `idRol` (`idRol`),
  KEY `idPantalla` (`idPantalla`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT INTO `permisos` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9);
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reemplazos`
--

DROP TABLE IF EXISTS `reemplazos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reemplazos` (
  `idReemplazos` int(11) NOT NULL AUTO_INCREMENT,
  `ELS` int(11) DEFAULT NULL,
  `ref` varchar(100) DEFAULT NULL,
  `original` varchar(100) DEFAULT NULL,
  `reemplazo` varchar(100) DEFAULT NULL,
  `notas` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idReemplazos`),
  KEY `ELS` (`ELS`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reemplazos`
--

LOCK TABLES `reemplazos` WRITE;
/*!40000 ALTER TABLE `reemplazos` DISABLE KEYS */;
/*!40000 ALTER TABLE `reemplazos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remitos`
--

DROP TABLE IF EXISTS `remitos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `remitos` (
  `idRemito` int(11) NOT NULL DEFAULT '0',
  `NumeroRemitoSalida` int(11) DEFAULT NULL,
  `IdUbicacion` int(11) DEFAULT '0',
  PRIMARY KEY (`idRemito`),
  KEY `IdUbicacion` (`IdUbicacion`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remitos`
--

LOCK TABLES `remitos` WRITE;
/*!40000 ALTER TABLE `remitos` DISABLE KEYS */;
INSERT INTO `remitos` VALUES (0,NULL,6);
/*!40000 ALTER TABLE `remitos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reparaciones`
--

DROP TABLE IF EXISTS `reparaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reparaciones` (
  `ELS` int(11) NOT NULL DEFAULT '0',
  `FechaEntrada` datetime DEFAULT NULL,
  `FechadeDiagnostico` datetime DEFAULT NULL,
  `Falla` varchar(1000) DEFAULT NULL,
  `Solucion` longtext,
  `Informecliente` longtext,
  `idUsuario` int(11) DEFAULT '0',
  `NombreUsuario` varchar(255) DEFAULT NULL,
  `EstadoFisico` varchar(255) DEFAULT NULL,
  `EstadoTecnico` varchar(255) DEFAULT NULL,
  `EstadoComercial` varchar(255) DEFAULT NULL,
  `RemitoCliente` varchar(255) DEFAULT NULL,
  `OrdendeCompra` varchar(255) DEFAULT NULL,
  `Agregadoaremito` tinyint(1) DEFAULT '0',
  `RemitoGenerado` tinyint(1) DEFAULT '0',
  `idEquipo` int(11) DEFAULT '0',
  `idRemito` int(11) DEFAULT '0',
  `PrecioPeso` decimal(19,4) DEFAULT '0.0000',
  `PrecioDolar` decimal(19,4) DEFAULT '0.0000',
  `InformeEnviado` tinyint(1) DEFAULT '0',
  `FechAceptacion` datetime DEFAULT NULL,
  `PresupuestoGenerado` tinyint(1) DEFAULT '0',
  `PresupuestoEnviado` tinyint(1) DEFAULT '0',
  `WordGenerado` tinyint(1) DEFAULT '0',
  `WordEnviado` tinyint(1) DEFAULT '0',
  `Enviado` tinyint(1) DEFAULT '0',
  `AvisoEnviado` tinyint(1) DEFAULT '0',
  `Pago` decimal(19,4) DEFAULT '0.0000',
  PRIMARY KEY (`ELS`),
  KEY `idUsuario` (`idUsuario`),
  KEY `idEquipo` (`idEquipo`),
  KEY `idRemito` (`idRemito`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reparaciones`
--

LOCK TABLES `reparaciones` WRITE;
/*!40000 ALTER TABLE `reparaciones` DISABLE KEYS */;
INSERT INTO `reparaciones` VALUES (1,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tenía el capa de arranque dañado (47 uF * 25v) Se reemplazó por el mismo sacado de otro equipo.\nSE ENTREGÓ SIN CARGO','El equipo presenta fallas en la etapa de alimentación a la lógica de control, en la protección contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes dañados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','00001',0,0,1,0,0.0100,0.0000,1,NULL,1,0,0,0,0,0,0.0100),(2,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene en corto un ULN2004 y eso ponía en corto varios puntos de la alimentación. Se reemplazó por el mismo sacado de otro equipo.','El equipo presenta fallas en la etapa de alimentación a la lógica de control, en la protección contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes dañados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,2,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(3,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene dañada una protección gaseosa y evidencias de una explosión que afectó al circuito impreso.El mismo\ndebe ser reconstuído y se debe limpiar la zonaafectada. Se reemplazará el componente dañado por el mismo sacado de otra placa. ELS: 0004','El equipo presenta fallas en la etapa de alimentación a la lógica de control, en la protección contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes dañados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,3,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(4,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene en corto varios componentes incluyendo el micro M0380F6HP, por lo cual se sacaron componentes para lareparación de los otros equipo.  NO SE REPARA','',1,'Diego','BRC','No Tiene Reparación','Aceptado','','',0,0,4,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(5,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene el pack dañado. Además del capa del circuito Intermedio ( 220uF * 400V). Se denota que tiene rastros de humedad, ya que hay varios componentes (optos y resistencias) corroídos. NO SE REPARA POR AHORA.','',1,'Diego','BRC','No Tiene Reparación','A la Espera de Aceptación','','',0,0,5,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(6,'2023-09-06 00:00:00','2017-09-11 00:00:00','','Se revisaron componentes sin encontrar defectuosos. Se probó y funciona igual que los otros reparados.  Se realiza resoldado general y revisión completa.','Se denotan fallas en la etapa de alimentación al circuito de disparos y en las fuentes asociadas a la lógica de control.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,6,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(7,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tenía el capa de arranque dañado (47 uF * 25v) .','Se denotan fallas en la etapa de alimentación al circuito de disparos y en las fuentes asociadas a la lógica de control.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,7,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(8,'2017-09-13 00:00:00','2017-10-11 00:00:00','','Tiene en corto un integrado de fuente de la placa principal. Es un 1NTC001107. En la placa del display tiene otro el cual se saco y se colocó en la placa dañada y luego se alimentó en +RECT y -UC con un poco más de 60vdc y la fuente arranca. Tengo 12vdc a la salida del cooler. Se revisaron packs de potencia y coolers sin encontrar desperfectos. Se necesita ese integrado. Además tiene mucha tierra.','El equipo presenta fallas en las protecciones contra sobre tensiones, en  la etapa de fuente de arranque y en las alimentaciones asociadas a los circuitos de disparos del pack de potencia. Se denotan componentes dañados debido a un corto circuito en toda una rama de alimentación que afectó a varios drivers de fuente y de control de la lógica. \nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio. \nSe recomienda verificar el estado y los niveles de tensiones presentes en la línea como así también el conexionado y correcto cableado.',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,8,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(9,'2017-10-06 00:00:00','2017-10-09 00:00:00','','No enciende. Tiene la fuente aparte y ese es el problema. La misma tiene en corto un diodo de salida SB2100. Tiene dos por lo cual se reemplazaron ambos por BYM36C sacados de scrap. Se mantuvo en prueba por dos días sin presentar falla.','',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,9,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(10,'2017-10-09 00:00:00','2017-10-11 00:00:00','','Tiene varios falsos contactos y además se denota un capa de 10uF * 50v dañado y uno de poliester de 100nf*275 mal. Se reemplazaron componentes. se alimentó con 220vac por  M1 (15 y 16) ... el diagrama es muy similar al de la placa BAXI \"manual_eco280i\", que está en el servidor. Por otro lado se aclara al cliente que esta placa tenia fallas y está reparada, pero que el equipo puede conformarse por más placas que pueden llegar a fallar.','',1,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,10,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000);
/*!40000 ALTER TABLE `reparaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Administrador Programador'),(2,'Tecnico'),(3,'Contable'),(4,'Tecnico Contable');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sucursal`
--

DROP TABLE IF EXISTS `sucursal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sucursal` (
  `IdSucursal` int(11) NOT NULL,
  `NombreSucursal` varchar(255) DEFAULT NULL,
  `idCliente` int(11) DEFAULT NULL,
  `DomicilioSucursal` varchar(255) DEFAULT NULL,
  `ContactoSucursal` varchar(255) DEFAULT NULL,
  `TelefonoSucursal` varchar(50) DEFAULT NULL,
  `CorreoElectronico` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IdSucursal`),
  KEY `idCliente` (`idCliente`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sucursal`
--

LOCK TABLES `sucursal` WRITE;
/*!40000 ALTER TABLE `sucursal` DISABLE KEYS */;
INSERT INTO `sucursal` VALUES (0,'',1,NULL,NULL,NULL,NULL),(1,'',2,NULL,NULL,NULL,NULL),(2,'',3,NULL,NULL,NULL,NULL),(3,'',4,'','','','');
/*!40000 ALTER TABLE `sucursal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ubicacionremitos`
--

DROP TABLE IF EXISTS `ubicacionremitos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ubicacionremitos` (
  `IdUbicacion` int(11) NOT NULL AUTO_INCREMENT,
  `Ubicacion` varchar(255) DEFAULT NULL,
  `Codigo` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdUbicacion`),
  KEY `IdUbicacion` (`IdUbicacion`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ubicacionremitos`
--

LOCK TABLES `ubicacionremitos` WRITE;
/*!40000 ALTER TABLE `ubicacionremitos` DISABLE KEYS */;
INSERT INTO `ubicacionremitos` VALUES (1,'CABA',5),(2,'MDP',2),(3,'COMUN CABA',1000),(4,'COMUN MDP',2000),(5,'COMUN BRC',3000),(6,NULL,NULL),(7,'BRC',6);
/*!40000 ALTER TABLE `ubicacionremitos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `idRol` int(11) DEFAULT NULL,
  `dni` int(11) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `login` varchar(50) DEFAULT NULL,
  `pass` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `idRol` (`idRol`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,30925503,'Diego','Bertossi','1137688372','diego.bertossi@elsweb.com.ar','diego','1234');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-16 17:15:07
