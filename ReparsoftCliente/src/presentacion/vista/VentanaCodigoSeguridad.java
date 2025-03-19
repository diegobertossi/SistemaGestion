package presentacion.vista;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorListados;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;



public class VentanaCodigoSeguridad extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@SuppressWarnings("unused")
	private ControladorListados controlador;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JPasswordField txtCodigoSeguridad;
	private JPanel panelDetalle;
	private JPanel panelCodigo;
	private JRadioButton rdbtnMostrar;
	private JRadioButton rdbtnOcultar;
	private ButtonGroup buttonGroupDetalle;

	public VentanaCodigoSeguridad(ControladorListados controlador) {
		super();
		setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 308, 177);

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelCodigo = new JPanel();
		panelCodigo.setBackground(SystemColor.inactiveCaption);
		panelCodigo.setBorder(new LineBorder(new Color(0, 139, 139)));
		panelCodigo.setBounds(10, 11, 269, 124);
		contentPane.add(panelCodigo);
		panelCodigo.setLayout(null);

		JLabel lblPresupuestoEnviado = new JLabel("CÓDIGO DE SEGURIDAD");
		lblPresupuestoEnviado.setBorder(null);
		lblPresupuestoEnviado.setForeground(Color.BLACK);
		lblPresupuestoEnviado.setFont(new Font("Cambria", Font.BOLD, 18));
		lblPresupuestoEnviado.setBounds(32, 11, 205, 22);
		panelCodigo.add(lblPresupuestoEnviado);

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(15, 86, 111, 27);
		panelCodigo.add(btnCancelar);
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 12));

		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(141, 86, 111, 27);
		panelCodigo.add(btnAceptar);
		btnAceptar.setFont(new Font("Cambria", Font.BOLD, 12));
		
		txtCodigoSeguridad = new JPasswordField();
		txtCodigoSeguridad.setBounds(93, 45, 82, 22);
		panelCodigo.add(txtCodigoSeguridad);
		
		panelDetalle = new JPanel();
		panelDetalle.setBorder(new LineBorder(new Color(0, 139, 139)));
		panelDetalle.setVisible(false);
		panelDetalle.setOpaque(false);
		panelDetalle.setBounds(10, 11, 269, 124);
		contentPane.add(panelDetalle);
		panelDetalle.setLayout(null);
		
		rdbtnMostrar = new JRadioButton("MOSTRAR DETALLE");
		rdbtnMostrar.setFont(new Font("Cambria", Font.BOLD, 14));
		rdbtnMostrar.setOpaque(false);
		rdbtnMostrar.setBounds(55, 35, 159, 23);
		panelDetalle.add(rdbtnMostrar);
		
		rdbtnOcultar = new JRadioButton("OCULTAR DETALLE");
		rdbtnOcultar.setSelected(true);
		rdbtnOcultar.setFont(new Font("Cambria", Font.BOLD, 14));
		rdbtnOcultar.setOpaque(false);
		rdbtnOcultar.setBounds(55, 63, 159, 23);
		panelDetalle.add(rdbtnOcultar);
		
		
		buttonGroupDetalle= new ButtonGroup();
		buttonGroupDetalle.add(rdbtnMostrar);
		buttonGroupDetalle.add(rdbtnOcultar);

		this.setVisible(true);

	}


	public JButton getBtnAceptar() {
		return btnAceptar;
	}


	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}


	public JButton getBtnCancelar() {
		return btnCancelar;
	}


	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}



	public JPasswordField getTxtCodigoSeguridad() {
		return txtCodigoSeguridad;
	}


	public void setTxtCodigoSeguridad(JPasswordField txtCodigoSeguridad) {
		this.txtCodigoSeguridad = txtCodigoSeguridad;
	}


	public JPanel getPanelDetalle() {
		return panelDetalle;
	}


	public void setPanelDetalle(JPanel panelDetalle) {
		this.panelDetalle = panelDetalle;
	}


	public JPanel getPanelCodigo() {
		return panelCodigo;
	}


	public void setPanelCodigo(JPanel panelCodigo) {
		this.panelCodigo = panelCodigo;
	}


	public JRadioButton getRdbtnMostrar() {
		return rdbtnMostrar;
	}


	public void setRdbtnMostrar(JRadioButton rdbtnMostrar) {
		this.rdbtnMostrar = rdbtnMostrar;
	}


	public JRadioButton getRdbtnOcultar() {
		return rdbtnOcultar;
	}


	public void setRdbtnOcultar(JRadioButton rdbtnOcultar) {
		this.rdbtnOcultar = rdbtnOcultar;
	}


	public ButtonGroup getButtonGroupDetalle() {
		return buttonGroupDetalle;
	}


	public void setButtonGroupDetalle(ButtonGroup buttonGroupDetalle) {
		this.buttonGroupDetalle = buttonGroupDetalle;
	}
}