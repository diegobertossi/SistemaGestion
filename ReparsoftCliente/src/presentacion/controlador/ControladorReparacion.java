package presentacion.controlador;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.inet.jortho.SpellChecker;

//import com.sun.xml.internal.org.jvnet.fastinfoset.sax.ExtendedContentHandler;

import modelo.Agenda;
import presentacion.reportes.ReporteRegistroEntrada;

import presentacion.vista.VentanaAgregarEquipo;
import presentacion.vista.VentanaAgregarRepuesto;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaEstados;

import presentacion.vista.VentanaVerificarIngresoAnterior;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaWSP;
import tiposPropios.MonedaFormatter;
import presentacion.vista.VentanaClientesWSP;

import presentacion.vista.VentanaEnviarCorreoOwsp;
import dto.ClienteDTO;
import dto.ClienteWSPDTO;

import dto.RegistroEntradaReporteDTO;

import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.SucursalDTO;

import java.security.SecureRandom;
import java.math.BigInteger;

public class ControladorReparacion implements ActionListener, MouseListener, KeyListener, ItemListener {

	private VentanaVisualizarEquipos ventanaVisualizarEquipos;
	private VentanaEquipos ventanaEquipos;

	private VentanaAgregarRepuesto ventanaagregarRepuesto;

	private VentanaEnviarCorreoOwsp ventanaEnviarCorreoOwsp;
	private VentanaAgregarEquipo ventanaAgregarEquipo;
	private VentanaEstados ventanaEstados;
	private VentanaVerificarIngresoAnterior ventanaVerificarIngresoAnterior;
	private VentanaWSP ventanaWSP;
	private VentanaClientesWSP ventanaClientesWSP;

	private ControladorUsuLogin controladorUsuLogin;
	private ControladorPresupuestos controladorpresupuestos;
	private ControladorSalidas controladorSalidas;
	private ControladorCliente controladorCliente;

	private int NumeroELSSeleccionado;

	private List<RepuestosDTO> Repuestos_en_tabla;

	private List<ClienteWSPDTO> clientesWSP_en_tabla;
	private ClienteWSPDTO clienteWSP_Elegido;

	private ClienteDTO Cliente;
	private SucursalDTO Sucursal;

	private RepuestosDTO repuestoElegido;
	boolean guardado = true;

	private Agenda agenda;
	private int ELSinicial = 1;
	private int ELS = 1;

	private ReparacionDTO reparacion;
	private int NumeroELS;
	private int NumeroELSParaRemito;

	private String estadoFisico = "";
	private String estadoTecnico = "";
	private String estadocomercial = "";
	private String NombreEq = "";
	private String Marca = "";
	private String Modelo = "";
	private String Serie = "";
	private String ContactoWSP = "";
	private String nombreBuscado = "";

	private String numeros = "";
	private String part1;
	private String part2;

	private int idCli;
	private int idSuc;

	private String fechaentrada;
	private String fechaFarbricacion;

	private MonedaFormatter monedaFormatter;

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

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

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventanaEquipos.getBtnVisualizarEquipos()) {

			int ELS = DameNumeroELS() - 1;

			if (ELS < 1) {

				Object mje = "No se ha ingresado ningún equipo.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else {

				ventanaVisualizarEquipos = new VentanaVisualizarEquipos(this);
				cerraVentanaVisualizarEquipo();
				monedaFormatter = new MonedaFormatter();

				controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);

				SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

				try {
					TomarDatosDeTablas();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}

				this.ventanaEquipos.dispose();

				agregarListenersVentanaVisualizarEquipos();

				llenarComboELSvisualizacion();

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
			int eleccion = JOptionPane.YES_OPTION;

			if (!guardado) {
				eleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,

						"No se han guardado los cambios. Si continua se descartarán los mismos. ¿Desea continuar?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			}
			if (eleccion == JOptionPane.YES_OPTION) {

				int tam = agenda.obtenerReparacion().size();

				if (ELSinicial < tam) {
					ELSinicial = ELSinicial + 1;
					try {
						TomarDatosDeTablas();

					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Object mje = "No hay más reparaciones ";
					JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
				}

				guardado = true;
			}
		} else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonAnterior()) {

			int eleccion = JOptionPane.YES_OPTION;

			if (!guardado) {
				eleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,

						"No se han guardado los cambios. Si continua se descartarán los mismos. ¿Desea continuar?",

						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			}
			if (eleccion == JOptionPane.YES_OPTION) {

				if (ELSinicial > 1) {
					ELSinicial = ELSinicial - 1;
					try {
						TomarDatosDeTablas();

					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				guardado = true;
			}
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonUltimo()) {

			int eleccion = JOptionPane.YES_OPTION;

			if (!guardado) {
				eleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,

						"No se han guardado los cambios. Si continua se descartarán los mismos. ¿Desea continuar?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			}
			if (eleccion == JOptionPane.YES_OPTION) {

				ELSinicial = agenda.obtenerReparacion().size();
				try {
					TomarDatosDeTablas();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				guardado = true;
			}
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonPrimero()) {

			int eleccion = JOptionPane.YES_OPTION;

			if (!guardado) {
				eleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,

						"No se han guardado los cambios. Si continua se descartarán los mismos. ¿Desea continuar?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			}
			if (eleccion == JOptionPane.YES_OPTION) {

				ELSinicial = 1;
				try {
					TomarDatosDeTablas();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				guardado = true;
			}
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonAvisoInforme()) {

			String correo = "diego.bertossi@elsweb.com.ar";
			String ELS = ventanaVisualizarEquipos.getTextELS().toString();
			String Cliente = ventanaVisualizarEquipos.getTextCliente().getText();
			String Sucursal = ventanaVisualizarEquipos.getTextSucursal().getText();

			int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,

					"¿Desea enviar el aviso  a " + correo + " ?", "Confirmación", JOptionPane.YES_NO_OPTION,

					JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				if (mails.EnviarMail.enviarAvisoInforme(correo, ELS, Cliente, Sucursal)) {

					ventanaVisualizarEquipos.setChckbxAvisoEnviado(true);
					ReparacionDTO reparacionAeditar = TomarDatosVisualizacion();
					this.agenda.editarReparacionR(reparacionAeditar);

				}

			}

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonAvisoEquipoListo()) {

			String correo = "diego.bertossi@elsweb.com.ar";

			String ELS = ventanaVisualizarEquipos.getTextELS().toString();
			String Cliente = ventanaVisualizarEquipos.getTextCliente().getText();
			String Sucursal = ventanaVisualizarEquipos.getTextSucursal().getText();

			int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,

					"¿Desea enviar el aviso de 'Equipo Terminado' a " + correo + " ?", "Confirmación",

					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				mails.EnviarMail.enviarAvisoEquipoTerminado(correo, ELS, Cliente, Sucursal);

			}

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonRespuestaAlTecnico()) {

			String correo = reparacion.getCorreo();
			String ELS = ventanaVisualizarEquipos.getTextELS().toString();
			String Cliente = ventanaVisualizarEquipos.getTextCliente().getText();
			String Sucursal = ventanaVisualizarEquipos.getTextSucursal().getText();
			String EstadoComercial = reparacion.getEstadoComercial();

			int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
					"Desea enviar el aviso de 'Respuesta del Cliente' a " + correo + " ?", "ConfirmaciÃ³n",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				mails.EnviarMail.enviarAvisoRespuestaCliente(correo, ELS, Cliente, Sucursal, EstadoComercial);

			}

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnEditar()) {

			String nombreCliente = "";
			String nombreSucursal = "";

			llenarComboClienteV();
			llenarComboTecnico();

			habilitarCampos();
			guardado = false;

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonRegistroIngreso()) {

			List<RegistroEntradaReporteDTO> lista = new ArrayList<RegistroEntradaReporteDTO>();

			RegistroEntradaReporteDTO rep = TomarDatosPantallaVisualizacion();

			lista.add(rep);

			ReporteRegistroEntrada reporte = new ReporteRegistroEntrada(rep, lista);
			reporte.mostrar();

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonRefrescarPantalla()) {

			try {
				TomarDatosDeTablas();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnGuardarCambios()) {

			ReparacionDTO reparacionAeditar = TomarDatosVisualizacion();
			this.agenda.editarReparacionR(reparacionAeditar);

			guardado = true;

			deshabilitarCampos();

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonEditarEstados()) {

			ventanaEstados = new VentanaEstados(this);

			Enumeration elementsF = ventanaEstados.getGrupoEstadoFisico().getElements();

			while (elementsF.hasMoreElements()) {
				AbstractButton button = (AbstractButton) elementsF.nextElement();
				if (button.getText()
						.compareToIgnoreCase(ventanaVisualizarEquipos.getTextEstadoFisico().getText()) == 0) {

					button.setSelected(true);

				}
			}

			Enumeration elementsT = ventanaEstados.getGrupoEstadoTecnico().getElements();

			while (elementsT.hasMoreElements()) {
				AbstractButton button = (AbstractButton) elementsT.nextElement();
				if (button.getText()
						.compareToIgnoreCase(ventanaVisualizarEquipos.getTextEstadoTecnico().getText()) == 0) {

					button.setSelected(true);

				}
			}

			Enumeration elementsC = ventanaEstados.getGrupoEstadoComercial().getElements();

			while (elementsC.hasMoreElements()) {
				AbstractButton button = (AbstractButton) elementsC.nextElement();
				if (button.getText()
						.compareToIgnoreCase(ventanaVisualizarEquipos.getTextEstadoComercial().getText()) == 0) {

					button.setSelected(true);

				}
			}

			ventanaEstados.getBtnAceptarEdicion().addActionListener(this);

		}

		else if (this.ventanaEstados != null && e.getSource() == this.ventanaEstados.getBtnAceptarEdicion()) {

			String estadoFisico = "";
			String estadoTecnico = "";
			String estadoComercial = "";

			Boolean cambioDeEstadoBoolean = false;

			Enumeration elementsF = ventanaEstados.getGrupoEstadoFisico().getElements();

			while (elementsF.hasMoreElements()) {
				AbstractButton button = (AbstractButton) elementsF.nextElement();
				if (button.isSelected()) {

					estadoFisico = button.getText();

				}
			}

			Enumeration elementsT = ventanaEstados.getGrupoEstadoTecnico().getElements();

			while (elementsT.hasMoreElements()) {
				AbstractButton button = (AbstractButton) elementsT.nextElement();
				if (button.isSelected()) {

					estadoTecnico = button.getText();

				}
			}

			Enumeration elementsC = ventanaEstados.getGrupoEstadoComercial().getElements();

			while (elementsC.hasMoreElements()) {
				AbstractButton button = (AbstractButton) elementsC.nextElement();
				if (button.isSelected()) {

					estadoComercial = button.getText();

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

				int seleccion = JOptionPane.showConfirmDialog(ventanaEstados,
						"EL ESTADO FÍSICO A CAMBIADO, ¿DESEA CONTINUAR?", "Confirmación", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (seleccion == JOptionPane.YES_OPTION) {
					ventanaVisualizarEquipos.setTextEstadoFisico(estadoFisico);

				}

			}

			if (ventanaVisualizarEquipos.getTextEstadoTecnico().getText().compareTo(estadoTecnico) != 0) {

				cambioDeEstadoBoolean = true;

				int seleccion = JOptionPane.showConfirmDialog(ventanaEstados,
						"EL ESTADO TÉCNICO A CAMBIADO, SE MODIFICARÁ LA FECHA DE DIAGNÓSTICO. ¿DESEA CONTINUAR?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (seleccion == JOptionPane.YES_OPTION) {
					ventanaVisualizarEquipos.setTextEstadoTecnico(estadoTecnico);

					if (estadoTecnico == "Sin Revisar") {

						ventanaVisualizarEquipos.getFechaReparacion().setDate(null);

					}

					else {

						ventanaVisualizarEquipos.getFechaReparacion().setDate(fechaParseadaHOY);

					}

				}

			}

			if (ventanaVisualizarEquipos.getTextEstadoComercial().getText().compareTo(estadoComercial) != 0) {

				cambioDeEstadoBoolean = true;

				int seleccion = JOptionPane.showConfirmDialog(ventanaEstados,
						"EL ESTADO COMERCIAL A CAMBIADO, SE MODIFICARÁ LA FECHA DE ACEPTACIÓN. ¿DESEA CONTINUAR?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (seleccion == JOptionPane.YES_OPTION) {
					ventanaVisualizarEquipos.setTextEstadoComercial(estadoComercial);

					if (estadoComercial == "A la Espera de Aceptación") {

						ventanaVisualizarEquipos.getFechaRespuesta().setDate(null);

					}

					else {

						ventanaVisualizarEquipos.getFechaRespuesta().setDate(fechaParseadaHOY);

					}

				}

			}

			if (cambioDeEstadoBoolean) {
				Object mje = "Deberá 'GUARDAR CAMBIOS' para mantener las modificaciones.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
			} else {
				Object mje = "No se realizó ningún cambio de estado.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
			}

			this.ventanaEstados.dispose();
			this.ventanaEstados = null;

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBotonPresupuestar()) {

			if (ventanaVisualizarEquipos.getBtnGuardarCambios().isEnabled()) {

				Object mje = "Debe guardar los cambios realizados para poder presupuestar.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else {

				NumeroELS = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());
				controladorpresupuestos.TomarDatosDeTablasParaVisualizacion(NumeroELS);
				controladorpresupuestos.agregarListenersVentanaGenerarPresupuesto();
			}
		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnenviarCorreoOwsp()) {

			ventanaEnviarCorreoOwsp = new VentanaEnviarCorreoOwsp(this);

			ventanaEnviarCorreoOwsp.getBtnEnviarWST().addActionListener(this);

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

				agregarListenersVentanaVisualizarEquipos();

			}
		}

		else if (this.ventanaEnviarCorreoOwsp != null
				&& e.getSource() == this.ventanaEnviarCorreoOwsp.getBtnEnviarWST()) {

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

			ventanaagregarRepuesto = new VentanaAgregarRepuesto(this);
			this.ventanaagregarRepuesto.getBtnAgregarRepuesto().addActionListener(this);
			this.ventanaagregarRepuesto.getBtnCancelar().addActionListener(this);

		}

		else if (this.ventanaagregarRepuesto != null
				&& e.getSource() == this.ventanaagregarRepuesto.getBtnAgregarRepuesto()) {

			RepuestosDTO nuevoRepuesto = TomarDatosRepuesto();
			this.agenda.agregarRepuesto(nuevoRepuesto);

			this.ventanaagregarRepuesto.dispose();
			this.ventanaagregarRepuesto = null;

			llenarTablaRepuestos();

		}

		else if (this.ventanaagregarRepuesto != null && e.getSource() == this.ventanaagregarRepuesto.getBtnCancelar()) {

			this.ventanaagregarRepuesto.dispose();
			this.ventanaagregarRepuesto = null;

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnEditarRepuesto()) {

			if (repuestoElegido == null) {
				this.ventanaVisualizarEquipos.getErrorMsj("Seleccione un Repuesto");

			} else {

				int i = this.ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
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
				this.ventanaVisualizarEquipos.getBtnEditarRepuesto().setEnabled(false);

				agenda.editarRepuesto(repuestoElegido);

				llenarTablaRepuestos();
				repuestoElegido = null;

			}
		} else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnEliminarRepuesto()) {

			if (repuestoElegido == null) {
				this.ventanaVisualizarEquipos.getErrorMsj("Seleccione un Repuesto");

			}

			else {
				int seleccion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
						"¿Está seguro de realizar la operación?", "Confirmación", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (seleccion == JOptionPane.YES_OPTION) {

					int[] filas_seleccionadas = this.ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRows();
					for (int fila : filas_seleccionadas) {
						agenda.borraRepuesto(Repuestos_en_tabla.get(fila));
					}

					llenarTablaRepuestos();
					repuestoElegido = null;

				}
			}

		}

		else if (this.ventanaVisualizarEquipos != null
				&& e.getSource() == this.ventanaVisualizarEquipos.getBtnGenerarRemito()) {

			NumeroELSParaRemito = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());

			controladorSalidas.cargarRemitoVisualizacion(NumeroELSParaRemito);
			controladorSalidas.agregarListenersVentanaRemitos();

		}

		else if (this.ventanaAgregarEquipo != null && e.getSource() == this.ventanaAgregarEquipo.getBtnaltaCliente()) {

			controladorCliente.agregarListenersVentanaAgregarCliente();
			// controladorCliente.llenarTabla();

		}

		else if (this.ventanaAgregarEquipo != null
				&& e.getSource() == this.ventanaAgregarEquipo.getBtnrecargarLista()) {

			llenarComboCliente();

		}

		else if (this.ventanaAgregarEquipo != null
				&& e.getSource() == this.ventanaAgregarEquipo.getBotonGenerarRegistro()) {

			if (verificacionDatosIngreso()) {

				List<RegistroEntradaReporteDTO> lista = new ArrayList<RegistroEntradaReporteDTO>();

				RegistroEntradaReporteDTO rep = TomarDatosPantallaIngresoRep();

				lista.add(rep);

				ReporteRegistroEntrada reporte = new ReporteRegistroEntrada(rep, lista);
				reporte.mostrar();

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
						this.agenda.agregarReparacionR(nuevoReparacion);

						ventanaAgregarEquipo.getTextAvisoCliente().setEditable(false);
						ventanaAgregarEquipo.getTextClienteCliente().setEditable(false);
						ventanaAgregarEquipo.getTextFalla().setEditable(false);
						ventanaAgregarEquipo.getTextRemitoCliente().setEditable(false);
						ventanaAgregarEquipo.getTextFechafabricacion().setEditable(false);
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
						ventanaAgregarEquipo.getRdbtnENVIADO().setEnabled(false);
						ventanaAgregarEquipo.getRdbtnMDQ().setEnabled(false);
						ventanaAgregarEquipo.getBtnFechaDefault().setEnabled(false);
						ventanaAgregarEquipo.getBtnGenerarSerie().setEnabled(false);
						ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(true);
						ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().setEnabled(false);

						Object mje = "Equipo Guardado. Solo se podrá modificar desde la visualización";

						JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo",
								JOptionPane.INFORMATION_MESSAGE);

						break;
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
			ventanaAgregarEquipo.getTextClienteCliente().setEditable(true);
			ventanaAgregarEquipo.getTextFalla().setEditable(true);
			ventanaAgregarEquipo.getTextFalla().setEnabled(true);
			ventanaAgregarEquipo.getTextRemitoCliente().setEditable(true);
			ventanaAgregarEquipo.getComboClientes().setEnabled(true);
			ventanaAgregarEquipo.getComboSucursal().setEnabled(true);
			ventanaAgregarEquipo.getComboMarca().setEnabled(true);
			ventanaAgregarEquipo.getComboNombreEquipo().setEnabled(true);
			ventanaAgregarEquipo.getComboModelo().setEnabled(true);
			ventanaAgregarEquipo.getComboSerie().setEnabled(true);
			ventanaAgregarEquipo.getFechaEntrada().setEnabled(true);
			ventanaAgregarEquipo.getTextFechafabricacion().setEditable(true);
			ventanaAgregarEquipo.getTextFechafabricacion().setEnabled(true);

			ventanaAgregarEquipo.getRdbtnBRC().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnCABA().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnENVIADO().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnMDQ().setEnabled(true);
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

			ventanaAgregarEquipo.getRdbtnBRC().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnCABA().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnENVIADO().setEnabled(true);
			ventanaAgregarEquipo.getRdbtnMDQ().setEnabled(true);

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

	public void agregarListenersVentanaVisualizarEquipos() {

		this.ventanaVisualizarEquipos.getBotonAnterior().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonSiguiente().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonUltimo().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonPrimero().addActionListener(this);
		this.ventanaVisualizarEquipos.getBtnGuardarCambios().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonRegistroIngreso().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonEditarEstados().addActionListener(this);
		this.ventanaVisualizarEquipos.getBtnEditar().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonAvisoInforme().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonAvisoEquipoListo().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonRespuestaAlTecnico().addActionListener(this);
		this.ventanaVisualizarEquipos.getBtnGenerarRemito().addActionListener(this);
		this.ventanaVisualizarEquipos.getBotonPresupuestar().addActionListener(this);
		this.ventanaVisualizarEquipos.getBtnenviarCorreoOwsp().addActionListener(this);
		this.ventanaVisualizarEquipos.getComboClientes().addActionListener(this);
		this.ventanaVisualizarEquipos.getComboSucursal().addActionListener(this);
		this.ventanaVisualizarEquipos.getComboTecnico().addActionListener(this);
		this.ventanaVisualizarEquipos.getBtnRepuestos().addActionListener(this);
		this.ventanaVisualizarEquipos.getBtnEditarRepuesto().addActionListener(this);
		this.ventanaVisualizarEquipos.getBtnEliminarRepuesto().addActionListener(this);
		this.ventanaVisualizarEquipos.getTablaRepuestos().addMouseListener(this);
		this.ventanaVisualizarEquipos.getTablaRepuestos().addKeyListener(this);
		this.ventanaVisualizarEquipos.getBotonRefrescarPantalla().addActionListener(this);

		this.ventanaVisualizarEquipos.getTextPresupuesto().addKeyListener(this);
		this.ventanaVisualizarEquipos.getBtnBuscarELS().addActionListener(this);
		this.ventanaVisualizarEquipos.getComboELS().addActionListener(this);
		llenarComboELSvisualizacion();
		AutoCompleteDecorator.decorate(ventanaVisualizarEquipos.getComboELS());

		this.ventanaVisualizarEquipos.getTextPresupuesto().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				SwingUtilities.invokeLater(() -> {
					ventanaVisualizarEquipos.getTextPresupuesto().selectAll();
				});
			}
		});

		this.ventanaVisualizarEquipos.getTextPago().addKeyListener(this);
		this.ventanaVisualizarEquipos.getTextPago().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent arg0) {

				SwingUtilities.invokeLater(() -> {
					ventanaVisualizarEquipos.getTextPago().selectAll();
				});

			}
		});

		
		
//		ventanaVisualizarEquipos.getTextPresupuesto().addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                SwingUtilities.invokeLater(() -> {
//                	
//                	
//                	ventanaVisualizarEquipos.getTextPresupuesto().setText(""); // Borra el texto al presionar cualquier tecla
//                });
//            }
//        });
		
		
		
		ventanaVisualizarEquipos.getTextPresupuesto().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String presupuesto = ventanaVisualizarEquipos.getTextPresupuesto().getText();
				ventanaVisualizarEquipos.getTextPresupuesto().setText(monedaFormatter.formatPeso(presupuesto));
				verificarPresupuestoEditado();

			}
		});

		ventanaVisualizarEquipos.getTextPago().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String pago = ventanaVisualizarEquipos.getTextPago().getText();
				ventanaVisualizarEquipos.getTextPago().setText(monedaFormatter.formatPeso(pago));
				verificarPresupuestoEditado();

			}
		});

	}

	public void agregarListenersVentanaVisualizarEquiposListado() {

		agregarListenersVentanaVisualizarEquipos();
		this.ventanaVisualizarEquipos.getBotonAnterior().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonSiguiente().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonUltimo().removeActionListener(this);
		this.ventanaVisualizarEquipos.getBotonPrimero().removeActionListener(this);

	}

	public void agregarListenersVentanaAgregarEquipos() {

		ventanaAgregarEquipo.setTextELS(Integer.toString(ELS));
		ventanaAgregarEquipo.getComboClientes().addActionListener(this);
		ventanaAgregarEquipo.getComboSucursal().addActionListener(this);
		ventanaAgregarEquipo.getComboMarca().addActionListener(this);
		ventanaAgregarEquipo.getComboNombreEquipo().addActionListener(this);
		ventanaAgregarEquipo.getComboModelo().addActionListener(this);
		ventanaAgregarEquipo.getComboSerie().addActionListener(this);
		ventanaAgregarEquipo.getBotonGuardar().addActionListener(this);
		ventanaAgregarEquipo.getBotonGenerarRegistro().addActionListener(this);
		ventanaAgregarEquipo.getBotonNuevaReparacion().addActionListener(this);
		ventanaAgregarEquipo.getBtnFechaDefault().addActionListener(this);
		ventanaAgregarEquipo.getBtnGenerarSerie().addActionListener(this);
		ventanaAgregarEquipo.getBotonNuevaReparacion().setEnabled(false);
		ventanaAgregarEquipo.getBotonVerificarIngresoAnterior().addActionListener(this);
		ventanaAgregarEquipo.getBtnaltaCliente().addActionListener(this);
		ventanaAgregarEquipo.getBtnrecargarLista().addActionListener(this);

		llenarComboCliente();
		llenarComboSucursal();
		llenarComboNombreEquipo();
		llenarComboMarca();
		llenarComboModelo();
		llenarComboSerie();

		AutoCompleteDecorator.decorate(ventanaAgregarEquipo.getComboClientes());
		AutoCompleteDecorator.decorate(ventanaAgregarEquipo.getComboSucursal());
		AutoCompleteDecorator.decorate(ventanaAgregarEquipo.getComboNombreEquipo());
		AutoCompleteDecorator.decorate(ventanaAgregarEquipo.getComboMarca());
		AutoCompleteDecorator.decorate(ventanaAgregarEquipo.getComboModelo());
		AutoCompleteDecorator.decorate(ventanaAgregarEquipo.getComboSerie());

		ventanaAgregarEquipo.getGrupoEstadoFisico().setSelected(ventanaAgregarEquipo.getRdbtnBRC().getModel(), true);

	}

	private void TomarDatosDeTablas() throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		ventanaVisualizarEquipos.setTextELS(Integer.toString(ELSinicial));
		NumeroELS = Integer.parseInt(ventanaVisualizarEquipos.getTextELS().toString());
		reparacion = agenda.dameReparacionXels(NumeroELS);

		// llenarComboTecnico();

		ventanaVisualizarEquipos.setTextNombreEquipo(reparacion.getNombreEquipo());
		ventanaVisualizarEquipos.setTextMarca(reparacion.getMarca());
		ventanaVisualizarEquipos.setTextModelo(reparacion.getModelo());
		ventanaVisualizarEquipos.setTextNSerie(reparacion.getNumeroDeSerie());

		// ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(reparacion.getPresupuestoGenerado());

		if (reparacion.getFalla() == null)
			ventanaVisualizarEquipos.setTextFalla("");
		else
			ventanaVisualizarEquipos.setTextFalla(reparacion.getFalla());

		ventanaVisualizarEquipos.setTextAvisoCliente(reparacion.getAviso());
		ventanaVisualizarEquipos.setTextClienteCliente(reparacion.getClienteCliente());
		ventanaVisualizarEquipos.setTextRemitoCliente(reparacion.getRemitoCliente());

		ventanaVisualizarEquipos.setTextCliente(reparacion.getCliente());
		ventanaVisualizarEquipos.setTextSucursal(reparacion.getSucursal());

		if (reparacion.getFecha_Entrada() == null)
			ventanaVisualizarEquipos.setTextFechaEntrada2(null);
		else
			ventanaVisualizarEquipos.setTextFechaEntrada2((dateFormat.parse(reparacion.getFecha_Entrada())));

		ventanaVisualizarEquipos.setTextEstadoFisico(reparacion.getEstadoFisico());
		ventanaVisualizarEquipos.setTextEstadoTecnico(reparacion.getEstadoTecnico());
		ventanaVisualizarEquipos.setTextEstadoComercial(reparacion.getEstadoComercial());
		ventanaVisualizarEquipos.setTextDiagnostico(reparacion.getSolucion());
		ventanaVisualizarEquipos.setTextInformeCliente(reparacion.getInformecliente());

		if (reparacion.getFechadereparacion() == null)
			ventanaVisualizarEquipos.setTextFechaReparacion2(null);
		else
			ventanaVisualizarEquipos.setTextFechaReparacion2((dateFormat.parse(reparacion.getFechadereparacion())));

		if (reparacion.getFechAceptacion() == null)
			ventanaVisualizarEquipos.setTextFechaRespuesta2(null);
		else
			ventanaVisualizarEquipos.setTextFechaRespuesta2((dateFormat.parse(reparacion.getFechAceptacion())));

		if (reparacion.getFechaFabr() == null)
			ventanaVisualizarEquipos.setFechaFabr2(null);
		else {
			ventanaVisualizarEquipos.setFechaFabr2((dateFormat.parse(reparacion.getFechaFabr())));
		}

		ventanaVisualizarEquipos.setTextNombreTecnico(reparacion.getNombreUsuario());
		// ventanaVisualizarEquipos.getComboTecnico().setSelectedIndex(reparacion.getidUsuario()-1);
		ventanaVisualizarEquipos.setTextOC(reparacion.getOrdendeCompra());
		ventanaVisualizarEquipos.setTextUbicacionRemito(Integer.toString(reparacion.getCodigo()));
		ventanaVisualizarEquipos.setTextNumeroRemito(Integer.toString(reparacion.getNumeroRemitoSalida()));
		// ventanaVisualizarEquipos.setChckbxAvisoEnviado((boolean)reparacion.getEnviado());

		llenarTablaRepuestos();
		ventanaVisualizarEquipos.getTextNombreEquipo().moveCaretPosition(0);

		String presupuestoPeso = monedaFormatter.formatPeso(reparacion.getPrecioPeso().toString());
		String pagoPeso = monedaFormatter.formatPeso(reparacion.getPago().toString());

		ventanaVisualizarEquipos.setTextPresupuesto(presupuestoPeso);
		ventanaVisualizarEquipos.setTextPago(pagoPeso);

		ventanaVisualizarEquipos.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
		ventanaVisualizarEquipos.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
		ventanaVisualizarEquipos.setChckWORDGenerado(reparacion.getWORDgenerado());
		ventanaVisualizarEquipos.setChckWORDEnviado(reparacion.getWORDenviado());

		ventanaVisualizarEquipos.setChckbxAvisoEnviado(reparacion.getAvisoEnviado());

		verificarPresupuesto();
		deshabilitarCampos();

	}

	private void TomarDatosDeTablasBusquedaOrden(int numeroELSSeleccionado) throws ParseException {

		// NumberFormat nf =
		// NumberFormat.getCurrencyInstance(Locale.getDefault());
		ventanaVisualizarEquipos.dispose();
		ventanaVisualizarEquipos = null;
		ventanaVisualizarEquipos = new VentanaVisualizarEquipos(this);
		cerraVentanaVisualizarEquipo();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELSSeleccionado));

		reparacion = agenda.dameReparacionXels(numeroELSSeleccionado);

		// llenarComboTecnico();

		ventanaVisualizarEquipos.setTextNombreEquipo(reparacion.getNombreEquipo());
		ventanaVisualizarEquipos.setTextMarca(reparacion.getMarca());
		ventanaVisualizarEquipos.setTextModelo(reparacion.getModelo());
		ventanaVisualizarEquipos.setTextNSerie(reparacion.getNumeroDeSerie());

		// ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(reparacion.getPresupuestoGenerado());

		if (reparacion.getFalla() == null)
			ventanaVisualizarEquipos.setTextFalla("");
		else
			ventanaVisualizarEquipos.setTextFalla(reparacion.getFalla());

		ventanaVisualizarEquipos.setTextAvisoCliente(reparacion.getAviso());
		ventanaVisualizarEquipos.setTextClienteCliente(reparacion.getClienteCliente());
		ventanaVisualizarEquipos.setTextRemitoCliente(reparacion.getRemitoCliente());

		ventanaVisualizarEquipos.setTextCliente(reparacion.getCliente());
		ventanaVisualizarEquipos.setTextSucursal(reparacion.getSucursal());

		if (reparacion.getFecha_Entrada() == null)
			ventanaVisualizarEquipos.setTextFechaEntrada2(null);
		else
			ventanaVisualizarEquipos.setTextFechaEntrada2((dateFormat.parse(reparacion.getFecha_Entrada())));

		ventanaVisualizarEquipos.setTextEstadoFisico(reparacion.getEstadoFisico());
		ventanaVisualizarEquipos.setTextEstadoTecnico(reparacion.getEstadoTecnico());
		ventanaVisualizarEquipos.setTextEstadoComercial(reparacion.getEstadoComercial());
		ventanaVisualizarEquipos.setTextDiagnostico(reparacion.getSolucion());
		ventanaVisualizarEquipos.setTextInformeCliente(reparacion.getInformecliente());

		if (reparacion.getFechadereparacion() == null)
			ventanaVisualizarEquipos.setTextFechaReparacion2(null);
		else
			ventanaVisualizarEquipos.setTextFechaReparacion2((dateFormat.parse(reparacion.getFechadereparacion())));

		if (reparacion.getFechAceptacion() == null)
			ventanaVisualizarEquipos.setTextFechaRespuesta2(null);
		else
			ventanaVisualizarEquipos.setTextFechaRespuesta2((dateFormat.parse(reparacion.getFechAceptacion())));

		if (reparacion.getFechaFabr() == null)
			ventanaVisualizarEquipos.setFechaFabr2(null);
		else {
			ventanaVisualizarEquipos.setFechaFabr2((dateFormat.parse(reparacion.getFechaFabr())));
		}

		ventanaVisualizarEquipos.setTextNombreTecnico(reparacion.getNombreUsuario());
		// ventanaVisualizarEquipos.getComboTecnico().setSelectedIndex(reparacion.getidUsuario()-1);
		ventanaVisualizarEquipos.setTextOC(reparacion.getOrdendeCompra());
		ventanaVisualizarEquipos.setTextUbicacionRemito(Integer.toString(reparacion.getCodigo()));
		ventanaVisualizarEquipos.setTextNumeroRemito(Integer.toString(reparacion.getNumeroRemitoSalida()));
		// ventanaVisualizarEquipos.setChckbxAvisoEnviado((boolean)reparacion.getEnviado());

		llenarTablaRepuestos();
		ventanaVisualizarEquipos.getTextNombreEquipo().moveCaretPosition(0);

		ventanaVisualizarEquipos.setTextPresupuesto(reparacion.getPrecioPeso().toString());
		ventanaVisualizarEquipos.setTextPago(reparacion.getPago().toString());

		ventanaVisualizarEquipos.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
		ventanaVisualizarEquipos.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
		ventanaVisualizarEquipos.setChckWORDGenerado(reparacion.getWORDgenerado());
		ventanaVisualizarEquipos.setChckWORDEnviado(reparacion.getWORDenviado());

		ventanaVisualizarEquipos.setChckbxAvisoEnviado(reparacion.getAvisoEnviado());

		verificarPresupuesto();
		deshabilitarCampos();

	}

	public void TomarDatosDeTablasListado(int numeroELSSeleccionado2) throws ParseException {

		ventanaVisualizarEquipos = new VentanaVisualizarEquipos(this);
		cerraVentanaVisualizarEquipo();

		controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);

		SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELSSeleccionado2));

		reparacion = agenda.dameReparacionXels(numeroELSSeleccionado2);

		// llenarComboTecnico();

		ventanaVisualizarEquipos.setTextNombreEquipo(reparacion.getNombreEquipo());
		ventanaVisualizarEquipos.setTextMarca(reparacion.getMarca());
		ventanaVisualizarEquipos.setTextModelo(reparacion.getModelo());
		ventanaVisualizarEquipos.setTextNSerie(reparacion.getNumeroDeSerie());

		// ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(reparacion.getPresupuestoGenerado());

		if (reparacion.getFalla() == null)
			ventanaVisualizarEquipos.setTextFalla("");
		else
			ventanaVisualizarEquipos.setTextFalla(reparacion.getFalla());

		ventanaVisualizarEquipos.setTextAvisoCliente(reparacion.getAviso());
		ventanaVisualizarEquipos.setTextClienteCliente(reparacion.getClienteCliente());
		ventanaVisualizarEquipos.setTextRemitoCliente(reparacion.getRemitoCliente());

		ventanaVisualizarEquipos.setTextCliente(reparacion.getCliente());
		ventanaVisualizarEquipos.setTextSucursal(reparacion.getSucursal());

		if (reparacion.getFecha_Entrada() == null)
			ventanaVisualizarEquipos.setTextFechaEntrada2(null);
		else
			ventanaVisualizarEquipos.setTextFechaEntrada2((dateFormat.parse(reparacion.getFecha_Entrada())));

		ventanaVisualizarEquipos.setTextEstadoFisico(reparacion.getEstadoFisico());
		ventanaVisualizarEquipos.setTextEstadoTecnico(reparacion.getEstadoTecnico());
		ventanaVisualizarEquipos.setTextEstadoComercial(reparacion.getEstadoComercial());
		ventanaVisualizarEquipos.setTextDiagnostico(reparacion.getSolucion());
		ventanaVisualizarEquipos.setTextInformeCliente(reparacion.getInformecliente());

		if (reparacion.getFechadereparacion() == null)
			ventanaVisualizarEquipos.setTextFechaReparacion2(null);
		else
			ventanaVisualizarEquipos.setTextFechaReparacion2((dateFormat.parse(reparacion.getFechadereparacion())));

		if (reparacion.getFechAceptacion() == null)
			ventanaVisualizarEquipos.setTextFechaRespuesta2(null);
		else
			ventanaVisualizarEquipos.setTextFechaRespuesta2((dateFormat.parse(reparacion.getFechAceptacion())));

		if (reparacion.getFechaFabr() == null)
			ventanaVisualizarEquipos.setFechaFabr2(null);
		else {
			ventanaVisualizarEquipos.setFechaFabr2((dateFormat.parse(reparacion.getFechaFabr())));
		}

		ventanaVisualizarEquipos.setTextNombreTecnico(reparacion.getNombreUsuario());
		// ventanaVisualizarEquipos.getComboTecnico().setSelectedIndex(reparacion.getidUsuario()-1);
		ventanaVisualizarEquipos.setTextOC(reparacion.getOrdendeCompra());
		ventanaVisualizarEquipos.setTextUbicacionRemito(Integer.toString(reparacion.getCodigo()));
		ventanaVisualizarEquipos.setTextNumeroRemito(Integer.toString(reparacion.getNumeroRemitoSalida()));
		// ventanaVisualizarEquipos.setChckbxAvisoEnviado((boolean)reparacion.getEnviado());

		llenarTablaRepuestos();
		ventanaVisualizarEquipos.getTextNombreEquipo().moveCaretPosition(0);

		ventanaVisualizarEquipos.setTextPresupuesto(reparacion.getPrecioPeso().toString());
		ventanaVisualizarEquipos.setTextPago(reparacion.getPago().toString());

		ventanaVisualizarEquipos.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
		ventanaVisualizarEquipos.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
		ventanaVisualizarEquipos.setChckWORDGenerado(reparacion.getWORDgenerado());
		ventanaVisualizarEquipos.setChckWORDEnviado(reparacion.getWORDenviado());
		ventanaVisualizarEquipos.setChckbxAvisoEnviado(reparacion.getAvisoEnviado());

		verificarPresupuesto();
		deshabilitarCampos();

	}

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

		int ELS = Integer.parseInt(this.ventanaAgregarEquipo.getTextELS());
		String falla = this.ventanaAgregarEquipo.getTextFalla().getText();
		String RemitoCLiente = this.ventanaAgregarEquipo.getTextRemitoCliente().getText();
		int IDEquipo = dameIDequipo();
		String NombreEquipo = this.ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString();
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

		Enumeration elements = ventanaAgregarEquipo.getGrupoEstadoFisico().getElements();

		while (elements.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elements.nextElement();
			if (button.isSelected()) {

				estadoFisico = button.getText();

			}
		}

		RegistroEntradaReporteDTO nuevoReparacion = new RegistroEntradaReporteDTO(ELS, fechaentrada, falla,
				estadoFisico, estadoTecnico, RemitoCLiente, IDEquipo, NombreEquipo, Modelo, Marca, Serie, aviso,
				ClienteCliente, idCliente, idSucursal, Cliente, Sucursal);

		return nuevoReparacion;
	}

	private static String generateRandomText() {

		SecureRandom random = new SecureRandom();
		String text = new BigInteger(25, random).toString(32);
		text = text.toUpperCase();
		return text;

	}

	private RepuestosDTO TomarDatosRepuesto() {

		int ELS = Integer.parseInt(this.ventanaVisualizarEquipos.getTextELS());
		String Referencia = this.ventanaagregarRepuesto.getTxtReferencia().getText();
		String Original = this.ventanaagregarRepuesto.getTxtOriginal().getText();
		String Reemplazo = this.ventanaagregarRepuesto.getTxtReemplazo().getText();
		;
		String Nota = this.ventanaagregarRepuesto.getTxtNota().getText();
		;

		RepuestosDTO nuevoRepuesto = new RepuestosDTO(ELS, Referencia, Original, Reemplazo, Nota);

		return nuevoRepuesto;

	}

	private void llenarTablaRepuestos() {
		this.ventanaVisualizarEquipos.getModelRepuestos().setRowCount(0); // Para
																			// vaciar
																			// tabla
		this.ventanaVisualizarEquipos.getModelRepuestos().setColumnCount(0);
		this.ventanaVisualizarEquipos.getModelRepuestos()
				.setColumnIdentifiers(this.ventanaVisualizarEquipos.getNombreColumnas());

		int ELS = Integer.parseInt(this.ventanaVisualizarEquipos.getTextELS());

		this.Repuestos_en_tabla = (List<RepuestosDTO>) agenda.dameRepuestoXels(ELS);

		for (int i = 0; i < this.Repuestos_en_tabla.size(); i++) {
			Object[] fila = { this.Repuestos_en_tabla.get(i).getRef(), this.Repuestos_en_tabla.get(i).getOriginal(),
					this.Repuestos_en_tabla.get(i).getReemplazo(), this.Repuestos_en_tabla.get(i).getNotas() };
			this.ventanaVisualizarEquipos.getModelRepuestos().addRow(fila);
		}
		this.ventanaVisualizarEquipos.show();

	}

	private void verificarPresupuesto() {

		Color EquipoPagado = new Color(130, 224, 170);
		Color AzulClaro = new Color(169, 204, 227);
		Color CyanClaro = new Color(224, 255, 255);
		Color FaltaPago = new Color(241, 148, 138);

		Double PresupuestoDefault = 0.0;
		if ((reparacion.getPrecioPeso().compareTo(PresupuestoDefault) != 0)) {

			if (reparacion.getPrecioPeso().compareTo(reparacion.getPago()) == 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPago().setBackground(EquipoPagado);

			} else if (reparacion.getPrecioPeso().compareTo(reparacion.getPago()) > 0
					&& reparacion.getPago().compareTo(PresupuestoDefault) != 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO PARCIALMENTE");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPago().setBackground(CyanClaro);

			} else if (reparacion.getPago().compareTo(PresupuestoDefault) == 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("FALTA PAGO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPago().setBackground(FaltaPago);

			}

		} else {
			ventanaVisualizarEquipos.getTextEquipoPagado().setText("SIN PRESUPUESTAR");
			ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
			ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPago().setBackground(AzulClaro);

		}
		// TODO Auto-generated method stub

	}

	public void verificarPresupuestoEditado() {

		Color EquipoPagado = new Color(130, 224, 170);
		Color AzulClaro = new Color(169, 204, 227);
		Color CyanClaro = new Color(224, 255, 255);
		Color FaltaPago = new Color(241, 148, 138);

		String PresupuestoDefault = "0.0";

		double presupuesto = monedaFormatter
				.parseAmountGuardar(ventanaVisualizarEquipos.getTextPresupuesto().getText());
		double pago = monedaFormatter.parseAmountGuardar(ventanaVisualizarEquipos.getTextPago().getText());

		double diferencia = presupuesto - pago;

		System.out.println(presupuesto + "-" + pago + "=" + diferencia);

		if ((presupuesto != 0.0)) {

			if (diferencia == 0.0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPago().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.setChckPDFGenerado(true);

			} else if (diferencia > 0.0 && diferencia < presupuesto) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO PARCIALMENTE");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPago().setBackground(CyanClaro);

			} else if (diferencia == presupuesto) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("FALTA PAGO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPago().setBackground(FaltaPago);
				ventanaVisualizarEquipos.setChckPDFGenerado(true);

			}

		} else {
			ventanaVisualizarEquipos.getTextEquipoPagado().setText("SIN PRESUPUESTAR");
			ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
			ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPago().setBackground(AzulClaro);
			ventanaVisualizarEquipos.setChckPDFGenerado(false);

		}
		// TODO Auto-generated method stub

	}

	private void deshabilitarCampos() {

		this.ventanaVisualizarEquipos.getTextNombreEquipo().setEditable(false);
		this.ventanaVisualizarEquipos.getTextModelo().setEditable(false);
		this.ventanaVisualizarEquipos.getTextMarca().setEditable(false);
		this.ventanaVisualizarEquipos.getTextNSerie().setEditable(false);
		this.ventanaVisualizarEquipos.getTextClienteCliente().setEditable(false);
		this.ventanaVisualizarEquipos.getTextAvisoCliente().setEditable(false);
		this.ventanaVisualizarEquipos.getTextRemitoCliente().setEditable(false);
		this.ventanaVisualizarEquipos.getTextFalla().setEditable(false);
		this.ventanaVisualizarEquipos.getTextOC().setEditable(false);
		this.ventanaVisualizarEquipos.getTextPresupuesto().setEditable(false);
		this.ventanaVisualizarEquipos.getTextPago().setEditable(false);

		this.ventanaVisualizarEquipos.getTextNombreTecnico().setEditable(false);
		this.ventanaVisualizarEquipos.getTextDiagnostico().setEditable(false);
		this.ventanaVisualizarEquipos.getTextInformeCliente().setEditable(false);
		this.ventanaVisualizarEquipos.getTablaRepuestos().setEnabled(true);
		this.ventanaVisualizarEquipos.getFechaEntrada().setEnabled(false);
		this.ventanaVisualizarEquipos.getFechaReparacion().setEnabled(false);
		this.ventanaVisualizarEquipos.getFechaRespuesta().setEnabled(false);
		this.ventanaVisualizarEquipos.getBtnGuardarCambios().setEnabled(false);
		this.ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(false);

		this.ventanaVisualizarEquipos.getTextCliente().setVisible(true);
		this.ventanaVisualizarEquipos.getTextSucursal().setVisible(true);
		this.ventanaVisualizarEquipos.getComboClientes().setVisible(false);
		this.ventanaVisualizarEquipos.getComboSucursal().setVisible(false);
		this.ventanaVisualizarEquipos.getTextNombreTecnico().setVisible(true);
		this.ventanaVisualizarEquipos.getComboTecnico().setVisible(false);
	}

	private void habilitarCampos() {

		String nombreCliente = "";
		String nombreSucursal = "";
		String nombreTecnico = "";

		this.ventanaVisualizarEquipos.getTextNombreEquipo().setEditable(true);
		this.ventanaVisualizarEquipos.getTextModelo().setEditable(true);
		this.ventanaVisualizarEquipos.getTextMarca().setEditable(true);
		this.ventanaVisualizarEquipos.getTextNSerie().setEditable(true);
		this.ventanaVisualizarEquipos.getTextClienteCliente().setEditable(true);
		this.ventanaVisualizarEquipos.getTextAvisoCliente().setEditable(true);
		this.ventanaVisualizarEquipos.getTextRemitoCliente().setEditable(true);
		this.ventanaVisualizarEquipos.getTextFalla().setEditable(true);
		this.ventanaVisualizarEquipos.getTextOC().setEditable(true);
		this.ventanaVisualizarEquipos.getTextPresupuesto().setEditable(true);
		this.ventanaVisualizarEquipos.getTextPago().setEditable(true);

		this.ventanaVisualizarEquipos.getTextNombreTecnico().setEditable(true);
		this.ventanaVisualizarEquipos.getTextDiagnostico().setEditable(true);
		this.ventanaVisualizarEquipos.getTextInformeCliente().setEditable(true);
		this.ventanaVisualizarEquipos.getTablaRepuestos().setEnabled(true);

		this.ventanaVisualizarEquipos.getFechaEntrada().setEnabled(true);
		this.ventanaVisualizarEquipos.getFechaReparacion().setEnabled(true);
		this.ventanaVisualizarEquipos.getFechaRespuesta().setEnabled(true);
		this.ventanaVisualizarEquipos.getBotonEditarEstados().setEnabled(true);
		this.ventanaVisualizarEquipos.getBtnGuardarCambios().setEnabled(true);

		this.ventanaVisualizarEquipos.getTextCliente().setVisible(false);
		this.ventanaVisualizarEquipos.getTextSucursal().setVisible(false);
		this.ventanaVisualizarEquipos.getComboClientes().setVisible(true);
		this.ventanaVisualizarEquipos.getComboSucursal().setVisible(true);
		this.ventanaVisualizarEquipos.getComboSucursal().setEditable(true);
		this.ventanaVisualizarEquipos.getComboTecnico().setVisible(true);
		this.ventanaVisualizarEquipos.getComboTecnico().setEditable(true);

		nombreCliente = this.ventanaVisualizarEquipos.getTextCliente().getText();
		nombreSucursal = this.ventanaVisualizarEquipos.getTextSucursal().getText();
		nombreTecnico = this.ventanaVisualizarEquipos.getTextNombreTecnico().getText();

		int idcliente = IDClientePorNombre(nombreCliente);
		int idSucursal = IDSucursalPorNombre(nombreSucursal, idcliente);
		int idtecnico = IDUsuarioPorNombre(nombreTecnico);

		this.ventanaVisualizarEquipos.getComboClientes().setSelectedIndex(idcliente);
		this.ventanaVisualizarEquipos.getComboSucursal().setSelectedItem(nombreSucursal);
		this.ventanaVisualizarEquipos.getComboTecnico().setSelectedItem(nombreTecnico);

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

	private void llenarComboClienteV() {

		agenda.ListarCliente(ventanaVisualizarEquipos.getComboClientes());

		ventanaVisualizarEquipos.getComboClientes().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaVisualizarEquipos.getComboClientes().getSelectedItem() != null) {
					Cliente = (ClienteDTO) ventanaVisualizarEquipos.getComboClientes().getSelectedItem();
					int id = Cliente.getId();

					agenda.ListarSucursalesxCliente(ventanaVisualizarEquipos.getComboSucursal(), id);
					idCli = id;

				}

			}
		});

	}

	private void llenarComboSucursal() {

		ventanaAgregarEquipo.getComboSucursal().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaAgregarEquipo.getComboSucursal().getSelectedItem() != null) {
					Sucursal = (SucursalDTO) ventanaAgregarEquipo.getComboSucursal().getSelectedItem();
					int idsuc = Sucursal.getIdSucursal();

					idSuc = idsuc;

				}
			}
		});

	}

	private void llenarComboCliente() {

		agenda.ListarCliente(ventanaAgregarEquipo.getComboClientes());

		ventanaAgregarEquipo.getComboClientes().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaAgregarEquipo.getComboClientes().getSelectedItem() != null) {
					Cliente = (ClienteDTO) ventanaAgregarEquipo.getComboClientes().getSelectedItem();
					int id = Cliente.getId();

					agenda.ListarSucursalesxCliente(ventanaAgregarEquipo.getComboSucursal(), id);
					idCli = id;

				}

			}
		});

	}

	private void llenarComboTecnico() {

		agenda.ListarTecnicos(ventanaVisualizarEquipos.getComboTecnico());

	}

	private void llenarComboMarca() {

		agenda.ListarMarca(ventanaAgregarEquipo.getComboMarca());

		ventanaAgregarEquipo.getComboMarca().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaAgregarEquipo.getComboMarca().getSelectedItem() != null) {
					Marca = ventanaAgregarEquipo.getComboMarca().getSelectedItem().toString();
					// int id = reparacion.getIDEquipo();

					agenda.ListarModelosxMarca(ventanaAgregarEquipo.getComboModelo(), Marca);
					// idCli = id;
				}

			}
		});

		ventanaAgregarEquipo.getComboMarca().setSelectedItem("");
	}

	private void llenarComboNombreEquipo() {

		agenda.ListarEquipo(ventanaAgregarEquipo.getComboNombreEquipo());

		ventanaAgregarEquipo.getComboNombreEquipo().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem() != null) {
					NombreEq = ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString();

				}

			}
		});

		ventanaAgregarEquipo.getComboNombreEquipo().setSelectedItem("");

	}

	private void llenarComboModelo() {

		ventanaAgregarEquipo.getComboModelo().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaAgregarEquipo.getComboModelo().getSelectedItem() != null) {
					Modelo = ventanaAgregarEquipo.getComboModelo().getSelectedItem().toString();
					agenda.ListarSeriexModelo(ventanaAgregarEquipo.getComboSerie(), Modelo);
				}
			}
		});

		ventanaAgregarEquipo.getComboModelo().setSelectedItem("");
	}

	private void llenarComboSerie() {

		ventanaAgregarEquipo.getComboSerie().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaAgregarEquipo.getComboSerie().getSelectedItem() != null) {
					Serie = ventanaAgregarEquipo.getComboSerie().getSelectedItem().toString();

				}
			}
		});
		ventanaAgregarEquipo.getComboSerie().setSelectedItem("");
	}

	private int dameIDequipo() {
		int idEquipo = 0;
		idEquipo = agenda.dameIDequipo() + 1;
		return idEquipo;
	}

	private int DameNumeroELS() {
		int ELS = 0;
		ELS = agenda.dameNumeroELS() + 1;
		return ELS;
	}

	private ReparacionDTO TomarDatosPantallaIngreso() {

		int ELS = Integer.parseInt(this.ventanaAgregarEquipo.getTextELS());
		String falla = this.ventanaAgregarEquipo.getTextFalla().getText();
		String RemitoCLiente = this.ventanaAgregarEquipo.getTextRemitoCliente().getText();
		int IDEquipo = dameIDequipo();
		String NombreEquipo = NombreEq;
		String Modelo = this.Modelo;
		String Marca = this.Marca;
		String Series = this.Serie;
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
		Enumeration elements = ventanaAgregarEquipo.getGrupoEstadoFisico().getElements();

		while (elements.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elements.nextElement();
			if (button.isSelected()) {

				estadoFisico = button.getText();

			}
		}

		estadoTecnico = "Sin revisar";
		estadocomercial = "A la Espera de Aceptación";

		ReparacionDTO nuevoReparacion = new ReparacionDTO(ELS, fechaentrada, falla, estadoFisico, estadoTecnico,
				estadocomercial, RemitoCLiente, IDEquipo, idUsuarios, NombreEquipo, Modelo, Marca, Series, aviso,
				ClienteCliente, idCliente, idSucursal, fechaFarbricacion);

		return nuevoReparacion;

	}

	private ReparacionDTO TomarDatosVisualizacion() {

		int ELS = Integer.parseInt(this.ventanaVisualizarEquipos.getTextELS());
		String falla = this.ventanaVisualizarEquipos.getTextFalla().getText();
		String solucion = this.ventanaVisualizarEquipos.getTextDiagnostico().getText();
		String informeCliente = this.ventanaVisualizarEquipos.getTextInformeCliente().getText();

		String RemitoCLiente = this.ventanaVisualizarEquipos.getTextRemitoCliente().getText();
		int IDEquipo = reparacion.getIDEquipo();
		String NombreEquipo = this.ventanaVisualizarEquipos.getTextNombreEquipo().getText();
		String Modelo = this.ventanaVisualizarEquipos.getTextModelo().getText();
		String Marca = this.ventanaVisualizarEquipos.getTextMarca().getText();
		String Serie = this.ventanaVisualizarEquipos.getTextNSerie().getText();

		String aviso = this.ventanaVisualizarEquipos.getTextAvisoCliente().getText();
		String ClienteCliente = this.ventanaVisualizarEquipos.getTextClienteCliente().getText();

		if (!guardado) {
			this.ventanaVisualizarEquipos.getTextCliente()
					.setText(this.ventanaVisualizarEquipos.getComboClientes().getSelectedItem().toString());
			this.ventanaVisualizarEquipos.getTextSucursal()
					.setText(this.ventanaVisualizarEquipos.getComboSucursal().getSelectedItem().toString());
			this.ventanaVisualizarEquipos.getTextNombreTecnico()
					.setText(this.ventanaVisualizarEquipos.getComboTecnico().getSelectedItem().toString());
		}

		String Cliente = this.ventanaVisualizarEquipos.getTextCliente().getText();
		String Sucursal = this.ventanaVisualizarEquipos.getTextSucursal().getText();
		String nombreTecnico = this.ventanaVisualizarEquipos.getTextNombreTecnico().getText();

		int idCliente = IDClientePorNombre(Cliente);
		int idSucursal = IDSucursalPorNombre(Sucursal, idCliente);
		int idUsuario = IDUsuarioPorNombre(nombreTecnico);

		String estadoFisico = this.ventanaVisualizarEquipos.getTextEstadoFisico().getText();
		String estadoTecnico = this.ventanaVisualizarEquipos.getTextEstadoTecnico().getText();
		String estadoComercial = this.ventanaVisualizarEquipos.getTextEstadoComercial().getText();

		boolean enviado = false;

		boolean presupuestoGenerado = this.ventanaVisualizarEquipos.getChckPDFGenerado();
		boolean presupuestoEnviado = this.ventanaVisualizarEquipos.getChckPDFEnviado();
		boolean avisoEnviado = this.ventanaVisualizarEquipos.getChckbxAvisoEnviado();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		String fechaentradavisual = null;
		java.util.Date fechaEntradaVisual = this.ventanaVisualizarEquipos.getFechaEntrada().getDate();
		if (fechaEntradaVisual != null) {

			fechaentradavisual = dateFormat.format(fechaEntradaVisual);
		}

		String fechareparacionvisual = null;
		java.util.Date fechaReparacionVisual = this.ventanaVisualizarEquipos.getFechaReparacion().getDate();
		if (fechaReparacionVisual != null) {

			fechareparacionvisual = dateFormat.format(fechaReparacionVisual);
		}

		String fechaaceptacionvisual = null;
		java.util.Date fechaAceptacionVisual = this.ventanaVisualizarEquipos.getFechaRespuesta().getDate();
		if (fechaAceptacionVisual != null) {

			fechaaceptacionvisual = dateFormat.format(fechaAceptacionVisual);
		}

		String fechafabrvisual = null;
		java.util.Date fechaFabrvisual = this.ventanaVisualizarEquipos.getFechaFabr().getDate();
		if (fechaFabrvisual != null) {

			fechafabrvisual = dateFormat.format(fechaFabrvisual);
		}

		if (estadoFisico != "Enviado") {

			enviado = false;

		} else
			enviado = true;

		double presupuesto;
		double pago;

		if (monedaFormatter.tieneFormato(this.ventanaVisualizarEquipos.getTextPresupuesto().getText())) {

			presupuesto = monedaFormatter
					.parseAmountGuardar(this.ventanaVisualizarEquipos.getTextPresupuesto().getText());
			pago = monedaFormatter.parseAmountGuardar(this.ventanaVisualizarEquipos.getTextPago().getText());

		} else {

			presupuesto = monedaFormatter.parseAmount(this.ventanaVisualizarEquipos.getTextPresupuesto().getText());
			pago = monedaFormatter.parseAmount(this.ventanaVisualizarEquipos.getTextPago().getText());

			ventanaVisualizarEquipos.getTextPresupuesto()
					.setText(monedaFormatter.formatPeso(this.ventanaVisualizarEquipos.getTextPresupuesto().getText()));
			ventanaVisualizarEquipos.getTextPago()
					.setText(monedaFormatter.formatPeso(this.ventanaVisualizarEquipos.getTextPago().getText()));

		}

		String OrdenDeCompra = this.ventanaVisualizarEquipos.getTextOC().getText();

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, fechaentradavisual, fechareparacionvisual, falla,
				solucion, informeCliente, estadoFisico, estadoTecnico, estadoComercial, RemitoCLiente, IDEquipo,
				Cliente, Sucursal, fechaaceptacionvisual, NombreEquipo, Modelo, Marca, Serie, aviso, ClienteCliente,
				idCliente, idSucursal, fechafabrvisual, idUsuario, enviado, presupuesto, pago, presupuestoGenerado,
				avisoEnviado, presupuestoEnviado, OrdenDeCompra);

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

	public void mouseClicked(MouseEvent arg0) {

		if (this.ventanaVisualizarEquipos != null) {
			if (arg0.getSource() == this.ventanaVisualizarEquipos.getTablaRepuestos()) {
				int i = this.ventanaVisualizarEquipos.getTablaRepuestos().getSelectedRow();
				if (i != -1) {
					if (!Repuestos_en_tabla.isEmpty()) {
						repuestoElegido = Repuestos_en_tabla.get(i);

					}
				}
			}
		}

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

			Object mje = "Debe asignar un Cliente a la reparación ";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE); // Tipo
		} else if (ventanaAgregarEquipo.getFechaEntrada().getDate() == null) {

			Object mje = "Fecha de entrada Incorrecta. Colocar una fecha Válida dd/mm/aaaa. Distinta de 00/00/0000";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE); // Tipo

		} else if (ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString().compareTo("") == 0
				|| ventanaAgregarEquipo.getComboNombreEquipo().getSelectedItem().toString().compareTo(" ") == 0) {

			Object mje = "'NOMBRE DE EQUIPO'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE); // Tipo

		} else if (ventanaAgregarEquipo.getComboSerie().getSelectedItem() == null) {

			Object mje = "'NÚMERO DE SERIE'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE); // Tipo
		} else if (ventanaAgregarEquipo.getComboSerie().getSelectedItem().toString().compareTo("") == 0
				|| ventanaAgregarEquipo.getComboSerie().getSelectedItem().toString().compareTo(" ") == 0) {

			Object mje = "'NÚMERO DE SERIE'. Campo obligatorio.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE); // Tipo

		} else if (ventanaAgregarEquipo.getComboClientes().getSelectedItem().toString().compareTo("Siemens SA") == 0
				&& ventanaAgregarEquipo.getTextFechafabricacion().getDate() == null) {

			Object mje = "Fecha de fabricación Incorrecta. Colocar una fecha Válida dd/mm/aaaa. Distinta de 00/00/0000";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE); // Tipo

		} else {

			salida = true;

		}

		return salida;

	}

	private void llenarComboSeries() {

		agenda.ListarSerie(ventanaVerificarIngresoAnterior.getComboSerie());

		ventanaVerificarIngresoAnterior.getComboSerie().setSelectedIndex(-1);

	}

	boolean validacionMail(String email) {

		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
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

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if (this.ventanaVisualizarEquipos != null) {

//			if (e.getSource() == this.ventanaVisualizarEquipos.getTextPago()) {
//
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//
//					if (ventanaVisualizarEquipos.getTextPago().getText().isEmpty()) {
//
//						ventanaVisualizarEquipos.getTextPago().setText("0.0");
//					}
//
//					verificarPresupuestoEditado();
//
//				}
//
//			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (this.ventanaVisualizarEquipos != null) {

			if (e.getSource() == this.ventanaVisualizarEquipos.getTablaRepuestos()) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					Object mje = "Deberá 'GUARDAR EDICIÓN' para mantener las modificaciones.";
					JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

					ventanaVisualizarEquipos.getBtnEditarRepuesto().setEnabled(true);

				}

			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

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

	public void cerraVentanaVisualizarEquipo() {

		this.ventanaVisualizarEquipos.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
						"¿Desea salir de la ventana 'VISUALIZAR EQUIPOS'?", "Aviso", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					ventanaVisualizarEquipos.dispose();
					ventanaVisualizarEquipos = null;

				}
			}

		});

	}

}
