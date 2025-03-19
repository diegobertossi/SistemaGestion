package presentacion.vista;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorBusquedas;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class VentanaBusqueda extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@SuppressWarnings("unused")
	private ControladorBusquedas controlador;

	private JRadioButton rdbComponenteOriginal;
	private JRadioButton rdbComponenteReemplazado;
	private ButtonGroup GrupoComponente;

	JComboBox<?> comboBuscador;

	JButton btnBuscar;
	private JPanel panel;
	private JSeparator separator;
	private JSeparator separator_1;

	public VentanaBusqueda(ControladorBusquedas controladorBusqueda) {
		super();
		setResizable(false);
		this.controlador = controladorBusqueda;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 595, 244);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBuscarEquiposPor = new JLabel("BUSCAR EQUIPOS POR :");
		lblBuscarEquiposPor.setForeground(new Color(0, 0, 205));
		lblBuscarEquiposPor.setFont(new Font("Cambria", Font.BOLD, 20));
		lblBuscarEquiposPor.setBounds(17, 11, 233, 34);
		contentPane.add(lblBuscarEquiposPor);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(17, 56, 269, 97);
		contentPane.add(panel);
		panel.setLayout(null);
		GrupoComponente = new ButtonGroup();
		
		
		
		
		rdbComponenteOriginal = new JRadioButton("COMPONENTE ORIGINAL");
		rdbComponenteOriginal.setBounds(6, 16, 205, 23);
		panel.add(rdbComponenteOriginal);
		rdbComponenteOriginal.setBackground(SystemColor.activeCaption);
		rdbComponenteOriginal.setFont(new Font("Cambria", Font.BOLD, 12));
		GrupoComponente.add(rdbComponenteOriginal);


		rdbComponenteReemplazado = new JRadioButton("COMPONENTE REEMPLAZADO");
		rdbComponenteReemplazado.setBounds(6, 52, 205, 23);
		panel.add(rdbComponenteReemplazado);
		rdbComponenteReemplazado.setBackground(SystemColor.activeCaption);
		rdbComponenteReemplazado.setFont(new Font("Cambria", Font.BOLD, 12));
		GrupoComponente.add(rdbComponenteReemplazado);

		

		comboBuscador = new JComboBox<Object>();
		comboBuscador.setBounds(340, 56, 239, 23);
		contentPane.add(comboBuscador);

		btnBuscar = new JButton("BUSCAR");
		btnBuscar.setFont(new Font("Cambria", Font.BOLD, 12));
		btnBuscar.setBounds(490, 181, 89, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblSeleccionarElemento = new JLabel("SELECCIONAR ELEMENTO:");
		lblSeleccionarElemento.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblSeleccionarElemento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeleccionarElemento.setForeground(new Color(0, 0, 205));
		lblSeleccionarElemento.setFont(new Font("Cambria", Font.BOLD, 20));
		lblSeleccionarElemento.setBounds(330, 11, 249, 34);
		contentPane.add(lblSeleccionarElemento);
		
		separator = new JSeparator();
		separator.setForeground(SystemColor.desktop);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(321, 56, 2, 133);
		contentPane.add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.desktop);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(318, 56, 5, 133);
		contentPane.add(separator_1);

		this.setVisible(true);
	}

	public JRadioButton getRdbComponenteOriginal() {
		return rdbComponenteOriginal;
	}

	public void setRdbComponenteOriginal(JRadioButton rdbComponenteOriginal) {
		this.rdbComponenteOriginal = rdbComponenteOriginal;
	}

	public JRadioButton getRdbComponenteReemplazado() {
		return rdbComponenteReemplazado;
	}

	public void setRdbComponenteReemplazado(JRadioButton rdbComponenteReemplazado) {
		this.rdbComponenteReemplazado = rdbComponenteReemplazado;
	}

	public JComboBox<?> getComboBuscador() {
		return comboBuscador;
	}

	public void setcomboBuscador(JComboBox<?> comboCompOriginal) {
		this.comboBuscador = comboCompOriginal;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public ButtonGroup getGrupoComponente() {
		return GrupoComponente;
	}

	public void setGrupoComponente(ButtonGroup grupoComponente) {
		GrupoComponente = grupoComponente;
	}
}
