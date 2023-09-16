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

public class VentanaPresupuestos extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnEnviarPresupuestos;
	private JButton btnPresupuestoPorELS;
	
	private JButton btningresarPago;
	private JButton btnmarcarAceptaciones;
	private JButton btnenviarInformesSiemens;
	
	
	private ControladorReparacion controlador;

	public VentanaPresupuestos(ControladorReparacion controlador) 
	{
		super();
		setResizable(false);
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 504, 213);

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
			
			btnEnviarPresupuestos = new JButton("<html><center>ENVIAR PRESUPUESTOS</html>");
			btnEnviarPresupuestos.setBounds(62, 107, 151, 60);
			contentPane.add(btnEnviarPresupuestos);
			btnEnviarPresupuestos.setFont(new Font("Cambria", Font.BOLD, 14));
			
			btningresarPago = new JButton("<html><center>INGRESAR PAGOS</html>");
			btningresarPago.setFont(new Font("Cambria", Font.BOLD, 14));
			btningresarPago.setBounds(326, 29, 151, 60);
			contentPane.add(btningresarPago);
			
			btnmarcarAceptaciones = new JButton("<html><center>MARCAR ACEPTACIONES</html>");
			btnmarcarAceptaciones.setFont(new Font("Cambria", Font.BOLD, 14));
			btnmarcarAceptaciones.setBounds(167, 29, 151, 60);
			contentPane.add(btnmarcarAceptaciones);
			
			btnenviarInformesSiemens = new JButton("<html><center>ENVIAR INFORMES SIEMENS</html>");
			btnenviarInformesSiemens.setFont(new Font("Cambria", Font.BOLD, 14));
			btnenviarInformesSiemens.setBounds(275, 107, 151, 60);
			contentPane.add(btnenviarInformesSiemens);

		
		
		this.setVisible(true);
	}

	public JButton getBtnEnviarPresupuestos() {
		return btnEnviarPresupuestos;
	}

	public void setBtnEnviarPresupuestos(JButton btnEnviarPresupuestos) {
		this.btnEnviarPresupuestos = btnEnviarPresupuestos;
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

	public JButton getBtnenviarInformesSiemens() {
		return btnenviarInformesSiemens;
	}

	public void setBtnenviarInformesSiemens(JButton btnenviarInformesSiemens) {
		this.btnenviarInformesSiemens = btnenviarInformesSiemens;
	}

	
	}

