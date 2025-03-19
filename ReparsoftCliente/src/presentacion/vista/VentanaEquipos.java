package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorReparacion;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

public class VentanaEquipos extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAgregarEquipos;
	private JButton btnVisualizarEquipos;
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;

	public VentanaEquipos() 
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
		
				
		btnAgregarEquipos = new JButton("<html><center>AGREGAR EQUIPOS</html>");
		btnAgregarEquipos.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregarEquipos.setBounds(39, 33, 100, 50);
		contentPane.add(btnAgregarEquipos);
		
			
			btnVisualizarEquipos = new JButton("<html><center>VISUALIZAR EQUIPOS</html>");
			btnVisualizarEquipos.setFont(new Font("Cambria", Font.BOLD, 14));
			btnVisualizarEquipos.setBounds(149, 33, 100, 50);
			contentPane.add(btnVisualizarEquipos);

		
		
		this.setVisible(true);
	}

	public JButton getBtnAgregarEquipos() {
		return btnAgregarEquipos;
	}

	public void setBtnAgregarEquipos(JButton btnAgregarEquipos) {
		this.btnAgregarEquipos = btnAgregarEquipos;
	}

	public JButton getBtnVisualizarEquipos() {
		return btnVisualizarEquipos;
	}

	public void setBtnVisualizarEquipos(JButton btnVisualizarEquipos) {
		this.btnVisualizarEquipos = btnVisualizarEquipos;
	}
	
	
	
}

