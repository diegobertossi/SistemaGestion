package presentacion.vista;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import presentacion.controlador.ControladorReparacion;

import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.border.EtchedBorder;
import java.awt.Cursor;

public class VentanaEstados extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JRadioButton rdbtnMDP;
	private JRadioButton rdbtnCABA;
	private JRadioButton rdbtnBRC;
	private JRadioButton rdbtnENVIADO;
	
	private ButtonGroup GrupoEstadoFisico;
	private JButton btnHabilitarLugarIngreso;
	
	private JRadioButton rdbtnSinRevisar;
	private JRadioButton rdbtnEnReparacion;
	private JRadioButton rdbtnReparado;
	private JRadioButton rdbtnReparadoGarantia;
	private JRadioButton rdbtnVendido;
	private JRadioButton rdbtnDesguace;
	private JRadioButton rdbtnSinFalla;
	private JRadioButton rdbtnSinReparacion;
	private JRadioButton rdbtnNoAceptaron;
	private JRadioButton rdbtnRecambio;
	
	private ButtonGroup GrupoEstadoTecnico;
	
	private JRadioButton rdbtnEspera;
	private JRadioButton rdbtnAceptado;
	private JRadioButton rdbtnNoAceptado;
	private JRadioButton rdbtnGarantia;
	private JRadioButton rdbtnGarantiaSiemens;
	
	
	private ButtonGroup GrupoEstadoComercial;
	
	
	private JButton btnAceptarEdicion ;
	
	
	private JLabel lblEstadoFisico;
	private JLabel lblEstadoTecnico;
	private JLabel lblEstadoComercial;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	
	private ButtonGroup GrupoLugarDeIngreso;
	
	
	private JPanel panelLugarDeIngreso;
	private JRadioButton rdbtnIngresoMDP;
	private JRadioButton rdbtnIngresoBRC;
	private JRadioButton rdbtnIngresoCABA;

	public VentanaEstados(ControladorReparacion controlador) 
	{
		super();
		setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 772, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 756, 246);
		contentPane.add(panel);
		panel.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(22, 36, 136, 130);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		
		rdbtnMDP = new JRadioButton("MDP");
		rdbtnMDP.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		rdbtnMDP.setBackground(SystemColor.activeCaption);
		rdbtnMDP.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnMDP.setBounds(6, 27, 109, 23);
		panel_2.add(rdbtnMDP);
		
		rdbtnDesguace = new JRadioButton("Desgüace");
		rdbtnDesguace.setBackground(SystemColor.activeCaption);
		rdbtnDesguace.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnDesguace.setBounds(6, 102, 109, 23);
		panel_2.add(rdbtnDesguace);
		
		rdbtnBRC = new JRadioButton("BRC");
		rdbtnBRC.setBackground(SystemColor.activeCaption);
		rdbtnBRC.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnBRC.setBounds(6, 52, 109, 23);
		panel_2.add(rdbtnBRC);
		
		rdbtnENVIADO = new JRadioButton("Enviado");
		rdbtnENVIADO.setBackground(SystemColor.activeCaption);
		rdbtnENVIADO.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnENVIADO.setBounds(6, 77, 109, 23);
		panel_2.add(rdbtnENVIADO);
		
		rdbtnCABA = new JRadioButton("CABA");
		rdbtnCABA.setBackground(SystemColor.activeCaption);
		rdbtnCABA.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnCABA.setBounds(6, 2, 109, 23);
		panel_2.add(rdbtnCABA);
		
		GrupoEstadoFisico = new ButtonGroup();
		GrupoEstadoFisico.add(rdbtnMDP);
		GrupoEstadoFisico.add(rdbtnBRC);
		GrupoEstadoFisico.add(rdbtnENVIADO);
		GrupoEstadoFisico.add(rdbtnCABA);
		GrupoEstadoFisico.add(rdbtnDesguace);
		
		panel_3 = new JPanel();
		panel_3.setFont(new Font("Cambria", Font.BOLD, 12));
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(180, 36, 324, 130);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		
		rdbtnSinRevisar = new JRadioButton("Sin Revisar");
		rdbtnSinRevisar.setBackground(SystemColor.activeCaption);
		rdbtnSinRevisar.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnSinRevisar.setBounds(6, 2, 109, 23);
		panel_3.add(rdbtnSinRevisar);
		
		rdbtnEnReparacion = new JRadioButton("En Reparación");
		rdbtnEnReparacion.setBackground(SystemColor.activeCaption);
		rdbtnEnReparacion.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnEnReparacion.setBounds(6, 27, 109, 23);
		panel_3.add(rdbtnEnReparacion);
		
		rdbtnReparado = new JRadioButton("Reparado");
		rdbtnReparado.setBackground(SystemColor.activeCaption);
		rdbtnReparado.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnReparado.setBounds(6, 52, 109, 23);
		panel_3.add(rdbtnReparado);
		
		rdbtnReparadoGarantia = new JRadioButton("Reparado En Garantía");
		rdbtnReparadoGarantia.setBackground(SystemColor.activeCaption);
		rdbtnReparadoGarantia.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnReparadoGarantia.setBounds(6, 77, 152, 23);
		panel_3.add(rdbtnReparadoGarantia);
				
		rdbtnVendido = new JRadioButton("Vendido");
		rdbtnVendido.setBackground(SystemColor.activeCaption);
		rdbtnVendido.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnVendido.setBounds(155, 2, 139, 23);
		panel_3.add(rdbtnVendido);
		
		rdbtnSinFalla = new JRadioButton("Sin Falla");
		rdbtnSinFalla.setBackground(SystemColor.activeCaption);
		rdbtnSinFalla.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnSinFalla.setBounds(155, 27, 139, 23);
		panel_3.add(rdbtnSinFalla);
		
		rdbtnSinReparacion = new JRadioButton("Sin Reparación");
		rdbtnSinReparacion.setBackground(SystemColor.activeCaption);
		rdbtnSinReparacion.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnSinReparacion.setBounds(155, 52, 139, 23);
		panel_3.add(rdbtnSinReparacion);
		
		rdbtnNoAceptaron = new JRadioButton("No Aceptaron Reparación");
		rdbtnNoAceptaron.setBackground(SystemColor.activeCaption);
		rdbtnNoAceptaron.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnNoAceptaron.setBounds(155, 77, 163, 23);
		panel_3.add(rdbtnNoAceptaron);
		
		rdbtnRecambio = new JRadioButton("Sin Rep-Recambio Propuesto");
		rdbtnRecambio.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnRecambio.setBackground(SystemColor.activeCaption);
		rdbtnRecambio.setBounds(6, 102, 221, 23);
		panel_3.add(rdbtnRecambio);
		
		GrupoEstadoTecnico = new ButtonGroup();
		GrupoEstadoTecnico.add(rdbtnSinRevisar);
		GrupoEstadoTecnico.add(rdbtnEnReparacion);
		GrupoEstadoTecnico.add(rdbtnReparado);
		GrupoEstadoTecnico.add(rdbtnReparadoGarantia);
		GrupoEstadoTecnico.add(rdbtnVendido);
		GrupoEstadoTecnico.add(rdbtnSinFalla);
		GrupoEstadoTecnico.add(rdbtnSinReparacion);
		GrupoEstadoTecnico.add(rdbtnNoAceptaron);
		GrupoEstadoTecnico.add(rdbtnRecambio);
		

		
		

		panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.activeCaption);
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(526, 36, 206, 130);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		rdbtnEspera = new JRadioButton("A la Espera de Aceptación");
		rdbtnEspera.setBackground(SystemColor.activeCaption);
		rdbtnEspera.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnEspera.setBounds(6, 2, 194, 23);
		panel_4.add(rdbtnEspera);
		
		rdbtnAceptado = new JRadioButton("Aceptado");
		rdbtnAceptado.setBackground(SystemColor.activeCaption);
		rdbtnAceptado.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnAceptado.setBounds(6, 27, 149, 23);
		panel_4.add(rdbtnAceptado);
		
		rdbtnNoAceptado = new JRadioButton("NO Aceptado");
		rdbtnNoAceptado.setBackground(SystemColor.activeCaption);
		rdbtnNoAceptado.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnNoAceptado.setBounds(6, 52, 149, 23);
		panel_4.add(rdbtnNoAceptado);
		
		rdbtnGarantia = new JRadioButton("Garantía");
		rdbtnGarantia.setBackground(SystemColor.activeCaption);
		rdbtnGarantia.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnGarantia.setBounds(6, 77, 149, 23);
		panel_4.add(rdbtnGarantia);
		

		
		rdbtnGarantiaSiemens = new JRadioButton("Garantía Siemens");
		rdbtnGarantiaSiemens.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnGarantiaSiemens.setBackground(SystemColor.activeCaption);
		rdbtnGarantiaSiemens.setBounds(6, 102, 149, 23);
		panel_4.add(rdbtnGarantiaSiemens);
		
		
		GrupoEstadoComercial = new ButtonGroup();
		GrupoEstadoComercial.add(rdbtnEspera);
		GrupoEstadoComercial.add(rdbtnAceptado);
		GrupoEstadoComercial.add(rdbtnNoAceptado);
		GrupoEstadoComercial.add(rdbtnGarantia);
		GrupoEstadoComercial.add(rdbtnGarantiaSiemens);
		
		
		lblEstadoFisico = new JLabel("ESTADO FÍSICO:");
		lblEstadoFisico.setBounds(22, 11, 136, 18);
		panel.add(lblEstadoFisico);
		lblEstadoFisico.setFont(new Font("Cambria", Font.BOLD, 16));
		
		lblEstadoComercial = new JLabel("ESTADO COMERCIAL:");
		lblEstadoComercial.setBounds(526, 11, 206, 18);
		panel.add(lblEstadoComercial);
		lblEstadoComercial.setFont(new Font("Cambria", Font.BOLD, 16));
		
		lblEstadoTecnico = new JLabel("ESTADO TÉCNICO:");
		lblEstadoTecnico.setBounds(180, 11, 300, 18);
		panel.add(lblEstadoTecnico);
		lblEstadoTecnico.setFont(new Font("Cambria", Font.BOLD, 16));
		
		btnAceptarEdicion = new JButton("ACEPTAR EDICIÓN");
		btnAceptarEdicion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAceptarEdicion.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAceptarEdicion.setBounds(563, 185, 164, 34);
		panel.add(btnAceptarEdicion);
		
		panelLugarDeIngreso = new JPanel();
		panelLugarDeIngreso.setBounds(180, 185, 219, 34);
		panel.add(panelLugarDeIngreso);
		panelLugarDeIngreso.setLayout(null);
		panelLugarDeIngreso.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelLugarDeIngreso.setBackground(SystemColor.activeCaption);
		
		rdbtnIngresoMDP = new JRadioButton("MDP");
		rdbtnIngresoMDP.setEnabled(false);
		rdbtnIngresoMDP.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnIngresoMDP.setBackground(SystemColor.activeCaption);
		rdbtnIngresoMDP.setBounds(80, 5, 58, 23);
		panelLugarDeIngreso.add(rdbtnIngresoMDP);
		
		rdbtnIngresoBRC = new JRadioButton("BRC");
		rdbtnIngresoBRC.setEnabled(false);
		rdbtnIngresoBRC.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnIngresoBRC.setBackground(SystemColor.activeCaption);
		rdbtnIngresoBRC.setBounds(149, 5, 58, 23);
		panelLugarDeIngreso.add(rdbtnIngresoBRC);
		
		rdbtnIngresoCABA = new JRadioButton("CABA");
		rdbtnIngresoCABA.setEnabled(false);
		rdbtnIngresoCABA.setFont(new Font("Cambria", Font.PLAIN, 12));
		rdbtnIngresoCABA.setBackground(SystemColor.activeCaption);
		rdbtnIngresoCABA.setBounds(11, 5, 58, 23);
		panelLugarDeIngreso.add(rdbtnIngresoCABA);
		
		
		GrupoLugarDeIngreso = new ButtonGroup();
		GrupoLugarDeIngreso.add(rdbtnIngresoMDP);
		GrupoLugarDeIngreso.add(rdbtnIngresoBRC);
		GrupoLugarDeIngreso.add(rdbtnIngresoCABA);
		
		
		
		JLabel lblLugarDeIngreso = new JLabel("LUGAR DE INGRESO:");
		lblLugarDeIngreso.setFont(new Font("Cambria", Font.BOLD, 16));
		lblLugarDeIngreso.setBounds(22, 201, 158, 18);
		panel.add(lblLugarDeIngreso);
		
		btnHabilitarLugarIngreso = new JButton("EDITAR");
		btnHabilitarLugarIngreso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHabilitarLugarIngreso.setFont(new Font("Cambria", Font.BOLD, 14));
		btnHabilitarLugarIngreso.setBounds(409, 185, 95, 34);
		panel.add(btnHabilitarLugarIngreso);

		
		
		
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


	public JRadioButton getRdbtnMDP() {
		return rdbtnMDP;
	}

	public void setRdbtnMDP(JRadioButton rdbtnMDP) {
		this.rdbtnMDP = rdbtnMDP;
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




	public ButtonGroup getGrupoLugarDeIngreso() {
		return GrupoLugarDeIngreso;
	}




	public void setGrupoLugarDeIngreso(ButtonGroup grupoLugarDeIngreso) {
		GrupoLugarDeIngreso = grupoLugarDeIngreso;
	}




	public JPanel getPanelLugarDeIngreso() {
		return panelLugarDeIngreso;
	}




	public void setPanelLugarDeIngreso(JPanel panelLugarDeIngreso) {
		this.panelLugarDeIngreso = panelLugarDeIngreso;
	}




	public JRadioButton getRdbtnIngresoMDP() {
		return rdbtnIngresoMDP;
	}




	public void setRdbtnIngresoMDP(JRadioButton rdbtnIngresoMDP) {
		this.rdbtnIngresoMDP = rdbtnIngresoMDP;
	}




	public JRadioButton getRdbtnIngresoBRC() {
		return rdbtnIngresoBRC;
	}




	public void setRdbtnIngresoBRC(JRadioButton rdbtnIngresoBRC) {
		this.rdbtnIngresoBRC = rdbtnIngresoBRC;
	}




	public JRadioButton getRdbtnIngresoCABA() {
		return rdbtnIngresoCABA;
	}




	public void setRdbtnIngresoCABA(JRadioButton rdbtnIngresoCABA) {
		this.rdbtnIngresoCABA = rdbtnIngresoCABA;
	}




	public JButton getBtnHabilitarLugarIngreso() {
		return btnHabilitarLugarIngreso;
	}




	public void setBtnHabilitarLugarIngreso(JButton btnHabilitarLugarIngreso) {
		this.btnHabilitarLugarIngreso = btnHabilitarLugarIngreso;
	}




	public JRadioButton getRdbtnRecambio() {
		return rdbtnRecambio;
	}




	public void setRdbtnRecambio(JRadioButton rdbtnRecambio) {
		this.rdbtnRecambio = rdbtnRecambio;
	}




	public JRadioButton getRdbtnGarantiaSiemens() {
		return rdbtnGarantiaSiemens;
	}




	public void setRdbtnGarantiaSiemens(JRadioButton rdbtnGarantiaSiemens) {
		this.rdbtnGarantiaSiemens = rdbtnGarantiaSiemens;
	}
}

