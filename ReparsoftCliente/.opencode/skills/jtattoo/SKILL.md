---
name: jtattoo
description: Configuración de JTattoo LookAndFeel para interfaces Java Swing con temas Aluminium y otros
license: MIT
compatibility: opencode
metadata:
  library: JTattoo-1.6.11
  themes: Aluminium, Mint, Graphite, Texture
  audience: developers
---

## Concepto

Skill para configuración de LookAndFeel JTattoo en Reparsoft. El proyecto usa el tema Aluminium para una apariencia moderna en las interfaces Swing.

## Dependencias

```
lib/JTattoo-1.6.11.jar
```

## Configuración en Main.java

```java
// Main.java - configuración al inicio de la aplicación
public static void main(String[] args) {
    // Precarga del driver JDBC
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    // Establecer LookAndFeel JTattoo
    try {
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Iniciar aplicación...
    VentanaUbicacionBaseDeDatos ventana = new VentanaUbicacionBaseDeDatos();
    ControladorUbicacionBase controlador = new ControladorUbicacionBase(ventana);
}
```

## Temas JTattoo disponibles

```java
// Aluminium
"com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"

// Otros temas (no usados en Reparsoft pero disponibles)
"com.jtattoo.plaf.mint.MintLookAndFeel"
"com.jtattoo.plaf.graphite.GraphiteLookAndFeel"
"com.jtattoo.plaf.texture.TextureLookAndFeel"
"com.jtattoo.plaf.aero.AeroLookAndFeel"
```

## Consideraciones

- El LookAndFeel DEBE establecerse antes de crear cualquier componente Swing
- Se establece en `Main.java` antes de instanciar ventanas
- Si falla la carga, se usa el LookAndFeel por defecto
- El tema Aluminium proporciona una apariencia metálica/industrial adecuada para software técnico

## Otros LookAndFeels en lib/

El proyecto también tiene disponibles (no activos):
- Synthetica themes (BlackEye, BlackMoon, BlueIce, etc.)
- LiquidLNFTM
- MigLayout para layouts avanzados
