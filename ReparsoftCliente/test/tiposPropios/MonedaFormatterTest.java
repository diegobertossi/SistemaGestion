package tiposPropios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Valida el formateo/parseo de montos en pesos y dólares (formato es-AR).
 * El formateo de MonedaFormatter usa DecimalFormatSymbols es-AR explícito,
 * por lo que las aserciones son independientes del locale de la máquina.
 */
public class MonedaFormatterTest {

    private final MonedaFormatter formatter = new MonedaFormatter();

    /** Valores nulos, vacíos o cero se muestran como "$ 0,00". */
    @Test
    public void formatPeso_conValoresVaciosDevuelveCero() {
        assertEquals("$ 0,00", formatter.formatPeso(null));
        assertEquals("$ 0,00", formatter.formatPeso(""));
        assertEquals("$ 0,00", formatter.formatPeso("   "));
        assertEquals("$ 0,00", formatter.formatPeso("0"));
    }

    /** Formatea con separador de miles (punto) y decimal (coma). */
    @Test
    public void formatPeso_formateaConMilesYDecimales() {
        assertEquals("$ 1.234,56", formatter.formatPeso("1234.56"));
        assertEquals("$ 1.234,56", formatter.formatPeso("1.234,56"));
        assertEquals("$ 1.000,00", formatter.formatPeso("1000"));
        assertEquals("$ 5,50", formatter.formatPeso("5.5"));
    }

    /** Un texto no numérico se degrada a "$ 0,00" (parseAmount devuelve 0). */
    @Test
    public void formatPeso_conTextoInvalidoDegradaACero() {
        assertEquals("$ 0,00", formatter.formatPeso("abc"));
    }

    /** Valores vacíos en dólares se muestran como "U$S 0,00". */
    @Test
    public void formatDolar_conValoresVaciosDevuelveCero() {
        assertEquals("U$S 0,00", formatter.formatDolar(null));
        assertEquals("U$S 0,00", formatter.formatDolar("0"));
        assertEquals("U$S 0,00", formatter.formatDolar(""));
    }

    /** Formatea dólares con el mismo estilo de separadores. */
    @Test
    public void formatDolar_formateaConMilesYDecimales() {
        assertEquals("U$S 1.500,00", formatter.formatDolar("1500"));
        assertEquals("U$S 2.250,75", formatter.formatDolar("2250.75"));
    }

    /** Parseo formato argentino: puntos de miles, coma decimal. */
    @Test
    public void parseAmount_formatoArgentino() {
        assertEquals(1234.56, formatter.parseAmount("1.234,56"), 0.001);
        assertEquals(1234.56, formatter.parseAmount("$ 1.234,56"), 0.001);
        assertEquals(5.5, formatter.parseAmount("5,5"), 0.001);
    }

    /** Parseo formato inglés: comas de miles, punto decimal. */
    @Test
    public void parseAmount_formatoIngles() {
        assertEquals(1234.56, formatter.parseAmount("1,234.56"), 0.001);
        assertEquals(1234.56, formatter.parseAmount("1234.56"), 0.001);
    }

    /** Números sin separadores se parsean directo; símbolos y letras se eliminan. */
    @Test
    public void parseAmount_sinSeparadoresYSinSimbolo() {
        assertEquals(500.0, formatter.parseAmount("$ 500"), 0.001);
        assertEquals(1234.0, formatter.parseAmount("1234"), 0.001);
    }

    /**
     * Un punto sin coma se interpreta como decimal (formato inglés):
     * "12.345" → 12,345. Comportamiento documentado del parser actual.
     */
    @Test
    public void parseAmount_puntoUnicoSeInterpretaComoDecimal() {
        assertEquals(12.345, formatter.parseAmount("12.345"), 0.001);
    }

    /** Valores vacíos o inválidos devuelven 0.0 en vez de lanzar excepción. */
    @Test
    public void parseAmount_vaciosOInvalidosDevuelvenCero() {
        assertEquals(0.0, formatter.parseAmount(null), 0.001);
        assertEquals(0.0, formatter.parseAmount(""), 0.001);
        assertEquals(0.0, formatter.parseAmount("abc"), 0.001);
        assertEquals(0.0, formatter.parseAmount(".,"), 0.001);
    }

    /** parseAmountGuardar divide por 100: recibe el monto en centavos. */
    @Test
    public void parseAmountGuardar_divididoPor100() {
        assertEquals(12.34, formatter.parseAmountGuardar("1234"), 0.001);
        assertEquals(1234.56, formatter.parseAmountGuardar("123456"), 0.001);
        assertEquals(0.0, formatter.parseAmountGuardar("abc"), 0.001);
    }

    /** formatAmount redondea a 2 decimales; se valida por round-trip por independencia de locale. */
    @Test
    public void formatAmount_redondeaA2Decimales() {
        String formato = formatter.formatAmount(1234.56);
        String sinMiles = formato.replace(".", "").replace(",", ".");
        assertEquals(1234.56, Double.parseDouble(sinMiles), 0.001);

        assertEquals(5.0, Double.parseDouble(formatter.formatAmount(5.0).replace(",", ".")), 0.001);
    }

    /** tieneFormato detecta el símbolo de moneda. */
    @Test
    public void tieneFormato_detectaSimbolos() {
        assertTrue(formatter.tieneFormato("$ 100"));
        assertTrue(formatter.tieneFormato("U$S 50"));
        assertTrue(formatter.tieneFormato("costo $"));
        assertFalse(formatter.tieneFormato("100"));
        assertFalse(formatter.tieneFormato(""));
    }
}
