package VistaPropias;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CellRendererTablaMarcarAceptaciones2 extends DefaultTableCellRenderer implements TableCellRenderer {

    private Map<Integer, JCheckBox> checkBoxMap = new HashMap<>();
    private Map<Integer, ButtonGroup> buttonGroupMap = new HashMap<>();
    private Font fuenteELS = new Font("Cambria", Font.BOLD, 14);

    /** Constructor de clase */
    public CellRendererTablaMarcarAceptaciones2() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        if (column == 8 || column == 9 || column == 10 || column == 11) {
           
        	boolean oddRow = (row % 2 == 0);
            Color fondoImpar = new Color(230, 230, 250);
            Color fondoPar = new Color(176, 196, 222);
            Color fondoParSeleccionado = new Color(70, 130, 180);
            Color fondoImparSeleccionado = new Color(70, 130, 180);
            Color letra = new Color(0, 0, 0);
            Color letraSeleccionado = new Color(255, 255, 255);

            JCheckBox checkBox;
            if (checkBoxMap.containsKey(row)) {
                checkBox = checkBoxMap.get(row);
            } else {
                checkBox = new JCheckBox();
                checkBoxMap.put(row, checkBox);
            }

            boolean isChecked = Boolean.parseBoolean(String.valueOf(value));
            checkBox.setSelected(isChecked);
            checkBox.setHorizontalAlignment(JLabel.CENTER);

            if (oddRow) {
                checkBox.setBackground((isSelected) ? fondoImparSeleccionado : fondoImpar);
                checkBox.setForeground((isSelected) ? letraSeleccionado : letra);
            } else {
                checkBox.setBackground((isSelected) ? fondoParSeleccionado : fondoPar);
                checkBox.setForeground((isSelected) ? letraSeleccionado : letra);
            }

            return checkBox;
        } else {
            // Establecemos el fondo y la fuente para las celdas normales
            setBackground(null);
            setFont(table.getFont());

            if (column == 0) {
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(fuenteELS);
                setForeground(Color.blue);
            }

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}

