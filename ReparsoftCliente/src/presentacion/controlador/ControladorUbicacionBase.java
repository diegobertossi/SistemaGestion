package presentacion.controlador;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import modelo.Agenda;
import modelo.Permisos;
import presentacion.vista.VentanaUbicacionBaseDeDatos;
import presentacion.vista.VistaPrincipal;

public class ControladorUbicacionBase implements ActionListener {

    private VentanaUbicacionBaseDeDatos vistaUbicacionBase;
    private String ubicacionBase;

    public ControladorUbicacionBase(VentanaUbicacionBaseDeDatos vistaUbicacionBaseDatos) {
        this.vistaUbicacionBase = vistaUbicacionBaseDatos;
        this.vistaUbicacionBase.getBtnAcceder().addActionListener(this);
        this.vistaUbicacionBase.getComboUbicacion().addActionListener(this);
        llenarComboUbicacionBase();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        if (arg0.getSource() == vistaUbicacionBase.getBtnAcceder()) {

            if (vistaUbicacionBase.getComboUbicacion().getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una UBICACIÓN para acceder",
                    "SELECCIONAR UBICACIÓN", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            ubicacionBase = vistaUbicacionBase.getComboUbicacion().getSelectedItem().toString();

            // Bloquear UI mientras se conecta a la base
            vistaUbicacionBase.getBtnAcceder().setEnabled(false);
            vistaUbicacionBase.getComboUbicacion().setEnabled(false);
            vistaUbicacionBase.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // SwingWorker: la conexión MySQL ocurre en background, no en el EDT
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                private Agenda    agenda;
                private Permisos  permisos;
                private Exception errorOcurrido;

                @Override
                protected Void doInBackground() {
                    try {
                        // Esto puede tardar: handshake TCP + autenticación MySQL
                        agenda   = new Agenda(ubicacionBase, false);
                        permisos = new Permisos(ubicacionBase);
                    } catch (Exception e) {
                        errorOcurrido = e;
                    }
                    return null;
                }

                @Override
                protected void done() {
                    // Restaurar cursor y controles (estamos de vuelta en el EDT)
                    vistaUbicacionBase.setCursor(Cursor.getDefaultCursor());
                    vistaUbicacionBase.getBtnAcceder().setEnabled(true);
                    vistaUbicacionBase.getComboUbicacion().setEnabled(true);

                    if (errorOcurrido != null) {
                        JOptionPane.showMessageDialog(null,
                            "No se pudo conectar a la base de datos:\n" + errorOcurrido.getMessage(),
                            "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Conexión exitosa: abrir la ventana principal
                    vistaUbicacionBase.dispose();
                    vistaUbicacionBase = null;

                    VistaPrincipal vista = new VistaPrincipal();
                    ControladorPrincipal controlador = new ControladorPrincipal(vista, ubicacionBase, agenda, permisos);
                    controlador.inicializar();
                }
            };

            worker.execute();
        }
    }

    @SuppressWarnings("unchecked")
    private void llenarComboUbicacionBase() {
        vistaUbicacionBase.getComboUbicacion().addItem("Buenos Aires");
        vistaUbicacionBase.getComboUbicacion().addItem("Bariloche");
        vistaUbicacionBase.getComboUbicacion().setSelectedIndex(-1);
    }
}