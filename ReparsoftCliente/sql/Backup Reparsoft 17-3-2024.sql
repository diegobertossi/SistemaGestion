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
INSERT INTO `cliente` VALUES (1,'Ascensores Lucero','30706433585','Las Violetas 1215, San Carlos de Bariloche, RÃ­o Negro','542944425706','Ricardo Lucero','2944602590','luceroasc@speedy.com.ar'),(2,'Felipe',NULL,'Personal','0',NULL,'2944685047',NULL),(3,'Total Clima','30708448202','Elordi 368 (8400), San Carlos de Bariloche. Rio Negro, Argentina','2944431070','HÃ©ctor Spirito','2944487014','info@totalclimaonline.com.ar'),(4,'Mariel Celio','','','','','',''),(5,'Julio (Instalador Convertec)','','','','','',''),(6,'Daniel','','','','','',''),(7,'Privatel','30615757078','Rivadavia 571, San Carlos De Bariloche (8400), Rio Negro, Argentina','','Lisandro','5492944394682',''),(8,'Stec Climatización ','','','','Gonzalo Albarracín  Vranken','2944907634','stec.climatizacion@gmail.com'),(9,'ELS','','','','Sergio Fernández','','els@elsweb.com.ar'),(10,'Huilque SRL','30660795568','AVENIDA CARLOS BUSTOS 329 / PARAJE: CERRO CATEDRAL Código postal: 8400 RIO NEGRO','','Santiago Lema','2944921455','santiagol@huilque.com'),(11,'Ascensores Bariloche','','','','Claudio','2944801351','barilocheascensores@gmail.com'),(12,'Andrés Gallardo (X28)','','','','','',''),(13,'Asociación Club Los Pehuenes','30632773354','Pintores Argentinos 250','','Leandro','2944269793',''),(14,'Distribuidora Patagonia','','','','Luciano Zeizz','2944412526','lzeiss@yahoo.com.ar'),(15,'IPATEC','30715328433','Av De Los Pioneros 2350, (CP: 8400), San Carlos de Bariloche, Río Negro, Argentina.','','Nicolás','5492944554495','contacto.ipatec@comahue-conicet.gob.ar'),(16,'Hugo','','','','','',''),(17,'Jose Luis (Privatel)','','','','','',''),(18,'Ariel Lotito','','','','','',''),(19,'Alma Del Lago','','Av. Bustillo km 1,151','','','','abastecimiento@almasuites.com.ar'),(20,'Leandro Slosel','','','','','',''),(21,'Mamuschka SRL','30708107758','Mitre 298, San Carlos De Bariloche (8400), Rio Negro, Argentina','2944426585','Daniel Berón','291544556611','proveedores@mamuschka.com'),(22,'Abuelo Ian','','','','','',''),(23,'Nicolás (Placas Lav/Sec ropas)','','','','','',''),(24,'QUASAR INFORMATICA SA','30710358938','Emilio Frey 568, San Carlos de Bariloche','','Maximiliano','5492944803711','');
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
INSERT INTO `equipos` VALUES (1,'Programador de Riego','AT 2000','Gadnic','R70Z5',NULL,'','','',4,3),(2,'Programador de Riego','9001D-C','Galcon','GM2529518',NULL,'','','',4,3),(3,'Programador de Riego','9001BT','Galcon','NYMQG',NULL,'','','',4,3),(4,'Central de arranque y transferencia automática','DKG-207','Datakom','2602070172960',NULL,'','','',5,4),(5,'Placa de caldera','Slim ( LMU33.201E149)','Baxi (Siemens)','9777',NULL,'','','',6,5),(6,'Placa Fuente de ascensor','A6210','Automac','A7422A',NULL,'','','',1,0),(7,'Placa de caldera','E312264 (TOP digital Sa26F)','Caldaia','65E7Q',NULL,'','','',6,5),(8,'Cargador de baterías( 220vac - 12V/50A)','CB-FL-12/750-50-D','HT SA','JOG9S',NULL,'','','',7,6),(9,'Placa de caldera','Genius M30 (11600011)','Caldaia','PV6YG',NULL,'','','',8,7),(10,'Inverter 48vdc / 220vac - 5000W','INVERTER5KW','Enertik','92932011101828',NULL,'','','',5,4),(11,'Central de arranque y transferencia automática','DKG-207','Datakom','2602070124518',NULL,'','','',5,4),(12,'Micro-Ohmmetro','MPK-253','Megabras','OA2098J',NULL,'','','',9,9),(13,'Microondas + Grill + Convector','MD11711','Medion','0076/08',NULL,'','','',7,6),(14,'Variador de velocidad 30 HP','ATV58HD33N4','Telemecanique','6W0335000005B',NULL,'','','',1,0),(15,'Horno Convector trifásico 6,25KW','Beta 107I','Pauna','4580',NULL,'','','',10,11),(16,'Operador de Puerta','VVVF4+','Fermator','11/42739',NULL,'','','',11,12),(17,'Teclado de música','CTK-230','Casio','6369621',NULL,'','','',5,4),(18,'DVR 8CH','DS-7208HGHI-SHSE','HIKVISION','496010329',NULL,'','','',12,13),(19,'Placa de caldera','Laura 35/35TH','BaxiRoca','42,062,03',NULL,'','','',6,5),(20,'Placa de caldera','Laura 35/35TH','BaxiRoca','42,062,02',NULL,'','','',6,5),(21,'Detector de Huella','K20-ID-SSR-V2','ZKTECO','3157153200136',NULL,'','','',13,14),(22,'Notebook','NP300E5C','Samsung','BA68-08754A',NULL,'5850','','',14,15),(23,'Variador de velocidad 10HP','ATV31HU75N4','Telemecanique','8B0931411132',NULL,'','','',1,0),(24,'Freidora Industrial 8L','8L','Ind. Arg','IAD5Y',NULL,'','','',10,11),(25,'Freidora Industrial 8L','8L','Ind. Arg.','E1N6T',NULL,'','','',10,11),(26,'Impresora','DCP-T300','Brother','KQSOI',NULL,'','','',14,15),(27,'Sistema de elaboración de cerveza eléctrico','008695','Robobrew','9337310008695',NULL,'','','',15,16),(28,'Soldadora inverter 200A','LHN 240i Plus','ESAB','R9KTD',NULL,'','','',16,17),(29,'TV LCD 32','LN32B530P7R','Samsung','AZXT3CPS600005W',NULL,'','','',17,18),(30,'Placa de caldera','AMPC-GAL1 MCU STD MED FF','Ariston','1709066A0178',NULL,'','','',18,19),(31,'Impresora Laser','HL-1212W','Brother','U63982F8N692142',NULL,'5942','','',14,15),(32,'Placa de ascensor','A6220v4','Automac','199221020',NULL,'','','',19,20),(33,'Sistema de control de Jacuzzi','GL2001M3','Balboa','55216-031106160001',NULL,'','','',20,21),(34,'Inverter 12vdc / 220vac - 600W','IE-600-12','Enertik','E05Q026',NULL,'','','',5,4),(35,'Cargador de baterías','DC18RA','Makita','V3C1L',NULL,'','','',5,4),(36,'Placa de unidad exterior multisplit','INV20Y-BOARD KE76B5INV20Y-BOARD KE76B564G0264G02','Mitsubishi','160112B',NULL,'','','',21,22),(37,'Motor de portón eléctrico','PCI 0106/2','Central Tem','K371U',NULL,'','','',22,23),(38,'Placa de Lavarropas Next Eco','772204100','Drean','3009162',NULL,'','','',23,24),(39,'Placa de Secarropas ASL70C','LT C126','Ariston','0639',NULL,'','','',23,24),(40,'Baby monitor','scd501','Philips','GC2TK',NULL,'','','',23,24),(41,'Multifuncion Laser','AL-2020','Sharp','85051196',NULL,'','','',14,15),(42,'Cargador Automático Inteligente 12V 15A','IC-15-12','Enertik','1L129',NULL,'','','',24,25),(43,'Cargador de Baterias 220VAC  - 48V/20A','M4820','Melex','3052',NULL,'','','',9,9);
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
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reemplazos`
--

LOCK TABLES `reemplazos` WRITE;
/*!40000 ALTER TABLE `reemplazos` DISABLE KEYS */;
INSERT INTO `reemplazos` VALUES (1,999,'','6B (TR smd)','6B (TR smd)',''),(2,999,'','TIP31C','TIP41C',''),(3,999,'','TIP115','TIP127',''),(4,1010,'','HCNW1126','HCNW1126',''),(5,1017,'C34/C38','100 * 25','100 * 35',''),(6,1017,'C29','100 * 50','100 * 50',''),(7,1026,'RV3','VARISTOR 10mm 275V','VARISTOR 10mm 275V',''),(8,1026,'RV1','VARISTOR 7mm 275V','VARISTOR 7mm 275V',''),(9,1026,'C4','10uF * 450V','10uF * 450V',''),(10,1025,'','s10k300','s10k300',''),(11,1025,'','LNK564N','LNK564N',''),(12,1025,'C21','10uF*400v','10uF*400v',''),(13,1024,'','JST08A600BW','BTB12-600','');
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
INSERT INTO `remitos` VALUES (0,NULL,6),(1,669,5),(2,668,5),(3,670,5),(4,671,5),(5,672,5),(6,673,5),(7,674,5),(8,675,5),(9,676,5),(10,677,5),(11,678,5),(12,679,5),(13,680,5),(14,681,5),(15,682,5),(16,683,5),(17,684,5),(18,685,5),(19,686,5),(20,687,5),(21,688,5),(22,689,5),(23,690,5),(24,691,5),(25,692,5),(26,693,5);
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
INSERT INTO `reparaciones` VALUES (988,'2024-01-11 00:00:00','2024-01-16 00:00:00','','Enciente pero no responde OK a los pulsadores. Tienen mucha resistencia, son touch, se deben limpiar.','Se denota que el equipo posee componentes dañados en la etapa de recepción de señales provenientes de la botonera. Varios elementos de sensado y procesamiento de datos estan defectuosos y los mismos generan fallas aleatorias que perjudican la funcionalidad integral del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes afectados y luego el programador será probado bajo condiciones normales de trabajo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,1,1,1,21500.0000,21.0000,0,'2024-01-16 00:00:00',1,0,0,0,0,0,21500.0000),(989,'2024-01-11 00:00:00','2024-01-16 00:00:00','Le entró agua','Tiene la placa con pistas corroídas y componentes afectados por lo mismo. \nSe debe realizar limpieza total y verificar luego el funcionamiento.','El equipo posee corrosión en toda la placa electrónica y en los componentes. Se denotan elementos dañados por la acción corrosiba del agua y además hay varias pistas que están cortadas. Se observa además, que el microprocesador que controla toda la lógica también fue afectado.\nSe necesitará realizar una limpieza total del equipo más la reparación y reemplazo de componentes dañados para verificar el buen funcionamiento del mismo, ya que por los daños en la placa, el microprocesador fue afectado y hay que realizar todo el trabajo para luego verificar el encendido y posterior prueba del equipo. \nEn caso de que el microprocesador esté dañado y en consecuencia el equipo no pueda ser reparado, los trabajos realizados no tendrán costo alguno.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,1,2,1,49500.0000,49.0000,0,'2024-01-16 00:00:00',1,0,0,0,0,0,49500.0000),(990,'2024-01-11 00:00:00','2024-01-16 00:00:00','Le entró Agua','Tiene la placa con pistas corroídas y componentes afectados por lo mismo. \nSe debe realizar limpieza total y verificar luego el funcionamiento.','El equipo posee corrosión en toda la placa electrónica y en los componentes. Se denotan elementos dañados por la acción corrosiba del agua y además hay varias pistas que están cortadas. Se observa además, que el microprocesador que controla toda la lógica también fue afectado.\nSe necesitará realizar una limpieza total del equipo más la reparación y reemplazo de componentes dañados para verificar el buen funcionamiento del mismo, ya que por los daños en la placa, el microprocesador fue afectado y hay que realizar todo el trabajo para luego verificar el encendido y posterior prueba del equipo. \nEn caso de que el microprocesador esté dañado y en consecuencia el equipo no pueda ser reparado, los trabajos realizados no tendrán costo alguno.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,1,3,1,49500.0000,49.0000,0,'2024-01-16 00:00:00',1,0,0,0,1,0,49500.0000),(991,'2024-01-23 00:00:00','2024-01-12 00:00:00','Queda con la alarma de AUXILIAR encendida y no hace transferencia','El equipo está lleno de insectos. Se limpia y luego se prueba y se denota que hay un grupo de parámentros que controla la entrada y salida de SPARE == AUXILIAR (parametros de 77 al 82) en donde el parámetro 80 estba en 1 (lo cual es normal cerrado , por eso quedaba cerrado. De fábrica está en 0. \nparametros del cliente:\n77=0 - 78=0 - 79=0 - 80=1  - 81=1 - 82=1\nparametros de fábrica:\n77=2 - 78=0 - 79=0 - 80=0  - 81=0 - 82=0\n\nse modificó a los de fábrica y se teste+o funcionamiento.','Se denota que el interior del equipo posee tierra e insectos que generaron corto circuitos en varias zonas de la lógica de control y de las salidas a relé. Se observan componentes dañados en la recepción de señales provenientes de las borneras, y en consecuencia el procesamiento de las mismas es erróneo lo que generan fallas aleatorias.\nSu reparación es posible.\nSe realizará un mantenimiento y limpieza general a todo el equipo, se reemplazarán los componentes dañados y luego se realizarán pruebas de todas las funcionalidades, verificando sensado de tensiones y transferencia de línea a generador.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,4,6,156500.0000,185.0000,0,'2024-01-23 00:00:00',1,1,0,0,0,0,156500.0000),(992,'2024-01-15 00:00:00','2024-01-15 00:00:00','','Se realiza conexionado (en carpeta y en server), y se denota que para que funciona OK y detecte llama debe estar conectada la tierra en  F1-F3- ANAFE -TIERRA DE LINEA. \nCon eso detecta llama OK. Se prueba y funciona OK. \nSe denota que dos de los relés estaban con algo de resistencia K4 y K2 pero no tienen que ver con la detección de llama ni la valvula de gas. \nSe informa como reparación aclarando que se revise tierra.','Se denotan fallas en la etapa de sensado de llama y en los circuitos que se encargan de procesar las señales de dicha etapa. Varios componentes se encuentran dañados a causa posiblemente de sobretensiones que ingresaron por el pin de sensado F2. \nSu reparación es posible.\nSe reemplazarán los componentes afectados y luego el equipo será probado en banco, bajo condiciones normales de trabajo.\nLas probables causas de este tipo de fallas pueden deberse a posibles desperfectos en el cableado, como la falta de tierra en la placa, ya que esta se usa como referencia y al estar posiblemente cortada, la tensión en dicho sector puede ser muy elevada. \nSe recomienda verificar el cableado y el conexionado del equipo para evitar futuros inconvenientes.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,5,2,140500.0000,160.0000,0,'2024-01-16 00:00:00',1,0,0,0,0,0,140500.0000),(993,'2024-01-15 00:00:00','2024-01-16 00:00:00','','Tiene la pista de tierra que va al conector W4-44 con una explsión y levantada. Además los tres reguladores dañados y los capas de salida C12 y C13.\nAdemás se observa que el puente JP1 está abierto, probablemente por la entrada de tensión por  W4-44. Se rehizo el puente (en otras placas estaba cerrado). Con eso la salida de 12VDC queda habilitada con la misma alimentación de 8vac y 24vac.','Se denota una explosión en las cercanías de la bornera W4-44. Debido a un exceso de tensión, se vieron afectados todos los reguladores, los filtros de salida y además varios componentes de rectificación.\nSu reparación es posible.\nSe reacondicionará el circuito impreso, se reemplazarán los componentes dañados y luego se realizarán pruebas funcionales tanto en vacío como con carga a las salidas de 5vdc 12vdc y 24vdc, verificando tensiones y corrientes entregadas. \nLas probables causas de este tipo de fallas pueden deberse a un ingreso de tensión indebida en la bornera W4-44 o también al agotamiento normal de los componentes. \nSe recomienda verificar todo elemento externo asociado a la placa fuente de referencia, como así también al cableado y al correcto conexionado del mismo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,6,3,45800.0000,45.0000,0,'2024-01-16 00:00:00',1,0,0,0,0,0,45800.0000),(994,'2024-01-16 00:00:00','2024-01-16 00:00:00','E26','Tiene dañado el relé RL3. Se reemplazó por común cuadrado. El resto de los relés ok. Además se cambio capa C44 por las dudas.','El equipo presenta fallas en la etapa asociada a la salida del ventilador, de la válvula de gas y en las fuentes de alimentación asociadas a ese circuito. Se denotan componentes dañado que perjudican la funcionalidad integral del equipo.\nSu reparación es posible.\nSe reemplaarán los componentes dañados y se probarán las funcionalidades en banco y bajo condiciones normales de trabajo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,7,5,112500.0000,115.0000,0,'2024-01-20 00:00:00',1,0,0,0,0,0,112500.0000),(995,'2024-01-17 00:00:00','2024-01-17 00:00:00','','Ingresó con algunos cables del puente rectificador sueltos. \nTiene agotados todos los capas de la fuente de arranque y de lógica.\nSe reemplazarón y se checkearon los relés (todos OK), luego se probó en vacío y con carga de batería, verificando la correcta carga de la misma.','Se observa que el equipo posee cables desconectados. Se realizará un análisis de los circuitos para verificar el correcto conexionado y  además, luego de proceder con la revisión del mismo, se denota que presenta fallas en la etapa de habilitación de las seguridades de entrada, en las fuentes de arranque, en el circuito de disparos asociado y en la potencia del mismo. Se observan componentes defectuosos y fuera de valor en los circuitos mencionados, que generan fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible. \nSe reemplazarán los componentes defectuosos y luego será probado en banco, bajo condiciones normales de trabajo, verificando las tensiones y corrientes entregadas, tanto en vacío como en funcionamiento normal de carga.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,8,15,256050.0000,300.0000,0,'2024-02-16 00:00:00',1,0,0,0,0,0,256050.0000),(996,'2024-01-23 00:00:00','2024-01-17 00:00:00','','Reparada de Stock','',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,9,4,110000.0000,129.0000,0,'2024-02-02 00:00:00',1,0,0,0,0,0,110000.0000),(997,'2024-01-18 00:00:00','2024-01-18 00:00:00','','Tiene los capas de la fuente de carga de baterías dañados. Se reemplazaron, se probó y funciona OK.','El equipo presenta fallas en la etapa de potencia, en el circuito de disparos asociados y en las fuentes secundarias que alimentan a la lógica de control. Se denotan componentes dañados en las etapas mencionadas, como son los transistores de potencia, los drivers de control de los mismos y elementos de filtrado de línea y estabilización de tensiones del circuito cargador de baterías.\nSu reparación es posible. \nSe reemplazarán los componentes afectados, se reacondicionará el circuito impreso y se realizará una limpieza y mantenimiento general del equipo. Luego, será probado en banco con  tensión de red, de batería y de las celdas fotovoltaicas. Se realizarán chequeos modulares de las alimentaciones correspondientes y luego de la salida de 220 VAC.\nLas probables causas de este tipo de fallas pueden deberse a un sobreconsumo en la etapa de salida o al normal desgaste de los componentes.\nSe recomienda verificar el conexionado y el correcto cableado del equipo, como así también a todo elemento conectado a la salida.',2,'Diego Bertossi','BRC','Reparado','A la Espera de Aceptación','','',0,0,10,0,571880.0000,680.0000,0,NULL,1,0,0,0,0,0,0.0000),(998,'2024-01-18 00:00:00','2024-01-18 00:00:00','','No leebien la tensión en R, la de S y la de T si están OK.\nTiene falsos contactos en las resistencias de sensado del canal R. Se realizó resoldado de dicho sector, luego se probó, verificando funcionamiento y mediciones OK en las tres fases.','Se denóta que el equipo no reliza correctamente las mediciones de tensión en la fase \"R\". Esto es debido a fallas en todo el circuito de sensado y a la etapa de recepción de señales, la cual posee componentes defectuosos, con derivaciones en curvas características que perjudican la funcionalidad integral del equipo.\nSu reparación es posible.\nSe realizará un mantenimiento y limpieza general a todo el equipo, se reemplazarán los componentes dañados y luego se realizarán pruebas de todas las funcionalidades, verificando sensado de tensiones y transferencia de línea a generador.',2,'Diego Bertossi','BRC','Reparado','A la Espera de Aceptación','','',0,0,11,0,143500.0000,171.0000,0,NULL,1,0,0,0,0,0,0.0000),(999,'2024-01-19 00:00:00','2024-01-19 00:00:00','No mide en las escalas de 1mA/10mS/100mA/1A','Se prueba con alambres de diferentes longitudes para verificar mediciones y se denota que efectivamente no mide en esas escalas, pero si realiza medicione en 10A.\nSe denota que hay una placa que controla el disparo de corrientes en las escalas que no funciona. dicha placa posee un circuito de disparos y 3 relés, uno de esos disparos posee un transistor smd 6B en corto. Además fuera de esa plaquita hay dos transistores de potencia TO220 tambien en corto: TIP31C, reemplazado por TIP41C, y un TIP115, reemplazado por un TIP127.\nLuego de reemplazar esos componentes, se prueba de nuevo y ahora si mide OK en todas las escalas.','El equipo presenta fallas en las etapas que controlan las salidas de corrientes de 1mA/10mA/100mA y 1A. Se denotan componentes dañados en los circuito de disparos asociados y en las etapas de potencia de cada canal, lo que perjudica la funcionalidad integral del mismo.\nSu reparación es posible.\nSe reemplazarán los componentes defectuosos y luego el equipo será probado en banco, bajo condiciones normales de trabajo, verificando las corrientes entregadas en todas las escalas y luego los valores de las mediciones tomadas.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',0,0,12,0,952807.0000,1100.0000,0,'2024-02-01 00:00:00',1,0,0,0,0,0,952807.0000),(1000,'2024-01-19 00:00:00','2024-01-23 00:00:00','','No hay tensión en algunos puntos. No llegan los 220vac . El problema esta en los switch de las puertas, no llegan a presionarse. Se limpiaron por las dudas y se puso suplemento, además se limpiaron los relés de salida. Se prueba y funciona OK.','Se denota que el equipo posee fallas en la etapa de fuente, en los disparos de las salidas a relé y en los propios actuadores. Se observan componentes dañados que perjudican la funcionalidad total del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados, se realizará un mantenimiento preventivo y una limpieza general  y luego el equipo será probado en banco, verificando todas las funcionalidades.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,13,7,108500.0000,129.0000,0,'2024-01-23 00:00:00',1,0,0,0,0,0,108500.0000),(1001,'2024-01-25 00:00:00','2024-01-26 00:00:00','No enciende','El equipo no enciende. Se pone en banco, se energiza con 220 monofásico y debería levantar , pero no lo hace y no tengo la tensión del circuito intermedio.\nSe verifica fuente superior, la que tiene el 1NTC001107 y se denota que al alimentar con la rectificada de 220vac en conector correspondiente y además ponerle los 12vdc en el pin del medio, la lógica levanta OK y enciende panel. Por lo cual esa fuente está OK.\nPacks ok (se desarmó y se midieron afuera ). El problema está en un capa que estaba totalmente agotado (es el que está debajo de la referencia C234... es de 1000uF * 25V). y además en el 1NTC001107 que está en esa misma placa, la inferior. Mide entre pata 3 y 14 el diodo chanfleado. se cambia por el de la fuente superiior, luego se prueba sinb dicha lógica, es decír sin la fuente/panel superior , y ahora si arranca la etapa de rectificación y tengo las tensiones del circuito intermedio OK, arrancan los ventiladores OK.','El equipo presenta fallas en la etapa de alimentación principal, en las fuentes de baja tensión y en la potencia de entrada que controla la rectificación de los 380 VAC. Se denotan varios componentes dañados, con derivaciones en las curvas características que perjudican la funcionalidad integral del mismo, incluyendo a los packs  semicontrolados de potencia y a la lógica asociada.\nSu reparación es posible.\nSe reemplazarán los componentes afectados y se realizará un mantenimiento general, para luego probar al equipo en banco bajo condiciones normales de trabajo. \nLas probables causas de este tipo de fallas pueden deberse a presencia de armónicas en la línea o al normal desgaste de los componentes.\nSe recomienda verificar el estado y los niveles de tensiones presentes en la línea como así también el conexionado y el correcto cableado.\nDebido a la tardanza en la recepción de los repuestos requeridos que deben de ser importados, el plazo de entrega será de entre 40 y 60 días a partir de la aceptación del presente informe.',2,'Diego Bertossi','Enviado','No Aceptaron Reparación','NO Aceptado','','',0,0,14,0,1052812.0000,1250.0000,0,'2024-02-06 00:00:00',1,0,0,0,0,0,0.0000),(1002,'2024-01-26 00:00:00','2024-02-15 00:00:00','No corta nunca la temperatura.','Se energiza con 380VAC + neutro (conexionado interno en carpeta). Se denota que no corta el termoestato. Se revisa afuera y efectivamente no corta. Se abre y se destraba, pero no queda bien, ya que hay que dejarlo medio abierto para que funcione OK. Se prueba y ahora corta casi OK, ya que no coincide la temp seteada con la medida, por lo cual se necesita cambiar el mismo.\nEn ML original 26/01/24:  el mas caro de ML: $ 160.200\nhttps://articulo.mercadolibre.com.ar/MLA-1510132744-termostato-de-temperatura-horno-pauna-original--_JM#position=6&search_layout=grid&type=item&tracking_id=d5544c9b-e2d0-4f77-a0d8-19144c47ac49\n\nEl más barato $75.000 :\nhttps://articulo.mercadolibre.com.ar/MLA-1588681028-termostato-horno-pauna-beta-21-y-107-_JM#position=5&search_layout=grid&type=item&tracking_id=d5544c9b-e2d0-4f77-a0d8-19144c47ac49','El equipo posee dañada la etapa de regulación y control de temperatura, como así también se denotan defectuosos varios elementos de potencia, como son los contactores y seguridades del circuito de disparo de las resistencias calefactoras.\nSu reparación es posible.\nSe reemplazarán los componentes afectados, se realizará un mantenimiento y limpieza general y luego el horno será probado bajo condiciones normales de trabajo, midiendo las temperaturas y los tiempos de corte y activación.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,15,16,285000.0000,336.0000,0,'2024-02-06 00:00:00',1,0,0,0,0,0,285000.0000),(1003,'2024-01-25 00:00:00','2024-01-26 00:00:00','','El equipo tiene totalmente abierto el capa del circuito intermedio. 220uF * 400V. Se reemplazó por uno igual de otro equipo de scrap, se revisaron demás componentes sin encontrar defectuosos. Seprueba y funciona OK.','El equipo presenta fallas en la etapa de alimentación, en la rectificación y estabilización del circuito intermedio, en la protección contra sobre tensiones de la entrada y en las fuentes de arranque. Se denotan componentes dañados en dichas etapas que generan ruido en las alimentaciones correspondientes, provocando un mal funcionamiento general del equipo. \nSu reparación es posible.\nSe denotan fallas en varios componentes asociados a la fuente interna que genera las bajas tensiones para la alimentación de la lógica. Se observa que tales componentes presentan signos de debilitamiento, causados probablemente por picos de tensiones en la línea o por la presencia de armónicos en la misma. \nSu reparación es posible. \nSe reemplazarán los componentes defectuosos y se realizará un mantenimiento general del equipo, para luego someterlo a pruebas en banco, bajo condiciones normales de trabajo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,16,9,105250.0000,125.0000,0,'2024-01-26 00:00:00',1,0,0,0,0,0,105250.0000),(1004,'2024-01-26 00:00:00','2024-01-31 00:00:00','No enciende.','Tiene la ficha de la fuente al revez. El positivo debe ir del lado de afuera, el negativo es el pin interno. Se alimentó correctamente y enciende OK. Se denota que algunas teclas no funcionan ok, debido a suciadad, no hacen buen contacto, es necesario limpieza.','Se observa que la fuente interna del equipo tiene componentes dañados debido al ingreso de tensión inversa en polaridad. Varios elementos de regulación y estabilización de tensiones fueron afectados. Además se denota que hay varias teclas que no funcionan correctamente. \nSu reparación es posible.\nSe reparará el circuito de alimentación, reemplazando los componentes defectuosos, se corregirán los desperfectos de las teclas y luego se realizará un mantenimiento y limpieza general.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,17,8,84500.0000,100.0000,0,'2024-01-27 00:00:00',1,0,0,0,0,0,84500.0000),(1005,'2024-01-30 00:00:00','2024-02-01 00:00:00','No enciende.','Tiene varios capas hinchados. No prende. Se reemplazaron los mismos y luego se probó ( ojo, con monitor 17 no llega con la definición, se prueba con tv y HDMI),.','El equipo presenta fallas en la etapa de alimentación principal, en la fuente de arranque y en la lógica de regulación que estabiliza las tensiones a los integrados de control. Se denotan componentes agotados que perjudican la funcionalidad integral del mismo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados, se realizará un mantenimiento preventivo y luego se probará el encendido y  la correcta transmisión y recepción de video.',2,'Diego Bertossi','BRC','Reparado','Aceptado','','',1,0,18,12,56800.0000,67.0000,0,'2024-02-01 00:00:00',1,0,0,0,0,0,56800.0000),(1006,'2024-02-01 00:00:00','2024-02-01 00:00:00','No dispara chispa','Tiene dañado en principio el capa de arranque 10uF * 50v. Por el problema de la chispa, el tema viene asociado al capa de 1uF del circuito de disparo de chispa. Además tiene agotados los capas de las salidas a relé C138/C139 = 100uF * 275v y el c140 2,2 nF * 275v. En ese mismo circuito se encontró resistencia R187 de 47 ohm abierta. Se revisaron los relés y están todos OK. Le falta fusible de 3,15A','El equipo posee fallas en la etapa de disparo de chispa, en el sensado de la misma, en las fuentes de baja tensión que alimentan a dicha lógica de control y en el circuito de salidas a relé. Se denotan componentes dañados, que no respetan las curvas características de los miismos.\nSu reparación es posible.\nSe reemplazarán los componentes dañados y luego la placa será probada en laboratorio bajo condiciones normales de trabajo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,19,10,147500.0000,175.0000,0,'2024-02-05 00:00:00',1,0,0,0,0,0,147500.0000),(1007,'2024-02-01 00:00:00',NULL,'No funciona en calefacción','','',1,' ','BRC','Sin revisar','A la Espera de Aceptación','',NULL,0,0,20,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(1008,'2024-02-02 00:00:00','2024-02-16 00:00:00','Falla teclado, bateria mal y pin de carga fuente externa mal.','Tiene mal la batería. 505060AR 2000mA / 3,7V \n02/02/24 - $41.786 + $ 5.000 en ML = 47000\nhttps://articulo.mercadolibre.com.ar/MLA-1140081178-bateria-505060-37v-2000mah-_JM#position=27&search_layout=stack&type=item&tracking_id=e8fbcd3d-4290-4d7b-97af-5a7d741a78ba\nAdemás tiene alguna resistencia en los contáctos de los pulsadores de la botonera, y hay que remplazar el pin de carga de la fuente externa.','Se denota que el equipo presenta fallas en la lógica que controla a la botonera frontal. Se observan componentes agotados en el circuito que maneja las pulsaciones en los botones, en especial la etapa asociada al botón \" M/OK \". Además, la batería está muy hinchada y es necesario su reemplazo.\nSu reparación es posible.\nSe reemplazarán los componentes afectados, se repondrá la batería y además, se proveerá de un cable a pedido, con el conector correspondiente al pin de carga del equipo en un extremo y del otro se dejará libre para su empalme en el lugar del montaje.\nDebido a la tardanza en los repuestos necesarios, el plazo de entrega será de entre 15 y 20 días a partir de la aceptación del presente presupuesto.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,21,14,167500.0000,197.5000,0,'2024-02-06 00:00:00',1,0,0,0,0,0,167500.0000),(1009,'2024-02-07 00:00:00','2024-02-07 00:00:00','Mal pin de carga','Además del pin de carga (fue comprado en ML: https://www.partesyrepuestos.com.ar/MLA-787437555-jack-o-pin-de-carga-notebook-samsung-np300-np550-_JM)  2 unidades + envío  = $12500. tiene falsos contactos en la pista positiva que sale del conector. \nSe reemplazó conector y se reacondicionó circuito impreso. Se prueba, funciona OK, pero el disco no tiene SO. Se prueba con otro rígido con SO y funciona OK. El Disco original se envía al cliente para que le ponga SO. La notebook se entrega Sin Disco.','Se denota que el equipo posee dañado el pin de carga y además posee fallas en la continuidad de las pistas que llevan la tensión a los reguladores y estabilizadores de voltaje. Además se denota que el disco rígido no posee Sistema operativo o está dañado. Se entrega Disco para la reinstalación del SO.\nSu reparación es posible.\nSe reemplazará el pin de carga, se repararán pistas y componentes afectados de la rama positiva y luego la notebok será testeada con disco de prueba para verificar funcionamiento y carga correcta.\nLa notebook será entregada sin el disco rígido.\nLa validéz del presupuesto es de 10 días.\nEl plazo de entrega es de 1 día. Se avisará cuando el equipo esté listo para retirar y si  luego de 5 días del aviso el equipo no fue retirado, el presupuesto podrá ser modificado.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,22,11,60000.0000,70.0000,0,'2024-02-07 00:00:00',1,0,0,0,0,0,60000.0000),(1010,'2024-02-09 00:00:00','2024-02-27 00:00:00','','En principio no dispara R2A R2C. Según cliente es el relé. Se revisa y el mismo está OK. \nSe Observa que no responde a botonera y además no reacciona a las entradas digitales, ya que queda en \"nst\", por mas qyue se puesntee LI4 con 24vdc. Se desarma todo y se encuentra que hay un opto (HCNW2611== 6N137) en la plaqueita vertical de la placa inferior que se conecta al panel, que tiene el diodo de entrada chanfleado. \n07/02/24: El cliente no lo quiere reparar. --> Se coloca nuevamente los optos, se rearma y se denota que ahora si responde a la botonera... para entregarlo se corta linea de tierra (pista 2 y 7) del cable plano del panel. \n09/02/24: el cliente quiere un presupuesto.\nEl problema estaba en esos optos. Se reemplazaron (HCNW1126 EN DICOMSE), se rehicieron pistas cortadas y luego se probó, verificando que responde ok al panel y además que funciona OK. Al probarlo se denota que tira OPF = falta de fase, ya que la corriente de salida es mínima o cero. Poniendo el parámetro OPL en NO, (FLT--> OPL= NO) no sensa la salida, por lo tanto se puede probar con poca carga o incluso en vacío y no da falla.\nAdemás, cuando se le da la orden FWR= LI1 en 24vdc, dispara OK el rele  R2A-R2C y esos contactos se ponen en corto.','El equipo presenta fallas en la etapa lógica de control, en la fuente que alimenta a dicha etapa y en el circuito de la botonera. Se denotan varios componentes defectuosos, con derivaciones en curvas características que generan fallas internas y de esta manera evitan la habilitación correspondiente de R2A y R2C, ya que el equipo detecta error.\nSu reparación es posible.\nSe reemplazarán los componentes dañados, se reacondicionará el circuito impreso y luego se realizará un mantenimiento preventivo a todas las placas, para poder ensayar al mismo, en banco de laboratorio. Se verificará el funcionamiento tanto en vacío como con carga nominal.\nDebido a la tardanza en la recepción de los repuestos necesarios, el plazo de entrega será de entre 10 y 15 días a partir de la aceptación del presente presupuesto.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,23,21,416500.0000,490.0000,0,'2024-02-12 00:00:00',1,0,0,0,0,0,416500.0000),(1011,'2024-02-12 00:00:00','2024-02-13 00:00:00','ELS ANTERIOR: 911','Está muy llena da grasa, aceite seco y tierra. Se realizó limpieza, se probó con la bacha y funciona OK.','Se denota que varios elementos eléctricos internos están llenos de aceite seco, por lo cual se vieron deteriorados, tanto física como funcionalmente. \nSu reparación es posible.\nSe realizará una limpieza integra de los componentes internos y de la carcasa metálica, se reemplazarán aquellos elementos defectuosos, se re cablearán los mismo y luego la freidora será probada con bacha y liquido para verificar temperaturas de encendido y de corte.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,24,13,76320.0000,90.0000,0,'2024-02-12 00:00:00',1,0,0,0,0,0,76320.0000),(1012,'2024-02-12 00:00:00','2024-02-12 00:00:00','','Le falta cablerío interno y está muy sucia.','Se denota que varios elementos eléctricos internos están llenos de aceite seco, por lo cual se vieron deteriorados, tanto física como funcionalmente. \nSu reparación es posible.\nSe realizará una limpieza integra de los componentes internos y de la carcasa metálica, se reemplazarán aquellos elementos defectuosos, se re cablearán los mismo y luego la freidora será probada con bacha y liquido para verificar temperaturas de encendido y de corte.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,25,13,76320.0000,90.0000,0,'2024-02-12 00:00:00',1,0,0,0,0,0,76320.0000),(1013,'2024-02-13 00:00:00','2024-02-13 00:00:00','Marca papel atascado.','El problema está en la cinta del encoder. La cinta transparente microperferada está sucia. Se limpió la misma y luego se prendión la máquina pasando OK todos los diagnósticos iniciales. Al principio no imprimía del escaner, pero se hizo una limpieza por soft y funcionó OK.','Se denota que el equipo posee fallas en los sensores de detección de papel y en el circuito de recepción de dicha señal. Se denotan componentes defectuosos que provocan errores en la transmisión y recepción de las señales hacia el sensor, por lo cual se genera el error de atasco de papel.\nSu reparación es posible.\nSe reemplazarán los componentes afectados y luego se probarán todas las funcionalidades de la impresora.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,26,17,115500.0000,135.0000,0,'2024-02-21 00:00:00',1,0,0,0,0,0,115500.0000),(1014,'2024-02-14 00:00:00','2024-02-14 00:00:00','E3','El E3 lo da cuando se pasa de temperatura y salta la protección termica ( \nReplacement Thermal Cut Out Switch for 220V BrewZilla / DigiBoil), pero no es ese el problema de este equipo, ya que el componente está OK. \nEn principio tiene pegado el boton de ON/OFF. Se limpiaron todos los pulsadores. Además el problema de E3 lo daba porque tiene al revez la fica de entrada, de fabrica (visto de frente, la línea esta a la derecha, cuando ahí debería ir Neutro). esto hace que a dicha protección le llegue neutro y el micro lo ve como error. Luego de verificar circuito se detectó eso.  (OJO EN LAS REFERENCIAS DE LA PLACA DONDE DICE \"AC-L\" VA EL CABLE AZUL Y EN \"AC-N\", VA EL MARRON) . Igualmente verificar en ficha.','El equipo presenta fallas en la etapa de recepción y procesamiento de las señales de medición de temperatura y en las fuentes secundarias que alimentan a dicho circuito, generando el error E3. Se denotan componentes agotados y algunos presentan residuos que deterioraron su funcionamiento y acortaron su tiempo de vida.\nSu reparación es posible.\nSe reemplazarán los componentes defectuosos, se reacondicionará el circuito impreso y luego el equipo será probado en banco de laboratorio, verificando las temperaturas y el funcionamiento total del equipo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,27,20,240720.0000,240.0000,0,'2024-02-15 00:00:00',1,0,0,0,0,0,240720.0000),(1015,'2024-02-21 00:00:00','2024-02-23 00:00:00','','No suelda. Enciende ok, pero no genera el arco.\nLos problemas son varios:\nCable de fase del lado del enchufe casi cortado.\nCable Portaelectrodo cortado totalmente justo en la base del mango.\nLlave de ON/OFF trabada en ON.\nEl alargue no tiene tierra.\nSe resolvieron esos problemas y la máquina funiciona OK.\nSe denota que la pinza de masa está muy deteriorada y se necesita cambiar (se informa al cliente para que lo haga)','El equipo presenta fallas en la etapa de potencia de salida y en la lógica que la controla. Se denotan componentes dañados a causa de posibles sobreconsumos, muy probablemente debido a el cruce de los cable de masa y electrodo. Se observa demás que la llave de ON/OFF está trabada.\nSu reparación es posible.\nSe recomienda reemplazar en principio el terminal de masa, que si bien hace contacto, el mismo está muy deteriorado y podría causar fallas en el futuro, y luego el cable portaelectrodos para un óptimo funcionamiento de la soldadora.\nSe reemplazarán los componentes electrónicos defectuosos, se realizará un mantenimiento preventivo a las placas y luego el equipo será probado, verificando las distintas potencias del mismo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,28,19,216000.0000,280.0000,0,'2024-02-21 00:00:00',1,0,0,0,0,0,216000.0000),(1016,'2024-02-21 00:00:00','2024-02-21 00:00:00','No prende','Tiene capas de fuente inchados. Se reemplazaran y funciona OK.','Se observa que el tv posee quemada la fuente de alimentación principal. Se denotan componentes dañados en los circuitos de protección y además en los que generan las tensiones necesarias para el buen funcionamiento del equipo.\nSu reparación es posible. \nSe reemplazarán los componentes dañados y luego el equipo se probará en banco de trabajo, verificando todas las funcionalidades del mismo.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',0,0,29,0,43000.0000,50.0000,0,'2024-02-21 00:00:00',1,0,0,0,0,0,43000.0000),(1017,'2024-02-22 00:00:00','2024-02-22 00:00:00','No dispara las velocidades de la bomba.','Tiene dañado el relé de velocidades RL03. Se reemplazó el mismo y se verrificaron los demas sin encontrar defectuosos. Además tieene uno de los capas de fuente mal. Se cambiaron C34 C38 y C29.\nOJO, ESTA PLACA ESTABA SETEADA PARA FUNCIONAR CON TERMOESTATO EXTERNO, CUANDO SE GIRA EL POTE DE SETEO TEMPERATURA CALEFACCION EL DISPLAY MUESTRA \"0\". ESTO ES POR PARAMETROS:  423- 0  /  425-0.\nSI SE COLOCA 423 -20   /   425- 57.\n\n421-1\n422-1_5\n423-0\n424-0\n425-82\n426-35\n432-0','El equipo presenta fallas en la etapa asociada al disparo de los relés, en los propios actuadores y en las fuentes que alimentan a dichos circuitos. Se denotan componentes agotados en la etapa de control de los relés de salida, como así también en dichos elementos. Tales desperfectos generan fallas aleatorias y un mal funcionamiento general de la placa\nSu reparación es posible. \nSe recomienda verificar todo elemento conectado a la placa ya que aquellos asociados a la salida del ventilador, a la bomba circuladora o a la válvula de gas, pudieron ser los responsables de la presente falla.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,30,18,110000.0000,140.0000,0,'2024-02-23 00:00:00',1,0,0,0,0,0,110000.0000),(1018,'2024-02-22 00:00:00','2024-02-28 00:00:00','USB ROTO','Se prueba y se denota que imprime bien, con algunas manchas de toner, pero eso es porque el interior está sucio.\nSe busca ficha USB para reemplazar y se cotiza.','Se denota que la ficha USB de la impresora está dañada. Es necesario el reemplazo de la misma. Se realizará además un mantenimiento preventivo de la placa principal que contiene a la lógica de control.\nLuego de la reparación se mantendrá a prueba, verificando todas las funcionalidades, la comunicación entre impresora y PC  y la correcta impresión hojas.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,31,23,72500.0000,84.0000,0,'2024-02-27 00:00:00',1,0,0,0,0,0,72500.0000),(1019,'2024-02-26 00:00:00','2024-02-26 00:00:00','ALLA rS: FR:mandos con alim. No se corta la alimentación de los relés\nde mandos de la CPU.','La falla se encontró en manual de A6300 FR -> rS. \"mandos con alim. No se corta la alimentación de los relés de mandos de la CPU.\"\nAl alimentarlo según carpeta y en AUTO, se vió esa falla. No salía de rS. Se verificaron alimentaciones y disparos de relés ok. \nLuego se sacaron todos los relés y se limpió zona ( toda la placa), se cambiaron capas C25 y C37 y se revisaron los relés. Todos estaban bien. \nSe analizó circuito y luego se simularon seguridades, verificando que la falla ya no estaba. Se colocaron los relés, luego se siguió procedimiento de ecendido (en carpeta), verificando que la falla había desaparecido.','La placa posee fallas en la etapa de lógica, en el circuito de disparos de los relés de salida y en las alimentaciones de dichas etapas. Se denotan componentes dañados, con derivaciones en curvas características que generan lecturas erróneas y por consiguiente la falla \"rS\" y mencionada.\nSu reparación es posible.\nSe reemplazarán los componentes afectados y luego el equipo será probado en banco con alimentaciones de 5V / 12V y 24V DC. Se simularán las señales de seguridad y se testeará la lógica de funcionamiento correspondiente.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,32,22,103000.0000,120.0000,0,'2024-02-27 00:00:00',1,0,0,0,0,0,103000.0000),(1020,'2024-02-28 00:00:00','2024-03-01 00:00:00','No enciende','ES TRIFÁSICO ( 3X 380) + NEUTRO. \nTiene comido varios cables y el capa de arranque de 2200uF * 35v esta explotado (parece comido). Se revisaron varios componentes críticos, luego se reemplazó el capa, se probó con 220en terminales correspondientes y enciende OK. \nSe informa y luego se termina el trabajo.\nSe reacondicionaron los cables comidos, se labó carcasa, y se probño con 380vac. Enviende OK y dispara pump 1 y 2 ok, (el blower está desabilitado por Dip switch B3 = off), ta,bbien dispara y tengo los 220vac en los trminales de la resistencia calefactora, pero luego de unos segundos corta, debido posiblemente a la falta de circulación de agua.','Se denota que el equipo posee varios cables comidos e incluso es visible uno de los componentes explotado y posiblemente comido también. Se observan fallas en las etapas de alimentación principal, en las fuentes de baja tensión que alimentan a la lógica de control y en las regulaciones asociadas para la generación de dicha alimentación.\nSu reparación es posible.\nSe reemplazarán los componentes dañados, se reacondicionará tanto el circuito impreso como en lo posible, los cables que fueron comidos y además se realizará una limpieza y mantenimiento general al equipo.\nLas probables causas de este tipo de fallas pudieron haber sido causadas por la puesta en corto circuito de cables expuestos por haber sido mordidos. \nAntes de instalar nuevamente el controlador, se recomienda verificar todo elemento conectado al mismo y así también el cableado.\nDebido a la tardanza en los repuestos necesarios, el plazo de entrega será de 7 días a partir de la aceptación del presente presupuesto.',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',1,0,33,24,331100.0000,385.0000,0,'2024-03-01 00:00:00',1,0,0,0,0,0,331100.0000),(1021,'2024-03-01 00:00:00','2024-03-01 00:00:00','Salida de 76VAC','Se denota que el pin del neutro está medi falseado y se va para adentro. Además se denota que una resistencia de 100K cerca del circuito del PWM KA3525, no hace contacto con las pistas. Se resuelda toda la zona y comopnentes críticos. Además se coloca poxipol para fijar el pin del Neutro.\nSe prueba con alimentación de batería 12V y salida a lámpara y funciona OK, teniendo 230vac en la salida.','El equipo presenta fallas en la etapa de fuente switching y en la potencia asociada que se encarga de realizar el acoplamiento de los disparos del circuito de salida. Varios componentes se encuentran defectuosos, con derivaciones en curvas características que perjudican la funcionalidad integral del equipo.\nSu reparación es posible.\nSe reemplazaran los componentes afectados y luego el equipo será probado en banco bajo condiciones normales de trabajo, verificando el consumo del equipo en vacío y con carga, y además que el mismo respete correctamente el rangos de tensión de salida.',2,'Diego Bertossi','BRC','Reparado','A la Espera de Aceptación','','',0,0,34,0,104000.0000,110.0000,0,NULL,1,0,0,0,0,0,0.0000),(1022,'2024-03-06 00:00:00','2024-03-06 00:00:00','','Tiene tierra adentro y afuera. Mantenimiento general. Se prueba con batería buena y carga bien.','',2,'Diego Bertossi','BRC','Reparado','Aceptado','','',1,0,35,25,45000.0000,52.0000,0,'2024-03-06 00:00:00',1,0,0,0,0,0,45000.0000),(1023,'2024-03-06 00:00:00','2024-03-06 00:00:00','','El cliente compró esta placa en el exterior y quiere verificar que este OK.\nSe revisaron híbridos, pack de potencia, disparos (incluídos en los híbridos) y fuentes. Todo parece OK..\nSe informa y se aclara que debido a la falla de la placa origical (salida en corto del pack) es necesario que se revisen los elementos conectados a la placa.','Se realizó una revisión completa de la placa, verificando los circuitos de disparos, de potencia asociada, filtros de linea, sensados de corriente y las fuentes que alimentan a dichas etapas. \nTodos los componentes revisados se encuentran en buen estado y además funcionan correctamente en conjunto con el resto, verificando así la funcionalidad integral de la placa. \nDebido a la falla que presentó la placa original, se recomienda verificar todo elemento conectado a la misma, en especial el motor conectado a las salidas inverte U(RED), V(WHITE) y W(BLACK).',2,'Diego Bertossi','BRC','Reparado','Aceptado','','',1,0,36,26,49500.0000,50.0000,0,'2024-03-13 00:00:00',1,0,0,0,0,0,49500.0000),(1024,'2024-03-11 00:00:00','2024-03-11 00:00:00','','Tiene en corto el trieac de salida , ( JST08A600BW TRIAC 600V 8A) Se reemplazó por BTB12-600','',2,'Diego Bertossi','Enviado','Reparado','Aceptado','','',0,0,37,0,55000.0000,63.0000,0,'2024-03-11 00:00:00',1,0,0,0,0,0,55000.0000),(1025,'2024-03-12 00:00:00','2024-03-12 00:00:00','Le entraron 380VAC','Tiene dañado varios componentes de la entrada, el pwm (LNK564N =  se sacó de otra placa igual) y una pista levantada..\nEn ML nueva está $113.000','Se denota que el equipo sufrió una sobretensión en la entrada de 220vac. Se observan componentes de protección contra tales condiciones que han sido activados y además otros elementos que sufrieron daños físicos, incluso el PCB, el cual posee varias pistas levantadas a causa de una explosión.\nSu reparación es posible.\nSe reacondicionará el circuito impreso, se reemplazarán los componentes dañados, para luego probar la placa modularmente y verificar todas las funcionalidades posibles.\nDebido a la falla presente, se recomienda verificar todo elemento periférico conectado a la placa y alimentado por los 220vac, ya que la falla pudo haberse extendido hacia otros componentes del lavarropas. \nEl presupuesto se limita a los trabajos mencionados.',2,'Diego Bertossi','BRC','Reparado','Aceptado','','',0,0,38,0,53000.0000,61.0000,0,'2024-03-15 00:00:00',1,0,0,0,0,0,0.0000),(1026,'2024-03-12 00:00:00','2024-03-12 00:00:00','Le entraron 380VAC','Varios componentes dañados en la etapa de entrada de 220vac. La falla no pasó al PWM, el cual está OK. Es reparable. Se reemplazaron componentes y luego se probó con 220vac y se denotan alimentaciones ok para lógica y micro.\nEn ML nueva está $346.800 12/03/24','Se denota que el equipo sufrió una sobretensión en la entrada de 220vac. La placa posee una capa de material carbonizado, producto de una explosión en componentes de la etapa de alimentación de 220vac. Se denotan elementos dañados tanto física como funcionalmente en dicha etapa y además en los circuitos asociados, como así tambuién el PCB. \nSu reparación es posible.\nSe reacondicionará el circuito impreso, se reemplazarán los componentes dañados, para luego probar la placa modularmente y verificar todas las funcionalidades posibles.\nDebido a la falla presente, se recomienda verificar todo elemento periférico conectado a la placa y alimentado por los 220vac, ya que la falla pudo haberse extendido hacia otros componentes del Secarropas. \nEl presupuesto se limita a los trabajos mencionados.',2,'Diego Bertossi','BRC','Reparado','Aceptado','','',0,0,39,0,110000.0000,127.0000,0,'2024-03-15 00:00:00',1,0,0,0,0,0,0.0000),(1027,'2024-03-12 00:00:00','2024-03-12 00:00:00','','Tiene dañada la fuente. Se colocó otra y funciona OK.\nOriginal:        6V 500mA\nReemplazo:  6,5V 600mA\nEl conjunto completo en ML usado está $ 60.000','Se denota que el equipo sufrió una sobretensión en la entrada de 220vac. Tanto la fuente externa como la fuente interna del equipo fueron afectadas por dicho exceso de voltaje, el cual produjo daños en los componentes de regulación y estabilización de tensiones.\nSu reparación es posible. \nSe reemplazarán los componentes dañados en el interior del equipo y se reemplazará la fuente externa por otra compatible.',2,'Diego Bertossi','BRC','En Reparación','Aceptado','','',0,0,40,0,26000.0000,30.0000,0,'2024-03-15 00:00:00',1,0,0,0,0,0,0.0000),(1028,'2024-03-13 00:00:00','2024-03-13 00:00:00','Marca papel atascado.','Aparentemente ya la han tocado porque tenía un suplemento plástico en una de las pestañas rebatibles del banco del fusor.\nLe falta el plastico que sostiene a las hojas de la bandeja y la misma bandeja no queda bien sujeta, pero si se acomoda bien y no se mueve no afecta al funcionamiento. Solo si las hojas se ban para atras (ya que no tiene el tope, si pasa eso marca P (La bandeja de papel o bandeja manual está vacía).\nAdemás de eso, y la falla principal es que el sensor que está debajo de ese banco, el que detecta cuando sale el papel de la bandeja, tiebne el resorte suelto, por lo tanto cuando pasa la hoja queda abajo y no vuelve a su posición, por eso la lógica lo detecta como que el papel quedó ahí atascado.Se desarmó:\nPrimero se saca toner y el rodillo verde del costado desde la puesta del frente, luego la puerta del costado, desatornillando la bisagra plástica y el resorte correspondiente.\nLuego se saca fusor, desatornillando los dos tornillos de los extremos (cuidado con los cables que hay que sacar -- para eso se saca la tapa trasera y se desenchufan los tres cables ). Con eso queda expuesto la tapa que recubre al sensor a sacar.\nTiene tres tornillos, al sacarlos además tiene dos trabas en los extremos.\nAl sacar esa tapa se puede acceder al sensor y acomodar el resorte,\nSe rearmó toto, se prueba y funciona OK.','Se denota que la bandeja de papel, le falta el soporte que sostiene a las hojas, dicho elemento no afecta al funcionamiento de la multifunción (salvo cuando las hojas se desplazan hacia el fondo, al no tener tope, la máquina no toma el papel correspondiente y acusa falla \"P\". Por otro lado, la falla principal \"atasco de papel\", proviene del procesamiento de señales de los sensores mecánico/opticos instalados en el interior del equipo. Se denota que dichas señales son erroneas por lo cual, se queda en constante error, por más que no haya ningún papel atascado.\nSu reparación es posible.\nSe tratará de solucionar el problema secundario debido a la falta del soporte mencionado, colocando un suplemento que haga tope con las hojas y además se reparará el circuito de sensado correspondiente a la falla de \"atasco de papel\".\nEl plazo de entrega es de 3 días a partir de la aceptación del presente presupuesto.',2,'Diego Bertossi','BRC','En Reparación','A la Espera de Aceptación','','',0,0,41,0,149000.0000,172.0000,0,NULL,1,0,0,0,0,0,0.0000),(1029,'2024-03-15 00:00:00','2024-03-15 00:00:00','ELS ANTERIOR: 971 - AVISO ANTERIOR: ','Es un equipo del año pasado 2023. Se pasó a este año porque lo aceptaron.\nLe falta el ventilador. Se revisó todo el equipo sin encontrar defectuosos. Se puso un cooler y se probó, funciona OK. en todas las funciones que permiten los dip switch.','El equipo presenta fallas en la etapa de alimentación hacia la lógica de control y en las fuentes secundarias que además habilitan al circuito de ventilación. Varios componentes se encuentran dañados, incluyendo la etapa de potencia, lo cual perjudica la funcionalidad integral del mismo. Se denota además que el ventilador no está presente.\nSu reparación es posible.\nSe reemplazarán los componentes afectados, se repondrán aquellos faltantes y luego será probado en banco en vacío y con carga, verificando que todas las funcionalidades seteadas por los dip switch entreguen las tensiones y corrientes que correspondan en cada caso.\nLas probables causas de este tipo de falla pueden deberse a presencia de obstrucciones en la ventilación que impiden el flujo de aire, y en consecuencia generan recalentamientos que perjudican el buen funcionamiento de los componentes.',2,'Diego Bertossi','BRC','Reparado','Aceptado','','',0,0,42,0,195500.0000,226.0000,0,'2024-03-15 00:00:00',1,0,0,0,0,0,0.0000),(1030,'2024-03-15 00:00:00','2024-03-17 00:00:00','ELS ANTERIOR: 851 - AVISO ANTERIOR: 22770','Según el cliente no disparaba relé para habilitar la carga.\nSe probó y funciona OK. El problema que están teniendo es que no conectan las baterías, por eso no habilita el equipo ya que nececita que las baterías estén conectadas.','El equipo fue puesto en banco de trabajo y probado  con banco de baterías, verificando un buen funcionamiento. Se observa que reconoce las baterías, siendo la tensión y la corriente de carga las correspondientes a la salida del mismo.\nSe realizó una revisión y mantenimeinto general.',2,'Diego Bertossi','BRC','Reparado','Aceptado','','',0,0,43,0,0.0000,0.0000,0,'2024-03-17 00:00:00',0,0,0,0,0,0,0.0000);
/*!40000 ALTER TABLE `reparaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL DEFAULT '0',
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (0,'Default'),(1,'Administrador Programador'),(2,'Tecnico'),(3,'Contable'),(4,'Tecnico Contable');
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
INSERT INTO `sucursal` VALUES (0,'',1,NULL,NULL,NULL,NULL),(1,'',2,NULL,NULL,NULL,NULL),(2,'',3,NULL,NULL,NULL,NULL),(3,'',4,'','','',''),(4,'',5,'','','',''),(5,'',6,'','','',''),(6,'',7,'','','',''),(7,'',8,'','','',''),(8,'BRC',9,'','','',''),(9,'MDP',9,'','','',''),(10,'CABA',9,'','','',''),(11,'',10,'','','',''),(12,'',11,'','','',''),(13,'',12,'','','',''),(14,'',13,'','','',''),(15,'',14,'','','',''),(16,'',15,'','','',''),(17,'',16,'','','',''),(18,'',17,'','','',''),(19,'',18,'','','',''),(20,'',19,'','','',''),(21,'',20,'','','',''),(22,'',21,'','','',''),(23,'',22,'','','',''),(24,'',23,'','','',''),(25,'',24,'','','','');
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
INSERT INTO `usuario` VALUES (1,1,0,'','','','','',''),(2,1,30925503,'Diego','Bertossi','1137688372','diego.bertossi@elsweb.com.ar','diego','1234');
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

-- Dump completed on 2024-03-17 19:06:10
