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
# Compile
javac -d bin -cp "lib/*" -sourcepath src src/main/Main.java
# Run
java -cp "bin;lib/*" main.Main
# MySQL backup
mysqldump -u root -p reparsoft > sql/Backup_$(date +%Y-%m-%d).sql
```

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