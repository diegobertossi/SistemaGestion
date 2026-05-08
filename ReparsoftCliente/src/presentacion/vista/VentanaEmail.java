package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;

import presentacion.controlador.ControladorReparacion;

import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class VentanaEmail extends JFrame {
	
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;
	private static final long serialVersionUID = 1L;
	private JTextField textCliente;
	private JTextField textPara;
	private JTextField textCC;
	private JTextField textAsunto;
	private JTextField textArchivos;
	private JTextArea textCuerpo;
	private JTextField textAdjunto;
	
	private JButton btnAgregarContacto;
	private JButton btnAdjuntarArchivo;
	private JButton btnAdjunto;
	private JButton btnEditar;
	private JButton btnEnviar;
	private JScrollPane scrollPane;
	private JLabel lblNombre;
	private JLabel lblTelfono;
	private JTextField textNombreContacto;
	private JTextField textEmailContacto;
	private JPanel panel_1;
	private JPanel panel_2;
	
	
	
	public VentanaEmail(){
		
		super();
		setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBounds(460, 260, 724, 569);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 149, 237));
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textCliente = new JTextField();
		textCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textCliente.setEditable(false);
		textCliente.setForeground(new Color(255, 255, 255));
		textCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		textCliente.setBorder(null);
		textCliente.setBackground(new Color(100, 149, 237));
		textCliente.setBounds(30, 11, 663, 31);
		panel.add(textCliente);
		textCliente.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(115, 43, 508, 2);
		panel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(115, 155, 508, 2);
		panel.add(separator_1);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBackground(new Color(100, 149, 237));
		panel_2.setBounds(38, 169, 643, 341);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel labelPara = new JLabel("Para:");
		labelPara.setBounds(10, 12, 151, 18);
		panel_2.add(labelPara);
		labelPara.setForeground(new Color(255, 255, 255));
		labelPara.setFont(new Font("Cambria", Font.BOLD, 16));
		
		JLabel labelCC = new JLabel("CC:");
		labelCC.setBounds(10, 38, 151, 18);
		panel_2.add(labelCC);
		labelCC.setForeground(new Color(255, 255, 255));
		labelCC.setFont(new Font("Cambria", Font.BOLD, 16));
		
		JLabel labelArchivos = new JLabel("Archivos Adjuntos");
		labelArchivos.setBounds(10, 112, 151, 18);
		panel_2.add(labelArchivos);
		labelArchivos.setForeground(new Color(255, 255, 255));
		labelArchivos.setFont(new Font("Cambria", Font.BOLD, 16));
		
		JLabel labelAsunto = new JLabel("Asunto: ");
		labelAsunto.setBounds(10, 63, 151, 18);
		panel_2.add(labelAsunto);
		labelAsunto.setForeground(new Color(255, 255, 255));
		labelAsunto.setFont(new Font("Cambria", Font.BOLD, 16));
		
		JLabel labelAjunto = new JLabel("Informe Adjunto: ");
		labelAjunto.setBounds(10, 88, 151, 18);
		panel_2.add(labelAjunto);
		labelAjunto.setForeground(new Color(255, 255, 255));
		labelAjunto.setFont(new Font("Cambria", Font.BOLD, 16));
		
		textPara = new JTextField();
		textPara.setBounds(160, 11, 359, 20);
		panel_2.add(textPara);
		textPara.setBackground(new Color(255, 255, 204));
		textPara.setFont(new Font("Cambria", Font.PLAIN, 11));
		textPara.setColumns(10);
		
		textCC = new JTextField();
		textCC.setBounds(160, 37, 359, 20);
		panel_2.add(textCC);
		textCC.setBackground(new Color(255, 255, 204));
		textCC.setFont(new Font("Cambria", Font.PLAIN, 11));
		textCC.setColumns(10);
		
		textAsunto = new JTextField();
		textAsunto.setBounds(160, 62, 359, 20);
		panel_2.add(textAsunto);
		textAsunto.setBackground(new Color(255, 255, 204));
		textAsunto.setFont(new Font("Cambria", Font.PLAIN, 11));
		textAsunto.setColumns(10);
		
		textArchivos = new JTextField();
		textArchivos.setBounds(160, 111, 359, 20);
		panel_2.add(textArchivos);
		textArchivos.setBackground(new Color(255, 255, 204));
		textArchivos.setFont(new Font("Cambria", Font.PLAIN, 11));
		textArchivos.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 160, 450, 170);
		panel_2.add(scrollPane);
		
		textCuerpo = new JTextArea();
		textCuerpo.setLocation(0, 157);
		textCuerpo.setSize(359, 113);
		textCuerpo.setBackground(new Color(255, 255, 204));
		textCuerpo.setFont(new Font("Cambria", Font.PLAIN, 11));
		scrollPane.setViewportView(textCuerpo);
		textCuerpo.setEditable(false);
		textCuerpo.setColumns(10);
		
		textAdjunto = new JTextField();
		textAdjunto.setBounds(160, 87, 359, 20);
		panel_2.add(textAdjunto);
		textAdjunto.setBackground(new Color(255, 255, 204));
		textAdjunto.setFont(new Font("Cambria", Font.PLAIN, 11));
		textAdjunto.setColumns(10);
		
		JLabel labelCuerpo = new JLabel("Mensaje:");
		labelCuerpo.setBounds(10, 134, 81, 18);
		panel_2.add(labelCuerpo);
		labelCuerpo.setForeground(Color.WHITE);
		labelCuerpo.setFont(new Font("Cambria", Font.BOLD, 16));
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setBounds(535, 283, 98, 47);
		panel_2.add(btnEnviar);
		btnEnviar.setBackground(new Color(204, 255, 255));
		btnEnviar.setForeground(new Color(0, 0, 0));
		btnEnviar.setFont(new Font("Cambria", Font.BOLD, 12));
		
		btnEditar = new JButton("EDITAR");
		btnEditar.setBounds(470, 189, 163, 25);
		panel_2.add(btnEditar);
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 12));
		
		btnAdjunto = new JButton("<html><center>VER INFORME<html>");
		btnAdjunto.setBounds(470, 217, 163, 25);
		panel_2.add(btnAdjunto);
		btnAdjunto.setFont(new Font("Cambria", Font.BOLD, 12));
		
		btnAdjuntarArchivo = new JButton("<html><center>ADJUNTAR ARCHIVO<html>");
		btnAdjuntarArchivo.setBounds(470, 160, 163, 25);
		panel_2.add(btnAdjuntarArchivo);
		btnAdjuntarArchivo.setFont(new Font("Cambria", Font.BOLD, 12));
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBackground(new Color(100, 149, 237));
		panel_1.setBounds(38, 55, 643, 93);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblContactoSugerido = new JLabel("CONTACTO SUGERIDO");
		lblContactoSugerido.setBounds(236, 0, 170, 20);
		panel_1.add(lblContactoSugerido);
		lblContactoSugerido.setForeground(Color.WHITE);
		lblContactoSugerido.setFont(new Font("Cambria", Font.BOLD, 16));
		
		lblNombre = new JLabel("Contacto");
		lblNombre.setBounds(10, 30, 84, 20);
		panel_1.add(lblNombre);
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font("Cambria", Font.BOLD, 16));
		
		lblTelfono = new JLabel("Correo");
		lblTelfono.setBounds(10, 55, 84, 20);
		panel_1.add(lblTelfono);
		lblTelfono.setForeground(Color.WHITE);
		lblTelfono.setFont(new Font("Cambria", Font.BOLD, 16));
		
		textNombreContacto = new JTextField();
		textNombreContacto.setBounds(160, 30, 359, 20);
		panel_1.add(textNombreContacto);
		textNombreContacto.setHorizontalAlignment(SwingConstants.CENTER);
		textNombreContacto.setForeground(Color.BLACK);
		textNombreContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		textNombreContacto.setEditable(false);
		textNombreContacto.setColumns(10);
		textNombreContacto.setBorder(null);
		textNombreContacto.setBackground(new Color(255, 255, 204));
		
		textEmailContacto = new JTextField();
		textEmailContacto.setBounds(160, 56, 359, 20);
		panel_1.add(textEmailContacto);
		textEmailContacto.setHorizontalAlignment(SwingConstants.CENTER);
		textEmailContacto.setForeground(Color.BLACK);
		textEmailContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		textEmailContacto.setEditable(false);
		textEmailContacto.setColumns(10);
		textEmailContacto.setBorder(null);
		textEmailContacto.setBackground(new Color(255, 255, 204));
		
		btnAgregarContacto = new JButton("UTILIZAR");
		btnAgregarContacto.setBounds(535, 28, 98, 47);
		panel_1.add(btnAgregarContacto);
		btnAgregarContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		btnAgregarContacto.setForeground(new Color(0, 0, 0));
		
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


	public JTextField getTextCliente() {
		return textCliente;
	}


	public void setTextCliente(JTextField textCliente) {
		this.textCliente = textCliente;
	}


	public JTextField getTextNombreContacto() {
		return textNombreContacto;
	}


	public void setTextNombreContacto(JTextField textNombreContacto) {
		this.textNombreContacto = textNombreContacto;
	}


	public JTextField getTextEmailContacto() {
		return textEmailContacto;
	}


	public void setTextEmailContacto(JTextField textEmailContacto) {
		this.textEmailContacto = textEmailContacto;
	}


	public JTextField getTextPara() {
		return textPara;
	}


	public void setTextPara(JTextField textPara) {
		this.textPara = textPara;
	}


	public JTextField getTextCC() {
		return textCC;
	}


	public void setTextCC(JTextField textCC) {
		this.textCC = textCC;
	}


	public JTextField getTextAsunto() {
		return textAsunto;
	}


	public void setTextAsunto(JTextField textAsunto) {
		this.textAsunto = textAsunto;
	}


	public JTextField getTextArchivos() {
		return textArchivos;
	}


	public void setTextArchivos(JTextField textImagenes) {
		this.textArchivos = textImagenes;
	}


	public JTextArea getTextCuerpo() {
		return textCuerpo;
	}


	public void setTextCuerpo(JTextArea textCuerpo) {
		this.textCuerpo = textCuerpo;
	}


	public JTextField getTextAdjunto() {
		return textAdjunto;
	}


	public void setTextAdjunto(JTextField textAdjunto) {
		this.textAdjunto = textAdjunto;
	}


	public JButton getBtnAgregarContacto() {
		return btnAgregarContacto;
	}


	public void setBtnAgregarContacto(JButton btnAgregarContacto) {
		this.btnAgregarContacto = btnAgregarContacto;
	}


	public JButton getBtnAdjuntarArchivo() {
		return btnAdjuntarArchivo;
	}


	public void setBtnAdjuntarArchivo(JButton btnAdjuntarIMG) {
		this.btnAdjuntarArchivo = btnAdjuntarIMG;
	}


	public JButton getBtnAdjunto() {
		return btnAdjunto;
	}


	public void setBtnAdjunto(JButton btnAdjunto) {
		this.btnAdjunto = btnAdjunto;
	}


	public JButton getBtnEditar() {
		return btnEditar;
	}


	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}


	public JButton getBtnEnviar() {
		return btnEnviar;
	}


	public void setBtnEnviar(JButton btnEnviar) {
		this.btnEnviar = btnEnviar;
	}
}
