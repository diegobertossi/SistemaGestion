package VistaPropias;

import javax.swing.TransferHandler;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.Component;
import java.awt.datatransfer.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

public class TableCopyHandler extends TransferHandler {

    private static final long serialVersionUID = 1L;

	@Override
    protected Transferable createTransferable(JComponent c) {
        JTable table = (JTable) c;
        StringBuilder sb = new StringBuilder();

        int[] rows = table.getSelectedRows();
        int[] cols = table.getSelectedColumns();

        if (rows.length == 0 || cols.length == 0) {
            return null;
        }

        // Preguntar al usuario si desea incluir cabeceras
        int includeHeaders = JOptionPane.showConfirmDialog(
            null,
            "¿Desea agregar las cabeceras al contenido copiado?",
            "Incluir cabeceras",
            JOptionPane.YES_NO_OPTION
        );

        boolean addHeaders = (includeHeaders == JOptionPane.YES_OPTION);

        TableColumnModel columnModel = table.getColumnModel();

        // Cabeceras (si el usuario aceptó)
        if (addHeaders) {
            StringJoiner headerJoiner = new StringJoiner("\t");
            for (int col : cols) {
                if (columnModel.getColumn(col).getWidth() > 0) { // Verificar si la columna no está oculta
                    headerJoiner.add(table.getColumnName(col));
                }
            }
            sb.append(headerJoiner.toString()).append("\n");
        }

        // Datos
        for (int row : rows) {
            StringJoiner dataJoiner = new StringJoiner("\t");
            for (int col : cols) {
                if (columnModel.getColumn(col).getWidth() > 0) { // Verificar si la columna no está oculta
                    Object value = table.getValueAt(row, col);
                    TableCellRenderer renderer = table.getCellRenderer(row, col);
                    Component comp = renderer.getTableCellRendererComponent(
                        table, value, false, false, row, col);

                    String text = "";
                    if (comp instanceof JLabel) {
                        text = ((JLabel) comp).getText();
                    } else if (comp instanceof JCheckBox) {
                        text = ((JCheckBox) comp).isSelected() ? "TRUE" : "FALSE";
                    } else if (value instanceof Date) {
                        text = new SimpleDateFormat("dd/MM/yyyy").format((Date) value);
                    } else {
                        text = value != null ? value.toString() : "";
                    }
                    dataJoiner.add(text);
                }
            }
            if (dataJoiner.length() > 0) { // Solo agregar filas con contenido
                sb.append(dataJoiner.toString()).append("\n");
            }
        }

        return new StringSelection(sb.toString());
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }
}

