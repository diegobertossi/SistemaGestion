package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dto.ClienteWSPDTO;
import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.vista.VentanaClientesWSP;
import presentacion.vista.VentanaWSP;

/**
 * GestorClientesWSP
 * Responsable de:
 * - Gestionar ventana de clientes WhatsApp
 * - CRUD de contactos WSP (crear, leer, actualizar, eliminar)
 * - Llenar tabla de clientes
 * - Enviar mensajes por WhatsApp
 */
public class GestorClientesWSP implements MouseListener {
    
    private GestorVisualizacionEquipos controlador;
    private Agenda agenda;
    private VentanaWSP ventanaWSP;
    private VentanaClientesWSP ventanaClientesWSP;
    
    private ClienteWSPDTO clienteWSPSeleccionado;
    private List<ClienteWSPDTO> clientesWSPEnTabla;
    
    /**
     * Constructor
     */
    public GestorClientesWSP(GestorVisualizacionEquipos controlador, Agenda agenda) {
        this.controlador = controlador;
        this.agenda = agenda;
    }
    
    /**
     * Abre ventana WSP
     */
    public void abrirVentanaWSP() {
        ventanaWSP = new VentanaWSP(controlador);
        
        // Cargar datos de contacto
        String cliente = controlador.getVentanaVisualizarEquipos().getTextCliente().getText();
        String nombreContacto = agenda.ContactoPorCliente(cliente);
        String telefonoContacto = agenda.obtenerTelefonoPorCliente(cliente);
        
        ventanaWSP.getTextNombreContacto().setText(nombreContacto);
        ventanaWSP.getTextNumeroContacto().setText(telefonoContacto);
        ventanaWSP.getTextCliente().setText(cliente);
        
        // Listeners
        ventanaWSP.getBtnEnviar().addActionListener(e -> enviarMensajeWSP());
        ventanaWSP.getBtnEditarNmero().addActionListener(e -> 
            ventanaWSP.getTextNumero().setEditable(true));
        ventanaWSP.getBtnUtilizarContacto().addActionListener(e -> 
            utilizarContacto(ventanaWSP.getTextNumeroContacto().getText()));
        ventanaWSP.getBtnClientes().addActionListener(e -> 
            abrirVentanaClientesWSP());
        
        llenarComboOrganizacion();
        llenarComboNombres();
        
        ventanaWSP.setVisible(true);
    }
    
    /**
     * Abre ventana de gestión de clientes WSP
     */
    private void abrirVentanaClientesWSP() {
        ventanaClientesWSP = new VentanaClientesWSP(controlador);
        ventanaClientesWSP.getTablaClienteSWSP().addMouseListener(this);
        
        // Listeners botones
        ventanaClientesWSP.getBtnAgregarCliente().addActionListener(e -> 
            modoAgregarCliente());
        ventanaClientesWSP.getBtnEditarCliente().addActionListener(e -> 
            modoEditarCliente());
        ventanaClientesWSP.getBtnEliminarCliente().addActionListener(e -> 
            eliminarClienteWSP());
        ventanaClientesWSP.getBtnGuardarNuevo().addActionListener(e -> 
            guardarNuevoCliente());
        ventanaClientesWSP.getBtnCancelarNuevo().addActionListener(e -> 
            cancelarOperacion());
        ventanaClientesWSP.getBtnGuardarEdicion().addActionListener(e -> 
            guardarEdicionCliente());
        ventanaClientesWSP.getBtnCancelarEdicion().addActionListener(e -> 
            cancelarOperacion());
        
        llenarTablaClientesWSP();
        ventanaClientesWSP.setVisible(true);
    }
    
    /**
     * Activa modo para agregar cliente
     */
    private void modoAgregarCliente() {
        ventanaClientesWSP.getBtnGuardarNuevo().setVisible(true);
        ventanaClientesWSP.getBtnCancelarNuevo().setVisible(true);
        ventanaClientesWSP.getBtnEliminarCliente().setEnabled(false);
        ventanaClientesWSP.getBtnEditarCliente().setEnabled(false);
        
        ventanaClientesWSP.getTxtNombre().setText("");
        ventanaClientesWSP.getTxtOrganizacion().setText("");
        ventanaClientesWSP.getTxtTelefono().setText("");
        ventanaClientesWSP.getTxtNombre().setEditable(true);
        ventanaClientesWSP.getTxtOrganizacion().setEditable(true);
        ventanaClientesWSP.getTxtTelefono().setEditable(true);
        clienteWSPSeleccionado = null;
    }
    
    /**
     * Activa modo para editar cliente
     */
    private void modoEditarCliente() {
        if (clienteWSPSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ventanaClientesWSP.getTxtNombre().setEditable(true);
        ventanaClientesWSP.getTxtOrganizacion().setEditable(true);
        ventanaClientesWSP.getTxtTelefono().setEditable(true);
        ventanaClientesWSP.getBtnGuardarEdicion().setVisible(true);
        ventanaClientesWSP.getBtnCancelarEdicion().setVisible(true);
        ventanaClientesWSP.getBtnAgregarCliente().setEnabled(false);
        ventanaClientesWSP.getBtnEliminarCliente().setEnabled(false);
    }
    
    /**
     * Guarda nuevo cliente
     */
    private void guardarNuevoCliente() {
        String nombre = ventanaClientesWSP.getTxtNombre().getText().trim();
        String organizacion = ventanaClientesWSP.getTxtOrganizacion().getText().trim();
        String telefono = ventanaClientesWSP.getTxtTelefono().getText().trim();
        
        if (nombre.isEmpty() || organizacion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (existeClienteWSP(telefono)) {
            JOptionPane.showMessageDialog(null, "El número de teléfono ya existe.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ClienteWSPDTO nuevoCliente = new ClienteWSPDTO(0, organizacion, nombre, telefono);
        agenda.agregarClienteWSP(nuevoCliente);
        
        llenarTablaClientesWSP();
        cancelarOperacion();
        
        JOptionPane.showMessageDialog(null, "Cliente agregado correctamente.", 
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Guarda cambios de cliente existente
     */
    private void guardarEdicionCliente() {
        if (clienteWSPSeleccionado == null) {
            return;
        }
        
        String nombre = ventanaClientesWSP.getTxtNombre().getText().trim();
        String organizacion = ventanaClientesWSP.getTxtOrganizacion().getText().trim();
        String telefono = ventanaClientesWSP.getTxtTelefono().getText().trim();
        
        if (nombre.isEmpty() || organizacion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        clienteWSPSeleccionado.setNombreWSP(nombre);
        clienteWSPSeleccionado.setOrganizacion(organizacion);
        clienteWSPSeleccionado.setTelefonoWSP(telefono);
        
        agenda.editarClienteWSP(clienteWSPSeleccionado);
        
        llenarTablaClientesWSP();
        cancelarOperacion();
        
        JOptionPane.showMessageDialog(null, "Cliente editado correctamente.", 
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Elimina cliente seleccionado
     */
    private void eliminarClienteWSP() {
        if (clienteWSPSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de eliminar este cliente?", "Confirmación", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int[] filasSeleccionadas = ventanaClientesWSP.getTablaClienteSWSP().getSelectedRows();
            for (int i = filasSeleccionadas.length - 1; i >= 0; i--) {
                agenda.borrarClienteWSP(clientesWSPEnTabla.get(filasSeleccionadas[i]));
            }
            
            llenarTablaClientesWSP();
            clienteWSPSeleccionado = null;
            
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Cancela operación y vuelve a estado normal
     */
    private void cancelarOperacion() {
        ventanaClientesWSP.getTxtNombre().setText("");
        ventanaClientesWSP.getTxtOrganizacion().setText("");
        ventanaClientesWSP.getTxtTelefono().setText("");
        ventanaClientesWSP.getTxtNombre().setEditable(false);
        ventanaClientesWSP.getTxtOrganizacion().setEditable(false);
        ventanaClientesWSP.getTxtTelefono().setEditable(false);
        ventanaClientesWSP.getBtnGuardarNuevo().setVisible(false);
        ventanaClientesWSP.getBtnCancelarNuevo().setVisible(false);
        ventanaClientesWSP.getBtnGuardarEdicion().setVisible(false);
        ventanaClientesWSP.getBtnCancelarEdicion().setVisible(false);
        ventanaClientesWSP.getBtnAgregarCliente().setEnabled(true);
        ventanaClientesWSP.getBtnEliminarCliente().setEnabled(true);
        ventanaClientesWSP.getBtnEditarCliente().setEnabled(true);
        clienteWSPSeleccionado = null;
    }
    
    /**
     * Llena tabla de clientes WSP
     */
    private void llenarTablaClientesWSP() {
        DefaultTableModel modelo = ventanaClientesWSP.getModelClientesWSP();
        modelo.setRowCount(0);
        
        this.clientesWSPEnTabla = agenda.obtenerClientesWSP();
        
        for (ClienteWSPDTO cliente : clientesWSPEnTabla) {
            Object[] fila = {
                cliente.getOrganizacion(),
                cliente.getNombreWSP(),
                cliente.getTelefonoWSP()
            };
            modelo.addRow(fila);
        }
    }
    
    /**
     * Llena combo de organizaciones
     */
    private void llenarComboOrganizacion() {
        agenda.ListarOrganizacionWSP(ventanaWSP.getComboOrganizacion());
        ventanaWSP.getComboOrganizacion().addActionListener(e -> {
            Object selected = ventanaWSP.getComboOrganizacion().getSelectedItem();
            if (selected != null) {
                String organizacion = selected.toString();
                agenda.ListarContactoxOrganizacion(ventanaWSP.getComboNombreBuscado(), organizacion);
            }
        });
    }
    
    /**
     * Llena combo de nombres de contactos
     */
    private void llenarComboNombres() {
        ventanaWSP.getComboNombreBuscado().addActionListener(e -> {
            Object selected = ventanaWSP.getComboNombreBuscado().getSelectedItem();
            if (selected != null) {
                String nombre = selected.toString();
                String telefono = agenda.obtenerTelefonoxContacto(nombre);
                ventanaWSP.getTextnumeroContactoBuscado().setText(telefono);
            }
        });
    }
    
    /**
     * Utiliza contacto seleccionado
     */
    private void utilizarContacto(String telefono) {
        ventanaWSP.getTextNumero().setText(telefono);
        ventanaWSP.getTextMensaje().setEditable(true);
        ventanaWSP.getTextMensaje().setText("Hola");
    }
    
    /**
     * Envía mensaje por WhatsApp
     */
    private void enviarMensajeWSP() {
        String numero = ventanaWSP.getTextNumero().getText().trim();
        String mensaje = ventanaWSP.getTextMensaje().getText().trim();
        
        if (numero.isEmpty() || mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete número y mensaje.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            consumoAPI.ConsumoAPI.abrirWSP("", numero, mensaje);
            JOptionPane.showMessageDialog(null, "Mensaje enviado correctamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al enviar: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Verifica si existe cliente con este teléfono
     */
    private boolean existeClienteWSP(String telefono) {
        if (clientesWSPEnTabla == null || clientesWSPEnTabla.isEmpty()) {
            return false;
        }
        
        for (ClienteWSPDTO cliente : clientesWSPEnTabla) {
            if (cliente.getTelefonoWSP().equals(telefono)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Implementación de MouseListener
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = ventanaClientesWSP.getTablaClienteSWSP().getSelectedRow();
        if (fila >= 0 && fila < clientesWSPEnTabla.size()) {
            clienteWSPSeleccionado = clientesWSPEnTabla.get(fila);
            ventanaClientesWSP.getTxtNombre().setText(clienteWSPSeleccionado.getNombreWSP());
            ventanaClientesWSP.getTxtOrganizacion().setText(clienteWSPSeleccionado.getOrganizacion());
            ventanaClientesWSP.getTxtTelefono().setText(clienteWSPSeleccionado.getTelefonoWSP());
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    /**
     * Procesa eventos delegados
     */
    public void procesarEventos(ActionEvent e) {
        // Delegación desde ControladorReparacion
    }
    
    /**
     * Getters
     */
    public VentanaWSP getVentanaWSP() {
        return ventanaWSP;
    }
    
    public VentanaClientesWSP getVentanaClientesWSP() {
        return ventanaClientesWSP;
    }
}

