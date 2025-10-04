
package presentacion.vista;

import javax.swing.*;
import java.awt.*;

public class PopupProgresoBackup {
    private JDialog popup;
    private JLabel label;
    private JProgressBar progressBar;

    public PopupProgresoBackup(Window parent, String mensaje) {
        popup = new JDialog(parent);
        popup.setTitle("Procesando");
        popup.setModal(false);
        popup.setSize(350, 120);
        popup.setResizable(false); 
        popup.setLocationRelativeTo(parent);

        label = new JLabel(mensaje, SwingConstants.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(300, 18));
        progressBar.setMinimumSize(new Dimension(300, 18));
        progressBar.setMaximumSize(new Dimension(300, 18));
        progressBar.setForeground(new Color(100, 149, 237)); // Azul claro

        JPanel barraPanel = new JPanel(new GridBagLayout());
        barraPanel.add(progressBar);
        barraPanel.setOpaque(false);

        popup.setLayout(new BorderLayout());
        popup.add(label, BorderLayout.NORTH);
        popup.add(barraPanel, BorderLayout.CENTER);
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> popup.setVisible(true));
    }

    public void actualizarProgreso(int valor) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(valor));
    }

    public void cerrar() {
        SwingUtilities.invokeLater(() -> popup.dispose());
    }
}
