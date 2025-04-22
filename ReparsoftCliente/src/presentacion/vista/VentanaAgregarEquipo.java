package presentacion.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import tiposPropios.JTextAreaCustom;
import presentacion.controlador.ControladorReparacion;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Cursor;

public class VentanaAgregarEquipo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton BotonGuardar;
	private JButton BotonGenerarRegistro;
	private JButton BotonVerificarIngresoAnterior;
	private JButton BotonNuevaReparacion;
	private JButton btnGenerarSerie;
	private JButton btnFechaDefault;

	private JTextField textELS;
	@SuppressWarnings("unused")
	private int ELS = 1;
	private JTextAreaCustom textFalla;

	private JTextField textAvisoCliente;
	private JTextField textClienteCliente;
	private JTextField textRemitoCliente;
	private JDateChooser FechaEntrada;
	private JTextFieldDateEditor textFechafabricacion;

	private JRadioButton rdbtnMDP;
	private JRadioButton rdbtnCABA;
	private JRadioButton rdbtnBRC;

	private ButtonGroup GrupoEstadoFisico;;

	private JComboBox<?> comboClientes;
	private JComboBox<?> comboSucursal;
	private JComboBox<?> comboNombreEquipo;
	private JComboBox<?> comboMarca;
	private JComboBox<?> comboModelo;
	private JComboBox<?> comboSerie;

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
	@SuppressWarnings("unused")
	private ControladorReparacion controladorReparacion;
	private JPanel panel_2;
	private JPanel panel_3;
	private JTextField textSerie;
	private JButton btnaltaCliente;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;

	public VentanaAgregarEquipo(ControladorReparacion controladorReparacion) {

		super();
		setResizable(false);
		this.controladorReparacion = controladorReparacion;
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		FechaEntrada = new com.toedter.calendar.JDateChooser("dd/MM/yyyy", "##-##-####", '-');
		FechaEntrada.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 821, 585);
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
		panel.setBounds(0, 0, 805, 547);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		lblEls = new JLabel("ELS: ");
		lblEls.setForeground(Color.BLUE);
		lblEls.setBounds(6, 11, 56, 54);
		panel.add(lblEls);
		lblEls.setFont(new Font("Cambria", Font.BOLD, 22));

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(new LineBorder(null));
		panel_1.setBounds(6, 106, 768, 361);
		panel.add(panel_1);
		panel_1.setLayout(null);

		panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setBounds(12, 75, 487, 264);
		panel_1.add(panel_6);
		panel_6.setLayout(null);

		comboSerie = new JComboBox<Object>();
		comboSerie.setBounds(125, 110, 190, 20);
		panel_6.add(comboSerie);
		comboSerie.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboSerie.setBackground(SystemColor.inactiveCaptionBorder);
		comboSerie.setEditable(true);

		comboModelo = new JComboBox<Object>();
		comboModelo.setBounds(125, 79, 347, 20);
		panel_6.add(comboModelo);
		comboModelo.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboModelo.setBackground(SystemColor.inactiveCaptionBorder);
		comboModelo.setEditable(true);

		comboMarca = new JComboBox<Object>();
		comboMarca.setBounds(125, 48, 347, 20);
		panel_6.add(comboMarca);
		comboMarca.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboMarca.setBackground(SystemColor.inactiveCaptionBorder);
		comboMarca.setEditable(true);

		comboNombreEquipo = new JComboBox<Object>();
		comboNombreEquipo.setBounds(125, 17, 347, 20);
		panel_6.add(comboNombreEquipo);
		comboNombreEquipo.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboNombreEquipo.setBackground(SystemColor.inactiveCaptionBorder);
		comboNombreEquipo.setEditable(true);

		lblNombreEquipo = new JLabel("Equipo: ");
		lblNombreEquipo.setBounds(24, 20, 87, 14);
		panel_6.add(lblNombreEquipo);
		lblNombreEquipo.setBorder(null);
		lblNombreEquipo.setFont(new Font("Cambria", Font.BOLD, 14));

		lblMarca = new JLabel("Marca:  ");
		lblMarca.setBounds(24, 51, 87, 14);
		panel_6.add(lblMarca);
		lblMarca.setBorder(null);
		lblMarca.setFont(new Font("Cambria", Font.BOLD, 14));

		lblModelo = new JLabel("Modelo: ");
		lblModelo.setBounds(24, 82, 87, 14);
		panel_6.add(lblModelo);
		lblModelo.setBorder(null);
		lblModelo.setFont(new Font("Cambria", Font.BOLD, 14));

		lblNserie = new JLabel("N° de Serie: ");
		lblNserie.setBounds(24, 113, 87, 14);
		panel_6.add(lblNserie);
		lblNserie.setBorder(null);
		lblNserie.setFont(new Font("Cambria", Font.BOLD, 14));

		JLabel lblFechaDeFabr = new JLabel("Fecha de Fabr.: ");
		lblFechaDeFabr.setBounds(22, 144, 100, 14);
		panel_6.add(lblFechaDeFabr);
		lblFechaDeFabr.setBorder(null);
		lblFechaDeFabr.setFont(new Font("Cambria", Font.BOLD, 14));

		btnGenerarSerie = new JButton("<html><center>Generar N° de Serie</html>");
		btnGenerarSerie.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGenerarSerie.setBounds(325, 110, 147, 20);
		panel_6.add(btnGenerarSerie);
		btnGenerarSerie.setForeground(Color.BLACK);
		btnGenerarSerie.setFont(new Font("Cambria", Font.BOLD, 12));
		btnGenerarSerie.setBackground(new Color(176, 224, 230));

		btnFechaDefault = new JButton("<html><center>Si no se conoce fecha, presionar aquí</html>");
		btnFechaDefault.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFechaDefault.setBounds(235, 140, 237, 23);
		panel_6.add(btnFechaDefault);
		btnFechaDefault.setForeground(Color.BLACK);
		btnFechaDefault.setFont(new Font("Cambria", Font.BOLD, 12));
		btnFechaDefault.setBackground(new Color(176, 224, 230));

		textFechafabricacion = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		textFechafabricacion.setBounds(125, 141, 100, 20);
		panel_6.add(textFechafabricacion);
		textFechafabricacion.setHorizontalAlignment(SwingConstants.CENTER);
		textFechafabricacion.setFont(new Font("Cambria", Font.BOLD, 14));

		textSerie = new JTextField();
		textSerie.setBounds(125, 110, 190, 20);
		panel_6.add(textSerie);
		textSerie.setHorizontalAlignment(SwingConstants.LEFT);
		textSerie.setFont(new Font("Cambria", Font.PLAIN, 14));
		textSerie.setColumns(10);
		textSerie.setBackground(SystemColor.inactiveCaptionBorder);
		textSerie.setAlignmentY(0.0f);
		textSerie.setAlignmentX(0.0f);

		lblFalla = new JLabel("Falla: ");
		lblFalla.setBounds(24, 181, 52, 14);
		panel_6.add(lblFalla);
		lblFalla.setFont(new Font("Cambria", Font.BOLD, 14));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 197, 448, 56);
		panel_6.add(scrollPane);

		textFalla = new JTextAreaCustom();
		textFalla.setTabSize(0);
		textFalla.setBackground(SystemColor.inactiveCaptionBorder);
		textFalla.setLocation(12, 0);
		textFalla.setSize(215, 20);
		textFalla.setAlignmentY(Component.TOP_ALIGNMENT);
		textFalla.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setViewportView(textFalla);
		textFalla.setFont(new Font("Cambria", Font.PLAIN, 14));
		textFalla.setLineWrap(true);
		textFalla.setWrapStyleWord(true);
		textFalla.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));

		panel_7 = new JPanel();
		panel_7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_7.setOpaque(false);
		panel_7.setBounds(509, 75, 237, 264);
		panel_1.add(panel_7);
		panel_7.setLayout(null);

		lblAvisoCliente = new JLabel("Aviso Cliente: ");
		lblAvisoCliente.setBounds(10, 89, 122, 14);
		panel_7.add(lblAvisoCliente);
		lblAvisoCliente.setBorder(null);
		lblAvisoCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblClienteCliente = new JLabel("Cliente de Cliente: ");
		lblClienteCliente.setBounds(10, 11, 122, 14);
		panel_7.add(lblClienteCliente);
		lblClienteCliente.setBorder(null);
		lblClienteCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblRemitoCliente = new JLabel("Remito de Cliente: ");
		lblRemitoCliente.setBounds(10, 50, 122, 14);
		panel_7.add(lblRemitoCliente);
		lblRemitoCliente.setBorder(null);
		lblRemitoCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		textAvisoCliente = new JTextField();
		textAvisoCliente.setBounds(10, 103, 211, 20);
		panel_7.add(textAvisoCliente);
		textAvisoCliente.setBackground(SystemColor.inactiveCaptionBorder);
		textAvisoCliente.setAlignmentY(Component.TOP_ALIGNMENT);
		textAvisoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
		textAvisoCliente.setHorizontalAlignment(SwingConstants.LEFT);
		textAvisoCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		//textAvisoCliente.setColumns(10);

		textClienteCliente = new JTextField();
		textClienteCliente.setBounds(10, 26, 211, 20);
		panel_7.add(textClienteCliente);
		textClienteCliente.setBackground(SystemColor.inactiveCaptionBorder);
		textClienteCliente.setAlignmentY(Component.TOP_ALIGNMENT);
		textClienteCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
		textClienteCliente.setHorizontalAlignment(SwingConstants.LEFT);
		textClienteCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		//textClienteCliente.setColumns(10);

		textRemitoCliente = new JTextField();
		textRemitoCliente.setBounds(10, 65, 211, 20);
		panel_7.add(textRemitoCliente);
		textRemitoCliente.setBackground(SystemColor.inactiveCaptionBorder);
		textRemitoCliente.setAlignmentY(Component.TOP_ALIGNMENT);
		textRemitoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
		textRemitoCliente.setHorizontalAlignment(SwingConstants.LEFT);
		textRemitoCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		//textRemitoCliente.setColumns(10);

		panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setOpaque(false);
		panel_5.setBounds(12, 15, 734, 54);
		panel_1.add(panel_5);
		panel_5.setLayout(null);

		lblCliente = new JLabel("Cliente: ");
		lblCliente.setBounds(25, 20, 50, 14);
		panel_5.add(lblCliente);
		lblCliente.setBorder(null);
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblSucursal = new JLabel("Sucursal: ");
		lblSucursal.setBounds(376, 20, 61, 14);
		panel_5.add(lblSucursal);
		lblSucursal.setBorder(null);
		lblSucursal.setFont(new Font("Cambria", Font.BOLD, 14));

		comboClientes = new JComboBox<Object>();
		comboClientes.setBounds(75, 17, 289, 20);
		panel_5.add(comboClientes);
		comboClientes.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboClientes.setBackground(SystemColor.inactiveCaptionBorder);

		comboSucursal = new JComboBox<Object>();
		comboSucursal.setBounds(435, 17, 289, 20);
		panel_5.add(comboSucursal);
		comboSucursal.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboSucursal.setBackground(SystemColor.inactiveCaptionBorder);

		lblDatosDeEquipo = new JLabel("DATOS DEL EQUIPO: ");
		lblDatosDeEquipo.setBounds(6, 78, 179, 17);
		panel.add(lblDatosDeEquipo);
		lblDatosDeEquipo.setFont(new Font("Cambria", Font.BOLD, 18));

		lblFechaEntrada = new JLabel("Fecha de Entrada: ");
		lblFechaEntrada.setBounds(480, 78, 140, 17);
		panel.add(lblFechaEntrada);
		lblFechaEntrada.setFont(new Font("Cambria", Font.BOLD, 16));

		textELS = new JTextField();
		textELS.setBorder(new LineBorder(new Color(0, 0, 0)));
		textELS.setBackground(SystemColor.activeCaption);
		textELS.setForeground(new Color(0, 0, 255));
		textELS.setEditable(false);
		textELS.setHorizontalAlignment(SwingConstants.CENTER);
		textELS.setBounds(60, 11, 115, 54);
		panel.add(textELS);
		textELS.setFont(new Font("Cambria", Font.BOLD, 22));
		textELS.setColumns(10);
		textELS.setAlignmentX(CENTER_ALIGNMENT);

		FechaEntrada.setFont(new Font("Cambria", Font.BOLD, 14));
		FechaEntrada.setBounds(622, 76, 152, 20);
		panel.add(FechaEntrada);

		GrupoEstadoFisico = new ButtonGroup();

		panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(372, 473, 402, 66);
		panel.add(panel_3);
		panel_3.setLayout(null);

		BotonNuevaReparacion = new JButton("<html><center>NUEVA REPARACIÓN</html>");
		BotonNuevaReparacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonNuevaReparacion.setEnabled(false);
		BotonNuevaReparacion.setBackground(new Color(152, 251, 152));
		BotonNuevaReparacion.setBounds(272, 11, 105, 45);
		panel_3.add(BotonNuevaReparacion);
		BotonNuevaReparacion.setForeground(Color.BLACK);
		BotonNuevaReparacion.setFont(new Font("Cambria", Font.BOLD, 14));

		BotonGenerarRegistro = new JButton("<html><center>GENERAR REGISTRO<html>");
		BotonGenerarRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonGenerarRegistro.setBackground(new Color(152, 251, 152));
		BotonGenerarRegistro.setBounds(10, 11, 105, 45);
		panel_3.add(BotonGenerarRegistro);
		BotonGenerarRegistro.setForeground(Color.BLACK);
		BotonGenerarRegistro.setFont(new Font("Cambria", Font.BOLD, 14));

		BotonGuardar = new JButton("<html><center>GUARDAR<html>");
		BotonGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonGuardar.setBackground(new Color(152, 251, 152));
		BotonGuardar.setBounds(125, 11, 105, 45);
		panel_3.add(BotonGuardar);
		BotonGuardar.setForeground(Color.BLACK);
		BotonGuardar.setFont(new Font("Cambria", Font.BOLD, 14));

		panel_2 = new JPanel();
		panel_2.setBounds(6, 473, 341, 66);
		panel.add(panel_2);
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setLayout(null);

		rdbtnMDP = new JRadioButton("MDP");
		rdbtnMDP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnMDP.setOpaque(false);
		rdbtnMDP.setBackground(SystemColor.activeCaption);
		rdbtnMDP.setFont(new Font("Cambria", Font.PLAIN, 14));
		rdbtnMDP.setBounds(130, 25, 80, 23);
		panel_2.add(rdbtnMDP);

		rdbtnBRC = new JRadioButton("BRC");
		rdbtnBRC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnBRC.setOpaque(false);
		rdbtnBRC.setBackground(SystemColor.activeCaption);
		rdbtnBRC.setFont(new Font("Cambria", Font.PLAIN, 14));
		rdbtnBRC.setBounds(235, 25, 80, 23);
		panel_2.add(rdbtnBRC);

		rdbtnCABA = new JRadioButton("CABA");
		rdbtnCABA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdbtnCABA.setOpaque(false);
		rdbtnCABA.setBackground(SystemColor.activeCaption);
		rdbtnCABA.setFont(new Font("Cambria", Font.PLAIN, 14));
		rdbtnCABA.setBounds(25, 25, 80, 23);
		panel_2.add(rdbtnCABA);
		GrupoEstadoFisico.add(rdbtnMDP);
		GrupoEstadoFisico.add(rdbtnBRC);
		GrupoEstadoFisico.add(rdbtnCABA);

		lblEstadoFisico = new JLabel("Estado Físico: ");
		lblEstadoFisico.setBounds(16, 4, 128, 14);
		panel_2.add(lblEstadoFisico);
		lblEstadoFisico.setFont(new Font("Cambria", Font.BOLD, 14));

		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBackground(SystemColor.activeCaption);
		panel_4.setBounds(457, 11, 317, 54);
		panel.add(panel_4);
		panel_4.setLayout(null);

		BotonVerificarIngresoAnterior = new JButton("<html><center>VERIFICAR INGRESO</html>");
		BotonVerificarIngresoAnterior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonVerificarIngresoAnterior.setBounds(10, 8, 145, 37);
		panel_4.add(BotonVerificarIngresoAnterior);
		BotonVerificarIngresoAnterior.setBackground(new Color(0, 191, 255));
		BotonVerificarIngresoAnterior.setForeground(Color.BLACK);
		BotonVerificarIngresoAnterior.setFont(new Font("Cambria", Font.BOLD, 12));

		btnaltaCliente = new JButton("<html><center>ALTA CLIENTE<html>");
		btnaltaCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnaltaCliente.setBounds(162, 8, 145, 37);
		panel_4.add(btnaltaCliente);
		btnaltaCliente.setForeground(Color.BLACK);
		btnaltaCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		btnaltaCliente.setBackground(new Color(0, 191, 255));

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

	public JRadioButton getRdbtnMDP() {
		return rdbtnMDP;
	}

	public void setRdbtnMDP(JRadioButton rdbtnMDQ) {
		this.rdbtnMDP = rdbtnMDQ;
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

	public JComboBox<?> getComboClientes() {
		return comboClientes;
	}

	public void setComboClientes(JComboBox<?> comboClientes) {
		this.comboClientes = comboClientes;
	}

	public JComboBox<?> getComboSucursal() {
		return comboSucursal;
	}

	public void setComboSucursal(JComboBox<?> comboSucursal) {
		this.comboSucursal = comboSucursal;
	}

	public JComboBox<?> getComboNombreEquipo() {
		return comboNombreEquipo;
	}

	public void setComboNombreEquipo(JComboBox<?> comboNombreEquipo) {
		this.comboNombreEquipo = comboNombreEquipo;
	}

	public JComboBox<?> getComboMarca() {
		return comboMarca;
	}

	public void setComboMarca(JComboBox<?> comboMarca) {
		this.comboMarca = comboMarca;
	}

	public JComboBox<?> getComboModelo() {
		return comboModelo;
	}

	public void setComboModelo(JComboBox<?> comboModelo) {
		this.comboModelo = comboModelo;
	}

	public JComboBox<?> getComboSerie() {
		return comboSerie;
	}

	public void setComboSerie(JComboBox<?> comboSerie) {
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


}
