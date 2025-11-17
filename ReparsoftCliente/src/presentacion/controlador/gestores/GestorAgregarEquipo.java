package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import VistaPropias.AutoCompletarComboBox;
import dto.ClienteDTO;
import dto.ReparacionDTO;
import dto.SucursalDTO;
import dto.RegistroEntradaReporteDTO;
import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.vista.VentanaAgregarEquipo;
import presentacion.reportes.ReporteRegistroEntrada;

/**
 * GestorAgregarEquipo
 * Responsable de:
 * - Abrir ventana de agregar equipo
 * - Llenar combos (clientes, marcas, modelos, series)
 * - Validar datos ingresados
 * - Guardar nuevo equipo
 * - Generar registro de ingreso
 * - Verificar ingreso anterior
 */
public class GestorAgregarEquipo {
    
    private ControladorReparacion controlador;
    private Agenda agenda;
    private VentanaAgregarEquipo ventanaAgregarEquipo;
    
    private GestorDatos gestorDatos;
    private GestorInterfazEquipos gestorInterfaz;
    
    private int idClienteSeleccionado = 0;
    private int idSucursalSeleccionada = 0;
    private List<String> caracteresNoValidos = new ArrayList<>();
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    private boolean procesandoCliente = false;
    private boolean procesandoMarca = false;
    private boolean procesandoModelo = false;
    
    /**
     * Constructor
     */
    public GestorAgregarEquipo(ControladorReparacion controlador, Agenda agenda) {
        this.controlador = controlador;
        this.agenda = agenda;
        this.gestorDatos = new GestorDatos(agenda);
        this.gestorInterfaz = new GestorInterfazEquipos();
    }
    
    /**
     * Abre la ventana para agregar equipo
     */
    public void abrirVentanaAgregarEquipo() {
        ventanaAgregarEquipo = new VentanaAgregarEquipo(controlador);
        controlador.setVentanaAgregarEquipo(ventanaAgregarEquipo);
        
        // Configurar fecha por defecto
        Calendar c2 = new GregorianCalendar();
        ventanaAgregarEquipo.getFechaEntrada().setCalendar(c2);
        
        // Establecer ELS
        int els = obtenerNumeroELS();
        ventanaAgregarEquipo.setTextELS(Integer.toString(els));
        
        // Agregar listeners
        agregarListeners();
        
        // Llenar combos
        llenarCombos();
        
        // Habilitar autocomplete
        habilitarAutoComplete();
        
        // Habilitar menú contextual
        gestorInterfaz.habilitarMenuContextual(ventanaAgregarEquipo);
    }
    
    /**
     * Agrega listeners a la ventana
     */
    private void agregarListeners() {
        ventanaAgregarEquipo.getComboClientes().addActionListener(e -> procesarClienteSeleccionado());
        ventanaAgregarEquipo.getComboMarca().addActionListener(e -> procesarMarcaSeleccionada());
        ventanaAgregarEquipo.getComboModelo().addActionListener(e -> procesarModeloSeleccionado());
        
        ventanaAgregarEquipo.getBotonGuardar().addActionListener(e -> guardarEquipo());
        ventanaAgregarEquipo.getBotonGenerarRegistro().addActionListener(e -> generarRegistroIngreso());
        ventanaAgregarEquipo.getBotonNuevaReparacion().addActionListener(e -> nuevaReparacion());
        ventanaAgregarEquipo.getBtnGenerarSerie().addActionListener(e -> generarSerie());
        ventanaAgregarEquipo.getBtnFechaDefault().addActionListener(e -> fechaDefault());
        ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().addActionListener(e -> verificarIngresoAnterior());
        ventanaAgregarEquipo.getBtnaltaCliente().addActionListener(e -> abrirVentanaCliente());
    }
    
    /**
     * Llena todos los combos iniciales
     */
    private void llenarCombos() {
        try {
            agenda.ListarCliente(ventanaAgregarEquipo.getComboClientes());
            agenda.ListarSucursales(ventanaAgregarEquipo.getComboSucursal());
            agenda.ListarEquipo(ventanaAgregarEquipo.getComboNombreEquipo());
            agenda.ListarMarca(ventanaAgregarEquipo.getComboMarca());
            
            ventanaAgregarEquipo.getComboClientes().setSelectedIndex(0);
            ventanaAgregarEquipo.getComboMarca().setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Habilita autocompletado en combos
     */
    private void habilitarAutoComplete() {
        AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboClientes(), false, true);
        AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboSucursal(), false, true);
        AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboNombreEquipo(), true, false);
        AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboMarca(), true, false);
        AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboModelo(), true, false);
        AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboSerie(), true, false);
    }
    
    /**
     * Procesa selección de cliente
     */
    private void procesarClienteSeleccionado() {
        if (procesandoCliente) return;
        procesandoCliente = true;
        
        try {
            Object selectedItem = ventanaAgregarEquipo.getComboClientes().getSelectedItem();
            if (selectedItem instanceof ClienteDTO) {
                ClienteDTO cliente = (ClienteDTO) selectedItem;
                idClienteSeleccionado = cliente.getId();
                
                // Llenar sucursales
                ventanaAgregarEquipo.getComboSucursal().removeAllItems();
                agenda.ListarSucursalesxCliente(ventanaAgregarEquipo.getComboSucursal(), 
                    idClienteSeleccionado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar sucursales: " + ex.getMessage());
        } finally {
            procesandoCliente = false;
        }
    }
    
    /**
     * Procesa selección de marca
     */
    private void procesarMarcaSeleccionada() {
        if (procesandoMarca) return;
        procesandoMarca = true;
        
        try {
            Object selectedItem = ventanaAgregarEquipo.getComboMarca().getSelectedItem();
            if (selectedItem != null) {
                String marca = selectedItem.toString();
                ventanaAgregarEquipo.getComboModelo().removeAllItems();
                ventanaAgregarEquipo.getComboSerie().removeAllItems();
                agenda.ListarModelosxMarca(ventanaAgregarEquipo.getComboModelo(), marca);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar modelos: " + ex.getMessage());
        } finally {
            procesandoMarca = false;
        }
    }
    
    /**
     * Procesa selección de modelo
     */
    private void procesarModeloSeleccionado() {
        if (procesandoModelo) return;
        procesandoModelo = true;
        
        try {
            Object selectedItem = ventanaAgregarEquipo.getComboModelo().getSelectedItem();
            if (selectedItem != null) {
                String modelo = selectedItem.toString();
                ventanaAgregarEquipo.getComboSerie().removeAllItems();
                agenda.ListarSeriexModelo(ventanaAgregarEquipo.getComboSerie(), modelo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar series: " + ex.getMessage());
        } finally {
            procesandoModelo = false;
        }
    }
    
    /**
     * Valida datos ingresados
     */
    private boolean validarDatos() {
        if (idClienteSeleccionado == 0) {
            JOptionPane.showMessageDialog(null, "'CLIENTE' es obligatorio.", 
                "Validación", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        if (ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem() == null ||
            ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "'NOMBRE DE EQUIPO' es obligatorio.", 
                "Validación", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        if (ventanaAgregarEquipo.getComboModelo().getSelectedItem() == null ||
            ventanaAgregarEquipo.getComboModelo().getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "'MODELO' es obligatorio.", 
                "Validación", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        if (ventanaAgregarEquipo.getComboMarca().getSelectedItem() == null ||
            ventanaAgregarEquipo.getComboMarca().getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "'MARCA' es obligatorio.", 
                "Validación", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Guarda el equipo nuevo
     */
    public void guardarEquipo() {
        if (!validarDatos()) return;
        
        int opcion = JOptionPane.showConfirmDialog(null, "¿Desea guardar este equipo?", 
            "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (opcion != JOptionPane.YES_OPTION) return;
        
        try {
            ReparacionDTO nuevoReparacion = gestorDatos.extraerDatosAgregar(ventanaAgregarEquipo, 
                idClienteSeleccionado, idSucursalSeleccionada);
            
            if (nuevoReparacion != null) {
                agenda.agregarReparacionR(nuevoReparacion);
                
                // Deshabilitar campos
                deshabilitarCamposPostGuardado();
                
                // Preguntar por registro de ingreso
                int opcionRegistro = JOptionPane.showConfirmDialog(null, 
                    "¿Generar Registro de Ingreso?", "Aviso", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (opcionRegistro == JOptionPane.YES_OPTION) {
                    generarRegistroIngreso();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Deshabilita campos después de guardar
     */
    private void deshabilitarCamposPostGuardado() {
        ventanaAgregarEquipo.getComboClientes().setEnabled(false);
        ventanaAgregarEquipo.getComboMarca().setEnabled(false);
        ventanaAgregarEquipo.getComboModelo().setEnabled(false);
        ventanaAgregarEquipo.getComboSerie().setEnabled(false);
        ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(true);
    }
    
    /**
     * Genera registro de ingreso
     */
    private void generarRegistroIngreso() {
        if (!validarDatos()) return;
        
        try {
            List<RegistroEntradaReporteDTO> lista = new ArrayList<>();
            RegistroEntradaReporteDTO rep = gestorDatos.extraerRegistroIngreso(ventanaAgregarEquipo, 
                idClienteSeleccionado, idSucursalSeleccionada);
            
            if (rep != null) {
                lista.add(rep);
                ReporteRegistroEntrada reporte = new ReporteRegistroEntrada(rep, lista, agenda);
                reporte.mostrar();
                reporte.guardar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar registro: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Inicia nueva reparación limpiando campos
     */
    private void nuevaReparacion() {
        int els = obtenerNumeroELS();
        ventanaAgregarEquipo.setTextELS(Integer.toString(els));
        
        // Limpiar todos los campos
        ventanaAgregarEquipo.getTextAvisoCliente().setText("");
        ventanaAgregarEquipo.getTextClienteCliente().setText("");
        ventanaAgregarEquipo.getTextFalla().setText("");
        ventanaAgregarEquipo.getTextRemitoCliente().setText("");
        
        // Resetear combos
        ventanaAgregarEquipo.getComboClientes().setSelectedIndex(0);
        ventanaAgregarEquipo.getComboMarca().setSelectedItem("");
        ventanaAgregarEquipo.getComboModelo().setSelectedItem("");
        ventanaAgregarEquipo.getComboSerie().setSelectedItem("");
        ventanaAgregarEquipo.setTextFechafabricacion2(null);
        
        // Habilitar campos
        ventanaAgregarEquipo.getComboClientes().setEnabled(true);
        ventanaAgregarEquipo.getComboMarca().setEnabled(true);
        ventanaAgregarEquipo.getComboModelo().setEnabled(true);
        ventanaAgregarEquipo.getComboSerie().setEnabled(true);
        ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(false);
    }
    
    /**
     * Genera número de serie aleatorio
     */
    private void generarSerie() {
        int opcion = JOptionPane.showConfirmDialog(null, 
            "¿Desea generar un Número de Serie?", "Confirmación", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            String seriGenerada = generarTextoAleatorio();
            ventanaAgregarEquipo.getComboSerie().setSelectedItem(seriGenerada);
        }
    }
    
    /**
     * Genera texto aleatorio para serie
     */
    private String generarTextoAleatorio() {
        java.security.SecureRandom random = new java.security.SecureRandom();
        String text = new java.math.BigInteger(25, random).toString(32);
        return text.toUpperCase();
    }
    
    /**
     * Establece fecha por defecto (01/01/0001)
     */
    private void fechaDefault() {
        int opcion = JOptionPane.showConfirmDialog(null, 
            "¿Desea colocar la fecha default 01/01/0001?", "Confirmación", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                java.util.Date defaultDate = dateFormat.parse("00010101");
                ventanaAgregarEquipo.setTextFechafabricacion2(defaultDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Verifica si hubo ingreso anterior del mismo equipo
     */
    private void verificarIngresoAnterior() {
        // Delegar a clase específica de verificación
        new GestorVerificacionIngresoAnterior(controlador, agenda, ventanaAgregarEquipo).mostrar();
    }
    
    /**
     * Abre ventana de cliente
     */
    private void abrirVentanaCliente() {
        controlador.getControladorCliente().setLlamadoDesdeAgregarEquipo(true);
        controlador.getControladorCliente().agregarListenersVentanaCliente();
        llenarCombos(); // Actualizar combos después de agregar cliente
    }
    
    /**
     * Procesa eventos delegados
     */
    public void procesarEventos(ActionEvent e) {
        if (ventanaAgregarEquipo == null) return;
        
        if (e.getSource() == ventanaAgregarEquipo.getBotonGuardar()) {
            guardarEquipo();
        } else if (e.getSource() == ventanaAgregarEquipo.getBotonGenerarRegistro()) {
            generarRegistroIngreso();
        } else if (e.getSource() == ventanaAgregarEquipo.getBotonNuevaReparacion()) {
            nuevaReparacion();
        }
    }
    
    /**
     * Obtiene número de ELS siguiente
     */
    private int obtenerNumeroELS() {
        String ubicacion = agenda.getUbicacionBase();
        if (ubicacion.equals("Buenos Aires")) {
            return agenda.dameNumeroELSbsas() + 1;
        } else {
            return agenda.dameNumeroELS() + 1;
        }
    }
    
    /**
     * Getters
     */
    public VentanaAgregarEquipo getVentanaAgregarEquipo() {
        return ventanaAgregarEquipo;
    }
}
