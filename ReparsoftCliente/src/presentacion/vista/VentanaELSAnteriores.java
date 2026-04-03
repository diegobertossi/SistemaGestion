package presentacion.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import modelo.ELSAnterior;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.border.MatteBorder;

public class VentanaELSAnteriores extends JFrame {
	private JPanel contentPane;
	private JTextField txtEls;
	private List<ELSAnterior> registros;
	private int index = 0;
	private JTextField textMarca;
	private JTextField textModelo;
	private JTextField textNombreEquipo;
	private JTextField textCliente;
	private JTextField textSucursal;
	private JTextArea textInformeCliente;
	private JTextArea textDiagnostico;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;

	private JTextFieldDateEditor FechaEntrada;
	private JTextFieldDateEditor FechaReparacion;
	private JTextFieldDateEditor FechaRespuesta;
	private JTextField textPrecioPeso;
	private JTextField textPrecioDolar;
	private JTextField textTecnico;
	private JTextField textOrdenCompra;
	private JTextField textClienteCliente;
	private JTextField textRemitoCliente;

	private JTextField textEstadoFisico;
	private JTextField textEstadoTecnico;
	private JTextField textEstadoComercial;
	private JTextField textRemitoSalida;
	private JTextField textPago;
	

	public VentanaELSAnteriores(List<ELSAnterior> registros) {
		this.registros = registros;
		setTitle("Histórico de Reparaciones - Sistema Anterior");
		setBounds(100, 100, 900, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- Diseño similar al tuyo ---
		JLabel lblTitulo = new JLabel("CONSULTA ELS ANTERIORES");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBounds(300, 10, 300, 30);
		contentPane.add(lblTitulo);

		txtEls = new JTextField();
		txtEls.setEditable(false);
		txtEls.setBounds(150, 60, 256, 25);
		contentPane.add(txtEls);

		// Botones de navegación
		JButton btnAnt = new JButton("ANTERIOR");
		btnAnt.setBounds(250, 600, 120, 35);
		btnAnt.addActionListener(e -> navegar(-1));
		contentPane.add(btnAnt);

		JButton btnSig = new JButton("SIGUIENTE");
		btnSig.setBounds(400, 600, 120, 35);
		btnSig.addActionListener(e -> navegar(1));
		contentPane.add(btnSig);

		textMarca = new JTextField();
		textMarca.setEditable(false);
		textMarca.setBounds(150, 96, 256, 25);
		contentPane.add(textMarca);

		textModelo = new JTextField();
		textModelo.setEditable(false);
		textModelo.setBounds(150, 132, 256, 25);
		contentPane.add(textModelo);

		textNombreEquipo = new JTextField();
		textNombreEquipo.setEditable(false);
		textNombreEquipo.setBounds(150, 168, 256, 25);
		contentPane.add(textNombreEquipo);

		textCliente = new JTextField();
		textCliente.setEditable(false);
		textCliente.setBounds(150, 204, 256, 25);
		contentPane.add(textCliente);

		textSucursal = new JTextField();
		textSucursal.setEditable(false);
		textSucursal.setBounds(150, 240, 256, 25);
		contentPane.add(textSucursal);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(150, 276, 383, 144);
		contentPane.add(scrollPane);

		textInformeCliente = new JTextArea();
		scrollPane.setViewportView(textInformeCliente);
		textInformeCliente.setWrapStyleWord(true);
		textInformeCliente.setMargin(new Insets(5, 5, 5, 5));
		textInformeCliente.setLineWrap(true);
		textInformeCliente.setFont(new Font("Cambria", Font.PLAIN, 12));
		textInformeCliente.setEditable(false);
		textInformeCliente.setDragEnabled(true);
		textInformeCliente.setAutoscrolls(false);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(150, 431, 383, 150);
		contentPane.add(scrollPane_1);

		
		textTecnico = new JTextField();
		textTecnico.setEditable(false);
		textTecnico.setBounds(557, 168, 211, 25);
		contentPane.add(textTecnico);
		textOrdenCompra = new JTextField();
		textOrdenCompra.setEditable(false);
		textOrdenCompra.setBounds(557, 204, 211, 25);
		contentPane.add(textOrdenCompra);
		textClienteCliente = new JTextField();
		textClienteCliente.setEditable(false);
		textClienteCliente.setBounds(557, 240, 211, 25);
		contentPane.add(textClienteCliente);
		textRemitoCliente = new JTextField();
		textRemitoCliente.setEditable(false);
		textRemitoCliente.setBounds(557, 276, 211, 25);
		contentPane.add(textRemitoCliente);
		textEstadoFisico = new JTextField();
		textEstadoFisico.setEditable(false);
		textEstadoFisico.setBounds(557, 312, 211, 25);
		contentPane.add(textEstadoFisico);
		textEstadoTecnico = new JTextField();
		textEstadoTecnico.setEditable(false);
		textEstadoTecnico.setBounds(557, 348, 211, 25);
		contentPane.add(textEstadoTecnico);
		textEstadoComercial = new JTextField();
		textEstadoComercial.setEditable(false);
		textEstadoComercial.setBounds(557, 384, 211, 25);
		contentPane.add(textEstadoComercial);
		textRemitoSalida = new JTextField();
		textRemitoSalida.setEditable(false);
		textRemitoSalida.setBounds(557, 420, 211, 25);
		contentPane.add(textRemitoSalida);
		textPago = new JTextField();
		textPago.setEditable(false);
		textPago.setBounds(557, 456, 211, 25);
		contentPane.add(textPago);
		
		
		textDiagnostico = new JTextArea();
		scrollPane_1.setViewportView(textDiagnostico);
		textDiagnostico.setWrapStyleWord(true);
		textDiagnostico.setMargin(new Insets(5, 5, 5, 5));
		textDiagnostico.setLineWrap(true);
		textDiagnostico.setFont(new Font("Cambria", Font.PLAIN, 12));
		textDiagnostico.setEditable(false);

		FechaEntrada = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		FechaEntrada.setHorizontalAlignment(SwingConstants.CENTER);
		FechaEntrada.setFont(new Font("Cambria", Font.PLAIN, 14));
		FechaEntrada.setBackground(Color.WHITE);
		FechaEntrada.setBounds(557, 62, 211, 20);
		contentPane.add(FechaEntrada);

		FechaReparacion = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		FechaReparacion.setHorizontalAlignment(SwingConstants.CENTER);
		FechaReparacion.setFont(new Font("Cambria", Font.PLAIN, 14));
		FechaReparacion.setBackground(Color.WHITE);
		FechaReparacion.setBounds(557, 96, 211, 20);
		contentPane.add(FechaReparacion);
		
		FechaRespuesta = new JTextFieldDateEditor("dd/MM/yyyy", "##-##-####", '-');
		FechaRespuesta.setHorizontalAlignment(SwingConstants.CENTER);
		FechaRespuesta.setFont(new Font("Cambria", Font.PLAIN, 14));
		FechaRespuesta.setBackground(Color.WHITE);
		FechaRespuesta.setBounds(557, 132, 211, 20);
		contentPane.add(FechaRespuesta);
		
		
		textPrecioPeso = new JTextField(10);
		textPrecioPeso.setHorizontalAlignment(SwingConstants.RIGHT);
		textPrecioPeso.setForeground(SystemColor.desktop);
		textPrecioPeso.setFont(new Font("Cambria", Font.BOLD, 12));
		textPrecioPeso.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		textPrecioPeso.setBackground(SystemColor.inactiveCaption);
		textPrecioPeso.setBounds(693, 525, 95, 15);
		contentPane.add(textPrecioPeso);

		textPrecioDolar = new JTextField(10);
		textPrecioDolar.setHorizontalAlignment(SwingConstants.RIGHT);
		textPrecioDolar.setForeground(SystemColor.desktop);
		textPrecioDolar.setFont(new Font("Cambria", Font.BOLD, 12));
		textPrecioDolar.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		textPrecioDolar.setBackground(SystemColor.inactiveCaption);
		textPrecioDolar.setBounds(693, 551, 95, 15);
		contentPane.add(textPrecioDolar);

		if (registros != null && !registros.isEmpty()) {
			actualizarCampos();
		} else {
			JOptionPane.showMessageDialog(this, "No se encontraron registros en el archivo Access.");
		}
	}

	private void navegar(int direccion) {
		int nuevoIndex = index + direccion;
		if (nuevoIndex >= 0 && nuevoIndex < registros.size()) {
			index = nuevoIndex;
			actualizarCampos();
		}
	}

	private void actualizarCampos() {
		ELSAnterior r = registros.get(index);
		txtEls.setText(r.getEls());
		textMarca.setText(r.getMarca());
		textModelo.setText(r.getModelo());
		textNombreEquipo.setText(r.getEquipo());
		textCliente.setText(r.getCliente());
		textSucursal.setText(r.getSucursal());
		textDiagnostico.setText(r.getDiagnostico());
		textInformeCliente.setText(r.getInformeCliente());
		FechaEntrada.setText(r.getFechaEntrada());
		FechaReparacion.setText(r.getFechaReparacion());
		FechaRespuesta.setText(r.getFechaRespuesta());
		
		textPrecioPeso.setText(String.valueOf(r.getPrecioPesos()));
		textPrecioDolar.setText(String.valueOf(r.getPrecioDolar()));
		textTecnico.setText(r.getTecnico());
		textOrdenCompra.setText(r.getOrdenCompra());
		textClienteCliente.setText(r.getClienteCliente());
		textRemitoCliente.setText(r.getRemitoCliente());
		textEstadoFisico.setText(r.getEstadoFisico());
		textEstadoTecnico.setText(r.getEstadoTecnico());
		textEstadoComercial.setText(r.getEstadoComercial());
		textRemitoSalida.setText(r.getRemitoSalida());
		textPago.setText(r.getPago());
		
		

		// imprimir en consola para verificar que se están cargando los datos
		// correctamente
//		System.out.println("Cargando ELS: " + r.getEls() + ", Marca: " + r.getMarca() + ", Modelo: " + r.getModelo()
//				+ ", Nombre Equipo: " + r.getEquipo() + ", Cliente: " + r.getCliente() + ", Sucursal: "
//				+ r.getSucursal() + ", Diagnóstico: " + "..." + ", Informe Cliente: " + "..." + ", Fecha Entrada: "
//				+ r.getFechaEntrada() + ", Precio Pesos: " + r.getPrecioPesos() + ", Precio Dolar: "
//				+ r.getPrecioDolar() + ", Nombre Tecnico: " + r.getTecnico()
//				+ ", OC: " + r.getOrdenCompra()
//				+ ", Cliente/Cliente: " + r.getClienteCliente()
//				+ ", Remito Cliente: " + r.getRemitoCliente()
//				+ ", Fecha Reparacion: " + r.getFechaReparacion()
//				+ ", Estado Fisico: " + r.getEstadoFisico()
//				+ ", Estado Tecnico: " + r.getEstadoTecnico()
//				+ ", Estado Comercial: " + r.getEstadoComercial()
//				+ ", Remito Salida: " + r.getRemitoSalida()
//				+ ", Pago: " + r.getPago()
//				+ ", Fecha Respuesta: " + r.getFechaRespuesta());

	}

	public JTextField getTextMarca() {
		return textMarca;
	}

	public void setTextMarca(JTextField textMarca) {
		this.textMarca = textMarca;
	}
}