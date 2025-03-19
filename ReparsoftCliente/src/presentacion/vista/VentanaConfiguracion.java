package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorConfiguraciones;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorUbicacionBase;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

public class VentanaConfiguracion extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnUbicacionSistema;
	@SuppressWarnings("unused")
	private ControladorConfiguraciones controlador;

	public VentanaConfiguracion(ControladorConfiguraciones controlador) 
	{
		super();
		setResizable(false);
		//this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 306, 155);

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
		btnUbicacionSistema = new JButton("<html><center>CAMBIAR UBICACION DEL SISTEMEA</html>");
		btnUbicacionSistema.setFont(new Font("Cambria", Font.BOLD, 14));
		btnUbicacionSistema.setBounds(39, 33, 211, 50);
		contentPane.add(btnUbicacionSistema);

		
		
		this.setVisible(true);
	}

	public JButton getBtnUbicacionSistema() {
		return btnUbicacionSistema;
	}

	public void setBtnUbicacionSistema(JButton btnUbicacionSistema) {
		this.btnUbicacionSistema = btnUbicacionSistema;
	}


	
	
	
}

