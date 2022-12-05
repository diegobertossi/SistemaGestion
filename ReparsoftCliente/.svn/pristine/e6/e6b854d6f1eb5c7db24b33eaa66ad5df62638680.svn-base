package VistaPropias;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListaArch  extends JFrame {

		private DefaultTableModel modelMultiseleccion;
		private String[] columnas = {"Nombre", "Código","Elegir"};
		private JTable grilla;
		private JButton btnConfirmar;
		
		public ListaArch(DefaultTableModel newModel){
			super();
			this.modelMultiseleccion = newModel;
			inicialize();
		}

		public ListaArch(){
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
			setBounds(100, 100, 459, 381);
			JPanel panel = new JPanel();
			
			getContentPane().setLayout(null);
				
			getContentPane().setLayout(null);
				
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(0, 0, 443, 319);
						
			
						grilla = new JTable(modelMultiseleccion);
						
						grilla.setShowVerticalLines(false);
						grilla.setShowHorizontalLines(false);
						grilla.setShowGrid(true);
						//grilla.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
						scrollPane_1.setViewportView(grilla);
						getContentPane().add(scrollPane_1);
						
						
						
						
						
			
			btnConfirmar = new JButton("Confirmar");
			btnConfirmar.setBounds(0, 319, 443, 23);
			getContentPane().add(btnConfirmar);
		
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


}
