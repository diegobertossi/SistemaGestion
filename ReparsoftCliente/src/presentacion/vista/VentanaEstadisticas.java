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
import javax.swing.SwingConstants;

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
	private JLabel lblFacTotalDolares;
	private JLabel lblFacTotalPesos;
	
	private JPanel panel_Datos;
	private JPanel panel;
	private JPanel panel_Ingresos;
	private JPanel panel_Diagnosticos;
	private JPanel panel_Facturacion;
	private JTextField textPorcentajeReparados;
	private JTextField textPorcentajeSinFalla;
	private JTextField textPorcentajeRepEnGtia;
	private JTextField textPorcentajeEnReparacion;
	private JTextField textPorcentajeVentas;
	private JTextField textPorcentajeSinReparacion;
	private JTextField textPorcentajeReparadosAceptados;
	private JTextField textPorcentajeReparadosNoAceptados;
	private JTextField textPorcentajeRepEspera;
	private JTextField textFacTotalPesos;
	private JTextField textFacTotalDolares;
	private JPanel panel_datosPorAnio;
	private JPanel panel_facturacionPorAnio;
	
	
	

	@SuppressWarnings("rawtypes")
	public VentanaEstadisticas(ControladorListados controlador) {
		super();
		//setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1279, 721);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(new Color(176, 196, 222));
		panelNorte.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128), 2), new LineBorder(new Color(0, 0, 0), 2)));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("ESTADÍSTICAS");
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 30));
		panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		panelNorte.add(lblNewLabel);
		
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128), 2), new LineBorder(new Color(0, 0, 0))));
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Filtros = new JPanel();
		panel_Filtros.setBackground(new Color(176, 196, 222));
		panel_Filtros.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Filtros.setPreferredSize(new Dimension(500, 50));
		panelCentro.add(panel_Filtros, BorderLayout.NORTH);
		panel_Filtros.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("FILTRO: ");
		lblNewLabel_1_3.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(78, 6, 104, 14);
		lblNewLabel_1_3.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblNewLabel_1_3);
		
		comboFiltro = new JComboBox();
		comboFiltro.setFont(new Font("Cambria", Font.BOLD, 14));
		comboFiltro.setBounds(78, 23, 166, 22);
		panel_Filtros.add(comboFiltro);
		
		lblAnio = new JLabel("AÑO: ");
		lblAnio.setVisible(false);
		lblAnio.setFont(new Font("Cambria", Font.BOLD, 14));
		lblAnio.setBounds(535, 6, 104, 14);
		lblAnio.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblAnio);
		
		comboAnio = new JComboBox();
		comboAnio.setVisible(false);
		comboAnio.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboAnio.setBounds(535, 23, 104, 22);
		panel_Filtros.add(comboAnio);
		
		comboTecnico = new JComboBox<Object>();
		comboTecnico.setVisible(false);
		comboTecnico.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboTecnico.setBounds(291, 23, 166, 22);
		panel_Filtros.add(comboTecnico);
		
		lblTecnico = new JLabel("TÉCNICO: ");
		lblTecnico.setVisible(false);
		lblTecnico.setFont(new Font("Cambria", Font.BOLD, 14));
		lblTecnico.setBounds(291, 6, 104, 14);
		panel_Filtros.add(lblTecnico);
		
		comboMes = new JComboBox();
		comboMes.setVisible(false);
		comboMes.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboMes.setBounds(717, 23, 104, 22);
		panel_Filtros.add(comboMes);
		
		lblMes = new JLabel("MES: ");
		lblMes.setVisible(false);
		lblMes.setFont(new Font("Cambria", Font.BOLD, 14));
		lblMes.setBounds(717, 6, 104, 14);
		lblMes.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblMes);
		
		comboCliente = new JComboBox();
		comboCliente.setVisible(false);
		comboCliente.setBounds(291, 23, 166, 22);
		panel_Filtros.add(comboCliente);
		
		panel = new JPanel();
		panel.setBorder(null);
		panelCentro.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_Ingresos = new JPanel();
		panel_Ingresos.setBackground(new Color(176, 196, 222));
		panel_Ingresos.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new LineBorder(new Color(0, 128, 128), 3)));
		panel.add(panel_Ingresos);
		panel_Ingresos.setLayout(new BorderLayout(0, 0));
		
		panel_Diagnosticos = new JPanel();
		panel_Diagnosticos.setBackground(new Color(176, 196, 222));
		panel_Diagnosticos.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new LineBorder(new Color(0, 128, 128), 3)));
		panel.add(panel_Diagnosticos);
		panel_Diagnosticos.setLayout(new BorderLayout(0, 0));
		
		panel_Facturacion = new JPanel();
		panel_Facturacion.setVisible(false);
		panel_Facturacion.setBackground(new Color(176, 196, 222));
		panel_Facturacion.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new LineBorder(new Color(0, 128, 128), 3)));
		panel.add(panel_Facturacion);
		panel_Facturacion.setLayout(new BorderLayout(0, 0));
		
		JPanel panelDerecha = new JPanel();
		panelDerecha.setBackground(new Color(176, 196, 222));
		panelDerecha.setPreferredSize(new Dimension(350, 10));
		panelDerecha.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128), 2), new LineBorder(new Color(0, 0, 0), 2)));
		contentPane.add(panelDerecha, BorderLayout.EAST);
		panelDerecha.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Derecha_Inferior = new JPanel();
		panel_Derecha_Inferior.setBackground(new Color(176, 196, 222));
		panel_Derecha_Inferior.setBorder(null);
		panelDerecha.add(panel_Derecha_Inferior, BorderLayout.SOUTH);
		panel_Derecha_Inferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 6));
		
		btnConfiguracion = new JButton("CONFIGURACIÓN");
		btnConfiguracion.setFont(new Font("Cambria", Font.BOLD, 16));
		panel_Derecha_Inferior.add(btnConfiguracion);
		
		panel_Datos = new JPanel();
		panel_Datos.setBackground(new Color(176, 196, 222));
		panel_Datos.setBorder(null);
		panel_Datos.setVisible(false);
		panelDerecha.add(panel_Datos, BorderLayout.CENTER);
		panel_Datos.setLayout(null);
		
		lblAnioDatos = new JLabel("");
		lblAnioDatos.setFont(new Font("Cambria", Font.BOLD, 20));
		lblAnioDatos.setBounds(133, 5, 75, 24);
		panel_Datos.add(lblAnioDatos);
		
		JLabel lblNewLabel_2_1 = new JLabel("INGRESOS TOTALES: ");
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.BOLD, 13));
		lblNewLabel_2_1.setBounds(11, 40, 147, 18);
		panel_Datos.add(lblNewLabel_2_1);
		
		panel_datosPorAnio = new JPanel();
		panel_datosPorAnio.setBorder(null);
		panel_datosPorAnio.setOpaque(false);
		panel_datosPorAnio.setBounds(4, 126, 333, 251);
		panel_Datos.add(panel_datosPorAnio);
		panel_datosPorAnio.setLayout(null);
		
		JLabel lblNewLabel_2_2 = new JLabel("REPARADOS");
		lblNewLabel_2_2.setBounds(10, 82, 110, 14);
		panel_datosPorAnio.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2_3 = new JLabel("REPARADOS EN GTÍA");
		lblNewLabel_2_3.setBounds(10, 116, 111, 14);
		panel_datosPorAnio.add(lblNewLabel_2_3);
		lblNewLabel_2_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2_4 = new JLabel("SIN FALLA");
		lblNewLabel_2_4.setBounds(10, 99, 111, 14);
		panel_datosPorAnio.add(lblNewLabel_2_4);
		lblNewLabel_2_4.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(3, 244, 332, 2);
		panel_datosPorAnio.add(separator_1);
		
		JLabel lblNewLabel_2_4_1 = new JLabel("EN REPARACIÓN");
		lblNewLabel_2_4_1.setBounds(10, 133, 110, 14);
		panel_datosPorAnio.add(lblNewLabel_2_4_1);
		lblNewLabel_2_4_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2_4_1_1 = new JLabel("VENTAS");
		lblNewLabel_2_4_1_1.setBounds(10, 150, 111, 14);
		panel_datosPorAnio.add(lblNewLabel_2_4_1_1);
		lblNewLabel_2_4_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2_4_1_1_1 = new JLabel("SIN REPARACIÓN");
		lblNewLabel_2_4_1_1_1.setBounds(10, 167, 111, 14);
		panel_datosPorAnio.add(lblNewLabel_2_4_1_1_1);
		lblNewLabel_2_4_1_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2_5 = new JLabel("RESUMEN ANUAL");
		lblNewLabel_2_5.setBounds(102, 10, 129, 19);
		panel_datosPorAnio.add(lblNewLabel_2_5);
		lblNewLabel_2_5.setFont(new Font("Cambria", Font.PLAIN, 16));
		
		JLabel lblNewLabel_2_2_1 = new JLabel("REPARADOS/ ACEPTADOS");
		lblNewLabel_2_2_1.setBounds(10, 190, 129, 14);
		panel_datosPorAnio.add(lblNewLabel_2_2_1);
		lblNewLabel_2_2_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2_4_2 = new JLabel("REPARADOS/ NO ACEPTADOS");
		lblNewLabel_2_4_2.setBounds(10, 207, 144, 14);
		panel_datosPorAnio.add(lblNewLabel_2_4_2);
		lblNewLabel_2_4_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2_3_1 = new JLabel("REPARADOS/ A LA ESPERA");
		lblNewLabel_2_3_1.setBounds(10, 224, 144, 14);
		panel_datosPorAnio.add(lblNewLabel_2_3_1);
		lblNewLabel_2_3_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		
		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setBounds(3, 242, 332, 2);
		panel_datosPorAnio.add(separator_2_1);
		
		textPorcentajeReparados = new JTextField();
		textPorcentajeReparados.setBounds(251, 82, 75, 16);
		panel_datosPorAnio.add(textPorcentajeReparados);
		textPorcentajeReparados.setBorder(null);
		textPorcentajeReparados.setOpaque(false);
		textPorcentajeReparados.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeReparados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeReparados.setEditable(false);
		textPorcentajeReparados.setColumns(10);
		
		textPorcentajeSinFalla = new JTextField();
		textPorcentajeSinFalla.setBounds(251, 99, 75, 16);
		panel_datosPorAnio.add(textPorcentajeSinFalla);
		textPorcentajeSinFalla.setBorder(null);
		textPorcentajeSinFalla.setOpaque(false);
		textPorcentajeSinFalla.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeSinFalla.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeSinFalla.setEditable(false);
		textPorcentajeSinFalla.setColumns(10);
		
		textPorcentajeRepEnGtia = new JTextField();
		textPorcentajeRepEnGtia.setBounds(251, 116, 75, 16);
		panel_datosPorAnio.add(textPorcentajeRepEnGtia);
		textPorcentajeRepEnGtia.setBorder(null);
		textPorcentajeRepEnGtia.setOpaque(false);
		textPorcentajeRepEnGtia.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeRepEnGtia.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeRepEnGtia.setEditable(false);
		textPorcentajeRepEnGtia.setColumns(10);
		
		textPorcentajeEnReparacion = new JTextField();
		textPorcentajeEnReparacion.setBounds(251, 133, 75, 16);
		panel_datosPorAnio.add(textPorcentajeEnReparacion);
		textPorcentajeEnReparacion.setBorder(null);
		textPorcentajeEnReparacion.setOpaque(false);
		textPorcentajeEnReparacion.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeEnReparacion.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeEnReparacion.setEditable(false);
		textPorcentajeEnReparacion.setColumns(10);
		
		textPorcentajeVentas = new JTextField();
		textPorcentajeVentas.setBounds(251, 150, 75, 16);
		panel_datosPorAnio.add(textPorcentajeVentas);
		textPorcentajeVentas.setBorder(null);
		textPorcentajeVentas.setOpaque(false);
		textPorcentajeVentas.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeVentas.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeVentas.setEditable(false);
		textPorcentajeVentas.setColumns(10);
		
		textPorcentajeSinReparacion = new JTextField();
		textPorcentajeSinReparacion.setBounds(251, 167, 75, 16);
		panel_datosPorAnio.add(textPorcentajeSinReparacion);
		textPorcentajeSinReparacion.setBorder(null);
		textPorcentajeSinReparacion.setOpaque(false);
		textPorcentajeSinReparacion.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeSinReparacion.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeSinReparacion.setEditable(false);
		textPorcentajeSinReparacion.setColumns(10);
		
		textPorcentajeReparadosAceptados = new JTextField();
		textPorcentajeReparadosAceptados.setBounds(251, 190, 75, 16);
		panel_datosPorAnio.add(textPorcentajeReparadosAceptados);
		textPorcentajeReparadosAceptados.setBorder(null);
		textPorcentajeReparadosAceptados.setOpaque(false);
		textPorcentajeReparadosAceptados.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeReparadosAceptados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeReparadosAceptados.setEditable(false);
		textPorcentajeReparadosAceptados.setColumns(10);
		
		textPorcentajeReparadosNoAceptados = new JTextField();
		textPorcentajeReparadosNoAceptados.setBounds(251, 207, 75, 16);
		panel_datosPorAnio.add(textPorcentajeReparadosNoAceptados);
		textPorcentajeReparadosNoAceptados.setBorder(null);
		textPorcentajeReparadosNoAceptados.setOpaque(false);
		textPorcentajeReparadosNoAceptados.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeReparadosNoAceptados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeReparadosNoAceptados.setEditable(false);
		textPorcentajeReparadosNoAceptados.setColumns(10);
		
		textPorcentajeRepEspera = new JTextField();
		textPorcentajeRepEspera.setBounds(251, 224, 75, 16);
		panel_datosPorAnio.add(textPorcentajeRepEspera);
		textPorcentajeRepEspera.setBorder(null);
		textPorcentajeRepEspera.setOpaque(false);
		textPorcentajeRepEspera.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeRepEspera.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeRepEspera.setEditable(false);
		textPorcentajeRepEspera.setColumns(10);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(0, 2, 332, 2);
		panel_datosPorAnio.add(separator_1_2);
		
		JSeparator separator_2_1_1 = new JSeparator();
		separator_2_1_1.setBounds(0, 0, 332, 2);
		panel_datosPorAnio.add(separator_2_1_1);
		
		JSeparator separator_2_1_2_1 = new JSeparator();
		separator_2_1_2_1.setBounds(3, 185, 332, 2);
		panel_datosPorAnio.add(separator_2_1_2_1);
		
		textReparados = new JTextField();
		textReparados.setBounds(164, 82, 75, 16);
		panel_datosPorAnio.add(textReparados);
		textReparados.setBorder(null);
		textReparados.setOpaque(false);
		textReparados.setHorizontalAlignment(SwingConstants.CENTER);
		textReparados.setEditable(false);
		textReparados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textReparados.setColumns(10);
		
		textSinFalla = new JTextField();
		textSinFalla.setBounds(164, 99, 75, 16);
		panel_datosPorAnio.add(textSinFalla);
		textSinFalla.setBorder(null);
		textSinFalla.setOpaque(false);
		textSinFalla.setHorizontalAlignment(SwingConstants.CENTER);
		textSinFalla.setEditable(false);
		textSinFalla.setFont(new Font("Cambria", Font.PLAIN, 13));
		textSinFalla.setColumns(10);
		
		textRepEnGtia = new JTextField();
		textRepEnGtia.setBounds(164, 116, 75, 16);
		panel_datosPorAnio.add(textRepEnGtia);
		textRepEnGtia.setBorder(null);
		textRepEnGtia.setOpaque(false);
		textRepEnGtia.setHorizontalAlignment(SwingConstants.CENTER);
		textRepEnGtia.setEditable(false);
		textRepEnGtia.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepEnGtia.setColumns(10);
		
		textEnReparacion = new JTextField();
		textEnReparacion.setBounds(164, 133, 75, 16);
		panel_datosPorAnio.add(textEnReparacion);
		textEnReparacion.setBorder(null);
		textEnReparacion.setOpaque(false);
		textEnReparacion.setHorizontalAlignment(SwingConstants.CENTER);
		textEnReparacion.setEditable(false);
		textEnReparacion.setFont(new Font("Cambria", Font.PLAIN, 13));
		textEnReparacion.setColumns(10);
		
		textVentas = new JTextField();
		textVentas.setBounds(164, 150, 75, 16);
		panel_datosPorAnio.add(textVentas);
		textVentas.setBorder(null);
		textVentas.setOpaque(false);
		textVentas.setHorizontalAlignment(SwingConstants.CENTER);
		textVentas.setEditable(false);
		textVentas.setFont(new Font("Cambria", Font.PLAIN, 13));
		textVentas.setColumns(10);
		
		textSinReparacion = new JTextField();
		textSinReparacion.setBounds(164, 167, 75, 16);
		panel_datosPorAnio.add(textSinReparacion);
		textSinReparacion.setBorder(null);
		textSinReparacion.setOpaque(false);
		textSinReparacion.setHorizontalAlignment(SwingConstants.CENTER);
		textSinReparacion.setEditable(false);
		textSinReparacion.setFont(new Font("Cambria", Font.PLAIN, 13));
		textSinReparacion.setColumns(10);
		
		textReparadosAceptados = new JTextField();
		textReparadosAceptados.setBounds(164, 190, 75, 16);
		panel_datosPorAnio.add(textReparadosAceptados);
		textReparadosAceptados.setBorder(null);
		textReparadosAceptados.setOpaque(false);
		textReparadosAceptados.setHorizontalAlignment(SwingConstants.CENTER);
		textReparadosAceptados.setEditable(false);
		textReparadosAceptados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textReparadosAceptados.setColumns(10);
		
		textReparadosNoAceptados = new JTextField();
		textReparadosNoAceptados.setBounds(164, 207, 75, 16);
		panel_datosPorAnio.add(textReparadosNoAceptados);
		textReparadosNoAceptados.setBorder(null);
		textReparadosNoAceptados.setOpaque(false);
		textReparadosNoAceptados.setHorizontalAlignment(SwingConstants.CENTER);
		textReparadosNoAceptados.setEditable(false);
		textReparadosNoAceptados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textReparadosNoAceptados.setColumns(10);
		
		textRepEspera = new JTextField();
		textRepEspera.setBounds(164, 224, 75, 16);
		panel_datosPorAnio.add(textRepEspera);
		textRepEspera.setBorder(null);
		textRepEspera.setOpaque(false);
		textRepEspera.setHorizontalAlignment(SwingConstants.CENTER);
		textRepEspera.setEditable(false);
		textRepEspera.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepEspera.setColumns(10);
		
		textIngresosTotales = new JTextField();
		textIngresosTotales.setBorder(null);
		textIngresosTotales.setOpaque(false);
		textIngresosTotales.setHorizontalAlignment(SwingConstants.RIGHT);
		textIngresosTotales.setEditable(false);
		textIngresosTotales.setFont(new Font("Cambria", Font.BOLD, 13));
		textIngresosTotales.setBounds(217, 40, 111, 18);
		panel_Datos.add(textIngresosTotales);
		textIngresosTotales.setColumns(10);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("DIAGNÓSTICOS TOTALES: ");
		lblNewLabel_2_1_1.setFont(new Font("Cambria", Font.BOLD, 13));
		lblNewLabel_2_1_1.setBounds(11, 59, 166, 18);
		panel_Datos.add(lblNewLabel_2_1_1);
		
		textDiagnosticosTotales = new JTextField();
		textDiagnosticosTotales.setBorder(null);
		textDiagnosticosTotales.setOpaque(false);
		textDiagnosticosTotales.setHorizontalAlignment(SwingConstants.RIGHT);
		textDiagnosticosTotales.setEditable(false);
		textDiagnosticosTotales.setFont(new Font("Cambria", Font.BOLD, 13));
		textDiagnosticosTotales.setColumns(10);
		textDiagnosticosTotales.setBounds(217, 59, 111, 18);
		panel_Datos.add(textDiagnosticosTotales);
		
		panel_facturacionPorAnio = new JPanel();
		panel_facturacionPorAnio.setVisible(false);
		panel_facturacionPorAnio.setOpaque(false);
		panel_facturacionPorAnio.setBorder(null);
		panel_facturacionPorAnio.setBounds(4, 386, 333, 156);
		panel_Datos.add(panel_facturacionPorAnio);
		panel_facturacionPorAnio.setLayout(null);
		
		btnFacturacionPorCliente = new JButton("<html><center>FACTURACIÓN POR CLIENTE</html>");
		btnFacturacionPorCliente.setBounds(113, 59, 106, 37);
		panel_facturacionPorAnio.add(btnFacturacionPorCliente);
		btnFacturacionPorCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(5, 546, 332, 2);
		panel_Datos.add(separator_1_1);
		
		JSeparator separator_2_1_2 = new JSeparator();
		separator_2_1_2.setBounds(5, 544, 332, 2);
		panel_Datos.add(separator_2_1_2);
		
		lblFacTotalPesos = new JLabel("FACTURACIÓN TOTAL (PESOS): ");
		lblFacTotalPesos.setVisible(false);
		lblFacTotalPesos.setFont(new Font("Cambria", Font.BOLD, 13));
		lblFacTotalPesos.setBounds(11, 78, 205, 18);
		panel_Datos.add(lblFacTotalPesos);
		
		textFacTotalPesos = new JTextField();
		textFacTotalPesos.setVisible(false);
		textFacTotalPesos.setBorder(null);
		textFacTotalPesos.setOpaque(false);
		textFacTotalPesos.setHorizontalAlignment(SwingConstants.RIGHT);
		textFacTotalPesos.setFont(new Font("Cambria", Font.BOLD, 13));
		textFacTotalPesos.setEditable(false);
		textFacTotalPesos.setColumns(10);
		textFacTotalPesos.setBounds(217, 78, 111, 18);
		panel_Datos.add(textFacTotalPesos);
		
		lblFacTotalDolares = new JLabel("FACTURACIÓN TOTAL (DÓLARES): ");
		lblFacTotalDolares.setVisible(false);
		lblFacTotalDolares.setFont(new Font("Cambria", Font.BOLD, 13));
		lblFacTotalDolares.setBounds(11, 97, 216, 18);
		panel_Datos.add(lblFacTotalDolares);
		
		textFacTotalDolares = new JTextField();
		textFacTotalDolares.setVisible(false);
		textFacTotalDolares.setBorder(null);
		textFacTotalDolares.setOpaque(false);
		textFacTotalDolares.setHorizontalAlignment(SwingConstants.RIGHT);
		textFacTotalDolares.setFont(new Font("Cambria", Font.BOLD, 13));
		textFacTotalDolares.setEditable(false);
		textFacTotalDolares.setColumns(10);
		textFacTotalDolares.setBounds(217, 97, 111, 18);
		panel_Datos.add(textFacTotalDolares);

		
	

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





	public JTextField getTextSinFalla() {
		return textSinFalla;
	}





	public void setTextSinFalla(JTextField textSinFalla) {
		this.textSinFalla = textSinFalla;
	}





	public JTextField getTextRepEnGtia() {
		return textRepEnGtia;
	}





	public void setTextRepEnGtia(JTextField textRepEnGtia) {
		this.textRepEnGtia = textRepEnGtia;
	}





	public JTextField getTextVentas() {
		return textVentas;
	}





	public void setTextVentas(JTextField textVentas) {
		this.textVentas = textVentas;
	}





	public JTextField getTextSinReparacion() {
		return textSinReparacion;
	}





	public void setTextSinReparacion(JTextField textSinReparacion) {
		this.textSinReparacion = textSinReparacion;
	}





	public JTextField getTextReparadosNoAceptados() {
		return textReparadosNoAceptados;
	}





	public void setTextReparadosNoAceptados(JTextField textReparadosNoAceptados) {
		this.textReparadosNoAceptados = textReparadosNoAceptados;
	}





	public JTextField getTextRepEspera() {
		return textRepEspera;
	}





	public void setTextRepEspera(JTextField textRepEspera) {
		this.textRepEspera = textRepEspera;
	}





	public JTextField getTextPorcentajeReparados() {
		return textPorcentajeReparados;
	}





	public void setTextPorcentajeReparados(JTextField textPorcentajeReparados) {
		this.textPorcentajeReparados = textPorcentajeReparados;
	}





	public JTextField getTextPorcentajeSinFalla() {
		return textPorcentajeSinFalla;
	}





	public void setTextPorcentajeSinFalla(JTextField textPorcentajeSinFalla) {
		this.textPorcentajeSinFalla = textPorcentajeSinFalla;
	}





	public JTextField getTextPorcentajeRepEnGtia() {
		return textPorcentajeRepEnGtia;
	}





	public void setTextPorcentajeRepEnGtia(JTextField textPorcentajeRepEnGtia) {
		this.textPorcentajeRepEnGtia = textPorcentajeRepEnGtia;
	}





	public JTextField getTextPorcentajeEnReparacion() {
		return textPorcentajeEnReparacion;
	}





	public void setTextPorcentajeEnReparacion(JTextField textPorcentajeEnReparacion) {
		this.textPorcentajeEnReparacion = textPorcentajeEnReparacion;
	}





	public JTextField getTextPorcentajeVentas() {
		return textPorcentajeVentas;
	}





	public void setTextPorcentajeVentas(JTextField textPorcentajeVentas) {
		this.textPorcentajeVentas = textPorcentajeVentas;
	}





	public JTextField getTextPorcentajeSinReparacion() {
		return textPorcentajeSinReparacion;
	}





	public void setTextPorcentajeSinReparacion(JTextField textPorcentajeSinReparacion) {
		this.textPorcentajeSinReparacion = textPorcentajeSinReparacion;
	}





	public JTextField getTextPorcentajeReparadosAceptados() {
		return textPorcentajeReparadosAceptados;
	}





	public void setTextPorcentajeReparadosAceptados(JTextField textPorcentajeReparadosAceptados) {
		this.textPorcentajeReparadosAceptados = textPorcentajeReparadosAceptados;
	}





	public JTextField getTextPorcentajeReparadosNoAceptados() {
		return textPorcentajeReparadosNoAceptados;
	}





	public void setTextPorcentajeReparadosNoAceptados(JTextField textPorcentajeReparadosNoAceptados) {
		this.textPorcentajeReparadosNoAceptados = textPorcentajeReparadosNoAceptados;
	}





	public JTextField getTextPorcentajeRepEspera() {
		return textPorcentajeRepEspera;
	}





	public void setTextPorcentajeRepEspera(JTextField textPorcentajeRepEspera) {
		this.textPorcentajeRepEspera = textPorcentajeRepEspera;
	}





	public JTextField getTextFacTotalPesos() {
		return textFacTotalPesos;
	}





	public void setTextFacTotalPesos(JTextField textFacTotalPesos) {
		this.textFacTotalPesos = textFacTotalPesos;
	}





	public JTextField getTextFacTotalDolares() {
		return textFacTotalDolares;
	}





	public void setTextFacTotalDolares(JTextField textFacTotalDolares) {
		this.textFacTotalDolares = textFacTotalDolares;
	}





	public JLabel getLblFacTotalDolares() {
		return lblFacTotalDolares;
	}





	public void setLblFacTotalDolares(JLabel lblFacTotalDolares) {
		this.lblFacTotalDolares = lblFacTotalDolares;
	}





	public JLabel getLblFacTotalPesos() {
		return lblFacTotalPesos;
	}





	public void setLblFacTotalPesos(JLabel lblFacTotalPesos) {
		this.lblFacTotalPesos = lblFacTotalPesos;
	}





	public JPanel getPanel_datosPorAnio() {
		return panel_datosPorAnio;
	}





	public void setPanel_datosPorAnio(JPanel panel_datosPorAnio) {
		this.panel_datosPorAnio = panel_datosPorAnio;
	}





	public JPanel getPanel_facturacionPorAnio() {
		return panel_facturacionPorAnio;
	}





	public void setPanel_facturacionPorAnio(JPanel panel_facturacionPorAnio) {
		this.panel_facturacionPorAnio = panel_facturacionPorAnio;
	}
	}