package VistaPropias;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PopPapMultiseleccion extends JFrame {

	private DefaultTableModel modelMultiseleccion;
	private String[] columnas = {"Nombre", "Código","Elegir"};
	private JTable grilla;
	private JButton btnConfirmar;
	private JTextString txtNombre;
	private JTextNum  txtCodigo;
	private static DefaultTableCellRenderer tcr;
	private JButton btnFiltrar;
	
	public PopPapMultiseleccion(DefaultTableModel newModel){
		super();
		this.modelMultiseleccion = newModel;
		inicialize();
	}

	public PopPapMultiseleccion(){
		super();
		this.modelMultiseleccion = new DefaultTableModel(
				new Object[][]{
				},			
				new String[] {
						"Nombre","Código","Cantidad de sesiones","Elegir"
				}
			) 
		{
				Class[] columnTypes = new Class[] {
					Object.class, Object.class,Object.class,Boolean.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
						false,false,true,true
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
			};
		inicialize();
	}

	private void inicialize() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 459, 381);
		JPanel panel = new JPanel();
		
		getContentPane().setLayout(null);
			
		txtNombre = new JTextString(50);
		txtNombre.setBounds(102, 48, 101, 20);
		txtCodigo = new JTextNum(10);
		txtCodigo.setBounds(102, 17, 101, 20);
		btnFiltrar = new JButton("Filtrar");	
		btnFiltrar.setBounds(353, 35, 80, 33);
		getContentPane().setLayout(null);
		
		getContentPane().add(txtNombre);
		getContentPane().add(txtCodigo);
		getContentPane().add(btnFiltrar);
			
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 76, 443, 243);
					
		
					grilla = new JTable(modelMultiseleccion);
					
					tcr = new DefaultTableCellRenderer();
					tcr.setHorizontalAlignment(SwingConstants.CENTER);
					
					grilla.setShowVerticalLines(false);
					grilla.setShowHorizontalLines(false);
					grilla.setShowGrid(true);
					//grilla.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					scrollPane_1.setViewportView(grilla);
					getContentPane().add(scrollPane_1);
					
					
					
					
					
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(0, 319, 443, 23);
		getContentPane().add(btnConfirmar);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(30, 20, 46, 14);
		getContentPane().add(lblCodigo);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(30, 51, 46, 14);
		getContentPane().add(lblNombre);
		//getContentPane().add(panel);
		this.setVisible(true);
		
	}

	public DefaultTableModel getModelMultiseleccion() {
		return modelMultiseleccion;
	}

	public void setModelMultiseleccion(DefaultTableModel modelMultiseleccion) {
		this.modelMultiseleccion = modelMultiseleccion;
	}

	public String[] getColumnas() {
		return columnas;
	}

	public void setColumnas(String[] columnas) {
		this.columnas = columnas;
	}

	
	public JTable getGrilla() {
		return grilla;
	}

	public void setGrilla(JTable grilla) {
		this.grilla = grilla;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JTextString getTxtNombre() {
		return txtNombre;
	}

	public JTextNum getTxtCodigo() {
		return txtCodigo;
	}

	public static DefaultTableCellRenderer getTcr() {
		return tcr;
	}

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}
	public void getError(String error){
		JOptionPane.showMessageDialog(null,error);
	}
}
