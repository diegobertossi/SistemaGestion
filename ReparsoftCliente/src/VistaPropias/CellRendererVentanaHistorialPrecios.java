package VistaPropias;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import tiposPropios.MonedaFormatter;

public class CellRendererVentanaHistorialPrecios extends DefaultTableCellRenderer implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	private MonedaFormatter monedaFormatter = new MonedaFormatter();
	Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);
	Font fuenteELS = new Font("Cambria", Font.BOLD, 12);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		setFont(fuenteCeldas);
		setHorizontalAlignment(SwingConstants.CENTER);

		if (column == 0 || column == 5 || column == 6) {
			setFont(fuenteELS);
		}

		if (column == 1 || column == 2 || column == 3) {

			setHorizontalAlignment(SwingConstants.LEFT);
		}

		if (column == 5 && value != null) {
			setText(monedaFormatter.formatPeso(value.toString()));
			setHorizontalAlignment(SwingConstants.CENTER);
			setForeground(new Color(49, 69, 95));
		}

		if (column == 6 && value != null) {
			setText(monedaFormatter.formatDolar(value.toString()));
			setHorizontalAlignment(SwingConstants.CENTER);
			setForeground(new Color(0, 100, 0));
			}

		boolean oddRow = (row % 2 == 0);
		setBackground(
				isSelected ? new Color(70, 130, 180) : (oddRow ? new Color(230, 230, 250) : new Color(176, 196, 222)));
		// Solo aplicar color de letra por defecto si NO es la columna de dólares
		if (column != 6 && column != 5) {
			setForeground(isSelected ? new Color(255, 255, 255) : new Color(0, 0, 0));
		}

		return this;
	}
}