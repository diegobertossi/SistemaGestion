package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.gestores.GestorVisualizacionEquipos;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class VentanaEnviarCorreoOwsp extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnEnviarCorreo;
	private JButton btnEnviarWSP;
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;

	public VentanaEnviarCorreoOwsp(ControladorReparacion controlador2) {
		super();
		setResizable(false);
		this.controlador = controlador2;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 306, 155);

		
		
		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnEnviarCorreo = new JButton("<html><center>ENVIAR CORREO</html>");
		btnEnviarCorreo.setForeground(new Color(255, 255, 255));
		btnEnviarCorreo.setBackground(new Color(100, 149, 237));
		btnEnviarCorreo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEnviarCorreo.setBounds(39, 33, 100, 50);
		contentPane.add(btnEnviarCorreo);

		btnEnviarWSP = new JButton("<html><center>ENVIAR WHATSAPP</html>");
		btnEnviarWSP.setForeground(new Color(255, 255, 255));
		btnEnviarWSP.setBackground(new Color(0, 128, 128));
		btnEnviarWSP.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEnviarWSP.setBounds(149, 33, 100, 50);
		contentPane.add(btnEnviarWSP);

		this.setVisible(true);
	}

	public JButton getBtnEnviarWSP() {
		return btnEnviarWSP;
	}

	public void setBtnEnviarWSP(JButton btnEnviarWSP) {
		this.btnEnviarWSP = btnEnviarWSP;
	}

	public JButton getBtnEnviarCorreo() {
		return btnEnviarCorreo;
	}

	public void setBtnEnviarCorreo(JButton btnEnviarCorreo) {
		this.btnEnviarCorreo = btnEnviarCorreo;
	}

}
