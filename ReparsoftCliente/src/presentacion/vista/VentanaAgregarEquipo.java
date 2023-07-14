package presentacion.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import tiposPropios.JTextAreaCustom;
import dto.ClienteDTO;
import presentacion.controlador.ControladorPrincipal;
import presentacion.controlador.ControladorReparacion;
import java.awt.Frame;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import javax.swing.DropMode;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import javax.swing.JFormattedTextField;

public class VentanaAgregarEquipo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton BotonGuardar;
	private JButton BotonGenerarRegistro;
	private JButton BotonVerificarIngresoAnterior;
	private JButton BotonNuevaReparacion;
	private JButton btnGenerarSerie;
	private JButton btnFechaDefault;
	
	private JButton btnrecargarLista;

	

	private JTextField textELS;
	private int ELS = 1;
	private JTextAreaCustom textFalla;

	private JTextField textAvisoCliente;
	private JTextField textClienteCliente;
	private JTextField textRemitoCliente;
	private JDateChooser FechaEntrada;
	private JTextFieldDateEditor textFechafabricacion;

	private JRadioButton rdbtnMDQ;
	private JRadioButton rdbtnCABA;
	private JRadioButton rdbtnBRC;
	private JRadioButton rdbtnENVIADO;

	private ButtonGroup GrupoEstadoFisico;;

	private JComboBox comboClientes;
	private JComboBox comboSucursal;
	private JComboBox comboNombreEquipo;
	private JComboBox comboMarca;
	private JComboBox comboModelo;
	private JComboBox comboSerie;

	private JLabel lblEls;
	private JLabel lblNombreEquipo;

	private JLabel lblMarca;
	private JLabel lblModelo;
	private JLabel lblNserie;
	private JLabel lblFalla;
	private JLabel lblAvisoCliente;
	private JLabel lblClienteCliente;
	private JLabel lblRemitoCliente;
	private JLabel lblCliente;
	private JLabel lblSucursal;
	private JLabel lblDatosDeEquipo;
	private JLabel lblFechaEntrada;
	private JLabel lblEstadoFisico;

	private JPanel panel_1;
	private JScrollPane scrollPane;
	private ControladorReparacion controladorReparacion;
	private JPanel panel_2;
	private JPanel panel_3;
	private JTextField textSerie;
	private JButton btnaltaCliente;

	public VentanaAgregarEquipo(ControladorReparacion controladorReparacion) {

		super();
		setResizable(false);
		this.controladorReparacion = controladorReparacion;

		FechaEntrada = new com.toedter.calendar.JDateChooser("dd/MM/yyyy", "##-##-####", '-');

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBounds(0, 0, 784, 561);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		lblEls = new JLabel("ELS: ");
		lblEls.setForeground(Color.BLUE);
		lblEls.setBounds(6, 11, 56, 26);
		panel.add(lblEls);
		lblEls.setFont(new Font("Cambria", Font.BOLD, 22));

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(new LineBorder(null));
		panel_1.setBounds(6, 101, 768, 260);
		panel.add(panel_1);
		panel_1.setLayout(null);

		comboSerie = new JComboBox();
		comboSerie.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboSerie.setBackground(SystemColor.inactiveCaptionBorder);
		comboSerie.setEditable(true);
		comboSerie.setBounds(120, 125, 229, 20);
		panel_1.add(comboSerie);

		comboModelo = new JComboBox();
		comboModelo.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboModelo.setBackground(SystemColor.inactiveCaptionBorder);
		comboModelo.setEditable(true);
		comboModelo.setBounds(120, 103, 229, 20);
		panel_1.add(comboModelo);

		comboMarca = new JComboBox();
		comboMarca.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboMarca.setBackground(SystemColor.inactiveCaptionBorder);
		comboMarca.setEditable(true);
		comboMarca.setBounds(120, 81, 229, 20);
		panel_1.add(comboMarca);

		comboNombreEquipo = new JComboBox();
		comboNombreEquipo.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboNombreEquipo.setBackground(SystemColor.inactiveCaptionBorder);
		comboNombreEquipo.setEditable(true);
		comboNombreEquipo.setBounds(120, 59, 229, 20);
		panel_1.add(comboNombreEquipo);

		lblNombreEquipo = new JLabel("Equipo: ");
		lblNombreEquipo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblNombreEquipo.setBounds(12, 62, 102, 14);
		panel_1.add(lblNombreEquipo);
		lblNombreEquipo.setFont(new Font("Cambria", Font.BOLD, 14));

		lblMarca = new JLabel("Marca:  ");
		lblMarca.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblMarca.setBounds(12, 84, 102, 14);
		panel_1.add(lblMarca);
		lblMarca.setFont(new Font("Cambria", Font.BOLD, 14));

		lblModelo = new JLabel("Modelo: ");
		lblModelo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblModelo.setBounds(12, 106, 102, 14);
		panel_1.add(lblModelo);
		lblModelo.setFont(new Font("Cambria", Font.BOLD, 14));

		lblNserie = new JLabel("N° de Serie: ");
		lblNserie.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblNserie.setBounds(12, 128, 102, 14);
		panel_1.add(lblNserie);
		lblNserie.setFont(new Font("Cambria", Font.BOLD, 14));

		lblFalla = new JLabel("Falla: ");
		lblFalla.setBounds(12, 187, 52, 14);
		panel_1.add(lblFalla);
		lblFalla.setFont(new Font("Cambria", Font.BOLD, 14));

		lblAvisoCliente = new JLabel("Aviso Cliente: ");
		lblAvisoCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblAvisoCliente.setBounds(396, 65, 122, 14);
		panel_1.add(lblAvisoCliente);
		lblAvisoCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblClienteCliente = new JLabel("Cliente de Cliente: ");
		lblClienteCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblClienteCliente.setBounds(396, 21, 122, 14);
		panel_1.add(lblClienteCliente);
		lblClienteCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblRemitoCliente = new JLabel("Remito de Cliente: ");
		lblRemitoCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblRemitoCliente.setBounds(396, 43, 122, 14);
		panel_1.add(lblRemitoCliente);
		lblRemitoCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblCliente = new JLabel("Cliente: ");
		lblCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblCliente.setBounds(12, 18, 102, 14);
		panel_1.add(lblCliente);
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblSucursal = new JLabel("Sucursal: ");
		lblSucursal.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblSucursal.setBounds(12, 39, 102, 14);
		panel_1.add(lblSucursal);
		lblSucursal.setFont(new Font("Cambria", Font.BOLD, 14));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 203, 337, 44);
		panel_1.add(scrollPane);

		textFalla = new JTextAreaCustom();
		textFalla.setBackground(SystemColor.inactiveCaptionBorder);
		textFalla.setLocation(117, 0);
		textFalla.setSize(215, 20);
		textFalla.setAlignmentY(Component.TOP_ALIGNMENT);
		textFalla.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setViewportView(textFalla);
		textFalla.setFont(new Font("Cambria", Font.PLAIN, 14));
		textFalla.setLineWrap(true);
		textFalla.setWrapStyleWord(true);
		textFalla.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));

		textAvisoCliente = new JTextField();
		textAvisoCliente.setBackground(SystemColor.inactiveCaptionBorder);
		textAvisoCliente.setAlignmentY(Component.TOP_ALIGNMENT);
		textAvisoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
		textAvisoCliente.setBounds(522, 62, 211, 20);
		panel_1.add(textAvisoCliente);
		textAvisoCliente.setHorizontalAlignment(SwingConstants.LEFT);
		textAvisoCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		textAvisoCliente.setColumns(10);

		textClienteCliente = new JTextField();
		textClienteCliente.setBackground(SystemColor.inactiveCaptionBorder);
		textClienteCliente.setAlignmentY(Component.TOP_ALIGNMENT);
		textClienteCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
		textClienteCliente.setBounds(522, 18, 211, 20);
		panel_1.add(textClienteCliente);
		textClienteCliente.setHorizontalAlignment(SwingConstants.LEFT);
		textClienteCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		textClienteCliente.setColumns(10);

		textRemitoCliente = new JTextField();
		textRemitoCliente.setBackground(SystemColor.inactiveCaptionBorder);
		textRemitoCliente.setAlignmentY(Component.TOP_ALIGNMENT);
		textRemitoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
		textRemitoCliente.setBounds(522, 40, 211, 20);
		panel_1.add(textRemitoCliente);
		textRemitoCliente.setHorizontalAlignment(SwingConstants.LEFT);
		textRemitoCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		textRemitoCliente.setColumns(10);

		comboClientes = new JComboBox();
		comboClientes.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboClientes.setBackground(SystemColor.inactiveCaptionBorder);
		comboClientes.setBounds(120, 15, 229, 20);
		panel_1.add(comboClientes);

		comboSucursal = new JComboBox();
		comboSucursal.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboSucursal.setBackground(SystemColor.inactiveCaptionBorder);
		comboSucursal.setBounds(120, 37, 229, 20);
		panel_1.add(comboSucursal);

		JLabel lblFechaDeFabr = new JLabel("Fecha de Fabr.: ");
		lblFechaDeFabr.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblFechaDeFabr.setFont(new Font("Cambria", Font.BOLD, 14));
		lblFechaDeFabr.setBounds(396, 128, 122, 14);
		panel_1.add(lblFechaDeFabr);

		btnGenerarSerie = new JButton("<html><center>Generar N° de Serie</html>");
		btnGenerarSerie.setForeground(Color.BLACK);
		btnGenerarSerie.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGenerarSerie.setBackground(new Color(176, 224, 230));
		btnGenerarSerie.setBounds(120, 148, 96, 53);
		panel_1.add(btnGenerarSerie);

		btnFechaDefault = new JButton("<html><center>Si no se conoce fecha, presionar aquí</html>");
		btnFechaDefault.setForeground(Color.BLACK);
		btnFechaDefault.setFont(new Font("Cambria", Font.BOLD, 14));
		btnFechaDefault.setBackground(new Color(176, 224, 230));
		btnFechaDefault.setBounds(557, 151, 176, 44);
		panel_1.add(btnFechaDefault);

		textSerie = new JTextField();
		textSerie.setHorizontalAlignment(SwingConstants.LEFT);
		textSerie.setFont(new Font("Cambria", Font.PLAIN, 14));
		textSerie.setColumns(10);
		textSerie.setBackground(SystemColor.inactiveCaptionBorder);
		textSerie.setAlignmentY(0.0f);
		textSerie.setAlignmentX(0.0f);
		textSerie.setBounds(138, 125, 193, 20);
		panel_1.add(textSerie);

		textFechafabricacion = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		textFechafabricacion.setHorizontalAlignment(SwingConstants.CENTER);
		textFechafabricacion.setFont(new Font("Cambria", Font.BOLD, 14));
		textFechafabricacion.setBounds(522, 126, 211, 20);
		panel_1.add(textFechafabricacion);
		
		btnrecargarLista = new JButton("<html><center>Actualizar Listado Clientes</html>");
		btnrecargarLista.setBounds(219, 148, 129, 53);
		panel_1.add(btnrecargarLista);
		btnrecargarLista.setForeground(Color.BLACK);
		btnrecargarLista.setFont(new Font("Cambria", Font.BOLD, 14));
		btnrecargarLista.setBackground(new Color(176, 224, 230));

		lblDatosDeEquipo = new JLabel("DATOS DEL EQUIPO: ");
		lblDatosDeEquipo.setBounds(6, 57, 205, 17);
		panel.add(lblDatosDeEquipo);
		lblDatosDeEquipo.setFont(new Font("Cambria", Font.BOLD, 18));

		lblFechaEntrada = new JLabel("Fecha de Entrada: ");
		lblFechaEntrada.setBounds(480, 77, 140, 17);
		panel.add(lblFechaEntrada);
		lblFechaEntrada.setFont(new Font("Cambria", Font.BOLD, 16));

		textELS = new JTextField();
		textELS.setBackground(SystemColor.activeCaption);
		textELS.setForeground(new Color(0, 0, 255));
		textELS.setEditable(false);
		textELS.setHorizontalAlignment(SwingConstants.CENTER);
		textELS.setBounds(60, 11, 115, 26);
		panel.add(textELS);
		textELS.setFont(new Font("Cambria", Font.BOLD, 22));
		textELS.setColumns(10);
		textELS.setAlignmentX(CENTER_ALIGNMENT);

		FechaEntrada.setFont(new Font("Cambria", Font.BOLD, 14));
		FechaEntrada.setBounds(622, 75, 152, 20);
		panel.add(FechaEntrada);

		GrupoEstadoFisico = new ButtonGroup();

		panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_3.setBounds(6, 452, 768, 65);
		panel.add(panel_3);
		panel_3.setLayout(null);

		BotonNuevaReparacion = new JButton("<html><center>Nueva Reparación</html>");
		BotonNuevaReparacion.setEnabled(false);
		BotonNuevaReparacion.setBackground(new Color(152, 251, 152));
		BotonNuevaReparacion.setBounds(620, 11, 105, 45);
		panel_3.add(BotonNuevaReparacion);
		BotonNuevaReparacion.setForeground(Color.BLACK);
		BotonNuevaReparacion.setFont(new Font("Cambria", Font.BOLD, 14));

		BotonGenerarRegistro = new JButton("<html><center>Generár Registro<html>");
		BotonGenerarRegistro.setBackground(new Color(152, 251, 152));
		BotonGenerarRegistro.setBounds(321, 11, 105, 45);
		panel_3.add(BotonGenerarRegistro);
		BotonGenerarRegistro.setForeground(Color.BLACK);
		BotonGenerarRegistro.setFont(new Font("Cambria", Font.BOLD, 14));

		BotonGuardar = new JButton("Guardar");
		BotonGuardar.setBackground(new Color(152, 251, 152));
		BotonGuardar.setBounds(436, 11, 105, 45);
		panel_3.add(BotonGuardar);
		BotonGuardar.setForeground(Color.BLACK);
		BotonGuardar.setFont(new Font("Cambria", Font.BOLD, 14));

		BotonVerificarIngresoAnterior = new JButton("<html><center>Verificar Ingreso</html>");
		BotonVerificarIngresoAnterior.setBackground(SystemColor.textHighlight);
		BotonVerificarIngresoAnterior.setBounds(40, 11, 105, 45);
		panel_3.add(BotonVerificarIngresoAnterior);
		BotonVerificarIngresoAnterior.setForeground(Color.BLACK);
		BotonVerificarIngresoAnterior.setFont(new Font("Cambria", Font.BOLD, 14));

		btnaltaCliente = new JButton("<html><center>Alta Cliente<html>");
		btnaltaCliente.setForeground(Color.BLACK);
		btnaltaCliente.setFont(new Font("Cambria", Font.BOLD, 14));
		btnaltaCliente.setBackground(new Color(152, 251, 152));
		btnaltaCliente.setBounds(155, 11, 105, 45);
		panel_3.add(btnaltaCliente);

		lblEstadoFisico = new JLabel("Estado Físico: ");
		lblEstadoFisico.setBounds(6, 372, 128, 14);
		panel.add(lblEstadoFisico);
		lblEstadoFisico.setFont(new Font("Cambria", Font.BOLD, 14));

		panel_2 = new JPanel();
		panel_2.setBounds(6, 390, 356, 37);
		panel.add(panel_2);
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setLayout(null);

		rdbtnMDQ = new JRadioButton("MDQ");
		rdbtnMDQ.setBackground(SystemColor.activeCaption);
		rdbtnMDQ.setFont(new Font("Cambria", Font.PLAIN, 14));
		rdbtnMDQ.setBounds(93, 7, 85, 23);
		panel_2.add(rdbtnMDQ);

		rdbtnBRC = new JRadioButton("BRC");
		rdbtnBRC.setBackground(SystemColor.activeCaption);
		rdbtnBRC.setFont(new Font("Cambria", Font.PLAIN, 14));
		rdbtnBRC.setBounds(180, 7, 60, 23);
		panel_2.add(rdbtnBRC);

		rdbtnENVIADO = new JRadioButton("ENVIADO");
		rdbtnENVIADO.setBackground(SystemColor.activeCaption);
		rdbtnENVIADO.setFont(new Font("Cambria", Font.PLAIN, 14));
		rdbtnENVIADO.setBounds(256, 7, 94, 23);
		panel_2.add(rdbtnENVIADO);

		rdbtnCABA = new JRadioButton("CABA");
		rdbtnCABA.setBackground(SystemColor.activeCaption);
		rdbtnCABA.setFont(new Font("Cambria", Font.PLAIN, 14));
		rdbtnCABA.setBounds(16, 7, 75, 23);
		panel_2.add(rdbtnCABA);
		GrupoEstadoFisico.add(rdbtnMDQ);
		GrupoEstadoFisico.add(rdbtnBRC);
		GrupoEstadoFisico.add(rdbtnENVIADO);
		GrupoEstadoFisico.add(rdbtnCABA);

		this.setVisible(true);

	}

	public JPanel getPanel_1() {
		return panel_1;
	}

	public void setPanel_1(JPanel panel_1) {
		this.panel_1 = panel_1;
	}

	public JTextField getTextSerie() {
		return textSerie;
	}

	public void setTextSerie(JTextField textSerie) {
		this.textSerie = textSerie;
	}

	public JButton getBotonNuevaReparacion() {
		return BotonNuevaReparacion;
	}

	public void setBotonNuevaReparacion(JButton botonNuevaReparacion) {
		BotonNuevaReparacion = botonNuevaReparacion;
	}

	public JButton getBotonGuardar() {
		return BotonGuardar;
	}

	public void setBotonGuardar(JButton botonGuardar) {
		BotonGuardar = botonGuardar;
	}

	public JRadioButton getRdbtnMDQ() {
		return rdbtnMDQ;
	}

	public void setRdbtnMDQ(JRadioButton rdbtnMDQ) {
		this.rdbtnMDQ = rdbtnMDQ;
	}

	public JRadioButton getRdbtnCABA() {
		return rdbtnCABA;
	}

	public void setRdbtnCABA(JRadioButton rdbtnCABA) {
		this.rdbtnCABA = rdbtnCABA;
	}

	public JRadioButton getRdbtnBRC() {
		return rdbtnBRC;
	}

	public void setRdbtnBRC(JRadioButton rdbtnBRC) {
		this.rdbtnBRC = rdbtnBRC;
	}

	public JRadioButton getRdbtnENVIADO() {
		return rdbtnENVIADO;
	}

	public void setRdbtnENVIADO(JRadioButton rdbtnENVIADO) {
		this.rdbtnENVIADO = rdbtnENVIADO;
	}

	public JComboBox getComboClientes() {
		return comboClientes;
	}

	public void setComboClientes(JComboBox comboClientes) {
		this.comboClientes = comboClientes;
	}

	public JComboBox getComboSucursal() {
		return comboSucursal;
	}

	public void setComboSucursal(JComboBox comboSucursal) {
		this.comboSucursal = comboSucursal;
	}

	public JComboBox getComboNombreEquipo() {
		return comboNombreEquipo;
	}

	public void setComboNombreEquipo(JComboBox comboNombreEquipo) {
		this.comboNombreEquipo = comboNombreEquipo;
	}

	public JComboBox getComboMarca() {
		return comboMarca;
	}

	public void setComboMarca(JComboBox comboMarca) {
		this.comboMarca = comboMarca;
	}

	public JComboBox getComboModelo() {
		return comboModelo;
	}

	public void setComboModelo(JComboBox comboModelo) {
		this.comboModelo = comboModelo;
	}

	public JComboBox getComboSerie() {
		return comboSerie;
	}

	public void setComboSerie(JComboBox comboSerie) {
		this.comboSerie = comboSerie;
	}

	public JButton getBotonGenerarRegistro() {
		return BotonGenerarRegistro;
	}

	public void setBotonGenerarRegistro(JButton botonGenerarRegistro) {
		BotonGenerarRegistro = botonGenerarRegistro;
	}

	public ButtonGroup getGrupoEstadoFisico() {
		return GrupoEstadoFisico;
	}

	public void setGrupoEstadoFisico(ButtonGroup grupoEstadoFisico) {
		GrupoEstadoFisico = grupoEstadoFisico;
	}

	public JDateChooser getFechaEntrada() {
		return FechaEntrada;
	}

	public void setFechaEntrada(JDateChooser fechaEntrada) {
		FechaEntrada = fechaEntrada;
	}

	public JTextFieldDateEditor getTextFechafabricacion() {
		return textFechafabricacion;
	}

	public void setTextFechafabricacion(JTextFieldDateEditor textFechafabricacion) {
		this.textFechafabricacion = textFechafabricacion;
	}

	public void setTextFechafabricacion2(java.util.Date textFechafabricacion) {
		this.textFechafabricacion.setDate(textFechafabricacion);
	}

	public String getTextELS() {
		return textELS.getText();
	}

	public void setTextELS(String textELS) {
		this.textELS.setText(textELS);
	}

	public JTextAreaCustom getTextFalla() {
		return textFalla;
	}

	public void setTextFalla(JTextAreaCustom textFalla) {
		this.textFalla = textFalla;
	}

	public void setTextFalla(String string) {
		this.textFalla.setText(string);

	}

	public JTextField getTextAvisoCliente() {
		return textAvisoCliente;
	}

	public void setTextAvisoCliente(JTextField textAvisoCliente) {
		this.textAvisoCliente = textAvisoCliente;
	}

	public JTextField getTextClienteCliente() {
		return textClienteCliente;
	}

	public void setTextClienteCliente(JTextField textClienteCliente) {
		this.textClienteCliente = textClienteCliente;
	}

	public JTextField getTextRemitoCliente() {
		return textRemitoCliente;
	}

	public void setTextRemitoCliente(JTextField textRemitoCliente) {
		this.textRemitoCliente = textRemitoCliente;
	}

	public JButton getBtnGenerarSerie() {
		return btnGenerarSerie;
	}

	public void setBtnGenerarSerie(JButton btnGenerarSerie) {
		this.btnGenerarSerie = btnGenerarSerie;
	}

	public JButton getBtnFechaDefault() {
		return btnFechaDefault;
	}

	public void setBtnFechaDefault(JButton btnFechaDefault) {
		this.btnFechaDefault = btnFechaDefault;
	}

	public JButton getBotonVerificarIngresoAnterior() {
		return BotonVerificarIngresoAnterior;
	}

	public void setBotonVerificarIngresoAnterior(JButton botonVerificarIngresoAnterior) {
		BotonVerificarIngresoAnterior = botonVerificarIngresoAnterior;
	}

	public JButton getBtnaltaCliente() {
		return btnaltaCliente;
	}

	public void setBtnaltaCliente(JButton btnaltaCliente) {
		this.btnaltaCliente = btnaltaCliente;
	}
	public JButton getBtnrecargarLista() {
		return btnrecargarLista;
	}

	public void setBtnrecargarLista(JButton btnrecargarLista) {
		this.btnrecargarLista = btnrecargarLista;
	}
	
}
