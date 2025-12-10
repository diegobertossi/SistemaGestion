package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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
    	
    	int i = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
    	repuestosEnTabla = controlador.getGestorVisualizacion().getRepuestosEnTabla();
    	repuestoSeleccionado = repuestosEnTabla.get(i);
    	
        int fila = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
       
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
              
                agenda.editarRepuesto(repuestoSeleccionado);
  
                
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
                   
                }
            }
        }
    }
    
    
    
    /**
     * Agrega listener para edición automática de celdas en la tabla
     */
    public void agregarListenerEdicionTabla(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        DefaultTableModel modelo = ventanaVisualizarEquipos.getModelRepuestos();
        
        // Listener para detectar cambios en las celdas
        modelo.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int fila = e.getFirstRow();
                if (fila >= 0) {
                    
                }
            }
        });
        
        // Listener para guardar al presionar Enter o Tab, o perder foco
        ventanaVisualizarEquipos.getTablaRepuestos().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    // Esperar a que se complete la edición de la celda
                    SwingUtilities.invokeLater(() -> {
                     
                            guardarEdicionAutomatica(ventanaVisualizarEquipos);
                        
                    });
                }
            }
        });
        
        // Listener para guardar al perder el foco de la tabla
        ventanaVisualizarEquipos.getTablaRepuestos().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
              
                    guardarEdicionAutomatica(ventanaVisualizarEquipos);
                
            }
        });
    }

    /**
     * Guarda la edición automáticamente sin mostrar mensaje de confirmación
     */
    private void guardarEdicionAutomatica(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        try {
            int fila = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
            
            if (fila < 0) {
                return; // No hay fila seleccionada
            }
            
            repuestosEnTabla = controlador.getGestorVisualizacion().getRepuestosEnTabla();
            
            if (repuestosEnTabla.isEmpty() || fila >= repuestosEnTabla.size()) {
                return;
            }
            
            repuestoSeleccionado = repuestosEnTabla.get(fila);
            
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
              
                agenda.editarRepuesto(repuestoSeleccionado);
               
                
                // Guardado silencioso (sin mensaje emergente)
                // Si quieres mostrar confirmación, descomenta la siguiente línea:
                // JOptionPane.showMessageDialog(null, "Repuesto guardado automáticamente.", 
                //     "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar repuesto: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
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

