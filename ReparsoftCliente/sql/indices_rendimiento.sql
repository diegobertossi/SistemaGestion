-- ============================================================================
-- indices_rendimiento.sql
-- Índices de rendimiento para ReparSoft.
-- Aplica a las 4 bases: ordenesbrc, ordenesbsas, ordenesbrcantiguas,
-- ordenesbsasantiguas.
--
-- Motivo: las tablas solo tienen PKs y claves de join. Las consultas de
-- estadisticas filtran por fecha (YEAR(Fecha...) = ?) y el listado de
-- aceptaciones por EstadoComercial; hoy hacen full table scan.
--
-- NOTA sobre idempotencia: MySQL 8 no soporta "CREATE INDEX IF NOT EXISTS".
-- Si un indice ya existe, la sentencia falla con error 1061 (Duplicate key
-- name); ese error se puede ignorar sin riesgo.
--
-- NOTA FULLTEXT: el indice FULLTEXT sobre Falla/Solucion/Informecliente
-- prepara el terreno para acelerar la busqueda por texto (hoy LIKE '%...%'
-- hace scan completo). Los LIKE actuales NO se benefician; el aprovechamiento
-- real requiere migrar la query a MATCH ... AGAINST (trabajo aparte).
-- El indice se crea igual: no rompe nada y evita re-crearlo despues.
-- En tablas MyISAM/latin1 el FULLTEXT es soportado.
-- ============================================================================

-- ----------------------------------------------------------------------------
-- Base: ordenesbrc
-- ----------------------------------------------------------------------------
USE ordenesbrc;

ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaentrada (FechaEntrada);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechadiagnostico (FechadeDiagnostico);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaceptacion (FechAceptacion);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_estadocomercial (EstadoComercial);
ALTER TABLE cliente ADD INDEX idx_cliente_nombre (nombre);

ALTER TABLE reparaciones ADD FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente);

-- ----------------------------------------------------------------------------
-- Base: ordenesbsas
-- ----------------------------------------------------------------------------
USE ordenesbsas;

ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaentrada (FechaEntrada);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechadiagnostico (FechadeDiagnostico);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaceptacion (FechAceptacion);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_estadocomercial (EstadoComercial);
ALTER TABLE cliente ADD INDEX idx_cliente_nombre (nombre);

ALTER TABLE reparaciones ADD FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente);

-- ----------------------------------------------------------------------------
-- Base: ordenesbrcantiguas
-- ----------------------------------------------------------------------------
USE ordenesbrcantiguas;

ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaentrada (FechaEntrada);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechadiagnostico (FechadeDiagnostico);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaceptacion (FechAceptacion);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_estadocomercial (EstadoComercial);
ALTER TABLE cliente ADD INDEX idx_cliente_nombre (nombre);

ALTER TABLE reparaciones ADD FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente);

-- ----------------------------------------------------------------------------
-- Base: ordenesbsasantiguas
-- ----------------------------------------------------------------------------
USE ordenesbsasantiguas;

ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaentrada (FechaEntrada);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechadiagnostico (FechadeDiagnostico);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_fechaceptacion (FechAceptacion);
ALTER TABLE reparaciones ADD INDEX idx_reparaciones_estadocomercial (EstadoComercial);
ALTER TABLE cliente ADD INDEX idx_cliente_nombre (nombre);

ALTER TABLE reparaciones ADD FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente);
