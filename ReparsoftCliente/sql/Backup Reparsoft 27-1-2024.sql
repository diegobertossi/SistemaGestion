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
INSERT INTO `cliente` VALUES (1,'Ascensores Lucero','30706433585','Las Violetas 1215, San Carlos de Bariloche, RÃ­o Negro','542944425706','Ricardo Lucero','2944602590','luceroasc@speedy.com.ar'),(2,'Felipe',NULL,'Personal','0',NULL,'2944685047',NULL),(3,'Total Clima','30708448202','Elordi 368 (8400), San Carlos de Bariloche. Rio Negro, Argentina','2944431070','HÃ©ctor Spirito','2944487014','info@totalclimaonline.com.ar'),(4,'Mariel Celio','','','','','',''),(5,'Julio (Instalador Convertec)','','','','','',''),(6,'Daniel','','','','','',''),(7,'Privatel','30615757078','Rivadavia 571, San Carlos De Bariloche (8400), Rio Negro, Argentina','','Lisandro','5492944394682',''),(8,'Stec Climatización ','','','','Gonzalo Albarracín  Vranken','2944907634','stec.climatizacion@gmail.com'),(9,'ELS','','','','Sergio Fernández','','els@elsweb.com.ar');
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
INSERT INTO `equipos` VALUES (1,'Programador de Riego','AT 2000','Gadnic','R70Z5',NULL,'','','',4,3),(2,'Programador de Riego','9001D-C','Galcon','GM2529518',NULL,'','','',4,3),(3,'Programador de Riego','9001BT','Galcon','NYMQG',NULL,'','','',4,3),(4,'Central de arranque y transferencia automática','DKG-207','Datakom','2602070172960',NULL,'','','',5,4),(5,'Placa de caldera','Slim ( LMU33.201E149)','Baxi (Siemens)','9777',NULL,'','','',6,5),(6,'Placa Fuente de ascensor','A6210','Automac','A7422A',NULL,'','','',1,0),(7,'Placa de caldera','E312264 (TOP digital Sa26F)','Caldaia','65E7Q',NULL,'','','',6,5),(8,'Cargador de baterías( 220vac - 12V/50A)','CB-FL-12/750-50-D','HT SA','JOG9S',NULL,'','','',7,6),(9,'Placa de caldera','Genius M30 (11600011)','Caldaia','PV6YG',NULL,'','','',8,7),(10,'Inverter 48vdc / 220vac - 5000W','INVERTER5KW','Enertik','92932011101828',NULL,'','','',5,4),(11,'Central de arranque y transferencia automática','DKG-207','Datakom','2602070124518',NULL,'','','',5,4),(12,'Micro-Ohmmetro','MPK-253','Megabras','OA2098J',NULL,'','','',9,9),(13,'Microondas + Grill + Convector','MD11711','Medion','0076/08',NULL,'','','',7,6);
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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reemplazos`
--

LOCK TABLES `reemplazos` WRITE;
/*!40000 ALTER TABLE `reemplazos` DISABLE KEYS */;
INSERT INTO `reemplazos` VALUES (1,999,'','6B (TR smd)','6B (TR smd)',''),(2,999,'','TIP31C','TIP41C',''),(3,999,'','TIP115','TIP127','');
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
INSERT INTO `remitos` VALUES (0,NULL,6),(1,669,5),(2,668,5),(3,670,5),(4,671,5),(5,672,5),(6,673,5),(7,674,5);
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
INSERT INTO `reparaciones` VALUES (988,'2024-01-11 00:00:00','2024-01-16 00:00:00','','Enciente pero no responde OK a los pulsadores. Tienen mucha resistencia, son touch, se deben limpiar.','Se denota que el equipo posee componentes dañados en la etapa de recepción de señales provenientes de la botonera. Varios elementos de sensado y procesamiento de datos estan defectuosos y los mismos generan fallas aleatorias que perjudican la funcionalidad integral del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes afectados y luego el programador será probado bajo condiciones normales de trabajo.',2,'Diego','Enviado','Reparado','Aceptado','','',1,1,1,1,21500.0000,21.0000,0,'2024-01-16 00:00:00',1,0,0,0,1,0,21500.0000),(989,'2024-01-11 00:00:00','2024-01-16 00:00:00','Le entró agua','Tiene la placa con pistas corroídas y componentes afectados por lo mismo. \nSe debe realizar limpieza total y verificar luego el funcionamiento.','El equipo posee corrosión en toda la placa electrónica y en los componentes. Se denotan elementos dañados por la acción corrosiba del agua y además hay varias pistas que están cortadas. Se observa además, que el microprocesador que controla toda la lógica también fue afectado.\nSe necesitará realizar una limpieza total del equipo más la reparación y reemplazo de componentes dañados para verificar el buen funcionamiento del mismo, ya que por los daños en la placa, el microprocesador fue afectado y hay que realizar todo el trabajo para luego verificar el encendido y posterior prueba del equipo. \nEn caso de que el microprocesador esté dañado y en consecuencia el equipo no pueda ser reparado, los trabajos realizados no tendrán costo alguno.',2,'Diego','Enviado','Reparado','Aceptado','','',1,1,2,1,49500.0000,49.0000,0,'2024-01-16 00:00:00',1,0,0,0,1,0,49500.0000),(990,'2024-01-11 00:00:00','2024-01-16 00:00:00','Le entró Agua','Tiene la placa con pistas corroídas y componentes afectados por lo mismo. \nSe debe realizar limpieza total y verificar luego el funcionamiento.','El equipo posee corrosión en toda la placa electrónica y en los componentes. Se denotan elementos dañados por la acción corrosiba del agua y además hay varias pistas que están cortadas. Se observa además, que el microprocesador que controla toda la lógica también fue afectado.\nSe necesitará realizar una limpieza total del equipo más la reparación y reemplazo de componentes dañados para verificar el buen funcionamiento del mismo, ya que por los daños en la placa, el microprocesador fue afectado y hay que realizar todo el trabajo para luego verificar el encendido y posterior prueba del equipo. \nEn caso de que el microprocesador esté dañado y en consecuencia el equipo no pueda ser reparado, los trabajos realizados no tendrán costo alguno.',2,'Diego','Enviado','Reparado','Aceptado','','',1,1,3,1,49500.0000,49.0000,0,'2024-01-16 00:00:00',1,0,0,0,1,0,49500.0000),(991,'2024-01-23 00:00:00','2024-01-12 00:00:00','Queda con la alarma de AUXILIAR encendida y no hace transferencia','El equipo está lleno de insectos. Se limpia y luego se prueba y se denota que hay un grupo de parámentros que controla la entrada y salida de SPARE == AUXILIAR (parametros de 77 al 82) en donde el parámetro 80 estba en 1 (lo cual es normal cerrado , por eso quedaba cerrado. De fábrica está en 0. \nparametros del cliente:\n77=0 - 78=0 - 79=0 - 80=1  - 81=1 - 82=1\nparametros de fábrica:\n77=2 - 78=0 - 79=0 - 80=0  - 81=0 - 82=0\n\nse modificó a los de fábrica y se teste+o funcionamiento.','Se denota que el interior del equipo posee tierra e insectos que generaron corto circuitos en varias zonas de la lógica de control y de las salidas a relé. Se observan componentes dañados en la recepción de señales provenientes de las borneras, y en consecuencia el procesamiento de las mismas es erróneo lo que generan fallas aleatorias.\nSu reparación es posible.\nSe realizará un mantenimiento y limpieza general a todo el equipo, se reemplazarán los componentes dañados y luego se realizarán pruebas de todas las funcionalidades, verificando sensado de tensiones y transferencia de línea a generador.',2,'Diego','BRC','Reparado','Aceptado','','',1,0,4,6,156500.0000,185.0000,0,'2024-01-23 00:00:00',1,1,0,0,0,0,0.0000),(992,'2024-01-15 00:00:00','2024-01-15 00:00:00','','Se realiza conexionado (en carpeta y en server), y se denota que para que funciona OK y detecte llama debe estar conectada la tierra en  F1-F3- ANAFE -TIERRA DE LINEA. \nCon eso detecta llama OK. Se prueba y funciona OK. \nSe denota que dos de los relés estaban con algo de resistencia K4 y K2 pero no tienen que ver con la detección de llama ni la valvula de gas. \nSe informa como reparación aclarando que se revise tierra.','Se denotan fallas en la etapa de sensado de llama y en los circuitos que se encargan de procesar las señales de dicha etapa. Varios componentes se encuentran dañados a causa posiblemente de sobretensiones que ingresaron por el pin de sensado F2. \nSu reparación es posible.\nSe reemplazarán los componentes afectados y luego el equipo será probado en banco, bajo condiciones normales de trabajo.\nLas probables causas de este tipo de fallas pueden deberse a posibles desperfectos en el cableado, como la falta de tierra en la placa, ya que esta se usa como referencia y al estar posiblemente cortada, la tensión en dicho sector puede ser muy elevada. \nSe recomienda verificar el cableado y el conexionado del equipo para evitar futuros inconvenientes.',2,'Diego','Enviado','Reparado','Aceptado','','',1,0,5,2,140500.0000,160.0000,0,'2024-01-16 00:00:00',1,0,0,0,0,0,140500.0000),(993,'2024-01-15 00:00:00','2024-01-16 00:00:00','','Tiene la pista de tierra que va al conector W4-44 con una explsión y levantada. Además los tres reguladores dañados y los capas de salida C12 y C13.\nAdemás se observa que el puente JP1 está abierto, probablemente por la entrada de tensión por  W4-44. Se rehizo el puente (en otras placas estaba cerrado). Con eso la salida de 12VDC queda habilitada con la misma alimentación de 8vac y 24vac.','Se denota una explosión en las cercanías de la bornera W4-44. Debido a un exceso de tensión, se vieron afectados todos los reguladores, los filtros de salida y además varios componentes de rectificación.\nSu reparación es posible.\nSe reacondicionará el circuito impreso, se reemplazarán los componentes dañados y luego se realizarán pruebas funcionales tanto en vacío como con carga a las salidas de 5vdc 12vdc y 24vdc, verificando tensiones y corrientes entregadas. \nLas probables causas de este tipo de fallas pueden deberse a un ingreso de tensión indebida en la bornera W4-44 o también al agotamiento normal de los componentes. \nSe recomienda verificar todo elemento externo asociado a la placa fuente de referencia, como así también al cableado y al correcto conexionado del mismo.',2,'Diego','ENVIADO','Reparado','Aceptado','','',1,0,6,3,45800.0000,45.0000,0,'2024-01-16 00:00:00',1,0,0,0,0,0,45800.0000),(994,'2024-01-15 00:00:00','2024-01-16 00:00:00','E26','Tiene dañado el relé RL3. Se reemplazó por común cuadrado. El resto de los relés ok. Además se cambio capa C44 por las dudas.','El equipo presenta fallas en la etapa asociada a la salida del ventilador, de la válvula de gas y en las fuentes de alimentación asociadas a ese circuito. Se denotan componentes dañado que perjudican la funcionalidad integral del equipo.\nSu reparación es posible.\nSe reemplaarán los componentes dañados y se probarán las funcionalidades en banco y bajo condiciones normales de trabajo.',2,'Diego','BRC','Reparado','Aceptado','','',1,0,7,5,112500.0000,115.0000,0,'2024-01-20 00:00:00',1,0,0,0,0,0,112500.0000),(995,'2024-01-17 00:00:00','2024-01-17 00:00:00','','Ingresó con algunos cables del puente rectificador sueltos. \nTiene agotados todos los capas de la fuente de arranque y de lógica.\nSe reemplazarón y se checkearon los relés (todos OK), luego se probó en vacío y con carga de batería, verificando la correcta carga de la misma.','Se observa que el equipo posee cables desconectados. Se realizará un análisis de los circuitos para verificar el correcto conexionado y  además, luego de proceder con la revisión del mismo, se denota que presenta fallas en la etapa de habilitación de las seguridades de entrada, en las fuentes de arranque, en el circuito de disparos asociado y en la potencia del mismo. Se observan componentes defectuosos y fuera de valor en los circuitos mencionados, que generan fallas aleatorias y un mal funcionamiento general del equipo.\nSu reparación es posible. \nSe reemplazarán los componentes defectuosos y luego será probado en banco, bajo condiciones normales de trabajo, verificando las tensiones y corrientes entregadas, tanto en vacío como en funcionamiento normal de carga.',2,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,8,0,251475.0000,300.0000,0,NULL,0,0,0,0,0,0,0.0000),(996,'2024-01-23 00:00:00','2024-01-17 00:00:00','','Reparada de Stock','',2,'Diego','BRC','Reparado','A la Espera de Aceptación','','',1,0,9,4,110000.0000,0.0000,0,NULL,1,0,0,0,0,0,0.0000),(997,'2024-01-18 00:00:00','2024-01-18 00:00:00','','Tiene los capas de la fuente de carga de baterías dañados. Se reemplazaron, se probó y funciona OK.','El equipo presenta fallas en la etapa de potencia, en el circuito de disparos asociados y en las fuentes secundarias que alimentan a la lógica de control. Se denotan componentes dañados en las etapas mencionadas, como son los transistores de potencia, los drivers de control de los mismos y elementos de filtrado de línea y estabilización de tensiones del circuito cargador de baterías.\nSu reparación es posible. \nSe reemplazarán los componentes afectados, se reacondicionará el circuito impreso y se realizará una limpieza y mantenimiento general del equipo. Luego, será probado en banco con  tensión de red, de batería y de las celdas fotovoltaicas. Se realizarán chequeos modulares de las alimentaciones correspondientes y luego de la salida de 220 VAC.\nLas probables causas de este tipo de fallas pueden deberse a un sobreconsumo en la etapa de salida o al normal desgaste de los componentes.\nSe recomienda verificar el conexionado y el correcto cableado del equipo, como así también a todo elemento conectado a la salida.',2,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,10,0,571880.0000,680.0000,0,NULL,1,0,0,0,0,0,0.0000),(998,'2024-01-18 00:00:00','2024-01-18 00:00:00','','No leebien la tensión en R, la de S y la de T si están OK.\nTiene falsos contactos en las resistencias de sensado del canal R. Se realizó resoldado de dicho sector, luego se probó, verificando funcionamiento y mediciones OK en las tres fases.','Se denóta que el equipo no reliza correctamente las mediciones de tensión en la fase \"R\". Esto es debido a fallas en todo el circuito de sensado y a la etapa de recepción de señales, la cual posee componentes defectuosos, con derivaciones en curvas características que perjudican la funcionalidad integral del equipo.\nSu reparación es posible.\nSe realizará un mantenimiento y limpieza general a todo el equipo, se reemplazarán los componentes dañados y luego se realizarán pruebas de todas las funcionalidades, verificando sensado de tensiones y transferencia de línea a generador.',2,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,11,0,143500.0000,171.0000,0,NULL,1,0,0,0,0,0,0.0000),(999,'2024-01-19 00:00:00','2024-01-19 00:00:00','No mide en las escalas de 1mA/10mS/100mA/1A','Se prueba con alambres de diferentes longitudes para verificar mediciones y se denota que efectivamente no mide en esas escalas, pero si realiza medicione en 10A.\nSe denota que hay una placa que controla el disparo de corrientes en las escalas que no funciona. dicha placa posee un circuito de disparos y 3 relés, uno de esos disparos posee un transistor smd 6B en corto. Además fuera de esa plaquita hay dos transistores de potencia TO220 tambien en corto: TIP31C, reemplazado por TIP41C, y un TIP115, reemplazado por un TIP127.\nLuego de reemplazar esos componentes, se prueba de nuevo y ahora si mide OK en todas las escalas.','El equipo presenta fallas en las etapas que controlan las salidas de corrientes de 1mA/10mA/100mA y 1A. Se denotan componentes dañados en los circuito de disparos asociados y en las etapas de potencia de cada canal, lo que perjudica la funcionalidad integral del mismo.\nSu reparación es posible.\nSe reemplazarán los componentes defectuosos y luego el equipo será probado en banco, bajo condiciones normales de trabajo, verificando las corrientes entregadas en todas las escalas y luego los valores de las mediciones tomadas.',2,'Diego','BRC','Reparado','A la Espera de Aceptación','','',0,0,12,0,0.0000,0.0000,0,NULL,0,0,0,0,0,0,0.0000),(1000,'2024-01-19 00:00:00','2024-01-23 00:00:00','','No hay tensión en algunos puntos. No llegan los 220vac . El problema esta en los switch de las puertas, no llegan a presionarse. Se limpiaron por las dudas y se puso suplemento, además se limpiaron los relés de salida. Se prueba y funciona OK.','Se denota que el equipo posee fallas en la etapa de fuente, en los disparos de las salidas a relé y en los propios actuadores. Se observan componentes dañados que perjudican la funcionalidad total del equipo.\nSu reparación es posible.\nSe reemplazarán los componentes dañados, se realizará un mantenimiento preventivo y una limpieza general  y luego el equipo será probado en banco, verificando todas las funcionalidades.',2,'Diego','BRC','Reparado','Aceptado','','',1,0,13,7,108500.0000,129.0000,0,'2024-01-23 00:00:00',1,0,0,0,0,0,0.0000);
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
INSERT INTO `sucursal` VALUES (0,'',1,NULL,NULL,NULL,NULL),(1,'',2,NULL,NULL,NULL,NULL),(2,'',3,NULL,NULL,NULL,NULL),(3,'',4,'','','',''),(4,'',5,'','','',''),(5,'',6,'','','',''),(6,'',7,'','','',''),(7,'',8,'','','',''),(8,'BRC',9,'','','',''),(9,'MDP',9,'','','',''),(10,'CABA',9,'','','','');
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

-- Dump completed on 2024-01-27 12:59:57
