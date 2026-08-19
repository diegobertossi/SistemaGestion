package presentacion.controlador;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import modelo.Agenda;
import modelo.Permisos;
import presentacion.vista.SplashWindow;
import presentacion.vista.VentanaUbicacionBaseDeDatos;
import presentacion.vista.VistaPrincipal;
import util.RutasSistema;

public class ControladorUbicacionBase implements ActionListener {

    private VentanaUbicacionBaseDeDatos vistaUbicacionBase;
    private SplashWindow splash;
    private String ubicacionBase;

    public ControladorUbicacionBase(VentanaUbicacionBaseDeDatos vistaUbicacionBaseDatos, SplashWindow splash) {
        this.vistaUbicacionBase = vistaUbicacionBaseDatos;
        this.splash = splash;
        this.vistaUbicacionBase.getBtnAcceder().addActionListener(this);
        this.vistaUbicacionBase.getComboUbicacion().addActionListener(this);
        llenarComboUbicacionBase();
    }

    private void disposeSplash() {
        if (this.splash != null) {
            this.splash.disposeConTransicion();
        }
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
            RutasSistema.setModoPrueba(vistaUbicacionBase.getBtnModoPrueba().isSelected());

            vistaUbicacionBase.getBtnAcceder().setEnabled(false);
            vistaUbicacionBase.getComboUbicacion().setEnabled(false);
            vistaUbicacionBase.getBtnModoPrueba().setEnabled(false);
            vistaUbicacionBase.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                private Exception errorOcurrido;

                @Override
                protected Void doInBackground() {
                    try {
                        new Agenda(ubicacionBase, false);
                    } catch (Exception e) {
                        errorOcurrido = e;
                    }
                    return null;
                }

                @Override
                protected void done() {
                    vistaUbicacionBase.setCursor(Cursor.getDefaultCursor());
                    vistaUbicacionBase.getBtnAcceder().setEnabled(true);
                    vistaUbicacionBase.getComboUbicacion().setEnabled(true);
                    vistaUbicacionBase.getBtnModoPrueba().setEnabled(true);

                    if (errorOcurrido != null) {
                        JOptionPane.showMessageDialog(null,
                            "No se pudo conectar a la base de datos:\n" + errorOcurrido.getMessage(),
                            "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    vistaUbicacionBase.dispose();
                    vistaUbicacionBase = null;

                    VistaPrincipal vista = new VistaPrincipal();
                    presentacion.reportes.ReportePreloader.preload();
                    ControladorPrincipal controlador = new ControladorPrincipal(vista, ubicacionBase);
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