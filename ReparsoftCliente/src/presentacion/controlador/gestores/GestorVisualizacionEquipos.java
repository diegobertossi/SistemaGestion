package presentacion.controlador.gestores;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.inet.jortho.SpellChecker;

import dto.ClienteDTO;
import dto.RegistroEntradaReporteDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import modelo.Agenda;
import persistencia.conexion.Conexion;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorUsuLogin;
import presentacion.reportes.ReporteRegistroEntrada;
import presentacion.vista.VentanaBusquedaEquipo;
import presentacion.vista.VentanaEnviarCorreoOwsp;
import presentacion.vista.VentanaEstados;
import presentacion.vista.VentanaExcel;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaWSP;
import tiposPropios.MonedaFormatter;

/**
 * GestorVisualizacionEquipos Responsable de:
 * - Cargar y mostrar datos de equipos en pantalla
 * - Navegación entre equipos (siguiente, anterior, primero, último)
 * - Llenar tabla de repuestos
 * - Llenar combos (clientes, técnicos, sucursales)
 * - Verificar presupuestos y aplicar colores
 * - Editar y guardar cambios
 * - Gestionar envío de avisos
 */
public class GestorVisualizacionEquipos {

	// ====================== CONFIGURACIÓN DE ELS INICIALES ======================
	// Estos valores se pueden modificar fácilmente aquí según la base de datos

	private static final int ELS_INICIAL_NORMAL_BARILOCHE = 988;
	private static final int ELS_INICIAL_NORMAL_BUENOS_AIRES = 24333;
	private static final int ELS_INICIAL_ANTIGUA_BARILOCHE = 1;
	private static final int ELS_INICIAL_ANTIGUA_BUENOS_AIRES = 16550;

	// ==== REFERENCIAS ====
	private ControladorReparacion controlador;
	private Agenda agenda;
	private ControladorUsuLogin controladorUsuLogin;
	private VentanaVisualizarEquipos ventanaVisualizarEquipos;
	private VentanaBusquedaEquipo ventanaBusquedaEquipo;
	private VentanaExcel ventanaExcel;
	private VentanaEnviarCorreoOwsp ventanaEnviarCorreoOwsp;
	private VentanaWSP ventanaWSP;
	private VentanaEstados ventanaEstados;

	// ==== GESTORES AUXILIARES ====
	private GestorDatos gestorDatos;
	private GestorBusqueda gestorBusqueda;
	private GestorInterfazEquipos gestorInterfaz;
	private GestorEstadosPresupuestos gestorEstados;
	private GestorArchivosExcel gestorExcel;

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

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private java.util.Date fechaParseadaHOY = null;

	/**
	 * Constructor
	 */
	public GestorVisualizacionEquipos(ControladorReparacion controlador, Agenda agenda,
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
		this.gestorBusqueda = new GestorBusqueda(controlador, agenda);

		try {
			fechaParseadaHOY = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(LocalDateTime.now()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Devuelve el ELS inicial según la ubicación y si es base antigua o normal
	 */
	private int obtenerELSInicial(String ubicacion) {
		boolean esAntigua = Conexion.isModoAntigua();

		if (ubicacion.equalsIgnoreCase("Bariloche")) {
			return esAntigua ? ELS_INICIAL_ANTIGUA_BARILOCHE : ELS_INICIAL_NORMAL_BARILOCHE;
		} else if (ubicacion.equalsIgnoreCase("Buenos Aires")) {
			return esAntigua ? ELS_INICIAL_ANTIGUA_BUENOS_AIRES : ELS_INICIAL_NORMAL_BUENOS_AIRES;
		}
		return 1; // fallback
	}

	/**
	 * Abre la ventana de visualización de equipos
	 */
	public void abrirVentanaVisualizarEquipos() {
		int elsInicial = obtenerELSInicial(agenda.getUbicacionBase());
		String ubicacion = agenda.getUbicacionBase();

		// Usamos el último ELS disponible menos 1 para empezar desde el más reciente
		int ultimoELS = obtenerNumeroELS() - 1;

		// Si no hay registros, usar el inicial
		if (ultimoELS < elsInicial) {
			ultimoELS = elsInicial;
		}

		if ((ubicacion.equalsIgnoreCase("Bariloche") && ultimoELS >= elsInicial)
				|| (ubicacion.equalsIgnoreCase("Buenos Aires") && ultimoELS >= elsInicial)) {

			ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
			controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
			SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

			try {
				// Inicializar las variables de navegación con el ÚLTIMO ELS
				if (ubicacion.equalsIgnoreCase("Bariloche")) {
					elsActual = ultimoELS;
				} else if (ubicacion.equalsIgnoreCase("Buenos Aires")) {
					elsActualBSAS = ultimoELS;
				}

				cargarDatosEquipo(ventanaVisualizarEquipos, ultimoELS);
				agregarListeners(ventanaVisualizarEquipos);
				llenarComboELS(ventanaVisualizarEquipos);
				controlador.setVentanaVisualizarEquipos(ventanaVisualizarEquipos);

				cerrarVentanaAnterior();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "No se ha ingresado ningún equipo.", "Mensaje Informativo",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Abre la ventana de visualización de equipos con un ELS específico
	 */
	public void abrirVentanaVisualizarEquipos(int elsEspecifico) {
		ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
		controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
		SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

		try {
			// Inicializar las variables de navegación con el ELS específico
			String ubicacion = agenda.getUbicacionBase();
			if (ubicacion.equalsIgnoreCase("Bariloche")) {
				elsActual = elsEspecifico;
			} else if (ubicacion.equalsIgnoreCase("Buenos Aires")) {
				elsActualBSAS = elsEspecifico;
			}

			cargarDatosEquipo(ventanaVisualizarEquipos, elsEspecifico);
			agregarListeners(ventanaVisualizarEquipos);
			llenarComboELS(ventanaVisualizarEquipos);
			controlador.setVentanaVisualizarEquipos(ventanaVisualizarEquipos);
			cerrarVentanaAnterior();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga los datos de un equipo específico en la ventana
	 */
	void cargarDatosEquipo(VentanaVisualizarEquipos ventana, int numeroELS) throws ParseException {
		reparacionActual = agenda.dameReparacionXels(numeroELS);

		if (reparacionActual == null) {
			JOptionPane.showMessageDialog(null, "Equipo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
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
		gestorInterfaz.verificarPresupuesto(ventana);

		// Deshabilitar campos (modo lectura)
		deshabilitarCampos(ventana);
		
		if (controladorUsuLogin.getUsu_login().getIdRol() != 1) {
		 if (Conexion.isModoAntigua()) {
		        deshabilitarBotonesModoBloqueado(ventana);
		        }
		}
		gestorInterfaz.resetearUndoRedo(ventana);
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

	
	
	private void deshabilitarBotonesModoBloqueado(VentanaVisualizarEquipos ventana) {
	    ventana.getBtnEditar().setEnabled(false);
	    ventana.getBtnGuardarCambios().setEnabled(false);
	    ventana.getBotonAvisoEquipoListo().setEnabled(false);
	    ventana.getBotonRespuestaAlTecnico().setEnabled(false);
	    ventana.getBotonAvisoInforme().setEnabled(false);
	    ventana.getBotonPresupuestar().setEnabled(false);
	    ventana.getBtnfacturar().setEnabled(false);
	    ventana.getBtnabrirExcel().setEnabled(false);
	    ventana.getBtnGenerarRemito().setEnabled(false);
	    ventana.getBtnenviarCorreoOwsp().setEnabled(false);
	    ventana.getBtnCopiarFactura().setEnabled(false);
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
		ventana.setTextNumeroFactura(reparacionActual.getNrofactura());

		int codigoRemitoBase = reparacionActual.getCodigo();
		String codigoRemitoVisual = obtenerCodigoRemitoVisual(codigoRemitoBase);
		ventana.setTextUbicacionRemito(codigoRemitoVisual);

		int numeroRemitoBase = reparacionActual.getNumeroRemitoSalida();
		String numeroRemitoVisual = numeroRemitoBase > 0 ? String.format("%08d", numeroRemitoBase) : "";
		ventana.setTextNumeroRemito(numeroRemitoVisual);

	}

	/**
	 * Carga las fechas del equipo
	 */
	private void cargarFechas(VentanaVisualizarEquipos ventana) throws ParseException {
		ventana.setTextFechaEntrada2(reparacionActual.getFecha_Entrada() == null ? null
				: dateFormat.parse(reparacionActual.getFecha_Entrada()));
		ventana.setTextFechaSalida(reparacionActual.getFecha_Salida() == null ? null
				: dateFormat.parse(reparacionActual.getFecha_Salida()));
		ventana.setTextFechaReparacion2(reparacionActual.getFechadereparacion() == null ? null
				: dateFormat.parse(reparacionActual.getFechadereparacion()));
		ventana.setTextFechaRespuesta2(reparacionActual.getFechAceptacion() == null ? null
				: dateFormat.parse(reparacionActual.getFechAceptacion()));
		ventana.setFechaFabr2(
				reparacionActual.getFechaFabr() == null ? null : dateFormat.parse(reparacionActual.getFechaFabr()));
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
	public void llenarTablaRepuestos(VentanaVisualizarEquipos ventana) {
		DefaultTableModel modelo = ventana.getModelRepuestos();
		modelo.setRowCount(0);

		int els = Integer.parseInt(ventana.getTextELS());
		this.repuestosEnTabla = (List<RepuestosDTO>) agenda.dameRepuestoXels(els);

		for (RepuestosDTO repuesto : repuestosEnTabla) {
			Object[] fila = { repuesto.getRef(), repuesto.getOriginal(), repuesto.getReemplazo(), repuesto.getNotas() };
			modelo.addRow(fila);
		}
	}

	private String obtenerCodigoRemitoVisual(int codigoRemitoBase) {
		if (codigoRemitoBase == 2 || codigoRemitoBase == 5 || codigoRemitoBase == 6 || codigoRemitoBase == 7) {
			return String.format("%04d", codigoRemitoBase);
		} else if (codigoRemitoBase == 1000 || codigoRemitoBase == 2000 || codigoRemitoBase == 3000) {
			return String.valueOf(codigoRemitoBase);
		} else {
			return "";
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

		if (ubicacion.equalsIgnoreCase("Bariloche")) {
			actualizar = procesarNavegacionBariloche(tipo, tam);
		} else if (ubicacion.equalsIgnoreCase("Buenos Aires")) {
			actualizar = procesarNavegacionBuenosAires(tipo, tam);
		}

		if (actualizar) {
			try {
				cargarDatosEquipo(ventanaVisualizarEquipos, ubicacion.equalsIgnoreCase("Bariloche") ? elsActual : elsActualBSAS);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Procesa navegación en Bariloche
	 */
	private boolean procesarNavegacionBariloche(String tipo, int tam) {
		int elsInicial = obtenerELSInicial("Bariloche");

		switch (tipo) {
		case "SIGUIENTE":
			if (elsActual < tam + (elsInicial - 1)) {
				elsActual++;
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "No hay más reparaciones", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		case "ANTERIOR":
			if (elsActual > elsInicial) {
				elsActual--;
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Esta es la primera reparación", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		case "PRIMERO":
			elsActual = elsInicial;
			return true;
		case "ULTIMO":
			elsActual = tam + (elsInicial - 1);
			return true;
		default:
			return false;
		}
	}

	/**
	 * Procesa navegación en Buenos Aires
	 */
	private boolean procesarNavegacionBuenosAires(String tipo, int tam) {
		int elsInicial = obtenerELSInicial("Buenos Aires");

		switch (tipo) {
		case "SIGUIENTE":
			if (elsActualBSAS < tam + (elsInicial - 1)) {
				elsActualBSAS++;
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "No hay más reparaciones", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		case "ANTERIOR":
			if (elsActualBSAS > elsInicial) {
				elsActualBSAS--;
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Esta es la primera reparación", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		case "PRIMERO":
			elsActualBSAS = elsInicial;
			return true;
		case "ULTIMO":
			elsActualBSAS = tam + (elsInicial - 1);
			return true;
		default:
			return false;
		}
	}

	/**
	 * Habilita campos para edición
	 */
	public void editar(VentanaVisualizarEquipos ventana) {
		llenarComboClientes(ventana);
		llenarComboTecnicos(ventana);
		llenarComboEstadoFisico(ventana);
		llenarComboEstadoTecnico(ventana);
		llenarComboEstadoComercial(ventana);
		llenarComboIngreso(ventana);

		gestorInterfaz.habilitarCampos(ventana);
		guardado = false;
	}

	/**
	 * Guarda cambios realizados en el equipo
	 */
	public void guardarCambios(VentanaVisualizarEquipos ventana) {
		ReparacionDTO reparacionAeditar = gestorDatos.extraerDatos(ventana, reparacionActual);

		if (reparacionAeditar == null) {
			return;
		} else {
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
			if (reparacionAeditar == null) {
				return;
			} else {
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

		// Búsqueda
		ventana.getBtnBuscarELS().addActionListener(e -> buscarPorELS(ventana));
		ventana.getBtnBuscar().addActionListener(e -> abrirBusqueda(ventana));

		ventana.getComboELS().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarPorELS(ventana);
				}
			}
		});

		// Abrir Excel
		ventana.getBtnabrirExcel().addActionListener(e -> abrirExcelDeEquipo());

		// Copiar el monto del presupuesto al pago
		ventana.getBtnCopiarPresupuesto().addActionListener(e -> {
			copiarPago(ventana);
		});

		// abrir ventana copiar factura
		ventana.getBtnCopiarFactura().addActionListener(e -> {
			controlador.getGestorPresupuesto().abrirVentanaCopiarFactura(ventana);
		});

		// Presupuesto
		ventana.getBotonPresupuestar()
				.addActionListener(e -> controlador.getGestorPresupuesto().abrirPresupuesto(ventana));

		// Facturación
		ventana.getBtnfacturar().addActionListener(e -> controlador.getGestorPresupuesto().abrirFacturacion(ventana));

		// Registro de ingreso
		ventanaVisualizarEquipos.getBotonRegistroIngreso()
				.addActionListener(e -> generarRegistroIngreso(ventanaVisualizarEquipos));

		// Correo WSP
		ventanaVisualizarEquipos.getBtnenviarCorreoOwsp()
				.addActionListener(e -> abrirEnviarCorreoWSP(ventanaVisualizarEquipos));

		// Estados
		ventana.getBotonEditarEstados().addActionListener(e -> {
			abrirVentanaEstados(ventanaVisualizarEquipos);
		});

		// Remito
		ventana.getBtnGenerarRemito().addActionListener(e -> controlador.getGestorPresupuesto().generarRemito(ventana));

		// Avisos
		ventana.getBotonAvisoInforme()
				.addActionListener(e -> controlador.getGestorPresupuesto().enviarAvisoInforme(ventana));

		ventana.getBotonAvisoEquipoListo()
				.addActionListener(e -> controlador.getGestorPresupuesto().enviarAvisoEquipoListo(ventana));

		ventana.getBotonRespuestaAlTecnico()
				.addActionListener(e -> controlador.getGestorPresupuesto().enviarRespuestaCliente(ventana));

		// Repuestos
		ventana.getBtnRepuestos()
				.addActionListener(e -> controlador.getGestorRepuestos().abrirVentanaRepuestos(ventana));
		ventana.getBtnEliminarRepuesto()
				.addActionListener(e -> controlador.getGestorRepuestos().eliminarRepuesto(ventana));

		// Listener para edición automática de tabla de repuestos
		controlador.getGestorRepuestos().agregarListenerEdicionTabla(ventanaVisualizarEquipos);

		AutoCompleteDecorator.decorate(ventana.getComboELS());
		gestorInterfaz.agregarListenersPrecios(ventana);
		gestorInterfaz.agregarFocusListeners(ventana);
		gestorInterfaz.configurarUndoRedo(ventanaVisualizarEquipos);
		gestorInterfaz.habilitarMenuContextual(ventanaVisualizarEquipos.getTextInformeCliente());
		gestorInterfaz.habilitarMenuContextual(ventanaVisualizarEquipos.getTextDiagnostico());
		gestorInterfaz.habilitarMenuContextual(ventanaVisualizarEquipos.getTextFalla());
		gestorInterfaz.habilitarMenuContextual(ventanaVisualizarEquipos.getTextMarca());
		gestorInterfaz.habilitarMenuContextual(ventanaVisualizarEquipos.getTextModelo());
		gestorInterfaz.habilitarMenuContextual(ventanaVisualizarEquipos.getTextNSerie());
		gestorInterfaz.habilitarMenuContextual(ventanaVisualizarEquipos.getTextNombreEquipo());

	}

	public void copiarPago(VentanaVisualizarEquipos ventana) {

		String presupuesto = ventana.getTextPresupuesto().getText();

		if (ventana.getBtnCopiarPresupuesto().getText().equals("COPIAR PAGO")) {

			ventana.setTextPago(presupuesto);

			ventana.getBtnCopiarPresupuesto().setFont(new java.awt.Font("Cambria", java.awt.Font.BOLD, 10));
			ventana.getBtnCopiarPresupuesto().setText("LIMPIAR PAGO");

		} else {

			int respuesta = JOptionPane.showConfirmDialog(null, "Se va a eliminar el monto del pago. ¿Desea continuar?",
					"Confirmar", JOptionPane.YES_NO_OPTION);

			if (respuesta == JOptionPane.YES_OPTION) {
				ventana.setTextPago(monedaFormatter.formatPeso("0"));

				ventana.getBtnCopiarPresupuesto().setFont(new java.awt.Font("Cambria", java.awt.Font.BOLD, 10));
				ventana.getBtnCopiarPresupuesto().setText("COPIAR PAGO");
			} else {
				return;
			}
		}

		gestorInterfaz.verificarPresupuesto(ventana);
	}

	void abrirVentanaEstados(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(false);
		ventanaEstados = editarEstados(ventanaVisualizarEquipos);

		ventanaEstados.getBtnAceptarEdicion().addActionListener(e -> {
			aceptarEdicionEstados(ventanaVisualizarEquipos);
		});

		ventanaEstados.getBtnHabilitarLugarIngreso().addActionListener(e -> {
			habilitarLugarIngreso();
		});
	}

	private VentanaEstados editarEstados(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		ventanaEstados = new VentanaEstados(controlador);

		Enumeration<?> elementsF = ventanaEstados.getGrupoEstadoFisico().getElements();
		while (elementsF.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsF.nextElement();
			if (button.getText().compareToIgnoreCase(ventanaVisualizarEquipos.getTextEstadoFisico().getText()) == 0) {
				button.setSelected(true);
			}
		}

		Enumeration<?> elementsT = ventanaEstados.getGrupoEstadoTecnico().getElements();
		while (elementsT.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsT.nextElement();
			if (button.getText().compareToIgnoreCase(ventanaVisualizarEquipos.getTextEstadoTecnico().getText()) == 0) {
				button.setSelected(true);
			}
		}

		Enumeration<?> elementsC = ventanaEstados.getGrupoEstadoComercial().getElements();
		while (elementsC.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsC.nextElement();
			if (button.getText().compareToIgnoreCase(ventanaVisualizarEquipos.getTextEstadoComercial().getText()) == 0) {
				button.setSelected(true);
			}
		}

		Enumeration<?> elementsIngreso = ventanaEstados.getGrupoLugarDeIngreso().getElements();
		while (elementsIngreso.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsIngreso.nextElement();
			if (button.getText().compareToIgnoreCase(ventanaVisualizarEquipos.getTextLugarDeIngreso().getText()) == 0) {
				button.setSelected(true);
			}
		}

		return ventanaEstados;
	}

	private void aceptarEdicionEstados(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		String estadoFisico = "";
		String estadoTecnico = "";
		String estadoComercial = "";
		String lugarDeIngreso = "";

		Enumeration<?> elementsF = ventanaEstados.getGrupoEstadoFisico().getElements();
		while (elementsF.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsF.nextElement();
			if (button.isSelected()) {
				estadoFisico = button.getText();
			}
		}

		Enumeration<?> elementsT = ventanaEstados.getGrupoEstadoTecnico().getElements();
		while (elementsT.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsT.nextElement();
			if (button.isSelected()) {
				estadoTecnico = button.getText();
			}
		}

		Enumeration<?> elementsC = ventanaEstados.getGrupoEstadoComercial().getElements();
		while (elementsC.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsC.nextElement();
			if (button.isSelected()) {
				estadoComercial = button.getText();
			}
		}

		Enumeration<?> elementsIngreso = ventanaEstados.getGrupoLugarDeIngreso().getElements();
		while (elementsIngreso.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elementsIngreso.nextElement();
			if (button.isSelected()) {
				lugarDeIngreso = button.getText();
			}
		}

		if (ventanaVisualizarEquipos.getTextEstadoFisico().getText().compareTo(estadoFisico) != 0) {
			ventanaVisualizarEquipos.setTextEstadoFisico(estadoFisico);

			if (estadoFisico.equals("Enviado")) {
				ventanaVisualizarEquipos.getFechaSalida().setDate(fechaParseadaHOY);
			}
		}

		if (ventanaVisualizarEquipos.getTextEstadoTecnico().getText().compareTo(estadoTecnico) != 0) {
			ventanaVisualizarEquipos.setTextEstadoTecnico(estadoTecnico);

			if (estadoTecnico.equals("Sin Revisar")) {
				ventanaVisualizarEquipos.getFechaReparacion().setDate(null);
			} else {
				ventanaVisualizarEquipos.getFechaReparacion().setDate(fechaParseadaHOY);
			}
		}

		if (ventanaVisualizarEquipos.getTextEstadoComercial().getText().compareTo(estadoComercial) != 0) {
			ventanaVisualizarEquipos.setTextEstadoComercial(estadoComercial);

			if (estadoComercial.equals("A la Espera de Aceptación")) {
				ventanaVisualizarEquipos.getFechaRespuesta().setDate(null);
			} else {
				ventanaVisualizarEquipos.getFechaRespuesta().setDate(fechaParseadaHOY);
			}
		}

		if (ventanaVisualizarEquipos.getTextLugarDeIngreso().getText().compareTo(lugarDeIngreso) != 0) {
			ventanaVisualizarEquipos.setTextLugarDeIngreso(lugarDeIngreso);
		}

		gestorInterfaz.verificarPresupuesto(ventanaVisualizarEquipos);

		this.ventanaEstados.dispose();
		this.ventanaEstados = null;
		ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(true);
	}

	private void habilitarLugarIngreso() {
		ventanaEstados.getRdbtnIngresoMDP().setEnabled(true);
		ventanaEstados.getRdbtnIngresoBRC().setEnabled(true);
		ventanaEstados.getRdbtnIngresoCABA().setEnabled(true);
	}

	private void abrirEnviarCorreoWSP(VentanaVisualizarEquipos ventanaVisualizarEquipos2) {
	    ventanaEnviarCorreoOwsp = new VentanaEnviarCorreoOwsp(controlador);

	    ventanaEnviarCorreoOwsp.getBtnEnviarWSP().addActionListener(e -> {
	        controlador.getGestorClientesWSP().abrirVentanaWSP();
	        ventanaEnviarCorreoOwsp.dispose();
	    });

	    ventanaEnviarCorreoOwsp.getBtnEnviarCorreo().addActionListener(e -> {
	        // Obtener el ELS actual según la ubicación
	        String ubicacion = agenda.getUbicacionBase();
	        int elsParaEnviar = ubicacion.equalsIgnoreCase("Buenos Aires") ? elsActualBSAS : elsActual;

	        // Delegar al ControladorPresupuestos — él verifica si el PDF fue generado
	        controlador.getGestorPresupuesto().abrirEnvioCorreoPresupuestoExistente(elsParaEnviar);

	        ventanaEnviarCorreoOwsp.dispose();
	    });
	}
	

	private void generarRegistroIngreso(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		try {
			List<RegistroEntradaReporteDTO> lista = new ArrayList<>();
			RegistroEntradaReporteDTO rep = gestorDatos.extraerRegistroIngreso(ventanaVisualizarEquipos, 1, 1);

			if (rep != null) {
				lista.add(rep);
				ReporteRegistroEntrada reporte = new ReporteRegistroEntrada(rep, lista, agenda);
				reporte.mostrar();
				reporte.guardar();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al generar registro: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void buscarPorELS(VentanaVisualizarEquipos ventana) {
		Object selectedItem = ventana.getComboELS().getSelectedItem();
		if (selectedItem != null && !selectedItem.toString().isEmpty()) {
			try {
				int els = Integer.parseInt(selectedItem.toString());
				cargarDatosEquipo(ventana, els);
				if (agenda.getUbicacionBase().equalsIgnoreCase("Bariloche")) {
					elsActual = els;
				} else {
					elsActualBSAS = els;
				}
			} catch (NumberFormatException | ParseException ex) {
				JOptionPane.showMessageDialog(null, "ELS inválido", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void abrirBusqueda(VentanaVisualizarEquipos ventana) {
		gestorBusqueda.abrirVentanaBusqueda();
	}

	private void llenarComboClientes(VentanaVisualizarEquipos ventana) {
		// ... (código original sin cambios - se mantiene completo)
		JComboBox<ClienteDTO> comboClientes = ventana.getComboClientes();

		ItemListener[] listeners = comboClientes.getItemListeners();
		for (ItemListener listener : listeners) {
			comboClientes.removeItemListener(listener);
		}

		String textoClienteActual = ventana.getTextCliente().getText().trim();

		agenda.ListarCliente(comboClientes);

		boolean seleccionEncontrada = false;
		ClienteDTO clienteSeleccionado = null;

		if (!textoClienteActual.isEmpty()) {
			DefaultComboBoxModel<ClienteDTO> model = (DefaultComboBoxModel<ClienteDTO>) comboClientes.getModel();

			for (int i = 0; i < model.getSize(); i++) {
				ClienteDTO cliente = model.getElementAt(i);
				if (cliente != null && cliente.getRazon_Social() != null) {
					if (cliente.getRazon_Social().equalsIgnoreCase(textoClienteActual)) {
						comboClientes.setSelectedIndex(i);
						clienteSeleccionado = cliente;
						seleccionEncontrada = true;
						break;
					}
				}
			}
		}

		if (!seleccionEncontrada) {
			comboClientes.setSelectedIndex(-1);
		}

		comboClientes.addItemListener(new ItemListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && ventana.getComboClientes().getSelectedItem() != null) {

					ClienteDTO cliente = (ClienteDTO) ventana.getComboClientes().getSelectedItem();
					int id = cliente.getId();

					agenda.ListarSucursalesxCliente(ventana.getComboSucursal(), id);

					String nombreSucursal = ventana.getTextSucursal().getText();
					if (nombreSucursal != null && !nombreSucursal.trim().isEmpty()) {
						DefaultComboBoxModel<SucursalDTO> modelSucursal = (DefaultComboBoxModel<SucursalDTO>) ventana
								.getComboSucursal().getModel();

						for (int i = 0; i < modelSucursal.getSize(); i++) {
							SucursalDTO sucursal = modelSucursal.getElementAt(i);
							if (sucursal != null && sucursal.getNombreSucursal() != null
									&& sucursal.getNombreSucursal().equalsIgnoreCase(nombreSucursal.trim())) {
								ventana.getComboSucursal().setSelectedItem(sucursal);
								break;
							}
						}
					}
				}
			}
		});

		if (clienteSeleccionado != null) {
			agenda.ListarSucursalesxCliente(ventana.getComboSucursal(), clienteSeleccionado.getId());

			String nombreSucursal = ventana.getTextSucursal().getText();
			if (nombreSucursal != null && !nombreSucursal.trim().isEmpty()) {
				DefaultComboBoxModel<SucursalDTO> modelSucursal = (DefaultComboBoxModel<SucursalDTO>) ventana
						.getComboSucursal().getModel();

				for (int i = 0; i < modelSucursal.getSize(); i++) {
					SucursalDTO sucursal = modelSucursal.getElementAt(i);
					if (sucursal != null && sucursal.getNombreSucursal() != null
							&& sucursal.getNombreSucursal().equalsIgnoreCase(nombreSucursal.trim())) {
						ventana.getComboSucursal().setSelectedItem(sucursal);
						break;
					}
				}
			}
		}
	}

	private void llenarComboTecnicos(VentanaVisualizarEquipos ventana) {
		JComboBox<UsuarioDTO> comboTecnico = ventana.getComboTecnico();

		String textoTecnicoActual = ventana.getTextNombreTecnico().getText().trim();

		agenda.ListarTecnicosV(comboTecnico);

		boolean seleccionEncontrada = false;
		if (!textoTecnicoActual.isEmpty()) {
			DefaultComboBoxModel<UsuarioDTO> model = (DefaultComboBoxModel<UsuarioDTO>) comboTecnico.getModel();

			for (int i = 0; i < model.getSize(); i++) {
				UsuarioDTO tecnico = model.getElementAt(i);
				String NombreCompletoTecnico = tecnico.getNombre() + " " + tecnico.getApellido();

				if (tecnico != null && tecnico.getNombre() != null) {
					if (NombreCompletoTecnico.equalsIgnoreCase(textoTecnicoActual)) {
						comboTecnico.setSelectedIndex(i);
						seleccionEncontrada = true;
						break;
					}
				}
			}
		}

		if (!seleccionEncontrada) {
			comboTecnico.setSelectedIndex(-1);
		}
	}

	private void llenarComboEstadoFisico(VentanaVisualizarEquipos ventana) {
		JComboBox<String> comboEstadoFisico = ventana.getComboEstadoFisico();
		String estadoFisicoActual = ventana.getTextEstadoFisico().getText().trim();
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboEstadoFisico.getModel();

		for (int i = 0; i < model.getSize(); i++) {
			if (estadoFisicoActual.equalsIgnoreCase(model.getElementAt(i))) {
				comboEstadoFisico.setSelectedIndex(i);
				break;
			}
		}

		comboEstadoFisico.addItemListener(e -> {
			if (e.getStateChange() != ItemEvent.SELECTED)
				return;

			String nuevoEstado = (String) comboEstadoFisico.getSelectedItem();
			if (nuevoEstado == null)
				return;

			String estadoAnterior = ventana.getTextEstadoFisico().getText();
			if (nuevoEstado.equals(estadoAnterior))
				return;

			ventana.setTextEstadoFisico(nuevoEstado);
			ventana.getFechaSalida().setDate("Enviado".equals(nuevoEstado) ? fechaParseadaHOY : null);

			gestorInterfaz.verificarPresupuesto(ventana);
		});
	}

	private void llenarComboEstadoTecnico(VentanaVisualizarEquipos ventana) {
		JComboBox<String> comboEstadoTecnico = ventana.getComboEstadoTecnico();
		String estadoTecnicoActual = ventana.getTextEstadoTecnico().getText().trim();
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboEstadoTecnico.getModel();

		for (int i = 0; i < model.getSize(); i++) {
			if (estadoTecnicoActual.equalsIgnoreCase(model.getElementAt(i))) {
				comboEstadoTecnico.setSelectedIndex(i);
				break;
			}
		}

		comboEstadoTecnico.addItemListener(e -> {
			if (e.getStateChange() != ItemEvent.SELECTED)
				return;

			String nuevoEstado = (String) comboEstadoTecnico.getSelectedItem();
			if (nuevoEstado == null)
				return;

			String estadoAnterior = ventana.getTextEstadoTecnico().getText();
			if (nuevoEstado.equals(estadoAnterior))
				return;

			ventana.setTextEstadoTecnico(nuevoEstado);
			ventana.getFechaReparacion().setDate("Sin Revisar".equals(nuevoEstado) ? null : fechaParseadaHOY);

			gestorInterfaz.verificarPresupuesto(ventana);
		});
	}

	private void llenarComboEstadoComercial(VentanaVisualizarEquipos ventana) {
		JComboBox<String> comboEstadoComercial = ventana.getComboEstadoComercial();
		String estadoComercialActual = ventana.getTextEstadoComercial().getText().trim();
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboEstadoComercial.getModel();

		for (int i = 0; i < model.getSize(); i++) {
			if (estadoComercialActual.equalsIgnoreCase(model.getElementAt(i))) {
				comboEstadoComercial.setSelectedIndex(i);
				break;
			}
		}

		comboEstadoComercial.addItemListener(e -> {
			if (e.getStateChange() != ItemEvent.SELECTED)
				return;

			String nuevoEstado = (String) comboEstadoComercial.getSelectedItem();
			if (nuevoEstado == null)
				return;

			String estadoAnterior = ventana.getTextEstadoComercial().getText();
			if (nuevoEstado.equals(estadoAnterior))
				return;

			ventana.setTextEstadoComercial(nuevoEstado);
			ventana.getFechaRespuesta()
					.setDate("A la Espera de Aceptación".equals(nuevoEstado) ? null : fechaParseadaHOY);

			gestorInterfaz.verificarPresupuesto(ventana);
		});
	}

	private void llenarComboIngreso(VentanaVisualizarEquipos ventana) {
		JComboBox<String> comboIngreso = ventana.getComboIngreso();

		String ingresoActual = ventana.getTextLugarDeIngreso().getText().trim();
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboIngreso.getModel();
		boolean seleccionEncontrada = false;
		for (int i = 0; i < model.getSize(); i++) {
			String ingreso = model.getElementAt(i);
			if (ingreso != null && ingreso.equalsIgnoreCase(ingresoActual)) {
				comboIngreso.setSelectedIndex(i);
				seleccionEncontrada = true;
				break;
			}
		}
	}

	private void llenarComboELS(VentanaVisualizarEquipos ventana) {
		agenda.ListarELS(ventana.getComboELS());
		ventana.getComboELS().setSelectedIndex(-1);
	}

	private void deshabilitarCampos(VentanaVisualizarEquipos ventana) {
		gestorInterfaz.deshabilitarCampos(ventana);
	}

	private void abrirExcelDeEquipo() {
		gestorExcel = new GestorArchivosExcel(agenda.getUbicacionBase());
		ventanaExcel = new VentanaExcel(gestorExcel);

		agregarListenersExcel(ventanaExcel);
	}

	private void agregarListenersExcel(VentanaExcel ventanaExcel2) {
		ventanaExcel2.getBtnRepar().addActionListener(e -> {
			gestorExcel.abrirReparaciones();
			ventanaExcel2.dispose();
		});

		ventanaExcel2.getBtnCaja().addActionListener(e -> {
			gestorExcel.abrirCaja();
			ventanaExcel2.dispose();
		});

		ventanaExcel2.getBtnDetalleGastos().addActionListener(e -> {
			gestorExcel.abrirDetalleGastosAnioActual();
			ventanaExcel2.dispose();
		});

		ventanaExcel2.getBtnAbrirTodos().addActionListener(e -> {
			gestorExcel.abrirTodosLosArchivos();
			ventanaExcel2.dispose();
		});
	}

	private void cerrarVentanaAnterior() {
		if (ventanaVisualizarEquipos != null) {
			ventanaVisualizarEquipos.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					if (!guardado) {
						int opcion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
								"Hay cambios sin guardar. ¿Desea guardar antes de salir?", "Aviso",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

						if (opcion == JOptionPane.YES_OPTION) {
							ReparacionDTO reparacionAeditar = gestorDatos.extraerDatos(ventanaVisualizarEquipos,
									reparacionActual);

							if (reparacionAeditar != null) {
								guardarCambios(ventanaVisualizarEquipos);
								ventanaVisualizarEquipos.dispose();
								ventanaVisualizarEquipos = null;
							} else {
								ventanaVisualizarEquipos
										.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
							}
						} else if (opcion == JOptionPane.NO_OPTION) {
							ventanaVisualizarEquipos.dispose();
							ventanaVisualizarEquipos = null;
						}
					} else {
						ventanaVisualizarEquipos.dispose();
						ventanaVisualizarEquipos = null;
					}
				}
			});
		}
	}

	public void procesarEventos(ActionEvent e) {
		if (ventanaVisualizarEquipos == null)
			return;

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
		if (ubicacion.equalsIgnoreCase("Buenos Aires")) {
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