package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.plaf.UIManagerExt;

import VistaPropias.CellRenderer;
import VistaPropias.CellRendererTablaListado;
import VistaPropias.CellRendererTablaRemitos;
import presentacion.controlador.ControladorListados;
import presentacion.controlador.ControladorPresupuestos;
import presentacion.controlador.ControladorReparacion;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaListadoReparacionesPresupuestos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//private JTable tblReparaciones;
	private JTable tblReparaciones_1;
	private DefaultTableModel modelReparaciones;
	private String[] nombreColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO",
			"N° SERIE", "AVISO", "REVISIÓN", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS", "TÉCNICO",
			"UBIC. REM", "NUM REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO" };

	private JButton btnFiltrar;
	private JButton btnMostrarTodo;
	private JButton btnMax;
	public static int est;
	private JPanel panel;
	private JScrollPane scrollPane;

	//private ControladorListados controlador;
	private ControladorReparacion controladorReparacion;
	
	private ControladorPresupuestos controlador;
	
	private JComboBox comboFiltroMarca;
	private JComboBox comboFiltroCliente;
	private JComboBox comboFiltroSucursal;

	private JComboBox comboFiltroEstadoFis;
	private JComboBox comboFiltroEstadoCom;
	private JComboBox comboFiltroEstadoTec;
	private JComboBox comboFiltroELS;

	private JRadioButton radioButtonMarca;
	private JRadioButton radioButtonCliente;
	private JRadioButton radioButtonSucursal;
	private JRadioButton radioButtonELS;

	private JRadioButton radioButtonEstadoFis;
	private JRadioButton radioButtonEstadoCom;
	private JRadioButton radioButtonEstadoTec;

	private JCheckBox chckbxPresupuestoGenerado;
	private JCheckBox chckbxPresupuestoEnviado;

	private JLabel lblMarca;

	private JLabel lblEquipo;
	private JLabel lblModelo;
	private JLabel lblAviso;
	private JComboBox comboFiltroEquipo;
	private JComboBox comboFiltroModelo;
	private JComboBox comboFiltroAviso;
	private JRadioButton radioButtonEquipo;
	private JRadioButton radioButtonModelo;
	private JRadioButton radioButtonAviso;
	private JSeparator separator_1;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JRadioButton radioButtonPresupGenerado;
	private JRadioButton radioButtonPresupEnviado;
	private JPanel panel_1;
	private JLabel lblTcnico;
	private JComboBox comboFiltroTecnico;
	private JRadioButton radioButtonTecnico;
	private JTextField txtListadosDeEquipos;

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

	public VentanaListadoReparacionesPresupuestos(ControladorPresupuestos controlador ) {

		super();
		setUndecorated(true);
		setResizable(false);
		this.controlador = controlador;

		this.this_windowOpened(null);
		setSize(1080, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		getContentPane().setLayout(null);

		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(0, 0));
		contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DimContentPane = contentPane.getSize();

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 0));
		panel.setBounds(10, 0, 1054, 561);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(panel);
		panel.setLayout(null);
		DimPanel = panel.getSize();

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// scrollPane.setAutoscrolls(true);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 227, 1054, 334);
		panel.add(scrollPane);

		DimScrollPane = scrollPane.getSize();

		modelReparaciones = new DefaultTableModel(null, nombreColumnas);
		tblReparaciones_1 = new JTable(modelReparaciones);

		modelReparaciones = new DefaultTableModel(new Object[][] {},
				new String[] { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO", "N° SERIE",
						"AVISO", "REVISIÓN", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS", "TÉCNICO",
						"UBIC. REM", "N° REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO" }) {

			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, Boolean.class,
					Boolean.class, double.class, double.class, double.class   };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false,false,false,false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};

		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
		Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

		tblReparaciones_1 = new JTable(modelReparaciones);
	
		tblReparaciones_1.setFont(fuenteCeldas);

		tblReparaciones_1.getTableHeader().setForeground(Color.BLACK);
		tblReparaciones_1.getTableHeader().setFont(fuenteCabecera);
		tblReparaciones_1.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tblReparaciones_1.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tblReparaciones_1.setShowGrid(true);
		tblReparaciones_1.setCellSelectionEnabled(true);

		tblReparaciones_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblReparaciones_1.doLayout();

		scrollPane.setViewportView(tblReparaciones_1);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tblReparaciones_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblReparaciones_1.setAutoCreateColumnsFromModel(false);

		DimTblReparaciones = tblReparaciones_1.getSize();

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(35, 50, 994, 134);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblCliente = new JLabel("CLIENTE:");
		lblCliente.setBounds(6, 15, 60, 14);
		panel_1.add(lblCliente);
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 12));

		comboFiltroCliente = new JComboBox();
		comboFiltroCliente.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroCliente.setBounds(77, 10, 138, 20);
		panel_1.add(comboFiltroCliente);
		comboFiltroCliente.setEnabled(false);

		comboFiltroMarca = new JComboBox();
		comboFiltroMarca.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroMarca.setBounds(77, 70, 138, 20);
		panel_1.add(comboFiltroMarca);
		comboFiltroMarca.setEnabled(false);

		lblMarca = new JLabel("MARCA:");
		lblMarca.setBounds(6, 73, 60, 14);
		panel_1.add(lblMarca);
		lblMarca.setFont(new Font("Cambria", Font.BOLD, 12));

		radioButtonCliente = new JRadioButton("");
		radioButtonCliente.setBounds(221, 10, 21, 20);
		panel_1.add(radioButtonCliente);
		radioButtonCliente.setBackground(SystemColor.inactiveCaption);

		radioButtonMarca = new JRadioButton("");
		radioButtonMarca.setBounds(221, 70, 21, 20);
		panel_1.add(radioButtonMarca);
		radioButtonMarca.setBackground(SystemColor.inactiveCaption);

		comboFiltroSucursal = new JComboBox();
		comboFiltroSucursal.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroSucursal.setBounds(77, 40, 138, 20);
		panel_1.add(comboFiltroSucursal);
		comboFiltroSucursal.setEnabled(false);

		JLabel lblSucursal = new JLabel("SUCURSAL:");
		lblSucursal.setBounds(6, 44, 72, 14);
		panel_1.add(lblSucursal);
		lblSucursal.setFont(new Font("Cambria", Font.BOLD, 12));

		radioButtonSucursal = new JRadioButton("");
		radioButtonSucursal.setBounds(221, 40, 21, 20);
		panel_1.add(radioButtonSucursal);
		radioButtonSucursal.setBackground(SystemColor.inactiveCaption);

		lblEquipo = new JLabel("EQUIPO:");
		lblEquipo.setBounds(263, 15, 60, 14);
		panel_1.add(lblEquipo);
		lblEquipo.setFont(new Font("Cambria", Font.BOLD, 12));

		lblModelo = new JLabel("MODELO:");
		lblModelo.setBounds(263, 44, 60, 14);
		panel_1.add(lblModelo);
		lblModelo.setFont(new Font("Cambria", Font.BOLD, 12));

		lblAviso = new JLabel("AVISO:");
		lblAviso.setBounds(263, 73, 60, 14);
		panel_1.add(lblAviso);
		lblAviso.setFont(new Font("Cambria", Font.BOLD, 12));

		comboFiltroEquipo = new JComboBox();
		comboFiltroEquipo.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEquipo.setBounds(323, 10, 138, 20);
		panel_1.add(comboFiltroEquipo);
		comboFiltroEquipo.setEnabled(false);

		comboFiltroModelo = new JComboBox();
		comboFiltroModelo.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroModelo.setBounds(323, 40, 138, 20);
		panel_1.add(comboFiltroModelo);
		comboFiltroModelo.setEnabled(false);

		comboFiltroAviso = new JComboBox();
		comboFiltroAviso.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroAviso.setBounds(323, 70, 138, 20);
		panel_1.add(comboFiltroAviso);
		comboFiltroAviso.setEnabled(false);

		radioButtonEquipo = new JRadioButton("");
		radioButtonEquipo.setBounds(467, 10, 21, 20);
		panel_1.add(radioButtonEquipo);
		radioButtonEquipo.setBackground(SystemColor.inactiveCaption);

		radioButtonModelo = new JRadioButton("");
		radioButtonModelo.setBounds(467, 40, 21, 20);
		panel_1.add(radioButtonModelo);
		radioButtonModelo.setBackground(SystemColor.inactiveCaption);

		radioButtonAviso = new JRadioButton("");
		radioButtonAviso.setBounds(467, 70, 21, 20);
		panel_1.add(radioButtonAviso);
		radioButtonAviso.setBackground(SystemColor.inactiveCaption);

		comboFiltroEstadoFis = new JComboBox();
		comboFiltroEstadoFis.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEstadoFis.setBounds(601, 70, 138, 20);
		panel_1.add(comboFiltroEstadoFis);
		comboFiltroEstadoFis.setEnabled(false);

		radioButtonEstadoFis = new JRadioButton("");
		radioButtonEstadoFis.setBounds(745, 70, 21, 20);
		panel_1.add(radioButtonEstadoFis);
		radioButtonEstadoFis.setBackground(SystemColor.inactiveCaption);

		radioButtonEstadoCom = new JRadioButton("");
		radioButtonEstadoCom.setBounds(745, 40, 21, 20);
		panel_1.add(radioButtonEstadoCom);
		radioButtonEstadoCom.setBackground(SystemColor.inactiveCaption);

		comboFiltroEstadoCom = new JComboBox();
		comboFiltroEstadoCom.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEstadoCom.setBounds(601, 40, 138, 20);
		panel_1.add(comboFiltroEstadoCom);
		comboFiltroEstadoCom.setEnabled(false);

		radioButtonEstadoTec = new JRadioButton("");
		radioButtonEstadoTec.setBounds(745, 12, 21, 20);
		panel_1.add(radioButtonEstadoTec);
		radioButtonEstadoTec.setBackground(SystemColor.inactiveCaption);

		comboFiltroEstadoTec = new JComboBox();
		comboFiltroEstadoTec.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroEstadoTec.setBounds(601, 12, 138, 20);
		panel_1.add(comboFiltroEstadoTec);
		comboFiltroEstadoTec.setEnabled(false);

		JLabel lblEstadoTec = new JLabel("ESTADO TEC:");
		lblEstadoTec.setBounds(509, 15, 82, 14);
		panel_1.add(lblEstadoTec);
		lblEstadoTec.setFont(new Font("Cambria", Font.BOLD, 12));

		JLabel lblEstadoCom = new JLabel("ESTADO COM:");
		lblEstadoCom.setBounds(509, 43, 82, 14);
		panel_1.add(lblEstadoCom);
		lblEstadoCom.setFont(new Font("Cambria", Font.BOLD, 12));

		JLabel lblEstadoFis = new JLabel("ESTADO FIS:");
		lblEstadoFis.setBounds(509, 73, 82, 14);
		panel_1.add(lblEstadoFis);
		lblEstadoFis.setFont(new Font("Cambria", Font.BOLD, 12));

		chckbxPresupuestoGenerado = new JCheckBox("PRESUPUESTO GENERADO:");
		chckbxPresupuestoGenerado.setEnabled(false);
		chckbxPresupuestoGenerado.setBounds(784, 16, 180, 20);
		panel_1.add(chckbxPresupuestoGenerado);
		chckbxPresupuestoGenerado.setBackground(SystemColor.inactiveCaption);
		chckbxPresupuestoGenerado.setFont(new Font("Cambria", Font.BOLD, 12));

		chckbxPresupuestoEnviado = new JCheckBox("PRESUPUESTO ENVIADO:");
		chckbxPresupuestoEnviado.setEnabled(false);
		chckbxPresupuestoEnviado.setBounds(784, 39, 180, 20);
		panel_1.add(chckbxPresupuestoEnviado);
		chckbxPresupuestoEnviado.setBackground(SystemColor.inactiveCaption);
		chckbxPresupuestoEnviado.setFont(new Font("Cambria", Font.BOLD, 12));

		JSeparator separator = new JSeparator();
		separator.setBounds(246, 17, 5, 103);
		panel_1.add(separator);
		separator.setForeground(SystemColor.textInactiveText);
		separator.setOrientation(SwingConstants.VERTICAL);

		separator_1 = new JSeparator();
		separator_1.setBounds(249, 17, 2, 103);
		panel_1.add(separator_1);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(SystemColor.textInactiveText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(494, 19, 4, 98);
		panel_1.add(separator_2);
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(SystemColor.textInactiveText);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(497, 19, 1, 98);
		panel_1.add(separator_3);
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setForeground(SystemColor.textInactiveText);

		separator_4 = new JSeparator();
		separator_4.setBounds(772, 19, 6, 101);
		panel_1.add(separator_4);
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setForeground(SystemColor.textInactiveText);

		separator_5 = new JSeparator();
		separator_5.setBounds(775, 19, 3, 101);
		panel_1.add(separator_5);
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setForeground(SystemColor.textInactiveText);

		radioButtonPresupGenerado = new JRadioButton("");
		radioButtonPresupGenerado.setBounds(960, 19, 21, 20);
		panel_1.add(radioButtonPresupGenerado);
		radioButtonPresupGenerado.setBackground(SystemColor.inactiveCaption);

		radioButtonPresupEnviado = new JRadioButton("");
		radioButtonPresupEnviado.setBounds(960, 42, 21, 20);
		panel_1.add(radioButtonPresupEnviado);
		radioButtonPresupEnviado.setBackground(SystemColor.inactiveCaption);

		radioButtonELS = new JRadioButton("");
		radioButtonELS.setBackground(SystemColor.inactiveCaption);
		radioButtonELS.setBounds(221, 100, 21, 20);
		panel_1.add(radioButtonELS);

		comboFiltroELS = new JComboBox();
		comboFiltroELS.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroELS.setEnabled(false);
		comboFiltroELS.setBounds(77, 100, 138, 20);
		panel_1.add(comboFiltroELS);

		JLabel lblEls = new JLabel("ELS:");
		lblEls.setFont(new Font("Cambria", Font.BOLD, 12));
		lblEls.setBounds(6, 102, 60, 14);
		panel_1.add(lblEls);

		lblTcnico = new JLabel("TÉCNICO:");
		lblTcnico.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTcnico.setBounds(263, 102, 60, 14);
		panel_1.add(lblTcnico);

		comboFiltroTecnico = new JComboBox();
		comboFiltroTecnico.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboFiltroTecnico.setEnabled(false);
		comboFiltroTecnico.setBounds(323, 100, 138, 20);
		panel_1.add(comboFiltroTecnico);

		radioButtonTecnico = new JRadioButton("");
		radioButtonTecnico.setBackground(SystemColor.inactiveCaption);
		radioButtonTecnico.setBounds(467, 100, 21, 20);
		panel_1.add(radioButtonTecnico);

		btnFiltrar = new JButton("FILTRAR");
		btnFiltrar.setFont(new Font("Cambria", Font.BOLD, 10));
		btnFiltrar.setBounds(440, 193, 113, 23);
		panel.add(btnFiltrar);

		btnMostrarTodo = new JButton("MOSTRAR TODO");
		btnMostrarTodo.setFont(new Font("Cambria", Font.BOLD, 10));
		btnMostrarTodo.setBounds(588, 193, 113, 23);
		panel.add(btnMostrarTodo);

		txtListadosDeEquipos = new JTextField();
		txtListadosDeEquipos.setEditable(false);
		txtListadosDeEquipos.setBackground(SystemColor.inactiveCaption);
		txtListadosDeEquipos.setFont(new Font("Cambria", Font.BOLD, 22));
		txtListadosDeEquipos.setText("LISTADOS DE EQUIPOS");
		txtListadosDeEquipos.setBounds(35, 11, 253, 28);
		panel.add(txtListadosDeEquipos);
		txtListadosDeEquipos.setColumns(10);

		btnMax = new JButton("");
		btnMax.setBounds(999, 14, 30, 25);
		btnMax.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
		panel.add(btnMax);

		tblReparaciones_1.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 60, 80, 150, 150, 200, 100, 200, 100, 100, 80, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,100,100};

		for (int i = 0; i < tblReparaciones_1.getColumnCount(); i++) {

			tblReparaciones_1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

		}

		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(ComponentEvent e) {

				Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
				// Se obtienen las dimensiones en pixels de la ventana.
				Dimension ventana = getSize();
				if (est % 2 != 0) {

					// Se obtienen las dimensiones en pixels de la pantalla.

					scrollPane.setSize(ventana.width - 20, (ventana.height) - (ventana.height / 3));
					panel.setSize(ventana.width - 10, ventana.height);
					contentPane.setSize(ventana.width - 10, ventana.height);

					// tblReparaciones.setSize(ventana.width-5,DimTblReparaciones.height+50);

				} else {

					scrollPane.setSize(DimScrollPane);
					panel.setSize(DimPanel);
					//contentPane.setSize(DimContentPane);
					tblReparaciones_1.setSize(DimTblReparaciones);

				}

				est++;
			}
		});

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
		return tblReparaciones_1;
	}

	public void setTblReparaciones(JTable tblReparaciones) {
		this.tblReparaciones_1 = tblReparaciones;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	public JComboBox getComboFiltroCliente() {
		return comboFiltroCliente;
	}

	public void setComboFiltroCliente(JComboBox comboFiltroCliente) {
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

	public JComboBox getComboFiltroMarca() {
		return comboFiltroMarca;
	}

	public void setComboFiltroMarca(JComboBox comboFiltroMarca) {
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

	public JComboBox getComboFiltroSucursal() {
		return comboFiltroSucursal;
	}

	public void setComboFiltroSucursal(JComboBox comboFiltroSucursal) {
		this.comboFiltroSucursal = comboFiltroSucursal;
	}

	public JRadioButton getRadioButtonSucursal() {
		return radioButtonSucursal;
	}

	public void setRadioButtonSucursal(JRadioButton radioButtonSucursal) {
		this.radioButtonSucursal = radioButtonSucursal;
	}

	public JComboBox getComboFiltroEstadoFis() {
		return comboFiltroEstadoFis;
	}

	public void setComboFiltroEstadoFis(JComboBox comboFiltroEstadoFis) {
		this.comboFiltroEstadoFis = comboFiltroEstadoFis;
	}

	public JComboBox getComboFiltroEstadoCom() {
		return comboFiltroEstadoCom;
	}

	public void setComboFiltroEstadoCom(JComboBox comboFiltroEstadoCom) {
		this.comboFiltroEstadoCom = comboFiltroEstadoCom;
	}

	public JComboBox getComboFiltroEstadoTec() {
		return comboFiltroEstadoTec;
	}

	public void setComboFiltroEstadoTec(JComboBox comboFiltroEstadoTec) {
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

	public JComboBox getComboFiltroEquipo() {
		return comboFiltroEquipo;
	}

	public void setComboFiltroEquipo(JComboBox comboFiltroEquipo) {
		this.comboFiltroEquipo = comboFiltroEquipo;
	}

	public JComboBox getComboFiltroModelo() {
		return comboFiltroModelo;
	}

	public void setComboFiltroModelo(JComboBox comboFiltroModelo) {
		this.comboFiltroModelo = comboFiltroModelo;
	}

	public JComboBox getRadio() {
		return comboFiltroAviso;
	}

	public void setComboFiltroAviso(JComboBox comboFiltroAviso) {
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

	public JComboBox getComboFiltroAviso() {
		return comboFiltroAviso;
	}

	public JComboBox getComboFiltroELS() {
		return comboFiltroELS;
	}

	public void setComboFiltroELS(JComboBox comboFiltroELS) {
		this.comboFiltroELS = comboFiltroELS;
	}

	public JRadioButton getRadioButtonELS() {
		return radioButtonELS;
	}

	public void setRadioButtonELS(JRadioButton radioButtonELS) {
		this.radioButtonELS = radioButtonELS;
	}

	public JComboBox getComboFiltroTecnico() {
		return comboFiltroTecnico;
	}

	public void setComboFiltroTecnico(JComboBox comboFiltroTecnico) {
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
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

}
