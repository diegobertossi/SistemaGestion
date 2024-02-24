package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.ControladorBusquedas;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JComboBox;

public class VentanaBusqueda1eraVersion extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBuscarXels;
	private JButton btnBuscarXcliente;
	private JButton btnBuscarXtecnico; 
	private JButton btnBuscarXcomponente;
	private JButton btnBuscarELS;
	private JButton btnBuscarCliente;
	private JButton btnBuscarTecnico;
	private JButton btnBuscarComponente;
	
	
	
	@SuppressWarnings("unused")
	private ControladorBusquedas controlador;
	private JComboBox<?> comboELS;
	private JComboBox<?> comboCliente;
	private JComboBox<?> comboTecnico;
	private JComboBox<?> comboComponentes;

	public VentanaBusqueda1eraVersion(ControladorBusquedas controladorBusqueda) {
		super();
		setResizable(false);
		this.controlador = controladorBusqueda;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 484, 244);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnBuscarXels = new JButton("<html><center>ELS</html>");
		btnBuscarXels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBuscarXels.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBuscarXels.setBounds(31, 62, 118, 20);
		contentPane.add(btnBuscarXels);

		btnBuscarXcliente = new JButton("<html><center>CLIENTE</html>");
		btnBuscarXcliente.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBuscarXcliente.setBounds(31, 93, 118, 20);
		contentPane.add(btnBuscarXcliente);

		JLabel lblBuscarEquiposPor = new JLabel("BUSCAR EQUIPOS POR :");
		lblBuscarEquiposPor.setForeground(new Color(0, 0, 205));
		lblBuscarEquiposPor.setFont(new Font("Cambria", Font.BOLD, 20));
		lblBuscarEquiposPor.setBounds(31, 11, 233, 34);
		contentPane.add(lblBuscarEquiposPor);

		btnBuscarXtecnico = new JButton("<html><center>T\u00C9CNICO</html>");
		btnBuscarXtecnico.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBuscarXtecnico.setBounds(31, 126, 118, 20);
		contentPane.add(btnBuscarXtecnico);

		btnBuscarXcomponente = new JButton("<html><center>COMPONENTE</html>");
		btnBuscarXcomponente.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBuscarXcomponente.setBounds(31, 157, 118, 20);
		contentPane.add(btnBuscarXcomponente);
		
		comboELS = new JComboBox<Object>();
		comboELS.setBounds(180, 62, 102, 20);
		contentPane.add(comboELS);
		
		comboCliente = new JComboBox<Object>();
		comboCliente.setBounds(180, 93, 163, 20);
		contentPane.add(comboCliente);
		
		comboTecnico = new JComboBox<Object>();
		comboTecnico.setBounds(180, 126, 163, 20);
		contentPane.add(comboTecnico);
		
		comboComponentes = new JComboBox<Object>();
		comboComponentes.setBounds(180, 157, 163, 20);
		contentPane.add(comboComponentes);
		
		btnBuscarELS = new JButton("BUSCAR");
		btnBuscarELS.setBounds(366, 62, 89, 23);
		contentPane.add(btnBuscarELS);
		
		btnBuscarCliente = new JButton("BUSCAR");
		btnBuscarCliente.setBounds(366, 93, 89, 23);
		contentPane.add(btnBuscarCliente);
		
		btnBuscarTecnico = new JButton("BUSCAR");
		btnBuscarTecnico.setBounds(366, 126, 89, 23);
		contentPane.add(btnBuscarTecnico);
		
		btnBuscarComponente = new JButton("BUSCAR");
		btnBuscarComponente.setBounds(366, 157, 89, 23);
		contentPane.add(btnBuscarComponente);

		this.setVisible(true);
	}

	public JButton getBtnBuscarXels() {
		return btnBuscarXels;
	}

	public void setBtnBuscarXels(JButton btnBuscarXels) {
		this.btnBuscarXels = btnBuscarXels;
	}

	public JButton getBtnBuscarXcliente() {
		return btnBuscarXcliente;
	}

	public void setBtnBuscarXcliente(JButton btnBuscarXcliente) {
		this.btnBuscarXcliente = btnBuscarXcliente;
	}

	public JButton getBtnBuscarXtecnico() {
		return btnBuscarXtecnico;
	}

	public void setBtnBuscarXtecnico(JButton btnBuscarXtecnico) {
		this.btnBuscarXtecnico = btnBuscarXtecnico;
	}

	public JButton getBtnBuscarXcomponente() {
		return btnBuscarXcomponente;
	}

	public void setBtnBuscarXcomponente(JButton btnBuscarXcomponente) {
		this.btnBuscarXcomponente = btnBuscarXcomponente;
	}

	public JButton getBtnBuscarELS() {
		return btnBuscarELS;
	}

	public void setBtnBuscarELS(JButton btnBuscarELS) {
		this.btnBuscarELS = btnBuscarELS;
	}

	public JButton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public void setBtnBuscarCliente(JButton btnBuscarCliente) {
		this.btnBuscarCliente = btnBuscarCliente;
	}

	public JButton getBtnBuscarTecnico() {
		return btnBuscarTecnico;
	}

	public void setBtnBuscarTecnico(JButton btnBuscarTecnico) {
		this.btnBuscarTecnico = btnBuscarTecnico;
	}

	public JButton getBtnBuscarComponente() {
		return btnBuscarComponente;
	}

	public void setBtnBuscarComponente(JButton btnBuscarComponente) {
		this.btnBuscarComponente = btnBuscarComponente;
	}

	public JComboBox<?> getComboELS() {
		return comboELS;
	}

	public void setComboELS(JComboBox<?> comboELS) {
		this.comboELS = comboELS;
	}

	public JComboBox<?> getComboCliente() {
		return comboCliente;
	}

	public void setComboCliente(JComboBox<?> comboCliente) {
		this.comboCliente = comboCliente;
	}

	public JComboBox<?> getComboTecnico() {
		return comboTecnico;
	}

	public void setComboTecnico(JComboBox<?> comboTecnico) {
		this.comboTecnico = comboTecnico;
	}

	public JComboBox<?> getComboComponentes() {
		return comboComponentes;
	}

	public void setComboComponentes(JComboBox<?> comboComponentes) {
		this.comboComponentes = comboComponentes;
	}
}
