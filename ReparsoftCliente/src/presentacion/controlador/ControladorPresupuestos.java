package presentacion.controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import javax.swing.*;
import java.awt.*;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.util.Units;

import java.io.FileInputStream;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.google.gson.JsonSyntaxException;
import com.inet.jortho.SpellChecker;

import VistaPropias.CorrectorGramaticalAPI;
import VistaPropias.CorrectorGramaticalAPI.ErrorGramatical;
import VistaPropias.CorrectorGramaticalAPI.ResultadoRevision;
import VistaPropias.DialogoRevisionGramatical;
import modelo.Agenda;
import presentacion.reportes.ReportePresupuesto;
import presentacion.vista.VentanaAgregarImagenes;
import presentacion.vista.VentanaEmail;
import presentacion.vista.VentanaGenerarPresupuesto;
import presentacion.vista.VentanaHistorialPrecios;
import presentacion.vista.VentanaIngresoDePago;
import presentacion.vista.VentanaMarcarAceptaciones;
import presentacion.vista.VentanaPresupuestos;
import presentacion.vista.VentanaSeleccionarELS;
import tiposPropios.MonedaFormatter;
import dto.RegistroPresupuestoDTO;
import dto.ReparacionDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

import java.io.FileOutputStream;

public class ControladorPresupuestos implements ActionListener, MouseListener, ItemListener, KeyListener {

	private VentanaPresupuestos ventanaPresupuestos;
	private VentanaIngresoDePago ventanaIngresoDePago;
	private VentanaSeleccionarELS ventanaSeleccionarELS;
	private VentanaGenerarPresupuesto ventanaGenerarPresupuesto;
	private VentanaEmail ventanaEmail;
	private VentanaAgregarImagenes ventanaAgregarImagenes;
	private VentanaMarcarAceptaciones ventanaMarcarAceptaciones;
	private VentanaHistorialPrecios ventanaHistorialPrecios;

	// private ControladorReparacion controladorReparacion;

	private Agenda agenda;

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	private ReparacionDTO reparacion;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	String numeros = "";
	boolean guardado = false;

	private boolean btnPresupuestoxELS = false;
	private boolean btnpago = false;

	private boolean presupuestoGenerado = false;
	private boolean btnPresupuestoPDF = false;
	private boolean presupuestoEnviado = false;
	private boolean informeWordGenerado = false;
	private boolean informeWordEnviado = false;

	private int max = Frame.MAXIMIZED_BOTH;
	private int min = Frame.NORMAL;

	private int clickMax = 1;
	// private int clickMin = 1;

	private MonedaFormatter monedaFormatter;

	private JFrame frame;

	// this.ventanaGenerarPresupuesto.getImagePath().setText("img\\anterior.png");
	private String imagePath = "";
	private String rutaImagen_1 = "";
	private String rutaImagen_2 = "";
	private String rutaImagen_3 = "";
	private String rutaImagen_4 = "";
	private String rutaImagen_5 = "";
	private String rutaImagen_6 = "";

	private ArrayList<File> archivosAdjuntos = new ArrayList<>();

	private ArrayList<File> archivosAdjuntosExtras = new ArrayList<>();

	public ControladorPresupuestos(VentanaPresupuestos ventanaPresupuestos, Agenda agenda) {

		this.ventanaPresupuestos = ventanaPresupuestos;

		this.ventanaPresupuestos.getBtningresarPago().addActionListener(this);
		this.ventanaPresupuestos.getBtnmarcarAceptaciones().addActionListener(this);
		this.ventanaPresupuestos.getBtnPresupuestoPorELS().addActionListener(this);

		this.agenda = agenda;

	}

	public void actionPerformed(ActionEvent e) {

		if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtningresarPago()) {

			btnPresupuestoxELS = false;
			btnpago = true;

			ventanaSeleccionarELS = new VentanaSeleccionarELS(this);

			ventanaSeleccionarELS.getComboELS().addActionListener(this);
			ventanaSeleccionarELS.getBtnAceptar().addActionListener(this);
			ventanaSeleccionarELS.getBtnCancelar().addActionListener(this);

			AutoCompleteDecorator.decorate(ventanaSeleccionarELS.getComboELS());

			llenarComboELS();

		}

		else if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtnmarcarAceptaciones()) {

			ventanaMarcarAceptaciones = new VentanaMarcarAceptaciones(this);
			cerraVentanaMarcarAceptaciones();
			agregarListenerAMarcarAceptaciones();
			llenarComboAviso();
			llenarComboCliente();
			llenarComboELS();
			llenarComboSucursales();
			cargarTablaMarcarAceptaciones();
			initCheckboxListeners();

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
					monedaFormatter = new MonedaFormatter();

					SpellChecker.register(ventanaGenerarPresupuesto.getTextInforme());

					TomarDatosDeTablas();

					agregarListenersVentanaGenerarPresupuesto();

				}
			}

			if (btnpago) {

				if (ventanaSeleccionarELS.getComboELS().getSelectedItem() != null
						&& ventanaSeleccionarELS.getComboELS().getSelectedIndex() != -1) {

					ventanaIngresoDePago = new VentanaIngresoDePago(this);
					cerraVentanaAgregarPrecio();
					monedaFormatter = new MonedaFormatter();

					TomarDatosDeTablas();

					if (ventanaIngresoDePago.gettextIngresoPago().getText().compareTo("$ 0,00") != 0) {

						ventanaIngresoDePago.gettextIngresoPago().setEditable(false);

					} else {

						ventanaIngresoDePago.gettextIngresoPago().setEditable(true);
						ventanaIngresoDePago.gettextIngresoPago().requestFocus();
						ventanaIngresoDePago.gettextIngresoPago().selectAll();

					}

					ventanaIngresoDePago.getBtnEditarPrecios().addActionListener(this);
					ventanaIngresoDePago.getBtnGuardarCambios().addActionListener(this);

					ventanaIngresoDePago.getTextPrecioPeso().addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							String precioPeso = ventanaIngresoDePago.getTextPrecioPeso().getText();
							ventanaIngresoDePago.getTextPrecioPeso().setText(monedaFormatter.formatPeso(precioPeso));

						}
					});

					ventanaIngresoDePago.getTextPrecioDolar().addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							String precioDolar = ventanaIngresoDePago.getTextPrecioDolar().getText();
							ventanaIngresoDePago.getTextPrecioDolar().setText(monedaFormatter.formatDolar(precioDolar));

						}
					});

					ventanaIngresoDePago.gettextIngresoPago().addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							String pagoPesos = ventanaIngresoDePago.gettextIngresoPago().getText();
							ventanaIngresoDePago.gettextIngresoPago().setText(monedaFormatter.formatPeso(pagoPesos));

						}
					});

					performActionOnTextComponents(ventanaIngresoDePago);
				}

			}

		}

		else if (this.ventanaIngresoDePago != null
				&& e.getSource() == this.ventanaIngresoDePago.getBtnEditarPrecios()) {

			ventanaIngresoDePago.gettextIngresoPago().setEditable(true);
			ventanaIngresoDePago.getTextPrecioDolar().setEditable(true);
			ventanaIngresoDePago.getTextPrecioPeso().setEditable(true);

		}

		else if (this.ventanaIngresoDePago != null
				&& e.getSource() == this.ventanaIngresoDePago.getBtnGuardarCambios()) {

			int seleccion = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto, "Desea guardar el pago realizado?",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				ReparacionDTO reparacionAeditar = TomarDatosPago();

				this.agenda.editarReparacionPago(reparacionAeditar);
			}

		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnEditarInforme()) {

			Color amarilloClaro = new Color(237,232,208);

			ventanaGenerarPresupuesto.getTextInforme().setEditable(true);
			ventanaGenerarPresupuesto.getTextInforme().setBackground(amarilloClaro);

			ventanaGenerarPresupuesto.getTextPrecioPeso().setEditable(true);
			ventanaGenerarPresupuesto.getTextPrecioPeso().setBackground(amarilloClaro);
			ventanaGenerarPresupuesto.getPanel_4().setBackground(amarilloClaro);

			ventanaGenerarPresupuesto.getTextPrecioDolar().setEditable(true);
			ventanaGenerarPresupuesto.getTextPrecioDolar().setBackground(amarilloClaro);
			ventanaGenerarPresupuesto.getPanel_5().setBackground(amarilloClaro);

			//habilitar boton de gramatica y gurdar cambios, y deshabilitar los botones de generar PDF y WORD mientras se editan los campos
			ventanaGenerarPresupuesto.getBtnGramatica().setEnabled(true);
			ventanaGenerarPresupuesto.getBtnGuardarCambios().setEnabled(true);
			ventanaGenerarPresupuesto.getVisualizarPresupuestoPDF().setEnabled(false);
			ventanaGenerarPresupuesto.getGuardarPresupuestoPDF().setEnabled(false);
			ventanaGenerarPresupuesto.getBtnGenerarInformeSiemens().setEnabled(false);
						
			
		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnGuardarCambios()) {

			int seleccion = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto,
					"Desea guardar los cambios hechos en el informe y en el precio de la reparación?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();

				this.agenda.editarReparacionPresupuesto(reparacionAeditar);

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
				ventanaGenerarPresupuesto.getBtnGenerarInformeSiemens().setEnabled(true);
				
				ventanaGenerarPresupuesto.getBtnGramatica().setEnabled(false);
				ventanaGenerarPresupuesto.getBtnGuardarCambios().setEnabled(false);
				

			}
		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnGramatica()) {

						
			revisarGramaticaActionPerformed(ventanaGenerarPresupuesto.getTextInforme(), ventanaGenerarPresupuesto);
			
		
		}
		

	

	// Java
	else if(this.ventanaGenerarPresupuesto!=null&&e.getSource()==this.ventanaGenerarPresupuesto.getBtnCotizacionDolar())

	{

		DecimalFormat df = new DecimalFormat("#.##");
		double[] cotizaciones = consumoAPI.ConsumoAPI.consultaCotizacionDolar();

		String cotizacionDolarOf = Double.toString(cotizaciones[0]);
		String cotizacionDolarBl = Double.toString(cotizaciones[1]);

		// Usar MonedaFormatter para parsear correctamente los campos
		String textoPeso = ventanaGenerarPresupuesto.getTextPrecioPeso().getText();
		String textoDolar = ventanaGenerarPresupuesto.getTextPrecioDolar().getText();

		double presupuestoPesos = monedaFormatter.parseAmount(textoPeso);
		double presupuestoDolar = monedaFormatter.parseAmount(textoDolar);

		ventanaGenerarPresupuesto.getTextCotizacionDolarOf().setText(cotizacionDolarOf);
		ventanaGenerarPresupuesto.getTextCotizacionDolarBl().setText(cotizacionDolarBl);

		// Depuración
		System.out.println("textoPeso: " + textoPeso);
		System.out.println("textoDolar: " + textoDolar);
		System.out.println("presupuestoPesos: " + presupuestoPesos);
		System.out.println("presupuestoDolar: " + presupuestoDolar);

		boolean pesosCero = Math.abs(presupuestoPesos) < 0.0001;
		boolean dolarCero = Math.abs(presupuestoDolar) < 0.0001;

		System.out.println("pesosCero: " + pesosCero);
		System.out.println("dolarCero: " + dolarCero);

		if (!pesosCero && dolarCero) {
			double sugerenciaDolar = presupuestoPesos / cotizaciones[0];
			ventanaGenerarPresupuesto.getTextSugerenciaDolar().setText(df.format(sugerenciaDolar));
			ventanaGenerarPresupuesto.getTextSugerenciaPeso().setText(df.format(presupuestoPesos));
			System.out.println("Sugerencia: solo pesos, calculando dólares");
		} else if (pesosCero && !dolarCero) {
			double sugerenciaPeso = presupuestoDolar * cotizaciones[0];
			ventanaGenerarPresupuesto.getTextSugerenciaPeso().setText(df.format(sugerenciaPeso));
			ventanaGenerarPresupuesto.getTextSugerenciaDolar().setText(df.format(presupuestoDolar));
			System.out.println("Sugerencia: solo dólares, calculando pesos");
		} else {
			ventanaGenerarPresupuesto.getTextSugerenciaDolar().setText("");
			ventanaGenerarPresupuesto.getTextSugerenciaPeso().setText("");
			System.out.println("No se muestra sugerencia");
		}
	}

	else if (this.ventanaGenerarPresupuesto != null 
		      && e.getSource() == this.ventanaGenerarPresupuesto.getBtnHistorialDePrecios()) {

		    ventanaHistorialPrecios = new VentanaHistorialPrecios();
		    new ControladorHistorialPrecios(ventanaHistorialPrecios, ventanaGenerarPresupuesto, agenda);
		}
	
	else if(this.ventanaGenerarPresupuesto!=null&&e.getSource()==this.ventanaGenerarPresupuesto.getGuardarPresupuestoPDF())
	{

		btnPresupuestoPDF = true;

		if (ventanaGenerarPresupuesto.getGrupoMoneda().getSelection() == null) {

			Object mje = "Debe seleccionar un moneda para agregar al presupuesto.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

		} else {

			int seleccion2 = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto, "Desea generar el archivo PDF?",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion2 == JOptionPane.YES_OPTION) {

				ventanaAgregarImagenes = new VentanaAgregarImagenes(this);

				agregarListenersVentanaImagenes();

			}
		}
	}

	else if(this.ventanaGenerarPresupuesto!=null&&e.getSource()==this.ventanaGenerarPresupuesto.getBtnGenerarInformeSiemens())
	{

		btnPresupuestoPDF = false;

		if (ventanaGenerarPresupuesto.getGrupoMoneda().getSelection() == null) {

			Object mje = "Debe seleccionar un moneda para agregar al informe.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

		} else {

			int seleccion2 = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto, "Desea generar el archivo WORD?",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion2 == JOptionPane.YES_OPTION) {

				ventanaAgregarImagenes = new VentanaAgregarImagenes(this);

				agregarListenersVentanaImagenes();

			}
		}
	}else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnAgregarImagen())
	{

		agregarImagenesIngreso();

	}

	else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnAgregarImagenDiagnostico())
	{

		agregarImagenesDiagnostico();
	}

	else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnBorrarImagen_1())
	{

		ventanaAgregarImagenes.getTxtRutaImagen_1().setText("");

	}else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnBorrarImagen_2())
	{

		ventanaAgregarImagenes.getTxtRutaImagen_2().setText("");

	}else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnBorrarImagen_3())
	{

		ventanaAgregarImagenes.getTxtRutaImagen_3().setText("");

	}else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnBorrarImagen_4())
	{

		ventanaAgregarImagenes.getTxtRutaImagen_4().setText("");

	}else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnBorrarImagen_5())
	{

		ventanaAgregarImagenes.getTxtRutaImagen_5().setText("");

	}else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnBorrarImagen_6())
	{

		ventanaAgregarImagenes.getTxtRutaImagen_6().setText("");

	}

	else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtngenerarInforme())
	{

		rutaImagen_1 = ventanaAgregarImagenes.getTxtRutaImagen_1().getText();
		rutaImagen_2 = ventanaAgregarImagenes.getTxtRutaImagen_2().getText();
		rutaImagen_3 = ventanaAgregarImagenes.getTxtRutaImagen_3().getText();
		rutaImagen_4 = ventanaAgregarImagenes.getTxtRutaImagen_4().getText();
		rutaImagen_5 = ventanaAgregarImagenes.getTxtRutaImagen_5().getText();
		rutaImagen_6 = ventanaAgregarImagenes.getTxtRutaImagen_6().getText();

		//String emailPrueba = "diego.bertossi@gmail.com";

		ventanaAgregarImagenes.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		if (btnPresupuestoPDF) {

			// rutaImagen_1 = ventanaAgregarImagenes.getTxtRutaImagen_1().getText();

			List<RegistroPresupuestoDTO> lista = new ArrayList<RegistroPresupuestoDTO>();
			RegistroPresupuestoDTO rep = TomarDatosPantallaPresupuesto();

			lista.add(rep);

			ReportePresupuesto reporte = new ReportePresupuesto(rep, lista, agenda);
			reporte.guardar();

			ventanaGenerarPresupuesto.setChckPDFGenerado(true);

			ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();
			this.agenda.editarReparacionPresupuesto(reparacionAeditar);

			int seleccion3 = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto,
					"Desea enviar el Presupuesto por correo?", "Confirmación", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (seleccion3 == JOptionPane.YES_OPTION) {

				ventanaEmail = new VentanaEmail();

				String NombreCliente = ventanaGenerarPresupuesto.getTextCliente().getText();
				String Sucursal = ventanaGenerarPresupuesto.getTextSucursal().getText();
				String ELS = ventanaGenerarPresupuesto.getTextELS().getText();
				String NombreContacto = this.agenda.ContactoPorCliente(NombreCliente);
				String emailContacto = this.agenda.EmailPorCliente(NombreCliente);
				String NombrePDF = "Presupuesto ELS_" + ELS + "_" + NombreCliente + ".pdf";

				agregarListenerAventanaEmail();

				ventanaEmail.getTextCliente().setText(NombreCliente + " ( " + Sucursal + " ) ");
				ventanaEmail.getTextNombreContacto().setText(NombreContacto);

				 ventanaEmail.getTextEmailContacto().setText(emailContacto);
				//ventanaEmail.getTextEmailContacto().setText(emailPrueba);

				ventanaEmail.getTextAdjunto().setText(NombrePDF);

				String empresa = "ELS - Electronic Laboratory & Services.";
				String mdp = "Mar del Plata: Avellaneda 2766 1 piso MDP -(7600) - Te: +54 9 223 5969934. NUEVA DIRECCION.";
				String caba = "Bs As: Arcos 4002 4 A - Buenos Aires(1429) - Te: +54 9 11 4703-2205.";
				String brc = "Bariloche: 9 de julio 710 - Bariloche (8400) - Te: +54 9 11 3768-8372..";
				String web = "www.elsweb.com.ar";
				String email = "E-mail: els@elsweb.com.ar";
				String Asunto = "Presupuesto ELS: " + ELS;
				String cuerpoEnvioPresupuesto = "Buenos días!\n\nAdjunto presupuesto.\nEn caso de aceptar el mismo,favor de responder este correo para poder proceder con la reparación.\nAtte.";

				ventanaEmail.getTextCuerpo().setText(cuerpoEnvioPresupuesto + "\n\n" + empresa + "\n" + mdp + "\n"
						+ caba + "\n" + brc + "\n" + web + "\n" + email);
				ventanaEmail.getTextAsunto().setText(Asunto);
				ventanaEmail.getTextCuerpo().moveCaretPosition(0);
			}

		}

		else {

			String nombreWordBase = "Modelo Generico de informe 2023.docx";
			String documentoBase = "";
			String pathBase = "";

			switch (agenda.getUbicacionBase()) {
			case "Bariloche":
				pathBase = "F:/els/Bariloche/Administracion/Sistema/Informes Siemens/";
				break;

			case "Buenos Aires":
				pathBase = "F:/els/Administracion/Sistema/Informes Siemens/";
				break;

			default:
				break;
			}

			documentoBase = pathBase + nombreWordBase;

			LocalDate fechaHoy = LocalDate.now();
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");
			String fechaHoyString = fechaHoy.format(formato);

			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

			String els = ventanaGenerarPresupuesto.getTextELS().getText();
			String aviso = ventanaGenerarPresupuesto.getTextAviso().getText();
			String cliente = ventanaGenerarPresupuesto.getTextCliente().getText();
			String sucursal = ventanaGenerarPresupuesto.getTextSucursal().getText();
			String equipo = ventanaGenerarPresupuesto.getTextEquipo().getText();
			String modelo = ventanaGenerarPresupuesto.getTextModelo().getText();
			String serie = ventanaGenerarPresupuesto.getTextSerie().getText();

			String fechaFabricacion = ventanaGenerarPresupuesto.getTextFabrString();

			String diagnostico = ventanaGenerarPresupuesto.getTextInforme().getText();
			String precioDolar = ventanaGenerarPresupuesto.getTextPrecioDolar().getText();
			String plazoEntrega = ventanaGenerarPresupuesto.getTextPlazoEntrega().getText();

			String nombreWordNuevo = "AV " + aviso + "-" + "ELS " + els + "_" + cliente + ".docx";
			String nuevoDocumento = pathBase + nombreWordNuevo;

			try {
				XWPFDocument doc = new XWPFDocument(new FileInputStream(documentoBase));

				buscarYReemplazar(doc, "#fecha#", fechaHoyString, "");
				buscarYReemplazar(doc, "#aviso#", aviso, "");
				buscarYReemplazar(doc, "#cliente#", cliente, "");
				buscarYReemplazar(doc, "#equipo#", equipo, "");
				buscarYReemplazar(doc, "#modelo#", modelo, "");
				buscarYReemplazar(doc, "#serie#", serie, "");
				buscarYReemplazar(doc, "#fechafabr#", fechaFabricacion, "");
				buscarYReemplazar(doc, "#diagnostico#", diagnostico, "");
				buscarYReemplazar(doc, "#PrecioD#", precioDolar, "");
				buscarYReemplazar(doc, "#Plazo#", plazoEntrega, "");
				buscarYReemplazar(doc, "#Imagen1#", "", rutaImagen_1);
				buscarYReemplazar(doc, "#Imagen2#", "", rutaImagen_2);
				buscarYReemplazar(doc, "#Imagen3#", "", rutaImagen_3);
				buscarYReemplazar(doc, "#Imagen4#", "", rutaImagen_4);
				buscarYReemplazar(doc, "#Imagen5#", "", rutaImagen_5);
				buscarYReemplazar(doc, "#Imagen6#", "", rutaImagen_6);

				FileOutputStream out = new FileOutputStream(nuevoDocumento);
				doc.write(out);
				out.close();
				doc.close();

				JOptionPane.showMessageDialog(null, "Documento generado exitosamente.");

				ventanaGenerarPresupuesto.setChckWORDGenerado(true);

				ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();
				this.agenda.editarReparacionPresupuesto(reparacionAeditar);

				int seleccion3 = JOptionPane.showConfirmDialog(null, "Desea enviar el informe WORD por correo?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (seleccion3 == JOptionPane.YES_OPTION) {

					ventanaEmail = new VentanaEmail();

					String NombreContacto = this.agenda.ContactoPorCliente(cliente);
					String emailContacto = this.agenda.EmailPorCliente(cliente);
					// String NombreWORD = "AV " + aviso + "_" + cliente + ".pdf";

					agregarListenerAventanaEmail();

					ventanaEmail.getTextCliente().setText(cliente + " ( " + sucursal + " ) ");
					ventanaEmail.getTextNombreContacto().setText(NombreContacto);

					//ventanaEmail.getTextEmailContacto().setText(emailPrueba);
					ventanaEmail.getTextEmailContacto().setText(emailContacto);
					ventanaEmail.getTextAdjunto().setText(nombreWordNuevo);

					String empresa = "ELS - Electronic Laboratory & Services.";
					String mdp = "Mar del Plata: Avellaneda 2766 1 piso MDP -(7600) - Te: +54 9 223 5969934. NUEVA DIRECCION.";
					String caba = "Bs As: Arcos 4002 4 A - Buenos Aires(1429) - Te: +54 9 11 4703-2205.";
					String brc = "Bariloche: 9 de julio 710 - Bariloche (8400) - Te: +54 9 11 3768-8372..";
					String web = "www.elsweb.com.ar";
					String email = "E-mail: els@elsweb.com.ar";
					String Asunto = "Informe " + nombreWordNuevo;
					String cuerpoEnvioPresupuesto = "Buenos días!\n\nAdjunto el informe correspondiente.\n";

					ventanaEmail.getTextCuerpo().setText(cuerpoEnvioPresupuesto + "\n\n" + empresa + "\n" + mdp + "\n"
							+ caba + "\n" + brc + "\n" + web + "\n" + email);
					ventanaEmail.getTextAsunto().setText(Asunto);
					ventanaEmail.getTextCuerpo().moveCaretPosition(0);
				}

			} catch (IOException f) {
				f.printStackTrace();
			}
		}

		ventanaAgregarImagenes.setCursor(Cursor.getDefaultCursor());

		ventanaAgregarImagenes.dispose();
		ventanaAgregarImagenes = null;

	}else if(this.ventanaAgregarImagenes!=null&&e.getSource()==this.ventanaAgregarImagenes.getBtnCancelar())
	{

		this.ventanaAgregarImagenes.dispose();
		this.ventanaAgregarImagenes = null;
	}

	else if(this.ventanaEmail!=null&&e.getSource()==this.ventanaEmail.getBtnAgregarContacto())
	{

		ventanaEmail.getTextPara().setText(ventanaEmail.getTextEmailContacto().getText());

	}

	else if(this.ventanaEmail!=null&&e.getSource()==this.ventanaEmail.getBtnEnviar())
	{

		if (ventanaEmail.getTextPara().getText().isEmpty()) {
			Object mje = "Debe agregar al menos un destinatario al correo.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

		} else if (!validacionMail(ventanaEmail.getTextPara().getText())) {
			JOptionPane.showMessageDialog(null, "Escriba un email correcto",
					"Error al registrar una direccion de email", JOptionPane.ERROR_MESSAGE);
		} else {
			int seleccion = JOptionPane.showConfirmDialog(ventanaEmail, "Desea enviar el Informe al cliente",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (seleccion == JOptionPane.YES_OPTION) {

				enviarMail();

			}
		}
	}

	else if(this.ventanaEmail!=null&&e.getSource()==this.ventanaEmail.getBtnEditar())
	{

		ventanaEmail.getTextCuerpo().setEditable(true);

	}

	else if(this.ventanaEmail!=null&&e.getSource()==this.ventanaEmail.getBtnAdjuntarArchivo())
	{

		// En tu clase controlador, agregar como atributo
		agregarArchivosAdjuntos();

	}

	else if(this.ventanaEmail!=null && e.getSource()==this.ventanaEmail.getBtnAdjunto()) {

	    String nombreArchivo = ventanaEmail.getTextAdjunto().getText();
	    String ubicacion = agenda.getUbicacionBase();

	    String rutaBase;
	    switch (ubicacion) {
	    case "Bariloche":
	        rutaBase = "F:/ELS/Bariloche/Administracion/Sistema/";
	        break;
	    case "Buenos Aires":
	        rutaBase = "F:/ELS/Administracion/Sistema/";
	        break;
	    default:
	        System.err.println("Ubicación no válida: " + ubicacion);
	        return;
	    }

	    // CORRECCIÓN: determinar subdirectorio por extensión, no por estado del booleano
	    String subdirectorio;
	    if (nombreArchivo.endsWith(".pdf")) {
	        subdirectorio = "Presupuestos PDF/";
	    } else if (nombreArchivo.endsWith(".docx")) {
	        subdirectorio = "Informes Siemens/";
	    } else {
	        subdirectorio = "";
	    }

	    File path = new File(rutaBase + subdirectorio + nombreArchivo);

	    try {
	        Desktop.getDesktop().open(path);
	    } catch (IOException ex) {
	        System.err.println("Error al abrir el archivo: " + path.getAbsolutePath());
	        ex.printStackTrace();
	    }
	}

	else if(this.ventanaGenerarPresupuesto!=null&&e.getSource()==this.ventanaGenerarPresupuesto.getVisualizarPresupuestoPDF())
	{

		if (ventanaGenerarPresupuesto.getGrupoMoneda().getSelection() == null) {

			Object mje = "Debe seleccionar un moneda para agregar al presupuesto.";
			JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

		} else {
			List<RegistroPresupuestoDTO> lista = new ArrayList<RegistroPresupuestoDTO>();

			RegistroPresupuestoDTO rep = TomarDatosPantallaPresupuesto();

			lista.add(rep);

			ReportePresupuesto reporte = new ReportePresupuesto(rep, lista, agenda);
			reporte.mostrar();
		}

	}

	else if(ventanaMarcarAceptaciones!=null&&e.getSource()==this.ventanaMarcarAceptaciones.getBtnFiltrar())
	{

		DefaultTableModel dm;
		dm = (DefaultTableModel) this.ventanaMarcarAceptaciones.getTblReparaciones().getModel();

		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);

		this.ventanaMarcarAceptaciones.getTblReparaciones().setRowSorter(tr);

		RowFilter<DefaultTableModel, Object> rf = null;
		List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>();

		if (!ventanaMarcarAceptaciones.getRadioButtonCliente().isSelected()
				&& !ventanaMarcarAceptaciones.getRadioButtonSucursal().isSelected()
				&& !ventanaMarcarAceptaciones.getRadioButtonAviso().isSelected()
				&& !ventanaMarcarAceptaciones.getRadioButtonELS().isSelected()) {
			this.ventanaMarcarAceptaciones.getTblReparaciones().setRowSorter(null);
		}

		if (ventanaMarcarAceptaciones.getRadioButtonCliente().isSelected()
				&& ventanaMarcarAceptaciones.getComboFiltroCliente().getSelectedItem() != null
				&& ventanaMarcarAceptaciones.getComboFiltroCliente().getSelectedItem().toString() != null) {
			String searchText = ventanaMarcarAceptaciones.getComboFiltroCliente().getSelectedItem().toString();
			rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 2)); // (?i) para ignorar
																							// mayúsculas/minúsculas
		}

		if (ventanaMarcarAceptaciones.getRadioButtonSucursal().isSelected()
				&& ventanaMarcarAceptaciones.getComboFiltroSucursal().getSelectedItem() != null
				&& ventanaMarcarAceptaciones.getComboFiltroSucursal().getSelectedItem().toString() != null) {
			String searchText = ventanaMarcarAceptaciones.getComboFiltroSucursal().getSelectedItem().toString();
			rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 3)); // (?i) para ignorar
																							// mayúsculas/minúsculas
		}

		if (ventanaMarcarAceptaciones.getRadioButtonAviso().isSelected()
				&& ventanaMarcarAceptaciones.getComboFiltroAviso().getSelectedItem() != null
				&& ventanaMarcarAceptaciones.getComboFiltroAviso().getSelectedItem().toString() != null) {
			rfs.add(RowFilter.regexFilter(
					"^" + ventanaMarcarAceptaciones.getComboFiltroAviso().getSelectedItem().toString() + "$", 1));
		}

		if (ventanaMarcarAceptaciones.getRadioButtonELS().isSelected()
				&& ventanaMarcarAceptaciones.getComboFiltroELS().getSelectedItem() != null
				&& ventanaMarcarAceptaciones.getComboFiltroELS().getSelectedItem().toString() != null) {
			rfs.add(RowFilter.regexFilter(
					"^" + ventanaMarcarAceptaciones.getComboFiltroELS().getSelectedItem().toString() + "$", 0));
		}

		rf = RowFilter.andFilter(rfs);

		tr.setRowFilter(rf);

		if (this.ventanaMarcarAceptaciones.getTblReparaciones().getRowSorter() != null) {
			int rowCount = this.ventanaMarcarAceptaciones.getTblReparaciones().getRowSorter().getViewRowCount();

			if (rowCount != 0) {

				initCheckboxListeners();
			}
		}

	}

	else if(ventanaMarcarAceptaciones!=null&&e.getSource()==this.ventanaMarcarAceptaciones.getBtnMostrarTodo())
	{

		mostrarTodo();

	}

	else if(ventanaMarcarAceptaciones!=null&&e.getSource()==this.ventanaMarcarAceptaciones.getBtnGrardarCambios())
	{

		String estadoComercial;
		int filas = this.ventanaMarcarAceptaciones.getModelReparaciones().getRowCount();

		for (int i = 0; i < filas; i++) {

			estadoComercial = this.ventanaMarcarAceptaciones.getModelReparaciones().getValueAt(i, 7).toString();
			if (estadoComercial.compareTo("A la Espera de Aceptación") > 0) {

				ReparacionDTO reparacionAeditar = TomarDatosVentanaMarcarAceptaciones(i);
				this.agenda.editarReparacionAceptacion(reparacionAeditar);

			}
		}

		JOptionPane.showMessageDialog(null, "Se guardaron los cambios");

		cargarTablaMarcarAceptaciones();
		mostrarTodo();

	}
	}

	private void enviarMail() {
	    JDialog popup = new JDialog();
	    popup.setTitle("Procesando");
	    popup.setModal(false);
	    popup.setSize(300, 100);
	    popup.setLocationRelativeTo(ventanaEmail);
	    popup.add(new JLabel("Enviando correo, espere...", SwingConstants.CENTER));

	    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
	        @Override
	        protected Void doInBackground() {
	            try {
	                String correo        = ventanaEmail.getTextPara().getText();
	                String asunto        = ventanaEmail.getTextAsunto().getText();
	                String cuerpo        = ventanaEmail.getTextCuerpo().getText();
	                String nombreArchivo = ventanaEmail.getTextAdjunto().getText();
	                String ubicacion     = agenda.getUbicacionBase();

	                mails.EnviarMail.enviarInformeAlCliente(correo, asunto, cuerpo, nombreArchivo,
	                        archivosAdjuntosExtras, ubicacion);

	                // CORRECCIÓN: solo actualizar checkboxes si la ventana está abierta
	                if (ventanaGenerarPresupuesto != null) {
	                    if (nombreArchivo.endsWith(".pdf")) {
	                        ventanaGenerarPresupuesto.setChckPDFEnviado(true);
	                    } else if (nombreArchivo.endsWith(".docx")) {
	                        ventanaGenerarPresupuesto.setChckWORDEnviado(true);
	                    }

	                    ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();
	                    agenda.editarReparacionPresupuesto(reparacionAeditar);
	                } else {
	                    // Flujo directo desde VentanaVisualizarEquipos — actualizar DB por nombre de archivo
	                    String nombrePDF = ventanaEmail.getTextAdjunto().getText();
	                    if (nombrePDF != null && nombrePDF.startsWith("Presupuesto ELS_")) {
	                        try {
	                            // Extraer ELS del nombre: "Presupuesto ELS_1234_Cliente.pdf"
	                            String sinPrefijo = nombrePDF.replace("Presupuesto ELS_", "");
	                            int els = Integer.parseInt(sinPrefijo.split("_")[0]);
	                            ReparacionDTO rep = agenda.dameReparacionXels(els);
	                            if (rep != null) {
	                                rep.setPresupuestoEnviado(true);
	                                agenda.editarReparacionPresupuesto(rep);
	                            }
	                        } catch (NumberFormatException ex) {
	                            System.err.println("No se pudo extraer ELS del nombre del archivo: " + nombrePDF);
	                        }
	                    }
	                }

	                archivosAdjuntosExtras.clear();
	                ventanaEmail.getTextArchivos().setText("");

	            } catch (Exception ex) {
	                popup.dispose();
	                ex.printStackTrace();
	            }
	            return null;
	        }

	        @Override
	        protected void done() {
	            popup.dispose();
	        }
	    };

	    SwingUtilities.invokeLater(() -> {
	        popup.setVisible(true);
	        worker.execute();
	    });
	}

	private void agregarImagenesDiagnostico() {
		JTextField txtRutaImagen_4 = ventanaAgregarImagenes.getTxtRutaImagen_4();
		JTextField txtRutaImagen_5 = ventanaAgregarImagenes.getTxtRutaImagen_5();
		JTextField txtRutaImagen_6 = ventanaAgregarImagenes.getTxtRutaImagen_6();

		abrirSelectorImagen(txtRutaImagen_4, txtRutaImagen_5, txtRutaImagen_6);

	}

	private void agregarImagenesIngreso() {

		JTextField txtRutaImagen_1 = ventanaAgregarImagenes.getTxtRutaImagen_1();
		JTextField txtRutaImagen_2 = ventanaAgregarImagenes.getTxtRutaImagen_2();
		JTextField txtRutaImagen_3 = ventanaAgregarImagenes.getTxtRutaImagen_3();

		abrirSelectorImagen(txtRutaImagen_1, txtRutaImagen_2, txtRutaImagen_3);

	}

	private void agregarListenersVentanaImagenes() {

		ventanaAgregarImagenes.getBtngenerarInforme().addActionListener(this);
		ventanaAgregarImagenes.getBtnAgregarImagen().addActionListener(this);
		ventanaAgregarImagenes.getBtnBorrarImagen_1().addActionListener(this);
		ventanaAgregarImagenes.getBtnBorrarImagen_2().addActionListener(this);
		ventanaAgregarImagenes.getBtnBorrarImagen_3().addActionListener(this);
		ventanaAgregarImagenes.getBtnBorrarImagen_4().addActionListener(this);
		ventanaAgregarImagenes.getBtnBorrarImagen_5().addActionListener(this);
		ventanaAgregarImagenes.getBtnBorrarImagen_6().addActionListener(this);
		ventanaAgregarImagenes.getBtnCancelar().addActionListener(this);
		ventanaAgregarImagenes.getBtnAgregarImagenDiagnostico().addActionListener(this);

	}

	public void cerraVentanaMarcarAceptaciones() {

		this.ventanaMarcarAceptaciones.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(ventanaMarcarAceptaciones,
						"¿Desea salir de la ventana 'LISTADO'?", "Aviso", JOptionPane.YES_NO_OPTION);

				if (opcion == JOptionPane.YES_OPTION) {

					ventanaMarcarAceptaciones.dispose();
					ventanaMarcarAceptaciones = null;

				}

			}

		});

	}

	// Modificar el método agregarArchivosAdjuntos
	private void agregarArchivosAdjuntos() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setDialogTitle("Seleccionar archivos adjuntos adicionales");

		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = fileChooser.getSelectedFiles();

			if (selectedFiles != null && selectedFiles.length > 0) {
				// Agregar archivos al ArrayList (evitando duplicados)
				for (File file : selectedFiles) {
					if (!archivosAdjuntosExtras.contains(file)) {
						archivosAdjuntosExtras.add(file);
					}
				}

				// Actualizar el texto con solo los nombres
				actualizarTextoArchivos();
			}
		}
	}

	// Método auxiliar para actualizar el campo de texto
	private void actualizarTextoArchivos() {
		StringBuilder nombresArchivos = new StringBuilder();

		for (int i = 0; i < archivosAdjuntosExtras.size(); i++) {
			nombresArchivos.append(archivosAdjuntosExtras.get(i).getName());

			if (i < archivosAdjuntosExtras.size() - 1) {
				nombresArchivos.append(" ; ");
			}
		}

		ventanaEmail.getTextArchivos().setText(nombresArchivos.toString());
	}

	// Método para obtener los archivos cuando vayas a enviar el email
	public ArrayList<File> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	public void cerraVentanaAgregarPrecio() {

		this.ventanaIngresoDePago.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(ventanaIngresoDePago,
						"¿Desea salir de la ventana 'INGRESO DE PAGO'?", "Aviso", JOptionPane.YES_NO_OPTION);

				if (opcion == JOptionPane.YES_OPTION) {

					ventanaIngresoDePago.dispose();
					ventanaIngresoDePago = null;

				}

			}

		});

	}

	private void abrirSelectorImagen(JTextField txtRutaImagen_1, JTextField txtRutaImagen_2,
			JTextField txtRutaImagen_3) {

		// String rutadefaulImagenes = "F:\\els\\Administracion\\Sistema\\";
		String rutadefaulImagenes = "C:\\";

		JFileChooser archivosImagenes = new JFileChooser(rutadefaulImagenes);

		FileNameExtensionFilter imagelFilter = new FileNameExtensionFilter("Archivos de Imagen", "gif", "jpg", "jpeg",
				"bmp");
		archivosImagenes.setFileFilter(imagelFilter);

		int result;
		result = archivosImagenes.showOpenDialog(ventanaAgregarImagenes);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = archivosImagenes.getSelectedFile();

			if (txtRutaImagen_1.getText().isEmpty()) {
				txtRutaImagen_1.setText(selectedFile.getAbsolutePath());
			} else if (txtRutaImagen_2.getText().isEmpty()) {
				txtRutaImagen_2.setText(selectedFile.getAbsolutePath());
			} else if (txtRutaImagen_3.getText().isEmpty()) {
				txtRutaImagen_3.setText(selectedFile.getAbsolutePath());
			} else {
				JOptionPane.showMessageDialog(null, "Solo se pueden agregar 3 imágenes",
						"Cantidad de imagenes superada", JOptionPane.WARNING_MESSAGE);
			}

		}

	}

	public void buscarYReemplazar(XWPFDocument doc, String textoBusqueda, String textoReemplazo, String rutaImagen) {
		Double anchoImagen = 6.0;
		Double altoImagen = 3.5;
		int newWidth = 0;
		int newHeight = 0;
		Double aspectRatio = 0.0;

		for (XWPFParagraph paragraph : doc.getParagraphs()) {
			for (XWPFRun run : paragraph.getRuns()) {
				String text = run.getText(0);
				if (text != null && text.contains(textoBusqueda)) {
					text = text.replace(textoBusqueda, textoReemplazo);
					run.setText(text, 0);
				}
			}
		}

		for (XWPFTable table : doc.getTables()) {
			for (XWPFTableRow row : table.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						for (XWPFRun run : paragraph.getRuns()) {
							String text = run.getText(0);
							// System.out.println("text: " + text);
							if (text != null && text.contains(textoBusqueda)) {
								run.setText("", 0);

								if (rutaImagen != null && !rutaImagen.isEmpty()) {
									try {
										File image = new File(rutaImagen);
										BufferedImage bimg = ImageIO.read(image);

										// double aspectRatio = (double) bimg.getWidth() / bimg.getHeight();

										if (bimg.getWidth() > bimg.getHeight()) {
											aspectRatio = (double) bimg.getWidth() / bimg.getHeight();

											newWidth = cmToPixels(anchoImagen);
											newHeight = (int) (newWidth / aspectRatio);

										} else {
											aspectRatio = (double) bimg.getHeight() / bimg.getWidth();
											newHeight = cmToPixels(altoImagen);
											newWidth = (int) (newHeight / aspectRatio);
										}

//										System.out.println("ancho: " + bimg.getWidth() + " alto: " + bimg.getHeight());
//										System.out.println("aspect ratio: " + aspectRatio);
//										System.out.println("nuevo ancho: " + newWidth + " nuevo alto: " + newHeight);

										String imgFile = image.getName();
										int imgFormat = getImageFormat(imgFile);

										FileInputStream fis = new FileInputStream(image);
										run.addPicture(fis, imgFormat, imgFile, Units.toEMU(newWidth),
												Units.toEMU(newHeight));

									} catch (Exception e) {
										e.printStackTrace();
									}
								} else if (textoReemplazo != null && !textoReemplazo.isEmpty()) {
									String[] lines = textoReemplazo.split("\\r?\\n");
									for (int i = 0; i < lines.length; i++) {
										run.setText(lines[i], i);
										if (i < lines.length - 1) {
											run.addBreak();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static int cmToPixels(double cm) {
		// 1 centimeter is approximately 0.3937 inches
		// 1 inch is exactly 96 pixels
		return (int) (cm * 0.3937 * 96);
	}

	private static int getImageFormat(String imgFileName) {
		int format;
		if (imgFileName.endsWith(".emf") || imgFileName.endsWith(".EMF"))
			format = XWPFDocument.PICTURE_TYPE_EMF;
		else if (imgFileName.endsWith(".wmf") || imgFileName.endsWith(".WMF"))
			format = XWPFDocument.PICTURE_TYPE_WMF;
		else if (imgFileName.endsWith(".pict") || imgFileName.endsWith(".PICT"))
			format = XWPFDocument.PICTURE_TYPE_PICT;
		else if (imgFileName.endsWith(".jpeg") || imgFileName.endsWith(".jpg") || imgFileName.endsWith(".JPEG")
				|| imgFileName.endsWith(".JPG"))
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		else if (imgFileName.endsWith(".png") || imgFileName.endsWith(".PNG"))
			format = XWPFDocument.PICTURE_TYPE_PNG;
		else if (imgFileName.endsWith(".dib") || imgFileName.endsWith(".DIB"))
			format = XWPFDocument.PICTURE_TYPE_DIB;
		else if (imgFileName.endsWith(".gif") || imgFileName.endsWith(".GIF"))
			format = XWPFDocument.PICTURE_TYPE_GIF;
		else if (imgFileName.endsWith(".tiff") || imgFileName.endsWith(".TIFF"))
			format = XWPFDocument.PICTURE_TYPE_TIFF;
		else if (imgFileName.endsWith(".eps") || imgFileName.endsWith(".EPS"))
			format = XWPFDocument.PICTURE_TYPE_EPS;
		else if (imgFileName.endsWith(".bmp") || imgFileName.endsWith(".BMP"))
			format = XWPFDocument.PICTURE_TYPE_BMP;
		else if (imgFileName.endsWith(".wpg") || imgFileName.endsWith(".WPG"))
			format = XWPFDocument.PICTURE_TYPE_WPG;
		else {
			return 0;
		}
		return format;
	}

	private void agregarListenerAMarcarAceptaciones() {

		ventanaMarcarAceptaciones.getBtnFiltrar().addActionListener(this);
		ventanaMarcarAceptaciones.getBtnMax().addActionListener(this);
		ventanaMarcarAceptaciones.getBtnMostrarTodo().addActionListener(this);
		ventanaMarcarAceptaciones.getBtnGrardarCambios().addActionListener(this);

		ventanaMarcarAceptaciones.getComboFiltroAviso().addActionListener(this);
		ventanaMarcarAceptaciones.getComboFiltroELS().addActionListener(this);
		ventanaMarcarAceptaciones.getComboFiltroCliente().addActionListener(this);
		ventanaMarcarAceptaciones.getComboFiltroSucursal().addActionListener(this);

		ventanaMarcarAceptaciones.getRadioButtonAviso().addActionListener(this);
		ventanaMarcarAceptaciones.getRadioButtonCliente().addActionListener(this);
		ventanaMarcarAceptaciones.getRadioButtonELS().addActionListener(this);
		ventanaMarcarAceptaciones.getRadioButtonSucursal().addActionListener(this);

		ventanaMarcarAceptaciones.getRadioButtonAviso().addMouseListener(this);
		ventanaMarcarAceptaciones.getRadioButtonCliente().addMouseListener(this);
		ventanaMarcarAceptaciones.getRadioButtonELS().addMouseListener(this);
		ventanaMarcarAceptaciones.getRadioButtonSucursal().addMouseListener(this);

		ventanaMarcarAceptaciones.getRadioButtonAviso().addItemListener(this);
		ventanaMarcarAceptaciones.getRadioButtonCliente().addItemListener(this);
		ventanaMarcarAceptaciones.getRadioButtonELS().addItemListener(this);
		ventanaMarcarAceptaciones.getRadioButtonSucursal().addItemListener(this);

		ventanaMarcarAceptaciones.getBtnMax().addMouseListener(this);
		ventanaMarcarAceptaciones.getBtnGrardarCambios().addMouseListener(this);
		ventanaMarcarAceptaciones.getBtnFiltrar().addMouseListener(this);
		ventanaMarcarAceptaciones.getBtnMostrarTodo().addMouseListener(this);

		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroCliente());
		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroAviso());
		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroELS());
		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroSucursal());

	}

	private void marcarPorDefault() {

		int filas = this.ventanaMarcarAceptaciones.getModelReparaciones().getRowCount();

		for (int i = 0; i < filas; i++) {

			this.ventanaMarcarAceptaciones.getModelReparaciones().setValueAt(true, i, 11);
		}

	}

	private void llenarComboCliente() {

		agenda.ListarCliente(ventanaMarcarAceptaciones.getComboFiltroCliente());
		ventanaMarcarAceptaciones.getComboFiltroCliente().setSelectedIndex(-1);

	}

	private void llenarComboSucursales() {

		agenda.ListarSucursales(ventanaMarcarAceptaciones.getComboFiltroSucursal());
		ventanaMarcarAceptaciones.getComboFiltroSucursal().setSelectedIndex(-1);

	}

	private void llenarComboAviso() {

		agenda.ListarAvisos(ventanaMarcarAceptaciones.getComboFiltroAviso());

		ventanaMarcarAceptaciones.getComboFiltroAviso().setSelectedIndex(-1);

	}

	public void agregarListenersVentanaGenerarPresupuesto() {

		ventanaGenerarPresupuesto.getBtnEditarInforme().addActionListener(this);
		ventanaGenerarPresupuesto.getBtnGuardarCambios().addActionListener(this);
		ventanaGenerarPresupuesto.getBtnGramatica().addActionListener(this);
		ventanaGenerarPresupuesto.getBtnCotizacionDolar().addActionListener(this);
		ventanaGenerarPresupuesto.getBtnHistorialDePrecios().addActionListener(this);
		ventanaGenerarPresupuesto.getChckDolar().addMouseListener(this);
		ventanaGenerarPresupuesto.getChckPesos().addMouseListener(this);
		ventanaGenerarPresupuesto.getChckIVA().addMouseListener(this);

		switch (agenda.getUbicacionBase()) {
		case "Bariloche":
			ventanaGenerarPresupuesto.getChckIVA().setSelected(false);
			break;
		case "Buenos Aires":
			ventanaGenerarPresupuesto.getChckIVA().setSelected(true);
			break;

		default:
			break;
		}

		ventanaGenerarPresupuesto.getGuardarPresupuestoPDF().addActionListener(this);
		ventanaGenerarPresupuesto.getVisualizarPresupuestoPDF().addActionListener(this);

		ventanaGenerarPresupuesto.getTextPrecioPeso().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String precioPeso = ventanaGenerarPresupuesto.getTextPrecioPeso().getText();
				ventanaGenerarPresupuesto.getTextPrecioPeso().setText(monedaFormatter.formatPeso(precioPeso));

			}
		});

		ventanaGenerarPresupuesto.getTextPrecioDolar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String precioDolar = ventanaGenerarPresupuesto.getTextPrecioDolar().getText();
				ventanaGenerarPresupuesto.getTextPrecioDolar().setText(monedaFormatter.formatDolar(precioDolar));

			}
		});

		this.ventanaGenerarPresupuesto.getBtnGenerarInformeSiemens().addActionListener(this);

		performActionOnTextComponents(ventanaGenerarPresupuesto);

	}

	private void cargarTablaMarcarAceptaciones() {

		this.ventanaMarcarAceptaciones.getModelReparaciones().setRowCount(0); // Para
		// vaciar
		// tabla
		this.ventanaMarcarAceptaciones.getModelReparaciones().setColumnCount(0);
		this.ventanaMarcarAceptaciones.getModelReparaciones()
				.setColumnIdentifiers(this.ventanaMarcarAceptaciones.getNombreColumnas());

		this.Reparaciones_en_tabla = (List<ReparacionDTO>) agenda.obtenerReparacionParaListadoMarcarAceptaciones();

		for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {

			Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(), this.Reparaciones_en_tabla.get(i).getAviso(),
					this.Reparaciones_en_tabla.get(i).getCliente(), this.Reparaciones_en_tabla.get(i).getSucursal(),
					this.Reparaciones_en_tabla.get(i).getNombreEquipo(), this.Reparaciones_en_tabla.get(i).getModelo(),
					this.Reparaciones_en_tabla.get(i).getEstadoTecnico(),
					this.Reparaciones_en_tabla.get(i).getEstadoComercial(), };
			this.ventanaMarcarAceptaciones.getModelReparaciones().addRow(fila);
		}

		ventanaMarcarAceptaciones.setCellRender(this.ventanaMarcarAceptaciones.getTblReparaciones());

		this.ventanaMarcarAceptaciones.setVisible(true);
		;

		marcarPorDefault();

	}

	private void handleCheckboxSelection(int selectedRowInView, int selectedColumnInView) {
		if (selectedRowInView != -1 && selectedColumnInView != -1) {
			int selectedRowInModel = ventanaMarcarAceptaciones.getTblReparaciones()
					.convertRowIndexToModel(selectedRowInView);

			for (int i = ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount()
					- 4; i < ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount(); i++) {
				if (i != selectedColumnInView) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt(false, selectedRowInModel, i);
				}
			}
		}
	}

	// Método para inicializar los listeners de los checkboxes

	public void initCheckboxListeners() {
		ActionListener checkboxListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = ventanaMarcarAceptaciones.getTblReparaciones().getSelectedRow();
				int selectedColumn = ventanaMarcarAceptaciones.getTblReparaciones().getSelectedColumn();
				handleCheckboxSelection(selectedRow, selectedColumn);
				int selectedRowInModel = ventanaMarcarAceptaciones.getTblReparaciones()
						.convertRowIndexToModel(selectedRow);

				// Lógica para manejar la selección automática del último checkbox si todos los
				// demás están deseleccionados
				boolean anyChecked = true;
				for (int i = ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount()
						- 4; i < ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount(); i++) {
					Object value = ventanaMarcarAceptaciones.getModelReparaciones().getValueAt(selectedRowInModel, i);
					boolean isChecked = value != null && (boolean) value;
					if (isChecked) {
						anyChecked = false;
						break;
					}
				}

				// Resto de la lógica para manejar la selección de checkboxes y otras acciones
				if (selectedColumn == 8) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt("Aceptado", selectedRowInModel, 7);
				}
				if (selectedColumn == 9) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt(" NO Aceptado", selectedRowInModel, 7);
				}
				if (selectedColumn == 10) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt("Garantía", selectedRowInModel, 7);
				}
				if (selectedColumn == 11) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt("A la Espera de Aceptación",
							selectedRowInModel, 7);
				}

				if (!anyChecked) {
					int lastColumn = ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount() - 1;
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt(true, selectedRowInModel, lastColumn);
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt("A la Espera de Aceptación",
							selectedRowInModel, 7);

				}
			}
		};

		// Agregar listener a las celdas de checkbox
		for (int i = ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount()
				- 4; i < ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount(); i++) {
			TableColumn column = ventanaMarcarAceptaciones.getTblReparaciones().getColumnModel().getColumn(i);
			column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
			JCheckBox checkBox = (JCheckBox) column.getCellEditor()
					.getTableCellEditorComponent(ventanaMarcarAceptaciones.getTblReparaciones(), null, false, 0, i);
			checkBox.addActionListener(checkboxListener);
		}
	}

	private void mostrarTodo() {
		this.ventanaMarcarAceptaciones.getTblReparaciones().setRowSorter(null);

		this.ventanaMarcarAceptaciones.getComboFiltroELS().setSelectedItem(null);
		this.ventanaMarcarAceptaciones.getComboFiltroELS().setEnabled(false);

		this.ventanaMarcarAceptaciones.getComboFiltroAviso().setSelectedItem(null);
		this.ventanaMarcarAceptaciones.getComboFiltroAviso().setEnabled(false);

		this.ventanaMarcarAceptaciones.getComboFiltroCliente().setSelectedItem(null);
		this.ventanaMarcarAceptaciones.getComboFiltroCliente().setEnabled(false);

		this.ventanaMarcarAceptaciones.getComboFiltroSucursal().setSelectedItem(null);
		this.ventanaMarcarAceptaciones.getComboFiltroSucursal().setEnabled(false);

		this.ventanaMarcarAceptaciones.getRadioButtonAviso().setSelected(false);
		this.ventanaMarcarAceptaciones.getRadioButtonELS().setSelected(false);
		this.ventanaMarcarAceptaciones.getRadioButtonCliente().setSelected(false);
		this.ventanaMarcarAceptaciones.getRadioButtonSucursal().setSelected(false);

	}

	private void TomarDatosDeTablas() {

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
		Double pago = reparacion.getPago();
		String EstadoTecnico = reparacion.getEstadoTecnico();
		String EstadoComercial = reparacion.getEstadoComercial();
		String EstadoFisico = reparacion.getEstadoFisico();

		if (ventanaGenerarPresupuesto != null && ventanaIngresoDePago == null) {
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

			ventanaGenerarPresupuesto.getTextPrecioPeso().setText(monedaFormatter.formatPeso(PrecioPeso.toString()));
			ventanaGenerarPresupuesto.getTextPrecioDolar().setText(monedaFormatter.formatDolar(PrecioDolar.toString()));

			ventanaGenerarPresupuesto.getTextcondicionesPago().setText("Contado.");
			ventanaGenerarPresupuesto.getTextPlazoEntrega().setText("7 días.");

			ventanaGenerarPresupuesto.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
			ventanaGenerarPresupuesto.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
			ventanaGenerarPresupuesto.setChckWORDGenerado(reparacion.getWORDgenerado());
			ventanaGenerarPresupuesto.setChckWORDEnviado(reparacion.getWORDenviado());

		} else if (ventanaIngresoDePago != null) {

			ventanaIngresoDePago.getTextELS().setText(Integer.toString(ELS));
			ventanaIngresoDePago.getTextAviso().setText(Aviso);
			ventanaIngresoDePago.getTextCliente().setText(Cliente);
			ventanaIngresoDePago.getTextSucursal().setText(Sucursal);
			ventanaIngresoDePago.getTextEquipo().setText(Equipo);
			ventanaIngresoDePago.getTextModelo().setText(Modelo);
			ventanaIngresoDePago.getTextMarca().setText(Marca);
			ventanaIngresoDePago.getTextSerie().setText(Serie);
			ventanaIngresoDePago.getTextEstadoComercial().setText(EstadoComercial);
			ventanaIngresoDePago.getTextEstadoTecnico().setText(EstadoTecnico);
			ventanaIngresoDePago.getTextEstadoFisico().setText(EstadoFisico);

			ventanaIngresoDePago.getTextPrecioPeso().setText(monedaFormatter.formatPeso(PrecioPeso.toString()));
			ventanaIngresoDePago.getTextPrecioDolar().setText(monedaFormatter.formatDolar(PrecioDolar.toString()));
			ventanaIngresoDePago.gettextIngresoPago().setText(monedaFormatter.formatPeso(pago.toString()));

		}

		ventanaPresupuestos.dispose();
		ventanaPresupuestos = null;

		ventanaSeleccionarELS.dispose();
		ventanaSeleccionarELS = null;

	}

	public VentanaGenerarPresupuesto TomarDatosDeTablasParaVisualizacion(int numeroELS) {

		monedaFormatter = new MonedaFormatter();

		ventanaGenerarPresupuesto = new VentanaGenerarPresupuesto(this);

		SpellChecker.register(ventanaGenerarPresupuesto.getTextInforme());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		reparacion = agenda.dameReparacionXels(numeroELS);

		String ELS = String.valueOf(numeroELS);
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
		ventanaGenerarPresupuesto.getTextELS().setText(ELS);
		ventanaGenerarPresupuesto.getTextEquipo().setText(Equipo);
		ventanaGenerarPresupuesto.getTextMarca().setText(Marca);
		ventanaGenerarPresupuesto.getTextModelo().setText(Modelo);
		ventanaGenerarPresupuesto.getTextSerie().setText(Serie);
		ventanaGenerarPresupuesto.getTextAviso().setText(Aviso);
		ventanaGenerarPresupuesto.getTextClienteCliente().setText(ClienteCliente);
		ventanaGenerarPresupuesto.getTextRemCliente().setText(RemitoCliente);

		ventanaGenerarPresupuesto.getTextInforme().setText(Informe);
		ventanaGenerarPresupuesto.getTextInforme().setCaretPosition(0);
		ventanaGenerarPresupuesto.getScrollPane().getVerticalScrollBar().setValue(0);
		ventanaGenerarPresupuesto.getTextPrecioPeso().setText(monedaFormatter.formatPeso(PrecioPeso.toString()));
		ventanaGenerarPresupuesto.getTextPrecioDolar().setText(monedaFormatter.formatDolar(PrecioDolar.toString()));

		ventanaGenerarPresupuesto.getTextcondicionesPago().setText("Contado.");
		ventanaGenerarPresupuesto.getTextPlazoEntrega().setText("7 días.");

		ventanaGenerarPresupuesto.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
		ventanaGenerarPresupuesto.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
		ventanaGenerarPresupuesto.setChckWORDGenerado(reparacion.getWORDgenerado());
		ventanaGenerarPresupuesto.setChckWORDEnviado(reparacion.getWORDenviado());
		
		
		return ventanaGenerarPresupuesto;

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (this.ventanaGenerarPresupuesto != null) {

			switch (agenda.getUbicacionBase()) {

			case "Bariloche":
				if (this.ventanaGenerarPresupuesto.getGrupoMoneda()
						.isSelected(this.ventanaGenerarPresupuesto.getChckPesos().getModel())) {

					ventanaGenerarPresupuesto.getTextcondicionesMoneda()
							.setText("Los precios están expresados en Pesos. Facturación C (Monotributista)\r\n"
									+ "La garantía es de 90 días sobre la reparación realizada.\r\n"
									+ "La validez del presupuesto es de 7 días.");

				}

				else {
					ventanaGenerarPresupuesto.getTextcondicionesMoneda().setText(
							"Los precios están expresados en Dólares estadounidenses. Los USD se convertirán a pesos al tipo de cambio\r\n"
									+ "vendedor del Banco Nación vigente al día anterior al que se efectúe el pago. Facturación C (Monotributista)\r\n"
									+ "La garantía es de 90 días sobre la reparación realizada.\r\n"
									+ "La validez del presupuesto es de 15 días.");

				}
				break;

			case "Buenos Aires":
				if (this.ventanaGenerarPresupuesto.getGrupoMoneda()
						.isSelected(this.ventanaGenerarPresupuesto.getChckPesos().getModel())) {

					ventanaGenerarPresupuesto.getTextcondicionesMoneda()
							.setText("Los precios están expresados en Pesos, son Netos y no incluyen el IVA (21%).\r\n"
									+ "La garantía es de 90 días sobre la reparación realizada.\r\n"
									+ "La validez del presupuesto es de 15 días.");

				}

				else {
					ventanaGenerarPresupuesto.getTextcondicionesMoneda().setText(
							"Los precios están expresados en Dólares estadounidenses. Los USD se convertirán a pesos al tipo de cambio\r\n"
									+ "vendedor del Banco Nación vigente al día anterior al que se efectúe el pago. Son Netos y no incluyen el IVA (21%).\r\n"
									+ "La garantía es de 90 días sobre la reparación realizada.\r\n"
									+ "La validez del presupuesto es de 15 días.");

				}
				break;

			default:
				break;
			}

		}

		if (this.ventanaMarcarAceptaciones != null) {

			if (arg0.getSource() == this.ventanaMarcarAceptaciones.getBtnMax()) {

				if (clickMax % 2 != 0) {

					ventanaMarcarAceptaciones.setExtendedState(max);
					this.ventanaMarcarAceptaciones.getBtnMax()
							.setIcon(new ImageIcon(this.getClass().getResource("/minimizar.png")));
					ventanaMarcarAceptaciones.setVisible(true);

				} else {

					ventanaMarcarAceptaciones.setExtendedState(min);
					this.ventanaMarcarAceptaciones.getBtnMax()
							.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
					ventanaMarcarAceptaciones.setVisible(true);

				}
				clickMax++;
			}
		}

	}

	
	/**
	 * Función para revisar gramática - AHORA RECIBE EL COMPONENTE DE TEXTO
	 * @param textoComponente El componente JTextComponent que contiene el texto a revisar
	 * @param parent El componente padre para los diálogos
	 */
	private void revisarGramaticaActionPerformed(JTextComponent textoComponente, Frame parent) {
	    String textoARevisar = textoComponente.getText();
	    
	    // Validar texto vacío
	    if (textoARevisar == null || textoARevisar.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(parent,
	            "No hay texto para revisar.",
	            "Aviso",
	            JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    // Crear diálogo de carga no modal
	    JDialog dialogoCarga = new JDialog(parent, "Revisando...", false);
	    configurarDialogoCarga(dialogoCarga);
	    final JDialog dialogoCargaRef = dialogoCarga;
	    
	    // SwingWorker para la revisión
	    SwingWorker<CorrectorGramaticalAPI.ResultadoRevision, Void> worker = 
	        new SwingWorker<CorrectorGramaticalAPI.ResultadoRevision, Void>() {
	        
	        @Override
	        protected CorrectorGramaticalAPI.ResultadoRevision doInBackground() throws Exception {
	            return CorrectorGramaticalAPI.revisarTexto(textoARevisar);
	        }
	        
	        @Override
	        protected void done() {
	            // Cerrar diálogo de carga
	            if (dialogoCargaRef != null && dialogoCargaRef.isVisible()) {
	                dialogoCargaRef.dispose();
	            }
	            
	            try {
	                CorrectorGramaticalAPI.ResultadoRevision resultado = get();
	                
	                SwingUtilities.invokeLater(() -> {
	                    // Pasar el COMPONENTE DE TEXTO, no solo el string
	                    DialogoRevisionGramatical.mostrarRevisor(parent, textoComponente, resultado);
	                });
	                
	            } catch (Exception e) {
	                SwingUtilities.invokeLater(() -> {
	                    JOptionPane.showMessageDialog(parent,
	                        "Error al revisar el texto: " + e.getMessage(),
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
	                });
	                e.printStackTrace();
	            }
	        }
	    };
	    
	    // Respaldo para cerrar diálogo de carga
	    worker.addPropertyChangeListener(evt -> {
	        if (evt.getPropertyName().equals("state") && 
	            evt.getNewValue() == SwingWorker.StateValue.DONE) {
	            if (dialogoCargaRef.isVisible()) {
	                dialogoCargaRef.dispose();
	            }
	        }
	    });
	    
	    worker.execute();
	    dialogoCarga.setVisible(true);
	}

	/**
	 * Versión simplificada si no tienes referencia al Frame padre
	 */
	private void revisarGramaticaActionPerformed(JTextComponent textoComponente) {
	    revisarGramaticaActionPerformed(textoComponente, null);
	}

	/**
	 * Configura el diálogo de carga
	 */
	private void configurarDialogoCarga(JDialog dialogo) {
	    JPanel panelCarga = new JPanel(new BorderLayout(5, 5));
	    panelCarga.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
	    JLabel lblMensaje = new JLabel("Revisando gramática y ortografía... espere");
	    lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    JProgressBar progressBar = new JProgressBar();
	    progressBar.setIndeterminate(true);
	    
	    panelCarga.add(lblMensaje, BorderLayout.NORTH);
	    panelCarga.add(progressBar, BorderLayout.CENTER);
	    
	    dialogo.add(panelCarga);
	    dialogo.setSize(300, 100);
	    dialogo.setLocationRelativeTo(dialogo.getParent());
	    dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dialogo.setModalityType(Dialog.ModalityType.MODELESS);
	}


	/**
	 * Método auxiliar para mostrar errores de forma consistente
	 */
	private void mostrarError(Component parent, String mensaje, Exception e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(parent,
	        mensaje + ": " + e.getMessage(),
	        "Error",
	        JOptionPane.ERROR_MESSAGE);
	}
	
	
	private void llenarComboELS() {

		if (ventanaSeleccionarELS != null) {
			agenda.ListarELS(ventanaSeleccionarELS.getComboELS());
			ventanaSeleccionarELS.getComboELS().setSelectedIndex(-1);

		} else if (ventanaMarcarAceptaciones != null) {

			agenda.ListarELS(ventanaMarcarAceptaciones.getComboFiltroELS());
			ventanaMarcarAceptaciones.getComboFiltroELS().setSelectedIndex(-1);

		}

	}

	private ReparacionDTO TomarDatosPresupuesto() {

		int ELS = Integer.parseInt(this.ventanaGenerarPresupuesto.getTextELS().getText());
		String informeCliente = this.ventanaGenerarPresupuesto.getTextInforme().getText();

		double PrecioPeso;
		double PrecioDolar;

		if (monedaFormatter.tieneFormato(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText())) {

			PrecioPeso = monedaFormatter
					.parseAmountGuardar(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText());

		} else {

			PrecioPeso = monedaFormatter.parseAmount(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText());

		}

		if (monedaFormatter.tieneFormato(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText())) {

			PrecioDolar = monedaFormatter
					.parseAmountGuardar(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText());

		} else {

			PrecioDolar = monedaFormatter.parseAmount(this.ventanaIngresoDePago.getTextPrecioDolar().getText());

		}

		presupuestoEnviado = ventanaGenerarPresupuesto.getChckPDFEnviado();
		presupuestoGenerado = ventanaGenerarPresupuesto.getChckPDFGenerado();
		informeWordGenerado = ventanaGenerarPresupuesto.getChckWORDGenerado();
		informeWordEnviado = ventanaGenerarPresupuesto.getChckWORDEnviado();

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, informeCliente, PrecioPeso, PrecioDolar,
				presupuestoGenerado, presupuestoEnviado, informeWordGenerado, informeWordEnviado);

		return reparacionAeditar;

	}

	private ReparacionDTO TomarDatosPago() {

		int ELS = Integer.parseInt(this.ventanaIngresoDePago.getTextELS().getText());

		double PrecioPeso;
		double PrecioDolar;
		double pago;

		String EstadoComercial;

		if (monedaFormatter.tieneFormato(this.ventanaIngresoDePago.getTextPrecioPeso().getText())) {

			PrecioPeso = monedaFormatter.parseAmountGuardar(this.ventanaIngresoDePago.getTextPrecioPeso().getText());

		} else {

			PrecioPeso = monedaFormatter.parseAmount(this.ventanaIngresoDePago.getTextPrecioPeso().getText());

		}

		if (monedaFormatter.tieneFormato(this.ventanaIngresoDePago.getTextPrecioDolar().getText())) {

			PrecioDolar = monedaFormatter.parseAmountGuardar(this.ventanaIngresoDePago.getTextPrecioDolar().getText());

		} else {

			PrecioDolar = monedaFormatter.parseAmount(this.ventanaIngresoDePago.getTextPrecioDolar().getText());

		}

		if (monedaFormatter.tieneFormato(this.ventanaIngresoDePago.gettextIngresoPago().getText())) {

			pago = monedaFormatter.parseAmountGuardar(this.ventanaIngresoDePago.gettextIngresoPago().getText());
		}

		else {

			pago = monedaFormatter.parseAmount(this.ventanaIngresoDePago.gettextIngresoPago().getText());

		}

		EstadoComercial = ventanaIngresoDePago.getTextEstadoComercial().getText();

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, PrecioPeso, PrecioDolar, pago, EstadoComercial);

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
		boolean chckIVA = this.ventanaGenerarPresupuesto.getChckIVA().isSelected();

		String CondicionesMoneda = this.ventanaGenerarPresupuesto.getTextcondicionesMoneda().getText();
		String CondicionesPago = this.ventanaGenerarPresupuesto.getTextcondicionesPago().getText();
		String plazoEntrega = this.ventanaGenerarPresupuesto.getTextPlazoEntrega().getText();

		double PrecioPeso;
		double PrecioDolar;

		if (monedaFormatter.tieneFormato(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText())) {

			PrecioPeso = monedaFormatter
					.parseAmountGuardar(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText());

		} else {

			PrecioPeso = monedaFormatter.parseAmount(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText());

		}

		if (monedaFormatter.tieneFormato(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText())) {

			PrecioDolar = monedaFormatter
					.parseAmountGuardar(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText());

		} else {

			PrecioDolar = monedaFormatter.parseAmount(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText());

		}

		RegistroPresupuestoDTO nuevoPresupuesto = new RegistroPresupuestoDTO(ELS, InformeCliente, RemitoCLiente,
				PrecioPeso, PrecioDolar, NombreEquipo, Modelo, Marca, Serie, ClienteCliente, aviso, Sucursal, Cliente,
				chckpesos, chckdolar, chckIVA, CondicionesMoneda, CondicionesPago, plazoEntrega, rutaImagen_1,
				rutaImagen_2, rutaImagen_3, rutaImagen_4, rutaImagen_5, rutaImagen_6);

		return nuevoPresupuesto;

	}

	private ReparacionDTO TomarDatosVentanaMarcarAceptaciones(int i) {

		int ELS = Integer.parseInt(this.ventanaMarcarAceptaciones.getModelReparaciones().getValueAt(i, 0).toString());
		String estadoComercial;
		String fechaAceptacion;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

		fechaAceptacion = dtf.format(LocalDateTime.now());

		estadoComercial = this.ventanaMarcarAceptaciones.getModelReparaciones().getValueAt(i, 7).toString();

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, fechaAceptacion, estadoComercial);

		return reparacionAeditar;

	}

	private void agregarListenerAventanaEmail() {

		ventanaEmail.getBtnAdjuntarArchivo().addActionListener(this);
		ventanaEmail.getBtnAdjunto().addActionListener(this);
		ventanaEmail.getBtnAgregarContacto().addActionListener(this);
		ventanaEmail.getBtnEditar().addActionListener(this);
		ventanaEmail.getBtnEnviar().addActionListener(this);

	}

	/**
	 * Valida una o más direcciones de correo electrónico separadas por punto y coma.
	 * 
	 * @param email Cadena con una o más direcciones de correo separadas por ;
	 * @return true si TODAS las direcciones son válidas, false en caso contrario
	 */
	boolean validacionMail(String email) {
	    // Si la cadena está vacía o es nula, retornar false
	    if (email == null || email.trim().isEmpty()) {
	        System.out.println("Error: La cadena de correos está vacía");
	        return false;
	    }
	    
	    // Separar por punto y coma, ignorando espacios alrededor
	    String[] emails = email.split(";");
	    
	    Pattern pattern = Pattern.compile(PATTERN_EMAIL);
	    boolean todosValidos = true;
	    StringBuilder emailsInvalidos = new StringBuilder();
	    
	    // Validar cada dirección de correo individualmente
	    for (String emailIndividual : emails) {
	        // Eliminar espacios al inicio y al final
	        emailIndividual = emailIndividual.trim();
	        
	        // Si después de trim está vacío, saltar (por si hay dobles ;;)
	        if (emailIndividual.isEmpty()) {
	            continue;
	        }
	        
	        // Validar el email individual
	        Matcher matcher = pattern.matcher(emailIndividual);
	        if (!matcher.matches()) {
	            todosValidos = false;
	            emailsInvalidos.append("'").append(emailIndividual).append("' ");
	        }
	    }
	    
	    if (!todosValidos) {
	        System.out.println("Correos inválidos encontrados: " + emailsInvalidos.toString().trim());
	    }
	    
	    return todosValidos;
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
	public void itemStateChanged(ItemEvent e) {

		if (this.ventanaMarcarAceptaciones != null) {

			if (e.getSource() == this.ventanaMarcarAceptaciones.getRadioButtonCliente()) {

				if (ventanaMarcarAceptaciones.getRadioButtonCliente().isSelected())
					this.ventanaMarcarAceptaciones.getComboFiltroCliente().setEnabled(true);
				else {
					this.ventanaMarcarAceptaciones.getComboFiltroCliente().setEnabled(false);
					ventanaMarcarAceptaciones.getComboFiltroCliente().setSelectedIndex(-1);

				}
			}

			if (e.getSource() == this.ventanaMarcarAceptaciones.getRadioButtonAviso()) {

				if (ventanaMarcarAceptaciones.getRadioButtonAviso().isSelected())
					this.ventanaMarcarAceptaciones.getComboFiltroAviso().setEnabled(true);
				else {
					this.ventanaMarcarAceptaciones.getComboFiltroAviso().setEnabled(false);
					ventanaMarcarAceptaciones.getComboFiltroAviso().setSelectedIndex(-1);

				}
			}

			if (e.getSource() == this.ventanaMarcarAceptaciones.getRadioButtonELS()) {

				if (ventanaMarcarAceptaciones.getRadioButtonELS().isSelected())
					this.ventanaMarcarAceptaciones.getComboFiltroELS().setEnabled(true);
				else {
					this.ventanaMarcarAceptaciones.getComboFiltroELS().setEnabled(false);
					ventanaMarcarAceptaciones.getComboFiltroELS().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaMarcarAceptaciones.getRadioButtonSucursal()) {

				if (ventanaMarcarAceptaciones.getRadioButtonSucursal().isSelected())
					this.ventanaMarcarAceptaciones.getComboFiltroSucursal().setEnabled(true);
				else {
					this.ventanaMarcarAceptaciones.getComboFiltroSucursal().setEnabled(false);
					ventanaMarcarAceptaciones.getComboFiltroSucursal().setSelectedIndex(-1);

				}
			}

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// char c = e.getKeyChar();

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

	// Método para realizar una acción sobre todos los JTextField y JTextArea en un
	// JFrame
	private void performActionOnTextComponents(JFrame frame) {
		List<JTextComponent> textComponents = getAllTextComponents(frame);
		// Realiza la acción deseada sobre cada JTextComponent
		for (JTextComponent textComponent : textComponents) {
			configureUndoManager(textComponent);
		}
	}

	// Método para obtener todos los JTextField y JTextArea en un JFrame
	private List<JTextComponent> getAllTextComponents(Container container) {
		List<JTextComponent> textComponents = new ArrayList<>();
		Component[] components = container.getComponents();
		// Itera sobre los componentes y filtra los JTextField y JTextArea
		for (Component component : components) {
			if (component instanceof JTextComponent) {
				textComponents.add((JTextComponent) component);
			} else if (component instanceof Container) {
				textComponents.addAll(getAllTextComponents((Container) component));
			}
		}
		return textComponents;
	}
	
	
	/**
	 * Abre VentanaEmail con los datos del presupuesto PDF ya generado.
	 * Llamado desde GestorVisualizacionEquipos cuando el usuario presiona "ENVIAR CORREO".
	 *
	 * @param numeroELS ELS del equipo a enviar
	 */
	public void abrirEnvioCorreoPresupuestoExistente(int numeroELS) {

	    // Cargar la reparación desde la agenda
	    ReparacionDTO rep = agenda.dameReparacionXels(numeroELS);

	    if (rep == null) {
	        JOptionPane.showMessageDialog(null,
	            "No se encontró la reparación con ELS: " + numeroELS,
	            "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Verificar que el PDF ya fue generado
	    if (!rep.getPresupuestoGenerado()) {
	        JOptionPane.showMessageDialog(null,
	            "Aún no se ha generado el Informe.",
	            "Aviso", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }

	    monedaFormatter = new MonedaFormatter();

	    String NombreCliente   = rep.getCliente();
	    String Sucursal        = rep.getSucursal();
	    String ELS             = String.valueOf(numeroELS);
	    String NombreContacto  = agenda.ContactoPorCliente(NombreCliente);
	    String emailContacto   = agenda.EmailPorCliente(NombreCliente);
	    String NombrePDF       = "Presupuesto ELS_" + ELS + "_" + NombreCliente + ".pdf";

	    ventanaEmail = new VentanaEmail();
	    agregarListenerAventanaEmail();

	    ventanaEmail.getTextCliente().setText(NombreCliente + " ( " + Sucursal + " ) ");
	    ventanaEmail.getTextNombreContacto().setText(NombreContacto);
	    ventanaEmail.getTextEmailContacto().setText(emailContacto);
	    ventanaEmail.getTextAdjunto().setText(NombrePDF);

	    String empresa  = "ELS - Electronic Laboratory & Services.";
	    String mdp      = "Mar del Plata: Avellaneda 2766 1 piso MDP -(7600) - Te: +54 9 223 5969934. NUEVA DIRECCION.";
	    String caba     = "Bs As: Arcos 4002 4 A - Buenos Aires(1429) - Te: +54 9 11 4703-2205.";
	    String brc      = "Bariloche: 9 de julio 710 - Bariloche (8400) - Te: +54 9 11 3768-8372..";
	    String web      = "www.elsweb.com.ar";
	    String email    = "E-mail: els@elsweb.com.ar";
	    String Asunto   = "Presupuesto ELS: " + ELS;
	    String cuerpo   = "Buenos días!\n\nAdjunto presupuesto.\n"
	                    + "En caso de aceptar el mismo, favor de responder este correo "
	                    + "para poder proceder con la reparación.\nAtte.";

	    ventanaEmail.getTextCuerpo().setText(
	        cuerpo + "\n\n" + empresa + "\n" + mdp + "\n" + caba + "\n" + brc + "\n" + web + "\n" + email);
	    ventanaEmail.getTextAsunto().setText(Asunto);
	    ventanaEmail.getTextCuerpo().moveCaretPosition(0);

	    // Necesario para que enviarMail() pueda actualizar los checkboxes
	    // Apuntamos a una ventanaGenerarPresupuesto nula — enviarMail() ya lo maneja con null-check implícito.
	    // Si querés actualizar los checkboxes también desde este flujo, podés llamar:
	    //   TomarDatosDeTablasParaVisualizacion(numeroELS);
	    // antes de esta línea, para que ventanaGenerarPresupuesto esté inicializada.
	    // Por ahora el envío funciona correctamente sin eso.
	}
	
	

}