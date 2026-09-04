package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Cursor;

public class VentanaUbicacionBaseDeDatos extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAcceder;
	private JLabel lblreparsoft;
	private JPanel panel;
	
	private JComboBox comboUbicacion;
	
	public VentanaUbicacionBaseDeDatos() 
	{
		super();
		setResizable(false);
		//this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 391);

		this.setLocationRelativeTo(null);

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(176, 196, 222));
		panel.setBorder(new LineBorder(new Color(0, 128, 128)));
		panel.setBounds(36, 227, 356, 104);
		contentPane.add(panel);
		panel.setLayout(null);
		
			
			btnAcceder = new JButton("<html><center>ACCEDER</html>");
			btnAcceder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnAcceder.setBackground(new Color(176, 196, 222));
			btnAcceder.setForeground(new Color(30, 144, 255));
			btnAcceder.setBounds(119, 55, 118, 38);
			panel.add(btnAcceder);
			btnAcceder.setFont(new Font("Cambria", Font.BOLD, 14));

			JLabel lblNewLabel_1 = new JLabel("UBICACION DEL SISTEMA :");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setForeground(new Color(25, 25, 112));
			lblNewLabel_1.setBounds(6, 19, 202, 25);
			panel.add(lblNewLabel_1);
			lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 16));
			
			comboUbicacion = new JComboBox();
			comboUbicacion.setForeground(new Color(25, 25, 112));
			comboUbicacion.setOpaque(false);
			comboUbicacion.setFont(new Font("Cambria", Font.BOLD, 16));
			comboUbicacion.setBounds(210, 19, 136, 25);
			panel.add(comboUbicacion);
			
			lblreparsoft = new JLabel("");
			lblreparsoft.setIcon(new ImageIcon(this.getClass().getResource("/REPARSOFT logo Inicio.png")));
			lblreparsoft.setVerticalAlignment(SwingConstants.TOP);
			lblreparsoft.setHorizontalAlignment(SwingConstants.CENTER);
			lblreparsoft.setForeground(new Color(105, 105, 105));
			lblreparsoft.setFont(new Font("Tahoma", Font.BOLD, 32));
			lblreparsoft.setBounds(1, -9, 445, 366);
			contentPane.add(lblreparsoft);

		
		
		this.setVisible(true);
	}



	public JButton getBtnAcceder() {
		return btnAcceder;
	}

	public void setBtnAcceder(JButton btnBariloche) {
		this.btnAcceder = btnBariloche;
	}



	public JComboBox getComboUbicacion() {
		return comboUbicacion;
	}



	public void setComboUbicacion(JComboBox comboUbicacion) {
		this.comboUbicacion = comboUbicacion;
	}
}

