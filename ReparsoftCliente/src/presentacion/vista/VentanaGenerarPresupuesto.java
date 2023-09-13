package presentacion.vista;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorPresupuestos;
import presentacion.controlador.ControladorPrincipal;
import presentacion.controlador.ControladorReparacion;
import java.awt.SystemColor;
import java.sql.Date;
import java.text.DecimalFormat;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import VistaPropias.JTextDouble;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class VentanaGenerarPresupuesto extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_4;
	private JPanel panel_5;

	private ControladorReparacion controladorP;
	private ControladorPresupuestos controlador;
	private JTextField textCliente;
	private JTextField textELS;
	private JTextField textEquipo;
	private JTextField textMarca;
	private JTextField textModelo;
	private JTextField textSerie;
	private JTextField textAviso;
	private JTextField textClienteCliente;
	private JTextField textRemCliente;
	private JTextArea textInforme;
	private JTextArea textcondicionesMoneda;
	private JTextArea textcondicionesPago;
	private JTextArea textPlazoEntrega;

	private JTextField textPrecioPeso;
	private JTextField textPrecioDolar;
	private JTextDouble textCotizacionDolarOf;
	private JTextDouble textCotizacionDolarBl;
	
	private JTextDouble textSugerenciaPeso;
	private JTextDouble textSugerenciaDolar;

	private JButton btnEditarInforme;
	private JButton btnGuardarCambios;
	private JButton GuardarPresupuestoPDF;
	private JButton VisualizarPresupuestoPDF;
	private JTextField textSucursal;
	private JButton btnCotizacionDolar;
	private JButton btnGenerarInformeSiemens;

	private JCheckBox chckPesos;
	private JCheckBox chckDolar;
	
	private JCheckBox chckPDFgenerado;
	private JCheckBox chckPDFenviado;
	private JCheckBox chckWORDgenerado;
	private JCheckBox chckWORDenviado;
	
	
	private JTextFieldDateEditor textFabr;

	private ButtonGroup GrupoMoneda;
	
	

	public VentanaGenerarPresupuesto(ControladorPresupuestos controlador) {
		super();
		setResizable(false);
		this.controlador = controlador;
		
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 888, 706);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));
		panel.setBounds(216, 11, 648, 73);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblCliente = new JLabel("CLIENTE :");
		lblCliente.setForeground(new Color(0, 0, 0));
		lblCliente.setBounds(10, 25, 90, 22);
		panel.add(lblCliente);
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 18));

		JLabel lblEls = new JLabel("ELS :");
		lblEls.setForeground(new Color(0, 0, 0));
		lblEls.setBounds(10, 4, 46, 22);
		panel.add(lblEls);
		lblEls.setFont(new Font("Cambria", Font.BOLD, 18));

		textELS = new JTextField();
		textELS.setEditable(false);
		textELS.setForeground(new Color(0, 0, 0));
		textELS.setBounds(117, 4, 143, 22);
		panel.add(textELS);
		textELS.setBorder(null);
		textELS.setBackground(SystemColor.activeCaption);
		textELS.setFont(new Font("Cambria", Font.BOLD, 18));
		textELS.setColumns(10);

		
		
		textCliente = new JTextField();
		textCliente.setEditable(false);
		textCliente.setForeground(new Color(0, 0, 0));
		textCliente.setBounds(117, 25, 394, 22);
		panel.add(textCliente);
		textCliente.setBorder(null);
		textCliente.setBackground(SystemColor.activeCaption);
		textCliente.setFont(new Font("Cambria", Font.BOLD, 18));
		textCliente.setColumns(10);

		JLabel Sucursal = new JLabel("SUCURSAL: ");
		Sucursal.setForeground(Color.BLACK);
		Sucursal.setFont(new Font("Cambria", Font.BOLD, 18));
		Sucursal.setBounds(10, 47, 97, 22);
		panel.add(Sucursal);

		textSucursal = new JTextField();
		textSucursal.setForeground(Color.BLACK);
		textSucursal.setFont(new Font("Cambria", Font.BOLD, 18));
		textSucursal.setEditable(false);
		textSucursal.setColumns(10);
		textSucursal.setBorder(null);
		textSucursal.setBackground(SystemColor.activeCaption);
		textSucursal.setBounds(117, 47, 394, 22);
		panel.add(textSucursal);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));
		panel_1.setBounds(21, 95, 843, 109);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblEquipo = new JLabel("Equipo:");
		lblEquipo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblEquipo.setBounds(32, 11, 60, 20);
		panel_1.add(lblEquipo);
		lblEquipo.setForeground(Color.BLACK);
		lblEquipo.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblModelo.setBounds(32, 55, 60, 20);
		panel_1.add(lblModelo);
		lblModelo.setForeground(Color.BLACK);
		lblModelo.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblMarca.setBounds(32, 33, 60, 20);
		panel_1.add(lblMarca);
		lblMarca.setForeground(Color.BLACK);
		lblMarca.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblNSerie = new JLabel("N Serie:");
		lblNSerie.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblNSerie.setBounds(32, 77, 60, 20);
		panel_1.add(lblNSerie);
		lblNSerie.setForeground(Color.BLACK);
		lblNSerie.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblAviso = new JLabel("Aviso:");
		lblAviso.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblAviso.setBounds(496, 11, 60, 20);
		panel_1.add(lblAviso);
		lblAviso.setForeground(Color.BLACK);
		lblAviso.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblCiente = new JLabel("Ciente:");
		lblCiente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblCiente.setBounds(496, 33, 60, 20);
		panel_1.add(lblCiente);
		lblCiente.setForeground(Color.BLACK);
		lblCiente.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblAviso_1 = new JLabel("Remito Cliente: ");
		lblAviso_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblAviso_1.setBounds(496, 55, 121, 20);
		panel_1.add(lblAviso_1);
		lblAviso_1.setForeground(Color.BLACK);
		lblAviso_1.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblFechaFabr = new JLabel("Fecha Fabr. :");
		lblFechaFabr.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblFechaFabr.setBounds(496, 77, 108, 20);
		panel_1.add(lblFechaFabr);
		lblFechaFabr.setForeground(Color.BLACK);
		lblFechaFabr.setFont(new Font("Cambria", Font.BOLD, 15));

		textEquipo = new JTextField();
		textEquipo.setEditable(false);
		textEquipo.setForeground(Color.BLACK);
		textEquipo.setFont(new Font("Cambria", Font.PLAIN, 15));
		textEquipo.setColumns(10);
		textEquipo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textEquipo.setBackground(SystemColor.inactiveCaption);
		textEquipo.setBounds(101, 11, 341, 20);
		panel_1.add(textEquipo);

		textMarca = new JTextField();
		textMarca.setEditable(false);
		textMarca.setForeground(Color.BLACK);
		textMarca.setFont(new Font("Cambria", Font.PLAIN, 15));
		textMarca.setColumns(10);
		textMarca.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textMarca.setBackground(SystemColor.inactiveCaption);
		textMarca.setBounds(101, 33, 341, 20);
		panel_1.add(textMarca);

		textModelo = new JTextField();
		textModelo.setEditable(false);
		textModelo.setForeground(Color.BLACK);
		textModelo.setFont(new Font("Cambria", Font.PLAIN, 15));
		textModelo.setColumns(10);
		textModelo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textModelo.setBackground(SystemColor.inactiveCaption);
		textModelo.setBounds(101, 55, 341, 20);
		panel_1.add(textModelo);

		textSerie = new JTextField();
		textSerie.setEditable(false);
		textSerie.setForeground(Color.BLACK);
		textSerie.setFont(new Font("Cambria", Font.PLAIN, 15));
		textSerie.setColumns(10);
		textSerie.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textSerie.setBackground(SystemColor.inactiveCaption);
		textSerie.setBounds(101, 77, 341, 20);
		panel_1.add(textSerie);

		textAviso = new JTextField();
		textAviso.setEditable(false);
		textAviso.setForeground(Color.BLACK);
		textAviso.setFont(new Font("Cambria", Font.PLAIN, 15));
		textAviso.setColumns(10);
		textAviso.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textAviso.setBackground(SystemColor.inactiveCaption);
		textAviso.setBounds(618, 11, 191, 20);
		panel_1.add(textAviso);

		textClienteCliente = new JTextField();
		textClienteCliente.setEditable(false);
		textClienteCliente.setForeground(Color.BLACK);
		textClienteCliente.setFont(new Font("Cambria", Font.PLAIN, 15));
		textClienteCliente.setColumns(10);
		textClienteCliente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textClienteCliente.setBackground(SystemColor.inactiveCaption);
		textClienteCliente.setBounds(618, 33, 191, 20);
		panel_1.add(textClienteCliente);

		textRemCliente = new JTextField();
		textRemCliente.setEditable(false);
		textRemCliente.setForeground(Color.BLACK);
		textRemCliente.setFont(new Font("Cambria", Font.PLAIN, 15));
		textRemCliente.setColumns(10);
		textRemCliente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textRemCliente.setBackground(SystemColor.inactiveCaption);
		textRemCliente.setBounds(618, 55, 191, 20);
		panel_1.add(textRemCliente);
		
		
		textFabr = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		textFabr.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textFabr.setEditable(false);
		textFabr.setBounds(618, 80, 191, 20);
		panel_1.add(textFabr);
		textFabr.setHorizontalAlignment(SwingConstants.CENTER);
		textFabr.setBackground(SystemColor.inactiveCaption);
		textFabr.setForeground(Color.BLACK);
		textFabr.setFont(new Font("Cambria", Font.PLAIN, 15));

		
		
		JLabel lblInforme = new JLabel("INFORME :");
		lblInforme.setForeground(Color.BLACK);
		lblInforme.setFont(new Font("Cambria", Font.BOLD, 18));
		lblInforme.setBounds(20, 214, 115, 31);
		contentPane.add(lblInforme);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));
		scrollPane.setBounds(21, 243, 591, 132);
		contentPane.add(scrollPane);

		textInforme = new JTextArea();
		textInforme.setFont(new Font("Cambria", Font.PLAIN, 12));
		textInforme.setEditable(false);
		textInforme.setLocation(0, 241);
		textInforme.setBackground(Color.LIGHT_GRAY);
		textInforme.setLineWrap(true);
		textInforme.setWrapStyleWord(true);
		scrollPane.setViewportView(textInforme);

		JLabel lblPresupuesto = new JLabel("PRESUPUESTO");
		lblPresupuesto.setBounds(20, 11, 177, 73);
		contentPane.add(lblPresupuesto);
		lblPresupuesto.setBackground(SystemColor.activeCaption);
		lblPresupuesto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPresupuesto.setForeground(new Color(0, 0, 139));
		lblPresupuesto.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPresupuesto.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPresupuesto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPresupuesto.setFont(new Font("Cambria", Font.BOLD, 24));

		GrupoMoneda = new ButtonGroup();

		btnEditarInforme = new JButton("Editar Informe y Precio");
		btnEditarInforme.setFont(new Font("Cambria", Font.BOLD, 12));
		btnEditarInforme.setBounds(21, 386, 185, 23);
		contentPane.add(btnEditarInforme);

		btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.setFont(new Font("Cambria", Font.BOLD, 12));
		btnGuardarCambios.setBounds(221, 386, 185, 23);
		contentPane.add(btnGuardarCambios);
		
		GuardarPresupuestoPDF = new JButton("<html><center>Informe PDF</html>");
		GuardarPresupuestoPDF.setEnabled(false);
		GuardarPresupuestoPDF.setFont(new Font("Cambria", Font.BOLD, 12));
		GuardarPresupuestoPDF.setBounds(647, 541, 92, 38);
		contentPane.add(GuardarPresupuestoPDF);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_3.setBounds(21, 496, 620, 144);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		JScrollPane textCondicionesPesos = new JScrollPane();
		textCondicionesPesos.setBounds(157, 35, 440, 31);
		panel_3.add(textCondicionesPesos);
		textCondicionesPesos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));

		textcondicionesMoneda = new JTextArea();
		textcondicionesMoneda.setFont(new Font("Cambria", Font.PLAIN, 10));
		textcondicionesMoneda.setEditable(true);
		textcondicionesMoneda.setLocation(0, 241);
		textcondicionesMoneda.setBackground(Color.LIGHT_GRAY);
		textcondicionesMoneda.setLineWrap(true);
		textcondicionesMoneda.setWrapStyleWord(true);
		textCondicionesPesos.setViewportView(textcondicionesMoneda);

		JScrollPane condicionesPago = new JScrollPane();
		condicionesPago.setBounds(157, 68, 440, 31);
		panel_3.add(condicionesPago);
		condicionesPago.setFont(new Font("Cambria", Font.PLAIN, 10));
		condicionesPago.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));

		textcondicionesPago = new JTextArea();
		textcondicionesPago.setFont(new Font("Cambria", Font.PLAIN, 10));
		textcondicionesPago.setEditable(true);
		textcondicionesPago.setLocation(0, 241);
		textcondicionesPago.setBackground(Color.LIGHT_GRAY);
		textcondicionesPago.setLineWrap(true);
		textcondicionesPago.setWrapStyleWord(true);
		condicionesPago.setViewportView(textcondicionesPago);

		JScrollPane plazodeEntregaa = new JScrollPane();
		plazodeEntregaa.setBounds(157, 100, 440, 31);
		panel_3.add(plazodeEntregaa);
		plazodeEntregaa.setFont(new Font("Cambria", Font.PLAIN, 10));
		plazodeEntregaa.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));

		textPlazoEntrega = new JTextArea();
		textPlazoEntrega.setFont(new Font("Cambria", Font.PLAIN, 10));
		textPlazoEntrega.setEditable(true);
		textPlazoEntrega.setLocation(0, 241);
		textPlazoEntrega.setBackground(Color.LIGHT_GRAY);
		textPlazoEntrega.setLineWrap(true);
		textPlazoEntrega.setWrapStyleWord(true);
		plazodeEntregaa.setViewportView(textPlazoEntrega);

		JLabel CondicionesMoneda = new JLabel("CONDICIONES EN MONEDA :");
		CondicionesMoneda.setBounds(12, 35, 139, 31);
		panel_3.add(CondicionesMoneda);
		CondicionesMoneda.setForeground(Color.BLACK);
		CondicionesMoneda.setFont(new Font("Cambria", Font.BOLD, 10));

		JLabel lblCondicionesDePago = new JLabel("CONDICIONES DE PAGO :");
		lblCondicionesDePago.setBounds(12, 68, 139, 31);
		panel_3.add(lblCondicionesDePago);
		lblCondicionesDePago.setForeground(Color.BLACK);
		lblCondicionesDePago.setFont(new Font("Cambria", Font.BOLD, 10));

		JLabel lblPlazoDeEntrega = new JLabel("PLAZO DE ENTREGA :");
		lblPlazoDeEntrega.setBounds(12, 100, 139, 31);
		panel_3.add(lblPlazoDeEntrega);
		lblPlazoDeEntrega.setForeground(Color.BLACK);
		lblPlazoDeEntrega.setFont(new Font("Cambria", Font.BOLD, 10));

		chckPesos = new JCheckBox("PRESUPUESTO EN PESOS");
		chckPesos.setBounds(6, 11, 152, 17);
		panel_3.add(chckPesos);
		chckPesos.setFont(new Font("Cambria", Font.BOLD, 10));
		chckPesos.setHorizontalAlignment(SwingConstants.LEFT);
		chckPesos.setBackground(SystemColor.activeCaption);
		GrupoMoneda.add(chckPesos);

		chckDolar = new JCheckBox("PRESUPUESTO EN DÓLARES");
		chckDolar.setBounds(160, 11, 168, 17);
		panel_3.add(chckDolar);
		chckDolar.setHorizontalAlignment(SwingConstants.LEFT);
		chckDolar.setFont(new Font("Cambria", Font.BOLD, 10));
		chckDolar.setBackground(SystemColor.activeCaption);
		GrupoMoneda.add(chckDolar);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.inactiveCaptionText);
		separator_1.setBounds(79, 436, 726, 2);
		contentPane.add(separator_1);

		JLabel lblCondicionesDelPresupuesto = new JLabel("CONDICIONES DEL PRESUPUESTO");
		lblCondicionesDelPresupuesto.setBounds(21, 442, 282, 23);
		contentPane.add(lblCondicionesDelPresupuesto);
		lblCondicionesDelPresupuesto.setForeground(Color.BLACK);
		lblCondicionesDelPresupuesto.setFont(new Font("Cambria", Font.BOLD, 18));

		panel_4 = new JPanel();
		panel_4.setBounds(621, 380, 243, 24);
		contentPane.add(panel_4);
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_4.setLayout(null);

		textPrecioPeso = new JTextField(10);
		textPrecioPeso.setEditable(false);
		textPrecioPeso.setBounds(108, 4, 125, 15);
		panel_4.add(textPrecioPeso);
		textPrecioPeso.setBorder(null);
		textPrecioPeso.setBackground(Color.LIGHT_GRAY);
		textPrecioPeso.setFont(new Font("Cambria", Font.BOLD, 12));
		textPrecioPeso.setColumns(10);

		JLabel lblTotalPesos = new JLabel("Total en Pesos:");
		lblTotalPesos.setBounds(10, 4, 104, 15);
		panel_4.add(lblTotalPesos);
		lblTotalPesos.setBorder(null);
		lblTotalPesos.setFont(new Font("Cambria", Font.PLAIN, 12));

		panel_5 = new JPanel();
		panel_5.setBounds(621, 407, 243, 24);
		contentPane.add(panel_5);
		panel_5.setBackground(Color.LIGHT_GRAY);
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_5.setLayout(null);

		textPrecioDolar = new JTextField(10);
		textPrecioDolar.setEditable(false);
		textPrecioDolar.setBounds(108, 5, 125, 15);
		panel_5.add(textPrecioDolar);
		textPrecioDolar.setBorder(null);
		textPrecioDolar.setBackground(Color.LIGHT_GRAY);
		textPrecioDolar.setFont(new Font("Cambria", Font.BOLD, 12));
		textPrecioDolar.setColumns(10);

		JLabel lbltotaldolares = new JLabel("Total en Dolares:");
		lbltotaldolares.setBounds(10, 5, 115, 15);
		panel_5.add(lbltotaldolares);
		lbltotaldolares.setBorder(null);
		lbltotaldolares.setFont(new Font("Cambria", Font.PLAIN, 12));

		JLabel avisoDelpresupuesto = new JLabel(
				"Estos datos no se guardarán en la base. Serán utilizados solo una véz al generar el PDF correspondiente. ");
		avisoDelpresupuesto.setFont(new Font("Cambria", Font.PLAIN, 11));
		avisoDelpresupuesto.setForeground(new Color(47, 79, 79));
		avisoDelpresupuesto.setBounds(21, 469, 487, 14);
		contentPane.add(avisoDelpresupuesto);

		VisualizarPresupuestoPDF = new JButton("Visualizar PDF");
		VisualizarPresupuestoPDF.setEnabled(false);
		VisualizarPresupuestoPDF.setFont(new Font("Cambria", Font.BOLD, 12));
		VisualizarPresupuestoPDF.setBounds(647, 496, 114, 23);
		contentPane.add(VisualizarPresupuestoPDF);
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setLayout(null);
		panel_4_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_4_1.setBackground(Color.LIGHT_GRAY);
		panel_4_1.setBounds(622, 243, 242, 132);
		contentPane.add(panel_4_1);
		
				btnCotizacionDolar = new JButton("<html>COTIZACIÓN DEL DOLAR</html>");
				btnCotizacionDolar.setBounds(10, 11, 87, 51);
				panel_4_1.add(btnCotizacionDolar);
				btnCotizacionDolar.setFont(new Font("Cambria", Font.BOLD, 12));
				
				textCotizacionDolarOf = new JTextDouble(10);
				textCotizacionDolarOf.setBounds(163, 11, 69, 20);
				panel_4_1.add(textCotizacionDolarOf);
				textCotizacionDolarOf.setFont(new Font("Cambria", Font.BOLD, 14));
				textCotizacionDolarOf.setEditable(false);
				textCotizacionDolarOf.setColumns(10);
				textCotizacionDolarOf.setBorder(null);
				textCotizacionDolarOf.setBackground(Color.LIGHT_GRAY);
				
				JLabel lblsugerenciaEnPesos = new JLabel("<html>SUGERENCIA EN PESOS</html>");
				lblsugerenciaEnPesos.setBounds(22, 73, 69, 31);
				panel_4_1.add(lblsugerenciaEnPesos);
				lblsugerenciaEnPesos.setHorizontalAlignment(SwingConstants.CENTER);
				lblsugerenciaEnPesos.setForeground(new Color(47, 79, 79));
				lblsugerenciaEnPesos.setFont(new Font("Cambria", Font.PLAIN, 11));
				
				textSugerenciaPeso = new JTextDouble(10);
				textSugerenciaPeso.setBounds(33, 106, 69, 20);
				panel_4_1.add(textSugerenciaPeso);
				textSugerenciaPeso.setFont(new Font("Cambria", Font.BOLD, 14));
				textSugerenciaPeso.setEditable(false);
				textSugerenciaPeso.setColumns(10);
				textSugerenciaPeso.setBorder(null);
				textSugerenciaPeso.setBackground(Color.LIGHT_GRAY);
				
				JLabel label_2_1 = new JLabel("$");
				label_2_1.setBounds(22, 106, 20, 20);
				panel_4_1.add(label_2_1);
				label_2_1.setFont(new Font("Cambria", Font.BOLD, 14));
				
				JLabel lblsugerenciaEnDolares = new JLabel("<html>SUGERENCIA EN DÓLARES</html>");
				lblsugerenciaEnDolares.setBounds(121, 73, 69, 31);
				panel_4_1.add(lblsugerenciaEnDolares);
				lblsugerenciaEnDolares.setHorizontalAlignment(SwingConstants.CENTER);
				lblsugerenciaEnDolares.setForeground(new Color(47, 79, 79));
				lblsugerenciaEnDolares.setFont(new Font("Cambria", Font.PLAIN, 11));
				
				JLabel lblUs_2 = new JLabel("US$");
				lblUs_2.setBounds(121, 106, 30, 20);
				panel_4_1.add(lblUs_2);
				lblUs_2.setFont(new Font("Cambria", Font.BOLD, 14));
				
				textSugerenciaDolar = new JTextDouble(10);
				textSugerenciaDolar.setBounds(149, 106, 69, 20);
				panel_4_1.add(textSugerenciaDolar);
				textSugerenciaDolar.setFont(new Font("Cambria", Font.BOLD, 14));
				textSugerenciaDolar.setEditable(false);
				textSugerenciaDolar.setColumns(10);
				textSugerenciaDolar.setBorder(null);
				textSugerenciaDolar.setBackground(Color.LIGHT_GRAY);
				
				JLabel lblOficial = new JLabel("<html>OFICIAL</html>");
				lblOficial.setBounds(107, 17, 45, 14);
				panel_4_1.add(lblOficial);
				lblOficial.setHorizontalAlignment(SwingConstants.LEFT);
				lblOficial.setForeground(new Color(47, 79, 79));
				lblOficial.setFont(new Font("Cambria", Font.PLAIN, 11));
				
				JLabel lblblue = new JLabel("BLUE");
				lblblue.setBounds(107, 48, 45, 14);
				panel_4_1.add(lblblue);
				lblblue.setHorizontalAlignment(SwingConstants.LEFT);
				lblblue.setForeground(new Color(47, 79, 79));
				lblblue.setFont(new Font("Cambria", Font.PLAIN, 11));
				
				textCotizacionDolarBl = new JTextDouble(10);
				textCotizacionDolarBl.setFont(new Font("Cambria", Font.BOLD, 14));
				textCotizacionDolarBl.setEditable(false);
				textCotizacionDolarBl.setColumns(10);
				textCotizacionDolarBl.setBorder(null);
				textCotizacionDolarBl.setBackground(Color.LIGHT_GRAY);
				textCotizacionDolarBl.setBounds(163, 42, 69, 20);
				panel_4_1.add(textCotizacionDolarBl);
				
				JSeparator separator = new JSeparator();
				separator.setBounds(107, 32, 125, 2);
				panel_4_1.add(separator);
				
				JSeparator separator_2 = new JSeparator();
				separator_2.setBounds(107, 62, 125, 2);
				panel_4_1.add(separator_2);
				
				btnGenerarInformeSiemens = new JButton("<html><center>Informe WORD</html>");
				btnGenerarInformeSiemens.setEnabled(false);
				btnGenerarInformeSiemens.setFont(new Font("Cambria", Font.BOLD, 12));
				btnGenerarInformeSiemens.setBounds(647, 602, 92, 38);
				contentPane.add(btnGenerarInformeSiemens);
				
				chckPDFgenerado = new JCheckBox("");
				chckPDFgenerado.setEnabled(false);
				chckPDFgenerado.setHorizontalAlignment(SwingConstants.LEFT);
				chckPDFgenerado.setFont(new Font("Cambria", Font.BOLD, 9));
				chckPDFgenerado.setBackground(UIManager.getColor("inactiveCaption"));
				chckPDFgenerado.setBounds(745, 541, 21, 17);
				contentPane.add(chckPDFgenerado);
				
				chckPDFenviado = new JCheckBox("");
				chckPDFenviado.setEnabled(false);
				chckPDFenviado.setHorizontalAlignment(SwingConstants.LEFT);
				chckPDFenviado.setFont(new Font("Cambria", Font.BOLD, 9));
				chckPDFenviado.setBackground(UIManager.getColor("inactiveCaption"));
				chckPDFenviado.setBounds(745, 562, 21, 17);
				contentPane.add(chckPDFenviado);
				
				chckWORDgenerado = new JCheckBox("");
				chckWORDgenerado.setEnabled(false);
				chckWORDgenerado.setHorizontalAlignment(SwingConstants.LEFT);
				chckWORDgenerado.setFont(new Font("Cambria", Font.BOLD, 9));
				chckWORDgenerado.setBackground(UIManager.getColor("inactiveCaption"));
				chckWORDgenerado.setBounds(745, 602, 21, 17);
				contentPane.add(chckWORDgenerado);
				
				chckWORDenviado = new JCheckBox("");
				chckWORDenviado.setEnabled(false);
				chckWORDenviado.setHorizontalAlignment(SwingConstants.LEFT);
				chckWORDenviado.setFont(new Font("Cambria", Font.BOLD, 9));
				chckWORDenviado.setBackground(UIManager.getColor("inactiveCaption"));
				chckWORDenviado.setBounds(745, 623, 21, 17);
				contentPane.add(chckWORDenviado);
				
				JLabel lblNewLabel = new JLabel("PDF GENERADO");
				lblNewLabel.setForeground(new Color(0, 0, 51));
				lblNewLabel.setFont(new Font("Cambria", Font.PLAIN, 11));
				lblNewLabel.setBounds(772, 542, 90, 14);
				contentPane.add(lblNewLabel);
				
				JLabel lblPdfEnviado = new JLabel("PDF ENVIADO");
				lblPdfEnviado.setForeground(new Color(0, 0, 51));
				lblPdfEnviado.setFont(new Font("Cambria", Font.PLAIN, 11));
				lblPdfEnviado.setBounds(772, 562, 90, 14);
				contentPane.add(lblPdfEnviado);
				
				JLabel lblWordGenerado = new JLabel("WORD GENERADO");
				lblWordGenerado.setForeground(new Color(0, 0, 51));
				lblWordGenerado.setFont(new Font("Cambria", Font.PLAIN, 11));
				lblWordGenerado.setBounds(772, 602, 90, 14);
				contentPane.add(lblWordGenerado);
				
				JLabel lblWordEnviado = new JLabel("WORD ENVIADO");
				lblWordEnviado.setForeground(new Color(0, 0, 51));
				lblWordEnviado.setFont(new Font("Cambria", Font.PLAIN, 11));
				lblWordEnviado.setBounds(772, 622, 90, 14);
				contentPane.add(lblWordEnviado);

		this.setVisible(true);

	}

	public JButton getBtnGenerarInformeSiemens() {
		return btnGenerarInformeSiemens;
	}

	public void setBtnGenerarInformeSiemens(JButton btnGenerarInformeSiemens) {
		this.btnGenerarInformeSiemens = btnGenerarInformeSiemens;
	}

	public JTextField getTextSucursal() {
		return textSucursal;
	}

	public void setTextSucursal(JTextField textSucursal) {
		this.textSucursal = textSucursal;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public void setBtnGuardarCambios(JButton btnGuardarCambios) {
		this.btnGuardarCambios = btnGuardarCambios;
	}

	public JTextArea getTextInforme() {
		return textInforme;
	}

	public void setTextInforme(JTextArea textInforme) {
		this.textInforme = textInforme;
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

	public JTextField getTextAviso() {
		return textAviso;
	}

	public void setTextAviso(JTextField textAviso) {
		this.textAviso = textAviso;
	}

	public JTextField getTextClienteCliente() {
		return textClienteCliente;
	}

	public void setTextClienteCliente(JTextField textClienteCliente) {
		this.textClienteCliente = textClienteCliente;
	}

	public JTextField getTextRemCliente() {
		return textRemCliente;
	}

	public void setTextRemCliente(JTextField textRemCliente) {
		this.textRemCliente = textRemCliente;
	}


	public JTextField getTextCliente() {
		return textCliente;
	}

	public void setTextCliente(JTextField textCliente) {
		this.textCliente = textCliente;
	}

	public JTextField getTextELS() {
		return textELS;
	}

	public void setTextELS(JTextField textELS) {
		this.textELS = textELS;
	}

	public JTextField getTextPrecioPeso() {
		return textPrecioPeso;
	}

	public void setTextPrecioPeso(String textPrecioPeso) {
		this.textPrecioPeso.setText(textPrecioPeso);
	}

	public JTextField getTextPrecioDolar() {
		return textPrecioDolar;
	}

	public void setTextPrecioDolar(String textPrecioDolar) {
		this.textPrecioDolar.setText(textPrecioDolar);
	}

	public JButton getBtnEditarInforme() {
		return btnEditarInforme;
	}

	public void setBtnEditarInforme(JButton btnEditarInforme) {
		this.btnEditarInforme = btnEditarInforme;
	}

	public JButton getGuardarPresupuestoPDF() {
		return GuardarPresupuestoPDF;
	}

	public void setGuardarPresupuestoPDF(JButton guardarPresupuestoPDF) {
		GuardarPresupuestoPDF = guardarPresupuestoPDF;
	}

	public JPanel getPanel_4() {
		return panel_4;
	}

	public void setPanel_4(JPanel panel_4) {
		this.panel_4 = panel_4;
	}

	public JPanel getPanel_5() {
		return panel_5;
	}

	public void setPanel_5(JPanel panel_5) {
		this.panel_5 = panel_5;
	}

	public JCheckBox getChckPesos() {
		return chckPesos;
	}

	public void setChckPesos(JCheckBox chckPesos) {
		this.chckPesos = chckPesos;
	}

	public JCheckBox getChckDolar() {
		return chckDolar;
	}

	public void setChckDolar(JCheckBox chckDolar) {
		this.chckDolar = chckDolar;
	}

	public JTextArea getTextcondicionesMoneda() {
		return textcondicionesMoneda;
	}

	public void setTextcondicionesMoneda(JTextArea textcondicionesMoneda) {
		this.textcondicionesMoneda = textcondicionesMoneda;
	}

	public ButtonGroup getGrupoMoneda() {
		return GrupoMoneda;
	}

	public void setGrupoMoneda(ButtonGroup grupoMoneda) {
		GrupoMoneda = grupoMoneda;
	}

	public JTextArea getTextcondicionesPago() {
		return textcondicionesPago;
	}

	public void setTextcondicionesPago(JTextArea textcondicionesPago) {
		this.textcondicionesPago = textcondicionesPago;
	}

	public JTextArea getTextPlazoEntrega() {
		return textPlazoEntrega;
	}

	public void setTextPlazoEntrega(JTextArea textPlazoEntrega) {
		this.textPlazoEntrega = textPlazoEntrega;
	}

	public JButton getVisualizarPresupuestoPDF() {
		return VisualizarPresupuestoPDF;
	}

	public void setVisualizarPresupuestoPDF(JButton visualizarPresupuestoPDF) {
		VisualizarPresupuestoPDF = visualizarPresupuestoPDF;
	}

	
	
	
//	public JDateChooser getTextFabr2() {
//		return textFabr;
//	}
//
//	public void setTextFabr2(JDateChooser textFabr) {
//		this.textFabr = textFabr;
//	}
	
	public Date getTextFabr() {
		return (Date) textFabr.getDate();

	}
	
	public String getTextFabrString() {
		return textFabr.getText();

	}
	
	public void setTextFabr(java.util.Date date) {
		this.textFabr.setDate(date);
	}

	public JButton getBtnCotizacionDolar() {
		return btnCotizacionDolar;
	}

	public void setBtnCotizacionDolar(JButton btnCotizacionDolar) {
		this.btnCotizacionDolar = btnCotizacionDolar;
	}

//	public JTextDouble getTextCotizacionDolar() {
//		return textCotizacionDolarOf;
//	}
//
//	public void setTextCotizacionDolar(String textCotizacionDolar) {
//		this.textCotizacionDolarOf.setText(textCotizacionDolar);
//	}

	public JTextDouble getTextSugerenciaPeso() {
		return textSugerenciaPeso;
	}

	public void setTextSugerenciaPeso(String textSugerenciaPeso) {
		this.textSugerenciaPeso.setText(textSugerenciaPeso);
	}

	public JTextDouble getTextSugerenciaDolar() {
		return textSugerenciaDolar;
	}

	public void setTextSugerenciaDolar(String textSugerenciaDolar) {
		this.textSugerenciaDolar.setText(textSugerenciaDolar);
	}

	public boolean getChckPDFGenerado() {
		return chckPDFgenerado.isSelected();
	}

	public void setChckPDFGenerado(Boolean enviado) {
		chckPDFgenerado.setSelected(enviado);
	}

	public boolean getChckPDFEnviado() {
		return chckPDFenviado.isSelected();
	}

	public void setChckPDFEnviado(Boolean PresupuestoEnviado) {
		chckPDFenviado.setSelected(PresupuestoEnviado);
	}

	public boolean getChckWORDGenerado() {
		return chckWORDgenerado.isSelected();
	}

	public void setChckWORDGenerado(Boolean Generado) {
		chckWORDgenerado.setSelected(Generado);
	}

	public boolean getChckWORDEnviado() {
		return chckWORDenviado.isSelected();
	}

	public void setChckWORDEnviado(Boolean Enviado) {
		chckWORDenviado.setSelected(Enviado);
	}

	public JTextDouble getTextCotizacionDolarOf() {
		return textCotizacionDolarOf;
	}

	public void setTextCotizacionDolarOf(String textCotizacionDolarOf) {
		this.textCotizacionDolarOf.setText(textCotizacionDolarOf);
	}
	
	
	public JTextDouble getTextCotizacionDolarBl() {
		return textCotizacionDolarBl;
	}

	public void setTextCotizacionDolarBl(String textCotizacionDolarBl) {
		this.textCotizacionDolarBl.setText(textCotizacionDolarBl);
	}
}
