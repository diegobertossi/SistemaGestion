---
name: poi-excel
description: Generación y lectura de archivos Excel usando Apache POI 5.2.3 - Workbook, Sheet, Row, Cell styling
license: MIT
compatibility: opencode
metadata:
  library: poi-5.2.3
  formats: xlsx, xls
  audience: developers
---

## Concepto

Skill para manipulación de archivos Excel en Reparsoft usando Apache POI 5.2.3. Usado para exportar reportes, listados y datos a formato spreadsheet.

## Dependencias

```xml
lib/poi-5.2.3.jar
lib/poi-ooxml-5.2.3.jar
lib/poi-ooxml-lite-5.2.3.jar
lib/commons-codec-1.15.jar
lib/commons-compress-1.21.jar
lib/commons-io-2.11.0.jar
```

## Creación básica de workbook

```java
// Crear workbook
Workbook workbook = new XSSFWorkbook(); // .xlsx
// O para .xls legacy:
Workbook workbook = new HSSFWorkbook();

// Crear hoja
Sheet hoja = workbook.createSheet("Nombre Hoja");

// Crear fila y celdas
Row fila = hoja.createRow(0);
Cell celda = fila.createCell(0);
celda.setCellValue("Título");
```

## Escritura de datos

```java
// Desde una lista de DTOs
for (int i = 0; i < lista.size(); i++) {
    DTO dto = lista.get(i);
    Row row = sheet.createRow(i + 1);
    row.createCell(0).setCellValue(dto.getId());
    row.createCell(1).setCellValue(dto.getNombre());
}
```

## Estilos de celdas

```java
// Crear estilo
CellStyle estilo = workbook.createCellStyle();
Font fuente = workbook.createFont();
fuente.setBold(true);
estilo.setFont(fuente);

// Aplicar a celda
celda.setCellStyle(estilo);

// Anchos automáticos
hoja.autoSizeColumn(0);
hoja.autoSizeColumn(1);
```

## Escritura a archivo

```java
try (FileOutputStream out = new FileOutputStream("archivo.xlsx")) {
    workbook.write(out);
}
workbook.close();
```

## Uso en Reparsoft

La clase `GestorArchivosExcel.java` encapsula la lógica de exportación:
- `exportarListadoReparaciones()`
- `exportarClientes()`
- `exportarPresupuestos()`

## Formatos soportados

| Formato | Clase | Extensión |
|---------|-------|-----------|
| Excel 2007+ | `XSSFWorkbook` | .xlsx |
| Excel 97-2003 | `HSSFWorkbook` | .xls |
