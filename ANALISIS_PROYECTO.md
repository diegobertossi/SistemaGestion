# ANALISIS_PROYECTO.md — SistemaGestion / ReparsoftCliente

Auditoría de arquitectura y testing. Generado: 2026-08-01. 169 clases Java (Java 8, Eclipse plano, Swing + MySQL + JasperReports).

---

## 1. Mapa de clases y dependencias

### Capa de presentación (Swing)
- **52 vistas** en `presentacion/vista/` (VentanaLogin, VistaPrincipal, VentanaVisualizarEquipos, VentanaGenerarPresupuesto, VentanaListadoReparaciones, VentanaMigracion [herramienta crítica], etc.)
- **28 componentes** en `VistaPropias/` (CellRenderer ×13, JTextString/JTextNum/JTextDouble, TablaFiltros, AutoCompletarComboBox, CorrectorGramaticalAPI, ExtractorFacturaPDF, EscanerFacturasPDF, ActualizadorFacturasDB, CodigoSeguridadHandler, TableCopyHandler...)
- **13 controladores** en `presentacion/controlador/` (Implementan `ActionListener`; orquestan vistas + gestores + DAOs)

### Capa de lógica de negocio
- **12 gestores** en `presentacion/controlador/gestores/`
  - `GestorEstadosPresupuestos` → **lógica pura** (estados de pago/presupuesto, retrasos, facturación). Sin dependencia directa a Conexion/DAO.
  - `GestorDatos`, `GestorArchivosExcel`, `GestorRepuestos`, `GestorBusqueda`, `GestorInterfazEquipos`, `GestorAgregarEquipo`, `GestorListadoEquipos`, `GestorPresupuestoFactura`, `GestorClientesWSP`, `GestorVisualizacionEquipos`, `GestorVerificacionIngresoAnterior`
- **1 fachada central**: `modelo/Agenda.java` (723 líneas) — encapsula los 9 DAOs, ~100 métodos wrapper. NO testable sin BD (construye DAOs reales en el constructor).

### Capa de persistencia (DAO)
```
ClienteDAO/Impl ─┐
ClienteWSPDAO/Impl ─┤
RepuestoDAO/Impl ─┤
RolDAO/Impl ─┤
UsuarioDAO/Impl ─┤   ┌→ Conexion (singleton, MySQL) → DriverManager
SucursalDAO/Impl ─┤  │   └→ ConectorAccess (UCanAccess, migración)
PermisoDAO/Impl ─┤  │
RemitoDAO/Impl ─┤   │
FacturacionXclienteDAO ─┘
ReparacionDAO (interfaz, 75 métodos)
  └→ ReparacionDAOImpl (fachada pura, 0 SQL)
       ├→ ReparacionQueryManager (25 métodos, 748 líneas, SQL pesado)
       ├→ ReparacionEstadisticasManager (47 métodos, 368 líneas)
       ├→ ReparacionComboManager (12 métodos, 216 líneas)
       └→ ReparacionMapper (estático, 100% puro, ResultSet→DTO) ← ÚNICA clase de persistencia testeable sin BD
SQLQueries (501 líneas, ~60 constantes SQL + 2 whitelists)
LogDAO (estático, java.util.logging; error() usado 74×, info/warning nunca usados)
PantallaDAO (interfaz huérfana, SIN implementación)
```

### Capa de DTOs (14) y Modelo
- `dto/`: ReparacionDTO (god DTO, 46 campos, 12 constructores), ClienteDTO (defaults en constructor), UsuarioDTO (`getNombreRol()` lógica), PermisoDTO (equals roto con `==`), RemitoDTO (stub `getFecha_Entrada()` → null), RegistroPresupuestoDTO, etc.
- `modelo/`: Agenda (fachada), ELSAnterior (POJO), Permisos (fachada auth)

### Utilidades
- `tiposPropios/MonedaFormatter` + `MonedaFormatterbis` (formateo AR/BRL de montos — **puros**, `MonedaFormatterbis.parseAmount` **CORREGIDO** para manejar separadores de miles estilo argentino e inglés)
- `util/GeneradorPlanPDF` (plan de desarrollo FacturaSoft, standalone)
- `util/Config` (**NUEVO**): carga centralizada de `config.properties` con fallbacks
- `consumoAPI/ConsumoAPI` (dólar API + WhatsApp, sin credenciales)
- `mails/EnviarMail` (SMTP — **credenciales externalizadas a config.properties**)

### Puente con FacturaSoft (sistema hermano)
- `ControladorCliente.sincronizarConFacturaSoft()` y `eliminarEnFacturaSoft()` escriben en BDs `facturacion_db_brc`/`facturacion_db_bsas` con JDBC directo + credenciales root/root hardcodeadas.

---

## 2. Riesgos y code smells priorizados

### SEVERIDAD ALTA
| # | Hallazgo | Ubicación | Estado |
|---|---|---|---|
| A1 | **3 managers de Reparacion cierran la conexión SINGLETON** al final de cada operación (`conexion.cerrarConexion()`) → reconexión en cada llamada, riesgo de conexiones a medio cerrar | ReparacionQueryManager:711, EstadisticasManager:364, ComboManager:212 | **RESUELTO** (eliminado `cerrarConexion()` de `closeResources` en los 3 managers) |
| A2 | Contraseñas de usuario **en texto plano** (login `WHERE login=? AND pass=?` y almacenamiento) | UsuarioDAOImpl:21,50,81 | **RESUELTO** (BCrypt con hash + salt, fallback legacy para migración gradual) |
| A3 | Credenciales hardcodeadas: root/root en Conexion:19-23; SMTP en EnviarMail:16-41; Clever Cloud en ControladorBackup:54-64; facturacion_db_* en ControladorCliente:1493-1570; "0000" en CodigoSeguridadHandler y ControladorCliente | varios | **PARCIALMENTE RESUELTO** (SMTP externalizado a config.properties + Config.java; resto pendiente en ControladorBackup, ControladorCliente, VentanaMigracion, CodigoSeguridadHandler) |
| A4 | `PermisoDAOImpl.edit()` y `RemitoDAOImpl.edit()` son **stubs que devuelven false**; `RemitoDAOImpl.readAll()` devuelve **null** (contrato roto, riesgo NPE en callers) | PermisoDAOImpl:68, RemitoDAOImpl:61,107 | Pendiente (sin callers actuales) |
| A5 | ControladorBackup: `worker.get()` bloquea el EDT; rutas absolutas `F:\els\...` | ControladorBackup | Pendiente |
| A6 | `DELETE` con `setString` sobre columna int; SQL dinámico `String.format` (mitigado por whitelists) | ReparacionQueryManager:99,600,733 | Pendiente |

### SEVERIDAD MEDIA
| # | Hallazgo | Ubicación |
|---|---|---|
| M1 | 36 `e.printStackTrace()` violando convención LogDAO (managers de Reparacion + ConectorAccess) | varios |
| M2 | Métodos stub/tragados: updates `void` que no informan éxito; `catch` vacíos en `mapearCliente` (ClienteDAOImpl:273-275) | varios |
| M3 | **equals/hashCode rotos**: UsuarioDTO y PermisoDTO (sin hashCode, `==` en strings, NPE potencial) | dto |
| M4 | IDs por `SELECT MAX(id)` en vez de auto-increment (race condition) | ClienteDAOImpl:22, SucursalDAOImpl:22 |
| M5 | `PantallaDAO` sin implementación; import muerto en ReparacionDAO:7 | interfaz |
| M6 | Bug de tipo: SucursalDAOImpl:216 agrega `new ReparacionDTO(...)` a combos de sucursales | SucursalDAOImpl |
| M7 | `READ_ALL_X_ID_CLIENTE_ID_SUCURSAL` usa `||` como OR (en MySQL 8 con PIPES_AS_CONCAT sería concatenación) | SQLQueries:151 |
| M8 | Lógica de UI embebida en DAOs (combos con placeholders, `%05d`, prefijos de ubicación) | ClienteDAOImpl:161, RemitoDAOImpl:77,135 |
| M9 | Duplicación severa: MonedaFormatter/bis, JTextAreaCustom ×2, 13 CellRenderers casi idénticos, READ_ALL ×6 copias | varios |
| M10 | `obtenerIDporNombre` devuelve **1** (admin) como default silencioso | UsuarioDAOImpl:222,227,240 |
| M11 | Valores mágicos: ELS default 987/16549, idUsuario=1, códigos de ubicación 2,5,6,7 | ReparacionQueryManager:247,272; RemitoDAOImpl:77 |

### SEVERIDAD BAJA
- `info()`/`warning()` de LogDAO nunca usados; `System.out` de debug en Agenda/Conexion.
- Nombres mezclados ES/EN; mojibake "C�digo" en ListaArch/PopPapMultiseleccion; "armar请求" en GeneradorPlanPDF:101.
- SQL acces-heredado (`DISTINCTROW`, joins anidados); `READ_ALL` ASC vs `READ_ALL_PAGINADO` DESC inconsistente.

---

## 3. Clases con lógica pura testeable (candidatas a test sin BD)

| Clase | Métodos | Prioridad |
|---|---|---|
| `ReparacionMapper` | Todos los estáticos (mapeo ResultSet, listas mensuales, whitelists) | ALTA |
| `GestorEstadosPresupuestos` | verificarEstadoPago, estaPagado, calcularMontoPendiente, calcularPorcentajePago, puedeFacturarse, verificarEstadoPresupuesto, determinarEstadoTecnico, calcularDiasRetraso, verificarRetraso | ALTA |
| `MonedaFormatter` / `MonedaFormatterbis` | format/parse de montos es-AR | ALTA |
| `UsuarioDTO.getNombreRol` | switch idRol→nombre | MEDIA |
| `ClienteDTO` | defaults de constructor | MEDIA |
| `ConfigMigracion` | construcción de URLs JDBC | MEDIA |
| `ReparacionDTO` | constructores sobrecargados | MEDIA |
| `SQLQueries` | whitelists de campos | BAJA |

## 4. Cobertura de tests actual

El proyecto tenía **0 tests** originalmente. Se han creado **108 tests unitarios** organizados por capa:

| Capa | Tests | Clases cubiertas |
|---|---|---|
| **Persistencia (DAO + Mapper)** | 49 | `ReparacionMapper`, `ClienteDAOImpl`, `UsuarioDAOImpl`, `ReparacionDAOImpl` |
| **Lógica de negocio** | 20 | `GestorEstadosPresupuestos` |
| **Utilidades / DTOs** | 37 | `MonedaFormatter`, `MonedaFormatterbis`, `UsuarioDTO`, `ClienteDTO`, `ConfigMigracion`, `ReparacionDTO` |
| **Infraestructura** | 2 | `SmokeTest` (JUnit + Mockito) |
| **Total** | **108** | |

Todos los tests usan **mocks** (Mockito + mockito-inline para static mocking de `Conexion`), **sin base de datos real**.
