package util;

import java.io.File;

/**
 * Construccion centralizada de los nombres de archivo de los documentos que la
 * app genera en carpetas compartidas (PDFs de presupuesto/remito/registro e
 * informes Word). Los nombres son deterministicos (sin usuario ni fecha):
 * regenerar el mismo documento sobrescribe el archivo anterior.
 *
 * Quien genera y quien adjunta/envia deben usar estos mismos metodos (o el
 * path devuelto por getPdfGuardado()), nunca reconstruir el nombre a mano.
 * Para reenvios posteriores usar {@link #localizarMasReciente(String, String)}.
 */
public final class NombresArchivos {

    private NombresArchivos() {
    }

    public static String pdfPresupuesto(int els, String cliente) {
        return "Presupuesto ELS_" + els + "_" + cliente + ".pdf";
    }

    public static String pdfRegistro(int els) {
        return "ELS_" + els + ".pdf";
    }

    public static String pdfRemito(String numeroRemito, String ubicacionRemito, String cliente) {
        return numeroRemito + "-" + ubicacionRemito + "_" + cliente + ".pdf";
    }

    public static String docxInforme(String aviso, String els, String cliente) {
        return "AV " + aviso + "-" + "ELS " + els + "_" + cliente + ".docx";
    }

    /**
     * Para reenvios: localiza en la carpeta el archivo mas reciente cuyo nombre
     * empiece con el prefijo dado. El prefijo debe incluir el tramo completo
     * hasta el "_" posterior al identificador (ej. "Presupuesto ELS_5001_") para
     * no confundir ELS 5001 con 50010. Devuelve null si no hay coincidencia.
     */
    public static File localizarMasReciente(String carpeta, String prefijo) {
        if (carpeta == null || prefijo == null) {
            return null;
        }
        File dir = new File(carpeta);
        File[] candidatos = dir.listFiles((d, nombre) -> nombre.startsWith(prefijo));
        if (candidatos == null || candidatos.length == 0) {
            return null;
        }
        File mejor = candidatos[0];
        for (int i = 1; i < candidatos.length; i++) {
            if (candidatos[i].lastModified() > mejor.lastModified()) {
                mejor = candidatos[i];
            }
        }
        return mejor;
    }
}
