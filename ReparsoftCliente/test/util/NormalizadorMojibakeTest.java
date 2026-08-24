package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Verifica la inversión del doble-encoding UTF-8 leído como CP850: la rutina
 * solo debe tocar cadenas con síntomas de mojibake y nunca debe romper cadenas
 * sanas. Todos los caracteres no-ASCII se escriben como escapes \\uXXXX para
 * que el test sea independiente de la codificación del archivo fuente.
 */
public class NormalizadorMojibakeTest {

    // "├" (U+251C) + "│" (U+2502): bytes CP850 C3 B3 = UTF-8 de 'ó' (U+00F3)
    private static final String CLIMATIZACION_DANIADO =
            "Stec Climatizaci\u251C\u2502n";
    private static final String CLIMATIZACION_SANO =
            "Stec Climatizaci\u00F3n";

    // "├" (U+251C) + "▒" (U+2592): CP850 C3 B1 = UTF-8 de 'ñ' (U+00F1)
    private static final String NIE_DANIADO = "a\u251C\u2592o";
    private static final String NIE_SANO = "a\u00F1o";

    // "ÔÇ£" = "Ô"(U+00D4) "Ç"(U+00C7) "£"(U+00A3): CP850 E2 80 9C = UTF-8 de “
    // "ÔÇØ" = "Ô"(U+00D4) "Ç"(U+00C7) "Ø"(U+00D8): CP850 E2 80 9D = UTF-8 de ”
    private static final String COMILLAS_ABIERTA_DANIADA =
            "\u00D4\u00C7\u00A3Hola\u00D4\u00C7\u00D8";
    private static final String COMILLAS_SANO = "\u201CHola\u201D";

    @Test
    public void reparar_nombreConTildeDaniado_recuperaTilde() {
        assertEquals(CLIMATIZACION_SANO, NormalizadorMojibake.reparar(CLIMATIZACION_DANIADO));
    }

    @Test
    public void reparar_enieDaniada_recuperaEnie() {
        assertEquals(NIE_SANO, NormalizadorMojibake.reparar(NIE_DANIADO));
    }

    @Test
    public void reparar_comillasTipograficasDaniadas_recuperaComillas() {
        assertEquals(COMILLAS_SANO, NormalizadorMojibake.reparar(COMILLAS_ABIERTA_DANIADA));
    }

    @Test
    public void reparar_textoSanoConTilde_noLoToca() {
        assertEquals(CLIMATIZACION_SANO, NormalizadorMojibake.reparar(CLIMATIZACION_SANO));
    }

    @Test
    public void reparar_null_devuelveNull() {
        assertNull(NormalizadorMojibake.reparar(null));
    }

    @Test
    public void reparar_vacio_devuelveVacio() {
        assertEquals("", NormalizadorMojibake.reparar(""));
    }

    @Test
    public void reparar_textoPlanoAscii_noLoToca() {
        assertEquals("abc123", NormalizadorMojibake.reparar("abc123"));
    }
}
