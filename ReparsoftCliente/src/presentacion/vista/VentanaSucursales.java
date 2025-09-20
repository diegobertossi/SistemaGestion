 package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import VistaPropias.CellRendererTablaClientes;
import presentacion.controlador.ControladorCliente;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.util.Enumeration;
import java.awt.Font;
import java.awt.Image;

import javax.swing.border.EtchedBorder;
import java.awt.Cursor;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class VentanaSucursales extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private JTable tablaSucursal;
	private JTable tablaSucursales;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnEditar;
	
	private JButton btnGuardarSucursal;
	private JButton btnCancelarSucursal;
	
	private DefaultTableModel modelSucursales;
	private  String[] nombreColumnas = {"NOMBRE"};
	@SuppressWarnings("unused")
	private JPanel panel;
	@SuppressWarnings("unused")
	private ControladorCliente controladorSucursal;
	
	private JTextField txtNombreSucursal;
	private JLabel lblNombreSucursal;
	private JTextField txtDireccion;
	private JLabel lblDiereccion;
	private JTextField txtContacto;
	private JLabel lblContacto;
	private JTextField txtTelContacto;
	private JLabel lblTelContacto;
	private JTextArea txtCorreo;
	private JLabel lblCorreo;
	private JPanel panel_1;
	private JTextField textCliente;
	private JPanel panel_2;
	private JButton btnAgregarCorreo;
	private JButton btnQuitarCorreo;
	

	@SuppressWarnings({ "serial", "unused" })
	public VentanaSucursales(ControladorCliente controladorSucursal) 
	{
		super();
		setResizable(false);
        setTitle("Sucursales");
        this.controladorSucursal = controladorSucursal;
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		setBounds(100, 100, 560, 507);
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
		panel.setBounds(0, 0, 554, 468);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		
		JScrollPane spSucursal = new JScrollPane();
		spSucursal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spSucursal.setBounds(20, 75, 307, 170);
		panel.add(spSucursal);
		
		modelSucursales = new DefaultTableModel(null,nombreColumnas);
		tablaSucursal = new JTable(modelSucursales);
		
				
		modelSucursales = new DefaultTableModel(new Object[][]{
		},			
		new String[] {
				"NOMBRE"
			}
	) 
		{
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {
				String.class
			//String.class,String.class,String.class,String.class,String.class,String.class 
		};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}
		boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
	};

		
	
	tablaSucursales = new JTable(modelSucursales);
	tablaSucursales.setFont(new Font("Cambria", Font.PLAIN, 12));
	tablaSucursales.getTableHeader().setReorderingAllowed(false);

	
	//Seteo para los anchos de las columnas
	tablaSucursales.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	tablaSucursales.getColumnModel().getColumn(0).setPreferredWidth(250);

	    
	tablaSucursales.setAutoCreateColumnsFromModel(false);
		spSucursal.setViewportView(tablaSucursales);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(119, 136, 153));
		panel_2.setBounds(386, 75, 153, 170);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		btnAgregar = new JButton("AGREGAR");
		btnAgregar.setBounds(22, 24, 108, 23);
		panel_2.add(btnAgregar);
		btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar.setBackground(new Color(240, 240, 240));
		btnAgregar.setFont(new Font("Cambria", Font.BOLD, 14));
		
		btnEditar = new JButton("EDITAR");
		btnEditar.setBounds(22, 71, 108, 23);
		panel_2.add(btnEditar);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBackground(new Color(240, 240, 240));
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 14));
		
		btnBorrar = new JButton("ELIMINAR");
		btnBorrar.setBounds(22, 117, 108, 23);
		panel_2.add(btnBorrar);
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrar.setBackground(new Color(240, 240, 240));
		btnBorrar.setFont(new Font("Cambria", Font.BOLD, 14));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(119, 136, 153));
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(20, 256, 519, 201);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		lblNombreSucursal = new JLabel("NOMBRE:");
		lblNombreSucursal.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNombreSucursal.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNombreSucursal.setBounds(6, 17, 97, 20);
		panel_1.add(lblNombreSucursal);
				
		txtNombreSucursal = new JTextField();
		txtNombreSucursal.setEditable(false);
		txtNombreSucursal.setBackground(new Color(255, 255, 255));
		txtNombreSucursal.setFont(new Font("Cambria", Font.BOLD, 12));
		txtNombreSucursal.setBounds(113, 17, 267, 20);
		panel_1.add(txtNombreSucursal);
		txtNombreSucursal.setColumns(10);

		lblDiereccion = new JLabel("DIRECCIÓN:");
		lblDiereccion.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblDiereccion.setFont(new Font("Cambria", Font.BOLD, 12));
		lblDiereccion.setBounds(6, 39, 97, 20);
		panel_1.add(lblDiereccion);
				
		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBackground(new Color(255, 255, 255));
		txtDireccion.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDireccion.setBounds(113, 39, 267, 20);
		panel_1.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		lblContacto = new JLabel("CONTACTO:");
		lblContacto.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblContacto.setBounds(6, 61, 97, 20);
		panel_1.add(lblContacto);
				
		txtContacto = new JTextField();
		txtContacto.setEditable(false);
		txtContacto.setBackground(new Color(255, 255, 255));
		txtContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtContacto.setBounds(113, 61, 267, 20);
		panel_1.add(txtContacto);
		txtContacto.setColumns(10);
		
		lblTelContacto = new JLabel("TEL. CONTACTO:");
		lblTelContacto.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblTelContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTelContacto.setBounds(6, 83, 97, 20);
		panel_1.add(lblTelContacto);
				
		txtTelContacto = new JTextField();
		txtTelContacto.setEditable(false);
		txtTelContacto.setBackground(new Color(255, 255, 255));
		txtTelContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelContacto.setBounds(113, 83, 267, 20);
		panel_1.add(txtTelContacto);
		txtTelContacto.setColumns(10);
		
		lblCorreo = new JLabel("CORREO:");
		lblCorreo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblCorreo.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCorreo.setBounds(6, 130, 97, 20);
		panel_1.add(lblCorreo);
		
		
		txtCorreo = new JTextArea();
		txtCorreo.setEditable(false);
		txtCorreo.setBackground(new Color(240, 240, 240));
		txtCorreo.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtCorreo.setBounds(0, 80, 281, 70);
		panel_1.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JScrollPane scrollCorreo = new JScrollPane(txtCorreo);
		scrollCorreo.setBounds(113, 105, 267, 70);
		panel_1.add(scrollCorreo);

		
		btnGuardarSucursal = new JButton("GUARDAR");
		btnGuardarSucursal.setVisible(false);
		btnGuardarSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGuardarSucursal.setBackground(UIManager.getColor("Button.background"));
		btnGuardarSucursal.setBounds(401, 39, 108, 23);
		panel_1.add(btnGuardarSucursal);
		
		btnCancelarSucursal = new JButton("CANCELAR");
		btnCancelarSucursal.setVisible(false);
		btnCancelarSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelarSucursal.setBackground(UIManager.getColor("Button.background"));
		btnCancelarSucursal.setBounds(401, 68, 108, 23);
		panel_1.add(btnCancelarSucursal);
		
		btnAgregarCorreo = new JButton(" ");
		btnAgregarCorreo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAgregarCorreo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregarCorreo.setIcon(new ImageIcon(this.getClass().getResource("/mas.png")));
		btnAgregarCorreo.setEnabled(false);
		btnAgregarCorreo.setBackground(UIManager.getColor("Button.background"));
		btnAgregarCorreo.setBounds(381, 110, 35, 23);
		panel_1.add(btnAgregarCorreo);
		
		btnQuitarCorreo = new JButton(" ");
		btnQuitarCorreo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnQuitarCorreo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnQuitarCorreo.setIcon(new ImageIcon(this.getClass().getResource("/menos.png")));
		btnQuitarCorreo.setEnabled(false);
		btnQuitarCorreo.setBackground(UIManager.getColor("Button.background"));
		btnQuitarCorreo.setBounds(381, 144, 35, 23);
		panel_1.add(btnQuitarCorreo);
		
		
		
		JLabel lblSucursales = new JLabel("SUCURSALES DE : ");
		lblSucursales.setFont(new Font("Cambria", Font.BOLD, 22));
		lblSucursales.setBounds(22, 22, 187, 31);
		panel.add(lblSucursales);
		
		textCliente = new JTextField();
		textCliente.setEditable(false);
		textCliente.setBorder(null);
		textCliente.setBackground(SystemColor.inactiveCaption);
		textCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		textCliente.setBounds(209, 23, 254, 29);
		panel.add(textCliente);
		textCliente.setColumns(10);

		
		this.setVisible(true);
	}
	

	public JTextField getTextCliente() {
		return textCliente;
	}

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaClientes());
		}
	}


	public void setTextCliente(JTextField textCliente) {
		this.textCliente = textCliente;
	}



	public JButton getBtnEditar() {
		return btnEditar;
	}


	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}



	public JTextField getTxtNombreSucursal() {
		return txtNombreSucursal;
	}


	public void setTxtNombreSucursal(JTextField txtNombreSucursal) {
		this.txtNombreSucursal = txtNombreSucursal;
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


	public JButton getBtnAgregar() 
	{
		return btnAgregar;
	}

	public JButton getBtnBorrar() 
	{
		return btnBorrar;
	}
	
	public DefaultTableModel getModelSucursales() 
	{
		return modelSucursales;
	}
	
	public JTable getTablaSucursales()
	{
		return tablaSucursales;
	}

	public String[] getNombreColumnas() 
	{
		return nombreColumnas;
	}


	public JButton getBtnGuardarSucursal() {
		return btnGuardarSucursal;
	}


	public void setBtnGuardarSucursal(JButton btnGuardarSucursal) {
		this.btnGuardarSucursal = btnGuardarSucursal;
	}


	public JButton getBtnCancelarSucursal() {
		return btnCancelarSucursal;
	}


	public void setBtnCancelarSucursal(JButton btnCancelarSucursal) {
		this.btnCancelarSucursal = btnCancelarSucursal;
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


	public void setBtnQuitarCorreo(JButton btnQuitarCorreo) {
		this.btnQuitarCorreo = btnQuitarCorreo;
	}
}
