package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import VistaPropias.CellRenderer;
import VistaPropias.CellRendererTablaClientes;
import VistaPropias.CellRendererTablaListado;
import presentacion.controlador.ControladorCliente;

import java.sql.Date;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.font.TextAttribute;
import java.awt.geom.Arc2D.Double;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class VentanaClientes extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaClientes;
	private JTable tablaClientes_1;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnVisualizarSucursales;
	private JButton btnGenerarSucursales;
	private JButton btnEditar;
	private DefaultTableModel modelClientes;
	private String[] nombreColumnas = { "Nombre", "CUIT" };
	// private String[] nombreColumnas = {"Nombre","CUIT",
	// "Direcci�n","Contacto", "Tel. Cont�cto", "Correo"};
	private JPanel panel;
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
	private JLabel lblTelEmpresa;
	private JTextField txtTelContacto;
	private JLabel lblTelContacto;
	private JTextField txtCorreo;
	private JLabel lblCorreo;
	private JPanel panel_1;
	private JLabel lblDetalle;
	private JLabel lblSucursales;
	private JTextField txtTelEmpr;

	public VentanaClientes(ControladorCliente controladorCliente) {
		super();
		setResizable(false);
		this.controladorCliente = controladorCliente;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 647, 524);
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
		panel.setBounds(0, 0, 631, 530);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JScrollPane spClientes = new JScrollPane();
		spClientes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spClientes.setBounds(51, 74, 400, 184);
		panel.add(spClientes);

		modelClientes = new DefaultTableModel(null, nombreColumnas);
		tablaClientes = new JTable(modelClientes);

		modelClientes = new DefaultTableModel(new Object[][] {}, new String[] { "NOMBRE", "CUIT"

		}) {
			Class[] columnTypes = new Class[] { String.class, String.class

			};

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

		btnAgregar = new JButton("<html><center>Agregar Cliente</html>");
		btnAgregar.setBackground(new Color(0, 255, 127));
		btnAgregar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregar.setBounds(475, 74, 98, 40);
		panel.add(btnAgregar);

		btnEditar = new JButton("<html><center>Editar Cliente</html>");
		btnEditar.setBackground(new Color(255, 255, 153));
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEditar.setBounds(475, 121, 98, 40);
		panel.add(btnEditar);

		btnBorrar = new JButton("<html><center>Eliminar Cliente</html>");
		btnBorrar.setBackground(new Color(255, 51, 0));
		btnBorrar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBorrar.setBounds(475, 167, 98, 40);
		panel.add(btnBorrar);

		btnVisualizarSucursales = new JButton("<html><center>Visualizar Sucursales</html>");
		btnVisualizarSucursales.setVisible(false);
		btnVisualizarSucursales.setBackground(Color.WHITE);
		btnVisualizarSucursales.setFont(new Font("Cambria", Font.BOLD, 14));
		btnVisualizarSucursales.setBounds(475, 370, 98, 39);
		panel.add(btnVisualizarSucursales);

		btnGenerarSucursales = new JButton("<html><center>Generar Sucursal</html>");
		btnGenerarSucursales.setBackground(Color.WHITE);
		btnGenerarSucursales.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGenerarSucursales.setBounds(475, 218, 98, 40);
		panel.add(btnGenerarSucursales);

		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(51, 290, 400, 184);
		panel.add(panel_1);
		panel_1.setLayout(null);

		lblNombreCliente = new JLabel("Nombre : ");
		lblNombreCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNombreCliente.setBounds(6, 16, 84, 20);
		panel_1.add(lblNombreCliente);

		txtNombreCliente = new JTextField();
		txtNombreCliente.setEditable(false);
		txtNombreCliente.setBackground(SystemColor.activeCaption);
		txtNombreCliente.setFont(new Font("Cambria", Font.BOLD, 12));
		txtNombreCliente.setBounds(100, 16, 281, 20);
		panel_1.add(txtNombreCliente);
		txtNombreCliente.setColumns(10);

		lblCUIT = new JLabel("CUIT : ");
		lblCUIT.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCUIT.setBounds(6, 38, 84, 20);
		panel_1.add(lblCUIT);

		txtCUIT = new JTextField();
		txtCUIT.setEditable(false);
		txtCUIT.setBackground(SystemColor.activeCaption);
		txtCUIT.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtCUIT.setBounds(100, 38, 281, 20);
		panel_1.add(txtCUIT);
		txtCUIT.setColumns(10);

		lblDiereccion = new JLabel("Dirección : ");
		lblDiereccion.setFont(new Font("Cambria", Font.BOLD, 12));
		lblDiereccion.setBounds(6, 60, 84, 20);
		panel_1.add(lblDiereccion);

		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBackground(SystemColor.activeCaption);
		txtDireccion.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDireccion.setBounds(100, 60, 281, 20);
		panel_1.add(txtDireccion);
		txtDireccion.setColumns(10);

		lblContacto = new JLabel("Contacto : ");
		lblContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblContacto.setBounds(6, 82, 84, 20);
		panel_1.add(lblContacto);

		txtContacto = new JTextField();
		txtContacto.setEditable(false);
		txtContacto.setBackground(SystemColor.activeCaption);
		txtContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtContacto.setBounds(100, 82, 281, 20);
		panel_1.add(txtContacto);
		txtContacto.setColumns(10);

		lblTelContacto = new JLabel("Tel. Contacto : ");
		lblTelContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTelContacto.setBounds(6, 104, 84, 20);
		panel_1.add(lblTelContacto);

		txtTelContacto = new JTextField();
		txtTelContacto.setEditable(false);
		txtTelContacto.setBackground(SystemColor.activeCaption);
		txtTelContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelContacto.setBounds(100, 104, 281, 20);
		panel_1.add(txtTelContacto);
		txtTelContacto.setColumns(10);

		lblCorreo = new JLabel("Correo");
		lblCorreo.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCorreo.setBounds(6, 125, 84, 20);
		panel_1.add(lblCorreo);

		txtCorreo = new JTextField();
		txtCorreo.setEditable(false);
		txtCorreo.setBackground(SystemColor.activeCaption);
		txtCorreo.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtCorreo.setBounds(100, 126, 281, 20);
		panel_1.add(txtCorreo);
		txtCorreo.setColumns(10);

		txtTelEmpresa = new JTextField();
		txtTelEmpresa.setEditable(false);
		txtTelEmpresa.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelEmpresa.setColumns(10);
		txtTelEmpresa.setBackground(SystemColor.activeCaption);
		txtTelEmpresa.setBounds(100, 148, 281, 20);
		panel_1.add(txtTelEmpresa);

		JLabel lblTelEmpresa = new JLabel("Tel. Empresa : ");
		lblTelEmpresa.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTelEmpresa.setBounds(6, 147, 84, 20);
		panel_1.add(lblTelEmpresa);

		JLabel lblClientes = new JLabel("CLIENTES ");
		lblClientes.setFont(new Font("Cambria", Font.BOLD, 22));
		lblClientes.setBounds(10, 11, 117, 31);
		panel.add(lblClientes);

		JLabel lblListadoDeClientes = new JLabel("Listado de Clientes Ingresados");
		lblListadoDeClientes.setFont(new Font("Cambria", Font.BOLD, 14));
		lblListadoDeClientes.setBounds(51, 50, 204, 23);

		panel.add(lblListadoDeClientes);

		lblDetalle = new JLabel("Detalle");
		lblDetalle.setFont(new Font("Cambria", Font.BOLD, 14));
		lblDetalle.setBounds(51, 267, 204, 23);
		panel.add(lblDetalle);

		lblSucursales = new JLabel("<html>Este cliente posee Sucursales<html>");
		lblSucursales.setVisible(false);
		lblSucursales.setFont(new Font("Cambria", Font.BOLD, 12));
		lblSucursales.setBounds(475, 331, 110, 39);
		panel.add(lblSucursales);

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

	public JTextField getTxtCorreo() {
		return txtCorreo;
	}

	public void setTxtCorreo(JTextField txtCorreo) {
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
}
