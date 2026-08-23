package presentacion.controlador.gestores;

import java.awt.Cursor;
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

import VistaPropias.ExtractorFacturaPDF;
import VistaPropias.ExtractorFacturaPDF.DatosFactura;

import javax.swing.SwingUtilities;

import dto.ReparacionDTO;
import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorPresupuestos;
import presentacion.controlador.ControladorSalidas;
import persistencia.dao.mysql.LogDAO;
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
     * Abre ventana de presupuesto. La consulta de la reparación corre en
     * segundo plano para no bloquear la EDT; la ventana se construye y
     * rellena cuando los datos están listos.
     */
    public void abrirPresupuesto(final VentanaVisualizarEquipos ventana) {
        if (ventana.getBtnGuardarCambios().isEnabled()) {
            JOptionPane.showMessageDialog(null,
                "Debe guardar los cambios realizados para poder presupuestar.",
                "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        final int els = Integer.parseInt(ventana.getTextELS());
        ventana.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<ReparacionDTO, Void>() {
            @Override
            protected ReparacionDTO doInBackground() {
                return agenda.dameReparacionXels(els);
            }

            @Override
            protected void done() {
                ventana.setCursor(Cursor.getDefaultCursor());
                try {
                    ReparacionDTO reparacion = get();
                    if (reparacion == null) {
                        JOptionPane.showMessageDialog(null, "No se encontraron datos para la reparación " + els,
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    ventanaPresupuesto = controladorPresupuestos.TomarDatosDeTablasParaVisualizacion(reparacion);
                    if (ventanaPresupuesto != null) {
                        controladorPresupuestos.agregarListenersVentanaGenerarPresupuesto();
                        ventanaPresupuesto.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                refrescarPantalla(ventana);
                            }
                        });
                    }
                } catch (Exception ex) {
                    LogDAO.error("Error al abrir presupuesto para ELS " + els, ex);
                    JOptionPane.showMessageDialog(null, "No se pudo cargar los datos de la reparación " + els,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
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
    	String correo = "els@elsweb.com.ar; natalia.seip@elsweb.com.ar";
        enviarAviso(ventana, correo, "¿Desea enviar el aviso de 'Informe'?", "INFORME");
    }
    
    /**
     * Envía aviso de equipo listo
     */
    public void enviarAvisoEquipoListo(VentanaVisualizarEquipos ventana) {
        //String correo = "diego.bertossi@elsweb.com.ar";
    	String correo = "els@elsweb.com.ar; natalia.seip@elsweb.com.ar";
    	//String correo = "diego.bertossi@elsweb.com.ar; diego.bertossi@gmail.com";
    	
    	enviarAviso(ventana, correo, "¿Desea enviar el aviso de 'Equipo Terminado'?", "EQUIPO_LISTO");
    }
    

    /**
     * Envía aviso de respuesta del cliente
     */
    public void enviarRespuestaCliente(VentanaVisualizarEquipos ventana) {
        ReparacionDTO reparacion = controlador.getGestorVisualizacion().getReparacionActual();
        
        // Obtener el nombre completo del técnico
        String nombreCompletoTecnico = reparacion.getNombreUsuario();
        
        // Validar que exista un técnico asignado
        if (nombreCompletoTecnico == null || nombreCompletoTecnico.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, 
                "No hay un técnico asignado a este equipo.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener el correo del técnico usando el método correoPorNombre
        String correoTecnico = agenda.obtenerCorreoPorNombre(nombreCompletoTecnico);
        
        // Validar que se haya encontrado el correo
        if (correoTecnico == null || correoTecnico.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, 
                "No se encontró correo electrónico para el técnico: " + nombreCompletoTecnico, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            
            // Debug: mostrar información
            System.out.println("Técnico sin correo: " + nombreCompletoTecnico);
            System.out.println("ELS: " + reparacion.getELS());
            System.out.println("Cliente: " + reparacion.getCliente());
            return;
        }
        
        // Debug: confirmar que se obtuvo el correo
        System.out.println("Enviando aviso de respuesta del cliente");
        System.out.println("Técnico: " + nombreCompletoTecnico);
        System.out.println("Correo técnico: " + correoTecnico);
        System.out.println("ELS: " + reparacion.getELS());
        System.out.println("Cliente: " + reparacion.getCliente());
        
        // Enviar aviso con el correo del técnico
        enviarAviso(ventana, correoTecnico, 
            "¿Desea enviar el aviso de 'Respuesta del Cliente'?", 
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
        presentacion.vista.VentanaProgreso progreso = new presentacion.vista.VentanaProgreso("ENVIANDO CORREO");
        progreso.mostrar();

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String els = ventana.getTextELS().toString();
                    String cliente = ventana.getTextCliente().getText();
                    String sucursal = ventana.getTextSucursal().getText();
                    ReparacionDTO reparacion = controlador.getGestorVisualizacion().getReparacionActual();
                    
				String error = null;
					switch (tipoAviso) {
                        case "RESPUESTA_CLIENTE":
                            error = mails.EnviarMail.enviarAvisoRespuestaClienteSinDialogo(correo, els,
                                cliente, sucursal, reparacion.getEstadoComercial());
                            break;
                        case "EQUIPO_LISTO":
                            error = mails.EnviarMail.enviarAvisoEquipoTerminadoSinDialogo(correo, els,
                                cliente, sucursal);
                            break;
                        case "INFORME":
                            error = mails.EnviarMail.enviarAvisoInformeSinDialogo(correo, els,
                                cliente, sucursal);
                            break;
                    }

					progreso.cerrar();

					if (error != null) {
						JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
						return null;
					}

					JOptionPane.showMessageDialog(null,
							"El correo se envi\u00f3 exitosamente.",
							"Confirmaci\u00f3n de env\u00edo", JOptionPane.INFORMATION_MESSAGE);

					if (tipoAviso.equals("INFORME")) {
						SwingUtilities.invokeLater(() -> {
							ventana.setChckbxAvisoEnviado(true);
						});
						reparacion.setAvisoEnviado(true);
						agenda.editarReparacionR(reparacion);
					}
				} catch (Exception ex) {
					progreso.cerrar();
					LogDAO.error("Error al enviar presupuesto por mail", ex);
				}
                return null;
            }
            
            @Override
            protected void done() {
                if (tipoAviso.equals("INFORME")) {
                    refrescarPantalla(ventana);
                }
            }
        };
        
        worker.execute();
    }

    
    
    /**
     * Refresca pantalla después de operación
     */
    private void refrescarPantalla(VentanaVisualizarEquipos ventana) {
        try {
            int els = Integer.parseInt(ventana.getTextELS());
            controlador.getGestorVisualizacion().cargarDatosEquipo(ventana, els);
        } catch (Exception ex) {
            LogDAO.error("Error al refrescar pantalla", ex);
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

    public void abrirVentanaCopiarFactura(VentanaVisualizarEquipos ventana) {
        DatosFactura datos = ExtractorFacturaPDF.extraerDesdeSelector(ventana);
        if (datos != null) {
            datos.imprimirEnConsola();
            
            // Preguntar si desea copiar el número de factura
            int respuesta = JOptionPane.showConfirmDialog(
                ventana,
                "¿Desea copiar el número de factura?\n\n" + 
                "Número: " + datos.getNumeroComprobante(),
                "Confirmar copia",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (respuesta == JOptionPane.YES_OPTION) {
                ventana.getTextNumeroFactura().setText(datos.getNumeroComprobante());

            }
        }
    }
    
    
    
    public void abrirEnvioCorreoPresupuestoExistente(int numeroELS) {
        controladorPresupuestos.abrirEnvioCorreoPresupuestoExistente(numeroELS);
    }
    
}
