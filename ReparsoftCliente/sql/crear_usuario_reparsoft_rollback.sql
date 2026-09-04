-- ============================================================
-- crear_usuario_reparsoft_rollback.sql
-- Elimina el usuario de aplicacion reparsoft_app.
-- Uso:  mysql -u root -p < crear_usuario_reparsoft_rollback.sql
-- ============================================================

DROP USER IF EXISTS 'reparsoft_app'@'localhost';
FLUSH PRIVILEGES;
