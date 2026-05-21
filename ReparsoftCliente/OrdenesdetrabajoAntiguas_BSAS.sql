DROP DATABASE IF EXISTS `ordenesbsasantiguas`;

CREATE DATABASE IF NOT EXISTS `ordenesbsasantiguas` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ordenesbsasantiguas`;

-- =============================================
-- Table structure for table 'Cliente'
-- =============================================

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` int NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CUIT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Domicilio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `TelefonoEmpresa` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Contacto` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `TelefonoContacto` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CorreoElectronico` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Table structure for table 'ClienteWSP'
-- =============================================

DROP TABLE IF EXISTS `clientewsp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientewsp` (
  `idClienteWSP` int NOT NULL AUTO_INCREMENT,
  `organizacion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombreWSP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TelefonoWSP` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idClienteWSP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Table structure for table 'Sucursal'
-- =============================================

DROP TABLE IF EXISTS `sucursal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sucursal` (
  `IdSucursal` int NOT NULL,
  `NombreSucursal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `idCliente` int DEFAULT NULL,
  `DomicilioSucursal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ContactoSucursal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TelefonoSucursal` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CorreoElectronico` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`IdSucursal`),
  KEY `idx_sucursal_idCliente` (`idCliente`),
  CONSTRAINT `fk_sucursal_cliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Table structure for table 'Equipos'
-- =============================================

DROP TABLE IF EXISTS `equipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipos` (
  `IdEquipo` int NOT NULL DEFAULT '0',
  `Nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Modelo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Marca` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NumeroDeSerie` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FechaFabr` datetime DEFAULT NULL,
  `Aviso` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ClienteCliente` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RemitoCliente` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `idCliente` int DEFAULT '0',
  `IdSucursal` int DEFAULT NULL,
  PRIMARY KEY (`IdEquipo`),
  KEY `idx_equipos_idCliente` (`idCliente`),
  KEY `idx_equipos_IdSucursal` (`IdSucursal`),
  KEY `idx_equipos_NumeroDeSerie` (`NumeroDeSerie`),
  CONSTRAINT `fk_equipos_cliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  CONSTRAINT `fk_equipos_sucursal` FOREIGN KEY (`IdSucursal`) REFERENCES `sucursal` (`IdSucursal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Table structure for table 'UbicacionRemitos'
-- =============================================

DROP TABLE IF EXISTS `ubicacionremitos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ubicacionremitos` (
  `IdUbicacion` int NOT NULL AUTO_INCREMENT,
  `Ubicacion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Codigo` int DEFAULT NULL,
  PRIMARY KEY (`IdUbicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Inserting data for table 'UbicacionRemitos'
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (1, 'CABA', 5);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (2, 'MDP', 2);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (3, 'COMUN CABA', 1000);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (4, 'COMUN MDP', 2000);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (5, 'COMUN BRC', 3000);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (6, NULL, NULL);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (7, 'BRC', 6);
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES (8, 'MDP Avellaneda', 7);

-- =============================================
-- Table structure for table 'Remitos'
-- =============================================

DROP TABLE IF EXISTS `remitos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `remitos` (
  `idRemito` int NOT NULL DEFAULT '0',
  `NumeroRemitoSalida` int DEFAULT NULL,
  `IdUbicacion` int DEFAULT '0',
  PRIMARY KEY (`idRemito`),
  KEY `idx_remitos_IdUbicacion` (`IdUbicacion`),
  CONSTRAINT `fk_remitos_ubicacion` FOREIGN KEY (`IdUbicacion`) REFERENCES `ubicacionremitos` (`IdUbicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Inserting data for table 'Remitos'
INSERT INTO `Remitos` (`idRemito`, `NumeroRemitoSalida`, `IdUbicacion`) VALUES (0, NULL, 6);

-- =============================================
-- Table structure for table 'rol'
-- =============================================


DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `idRol` int NOT NULL DEFAULT '0',
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `rol` (`idRol`, `nombre`) VALUES (0, 'Default');
INSERT INTO `rol` (`idRol`, `nombre`) VALUES (1, 'Administrador Programador');
INSERT INTO `rol` (`idRol`, `nombre`) VALUES (2, 'Tecnico');
INSERT INTO `rol` (`idRol`, `nombre`) VALUES (3, 'Contable');
INSERT INTO `rol` (`idRol`, `nombre`) VALUES (4, 'Tecnico Contable');

#ALTER TABLE rol MODIFY idRol INT NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
-- =============================================
-- Table structure for table 'usuario'
-- =============================================


DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `idRol` int DEFAULT NULL,
  `dni` int DEFAULT NULL,
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `telefono` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `login` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pass` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `idx_usuario_idRol` (`idRol`),
  CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



INSERT INTO `usuario` (`idUsuario`, `idRol`, `dni`,`nombre`, `apellido`, `telefono`, `email`, `login`, `pass`) VALUES ( 1,0,0,'','','','','','');
INSERT INTO `usuario` (`idUsuario`, `idRol`, `dni`,`nombre`, `apellido`, `telefono`, `email`, `login`, `pass`) VALUES ( 2,1,30925503,'Diego','Bertossi','1137688372','diego.bertossi@elsweb.com.ar','diego','1234');
INSERT INTO `usuario` (`idUsuario`, `idRol`, `dni`,`nombre`, `apellido`, `telefono`, `email`, `login`, `pass`) VALUES (6,4,11111111,'Sergio','Fernández','5492235969934','sergio.fernandez@elsweb.com.ar','sfernandez','123456');




-- =============================================
-- Table structure for table 'reparaciones'
-- =============================================

DROP TABLE IF EXISTS `reparaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reparaciones` (
  `ELS` int NOT NULL DEFAULT '0',
  `FechaEntrada` datetime DEFAULT NULL,
  `FechaSalida` datetime DEFAULT NULL,
  `FechadeDiagnostico` datetime DEFAULT NULL,
  `Falla` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Solucion` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `Informecliente` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `idUsuario` int DEFAULT '0',
  `NombreUsuario` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EstadoFisico` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EstadoTecnico` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `EstadoComercial` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RemitoCliente` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `OrdendeCompra` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Agregadoaremito` tinyint(1) DEFAULT '0',
  `RemitoGenerado` tinyint(1) DEFAULT '0',
  `idEquipo` int DEFAULT '0',
  `idRemito` int DEFAULT '0',
  `PrecioPeso` decimal(19,4) DEFAULT '0.0000',
  `PrecioDolar` decimal(19,4) DEFAULT '0.0000',
  `FechAceptacion` datetime DEFAULT NULL,
  `PresupuestoGenerado` tinyint(1) DEFAULT '0',
  `PresupuestoEnviado` tinyint(1) DEFAULT '0',
  `WordGenerado` tinyint(1) DEFAULT '0',
  `WordEnviado` tinyint(1) DEFAULT '0',
  `AvisoEnviado` tinyint(1) DEFAULT '0',
  `Pago` decimal(19,4) DEFAULT '0.0000',
  `lugar_de_ingreso` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NroFactura` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ELS`),
  KEY `idx_rep_idUsuario` (`idUsuario`),
  KEY `idx_rep_idEquipo` (`idEquipo`),
  KEY `idx_rep_idRemito` (`idRemito`),
  KEY `idx_rep_FechaEntrada` (`FechaEntrada`),
  KEY `idx_rep_FechadeDiagnostico` (`FechadeDiagnostico`),
  KEY `idx_rep_FechAceptacion` (`FechAceptacion`),
  KEY `idx_rep_usuario_diagn` (`idUsuario`,`FechadeDiagnostico`),
  KEY `idx_rep_usuario_acept` (`idUsuario`,`FechAceptacion`),
  KEY `idx_rep_estados` (`EstadoComercial`,`EstadoFisico`,`Agregadoaremito`),
  KEY `idx_rep_nrofactura` (`NroFactura`),
  CONSTRAINT `fk_rep_equipo` FOREIGN KEY (`idEquipo`) REFERENCES `equipos` (`IdEquipo`),
  CONSTRAINT `fk_rep_remito` FOREIGN KEY (`idRemito`) REFERENCES `remitos` (`idRemito`),
  CONSTRAINT `fk_rep_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Table structure for table 'reemplazos'
-- =============================================

DROP TABLE IF EXISTS `reemplazos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reemplazos` (
  `idReemplazos` int NOT NULL AUTO_INCREMENT,
  `ELS` int DEFAULT NULL,
  `ref` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `original` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reemplazo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `notas` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idReemplazos`),
  KEY `idx_reemplazos_ELS` (`ELS`),
  KEY `idx_reemplazos_original` (`original`),
  KEY `idx_reemplazos_reemplazo` (`reemplazo`),
  CONSTRAINT `fk_reemplazos_rep` FOREIGN KEY (`ELS`) REFERENCES `reparaciones` (`ELS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Table structure for table 'pantalla'
-- =============================================

DROP TABLE IF EXISTS `pantalla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pantalla` (
  `idPantalla` int NOT NULL AUTO_INCREMENT,
  `idPantPadre` int DEFAULT NULL,
  `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idPantalla`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `idx_pantalla_padre` (`idPantPadre`),
  CONSTRAINT `fk_pantalla_padre` FOREIGN KEY (`idPantPadre`) REFERENCES `pantalla` (`idPantalla`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Table structure for table 'permisos'
-- =============================================

DROP TABLE IF EXISTS `permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos` (
  `idPermiso` int NOT NULL AUTO_INCREMENT,
  `idRol` int NOT NULL,
  `idPantalla` int NOT NULL,
  PRIMARY KEY (`idPermiso`),
  KEY `idx_permisos_idRol` (`idRol`),
  KEY `idx_permisos_idPantalla` (`idPantalla`),
  CONSTRAINT `fk_permisos_pantalla` FOREIGN KEY (`idPantalla`) REFERENCES `pantalla` (`idPantalla`),
  CONSTRAINT `fk_permisos_rol` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- =============================================
-- Inserting data for table 'pantalla'
-- =============================================

LOCK TABLES `pantalla` WRITE;
/*!40000 ALTER TABLE `pantalla` DISABLE KEYS */;
INSERT INTO `pantalla` (`idPantalla`, `idPantPadre`, `nombre`) VALUES (1,NULL,'Equipos'),(2,NULL,'Salidas'),(3,NULL,'Listados'),(4,NULL,'Busquedas'),(5,NULL,'Clientes'),(6,NULL,'Presupuestos'),(7,NULL,'Usuarios'),(8,NULL,'BackUp'),(9,NULL,'Configuracion');
/*!40000 ALTER TABLE `pantalla` ENABLE KEYS */;
UNLOCK TABLES;

-- =============================================
-- Inserting data for table 'permisos'
-- =============================================

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT INTO `permisos` (`idPermiso`, `idRol`, `idPantalla`) VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,2,1),(11,2,2),(12,2,3),(13,2,4),(14,3,1),(15,3,2),(16,3,3),(17,3,4),(18,3,5),(19,3,6),(20,3,9),(24,4,1),(25,4,2),(26,4,3),(27,4,4),(28,4,5),(29,4,6),(30,4,7),(31,4,8),(32,4,9);
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;
