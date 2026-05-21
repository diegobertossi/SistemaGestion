# AGENTS.md - ReparsoftCliente

Sistema de gestión de taller de reparaciones (Reparsoft) - Aplicación de escritorio Java Swing.

## 1. Concepto y visión

Reparsoft es un sistema de gestión integral para talleres de reparación de equipos electrónicos. La aplicación permite administrar clientes, equipos, reparaciones, presupuestos, remitos, facturación y reportes. Es una aplicación de escritorio robusta con interfaz Swing que gestiona el ciclo completo de una reparación: desde el ingreso del equipo hasta la facturación y entrega al cliente.

## 2. Stack tecnológico

- **Lenguaje**: Java 8
- **Framework UI**: Swing con JTattoo (AluminiumLookAndFeel)
- **IDE**: Eclipse
- **Base de datos**: MySQL 8.x (con soporte para bases Access legacy via UCanAccess)
- **Reportes**: JasperReports 6.21.3
- **Excel**: Apache POI 5.2.3
- **PDF**: iText 2.1.7 / OpenPDF 1.3.30
- **Spell Check**: JOrtho
- **Email**: JavaMail (javax.mail)
- **Build**: Eclipse build system (compilación manual)
- **Output**: `bin/`

## 3. Estructura del proyecto

```
ReparsoftCliente/
├── src/
│   ├── main/
│   │   └── Main.java                    # Punto de entrada
│   ├── presentacion/
│   │   ├── controlador/                 # Controladores MVC
│   │   │   ├── ControladorPrincipal.java
│   │   │   ├── ControladorCliente.java
│   │   │   ├── ControladorReparacion.java
│   │   │   ├── ControladorPresupuestos.java
│   │   │   ├── ControladorSalidas.java
│   │   │   ├── ControladorListados.java
│   │   │   ├── ControladorBackup.java
│   │   │   ├── ControladorUsuLogin.java
│   │   │   ├── ControladorUsuarios.java
│   │   │   ├── ControladorBusquedas.java
│   │   │   ├── ControladorHistorialPrecios.java
│   │   │   ├── ControladorConfiguraciones.java
│   │   │   ├── ControladorUbicacionBase.java
│   │   │   └── gestores/                 # Gestores de negocio
│   │   │       ├── GestorClientesWSP.java
│   │   │       ├── GestorPresupuestoFactura.java
│   │   │       ├── GestorVisualizacionEquipos.java
│   │   │       ├── GestorVerificacionIngresoAnterior.java
│   │   │       ├── GestorRepuestos.java
│   │   │       ├── GestorListadoEquipos.java
│   │   │       ├── GestorInterfazEquipos.java
│   │   │       ├── GestorEstadosPresupuestos.java
│   │   │       ├── GestorDatos.java
│   │   │       ├── GestorBusqueda.java
│   │   │       ├── GestorArchivosExcel.java
│   │   │       └── GestorAgregarEquipo.java
│   │   └── vista/                       # Ventanas y vistas
│   │       ├── VistaPrincipal.java
│   │       ├── VentanaLogin.java
│   │       ├── VentanaClientes.java
│   │       ├── VentanaEquipos.java
│   │       ├── VentanaPresupuestos.java
│   │       ├── VentanaRemitos.java
│   │       ├── VentanaSalidas.java
│   │       ├── VentanaListadoReparaciones.java
│   │       ├── VentanaBusqueda.java
│   │       ├── VentanaBackup.java
│   │       ├── VentanaRolesUsuarios.java
│   │       ├── VentanaPermisos.java
│   │       ├── VentanaSucursales.java
│   │       └── ... (mas ventanas)
│   ├── modelo/                          # Modelo de dominio
│   │   ├── Agenda.java
│   │   ├── Permisos.java
│   │   └── ELSAnterior.java
│   ├── dto/                             # Data Transfer Objects
│   │   ├── ClienteDTO.java
│   │   ├── ReparacionDTO.java
│   │   ├── UsuarioDTO.java
│   │   ├── RemitoDTO.java
│   │   ├── RepuestosDTO.java
│   │   ├── SucursalDTO.java
│   │   ├── RolDTO.java
│   │   ├── PermisoDTO.java
│   │   ├── PantallaDTO.java
│   │   ├── ClienteWSPDTO.java
│   │   ├── FacturacionXclienteDTO.java
│   │   ├── RegistroPresupuestoDTO.java
│   │   ├── RegistroResumenTecnicoDTO.java
│   │   └── RegistroEntradaReporteDTO.java
│   ├── persistencia/
│   │   ├── conexion/
│   │   │   ├── Conexion.java            # Singleton de conexion MySQL
│   │   │   └── ConectorAccess.java      # Connector para Access legacy
│   │   └── dao/
│   │       ├── interfaz/               # Interfaces DAO
│   │       │   ├── ClienteDAO.java
│   │       │   ├── ReparacionDAO.java
│   │       │   ├── UsuarioDAO.java
│   │       │   ├── RepuestoDAO.java
│   │       │   ├── RemitoDAO.java
│   │       │   ├── RolDAO.java
│   │       │   ├── PermisoDAO.java
│   │       │   ├── PantallaDAO.java
│   │       │   ├── SucursalDAO.java
│   │       │   ├── FacturacionXclienteDAO.java
│   │       │   └── ClienteWSPDAO.java
│   │       └── mysql/                   # Implementaciones MySQL
│   │           ├── ClienteDAOImpl.java
│   │           ├── ReparacionDAOImpl.java
│   │           ├── UsuarioDAOImpl.java
│   │           ├── ReparacionQueryManager.java
│   │           ├── SQLQueries.java       # Queries SQL centralizadas
│   │           ├── LogDAO.java           # Sistema de logging
│   │           └── ... (mas implementaciones)
│   ├── mails/
│   │   └── EnviarMail.java              # Envio de emails
│   ├── consumoAPI/
│   │   └── ConsumoAPI.java             # Consumo de APIs externas
│   ├── VistaPropias/                    # Componentes Swing personalizados
│   │   ├── CellRenderer*.java
│   │   ├── JTextString.java
│   │   ├── JTextNum.java
│   │   ├── JTextDouble.java
│   │   ├── TablaFiltros.java
│   │   ├── AutoCompletarComboBox.java
│   │   └── ...
│   ├── tiposPropios/
│   │   ├── MonedaFormatter.java
│   │   ├── FuenteCambria.java
│   │   └── ...
│   └── vista/
│       └── migracion/                   # Herramientas de migracion Access->MySQL
│           ├── VentanaMigracion.java
│           ├── MigracionController.java
│           └── ConfigMigracion.java
├── lib/                                 # Librerias JAR
├── img/                                 # Recursos graficos
├── sql/                                 # Scripts SQL y backups
├── Recursos/                            # Recursos adicionales
├── reportes/                            # Plantillas JasperReports (.jrxml)
├── Fonts/                               # Fuentes personalizadas
└── .opencode/skills/                   # Skills para OpenCode
```

## 4. Convenciones y patrones

### Arquitectura general
- **Patrón MVC** con controladores que implementan `ActionListener`
- **Patrón DAO** para acceso a datos con interfaces en `interfaz/` e implementaciones en `mysql/`
- **Singleton** para Conexion y otros servicios compartidos
- **DTOs inmutables** para transferencia de datos entre capas

### Convenciones de codigo
- Paquetes en español para conceptos de negocio
- Nombres de clases: UpperCamelCase (`ControladorCliente`, `ClienteDTO`)
- Nombres de metodos: lowerCamelCase (`obtenerClientes`, `guardarReparacion`)
- Controladores como punto de union entre vistas y DAOs
- Gestores para logica de negocio compleja
- **Queries SQL en constantes `private static final String`** al inicio de cada DAO
- **Usar `try-with-resources`** para `PreparedStatement` y `ResultSet`
- **Parametrizar TODAS las queries** con `?` y `setString/setInt/etc`
- **Usar `LogDAO.error()`** en lugar de `e.printStackTrace()`

### Patrón DAO refactorizado

```java
public class XxxDAOImpl implements XxxDAO {

    private static final String INSERT = "INSERT INTO tabla(campo) VALUES(?)";
    private static final String UPDATE = "UPDATE tabla SET campo = ? WHERE id = ?";

    private Conexion conexion;

    public XxxDAOImpl(String ubicacionBase) {
        this.conexion = Conexion.getConexion(ubicacionBase);
    }

    @Override
    public boolean insert(DTO dto) {
        String sql = INSERT;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql)) {
            stmt.setString(1, dto.getCampo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LogDAO.error("Error al insertar dto", e);
            return false;
        }
    }

    @Override
    public List<DTO> readAll() {
        List<DTO> lista = new ArrayList<>();
        String sql = READ_ALL;
        try (PreparedStatement stmt = conexion.getSQLConexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearDTO(rs));
            }
        } catch (SQLException e) {
            LogDAO.error("Error al leer todos los dtos", e);
        }
        return lista;
    }

    private DTO mapearDTO(ResultSet rs) throws SQLException {
        return new DTO(...);
    }
}
```

### Gestores (gestores/)
Los gestores encapsulan logica de negocio compleja que involucra multiples operaciones. Ejemplos:
- `GestorArchivosExcel`: Generación de archivos Excel para reportes
- `GestorPresupuestoFactura`: Lógica de presupuestos y facturación
- `GestorClientesWSP`: Integración con WhatsApp

### Estados comunes de la aplicación
1. `VentanaUbicacionBaseDeDatos` - Selección de base de datos (MySQL o Access legacy)
2. `VentanaLogin` - Autenticación de usuarios
3. `VistaPrincipal` - Menú principal post-login
4. Ventanas de gestión según permisos del usuario

### Permisos y roles
- Sistema de permisos por pantalla/acción
- Roles: Administrador, Técnico, Recepcionista (configurable)
- Ver `presentacion/vista/VentanaPermisos.java` y `presentacion/vista/VentanaRolesUsuarios.java`

## 5. Flujo de arranque

```
Main.java
  → Precarga driver JDBC MySQL
  → Establece LookAndFeel JTattoo (Aluminium)
  → ControladorUbicacionBase + VentanaUbicacionBaseDeDatos
      → Selección de base de datos (MySQL o Access legacy)
          → VentanaLogin
              → Validación de credenciales
                  → VistaPrincipal (menú principal)
                      → Apertura de ventanas según permisos
```

## 6. Configuración y conexión

### Conexión MySQL (Conexion.java)
```java
// Configuración por defecto hardcodeada en props
props.setProperty("db.host", "localhost");
props.setProperty("db.port", "3306");
props.setProperty("db.user", "root");
props.setProperty("db.password", "root");
```

### Migración legacy
- `VentanaMigracion.java` permite migrar datos desde Access (.mdb/.accdb) a MySQL
- `ConectorAccess.java` maneja conexiones Access legacy
- `MigracionController.java` gestiona la migración paso a paso
- BDs destino: `ordenesbrcantiguas`, `ordenesbsasantiguas`, `ordenesbrc`, `ordenesbsas`

## 7. Dependencias clave

| Librería | Propósito |
|----------|-----------|
| `mysql-connector-j-8.4.0.jar` | Driver JDBC MySQL 8.x |
| `jasperreports-6.21.3.jar` | Generación de reportes |
| `poi-5.2.3.jar` | Lectura/escritura Excel |
| `openpdf-1.3.30.jaspersoft.2.jar` | Generación PDF |
| `javax.mail.jar` | Envío de emails |
| `jcalendar-1.4.jar` | Selector de fechas |
| `JTattoo-1.6.11.jar` | Look and Feel |
| `jortho.jar` | Corrector ortográfico |
| `ucanaccess-5.0.1.jar` | Acceso a Access legacy |

## 8. Comandos de desarrollo

### Compilación y ejecución
```bash
# Desde Eclipse: Run as > Java Application
# Main.java es el punto de entrada

# Compilación manual (desde raíz del proyecto)
javac -d bin -cp "lib/*" -sourcepath src src/main/Main.java

# Ejecución
java -cp "bin;lib/*" main.Main
```

### Backup de base de datos
```bash
# MySQL dump (ubicación configurada en Conexion)
mysqldump -u root -p reparsoft > sql/Backup_Reparsoft_$(date +%Y-%m-%d).sql
```

## 9. Reglas de estilo

- **Sin comentarios innecesarios** - No agregar comentarios a menos que explícitamente solicitados
- **Imports organizados** - Primero java.*, luego javax.*, luego librerías, finalmente del proyecto
- **Spanish naming** - Paquetes y conceptos de negocio en español
- **Singletons** - Usar para Conexion y servicios compartidos
- **Nombres descriptivos** - Preferir nombres largos claros (`ventanaListadoReparaciones`) sobre abreviaturas

## 10. Archivos de infraestructura

### LogDAO (persistencia.dao.mysql.LogDAO)
Sistema de logging centralizado:
```java
LogDAO.error("Error al insertar cliente", e);
LogDAO.info("Cliente insertado exitosamente");
LogDAO.warning("Cliente duplicado omitido");
```

### SQLQueries (persistencia.dao.mysql.SQLQueries)
Contiene todas las queries SQL centralizadas en constantes para ReparacionDAO. Mantiene consistencia entre módulos.

## 11. Migración y backup (NO MODIFICAR)

Las herramientas de migración y backup son **críticas para el negocio**:
- `VentanaMigracion.java` - Interfaz de migración Access → MySQL
- `MigracionController.java` - Lógica de migración con ID blindado
- `ConfigMigracion.java` - Configuración de conexiones
- `ControladorBackup.java` - Backup/restore de MySQL

**Cualquier refactor debe mantener backwards compatibility con estas clases.**
