---
name: jasperreports
description: Generación de reportes con JasperReports 6.21.3 - diseño de JRXML, compilación y exportación a PDF/Excel
license: MIT
compatibility: opencode
metadata:
  library: jasperreports-6.21.3
  format: PDF, HTML, Excel, RTF
  audience: developers
---

## Concepto

Skill para generación de reportes en Reparsoft usando JasperReports 6.21.3. Los reportes están en la carpeta `reportes/` con extensión `.jrxml`.

## Dependencias

```xml
jasperreports-6.21.3.jar
openpdf-1.3.30.jaspersoft.2.jar (para PDF)
commons-beanutils-1.9.x.jar
commons-collections-3.2.x / 4.x
commons-digester-2.1.jar
commons-logging-1.1.x / 1.2.jar
```

## Flujo típico de generación

```java
// 1. Cargar y compilar el reporte
JasperReport reporte = JasperCompileManager.compileReport("reportes/miReporte.jrxml");

// 2. Preparar parámetros
Map<String, Object> parametros = new HashMap<>();
parametros.put("titulo", "Reporte de Reparaciones");

// 3. Ejecutar reporte
JasperPrint print = JasperFillManager.fillReport(reporte, parametros, conexion);

// 4. Exportar
JasperExportManager.exportReportToPdfFile(print, "salida.pdf");
```

## Ubicación de reportes

```
ReparsoftCliente/
└── reportes/
    ├── ReportePresupuesto.jrxml
    ├── ReporteRemito.jrxml
    └── ... (plantillas JasperReports)
```

## Parámetros comunes

Los reportes reciben parámetros desde los controladores para filtrado:
- Fechas de inicio/fin
- IDs de cliente, sucursal, técnico
- Estados de reparación

## Exportación

```java
// PDF
JasperExportManager.exportReportToPdfFile(print, "archivo.pdf");

// Excel
JRXlsxExporter exporter = new JRXlsxExporter();
exporter.setParameter(JRXlsxExporterParameter.JASPER_PRINT, print);
exporter.setParameter(JRXlsxExporterParameter.OUTPUT_FILE, new File("archivo.xlsx"));
exporter.exportReport();
```

## Consideraciones

- Usar OpenPDF para generación de PDF cuando JasperReports no esté disponible
- Las fuentes personalizadas están en `Fonts/` (Roboto, Cambria)
- Parámetros de conexión obtenidos de `Conexion.getInstancia().getConnection()`
