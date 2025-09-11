package VistaPropias;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.awt.event.*;

public class TablaFiltros {

	private JComboBox<String>[] filterCombos;

	public void agregarAutofiltros(JTable tabla) {
		Font fuenteFiltros = new Font("Cambria", Font.PLAIN, 11);
		Color fondoFiltros = new Color(252, 243, 207);
		Color fondoBusqueda = new Color(218, 247, 166);

		DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		int columnCount = tabla.getColumnCount();
		filterCombos = new JComboBox[columnCount];

		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(null);

		int xPosition = 0;
		for (int i = 0; i < columnCount; i++) {
			// Crear y configurar el JComboBox
			filterCombos[i] = new JComboBox<>();
			filterCombos[i].setFont(fuenteFiltros);
			filterCombos[i].setEditable(true);
			filterCombos[i].addItem("Todos");
			AutoCompletarComboBox.enable(filterCombos[i], true, false);

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

			// Manejo de eventos para el JComboBox
			filterCombos[i].addActionListener(e -> {
				if (filterCombos[columnIndex] != null) {
					manejarFiltro(tabla, filterCombos[columnIndex], editor, fondoFiltros, fondoBusqueda, columnIndex);
				}
			});

			filterCombos[i].addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (filterCombos[columnIndex] != null) {
						manejarFiltro(tabla, filterCombos[columnIndex], editor, fondoFiltros, fondoBusqueda,
								columnIndex);
					}
				}
			});

			filterCombos[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER && filterCombos[columnIndex] != null) {
						manejarFiltro(tabla, filterCombos[columnIndex], editor, fondoFiltros, fondoBusqueda,
								columnIndex);
					}
				}
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
					if (filterCombos[i] != null) {
						filterCombos[i].setBounds(xPosition, 0, columnWidth, 25);
						filterCombos[i].revalidate();
						filterCombos[i].repaint();
					}
					xPosition += columnWidth;
				}
			}

			@Override
			public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
			}
		});
		
		
		
	}
	
	
	

public void deshabilitarAutofiltro(JTable tabla) {
    if (filterCombos != null) {
        for (JComboBox<String> combo : filterCombos) {
            if (combo != null) {
            	combo.setSelectedItem("Todos");
                combo.setEnabled(false);
                combo.setEditable(false);
            }
        }
    }
}


public void habilitarAutofiltro(JTable tabla) {
    if (filterCombos != null) {
        for (JComboBox<String> combo : filterCombos) {
            if (combo != null) {
                combo.setEnabled(true);
                combo.setEditable(true);
            }
        }
    }
}



	private void manejarFiltro(JTable tabla, JComboBox<String> comboBox, JTextField editor, Color fondoFiltros,
			Color fondoBusqueda, int columnIndex) {
		String filterValue = (String) comboBox.getSelectedItem();

		if (filterValue == null || filterValue.isEmpty() || "Todos".equals(filterValue)) {
			comboBox.setSelectedItem("Todos");
			editor.setBackground(fondoFiltros);
		} else {
			editor.setBackground(fondoBusqueda);
		}
		filtrarTabla(tabla, filterCombos);
	}

	
	private void actualizarFiltroColumna(JTable tabla, JComboBox<String> comboBox, int columnIndex) {
	    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	    String columnName = tabla.getColumnName(columnIndex);
	    comboBox.removeAllItems();
	    comboBox.addItem("Todos");

	    Set<String> uniqueValues = new TreeSet<>((a, b) -> {
	        if (esFecha(a) && esFecha(b)) {
	            try {
	                Date dateA = new SimpleDateFormat("yyyyMMdd").parse(a);
	                Date dateB = new SimpleDateFormat("yyyyMMdd").parse(b);
	                return dateA.compareTo(dateB);
	            } catch (ParseException e) {
	                return a.compareTo(b);
	            }
	        } else if (esNumero(a) && esNumero(b)) {
	        	 return Double.compare(Double.parseDouble(a), Double.parseDouble(b));
	        }
	        return a.compareTo(b);
	    });

	    for (int row = 0; row < model.getRowCount(); row++) {
	        Object value = model.getValueAt(row, columnIndex);
	        if (value != null) {
	            String formattedValue = formatearSegunColumna(value.toString(), columnName);
	            uniqueValues.add(formattedValue);
	        }
	    }

	    for (String value : uniqueValues) {
	        comboBox.addItem(esFecha(value) ? formatearFecha(value) : value);
	    }
	}

	private String formatearSegunColumna(String value, String columnName) {
	    if (esNumero(value)) {
	        double numero = Double.parseDouble(value);
	        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	        symbols.setDecimalSeparator(',');
	        symbols.setGroupingSeparator('.');
	        
	        DecimalFormat df = new DecimalFormat("#,##0.0", symbols);
	        
	        if (columnName.equalsIgnoreCase("PRECIO $") || columnName.equalsIgnoreCase("PAGO")) {
	            return "$ " + df.format(numero);
	        } else if (columnName.equalsIgnoreCase("PRECIO U$$")) {
	            return "U$$ " + df.format(numero);
	        }
	    }
	    return value;
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
	    try {
	        Date date = new SimpleDateFormat("yyyyMMdd").parse(fecha);
	        return new SimpleDateFormat("dd/MM/yyyy").format(date);
	    } catch (ParseException e) {
	        return fecha;
	    }
	}

	public void filtrarTabla(JTable tabla, JComboBox<String>[] filterCombos) {
	    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
	    tabla.setRowSorter(sorter);

	    ArrayList<RowFilter<Object, Object>> rowFilters = new ArrayList<>();
	    
	    

	    for (int i = 0; i < filterCombos.length; i++) {
	        final int columnIndex = i;
	        
	           
	        String columnName = tabla.getColumnName(columnIndex);
	        if (filterCombos[columnIndex] != null) {
	        	
//	        	System.out.println(filterCombos[columnIndex].getSelectedItem());
	        	
	            String filterValue = (String) filterCombos[columnIndex].getSelectedItem();

	            if (filterValue != null && !"Todos".equals(filterValue)) {
	                // Manejo especial para columnas de precio
	                if (columnName.equalsIgnoreCase("PRECIO $") || 
	                    columnName.equalsIgnoreCase("PAGO") || 
	                    columnName.equalsIgnoreCase("PRECIO U$$")) {
	                    
	                    // Extraer el valor numérico del formato mostrado (ej: "$ 1.234,56" -> 1234.56)
	                    String numericValue = filterValue.replaceAll("[^\\d,]", "").replace(".", "").replace(",", ".");

	                    final double filterNumber;
	                    try {
	                        filterNumber = Double.parseDouble(numericValue);
	                    } catch (NumberFormatException e) {
	                        continue; // Si no es un número válido, ignorar este filtro
	                    }

	                    rowFilters.add(new RowFilter<Object, Object>() {
	                        @Override
	                        public boolean include(Entry<?, ?> entry) {
	                            try {
	                                String cellValue = entry.getStringValue(columnIndex);
	                                // Extraer valor numérico de la celda (puede estar formateado)
	                                String cellNumericValue = cellValue.replaceAll("[^\\d,]", "").replace(".", "").replace(",", ".");
	                                double cellNumber = Double.parseDouble(cellNumericValue)/10;
	                                
	                                // Comparar valores numéricos directamente
	                                return Math.abs(cellNumber - filterNumber) < 0.001; // Tolerancia para decimales
	                            } catch (Exception e) {
	                                return false;
	                            }
	                        }
	                    });
	                }
	                // Manejo especial para columnas de fecha
	                else if (columnName.equals("ENTRADA") || columnName.equals("REVISIÓN") || columnName.equals("SALIDA")) {
	                    String regex = Pattern.quote(obtenerFechaOriginal(filterValue)).replace("*", ".*");
	                    rowFilters.add(RowFilter.regexFilter("(?i)" + regex, columnIndex));
	                } 
	                // Filtrado para texto normal con comodines y manejo de acentos
	                else {
	                    final String normalizedFilter = removeDiacritics(filterValue.toLowerCase());
	                    final String regexPattern;

	                    if (normalizedFilter.contains("*")) {
	                        String tempRegex = normalizedFilter.replace(".", "\\.").replace("*", ".*").replace("?", ".");

	                        if (!normalizedFilter.startsWith("*") && !normalizedFilter.endsWith("*")) {
	                            tempRegex = "^" + tempRegex + "$";
	                        } else if (!normalizedFilter.startsWith("*")) {
	                            tempRegex = "^" + tempRegex;
	                        } else if (!normalizedFilter.endsWith("*")) {
	                            tempRegex = tempRegex + "$";
	                        }
	                        regexPattern = tempRegex;
	                    } else {
	                        regexPattern = "^" + Pattern.quote(normalizedFilter) + "$";
	                    }

	                    rowFilters.add(new RowFilter<Object, Object>() {
	                        @Override
	                        public boolean include(Entry<?, ?> entry) {
	                            String cellValue = entry.getStringValue(columnIndex);
	                            String normalizedCellValue = removeDiacritics(cellValue.toLowerCase());

	                            if (normalizedFilter.contains("*")) {
	                                return normalizedCellValue.matches(regexPattern);
	                            } else {
	                                return normalizedCellValue.equals(normalizedFilter);
	                            }
	                        }
	                    });
	                }
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
	// Método auxiliar para eliminar acentos
	private static String removeDiacritics(String str) {
		if (str == null)
			return null;
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return str;
	}



	private String obtenerFechaOriginal(String fechaFormateada) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaFormateada);
			return new SimpleDateFormat("yyyyMMdd").format(date);
		} catch (ParseException e) {
			return fechaFormateada;
		}
	}

}
