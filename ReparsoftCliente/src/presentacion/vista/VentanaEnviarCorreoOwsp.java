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

public class VentanaEnviarCorreoOwsp extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnEnviarCorreo;
	private JButton btnEnviarWST;
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;

	public VentanaEnviarCorreoOwsp(ControladorReparacion controlador2) {
		super();
		setResizable(false);
		this.controlador = controlador2;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
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
		btnEnviarCorreo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEnviarCorreo.setBounds(39, 33, 100, 50);
		contentPane.add(btnEnviarCorreo);

		btnEnviarWST = new JButton("<html><center>ENVIAR WHATSAPP</html>");
		btnEnviarWST.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEnviarWST.setBounds(149, 33, 100, 50);
		contentPane.add(btnEnviarWST);

		this.setVisible(true);
	}

	public JButton getBtnEnviarWST() {
		return btnEnviarWST;
	}

	public void setBtnEnviarWST(JButton btnEnviarWST) {
		this.btnEnviarWST = btnEnviarWST;
	}

	public JButton getBtnEnviarCorreo() {
		return btnEnviarCorreo;
	}

	public void setBtnEnviarCorreo(JButton btnEnviarCorreo) {
		this.btnEnviarCorreo = btnEnviarCorreo;
	}

}
