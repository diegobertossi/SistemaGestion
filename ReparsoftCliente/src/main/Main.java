package main;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import presentacion.controlador.ControladorUbicacionBase;
import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        
        // Precarga el driver JDBC antes de mostrar cualquier ventana.
        // Así ese costo no lo paga Conexion más tarde, bloqueando el EDT.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver JDBC precargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver JDBC MySQL 8.x");
            e.printStackTrace();
        }

        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        VentanaUbicacionBaseDeDatos ventanaUbicacionBaseDeDatos = new VentanaUbicacionBaseDeDatos();
        ControladorUbicacionBase controlador = new ControladorUbicacionBase(ventanaUbicacionBaseDeDatos);
    }
}