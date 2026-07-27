-- Migración: expandir tabla cliente de ReparSoft con campos de FacturaSoft
-- Ejecutar manualmente contra el servidor MySQL antes de usar los nuevos campos
-- Las columnas son NULL por defecto para no romper el sistema ReparSoft existente
-- Los backups (mysqldump --complete-insert) incluyen automáticamente estas columnas

ALTER TABLE ordenesbsas.cliente
    ADD COLUMN tipo_documento VARCHAR(10) NULL DEFAULT 'CUIT',
    ADD COLUMN condicion_iva VARCHAR(60) NULL,
    ADD COLUMN tipo_persona VARCHAR(20) NULL DEFAULT 'empresa';

ALTER TABLE ordenesbrc.cliente
    ADD COLUMN tipo_documento VARCHAR(10) NULL DEFAULT 'CUIT',
    ADD COLUMN condicion_iva VARCHAR(60) NULL,
    ADD COLUMN tipo_persona VARCHAR(20) NULL DEFAULT 'empresa';
