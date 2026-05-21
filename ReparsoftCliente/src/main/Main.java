package main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import presentacion.vista.SplashWindow;
import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class Main {

    public static void main(String[] args) {
        final SplashWindow splash = new SplashWindow();
        splash.mostrar();

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }

            final VentanaUbicacionBaseDeDatos ventana = new VentanaUbicacionBaseDeDatos();
            ventana.setVisible(false);

            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {}

                splash.disposeConTransicion();

                new Thread(() -> {
                    while (splash.isVisible()) {
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException ignored) {}
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {}

                    EventQueue.invokeLater(() -> {
                        ventana.setVisible(true);
                        ventana.setAlwaysOnTop(true);
                        ventana.toFront();
                        ventana.requestFocus();
                        ventana.setAlwaysOnTop(false);
                        presentacion.controlador.ControladorUbicacionBase controlador =
                            new presentacion.controlador.ControladorUbicacionBase(ventana, splash);
                    });
                }).start();
            }).start();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}