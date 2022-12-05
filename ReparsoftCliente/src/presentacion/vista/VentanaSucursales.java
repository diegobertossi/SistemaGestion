package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import presentacion.controlador.ControladorCliente;

import java.sql.Date;

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

public class VentanaSucursales extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaSucursal;
	private JTable tablaSucursales;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnEditar;
	private DefaultTableModel modelSucursales;
	private  String[] nombreColumnas = {"Nombre"};
	//private  String[] nombreColumnas = {"Nombre","CUIT", "Dirección","Contacto", "Tel. Contácto", "Correo"};
	private JPanel panel;
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
	private JLabel lblDetalle;
	private JTextField textCliente;
	

	public VentanaSucursales(ControladorCliente controladorSucursal) 
	{
		super();
		setResizable(false);
		this.setDefaultCloseOperation(VentanaSucursales.DO_NOTHING_ON_CLOSE);
		this.controladorSucursal = controladorSucursal;
		setBounds(100, 100, 517, 493);
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
		panel.setBounds(0, 0, 500, 450);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		
		JScrollPane spSucursal = new JScrollPane();
		spSucursal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spSucursal.setBounds(51, 74, 242, 184);
		panel.add(spSucursal);
		
		modelSucursales = new DefaultTableModel(null,nombreColumnas);
		tablaSucursal = new JTable(modelSucursales);
		
				
		modelSucursales = new DefaultTableModel(new Object[][]{
		},			
		new String[] {
				"Nombre"
				
				//"Nombre", "CUIT", "Dirección","Contacto", "Tel. Contácto", "Correo"
		}
	) 
		{
		Class[] columnTypes = new Class[] {
				String.class
			//String.class,String.class,String.class,String.class,String.class,String.class 
		};
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
		
		btnAgregar = new JButton("<html><center>Agregar Sucursal</html>");
		btnAgregar.setBackground(new Color(0, 255, 127));
		btnAgregar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregar.setBounds(362, 74, 89, 40);
		panel.add(btnAgregar);
		
		btnEditar = new JButton("<html><center>Editar Sucursal</html>");
		btnEditar.setBackground(new Color(255, 255, 153));
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEditar.setBounds(362, 121, 89, 40);
		panel.add(btnEditar);
		
		btnBorrar = new JButton("<html><center>Eliminar Sucursal</html>");
		btnBorrar.setBackground(new Color(255, 51, 0));
		btnBorrar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBorrar.setBounds(362, 167, 89, 40);
		panel.add(btnBorrar);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(51, 290, 400, 140);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		lblNombreSucursal = new JLabel("Nombre : ");
		lblNombreSucursal.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNombreSucursal.setBounds(6, 17, 84, 20);
		panel_1.add(lblNombreSucursal);
				
		txtNombreSucursal = new JTextField();
		txtNombreSucursal.setEditable(false);
		txtNombreSucursal.setBackground(SystemColor.activeCaption);
		txtNombreSucursal.setFont(new Font("Cambria", Font.BOLD, 12));
		txtNombreSucursal.setBounds(100, 17, 281, 20);
		panel_1.add(txtNombreSucursal);
		txtNombreSucursal.setColumns(10);

		lblDiereccion = new JLabel("Direcci\u00F3n : ");
		lblDiereccion.setFont(new Font("Cambria", Font.BOLD, 12));
		lblDiereccion.setBounds(6, 39, 84, 20);
		panel_1.add(lblDiereccion);
				
		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBackground(SystemColor.activeCaption);
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
		txtContacto.setBackground(SystemColor.activeCaption);
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
		txtTelContacto.setBackground(SystemColor.activeCaption);
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
		txtCorreo.setBackground(SystemColor.activeCaption);
		txtCorreo.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtCorreo.setBounds(100, 105, 281, 20);
		panel_1.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JLabel lblSucursales = new JLabel("SUCURSALES DE : ");
		lblSucursales.setFont(new Font("Cambria", Font.BOLD, 22));
		lblSucursales.setBounds(10, 8, 187, 31);
		panel.add(lblSucursales);
		
		JLabel lblListadoDeSucursales = new JLabel("Listado de Sucursales Ingresadas");
		lblListadoDeSucursales.setFont(new Font("Cambria", Font.BOLD, 14));
		lblListadoDeSucursales.setBounds(51, 50, 229, 23);
		 
		panel.add(lblListadoDeSucursales);
		
		lblDetalle = new JLabel("Detalle");
		lblDetalle.setFont(new Font("Cambria", Font.BOLD, 14));
		lblDetalle.setBounds(51, 267, 204, 23);
		panel.add(lblDetalle);
		
		textCliente = new JTextField();
		textCliente.setEditable(false);
		textCliente.setBorder(null);
		textCliente.setBackground(SystemColor.inactiveCaption);
		textCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		textCliente.setBounds(197, 8, 254, 29);
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
}
