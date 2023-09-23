package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import VistaPropias.CellRendererTablaListado;
import presentacion.controlador.ControladorListados;
import presentacion.controlador.ControladorReparacion;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

public class VentanaListadoReparaciones extends JFrame {


	private static final long serialVersionUID = 1L;
	private JTable tblListado;
	private DefaultTableModel modelReparaciones;

	private String[] nombreColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO",
			"N° SERIE", "AVISO", "REVISIÓN", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS", "TÉCNICO",
			"UBIC. REM", "NUM REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO" };

	private JButton btnFiltrar;
	private JButton btnMostrarTodo;
	private JButton btnEstadisticas;
	private JButton btnMax;
	public static int est;

	private JPanel panelPrincipal;
	private JPanel panelFiltros;
	private JPanel panelSuperior;
	private JPanel panelTitulo;
	private JPanel panelBotones;
	private JPanel panelInferior;
	private JPanel panelCentral;
	private JScrollPane scrollPane;

	private ControladorListados controlador;

	private JComboBox<?> comboFiltroMarca;
	private JComboBox<?> comboFiltroCliente;
	private JComboBox<?> comboFiltroSucursal;
	private JComboBox<?> comboFiltroEstadoFis;
	private JComboBox<?> comboFiltroEstadoCom;
	private JComboBox<?> comboFiltroEstadoTec;
	private JComboBox<?> comboFiltroELS;
	private JComboBox<?> comboFiltroEquipo;
	private JComboBox<?> comboFiltroModelo;
	private JComboBox<?> comboFiltroAviso;
	private JComboBox<?> comboFiltroTecnico;

	private JRadioButton radioButtonMarca;
	private JRadioButton radioButtonCliente;
	private JRadioButton radioButtonSucursal;
	private JRadioButton radioButtonELS;
	private JRadioButton radioButtonEstadoFis;
	private JRadioButton radioButtonEstadoCom;
	private JRadioButton radioButtonEstadoTec;
	private JRadioButton radioButtonEquipo;
	private JRadioButton radioButtonModelo;
	private JRadioButton radioButtonAviso;
	private JRadioButton radioButtonPresupGenerado;
	private JRadioButton radioButtonPresupEnviado;
	private JRadioButton radioButtonTecnico;

	private JCheckBox chckbxPresupuestoGenerado;
	private JCheckBox chckbxPresupuestoEnviado;

	Dimension DimScrollPane;
	Dimension DimPanel;
	Dimension DimContentPane;
	Dimension DimTblReparaciones;


	protected void this_windowOpened(WindowEvent e) {
		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	public VentanaListadoReparaciones(ControladorListados controlador) {
		
		
		super();
		this.controlador = controlador;

		this.this_windowOpened(null);
		setSize(1250, 601);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);

		
		getContentPane().setLayout(new BorderLayout(0, 0));

		panelPrincipal = new JPanel();
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));

		panelSuperior = new JPanel();
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(0, 0));

		panelFiltros = new JPanel();
		panelFiltros.setBackground(new Color(176, 196, 222));
		panelFiltros.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		panelFiltros.setBounds(35, 50, 994, 134);
		panelSuperior.add(panelFiltros, BorderLayout.CENTER);
		GridBagLayout gbl_panelFiltros = new GridBagLayout();
		gbl_panelFiltros.columnWidths = new int[] { 30, 50, 150, 50, 50, 150, 50, 80, 150, 50, 90, 50, 30 };
		gbl_panelFiltros.rowHeights = new int[] { 5, 0, 23, 0, 0, 5, 0 };
		gbl_panelFiltros.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0 };
		gbl_panelFiltros.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		panelFiltros.setLayout(gbl_panelFiltros);

		JLabel lblNewLabel_1 = new JLabel("CLIENTE");
		lblNewLabel_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelFiltros.add(lblNewLabel_1, gbc_lblNewLabel_1);

		comboFiltroCliente = new JComboBox<Object>();
		comboFiltroCliente.setEnabled(false);
		comboFiltroCliente.setBackground(new Color(176, 196, 222));
		comboFiltroCliente.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroCliente = new GridBagConstraints();
		gbc_comboFiltroCliente.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroCliente.gridx = 2;
		gbc_comboFiltroCliente.gridy = 1;
		panelFiltros.add(comboFiltroCliente, gbc_comboFiltroCliente);

		radioButtonCliente = new JRadioButton("");
		radioButtonCliente.setBackground(new Color(176, 196, 222));
		radioButtonCliente.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonCliente = new GridBagConstraints();
		gbc_radioButtonCliente.anchor = GridBagConstraints.WEST;
		gbc_radioButtonCliente.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonCliente.gridx = 3;
		gbc_radioButtonCliente.gridy = 1;
		panelFiltros.add(radioButtonCliente, gbc_radioButtonCliente);

		JLabel lblNewLabel_4_1 = new JLabel("EQUIPO");
		lblNewLabel_4_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_4_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_4_1 = new GridBagConstraints();
		gbc_lblNewLabel_4_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4_1.gridx = 4;
		gbc_lblNewLabel_4_1.gridy = 1;
		panelFiltros.add(lblNewLabel_4_1, gbc_lblNewLabel_4_1);

		comboFiltroEquipo = new JComboBox<Object>();
		comboFiltroEquipo.setEnabled(false);
		comboFiltroEquipo.setBackground(new Color(176, 196, 222));
		comboFiltroEquipo.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroEquipo = new GridBagConstraints();
		gbc_comboFiltroEquipo.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEquipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEquipo.gridx = 5;
		gbc_comboFiltroEquipo.gridy = 1;
		panelFiltros.add(comboFiltroEquipo, gbc_comboFiltroEquipo);

		radioButtonEquipo = new JRadioButton("");
		radioButtonEquipo.setBackground(new Color(176, 196, 222));
		radioButtonEquipo.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonEquipo = new GridBagConstraints();
		gbc_radioButtonEquipo.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEquipo.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonEquipo.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEquipo.gridx = 6;
		gbc_radioButtonEquipo.gridy = 1;
		panelFiltros.add(radioButtonEquipo, gbc_radioButtonEquipo);

		JLabel lblNewLabel_9_1 = new JLabel("ESTADO COM");
		lblNewLabel_9_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_9_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_9_1 = new GridBagConstraints();
		gbc_lblNewLabel_9_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_9_1.gridx = 7;
		gbc_lblNewLabel_9_1.gridy = 1;
		panelFiltros.add(lblNewLabel_9_1, gbc_lblNewLabel_9_1);

		comboFiltroEstadoCom = new JComboBox<Object>();
		comboFiltroEstadoCom.setEnabled(false);
		comboFiltroEstadoCom.setBackground(new Color(176, 196, 222));
		comboFiltroEstadoCom.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroEstadoCom = new GridBagConstraints();
		gbc_comboFiltroEstadoCom.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEstadoCom.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEstadoCom.gridx = 8;
		gbc_comboFiltroEstadoCom.gridy = 1;
		panelFiltros.add(comboFiltroEstadoCom, gbc_comboFiltroEstadoCom);

		radioButtonEstadoCom = new JRadioButton("");
		radioButtonEstadoCom.setBackground(new Color(176, 196, 222));
		radioButtonEstadoCom.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonEstadoCom = new GridBagConstraints();
		gbc_radioButtonEstadoCom.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEstadoCom.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonEstadoCom.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEstadoCom.gridx = 9;
		gbc_radioButtonEstadoCom.gridy = 1;
		panelFiltros.add(radioButtonEstadoCom, gbc_radioButtonEstadoCom);

		chckbxPresupuestoEnviado = new JCheckBox("PRESUPUESTO ENVIADO");
		chckbxPresupuestoEnviado.setEnabled(false);
		chckbxPresupuestoEnviado.setBackground(new Color(176, 196, 222));
		chckbxPresupuestoEnviado.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxPresupuestoEnviado = new GridBagConstraints();
		gbc_chckbxPresupuestoEnviado.anchor = GridBagConstraints.WEST;
		gbc_chckbxPresupuestoEnviado.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPresupuestoEnviado.gridx = 10;
		gbc_chckbxPresupuestoEnviado.gridy = 1;
		panelFiltros.add(chckbxPresupuestoEnviado, gbc_chckbxPresupuestoEnviado);

		radioButtonPresupEnviado = new JRadioButton("");
		radioButtonPresupEnviado.setBackground(new Color(176, 196, 222));
		radioButtonPresupEnviado.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonPresupEnviado = new GridBagConstraints();
		gbc_radioButtonPresupEnviado.anchor = GridBagConstraints.WEST;
		gbc_radioButtonPresupEnviado.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonPresupEnviado.gridx = 11;
		gbc_radioButtonPresupEnviado.gridy = 1;
		panelFiltros.add(radioButtonPresupEnviado, gbc_radioButtonPresupEnviado);

		JLabel lblNewLabel_1_1 = new JLabel("SUCURSAL");
		lblNewLabel_1_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.gridx = 1;
		gbc_lblNewLabel_1_1.gridy = 2;
		panelFiltros.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);

		comboFiltroSucursal = new JComboBox<Object>();
		comboFiltroSucursal.setEnabled(false);
		comboFiltroSucursal.setBackground(new Color(176, 196, 222));
		comboFiltroSucursal.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroSucursal = new GridBagConstraints();
		gbc_comboFiltroSucursal.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroSucursal.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroSucursal.gridx = 2;
		gbc_comboFiltroSucursal.gridy = 2;
		panelFiltros.add(comboFiltroSucursal, gbc_comboFiltroSucursal);

		radioButtonSucursal = new JRadioButton("");
		radioButtonSucursal.setBackground(new Color(176, 196, 222));
		radioButtonSucursal.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonSucursal = new GridBagConstraints();
		gbc_radioButtonSucursal.anchor = GridBagConstraints.WEST;
		gbc_radioButtonSucursal.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonSucursal.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonSucursal.gridx = 3;
		gbc_radioButtonSucursal.gridy = 2;
		panelFiltros.add(radioButtonSucursal, gbc_radioButtonSucursal);

		JLabel lblNewLabel_2_1 = new JLabel("MARCA");
		lblNewLabel_2_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2_1.gridx = 4;
		gbc_lblNewLabel_2_1.gridy = 2;
		panelFiltros.add(lblNewLabel_2_1, gbc_lblNewLabel_2_1);

		comboFiltroMarca = new JComboBox<Object>();
		comboFiltroMarca.setEnabled(false);
		comboFiltroMarca.setBackground(new Color(176, 196, 222));
		comboFiltroMarca.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroMarca = new GridBagConstraints();
		gbc_comboFiltroMarca.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroMarca.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroMarca.gridx = 5;
		gbc_comboFiltroMarca.gridy = 2;
		panelFiltros.add(comboFiltroMarca, gbc_comboFiltroMarca);

		radioButtonMarca = new JRadioButton("");
		radioButtonMarca.setBackground(new Color(176, 196, 222));
		radioButtonMarca.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonMarca = new GridBagConstraints();
		gbc_radioButtonMarca.anchor = GridBagConstraints.WEST;
		gbc_radioButtonMarca.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonMarca.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonMarca.gridx = 6;
		gbc_radioButtonMarca.gridy = 2;
		panelFiltros.add(radioButtonMarca, gbc_radioButtonMarca);

		JLabel lblNewLabel_8_1 = new JLabel("ESTADO TEC");
		lblNewLabel_8_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_8_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_8_1 = new GridBagConstraints();
		gbc_lblNewLabel_8_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8_1.gridx = 7;
		gbc_lblNewLabel_8_1.gridy = 2;
		panelFiltros.add(lblNewLabel_8_1, gbc_lblNewLabel_8_1);

		comboFiltroEstadoTec = new JComboBox<Object>();
		comboFiltroEstadoTec.setEnabled(false);
		comboFiltroEstadoTec.setBackground(new Color(176, 196, 222));
		comboFiltroEstadoTec.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroEstadoTec = new GridBagConstraints();
		gbc_comboFiltroEstadoTec.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEstadoTec.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEstadoTec.gridx = 8;
		gbc_comboFiltroEstadoTec.gridy = 2;
		panelFiltros.add(comboFiltroEstadoTec, gbc_comboFiltroEstadoTec);

		radioButtonEstadoTec = new JRadioButton("");
		radioButtonEstadoTec.setBackground(new Color(176, 196, 222));
		radioButtonEstadoTec.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonEstadoTec = new GridBagConstraints();
		gbc_radioButtonEstadoTec.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEstadoTec.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonEstadoTec.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEstadoTec.gridx = 9;
		gbc_radioButtonEstadoTec.gridy = 2;
		panelFiltros.add(radioButtonEstadoTec, gbc_radioButtonEstadoTec);

		chckbxPresupuestoGenerado = new JCheckBox("PRESUPUESTO GENERADO");
		chckbxPresupuestoGenerado.setEnabled(false);
		chckbxPresupuestoGenerado.setBackground(new Color(176, 196, 222));
		chckbxPresupuestoGenerado.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxPresupuestoGenerado = new GridBagConstraints();
		gbc_chckbxPresupuestoGenerado.anchor = GridBagConstraints.WEST;
		gbc_chckbxPresupuestoGenerado.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPresupuestoGenerado.gridx = 10;
		gbc_chckbxPresupuestoGenerado.gridy = 2;
		panelFiltros.add(chckbxPresupuestoGenerado, gbc_chckbxPresupuestoGenerado);

		radioButtonPresupGenerado = new JRadioButton("");
		radioButtonPresupGenerado.setBackground(new Color(176, 196, 222));
		radioButtonPresupGenerado.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonPresupGenerado = new GridBagConstraints();
		gbc_radioButtonPresupGenerado.anchor = GridBagConstraints.WEST;
		gbc_radioButtonPresupGenerado.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonPresupGenerado.gridx = 11;
		gbc_radioButtonPresupGenerado.gridy = 2;
		panelFiltros.add(radioButtonPresupGenerado, gbc_radioButtonPresupGenerado);

		JLabel lblNewLabel_3_1 = new JLabel("ELS");
		lblNewLabel_3_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.gridx = 1;
		gbc_lblNewLabel_3_1.gridy = 3;
		panelFiltros.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);

		comboFiltroELS = new JComboBox<Object>();
		comboFiltroELS.setEnabled(false);
		comboFiltroELS.setBackground(new Color(176, 196, 222));
		comboFiltroELS.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroELS = new GridBagConstraints();
		gbc_comboFiltroELS.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroELS.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroELS.gridx = 2;
		gbc_comboFiltroELS.gridy = 3;
		panelFiltros.add(comboFiltroELS, gbc_comboFiltroELS);

		radioButtonELS = new JRadioButton("");
		radioButtonELS.setBackground(new Color(176, 196, 222));
		radioButtonELS.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonELS = new GridBagConstraints();
		gbc_radioButtonELS.anchor = GridBagConstraints.WEST;
		gbc_radioButtonELS.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonELS.gridx = 3;
		gbc_radioButtonELS.gridy = 3;
		panelFiltros.add(radioButtonELS, gbc_radioButtonELS);

		JLabel lblNewLabel_7_1 = new JLabel("TÉCNICO");
		lblNewLabel_7_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_7_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_7_1 = new GridBagConstraints();
		gbc_lblNewLabel_7_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7_1.gridx = 4;
		gbc_lblNewLabel_7_1.gridy = 3;
		panelFiltros.add(lblNewLabel_7_1, gbc_lblNewLabel_7_1);

		comboFiltroTecnico = new JComboBox<Object>();
		comboFiltroTecnico.setEnabled(false);
		comboFiltroTecnico.setBackground(new Color(176, 196, 222));
		comboFiltroTecnico.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroTecnico = new GridBagConstraints();
		gbc_comboFiltroTecnico.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroTecnico.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroTecnico.gridx = 5;
		gbc_comboFiltroTecnico.gridy = 3;
		panelFiltros.add(comboFiltroTecnico, gbc_comboFiltroTecnico);

		radioButtonTecnico = new JRadioButton("");
		radioButtonTecnico.setBackground(new Color(176, 196, 222));
		radioButtonTecnico.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonTecnico = new GridBagConstraints();
		gbc_radioButtonTecnico.anchor = GridBagConstraints.WEST;
		gbc_radioButtonTecnico.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonTecnico.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonTecnico.gridx = 6;
		gbc_radioButtonTecnico.gridy = 3;
		panelFiltros.add(radioButtonTecnico, gbc_radioButtonTecnico);

		JLabel lblNewLabel_10_1 = new JLabel("ESTADO FIS");
		lblNewLabel_10_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_10_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_10_1 = new GridBagConstraints();
		gbc_lblNewLabel_10_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_10_1.gridx = 7;
		gbc_lblNewLabel_10_1.gridy = 3;
		panelFiltros.add(lblNewLabel_10_1, gbc_lblNewLabel_10_1);

		comboFiltroEstadoFis = new JComboBox<Object>();
		comboFiltroEstadoFis.setEnabled(false);
		comboFiltroEstadoFis.setBackground(new Color(176, 196, 222));
		comboFiltroEstadoFis.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroEstadoFis = new GridBagConstraints();
		gbc_comboFiltroEstadoFis.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEstadoFis.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEstadoFis.gridx = 8;
		gbc_comboFiltroEstadoFis.gridy = 3;
		panelFiltros.add(comboFiltroEstadoFis, gbc_comboFiltroEstadoFis);

		radioButtonEstadoFis = new JRadioButton("");
		radioButtonEstadoFis.setBackground(new Color(176, 196, 222));
		radioButtonEstadoFis.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonEstadoFis = new GridBagConstraints();
		gbc_radioButtonEstadoFis.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEstadoFis.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEstadoFis.gridx = 9;
		gbc_radioButtonEstadoFis.gridy = 3;
		panelFiltros.add(radioButtonEstadoFis, gbc_radioButtonEstadoFis);

		JLabel lblNewLabel_6_1 = new JLabel("AVISO");
		lblNewLabel_6_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_6_1 = new GridBagConstraints();
		gbc_lblNewLabel_6_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6_1.gridx = 1;
		gbc_lblNewLabel_6_1.gridy = 4;
		panelFiltros.add(lblNewLabel_6_1, gbc_lblNewLabel_6_1);

		comboFiltroAviso = new JComboBox<Object>();
		comboFiltroAviso.setEnabled(false);
		comboFiltroAviso.setBackground(new Color(176, 196, 222));
		comboFiltroAviso.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroAviso = new GridBagConstraints();
		gbc_comboFiltroAviso.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroAviso.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroAviso.gridx = 2;
		gbc_comboFiltroAviso.gridy = 4;
		panelFiltros.add(comboFiltroAviso, gbc_comboFiltroAviso);

		radioButtonAviso = new JRadioButton("");
		radioButtonAviso.setBackground(new Color(176, 196, 222));
		radioButtonAviso.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonAviso = new GridBagConstraints();
		gbc_radioButtonAviso.anchor = GridBagConstraints.WEST;
		gbc_radioButtonAviso.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonAviso.gridx = 3;
		gbc_radioButtonAviso.gridy = 4;
		panelFiltros.add(radioButtonAviso, gbc_radioButtonAviso);

		JLabel lblNewLabel_5_1 = new JLabel("MODELO");
		lblNewLabel_5_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_1.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_1.gridx = 4;
		gbc_lblNewLabel_5_1.gridy = 4;
		panelFiltros.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);

		comboFiltroModelo = new JComboBox<Object>();
		comboFiltroModelo.setEnabled(false);
		comboFiltroModelo.setBackground(new Color(176, 196, 222));
		comboFiltroModelo.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_comboFiltroModelo = new GridBagConstraints();
		gbc_comboFiltroModelo.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroModelo.gridx = 5;
		gbc_comboFiltroModelo.gridy = 4;
		panelFiltros.add(comboFiltroModelo, gbc_comboFiltroModelo);

		radioButtonModelo = new JRadioButton("");
		radioButtonModelo.setBackground(new Color(176, 196, 222));
		radioButtonModelo.setFont(new Font("Cambria", Font.BOLD, 13));
		GridBagConstraints gbc_radioButtonModelo = new GridBagConstraints();
		gbc_radioButtonModelo.anchor = GridBagConstraints.WEST;
		gbc_radioButtonModelo.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonModelo.gridx = 6;
		gbc_radioButtonModelo.gridy = 4;
		panelFiltros.add(radioButtonModelo, gbc_radioButtonModelo);
	
		panelTitulo = new JPanel();
		panelTitulo.setBackground(new Color(176, 196, 222));
		panelSuperior.add(panelTitulo, BorderLayout.NORTH);
		FlowLayout fl_panelTitulo = new FlowLayout(FlowLayout.CENTER, 400, 15);
		panelTitulo.setLayout(fl_panelTitulo);

		JLabel lbTitulo_1 = new JLabel("LISTADO DE EQUIPOS");
		lbTitulo_1.setFont(new Font("Cambria", Font.BOLD, 30));
		panelTitulo.add(lbTitulo_1 );
		
		btnMax = new JButton("");
		btnMax.setPreferredSize(new Dimension(50, 30));
		btnMax.setFont(new Font("Cambria", Font.BOLD, 14));
		btnMax.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
		panelTitulo.add(btnMax );
		
		panelBotones = new JPanel();
		panelBotones.setBackground(new Color(176, 196, 222));
		panelSuperior.add(panelBotones, BorderLayout.SOUTH);
		FlowLayout fl_panelBotones = new FlowLayout(FlowLayout.CENTER, 190, 15);
		panelBotones.setLayout(fl_panelBotones);
				
		btnFiltrar = new JButton("FILTRAR");
		btnFiltrar.setPreferredSize(new Dimension(150, 30));
		btnFiltrar.setFont(new Font("Cambria", Font.BOLD, 14));

		panelBotones.add(btnFiltrar);

		btnMostrarTodo = new JButton("MOSTRAR TODO");
		btnMostrarTodo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnMostrarTodo.setPreferredSize(new Dimension(150, 30));
		panelBotones.add(btnMostrarTodo);

		btnEstadisticas = new JButton("ESTADÍSTICAS");
		btnEstadisticas.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEstadisticas.setPreferredSize(new Dimension(150, 30));
		panelBotones.add(btnEstadisticas);

		panelInferior = new JPanel();
		panelInferior.setBackground(new Color(176, 196, 222));
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

		JLabel lblNewLabel = new JLabel("       ");
		panelInferior.add(lblNewLabel);

		panelCentral = new JPanel();
		panelCentral.setBackground(new Color(176, 196, 222));
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelCentral.add(scrollPane, BorderLayout.CENTER);

		
		modelReparaciones = new DefaultTableModel(new Object[][] {}, nombreColumnas) {

			
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, Boolean.class, Boolean.class,
					double.class, double.class, double.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};
		
		tblListado = new JTable(modelReparaciones);
		


		try {
			//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}

//		Font fuenteCabecera = new Font("Cambria", Font.BOLD, 12);
//		Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

		tblListado = new JTable(modelReparaciones);
		tblListado.setGridColor(new Color(105, 105, 105));
		tblListado.setBackground(new Color(176, 196, 222));
		tblListado.setOpaque(false);
		tblListado.setRowMargin(3);
		tblListado.setRowHeight(18);
	
		//tblListado.setFont(fuenteCeldas);

		//tblListado.getTableHeader().setForeground(Color.BLACK);
		//tblListado.getTableHeader().setFont(fuenteCabecera);
		//tblListado.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tblListado.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tblListado.setShowGrid(true);
		tblListado.setCellSelectionEnabled(true);

		// tblReparaciones_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// tblReparaciones_1.doLayout();

		scrollPane.setViewportView(tblListado);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tblListado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblListado.setAutoCreateColumnsFromModel(false);

		tblListado.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 60, 80, 150, 150, 200, 100, 150, 100, 100, 80, 110, 120, 150, 100, 100, 100, 100, 80, 80,
				100, 100, 100 };
		
//		int[] anchos = { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
//				30, 30, 30 };

		for (int i = 0; i < tblListado.getColumnCount(); i++) {

			tblListado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

		}
	

		this.setVisible(true);

	}
	
	
	
	
	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaListado());
		}
	}

	public DefaultTableModel getModelReparaciones() {
		return modelReparaciones;
	}

	public void setModelReparaciones(DefaultTableModel modelReparaciones) {
		this.modelReparaciones = modelReparaciones;
	}

	public JTable getTblReparaciones() {
		return tblListado;
	}

	public void setTblReparaciones(JTable tblReparaciones) {
		this.tblListado = tblReparaciones;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	public JComboBox<?> getComboFiltroCliente() {
		return comboFiltroCliente;
	}

	public void setComboFiltroCliente(JComboBox<?> comboFiltroCliente) {
		this.comboFiltroCliente = comboFiltroCliente;
	}

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public void setBtnFiltrar(JButton btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	public JButton getBtnMostrarTodo() {
		return btnMostrarTodo;
	}

	public void setBtnMostrarTodo(JButton btnMostrarTodo) {
		this.btnMostrarTodo = btnMostrarTodo;
	}

	public JComboBox<?> getComboFiltroMarca() {
		return comboFiltroMarca;
	}

	public void setComboFiltroMarca(JComboBox<?> comboFiltroMarca) {
		this.comboFiltroMarca = comboFiltroMarca;
	}

	public JRadioButton getRadioButtonMarca() {
		return radioButtonMarca;
	}

	public void setRadioButtonMarca(JRadioButton radioButtonMarca) {
		this.radioButtonMarca = radioButtonMarca;
	}

	public JRadioButton Button() {
		return radioButtonCliente;
	}

	public JRadioButton getRadioButtonCliente() {
		return radioButtonCliente;
	}

	public void setRadioButtonCliente(JRadioButton radioButtonCliente) {
		this.radioButtonCliente = radioButtonCliente;
	}

	public JComboBox<?> getComboFiltroSucursal() {
		return comboFiltroSucursal;
	}

	public void setComboFiltroSucursal(JComboBox<?> comboFiltroSucursal) {
		this.comboFiltroSucursal = comboFiltroSucursal;
	}

	public JRadioButton getRadioButtonSucursal() {
		return radioButtonSucursal;
	}

	public void setRadioButtonSucursal(JRadioButton radioButtonSucursal) {
		this.radioButtonSucursal = radioButtonSucursal;
	}

	public JComboBox<?> getComboFiltroEstadoFis() {
		return comboFiltroEstadoFis;
	}

	public void setComboFiltroEstadoFis(JComboBox<?> comboFiltroEstadoFis) {
		this.comboFiltroEstadoFis = comboFiltroEstadoFis;
	}

	public JComboBox<?> getComboFiltroEstadoCom() {
		return comboFiltroEstadoCom;
	}

	public void setComboFiltroEstadoCom(JComboBox<?> comboFiltroEstadoCom) {
		this.comboFiltroEstadoCom = comboFiltroEstadoCom;
	}

	public JComboBox<?> getComboFiltroEstadoTec() {
		return comboFiltroEstadoTec;
	}

	public void setComboFiltroEstadoTec(JComboBox<?> comboFiltroEstadoTec) {
		this.comboFiltroEstadoTec = comboFiltroEstadoTec;
	}

	public JRadioButton getRadioButtonEstadoFis() {
		return radioButtonEstadoFis;
	}

	public void setRadioButtonEstadoFis(JRadioButton radioButtonEstadoFis) {
		this.radioButtonEstadoFis = radioButtonEstadoFis;
	}

	public JRadioButton getRadioButtonEstadoCom() {
		return radioButtonEstadoCom;
	}

	public void setRadioButtonEstadoCom(JRadioButton radioButtonEstadoCom) {
		this.radioButtonEstadoCom = radioButtonEstadoCom;
	}

	public JRadioButton getRadioButtonEstadoTec() {
		return radioButtonEstadoTec;
	}

	public void setRadioButtonEstadoTec(JRadioButton radioButtonEstadoTec) {
		this.radioButtonEstadoTec = radioButtonEstadoTec;
	}

	public JCheckBox getChckbxPresupuestoGenerado() {
		return chckbxPresupuestoGenerado;
	}

	public void setChckbxPresupuestoGenerado(JCheckBox chckbxPresupuestoGenerado) {
		this.chckbxPresupuestoGenerado = chckbxPresupuestoGenerado;
	}

	public JCheckBox getChckbxPresupuestoEnviado() {
		return chckbxPresupuestoEnviado;
	}

	public void setChckbxPresupuestoEnviado(JCheckBox chckbxPresupuestoEnviado) {
		this.chckbxPresupuestoEnviado = chckbxPresupuestoEnviado;
	}

	public JComboBox<?> getComboFiltroEquipo() {
		return comboFiltroEquipo;
	}

	public void setComboFiltroEquipo(JComboBox<?> comboFiltroEquipo) {
		this.comboFiltroEquipo = comboFiltroEquipo;
	}

	public JComboBox<?> getComboFiltroModelo() {
		return comboFiltroModelo;
	}

	public void setComboFiltroModelo(JComboBox<?> comboFiltroModelo) {
		this.comboFiltroModelo = comboFiltroModelo;
	}

	public JComboBox<?> getRadio() {
		return comboFiltroAviso;
	}

	public void setComboFiltroAviso(JComboBox<?> comboFiltroAviso) {
		this.comboFiltroAviso = comboFiltroAviso;
	}

	public JRadioButton getRadioButtonEquipo() {
		return radioButtonEquipo;
	}

	public void setRadioButtonEquipo(JRadioButton radioButtonEquipo) {
		this.radioButtonEquipo = radioButtonEquipo;
	}

	public JRadioButton getRadioButtonModelo() {
		return radioButtonModelo;
	}

	public void setRadioButtonModelo(JRadioButton radioButtonModelo) {
		this.radioButtonModelo = radioButtonModelo;
	}

	public JRadioButton getRadioButtonAviso() {
		return radioButtonAviso;
	}

	public void setRadioButtonAviso(JRadioButton radioButtonAviso) {
		this.radioButtonAviso = radioButtonAviso;
	}

	public JRadioButton getRadioButtonPresupGenerado() {
		return radioButtonPresupGenerado;
	}

	public void setRadioButtonPresupGenerado(JRadioButton radioButtonPresupGenerado) {
		this.radioButtonPresupGenerado = radioButtonPresupGenerado;
	}

	public JRadioButton getRadioButtonPresupEnviado() {
		return radioButtonPresupEnviado;
	}

	public void setRadioButtonPresupEnviado(JRadioButton radioButtonPresupEnviado) {
		this.radioButtonPresupEnviado = radioButtonPresupEnviado;
	}

	public JComboBox<?> getComboFiltroAviso() {
		return comboFiltroAviso;
	}

	public JComboBox<?> getComboFiltroELS() {
		return comboFiltroELS;
	}

	public void setComboFiltroELS(JComboBox<?> comboFiltroELS) {
		this.comboFiltroELS = comboFiltroELS;
	}

	public JRadioButton getRadioButtonELS() {
		return radioButtonELS;
	}

	public void setRadioButtonELS(JRadioButton radioButtonELS) {
		this.radioButtonELS = radioButtonELS;
	}

	public JComboBox<?> getComboFiltroTecnico() {
		return comboFiltroTecnico;
	}

	public void setComboFiltroTecnico(JComboBox<?> comboFiltroTecnico) {
		this.comboFiltroTecnico = comboFiltroTecnico;
	}

	public JRadioButton getRadioButtonTecnico() {
		return radioButtonTecnico;
	}

	public void setRadioButtonTecnico(JRadioButton radioButtonTecnico) {
		this.radioButtonTecnico = radioButtonTecnico;
	}

	public JButton getBtnMax() {
		return btnMax;
	}

	public void setBtnMax(JButton btnMax) {
		this.btnMax = btnMax;
	}

	public JPanel getPanel() {
		return panelPrincipal;
	}

	public void setPanel(JPanel panel) {
		this.panelPrincipal = panel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JButton getBtnEstadisticas() {
		return btnEstadisticas;
	}

	public void setBtnEstadisticas(JButton btnEstadisticas) {
		this.btnEstadisticas = btnEstadisticas;
	}
	
	
	
	
	
	

}
