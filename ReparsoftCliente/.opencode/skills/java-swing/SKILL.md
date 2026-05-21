---
name: java-swing
description: Desarrollo de interfaces gráficas Java Swing con patrones MVC, ActionListeners y componentes personalizados
license: MIT
compatibility: opencode
metadata:
  language: Java
  framework: Swing
  audience: developers
---

## Concepto

Skill para desarrollo de aplicaciones Java Swing en Reparsoft. Este proyecto usa Swing con JTattoo LookAndFeel para interfaces de escritorio en un sistema de gestión de taller de reparaciones.

## Patrones de arquitectura

- **MVC con Controladores ActionListener**: Cada ventana tiene su controlador que implementa `ActionListener`
- **DTOs inmutables**: Transferencia de datos entre capas (presentacion ↔ persistencia)
- **Singleton**: Conexión a base de datos y servicios compartidos

## Estructura típica de un controlador

```java
public class ControladorXXX implements ActionListener {
    private VentanaXXX vista;
    private ModeloXXX modelo;

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "GUARDAR": guardar(); break;
            case "ELIMINAR": eliminar(); break;
            case "ACTUALIZAR": actualizar(); break;
        }
    }
}
```

## Convenciones de código

- Paquetes en español para conceptos de negocio
- Nombres de clases: UpperCamelCase
- Nombres de métodos: lowerCamelCase
- Nombres descriptivos: `ventanaListadoReparaciones` en lugar de `vLR`
- Sin comentarios innecesarios

## Ventanas comunes

- `VentanaClientes`, `VentanaEquipos`, `VentanaPresupuestos`, `VentanaRemitos`, `VentanaSalidas`
- `VentanaLogin`, `VistaPrincipal`, `VentanaBusqueda`
- `VentanaListadoReparaciones`, `VentanaRolesUsuarios`, `VentanaPermisos`

## Componentes personalizados en `VistaPropias/`

- `JTextString`, `JTextNum`, `JTextDouble`: Campos de texto especializados
- `JTextAreaCustom`: Áreas de texto con características específicas
- `CellRendererTablaXXX`: Renderizadores para tablas JTable
- `TablaFiltros`: Tabla con capacidad de filtrado
- `AutoCompletarComboBox`: Combos con autocompletado

## Flujo de arranque

```
Main.java
  → Precarga driver JDBC (Class.forName)
  → UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel")
  → VentanaUbicacionBaseDeDatos (selección MySQL o Access)
      → VentanaLogin (autenticación)
          → VistaPrincipal (menú según permisos)
```

## Ejemplo de creación de ventana

```java
// En ControladorPrincipal.java
private VentanaEquipos ventanaEquipos;

public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("EQUIPOS")) {
        if (ventanaEquipos == null) {
            ventanaEquipos = new VentanaEquipos();
            new ControladorReparacion(ventanaEquipos, modelo);
        }
        ventanaEquipos.mostrar();
    }
}
```

## Consideraciones

- El EDT (Event Dispatch Thread) debe mantenerse responsivo
- Operaciones pesadas van en hilos separados
- Usar `JOptionPane` para diálogos simples
- LookAndFeel se establece en `Main.java` antes de crear ventanas
