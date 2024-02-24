package presentacion.vista;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorSalidas;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class VentanaSeleccionarRemito extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAceptar;
	private JButton btnCancelar;
	@SuppressWarnings("unused")
	private ControladorSalidas controlador;
	@SuppressWarnings("rawtypes")
	private JComboBox comboUbicacion;
	@SuppressWarnings("rawtypes")
	private JComboBox comboNumRemito;
	

	@SuppressWarnings("rawtypes")
	public VentanaSeleccionarRemito(ControladorSalidas controlador) 
	{
		super();
		setResizable(false);
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 144);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAceptar.setBounds(320, 21, 92, 27);
		contentPane.add(btnAceptar);
		
			
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
			btnCancelar.setBounds(320, 59, 92, 27);
			contentPane.add(btnCancelar);
			
			comboUbicacion = new JComboBox();
			comboUbicacion.setFont(new Font("Cambria", Font.PLAIN, 14));
			comboUbicacion.setBounds(122, 28, 165, 20);
			contentPane.add(comboUbicacion);
			
			comboNumRemito = new JComboBox();
			comboNumRemito.setFont(new Font("Cambria", Font.PLAIN, 14));
			comboNumRemito.setBounds(122, 59, 165, 20);
			contentPane.add(comboNumRemito);
			
			JLabel lblUbicacion = new JLabel("UBICACIÓN:");
			lblUbicacion.setFont(new Font("Cambria", Font.BOLD, 14));
			lblUbicacion.setBounds(29, 28, 87, 20);
			contentPane.add(lblUbicacion);
			
			JLabel lblNumRemito = new JLabel("N° REMITO:");
			lblNumRemito.setFont(new Font("Cambria", Font.BOLD, 14));
			lblNumRemito.setBounds(29, 59, 87, 20);
			contentPane.add(lblNumRemito);

		
		
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
	


	@SuppressWarnings("rawtypes")
	public JComboBox getComboUbicacion() {
		return comboUbicacion;
	}
	


	@SuppressWarnings("rawtypes")
	public void setComboUbicacion(JComboBox comboUbicacion) {
		this.comboUbicacion = comboUbicacion;
	}
	


	@SuppressWarnings("rawtypes")
	public JComboBox getComboNumRemito() {
		return comboNumRemito;
	}
	


	@SuppressWarnings("rawtypes")
	public void setComboNumRemito(JComboBox comboNumRemito) {
		this.comboNumRemito = comboNumRemito;
	}
	





}

