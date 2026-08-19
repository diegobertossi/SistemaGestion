package main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }

            final VentanaUbicacionBaseDeDatos ventana = new VentanaUbicacionBaseDeDatos();
            ventana.setVisible(true);
            ventana.setAlwaysOnTop(true);
            ventana.toFront();
            ventana.requestFocus();
            ventana.setAlwaysOnTop(false);

            presentacion.controlador.ControladorUbicacionBase controlador =
                new presentacion.controlador.ControladorUbicacionBase(ventana, null);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}