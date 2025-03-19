package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.toedter.calendar.JTextFieldDateEditor;

import presentacion.controlador.ControladorReparacion;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JSeparator;

public class VentanaVerificarIngresoAnterior extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPorels;
	private JButton btnPorSerie;
	private JButton btnVerificar;
	@SuppressWarnings("unused")
	private JPanel panel;
	@SuppressWarnings("unused")
	private ControladorReparacion controladorRepacion;
	
	@SuppressWarnings("rawtypes")
	private JComboBox comboFiltroELS;
	@SuppressWarnings("rawtypes")
	private JComboBox comboSerie;

	
	private JPanel panel_2;
	private JTextField textELS;
	private JTextField textCliente;
	private JTextField textEquipo;
	private JTextField textMarca;
	private JTextField textModelo;
	private JTextField textSerie;
	private JTextFieldDateEditor textFabricacion;
	private JTextFieldDateEditor textIngresoAnterior;
	private JLabel lblSerie;
	private JLabel lblFabricacion;
	private JLabel lblAviso;
	private JTextField textAviso;
	private JLabel lblFechaAnterior;
	private JLabel lblPasarondias;
	private JTextField textPasaron;
	private JLabel lblNota;
	private JTextArea textNota;
	private JLabel lblCargarAnuevoELS;
	private JButton btnSI;
	private JButton btnNO;
	private JPanel panel_3;
	

	@SuppressWarnings({ "unused", "rawtypes" })
	public VentanaVerificarIngresoAnterior(ControladorReparacion controladorrepacion) 
	{
		super();
		setResizable(false);
		this.controladorRepacion = controladorrepacion;
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 589, 578);
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
		panel.setBounds(0, 0, 574, 539);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBounds(51, 66, 471, 83);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		
		btnPorels = new JButton("<html><center>POR ELS:</html>");
		btnPorels.setBounds(27, 45, 110, 23);
		panel_2.add(btnPorels);
		btnPorels.setBackground(new Color(204, 255, 204));
		btnPorels.setFont(new Font("Cambria", Font.BOLD, 12));
		
		btnPorSerie = new JButton("<html><center>POR N° SERIE:</html>");
		btnPorSerie.setBounds(27, 11, 110, 23);
		panel_2.add(btnPorSerie);
		btnPorSerie.setBackground(new Color(204, 255, 204));
		btnPorSerie.setFont(new Font("Cambria", Font.BOLD, 12));
		
		comboFiltroELS = new JComboBox();
		comboFiltroELS.setVisible(false);
		comboFiltroELS.setBounds(149, 46, 126, 23);
		panel_2.add(comboFiltroELS);
		comboFiltroELS.setEditable(true);
		
		comboSerie = new JComboBox();
		comboSerie.setVisible(false);
		comboSerie.setBounds(149, 12, 126, 23);
		panel_2.add(comboSerie);
		comboSerie.setEditable(true);
		
		btnVerificar = new JButton("<html><center>VERIFICAR</html>");
		btnVerificar.setFont(new Font("Cambria", Font.BOLD, 12));
		btnVerificar.setBackground(new Color(224, 255, 255));
		btnVerificar.setBounds(332, 30, 110, 23);
		panel_2.add(btnVerificar);
		
		JLabel lblClientes = new JLabel("VERIFICACIÓN DE INGRESO ANTERIOR");
		lblClientes.setFont(new Font("Cambria", Font.BOLD, 22));
		lblClientes.setBounds(79, 11, 415, 31);
		panel.add(lblClientes);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBounds(50, 187, 474, 269);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		textELS = new JTextField();
		textELS.setEditable(false);
		textELS.setFont(new Font("Cambria", Font.BOLD, 12));
		textELS.setBounds(124, 11, 86, 20);
		panel_1.add(textELS);
		textELS.setColumns(10);
		
		JLabel lblels = new JLabel("ELS:");
		lblels.setFont(new Font("Cambria", Font.BOLD, 12));
		lblels.setBounds(10, 11, 104, 20);
		panel_1.add(lblels);
		
		JLabel lblCliente = new JLabel("CLIENTE:\r\n");
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCliente.setBounds(10, 34, 104, 20);
		panel_1.add(lblCliente);
		
		JLabel lblEquipo = new JLabel("EQUIPO:");
		lblEquipo.setFont(new Font("Cambria", Font.BOLD, 12));
		lblEquipo.setBounds(10, 57, 104, 20);
		panel_1.add(lblEquipo);
		
		JLabel lblMarca = new JLabel("MARCA:");
		lblMarca.setFont(new Font("Cambria", Font.BOLD, 12));
		lblMarca.setBounds(10, 80, 104, 20);
		panel_1.add(lblMarca);
		
		JLabel lblModelo = new JLabel("MODELO:");
		lblModelo.setFont(new Font("Cambria", Font.BOLD, 12));
		lblModelo.setBounds(10, 102, 104, 20);
		panel_1.add(lblModelo);
		
		textCliente = new JTextField();
		textCliente.setEditable(false);
		textCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		textCliente.setColumns(10);
		textCliente.setBounds(124, 34, 340, 20);
		panel_1.add(textCliente);
		
		textEquipo = new JTextField();
		textEquipo.setEditable(false);
		textEquipo.setFont(new Font("Cambria", Font.BOLD, 12));
		textEquipo.setColumns(10);
		textEquipo.setBounds(124, 57, 340, 20);
		panel_1.add(textEquipo);
		
		textMarca = new JTextField();
		textMarca.setEditable(false);
		textMarca.setFont(new Font("Cambria", Font.BOLD, 12));
		textMarca.setColumns(10);
		textMarca.setBounds(124, 80, 340, 20);
		panel_1.add(textMarca);
		
		textModelo = new JTextField();
		textModelo.setEditable(false);
		textModelo.setFont(new Font("Cambria", Font.BOLD, 12));
		textModelo.setColumns(10);
		textModelo.setBounds(124, 102, 340, 20);
		panel_1.add(textModelo);
		
		textSerie = new JTextField();
		textSerie.setEditable(false);
		textSerie.setFont(new Font("Cambria", Font.BOLD, 12));
		textSerie.setColumns(10);
		textSerie.setBounds(124, 125, 340, 20);
		panel_1.add(textSerie);
		
		textFabricacion = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		textFabricacion.setEditable(false);
		textFabricacion.setHorizontalAlignment(SwingConstants.CENTER);
		textFabricacion.setColumns(10);
		textFabricacion.setBounds(124, 148, 86, 20);
		textFabricacion.setFont(new Font("Cambria", Font.BOLD, 12));
		panel_1.add(textFabricacion);
		
		
		
		textIngresoAnterior = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		textIngresoAnterior.setEditable(false);
		textIngresoAnterior.setHorizontalAlignment(SwingConstants.CENTER);
		textIngresoAnterior.setFont(new Font("Cambria", Font.BOLD, 12));
		textIngresoAnterior.setColumns(10);
		textIngresoAnterior.setBounds(195, 197, 86, 20);
		panel_1.add(textIngresoAnterior);
		
		lblSerie = new JLabel("N° SERIE:");
		lblSerie.setFont(new Font("Cambria", Font.BOLD, 12));
		lblSerie.setBounds(10, 125, 104, 20);
		panel_1.add(lblSerie);
		
		lblFabricacion = new JLabel("FECHA DE FABR.:");
		lblFabricacion.setFont(new Font("Cambria", Font.BOLD, 12));
		lblFabricacion.setBounds(10, 148, 104, 20);
		panel_1.add(lblFabricacion);
		
		lblAviso = new JLabel("AVISO:");
		lblAviso.setFont(new Font("Cambria", Font.BOLD, 12));
		lblAviso.setBounds(322, 11, 53, 20);
		panel_1.add(lblAviso);
		
		textAviso = new JTextField();
		textAviso.setEditable(false);
		textAviso.setFont(new Font("Cambria", Font.BOLD, 12));
		textAviso.setColumns(10);
		textAviso.setBounds(378, 11, 86, 20);
		panel_1.add(textAviso);
		
		lblFechaAnterior = new JLabel("FECHA DE INGRESO ANTERIOR:");
		lblFechaAnterior.setFont(new Font("Cambria", Font.BOLD, 12));
		lblFechaAnterior.setBounds(10, 197, 175, 20);
		panel_1.add(lblFechaAnterior);
		
		lblPasarondias = new JLabel("PASARON (DIAS):");
		lblPasarondias.setFont(new Font("Cambria", Font.BOLD, 12));
		lblPasarondias.setBounds(299, 197, 104, 20);
		panel_1.add(lblPasarondias);
		
		textPasaron = new JTextField();
		textPasaron.setEditable(false);
		textPasaron.setFont(new Font("Cambria", Font.BOLD, 12));
		textPasaron.setColumns(10);
		textPasaron.setBounds(411, 197, 53, 20);
		panel_1.add(textPasaron);
		
		lblNota = new JLabel("NOTA:");
		lblNota.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNota.setBounds(10, 224, 40, 20);
		panel_1.add(lblNota);
		
		textNota = new JTextArea();
		textNota.setForeground(new Color(0, 0, 204));
		textNota.setBackground(new Color(255, 255, 204));
		textNota.setMargin(new Insets(3, 3, 3, 3));
		textNota.setEditable(false);
		textNota.setLineWrap(true);
		textNota.setWrapStyleWord(true);
		textNota.setFont(new Font("Cambria", Font.BOLD, 10));
		textNota.setColumns(10);
		textNota.setBounds(124, 224, 340, 34);
		panel_1.add(textNota);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(30, 184, 413, 2);
		panel_1.add(separator);
		
		
		JLabel lblElEquipoEncontrado = new JLabel("EL EQUIPO ENCONTRADO ES EL SIGUIENTE:");
		lblElEquipoEncontrado.setBounds(51, 160, 319, 20);
		panel.add(lblElEquipoEncontrado);
		lblElEquipoEncontrado.setFont(new Font("Cambria", Font.BOLD, 16));
		
		panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_3.setBounds(51, 467, 471, 61);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		lblCargarAnuevoELS = new JLabel("¿CARGAR DATOS A UN NUEVO ELS?");
		lblCargarAnuevoELS.setBounds(106, 4, 259, 20);
		panel_3.add(lblCargarAnuevoELS);
		lblCargarAnuevoELS.setFont(new Font("Cambria", Font.BOLD, 16));
		
		btnSI = new JButton("<html><center>SI</html>");
		btnSI.setEnabled(false);
		btnSI.setBounds(177, 31, 43, 23);
		panel_3.add(btnSI);
		btnSI.setFont(new Font("Cambria", Font.BOLD, 12));
		btnSI.setBackground(new Color(204, 255, 204));
		
		btnNO = new JButton("<html><center>NO</html>");
		btnNO.setEnabled(false);
		btnNO.setBounds(258, 31, 43, 23);
		panel_3.add(btnNO);
		btnNO.setFont(new Font("Cambria", Font.BOLD, 12));
		btnNO.setBackground(new Color(250, 128, 114));
				
		
		
		this.setVisible(true);
	}
	
	
	public JButton getBtnSI() {
		return btnSI;
	}


	public void setBtnSI(JButton btnSI) {
		this.btnSI = btnSI;
	}


	public JButton getBtnNO() {
		return btnNO;
	}


	public void setBtnNO(JButton btnNO) {
		this.btnNO = btnNO;
	}


	public JTextField getTextAviso() {
		return textAviso;
	}


	public void setTextAviso(JTextField textAviso) {
		this.textAviso = textAviso;
	}


	public JTextArea getTextNota() {
		return textNota;
	}


	public void setTextNota(JTextArea textNota) {
		this.textNota = textNota;
	}


	public JTextField getTextELS() {
		return textELS;
	}


	public void setTextELS(JTextField textELS) {
		this.textELS = textELS;
	}


	public JTextField getTextCliente() {
		return textCliente;
	}


	public void setTextCliente(JTextField textCliente) {
		this.textCliente = textCliente;
	}


	public JTextField getTextEquipo() {
		return textEquipo;
	}


	public void setTextEquipo(JTextField textEquipo) {
		this.textEquipo = textEquipo;
	}


	public JTextField getTextMarca() {
		return textMarca;
	}


	public void setTextMarca(JTextField textMarca) {
		this.textMarca = textMarca;
	}


	public JTextField getTextModelo() {
		return textModelo;
	}


	public void setTextModelo(JTextField textModelo) {
		this.textModelo = textModelo;
	}


	public JTextField getTextSerie() {
		return textSerie;
	}


	public void setTextSerie(JTextField textSerie) {
		this.textSerie = textSerie;
	}



	public JTextField getTextPasaron() {
		return textPasaron;
	}


	public void setTextPasaron(JTextField textPasaron) {
		this.textPasaron = textPasaron;
	}


	@SuppressWarnings("rawtypes")
	public JComboBox getComboFiltroELS() {
		return comboFiltroELS;
	}

	@SuppressWarnings("rawtypes")
	public void setComboFiltroELS(JComboBox comboFiltroELS) {
		this.comboFiltroELS = comboFiltroELS;
	}


	@SuppressWarnings("rawtypes")
	public JComboBox getComboSerie() {
		return comboSerie;
	}


	@SuppressWarnings("rawtypes")
	public void setComboSerie(JComboBox comboSerie) {
		this.comboSerie = comboSerie;
	}


	public JButton getBtnPorels() {
		return btnPorels;
	}


	public void setBtnPorels(JButton btnPorels) {
		this.btnPorels = btnPorels;
	}


	public JButton getBtnPorSerie() {
		return btnPorSerie;
	}


	public void setBtnPorSerie(JButton btnPorSerie) {
		this.btnPorSerie = btnPorSerie;
	}


	public JButton getBtnVerificar() {
		return btnVerificar;
	}


	public void setBtnVerificar(JButton btnVerificar) {
		this.btnVerificar = btnVerificar;
	}


	public JTextFieldDateEditor getTextFabricacion() {
		return textFabricacion;
	}


	public void setTextFabricacion(JTextFieldDateEditor textFabricacion) {
		this.textFabricacion = textFabricacion;
	}
	
	public void setFechaFabr2(java.util.Date fechaFabr) {
		this.textFabricacion.setDate(fechaFabr);
	}

	public void setFechaIngresoAnterior(java.util.Date FechaIngresoAnterior) {
		this.textIngresoAnterior.setDate(FechaIngresoAnterior);
	}

	public JTextFieldDateEditor getTextFechaIngreso() {
		return textIngresoAnterior;
	}
}

