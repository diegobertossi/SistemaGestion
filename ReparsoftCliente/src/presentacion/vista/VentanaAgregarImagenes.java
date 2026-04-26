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
import java.awt.SystemColor;
import java.awt.Toolkit;

import presentacion.controlador.ControladorPresupuestos;

import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class VentanaAgregarImagenes extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btngenerarInforme;
	private JButton btnCancelar;
	private JButton btnBorrarImagen_1;
	private JButton btnBorrarImagen_2;
	private JButton btnBorrarImagen_3;
	private JButton btnAgregarImagen;
	private JButton btnAgregarImagenDiagnostico;
	private JButton btnBorrarImagen_4;
	private JButton btnBorrarImagen_5;
	private JButton btnBorrarImagen_6;
	
	
	
	private JTextField txtRutaImagen_1;
	private JTextField txtRutaImagen_2;
	private JTextField txtRutaImagen_3;
	private JTextField txtRutaImagen_4;
	private JTextField txtRutaImagen_5;
	private JTextField txtRutaImagen_6;

	public VentanaAgregarImagenes(ControladorPresupuestos controladorPresupuestos ) {

		super();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBounds(460, 260, 678, 558);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);


		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);

		btngenerarInforme = new JButton("<html><center>GENERAR INFORME</html>");
		btngenerarInforme.setBounds(394, 493, 174, 29);
		panel.add(btngenerarInforme);
		btngenerarInforme.setForeground(new Color(0, 0, 128));
		btngenerarInforme.setFont(new Font("Calibri", Font.BOLD, 16));

		btnCancelar = new JButton("<html><center>CANCELAR</html>");
		btnCancelar.setBounds(110, 493, 174, 29);
		panel.add(btnCancelar);
		btnCancelar.setForeground(new Color(0, 0, 128));
		btnCancelar.setFont(new Font("Calibri", Font.BOLD, 16));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("InternalFrame.border"));
		panel_1.setBounds(40, 101, 573, 163);
		panel.add(panel_1);
		panel_1.setLayout(null);

		txtRutaImagen_1 = new JTextField();
		txtRutaImagen_1.setEditable(false);
		txtRutaImagen_1.setBounds(76, 59, 417, 20);
		panel_1.add(txtRutaImagen_1);
		txtRutaImagen_1.setBackground(new Color(250, 250, 210));
		txtRutaImagen_1.setForeground(Color.BLACK);
		txtRutaImagen_1.setFont(new Font("Cambria", Font.PLAIN, 10));

		txtRutaImagen_2 = new JTextField();
		txtRutaImagen_2.setEditable(false);
		txtRutaImagen_2.setBounds(76, 90, 417, 20);
		panel_1.add(txtRutaImagen_2);
		txtRutaImagen_2.setBackground(new Color(250, 250, 210));
		txtRutaImagen_2.setForeground(Color.BLACK);
		txtRutaImagen_2.setFont(new Font("Cambria", Font.PLAIN, 10));
		
		btnBorrarImagen_1 = new JButton();
		btnBorrarImagen_1.setBounds(513, 57, 37, 25);
		btnBorrarImagen_1.setIcon(new ImageIcon(this.getClass().getResource("/eliminar.png")));
		panel_1.add(btnBorrarImagen_1);
		btnBorrarImagen_1.setForeground(new Color(0, 0, 128));
		btnBorrarImagen_1.setFont(new Font("Calibri", Font.BOLD, 12));
		
		txtRutaImagen_3 = new JTextField();
		txtRutaImagen_3.setEditable(false);
		txtRutaImagen_3.setBounds(76, 121, 417, 20);
		panel_1.add(txtRutaImagen_3);
		txtRutaImagen_3.setForeground(Color.BLACK);
		txtRutaImagen_3.setFont(new Font("Cambria", Font.PLAIN, 10));
		txtRutaImagen_3.setBackground(new Color(250, 250, 210));
		
		JLabel lblNewLabel = new JLabel("IMAGEN 1");
		lblNewLabel.setBounds(15, 59, 64, 20);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblImagen = new JLabel("IMAGEN 2");
		lblImagen.setBounds(15, 90, 64, 20);
		panel_1.add(lblImagen);
		lblImagen.setFont(new Font("Cambria", Font.BOLD, 12));
		
		JLabel lblImagen_1 = new JLabel("IMAGEN 3");
		lblImagen_1.setBounds(15, 120, 64, 20);
		panel_1.add(lblImagen_1);
		lblImagen_1.setFont(new Font("Cambria", Font.BOLD, 12));
		
		btnAgregarImagen = new JButton("<html><center>AGREGAR IMÁGEN</html>");
		btnAgregarImagen.setBounds(15, 13, 134, 29);
		panel_1.add(btnAgregarImagen);
		btnAgregarImagen.setForeground(new Color(0, 0, 128));
		btnAgregarImagen.setFont(new Font("Calibri", Font.BOLD, 12));
		
		btnBorrarImagen_2 = new JButton();
		btnBorrarImagen_2.setBounds(513, 88, 37, 25);
		btnBorrarImagen_2.setIcon(new ImageIcon(this.getClass().getResource("/eliminar.png")));
		panel_1.add(btnBorrarImagen_2);
		btnBorrarImagen_2.setForeground(new Color(0, 0, 128));
		btnBorrarImagen_2.setFont(new Font("Calibri", Font.BOLD, 12));
		
		btnBorrarImagen_3 = new JButton();
		btnBorrarImagen_3.setBounds(513, 119, 37, 25);
		btnBorrarImagen_3.setIcon(new ImageIcon(this.getClass().getResource("/eliminar.png")));
		panel_1.add(btnBorrarImagen_3);
		btnBorrarImagen_3.setForeground(new Color(0, 0, 128));
		btnBorrarImagen_3.setFont(new Font("Calibri", Font.BOLD, 12));
		
		JLabel lblBorrarImgen = new JLabel("<html><center>BORRAR IMÁGEN</html>");
		lblBorrarImgen.setBounds(499, 17, 64, 29);
		panel_1.add(lblBorrarImgen);
		lblBorrarImgen.setFont(new Font("Cambria", Font.BOLD, 12));

		JLabel lblBackupSistema = new JLabel("DATOS PARA EL INFORME");
		lblBackupSistema.setHorizontalAlignment(SwingConstants.LEFT);
		lblBackupSistema.setBounds(40, 24, 298, 29);
		panel.add(lblBackupSistema);
		lblBackupSistema.setForeground(new Color(0, 0, 205));
		lblBackupSistema.setFont(new Font("Calibri", Font.BOLD, 26));

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(40, 51, 459, 2);
		panel.add(separator_1);
		
		JLabel lblRecepcin = new JLabel("RECEPCIÓN");
		lblRecepcin.setFont(new Font("Cambria", Font.BOLD, 14));
		lblRecepcin.setBounds(40, 77, 82, 20);
		panel.add(lblRecepcin);
		
		JLabel lblDiagnstico = new JLabel("DIAGNÓSTICO");
		lblDiagnstico.setFont(new Font("Cambria", Font.BOLD, 14));
		lblDiagnstico.setBounds(40, 275, 100, 20);
		panel.add(lblDiagnstico);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(UIManager.getBorder("InternalFrame.border"));
		panel_1_1.setBounds(40, 299, 573, 163);
		panel.add(panel_1_1);
		
		txtRutaImagen_4 = new JTextField();
		txtRutaImagen_4.setEditable(false);
		txtRutaImagen_4.setForeground(Color.BLACK);
		txtRutaImagen_4.setFont(new Font("Cambria", Font.PLAIN, 10));
		txtRutaImagen_4.setBackground(new Color(250, 250, 210));
		txtRutaImagen_4.setBounds(76, 59, 417, 20);
		panel_1_1.add(txtRutaImagen_4);
		
		txtRutaImagen_5 = new JTextField();
		txtRutaImagen_5.setEditable(false);
		txtRutaImagen_5.setForeground(Color.BLACK);
		txtRutaImagen_5.setFont(new Font("Cambria", Font.PLAIN, 10));
		txtRutaImagen_5.setBackground(new Color(250, 250, 210));
		txtRutaImagen_5.setBounds(76, 90, 417, 20);
		panel_1_1.add(txtRutaImagen_5);
		
		txtRutaImagen_6 = new JTextField();
		txtRutaImagen_6.setEditable(false);
		txtRutaImagen_6.setForeground(Color.BLACK);
		txtRutaImagen_6.setFont(new Font("Cambria", Font.PLAIN, 10));
		txtRutaImagen_6.setBackground(new Color(250, 250, 210));
		txtRutaImagen_6.setBounds(76, 121, 417, 20);
		panel_1_1.add(txtRutaImagen_6);
		
		JLabel lblNewLabel_1 = new JLabel("IMAGEN 1");
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblNewLabel_1.setBounds(15, 59, 64, 20);
		panel_1_1.add(lblNewLabel_1);
		
		JLabel lblImagen_2 = new JLabel("IMAGEN 2");
		lblImagen_2.setFont(new Font("Cambria", Font.BOLD, 12));
		lblImagen_2.setBounds(15, 90, 64, 20);
		panel_1_1.add(lblImagen_2);
		
		JLabel lblImagen_1_1 = new JLabel("IMAGEN 3");
		lblImagen_1_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblImagen_1_1.setBounds(15, 120, 64, 20);
		panel_1_1.add(lblImagen_1_1);
		
		btnAgregarImagenDiagnostico = new JButton("<html><center>AGREGAR IMÁGEN</html>");
		btnAgregarImagenDiagnostico.setForeground(new Color(0, 0, 128));
		btnAgregarImagenDiagnostico.setFont(new Font("Calibri", Font.BOLD, 12));
		btnAgregarImagenDiagnostico.setBounds(15, 14, 134, 29);
		panel_1_1.add(btnAgregarImagenDiagnostico);
		
		btnBorrarImagen_5 = new JButton();
		btnBorrarImagen_5.setForeground(new Color(0, 0, 128));
		btnBorrarImagen_5.setIcon(new ImageIcon(this.getClass().getResource("/eliminar.png")));
		btnBorrarImagen_5.setFont(new Font("Calibri", Font.BOLD, 12));
		btnBorrarImagen_5.setBounds(513, 88, 37, 25);
		panel_1_1.add(btnBorrarImagen_5);
		
		btnBorrarImagen_6 = new JButton();
		btnBorrarImagen_6.setForeground(new Color(0, 0, 128));
		btnBorrarImagen_6.setIcon(new ImageIcon(this.getClass().getResource("/eliminar.png")));
		btnBorrarImagen_6.setFont(new Font("Calibri", Font.BOLD, 12));
		btnBorrarImagen_6.setBounds(513, 119, 37, 25);
		panel_1_1.add(btnBorrarImagen_6);
		
		JLabel lblBorrarImgen_1 = new JLabel("<html><center>BORRAR IMÁGEN</html>");
		lblBorrarImgen_1.setFont(new Font("Cambria", Font.BOLD, 12));
		lblBorrarImgen_1.setBounds(499, 19, 64, 29);
		panel_1_1.add(lblBorrarImgen_1);
		
		btnBorrarImagen_4 = new JButton();
		btnBorrarImagen_4.setForeground(new Color(0, 0, 128));
		btnBorrarImagen_4.setIcon(new ImageIcon(this.getClass().getResource("/eliminar.png")));
		btnBorrarImagen_4.setFont(new Font("Calibri", Font.BOLD, 12));
		btnBorrarImagen_4.setBounds(513, 57, 37, 25);
		panel_1_1.add(btnBorrarImagen_4);

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

	public JButton getBtngenerarInforme() {
		return btngenerarInforme;
	}

	public void setBtngenerarInforme(JButton btngenerarInforme) {
		this.btngenerarInforme = btngenerarInforme;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnBorrarImagen_1() {
		return btnBorrarImagen_1;
	}

	public void setBtnBorrarImagen_1(JButton btnBorrarImagen_1) {
		this.btnBorrarImagen_1 = btnBorrarImagen_1;
	}

	public JButton getBtnBorrarImagen_2() {
		return btnBorrarImagen_2;
	}

	public void setBtnBorrarImagen_2(JButton btnBorrarImagen_2) {
		this.btnBorrarImagen_2 = btnBorrarImagen_2;
	}

	public JButton getBtnBorrarImagen_3() {
		return btnBorrarImagen_3;
	}

	public void setBtnBorrarImagen_3(JButton btnBorrarImagen_3) {
		this.btnBorrarImagen_3 = btnBorrarImagen_3;
	}

	public JButton getBtnAgregarImagen() {
		return btnAgregarImagen;
	}

	public void setBtnAgregarImagen(JButton btnAgregarImagen) {
		this.btnAgregarImagen = btnAgregarImagen;
	}

	public JButton getBtnAgregarImagenDiagnostico() {
		return btnAgregarImagenDiagnostico;
	}

	public void setBtnAgregarImagenDiagnostico(JButton btnAgregarImagenDiagnostico) {
		this.btnAgregarImagenDiagnostico = btnAgregarImagenDiagnostico;
	}

	public JButton getBtnBorrarImagen_4() {
		return btnBorrarImagen_4;
	}

	public void setBtnBorrarImagen_4(JButton btnBorrarImagen_4) {
		this.btnBorrarImagen_4 = btnBorrarImagen_4;
	}

	public JButton getBtnBorrarImagen_5() {
		return btnBorrarImagen_5;
	}

	public void setBtnBorrarImagen_5(JButton btnBorrarImagen_5) {
		this.btnBorrarImagen_5 = btnBorrarImagen_5;
	}

	public JButton getBtnBorrarImagen_6() {
		return btnBorrarImagen_6;
	}

	public void setBtnBorrarImagen_6(JButton btnBorrarImagen_6) {
		this.btnBorrarImagen_6 = btnBorrarImagen_6;
	}

	public JTextField getTxtRutaImagen_1() {
		return txtRutaImagen_1;
	}

	public void setTxtRutaImagen_1(JTextField txtRutaImagen_1) {
		this.txtRutaImagen_1 = txtRutaImagen_1;
	}

	public JTextField getTxtRutaImagen_2() {
		return txtRutaImagen_2;
	}

	public void setTxtRutaImagen_2(JTextField txtRutaImagen_2) {
		this.txtRutaImagen_2 = txtRutaImagen_2;
	}

	public JTextField getTxtRutaImagen_3() {
		return txtRutaImagen_3;
	}

	public void setTxtRutaImagen_3(JTextField txtRutaImagen_3) {
		this.txtRutaImagen_3 = txtRutaImagen_3;
	}

	public JTextField getTxtRutaImagen_4() {
		return txtRutaImagen_4;
	}

	public void setTxtRutaImagen_4(JTextField txtRutaImagen_4) {
		this.txtRutaImagen_4 = txtRutaImagen_4;
	}

	public JTextField getTxtRutaImagen_5() {
		return txtRutaImagen_5;
	}

	public void setTxtRutaImagen_5(JTextField txtRutaImagen_5) {
		this.txtRutaImagen_5 = txtRutaImagen_5;
	}

	public JTextField getTxtRutaImagen_6() {
		return txtRutaImagen_6;
	}

	public void setTxtRutaImagen_6(JTextField txtRutaImagen_6) {
		this.txtRutaImagen_6 = txtRutaImagen_6;
	}


}
