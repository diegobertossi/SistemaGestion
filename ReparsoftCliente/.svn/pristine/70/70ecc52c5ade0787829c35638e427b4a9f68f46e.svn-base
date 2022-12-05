package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorPrincipal;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorSalidas;

import java.awt.Font;
import java.awt.SystemColor;

public class VentanaSalidas extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnGenerarRemito;
	private JButton btnDesvincularRemito;
	private JButton btnMarcarEnviados;
	private ControladorSalidas controlador;

	public VentanaSalidas(ControladorSalidas controlador) 
	{
		super();
		setResizable(false);
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 495, 183);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
		btnGenerarRemito = new JButton("<html><center>GENERAR REMITO POR CLIENTE</html>");
		btnGenerarRemito.setBounds(10, 47, 147, 63);
		contentPane.add(btnGenerarRemito);
		btnGenerarRemito.setFont(new Font("Cambria", Font.BOLD, 14));
		
			
			btnMarcarEnviados = new JButton("<html><center>MARCAR ENVIADOS</html>");
			btnMarcarEnviados.setBounds(173, 47, 147, 63);
			contentPane.add(btnMarcarEnviados);
			btnMarcarEnviados.setFont(new Font("Cambria", Font.BOLD, 14));
			
			btnDesvincularRemito = new JButton("<html><center>DESVINCULAR REMITO</html>");
			btnDesvincularRemito.setBounds(332, 47, 147, 63);
			contentPane.add(btnDesvincularRemito);
			btnDesvincularRemito.setFont(new Font("Cambria", Font.BOLD, 14));

		
		
		this.setVisible(true);
	}

	public JButton getBtnGenerarRemito() {
		return btnGenerarRemito;
	}

	public void setBtnGenerarRemito(JButton btnGenerarRemito) {
		this.btnGenerarRemito = btnGenerarRemito;
	}

	public JButton getBtnDesvincularRemito() {
		return btnDesvincularRemito;
	}

	public void setBtnDesvincularRemito(JButton btnDesvincularRemito) {
		this.btnDesvincularRemito = btnDesvincularRemito;
	}

	public JButton getBtnMarcarEnviados() {
		return btnMarcarEnviados;
	}

	public void setBtnMarcarEnviados(JButton btnMarcarEnviados) {
		this.btnMarcarEnviados = btnMarcarEnviados;
	}
	
	

	}

