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

public class VentanaExcel extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnRepar;
	private JButton btnCaja;
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;

	public VentanaExcel() 
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
		
				
		btnRepar = new JButton("<html><center>REPAR</html>");
		btnRepar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnRepar.setBounds(30, 33, 100, 50);
		contentPane.add(btnRepar);
		
			
			btnCaja = new JButton("<html><center>CAJA</html>");
			btnCaja.setFont(new Font("Cambria", Font.BOLD, 14));
			btnCaja.setBounds(160, 33, 100, 50);
			contentPane.add(btnCaja);

		
		
		this.setVisible(true);
	}

	public JButton getBtnRepar() {
		return btnRepar;
	}

	public void setBtnRepar(JButton btnRepar) {
		this.btnRepar = btnRepar;
	}

	public JButton getBtnCaja() {
		return btnCaja;
	}

	public void setBtnCaja(JButton btnCaja) {
		this.btnCaja = btnCaja;
	}

	
	
	
}

