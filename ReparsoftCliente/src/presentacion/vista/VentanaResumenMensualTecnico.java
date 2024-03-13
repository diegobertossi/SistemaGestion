package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorListados;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;

public class VentanaResumenMensualTecnico extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private ControladorListados controlador;


	private JTextField textTecnicoAnio;
	private JLabel lblMes;
	@SuppressWarnings("rawtypes")
	private JComboBox comboMes;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

	@SuppressWarnings("rawtypes")
	public VentanaResumenMensualTecnico(ControladorListados controlador) {
		super();
		setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 888, 599);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_DatosDelMes = new JPanel();
		panel_DatosDelMes.setBackground(SystemColor.inactiveCaption);
		panel_DatosDelMes.setBorder(new LineBorder(new Color(51, 153, 153)));
		panel_DatosDelMes.setBounds(10, 100, 854, 272);
		contentPane.add(panel_DatosDelMes);
		panel_DatosDelMes.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MES");
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 35, 23);
		panel_DatosDelMes.add(lblNewLabel);
		
		comboMes = new JComboBox();
		comboMes.setOpaque(false);
		comboMes.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboMes.setBounds(55, 11, 110, 23);
		panel_DatosDelMes.add(comboMes);
		
		JLabel lblNewLabel_1 = new JLabel("Diagnósticos realizados en ");
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 41, 171, 23);
		panel_DatosDelMes.add(lblNewLabel_1);
		
		lblMes = new JLabel("");
		lblMes.setHorizontalAlignment(SwingConstants.LEFT);
		lblMes.setFont(new Font("Cambria", Font.BOLD, 14));
		lblMes.setBounds(183, 41, 80, 23);
		panel_DatosDelMes.add(lblMes);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(null);
		panel.setBounds(10, 101, 413, 160);
		panel_DatosDelMes.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("EQUIPOS REVISADOS:");
		lblNewLabel_1_1.setBounds(0, 5, 171, 23);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_1_2 = new JLabel("REPARADOS:");
		lblNewLabel_1_2.setBounds(0, 33, 171, 16);
		panel.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_2_1 = new JLabel("REPARADOS EN GTÍA:");
		lblNewLabel_1_2_1.setBounds(0, 54, 171, 16);
		panel.add(lblNewLabel_1_2_1);
		lblNewLabel_1_2_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_3_1 = new JLabel("SIN FALLA:");
		lblNewLabel_1_3_1.setBounds(0, 75, 171, 16);
		panel.add(lblNewLabel_1_3_1);
		lblNewLabel_1_3_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1 = new JLabel("EN REPARACIÓN:");
		lblNewLabel_1_4_1.setBounds(0, 96, 171, 16);
		panel.add(lblNewLabel_1_4_1);
		lblNewLabel_1_4_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("VENTAS: ");
		lblNewLabel_1_4_1_1.setBounds(0, 117, 171, 16);
		panel.add(lblNewLabel_1_4_1_1);
		lblNewLabel_1_4_1_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1_1_1 = new JLabel("SIN REPARACIÓN:");
		lblNewLabel_1_4_1_1_1.setBounds(0, 138, 171, 16);
		panel.add(lblNewLabel_1_4_1_1_1);
		lblNewLabel_1_4_1_1_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		textField = new JTextField();
		textField.setOpaque(false);
		textField.setBounds(175, 7, 42, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setOpaque(false);
		textField_1.setColumns(10);
		textField_1.setBounds(175, 33, 42, 16);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setOpaque(false);
		textField_2.setColumns(10);
		textField_2.setBounds(175, 54, 42, 16);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setOpaque(false);
		textField_3.setColumns(10);
		textField_3.setBounds(175, 74, 42, 16);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setOpaque(false);
		textField_4.setColumns(10);
		textField_4.setBounds(175, 96, 42, 16);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setOpaque(false);
		textField_5.setColumns(10);
		textField_5.setBounds(175, 117, 42, 16);
		panel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setOpaque(false);
		textField_6.setColumns(10);
		textField_6.setBounds(175, 138, 42, 16);
		panel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setOpaque(false);
		textField_7.setColumns(10);
		textField_7.setBounds(260, 33, 42, 16);
		panel.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setOpaque(false);
		textField_8.setColumns(10);
		textField_8.setBounds(307, 33, 42, 16);
		panel.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setOpaque(false);
		textField_9.setColumns(10);
		textField_9.setBounds(355, 33, 42, 16);
		panel.add(textField_9);
		
		JLabel lblNewLabel_2 = new JLabel("ACEP");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_2.setBounds(265, 85, 46, 14);
		panel_DatosDelMes.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("NO ACEP");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(311, 85, 56, 14);
		panel_DatosDelMes.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("ESP");
		lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_2_1_1.setBounds(363, 85, 46, 14);
		panel_DatosDelMes.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("DE LOS REPARADOS");
		lblNewLabel_2_2.setFont(new Font("Cambria", Font.PLAIN, 10));
		lblNewLabel_2_2.setBounds(289, 65, 96, 14);
		panel_DatosDelMes.add(lblNewLabel_2_2);
		
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
		panel_Facturacion.setBounds(10, 383, 854, 166);
		contentPane.add(panel_Facturacion);
		panel_Facturacion.setLayout(null);
		
	

		this.setVisible(true);

	}

	public JTextField getTextTecnicoAnio() {
		return textTecnicoAnio;
	}

	public void setTextTecnicoAnio(JTextField textTecnicoAnio) {
		this.textTecnicoAnio = textTecnicoAnio;
	}

	public JLabel getLblMes() {
		return lblMes;
	}

	public void setLblMes(JLabel lblMes) {
		this.lblMes = lblMes;
	}

	public JComboBox getComboMes() {
		return comboMes;
	}

	public void setComboMes(JComboBox comboMes) {
		this.comboMes = comboMes;
	}
}