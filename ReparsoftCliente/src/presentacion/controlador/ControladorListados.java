package presentacion.controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import dto.ReparacionDTO;
import modelo.Agenda;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaEstadisticas;
import presentacion.vista.VentanaListadoReparaciones;
import javax.swing.*;

public class ControladorListados
		implements ActionListener, MouseListener, KeyListener, ItemListener, MouseMotionListener {

	private Agenda modelo;

	private VentanaListadoReparaciones ventanaListadoReparaciones;

	private ControladorReparacion controladorReparacion;
	private VentanaEquipos ventanaEquipos;
	private VentanaEstadisticas ventanaEstadisticas;
	// private int max = Frame.MAXIMIZED_BOTH;
	// private int min = Frame.NORMAL;

	// private int clickMax = 1;
	// private int clickMin = 1;

	public int NumeroELSSeleccionado;

	// private TableRowSorter<DefaultTableModel> sorter;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	@SuppressWarnings("unused")
	private ControladorUsuLogin controladorUsuLogin;

	
	private int filtro;
	
	public ControladorListados(VentanaListadoReparaciones ventanaListadoReparaciones, Agenda modelo,
			ControladorUsuLogin controladorUsuLogin, ControladorReparacion controladorReparacion) {

		this.ventanaListadoReparaciones = ventanaListadoReparaciones;
		this.controladorUsuLogin = controladorUsuLogin;
		this.controladorReparacion = controladorReparacion;
		this.modelo = modelo;

		agregarListenerVentanaListados();

		cargarTablaListadoReparaciones();

		llenarComboCliente();
		llenarComboSucursales();
		llenarComboMarca();
		llenarComboNombreEquipo();
		llenarComboModelo();
		llenarComboAviso();
		llenarComboEstadoCom();
		llenarComboEstadoFis();
		llenarComboEstadoTec();
		llenarComboELS();
		llenarComboTecnico();

		
		
		
	}

	public void inicializar() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		ventanaListadoReparaciones.getTblReparaciones().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				// int row =
				// ventanaListadoReparaciones.getTblReparaciones().rowAtPoint(e.getPoint());
				int column = ventanaListadoReparaciones.getTblReparaciones().columnAtPoint(e.getPoint());

				// Verificar si el mouse est� sobre la celda deseada
				if (column == 0) {
					ventanaListadoReparaciones.getTblReparaciones()
							.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					ventanaListadoReparaciones.getTblReparaciones().setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		if (arg0.getSource() == this.ventanaListadoReparaciones.getBtnFiltrar()) {

			DefaultTableModel dm;
			dm = (DefaultTableModel) this.ventanaListadoReparaciones.getTblReparaciones().getModel();

			TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);

			this.ventanaListadoReparaciones.getTblReparaciones().setRowSorter(tr);

			RowFilter<DefaultTableModel, Object> rf = null;
			List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>();

			if (!ventanaListadoReparaciones.getRadioButtonCliente().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonMarca().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonSucursal().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonEquipo().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonModelo().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonAviso().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonEstadoCom().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonEstadoTec().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonELS().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonTecnico().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonEstadoFis().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonPresupEnviado().isSelected()
					&& !ventanaListadoReparaciones.getRadioButtonPresupGenerado().isSelected()) {
				this.ventanaListadoReparaciones.getTblReparaciones().setRowSorter(null);
			}

			if (ventanaListadoReparaciones.getRadioButtonCliente().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroCliente().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroCliente().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroCliente().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 2)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonMarca().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroMarca().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroMarca().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroMarca().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 5)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonSucursal().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroSucursal().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroSucursal().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroSucursal().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 3)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonEquipo().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEquipo().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEquipo().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroEquipo().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 4)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonModelo().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroModelo().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroModelo().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroModelo().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 6)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonAviso().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroAviso().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroAviso().getSelectedItem().toString() != null) {
				rfs.add(RowFilter.regexFilter(
						"^" + ventanaListadoReparaciones.getComboFiltroAviso().getSelectedItem().toString() + "$", 8));
			}

			if (ventanaListadoReparaciones.getRadioButtonEstadoCom().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEstadoCom().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEstadoCom().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroEstadoCom().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 12)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonEstadoFis().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEstadoFis().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEstadoFis().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroEstadoFis().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 13)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonEstadoTec().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEstadoTec().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEstadoTec().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroEstadoTec().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 11)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonELS().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroELS().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroELS().getSelectedItem().toString() != null) {
				rfs.add(RowFilter.regexFilter(
						"^" + ventanaListadoReparaciones.getComboFiltroELS().getSelectedItem().toString() + "$", 0));
			}

			if (ventanaListadoReparaciones.getRadioButtonTecnico().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroTecnico().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroTecnico().getSelectedItem().toString() != null) {
				String searchText = ventanaListadoReparaciones.getComboFiltroTecnico().getSelectedItem().toString();
				rfs.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(searchText) + "$", 14)); // (?i) para ignorar
																								// mayúsculas/minúsculas
			}

			if (ventanaListadoReparaciones.getRadioButtonPresupEnviado().isSelected()) {
				rfs.add(RowFilter.regexFilter(
						String.valueOf(ventanaListadoReparaciones.getChckbxPresupuestoEnviado().isSelected()), 18));
			}

			if (ventanaListadoReparaciones.getRadioButtonPresupGenerado().isSelected()) {
				rfs.add(RowFilter.regexFilter(
						String.valueOf(ventanaListadoReparaciones.getChckbxPresupuestoGenerado().isSelected()), 17));
			}

			rf = RowFilter.andFilter(rfs);

			tr.setRowFilter(rf);

		}

		else if (this.ventanaListadoReparaciones != null
				&& arg0.getSource() == this.ventanaListadoReparaciones.getBtnMostrarTodo()) {

			this.ventanaListadoReparaciones.getTblReparaciones().setRowSorter(null);
			this.ventanaListadoReparaciones.getComboFiltroCliente().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroCliente().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroSucursal().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroSucursal().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroMarca().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroMarca().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroELS().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroELS().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroEquipo().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroEquipo().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroModelo().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroModelo().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroAviso().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroAviso().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroTecnico().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroTecnico().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroEstadoCom().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroEstadoCom().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroEstadoFis().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroEstadoFis().setEnabled(false);
			this.ventanaListadoReparaciones.getComboFiltroEstadoTec().setSelectedItem(null);
			this.ventanaListadoReparaciones.getComboFiltroEstadoTec().setEnabled(false);
			this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setSelected(false);
			this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setEnabled(false);
			this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setSelected(false);
			this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setEnabled(false);
			this.ventanaListadoReparaciones.getRadioButtonCliente().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonSucursal().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonMarca().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonELS().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonEquipo().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonModelo().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonAviso().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonTecnico().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonEstadoCom().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonEstadoFis().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonEstadoTec().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonPresupEnviado().setSelected(false);
			this.ventanaListadoReparaciones.getRadioButtonPresupGenerado().setSelected(false);

		}

		else if (this.ventanaListadoReparaciones != null
				&& arg0.getSource() == this.ventanaListadoReparaciones.getBtnEstadisticas()) {

			ventanaEstadisticas = new VentanaEstadisticas(this);
			agregarListenerAventanaEstadisticas();

			llenarcomboFiltro();

		}

	}

	private void cargarTablaListadoReparaciones() {

		this.ventanaListadoReparaciones.getModelReparaciones().setRowCount(0); // Para
		// vaciar
		// tabla
		this.ventanaListadoReparaciones.getModelReparaciones().setColumnCount(0);
		this.ventanaListadoReparaciones.getModelReparaciones()
				.setColumnIdentifiers(this.ventanaListadoReparaciones.getNombreColumnas());

		this.Reparaciones_en_tabla = (List<ReparacionDTO>) modelo.obtenerReparacion();

		for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {

			Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(),
					this.Reparaciones_en_tabla.get(i).getFecha_Entrada(),
					this.Reparaciones_en_tabla.get(i).getCliente(), this.Reparaciones_en_tabla.get(i).getSucursal(),
					this.Reparaciones_en_tabla.get(i).getNombreEquipo(), this.Reparaciones_en_tabla.get(i).getMarca(),
					this.Reparaciones_en_tabla.get(i).getModelo(), this.Reparaciones_en_tabla.get(i).getNumeroDeSerie(),
					this.Reparaciones_en_tabla.get(i).getAviso(),
					this.Reparaciones_en_tabla.get(i).getFechadereparacion(),
					this.Reparaciones_en_tabla.get(i).getClienteCliente(),
					this.Reparaciones_en_tabla.get(i).getEstadoTecnico(),
					this.Reparaciones_en_tabla.get(i).getEstadoComercial(),
					this.Reparaciones_en_tabla.get(i).getEstadoFisico(),
					this.Reparaciones_en_tabla.get(i).getNombreUsuario(), this.Reparaciones_en_tabla.get(i).getCodigo(),
					this.Reparaciones_en_tabla.get(i).getNumeroRemitoSalida(),
					this.Reparaciones_en_tabla.get(i).getPresupuestoGenerado(),
					this.Reparaciones_en_tabla.get(i).getPresupuestoEnviado(),
					this.Reparaciones_en_tabla.get(i).getPrecioPeso(),
					this.Reparaciones_en_tabla.get(i).getPrecioDolar(), this.Reparaciones_en_tabla.get(i).getPago(), };
			this.ventanaListadoReparaciones.getModelReparaciones().addRow(fila);

//			String presupuestoPeso = monedaFormatter.formatPeso(reparacion.getPrecioPeso().toString());
//			String pagoPeso = monedaFormatter.formatPeso(reparacion.getPago().toString());
//
//			ventanaVisualizarEquipos.setTextPresupuesto(presupuestoPeso);
//			ventanaVisualizarEquipos.setTextPago(pagoPeso);

		}

		ventanaListadoReparaciones.setCellRender(this.ventanaListadoReparaciones.getTblReparaciones());

		this.ventanaListadoReparaciones.setVisible(true);
		;

	}

	public void agregarListenerVentanaListados() {

		this.ventanaListadoReparaciones.getBtnFiltrar().addActionListener(this);
		this.ventanaListadoReparaciones.getBtnMostrarTodo().addActionListener(this);
		this.ventanaListadoReparaciones.getBtnEstadisticas().addActionListener(this);

		this.ventanaListadoReparaciones.getComboFiltroCliente().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroMarca().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroSucursal().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroAviso().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroEquipo().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroEstadoCom().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroEstadoFis().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroEstadoTec().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroModelo().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroELS().addActionListener(this);
		this.ventanaListadoReparaciones.getComboFiltroTecnico().addActionListener(this);

		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().addActionListener(this);

		this.ventanaListadoReparaciones.getRadioButtonCliente().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonMarca().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonSucursal().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonAviso().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEquipo().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoCom().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoFis().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoTec().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonModelo().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonELS().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonTecnico().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonPresupEnviado().addActionListener(this);
		this.ventanaListadoReparaciones.getRadioButtonPresupGenerado().addActionListener(this);

		this.ventanaListadoReparaciones.getRadioButtonCliente().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonMarca().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonSucursal().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonAviso().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEquipo().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoCom().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoFis().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoTec().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonModelo().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonELS().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonTecnico().addItemListener(this);
		this.ventanaListadoReparaciones.getBtnMax().addMouseListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().addMouseListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonPresupEnviado().addItemListener(this);
		this.ventanaListadoReparaciones.getRadioButtonPresupGenerado().addItemListener(this);
		this.ventanaListadoReparaciones.getTblReparaciones().addMouseListener(this);
		this.ventanaListadoReparaciones.getTblReparaciones().addMouseMotionListener(this);

		this.ventanaListadoReparaciones.getChckbxELS().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxELS().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxELS().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEntrada().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEntrada().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEntrada().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxCliente().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxCliente().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxCliente().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxSucursal().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxSucursal().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxSucursal().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEquipo().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEquipo().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEquipo().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxMarca().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxMarca().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxMarca().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxModelo().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxModelo().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxModelo().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxSerie().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxSerie().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxSerie().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxAviso().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxAviso().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxAviso().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxRevisión().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxRevisión().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxRevisión().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxClienteCliente().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxClienteCliente().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxClienteCliente().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEstadoTec().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoTec().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoTec().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEstadoCom().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoCom().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoCom().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEstadoFis().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoFis().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoFis().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxTecnico().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxTecnico().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxTecnico().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxUbicacionRemito().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxUbicacionRemito().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxUbicacionRemito().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxNumeroRemito().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxNumeroRemito().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxNumeroRemito().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPrecioPeso().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioPeso().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioPeso().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPrecioDolar().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioDolar().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioDolar().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPago().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPago().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPago().addMouseListener(this);

		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroCliente());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroMarca());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroSucursal());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroAviso());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroEquipo());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroEstadoCom());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroEstadoFis());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroEstadoTec());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroModelo());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroELS());
		AutoCompleteDecorator.decorate(ventanaListadoReparaciones.getComboFiltroTecnico());

	}

	private void llenarComboCliente() {

		modelo.ListarCliente(ventanaListadoReparaciones.getComboFiltroCliente());
		ventanaListadoReparaciones.getComboFiltroCliente().setSelectedIndex(-1);

	}

	private void llenarComboSucursales() {

		modelo.ListarSucursales(ventanaListadoReparaciones.getComboFiltroSucursal());
		ventanaListadoReparaciones.getComboFiltroSucursal().setSelectedIndex(-1);

	}

	private void llenarComboMarca() {

		modelo.ListarMarca(ventanaListadoReparaciones.getComboFiltroMarca());

		ventanaListadoReparaciones.getComboFiltroMarca().setSelectedIndex(-1);

	}

	private void llenarComboModelo() {

		modelo.ListarModelos(ventanaListadoReparaciones.getComboFiltroModelo());
		ventanaListadoReparaciones.getComboFiltroModelo().setSelectedIndex(-1);

	}

	private void llenarComboNombreEquipo() {

		modelo.ListarEquipo(ventanaListadoReparaciones.getComboFiltroEquipo());

		ventanaListadoReparaciones.getComboFiltroEquipo().setSelectedIndex(-1);

	}

	private void llenarComboEstadoCom() {

		modelo.ListarEstadoCom(ventanaListadoReparaciones.getComboFiltroEstadoCom());

		ventanaListadoReparaciones.getComboFiltroEstadoCom().setSelectedIndex(-1);

	}

	private void llenarComboEstadoFis() {

		modelo.ListarEstadoFis(ventanaListadoReparaciones.getComboFiltroEstadoFis());

		ventanaListadoReparaciones.getComboFiltroEstadoFis().setSelectedIndex(-1);

	}

	private void llenarComboEstadoTec() {

		modelo.ListarEstadoTec(ventanaListadoReparaciones.getComboFiltroEstadoTec());

		ventanaListadoReparaciones.getComboFiltroEstadoTec().setSelectedIndex(-1);

	}

	private void llenarComboAviso() {

		modelo.ListarAvisos(ventanaListadoReparaciones.getComboFiltroAviso());

		ventanaListadoReparaciones.getComboFiltroAviso().setSelectedIndex(-1);

	}

	private void llenarComboELS() {

		modelo.ListarELS(ventanaListadoReparaciones.getComboFiltroELS());

		ventanaListadoReparaciones.getComboFiltroELS().setSelectedIndex(-1);

	}

	private void llenarComboTecnico() {

		modelo.ListarTecnicos(ventanaListadoReparaciones.getComboFiltroTecnico());

		ventanaListadoReparaciones.getComboFiltroTecnico().setSelectedIndex(-1);

	}

	public void cerraVentanaListadoEquipos() {

		this.ventanaListadoReparaciones.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(ventanaListadoReparaciones,
						"¿Desea salir de la ventana 'LISTADO'?", "Aviso", JOptionPane.YES_NO_OPTION);

				if (opcion == JOptionPane.YES_OPTION) {

					ventanaListadoReparaciones.dispose();
					ventanaListadoReparaciones = null;

					if (ventanaEquipos != null) {

						ventanaEquipos.dispose();
						ventanaEquipos = null;

					}

				}

			}

		});

	}

	private void agregarListenerAventanaEstadisticas() {

		ventanaEstadisticas.getComboFiltro().addActionListener(this);
		ventanaEstadisticas.getComboAnio().addActionListener(this);
		ventanaEstadisticas.getComboTecnico().addActionListener(this);
		ventanaEstadisticas.getComboMes().addActionListener(this);
		
		

	}

	@SuppressWarnings("unchecked")
	private void llenarcomboFiltro() {

		
		ventanaEstadisticas.getComboFiltro().addItem("--Seleccionar filtro--");
		ventanaEstadisticas.getComboFiltro().addItem("POR AÑO");
		ventanaEstadisticas.getComboFiltro().addItem("POR TÉCNICO");
		ventanaEstadisticas.getComboFiltro().addItem("POR CLIENTE");
		
		llenarcomboAnio();
		

		ventanaEstadisticas.getComboFiltro().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaEstadisticas.getComboFiltro().getSelectedItem() != null) {

					 
					filtro = ventanaEstadisticas.getComboFiltro().getSelectedIndex();
					//System.out.println(ventanaEstadisticas.getComboFiltro().getSelectedIndex());

					switch (filtro) {
					
					
					case 0:
						
						ventanaEstadisticas.getLblAnio().setVisible(false);
						ventanaEstadisticas.getComboAnio().setVisible(false);
						
						ventanaEstadisticas.getLblMes().setVisible(false);
						ventanaEstadisticas.getComboMes().setVisible(false);
						
						ventanaEstadisticas.getLblTecnico().setVisible(false);
						ventanaEstadisticas.getComboTecnico().setVisible(false);
						
						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);
						
						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();
										
					break;
					
					case 1:
						
						ventanaEstadisticas.getLblAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
						ventanaEstadisticas.getComboAnio().setVisible(true);
						
						ventanaEstadisticas.getLblMes().setVisible(false);
						ventanaEstadisticas.getComboMes().setVisible(false);
						
						ventanaEstadisticas.getLblTecnico().setVisible(false);
						ventanaEstadisticas.getComboTecnico().setVisible(false);
						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);
						
						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();
						
						
										
					break;

					case 2:
						
						ventanaEstadisticas.getLblAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
						ventanaEstadisticas.getComboAnio().setVisible(true);
						
						ventanaEstadisticas.getLblMes().setVisible(false);
						ventanaEstadisticas.getComboMes().setVisible(false);
						
						ventanaEstadisticas.getLblTecnico().setText("TÉCNICO");
						ventanaEstadisticas.getLblTecnico().setVisible(true);
						ventanaEstadisticas.getComboTecnico().setSelectedIndex(-1);
						ventanaEstadisticas.getComboTecnico().setVisible(true);
						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);
						
						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();
										
					break;	
					case 3:
						
						ventanaEstadisticas.getLblAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
						ventanaEstadisticas.getComboCliente().setVisible(true);
						ventanaEstadisticas.getComboCliente().setSelectedIndex(-1);
						ventanaEstadisticas.getLblTecnico().setText("CLIENTE");
						ventanaEstadisticas.getLblTecnico().setVisible(true);
						
						ventanaEstadisticas.getLblMes().setVisible(false);
						ventanaEstadisticas.getComboMes().setVisible(false);
											
						ventanaEstadisticas.getComboTecnico().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);
						
						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();
						
										

					break;

					default:
						break;
					}

				}

			}
		});

		
		
	}

	@SuppressWarnings("unchecked")
	private void llenarcomboAnio() {
		
		for (int i = 2024; i < 2030; i++) {

			ventanaEstadisticas.getComboAnio().addItem(i);

		}		
		
		ventanaEstadisticas.getComboAnio().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaEstadisticas.getComboAnio().getSelectedItem() != null) {
					
					
					ventanaEstadisticas.getLblAnioDatos().setText(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
					ventanaEstadisticas.getPanel_Datos().setVisible(true);
					
					llenarDatosPorAnio();
					ventanaEstadisticas.getPanel_Ingresos().removeAll();
					ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
					ventanaEstadisticas.getPanel_Facturacion().removeAll();
					mostrarGraficos();
					

					
				}

			}
		});
		
		
	}

	private void llenarDatosPorAnio() {
		
		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		int cantidadIngresosPorAnio = modelo.dameIngresosPorAnio(anio);
		int cantidadDiagnosticosPorAnio = modelo.dameDiagnosticosPorAnio(anio);
		
		
		
		ventanaEstadisticas.getTextIngresosTotales().setText(Integer.toString(cantidadIngresosPorAnio));
		ventanaEstadisticas.getTextDiagnosticosTotales().setText(Integer.toString(cantidadDiagnosticosPorAnio));
		
	}
	
	private void mostrarGraficos(){
		
		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		
		List<Integer> listaIngresos = modelo.dameIngresosPorAnioPorMes(anio);
		List<Integer> listaDiagnosticos = modelo.dameDiagnosticosPorAnioPorMes(anio);
		List<Integer> listaFacturacion = modelo.dameFacturacionPorAnioPorMes(anio);
		
		
		DefaultCategoryDataset datosIngresos =  new DefaultCategoryDataset();
		DefaultCategoryDataset datosDiagnosticos =  new DefaultCategoryDataset();
		DefaultCategoryDataset datosFacturacion =  new DefaultCategoryDataset();

		
		datosIngresos.setValue(listaIngresos.get(0), "Ingresos", "ENE");
		datosIngresos.setValue(listaIngresos.get(1), "Ingresos", "FEB");
		datosIngresos.setValue(listaIngresos.get(2), "Ingresos", "MAR");
		datosIngresos.setValue(listaIngresos.get(3), "Ingresos", "ABR");
		datosIngresos.setValue(listaIngresos.get(4), "Ingresos", "MAY");
		datosIngresos.setValue(listaIngresos.get(5), "Ingresos", "JUN");
		datosIngresos.setValue(listaIngresos.get(6), "Ingresos", "JUL");
		datosIngresos.setValue(listaIngresos.get(7), "Ingresos", "AGO");
		datosIngresos.setValue(listaIngresos.get(8), "Ingresos", "SEP");
		datosIngresos.setValue(listaIngresos.get(9), "Ingresos", "OCT");
		datosIngresos.setValue(listaIngresos.get(10), "Ingresos", "NOV");
		datosIngresos.setValue(listaIngresos.get(11), "Ingresos", "DIC");
		
		
		datosDiagnosticos.setValue(listaDiagnosticos.get(0), "Diagnósticos", "ENE");
		datosDiagnosticos.setValue(listaDiagnosticos.get(1), "Diagnósticos", "FEB");
		datosDiagnosticos.setValue(listaDiagnosticos.get(2), "Diagnósticos", "MAR");
		datosDiagnosticos.setValue(listaDiagnosticos.get(3), "Diagnósticos", "ABR");
		datosDiagnosticos.setValue(listaDiagnosticos.get(4), "Diagnósticos", "MAY");
		datosDiagnosticos.setValue(listaDiagnosticos.get(5), "Diagnósticos", "JUN");
		datosDiagnosticos.setValue(listaDiagnosticos.get(6), "Diagnósticos", "JUL");
		datosDiagnosticos.setValue(listaDiagnosticos.get(7), "Diagnósticos", "AGO");
		datosDiagnosticos.setValue(listaDiagnosticos.get(8), "Diagnósticos", "SEP");
		datosDiagnosticos.setValue(listaDiagnosticos.get(9), "Diagnósticos", "OCT");
		datosDiagnosticos.setValue(listaDiagnosticos.get(10), "Diagnósticos", "NOV");
		datosDiagnosticos.setValue(listaDiagnosticos.get(11), "Diagnósticos", "DIC");
		
				
		datosFacturacion.setValue(listaFacturacion.get(0), "Facturación", "ENE");
		datosFacturacion.setValue(listaFacturacion.get(1), "Facturación", "FEB");
		datosFacturacion.setValue(listaFacturacion.get(2), "Facturación", "MAR");
		datosFacturacion.setValue(listaFacturacion.get(3), "Facturación", "ABR");
		datosFacturacion.setValue(listaFacturacion.get(4), "Facturación", "MAY");
		datosFacturacion.setValue(listaFacturacion.get(5), "Facturación", "JUN");
		datosFacturacion.setValue(listaFacturacion.get(6), "Facturación", "JUL");
		datosFacturacion.setValue(listaFacturacion.get(7), "Facturación", "AGO");
		datosFacturacion.setValue(listaFacturacion.get(8), "Facturación", "SEP");
		datosFacturacion.setValue(listaFacturacion.get(9), "Facturación", "OCT");
		datosFacturacion.setValue(listaFacturacion.get(10), "Facturación", "NOV");
		datosFacturacion.setValue(listaFacturacion.get(11), "Facturación", "DIC");
		
			
		JFreeChart grafico_ingresos = ChartFactory.createBarChart("Ingresos", "Mes", "Cantidad", datosIngresos,PlotOrientation.VERTICAL,false,true,false);
		JFreeChart grafico_diagnosticos = ChartFactory.createBarChart("Diagnósicos", "Mes", "Cantidad", datosDiagnosticos,PlotOrientation.VERTICAL,false,true,false);
		JFreeChart grafico_facturacion = ChartFactory.createBarChart("Facturación", "Mes", "Pesos($)", datosFacturacion,PlotOrientation.VERTICAL,false,true,false);
		
		
		
		
		
		
		CategoryPlot plot_ingreso =(CategoryPlot) grafico_ingresos.getPlot();
		CategoryPlot plot_diagnostico =(CategoryPlot) grafico_diagnosticos.getPlot();
		CategoryPlot plot_facturacion =(CategoryPlot) grafico_facturacion.getPlot();
		
		BarRenderer renderer_ingreso = (BarRenderer) plot_ingreso.getRenderer();
		renderer_ingreso.setDrawBarOutline(false);        
        GradientPaint gp0= new GradientPaint(0.0f,0.0f,Color.blue,0.0f,0.0f,new Color(0,0,64));
        renderer_ingreso.setSeriesPaint(0,gp0);
        
        BarRenderer renderer_diagnostico = (BarRenderer) plot_diagnostico.getRenderer();
        renderer_diagnostico.setDrawBarOutline(false);      
        GradientPaint gp1= new GradientPaint(0.0f,0.0f,Color.green,0.0f,0.0f,new Color(0,64,0));
        renderer_diagnostico.setSeriesPaint(0,gp1);
                
        BarRenderer renderer_facturacion = (BarRenderer) plot_facturacion.getRenderer();
        renderer_facturacion.setDrawBarOutline(false);      
        GradientPaint gp2= new GradientPaint(0.0f,0.0f,Color.red,0.0f,0.0f,new Color(64,0,0));
        renderer_facturacion.setSeriesPaint(0,gp2);
		

		ChartPanel panelGraficoIngresos= new ChartPanel(grafico_ingresos);
		panelGraficoIngresos.setMouseWheelEnabled(true);
		panelGraficoIngresos.setPreferredSize(new Dimension(700,40));
		
		ChartPanel panelGraficoDiagnosticos= new ChartPanel(grafico_diagnosticos);
		panelGraficoDiagnosticos.setMouseWheelEnabled(true);
		panelGraficoDiagnosticos.setPreferredSize(new Dimension(700,40));
		
		ChartPanel panelGraficoIngresosFacturacion= new ChartPanel(grafico_facturacion);
		panelGraficoIngresosFacturacion.setMouseWheelEnabled(true);
		panelGraficoIngresosFacturacion.setPreferredSize(new Dimension(700,40));
				
		ventanaEstadisticas.getPanel_Ingresos().add(panelGraficoIngresos,BorderLayout.CENTER);
		ventanaEstadisticas.getPanel_Diagnosticos().add(panelGraficoDiagnosticos,BorderLayout.CENTER);
		ventanaEstadisticas.getPanel_Facturacion().add(panelGraficoIngresosFacturacion,BorderLayout.CENTER);
		
		
		//ventanaEstadisticas.pack();
		ventanaEstadisticas.repaint();
		
		
		
		
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

		if (this.ventanaListadoReparaciones != null) {
			if (arg0.getSource() == this.ventanaListadoReparaciones.getTblReparaciones()) {

				int row = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedRow();
				int col = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedColumn();

				int els = 0;
				if (col == 0)

					els = Integer.parseInt(
							this.ventanaListadoReparaciones.getTblReparaciones().getValueAt(row, col).toString());

				NumeroELSSeleccionado = els;

				if (NumeroELSSeleccionado != 0) {

					try {
						controladorReparacion.TomarDatosDeTablasListado(NumeroELSSeleccionado);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					controladorReparacion.agregarListenersVentanaVisualizarEquiposListado();

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxELS()) {
				if (this.ventanaListadoReparaciones.getChckbxELS().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(100);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEntrada()) {
				if (this.ventanaListadoReparaciones.getChckbxEntrada().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(100);
				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxSucursal()) {
				if (this.ventanaListadoReparaciones.getChckbxSucursal().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEquipo()) {
				if (this.ventanaListadoReparaciones.getChckbxEquipo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxMarca()) {
				if (this.ventanaListadoReparaciones.getChckbxMarca().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(250);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxModelo()) {
				if (this.ventanaListadoReparaciones.getChckbxModelo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxSerie()) {
				if (this.ventanaListadoReparaciones.getChckbxSerie().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxAviso()) {
				if (this.ventanaListadoReparaciones.getChckbxAviso().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxRevisión()) {
				if (this.ventanaListadoReparaciones.getChckbxRevisión().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(110);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxClienteCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxClienteCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoTec()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoTec().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoCom()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoCom().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoFis()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoFis().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxTecnico()) {
				if (this.ventanaListadoReparaciones.getChckbxTecnico().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxUbicacionRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxUbicacionRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxNumeroRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxNumeroRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(80);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioPeso()) {
				if (this.ventanaListadoReparaciones.getChckbxPrecioPeso().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioDolar()) {
				if (this.ventanaListadoReparaciones.getChckbxPrecioDolar().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPago()) {
				if (this.ventanaListadoReparaciones.getChckbxPago().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMaxWidth(150);

				}

			}

		}

		if (arg0.getSource() == this.ventanaListadoReparaciones.getBtnMax()) {
			int state = ventanaListadoReparaciones.getExtendedState();

			if ((state & JFrame.MAXIMIZED_BOTH) == 0) {
				// La ventana no está maximizada, maximízala
				ventanaListadoReparaciones.setExtendedState(state | JFrame.MAXIMIZED_BOTH);
				this.ventanaListadoReparaciones.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/minimizar.png")));
			} else {
				// La ventana ya está maximizada, restaura el tamaño original
				ventanaListadoReparaciones.setExtendedState(state & ~JFrame.MAXIMIZED_BOTH);
				this.ventanaListadoReparaciones.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
			}

			// Forzar una actualización completa del contenido de la ventana
			ventanaListadoReparaciones.repaint();
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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (this.ventanaListadoReparaciones != null) {
			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonCliente()) {

				if (ventanaListadoReparaciones.getRadioButtonCliente().isSelected())
					this.ventanaListadoReparaciones.getComboFiltroCliente().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroCliente().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroCliente().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonMarca()) {
				if (ventanaListadoReparaciones.getRadioButtonMarca().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroMarca().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroMarca().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroMarca().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonSucursal()) {
				if (ventanaListadoReparaciones.getRadioButtonSucursal().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroSucursal().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroSucursal().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroSucursal().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonEquipo()) {
				if (ventanaListadoReparaciones.getRadioButtonEquipo().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEquipo().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEquipo().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEquipo().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonEstadoCom()) {
				if (ventanaListadoReparaciones.getRadioButtonEstadoCom().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEstadoCom().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEstadoCom().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEstadoCom().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonEstadoFis()) {
				if (ventanaListadoReparaciones.getRadioButtonEstadoFis().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEstadoFis().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEstadoFis().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEstadoFis().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonEstadoTec()) {
				if (ventanaListadoReparaciones.getRadioButtonEstadoTec().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEstadoTec().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEstadoTec().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEstadoTec().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonAviso()) {
				if (ventanaListadoReparaciones.getRadioButtonAviso().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroAviso().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroAviso().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroAviso().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonModelo()) {
				if (this.ventanaListadoReparaciones.getRadioButtonModelo().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroModelo().setEnabled(true);
				else
					this.ventanaListadoReparaciones.getComboFiltroModelo().setEnabled(false);
				ventanaListadoReparaciones.getComboFiltroModelo().setSelectedIndex(-1);

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonELS()) {
				if (ventanaListadoReparaciones.getRadioButtonELS().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroELS().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroELS().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroELS().setSelectedIndex(-1);
				}
			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonTecnico()) {
				if (this.ventanaListadoReparaciones.getRadioButtonTecnico().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroTecnico().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroTecnico().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroTecnico().setSelectedIndex(-1);
				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonPresupEnviado()) {
				if (this.ventanaListadoReparaciones.getRadioButtonPresupEnviado().isSelected())

					this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setEnabled(false);
					ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setSelected(false);
				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getRadioButtonPresupGenerado()) {
				if (this.ventanaListadoReparaciones.getRadioButtonPresupGenerado().isSelected())

					this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setEnabled(false);
					ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setSelected(false);
				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxELS()) {
				if (this.ventanaListadoReparaciones.getChckbxELS().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(100);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEntrada()) {
				if (this.ventanaListadoReparaciones.getChckbxEntrada().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(100);
				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxSucursal()) {
				if (this.ventanaListadoReparaciones.getChckbxSucursal().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEquipo()) {
				if (this.ventanaListadoReparaciones.getChckbxEquipo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxMarca()) {
				if (this.ventanaListadoReparaciones.getChckbxMarca().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(250);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxModelo()) {
				if (this.ventanaListadoReparaciones.getChckbxModelo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxSerie()) {
				if (this.ventanaListadoReparaciones.getChckbxSerie().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxAviso()) {
				if (this.ventanaListadoReparaciones.getChckbxMarca().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxRevisión()) {
				if (this.ventanaListadoReparaciones.getChckbxRevisión().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(110);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxClienteCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxClienteCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoTec()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoTec().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoCom()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoCom().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoFis()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoFis().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxTecnico()) {
				if (this.ventanaListadoReparaciones.getChckbxTecnico().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxUbicacionRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxUbicacionRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxNumeroRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxNumeroRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(80);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(80);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioPeso()) {
				if (this.ventanaListadoReparaciones.getChckbxPrecioPeso().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioDolar()) {
				if (this.ventanaListadoReparaciones.getChckbxPrecioDolar().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPago()) {
				if (this.ventanaListadoReparaciones.getChckbxPago().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
							.setMaxWidth(150);

				}

			}

		}

		if (this.ventanaListadoReparaciones != null) {
			if (e.getSource() == this.ventanaListadoReparaciones.getTblReparaciones()) {

				int row = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedRow();
				int col = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedColumn();

				int els = 0;
				if (col == 0)

					els = Integer.parseInt(
							this.ventanaListadoReparaciones.getTblReparaciones().getValueAt(row, col).toString());

				NumeroELSSeleccionado = els;

				if (NumeroELSSeleccionado != 0) {

					try {
						controladorReparacion.TomarDatosDeTablasListado(NumeroELSSeleccionado);
					} catch (ParseException error) {
						// TODO Auto-generated catch block
						error.printStackTrace();
					}

					controladorReparacion.agregarListenersVentanaVisualizarEquiposListado();

				}

			}

		}

	}

}
