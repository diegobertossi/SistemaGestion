package tiposPropios;// Reemplaza con tu paquete actual

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;
import java.awt.Color;

/**
 * Clase utilitaria para gestionar la fuente Cambria en toda la aplicación
 */
public class FuenteCambria {
    
    // Nombres posibles para la fuente Cambria (según el sistema)
    private static final String[] NOMBRES_CAMBRIA = {
        "Cambria", 
        "Cambria Math", 
        "Cambria Regular",
        "Cambria (Corp)"
    };
    
    private static Font fuenteRegular;
    private static Font fuenteNegrita;
    private static Font fuenteCursiva;
    private static Font fuenteNegritaCursiva;
    
    private static boolean fuenteDisponible = false;
    
    static {
        inicializarFuente();
    }
    
    /**
     * Inicializa la fuente Cambria
     */
    private static void inicializarFuente() {
        // Buscar Cambria entre las fuentes del sistema
        String[] nombresSistema = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                    .getAvailableFontFamilyNames();
        
        String nombreEncontrado = null;
        for (String nombreSistema : nombresSistema) {
            for (String nombreCambria : NOMBRES_CAMBRIA) {
                if (nombreSistema.equalsIgnoreCase(nombreCambria)) {
                    nombreEncontrado = nombreSistema;
                    break;
                }
            }
            if (nombreEncontrado != null) break;
        }
        
        if (nombreEncontrado != null) {
            // Crear la fuente base
            fuenteRegular = new Font(nombreEncontrado, Font.PLAIN, 12);
            fuenteDisponible = true;
            
            // Crear las variantes
            fuenteNegrita = fuenteRegular.deriveFont(Font.BOLD);
            fuenteCursiva = fuenteRegular.deriveFont(Font.ITALIC);
            fuenteNegritaCursiva = fuenteRegular.deriveFont(Font.BOLD | Font.ITALIC);
            
            System.out.println("Fuente Cambria encontrada: " + nombreEncontrado);
        } else {
            // Fallback a Serif si no hay Cambria
            System.err.println("Fuente Cambria no encontrada. Usando Serif como fallback.");
            fuenteRegular = new Font("Serif", Font.PLAIN, 12);
            fuenteNegrita = new Font("Serif", Font.BOLD, 12);
            fuenteCursiva = new Font("Serif", Font.ITALIC, 12);
            fuenteNegritaCursiva = new Font("Serif", Font.BOLD | Font.ITALIC, 12);
        }
    }
    
    /**
     * Obtiene la fuente regular en el tamaño especificado
     */
    public static Font getRegular(float tamaño) {
        return fuenteRegular.deriveFont(tamaño);
    }
    
    /**
     * Obtiene la fuente negrita en el tamaño especificado
     */
    public static Font getNegrita(float tamaño) {
        return fuenteNegrita.deriveFont(tamaño);
    }
    
    /**
     * Obtiene la fuente cursiva en el tamaño especificado
     */
    public static Font getCursiva(float tamaño) {
        return fuenteCursiva.deriveFont(tamaño);
    }
    
    /**
     * Obtiene la fuente negrita cursiva en el tamaño especificado
     */
    public static Font getNegritaCursiva(float tamaño) {
        return fuenteNegritaCursiva.deriveFont(tamaño);
    }
    
    /**
     * Aplica la fuente regular a un componente
     */
    public static void aplicarFuente(JComponent componente, float tamaño) {
        componente.setFont(getRegular(tamaño));
    }
    
    /**
     * Aplica la fuente negrita a un componente
     */
    public static void aplicarFuenteNegrita(JComponent componente, float tamaño) {
        componente.setFont(getNegrita(tamaño));
    }
    
    /**
     * Aplica la fuente cursiva a un componente
     */
    public static void aplicarFuenteCursiva(JComponent componente, float tamaño) {
        componente.setFont(getCursiva(tamaño));
    }
    
    /**
     * Aplica la fuente negrita cursiva a un componente
     */
    public static void aplicarFuenteNegritaCursiva(JComponent componente, float tamaño) {
        componente.setFont(getNegritaCursiva(tamaño));
    }
    
    /**
     * Verifica si Cambria está disponible
     */
    public static boolean isDisponible() {
        return fuenteDisponible;
    }
}