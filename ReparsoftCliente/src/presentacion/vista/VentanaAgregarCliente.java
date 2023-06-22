package presentacion.vista;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

import VistaPropias.JTextNum;
import presentacion.controlador.ControladorCliente;
import presentacion.controlador.ControladorUsuarios;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;

public class VentanaAgregarCliente extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField txtNombre;
	private JTextField txtCUIT;
	private JTextField txtDireccion;
	private JTextField txtTelefonoEmpresa;
	private JTextField txtEmail;
	private JTextField txtTelefonoContacto;
	private ControladorCliente controlador;
	private JButton btnAgregarCliente;
	private JButton btnCancelar;
	private JLabel lblContacto;
	private JTextField txtContacto;
	private JLabel lblNuevoCliente;
	
	public VentanaAgregarCliente(ControladorCliente controlador) 
	{
		super();
		setResizable(false);
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 544, 326);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 528, 287);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCUIT = new JLabel("CUIT : ");
		lblCUIT.setFont(new Font("Cambria", Font.BOLD, 14));
		lblCUIT.setBounds(22, 81, 113, 14);
		panel.add(lblCUIT);
		
		JLabel lblNombre = new JLabel("Nombre : ");
		lblNombre.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNombre.setBounds(22, 59, 113, 14);
		panel.add(lblNombre);
		
		JLabel lblDireccion = new JLabel("Diereccion : ");
		lblDireccion.setFont(new Font("Cambria", Font.BOLD, 14));
		lblDireccion.setBounds(22, 106, 113, 14);
		panel.add(lblDireccion);
		
		JLabel lblTelefonoEMpresa = new JLabel("Teléfono Empresa : ");
		lblTelefonoEMpresa.setFont(new Font("Cambria", Font.BOLD, 14));
		lblTelefonoEMpresa.setBounds(22, 199, 130, 14);
		panel.add(lblTelefonoEMpresa);
		
		txtCUIT = new JTextField();
		txtCUIT.setBackground(SystemColor.inactiveCaptionBorder);
		txtCUIT.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtCUIT.setBounds(162, 79, 340, 20);
		panel.add(txtCUIT);
		txtCUIT.setColumns(10);
		soloNumeros(txtCUIT);
		
		
		txtNombre = new JTextField();
		txtNombre.setBackground(SystemColor.inactiveCaptionBorder);
		txtNombre.setFont(new Font("Cambria", Font.BOLD, 14));
		txtNombre.setBounds(162, 56, 340, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		//soloLetras(txtNombre);
		
		txtDireccion = new JTextField();
		txtDireccion.setBackground(SystemColor.inactiveCaptionBorder);
		txtDireccion.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtDireccion.setBounds(162, 102, 340, 20);
		panel.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		btnAgregarCliente = new JButton("Agregar");
		btnAgregarCliente.setBackground(new Color(152, 251, 152));
		btnAgregarCliente.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregarCliente.setBounds(314, 239, 89, 23);
			
		panel.add(btnAgregarCliente);		
		
		txtEmail = new JTextField();
		txtEmail.setBackground(SystemColor.inactiveCaptionBorder);
		txtEmail.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtEmail.setBounds(162, 149, 340, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);		
		
		JLabel lbleMail = new JLabel("Correo : ");
		lbleMail.setFont(new Font("Cambria", Font.BOLD, 14));
		lbleMail.setBounds(22, 152, 63, 14);
		panel.add(lbleMail);
		
		JLabel lblTelefonoContacto = new JLabel("Teléfono Contacto : ");
		lblTelefonoContacto.setFont(new Font("Cambria", Font.BOLD, 14));
		lblTelefonoContacto.setBounds(22, 176, 130, 14);
		panel.add(lblTelefonoContacto);
		
		txtTelefonoEmpresa = new JTextField();
		txtTelefonoEmpresa.setBackground(SystemColor.inactiveCaptionBorder);
		txtTelefonoEmpresa.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtTelefonoEmpresa.setColumns(10);
		txtTelefonoEmpresa.setBounds(162, 196, 340, 20);
		panel.add(txtTelefonoEmpresa);
		soloNumeros(txtTelefonoEmpresa);
		
		txtTelefonoContacto = new JTextField();
		txtTelefonoContacto.setBackground(SystemColor.inactiveCaptionBorder);
		txtTelefonoContacto.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtTelefonoContacto.setColumns(10);
		txtTelefonoContacto.setBounds(162, 173, 340, 20);
		panel.add(txtTelefonoContacto);
		soloNumeros(txtTelefonoContacto);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(140, 232, 113, -8);
		panel.add(horizontalStrut);
		
		lblContacto = new JLabel("Contacto: ");
		lblContacto.setFont(new Font("Cambria", Font.BOLD, 14));
		lblContacto.setBounds(22, 128, 113, 14);
		panel.add(lblContacto);
		
		txtContacto = new JTextField();
		txtContacto.setBackground(SystemColor.inactiveCaptionBorder);
		txtContacto.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtContacto.setColumns(10);
		txtContacto.setBounds(162, 125, 340, 20);
		panel.add(txtContacto);
		
		lblNuevoCliente = new JLabel("NUEVO CLIENTE");
		lblNuevoCliente.setFont(new Font("Cambria", Font.BOLD, 18));
		lblNuevoCliente.setBounds(20, 11, 144, 23);
		panel.add(lblNuevoCliente);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(255, 0, 0));
		btnCancelar.setBounds(413, 240, 89, 23);
		panel.add(btnCancelar);

	
		this.setVisible(true);
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


	public JTextField getTxtTelefonoEmpresa() {
		return txtTelefonoEmpresa;
	}


	public void setTxtTelefonoEmpresa(JTextField txtTelefonoEmpresa) {
		this.txtTelefonoEmpresa = txtTelefonoEmpresa;
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


	public JButton getBtnAgregarCliente() {
		return btnAgregarCliente;
	}


	public void setBtnAgregarCliente(JButton btnAgregarCliente) {
		this.btnAgregarCliente = btnAgregarCliente;
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