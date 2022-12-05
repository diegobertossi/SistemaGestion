package VistaPropias;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import presentacion.vista.VentanaListadoReparaciones;




public class CellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
	
	private VentanaListadoReparaciones ventanaListadoReparaciones;

	private JCheckBox check = new JCheckBox();
	Font fuenteELS = new Font("Cambria", Font.BOLD, 12);

	/** Constructor de clase */
	public CellRenderer() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {			
		

		// establecemos el fondo blanco o vacío
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

		if (column == 17 || column == 18) {
			Boolean bol = Boolean.valueOf(String.valueOf(value));
			setHorizontalAlignment(SwingConstants.CENTER);
			check = new JCheckBox();
			check.setHorizontalAlignment(JLabel.CENTER);
			if (oddRow)
				check.setBackground((isSelected) ? fondoImparSeleccionado : fondoImpar);
			else
				check.setBackground((isSelected) ? fondoParSeleccionado : fondoPar);

			check.setSelected(bol); // valor de celda
			return check;

		}

		// Si las filas son pares, se cambia el color a gris
		if (oddRow) {
			setBackground((isSelected) ? fondoImparSeleccionado : fondoImpar);
			setForeground((isSelected) ? letraSeleccionado : letra);

		} else {
			setBackground((isSelected) ? fondoParSeleccionado : fondoPar);
			setForeground((isSelected) ? letraSeleccionado : letra);
		}

		if (column == 0 || column == 1 || column == 9) {

			setHorizontalAlignment(SwingConstants.CENTER);

			if (column == 0)
				setFont(fuenteELS);

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