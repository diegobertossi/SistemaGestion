package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorListados;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;

public class VentanaResumenMensualTecnico extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private ControladorListados controlador;


	private JTextField textTecnicoAnio;
	@SuppressWarnings("rawtypes")
	private JComboBox comboMes;
	private JTextField textRevisados;
	private JTextField textReparados;
	private JTextField textRepGtia;
	private JTextField textSinFalla;
	private JTextField textEnRep;
	private JTextField textVentas;
	private JTextField textSinRep;
	private JTextField textRepAcep;
	private JTextField textRepNoAcep;
	private JTextField textRepEspera;
	private JTextField textAceptadosDelMes;
	private JTextField textFacturacionPesos;
	private JTextField textFacturacionDolar;
	private JTextField textPorcComisiones;
	private JTextField textTotalComicionesPesos;
	private JButton btnCalcularComiciones;
	private JButton btnMostrarResumen;
	
	@SuppressWarnings("rawtypes")
	public VentanaResumenMensualTecnico(ControladorListados controlador) {
		super();
		setResizable(false);
		this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 888, 443);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_DatosDelMes = new JPanel();
		panel_DatosDelMes.setBackground(SystemColor.inactiveCaption);
		panel_DatosDelMes.setBorder(new LineBorder(new Color(51, 153, 153)));
		panel_DatosDelMes.setBounds(10, 100, 854, 224);
		contentPane.add(panel_DatosDelMes);
		panel_DatosDelMes.setLayout(null);
		
		comboMes = new JComboBox();
		comboMes.setBackground(new Color(204, 204, 255));
		comboMes.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboMes.setBounds(184, 11, 110, 23);
		panel_DatosDelMes.add(comboMes);
		
		JLabel lblNewLabel_1 = new JLabel("Diagnósticos realizados en ");
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 11, 171, 23);
		panel_DatosDelMes.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new LineBorder(new Color(0, 153, 153)));
		panel.setBounds(10, 41, 406, 174);
		panel_DatosDelMes.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("EQUIPOS REVISADOS:");
		lblNewLabel_1_1.setBounds(7, 5, 137, 23);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_1_2 = new JLabel("REPARADOS:");
		lblNewLabel_1_2.setBounds(7, 48, 137, 16);
		panel.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_2_1 = new JLabel("REPARADOS EN GTÍA:");
		lblNewLabel_1_2_1.setBounds(7, 69, 137, 16);
		panel.add(lblNewLabel_1_2_1);
		lblNewLabel_1_2_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_3_1 = new JLabel("SIN FALLA:");
		lblNewLabel_1_3_1.setBounds(7, 90, 137, 16);
		panel.add(lblNewLabel_1_3_1);
		lblNewLabel_1_3_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1 = new JLabel("EN REPARACIÓN:");
		lblNewLabel_1_4_1.setBounds(7, 111, 137, 16);
		panel.add(lblNewLabel_1_4_1);
		lblNewLabel_1_4_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("VENTAS: ");
		lblNewLabel_1_4_1_1.setBounds(7, 132, 137, 16);
		panel.add(lblNewLabel_1_4_1_1);
		lblNewLabel_1_4_1_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1_1_1 = new JLabel("SIN REPARACIÓN:");
		lblNewLabel_1_4_1_1_1.setBounds(7, 153, 137, 16);
		panel.add(lblNewLabel_1_4_1_1_1);
		lblNewLabel_1_4_1_1_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		textRevisados = new JTextField();
		textRevisados.setBorder(null);
		textRevisados.setEditable(false);
		textRevisados.setFont(new Font("Cambria", Font.PLAIN, 12));
		textRevisados.setHorizontalAlignment(SwingConstants.CENTER);
		textRevisados.setOpaque(false);
		textRevisados.setBounds(147, 7, 42, 20);
		panel.add(textRevisados);
		textRevisados.setColumns(10);
		
		textReparados = new JTextField();
		textReparados.setBorder(null);
		textReparados.setEditable(false);
		textReparados.setFont(new Font("Cambria", Font.PLAIN, 12));
		textReparados.setHorizontalAlignment(SwingConstants.CENTER);
		textReparados.setOpaque(false);
		textReparados.setColumns(10);
		textReparados.setBounds(147, 48, 42, 16);
		panel.add(textReparados);
		
		textRepGtia = new JTextField();
		textRepGtia.setBorder(null);
		textRepGtia.setEditable(false);
		textRepGtia.setFont(new Font("Cambria", Font.PLAIN, 12));
		textRepGtia.setHorizontalAlignment(SwingConstants.CENTER);
		textRepGtia.setOpaque(false);
		textRepGtia.setColumns(10);
		textRepGtia.setBounds(147, 69, 42, 16);
		panel.add(textRepGtia);
		
		textSinFalla = new JTextField();
		textSinFalla.setBorder(null);
		textSinFalla.setEditable(false);
		textSinFalla.setFont(new Font("Cambria", Font.PLAIN, 12));
		textSinFalla.setHorizontalAlignment(SwingConstants.CENTER);
		textSinFalla.setOpaque(false);
		textSinFalla.setColumns(10);
		textSinFalla.setBounds(147, 89, 42, 16);
		panel.add(textSinFalla);
		
		textEnRep = new JTextField();
		textEnRep.setBorder(null);
		textEnRep.setEditable(false);
		textEnRep.setFont(new Font("Cambria", Font.PLAIN, 12));
		textEnRep.setHorizontalAlignment(SwingConstants.CENTER);
		textEnRep.setOpaque(false);
		textEnRep.setColumns(10);
		textEnRep.setBounds(147, 111, 42, 16);
		panel.add(textEnRep);
		
		textVentas = new JTextField();
		textVentas.setBorder(null);
		textVentas.setEditable(false);
		textVentas.setFont(new Font("Cambria", Font.PLAIN, 12));
		textVentas.setHorizontalAlignment(SwingConstants.CENTER);
		textVentas.setOpaque(false);
		textVentas.setColumns(10);
		textVentas.setBounds(147, 132, 42, 16);
		panel.add(textVentas);
		
		textSinRep = new JTextField();
		textSinRep.setBorder(null);
		textSinRep.setEditable(false);
		textSinRep.setFont(new Font("Cambria", Font.PLAIN, 12));
		textSinRep.setHorizontalAlignment(SwingConstants.CENTER);
		textSinRep.setOpaque(false);
		textSinRep.setColumns(10);
		textSinRep.setBounds(147, 153, 42, 16);
		panel.add(textSinRep);
		
		textRepAcep = new JTextField();
		textRepAcep.setBorder(null);
		textRepAcep.setEditable(false);
		textRepAcep.setFont(new Font("Cambria", Font.PLAIN, 12));
		textRepAcep.setHorizontalAlignment(SwingConstants.CENTER);
		textRepAcep.setOpaque(false);
		textRepAcep.setColumns(10);
		textRepAcep.setBounds(229, 48, 56, 16);
		panel.add(textRepAcep);
		
		textRepNoAcep = new JTextField();
		textRepNoAcep.setBorder(null);
		textRepNoAcep.setEditable(false);
		textRepNoAcep.setFont(new Font("Cambria", Font.PLAIN, 12));
		textRepNoAcep.setHorizontalAlignment(SwingConstants.CENTER);
		textRepNoAcep.setOpaque(false);
		textRepNoAcep.setColumns(10);
		textRepNoAcep.setBounds(286, 48, 56, 16);
		panel.add(textRepNoAcep);
		
		textRepEspera = new JTextField();
		textRepEspera.setBorder(null);
		textRepEspera.setEditable(false);
		textRepEspera.setFont(new Font("Cambria", Font.PLAIN, 12));
		textRepEspera.setHorizontalAlignment(SwingConstants.CENTER);
		textRepEspera.setOpaque(false);
		textRepEspera.setColumns(10);
		textRepEspera.setBounds(342, 48, 56, 16);
		panel.add(textRepEspera);
		
		JLabel lblNewLabel_2_2 = new JLabel("DE LOS REPARADOS");
		lblNewLabel_2_2.setBounds(277, 9, 0, 14);
		panel.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setFont(new Font("Cambria", Font.PLAIN, 10));
		
		JLabel lblNewLabel_2 = new JLabel("ACEP");
		lblNewLabel_2.setBounds(229, 26, 56, 14);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_1 = new JLabel("NO ACEP");
		lblNewLabel_2_1.setBounds(286, 26, 56, 14);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_1_1 = new JLabel("ESP");
		lblNewLabel_2_1_1.setBounds(342, 26, 56, 14);
		panel.add(lblNewLabel_2_1_1);
		lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_2_1_1 = new JLabel("REPARADOS");
		lblNewLabel_2_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1.setFont(new Font("Cambria", Font.PLAIN, 10));
		lblNewLabel_2_2_1_1.setBounds(246, 10, 137, 14);
		panel.add(lblNewLabel_2_2_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(203, 6, 3, 161);
		panel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(207, 6, 3, 161);
		panel.add(separator_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 153, 153)));
		panel_1.setOpaque(false);
		panel_1.setBounds(421, 41, 423, 174);
		panel_DatosDelMes.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2_3 = new JLabel("ACEP DEL MES");
		lblNewLabel_2_3.setBounds(10, 26, 123, 14);
		panel_1.add(lblNewLabel_2_3);
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_3_1 = new JLabel("FACTURACIÓN PESOS ");
		lblNewLabel_2_3_1.setBounds(143, 26, 124, 14);
		panel_1.add(lblNewLabel_2_3_1);
		lblNewLabel_2_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_3_2 = new JLabel("FACTURACIÓN DOLAR");
		lblNewLabel_2_3_2.setBounds(277, 26, 134, 14);
		panel_1.add(lblNewLabel_2_3_2);
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3_2.setFont(new Font("Cambria", Font.BOLD, 12));
		
		textAceptadosDelMes = new JTextField();
		textAceptadosDelMes.setBorder(null);
		textAceptadosDelMes.setEditable(false);
		textAceptadosDelMes.setFont(new Font("Cambria", Font.PLAIN, 12));
		textAceptadosDelMes.setHorizontalAlignment(SwingConstants.CENTER);
		textAceptadosDelMes.setBounds(10, 48, 123, 20);
		panel_1.add(textAceptadosDelMes);
		textAceptadosDelMes.setOpaque(false);
		textAceptadosDelMes.setColumns(10);
		
		textFacturacionPesos = new JTextField();
		textFacturacionPesos.setBorder(null);
		textFacturacionPesos.setEditable(false);
		textFacturacionPesos.setFont(new Font("Cambria", Font.PLAIN, 12));
		textFacturacionPesos.setHorizontalAlignment(SwingConstants.CENTER);
		textFacturacionPesos.setBounds(143, 48, 124, 20);
		panel_1.add(textFacturacionPesos);
		textFacturacionPesos.setOpaque(false);
		textFacturacionPesos.setColumns(10);
		
		textFacturacionDolar = new JTextField();
		textFacturacionDolar.setBorder(null);
		textFacturacionDolar.setEditable(false);
		textFacturacionDolar.setFont(new Font("Cambria", Font.PLAIN, 12));
		textFacturacionDolar.setHorizontalAlignment(SwingConstants.CENTER);
		textFacturacionDolar.setBounds(277, 48, 134, 20);
		panel_1.add(textFacturacionDolar);
		textFacturacionDolar.setOpaque(false);
		textFacturacionDolar.setColumns(10);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("ACEPTADOS/REPARADOS");
		lblNewLabel_2_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1.setBounds(7, 9, 123, 14);
		panel_1.add(lblNewLabel_2_2_1);
		lblNewLabel_2_2_1.setFont(new Font("Cambria", Font.PLAIN, 10));
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(10, 106, 403, 61);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3_1 = new JLabel("PORCENTAJE COMISIONES: ");
		lblNewLabel_3_1.setBounds(10, 11, 150, 18);
		panel_2.add(lblNewLabel_3_1);
		lblNewLabel_3_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		textPorcComisiones = new JTextField();
		textPorcComisiones.setBackground(new Color(204, 204, 255));
		textPorcComisiones.setBounds(170, 11, 90, 18);
		panel_2.add(textPorcComisiones);
		textPorcComisiones.setBorder(new LineBorder(new Color(0, 153, 153)));
		textPorcComisiones.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcComisiones.setFont(new Font("Cambria", Font.PLAIN, 12));
		textPorcComisiones.setColumns(10);
		
		btnCalcularComiciones = new JButton("<html><center>CALCULAR COMICIÓN</html>");
		btnCalcularComiciones.setBounds(285, 12, 98, 38);
		panel_2.add(btnCalcularComiciones);
		btnCalcularComiciones.setFont(new Font("Cambria", Font.BOLD, 11));
		
		JLabel lblNewLabel_3_1_1 = new JLabel("TOTAL COMISIONES: ");
		lblNewLabel_3_1_1.setBounds(10, 33, 123, 18);
		panel_2.add(lblNewLabel_3_1_1);
		lblNewLabel_3_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		textTotalComicionesPesos = new JTextField();
		textTotalComicionesPesos.setBackground(new Color(204, 204, 255));
		textTotalComicionesPesos.setBounds(170, 33, 90, 18);
		panel_2.add(textTotalComicionesPesos);
		textTotalComicionesPesos.setBorder(new LineBorder(new Color(0, 153, 153)));
		textTotalComicionesPesos.setEditable(false);
		textTotalComicionesPesos.setHorizontalAlignment(SwingConstants.CENTER);
		textTotalComicionesPesos.setFont(new Font("Cambria", Font.BOLD, 12));
		textTotalComicionesPesos.setColumns(10);
		
		JPanel panel_NombreTecnico = new JPanel();
		panel_NombreTecnico.setOpaque(false);
		panel_NombreTecnico.setBorder(new LineBorder(new Color(51, 153, 153)));
		panel_NombreTecnico.setBounds(10, 6, 854, 83);
		contentPane.add(panel_NombreTecnico);
		panel_NombreTecnico.setLayout(null);

		JLabel lblPresupuesto = new JLabel("RESUMEN MENSUAL");
		lblPresupuesto.setBounds(313, 5, 227, 34);
		panel_NombreTecnico.add(lblPresupuesto);
		lblPresupuesto.setBackground(SystemColor.activeCaption);
		lblPresupuesto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPresupuesto.setForeground(new Color(0, 0, 139));
		lblPresupuesto.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPresupuesto.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPresupuesto.setBorder(null);
		lblPresupuesto.setFont(new Font("Cambria", Font.BOLD, 24));
		
		textTecnicoAnio = new JTextField();
		textTecnicoAnio.setHorizontalAlignment(SwingConstants.CENTER);
		textTecnicoAnio.setForeground(new Color(0, 0, 139));
		textTecnicoAnio.setFont(new Font("Cambria", Font.BOLD, 24));
		textTecnicoAnio.setEditable(false);
		textTecnicoAnio.setBounds(161, 40, 532, 34);
		panel_NombreTecnico.add(textTecnicoAnio);
		textTecnicoAnio.setBorder(null);
		textTecnicoAnio.setOpaque(false);
		textTecnicoAnio.setColumns(10);

				
		JPanel panel_Facturacion = new JPanel();
		panel_Facturacion.setBackground(SystemColor.inactiveCaption);
		panel_Facturacion.setBorder(new LineBorder(new Color(0, 153, 153)));
		panel_Facturacion.setBounds(10, 335, 854, 64);
		contentPane.add(panel_Facturacion);
		panel_Facturacion.setLayout(null);
		
		btnMostrarResumen = new JButton("<html><center>RESUMEN PARA EL TÉCNICO</html>");
		btnMostrarResumen.setFont(new Font("Cambria", Font.BOLD, 11));
		btnMostrarResumen.setBounds(291, 12, 272, 39);
		panel_Facturacion.add(btnMostrarResumen);
		
	

		this.setVisible(true);

	}

	public JTextField getTextTecnicoAnio() {
		return textTecnicoAnio;
	}

	public void setTextTecnicoAnio(JTextField textTecnicoAnio) {
		this.textTecnicoAnio = textTecnicoAnio;
	}


	@SuppressWarnings("rawtypes")
	public JComboBox getComboMes() {
		return comboMes;
	}

	@SuppressWarnings("rawtypes")
	public void setComboMes(JComboBox comboMes) {
		this.comboMes = comboMes;
	}

	public JTextField getTextRevisados() {
		return textRevisados;
	}

	public void setTextRevisados(JTextField textRevisados) {
		this.textRevisados = textRevisados;
	}

	public JTextField getTextReparados() {
		return textReparados;
	}

	public void setTextReparados(JTextField textReparados) {
		this.textReparados = textReparados;
	}

	public JTextField getTextSinFalla() {
		return textSinFalla;
	}

	public void setTextSinFalla(JTextField textSinFalla) {
		this.textSinFalla = textSinFalla;
	}

	public JTextField getTextEnRep() {
		return textEnRep;
	}

	public void setTextEnRep(JTextField textEnRep) {
		this.textEnRep = textEnRep;
	}

	public JTextField getTextVentas() {
		return textVentas;
	}

	public void setTextVentas(JTextField textVentas) {
		this.textVentas = textVentas;
	}

	public JTextField getTextSinRep() {
		return textSinRep;
	}

	public void setTextSinRep(JTextField textSinRep) {
		this.textSinRep = textSinRep;
	}

	public JTextField getTextRepAcep() {
		return textRepAcep;
	}

	public void setTextRepAcep(JTextField textRepAcep) {
		this.textRepAcep = textRepAcep;
	}

	public JTextField getTextRepNoAcep() {
		return textRepNoAcep;
	}

	public void setTextRepNoAcep(JTextField textRepNoAcep) {
		this.textRepNoAcep = textRepNoAcep;
	}

	public JTextField getTextRepEspera() {
		return textRepEspera;
	}

	public void setTextRepEspera(JTextField textRepEspera) {
		this.textRepEspera = textRepEspera;
	}

	public JTextField getTextAceptadosDelMes() {
		return textAceptadosDelMes;
	}

	public void setTextAceptadosDelMes(JTextField textAceptadosDelMes) {
		this.textAceptadosDelMes = textAceptadosDelMes;
	}

	public JTextField getTextFacturacionPesos() {
		return textFacturacionPesos;
	}

	public void setTextFacturacionPesos(JTextField textFacturacionPesos) {
		this.textFacturacionPesos = textFacturacionPesos;
	}

	public JTextField getTextFacturacionDolar() {
		return textFacturacionDolar;
	}

	public void setTextFacturacionDolar(JTextField textFacturacionDolar) {
		this.textFacturacionDolar = textFacturacionDolar;
	}

	public JTextField getTextPorcComisiones() {
		return textPorcComisiones;
	}

	public void setTextPorcComisiones(JTextField textPorcComisiones) {
		this.textPorcComisiones = textPorcComisiones;
	}

	public JTextField getTextTotalComisionesPesos() {
		return textTotalComicionesPesos;
	}

	public void setTextTotalComisionesPesos(JTextField textTotalComicionesPesos) {
		this.textTotalComicionesPesos = textTotalComicionesPesos;
	}

	public JButton getBtnCalcularComisiones() {
		return btnCalcularComiciones;
	}

	public void setBtnCalcularComisiones(JButton btnCalcularComiciones) {
		this.btnCalcularComiciones = btnCalcularComiciones;
	}

	public JButton getBtnMostrarResumen() {
		return btnMostrarResumen;
	}

	public void setBtnMostrarResumen(JButton btnMostrarResumen) {
		this.btnMostrarResumen = btnMostrarResumen;
	}


	public JTextField getTextRepGtia() {
		return textRepGtia;
	}

	public void setTextRepGtia(JTextField textRepGtia) {
		this.textRepGtia = textRepGtia;
	}


}