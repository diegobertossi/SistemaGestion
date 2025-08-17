package presentacion.vista;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class VentanaOpcionesBackup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnGuardarLocal;
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
		setBounds(460, 260, 548, 354);

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnGuardarLocal = new JButton("GUARDAR LOCAL");
		btnGuardarLocal.setBounds(104, 278, 141, 34);
		panel.add(btnGuardarLocal);
		btnGuardarLocal.setForeground(new Color(0, 0, 0));
		btnGuardarLocal.setFont(new Font("Cambria", Font.BOLD, 12));

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(349, 278, 93, 34);
		panel.add(btnCancelar);
		btnCancelar.setForeground(new Color(0, 0, 0));
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 12));

		txtNombreArchivo = new JTextField();
		txtNombreArchivo.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128)), new EmptyBorder(0, 5, 0, 0)));
		txtNombreArchivo.setHorizontalAlignment(SwingConstants.LEFT);
		txtNombreArchivo.setEditable(false);
		txtNombreArchivo.setBounds(79, 115, 383, 25);
		panel.add(txtNombreArchivo);
		txtNombreArchivo.setBackground(new Color(255, 248, 220));
		txtNombreArchivo.setForeground(new Color(0, 0, 139));
		txtNombreArchivo.setFont(new Font("Cambria", Font.PLAIN, 12));

		txtRutaArchivo = new JTextField();
		txtRutaArchivo.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128)), new EmptyBorder(0, 5, 0, 0)));
		txtRutaArchivo.setHorizontalAlignment(SwingConstants.LEFT);
		txtRutaArchivo.setEditable(false);
		txtRutaArchivo.setBounds(79, 160, 383, 25);
		panel.add(txtRutaArchivo);
		txtRutaArchivo.setBackground(new Color(255, 248, 220));
		txtRutaArchivo.setForeground(new Color(0, 0, 139));
		txtRutaArchivo.setFont(new Font("Cambria", Font.PLAIN, 12));

		JLabel lblNewLabel = new JLabel("NOMBRE:");
		lblNewLabel.setBounds(79, 98, 99, 20);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 14));

		JLabel lblNewLabel_1 = new JLabel("DESTINO:");
		lblNewLabel_1.setBounds(79, 143, 99, 20);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 14));

		JLabel lblBackupSistema = new JLabel("BACKUP DEL SISTEMA");
		lblBackupSistema.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackupSistema.setBounds(110, 27, 319, 29);
		panel.add(lblBackupSistema);
		lblBackupSistema.setForeground(new Color(0, 0, 139));
		lblBackupSistema.setFont(new Font("Cambria", Font.BOLD, 26));

		JLabel img = new JLabel("");
		img.setBounds(40, 3, 70, 73);
		panel.add(img);
		img.setIcon(new ImageIcon(this.getClass().getResource("/data-backup.png")));

		btnCambiarNombre = new JButton("<html><center>CAMBIAR NOMBRE Y DESTINO LOCAL</html>");
		btnCambiarNombre.setForeground(new Color(0, 0, 0));
		btnCambiarNombre.setFont(new Font("Cambria", Font.BOLD, 12));
		btnCambiarNombre.setBounds(79, 212, 146, 34);
		panel.add(btnCambiarNombre);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(37, 79, 459, 2);
		panel.add(separator_1);
		
		JLabel img_1 = new JLabel("");
		img_1.setBounds(429, 3, 70, 73);
		panel.add(img_1);
		img_1.setIcon(new ImageIcon(this.getClass().getResource("/data-backup.png")));
		
		btnResetDatos = new JButton("<html><center>RESET DATOS</html>");
		btnResetDatos.setForeground(new Color(0, 0, 0));
		btnResetDatos.setFont(new Font("Cambria", Font.BOLD, 12));
		btnResetDatos.setBounds(295, 212, 167, 34);
		panel.add(btnResetDatos);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(37, 83, 459, 2);
		panel.add(separator_1_2);
		
		JSeparator separator_1_2_1 = new JSeparator();
		separator_1_2_1.setBounds(40, 260, 459, 2);
		panel.add(separator_1_2_1);
		
		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setBounds(40, 256, 459, 2);
		panel.add(separator_1_3);
		
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

	public JButton getBtnGuardarLocal() {
		return btnGuardarLocal;
	}

	public void setBtnGuardarLocal(JButton btnAceptar) {
		this.btnGuardarLocal = btnAceptar;
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
