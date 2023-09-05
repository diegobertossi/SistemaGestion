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
        // Elimina todos los caracteres no numéricos, pero conserva los ceros iniciales
        StringBuilder cleanedAmount = new StringBuilder();
        boolean leadingZero = true;

        for (char c : amount.toCharArray()) {
            if (Character.isDigit(c)) {
                if (leadingZero && c == '0') {
                    // Ignorar ceros iniciales
                    continue;
                }
                cleanedAmount.append(c);
                leadingZero = false;
            }
        }

        try {
            // Intenta analizar el número, ahora sin caracteres no numéricos y sin eliminar los ceros iniciales
            return Double.parseDouble(cleanedAmount.toString());
        } catch (NumberFormatException e) {
            return 0.00; // Valor predeterminado si no se puede analizar el número
        }
    }


}
