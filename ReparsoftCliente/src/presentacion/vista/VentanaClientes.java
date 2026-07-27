package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import VistaPropias.CellRendererTablaClientes;
import presentacion.controlador.ControladorCliente;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

public class VentanaClientes extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
//  private JTable tablaClientes;
	private JTable tablaClientes_1;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnVisualizarSucursales;
	private JButton btnGenerarSucursales;
	private JButton btnEditar;

	private JButton btnCancelar;
	private JButton btnGuardar;
	private JButton btnAgregarCorreo;
	private JButton btnQuitarCorreo;

	private DefaultTableModel modelClientes;
	private String[] nombreColumnas = { "Nombre", "CUIT" };

	@SuppressWarnings("unused")
	private JPanel panel;
	@SuppressWarnings("unused")
	private ControladorCliente controladorCliente;

	private JTextField txtNombreCliente;
	private JLabel lblNombreCliente;
	private JTextField txtCUIT;
	private JLabel lblCUIT;
	private JTextField txtDireccion;
	private JLabel lblDiereccion;
	private JTextField txtContacto;
	private JLabel lblContacto;
	private JTextField txtTelEmpresa;
	@SuppressWarnings("unused")
	private JLabel lblTelEmpresa;
	private JTextField txtTelContacto;
	private JLabel lblTelContacto;
	private JTextArea txtCorreo;
	private JLabel lblCorreo;
	private JComboBox<String> cmbTipoDocumento;
	private JComboBox<String> cmbCondicionIva;
	private JRadioButton rdParticular;
	private JRadioButton rdEmpresa;
	private ButtonGroup groupTipoPersona;
	private JPanel panel_1;
	private JLabel lblSucursales;
	@SuppressWarnings("unused")
	private JTextField txtTelEmpr;
	private JPanel panel_2;
	private JButton btnAgregar_1;

	@SuppressWarnings("unused")
	public VentanaClientes(ControladorCliente controladorCliente) {
		super();
		setResizable(false);
		
		this.controladorCliente = controladorCliente;
		setTitle("Clientes");
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);


		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 668, 570);
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
		panel.setBounds(0, 0, 652, 531);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JScrollPane spClientes = new JScrollPane();
		spClientes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spClientes.setBounds(20, 52, 379, 198);
		panel.add(spClientes);

		modelClientes = new DefaultTableModel(null, nombreColumnas);
		// tablaClientes = new JTable(modelClientes);

		modelClientes = new DefaultTableModel(new Object[][] {}, new String[] { "NOMBRE", "CUIT"

		}) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class

			};

			@SuppressWarnings({ "unchecked", "rawtypes" })
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
			// UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
		Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

		tablaClientes_1 = new JTable(modelClientes);

		tablaClientes_1.getTableHeader().setForeground(Color.BLACK);
		tablaClientes_1.getTableHeader().setFont(fuenteCabecera);
		tablaClientes_1.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tablaClientes_1.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tablaClientes_1.setShowGrid(true);

		// Seteo para los anchos de las columnas
		tablaClientes_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tablaClientes_1.getColumnModel().getColumn(0).setPreferredWidth(250);
		tablaClientes_1.getColumnModel().getColumn(1).setPreferredWidth(120);

		tablaClientes_1.setAutoCreateColumnsFromModel(false);
		spClientes.setViewportView(tablaClientes_1);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(119, 136, 153));
		panel_2.setBounds(443, 64, 199, 180);
		panel.add(panel_2);
		panel_2.setLayout(null);

		btnAgregar = new JButton("<html><center>AGREGAR</html>");
		btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar.setBounds(14, 18, 171, 23);
		panel_2.add(btnAgregar);
		btnAgregar.setBackground(new Color(240, 240, 240));
		btnAgregar.setFont(new Font("Cambria", Font.BOLD, 14));

		btnEditar = new JButton("<html><center>EDITAR</html>");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBounds(14, 59, 171, 23);
		panel_2.add(btnEditar);
		btnEditar.setBackground(new Color(240, 240, 240));
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 14));

		btnBorrar = new JButton("<html><center>ELIMINAR</html>");
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrar.setBounds(14, 100, 171, 23);
		panel_2.add(btnBorrar);
		btnBorrar.setBackground(new Color(240, 240, 240));
		btnBorrar.setFont(new Font("Cambria", Font.BOLD, 14));

		btnGenerarSucursales = new JButton("<html><center>GENERAR SUCURSAL</html>");
		btnGenerarSucursales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGenerarSucursales.setBounds(14, 141, 171, 23);
		panel_2.add(btnGenerarSucursales);
		btnGenerarSucursales.setBackground(new Color(240, 240, 240));
		btnGenerarSucursales.setFont(new Font("Cambria", Font.BOLD, 14));

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(119, 136, 153));
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(20, 260, 622, 265);
		panel.add(panel_1);
		panel_1.setLayout(null);

		lblNombreCliente = new JLabel("NOMBRE");
		lblNombreCliente.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblNombreCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNombreCliente.setBounds(6, 54, 84, 20);
		panel_1.add(lblNombreCliente);

		txtNombreCliente = new JTextField();
		txtNombreCliente.setEditable(false);
		txtNombreCliente.setBackground(new Color(240, 240, 240));
		txtNombreCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		txtNombreCliente.setBounds(114, 54, 267, 20);
		panel_1.add(txtNombreCliente);
		txtNombreCliente.setColumns(10);

		lblCUIT = new JLabel("N° DOC.");
		lblCUIT.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblCUIT.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCUIT.setBounds(197, 77, 48, 20);
		panel_1.add(lblCUIT);

		txtCUIT = new JTextField();
		txtCUIT.setEditable(false);
		txtCUIT.setBackground(new Color(240, 240, 240));
		txtCUIT.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtCUIT.setBounds(245, 77, 136, 20);
		panel_1.add(txtCUIT);
		txtCUIT.setColumns(10);

		lblDiereccion = new JLabel("DIRECCIÓN");
		lblDiereccion.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblDiereccion.setFont(new Font("Cambria", Font.BOLD, 12));
		lblDiereccion.setBounds(6, 99, 84, 20);
		panel_1.add(lblDiereccion);

		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBackground(new Color(240, 240, 240));
		txtDireccion.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDireccion.setBounds(114, 99, 267, 20);
		panel_1.add(txtDireccion);
		txtDireccion.setColumns(10);

		lblContacto = new JLabel("CONTACTO");
		lblContacto.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblContacto.setBounds(6, 121, 84, 20);
		panel_1.add(lblContacto);

		txtContacto = new JTextField();
		txtContacto.setEditable(false);
		txtContacto.setBackground(new Color(240, 240, 240));
		txtContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtContacto.setBounds(114, 121, 267, 20);
		panel_1.add(txtContacto);
		txtContacto.setColumns(10);

		lblTelContacto = new JLabel("TEL. CONTACTO");
		lblTelContacto.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblTelContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTelContacto.setBounds(6, 144, 98, 20);
		panel_1.add(lblTelContacto);

		txtTelContacto = new JTextField();
		txtTelContacto.setEditable(false);
		txtTelContacto.setBackground(new Color(240, 240, 240));
		txtTelContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelContacto.setBounds(114, 144, 267, 20);
		panel_1.add(txtTelContacto);
		txtTelContacto.setColumns(10);

		JLabel lblTipoDoc = new JLabel("TIPO DOC.");
		lblTipoDoc.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblTipoDoc.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTipoDoc.setBounds(6, 77, 80, 20);
		panel_1.add(lblTipoDoc);

		cmbTipoDocumento = new JComboBox<>(new String[]{"CUIT", "DNI", "CI", "Pasaporte", "Otro"});
		cmbTipoDocumento.setEditable(false);
		cmbTipoDocumento.setBackground(new Color(240, 240, 240));
		cmbTipoDocumento.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbTipoDocumento.setBounds(114, 77, 61, 20);
		panel_1.add(cmbTipoDocumento);

		JLabel lblCondIva = new JLabel("COND. IVA");
		lblCondIva.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblCondIva.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCondIva.setBounds(6, 31, 80, 20);
		panel_1.add(lblCondIva);

		cmbCondicionIva = new JComboBox<>(new String[]{"IVA Responsable Inscripto", "Responsable Monotributo", "Consumidor Final", "Exento", "No Responsable", "IVA Sujeto No Categorizado"});
		cmbCondicionIva.setEditable(false);
		cmbCondicionIva.setBackground(new Color(240, 240, 240));
		cmbCondicionIva.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbCondicionIva.setBounds(114, 31, 267, 20);
		panel_1.add(cmbCondicionIva);

		JLabel lblTipoPersona = new JLabel("TIPO");
		lblTipoPersona.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblTipoPersona.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTipoPersona.setBounds(6, 9, 80, 20);
		panel_1.add(lblTipoPersona);

		groupTipoPersona = new ButtonGroup();
		rdParticular = new JRadioButton("Particular");
		rdParticular.setFont(new Font("Cambria", Font.BOLD, 12));
		rdParticular.setBackground(new Color(119, 136, 153));
		rdParticular.setBounds(110, 9, 84, 20);
		panel_1.add(rdParticular);
		groupTipoPersona.add(rdParticular);

		rdEmpresa = new JRadioButton("Empresa");
		rdEmpresa.setFont(new Font("Cambria", Font.BOLD, 12));
		rdEmpresa.setBackground(new Color(119, 136, 153));
		rdEmpresa.setBounds(196, 9, 80, 20);
		rdEmpresa.setSelected(true);
		panel_1.add(rdEmpresa);
		groupTipoPersona.add(rdEmpresa);

		lblCorreo = new JLabel("CORREO");
		lblCorreo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblCorreo.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCorreo.setBounds(6, 191, 84, 20);
		panel_1.add(lblCorreo);

		txtCorreo = new JTextArea();
		txtCorreo.setEditable(false);
		txtCorreo.setBackground(new Color(240, 240, 240));
		txtCorreo.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtCorreo.setBounds(114, 138, 267, 50);
		panel_1.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JScrollPane scrollCorreo = new JScrollPane(txtCorreo);
		scrollCorreo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollCorreo.setBounds(114, 166, 267, 70);
		panel_1.add(scrollCorreo);

		txtTelEmpresa = new JTextField();
		txtTelEmpresa.setEditable(false);
		txtTelEmpresa.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelEmpresa.setColumns(10);
		txtTelEmpresa.setBackground(new Color(240, 240, 240));
		txtTelEmpresa.setBounds(114, 238, 267, 20);
		panel_1.add(txtTelEmpresa);

		JLabel lblTelEmpresa = new JLabel("TEL. EMPRESA");
		lblTelEmpresa.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
		lblTelEmpresa.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTelEmpresa.setBounds(6, 238, 84, 20);
		panel_1.add(lblTelEmpresa);

		lblSucursales = new JLabel("<html>ESTE CLIENTE POSEE SUCURSALES<html>");
		lblSucursales.setBounds(409, 8, 203, 27);
		panel_1.add(lblSucursales);
		lblSucursales.setVisible(false);
		lblSucursales.setFont(new Font("Cambria", Font.BOLD, 12));

		btnVisualizarSucursales = new JButton("<html><center>VISUALIZAR SUCURSALES</html>");
		btnVisualizarSucursales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVisualizarSucursales.setBounds(447, 59, 108, 43);
		panel_1.add(btnVisualizarSucursales);
		btnVisualizarSucursales.setVisible(false);
		btnVisualizarSucursales.setBackground(new Color(255, 255, 255));
		btnVisualizarSucursales.setFont(new Font("Cambria", Font.BOLD, 14));

		btnCancelar = new JButton("<html><center>CANCELAR</html>");
		btnCancelar.setVisible(false);
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelar.setBackground(UIManager.getColor("Button.background"));
		btnCancelar.setBounds(491, 185, 108, 43);
		panel_1.add(btnCancelar);

		btnGuardar = new JButton("<html><center>GUARDAR</html>");
		btnGuardar.setVisible(false);
		btnGuardar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGuardar.setBackground(UIManager.getColor("Button.background"));
		btnGuardar.setBounds(491, 129, 108, 43);
		panel_1.add(btnGuardar);
		

		btnAgregarCorreo = new JButton(" ");
		btnAgregarCorreo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAgregarCorreo.setEnabled(false);
		btnAgregarCorreo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregarCorreo.setBackground(UIManager.getColor("Button.background"));
		btnAgregarCorreo.setIcon(new ImageIcon(this.getClass().getResource("/mas.png")));
		btnAgregarCorreo.setBounds(383, 171, 35, 23);
		panel_1.add(btnAgregarCorreo);
		
		btnQuitarCorreo = new JButton(" ");
		btnQuitarCorreo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnQuitarCorreo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnQuitarCorreo.setEnabled(false);
		btnQuitarCorreo.setBackground(UIManager.getColor("Button.background"));
		btnQuitarCorreo.setIcon(new ImageIcon(this.getClass().getResource("/menos.png")));
		btnQuitarCorreo.setBounds(383, 205, 35, 23);
		panel_1.add(btnQuitarCorreo);

		JLabel lblClientes = new JLabel("CLIENTES ");
		lblClientes.setFont(new Font("Cambria", Font.BOLD, 22));
		lblClientes.setBounds(20, 18, 117, 31);
		panel.add(lblClientes);

		this.setVisible(true);
	}

	public JLabel getLblSucursales() {
		return lblSucursales;
	}

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaClientes());
		}
	}

	public void setLblSucursales(JLabel lblSucursales) {
		this.lblSucursales = lblSucursales;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}

	public JButton getBtnVisualizarSucursales() {
		return btnVisualizarSucursales;
	}

	public void setBtnVisualizarSucursales(JButton btnVisualizarSucursales) {
		this.btnVisualizarSucursales = btnVisualizarSucursales;
	}

	public JButton getBtnGenerarSucursales() {
		return btnGenerarSucursales;
	}

	public void setBtnGenerarSucursales(JButton btnGenerarSucursales) {
		this.btnGenerarSucursales = btnGenerarSucursales;
	}

	public JTextField getTxtNombreCliente() {
		return txtNombreCliente;
	}

	public void setTxtNombreCliente(JTextField txtNombreCliente) {
		this.txtNombreCliente = txtNombreCliente;
	}

	public JTextField getTxtCUIT() {
		return txtCUIT;
	}

	public void setTxtCUIT(JTextField txtCUIT) {
		this.txtCUIT = txtCUIT;
	}

	public JTextField getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(JTextField txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public JTextField getTxtContacto() {
		return txtContacto;
	}

	public void setTxtContacto(JTextField txtContacto) {
		this.txtContacto = txtContacto;
	}

	public JTextField getTxtTelEmpresa() {
		return txtTelEmpresa;
	}

	public void setTxtTelEmpresa(JTextField txtTelEmpresa) {
		this.txtTelEmpresa = txtTelEmpresa;
	}

	public JTextField getTxtTelContacto() {
		return txtTelContacto;
	}

	public void setTxtTelContacto(JTextField txtTelContacto) {
		this.txtTelContacto = txtTelContacto;
	}

	public JTextArea getTxtCorreo() {
		return txtCorreo;
	}

	public void setTxtCorreo(JTextArea txtCorreo) {
		this.txtCorreo = txtCorreo;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JButton getBtnBorrar() {
		return btnBorrar;
	}

	public DefaultTableModel getModelClientes() {
		return modelClientes;
	}

	public JTable getTablaClientes() {
		return tablaClientes_1;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JButton getBtnAgregarCorreo() {
		return btnAgregarCorreo;
	}

	public void setBtnAgregarCorreo(JButton btnAgregarCorreo) {
		this.btnAgregarCorreo = btnAgregarCorreo;
	}

	public JButton getBtnQuitarCorreo() {
		return btnQuitarCorreo;
	}

	public JComboBox<String> getCmbTipoDocumento() {
		return cmbTipoDocumento;
	}

	public JComboBox<String> getCmbCondicionIva() {
		return cmbCondicionIva;
	}

	public JRadioButton getRdParticular() {
		return rdParticular;
	}

	public JRadioButton getRdEmpresa() {
		return rdEmpresa;
	}

	public ButtonGroup getGroupTipoPersona() {
		return groupTipoPersona;
	}

	public void setBtnQuitarCorreo(JButton btnQuitarCorreo) {
		this.btnQuitarCorreo = btnQuitarCorreo;
	}
}
