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

import presentacion.controlador.ControladorReparacion;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class VentanaEmail extends JFrame {
	
	private ControladorReparacion controlador;
	private static final long serialVersionUID = 1L;
	private JTextField textCliente;
	private JTextField textNombreContacto;
	private JTextField textEmailContacto;
	private JTextField textPara;
	private JTextField textCC;
	private JTextField textAsunto;
	private JTextField textImagenes;
	private JTextArea textCuerpo;
	private JTextField textAdjunto;
	
	private JButton btnAgregarContacto;
	private JButton btnAdjuntarIMG;
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
		setBounds(460, 260, 739, 499);
		
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
		textNombreContacto.setBounds(168, 8, 348, 20);
		panel_1.add(textNombreContacto);
		textNombreContacto.setForeground(SystemColor.textHighlight);
		textNombreContacto.setFont(new Font("Cambria", Font.BOLD, 16));
		textNombreContacto.setBorder(null);
		textNombreContacto.setBackground(SystemColor.inactiveCaption);
		textNombreContacto.setColumns(10);
		
		textEmailContacto = new JTextField();
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
		labelPara.setBounds(37, 140, 35, 18);
		panel.add(labelPara);
		
		JLabel labelCC = new JLabel("CC:");
		labelCC.setFont(new Font("Cambria", Font.BOLD, 14));
		labelCC.setBounds(37, 165, 20, 18);
		panel.add(labelCC);
		
		JLabel labelImagenes = new JLabel("Imagenes:");
		labelImagenes.setFont(new Font("Cambria", Font.BOLD, 14));
		labelImagenes.setBounds(37, 240, 64, 18);
		panel.add(labelImagenes);
		
		JLabel labelAsunto = new JLabel("Asunto: ");
		labelAsunto.setFont(new Font("Cambria", Font.BOLD, 14));
		labelAsunto.setBounds(37, 190, 51, 18);
		panel.add(labelAsunto);
		
		JLabel labelAjunto = new JLabel("Adjunto: ");
		labelAjunto.setFont(new Font("Cambria", Font.BOLD, 14));
		labelAjunto.setBounds(37, 215, 57, 18);
		panel.add(labelAjunto);
		
		JLabel labelCuerpo = new JLabel("Cuerpo");
		labelCuerpo.setFont(new Font("Cambria", Font.BOLD, 14));
		labelCuerpo.setBounds(37, 311, 45, 18);
		panel.add(labelCuerpo);
		
		textPara = new JTextField();
		textPara.setFont(new Font("Cambria", Font.PLAIN, 11));
		textPara.setColumns(10);
		textPara.setBounds(128, 139, 360, 20);
		panel.add(textPara);
		
		textCC = new JTextField();
		textCC.setFont(new Font("Cambria", Font.PLAIN, 11));
		textCC.setColumns(10);
		textCC.setBounds(128, 165, 360, 20);
		panel.add(textCC);
		
		textAsunto = new JTextField();
		textAsunto.setFont(new Font("Cambria", Font.PLAIN, 11));
		textAsunto.setColumns(10);
		textAsunto.setBounds(128, 190, 360, 20);
		panel.add(textAsunto);
		
		textImagenes = new JTextField();
		textImagenes.setFont(new Font("Cambria", Font.PLAIN, 11));
		textImagenes.setColumns(10);
		textImagenes.setBounds(128, 240, 531, 65);
		panel.add(textImagenes);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(128, 311, 531, 94);
		panel.add(scrollPane);
		
		textCuerpo = new JTextArea();
		textCuerpo.setFont(new Font("Cambria", Font.PLAIN, 11));
		scrollPane.setViewportView(textCuerpo);
		textCuerpo.setEditable(false);
		textCuerpo.setColumns(10);
		
		textAdjunto = new JTextField();
		textAdjunto.setFont(new Font("Cambria", Font.PLAIN, 11));
		textAdjunto.setColumns(10);
		textAdjunto.setBounds(128, 215, 531, 20);
		panel.add(textAdjunto);
		
		btnAdjuntarIMG = new JButton("ADJUNTAR IMÁGENES");
		btnAdjuntarIMG.setBounds(27, 429, 185, 23);
		panel.add(btnAdjuntarIMG);
		
		btnAdjunto = new JButton("VER ADJUNTO");
		btnAdjunto.setBounds(239, 429, 139, 23);
		panel.add(btnAdjunto);
		
		btnEditar = new JButton("EDITAR");
		btnEditar.setBounds(405, 429, 139, 23);
		panel.add(btnEditar);
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setForeground(new Color(0, 204, 102));
		btnEnviar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEnviar.setBounds(571, 429, 139, 23);
		panel.add(btnEnviar);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(38, 156, 80, 2);
		panel.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(37, 181, 80, 2);
		panel.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(37, 206, 80, 2);
		panel.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(37, 231, 80, 2);
		panel.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(37, 257, 80, 2);
		panel.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(37, 327, 80, 2);
		panel.add(separator_7);
		
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


	public JTextField getTextImagenes() {
		return textImagenes;
	}


	public void setTextImagenes(JTextField textImagenes) {
		this.textImagenes = textImagenes;
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


	public JButton getBtnAdjuntarIMG() {
		return btnAdjuntarIMG;
	}


	public void setBtnAdjuntarIMG(JButton btnAdjuntarIMG) {
		this.btnAdjuntarIMG = btnAdjuntarIMG;
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
