package VistaPropias;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

//import com.sun.xml.internal.ws.assembler.jaxws.HandlerTubeFactory;

import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaMarcarAceptaciones;

public class CellRendererTablaMarcarAceptaciones extends DefaultTableCellRenderer implements TableCellRenderer {

	private VentanaMarcarAceptaciones ventanaMarcarAceptaciones;

	private ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox check = new JCheckBox();
	Font fuenteELS = new Font("Cambria", Font.BOLD, 14);

	/** Constructor de clase */
	public CellRendererTablaMarcarAceptaciones() {
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
		
		Color fondoImparCheck = new Color(179, 229, 252);
		Color fondoParCheck = new Color(100, 181, 246);
		Color fondoParSeleccionadoCheck = new Color(80, 130, 180);
		Color fondoImparSeleccionadoCheck = new Color(80, 130, 180);
		
		Color letra = new Color(0, 0, 0);
		Color letraSeleccionado = new Color(255, 255, 255);

		setHorizontalAlignment(SwingConstants.CENTER);

		if (column == 8 || column == 9 || column == 10 || column == 11) {

			Boolean bol = Boolean.valueOf(String.valueOf(value));
			setHorizontalAlignment(SwingConstants.CENTER);
			check = new JCheckBox();

			// buttonGroup.add(check);

			check.setHorizontalAlignment(JLabel.CENTER);
			
			if (oddRow)
				check.setBackground((isSelected) ? fondoImparCheck : fondoImparCheck);
			else
				check.setBackground((isSelected) ? fondoParCheck : fondoParCheck);

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

		if (column == 0) {

			// setHorizontalAlignment(SwingConstants.CENTER);
			setFont(fuenteELS);
			setForeground(Color.blue);

		}

		return this;
	}

}