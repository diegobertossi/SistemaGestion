package presentacion.controlador;

import java.awt.Color;
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

public class ControladorListados implements ActionListener, MouseListener, KeyListener, MouseMotionListener {

	private Agenda modelo;

	private VentanaListadoReparaciones ventanaListadoReparaciones;
	private VentanaVisualizarEquipos ventanaVisualizarEquipos;

	private ClienteDTO Cliente;

	private ControladorReparacion controladorReparacion;
	private ControladorUsuLogin controladorUsuLogin;

	private SucursalDTO Sucursal;
	private String Marca;
	private String NombreEq = "";
	private int idCli;
	private String Modelo;
	private int max = Frame.MAXIMIZED_BOTH;
	private int min = Frame.NORMAL;
	private int maxHorizontal = Frame.MAXIMIZED_HORIZ;
	private int maxVertical = Frame.MAXIMIZED_VERT;

	private int clickMax = 1;
	private int clickMin = 1;
	
	private int NumeroELSSeleccionado;
	private ReparacionDTO reparacion;
	private List<RepuestosDTO> Repuestos_en_tabla;

	private TableRowSorter<DefaultTableModel> sorter;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	public ControladorListados(VentanaListadoReparaciones ventanaListadoReparaciones, Agenda modelo,
			ControladorUsuLogin controladorUsuLogin) {

		this.ventanaListadoReparaciones = ventanaListadoReparaciones;
		this.controladorUsuLogin = controladorUsuLogin;

		this.ventanaListadoReparaciones.getBtnFiltrar().addActionListener(this);
		this.ventanaListadoReparaciones.getBtnMostrarTodo().addActionListener(this);

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

		this.ventanaListadoReparaciones.getRadioButtonCliente().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonMarca().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonSucursal().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonAviso().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEquipo().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoCom().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoFis().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonEstadoTec().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonModelo().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonELS().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonTecnico().addMouseListener(this);
		this.ventanaListadoReparaciones.getBtnMax().addMouseListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().addMouseListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonPresupEnviado().addMouseListener(this);
		this.ventanaListadoReparaciones.getRadioButtonPresupGenerado().addMouseListener(this);
		this.ventanaListadoReparaciones.getTblReparaciones().addMouseListener(this);
		this.ventanaListadoReparaciones.getTblReparaciones().addMouseMotionListener(this);

		this.modelo = modelo;

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

	public void inicializar() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

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
				rfs.add(RowFilter

						.regexFilter(ventanaListadoReparaciones.getComboFiltroCliente().getSelectedItem().toString(),
								2));
			}

			if (ventanaListadoReparaciones.getRadioButtonMarca().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroMarca().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroMarca().getSelectedItem().toString() != null) {
				rfs.add(RowFilter
						.regexFilter(ventanaListadoReparaciones.getComboFiltroMarca().getSelectedItem().toString(), 5));
			}

			if (ventanaListadoReparaciones.getRadioButtonSucursal().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroSucursal().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroSucursal().getSelectedItem().toString() != null) {
				rfs.add(RowFilter.regexFilter(
						ventanaListadoReparaciones.getComboFiltroSucursal().getSelectedItem().toString(), 3));
			}

			if (ventanaListadoReparaciones.getRadioButtonEquipo().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEquipo().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEquipo().getSelectedItem().toString() != null) {
				rfs.add(RowFilter.regexFilter(
						ventanaListadoReparaciones.getComboFiltroEquipo().getSelectedItem().toString(), 4));

			}

			if (ventanaListadoReparaciones.getRadioButtonModelo().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroModelo().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroModelo().getSelectedItem().toString() != null) {
				rfs.add(RowFilter.regexFilter(
						ventanaListadoReparaciones.getComboFiltroModelo().getSelectedItem().toString(), 6));

			}

			if (ventanaListadoReparaciones.getRadioButtonAviso().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroAviso().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroAviso().getSelectedItem().toString() != null) {
				rfs.add(RowFilter

						.regexFilter(ventanaListadoReparaciones.getComboFiltroAviso().getSelectedItem().toString(), 8));
			}

			if (ventanaListadoReparaciones.getRadioButtonEstadoCom().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEstadoCom().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEstadoCom().getSelectedItem().toString() != null) {

				rfs.add(RowFilter

						.regexFilter(ventanaListadoReparaciones.getComboFiltroEstadoCom().getSelectedItem().toString(),
								12));
			}

			if (ventanaListadoReparaciones.getRadioButtonEstadoFis().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEstadoFis().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEstadoFis().getSelectedItem().toString() != null) {
				rfs.add(RowFilter

						.regexFilter(ventanaListadoReparaciones.getComboFiltroEstadoFis().getSelectedItem().toString(),
								13));
			}

			if (ventanaListadoReparaciones.getRadioButtonEstadoTec().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroEstadoTec().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroEstadoTec().getSelectedItem().toString() != null) {
				rfs.add(RowFilter

						.regexFilter(ventanaListadoReparaciones.getComboFiltroEstadoTec().getSelectedItem().toString(),
								11));
			}

			if (ventanaListadoReparaciones.getRadioButtonELS().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroELS().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroELS().getSelectedItem().toString() != null) {
				rfs.add(RowFilter

						.regexFilter(ventanaListadoReparaciones.getComboFiltroELS().getSelectedItem().toString(), 0));
			}

			if (ventanaListadoReparaciones.getRadioButtonTecnico().isSelected()
					&& ventanaListadoReparaciones.getComboFiltroTecnico().getSelectedItem() != null
					&& ventanaListadoReparaciones.getComboFiltroTecnico().getSelectedItem().toString() != null) {
				rfs.add(RowFilter

						.regexFilter(ventanaListadoReparaciones.getComboFiltroTecnico().getSelectedItem().toString(),
								14));
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
					this.Reparaciones_en_tabla.get(i).getInformeEnviado(), };
			this.ventanaListadoReparaciones.getModelReparaciones().addRow(fila);
		}

		ventanaListadoReparaciones.setCellRender(this.ventanaListadoReparaciones.getTblReparaciones());

		this.ventanaListadoReparaciones.show();

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
			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonCliente()) {

				if (ventanaListadoReparaciones.getRadioButtonCliente().isSelected())
					this.ventanaListadoReparaciones.getComboFiltroCliente().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroCliente().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroCliente().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonMarca()) {
				if (ventanaListadoReparaciones.getRadioButtonMarca().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroMarca().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroMarca().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroMarca().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonSucursal()) {
				if (ventanaListadoReparaciones.getRadioButtonSucursal().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroSucursal().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroSucursal().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroSucursal().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonEquipo()) {
				if (ventanaListadoReparaciones.getRadioButtonEquipo().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEquipo().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEquipo().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEquipo().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonEstadoCom()) {
				if (ventanaListadoReparaciones.getRadioButtonEstadoCom().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEstadoCom().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEstadoCom().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEstadoCom().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonEstadoFis()) {
				if (ventanaListadoReparaciones.getRadioButtonEstadoFis().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEstadoFis().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEstadoFis().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEstadoFis().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonEstadoTec()) {
				if (ventanaListadoReparaciones.getRadioButtonEstadoTec().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroEstadoTec().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroEstadoTec().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroEstadoTec().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonAviso()) {
				if (ventanaListadoReparaciones.getRadioButtonAviso().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroAviso().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroAviso().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroAviso().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonModelo()) {
				if (this.ventanaListadoReparaciones.getRadioButtonModelo().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroModelo().setEnabled(true);
				else
					this.ventanaListadoReparaciones.getComboFiltroModelo().setEnabled(false);
				ventanaListadoReparaciones.getComboFiltroModelo().setSelectedIndex(-1);

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonELS()) {
				if (ventanaListadoReparaciones.getRadioButtonELS().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroELS().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroELS().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroELS().setSelectedIndex(-1);
				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonTecnico()) {
				if (this.ventanaListadoReparaciones.getRadioButtonTecnico().isSelected())

					this.ventanaListadoReparaciones.getComboFiltroTecnico().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getComboFiltroTecnico().setEnabled(false);
					ventanaListadoReparaciones.getComboFiltroTecnico().setSelectedIndex(-1);
				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonPresupEnviado()) {
				if (this.ventanaListadoReparaciones.getRadioButtonPresupEnviado().isSelected())

					this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setEnabled(false);
					ventanaListadoReparaciones.getChckbxPresupuestoEnviado().setSelected(false);
				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getRadioButtonPresupGenerado()) {
				if (this.ventanaListadoReparaciones.getRadioButtonPresupGenerado().isSelected())

					this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setEnabled(true);
				else {
					this.ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setEnabled(false);
					ventanaListadoReparaciones.getChckbxPresupuestoGenerado().setSelected(false);
				}

			}

			if (this.ventanaListadoReparaciones != null) {
				if (arg0.getSource() == this.ventanaListadoReparaciones.getTblReparaciones()) {

					int row = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedRow();
					int col = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedColumn();

					int els = 0;
					if (col == 0)

						els = Integer.parseInt(
								this.ventanaListadoReparaciones.getTblReparaciones().getValueAt(row, col).toString());
					System.out.println(els);
					
					NumeroELSSeleccionado = els;
					
					

					ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controladorReparacion);
					cerraVentanaVisualizarEquipo();
					
					try {
						TomarDatosDeTablas();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					controladorUsuLogin.verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);

					SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());
					
					
					

//						try {
//							TomarDatosDeTablas();
//						} catch (ParseException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}


					this.ventanaVisualizarEquipos.getBtnGuardarCambios().addActionListener(this);
					this.ventanaVisualizarEquipos.getBotonRegistroIngreso().addActionListener(this);
					this.ventanaVisualizarEquipos.getBotonEditarEstados().addActionListener(this);
					this.ventanaVisualizarEquipos.getBtnEditar().addActionListener(this);
					this.ventanaVisualizarEquipos.getBotonAvisoInforme().addActionListener(this);
					this.ventanaVisualizarEquipos.getBotonAvisoEquipoListo().addActionListener(this);
					this.ventanaVisualizarEquipos.getBotonRespuestaAlTecnico().addActionListener(this);
					this.ventanaVisualizarEquipos.getBtnGenerarRemito().addActionListener(this);
					this.ventanaVisualizarEquipos.getBotonPresupuestar().addActionListener(this);
					this.ventanaVisualizarEquipos.getBtnenviarCorreoOwsp().addActionListener(this);
					this.ventanaVisualizarEquipos.getComboClientes().addActionListener(this);
					this.ventanaVisualizarEquipos.getComboSucursal().addActionListener(this);
					this.ventanaVisualizarEquipos.getComboTecnico().addActionListener(this);
					this.ventanaVisualizarEquipos.getBtnRepuestos().addActionListener(this);
					this.ventanaVisualizarEquipos.getBtnEditarRepuesto().addActionListener(this);
					this.ventanaVisualizarEquipos.getBtnEliminarRepuesto().addActionListener(this);
					this.ventanaVisualizarEquipos.getTablaRepuestos().addMouseListener(this);
					this.ventanaVisualizarEquipos.getTablaRepuestos().addKeyListener(this);

					this.ventanaVisualizarEquipos.getTextPresupuesto().addKeyListener(this);
					this.ventanaVisualizarEquipos.getTextPresupuesto().addFocusListener(new FocusListener() {
						public void focusLost(FocusEvent e) {

							if (ventanaVisualizarEquipos.getTextPresupuesto().getText().isEmpty()) {

								ventanaVisualizarEquipos.getTextPresupuesto().setText("0.0");
							}

							controladorReparacion.verificarPresupuestoEditado();

						}

						@Override
						public void focusGained(FocusEvent arg0) {
							// TODO Auto-generated method stub

						}
					});

					this.ventanaVisualizarEquipos.getTextPago().addKeyListener(this);
					this.ventanaVisualizarEquipos.getTextPago().addFocusListener(new FocusListener() {
						public void focusLost(FocusEvent e) {

							if (ventanaVisualizarEquipos.getTextPago().getText().isEmpty()) {

								ventanaVisualizarEquipos.getTextPago().setText("0.0");
							}

							controladorReparacion.verificarPresupuestoEditado();
							// verificarPresupuestoEditado();

						}

						@Override
						public void focusGained(FocusEvent arg0) {
							// TODO Auto-generated method stub

						}
					});

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
	
	
	private void TomarDatosDeTablas() throws ParseException {

		// NumberFormat nf =
		// NumberFormat.getCurrencyInstance(Locale.getDefault());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		ventanaVisualizarEquipos.setTextELS(Integer.toString(NumeroELSSeleccionado));
		//NumeroELSSeleccionado = Integer.parseInt(ventanaVisualizarEquipos.getTextELS().toString());
		reparacion = modelo.dameReparacionXels(NumeroELSSeleccionado);

		// llenarComboTecnico();

		ventanaVisualizarEquipos.setTextNombreEquipo(reparacion.getNombreEquipo());
		ventanaVisualizarEquipos.setTextMarca(reparacion.getMarca());
		ventanaVisualizarEquipos.setTextModelo(reparacion.getModelo());
		ventanaVisualizarEquipos.setTextNSerie(reparacion.getNumeroDeSerie());

		// ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(reparacion.getPresupuestoGenerado());

		if (reparacion.getFalla() == null)
			ventanaVisualizarEquipos.setTextFalla("");
		else
			ventanaVisualizarEquipos.setTextFalla(reparacion.getFalla());

		ventanaVisualizarEquipos.setTextAvisoCliente(reparacion.getAviso());
		ventanaVisualizarEquipos.setTextClienteCliente(reparacion.getClienteCliente());
		ventanaVisualizarEquipos.setTextRemitoCliente(reparacion.getRemitoCliente());

		ventanaVisualizarEquipos.setTextCliente(reparacion.getCliente());
		ventanaVisualizarEquipos.setTextSucursal(reparacion.getSucursal());

		if (reparacion.getFecha_Entrada() == null)
			ventanaVisualizarEquipos.setTextFechaEntrada2(null);
		else
			ventanaVisualizarEquipos.setTextFechaEntrada2((dateFormat.parse(reparacion.getFecha_Entrada())));

		ventanaVisualizarEquipos.setTextEstadoFisico(reparacion.getEstadoFisico());
		ventanaVisualizarEquipos.setTextEstadoTecnico(reparacion.getEstadoTecnico());
		ventanaVisualizarEquipos.setTextEstadoComercial(reparacion.getEstadoComercial());
		ventanaVisualizarEquipos.setTextDiagnostico(reparacion.getSolucion());
		ventanaVisualizarEquipos.setTextInformeCliente(reparacion.getInformecliente());

		if (reparacion.getFechadereparacion() == null)
			ventanaVisualizarEquipos.setTextFechaReparacion2(null);
		else
			ventanaVisualizarEquipos.setTextFechaReparacion2((dateFormat.parse(reparacion.getFechadereparacion())));

		if (reparacion.getFechAceptacion() == null)
			ventanaVisualizarEquipos.setTextFechaRespuesta2(null);
		else
			ventanaVisualizarEquipos.setTextFechaRespuesta2((dateFormat.parse(reparacion.getFechAceptacion())));

		if (reparacion.getFechaFabr() == null)
			ventanaVisualizarEquipos.setFechaFabr2(null);
		else {
			ventanaVisualizarEquipos.setFechaFabr2((dateFormat.parse(reparacion.getFechaFabr())));
		}

		ventanaVisualizarEquipos.setTextNombreTecnico(reparacion.getNombreUsuario());
		// ventanaVisualizarEquipos.getComboTecnico().setSelectedIndex(reparacion.getidUsuario()-1);
		ventanaVisualizarEquipos.setTextOC(reparacion.getOrdendeCompra());
		ventanaVisualizarEquipos.setTextUbicacionRemito(Integer.toString(reparacion.getCodigo()));
		ventanaVisualizarEquipos.setTextNumeroRemito(Integer.toString(reparacion.getNumeroRemitoSalida()));
		// ventanaVisualizarEquipos.setChckbxAvisoEnviado((boolean)reparacion.getEnviado());

		llenarTablaRepuestos();
		ventanaVisualizarEquipos.getTextNombreEquipo().moveCaretPosition(0);

		ventanaVisualizarEquipos.setTextPresupuesto(reparacion.getPrecioPeso().toString());
		ventanaVisualizarEquipos.setTextPago(reparacion.getPago().toString());

		ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(reparacion.getPresupuestoGenerado());
		ventanaVisualizarEquipos.setChckbxPresupuestoEnviado(reparacion.getPresupuestoEnviado());
		ventanaVisualizarEquipos.setChckbxAvisoEnviado(reparacion.getAvisoEnviado());

		verificarPresupuesto();
		//deshabilitarCampos();

	}

	private void verificarPresupuesto() {

		Color EquipoPagado = new Color(130, 224, 170);
		Color AzulClaro = new Color(169, 204, 227);
		Color CyanClaro = new Color(224, 255, 255);
		Color FaltaPago = new Color(241, 148, 138);

		double PresupuestoDefault = 0;
		if ((reparacion.getPrecioPeso().compareTo(PresupuestoDefault) != 0)) {

			if (reparacion.getPrecioPeso().compareTo(reparacion.getPago()) == 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPago().setBackground(EquipoPagado);

			} else if (reparacion.getPrecioPeso().compareTo(reparacion.getPago()) > 0
					&& reparacion.getPago().compareTo(PresupuestoDefault) != 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO PARCIALMENTE");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPago().setBackground(CyanClaro);

			} else if (reparacion.getPago().compareTo(PresupuestoDefault) == 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("FALTA PAGO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPago().setBackground(FaltaPago);

			}

		} else {
			ventanaVisualizarEquipos.getTextEquipoPagado().setText("SIN PRESUPUESTAR");
			ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
			ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPago().setBackground(AzulClaro);

		}
		// TODO Auto-generated method stub

	}

	public void verificarPresupuestoEditado() {

		Color EquipoPagado = new Color(130, 224, 170);
		Color AzulClaro = new Color(169, 204, 227);
		Color CyanClaro = new Color(224, 255, 255);
		Color FaltaPago = new Color(241, 148, 138);

		String PresupuestoDefault = "0.0";

		if ((ventanaVisualizarEquipos.getTextPresupuesto().getText().compareTo(PresupuestoDefault) != 0)) {

			if (ventanaVisualizarEquipos.getTextPresupuesto().getText()
					.compareTo(ventanaVisualizarEquipos.getTextPago().getText()) == 0
					|| ventanaVisualizarEquipos.getTextPresupuesto().getText()
							.compareTo(ventanaVisualizarEquipos.getTextPago().getText() + ".0") == 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.getTextPago().setBackground(EquipoPagado);
				ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(true);

			} else if (ventanaVisualizarEquipos.getTextPresupuesto().getText()
					.compareTo(ventanaVisualizarEquipos.getTextPago().getText()) != 0
					&& ventanaVisualizarEquipos.getTextPago().getText().compareTo(PresupuestoDefault) != 0
					&& ventanaVisualizarEquipos.getTextPago().getText().compareTo("0") != 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("PAGADO PARCIALMENTE");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(CyanClaro);
				ventanaVisualizarEquipos.getTextPago().setBackground(CyanClaro);

			} else if (ventanaVisualizarEquipos.getTextPago().getText().compareTo("0") == 0
					|| ventanaVisualizarEquipos.getTextPago().getText().compareTo("0.0") == 0) {

				ventanaVisualizarEquipos.getTextEquipoPagado().setText("FALTA PAGO");
				ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
				ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPresupuesto().setBackground(FaltaPago);
				ventanaVisualizarEquipos.getTextPago().setBackground(FaltaPago);
				ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(true);

			}

		} else {
			ventanaVisualizarEquipos.getTextEquipoPagado().setText("SIN PRESUPUESTAR");
			ventanaVisualizarEquipos.getTextEquipoPagado().setVisible(true);
			ventanaVisualizarEquipos.getPanel_MontoPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextEquipoPagado().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPresupuesto().setBackground(AzulClaro);
			ventanaVisualizarEquipos.getTextPago().setBackground(AzulClaro);
			ventanaVisualizarEquipos.setChckbxPresupuestoGenerado(false);

		}
		// TODO Auto-generated method stub

	}

	
	private void llenarTablaRepuestos() {
		this.ventanaVisualizarEquipos.getModelRepuestos().setRowCount(0); // Para
																			// vaciar
																			// tabla
		this.ventanaVisualizarEquipos.getModelRepuestos().setColumnCount(0);
		this.ventanaVisualizarEquipos.getModelRepuestos()
				.setColumnIdentifiers(this.ventanaVisualizarEquipos.getNombreColumnas());

		int ELS = Integer.parseInt(this.ventanaVisualizarEquipos.getTextELS());

		this.Repuestos_en_tabla = (List<RepuestosDTO>) modelo.dameRepuestoXels(ELS);

		for (int i = 0; i < this.Repuestos_en_tabla.size(); i++) {
			Object[] fila = { this.Repuestos_en_tabla.get(i).getRef(), this.Repuestos_en_tabla.get(i).getOriginal(),
					this.Repuestos_en_tabla.get(i).getReemplazo(), this.Repuestos_en_tabla.get(i).getNotas() };
			this.ventanaVisualizarEquipos.getModelRepuestos().addRow(fila);
		}
		this.ventanaVisualizarEquipos.show();

	}
	
	
	

	public void cerraVentanaVisualizarEquipo() {

		this.ventanaVisualizarEquipos.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(ventanaVisualizarEquipos,
						"¿Desea salir de la ventana 'VISUALIZAR EQUIPOS'?", "Aviso", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					ventanaVisualizarEquipos.dispose();
					ventanaVisualizarEquipos = null;

				}
			}

		});

	}

}
