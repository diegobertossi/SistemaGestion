package presentacion.vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.sql.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import presentacion.controlador.ControladorReparacion;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.ActionEvent;

public class VentanaVisualizarEquipos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton BotonAnterior;
	private JButton BotonSiguiente;
	private JButton BotonPrimero;
	private JButton BotonUltimo;
	private JButton btnEditar;
	private JButton btnRepuestos;
	private JButton btnEliminarRepuesto;

	private JButton btnGuardarCambios;
	private JButton BotonRegistroIngreso;
	private JButton BotonEditarEstados;
	private JButton BotonAvisoEquipoListo;
	private JButton BotonRespuestaAlTecnico;
	private JButton BotonAvisoInforme;
	private JButton BotonPresupuestar;
	private JButton btnfacturar;
	private JButton btnabrirExcel;
	
	private JButton btnGenerarRemito;

	private JButton btnenviarCorreoOwsp;

	private JButton btnBuscarELS;

	private JButton btnBuscar;

	private JCheckBox chckbxAvisoEnviado;
	private JCheckBox chckPDFgenerado;
	private JCheckBox chckPDFenviado;
	private JCheckBox chckWORDgenerado;
	private JCheckBox chckWORDenviado;

	private JTextField textELS;
	private int ELS = 1;
	private JTextField textNombreEquipo;
	private JTextField textMarca;
	private JTextField textModelo;
	private JTextField textNSerie;
	private JTextArea textFalla;
	private JTextField textAvisoCliente;
	private JTextField textClienteCliente;
	private JTextField textRemitoCliente;
	private JTextField textCliente;
	private JTextField textSucursal;
	private JDateChooser FechaEntrada;
	private JDateChooser FechaSalida;
	private JTextField textEstadoFisico;

	private JTextField textEstadoComercial;
	private JTextField textEstadoTecnico;
	private JTextArea textDiagnostico;
	private JTextArea textInformeCliente;
	private JTextField textNombreTecnico;
	private JDateChooser FechaReparacion;
	private JDateChooser FechaRespuesta;
	private JTextField textOC;

	@SuppressWarnings("rawtypes")
	private JComboBox comboClientes;
	@SuppressWarnings("rawtypes")
	private JComboBox comboSucursal;
	@SuppressWarnings("rawtypes")
	private JComboBox comboTecnico;

	@SuppressWarnings("rawtypes")
	private JComboBox comboELS;

	private JLabel lblEls;
	private JLabel lblNombreEquipo;
	private JLabel lblMarca;
	private JLabel lblModelo;
	private JLabel lblNserie;
	private JLabel lblFalla;
	private JLabel lblAvisoCliente;
	private JLabel lblClienteCliente;
	private JLabel lblRemitoCliente;
	private JLabel lblCliente;
	private JLabel lblSucursal;
	private JLabel lblDatosDeEquipo;
	private JLabel lblFechaEntrada;
	private JLabel lblEstadoFisico;
	private JLabel lblEstadoComercial;
	private JLabel lblEstadoTecnico;
	private JLabel lblDiagnosticoReparacion;
	private JLabel lblDiagnostico;
	private JLabel lblInformeCliente;
	private JLabel lblFechaReparacion;
	private JLabel lblNombreTecnico;
	private JLabel lblRepuestos;
	private JLabel lblFechaRespuesta;
	private JLabel lblSalida;
	private JLabel lblOC;
	private JLabel lblRemitoDeSalida;
	private JLabel labelPresupuesto;

	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	@SuppressWarnings("unused")
	private ControladorReparacion controladorReparacion;
	private JPanel panel_2;
	private JPanel panel_3;
	@SuppressWarnings("unused")
	private JTable tablaRepuestos;
	private JTable tablaRepuestos_1;
	@SuppressWarnings("unused")
	private JScrollPane scrollPane_3;

	private DefaultTableModel modelRepuestos;
	private String[] nombreColumnas = { "REFERENCIA", "ORIGINAL", "REEMPLAZO", "NOTA" };
	private JTextField textUbicacionRemito;
	private JTextField textNumeroRemito;
	private JPanel panel_4;
	private JPanel panel_5;
	private JSeparator separator;
	private JSeparator separator_1;
	private JPanel panel_6;
	private JSeparator separator_2;
	private JPanel panel_presupuesto;
	private JPanel panel_8;
	private JPanel panel_9;
	private JLabel lblFechaFabr;
	private JTextFieldDateEditor FechaFabr;
//	private JTextDouble textPresupuesto;
//	private JTextDouble textPago;
	private JTextField textPresupuesto;
	private JTextField textPago;
	private JLabel lblPago;
	private JPanel panel_MontoPresupuesto;
	private JTextField textEquipoPagado;
	private JPanel panel_7;
	private JSeparator separator_6;
	private JSeparator separator_7;
	private JSeparator separator_8;
	private JSeparator separator_9;
	private JLabel lblInformeSiemensGenerado;
	private JLabel lblInformeSiemensEnviado;
	private JTextField textPresupuestoDolar;
	private JTextField textLugarDeIngreso;
	private JLabel lblFechaDeSalida;
	private JSeparator separator_11;
	private JSeparator separator_12;
	private JSeparator separator_13;
	private JSeparator separator_14;
	private JSeparator separator_5;
	private JSeparator separator_10;
	private JPanel PanelDatosEquipo;
	private JPanel panelDatosCliente;
	private JPanel panelDatosExtrasCliente;
	private JButton btnCopiarPresupuesto;

	@SuppressWarnings({ "rawtypes", "serial" })
	public VentanaVisualizarEquipos(ControladorReparacion controladorReparacion) {

		super();
		setResizable(false);
		this.controladorReparacion = controladorReparacion;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		configurarAtajoGuardar();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1195, 701);
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
		panel.setBounds(0, 0, 1188, 664);
		contentPane.add(panel);
		panel.setLayout(null);
	

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		panel_5 = new JPanel();
		panel_5.setBackground(SystemColor.activeCaption);
		panel_5.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_5.setBounds(834, 491, 346, 167);
		panel.add(panel_5);
		panel_5.setLayout(null);

		panel_9 = new JPanel();
		panel_9.setBackground(SystemColor.activeCaption);
		panel_9.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE));
		panel_9.setBounds(16, 11, 314, 64);
		panel_5.add(panel_9);
		panel_9.setLayout(null);

		btnEditar = new JButton("EDITAR");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBounds(8, 11, 90, 41);
		panel_9.add(btnEditar);
		btnEditar.setBackground(SystemColor.textHighlight);
		btnEditar.setForeground(SystemColor.text);
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 14));

		btnGuardarCambios = new JButton("<html><center>GUARDAR CAMBIOS</html>");
		btnGuardarCambios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardarCambios.setBounds(106, 11, 90, 41);
		panel_9.add(btnGuardarCambios);
		btnGuardarCambios.setBackground(SystemColor.textHighlight);
		btnGuardarCambios.setEnabled(false);
		btnGuardarCambios.setForeground(SystemColor.text);
		btnGuardarCambios.setFont(new Font("Cambria", Font.BOLD, 14));

		BotonRegistroIngreso = new JButton("<html><center>REGISTRO INGRESO</html>\r\n");
		BotonRegistroIngreso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonRegistroIngreso.setBounds(204, 11, 100, 41);
		panel_9.add(BotonRegistroIngreso);
		BotonRegistroIngreso.setBackground(SystemColor.textHighlight);
		BotonRegistroIngreso.setForeground(SystemColor.text);
		BotonRegistroIngreso.setFont(new Font("Cambria", Font.BOLD, 14));

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE));
		panel_10.setBackground(SystemColor.inactiveCaption);
		panel_10.setBounds(16, 110, 314, 54);
		panel_5.add(panel_10);
		panel_10.setLayout(null);

		BotonAnterior = new JButton("");
		BotonAnterior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonAnterior.setBounds(88, 6, 65, 41);
		panel_10.add(BotonAnterior);
		BotonAnterior.setIcon(new ImageIcon(this.getClass().getResource("/anterior.png")));
		BotonAnterior.setFont(new Font("Verdana", Font.BOLD, 11));

		BotonPrimero = new JButton("");
		BotonPrimero.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonPrimero.setBounds(16, 6, 65, 41);
		panel_10.add(BotonPrimero);
		BotonPrimero.setIcon(new ImageIcon(this.getClass().getResource("/primero.png")));
		BotonPrimero.setFont(new Font("Verdana", Font.BOLD, 11));

		BotonUltimo = new JButton("");
		BotonUltimo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonUltimo.setBounds(232, 6, 65, 41);
		panel_10.add(BotonUltimo);
		BotonUltimo.setIcon(new ImageIcon(this.getClass().getResource("/ultimo.png")));
		BotonUltimo.setFont(new Font("Verdana", Font.BOLD, 11));

		BotonSiguiente = new JButton("");
		BotonSiguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonSiguiente.setBounds(160, 6, 65, 41);
		panel_10.add(BotonSiguiente);
		BotonSiguiente.setIcon(new ImageIcon(this.getClass().getResource("/siguiente.png")));
		BotonSiguiente.setFont(new Font("Verdana", Font.BOLD, 11));

		panel_6 = new JPanel();
		panel_6.setBackground(SystemColor.activeCaption);
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setBounds(11, 6, 181, 32);
		panel.add(panel_6);
		panel_6.setLayout(null);

		lblEls = new JLabel("ELS: ");
		lblEls.setBounds(10, 5, 55, 20);
		panel_6.add(lblEls);
		lblEls.setForeground(Color.BLUE);
		lblEls.setFont(new Font("Cambria", Font.BOLD, 24));

		textELS = new JTextField();
		textELS.setBounds(73, 5, 97, 20);
		panel_6.add(textELS);
		textELS.setBorder(null);
		textELS.setBackground(SystemColor.activeCaption);
		textELS.setForeground(Color.BLUE);
		textELS.setHorizontalAlignment(SwingConstants.CENTER);
		textELS.setFont(new Font("Cambria", Font.BOLD, 24));
		textELS.setColumns(10);
		textELS.setText(Integer.toString(ELS));
		textELS.setEditable(false);
		textELS.setAlignmentX(CENTER_ALIGNMENT);

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBounds(11, 110, 355, 353);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		PanelDatosEquipo = new JPanel();
		PanelDatosEquipo.setBackground(SystemColor.activeCaption);
		PanelDatosEquipo.setBorder(new EmptyBorder(10, 10, 10, 10));
		PanelDatosEquipo.setBounds(12, 63, 332, 128);
		panel_1.add(PanelDatosEquipo);
		PanelDatosEquipo.setLayout(null);

		lblNombreEquipo = new JLabel("Equipo: ");
		lblNombreEquipo.setBounds(0, 7, 65, 14);
		PanelDatosEquipo.add(lblNombreEquipo);
		lblNombreEquipo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
		lblNombreEquipo.setFont(new Font("Cambria", Font.BOLD, 14));

		lblMarca = new JLabel("Marca:  ");
		lblMarca.setBounds(0, 31, 119, 14);
		PanelDatosEquipo.add(lblMarca);
		lblMarca.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
		lblMarca.setFont(new Font("Cambria", Font.BOLD, 14));

		lblModelo = new JLabel("Modelo: ");
		lblModelo.setBounds(0, 55, 119, 14);
		PanelDatosEquipo.add(lblModelo);
		lblModelo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
		lblModelo.setFont(new Font("Cambria", Font.BOLD, 14));

		lblNserie = new JLabel("N\u00B0 de Serie: ");
		lblNserie.setBounds(0, 79, 119, 14);
		PanelDatosEquipo.add(lblNserie);
		lblNserie.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
		lblNserie.setFont(new Font("Cambria", Font.BOLD, 14));
								
										textNombreEquipo = new JTextField();
										textNombreEquipo.setBounds(67, 4, 265, 20);
										PanelDatosEquipo.add(textNombreEquipo);
										textNombreEquipo.setBackground(Color.WHITE);
										textNombreEquipo.setAlignmentY(Component.TOP_ALIGNMENT);
										textNombreEquipo.setAlignmentX(Component.LEFT_ALIGNMENT);
										textNombreEquipo.setHorizontalAlignment(SwingConstants.LEFT);
										textNombreEquipo.setFont(new Font("Cambria", Font.PLAIN, 14));
										textNombreEquipo.setEditable(false);
										textNombreEquipo.setColumns(10);
										
												textModelo = new JTextField();
												textModelo.setBounds(121, 52, 211, 20);
												PanelDatosEquipo.add(textModelo);
												textModelo.setBackground(Color.WHITE);
												textModelo.setAlignmentY(Component.TOP_ALIGNMENT);
												textModelo.setAlignmentX(Component.LEFT_ALIGNMENT);
												textModelo.setHorizontalAlignment(SwingConstants.LEFT);
												textModelo.setFont(new Font("Cambria", Font.PLAIN, 14));
												textModelo.setEditable(false);
												textModelo.setColumns(10);
												
														textMarca = new JTextField();
														textMarca.setBounds(121, 28, 211, 20);
														PanelDatosEquipo.add(textMarca);
														textMarca.setBackground(Color.WHITE);
														textMarca.setAlignmentY(Component.TOP_ALIGNMENT);
														textMarca.setAlignmentX(Component.LEFT_ALIGNMENT);
														textMarca.setHorizontalAlignment(SwingConstants.LEFT);
														textMarca.setFont(new Font("Cambria", Font.PLAIN, 14));
														textMarca.setEditable(false);
														textMarca.setColumns(10);
														
																textNSerie = new JTextField();
																textNSerie.setBounds(121, 76, 211, 20);
																PanelDatosEquipo.add(textNSerie);
																textNSerie.setBackground(Color.WHITE);
																textNSerie.setAlignmentY(Component.TOP_ALIGNMENT);
																textNSerie.setAlignmentX(Component.LEFT_ALIGNMENT);
																textNSerie.setHorizontalAlignment(SwingConstants.LEFT);
																textNSerie.setFont(new Font("Cambria", Font.PLAIN, 14));
																textNSerie.setEditable(false);
																textNSerie.setColumns(10);
																						
																						
																						

																						lblFechaFabr = new JLabel("Fecha Fabr: ");
																						lblFechaFabr.setBounds(0, 103, 119, 14);
																						PanelDatosEquipo.add(lblFechaFabr);
																						lblFechaFabr.setFont(new Font("Cambria", Font.BOLD, 14));
																						lblFechaFabr.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
																						
																								FechaFabr = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
																								FechaFabr.setBounds(121, 100, 211, 20);
																								PanelDatosEquipo.add(FechaFabr);
																								FechaFabr.setBackground(Color.WHITE);
																								FechaFabr.setHorizontalAlignment(SwingConstants.CENTER);
																								FechaFabr.setFont(new Font("Cambria", Font.PLAIN, 14));

		lblFalla = new JLabel("Falla: ");
		lblFalla.setBounds(12, 276, 50, 14);
		panel_1.add(lblFalla);
		lblFalla.setFont(new Font("Cambria", Font.BOLD, 14));
		
		panelDatosCliente = new JPanel();
		panelDatosCliente.setBackground(SystemColor.activeCaption);
		panelDatosCliente.setBounds(11, 9, 333, 44);
		panel_1.add(panelDatosCliente);
		panelDatosCliente.setLayout(null);

		lblCliente = new JLabel("Cliente: ");
		lblCliente.setBounds(1, 3, 65, 14);
		panelDatosCliente.add(lblCliente);
		lblCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		lblSucursal = new JLabel("Sucursal: ");
		lblSucursal.setBounds(0, 27, 65, 14);
		panelDatosCliente.add(lblSucursal);
		lblSucursal.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
		lblSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
		
				comboClientes = new JComboBox();
				comboClientes.setBounds(68, 0, 265, 20);
				panelDatosCliente.add(comboClientes);
				comboClientes.setBackground(Color.WHITE);
				comboClientes.setVisible(false);
				comboClientes.setFont(new Font("Cambria", Font.BOLD, 14));
				
						comboSucursal = new JComboBox();
						comboSucursal.setBounds(68, 24, 265, 20);
						panelDatosCliente.add(comboSucursal);
						comboSucursal.setBackground(Color.WHITE);
						comboSucursal.setVisible(false);
						comboSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
						
								textCliente = new JTextField();
								textCliente.setBounds(68, 0, 265, 20);
								panelDatosCliente.add(textCliente);
								textCliente.setAlignmentY(Component.TOP_ALIGNMENT);
								textCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
								textCliente.setHorizontalAlignment(SwingConstants.LEFT);
								textCliente.setFont(new Font("Cambria", Font.BOLD, 14));
								textCliente.setEditable(false);
								textCliente.setColumns(10);
								
										textSucursal = new JTextField();
										textSucursal.setBounds(68, 24, 265, 20);
										panelDatosCliente.add(textSucursal);
										textSucursal.setAlignmentY(Component.TOP_ALIGNMENT);
										textSucursal.setAlignmentX(Component.LEFT_ALIGNMENT);
										textSucursal.setHorizontalAlignment(SwingConstants.LEFT);
										textSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
										textSucursal.setEditable(false);
										textSucursal.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 294, 334, 52);
		panel_1.add(scrollPane);

		textFalla = new JTextArea();
		textFalla.setBackground(Color.WHITE);
		scrollPane.setViewportView(textFalla);
		textFalla.setAlignmentY(Component.TOP_ALIGNMENT);
		textFalla.setAlignmentX(Component.LEFT_ALIGNMENT);
		textFalla.setFont(new Font("Cambria", Font.PLAIN, 12));
		textFalla.setEditable(false);
		textFalla.setLineWrap(true);
		textFalla.setWrapStyleWord(true);
		textFalla.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));

		lblDatosDeEquipo = new JLabel("DATOS DEL EQUIPO: ");
		lblDatosDeEquipo.setForeground(new Color(0, 0, 128));
		lblDatosDeEquipo.setBounds(11, 60, 205, 17);
		panel.add(lblDatosDeEquipo);
		lblDatosDeEquipo.setFont(new Font("Cambria", Font.BOLD, 20));

		lblDiagnosticoReparacion = new JLabel("DIAGÓSTICO / REPARACIÓN: ");
		lblDiagnosticoReparacion.setForeground(new Color(0, 0, 128));
		lblDiagnosticoReparacion.setBounds(397, 60, 307, 17);
		panel.add(lblDiagnosticoReparacion);
		lblDiagnosticoReparacion.setFont(new Font("Cambria", Font.BOLD, 20));

		lblSalida = new JLabel("SALIDA: ");
		lblSalida.setForeground(new Color(0, 0, 128));
		lblSalida.setBounds(831, 60, 124, 17);

		panel.add(lblSalida);
		lblSalida.setFont(new Font("Cambria", Font.BOLD, 20));

		lblFechaEntrada = new JLabel("Fecha de Entrada: ");
		lblFechaEntrada.setBounds(109, 88, 124, 17);
		panel.add(lblFechaEntrada);
		lblFechaEntrada.setFont(new Font("Cambria", Font.BOLD, 14));

		lblFechaReparacion = new JLabel("Fecha de Reparación: ");
		lblFechaReparacion.setBounds(536, 88, 139, 17);
		panel.add(lblFechaReparacion);
		lblFechaReparacion.setFont(new Font("Cambria", Font.BOLD, 14));

		lblFechaRespuesta = new JLabel("Fecha de Respuesta: ");
		lblFechaRespuesta.setBounds(913, 88, 139, 17);
		panel.add(lblFechaRespuesta);
		lblFechaRespuesta.setFont(new Font("Cambria", Font.BOLD, 14));

		panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.activeCaption);
		panel_4.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_4.setBounds(834, 110, 344, 102);
		panel.add(panel_4);
		panel_4.setLayout(null);

		lblOC = new JLabel("Orden De Compra: ");
		lblOC.setBounds(7, 13, 143, 14);
		panel_4.add(lblOC);
		lblOC.setFont(new Font("Cambria", Font.BOLD, 14));

		textOC = new JTextField();
		textOC.setBounds(138, 8, 159, 20);
		panel_4.add(textOC);
		textOC.setHorizontalAlignment(SwingConstants.CENTER);
		textOC.setFont(new Font("Cambria", Font.PLAIN, 14));
		textOC.setColumns(10);
		textOC.setEditable(false);
		textOC.setAlignmentX(CENTER_ALIGNMENT);

		lblRemitoDeSalida = new JLabel("Remito de Salida: ");
		lblRemitoDeSalida.setBounds(7, 35, 121, 14);
		panel_4.add(lblRemitoDeSalida);
		lblRemitoDeSalida.setFont(new Font("Cambria", Font.BOLD, 14));

		textUbicacionRemito = new JTextField();
		textUbicacionRemito.setBounds(138, 32, 71, 20);
		panel_4.add(textUbicacionRemito);
		textUbicacionRemito.setHorizontalAlignment(SwingConstants.CENTER);
		textUbicacionRemito.setFont(new Font("Cambria", Font.PLAIN, 14));
		textUbicacionRemito.setEditable(false);
		textUbicacionRemito.setColumns(10);
		textUbicacionRemito.setAlignmentX(0.5f);

		textNumeroRemito = new JTextField();
		textNumeroRemito.setBounds(228, 32, 71, 20);
		panel_4.add(textNumeroRemito);
		textNumeroRemito.setHorizontalAlignment(SwingConstants.CENTER);
		textNumeroRemito.setFont(new Font("Cambria", Font.PLAIN, 14));
		textNumeroRemito.setEditable(false);
		textNumeroRemito.setColumns(10);
		textNumeroRemito.setAlignmentX(0.5f);

		FechaEntrada = new JDateChooser();
		FechaEntrada.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FechaEntrada.setDateFormatString("dd/MM/yyyy");
		FechaEntrada.setForeground(new Color(0, 0, 0));
		FechaEntrada.setEnabled(false);
		((JTextFieldDateEditor) FechaEntrada.getDateEditor()).setDisabledTextColor(Color.darkGray);
		FechaEntrada.setFont(new Font("Cambria", Font.BOLD, 14));
		FechaEntrada.setBounds(232, 88, 134, 17);
		panel.add(FechaEntrada);

		FechaReparacion = new JDateChooser("dd/MM/yyyy", "##-##-####", '-');
		FechaReparacion.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FechaReparacion.setEnabled(false);
		((JTextFieldDateEditor) FechaReparacion.getDateEditor()).setDisabledTextColor(Color.darkGray);
		FechaReparacion.setFont(new Font("Cambria", Font.BOLD, 14));
		FechaReparacion.setBounds(673, 88, 134, 17);
		panel.add(FechaReparacion);
		FechaReparacion.setFocusable(false);

		FechaRespuesta = new JDateChooser("dd/MM/yyyy", "##-##-####", '-');
		FechaRespuesta.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FechaRespuesta.setEnabled(false);
		((JTextFieldDateEditor) FechaRespuesta.getDateEditor()).setDisabledTextColor(Color.darkGray);
		FechaRespuesta.setFont(new Font("Cambria", Font.BOLD, 14));
		FechaRespuesta.setBounds(1045, 88, 131, 17);
		panel.add(FechaRespuesta);
		FechaRespuesta.setFocusable(false);
		
		
		
		lblFechaDeSalida = new JLabel("Fecha de Salida: ");
		lblFechaDeSalida.setFont(new Font("Cambria", Font.BOLD, 14));
		lblFechaDeSalida.setBounds(7, 81, 121, 14);
		panel_4.add(lblFechaDeSalida);

		FechaSalida = new JDateChooser("dd/MM/yyyy", "##-##-####", '-');
		FechaSalida.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FechaSalida.setFont(new Font("Cambria", Font.BOLD, 14));
		FechaSalida.setFocusable(false);
		((JTextFieldDateEditor) FechaSalida.getDateEditor()).setDisabledTextColor(Color.darkGray);
		FechaSalida.setEnabled(false);
		FechaSalida.setBounds(138, 80, 159, 17);
		panel_4.add(FechaSalida);

		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_2.setBounds(11, 474, 355, 184);
		panel.add(panel_2);
		panel_2.setLayout(null);

		textEstadoFisico = new JTextField();
		textEstadoFisico.setBackground(new Color(255, 255, 224));
		textEstadoFisico.setBounds(128, 11, 217, 20);
		panel_2.add(textEstadoFisico);
		textEstadoFisico.setHorizontalAlignment(SwingConstants.LEFT);
		textEstadoFisico.setFont(new Font("Cambria", Font.PLAIN, 14));
		textEstadoFisico.setEditable(false);
		textEstadoFisico.setColumns(10);

		textEstadoTecnico = new JTextField();
		textEstadoTecnico.setBackground(new Color(255, 255, 224));
		textEstadoTecnico.setBounds(128, 32, 217, 20);
		panel_2.add(textEstadoTecnico);
		textEstadoTecnico.setHorizontalAlignment(SwingConstants.LEFT);
		textEstadoTecnico.setFont(new Font("Cambria", Font.PLAIN, 14));
		textEstadoTecnico.setEditable(false);
		textEstadoTecnico.setColumns(10);

		textEstadoComercial = new JTextField();
		textEstadoComercial.setBackground(new Color(255, 255, 224));
		textEstadoComercial.setBounds(128, 53, 217, 20);
		panel_2.add(textEstadoComercial);
		textEstadoComercial.setHorizontalAlignment(SwingConstants.LEFT);
		textEstadoComercial.setFont(new Font("Cambria", Font.PLAIN, 14));
		textEstadoComercial.setEditable(false);
		textEstadoComercial.setColumns(10);

		lblEstadoFisico = new JLabel("Estado Físico: ");
		lblEstadoFisico.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblEstadoFisico.setBounds(10, 14, 121, 14);
		panel_2.add(lblEstadoFisico);
		lblEstadoFisico.setFont(new Font("Cambria", Font.BOLD, 14));

		lblEstadoTecnico = new JLabel("Estado Técnico: ");
		lblEstadoTecnico.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblEstadoTecnico.setBounds(10, 35, 121, 14);
		panel_2.add(lblEstadoTecnico);
		lblEstadoTecnico.setFont(new Font("Cambria", Font.BOLD, 14));

		lblEstadoComercial = new JLabel("Estado Comencial: ");
		lblEstadoComercial.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblEstadoComercial.setBounds(10, 56, 121, 14);
		panel_2.add(lblEstadoComercial);
		lblEstadoComercial.setFont(new Font("Cambria", Font.BOLD, 14));

		BotonEditarEstados = new JButton("EDITAR ESTADOS");
		BotonEditarEstados.setForeground(SystemColor.text);
		BotonEditarEstados.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonEditarEstados.setBackground(SystemColor.textHighlight);
		BotonEditarEstados.setEnabled(false);
		BotonEditarEstados.setBounds(200, 83, 145, 28);
		panel_2.add(BotonEditarEstados);
		BotonEditarEstados.setFont(new Font("Cambria", Font.BOLD, 14));

		btnGenerarRemito = new JButton("GENERAR REMITO");
		btnGenerarRemito.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGenerarRemito.setBackground(SystemColor.textHighlight);
		btnGenerarRemito.setForeground(SystemColor.text);
		btnGenerarRemito.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGenerarRemito.setBounds(138, 56, 159, 20);
		panel_4.add(btnGenerarRemito);



		panel_7 = new JPanel();
		panel_7.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE));
		panel_7.setBackground(SystemColor.inactiveCaption);
		panel_7.setBounds(34, 138, 291, 43);
		panel_2.add(panel_7);
		panel_7.setLayout(null);

		BotonAvisoEquipoListo = new JButton("<html><center>EQUIPO TERMINADO</html>");
		BotonAvisoEquipoListo.setBackground(SystemColor.textHighlight);
		BotonAvisoEquipoListo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonAvisoEquipoListo.setBounds(11, 3, 118, 36);
		panel_7.add(BotonAvisoEquipoListo);
		BotonAvisoEquipoListo.setForeground(SystemColor.text);
		BotonAvisoEquipoListo.setFont(new Font("Cambria", Font.PLAIN, 10));
		BotonAvisoEquipoListo.setIcon(new ImageIcon(this.getClass().getResource("/email.png")));

		BotonRespuestaAlTecnico = new JButton("<html><center>RESPUESTA AL TÉCNICO</html>");
		BotonRespuestaAlTecnico.setBackground(SystemColor.textHighlight);
		BotonRespuestaAlTecnico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonRespuestaAlTecnico.setBounds(152, 3, 118, 36);
		panel_7.add(BotonRespuestaAlTecnico);
		BotonRespuestaAlTecnico.setForeground(SystemColor.text);
		BotonRespuestaAlTecnico.setFont(new Font("Cambria", Font.PLAIN, 10));
		BotonRespuestaAlTecnico.setIcon(new ImageIcon(this.getClass().getResource("/email.png")));

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 122, 332, 4);
		panel_2.add(separator_3);

		textLugarDeIngreso = new JTextField();
		textLugarDeIngreso.setBackground(new Color(255, 255, 224));
		textLugarDeIngreso.setFont(new Font("Cambria", Font.PLAIN, 14));
		textLugarDeIngreso.setEditable(false);
		textLugarDeIngreso.setBounds(128, 87, 62, 20);
		panel_2.add(textLugarDeIngreso);
		textLugarDeIngreso.setColumns(10);

		JLabel lblLugarDeIngreso = new JLabel("Lugar de ingreso: ");
		lblLugarDeIngreso.setFont(new Font("Cambria", Font.BOLD, 14));
		lblLugarDeIngreso.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		lblLugarDeIngreso.setBounds(10, 90, 121, 14);
		panel_2.add(lblLugarDeIngreso);

		panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_3.setBounds(396, 110, 411, 548);
		panel.add(panel_3);
		panel_3.setLayout(null);

		comboTecnico = new JComboBox();
		comboTecnico.setBounds(255, 3, 139, 20);
		comboTecnico.setFont(new Font("Cambria", Font.PLAIN, 14));
		panel_3.add(comboTecnico);

		textNombreTecnico = new JTextField();
		textNombreTecnico.setBounds(255, 3, 139, 20);
		panel_3.add(textNombreTecnico);
		textNombreTecnico.setHorizontalAlignment(SwingConstants.LEFT);
		textNombreTecnico.setFont(new Font("Cambria", Font.PLAIN, 14));
		textNombreTecnico.setEditable(false);
		textNombreTecnico.setColumns(10);

		lblNombreTecnico = new JLabel("Técnico: ");
		lblNombreTecnico.setBounds(185, 6, 61, 14);
		panel_3.add(lblNombreTecnico);
		lblNombreTecnico.setFont(new Font("Cambria", Font.BOLD, 14));

		lblDiagnostico = new JLabel("Diagnóstico: ");
		lblDiagnostico.setBounds(10, 6, 89, 14);
		panel_3.add(lblDiagnostico);
		lblDiagnostico.setFont(new Font("Cambria", Font.BOLD, 14));

		lblRepuestos = new JLabel("Repuestos: ");
		lblRepuestos.setBounds(10, 180, 89, 14);
		panel_3.add(lblRepuestos);
		lblRepuestos.setFont(new Font("Cambria", Font.BOLD, 14));

		lblInformeCliente = new JLabel("Informe Cliente: ");
		lblInformeCliente.setBounds(10, 335, 115, 14);
		panel_3.add(lblInformeCliente);
		lblInformeCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setPreferredSize(new Dimension(5, 5));
		scrollPane_1.setBounds(9, 25, 385, 152);
		panel_3.add(scrollPane_1);

		textDiagnostico = new JTextArea();
		textDiagnostico.setMargin(new Insets(5, 5, 5, 5));
		scrollPane_1.setViewportView(textDiagnostico);
		textDiagnostico.setFont(new Font("Cambria", Font.PLAIN, 12));
		textDiagnostico.setEditable(false);
		textDiagnostico.setLineWrap(true);
		textDiagnostico.setWrapStyleWord(true);
		textDiagnostico
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 353, 385, 146);
		panel_3.add(scrollPane_2);

		textInformeCliente = new JTextArea();
		textInformeCliente.setDragEnabled(true);
		textInformeCliente.setAutoscrolls(false);
		textInformeCliente.setMargin(new Insets(5, 5, 5, 5));
		scrollPane_2.setViewportView(textInformeCliente);
		textInformeCliente.setFont(new Font("Cambria", Font.PLAIN, 12));
		textInformeCliente.setEditable(false);
		textInformeCliente.setLineWrap(true);
		textInformeCliente.setWrapStyleWord(true);
		textInformeCliente
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));

		JScrollPane spRepuestos = new JScrollPane();
		spRepuestos.setBounds(10, 197, 385, 99);
		this.panel_3.add(spRepuestos);

		modelRepuestos = new DefaultTableModel(null, nombreColumnas);
		tablaRepuestos = new JTable(modelRepuestos);

		modelRepuestos = new DefaultTableModel(new Object[][] {},
				new String[] { "REFERENCIA", "ORIGINAL", "REEMPLAZO", "NOTA" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

			@SuppressWarnings("unchecked")
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { true, true, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};

		tablaRepuestos_1 = new JTable(modelRepuestos);
		tablaRepuestos_1.setEnabled(false);
		tablaRepuestos_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		tablaRepuestos_1.getTableHeader().setReorderingAllowed(false);

		// Seteo para los anchos de las columnas
		tablaRepuestos_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tablaRepuestos_1.setAutoCreateColumnsFromModel(false);

		spRepuestos.setViewportView(tablaRepuestos_1);

		btnRepuestos = new JButton("AGREGAR");
		btnRepuestos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRepuestos.setEnabled(false);
		btnRepuestos.setBounds(69, 298, 103, 23);
		panel_3.add(btnRepuestos);
		btnRepuestos.setForeground(new Color(0, 128, 0));
		btnRepuestos.setFont(new Font("Cambria", Font.BOLD, 14));

		btnEliminarRepuesto = new JButton("ELIMINAR");
		btnEliminarRepuesto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminarRepuesto.setEnabled(false);
		btnEliminarRepuesto.setBounds(241, 298, 99, 23);
		panel_3.add(btnEliminarRepuesto);
		btnEliminarRepuesto.setForeground(new Color(255, 0, 0));
		btnEliminarRepuesto.setFont(new Font("Cambria", Font.BOLD, 14));

		panel_8 = new JPanel();
		panel_8.setBounds(77, 502, 257, 43);
		panel_3.add(panel_8);
		panel_8.setBackground(SystemColor.inactiveCaption);
		panel_8.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE));
		panel_8.setLayout(null);

		BotonAvisoInforme = new JButton("<html><center>AVISO DE INFORME</html>");
		BotonAvisoInforme.setBackground(SystemColor.textHighlight);
		BotonAvisoInforme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonAvisoInforme.setBounds(10, 3, 118, 36);
		panel_8.add(BotonAvisoInforme);
		BotonAvisoInforme.setForeground(SystemColor.text);
		BotonAvisoInforme.setFont(new Font("Cambria", Font.PLAIN, 10));
		BotonAvisoInforme.setIcon(new ImageIcon(this.getClass().getResource("/email.png")));

		chckbxAvisoEnviado = new JCheckBox("");
		chckbxAvisoEnviado.setBackground(SystemColor.inactiveCaption);
		chckbxAvisoEnviado.setDoubleBuffered(true);
		chckbxAvisoEnviado.setBounds(134, 10, 21, 23);

		chckbxAvisoEnviado.setEnabled(false);
		chckbxAvisoEnviado.setFont(new Font("Cambria", Font.PLAIN, 12));
		chckbxAvisoEnviado.setForeground(Color.BLUE);
		panel_8.add(chckbxAvisoEnviado);

		JLabel lblInformeEnviado = new JLabel("AVISO ENVIADO");
		lblInformeEnviado.setForeground(Color.BLUE);
		lblInformeEnviado.setFont(new Font("Cambria", Font.BOLD, 10));
		lblInformeEnviado.setBounds(157, 13, 90, 17);
		panel_8.add(lblInformeEnviado);

		JLabel lblPanelDeControl = new JLabel("PANEL DE CONTROL");
		lblPanelDeControl.setForeground(new Color(0, 0, 128));
		lblPanelDeControl.setFont(new Font("Cambria", Font.BOLD, 20));
		lblPanelDeControl.setBounds(834, 467, 218, 17);
		panel.add(lblPanelDeControl);
				
				panelDatosExtrasCliente = new JPanel();
				panelDatosExtrasCliente.setBackground(SystemColor.activeCaption);
				panelDatosExtrasCliente.setBounds(12, 199, 332, 66);
				panel_1.add(panelDatosExtrasCliente);
				panelDatosExtrasCliente.setLayout(null);
		
				textAvisoCliente = new JTextField();
				textAvisoCliente.setBounds(121, 46, 211, 20);
				panelDatosExtrasCliente.add(textAvisoCliente);
				textAvisoCliente.setBackground(Color.WHITE);
				textAvisoCliente.setAlignmentY(Component.TOP_ALIGNMENT);
				textAvisoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
				textAvisoCliente.setHorizontalAlignment(SwingConstants.LEFT);
				textAvisoCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
				textAvisoCliente.setEditable(false);
				textAvisoCliente.setColumns(10);
				
						textRemitoCliente = new JTextField();
						textRemitoCliente.setBounds(121, 23, 211, 20);
						panelDatosExtrasCliente.add(textRemitoCliente);
						textRemitoCliente.setBackground(Color.WHITE);
						textRemitoCliente.setAlignmentY(Component.TOP_ALIGNMENT);
						textRemitoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
						textRemitoCliente.setHorizontalAlignment(SwingConstants.LEFT);
						textRemitoCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
						textRemitoCliente.setEditable(false);
						textRemitoCliente.setColumns(10);
						
								textClienteCliente = new JTextField();
								textClienteCliente.setBounds(121, 0, 211, 20);
								panelDatosExtrasCliente.add(textClienteCliente);
								textClienteCliente.setBackground(Color.WHITE);
								textClienteCliente.setAlignmentY(Component.TOP_ALIGNMENT);
								textClienteCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
								textClienteCliente.setHorizontalAlignment(SwingConstants.LEFT);
								textClienteCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
								textClienteCliente.setEditable(false);
								textClienteCliente.setColumns(10);
								
										lblClienteCliente = new JLabel("Cliente de Cliente: ");
										lblClienteCliente.setBounds(0, 3, 119, 14);
										panelDatosExtrasCliente.add(lblClienteCliente);
										lblClienteCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
										lblClienteCliente.setFont(new Font("Cambria", Font.BOLD, 14));
										
												lblRemitoCliente = new JLabel("Remito de Cliente: ");
												lblRemitoCliente.setBounds(0, 26, 119, 14);
												panelDatosExtrasCliente.add(lblRemitoCliente);
												lblRemitoCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
												lblRemitoCliente.setFont(new Font("Cambria", Font.BOLD, 14));
												
														lblAvisoCliente = new JLabel("Aviso Cliente: ");
														lblAvisoCliente.setBounds(0, 49, 119, 14);
														panelDatosExtrasCliente.add(lblAvisoCliente);
														lblAvisoCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(105, 105, 105)));
														lblAvisoCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		separator = new JSeparator();
		separator.setBorder(null);
		separator.setOpaque(true);
		separator.setForeground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(379, 53, 2, 600);
		panel.add(separator);

		separator_1 = new JSeparator();
		separator_1.setBorder(null);
		separator_1.setOpaque(true);
		separator_1.setForeground(Color.BLACK);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(820, 53, 2, 600);
		panel.add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setBorder(null);
		separator_2.setOpaque(true);
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(834, 219, 340, 2);
		panel.add(separator_2);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBorder(null);
		separator_4.setOpaque(true);
		separator_4.setForeground(Color.BLACK);
		separator_4.setBounds(11, 43, 1165, 2);
		panel.add(separator_4);

		separator_6 = new JSeparator();
		separator_6.setBorder(null);
		separator_6.setOpaque(true);
		separator_6.setOrientation(SwingConstants.VERTICAL);
		separator_6.setForeground(Color.BLACK);
		separator_6.setBounds(383, 53, 2, 600);
		panel.add(separator_6);

		separator_7 = new JSeparator();
		separator_7.setBorder(null);
		separator_7.setOpaque(true);
		separator_7.setOrientation(SwingConstants.VERTICAL);
		separator_7.setForeground(Color.BLACK);
		separator_7.setBounds(824, 53, 2, 600);
		panel.add(separator_7);

		separator_8 = new JSeparator();
		separator_8.setBorder(null);
		separator_8.setOpaque(true);
		separator_8.setForeground(Color.BLACK);
		separator_8.setBounds(11, 47, 1165, 2);
		panel.add(separator_8);

		separator_9 = new JSeparator();
		separator_9.setBorder(null);
		separator_9.setOpaque(true);
		separator_9.setForeground(Color.BLACK);
		separator_9.setBounds(834, 223, 340, 2);
		panel.add(separator_9);

		comboELS = new JComboBox();
		comboELS.setBackground(new Color(224, 255, 255));
		comboELS.setFont(new Font("Cambria", Font.BOLD, 16));
		comboELS.setBounds(397, 5, 100, 35);
		panel.add(comboELS);

		btnBuscarELS = new JButton("BUSCAR ELS");
		btnBuscarELS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarELS.setBackground(new Color(224, 255, 255));
		btnBuscarELS.setFont(new Font("Cambria", Font.BOLD, 13));
		btnBuscarELS.setBounds(500, 5, 113, 35);
		panel.add(btnBuscarELS);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBackground(new Color(224, 255, 255));
		btnBuscar.setFont(new Font("Cambria", Font.BOLD, 13));
		btnBuscar.setBounds(694, 5, 113, 35);
		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/Buscar.png")));
		panel.add(btnBuscar);

		panel_presupuesto = new JPanel();
		panel_presupuesto.setBounds(834, 253, 344, 200);
		panel.add(panel_presupuesto);
		panel_presupuesto.setBackground(SystemColor.activeCaption);
		panel_presupuesto.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_presupuesto.setLayout(null);

		BotonPresupuestar = new JButton("<html><center>PRESUPUESTAR<html>");
		BotonPresupuestar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		BotonPresupuestar.setBackground(SystemColor.textHighlight);
		BotonPresupuestar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BotonPresupuestar.setBounds(223, 11, 113, 27);
		panel_presupuesto.add(BotonPresupuestar);
		BotonPresupuestar.setForeground(SystemColor.text);
		BotonPresupuestar.setFont(new Font("Cambria", Font.BOLD, 12));
		BotonPresupuestar.setIcon(null);

		chckPDFgenerado = new JCheckBox("");
		chckPDFgenerado.setEnabled(false);
		chckPDFgenerado.setBounds(8, 152, 21, 23);
		panel_presupuesto.add(chckPDFgenerado);
		chckPDFgenerado.setBackground(SystemColor.activeCaption);
		chckPDFgenerado.setFont(new Font("Cambria", Font.PLAIN, 12));
		chckPDFgenerado.setForeground(Color.BLUE);

		chckPDFenviado = new JCheckBox("");
		chckPDFenviado.setEnabled(false);
		chckPDFenviado.setBounds(8, 172, 21, 23);
		panel_presupuesto.add(chckPDFenviado);
		chckPDFenviado.setBackground(SystemColor.activeCaption);
		chckPDFenviado.setFont(new Font("Cambria", Font.PLAIN, 12));
		chckPDFenviado.setForeground(Color.BLUE);

		chckWORDgenerado = new JCheckBox("");
		chckWORDgenerado.setEnabled(false);
		chckWORDgenerado.setBounds(166, 152, 21, 23);
		panel_presupuesto.add(chckWORDgenerado);
		chckWORDgenerado.setBackground(SystemColor.activeCaption);
		chckWORDgenerado.setFont(new Font("Cambria", Font.PLAIN, 12));
		chckWORDgenerado.setForeground(Color.BLUE);

		chckWORDenviado = new JCheckBox("");
		chckWORDenviado.setEnabled(false);
		chckWORDenviado.setBounds(166, 172, 21, 23);
		panel_presupuesto.add(chckWORDenviado);
		chckWORDenviado.setBackground(SystemColor.activeCaption);
		chckWORDenviado.setFont(new Font("Cambria", Font.PLAIN, 12));
		chckWORDenviado.setForeground(Color.BLUE);

		panel_MontoPresupuesto = new JPanel();
		panel_MontoPresupuesto.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_MontoPresupuesto.setBackground(SystemColor.inactiveCaption);
		panel_MontoPresupuesto.setBounds(12, 11, 202, 119);
		panel_presupuesto.add(panel_MontoPresupuesto);
		panel_MontoPresupuesto.setLayout(null);


		textPresupuesto = new JTextField(10);
		textPresupuesto.setForeground(SystemColor.desktop);
		textPresupuesto.setHorizontalAlignment(SwingConstants.RIGHT);
		textPresupuesto.setFont(new Font("Cambria", Font.BOLD, 12));
		textPresupuesto.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		textPresupuesto.setBackground(SystemColor.inactiveCaption);
		textPresupuesto.setBounds(97, 8, 95, 15);
		panel_MontoPresupuesto.add(textPresupuesto);
		textPresupuesto.setColumns(10);

		JLabel lblPresupuesto = new JLabel("EN PESOS : ");
		lblPresupuesto.setHorizontalAlignment(SwingConstants.LEFT);
		lblPresupuesto.setForeground(SystemColor.desktop);
		lblPresupuesto.setBorder(null);
		lblPresupuesto.setBounds(7, 8, 80, 15);
		panel_MontoPresupuesto.add(lblPresupuesto);
		lblPresupuesto.setFont(new Font("Cambria", Font.BOLD, 12));

		textPago = new JTextField(10);
		textPago.setForeground(SystemColor.desktop);
		textPago.setHorizontalAlignment(SwingConstants.RIGHT);
		textPago.setFont(new Font("Cambria", Font.BOLD, 12));
		textPago.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		textPago.setBackground(SystemColor.inactiveCaption);
		textPago.setBounds(97, 56, 95, 15);
		panel_MontoPresupuesto.add(textPago);
		textPago.setColumns(10);

		lblPago = new JLabel("PAGO:");
		lblPago.setHorizontalAlignment(SwingConstants.LEFT);
		lblPago.setForeground(SystemColor.desktop);
		lblPago.setBorder(null);
		lblPago.setBounds(7, 56, 80, 15);
		panel_MontoPresupuesto.add(lblPago);
		lblPago.setFont(new Font("Cambria", Font.BOLD, 12));

		textEquipoPagado = new JTextField();
		textEquipoPagado.setEditable(false);
		textEquipoPagado.setForeground(SystemColor.desktop);
		textEquipoPagado.setVisible(false);
		textEquipoPagado.setFont(new Font("Cambria", Font.BOLD, 12));
		textEquipoPagado.setHorizontalAlignment(SwingConstants.CENTER);
		textEquipoPagado.setBorder(null);
		textEquipoPagado.setBackground(SystemColor.inactiveCaption);
		textEquipoPagado.setBounds(6, 82, 190, 20);
		panel_MontoPresupuesto.add(textEquipoPagado);
		textEquipoPagado.setColumns(10);

		JLabel lblPresupuestoUs = new JLabel("EN DOLARES : ");
		lblPresupuestoUs.setHorizontalAlignment(SwingConstants.LEFT);
		lblPresupuestoUs.setForeground(SystemColor.desktop);
		lblPresupuestoUs.setFont(new Font("Cambria", Font.BOLD, 12));
		lblPresupuestoUs.setBorder(null);
		lblPresupuestoUs.setBounds(7, 32, 80, 15);
		panel_MontoPresupuesto.add(lblPresupuestoUs);

		textPresupuestoDolar = new JTextField(10);
		textPresupuestoDolar.setHorizontalAlignment(SwingConstants.RIGHT);
		textPresupuestoDolar.setForeground(SystemColor.desktop);
		textPresupuestoDolar.setFont(new Font("Cambria", Font.BOLD, 12));
		textPresupuestoDolar.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		textPresupuestoDolar.setBackground(SystemColor.inactiveCaption);
		textPresupuestoDolar.setBounds(97, 32, 95, 15);
		panel_MontoPresupuesto.add(textPresupuestoDolar);

		JLabel lblPresupuestoGenerado = new JLabel("PRESUPUESTO GENERADO");
		lblPresupuestoGenerado.setForeground(Color.BLUE);
		lblPresupuestoGenerado.setFont(new Font("Cambria", Font.BOLD, 10));
		lblPresupuestoGenerado.setBounds(32, 155, 136, 17);
		panel_presupuesto.add(lblPresupuestoGenerado);

		JLabel lblPresupuestoEnviado = new JLabel("PRESUPUESTO ENVIADO");
		lblPresupuestoEnviado.setForeground(Color.BLUE);
		lblPresupuestoEnviado.setFont(new Font("Cambria", Font.BOLD, 10));
		lblPresupuestoEnviado.setBounds(32, 176, 136, 17);
		panel_presupuesto.add(lblPresupuestoEnviado);

		lblInformeSiemensGenerado = new JLabel("INFORME SIEMENS GENERADO");
		lblInformeSiemensGenerado.setBounds(189, 155, 148, 17);
		panel_presupuesto.add(lblInformeSiemensGenerado);
		lblInformeSiemensGenerado.setForeground(Color.BLUE);
		lblInformeSiemensGenerado.setFont(new Font("Cambria", Font.BOLD, 10));

		lblInformeSiemensEnviado = new JLabel("INFORME SIEMENS ENVIADO");
		lblInformeSiemensEnviado.setForeground(Color.BLUE);
		lblInformeSiemensEnviado.setFont(new Font("Cambria", Font.BOLD, 10));
		lblInformeSiemensEnviado.setBounds(189, 176, 148, 17);
		panel_presupuesto.add(lblInformeSiemensEnviado);

		btnenviarCorreoOwsp = new JButton("<html><center>ENVIAR</html>");
		btnenviarCorreoOwsp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnenviarCorreoOwsp.setForeground(Color.WHITE);
		btnenviarCorreoOwsp.setFont(new Font("Cambria", Font.BOLD, 12));
		btnenviarCorreoOwsp.setBackground(SystemColor.textHighlight);
		btnenviarCorreoOwsp.setBounds(223, 42, 113, 27);
		panel_presupuesto.add(btnenviarCorreoOwsp);
		
		btnfacturar = new JButton("<html><center>FACTURAR</html>");
		btnfacturar.setForeground(Color.WHITE);
		btnfacturar.setFont(new Font("Cambria", Font.BOLD, 12));
		btnfacturar.setBackground(SystemColor.textHighlight);
		btnfacturar.setBounds(223, 73, 113, 27);
		panel_presupuesto.add(btnfacturar);
		
		btnabrirExcel = new JButton("<html><center>ABRIR EXCEL</html>");
		btnabrirExcel.setBounds(223, 103, 113, 27);
		panel_presupuesto.add(btnabrirExcel);
		btnabrirExcel.setForeground(SystemColor.text);
		btnabrirExcel.setFont(new Font("Cambria", Font.BOLD, 10));
		btnabrirExcel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnabrirExcel.setBackground(new Color(0, 153, 102));
		
		btnCopiarPresupuesto = new JButton("<html><center>COPIAR PRESUPUESTO</html>");
		btnCopiarPresupuesto.setEnabled(false);
		btnCopiarPresupuesto.setForeground(new Color(0, 0, 0));
		btnCopiarPresupuesto.setFont(new Font("Cambria", Font.BOLD, 10));
		btnCopiarPresupuesto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCopiarPresupuesto.setBackground(new Color(143, 188, 143));
		btnCopiarPresupuesto.setBounds(12, 134, 202, 18);
		panel_presupuesto.add(btnCopiarPresupuesto);

		labelPresupuesto = new JLabel("PRESUPUESTO: ");
		labelPresupuesto.setBounds(834, 232, 150, 17);
		panel.add(labelPresupuesto);
		labelPresupuesto.setForeground(new Color(0, 0, 128));
		labelPresupuesto.setFont(new Font("Cambria", Font.BOLD, 20));
		
		separator_11 = new JSeparator();
		separator_11.setForeground(new Color(0, 0, 0));
		separator_11.setOrientation(SwingConstants.VERTICAL);
		separator_11.setBounds(379, 6, 2, 32);
		panel.add(separator_11);
		
		separator_12 = new JSeparator();
		separator_12.setForeground(new Color(0, 0, 0));
		separator_12.setOrientation(SwingConstants.VERTICAL);
		separator_12.setBounds(383, 6, 2, 32);
		panel.add(separator_12);
		
		separator_13 = new JSeparator();
		separator_13.setOrientation(SwingConstants.VERTICAL);
		separator_13.setForeground(Color.BLACK);
		separator_13.setBounds(820, 6, 2, 32);
		panel.add(separator_13);
		
		separator_14 = new JSeparator();
		separator_14.setOrientation(SwingConstants.VERTICAL);
		separator_14.setForeground(Color.BLACK);
		separator_14.setBounds(824, 6, 2, 32);
		panel.add(separator_14);
		
		separator_5 = new JSeparator();
		separator_5.setOpaque(true);
		separator_5.setForeground(Color.BLACK);
		separator_5.setBorder(null);
		separator_5.setBounds(834, 457, 340, 2);
		panel.add(separator_5);
		
		separator_10 = new JSeparator();
		separator_10.setOpaque(true);
		separator_10.setForeground(Color.BLACK);
		separator_10.setBorder(null);
		separator_10.setBounds(834, 461, 340, 2);
		panel.add(separator_10);

		setLocationCenter();
		this.setVisible(true);


	}

	private void configurarAtajoGuardar() {
	    JRootPane rootPane = this.getRootPane();
	    KeyStroke ctrlG = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK);
	    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlG, "guardar");
	    rootPane.getActionMap().put("guardar", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//controladorReparacion.guardarCambios(VentanaVisualizarEquipos.this);
	        }
	    });
		
	}

	public void setLocationCenter() {
		setLocationMove(0, 0);
	}

	public void setLocationMove(int moveWidth, int moveHeight) {
		// Obtenemos el tama�o de la pantalla.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Obtenemos el tama�o de nuestro frame.
		Dimension frameSize = this.getSize();
		frameSize.width = frameSize.width > screenSize.width ? screenSize.width : frameSize.width;
		frameSize.height = frameSize.height > screenSize.height ? screenSize.height : frameSize.height;
		// We define the location. Definimos la localizaci�n.
		setLocation((screenSize.width - frameSize.width) / 2 + moveWidth,
				(screenSize.height - frameSize.height) / 2 + moveHeight);
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public void setBtnGuardarCambios(JButton btnGuardarCambios) {
		this.btnGuardarCambios = btnGuardarCambios;
	}

	public JButton getBotonPrimero() {
		return BotonPrimero;
	}

	public void setBotonPrimero(JButton botonPrimero) {
		BotonPrimero = botonPrimero;
	}

	public JButton getBotonUltimo() {
		return BotonUltimo;
	}

	public void setBotonUltimo(JButton botonUltimo) {
		BotonUltimo = botonUltimo;
	}

	public JDateChooser getFechaEntrada() {
		return FechaEntrada;
	}

	public Date getFechaEntrada2() {
		return (Date) FechaEntrada.getDate();

	}

	public Date getFecha_Salida() {
		return (Date) FechaSalida.getDate();

	}

	public JDateChooser getFechaSalida() {
		return FechaSalida;
	}

	public JDateChooser getFechaReparacion() {
		return FechaReparacion;
	}

	public JDateChooser getFechaRespuesta() {
		return FechaRespuesta;
	}

	public JButton getBotonRegistroIngreso() {
		return BotonRegistroIngreso;
	}

	public void setBotonRegistroIngreso(JButton botonReparacion) {
		BotonRegistroIngreso = botonReparacion;
	}

	public JTextField getTextNombreEquipo() {
		return textNombreEquipo;
	}

	public void setTextNombreEquipo(JTextField textNombreEquipo) {
		this.textNombreEquipo = textNombreEquipo;
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

	public JTextField getTextNSerie() {
		return textNSerie;
	}

	public void setTextNSerie(JTextField textNSerie) {
		this.textNSerie = textNSerie;
	}

	public JTextArea getTextFalla() {
		return textFalla;
	}

	public void setTextFalla(JTextArea textFalla) {
		this.textFalla = textFalla;
	}

	public JTextField getTextAvisoCliente() {
		return textAvisoCliente;
	}

	public void setTextAvisoCliente(JTextField textAvisoCliente) {
		this.textAvisoCliente = textAvisoCliente;
	}

	public JTextField getTextClienteCliente() {
		return textClienteCliente;
	}

	public void setTextClienteCliente(JTextField textClienteCliente) {
		this.textClienteCliente = textClienteCliente;
	}

	public JTextField getTextRemitoCliente() {
		return textRemitoCliente;
	}

	public void setTextRemitoCliente(JTextField textRemitoCliente) {
		this.textRemitoCliente = textRemitoCliente;
	}

	public JTextField getTextCliente() {
		return textCliente;
	}

	public void setTextCliente(JTextField textCliente) {
		this.textCliente = textCliente;
	}

	public JTextField getTextSucursal() {
		return textSucursal;
	}

	public void setTextSucursal(JTextField textSucursal) {
		this.textSucursal = textSucursal;
	}

	public JTextField getTextEstadoFisico() {
		return textEstadoFisico;
	}

	public void setTextEstadoFisico(JTextField textEstadoFisico) {
		this.textEstadoFisico = textEstadoFisico;
	}

	public JTextField getTextEstadoComercial() {
		return textEstadoComercial;
	}

	public void setTextEstadoComercial(JTextField textEstadoComercial) {
		this.textEstadoComercial = textEstadoComercial;
	}

	public JTextField getTextEstadoTecnico() {
		return textEstadoTecnico;
	}

	public void setTextEstadoTecnico(JTextField textEstadoTecnico) {
		this.textEstadoTecnico = textEstadoTecnico;
	}

	public JTextArea getTextDiagnostico() {
		return textDiagnostico;
	}

	public void setTextDiagnostico(JTextArea textDiagnostico) {
		this.textDiagnostico = textDiagnostico;
	}

	public JTextArea getTextInformeCliente() {
		return textInformeCliente;
	}

	public void setTextInformeCliente(JTextArea textInformeCliente) {
		this.textInformeCliente = textInformeCliente;
	}

	public JTextField getTextNombreTecnico() {
		return textNombreTecnico;
	}

	public void setTextNombreTecnico(JTextField textNombreTecnico) {
		this.textNombreTecnico = textNombreTecnico;
	}

	public JTextField getTextOC() {
		return textOC;
	}

	public void setTextOC(JTextField textOC) {
		this.textOC = textOC;
	}

	public JTextField getTextUbicacionRemito() {
		return textUbicacionRemito;
	}

	public void setTextUbicacionRemito(JTextField textUbicacionRemito) {
		this.textUbicacionRemito = textUbicacionRemito;
	}

	public JTextField getTextNumeroRemito() {
		return textNumeroRemito;
	}

	public void setTextNumeroRemito(JTextField textNumeroRemito) {
		this.textNumeroRemito = textNumeroRemito;
	}

	public void setTextELS(JTextField textELS) {
		this.textELS = textELS;
	}

	public JButton getBotonAnterior() {
		return BotonAnterior;
	}

	public void setBotonAnterior(JButton botonAnterior) {
		BotonAnterior = botonAnterior;
	}

	public JButton getBotonSiguiente() {
		return BotonSiguiente;
	}

	public void setBotonSiguiente(JButton botonSiguiente) {
		BotonSiguiente = botonSiguiente;
	}

	public JButton getBotonEditarEstados() {
		return BotonEditarEstados;
	}

	public void setBotonEditarEstados(JButton btnEditarEstados) {
		this.BotonEditarEstados = btnEditarEstados;
	}

	public String getTextELS() {
		return textELS.getText();
	}

	public void setTextELS(String textELS) {
		this.textELS.setText(textELS);
	}

	public void setTextNombreEquipo(String string) {
		this.textNombreEquipo.setText(string);

	}

	public void setTextMarca(String string) {
		this.textMarca.setText(string);

	}

	public void setTextModelo(String string) {
		this.textModelo.setText(string);

	}

	public void setTextNSerie(String string) {
		this.textNSerie.setText(string);

	}

	public void setTextFalla(String string) {
		this.textFalla.setText(string);

	}

	public void setTextDiagnostico(String string) {
		this.textDiagnostico.setText(string);
		textDiagnostico.setCaretPosition(0);

	}

	public void setTextInformeCliente(String string) {
		this.textInformeCliente.setText(string);
		textInformeCliente.setCaretPosition(0);

	}

	public void setTextAvisoCliente(String string) {
		this.textAvisoCliente.setText(string);

	}

	public void setTextClienteCliente(String string) {
		this.textClienteCliente.setText(string);

	}

	public void setTextRemitoCliente(String string) {
		this.textRemitoCliente.setText(string);

	}

	public void setTextSucursal(String string) {
		this.textSucursal.setText(string);

	}

	public void setTextCliente(String string) {
		this.textCliente.setText(string);

	}

	public void setTextFechaEntrada(JDateChooser fecha) {
		this.FechaEntrada = fecha;
	}

	public void setTextFechaEntrada2(java.util.Date date) {
		this.FechaEntrada.setDate(date);
	}

	public void setTextFechaSalida(java.util.Date date) {
		this.FechaSalida.setDate(date);
	}

	public void setTextFechaSalida2(JDateChooser fecha) {
		this.FechaSalida=fecha;
	}
	
	public void setTextEstadoFisico(String string) {
		this.textEstadoFisico.setText(string);
	}

	public void setTextEstadoTecnico(String string) {
		this.textEstadoTecnico.setText(string);

	}

	public void setTextEstadoComercial(String string) {
		this.textEstadoComercial.setText(string);

	}

	public void setTextFechaReparacion(JDateChooser fecha) {
		this.FechaReparacion = fecha;
	}

	public void setTextFechaReparacion2(java.util.Date date) {
		this.FechaReparacion.setDate(date);
	}

	public void setTextNombreTecnico(String string) {
		this.textNombreTecnico.setText(string);

	}

	public DefaultTableModel getModelRepuestos() {
		return modelRepuestos;
	}

	public JTable getTablaRepuestos() {
		return tablaRepuestos_1;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setTextFechaRespuesta(JDateChooser fecha) {
		this.FechaRespuesta = fecha;
	}

	public void setTextFechaRespuesta2(java.util.Date fecha) {
		this.FechaRespuesta.setDate(fecha);
	}

	public JTextFieldDateEditor getFechaFabr() {
		return FechaFabr;
	}

	public void setFechaFabr(JTextFieldDateEditor fechaFabr) {
		FechaFabr = fechaFabr;
	}

	public void setFechaFabr2(java.util.Date fechaFabr) {
		this.FechaFabr.setDate(fechaFabr);
	}

	public void setFechaFabr3(String fechaFabr) {
		this.FechaFabr.setDateFormatString(fechaFabr);
	}

	public void setTextOC(String string) {
		this.textOC.setText(string);

	}

	public void setTextUbicacionRemito(String string) {
		this.textUbicacionRemito.setText(string);

	}

	public void setTextNumeroRemito(String string) {
		this.textNumeroRemito.setText(string);

	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboClientes() {
		return comboClientes;
	}

	@SuppressWarnings("rawtypes")
	public void setComboClientes(JComboBox comboClientes) {
		this.comboClientes = comboClientes;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboSucursal() {
		return comboSucursal;
	}

	@SuppressWarnings("rawtypes")
	public void setComboSucursal(JComboBox comboSucursal) {
		this.comboSucursal = comboSucursal;
	}

	public JButton getBtnRepuestos() {
		return btnRepuestos;
	}

	public void setBtnRepuestos(JButton btnRepuestos) {
		this.btnRepuestos = btnRepuestos;
	}


	public JButton getBtnEliminarRepuesto() {
		return btnEliminarRepuesto;
	}

	public void setBtnEliminarRepuesto(JButton btnEliminarRepuesto) {
		this.btnEliminarRepuesto = btnEliminarRepuesto;
	}

	public void getErrorMsj(String msj) {
		JOptionPane.showMessageDialog(null, msj);
	}

	public JButton getBotonAvisoEquipoListo() {
		return BotonAvisoEquipoListo;
	}

	public void setBotonAvisoEquipoListo(JButton botonAvisoEquipoListo) {
		BotonAvisoEquipoListo = botonAvisoEquipoListo;
	}

	public JButton getBotonRespuestaAlTecnico() {
		return BotonRespuestaAlTecnico;
	}

	public void setBotonRespuestaAlTecnico(JButton botonRespuestaAlTecnico) {
		BotonRespuestaAlTecnico = botonRespuestaAlTecnico;
	}

	public JButton getBotonAvisoInforme() {
		return BotonAvisoInforme;
	}

	public void setBotonAvisoInforme(JButton botonAvisoInforme) {
		BotonAvisoInforme = botonAvisoInforme;
	}

	public boolean getChckbxAvisoEnviado() {
		return chckbxAvisoEnviado.isSelected();
	}

	public void setChckbxAvisoEnviado(Boolean AvEnviado) {
		chckbxAvisoEnviado.setSelected(AvEnviado);
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

	public JButton getBotonPresupuestar() {
		return BotonPresupuestar;
	}

	public void setBotonPresupuestar(JButton botonPresupuestar) {
		BotonPresupuestar = botonPresupuestar;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboTecnico() {
		return comboTecnico;
	}

	@SuppressWarnings("rawtypes")
	public void setComboTecnico(JComboBox comboTecnico) {
		this.comboTecnico = comboTecnico;
	}

	public JTextField getTextPresupuesto() {
		return textPresupuesto;
	}

	public void setTextPresupuesto(String textPresupuesto) {
		this.textPresupuesto.setText(textPresupuesto);
	}

	public JTextField getTextPago() {
		return textPago;
	}

	public void setTextPago(String string) {
		this.textPago.setText(string);
	}

	public JPanel getPanel_MontoPresupuesto() {
		return panel_MontoPresupuesto;
	}

	public void setPanel_MontoPresupuesto(JPanel panel_MontoPresupuesto) {
		this.panel_MontoPresupuesto = panel_MontoPresupuesto;
	}

	public JTextField getTextEquipoPagado() {
		return textEquipoPagado;
	}

	public void setTextEquipoPagado(JTextField textEquipoPagado) {
		this.textEquipoPagado = textEquipoPagado;
	}

	public JPanel getPanel_presupuesto() {
		return panel_presupuesto;
	}

	public void setPanel_presupuesto(JPanel panel_presupuesto) {
		this.panel_presupuesto = panel_presupuesto;
	}

	public JLabel getLabelPresupuesto() {
		return labelPresupuesto;
	}

	public void setLabelPresupuesto(JLabel labelPresupuesto) {
		this.labelPresupuesto = labelPresupuesto;
	}

	public JButton getBtnGenerarRemito() {
		return btnGenerarRemito;
	}

	public void setBtnGenerarRemito(JButton btnGenerarRemito) {
		this.btnGenerarRemito = btnGenerarRemito;
	}

	public JButton getBtnenviarCorreoOwsp() {
		return btnenviarCorreoOwsp;
	}

	public void setBtnenviarCorreoOwsp(JButton btnenviarCorreoOwsp) {
		this.btnenviarCorreoOwsp = btnenviarCorreoOwsp;
	}

	public void mostrarVentanaVisualizarEquipos() {
		setVisible(true);
	}


	public JButton getBtnBuscarELS() {
		return btnBuscarELS;
	}

	public void setBtnBuscarELS(JButton btnBuscarELS) {
		this.btnBuscarELS = btnBuscarELS;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getComboELS() {
		return comboELS;
	}

	@SuppressWarnings("rawtypes")
	public void setComboELS(JComboBox comboELS) {
		this.comboELS = comboELS;
	}

	public JTextField getTextPresupuestoDolar() {
		return textPresupuestoDolar;
	}

	public void setTextPresupuestoDolar(String textPresupuestoDolar) {
		this.textPresupuestoDolar.setText(textPresupuestoDolar);
	}

	public JTextField getTextLugarDeIngreso() {
		return textLugarDeIngreso;
	}

	public void setTextLugarDeIngreso(JTextField textLugarDeIngreso) {
		this.textLugarDeIngreso = textLugarDeIngreso;
	}

	public void setTextLugarDeIngreso(String lugarDeIngreso) {
		this.textLugarDeIngreso.setText(lugarDeIngreso);

	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public JButton getBtnfacturar() {
		return btnfacturar;
	}

	public void setBtnfacturar(JButton btnfacturar) {
		this.btnfacturar = btnfacturar;
	}
	
	public JButton getBtnabrirExcel() {
		return btnabrirExcel;
	}

	public void setBtnabrirExcel(JButton btnabrirExcel) {
		this.btnabrirExcel = btnabrirExcel;
	}


	public JButton getBtnCopiarPresupuesto() {
		return btnCopiarPresupuesto;
	}

	public void setBtnCopiarPresupuesto(JButton btnCopiarPresupuesto) {
		this.btnCopiarPresupuesto = btnCopiarPresupuesto;
	}

	
}
