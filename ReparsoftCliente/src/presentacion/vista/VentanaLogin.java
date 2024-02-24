package presentacion.vista;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class VentanaLogin extends JFrame {
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTextField txtUsuLogin;
	private JPasswordField txtUsuPass;
	
	
	
	public VentanaLogin(){
		
		super();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setBounds(460, 260, 439, 195);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);
		
				btnAceptar = new JButton("Aceptar");
				btnAceptar.setBounds(292, 78, 108, 29);
				panel.add(btnAceptar);
				btnAceptar.setForeground(new Color(47, 79, 79));
				btnAceptar.setFont(new Font("Cambria", Font.BOLD, 16));
				
				btnCancelar = new JButton("Cancelar");
				btnCancelar.setBounds(292, 108, 108, 29);
				panel.add(btnCancelar);
				btnCancelar.setForeground(new Color(47, 79, 79));
				btnCancelar.setFont(new Font("Cambria", Font.BOLD, 16));
				
				
				txtUsuLogin = new JTextField();
				txtUsuLogin.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				txtUsuLogin.setBounds(151, 86, 111, 20);
				panel.add(txtUsuLogin);
				txtUsuLogin.setBackground(SystemColor.activeCaption);
				txtUsuLogin.setForeground(new Color(47, 79, 79));
				txtUsuLogin.setFont(new Font("Cambria", Font.PLAIN, 14));
				txtUsuPass = new JPasswordField();
				txtUsuPass.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				txtUsuPass.setBounds(151, 109, 111, 20);
				panel.add(txtUsuPass);
				txtUsuPass.setBackground(SystemColor.activeCaption);
				txtUsuPass.setForeground(new Color(47, 79, 79));
				txtUsuPass.setFont(new Font("Cambria", Font.PLAIN, 14));
				
							
		
		JLabel lblNewLabel = new JLabel("Usuario\r\n");
		lblNewLabel.setBounds(42, 89, 106, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(47, 79, 79));
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 14));
		
		JLabel lblNewLabel_1 = new JLabel("Contraseña");
		lblNewLabel_1.setBounds(42, 112, 106, 14);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(47, 79, 79));
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 14));
		
		JLabel lblLogingAlSistema = new JLabel("LOGIN AL SISTEMA");
		lblLogingAlSistema.setBounds(135, 29, 207, 32);
		panel.add(lblLogingAlSistema);
		lblLogingAlSistema.setForeground(new Color(47, 79, 79));
		lblLogingAlSistema.setFont(new Font("Cambria", Font.BOLD, 22));
		
				JLabel img = new JLabel("");
				img.setBounds(55, 11, 70, 73);
				panel.add(img);
				img.setIcon(new ImageIcon(this.getClass().getResource("/Login.png")));
				
				JSeparator separator = new JSeparator();
				separator.setBounds(42, 104, 97, 2);
				panel.add(separator);
				
				JSeparator separator_1 = new JSeparator();
				separator_1.setBounds(42, 127, 97, 2);
				panel.add(separator_1);
				setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsuLogin, txtUsuPass, btnAceptar, btnCancelar}));
		
				setLocationCenter();
		setVisible(true);
		
	}

	
	public void setLocationCenter() {
		setLocationMove(0, 0);
	}

	public void setLocationMove(int moveWidth, int moveHeight) {
		// Obtenemos el tama�o de la pantalla.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Obtenemos el tama�o de nuestro frame.
		Dimension frameSize = this.getSize();
		frameSize.width = frameSize.width > screenSize.width ? screenSize.width : frameSize.width;
		frameSize.height = frameSize.height > screenSize.height ? screenSize.height : frameSize.height;
		// We define the location. Definimos la localizaci�n.
		setLocation((screenSize.width - frameSize.width) / 2 + moveWidth,
				(screenSize.height - frameSize.height) / 2 + moveHeight);
	}
	

	public JButton getBtnCancelar() {
		return btnCancelar;
	}


	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}


	public JButton getBtnAceptar() {
		return btnAceptar;
	}





	public JTextField getTxtUsuLogin() {
		return txtUsuLogin;
	}


	public JPasswordField getTxtUsuPass() {
		return txtUsuPass;
	}
}
