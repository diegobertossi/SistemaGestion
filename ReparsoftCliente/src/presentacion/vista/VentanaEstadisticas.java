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

public class VentanaEstadisticas extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@SuppressWarnings("unused")
	private ControladorReparacion controladorP;
	@SuppressWarnings("unused")
	private ControladorListados controlador;
	private JTextField textField_2;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;


	@SuppressWarnings("rawtypes")
	public VentanaEstadisticas(ControladorListados controlador) {
		super();
		//setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 893, 613);

		//this.setLocationRelativeTo(null);

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
		
		textField_2 = new JTextField();
		panelCentro.add(textField_2, BorderLayout.CENTER);
		textField_2.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel.setPreferredSize(new Dimension(500, 50));
		panelCentro.add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("FILTRO: ");
		lblNewLabel_1_3.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_1_3.setBounds(39, 6, 104, 14);
		lblNewLabel_1_3.setPreferredSize(new Dimension(50, 14));
		panel.add(lblNewLabel_1_3);
		
		JComboBox<?> comboBox = new JComboBox();
		comboBox.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox.setBounds(39, 23, 104, 22);
		panel.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("AÑO: ");
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_1.setBounds(182, 6, 104, 14);
		lblNewLabel_1.setPreferredSize(new Dimension(50, 14));
		panel.add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox_1.setBounds(182, 23, 104, 22);
		panel.add(comboBox_1);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox_4.setBounds(325, 23, 104, 22);
		panel.add(comboBox_4);
		
		JLabel lblNewLabel_1_2 = new JLabel("TÉCNICO: ");
		lblNewLabel_1_2.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_1_2.setBounds(325, 6, 104, 14);
		panel.add(lblNewLabel_1_2);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox_2.setBounds(468, 23, 104, 22);
		panel.add(comboBox_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("MES: ");
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(468, 6, 104, 14);
		lblNewLabel_1_1.setPreferredSize(new Dimension(50, 14));
		panel.add(lblNewLabel_1_1);
		
		JPanel panelDerecha = new JPanel();
		panelDerecha.setPreferredSize(new Dimension(250, 10));
		panelDerecha.setBorder(new LineBorder(new Color(0, 128, 128)));
		contentPane.add(panelDerecha, BorderLayout.EAST);
		panelDerecha.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panelDerecha.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("CONFIGURACIÓN");
		panel_1.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panelDerecha.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("AÑO");
		lblNewLabel_2.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(101, 11, 46, 14);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("INGRESOS TOTALES: ");
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblNewLabel_2_1.setBounds(11, 41, 147, 14);
		panel_2.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("REPARADOS");
		lblNewLabel_2_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2.setBounds(11, 122, 110, 14);
		panel_2.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("REPARADOS EN GTÍA");
		lblNewLabel_2_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3.setBounds(11, 154, 111, 14);
		panel_2.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_4 = new JLabel("SIN FALLA");
		lblNewLabel_2_4.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4.setBounds(11, 138, 111, 14);
		panel_2.add(lblNewLabel_2_4);
		
		textField = new JTextField();
		textField.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField.setBounds(187, 41, 52, 14);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("DIAGNÓSTICOS TOTALES: ");
		lblNewLabel_2_1_1.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblNewLabel_2_1_1.setBounds(11, 58, 166, 14);
		panel_2.add(lblNewLabel_2_1_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_1.setColumns(10);
		textField_1.setBounds(187, 58, 52, 14);
		panel_2.add(textField_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 83, 218, 2);
		panel_2.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(15, 86, 218, 2);
		panel_2.add(separator_1);
		
		JLabel lblNewLabel_2_4_1 = new JLabel("EN REPARACIÓN");
		lblNewLabel_2_4_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1.setBounds(11, 170, 110, 14);
		panel_2.add(lblNewLabel_2_4_1);
		
		JLabel lblNewLabel_2_4_1_1 = new JLabel("VENTAS");
		lblNewLabel_2_4_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1.setBounds(10, 186, 111, 14);
		panel_2.add(lblNewLabel_2_4_1_1);
		
		JLabel lblNewLabel_2_4_1_1_1 = new JLabel("SIN REPARACIÓN");
		lblNewLabel_2_4_1_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1_1.setBounds(10, 202, 111, 14);
		panel_2.add(lblNewLabel_2_4_1_1_1);
		
		JLabel lblNewLabel_2_5 = new JLabel("RESUMEN ANUAL");
		lblNewLabel_2_5.setFont(new Font("Cambria", Font.PLAIN, 14));
		lblNewLabel_2_5.setBounds(62, 97, 123, 14);
		panel_2.add(lblNewLabel_2_5);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_3.setColumns(10);
		textField_3.setBounds(187, 122, 52, 14);
		panel_2.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_4.setColumns(10);
		textField_4.setBounds(187, 138, 52, 14);
		panel_2.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_5.setColumns(10);
		textField_5.setBounds(187, 154, 52, 14);
		panel_2.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_6.setColumns(10);
		textField_6.setBounds(187, 170, 52, 14);
		panel_2.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_7.setColumns(10);
		textField_7.setBounds(187, 186, 52, 14);
		panel_2.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_8.setColumns(10);
		textField_8.setBounds(187, 202, 52, 14);
		panel_2.add(textField_8);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(11, 227, 218, 2);
		panel_2.add(separator_2);
		
		JLabel lblNewLabel_2_2_1 = new JLabel("REPARADOS/ACEPTADOS");
		lblNewLabel_2_2_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2_1.setBounds(11, 241, 166, 14);
		panel_2.add(lblNewLabel_2_2_1);
		
		JLabel lblNewLabel_2_4_2 = new JLabel("REPARADOS/NO ACEPTADOS");
		lblNewLabel_2_4_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_2.setBounds(11, 257, 166, 14);
		panel_2.add(lblNewLabel_2_4_2);
		
		JLabel lblNewLabel_2_3_1 = new JLabel("REPARADOS A LA ESPERA");
		lblNewLabel_2_3_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3_1.setBounds(11, 273, 166, 14);
		panel_2.add(lblNewLabel_2_3_1);
		
		textField_9 = new JTextField();
		textField_9.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_9.setColumns(10);
		textField_9.setBounds(187, 241, 52, 14);
		panel_2.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_10.setColumns(10);
		textField_10.setBounds(187, 257, 52, 14);
		panel_2.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setFont(new Font("Cambria", Font.PLAIN, 14));
		textField_11.setColumns(10);
		textField_11.setBounds(187, 273, 52, 14);
		panel_2.add(textField_11);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(15, 304, 218, 2);
		panel_2.add(separator_3);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(15, 307, 218, 2);
		panel_2.add(separator_1_1);

		
	

		this.setVisible(true);

	}
	}