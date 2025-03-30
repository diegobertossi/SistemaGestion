package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import VistaPropias.CellRendererTablaListado;
import VistaPropias.CellRendererTablaRemitos;
import VistaPropias.CellRendererTablaRoles;
import presentacion.controlador.ControladorUsuarios;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class VentanaPermisos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JComboBox<?> cmbRoles;
	private JTable tblPermisosTenidos;
	private DefaultTableModel modelPermisosTenidos;
	private JTable tblPermisosFaltantes;
	private DefaultTableModel modelPermisosFaltantes;
	private JButton btnAgregar;
	private JButton btnRemover;
	@SuppressWarnings("unused")
	private ControladorUsuarios controlador;
	private JLabel lblSinAccesoA;
	private JLabel lblConAccesoA;

	public VentanaPermisos(ControladorUsuarios controlador) {

		super();
		setResizable(false);
		this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 731, 394);
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
		panel.setBounds(0, 0, 718, 352);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(389, 114, 311, 215);
		panel.add(scrollPane);

		this.modelPermisosTenidos = new DefaultTableModel(new Object[][] {},
				new String[] {  "SEL", "PANTALLA", "MODULO PADRE" }) {

			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Boolean.class, String.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { true, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
				
				
			}
		};

		this.tblPermisosTenidos = new JTable(modelPermisosTenidos);
		tblPermisosTenidos.getTableHeader().setReorderingAllowed(false);
		tblPermisosTenidos.getTableHeader().setResizingAllowed(false);
			
		int[] anchos = {20, 60, 60};
		scrollPane.setViewportView(tblPermisosTenidos);

		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(16, 114, 311, 215);
		panel.add(scrollPane_1);

		this.modelPermisosFaltantes = new DefaultTableModel(new Object[][] {},
				new String[] { "SEL", "PANTALLA", "MODULO PADRE" }) {
		
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Boolean.class, String.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { true, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		tblPermisosFaltantes = new JTable(modelPermisosFaltantes);
		tblPermisosFaltantes.getTableHeader().setReorderingAllowed(false);
		tblPermisosFaltantes.getTableHeader().setResizingAllowed(false);

		
		
		scrollPane_1.setViewportView(tblPermisosFaltantes);
				
		
		
		cmbRoles = new JComboBox<Object>();
		cmbRoles.setBounds(58, 53, 215, 20);
		panel.add(cmbRoles);

		JLabel lblRol = new JLabel("ROL:");
		lblRol.setForeground(new Color(25, 25, 112));
		lblRol.setFont(new Font("Cambria", Font.BOLD, 14));
		lblRol.setBounds(16, 55, 46, 14);
		panel.add(lblRol);

		btnAgregar = new JButton("");
		btnAgregar.setBackground(new Color(60, 179, 113));
		btnAgregar.setBounds(336, 183, 46, 33);
		btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/siguiente.png")));
		panel.add(btnAgregar);

		btnRemover = new JButton("");
		btnRemover.setForeground(new Color(255, 255, 255));
		btnRemover.setBackground(new Color(255, 99, 71));
		btnRemover.setBounds(336, 225, 46, 33);
		btnRemover.setIcon(new ImageIcon(this.getClass().getResource("/anterior.png")));
		panel.add(btnRemover);

		JLabel lblPermisosPorRol = new JLabel("PERMISOS POR ROL");
		lblPermisosPorRol.setForeground(new Color(25, 25, 112));
		lblPermisosPorRol.setFont(new Font("Cambria", Font.BOLD, 20));
		lblPermisosPorRol.setBounds(16, 11, 190, 20);
		panel.add(lblPermisosPorRol);

		lblSinAccesoA = new JLabel("SIN ACCESO A:");
		lblSinAccesoA.setForeground(new Color(25, 25, 112));
		lblSinAccesoA.setFont(new Font("Cambria", Font.BOLD, 14));
		lblSinAccesoA.setBounds(16, 92, 107, 14);
		panel.add(lblSinAccesoA);

		lblConAccesoA = new JLabel("CON ACCESO A:");
		lblConAccesoA.setForeground(new Color(25, 25, 112));
		lblConAccesoA.setFont(new Font("Cambria", Font.BOLD, 14));
		lblConAccesoA.setBounds(389, 92, 107, 14);
		panel.add(lblConAccesoA);
		

		for (int i = 0; i < tblPermisosTenidos.getColumnCount(); i++) {
			tblPermisosTenidos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
			tblPermisosFaltantes.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
			
		}
		
		

		setVisible(true);

	}
	
	
	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaRoles(table));
		}
	}
	

	@SuppressWarnings("rawtypes")
	public JComboBox getCmbRoles() {
		return cmbRoles;
	}

	public JTable getTblPermisosTenidos() {
		return tblPermisosTenidos;
	}

	public DefaultTableModel getModelPermisosTenidos() {
		return modelPermisosTenidos;
	}

	public JTable getTblPermisosFaltantes() {
		return tblPermisosFaltantes;
	}

	public DefaultTableModel getModelPermisosFaltantes() {
		return modelPermisosFaltantes;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JButton getBtnRemover() {
		return btnRemover;
	}
}
