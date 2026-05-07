package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;


import javax.swing.JOptionPane;

import modelo.Agenda;

import presentacion.vista.VentanaWSP;

/**
 * GestorClientesWSP
 * Responsable de:
 * - Gestionar ventana de clientes WhatsApp
 * - CRUD de contactos WSP (crear, leer, actualizar, eliminar)
 * - Llenar tabla de clientes
 * - Enviar mensajes por WhatsApp
 */
public class GestorClientesWSP {

    private GestorVisualizacionEquipos controlador;
    private Agenda agenda;
    private VentanaWSP ventanaWSP;

    // =====================================================================
    // CONSTRUCTOR
    // =====================================================================

    public GestorClientesWSP(GestorVisualizacionEquipos controlador, Agenda agenda) {
        this.controlador = controlador;
        this.agenda = agenda;
    }

    // =====================================================================
    // VENTANA WSP PRINCIPAL
    // =====================================================================

    public void abrirVentanaWSP() {
        ventanaWSP = new VentanaWSP(controlador);

        String cliente        = controlador.getVentanaVisualizarEquipos().getTextCliente().getText();
        String nombreContacto = agenda.ContactoPorCliente(cliente);
        String telefonoContacto = agenda.obtenerTelefonoPorCliente(cliente);

        ventanaWSP.getTextNombreContacto().setText(nombreContacto);
        ventanaWSP.getTextNumeroContacto().setText(telefonoContacto);
        ventanaWSP.getTextCliente().setText(cliente);

        ventanaWSP.getBtnEnviar().addActionListener(e -> enviarMensajeWSP());
        ventanaWSP.getBtnEditarNmero().addActionListener(e ->
            ventanaWSP.getTextNumero().setEditable(true));
        ventanaWSP.getBtnUtilizarContacto().addActionListener(e ->
            utilizarContacto(ventanaWSP.getTextNumeroContacto().getText()));
       
        ventanaWSP.setVisible(true);
    }


    private void utilizarContacto(String telefono) {
        ventanaWSP.getTextNumero().setText(telefono);
        ventanaWSP.getTextMensaje().setEditable(true);
        ventanaWSP.getTextMensaje().setText("Hola, Adjunto el informe/presupuesto de\nreparación del equipo.\nQuedo a disposición para cualquier consulta.");
    }

    // =====================================================================
    // ENVÍO DE MENSAJE — MÉTODO CORREGIDO
    // =====================================================================

    /**
     * Envía el mensaje por WhatsApp.
     * Usa ConsumoAPI.abrirWSP(numero, mensaje) que detecta automáticamente
     * si WhatsApp Desktop está instalado y lo usa directamente, evitando
     * abrir el navegador. Si no está instalado, cae al navegador.
     */
    private void enviarMensajeWSP() {
        String numero  = ventanaWSP.getTextNumero().getText().trim();
        String mensaje = ventanaWSP.getTextMensaje().getText().trim();

        if (numero.isEmpty() || mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete número y mensaje.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Llamada con 2 parámetros: usa Desktop si está disponible, navegador si no
            consumoAPI.ConsumoAPI.abrirWSP(numero, mensaje);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error al enviar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 // =====================================================================
    // MOUSE LISTENER
    // =====================================================================

     // =====================================================================
    // EVENTOS DELEGADOS / GETTERS
    // =====================================================================

    public void procesarEventos(ActionEvent e) {}

    public VentanaWSP getVentanaWSP() {
        return ventanaWSP;
    }


}