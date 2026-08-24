-- ============================================================================
-- indices_rendimiento.sql
-- Indices de rendimiento para ReparSoft. Aplica a las 4 bases:
-- ordenesbrc, ordenesbsas, ordenesbrcantiguas, ordenesbsasantiguas.
--
-- Estado verificado (information_schema, 2026-08-22): las 4 bases YA tienen
-- los indices de reparaciones (idx_rep_FechaEntrada, idx_rep_FechadeDiagnostico,
-- idx_rep_FechAceptacion, idx_rep_estados, idx_rep_usuario_diagn/acept...),
-- por lo que NO se recrean aqui para evitar duplicados.
--
-- Lo que falta y crea este script (si no existe ya):
--   1) idx_cliente_nombre en cliente(nombre): las busquedas de cliente por
--      razon social filtran con WHERE nombre = ? y hoy escanean la tabla.
--   2) ft_reparaciones_texto FULLTEXT en reparaciones(Falla, Solucion,
--      Informecliente): prepara el terreno para acelerar la busqueda por
--      texto (las queries actuales usan LIKE '...%' y no lo usan; migrar a
--      MATCH...AGAINST es trabajo aparte). El indice no rompe nada.
--
-- El script es idempotente: usa information_schema para crear el indice solo
-- si no existe, de modo que se puede reejecutar sin errores.
-- ============================================================================

DELIMITER $$

CREATE PROCEDURE ordenesbrc.crear_indice_si_falta(
    IN p_tabla VARCHAR(64),
    IN p_indice VARCHAR(64),
    IN p_definicion VARCHAR(512)
)
BEGIN
    DECLARE existe INT DEFAULT 0;
    SELECT COUNT(*) INTO existe
      FROM information_schema.STATISTICS
     WHERE TABLE_SCHEMA = DATABASE()
       AND TABLE_NAME = p_tabla
       AND INDEX_NAME = p_indice;
    IF existe = 0 THEN
        SET @sql = CONCAT('ALTER TABLE ', p_tabla, ' ADD ', p_definicion);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END$$

DELIMITER ;

-- Base: ordenesbrc
USE ordenesbrc;
CALL ordenesbrc.crear_indice_si_falta('cliente', 'idx_cliente_nombre', 'INDEX idx_cliente_nombre (nombre)');
CALL ordenesbrc.crear_indice_si_falta('reparaciones', 'ft_reparaciones_texto', 'FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente)');

-- Base: ordenesbsas
USE ordenesbsas;
CALL ordenesbrc.crear_indice_si_falta('cliente', 'idx_cliente_nombre', 'INDEX idx_cliente_nombre (nombre)');
CALL ordenesbrc.crear_indice_si_falta('reparaciones', 'ft_reparaciones_texto', 'FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente)');

-- Base: ordenesbrcantiguas
USE ordenesbrcantiguas;
CALL ordenesbrc.crear_indice_si_falta('cliente', 'idx_cliente_nombre', 'INDEX idx_cliente_nombre (nombre)');
CALL ordenesbrc.crear_indice_si_falta('reparaciones', 'ft_reparaciones_texto', 'FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente)');

-- Base: ordenesbsasantiguas
USE ordenesbsasantiguas;
CALL ordenesbrc.crear_indice_si_falta('cliente', 'idx_cliente_nombre', 'INDEX idx_cliente_nombre (nombre)');
CALL ordenesbrc.crear_indice_si_falta('reparaciones', 'ft_reparaciones_texto', 'FULLTEXT INDEX ft_reparaciones_texto (Falla, Solucion, Informecliente)');

-- Limpieza del procedimiento auxiliar
DROP PROCEDURE IF EXISTS ordenesbrc.crear_indice_si_falta;

-- Verificacion final
SELECT TABLE_SCHEMA AS base, TABLE_NAME AS tabla, INDEX_NAME AS indice
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA IN ('ordenesbrc','ordenesbsas','ordenesbrcantiguas','ordenesbsasantiguas')
  AND INDEX_NAME IN ('idx_cliente_nombre','ft_reparaciones_texto')
GROUP BY base, tabla, indice
ORDER BY base, indice;
