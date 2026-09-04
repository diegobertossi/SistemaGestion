-- ============================================================
-- crear_usuario_reparsoft.sql
-- Crea el usuario de aplicacion 'reparsoft_app'@'localhost'
-- usado por ReparSoft y FacturaSoft en reemplazo de root/root.
--
-- Uso:  mysql -u root -p < crear_usuario_reparsoft.sql
-- Rollback: crear_usuario_reparsoft_rollback.sql
-- ============================================================

CREATE USER IF NOT EXISTS 'reparsoft_app'@'localhost' IDENTIFIED BY 'els2026';

-- Nivel schema: cubre SELECT, INSERT, UPDATE, DELETE, CREATE, DROP,
-- ALTER, INDEX, REFERENCES, LOCK TABLES, CREATE VIEW, SHOW VIEW,
-- CREATE ROUTINE, ALTER ROUTINE, EXECUTE, EVENT, TRIGGER,
-- SHOW_ROUTINE (equivalente 8.x del viejo acceso a mysql.proc).
GRANT ALL PRIVILEGES ON `ordenesbrc`.*          TO 'reparsoft_app'@'localhost';
GRANT ALL PRIVILEGES ON `ordenesbsas`.*         TO 'reparsoft_app'@'localhost';
GRANT ALL PRIVILEGES ON `ordenesbrcantiguas`.*  TO 'reparsoft_app'@'localhost';
GRANT ALL PRIVILEGES ON `ordenesbsasantiguas`.* TO 'reparsoft_app'@'localhost';
GRANT ALL PRIVILEGES ON `reparsoft_staging`.*   TO 'reparsoft_app'@'localhost';
GRANT ALL PRIVILEGES ON `facturacion_db`.*      TO 'reparsoft_app'@'localhost';
GRANT ALL PRIVILEGES ON `facturacion_db_brc`.*  TO 'reparsoft_app'@'localhost';
GRANT ALL PRIVILEGES ON `facturacion_db_bsas`.* TO 'reparsoft_app'@'localhost';

-- Nivel global (no otorgables por schema):
-- PROCESS:        mysqldump 8.4 lo exige si no se usa --no-tablespaces.
-- SET_ANY_DEFINER: permite recrear en un restore objetos con
--                 DEFINER=root (ej. vista reparsoft_staging.resumen_migracion).
--                 (En MySQL 8.4 reemplaza al eliminado SET_USER_ID.)
GRANT PROCESS        ON *.* TO 'reparsoft_app'@'localhost';
GRANT SET_ANY_DEFINER ON *.* TO 'reparsoft_app'@'localhost';
-- SYSTEM_USER: necesario ademas cuando el DEFINER a recrear es un
-- usuario del sistema (root@localhost). Sin el, el restore de dumps
-- viejos falla con ERROR 1227 aunque se tenga SET_ANY_DEFINER.
-- No otorga acceso a datos; es seguro para un usuario solo-localhost.
GRANT SYSTEM_USER    ON *.* TO 'reparsoft_app'@'localhost';

FLUSH PRIVILEGES;

-- Verificacion
SHOW GRANTS FOR 'reparsoft_app'@'localhost';
