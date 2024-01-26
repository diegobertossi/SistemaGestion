package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.plaf.UIManagerExt;

import VistaPropias.JTextNum;
import VistaPropias.CellRenderer;
import VistaPropias.CellRendererTablaRemitos;
import presentacion.controlador.ControladorListados;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorSalidas;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Enumeration;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.JFormattedTextField;

public class VentanaRemitos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblEquiposParaRemito;
	private DefaultTableModel modelEquiposParaRemito;
	private String[] nombreColumnas = { "ELS","EQUIPO", "MARCA", "MODELO","N° SERIE", "AVISO", "ESTADO TEC", "ESTADO COM", "AGREGAR A REMITO" };
	public static int est;
	private JPanel panel;
	private JScrollPane  scrollPane ;

	private ControladorSalidas controlador;
	private JTextField txtRemitos;

	Dimension DimScrollPane;
	Dimension DimPanel;
	Dimension DimContentPane;
	Dimension DimTblReparaciones;
	private JTextField txtCliente;
	private JSeparator separator;
	private JTextField txtSeleccinDeRemito;
	private JLabel lblUbicacin;
	private JLabel lblNDeRemito;
	private JLabel lblCantDeBultos;
	private JTextNum textCantBultos;
	
	private JTextField textTipoRemito;
	private JComboBox comboUbicacion;
	private JTextField textRemitoConformado;
	private JPanel panel_2;
	private JButton btnVisualizarRemito;
	private JButton  btnGuardarRemito;
	private JButton  btnImprimirRemito;
	private JButton btnCambiarN;
	
	private JFormattedTextField txtNumeroRemito;
	

	protected void this_windowOpened(WindowEvent e) {
		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	public VentanaRemitos(ControladorSalidas controlador) {

		super();
		setResizable(false);
		this.controlador = controlador;

		this.this_windowOpened(null);
		setSize(1070, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		getContentPane().setLayout(null);

		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(0, 0));
		contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DimContentPane = contentPane.getSize();
		

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 0));
		panel.setBounds(10, 0, 1044, 561);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(panel);
		panel.setLayout(null);
		DimPanel = panel.getSize();

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// scrollPane.setAutoscrolls(true);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 50, 1044, 263);
		panel.add(scrollPane);
		
		DimScrollPane = scrollPane.getSize();

		modelEquiposParaRemito = new DefaultTableModel(null, nombreColumnas);
		tblEquiposParaRemito = new JTable(modelEquiposParaRemito);

		modelEquiposParaRemito = new DefaultTableModel(new Object[][] {},
				new String[] { "ELS","EQUIPO", "MARCA", "MODELO","N° SERIE", "AVISO", "ESTADO TEC", "ESTADO COM", "AGREGAR A REMITO" }) {

			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
		Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

		tblEquiposParaRemito = new JTable(modelEquiposParaRemito);

		tblEquiposParaRemito.setFont(fuenteCeldas);

		tblEquiposParaRemito.getTableHeader().setForeground(Color.BLACK);
		tblEquiposParaRemito.getTableHeader().setFont(fuenteCabecera);
		tblEquiposParaRemito.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tblEquiposParaRemito.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
	

		tblEquiposParaRemito.setShowGrid(true);
		tblEquiposParaRemito.setCellSelectionEnabled(true);

		tblEquiposParaRemito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblEquiposParaRemito.doLayout();

		scrollPane.setViewportView(tblEquiposParaRemito);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tblEquiposParaRemito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblEquiposParaRemito.setAutoCreateColumnsFromModel(false);

		DimTblReparaciones = tblEquiposParaRemito.getSize();

		txtRemitos = new JTextField();
		txtRemitos.setBorder(null);
		txtRemitos.setEditable(false);
		txtRemitos.setBackground(SystemColor.inactiveCaption);
		txtRemitos.setFont(new Font("Cambria", Font.BOLD, 22));
		txtRemitos.setText("REMITOS: ");
		txtRemitos.setBounds(10, 7, 107, 28);
		panel.add(txtRemitos);
		txtRemitos.setColumns(10);
		
		txtCliente = new JTextField();
		txtCliente.setForeground(new Color(51, 102, 204));
		txtCliente.setBorder(null);
		txtCliente.setFont(new Font("Cambria", Font.BOLD, 22));
		txtCliente.setBackground(SystemColor.inactiveCaption);
		txtCliente.setBounds(123, 7, 913, 28);
		panel.add(txtCliente);
		txtCliente.setColumns(10);
		
		separator = new JSeparator();
		separator.setBounds(77, 334, 886, 2);
		panel.add(separator);
		
		txtSeleccinDeRemito = new JTextField();
		txtSeleccinDeRemito.setHorizontalAlignment(SwingConstants.CENTER);
		txtSeleccinDeRemito.setText("SELECCIÓN DE REMITO: ");
		txtSeleccinDeRemito.setFont(new Font("Cambria", Font.BOLD, 22));
		txtSeleccinDeRemito.setEditable(false);
		txtSeleccinDeRemito.setColumns(10);
		txtSeleccinDeRemito.setBorder(null);
		txtSeleccinDeRemito.setBackground(SystemColor.inactiveCaption);
		txtSeleccinDeRemito.setBounds(123, 366, 254, 28);
		panel.add(txtSeleccinDeRemito);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(new CompoundBorder(new LineBorder(new Color(240, 240, 240)), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_1.setBounds(110, 419, 280, 103);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		lblUbicacin = new JLabel("UBICACIÓN :");
		lblUbicacin.setBounds(6, 10, 125, 20);
		panel_1.add(lblUbicacin);
		lblUbicacin.setFont(new Font("Cambria", Font.BOLD, 14));
		lblUbicacin.setForeground(new Color(51, 102, 204));
		
		lblNDeRemito = new JLabel("N° DE REMITO:");
		lblNDeRemito.setBounds(6, 40, 125, 20);
		panel_1.add(lblNDeRemito);
		lblNDeRemito.setForeground(new Color(51, 102, 204));
		lblNDeRemito.setFont(new Font("Cambria", Font.BOLD, 14));
		
		lblCantDeBultos = new JLabel("CANT. DE BULTOS:");
		lblCantDeBultos.setBounds(6, 70, 125, 20);
		panel_1.add(lblCantDeBultos);
		lblCantDeBultos.setForeground(new Color(51, 102, 204));
		lblCantDeBultos.setFont(new Font("Cambria", Font.BOLD, 14));
		
		textCantBultos = new JTextNum();
		textCantBultos.setForeground(new Color(51, 102, 204));
		textCantBultos.setFont(new Font("Cambria", Font.BOLD, 14));
		textCantBultos.setBounds(131, 70, 140, 20);
		panel_1.add(textCantBultos);
		textCantBultos.setColumns(10);
		
		
		comboUbicacion = new JComboBox();
		comboUbicacion.setForeground(new Color(51, 102, 204));
		comboUbicacion.setFont(new Font("Cambria", Font.BOLD, 12));
		comboUbicacion.setBounds(131, 10, 140, 20);
		panel_1.add(comboUbicacion);
		
		txtNumeroRemito = new JFormattedTextField(mascara());
		txtNumeroRemito.setEditable(false);
		txtNumeroRemito.setForeground(new Color(51, 102, 204));
		txtNumeroRemito.setFont(new Font("Cambria", Font.BOLD, 14));
		txtNumeroRemito.setBounds(131, 40, 140, 20);
		panel_1.add(txtNumeroRemito);
		
		textTipoRemito = new JTextField();
		textTipoRemito.setHorizontalAlignment(SwingConstants.CENTER);
		textTipoRemito.setBorder(null);
		textTipoRemito.setVisible(false);
		textTipoRemito.setForeground(SystemColor.desktop);
		textTipoRemito.setFont(new Font("Cambria", Font.BOLD, 22));
		textTipoRemito.setBackground(SystemColor.inactiveCaption);
		textTipoRemito.setBounds(569, 366, 329, 28);
		panel.add(textTipoRemito);
		textTipoRemito.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255)), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_2.setBounds(529, 419, 406, 103);
		panel.add(panel_2);
		panel_2.setLayout(null);
		panel_2.setVisible(false);
		

		btnVisualizarRemito = new JButton("VISUALIZAR REMITO");
		btnVisualizarRemito.setBounds(234, 8, 149, 23);
		panel_2.add(btnVisualizarRemito);
		btnVisualizarRemito.setFont(new Font("Cambria", Font.BOLD, 12));

		
		btnGuardarRemito = new JButton("GUARDAR REMITO");
		btnGuardarRemito.setBounds(234, 39, 149, 23);
		panel_2.add(btnGuardarRemito);
		btnGuardarRemito.setFont(new Font("Cambria", Font.BOLD, 12));
		
		btnImprimirRemito = new JButton("IMPRIMIR REMITO");
		btnImprimirRemito.setBounds(234, 70, 149, 23);
		panel_2.add(btnImprimirRemito);
		btnImprimirRemito.setFont(new Font("Cambria", Font.BOLD, 12));
		
		textRemitoConformado = new JTextField();
		textRemitoConformado.setEditable(false);
		textRemitoConformado.setHorizontalAlignment(SwingConstants.CENTER);
		textRemitoConformado.setFont(new Font("Cambria", Font.BOLD, 16));
		textRemitoConformado.setForeground(new Color(0, 0, 0));
		textRemitoConformado.setBounds(10, 55, 156, 20);
		panel_2.add(textRemitoConformado);
		textRemitoConformado.setColumns(10);
		
		JLabel lblRemitoConformado = new JLabel("REMITO CONFORMADO");
		lblRemitoConformado.setBounds(10, 27, 169, 20);
		panel_2.add(lblRemitoConformado);
		lblRemitoConformado.setForeground(new Color(51, 102, 204));
		lblRemitoConformado.setFont(new Font("Cambria", Font.BOLD, 14));
		
		btnCambiarN = new JButton("CAMBIAR N°");
		btnCambiarN.setFont(new Font("Cambria", Font.BOLD, 12));
		btnCambiarN.setBounds(395, 455, 107, 28);
		panel.add(btnCambiarN);
		
		

		tblEquiposParaRemito.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 60, 200, 150, 100, 100, 60, 100, 100, 150};

		for (int i = 0; i < tblEquiposParaRemito.getColumnCount(); i++) {

			tblEquiposParaRemito.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

		}


		this.setVisible(true);

	}
		
	
	public JTextNum getTextCantBultos() {
		return textCantBultos;
	}
	

	public void setTextCantBultos(JTextNum textCantBultos) {
		this.textCantBultos = textCantBultos;
	}
	

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaRemitos());
		}
	}
	
	
	
    private MaskFormatter mascara() {
        // Inicializamos el objeto
    	MaskFormatter mascara = new MaskFormatter();
        // Entramos en un try/catch por alguna eventualidad
        try {

            mascara = new MaskFormatter("########");
            mascara.setPlaceholderCharacter('#');
       } catch (ParseException e) {
           // Alg�n error que pueda ocurrir
           e.printStackTrace();
       }
       return mascara;
    }


	public DefaultTableModel getModelEquiposParaRemito() {
		return modelEquiposParaRemito;
	}

	public void setModelEquiposParaRemito(DefaultTableModel modelEquiposParaRemito) {
		this.modelEquiposParaRemito = modelEquiposParaRemito;
	}

	public JTable getTblEquiposParaRemito() {
		return tblEquiposParaRemito;
	}

	public void setTblEquiposParaRemito(JTable tblEquiposParaRemito) {
		this.tblEquiposParaRemito = tblEquiposParaRemito;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}


	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public void setTxtCliente(JTextField txtCliente) {
		this.txtCliente = txtCliente;
	}

	public JComboBox getComboUbicacion() {
		return comboUbicacion;
	}

	public void setComboUbicacion(JComboBox comboUbicacion) {
		this.comboUbicacion = comboUbicacion;
	}

	public JTextField getTextTipoRemito() {
		return textTipoRemito;
	}

	public void setTextTipoRemito(JTextField textTipoRemito) {
		this.textTipoRemito = textTipoRemito;
	}

	public JPanel getPanel_2() {
		return panel_2;
	}

	public void setPanel_2(JPanel panel_2) {
		this.panel_2 = panel_2;
	}

	public JFormattedTextField getTxtNumeroRemito() {
		return txtNumeroRemito;
	}

	public void setTxtNumeroRemito(String txtNumeroRemitos) {
				
		txtNumeroRemito.setValue(new String(txtNumeroRemitos));
	}

	public JTextField getTextRemitoConformado() {
		return textRemitoConformado;
	}

	public void setTextRemitoConformado(JTextField textRemitoConformado) {
		this.textRemitoConformado = textRemitoConformado;
	}

	public JButton getBtnVisualizarRemito() {
		return btnVisualizarRemito;
	}

	public void setBtnVisualizarRemito(JButton btnVisualizarRemito) {
		this.btnVisualizarRemito = btnVisualizarRemito;
	}

	public JButton getBtnGuardarRemito() {
		return btnGuardarRemito;
	}

	public void setBtnGuardarRemito(JButton btnGuardarRemito) {
		this.btnGuardarRemito = btnGuardarRemito;
	}

	public JButton getBtnImprimirRemito() {
		return btnImprimirRemito;
	}

	public void setBtnImprimirRemito(JButton btnImprimirRemito) {
		this.btnImprimirRemito = btnImprimirRemito;
	}

	public JButton getBtnCambiarN() {
		return btnCambiarN;
	}

	public void setBtnCambiarN(JButton btnCambiarN) {
		this.btnCambiarN = btnCambiarN;
	}
}
