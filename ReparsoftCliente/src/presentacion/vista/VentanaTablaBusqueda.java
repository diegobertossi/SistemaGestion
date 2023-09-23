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
import VistaPropias.CellRendererTablaBusqueda;
import presentacion.controlador.ControladorBusquedas;
import presentacion.controlador.ControladorListados;
import presentacion.controlador.ControladorReparacion;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
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

public class VentanaTablaBusqueda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblReparaciones;
	private DefaultTableModel modelReparaciones;
	private String[] nombreColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO", "COMP. ORIGINAL","COMP. REEMPLAZO" };
	private JButton btnMax;
	public static int est;
	private JPanel panel;
	private JScrollPane  scrollPane ;
//
	private ControladorBusquedas controlador;
//
	Dimension DimScrollPane;
	Dimension DimPanel;
	Dimension DimContentPane;
	Dimension DimTblReparaciones;
	private JTextField txtCategoriaBusqueda;

	protected void this_windowOpened(WindowEvent e) {
		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	public VentanaTablaBusqueda(ControladorBusquedas controlador) {

		super();
		setUndecorated(true);
		setResizable(false);
		this.controlador = controlador;

		this.this_windowOpened(null);
		setSize(1241, 411);
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
		panel.setBounds(0, 0, 1241, 411);
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
		scrollPane.setBounds(10, 73, 1221, 314);
		panel.add(scrollPane);
		
		DimScrollPane = scrollPane.getSize();

		modelReparaciones = new DefaultTableModel(null, nombreColumnas);
		tblReparaciones = new JTable(modelReparaciones);

		modelReparaciones = new DefaultTableModel(new Object[][] {},
				new String[] { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO", "COMP. ORIGINAL","COMP. REEMPLAZO" }) {

			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
		Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

		tblReparaciones = new JTable(modelReparaciones);

		tblReparaciones.setFont(fuenteCeldas);

		tblReparaciones.getTableHeader().setForeground(Color.BLACK);
		tblReparaciones.getTableHeader().setFont(fuenteCabecera);
		tblReparaciones.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tblReparaciones.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tblReparaciones.setShowGrid(true);
		tblReparaciones.setCellSelectionEnabled(true);

		tblReparaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblReparaciones.doLayout();

		scrollPane.setViewportView(tblReparaciones);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tblReparaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblReparaciones.setAutoCreateColumnsFromModel(false);

		DimTblReparaciones = tblReparaciones.getSize();

		btnMax = new JButton("");
		btnMax.setBounds(999, 14, 30, 25);
		btnMax.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
		panel.add(btnMax);
		
		txtCategoriaBusqueda = new JTextField();
		txtCategoriaBusqueda.setForeground(new Color(0, 0, 205));
		txtCategoriaBusqueda.setBorder(null);
		txtCategoriaBusqueda.setFont(new Font("Cambria", Font.BOLD, 18));
		txtCategoriaBusqueda.setBackground(SystemColor.inactiveCaption);
		txtCategoriaBusqueda.setBounds(10, 11, 909, 28);
		panel.add(txtCategoriaBusqueda);
		txtCategoriaBusqueda.setColumns(10);

		tblReparaciones.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 60, 80, 150, 100, 200, 100, 200, 150, 150};

		for (int i = 0; i < tblReparaciones.getColumnCount(); i++) {

			tblReparaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

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
					tblReparaciones.setSize(DimTblReparaciones);

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
			tc.setCellRenderer(new CellRendererTablaBusqueda());
		}
	}

	public DefaultTableModel getModelReparaciones() {
		return modelReparaciones;
	}

	public void setModelReparaciones(DefaultTableModel modelReparaciones) {
		this.modelReparaciones = modelReparaciones;
	}

	public JTable getTblReparaciones() {
		return tblReparaciones;
	}

	public void setTblReparaciones(JTable tblReparaciones) {
		this.tblReparaciones = tblReparaciones;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
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

	public JTextField getTxtCategoriaBusqueda() {
		return txtCategoriaBusqueda;
	}

	public void setTxtCategoriaBusqueda(JTextField txtCategoriaBusqueda) {
		this.txtCategoriaBusqueda = txtCategoriaBusqueda;
	}
}
