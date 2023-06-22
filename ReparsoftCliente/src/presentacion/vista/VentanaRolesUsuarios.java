package presentacion.vista;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import com.toedter.calendar.JDateChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.toedter.calendar.JYearChooser;

import VistaPropias.CellRendererTablaClientes;
import VistaPropias.JTextNum;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorUsuLogin;
import presentacion.controlador.ControladorUsuarios;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;

public class VentanaRolesUsuarios extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DefaultTableModel modelUsuarios;
	private String[] nombreColumnasUsuarios = { "Usuario", "DNI" };
	private JTable tablaUsuarios;
	private JButton btnAgregarUsuario;
	private JButton btnEditarUsuario;
	private JButton btnEliminarUsuario;
	private JButton btnGuardarEdicion;
	private JButton btnGuardarNuevo;
	private JButton btnCancelarNuevo;
	private JButton btnCancelarEdicion;
	private JTextField txtNombreUsuario;
	private JTextNum txtTelefonoUsuario;
	private JTextField txtApellidoUsuario;
	private JTextField txtEmailUsuario;
	private JTextNum txtDNI;
	private JLabel lblDni;
	private JComboBox comboRoles;
	private JTextField txtLogin;
	private JTextField txtPass;

	private JButton btnPermisosXrol;

	private ControladorUsuarios controlador;
	private JPanel panel_1;

	public VentanaRolesUsuarios(ControladorUsuarios controlador) {

		super();
		setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 582, 474);
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
		panel.setBounds(0, 0, 566, 436);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		JScrollPane spUsuarios = new JScrollPane();
		spUsuarios.setBackground(Color.WHITE);
		spUsuarios.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		spUsuarios.setFont(new Font("Cambria", Font.PLAIN, 11));
		spUsuarios.setBounds(21, 67, 322, 170);
		panel.add(spUsuarios);

		// modelUsuarios = new DefaultTableModel(null, nombreColumnasUsuarios);
		modelUsuarios = new DefaultTableModel(new Object[][] {}, new String[] { "USUARIO", "DNI" }) {
			Class[] columnTypes = new Class[] { String.class, int.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

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

		tablaUsuarios = new JTable(modelUsuarios);
		tablaUsuarios.getTableHeader().setForeground(Color.BLACK);
		tablaUsuarios.getTableHeader().setFont(fuenteCabecera);
		tablaUsuarios.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tablaUsuarios.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tablaUsuarios.setShowGrid(true);

		
		tablaUsuarios.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaUsuarios.setAutoCreateColumnsFromModel(false);
		tablaUsuarios.getTableHeader().setReorderingAllowed(false);
		spUsuarios.setViewportView(tablaUsuarios);

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

		JLabel lblUsuarios = new JLabel("USUARIOS");
		lblUsuarios.setForeground(new Color(0, 0, 128));
		lblUsuarios.setFont(new Font("Cambria", Font.BOLD, 18));
		lblUsuarios.setBounds(21, 32, 94, 14);
		panel.add(lblUsuarios);

		panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(100, 149, 237));
		panel_1.setBounds(369, 67, 162, 170);
		panel.add(panel_1);
		panel_1.setLayout(null);

		btnAgregarUsuario = new JButton("Agregar");
		btnAgregarUsuario.setBounds(36, 16, 101, 23);
		panel_1.add(btnAgregarUsuario);
		btnAgregarUsuario.setBackground(new Color(152, 251, 152));
		btnAgregarUsuario.setFont(new Font("Cambria", Font.BOLD, 14));

		btnEditarUsuario = new JButton("Editar");
		btnEditarUsuario.setBounds(36, 48, 101, 23);
		panel_1.add(btnEditarUsuario);
		btnEditarUsuario.setBackground(new Color(240, 230, 140));
		btnEditarUsuario.setFont(new Font("Cambria", Font.BOLD, 14));

		btnEliminarUsuario = new JButton("Eliminar");
		btnEliminarUsuario.setBounds(36, 82, 101, 23);
		panel_1.add(btnEliminarUsuario);
		btnEliminarUsuario.setBackground(new Color(255, 0, 0));
		btnEliminarUsuario.setFont(new Font("Cambria", Font.BOLD, 14));

		btnPermisosXrol = new JButton("<html><center>PERMISOS POR ROL<html>");
		btnPermisosXrol.setBounds(36, 116, 101, 43);
		panel_1.add(btnPermisosXrol);
		btnPermisosXrol.setFont(new Font("Cambria", Font.BOLD, 14));
		btnPermisosXrol.setAlignmentX(SwingConstants.CENTER);
		btnPermisosXrol.setAlignmentY(SwingConstants.CENTER);

		btnGuardarEdicion = new JButton("<html><center>Guardar Edición</html>");
		btnGuardarEdicion.setVisible(false);
		btnGuardarEdicion.setBackground(new Color(152, 251, 152));
		btnGuardarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGuardarEdicion.setBounds(335, 369, 99, 43);
		panel.add(btnGuardarEdicion);

		btnCancelarEdicion = new JButton("<html><center>Cancelar Edición</html>");
		btnCancelarEdicion.setVisible(false);
		btnCancelarEdicion.setBackground(new Color(255, 0, 0));
		btnCancelarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelarEdicion.setBounds(456, 369, 89, 43);
		panel.add(btnCancelarEdicion);

		btnGuardarNuevo = new JButton("<html><p>Guardar</p></html>");
		btnGuardarNuevo.setVisible(false);
		btnGuardarNuevo.setBackground(new Color(152, 251, 152));
		btnGuardarNuevo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGuardarNuevo.setBounds(335, 369, 99, 43);
		panel.add(btnGuardarNuevo);

		btnCancelarNuevo = new JButton("<html><p>Cancelar</p></html>");
		btnCancelarNuevo.setVisible(false);
		btnCancelarNuevo.setBackground(new Color(255, 0, 0));
		btnCancelarNuevo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelarNuevo.setBounds(456, 369, 89, 43);
		panel.add(btnCancelarNuevo);

		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setEditable(false);
		txtNombreUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtNombreUsuario.setBounds(107, 264, 198, 20);
		panel.add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);

		txtTelefonoUsuario = new JTextNum(14);
		txtTelefonoUsuario.setEditable(false);
		txtTelefonoUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelefonoUsuario.setBounds(107, 327, 198, 20);
		panel.add(txtTelefonoUsuario);
		txtTelefonoUsuario.setColumns(10);

		JLabel lblNombre_1 = new JLabel("Nombre");
		lblNombre_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNombre_1.setBounds(21, 264, 76, 14);
		panel.add(lblNombre_1);

		JLabel lblCantidad = new JLabel("Teléfono");
		lblCantidad.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCantidad.setBounds(21, 329, 76, 14);
		panel.add(lblCantidad);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Cambria", Font.BOLD, 12));
		lblApellido.setBounds(21, 286, 76, 14);
		panel.add(lblApellido);

		txtApellidoUsuario = new JTextField();
		txtApellidoUsuario.setEditable(false);
		txtApellidoUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtApellidoUsuario.setBounds(107, 285, 198, 20);
		panel.add(txtApellidoUsuario);
		txtApellidoUsuario.setColumns(10);

		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel.setBounds(21, 350, 76, 14);
		panel.add(lblNewLabel);

		txtEmailUsuario = new JTextField();
		txtEmailUsuario.setEditable(false);
		txtEmailUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtEmailUsuario.setBounds(107, 348, 198, 20);
		panel.add(txtEmailUsuario);
		txtEmailUsuario.setColumns(10);

		txtDNI = new JTextNum(8);
		txtDNI.setEditable(false);
		txtDNI.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDNI.setBounds(107, 306, 198, 20);
		panel.add(txtDNI);
		txtDNI.setColumns(10);

		lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Cambria", Font.BOLD, 12));
		lblDni.setBounds(21, 308, 76, 14);
		panel.add(lblDni);

		JLabel lblRol = new JLabel("ROL : ");
		lblRol.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRol.setBounds(315, 267, 45, 14);
		panel.add(lblRol);

		comboRoles = new JComboBox();
		comboRoles.setForeground(new Color(0, 0, 0));
		comboRoles.setEnabled(false);
		comboRoles.setFont(new Font("Cambria", Font.PLAIN, 12));
		comboRoles.setBounds(360, 264, 185, 20);
		panel.add(comboRoles);

		txtLogin = new JTextField();
		txtLogin.setEditable(false);
		txtLogin.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtLogin.setBounds(107, 369, 198, 20);
		panel.add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblLogin = new JLabel("Usuario");
		lblLogin.setFont(new Font("Cambria", Font.BOLD, 12));
		lblLogin.setBounds(21, 371, 76, 14);
		panel.add(lblLogin);

		txtPass = new JTextField();
		txtPass.setEditable(false);
		txtPass.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtPass.setBounds(107, 390, 198, 20);
		panel.add(txtPass);
		txtPass.setColumns(10);

		JLabel lblPass = new JLabel("Contraseña");
		lblPass.setFont(new Font("Cambria", Font.BOLD, 12));
		lblPass.setBounds(21, 393, 76, 14);
		panel.add(lblPass);

		this.setVisible(true);
	}

	public JButton getBtnGuardarEdicion() {
		return btnGuardarEdicion;
	}

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaClientes());
		}
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

	public JButton getBtnPermisosXrol() {
		return btnPermisosXrol;
	}

	public void setBtnPermisosXrol(JButton btnPermisosXrol) {
		this.btnPermisosXrol = btnPermisosXrol;
	}

	public JTextField getTxtPass() {
		return txtPass;
	}

	public void setTxtPass(JTextField txtPass) {
		this.txtPass = txtPass;
	}

	public DefaultTableModel getModelUsuarios() {
		return modelUsuarios;
	}

	public JTable getTablaUsuarios() {
		return tablaUsuarios;
	}

	public String[] getNombreColumnasUsuarios() {
		return nombreColumnasUsuarios;
	}

	public JButton getBtnAgregarUsuario() {
		return btnAgregarUsuario;
	}

	public JButton getBtnEditarUsuario() {
		return btnEditarUsuario;
	}

	public JButton getBtnEliminarUsuario() {
		return btnEliminarUsuario;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JTextField getTxtNombreUsuario() {
		return txtNombreUsuario;
	}

	public JTextField getTxtTelefonoUsuario() {
		return txtTelefonoUsuario;
	}

	public JTextField getTxtApellidoUsuario() {
		return txtApellidoUsuario;
	}

	public JTextField getTxtEmailUsuario() {
		return txtEmailUsuario;
	}

	public JTextField getTxtDNI() {
		return txtDNI;
	}

	public JComboBox getComboRoles() {
		return comboRoles;
	}

	public void getErrorMsj(String msj) {
		JOptionPane.showMessageDialog(null, msj);
	}

	public JTextField getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(JTextField txtLogin) {
		this.txtLogin = txtLogin;
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
}
