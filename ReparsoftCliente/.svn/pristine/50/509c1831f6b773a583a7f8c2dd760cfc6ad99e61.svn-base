package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorPresupuestos;
import presentacion.controlador.ControladorPrincipal;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorSalidas;

import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class VentanaSeleccionarELS extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAceptar;
	private ControladorPresupuestos controlador;
	private JComboBox comboELS;
	private JLabel lblELS;
	private JLabel labelELS;
	private JButton btnCancelar;
	
	

	public VentanaSeleccionarELS(ControladorPresupuestos controlador) {
		super();
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 163);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAceptar.setBounds(326, 52, 92, 27);
		contentPane.add(btnAceptar);

		comboELS = new JComboBox();
		comboELS.setFont(new Font("Cambria", Font.PLAIN, 14));
		comboELS.setBounds(123, 55, 180, 20);
		contentPane.add(comboELS);

		lblELS = new JLabel("ELS:");
		lblELS.setFont(new Font("Cambria", Font.BOLD, 14));
		lblELS.setBounds(39, 55, 68, 20);
		contentPane.add(lblELS);

		labelELS = new JLabel("SELECCIONAR N\u00DAMERO DE ELS:");
		labelELS.setFont(new Font("Cambria", Font.BOLD, 14));
		labelELS.setBounds(29, 11, 306, 20);
		contentPane.add(labelELS);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelar.setBounds(326, 86, 92, 27);
		contentPane.add(btnCancelar);

		this.setVisible(true);
	}



	public JButton getBtnAceptar() {
		return btnAceptar;
	}



	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}



	public JComboBox getComboELS() {
		return comboELS;
	}



	public void setComboELS(JComboBox comboELS) {
		this.comboELS = comboELS;
	}



	public JButton getBtnCancelar() {
		return btnCancelar;
	}



	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

}
