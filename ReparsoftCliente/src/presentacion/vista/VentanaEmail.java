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

public class VentanaEmail extends JFrame {
	
	@SuppressWarnings("unused")
	private ControladorReparacion controlador;
	private static final long serialVersionUID = 1L;
	private JTextField textCliente;
	private JTextField textNombreContacto;
	private JTextField textEmailContacto;
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
	private JPanel panel_1;
	
	
	
	public VentanaEmail(){
		
		super();
		setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBounds(460, 260, 739, 517);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCliente = new JLabel("CLIENTE:");
		lblCliente.setForeground(new Color(0, 0, 128));
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		lblCliente.setBounds(37, 11, 103, 31);
		panel.add(lblCliente);
		
		textCliente = new JTextField();
		textCliente.setEditable(false);
		textCliente.setForeground(new Color(0, 0, 128));
		textCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		textCliente.setBorder(null);
		textCliente.setBackground(SystemColor.inactiveCaption);
		textCliente.setBounds(139, 11, 520, 31);
		panel.add(textCliente);
		textCliente.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(38, 50, 663, 2);
		panel.add(separator);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBounds(37, 57, 523, 65);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Contacto sugerido:");
		label.setBounds(10, 8, 139, 20);
		panel_1.add(label);
		label.setForeground(SystemColor.textHighlight);
		label.setFont(new Font("Cambria", Font.BOLD, 16));
		
		textNombreContacto = new JTextField();
		textNombreContacto.setEditable(false);
		textNombreContacto.setBounds(168, 8, 348, 20);
		panel_1.add(textNombreContacto);
		textNombreContacto.setForeground(SystemColor.textHighlight);
		textNombreContacto.setFont(new Font("Cambria", Font.BOLD, 16));
		textNombreContacto.setBorder(null);
		textNombreContacto.setBackground(SystemColor.inactiveCaption);
		textNombreContacto.setColumns(10);
		
		textEmailContacto = new JTextField();
		textEmailContacto.setEditable(false);
		textEmailContacto.setBounds(168, 34, 348, 20);
		panel_1.add(textEmailContacto);
		textEmailContacto.setForeground(SystemColor.textHighlight);
		textEmailContacto.setFont(new Font("Cambria", Font.BOLD, 16));
		textEmailContacto.setBorder(null);
		textEmailContacto.setBackground(SystemColor.inactiveCaption);
		textEmailContacto.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(38, 127, 663, 2);
		panel.add(separator_1);
		
		btnAgregarContacto = new JButton("AGREGAR");
		btnAgregarContacto.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAgregarContacto.setForeground(SystemColor.textHighlight);
		btnAgregarContacto.setBounds(570, 66, 89, 23);
		panel.add(btnAgregarContacto);
		
		JLabel labelPara = new JLabel("Para:");
		labelPara.setFont(new Font("Cambria", Font.BOLD, 14));
		labelPara.setBounds(37, 140, 133, 18);
		panel.add(labelPara);
		
		JLabel labelCC = new JLabel("CC:");
		labelCC.setFont(new Font("Cambria", Font.BOLD, 14));
		labelCC.setBounds(37, 165, 133, 18);
		panel.add(labelCC);
		
		JLabel labelArchivos = new JLabel("Archivos Adjuntos");
		labelArchivos.setFont(new Font("Cambria", Font.BOLD, 14));
		labelArchivos.setBounds(37, 240, 133, 18);
		panel.add(labelArchivos);
		
		JLabel labelAsunto = new JLabel("Asunto: ");
		labelAsunto.setFont(new Font("Cambria", Font.BOLD, 14));
		labelAsunto.setBounds(37, 190, 133, 18);
		panel.add(labelAsunto);
		
		JLabel labelAjunto = new JLabel("Informe Adjunto: ");
		labelAjunto.setFont(new Font("Cambria", Font.BOLD, 14));
		labelAjunto.setBounds(37, 215, 133, 18);
		panel.add(labelAjunto);
		
		JLabel labelCuerpo = new JLabel("Cuerpo");
		labelCuerpo.setFont(new Font("Cambria", Font.BOLD, 14));
		labelCuerpo.setBounds(37, 269, 133, 18);
		panel.add(labelCuerpo);
		
		textPara = new JTextField();
		textPara.setFont(new Font("Cambria", Font.PLAIN, 11));
		textPara.setColumns(10);
		textPara.setBounds(184, 139, 475, 20);
		panel.add(textPara);
		
		textCC = new JTextField();
		textCC.setFont(new Font("Cambria", Font.PLAIN, 11));
		textCC.setColumns(10);
		textCC.setBounds(184, 165, 475, 20);
		panel.add(textCC);
		
		textAsunto = new JTextField();
		textAsunto.setFont(new Font("Cambria", Font.PLAIN, 11));
		textAsunto.setColumns(10);
		textAsunto.setBounds(184, 190, 475, 20);
		panel.add(textAsunto);
		
		textArchivos = new JTextField();
		textArchivos.setFont(new Font("Cambria", Font.PLAIN, 11));
		textArchivos.setColumns(10);
		textArchivos.setBounds(184, 239, 475, 20);
		panel.add(textArchivos);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 303, 624, 115);
		panel.add(scrollPane);
		
		textCuerpo = new JTextArea();
		textCuerpo.setFont(new Font("Cambria", Font.PLAIN, 11));
		scrollPane.setViewportView(textCuerpo);
		textCuerpo.setEditable(false);
		textCuerpo.setColumns(10);
		
		textAdjunto = new JTextField();
		textAdjunto.setFont(new Font("Cambria", Font.PLAIN, 11));
		textAdjunto.setColumns(10);
		textAdjunto.setBounds(184, 215, 475, 20);
		panel.add(textAdjunto);
		
		btnAdjuntarArchivo = new JButton("<html><center>ADJUNTAR ARCHIVO<html>");
		btnAdjuntarArchivo.setFont(new Font("Cambria", Font.BOLD, 15));
		btnAdjuntarArchivo.setBounds(37, 429, 139, 38);
		panel.add(btnAdjuntarArchivo);
		
		btnAdjunto = new JButton("VER INFORME");
		btnAdjunto.setFont(new Font("Cambria", Font.BOLD, 15));
		btnAdjunto.setBounds(184, 429, 139, 38);
		panel.add(btnAdjunto);
		
		btnEditar = new JButton("EDITAR");
		btnEditar.setFont(new Font("Cambria", Font.BOLD, 15));
		btnEditar.setBounds(333, 429, 139, 38);
		panel.add(btnEditar);
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setForeground(new Color(0, 204, 102));
		btnEnviar.setFont(new Font("Cambria", Font.BOLD, 15));
		btnEnviar.setBounds(520, 429, 139, 38);
		panel.add(btnEnviar);
		
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
