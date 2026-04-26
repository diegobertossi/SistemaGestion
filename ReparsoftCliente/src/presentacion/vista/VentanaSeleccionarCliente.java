package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorSalidas;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class VentanaSeleccionarCliente extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAceptar;
	private JButton btnCancelar;
	@SuppressWarnings("unused")
	private ControladorSalidas controlador;
	@SuppressWarnings("rawtypes")
	private JComboBox comboCliente;
	@SuppressWarnings("rawtypes")
	private JComboBox comboSucursal;
	

	@SuppressWarnings("rawtypes")
	public VentanaSeleccionarCliente(ControladorSalidas controlador) 
	{
		super();
		this.controlador = controlador;
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 144);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAceptar.setBounds(320, 21, 92, 27);
		contentPane.add(btnAceptar);
		
			
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
			btnCancelar.setBounds(320, 59, 92, 27);
			contentPane.add(btnCancelar);
			
			comboCliente = new JComboBox();
			comboCliente.setFont(new Font("Cambria", Font.PLAIN, 14));
			comboCliente.setBounds(107, 28, 180, 20);
			contentPane.add(comboCliente);
			
			comboSucursal = new JComboBox();
			comboSucursal.setFont(new Font("Cambria", Font.PLAIN, 14));
			comboSucursal.setBounds(107, 59, 180, 20);
			contentPane.add(comboSucursal);
			
			JLabel lblCliente = new JLabel("CLIENTE:");
			lblCliente.setFont(new Font("Cambria", Font.BOLD, 14));
			lblCliente.setBounds(29, 28, 68, 20);
			contentPane.add(lblCliente);
			
			JLabel lblSucursal = new JLabel("SUCURSAL:");
			lblSucursal.setFont(new Font("Cambria", Font.BOLD, 14));
			lblSucursal.setBounds(29, 59, 79, 20);
			contentPane.add(lblSucursal);

		
		
		this.setVisible(true);
	}


	public JButton getBtnAceptar() {
		return btnAceptar;
	}


	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}


	public JButton getBtnCancelar() {
		return btnCancelar;
	}


	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}


	@SuppressWarnings("rawtypes")
	public JComboBox getComboCliente() {
		return comboCliente;
	}


	@SuppressWarnings("rawtypes")
	public void setComboCliente(JComboBox comboCliente) {
		this.comboCliente = comboCliente;
	}


	@SuppressWarnings("rawtypes")
	public JComboBox getComboSucursal() {
		return comboSucursal;
	}


	@SuppressWarnings("rawtypes")
	public void setComboSucursal(JComboBox comboSucursal) {
		this.comboSucursal = comboSucursal;
	}


}

