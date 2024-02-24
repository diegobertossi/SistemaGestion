package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorReparacion;
import java.awt.Font;
import java.awt.SystemColor;

public class VentanaPresupuestos extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPresupuestoPorELS;
	
	private JButton btningresarPago;
	private JButton btnmarcarAceptaciones;
	
	
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;

	public VentanaPresupuestos(ControladorReparacion controlador) 
	{
		super();
		setResizable(false);
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 504, 153);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
			
			btnPresupuestoPorELS = new JButton("<html><center>PRESUPUESTO POR ELS</html>");
			btnPresupuestoPorELS.setBounds(8, 29, 151, 60);
			contentPane.add(btnPresupuestoPorELS);
			btnPresupuestoPorELS.setFont(new Font("Cambria", Font.BOLD, 14));
			
			btningresarPago = new JButton("<html><center>INGRESAR PAGOS</html>");
			btningresarPago.setFont(new Font("Cambria", Font.BOLD, 14));
			btningresarPago.setBounds(326, 29, 151, 60);
			contentPane.add(btningresarPago);
			
			btnmarcarAceptaciones = new JButton("<html><center>MARCAR ACEPTACIONES</html>");
			btnmarcarAceptaciones.setFont(new Font("Cambria", Font.BOLD, 14));
			btnmarcarAceptaciones.setBounds(167, 29, 151, 60);
			contentPane.add(btnmarcarAceptaciones);

		
		
		this.setVisible(true);
	}


	public JButton getBtnPresupuestoPorELS() {
		return btnPresupuestoPorELS;
	}

	public void setBtnPresupuestoPorELS(JButton btnPresupuestoPorELS) {
		this.btnPresupuestoPorELS = btnPresupuestoPorELS;
	}

	public JButton getBtningresarPago() {
		return btningresarPago;
	}

	public void setBtningresarPago(JButton btningresarPago) {
		this.btningresarPago = btningresarPago;
	}

	public JButton getBtnmarcarAceptaciones() {
		return btnmarcarAceptaciones;
	}

	public void setBtnmarcarAceptaciones(JButton btnmarcarAceptaciones) {
		this.btnmarcarAceptaciones = btnmarcarAceptaciones;
	}

	
	}

