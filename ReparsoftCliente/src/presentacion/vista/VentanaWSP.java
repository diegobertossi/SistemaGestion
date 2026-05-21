package presentacion.vista;

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

import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.gestores.GestorVisualizacionEquipos;

import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class VentanaWSP extends JFrame {
	
	@SuppressWarnings("unused")
	private GestorVisualizacionEquipos controlador;
	private static final long serialVersionUID = 1L;
	private JTextField textCliente;
	private JTextField textNombreContacto;
	private JTextField textNumeroContacto;
	private JTextField textNumero;
	private JTextArea textMensaje;
	
	private JButton btnUtilizarContacto;
	private JButton btnEnviar;	
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JButton btnEditarNmero;
	
	
	
	@SuppressWarnings("rawtypes")
	public VentanaWSP(GestorVisualizacionEquipos gestorVisualizacionEquipos){
		
		super();
		setResizable(false);
		this.controlador = gestorVisualizacionEquipos;
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBounds(460, 260, 535, 351);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(180, 215, 190));
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textCliente = new JTextField();
		textCliente.setHorizontalAlignment(SwingConstants.CENTER);
		textCliente.setForeground(new Color(50, 95, 65));
		textCliente.setFont(new Font("Cambria", Font.BOLD, 20));
		textCliente.setBorder(null);
		textCliente.setBackground(new Color(180, 215, 190));
		textCliente.setBounds(37, 3, 461, 31);
		panel.add(textCliente);
		textCliente.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(54, 39, 426, 2);
		panel.add(separator);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(180, 215, 190));
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(37, 48, 461, 92);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblContactoSugerido = new JLabel("CONTACTO SUGERIDO");
		lblContactoSugerido.setBounds(145, 5, 170, 20);
		panel_1.add(lblContactoSugerido);
		lblContactoSugerido.setForeground(new Color(50, 95, 65));
		lblContactoSugerido.setFont(new Font("Cambria", Font.BOLD, 16));
		
		textNombreContacto = new JTextField();
		textNombreContacto.setHorizontalAlignment(SwingConstants.CENTER);
		textNombreContacto.setEditable(false);
		textNombreContacto.setBounds(94, 36, 244, 20);
		panel_1.add(textNombreContacto);
		textNombreContacto.setForeground(new Color(50, 80, 60));
		textNombreContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		textNombreContacto.setBorder(null);
		textNombreContacto.setBackground(new Color(235, 250, 240));
		textNombreContacto.setColumns(10);
		
		textNumeroContacto = new JTextField();
		textNumeroContacto.setHorizontalAlignment(SwingConstants.CENTER);
		textNumeroContacto.setEditable(false);
		textNumeroContacto.setBounds(94, 61, 244, 20);
		panel_1.add(textNumeroContacto);
		textNumeroContacto.setForeground(new Color(50, 80, 60));
		textNumeroContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		textNumeroContacto.setBorder(null);
		textNumeroContacto.setBackground(new Color(235, 250, 240));
		textNumeroContacto.setColumns(10);
		
		JLabel lblNombre_1 = new JLabel("Contacto");
		lblNombre_1.setForeground(new Color(50, 95, 65));
		lblNombre_1.setFont(new Font("Cambria", Font.BOLD, 16));
		lblNombre_1.setBounds(10, 36, 84, 20);
		panel_1.add(lblNombre_1);
		
		JLabel lblTelfono_1 = new JLabel("Teléfono");
		lblTelfono_1.setForeground(new Color(50, 95, 65));
		lblTelfono_1.setFont(new Font("Cambria", Font.BOLD, 16));
		lblTelfono_1.setBounds(10, 61, 84, 20);
		panel_1.add(lblTelfono_1);
		
		btnUtilizarContacto = new JButton("<html>UTILIZAR</html>");
		btnUtilizarContacto.setBounds(355, 34, 100, 47);
		panel_1.add(btnUtilizarContacto);
		btnUtilizarContacto.setBackground(new Color(65, 145, 85));
		btnUtilizarContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		btnUtilizarContacto.setForeground(Color.WHITE);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(54, 148, 426, 2);
		panel.add(separator_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(180, 215, 190));
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(37, 158, 461, 146);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel labelPara = new JLabel("Número:");
		labelPara.setBounds(10, 17, 81, 18);
		panel_2.add(labelPara);
		labelPara.setForeground(new Color(50, 95, 65));
		labelPara.setFont(new Font("Cambria", Font.BOLD, 16));
		
		JLabel labelCuerpo = new JLabel("Mensaje:");
		labelCuerpo.setBounds(10, 46, 81, 18);
		panel_2.add(labelCuerpo);
		labelCuerpo.setForeground(new Color(50, 95, 65));
		labelCuerpo.setFont(new Font("Cambria", Font.BOLD, 16));
		
		textNumero = new JTextField();
		textNumero.setBackground(new Color(235, 250, 240));
		textNumero.setBounds(94, 16, 244, 20);
		panel_2.add(textNumero);
		textNumero.setEditable(false);
		textNumero.setFont(new Font("Cambria", Font.PLAIN, 12));
		textNumero.setColumns(10);
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setBackground(new Color(65, 145, 85));
		btnEnviar.setBounds(355, 90, 100, 47);
		panel_2.add(btnEnviar);
		btnEnviar.setForeground(Color.WHITE);
		btnEnviar.setFont(new Font("Cambria", Font.BOLD, 12));
		
		btnEditarNmero = new JButton("<html>EDITAR</html>");
		btnEditarNmero.setBounds(355, 16, 100, 20);
		panel_2.add(btnEditarNmero);
		btnEditarNmero.setBackground(new Color(65, 145, 85));
		btnEditarNmero.setForeground(Color.WHITE);
		btnEditarNmero.setFont(new Font("Cambria", Font.BOLD, 12));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(94, 45, 244, 92);
		panel_2.add(scrollPane);
		
		textMensaje = new JTextArea();
		textMensaje.setLocation(0, 46);
		textMensaje.setBackground(new Color(235, 250, 240));
		textMensaje.setFont(new Font("Cambria", Font.PLAIN, 12));
		scrollPane.setViewportView(textMensaje);
		textMensaje.setColumns(10);
		
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


	public JTextField getTextNumeroContacto() {
		return textNumeroContacto;
	}


	public void setTextNumeroContacto(JTextField textNumeroContacto) {
		this.textNumeroContacto = textNumeroContacto;
	}


	public JTextField getTextNumero() {
		return textNumero;
	}


	public void setTextNumero(JTextField textNumero) {
		this.textNumero = textNumero;
	}


	public JTextArea getTextMensaje() {
		return textMensaje;
	}


	public void setTextMensaje(JTextArea textMensaje) {
		this.textMensaje = textMensaje;
	}


	public JButton getBtnAgregarContacto() {
		return btnUtilizarContacto;
	}


	public void setBtnAgregarContacto(JButton btnAgregarContacto) {
		this.btnUtilizarContacto = btnAgregarContacto;
	}


	public JButton getBtnEnviar() {
		return btnEnviar;
	}


	public void setBtnEnviar(JButton btnEnviar) {
		this.btnEnviar = btnEnviar;
	}


	public JButton getBtnUtilizarContacto() {
		return btnUtilizarContacto;
	}


	public void setBtnUtilizarContacto(JButton btnUtilizarContacto) {
		this.btnUtilizarContacto = btnUtilizarContacto;
	}



	public JButton getBtnEditarNmero() {
		return btnEditarNmero;
	}


	public void setBtnEditarNmero(JButton btnEditarNmero) {
		this.btnEditarNmero = btnEditarNmero;
	}
}
