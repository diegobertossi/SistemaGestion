package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Agenda;
import persistencia.conexion.Conexion;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.controlador.gestores.GestorAgregarEquipo;
import presentacion.controlador.gestores.GestorBusqueda;
import presentacion.controlador.gestores.GestorClientesWSP;
import presentacion.controlador.gestores.GestorDatos;
import presentacion.controlador.gestores.GestorInterfazEquipos;
import presentacion.controlador.gestores.GestorListadoEquipos;
import presentacion.controlador.gestores.GestorPresupuestoFactura;
import presentacion.controlador.gestores.GestorRepuestos;
import presentacion.controlador.gestores.GestorVisualizacionEquipos;
import presentacion.vista.VentanaAgregarEquipo;

/**
 * ControladorReparacion - REFACTORIZADO
 * Orquestador principal que coordina todos los gestores especializados
 * Esta clase AHORA actúa solo como punto de coordinación central
 * 
 * Responsabilidades MÍNIMAS:
 * - Instanciar gestores
 * - Rutear eventos principales
 * - Coordinar entre ventanas
 * - Mantener referencias globales de gestores
 * - Exponer métodos públicos utilizados por otras clases
 */
public class ControladorReparacion implements ActionListener, MouseListener, KeyListener, ItemListener {
    
    // ==== REFERENCIAS DE VENTANAS ====
    private VentanaEquipos ventanaEquipos;
    private VentanaVisualizarEquipos ventanaVisualizarEquipos;
    private VentanaAgregarEquipo ventanaAgregarEquipo;
    
    // ==== GESTORES ESPECIALIZADOS ====
    private GestorVisualizacionEquipos gestorVisualizacion;
    private GestorAgregarEquipo gestorAgregar;
    private GestorClientesWSP gestorClientesWSP;
    private GestorRepuestos gestorRepuestos;
    private GestorBusqueda gestorBusqueda;
    private GestorPresupuestoFactura gestorPresupuesto;
    private GestorListadoEquipos gestorListado;
    private GestorDatos gestorDatos;
    private GestorInterfazEquipos gestorInterfaz;
    
    // ==== OTROS CONTROLADORES ====
    private ControladorUsuLogin controladorUsuLogin;
    private ControladorPresupuestos controladorPresupuestos;
    private ControladorSalidas controladorSalidas;
    private ControladorCliente controladorCliente;
    
    
    // ==== DATOS ====
    private Agenda agenda;
    private List<VentanaVisualizarEquipos> ventanasAbiertas = new ArrayList<>();
    
    /**
     * Constructor principal
     */
    public ControladorReparacion(VentanaEquipos ventanaEquipos, 
                                 ControladorUsuLogin controladorUsuLogin, 
                                 Agenda agenda,
                                 ControladorPresupuestos controladorPresupuestos, 
                                 ControladorSalidas controladorSalidas,
                                 ControladorCliente controladorCliente) {
        this.ventanaEquipos = ventanaEquipos;
        this.agenda = agenda;
        this.controladorUsuLogin = controladorUsuLogin;
        this.controladorPresupuestos = controladorPresupuestos;
        this.controladorSalidas = controladorSalidas;
        this.controladorCliente = controladorCliente;
        
        // Instanciar gestores
        inicializarGestores();
        
        // Agregar listeners principales
        this.ventanaEquipos.getBtnVisualizarEquipos().addActionListener(this);
        this.ventanaEquipos.getBtnAgregarEquipos().addActionListener(this);
    }
    
    /**
     * Inicializa todos los gestores especializados
     */
    private void inicializarGestores() {
        this.gestorDatos = new GestorDatos(agenda);
        this.gestorInterfaz = new GestorInterfazEquipos();
        this.gestorVisualizacion = new GestorVisualizacionEquipos(this, agenda, controladorUsuLogin);
        this.gestorAgregar = new GestorAgregarEquipo(this, agenda);
        this.gestorClientesWSP = new GestorClientesWSP(gestorVisualizacion, agenda);
        this.gestorRepuestos = new GestorRepuestos(this, agenda);
        this.gestorBusqueda = new GestorBusqueda(this, agenda);
        this.gestorPresupuesto = new GestorPresupuestoFactura(this, agenda, controladorPresupuestos, controladorSalidas);
        this.gestorListado = new GestorListadoEquipos(this, agenda);
    }
    
    /**
     * Método principal de routing de eventos
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Navegación principal
        if (e.getSource() == ventanaEquipos.getBtnVisualizarEquipos()) {
            gestorVisualizacion.abrirVentanaVisualizarEquipos();
            ventanaEquipos.dispose();
        } 
        else if (e.getSource() == ventanaEquipos.getBtnAgregarEquipos()) {
           
        	
        	if (controladorUsuLogin.getUsu_login().getIdRol() != 1) {
        	if (Conexion.isModoAntigua()) {
                JOptionPane.showMessageDialog(
                    ventanaEquipos,
                    "NO ES POSIBLE ACCEDER A ESTE MÓDULO CON DATOS ANTIGUOS.",
                    "Módulo no disponible",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }}
            gestorAgregar.abrirVentanaAgregarEquipo();
            ventanaEquipos.dispose();
        }
        // Delegar a gestores específicos
        else {
            gestorVisualizacion.procesarEventos(e);
            //gestorAgregar.procesarEventos(e);
            gestorClientesWSP.procesarEventos(e);
            gestorRepuestos.procesarEventos(e);
            gestorBusqueda.procesarEventos(e);
            gestorPresupuesto.procesarEventos(e);
            gestorListado.procesarEventos(e);
        }
    }
    
    /**
     * Setters para ventanas (usados por gestores)
     */
    public void setVentanaVisualizarEquipos(VentanaVisualizarEquipos ventana) {
        this.ventanaVisualizarEquipos = ventana;
    }
    
    public void setVentanaAgregarEquipo(VentanaAgregarEquipo ventana) {
        this.ventanaAgregarEquipo = ventana;
    }
    
    /**
     * Getters para ventanas y datos
     */
    public VentanaEquipos getVentanaEquipos() {
        return ventanaEquipos;
    }
    
    public VentanaVisualizarEquipos getVentanaVisualizarEquipos() {
        return ventanaVisualizarEquipos;
    }
    
    public VentanaAgregarEquipo getVentanaAgregarEquipo() {
        return ventanaAgregarEquipo;
    }
    
    public Agenda getAgenda() {
        return agenda;
    }
    
    public ControladorUsuLogin getControladorUsuLogin() {
        return controladorUsuLogin;
    }
    
    public ControladorPresupuestos getControladorPresupuestos() {
        return controladorPresupuestos;
    }
    
    public ControladorSalidas getControladorSalidas() {
        return controladorSalidas;
    }
    
    public ControladorCliente getControladorCliente() {
        return controladorCliente;
    }
    
    public List<VentanaVisualizarEquipos> getVentanasAbiertas() {
        return ventanasAbiertas;
    }
    
    public void agregarVentanaAbierta(VentanaVisualizarEquipos ventana) {
        ventanasAbiertas.add(ventana);
    }
    
    public void removerVentanaAbierta(VentanaVisualizarEquipos ventana) {
        ventanasAbiertas.remove(ventana);
    }
    
    /**
     * Getters para gestores (acceso desde otras clases si es necesario)
     */
    public GestorVisualizacionEquipos getGestorVisualizacion() {
        return gestorVisualizacion;
    }
    
    public GestorAgregarEquipo getGestorAgregar() {
        return gestorAgregar;
    }
    
    public GestorClientesWSP getGestorClientesWSP() {
        return gestorClientesWSP;
    }
    
    public GestorRepuestos getGestorRepuestos() {
        return gestorRepuestos;
    }
    
    public GestorBusqueda getGestorBusqueda() {
        return gestorBusqueda;
    }
    
    public GestorPresupuestoFactura getGestorPresupuesto() {
        return gestorPresupuesto;
    }
    
    public GestorListadoEquipos getGestorListado() {
        return gestorListado;
    }
    
    public GestorDatos getGestorDatos() {
        return gestorDatos;
    }
    
    public GestorInterfazEquipos getGestorInterfaz() {
        return gestorInterfaz;
    }
    
    /**
     * Métodos públicos para gestión de listado
     * (usados por clases que abren múltiples ventanas de visualización)
     */
    public VentanaVisualizarEquipos tomarDatosDeTablasListado(int numeroELS, 
                                                              VentanaVisualizarEquipos ventana) throws ParseException {
        return gestorListado.tomarDatosDeTablasListado(numeroELS);
    }
    
    public VentanaVisualizarEquipos actualizarDatosDeTablasListado(int numeroELS, 
                                                                   VentanaVisualizarEquipos ventana) throws ParseException {
        return gestorListado.actualizarDatosDeTablasListado(numeroELS, ventana);
    }
    
    public void agregarListenersVentanaVisualizarEquiposListado(VentanaVisualizarEquipos ventana) {
        gestorListado.agregarListenersVentanaVisualizarEquiposListado(ventana);
    }
    
    public int cantidadVentanasAbiertas() {
        return gestorListado.cantidadVentanasAbiertas();
    }
    
    /**
     * Métodos públicos para edición y guardado
     * (usados por otras clases del proyecto)
     */
    public void guardarCambios(VentanaVisualizarEquipos ventana) {
        gestorVisualizacion.guardarCambios(ventana);
    }
    
    public void editar(VentanaVisualizarEquipos ventana) {
        gestorVisualizacion.editar(ventana);
    }
    
    public void procesarNavegacion(String tipo) {
        gestorVisualizacion.procesarNavegacion(tipo);
    }
    
    // ===== IMPLEMENTACIÓN DE INTERFACES (vacías, delegadas) ====
    @Override
    public void mouseClicked(MouseEvent e) {
        gestorRepuestos.mouseClicked(e);
        gestorClientesWSP.mouseClicked(e);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {
       // gestorRepuestos.keyReleased(e);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void itemStateChanged(ItemEvent e) {}


}
