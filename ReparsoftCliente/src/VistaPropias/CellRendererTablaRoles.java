package VistaPropias;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class CellRendererTablaRoles extends DefaultTableCellRenderer implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private JCheckBox check = new JCheckBox();

	Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
	Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

	/** Constructor de clase */
	public CellRendererTablaRoles(JTable table) {
		// Configurar un renderer personalizado para el encabezado
		JTableHeader header = table.getTableHeader();
		if (header != null) {
			header.setFont(fuenteCabecera);
			header.setDefaultRenderer(new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

					setFont(fuenteCabecera);
					setHorizontalAlignment(SwingConstants.CENTER);
					table.getTableHeader().setReorderingAllowed(false);

					// Fondo gris metalizado
					setBackground(new Color(169, 169, 169)); // Gris metalizado
					setForeground(Color.BLACK); // Color del texto del encabezado

					return c;
				}

				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);

					// Dibujar divisiones entre columnas
					g.setColor(new Color(105, 105, 105)); // Gris oscuro para las divisiones
					g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // Línea horizontal inferior
					g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight()); // Línea vertical derecha
				}
			});
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		// Construcción del componente para la celda
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null && c instanceof JComponent) {
			((JComponent) c).setToolTipText(value.toString());
		}

		// Alternar colores de las filas
		boolean oddRow = (row % 2 == 0);
		Color fondoImpar = new Color(230, 230, 250);
		Color fondoPar = new Color(176, 196, 222);
		Color fondoSeleccionado = new Color(70, 130, 180);
		Color letra = new Color(0, 0, 0);
		Color letraSeleccionado = new Color(255, 255, 255);

		setFont(fuenteCeldas);

		setHorizontalAlignment(SwingConstants.CENTER);
		

		if (column == 0) {
			Boolean bol = Boolean.valueOf(String.valueOf(value));
			check.setHorizontalAlignment(JLabel.CENTER);
			check.setBackground(isSelected ? fondoSeleccionado : (oddRow ? fondoImpar : fondoPar));
			check.setSelected(bol);
			return check;
		}


		setBackground(isSelected ? fondoSeleccionado : (oddRow ? fondoImpar : fondoPar));
		setForeground(isSelected ? letraSeleccionado : letra);



		return this;
	}
}
