package presentacion.controlador.gestores;

import java.awt.Desktop;
import java.awt.event.ActionEvent;


import javax.swing.JOptionPane;

import dto.ReparacionDTO;
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

        // Verificar que el PDF ya fue generado
        String cliente = controlador.getVentanaVisualizarEquipos().getTextCliente().getText();
        int els = Integer.parseInt(controlador.getVentanaVisualizarEquipos().getTextELS());
        ReparacionDTO rep = agenda.dameReparacionXels(els);

        if (rep == null) {
            JOptionPane.showMessageDialog(null,
                "No se encontró la reparación.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!rep.getPresupuestoGenerado()) {
            JOptionPane.showMessageDialog(null,
                "Aún no se ha generado el Informe.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Resto del método sin cambios
        ventanaWSP = new VentanaWSP(controlador);

        String nombreContacto   = agenda.ContactoPorCliente(cliente);
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


    private void enviarMensajeWSP() {
        String numero  = ventanaWSP.getTextNumero().getText().trim();
        String mensaje = ventanaWSP.getTextMensaje().getText().trim();

        if (numero.isEmpty() || mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete número y mensaje.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            consumoAPI.ConsumoAPI.abrirWSP(numero, mensaje);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Error al enviar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Abrir explorador de carpetas según ubicación
        String ubicacion = agenda.dameUbucacionBase();
        String ruta;

        if (ubicacion != null && ubicacion.equalsIgnoreCase("Bariloche")) {
            ruta = "F:\\els\\Bariloche\\Administracion\\Sistema\\Presupuestos PDF";
        } else if (ubicacion != null && ubicacion.equalsIgnoreCase("Buenos Aires")) {
            ruta = "F:\\els\\Administracion\\Sistema\\Presupuestos PDF";
        } else {
            return; // Ubicación no reconocida, no abre nada
        }

        try {
            java.io.File carpeta = new java.io.File(ruta);
            if (carpeta.exists() && carpeta.isDirectory()) {
                Desktop.getDesktop().open(carpeta);
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se encontró la carpeta:\n" + ruta,
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "No se pudo abrir el explorador:\n" + ex.getMessage(),
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