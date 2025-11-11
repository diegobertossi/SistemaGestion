package presentacion.controlador;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.List;
import javax.swing.ImageIcon;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import dto.ReparacionDTO;
import modelo.Agenda;
import presentacion.vista.VentanaBusqueda;
import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaTablaBusqueda;
import presentacion.vista.VentanaVisualizarEquipos;

public class ControladorBusquedas implements ActionListener, MouseListener, KeyListener, MouseMotionListener {

	private Agenda modelo;

	private VentanaBusqueda ventanaBusqueda;
	private VentanaTablaBusqueda ventanaTablaBusqueda;
	private VentanaVisualizarEquipos ventanaVisualizarEquipos;
	private ControladorReparacion controladorReparacion;
	

	@SuppressWarnings("unused")
	private ControladorBusquedas controladorBusqueda;

	@SuppressWarnings("unused")
	private int maxHorizontal = Frame.MAXIMIZED_HORIZ;
	@SuppressWarnings("unused")
	private int maxVertical = Frame.MAXIMIZED_VERT;
	private int clickMax = 1;
	@SuppressWarnings("unused")
	private int clickMin = 1;
	@SuppressWarnings("unused")
	private String buscarPor;

	private List<ReparacionDTO> Reparaciones_en_tabla;
	
	public int NumeroELSSeleccionado;

	public ControladorBusquedas(VentanaBusqueda ventanaBusqueda,ControladorReparacion controladorReparacion, Agenda modelo) {

		this.ventanaBusqueda = ventanaBusqueda;
		this.controladorReparacion = controladorReparacion;
		this.modelo = modelo;

		this.ventanaBusqueda.getBtnBuscar().addActionListener(this);
		this.ventanaBusqueda.getRdbComponenteOriginal().addActionListener(this);
		this.ventanaBusqueda.getRdbComponenteReemplazado().addActionListener(this);

	}

	public void inicializar() {

	}

	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("deprecation")
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
	
		ventanaTablaBusqueda.getTblReparaciones().addMouseMotionListener(this);
		
		this.ventanaTablaBusqueda.getTblReparaciones().addMouseListener(this);
		
		
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
		

	
	}

	private void handleTableClick(MouseEvent arg0) {
		int row = this.ventanaTablaBusqueda.getTblReparaciones().getSelectedRow();
		int col = this.ventanaTablaBusqueda.getTblReparaciones().getSelectedColumn();

		if (col == 0) {
			int els = Integer
					.parseInt(this.ventanaTablaBusqueda.getTblReparaciones().getValueAt(row, col).toString());

			NumeroELSSeleccionado = els;

			try {
				ventanaVisualizarEquipos = controladorReparacion.tomarDatosDeTablasListado(NumeroELSSeleccionado,
						ventanaVisualizarEquipos);
				ventanaVisualizarEquipos.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						actualizarTabla();
					}
				});

			} catch (ParseException e) {
				e.printStackTrace();
			}
			controladorReparacion.agregarListenersVentanaVisualizarEquiposListado(ventanaVisualizarEquipos);
		}
	}

	protected void actualizarTabla() {
		// TODO Auto-generated method stub
		
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
		if (arg0.getSource() == this.ventanaTablaBusqueda.getTblReparaciones()) {
			handleTableClick(arg0);
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (ventanaTablaBusqueda != null) {
			int column = ventanaTablaBusqueda.getTblReparaciones().columnAtPoint(arg0.getPoint());
			Cursor cursor = column == 0 ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor();
			ventanaTablaBusqueda.getTblReparaciones().setCursor(cursor);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
