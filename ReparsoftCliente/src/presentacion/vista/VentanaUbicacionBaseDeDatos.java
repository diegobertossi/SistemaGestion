package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;

public class VentanaUbicacionBaseDeDatos extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBuenosAires;
	private JButton btnBariloche;
	
	public VentanaUbicacionBaseDeDatos() 
	{
		super();
		setResizable(false);
		//this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 306, 155);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
		btnBuenosAires = new JButton("<html><center>BUENOS AIRES</html>");
		btnBuenosAires.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBuenosAires.setBounds(30, 55, 100, 50);
		contentPane.add(btnBuenosAires);
		
			
			btnBariloche = new JButton("<html><center>BARILOCHE</html>");
			btnBariloche.setFont(new Font("Cambria", Font.BOLD, 14));
			btnBariloche.setBounds(160, 55, 100, 50);
			contentPane.add(btnBariloche);
			
			JLabel lblNewLabel = new JLabel("ELIJA LA UBICACION DEL SISTEMA :");
			lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 14));
			lblNewLabel.setBounds(30, 11, 227, 33);
			contentPane.add(lblNewLabel);

		
		
		this.setVisible(true);
	}

	public JButton getBtnBuenosAires() {
		return btnBuenosAires;
	}

	public void setBtnBuenosAires(JButton btnBuenosAires) {
		this.btnBuenosAires = btnBuenosAires;
	}

	public JButton getBtnBariloche() {
		return btnBariloche;
	}

	public void setBtnBariloche(JButton btnBariloche) {
		this.btnBariloche = btnBariloche;
	}


}

