package main;

import java.io.*;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

public class Main2 {
    public static void main(String[] args) {
        try {
            // Cargar el documento existente
            //String nombreWordBase = "Documento Base.docx";
            String nombreWordBase = "Modelo Generico de informe 2023.docx"; 
            
            String documentoBase = "F:/els/Administracion/Sistema/Informes Siemens/" + nombreWordBase;

            FileInputStream fis = new FileInputStream(documentoBase); // Cambiar por la ruta correcta
            XWPFDocument existingDoc = new XWPFDocument(fis);
            fis.close();

            // Palabra a buscar y reemplazar1
            String palabraBusquedaTexto = "#cliente#";
            String palabraReemplazo = "121212";
            
            String palabraBusquedaImagen = "#picture1#";
            //String palabraReemplazo = "palabra_de_reemplazo";
            

            // Ruta de la imagen a agregar
            String rutaImagen = "F:\\Fotos equipos\\6ra20\\DSC_0912.jpg"; // Cambiar por la ruta correcta

            // Recorrer los párrafos y reemplazar la palabra
            for (XWPFParagraph paragraph : existingDoc.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null && text.contains(palabraBusquedaTexto)) {
                        run.setText(text.replace(palabraBusquedaTexto, palabraReemplazo), 0);
                    }
                }
            }

            // Recorrer las tablas y reemplazar la palabra
            for (XWPFTable table : existingDoc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String text = run.getText(0);
                                if (text != null && text.contains(palabraBusquedaTexto)) {
                                    run.setText(text.replace(palabraBusquedaTexto, palabraReemplazo), 0);
                                }
                            }
                        }
                    }
                }
            }

            // Recorrer los párrafos y reemplazar la palabra por la imagen
            for (XWPFParagraph paragraph : existingDoc.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null && text.contains(palabraBusquedaImagen)) {
                        run.setText("", 0);
                        FileInputStream imgStream = new FileInputStream(rutaImagen);
                        byte[] imageBytes = new byte[imgStream.available()];
                        imgStream.read(imageBytes);
                        imgStream.close();
                        run.addPicture(new ByteArrayInputStream(imageBytes), XWPFDocument.PICTURE_TYPE_JPEG,
                        		"DSC_0912.jpg", Units.toEMU(100), Units.toEMU(100));
                    }
                }
            }

            // Recorrer las tablas y reemplazar la palabra por la imagen
            for (XWPFTable table : existingDoc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String text = run.getText(0);
                                if (text != null && text.contains(palabraBusquedaImagen)) {
                                    run.setText("", 0);
                                    FileInputStream imgStream = new FileInputStream(rutaImagen);
                                    byte[] imageBytes = new byte[imgStream.available()];
                                    imgStream.read(imageBytes);
                                    imgStream.close();
                                    run.addPicture(new ByteArrayInputStream(imageBytes), XWPFDocument.PICTURE_TYPE_JPEG,
                                    		"DSC_0912.jpg", Units.toEMU(100), Units.toEMU(100));
                                }
                            }
                        }
                    }
                }
            }

            // Guardar el nuevo documento con otro nombre
            String nombreWordNuevo = "con_imagen.docx";
            String nuevoDocumento = "F:/els/Administracion/Sistema/Informes Siemens/" + nombreWordNuevo;

            FileOutputStream fos = new FileOutputStream(nuevoDocumento);
            existingDoc.write(fos);
            fos.close();

            existingDoc.close();

            System.out.println("Documento Word modificado y guardado con imágenes agregadas.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
