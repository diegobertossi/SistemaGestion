package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import VistaPropias.CellRendererTablaMarcarAceptaciones;
import presentacion.controlador.ControladorPresupuestos;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaMarcarAceptaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tblmarcarAceptaciones;
	private DefaultTableModel modelReparaciones;

	private String[] nombreColumnas = { "ELS", "AVISO", "CLIENTE", "SUCURSAL", "EQUIPO", "MODELO", "ESTADO TEC",
			"ESTADO COM", "ACEP", "NO ACEP", "GTÍA", "PEND" };

	private JButton btnMax;
	public static int est;

	private JPanel panelPrincipal;
	private JPanel panelFiltros;
	private JPanel panelSuperior;
	private JPanel panelTitulo;
	private JPanel panelInferior;
	private JPanel panelCentral;
	private JScrollPane scrollPane;

	private JComboBox<?> comboFiltroCliente;
	private JComboBox<?> comboFiltroSucursal;
	private JComboBox<?> comboFiltroELS;
	private JComboBox<?> comboFiltroAviso;
	private JRadioButton radioButtonCliente;
	private JRadioButton radioButtonSucursal;
	private JRadioButton radioButtonELS;
	private JRadioButton radioButtonAviso;

	Dimension DimScrollPane;
	Dimension DimPanel;
	Dimension DimContentPane;
	Dimension DimTblReparaciones;
	private JPanel panelBotonera;
	private JButton btnFiltrar;
	private JButton btnMostrarTodo;
	private JButton btnGrardarCambios;
	private JPanel panelColumnas;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblseMuestranLos;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_7;

	@SuppressWarnings("unused")
	private ControladorPresupuestos controladorPresupuestos;

	protected void this_windowOpened(WindowEvent e) {
		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	public VentanaMarcarAceptaciones(ControladorPresupuestos controladorPresupuestos) {

		super();
		this.controladorPresupuestos = controladorPresupuestos;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
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
		panelFiltros.setPreferredSize(new Dimension(600, 160));
		panelFiltros.setMaximumSize(new Dimension(600, 160));
		panelFiltros.setFont(new Font("Cambria", Font.PLAIN, 10));
		panelFiltros.setBackground(new Color(176, 196, 222));
		panelFiltros.setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(0, 128, 128)));

		panelFiltros.setBounds(35, 50, 994, 134);
		panelSuperior.add(panelFiltros, BorderLayout.CENTER);
		GridBagLayout gbl_panelFiltros = new GridBagLayout();
		gbl_panelFiltros.columnWidths = new int[] { 50, 50, 350, 50, 184, 32 };
		gbl_panelFiltros.rowHeights = new int[] { 10, 20, 10, 10, 10, 10 };
		gbl_panelFiltros.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };

		gbl_panelFiltros.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelFiltros.setLayout(gbl_panelFiltros);

		JLabel lblNewLabel_1 = new JLabel("CLIENTE");
		lblNewLabel_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelFiltros.add(lblNewLabel_1, gbc_lblNewLabel_1);

		comboFiltroCliente = new JComboBox<Object>();
		comboFiltroCliente.setEnabled(false);
		comboFiltroCliente.setBackground(new Color(176, 196, 222));
		comboFiltroCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroCliente.setPreferredSize(new Dimension(350, 22));
		GridBagConstraints gbc_comboFiltroCliente = new GridBagConstraints();
		gbc_comboFiltroCliente.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroCliente.gridx = 2;
		gbc_comboFiltroCliente.gridy = 1;
		panelFiltros.add(comboFiltroCliente, gbc_comboFiltroCliente);

		radioButtonCliente = new JRadioButton("");
		radioButtonCliente.setBackground(new Color(176, 196, 222));
		radioButtonCliente.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_radioButtonCliente = new GridBagConstraints();
		gbc_radioButtonCliente.anchor = GridBagConstraints.WEST;
		gbc_radioButtonCliente.insets = new Insets(0, 0, 5, 100);
		gbc_radioButtonCliente.gridx = 3;
		gbc_radioButtonCliente.gridy = 1;
		panelFiltros.add(radioButtonCliente, gbc_radioButtonCliente);

		btnFiltrar = new JButton("FILTRAR");
		GridBagConstraints gbc_btnFiltrar = new GridBagConstraints();
		gbc_btnFiltrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnFiltrar.gridx = 4;
		gbc_btnFiltrar.gridy = 1;
		panelFiltros.add(btnFiltrar, gbc_btnFiltrar);
		btnFiltrar.setSize(new Dimension(150, 30));
		btnFiltrar.setMinimumSize(new Dimension(170, 30));
		btnFiltrar.setMaximumSize(new Dimension(170, 30));
		btnFiltrar.setPreferredSize(new Dimension(170, 30));
		btnFiltrar.setFont(new Font("Cambria", Font.BOLD, 14));

		JLabel lblNewLabel_1_1 = new JLabel("SUCURSAL");
		lblNewLabel_1_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.gridx = 1;
		gbc_lblNewLabel_1_1.gridy = 2;
		panelFiltros.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);

		comboFiltroSucursal = new JComboBox<Object>();
		comboFiltroSucursal.setEnabled(false);
		comboFiltroSucursal.setBackground(new Color(176, 196, 222));
		comboFiltroSucursal.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroSucursal.setPreferredSize(new Dimension(350, 22));
		GridBagConstraints gbc_comboFiltroSucursal = new GridBagConstraints();
		gbc_comboFiltroSucursal.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroSucursal.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroSucursal.gridx = 2;
		gbc_comboFiltroSucursal.gridy = 2;
		panelFiltros.add(comboFiltroSucursal, gbc_comboFiltroSucursal);

		radioButtonSucursal = new JRadioButton("");
		radioButtonSucursal.setBackground(new Color(176, 196, 222));
		radioButtonSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_radioButtonSucursal = new GridBagConstraints();
		gbc_radioButtonSucursal.anchor = GridBagConstraints.WEST;
		gbc_radioButtonSucursal.fill = GridBagConstraints.VERTICAL;
		gbc_radioButtonSucursal.insets = new Insets(0, 0, 5, 100);
		gbc_radioButtonSucursal.gridx = 3;
		gbc_radioButtonSucursal.gridy = 2;
		panelFiltros.add(radioButtonSucursal, gbc_radioButtonSucursal);

		btnMostrarTodo = new JButton("MOSTRAR TODO");
		GridBagConstraints gbc_btnMostrarTodo = new GridBagConstraints();
		gbc_btnMostrarTodo.insets = new Insets(0, 0, 5, 5);
		gbc_btnMostrarTodo.gridx = 4;
		gbc_btnMostrarTodo.gridy = 2;
		panelFiltros.add(btnMostrarTodo, gbc_btnMostrarTodo);
		btnMostrarTodo.setSize(new Dimension(170, 30));
		btnMostrarTodo.setMinimumSize(new Dimension(170, 30));
		btnMostrarTodo.setMaximumSize(new Dimension(170, 30));
		btnMostrarTodo.setPreferredSize(new Dimension(170, 30));
		btnMostrarTodo.setFont(new Font("Cambria", Font.BOLD, 14));

		JLabel lblNewLabel_3_1 = new JLabel("ELS");
		lblNewLabel_3_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.gridx = 1;
		gbc_lblNewLabel_3_1.gridy = 3;
		panelFiltros.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);

		comboFiltroELS = new JComboBox<Object>();
		comboFiltroELS.setEnabled(false);
		comboFiltroELS.setBackground(new Color(176, 196, 222));
		comboFiltroELS.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroELS.setPreferredSize(new Dimension(350, 22));
		GridBagConstraints gbc_comboFiltroELS = new GridBagConstraints();
		gbc_comboFiltroELS.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroELS.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroELS.gridx = 2;
		gbc_comboFiltroELS.gridy = 3;
		panelFiltros.add(comboFiltroELS, gbc_comboFiltroELS);

		radioButtonELS = new JRadioButton("");
		radioButtonELS.setBackground(new Color(176, 196, 222));
		radioButtonELS.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_radioButtonELS = new GridBagConstraints();
		gbc_radioButtonELS.anchor = GridBagConstraints.WEST;
		gbc_radioButtonELS.insets = new Insets(0, 0, 5, 100);
		gbc_radioButtonELS.gridx = 3;
		gbc_radioButtonELS.gridy = 3;
		panelFiltros.add(radioButtonELS, gbc_radioButtonELS);

		JLabel lblNewLabel_6_1 = new JLabel("AVISO");
		lblNewLabel_6_1.setBackground(new Color(176, 196, 222));
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_6_1 = new GridBagConstraints();
		gbc_lblNewLabel_6_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6_1.gridx = 1;
		gbc_lblNewLabel_6_1.gridy = 4;
		panelFiltros.add(lblNewLabel_6_1, gbc_lblNewLabel_6_1);

		comboFiltroAviso = new JComboBox<Object>();
		comboFiltroAviso.setEnabled(false);
		comboFiltroAviso.setBackground(new Color(176, 196, 222));
		comboFiltroAviso.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroAviso.setPreferredSize(new Dimension(350, 22));
		GridBagConstraints gbc_comboFiltroAviso = new GridBagConstraints();
		gbc_comboFiltroAviso.insets = new Insets(0, 0, 5, 5);
		gbc_comboFiltroAviso.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFiltroAviso.gridx = 2;
		gbc_comboFiltroAviso.gridy = 4;
		panelFiltros.add(comboFiltroAviso, gbc_comboFiltroAviso);

		radioButtonAviso = new JRadioButton("");
		radioButtonAviso.setBackground(new Color(176, 196, 222));
		radioButtonAviso.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_radioButtonAviso = new GridBagConstraints();
		gbc_radioButtonAviso.anchor = GridBagConstraints.WEST;
		gbc_radioButtonAviso.insets = new Insets(0, 0, 5, 100);
		gbc_radioButtonAviso.gridx = 3;
		gbc_radioButtonAviso.gridy = 4;
		panelFiltros.add(radioButtonAviso, gbc_radioButtonAviso);
		
				btnGrardarCambios = new JButton("GUARDAR CAMBIOS");
				btnGrardarCambios.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				GridBagConstraints gbc_btnGrardarCambios = new GridBagConstraints();
				gbc_btnGrardarCambios.insets = new Insets(0, 0, 5, 5);
				gbc_btnGrardarCambios.gridx = 4;
				gbc_btnGrardarCambios.gridy = 4;
				panelFiltros.add(btnGrardarCambios, gbc_btnGrardarCambios);
				btnGrardarCambios.setSize(new Dimension(170, 30));
				btnGrardarCambios.setMinimumSize(new Dimension(170, 30));
				btnGrardarCambios.setMaximumSize(new Dimension(170, 30));
				btnGrardarCambios.setPreferredSize(new Dimension(170, 30));
				btnGrardarCambios.setFont(new Font("Cambria", Font.BOLD, 14));

		panelTitulo = new JPanel();
		panelTitulo.setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(0, 128, 128)));
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

		panelColumnas = new JPanel();
		panelColumnas.setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(0, 128, 128)));
		panelColumnas.setBackground(new Color(176, 196, 222));
		panelBotonera.add(panelColumnas, BorderLayout.NORTH);
		panelColumnas.setLayout(new BorderLayout(0, 0));

		lblNewLabel_3 = new JLabel("      ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelColumnas.add(lblNewLabel_3, BorderLayout.WEST);

		lblNewLabel_7 = new JLabel("      ");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panelColumnas.add(lblNewLabel_7, BorderLayout.EAST);

		lblseMuestranLos = new JLabel(
				"<html><center>SE MUESTRAN LOS EQUIPOS CUYOS PRESUPUESTOS FUERON GENERADOS Y ESTÁN PENDIENTES DE RESPUESTA<html>");
		panelColumnas.add(lblseMuestranLos, BorderLayout.CENTER);
		lblseMuestranLos.setHorizontalAlignment(SwingConstants.CENTER);
		lblseMuestranLos.setForeground(new Color(0, 0, 128));
		lblseMuestranLos.setFont(new Font("Cambria", Font.BOLD, 14));
		lblseMuestranLos.setBorder(null);
		lblseMuestranLos.setBackground(Color.WHITE);

		panelInferior = new JPanel();
		panelInferior.setBorder(new MatteBorder(0, 2, 2, 2, (Color) new Color(0, 128, 128)));
		FlowLayout flowLayout = (FlowLayout) panelInferior.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		panelInferior.setBackground(new Color(176, 196, 222));
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

		JLabel lblNewLabel = new JLabel("       ");
		panelInferior.add(lblNewLabel);

		panelCentral = new JPanel();
		panelCentral.setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(0, 128, 128)));
		panelCentral.setBackground(new Color(176, 196, 222));
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(0, 128, 128), 2));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelCentral.add(scrollPane, BorderLayout.CENTER);

		modelReparaciones = new DefaultTableModel(new Object[][] {}, nombreColumnas) {

			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, Integer.class, String.class, String.class, String.class,
					String.class, String.class, String.class, Boolean.class, Boolean.class, Boolean.class,
					Boolean.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, true,
					true, true, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};

		tblmarcarAceptaciones = new JTable(modelReparaciones);

		try {
			// UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
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

		tblmarcarAceptaciones = new JTable(modelReparaciones);
		tblmarcarAceptaciones.setGridColor(new Color(105, 105, 105));
		tblmarcarAceptaciones.setBackground(new Color(176, 196, 222));
		tblmarcarAceptaciones.setOpaque(false);
		tblmarcarAceptaciones.setRowMargin(3);
		tblmarcarAceptaciones.setRowHeight(18);

		((DefaultTableCellRenderer) tblmarcarAceptaciones.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tblmarcarAceptaciones.setShowGrid(true);
		tblmarcarAceptaciones.setCellSelectionEnabled(true);

		scrollPane.setViewportView(tblmarcarAceptaciones);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tblmarcarAceptaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblmarcarAceptaciones.setAutoCreateColumnsFromModel(false);

		lblNewLabel_6 = new JLabel("        ");
		panelCentral.add(lblNewLabel_6, BorderLayout.NORTH);

		lblNewLabel_5 = new JLabel("   ");
		panelCentral.add(lblNewLabel_5, BorderLayout.EAST);

		lblNewLabel_4 = new JLabel("   ");
		panelCentral.add(lblNewLabel_4, BorderLayout.WEST);

		tblmarcarAceptaciones.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 65, 65, 130, 130, 195, 130, 90, 90, 60, 60, 60, 60};
		int[] anchoMinimo= {20,20,20,20,20,20,20,20,20,20,20,20};
		

		for (int i = 0; i < tblmarcarAceptaciones.getColumnCount(); i++) {

			tblmarcarAceptaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
			tblmarcarAceptaciones.getColumnModel().getColumn(i).setMinWidth(anchoMinimo[i]);
			tblmarcarAceptaciones.getColumnModel().getColumn(i).setMaxWidth(anchos[i]);

		}


		for (int i = tblmarcarAceptaciones.getColumnCount() - 4; i < tblmarcarAceptaciones.getColumnCount(); i++) {
			tblmarcarAceptaciones.getColumnModel().getColumn(i)
					.setCellRenderer(new CellRendererTablaMarcarAceptaciones());
		}

		this.setVisible(true);

	}

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaMarcarAceptaciones());
		}
	}

	public DefaultTableModel getModelReparaciones() {
		return modelReparaciones;
	}

	public void setModelReparaciones(DefaultTableModel modelReparaciones) {
		this.modelReparaciones = modelReparaciones;
	}

	public JTable getTblReparaciones() {
		return tblmarcarAceptaciones;
	}

	public void setTblReparaciones(JTable tblReparaciones) {
		this.tblmarcarAceptaciones = tblReparaciones;
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

	public JComboBox<?> getRadio() {
		return comboFiltroAviso;
	}

	public void setComboFiltroAviso(JComboBox<?> comboFiltroAviso) {
		this.comboFiltroAviso = comboFiltroAviso;
	}

	public JRadioButton getRadioButtonAviso() {
		return radioButtonAviso;
	}

	public void setRadioButtonAviso(JRadioButton radioButtonAviso) {
		this.radioButtonAviso = radioButtonAviso;
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

	public JButton getBtnMax() {
		return btnMax;
	}

	public void setBtnMax(JButton btnMax) {
		this.btnMax = btnMax;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JButton getBtnGrardarCambios() {
		return btnGrardarCambios;
	}

	public void setBtnGuardarCambios(JButton btnGrardarCambios) {
		this.btnGrardarCambios = btnGrardarCambios;
	}
}
