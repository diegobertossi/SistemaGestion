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

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ordenesbrc` /*!40100 DEFAULT CHARACTER SET utf8 */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Ascensores Lucero','30706433585','Las Violetas 1215, San Carlos de Bariloche, RÃƒÂ­o Negro','542944425706','Ricardo Lucero','2944602590','luceroasc@speedy.com.ar'),(2,'Felipe',NULL,'Personal','0',NULL,'2944685047',NULL),(3,'Total Clima','30708448202','Elordi 368 (8400), San Carlos de Bariloche. Rio Negro, Argentina','2944431070','Héctor Spirito','2944487014','info@totalclimaonline.com.ar'),(4,'A3 Unión Gráfica','','','','Gastón','2944639503','a3uniongrafica@gmail.com'),(5,'Alejandro Azocar','','Particular','','','',''),(6,'Alicia','-------','Particular','','','2944501202',''),(7,'Andrés','','','','','',''),(8,'Aranda, Julián','','','','','5492944217841','arandajulian116@gmail.com'),(9,'Ariel','','','','','2945910546',''),(10,'Ariel (Técnico Portero)','','','','Ariel','5492944315784',''),(11,'Ariel Antonio Barea','20121625920','Av. San Martín 3220 - El Bolsón -RN','','','5492944576626','barilocheascensores@gmail.com'),(12,'Ascensores Autore','23173266669','Sta Elisa 260 Playa Bonita','','Juan Carlos Autore','2944677779','autorealberto@yahoo.com'),(13,'Ascensores Bariloche','','','','Claudio','5492944801351','barilocheascensores@gmail.com'),(14,'Ascensores Patagonia','','','','','',''),(15,'Cabañas La Estancia','','Ruta 40 km, Villa la Angostura 2112','','Lic. Sergio Alvarez','5492944495278','info@laestanciapatagonia.com.ar'),(16,'Daniel Calderista','','','','','','heat.central@gmail.com'),(17,'Distribuidora Patagonia','','Diagonal Capraro','','Luciano Zeizz','2944412526','lzeiss@yahoo.com.ar'),(18,'Domingo','','','','','5492944564527',''),(19,'ELS','20214888956','Arcos 4002 4to A','1147032205','Sergio Fernández','9111554037207','els@elsweb.com.ar'),(20,'Enciso, Oscar','','','','Oscar Enciso','1160521436','tecnosportargentina@gmail.com'),(21,'Enrique Dominguez','','','','Enrique','5492944555986','enriquedominguez53@gmail.com'),(22,'Facundo Calderista','','','','Facundo','2944511966',''),(23,'Fernanda','','','','','1163579548',''),(24,'Gabriel','','','','','5492944288680',''),(25,'Gustavo Calderas','','','','','','belgranodecks@gmail.com'),(26,'Hernán Calderas','','','','','',''),(27,'Hotel NH Bariloche Edelweiss','30548183614','San Martín 232, San Carlos de Bariloche, Río Negro','2944445510','mt.nhedelweiss@nh-hotels.com','2944673040',''),(28,'Huilque SRL','30660795568','AVENIDA CARLOS BUSTOS 329 / PARAJE: CERRO CATEDRAL Código postal: 8400 RIO NEGRO','','Santiago Lema','2944921455','santiagol@huilque.com'),(29,'Jorge Roca','','','','Jorge Roca','2944502450','jorgeroca@gmail.com'),(30,'Administración de Parques Nacionales','30571910833','','','','','stomas@apn.gob.ar'),(31,'Adrián Rodríguez TV Philco 43','','','','','',''),(32,'Alberto (total Clima)','','','','','',''),(33,'Alejandro (2do 13)','','','','','',''),(34,'Alejandro Azocar','','','','','',''),(35,'Alicia','','','','','',''),(36,'Alma del Lago','','Av. Bustillo km 1,151','','','','abastecimiento@almasuites.com.ar'),(37,'Andrea ( Jardín Luna de Colores)','','','','','',''),(38,'Andrea TV 32','','','','','',''),(39,'Aranda, Julián','','','','','5492944217841','arandajulian116@gmail.com'),(40,'Ascensores Basaldua','20263643810','','','Matías','5491150193542','elevatec@yahoo.com.ar'),(41,'Asociación Club Los Pehuenes','30632773354','Pintores Argentinos 250','','Leandro','2944269793',''),(42,'Blás Galduróz','','','','','5492944131525',''),(43,'CNEA Centro Atómico Bariloche','30546660210','Av. Ezequiel Bustillo KM 9,500','','Sotelo, Juan José','','juan.sotelo@cab.cnea.gov.ar'),(44,'Concesionaria Fiat Pire Rayen','30575314674','','','Jonathan Astrada','2944130522',''),(45,'Convertec-Energías Renovables Bariloche','','Rivadavia 571. San Carlos de Bariloche','','Leandro Suárez','5492944635057','info@convertec.com.ar'),(46,'Daniel Lopez (COARCO)','','','','','5492944365038',''),(47,'Tito','','','','','','');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
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
  KEY `IdSucursal` (`IdSucursal`),
  CONSTRAINT `equipos_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  CONSTRAINT `equipos_ibfk_2` FOREIGN KEY (`IdSucursal`) REFERENCES `sucursal` (`IdSucursal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipos`
--

LOCK TABLES `equipos` WRITE;
/*!40000 ALTER TABLE `equipos` DISABLE KEYS */;
INSERT INTO `equipos` VALUES (1,'Control de Puerta 220vac/250W','VVVF5','Fermator','10/48306',NULL,'','','',1,0),(2,'Control de Puerta 220vac/250W','VVVF5','Fermator','10/5613',NULL,'',NULL,NULL,1,0),(3,'Control de Puerta 220vac/250W','VVVF5','Fermator','1/269271',NULL,'',NULL,NULL,1,0),(4,'Control de Puerta 220vac/250W','VVVF5','Fermator','10/5613',NULL,'','','',1,0),(5,'Control de Puerta 220vac/250W','VVVF5','Fermator','1/269271',NULL,'','','',1,0),(6,'Control de Puerta 220vac/250W','VVVF5','Fermator','10/612',NULL,'','','',1,0),(7,'Control de Puerta 220vac/250W','VVVF4+','Fermator','2/84490',NULL,'','','',1,0),(8,'Control de Puerta 220vac/250W','VVVF4+','Fermator','5/35572',NULL,'','','',1,0),(9,'Control de Puerta 220vac/250W','VVVF4+','Fermator','8/107277',NULL,'','','',1,0),(10,'Variador de velocidad 30KW','ATV58HD33N4','Telemecanique','0335000009',NULL,'','','',1,0),(11,'Monitor 19\"','Syncmaster S19B300','Samsung','S041H9XD209970A',NULL,'','','',2,1),(12,'Placa de caldera','ECIINT00(CS0116C-LS)','Baxi(Honeywell)','5653891',NULL,'','','',10,9),(13,'Placa Fuente de ascensor','A6210','Automac','3199',NULL,'','','',1,0),(14,'Placa de caldera','A6220v3','Automac','60689',NULL,'','','',1,0),(15,'Placa de botón de ascensor','DC8967G007','','240206',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(16,'Placa de boton de ascensor','DC8967G007','','050106',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(17,'Placa de botón de ascensor','DC5967G007','','280404',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(18,'Placa de botón de ascensor','DC8967G007','','271005',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(19,'Placa de botón de ascensor','DC8967G007','','060607',NULL,'','','',19,20),(20,'Placa de botón de ascensor','DC8967G007','','2009',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(21,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(22,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(23,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(24,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(25,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(26,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(27,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(28,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(29,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(30,'Fuente de display de ascensor','JP0233S02','','06040073832',NULL,'','Dalkia 35 - Sanatorio de los Arcos','',19,20),(31,'Placa de disparos','A5E00714561','Siemens','T-B71023925',NULL,'','Siemens Labo','',19,20),(32,'Placa de disparos','A5E00714561','Siemens','T-T21018270',NULL,'','Siemens Labo','',19,20),(33,'Placa control de ascensor','A6403V3','Automac','0433',NULL,'','','',1,0),(34,'Balastro electrónico LED','LC1x30-E-DA','Helvar','5502001G38200797 6',NULL,'','Dalkia 5 - Cata Norte','',19,20),(35,'Balastro electrónico LED','LC1x30-E-DA','Helvar','5502001K20403418 1',NULL,'','Dalkia 5 - Cata Norte','',19,20),(36,'Balastro electrónico LED','EL1/2x26-42iDim-c','Helvar','4228000E30101674',NULL,'','Dalkia 5 - Cata Norte','',19,20),(37,'Balastro electrónico','EL1/2x26-42iDim-c','Helvar','4228000E30101317',NULL,'','Dalkia 5 - Cata Norte','',19,20),(38,'Balastro electrónico','EL1/2x26-42iDim-c','Helvar','4228000E32101904',NULL,'','Dalkia 5 - Cata Norte','',19,20),(39,'Balastro electrónico','EL1/2x26-42iDim-c','Helvar','4228000E30101534',NULL,'','Dalkia 5 - Cata Norte','',19,20),(40,'Balastro electrónico','EL1/2x26-42iDim-c','Helvar','4228000E32102169',NULL,'','Dalkia 5 - Cata Norte','',19,20),(41,'Plca de central de alarmas 8 zonas','','Zuden','3VO9F',NULL,'','','',29,30),(42,'Placa de unidad exterior multisplit','6870A90154S','LG','20071221 08082',NULL,'','','',3,2),(43,'Kit Portón automatización corredizo 1.5HP','500K','SEG','898511',NULL,'','','',47,48),(44,'Placa control de ascensor','A6403V3','Automac','0433',NULL,'','','',1,0),(45,'Fuente de alimentación','0950-2528','Agilent','YN771481ZH9K',NULL,'16503','AGS Analítica','',19,20),(46,'Fuente de alimentación','SF50-EE(LG)','Suntronix','S332635',NULL,'16475','Heavenward','',19,20),(47,'Placa Fuente de Ascensor 12V / 2,5A','ZWS30-12/J','TDK Lambda','751CF4HFP',NULL,'16488','Heavenward','',19,20),(48,'Placa de ascensor','DOR-123 01','Mitsubishi','YX401B267*',NULL,'16489','Heavenward','',19,20),(49,'Placa de ascensor','KCR-750C','Mitsubishi','7B28',NULL,'16490','Heavenward','',19,20),(50,'Variador de velocidad 11A','AS2-122','Adleepower','AS3090778',NULL,'16367','Emanuel','',19,20),(51,'Placa de Botonera de Ascensor','LHB-059A','Mitsubishi','1111B',NULL,'16474','Heavenward','',19,20);
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
  KEY `idPantPadre` (`idPantPadre`),
  CONSTRAINT `pantalla_ibfk_1` FOREIGN KEY (`idPantPadre`) REFERENCES `pantalla` (`idPantalla`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
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
  KEY `idPantalla` (`idPantalla`),
  CONSTRAINT `permisos_fk1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`),
  CONSTRAINT `permisos_fk2` FOREIGN KEY (`idPantalla`) REFERENCES `pantalla` (`idPantalla`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT INTO `permisos` VALUES (1,2,1),(2,2,2),(3,2,3),(4,2,4),(5,2,5),(6,2,6),(7,2,7),(8,2,8),(9,2,9);
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
  KEY `ELS` (`ELS`),
  CONSTRAINT `reemplazos_ibfk_1` FOREIGN KEY (`ELS`) REFERENCES `reparaciones` (`ELS`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
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
  KEY `IdUbicacion` (`IdUbicacion`),
  CONSTRAINT `remitos_ibfk_1` FOREIGN KEY (`IdUbicacion`) REFERENCES `ubicacionremitos` (`IdUbicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remitos`
--

LOCK TABLES `remitos` WRITE;
/*!40000 ALTER TABLE `remitos` DISABLE KEYS */;
INSERT INTO `remitos` VALUES (0,NULL,6),(1,1,5),(2,2,5),(3,3,5),(4,4,5),(5,5,5),(6,6,5),(7,7,5),(8,8,5),(10,10,5);
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
  `Enviado` tinyint(1) DEFAULT '0',
  `AvisoEnviado` tinyint(1) DEFAULT '0',
  `Pago` decimal(19,4) DEFAULT '0.0000',
  PRIMARY KEY (`ELS`),
  KEY `idUsuario` (`idUsuario`),
  KEY `idEquipo` (`idEquipo`),
  KEY `idRemito` (`idRemito`),
  CONSTRAINT `reparaciones_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `reparaciones_ibfk_2` FOREIGN KEY (`idEquipo`) REFERENCES `equipos` (`IdEquipo`),
  CONSTRAINT `reparaciones_ibfk_3` FOREIGN KEY (`idRemito`) REFERENCES `remitos` (`idRemito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reparaciones`
--

LOCK TABLES `reparaciones` WRITE;
/*!40000 ALTER TABLE `reparaciones` DISABLE KEYS */;
INSERT INTO `reparaciones` VALUES (1,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tenía el capa de arranque dañado (47 uF * 25v) Se reemplazó por el mismo sacado de otro equipo.\nSE ENTREGÓ SIN CARGO','El equipo presenta fallas en la etapa de alimentación a la lógica de control, en la protección contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes dañados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',2,'Enviado','Reparado','Aceptado','','00001',1,0,1,1,0.0000,0.0000,1,'2017-09-12 00:00:00',1,0,0,1,0.0000),(2,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene en corto un ULN2004 y eso ponía en corto varios puntos de la alimentación. Se reemplazó por el mismo sacado de otro equipo.','El equipo presenta fallas en la etapa de alimentación a la lógica de control, en la protección contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes dañados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',2,'Enviado','Reparado','Aceptado','',NULL,1,0,4,3,1500.0000,0.0000,0,'2017-09-12 00:00:00',1,1,1,0,1500.0000),(3,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene dañada una protección gaseosa y evidencias de una explosión que afectó al circuito impreso.El mismo\ndebe ser reconstuído y se debe limpiar la zonaafectada. Se reemplazará el componente dañado por el mismo sacado de otra placa. ELS: 0004','El equipo presenta fallas en la etapa de alimentación a la lógica de control, en la protección contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes dañados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',2,'Enviado','Reparado','Aceptado','',NULL,1,0,5,3,1500.0000,0.0000,0,'2017-11-21 00:00:00',1,0,1,0,1500.0000),(4,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene en corto varios componentes incluyendo el micro M0380F6HP, por lo cual se sacaron componentes para lareparación de los otros equipo.  NO SE REPARA','',2,'DESGUACE','Desguace','Aceptado','',NULL,0,0,6,0,0.0000,0.0000,0,'2017-12-27 00:00:00',0,0,0,0,0.0000),(5,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tiene el pack dañado. Además del capa del circuito Intermedio ( 220uF * 400V). Se denota que tiene rastros de humedad, ya que hay varios componentes (optos y resistencias) corroídos. NO SE REPARA POR AHORA.','',2,'DESGUACE','Desguace','Aceptado','',NULL,0,0,7,0,0.0000,0.0000,0,'2017-12-27 00:00:00',0,0,0,0,0.0000),(6,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Se revisaron componentes sin encontrar defectuosos. Se probó y funciona igual que los otros reparados.  Se realiza resoldado general y revisión completa.','Se denotan fallas en la etapa de alimentación al circuito de disparos y en las fuentes asociadas a la lógica de control.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',2,'Enviado','Reparado','Aceptado','',NULL,1,0,8,2,1500.0000,0.0000,0,'2017-12-21 00:00:00',1,0,1,0,1500.0000),(7,'2017-09-06 00:00:00','2017-09-11 00:00:00','','Tenía el capa de arranque dañado (47 uF * 25v) .','Se denotan fallas en la etapa de alimentación al circuito de disparos y en las fuentes asociadas a la lógica de control.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio.',2,'Enviado','Reparado','Aceptado','',NULL,1,0,9,2,1500.0000,0.0000,0,'2017-12-21 00:00:00',1,0,1,0,1500.0000),(8,'2017-09-13 00:00:00','2017-10-11 00:00:00','','Tiene en corto un integrado de fuente de la placa principal. Es un 1NTC001107. En la placa del display tiene otro el cual se saco y se colocó en la placa dañada y luego se alimentó en +RECT y -UC con un poco más de 60vdc y la fuente arranca. Tengo 12vdc a la salida del cooler. Se revisaron packs de potencia y coolers sin encontrar desperfectos. Se necesita ese integrado. Además tiene mucha tierra.','El equipo presenta fallas en las protecciones contra sobre tensiones, en  la etapa de fuente de arranque y en las alimentaciones asociadas a los circuitos de disparos del pack de potencia. Se denotan componentes dañados debido a un corto circuito en toda una rama de alimentación que afectó a varios drivers de fuente y de control de la lógica. \nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condiciones de laboratorio. \nSe recomienda verificar el estado y los niveles de tensiones presentes en la línea como así también el conexionado y correcto cableado.',2,'Enviado','Reparado','Aceptado','',NULL,1,0,10,6,9500.0000,0.0000,0,'2017-12-21 00:00:00',1,0,1,0,9500.0000),(9,'2017-10-06 00:00:00','2017-10-09 00:00:00','','No enciende. Tiene la fuente aparte y ese es el problema. La misma tiene en corto un diodo de salida SB2100. Tiene dos por lo cual se reemplazaron ambos por BYM36C sacados de scrap. Se mantuvo en prueba por dos días sin presentar falla.','',2,'Enviado','Reparado','Aceptado','',NULL,1,0,11,4,650.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,650.0000),(10,'2017-10-09 00:00:00','2017-10-11 00:00:00','','Tiene varios falsos contactos y además se denota un capa de 10uF * 50v dañado y uno de poliester de 100nf*275 mal. Se reemplazaron componentes. se alimentó con 220vac por  M1 (15 y 16) ... el diagrama es muy similar al de la placa BAXI \"manual_eco280i\", que está en el servidor. Por otro lado se aclara al cliente que esta placa tenia fallas y está reparada, pero que el equipo puede conformarse por más placas que pueden llegar a fallar.','',2,'Enviado','Reparado','Aceptado','',NULL,1,0,12,5,1500.0000,0.0000,0,'2017-12-21 00:00:00',1,0,1,0,1500.0000),(11,'2017-10-19 00:00:00','2017-10-23 00:00:00','','Tiene en corto un UA7824C (24v / 1,5A ) y un LM7812. Además de un par de componenteas más. Se reemplazaron y se probó con alimentación de 24vac y 8vac, verificando correctamente la salida de 5vdc 12vdc y 24vdc. Se probó con la carga de la placa CPU asociada.','El equipo presenta fallas en la etapa de regulación y en la estabilización tanto de 125vdc como de 24vdc. Se denotan componentes dañados en dichas etapas que evidencian un recalentamiento, producido por un consumo excesivo de corriente. \nSu reparación es posible.\nSe reemplazaran los componentes dañados y el equipo será probado en banco bajo condiciones normales de trabajo.',2,'BRC','Reparado','Aceptado','',NULL,1,0,13,7,900.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,1,900.0000),(12,'2017-10-19 00:00:00','2017-10-23 00:00:00','','Tiene dañado dos reles de 24vdc. Unoes solo limpieza, pero el ptro está dañado físicamente, se recalentaron los contáctos y la carcasa. Se revisaron los otros 4 relés sin presentar falla al igual que el resto de los componentes. Se probó con la fuente asociada y encienden varios leds y muestra F5.','Se denotan daños físicos (un recalentamiento ) en varios relés de la etapa de salida y componentes con derivaciones en curvas características en el circuito de disparo de los mismos. \nSu reparación es posible. \nDebido a la falla presente se recominda verificar todo elemento conectado a las borneras correpondientes, en especial a las referenciadas como PS1 PS2 // PC1 PC2 PC3.',2,'BRC','Reparado','Aceptado','',NULL,1,0,14,7,3780.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,3780.0000),(13,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Tiene los switch dañados.','Se denotan daños en todos los swich los cuales deberán ser reemplazados y además se reacondicionará toda la placa realizando también un mantenimiento preventivo.',2,'BRC','No Aceptaron Reparación','NO Aceptado','',NULL,0,0,15,0,0.0000,0.0000,0,'2017-12-21 00:00:00',0,0,0,0,0.0000),(14,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Tiene los switch dañados.','Se denotan daños en todos los swich los cuales deberán ser reemplazados y además se reacondicionará toda la placa realizando también un mantenimiento preventivo.',2,'BRC','No Aceptaron Reparación','NO Aceptado','',NULL,0,0,16,0,0.0000,0.0000,0,'2017-12-21 00:00:00',0,0,0,0,0.0000),(15,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Tiene los switch dañados.','Se denotan daños en todos los swich los cuales deberán ser reemplazados y además se reacondicionará toda la placa realizando también un mantenimiento preventivo',2,'BRC','No Aceptaron Reparación','NO Aceptado','',NULL,0,0,17,0,0.0000,0.0000,0,'2017-12-21 00:00:00',0,0,0,0,0.0000),(16,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Tiene los switch dañados.','Se denotan daños en todos los swich los cuales deberán ser reemplazados y además se reacondicionará toda la placa realizando también un mantenimiento preventivo.',2,'BRC','','','',NULL,0,0,18,0,0.0000,0.0000,0,'2017-12-21 00:00:00',0,0,0,0,0.0000),(17,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Tiene los switch dañados.','Se denotan daños en todos los swich los cuales deberán ser reemplazados y además se reacondicionará toda la placa realizando también un mantenimiento preventivo.',2,'BRC','No Aceptaron Reparación','NO Aceptado','',NULL,0,0,19,0,0.0000,0.0000,0,'2017-12-21 00:00:00',0,0,0,0,0.0000),(18,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Dalkia 35 - Sanatorio de los Arcos','Se denotan daños en todos los swich los cuales deberán ser reemplazados y además se reacondicionará toda la placa realizando también un mantenimiento preventivo.',2,'BRC','No Aceptaron Reparación','NO Aceptado','',NULL,0,0,20,0,0.0000,0.0000,0,'2017-12-21 00:00:00',0,0,0,0,0.0000),(19,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc. Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,0,0,21,0,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(20,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc. Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,0,0,22,0,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(21,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,0,0,23,0,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(22,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,0,0,24,0,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(23,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,0,0,25,0,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(24,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,0,0,26,0,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(25,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,1,0,27,10,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(26,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,1,0,28,10,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(27,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,1,0,29,10,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(28,'2017-10-23 00:00:00','2017-11-07 00:00:00','','Capas de 220uF *16v y de 100uF *50v dañados. Limpieza general. Todas levantan en vacío a 5,3vdc o 5,5vdc y con carga de 0,5A a 5,0vdc y a 8,7vdc en vacío y con carga de 0,5A a 8,4vdc.  Se alimenta con 220vac.','Las fuentes poseen varios componentes dañados en la etapa de regulación de las tensiones de salida y en el circuito de modulación de pulsos. \nSe reemplazarán los componentes afectados, se realizará un mantenimiento preventivo y una limpieza general.',2,'BRC','Reparado','Aceptado','',NULL,1,0,30,10,700.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,700.0000),(29,'2017-10-23 00:00:00','2017-10-30 00:00:00','','Tiene dañado uno de los sensores de luz. Se ca,bió todo el módulo (dos sensores  +  un led rojo ) sacado de la placa de scrap. Se le sacó el pack FS300R12KE3_S1. Luego se probó con 24vdc en el conector X1 y se denota que en todos los disparos tengo -6,7 vdc. Al hacerle incidir luz en el sensor correspondiente, cada uno de los disparos asociados cae a 1,4vdc. Se verificaron todos los canales. Se realizó una limpieza general y un resoldado.','El equipo presenta fallas en la etapa de alimentación a los disparos y en el circuito de detección óptica. Se denotan componentes dañados con derivaciones en curvas características tanto en la etapa de fuentes como en los circuitos de procesamientos de señales hacia los disparos del pack de potencia. \nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condicioones normales de trabajo.\nLas probables causas de este tipo de fallas se deben a presencia de armónicas en la línea o a condicioones ambientales adversas para su buen funcionamiento.\nSe recomienda verificar el estado de la línea de alimentación a la placa, el conexionado y el correcto cableado.',2,'BRC','Reparado','Aceptado','',NULL,1,0,31,10,5040.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,5040.0000),(30,'2017-10-23 00:00:00','2017-10-30 00:00:00','','Se le sacó el pack FS300R12KE3_S1. Luego se probó con 24vdc en el conector X1 y se denota que en todos los disparos tengo -6,7 vdc. Al hacerle incidir luz en el sensor correspondiente, cada uno de los disparos asociados cae a 1,4vdc. Se verificaron todos los canales.Se realizó una limpieza general y un resoldado.','Se denotan fallas en la etapa de alimentación principal y en las fuentes que generan las tensiones asociadas a la lógica de control de disparos. Se observan componentes dañados que provocan riple en dichas tensiones y de esta manera causas fallas aleatorias en el resto de las etapas asociadas.   \nSu reparación es posible.\nSe reemplazarán los componentes dañados y el equipo será probado en banco bajo condicioones normales de trabajo.\nLas probables causas de este tipo de fallas se deben a presencia de armónicas en la línea o a condicioones ambientales adversas para su buen funcionamiento.\nSe recomienda verificar el estado de la línea de alimentación a la placa, el conexionado y el correcto cableado.',2,'BRC','Reparado','Aceptado','',NULL,1,0,32,10,5040.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,5040.0000),(31,'2017-11-14 00:00:00','2017-11-14 00:00:00','','A pedido del cliente por urgencia se le cambió un relé (el correspondiente a lla salida \"puerta\"). El relé se probó y aparentemente funciona OK. Se informa de esto al cliente, pero igualmente pide solo el cambiodel relé.  (el mismo fué proveído por el cliente...de una placa de scrap)','',2,'Enviado','Reparado','Aceptado','',NULL,1,0,33,8,0.0000,0.0000,0,'2017-12-21 00:00:00',0,0,1,0,0.0000),(32,'2017-11-21 00:00:00','2017-11-24 00:00:00','','Al ponerle una tira de leds que consume 500mA en la salida de 700mA no llega a encender. Puede que sea porque sea poca carga para esa salida pero en el otro que es igual funciona OK (enciende bien). Se sigue revisando.\nPara probarlo con la dimerización hay que ponrle pulsos de 24vac en DALI  DA-DA.\nSOLUCIÓN: Era un falso contacto ya que al realizar intercambios de componentes del equipo que funcionaba OK a este, el mismo terminó fucnionando OK al igual que el otro. Se termina de hacer resoldado y se dejan en prueba ambos.','Se denotan componentes recalentados a causa de un sobreconsumo de corriente lo que afectó a la etapa de salida y a la potencia asociada. \nSu reparación es posible.',2,'BRC','Reparado','Aceptado','',NULL,0,0,34,0,360.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,360.0000),(33,'2017-11-21 00:00:00','2017-11-24 00:00:00','','Tiene cortado una pista y un cable desoldado. Se reacondicionó el equipo y ahora funicona OK.\nPara probarlo con la dimerización hay que ponrle pulsos de 24vac en DALI  DA-DA.','Se denotan componentes recalentados a causa de un sobreconsumo de corriente lo que afectó a la etapa de salida y a la potencia asociada. \nSu reparación es posible.',2,'BRC','Reparado','Aceptado','',NULL,0,0,35,0,360.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,360.0000),(34,'2017-11-21 00:00:00','2017-11-24 00:00:00','','Capacitor de 3,3nF dañado.\nPara probarlo con la dimerización hay que ponrle pulsos de 24vac en DALI  DA-DA.','Se denotan componentes recalentados a causa de un sobreconsumo de corriente lo que afectó a la etapa de salida y a la potencia asociada. \nSu reparación es posible.',2,'BRC','Reparado','Aceptado','',NULL,0,0,36,0,360.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,360.0000),(35,'2023-04-23 00:00:00','2017-11-24 00:00:00','','Transistor smd de la etapa de salida dañado.\nPara probarlo con la dimerización hay que ponrle pulsos de 24vac en DALI  DA-DA.','Se denotan componentes recalentados a causa de un sobreconsumo de corriente lo que afectó a la etapa de salida y a la potencia asociada. \nSu reparación es posible.',2,'BRC','Reparado','Aceptado','',NULL,0,0,37,0,360.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,360.0000),(36,'2017-11-21 00:00:00','2017-11-24 00:00:00','','Estaba muy dañado y con el micro en corto. Se reparó otro de scrap y se reemplazó.\nPara probarlo con la dimerización hay que ponrle pulsos de 24vac en DALI  DA-DA.','Se denotan componentes recalentados a causa de un sobreconsumo de corriente lo que afectó a la etapa de salida y a la potencia asociada. \nSu reparación es posible.',2,'BRC','Reparado','Aceptado','',NULL,0,0,38,0,360.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,360.0000),(37,'2017-11-21 00:00:00','2017-11-24 00:00:00','','Estaba muy dañado y con el micro en corto. Se reparó otro de scrap y se reemplazó.\nPara probarlo con la dimerización hay que ponrle pulsos de 24vac en DALI  DA-DA.','',2,'BRC','Reparado','Aceptado','',NULL,0,0,39,0,360.0000,0.0000,0,'2017-12-21 00:00:00',1,0,0,0,360.0000),(38,'2017-11-21 00:00:00','2017-11-24 00:00:00','','Estaba muy dañado y con el micro en corto. Se reparó otro de scrap y se reemplazó.\nPara probarlo con la dimerización hay que ponrle pulsos de 24vac en DALI  DA-DA.','Se denotan componentes recalentados a causa de un sobreconsumo de corriente lo que afectó a la etapa de salida y a la potencia asociada. \nSu reparación es posible.',2,'BRC','Reparado','Aceptado','',NULL,0,0,40,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(39,'2017-11-23 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,41,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(40,'2017-11-23 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,42,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(41,'2023-04-23 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,43,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(42,'2017-12-27 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,44,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(43,'2017-12-27 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,45,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(44,'2017-12-27 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,46,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(45,'2017-12-27 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,47,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(46,'2017-12-27 00:00:00',NULL,'Falla 5V',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,48,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(47,'2017-12-29 00:00:00',NULL,'No especifica\nELS ant.: 15557',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,49,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(48,'2017-12-29 00:00:00',NULL,'ELS ANTERIOR: 14952.\nNo funciona pero no se acuerda si por lo menos prende o no.',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,50,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000),(49,'2017-12-29 00:00:00',NULL,'',NULL,NULL,1,'BRC','',NULL,'',NULL,0,0,51,0,0.0000,0.0000,0,NULL,0,0,0,0,0.0000);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Default'),(2,'Administrador Programador'),(3,'Tecnico'),(4,'Contable'),(5,'Tecnico Contable');
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
  KEY `idCliente` (`idCliente`),
  CONSTRAINT `sucursal_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sucursal`
--

LOCK TABLES `sucursal` WRITE;
/*!40000 ALTER TABLE `sucursal` DISABLE KEYS */;
INSERT INTO `sucursal` VALUES (0,'',1,NULL,NULL,NULL,NULL),(1,'',2,NULL,NULL,NULL,NULL),(2,'',3,NULL,NULL,NULL,NULL),(3,'',4,'','','',''),(4,'',5,'','','',''),(5,'',6,'','','',''),(6,'',7,'','','',''),(7,'',8,'','','',''),(8,'',9,'','','',''),(9,'',10,'','','',''),(10,'',11,'','','',''),(11,'',12,'','','',''),(12,'',13,'','','',''),(13,'',14,'','','',''),(14,'',15,'','','',''),(15,'',16,'','','',''),(16,'',17,'','','',''),(17,'',18,'','','',''),(18,'BRC',19,'Onelli 1216 2do 5to','Diego Bertossi','5491137688372','diego.bertossi@elsweb.com.ar'),(19,'MDP',19,'','','',''),(20,'CABA',19,'','','',''),(21,'',20,'','','',''),(22,'',21,'','','',''),(23,'',22,'','','',''),(24,'',23,'','','',''),(25,'',24,'','','',''),(26,'',25,'','','',''),(27,'',26,'','','',''),(28,'',27,'','','',''),(29,'',28,'','','',''),(30,'',29,'','','',''),(31,'',30,'','','',''),(32,'',31,'','','',''),(33,'',32,'','','',''),(34,'',33,'','','',''),(35,'',34,'','','',''),(36,'',35,'','','',''),(37,'',36,'','','',''),(38,'',37,'','','',''),(39,'',38,'','','',''),(40,'',39,'','','',''),(41,'',40,'','','',''),(42,'',41,'','','',''),(43,'',42,'','','',''),(44,'',43,'','','',''),(45,'',44,'','','',''),(46,'',45,'','','',''),(47,'',46,'','','',''),(48,'',47,'','','','');
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
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
  KEY `idRol` (`idRol`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,0,'','','','','',''),(2,2,30925503,'Diego','Bertossi','1137688372','diego.bertossi@elsweb.com.ar','diego','1234'),(3,3,30925501,'Juan','Perez','1137688372','diego.bertossi@elsweb.com.ar','juan','0000'),(4,4,30925505,'Pedro','Zerez','1137688372','diego.bertossi@elsweb.com.ar','pedro','0000');
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

-- Dump completed on 2023-04-23 16:01:32
