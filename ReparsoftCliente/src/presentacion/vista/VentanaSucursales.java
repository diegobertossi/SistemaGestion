 package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import presentacion.controlador.ControladorCliente;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.border.EtchedBorder;
import java.awt.Cursor;
import javax.swing.UIManager;

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
	private  String[] nombreColumnas = {"Nombre"};
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
	private JTextField txtCorreo;
	private JLabel lblCorreo;
	private JPanel panel_1;
	private JTextField textCliente;
	private JPanel panel_2;
	

	@SuppressWarnings({ "serial", "unused" })
	public VentanaSucursales(ControladorCliente controladorSucursal) 
	{
		super();
		setResizable(false);
        setTitle("Sucursales");
        this.controladorSucursal = controladorSucursal;
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		setBounds(100, 100, 565, 483);
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
		panel.setBounds(0, 0, 549, 450);
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
				"Nombre"
				
				//"Nombre", "CUIT", "Direcci�n","Contacto", "Tel. Cont�cto", "Correo"
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
		panel_2.setBounds(337, 75, 202, 170);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		btnAgregar = new JButton("AGREGAR");
		btnAgregar.setBounds(52, 24, 108, 23);
		panel_2.add(btnAgregar);
		btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar.setBackground(new Color(240, 240, 240));
		btnAgregar.setFont(new Font("Cambria", Font.BOLD, 14));
		
		btnEditar = new JButton("EDITAR");
		btnEditar.setBounds(52, 71, 108, 23);
		panel_2.add(btnEditar);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBackground(new Color(240, 240, 240));
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 14));
		
		btnBorrar = new JButton("ELIMINAR");
		btnBorrar.setBounds(52, 117, 108, 23);
		panel_2.add(btnBorrar);
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrar.setBackground(new Color(240, 240, 240));
		btnBorrar.setFont(new Font("Cambria", Font.BOLD, 14));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(119, 136, 153));
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(20, 256, 519, 170);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		lblNombreSucursal = new JLabel("Nombre : ");
		lblNombreSucursal.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNombreSucursal.setBounds(6, 17, 84, 20);
		panel_1.add(lblNombreSucursal);
				
		txtNombreSucursal = new JTextField();
		txtNombreSucursal.setEditable(false);
		txtNombreSucursal.setBackground(new Color(255, 255, 255));
		txtNombreSucursal.setFont(new Font("Cambria", Font.BOLD, 12));
		txtNombreSucursal.setBounds(100, 17, 281, 20);
		panel_1.add(txtNombreSucursal);
		txtNombreSucursal.setColumns(10);

		lblDiereccion = new JLabel("Dirección : ");
		lblDiereccion.setFont(new Font("Cambria", Font.BOLD, 12));
		lblDiereccion.setBounds(6, 39, 84, 20);
		panel_1.add(lblDiereccion);
				
		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBackground(new Color(255, 255, 255));
		txtDireccion.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDireccion.setBounds(100, 39, 281, 20);
		panel_1.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		lblContacto = new JLabel("Contacto : ");
		lblContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblContacto.setBounds(6, 61, 84, 20);
		panel_1.add(lblContacto);
				
		txtContacto = new JTextField();
		txtContacto.setEditable(false);
		txtContacto.setBackground(new Color(255, 255, 255));
		txtContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtContacto.setBounds(100, 61, 281, 20);
		panel_1.add(txtContacto);
		txtContacto.setColumns(10);
		
		lblTelContacto = new JLabel("Tel. Contacto : ");
		lblTelContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		lblTelContacto.setBounds(6, 83, 84, 20);
		panel_1.add(lblTelContacto);
				
		txtTelContacto = new JTextField();
		txtTelContacto.setEditable(false);
		txtTelContacto.setBackground(new Color(255, 255, 255));
		txtTelContacto.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtTelContacto.setBounds(100, 83, 281, 20);
		panel_1.add(txtTelContacto);
		txtTelContacto.setColumns(10);
		
		lblCorreo = new JLabel("Correo");
		lblCorreo.setFont(new Font("Cambria", Font.BOLD, 12));
		lblCorreo.setBounds(6, 104, 84, 20);
		panel_1.add(lblCorreo);
				
		txtCorreo = new JTextField();
		txtCorreo.setEditable(false);
		txtCorreo.setBackground(new Color(255, 255, 255));
		txtCorreo.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtCorreo.setBounds(100, 105, 281, 50);
		panel_1.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		btnGuardarSucursal = new JButton("GUARDAR");
		btnGuardarSucursal.setVisible(false);
		btnGuardarSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGuardarSucursal.setBackground(UIManager.getColor("Button.background"));
		btnGuardarSucursal.setBounds(401, 103, 108, 23);
		panel_1.add(btnGuardarSucursal);
		
		btnCancelarSucursal = new JButton("CANCELAR");
		btnCancelarSucursal.setVisible(false);
		btnCancelarSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelarSucursal.setBackground(UIManager.getColor("Button.background"));
		btnCancelarSucursal.setBounds(401, 132, 108, 23);
		panel_1.add(btnCancelarSucursal);
		
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


	public JTextField getTxtCorreo() {
		return txtCorreo;
	}


	public void setTxtCorreo(JTextField txtCorreo) {
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
}
