package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

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
		setBounds(100, 100, 452, 385);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(new Color(176, 196, 222));
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(53, 227, 339, 104);
		contentPane.add(panel);
		panel.setLayout(null);
		
				
		btnBuenosAires = new JButton("<html><center>BUENOS AIRES</html>");
		btnBuenosAires.setBackground(new Color(176, 196, 222));
		btnBuenosAires.setForeground(new Color(70, 130, 180));
		btnBuenosAires.setBounds(46, 43, 100, 43);
		panel.add(btnBuenosAires);
		btnBuenosAires.setFont(new Font("Cambria", Font.BOLD, 14));
		
			
			btnBariloche = new JButton("<html><center>BARILOCHE</html>");
			btnBariloche.setBackground(new Color(176, 196, 222));
			btnBariloche.setForeground(new Color(70, 130, 180));
			btnBariloche.setBounds(192, 40, 100, 50);
			panel.add(btnBariloche);
			btnBariloche.setFont(new Font("Cambria", Font.BOLD, 14));
			
			JLabel lblNewLabel_1 = new JLabel("ELIJA LA UBICACIÓN DEL SISTEMA :");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setForeground(new Color(65, 105, 225));
			lblNewLabel_1.setBounds(10, 4, 319, 33);
			panel.add(lblNewLabel_1);
			lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 18));
			
			lblreparsoft = new JLabel("");
			lblreparsoft.setIcon(new ImageIcon(this.getClass().getResource("/REPARSOFT logo Inicio.png")));
			lblreparsoft.setVerticalAlignment(SwingConstants.TOP);
			lblreparsoft.setHorizontalAlignment(SwingConstants.CENTER);
			lblreparsoft.setForeground(new Color(105, 105, 105));
			lblreparsoft.setFont(new Font("Tahoma", Font.BOLD, 32));
			lblreparsoft.setBounds(1, -9, 445, 362);
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

