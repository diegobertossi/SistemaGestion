package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import presentacion.controlador.ControladorReparacion;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Color;

public class VentanaAgregarCorreo extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField txtCorreo;
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;
	private JButton btnAgregarCorreo;
	private JButton btnCancelar;
	
	public VentanaAgregarCorreo(ControladorReparacion controlador) 
	{
		super();
		setResizable(false);
		this.controlador = controlador;
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 490, 159);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 474, 126);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCorreo = new JLabel("Correo: ");
		lblCorreo.setFont(new Font("Cambria", Font.BOLD, 18));
		lblCorreo.setBounds(16, 31, 89, 18);
		panel.add(lblCorreo);

		
		
		txtCorreo = new JTextField();
		txtCorreo.setBackground(SystemColor.inactiveCaptionBorder);
		txtCorreo.setFont(new Font("Cambria", Font.BOLD, 18));
		txtCorreo.setBounds(109, 25, 340, 30);
		panel.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		btnAgregarCorreo = new JButton("ACEPTAR");
		btnAgregarCorreo.setBackground(new Color(192, 192, 192));
		btnAgregarCorreo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregarCorreo.setBounds(225, 78, 107, 30);
			
		panel.add(btnAgregarCorreo);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(140, 232, 113, -8);
		panel.add(horizontalStrut);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 192, 192));
		btnCancelar.setBounds(342, 79, 107, 30);
		panel.add(btnCancelar);

	
		this.setVisible(true);
	}


	public JTextField getTxtReferencia() {
		return txtCorreo;
	}


	public void setTxtReferencia(JTextField txtReferencia) {
		this.txtCorreo = txtReferencia;
	}


	


	public JButton getBtnAgregarRepuesto() {
		return btnAgregarCorreo;
	}


	public void setBtnAgregarRepuesto(JButton btnAgregarCliente) {
		this.btnAgregarCorreo = btnAgregarCliente;
	}


	public JButton getBtnCancelar() {
		return btnCancelar;
	}


	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	
}	