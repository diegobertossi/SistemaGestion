package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import presentacion.controlador.ControladorReparacion;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class VentanaQuitarCorreo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField txtCorreo1;
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;
	private JButton btnQuitarCorreoSeleccionado;
	private JButton btnCancelar;
	private JTextField txtCorreo2;
	private JTextField txtCorreo3;
	private JTextField txtCorreo4;
	
	private JCheckBox chkCorreo1;
	private JCheckBox chkCorreo2;
	private JCheckBox chkCorreo3;
	private JCheckBox chkCorreo4;
	
	

	public VentanaQuitarCorreo() {
		super();
		setResizable(false);
		setTitle("Quitar Correos");

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 617, 252);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 601, 213);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setOpaque(false);
		panel_1.setBounds(10, 11, 471, 186);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblCorreo = new JLabel("CORREO");
		lblCorreo.setBounds(15, 11, 89, 18);
		panel_1.add(lblCorreo);
		lblCorreo.setFont(new Font("Cambria", Font.BOLD, 18));

		txtCorreo1 = new JTextField();
		txtCorreo1.setBounds(15, 50, 340, 30);
		panel_1.add(txtCorreo1);
		txtCorreo1.setBackground(SystemColor.inactiveCaptionBorder);
		txtCorreo1.setFont(new Font("Cambria", Font.BOLD, 18));
		txtCorreo1.setColumns(10);

		txtCorreo2 = new JTextField();
		txtCorreo2.setBounds(15, 83, 340, 30);
		panel_1.add(txtCorreo2);
		txtCorreo2.setFont(new Font("Cambria", Font.BOLD, 18));
		txtCorreo2.setColumns(10);
		txtCorreo2.setBackground(SystemColor.inactiveCaptionBorder);

		txtCorreo3 = new JTextField();
		txtCorreo3.setBounds(15, 116, 340, 30);
		panel_1.add(txtCorreo3);
		txtCorreo3.setFont(new Font("Cambria", Font.BOLD, 18));
		txtCorreo3.setColumns(10);
		txtCorreo3.setBackground(SystemColor.inactiveCaptionBorder);

		txtCorreo4 = new JTextField();
		txtCorreo4.setBounds(15, 149, 340, 30);
		panel_1.add(txtCorreo4);
		txtCorreo4.setFont(new Font("Cambria", Font.BOLD, 18));
		txtCorreo4.setColumns(10);
		txtCorreo4.setBackground(SystemColor.inactiveCaptionBorder);

		// Después de crear cada JTextField, agrega su JCheckBox al panel

		chkCorreo1 = new JCheckBox();
		chkCorreo1.setBounds(393, 50, 21, 30);
		panel_1.add(chkCorreo1);
		chkCorreo1.setBackground(SystemColor.inactiveCaption);
		chkCorreo1.setFont(new Font("Cambria", Font.PLAIN, 14));

		chkCorreo2 = new JCheckBox();
		chkCorreo2.setBounds(393, 83, 21, 30);
		panel_1.add(chkCorreo2);
		chkCorreo2.setBackground(SystemColor.inactiveCaption);
		chkCorreo2.setFont(new Font("Cambria", Font.PLAIN, 14));

		chkCorreo3 = new JCheckBox();
		chkCorreo3.setBounds(393, 116, 21, 30);
		panel_1.add(chkCorreo3);
		chkCorreo3.setBackground(SystemColor.inactiveCaption);
		chkCorreo3.setFont(new Font("Cambria", Font.PLAIN, 14));

		chkCorreo4 = new JCheckBox();
		chkCorreo4.setBounds(393, 149, 21, 30);
		panel_1.add(chkCorreo4);
		chkCorreo4.setBackground(SystemColor.inactiveCaption);
		chkCorreo4.setFont(new Font("Cambria", Font.PLAIN, 14));

		JLabel lblBorrar = new JLabel("BORRAR");
		lblBorrar.setBounds(372, 11, 89, 18);
		panel_1.add(lblBorrar);
		lblBorrar.setFont(new Font("Cambria", Font.BOLD, 18));

		btnQuitarCorreoSeleccionado = new JButton("ACEPTAR");
		btnQuitarCorreoSeleccionado.setBackground(new Color(192, 192, 192));
		btnQuitarCorreoSeleccionado.setFont(new Font("Cambria", Font.BOLD, 14));
		btnQuitarCorreoSeleccionado.setBounds(490, 70, 107, 30);

		panel.add(btnQuitarCorreoSeleccionado);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(140, 232, 113, -8);
		panel.add(horizontalStrut);

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 192, 192));
		btnCancelar.setBounds(491, 121, 107, 30);
		panel.add(btnCancelar);

		this.setVisible(true);
	}



	public JTextField getTxtCorreo1() {
		return txtCorreo1;
	}



	public void setTxtCorreo1(JTextField txtCorreo1) {
		this.txtCorreo1 = txtCorreo1;
	}



	public JButton getBtnQuitarCorreoSeleccionado() {
		return btnQuitarCorreoSeleccionado;
	}



	public void setBtnQuitarCorreoSeleccionado(JButton btnQuitarCorreoSeleccionado) {
		this.btnQuitarCorreoSeleccionado = btnQuitarCorreoSeleccionado;
	}



	public JButton getBtnCancelar() {
		return btnCancelar;
	}



	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}



	public JTextField getTxtCorreo2() {
		return txtCorreo2;
	}



	public void setTxtCorreo2(JTextField txtCorreo2) {
		this.txtCorreo2 = txtCorreo2;
	}



	public JTextField getTxtCorreo3() {
		return txtCorreo3;
	}



	public void setTxtCorreo3(JTextField txtCorreo3) {
		this.txtCorreo3 = txtCorreo3;
	}



	public JTextField getTxtCorreo4() {
		return txtCorreo4;
	}



	public void setTxtCorreo4(JTextField txtCorreo4) {
		this.txtCorreo4 = txtCorreo4;
	}



	public JCheckBox getChkCorreo1() {
		return chkCorreo1;
	}



	public void setChkCorreo1(JCheckBox chkCorreo1) {
		this.chkCorreo1 = chkCorreo1;
	}


	

	public JCheckBox getChkCorreo2() {
		return chkCorreo2;
	}



	public void setChkCorreo2(JCheckBox chkCorreo2) {
		this.chkCorreo2 = chkCorreo2;
	}



	public JCheckBox getChkCorreo3() {
		return chkCorreo3;
	}



	public void setChkCorreo3(JCheckBox chkCorreo3) {
		this.chkCorreo3 = chkCorreo3;
	}



	public JCheckBox getChkCorreo4() {
		return chkCorreo4;
	}



	public void setChkCorreo4(JCheckBox chkCorreo4) {
		this.chkCorreo4 = chkCorreo4;
	}
}