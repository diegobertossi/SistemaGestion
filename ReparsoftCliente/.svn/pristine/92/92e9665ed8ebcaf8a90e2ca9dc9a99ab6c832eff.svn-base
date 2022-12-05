package presentacion.controlador;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.itextpdf.text.TabStop.Alignment;

import VistaPropias.CellRenderer;
import VistaPropias.Resaltador;
import dto.ClienteDTO;
import dto.PermisoDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.SucursalDTO;
import modelo.Agenda;
import modelo.Permisos;
import presentacion.vista.VistaPrincipal;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaBusqueda;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaRolesUsuarios;
import presentacion.vista.VentanaSalidas;
import presentacion.vista.VentanaTablaBusqueda;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaBusqueda;

public class ControladorBusquedas implements ActionListener, MouseListener, KeyListener {

	private Agenda modelo;

	private VentanaBusqueda ventanaBusqueda;
	private VentanaTablaBusqueda ventanaTablaBusqueda;

	private ControladorBusquedas controladorBusqueda;

	private int max = Frame.MAXIMIZED_BOTH;
	private int min = Frame.NORMAL;
	private int maxHorizontal = Frame.MAXIMIZED_HORIZ;
	private int maxVertical = Frame.MAXIMIZED_VERT;
	private int clickMax = 1;
	private int clickMin = 1;
	private String buscarPor;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	public ControladorBusquedas(VentanaBusqueda ventanaBusqueda, Agenda modelo) {

		this.ventanaBusqueda = ventanaBusqueda;

		this.modelo = modelo;

		this.ventanaBusqueda.getBtnBuscar().addActionListener(this);
		this.ventanaBusqueda.getRdbComponenteOriginal().addActionListener(this);
		this.ventanaBusqueda.getRdbComponenteReemplazado().addActionListener(this);

	}

	public void inicializar() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == this.ventanaBusqueda.getRdbComponenteOriginal()) {

			llenarComboComponentesOriginal();
			AutoCompleteDecorator.decorate(ventanaBusqueda.getComboBuscador());

		}

		else if (arg0.getSource() == this.ventanaBusqueda.getRdbComponenteReemplazado()) {

			llenarComboComponentesReemplazo();
			AutoCompleteDecorator.decorate(ventanaBusqueda.getComboBuscador());

		}

		this.ventanaBusqueda.show();

		if (arg0.getSource() == this.ventanaBusqueda.getBtnBuscar()) {

			if (ventanaBusqueda.getRdbComponenteReemplazado().isSelected()
					|| ventanaBusqueda.getRdbComponenteOriginal().isSelected()) {

				if (ventanaBusqueda.getComboBuscador() != null
						&& ventanaBusqueda.getComboBuscador().getSelectedIndex() != -1) {

					this.ventanaTablaBusqueda = new VentanaTablaBusqueda(this);
					this.ventanaTablaBusqueda.getBtnMax().addMouseListener(this);

					if (ventanaBusqueda.getRdbComponenteOriginal().isSelected()) {

						ventanaTablaBusqueda.getTxtCategoriaBusqueda()
								.setText("EQUIPOS DONDE SE REEMPLAZÓ EL COMPONENTE:      "+"' "
										+ ventanaBusqueda.getComboBuscador().getSelectedItem().toString()+" '");

						cargarTablaListadoBusqueda(ventanaBusqueda.getComboBuscador().getSelectedItem().toString());

					}

					if (ventanaBusqueda.getRdbComponenteReemplazado().isSelected()) {

						ventanaTablaBusqueda.getTxtCategoriaBusqueda()
								.setText("EQUIPOS DONDE FUE UTILIZADO COMO REEMPLAZO EL COMPONENTE:      "+"' " 
										+ ventanaBusqueda.getComboBuscador().getSelectedItem().toString()+" '" );

						cargarTablaListadoBusqueda(ventanaBusqueda.getComboBuscador().getSelectedItem().toString());

					}

				}
			}

			this.ventanaBusqueda.dispose();
			this.ventanaBusqueda = null;
		}

	}

	private void llenarComboComponentesOriginal() {

		modelo.ListarRepuestos(ventanaBusqueda.getComboBuscador());
		ventanaBusqueda.getComboBuscador().setSelectedIndex(-1);

	}

	private void llenarComboComponentesReemplazo() {

		modelo.ListarRepuestosReemplazo(ventanaBusqueda.getComboBuscador());
		ventanaBusqueda.getComboBuscador().setSelectedIndex(-1);

	}

	private void cargarTablaListadoBusqueda(String componente) {

		this.ventanaTablaBusqueda.getModelReparaciones().setRowCount(0); // Para
		// vaciar
		// tabla
		this.ventanaTablaBusqueda.getModelReparaciones().setColumnCount(0);
		this.ventanaTablaBusqueda.getModelReparaciones()
				.setColumnIdentifiers(this.ventanaTablaBusqueda.getNombreColumnas());

		if (ventanaBusqueda.getRdbComponenteOriginal().isSelected()) {

			this.Reparaciones_en_tabla = modelo.obtenerReparacionPorCompOriginal(componente);

		}

		if (ventanaBusqueda.getRdbComponenteReemplazado().isSelected()) {

			this.Reparaciones_en_tabla = modelo.obtenerReparacionPorCompReemplazo(componente);

		}

		for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {

			Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(),
					this.Reparaciones_en_tabla.get(i).getFecha_Entrada(),
					this.Reparaciones_en_tabla.get(i).getCliente(), this.Reparaciones_en_tabla.get(i).getSucursal(),
					this.Reparaciones_en_tabla.get(i).getNombreEquipo(), this.Reparaciones_en_tabla.get(i).getMarca(),
					this.Reparaciones_en_tabla.get(i).getModelo(),
					this.Reparaciones_en_tabla.get(i).getComponenteOriginal(),
					this.Reparaciones_en_tabla.get(i).getComponenteReemplazo(),};
			this.ventanaTablaBusqueda.getModelReparaciones().addRow(fila);
		}

		ventanaTablaBusqueda.setCellRender(this.ventanaTablaBusqueda.getTblReparaciones());

		this.ventanaTablaBusqueda.show();

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (arg0.getSource() == this.ventanaTablaBusqueda.getBtnMax()) {

			if (clickMax % 2 != 0) {

				ventanaTablaBusqueda.setExtendedState(max);
				this.ventanaTablaBusqueda.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/minimizar.png")));
				ventanaTablaBusqueda.setVisible(true);

			} else {

				ventanaTablaBusqueda.setExtendedState(min);
				this.ventanaTablaBusqueda.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
				ventanaTablaBusqueda.setVisible(true);

			}
			clickMax++;
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
