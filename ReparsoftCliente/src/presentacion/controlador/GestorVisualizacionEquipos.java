package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.inet.jortho.SpellChecker;

import VistaPropias.GestorArchivosExcel;
import dto.ClienteDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorUsuLogin;
import presentacion.vista.VentanaBusquedaEquipo;
import presentacion.vista.VentanaExcel;
import presentacion.vista.VentanaVisualizarEquipos;
import tiposPropios.MonedaFormatter;

/**
 * GestorVisualizacionEquipos
 * Responsable de:
 * - Cargar y mostrar datos de equipos en pantalla
 * - Navegación entre equipos (siguiente, anterior, primero, último)
 * - Llenar tabla de repuestos
 * - Llenar combos (clientes, técnicos, sucursales)
 * - Verificar presupuestos y aplicar colores
 * - Editar y guardar cambios
 * - Gestionar envío de avisos
 */
public class GestorVisualizacionEquipos {
    
    // ==== REFERENCIAS ====
    private ControladorReparacion controlador;
    private Agenda agenda;
    private ControladorUsuLogin controladorUsuLogin;
    private VentanaVisualizarEquipos ventanaVisualizarEquipos;
    private VentanaBusquedaEquipo ventanaBusquedaEquipo;
    private VentanaExcel ventanaExcel;
    
    // ==== GESTORES AUXILIARES ====
    private GestorDatos gestorDatos;
    private GestorInterfazEquipos gestorInterfaz;
    private GestorEstadosPresupuestos gestorEstados;
    
    // ==== DATOS ====
    private ReparacionDTO reparacionActual;
    private List<RepuestosDTO> repuestosEnTabla;
    private MonedaFormatter monedaFormatter;
    
    // ==== NAVEGACIÓN ====
    private int elsActual = 988;
    private int elsActualBSAS = 24900;
    private boolean guardado = true;
    
    // ==== FORMATEO ====
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    // ==== COLORES ====
    private static final java.awt.Color PAGADO = new java.awt.Color(144, 238, 144);
    private static final java.awt.Color SIN_PRESUPUESTAR = new java.awt.Color(211, 211, 211);
    private static final java.awt.Color PARCIAL = new java.awt.Color(255, 239, 153);
    private static final java.awt.Color FALTA_PAGO = new java.awt.Color(255, 182, 193);
    private static final java.awt.Color NO_ACEPTADO = new java.awt.Color(216, 191, 216);
    private static final java.awt.Color ESPERANDO = new java.awt.Color(173, 216, 230);
    private static final java.awt.Color SIN_REPARACION = new java.awt.Color(255, 218, 185);
    
    /**
     * Constructor
     */
    public GestorVisualizacionEquipos(ControladorReparacion controlador, 
                                       Agenda agenda,
                                       ControladorUsuLogin controladorUsuLogin) {
        this.controlador = controlador;
        this.agenda = agenda;
        this.controladorUsuLogin = controladorUsuLogin;
        this.monedaFormatter = new MonedaFormatter();
        this.repuestosEnTabla = new ArrayList<>();
        
        // Instanciar gestores auxiliares
        this.gestorDatos = new GestorDatos(agenda);
        this.gestorInterfaz = new GestorInterfazEquipos();
        this.gestorEstados = new GestorEstadosPresupuestos();
    }
    
    /**
     * Abre la ventana de visualización de equipos
     */
    public void abrirVentanaVisualizarEquipos() {
        int els = obtenerNumeroELS() - 1;
        
        if ((agenda.getUbicacionBase().compareTo("Bariloche") == 0 && els >= 988) ||
            (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0 && els >= 24900)) {
            
            ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
            controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
            SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());
            
            try {
                cargarDatosEquipo(ventanaVisualizarEquipos, els);
                agregarListeners(ventanaVisualizarEquipos);
                llenarComboELS(ventanaVisualizarEquipos);
                controlador.setVentanaVisualizarEquipos(ventanaVisualizarEquipos);
                cerrarVentanaAnterior();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha ingresado ningún equipo.", 
                "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Carga los datos de un equipo específico en la ventana
     */
    void cargarDatosEquipo(VentanaVisualizarEquipos ventana, int numeroELS) throws ParseException {
        reparacionActual = agenda.dameReparacionXels(numeroELS);
        
        if (reparacionActual == null) {
            JOptionPane.showMessageDialog(null, "Equipo no encontrado", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Establecer ELS
        ventana.setTextELS(Integer.toString(numeroELS));
        
        // Cargar datos técnicos
        cargarDatosTecnicos(ventana);
        
        // Cargar datos administrativos
        cargarDatosAdministrativos(ventana);
        
        // Cargar fechas
        cargarFechas(ventana);
        
        // Cargar estados
        cargarEstados(ventana);
        
        // Cargar valores monetarios
        cargarValoresMonetarios(ventana);
        
        // Llenar tabla de repuestos
        llenarTablaRepuestos(ventana);
        
        // Verificar presupuesto y aplicar estilos
        verificarPresupuesto(ventana);
        
        // Deshabilitar campos (modo lectura)
        deshabilitarCampos(ventana);
    }
    
    /**
     * Carga datos técnicos del equipo
     */
    private void cargarDatosTecnicos(VentanaVisualizarEquipos ventana) {
        ventana.setTextNombreEquipo(reparacionActual.getNombreEquipo());
        ventana.getTextNombreEquipo().setCaretPosition(0);
        ventana.setTextMarca(reparacionActual.getMarca());
        ventana.getTextMarca().setCaretPosition(0);
        ventana.setTextModelo(reparacionActual.getModelo());
        ventana.getTextModelo().setCaretPosition(0);
        ventana.setTextNSerie(reparacionActual.getNumeroDeSerie());
        ventana.getTextNSerie().setCaretPosition(0);
        ventana.setTextLugarDeIngreso(reparacionActual.getLugarDeIngreso());
        ventana.setTextFalla(reparacionActual.getFalla() == null ? "" : reparacionActual.getFalla());
        ventana.getTextFalla().setCaretPosition(0);
    }
    
    /**
     * Carga datos administrativos (cliente, sucursal, etc.)
     */
    private void cargarDatosAdministrativos(VentanaVisualizarEquipos ventana) {
        ventana.setTextAvisoCliente(reparacionActual.getAviso());
        ventana.setTextClienteCliente(reparacionActual.getClienteCliente());
        ventana.getTextClienteCliente().setCaretPosition(0);
        ventana.setTextRemitoCliente(reparacionActual.getRemitoCliente());
        ventana.setTextCliente(reparacionActual.getCliente());
        ventana.getTextCliente().setCaretPosition(0);
        ventana.setTextSucursal(reparacionActual.getSucursal());
        ventana.getTextSucursal().setCaretPosition(0);
        ventana.setTextNombreTecnico(reparacionActual.getNombreUsuario());
        ventana.setTextOC(reparacionActual.getOrdendeCompra());
        ventana.setTextDiagnostico(reparacionActual.getSolucion());
        ventana.setTextInformeCliente(reparacionActual.getInformecliente());
    }
    
    /**
     * Carga las fechas del equipo
     */
    private void cargarFechas(VentanaVisualizarEquipos ventana) throws ParseException {
        ventana.setTextFechaEntrada2(
            reparacionActual.getFecha_Entrada() == null ? null : 
            dateFormat.parse(reparacionActual.getFecha_Entrada()));
        ventana.setTextFechaSalida(
            reparacionActual.getFecha_Salida() == null ? null : 
            dateFormat.parse(reparacionActual.getFecha_Salida()));
        ventana.setTextFechaReparacion2(
            reparacionActual.getFechadereparacion() == null ? null : 
            dateFormat.parse(reparacionActual.getFechadereparacion()));
        ventana.setTextFechaRespuesta2(
            reparacionActual.getFechAceptacion() == null ? null : 
            dateFormat.parse(reparacionActual.getFechAceptacion()));
        ventana.setFechaFabr2(
            reparacionActual.getFechaFabr() == null ? null : 
            dateFormat.parse(reparacionActual.getFechaFabr()));
    }
    
    /**
     * Carga los estados del equipo
     */
    private void cargarEstados(VentanaVisualizarEquipos ventana) {
        ventana.setTextEstadoFisico(reparacionActual.getEstadoFisico());
        ventana.setTextEstadoTecnico(reparacionActual.getEstadoTecnico());
        ventana.setTextEstadoComercial(reparacionActual.getEstadoComercial());
    }
    
    /**
     * Carga valores monetarios del equipo
     */
    private void cargarValoresMonetarios(VentanaVisualizarEquipos ventana) {
        String presupuestoPeso = monedaFormatter.formatPeso(reparacionActual.getPrecioPeso().toString());
        String presupuestoDolar = monedaFormatter.formatDolar(reparacionActual.getPrecioDolar().toString());
        String pagoPeso = monedaFormatter.formatPeso(reparacionActual.getPago().toString());
        
        ventana.setTextPresupuesto(presupuestoPeso);
        ventana.setTextPresupuestoDolar(presupuestoDolar);
        ventana.setTextPago(pagoPeso);
        
        ventana.setChckPDFGenerado(reparacionActual.getPresupuestoGenerado());
        ventana.setChckPDFEnviado(reparacionActual.getPresupuestoEnviado());
        ventana.setChckWORDGenerado(reparacionActual.getWORDgenerado());
        ventana.setChckWORDEnviado(reparacionActual.getWORDenviado());
        ventana.setChckbxAvisoEnviado(reparacionActual.getAvisoEnviado());
    }
    
    /**
     * Llena la tabla de repuestos del equipo
     */
    void llenarTablaRepuestos(VentanaVisualizarEquipos ventana) {
        DefaultTableModel modelo = ventana.getModelRepuestos();
        modelo.setRowCount(0);
        
        int els = Integer.parseInt(ventana.getTextELS());
        this.repuestosEnTabla = (List<RepuestosDTO>) agenda.dameRepuestoXels(els);
        
        for (RepuestosDTO repuesto : repuestosEnTabla) {
            Object[] fila = {
                repuesto.getRef(),
                repuesto.getOriginal(),
                repuesto.getReemplazo(),
                repuesto.getNotas()
            };
            modelo.addRow(fila);
        }
    }
    
    /**
     * Procesa navegación entre equipos
     */
    public void procesarNavegacion(String tipo) {
        if (!guardado) {
            guardarCambiosSiNecesario();
        }
        
        int tam = agenda.obtenerReparacion().size();
        String ubicacion = agenda.getUbicacionBase();
        boolean actualizar = true;
        
        if (ubicacion.equals("Bariloche")) {
            actualizar = procesarNavegacionBariloche(tipo, tam);
        } else if (ubicacion.equals("Buenos Aires")) {
            actualizar = procesarNavegacionBuenosAires(tipo, tam);
        }
        
        if (actualizar) {
            try {
                cargarDatosEquipo(ventanaVisualizarEquipos, 
                        ubicacion.equals("Bariloche") ? elsActual : elsActualBSAS);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            /**
             * Procesa navegación en Bariloche
             */
            private boolean procesarNavegacionBariloche(String tipo, int tam) {
                switch (tipo) {
                    case "SIGUIENTE":
                        if (elsActual < tam + 987) {
                            elsActual++;
                            return true;
                        } else {
                            JOptionPane.showMessageDialog(null, "No hay más reparaciones", 
                                "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                            return false;
                        }
                    case "ANTERIOR":
                        if (elsActual > 988) {
                            elsActual--;
                            return true;
                        }
                        return false;
                    case "PRIMERO":
                        elsActual = 988;
                        return true;
                    case "ULTIMO":
                        elsActual = tam + 987;
                        return true;
                    default:
                        return false;
                }
            }
            
            /**
             * Procesa navegación en Buenos Aires
             */
            private boolean procesarNavegacionBuenosAires(String tipo, int tam) {
                switch (tipo) {
                    case "SIGUIENTE":
                        if (elsActualBSAS < tam + 24899) {
                            elsActualBSAS++;
                            return true;
                        } else {
                            JOptionPane.showMessageDialog(null, "No hay más reparaciones", 
                                "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                            return false;
                        }
                    case "ANTERIOR":
                        if (elsActualBSAS > 24900) {
                            elsActualBSAS--;
                            return true;
                        }
                        return false;
                    case "PRIMERO":
                        elsActualBSAS = 24900;
                        return true;
                    case "ULTIMO":
                        elsActualBSAS = tam + 24899;
                        return true;
                    default:
                        return false;
                }
            }
            
            /**
             * Verifica presupuesto y aplica estilos visuales
             */
            private void verificarPresupuesto(VentanaVisualizarEquipos ventana) {
                Double presupuesto = reparacionActual.getPrecioPeso();
                Double pago = reparacionActual.getPago();
                String estadoComercial = ventana.getTextEstadoComercial().getText();
                String estadoTecnico = ventana.getTextEstadoTecnico().getText();
                
                if ("Sin Reparación".equals(estadoTecnico)) {
                    aplicarEstadoVisual(ventana, "SIN REPARACIÓN", SIN_REPARACION);
                    return;
                }
                
                if (presupuesto.compareTo(0.0) == 0) {
                    aplicarEstadoVisual(ventana, "SIN PRESUPUESTAR", SIN_PRESUPUESTAR);
                    return;
                }
                
                if ("NO Aceptado".equals(estadoComercial)) {
                    aplicarEstadoVisual(ventana, "NO ACEPTADO", NO_ACEPTADO);
                    return;
                }
                
                int comparacion = presupuesto.compareTo(pago);
                if (comparacion == 0) {
                    aplicarEstadoVisual(ventana, "PAGADO", PAGADO);
                } else if (comparacion > 0 && pago.compareTo(0.0) > 0) {
                    aplicarEstadoVisual(ventana, "PAGADO PARCIALMENTE", PARCIAL);
                } else if (pago.compareTo(0.0) == 0) {
                    String leyenda = "A la Espera de Aceptación".equals(estadoComercial) ? 
                        "ESPERANDO ACEPTACIÓN" : "FALTA PAGO";
                    java.awt.Color color = "ESPERANDO ACEPTACIÓN".equals(leyenda) ? ESPERANDO : FALTA_PAGO;
                    aplicarEstadoVisual(ventana, leyenda, color);
                }
            }
            
            /**
             * Aplica estilo visual al panel de presupuesto
             */
            private void aplicarEstadoVisual(VentanaVisualizarEquipos ventana, String leyenda, java.awt.Color color) {
                ventana.getTextEquipoPagado().setText(leyenda);
                ventana.getTextEquipoPagado().setVisible(true);
                ventana.getTextEquipoPagado().setBackground(color);
                ventana.getPanel_MontoPresupuesto().setBackground(color);
                ventana.getTextPresupuesto().setBackground(color);
                ventana.getTextPresupuestoDolar().setBackground(color);
                ventana.getTextPago().setBackground(color);
            }
            
            /**
             * Habilita campos para edición
             */
            public void editar(VentanaVisualizarEquipos ventana) {
                llenarComboClientes(ventana);
                llenarComboTecnicos(ventana);
                gestorInterfaz.habilitarCampos(ventana);
                guardado = false;
            }
            
            /**
             * Guarda cambios realizados en el equipo
             */
            public void guardarCambios(VentanaVisualizarEquipos ventana) {
                ReparacionDTO reparacionAeditar = gestorDatos.extraerDatos(ventana, reparacionActual);
                
                if (reparacionAeditar != null) {
                    agenda.editarReparacionR(reparacionAeditar);
                    guardado = true;
                    gestorInterfaz.deshabilitarCampos(ventana);
                }
            }
            
            /**
             * Guarda cambios si es necesario antes de navegar
             */
            private void guardarCambiosSiNecesario() {
                if (!guardado) {
                    ReparacionDTO reparacionAeditar = gestorDatos.extraerDatos(ventanaVisualizarEquipos, reparacionActual);
                    if (reparacionAeditar != null) {
                        agenda.editarReparacionR(reparacionAeditar);
                        guardado = true;
                    }
                }
            }
            
            /**
             * Agrega listeners a la ventana
             */
            private void agregarListeners(VentanaVisualizarEquipos ventana) {
                // Navegación
                ventana.getBotonSiguiente().addActionListener(e -> procesarNavegacion("SIGUIENTE"));
                ventana.getBotonAnterior().addActionListener(e -> procesarNavegacion("ANTERIOR"));
                ventana.getBotonPrimero().addActionListener(e -> procesarNavegacion("PRIMERO"));
                ventana.getBotonUltimo().addActionListener(e -> procesarNavegacion("ULTIMO"));
                
                // Edición
                ventana.getBtnEditar().addActionListener(e -> editar(ventana));
                ventana.getBtnGuardarCambios().addActionListener(e -> guardarCambios(ventana));
                
                // Presupuesto
                ventana.getBotonPresupuestar().addActionListener(e -> abrirPresupuesto(ventana));
                
                // Búsqueda
                ventana.getBtnBuscarELS().addActionListener(e -> buscarPorELS(ventana));
                ventana.getBtnBuscar().addActionListener(e -> abrirBusqueda(ventana));
                ventana.getComboELS().addActionListener(e -> buscarPorELS(ventana));
                
                
              //  ventana.getBtnabrirExcel().addActionListener(e -> GestorArchivosExcel.abrirArchivoExcel(ventanaExcel));
                
                // Repuestos
                ventana.getBtnRepuestos().addActionListener(e -> abrirRepuestos(ventana));
                ventana.getBtnEditarRepuesto().addActionListener(e -> editarRepuesto(ventana));
                ventana.getBtnEliminarRepuesto().addActionListener(e -> eliminarRepuesto(ventana));
                ventana.getTablaRepuestos().addMouseListener(new MouseListener() {
                    @Override public void mouseClicked(MouseEvent e) { seleccionarRepuesto(ventana, e); }
                    @Override public void mousePressed(MouseEvent e) {}
                    @Override public void mouseReleased(MouseEvent e) {}
                    @Override public void mouseEntered(MouseEvent e) {}
                    @Override public void mouseExited(MouseEvent e) {}
                });
                
                AutoCompleteDecorator.decorate(ventana.getComboELS());
                gestorInterfaz.agregarListenersPrecios(ventana);
                gestorInterfaz.agregarFocusListeners(ventana);
            }
            
            /**
             * Busca equipo por ELS
             */
            private void buscarPorELS(VentanaVisualizarEquipos ventana) {
                Object selectedItem = ventana.getComboELS().getSelectedItem();
                if (selectedItem != null && !selectedItem.toString().isEmpty()) {
                    try {
                        int els = Integer.parseInt(selectedItem.toString());
                        cargarDatosEquipo(ventana, els);
                        agregarListeners(ventana);
                        if (agenda.getUbicacionBase().equals("Bariloche")) {
                            elsActual = els;
                        } else {
                            elsActualBSAS = els;
                        }
                    } catch (NumberFormatException | ParseException ex) {
                        JOptionPane.showMessageDialog(null, "ELS inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
            /**
             * Abre ventana de búsqueda
             */
            private void abrirBusqueda(VentanaVisualizarEquipos ventana) {
                ventanaBusquedaEquipo = new VentanaBusquedaEquipo(controlador);
                ventanaBusquedaEquipo.btnBuscar.addActionListener(f -> gestorDatos.realizarBusqueda(ventanaBusquedaEquipo));
            }
            
            /**
             * Abre gestor de presupuestos
             */
            private void abrirPresupuesto(VentanaVisualizarEquipos ventana) {
                if (ventana.getBtnGuardarCambios().isEnabled()) {
                    JOptionPane.showMessageDialog(null, 
                        "Debe guardar los cambios realizados para poder presupuestar.", 
                        "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int els = Integer.parseInt(ventana.getTextELS());
                    controlador.getControladorPresupuestos().TomarDatosDeTablasParaVisualizacion(els);
                }
            }
            
            /**
             * Abre gestor de repuestos
             */
            private void abrirRepuestos(VentanaVisualizarEquipos ventana) {
                // Delegar a GestorRepuestos
                controlador.getGestorRepuestos().abrirVentanaRepuestos(ventana);
            }
            
            /**
             * Selecciona repuesto de la tabla
             */
            private void seleccionarRepuesto(VentanaVisualizarEquipos ventana, MouseEvent e) {
                int fila = ventana.getTablaRepuestos().getSelectedRow();
                if (fila >= 0 && fila < repuestosEnTabla.size()) {
                    // Repuesto seleccionado
                }
            }
            
            /**
             * Edita repuesto seleccionado
             */
            private void editarRepuesto(VentanaVisualizarEquipos ventana) {
                int fila = ventana.getTablaRepuestos().getSelectedRow();
                if (fila < 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un repuesto", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Obtener datos de la tabla
                RepuestosDTO repuesto = repuestosEnTabla.get(fila);
                // Actualizar repuesto en BD
                agenda.editarRepuesto(repuesto);
                llenarTablaRepuestos(ventana);
            }
            
            /**
             * Elimina repuesto seleccionado
             */
            private void eliminarRepuesto(VentanaVisualizarEquipos ventana) {
                int fila = ventana.getTablaRepuestos().getSelectedRow();
                if (fila < 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un repuesto", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de eliminar este repuesto?", "Confirmación", JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    RepuestosDTO repuesto = repuestosEnTabla.get(fila);
                    agenda.borraRepuesto(repuesto);
                    llenarTablaRepuestos(ventana);
                }
            }
            

            
            /**
             * Llena combo de clientes
             */
            private void llenarComboClientes(VentanaVisualizarEquipos ventana) {
                agenda.ListarCliente(ventana.getComboClientes());
            }
            
            /**
             * Llena combo de técnicos
             */
            private void llenarComboTecnicos(VentanaVisualizarEquipos ventana) {
                agenda.ListarTecnicosV(ventana.getComboTecnico());
            }
            
            /**
             * Llena combo de ELS
             */
            private void llenarComboELS(VentanaVisualizarEquipos ventana) {
                agenda.ListarELS(ventana.getComboELS());
                ventana.getComboELS().setSelectedIndex(-1);
            }
            
            /**
             * Deshabilita campos (modo lectura)
             */
            private void deshabilitarCampos(VentanaVisualizarEquipos ventana) {
                gestorInterfaz.deshabilitarCampos(ventana);
            }
            
            /**
             * Cierra ventana anterior
             */
            private void cerrarVentanaAnterior() {
                if (ventanaVisualizarEquipos != null) {
                    ventanaVisualizarEquipos.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            if (!guardado) {
                                int opcion = JOptionPane.showConfirmDialog(null,
                                    "Hay cambios sin guardar. ¿Desea guardar antes de salir?",
                                    "Aviso", JOptionPane.YES_NO_CANCEL_OPTION);
                                
                                if (opcion == JOptionPane.YES_OPTION) {
                                    guardarCambios(ventanaVisualizarEquipos);
                                } else if (opcion == JOptionPane.CANCEL_OPTION) {
                                    return;
                                }
                                
                            }
                            ventanaVisualizarEquipos.dispose();
                            ventanaVisualizarEquipos = null;
                        }
                    });
                }
            }
            
            /**
             * Procesa eventos delegados de ActionListener
             */
            public void procesarEventos(ActionEvent e) {
                if (ventanaVisualizarEquipos == null) return;
                
                if (e.getSource() == ventanaVisualizarEquipos.getBotonSiguiente()) {
                    procesarNavegacion("SIGUIENTE");
                } else if (e.getSource() == ventanaVisualizarEquipos.getBotonAnterior()) {
                    procesarNavegacion("ANTERIOR");
                } else if (e.getSource() == ventanaVisualizarEquipos.getBotonPrimero()) {
                    procesarNavegacion("PRIMERO");
                } else if (e.getSource() == ventanaVisualizarEquipos.getBotonUltimo()) {
                    procesarNavegacion("ULTIMO");
                }
            }
            
            /**
             * Getters
             */
            public VentanaVisualizarEquipos getVentanaVisualizarEquipos() {
                return ventanaVisualizarEquipos;
            }
            
            public ReparacionDTO getReparacionActual() {
                return reparacionActual;
            }
            
            public List<RepuestosDTO> getRepuestosEnTabla() {
                return repuestosEnTabla;
            }
            
            public int obtenerNumeroELS() {
                String ubicacion = agenda.getUbicacionBase();
                if (ubicacion.equals("Buenos Aires")) {
                    return agenda.dameNumeroELSbsas() + 1;
                } else {
                    return agenda.dameNumeroELS() + 1;
                }
            }
            
            public boolean isGuardado() {
                return guardado;
            }
            
            public void setGuardado(boolean guardado) {
                this.guardado = guardado;
            }
        }

                                
                                       
                            
                            

