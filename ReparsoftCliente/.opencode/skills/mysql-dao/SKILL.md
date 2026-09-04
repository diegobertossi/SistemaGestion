---
name: mysql-dao
description: Acceso a datos MySQL con patrón DAO - conexiones, PreparedStatements, transacciones y manejo de resultados
license: MIT
compatibility: opencode
metadata:
  database: MySQL 8.x
  pattern: DAO
  audience: developers
---

## Concepto

Skill para acceso a datos en Reparsoft usando MySQL 8.x con patrón DAO. La conexión usa driver `mysql-connector-j-8.4.0.jar`.

## Arquitectura DAO

```
persistencia/dao/
├── interfaz/           # Contratos (interfaces)
│   ├── ClienteDAO.java
│   ├── ReparacionDAO.java
│   └── ...
└── mysql/              # Implementaciones
    ├── ClienteDAOImpl.java
    ├── ReparacionDAOImpl.java
    └── ...
```

## Conexión Singleton

```java
// Conexion.java (Singleton)
private static Conexion instancia;
private Connection conexion;

public static Conexion getInstancia() {
    if (instancia == null) {
        instancia = new Conexion(ubicacion, esAntigua);
    }
    return instancia;
}

public Connection getConnection() {
    return conexion;
}
```

## Configuración de conexión

```java
// Las credenciales se leen de config.properties via util.Config
// (usuario de aplicacion: reparsoft_app@localhost, NO root).
props.setProperty("db.host", Config.get("db.host", "localhost"));
props.setProperty("db.port", Config.get("db.port", "3306"));
props.setProperty("db.user", Config.get("db.user", "reparsoft_app"));
props.setProperty("db.password", Config.get("db.password", ""));
props.setProperty("db.options", 
    "useUnicode=true&characterEncoding=UTF-8&" +
    "connectionCollation=utf8mb4_unicode_ci&" +
    "serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true");
```

## Patrón típico de implementación

```java
public class XXXDAOImpl implements XXXDAO {
    
    @Override
    public TipoDato obtenerPorId(int id) {
        String sql = "SELECT * FROM tabla WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private TipoDato mapearResultSet(ResultSet rs) throws SQLException {
        // Mapeo de columnas a DTO
    }
}
```

## Uso con try-with-resources

Siempre usar try-with-resources para cerrar recursos:
```java
try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
    // operaciones
}
```

## PreparedStatements obligatorios

Nunca concatenar strings en SQL. Usar siempre `?` y `setString()`, `setInt()`, etc.

## Legacy Access

El proyecto soporta bases Access legacy via UCanAccess:
- `ConectorAccess.java` maneja conexiones `.mdb`
- `VentanaMigracion.java` migra datos de Access a MySQL
