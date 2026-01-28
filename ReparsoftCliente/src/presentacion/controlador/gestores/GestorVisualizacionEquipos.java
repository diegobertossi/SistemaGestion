package presentacion.controlador.gestores;

import java.awt.Color;
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

//import presentacion.controlador.gestores.GestorArchivosExcel;
import dto.ClienteDTO;
import dto.RegistroEntradaReporteDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import modelo.Agenda;
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
 * GestorVisualizacionEquipos Responsable de: - Cargar y mostrar datos de
 * equipos en pantalla - Navegación entre equipos (siguiente, anterior, primero,
 * último) - Llenar tabla de repuestos - Llenar combos (clientes, técnicos,
 * sucursales) - Verificar presupuestos y aplicar colores - Editar y guardar
 * cambios - Gestionar envío de avisos
 */
public class GestorVisualizacionEquipos {

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

	}

	/**
	 * Abre la ventana de visualización de equipos
	 */
	public void abrirVentanaVisualizarEquipos() {
		int els = obtenerNumeroELS() - 1;
		String ubicacion = agenda.getUbicacionBase();

		if ((ubicacion.compareTo("Bariloche") == 0 && els >= 988)
				|| (ubicacion.compareTo("Buenos Aires") == 0 && els >= 24900)) {

			ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
			controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
			SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

			try {
				// Inicializar las variables de navegación con el ÚLTIMO ELS
				if (ubicacion.equals("Bariloche")) {
					elsActual = els;
				} else if (ubicacion.equals("Buenos Aires")) {
					elsActualBSAS = els;
				}

				cargarDatosEquipo(ventanaVisualizarEquipos, ubicacion.equals("Bariloche") ? elsActual : elsActualBSAS);
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
			if (ubicacion.equals("Bariloche")) {
				elsActual = elsEspecifico;
			} else if (ubicacion.equals("Buenos Aires")) {
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
		// verificarPresupuesto(ventana);
		gestorInterfaz.verificarPresupuesto(ventana);

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

		if (ubicacion.equals("Bariloche")) {
			actualizar = procesarNavegacionBariloche(tipo, tam);
		} else if (ubicacion.equals("Buenos Aires")) {
			actualizar = procesarNavegacionBuenosAires(tipo, tam);
		}

		if (actualizar) {
			try {
				cargarDatosEquipo(ventanaVisualizarEquipos, ubicacion.equals("Bariloche") ? elsActual : elsActualBSAS);
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
				JOptionPane.showMessageDialog(null, "No hay más reparaciones", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		case "ANTERIOR":
			if (elsActual > 988) {
				elsActual--;
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Esta es la primera reparación", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
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
				JOptionPane.showMessageDialog(null, "No hay más reparaciones", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		case "ANTERIOR":
			if (elsActualBSAS > 24900) {
				elsActualBSAS--;
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Esta es la primera reparación", "Mensaje Informativo",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
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

		// Verificar si es null antes de usar
		if (reparacionAeditar == null) {
			// El gestor ya mostró el popup con los caracteres inválidos
			// No continuar con el proceso de guardado
			return;
		}

		else {

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

		// Presupuesto
//		ventana.getBotonPresupuestar().addActionListener(e -> abrirPresupuesto(ventana));

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
			abrirVentanaEstados();
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
	}

	private void abrirVentanaEstados() {
		ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(false);
		ventanaEstados = editarEstados(ventanaVisualizarEquipos);

		// Listener para botón ACEPTAR EDICIÓN
		ventanaEstados.getBtnAceptarEdicion().addActionListener(e -> {
			aceptarEdicionEstados(ventanaVisualizarEquipos);
		});

		// Listener para botón EDITAR LUGAR DE INGRESO
		ventanaEstados.getBtnHabilitarLugarIngreso().addActionListener(e -> {
			habilitarLugarIngreso();
		});
	}

	// =============================================
	// MÉTODOS DE GESTIÓN DE ESTADOS
	// =============================================

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
			if (button.getText()
					.compareToIgnoreCase(ventanaVisualizarEquipos.getTextEstadoComercial().getText()) == 0) {
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

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		java.util.Date fechaParseadaHOY = null;

		try {
			fechaParseadaHOY = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(LocalDateTime.now()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (ventanaVisualizarEquipos.getTextEstadoFisico().getText().compareTo(estadoFisico) != 0) {
			ventanaVisualizarEquipos.setTextEstadoFisico(estadoFisico);

			if (estadoFisico == "Enviado") {
				ventanaVisualizarEquipos.getFechaSalida().setDate(fechaParseadaHOY);
			}
		}

		if (ventanaVisualizarEquipos.getTextEstadoTecnico().getText().compareTo(estadoTecnico) != 0) {
			ventanaVisualizarEquipos.setTextEstadoTecnico(estadoTecnico);

			if (estadoTecnico == "Sin Revisar") {
				ventanaVisualizarEquipos.getFechaReparacion().setDate(null);
			} else {
				ventanaVisualizarEquipos.getFechaReparacion().setDate(fechaParseadaHOY);
			}
		}

		if (ventanaVisualizarEquipos.getTextEstadoComercial().getText().compareTo(estadoComercial) != 0) {
			ventanaVisualizarEquipos.setTextEstadoComercial(estadoComercial);

			if (estadoComercial == "A la Espera de Aceptación") {
				ventanaVisualizarEquipos.getFechaRespuesta().setDate(null);
			} else {
				ventanaVisualizarEquipos.getFechaRespuesta().setDate(fechaParseadaHOY);
			}
		}

		if (ventanaVisualizarEquipos.getTextLugarDeIngreso().getText().compareTo(lugarDeIngreso) != 0) {
			ventanaVisualizarEquipos.setTextLugarDeIngreso(lugarDeIngreso);
		}

		this.ventanaEstados.dispose();
		this.ventanaEstados = null;
		ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(true);
	}

	/**
	 * Habilita la edición del lugar de ingreso en la ventana de estados
	 */
	private void habilitarLugarIngreso() {
		ventanaEstados.getRdbtnIngresoMDP().setEnabled(true);
		ventanaEstados.getRdbtnIngresoBRC().setEnabled(true);
		ventanaEstados.getRdbtnIngresoCABA().setEnabled(true);
	}

	private void abrirEnviarCorreoWSP(VentanaVisualizarEquipos ventanaVisualizarEquipos2) {

		ventanaEnviarCorreoOwsp = new VentanaEnviarCorreoOwsp(controlador);

//        ventanaEnviarCorreoOwsp.getBtnEnviarWST().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                abrirVentanaWsp(ventanaVisualizarEquipos);
//            }
//        });
	}

	private void abrirVentanaWsp(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		ventanaWSP = new VentanaWSP(this);

		String cliente = ventanaVisualizarEquipos.getTextCliente().getText();

		String NombreContacto = this.agenda.ContactoPorCliente(cliente);
		String TelefonoContacto = this.agenda.obtenerTelefonoPorCliente(cliente);

		ventanaWSP.getTextNombreContacto().setText(NombreContacto);
		ventanaWSP.getTextNumeroContacto().setText(TelefonoContacto);

		ventanaWSP.getTextCliente().setText(cliente);
		ventanaWSP.getBtnEnviar().addActionListener(controlador);
		ventanaWSP.getBtnEditarNmero().addActionListener(controlador);
		ventanaWSP.getBtnClientes().addActionListener(controlador);
		ventanaWSP.getBtnUtilizarContactoBuscado().addActionListener(controlador);
		ventanaWSP.getBtnUtilizarContacto().addActionListener(controlador);
		ventanaWSP.getComboOrganizacion().addActionListener(controlador);
		ventanaWSP.getComboNombreBuscado().addActionListener(controlador);

//        llenarComboOrganizacion();
//        llenarComboNombreWSP();
//
//        performActionOnTextComponents(ventanaWSP);
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

	/**
	 * Busca equipo por ELS
	 */
	private void buscarPorELS(VentanaVisualizarEquipos ventana) {
		Object selectedItem = ventana.getComboELS().getSelectedItem();
		if (selectedItem != null && !selectedItem.toString().isEmpty()) {
			try {
				int els = Integer.parseInt(selectedItem.toString());
				cargarDatosEquipo(ventana, els);
				// agregarListeners(ventana);
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
			JOptionPane.showMessageDialog(null, "Debe guardar los cambios realizados para poder presupuestar.",
					"Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		} else {
			int els = Integer.parseInt(ventana.getTextELS());
			controlador.getControladorPresupuestos().TomarDatosDeTablasParaVisualizacion(els);
		}
	}

	/**
	 * Llena combo de clientes manteniendo la selección actual basada en el texto
	 * del campo y configura el listener para llenar automáticamente las sucursales
	 */
	private void llenarComboClientes(VentanaVisualizarEquipos ventana) {
		JComboBox<ClienteDTO> comboClientes = ventana.getComboClientes();

		// Remover listeners existentes temporalmente para evitar conflictos
		ItemListener[] listeners = comboClientes.getItemListeners();
		for (ItemListener listener : listeners) {
			comboClientes.removeItemListener(listener);
		}

		// 1. Obtener el texto actual del campo de cliente (modo visualización)
		String textoClienteActual = ventana.getTextCliente().getText().trim();

		// 2. Actualizar la lista de clientes en el combo
		agenda.ListarCliente(comboClientes);

		// 3. Buscar y seleccionar el cliente que coincide con el texto actual
		boolean seleccionEncontrada = false;
		ClienteDTO clienteSeleccionado = null;

		if (!textoClienteActual.isEmpty()) {
			DefaultComboBoxModel<ClienteDTO> model = (DefaultComboBoxModel<ClienteDTO>) comboClientes.getModel();

			for (int i = 0; i < model.getSize(); i++) {
				ClienteDTO cliente = model.getElementAt(i);
				if (cliente != null && cliente.getRazon_Social() != null) {
					// Comparar el nombre del cliente con el texto del campo
					if (cliente.getRazon_Social().equalsIgnoreCase(textoClienteActual)) {
						comboClientes.setSelectedIndex(i);
						clienteSeleccionado = cliente;
						seleccionEncontrada = true;
						break;
					}
				}
			}
		}

		// 4. Si no se encontró coincidencia, dejar el combo sin selección
		if (!seleccionEncontrada) {
			comboClientes.setSelectedIndex(-1);
		}

		// 5. Agregar el listener para el cambio de cliente (llenado de sucursales)
		comboClientes.addItemListener(new ItemListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && ventana.getComboClientes().getSelectedItem() != null) {

					ClienteDTO cliente = (ClienteDTO) ventana.getComboClientes().getSelectedItem();
					int id = cliente.getId();

					// Llenar combo de sucursales para el cliente seleccionado
					agenda.ListarSucursalesxCliente(ventana.getComboSucursal(), id);

					// Seleccionar automáticamente la sucursal basada en el texto del JTextField
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

		// 6. Si se encontró un cliente, disparar el evento para llenar las sucursales
		if (clienteSeleccionado != null) {
			// Disparar el evento manualmente para llenar las sucursales
			agenda.ListarSucursalesxCliente(ventana.getComboSucursal(), clienteSeleccionado.getId());

			// Seleccionar la sucursal correspondiente
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

	/**
	 * Llena combo de técnicos manteniendo la selección actual basada en el texto
	 * del campo
	 */
	private void llenarComboTecnicos(VentanaVisualizarEquipos ventana) {
		JComboBox<UsuarioDTO> comboTecnico = ventana.getComboTecnico();

		// 1. Obtener el texto actual del campo de técnico (modo visualización)
		String textoTecnicoActual = ventana.getTextNombreTecnico().getText().trim();

		// 2. Actualizar la lista de técnicos en el combo
		agenda.ListarTecnicosV(comboTecnico);

		// 3. Buscar y seleccionar el técnico que coincide con el texto actual
		boolean seleccionEncontrada = false;
		if (!textoTecnicoActual.isEmpty()) {
			DefaultComboBoxModel<UsuarioDTO> model = (DefaultComboBoxModel<UsuarioDTO>) comboTecnico.getModel();

			for (int i = 0; i < model.getSize(); i++) {
				UsuarioDTO tecnico = model.getElementAt(i);
				String NombreCompletoTecnico = tecnico.getNombre() + " " + tecnico.getApellido();

				if (tecnico != null && tecnico.getNombre() != null) {

					// Comparar el nombre del técnico con el texto del campo
					if (NombreCompletoTecnico.equalsIgnoreCase(textoTecnicoActual)) {
						comboTecnico.setSelectedIndex(i);
						seleccionEncontrada = true;
						break;
					}
				}
			}
		}

		// 4. Si no se encontró coincidencia, dejar el combo sin selección
		if (!seleccionEncontrada) {
			comboTecnico.setSelectedIndex(-1);
		}
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

	// Métodos de acción para botones (implementación modular)

	private void abrirExcelDeEquipo() {
		// Implementación apertura Excel correspondiente, vía GestorArchivosExcel
		gestorExcel = new GestorArchivosExcel(agenda.getUbicacionBase());
		ventanaExcel = new VentanaExcel(gestorExcel);

		agregarListenersExcel(ventanaExcel);

	}

	private void agregarListenersExcel(VentanaExcel ventanaExcel2) {
		ventanaExcel2.getBtnRepar().addActionListener(e -> {
			gestorExcel.abrirReparaciones();
			ventanaExcel2.dispose(); // Cerrar aquí
		});

		ventanaExcel2.getBtnCaja().addActionListener(e -> {
			gestorExcel.abrirCaja();
			ventanaExcel2.dispose(); // Cerrar aquí
		});

		ventanaExcel2.getBtnDetalleGastos().addActionListener(e -> {
			gestorExcel.abrirDetalleGastosAnioActual();
			ventanaExcel2.dispose(); // Cerrar aquí
		});

		ventanaExcel2.getBtnAbrirTodos().addActionListener(e -> {
			gestorExcel.abrirTodosLosArchivos();
			ventanaExcel2.dispose(); // Cerrar aquí
		});
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
	                    int opcion = JOptionPane.showConfirmDialog(
	                        ventanaVisualizarEquipos,
	                        "Hay cambios sin guardar. ¿Desea guardar antes de salir?", 
	                        "Aviso",
	                        JOptionPane.YES_NO_CANCEL_OPTION,
	                        JOptionPane.QUESTION_MESSAGE
	                    );

	                    if (opcion == JOptionPane.YES_OPTION) {
	                        // Extraer datos y verificar caracteres inválidos
	                        ReparacionDTO reparacionAeditar = gestorDatos.extraerDatos(ventanaVisualizarEquipos, reparacionActual);
	                        
	                        if (reparacionAeditar != null) {
	                            // No hay caracteres inválidos, proceder a guardar
	                            guardarCambios(ventanaVisualizarEquipos);
	                            ventanaVisualizarEquipos.dispose();
	                            ventanaVisualizarEquipos = null;
	                        } else {
	                            // Hay caracteres inválidos
	                            // El gestorDatos ya mostró el popup con los caracteres inválidos
	                            // No cerrar la ventana, permitir al usuario corregir
	                            // Prevenir el cierre de la ventana
	                            ventanaVisualizarEquipos.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
	                        }
	                    } 
	                    else if (opcion == JOptionPane.NO_OPTION) {
	                        // Usuario no quiere guardar, cerrar directamente
	                        ventanaVisualizarEquipos.dispose();
	                        ventanaVisualizarEquipos = null;
	                    } 
	                    else if (opcion == JOptionPane.CANCEL_OPTION) {
	                        // Usuario canceló, no hacer nada
	                        // La ventana permanece abierta
	                        return;
	                    }
	                } else {
	                    // Ya está guardado, cerrar directamente
	                    ventanaVisualizarEquipos.dispose();
	                    ventanaVisualizarEquipos = null;
	                }
	            }
	        });
	    }
	}

	/**
	 * Procesa eventos delegados de ActionListener
	 */
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
