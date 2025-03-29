package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import VistaPropias.CellRenderer;
import VistaPropias.CellRendererTablaRemitos;
import VistaPropias.CellRendererTablaRoles;
import presentacion.controlador.ControladorSalidas;

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
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Enumeration;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

public class VentanaRemitoGenerado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblEquiposParaRemito;
	private DefaultTableModel modelEquiposParaRemito;
	private String[] nombreColumnas = { "ELS", "EQUIPO", "MODELO", "N° SERIE", "ENVIADO" };
	public static int est;
	private JPanel panel;
	private JScrollPane scrollPane;

	@SuppressWarnings("unused")
	private ControladorSalidas controlador;
	private JTextField lblRemito;

	Dimension DimScrollPane;
	Dimension DimPanel;
	Dimension DimContentPane;
	Dimension DimTblReparaciones;
	private JTextField txtCliente;
	private JTextField lblCliente;
	private JTextField textNumeroRemito;
	private JSeparator separator;
	private JSeparator separator_1;
	
	private JButton btnMarcarTodos;
	private JButton btnGuardar;

	protected void this_windowOpened(WindowEvent e) {
		centrarVentana();
	}

	
	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	public VentanaRemitoGenerado(ControladorSalidas controlador) {

		super();
		setResizable(false);
		this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		this.this_windowOpened(null);
		setSize(658, 348);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);

		getContentPane().setLayout(null);

		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(0, 0));
		contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DimContentPane = contentPane.getSize();

		panel = new JPanel();
		panel.setBorder(null);
		panel.setPreferredSize(new Dimension(0, 0));
		panel.setBounds(7, 5, 638, 308);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(panel);
		panel.setLayout(null);
		DimPanel = panel.getSize();

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(3, 85, 630, 182);
		panel.add(scrollPane);

		DimScrollPane = scrollPane.getSize();

		modelEquiposParaRemito = new DefaultTableModel(null, nombreColumnas);
		tblEquiposParaRemito = new JTable(modelEquiposParaRemito);

		modelEquiposParaRemito = new DefaultTableModel(new Object[][] {},
				new String[] { "ELS", "EQUIPO", "MODELO", "N° SERIE","ENVIADO" }) {

					private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return nombreColumnas.length;
			}

			// retornamos el elemento indicado
			public String getColumnName(int col) {
				return nombreColumnas[col];
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}
			
			boolean[] columnEditables = new boolean[] { false, false, false, false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		
		
		tblEquiposParaRemito = new JTable(modelEquiposParaRemito);


		tblEquiposParaRemito.doLayout();
	
		scrollPane.setViewportView(tblEquiposParaRemito);

		

		DimTblReparaciones = tblEquiposParaRemito.getSize();

		lblRemito = new JTextField();
		lblRemito.setBorder(null);
		lblRemito.setEditable(false);
		lblRemito.setBackground(SystemColor.inactiveCaption);
		lblRemito.setFont(new Font("Cambria", Font.BOLD, 22));
		lblRemito.setText("REMITO: ");
		lblRemito.setBounds(10, 7, 107, 28);
		panel.add(lblRemito);
		lblRemito.setColumns(10);

		txtCliente = new JTextField();
		txtCliente.setForeground(new Color(51, 102, 204));
		txtCliente.setBorder(null);
		txtCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		txtCliente.setBackground(SystemColor.inactiveCaption);
		txtCliente.setBounds(121, 36, 345, 28);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		lblCliente = new JTextField();
		lblCliente.setText("CLIENTE: ");
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		lblCliente.setEditable(false);
		lblCliente.setColumns(10);
		lblCliente.setBorder(null);
		lblCliente.setBackground(SystemColor.inactiveCaption);
		lblCliente.setBounds(10, 36, 107, 28);
		panel.add(lblCliente);

		textNumeroRemito = new JTextField();
		textNumeroRemito.setForeground(new Color(51, 102, 204));
		textNumeroRemito.setFont(new Font("Cambria", Font.BOLD, 22));
		textNumeroRemito.setColumns(10);
		textNumeroRemito.setBorder(null);
		textNumeroRemito.setBackground(SystemColor.inactiveCaption);
		textNumeroRemito.setBounds(121, 7, 345, 28);
		panel.add(textNumeroRemito);
		
		btnMarcarTodos = new JButton("MARCAR TODOS");
		btnMarcarTodos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMarcarTodos.setBackground(Color.ORANGE);
		btnMarcarTodos.setFont(new Font("Cambria", Font.BOLD, 9));
		btnMarcarTodos.setBounds(203, 274, 102, 28);
		panel.add(btnMarcarTodos);
		
		separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(96, 72, 460, 2);
		panel.add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(96, 68, 460, 2);
		panel.add(separator_1);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setFont(new Font("Cambria", Font.BOLD, 9));
		btnGuardar.setBackground(new Color(50, 205, 50));
		btnGuardar.setBounds(336, 274, 102, 28);
		panel.add(btnGuardar);

		tblEquiposParaRemito.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 60, 200, 150, 100,100, 100 };

		for (int i = 0; i < tblEquiposParaRemito.getColumnCount(); i++) {

			tblEquiposParaRemito.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

		}

		this.setVisible(true);

	}

	public JTextField getTextNumeroRemito() {
		return textNumeroRemito;
	}

	public void setTextNumeroRemito(JTextField textNumeroRemito) {
		this.textNumeroRemito = textNumeroRemito;
	}

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaRemitos(table));
		}
	}

	@SuppressWarnings("unused")
	private MaskFormatter mascara() {
		// Inicializamos el objeto
		MaskFormatter mascara = new MaskFormatter();
		// Entramos en un try/catch por alguna eventualidad
		try {

			mascara = new MaskFormatter("########");
			mascara.setPlaceholderCharacter('#');
		} catch (ParseException e) {
			// Alg�n error que pueda ocurrir
			e.printStackTrace();
		}
		return mascara;
	}

	public DefaultTableModel getModelEquiposParaRemito() {
		return modelEquiposParaRemito;
	}

	public void setModelEquiposParaRemito(DefaultTableModel modelEquiposParaRemito) {
		this.modelEquiposParaRemito = modelEquiposParaRemito;
	}

	public JTable getTblEquiposParaRemito() {
		return tblEquiposParaRemito;
	}

	public void setTblEquiposParaRemito(JTable tblEquiposParaRemito) {
		this.tblEquiposParaRemito = tblEquiposParaRemito;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
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

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public void setTxtCliente(JTextField txtCliente) {
		this.txtCliente = txtCliente;
	}
	
	public JButton getBtnMarcarTodos() {
		return btnMarcarTodos;
	}

	public void setBtnMarcarTodos(JButton btnMarcarTodos) {
		this.btnMarcarTodos = btnMarcarTodos;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}
}
