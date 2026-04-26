package presentacion.vista;

import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import VistaPropias.CellRendererTablaClientes;
import VistaPropias.JTextNum;
import presentacion.controlador.ControladorUsuarios;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;

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
	private JButton btnMostrarContraseña;
	private JTextField txtNombreUsuario;
	private JTextNum txtTelefonoUsuario;
	private JTextField txtApellidoUsuario;
	private JTextField txtEmailUsuario;
	private JTextNum txtDNI;
	private JLabel lblDni;
	@SuppressWarnings("rawtypes")
	private JComboBox comboRoles;
	private JTextField txtLogin;
	//private JTextField txtPass;
	
	private JPasswordField txtPass;

	private JButton btnPermisosXrol;

	@SuppressWarnings("unused")
	private ControladorUsuarios controlador;
	private JTextField textRol;
	private JPanel panel_1;

	@SuppressWarnings({ "serial", "rawtypes", "unused" })
	public VentanaRolesUsuarios(ControladorUsuarios controlador) {

		super();
		setResizable(false);
		this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 602, 496);
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
		panel.setBounds(0, 0, 586, 457);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		JScrollPane spUsuarios = new JScrollPane();
		spUsuarios.setBackground(new Color(119, 136, 153));
		spUsuarios.setBorder(new LineBorder(new Color(0, 128, 128)));
		spUsuarios.setFont(new Font("Cambria", Font.PLAIN, 12));
		spUsuarios.setBounds(21, 67, 322, 170);
		panel.add(spUsuarios);

		// modelUsuarios = new DefaultTableModel(null, nombreColumnasUsuarios);
		modelUsuarios = new DefaultTableModel(new Object[][] {}, new String[] { "USUARIO", "DNI" }) {
			Class[] columnTypes = new Class[] { String.class, int.class };

			@SuppressWarnings({ "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false };

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

		tablaUsuarios = new JTable(modelUsuarios);
		tablaUsuarios.setFont(new Font("Cambria", Font.PLAIN, 11));
		tablaUsuarios.setBackground(new Color(119, 136, 153));
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

		JLabel lblUsuarios = new JLabel("USUARIOS");
		lblUsuarios.setForeground(new Color(0, 0, 0));
		lblUsuarios.setFont(new Font("Cambria", Font.BOLD, 22));
		lblUsuarios.setBounds(21, 20, 115, 26);
		panel.add(lblUsuarios);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(119, 136, 153));
		panel_2.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel_2.setBounds(21, 248, 555, 198);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
				btnGuardarEdicion = new JButton("<html><center>GUARDAR EDICIÓN</html>");
				btnGuardarEdicion.setBounds(401, 83, 108, 43);
				panel_2.add(btnGuardarEdicion);
				btnGuardarEdicion.setVisible(false);
		btnGuardarEdicion.setBackground(new Color(240, 240, 240));
		btnGuardarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));

		btnCancelarEdicion = new JButton("<html><center>CANCELAR EDICIÓN</html>");
		btnCancelarEdicion.setBounds(401, 144, 108, 43);
		panel_2.add(btnCancelarEdicion);
		btnCancelarEdicion.setVisible(false);
		btnCancelarEdicion.setBackground(new Color(240, 240, 240));
		btnCancelarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));

		btnGuardarNuevo = new JButton("<html><p>GUARDAR</p></html>");
		btnGuardarNuevo.setBounds(401, 83, 108, 43);
		panel_2.add(btnGuardarNuevo);
		btnGuardarNuevo.setVisible(false);
		btnGuardarNuevo.setBackground(new Color(240, 240, 240));
		btnGuardarNuevo.setFont(new Font("Cambria", Font.BOLD, 14));

		btnCancelarNuevo = new JButton("<html><p>CANCELAR</p></html>");
		btnCancelarNuevo.setBounds(401, 144, 108, 43);
		panel_2.add(btnCancelarNuevo);
		btnCancelarNuevo.setVisible(false);
		btnCancelarNuevo.setBackground(new Color(240, 240, 240));
		btnCancelarNuevo.setFont(new Font("Cambria", Font.BOLD, 14));

		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(101, 20, 221, 20);
		panel_2.add(txtNombreUsuario);
		txtNombreUsuario.setEditable(false);
		txtNombreUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtNombreUsuario.setColumns(10);

		txtTelefonoUsuario = new JTextNum(14);
		txtTelefonoUsuario.setBounds(101, 83, 221, 20);
		panel_2.add(txtTelefonoUsuario);
		txtTelefonoUsuario.setEditable(false);
		txtTelefonoUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelefonoUsuario.setColumns(10);

		JLabel lblNombre_1 = new JLabel("NOMBRE");
		lblNombre_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblNombre_1.setBounds(15, 20, 76, 20);
		panel_2.add(lblNombre_1);
		lblNombre_1.setFont(new Font("Cambria", Font.BOLD, 12));

		JLabel lblCantidad = new JLabel("TELEFONO");
		lblCantidad.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblCantidad.setBounds(15, 83, 76, 20);
		panel_2.add(lblCantidad);
		lblCantidad.setFont(new Font("Cambria", Font.BOLD, 12));

		JLabel lblApellido = new JLabel("APELLIDO");
		lblApellido.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblApellido.setBounds(15, 41, 76, 20);
		panel_2.add(lblApellido);
		lblApellido.setFont(new Font("Cambria", Font.BOLD, 12));

		txtApellidoUsuario = new JTextField();
		txtApellidoUsuario.setBounds(101, 41, 221, 20);
		panel_2.add(txtApellidoUsuario);
		txtApellidoUsuario.setEditable(false);
		txtApellidoUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtApellidoUsuario.setColumns(10);

		JLabel lblNewLabel = new JLabel("EMAIL");
		lblNewLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblNewLabel.setBounds(15, 104, 76, 20);
		panel_2.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 12));

		txtEmailUsuario = new JTextField();
		txtEmailUsuario.setBounds(101, 104, 221, 20);
		panel_2.add(txtEmailUsuario);
		txtEmailUsuario.setEditable(false);
		txtEmailUsuario.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtEmailUsuario.setColumns(10);

		txtDNI = new JTextNum(8);
		txtDNI.setBounds(101, 62, 221, 20);
		panel_2.add(txtDNI);
		txtDNI.setEditable(false);
		txtDNI.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDNI.setColumns(10);

		lblDni = new JLabel("DNI\r\n");
		lblDni.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblDni.setBounds(15, 62, 76, 20);
		panel_2.add(lblDni);
		lblDni.setFont(new Font("Cambria", Font.BOLD, 12));

		comboRoles = new JComboBox();
		comboRoles.setBounds(101, 167, 221, 20);
		panel_2.add(comboRoles);
		comboRoles.setVisible(false);
		comboRoles.setForeground(new Color(0, 0, 0));
		comboRoles.setEnabled(false);
		comboRoles.setFont(new Font("Cambria", Font.PLAIN, 12));

		txtLogin = new JTextField();
		txtLogin.setBounds(101, 125, 221, 20);
		panel_2.add(txtLogin);
		txtLogin.setEditable(false);
		txtLogin.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtLogin.setColumns(10);

		JLabel lblLogin = new JLabel("USUARIO");
		lblLogin.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblLogin.setBounds(15, 125, 76, 20);
		panel_2.add(lblLogin);
		lblLogin.setFont(new Font("Cambria", Font.BOLD, 12));

		txtPass = new JPasswordField();
		txtPass.setBounds(101, 146, 221, 20);
		panel_2.add(txtPass);
		txtPass.setEditable(false);
		txtPass.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtPass.setColumns(10);

		JLabel lblPass = new JLabel("CONTRASEÑA");
		lblPass.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblPass.setBounds(15, 146, 76, 20);
		panel_2.add(lblPass);
		lblPass.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblRol1 = new JLabel("ROL");
		lblRol1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblRol1.setBounds(15, 167, 76, 20);
		panel_2.add(lblRol1);
		lblRol1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		textRol = new JTextField();
		textRol.setBounds(101, 167, 221, 20);
		panel_2.add(textRol);
		textRol.setHorizontalAlignment(SwingConstants.LEFT);
		textRol.setFont(new Font("Cambria", Font.PLAIN, 12));
		textRol.setEditable(false);
		textRol.setColumns(10);
		
				btnPermisosXrol = new JButton("<html><center>PERMISOS POR ROL<html>");
				btnPermisosXrol.setBounds(401, 20, 108, 43);
				panel_2.add(btnPermisosXrol);
				btnPermisosXrol.setFont(new Font("Cambria", Font.BOLD, 14));
				btnPermisosXrol.setAlignmentX(SwingConstants.CENTER);
				btnPermisosXrol.setAlignmentY(SwingConstants.CENTER);
				
				btnMostrarContraseña = new JButton("");
				btnMostrarContraseña.setBounds(332, 146, 32, 18);
				panel_2.add(btnMostrarContraseña);
				btnMostrarContraseña.setIcon(new ImageIcon(this.getClass().getResource("/mostrar-contraseña-2.png")));
						
						panel_1 = new JPanel();
						panel_1.setBorder(new LineBorder(new Color(0, 128, 128)));
						panel_1.setBackground(new Color(119, 136, 153));
						panel_1.setBounds(374, 67, 202, 170);
						panel.add(panel_1);
						panel_1.setLayout(null);
				
						btnEliminarUsuario = new JButton("ELIMINAR");
						btnEliminarUsuario.setBounds(47, 121, 108, 23);
						panel_1.add(btnEliminarUsuario);
						btnEliminarUsuario.setBackground(new Color(240, 240, 240));
						btnEliminarUsuario.setFont(new Font("Cambria", Font.BOLD, 14));
						
								btnEditarUsuario = new JButton("EDITAR");
								btnEditarUsuario.setBounds(47, 73, 108, 23);
								panel_1.add(btnEditarUsuario);
								btnEditarUsuario.setBackground(new Color(240, 240, 240));
								btnEditarUsuario.setFont(new Font("Cambria", Font.BOLD, 14));
								
										btnAgregarUsuario = new JButton("AGREGAR");
										btnAgregarUsuario.setBounds(47, 25, 108, 23);
										panel_1.add(btnAgregarUsuario);
										btnAgregarUsuario.setBackground(new Color(240, 240, 240));
										btnAgregarUsuario.setFont(new Font("Cambria", Font.BOLD, 14));

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

//	public JTextField getTxtPass() {
//		return txtPass;
//	}
//
//	public void setTxtPass(JTextField txtPass) {
//		this.txtPass = txtPass;
//	}

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

	@SuppressWarnings("rawtypes")
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

	public JTextField getTextRol() {
		return textRol;
	}

	public void setTextRol(JTextField textRol) {
		this.textRol = textRol;
	}

	public JPasswordField getTxtPass() {
		return txtPass;
	}

	public void setTxtPass(JPasswordField txtPass) {
		this.txtPass = txtPass;
	}

	public JButton getBtnMostrarContraseña() {
		return btnMostrarContraseña;
	}

	public void setBtnMostrarContraseña(JButton btnMostrarContraseña) {
		this.btnMostrarContraseña = btnMostrarContraseña;
	}

}
