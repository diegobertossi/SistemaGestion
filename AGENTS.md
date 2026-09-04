# AGENTS.md - SistemaGestion / ReparsoftCliente

Sistema de gestión para talleres de reparación electrónica. Desktop Java Swing app (Eclipse project).

## Stack

Java 8, Swing (JTattoo Aluminium L&F), MySQL 8, JasperReports 6.21, Apache POI 5.2, iText/OpenPDF, JavaMail, JOrtho (spellcheck).

## Entry point

`ReparsoftCliente/src/main/Main.java` → splash → `VentanaUbicacionBaseDeDatos` (DB selection) → `VentanaLogin` → `VistaPrincipal`

Modo PRUEBA/PRODUCCION: el toggle vive en `VistaPrincipal` (debajo de SALIR), visible solo para el rol "Administrador Programador" (`ControladorUsuLogin.esAdministradorProgramador()` al loguear; se oculta al cerrar sesión). `util/RutasSistema` lo persiste con `java.util.prefs.Preferences` (nodo `reparsoft` → en Windows `HKCU\Software\JavaSoft\Prefs\reparsoft`), así que el sistema recuerda el modo al reabrirse. Solo cambia las rutas de guardado de reportes/excels/backups (carpeta `Sistema Reparsoft Pruebas`), NO las bases MySQL. Default si nunca se tocó: PRUEBA.

## Structure (src/ top-level dirs under ReparsoftCliente/)

`consumoAPI/ dto/ mails/ main/ modelo/ org/ persistencia/ presentacion/ tiposPropios/ util/ vista/ VistaPropias/`

Notable: `org/eclipse/wb/swing/FocusTraversalOnArray.java` (WindowBuilder helper), `util/GeneradorPlanPDF.java`

Herramientas de datos (standalone, en src/util/):
- `NormalizadorMojibake.java` — detecta y revierte doble-encoding UTF-8→CP850 (mojibake histórico: `Climatizaci├│n`→`Climatización`, `ÔÇ£`→`“`). `main(JDBC_URL user pass)` es dry-run (solo reporta); con `--apply` aplica UPDATEs. Solo toca valores con síntomas (box-drawing `U+2500-U+257F`, `ÔÇ`, `â€`, `Ã`+letra) y es 100% segura: requiere que el texto sea codificable a CP850 y que el resultado quede sin nuevos artefactos. No toca `pass`. Aplicado 2026-08-23 a las 4 locales + 2 cloud (1377 celdas solo en ordenesbrc) — verificadas por HEX en bytes. **OJO en Windows:** nunca usar `mysqldump > archivo` ni `Get-Content | mysql` de PowerShell para mover datos con tildes — la consola recodifica (CP850) y corrompe UTF-8. Usar `--result-file` en mysqldump o el flujo JDBC.

## Architecture

- **MVC**: Controladores (13 files in `presentacion/controlador/`) implement `ActionListener`; vistas in `presentacion/vista/`
- **DAO**: interfaces in `persistencia/dao/interfaz/`, MySQL impls in `persistencia/dao/mysql/` (16 files)
- **Gestores**: business logic in `presentacion/controlador/gestores/` (12 files)
- **Singleton**: `Conexion.java` for MySQL connection
- **Spanish naming** for business concepts (clientes, equipos, presupuestos, remitos...)

## DB connection (config.properties — NO versionado)

Las credenciales se leen de `config.properties` (vía `util.Config`; `Conexion.java` ya NO las hardcodea — usa los mismos defaults como último recurso). Claves: `db.host/db.port/db.user/db.password/db.options`.

Desde 2026-09-01 las apps usan el usuario dedicado **`reparsoft_app`@`localhost`** (grants por `sql/crear_usuario_reparsoft.sql`: ALL sobre las 8 bases `ordenes*`/`reparsoft_staging`/`facturacion_db*` + `PROCESS`, `SET_ANY_DEFINER`, `SYSTEM_USER` globales — requeridos por mysqldump 8.4 y restores con DEFINER=root). `root@localhost` tiene password fuerte y NO se usa en la app (solo admin manual/Workbench). Kit de migración para PCs ya instaladas: `F:\Trabajo\actualizacion_credenciales_2026\`. El instalador NSIS crea `reparsoft_app` y jamás debe volver a fijar `root/root`: en PC limpia fija la fuerte de root y crea el usuario (`CreateAppUserMySQL64`); si MySQL ya estaba instalado, `EnsureAppUserExisting` intenta con la fuerte y luego con `root/root` (rotándola si era el caso); `configurar_mysql.bat` (reparación interactiva del usuario) se genera SIEMPRE; `config.properties` se copia también a la carpeta de FacturaSoft.

**GOTCHA NSIS (crítico, medido):** `nsExec::ExecToLog/ExecToStack` NO pasa por cmd.exe, por lo que la redirección `mysql ... < script.sql` no funciona (mysql recibe `<` como argumento literal → exit 1 y no ejecuta nada). Todo `nsExec` con `<` debe envolverse: `nsExec::ExecToLog 'cmd /c ""$MySQLBinPath\mysql.exe" -u root < "$TEMP\x.sql""'`. Esto era un bug latente del instalador viejo (los métodos 0/2/4 de seteo de password nunca andaban; salvaba mysqladmin del método 1).

DB names: `ordenesbrc` / `ordenesbsas` (normal), `ordenesbrcantiguas` / `ordenesbsasantiguas` (legacy). Also supports Access via UCanAccess.

**IMPORTANTE — cambios de esquema:** cualquier ALTER TABLE / índice / columna nueva debe aplicarse a las 4 bases (`ordenesbrc`, `ordenesbsas`, `ordenesbrcantiguas`, `ordenesbsasantiguas`). Los scripts de esquema van en `ReparsoftCliente/sql/` (ej. `indices_rendimiento.sql`: índices de fechas/EstadoComercial + FULLTEXT para las 4 bases — `FechaEntrada`, `FechadeDiagnostico`, `FechAceptacion` son la base de las estadísticas con rangos `MAKEDATE(?,1)` reemplazando `YEAR()= ?` no-sargable).

## Key dependencies (in ReparsoftCliente/lib/)

mysql-connector-j-8.4.0, jasperreports-6.21.3, poi-5.2.3, openpdf-1.3.30, JTattoo-1.6.11, ucanaccess-5.0.1, gson-2.10.1

## Development commands

```bash
# Compile + jar reproducible (thin jar: Main-Class main.Main + Class-Path libs/*)
# En Eclipse: clic derecho en build.xml -> Run As -> Ant Build (default = exe: jar + libs + exe)
# Cerrar ReparSoft.exe antes de exe/installer (archivo bloqueado)
java -cp "lib/ant-launcher-1.10.12.jar" org.apache.tools.ant.launch.Launcher -f build.xml
# Targets: clean | compile | compile-tests | jar (dist/ReparSoft.jar + dist/libs/) | dist (copia a instalador) | exe (launch4j) | installer (NSIS, ~2 min) | actualizador (NSIS parche)
# Tests: build_test.cmd compile | compile-tests | test
# Run (desde la raiz del proyecto, requiere reportes/ en el CWD)
java -cp "dist/ReparSoft.jar;dist/libs/*" main.Main
# MySQL backup
mysqldump -u root -p reparsoft > sql/Backup_$(date +%Y-%m-%d).sql
```

## Empaquetado (thin jar) — IMPORTANTE

- `build.xml` compila `src` (UTF-8, javac forkeado de `C:\jdk8u422-b05`) a `bin`, copia `img/` y `Diccionario/`, arma `dist/ReparSoft.jar` con manifest `Main-Class: main.Main` + `Class-Path: libs/...` (74 jars, auto-generado). El default `exe` regenera además `ReparSoft.exe` vía `launch4j\launch4jc.exe`; `installer` corre makensis (NSIS).
- `dist/` está git-ignored; `bin/` está trackeado y `clean` conserva `bin/.gitignore`.
- La carpeta `libs/` del jar = todos los jars de `lib/` + `lib-test/` (sin itext). NO copiar `lib/*` a mano: usar `ant dist`.
- `itext-2.1.7.js8.jar` NO debe volver al classpath: duplica `com.lowagie.*` de openpdf y rompe el export PDF del remito (NoSuchMethodError). Todos los jars viven en `lib/` (jgoodies, javax.mail, miglayout, fuentes incluidas).

## Conventions

- `try-with-resources` for PreparedStatement/ResultSet
- `LogDAO.error()` / `LogDAO.info()` / `LogDAO.warning()` instead of `e.printStackTrace()`
- SQL queries as `private static final String` at top of each DAO impl
- All queries parameterized with `?`
- No unnecessary comments; Spanish naming for business concepts
- Imports order: java.*, javax.*, libraries, project

## Critical — DO NOT MODIFY carelessly

- Migration tools: `VentanaMigracion.java`, `MigracionController.java`, `ConfigMigracion.java` (Access→MySQL migration with blindado IDs)
- Backup: `ControladorBackup.java` (MySQL backup/restore)
- Must maintain backwards compatibility with these classes.

## Reports

6 `.jrxml` templates in `reportes/`: Presupuesto, RemitoComun, RemitoPreImpreso, ResumenTecnico, ReporteRegistroEntrada2, Presupuesto_1 (in reportes/MyReports/)

Visualización: todos los reportes se muestran con el visor interno JasperViewer (NO se abre el PDF con el lector externo; el PDF igual se guarda a disco para mail/WSP/archivo). `presentacion/reportes/VisorReportes.java` customiza el visor (título+ícono en ventana, zoom "página completa" al abrir vía windowOpened + doClick del toggle del toolbar, botón Guardar oculto). Si se cambia de versión de JasperReports, re-verificar esas customizaciones internas (dependen de la estructura del toolbar `net.sf.jasperreports.swing.JRViewerToolbar`).