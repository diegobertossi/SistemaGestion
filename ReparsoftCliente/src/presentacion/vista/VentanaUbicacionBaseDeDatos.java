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
		setBounds(100, 100, 452, 420);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 255), 2));
		panel.setOpaque(false);
		panel.setBounds(53, 256, 339, 104);
		contentPane.add(panel);
		panel.setLayout(null);
		
				
		btnBuenosAires = new JButton("<html><center>BUENOS AIRES</html>");
		btnBuenosAires.setBackground(new Color(0, 139, 139));
		btnBuenosAires.setForeground(new Color(255, 255, 255));
		btnBuenosAires.setBounds(46, 43, 100, 43);
		panel.add(btnBuenosAires);
		btnBuenosAires.setFont(new Font("Roboto", Font.BOLD, 14));
		
			
			btnBariloche = new JButton("<html><center>BARILOCHE</html>");
			btnBariloche.setBackground(new Color(0, 139, 139));
			btnBariloche.setForeground(new Color(255, 255, 255));
			btnBariloche.setBounds(192, 40, 100, 50);
			panel.add(btnBariloche);
			btnBariloche.setFont(new Font("Roboto", Font.BOLD, 14));
			
			JLabel lblNewLabel_1 = new JLabel("ELIJA LA UBICACION DEL SISTEMA :");
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setBounds(54, 4, 231, 33);
			panel.add(lblNewLabel_1);
			lblNewLabel_1.setFont(new Font("Roboto", Font.BOLD, 14));
			
			lblreparsoft = new JLabel("");
			lblreparsoft.setIcon(new ImageIcon("F:\\Users\\Diego\\git\\SistemaGestion\\ReparsoftCliente\\img\\REPARSOFT logo.png"));
			lblreparsoft.setForeground(new Color(105, 105, 105));
			lblreparsoft.setFont(new Font("Magneto", Font.BOLD, 32));
			lblreparsoft.setBounds(3, 3, 445, 385);
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

