package tiposPropios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Valida la variante MonedaFormatterbis.
 * El formato de salida depende del locale por defecto de la máquina
 * (DecimalFormatSymbols(Locale.getDefault())), por lo que solo se asertan
 * comportamientos deterministas: defaults, parseo y redondeo.
 */
public class MonedaFormatterbisTest {

    /** Constructor con moneda dolar usa símbolo "U$S " (el separador de miles impide contains("1500")). */
    @Test
    public void formatoDolar_incluyeSimbolo() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("dolar");
        String formato = formatter.format("1500");
        assertTrue(formato.startsWith("U$S "));
        assertTrue(formato.contains("500"));
    }

    /** Constructor con moneda peso (o cualquier otra) usa símbolo "$ ". */
    @Test
    public void formatoPeso_incluyeSimbolo() {
        MonedaFormatterbis peso = new MonedaFormatterbis("peso");
        assertTrue(peso.format("1500").startsWith("$ "));

        MonedaFormatterbis porDefecto = new MonedaFormatterbis("euro");
        assertTrue(porDefecto.format("1500").startsWith("$ "));
    }

    /** Valor nulo, vacío o cero produce el default literal "$ 0.00" (comportamiento actual). */
    @Test
    public void format_valoresVaciosDevuelvenDefaultLiteral() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        assertEquals("$ 0.00", formatter.format(null));
        assertEquals("$ 0.00", formatter.format(""));
        assertEquals("$ 0.00", formatter.format("0"));
    }

    /** Un texto no numérico degrada a 0 (parseAmount atrapa la excepción). */
    @Test
    public void format_conTextoInvalidoDegradaACero() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        assertTrue(formatter.format("abc").contains("0,0") || formatter.format("abc").contains("0.0"));
    }

    /**
     * Comportamiento corregido: ahora maneja separadores de miles en ambos formatos
     * (argentino 1.234,56 e inglés 1,234.56), igual que MonedaFormatter.
     */
    @Test
    public void parseAmount_conPuntosDeMilesFormatoArgentino() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        assertEquals(1234.56, formatter.parseAmount("1.234,56"), 0.001);
        assertEquals(1234.56, formatter.parseAmount("$ 1.234,56"), 0.001);
    }

    /** Formato inglés con comas de miles también funciona. */
    @Test
    public void parseAmount_conComasDeMilesFormatoIngles() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        assertEquals(1234.56, formatter.parseAmount("1,234.56"), 0.001);
    }

    /** Sin separadores de miles, la coma decimal se convierte correctamente. */
    @Test
    public void parseAmount_aceptaComaDecimal() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        assertEquals(5.5, formatter.parseAmount("5,5"), 0.001);
        assertEquals(1234.56, formatter.parseAmount("1234,56"), 0.001);
    }

    /** Texto inválido devuelve 0.0 sin lanzar excepción. */
    @Test
    public void parseAmount_invalidoDevuelveCero() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        assertEquals(0.0, formatter.parseAmount("abc"), 0.001);
    }

    /** parseAmountGuardar divide por 100 (monto en centavos). */
    @Test
    public void parseAmountGuardar_divididoPor100() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        assertEquals(12.34, formatter.parseAmountGuardar("1234"), 0.001);
        assertEquals(1234.56, formatter.parseAmountGuardar("123456"), 0.001);
        assertEquals(0.0, formatter.parseAmountGuardar("abc"), 0.001);
    }

    /** formatAmount redondea a 2 decimales (round-trip independiente del locale). */
    @Test
    public void formatAmount_redondeaA2Decimales() {
        MonedaFormatterbis formatter = new MonedaFormatterbis("peso");
        String formato = formatter.formatAmount(1234.56);
        String sinMiles = formato.replace(".", "").replace(",", ".");
        assertEquals(1234.56, Double.parseDouble(sinMiles), 0.001);
    }
}
