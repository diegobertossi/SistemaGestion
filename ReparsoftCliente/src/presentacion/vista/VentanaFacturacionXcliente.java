package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import presentacion.controlador.ControladorListados;
import presentacion.controlador.ControladorReparacion;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaFacturacionXcliente extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@SuppressWarnings("unused")
	private ControladorReparacion controladorP;
	@SuppressWarnings("unused")
	private ControladorListados controlador;
	private JTextField textField;
	private JPanel panelCentro;
	private JPanel panelTabla;
	private JPanel panelGrafico;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	
	private JScrollPane scrollPane;
	private JTable tblFacturacionClientes;
	private DefaultTableModel modelFacturacionClientes;
	
	private String[] nombreColumnas = { "CLIENTE", "FACTURACIÓN", "PORCENTAJE"};
	
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JPanel panelGraficoCliente;
	
	
	

	@SuppressWarnings({ "rawtypes", "serial" })
	public VentanaFacturacionXcliente(ControladorListados controlador) {
		super();
		//setResizable(false);
		this.controlador = controlador;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1279, 721);

		this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(new Color(176, 196, 222));
		panelNorte.setBorder(new LineBorder(new Color(0, 128, 128), 4));
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("FACTURACIÓN POR CLIENTE");
		lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 30));
		panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		panelNorte.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setOpaque(false);
		textField.setFont(new Font("Cambria", Font.BOLD, 30));
		panelNorte.add(textField);
		textField.setColumns(10);
		
		panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.X_AXIS));
		
		panelTabla = new JPanel();
		panelTabla.setBorder(new LineBorder(new Color(0, 128, 128), 4));
		panelCentro.add(panelTabla);
		panelTabla.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel_1 = new JLabel("          ");
		panelTabla.add(lblNewLabel_1, BorderLayout.NORTH);
		
		lblNewLabel_2 = new JLabel("          ");
		panelTabla.add(lblNewLabel_2, BorderLayout.WEST);
		
		lblNewLabel_3 = new JLabel("          ");
		panelTabla.add(lblNewLabel_3, BorderLayout.EAST);
		
		
		modelFacturacionClientes = new DefaultTableModel(new Object[][] {}, nombreColumnas) {

			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { String.class, double.class, double.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			


		};



		tblFacturacionClientes = new JTable(modelFacturacionClientes) {};

		tblFacturacionClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
				
		
		scrollPane = new JScrollPane(tblFacturacionClientes);
		panelTabla.add(scrollPane, BorderLayout.CENTER);
		
		lblNewLabel_4 = new JLabel("          ");
		panelTabla.add(lblNewLabel_4, BorderLayout.SOUTH);
		
		panelGrafico = new JPanel();
		panelGrafico.setBorder(new LineBorder(new Color(0, 128, 128), 4));
		panelCentro.add(panelGrafico);
		panelGrafico.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel_5 = new JLabel("          ");
		panelGrafico.add(lblNewLabel_5, BorderLayout.NORTH);
		
		lblNewLabel_6 = new JLabel("          ");
		panelGrafico.add(lblNewLabel_6, BorderLayout.WEST);
		
		lblNewLabel_7 = new JLabel("          ");
		panelGrafico.add(lblNewLabel_7, BorderLayout.SOUTH);
		
		lblNewLabel_8 = new JLabel("          ");
		panelGrafico.add(lblNewLabel_8, BorderLayout.EAST);
		
		panelGraficoCliente = new JPanel();
		panelGraficoCliente.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelGrafico.add(panelGraficoCliente, BorderLayout.CENTER);
		panelGraficoCliente.setLayout(new BorderLayout(0, 0));

		
	

		this.setVisible(true);

	}




	public JPanel getPanelCentro() {
		return panelCentro;
	}




	public void setPanelCentro(JPanel panelCentro) {
		this.panelCentro = panelCentro;
	}




	public JPanel getPanelTabla() {
		return panelTabla;
	}




	public void setPanelTabla(JPanel panelTabla) {
		this.panelTabla = panelTabla;
	}




	public JPanel getPanelGrafico() {
		return panelGrafico;
	}




	public void setPanelGrafico(JPanel panelGrafico) {
		this.panelGrafico = panelGrafico;
	}




	public JScrollPane getScrollPane() {
		return scrollPane;
	}




	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}




	public JTable getTblFacturacionClientes() {
		return tblFacturacionClientes;
	}




	public void setTblFacturacionClientes(JTable tblFacturacionClientes) {
		this.tblFacturacionClientes = tblFacturacionClientes;
	}




	public DefaultTableModel getModelFacturacionClientes() {
		return modelFacturacionClientes;
	}




	public void setModelFacturacionClientes(DefaultTableModel modelFacturacionClientes) {
		this.modelFacturacionClientes = modelFacturacionClientes;
	}




	public String[] getNombreColumnas() {
		return nombreColumnas;
	}




	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}




	public JPanel getPanelGraficoCliente() {
		return panelGraficoCliente;
	}




	public void setPanelGraficoCliente(JPanel panelGraficoCliente) {
		this.panelGraficoCliente = panelGraficoCliente;
	}





	
	}