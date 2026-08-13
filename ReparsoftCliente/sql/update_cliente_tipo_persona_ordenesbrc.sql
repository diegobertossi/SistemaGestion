-- Actualiza la tabla `cliente` de la base `ordenesbrc` según tipo de persona.
--   · Si tipo_persona = 'particular' → condicion_iva = 'Consumidor Final' y tipo_documento = 'DNI'
--   · Si tipo_persona = 'empresa'   → tipo_documento = 'CUIT'
--
-- Las condiciones usan LOWER() para tolerar diferencias de mayúsculas.
-- La cláusula WHERE extra evita actualizaciones innecesarias (solo filas que realmente cambian).

USE `ordenesbrc`;

-- Evita el error 1175 de "safe update mode" de MySQL Workbench para scripts de mantenimiento
SET SQL_SAFE_UPDATES = 0;

-- Registros afectados por cada regla (solo informativo)
SELECT
    CASE WHEN LOWER(tipo_persona) = 'particular' THEN 'particular'
         WHEN LOWER(tipo_persona) = 'empresa' THEN 'empresa'
         ELSE 'otros' END AS tipo,
    COUNT(*) AS total
FROM `cliente`
GROUP BY tipo;

-- Regla 1: Particular → Consumidor Final + DNI
UPDATE `cliente`
SET `condicion_iva` = 'Consumidor Final',
    `tipo_documento` = 'DNI'
WHERE LOWER(`tipo_persona`) = 'particular'
  AND (`condicion_iva` IS NULL OR `condicion_iva` <> 'Consumidor Final' OR `tipo_documento` <> 'DNI');

-- Regla 2: Empresa → CUIT
UPDATE `cliente`
SET `tipo_documento` = 'CUIT'
WHERE LOWER(`tipo_persona`) = 'empresa'
  AND (`tipo_documento` IS NULL OR `tipo_documento` <> 'CUIT');

-- Restaura el modo seguro (solo afecta a esta conexión/sesión)
SET SQL_SAFE_UPDATES = 1;
