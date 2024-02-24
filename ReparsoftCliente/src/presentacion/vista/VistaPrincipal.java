package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;

public class VistaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuItem btnTecnicos;
	private JMenuItem btnClientes;
	private JMenuItem btnReparaciones;
	private JButton botonClientes;
	private JButton botonEquipos;
	private JButton botonSalidas;
	private JButton botonBusquedas;
	private JButton botonPresupuestos;
	private JButton botonListados;
	private JButton botonUsuarios;
	private JButton botonBackUp;
	private JButton btnSalir;
	private JPanel panelDeControl;
	private JTextField textUsuario;
	private JTextField textVersionSoft;
	private JTextField textProgramador;
	
	private JMenuItem  btnCerrarSesion;
	private JMenuItem  btnConfiguracion;
	private JMenuItem  btnAcercaDe;
	private JMenuItem  btnAyuda;
	private JSeparator separator;
	private JPanel panel_1;
	
	

	public VistaPrincipal() {
		super();
		setResizable(false);
		this.setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(176, 196, 222));
		setMinimumSize(new Dimension(500, 400));
		initialize();

	}

	private void initialize() {

		setBounds(100, 10, 500, 440);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);

		panelDeControl = new JPanel();
		panelDeControl.setBackground(new Color(112, 128, 144));
		panelDeControl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		panelDeControl.setBounds(11, 149, 462, 210);
		getContentPane().add(panelDeControl);
		panelDeControl.setLayout(null);

		botonEquipos = new JButton("EQUIPOS");
		botonEquipos.setForeground(new Color(0, 0, 128));
		botonEquipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		botonEquipos.setBounds(11, 24, 143, 49);
		botonEquipos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonEquipos.setFont(new Font("Cambria", Font.BOLD, 13));

		botonEquipos.setToolTipText("Ingreso y visualización de equipos al Sistema");
		
		botonEquipos.setIcon(new ImageIcon(this.getClass().getResource("/motherboard_46935.png")));
		panelDeControl.add(botonEquipos);

		botonSalidas = new JButton("SALIDAS");
		botonSalidas.setForeground(new Color(0, 0, 128));
		botonSalidas.setBounds(160, 24, 143, 49);
		botonSalidas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonSalidas.setFont(new Font("Cambria", Font.BOLD, 13));
		botonSalidas.setToolTipText("Egreso de equipos y generación de Remitos");
		botonSalidas.setIcon(new ImageIcon(this.getClass().getResource("/Salida.png")));
		panelDeControl.add(botonSalidas);

		botonBusquedas = new JButton("BUSCAR");
		botonBusquedas.setForeground(new Color(0, 0, 128));
		botonBusquedas.setBounds(11, 84, 143, 49);
		botonBusquedas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBusquedas.setFont(new Font("Cambria", Font.BOLD, 13));
		botonBusquedas.setToolTipText("Busqueda de equipos, por ELS, Cliente o Tecnico.");
		botonBusquedas.setIcon(new ImageIcon(this.getClass().getResource("/Buscar.png")));
		panelDeControl.add(botonBusquedas);

		botonListados = new JButton("LISTADOS");
		botonListados.setForeground(new Color(0, 0, 128));
		botonListados.setBounds(309, 24, 143, 49);
		botonListados.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonListados.setFont(new Font("Cambria", Font.BOLD, 13));
		botonListados.setToolTipText("Listados de equipos.");
		botonListados.setIcon(new ImageIcon(this.getClass().getResource("/Listado.png")));
		panelDeControl.add(botonListados);

		botonClientes = new JButton("CLIENTES");
		botonClientes.setForeground(new Color(0, 0, 128));
		botonClientes.setBounds(160, 84, 143, 49);
		botonClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonClientes.setFont(new Font("Cambria", Font.BOLD, 13));

		botonClientes.setToolTipText("Visualización y alta de Clientes");

		botonClientes.setIcon(new ImageIcon(this.getClass().getResource("/Clientes.png")));
		panelDeControl.add(botonClientes);

		botonPresupuestos = new JButton("PRESUPUESTOS");
		botonPresupuestos.setHorizontalAlignment(SwingConstants.LEFT);
		botonPresupuestos.setForeground(new Color(0, 0, 128));
		botonPresupuestos.setBounds(309, 84, 143, 49);
		botonPresupuestos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonPresupuestos.setFont(new Font("Cambria", Font.BOLD, 11));

		botonPresupuestos.setToolTipText("Generación de Presupuestos, pendientes e Ingreso de Pagos.");

		botonPresupuestos.setIcon(new ImageIcon(this.getClass().getResource("/Presupuestos.png")));
		panelDeControl.add(botonPresupuestos);

		botonUsuarios = new JButton("USUARIOS");
		botonUsuarios.setForeground(new Color(0, 0, 128));
		botonUsuarios.setBounds(160, 144, 143, 49);
		botonUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonUsuarios.setFont(new Font("Cambria", Font.BOLD, 13));
		botonUsuarios.setToolTipText("Gestión de los Usuarios del Sistema.");
		botonUsuarios.setIcon(new ImageIcon(this.getClass().getResource("/Usuarios.png")));
		panelDeControl.add(botonUsuarios);

		botonBackUp = new JButton("BACKUP");
		botonBackUp.setForeground(new Color(0, 0, 128));
		botonBackUp.setBounds(11, 144, 143, 49);
		botonBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBackUp.setFont(new Font("Cambria", Font.BOLD, 13));
		botonBackUp.setToolTipText("Backup del Sistema.");
		botonBackUp.setIcon(new ImageIcon(this.getClass().getResource("/Backup.png")));
		panelDeControl.add(botonBackUp);
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBackground(new Color(153, 153, 255));
		panel_1.setBorder(null);
		panel_1.setBounds(11, 47, 462, 83);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);


		JLabel lblNewLabel = new JLabel("SISTEMA DE GESTIÓN");
		lblNewLabel.setBounds(68, 2, 325, 36);
		panel_1.add(lblNewLabel);

		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 25));

		JLabel lblReparacionesEls = new JLabel("REPARSOFT");
		lblReparacionesEls.setBackground(new Color(119, 136, 153));
		lblReparacionesEls.setBounds(90, 41, 282, 36);
		panel_1.add(lblReparacionesEls);
		lblReparacionesEls.setForeground(new Color(105, 105, 105));
		lblReparacionesEls.setHorizontalAlignment(SwingConstants.CENTER);
		lblReparacionesEls.setFont(new Font("Wide Latin", Font.BOLD, 22));

		textUsuario = new JTextField();
		textUsuario.setOpaque(false);
		textUsuario.setEditable(false);
		textUsuario.setBorder(null);
		textUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textUsuario.setBackground(SystemColor.activeCaption);
		textUsuario.setForeground(Color.WHITE);
		textUsuario.setFont(new Font("Cambria", Font.BOLD, 14));
		textUsuario.setBounds(143, 0, 198, 27);
		getContentPane().add(textUsuario);
		textUsuario.setColumns(10);

		textVersionSoft = new JTextField();
		textVersionSoft.setOpaque(false);
		textVersionSoft.setHorizontalAlignment(SwingConstants.RIGHT);
		textVersionSoft.setForeground(Color.WHITE);
		textVersionSoft.setFont(new Font("Cambria", Font.PLAIN, 12));
		textVersionSoft.setEditable(false);
		textVersionSoft.setColumns(10);
		textVersionSoft.setBorder(null);
		textVersionSoft.setBackground(SystemColor.activeCaption);
		textVersionSoft.setBounds(308, 383, 166, 16);
		getContentPane().add(textVersionSoft);

		textProgramador = new JTextField();
		textProgramador.setOpaque(false);
		textProgramador.setHorizontalAlignment(SwingConstants.RIGHT);
		textProgramador.setForeground(Color.WHITE);
		textProgramador.setFont(new Font("Cambria", Font.PLAIN, 12));
		textProgramador.setEditable(false);
		textProgramador.setColumns(10);
		textProgramador.setBorder(null);
		textProgramador.setBackground(SystemColor.activeCaption);
		textProgramador.setBounds(11, 383, 166, 16);
		getContentPane().add(textProgramador);

		btnSalir = new JButton("SALIR");
		btnSalir.setForeground(new Color(255, 0, 51));
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.setFont(new Font("Cambria", Font.BOLD, 14));
		btnSalir.setBounds(376, 1, 107, 36);
		btnSalir.setToolTipText("Salir del Sistema");
		btnSalir.setIcon(new ImageIcon(this.getClass().getResource("/logout.png")));
		getContentPane().add(btnSalir);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(false);
		menuBar.setBorderPainted(false);
		menuBar.setBorder(UIManager.getBorder("MenuBar.border"));
		menuBar.setFont(new Font("Cambria", Font.BOLD, 12));
		menuBar.setBackground(SystemColor.activeCaption);
		JMenu mnMenu = new JMenu("");
		mnMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		mnMenu.setHorizontalAlignment(SwingConstants.CENTER);
		mnMenu.setPreferredSize(new Dimension(40, 25));
		mnMenu.setIcon(new ImageIcon(this.getClass().getResource("/icons8-menú-25.png")));
		mnMenu.setBorderPainted(true);
		mnMenu.setForeground(new Color(0, 0, 0));
		mnMenu.setFont(new Font("Cambria", Font.BOLD, 13));
		mnMenu.setBackground(new Color(192, 192, 192));
		menuBar.setBounds(0, 0, 40, 22);
		mnMenu.setBounds(0, 0, 101, 22);
		getContentPane().add(menuBar);
		menuBar.add(mnMenu);
		

		btnCerrarSesion = new JMenuItem("Cerrar Sesion");
		btnCerrarSesion.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnConfiguracion = new JMenuItem("Configuración");
		btnConfiguracion.setFont(new Font("Cambria", Font.PLAIN, 12));
		separator = new JSeparator();
		separator.setBounds(new Rectangle(0, 0, 0, 3));
		separator.setBorder(new EmptyBorder(10, 0, 10, 0));
		btnAcercaDe = new JMenuItem("Acerca de...");
		btnAcercaDe.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnAyuda = new JMenuItem("Ayuda");
		btnAyuda.setFont(new Font("Cambria", Font.PLAIN, 12));
		
		
		
	
		mnMenu.add(btnCerrarSesion);
		
		mnMenu.add(btnConfiguracion);
		
		mnMenu.add(separator);
		
		mnMenu.add(btnAcercaDe);
		
		mnMenu.add(btnAyuda);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(85, 132, 314, 2);
		getContentPane().add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(85, 136, 314, 2);
		getContentPane().add(separator_1_1);
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(85, 374, 314, 2);
		getContentPane().add(separator_1_1_1);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(85, 370, 314, 2);
		getContentPane().add(separator_1_2);
		
		
		
		
		
		
		

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

	public JButton getBotonBackUp() {
		return botonBackUp;
	}

	public void setBotonBackUp(JButton botonBackUp) {
		this.botonBackUp = botonBackUp;
	}


	public JButton getBotonSalidas() {
		return botonSalidas;
	}

	public void setBotonSalidas(JButton botonSalidas) {
		this.botonSalidas = botonSalidas;
	}

	public JButton getBotonBusquedas() {
		return botonBusquedas;
	}

	public void setBotonBusquedas(JButton botonBusquedas) {
		this.botonBusquedas = botonBusquedas;
	}

	public JButton getBotonPresupuestos() {
		return botonPresupuestos;
	}

	public void setBotonPresupuestos(JButton botonPresupuestos) {
		this.botonPresupuestos = botonPresupuestos;
	}

	public JButton getBotonListados() {
		return botonListados;
	}

	public void setBotonListados(JButton botonListados) {
		this.botonListados = botonListados;
	}

	public JButton getBotonUsuarios() {
		return botonUsuarios;
	}

	public void setBotonUsuarios(JButton botonUsuarios) {
		this.botonUsuarios = botonUsuarios;
	}

	public JButton getBotonEquipos() {
		return botonEquipos;
	}

	public void setBotonEquipos(JButton botonEquipos) {
		this.botonEquipos = botonEquipos;
	}

	public JButton getBotonClientes() {
		return botonClientes;
	}

	public void setBotonClientes(JButton botonClientes) {
		this.botonClientes = botonClientes;
	}

	public JPanel getPanel() {
		return panelDeControl;
	}

	public void setPanel(JPanel panel) {
		this.getContentPane().remove(this.panelDeControl);
		this.panelDeControl = panel;
		this.getContentPane().add(this.panelDeControl);
		this.panelDeControl.repaint();

	}

	public JMenuItem getBtnTecnicos() {
		return btnTecnicos;
	}

	public void setBtnTecnicos(JMenuItem btnTecnicos) {
		this.btnTecnicos = btnTecnicos;
	}

	public JMenuItem getBtnClientes() {
		return btnClientes;
	}

	public void setBtnClientes(JMenuItem btnClientes) {
		this.btnClientes = btnClientes;
	}

	public JMenuItem getBtnReparaciones() {
		return btnReparaciones;
	}

	public void setBtnReparaciones(JMenuItem btnReparaciones) {
		this.btnReparaciones = btnClientes;
	}

	public JTextField getTextUsuario() {
		return textUsuario;
	}

	public void setTextUsuario(JTextField textUsuario) {
		this.textUsuario = textUsuario;
	}

	public JTextField getTextVersionSoft() {
		return textVersionSoft;
	}

	public void setTextVersionSoft(JTextField textVersionSoft) {
		this.textVersionSoft = textVersionSoft;
	}

	public JTextField getTextProgramador() {
		return textProgramador;
	}

	public void setTextProgramador(JTextField textProgramador) {
		this.textProgramador = textProgramador;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public JMenuItem getBtnCerrarSesion() {
		return btnCerrarSesion;
	}

	public void setBtnCerrarSesion(JMenuItem btnCerrarSesion) {
		this.btnCerrarSesion = btnCerrarSesion;
	}

	public JMenuItem getBtnConfiguracion() {
		return btnConfiguracion;
	}

	public void setBtnConfiguracion(JMenuItem btnConfiguracion) {
		this.btnConfiguracion = btnConfiguracion;
	}

	public JMenuItem getBtnAcercaDe() {
		return btnAcercaDe;
	}

	public void setBtnAcercaDe(JMenuItem btnAcercaDe) {
		this.btnAcercaDe = btnAcercaDe;
	}

	public JMenuItem getBtnAyuda() {
		return btnAyuda;
	}

	public void setBtnAyuda(JMenuItem btnAyuda) {
		this.btnAyuda = btnAyuda;
	}
}
