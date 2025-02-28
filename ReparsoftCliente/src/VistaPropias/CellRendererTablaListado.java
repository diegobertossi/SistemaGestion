package VistaPropias;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

//import com.sun.xml.internal.ws.assembler.jaxws.HandlerTubeFactory;

import presentacion.vista.VentanaListadoReparaciones;
import tiposPropios.MonedaFormatter;

public class CellRendererTablaListado extends DefaultTableCellRenderer implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private VentanaListadoReparaciones ventanaListadoReparaciones;
	private MonedaFormatter monedaFormatter;

	private JCheckBox check = new JCheckBox();
	Font fuenteELS = new Font("Cambria", Font.BOLD, 12);
	Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
	Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

	/** Constructor de clase */
	public CellRendererTablaListado() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		// establecemos el fondo blanco o vac�o
		setBackground(null);
		// COnstructor de la clase DefaultTableCellRenderer
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null) {
			if (c instanceof JComponent) {
				((JComponent) c).setToolTipText(value.toString()); // Muestra el valor completo al pasar el cursor
																	// sobre la celda.
			}
		}
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
		table.getTableHeader().setReorderingAllowed(false);
		setFont(fuenteCeldas);

		if (column == 0 || column == 1 || column == 8 || column == 9 || column == 11 || column == 12 || column == 13
				|| column == 14 || column == 17 || column == 18 || column == 19 || column == 20 || column == 21
				|| column == 22) {

			setHorizontalAlignment(SwingConstants.CENTER);

		}

		if (column == 17 || column == 18) {
			Boolean bol = Boolean.valueOf(String.valueOf(value));

			check = new JCheckBox();
			check.setHorizontalAlignment(JLabel.CENTER);
			if (oddRow)
				check.setBackground((isSelected) ? fondoImparSeleccionado : fondoImpar);
			else
				check.setBackground((isSelected) ? fondoParSeleccionado : fondoPar);

			check.setSelected(bol); // valor de celda
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

		// Si las filas son pares, se cambia el color a gris
		if (oddRow) {
			setBackground((isSelected) ? fondoImparSeleccionado : fondoImpar);
			setForeground((isSelected) ? letraSeleccionado : letra);

		} else {
			setBackground((isSelected) ? fondoParSeleccionado : fondoPar);
			setForeground((isSelected) ? letraSeleccionado : letra);
		}

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