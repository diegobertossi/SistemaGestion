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

public class CellRendererTablaBusqueda extends DefaultTableCellRenderer implements TableCellRenderer{

	private VentanaListadoReparaciones ventanaListadoReparaciones;

	private JCheckBox check = new JCheckBox();
	Font fuenteELS = new Font("Cambria", Font.BOLD, 14);

	/** Constructor de clase */
	public CellRendererTablaBusqueda() {
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

		
		// Si las filas son pares, se cambia el color a gris
		if (oddRow) {
			setBackground((isSelected) ? fondoImparSeleccionado : fondoImpar);
			setForeground((isSelected) ? letraSeleccionado : letra);

		} else {
			setBackground((isSelected) ? fondoParSeleccionado : fondoPar);
			setForeground((isSelected) ? letraSeleccionado : letra);
		}

		if (column == 0 || column == 7 || column == 8) {
			
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(fuenteELS);
			setForeground(Color.blue);
					
		
		}

		if ((column == 1) && (value != null && value != "")) {

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