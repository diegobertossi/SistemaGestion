package tiposPropios;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MonedaFormatter {
    private DecimalFormat pesoFormatter;
    private DecimalFormat dolarFormatter;

    public MonedaFormatter() {
        // Configura el formato de moneda argentina con el símbolo "$" seguido de un espacio
        DecimalFormatSymbols pesoSymbols = new DecimalFormatSymbols(Locale.US);
        pesoSymbols.setCurrencySymbol("$ ");
        pesoFormatter = new DecimalFormat("#,##0.00", pesoSymbols);

        // Configura el formato de dólar estadounidense con el símbolo "$" y sin espacio
        DecimalFormatSymbols dolarSymbols = new DecimalFormatSymbols(Locale.US);
        dolarSymbols.setCurrencySymbol("$ ");
        dolarFormatter = new DecimalFormat("#,##0.00", dolarSymbols);
    }

    public String formatPeso(String amount) {
        if (amount == null || amount.trim().isEmpty() || amount.equals("0")) {
            return "$ 0.00";
        }

        try {
            double parsedAmount = parseAmount(amount);
            // Formatea el número en pesos argentinos y lo devuelve como una cadena
            return pesoFormatter.format(parsedAmount);
        } catch (NumberFormatException e) {
            return "Formato de número inválido";
        }
    }

    public String formatDolar(String amount) {
        if (amount == null || amount.trim().isEmpty() || amount.equals("0")) {
            return "$ 0.00";
        }

        try {
            double parsedAmount = parseAmount(amount);
            // Formatea el número en dólares estadounidenses y lo devuelve como una cadena
            return dolarFormatter.format(parsedAmount);
        } catch (NumberFormatException e) {
            return "Formato de número inválido";
        }
    }

    public double parseAmount(String amount) {
        // Elimina los caracteres no numéricos y reemplaza la coma por el punto como separador decimal
        String cleanedAmount = amount.replaceAll("[^0-9.,]", "").replace(",", ".");

        // Intenta analizar el número
        return Double.parseDouble(cleanedAmount);
    }
}
