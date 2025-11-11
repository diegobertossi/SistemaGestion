package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.vista.VentanaVisualizarEquipos;
import tiposPropios.MonedaFormatter;
import com.inet.jortho.SpellChecker;




/**
 * GestorListadoEquipos
 * Responsable de:
 * - Gestionar múltiples ventanas abiertas de visualización
 * - Cargar datos en ventanas de listado
 * - Actualizar datos en ventanas de listado
 * - Agregar listeners específicos para ventanas de listado
 * - Mantener coherencia entre ventanas múltiples
 */
public class GestorListadoEquipos {
    
    private ControladorReparacion controlador;
    private Agenda agenda;
    
    private List<VentanaVisualizarEquipos> ventanasAbiertas;
    private boolean actualizarEnListado = false;
    private MonedaFormatter monedaFormatter;
    
    /**
     * Constructor
     */
    public GestorListadoEquipos(ControladorReparacion controlador, Agenda agenda) {
        this.controlador = controlador;
        this.agenda = agenda;
        this.ventanasAbiertas = new ArrayList<>();
        this.monedaFormatter = new MonedaFormatter();
    }
    
    /**
     * Carga datos en una ventana de listado por primera vez
     */
    public VentanaVisualizarEquipos tomarDatosDeTablasListado(int numeroELS) throws ParseException {
        VentanaVisualizarEquipos ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
        ventanaVisualizarEquipos.setTitle(String.valueOf(numeroELS));
        ventanasAbiertas.add(ventanaVisualizarEquipos);
        
        cerrarVentanaVisualizarEquipoListado(ventanaVisualizarEquipos);
        
        monedaFormatter = new MonedaFormatter();
        controlador.getControladorUsuLogin().verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
        SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());
        
        ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELS));
        
        // Cargar datos en la ventana
        controlador.getGestorVisualizacion().cargarDatosEquipo(ventanaVisualizarEquipos, numeroELS);
        
        // Agregar listeners específicos para listado
        agregarListenersVentanaVisualizarEquiposListado(ventanaVisualizarEquipos);
        
        return ventanaVisualizarEquipos;
    }
    
    /**
     * Actualiza datos en una ventana de listado ya abierta
     */
    public VentanaVisualizarEquipos actualizarDatosDeTablasListado(int numeroELS, 
                                                                   VentanaVisualizarEquipos ventanaVisualizarEquipos) 
                                                                   throws ParseException {
        // Si es la primera vez, crear nueva ventana
        if (!actualizarEnListado) {
            ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
            ventanaVisualizarEquipos.setTitle(String.valueOf(numeroELS));
            ventanasAbiertas.add(ventanaVisualizarEquipos);
            cerrarVentanaVisualizarEquipoListado(ventanaVisualizarEquipos);
        }
        
        monedaFormatter = new MonedaFormatter();
        controlador.getControladorUsuLogin().verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
        SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());
        
        ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELS));
        
        // Cargar datos en la ventana
        controlador.getGestorVisualizacion().cargarDatosEquipo(ventanaVisualizarEquipos, numeroELS);
        
        return ventanaVisualizarEquipos;
    }
    
    /**
     * Agrega listeners específicos para ventanas de listado
     */
    public void agregarListenersVentanaVisualizarEquiposListado(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        actualizarEnListado = true;
        
        // Edición
        ventanaVisualizarEquipos.getBtnEditar().addActionListener(e -> 
            controlador.getGestorVisualizacion().editar(ventanaVisualizarEquipos));
        
        ventanaVisualizarEquipos.getBtnGuardarCambios().addActionListener(e -> 
            controlador.getGestorVisualizacion().guardarCambios(ventanaVisualizarEquipos));
        
        // Presupuesto
        ventanaVisualizarEquipos.getBotonPresupuestar().addActionListener(e -> 
            controlador.getGestorPresupuesto().abrirPresupuesto(ventanaVisualizarEquipos));
        
        // Excel
        ventanaVisualizarEquipos.getBtnabrirExcel().addActionListener(e -> 
            abrirExcel(ventanaVisualizarEquipos));
        
        // Facturación
        ventanaVisualizarEquipos.getBtnfacturar().addActionListener(e -> 
            controlador.getGestorPresupuesto().abrirFacturacion(ventanaVisualizarEquipos));
        
        // Registro de ingreso
        ventanaVisualizarEquipos.getBotonRegistroIngreso().addActionListener(e -> 
            generarRegistroIngreso(ventanaVisualizarEquipos));
        
        // Correo WSP
        ventanaVisualizarEquipos.getBtnenviarCorreoOwsp().addActionListener(e -> 
            abrirEnviarCorreoWSP(ventanaVisualizarEquipos));
        
        // Estados
        ventanaVisualizarEquipos.getBotonEditarEstados().addActionListener(e -> {
            controlador.getGestorVisualizacion().editar(ventanaVisualizarEquipos);
        });
        
        // Remito
        ventanaVisualizarEquipos.getBtnGenerarRemito().addActionListener(e -> 
            controlador.getGestorPresupuesto().generarRemito(ventanaVisualizarEquipos));
        
        // Repuestos
        ventanaVisualizarEquipos.getBtnRepuestos().addActionListener(e -> 
            controlador.getGestorRepuestos().abrirVentanaRepuestos(ventanaVisualizarEquipos));
        
        ventanaVisualizarEquipos.getBtnEditarRepuesto().addActionListener(e -> 
            controlador.getGestorRepuestos().editarRepuesto(ventanaVisualizarEquipos));
        
        ventanaVisualizarEquipos.getBtnEliminarRepuesto().addActionListener(e -> 
            controlador.getGestorRepuestos().eliminarRepuesto(ventanaVisualizarEquipos));
        
        // Avisos
        ventanaVisualizarEquipos.getBotonAvisoInforme().addActionListener(e -> 
            controlador.getGestorPresupuesto().enviarAvisoInforme(ventanaVisualizarEquipos));
        
        ventanaVisualizarEquipos.getBotonAvisoEquipoListo().addActionListener(e -> 
            controlador.getGestorPresupuesto().enviarAvisoEquipoListo(ventanaVisualizarEquipos));
        
        ventanaVisualizarEquipos.getBotonRespuestaAlTecnico().addActionListener(e -> 
            controlador.getGestorPresupuesto().enviarRespuestaCliente(ventanaVisualizarEquipos));
        
        // Precios
        controlador.getGestorInterfaz().agregarListenersPrecios(ventanaVisualizarEquipos);
    }
    
    /**
     * Genera registro de ingreso
     */
    /**
     * Genera registro de ingreso desde ventana de visualización
     */
    private void generarRegistroIngreso(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        try {
            List<dto.RegistroEntradaReporteDTO> lista = new ArrayList<>();
            
            // Extraer datos de la reparación actual
            int els = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());
            String falla = ventanaVisualizarEquipos.getTextFalla().getText();
            String remito = ventanaVisualizarEquipos.getTextRemitoCliente().getText();
            String nombreEquipo = ventanaVisualizarEquipos.getTextNombreEquipo().getText();
            String modelo = ventanaVisualizarEquipos.getTextModelo().getText();
            String marca = ventanaVisualizarEquipos.getTextMarca().getText();
            String serie = ventanaVisualizarEquipos.getTextNSerie().getText();
            String aviso = ventanaVisualizarEquipos.getTextAvisoCliente().getText();
            String clienteCliente = ventanaVisualizarEquipos.getTextClienteCliente().getText();
            String cliente = ventanaVisualizarEquipos.getTextCliente().getText();
            String sucursal = ventanaVisualizarEquipos.getTextSucursal().getText();
            
            // Obtener IDs
            int idCliente = agenda.idClienteporNombre(cliente);
            int idSucursal = agenda.idSucursalporNombre(sucursal, idCliente);
            int idEquipo = agenda.dameIDequipo();
            
            // Parsear fecha
            java.util.Date fechaEntradaVisual = ventanaVisualizarEquipos.getFechaEntrada().getDate();
            String fechaEntrada = null;
            if (fechaEntradaVisual != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                fechaEntrada = dateFormat.format(fechaEntradaVisual);
            }
            
            // Obtener estado físico
            String estadoFisico = ventanaVisualizarEquipos.getTextEstadoFisico().getText();
            String estadoTecnico = "Sin Revisar";
            
            // Crear DTO
            dto.RegistroEntradaReporteDTO rep = new dto.RegistroEntradaReporteDTO(
                els, fechaEntrada, falla, estadoFisico, estadoTecnico, remito, idEquipo, 
                nombreEquipo, modelo, marca, serie, aviso, clienteCliente, idCliente, 
                idSucursal, cliente, sucursal);
            
            if (rep != null) {
                lista.add(rep);
                presentacion.reportes.ReporteRegistroEntrada reporte = 
                    new presentacion.reportes.ReporteRegistroEntrada(rep, lista, agenda);
                reporte.mostrar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar registro: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    
    /**
     * Abre Excel
     */
    private void abrirExcel(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        // Delegar a controlador de Excel si existe
        VistaPropias.GestorArchivosExcel gestorExcel = 
            new VistaPropias.GestorArchivosExcel(agenda.getUbicacionBase());
        gestorExcel.setUbicacionBase(agenda.getUbicacionBase());
        gestorExcel.abrirReparaciones();
    }
    
    /**
     * Abre ventana de enviar correo por WSP
     */
    private void abrirEnviarCorreoWSP(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
        presentacion.vista.VentanaEnviarCorreoOwsp ventanaEnviarCorreoOwsp = 
            new presentacion.vista.VentanaEnviarCorreoOwsp(controlador);
        
        ventanaEnviarCorreoOwsp.getBtnEnviarWST().addActionListener(e -> 
            controlador.getGestorClientesWSP().abrirVentanaWSP());
        
        ventanaEnviarCorreoOwsp.setVisible(true);
    }
    
    /**
     * Cierra ventana de listado
     */
    private void cerrarVentanaVisualizarEquipoListado(VentanaVisualizarEquipos ventana) {
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                boolean guardado = controlador.getGestorVisualizacion().isGuardado();
                
                if (!guardado) {
                    int opcion = JOptionPane.showConfirmDialog(null,
                        "Hay cambios sin guardar. ¿Desea guardar antes de salir?",
                        "Aviso", JOptionPane.YES_NO_CANCEL_OPTION);
                    
                    if (opcion == JOptionPane.YES_OPTION) {
                        controlador.getGestorVisualizacion().guardarCambios(ventana);
                    } else if (opcion == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                
                ventanasAbiertas.remove(ventana);
                ventana.dispose();
                
                if (ventanasAbiertas.isEmpty()) {
                    actualizarEnListado = false;
                }
            }
        });
    }
    
    /**
     * Procesa eventos delegados
     */
    public void procesarEventos(ActionEvent e) {
        // Delegación de eventos específicos de listado
    }
    
    /**
     * Getters y Setters
     */
    public List<VentanaVisualizarEquipos> getVentanasAbiertas() {
        return ventanasAbiertas;
    }
    
    public void agregarVentanaAbierta(VentanaVisualizarEquipos ventana) {
        if (!ventanasAbiertas.contains(ventana)) {
            ventanasAbiertas.add(ventana);
        }
    }
    
    public void removerVentanaAbierta(VentanaVisualizarEquipos ventana) {
        ventanasAbiertas.remove(ventana);
    }
    
    public int cantidadVentanasAbiertas() {
        return ventanasAbiertas.size();
    }
    
    public boolean isActualizarEnListado() {
        return actualizarEnListado;
    }
    
    public void setActualizarEnListado(boolean actualizar) {
        this.actualizarEnListado = actualizar;
    }
}
