package VistaPropias;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

public class TablaFiltros {

    public void agregarAutofiltros(JTable tabla) {
        Font fuenteFiltros = new Font("Cambria", Font.PLAIN, 11);
        Color fondoFiltros = new Color(252, 243, 207);
        Color fondoBusqueda = new Color(218, 247, 166);

        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        int columnCount = tabla.getColumnCount();
        JComboBox<String>[] filterCombos = new JComboBox[columnCount];

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(null);

        int xPosition = 0;
        for (int i = 0; i < columnCount; i++) {
            filterCombos[i] = new JComboBox<>();
            filterCombos[i].setFont(fuenteFiltros);
            filterCombos[i].setEditable(true);
            filterCombos[i].addItem("Todos");

            JTextField editor = (JTextField) filterCombos[i].getEditor().getEditorComponent();
            editor.setBackground(fondoFiltros);

            filterCombos[i].setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    c.setBackground(isSelected ? fondoFiltros.darker() : fondoFiltros);
                    c.setForeground(Color.BLACK);
                    return c;
                }
            });

            final int columnIndex = i;
            filterCombos[i].addActionListener(e -> {
                String filterValue = (String) filterCombos[columnIndex].getSelectedItem();

                if (filterValue != null && !(filterValue.equals("Todos") || filterValue.equals(""))) {
                    editor.setBackground(fondoBusqueda);
                } else {
                    editor.setBackground(fondoFiltros);
                }

                filtrarTabla(tabla, filterCombos);
            });

            actualizarFiltroColumna(tabla, filterCombos[i], i);

            TableColumn column = tabla.getColumnModel().getColumn(columnIndex);
            int columnWidth = column.getWidth();

            filterCombos[i].setBounds(xPosition, 0, columnWidth, 25);
            filterPanel.add(filterCombos[i]);

            xPosition += columnWidth;
        }

        filterPanel.setPreferredSize(new Dimension(tabla.getWidth(), 25));

        JPanel headerContainer = new JPanel(new BorderLayout());
        headerContainer.add(filterPanel, BorderLayout.NORTH);
        headerContainer.add(tabla.getTableHeader(), BorderLayout.SOUTH);

        JScrollPane scrollPane = (JScrollPane) tabla.getParent().getParent();
        scrollPane.setColumnHeaderView(headerContainer);

        tabla.getColumnModel().addColumnModelListener(new javax.swing.event.TableColumnModelListener() {
            @Override
            public void columnAdded(javax.swing.event.TableColumnModelEvent e) {
            }

            @Override
            public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {
            }

            @Override
            public void columnMoved(javax.swing.event.TableColumnModelEvent e) {
            }

            @Override
            public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
                int xPosition = 0;
                for (int i = 0; i < columnCount; i++) {
                    TableColumn column = tabla.getColumnModel().getColumn(i);
                    int columnWidth = column.getWidth();
                    filterCombos[i].setBounds(xPosition, 0, columnWidth, 25);
                    filterCombos[i].revalidate();
                    filterCombos[i].repaint();
                    xPosition += columnWidth;
                }
            }

            @Override
            public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
            }
        });
    }

    private void actualizarFiltroColumna(JTable tabla, JComboBox<String> comboBox, int columnIndex) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        comboBox.removeAllItems();
        comboBox.addItem("Todos");

        Set<String> uniqueValues = new TreeSet<>((a, b) -> {
            if (esNumero(a) && esNumero(b)) {
                return Double.compare(Double.parseDouble(a), Double.parseDouble(b));
            }
            return a.compareTo(b);
        });

        for (int row = 0; row < model.getRowCount(); row++) {
            Object value = model.getValueAt(row, columnIndex);
            if (value != null) {
                String valueString = value.toString();
                uniqueValues.add(valueString);
            }
        }

        for (String value : uniqueValues) {
            comboBox.addItem(value);
        }
        
        
		// Agregar los valores ordenados al JComboBox
		for (String value : uniqueValues) {
			if (esFecha(value)) {
				value = formatearFecha(value);

			}

			comboBox.addItem(value);
		}
    }

    private void filtrarTabla(JTable tabla, JComboBox<String>[] filterCombos) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tabla.setRowSorter(sorter);

        List<RowFilter<Object, Object>> rowFilters = new ArrayList<>();

        for (int columnIndex = 0; columnIndex < filterCombos.length; columnIndex++) {
            if (filterCombos[columnIndex] != null) {
                String filterValue = (String) filterCombos[columnIndex].getSelectedItem();

                if (filterValue != null && !filterValue.equals("Todos")) {
                    if (filterValue.contains("/")) {
                        String[] parts = filterValue.split("/");
                        filterValue = parts[2] + parts[1] + parts[0];
                    }

                    String escapedFilterValue = Pattern.quote(filterValue);
                    String regex = escapedFilterValue.replace("*", ".*");
                    RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + regex, columnIndex);
                    rowFilters.add(rowFilter);
                }
            }
        }

        if (rowFilters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(rowFilters);
            sorter.setRowFilter(combinedFilter);
        }
    }

    private boolean esNumero(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean esFecha(String value) {
        return value.length() == 8 && value.matches("\\d{8}");
    }

    private String formatearFecha(String fecha) {
        String dia = fecha.substring(6, 8);
        String mes = fecha.substring(4, 6);
        String año = fecha.substring(0, 4);
        return dia + "/" + mes + "/" + año;
    }
}
