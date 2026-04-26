package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.gestores.GestorArchivosExcel;
import presentacion.controlador.ControladorReparacion;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class VentanaExcel extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnRepar;
	private JButton btnCaja;
	private JButton btnDetalleGastos;
	private JButton btnAbrirTodos;

	public JButton getBtnDetalleGastos() {
		return btnDetalleGastos;
	}

	public void setBtnDetalleGastos(JButton btnDetalleGastos) {
		this.btnDetalleGastos = btnDetalleGastos;
	}

	@SuppressWarnings("unused")
	private ControladorReparacion controlador;

	public VentanaExcel(GestorArchivosExcel gestorExcel) {
		super();
		setResizable(false);
		// this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 155);

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
		this.setIconImage(icon);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnRepar = new JButton("<html><center>REPAR</html>");
		btnRepar.setForeground(new Color(255, 255, 255));
		btnRepar.setBackground(new Color(0, 153, 102));
		btnRepar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnRepar.setBounds(34, 28, 100, 42);
		contentPane.add(btnRepar);

		btnCaja = new JButton("<html><center>CAJA</html>");
		btnCaja.setForeground(new Color(255, 255, 255));
		btnCaja.setBackground(new Color(0, 153, 102));
		btnCaja.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCaja.setBounds(168, 28, 100, 42);
		contentPane.add(btnCaja);

		btnDetalleGastos = new JButton("<html><center>DETALLE DE GASTOS</html>");
		btnDetalleGastos.setForeground(new Color(255, 255, 255));
		btnDetalleGastos.setBackground(new Color(0, 153, 102));
		btnDetalleGastos.setFont(new Font("Cambria", Font.BOLD, 14));
		btnDetalleGastos.setBounds(302, 28, 100, 42);
		contentPane.add(btnDetalleGastos);

		btnAbrirTodos = new JButton("<html><center>TODOS</html>");
		btnAbrirTodos.setForeground(new Color(255, 255, 255));
		btnAbrirTodos.setBackground(new Color(0, 153, 102));
		btnAbrirTodos.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAbrirTodos.setBounds(143, 84, 151, 21);
		contentPane.add(btnAbrirTodos);

		this.setVisible(true);
	}

	public JButton getBtnRepar() {
		return btnRepar;
	}

	public void setBtnRepar(JButton btnRepar) {
		this.btnRepar = btnRepar;
	}

	public JButton getBtnCaja() {
		return btnCaja;
	}

	public void setBtnCaja(JButton btnCaja) {
		this.btnCaja = btnCaja;
	}

	public JButton getBtnAbrirTodos() {
		return btnAbrirTodos;
	}

	public void setBtnAbrirTodos(JButton btnAbrirTodos) {
		this.btnAbrirTodos = btnAbrirTodos;
	}
}
