# AGENTS.md - SistemaGestion / ReparsoftCliente

Sistema de gestión para talleres de reparación electrónica. Desktop Java Swing app (Eclipse project).

## Stack

Java 8, Swing (JTattoo Aluminium L&F), MySQL 8, JasperReports 6.21, Apache POI 5.2, iText/OpenPDF, JavaMail, JOrtho (spellcheck).

## Entry point

`ReparsoftCliente/src/main/Main.java` → splash → `VentanaUbicacionBaseDeDatos` (DB selection) → `VentanaLogin` → `VistaPrincipal`

## Structure (src/ top-level dirs under ReparsoftCliente/)

`consumoAPI/ dto/ mails/ main/ modelo/ org/ persistencia/ presentacion/ tiposPropios/ util/ vista/ VistaPropias/`

Notable: `org/eclipse/wb/swing/FocusTraversalOnArray.java` (WindowBuilder helper), `util/GeneradorPlanPDF.java`

## Architecture

- **MVC**: Controladores (13 files in `presentacion/controlador/`) implement `ActionListener`; vistas in `presentacion/vista/`
- **DAO**: interfaces in `persistencia/dao/interfaz/`, MySQL impls in `persistencia/dao/mysql/` (16 files)
- **Gestores**: business logic in `presentacion/controlador/gestores/` (12 files)
- **Singleton**: `Conexion.java` for MySQL connection
- **Spanish naming** for business concepts (clientes, equipos, presupuestos, remitos...)

## DB connection (Conexion.java defaults)

```
host=localhost, port=3306, user=root, password=root
```
DB names: `ordenesbrc` / `ordenesbsas` (normal), `ordenesbrcantiguas` / `ordenesbsasantiguas` (legacy). Also supports Access via UCanAccess.

## Key dependencies (in ReparsoftCliente/lib/)

mysql-connector-j-8.4.0, jasperreports-6.21.3, poi-5.2.3, openpdf-1.3.30, JTattoo-1.6.11, ucanaccess-5.0.1, gson-2.10.1

## Development commands

```bash
# Compile + jar reproducible (thin jar: Main-Class main.Main + Class-Path libs/*)
# En Eclipse: clic derecho en build.xml -> Run As -> Ant Build (default = exe: jar + libs + exe)
# Cerrar ReparSoft.exe antes de exe/installer (archivo bloqueado)
java -cp "lib/ant-launcher-1.10.12.jar" org.apache.tools.ant.launch.Launcher -f build.xml
# Targets: clean | compile | compile-tests | jar (dist/ReparSoft.jar + dist/libs/) | dist (copia a instalador) | exe (launch4j) | installer (NSIS, ~2 min)
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