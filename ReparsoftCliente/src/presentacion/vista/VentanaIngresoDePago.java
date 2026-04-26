package presentacion.vista;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorPresupuestos;
import presentacion.controlador.ControladorReparacion;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class VentanaIngresoDePago extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_4;
	private JPanel panel_5;

	@SuppressWarnings("unused")
	private ControladorReparacion controladorP;
	@SuppressWarnings("unused")
	private ControladorPresupuestos controlador;
	private JTextField textCliente;
	private JTextField textELS;
	private JTextField textEquipo;
	private JTextField textMarca;
	private JTextField textModelo;
	private JTextField textSerie;
	private JButton btnGuardarCambios;
	private JButton btnEditarPrecios;
	private JTextField textSucursal;

	@SuppressWarnings("unused")
	private ButtonGroup GrupoMoneda;
	private JTextField textAviso;
	private JTextField textEstadoFisico;
	private JTextField textEstadoTecnico;
	private JTextField textEstadoComercial;
	private JTextField textPrecioPeso;
	private JTextField textPrecioDolar;
	private JTextField textIngresoPago;
	private JTextField textIngresoPagoDolar;

	public VentanaIngresoDePago(ControladorPresupuestos controlador) {
		super();
		setResizable(false);
		this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 888, 536);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));
		panel.setBounds(353, 11, 511, 111);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblCliente = new JLabel("CLIENTE :");
		lblCliente.setForeground(new Color(0, 0, 0));
		lblCliente.setBounds(10, 56, 90, 22);
		panel.add(lblCliente);
		lblCliente.setFont(new Font("Cambria", Font.BOLD, 18));

		JLabel lblEls = new JLabel("ELS :");
		lblEls.setForeground(new Color(0, 0, 0));
		lblEls.setBounds(10, 4, 46, 22);
		panel.add(lblEls);
		lblEls.setFont(new Font("Cambria", Font.BOLD, 18));

		textELS = new JTextField();
		textELS.setEditable(false);
		textELS.setForeground(new Color(0, 0, 0));
		textELS.setBounds(117, 4, 106, 22);
		panel.add(textELS);
		textELS.setBorder(null);
		textELS.setBackground(SystemColor.activeCaption);
		textELS.setFont(new Font("Cambria", Font.BOLD, 18));
		textELS.setColumns(10);

		textCliente = new JTextField();
		textCliente.setEditable(false);
		textCliente.setForeground(new Color(0, 0, 0));
		textCliente.setBounds(117, 56, 384, 22);
		panel.add(textCliente);
		textCliente.setBorder(null);
		textCliente.setBackground(SystemColor.activeCaption);
		textCliente.setFont(new Font("Cambria", Font.BOLD, 18));
		textCliente.setColumns(10);

		JLabel Sucursal = new JLabel("SUCURSAL: ");
		Sucursal.setForeground(Color.BLACK);
		Sucursal.setFont(new Font("Cambria", Font.BOLD, 18));
		Sucursal.setBounds(10, 82, 97, 22);
		panel.add(Sucursal);

		textSucursal = new JTextField();
		textSucursal.setForeground(Color.BLACK);
		textSucursal.setFont(new Font("Cambria", Font.BOLD, 18));
		textSucursal.setEditable(false);
		textSucursal.setColumns(10);
		textSucursal.setBorder(null);
		textSucursal.setBackground(SystemColor.activeCaption);
		textSucursal.setBounds(117, 82, 384, 22);
		panel.add(textSucursal);

		JLabel lblAviso_2 = new JLabel("AVISO :");
		lblAviso_2.setForeground(Color.BLACK);
		lblAviso_2.setFont(new Font("Cambria", Font.BOLD, 18));
		lblAviso_2.setBounds(10, 30, 69, 22);
		panel.add(lblAviso_2);

		textAviso = new JTextField();
		textAviso.setForeground(Color.BLACK);
		textAviso.setFont(new Font("Cambria", Font.BOLD, 18));
		textAviso.setEditable(false);
		textAviso.setColumns(10);
		textAviso.setBorder(null);
		textAviso.setBackground(SystemColor.activeCaption);
		textAviso.setBounds(117, 30, 106, 22);
		panel.add(textAviso);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));
		panel_1.setBounds(21, 133, 843, 109);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblEquipo = new JLabel("Equipo:");
		lblEquipo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblEquipo.setBounds(32, 11, 60, 20);
		panel_1.add(lblEquipo);
		lblEquipo.setForeground(Color.BLACK);
		lblEquipo.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblModelo.setBounds(32, 55, 60, 20);
		panel_1.add(lblModelo);
		lblModelo.setForeground(Color.BLACK);
		lblModelo.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblMarca.setBounds(32, 33, 60, 20);
		panel_1.add(lblMarca);
		lblMarca.setForeground(Color.BLACK);
		lblMarca.setFont(new Font("Cambria", Font.BOLD, 15));

		JLabel lblNSerie = new JLabel("N Serie:");
		lblNSerie.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblNSerie.setBounds(32, 77, 60, 20);
		panel_1.add(lblNSerie);
		lblNSerie.setForeground(Color.BLACK);
		lblNSerie.setFont(new Font("Cambria", Font.BOLD, 15));

		textEquipo = new JTextField();
		textEquipo.setEditable(false);
		textEquipo.setForeground(Color.BLACK);
		textEquipo.setFont(new Font("Cambria", Font.PLAIN, 15));
		textEquipo.setColumns(10);
		textEquipo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textEquipo.setBackground(SystemColor.inactiveCaption);
		textEquipo.setBounds(101, 11, 341, 20);
		panel_1.add(textEquipo);

		textMarca = new JTextField();
		textMarca.setEditable(false);
		textMarca.setForeground(Color.BLACK);
		textMarca.setFont(new Font("Cambria", Font.PLAIN, 15));
		textMarca.setColumns(10);
		textMarca.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textMarca.setBackground(SystemColor.inactiveCaption);
		textMarca.setBounds(101, 33, 341, 20);
		panel_1.add(textMarca);

		textModelo = new JTextField();
		textModelo.setEditable(false);
		textModelo.setForeground(Color.BLACK);
		textModelo.setFont(new Font("Cambria", Font.PLAIN, 15));
		textModelo.setColumns(10);
		textModelo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textModelo.setBackground(SystemColor.inactiveCaption);
		textModelo.setBounds(101, 55, 341, 20);
		panel_1.add(textModelo);

		textSerie = new JTextField();
		textSerie.setEditable(false);
		textSerie.setForeground(Color.BLACK);
		textSerie.setFont(new Font("Cambria", Font.PLAIN, 15));
		textSerie.setColumns(10);
		textSerie.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textSerie.setBackground(SystemColor.inactiveCaption);
		textSerie.setBounds(101, 77, 341, 20);
		panel_1.add(textSerie);

		textEstadoFisico = new JTextField();
		textEstadoFisico.setForeground(Color.BLACK);
		textEstadoFisico.setFont(new Font("Cambria", Font.PLAIN, 15));
		textEstadoFisico.setEditable(false);
		textEstadoFisico.setColumns(10);
		textEstadoFisico.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textEstadoFisico.setBackground(SystemColor.inactiveCaption);
		textEstadoFisico.setBounds(603, 11, 207, 20);
		panel_1.add(textEstadoFisico);

		JLabel lblEstadoFsico = new JLabel("Estado Físico:");
		lblEstadoFsico.setForeground(Color.BLACK);
		lblEstadoFsico.setFont(new Font("Cambria", Font.BOLD, 15));
		lblEstadoFsico.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblEstadoFsico.setBounds(466, 11, 127, 20);
		panel_1.add(lblEstadoFsico);

		JLabel lblEstadoTcnico = new JLabel("Estado Técnico");
		lblEstadoTcnico.setForeground(Color.BLACK);
		lblEstadoTcnico.setFont(new Font("Cambria", Font.BOLD, 15));
		lblEstadoTcnico.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblEstadoTcnico.setBounds(466, 33, 127, 20);
		panel_1.add(lblEstadoTcnico);

		textEstadoTecnico = new JTextField();
		textEstadoTecnico.setForeground(Color.BLACK);
		textEstadoTecnico.setFont(new Font("Cambria", Font.PLAIN, 15));
		textEstadoTecnico.setEditable(false);
		textEstadoTecnico.setColumns(10);
		textEstadoTecnico.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textEstadoTecnico.setBackground(SystemColor.inactiveCaption);
		textEstadoTecnico.setBounds(603, 33, 207, 20);
		panel_1.add(textEstadoTecnico);

		JLabel lblEstadoComercial = new JLabel("Estado Comercial:");
		lblEstadoComercial.setForeground(Color.BLACK);
		lblEstadoComercial.setFont(new Font("Cambria", Font.BOLD, 15));
		lblEstadoComercial.setBorder(new MatteBorder(0, 0, 1, 0, (Color) SystemColor.controlDkShadow));
		lblEstadoComercial.setBounds(466, 55, 127, 20);
		panel_1.add(lblEstadoComercial);

		textEstadoComercial = new JTextField();
		textEstadoComercial.setForeground(Color.BLACK);
		textEstadoComercial.setFont(new Font("Cambria", Font.PLAIN, 15));
		textEstadoComercial.setEditable(false);
		textEstadoComercial.setColumns(10);
		textEstadoComercial.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textEstadoComercial.setBackground(SystemColor.inactiveCaption);
		textEstadoComercial.setBounds(603, 55, 207, 20);
		panel_1.add(textEstadoComercial);

		JLabel lblPresupuesto = new JLabel("INGRESO DE PAGO");
		lblPresupuesto.setBounds(20, 11, 309, 111);
		contentPane.add(lblPresupuesto);
		lblPresupuesto.setBackground(SystemColor.activeCaption);
		lblPresupuesto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPresupuesto.setForeground(new Color(0, 0, 139));
		lblPresupuesto.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPresupuesto.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPresupuesto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPresupuesto.setFont(new Font("Cambria", Font.BOLD, 24));

		GrupoMoneda = new ButtonGroup();

		btnGuardarCambios = new JButton("GUARDAR CAMBIOS");
		btnGuardarCambios.setFont(new Font("Cambria", Font.BOLD, 12));
		btnGuardarCambios.setBounds(510, 448, 185, 38);
		contentPane.add(btnGuardarCambios);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.inactiveCaption);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(95, 158, 160), null));
		panel_2.setBounds(21, 253, 841, 175);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		panel_4 = new JPanel();
		panel_4.setBounds(10, 57, 295, 53);
		panel_2.add(panel_4);
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_4.setLayout(null);

		JLabel lblTotalPesos = new JLabel("TOTAL EN PESOS:");
		lblTotalPesos.setBounds(10, 11, 128, 30);
		panel_4.add(lblTotalPesos);
		lblTotalPesos.setBorder(null);
		lblTotalPesos.setFont(new Font("Cambria", Font.PLAIN, 16));
		
				textPrecioPeso = new JTextField(10);
				textPrecioPeso.setHorizontalAlignment(SwingConstants.RIGHT);
				textPrecioPeso.setEditable(false);
				textPrecioPeso.setBorder(null);
				textPrecioPeso.setBackground(new Color(192, 192, 192));
				textPrecioPeso.setFont(new Font("Cambria", Font.PLAIN, 16));
				textPrecioPeso.setBounds(148, 11, 140, 30);
				panel_4.add(textPrecioPeso);
				textPrecioPeso.setColumns(10);

		panel_5 = new JPanel();
		panel_5.setBounds(10, 111, 295, 53);
		panel_2.add(panel_5);
		panel_5.setBackground(Color.LIGHT_GRAY);
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_5.setLayout(null);

		JLabel lbltotaldolares = new JLabel("TOTAL EN DÓLARES:");
		lbltotaldolares.setBounds(10, 11, 146, 30);
		panel_5.add(lbltotaldolares);
		lbltotaldolares.setBorder(null);
		lbltotaldolares.setFont(new Font("Cambria", Font.PLAIN, 16));

		textPrecioDolar = new JTextField(10);
		textPrecioDolar.setHorizontalAlignment(SwingConstants.RIGHT);
		textPrecioDolar.setEditable(false);
		textPrecioDolar.setBorder(null);
		textPrecioDolar.setFont(new Font("Cambria", Font.PLAIN, 16));
		textPrecioDolar.setBackground(new Color(192, 192, 192));
		textPrecioDolar.setBounds(168, 11, 120, 30);
		panel_5.add(textPrecioDolar);
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBounds(461, 57, 295, 53);
		panel_2.add(panel_4_1);
		panel_4_1.setLayout(null);
		panel_4_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_4_1.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblPagoRecibidoEn = new JLabel("PAGO EN PESOS:");
		lblPagoRecibidoEn.setFont(new Font("Cambria", Font.PLAIN, 16));
		lblPagoRecibidoEn.setBorder(null);
		lblPagoRecibidoEn.setBounds(10, 11, 125, 30);
		panel_4_1.add(lblPagoRecibidoEn);
		
		textIngresoPago = new JTextField(10);
		textIngresoPago.setHorizontalAlignment(SwingConstants.RIGHT);
		textIngresoPago.setFont(new Font("Cambria", Font.PLAIN, 16));
		textIngresoPago.setBorder(null);
		textIngresoPago.setBackground(Color.LIGHT_GRAY);
		textIngresoPago.setBounds(145, 11, 140, 30);
		panel_4_1.add(textIngresoPago);
		
		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setVisible(false);
		panel_4_1_1.setLayout(null);
		panel_4_1_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(102, 205, 170), null));
		panel_4_1_1.setBackground(Color.LIGHT_GRAY);
		panel_4_1_1.setBounds(461, 111, 295, 53);
		panel_2.add(panel_4_1_1);
		
		JLabel lblPagoEnDolares = new JLabel("PAGO EN DOLARES:");
		lblPagoEnDolares.setVisible(false);
		lblPagoEnDolares.setFont(new Font("Cambria", Font.PLAIN, 16));
		lblPagoEnDolares.setBorder(null);
		lblPagoEnDolares.setBounds(10, 11, 146, 30);
		panel_4_1_1.add(lblPagoEnDolares);
		
		textIngresoPagoDolar = new JTextField(10);
		textIngresoPagoDolar.setVisible(false);
		textIngresoPagoDolar.setHorizontalAlignment(SwingConstants.RIGHT);
		textIngresoPagoDolar.setFont(new Font("Cambria", Font.PLAIN, 16));
		textIngresoPagoDolar.setBorder(null);
		textIngresoPagoDolar.setBackground(Color.LIGHT_GRAY);
		textIngresoPagoDolar.setBounds(165, 11, 120, 30);
		panel_4_1_1.add(textIngresoPagoDolar);
		
		JLabel lblPresupuestoEnviado = new JLabel("PRESUPUESTO ENVIADO");
		lblPresupuestoEnviado.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblPresupuestoEnviado.setForeground(Color.BLACK);
		lblPresupuestoEnviado.setFont(new Font("Cambria", Font.BOLD, 18));
		lblPresupuestoEnviado.setBounds(10, 11, 295, 22);
		panel_2.add(lblPresupuestoEnviado);
		
		JLabel lblPagoRecibido = new JLabel("PAGO RECIBIDO");
		lblPagoRecibido.setForeground(Color.BLACK);
		lblPagoRecibido.setFont(new Font("Cambria", Font.BOLD, 18));
		lblPagoRecibido.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		lblPagoRecibido.setBounds(461, 11, 295, 22);
		panel_2.add(lblPagoRecibido);
		
		btnEditarPrecios = new JButton("EDITAR PRECIOS");
		btnEditarPrecios.setFont(new Font("Cambria", Font.BOLD, 12));
		btnEditarPrecios.setBounds(188, 448, 185, 38);
		contentPane.add(btnEditarPrecios);
		
	

		this.setVisible(true);

	}

	public JTextField getTextPrecioPeso() {
		return textPrecioPeso;
	}

	public void setTextPrecioPeso(String textPresioPesotexto) {
		this.textPrecioPeso.setText(textPresioPesotexto);
	}
	
	
	
	
	public JTextField getTextPrecioDolar() {
		return textPrecioDolar;
	}


	public void setTextPrecioDolar(String textPrecioDolar) {
		this.textPrecioDolar.setText(textPrecioDolar); 
	}
	
	
	public JTextField gettextIngresoPago() {
		return textIngresoPago;
	}


	public void settextIngresoPago(String textIngresoPago) {
		this.textIngresoPago.setText(textIngresoPago); 
	}

	public JTextField getTextCliente() {
		return textCliente;
	}

	public void setTextCliente(JTextField textCliente) {
		this.textCliente = textCliente;
	}

	public JTextField getTextELS() {
		return textELS;
	}

	public void setTextELS(JTextField textELS) {
		this.textELS = textELS;
	}

	public JTextField getTextEquipo() {
		return textEquipo;
	}

	public void setTextEquipo(JTextField textEquipo) {
		this.textEquipo = textEquipo;
	}

	public JTextField getTextMarca() {
		return textMarca;
	}

	public void setTextMarca(JTextField textMarca) {
		this.textMarca = textMarca;
	}

	public JTextField getTextModelo() {
		return textModelo;
	}

	public void setTextModelo(JTextField textModelo) {
		this.textModelo = textModelo;
	}

	public JTextField getTextSerie() {
		return textSerie;
	}

	public void setTextSerie(JTextField textSerie) {
		this.textSerie = textSerie;
	}

	public JTextField getTextSucursal() {
		return textSucursal;
	}

	public void setTextSucursal(JTextField textSucursal) {
		this.textSucursal = textSucursal;
	}

	public JTextField getTextEstadoFisico() {
		return textEstadoFisico;
	}

	public void setTextEstadoFisico(JTextField textEstadoFisico) {
		this.textEstadoFisico = textEstadoFisico;
	}

	public JTextField getTextEstadoTecnico() {
		return textEstadoTecnico;
	}

	public void setTextEstadoTecnico(JTextField textEstadoTecnico) {
		this.textEstadoTecnico = textEstadoTecnico;
	}

	public JTextField getTextEstadoComercial() {
		return textEstadoComercial;
	}

	public void setTextEstadoComercial(JTextField textEstadoComercial) {
		this.textEstadoComercial = textEstadoComercial;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public void setBtnGuardarCambios(JButton btnGuardarCambios) {
		this.btnGuardarCambios = btnGuardarCambios;
	}

	public JTextField getTextAviso() {
		return textAviso;
	}

	public void setTextAviso(JTextField textAviso) {
		this.textAviso = textAviso;
	}

	public JButton getBtnEditarPrecios() {
		return btnEditarPrecios;
	}

	public void setBtnEditarPrecios(JButton btnEditarPrecios) {
		this.btnEditarPrecios = btnEditarPrecios;
	}
}