package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import VistaPropias.CellRendererTablaListado;
import presentacion.controlador.ControladorListados;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;

public class VentanaListadoReparaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tblListado, tablaELS;
	private DefaultTableModel modelReparaciones;

	private String[] nombreColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO",
			"N° SERIE", "AVISO", "REVISIÓN", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS", "TÉCNICO",
			"UBIC. REM", "NUM REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO" };
	private JButton btnMax;
	public static int est;

	private JPanel panelPrincipal;
	private JPanel panelFiltros;
	private JPanel panelSuperior;
	private JPanel panelTitulo;
	private JPanel panelInferior;
	private JPanel panelCentral;
	private JScrollPane scrollPane;

	@SuppressWarnings("unused")
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
	private JPanel panelBotonera;
	private JPanel panelBotones;
	private JButton btnFiltrar;
	private JButton btnMostrarTodo;
	private JButton btnEstadisticas;
	private JPanel panelColumnas;
	private JCheckBox chckbxSucursal;
	private JCheckBox chckbxModelo;
	private JCheckBox chckbxRevisión;
	private JCheckBox chckbxEntrada;
	private JCheckBox chckbxEquipo;
	private JCheckBox chckbxELS;
	private JCheckBox chckbxClienteCliente;
	private JCheckBox chckbxSerie;
	private JCheckBox chckbxCliente;
	private JCheckBox chckbxMarca;
	private JCheckBox chckbxAviso;
	private JCheckBox chckbxEstadoTec;
	private JCheckBox chckbxEstadoCom;
	private JCheckBox chckbxEstadoFis;
	private JCheckBox chckbxTecnico;
	private JCheckBox chckbxUbicacionRemito;
	private JCheckBox chckbxNumeroRemito;
	private JCheckBox chckbxPresupuestoGeneradoColumna;
	private JCheckBox chckbxPresupuestoEnviadoColumna;
	private JCheckBox chckbxPago;
	private JCheckBox chckbxPrecioPeso;
	private JCheckBox chckbxPrecioDolar;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_7;

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
		setSize(1200, 680);
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
		panelFiltros.setFont(new Font("Cambria", Font.PLAIN, 10));
		panelFiltros.setBackground(new Color(176, 196, 222));
		panelFiltros.setBorder(new LineBorder(new Color(0, 128, 128)));

		panelFiltros.setBounds(35, 50, 994, 134);
		panelSuperior.add(panelFiltros, BorderLayout.CENTER);
		GridBagLayout gbl_panelFiltros = new GridBagLayout();
		gbl_panelFiltros.columnWidths = new int[] { 30, 50, 150, 50, 50, 150, 50, 80, 150, 50, 90, 50, 30 };
		gbl_panelFiltros.rowHeights = new int[] { 10, 10, 10, 10, 10, 10 };
		gbl_panelFiltros.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				0.0 };

		gbl_panelFiltros.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelFiltros.setLayout(gbl_panelFiltros);

		JLabel lblNewLabel_1 = new JLabel("CLIENTE");
		lblNewLabel_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelFiltros.add(lblNewLabel_1, gbc_lblNewLabel_1);

		comboFiltroCliente = new JComboBox<Object>();
		comboFiltroCliente.setEnabled(false);
		comboFiltroCliente.setBackground(new Color(176, 196, 222));
		comboFiltroCliente.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroCliente.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroCliente = new GridBagConstraints();
		gbc_comboFiltroCliente.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroCliente.gridx = 2;
		gbc_comboFiltroCliente.gridy = 1;
		panelFiltros.add(comboFiltroCliente, gbc_comboFiltroCliente);

		radioButtonCliente = new JRadioButton("");
		radioButtonCliente.setBackground(new Color(176, 196, 222));
		radioButtonCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonCliente = new GridBagConstraints();
		gbc_radioButtonCliente.anchor = GridBagConstraints.WEST;
		gbc_radioButtonCliente.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonCliente.gridx = 3;
		gbc_radioButtonCliente.gridy = 1;
		panelFiltros.add(radioButtonCliente, gbc_radioButtonCliente);

		JLabel lblNewLabel_4_1 = new JLabel("EQUIPO");
		lblNewLabel_4_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_4_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_4_1 = new GridBagConstraints();
		gbc_lblNewLabel_4_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4_1.gridx = 4;
		gbc_lblNewLabel_4_1.gridy = 1;
		panelFiltros.add(lblNewLabel_4_1, gbc_lblNewLabel_4_1);

		comboFiltroEquipo = new JComboBox<Object>();
		comboFiltroEquipo.setEnabled(false);
		comboFiltroEquipo.setBackground(new Color(176, 196, 222));
		comboFiltroEquipo.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEquipo.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroEquipo = new GridBagConstraints();
		gbc_comboFiltroEquipo.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEquipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEquipo.gridx = 5;
		gbc_comboFiltroEquipo.gridy = 1;
		panelFiltros.add(comboFiltroEquipo, gbc_comboFiltroEquipo);

		radioButtonEquipo = new JRadioButton("");
		radioButtonEquipo.setBackground(new Color(176, 196, 222));
		radioButtonEquipo.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonEquipo = new GridBagConstraints();
		gbc_radioButtonEquipo.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEquipo.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonEquipo.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEquipo.gridx = 6;
		gbc_radioButtonEquipo.gridy = 1;
		panelFiltros.add(radioButtonEquipo, gbc_radioButtonEquipo);

		JLabel lblNewLabel_9_1 = new JLabel("ESTADO COM");
		lblNewLabel_9_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_9_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_9_1 = new GridBagConstraints();
		gbc_lblNewLabel_9_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_9_1.gridx = 7;
		gbc_lblNewLabel_9_1.gridy = 1;
		panelFiltros.add(lblNewLabel_9_1, gbc_lblNewLabel_9_1);

		comboFiltroEstadoCom = new JComboBox<Object>();
		comboFiltroEstadoCom.setEnabled(false);
		comboFiltroEstadoCom.setBackground(new Color(176, 196, 222));
		comboFiltroEstadoCom.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEstadoCom.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroEstadoCom = new GridBagConstraints();
		gbc_comboFiltroEstadoCom.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEstadoCom.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEstadoCom.gridx = 8;
		gbc_comboFiltroEstadoCom.gridy = 1;
		panelFiltros.add(comboFiltroEstadoCom, gbc_comboFiltroEstadoCom);

		radioButtonEstadoCom = new JRadioButton("");
		radioButtonEstadoCom.setBackground(new Color(176, 196, 222));
		radioButtonEstadoCom.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonEstadoCom = new GridBagConstraints();
		gbc_radioButtonEstadoCom.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEstadoCom.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonEstadoCom.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEstadoCom.gridx = 9;
		gbc_radioButtonEstadoCom.gridy = 1;
		panelFiltros.add(radioButtonEstadoCom, gbc_radioButtonEstadoCom);

		chckbxPresupuestoEnviado = new JCheckBox("PRESUPUESTO ENVIADO");
		chckbxPresupuestoEnviado.setPreferredSize(new Dimension(170, 20));
		chckbxPresupuestoEnviado.setEnabled(false);
		chckbxPresupuestoEnviado.setBackground(new Color(176, 196, 222));
		chckbxPresupuestoEnviado.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_chckbxPresupuestoEnviado = new GridBagConstraints();
		gbc_chckbxPresupuestoEnviado.anchor = GridBagConstraints.WEST;
		gbc_chckbxPresupuestoEnviado.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPresupuestoEnviado.gridx = 10;
		gbc_chckbxPresupuestoEnviado.gridy = 1;
		panelFiltros.add(chckbxPresupuestoEnviado, gbc_chckbxPresupuestoEnviado);

		radioButtonPresupEnviado = new JRadioButton("");
		radioButtonPresupEnviado.setBackground(new Color(176, 196, 222));
		radioButtonPresupEnviado.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonPresupEnviado = new GridBagConstraints();
		gbc_radioButtonPresupEnviado.anchor = GridBagConstraints.WEST;
		gbc_radioButtonPresupEnviado.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonPresupEnviado.gridx = 11;
		gbc_radioButtonPresupEnviado.gridy = 1;
		panelFiltros.add(radioButtonPresupEnviado, gbc_radioButtonPresupEnviado);

		JLabel lblNewLabel_1_1 = new JLabel("SUCURSAL");
		lblNewLabel_1_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.gridx = 1;
		gbc_lblNewLabel_1_1.gridy = 2;
		panelFiltros.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);

		comboFiltroSucursal = new JComboBox<Object>();
		comboFiltroSucursal.setEnabled(false);
		comboFiltroSucursal.setBackground(new Color(176, 196, 222));
		comboFiltroSucursal.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroSucursal.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroSucursal = new GridBagConstraints();
		gbc_comboFiltroSucursal.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroSucursal.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroSucursal.gridx = 2;
		gbc_comboFiltroSucursal.gridy = 2;
		panelFiltros.add(comboFiltroSucursal, gbc_comboFiltroSucursal);

		radioButtonSucursal = new JRadioButton("");
		radioButtonSucursal.setBackground(new Color(176, 196, 222));
		radioButtonSucursal.setFont(new Font("Cambria", Font.BOLD, 12));
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
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2_1.gridx = 4;
		gbc_lblNewLabel_2_1.gridy = 2;
		panelFiltros.add(lblNewLabel_2_1, gbc_lblNewLabel_2_1);

		comboFiltroMarca = new JComboBox<Object>();
		comboFiltroMarca.setEnabled(false);
		comboFiltroMarca.setBackground(new Color(176, 196, 222));
		comboFiltroMarca.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroMarca.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroMarca = new GridBagConstraints();
		gbc_comboFiltroMarca.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroMarca.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroMarca.gridx = 5;
		gbc_comboFiltroMarca.gridy = 2;
		panelFiltros.add(comboFiltroMarca, gbc_comboFiltroMarca);

		radioButtonMarca = new JRadioButton("");
		radioButtonMarca.setBackground(new Color(176, 196, 222));
		radioButtonMarca.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonMarca = new GridBagConstraints();
		gbc_radioButtonMarca.anchor = GridBagConstraints.WEST;
		gbc_radioButtonMarca.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonMarca.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonMarca.gridx = 6;
		gbc_radioButtonMarca.gridy = 2;
		panelFiltros.add(radioButtonMarca, gbc_radioButtonMarca);

		JLabel lblNewLabel_8_1 = new JLabel("ESTADO TEC");
		lblNewLabel_8_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_8_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_8_1 = new GridBagConstraints();
		gbc_lblNewLabel_8_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8_1.gridx = 7;
		gbc_lblNewLabel_8_1.gridy = 2;
		panelFiltros.add(lblNewLabel_8_1, gbc_lblNewLabel_8_1);

		comboFiltroEstadoTec = new JComboBox<Object>();
		comboFiltroEstadoTec.setEnabled(false);
		comboFiltroEstadoTec.setBackground(new Color(176, 196, 222));
		comboFiltroEstadoTec.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEstadoTec.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroEstadoTec = new GridBagConstraints();
		gbc_comboFiltroEstadoTec.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEstadoTec.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEstadoTec.gridx = 8;
		gbc_comboFiltroEstadoTec.gridy = 2;
		panelFiltros.add(comboFiltroEstadoTec, gbc_comboFiltroEstadoTec);

		radioButtonEstadoTec = new JRadioButton("");
		radioButtonEstadoTec.setBackground(new Color(176, 196, 222));
		radioButtonEstadoTec.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonEstadoTec = new GridBagConstraints();
		gbc_radioButtonEstadoTec.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEstadoTec.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonEstadoTec.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEstadoTec.gridx = 9;
		gbc_radioButtonEstadoTec.gridy = 2;
		panelFiltros.add(radioButtonEstadoTec, gbc_radioButtonEstadoTec);

		chckbxPresupuestoGenerado = new JCheckBox("PRESUPUESTO GENERADO");
		chckbxPresupuestoGenerado.setPreferredSize(new Dimension(170, 20));
		chckbxPresupuestoGenerado.setEnabled(false);
		chckbxPresupuestoGenerado.setBackground(new Color(176, 196, 222));
		chckbxPresupuestoGenerado.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_chckbxPresupuestoGenerado = new GridBagConstraints();
		gbc_chckbxPresupuestoGenerado.anchor = GridBagConstraints.WEST;
		gbc_chckbxPresupuestoGenerado.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPresupuestoGenerado.gridx = 10;
		gbc_chckbxPresupuestoGenerado.gridy = 2;
		panelFiltros.add(chckbxPresupuestoGenerado, gbc_chckbxPresupuestoGenerado);

		radioButtonPresupGenerado = new JRadioButton("");
		radioButtonPresupGenerado.setBackground(new Color(176, 196, 222));
		radioButtonPresupGenerado.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonPresupGenerado = new GridBagConstraints();
		gbc_radioButtonPresupGenerado.anchor = GridBagConstraints.WEST;
		gbc_radioButtonPresupGenerado.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonPresupGenerado.gridx = 11;
		gbc_radioButtonPresupGenerado.gridy = 2;
		panelFiltros.add(radioButtonPresupGenerado, gbc_radioButtonPresupGenerado);

		JLabel lblNewLabel_3_1 = new JLabel("ELS");
		lblNewLabel_3_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.gridx = 1;
		gbc_lblNewLabel_3_1.gridy = 3;
		panelFiltros.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);

		comboFiltroELS = new JComboBox<Object>();
		comboFiltroELS.setEnabled(false);
		comboFiltroELS.setBackground(new Color(176, 196, 222));
		comboFiltroELS.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroELS.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroELS = new GridBagConstraints();
		gbc_comboFiltroELS.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroELS.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroELS.gridx = 2;
		gbc_comboFiltroELS.gridy = 3;
		panelFiltros.add(comboFiltroELS, gbc_comboFiltroELS);

		radioButtonELS = new JRadioButton("");
		radioButtonELS.setBackground(new Color(176, 196, 222));
		radioButtonELS.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonELS = new GridBagConstraints();
		gbc_radioButtonELS.anchor = GridBagConstraints.WEST;
		gbc_radioButtonELS.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonELS.gridx = 3;
		gbc_radioButtonELS.gridy = 3;
		panelFiltros.add(radioButtonELS, gbc_radioButtonELS);

		JLabel lblNewLabel_7_1 = new JLabel("TÉCNICO");
		lblNewLabel_7_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_7_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_7_1 = new GridBagConstraints();
		gbc_lblNewLabel_7_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7_1.gridx = 4;
		gbc_lblNewLabel_7_1.gridy = 3;
		panelFiltros.add(lblNewLabel_7_1, gbc_lblNewLabel_7_1);

		comboFiltroTecnico = new JComboBox<Object>();
		comboFiltroTecnico.setEnabled(false);
		comboFiltroTecnico.setBackground(new Color(176, 196, 222));
		comboFiltroTecnico.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroTecnico.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroTecnico = new GridBagConstraints();
		gbc_comboFiltroTecnico.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroTecnico.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroTecnico.gridx = 5;
		gbc_comboFiltroTecnico.gridy = 3;
		panelFiltros.add(comboFiltroTecnico, gbc_comboFiltroTecnico);

		radioButtonTecnico = new JRadioButton("");
		radioButtonTecnico.setBackground(new Color(176, 196, 222));
		radioButtonTecnico.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonTecnico = new GridBagConstraints();
		gbc_radioButtonTecnico.anchor = GridBagConstraints.WEST;
		gbc_radioButtonTecnico.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonTecnico.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonTecnico.gridx = 6;
		gbc_radioButtonTecnico.gridy = 3;
		panelFiltros.add(radioButtonTecnico, gbc_radioButtonTecnico);

		JLabel lblNewLabel_10_1 = new JLabel("ESTADO FIS");
		lblNewLabel_10_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_10_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_10_1 = new GridBagConstraints();
		gbc_lblNewLabel_10_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_10_1.gridx = 7;
		gbc_lblNewLabel_10_1.gridy = 3;
		panelFiltros.add(lblNewLabel_10_1, gbc_lblNewLabel_10_1);

		comboFiltroEstadoFis = new JComboBox<Object>();
		comboFiltroEstadoFis.setEnabled(false);
		comboFiltroEstadoFis.setBackground(new Color(176, 196, 222));
		comboFiltroEstadoFis.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEstadoFis.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroEstadoFis = new GridBagConstraints();
		gbc_comboFiltroEstadoFis.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroEstadoFis.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroEstadoFis.gridx = 8;
		gbc_comboFiltroEstadoFis.gridy = 3;
		panelFiltros.add(comboFiltroEstadoFis, gbc_comboFiltroEstadoFis);

		radioButtonEstadoFis = new JRadioButton("");
		radioButtonEstadoFis.setBackground(new Color(176, 196, 222));
		radioButtonEstadoFis.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonEstadoFis = new GridBagConstraints();
		gbc_radioButtonEstadoFis.anchor = GridBagConstraints.WEST;
		gbc_radioButtonEstadoFis.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonEstadoFis.gridx = 9;
		gbc_radioButtonEstadoFis.gridy = 3;
		panelFiltros.add(radioButtonEstadoFis, gbc_radioButtonEstadoFis);

		JLabel lblNewLabel_6_1 = new JLabel("AVISO");
		lblNewLabel_6_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_6_1 = new GridBagConstraints();
		gbc_lblNewLabel_6_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6_1.gridx = 1;
		gbc_lblNewLabel_6_1.gridy = 4;
		panelFiltros.add(lblNewLabel_6_1, gbc_lblNewLabel_6_1);

		comboFiltroAviso = new JComboBox<Object>();
		comboFiltroAviso.setEnabled(false);
		comboFiltroAviso.setBackground(new Color(176, 196, 222));
		comboFiltroAviso.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroAviso.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroAviso = new GridBagConstraints();
		gbc_comboFiltroAviso.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroAviso.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroAviso.gridx = 2;
		gbc_comboFiltroAviso.gridy = 4;
		panelFiltros.add(comboFiltroAviso, gbc_comboFiltroAviso);

		radioButtonAviso = new JRadioButton("");
		radioButtonAviso.setBackground(new Color(176, 196, 222));
		radioButtonAviso.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonAviso = new GridBagConstraints();
		gbc_radioButtonAviso.anchor = GridBagConstraints.WEST;
		gbc_radioButtonAviso.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonAviso.gridx = 3;
		gbc_radioButtonAviso.gridy = 4;
		panelFiltros.add(radioButtonAviso, gbc_radioButtonAviso);

		JLabel lblNewLabel_5_1 = new JLabel("MODELO");
		lblNewLabel_5_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_1.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_1.gridx = 4;
		gbc_lblNewLabel_5_1.gridy = 4;
		panelFiltros.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);

		comboFiltroModelo = new JComboBox<Object>();
		comboFiltroModelo.setEnabled(false);
		comboFiltroModelo.setBackground(new Color(176, 196, 222));
		comboFiltroModelo.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroModelo.setPreferredSize(new Dimension(150, 20));
		GridBagConstraints gbc_comboFiltroModelo = new GridBagConstraints();
		gbc_comboFiltroModelo.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroModelo.gridx = 5;
		gbc_comboFiltroModelo.gridy = 4;
		panelFiltros.add(comboFiltroModelo, gbc_comboFiltroModelo);

		radioButtonModelo = new JRadioButton("");
		radioButtonModelo.setBackground(new Color(176, 196, 222));
		radioButtonModelo.setFont(new Font("Cambria", Font.BOLD, 12));
		GridBagConstraints gbc_radioButtonModelo = new GridBagConstraints();
		gbc_radioButtonModelo.anchor = GridBagConstraints.WEST;
		gbc_radioButtonModelo.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonModelo.gridx = 6;
		gbc_radioButtonModelo.gridy = 4;
		panelFiltros.add(radioButtonModelo, gbc_radioButtonModelo);

		panelTitulo = new JPanel();
		panelTitulo.setBorder(new LineBorder(new Color(0, 128, 128)));
		panelTitulo.setBackground(new Color(176, 196, 222));
		panelSuperior.add(panelTitulo, BorderLayout.NORTH);
		FlowLayout fl_panelTitulo = new FlowLayout(FlowLayout.LEFT, 25, 10);
		panelTitulo.setLayout(fl_panelTitulo);

		JLabel lbTitulo_1 = new JLabel("LISTADO DE EQUIPOS");
		lbTitulo_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lbTitulo_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbTitulo_1.setFont(new Font("Cambria", Font.BOLD, 30));
		panelTitulo.add(lbTitulo_1);

		btnMax = new JButton("");
		btnMax.setVisible(false);
		btnMax.setPreferredSize(new Dimension(50, 30));
		btnMax.setFont(new Font("Cambria", Font.BOLD, 14));
		btnMax.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
		panelTitulo.add(btnMax);

		panelBotonera = new JPanel();
		panelSuperior.add(panelBotonera, BorderLayout.SOUTH);
		panelBotonera.setLayout(new BorderLayout(0, 0));

		panelBotones = new JPanel();
		panelBotones.setBorder(new LineBorder(new Color(0, 128, 128)));
		panelBotones.setBackground(new Color(176, 196, 222));
		panelBotonera.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 190, 5));

		btnFiltrar = new JButton("FILTRAR");
		btnFiltrar.setPreferredSize(new Dimension(150, 30));
		btnFiltrar.setFont(new Font("Cambria", Font.BOLD, 14));
		panelBotones.add(btnFiltrar);

		btnMostrarTodo = new JButton("MOSTRAR TODO");
		btnMostrarTodo.setPreferredSize(new Dimension(150, 30));
		btnMostrarTodo.setFont(new Font("Cambria", Font.BOLD, 14));
		panelBotones.add(btnMostrarTodo);

		btnEstadisticas = new JButton("ESTADÍSTICAS");
		btnEstadisticas.setPreferredSize(new Dimension(150, 30));
		btnEstadisticas.setFont(new Font("Cambria", Font.BOLD, 14));
		panelBotones.add(btnEstadisticas);

		panelColumnas = new JPanel();
		panelColumnas.setBorder(new LineBorder(new Color(0, 128, 128)));
		panelColumnas.setBackground(new Color(176, 196, 222));
		panelBotonera.add(panelColumnas, BorderLayout.NORTH);
		GridBagLayout gbl_panelColumnas = new GridBagLayout();
		gbl_panelColumnas.columnWidths = new int[] { 30, 100, 100, 100, 100, 100, 100, 10 };
		gbl_panelColumnas.rowHeights = new int[] { 10, 10, 10, 10, 10 };
		gbl_panelColumnas.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0 };
		gbl_panelColumnas.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelColumnas.setLayout(gbl_panelColumnas);

		chckbxELS = new JCheckBox("ELS");
		chckbxELS.setOpaque(false);
		chckbxELS.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxELS.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxELS = new GridBagConstraints();
		gbc_chckbxELS.insets = new Insets(5, 5, 0, 5);
		gbc_chckbxELS.anchor = GridBagConstraints.WEST;
		gbc_chckbxELS.gridx = 1;
		gbc_chckbxELS.gridy = 0;
		panelColumnas.add(chckbxELS, gbc_chckbxELS);

		chckbxEquipo = new JCheckBox("EQUIPO");
		chckbxEquipo.setOpaque(false);
		chckbxEquipo.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxEquipo.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxEquipo = new GridBagConstraints();
		gbc_chckbxEquipo.insets = new Insets(5, 5, 0, 5);
		gbc_chckbxEquipo.anchor = GridBagConstraints.WEST;
		gbc_chckbxEquipo.gridx = 2;
		gbc_chckbxEquipo.gridy = 0;
		panelColumnas.add(chckbxEquipo, gbc_chckbxEquipo);

		chckbxRevisión = new JCheckBox("REVISIÓN");
		chckbxRevisión.setOpaque(false);
		chckbxRevisión.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxRevisión.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxRevisión = new GridBagConstraints();
		gbc_chckbxRevisión.insets = new Insets(5, 5, 0, 5);
		gbc_chckbxRevisión.anchor = GridBagConstraints.WEST;
		gbc_chckbxRevisión.gridx = 3;
		gbc_chckbxRevisión.gridy = 0;
		panelColumnas.add(chckbxRevisión, gbc_chckbxRevisión);

		chckbxEstadoFis = new JCheckBox("ESTADO FÍSICO");
		chckbxEstadoFis.setOpaque(false);
		chckbxEstadoFis.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxEstadoFis.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxEstadoFis = new GridBagConstraints();
		gbc_chckbxEstadoFis.insets = new Insets(5, 5, 0, 5);
		gbc_chckbxEstadoFis.anchor = GridBagConstraints.WEST;
		gbc_chckbxEstadoFis.gridx = 4;
		gbc_chckbxEstadoFis.gridy = 0;
		panelColumnas.add(chckbxEstadoFis, gbc_chckbxEstadoFis);

		chckbxNumeroRemito = new JCheckBox("NÚMERO REMITO");
		chckbxNumeroRemito.setOpaque(false);
		chckbxNumeroRemito.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxNumeroRemito.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxNumeroRemito = new GridBagConstraints();
		gbc_chckbxNumeroRemito.insets = new Insets(5, 5, 0, 5);
		gbc_chckbxNumeroRemito.anchor = GridBagConstraints.WEST;
		gbc_chckbxNumeroRemito.gridx = 5;
		gbc_chckbxNumeroRemito.gridy = 0;
		panelColumnas.add(chckbxNumeroRemito, gbc_chckbxNumeroRemito);

		chckbxPrecioDolar = new JCheckBox("PRECIO DOLAR");
		chckbxPrecioDolar.setOpaque(false);
		chckbxPrecioDolar.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxPrecioDolar.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxPrecioDolar = new GridBagConstraints();
		gbc_chckbxPrecioDolar.insets = new Insets(5, 5, 0, 5);
		gbc_chckbxPrecioDolar.anchor = GridBagConstraints.WEST;
		gbc_chckbxPrecioDolar.gridx = 6;
		gbc_chckbxPrecioDolar.gridy = 0;
		panelColumnas.add(chckbxPrecioDolar, gbc_chckbxPrecioDolar);

		lblNewLabel_3 = new JLabel("     ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(5, 5, 0, 5);
		gbc_lblNewLabel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_3.gridx = 7;
		gbc_lblNewLabel_3.gridy = 0;
		panelColumnas.add(lblNewLabel_3, gbc_lblNewLabel_3);

		lblNewLabel_2 = new JLabel("OCULTAR");
		lblNewLabel_2.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 20, 0, 20);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		panelColumnas.add(lblNewLabel_2, gbc_lblNewLabel_2);

		chckbxEntrada = new JCheckBox("ENTRADA");
		chckbxEntrada.setOpaque(false);
		chckbxEntrada.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxEntrada.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxEntrada = new GridBagConstraints();
		gbc_chckbxEntrada.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxEntrada.anchor = GridBagConstraints.WEST;
		gbc_chckbxEntrada.gridx = 1;
		gbc_chckbxEntrada.gridy = 1;
		panelColumnas.add(chckbxEntrada, gbc_chckbxEntrada);

		chckbxMarca = new JCheckBox("MARCA");
		chckbxMarca.setOpaque(false);
		chckbxMarca.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxMarca.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxMarca = new GridBagConstraints();
		gbc_chckbxMarca.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxMarca.anchor = GridBagConstraints.WEST;
		gbc_chckbxMarca.gridx = 2;
		gbc_chckbxMarca.gridy = 1;
		panelColumnas.add(chckbxMarca, gbc_chckbxMarca);

		chckbxClienteCliente = new JCheckBox("CLIENTE/CLIENTE");
		chckbxClienteCliente.setOpaque(false);
		chckbxClienteCliente.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxClienteCliente.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxClienteCliente = new GridBagConstraints();
		gbc_chckbxClienteCliente.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxClienteCliente.anchor = GridBagConstraints.WEST;
		gbc_chckbxClienteCliente.gridx = 3;
		gbc_chckbxClienteCliente.gridy = 1;
		panelColumnas.add(chckbxClienteCliente, gbc_chckbxClienteCliente);

		chckbxEstadoTec = new JCheckBox("ESTADO TÉCNICO");
		chckbxEstadoTec.setOpaque(false);
		chckbxEstadoTec.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxEstadoTec.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxEstadoTec = new GridBagConstraints();
		gbc_chckbxEstadoTec.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxEstadoTec.anchor = GridBagConstraints.WEST;
		gbc_chckbxEstadoTec.gridx = 4;
		gbc_chckbxEstadoTec.gridy = 1;
		panelColumnas.add(chckbxEstadoTec, gbc_chckbxEstadoTec);

		chckbxPresupuestoGeneradoColumna = new JCheckBox("PRESUPUESTO GENERADO");
		chckbxPresupuestoGeneradoColumna.setOpaque(false);
		chckbxPresupuestoGeneradoColumna.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxPresupuestoGeneradoColumna.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxPresupuestoGeneradoColumna = new GridBagConstraints();
		gbc_chckbxPresupuestoGeneradoColumna.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxPresupuestoGeneradoColumna.anchor = GridBagConstraints.WEST;
		gbc_chckbxPresupuestoGeneradoColumna.gridx = 5;
		gbc_chckbxPresupuestoGeneradoColumna.gridy = 1;
		panelColumnas.add(chckbxPresupuestoGeneradoColumna, gbc_chckbxPresupuestoGeneradoColumna);

		chckbxPago = new JCheckBox("PAGO");
		chckbxPago.setOpaque(false);
		chckbxPago.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxPago.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxPago = new GridBagConstraints();
		gbc_chckbxPago.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxPago.anchor = GridBagConstraints.WEST;
		gbc_chckbxPago.gridx = 6;
		gbc_chckbxPago.gridy = 1;
		panelColumnas.add(chckbxPago, gbc_chckbxPago);

		lblNewLabel_7 = new JLabel("COLUMNAS");
		lblNewLabel_7.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 20, 0, 20);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 2;
		panelColumnas.add(lblNewLabel_7, gbc_lblNewLabel_7);

		chckbxCliente = new JCheckBox("CLIENTE");
		chckbxCliente.setOpaque(false);
		chckbxCliente.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxCliente.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxCliente = new GridBagConstraints();
		gbc_chckbxCliente.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxCliente.anchor = GridBagConstraints.WEST;
		gbc_chckbxCliente.gridx = 1;
		gbc_chckbxCliente.gridy = 2;
		panelColumnas.add(chckbxCliente, gbc_chckbxCliente);

		chckbxModelo = new JCheckBox("MODELO");
		chckbxModelo.setOpaque(false);
		chckbxModelo.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxModelo.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxModelo = new GridBagConstraints();
		gbc_chckbxModelo.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxModelo.anchor = GridBagConstraints.WEST;
		gbc_chckbxModelo.gridx = 2;
		gbc_chckbxModelo.gridy = 2;
		panelColumnas.add(chckbxModelo, gbc_chckbxModelo);

		chckbxAviso = new JCheckBox("AVISO");
		chckbxAviso.setOpaque(false);
		chckbxAviso.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxAviso.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxAviso = new GridBagConstraints();
		gbc_chckbxAviso.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxAviso.anchor = GridBagConstraints.WEST;
		gbc_chckbxAviso.gridx = 3;
		gbc_chckbxAviso.gridy = 2;
		panelColumnas.add(chckbxAviso, gbc_chckbxAviso);

		chckbxTecnico = new JCheckBox("TÉCNICO");
		chckbxTecnico.setOpaque(false);
		chckbxTecnico.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxTecnico.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxTecnico = new GridBagConstraints();
		gbc_chckbxTecnico.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxTecnico.anchor = GridBagConstraints.WEST;
		gbc_chckbxTecnico.gridx = 4;
		gbc_chckbxTecnico.gridy = 2;
		panelColumnas.add(chckbxTecnico, gbc_chckbxTecnico);

		chckbxPresupuestoEnviadoColumna = new JCheckBox("PRESUPUESTO ENVIADO");
		chckbxPresupuestoEnviadoColumna.setOpaque(false);
		chckbxPresupuestoEnviadoColumna.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxPresupuestoEnviadoColumna.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxPresupúestoEnviadoColumna = new GridBagConstraints();
		gbc_chckbxPresupúestoEnviadoColumna.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxPresupúestoEnviadoColumna.anchor = GridBagConstraints.WEST;
		gbc_chckbxPresupúestoEnviadoColumna.gridx = 5;
		gbc_chckbxPresupúestoEnviadoColumna.gridy = 2;
		panelColumnas.add(chckbxPresupuestoEnviadoColumna, gbc_chckbxPresupúestoEnviadoColumna);

		chckbxSucursal = new JCheckBox("SUCURSAL");
		chckbxSucursal.setOpaque(false);
		chckbxSucursal.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxSucursal.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxSucursal = new GridBagConstraints();
		gbc_chckbxSucursal.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxSucursal.anchor = GridBagConstraints.WEST;
		gbc_chckbxSucursal.gridx = 1;
		gbc_chckbxSucursal.gridy = 3;
		panelColumnas.add(chckbxSucursal, gbc_chckbxSucursal);

		chckbxSerie = new JCheckBox("SERIE");
		chckbxSerie.setOpaque(false);
		chckbxSerie.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxSerie.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxSerie = new GridBagConstraints();
		gbc_chckbxSerie.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxSerie.anchor = GridBagConstraints.WEST;
		gbc_chckbxSerie.gridx = 2;
		gbc_chckbxSerie.gridy = 3;
		panelColumnas.add(chckbxSerie, gbc_chckbxSerie);

		chckbxEstadoCom = new JCheckBox("ESTADO COMERCIAL");
		chckbxEstadoCom.setOpaque(false);
		chckbxEstadoCom.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxEstadoCom.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxEstadoCom = new GridBagConstraints();
		gbc_chckbxEstadoCom.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxEstadoCom.anchor = GridBagConstraints.WEST;
		gbc_chckbxEstadoCom.gridx = 3;
		gbc_chckbxEstadoCom.gridy = 3;
		panelColumnas.add(chckbxEstadoCom, gbc_chckbxEstadoCom);

		chckbxUbicacionRemito = new JCheckBox("UBICACIÓN REMITO");
		chckbxUbicacionRemito.setOpaque(false);
		chckbxUbicacionRemito.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxUbicacionRemito.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxUbicacionRemito = new GridBagConstraints();
		gbc_chckbxUbicacionRemito.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxUbicacionRemito.anchor = GridBagConstraints.WEST;
		gbc_chckbxUbicacionRemito.gridx = 4;
		gbc_chckbxUbicacionRemito.gridy = 3;
		panelColumnas.add(chckbxUbicacionRemito, gbc_chckbxUbicacionRemito);

		chckbxPrecioPeso = new JCheckBox("PRECIO PESO");
		chckbxPrecioPeso.setOpaque(false);
		chckbxPrecioPeso.setFont(new Font("Cambria", Font.PLAIN, 10));
		chckbxPrecioPeso.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxPrecioPeso = new GridBagConstraints();
		gbc_chckbxPrecioPeso.insets = new Insets(0, 5, 0, 5);
		gbc_chckbxPrecioPeso.anchor = GridBagConstraints.WEST;
		gbc_chckbxPrecioPeso.gridx = 5;
		gbc_chckbxPrecioPeso.gridy = 3;
		panelColumnas.add(chckbxPrecioPeso, gbc_chckbxPrecioPeso);

		panelInferior = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelInferior.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		panelInferior.setBackground(new Color(176, 196, 222));
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

		JLabel lblNewLabel = new JLabel("       ");
		panelInferior.add(lblNewLabel);

		panelCentral = new JPanel();
		panelCentral.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 128, 128)));
		panelCentral.setBackground(new Color(176, 196, 222));
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(0, 128, 128), 2));

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

		AbstractTableModel fixedModel = new AbstractTableModel() {
			public int getColumnCount() {
				return 1;
			}

			public int getRowCount() {
				return tblListado.getRowCount();
			}

			public String getColumnName(int col) {
				return (String) tblListado.getColumnName(col);
			}

			public Object getValueAt(int row, int col) {
				return tblListado.getValueAt(row, col);
			}
		};


//		AbstractTableModel model = new AbstractTableModel() {
//			public int getColumnCount() {
//				return nombreColumnas.length - 1;
//			}
//
//			public int getRowCount() {
//				return tblListado.getRowCount();
//			}
//
//			public String getColumnName(int col) {
//				return (String) tblListado.getColumnName(col + 1);
//			}
//
//			public Object getValueAt(int row, int col) {
//				return tblListado.getValueAt(row, col + 1);
//			}
//
//			public void setValueAt(Object obj, int row, int col) {
//				tblListado.setValueAt(fixedModel, row, col + 1);
//			}
//
//			public boolean CellEditable(int row, int col) {
//				return true;
//			}
//		};
//		

		tblListado = new JTable(modelReparaciones) {
			public void valueChanged(ListSelectionEvent e) {
				super.valueChanged(e);
				checkSelection(false);
			}
		};

		tablaELS = new JTable(fixedModel) {
			public void valueChanged(ListSelectionEvent e) {
				super.valueChanged(e);
				checkSelection(true);
			}
		};
		;

		try {

			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}

		tblListado.setGridColor(new Color(105, 105, 105));
		tblListado.setBackground(new Color(176, 196, 222));
		tblListado.setOpaque(false);
		tblListado.setRowMargin(3);
		tblListado.setRowHeight(18);

		((DefaultTableCellRenderer) tblListado.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tblListado.setShowGrid(true);
		tblListado.setCellSelectionEnabled(true);

		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tablaELS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblListado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaELS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblListado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lblNewLabel_6 = new JLabel("        ");
		panelCentral.add(lblNewLabel_6, BorderLayout.NORTH);

		lblNewLabel_5 = new JLabel("        ");
		panelCentral.add(lblNewLabel_5, BorderLayout.EAST);

		lblNewLabel_4 = new JLabel("        ");
		panelCentral.add(lblNewLabel_4, BorderLayout.WEST);

		tblListado.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 60, 80, 150, 150, 200, 100, 150, 100, 100, 80, 110, 120, 150, 100, 100, 100, 100, 80, 80, 100,
				100, 100 };

		for (int i = 0; i < tblListado.getColumnCount(); i++) {

			tblListado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

		}

		JViewport viewport = new JViewport();
		viewport.setView(tablaELS);
		viewport.setPreferredSize(tablaELS.getPreferredSize());
		scrollPane.setRowHeaderView(viewport);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, tablaELS.getTableHeader());

		panelCentral.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(tblListado);

		this.setVisible(true);

	}

	private void checkSelection(boolean isFixedTable) {
		int fixedSelectedIndex = tablaELS.getSelectedRow();
		int selectedIndex = tblListado.getSelectedRow();
		if (fixedSelectedIndex != selectedIndex) {
			if (isFixedTable) {
				tblListado.setRowSelectionInterval(fixedSelectedIndex, fixedSelectedIndex);
			} else {
				tablaELS.setRowSelectionInterval(selectedIndex, selectedIndex);
			}
		}
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

	public JCheckBox getChckbxSucursal() {
		return chckbxSucursal;
	}

	public void setChckbxSucursal(JCheckBox chckbxSucursal) {
		this.chckbxSucursal = chckbxSucursal;
	}

	public JCheckBox getChckbxModelo() {
		return chckbxModelo;
	}

	public void setChckbxModelo(JCheckBox chckbxModelo) {
		this.chckbxModelo = chckbxModelo;
	}

	public JCheckBox getChckbxRevisión() {
		return chckbxRevisión;
	}

	public void setChckbxRevisión(JCheckBox chckbxRevisión) {
		this.chckbxRevisión = chckbxRevisión;
	}

	public JCheckBox getChckbxEntrada() {
		return chckbxEntrada;
	}

	public void setChckbxEntrada(JCheckBox chckbxEntrada) {
		this.chckbxEntrada = chckbxEntrada;
	}

	public JCheckBox getChckbxEquipo() {
		return chckbxEquipo;
	}

	public void setChckbxEquipo(JCheckBox chckbxEquipo) {
		this.chckbxEquipo = chckbxEquipo;
	}

	public JCheckBox getChckbxELS() {
		return chckbxELS;
	}

	public void setChckbxELS(JCheckBox chckbxELS) {
		this.chckbxELS = chckbxELS;
	}

	public JCheckBox getChckbxClienteCliente() {
		return chckbxClienteCliente;
	}

	public void setChckbxClienteCliente(JCheckBox chckbxClienteCliente) {
		this.chckbxClienteCliente = chckbxClienteCliente;
	}

	public JCheckBox getChckbxSerie() {
		return chckbxSerie;
	}

	public void setChckbxSerie(JCheckBox chckbxSerie) {
		this.chckbxSerie = chckbxSerie;
	}

	public JCheckBox getChckbxCliente() {
		return chckbxCliente;
	}

	public void setChckbxCliente(JCheckBox chckbxCliente) {
		this.chckbxCliente = chckbxCliente;
	}

	public JCheckBox getChckbxMarca() {
		return chckbxMarca;
	}

	public void setChckbxMarca(JCheckBox chckbxMarca) {
		this.chckbxMarca = chckbxMarca;
	}

	public JCheckBox getChckbxAviso() {
		return chckbxAviso;
	}

	public void setChckbxAviso(JCheckBox chckbxAviso) {
		this.chckbxAviso = chckbxAviso;
	}

	public JCheckBox getChckbxEstadoTec() {
		return chckbxEstadoTec;
	}

	public void setChckbxEstadoTec(JCheckBox chckbxEstadoTec) {
		this.chckbxEstadoTec = chckbxEstadoTec;
	}

	public JCheckBox getChckbxEstadoCom() {
		return chckbxEstadoCom;
	}

	public void setChckbxEstadoCom(JCheckBox chckbxEstadoCom) {
		this.chckbxEstadoCom = chckbxEstadoCom;
	}

	public JCheckBox getChckbxEstadoFis() {
		return chckbxEstadoFis;
	}

	public void setChckbxEstadoFis(JCheckBox chckbxEstadoFis) {
		this.chckbxEstadoFis = chckbxEstadoFis;
	}

	public JCheckBox getChckbxTecnico() {
		return chckbxTecnico;
	}

	public void setChckbxTecnico(JCheckBox chckbxTecnico) {
		this.chckbxTecnico = chckbxTecnico;
	}

	public JCheckBox getChckbxUbicacionRemito() {
		return chckbxUbicacionRemito;
	}

	public void setChckbxUbicacionRemito(JCheckBox chckbxUbicacionRemito) {
		this.chckbxUbicacionRemito = chckbxUbicacionRemito;
	}

	public JCheckBox getChckbxNumeroRemito() {
		return chckbxNumeroRemito;
	}

	public void setChckbxNumeroRemito(JCheckBox chckbxNumeroRemito) {
		this.chckbxNumeroRemito = chckbxNumeroRemito;
	}

	public JCheckBox getChckbxPresupuestoGeneradoColumna() {
		return chckbxPresupuestoGeneradoColumna;
	}

	public void setChckbxPresupuestoGeneradoColumna(JCheckBox chckbxPresupuestoGeneradoColumna) {
		this.chckbxPresupuestoGeneradoColumna = chckbxPresupuestoGeneradoColumna;
	}

	public JCheckBox getChckbxPresupuestoEnviadoColumna() {
		return chckbxPresupuestoEnviadoColumna;
	}

	public void setChckbxPresupuestoEnviadoColumna(JCheckBox chckbxPresupuestoEnviadoColumna) {
		this.chckbxPresupuestoEnviadoColumna = chckbxPresupuestoEnviadoColumna;
	}

	public JCheckBox getChckbxPago() {
		return chckbxPago;
	}

	public void setChckbxPago(JCheckBox chckbxPago) {
		this.chckbxPago = chckbxPago;
	}

	public JCheckBox getChckbxPrecioPeso() {
		return chckbxPrecioPeso;
	}

	public void setChckbxPrecioPeso(JCheckBox chckbxPrecioPeso) {
		this.chckbxPrecioPeso = chckbxPrecioPeso;
	}

	public JCheckBox getChckbxPrecioDolar() {
		return chckbxPrecioDolar;
	}

	public void setChckbxPrecioDolar(JCheckBox chckbxPrecioDolar) {
		this.chckbxPrecioDolar = chckbxPrecioDolar;
	}

}
