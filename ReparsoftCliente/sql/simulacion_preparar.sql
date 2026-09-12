-- ============================================================
-- simulacion_preparar.sql
-- Seeds minimos para la BD de simulacion de concurrencia
-- (ordenesbrc_sim). Solo catalogos y filas requeridas por FKs
-- y defaults. NO contiene datos de negocio.
--
-- Uso (tras clonar la estructura con mysqldump --no-data):
--   mysql -u reparsoft_app ordenesbrc_sim < simulacion_preparar.sql
-- ============================================================

-- Limpieza idempotente (orden inverso a FKs; solo lo que este script crea)
DELETE FROM `Remitos` WHERE `idRemito` = 0;
DELETE FROM `usuario` WHERE `idUsuario` = 1;
DELETE FROM `rol` WHERE `idRol` IN (0,1,2,3,4);
DELETE FROM `UbicacionRemitos` WHERE `IdUbicacion` BETWEEN 1 AND 8;

-- Catalogo de ubicaciones de remito (copia del dump productivo)
INSERT INTO `UbicacionRemitos` (`IdUbicacion`, `Ubicacion`, `Codigo`) VALUES
  (1, 'CABA', 5),
  (2, 'MDP', 2),
  (3, 'COMUN CABA', 1000),
  (4, 'COMUN MDP', 2000),
  (5, 'COMUN BRC', 3000),
  (6, NULL, NULL),
  (7, 'BRC', 6),
  (8, 'MDP Avellaneda', 7);

-- Roles (copia del dump productivo)
INSERT INTO `rol` (`idRol`, `nombre`) VALUES
  (0, 'Default'),
  (1, 'Administrador Programador'),
  (2, 'Tecnico'),
  (3, 'Contable'),
  (4, 'Tecnico Contable');

-- Tecnico de simulacion (los workers usan idUsuario = 1)
INSERT INTO `usuario` (`idUsuario`, `idRol`, `dni`, `nombre`, `apellido`, `telefono`, `email`, `login`, `pass`)
  VALUES (1, 2, 0, 'Sim', 'Worker', '', '', 'simworker', '');

-- Fila comodin exigida por FKs y defaults (idRemito DEFAULT 0)
INSERT INTO `Remitos` (`idRemito`, `NumeroRemitoSalida`, `IdUbicacion`) VALUES (0, NULL, 6);
