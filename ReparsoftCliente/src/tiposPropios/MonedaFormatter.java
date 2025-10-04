package tiposPropios;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MonedaFormatter {
    private NumberFormat pesoFormatter;
    private NumberFormat dolarFormatter;

    public MonedaFormatter() {
        // Configura el formato de moneda argentina con el símbolo "$" seguido de un espacio
        DecimalFormatSymbols pesoSymbols = new DecimalFormatSymbols(new Locale("es", "AR"));
        pesoSymbols.setCurrencySymbol("$ ");
        pesoSymbols.setGroupingSeparator('.');
        pesoSymbols.setMonetaryDecimalSeparator(',');
        String currencySymbolP = "$ ";
        pesoFormatter = new DecimalFormat(currencySymbolP + "#,##0.00", pesoSymbols);

        // Configura el formato de dólar estadounidense con el símbolo "$" y sin espacio
        DecimalFormatSymbols dolarSymbols = new DecimalFormatSymbols(new Locale("es", "AR"));
        dolarSymbols.setCurrencySymbol("U$S ");
        dolarSymbols.setGroupingSeparator('.');
        dolarSymbols.setMonetaryDecimalSeparator(',');
        String currencySymbolD = "U$S ";
        dolarFormatter = new DecimalFormat(currencySymbolD + "#,##0.00", dolarSymbols);
    }

    public String formatPeso(String amount) {
    	

        if (amount == null || amount.trim().isEmpty() || amount.equals("0")) {
            return "$ 0,00";
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
            return "U$S 0,00";
        }

        try {
            double parsedAmount = parseAmount(amount);
            // Formatea el número en dólares estadounidenses y lo devuelve como una cadena
            return dolarFormatter.format(parsedAmount);
        } catch (NumberFormatException e) {
            return "Formato de número inválido";
        }
    }

    
    
    

 // Java
 public double parseAmount(String amount) {
     if (amount == null || amount.trim().isEmpty()) {
         return 0.0;
     }
     // Elimina símbolos de moneda y espacios
     String cleanedAmount = amount.replaceAll("[^0-9.,]", "");
     // Si hay más de una coma, la última es el decimal, las otras son miles
     int lastComma = cleanedAmount.lastIndexOf(',');
     int lastDot = cleanedAmount.lastIndexOf('.');
     if (lastComma > lastDot) {
         // Formato argentino: 1.234,56
         cleanedAmount = cleanedAmount.replace(".", ""); // quita puntos de miles
         cleanedAmount = cleanedAmount.replace(",", "."); // decimal a punto
     } else if (lastDot > lastComma) {
         // Formato inglés: 1,234.56
         cleanedAmount = cleanedAmount.replace(",", ""); // quita comas de miles
         // el punto ya es decimal
     }
     try {
         return Double.parseDouble(cleanedAmount);
     } catch (NumberFormatException e) {
         System.out.println("error numero " + cleanedAmount);
         return 0.00;
     }
 }

//    public double parseAmount(String amount) {
//        // Elimina todos los caracteres no numéricos, excepto comas y puntos
//        String cleanedAmount = amount.replaceAll("[^0-9,.]", "");
//
//        try {
//            // Sustituye comas por puntos para obtener un formato válido para Double
//            cleanedAmount = cleanedAmount.replace(",", ".");
//
//            // Intenta analizar el número
//            return Double.parseDouble(cleanedAmount);
//
//        } catch (NumberFormatException e) {
//            return 0.00; // Valor predeterminado si no se puede analizar el número
//        }
//    }
//    
    

    public double parseAmountGuardar(String amount) {
        // Elimina todos los caracteres no numéricos, excepto comas y puntos
        String cleanedAmount = amount.replaceAll("[^0-9]", "");

        try {
            return Double.parseDouble(cleanedAmount) / 100;

        } catch (NumberFormatException e) {
            return 0.00; // Valor predeterminado si no se puede analizar el número
        }
    }

    public String formatAmount(double amount) {
        // Formatea el número en el formato deseado con dos decimales
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(amount);
    }

    public boolean tieneFormato(String input) {
        // Expresión regular para el formato de pesos argentinos o dólares estadounidenses
        return input.contains("$") || input.contains("U$S");
    }


}

