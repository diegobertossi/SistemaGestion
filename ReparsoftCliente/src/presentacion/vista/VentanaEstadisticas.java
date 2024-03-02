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

public class VentanaEstadisticas extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@SuppressWarnings("unused")
	private ControladorReparacion controladorP;
	@SuppressWarnings("unused")
	private ControladorListados controlador;
	private JTextField textField_2;


	@SuppressWarnings("rawtypes")
	public VentanaEstadisticas(ControladorListados controlador) {
		super();
		//setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 888, 536);

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
		panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));
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
		lblNewLabel_1_3.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNewLabel_1_3.setBounds(11, 6, 104, 14);
		lblNewLabel_1_3.setPreferredSize(new Dimension(50, 14));
		panel.add(lblNewLabel_1_3);
		
		JComboBox<?> comboBox = new JComboBox();
		comboBox.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox.setBounds(11, 23, 104, 22);
		panel.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("AÑO: ");
		lblNewLabel_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(176, 6, 104, 14);
		lblNewLabel_1.setPreferredSize(new Dimension(50, 14));
		panel.add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox_1.setBounds(176, 23, 104, 22);
		panel.add(comboBox_1);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox_4.setBounds(341, 23, 104, 22);
		panel.add(comboBox_4);
		
		JLabel lblNewLabel_1_2 = new JLabel("TÉCNICO: ");
		lblNewLabel_1_2.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(341, 6, 104, 14);
		panel.add(lblNewLabel_1_2);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboBox_2.setBounds(506, 23, 104, 22);
		panel.add(comboBox_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("MES: ");
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(506, 6, 104, 14);
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
		
		JButton btnNewButton = new JButton("New button");
		panel_1.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panelDerecha.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(10, 41, 46, 14);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("New label");
		lblNewLabel_2_1.setBounds(10, 66, 46, 14);
		panel_2.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("New label");
		lblNewLabel_2_2.setBounds(10, 91, 46, 14);
		panel_2.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("New label");
		lblNewLabel_2_3.setBounds(10, 116, 46, 14);
		panel_2.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_4 = new JLabel("New label");
		lblNewLabel_2_4.setBounds(10, 141, 46, 14);
		panel_2.add(lblNewLabel_2_4);

		
	

		this.setVisible(true);

	}
	}