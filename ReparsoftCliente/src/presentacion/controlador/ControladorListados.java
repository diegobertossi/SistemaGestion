package presentacion.controlador;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

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

import VistaPropias.CellRenderer;
import VistaPropias.Resaltador;
import dto.ClienteDTO;
import dto.PermisoDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import dto.SucursalDTO;
import modelo.Agenda;
import modelo.Permisos;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import presentacion.vista.VistaPrincipal;
import presentacion.vista.VentanaAgregarCliente;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaRolesUsuarios;
import presentacion.vista.VentanaSalidas;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaClientes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ControladorListados
		implements ActionListener, MouseListener, KeyListener, ItemListener, MouseMotionListener {

	private Agenda modelo;

	private VentanaListadoReparaciones ventanaListadoReparaciones;

	private ClienteDTO Cliente;

	private ControladorReparacion controladorReparacion;
	private VentanaEquipos ventanaEquipos;
	private ControladorUsuLogin controladorUsuLogin;

//	private SucursalDTO Sucursal;
//	private String Marca;
//	private String NombreEq = "";
//	private int idCli;
//	private String Modelo;
	private int max = Frame.MAXIMIZED_BOTH;
	private int min = Frame.NORMAL;
//	private int maxHorizontal = Frame.MAXIMIZED_HORIZ;
//	private int maxVertical = Frame.MAXIMIZED_VERT;

	private int clickMax = 1;
	// private int clickMin = 1;

	public int NumeroELSSeleccionado;
	// private ReparacionDTO reparacion;
	// private List<RepuestosDTO> Repuestos_en_tabla;

	private TableRowSorter<DefaultTableModel> sorter;

	private List<ReparacionDTO> Reparaciones_en_tabla;

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

				int row = ventanaListadoReparaciones.getTblReparaciones().rowAtPoint(e.getPoint());
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
			;
			this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setEnabled(false);

			this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setSelected(false);
			;
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

			System.out.println("ESTADISTICAS");
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
		}

		ventanaListadoReparaciones.setCellRender(this.ventanaListadoReparaciones.getTblReparaciones());

		this.ventanaListadoReparaciones.show();

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

		}

		if (arg0.getSource() == this.ventanaListadoReparaciones.getBtnMax()) {

			if (clickMax % 2 != 0) {

				ventanaListadoReparaciones.setExtendedState(max);
				this.ventanaListadoReparaciones.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/minimizar.png")));
				ventanaListadoReparaciones.setVisible(true);

			} else {

				ventanaListadoReparaciones.setExtendedState(min);
				this.ventanaListadoReparaciones.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
				ventanaListadoReparaciones.setVisible(true);

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

		if (e.getSource() == this.ventanaListadoReparaciones.getBtnMax()) {

			if (clickMax % 2 != 0) {

				ventanaListadoReparaciones.setExtendedState(max);
				this.ventanaListadoReparaciones.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/minimizar.png")));
				ventanaListadoReparaciones.setVisible(true);

			} else {

				ventanaListadoReparaciones.setExtendedState(min);
				this.ventanaListadoReparaciones.getBtnMax()
						.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
				ventanaListadoReparaciones.setVisible(true);

			}
			clickMax++;
		}
	}

}
