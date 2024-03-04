package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorListados;
import presentacion.controlador.ControladorReparacion;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;
import javax.swing.border.CompoundBorder;

public class VentanaEstadisticas extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@SuppressWarnings("unused")
	private ControladorReparacion controladorP;
	@SuppressWarnings("unused")
	private ControladorListados controlador;
	private JTextField textIngresosTotales;
	private JTextField textDiagnosticosTotales;
	private JTextField textReparados;
	private JTextField textSinFalla;
	private JTextField textRepEnGtia;
	private JTextField textEnReparacion;
	private JTextField textVentas;
	private JTextField textSinReparacion;
	private JTextField textReparadosAceptados;
	private JTextField textReparadosNoAceptados;
	private JTextField textRepEspera;
	
	private JButton btnConfiguracion;
	private JButton btnFacturacionPorCliente;

	@SuppressWarnings("rawtypes")
	private JComboBox comboFiltro;
	@SuppressWarnings("rawtypes")
	private JComboBox comboAnio;
	@SuppressWarnings("rawtypes")
	private JComboBox comboTecnico;
	@SuppressWarnings("rawtypes")
	private JComboBox comboCliente;
	@SuppressWarnings("rawtypes")
	private JComboBox comboMes;
	
	
	private JLabel lblAnio; 
	private JLabel lblTecnico;
	private JLabel lblMes;
	
	private JLabel lblAnioDatos;
	
	private JPanel panel_Datos;
	private JPanel panel;
	private JPanel panel_Ingresos;
	private JPanel panel_Diagnosticos;
	private JPanel panel_Facturacion;
	
	

	@SuppressWarnings("rawtypes")
	public VentanaEstadisticas(ControladorListados controlador) {
		super();
		//setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1235, 711);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new LineBorder(new Color(0, 128, 128)));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("ESTADÍSTICAS");
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 24));
		panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		panelNorte.add(lblNewLabel);
		
		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Filtros = new JPanel();
		panel_Filtros.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel_Filtros.setPreferredSize(new Dimension(500, 50));
		panelCentro.add(panel_Filtros, BorderLayout.NORTH);
		panel_Filtros.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("FILTRO: ");
		lblNewLabel_1_3.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_1_3.setBounds(102, 6, 104, 14);
		lblNewLabel_1_3.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblNewLabel_1_3);
		
		comboFiltro = new JComboBox();
		comboFiltro.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltro.setBounds(90, 23, 135, 22);
		panel_Filtros.add(comboFiltro);
		
		lblAnio = new JLabel("AÑO: ");
		lblAnio.setVisible(false);
		lblAnio.setFont(new Font("Cambria", Font.BOLD, 12));
		lblAnio.setBounds(571, 6, 104, 14);
		lblAnio.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblAnio);
		
		comboAnio = new JComboBox();
		comboAnio.setVisible(false);
		comboAnio.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboAnio.setBounds(571, 23, 104, 22);
		panel_Filtros.add(comboAnio);
		
		comboTecnico = new JComboBox<Object>();
		comboTecnico.setVisible(false);
		comboTecnico.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboTecnico.setBounds(315, 23, 166, 22);
		panel_Filtros.add(comboTecnico);
		
		lblTecnico = new JLabel("TÉCNICO: ");
		lblTecnico.setVisible(false);
		lblTecnico.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTecnico.setBounds(315, 6, 104, 14);
		panel_Filtros.add(lblTecnico);
		
		comboMes = new JComboBox();
		comboMes.setVisible(false);
		comboMes.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboMes.setBounds(765, 23, 104, 22);
		panel_Filtros.add(comboMes);
		
		lblMes = new JLabel("MES: ");
		lblMes.setVisible(false);
		lblMes.setFont(new Font("Cambria", Font.BOLD, 12));
		lblMes.setBounds(765, 6, 104, 14);
		lblMes.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblMes);
		
		comboCliente = new JComboBox();
		comboCliente.setVisible(false);
		comboCliente.setBounds(315, 23, 166, 22);
		panel_Filtros.add(comboCliente);
		
		panel = new JPanel();
		panelCentro.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_Ingresos = new JPanel();
		panel_Ingresos.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128), 2), new LineBorder(new Color(0, 0, 0))));
		panel.add(panel_Ingresos);
		panel_Ingresos.setLayout(new BorderLayout(0, 0));
		
		panel_Diagnosticos = new JPanel();
		panel_Diagnosticos.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128), 2), new LineBorder(new Color(0, 0, 0))));
		panel.add(panel_Diagnosticos);
		panel_Diagnosticos.setLayout(new BorderLayout(0, 0));
		
		panel_Facturacion = new JPanel();
		panel_Facturacion.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128), 2), new LineBorder(new Color(0, 0, 0))));
		panel.add(panel_Facturacion);
		panel_Facturacion.setLayout(new BorderLayout(0, 0));
		
		JPanel panelDerecha = new JPanel();
		panelDerecha.setPreferredSize(new Dimension(250, 10));
		panelDerecha.setBorder(new LineBorder(new Color(0, 128, 128)));
		contentPane.add(panelDerecha, BorderLayout.EAST);
		panelDerecha.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Derecha_Inferior = new JPanel();
		panelDerecha.add(panel_Derecha_Inferior, BorderLayout.SOUTH);
		panel_Derecha_Inferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnConfiguracion = new JButton("CONFIGURACIÓN");
		btnConfiguracion.setFont(new Font("Cambria", Font.PLAIN, 11));
		panel_Derecha_Inferior.add(btnConfiguracion);
		
		panel_Datos = new JPanel();
		panel_Datos.setVisible(false);
		panelDerecha.add(panel_Datos, BorderLayout.CENTER);
		panel_Datos.setLayout(null);
		
		lblAnioDatos = new JLabel("");
		lblAnioDatos.setFont(new Font("Cambria", Font.BOLD, 16));
		lblAnioDatos.setBounds(95, 11, 57, 14);
		panel_Datos.add(lblAnioDatos);
		
		JLabel lblNewLabel_2_1 = new JLabel("INGRESOS TOTALES: ");
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblNewLabel_2_1.setBounds(11, 41, 147, 14);
		panel_Datos.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("REPARADOS");
		lblNewLabel_2_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2.setBounds(11, 122, 110, 14);
		panel_Datos.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("REPARADOS EN GTÍA");
		lblNewLabel_2_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3.setBounds(11, 154, 111, 14);
		panel_Datos.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_4 = new JLabel("SIN FALLA");
		lblNewLabel_2_4.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4.setBounds(11, 138, 111, 14);
		panel_Datos.add(lblNewLabel_2_4);
		
		textIngresosTotales = new JTextField();
		textIngresosTotales.setEditable(false);
		textIngresosTotales.setFont(new Font("Cambria", Font.PLAIN, 14));
		textIngresosTotales.setBounds(187, 41, 52, 14);
		panel_Datos.add(textIngresosTotales);
		textIngresosTotales.setColumns(10);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("DIAGNÓSTICOS TOTALES: ");
		lblNewLabel_2_1_1.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblNewLabel_2_1_1.setBounds(11, 58, 166, 14);
		panel_Datos.add(lblNewLabel_2_1_1);
		
		textDiagnosticosTotales = new JTextField();
		textDiagnosticosTotales.setEditable(false);
		textDiagnosticosTotales.setFont(new Font("Cambria", Font.PLAIN, 14));
		textDiagnosticosTotales.setColumns(10);
		textDiagnosticosTotales.setBounds(187, 58, 52, 14);
		panel_Datos.add(textDiagnosticosTotales);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 83, 218, 2);
		panel_Datos.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(15, 86, 218, 2);
		panel_Datos.add(separator_1);
		
		JLabel lblNewLabel_2_4_1 = new JLabel("EN REPARACIÓN");
		lblNewLabel_2_4_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1.setBounds(11, 170, 110, 14);
		panel_Datos.add(lblNewLabel_2_4_1);
		
		JLabel lblNewLabel_2_4_1_1 = new JLabel("VENTAS");
		lblNewLabel_2_4_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1.setBounds(10, 186, 111, 14);
		panel_Datos.add(lblNewLabel_2_4_1_1);
		
		JLabel lblNewLabel_2_4_1_1_1 = new JLabel("SIN REPARACIÓN");
		lblNewLabel_2_4_1_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1_1.setBounds(10, 202, 111, 14);
		panel_Datos.add(lblNewLabel_2_4_1_1_1);
		
		JLabel lblNewLabel_2_5 = new JLabel("RESUMEN ANUAL");
		lblNewLabel_2_5.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblNewLabel_2_5.setBounds(62, 97, 123, 14);
		panel_Datos.add(lblNewLabel_2_5);
		
		textReparados = new JTextField();
		textReparados.setEditable(false);
		textReparados.setFont(new Font("Cambria", Font.PLAIN, 14));
		textReparados.setColumns(10);
		textReparados.setBounds(187, 122, 52, 14);
		panel_Datos.add(textReparados);
		
		textSinFalla = new JTextField();
		textSinFalla.setEditable(false);
		textSinFalla.setFont(new Font("Cambria", Font.PLAIN, 14));
		textSinFalla.setColumns(10);
		textSinFalla.setBounds(187, 138, 52, 14);
		panel_Datos.add(textSinFalla);
		
		textRepEnGtia = new JTextField();
		textRepEnGtia.setEditable(false);
		textRepEnGtia.setFont(new Font("Cambria", Font.PLAIN, 14));
		textRepEnGtia.setColumns(10);
		textRepEnGtia.setBounds(187, 154, 52, 14);
		panel_Datos.add(textRepEnGtia);
		
		textEnReparacion = new JTextField();
		textEnReparacion.setEditable(false);
		textEnReparacion.setFont(new Font("Cambria", Font.PLAIN, 14));
		textEnReparacion.setColumns(10);
		textEnReparacion.setBounds(187, 170, 52, 14);
		panel_Datos.add(textEnReparacion);
		
		textVentas = new JTextField();
		textVentas.setEditable(false);
		textVentas.setFont(new Font("Cambria", Font.PLAIN, 14));
		textVentas.setColumns(10);
		textVentas.setBounds(187, 186, 52, 14);
		panel_Datos.add(textVentas);
		
		textSinReparacion = new JTextField();
		textSinReparacion.setEditable(false);
		textSinReparacion.setFont(new Font("Cambria", Font.PLAIN, 14));
		textSinReparacion.setColumns(10);
		textSinReparacion.setBounds(187, 202, 52, 14);
		panel_Datos.add(textSinReparacion);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(11, 229, 218, 2);
		panel_Datos.add(separator_2);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("REPARADOS/ACEPTADOS");
		lblNewLabel_2_2_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2_1.setBounds(11, 241, 166, 14);
		panel_Datos.add(lblNewLabel_2_2_1);
		
		JLabel lblNewLabel_2_4_2 = new JLabel("REPARADOS/NO ACEPTADOS");
		lblNewLabel_2_4_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_2.setBounds(11, 257, 166, 14);
		panel_Datos.add(lblNewLabel_2_4_2);
		
		JLabel lblNewLabel_2_3_1 = new JLabel("REPARADOS A LA ESPERA");
		lblNewLabel_2_3_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3_1.setBounds(11, 273, 166, 14);
		panel_Datos.add(lblNewLabel_2_3_1);
		
		textReparadosAceptados = new JTextField();
		textReparadosAceptados.setEditable(false);
		textReparadosAceptados.setFont(new Font("Cambria", Font.PLAIN, 14));
		textReparadosAceptados.setColumns(10);
		textReparadosAceptados.setBounds(187, 241, 52, 14);
		panel_Datos.add(textReparadosAceptados);
		
		textReparadosNoAceptados = new JTextField();
		textReparadosNoAceptados.setEditable(false);
		textReparadosNoAceptados.setFont(new Font("Cambria", Font.PLAIN, 14));
		textReparadosNoAceptados.setColumns(10);
		textReparadosNoAceptados.setBounds(187, 257, 52, 14);
		panel_Datos.add(textReparadosNoAceptados);
		
		textRepEspera = new JTextField();
		textRepEspera.setEditable(false);
		textRepEspera.setFont(new Font("Cambria", Font.PLAIN, 14));
		textRepEspera.setColumns(10);
		textRepEspera.setBounds(187, 273, 52, 14);
		panel_Datos.add(textRepEspera);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(17, 548, 218, 2);
		panel_Datos.add(separator_3);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(17, 551, 218, 2);
		panel_Datos.add(separator_1_1);
		
		btnFacturacionPorCliente = new JButton("<html><center>FACTURACIÓN POR CLIENTE</html>");
		btnFacturacionPorCliente.setVisible(false);
		btnFacturacionPorCliente.setFont(new Font("Cambria", Font.PLAIN, 11));
		btnFacturacionPorCliente.setBounds(15, 333, 106, 37);
		panel_Datos.add(btnFacturacionPorCliente);
		
		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setBounds(11, 299, 218, 2);
		panel_Datos.add(separator_2_1);

		
	

		this.setVisible(true);

	}





	public JTextField getTextIngresosTotales() {
		return textIngresosTotales;
	}





	public void setTextIngresosTotales(JTextField textIngresosTotales) {
		this.textIngresosTotales = textIngresosTotales;
	}





	public JTextField getTextDiagnosticosTotales() {
		return textDiagnosticosTotales;
	}





	public void setTextDiagnosticosTotales(JTextField textDiagnosticosTotales) {
		this.textDiagnosticosTotales = textDiagnosticosTotales;
	}





	public JTextField getTextReparados() {
		return textReparados;
	}





	public void setTextReparados(JTextField textReparados) {
		this.textReparados = textReparados;
	}





	public JTextField getTextEnReparacion() {
		return textEnReparacion;
	}





	public void setTextEnReparacion(JTextField textEnReparacion) {
		this.textEnReparacion = textEnReparacion;
	}





	public JTextField getTextReparadosAceptados() {
		return textReparadosAceptados;
	}





	public void setTextReparadosAceptados(JTextField textReparadosAceptados) {
		this.textReparadosAceptados = textReparadosAceptados;
	}





	public JButton getBtnConfiguracion() {
		return btnConfiguracion;
	}





	public void setBtnConfiguracion(JButton btnConfiguracion) {
		this.btnConfiguracion = btnConfiguracion;
	}





	public JButton getBtnFacturacionPorCliente() {
		return btnFacturacionPorCliente;
	}





	public void setBtnFacturacionPorCliente(JButton btnFacturacionPorCliente) {
		this.btnFacturacionPorCliente = btnFacturacionPorCliente;
	}





	@SuppressWarnings("rawtypes")
	public JComboBox getComboFiltro() {
		return comboFiltro;
	}





	@SuppressWarnings("rawtypes")
	public void setComboFiltro(JComboBox comboFiltro) {
		this.comboFiltro = comboFiltro;
	}





	@SuppressWarnings("rawtypes")
	public JComboBox getComboAnio() {
		return comboAnio;
	}





	@SuppressWarnings("rawtypes")
	public void setComboAnio(JComboBox comboAnio) {
		this.comboAnio = comboAnio;
	}





	@SuppressWarnings("rawtypes")
	public JComboBox getComboTecnico() {
		return comboTecnico;
	}





	@SuppressWarnings("rawtypes")
	public void setComboTecnico(JComboBox comboTecnico) {
		this.comboTecnico = comboTecnico;
	}





	@SuppressWarnings("rawtypes")
	public JComboBox getComboMes() {
		return comboMes;
	}





	@SuppressWarnings("rawtypes")
	public void setComboMes(JComboBox comboMes) {
		this.comboMes = comboMes;
	}





	public JLabel getLblAnio() {
		return lblAnio;
	}





	public void setLblAnio(JLabel lblAnio) {
		this.lblAnio = lblAnio;
	}





	public JLabel getLblTecnico() {
		return lblTecnico;
	}





	public void setLblTecnico(JLabel lblTecnico) {
		this.lblTecnico = lblTecnico;
	}





	public JLabel getLblMes() {
		return lblMes;
	}





	public void setLblMes(JLabel lblMes) {
		this.lblMes = lblMes;
	}





	@SuppressWarnings("rawtypes")
	public JComboBox getComboCliente() {
		return comboCliente;
	}





	@SuppressWarnings("rawtypes")
	public void setComboCliente(JComboBox comboCliente) {
		this.comboCliente = comboCliente;
	}





	public JPanel getPanel_Datos() {
		return panel_Datos;
	}





	public void setPanel_Datos(JPanel panel_Datos) {
		this.panel_Datos = panel_Datos;
	}





	public JLabel getLblAnioDatos() {
		return lblAnioDatos;
	}





	public void setLblAnioDatos(JLabel lblAnioDatos) {
		this.lblAnioDatos = lblAnioDatos;
	}





	public JPanel getPanel_Ingresos() {
		return panel_Ingresos;
	}





	public void setPanel_Ingresos(JPanel panel_Ingresos) {
		this.panel_Ingresos = panel_Ingresos;
	}





	public JPanel getPanel_Diagnosticos() {
		return panel_Diagnosticos;
	}





	public void setPanel_Diagnosticos(JPanel panel_Diagnosticos) {
		this.panel_Diagnosticos = panel_Diagnosticos;
	}





	public JPanel getPanel_Facturacion() {
		return panel_Facturacion;
	}





	public void setPanel_Facturacion(JPanel panel_Facturacion) {
		this.panel_Facturacion = panel_Facturacion;
	}
	}