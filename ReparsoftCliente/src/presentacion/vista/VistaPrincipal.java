package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;

import java.awt.Frame;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.Toolkit;

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
	private JButton botonConfiguracion;
	private JButton btnSalir;
	private JPanel panelDeControl;
	private JTextField textUsuario;
	private JTextField textVersionSoft;
	private JTextField textProgramador;

	public VistaPrincipal() {
		super();
		setResizable(false);
		this.setLocationRelativeTo(null);
		getContentPane().setBackground(SystemColor.activeCaption);
		setMinimumSize(new Dimension(500, 400));
		initialize();

	}

	private void initialize() {

		setBounds(100, 10, 690, 517);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);

		panelDeControl = new JPanel();
		panelDeControl.setBackground(SystemColor.inactiveCaption);
		panelDeControl.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.activeCaption,
				SystemColor.inactiveCaption, null, null));

		panelDeControl.setBounds(10, 156, 654, 299);
		getContentPane().add(panelDeControl);
		panelDeControl.setLayout(null);

		botonEquipos = new JButton("EQUIPOS");
		botonEquipos.setForeground(new Color(0, 0, 128));
		botonEquipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		botonEquipos.setBounds(11, 37, 200, 73);
		botonEquipos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonEquipos.setFont(new Font("Arial", Font.BOLD, 12));
		botonEquipos.setToolTipText("Ingreso y visualización de equipos al Sistema");
		botonEquipos.setIcon(new ImageIcon(this.getClass().getResource("/Motherboard_icon-icons.com_55228.png")));
		panelDeControl.add(botonEquipos);

		botonSalidas = new JButton("SALIDAS");
		botonSalidas.setForeground(new Color(0, 0, 128));
		botonSalidas.setBounds(231, 38, 200, 73);
		botonSalidas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonSalidas.setFont(new Font("Arial", Font.BOLD, 12));
		botonSalidas.setToolTipText("Egreso de equipos y generaciÃ³n de Remitos");
		botonSalidas.setIcon(new ImageIcon(this.getClass().getResource("/package.png")));
		panelDeControl.add(botonSalidas);

		botonBusquedas = new JButton("BUDASQUEDAS");
		botonBusquedas.setForeground(new Color(0, 0, 128));
		botonBusquedas.setBounds(10, 122, 200, 73);
		botonBusquedas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBusquedas.setFont(new Font("Arial", Font.BOLD, 12));
		botonBusquedas.setToolTipText("Busqueda de equipos, por ELS, Cliente o Tecnico.");
		botonBusquedas.setIcon(new ImageIcon(this.getClass().getResource("/search_64x64.png")));
		panelDeControl.add(botonBusquedas);

		botonListados = new JButton("LISTADOS");
		botonListados.setForeground(new Color(0, 0, 128));
		botonListados.setBounds(450, 36, 197, 73);
		botonListados.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonListados.setFont(new Font("Arial", Font.BOLD, 12));
		botonListados.setToolTipText("Listados de equipos.");
		botonListados.setIcon(new ImageIcon(this.getClass().getResource("/notes_64x64.png")));
		panelDeControl.add(botonListados);

		botonClientes = new JButton("CLIENTES");
		botonClientes.setForeground(new Color(0, 0, 128));
		botonClientes.setBounds(231, 124, 200, 73);
		botonClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonClientes.setFont(new Font("Arial", Font.BOLD, 12));
		botonClientes.setToolTipText("Visualización y alta de Clientes");
		botonClientes.setIcon(new ImageIcon(this.getClass().getResource("/admin_64x64.png")));
		panelDeControl.add(botonClientes);

		botonPresupuestos = new JButton("PRESUPUESTOS");
		botonPresupuestos.setForeground(new Color(0, 0, 128));
		botonPresupuestos.setBounds(450, 122, 197, 73);
		botonPresupuestos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonPresupuestos.setFont(new Font("Arial", Font.BOLD, 12));
		botonPresupuestos.setToolTipText("Generación de Presupuestos, pendientes e Ingreso de Pagos.");
		botonPresupuestos.setIcon(new ImageIcon(this.getClass().getResource("/presupuesto.png")));
		panelDeControl.add(botonPresupuestos);

		botonUsuarios = new JButton("USUARIOS");
		botonUsuarios.setForeground(new Color(0, 0, 128));
		botonUsuarios.setBounds(10, 208, 200, 73);
		botonUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonUsuarios.setFont(new Font("Arial", Font.BOLD, 12));
		botonUsuarios.setToolTipText("GestiÃ³n de los Usuarios del Sistema.");
		botonUsuarios.setIcon(new ImageIcon(this.getClass().getResource("/Usuarios.png")));
		panelDeControl.add(botonUsuarios);

		botonBackUp = new JButton("BACKUP");
		botonBackUp.setForeground(new Color(0, 0, 128));
		botonBackUp.setBounds(231, 208, 200, 73);
		botonBackUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBackUp.setFont(new Font("Arial", Font.BOLD, 12));
		botonBackUp.setToolTipText("Backup del Sistema.");
		botonBackUp.setIcon(new ImageIcon(this.getClass().getResource("/data-backup.png")));
		panelDeControl.add(botonBackUp);

		botonConfiguracion = new JButton("CONFIGURACIÓN");
		botonConfiguracion.setForeground(new Color(0, 0, 128));
		botonConfiguracion.setBounds(450, 208, 197, 73);
		botonConfiguracion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonConfiguracion.setFont(new Font("Arial", Font.BOLD, 12));
		botonConfiguracion.setToolTipText("Configuración del Sitema");
		botonConfiguracion.setIcon(new ImageIcon(this.getClass().getResource("/Settings.png")));
		panelDeControl.add(botonConfiguracion);

		JLabel lblNewLabel = new JLabel("SISTEMA DE GESIÓN");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 25));
		lblNewLabel.setBounds(212, 51, 265, 33);
		getContentPane().add(lblNewLabel);

		JLabel lblReparacionesEls = new JLabel("REPARACIONES ELS");
		lblReparacionesEls.setForeground(Color.WHITE);
		lblReparacionesEls.setHorizontalAlignment(SwingConstants.CENTER);
		lblReparacionesEls.setFont(new Font("Cambria", Font.BOLD, 25));
		lblReparacionesEls.setBounds(212, 88, 265, 33);
		getContentPane().add(lblReparacionesEls);

		textUsuario = new JTextField();
		textUsuario.setEditable(false);
		textUsuario.setBorder(null);
		textUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		textUsuario.setBackground(SystemColor.activeCaption);
		textUsuario.setForeground(Color.WHITE);
		textUsuario.setFont(new Font("Cambria", Font.BOLD, 14));
		textUsuario.setBounds(466, 11, 198, 27);
		getContentPane().add(textUsuario);
		textUsuario.setColumns(10);

		textVersionSoft = new JTextField();
		textVersionSoft.setHorizontalAlignment(SwingConstants.RIGHT);
		textVersionSoft.setForeground(Color.WHITE);
		textVersionSoft.setFont(new Font("Cambria", Font.PLAIN, 12));
		textVersionSoft.setEditable(false);
		textVersionSoft.setColumns(10);
		textVersionSoft.setBorder(null);
		textVersionSoft.setBackground(SystemColor.activeCaption);
		textVersionSoft.setBounds(498, 461, 166, 16);
		getContentPane().add(textVersionSoft);

		textProgramador = new JTextField();
		textProgramador.setHorizontalAlignment(SwingConstants.RIGHT);
		textProgramador.setForeground(Color.WHITE);
		textProgramador.setFont(new Font("Cambria", Font.PLAIN, 12));
		textProgramador.setEditable(false);
		textProgramador.setColumns(10);
		textProgramador.setBorder(null);
		textProgramador.setBackground(SystemColor.activeCaption);
		textProgramador.setBounds(10, 461, 166, 16);
		getContentPane().add(textProgramador);

		btnSalir = new JButton("SALIR");
		btnSalir.setForeground(new Color(255, 0, 51));
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.setFont(new Font("Arial", Font.BOLD, 12));
		btnSalir.setBounds(558, 61, 103, 49);
		btnSalir.setToolTipText("Salir del Sistema");
		btnSalir.setIcon(new ImageIcon(this.getClass().getResource("/logout.png")));
		getContentPane().add(btnSalir);

		setLocationCenter();
		setVisible(true);

	}

	public void setLocationCenter() {
		setLocationMove(0, 0);
	}

	public void setLocationMove(int moveWidth, int moveHeight) {
		// Obtenemos el tamaï¿½o de la pantalla.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Obtenemos el tamaï¿½o de nuestro frame.
		Dimension frameSize = this.getSize();
		frameSize.width = frameSize.width > screenSize.width ? screenSize.width : frameSize.width;
		frameSize.height = frameSize.height > screenSize.height ? screenSize.height : frameSize.height;
		// We define the location. Definimos la localizaciï¿½n.
		setLocation((screenSize.width - frameSize.width) / 2 + moveWidth,
				(screenSize.height - frameSize.height) / 2 + moveHeight);
	}

	public JButton getBotonBackUp() {
		return botonBackUp;
	}

	public void setBotonBackUp(JButton botonBackUp) {
		this.botonBackUp = botonBackUp;
	}

	public JButton getBotonConfiguracion() {
		return botonConfiguracion;
	}

	public void setBotonConfiguracion(JButton botonConfiguracion) {
		this.botonConfiguracion = botonConfiguracion;
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

}
