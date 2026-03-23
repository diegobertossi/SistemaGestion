package presentacion.controlador.gestores;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import java.awt.Toolkit;

import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaAgregarEquipo;
import tiposPropios.MonedaFormatter;

/**
 * GestorInterfazEquipos
 * Responsable de:
 * - Habilitar/deshabilitar campos
 * - Gestionar listeners de precios
 * - Habilitar autocompletado
 * - Configurar menús contextuales
 * - Gestionar undo/redo
 */
public class GestorInterfazEquipos {
    
    private MonedaFormatter monedaFormatter;
    // Colores para estados de reparación
    private static final Color PAGADO = new Color(144, 238, 144);
    private static final Color SIN_PRESUPUESTAR = new Color(211, 211, 211);
    private static final Color PARCIAL = new Color(255, 239, 153);
    private static final Color FALTA_PAGO = new Color(255, 182, 193);
    private static final Color NO_ACEPTADO = new Color(216, 191, 216);
    private static final Color ESPERANDO = new Color(173, 216, 230);
    private static final Color SIN_REPARACION = new Color(255, 218, 185);
    
    
    /**
     * Constructor
     */
    public GestorInterfazEquipos() {
        this.monedaFormatter = new MonedaFormatter();
    }
    
    /**
     * Habilita campos para edición
     */
    public void habilitarCampos(VentanaVisualizarEquipos ventana) {
        ventana.getTextNombreEquipo().setEditable(true);
        ventana.getTextModelo().setEditable(true);
        ventana.getTextMarca().setEditable(true);
        ventana.getTextNSerie().setEditable(true);
        ventana.getTextClienteCliente().setEditable(true);
        ventana.getTextAvisoCliente().setEditable(true);
        ventana.getTextRemitoCliente().setEditable(true);
        ventana.getTextFalla().setEditable(true);
        ventana.getTextOC().setEditable(true);
        ventana.getTextPresupuesto().setEditable(true);
        ventana.getTextPresupuestoDolar().setEditable(true);
        ventana.getTextPago().setEditable(true);
        ventana.getTextDiagnostico().setEditable(true);
        ventana.getTextInformeCliente().setEditable(true);
        ventana.getTablaRepuestos().setEnabled(true);
        ventana.getFechaEntrada().setEnabled(true);
        ventana.getFechaReparacion().setEnabled(true);
        ventana.getFechaRespuesta().setEnabled(true);
        ventana.getFechaSalida().setEnabled(true);
        ventana.getBtnGuardarCambios().setEnabled(true);
        ventana.getBotonEditarEstados().setEnabled(true);
        ventana.getBtnRepuestos().setEnabled(true);
        ventana.getBtnEliminarRepuesto().setEnabled(true);
        ventana.getBtnCopiarPresupuesto().setEnabled(true);
        ventana.getTablaRepuestos().setEnabled(true);
        ventana.getTextNumeroFactura().setEditable(true);
        ventana.getBtnCopiarFactura().setEnabled(true);
        
        // Mostrar combos, ocultar textos
        ventana.getTextCliente().setVisible(false);
        ventana.getTextSucursal().setVisible(false);
        ventana.getTextNombreTecnico().setVisible(false);
        ventana.getTextEstadoComercial().setVisible(false);
        ventana.getTextEstadoTecnico().setVisible(false);
        ventana.getTextEstadoFisico().setVisible(false);
        ventana.getTextLugarDeIngreso().setVisible(false);
        
        ventana.getComboClientes().setVisible(true);
        ventana.getComboSucursal().setVisible(true);
        ventana.getComboTecnico().setVisible(true);
        ventana.getComboEstadoComercial().setVisible(true);
        ventana.getComboEstadoTecnico().setVisible(true);
        ventana.getComboEstadoFisico().setVisible(true);
        ventana.getComboIngreso().setVisible(true);
    }
    
    /**
     * Deshabilita campos (modo lectura)
     */
    public void deshabilitarCampos(VentanaVisualizarEquipos ventana) {
        ventana.getTextNombreEquipo().setEditable(false);
        ventana.getTextModelo().setEditable(false);
        ventana.getTextMarca().setEditable(false);
        ventana.getTextNSerie().setEditable(false);
        ventana.getTextClienteCliente().setEditable(false);
        ventana.getTextAvisoCliente().setEditable(false);
        ventana.getTextRemitoCliente().setEditable(false);
        ventana.getTextFalla().setEditable(false);
        ventana.getTextOC().setEditable(false);
        ventana.getTextPresupuesto().setEditable(false);
        ventana.getTextPresupuestoDolar().setEditable(false);
        ventana.getTextPago().setEditable(false);
        ventana.getTextDiagnostico().setEditable(false);
        ventana.getTextInformeCliente().setEditable(false);
        ventana.getFechaEntrada().setEnabled(false);
        ventana.getFechaReparacion().setEnabled(false);
        ventana.getFechaRespuesta().setEnabled(false);
        ventana.getFechaSalida().setEnabled(false);
        ventana.getBtnGuardarCambios().setEnabled(false);
        ventana.getBotonEditarEstados().setEnabled(false);
        ventana.getBtnRepuestos().setEnabled(false);
        ventana.getBtnEliminarRepuesto().setEnabled(false);
        ventana.getBtnCopiarPresupuesto().setEnabled(false);
        ventana.getTablaRepuestos().setEnabled(false);
        ventana.getTextNumeroFactura().setEditable(false);
        ventana.getBtnCopiarFactura().setEnabled(false);
        
        
        // Mostrar textos, ocultar combos
        ventana.getTextCliente().setVisible(true);
        ventana.getTextSucursal().setVisible(true);
        ventana.getTextNombreTecnico().setVisible(true);
        ventana.getTextEstadoComercial().setVisible(true);
        ventana.getTextEstadoTecnico().setVisible(true);
        ventana.getTextEstadoFisico().setVisible(true);
        ventana.getTextLugarDeIngreso().setVisible(true);
        
        ventana.getComboClientes().setVisible(false);
        ventana.getComboSucursal().setVisible(false);
        ventana.getComboTecnico().setVisible(false);
        ventana.getComboEstadoComercial().setVisible(false);
        ventana.getComboEstadoTecnico().setVisible(false);
        ventana.getComboEstadoFisico().setVisible(false);
        ventana.getComboIngreso().setVisible(false);
        
    }
    
    /**
     * Agrega listeners de precios
     */
    public void agregarListenersPrecios(VentanaVisualizarEquipos ventana) {
        // Presupuesto
        ventana.getTextPresupuesto().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ventana.getTextPresupuesto().selectAll();
            }
        });
        
        ventana.getTextPresupuesto().addActionListener(e -> {
            String presupuesto = ventana.getTextPresupuesto().getText();
            ventana.getTextPresupuesto().setText(monedaFormatter.formatPeso(presupuesto));
            verificarPresupuestoEditado(ventana);
        });
        
        // Presupuesto Dólar
        ventana.getTextPresupuestoDolar().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ventana.getTextPresupuestoDolar().selectAll();
            }
        });
        
        ventana.getTextPresupuestoDolar().addActionListener(e -> {
            String presupuesto = ventana.getTextPresupuestoDolar().getText();
            ventana.getTextPresupuestoDolar().setText(monedaFormatter.formatDolar(presupuesto));
            verificarPresupuestoEditado(ventana);
        });
        
        // Pago
        ventana.getTextPago().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ventana.getTextPago().selectAll();
            }
        });
        
        ventana.getTextPago().addActionListener(e -> {
            String pago = ventana.getTextPago().getText();
            ventana.getTextPago().setText(monedaFormatter.formatPeso(pago));
            verificarPresupuestoEditado(ventana);
        });
    }
    
    /**
     * Agrega focus listeners para posición de cursor
     */
    public void agregarFocusListeners(VentanaVisualizarEquipos ventana) {
        FocusListener cursorAlInicioTF = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (e.getComponent() instanceof JTextField) {
                    JTextField tf = (JTextField) e.getComponent();
                    tf.setCaretPosition(0);
                }
            }
        };
        
        ventana.getTextCliente().addFocusListener(cursorAlInicioTF);
        ventana.getTextSucursal().addFocusListener(cursorAlInicioTF);
        ventana.getTextNombreEquipo().addFocusListener(cursorAlInicioTF);
        ventana.getTextMarca().addFocusListener(cursorAlInicioTF);
        ventana.getTextModelo().addFocusListener(cursorAlInicioTF);
        ventana.getTextNSerie().addFocusListener(cursorAlInicioTF);
        ventana.getTextPresupuesto().addFocusListener(cursorAlInicioTF);
        ventana.getTextPresupuestoDolar().addFocusListener(cursorAlInicioTF);
        ventana.getTextPago().addFocusListener(cursorAlInicioTF);
    }
    
    
    
    
	
	 // =============================================
   // MÉTODOS DE GESTIÓN DE PRESUPUESTOS Y PAGOS
   // =============================================

   void verificarPresupuesto(VentanaVisualizarEquipos ventana) {
	   
	   
//       Double presupuesto = Double.parseDouble(ventana.getTextPresupuesto().getText());
//       Double pago = Double.parseDouble(ventana.getTextPago().getText());
//       
	   Double presupuesto = monedaFormatter.parseAmountGuardar(ventana.getTextPresupuesto().getText());
       Double pago = monedaFormatter.parseAmountGuardar(ventana.getTextPago().getText());
       
       String estadoComercial = ventana.getTextEstadoComercial().getText();
       String estadoTecnico = ventana.getTextEstadoTecnico().getText();

       // Caso especial: Sin Reparación
       if ("Sin Reparación".equals(estadoTecnico)) {
           aplicarEstadoVisual(ventana, "SIN REPARACIÓN", SIN_REPARACION);
           return;
       }

       // Sin presupuesto
       if (presupuesto.compareTo(0.0) == 0) {
           aplicarEstadoVisual(ventana, "SIN PRESUPUESTAR", SIN_PRESUPUESTAR);
           ventana.getBtnCopiarPresupuesto().setText("COPIAR PAGO");
           ventana.getBtnCopiarPresupuesto().setToolTipText("Copiar monto de pago al presupuesto");
           ventana.getBtnCopiarPresupuesto().setFont(new Font("Cambria", Font.BOLD, 10));
           return;
       }

       // Caso especial: Presupuesto no aceptado
       if ("NO Aceptado".equals(estadoComercial)) {
           aplicarEstadoVisual(ventana, "NO ACEPTADO", NO_ACEPTADO);
           return;
       }

       // Hay presupuesto
       int comparacion = presupuesto.compareTo(pago);

       if (comparacion == 0) {
           // Totalmente pagado
           aplicarEstadoVisual(ventana, "PAGADO", PAGADO);
           ventana.getBtnCopiarPresupuesto().setText("LIMPIAR PAGO");
           ventana.getBtnCopiarPresupuesto().setToolTipText("Borra monto de pago");
           ventana.getBtnCopiarPresupuesto().setFont(new Font("Cambria", Font.BOLD, 10));
       } else if (comparacion > 0 && pago.compareTo(0.0) > 0) {
           // Pago parcial
           aplicarEstadoVisual(ventana, "PAGADO PARCIALMENTE", PARCIAL);
       } else if (pago.compareTo(0.0) == 0) {
           // Sin pago - verificar estado comercial
           String leyenda = determinarLeyendaSinPago(estadoComercial);
           Color color = "ESPERANDO ACEPTACIÓN".equals(leyenda) ? ESPERANDO : FALTA_PAGO;
           aplicarEstadoVisual(ventana, leyenda, color);
           ventana.getBtnCopiarPresupuesto().setText("COPIAR PAGO");
           ventana.getBtnCopiarPresupuesto().setToolTipText("Copiar monto de pago al presupuesto");
           ventana.getBtnCopiarPresupuesto().setFont(new Font("Cambria", Font.BOLD, 10));
       }
   }

   public void verificarPresupuestoEditado(VentanaVisualizarEquipos ventana) {
       double presupuesto = monedaFormatter.parseAmountGuardar(ventana.getTextPresupuesto().getText());
       double pago = monedaFormatter.parseAmountGuardar(ventana.getTextPago().getText());
       String estadoComercial = ventana.getTextEstadoComercial().getText();

       // Caso especial: Sin Reparación
       if ("Sin Reparación".equals(estadoComercial)) {
           aplicarEstadoVisual(ventana, "SIN REPARACIÓN", SIN_REPARACION);
           ventana.setChckPDFGenerado(false);
           return;
       }

       // Sin presupuesto
       if (presupuesto == 0.0) {
           aplicarEstadoVisual(ventana, "SIN PRESUPUESTAR", SIN_PRESUPUESTAR);
           ventana.setChckPDFGenerado(false);
           return;
       }

       // Caso especial: Presupuesto no aceptado
       if ("NO ACEPTADO".equals(estadoComercial)) {
           aplicarEstadoVisual(ventana, "NO ACEPTADO", NO_ACEPTADO);
           ventana.setChckPDFGenerado(false);
           return;
       }

       // Hay presupuesto
       double diferencia = presupuesto - pago;

       if (diferencia == 0.0) {
           // Totalmente pagado
           aplicarEstadoVisual(ventana, "PAGADO", PAGADO);
           ventana.setChckPDFGenerado(true);
           ventana.getBtnCopiarPresupuesto().setText("LIMPIAR PAGO");
           ventana.getBtnCopiarPresupuesto().setToolTipText("Borra monto de pago");
           ventana.getBtnCopiarPresupuesto().setFont(new Font("Cambria", Font.BOLD, 10));
       } else if (diferencia > 0.0 && pago > 0.0) {
           // Pago parcial
           aplicarEstadoVisual(ventana, "PAGADO PARCIALMENTE", PARCIAL);
       } else if (pago == 0.0) {
           // Sin pago - verificar estado comercial
           String leyenda = determinarLeyendaSinPago(estadoComercial);
           Color color = "ESPERANDO ACEPTACIÓN".equals(leyenda) ? ESPERANDO : FALTA_PAGO;
           aplicarEstadoVisual(ventana, leyenda, color);
           ventana.setChckPDFGenerado(true);
       }
   }

   private String determinarLeyendaSinPago(String estadoComercial) {
       switch (estadoComercial) {
           case "A la Espera de Aceptación":
               return "ESPERANDO ACEPTACIÓN";
           case "Aceptado":
               return "FALTA PAGO";
           default:
               return "FALTA PAGO";
       }
   }

   private void aplicarEstadoVisual(VentanaVisualizarEquipos ventana, String leyenda, Color color) {
       ventana.getTextEquipoPagado().setText(leyenda);
       ventana.getTextEquipoPagado().setVisible(true);
       ventana.getTextEquipoPagado().setBackground(color);
       ventana.getPanel_MontoPresupuesto().setBackground(color);
       ventana.getTextPresupuesto().setBackground(color);
       ventana.getTextPresupuestoDolar().setBackground(color);
       ventana.getTextPago().setBackground(color);
   }

       
    
    /**
     * Habilita menú contextual para componente
     */
    public void habilitarMenuContextual(Object componente) {
        final JTextComponent editor;
        
        if (componente instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) componente;
            if (!comboBox.isEditable()) return;
            editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        } else if (componente instanceof JTextField) {
            editor = (JTextComponent) componente;
        } else if (componente instanceof javax.swing.JTextArea) {
            editor = (JTextComponent) componente;
        } else {
            return;
        }
        
        JPopupMenu menu = new JPopupMenu();
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem pegar = new JMenuItem("Pegar");
        JMenuItem cortar = new JMenuItem("Cortar");
        
        copiar.addActionListener(e -> editor.copy());
        pegar.addActionListener(e -> editor.paste());
        cortar.addActionListener(e -> editor.cut());
        
        menu.add(cortar);
        menu.add(copiar);
        menu.add(pegar);
        
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(editor, e.getX(), e.getY());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(editor, e.getX(), e.getY());
                }
            }
        });
    }
    
 // Mapa para guardar los UndoManagers por componente
    private Map<JTextComponent, UndoManager> undoManagers = new HashMap<>();

    /**
     * Configura Undo/Redo para componentes de texto.
     * Solo registra los listeners UNA VEZ por componente.
     */
    @SuppressWarnings({ "serial", "deprecation" })
    public void configurarUndoRedo(JFrame frame) {
        List<JTextComponent> componentes = obtenerComponentesTexto(frame);

        for (JTextComponent componente : componentes) {
            // Evitar registrar el listener más de una vez
            if (undoManagers.containsKey(componente)) continue;

            UndoManager undoManager = new UndoManager();
            undoManagers.put(componente, undoManager);
            componente.getDocument().addUndoableEditListener(undoManager);

            // Undo
            AbstractAction undoAction = new AbstractAction("Deshacer") {
                public void actionPerformed(ActionEvent e) {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    } else {
                        // Restaurar texto original del registro actual
                        String original = (String) componente.getClientProperty("textoOriginal");
                        if (original != null && !componente.getText().equals(original)) {
                            componente.setText(original);
                            undoManager.discardAllEdits();
                        }
                    }
                }
            };
            undoAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            componente.getActionMap().put("Undo", undoAction);
            componente.getInputMap().put((KeyStroke) undoAction.getValue(Action.ACCELERATOR_KEY), "Undo");

            // Redo
            AbstractAction redoAction = new AbstractAction("Rehacer") {
                public void actionPerformed(ActionEvent e) {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                }
            };
            redoAction.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            componente.getActionMap().put("Redo", redoAction);
            componente.getInputMap().put((KeyStroke) redoAction.getValue(Action.ACCELERATOR_KEY), "Redo");
        }
    }

    /**
     * Resetea el historial de undo y guarda el texto actual como original.
     * Llamar cada vez que se carga un nuevo registro.
     */
    public void resetearUndoRedo(JFrame frame) {
        List<JTextComponent> componentes = obtenerComponentesTexto(frame);
        for (JTextComponent componente : componentes) {
            // Guardar el texto actual como el "original" de este registro
            componente.putClientProperty("textoOriginal", componente.getText());

            // Limpiar historial del registro anterior
            UndoManager undoManager = undoManagers.get(componente);
            if (undoManager != null) {
                undoManager.discardAllEdits();
            }
        }
    }
    
    /**
     * Obtiene todos los componentes de texto de un frame
     */
    private List<JTextComponent> obtenerComponentesTexto(Container container) {
        List<JTextComponent> componentes = new ArrayList<>();
        Component[] items = container.getComponents();
        
        for (Component componente : items) {
            if (componente instanceof JTextComponent) {
                componentes.add((JTextComponent) componente);
            } else if (componente instanceof Container) {
                componentes.addAll(obtenerComponentesTexto((Container) componente));
            }
        }
        
        return componentes;
    }


}

