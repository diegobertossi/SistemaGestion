package tiposPropios;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MonedaFormatter {
    private NumberFormat formatter;

    public MonedaFormatter(String moneda) {
        // Determina el símbolo y el formato según la moneda
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        String currencySymbol = "$ "; // Símbolo predeterminado

        if ("dolar".equalsIgnoreCase(moneda)) {
            currencySymbol = "U$S ";
        } else if ("peso".equalsIgnoreCase(moneda)) {
            currencySymbol = "$ ";
        }

        formatter = new DecimalFormat(currencySymbol + "#,##0.00", symbols);
    }

    public String format(String amount) {
        if (amount == null || amount.trim().isEmpty() || amount.equals("0")) {
            return "$ 0.00"; // Valor predeterminado
        }

        try {
            double parsedAmount = parseAmount(amount);
            // Formatea el número y lo devuelve como una cadena
            return formatter.format(parsedAmount);
        } catch (NumberFormatException e) {
            return "Formato de número inválido";
        }
    }

    public double parseAmount(String amount) {
        // Elimina los caracteres no numéricos y la coma
        String cleanedAmount = amount.replaceAll("[^0-9.]", "");

        try {
            // Intenta analizar el número y divide por 100 para obtener dos decimales
            return Double.parseDouble(cleanedAmount);
        } catch (NumberFormatException e) {
            return 0.00; // Valor predeterminado si no se puede analizar el número
        }
    }

    public String formatAmount(double amount) {
        // Formatea el número en el formato deseado con dos decimales
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(amount);
    }



}
