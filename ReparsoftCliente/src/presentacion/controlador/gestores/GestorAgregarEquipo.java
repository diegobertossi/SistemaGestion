package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        cerraVentanaAgregarEquipo();
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
    

        public void cerraVentanaAgregarEquipo() {
            this.ventanaAgregarEquipo.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    int opcion = JOptionPane.showConfirmDialog(ventanaAgregarEquipo,
                            "¿Desea salir de la ventana 'AGREGAR EQUIPO'?", "Aviso", JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if (opcion == JOptionPane.YES_OPTION) {
                        ventanaAgregarEquipo.dispose();
                        ventanaAgregarEquipo = null;
                    }
                }
            });
        }
		
	

    /**
     * Agrega listeners a la ventana
     */
    private void agregarListeners() {
        ventanaAgregarEquipo.getComboClientes().addActionListener(e -> procesarClienteSeleccionado());
        ventanaAgregarEquipo.getComboSucursal().addActionListener(e -> procesarSucursalSeleccionada());
        ventanaAgregarEquipo.getComboMarca().addActionListener(e -> procesarMarcaSeleccionada());
        ventanaAgregarEquipo.getComboModelo().addActionListener(e -> procesarModeloSeleccionado());
        
        ventanaAgregarEquipo.getBotonGuardar().addActionListener(e -> guardarEquipo());
        ventanaAgregarEquipo.getBotonGenerarRegistro().addActionListener(e -> generarRegistroIngreso());
        ventanaAgregarEquipo.getBotonNuevaReparacion().addActionListener(e -> nuevaReparacion());
        ventanaAgregarEquipo.getBtnGenerarSerie().addActionListener(e -> generarSerie());
        ventanaAgregarEquipo.getBtnFechaDefault().addActionListener(e -> fechaDefault());
        ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().addActionListener(e -> verificarIngresoAnterior());
        ventanaAgregarEquipo.getBtnaltaCliente().addActionListener(e -> abrirVentanaCliente());
        ventanaAgregarEquipo.getBotonIRaELS().addActionListener(e -> abrirVentanaELS());
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
            ventanaAgregarEquipo.getComboNombreEquipo().setSelectedIndex(-1);
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
            if (selectedItem != null && selectedItem instanceof ClienteDTO) {
                ClienteDTO cliente = (ClienteDTO) selectedItem;
                idClienteSeleccionado = cliente.getId();
                
                // Llenar sucursales
                ventanaAgregarEquipo.getComboSucursal().removeAllItems();
                agenda.ListarSucursalesxCliente(ventanaAgregarEquipo.getComboSucursal(), 
                    idClienteSeleccionado);
                
                // Seleccionar la primera sucursal (por defecto) y capturar su ID
                if (ventanaAgregarEquipo.getComboSucursal().getItemCount() > 0) {
                    ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(0);
                    Object firstSucursal = ventanaAgregarEquipo.getComboSucursal().getSelectedItem();
                    if (firstSucursal != null && firstSucursal instanceof SucursalDTO) {
                        SucursalDTO sucursal = (SucursalDTO) firstSucursal;
                        idSucursalSeleccionada = sucursal.getIdSucursal();
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar sucursales: " + ex.getMessage());
        } finally {
            procesandoCliente = false;
        }
    }
    
    /**
     * Procesa selección de sucursal
     */
    private void procesarSucursalSeleccionada() {
        try {
            Object selectedItem = ventanaAgregarEquipo.getComboSucursal().getSelectedItem();
            if (selectedItem != null && selectedItem instanceof SucursalDTO) {
                SucursalDTO sucursal = (SucursalDTO) selectedItem;
                idSucursalSeleccionada = sucursal.getIdSucursal();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al procesar sucursal: " + ex.getMessage());
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
        // Validar Cliente
        if (idClienteSeleccionado == 0) {
            JOptionPane.showMessageDialog(null, "⚠️ Campo obligatorio faltante:\n\n'CLIENTE'", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar Equipo
        if (ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem() == null ||
            ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Campo obligatorio faltante:\n\n'NOMBRE DE EQUIPO'", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar Marca
        if (ventanaAgregarEquipo.getComboMarca().getSelectedItem() == null ||
            ventanaAgregarEquipo.getComboMarca().getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Campo obligatorio faltante:\n\n'MARCA'", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar Modelo
        if (ventanaAgregarEquipo.getComboModelo().getSelectedItem() == null ||
            ventanaAgregarEquipo.getComboModelo().getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Campo obligatorio faltante:\n\n'MODELO'", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar Serie
        if (ventanaAgregarEquipo.getComboSerie().getSelectedItem() == null ||
            ventanaAgregarEquipo.getComboSerie().getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Campo obligatorio faltante:\n\n'SERIE'", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar Estado Físico
        if (ventanaAgregarEquipo.getGrupoEstadoFisico().getSelection() == null) {
            JOptionPane.showMessageDialog(null, "⚠️ Campo obligatorio faltante:\n\n'ESTADO FÍSICO'", 
                "Validación", JOptionPane.WARNING_MESSAGE);
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
        ventanaAgregarEquipo.getComboSucursal().setEnabled(false);
        ventanaAgregarEquipo.getComboNombreEquipo().setEnabled(false);
        ventanaAgregarEquipo.getComboMarca().setEnabled(false);
        ventanaAgregarEquipo.getComboModelo().setEnabled(false);
        ventanaAgregarEquipo.getComboSerie().setEnabled(false);
        ventanaAgregarEquipo.getTextFalla().setEnabled(false);
        ventanaAgregarEquipo.getTextRemitoCliente().setEnabled(false);
        ventanaAgregarEquipo.getTextAvisoCliente().setEnabled(false);
        ventanaAgregarEquipo.getFechaEntrada().setEnabled(false);
        ventanaAgregarEquipo.getTextFechafabricacion().setEnabled(false);
        ventanaAgregarEquipo.getTextClienteCliente().setEnabled(false);
        
        Enumeration<AbstractButton> botones = ventanaAgregarEquipo.getGrupoEstadoFisico().getElements();
        while (botones.hasMoreElements()) {	
			botones.nextElement().setEnabled(false);
		}
        
        
        ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(true);
        ventanaAgregarEquipo.getBotonGuardar().setEnabled(false);
        ventanaAgregarEquipo.getBotonGenerarRegistro().setEnabled(true);
        ventanaAgregarEquipo.getBtnGenerarSerie().setEnabled(false);
        ventanaAgregarEquipo.getBtnFechaDefault().setEnabled(false);
        ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().setEnabled(false);
        ventanaAgregarEquipo.getBtnaltaCliente().setEnabled(false);
        ventanaAgregarEquipo.getBotonIRaELS().setEnabled(true);
    }
    
    /**
     * Genera registro de ingreso
     */
    private void generarRegistroIngreso() {
        if (!validarDatos()) return;

        presentacion.vista.VentanaProgreso progreso = new presentacion.vista.VentanaProgreso("GENERANDO REGISTRO");
        progreso.mostrar();

        new Thread(() -> {
            try {
                long inicio = System.currentTimeMillis();
                List<RegistroEntradaReporteDTO> lista = new ArrayList<>();
                RegistroEntradaReporteDTO rep = gestorDatos.extraerRegistroIngreso(ventanaAgregarEquipo,
                    idClienteSeleccionado, idSucursalSeleccionada);

                if (rep != null) {
                    lista.add(rep);
                    ReporteRegistroEntrada reporte = new ReporteRegistroEntrada(rep, lista, agenda);

                    new Thread(() -> {
                        reporte.guardar();
                    }).start();

                    SwingUtilities.invokeLater(() -> {
                        reporte.mostrar();
                    });

                    while (!reporte.isViewerVisible()) {
                        Thread.sleep(100);
                        if (System.currentTimeMillis() - inicio > 10000) {
                            break;
                        }
                    }

                    SwingUtilities.invokeLater(() -> {
                        progreso.cerrar();
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        progreso.cerrar();
                        JOptionPane.showMessageDialog(null, "No se encontraron datos para el registro", "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    progreso.cerrar();
                    JOptionPane.showMessageDialog(null, "Error al generar registro: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
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
        
        // HABILITAR COMBOS ANTES DE LLENARLOS
        ventanaAgregarEquipo.getComboClientes().setEnabled(true);
        ventanaAgregarEquipo.getComboSucursal().setEnabled(true);
        ventanaAgregarEquipo.getComboNombreEquipo().setEnabled(true);
        ventanaAgregarEquipo.getComboMarca().setEnabled(true);
        ventanaAgregarEquipo.getComboModelo().setEnabled(true);
        ventanaAgregarEquipo.getComboSerie().setEnabled(true);
        
        // LLENAR COMBOS CON DATOS
        ventanaAgregarEquipo.getComboClientes().removeAllItems();
        ventanaAgregarEquipo.getComboSucursal().removeAllItems();
        ventanaAgregarEquipo.getComboNombreEquipo().removeAllItems();
        ventanaAgregarEquipo.getComboMarca().removeAllItems();
        ventanaAgregarEquipo.getComboModelo().removeAllItems();
        ventanaAgregarEquipo.getComboSerie().removeAllItems();
        
        // Llenar los combos con datos
        agenda.ListarCliente(ventanaAgregarEquipo.getComboClientes());
        agenda.ListarSucursales(ventanaAgregarEquipo.getComboSucursal());
        agenda.ListarEquipo(ventanaAgregarEquipo.getComboNombreEquipo());
        agenda.ListarMarca(ventanaAgregarEquipo.getComboMarca());
        
        // SELECCIONAR ÍNDICES (después de llenar)
        if (ventanaAgregarEquipo.getComboClientes().getItemCount() > 0) {
            ventanaAgregarEquipo.getComboClientes().setSelectedIndex(0);
        }
        if (ventanaAgregarEquipo.getComboSucursal().getItemCount() > 0) {
            ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(0);
        }
        
        ventanaAgregarEquipo.getComboNombreEquipo().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboMarca().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboModelo().setSelectedIndex(-1);
        ventanaAgregarEquipo.getComboSerie().setSelectedIndex(-1);
        
        // Limpiar fechas
        ventanaAgregarEquipo.setTextFechafabricacion2(null);
        
        // HABILITAR CAMPOS DE TEXTO
        ventanaAgregarEquipo.getTextFalla().setEnabled(true);
        ventanaAgregarEquipo.getTextRemitoCliente().setEnabled(true);
        ventanaAgregarEquipo.getTextAvisoCliente().setEnabled(true);
        ventanaAgregarEquipo.getTextClienteCliente().setEnabled(true);
        ventanaAgregarEquipo.getFechaEntrada().setEnabled(true);
        ventanaAgregarEquipo.getTextFechafabricacion().setEnabled(true);
        
        // HABILITAR BOTONES
        ventanaAgregarEquipo.getBotonGuardar().setEnabled(true);
        ventanaAgregarEquipo.getBotonGenerarRegistro().setEnabled(true);
        ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().setEnabled(true);
        ventanaAgregarEquipo.getBtnaltaCliente().setEnabled(true);
        ventanaAgregarEquipo.getBtnGenerarSerie().setEnabled(true);
        ventanaAgregarEquipo.getBtnFechaDefault().setEnabled(true);
        
        // DESHABILITAR BOTONES
        ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(false);
        ventanaAgregarEquipo.getBotonIRaELS().setEnabled(false);
        
        // HABILITAR RADIO BUTTONS DE ESTADO FÍSICO
        Enumeration<AbstractButton> botones = ventanaAgregarEquipo.getGrupoEstadoFisico().getElements();
        while (botones.hasMoreElements()) {	
            botones.nextElement().setEnabled(true);
        }
        
        // Resetear selección de estado físico
        ventanaAgregarEquipo.getGrupoEstadoFisico().clearSelection();
        
        // Resetear variables internas
        idClienteSeleccionado = 0;
        idSucursalSeleccionada = 0;
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
        controlador.getControladorCliente().setGestorAgregarEquipo(this);
        controlador.getControladorCliente().agregarListenersVentanaCliente();
        llenarCombos(); // Actualizar combos después de agregar cliente
    }
    
    
    /**
	 * Abre ventana ELS
	 */
	private void abrirVentanaELS() {
		// Obtener el número de ELS actual
		int elsActual = Integer.parseInt(ventanaAgregarEquipo.getTextELS());
		
		// Preguntar si desea ir al ELS generado
		int opcion = JOptionPane.showConfirmDialog(null, 
			"¿Desea ir al ELS generado?", 
			"Confirmar", 
			JOptionPane.YES_NO_OPTION, 
			JOptionPane.QUESTION_MESSAGE);
		
		// Si selecciona "No", no hacer nada
		if (opcion != JOptionPane.YES_OPTION) {
			return;
		}
		
		// Si selecciona "Sí":
		// 1. Cerrar la ventana de agregar equipo
		ventanaAgregarEquipo.dispose();
		ventanaAgregarEquipo = null;
		
		// 2. Abrir la ventana de visualizar equipo con el ELS correspondiente
		try {
			controlador.getGestorVisualizacion().abrirVentanaVisualizarEquipos(elsActual);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, 
				"Error al abrir la ventana de visualización: " + ex.getMessage(), 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
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
     * Actualiza el combo de clientes después de agregar uno nuevo
     */
    public void actualizarComboClientes() {
        if (ventanaAgregarEquipo != null) {
            try {
                // Obtener el índice seleccionado antes de actualizar
                int indexAnterior = ventanaAgregarEquipo.getComboClientes().getSelectedIndex();
                
                // Limpiar y rellenar el combo de clientes
                ventanaAgregarEquipo.getComboClientes().removeAllItems();
                agenda.ListarCliente(ventanaAgregarEquipo.getComboClientes());
                
                // Seleccionar el último cliente agregado (que estará al final)
                int itemCount = ventanaAgregarEquipo.getComboClientes().getItemCount();
                if (itemCount > 0) {
                    ventanaAgregarEquipo.getComboClientes().setSelectedIndex(itemCount - 1);
                    
                    // Capturar el ID del cliente recién agregado
                    Object selectedItem = ventanaAgregarEquipo.getComboClientes().getSelectedItem();
                    if (selectedItem instanceof ClienteDTO) {
                        ClienteDTO cliente = (ClienteDTO) selectedItem;
                        idClienteSeleccionado = cliente.getId();
                        
                        // Llenar sucursales para el nuevo cliente
                        ventanaAgregarEquipo.getComboSucursal().removeAllItems();
                        agenda.ListarSucursalesxCliente(ventanaAgregarEquipo.getComboSucursal(), 
                            idClienteSeleccionado);
                        
                        // Seleccionar la primera sucursal (por defecto)
                        if (ventanaAgregarEquipo.getComboSucursal().getItemCount() > 0) {
                            ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(0);
                            Object firstSucursal = ventanaAgregarEquipo.getComboSucursal().getSelectedItem();
                            if (firstSucursal != null && firstSucursal instanceof SucursalDTO) {
                                SucursalDTO sucursal = (SucursalDTO) firstSucursal;
                                idSucursalSeleccionada = sucursal.getIdSucursal();
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error al actualizar clientes: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Getters
     */
    public VentanaAgregarEquipo getVentanaAgregarEquipo() {
        return ventanaAgregarEquipo;
    }
}
