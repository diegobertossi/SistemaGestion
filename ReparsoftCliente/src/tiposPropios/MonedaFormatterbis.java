package tiposPropios;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MonedaFormatterbis {
    private NumberFormat formatter;

    public MonedaFormatterbis(String moneda) {
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
        if (amount == null || amount.trim().isEmpty()) {
            return 0.0;
        }
        // Elimina símbolos de moneda y espacios, conserva dígitos, coma y punto
        String cleanedAmount = amount.replaceAll("[^0-9.,]", "");
        
        // Detecta formato por posición del último separador
        int lastComma = cleanedAmount.lastIndexOf(',');
        int lastDot = cleanedAmount.lastIndexOf('.');
        
        if (lastComma > lastDot) {
            // Formato argentino: 1.234,56 (punto = miles, coma = decimal)
            cleanedAmount = cleanedAmount.replace(".", "");
            cleanedAmount = cleanedAmount.replace(",", ".");
        } else if (lastDot > lastComma) {
            // Formato inglés: 1,234.56 (coma = miles, punto = decimal)
            cleanedAmount = cleanedAmount.replace(",", "");
        }
        // Si no hay separadores o solo uno, parseo directo
        
        try {
            return Double.parseDouble(cleanedAmount);
        } catch (NumberFormatException e) {
            System.out.println("error numero " + cleanedAmount);
            return 0.00;
        }
    }
    
    public double parseAmountGuardar(String amount) {
        // Elimina todos los caracteres no numéricos, excepto comas y puntos
        String cleanedAmount = amount.replaceAll("[^0-9]", "");

        try {
            // Sustituye comas por puntos para obtener un formato válido para Double
        	//cleanedAmount = cleanedAmount.replace(".", ",");
        	//cleanedAmount = cleanedAmount.replace(",", ".");

            // Intenta analizar el número
        	
            return Double.parseDouble(cleanedAmount)/100;
            
        } catch (NumberFormatException e) {
        	System.out.println("error numero " + cleanedAmount);
            return 0.00; // Valor predeterminado si no se puede analizar el número
        }
    }



    public String formatAmount(double amount) {
        // Formatea el número en el formato deseado con dos decimales
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(amount);
    }



}
