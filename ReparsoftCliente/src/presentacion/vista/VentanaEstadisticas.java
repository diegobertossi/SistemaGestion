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
import java.awt.CardLayout;

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
	private JButton btnResumenMensualTecnico;

	@SuppressWarnings("rawtypes")
	private JComboBox comboFiltro;
	@SuppressWarnings("rawtypes")
	private JComboBox comboAnio;
	@SuppressWarnings("rawtypes")
	private JComboBox comboTecnico;
	@SuppressWarnings("rawtypes")
	private JComboBox comboCliente;
	
	
	private JLabel lblAnio; 
	private JLabel lblTecnico;
	private JLabel lblAnioDatos;
	private JLabel lblFacTotalDolares;
	private JLabel lblFacTotalPesos;
	
	private JPanel panel_Datos;
	private JPanel panel;
	private JPanel panel_Ingresos;
	private JPanel panel_Diagnosticos;
	private JPanel panel_Facturacion;
	private JPanel panel_datosPorAnio;
	private JPanel panel_facturacionPorAnio;
	private JPanel panel_datosPorCliente;
	
	
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

	private JTextField textPorcRepPorCliente;
	private JTextField textPorcSinFallaPorCliente;
	private JTextField textPorcRepEnGtiaPorCliente;
	private JTextField textPorcEnRepPorCliente;
	private JTextField textPorcVentasPorCliente;
	private JTextField textPorcSinRepPorCliente;
	private JTextField textPorcRepAcepPorCliente;
	private JTextField textPorcRepNoAcepPorCliente;
	private JTextField textPorcRepEsperaPorCliente;
	private JTextField textReparadosPorCliente;
	private JTextField textSinFallaPorCliente;
	private JTextField textRepEnGtiaPorCliente;
	private JTextField textEnRepPorCLiente;
	private JTextField textVentasPorCliente;
	private JTextField textSinRepPorCliente;
	private JTextField textRepAcepPorCliente;
	private JTextField textRepNoAcepPorCliente;
	private JTextField textRepEsperaPorCliente;
	private JTextField textIngresosPorCliente;
	private JTextField textPorcIngresosPorCliente;
	private JPanel panel_facturacionPorCliente;
	private JLabel lblFacturacionClientePesos;
	private JLabel lblFacturacionClienteDolar;
	private JTextField textFactClientePesos;
	private JTextField textFactClienteDolar;
	private JTextField textNombreCliente;
	private JPanel panel_datosPorTecnico;
	private JLabel lblNewLabel_2_2_3;
	private JLabel lblNewLabel_2_3_3;
	private JLabel lblNewLabel_2_4_4;
	private JSeparator separator_1_4;
	private JLabel lblNewLabel_2_4_1_3;
	private JLabel lblNewLabel_2_4_1_1_3;
	private JLabel lblNewLabel_2_4_1_1_1_2;
	private JLabel lblNewLabel_2_5_2;
	private JLabel lblNewLabel_2_2_1_2;
	private JLabel lblNewLabel_2_4_2_2;
	private JLabel lblNewLabel_2_3_1_2;
	private JSeparator separator_2_1_4;
	private JTextField textPorcReparadosXTecnico;
	private JTextField textPorcSinFallasXtecnico;
	private JTextField textPorcRepGtiaXtecnico;
	private JTextField textPorcEnRepXtecnico;
	private JTextField textPorcVentasXtecnico;
	private JTextField textPorcSinRepXtecnico;
	private JTextField textPorcRepAcepXtecnico;
	private JTextField textPorcRepNoAcepXtecnico;
	private JTextField textPorcRepEsperaXtecnico;
	private JSeparator separator_1_2_2;
	private JSeparator separator_2_1_1_2;
	private JSeparator separator_2_1_2_1_2;
	private JTextField textReparadosXTecnico;
	private JTextField textSinFallasXtecnico;
	private JTextField textRepGtiaXtecnico;
	private JTextField textEnRepXtecnico;
	private JTextField textVentasXtecnico;
	private JTextField textSinRepXtecnico;
	private JTextField textRepAcepXtecnico;
	private JTextField textRepNoAcepXtecnico;
	private JTextField textRepEsperaXtecnico;
	private JLabel lblEquiposRevidados;
	private JTextField textTotalRevisados;
	private JTextField textPorcentajeTotalRevisado;
	private JTextField textNombreTecnico;
	private JPanel panel_facturacionPorTecnico;
	private JLabel lblFacturacionTecnicoPesos;
	private JLabel lblFacturacionTecnicoDolar;
	private JTextField textFacturacionTecnicoPesos;
	private JTextField textFacturacionTecnicoDolares;
	private JTextField textPorcFacturacionPesoCliente;
	private JTextField textPorcFacturacionDolarCliente;
	private JTextField textPorcFacturacionTecnicoDolar;
	private JTextField textPorcFacturacionTecnicoPesos;
	
	
	

	@SuppressWarnings("rawtypes")
	public VentanaEstadisticas(ControladorListados controlador) {
		super();
		//setResizable(false);
		this.controlador = controlador;
		
		int x = 1279;
		int y = 721;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, x, y);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(new Color(176, 196, 222));
		panelNorte.setBorder(new LineBorder(new Color(0, 128, 128)));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("ESTADÍSTICAS");
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 30));
		panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		panelNorte.add(lblNewLabel);
		
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(null);
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Filtros = new JPanel();
		panel_Filtros.setBackground(new Color(176, 196, 222));
		panel_Filtros.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel_Filtros.setPreferredSize(new Dimension(500, 50));
		panelCentro.add(panel_Filtros, BorderLayout.NORTH);
		panel_Filtros.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("FILTRO: ");
		lblNewLabel_1_3.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(115, 6, 104, 14);
		lblNewLabel_1_3.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblNewLabel_1_3);
		
		comboFiltro = new JComboBox();
		comboFiltro.setFont(new Font("Cambria", Font.BOLD, 14));
		comboFiltro.setBounds(115, 23, 166, 22);
		panel_Filtros.add(comboFiltro);
		
		lblAnio = new JLabel("AÑO: ");
		lblAnio.setVisible(false);
		lblAnio.setFont(new Font("Cambria", Font.BOLD, 14));
		lblAnio.setBounds(677, 6, 104, 14);
		lblAnio.setPreferredSize(new Dimension(50, 14));
		panel_Filtros.add(lblAnio);
		
		comboAnio = new JComboBox();
		comboAnio.setVisible(false);
		comboAnio.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboAnio.setBounds(677, 23, 104, 22);
		panel_Filtros.add(comboAnio);
		
		comboTecnico = new JComboBox<Object>();
		comboTecnico.setVisible(false);
		comboTecnico.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboTecnico.setBounds(396, 23, 166, 22);
		panel_Filtros.add(comboTecnico);
		
		lblTecnico = new JLabel("TÉCNICO: ");
		lblTecnico.setVisible(false);
		lblTecnico.setFont(new Font("Cambria", Font.BOLD, 14));
		lblTecnico.setBounds(396, 6, 104, 14);
		panel_Filtros.add(lblTecnico);
		
		comboCliente = new JComboBox();
		comboCliente.setVisible(false);
		comboCliente.setBounds(396, 23, 166, 22);
		panel_Filtros.add(comboCliente);
		
		panel = new JPanel();
		panel.setBorder(null);
		panelCentro.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_Ingresos = new JPanel();
		panel_Ingresos.setBackground(new Color(176, 196, 222));
		panel_Ingresos.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel.add(panel_Ingresos);
		panel_Ingresos.setLayout(new CardLayout(0, 0));
		
		panel_Diagnosticos = new JPanel();
		panel_Diagnosticos.setBackground(new Color(176, 196, 222));
		panel_Diagnosticos.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel.add(panel_Diagnosticos);
		panel_Diagnosticos.setLayout(new CardLayout(0, 0));
		
		panel_Facturacion = new JPanel();
		panel_Facturacion.setVisible(false);
		panel_Facturacion.setBackground(new Color(176, 196, 222));
		panel_Facturacion.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel.add(panel_Facturacion);
		panel_Facturacion.setLayout(new CardLayout(0, 0));
		
		JPanel panelDerecha = new JPanel();
		panelDerecha.setBackground(new Color(176, 196, 222));
		panelDerecha.setPreferredSize(new Dimension(350, 10));
		panelDerecha.setBorder(new LineBorder(new Color(0, 128, 128)));
		contentPane.add(panelDerecha, BorderLayout.EAST);
		panelDerecha.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Derecha_Inferior = new JPanel();
		panel_Derecha_Inferior.setBackground(new Color(176, 196, 222));
		panel_Derecha_Inferior.setBorder(new LineBorder(new Color(0, 128, 128)));
		panelDerecha.add(panel_Derecha_Inferior, BorderLayout.SOUTH);
		panel_Derecha_Inferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 6));
		
		btnConfiguracion = new JButton("CONFIGURACIÓN");
		btnConfiguracion.setFont(new Font("Cambria", Font.BOLD, 16));
		panel_Derecha_Inferior.add(btnConfiguracion);
		
		panel_Datos = new JPanel();
		panel_Datos.setBackground(new Color(176, 196, 222));
		panel_Datos.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel_Datos.setVisible(false);
		panelDerecha.add(panel_Datos, BorderLayout.CENTER);
		panel_Datos.setLayout(null);
		
		panel_facturacionPorAnio = new JPanel();
		panel_facturacionPorAnio.setVisible(false);
		
		
		panel_datosPorCliente = new JPanel();
		panel_datosPorCliente.setVisible(false);
		
		panel_facturacionPorTecnico = new JPanel();
		panel_facturacionPorTecnico.setVisible(false);
		
		panel_datosPorTecnico = new JPanel();
		panel_datosPorTecnico.setLayout(null);
		panel_datosPorTecnico.setVisible(false);
		
		panel_facturacionPorCliente = new JPanel();
		panel_facturacionPorCliente.setVisible(false);
		panel_facturacionPorCliente.setLayout(null);
		panel_facturacionPorCliente.setOpaque(false);
		panel_facturacionPorCliente.setBorder(null);
		panel_facturacionPorCliente.setBounds(4, 386, 333, 156);
		panel_Datos.add(panel_facturacionPorCliente);
		
		lblFacturacionClientePesos = new JLabel("FACTURACIÓN CLIENTE (PESOS): ");
		lblFacturacionClientePesos.setFont(new Font("Cambria", Font.BOLD, 11));
		lblFacturacionClientePesos.setBounds(10, 13, 169, 14);
		panel_facturacionPorCliente.add(lblFacturacionClientePesos);
		
		lblFacturacionClienteDolar = new JLabel("FACTURACIÓN CLIENTE (DOLARES): ");
		lblFacturacionClienteDolar.setFont(new Font("Cambria", Font.BOLD, 11));
		lblFacturacionClienteDolar.setBounds(10, 30, 179, 14);
		panel_facturacionPorCliente.add(lblFacturacionClienteDolar);
		
		textFactClientePesos = new JTextField();
		textFactClientePesos.setOpaque(false);
		textFactClientePesos.setHorizontalAlignment(SwingConstants.RIGHT);
		textFactClientePesos.setFont(new Font("Cambria", Font.PLAIN, 13));
		textFactClientePesos.setEditable(false);
		textFactClientePesos.setColumns(10);
		textFactClientePesos.setBorder(null);
		textFactClientePesos.setBounds(199, 11, 75, 16);
		panel_facturacionPorCliente.add(textFactClientePesos);
		
		textFactClienteDolar = new JTextField();
		textFactClienteDolar.setOpaque(false);
		textFactClienteDolar.setHorizontalAlignment(SwingConstants.RIGHT);
		textFactClienteDolar.setFont(new Font("Cambria", Font.PLAIN, 13));
		textFactClienteDolar.setEditable(false);
		textFactClienteDolar.setColumns(10);
		textFactClienteDolar.setBorder(null);
		textFactClienteDolar.setBounds(199, 28, 75, 16);
		panel_facturacionPorCliente.add(textFactClienteDolar);
		
		textPorcFacturacionPesoCliente = new JTextField();
		textPorcFacturacionPesoCliente.setOpaque(false);
		textPorcFacturacionPesoCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		textPorcFacturacionPesoCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcFacturacionPesoCliente.setEditable(false);
		textPorcFacturacionPesoCliente.setColumns(10);
		textPorcFacturacionPesoCliente.setBorder(null);
		textPorcFacturacionPesoCliente.setBounds(286, 11, 47, 16);
		panel_facturacionPorCliente.add(textPorcFacturacionPesoCliente);
		
		textPorcFacturacionDolarCliente = new JTextField();
		textPorcFacturacionDolarCliente.setOpaque(false);
		textPorcFacturacionDolarCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		textPorcFacturacionDolarCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcFacturacionDolarCliente.setEditable(false);
		textPorcFacturacionDolarCliente.setColumns(10);
		textPorcFacturacionDolarCliente.setBorder(null);
		textPorcFacturacionDolarCliente.setBounds(286, 28, 47, 16);
		panel_facturacionPorCliente.add(textPorcFacturacionDolarCliente);
		panel_datosPorTecnico.setOpaque(false);
		panel_datosPorTecnico.setBorder(null);
		panel_datosPorTecnico.setBounds(4, 126, 333, 251);
		panel_Datos.add(panel_datosPorTecnico);
		
		lblNewLabel_2_2_3 = new JLabel("REPARADOS");
		lblNewLabel_2_2_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2_3.setBounds(10, 82, 110, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_2_3);
		
		lblNewLabel_2_3_3 = new JLabel("REPARADOS EN GTÍA");
		lblNewLabel_2_3_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3_3.setBounds(10, 116, 111, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_3_3);
		
		lblNewLabel_2_4_4 = new JLabel("SIN FALLA");
		lblNewLabel_2_4_4.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_4.setBounds(10, 99, 111, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_4_4);
		
		separator_1_4 = new JSeparator();
		separator_1_4.setBounds(3, 244, 332, 2);
		panel_datosPorTecnico.add(separator_1_4);
		
		lblNewLabel_2_4_1_3 = new JLabel("EN REPARACIÓN");
		lblNewLabel_2_4_1_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_3.setBounds(10, 133, 110, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_4_1_3);
		
		lblNewLabel_2_4_1_1_3 = new JLabel("VENTAS");
		lblNewLabel_2_4_1_1_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1_3.setBounds(10, 150, 111, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_4_1_1_3);
		
		lblNewLabel_2_4_1_1_1_2 = new JLabel("SIN REPARACIÓN");
		lblNewLabel_2_4_1_1_1_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1_1_2.setBounds(10, 167, 111, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_4_1_1_1_2);
		
		lblNewLabel_2_5_2 = new JLabel("RESUMEN ANUAL");
		lblNewLabel_2_5_2.setFont(new Font("Cambria", Font.PLAIN, 16));
		lblNewLabel_2_5_2.setBounds(102, 10, 129, 19);
		panel_datosPorTecnico.add(lblNewLabel_2_5_2);
		
		lblNewLabel_2_2_1_2 = new JLabel("REPARADOS/ ACEPTADOS");
		lblNewLabel_2_2_1_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2_1_2.setBounds(10, 190, 129, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_2_1_2);
		
		lblNewLabel_2_4_2_2 = new JLabel("REPARADOS/ NO ACEPTADOS");
		lblNewLabel_2_4_2_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_2_2.setBounds(10, 207, 144, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_4_2_2);
		
		lblNewLabel_2_3_1_2 = new JLabel("REPARADOS/ A LA ESPERA");
		lblNewLabel_2_3_1_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3_1_2.setBounds(10, 224, 144, 14);
		panel_datosPorTecnico.add(lblNewLabel_2_3_1_2);
		
		separator_2_1_4 = new JSeparator();
		separator_2_1_4.setBounds(3, 242, 332, 2);
		panel_datosPorTecnico.add(separator_2_1_4);
		
		textPorcReparadosXTecnico = new JTextField();
		textPorcReparadosXTecnico.setOpaque(false);
		textPorcReparadosXTecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcReparadosXTecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcReparadosXTecnico.setEditable(false);
		textPorcReparadosXTecnico.setColumns(10);
		textPorcReparadosXTecnico.setBorder(null);
		textPorcReparadosXTecnico.setBounds(251, 80, 75, 16);
		panel_datosPorTecnico.add(textPorcReparadosXTecnico);
		
		textPorcSinFallasXtecnico = new JTextField();
		textPorcSinFallasXtecnico.setOpaque(false);
		textPorcSinFallasXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcSinFallasXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcSinFallasXtecnico.setEditable(false);
		textPorcSinFallasXtecnico.setColumns(10);
		textPorcSinFallasXtecnico.setBorder(null);
		textPorcSinFallasXtecnico.setBounds(251, 97, 75, 16);
		panel_datosPorTecnico.add(textPorcSinFallasXtecnico);
		
		textPorcRepGtiaXtecnico = new JTextField();
		textPorcRepGtiaXtecnico.setOpaque(false);
		textPorcRepGtiaXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepGtiaXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepGtiaXtecnico.setEditable(false);
		textPorcRepGtiaXtecnico.setColumns(10);
		textPorcRepGtiaXtecnico.setBorder(null);
		textPorcRepGtiaXtecnico.setBounds(251, 114, 75, 16);
		panel_datosPorTecnico.add(textPorcRepGtiaXtecnico);
		
		textPorcEnRepXtecnico = new JTextField();
		textPorcEnRepXtecnico.setOpaque(false);
		textPorcEnRepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcEnRepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcEnRepXtecnico.setEditable(false);
		textPorcEnRepXtecnico.setColumns(10);
		textPorcEnRepXtecnico.setBorder(null);
		textPorcEnRepXtecnico.setBounds(251, 131, 75, 16);
		panel_datosPorTecnico.add(textPorcEnRepXtecnico);
		
		textPorcVentasXtecnico = new JTextField();
		textPorcVentasXtecnico.setOpaque(false);
		textPorcVentasXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcVentasXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcVentasXtecnico.setEditable(false);
		textPorcVentasXtecnico.setColumns(10);
		textPorcVentasXtecnico.setBorder(null);
		textPorcVentasXtecnico.setBounds(251, 148, 75, 16);
		panel_datosPorTecnico.add(textPorcVentasXtecnico);
		
		textPorcSinRepXtecnico = new JTextField();
		textPorcSinRepXtecnico.setOpaque(false);
		textPorcSinRepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcSinRepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcSinRepXtecnico.setEditable(false);
		textPorcSinRepXtecnico.setColumns(10);
		textPorcSinRepXtecnico.setBorder(null);
		textPorcSinRepXtecnico.setBounds(251, 165, 75, 16);
		panel_datosPorTecnico.add(textPorcSinRepXtecnico);
		
		textPorcRepAcepXtecnico = new JTextField();
		textPorcRepAcepXtecnico.setOpaque(false);
		textPorcRepAcepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepAcepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepAcepXtecnico.setEditable(false);
		textPorcRepAcepXtecnico.setColumns(10);
		textPorcRepAcepXtecnico.setBorder(null);
		textPorcRepAcepXtecnico.setBounds(251, 190, 75, 16);
		panel_datosPorTecnico.add(textPorcRepAcepXtecnico);
		
		textPorcRepNoAcepXtecnico = new JTextField();
		textPorcRepNoAcepXtecnico.setOpaque(false);
		textPorcRepNoAcepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepNoAcepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepNoAcepXtecnico.setEditable(false);
		textPorcRepNoAcepXtecnico.setColumns(10);
		textPorcRepNoAcepXtecnico.setBorder(null);
		textPorcRepNoAcepXtecnico.setBounds(251, 207, 75, 16);
		panel_datosPorTecnico.add(textPorcRepNoAcepXtecnico);
		
		textPorcRepEsperaXtecnico = new JTextField();
		textPorcRepEsperaXtecnico.setOpaque(false);
		textPorcRepEsperaXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepEsperaXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepEsperaXtecnico.setEditable(false);
		textPorcRepEsperaXtecnico.setColumns(10);
		textPorcRepEsperaXtecnico.setBorder(null);
		textPorcRepEsperaXtecnico.setBounds(251, 224, 75, 16);
		panel_datosPorTecnico.add(textPorcRepEsperaXtecnico);
		
		separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(0, 2, 332, 2);
		panel_datosPorTecnico.add(separator_1_2_2);
		
		separator_2_1_1_2 = new JSeparator();
		separator_2_1_1_2.setBounds(0, 0, 332, 2);
		panel_datosPorTecnico.add(separator_2_1_1_2);
		
		separator_2_1_2_1_2 = new JSeparator();
		separator_2_1_2_1_2.setBounds(3, 185, 332, 2);
		panel_datosPorTecnico.add(separator_2_1_2_1_2);
		
		textReparadosXTecnico = new JTextField();
		textReparadosXTecnico.setOpaque(false);
		textReparadosXTecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textReparadosXTecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textReparadosXTecnico.setEditable(false);
		textReparadosXTecnico.setColumns(10);
		textReparadosXTecnico.setBorder(null);
		textReparadosXTecnico.setBounds(164, 80, 75, 16);
		panel_datosPorTecnico.add(textReparadosXTecnico);
		
		textSinFallasXtecnico = new JTextField();
		textSinFallasXtecnico.setOpaque(false);
		textSinFallasXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textSinFallasXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textSinFallasXtecnico.setEditable(false);
		textSinFallasXtecnico.setColumns(10);
		textSinFallasXtecnico.setBorder(null);
		textSinFallasXtecnico.setBounds(164, 97, 75, 16);
		panel_datosPorTecnico.add(textSinFallasXtecnico);
		
		textRepGtiaXtecnico = new JTextField();
		textRepGtiaXtecnico.setOpaque(false);
		textRepGtiaXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textRepGtiaXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepGtiaXtecnico.setEditable(false);
		textRepGtiaXtecnico.setColumns(10);
		textRepGtiaXtecnico.setBorder(null);
		textRepGtiaXtecnico.setBounds(164, 114, 75, 16);
		panel_datosPorTecnico.add(textRepGtiaXtecnico);
		
		textEnRepXtecnico = new JTextField();
		textEnRepXtecnico.setOpaque(false);
		textEnRepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textEnRepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textEnRepXtecnico.setEditable(false);
		textEnRepXtecnico.setColumns(10);
		textEnRepXtecnico.setBorder(null);
		textEnRepXtecnico.setBounds(164, 131, 75, 16);
		panel_datosPorTecnico.add(textEnRepXtecnico);
		
		textVentasXtecnico = new JTextField();
		textVentasXtecnico.setOpaque(false);
		textVentasXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textVentasXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textVentasXtecnico.setEditable(false);
		textVentasXtecnico.setColumns(10);
		textVentasXtecnico.setBorder(null);
		textVentasXtecnico.setBounds(164, 148, 75, 16);
		panel_datosPorTecnico.add(textVentasXtecnico);
		
		textSinRepXtecnico = new JTextField();
		textSinRepXtecnico.setOpaque(false);
		textSinRepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textSinRepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textSinRepXtecnico.setEditable(false);
		textSinRepXtecnico.setColumns(10);
		textSinRepXtecnico.setBorder(null);
		textSinRepXtecnico.setBounds(164, 165, 75, 16);
		panel_datosPorTecnico.add(textSinRepXtecnico);
		
		textRepAcepXtecnico = new JTextField();
		textRepAcepXtecnico.setOpaque(false);
		textRepAcepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textRepAcepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepAcepXtecnico.setEditable(false);
		textRepAcepXtecnico.setColumns(10);
		textRepAcepXtecnico.setBorder(null);
		textRepAcepXtecnico.setBounds(164, 190, 75, 16);
		panel_datosPorTecnico.add(textRepAcepXtecnico);
		
		textRepNoAcepXtecnico = new JTextField();
		textRepNoAcepXtecnico.setOpaque(false);
		textRepNoAcepXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textRepNoAcepXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepNoAcepXtecnico.setEditable(false);
		textRepNoAcepXtecnico.setColumns(10);
		textRepNoAcepXtecnico.setBorder(null);
		textRepNoAcepXtecnico.setBounds(164, 207, 75, 16);
		panel_datosPorTecnico.add(textRepNoAcepXtecnico);
		
		textRepEsperaXtecnico = new JTextField();
		textRepEsperaXtecnico.setOpaque(false);
		textRepEsperaXtecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textRepEsperaXtecnico.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepEsperaXtecnico.setEditable(false);
		textRepEsperaXtecnico.setColumns(10);
		textRepEsperaXtecnico.setBorder(null);
		textRepEsperaXtecnico.setBounds(164, 224, 75, 16);
		panel_datosPorTecnico.add(textRepEsperaXtecnico);
		
		lblEquiposRevidados = new JLabel("EQUIPOS REVISADOS:");
		lblEquiposRevidados.setFont(new Font("Cambria", Font.BOLD, 11));
		lblEquiposRevidados.setBounds(10, 58, 129, 14);
		panel_datosPorTecnico.add(lblEquiposRevidados);
		
		textTotalRevisados = new JTextField();
		textTotalRevisados.setOpaque(false);
		textTotalRevisados.setHorizontalAlignment(SwingConstants.CENTER);
		textTotalRevisados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textTotalRevisados.setEditable(false);
		textTotalRevisados.setColumns(10);
		textTotalRevisados.setBorder(null);
		textTotalRevisados.setBounds(164, 56, 75, 16);
		panel_datosPorTecnico.add(textTotalRevisados);
		
		textPorcentajeTotalRevisado = new JTextField();
		textPorcentajeTotalRevisado.setOpaque(false);
		textPorcentajeTotalRevisado.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeTotalRevisado.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeTotalRevisado.setEditable(false);
		textPorcentajeTotalRevisado.setColumns(10);
		textPorcentajeTotalRevisado.setBorder(null);
		textPorcentajeTotalRevisado.setBounds(251, 56, 75, 16);
		panel_datosPorTecnico.add(textPorcentajeTotalRevisado);
		
		textNombreTecnico = new JTextField();
		textNombreTecnico.setOpaque(false);
		textNombreTecnico.setHorizontalAlignment(SwingConstants.CENTER);
		textNombreTecnico.setFont(new Font("Cambria", Font.BOLD, 16));
		textNombreTecnico.setEditable(false);
		textNombreTecnico.setColumns(10);
		textNombreTecnico.setBorder(null);
		textNombreTecnico.setBounds(3, 29, 327, 18);
		panel_datosPorTecnico.add(textNombreTecnico);
		panel_facturacionPorTecnico.setLayout(null);
		panel_facturacionPorTecnico.setOpaque(false);
		panel_facturacionPorTecnico.setBorder(null);
		panel_facturacionPorTecnico.setBounds(4, 386, 333, 156);
		panel_Datos.add(panel_facturacionPorTecnico);
		
		lblFacturacionTecnicoPesos = new JLabel("FACTURACIÓN TÉCNICO (PESOS): ");
		lblFacturacionTecnicoPesos.setFont(new Font("Cambria", Font.BOLD, 11));
		lblFacturacionTecnicoPesos.setBounds(10, 13, 187, 14);
		panel_facturacionPorTecnico.add(lblFacturacionTecnicoPesos);
		
		lblFacturacionTecnicoDolar = new JLabel("FACTURACIÓN TÉCNICO (DOLARES): ");
		lblFacturacionTecnicoDolar.setFont(new Font("Cambria", Font.BOLD, 11));
		lblFacturacionTecnicoDolar.setBounds(10, 30, 187, 14);
		panel_facturacionPorTecnico.add(lblFacturacionTecnicoDolar);
		
		textFacturacionTecnicoPesos = new JTextField();
		textFacturacionTecnicoPesos.setOpaque(false);
		textFacturacionTecnicoPesos.setHorizontalAlignment(SwingConstants.CENTER);
		textFacturacionTecnicoPesos.setFont(new Font("Cambria", Font.PLAIN, 13));
		textFacturacionTecnicoPesos.setEditable(false);
		textFacturacionTecnicoPesos.setColumns(10);
		textFacturacionTecnicoPesos.setBorder(null);
		textFacturacionTecnicoPesos.setBounds(194, 11, 75, 16);
		panel_facturacionPorTecnico.add(textFacturacionTecnicoPesos);
		
		textFacturacionTecnicoDolares = new JTextField();
		textFacturacionTecnicoDolares.setOpaque(false);
		textFacturacionTecnicoDolares.setHorizontalAlignment(SwingConstants.CENTER);
		textFacturacionTecnicoDolares.setFont(new Font("Cambria", Font.PLAIN, 13));
		textFacturacionTecnicoDolares.setEditable(false);
		textFacturacionTecnicoDolares.setColumns(10);
		textFacturacionTecnicoDolares.setBorder(null);
		textFacturacionTecnicoDolares.setBounds(194, 28, 75, 16);
		panel_facturacionPorTecnico.add(textFacturacionTecnicoDolares);
		
		btnResumenMensualTecnico = new JButton("<html><center>RESUMEN MENSUAL</html>");
		btnResumenMensualTecnico.setFont(new Font("Cambria", Font.BOLD, 12));
		btnResumenMensualTecnico.setBounds(113, 59, 106, 37);
		panel_facturacionPorTecnico.add(btnResumenMensualTecnico);
		
		textPorcFacturacionTecnicoDolar = new JTextField();
		textPorcFacturacionTecnicoDolar.setOpaque(false);
		textPorcFacturacionTecnicoDolar.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcFacturacionTecnicoDolar.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcFacturacionTecnicoDolar.setEditable(false);
		textPorcFacturacionTecnicoDolar.setColumns(10);
		textPorcFacturacionTecnicoDolar.setBorder(null);
		textPorcFacturacionTecnicoDolar.setBounds(279, 28, 48, 16);
		panel_facturacionPorTecnico.add(textPorcFacturacionTecnicoDolar);
		
		textPorcFacturacionTecnicoPesos = new JTextField();
		textPorcFacturacionTecnicoPesos.setOpaque(false);
		textPorcFacturacionTecnicoPesos.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcFacturacionTecnicoPesos.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcFacturacionTecnicoPesos.setEditable(false);
		textPorcFacturacionTecnicoPesos.setColumns(10);
		textPorcFacturacionTecnicoPesos.setBorder(null);
		textPorcFacturacionTecnicoPesos.setBounds(279, 11, 48, 16);
		panel_facturacionPorTecnico.add(textPorcFacturacionTecnicoPesos);
		panel_datosPorCliente.setLayout(null);
		panel_datosPorCliente.setOpaque(false);
		panel_datosPorCliente.setBorder(null);
		panel_datosPorCliente.setBounds(4, 126, 333, 251);
		panel_Datos.add(panel_datosPorCliente);
		
		JLabel lblNewLabel_2_2_2 = new JLabel("REPARADOS");
		lblNewLabel_2_2_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2_2.setBounds(10, 82, 110, 14);
		panel_datosPorCliente.add(lblNewLabel_2_2_2);
		
		JLabel lblNewLabel_2_3_2 = new JLabel("REPARADOS EN GTÍA");
		lblNewLabel_2_3_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3_2.setBounds(10, 116, 111, 14);
		panel_datosPorCliente.add(lblNewLabel_2_3_2);
		
		JLabel lblNewLabel_2_4_3 = new JLabel("SIN FALLA");
		lblNewLabel_2_4_3.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_3.setBounds(10, 99, 111, 14);
		panel_datosPorCliente.add(lblNewLabel_2_4_3);
		
		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setBounds(3, 244, 332, 2);
		panel_datosPorCliente.add(separator_1_3);
		
		JLabel lblNewLabel_2_4_1_2 = new JLabel("EN REPARACIÓN");
		lblNewLabel_2_4_1_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_2.setBounds(10, 133, 110, 14);
		panel_datosPorCliente.add(lblNewLabel_2_4_1_2);
		
		JLabel lblNewLabel_2_4_1_1_2 = new JLabel("VENTAS");
		lblNewLabel_2_4_1_1_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1_2.setBounds(10, 150, 111, 14);
		panel_datosPorCliente.add(lblNewLabel_2_4_1_1_2);
		
		JLabel lblNewLabel_2_4_1_1_1_1 = new JLabel("SIN REPARACIÓN");
		lblNewLabel_2_4_1_1_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_1_1_1_1.setBounds(10, 167, 111, 14);
		panel_datosPorCliente.add(lblNewLabel_2_4_1_1_1_1);
		
		JLabel lblNewLabel_2_5_1 = new JLabel("RESUMEN ANUAL");
		lblNewLabel_2_5_1.setFont(new Font("Cambria", Font.PLAIN, 16));
		lblNewLabel_2_5_1.setBounds(102, 10, 129, 19);
		panel_datosPorCliente.add(lblNewLabel_2_5_1);
		
		JLabel lblNewLabel_2_2_1_1 = new JLabel("REPARADOS/ ACEPTADOS");
		lblNewLabel_2_2_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_2_1_1.setBounds(10, 190, 129, 14);
		panel_datosPorCliente.add(lblNewLabel_2_2_1_1);
		
		JLabel lblNewLabel_2_4_2_1 = new JLabel("REPARADOS/ NO ACEPTADOS");
		lblNewLabel_2_4_2_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_4_2_1.setBounds(10, 207, 144, 14);
		panel_datosPorCliente.add(lblNewLabel_2_4_2_1);
		
		JLabel lblNewLabel_2_3_1_1 = new JLabel("REPARADOS/ A LA ESPERA");
		lblNewLabel_2_3_1_1.setFont(new Font("Cambria", Font.PLAIN, 11));
		lblNewLabel_2_3_1_1.setBounds(10, 224, 144, 14);
		panel_datosPorCliente.add(lblNewLabel_2_3_1_1);
		
		JSeparator separator_2_1_3 = new JSeparator();
		separator_2_1_3.setBounds(3, 242, 332, 2);
		panel_datosPorCliente.add(separator_2_1_3);
		
		textPorcRepPorCliente = new JTextField();
		textPorcRepPorCliente.setOpaque(false);
		textPorcRepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepPorCliente.setEditable(false);
		textPorcRepPorCliente.setColumns(10);
		textPorcRepPorCliente.setBorder(null);
		textPorcRepPorCliente.setBounds(251, 80, 75, 16);
		panel_datosPorCliente.add(textPorcRepPorCliente);
		
		textPorcSinFallaPorCliente = new JTextField();
		textPorcSinFallaPorCliente.setOpaque(false);
		textPorcSinFallaPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcSinFallaPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcSinFallaPorCliente.setEditable(false);
		textPorcSinFallaPorCliente.setColumns(10);
		textPorcSinFallaPorCliente.setBorder(null);
		textPorcSinFallaPorCliente.setBounds(251, 97, 75, 16);
		panel_datosPorCliente.add(textPorcSinFallaPorCliente);
		
		textPorcRepEnGtiaPorCliente = new JTextField();
		textPorcRepEnGtiaPorCliente.setOpaque(false);
		textPorcRepEnGtiaPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepEnGtiaPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepEnGtiaPorCliente.setEditable(false);
		textPorcRepEnGtiaPorCliente.setColumns(10);
		textPorcRepEnGtiaPorCliente.setBorder(null);
		textPorcRepEnGtiaPorCliente.setBounds(251, 114, 75, 16);
		panel_datosPorCliente.add(textPorcRepEnGtiaPorCliente);
		
		textPorcEnRepPorCliente = new JTextField();
		textPorcEnRepPorCliente.setOpaque(false);
		textPorcEnRepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcEnRepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcEnRepPorCliente.setEditable(false);
		textPorcEnRepPorCliente.setColumns(10);
		textPorcEnRepPorCliente.setBorder(null);
		textPorcEnRepPorCliente.setBounds(251, 131, 75, 16);
		panel_datosPorCliente.add(textPorcEnRepPorCliente);
		
		textPorcVentasPorCliente = new JTextField();
		textPorcVentasPorCliente.setOpaque(false);
		textPorcVentasPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcVentasPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcVentasPorCliente.setEditable(false);
		textPorcVentasPorCliente.setColumns(10);
		textPorcVentasPorCliente.setBorder(null);
		textPorcVentasPorCliente.setBounds(251, 148, 75, 16);
		panel_datosPorCliente.add(textPorcVentasPorCliente);
		
		textPorcSinRepPorCliente = new JTextField();
		textPorcSinRepPorCliente.setOpaque(false);
		textPorcSinRepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcSinRepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcSinRepPorCliente.setEditable(false);
		textPorcSinRepPorCliente.setColumns(10);
		textPorcSinRepPorCliente.setBorder(null);
		textPorcSinRepPorCliente.setBounds(251, 165, 75, 16);
		panel_datosPorCliente.add(textPorcSinRepPorCliente);
		
		textPorcRepAcepPorCliente = new JTextField();
		textPorcRepAcepPorCliente.setOpaque(false);
		textPorcRepAcepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepAcepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepAcepPorCliente.setEditable(false);
		textPorcRepAcepPorCliente.setColumns(10);
		textPorcRepAcepPorCliente.setBorder(null);
		textPorcRepAcepPorCliente.setBounds(251, 190, 75, 16);
		panel_datosPorCliente.add(textPorcRepAcepPorCliente);
		
		textPorcRepNoAcepPorCliente = new JTextField();
		textPorcRepNoAcepPorCliente.setOpaque(false);
		textPorcRepNoAcepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepNoAcepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepNoAcepPorCliente.setEditable(false);
		textPorcRepNoAcepPorCliente.setColumns(10);
		textPorcRepNoAcepPorCliente.setBorder(null);
		textPorcRepNoAcepPorCliente.setBounds(251, 207, 75, 16);
		panel_datosPorCliente.add(textPorcRepNoAcepPorCliente);
		
		textPorcRepEsperaPorCliente = new JTextField();
		textPorcRepEsperaPorCliente.setOpaque(false);
		textPorcRepEsperaPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcRepEsperaPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcRepEsperaPorCliente.setEditable(false);
		textPorcRepEsperaPorCliente.setColumns(10);
		textPorcRepEsperaPorCliente.setBorder(null);
		textPorcRepEsperaPorCliente.setBounds(251, 224, 75, 16);
		panel_datosPorCliente.add(textPorcRepEsperaPorCliente);
		
		JSeparator separator_1_2_1 = new JSeparator();
		separator_1_2_1.setBounds(0, 2, 332, 2);
		panel_datosPorCliente.add(separator_1_2_1);
		
		JSeparator separator_2_1_1_1 = new JSeparator();
		separator_2_1_1_1.setBounds(0, 0, 332, 2);
		panel_datosPorCliente.add(separator_2_1_1_1);
		
		JSeparator separator_2_1_2_1_1 = new JSeparator();
		separator_2_1_2_1_1.setBounds(3, 185, 332, 2);
		panel_datosPorCliente.add(separator_2_1_2_1_1);
		
		textReparadosPorCliente = new JTextField();
		textReparadosPorCliente.setOpaque(false);
		textReparadosPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textReparadosPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textReparadosPorCliente.setEditable(false);
		textReparadosPorCliente.setColumns(10);
		textReparadosPorCliente.setBorder(null);
		textReparadosPorCliente.setBounds(164, 80, 75, 16);
		panel_datosPorCliente.add(textReparadosPorCliente);
		
		textSinFallaPorCliente = new JTextField();
		textSinFallaPorCliente.setOpaque(false);
		textSinFallaPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textSinFallaPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textSinFallaPorCliente.setEditable(false);
		textSinFallaPorCliente.setColumns(10);
		textSinFallaPorCliente.setBorder(null);
		textSinFallaPorCliente.setBounds(164, 97, 75, 16);
		panel_datosPorCliente.add(textSinFallaPorCliente);
		
		textRepEnGtiaPorCliente = new JTextField();
		textRepEnGtiaPorCliente.setOpaque(false);
		textRepEnGtiaPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textRepEnGtiaPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepEnGtiaPorCliente.setEditable(false);
		textRepEnGtiaPorCliente.setColumns(10);
		textRepEnGtiaPorCliente.setBorder(null);
		textRepEnGtiaPorCliente.setBounds(164, 114, 75, 16);
		panel_datosPorCliente.add(textRepEnGtiaPorCliente);
		
		textEnRepPorCLiente = new JTextField();
		textEnRepPorCLiente.setOpaque(false);
		textEnRepPorCLiente.setHorizontalAlignment(SwingConstants.CENTER);
		textEnRepPorCLiente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textEnRepPorCLiente.setEditable(false);
		textEnRepPorCLiente.setColumns(10);
		textEnRepPorCLiente.setBorder(null);
		textEnRepPorCLiente.setBounds(164, 131, 75, 16);
		panel_datosPorCliente.add(textEnRepPorCLiente);
		
		textVentasPorCliente = new JTextField();
		textVentasPorCliente.setOpaque(false);
		textVentasPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textVentasPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textVentasPorCliente.setEditable(false);
		textVentasPorCliente.setColumns(10);
		textVentasPorCliente.setBorder(null);
		textVentasPorCliente.setBounds(164, 148, 75, 16);
		panel_datosPorCliente.add(textVentasPorCliente);
		
		textSinRepPorCliente = new JTextField();
		textSinRepPorCliente.setOpaque(false);
		textSinRepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textSinRepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textSinRepPorCliente.setEditable(false);
		textSinRepPorCliente.setColumns(10);
		textSinRepPorCliente.setBorder(null);
		textSinRepPorCliente.setBounds(164, 165, 75, 16);
		panel_datosPorCliente.add(textSinRepPorCliente);
		
		textRepAcepPorCliente = new JTextField();
		textRepAcepPorCliente.setOpaque(false);
		textRepAcepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textRepAcepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepAcepPorCliente.setEditable(false);
		textRepAcepPorCliente.setColumns(10);
		textRepAcepPorCliente.setBorder(null);
		textRepAcepPorCliente.setBounds(164, 190, 75, 16);
		panel_datosPorCliente.add(textRepAcepPorCliente);
		
		textRepNoAcepPorCliente = new JTextField();
		textRepNoAcepPorCliente.setOpaque(false);
		textRepNoAcepPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textRepNoAcepPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepNoAcepPorCliente.setEditable(false);
		textRepNoAcepPorCliente.setColumns(10);
		textRepNoAcepPorCliente.setBorder(null);
		textRepNoAcepPorCliente.setBounds(164, 207, 75, 16);
		panel_datosPorCliente.add(textRepNoAcepPorCliente);
		
		textRepEsperaPorCliente = new JTextField();
		textRepEsperaPorCliente.setOpaque(false);
		textRepEsperaPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textRepEsperaPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepEsperaPorCliente.setEditable(false);
		textRepEsperaPorCliente.setColumns(10);
		textRepEsperaPorCliente.setBorder(null);
		textRepEsperaPorCliente.setBounds(164, 224, 75, 16);
		panel_datosPorCliente.add(textRepEsperaPorCliente);
		
		JLabel lblIngresosdelCliente = new JLabel("INGRESOS DEL CLIENTE");
		lblIngresosdelCliente.setFont(new Font("Cambria", Font.BOLD, 11));
		lblIngresosdelCliente.setBounds(10, 57, 129, 14);
		panel_datosPorCliente.add(lblIngresosdelCliente);
		
		textIngresosPorCliente = new JTextField();
		textIngresosPorCliente.setOpaque(false);
		textIngresosPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textIngresosPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textIngresosPorCliente.setEditable(false);
		textIngresosPorCliente.setColumns(10);
		textIngresosPorCliente.setBorder(null);
		textIngresosPorCliente.setBounds(164, 56, 75, 16);
		panel_datosPorCliente.add(textIngresosPorCliente);
		
		textPorcIngresosPorCliente = new JTextField();
		textPorcIngresosPorCliente.setOpaque(false);
		textPorcIngresosPorCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcIngresosPorCliente.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcIngresosPorCliente.setEditable(false);
		textPorcIngresosPorCliente.setColumns(10);
		textPorcIngresosPorCliente.setBorder(null);
		textPorcIngresosPorCliente.setBounds(251, 54, 75, 16);
		panel_datosPorCliente.add(textPorcIngresosPorCliente);
		
		textNombreCliente = new JTextField();
		textNombreCliente.setOpaque(false);
		textNombreCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textNombreCliente.setFont(new Font("Cambria", Font.BOLD, 16));
		textNombreCliente.setEditable(false);
		textNombreCliente.setColumns(10);
		textNombreCliente.setBorder(null);
		textNombreCliente.setBounds(3, 29, 327, 16);
		panel_datosPorCliente.add(textNombreCliente);
		panel_facturacionPorAnio.setOpaque(false);
		panel_facturacionPorAnio.setBorder(null);
		panel_facturacionPorAnio.setBounds(4, 386, 333, 156);
		panel_Datos.add(panel_facturacionPorAnio);
		panel_facturacionPorAnio.setLayout(null);
		
		btnFacturacionPorCliente = new JButton("<html><center>FACTURACIÓN POR CLIENTE</html>");
		btnFacturacionPorCliente.setBounds(113, 59, 106, 37);
		panel_facturacionPorAnio.add(btnFacturacionPorCliente);
		btnFacturacionPorCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		
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
		textPorcentajeReparados.setBounds(251, 80, 75, 16);
		panel_datosPorAnio.add(textPorcentajeReparados);
		textPorcentajeReparados.setBorder(null);
		textPorcentajeReparados.setOpaque(false);
		textPorcentajeReparados.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeReparados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeReparados.setEditable(false);
		textPorcentajeReparados.setColumns(10);
		
		textPorcentajeSinFalla = new JTextField();
		textPorcentajeSinFalla.setBounds(251, 97, 75, 16);
		panel_datosPorAnio.add(textPorcentajeSinFalla);
		textPorcentajeSinFalla.setBorder(null);
		textPorcentajeSinFalla.setOpaque(false);
		textPorcentajeSinFalla.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeSinFalla.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeSinFalla.setEditable(false);
		textPorcentajeSinFalla.setColumns(10);
		
		textPorcentajeRepEnGtia = new JTextField();
		textPorcentajeRepEnGtia.setBounds(251, 114, 75, 16);
		panel_datosPorAnio.add(textPorcentajeRepEnGtia);
		textPorcentajeRepEnGtia.setBorder(null);
		textPorcentajeRepEnGtia.setOpaque(false);
		textPorcentajeRepEnGtia.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeRepEnGtia.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeRepEnGtia.setEditable(false);
		textPorcentajeRepEnGtia.setColumns(10);
		
		textPorcentajeEnReparacion = new JTextField();
		textPorcentajeEnReparacion.setBounds(251, 131, 75, 16);
		panel_datosPorAnio.add(textPorcentajeEnReparacion);
		textPorcentajeEnReparacion.setBorder(null);
		textPorcentajeEnReparacion.setOpaque(false);
		textPorcentajeEnReparacion.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeEnReparacion.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeEnReparacion.setEditable(false);
		textPorcentajeEnReparacion.setColumns(10);
		
		textPorcentajeVentas = new JTextField();
		textPorcentajeVentas.setBounds(251, 148, 75, 16);
		panel_datosPorAnio.add(textPorcentajeVentas);
		textPorcentajeVentas.setBorder(null);
		textPorcentajeVentas.setOpaque(false);
		textPorcentajeVentas.setHorizontalAlignment(SwingConstants.CENTER);
		textPorcentajeVentas.setFont(new Font("Cambria", Font.PLAIN, 13));
		textPorcentajeVentas.setEditable(false);
		textPorcentajeVentas.setColumns(10);
		
		textPorcentajeSinReparacion = new JTextField();
		textPorcentajeSinReparacion.setBounds(251, 165, 75, 16);
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
		textReparados.setBounds(164, 80, 75, 16);
		panel_datosPorAnio.add(textReparados);
		textReparados.setBorder(null);
		textReparados.setOpaque(false);
		textReparados.setHorizontalAlignment(SwingConstants.CENTER);
		textReparados.setEditable(false);
		textReparados.setFont(new Font("Cambria", Font.PLAIN, 13));
		textReparados.setColumns(10);
		
		textSinFalla = new JTextField();
		textSinFalla.setBounds(164, 97, 75, 16);
		panel_datosPorAnio.add(textSinFalla);
		textSinFalla.setBorder(null);
		textSinFalla.setOpaque(false);
		textSinFalla.setHorizontalAlignment(SwingConstants.CENTER);
		textSinFalla.setEditable(false);
		textSinFalla.setFont(new Font("Cambria", Font.PLAIN, 13));
		textSinFalla.setColumns(10);
		
		textRepEnGtia = new JTextField();
		textRepEnGtia.setBounds(164, 114, 75, 16);
		panel_datosPorAnio.add(textRepEnGtia);
		textRepEnGtia.setBorder(null);
		textRepEnGtia.setOpaque(false);
		textRepEnGtia.setHorizontalAlignment(SwingConstants.CENTER);
		textRepEnGtia.setEditable(false);
		textRepEnGtia.setFont(new Font("Cambria", Font.PLAIN, 13));
		textRepEnGtia.setColumns(10);
		
		textEnReparacion = new JTextField();
		textEnReparacion.setBounds(164, 131, 75, 16);
		panel_datosPorAnio.add(textEnReparacion);
		textEnReparacion.setBorder(null);
		textEnReparacion.setOpaque(false);
		textEnReparacion.setHorizontalAlignment(SwingConstants.CENTER);
		textEnReparacion.setEditable(false);
		textEnReparacion.setFont(new Font("Cambria", Font.PLAIN, 13));
		textEnReparacion.setColumns(10);
		
		textVentas = new JTextField();
		textVentas.setBounds(164, 148, 75, 16);
		panel_datosPorAnio.add(textVentas);
		textVentas.setBorder(null);
		textVentas.setOpaque(false);
		textVentas.setHorizontalAlignment(SwingConstants.CENTER);
		textVentas.setEditable(false);
		textVentas.setFont(new Font("Cambria", Font.PLAIN, 13));
		textVentas.setColumns(10);
		
		textSinReparacion = new JTextField();
		textSinReparacion.setBounds(164, 165, 75, 16);
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





	public JPanel getPanel_datosPorCliente() {
		return panel_datosPorCliente;
	}





	public void setPanel_datosPorCliente(JPanel panel_datosPorCliente) {
		this.panel_datosPorCliente = panel_datosPorCliente;
	}





	public JTextField getTextPorcRepPorCliente() {
		return textPorcRepPorCliente;
	}





	public void setTextPorcRepPorCliente(JTextField textPorcRepPorCliente) {
		this.textPorcRepPorCliente = textPorcRepPorCliente;
	}





	public JTextField getTextPorcSinFallaPorCliente() {
		return textPorcSinFallaPorCliente;
	}





	public void setTextPorcSinFallaPorCliente(JTextField textPorcSinFallaPorCliente) {
		this.textPorcSinFallaPorCliente = textPorcSinFallaPorCliente;
	}





	public JTextField getTextPorcRepEnGtiaPorCliente() {
		return textPorcRepEnGtiaPorCliente;
	}





	public void setTextPorcRepEnGtiaPorCliente(JTextField textPorcRepEnGtiaPorCliente) {
		this.textPorcRepEnGtiaPorCliente = textPorcRepEnGtiaPorCliente;
	}





	public JTextField getTextPorcEnRepPorCliente() {
		return textPorcEnRepPorCliente;
	}





	public void setTextPorcEnRepPorCliente(JTextField textPorcEnRepPorCliente) {
		this.textPorcEnRepPorCliente = textPorcEnRepPorCliente;
	}





	public JTextField getTextPorcVentasPorCliente() {
		return textPorcVentasPorCliente;
	}





	public void setTextPorcVentasPorCliente(JTextField textPorcVentasPorCliente) {
		this.textPorcVentasPorCliente = textPorcVentasPorCliente;
	}





	public JTextField getTextPorcSinRepPorCliente() {
		return textPorcSinRepPorCliente;
	}





	public void setTextPorcSinRepPorCliente(JTextField textPorcSinRepPorCliente) {
		this.textPorcSinRepPorCliente = textPorcSinRepPorCliente;
	}





	public JTextField getTextPorcRepAcepPorCliente() {
		return textPorcRepAcepPorCliente;
	}





	public void setTextPorcRepAcepPorCliente(JTextField textPorcRepAcepPorCliente) {
		this.textPorcRepAcepPorCliente = textPorcRepAcepPorCliente;
	}





	public JTextField getTextPorcRepNoAcepPorCliente() {
		return textPorcRepNoAcepPorCliente;
	}





	public void setTextPorcRepNoAcepPorCliente(JTextField textPorcRepNoAcepPorCliente) {
		this.textPorcRepNoAcepPorCliente = textPorcRepNoAcepPorCliente;
	}





	public JTextField getTextPorcRepEsperaPorCliente() {
		return textPorcRepEsperaPorCliente;
	}





	public void setTextPorcRepEsperaPorCliente(JTextField textPorcRepEsperaPorCliente) {
		this.textPorcRepEsperaPorCliente = textPorcRepEsperaPorCliente;
	}





	public JTextField getTextReparadosPorCliente() {
		return textReparadosPorCliente;
	}





	public void setTextReparadosPorCliente(JTextField textReparadosPorCliente) {
		this.textReparadosPorCliente = textReparadosPorCliente;
	}





	public JTextField getTextSinFallaPorCliente() {
		return textSinFallaPorCliente;
	}





	public void setTextSinFallaPorCliente(JTextField textSinFallaPorCliente) {
		this.textSinFallaPorCliente = textSinFallaPorCliente;
	}





	public JTextField getTextRepEnGtiaPorCliente() {
		return textRepEnGtiaPorCliente;
	}





	public void setTextRepEnGtiaPorCliente(JTextField textRepEnGtiaPorCliente) {
		this.textRepEnGtiaPorCliente = textRepEnGtiaPorCliente;
	}





	public JTextField getTextEnRepPorCLiente() {
		return textEnRepPorCLiente;
	}





	public void setTextEnRepPorCLiente(JTextField textEnRepPorCLiente) {
		this.textEnRepPorCLiente = textEnRepPorCLiente;
	}





	public JTextField getTextVentasPorCliente() {
		return textVentasPorCliente;
	}





	public void setTextVentasPorCliente(JTextField textVentasPorCliente) {
		this.textVentasPorCliente = textVentasPorCliente;
	}





	public JTextField getTextSinRepPorCliente() {
		return textSinRepPorCliente;
	}





	public void setTextSinRepPorCliente(JTextField textSinRepPorCliente) {
		this.textSinRepPorCliente = textSinRepPorCliente;
	}





	public JTextField getTextRepAcepPorCliente() {
		return textRepAcepPorCliente;
	}





	public void setTextRepAcepPorCliente(JTextField textRepAcepPorCliente) {
		this.textRepAcepPorCliente = textRepAcepPorCliente;
	}





	public JTextField getTextRepNoAcepPorCliente() {
		return textRepNoAcepPorCliente;
	}





	public void setTextRepNoAcepPorCliente(JTextField textRepNoAcepPorCliente) {
		this.textRepNoAcepPorCliente = textRepNoAcepPorCliente;
	}





	public JTextField getTextRepEsperaPorCliente() {
		return textRepEsperaPorCliente;
	}





	public void setTextRepEsperaPorCliente(JTextField textRepEsperaPorCliente) {
		this.textRepEsperaPorCliente = textRepEsperaPorCliente;
	}





	public JTextField getTextIngresosPorCliente() {
		return textIngresosPorCliente;
	}





	public void setTextIngresosPorCliente(JTextField textIngresosPorCliente) {
		this.textIngresosPorCliente = textIngresosPorCliente;
	}





	public JTextField getTextPorcIngresosPorCliente() {
		return textPorcIngresosPorCliente;
	}





	public void setTextPorcIngresosPorCliente(JTextField textPorcIngresosPorCliente) {
		this.textPorcIngresosPorCliente = textPorcIngresosPorCliente;
	}





	public JPanel getPanel_facturacionPorCliente() {
		return panel_facturacionPorCliente;
	}





	public void setPanel_facturacionPorCliente(JPanel panel_facturacionPorCliente) {
		this.panel_facturacionPorCliente = panel_facturacionPorCliente;
	}





	public JLabel getLblFacturacionClientePesos() {
		return lblFacturacionClientePesos;
	}





	public void setLblFacturacionClientePesos(JLabel lblFacturacionClientePesos) {
		this.lblFacturacionClientePesos = lblFacturacionClientePesos;
	}





	public JLabel getLblFacturacionClienteDolar() {
		return lblFacturacionClienteDolar;
	}





	public void setLblFacturacionClienteDolar(JLabel lblFacturacionClienteDolar) {
		this.lblFacturacionClienteDolar = lblFacturacionClienteDolar;
	}





	public JTextField getTextFactClientePesos() {
		return textFactClientePesos;
	}





	public void setTextFactClientePesos(JTextField textFactClientePesos) {
		this.textFactClientePesos = textFactClientePesos;
	}





	public JTextField getTextFactClienteDolar() {
		return textFactClienteDolar;
	}





	public void setTextFactClienteDolar(JTextField textFactClienteDolar) {
		this.textFactClienteDolar = textFactClienteDolar;
	}





	public JTextField getTextNombreCliente() {
		return textNombreCliente;
	}





	public void setTextNombreCliente(JTextField textNombreCliente) {
		this.textNombreCliente = textNombreCliente;
	}




	public JButton getBtnResumenMensualTecnico() {
		return btnResumenMensualTecnico;
	}





	public void setBtnResumenMensualTecnico(JButton btnResumenMensualTecnico) {
		this.btnResumenMensualTecnico = btnResumenMensualTecnico;
	}





	public JPanel getPanel_datosPorTecnico() {
		return panel_datosPorTecnico;
	}





	public void setPanel_datosPorTecnico(JPanel panel_datosPorTecnico) {
		this.panel_datosPorTecnico = panel_datosPorTecnico;
	}





	public JTextField getTextTotalRevisados() {
		return textTotalRevisados;
	}





	public void setTextTotalRevisados(JTextField textTotalRevisados) {
		this.textTotalRevisados = textTotalRevisados;
	}





	public JTextField getTextPorcentajeTotalRevisado() {
		return textPorcentajeTotalRevisado;
	}





	public void setTextPorcentajeTotalRevisado(JTextField textPorcentajeTotalRevisado) {
		this.textPorcentajeTotalRevisado = textPorcentajeTotalRevisado;
	}





	public JTextField getTextNombreTecnico() {
		return textNombreTecnico;
	}





	public void setTextNombreTecnico(JTextField textNombreTecnico) {
		this.textNombreTecnico = textNombreTecnico;
	}





	public JPanel getPanel_facturacionPorTecnico() {
		return panel_facturacionPorTecnico;
	}





	public void setPanel_facturacionPorTecnico(JPanel panel_facturacionTecnico) {
		this.panel_facturacionPorTecnico = panel_facturacionTecnico;
	}





	public JTextField getTextFacturacionTecnicoPesos() {
		return textFacturacionTecnicoPesos;
	}





	public void setTextFacturacionTecnicoPesos(JTextField textFacturacionTecnicoPesos) {
		this.textFacturacionTecnicoPesos = textFacturacionTecnicoPesos;
	}





	public JTextField getTextFacturacionTecnicoDolares() {
		return textFacturacionTecnicoDolares;
	}





	public void setTextFacturacionTecnicoDolares(JTextField textFacturacionTecnicoDolares) {
		this.textFacturacionTecnicoDolares = textFacturacionTecnicoDolares;
	}





	public JTextField getTextPorcFacturacionPesoCliente() {
		return textPorcFacturacionPesoCliente;
	}





	public void setTextPorcFacturacionPesoCliente(JTextField textPorcFacturacionPeso) {
		this.textPorcFacturacionPesoCliente = textPorcFacturacionPeso;
	}





	public JTextField getTextPorcFacturacionDolarCliente() {
		return textPorcFacturacionDolarCliente;
	}





	public void setTextPorcFacturacionDolarCliente(JTextField textPorcFacturacionDolar) {
		this.textPorcFacturacionDolarCliente = textPorcFacturacionDolar;
	}





	public JTextField getTextPorcReparadosXTecnico() {
		return textPorcReparadosXTecnico;
	}





	public void setTextPorcReparadosXTecnico(JTextField textPorcReparadosXTecnico) {
		this.textPorcReparadosXTecnico = textPorcReparadosXTecnico;
	}





	public JTextField getTextPorcSinFallasXtecnico() {
		return textPorcSinFallasXtecnico;
	}





	public void setTextPorcSinFallasXtecnico(JTextField textPorcSinFallasXtecnico) {
		this.textPorcSinFallasXtecnico = textPorcSinFallasXtecnico;
	}





	public JTextField getTextPorcRepGtiaXtecnico() {
		return textPorcRepGtiaXtecnico;
	}





	public void setTextPorcRepGtiaXtecnico(JTextField textPorcRepGtiaXtecnico) {
		this.textPorcRepGtiaXtecnico = textPorcRepGtiaXtecnico;
	}





	public JTextField getTextPorcEnRepXtecnico() {
		return textPorcEnRepXtecnico;
	}





	public void setTextPorcEnRepXtecnico(JTextField textPorcEnRepXtecnico) {
		this.textPorcEnRepXtecnico = textPorcEnRepXtecnico;
	}





	public JTextField getTextPorcVentasXtecnico() {
		return textPorcVentasXtecnico;
	}





	public void setTextPorcVentasXtecnico(JTextField textPorcVentasXtecnico) {
		this.textPorcVentasXtecnico = textPorcVentasXtecnico;
	}





	public JTextField getTextPorcSinRepXtecnico() {
		return textPorcSinRepXtecnico;
	}





	public void setTextPorcSinRepXtecnico(JTextField textPorcSinRepXtecnico) {
		this.textPorcSinRepXtecnico = textPorcSinRepXtecnico;
	}





	public JTextField getTextPorcRepAcepXtecnico() {
		return textPorcRepAcepXtecnico;
	}





	public void setTextPorcRepAcepXtecnico(JTextField textPorcRepAcepXtecnico) {
		this.textPorcRepAcepXtecnico = textPorcRepAcepXtecnico;
	}





	public JTextField getTextPorcRepNoAcepXtecnico() {
		return textPorcRepNoAcepXtecnico;
	}





	public void setTextPorcRepNoAcepXtecnico(JTextField textPorcRepNoAcepXtecnico) {
		this.textPorcRepNoAcepXtecnico = textPorcRepNoAcepXtecnico;
	}





	public JTextField getTextPorcRepEsperaXtecnico() {
		return textPorcRepEsperaXtecnico;
	}





	public void setTextPorcRepEsperaXtecnico(JTextField textPorcRepEsperaXtecnico) {
		this.textPorcRepEsperaXtecnico = textPorcRepEsperaXtecnico;
	}





	public JTextField XTecnico() {
		return textReparadosXTecnico;
	}





	public void setTextReparadosXTecnico(JTextField textReparadosXTecnico) {
		this.textReparadosXTecnico = textReparadosXTecnico;
	}





	public JTextField getTextSinFallasXtecnico() {
		return textSinFallasXtecnico;
	}





	public void setTextSinFallasXtecnico(JTextField textSinFallasXtecnico) {
		this.textSinFallasXtecnico = textSinFallasXtecnico;
	}





	public JTextField getTextRepGtiaXtecnico() {
		return textRepGtiaXtecnico;
	}





	public void setTextRepGtiaXtecnico(JTextField textRepGtiaXtecnico) {
		this.textRepGtiaXtecnico = textRepGtiaXtecnico;
	}





	public JTextField getTextEnRepXtecnico() {
		return textEnRepXtecnico;
	}





	public void setTextEnRepXtecnico(JTextField textEnRepXtecnico) {
		this.textEnRepXtecnico = textEnRepXtecnico;
	}





	public JTextField getTextVentasXtecnico() {
		return textVentasXtecnico;
	}





	public void setTextVentasXtecnico(JTextField textVentasXtecnico) {
		this.textVentasXtecnico = textVentasXtecnico;
	}





	public JTextField getTextSinRepXtecnico() {
		return textSinRepXtecnico;
	}





	public void setTextSinRepXtecnico(JTextField textSinRepXtecnico) {
		this.textSinRepXtecnico = textSinRepXtecnico;
	}





	public JTextField getTextRepAcepXtecnico() {
		return textRepAcepXtecnico;
	}





	public void setTextRepAcepXtecnico(JTextField textRepAcepXtecnico) {
		this.textRepAcepXtecnico = textRepAcepXtecnico;
	}





	public JTextField getTextRepNoAcepXtecnico() {
		return textRepNoAcepXtecnico;
	}





	public void setTextRepNoAcepXtecnico(JTextField textRepNoAcepXtecnico) {
		this.textRepNoAcepXtecnico = textRepNoAcepXtecnico;
	}





	public JTextField getTextRepEsperaXtecnico() {
		return textRepEsperaXtecnico;
	}





	public void setTextRepEsperaXtecnico(JTextField textRepEsperaXtecnico) {
		this.textRepEsperaXtecnico = textRepEsperaXtecnico;
	}





	public JTextField getTextPorcFacturacionTecnicoDolar() {
		return textPorcFacturacionTecnicoDolar;
	}





	public void setTextPorcFacturacionTecnicoDolar(JTextField textPorcFacturacionTecnicoDolar) {
		this.textPorcFacturacionTecnicoDolar = textPorcFacturacionTecnicoDolar;
	}





	public JTextField getTextPorcFacturacionTecnicoPesos() {
		return textPorcFacturacionTecnicoPesos;
	}





	public void setTextPorcFacturacionTecnicoPesos(JTextField textPorcFacturacionTecnicoPesos) {
		this.textPorcFacturacionTecnicoPesos = textPorcFacturacionTecnicoPesos;
	}





	public JTextField getTextReparadosXTecnico() {
		return textReparadosXTecnico;
	}
	}