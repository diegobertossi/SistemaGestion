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


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import presentacion.vista.VentanaListadoReparaciones;
import tiposPropios.MonedaFormatter;

public class CellRendererTablaListado extends DefaultTableCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private VentanaListadoReparaciones ventanaListadoReparaciones;
    private MonedaFormatter monedaFormatter;

    private JCheckBox check = new JCheckBox();
    Font fuenteELS = new Font("Cambria", Font.BOLD, 12);
    Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
    Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

    /** Constructor de clase */
    public CellRendererTablaListado(JTable table) {
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

        if (column == 0 || column == 1 || column == 8 || column == 9 || column == 11 || column == 12 || column == 13
                || column == 14 || column == 17 || column == 18 || column == 19 || column == 20 || column == 21
                || column == 22) {
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        if (column == 17 || column == 18) {
            Boolean bol = Boolean.valueOf(String.valueOf(value));
            check.setHorizontalAlignment(JLabel.CENTER);
            check.setBackground(isSelected ? fondoSeleccionado : (oddRow ? fondoImpar : fondoPar));
            check.setSelected(bol);
            return check;
        }

        if (column == 19 || column == 21) {
            monedaFormatter = new MonedaFormatter();
            String valor = monedaFormatter.formatPeso(value.toString());
            setText((value == null) ? "" : valor);
        }

        if (column == 20) {
            monedaFormatter = new MonedaFormatter();
            String valor = monedaFormatter.formatDolar(value.toString());
            setText((value == null) ? "" : valor);
        }

        setBackground(isSelected ? fondoSeleccionado : (oddRow ? fondoImpar : fondoPar));
        setForeground(isSelected ? letraSeleccionado : letra);

        if (column == 0) {
            setFont(fuenteELS);
            setForeground(Color.blue);
        }
        
        
        if ((column == 1 || column == 9) && (value != null && value != "")) {

			SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
			Date fechaDate = null;
			try {
				fechaDate = formato.parse(value.toString());
			} catch (ParseException ex) {
				System.out.println(ex);
			}

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			setText((value.toString() == null) ? "" : formatter.format(fechaDate));

		}

        return this;
    }
}
