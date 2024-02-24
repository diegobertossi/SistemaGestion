package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import presentacion.controlador.ControladorUsuarios;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
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
	
	public VentanaPermisos(ControladorUsuarios controlador)
	{
		
		super();
		setResizable(false);
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 952, 566);
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
		panel.setBounds(0, 0, 936, 527);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(502, 125, 358, 337);
		panel.add(scrollPane);
		
		this.modelPermisosTenidos = new DefaultTableModel(new Object[][] {
		},
		new String[] {
			"Sel","Pantalla", "Modulo Padre"
		}
				) {
		/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {
			Boolean.class,String.class, String.class
		};
		public Class<?> getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}
		boolean[] columnEditables = new boolean[] {
				true,false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
	};
	
		this.tblPermisosTenidos = new JTable(modelPermisosTenidos);
		tblPermisosTenidos.getTableHeader().setReorderingAllowed(false);
		
		scrollPane.setViewportView(tblPermisosTenidos);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(78, 125, 358, 337);
		panel.add(scrollPane_1);
		
		this.modelPermisosFaltantes = new DefaultTableModel(new Object[][] {
		},
		new String[] {
			"Sel","Pantalla", "Modulo Padre"
		}
	) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {
			Boolean.class,String.class, String.class
		};
		public Class<?> getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}
		boolean[] columnEditables = new boolean[] {
				true,false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
	};
		
		this.tblPermisosFaltantes = new JTable(modelPermisosFaltantes);
		tblPermisosFaltantes.getTableHeader().setReorderingAllowed(false);

		scrollPane_1.setViewportView(tblPermisosFaltantes);
		
		cmbRoles = new JComboBox<Object>();
		cmbRoles.setBounds(126, 64, 215, 20);
		panel.add(cmbRoles);
		
		JLabel lblRol = new JLabel("Rol:");
		lblRol.setForeground(new Color(0, 0, 128));
		lblRol.setFont(new Font("Cambria", Font.BOLD, 14));
		lblRol.setBounds(78, 66, 46, 14);
		panel.add(lblRol);
		
		btnAgregar = new JButton("");
		btnAgregar.setBackground(new Color(60, 179, 113));
		btnAgregar.setBounds(446, 239, 46, 33);
		btnAgregar.setIcon(new ImageIcon(this.getClass().getResource("/siguiente.png")));
		panel.add(btnAgregar);
		
		btnRemover = new JButton("");
		btnRemover.setForeground(new Color(255, 255, 255));
		btnRemover.setBackground(new Color(255, 99, 71));
		btnRemover.setBounds(446, 281, 46, 33);
		btnRemover.setIcon(new ImageIcon(this.getClass().getResource("/anterior.png")));
		panel.add(btnRemover);
		
		JLabel lblPermisosPorRol = new JLabel("PERMISOS POR ROL");
		lblPermisosPorRol.setForeground(new Color(0, 0, 128));
		lblPermisosPorRol.setFont(new Font("Cambria", Font.BOLD, 18));
		lblPermisosPorRol.setBounds(78, 22, 182, 20);
		panel.add(lblPermisosPorRol);
		
		lblSinAccesoA = new JLabel("Sin acceso a:");
		lblSinAccesoA.setForeground(new Color(0, 0, 255));
		lblSinAccesoA.setFont(new Font("Cambria", Font.BOLD, 14));
		lblSinAccesoA.setBounds(78, 99, 87, 14);
		panel.add(lblSinAccesoA);
		
		lblConAccesoA = new JLabel("Con acceso a:");
		lblConAccesoA.setForeground(new Color(0, 0, 255));
		lblConAccesoA.setFont(new Font("Cambria", Font.BOLD, 14));
		lblConAccesoA.setBounds(502, 100, 87, 14);
		panel.add(lblConAccesoA);
		
		setVisible(true);
		
		
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
