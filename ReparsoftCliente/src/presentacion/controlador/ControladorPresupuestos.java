package presentacion.controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.InputStream;
//import java.util.Date;
import java.sql.Date;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import java.text.SimpleDateFormat;
import java.io.ByteArrayOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.util.Units;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlip;

import com.inet.jortho.SpellChecker;

import modelo.Agenda;
import presentacion.reportes.ReportePresupuesto;
import presentacion.vista.VentanaAgregarEquipo;
import presentacion.vista.VentanaAgregarImagenes;
import presentacion.vista.VentanaEmail;
import presentacion.vista.VentanaGenerarPresupuesto;
import presentacion.vista.VentanaMarcarAceptaciones;
import presentacion.vista.VentanaPresupuestos;
import presentacion.vista.VentanaSeleccionarELS;
import presentacion.vista.VentanaVisualizarEquipos;
import dto.RegistroPresupuestoDTO;
import dto.ReparacionDTO;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ControladorPresupuestos implements ActionListener, MouseListener, ItemListener, KeyListener {

	private VentanaPresupuestos ventanaPresupuestos;
	private VentanaSeleccionarELS ventanaSeleccionarELS;
	private VentanaGenerarPresupuesto ventanaGenerarPresupuesto;
	private VentanaEmail ventanaEmail;
	private VentanaAgregarImagenes ventanaAgregarImagenes;
	private VentanaMarcarAceptaciones ventanaMarcarAceptaciones;

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
	private boolean presupuestoEnviado = false;
	private boolean informeWordGenerado = false;
	private boolean informeWordEnviado = false;

	private int max = Frame.MAXIMIZED_BOTH;
	private int min = Frame.NORMAL;

	private int clickMax = 1;
	private int clickMin = 1;

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

		else if (ventanaPresupuestos != null && e.getSource() == this.ventanaPresupuestos.getBtnmarcarAceptaciones()) {

			ventanaMarcarAceptaciones = new VentanaMarcarAceptaciones(this);
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

					SpellChecker.register(ventanaGenerarPresupuesto.getTextInforme());

					TomarDatosDeTablas();

					agregarListenersVentanaGenerarPresupuesto();

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
				ventanaGenerarPresupuesto.getBtnGenerarInformeSiemens().setEnabled(true);

			}
		}

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnCotizacionDolar()) {

			double[] cotizaciones = consumoAPI.ConsumoAPI.consultaCotizacionDolar();

			String cotizacionDolarOf = Double.toString(cotizaciones[0]);

			String cotizacionDolarBl = Double.toString(cotizaciones[1]);

			ventanaGenerarPresupuesto.getTextCotizacionDolarOf().setText(cotizacionDolarOf);
			ventanaGenerarPresupuesto.getTextCotizacionDolarBl().setText(cotizacionDolarBl);

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

					ventanaGenerarPresupuesto.setChckPDFGenerado(true);

					ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();
					this.agenda.editarReparacionR(reparacionAeditar);

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
						ventanaEmail.getTextAdjunto().setText(NombrePDF);

						String empresa = "ELS - Electronic Laboratory & Services.";
						String mdp = "Suc. Mar del Plata: Independencia 2609 1er piso- Te: +54 9 223 5969934.";
						String caba = "Suc. Bs As: Arcos 4002 4 A - Buenos Aires(1429) - Te: +54 9 11 4703-2205.";
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

		else if (this.ventanaGenerarPresupuesto != null
				&& e.getSource() == this.ventanaGenerarPresupuesto.getBtnGenerarInformeSiemens()) {

			if (ventanaGenerarPresupuesto.getGrupoMoneda().getSelection() == null) {

				Object mje = "Debe seleccionar un moneda para agregar al informe.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else {

				int seleccion2 = JOptionPane.showConfirmDialog(ventanaGenerarPresupuesto,
						"Desea generar el archivo WORD?", "Confirmación", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (seleccion2 == JOptionPane.YES_OPTION) {

					ventanaAgregarImagenes = new VentanaAgregarImagenes(this);
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
			}
		} else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnAgregarImagen()) {

			JTextField txtRutaImagen_1 = ventanaAgregarImagenes.getTxtRutaImagen_1();
			JTextField txtRutaImagen_2 = ventanaAgregarImagenes.getTxtRutaImagen_2();
			JTextField txtRutaImagen_3 = ventanaAgregarImagenes.getTxtRutaImagen_3();

			abrirSelectorImagen(txtRutaImagen_1, txtRutaImagen_2, txtRutaImagen_3);

		}

		else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnAgregarImagenDiagnostico()) {

			JTextField txtRutaImagen_4 = ventanaAgregarImagenes.getTxtRutaImagen_4();
			JTextField txtRutaImagen_5 = ventanaAgregarImagenes.getTxtRutaImagen_5();
			JTextField txtRutaImagen_6 = ventanaAgregarImagenes.getTxtRutaImagen_6();

			abrirSelectorImagen(txtRutaImagen_4, txtRutaImagen_5, txtRutaImagen_6);

		}

		else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnBorrarImagen_1()) {

			ventanaAgregarImagenes.getTxtRutaImagen_1().setText("");

		} else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnBorrarImagen_2()) {

			ventanaAgregarImagenes.getTxtRutaImagen_2().setText("");

		} else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnBorrarImagen_3()) {

			ventanaAgregarImagenes.getTxtRutaImagen_3().setText("");

		} else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnBorrarImagen_4()) {

			ventanaAgregarImagenes.getTxtRutaImagen_4().setText("");

		} else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnBorrarImagen_5()) {

			ventanaAgregarImagenes.getTxtRutaImagen_5().setText("");

		} else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnBorrarImagen_6()) {

			ventanaAgregarImagenes.getTxtRutaImagen_6().setText("");

		}

		else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtngenerarInforme()) {

			String nombreWordBase = "Modelo Generico de informe 2023.docx";

			String documentoBase = "F:/els/Administracion/Sistema/Informes Siemens/" + nombreWordBase;

			LocalDate fechaHoy = LocalDate.now();
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");
			String fechaHoyString = fechaHoy.format(formato);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

			String els = ventanaGenerarPresupuesto.getTextELS().getText();
			String aviso = ventanaGenerarPresupuesto.getTextAviso().getText();
			String cliente = ventanaGenerarPresupuesto.getTextCliente().getText();
			String sucursal = ventanaGenerarPresupuesto.getTextSucursal().getText();
			String equipo = ventanaGenerarPresupuesto.getTextEquipo().getText();
			String modelo = ventanaGenerarPresupuesto.getTextModelo().getText();
			String serie = ventanaGenerarPresupuesto.getTextSerie().getText();
			String rutaImagen_1 = ventanaAgregarImagenes.getTxtRutaImagen_1().getText();
			String rutaImagen_2 = ventanaAgregarImagenes.getTxtRutaImagen_2().getText();
			String rutaImagen_3 = ventanaAgregarImagenes.getTxtRutaImagen_3().getText();
			String rutaImagen_4 = ventanaAgregarImagenes.getTxtRutaImagen_4().getText();
			String rutaImagen_5 = ventanaAgregarImagenes.getTxtRutaImagen_5().getText();
			String rutaImagen_6 = ventanaAgregarImagenes.getTxtRutaImagen_6().getText();

			String fechaFabricacion = ventanaGenerarPresupuesto.getTextFabrString();

			String diagnostico = ventanaGenerarPresupuesto.getTextInforme().getText();
			String precioDolar = ventanaGenerarPresupuesto.getTextPrecioDolar().getText();
			String plazoEntrega = ventanaGenerarPresupuesto.getTextPlazoEntrega().getText();

			String nombreWordNuevo = "AV " + aviso + "-" + "ELS " + els + "_" + cliente + ".docx";
			String nuevoDocumento = "F:/els/Administracion/Sistema/Informes Siemens/" + nombreWordNuevo;

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
				this.agenda.editarReparacionR(reparacionAeditar);

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
					ventanaEmail.getTextEmailContacto().setText(emailContacto);
					ventanaEmail.getTextAdjunto().setText(nombreWordNuevo);

					String empresa = "ELS - Electronic Laboratory & Services.";
					String mdp = "Suc. Mar del Plata: Independencia 2609 1er piso- Te: +54 9 223 5969934.";
					String caba = "Suc. Bs As: Arcos 4002 4 A - Buenos Aires(1429) - Te: +54 9 11 4703-2205.";
					String brc = "Suc. Bariloche: 9 de Julio 710 - Te: +54 9 11 3768-8372.";
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

		} else if (this.ventanaAgregarImagenes != null
				&& e.getSource() == this.ventanaAgregarImagenes.getBtnCancelar()) {

			this.ventanaAgregarImagenes.dispose();
			this.ventanaAgregarImagenes = null;
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

					if (ventanaAgregarImagenes == null) {

						String NombrePDF = ventanaEmail.getTextAdjunto().getText();

						if (mails.EnviarMail.enviarInformeAlCliente(correo, Asunto, Cuerpo, NombrePDF)) {

							ventanaGenerarPresupuesto.setChckPDFEnviado(true);

							ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();
							this.agenda.editarReparacionR(reparacionAeditar);

						}

					} else {
						String NombreWORD = ventanaEmail.getTextAdjunto().getText();

						if (mails.EnviarMail.enviarInformeAlCliente(correo, Asunto, Cuerpo, NombreWORD)) {

							ventanaGenerarPresupuesto.setChckWORDEnviado(true);

							ReparacionDTO reparacionAeditar = TomarDatosPresupuesto();
							this.agenda.editarReparacionR(reparacionAeditar);

						}

					}

				}

			}
		}

		else if (this.ventanaEmail != null && e.getSource() == this.ventanaEmail.getBtnEditar()) {

			ventanaEmail.getTextCuerpo().setEditable(true);

		}

		else if (this.ventanaEmail != null && e.getSource() == this.ventanaEmail.getBtnAdjunto()) {

			if (ventanaAgregarImagenes == null) {

				String NombrePDF = ventanaEmail.getTextAdjunto().getText();

				try {
					File path = new File("F:/ELS/Bariloche/Administracion/Sistema/Presupuestos PDF/" + NombrePDF);
					Desktop.getDesktop().open(path);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				String nombreWORD = ventanaEmail.getTextAdjunto().getText();

				try {
					File path = new File("F:/ELS/Administracion/Sistema/Informes Siemens/" + nombreWORD);
					Desktop.getDesktop().open(path);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
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
		
		
		
		else if ( ventanaMarcarAceptaciones != null && e.getSource() == this.ventanaMarcarAceptaciones.getBtnFiltrar()) {
			
		
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
				rfs.add(RowFilter

						.regexFilter(ventanaMarcarAceptaciones.getComboFiltroCliente().getSelectedItem().toString(),
								2));
			}

			if (ventanaMarcarAceptaciones.getRadioButtonSucursal().isSelected()
					&& ventanaMarcarAceptaciones.getComboFiltroSucursal().getSelectedItem() != null
					&& ventanaMarcarAceptaciones.getComboFiltroSucursal().getSelectedItem().toString() != null) {
				rfs.add(RowFilter.regexFilter(
						ventanaMarcarAceptaciones.getComboFiltroSucursal().getSelectedItem().toString(), 3));
			}

			
			if (ventanaMarcarAceptaciones.getRadioButtonAviso().isSelected()
					&& ventanaMarcarAceptaciones.getComboFiltroAviso().getSelectedItem() != null
					&& ventanaMarcarAceptaciones.getComboFiltroAviso().getSelectedItem().toString() != null) {
				rfs.add(RowFilter

						.regexFilter(ventanaMarcarAceptaciones.getComboFiltroAviso().getSelectedItem().toString(), 8));
			}
			
			if (ventanaMarcarAceptaciones.getRadioButtonELS().isSelected()
					&& ventanaMarcarAceptaciones.getComboFiltroELS().getSelectedItem() != null
					&& ventanaMarcarAceptaciones.getComboFiltroELS().getSelectedItem().toString() != null) {
				rfs.add(RowFilter

						.regexFilter(ventanaMarcarAceptaciones.getComboFiltroELS().getSelectedItem().toString(), 0));
			}
			
			
			rf = RowFilter.andFilter(rfs);

			tr.setRowFilter(rf);
			
			

		}
		
		
		else if (ventanaMarcarAceptaciones != null && e.getSource() == this.ventanaMarcarAceptaciones.getBtnMostrarTodo()) {
		
			this.ventanaMarcarAceptaciones.getTblReparaciones().setRowSorter(null);

		}
		
		
		else if (ventanaMarcarAceptaciones != null && e.getSource() == this.ventanaMarcarAceptaciones.getBtnActualizar()) {

		}
	}

	private void abrirSelectorImagen(JTextField txtRutaImagen_1, JTextField txtRutaImagen_2,
			JTextField txtRutaImagen_3) {

		String rutadefaulImagenes = "F:\\els\\Administracion\\Sistema\\Informes Siemens\\";

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
		ventanaMarcarAceptaciones.getBtnActualizar().addActionListener(this);

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
		ventanaMarcarAceptaciones.getBtnActualizar().addMouseListener(this);
		ventanaMarcarAceptaciones.getBtnFiltrar().addMouseListener(this);
		ventanaMarcarAceptaciones.getBtnMostrarTodo().addMouseListener(this);
		

		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroCliente());
		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroAviso());
		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroELS());
		AutoCompleteDecorator.decorate(ventanaMarcarAceptaciones.getComboFiltroSucursal());

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

		this.ventanaGenerarPresupuesto.getBtnGenerarInformeSiemens().addActionListener(this);

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

		this.ventanaMarcarAceptaciones.show();

	}

	// Método para manejar la selección de los checkboxes
	private void handleCheckboxSelection(int selectedRow, int selectedColumn) {
		if (selectedRow != -1 && selectedColumn != -1) {
			for (int i = this.ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount()
					- 4; i < this.ventanaMarcarAceptaciones.getModelReparaciones().getColumnCount(); i++) {
				if (i != selectedColumn) {
					this.ventanaMarcarAceptaciones.getModelReparaciones().setValueAt(false, selectedRow, i);
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

				if (selectedColumn == 8) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt("Aceptado", selectedRow, 7);
				}
				if (selectedColumn == 9) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt(" NO Aceptado", selectedRow, 7);
				}
				if (selectedColumn == 10) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt("Garantía", selectedRow, 7);
				}
				if (selectedColumn == 11) {
					ventanaMarcarAceptaciones.getModelReparaciones().setValueAt("Pendiente", selectedRow, 7);
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

		ventanaGenerarPresupuesto.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
		ventanaGenerarPresupuesto.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
		ventanaGenerarPresupuesto.setChckWORDGenerado(reparacion.getWORDgenerado());
		ventanaGenerarPresupuesto.setChckWORDEnviado(reparacion.getWORDenviado());

		ventanaPresupuestos.dispose();
		ventanaPresupuestos = null;

		ventanaSeleccionarELS.dispose();
		ventanaSeleccionarELS = null;

	}

	public void TomarDatosDeTablasParaVisualizacion(int numeroELS) {

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
		ventanaGenerarPresupuesto.getTextPrecioPeso().setText(PrecioPeso.toString());
		ventanaGenerarPresupuesto.getTextPrecioDolar().setText(PrecioDolar.toString());

		ventanaGenerarPresupuesto.getTextcondicionesPago().setText("Contado.");
		ventanaGenerarPresupuesto.getTextPlazoEntrega().setText("7 días.");

		ventanaGenerarPresupuesto.setChckPDFGenerado(reparacion.getPresupuestoGenerado());
		ventanaGenerarPresupuesto.setChckPDFEnviado(reparacion.getPresupuestoEnviado());
		ventanaGenerarPresupuesto.setChckWORDGenerado(reparacion.getWORDgenerado());
		ventanaGenerarPresupuesto.setChckWORDEnviado(reparacion.getWORDenviado());

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

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

		if (this.ventanaMarcarAceptaciones != null) {
			
			if (arg0.getSource() == this.ventanaMarcarAceptaciones.getBtnMax()) {
				
				System.out.println("max");

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
		double PrecioPeso = Double.parseDouble(this.ventanaGenerarPresupuesto.getTextPrecioPeso().getText());
		double PrecioDolar = Double.parseDouble(this.ventanaGenerarPresupuesto.getTextPrecioDolar().getText());

//		if (PrecioPeso != 0) {
//			presupuestoGenerado = true;
//		}
//		System.out.println(presupuestoEnviado);

		presupuestoEnviado = ventanaGenerarPresupuesto.getChckPDFEnviado();
		presupuestoGenerado = ventanaGenerarPresupuesto.getChckPDFGenerado();
		informeWordGenerado = ventanaGenerarPresupuesto.getChckWORDGenerado();
		informeWordEnviado = ventanaGenerarPresupuesto.getChckWORDEnviado();

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, informeCliente, PrecioPeso, PrecioDolar,
				presupuestoGenerado, presupuestoEnviado, informeWordGenerado, informeWordEnviado);

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

	private void agregarListenerAventanaEmail() {

		ventanaEmail.getBtnAdjuntarIMG().addActionListener(this);
		ventanaEmail.getBtnAdjunto().addActionListener(this);
		ventanaEmail.getBtnAgregarContacto().addActionListener(this);
		ventanaEmail.getBtnEditar().addActionListener(this);
		ventanaEmail.getBtnEnviar().addActionListener(this);

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