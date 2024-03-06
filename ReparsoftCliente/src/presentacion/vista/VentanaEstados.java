package presentacion.vista;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import presentacion.controlador.ControladorReparacion;

import java.awt.SystemColor;
import java.awt.Toolkit;

public class VentanaEstados extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JRadioButton rdbtnMDQ;
	private JRadioButton rdbtnCABA;
	private JRadioButton rdbtnBRC;
	private JRadioButton rdbtnENVIADO;
	
	private ButtonGroup GrupoEstadoFisico;
	
	private JRadioButton rdbtnSinRevisar;
	private JRadioButton rdbtnEnReparacion;
	private JRadioButton rdbtnReparado;
	private JRadioButton rdbtnReparadoGarantia;
	private JRadioButton rdbtnVendido;
	private JRadioButton rdbtnDesguace;
	private JRadioButton rdbtnSinFalla;
	private JRadioButton rdbtnSinReparacion;
	private JRadioButton rdbtnNoAceptaron;
	
	private ButtonGroup GrupoEstadoTecnico;
	
	private JRadioButton rdbtnEspera;
	private JRadioButton rdbtnAceptado;
	private JRadioButton rdbtnNoAceptado;
	private JRadioButton rdbtnGarantia;
	
	
	private ButtonGroup GrupoEstadoComercial;
	
	
	private JButton btnAceptarEdicion ;
	
	
	private JLabel lblEstadoFisico;
	private JLabel lblEstadoTecnico;
	private JLabel lblEstadoComercial;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;

	public VentanaEstados(ControladorReparacion controlador) 
	{
		super();
		setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 772, 284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 756, 246);
		contentPane.add(panel);
		panel.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(22, 51, 136, 142);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		
		rdbtnMDQ = new JRadioButton("MDQ");
		rdbtnMDQ.setBackground(SystemColor.activeCaption);
		rdbtnMDQ.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnMDQ.setBounds(6, 31, 109, 23);
		panel_2.add(rdbtnMDQ);
		
		rdbtnDesguace = new JRadioButton("Desgüace");
		rdbtnDesguace.setBackground(SystemColor.activeCaption);
		rdbtnDesguace.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnDesguace.setBounds(6, 112, 109, 23);
		panel_2.add(rdbtnDesguace);
		
		rdbtnBRC = new JRadioButton("BRC");
		rdbtnBRC.setBackground(SystemColor.activeCaption);
		rdbtnBRC.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnBRC.setBounds(6, 58, 109, 23);
		panel_2.add(rdbtnBRC);
		
		rdbtnENVIADO = new JRadioButton("Enviado");
		rdbtnENVIADO.setBackground(SystemColor.activeCaption);
		rdbtnENVIADO.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnENVIADO.setBounds(6, 85, 109, 23);
		panel_2.add(rdbtnENVIADO);
		
		rdbtnCABA = new JRadioButton("CABA");
		rdbtnCABA.setBackground(SystemColor.activeCaption);
		rdbtnCABA.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnCABA.setBounds(6, 4, 109, 23);
		panel_2.add(rdbtnCABA);
		
		GrupoEstadoFisico = new ButtonGroup();
		GrupoEstadoFisico.add(rdbtnMDQ);
		GrupoEstadoFisico.add(rdbtnBRC);
		GrupoEstadoFisico.add(rdbtnENVIADO);
		GrupoEstadoFisico.add(rdbtnCABA);
		GrupoEstadoFisico.add(rdbtnDesguace);
		
		panel_3 = new JPanel();
		panel_3.setFont(new Font("Cambria", Font.BOLD, 12));
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(180, 51, 324, 142);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		
		rdbtnSinRevisar = new JRadioButton("Sin Revisar");
		rdbtnSinRevisar.setBackground(SystemColor.activeCaption);
		rdbtnSinRevisar.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnSinRevisar.setBounds(6, 5, 109, 23);
		panel_3.add(rdbtnSinRevisar);
		
		rdbtnEnReparacion = new JRadioButton("En Reparación");
		rdbtnEnReparacion.setBackground(SystemColor.activeCaption);
		rdbtnEnReparacion.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnEnReparacion.setBounds(6, 33, 109, 23);
		panel_3.add(rdbtnEnReparacion);
		
		rdbtnReparado = new JRadioButton("Reparado");
		rdbtnReparado.setBackground(SystemColor.activeCaption);
		rdbtnReparado.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnReparado.setBounds(6, 61, 109, 23);
		panel_3.add(rdbtnReparado);
		
		rdbtnReparadoGarantia = new JRadioButton("Reparado En Garantía");
		rdbtnReparadoGarantia.setBackground(SystemColor.activeCaption);
		rdbtnReparadoGarantia.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnReparadoGarantia.setBounds(6, 89, 152, 23);
		panel_3.add(rdbtnReparadoGarantia);
				
		rdbtnVendido = new JRadioButton("Vendido");
		rdbtnVendido.setBackground(SystemColor.activeCaption);
		rdbtnVendido.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnVendido.setBounds(155, 5, 139, 23);
		panel_3.add(rdbtnVendido);
		
		rdbtnSinFalla = new JRadioButton("Sin Falla");
		rdbtnSinFalla.setBackground(SystemColor.activeCaption);
		rdbtnSinFalla.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnSinFalla.setBounds(155, 33, 139, 23);
		panel_3.add(rdbtnSinFalla);
		
		rdbtnSinReparacion = new JRadioButton("Sin Reparación");
		rdbtnSinReparacion.setBackground(SystemColor.activeCaption);
		rdbtnSinReparacion.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnSinReparacion.setBounds(155, 61, 139, 23);
		panel_3.add(rdbtnSinReparacion);
		
		rdbtnNoAceptaron = new JRadioButton("No Aceptaron Reparación");
		rdbtnNoAceptaron.setBackground(SystemColor.activeCaption);
		rdbtnNoAceptaron.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnNoAceptaron.setBounds(155, 89, 163, 23);
		panel_3.add(rdbtnNoAceptaron);
		
		GrupoEstadoTecnico = new ButtonGroup();
		GrupoEstadoTecnico.add(rdbtnSinRevisar);
		GrupoEstadoTecnico.add(rdbtnEnReparacion);
		GrupoEstadoTecnico.add(rdbtnReparado);
		GrupoEstadoTecnico.add(rdbtnReparadoGarantia);
		GrupoEstadoTecnico.add(rdbtnVendido);
		GrupoEstadoTecnico.add(rdbtnSinFalla);
		GrupoEstadoTecnico.add(rdbtnSinReparacion);
		GrupoEstadoTecnico.add(rdbtnNoAceptaron);
		
		

		panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.activeCaption);
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBounds(526, 51, 206, 142);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		rdbtnEspera = new JRadioButton("A la Espera de Aceptación");
		rdbtnEspera.setBackground(SystemColor.activeCaption);
		rdbtnEspera.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnEspera.setBounds(6, 5, 194, 23);
		panel_4.add(rdbtnEspera);
		
		rdbtnAceptado = new JRadioButton("Aceptado");
		rdbtnAceptado.setBackground(SystemColor.activeCaption);
		rdbtnAceptado.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnAceptado.setBounds(6, 33, 149, 23);
		panel_4.add(rdbtnAceptado);
		
		rdbtnNoAceptado = new JRadioButton("NO Aceptado");
		rdbtnNoAceptado.setBackground(SystemColor.activeCaption);
		rdbtnNoAceptado.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnNoAceptado.setBounds(6, 61, 149, 23);
		panel_4.add(rdbtnNoAceptado);
		
		rdbtnGarantia = new JRadioButton("Garantía");
		rdbtnGarantia.setBackground(SystemColor.activeCaption);
		rdbtnGarantia.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnGarantia.setBounds(6, 89, 149, 23);
		panel_4.add(rdbtnGarantia);
		
		
		GrupoEstadoComercial = new ButtonGroup();
		GrupoEstadoComercial.add(rdbtnEspera);
		GrupoEstadoComercial.add(rdbtnAceptado);
		GrupoEstadoComercial.add(rdbtnNoAceptado);
		GrupoEstadoComercial.add(rdbtnGarantia);
		
		
		lblEstadoFisico = new JLabel("ESTADO FÍSICO:");
		lblEstadoFisico.setBounds(26, 26, 136, 14);
		panel.add(lblEstadoFisico);
		lblEstadoFisico.setFont(new Font("Cambria", Font.BOLD, 16));
		
		lblEstadoComercial = new JLabel("ESTADO COMERCIAL:");
		lblEstadoComercial.setBounds(526, 26, 206, 14);
		panel.add(lblEstadoComercial);
		lblEstadoComercial.setFont(new Font("Cambria", Font.BOLD, 16));
		
		lblEstadoTecnico = new JLabel("ESTADO TÉCNICO:");
		lblEstadoTecnico.setBounds(188, 26, 300, 14);
		panel.add(lblEstadoTecnico);
		lblEstadoTecnico.setFont(new Font("Cambria", Font.BOLD, 16));
		
		btnAceptarEdicion = new JButton("ACEPTAR EDICIÓN");
		btnAceptarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAceptarEdicion.setBounds(296, 212, 164, 23);
		panel.add(btnAceptarEdicion);

		
		
		
		setLocationCenter();
		this.setVisible(true);
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
	
	public ButtonGroup getGrupoEstadoFisico() {
		return GrupoEstadoFisico;
	}


	public void setGrupoEstadoFisico(ButtonGroup grupoEstadoFisico) {
		GrupoEstadoFisico = grupoEstadoFisico;
	}


	public ButtonGroup getGrupoEstadoTecnico() {
		return GrupoEstadoTecnico;
	}


	public void setGrupoEstadoTecnico(ButtonGroup grupoEstadoTecnico) {
		GrupoEstadoTecnico = grupoEstadoTecnico;
	}


	public JRadioButton getRdbtnEspera() {
		return rdbtnEspera;
	}


	public void setRdbtnEspera(JRadioButton rdbtnEspera) {
		this.rdbtnEspera = rdbtnEspera;
	}


	public JRadioButton getRdbtnAceptado() {
		return rdbtnAceptado;
	}


	public void setRdbtnAceptado(JRadioButton rdbtnAceptado) {
		this.rdbtnAceptado = rdbtnAceptado;
	}


	public JRadioButton getRdbtnNoAceptado() {
		return rdbtnNoAceptado;
	}


	public void setRdbtnNoAceptado(JRadioButton rdbtnNoAceptado) {
		this.rdbtnNoAceptado = rdbtnNoAceptado;
	}


	public JRadioButton getRdbtnGarantia() {
		return rdbtnGarantia;
	}


	public void setRdbtnGarantia(JRadioButton rdbtnGarantia) {
		this.rdbtnGarantia = rdbtnGarantia;
	}


	public ButtonGroup getGrupoEstadoComercial() {
		return GrupoEstadoComercial;
	}


	public void setGrupoEstadoComercial(ButtonGroup grupoEstadoComercial) {
		GrupoEstadoComercial = grupoEstadoComercial;
	}


	public JButton getBtnAceptarEdicion() {
		return btnAceptarEdicion;
	}


	public void setBtnaceptarEdicion(JButton btnGuardarEdicion) {
		this.btnAceptarEdicion = btnGuardarEdicion;
	}


	public JRadioButton getRdbtnMDQ() {
		return rdbtnMDQ;
	}

	public void setRdbtnMDQ(JRadioButton rdbtnMDQ) {
		this.rdbtnMDQ = rdbtnMDQ;
	}

	public JRadioButton getRdbtnCABA() {
		return rdbtnCABA;
	}

	public void setRdbtnCABA(JRadioButton rdbtnCABA) {
		this.rdbtnCABA = rdbtnCABA;
	}

	public JRadioButton getRdbtnBRC() {
		return rdbtnBRC;
	}

	public void setRdbtnBRC(JRadioButton rdbtnBRC) {
		this.rdbtnBRC = rdbtnBRC;
	}

	public JRadioButton getRdbtnENVIADO() {
		return rdbtnENVIADO;
	}

	public void setRdbtnENVIADO(JRadioButton rdbtnENVIADO) {
		this.rdbtnENVIADO = rdbtnENVIADO;
	}

	public JRadioButton getRdbtnSinRevisar() {
		return rdbtnSinRevisar;
	}

	public void setRdbtnSinRevisar(JRadioButton rdbtnSinRevisar) {
		this.rdbtnSinRevisar = rdbtnSinRevisar;
	}

	public JRadioButton getRdbtnEnReparacion() {
		return rdbtnEnReparacion;
	}

	public void setRdbtnEnReparacion(JRadioButton rdbtnEnReparacion) {
		this.rdbtnEnReparacion = rdbtnEnReparacion;
	}

	public JRadioButton getRdbtnReparado() {
		return rdbtnReparado;
	}

	public void setRdbtnReparado(JRadioButton rdbtnReparado) {
		this.rdbtnReparado = rdbtnReparado;
	}

	public JRadioButton getRdbtnReparadoGarantia() {
		return rdbtnReparadoGarantia;
	}

	public void setRdbtnReparadoGarantia(JRadioButton rdbtnReparadoGarantia) {
		this.rdbtnReparadoGarantia = rdbtnReparadoGarantia;
	}

	public JRadioButton getRdbtnVendido() {
		return rdbtnVendido;
	}

	public void setRdbtnVendido(JRadioButton rdbtnVendido) {
		this.rdbtnVendido = rdbtnVendido;
	}

	public JRadioButton getRdbtnDesguace() {
		return rdbtnSinFalla;
	}

	public void setRdbtnDesguace(JRadioButton rdbtnDesguace) {
		this.rdbtnSinFalla = rdbtnDesguace;
	}

	public JRadioButton getRdbtnNoTieneReparacion() {
		return rdbtnSinReparacion;
	}

	public void setRdbtnNoTieneReparacion(JRadioButton rdbtnNoTieneReparacion) {
		this.rdbtnSinReparacion = rdbtnNoTieneReparacion;
	}

	public JRadioButton getRdbtnNoAceptaron() {
		return rdbtnNoAceptaron;
	}

	public void setRdbtnNoAceptaron(JRadioButton rdbtnNoAceptaron) {
		this.rdbtnNoAceptaron = rdbtnNoAceptaron;
	}
}

