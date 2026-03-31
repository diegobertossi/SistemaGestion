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
import java.awt.Image;
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
	private JPanel panel_1;
	private JButton btncerrarSesion;
	private JButton botonConfiguracion;
	private JTextField textLugarBaseDatos;
	private JButton btnELSant;

	public VistaPrincipal() {
		super();
		setResizable(false);
		this.setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(219, 227, 246));
		setMinimumSize(new Dimension(500, 400));
		initialize();
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);

	}

	private void initialize() {

		setBounds(100, 10, 500, 480);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);

		textProgramador = new JTextField();
		textProgramador.setBounds(0, 427, 166, 16);
		getContentPane().add(textProgramador);
		textProgramador.setOpaque(false);
		textProgramador.setHorizontalAlignment(SwingConstants.RIGHT);
		textProgramador.setForeground(new Color(65, 105, 225));
		textProgramador.setFont(new Font("Cambria", Font.PLAIN, 12));
		textProgramador.setEditable(false);
		textProgramador.setColumns(10);
		textProgramador.setBorder(null);
		textProgramador.setBackground(SystemColor.activeCaption);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(85, 202, 314, 2);
		getContentPane().add(separator_1);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(85, 198, 314, 2);
		getContentPane().add(separator_1_1);

		textUsuario = new JTextField();
		textUsuario.setOpaque(false);
		textUsuario.setEditable(false);
		textUsuario.setBorder(null);
		textUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textUsuario.setBackground(SystemColor.activeCaption);
		textUsuario.setForeground(new Color(65, 105, 225));
		textUsuario.setFont(new Font("Cambria", Font.BOLD, 14));
		textUsuario.setBounds(143, 5, 198, 18);
		getContentPane().add(textUsuario);
		textUsuario.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBackground(new Color(219, 227, 246));
		panel_1.setBorder(null);
		panel_1.setBounds(0, 0, 480, 450);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnELSant = new JButton("ELS ANT");
		btnELSant.setToolTipText("Salir del Sistema");
		btnELSant.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnELSant.setHorizontalAlignment(SwingConstants.LEFT);
		btnELSant.setForeground(new Color(255, 0, 51));
		btnELSant.setFont(new Font("Cambria", Font.BOLD, 10));
		btnELSant.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnELSant.setBounds(376, 55, 97, 33);
		panel_1.add(btnELSant);

		textVersionSoft = new JTextField();
		textVersionSoft.setBounds(316, 427, 166, 16);
		panel_1.add(textVersionSoft);
		textVersionSoft.setOpaque(false);
		textVersionSoft.setHorizontalAlignment(SwingConstants.RIGHT);
		textVersionSoft.setForeground(new Color(65, 105, 225));
		textVersionSoft.setFont(new Font("Cambria", Font.PLAIN, 12));
		textVersionSoft.setEditable(false);
		textVersionSoft.setColumns(10);
		textVersionSoft.setBorder(null);
		textVersionSoft.setBackground(SystemColor.activeCaption);

		btncerrarSesion = new JButton("<html><center>CERRAR SESIÓN</html>");
		btncerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btncerrarSesion.setToolTipText("Cerrar sesión pero no el sistema");
		btncerrarSesion.setHorizontalTextPosition(SwingConstants.RIGHT);
		btncerrarSesion.setHorizontalAlignment(SwingConstants.LEFT);
		btncerrarSesion.setForeground(new Color(70, 130, 180));
		btncerrarSesion.setFont(new Font("Cambria", Font.BOLD, 10));
		btncerrarSesion.setBounds(10, 11, 98, 33);
		btncerrarSesion.setIcon(new ImageIcon(this.getClass().getResource("/Icono cerrar sesion.png")));
		btncerrarSesion.setHorizontalTextPosition(SwingConstants.RIGHT);

		panel_1.add(btncerrarSesion);

		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(376, 11, 97, 33);
		panel_1.add(btnSalir);
		btnSalir.setHorizontalAlignment(SwingConstants.LEFT);
		btnSalir.setForeground(new Color(255, 0, 51));
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.setFont(new Font("Cambria", Font.BOLD, 10));
		btnSalir.setToolTipText("Salir del Sistema");
		btnSalir.setIcon(new ImageIcon(this.getClass().getResource("/Icono salir.png")));
		btnSalir.setHorizontalTextPosition(SwingConstants.RIGHT);

		panelDeControl = new JPanel();
		panelDeControl.setBounds(10, 208, 462, 201);
		panel_1.add(panelDeControl);
		panelDeControl.setBackground(new Color(176, 196, 222));
		panelDeControl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelDeControl.setLayout(null);

		botonEquipos = new JButton("EQUIPOS");
		botonEquipos.setForeground(new Color(0, 0, 128));
		botonEquipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		botonEquipos.setBounds(11, 13, 143, 49);
		botonEquipos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonEquipos.setFont(new Font("Cambria", Font.BOLD, 13));

		botonEquipos.setToolTipText("Ingreso y visualización de equipos al Sistema");

		botonEquipos.setIcon(new ImageIcon(this.getClass().getResource("/motherboard_46935.png")));
		panelDeControl.add(botonEquipos);

		botonSalidas = new JButton("SALIDAS");
		botonSalidas.setForeground(new Color(0, 0, 128));
		botonSalidas.setBounds(160, 13, 143, 49);
		botonSalidas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonSalidas.setFont(new Font("Cambria", Font.BOLD, 13));
		botonSalidas.setToolTipText("Egreso de equipos y generación de Remitos");
		botonSalidas.setIcon(new ImageIcon(this.getClass().getResource("/Salida.png")));
		panelDeControl.add(botonSalidas);

		botonBusquedas = new JButton("BUSCAR");
		botonBusquedas.setForeground(new Color(0, 0, 128));
		botonBusquedas.setBounds(11, 75, 143, 49);
		botonBusquedas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBusquedas.setFont(new Font("Cambria", Font.BOLD, 13));
		botonBusquedas.setToolTipText("Busqueda de equipos, por ELS, Cliente o Tecnico.");
		botonBusquedas.setIcon(new ImageIcon(this.getClass().getResource("/Buscar.png")));
		panelDeControl.add(botonBusquedas);

		botonListados = new JButton("LISTADOS");
		botonListados.setForeground(new Color(0, 0, 128));
		botonListados.setBounds(309, 13, 143, 49);
		botonListados.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonListados.setFont(new Font("Cambria", Font.BOLD, 13));
		botonListados.setToolTipText("Listados de equipos.");
		botonListados.setIcon(new ImageIcon(this.getClass().getResource("/Listado.png")));
		panelDeControl.add(botonListados);

		botonClientes = new JButton("CLIENTES");
		botonClientes.setForeground(new Color(0, 0, 128));
		botonClientes.setBounds(160, 75, 143, 49);
		botonClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonClientes.setFont(new Font("Cambria", Font.BOLD, 13));

		botonClientes.setToolTipText("Visualización y alta de Clientes");

		botonClientes.setIcon(new ImageIcon(this.getClass().getResource("/Clientes.png")));
		panelDeControl.add(botonClientes);

		botonPresupuestos = new JButton("PRESUPUESTOS");
		botonPresupuestos.setHorizontalAlignment(SwingConstants.LEFT);
		botonPresupuestos.setForeground(new Color(0, 0, 128));
		botonPresupuestos.setBounds(309, 75, 143, 49);
		botonPresupuestos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonPresupuestos.setFont(new Font("Cambria", Font.BOLD, 11));

		botonPresupuestos.setToolTipText("Generación de Presupuestos, pendientes e Ingreso de Pagos.");

		botonPresupuestos.setIcon(new ImageIcon(this.getClass().getResource("/Presupuestos.png")));
		panelDeControl.add(botonPresupuestos);

		botonUsuarios = new JButton("USUARIOS");
		botonUsuarios.setForeground(new Color(0, 0, 128));
		botonUsuarios.setBounds(160, 137, 143, 49);
		botonUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonUsuarios.setFont(new Font("Cambria", Font.BOLD, 13));
		botonUsuarios.setToolTipText("Gestión de los Usuarios del Sistema.");
		botonUsuarios.setIcon(new ImageIcon(this.getClass().getResource("/Usuarios.png")));
		panelDeControl.add(botonUsuarios);

		botonBackUp = new JButton("BACKUP");
		botonBackUp.setForeground(new Color(0, 0, 128));
		botonBackUp.setBounds(11, 137, 143, 49);
		botonBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBackUp.setFont(new Font("Cambria", Font.BOLD, 13));
		botonBackUp.setToolTipText("Backup del Sistema.");
		botonBackUp.setIcon(new ImageIcon(this.getClass().getResource("/Backup.png")));
		panelDeControl.add(botonBackUp);

		botonConfiguracion = new JButton("CONFIGURACIÓN");
		botonConfiguracion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		botonConfiguracion.setToolTipText("Configuraciones del Sistema");
		botonConfiguracion.setForeground(new Color(0, 0, 128));
		botonConfiguracion.setFont(new Font("Cambria", Font.BOLD, 13));
		botonConfiguracion.setBounds(309, 135, 143, 49);
		panelDeControl.add(botonConfiguracion);

		JLabel lblReparacionesEls = new JLabel("");
		lblReparacionesEls.setIcon(new ImageIcon(this.getClass().getResource("/REPARSOFT logo pequeño.png")));
		lblReparacionesEls.setOpaque(true);
		lblReparacionesEls.setHorizontalTextPosition(SwingConstants.CENTER);
		lblReparacionesEls.setVerticalTextPosition(SwingConstants.TOP);
		lblReparacionesEls.setHorizontalAlignment(SwingConstants.CENTER);
		lblReparacionesEls.setBackground(new Color(176, 196, 222));
		lblReparacionesEls.setBounds(25, 58, 473, 153);
		panel_1.add(lblReparacionesEls);
		lblReparacionesEls.setForeground(new Color(105, 105, 105));
		lblReparacionesEls.setFont(new Font("Wide Latin", Font.BOLD, 22));

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(86, 414, 314, 2);
		panel_1.add(separator_1_2);

		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(86, 418, 314, 2);
		panel_1.add(separator_1_1_1);

		textLugarBaseDatos = new JTextField();
		textLugarBaseDatos.setOpaque(false);
		textLugarBaseDatos.setHorizontalAlignment(SwingConstants.CENTER);
		textLugarBaseDatos.setForeground(new Color(65, 105, 225));
		textLugarBaseDatos.setFont(new Font("Cambria", Font.BOLD, 14));
		textLugarBaseDatos.setEditable(false);
		textLugarBaseDatos.setColumns(10);
		textLugarBaseDatos.setBorder(null);
		textLugarBaseDatos.setBackground(SystemColor.activeCaption);
		textLugarBaseDatos.setBounds(143, 30, 198, 18);
		panel_1.add(textLugarBaseDatos);

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

	public JButton getBtncerrarSesion() {
		return btncerrarSesion;
	}

	public void setBtncerrarSesion(JButton btncerrarSesion) {
		this.btncerrarSesion = btncerrarSesion;
	}

	public JButton getBotonConfiguracion() {
		return botonConfiguracion;
	}

	public void setBotonConfiguracion(JButton botonConfiguracion) {
		this.botonConfiguracion = botonConfiguracion;
	}

	public JTextField getTextLugarBaseDatos() {
		return textLugarBaseDatos;
	}

	public void setTextLugarBaseDatos(JTextField textLugarBaseDatos) {
		this.textLugarBaseDatos = textLugarBaseDatos;
	}

	public JButton getBtnELSant() {
		return btnELSant;
	}

	public void setBtnELSant(JButton btnELSant) {
		this.btnELSant = btnELSant;
	}
}
