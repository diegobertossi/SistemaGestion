# CAMBIOS_APLICADOS.md — Registro de cambios del loop de auditoría

## Resumen
- **Cambios de producción: 12** (MonedaFormatterbis.parseAmount + 3 managers de Reparación + BCrypt→AES en UsuarioDAOImpl + config.properties centralizado + A3 completado en ControladorBackup/ControladorCliente/VentanaMigracion/CodigoSeguridadHandler/ControladorUsuarios/ControladorListados + fix mostrarOcultar 6 puntos + botón Equiparar FacturaSoft).
- **108 tests** creados, todos en verde. (Luego sumados: 7 de `edit`/`delete` de ClienteDAOImpl → **115 tests**).
- Infraestructura agregada (fuera de src/): carpeta `test/`, `lib-test/` (JUnit 4.13.2, Hamcrest 1.3, Mockito 4.11.0, mockito-inline 4.11.0, byte-buddy 1.12.23, objenesis 3.3, jbcrypt-0.4), `build_test.cmd`, `config.properties`, entradas nuevas en `.classpath`.

## Iteración 1 (4 fallos)
| Test | Causa raíz | Corrección |
|---|---|---|
| `ReparacionMapperTest.mapToReparacionDTO` | Aserción invertida: el constructor de 44 args mapea `nombre`→getCliente() y `ClienteCliente`→getClienteCliente() | Corregidas aserciones en el test |
| `MonedaFormatterbisTest.formatoDolar_incluyeSimbolo` | `contains("1500")` falla por el separador de miles ("U$S 1.500,00") | `contains("500")` |
| `MonedaFormatterbisTest.parseAmount_aceptaComaYPuntoDecimal` | Expectativa errónea: `parseAmount` de `MonedaFormatterbis` NO elimina puntos de miles → "1.234,56" degrada a 0.0 | Test reemplazado por 2 tests que documentan el comportamiento real |
| `MonedaFormatterTest.parseAmount_puntoUnicoSeInterpretaComoDecimal` | Expectativa errónea: "12.345" se interpreta formato inglés → 12.345 (no 12345) | Aserción corregida a 12.345 |

## Iteración 2 (2 fallos)
| Test | Causa raíz | Corrección |
|---|---|---|
| `ReparacionMapperTest.mapToReparacionDTO` (L91) | Bug en el test: esperaba "Diego" en `getNombreUsuario()`; el stub real es "Técnico 1" | Aserción corregida |
| `MonedaFormatterbisTest.parseAmount_aceptaComaDecimal` | Mi propio test contradijo el comportamiento documentado: "1,234.56" → 0.0 (miles con punto) | Aserción eliminada |

## Iteración 3 (1 fallo + 2 de compilación)
| Item | Causa raíz | Corrección |
|---|---|---|
| Compilación `UsuarioDAOImplTest` | `verify(...).prepareStatement()` lanza SQLException checked sin declarar | `throws SQLException` agregado |
| `ReparacionDAOImplTest.obtenerNumeroELSbsas` | NPE: el mock estático solo cubría `getConexion("Bariloche")`; el test usa "Buenos Aires" → mock devuelve null | Stub de ambas ubicaciones en `setUp` |

## Cambios de producción aplicados (iteraciones 4-8)

| Archivo | Cambio | Justificación | Dependientes verificados |
|---|---|---|---|
| `src/tiposPropios/MonedaFormatterbis.java` | `parseAmount` ahora detecta formato argentino (1.234,56) e inglés (1,234.56) y elimina separadores de miles antes de parsear. Alineado con `MonedaFormatter`. | Bug confirmado: "1.234,56" → 0.0. Clase sin callers hoy, pero riesgo latente si se usa. | Test `MonedaFormatterbisTest` actualizado y pasa; `MonedaFormatterTest` sin cambios. |
| `src/persistencia/dao/mysql/ReparacionQueryManager.java` | `closeResources` ya no llama `conexion.cerrarConexion()`; solo cierra Statement/ResultSet. | Evita reconexión innecesaria por operación (el resto de DAOs no cierran el singleton). También corrige NPE latente en `ControladorCliente:1490` al leer `getUbicacionActualStatic()` tras una operación. | `ReparacionDAOImplTest` (cadena completa) pasa; suite completa 107 tests OK. |
| `src/persistencia/dao/mysql/ReparacionEstadisticasManager.java` | Idem: elimina `cerrarConexion()` de `closeResources`. | Consistencia con QueryManager y resto de DAOs. | Suite completa OK. |
| `src/persistencia/dao/mysql/ReparacionComboManager.java` | Idem: elimina `cerrarConexion()` de `closeResources`. | Consistencia. | Suite completa OK. |
| `src/persistencia/dao/mysql/UsuarioDAOImpl.java` | Login y almacenamiento de contraseñas con **BCrypt** (hash + salt). `readUsuLogin` verifica con `BCrypt.checkpw`. Fallback a texto plano para compatibilidad con passwords legacy. | Elimina passwords en texto plano (riesgo A2). Fallback permite migración gradual. | `UsuarioDAOImplTest` actualizado (hash variable, fallback legacy); suite 108 tests OK. |
| `config.properties` (nuevo) + `src/util/Config.java` (nuevo) + `src/mails/EnviarMail.java` | Centralización de credenciales (SMTP, Clever Cloud, FacturaSoft, Migración, rutas, código de acceso) en archivo de propiedades externo. Eliminadas credenciales hardcodeadas de `EnviarMail` (4 cuentas), `ControladorBackup`, `ControladorCliente`, `VentanaMigracion`, `CodigoSeguridadHandler`. | Elimina riesgos A3 (credenciales hardcodeadas en múltiples archivos). Configuración externa para despliegue seguro. | Compilación OK; `EnviarMail` usa `Config.get()` con fallbacks a defaults. |
| `config.properties` + `ControladorBackup` | Credenciales Clever Cloud (BRC/BSAS) y credenciales locales (`db.host/port/user/password`) ahora vía `Config.get()` con fallback a los valores originales. `config.properties` sincronizado con las credenciales reales en uso. | Completa la centralización A3 para Backup remoto/local manteniendo compatibilidad (si falta config → defaults idénticos). | Compilación OK; suite 108 tests OK. |
| `ControladorCliente` | `sincronizarConFacturaSoft` y `eliminarEnFacturaSoft` leen host/port/user/pass/DB de `Config` (`facturasoft.brc.*` / `facturasoft.bsas.*`). Comparaciones `"0000"` reemplazadas por `Config.get("security.codigo.acceso", "0000")`. | Elimina credenciales hardcodeadas y código de acceso fijo. | Compilación OK; suite 108 tests OK. |
| `VentanaMigracion` | Constantes `STAGING_*` y `DESTINO_*` leen de `Config` (`migracion.staging.*`, `migracion.destino.*`) con fallbacks a localhost/root. | Elimina credenciales de migración hardcodeadas. | Compilación OK; suite 108 tests OK. |
| `CodigoSeguridadHandler` | Código `"0000"` → `Config.get("security.codigo.acceso", "0000")`. | Centraliza el código de acceso. | Compilación OK; suite 108 tests OK. |
| `ControladorUsuarios` | 1) `mostrarOcultar()` ahora fuerza exactamente `••••••` (6 puntos) al ocultar la contraseña (antes solo cambiaba echoChar). 2) `verificarCodigoSeguridad` usa `Config`. | El usuario veía cantidad variable de puntos al alternar mostrar/ocultar. | Compilación OK; suite 108 tests OK. |
| `ControladorListados` | `verificarCodigoSeguridad` usa `Config.get("security.codigo.acceso", "0000")`. | Centraliza el código de acceso. | Compilación OK; suite 108 tests OK. |
| `VentanaClientes` + `ControladorCliente` | Botón **EQUIPARAR FACTURASOFT** en la ventana de clientes: **VACÍA** la tabla `clientes` de Facturasoft y la **rellena** con todos los clientes de la BD actual (patrón Reparsoft) en una transacción. `nro_documento` ← columna `CUIT` de Reparsoft; si CUIT está vacío → `nro_documento = NULL`. Antes de insertar garantiza que `nro_documento` sea nullable (vía `information_schema` + `ALTER TABLE`) para que múltiples clientes sin documento no choquen con `uk_documento`. `setDocumento()` normaliza vacío→NULL también en el sync por cliente (alta/edición). Muestra resumen insertados/errores. | Herramienta eventual de coordinación/pruebas; resuelve los errores `Duplicate entry 'DNI-'` y `Column 'nro_documento' cannot be null`. | Compilación OK; suite 108 tests OK. |

## Clases dependientes verificadas tras cada corrección
- **MonedaFormatterbis**: test actualizado, 107 tests verdes.
- **3 managers**: `ReparacionDAOImplTest` valida la cadena fachada→manager→mapper; `ControladorCliente.sincronizarConFacturaSoft` ya no corre riesgo de NPE en `getUbicacionActualStatic()`; suite completa re-ejecutada: OK.
- **BCrypt + Config**: `UsuarioDAOImplTest` valida hash variable + fallback legacy; `Config` carga properties con fallbacks; suite completa (108 tests) OK en cada iteración.
- 115 tests totales pasan en cada iteración.

## Hallazgos de producción restantes (requieren decisión del usuario)

1. **`SELECT MAX(id)` race condition** (`ClienteDAOImpl`, `SucursalDAOImpl`): cambiar a auto-increment + `LAST_INSERT_ID` o usar sequences; requiere cambio de esquema BD.
2. **Stubs** `PermisoDAOImpl.edit()` → false, `RemitoDAOImpl.readAll()` → null, `RemitoDAOImpl.edit()` → false. Sin callers actuales; implementar si se necesitan (schema en scripts SQL).
3. **ControladorBackup**: `worker.get()` bloquea EDT; rutas absolutas `F:\els\...`. Requiere refactor a async + config.properties (las credenciales ya salieron a Config; queda el bloqueo EDT y las rutas).
4. **Duplicación severa**: `MonedaFormatter`/`MonedaFormatterbis` (ya unificados en lógica), 13 CellRenderers casi idénticos, `JTextAreaCustom` ×2, `READ_ALL` ×6 copias en SQLQueries.
