package presentacion.vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class VentanaDatosFacturacion extends JFrame {

    public VentanaDatosFacturacion(String clienteEquipo, String cuitCliente, String itemFactura, String presupuesto) {
        setBackground(new Color(112, 128, 144));
        setTitle("Datos para Facturación");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setFont(new Font("Cambria", Font.PLAIN, 14));
        mainPanel.setBackground(new Color(230, 230, 250));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Secciones sin scroll
        JPanel clientePanel = createSectionPanel("DATOS CLIENTE", 
                clienteEquipo + "\nCUIT: " + cuitCliente,
                cuitCliente, "COPIAR CUIT");
        
        JPanel itemPanel = createSectionPanel("ITEM FACTURA", itemFactura, 
                itemFactura, "COPIAR ÍTEM");
        
        JPanel totalPanel = createSectionPanel("TOTAL", presupuesto, 
                presupuesto, "COPIAR TOTAL");
        
        // Agregar componentes
        mainPanel.add(clientePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(itemPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(totalPanel);
        
        getContentPane().add(mainPanel);
    }
    
    private JPanel createSectionPanel(String titulo, String contenido, String textoACopiar, String textoBoton) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setFont(new Font("Cambria", Font.PLAIN, 16));
        panel.setBackground(new Color(176, 196, 222));
        
        
        
        //panel.setBorder(BorderFactory.createTitledBorder(titulo));
        
        
        // Crear borde personalizado con fuente Cambria Bold 14
        Font fuenteTitulo = new Font("Cambria", Font.BOLD, 14);
        TitledBorder border = BorderFactory.createTitledBorder(titulo);
        border.setTitleFont(fuenteTitulo);
        border.setTitleColor(Color.BLACK); // Color del texto
        panel.setBorder(border);
        
        // Área de texto no seleccionable
        JTextArea textArea = new JTextArea(contenido) {
            @Override
            public void setSelectionStart(int selectionStart) {
                super.setSelectionStart(0);
            }
            
            @Override
            public void setSelectionEnd(int selectionEnd) {
                super.setSelectionEnd(0);
            }
        };
        textArea.setForeground(new Color(0, 0, 128));
        
        // Área de texto SIN SCROLL
        
        textArea.setFont(new Font("Cambria", Font.PLAIN, 16));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(panel.getBackground());
        textArea.setRows(2); // Fijamos 2 líneas de altura
        
        // Botón de copiar (estilo mantenido)
        JButton btnCopiar = new JButton(textoBoton);
        btnCopiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCopiar.setFont(new Font("Cambria", Font.BOLD, 12));
        btnCopiar.addActionListener(e -> copiarAlPortapapeles(textoACopiar));
        
        // Panel inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(176, 196, 222));
        bottomPanel.add(btnCopiar);
        
        // Agregar componentes directamente sin JScrollPane
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void copiarAlPortapapeles(String texto) {
        StringSelection selection = new StringSelection(texto);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

//    // Ejemplo de uso
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            VentanaDatosFacturacion ventana = new VentanaDatosFacturacion(
//                "Cliente Ejemplo S.A.", 
//                "30-12345678-9", 
//                "Reparación de equipo\nModelo: ABC-1234", 
//                "2500.00"
//            );
//            ventana.setVisible(true);
//        });
//    }
}