package VistaPropias;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import presentacion.vista.VentanaListadoReparaciones;
import tiposPropios.MonedaFormatter;

public class CellRendererTablaFacturacionXlientes extends DefaultTableCellRenderer implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private VentanaListadoReparaciones ventanaListadoReparaciones;

	@SuppressWarnings("unused")
	private JCheckBox check = new JCheckBox();
	Font fuenteNombre = new Font("Cambria", Font.BOLD, 12);
	Font fuenteDNI = new Font("Cambria", Font.PLAIN, 12);
	Font fuenteCabecera = new Font("Cambria", Font.BOLD, 12);
	private MonedaFormatter monedaFormatter;

	/** Constructor de clase */
	public CellRendererTablaFacturacionXlientes() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		// establecemos el fondo blanco o vac�o
		setBackground(null);
		// COnstructor de la clase DefaultTableCellRenderer
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Establecemos las filas que queremos cambiar el color. == 0 para pares
		// y != 0 para impares
		boolean oddRow = (row % 2 == 0);

		Color fondoImpar = new Color(230, 230, 250);
		Color fondoPar = new Color(176, 196, 222);
		Color fondoParSeleccionado = new Color(70, 130, 180);
		Color fondoImparSeleccionado = new Color(70, 130, 180);
		Color letra = new Color(0, 0, 0);
		Color letraSeleccionado = new Color(255, 255, 255);

		table.getTableHeader().setFont(fuenteCabecera);
		// Si las filas son pares, se cambia el color a gris
		if (oddRow) {
			setBackground((isSelected) ? fondoImparSeleccionado : fondoImpar);
			setForeground((isSelected) ? letraSeleccionado : letra);

		} else {
			setBackground((isSelected) ? fondoParSeleccionado : fondoPar);
			setForeground((isSelected) ? letraSeleccionado : letra);
		}

		if (column == 0) {
			
			
			table.getColumnModel().getColumn(column).setPreferredWidth(240);
			

		}
		
		if (column == 1) {

			monedaFormatter = new MonedaFormatter();
			String valor = monedaFormatter.formatPeso(value.toString());
			setText((value == null) ? "" : valor);

			table.getColumnModel().getColumn(column).setPreferredWidth(100);
			
			setHorizontalAlignment(SwingConstants.RIGHT);
			
		}

		if (column == 2) {

			setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(column).setPreferredWidth(100);
						
		}

		return this;
	}

}