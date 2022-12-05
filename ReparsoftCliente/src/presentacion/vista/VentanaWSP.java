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
import javax.swing.JComboBox;

public class VentanaWSP extends JFrame {
	
	private ControladorReparacion controlador;
	private static final long serialVersionUID = 1L;
	private JTextField textCliente;
	private JTextField textNombreContacto;
	private JTextField textNumeroContacto;
	private JTextField textNumero;
	private JTextArea textMensaje;
	
	private JButton btnUtilizarContacto;
	private JButton btnEnviar;	
	private JButton btnClientes;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JTextField textnumeroContactoBuscado;
	private JComboBox comboNombreBuscado;
	private JButton btnUtilizarContactoBuscado;
	private JButton btnEditarNmero;
	private JComboBox comboOrganizacion;
	
	
	
	public VentanaWSP(ControladorReparacion controlador){
		
		super();
		setResizable(false);
		this.controlador = controlador;
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBounds(460, 260, 502, 418);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(null);
		panel.setBounds(0, 0, 434, 262);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCliente = new JLabel("CLIENTE:");
		lblCliente.setForeground(new Color(0, 0, 128));
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		lblCliente.setBounds(37, 7, 103, 31);
		panel.add(lblCliente);
		
		textCliente = new JTextField();
		textCliente.setForeground(new Color(0, 0, 128));
		textCliente.setFont(new Font("Cambria", Font.BOLD, 20));
		textCliente.setBorder(null);
		textCliente.setBackground(SystemColor.inactiveCaption);
		textCliente.setBounds(139, 7, 332, 31);
		panel.add(textCliente);
		textCliente.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(38, 39, 426, 2);
		panel.add(separator);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBounds(37, 48, 332, 86);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblContactoSugerido = new JLabel("CONTACTO SUGERIDO");
		lblContactoSugerido.setBounds(9, 5, 170, 20);
		panel_1.add(lblContactoSugerido);
		lblContactoSugerido.setForeground(new Color(0, 0, 128));
		lblContactoSugerido.setFont(new Font("Cambria", Font.BOLD, 16));
		
		textNombreContacto = new JTextField();
		textNombreContacto.setEditable(false);
		textNombreContacto.setBounds(121, 31, 186, 20);
		panel_1.add(textNombreContacto);
		textNombreContacto.setForeground(Color.BLACK);
		textNombreContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		textNombreContacto.setBorder(null);
		textNombreContacto.setBackground(Color.WHITE);
		textNombreContacto.setColumns(10);
		
		textNumeroContacto = new JTextField();
		textNumeroContacto.setEditable(false);
		textNumeroContacto.setBounds(121, 57, 186, 20);
		panel_1.add(textNumeroContacto);
		textNumeroContacto.setForeground(Color.BLACK);
		textNumeroContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		textNumeroContacto.setBorder(null);
		textNumeroContacto.setBackground(Color.WHITE);
		textNumeroContacto.setColumns(10);
		
		JLabel lblNombre_1 = new JLabel("Contacto");
		lblNombre_1.setForeground(SystemColor.textHighlight);
		lblNombre_1.setFont(new Font("Cambria", Font.BOLD, 16));
		lblNombre_1.setBounds(10, 32, 112, 20);
		panel_1.add(lblNombre_1);
		
		JLabel lblTelfono_1 = new JLabel("Tel\u00E9fono");
		lblTelfono_1.setForeground(SystemColor.textHighlight);
		lblTelfono_1.setFont(new Font("Cambria", Font.BOLD, 16));
		lblTelfono_1.setBounds(10, 57, 112, 20);
		panel_1.add(lblTelfono_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(37, 262, 426, 2);
		panel.add(separator_1);
		
		btnUtilizarContacto = new JButton("<html>\tUTILIZAR CONTACTO</html>");
		btnUtilizarContacto.setFont(new Font("Cambria", Font.BOLD, 12));
		btnUtilizarContacto.setForeground(SystemColor.textHighlight);
		btnUtilizarContacto.setBounds(379, 48, 85, 40);
		panel.add(btnUtilizarContacto);
		
		JLabel labelPara = new JLabel("N\u00FAmero:");
		labelPara.setFont(new Font("Cambria", Font.BOLD, 14));
		labelPara.setBounds(37, 288, 65, 18);
		panel.add(labelPara);
		
		JLabel labelCuerpo = new JLabel("Mensaje:");
		labelCuerpo.setFont(new Font("Cambria", Font.BOLD, 14));
		labelCuerpo.setBounds(37, 319, 65, 18);
		panel.add(labelCuerpo);
		
		textNumero = new JTextField();
		textNumero.setEditable(false);
		textNumero.setFont(new Font("Cambria", Font.PLAIN, 12));
		textNumero.setColumns(10);
		textNumero.setBounds(125, 288, 214, 20);
		panel.add(textNumero);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(125, 319, 214, 65);
		panel.add(scrollPane);
		
		textMensaje = new JTextArea();
		textMensaje.setFont(new Font("Cambria", Font.PLAIN, 12));
		scrollPane.setViewportView(textMensaje);
		textMensaje.setEditable(false);
		textMensaje.setColumns(10);
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setForeground(new Color(0, 204, 102));
		btnEnviar.setFont(new Font("Cambria", Font.BOLD, 12));
		btnEnviar.setBounds(379, 344, 85, 40);
		panel.add(btnEnviar);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(38, 304, 80, 2);
		panel.add(separator_2);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(37, 335, 80, 2);
		panel.add(separator_7);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1_1.setBounds(37, 138, 332, 113);
		panel.add(panel_1_1);
		
		JLabel lblBuscarContacto = new JLabel("BUSCAR CONTACTO");
		lblBuscarContacto.setForeground(new Color(0, 0, 128));
		lblBuscarContacto.setFont(new Font("Cambria", Font.BOLD, 16));
		lblBuscarContacto.setBounds(9, 5, 154, 20);
		panel_1_1.add(lblBuscarContacto);
		
		textnumeroContactoBuscado = new JTextField();
		textnumeroContactoBuscado.setEditable(false);
		textnumeroContactoBuscado.setForeground(Color.BLACK);
		textnumeroContactoBuscado.setFont(new Font("Cambria", Font.BOLD, 12));
		textnumeroContactoBuscado.setColumns(10);
		textnumeroContactoBuscado.setBorder(null);
		textnumeroContactoBuscado.setBackground(Color.WHITE);
		textnumeroContactoBuscado.setBounds(121, 83, 186, 20);
		panel_1_1.add(textnumeroContactoBuscado);
		
		comboNombreBuscado = new JComboBox();
		comboNombreBuscado.setFont(new Font("Cambria", Font.BOLD, 12));
		comboNombreBuscado.setBounds(121, 58, 186, 20);
		panel_1_1.add(comboNombreBuscado);
		
		comboOrganizacion = new JComboBox();
		comboOrganizacion.setFont(new Font("Cambria", Font.BOLD, 12));
		comboOrganizacion.setBounds(121, 33, 186, 20);
		panel_1_1.add(comboOrganizacion);
		
		JLabel lblOrganizacin = new JLabel("Organizaci\u00F3n");
		lblOrganizacin.setForeground(SystemColor.textHighlight);
		lblOrganizacin.setFont(new Font("Cambria", Font.BOLD, 16));
		lblOrganizacin.setBounds(10, 33, 112, 20);
		panel_1_1.add(lblOrganizacin);
		
		JLabel lblNombre = new JLabel("Contacto");
		lblNombre.setForeground(SystemColor.textHighlight);
		lblNombre.setFont(new Font("Cambria", Font.BOLD, 16));
		lblNombre.setBounds(10, 58, 112, 20);
		panel_1_1.add(lblNombre);
		
		JLabel lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setForeground(SystemColor.textHighlight);
		lblTelfono.setFont(new Font("Cambria", Font.BOLD, 16));
		lblTelfono.setBounds(10, 83, 112, 20);
		panel_1_1.add(lblTelfono);
		
		btnUtilizarContactoBuscado = new JButton("<html>\tUTILIZAR CONTACTO</html>");
		btnUtilizarContactoBuscado.setForeground(SystemColor.textHighlight);
		btnUtilizarContactoBuscado.setFont(new Font("Cambria", Font.BOLD, 12));
		btnUtilizarContactoBuscado.setBounds(379, 211, 85, 40);
		panel.add(btnUtilizarContactoBuscado);
		
		btnClientes = new JButton("<html>\tCLIENTES</html>");
		btnClientes.setForeground(SystemColor.textHighlight);
		btnClientes.setFont(new Font("Cambria", Font.BOLD, 12));
		btnClientes.setBounds(379, 138, 85, 40);
		panel.add(btnClientes);
		
		btnEditarNmero = new JButton("<html>\tEDITAR N\u00DAMERO</html>");
		btnEditarNmero.setForeground(SystemColor.textHighlight);
		btnEditarNmero.setFont(new Font("Cambria", Font.BOLD, 12));
		btnEditarNmero.setBounds(379, 288, 85, 40);
		panel.add(btnEditarNmero);
		
				setLocationCenter();
		setVisible(true);
		
	}

	
	public void setLocationCenter() {
		setLocationMove(0, 0);
	}

	public void setLocationMove(int moveWidth, int moveHeight) {
		// Obtenemos el tamaño de la pantalla.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Obtenemos el tamaño de nuestro frame.
		Dimension frameSize = this.getSize();
		frameSize.width = frameSize.width > screenSize.width ? screenSize.width : frameSize.width;
		frameSize.height = frameSize.height > screenSize.height ? screenSize.height : frameSize.height;
		// We define the location. Definimos la localización.
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


	public JTextField getTextnumeroContactoBuscado() {
		return textnumeroContactoBuscado;
	}


	public void setTextnumeroContactoBuscado(JTextField textnumeroContactoBuscado) {
		this.textnumeroContactoBuscado = textnumeroContactoBuscado;
	}


	public JComboBox getComboNombreBuscado() {
		return comboNombreBuscado;
	}


	public void setComboNombreBuscado(JComboBox comboNombreBuscado) {
		this.comboNombreBuscado = comboNombreBuscado;
	}


	public JButton getBtnUtilizarContactoBuscado() {
		return btnUtilizarContactoBuscado;
	}


	public void setBtnUtilizarContactoBuscado(JButton btnUtilizarContactoBuscado) {
		this.btnUtilizarContactoBuscado = btnUtilizarContactoBuscado;
	}


	public JComboBox getComboOrganizacion() {
		return comboOrganizacion;
	}


	public void setComboOrganizacion(JComboBox comboOrganizacion) {
		this.comboOrganizacion = comboOrganizacion;
	}


	public JButton getBtnClientes() {
		return btnClientes;
	}


	public void setBtnClientes(JButton btnClientes) {
		this.btnClientes = btnClientes;
	}


	public JButton getBtnEditarNmero() {
		return btnEditarNmero;
	}


	public void setBtnEditarNmero(JButton btnEditarNmero) {
		this.btnEditarNmero = btnEditarNmero;
	}
}
