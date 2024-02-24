package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class VentanaUbicacionBaseDeDatos extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBuenosAires;
	private JButton btnBariloche;
	private JLabel lblreparsoft;
	private JPanel panel;
	
	public VentanaUbicacionBaseDeDatos() 
	{
		super();
		setResizable(false);
		//this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 270);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 139, 139), 2));
		panel.setOpaque(false);
		panel.setBounds(10, 116, 339, 104);
		contentPane.add(panel);
		panel.setLayout(null);
		
				
		btnBuenosAires = new JButton("<html><center>BUENOS AIRES</html>");
		btnBuenosAires.setForeground(new Color(47, 79, 79));
		btnBuenosAires.setBounds(46, 43, 100, 43);
		panel.add(btnBuenosAires);
		btnBuenosAires.setFont(new Font("Cambria", Font.BOLD, 14));
		
			
			btnBariloche = new JButton("<html><center>BARILOCHE</html>");
			btnBariloche.setForeground(new Color(47, 79, 79));
			btnBariloche.setBounds(192, 40, 100, 50);
			panel.add(btnBariloche);
			btnBariloche.setFont(new Font("Cambria", Font.BOLD, 14));
			
			JLabel lblNewLabel_1 = new JLabel("ELIJA LA UBICACION DEL SISTEMA :");
			lblNewLabel_1.setForeground(new Color(47, 79, 79));
			lblNewLabel_1.setBounds(56, 4, 227, 33);
			panel.add(lblNewLabel_1);
			lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 14));
			
			JLabel lblNewLabel = new JLabel("<html><center>BIENVENIDO AL SISTEMA DE GESTIÓN MULTITAREA</html>");
			lblNewLabel.setForeground(new Color(70, 130, 180));
			lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 20));
			lblNewLabel.setBounds(32, 2, 295, 66);
			contentPane.add(lblNewLabel);
			
			lblreparsoft = new JLabel("<html><center>REPARSOFT</html>");
			lblreparsoft.setForeground(new Color(105, 105, 105));
			lblreparsoft.setFont(new Font("Wide Latin", Font.BOLD, 22));
			lblreparsoft.setBounds(41, 55, 276, 61);
			contentPane.add(lblreparsoft);

		
		
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

