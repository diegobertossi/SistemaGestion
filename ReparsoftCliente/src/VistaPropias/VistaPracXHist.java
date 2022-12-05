package VistaPropias;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;

public class VistaPracXHist  extends JFrame {

		private DefaultTableModel modelMultiseleccion;
		private String[] columnas = {"Nombre", "Código","Elegir"};
		private JTable grilla;
		private JTextAreaCustom textArea;
		
		public VistaPracXHist(DefaultTableModel newModel){
			super();
			this.modelMultiseleccion = newModel;
			inicialize();
		}

		public VistaPracXHist(){
			super();
			this.modelMultiseleccion = new DefaultTableModel(
					new Object[][]{
					},			
					new String[] {
							"Nombre"
					}
				) 
			{
					Class[] columnTypes = new Class[] {
						String.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
					boolean[] columnEditables = new boolean[] {
							false
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
			setBounds(100, 100, 459, 437);
			JPanel panel = new JPanel();
			
			getContentPane().setLayout(null);
				
			getContentPane().setLayout(null);
				
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(0, 183, 443, 215);
						
			
			grilla = new JTable(modelMultiseleccion);
			
			grilla.setShowVerticalLines(false);
			grilla.setShowHorizontalLines(false);
			grilla.setShowGrid(true);
			//grilla.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			scrollPane_1.setViewportView(grilla);
			getContentPane().add(scrollPane_1);
			
			textArea = new JTextAreaCustom(333);
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true); 
			textArea.setBounds(89, 11, 261, 147);
			getContentPane().add(textArea);
		
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

		public JTextAreaCustom getTextArea() {
			return textArea;
		}


}
