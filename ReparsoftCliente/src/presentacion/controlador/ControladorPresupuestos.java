package presentacion.controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

import modelo.Agenda;
import presentacion.reportes.ReporteAgenda;
import presentacion.reportes.ReportePresupuesto;
import presentacion.reportes.ReporteRegistroEntrada;
import presentacion.reportes.ReporteRemitoSalida;
import presentacion.vista.VentanaAgregarCliente;
import presentacion.vista.VentanaAgregarSucursal;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaEliminarRemito;
import presentacion.vista.VentanaEmail;
import presentacion.vista.VentanaGenerarPresupuesto;
import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaListadoReparacionesPresupuestos;
import presentacion.vista.VentanaPresupuestos;
import presentacion.vista.VentanaRemitoGenerado;
import presentacion.vista.VentanaRemitos;
import presentacion.vista.VentanaSalidas;
import presentacion.vista.VentanaSeleccionarCliente;
import presentacion.vista.VentanaSeleccionarELS;
import presentacion.vista.VentanaSeleccionarRemito;
import presentacion.vista.VentanaSucursales;
import presentacion.vista.VentanaVisualizarEquipos;
import dto.ClienteDTO;
import dto.RegistroEntradaReporteDTO;
import dto.RegistroPresupuestoDTO;
import dto.RemitoDTO;
import dto.ReparacionDTO;
import dto.SucursalDTO;

public class ControladorPresupuestos implements ActionListener, MouseListener, ItemListener, KeyListener {

	private VentanaPresupuestos ventanaPresupuestos;
	private VentanaListadoReparacionesPresupuestos ventanaListadoReparacionesPresupuestos;
	private VentanaSeleccionarELS ventanaSeleccionarELS;
	private VentanaGenerarPresupuesto ventanaGenerarPresupuesto;
	private VentanaEmail ventanaEmail;

	private ControladorReparacion controladorReparacion;

	private Agenda agenda;
	private ClienteDTO Cliente;
	private RemitoDTO Ubicacion;
	private int idCli;
	private int idSuc;
	private int idUbicacion;
	private String clienteSeleccionado;
	private String sucursalSeleccionada;
	private String ubicacionRemitoSeleccionado;
	private String numeroRemitoSeleccionado;

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	private List<ReparacionDTO> Reparaciones_en_tabla;

	private ReparacionDTO reparacion;

	private int maxHorizontal = Frame.MAXIMIZED_HORIZ;
	private int maxVertical = Frame.MAXIMIZED_VERT;

	private int clickMax = 1;
	private int clickMin = 1;
	private int max = Frame.MAXIMIZED_BOTH;
	private int min = Frame.NORMAL;
	private String part1;
	private String part2;
	String numeros = "";
	boolean guardado = false;

	private boolean btnMarcarEnviados = false;
	private boolean btnDesvincularRemito = false;
	private boolean btnPresupuestoxELS = false;
	private boolean btnpago = false;

	private boolean presupuestoGenerado = false;
	private boolean presupuestoEnviado = false;

	public ControladorPresupuestos(VentanaPresupuestos ventanaPresupuestos, Agenda agenda) {

		this.ventanaPresupuestos = ventanaPresupuestos;

		this.ventanaPresupuestos.getBtnenviarInformesSiemens().addActionListener(this);
		this.ventanaPresupuestos.getBtnEnviarPresupuestos().addActionListener(this);
		this.ventanaPresupuestos.getBtningresarPago().addActionListener(this);
		this.ventanaPresupuestos.getBtnListadoEquipos().addActionListener(this);
		this.ventanaPresupuestos.getBtnmarcarAceptaciones().addActionListener(this);
		this.ventanaPresupuestos.getBtnPresupuestoPorELS().addActionListener(this);

		this.agenda = agenda;

	}

	public void actionPerformed(ActionEvent e) {

		if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtnenviarInformesSiemens()) {

		}

		else if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtnEnviarPresupuestos()) {

		}

		else if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtningresarPago()) {

			btnPresupuestoxELS = false;
			btnpago = true;

			ventanaSeleccionarELS = new VentanaSeleccionarELS(this);
			ventanaSeleccionarELS.getComboELS().addActionListener(this);
			ventanaSeleccionarELS.getBtnAceptar().addActionListener(this);
			ventanaSeleccionarELS.getBtnCancelar().addActionListener(this);

			AutoCompleteDecorator.decorate(ventanaSeleccionarELS.getComboELS());

			llenarComboELS();

		}

		else if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtnListadoEquipos()) {

			ventanaListadoReparacionesPresupuestos = new VentanaListadoReparacionesPresupuestos(this);

			ventanaListadoReparacionesPresupuestos.getBtnMax().addMouseListener(this);

			cargarTablaListadoReparaciones();

			ventanaPresupuestos.dispose();
			ventanaPresupuestos = null;
		}

		else if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtnmarcarAceptaciones()) {

		}

		else if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtnPresupuestoPorELS()) {

			btnPresupuestoxELS = true;
			btnpago = false;

			ventanaSeleccionarELS = new VentanaSeleccionarELS(this);
			ventanaSeleccionarELS.getComboELS().addActionListener(this);
			ventanaSeleccionarELS.getBtnAceptar().addActionListener(this);
			ventanaSeleccionarELS.getBtnCancelar().addActionListener(this);

			AutoCompleteDecorator.decorate(ventanaSeleccionarELS.getComboELS());

			llenarComboELS();

		}

		if (ventanaPresupuestos != null && ventanaSeleccionarELS != null
				&& e.getSource() == this.ventanaSeleccionarELS.getBtnCancelar()) {

			ventanaSeleccionarELS.dispose();
			ventanaSeleccionarELS = null;

		}

		else if (ventanaPresupuestos != null && ventanaSeleccionarELS != null
				&& e.getSource() == this.ventanaSeleccionarELS.getBtnAceptar()) {

			if (btnPresupuestoxELS) {

				if (ventanaSeleccionarELS.getComboELS().getSelectedItem() != null
						&& ventanaSeleccionarELS.getComboELS().getSelectedIndex() != -1) {

					ventanaGenerarPresupuesto = new VentanaGenerarPresupuesto(this);

					SpellChecker.register(ventanaGenerarPresupuesto.getTextInforme());

					ventanaGenerarPresupuesto.getBtnEditarInforme().addActionListener(this);
					ventanaGenerarPresupuesto.getBtnGuardarCambios().addActionListener(this);
					ventanaGenerarPresupuesto.getBtnCotizacionDolar().addActionListener(this);
					ventanaGenerarPresupuesto.getChckDolar().addMouseListener(this);
					ventanaGenerarPresupuesto.getChckPesos().addMouseListener(this);

					ventanaGenerarPresupuesto.getGuardarPresupuestoPDF().addActionListener(this);
					ventanaGenerarPresupuesto.getVisualizarPresupuestoPDF().addActionListener(this);

					this.ventanaGenerarPresupuesto.getTextPrecioPeso().addKeyListener(this);
					this.ventanaGenerarPresupuesto.getTextPrecioPeso().addFocusListener(new FocusListener() {
						public void focusLost(FocusEvent e) {

							if (ventanaGenerarPresupuesto.getTextPrecioPeso().getText().isEmpty()) {

								ventanaGenerarPresupuesto.getTextPrecioPeso().setText("0.0");
							}

						}

						@Override
						public void focusGained(FocusEvent arg0) {
							// TODO Auto-generated method stub

						}
					});

					this.ventanaGenerarPresupuesto.getTextPrecioDolar().addKeyListener(this);
					this.ventanaGenerarPresupuesto.getTextPrecioDolar().addFocusListener(new FocusListener() {
						public void focusLost(FocusEvent e) {

							if (ventanaGenerarPresupuesto.getTextPrecioDolar().getText().isEmpty()) {

								ventanaGenerarPresupuesto.getTextPrecioDolar().setText("0.0");
							}

						}

						@Override
						public void focusGained(FocusEvent arg0) {
							// TODO Auto-generated method stub

						}
					});

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

					int ELS = Integer.parseInt(ventanaSeleccionarELS.getComboELS().getSelectedItem().toString());
					reparacion = agenda.dameReparacionXels(ELS);

					String Cliente = reparacion.getCliente();
					String Sucursal = reparacion.getSucursal();
					String Equipo = reparacion.getNombreEquipo();
					String Marca = reparacion.getMarca();
					String Modelo = reparacion.getModelo();
					String Serie = reparacion.getNumeroDeSerie();
					String Aviso = reparacion.getAviso();
					String ClienteCliente = reparacion.getClienteCliente();
					String RemitoCliente = reparacion.getRemitoCliente();
					String Informe = reparacion.getInformecliente();
					Double PrecioPeso = reparacion.getPrecioPeso();
					Double PrecioDolar = reparacion.getPrecioDolar();

					if (reparacion.getFechaFabr() == null)
						ventanaGenerarPresupuesto.setTextFabr(null);
					else
						try {
							ventanaGenerarPresupuesto.setTextFabr((dateFormat.parse(reparacion.getFechaFabr())));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					ventanaGenerarPresupuesto.getTextCliente().setText(Cliente);
					ventanaGenerarPresupuesto.getTextSucursal().setText(Sucursal);
					ventanaGenerarPresupuesto.getTextELS()
							.setText(ventanaSeleccionarELS.getComboELS().getSelectedItem().toString());
					ventanaGenerarPresupuesto.getTextEquipo().setText(Equipo);
					ventanaGenerarPresupuesto.getTextMarca().setText(Marca);
					ventanaGenerarPresupuesto.getTextModelo().setText(Modelo);
					ventanaGenerarPresupuesto.getTextSerie().setText(Serie);
					ventanaGenerarPresupuesto.getTextAviso().setText(Aviso);
					ventanaGenerarPresupuesto.getTextClienteCliente().setText(ClienteCliente);
					ventanaGenerarPresupuesto.getTextRemCliente().setText(RemitoCliente);

					ventanaGenerarPresupuesto.getTextInforme().setText(Informe);
					ventanaGenerarPresupuesto.getTextPrecioPeso().setText(PrecioPeso.toString());
					ventanaGenerarPresupuesto.getTextPrecioDolar().setText(PrecioDolar.toString());

					ventanaGenerarPresupuesto.getTextcondicionesPago().setText("Contado.");
					ventanaGenerarPresupuesto.getTextPlazoEntrega().setText("7 días.");

					ventanaPresupuestos.dispose();
					ventanaPresupuestos = null;

					ventanaSeleccionarELS.dispose();
					ventanaSeleccionarELS = null;

				}
			}
		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnEditarInforme()) {

			Color colorActiveCaption = new Color(153, 180, 209);

			ventanaGenerarPresupuesto.getTextInforme().setEditable(true);
			ventanaGenerarPresupuesto.getTextInforme().setBackground(colorActiveCaption);

			ventanaGenerarPresupuesto.getTextPrecioPeso().setEditable(true);
			ventanaGenerarPresupuesto.getTextPrecioPeso().setBackground(colorActiveCaption);
			ventanaGenerarPresupuesto.getPanel_4().setBackground(colorActiveCaption);

			ventanaGenerarPresupuesto.getTextPrecioDolar().setEditable(true);
			ventanaGenerarPresupuesto.getTextPrecioDolar().setBackground(colorActiveCaption);
			ventanaGenerarPresupuesto.getPanel_5().setBackground(colorActiveCaption);

		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnGuardarCambios()) {

			int seleccion = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto,
					"Desea guardar los cambios hechos en el informe y en el precio de la reparación?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();

				this.agenda.editarReparacionR(reparacionAeditar);

				ventanaGenerarPresupuesto.getTextInforme().setEditable(false);
				ventanaGenerarPresupuesto.getTextInforme().setBackground(Color.LIGHT_GRAY);

				ventanaGenerarPresupuesto.getTextPrecioPeso().setEditable(false);
				ventanaGenerarPresupuesto.getTextPrecioPeso().setBackground(Color.LIGHT_GRAY);
				ventanaGenerarPresupuesto.getPanel_4().setBackground(Color.LIGHT_GRAY);

				ventanaGenerarPresupuesto.getTextPrecioDolar().setEditable(false);
				ventanaGenerarPresupuesto.getTextPrecioDolar().setBackground(Color.LIGHT_GRAY);
				ventanaGenerarPresupuesto.getPanel_5().setBackground(Color.LIGHT_GRAY);

				ventanaGenerarPresupuesto.getVisualizarPresupuestoPDF().setEnabled(true);
				ventanaGenerarPresupuesto.getGuardarPresupuestoPDF().setEnabled(true);

			}
		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnCotizacionDolar()) {

			
			String cotizacionDolar = Double.toString(consumoAPI.ConsumoAPI.consultaCotizacionDolar());

			ventanaGenerarPresupuesto.getTextCotizacionDolar().setText(cotizacionDolar);

			if (ventanaGenerarPresupuesto.getTextPrecioPeso().getText().compareTo("0.0") != 0) {

			}

			

		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getGuardarPresupuestoPDF()) {

			if (ventanaGenerarPresupuesto.getGrupoMoneda().getSelection() == null) {

				Object mje = "Debe seleccionar un moneda para agregar al presupuesto.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else {

				int seleccion2 = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto,
						"Desea generar el archivo PDF?", "Confirmación", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (seleccion2 == JOptionPane.YES_OPTION) {

					List<RegistroPresupuestoDTO> lista = new ArrayList<RegistroPresupuestoDTO>();

					RegistroPresupuestoDTO rep = TomarDatosPantallaPresupuesto();

					lista.add(rep);

					ReportePresupuesto reporte = new ReportePresupuesto(rep, lista);
					reporte.guardar();

					int seleccion3 = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto,
							"Desea enviar el Presupuesto por correo?", "Confirmación", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (seleccion3 == JOptionPane.YES_OPTION) {

						ventanaEmail = new VentanaEmail(controladorReparacion);

						String NombreCliente = ventanaGenerarPresupuesto.getTextCliente().getText();
						String Sucursal = ventanaGenerarPresupuesto.getTextSucursal().getText();
						String ELS = ventanaGenerarPresupuesto.getTextELS().getText();

						String NombreContacto = this.agenda.ContactoPorCliente(NombreCliente);
						String emailContacto = this.agenda.EmailPorCliente(NombreCliente);

						String NombrePDF = "Presupuesto ELS_" + ELS + "_" + NombreCliente + ".pdf";

						ventanaEmail.getBtnAdjuntarIMG().addActionListener(this);
						ventanaEmail.getBtnAdjunto().addActionListener(this);
						ventanaEmail.getBtnAgregarContacto().addActionListener(this);
						ventanaEmail.getBtnEditar().addActionListener(this);
						ventanaEmail.getBtnEnviar().addActionListener(this);

						ventanaEmail.getTextCliente().setText(NombreCliente + " ( " + Sucursal + " ) ");
						ventanaEmail.getTextNombreContacto().setText(NombreContacto);
						ventanaEmail.getTextEmailContacto().setText(emailContacto);

						ventanaEmail.getTextAdjunto().setText(NombrePDF);

						String empresa = "ELS - Electronic Laboratory & Services.";
						String mdp = "Suc. Mar del Plata: Independencia 2609 1er piso- Te: +54 9 223 5969934.";
						String caba = "Suc. Bs As: Arcos 4002 4º A - Buenos Aires(1429) - Te: +54 9 11 4703-2205.";
						String brc = "Suc. Bariloche: Onelli 1216 2do 5 - Te: +54 9 11 3768-8372.";
						String web = "www.elsweb.com.ar";
						String email = "E-mail: els@elsweb.com.ar";
						String Asunto = "Presupuesto ELS: " + ELS;

						String cuerpoEnvioPresupuesto = "Buenos días!\n\nAdjunto presupuesto.\nEn caso de aceptar el mismo,favor de responder este correo para poder proceder con la reparación.\nAtte.";

						ventanaEmail.getTextCuerpo().setText(cuerpoEnvioPresupuesto + "\n\n" + empresa + "\n" + mdp
								+ "\n" + caba + "\n" + brc + "\n" + web + "\n" + email);
						ventanaEmail.getTextAsunto().setText(Asunto);

						ventanaEmail.getTextCuerpo().moveCaretPosition(0);
					}

				}
			}
		}

		else if (this.ventanaEmail != null && e.getSource() == this.ventanaEmail.getBtnAgregarContacto()) {

			ventanaEmail.getTextPara().setText(ventanaEmail.getTextEmailContacto().getText());

		}

		else if (this.ventanaEmail != null && e.getSource() == this.ventanaEmail.getBtnEnviar()) {

			if (ventanaEmail.getTextPara().getText().isEmpty()) {

				Object mje = "Debe agregar al menos un destinatario al correo.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else if (!validacionMail(ventanaEmail.getTextPara().getText())) {

				JOptionPane.showMessageDialog(null, "Escriba un email correcto",
						"Error al registrar una direccion de email", JOptionPane.ERROR_MESSAGE);
			} else

			{
				int seleccion = JOptionPane.showConfirmDialog(ventanaEmail, "Desea enviar el Informe al cliente",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (seleccion == JOptionPane.YES_OPTION) {

					String correo = ventanaEmail.getTextPara().getText();
					String Asunto = ventanaEmail.getTextAsunto().getText();
					String Cuerpo = ventanaEmail.getTextCuerpo().getText();
					String NombrePDF = ventanaEmail.getTextAdjunto().getText();

					if (mails.EnviarMail.enviarInformeAlCliente(correo, Asunto, Cuerpo, NombrePDF)) {

						presupuestoEnviado = true;
						ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();
						this.agenda.editarReparacionR(reparacionAeditar);

					}

				}

			}
		}

		else if (this.ventanaEmail != null && e.getSource() == this.ventanaEmail.getBtnEditar()) {

			ventanaEmail.getTextCuerpo().setEditable(true);

		}

		else if (this.ventanaEmail != null && e.getSource() == this.ventanaEmail.getBtnAdjunto()) {

			String NombrePDF = ventanaEmail.getTextAdjunto().getText();

			try {
				File path = new File("F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/" + NombrePDF);
				Desktop.getDesktop().open(path);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getVisualizarPresupuestoPDF()) {

			if (ventanaGenerarPresupuesto.getGrupoMoneda().getSelection() == null) {

				Object mje = "Debe seleccionar un moneda para agregar al presupuesto.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else {
				List<RegistroPresupuestoDTO> lista = new ArrayList<RegistroPresupuestoDTO>();

				RegistroPresupuestoDTO rep = TomarDatosPantallaPresupuesto();

				lista.add(rep);

				ReportePresupuesto reporte = new ReportePresupuesto(rep, lista);
				reporte.mostrar();
			}

		}
	}
	//
	// if (btnpago) {
	//
	// System.out.println("chau");
	//

	private void cargarTablaListadoReparaciones() {

		this.ventanaListadoReparacionesPresupuestos.getModelReparaciones().setRowCount(0); // Para
		// vaciar
		// tabla
		this.ventanaListadoReparacionesPresupuestos.getModelReparaciones().setColumnCount(0);
		this.ventanaListadoReparacionesPresupuestos.getModelReparaciones()
				.setColumnIdentifiers(this.ventanaListadoReparacionesPresupuestos.getNombreColumnas());

		this.Reparaciones_en_tabla = (List<ReparacionDTO>) agenda.obtenerReparacion();

		for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {

			Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(),
					this.Reparaciones_en_tabla.get(i).getFecha_Entrada(),
					this.Reparaciones_en_tabla.get(i).getCliente(), this.Reparaciones_en_tabla.get(i).getSucursal(),
					this.Reparaciones_en_tabla.get(i).getNombreEquipo(), this.Reparaciones_en_tabla.get(i).getMarca(),
					this.Reparaciones_en_tabla.get(i).getModelo(), this.Reparaciones_en_tabla.get(i).getNumeroDeSerie(),
					this.Reparaciones_en_tabla.get(i).getAviso(),
					this.Reparaciones_en_tabla.get(i).getFechadereparacion(),
					this.Reparaciones_en_tabla.get(i).getClienteCliente(),
					this.Reparaciones_en_tabla.get(i).getEstadoTecnico(),
					this.Reparaciones_en_tabla.get(i).getEstadoComercial(),
					this.Reparaciones_en_tabla.get(i).getEstadoFisico(),
					this.Reparaciones_en_tabla.get(i).getNombreUsuario(), this.Reparaciones_en_tabla.get(i).getCodigo(),
					this.Reparaciones_en_tabla.get(i).getNumeroRemitoSalida(),
					this.Reparaciones_en_tabla.get(i).getPresupuestoGenerado(),
					this.Reparaciones_en_tabla.get(i).getInformeEnviado(),
					this.Reparaciones_en_tabla.get(i).getPrecioPeso(),
					this.Reparaciones_en_tabla.get(i).getPrecioDolar(), this.Reparaciones_en_tabla.get(i).getPago(), };
			this.ventanaListadoReparacionesPresupuestos.getModelReparaciones().addRow(fila);
		}

		ventanaListadoReparacionesPresupuestos
				.setCellRender(this.ventanaListadoReparacionesPresupuestos.getTblReparaciones());

		this.ventanaListadoReparacionesPresupuestos.show();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (ventanaListadoReparacionesPresupuestos != null) {
			if (arg0.getSource() == this.ventanaListadoReparacionesPresupuestos.getBtnMax()) {

				if (clickMax % 2 != 0) {

					ventanaListadoReparacionesPresupuestos.setExtendedState(max);
					this.ventanaListadoReparacionesPresupuestos.getBtnMax()
							.setIcon(new ImageIcon(this.getClass().getResource("/minimizar.png")));
					ventanaListadoReparacionesPresupuestos.setVisible(true);

				} else {

					ventanaListadoReparacionesPresupuestos.setExtendedState(min);
					this.ventanaListadoReparacionesPresupuestos.getBtnMax()
							.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
					ventanaListadoReparacionesPresupuestos.setVisible(true);

				}
				clickMax++;
			}
		}

		if (this.ventanaGenerarPresupuesto != null) {

			if (this.ventanaGenerarPresupuesto.getGrupoMoneda()
					.isSelected(this.ventanaGenerarPresupuesto.getChckPesos().getModel())) {

				ventanaGenerarPresupuesto.getTextcondicionesMoneda().setText(
						"Los precios están expresados en Pesos, son Netos y no incluyen el IVA (21%). La garantía es de 90 días sobre la reparación realizada");

			}

			else {
				ventanaGenerarPresupuesto.getTextcondicionesMoneda().setText(
						"Los precios están expresados en Dólares, son Netos y no incluyen el IVA (21%). La garantía es de 90 días sobre la reparación realizada");

			}
		}

	}

	private void llenarComboELS() {

		agenda.ListarELS(ventanaSeleccionarELS.getComboELS());

		ventanaSeleccionarELS.getComboELS().setSelectedIndex(-1);

	}

	private ReparacionDTO TomarDatosPresupuesto() {

		int ELS = Integer.parseInt(this.ventanaGenerarPresupuesto.getTextELS().getText());
		String informeCliente = this.ventanaGenerarPresupuesto.getTextInforme().getText();
		double PrecioPeso = Double.parseDouble(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText());
		double PrecioDolar = Double.parseDouble(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText());

		if (PrecioPeso != 0) {
			presupuestoGenerado = true;
		}
		System.out.println(presupuestoEnviado);

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, informeCliente, PrecioPeso, PrecioDolar,
				presupuestoGenerado, presupuestoEnviado);

		return reparacionAeditar;

	}

	private RegistroPresupuestoDTO TomarDatosPantallaPresupuesto() {
		// TODO Auto-generated method stub

		int ELS = Integer.parseInt(this.ventanaGenerarPresupuesto.getTextELS().getText());
		String InformeCliente = this.ventanaGenerarPresupuesto.getTextInforme().getText();
		String RemitoCLiente = this.ventanaGenerarPresupuesto.getTextRemCliente().getText();
		String NombreEquipo = this.ventanaGenerarPresupuesto.getTextEquipo().getText();
		String Modelo = this.ventanaGenerarPresupuesto.getTextModelo().getText();
		String Marca = this.ventanaGenerarPresupuesto.getTextMarca().getText();
		String Serie = this.ventanaGenerarPresupuesto.getTextSerie().getText();
		String aviso = this.ventanaGenerarPresupuesto.getTextAviso().getText();
		String ClienteCliente = this.ventanaGenerarPresupuesto.getTextClienteCliente().getText();
		String Cliente = this.ventanaGenerarPresupuesto.getTextCliente().getText();
		String Sucursal = this.ventanaGenerarPresupuesto.getTextCliente().getText();
		boolean chckpesos = this.ventanaGenerarPresupuesto.getChckPesos().isSelected();
		boolean chckdolar = this.ventanaGenerarPresupuesto.getChckDolar().isSelected();
		String CondicionesMoneda = this.ventanaGenerarPresupuesto.getTextcondicionesMoneda().getText();
		String CondicionesPago = this.ventanaGenerarPresupuesto.getTextcondicionesPago().getText();
		String plazoEntrega = this.ventanaGenerarPresupuesto.getTextPlazoEntrega().getText();

		Double PrecioPeso = Double.parseDouble(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText());
		Double PrecioDolar = Double.parseDouble(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText());

		RegistroPresupuestoDTO nuevoPresupuesto = new RegistroPresupuestoDTO(ELS, InformeCliente, RemitoCLiente,
				PrecioPeso, PrecioDolar, NombreEquipo, Modelo, Marca, Serie, ClienteCliente, aviso, Sucursal, Cliente,
				chckpesos, chckdolar, CondicionesMoneda, CondicionesPago, plazoEntrega);

		return nuevoPresupuesto;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();

		if (this.ventanaGenerarPresupuesto != null) {

			if (e.getSource() == this.ventanaGenerarPresupuesto.getTextPrecioPeso()) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (ventanaGenerarPresupuesto.getTextPrecioPeso().getText().isEmpty()) {

						ventanaGenerarPresupuesto.getTextPrecioPeso().setText("0.0");
					}

				}

			}

			if (e.getSource() == this.ventanaGenerarPresupuesto.getTextPrecioDolar()) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (ventanaGenerarPresupuesto.getTextPrecioDolar().getText().isEmpty()) {

						ventanaGenerarPresupuesto.getTextPrecioDolar().setText("0.0");
					}

				}

			}

		}

		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}