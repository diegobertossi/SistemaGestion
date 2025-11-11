package presentacion.controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.SwingUtilities;

import dto.ReparacionDTO;
import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorPresupuestos;
import presentacion.controlador.ControladorSalidas;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaDatosFacturacion;
import presentacion.vista.VentanaGenerarPresupuesto;
import presentacion.vista.VentanaRemitos;
import tiposPropios.MonedaFormatter;

/**
 * GestorPresupuestoFactura
 * Responsable de:
 * - Abrir ventana de presupuestos
 * - Abrir ventana de facturación
 * - Generar remitos
 * - Enviar avisos por correo
 * - Integración con controladores externos
 */
public class GestorPresupuestoFactura {
    
    private ControladorReparacion controlador;
    private Agenda agenda;
    private ControladorPresupuestos controladorPresupuestos;
    private ControladorSalidas controladorSalidas;
    
    private VentanaGenerarPresupuesto ventanaPresupuesto;
    private VentanaRemitos ventanaRemitos;
    private VentanaDatosFacturacion ventanaFacturacion;
    
    private MonedaFormatter monedaFormatter;
    
    /**
     * Constructor
     */
    public GestorPresupuestoFactura(ControladorReparacion controlador,
                                     Agenda agenda,
                                     ControladorPresupuestos controladorPresupuestos,
                                     ControladorSalidas controladorSalidas) {
        this.controlador = controlador;
        this.agenda = agenda;
        this.controladorPresupuestos = controladorPresupuestos;
        this.controladorSalidas = controladorSalidas;
        this.monedaFormatter = new MonedaFormatter();
    }
    
    /**
     * Abre ventana de presupuesto
     */
    public void abrirPresupuesto(VentanaVisualizarEquipos ventana) {
        if (ventana.getBtnGuardarCambios().isEnabled()) {
            JOptionPane.showMessageDialog(null, 
                "Debe guardar los cambios realizados para poder presupuestar.", 
                "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int els = Integer.parseInt(ventana.getTextELS());
        ventanaPresupuesto = controladorPresupuestos.TomarDatosDeTablasParaVisualizacion(els);
        
        if (ventanaPresupuesto != null) {
            controladorPresupuestos.agregarListenersVentanaGenerarPresupuesto();
            ventanaPresupuesto.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refrescarPantalla(ventana);
                }
            });
        }
    }
    
    /**
     * Abre ventana de facturación
     */
    public void abrirFacturacion(VentanaVisualizarEquipos ventana) {
        String clienteEquipo = ventana.getTextCliente().getText();
        int idCliente = agenda.idClienteporNombre(clienteEquipo);
        String cuitCliente = agenda.dameCuitPorIdCliente(idCliente);
        
        String nombreEquipo = ventana.getTextNombreEquipo().getText();
        String marcaEquipo = ventana.getTextMarca().getText();
        String modeloEquipo = ventana.getTextModelo().getText();
        String serieEquipo = ventana.getTextNSerie().getText();
        String elsEquipo = ventana.getTextELS().toString();
        
        // Parsear presupuesto
        double presupuestoFactura;
        String presupuestoText = ventana.getTextPresupuesto().getText();
        
        if (monedaFormatter.tieneFormato(presupuestoText)) {
            presupuestoFactura = monedaFormatter.parseAmountGuardar(presupuestoText);
        } else {
            presupuestoFactura = monedaFormatter.parseAmount(presupuestoText);
        }
        
        DecimalFormat df = new DecimalFormat("#");
        String presupuesto = df.format(presupuestoFactura);
        
        String itemFactura = "Reparación de " + nombreEquipo + " " + marcaEquipo + " " + 
                            modeloEquipo + " s/n: " + serieEquipo + " ELS: " + elsEquipo;
        
        ventanaFacturacion = new VentanaDatosFacturacion(clienteEquipo, cuitCliente, 
                                                         itemFactura, presupuesto);
        ventanaFacturacion.setVisible(true);
        
        // Preguntar si abrir ARCA
        int seleccion = JOptionPane.showConfirmDialog(ventana, 
            "¿Ir a la Página de ARCA?", "Confirmación", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (seleccion == JOptionPane.YES_OPTION) {
            abrirPaginaARCA();
        }
    }
    
    /**
     * Abre página de ARCA en navegador
     */
    private void abrirPaginaARCA() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://www.arca.gob.ar/landing/default.asp"));
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede abrir el navegador automáticamente.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al abrir la URL: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Genera remito de salida
     */
    public void generarRemito(VentanaVisualizarEquipos ventana) {
        String numeroRemitoActual = ventana.getTextNumeroRemito().getText();
        
        if (!numeroRemitoActual.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Este equipo ya posee remito. Deberá ANULARLO o ELIMINARLO para generar uno nuevo.", 
                "Remito existente", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (ventana.getBtnGuardarCambios().isEnabled()) {
            JOptionPane.showMessageDialog(null, 
                "Debe guardar los cambios realizados para generar remito.", 
                "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int els = Integer.parseInt(ventana.getTextELS());
        ventanaRemitos = controladorSalidas.cargarRemitoVisualizacion(els);
        
        if (ventanaRemitos != null) {
            controladorSalidas.agregarListenersVentanaRemitos();
            ventanaRemitos.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refrescarPantalla(ventana);
                }
            });
        }
    }
    
    /**
     * Envía aviso de informe
     */
    public void enviarAvisoInforme(VentanaVisualizarEquipos ventana) {
        String correo = "diego.bertossi@elsweb.com.ar";
        enviarAviso(ventana, correo, "¿Desea enviar el aviso de 'Informe'?", "INFORME");
    }
    
    /**
     * Envía aviso de equipo listo
     */
    public void enviarAvisoEquipoListo(VentanaVisualizarEquipos ventana) {
        String correo = "diego.bertossi@elsweb.com.ar";
        enviarAviso(ventana, correo, "¿Desea enviar el aviso de 'Equipo Terminado'?", "EQUIPO_LISTO");
    }
    
    /**
     * Envía aviso de respuesta del cliente
     */
    public void enviarRespuestaCliente(VentanaVisualizarEquipos ventana) {
        ReparacionDTO reparacion = controlador.getGestorVisualizacion().getReparacionActual();
        String correo = reparacion.getCorreo();
        enviarAviso(ventana, correo, "¿Desea enviar el aviso de 'Respuesta del Cliente'?", 
                   "RESPUESTA_CLIENTE");
    }
    
    /**
     * Envía aviso por correo
     */
    private void enviarAviso(VentanaVisualizarEquipos ventana, String correo, 
                            String mensaje, String tipoAviso) {
        int seleccion = JOptionPane.showConfirmDialog(ventana, 
            mensaje + " a " + correo + "?", "Confirmación", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (seleccion != JOptionPane.YES_OPTION) return;
        
        // Dialog de procesamiento
        JDialog popup = new JDialog();
        popup.setTitle("Procesando");
        popup.setModal(false);
        popup.setSize(300, 100);
        popup.setLocationRelativeTo(ventana);
        popup.add(new JLabel("Enviando correo, espere...", SwingConstants.CENTER));
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String els = ventana.getTextELS().toString();
                    String cliente = ventana.getTextCliente().getText();
                    String sucursal = ventana.getTextSucursal().getText();
                    ReparacionDTO reparacion = controlador.getGestorVisualizacion().getReparacionActual();
                    
                    switch (tipoAviso) {
                        case "RESPUESTA_CLIENTE":
                            mails.EnviarMail.enviarAvisoRespuestaCliente(correo, els, 
                                cliente, sucursal, reparacion.getEstadoComercial());
                            break;
                        case "EQUIPO_LISTO":
                            mails.EnviarMail.enviarAvisoEquipoTerminado(correo, els, 
                                cliente, sucursal);
                            break;
                        case "INFORME":
                            mails.EnviarMail.enviarAvisoInforme(correo, els, 
                                cliente, sucursal);
                            ventana.setChckbxAvisoEnviado(true);
                            break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
            
            @Override
            protected void done() {
                popup.dispose();
                JOptionPane.showMessageDialog(ventana, "Correo enviado correctamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        
        SwingUtilities.invokeLater(() -> {
            popup.setVisible(true);
            worker.execute();
        });
    }
    
    /**
     * Refresca pantalla después de operación
     */
    private void refrescarPantalla(VentanaVisualizarEquipos ventana) {
        try {
            int els = Integer.parseInt(ventana.getTextELS());
            controlador.getGestorVisualizacion().cargarDatosEquipo(ventana, els);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Procesa eventos delegados
     */
    public void procesarEventos(ActionEvent e) {
        VentanaVisualizarEquipos ventana = controlador.getVentanaVisualizarEquipos();
        if (ventana == null) return;
        
        if (e.getSource() == ventana.getBotonPresupuestar()) {
            abrirPresupuesto(ventana);
        } else if (e.getSource() == ventana.getBtnfacturar()) {
            abrirFacturacion(ventana);
        } else if (e.getSource() == ventana.getBtnGenerarRemito()) {
            generarRemito(ventana);
        } else if (e.getSource() == ventana.getBotonAvisoInforme()) {
            enviarAvisoInforme(ventana);
        } else if (e.getSource() == ventana.getBotonAvisoEquipoListo()) {
            enviarAvisoEquipoListo(ventana);
        } else if (e.getSource() == ventana.getBotonRespuestaAlTecnico()) {
            enviarRespuestaCliente(ventana);
        }
    }
    
    /**
     * Getters
     */
    public VentanaGenerarPresupuesto getVentanaPresupuesto() {
        return ventanaPresupuesto;
    }
    
    public VentanaRemitos getVentanaRemitos() {
        return ventanaRemitos;
    }
}
