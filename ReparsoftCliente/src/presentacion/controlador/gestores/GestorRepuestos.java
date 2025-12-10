package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dto.RepuestosDTO;
import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.vista.VentanaAgregarRepuesto;
import presentacion.vista.VentanaVisualizarEquipos;

/**
 * GestorRepuestos
 * Responsable de:
 * - Abrir ventana de agregar repuestos
 * - Agregar nuevos repuestos
 * - Editar repuestos existentes
 * - Eliminar repuestos
 * - Llenar tabla de repuestos
 */
public class GestorRepuestos {
    
    private ControladorReparacion controlador;
    private Agenda agenda;
    private VentanaAgregarRepuesto ventanaAgregarRepuesto;
    
    private RepuestosDTO repuestoSeleccionado;
    private List<RepuestosDTO> repuestosEnTabla;
    private List<String> caracteresNoValidos = new ArrayList<>();
    
    /**
     * Constructor
     */
    public GestorRepuestos(ControladorReparacion controlador, Agenda agenda) {
        this.controlador = controlador;
        this.agenda = agenda;
        this.repuestosEnTabla = new ArrayList<>();
    }
    
    /**
     * Abre ventana para agregar repuesto
     */
    public void abrirVentanaRepuestos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        ventanaAgregarRepuesto = new VentanaAgregarRepuesto(controlador);
        
        ventanaAgregarRepuesto.getBtnAgregarRepuesto().addActionListener(e -> 
            agregarRepuesto(ventanaVisualizarEquipos));
        ventanaAgregarRepuesto.getBtnCancelar().addActionListener(e -> 
            cerrarVentana());
        
        ventanaAgregarRepuesto.setVisible(true);
    }
    
    /**
     * Agrega nuevo repuesto
     */
    private void agregarRepuesto(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        try {
            int els = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());
            String referencia = ventanaAgregarRepuesto.getTxtReferencia().getText();
            String original = ventanaAgregarRepuesto.getTxtOriginal().getText();
            String reemplazo = ventanaAgregarRepuesto.getTxtReemplazo().getText();
            String notas = ventanaAgregarRepuesto.getTxtNota().getText();
            
            // Validar caracteres
            if (verificarCaracteresPermitidos(referencia, original, reemplazo, notas)) {
                String mensaje = "Caracteres no válidos: " + String.join(", ", caracteresNoValidos);
                JOptionPane.showMessageDialog(null, mensaje, "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            RepuestosDTO nuevoRepuesto = new RepuestosDTO(els, referencia, original, 
                reemplazo, notas);
            
            agenda.agregarRepuesto(nuevoRepuesto);
            
            // Recargar tabla
            controlador.getGestorVisualizacion().llenarTablaRepuestos(ventanaVisualizarEquipos);
            
            ventanaAgregarRepuesto.dispose();
            JOptionPane.showMessageDialog(null, "Repuesto agregado correctamente.", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar repuesto: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Edita repuesto seleccionado
     */
    public void editarRepuesto(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
    	System.out.println("Editar repuesto llamado");
    	
    	int i = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
    	repuestosEnTabla = controlador.getGestorVisualizacion().getRepuestosEnTabla();
    	repuestoSeleccionado = repuestosEnTabla.get(i);
    	
        int fila = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
        System.out.println(fila);
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un repuesto para editar.", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            DefaultTableModel modelo = ventanaVisualizarEquipos.getModelRepuestos();
            String referencia = modelo.getValueAt(fila, 0).toString();
            String original = modelo.getValueAt(fila, 1).toString();
            String reemplazo = modelo.getValueAt(fila, 2).toString();
            String notas = modelo.getValueAt(fila, 3).toString();
            
            if (repuestoSeleccionado != null) {
                repuestoSeleccionado.setRef(referencia);
                repuestoSeleccionado.setOriginal(original);
                repuestoSeleccionado.setReemplazo(reemplazo);
                repuestoSeleccionado.setNotas(notas);
                
                System.out.println("Repuesto a editar: " + repuestoSeleccionado.getNotas() + " " + repuestoSeleccionado.getRef() + " " + repuestoSeleccionado.getOriginal() + " " + repuestoSeleccionado.getReemplazo());
                
                agenda.editarRepuesto(repuestoSeleccionado);
                ventanaVisualizarEquipos.getBtnEditarRepuesto().setEnabled(false);
                
                JOptionPane.showMessageDialog(null, "Repuesto editado correctamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al editar repuesto: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Elimina repuesto seleccionado
     */
    public void eliminarRepuesto(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        int fila = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
        
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un repuesto para eliminar.", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de eliminar este repuesto?", "Confirmación", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int[] filasSeleccionadas = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRows();
                List<RepuestosDTO> repuestos = controlador.getGestorVisualizacion().getRepuestosEnTabla();
                
                for (int i = filasSeleccionadas.length - 1; i >= 0; i--) {
                    agenda.borraRepuesto(repuestos.get(filasSeleccionadas[i]));
                }
                
                // Recargar tabla
                controlador.getGestorVisualizacion().llenarTablaRepuestos(ventanaVisualizarEquipos);
                repuestoSeleccionado = null;
                
                JOptionPane.showMessageDialog(null, "Repuesto eliminado correctamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar repuesto: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Verifica caracteres no permitidos
     */
    private boolean verificarCaracteresPermitidos(String... textos) {
        caracteresNoValidos.clear();
        
        for (String texto : textos) {
            if (texto.contains("'")) {
                caracteresNoValidos.add("'");
            }
            
            String[] simbolosNoPermitidos = {";", "\\", "/*", "*/"};
            for (String simbolo : simbolosNoPermitidos) {
                if (texto.contains(simbolo) && !caracteresNoValidos.contains(simbolo)) {
                    caracteresNoValidos.add(simbolo);
                }
            }
        }
        
        return !caracteresNoValidos.isEmpty();
    }
    
    /**
     * Maneja clic en tabla de repuestos
     */
    public void mouseClicked(MouseEvent e) {
        VentanaVisualizarEquipos ventana = controlador.getVentanaVisualizarEquipos();
        if (ventana == null) return;
        
        int fila = ventana.getTablaRepuestos().getSelectedRow();
        if (fila >= 0) {
            List<RepuestosDTO> repuestos = controlador.getGestorVisualizacion().getRepuestosEnTabla();
            if (!repuestos.isEmpty() && fila < repuestos.size()) {
                repuestoSeleccionado = repuestos.get(fila);
            }
        }
    }
    

    
    
    public void habilitarEdicionRepuestos(VentanaVisualizarEquipos ventanaVisualizarEquipos, KeyEvent e) {
        if (ventanaVisualizarEquipos != null) {
            if (e.getSource() == ventanaVisualizarEquipos.getTablaRepuestos()) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Object mje = "Deberá 'GUARDAR EDICIÓN' para mantener las modificaciones.";
                    JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                    ventanaVisualizarEquipos.getBtnEditarRepuesto().setEnabled(true);
                }
            }
        }
    }
    
    
    
    
    /**
     * Procesa eventos delegados
     */
    public void procesarEventos(ActionEvent e) {
        VentanaVisualizarEquipos ventana = controlador.getVentanaVisualizarEquipos();
        if (ventana == null) return;
        
        if (e.getSource() == ventana.getBtnRepuestos()) {
            abrirVentanaRepuestos(ventana);
        } else if (e.getSource() == ventana.getBtnEditarRepuesto()) {
            editarRepuesto(ventana);
        } else if (e.getSource() == ventana.getBtnEliminarRepuesto()) {
            eliminarRepuesto(ventana);
        }
    }
    
    /**
     * Cierra ventana de repuestos
     */
    private void cerrarVentana() {
        if (ventanaAgregarRepuesto != null) {
            ventanaAgregarRepuesto.dispose();
            ventanaAgregarRepuesto = null;
        }
    }
    
    /**
     * Getters
     */
    public VentanaAgregarRepuesto getVentanaAgregarRepuesto() {
        return ventanaAgregarRepuesto;
    }
    
    public RepuestosDTO getRepuestoSeleccionado() {
        return repuestoSeleccionado;
    }
}

