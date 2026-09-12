package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class NombresArchivosTest {

    @Rule
    public TemporaryFolder carpeta = new TemporaryFolder();

    @Test
    public void pdfPresupuesto_nombreDeterministicoSinUsuarioNiFecha() {
        assertEquals("Presupuesto ELS_5001_Acme.pdf", NombresArchivos.pdfPresupuesto(5001, "Acme"));
    }

    @Test
    public void pdfRegistro_nombreDeterministicoSinUsuarioNiFecha() {
        assertEquals("ELS_5001.pdf", NombresArchivos.pdfRegistro(5001));
    }

    @Test
    public void pdfRemito_nombreDeterministicoSinUsuarioNiFecha() {
        assertEquals("12-BRC_Acme.pdf", NombresArchivos.pdfRemito("12", "BRC", "Acme"));
    }

    @Test
    public void docxInforme_nombreDeterministicoSinUsuarioNiFecha() {
        assertEquals("AV 777-ELS 5001_Acme.docx", NombresArchivos.docxInforme("777", "5001", "Acme"));
    }

    @Test
    public void nombre_noDependeDelUsuarioDelSistema() {
        String anterior = System.getProperty("user.name");
        System.setProperty("user.name", "Juan Perez");
        try {
            assertEquals("ELS_7.pdf", NombresArchivos.pdfRegistro(7));
        } finally {
            if (anterior != null) {
                System.setProperty("user.name", anterior);
            }
        }
    }

    @Test
    public void localizarMasReciente_devuelveElMasNuevoSinConfundirEls() throws Exception {
        File viejo = carpeta.newFile("Presupuesto ELS_5001_Acme_ana_20200101-100000-000.pdf");
        File legacy = carpeta.newFile("Presupuesto ELS_5001_Acme.pdf");
        File nuevo = carpeta.newFile("Presupuesto ELS_5001_Acme_beto_20260101-100000-000.pdf");
        carpeta.newFile("Presupuesto ELS_50010_Acme_x_20270101-100000-000.pdf");
        assertTrue(viejo.setLastModified(1000000L));
        assertTrue(legacy.setLastModified(2000000L));
        assertTrue(nuevo.setLastModified(3000000L));

        File hallado = NombresArchivos.localizarMasReciente(carpeta.getRoot().getAbsolutePath(),
                "Presupuesto ELS_5001_");
        assertNotNull(hallado);
        assertEquals(nuevo.getName(), hallado.getName());
    }

    @Test
    public void localizarMasReciente_sinCoincidenciaONula_devuelveNull() throws Exception {
        assertNull(NombresArchivos.localizarMasReciente(carpeta.getRoot().getAbsolutePath(),
                "Presupuesto ELS_9999_"));
        assertNull(NombresArchivos.localizarMasReciente(null, "x"));
        assertNull(NombresArchivos.localizarMasReciente(carpeta.getRoot().getAbsolutePath(), null));
        assertNull(NombresArchivos.localizarMasReciente(
                new File(carpeta.getRoot(), "noexiste").getAbsolutePath(), "x"));
    }
}
