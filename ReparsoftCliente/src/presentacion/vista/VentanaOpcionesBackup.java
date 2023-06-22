package presentacion.vista;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class VentanaOpcionesBackup extends JFrame {

	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTextField txtNombreArchivo;
	private JTextField txtRutaArchivo;
	private JButton btnCambiarNombre;
	private JButton btnResetDatos;

	public VentanaOpcionesBackup() {

		super();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBounds(460, 260, 534, 337);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(114, 268, 120, 29);
		panel.add(btnAceptar);
		btnAceptar.setForeground(new Color(0, 0, 128));
		btnAceptar.setFont(new Font("Calibri", Font.BOLD, 16));

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(272, 268, 120, 29);
		panel.add(btnCancelar);
		btnCancelar.setForeground(new Color(0, 0, 128));
		btnCancelar.setFont(new Font("Calibri", Font.BOLD, 16));

		txtNombreArchivo = new JTextField();
		txtNombreArchivo.setEditable(false);
		txtNombreArchivo.setBounds(25, 128, 383, 20);
		panel.add(txtNombreArchivo);
		txtNombreArchivo.setBackground(new Color(0, 102, 153));
		txtNombreArchivo.setForeground(new Color(255, 255, 255));
		txtNombreArchivo.setFont(new Font("Consolas", Font.BOLD, 12));

		txtRutaArchivo = new JTextField();
		txtRutaArchivo.setEditable(false);
		txtRutaArchivo.setBounds(25, 199, 383, 20);
		panel.add(txtRutaArchivo);
		txtRutaArchivo.setBackground(new Color(0, 102, 153));
		txtRutaArchivo.setForeground(new Color(255, 255, 255));
		txtRutaArchivo.setFont(new Font("Consolas", Font.BOLD, 12));

		JLabel lblNewLabel = new JLabel("Se generará el siguiente archivo de backup:");
		lblNewLabel.setBounds(27, 106, 275, 20);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 14));

		JLabel lblNewLabel_1 = new JLabel("Se guardará en el siguiente destino:");
		lblNewLabel_1.setBounds(27, 176, 295, 20);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(0, 0, 128));
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 14));

		JLabel lblBackupSistema = new JLabel("BACKUP DEL SISTEMA");
		lblBackupSistema.setBounds(147, 35, 243, 29);
		panel.add(lblBackupSistema);
		lblBackupSistema.setForeground(new Color(0, 0, 205));
		lblBackupSistema.setFont(new Font("Calibri", Font.BOLD, 26));

		JLabel img = new JLabel("");
		img.setBounds(40, 7, 70, 73);
		panel.add(img);
		img.setIcon(new ImageIcon(this.getClass().getResource("/data-backup.png")));

		btnCambiarNombre = new JButton("<html><center>CAMBIAR NOMBRE Y DESTINO</html>");
		btnCambiarNombre.setForeground(new Color(0, 0, 128));
		btnCambiarNombre.setFont(new Font("Calibri", Font.BOLD, 12));
		btnCambiarNombre.setBounds(425, 112, 89, 51);
		panel.add(btnCambiarNombre);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(40, 83, 459, 2);
		panel.add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(40, 250, 459, 2);
		panel.add(separator_1_1);
		
		JLabel img_1 = new JLabel("");
		img_1.setBounds(429, 7, 70, 73);
		panel.add(img_1);
		img_1.setIcon(new ImageIcon(this.getClass().getResource("/data-backup.png")));
		
		btnResetDatos = new JButton("<html><center>RESET DATOS</html>");
		btnResetDatos.setForeground(new Color(0, 0, 128));
		btnResetDatos.setFont(new Font("Calibri", Font.BOLD, 12));
		btnResetDatos.setBounds(425, 185, 89, 51);
		panel.add(btnResetDatos);
		
		setLocationCenter();
		setVisible(true);

	}

	public void setLocationCenter() {
		setLocationMove(0, 0);
	}

	public void setLocationMove(int moveWidth, int moveHeight) {
		// Obtenemos el tama�o de la pantalla.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Obtenemos el tama�o de nuestro frame.
		Dimension frameSize = this.getSize();
		frameSize.width = frameSize.width > screenSize.width ? screenSize.width : frameSize.width;
		frameSize.height = frameSize.height > screenSize.height ? screenSize.height : frameSize.height;
		// We define the location. Definimos la localizaci�n.
		setLocation((screenSize.width - frameSize.width) / 2 + moveWidth,
				(screenSize.height - frameSize.height) / 2 + moveHeight);
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

	public JTextField getTxtNombreArchivo() {
		return txtNombreArchivo;
	}

	public void setTxtNombreArchivo(JTextField txtNombreArchivo) {
		this.txtNombreArchivo = txtNombreArchivo;
	}

	public JTextField getTxtRutaArchivo() {
		return txtRutaArchivo;
	}

	public void setTxtRutaArchivo(JTextField txtRutaArchivo) {
		this.txtRutaArchivo = txtRutaArchivo;
	}

	public JButton getBtnCambiarNombre() {
		return btnCambiarNombre;
	}

	public void setBtnCambiarNombre(JButton btnCambiarNombre) {
		this.btnCambiarNombre = btnCambiarNombre;
	}

	public JButton getBtnResetDatos() {
		return btnResetDatos;
	}

	public void setBtnResetDatos(JButton btnResetDatos) {
		this.btnResetDatos = btnResetDatos;
	}
}
