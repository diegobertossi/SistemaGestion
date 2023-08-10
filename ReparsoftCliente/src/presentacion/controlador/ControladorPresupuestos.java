package presentacion.controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
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
import presentacion.vista.VentanaPresupuestos;
import presentacion.vista.VentanaSeleccionarELS;
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

	private ControladorReparacion controladorReparacion;

	private Agenda agenda;

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	private ReparacionDTO reparacion;

	String numeros = "";
	boolean guardado = false;

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

		else if (this.ventanaAgregarImagenes != null
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

			} catch (IOException f) {
				f.printStackTrace();
			}

		}

		else if (this.ventanaAgregarImagenes != null && e.getSource() == this.ventanaAgregarImagenes.getBtnCancelar()) {

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

//	public void buscarYReemplazar(XWPFDocument doc, String textoBusqueda, String textoReemplazo, String rutaImagen) {
//	    for (XWPFParagraph paragraph : doc.getParagraphs()) {
//	        for (XWPFRun run : paragraph.getRuns()) {
//	            String text = run.getText(0);
//	            if (text != null && text.contains(textoBusqueda)) {
//	                text = text.replace(textoBusqueda, textoReemplazo);
//	                run.setText(text, 0);
//	            }
//	        }
//	    }
//
//	    for (XWPFTable table : doc.getTables()) {
//	        for (XWPFTableRow row : table.getRows()) {
//	            for (XWPFTableCell cell : row.getTableCells()) {
//	                for (XWPFParagraph paragraph : cell.getParagraphs()) {
//	                    for (XWPFRun run : paragraph.getRuns()) {
//	                        String text = run.getText(0);
//	                        if (text != null && text.contains(textoBusqueda)) {
//	                            run.setText("", 0);
//	                            if (rutaImagen != null && !rutaImagen.isEmpty()) {
//	                                try {
//	                                    BufferedImage image = ImageIO.read(new FileInputStream(rutaImagen));
//	                                    int width = image.getWidth();
//	                                    int height = image.getHeight();
//
//	                                    // Encode the image as base64
//	                                    byte[] imageBytes = Base64.getEncoder().encode(imageToBytes(image));
//
//	                                    // Add the image to the run
//	                                    try {
//											run.addPicture(new ByteArrayInputStream(imageBytes), XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(width), Units.toEMU(height));
//										} catch (InvalidFormatException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//	                                } catch (IOException e) {
//	                                    e.printStackTrace();
//	                                }
//	                            } else if (textoReemplazo != null && !textoReemplazo.isEmpty()) {
//	                                String[] lines = textoReemplazo.split("\\r?\\n");
//	                                for (int i = 0; i < lines.length; i++) {
//	                                    run.setText(lines[i], i);
//	                                    if (i < lines.length - 1) {
//	                                        run.addBreak();
//	                                    }
//	                                }
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	    }
//	}
//
//	private static byte[] imageToBytes(BufferedImage image) throws IOException {
//	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	    ImageIO.write(image, "png", baos);
//	    return baos.toByteArray();
//	}

//	public void buscarYReemplazar(XWPFDocument doc, String textoBusqueda, String textoReemplazo, String rutaImagen) {
//	    for (XWPFParagraph paragraph : doc.getParagraphs()) {
//	        for (XWPFRun run : paragraph.getRuns()) {
//	            String text = run.getText(0);
//	            if (text != null && text.contains(textoBusqueda)) {
//	                text = text.replace(textoBusqueda, textoReemplazo);
//	                run.setText(text, 0);
//	            }
//	        }
//	    }
//
//	    for (XWPFTable table : doc.getTables()) {
//	        for (XWPFTableRow row : table.getRows()) {
//	            for (XWPFTableCell cell : row.getTableCells()) {
//	                for (XWPFParagraph paragraph : cell.getParagraphs()) {
//	                    for (XWPFRun run : paragraph.getRuns()) {
//	                        String text = run.getText(0);
//	                        if (text != null && text.contains(textoBusqueda)) {
//	                            run.setText("", 0);
//	                            if (rutaImagen != null && !rutaImagen.isEmpty()) {
//	                                try {
//	                                    BufferedImage image = ImageIO.read(new FileInputStream(rutaImagen));
//	                                    int width = image.getWidth();
//	                                    int height = image.getHeight();
//
//	                                    // Encode the image as base64
//	                                    byte[] imageBytes;
//	                                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//	                                        ImageIO.write(image, "png", baos);
//	                                        imageBytes = baos.toByteArray();
//	                                    }
//
//	                                    // Add the image to the run
//	                                    try {
//											run.addPicture(new ByteArrayInputStream(imageBytes), XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(width), Units.toEMU(height));
//										} catch (InvalidFormatException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//	                                } catch (IOException e) {
//	                                    e.printStackTrace();
//	                                }
//	                            } else if (textoReemplazo != null && !textoReemplazo.isEmpty()) {
//	                                String[] lines = textoReemplazo.split("\\r?\\n");
//	                                for (int i = 0; i < lines.length; i++) {
//	                                    run.setText(lines[i], i);
//	                                    if (i < lines.length - 1) {
//	                                        run.addBreak();
//	                                    }
//	                                }
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	    }
//	    
//	    
//	    
//	    
//	    
//	}
//	

	public void buscarYReemplazar(XWPFDocument doc, String textoBusqueda, String textoReemplazo, String rutaImagen) {
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
							if (text != null && text.contains(textoBusqueda)) {
								run.setText("", 0);
								if (rutaImagen != null && !rutaImagen.isEmpty()) {
									try {
										
										

										File image = new File(rutaImagen);
										
										BufferedImage bimg = ImageIO.read(image);
										int width = bimg.getWidth();
										int height = bimg.getHeight();
										String imgFile = image.getName();
										int imgFormat = getImageFormat(imgFile);
										
										FileInputStream fis = new FileInputStream(image);
//										int imageType = XWPFDocument.PICTURE_TYPE_JPEG;
//										String imageFileName = image.getName();

										run.addPicture(fis, imgFormat, imgFile, Units.toEMU(width), Units.toEMU(height));
		
									
										//run.addPicture(fis, imageType, imageFileName, Units.toEMU(width),Units.toEMU(height));

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

	public static void addImagesToWordDocument(File imageFile1, File imageFile2)
			throws IOException, InvalidFormatException {
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph p = doc.createParagraph();
		XWPFRun r = p.createRun();
		BufferedImage bimg1 = ImageIO.read(imageFile1);
		int width1 = bimg1.getWidth();
		int height1 = bimg1.getHeight();
		BufferedImage bimg2 = ImageIO.read(imageFile2);
		int width2 = bimg2.getWidth();
		int height2 = bimg2.getHeight();
		String imgFile1 = imageFile1.getName();
		String imgFile2 = imageFile2.getName();
		int imgFormat1 = getImageFormat(imgFile1);
		int imgFormat2 = getImageFormat(imgFile2);
		String p1 = "Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog.";
		String p2 = "Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog.";
		r.setText(p1);
		r.addBreak();
		r.addPicture(new FileInputStream(imageFile1), imgFormat1, imgFile1, Units.toEMU(width1), Units.toEMU(height1));
		// page break
		// r.addBreak(BreakType.PAGE);
		// line break
		r.addBreak();
		r.setText(p2);
		r.addBreak();
		r.addPicture(new FileInputStream(imageFile2), imgFormat2, imgFile2, Units.toEMU(width2), Units.toEMU(height2));
		FileOutputStream out = new FileOutputStream("word_images.docx");
		doc.write(out);
		out.close();
		doc.close();
	}

	private static int getImageFormat(String imgFileName) {
		int format;
		if (imgFileName.endsWith(".emf"))
			format = XWPFDocument.PICTURE_TYPE_EMF;
		else if (imgFileName.endsWith(".wmf"))
			format = XWPFDocument.PICTURE_TYPE_WMF;
		else if (imgFileName.endsWith(".pict"))
			format = XWPFDocument.PICTURE_TYPE_PICT;
		else if (imgFileName.endsWith(".jpeg") || imgFileName.endsWith(".jpg"))
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		else if (imgFileName.endsWith(".png"))
			format = XWPFDocument.PICTURE_TYPE_PNG;
		else if (imgFileName.endsWith(".dib"))
			format = XWPFDocument.PICTURE_TYPE_DIB;
		else if (imgFileName.endsWith(".gif"))
			format = XWPFDocument.PICTURE_TYPE_GIF;
		else if (imgFileName.endsWith(".tiff"))
			format = XWPFDocument.PICTURE_TYPE_TIFF;
		else if (imgFileName.endsWith(".eps"))
			format = XWPFDocument.PICTURE_TYPE_EPS;
		else if (imgFileName.endsWith(".bmp"))
			format = XWPFDocument.PICTURE_TYPE_BMP;
		else if (imgFileName.endsWith(".wpg"))
			format = XWPFDocument.PICTURE_TYPE_WPG;
		else {
			return 0;
		}
		return format;
	}

//
//	private int calculateCellHeight(XWPFTableCell cell) {
//	    int totalHeight = 0;
//	    for (XWPFParagraph paragraph : cell.getParagraphs()) {
//	        totalHeight += paragraph.getSpacingAfter() + paragraph.getSpacingBefore();
//	        for (XWPFRun run : paragraph.getRuns()) {
//	            totalHeight += run.getFontFamily().length(); // Use the length of the font family string
//	        }
//	    }
//	    return totalHeight;
//	}

//
//	public void buscarYReemplazar(XWPFDocument doc, String textoBusqueda, String textoReemplazo) {
//	    for (XWPFParagraph paragraph : doc.getParagraphs()) {
//	        for (XWPFRun run : paragraph.getRuns()) {
//	            String text = run.getText(0);
//	            if (text != null && text.contains(textoBusqueda)) {
//	                text = text.replace(textoBusqueda, textoReemplazo);
//	                run.setText(text, 0);
//	            }
//	        }
//	    }
//
//	    for (XWPFTable table : doc.getTables()) {
//	        for (XWPFTableRow row : table.getRows()) {
//	            for (XWPFTableCell cell : row.getTableCells()) {
//	                for (XWPFParagraph paragraph : cell.getParagraphs()) {
//	                    for (XWPFRun run : paragraph.getRuns()) {
//	                        String text = run.getText(0);
//	                        if (text != null && text.contains(textoBusqueda)) {
//	                            // Split the replacement text by line breaks
//	                            String[] lines = textoReemplazo.split("\\r?\\n");
//	                            
//	                            // Clear the existing text and add the replacement lines
//	                            run.setText("", 0);
//	                            for (int i = 0; i < lines.length; i++) {
//	                                run.setText(lines[i], i);
//	                                if (i < lines.length - 1) {
//	                                    run.addBreak();
//	                                }
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	    }
//	}

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