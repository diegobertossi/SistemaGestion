# Plan de Correcciones y Mejoras de Seguridad - SistemaGestion

Este documento detalla el plan de acción para corregir y mejorar los puntos débiles identificados en la auditoría de seguridad del sistema.

## Fase 1: Eliminación del Cifrado Reversible de Contraseñas (Alta Prioridad)
- **Objetivo:** Reemplazar el cifrado simétrico AES con clave maestra hardcodeada (`CryptoUtil`) por un esquema de hashing unidireccional seguro (`BCrypt`).
- **Acciones:**
  1. **Actualizar `UsuarioDAOImpl`:** 
     - Modificar inserción y edición (`insert`, `edit`) para almacenar contraseñas usando `BCrypt.hashpw(pass, BCrypt.gensalt())`.
     - Actualizar la validación de login (`readUsuLogin`) para validar exclusivamente contra hashes `BCrypt`, eliminando la desencriptación simétrica AES (`CryptoUtil.decrypt`).
  2. **Depuración de `CryptoUtil.java`:**
     - Remover la clave maestra estática (`MASTER_PASSPHRASE`) del código fuente.
  3. **Migración:**
     - Implementar rutina de un solo uso para re-hashear credenciales legacy al primer inicio de sesión.

## Fase 2: Externalización de Credenciales de Base de Datos (Media Prioridad)
- **Objetivo:** Evitar credenciales por defecto hardcodeadas (`root` / `root`) en el código fuente (`Conexion.java`).
- **Acciones:**
  1. **Abstracción en `Conexion.java`:**
     - Leer parámetros de conexión (host, puerto, usuario, contraseña) desde un archivo de configuración externo (ej. `config.properties`) o variables de entorno.
  2. **Guía de Hardening de MySQL:**
     - Establecer directrices operativas para servidores MySQL (bind-address = 127.0.0.1, usuarios con privilegios mínimos).

## Fase 3: Verificación y Pruebas de Regresión
- **Objetivo:** Validar la refactorización de seguridad sin afectar el flujo de la aplicación.
- **Acciones:**
  1. Actualizar tests unitarios (`UsuarioDAOImplTest`, `CryptoUtilTest`).
  2. Ejecutar la suite completa de JUnit (`build_test.cmd test`).
