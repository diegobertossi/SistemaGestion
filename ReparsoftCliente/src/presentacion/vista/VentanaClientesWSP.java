package presentacion.vista;

import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import VistaPropias.CellRendererTablaClientes;
import VistaPropias.JTextNum;
import presentacion.controlador.ControladorReparacion;
import javax.swing.border.MatteBorder;

public class VentanaClientesWSP extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DefaultTableModel modelClientesWSP;
	private String[] nombreColumnasClientesWSP = { "ORGANIZACIÓN","CONTACTO", "TELÉFONO" };
	private JTable tablaClientesWSP;
	private JButton btnAgregarCliente;
	private JButton btnEditarCliente;
	private JButton btnEliminarCliente;
	private JButton btnGuardarEdicion;
	private JButton btnGuardarNuevo;
	private JButton btnCancelarNuevo;
	private JButton btnCancelarEdicion;
	private JTextField txtNombre;
	private JTextNum txtTelefono;
	private JTextField txtOrganizacion;

	@SuppressWarnings("unused")
	private ControladorReparacion controlador;

	@SuppressWarnings("unused")
	public VentanaClientesWSP(ControladorReparacion controlador) {

		super();
		setResizable(false);
		this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 554, 392);
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
		panel.setBounds(0, 0, 549, 364);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		JScrollPane spClientes = new JScrollPane();
		spClientes.setBackground(Color.WHITE);
		spClientes.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		spClientes.setFont(new Font("Cambria", Font.PLAIN, 11));
		spClientes.setBounds(21, 67, 396, 170);
		panel.add(spClientes);

		// modelUsuarios = new DefaultTableModel(null, nombreColumnasUsuarios);
		modelClientesWSP = new DefaultTableModel(new Object[][] {}, new String[] { "ORGANIZACIÓN","CONTACTO", "TELÉFONO" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8981972222642830943L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class,String.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false,false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
		Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

		tablaClientesWSP = new JTable(modelClientesWSP);
		tablaClientesWSP.getTableHeader().setForeground(Color.BLACK);
		tablaClientesWSP.getTableHeader().setFont(fuenteCabecera);
		tablaClientesWSP.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tablaClientesWSP.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tablaClientesWSP.setShowGrid(true);

		tablaClientesWSP.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaClientesWSP.setAutoCreateColumnsFromModel(false);
		tablaClientesWSP.getTableHeader().setReorderingAllowed(false);
		spClientes.setViewportView(tablaClientesWSP);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(140, 232, 113, -8);
		panel.add(horizontalStrut);

		JLabel lblClientes = new JLabel("CLIENTES PARA WHATSAPP");
		lblClientes.setForeground(new Color(0, 0, 128));
		lblClientes.setFont(new Font("Cambria", Font.BOLD, 18));
		lblClientes.setBounds(21, 32, 252, 14);
		panel.add(lblClientes);

		btnGuardarEdicion = new JButton("<html><center>Guardar Edición</html>");
		btnGuardarEdicion.setVisible(false);
		btnGuardarEdicion.setBackground(new Color(152, 251, 152));
		btnGuardarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGuardarEdicion.setBounds(335, 286, 99, 43);
		panel.add(btnGuardarEdicion);

		btnCancelarEdicion = new JButton("<html><center>Cancelar Edición</html>");
		btnCancelarEdicion.setVisible(false);
		btnCancelarEdicion.setBackground(new Color(255, 0, 0));
		btnCancelarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelarEdicion.setBounds(456, 286, 89, 43);
		panel.add(btnCancelarEdicion);

		btnGuardarNuevo = new JButton("<html><p>Guardar</p></html>");
		btnGuardarNuevo.setVisible(false);
		btnGuardarNuevo.setBackground(new Color(152, 251, 152));
		btnGuardarNuevo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGuardarNuevo.setBounds(335, 286, 99, 43);
		panel.add(btnGuardarNuevo);

		btnCancelarNuevo = new JButton("<html><p>Cancelar</p></html>");
		btnCancelarNuevo.setVisible(false);
		btnCancelarNuevo.setBackground(new Color(255, 0, 0));
		btnCancelarNuevo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelarNuevo.setBounds(456, 286, 89, 43);
		panel.add(btnCancelarNuevo);

		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtNombre.setBounds(107, 283, 198, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		txtTelefono = new JTextNum(14);
		txtTelefono.setEditable(false);
		txtTelefono.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelefono.setBounds(107, 305, 198, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);

		JLabel lblNombre_1 = new JLabel("Nombre");
		lblNombre_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNombre_1.setBounds(21, 283, 76, 20);
		panel.add(lblNombre_1);

		JLabel lblTelefono = new JLabel("Teléfono");
		lblTelefono.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTelefono.setBounds(21, 305, 76, 20);
		panel.add(lblTelefono);

		txtOrganizacion = new JTextField();
		txtOrganizacion.setEditable(false);
		txtOrganizacion.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtOrganizacion.setBounds(107, 261, 198, 20);
		panel.add(txtOrganizacion);
		txtOrganizacion.setColumns(10);

		JLabel lblOrganizacion = new JLabel("Organización");
		lblOrganizacion.setFont(new Font("Cambria", Font.BOLD, 12));
		lblOrganizacion.setBounds(21, 261, 76, 20);
		panel.add(lblOrganizacion);
		
				btnAgregarCliente = new JButton("Agregar");
				btnAgregarCliente.setBounds(444, 104, 101, 23);
				panel.add(btnAgregarCliente);
				btnAgregarCliente.setBackground(new Color(152, 251, 152));
				btnAgregarCliente.setFont(new Font("Cambria", Font.BOLD, 14));
				
						btnEditarCliente = new JButton("Editar");
						btnEditarCliente.setBounds(444, 136, 101, 23);
						panel.add(btnEditarCliente);
						btnEditarCliente.setBackground(new Color(240, 230, 140));
						btnEditarCliente.setFont(new Font("Cambria", Font.BOLD, 14));
						
								btnEliminarCliente = new JButton("Eliminar");
								btnEliminarCliente.setBounds(444, 170, 101, 23);
								panel.add(btnEliminarCliente);
								btnEliminarCliente.setBackground(new Color(255, 0, 0));
								btnEliminarCliente.setFont(new Font("Cambria", Font.BOLD, 14));

		this.setVisible(true);
	}

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaClientes());
		}
	}

	public JButton getBtnGuardarEdicion() {
		return btnGuardarEdicion;
	}

	public void setBtnGuardarEdicion(JButton btnGuardarEdicion) {
		this.btnGuardarEdicion = btnGuardarEdicion;
	}

	public JButton getBtnCancelarEdicion() {
		return btnCancelarEdicion;
	}

	public void setBtnCancelarEdicion(JButton btnCancelarEdicion) {
		this.btnCancelarEdicion = btnCancelarEdicion;
	}

	public DefaultTableModel getModelClientesWSP() {
		return modelClientesWSP;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void getErrorMsj(String msj) {
		JOptionPane.showMessageDialog(null, msj);
	}

	public JButton getBtnGuardarNuevo() {
		return btnGuardarNuevo;
	}

	public void setBtnGuardarNuevo(JButton btnGuardarNuevo) {
		this.btnGuardarNuevo = btnGuardarNuevo;
	}

	public JButton getBtnCancelarNuevo() {
		return btnCancelarNuevo;
	}

	public void setBtnCancelarNuevo(JButton btnCancelarNuevo) {
		this.btnCancelarNuevo = btnCancelarNuevo;
	}

	public String[] getNombreColumnasClientesWSP() {
		return nombreColumnasClientesWSP;
	}

	public void setNombreColumnasClientesWSP(String[] nombreColumnasClientesWSP) {
		this.nombreColumnasClientesWSP = nombreColumnasClientesWSP;
	}

	public JTable getTablaClienteSWSP() {
		return tablaClientesWSP;
	}

	public void setTablaClientesWSP(JTable tablaUsuarios) {
		this.tablaClientesWSP = tablaUsuarios;
	}

	public JButton getBtnAgregarCliente() {
		return btnAgregarCliente;
	}

	public void setBtnAgregarCliente(JButton btnAgregarCliente) {
		this.btnAgregarCliente = btnAgregarCliente;
	}

	public JButton getBtnEditarCliente() {
		return btnEditarCliente;
	}

	public void setBtnEditarCliente(JButton btnEditarCliente) {
		this.btnEditarCliente = btnEditarCliente;
	}

	public JButton getBtnEliminarCliente() {
		return btnEliminarCliente;
	}

	public void setBtnEliminarCliente(JButton btnEliminarCliente) {
		this.btnEliminarCliente = btnEliminarCliente;
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public JTextField getTxtOrganizacion() {
		return txtOrganizacion;
	}

	public void setTxtOrganizacion(JTextField txtOrganizacion) {
		this.txtOrganizacion = txtOrganizacion;
	}

	public void setModelClientesWSP(DefaultTableModel modelClientesWSP) {
		this.modelClientesWSP = modelClientesWSP;
	}
}
