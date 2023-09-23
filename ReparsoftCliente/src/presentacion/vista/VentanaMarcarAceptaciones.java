package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.plaf.UIManagerExt;

import VistaPropias.CellRenderer;
import VistaPropias.CellRendererTablaMarcarAceptaciones2;
import VistaPropias.CellRendererTablaMarcarAceptaciones;
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

public class VentanaMarcarAceptaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// private JTable tblReparaciones;
	private JTable tblReparaciones_1;
	private DefaultTableModel modelReparaciones;

	private String[] nombreColumnas = { "ELS","AVISO", "CLIENTE", "SUCURSAL", "EQUIPO","MODELO",
			"ESTADO TEC", "ESTADO COM", "ACEP", "NO ACEP","GTÍA","PEND" };
	
//	private String[] nombreColumnas = { "ELS","AVISO", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO", "N° SERIE",
//			"ESTADO TEC", "ESTADO COM" };

	private JButton btnFiltrar;
	private JButton btnMostrarTodo;
	private JButton btnActualizar;

	private JButton btnMax;
	public static int est;
	private JPanel panel;
	private JScrollPane scrollPane;

    private ControladorPresupuestos controladorPresupuestos;
	
	private JComboBox comboFiltroCliente;
	private JRadioButton radioButtonCliente;

	private JComboBox comboFiltroSucursal;
	private JRadioButton radioButtonSucursal;

	private JComboBox comboFiltroELS;
	private JRadioButton radioButtonELS;

	private JLabel lblAviso;
	private JComboBox comboFiltroAviso;
	private JRadioButton radioButtonAviso;

	private JSeparator separator_1;

	private JPanel panel_1;

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

	public VentanaMarcarAceptaciones(ControladorPresupuestos controlador) {

		super();
		setUndecorated(true);
		setResizable(false);
		this.controladorPresupuestos = controlador;

		this.this_windowOpened(null);
		setSize(1225, 600);
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
		panel.setBounds(-12, 0, 1227, 561);
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
		scrollPane.setBounds(20, 227, 1197, 334);
		panel.add(scrollPane);

		DimScrollPane = scrollPane.getSize();

		modelReparaciones = new DefaultTableModel(null, nombreColumnas);
		tblReparaciones_1 = new JTable(modelReparaciones);

		modelReparaciones = new DefaultTableModel(new Object[][] {}, nombreColumnas) {

//			Class[] columnTypes = new Class[] { Integer.class,String.class,  Integer.class, String.class, String.class, String.class,
//					String.class, String.class, String.class, String.class, String.class, Boolean.class,Boolean.class,Boolean.class,Boolean.class };
			
			Class[] columnTypes = new Class[] { Integer.class, Integer.class, String.class, String.class,
					String.class, String.class, String.class, String.class,Boolean.class,Boolean.class,Boolean.class,Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false,
					false,true,true,true,false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};

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

		Font fuenteCabecera = new Font("Tahoma", Font.BOLD, 13);
		Font fuenteCeldas = new Font("Tahoma", Font.PLAIN, 12);

		tblReparaciones_1 = new JTable(modelReparaciones);
		// tblReparaciones_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
		panel_1.setBounds(90, 65, 578, 134);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblCliente = new JLabel("CLIENTE:");
		lblCliente.setBounds(6, 13, 60, 14);
		panel_1.add(lblCliente);
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		comboFiltroCliente = new JComboBox();
		comboFiltroCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroCliente.setBounds(120, 10, 191, 20);
		panel_1.add(comboFiltroCliente);
		comboFiltroCliente.setEnabled(false);

		radioButtonCliente = new JRadioButton("");
		radioButtonCliente.setBounds(317, 10, 21, 20);
		panel_1.add(radioButtonCliente);
		radioButtonCliente.setBackground(SystemColor.inactiveCaption);

		comboFiltroSucursal = new JComboBox();
		comboFiltroSucursal.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroSucursal.setBounds(120, 40, 191, 20);
		panel_1.add(comboFiltroSucursal);
		comboFiltroSucursal.setEnabled(false);

		JLabel lblSucursal = new JLabel("SUCURSAL:");
		lblSucursal.setBounds(6, 43, 72, 14);
		panel_1.add(lblSucursal);
		lblSucursal.setFont(new Font("Cambria", Font.BOLD, 14));

		radioButtonSucursal = new JRadioButton("");
		radioButtonSucursal.setBounds(317, 40, 21, 20);
		panel_1.add(radioButtonSucursal);
		radioButtonSucursal.setBackground(SystemColor.inactiveCaption);

		lblAviso = new JLabel("AVISO:");
		lblAviso.setBounds(6, 74, 60, 14);
		panel_1.add(lblAviso);
		lblAviso.setFont(new Font("Cambria", Font.BOLD, 14));

		comboFiltroAviso = new JComboBox();
		comboFiltroAviso.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroAviso.setBounds(120, 71, 191, 20);
		panel_1.add(comboFiltroAviso);
		comboFiltroAviso.setEnabled(false);

		radioButtonAviso = new JRadioButton("");
		radioButtonAviso.setBounds(317, 71, 21, 20);
		panel_1.add(radioButtonAviso);
		radioButtonAviso.setBackground(SystemColor.inactiveCaption);

		JSeparator separator = new JSeparator();
		separator.setBounds(355, 15, 5, 103);
		panel_1.add(separator);
		separator.setForeground(SystemColor.textInactiveText);
		separator.setOrientation(SwingConstants.VERTICAL);

		separator_1 = new JSeparator();
		separator_1.setBounds(358, 15, 2, 103);
		panel_1.add(separator_1);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(SystemColor.textInactiveText);

		radioButtonELS = new JRadioButton("");
		radioButtonELS.setBackground(SystemColor.inactiveCaption);
		radioButtonELS.setBounds(317, 100, 21, 20);
		panel_1.add(radioButtonELS);

		comboFiltroELS = new JComboBox();
		comboFiltroELS.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboFiltroELS.setEnabled(false);
		comboFiltroELS.setBounds(120, 100, 191, 20);
		panel_1.add(comboFiltroELS);

		JLabel lblEls = new JLabel("ELS:");
		lblEls.setFont(new Font("Cambria", Font.BOLD, 14));
		lblEls.setBounds(6, 102, 60, 14);
		panel_1.add(lblEls);

		btnFiltrar = new JButton("FILTRAR");
		btnFiltrar.setBounds(403, 15, 132, 48);
		panel_1.add(btnFiltrar);
		btnFiltrar.setFont(new Font("Cambria", Font.BOLD, 14));

		btnMostrarTodo = new JButton("MOSTRAR TODO");
		btnMostrarTodo.setBounds(403, 70, 132, 48);
		panel_1.add(btnMostrarTodo);
		btnMostrarTodo.setFont(new Font("Cambria", Font.BOLD, 14));
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(5, 28, 107, 2);
		panel_1.add(separator_2);
		
		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setBounds(3, 58, 107, 2);
		panel_1.add(separator_2_1);
		
		JSeparator separator_2_2 = new JSeparator();
		separator_2_2.setBounds(6, 89, 107, 2);
		panel_1.add(separator_2_2);
		
		JSeparator separator_2_3 = new JSeparator();
		separator_2_3.setBounds(3, 118, 107, 2);
		panel_1.add(separator_2_3);

		txtListadosDeEquipos = new JTextField();
		txtListadosDeEquipos.setEditable(false);
		txtListadosDeEquipos.setBackground(SystemColor.inactiveCaption);
		txtListadosDeEquipos.setFont(new Font("Cambria", Font.BOLD, 22));
		txtListadosDeEquipos.setText("LISTADOS DE EQUIPOS");
		txtListadosDeEquipos.setBounds(35, 11, 253, 28);
		panel.add(txtListadosDeEquipos);
		txtListadosDeEquipos.setColumns(10);

		btnMax = new JButton("");
		btnMax.setBounds(1187, 11, 30, 25);
		btnMax.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
		panel.add(btnMax);
		
		btnActualizar = new JButton("<html><center>GUARDAR CAMBIOS Y ACTUALIZAR</html>");
		btnActualizar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnActualizar.setBounds(998, 101, 147, 63);
		panel.add(btnActualizar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 153));
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(705, 93, 262, 78);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html><center>SE MUESTRAN LOS EQUIPOS CUYOS PRESUPUESTOS FUERON ENVIADOS Y ESTÁN PENDIENTES DE RESPUESTA<html>");
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(0, 0, 262, 78);
		panel_2.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 14));

		tblReparaciones_1.getTableHeader().setReorderingAllowed(false);

		//int[] anchos = { 60, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 ,100,100};
		int[] anchos = { 65, 65, 130, 130, 195, 130, 90, 90, 70,70,70,70};

		for (int i = 0; i < tblReparaciones_1.getColumnCount(); i++) {

			tblReparaciones_1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

		}
		
		
		for (int i = 0; i < tblReparaciones_1.getColumnCount(); i++) {

			System.out.println(tblReparaciones_1.getColumnCount());

		}
		
		for (int i = tblReparaciones_1.getColumnCount() - 4; i < tblReparaciones_1.getColumnCount(); i++) {
			tblReparaciones_1.getColumnModel().getColumn(i).setCellRenderer(new CellRendererTablaMarcarAceptaciones());
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
					contentPane.setSize(DimContentPane);
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

	public JComboBox getRadio() {
		return comboFiltroAviso;
	}

	public void setComboFiltroAviso(JComboBox comboFiltroAviso) {
		this.comboFiltroAviso = comboFiltroAviso;
	}

	public JRadioButton getRadioButtonAviso() {
		return radioButtonAviso;
	}

	public void setRadioButtonAviso(JRadioButton radioButtonAviso) {
		this.radioButtonAviso = radioButtonAviso;
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

	public JButton getBtnActualizar() {
		return btnActualizar;
	}

	public void setBtnActualizar(JButton btnActualizar) {
		this.btnActualizar = btnActualizar;
	}
}
