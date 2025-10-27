package presentacion.controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.inet.jortho.SpellChecker;

import VistaPropias.GestorArchivosExcel;

//import com.sun.xml.internal.org.jvnet.fastinfoset.sax.ExtendedContentHandler;

import modelo.Agenda;
import presentacion.reportes.ReporteRegistroEntrada;
import presentacion.vista.VentanaAgregarEquipo;
import presentacion.vista.VentanaAgregarRepuesto;
import presentacion.vista.VentanaBusquedaEquipo;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaEstados;
import presentacion.vista.VentanaExcel;
import presentacion.vista.VentanaGenerarPresupuesto;
import presentacion.vista.VentanaRemitos;
import presentacion.vista.VentanaVerificarIngresoAnterior;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaWSP;
import tiposPropios.MonedaFormatter;
import presentacion.vista.VentanaClientesWSP;
import presentacion.vista.VentanaDatosFacturacion;
import presentacion.vista.VentanaEnviarCorreoOwsp;
import dto.ClienteDTO;
import dto.ClienteWSPDTO;

import dto.RegistroEntradaReporteDTO;

import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.SucursalDTO;
import dto.UsuarioDTO;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.net.URI;

import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class ControladorReparacion implements ActionListener, MouseListener, KeyListener, ItemListener {

	private VentanaVisualizarEquipos ventanaVisualizarEquipos;
	private VentanaEquipos ventanaEquipos;
	private GestorArchivosExcel gestorExcel;

	private VentanaAgregarRepuesto ventanaagregarRepuesto;

	private VentanaEnviarCorreoOwsp ventanaEnviarCorreoOwsp;

	private VentanaDatosFacturacion ventanaDatosFacturacion;
	private VentanaAgregarEquipo ventanaAgregarEquipo;
	private VentanaEstados ventanaEstados;
	private VentanaVerificarIngresoAnterior ventanaVerificarIngresoAnterior;
	private VentanaWSP ventanaWSP;
	private VentanaClientesWSP ventanaClientesWSP;

	private VentanaExcel ventanaExcel;

	private VentanaBusquedaEquipo ventanaBusquedaEquipo;

	private ControladorUsuLogin controladorUsuLogin;
	private ControladorPresupuestos controladorpresupuestos;
	private VentanaGenerarPresupuesto ventanaGenerarPresupuesto;
	private ControladorSalidas controladorSalidas;

	private VentanaClientes ventanaCliente;
	private VentanaRemitos ventanaRemitos;

	private ControladorCliente controladorCliente;

	@SuppressWarnings("unused")
	private int NumeroELSSeleccionado;

	private List<RepuestosDTO> Repuestos_en_tabla;

	private List<ClienteWSPDTO> clientesWSP_en_tabla;
	private ClienteWSPDTO clienteWSP_Elegido;

	private ClienteDTO Cliente;
	private SucursalDTO Sucursal;

	private RepuestosDTO repuestoElegido;
	private RepuestosDTO nuevoRepuesto;
	boolean guardado = true;

	private Agenda agenda;
	private int ELSinicial = 988; // poner en 1 para arrancar los ELS desde el número 1 //
	// private int ELSinicialBSAS = 1;
	private int ELSinicialBSAS = 24900;
	private int ELS = 1;

	private ReparacionDTO reparacion;
	private int NumeroELS;
	private int NumeroELSParaRemito;

	private String estadoFisico = "";
	private String estadoTecnico = "";
	private String estadocomercial = "";
	private String lugarDeIngreso = "";
	private String NombreEq = "";
	private String Marca = "";
	private String Modelo = "";
	private String Serie = "";
	private String ContactoWSP = "";
	private String nombreBuscado = "";

	@SuppressWarnings("unused")
	private String numeros = "";
	@SuppressWarnings("unused")
	private String part1;
	@SuppressWarnings("unused")
	private String part2;

	private int idCli;
	private int idSuc;

	private boolean actualizarEnlistado = false;

	private String fechaentrada;
	private String fechaFarbricacion;

	private MonedaFormatter monedaFormatter;

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	private List<VentanaVisualizarEquipos> ventanasAbiertas = new ArrayList<>();
	// private VentanaVisualizarEquipos ventanaConFoco; // Referencia a la ventana
	// que tiene el foco actualmente

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	// En la clase ControladorReparacion
	private List<String> caracteresNoValidosEncontrados = new ArrayList<>();
	
	
	private static final Color PAGADO = new Color(144, 238, 144);           // Verde menta suave
	private static final Color SIN_PRESUPUESTAR = new Color(211, 211, 211); // Gris claro
	private static final Color PARCIAL = new Color(255, 239, 153);          // Amarillo pastel
	private static final Color FALTA_PAGO = new Color(255, 182, 193);       // Rosa suave
	private static final Color NO_ACEPTADO = new Color(216, 191, 216);      // Lila suave
	private static final Color ESPERANDO = new Color(173, 216, 230);        // Azul cielo claro
	private static final Color SIN_REPARACION = new Color(255, 218, 185);   // Melocotón suave
	

	public ControladorReparacion(VentanaEquipos ventanaEquipos, ControladorUsuLogin controladorUsuLogin, Agenda agendas,
			ControladorPresupuestos controladorPresupuestos, ControladorSalidas controladorSalidas,
			ControladorCliente controladorCliente) {

		this.ventanaEquipos = ventanaEquipos;
		this.ventanaEquipos.getBtnVisualizarEquipos().addActionListener(this);
		this.ventanaEquipos.getBtnAgregarEquipos().addActionListener(this);
		this.agenda = agendas;
		this.reparacion = null;
		this.Repuestos_en_tabla = null;
		this.controladorUsuLogin = controladorUsuLogin;
		this.controladorpresupuestos = controladorPresupuestos;
		this.controladorSalidas = controladorSalidas;
		this.controladorCliente = controladorCliente;

		this.gestorExcel = new GestorArchivosExcel(agenda.getUbicacionBase());

	}

	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventanaEquipos.getBtnVisualizarEquipos()) {

			int ELS = DameNumeroELS() - 1;

			if ((agenda.getUbicacionBase().compareTo("Bariloche") == 0 && ELS >= 988)
					|| (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0 && ELS >= 24900)) {

				ventanaVisualizarEquipos = new VentanaVisualizarEquipos(this);

				cerraVentanaVisualizarEquipo();
				monedaFormatter = new MonedaFormatter();

				controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);

				SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

				try {
					TomarDatosDeTablas(ventanaVisualizarEquipos);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}

				this.ventanaEquipos.dispose();

				agregarListenersVentanaVisualizarEquipos(ventanaVisualizarEquipos);

				llenarComboELSvisualizacion();

			} else {

				Object mje = "No se ha ingresado ningún equipo.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			}

		}

		else if (e.getSource() == this.ventanaEquipos.getBtnAgregarEquipos()) {

			ventanaAgregarEquipo = new VentanaAgregarEquipo(this);
			cerraVentanaAgregarEquipo();

			new ArrayList<>();
			Calendar c2 = new GregorianCalendar();
			ventanaAgregarEquipo.getFechaEntrada().setCalendar(c2);

			ELS = DameNumeroELS();

			agregarListenersVentanaAgregarEquipos();

			this.ventanaEquipos.dispose();

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonSiguiente()) {

			procesarNavegacion(TipoNavegacion.SIGUIENTE);

		} else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonAnterior()) {

			procesarNavegacion(TipoNavegacion.ANTERIOR);

		} else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonPrimero()) {

			procesarNavegacion(TipoNavegacion.PRIMERO);

		} else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonUltimo()) {

			procesarNavegacion(TipoNavegacion.ULTIMO);
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonAvisoInforme()) {

			enviarAvisoInforme(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonAvisoEquipoListo()) {

			enviarAvisoEquipoListo(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonRespuestaAlTecnico()) {

			enviarRespuestaAlTecnico(ventanaVisualizarEquipos);

		}

		else if (ventanaVisualizarEquipos != null && e.getSource() == ventanaVisualizarEquipos.getBtnEditar()) {

			editar(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonRegistroIngreso()) {

			List<RegistroEntradaReporteDTO> lista = new ArrayList<RegistroEntradaReporteDTO>();

			RegistroEntradaReporteDTO rep = TomarDatosPantallaVisualizacion();

			lista.add(rep);

			ReporteRegistroEntrada reporte = new ReporteRegistroEntrada(rep, lista, agenda);
			reporte.mostrar();

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnGuardarCambios()) {

			guardarCambios(ventanaVisualizarEquipos);
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonEditarEstados()) {

			ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(false);

			ventanaEstados = editarEstados(ventanaVisualizarEquipos);

			ventanaEstados.getBtnAceptarEdicion().addActionListener(this);
			ventanaEstados.getBtnHabilitarLugarIngreso().addActionListener(this);

		}

		else if (this.ventanaEstados != null && e.getSource() == this.ventanaEstados.getBtnHabilitarLugarIngreso()) {

			ventanaEstados.getRdbtnIngresoBRC().setEnabled(true);
			ventanaEstados.getRdbtnIngresoCABA().setEnabled(true);
			ventanaEstados.getRdbtnIngresoMDP().setEnabled(true);

		}

		else if (this.ventanaEstados != null && e.getSource() == this.ventanaEstados.getBtnAceptarEdicion()) {

			ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(true);
			aceptarEdicionEstados(ventanaVisualizarEquipos);

		}

		else if (ventanaVisualizarEquipos != null && e.getSource() == ventanaVisualizarEquipos.getBotonPresupuestar()) {

			presupuestar(ventanaVisualizarEquipos);

		}

		else if (ventanaVisualizarEquipos != null && e.getSource() == ventanaVisualizarEquipos.getBtnfacturar()) {

			facturar(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnenviarCorreoOwsp()) {

			enviarCorreoOwsp(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnabrirExcel()) {

			abrirExcel(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnBuscarELS()) {

			if (ventanaVisualizarEquipos.getComboELS().getSelectedItem() != null
					&& ventanaVisualizarEquipos.getComboELS().getSelectedIndex() != -1) {

				Integer ELS = Integer.parseInt(ventanaVisualizarEquipos.getComboELS().getSelectedItem().toString());
				// reparacion = agenda.dameReparacionXels(ELS);

				try {
					TomarDatosDeTablasBusquedaOrden(ELS);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				agregarListenersVentanaVisualizarEquipos(ventanaVisualizarEquipos);

			}
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnBuscar()) {

			ventanaBusquedaEquipo = new VentanaBusquedaEquipo(this);

			ventanaBusquedaEquipo.btnBuscar.addActionListener(f -> realizarBusqueda());

			ventanaBusquedaEquipo.textPane.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					actualizarCursor(e);
				}
			});

			this.ventanaBusquedaEquipo.textPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					manejarClickEnTexto(e);
				}
			});

		}

		else if (this.ventanaEnviarCorreoOwsp != null
				&& e.getSource() == this.ventanaEnviarCorreoOwsp.getBtnEnviarWST()) {

			abrirVentanaWsp(ventanaVisualizarEquipos);

		}

		else if (this.ventanaWSP != null && e.getSource() == this.ventanaWSP.getBtnClientes()) {

			ventanaClientesWSP = new VentanaClientesWSP(this);
			ventanaClientesWSP.getTablaClienteSWSP().addMouseListener(this);
			ventanaClientesWSP.getBtnAgregarCliente().addActionListener(this);
			ventanaClientesWSP.getBtnCancelarEdicion().addActionListener(this);
			ventanaClientesWSP.getBtnCancelarNuevo().addActionListener(this);
			ventanaClientesWSP.getBtnEditarCliente().addActionListener(this);
			ventanaClientesWSP.getBtnEliminarCliente().addActionListener(this);
			ventanaClientesWSP.getBtnGuardarEdicion().addActionListener(this);
			ventanaClientesWSP.getBtnGuardarNuevo().addActionListener(this);

			llenarTablaClientesWSP();

			performActionOnTextComponents(ventanaWSP);

		}

		else if (this.ventanaWSP != null && e.getSource() == this.ventanaWSP.getBtnEditarNmero()) {

			ventanaWSP.getTextNumero().setEditable(true);

		}

		else if (this.ventanaWSP != null && e.getSource() == this.ventanaWSP.getBtnUtilizarContactoBuscado()) {

			String numeroParaEnviarString = ventanaWSP.getTextnumeroContactoBuscado().getText();
			ventanaWSP.getTextNumero().setText(numeroParaEnviarString);
			ventanaWSP.getTextMensaje().setEditable(true);
			ventanaWSP.getTextMensaje().setText("Hola");

		}

		else if (this.ventanaWSP != null && e.getSource() == this.ventanaWSP.getBtnUtilizarContacto()) {

			String numeroParaEnviarString = ventanaWSP.getTextNumeroContacto().getText();
			ventanaWSP.getTextNumero().setText(numeroParaEnviarString);
			ventanaWSP.getTextMensaje().setEditable(true);
			ventanaWSP.getTextMensaje().setText("Hola");

		}

		else if (this.ventanaWSP != null && e.getSource() == this.ventanaWSP.getBtnEnviar()) {

			String numeroParaStringEnviarString = ventanaWSP.getTextNumero().getText();
			String nombreParaEnviarString = ventanaWSP.getTextnumeroContactoBuscado().getText();
			String mensajeParaEnviarString = ventanaWSP.getTextMensaje().getText();

			consumoAPI.ConsumoAPI.abrirWSP(nombreParaEnviarString, numeroParaStringEnviarString,
					mensajeParaEnviarString);

		}

		else if (this.ventanaClientesWSP != null && e.getSource() == this.ventanaClientesWSP.getBtnAgregarCliente()) {

			this.ventanaClientesWSP.getBtnGuardarNuevo().setVisible(true);
			this.ventanaClientesWSP.getBtnCancelarNuevo().setVisible(true);
			this.ventanaClientesWSP.getBtnEliminarCliente().setEnabled(false);
			this.ventanaClientesWSP.getBtnEditarCliente().setEnabled(false);

			this.ventanaClientesWSP.getTxtNombre().setText("");
			this.ventanaClientesWSP.getTxtOrganizacion().setText("");
			this.ventanaClientesWSP.getTxtTelefono().setText("");

			clienteWSP_Elegido = null;

			this.ventanaClientesWSP.getTxtNombre().setEditable(true);
			this.ventanaClientesWSP.getTxtOrganizacion().setEditable(true);
			this.ventanaClientesWSP.getTxtTelefono().setEditable(true);

			this.ventanaClientesWSP.getTxtOrganizacion().requestFocus();

		}

		else if (this.ventanaClientesWSP != null && e.getSource() == this.ventanaClientesWSP.getBtnCancelarNuevo()) {

			this.ventanaClientesWSP.getTxtNombre().setText("");
			this.ventanaClientesWSP.getTxtOrganizacion().setText("");
			this.ventanaClientesWSP.getTxtTelefono().setText("");

			clienteWSP_Elegido = null;

			this.ventanaClientesWSP.getBtnGuardarNuevo().setVisible(false);
			this.ventanaClientesWSP.getBtnCancelarNuevo().setVisible(false);

			this.ventanaClientesWSP.getBtnEliminarCliente().setEnabled(true);
			this.ventanaClientesWSP.getBtnAgregarCliente().setEnabled(true);
			this.ventanaClientesWSP.getBtnEditarCliente().setEnabled(true);

			this.ventanaClientesWSP.getTxtNombre().setEditable(false);
			this.ventanaClientesWSP.getTxtOrganizacion().setEditable(false);
			this.ventanaClientesWSP.getTxtTelefono().setEditable(false);

		}

		else if (this.ventanaClientesWSP != null && e.getSource() == this.ventanaClientesWSP.getBtnGuardarNuevo()) {

			clienteWSP_Elegido = null;

			if (this.ventanaClientesWSP.getTxtNombre().getText().equals("")
					|| this.ventanaClientesWSP.getTxtOrganizacion().getText().equals("")
					|| this.ventanaClientesWSP.getTxtTelefono().getText().equals("")) {
				this.ventanaClientesWSP.getErrorMsj("Todos los campos son obligatoriso");

			} else if (existeClienteWSP(this.ventanaClientesWSP.getTxtTelefono().getText())) {
				JOptionPane.showMessageDialog(null, "El Número de teléfono ya existe en otro contacto",
						"TELEFONO EXISTENTE", JOptionPane.ERROR_MESSAGE);
			} else {
				ClienteWSPDTO nuevoClienteWSPDTO = new ClienteWSPDTO(0,
						this.ventanaClientesWSP.getTxtOrganizacion().getText(),
						this.ventanaClientesWSP.getTxtNombre().getText(),
						this.ventanaClientesWSP.getTxtTelefono().getText());

				agenda.agregarClienteWSP(nuevoClienteWSPDTO);
				llenarTablaClientesWSP();
				this.ventanaClientesWSP.getTxtNombre().setText("");
				this.ventanaClientesWSP.getTxtOrganizacion().setText("");
				this.ventanaClientesWSP.getTxtTelefono().setText("");

				clienteWSP_Elegido = null;

			}
		}

		else if (this.ventanaClientesWSP != null && e.getSource() == this.ventanaClientesWSP.getBtnEliminarCliente()) {

			if (clienteWSP_Elegido == null) {
				this.ventanaClientesWSP.getErrorMsj("Seleccione un cliente");
			}

			else {
				int seleccion = JOptionPane.showConfirmDialog(ventanaClientesWSP,
						"¿Está seguro de realizar la operación?", "Confirmación", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (seleccion == JOptionPane.YES_OPTION) {

					int[] filas_seleccionadas = this.ventanaClientesWSP.getTablaClienteSWSP().getSelectedRows();
					for (int fila : filas_seleccionadas) {
						agenda.borrarClienteWSP(clientesWSP_en_tabla.get(fila));
					}

					llenarTablaClientesWSP();
					this.ventanaClientesWSP.getTxtNombre().setText("");
					this.ventanaClientesWSP.getTxtOrganizacion().setText("");
					this.ventanaClientesWSP.getTxtTelefono().setText("");

					clienteWSP_Elegido = null;

				}
			}

		}

		else if (this.ventanaClientesWSP != null && e.getSource() == this.ventanaClientesWSP.getBtnEditarCliente()) {

			if (clienteWSP_Elegido == null) {
				this.ventanaClientesWSP.getErrorMsj("Seleccione un usuario");
			} else {

				this.ventanaClientesWSP.getTxtNombre().setEditable(true);
				this.ventanaClientesWSP.getTxtOrganizacion().setEditable(true);
				this.ventanaClientesWSP.getTxtTelefono().setEditable(true);

				this.ventanaClientesWSP.getBtnGuardarEdicion().setVisible(true);
				this.ventanaClientesWSP.getBtnCancelarEdicion().setVisible(true);
				this.ventanaClientesWSP.getBtnAgregarCliente().setEnabled(false);
				this.ventanaClientesWSP.getBtnEliminarCliente().setEnabled(false);

			}
		} else if (this.ventanaClientesWSP != null
				&& e.getSource() == this.ventanaClientesWSP.getBtnCancelarEdicion()) {

			this.ventanaClientesWSP.getBtnGuardarEdicion().setVisible(false);
			this.ventanaClientesWSP.getBtnCancelarEdicion().setVisible(false);

			this.ventanaClientesWSP.getTxtNombre().setEditable(false);
			this.ventanaClientesWSP.getTxtOrganizacion().setEditable(false);
			this.ventanaClientesWSP.getTxtTelefono().setEditable(false);

			this.ventanaClientesWSP.getBtnEliminarCliente().setEnabled(true);
			this.ventanaClientesWSP.getBtnAgregarCliente().setEnabled(true);

		}

		else if (this.ventanaClientesWSP != null && e.getSource() == this.ventanaClientesWSP.getBtnGuardarEdicion()) {

			if (this.ventanaClientesWSP.getTxtNombre().getText().equals("")
					|| this.ventanaClientesWSP.getTxtOrganizacion().getText().equals("")
					|| this.ventanaClientesWSP.getTxtTelefono().getText().equals("")) {
				this.ventanaClientesWSP.getErrorMsj("Todos los campos son obligatorios");
			} else {
				if (clienteWSP_Elegido != null) {

					clienteWSP_Elegido.setNombreWSP(this.ventanaClientesWSP.getTxtNombre().getText());
					clienteWSP_Elegido.setOrganizacion(this.ventanaClientesWSP.getTxtOrganizacion().getText());
					clienteWSP_Elegido.setTelefonoWSP(this.ventanaClientesWSP.getTxtTelefono().getText());

					agenda.editarClienteWSP(clienteWSP_Elegido);
					llenarTablaClientesWSP();

					this.ventanaClientesWSP.getBtnGuardarEdicion().setVisible(false);
					this.ventanaClientesWSP.getBtnCancelarEdicion().setVisible(false);
					this.ventanaClientesWSP.getTxtNombre().setEditable(false);
					this.ventanaClientesWSP.getTxtOrganizacion().setEditable(false);
					this.ventanaClientesWSP.getTxtTelefono().setEditable(false);

					this.ventanaClientesWSP.getBtnEliminarCliente().setEnabled(true);
					this.ventanaClientesWSP.getBtnEditarCliente().setEnabled(true);

					JOptionPane.showMessageDialog(null, new JLabel("Usuario Editado"), "Edición Exitosa",

							JOptionPane.INFORMATION_MESSAGE);

				}
			}
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnRepuestos()) {

			abrirVentanaRepuestos(ventanaVisualizarEquipos);
			listenerRepuestos(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnEditarRepuesto()) {

			guardarEditarRepuesto(ventanaVisualizarEquipos);

		} else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnEliminarRepuesto()) {

			eliminarRepuesto(ventanaVisualizarEquipos);

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnGenerarRemito()) {

			// System.out.println(ventanaVisualizarEquipos.getTextNumeroRemito().getText());

			generarRemito(ventanaVisualizarEquipos);

		}

		else if (this.ventanaAgregarEquipo != null && e.getSource() == this.ventanaAgregarEquipo.getBtnaltaCliente()) {

			controladorCliente.setLlamadoDesdeAgregarEquipo(true);

			ventanaCliente = controladorCliente.agregarListenersVentanaCliente();

			ventanaCliente.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {

					llenarComboCliente();

				}
			});

		}

		else if (this.ventanaAgregarEquipo != null
				&& e.getSource() == this.ventanaAgregarEquipo.getBotonGenerarRegistro()) {

			if (verificacionDatosIngreso()) {

				List<RegistroEntradaReporteDTO> lista = new ArrayList<RegistroEntradaReporteDTO>();

				RegistroEntradaReporteDTO rep = TomarDatosPantallaIngresoRep();

				if (!caracteresNoValidosEncontrados.isEmpty()) {
					String mensaje = "Caracteres no válidos encontrados: "
							+ String.join(", ", caracteresNoValidosEncontrados);
					JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
				}

				else {
					lista.add(rep);

					ReporteRegistroEntrada reporte = new ReporteRegistroEntrada(rep, lista, agenda);
					reporte.mostrar();
					reporte.guardar();

				}

			}
		}

		else if (this.ventanaAgregarEquipo != null && e.getSource() == this.ventanaAgregarEquipo.getBotonGuardar()) {

			if (verificacionDatosIngreso()) {
				int opcion = 0;

				opcion = JOptionPane.showConfirmDialog(null, "¿Desea guardar este equipo?", "Aviso",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				switch (opcion) {
				case JOptionPane.YES_OPTION: {

					if (Integer.parseInt(this.ventanaAgregarEquipo.getTextELS()) != DameNumeroELS() - 1) {

						ReparacionDTO nuevoReparacion = TomarDatosPantallaIngreso();

						if (!caracteresNoValidosEncontrados.isEmpty()) {
							String mensaje = "Caracteres no válidos encontrados: "
									+ String.join(", ", caracteresNoValidosEncontrados);
							JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
						}

						else {

							this.agenda.agregarReparacionR(nuevoReparacion);

							ventanaAgregarEquipo.getTextAvisoCliente().setEnabled(false);
							ventanaAgregarEquipo.getTextClienteCliente().setEnabled(false);
							ventanaAgregarEquipo.getTextFalla().setEnabled(false);
							ventanaAgregarEquipo.getTextRemitoCliente().setEnabled(false);
							ventanaAgregarEquipo.getTextFechafabricacion().setEnabled(false);
							ventanaAgregarEquipo.getFechaEntrada().setEnabled(false);
							ventanaAgregarEquipo.getComboClientes().setEnabled(false);
							ventanaAgregarEquipo.getComboSucursal().setEnabled(false);
							ventanaAgregarEquipo.getComboMarca().setEnabled(false);
							ventanaAgregarEquipo.getComboNombreEquipo().setEnabled(false);
							ventanaAgregarEquipo.getComboModelo().setEnabled(false);
							ventanaAgregarEquipo.getComboSerie().setEnabled(false);
							ventanaAgregarEquipo.getTextFalla().setEnabled(false);

							ventanaAgregarEquipo.getRdbtnBRC().setEnabled(false);
							ventanaAgregarEquipo.getRdbtnCABA().setEnabled(false);
							ventanaAgregarEquipo.getRdbtnMDP().setEnabled(false);
							ventanaAgregarEquipo.getBtnFechaDefault().setEnabled(false);
							ventanaAgregarEquipo.getBtnGenerarSerie().setEnabled(false);
							ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(true);
							ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().setEnabled(false);
							ventanaAgregarEquipo.getBotonIRaELS().setEnabled(true);

						}

						opcion = JOptionPane.showConfirmDialog(null, "¿Generar Registro de Ingreso?", "Aviso",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

						switch (opcion) {
						case JOptionPane.YES_OPTION:
							System.out.println("Generar Registro de Ingreso");

							ventanaAgregarEquipo.getBotonGenerarRegistro().doClick();

							break;

						case JOptionPane.NO_OPTION:
							System.out.println("No generar Registro de Ingreso");
							break;

						default:
							break;
						}

					}

					else {

						Object mje = "Este equipo ya fue guardado";
						JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				case JOptionPane.NO_OPTION:

					break;

				}

			}
		}

		else if (this.ventanaAgregarEquipo != null && e.getSource() == this.ventanaAgregarEquipo.getBotonIRaELS()) {

			int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos, "¿Desea ir al ELS generado?",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				Integer ELS = Integer.parseInt(ventanaAgregarEquipo.getTextELS());

				ventanaAgregarEquipo.dispose();
				ventanaAgregarEquipo = null;

				try {
					TomarDatosDeTablasBusquedaOrden(ELS);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				agregarListenersVentanaVisualizarEquipos(ventanaVisualizarEquipos);

			}

		}

		else if (this.ventanaAgregarEquipo != null
				&& e.getSource() == this.ventanaAgregarEquipo.getBotonVerificarIngresoAnterior()) {

			ventanaVerificarIngresoAnterior = new VentanaVerificarIngresoAnterior(this);

			ventanaVerificarIngresoAnterior.getComboFiltroELS().addActionListener(this);
			ventanaVerificarIngresoAnterior.getComboSerie().addActionListener(this);
			ventanaVerificarIngresoAnterior.getBtnPorels().addActionListener(this);
			ventanaVerificarIngresoAnterior.getBtnPorSerie().addActionListener(this);
			ventanaVerificarIngresoAnterior.getBtnVerificar().addActionListener(this);
			ventanaVerificarIngresoAnterior.getBtnNO().addActionListener(this);
			ventanaVerificarIngresoAnterior.getBtnSI().addActionListener(this);

			AutoCompleteDecorator.decorate(ventanaVerificarIngresoAnterior.getComboFiltroELS());
			AutoCompleteDecorator.decorate(ventanaVerificarIngresoAnterior.getComboSerie());
			llenarComboELS();
			llenarComboSeries();

			ventanaVerificarIngresoAnterior.getComboFiltroELS().setSelectedIndex(-1);
			ventanaVerificarIngresoAnterior.getComboFiltroELS().setSelectedItem(null);
			ventanaVerificarIngresoAnterior.getComboSerie().setSelectedIndex(-1);
			ventanaVerificarIngresoAnterior.getComboSerie().setSelectedItem(null);

		}

		else if (this.ventanaVerificarIngresoAnterior != null
				&& e.getSource() == this.ventanaVerificarIngresoAnterior.getBtnPorels()) {

			ventanaVerificarIngresoAnterior.getComboFiltroELS().setSelectedIndex(-1);
			ventanaVerificarIngresoAnterior.getComboFiltroELS().setSelectedItem(null);
			ventanaVerificarIngresoAnterior.getComboSerie().setSelectedIndex(-1);
			ventanaVerificarIngresoAnterior.getComboSerie().setSelectedItem(null);

			ventanaVerificarIngresoAnterior.getComboFiltroELS().setVisible(true);
			ventanaVerificarIngresoAnterior.getComboSerie().setVisible(false);

		}

		else if (this.ventanaVerificarIngresoAnterior != null
				&& e.getSource() == this.ventanaVerificarIngresoAnterior.getBtnPorSerie()) {

			ventanaVerificarIngresoAnterior.getComboFiltroELS().setSelectedIndex(-1);
			ventanaVerificarIngresoAnterior.getComboFiltroELS().setSelectedItem(null);
			ventanaVerificarIngresoAnterior.getComboSerie().setSelectedIndex(-1);
			ventanaVerificarIngresoAnterior.getComboSerie().setSelectedItem(null);

			ventanaVerificarIngresoAnterior.getComboFiltroELS().setVisible(false);
			ventanaVerificarIngresoAnterior.getComboSerie().setVisible(true);

		}

		else if (this.ventanaVerificarIngresoAnterior != null
				&& e.getSource() == this.ventanaVerificarIngresoAnterior.getBtnVerificar()) {

			if (ventanaVerificarIngresoAnterior.getComboFiltroELS().getSelectedItem() == null) {

				if (ventanaVerificarIngresoAnterior.getComboSerie().getSelectedItem() == null) {

				}

				else {

					String Serie = (ventanaVerificarIngresoAnterior.getComboSerie().getSelectedItem().toString());
					reparacion = agenda.dameReparacionXserie(Serie);

				}

			} else {

				Integer ELS = Integer
						.parseInt(ventanaVerificarIngresoAnterior.getComboFiltroELS().getSelectedItem().toString());
				reparacion = agenda.dameReparacionXels(ELS);

			}

			if (reparacion == null) {

				Object mje = "No se encontró ningún equipo.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

				ventanaVerificarIngresoAnterior.getTextELS().setText("");
				ventanaVerificarIngresoAnterior.getTextAviso().setText("");
				ventanaVerificarIngresoAnterior.getTextCliente().setText("");
				ventanaVerificarIngresoAnterior.getTextEquipo().setText("");
				ventanaVerificarIngresoAnterior.getTextMarca().setText("");
				ventanaVerificarIngresoAnterior.getTextModelo().setText("");
				ventanaVerificarIngresoAnterior.getTextSerie().setText("");
				ventanaVerificarIngresoAnterior.setFechaFabr2(null);
				ventanaVerificarIngresoAnterior.setFechaIngresoAnterior(null);
				ventanaVerificarIngresoAnterior.getTextPasaron().setText("");
				ventanaVerificarIngresoAnterior.getTextNota().setText("");

				ventanaVerificarIngresoAnterior.getBtnSI().setEnabled(false);
				ventanaVerificarIngresoAnterior.getBtnNO().setEnabled(false);

			} else {

				ventanaVerificarIngresoAnterior.getBtnSI().setEnabled(true);
				ventanaVerificarIngresoAnterior.getBtnNO().setEnabled(true);

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
				ventanaVerificarIngresoAnterior.getTextELS().setText(String.valueOf(reparacion.getELS()));
				ventanaVerificarIngresoAnterior.getTextAviso().setText(reparacion.getAviso());
				ventanaVerificarIngresoAnterior.getTextCliente()
						.setText(reparacion.getCliente() + " - " + reparacion.getSucursal());
				ventanaVerificarIngresoAnterior.getTextEquipo().setText(reparacion.getNombreEquipo());
				ventanaVerificarIngresoAnterior.getTextMarca().setText(reparacion.getMarca());
				ventanaVerificarIngresoAnterior.getTextModelo().setText(reparacion.getModelo());
				ventanaVerificarIngresoAnterior.getTextSerie().setText(reparacion.getNumeroDeSerie());

				if (reparacion.getFechaFabr() == null) {

					ventanaVerificarIngresoAnterior.setFechaFabr2(null);

				} else {

					try {
						ventanaVerificarIngresoAnterior.setFechaFabr2((dateFormat.parse(reparacion.getFechaFabr())));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				if (reparacion.getFecha_Entrada() == null) {

				} else {

					try {
						ventanaVerificarIngresoAnterior
								.setFechaIngresoAnterior((dateFormat.parse(reparacion.getFecha_Entrada())));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				java.util.Date fechaEntrada = this.ventanaVerificarIngresoAnterior.getTextFechaIngreso().getDate();
				fechaentrada = dateFormat2.format(fechaEntrada);
				String requestDate = fechaentrada;
				LocalDate myDate = LocalDate.parse(requestDate);
				LocalDate currentDate = LocalDate.now();
				long numberOFDays = ChronoUnit.DAYS.between(myDate, currentDate);

				ventanaVerificarIngresoAnterior.getTextPasaron().setText(String.valueOf(numberOFDays));

				int dias = Integer.parseInt(ventanaVerificarIngresoAnterior.getTextPasaron().getText());

				if (dias <= 30) {

					ventanaVerificarIngresoAnterior.getTextNota().setText(

							"EL EQUIPO NO DEBERÁ INGRESARSE NUEVAMENTE YA QUE HAN PASADO MENOS DE 30 DÍAS DESDE SU INGRESO ANTERIOR.");

				} else if (dias > 30 && dias <= 90) {

					ventanaVerificarIngresoAnterior.getTextNota()

							.setText("EL EQUIPO SE ENCUENTRA EN PERRÍODO DE GARANTÍA. VERIFICAR SI CORRESPONDE.");

				} else {
					ventanaVerificarIngresoAnterior.getTextNota()
							.setText("EL EQUIPO NO SE ENCUENTRA DENTRO DE LOS 90 DÍAS DE GARANTÍA.");
				}

			}

		}

		else if (this.ventanaVerificarIngresoAnterior != null
				&& e.getSource() == this.ventanaVerificarIngresoAnterior.getBtnSI()) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			ventanaAgregarEquipo.getComboClientes().setEditable(true);
			ventanaAgregarEquipo.getComboSucursal().setEditable(true);
			ventanaAgregarEquipo.getComboNombreEquipo().setEditable(true);
			ventanaAgregarEquipo.getComboSerie().setEditable(true);
			ventanaAgregarEquipo.getComboMarca().setEditable(true);
			ventanaAgregarEquipo.getComboModelo().setEditable(true);
			ventanaAgregarEquipo.getTextFechafabricacion().setEditable(true);
			ventanaAgregarEquipo.getTextFechafabricacion().setEnabled(true);

			ventanaAgregarEquipo.getComboClientes().setSelectedIndex(-1);
			ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(-1);
			ventanaAgregarEquipo.getComboNombreEquipo().setSelectedIndex(-1);
			ventanaAgregarEquipo.getComboSerie().setSelectedIndex(-1);
			ventanaAgregarEquipo.getComboMarca().setSelectedIndex(-1);
			ventanaAgregarEquipo.getComboModelo().setSelectedIndex(-1);

			ventanaAgregarEquipo.getComboNombreEquipo().setSelectedItem(reparacion.getNombreEquipo());
			ventanaAgregarEquipo.getComboClientes().setSelectedItem(reparacion.getCliente());

			if (!reparacion.getMarca().isEmpty()) {
				ventanaAgregarEquipo.getComboMarca().setSelectedItem(reparacion.getMarca());
			}

			if (!reparacion.getModelo().isEmpty()) {
				ventanaAgregarEquipo.getComboModelo().setSelectedItem(reparacion.getModelo());

			}

			ventanaAgregarEquipo.getComboSerie().setSelectedItem(reparacion.getNumeroDeSerie());

			if (!reparacion.getAviso().isEmpty()) {
				ventanaAgregarEquipo.getTextFalla()
						.setText("ELS ANT: " + reparacion.getELS() + " - AVISO ANT: " + reparacion.getAviso());
			} else {
				ventanaAgregarEquipo.getTextFalla().setText("ELS ANT: " + reparacion.getELS());
			}

			if (!reparacion.getSucursal().isEmpty()) {

				if (reparacion.getSucursal()
						.compareTo(ventanaAgregarEquipo.getComboSucursal().getItemAt(0).toString()) == 0) {

					ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(0);
				} else {

					ventanaAgregarEquipo.getComboSucursal().setSelectedItem(reparacion.getSucursal());
				}
			}

			if (reparacion.getFechaFabr() == null) {

				ventanaAgregarEquipo.setTextFechafabricacion2(null);

			} else {

				try {
					ventanaAgregarEquipo.setTextFechafabricacion2((dateFormat.parse(reparacion.getFechaFabr())));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			ventanaVerificarIngresoAnterior.dispose();
			ventanaVerificarIngresoAnterior = null;

			ventanaAgregarEquipo.getComboClientes().setEditable(false);
			ventanaAgregarEquipo.getComboSucursal().setEditable(false);

		}

		else if (this.ventanaVerificarIngresoAnterior != null
				&& e.getSource() == this.ventanaVerificarIngresoAnterior.getBtnNO()) {

			ventanaVerificarIngresoAnterior.dispose();
			ventanaVerificarIngresoAnterior = null;

		}

		else if (this.ventanaAgregarEquipo != null
				&& e.getSource() == this.ventanaAgregarEquipo.getBotonNuevaReparacion()) {

			ELS = DameNumeroELS();

			ventanaAgregarEquipo.setTextELS(Integer.toString(ELS));

			ventanaAgregarEquipo.getTextAvisoCliente().setEditable(true);
			ventanaAgregarEquipo.getTextAvisoCliente().setEnabled(true);

			ventanaAgregarEquipo.getTextClienteCliente().setEditable(true);
			ventanaAgregarEquipo.getTextClienteCliente().setEnabled(true);

			ventanaAgregarEquipo.getTextFalla().setEditable(true);
			ventanaAgregarEquipo.getTextFalla().setEnabled(true);

			ventanaAgregarEquipo.getTextRemitoCliente().setEditable(true);
			ventanaAgregarEquipo.getTextRemitoCliente().setEnabled(true);

			ventanaAgregarEquipo.getComboClientes().setEditable(true);
			ventanaAgregarEquipo.getComboClientes().setEnabled(true);

			ventanaAgregarEquipo.getComboSucursal().setEditable(true);
			ventanaAgregarEquipo.getComboSucursal().setEnabled(true);

			ventanaAgregarEquipo.getComboMarca().setEditable(true);
			ventanaAgregarEquipo.getComboMarca().setEnabled(true);

			ventanaAgregarEquipo.getComboNombreEquipo().setEditable(true);
			ventanaAgregarEquipo.getComboNombreEquipo().setEnabled(true);

			ventanaAgregarEquipo.getComboModelo().setEditable(true);
			ventanaAgregarEquipo.getComboModelo().setEnabled(true);

			ventanaAgregarEquipo.getComboSerie().setEditable(true);
			ventanaAgregarEquipo.getComboSerie().setEnabled(true);

			ventanaAgregarEquipo.getFechaEntrada().setEnabled(true);

			ventanaAgregarEquipo.getTextFechafabricacion().setEditable(true);
			ventanaAgregarEquipo.getTextFechafabricacion().setEnabled(true);

			ventanaAgregarEquipo.getRdbtnBRC().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnCABA().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnMDP().setEnabled(true);
			ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(false);
			ventanaAgregarEquipo.getBtnFechaDefault().setEnabled(true);
			ventanaAgregarEquipo.getBtnGenerarSerie().setEnabled(true);
			ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().setEnabled(true);

			ventanaAgregarEquipo.getTextAvisoCliente().setText("");
			ventanaAgregarEquipo.getTextClienteCliente().setText("");
			ventanaAgregarEquipo.getTextFalla().setText("");
			ventanaAgregarEquipo.getTextRemitoCliente().setText("");
			ventanaAgregarEquipo.getComboClientes().setSelectedIndex(0);
			ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(-1);
			ventanaAgregarEquipo.getComboMarca().setSelectedItem("");
			ventanaAgregarEquipo.getComboNombreEquipo().setSelectedItem("");
			ventanaAgregarEquipo.getComboModelo().setSelectedItem("");
			ventanaAgregarEquipo.getComboSerie().setSelectedItem("");
			ventanaAgregarEquipo.setTextFechafabricacion2(null);
			ventanaAgregarEquipo.getBotonIRaELS().setEnabled(false);
			ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(false);

			ventanaAgregarEquipo.getRdbtnBRC().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnCABA().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnMDP().setEnabled(true);

		}

		else if (this.ventanaAgregarEquipo != null && e.getSource() == this.ventanaAgregarEquipo.getBtnGenerarSerie()) {

			int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
					"¿Desea generar un Número De Serie?", "Confirmación", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				ventanaAgregarEquipo.getComboSerie().setSelectedItem(generateRandomText());

			}

		}

		else if (this.ventanaAgregarEquipo != null && e.getSource() == this.ventanaAgregarEquipo.getBtnFechaDefault()) {

			String testDateString = "00010101";
			DateFormat df = new SimpleDateFormat("yyyyMMdd");

			int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
					"¿Desea Colocar la fecha default 01/01/0001?", "Confirmación", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				try {
					java.util.Date d1 = df.parse(testDateString);
					ventanaAgregarEquipo.setTextFechafabricacion2(d1);

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}

	}

	private void abrirExcel(VentanaVisualizarEquipos ventanaVisualizarEquipos2) {

		ventanaExcel = new VentanaExcel();

		ventanaExcel.getBtnCaja().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				abrirExcelCaja(ventanaVisualizarEquipos);
				ventanaExcel.dispose();
				ventanaExcel = null;
			}

		});

		ventanaExcel.getBtnRepar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				abrirExcelReparaciones(ventanaVisualizarEquipos);
				ventanaExcel.dispose();
				ventanaExcel = null;

			}

		});

		ventanaExcel.getBtnDetalleGastos().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				abrirExcelDetalleGastos(ventanaVisualizarEquipos);
				ventanaExcel.dispose();
				ventanaExcel = null;

			}

		});

		ventanaExcel.getBtnAbrirTodos().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				abrirTodosLosExcels(ventanaVisualizarEquipos);
				ventanaExcel.dispose();
				ventanaExcel = null;

			}

		});

	}

	/**
	 * Abre el Excel de Reparaciones
	 */
	private void abrirExcelReparaciones(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		gestorExcel.setUbicacionBase(agenda.getUbicacionBase());
		gestorExcel.abrirReparaciones();
	}

	/**
	 * Abre el Excel de Caja (con opción de actualizar Reparaciones primero)
	 */
	private void abrirExcelCaja(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		gestorExcel.setUbicacionBase(agenda.getUbicacionBase());
		gestorExcel.abrirCaja();
	}

	/**
	 * Abre el Excel de Detalle de Gastos con selector de año
	 */
	private void abrirExcelDetalleGastos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		gestorExcel.setUbicacionBase(agenda.getUbicacionBase());
		gestorExcel.abrirDetalleGastos(null);
	}

	/**
	 * Abre el Excel de Detalle de Gastos del año actual directamente
	 */
	private void abrirExcelDetalleGastosAnioActual(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		gestorExcel.setUbicacionBase(agenda.getUbicacionBase());
		gestorExcel.abrirDetalleGastosAnioActual();
	}

	/**
	 * NUEVO: Abre todos los archivos Excel en secuencia (modo manual) El usuario
	 * controla cuándo pasar al siguiente archivo
	 */
	private void abrirTodosLosExcels(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		gestorExcel.setUbicacionBase(agenda.getUbicacionBase());
		gestorExcel.abrirTodosLosArchivos();
	}

	/**
	 * NUEVO: Abre todos los archivos Excel automáticamente con pausas Los archivos
	 * se abren con delays automáticos
	 */
	private void abrirTodosLosExcelsAutomatico(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		gestorExcel.setUbicacionBase(agenda.getUbicacionBase());
		gestorExcel.abrirTodosLosArchivosAutomatico();
	}

	// Enum para los tipos de navegación
	private enum TipoNavegacion {
		SIGUIENTE, ANTERIOR, PRIMERO, ULTIMO
	}

	// Método principal que maneja toda la lógica de navegación
	private void procesarNavegacion(TipoNavegacion tipoNavegacion) {
		String ubicacionDeBase = agenda.getUbicacionBase();
		int tam = agenda.obtenerReparacion().size();

		// Guardar cambios si es necesario
		guardarCambiosSiEsNecesario();

		// Procesar según la ubicación
		if (ubicacionDeBase.equals("Bariloche")) {
			procesarNavegacionBariloche(tipoNavegacion, tam);
		} else if (ubicacionDeBase.equals("Buenos Aires")) {
			procesarNavegacionBuenosAires(tipoNavegacion, tam);
		}
	}

	// Método para guardar cambios pendientes
	private void guardarCambiosSiEsNecesario() {
		if (!guardado) {
			ReparacionDTO reparacionAeditar = TomarDatosVisualizacion(ventanaVisualizarEquipos);
			this.agenda.editarReparacionR(reparacionAeditar);
			guardado = true;
		}
	}

	// Método para procesar navegación en Bariloche
	private void procesarNavegacionBariloche(TipoNavegacion tipoNavegacion, int tam) {
		boolean actualizarTablas = true;

		switch (tipoNavegacion) {
		case SIGUIENTE:
			if (ELSinicial < tam + 987) {
				ELSinicial = ELSinicial + 1;
			} else {
				mostrarMensajeNoMasReparaciones();
				actualizarTablas = false;
			}
			break;

		case ANTERIOR:
			if (ELSinicial > 988) {
				ELSinicial = ELSinicial - 1;
			} else {
				actualizarTablas = false;
			}
			break;

		case PRIMERO:
			ELSinicial = 988;
			break;

		case ULTIMO:
			ELSinicial = tam + 987;
			break;
		}

		if (actualizarTablas) {
			actualizarTablas();
		}
	}

	// Método para procesar navegación en Buenos Aires
	private void procesarNavegacionBuenosAires(TipoNavegacion tipoNavegacion, int tam) {
		boolean actualizarTablas = true;

		switch (tipoNavegacion) {
		case SIGUIENTE:
			if (ELSinicialBSAS < tam + 24899) {
				ELSinicialBSAS = ELSinicialBSAS + 1;
			} else {
				mostrarMensajeNoMasReparaciones();
				actualizarTablas = false;
			}
			break;

		case ANTERIOR:
			if (ELSinicialBSAS > 24900) {
				ELSinicialBSAS = ELSinicialBSAS - 1;
			} else {
				actualizarTablas = false;
			}
			break;

		case PRIMERO:
			ELSinicialBSAS = 24900;
			break;

		case ULTIMO:
			ELSinicialBSAS = tam + 24899;
			break;
		}

		if (actualizarTablas) {
			actualizarTablas();
		}
	}

	// Método para mostrar mensaje informativo
	private void mostrarMensajeNoMasReparaciones() {
		JOptionPane.showMessageDialog(null, "No hay más reparaciones", "Mensaje Informativo",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// Método para actualizar tablas con manejo de excepciones
	private void actualizarTablas() {
		try {
			TomarDatosDeTablas(ventanaVisualizarEquipos);
		} catch (ParseException e1) {
			e1.printStackTrace();
			// Aquí podrías agregar un logger más sofisticado
			// logger.error("Error al actualizar tablas", e1);
		}
	}

	private void facturar(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		String clienteEquipo = ventanaVisualizarEquipos.getTextCliente().getText();
		int idCliente = reparacion.getIDCliente();
		String cuitCliente = agenda.dameCuitPorIdCliente(idCliente);

		String nombreEquipo = ventanaVisualizarEquipos.getTextNombreEquipo().getText();
		String marcaEquipo = ventanaVisualizarEquipos.getTextMarca().getText();
		String modeloEquipo = ventanaVisualizarEquipos.getTextModelo().getText();
		String serieEquipo = ventanaVisualizarEquipos.getTextNSerie().getText();
		String elsEquipo = ventanaVisualizarEquipos.getTextELS().toString();
		String Presupuesto = ventanaVisualizarEquipos.getTextPresupuesto().getText();

		double presupuestoFactura;

		if (monedaFormatter.tieneFormato(Presupuesto)) {

			presupuestoFactura = monedaFormatter
					.parseAmountGuardar(ventanaVisualizarEquipos.getTextPresupuesto().getText());

		} else {

			presupuestoFactura = monedaFormatter.parseAmount(ventanaVisualizarEquipos.getTextPresupuesto().getText());

		}

		DecimalFormat df = new DecimalFormat("#");
		Presupuesto = df.format(presupuestoFactura);

		String ItemFactura = "Reparación de" + " " + nombreEquipo + " " + marcaEquipo + " " + modeloEquipo + " s/n: "
				+ serieEquipo + " ELS: " + elsEquipo;

		ventanaDatosFacturacion = new VentanaDatosFacturacion(clienteEquipo, cuitCliente, ItemFactura, Presupuesto);
		ventanaDatosFacturacion.setVisible(true);

		int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos, " Ir a la Página de ARCA?",
				"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (seleccion == JOptionPane.YES_OPTION) {

			try {
				// Verificar si Desktop está soportado (Windows, macOS, Linux)
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					if (desktop.isSupported(Desktop.Action.BROWSE)) {
						// Abrir la URL en el navegador predeterminado
						desktop.browse(new URI("https://www.arca.gob.ar/landing/default.asp"));
					}
				} else {
					JOptionPane.showMessageDialog(null, "No se puede abrir el navegador automáticamente.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al abrir la URL: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	private void realizarBusqueda() {
		// Limpiar el área de texto
		ventanaBusquedaEquipo.getTextPane().setText("");

		/* "Falla", "Diagnóstico", "Informe Cliente"}; */

		// Obtener el texto ingresado para buscar
		String campoBusqueda = ventanaBusquedaEquipo.getComboBuscador().getSelectedItem().toString();

		switch (campoBusqueda) {
		case "Falla":
			campoBusqueda = "Falla";
			break;
		case "Diagnóstico":
			campoBusqueda = "Solucion";
			break;
		case "Informe Cliente":
			campoBusqueda = "Informecliente";
			break;

		default:
			break;
		}

		String textoBusqueda = ventanaBusquedaEquipo.getTextField().getText();

		// Simulación de resultados filtrados
		List<Integer> resultadosFiltrados = new ArrayList<>();

		resultadosFiltrados = agenda.buscarEnCampos(campoBusqueda, textoBusqueda);

		// Mostrar resultados en el JTextPane con formato
		StyledDocument doc = ventanaBusquedaEquipo.textPane.getStyledDocument();
		Style style = ventanaBusquedaEquipo.textPane.addStyle("", null);

		for (Integer resultado : resultadosFiltrados) {
			try {
				StyleConstants.setForeground(style, Color.BLUE);
				StyleConstants.setBold(style, true);
				doc.insertString(doc.getLength(), resultado + "\n", style);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void actualizarCursor(MouseEvent e) {
		Point pt = e.getPoint();
		int pos = ventanaBusquedaEquipo.textPane.viewToModel(pt);
		StyledDocument doc = ventanaBusquedaEquipo.textPane.getStyledDocument();
		Element elem = doc.getCharacterElement(pos);
		AttributeSet as = elem.getAttributes();

		if (StyleConstants.isBold(as) && StyleConstants.getForeground(as).equals(Color.BLUE)) {
			ventanaBusquedaEquipo.textPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			ventanaBusquedaEquipo.textPane.setCursor(Cursor.getDefaultCursor());
		}
	}

	private void manejarClickEnTexto(MouseEvent e) {
		Point pt = e.getPoint();
		int pos = ventanaBusquedaEquipo.textPane.viewToModel(pt);
		StyledDocument doc = ventanaBusquedaEquipo.textPane.getStyledDocument();
		Element elem = doc.getCharacterElement(pos);
		AttributeSet as = elem.getAttributes();

		if (StyleConstants.isBold(as) && StyleConstants.getForeground(as).equals(Color.BLUE)) {
			try {
				int start = elem.getStartOffset();
				int end = elem.getEndOffset();
				String numeroELS = doc.getText(start, end - start).trim();
				Integer ELSaBuscar = Integer.parseInt(numeroELS);

				try {

					TomarDatosDeTablasBusquedaOrden(ELSaBuscar);
				} catch (ParseException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
				agregarListenersVentanaVisualizarEquipos(ventanaVisualizarEquipos);

			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void abrirVentanaDetalle(String numeroELS) {
		JOptionPane.showMessageDialog(ventanaBusquedaEquipo, "Se seleccionó el número ELS: " + numeroELS, "Detalle ELS",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void enviarRespuestaAlTecnico(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		String correo = reparacion.getCorreo();
		enviarAviso(ventanaVisualizarEquipos, correo, "Desea enviar el aviso de 'Respuesta del Cliente'",
				"RESPUESTA_CLIENTE");
	}

	private void enviarAvisoEquipoListo(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		String correo = "diego.bertossi@elsweb.com.ar";
		enviarAviso(ventanaVisualizarEquipos, correo, "¿Desea enviar el aviso de 'Equipo Terminado'", "EQUIPO_LISTO");
	}

	private void enviarAvisoInforme(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		String correo = "diego.bertossi@elsweb.com.ar";
		enviarAviso(ventanaVisualizarEquipos, correo, "¿Desea enviar el aviso", "INFORME");
	}

	private void enviarAviso(VentanaVisualizarEquipos ventanaVisualizarEquipos, String correo,
			String mensajeConfirmacion, String tipoAviso) {

		String ELS = ventanaVisualizarEquipos.getTextELS().toString();
		String Cliente = ventanaVisualizarEquipos.getTextCliente().getText();
		String Sucursal = ventanaVisualizarEquipos.getTextSucursal().getText();

		int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
				mensajeConfirmacion + " a " + correo + " ?", "Confirmación", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (seleccion == JOptionPane.YES_OPTION) {

			JDialog popup = new JDialog();
			popup.setTitle("Procesando");
			popup.setModal(false);
			popup.setSize(300, 100);
			popup.setLocationRelativeTo(ventanaVisualizarEquipos);
			popup.add(new JLabel("Enviando correo, espere...", SwingConstants.CENTER));

			// Ejecutar el envío del correo en un hilo separado para no bloquear el UI
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() {
					try {

						switch (tipoAviso) {
						case "RESPUESTA_CLIENTE":
							String EstadoComercial = reparacion.getEstadoComercial();
							mails.EnviarMail.enviarAvisoRespuestaCliente(correo, ELS, Cliente, Sucursal,
									EstadoComercial);
							break;
						case "EQUIPO_LISTO":
							mails.EnviarMail.enviarAvisoEquipoTerminado(correo, ELS, Cliente, Sucursal);
							break;
						case "INFORME":
							mails.EnviarMail.enviarAvisoInforme(correo, ELS, Cliente, Sucursal);
							ventanaVisualizarEquipos.setChckbxAvisoEnviado(true);
							ReparacionDTO reparacionAeditar = TomarDatosVisualizacion(ventanaVisualizarEquipos);
							agenda.editarReparacionR(reparacionAeditar);
							break;
						default:
							throw new IllegalArgumentException("Tipo de aviso no reconocido: " + tipoAviso);
						}

					} catch (Exception ex) {
						popup.dispose();
						ex.printStackTrace();
						// JOptionPane.showMessageDialog(null, "El correo NO ha sido enviado.", "Error
						// de envío", JOptionPane.WARNING_MESSAGE);
					}
					return null;
				}

				@Override
				protected void done() {
					// Cerrar el popup después de completar el envío
					popup.dispose();

				}
			};

			// Mostrar el popup y ejecutar el SwingWorker
			SwingUtilities.invokeLater(() -> {
				popup.setVisible(true);
				worker.execute();
			});

		}
	}

	private void eliminarRepuesto(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		if (repuestoElegido == null) {
			ventanaVisualizarEquipos.getErrorMsj("Seleccione un Repuesto");

		}

		else {
			int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
					"¿Está seguro de realizar la operación?", "Confirmación", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				int[] filas_seleccionadas = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRows();
				for (int fila : filas_seleccionadas) {
					agenda.borraRepuesto(Repuestos_en_tabla.get(fila));
				}

				llenarTablaRepuestos(ventanaVisualizarEquipos);
				repuestoElegido = null;

			}
		}

	}

	private void guardarEditarRepuesto(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		if (repuestoElegido == null) {
			ventanaVisualizarEquipos.getErrorMsj("Seleccione un Repuesto");

		} else {

			int i = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
			if (i != -1) {
				if (!Repuestos_en_tabla.isEmpty()) {
					repuestoElegido = Repuestos_en_tabla.get(i);

					int idreemplazo = repuestoElegido.getIdRepuesto();
					int ELS = repuestoElegido.getELS();
					String referencia = String.valueOf(ventanaVisualizarEquipos.getTablaRepuestos().getModel()
							.getValueAt(ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow(), 0));
					String original = String.valueOf(ventanaVisualizarEquipos.getTablaRepuestos().getModel()
							.getValueAt(ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow(), 1));
					String reemplazo = String.valueOf(ventanaVisualizarEquipos.getTablaRepuestos().getModel()
							.getValueAt(ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow(), 2));
					String nota = String.valueOf(ventanaVisualizarEquipos.getTablaRepuestos().getModel()
							.getValueAt(ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow(), 3));

					repuestoElegido.setRef(referencia);
					repuestoElegido.setOriginal(original);
					repuestoElegido.setReemplazo(reemplazo);
					repuestoElegido.setNotas(nota);

				}
			}
			ventanaVisualizarEquipos.getBtnEditarRepuesto().setEnabled(false);

			agenda.editarRepuesto(repuestoElegido);

			llenarTablaRepuestos(ventanaVisualizarEquipos);
			repuestoElegido = null;

		}

	}

	private void AgregarRepuesto(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		RepuestosDTO nuevoRepuesto = TomarDatosRepuesto(ventanaVisualizarEquipos);

		// Para mostrar el mensaje en cualquier parte de la clase:
		if (!caracteresNoValidosEncontrados.isEmpty()) {
			String mensaje = "Caracteres no válidos encontrados: " + String.join(", ", caracteresNoValidosEncontrados);
			JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
		}

		else {
			this.agenda.agregarRepuesto(nuevoRepuesto);
		}

		this.ventanaagregarRepuesto.dispose();
		this.ventanaagregarRepuesto = null;

		llenarTablaRepuestos(ventanaVisualizarEquipos);

	}

	private void abrirVentanaRepuestos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ventanaagregarRepuesto = new VentanaAgregarRepuesto(this);
		this.ventanaagregarRepuesto.getBtnAgregarRepuesto().addActionListener(this);
		this.ventanaagregarRepuesto.getBtnCancelar().addActionListener(this);

		performActionOnTextComponents(ventanaagregarRepuesto);
	}

	private void aceptarEdicionEstados(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		// asda

		String estadoFisico = "";
		String estadoTecnico = "";
		String estadoComercial = "";
		String lugarDeIngreso = "";

		Boolean cambioDeEstadoBoolean = false;

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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (ventanaVisualizarEquipos.getTextEstadoFisico().getText().compareTo(estadoFisico) != 0) {

			cambioDeEstadoBoolean = true;

			ventanaVisualizarEquipos.setTextEstadoFisico(estadoFisico);

			if (estadoFisico == "Enviado") {

				ventanaVisualizarEquipos.getFechaSalida().setDate(fechaParseadaHOY);

			}

		}

		if (ventanaVisualizarEquipos.getTextEstadoTecnico().getText().compareTo(estadoTecnico) != 0) {

			cambioDeEstadoBoolean = true;

			ventanaVisualizarEquipos.setTextEstadoTecnico(estadoTecnico);

			if (estadoTecnico == "Sin Revisar") {

				ventanaVisualizarEquipos.getFechaReparacion().setDate(null);

			}

			else {

				ventanaVisualizarEquipos.getFechaReparacion().setDate(fechaParseadaHOY);

			}

		}

		if (ventanaVisualizarEquipos.getTextEstadoComercial().getText().compareTo(estadoComercial) != 0) {

			cambioDeEstadoBoolean = true;

			ventanaVisualizarEquipos.setTextEstadoComercial(estadoComercial);

			if (estadoComercial == "A la Espera de Aceptación") {

				ventanaVisualizarEquipos.getFechaRespuesta().setDate(null);

			}

			else {

				ventanaVisualizarEquipos.getFechaRespuesta().setDate(fechaParseadaHOY);

			}

		}

		if (ventanaVisualizarEquipos.getTextLugarDeIngreso().getText().compareTo(lugarDeIngreso) != 0) {

			cambioDeEstadoBoolean = true;

			ventanaVisualizarEquipos.setTextLugarDeIngreso(lugarDeIngreso);

		}

		this.ventanaEstados.dispose();
		this.ventanaEstados = null;

	}

	private void abrirVentanaWsp(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ventanaWSP = new VentanaWSP(this);

		String cliente = ventanaVisualizarEquipos.getTextCliente().getText();

		String NombreContacto = this.agenda.ContactoPorCliente(cliente);
		String TelefonoContacto = this.agenda.obtenerTelefonoPorCliente(cliente);

		ventanaWSP.getTextNombreContacto().setText(NombreContacto);
		ventanaWSP.getTextNumeroContacto().setText(TelefonoContacto);

		ventanaWSP.getTextCliente().setText(cliente);
		ventanaWSP.getBtnEnviar().addActionListener(this);
		ventanaWSP.getBtnEditarNmero().addActionListener(this);
		ventanaWSP.getBtnClientes().addActionListener(this);
		ventanaWSP.getBtnUtilizarContactoBuscado().addActionListener(this);
		ventanaWSP.getBtnUtilizarContacto().addActionListener(this);
		ventanaWSP.getComboOrganizacion().addActionListener(this);
		ventanaWSP.getComboNombreBuscado().addActionListener(this);

		llenarComboOrganizacion();
		llenarComboNombreWSP();

		performActionOnTextComponents(ventanaWSP);

	}

	private void refrescarPantalla(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		try {
			TomarDatosDeTablas(ventanaVisualizarEquipos);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}

	private void refrescarPantallaListados(int ELS, VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		try {
			ActualizarDatosDeTablasListado(Integer.parseInt(ventanaVisualizarEquipos.getTextELS()),
					ventanaVisualizarEquipos);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}

	private void presupuestar(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		if (ventanaVisualizarEquipos.getBtnGuardarCambios().isEnabled()) {

			Object mje = "Debe guardar los cambios realizados para poder presupuestar.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

		} else {

			NumeroELS = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());

			ventanaGenerarPresupuesto = controladorpresupuestos.TomarDatosDeTablasParaVisualizacion(NumeroELS);
			controladorpresupuestos.agregarListenersVentanaGenerarPresupuesto();

			ventanaGenerarPresupuesto.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {

					if (actualizarEnlistado) {
						refrescarPantallaListados(Integer.parseInt(ventanaVisualizarEquipos.getTextELS()),
								ventanaVisualizarEquipos);

					} else {

						refrescarPantalla(ventanaVisualizarEquipos);
					}

				}
			});

		}

	}

	private VentanaEstados editarEstados(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ventanaEstados = new VentanaEstados(this);

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

	private void listenerVentanaEstados(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ventanaEstados.getBtnAceptarEdicion().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				aceptarEdicionEstados(ventanaVisualizarEquipos);

			}
		});

	}

	private void guardarCambios(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ReparacionDTO reparacionAeditar = TomarDatosVisualizacion(ventanaVisualizarEquipos);

		// Para mostrar el mensaje en cualquier parte de la clase:
		if (!caracteresNoValidosEncontrados.isEmpty()) {
			String mensaje = "Caracteres no válidos encontrados: " + String.join(", ", caracteresNoValidosEncontrados);
			JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
		}

		else {
			this.agenda.editarReparacionR(reparacionAeditar);

			guardado = true;

			deshabilitarCampos(ventanaVisualizarEquipos);
		}

	}

	private void editar(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		llenarComboClienteV(ventanaVisualizarEquipos);
		llenarComboTecnico(ventanaVisualizarEquipos);
		habilitarCampos(ventanaVisualizarEquipos);
		guardado = false;
	}

	public void agregarListenersVentanaVisualizarEquipos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		// System.out.println("listeners agregados en ventana "+
		// ventanaVisualizarEquipos.getTitle());

		ventanaVisualizarEquipos.getBotonAnterior().addActionListener(this);
		ventanaVisualizarEquipos.getBotonSiguiente().addActionListener(this);
		ventanaVisualizarEquipos.getBotonUltimo().addActionListener(this);
		ventanaVisualizarEquipos.getBotonPrimero().addActionListener(this);
		ventanaVisualizarEquipos.getBtnGuardarCambios().addActionListener(this);
		ventanaVisualizarEquipos.getBotonRegistroIngreso().addActionListener(this);
		ventanaVisualizarEquipos.getBotonEditarEstados().addActionListener(this);
		ventanaVisualizarEquipos.getBtnEditar().addActionListener(this);
		ventanaVisualizarEquipos.getBotonAvisoInforme().addActionListener(this);
		ventanaVisualizarEquipos.getBotonAvisoEquipoListo().addActionListener(this);
		ventanaVisualizarEquipos.getBotonRespuestaAlTecnico().addActionListener(this);
		ventanaVisualizarEquipos.getBtnGenerarRemito().addActionListener(this);
		ventanaVisualizarEquipos.getBotonPresupuestar().addActionListener(this);
		ventanaVisualizarEquipos.getBtnfacturar().addActionListener(this);
		ventanaVisualizarEquipos.getBtnabrirExcel().addActionListener(this);
		ventanaVisualizarEquipos.getBtnenviarCorreoOwsp().addActionListener(this);
		ventanaVisualizarEquipos.getComboClientes().addActionListener(this);
		ventanaVisualizarEquipos.getComboSucursal().addActionListener(this);
		ventanaVisualizarEquipos.getComboTecnico().addActionListener(this);
		ventanaVisualizarEquipos.getBtnRepuestos().addActionListener(this);
		ventanaVisualizarEquipos.getBtnEditarRepuesto().addActionListener(this);
		ventanaVisualizarEquipos.getBtnEliminarRepuesto().addActionListener(this);
		ventanaVisualizarEquipos.getTablaRepuestos().addMouseListener(this);
		ventanaVisualizarEquipos.getTablaRepuestos().addKeyListener(this);

		ventanaVisualizarEquipos.getTextPresupuesto().addKeyListener(this);
		ventanaVisualizarEquipos.getTextPresupuestoDolar().addKeyListener(this);

		ventanaVisualizarEquipos.getBtnBuscarELS().addActionListener(this);

		ventanaVisualizarEquipos.getComboELS().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// Simula el click del botón BuscarELS
					ventanaVisualizarEquipos.getBtnBuscarELS().doClick();
				}
			}
		});

		if (ventanaBusquedaEquipo == null) {
			ventanaVisualizarEquipos.getBtnBuscar().addActionListener(this);
		} else {
			ventanaVisualizarEquipos.getBtnBuscar().removeActionListener(this);
		}
		ventanaVisualizarEquipos.getComboELS().addActionListener(this);

		llenarComboELSvisualizacion();
		AutoCompleteDecorator.decorate(ventanaVisualizarEquipos.getComboELS());

		listenerPrecios(ventanaVisualizarEquipos);

	}

	private void listenerPrecios(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		ventanaVisualizarEquipos.getTextPresupuesto().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				SwingUtilities.invokeLater(() -> {
					ventanaVisualizarEquipos.getTextPresupuesto().selectAll();
				});
			}
		});

		ventanaVisualizarEquipos.getTextPresupuestoDolar().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				SwingUtilities.invokeLater(() -> {
					ventanaVisualizarEquipos.getTextPresupuestoDolar().selectAll();
				});
			}
		});

		ventanaVisualizarEquipos.getTextPago().addKeyListener(this);
		ventanaVisualizarEquipos.getTextPago().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {

				SwingUtilities.invokeLater(() -> {
					ventanaVisualizarEquipos.getTextPago().selectAll();
				});

			}
		});

		ventanaVisualizarEquipos.getTextPresupuesto().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String presupuesto = ventanaVisualizarEquipos.getTextPresupuesto().getText();
				ventanaVisualizarEquipos.getTextPresupuesto().setText(monedaFormatter.formatPeso(presupuesto));
				verificarPresupuestoEditado(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getTextPago().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String pago = ventanaVisualizarEquipos.getTextPago().getText();
				ventanaVisualizarEquipos.getTextPago().setText(monedaFormatter.formatPeso(pago));
				verificarPresupuestoEditado(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getTextPresupuestoDolar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String presupuestoDolar = ventanaVisualizarEquipos.getTextPresupuestoDolar().getText();
				ventanaVisualizarEquipos.getTextPresupuestoDolar()
						.setText(monedaFormatter.formatDolar(presupuestoDolar));
				verificarPresupuestoEditado(ventanaVisualizarEquipos);

			}
		});

		performActionOnTextComponents(ventanaVisualizarEquipos);

		FocusListener cursorAlInicioTF = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (e.getComponent() instanceof JTextField) {
					JTextField tf = (JTextField) e.getComponent();
					tf.setCaretPosition(0);
				}
			}
		};

		// Aplica a los JTextField relevantes
		ventanaVisualizarEquipos.getTextCliente().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextSucursal().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextNombreEquipo().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextMarca().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextModelo().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextNSerie().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextPresupuesto().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextPresupuestoDolar().addFocusListener(cursorAlInicioTF);
		ventanaVisualizarEquipos.getTextPago().addFocusListener(cursorAlInicioTF);

// Java
		FocusListener cursorAlInicioTA = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (e.getComponent() instanceof javax.swing.JTextArea) {
					javax.swing.JTextArea ta = (javax.swing.JTextArea) e.getComponent();
					ta.setCaretPosition(0);
				}
			}
		};

// Aplica a los JTextArea relevantes
		ventanaVisualizarEquipos.getTextFalla().addFocusListener(cursorAlInicioTA);
		ventanaVisualizarEquipos.getTextDiagnostico().addFocusListener(cursorAlInicioTA);
		ventanaVisualizarEquipos.getTextInformeCliente().addFocusListener(cursorAlInicioTA);

	}

	public void quitarListenersVentanaVisualizarEquipos() {

		this.ventanaVisualizarEquipos.getBotonAnterior().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonSiguiente().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonUltimo().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonPrimero().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBtnGuardarCambios().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonRegistroIngreso().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonEditarEstados().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBtnEditar().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonAvisoInforme().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonAvisoEquipoListo().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonRespuestaAlTecnico().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBtnGenerarRemito().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonPresupuestar().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBtnenviarCorreoOwsp().removeActionListener(this);
		this.ventanaVisualizarEquipos.getComboClientes().removeActionListener(this);
		this.ventanaVisualizarEquipos.getComboSucursal().removeActionListener(this);
		this.ventanaVisualizarEquipos.getComboTecnico().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBtnRepuestos().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBtnEditarRepuesto().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBtnEliminarRepuesto().removeActionListener(this);
		this.ventanaVisualizarEquipos.getTablaRepuestos().removeMouseListener(this);
		this.ventanaVisualizarEquipos.getTablaRepuestos().removeKeyListener(this);

		this.ventanaVisualizarEquipos.getTextPresupuesto().removeKeyListener(this);
		this.ventanaVisualizarEquipos.getTextPresupuestoDolar().removeKeyListener(this);
		this.ventanaVisualizarEquipos.getBtnBuscarELS().removeActionListener(this);
		this.ventanaVisualizarEquipos.getComboELS().removeActionListener(this);
//		llenarComboELSvisualizacion();
//		AutoCompleteDecorator.decorate(ventanaVisualizarEquipos.getComboELS());

		this.ventanaVisualizarEquipos.getTextPresupuesto().removeFocusListener(null);

		this.ventanaVisualizarEquipos.getTextPresupuestoDolar().removeFocusListener(null);

		this.ventanaVisualizarEquipos.getTextPago().removeKeyListener(this);
		this.ventanaVisualizarEquipos.getTextPago().removeFocusListener(null);

		ventanaVisualizarEquipos.getTextPresupuesto().removeActionListener(this);

		ventanaVisualizarEquipos.getTextPago().removeActionListener(this);

		ventanaVisualizarEquipos.getTextPresupuestoDolar().removeActionListener(this);

		// performActionOnTextComponents(ventanaVisualizarEquipos);

	}

	// Método para realizar una acción sobre todos los JTextField, JTextArea y
	// JComboBox en un JFrame
	private void performActionOnTextComponents(JFrame frame) {
		List<Component> textAndComboComponents = getAllTextAndComboComponents(frame);
		// Realiza la acción deseada sobre cada JTextComponent y JComboBox
		for (Component component : textAndComboComponents) {
			if (component instanceof JTextComponent) {
				configureUndoManager((JTextComponent) component);
			} else if (component instanceof JComboBox) {
				// Aquí puedes realizar alguna acción con el JComboBox
				// configureComboBox((JComboBox<?>) component);
			}
		}
	}

	// Método para obtener todos los JTextField, JTextArea y JComboBox en un JFrame
	private List<Component> getAllTextAndComboComponents(Container container) {
		List<Component> componentsList = new ArrayList<>();
		Component[] components = container.getComponents();
		// Itera sobre los componentes y filtra los JTextField, JTextArea y JComboBox
		for (Component component : components) {
			if (component instanceof JTextComponent) {
				componentsList.add((JTextComponent) component);
			} else if (component instanceof JComboBox) {
				componentsList.add((JComboBox<?>) component);
			} else if (component instanceof Container) {
				componentsList.addAll(getAllTextAndComboComponents((Container) component));
			}
		}
		return componentsList;
	}

	public void agregarListenersVentanaVisualizarEquiposListado(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		actualizarEnlistado = true;

		ventanaVisualizarEquipos.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				editar(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBtnGuardarCambios().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				guardarCambios(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBotonPresupuestar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				presupuestar(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBtnabrirExcel().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				abrirExcel(ventanaVisualizarEquipos);

			}
		});

		listenerPrecios(ventanaVisualizarEquipos);

		ventanaVisualizarEquipos.getBtnenviarCorreoOwsp().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				enviarCorreoOwsp(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBotonEditarEstados().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				editarEstados(ventanaVisualizarEquipos);
				listenerVentanaEstados(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBtnGenerarRemito().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				generarRemito(ventanaVisualizarEquipos);
			}

		});

		ventanaVisualizarEquipos.getBtnRepuestos().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				abrirVentanaRepuestos(ventanaVisualizarEquipos);
				listenerRepuestos(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBtnEditarRepuesto().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				guardarEditarRepuesto(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBtnEliminarRepuesto().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				eliminarRepuesto(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBotonAvisoInforme().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				enviarAvisoInforme(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBotonAvisoEquipoListo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				enviarAvisoEquipoListo(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getBotonRespuestaAlTecnico().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				enviarRespuestaAlTecnico(ventanaVisualizarEquipos);

			}
		});

		ventanaVisualizarEquipos.getTablaRepuestos().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				repuestoSeleccionado(ventanaVisualizarEquipos, e);

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		ventanaVisualizarEquipos.getTablaRepuestos().addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {

				habilitarEdicionRepuestos(ventanaVisualizarEquipos, e);

			}

		});

	}

	private void listenerRepuestos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ventanaagregarRepuesto.getBtnAgregarRepuesto().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				AgregarRepuesto(ventanaVisualizarEquipos);

			}
		});

		ventanaagregarRepuesto.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ventanaagregarRepuesto.dispose();
				ventanaagregarRepuesto = null;
			}
		});

	}

	public void generarRemito(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		if (ventanaVisualizarEquipos.getTextNumeroRemito().getText().compareTo("") == 0) {

			if (ventanaVisualizarEquipos.getBtnGuardarCambios().isEnabled()) {

				Object mje = "Debe guardar los cambios realizados para poder presupuestar.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else {

				NumeroELSParaRemito = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());

				ventanaRemitos = controladorSalidas.cargarRemitoVisualizacion(NumeroELSParaRemito);
				controladorSalidas.agregarListenersVentanaRemitos();

				ventanaRemitos.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						if (actualizarEnlistado) {
							refrescarPantallaListados(Integer.parseInt(ventanaVisualizarEquipos.getTextELS()),
									ventanaVisualizarEquipos);

						} else {

							refrescarPantalla(ventanaVisualizarEquipos);
						}
					}
				});
			}

		} else {
			Object mje = "Este equipo ya posee remito. Deberá ANULARLO o ELIMINARLO para generar una nuevo.";
			JOptionPane.showMessageDialog(null, mje, "Remito existente", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	protected void enviarCorreoOwsp(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ventanaEnviarCorreoOwsp = new VentanaEnviarCorreoOwsp(this);

		ventanaEnviarCorreoOwsp.getBtnEnviarWST().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				abrirVentanaWsp(ventanaVisualizarEquipos);

			}
		});

	}

	public void agregarListenersVentanaAgregarEquipos() {

		ventanaAgregarEquipo.setTextELS(Integer.toString(ELS));
		ventanaAgregarEquipo.getComboClientes().addActionListener(this);
		ventanaAgregarEquipo.getComboClientes().setName("comboCliente");
		ventanaAgregarEquipo.getComboSucursal().addActionListener(this);
		ventanaAgregarEquipo.getComboSucursal().setName("comboSucursal");
		ventanaAgregarEquipo.getComboMarca().addActionListener(this);
		ventanaAgregarEquipo.getComboMarca().setName("comboMarca");
		ventanaAgregarEquipo.getComboNombreEquipo().addActionListener(this);
		ventanaAgregarEquipo.getComboNombreEquipo().setName("comboNombreEquipo");
		ventanaAgregarEquipo.getComboModelo().addActionListener(this);
		ventanaAgregarEquipo.getComboModelo().setName("comboModelo");
		ventanaAgregarEquipo.getComboSerie().addActionListener(this);
		ventanaAgregarEquipo.getComboSerie().setName("comboSerie");
		ventanaAgregarEquipo.getBotonGuardar().addActionListener(this);
		ventanaAgregarEquipo.getBotonGenerarRegistro().addActionListener(this);
		ventanaAgregarEquipo.getBotonNuevaReparacion().addActionListener(this);
		ventanaAgregarEquipo.getBtnFechaDefault().addActionListener(this);
		ventanaAgregarEquipo.getBtnGenerarSerie().addActionListener(this);
		ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(false);
		ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().addActionListener(this);
		ventanaAgregarEquipo.getBtnaltaCliente().addActionListener(this);
		ventanaAgregarEquipo.getBotonIRaELS().addActionListener(this);

		llenarComboCliente();
		llenarComboSucursal();
		llenarComboNombreEquipo();
		llenarComboMarca();
		llenarComboModelo();

		VistaPropias.AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboClientes(), false, true);
		VistaPropias.AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboSucursal(), false, true);
		VistaPropias.AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboNombreEquipo(), true, false);
		VistaPropias.AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboMarca(), true, false);
		VistaPropias.AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboModelo(), true, false);
		VistaPropias.AutoCompletarComboBox.enable(ventanaAgregarEquipo.getComboSerie(), true, false);

		performActionOnTextComponents(ventanaAgregarEquipo);

		habilitarMenuContextual(ventanaAgregarEquipo.getComboNombreEquipo());
		habilitarMenuContextual(ventanaAgregarEquipo.getComboMarca());
		habilitarMenuContextual(ventanaAgregarEquipo.getComboModelo());
		habilitarMenuContextual(ventanaAgregarEquipo.getComboSerie());
		habilitarMenuContextual(ventanaAgregarEquipo.getTextFalla());
		habilitarMenuContextual(ventanaAgregarEquipo.getTextRemitoCliente());
		habilitarMenuContextual(ventanaAgregarEquipo.getTextClienteCliente());
		habilitarMenuContextual(ventanaAgregarEquipo.getTextAvisoCliente());

		// Java

		// Java
		FocusListener cursorAlInicio = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (e.getComponent() instanceof JTextField) {
					JTextField tf = (JTextField) e.getComponent();
					tf.setCaretPosition(0);
				}
			}
		};

		ventanaAgregarEquipo.getTextAvisoCliente().addFocusListener(cursorAlInicio);
		ventanaAgregarEquipo.getTextClienteCliente().addFocusListener(cursorAlInicio);
		ventanaAgregarEquipo.getTextRemitoCliente().addFocusListener(cursorAlInicio);
		ventanaAgregarEquipo.getTextFalla().addFocusListener(cursorAlInicio);

	}

	private void TomarDatosDeTablas(VentanaVisualizarEquipos ventanaVisualizarEquipos) throws ParseException {
		// Configuración inicial específica para esta función
		if (agenda.getUbicacionBase().compareTo("Bariloche") == 0) {
			ventanaVisualizarEquipos.setTextELS(Integer.toString(ELSinicial));

		} else if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0) {
			ventanaVisualizarEquipos.setTextELS(Integer.toString(ELSinicialBSAS));

		}

		int NumeroELS = Integer.parseInt(ventanaVisualizarEquipos.getTextELS().toString());
		cargarDatosComunes(ventanaVisualizarEquipos, NumeroELS);
	}

	private void TomarDatosDeTablasBusquedaOrden(int numeroELSSeleccionado) throws ParseException {
		// Configuración inicial específica para esta función

		if (ventanaVisualizarEquipos != null) {
			ventanaVisualizarEquipos.dispose();
		}

		ventanaVisualizarEquipos = new VentanaVisualizarEquipos(this);
		cerraVentanaVisualizarEquipo();

		controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
		SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());
		monedaFormatter = new MonedaFormatter();

		ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELSSeleccionado));
		cargarDatosComunes(ventanaVisualizarEquipos, numeroELSSeleccionado);

		if (agenda.getUbicacionBase().compareTo("Bariloche") == 0)
			ELSinicial = numeroELSSeleccionado;
		else if (agenda.getUbicacionBase().compareTo("Buenos Aires") == 0)
			ELSinicialBSAS = numeroELSSeleccionado;

	}

	public VentanaVisualizarEquipos TomarDatosDeTablasListado(int numeroELSSeleccionado2,
			VentanaVisualizarEquipos ventanaVisualizarEquipos) throws ParseException {
		// Configuración inicial específica para esta función

		ventanaVisualizarEquipos = new VentanaVisualizarEquipos(this);
		ventanaVisualizarEquipos.setTitle(String.valueOf(numeroELSSeleccionado2));
		ventanasAbiertas.add(ventanaVisualizarEquipos);
		cerraVentanaVisualizarEquipoListado(ventanaVisualizarEquipos);

		monedaFormatter = new MonedaFormatter();
		controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
		SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

		ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELSSeleccionado2));
		cargarDatosComunes(ventanaVisualizarEquipos, numeroELSSeleccionado2);

		return ventanaVisualizarEquipos;
	}

	public VentanaVisualizarEquipos ActualizarDatosDeTablasListado(int numeroELSSeleccionado2,
			VentanaVisualizarEquipos ventanaVisualizarEquipos) throws ParseException {
		// Configuración inicial específica para esta función
		if (!actualizarEnlistado) {
			ventanaVisualizarEquipos = new VentanaVisualizarEquipos(this);
			ventanaVisualizarEquipos.setTitle(String.valueOf(numeroELSSeleccionado2));
			ventanasAbiertas.add(ventanaVisualizarEquipos);
			cerraVentanaVisualizarEquipoListado(ventanaVisualizarEquipos);
		}

		monedaFormatter = new MonedaFormatter();
		controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
		SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

		ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELSSeleccionado2));
		cargarDatosComunes(ventanaVisualizarEquipos, numeroELSSeleccionado2);

		return ventanaVisualizarEquipos;
	}

	// Método privado para cargar los datos comunes
	private void cargarDatosComunes(VentanaVisualizarEquipos ventanaVisualizarEquipos, int numeroELS)
			throws ParseException {
		reparacion = agenda.dameReparacionXels(numeroELS);

		// Llenar campos de texto
		ventanaVisualizarEquipos.setTextNombreEquipo(reparacion.getNombreEquipo());
		ventanaVisualizarEquipos.getTextNombreEquipo().setCaretPosition(0);
		ventanaVisualizarEquipos.setTextMarca(reparacion.getMarca());
		ventanaVisualizarEquipos.getTextMarca().setCaretPosition(0);
		ventanaVisualizarEquipos.setTextModelo(reparacion.getModelo());
		ventanaVisualizarEquipos.getTextModelo().setCaretPosition(0);
		ventanaVisualizarEquipos.setTextNSerie(reparacion.getNumeroDeSerie());
		ventanaVisualizarEquipos.getTextNSerie().setCaretPosition(0);
		ventanaVisualizarEquipos.setTextLugarDeIngreso(reparacion.getLugarDeIngreso());

		// Campos opcionales (pueden ser nulos)
		ventanaVisualizarEquipos.setTextFalla(reparacion.getFalla() == null ? "" : reparacion.getFalla());
		ventanaVisualizarEquipos.getTextFalla().setCaretPosition(0);
		ventanaVisualizarEquipos.setTextAvisoCliente(reparacion.getAviso());
		ventanaVisualizarEquipos.setTextClienteCliente(reparacion.getClienteCliente());
		ventanaVisualizarEquipos.getTextClienteCliente().setCaretPosition(0);
		ventanaVisualizarEquipos.setTextRemitoCliente(reparacion.getRemitoCliente());
		ventanaVisualizarEquipos.setTextCliente(reparacion.getCliente());
		ventanaVisualizarEquipos.getTextCliente().setCaretPosition(0);
		ventanaVisualizarEquipos.setTextSucursal(reparacion.getSucursal());
		ventanaVisualizarEquipos.getTextSucursal().setCaretPosition(0);

		// Fechas (manejo de valores nulos)
		ventanaVisualizarEquipos.setTextFechaEntrada2(
				reparacion.getFecha_Entrada() == null ? null : dateFormat.parse(reparacion.getFecha_Entrada()));
		ventanaVisualizarEquipos.setTextFechaSalida(
				reparacion.getFecha_Salida() == null ? null : dateFormat.parse(reparacion.getFecha_Salida()));
		ventanaVisualizarEquipos.setTextFechaReparacion2(
				reparacion.getFechadereparacion() == null ? null : dateFormat.parse(reparacion.getFechadereparacion()));
		ventanaVisualizarEquipos.setTextFechaRespuesta2(
				reparacion.getFechAceptacion() == null ? null : dateFormat.parse(reparacion.getFechAceptacion()));
		ventanaVisualizarEquipos
				.setFechaFabr2(reparacion.getFechaFabr() == null ? null : dateFormat.parse(reparacion.getFechaFabr()));

		// Estados y diagnósticos
		ventanaVisualizarEquipos.setTextEstadoFisico(reparacion.getEstadoFisico());
		ventanaVisualizarEquipos.setTextEstadoTecnico(reparacion.getEstadoTecnico());
		ventanaVisualizarEquipos.setTextEstadoComercial(reparacion.getEstadoComercial());
		ventanaVisualizarEquipos.setTextDiagnostico(reparacion.getSolucion());
		ventanaVisualizarEquipos.setTextInformeCliente(reparacion.getInformecliente());

		// Información del técnico y OC
		ventanaVisualizarEquipos.setTextNombreTecnico(reparacion.getNombreUsuario());
		ventanaVisualizarEquipos.setTextOC(reparacion.getOrdendeCompra());

		// Código y número de remito
		int codigoRemitoBase = reparacion.getCodigo();
		String codigoRemitoVisual = obtenerCodigoRemitoVisual(codigoRemitoBase);
		ventanaVisualizarEquipos.setTextUbicacionRemito(codigoRemitoVisual);

		int numeroRemitoBase = reparacion.getNumeroRemitoSalida();
		String numeroRemitoVisual = numeroRemitoBase > 0 ? String.format("%08d", numeroRemitoBase) : "";
		ventanaVisualizarEquipos.setTextNumeroRemito(numeroRemitoVisual);

		// Llenar tabla de repuestos
		llenarTablaRepuestos(ventanaVisualizarEquipos);
		ventanaVisualizarEquipos.getTextNombreEquipo().moveCaretPosition(0);

		// Formatear y mostrar valores monetarios
		String presupuestoPeso = monedaFormatter.formatPeso(reparacion.getPrecioPeso().toString());
		String presupuestoDolar = monedaFormatter.formatDolar(reparacion.getPrecioDolar().toString());
		String pagoPeso = monedaFormatter.formatPeso(reparacion.getPago().toString());

		ventanaVisualizarEquipos.setTextPresupuesto(presupuestoPeso);
		ventanaVisualizarEquipos.setTextPresupuestoDolar(presupuestoDolar);
		ventanaVisualizarEquipos.setTextPago(pagoPeso);

		// Checkboxes
		ventanaVisualizarEquipos.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
		ventanaVisualizarEquipos.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
		ventanaVisualizarEquipos.setChckWORDGenerado(reparacion.getWORDgenerado());
		ventanaVisualizarEquipos.setChckWORDEnviado(reparacion.getWORDenviado());
		ventanaVisualizarEquipos.setChckbxAvisoEnviado(reparacion.getAvisoEnviado());

		// Verificar y deshabilitar campos
		verificarPresupuesto(ventanaVisualizarEquipos);
		deshabilitarCampos(ventanaVisualizarEquipos);
	}

	// Método auxiliar para formatear el código de remito
	private String obtenerCodigoRemitoVisual(int codigoRemitoBase) {
		if (codigoRemitoBase == 2 || codigoRemitoBase == 5 || codigoRemitoBase == 6 || codigoRemitoBase == 7) {
			return String.format("%04d", codigoRemitoBase); // Agrega ceros al inicio hasta 4 dígitos
		} else if (codigoRemitoBase == 1000 || codigoRemitoBase == 2000 || codigoRemitoBase == 3000) {
			return String.valueOf(codigoRemitoBase); // Si es 1000, 2000 o 3000, lo guarda tal cual
		} else {
			return ""; // Manejo para valores inesperados
		}
	}

	public int cantidadVentanasAbiertas() {

		return ventanasAbiertas.size() + 1;

	}

	@SuppressWarnings("deprecation")
	public void llenarTablaClientesWSP() {

		ventanaClientesWSP.getModelClientesWSP().setRowCount(0); // Para
																	// vaciar
																	// la
																	// tabla
		ventanaClientesWSP.getModelClientesWSP().setColumnCount(0);
		ventanaClientesWSP.getModelClientesWSP()
				.setColumnIdentifiers(ventanaClientesWSP.getNombreColumnasClientesWSP());

		this.clientesWSP_en_tabla = agenda.obtenerClientesWSP();

		for (int i = 0; i < this.clientesWSP_en_tabla.size(); i++) {

			Object[] fila = { this.clientesWSP_en_tabla.get(i).getOrganizacion(),
					this.clientesWSP_en_tabla.get(i).getNombreWSP(),
					this.clientesWSP_en_tabla.get(i).getTelefonoWSP(), };

			this.ventanaClientesWSP.getModelClientesWSP().addRow(fila);
		}

		ventanaClientesWSP.setCellRender(this.ventanaClientesWSP.getTablaClienteSWSP());
		this.ventanaClientesWSP.show();
	}

	private void llenarComboOrganizacion() {

		agenda.ListarOrganizacionWSP(ventanaWSP.getComboOrganizacion());

		ventanaWSP.getComboOrganizacion().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (ventanaWSP.getComboOrganizacion().getSelectedItem() != null) {

					ContactoWSP = ventanaWSP.getComboOrganizacion().getSelectedItem().toString();

					agenda.ListarContactoxOrganizacion(ventanaWSP.getComboNombreBuscado(), ContactoWSP);
					if (ventanaWSP.getComboOrganizacion().getSelectedItem().toString()
							.compareTo("-- Seleccionar Cliente --") == 0)
						ventanaWSP.getTextnumeroContactoBuscado().setText("");

				}

			}
		});

		ventanaWSP.getComboOrganizacion().setSelectedItem("");

	}

	private void llenarComboNombreWSP() {

		ventanaWSP.getComboNombreBuscado().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String telefono = "";

				if (ventanaWSP.getComboNombreBuscado().getSelectedItem() != null) {

					nombreBuscado = ventanaWSP.getComboNombreBuscado().getSelectedItem().toString();
					telefono = agenda.obtenerTelefonoxContacto(nombreBuscado);
					ventanaWSP.getTextnumeroContactoBuscado().setText(telefono);

				}
			}
		});

		ventanaWSP.getComboNombreBuscado().setSelectedItem("");
		ventanaWSP.getTextnumeroContactoBuscado().setText("");

	}

	private boolean existeClienteWSP(String telefono) {
		if (clientesWSP_en_tabla == null) {
			return false;
		} else if (clientesWSP_en_tabla.size() == 0)
			return false;

		for (int i = 0; i < clientesWSP_en_tabla.size(); i++) {

			if (clientesWSP_en_tabla.get(i).getTelefonoWSP().compareTo(telefono) == 0)
				return true;

		}
		return false;
	}

	private RegistroEntradaReporteDTO TomarDatosPantallaIngresoRep() {

		RegistroEntradaReporteDTO nuevoReparacion;

		int ELS = Integer.parseInt(this.ventanaAgregarEquipo.getTextELS());
		String falla = this.ventanaAgregarEquipo.getTextFalla().getText();
		String RemitoCLiente = this.ventanaAgregarEquipo.getTextRemitoCliente().getText();
		int IDEquipo = dameIDequipo();
		String NombreEquipo = this.ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString();
		System.out.println(NombreEquipo);

		String Modelo = this.ventanaAgregarEquipo.getComboModelo().getSelectedItem().toString();
		String Marca = this.ventanaAgregarEquipo.getComboMarca().getSelectedItem().toString();
		String Serie = this.ventanaAgregarEquipo.getComboSerie().getSelectedItem().toString();
		String aviso = this.ventanaAgregarEquipo.getTextAvisoCliente().getText();
		String ClienteCliente = this.ventanaAgregarEquipo.getTextClienteCliente().getText();
		int idCliente = idCli;
		int idSucursal = idSuc;
		String Cliente = this.ventanaAgregarEquipo.getComboClientes().getSelectedItem().toString();
		String Sucursal = this.ventanaAgregarEquipo.getComboSucursal().getSelectedItem().toString();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		fechaentrada = null;
		java.util.Date fechaEntrada = this.ventanaAgregarEquipo.getFechaEntrada().getDate();

		if (fechaEntrada != null) {

			fechaentrada = dateFormat.format(fechaEntrada);
		}

		Enumeration<?> elements = ventanaAgregarEquipo.getGrupoEstadoFisico().getElements();

		while (elements.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elements.nextElement();
			if (button.isSelected()) {

				estadoFisico = button.getText();

			}
		}

		if (verificarCaracteresPermitidos(NombreEquipo) || verificarCaracteresPermitidos(falla)
				|| verificarCaracteresPermitidos(Modelo) || verificarCaracteresPermitidos(Marca)
				|| verificarCaracteresPermitidos(Serie)) {

			nuevoReparacion = null;

		} else {
			nuevoReparacion = new RegistroEntradaReporteDTO(ELS, fechaentrada, falla, estadoFisico, estadoTecnico,
					RemitoCLiente, IDEquipo, NombreEquipo, Modelo, Marca, Serie, aviso, ClienteCliente, idCliente,
					idSucursal, Cliente, Sucursal);
		}

		return nuevoReparacion;
	}

	private static String generateRandomText() {

		SecureRandom random = new SecureRandom();
		String text = new BigInteger(25, random).toString(32);
		text = text.toUpperCase();
		return text;

	}

	private RepuestosDTO TomarDatosRepuesto(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		int ELS = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());
		String Referencia = this.ventanaagregarRepuesto.getTxtReferencia().getText();
		String Original = this.ventanaagregarRepuesto.getTxtOriginal().getText();
		String Reemplazo = this.ventanaagregarRepuesto.getTxtReemplazo().getText();

		String Nota = this.ventanaagregarRepuesto.getTxtNota().getText();

		if (verificarCaracteresPermitidos(Referencia) || verificarCaracteresPermitidos(Original)
				|| verificarCaracteresPermitidos(Reemplazo) || verificarCaracteresPermitidos(Nota)) {

			nuevoRepuesto = null;

		} else {
			nuevoRepuesto = new RepuestosDTO(ELS, Referencia, Original, Reemplazo, Nota);

		}

		return nuevoRepuesto;

	}

	@SuppressWarnings("deprecation")
	private void llenarTablaRepuestos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		ventanaVisualizarEquipos.getModelRepuestos().setRowCount(0); // Para
																		// vaciar
																		// tabla
		ventanaVisualizarEquipos.getModelRepuestos().setColumnCount(0);
		ventanaVisualizarEquipos.getModelRepuestos().setColumnIdentifiers(ventanaVisualizarEquipos.getNombreColumnas());

		int ELS = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());

		this.Repuestos_en_tabla = (List<RepuestosDTO>) agenda.dameRepuestoXels(ELS);

		for (int i = 0; i < this.Repuestos_en_tabla.size(); i++) {
			Object[] fila = { this.Repuestos_en_tabla.get(i).getRef(), this.Repuestos_en_tabla.get(i).getOriginal(),
					this.Repuestos_en_tabla.get(i).getReemplazo(), this.Repuestos_en_tabla.get(i).getNotas() };
			ventanaVisualizarEquipos.getModelRepuestos().addRow(fila);
		}
		ventanaVisualizarEquipos.show();

	}
	
	
	
	
	private void verificarPresupuesto(VentanaVisualizarEquipos ventana) {
		
	    Double presupuesto = reparacion.getPrecioPeso();
	    Double pago = reparacion.getPago();
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
	    } else if (comparacion > 0 && pago.compareTo(0.0) > 0) {
	        // Pago parcial
	        aplicarEstadoVisual(ventana, "PAGADO PARCIALMENTE", PARCIAL);
	    } else if (pago.compareTo(0.0) == 0) {
	        // Sin pago - verificar estado comercial
	        String leyenda = determinarLeyendaSinPago(estadoComercial);
	        Color color = "ESPERANDO ACEPTACIÓN".equals(leyenda) ? ESPERANDO : FALTA_PAGO;
	        aplicarEstadoVisual(ventana, leyenda, color);
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

	private void aplicarEstadoVisual(VentanaVisualizarEquipos ventana, String leyenda, Color color) {
	    ventana.getTextEquipoPagado().setText(leyenda);
	    ventana.getTextEquipoPagado().setVisible(true);
	    ventana.getTextEquipoPagado().setBackground(color);
	    ventana.getPanel_MontoPresupuesto().setBackground(color);
	    ventana.getTextPresupuesto().setBackground(color);
	    ventana.getTextPresupuestoDolar().setBackground(color);
	    ventana.getTextPago().setBackground(color);
	}


	private void deshabilitarCampos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ventanaVisualizarEquipos.getTextNombreEquipo().setEditable(false);
		ventanaVisualizarEquipos.getTextModelo().setEditable(false);
		ventanaVisualizarEquipos.getTextMarca().setEditable(false);
		ventanaVisualizarEquipos.getTextNSerie().setEditable(false);
		ventanaVisualizarEquipos.getTextClienteCliente().setEditable(false);
		ventanaVisualizarEquipos.getTextAvisoCliente().setEditable(false);
		ventanaVisualizarEquipos.getTextRemitoCliente().setEditable(false);
		ventanaVisualizarEquipos.getTextFalla().setEditable(false);
		ventanaVisualizarEquipos.getTextOC().setEditable(false);
		ventanaVisualizarEquipos.getTextPresupuesto().setEditable(false);
		ventanaVisualizarEquipos.getTextPresupuestoDolar().setEditable(false);
		ventanaVisualizarEquipos.getTextPago().setEditable(false);

		ventanaVisualizarEquipos.getTextNombreTecnico().setEditable(false);
		ventanaVisualizarEquipos.getTextDiagnostico().setEditable(false);
		ventanaVisualizarEquipos.getTextInformeCliente().setEditable(false);
		ventanaVisualizarEquipos.getTablaRepuestos().setEnabled(true);
		ventanaVisualizarEquipos.getFechaEntrada().setEnabled(false);
		ventanaVisualizarEquipos.getFechaReparacion().setEnabled(false);
		ventanaVisualizarEquipos.getFechaRespuesta().setEnabled(false);
		ventanaVisualizarEquipos.getFechaSalida().setEnabled(false);
		ventanaVisualizarEquipos.getBtnGuardarCambios().setEnabled(false);
		ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(false);
		ventanaVisualizarEquipos.getBtnRepuestos().setEnabled(false);
		ventanaVisualizarEquipos.getBtnEliminarRepuesto().setEnabled(false);
		ventanaVisualizarEquipos.getBtnEditarRepuesto().setEnabled(false);

		ventanaVisualizarEquipos.getTextCliente().setVisible(true);
		ventanaVisualizarEquipos.getTextSucursal().setVisible(true);
		ventanaVisualizarEquipos.getComboClientes().setVisible(false);
		ventanaVisualizarEquipos.getComboSucursal().setVisible(false);
		ventanaVisualizarEquipos.getTextNombreTecnico().setVisible(true);
		ventanaVisualizarEquipos.getComboTecnico().setVisible(false);
	}

	@SuppressWarnings("unused")
	private void habilitarCampos(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		// String nombreCliente = "";
		// String nombreSucursal = "";
		String nombreTecnico = ventanaVisualizarEquipos.getTextNombreTecnico().getText().trim();

		String nombreCliente = ventanaVisualizarEquipos.getTextCliente().getText(); // Obtener el texto actual del
																					// JTextField
		DefaultComboBoxModel<ClienteDTO> model = (DefaultComboBoxModel<ClienteDTO>) ventanaVisualizarEquipos
				.getComboClientes().getModel();
		String nombreSucursal = ventanaVisualizarEquipos.getTextSucursal().getText();
		DefaultComboBoxModel<SucursalDTO> modelSucursal = (DefaultComboBoxModel<SucursalDTO>) ventanaVisualizarEquipos
				.getComboSucursal().getModel();

		DefaultComboBoxModel<UsuarioDTO> modelTecnico = (DefaultComboBoxModel<UsuarioDTO>) ventanaVisualizarEquipos
				.getComboTecnico().getModel();

		ventanaVisualizarEquipos.getTextNombreEquipo().setEditable(true);
		ventanaVisualizarEquipos.getTextModelo().setEditable(true);
		ventanaVisualizarEquipos.getTextMarca().setEditable(true);
		ventanaVisualizarEquipos.getTextNSerie().setEditable(true);
		ventanaVisualizarEquipos.getTextClienteCliente().setEditable(true);
		ventanaVisualizarEquipos.getTextAvisoCliente().setEditable(true);
		ventanaVisualizarEquipos.getTextRemitoCliente().setEditable(true);
		ventanaVisualizarEquipos.getTextFalla().setEditable(true);
		ventanaVisualizarEquipos.getTextOC().setEditable(true);
		ventanaVisualizarEquipos.getTextPresupuesto().setEditable(true);
		ventanaVisualizarEquipos.getTextPresupuestoDolar().setEditable(true);
		ventanaVisualizarEquipos.getTextPago().setEditable(true);
//
		ventanaVisualizarEquipos.getTextDiagnostico().setEditable(true);
		ventanaVisualizarEquipos.getTextInformeCliente().setEditable(true);
		ventanaVisualizarEquipos.getTablaRepuestos().setEnabled(true);

		ventanaVisualizarEquipos.getFechaEntrada().setEnabled(true);
		ventanaVisualizarEquipos.getFechaReparacion().setEnabled(true);
		ventanaVisualizarEquipos.getFechaRespuesta().setEnabled(true);
		ventanaVisualizarEquipos.getFechaSalida().setEnabled(true);
		ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(true);
		ventanaVisualizarEquipos.getBtnGuardarCambios().setEnabled(true);
		ventanaVisualizarEquipos.getBtnRepuestos().setEnabled(true);
		ventanaVisualizarEquipos.getBtnEliminarRepuesto().setEnabled(true);

		ventanaVisualizarEquipos.getTextCliente().setVisible(false);
		ventanaVisualizarEquipos.getTextSucursal().setVisible(false);
		ventanaVisualizarEquipos.getTextNombreTecnico().setVisible(false);

		ventanaVisualizarEquipos.getComboClientes().setVisible(true);
		ventanaVisualizarEquipos.getComboSucursal().setVisible(true);
		ventanaVisualizarEquipos.getComboTecnico().setVisible(true);

		// Configurar el comportamiento para seleccionar automáticamente el cliente del
		// JTextField

		for (int i = 0; i < model.getSize(); i++) {
			ClienteDTO cliente = model.getElementAt(i);
			if (cliente.getRazon_Social().equalsIgnoreCase(nombreCliente)) { // Comparar nombres (ignorar
																				// mayúsculas/minúsculas)
				ventanaVisualizarEquipos.getComboClientes().setSelectedItem(cliente); // Seleccionar el cliente
																						// correspondiente
				break;
			}
		}

		for (int i = 0; i < modelSucursal.getSize(); i++) {
			SucursalDTO sucursal = modelSucursal.getElementAt(i);
			if (sucursal.getNombreSucursal().equalsIgnoreCase(nombreSucursal)) {
				ventanaVisualizarEquipos.getComboSucursal().setSelectedItem(sucursal);
				break;
			}
		}

		// Selecciona el técnico solo si hay coincidencia real, omitiendo el primer ítem
		// si es vacío
		for (int i = 0; i < modelTecnico.getSize(); i++) {
			UsuarioDTO tecnico = modelTecnico.getElementAt(i);
			String NombreTecnicoCompleto = tecnico.getNombre() + " " + tecnico.getApellido();
			if (NombreTecnicoCompleto.equalsIgnoreCase(nombreTecnico)) {
				ventanaVisualizarEquipos.getComboTecnico().setSelectedIndex(i);
				break;
			}
		}

	}

	private int IDSucursalPorNombre(String nombreSucursal, int IDcliente) {

		return agenda.idSucursalporNombre(nombreSucursal, IDcliente);
	}

	private int IDClientePorNombre(String nombreCliente) {

		return agenda.idClienteporNombre(nombreCliente);

	}

	private int IDUsuarioPorNombre(String nombreTecnico) {

		return agenda.idUsuarioporNombre(nombreTecnico);

	}

	private List<Integer> buscarEnCampos(String campo, String texto) {

		return agenda.buscarEnCampos(campo, texto);

	}

	private void llenarComboClienteV(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		// PASO 1: Remover todos los listeners existentes para evitar duplicados
		ItemListener[] listeners = ventanaVisualizarEquipos.getComboClientes().getItemListeners();
		for (ItemListener listener : listeners) {
			ventanaVisualizarEquipos.getComboClientes().removeItemListener(listener);
		}

		// PASO 2: Guardar la selección actual antes de recargar
		ClienteDTO seleccionado = (ClienteDTO) ventanaVisualizarEquipos.getComboClientes().getSelectedItem();

		// PASO 3: Recargar la lista de clientes
		agenda.ListarCliente(ventanaVisualizarEquipos.getComboClientes());

		// PASO 4: Restaurar la selección guardada
		if (seleccionado != null) {
			DefaultComboBoxModel<ClienteDTO> model = (DefaultComboBoxModel<ClienteDTO>) ventanaVisualizarEquipos
					.getComboClientes().getModel();
			for (int i = 0; i < model.getSize(); i++) {
				if (model.getElementAt(i).equals(seleccionado)) {
					ventanaVisualizarEquipos.getComboClientes().setSelectedIndex(i);
					break;
				}
			}
		}

		// PASO 5: Agregar UN SOLO listener nuevo
		ventanaVisualizarEquipos.getComboClientes().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Solo procesar cuando se SELECCIONA un item (evitar doble disparo)
				if (e.getStateChange() == ItemEvent.SELECTED
						&& ventanaVisualizarEquipos.getComboClientes().getSelectedItem() != null) {

					// Obtener cliente seleccionado
					Cliente = (ClienteDTO) ventanaVisualizarEquipos.getComboClientes().getSelectedItem();
					int id = Cliente.getId();
					idCli = id;

					// Cargar sucursales del cliente seleccionado
					agenda.ListarSucursalesxCliente(ventanaVisualizarEquipos.getComboSucursal(), id);

					// Seleccionar la sucursal basada en el texto del JTextField
					String nombreSucursal = ventanaVisualizarEquipos.getTextSucursal().getText();
					if (nombreSucursal != null && !nombreSucursal.trim().isEmpty()) {
						@SuppressWarnings("unchecked")
						DefaultComboBoxModel<SucursalDTO> modelSucursal = (DefaultComboBoxModel<SucursalDTO>) ventanaVisualizarEquipos
								.getComboSucursal().getModel();

						for (int i = 0; i < modelSucursal.getSize(); i++) {
							SucursalDTO sucursal = modelSucursal.getElementAt(i);
							if (sucursal != null && sucursal.getNombreSucursal() != null
									&& sucursal.getNombreSucursal().equalsIgnoreCase(nombreSucursal.trim())) {
								ventanaVisualizarEquipos.getComboSucursal().setSelectedItem(sucursal);
								break;
							}
						}
					}
				}
			}
		});
	}

	private void llenarComboSucursal() {

		// PASO 1: Remover todos los listeners existentes para evitar duplicados
		ItemListener[] listeners = ventanaAgregarEquipo.getComboSucursal().getItemListeners();
		for (ItemListener listener : listeners) {
			ventanaAgregarEquipo.getComboSucursal().removeItemListener(listener);
		}

		// PASO 2: Agregar UN SOLO listener nuevo
		ventanaAgregarEquipo.getComboSucursal().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Solo procesar cuando se SELECCIONA un item (evitar doble disparo)
				if (e.getStateChange() == ItemEvent.SELECTED
						&& ventanaAgregarEquipo.getComboSucursal().getSelectedItem() != null) {

					// Validar que sea un item válido usando tu método personalizado
					if (VistaPropias.AutoCompletarComboBox.esItemValido(ventanaAgregarEquipo.getComboSucursal())) {

						// Obtener sucursal seleccionada
						Sucursal = (SucursalDTO) ventanaAgregarEquipo.getComboSucursal().getSelectedItem();

						// Validar que la sucursal no sea null antes de obtener el ID
						if (Sucursal != null) {
							int idsuc = Sucursal.getIdSucursal();
							idSuc = idsuc;
						}
					}
				}
			}
		});
	}

	// Variables de instancia para evitar procesamiento múltiple
	private boolean procesandoCliente = false;
	private boolean procesandoMarca = false;
	private boolean procesandoModelo = false;

	// Variables para guardar las últimas selecciones y evitar reprocesamiento
	private Object ultimaSeleccionCliente = null;
	private Object ultimaSeleccionMarca = null;
	private Object ultimaSeleccionModelo = null;

	// Método para verificar si hay cambio en la selección
	private boolean hayaCambioEnSeleccion(JComboBox combo) {
		String comboName = combo.getName();
		Object seleccionActual = combo.getSelectedItem();

		switch (comboName) {
		case "comboCliente":
			return !Objects.equals(ultimaSeleccionCliente, seleccionActual);
		case "comboMarca":
			return !Objects.equals(ultimaSeleccionMarca, seleccionActual);
		case "comboModelo":
			return !Objects.equals(ultimaSeleccionModelo, seleccionActual);
		default:
			return true; // Para otros combos, siempre validar
		}
	}

	private void llenarComboCliente() {
		JComboBox combo = ventanaAgregarEquipo.getComboClientes();

		// Remover listeners anteriores para evitar duplicados
		removeAllListeners(combo);

		try {
			agenda.ListarCliente(combo);

			// ItemListener para selección por mouse o teclas
			combo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED && !procesandoCliente
							&& VistaPropias.AutoCompletarComboBox.esItemValido(combo)) {

						// Solo procesar si la selección realmente cambió
						Object seleccionActual = combo.getSelectedItem();
						if (!Objects.equals(ultimaSeleccionCliente, seleccionActual)) {
							SwingUtilities.invokeLater(() -> procesarClienteSeleccionado());
						}
					}
				}
			});

			escuchaDeEnterYtab(combo);

		} catch (Exception ex) {
			System.err.println("Error al llenar combo cliente: " + ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + ex.getMessage());
		}
	}

	private void llenarComboNombreEquipo() {
		try {
			agenda.ListarEquipo(ventanaAgregarEquipo.getComboNombreEquipo());
			ventanaAgregarEquipo.getComboNombreEquipo().setSelectedIndex(-1);
		} catch (Exception ex) {
			System.err.println("Error al llenar combo equipo: " + ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error al cargar equipos: " + ex.getMessage());
		}
	}

	private void llenarComboMarca() {
		JComboBox combo = ventanaAgregarEquipo.getComboMarca();

		// Remover listeners anteriores
		removeAllListeners(combo);

		try {
			agenda.ListarMarca(combo);

			// ItemListener para selección por mouse o teclas
			combo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED && !procesandoMarca
							&& VistaPropias.AutoCompletarComboBox.esItemValido(combo)) {

						// Solo procesar si la selección realmente cambió
						Object seleccionActual = combo.getSelectedItem();
						if (!Objects.equals(ultimaSeleccionMarca, seleccionActual)) {
							SwingUtilities.invokeLater(() -> procesarMarcaSeleccionada());
						}
					}
				}
			});

			escuchaDeEnterYtab(combo);
			combo.setSelectedIndex(-1);

		} catch (Exception ex) {
			System.err.println("Error al llenar combo marca: " + ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error al cargar marcas: " + ex.getMessage());
		}
	}

	private void llenarComboModelo() {
		JComboBox combo = ventanaAgregarEquipo.getComboModelo();

		// Remover listeners anteriores
		removeAllListeners(combo);

		// ItemListener para selección por mouse o teclas
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && !procesandoModelo
						&& VistaPropias.AutoCompletarComboBox.esItemValido(combo)) {

					// Solo procesar si la selección realmente cambió
					Object seleccionActual = combo.getSelectedItem();
					if (!Objects.equals(ultimaSeleccionModelo, seleccionActual)) {
						SwingUtilities.invokeLater(() -> procesarModeloSeleccionado());
					}
				}
			}
		});

		escuchaDeEnterYtab(combo);
		combo.setSelectedIndex(-1);
		ventanaAgregarEquipo.getComboSerie().setSelectedIndex(-1);
	}

	private void escuchaDeEnterYtab(JComboBox combo) {
		// Editor del combo
		JTextComponent editor = (JTextComponent) combo.getEditor().getEditorComponent();

		// NO remover listeners nativos - solo agregar los nuestros
		// Solo removemos nuestros listeners personalizados si ya existen
		removeCustomListeners(editor, combo);

		// Crear referencia final para usar en clases anónimas
		final JComboBox comboFinal = combo;

		// Validación con ENTER usando KeyListener
		KeyListener enterKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					SwingUtilities.invokeLater(() -> {
						if (hayaCambioEnSeleccion(comboFinal)) {
							validarSeleccion(comboFinal);
						}
					});
				}
			}
		};

		// Validación con TAB (focusLost)
		FocusListener customFocusListener = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				// Solo validar si realmente cambió la selección desde la última vez
				SwingUtilities.invokeLater(() -> {
					if (hayaCambioEnSeleccion(comboFinal)) {
						validarSeleccion(comboFinal);
					}
				});
			}
		};

		// Agregar nuestros listeners personalizados
		editor.addKeyListener(enterKeyListener);
		editor.addFocusListener(customFocusListener);

		// Guardar referencias para poder removerlos después si es necesario
		editor.putClientProperty("customKeyListener", enterKeyListener);
		editor.putClientProperty("customFocusListener", customFocusListener);
	}

	private void validarSeleccion(JComboBox combo) {
		String comboName = combo.getName();

		// Limpiar combos dependientes primero
		if ("comboCliente".equals(comboName)) {
			ventanaAgregarEquipo.getComboSucursal().removeAllItems();
			ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(-1);
		}

		if (VistaPropias.AutoCompletarComboBox.esItemValido(combo)) {
			combo.setPopupVisible(false);

			switch (comboName) {
			case "comboCliente":
				procesarClienteSeleccionado();
				break;
			case "comboMarca":
				procesarMarcaSeleccionada();
				break;
			case "comboModelo":
				procesarModeloSeleccionado();
				break;
			}

		} else {
			// Limpiar combos dependientes cuando la selección no es válida
			switch (comboName) {
			case "comboCliente":
				ultimaSeleccionCliente = null; // Resetear para que se pueda volver a procesar
				ventanaAgregarEquipo.getComboSucursal().removeAllItems();
				ventanaAgregarEquipo.getComboSucursal().setSelectedIndex(-1);
				break;
			case "comboMarca":
				ultimaSeleccionMarca = null; // Resetear para que se pueda volver a procesar
				ultimaSeleccionModelo = null; // También resetear modelo ya que depende de marca
				ventanaAgregarEquipo.getComboModelo().removeAllItems();
				ventanaAgregarEquipo.getComboModelo().setSelectedIndex(-1);
				ventanaAgregarEquipo.getComboSerie().removeAllItems();
				ventanaAgregarEquipo.getComboSerie().setSelectedIndex(-1);
				break;
			case "comboModelo":
				ultimaSeleccionModelo = null; // Resetear para que se pueda volver a procesar
				ventanaAgregarEquipo.getComboSerie().removeAllItems();
				ventanaAgregarEquipo.getComboSerie().setSelectedIndex(-1);
				break;
			}

			if (!combo.isEditable()) {
				JOptionPane.showMessageDialog(null, "Item no encontrado");
				combo.setSelectedIndex(0);
			}
		}
	}

	private void procesarClienteSeleccionado() {
		if (procesandoCliente)
			return;

		procesandoCliente = true;

		try {
			JComboBox comboClientes = ventanaAgregarEquipo.getComboClientes();
			ClienteDTO cliente = (ClienteDTO) comboClientes.getSelectedItem();

			if (cliente != null) {
				int id = cliente.getId();
				JComboBox comboSucursal = ventanaAgregarEquipo.getComboSucursal();
				comboSucursal.removeAllItems();
				comboSucursal.setSelectedIndex(-1);
				agenda.ListarSucursalesxCliente(comboSucursal, id);
				idCli = id;

				// Guardar la selección actual para evitar reprocesamiento
				ultimaSeleccionCliente = cliente;
			}

		} catch (Exception ex) {
			System.err.println("Error al procesar cliente seleccionado: " + ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error al cargar sucursales: " + ex.getMessage());

		} finally {
			procesandoCliente = false;
		}
	}

	private void procesarMarcaSeleccionada() {
		if (procesandoMarca)
			return;

		procesandoMarca = true;

		try {
			JComboBox comboMarca = ventanaAgregarEquipo.getComboMarca();

			if (comboMarca.getSelectedItem() != null) {
				Marca = comboMarca.getSelectedItem().toString();

				JComboBox comboModelo = ventanaAgregarEquipo.getComboModelo();
				JComboBox comboSerie = ventanaAgregarEquipo.getComboSerie();

				comboModelo.removeAllItems();
				comboModelo.setSelectedIndex(-1);
				comboSerie.removeAllItems();
				comboSerie.setSelectedIndex(-1);

				agenda.ListarModelosxMarca(comboModelo, Marca);

				// Guardar la selección actual para evitar reprocesamiento
				ultimaSeleccionMarca = comboMarca.getSelectedItem();
				// Resetear la selección de modelo ya que cambió la marca
				ultimaSeleccionModelo = null;
			}

		} catch (Exception ex) {
			System.err.println("Error al procesar marca seleccionada: " + ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error al cargar modelos: " + ex.getMessage());

		} finally {
			procesandoMarca = false;
		}
	}

	private void procesarModeloSeleccionado() {
		if (procesandoModelo)
			return;

		procesandoModelo = true;

		try {
			JComboBox comboModelo = ventanaAgregarEquipo.getComboModelo();

			if (comboModelo.getSelectedItem() != null) {
				Modelo = comboModelo.getSelectedItem().toString();

				JComboBox comboSerie = ventanaAgregarEquipo.getComboSerie();
				comboSerie.removeAllItems();
				comboSerie.setSelectedIndex(-1);

				agenda.ListarSeriexModelo(comboSerie, Modelo);

				// Guardar la selección actual para evitar reprocesamiento
				ultimaSeleccionModelo = comboModelo.getSelectedItem();
			}

		} catch (Exception ex) {
			System.err.println("Error al procesar modelo seleccionado: " + ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error al cargar series: " + ex.getMessage());

		} finally {
			procesandoModelo = false;
		}
	}

	// Método para remover solo nuestros listeners personalizados, no los nativos de
	// Swing
	private void removeCustomListeners(JTextComponent editor, JComboBox combo) {
		// Remover nuestros listeners personalizados si existen
		KeyListener customKeyListener = (KeyListener) editor.getClientProperty("customKeyListener");
		if (customKeyListener != null) {
			editor.removeKeyListener(customKeyListener);
			editor.putClientProperty("customKeyListener", null);
		}

		FocusListener customFocusListener = (FocusListener) editor.getClientProperty("customFocusListener");
		if (customFocusListener != null) {
			editor.removeFocusListener(customFocusListener);
			editor.putClientProperty("customFocusListener", null);
		}
	}

	private void removeAllListeners(JComboBox combo) {
		ItemListener[] itemListeners = combo.getItemListeners();
		for (ItemListener listener : itemListeners) {
			combo.removeItemListener(listener);
		}
	}

	private void llenarComboTecnico(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		agenda.ListarTecnicosV(ventanaVisualizarEquipos.getComboTecnico());
	}

	private int dameIDequipo() {
		int idEquipo = 0;
		idEquipo = agenda.dameIDequipo() + 1;
		return idEquipo;
	}

	private int DameNumeroELS() {
		int ELS = 0;
		String ubicacionDeBase = agenda.getUbicacionBase();

		if (ubicacionDeBase.compareTo("Buenos Aires") == 0) {
			ELS = agenda.dameNumeroELSbsas() + 1;
			if (ventanaAgregarEquipo != null) {
				ventanaAgregarEquipo.getGrupoEstadoFisico().setSelected(ventanaAgregarEquipo.getRdbtnCABA().getModel(),
						true);
			}

		} else if (ubicacionDeBase.compareTo("Bariloche") == 0) {
			ELS = agenda.dameNumeroELS() + 1;
			if (ventanaAgregarEquipo != null) {
				ventanaAgregarEquipo.getGrupoEstadoFisico().setSelected(ventanaAgregarEquipo.getRdbtnBRC().getModel(),
						true);
			}
		}
		return ELS;
	}

	private ReparacionDTO TomarDatosPantallaIngreso() {

		ReparacionDTO nuevoReparacion;

		int ELS = Integer.parseInt(this.ventanaAgregarEquipo.getTextELS());
		String falla = this.ventanaAgregarEquipo.getTextFalla().getText();
		String RemitoCLiente = this.ventanaAgregarEquipo.getTextRemitoCliente().getText();
		int IDEquipo = dameIDequipo();
		String NombreEquipo = ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString();
		String Modelo = ventanaAgregarEquipo.getComboModelo().getSelectedItem().toString();
		String Marca = ventanaAgregarEquipo.getComboMarca().getSelectedItem().toString();
		String Series = ventanaAgregarEquipo.getComboSerie().getSelectedItem().toString();
		String aviso = this.ventanaAgregarEquipo.getTextAvisoCliente().getText();
		String ClienteCliente = this.ventanaAgregarEquipo.getTextClienteCliente().getText();
		int idCliente = idCli;
		int idSucursal = idSuc;
		int idUsuarios = 1;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		fechaentrada = null;
		java.util.Date fechaEntrada = this.ventanaAgregarEquipo.getFechaEntrada().getDate();

		if (fechaEntrada != null) {

			fechaentrada = dateFormat.format(fechaEntrada);
		}

		if (this.ventanaAgregarEquipo.getTextFechafabricacion().getDate() == null) {

			fechaFarbricacion = null;

		} else {
			java.util.Date fechaFabr = this.ventanaAgregarEquipo.getTextFechafabricacion().getDate();
			fechaFarbricacion = dateFormat.format(fechaFabr);
		}
		Enumeration<?> elements = ventanaAgregarEquipo.getGrupoEstadoFisico().getElements();

		while (elements.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elements.nextElement();
			if (button.isSelected()) {

				estadoFisico = button.getText();
				lugarDeIngreso = button.getText();

			}
		}

		estadoTecnico = "Sin Revisar";
		estadocomercial = "A la Espera de Aceptación";

		if (verificarCaracteresPermitidos(NombreEquipo) || verificarCaracteresPermitidos(falla)
				|| verificarCaracteresPermitidos(Modelo) || verificarCaracteresPermitidos(Marca)
				|| verificarCaracteresPermitidos(Serie)) {

			nuevoReparacion = null;

		} else {

			nuevoReparacion = new ReparacionDTO(ELS, fechaentrada, falla, estadoFisico, estadoTecnico, estadocomercial,
					RemitoCLiente, IDEquipo, idUsuarios, NombreEquipo, Modelo, Marca, Series, aviso, ClienteCliente,
					idCliente, idSucursal, fechaFarbricacion, lugarDeIngreso);
		}
		return nuevoReparacion;

	}

	private ReparacionDTO TomarDatosVisualizacion(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		ReparacionDTO reparacionAeditar;

		int ELS = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());
		String falla = ventanaVisualizarEquipos.getTextFalla().getText();
		String solucion = ventanaVisualizarEquipos.getTextDiagnostico().getText();
		String informeCliente = ventanaVisualizarEquipos.getTextInformeCliente().getText();

		String RemitoCLiente = ventanaVisualizarEquipos.getTextRemitoCliente().getText();
		int IDEquipo = reparacion.getIDEquipo();
		int IDremito = reparacion.getidRemito();

		String NombreEquipo = ventanaVisualizarEquipos.getTextNombreEquipo().getText();
		String Modelo = ventanaVisualizarEquipos.getTextModelo().getText();
		String Marca = ventanaVisualizarEquipos.getTextMarca().getText();
		String Serie = ventanaVisualizarEquipos.getTextNSerie().getText();

		String aviso = ventanaVisualizarEquipos.getTextAvisoCliente().getText();
		String ClienteCliente = ventanaVisualizarEquipos.getTextClienteCliente().getText();

		if (!guardado) {
			ventanaVisualizarEquipos.getTextCliente()
					.setText(ventanaVisualizarEquipos.getComboClientes().getSelectedItem().toString());
			ventanaVisualizarEquipos.getTextSucursal()
					.setText(ventanaVisualizarEquipos.getComboSucursal().getSelectedItem().toString());
			ventanaVisualizarEquipos.getTextNombreTecnico()
					.setText(ventanaVisualizarEquipos.getComboTecnico().getSelectedItem().toString());
		}

		String Cliente = ventanaVisualizarEquipos.getTextCliente().getText();
		String Sucursal = ventanaVisualizarEquipos.getTextSucursal().getText();
		String nombreTecnico = ventanaVisualizarEquipos.getTextNombreTecnico().getText();

		int idCliente = IDClientePorNombre(Cliente);
		int idSucursal = IDSucursalPorNombre(Sucursal, idCliente);

		int idUsuario;
		int idUsuarioAux = IDUsuarioPorNombre(nombreTecnico);

		if (idUsuarioAux == 0) {

			idUsuario = 1;
		}

		else {
			idUsuario = idUsuarioAux;

		}

		String estadoFisico = ventanaVisualizarEquipos.getTextEstadoFisico().getText();
		String estadoTecnico = ventanaVisualizarEquipos.getTextEstadoTecnico().getText();
		String estadoComercial = ventanaVisualizarEquipos.getTextEstadoComercial().getText();
		String lugarIngreso = ventanaVisualizarEquipos.getTextLugarDeIngreso().getText();

		boolean enviado = false;

		boolean presupuestoGenerado = ventanaVisualizarEquipos.getChckPDFGenerado();
		boolean presupuestoEnviado = ventanaVisualizarEquipos.getChckPDFEnviado();
		boolean avisoEnviado = ventanaVisualizarEquipos.getChckbxAvisoEnviado();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		String fechaentradavisual = null;
		java.util.Date fechaEntradaVisual = ventanaVisualizarEquipos.getFechaEntrada().getDate();
		if (fechaEntradaVisual != null) {

			fechaentradavisual = dateFormat.format(fechaEntradaVisual);
		}

		String fechareparacionvisual = null;
		java.util.Date fechaReparacionVisual = ventanaVisualizarEquipos.getFechaReparacion().getDate();
		if (fechaReparacionVisual != null) {

			fechareparacionvisual = dateFormat.format(fechaReparacionVisual);
		}

		String fechaaceptacionvisual = null;
		java.util.Date fechaAceptacionVisual = ventanaVisualizarEquipos.getFechaRespuesta().getDate();
		if (fechaAceptacionVisual != null) {

			fechaaceptacionvisual = dateFormat.format(fechaAceptacionVisual);
		}

		String fechasalidaVisual = null;
		java.util.Date fechaSalidaVisual = ventanaVisualizarEquipos.getFechaSalida().getDate();
		if (fechaSalidaVisual != null) {

			fechasalidaVisual = dateFormat.format(fechaSalidaVisual);
		}

		String fechafabrvisual = null;
		java.util.Date fechaFabrvisual = ventanaVisualizarEquipos.getFechaFabr().getDate();
		if (fechaFabrvisual != null) {

			fechafabrvisual = dateFormat.format(fechaFabrvisual);
		}

		if (estadoFisico != "Enviado") {

			enviado = false;

		} else
			enviado = true;

		double presupuesto;
		double presupuestoDolar;
		double pago;

		if (monedaFormatter.tieneFormato(ventanaVisualizarEquipos.getTextPresupuesto().getText())) {

			presupuesto = monedaFormatter.parseAmountGuardar(ventanaVisualizarEquipos.getTextPresupuesto().getText());
			pago = monedaFormatter.parseAmountGuardar(ventanaVisualizarEquipos.getTextPago().getText());

		} else {

			presupuesto = monedaFormatter.parseAmount(ventanaVisualizarEquipos.getTextPresupuesto().getText());
			pago = monedaFormatter.parseAmount(ventanaVisualizarEquipos.getTextPago().getText());

			ventanaVisualizarEquipos.getTextPresupuesto()
					.setText(monedaFormatter.formatPeso(ventanaVisualizarEquipos.getTextPresupuesto().getText()));
			ventanaVisualizarEquipos.getTextPago()
					.setText(monedaFormatter.formatPeso(ventanaVisualizarEquipos.getTextPago().getText()));

		}

		if (monedaFormatter.tieneFormato(ventanaVisualizarEquipos.getTextPresupuestoDolar().getText())) {

			presupuestoDolar = monedaFormatter
					.parseAmountGuardar(ventanaVisualizarEquipos.getTextPresupuestoDolar().getText());

		} else {

			presupuestoDolar = monedaFormatter
					.parseAmount(ventanaVisualizarEquipos.getTextPresupuestoDolar().getText());

			ventanaVisualizarEquipos.getTextPresupuestoDolar()
					.setText(monedaFormatter.formatDolar(ventanaVisualizarEquipos.getTextPresupuestoDolar().getText()));

		}

		String OrdenDeCompra = ventanaVisualizarEquipos.getTextOC().getText();

		boolean wordGenerado = ventanaVisualizarEquipos.getChckWORDGenerado();
		boolean wordEnviado = ventanaVisualizarEquipos.getChckWORDEnviado();

		boolean agregadoAremito = reparacion.getAgregadoaremito();
		boolean remitoGenerado = reparacion.getRemitoGenerado();

		// Validar campos para evitar inyección de código
		// Si algún campo no es válido, setear reparacionAeditar a null

		if (verificarCaracteresPermitidos(falla) || verificarCaracteresPermitidos(solucion)
				|| verificarCaracteresPermitidos(informeCliente) || verificarCaracteresPermitidos(NombreEquipo)
				|| verificarCaracteresPermitidos(Modelo) || verificarCaracteresPermitidos(Marca)
				|| verificarCaracteresPermitidos(Serie)) {

			reparacionAeditar = null;

		} else {

			reparacionAeditar = new ReparacionDTO(ELS, fechaentradavisual, fechareparacionvisual, falla, solucion,
					informeCliente, estadoFisico, estadoTecnico, estadoComercial, RemitoCLiente, IDEquipo, IDremito,
					Cliente, Sucursal, fechaaceptacionvisual, NombreEquipo, Modelo, Marca, Serie, aviso, ClienteCliente,
					idCliente, idSucursal, fechafabrvisual, idUsuario, nombreTecnico, presupuesto, presupuestoDolar,
					pago, presupuestoGenerado, avisoEnviado, presupuestoEnviado, wordGenerado, wordEnviado,
					OrdenDeCompra, agregadoAremito, remitoGenerado, lugarIngreso, fechasalidaVisual);
		}
		return reparacionAeditar;

	}

	private RegistroEntradaReporteDTO TomarDatosPantallaVisualizacion() {

		int ELS = Integer.parseInt(this.ventanaVisualizarEquipos.getTextELS());
		String falla = this.ventanaVisualizarEquipos.getTextFalla().getText();
		String RemitoCLiente = this.ventanaVisualizarEquipos.getTextRemitoCliente().getText();
		int IDEquipo = dameIDequipo();
		String NombreEquipo = this.ventanaVisualizarEquipos.getTextNombreEquipo().getText();
		String Modelo = this.ventanaVisualizarEquipos.getTextModelo().getText();
		String Marca = this.ventanaVisualizarEquipos.getTextMarca().getText();
		String Serie = this.ventanaVisualizarEquipos.getTextNSerie().getText();
		String aviso = this.ventanaVisualizarEquipos.getTextAvisoCliente().getText();
		String ClienteCliente = this.ventanaVisualizarEquipos.getTextClienteCliente().getText();
		int idCliente = idCli;
		int idSucursal = idSuc;
		String Cliente = this.ventanaVisualizarEquipos.getTextCliente().getText();
		String Sucursal = this.ventanaVisualizarEquipos.getTextSucursal().getText();

		java.util.Date fechaEntradaVisual = this.ventanaVisualizarEquipos.getFechaEntrada().getDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		fechaentrada = dateFormat.format(fechaEntradaVisual);

		RegistroEntradaReporteDTO nuevoReparacion = new RegistroEntradaReporteDTO(ELS, fechaentrada, falla,
				estadoFisico, estadoTecnico, RemitoCLiente, IDEquipo, NombreEquipo, Modelo, Marca, Serie, aviso,
				ClienteCliente, idCliente, idSucursal, Cliente, Sucursal);

		return nuevoReparacion;

	}

	@SuppressWarnings("unused")
	public void mouseClicked(MouseEvent arg0) {

		repuestoSeleccionado(ventanaVisualizarEquipos, arg0);

		if (this.ventanaClientesWSP != null) {
			if (arg0.getSource() == this.ventanaClientesWSP.getTablaClienteSWSP()) {
				int i = this.ventanaClientesWSP.getTablaClienteSWSP().getSelectedRow();
				if (i != -1) {
					if (!clientesWSP_en_tabla.isEmpty()) {
						clienteWSP_Elegido = clientesWSP_en_tabla.get(i);
						int indiceRol = clienteWSP_Elegido.getIdClienteWSP() - 2;
						// this.ventanaClientesWSP.getComboRoles().setSelectedIndex(indiceRol);
						this.ventanaClientesWSP.getTxtNombre().setText(clienteWSP_Elegido.getNombreWSP());
						this.ventanaClientesWSP.getTxtOrganizacion().setText(clienteWSP_Elegido.getOrganizacion());
						this.ventanaClientesWSP.getTxtTelefono().setText(clienteWSP_Elegido.getTelefonoWSP());

						this.ventanaClientesWSP.getBtnGuardarEdicion().setVisible(false);
						this.ventanaClientesWSP.getBtnCancelarEdicion().setVisible(false);
						this.ventanaClientesWSP.getBtnGuardarNuevo().setVisible(false);
						this.ventanaClientesWSP.getBtnCancelarNuevo().setVisible(false);

						this.ventanaClientesWSP.getBtnEliminarCliente().setEnabled(true);
						this.ventanaClientesWSP.getBtnEditarCliente().setEnabled(true);
						this.ventanaClientesWSP.getBtnAgregarCliente().setEnabled(true);

						this.ventanaClientesWSP.getTxtNombre().setEditable(false);
						this.ventanaClientesWSP.getTxtOrganizacion().setEditable(false);
						this.ventanaClientesWSP.getTxtTelefono().setEditable(false);

					}
				}
			}
		}

	}

	private void repuestoSeleccionado(VentanaVisualizarEquipos ventanaVisualizarEquipos, MouseEvent arg0) {

		if (ventanaVisualizarEquipos != null) {
			if (arg0.getSource() == ventanaVisualizarEquipos.getTablaRepuestos()) {
				int i = ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
				if (i != -1) {
					if (!Repuestos_en_tabla.isEmpty()) {
						repuestoElegido = Repuestos_en_tabla.get(i);

					}
				}
			}
		}

	}

	private void llenarComboELS() {

		agenda.ListarELS(ventanaVerificarIngresoAnterior.getComboFiltroELS());

		ventanaVerificarIngresoAnterior.getComboFiltroELS().setSelectedIndex(-1);

	}

	private void llenarComboELSvisualizacion() {

		agenda.ListarELS(ventanaVisualizarEquipos.getComboELS());

		ventanaVisualizarEquipos.getComboELS().setSelectedIndex(-1);

	}

	private boolean verificacionDatosIngreso() {
		boolean salida = false;

		if (idCli == 0) {
			Object mje = "'CLIENTE'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		} else if (ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem() == null
				|| ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString().trim().isEmpty()) {
			Object mje = "'NOMBRE DE EQUIPO'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		} else if (ventanaAgregarEquipo.getComboModelo().getSelectedItem() == null
				|| ventanaAgregarEquipo.getComboModelo().getSelectedItem().toString().trim().isEmpty()) {
			Object mje = "'MODELO'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		} else if (ventanaAgregarEquipo.getComboSerie().getSelectedItem() == null
				|| ventanaAgregarEquipo.getComboSerie().getSelectedItem().toString().trim().isEmpty()) {
			Object mje = "'SERIE'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		} else if (ventanaAgregarEquipo.getComboMarca().getSelectedItem() == null
				|| ventanaAgregarEquipo.getComboMarca().getSelectedItem().toString().trim().isEmpty()) {
			Object mje = "'MARCA'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		} else if (ventanaAgregarEquipo.getComboClientes().getSelectedItem() == null
				|| ventanaAgregarEquipo.getComboClientes().getSelectedItem().toString().trim().isEmpty()) {
			Object mje = "'CLIENTE'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (ventanaAgregarEquipo.getComboClientes().getSelectedItem().toString().compareTo("Siemens SA") == 0
				&& ventanaAgregarEquipo.getTextFechafabricacion().getDate() == null) {
			Object mje = "'FECHA DE FABRICACIÓN'. Campo obligatorio para Siemens SA.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
		} else {
			salida = true;
		}

		return salida;
	}

	private static void habilitarMenuContextual(Object componente) {
		final JTextComponent editor;

		if (componente instanceof JComboBox) {
			JComboBox<?> comboBox = (JComboBox<?>) componente;
			if (!comboBox.isEditable())
				return;
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

		copiar.addActionListener(e -> editor.copy());
		pegar.addActionListener(e -> editor.paste());

		menu.add(copiar);
		menu.add(pegar);

		menu.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				menu.setVisible(false);
			}
		});

		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					showMenu(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				menu.show(editor, e.getX(), e.getY());
			}
		});
	}

	private void llenarComboSeries() {

		agenda.ListarSerie(ventanaVerificarIngresoAnterior.getComboSerie());

		ventanaVerificarIngresoAnterior.getComboSerie().setSelectedIndex(-1);

	}

	private boolean validacionMail(String email) {

		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean verificarCaracteresPermitidos(String texto) {
		caracteresNoValidosEncontrados.clear();

		// Comillas simples
		if (texto.contains("'"))
			caracteresNoValidosEncontrados.add("'");

		// Letras griegas (incluye Omega, mayúscula y minúscula)
		Pattern patronGriego = Pattern.compile("[\\u0370-\\u03FF\\u1F00-\\u1FFF]", Pattern.UNICODE_CASE);
		Matcher matcher = patronGriego.matcher(texto);
		while (matcher.find()) {
			String caracter = matcher.group();
			if (!caracteresNoValidosEncontrados.contains(caracter)) {
				caracteresNoValidosEncontrados.add(caracter);
			}
		}

		// Símbolos peligrosos para SQL
//	    String[] simbolosNoPermitidos = {";", "\"", "\\", "%", "/*", "*/"};
		String[] simbolosNoPermitidos = { ";", "\\", "/*", "*/" };
		for (String simbolo : simbolosNoPermitidos) {
			if (texto.contains(simbolo) && !caracteresNoValidosEncontrados.contains(simbolo)) {
				caracteresNoValidosEncontrados.add(simbolo);
			}
		}

		return !caracteresNoValidosEncontrados.isEmpty();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

		habilitarEdicionRepuestos(ventanaVisualizarEquipos, e);

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	private void habilitarEdicionRepuestos(VentanaVisualizarEquipos ventanaVisualizarEquipos, KeyEvent e) {

		if (ventanaVisualizarEquipos != null) {

			if (e.getSource() == ventanaVisualizarEquipos.getTablaRepuestos()) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					Object mje = "Deberá 'GUARDAR EDICIÓN' para mantener las modificaciones.";
					JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

					ventanaVisualizarEquipos.getBtnEditarRepuesto().setEnabled(true);

				}

			}
		}

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

	@SuppressWarnings({ "serial", "deprecation" })
	private static void configureUndoManager(JTextComponent textComponent) {
		UndoManager undoManager = new UndoManager();
		textComponent.getDocument().addUndoableEditListener(undoManager);

		// Crear una acción de deshacer
		AbstractAction undoAction = new AbstractAction("Deshacer") {
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canUndo()) {
					undoManager.undo();
				}
			}
		};

		// Asignar la tecla de acceso directo (Ctrl + Z) para la acción de deshacer
		undoAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Agregar la acción de deshacer al componente
		textComponent.getActionMap().put("Undo", undoAction);
		textComponent.getInputMap().put((KeyStroke) undoAction.getValue(Action.ACCELERATOR_KEY), "Undo");

		// Crear una acción de rehacer
		AbstractAction redoAction = new AbstractAction("Rehacer") {
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canRedo()) {
					undoManager.redo();
				}
			}
		};

		// Asignar la tecla de acceso directo (Ctrl + Y) para la acción de rehacer
		redoAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Agregar la acción de rehacer al componente
		textComponent.getActionMap().put("Redo", redoAction);
		textComponent.getInputMap().put((KeyStroke) redoAction.getValue(Action.ACCELERATOR_KEY), "Redo");
	}

	public void cerraVentanaVisualizarEquipo() {

		this.ventanaVisualizarEquipos.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {

				if (guardado == false) {
					int opcionGuardar = JOptionPane.showConfirmDialog(null,
							"Hay cambios sin guardar. ¿Desea guardar antes de salir?", "Aviso",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if (opcionGuardar == JOptionPane.YES_OPTION) {
						ventanaVisualizarEquipos.getBtnGuardarCambios().doClick();
					} else if (opcionGuardar == JOptionPane.CANCEL_OPTION) {
						return; // Cancelar el cierre de la ventana
					}
				}

				ventanaVisualizarEquipos.dispose();
				ventanaVisualizarEquipos = null;

				if (ventanaBusquedaEquipo != null) {
					ventanaBusquedaEquipo.dispose();
					ventanaBusquedaEquipo = null;
				}

			}

		});

	}

	public void cerraVentanaVisualizarEquipoListado(VentanaVisualizarEquipos ventana) {

		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {

				if (guardado == false) {
					int opcionGuardar = JOptionPane.showConfirmDialog(null,
							"Hay cambios sin guardar. ¿Desea guardar antes de salir?", "Aviso",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if (opcionGuardar == JOptionPane.YES_OPTION) {
						ventana.getBtnGuardarCambios().doClick();
					} else if (opcionGuardar == JOptionPane.CANCEL_OPTION) {
						return; // Cancelar el cierre de la ventana
					}
				}

				ventanasAbiertas.remove(ventana);

				ventana.dispose();

				if (ventanasAbiertas.size() == 0) {

					// System.out.println("no hay mas ventanas abietas");
					actualizarEnlistado = false;
				}

			}
		});

	}

	public List<VentanaVisualizarEquipos> getVentanasAbiertas() {
		return ventanasAbiertas;
	}

	public void setVentanasAbiertas(List<VentanaVisualizarEquipos> ventanasAbiertas) {
		this.ventanasAbiertas = ventanasAbiertas;
	}

}
