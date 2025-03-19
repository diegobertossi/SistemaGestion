package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import presentacion.controlador.ControladorCliente;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Color;

public class VentanaAgregarSucursal extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtEmail;
	private JTextField txtTelefonoContacto;
	private ControladorCliente controlador;
	private JButton btnAgregarSucursal;
	private JButton btnCancelar;
	private JLabel lblContacto;
	private JTextField txtContacto;
	private JLabel lblNuevoCliente;
	private JTextField txtCliente;
	
	
	
	protected void this_windowOpened(WindowEvent e) {
		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}
	
	public VentanaAgregarSucursal(ControladorCliente controlador) 
	{
		super();
		this.controlador = controlador;
		this.this_windowOpened(null);
		setResizable(false);
		setSize(544, 259);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 528, 220);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre : ");
		lblNombre.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNombre.setBounds(22, 59, 113, 14);
		panel.add(lblNombre);
		
		JLabel lblDireccion = new JLabel("Dierección : ");
		lblDireccion.setFont(new Font("Cambria", Font.BOLD, 14));
		lblDireccion.setBounds(22, 84, 113, 14);
		panel.add(lblDireccion);
		
		
		txtNombre = new JTextField();
		txtNombre.setBackground(new Color(220, 220, 220));
		txtNombre.setFont(new Font("Cambria", Font.BOLD, 14));
		txtNombre.setBounds(162, 56, 340, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		//soloLetras(txtNombre);
		
		txtDireccion = new JTextField();
		txtDireccion.setBackground(new Color(220, 220, 220));
		txtDireccion.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtDireccion.setBounds(162, 80, 340, 20);
		panel.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		btnAgregarSucursal = new JButton("AGREGAR");
		btnAgregarSucursal.setBackground(new Color(152, 251, 152));
		btnAgregarSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregarSucursal.addActionListener(this.controlador);
		btnAgregarSucursal.setBounds(280, 184, 106, 23);
			
		panel.add(btnAgregarSucursal);		
		
		txtEmail = new JTextField();
		txtEmail.setBackground(new Color(220, 220, 220));
		txtEmail.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtEmail.setBounds(162, 153, 340, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);		
		
		JLabel lbleMail = new JLabel("Correo : ");
		lbleMail.setFont(new Font("Cambria", Font.BOLD, 14));
		lbleMail.setBounds(22, 156, 63, 14);
		panel.add(lbleMail);
		
		JLabel lblTelefonoContacto = new JLabel("Teléfono Contacto : ");
		lblTelefonoContacto.setFont(new Font("Cambria", Font.BOLD, 14));
		lblTelefonoContacto.setBounds(22, 131, 130, 14);
		panel.add(lblTelefonoContacto);
		
		txtTelefonoContacto = new JTextField();
		txtTelefonoContacto.setBackground(new Color(220, 220, 220));
		txtTelefonoContacto.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtTelefonoContacto.setColumns(10);
		txtTelefonoContacto.setBounds(162, 128, 340, 20);
		panel.add(txtTelefonoContacto);
		soloNumeros(txtTelefonoContacto);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(140, 232, 113, -8);
		panel.add(horizontalStrut);
		
		lblContacto = new JLabel("Contacto: ");
		lblContacto.setFont(new Font("Cambria", Font.BOLD, 14));
		lblContacto.setBounds(22, 107, 113, 14);
		panel.add(lblContacto);
		
		txtContacto = new JTextField();
		txtContacto.setBackground(new Color(220, 220, 220));
		txtContacto.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtContacto.setColumns(10);
		txtContacto.setBounds(162, 104, 340, 20);
		panel.add(txtContacto);
		
		lblNuevoCliente = new JLabel("NUEVA SUCURSAL DE");
		lblNuevoCliente.setFont(new Font("Cambria", Font.BOLD, 18));
		lblNuevoCliente.setBounds(22, 11, 184, 23);
		panel.add(lblNuevoCliente);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(255, 0, 0));
		btnCancelar.setBounds(396, 185, 106, 23);
		panel.add(btnCancelar);
		
		txtCliente = new JTextField();
		txtCliente.setBorder(null);
		txtCliente.setOpaque(false);
		txtCliente.setEditable(false);
		txtCliente.setFont(new Font("Cambria", Font.BOLD, 18));
		txtCliente.setColumns(10);
		txtCliente.setBackground(SystemColor.inactiveCaptionBorder);
		txtCliente.setBounds(204, 10, 291, 24);
		panel.add(txtCliente);

	
		this.setVisible(true);
	}

	
	public JTextField getTxtCliente() {
		return txtCliente;
	}


	public void setTxtCliente(JTextField txtCliente) {
		this.txtCliente = txtCliente;
	}


	public void soloLetras(JTextField campo){
		campo.addKeyListener(new KeyAdapter() {
			 public void keyTyped(KeyEvent e){
				 char c = e.getKeyChar();
				 if(Character.isDigit(c)){
					 getToolkit().beep();
					 JOptionPane.showMessageDialog(null, "Solo se admiten letras");
					 e.consume();
				 }
			 }
		});
	}
	
	public void soloNumeros(JTextField campo){
		campo.addKeyListener(new KeyAdapter() {
			 public void keyTyped(KeyEvent e){
				 char c = e.getKeyChar();
				 if(Character.isLetter(c)){
					 getToolkit().beep();
					 JOptionPane.showMessageDialog(null, "Solo se admiten números");
					 e.consume();
				 }
			 }
		});
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}


	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}


	public JTextField getTxtDireccion() {
		return txtDireccion;
	}


	public void setTxtDireccion(JTextField txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}


	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}


	public JTextField getTxtTelefonoContacto() {
		return txtTelefonoContacto;
	}


	public void setTxtTelefonoContacto(JTextField txtTelefonoContacto) {
		this.txtTelefonoContacto = txtTelefonoContacto;
	}


	public JButton getBtnAgregarSucursal() {
		return btnAgregarSucursal;
	}


	public void setBtnAgregarSucursal(JButton btnAgregarCliente) {
		this.btnAgregarSucursal = btnAgregarCliente;
	}


	public JButton getBtnCancelar() {
		return btnCancelar;
	}


	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}


	public JTextField getTxtContacto() {
		return txtContacto;
	}


	public void setTxtContacto(JTextField txtContacto) {
		this.txtContacto = txtContacto;
	}
}	