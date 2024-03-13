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
import javax.swing.JButton;

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
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;

	@SuppressWarnings("rawtypes")
	public VentanaResumenMensualTecnico(ControladorListados controlador) {
		super();
		setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 888, 508);

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
		panel.setBorder(new LineBorder(new Color(0, 153, 153)));
		panel.setBounds(10, 87, 406, 174);
		panel_DatosDelMes.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("EQUIPOS REVISADOS:");
		lblNewLabel_1_1.setBounds(0, 5, 171, 23);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_1_2 = new JLabel("REPARADOS:");
		lblNewLabel_1_2.setBounds(0, 48, 171, 16);
		panel.add(lblNewLabel_1_2);
		lblNewLabel_1_2.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_2_1 = new JLabel("REPARADOS EN GTÍA:");
		lblNewLabel_1_2_1.setBounds(0, 69, 171, 16);
		panel.add(lblNewLabel_1_2_1);
		lblNewLabel_1_2_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_3_1 = new JLabel("SIN FALLA:");
		lblNewLabel_1_3_1.setBounds(0, 90, 171, 16);
		panel.add(lblNewLabel_1_3_1);
		lblNewLabel_1_3_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1 = new JLabel("EN REPARACIÓN:");
		lblNewLabel_1_4_1.setBounds(0, 111, 171, 16);
		panel.add(lblNewLabel_1_4_1);
		lblNewLabel_1_4_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1_1 = new JLabel("VENTAS: ");
		lblNewLabel_1_4_1_1.setBounds(0, 132, 171, 16);
		panel.add(lblNewLabel_1_4_1_1);
		lblNewLabel_1_4_1_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1_4_1_1_1 = new JLabel("SIN REPARACIÓN:");
		lblNewLabel_1_4_1_1_1.setBounds(0, 153, 171, 16);
		panel.add(lblNewLabel_1_4_1_1_1);
		lblNewLabel_1_4_1_1_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		textField = new JTextField();
		textField.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setOpaque(false);
		textField.setBounds(175, 7, 42, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setOpaque(false);
		textField_1.setColumns(10);
		textField_1.setBounds(175, 48, 42, 16);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setOpaque(false);
		textField_2.setColumns(10);
		textField_2.setBounds(175, 69, 42, 16);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setOpaque(false);
		textField_3.setColumns(10);
		textField_3.setBounds(175, 89, 42, 16);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setOpaque(false);
		textField_4.setColumns(10);
		textField_4.setBounds(175, 111, 42, 16);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setOpaque(false);
		textField_5.setColumns(10);
		textField_5.setBounds(175, 132, 42, 16);
		panel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setOpaque(false);
		textField_6.setColumns(10);
		textField_6.setBounds(175, 153, 42, 16);
		panel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setOpaque(false);
		textField_7.setColumns(10);
		textField_7.setBounds(253, 48, 46, 16);
		panel.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_8.setHorizontalAlignment(SwingConstants.CENTER);
		textField_8.setOpaque(false);
		textField_8.setColumns(10);
		textField_8.setBounds(300, 48, 56, 16);
		panel.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_9.setHorizontalAlignment(SwingConstants.CENTER);
		textField_9.setOpaque(false);
		textField_9.setColumns(10);
		textField_9.setBounds(356, 48, 46, 16);
		panel.add(textField_9);
		
		JLabel lblNewLabel_2_2 = new JLabel("DE LOS REPARADOS");
		lblNewLabel_2_2.setBounds(277, 9, 0, 14);
		panel.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setFont(new Font("Cambria", Font.PLAIN, 10));
		
		JLabel lblNewLabel_2 = new JLabel("ACEP");
		lblNewLabel_2.setBounds(253, 26, 46, 14);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_1 = new JLabel("NO ACEP");
		lblNewLabel_2_1.setBounds(300, 26, 56, 14);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_1_1 = new JLabel("ESP");
		lblNewLabel_2_1_1.setBounds(356, 26, 46, 14);
		panel.add(lblNewLabel_2_1_1);
		lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_2_1_1 = new JLabel("REPARADOS");
		lblNewLabel_2_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_2_2_1_1.setFont(new Font("Cambria", Font.PLAIN, 10));
		lblNewLabel_2_2_1_1.setBounds(260, 10, 137, 14);
		panel.add(lblNewLabel_2_2_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 153, 153)));
		panel_1.setOpaque(false);
		panel_1.setBounds(421, 87, 423, 174);
		panel_DatosDelMes.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2_3 = new JLabel("ACEP DEL MES");
		lblNewLabel_2_3.setBounds(7, 26, 123, 14);
		panel_1.add(lblNewLabel_2_3);
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_3_1 = new JLabel("FACTURACIÓN PESOS ");
		lblNewLabel_2_3_1.setBounds(134, 26, 124, 14);
		panel_1.add(lblNewLabel_2_3_1);
		lblNewLabel_2_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblNewLabel_2_3_2 = new JLabel("FACTURACIÓN DOLAR");
		lblNewLabel_2_3_2.setBounds(268, 26, 134, 14);
		panel_1.add(lblNewLabel_2_3_2);
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3_2.setFont(new Font("Cambria", Font.BOLD, 12));
		
		textField_10 = new JTextField();
		textField_10.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_10.setHorizontalAlignment(SwingConstants.CENTER);
		textField_10.setBounds(7, 48, 123, 16);
		panel_1.add(textField_10);
		textField_10.setOpaque(false);
		textField_10.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_11.setHorizontalAlignment(SwingConstants.CENTER);
		textField_11.setBounds(134, 48, 124, 16);
		panel_1.add(textField_11);
		textField_11.setOpaque(false);
		textField_11.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_12.setHorizontalAlignment(SwingConstants.CENTER);
		textField_12.setBounds(268, 48, 134, 16);
		panel_1.add(textField_12);
		textField_12.setOpaque(false);
		textField_12.setColumns(10);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("ACEPTADOS/REPARADOS");
		lblNewLabel_2_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2_1.setBounds(7, 9, 123, 14);
		panel_1.add(lblNewLabel_2_2_1);
		lblNewLabel_2_2_1.setFont(new Font("Cambria", Font.PLAIN, 10));
		
		JLabel lblNewLabel_3 = new JLabel("TOTAL FACTURACIÓN: ");
		lblNewLabel_3.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_3.setBounds(4, 149, 134, 14);
		panel_1.add(lblNewLabel_3);
		
		textField_13 = new JTextField();
		textField_13.setOpaque(false);
		textField_13.setFont(new Font("Cambria", Font.BOLD, 12));
		textField_13.setBounds(134, 147, 124, 20);
		panel_1.add(textField_13);
		textField_13.setColumns(10);
		
		textField_14 = new JTextField();
		textField_14.setOpaque(false);
		textField_14.setFont(new Font("Cambria", Font.BOLD, 12));
		textField_14.setColumns(10);
		textField_14.setBounds(268, 147, 124, 20);
		panel_1.add(textField_14);
		
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
		panel_Facturacion.setBounds(10, 383, 854, 83);
		contentPane.add(panel_Facturacion);
		panel_Facturacion.setLayout(null);
		
		JLabel lblNewLabel_3_1 = new JLabel("PORCENTAJE COMICIONES: ");
		lblNewLabel_3_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_3_1.setBounds(10, 16, 165, 14);
		panel_Facturacion.add(lblNewLabel_3_1);
		
		textField_15 = new JTextField();
		textField_15.setOpaque(false);
		textField_15.setHorizontalAlignment(SwingConstants.CENTER);
		textField_15.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_15.setColumns(10);
		textField_15.setBounds(185, 15, 42, 16);
		panel_Facturacion.add(textField_15);
		
		JButton btnNewButton = new JButton("<html><center>CALCULAR COMICIÓN</html>");
		btnNewButton.setFont(new Font("Cambria", Font.BOLD, 11));
		btnNewButton.setBounds(248, 6, 87, 35);
		panel_Facturacion.add(btnNewButton);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("TOTAL COMICIONES: ");
		lblNewLabel_3_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_3_1_1.setBounds(10, 58, 123, 14);
		panel_Facturacion.add(lblNewLabel_3_1_1);
		
		textField_16 = new JTextField();
		textField_16.setOpaque(false);
		textField_16.setHorizontalAlignment(SwingConstants.CENTER);
		textField_16.setFont(new Font("Cambria", Font.PLAIN, 12));
		textField_16.setColumns(10);
		textField_16.setBounds(143, 57, 124, 16);
		panel_Facturacion.add(textField_16);
		
		JButton btnmostrarResumenPara = new JButton("<html><center>MOSTRAR RESUMEN PARA EL TÉCNICO</html>");
		btnmostrarResumenPara.setFont(new Font("Cambria", Font.BOLD, 11));
		btnmostrarResumenPara.setBounds(446, 24, 158, 35);
		panel_Facturacion.add(btnmostrarResumenPara);
		
		JButton btnimprimirResumenPara = new JButton("<html><center>IMPRIMIR RESUMEN PARA EL TÉCNICO</html>");
		btnimprimirResumenPara.setFont(new Font("Cambria", Font.BOLD, 11));
		btnimprimirResumenPara.setBounds(614, 24, 158, 35);
		panel_Facturacion.add(btnimprimirResumenPara);
		
	

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