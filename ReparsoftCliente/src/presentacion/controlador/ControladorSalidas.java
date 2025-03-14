package presentacion.controlador;

import java.awt.Cursor;
import java.awt.Font;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import modelo.Agenda;

import presentacion.reportes.ReporteRemitoSalida;

import presentacion.vista.VentanaEliminarRemito;
import presentacion.vista.VentanaRemitoGenerado;
import presentacion.vista.VentanaRemitos;
import presentacion.vista.VentanaSalidas;
import presentacion.vista.VentanaSeleccionarCliente;
import presentacion.vista.VentanaSeleccionarRemito;
import dto.ClienteDTO;

import dto.RemitoDTO;
import dto.ReparacionDTO;

public class ControladorSalidas implements ActionListener, MouseListener, ItemListener, KeyListener {
	private VentanaSalidas ventanaSalidas;
	private VentanaSeleccionarCliente ventanaSeleccionarCliente;
	private VentanaSeleccionarRemito ventanaSeleccionarRemito;
	private VentanaRemitos ventanaRemitos;
	private VentanaRemitoGenerado ventanaRemitoGenerado;
	private VentanaEliminarRemito ventanaEliminarRemito;
	private Agenda agenda;
	private ClienteDTO Cliente;
	@SuppressWarnings("unused")
	private RemitoDTO Ubicacion;
	@SuppressWarnings("unused")
	private int idCli;
	@SuppressWarnings("unused")
	private int idSuc;
	@SuppressWarnings("unused")
	private int idUbicacion;
	@SuppressWarnings("unused")
	private String clienteSeleccionado;
	@SuppressWarnings("unused")
	private String sucursalSeleccionada;
	private String ubicacionRemitoSeleccionado;
	private String numeroRemitoSeleccionado;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	private ReparacionDTO reparacion;

	@SuppressWarnings("unused")
	private int clickMax = 1;
	@SuppressWarnings("unused")
	private int clickMin = 1;
	@SuppressWarnings("unused")
	private int max = Frame.MAXIMIZED_BOTH;
	@SuppressWarnings("unused")
	private int min = Frame.NORMAL;
	private String part1;
	@SuppressWarnings("unused")
	private String part2;
	String numeros = "";
	boolean guardado = false;

	private boolean btnMarcarEnviados = false;
	private boolean btnDesvincularRemito = false;

	public ControladorSalidas(VentanaSalidas ventanaSalidas, Agenda agenda) {

		this.ventanaSalidas = ventanaSalidas;

		this.ventanaSalidas.getBtnDesvincularRemito().addActionListener(this);
		this.ventanaSalidas.getBtnGenerarRemito().addActionListener(this);
		this.ventanaSalidas.getBtnMarcarEnviados().addActionListener(this);

		this.agenda = agenda;
	}

	public void cerraVentanaRemitoGenerado() {

		this.ventanaRemitoGenerado.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(ventanaRemitoGenerado, "¿Desea salir de la ventana?",
						"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					ventanaRemitoGenerado.dispose();
					ventanaRemitoGenerado = null;

				}
			}

		});

	}

	public void cerraVentanaEliminarRemito() {

		this.ventanaEliminarRemito.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(ventanaEliminarRemito, "¿Desea salir de la ventana?",
						"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					ventanaEliminarRemito.dispose();
					ventanaEliminarRemito = null;

				}
			}

		});

	}

	public void actionPerformed(ActionEvent e) {

		if (ventanaSalidas != null && e.getSource() == this.ventanaSalidas.getBtnGenerarRemito()) {

			ventanaSeleccionarCliente = new VentanaSeleccionarCliente(this);

			ventanaSeleccionarCliente.getBtnAceptar().addActionListener(this);
			ventanaSeleccionarCliente.getBtnCancelar().addActionListener(this);

			agenda.ListarCliente(ventanaSeleccionarCliente.getComboCliente());

			ventanaSeleccionarCliente.getComboCliente().addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					if (ventanaSeleccionarCliente.getComboCliente().getSelectedItem() != null) {
						Cliente = (ClienteDTO) ventanaSeleccionarCliente.getComboCliente().getSelectedItem();
						int id = Cliente.getId();

						agenda.ListarSucursalesxCliente(ventanaSeleccionarCliente.getComboSucursal(), id);
						idCli = id;

					}

				}
			});
		}

		if (ventanaSalidas != null && e.getSource() == this.ventanaSalidas.getBtnMarcarEnviados()) {

			btnMarcarEnviados = true;
			btnDesvincularRemito = false;

			ventanaSeleccionarRemito = new VentanaSeleccionarRemito(this);

			ventanaSeleccionarRemito.getBtnAceptar().addActionListener(this);
			ventanaSeleccionarRemito.getBtnCancelar().addActionListener(this);

			agenda.ListarUbicacion(ventanaSeleccionarRemito.getComboUbicacion());

			if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 0) {

				ventanaSeleccionarRemito.getComboUbicacion().setFont(new Font("Cambria", Font.PLAIN, 12));

			}

			ventanaSeleccionarRemito.getComboUbicacion().addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 0) {

						ventanaSeleccionarRemito.getComboUbicacion().setFont(new Font("Cambria", Font.PLAIN, 12));

					} else
						ventanaSeleccionarRemito.getComboUbicacion().setFont(new Font("Cambria", Font.PLAIN, 14));

					if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem() != null) {

						int ubicacion;
						int IDubicacion = 0;

						ubicacion = ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex();

						switch (ubicacion) {

						case 1:
							IDubicacion = 2;
							break;
						case 2:
							IDubicacion = 1;
							break;
						case 3:
							IDubicacion = 7;
							break;
						case 4:
							IDubicacion = 8;
							
						case 5:
							IDubicacion = 3;
							break;
						case 6:
							IDubicacion = 4;
							break;
						case 7:
							IDubicacion = 5;
							break;
						default:
							break;
						}

						agenda.ListarRemitoPorUbicacion(ventanaSeleccionarRemito.getComboNumRemito(), IDubicacion);

					}

				}
			});

		}

		if (ventanaSeleccionarRemito != null && e.getSource() == this.ventanaSeleccionarRemito.getBtnAceptar()
				&& btnMarcarEnviados) {

			if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem() != null
					&& ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem() != ventanaSeleccionarRemito
							.getComboUbicacion().getItemAt(0)) {

				if (ventanaSeleccionarRemito.getComboNumRemito().getSelectedItem() == null) {

					JOptionPane.showMessageDialog(null, "No hay remitos generados");

				} else {

					ubicacionRemitoSeleccionado = String.format("%04d", CodigoDeUbicacion(
							ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem().toString()));
					numeroRemitoSeleccionado = String.format("%08d", Integer
							.parseInt(ventanaSeleccionarRemito.getComboNumRemito().getSelectedItem().toString()));

					ventanaRemitoGenerado = new VentanaRemitoGenerado(this);
					ventanaRemitoGenerado.getBtnMarcarTodos().addActionListener(this);
					ventanaRemitoGenerado.getBtnGuardar().addActionListener(this);
					cerraVentanaRemitoGenerado();

					ventanaRemitoGenerado.getTextNumeroRemito()
							.setText(ubicacionRemitoSeleccionado + "-" + numeroRemitoSeleccionado);

					int IDubicacion = IDdeUbicacionRemiGenerado();
					int numero = Integer
							.parseInt(ventanaSeleccionarRemito.getComboNumRemito().getSelectedItem().toString());

					int IDremito = agenda.idRemitoXubicacionNumero(IDubicacion, numero);

					cargarTablaReparacionesEnRemito(IDremito);

					ventanaSalidas.dispose();
					ventanaSalidas = null;

					ventanaSeleccionarRemito.dispose();
					ventanaSeleccionarRemito = null;

				}
			} else {

				JOptionPane.showMessageDialog(null, "Debe seleccionar una ubicación");
			}

		} else if (ventanaSeleccionarRemito != null
				&& e.getSource() == this.ventanaSeleccionarRemito.getBtnCancelar()) {
			ventanaSeleccionarRemito.dispose();
			ventanaSeleccionarRemito = null;
		}

		if (ventanaSeleccionarRemito != null && e.getSource() == this.ventanaSeleccionarRemito.getBtnAceptar()
				&& btnDesvincularRemito) {

			if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem() != null
					&& ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem() != ventanaSeleccionarRemito
							.getComboUbicacion().getItemAt(0)) {

				if (ventanaSeleccionarRemito.getComboNumRemito().getSelectedItem() == null) {

					JOptionPane.showMessageDialog(null, "No hay remitos generados");

				} else {

					ubicacionRemitoSeleccionado = String.format("%04d", CodigoDeUbicacion(
							ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem().toString()));
					numeroRemitoSeleccionado = String.format("%08d", Integer
							.parseInt(ventanaSeleccionarRemito.getComboNumRemito().getSelectedItem().toString()));

					ventanaEliminarRemito = new VentanaEliminarRemito(this);
					ventanaEliminarRemito.getBtnAnular().addActionListener(this);
					ventanaEliminarRemito.getBtnEliminar().addActionListener(this);
					cerraVentanaEliminarRemito();

					ventanaEliminarRemito.getTextNumeroRemito()
							.setText(ubicacionRemitoSeleccionado + "-" + numeroRemitoSeleccionado);

					int IDubicacion = IDdeUbicacionRemiGenerado();
					int numero = Integer
							.parseInt(ventanaSeleccionarRemito.getComboNumRemito().getSelectedItem().toString());

					int IDremito = agenda.idRemitoXubicacionNumero(IDubicacion, numero);

					cargarTablaReparacionesEnRemito(IDremito);

					ventanaSalidas.dispose();
					ventanaSalidas = null;

					ventanaSeleccionarRemito.dispose();
					ventanaSeleccionarRemito = null;

				}
			} else {

				JOptionPane.showMessageDialog(null, "Debe seleccionar una ubicación");
			}

		}

		if (ventanaEliminarRemito != null && e.getSource() == this.ventanaEliminarRemito.getBtnAnular()) {

			JOptionPane.showMessageDialog(null,
					"Los equipos asociados serán removidos del remito, \npero el mismo seguirá existiendo. \nEl número no podrá ser utilizado nuevamente.");
			int opcion = JOptionPane.showConfirmDialog(ventanaEliminarRemito, "¿Desea ANULAR el remito?", "Aviso",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {

				int filas = this.ventanaEliminarRemito.getModelEquiposParaRemito().getRowCount();

				for (int i = 0; i < filas; i++) {

					ReparacionDTO reparacionAeditar = quitarRemito(i);
					this.agenda.editarReparacionR(reparacionAeditar);

				}

				this.ventanaEliminarRemito.getTblEquiposParaRemito().setVisible(false);
				this.ventanaEliminarRemito.getBtnAnular().setEnabled(false);
				this.ventanaEliminarRemito.getTxtCliente().setVisible(false);
				JOptionPane.showMessageDialog(null, "Se guardaron los cambios");

			}
		}

		if (ventanaEliminarRemito != null && e.getSource() == this.ventanaEliminarRemito.getBtnEliminar()) {

			JOptionPane.showMessageDialog(null, "El remito será ELIMINADO. \nEl número podrá ser utlizado nuevamente.");
			int opcion = JOptionPane.showConfirmDialog(ventanaEliminarRemito, "¿Desea ELIMINAR el remito?", "Aviso",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {

				int filas = this.ventanaEliminarRemito.getModelEquiposParaRemito().getRowCount();

				for (int i = 0; i < filas; i++) {

					ReparacionDTO reparacionAeditar = quitarRemito(i);
					this.agenda.editarReparacionR(reparacionAeditar);

				}

				int idRemito = agenda.idRemitoXubicacionNumero(IDUbicacion(),
						Integer.parseInt(numeroRemitoSeleccionado));

				agenda.eliminarRemito(idRemito);

				this.ventanaEliminarRemito.getTblEquiposParaRemito().setVisible(false);
				this.ventanaEliminarRemito.getBtnEliminar().setEnabled(false);
				this.ventanaEliminarRemito.getTextNumeroRemito().setVisible(false);
				this.ventanaEliminarRemito.getTxtCliente().setVisible(false);
				JOptionPane.showMessageDialog(null, "Se guardaron los cambios");

			}

		}

		if (ventanaRemitoGenerado != null && e.getSource() == this.ventanaRemitoGenerado.getBtnMarcarTodos()) {

			int filas = this.ventanaRemitoGenerado.getModelEquiposParaRemito().getRowCount();

			for (int i = 0; i < filas; i++) {

				this.ventanaRemitoGenerado.getModelEquiposParaRemito().setValueAt(true, i, 4);
			}

		}

		if (ventanaRemitoGenerado != null && e.getSource() == this.ventanaRemitoGenerado.getBtnGuardar()) {

			Boolean enviado = false;
			int filas = this.ventanaRemitoGenerado.getModelEquiposParaRemito().getRowCount();

			for (int i = 0; i < filas; i++) {

				enviado = (Boolean) this.ventanaRemitoGenerado.getModelEquiposParaRemito().getValueAt(i, 4);
				if (enviado != null) {

					ReparacionDTO reparacionAeditar = marcarEnviado(i);
					this.agenda.editarReparacionR(reparacionAeditar);

				}
			}

			JOptionPane.showMessageDialog(null, "Se guardaron los cambios");

		}

		if (ventanaSalidas != null && e.getSource() == this.ventanaSalidas.getBtnDesvincularRemito()) {

			btnMarcarEnviados = false;
			btnDesvincularRemito = true;

			ventanaSeleccionarRemito = new VentanaSeleccionarRemito(this);

			ventanaSeleccionarRemito.getBtnAceptar().addActionListener(this);
			ventanaSeleccionarRemito.getBtnCancelar().addActionListener(this);

			agenda.ListarUbicacion(ventanaSeleccionarRemito.getComboUbicacion());

			if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 0) {

				ventanaSeleccionarRemito.getComboUbicacion().setFont(new Font("Cambria", Font.PLAIN, 12));

			}

			ventanaSeleccionarRemito.getComboUbicacion().addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 0) {

						ventanaSeleccionarRemito.getComboUbicacion().setFont(new Font("Cambria", Font.PLAIN, 12));

					} else
						ventanaSeleccionarRemito.getComboUbicacion().setFont(new Font("Cambria", Font.PLAIN, 14));

					if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedItem() != null) {

						int ubicacion;
						int IDubicacion = 0;

						ubicacion = ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex();

						switch (ubicacion) {
						
						

						case 1:
							IDubicacion = 2;
							break;
						case 2:
							IDubicacion = 1;
							break;
						case 3:
							IDubicacion = 7;
							break;
						case 4:
							IDubicacion = 8;
							
						case 5:
							IDubicacion = 3;
							break;
						case 6:
							IDubicacion = 4;
							break;
						case 7:
							IDubicacion = 5;
							break;
						default:
							break;

						}

						agenda.ListarRemitoPorUbicacion(ventanaSeleccionarRemito.getComboNumRemito(), IDubicacion);

					}

				}
			});

		}

		if (ventanaSeleccionarCliente != null && e.getSource() == this.ventanaSeleccionarCliente.getBtnAceptar()) {

			if (ventanaSeleccionarCliente.getComboCliente().getSelectedItem() != null && ventanaSeleccionarCliente
					.getComboCliente().getSelectedItem() != ventanaSeleccionarCliente.getComboCliente().getItemAt(0)) {

				clienteSeleccionado = ventanaSeleccionarCliente.getComboCliente().getSelectedItem().toString();
				sucursalSeleccionada = ventanaSeleccionarCliente.getComboSucursal().getSelectedItem().toString();

				ventanaRemitos = new VentanaRemitos(this);

				agregarListenersVentanaRemitos();

				llenarComboUbicacion();

				String Cliente = ventanaSeleccionarCliente.getComboCliente().getSelectedItem().toString();
				String Sucursal = ventanaSeleccionarCliente.getComboSucursal().getSelectedItem().toString();

				int idCliente = agenda.idClienteporNombre(Cliente);
				int idSucursal = agenda.idSucursalporNombre(Sucursal, idCliente);

				cargarTablaEquiposParaRemito(idCliente, idSucursal);

				ventanaRemitos.getTxtCliente().setText(Cliente + " " + "(" + Sucursal + ")");

				ventanaSalidas.dispose();
				ventanaSalidas = null;

				ventanaSeleccionarCliente.dispose();
				ventanaSeleccionarCliente = null;

			} else {

				JOptionPane.showMessageDialog(null, "Debe seleccionar un Cliente");
			}

		} else if (ventanaSeleccionarCliente != null
				&& e.getSource() == this.ventanaSeleccionarCliente.getBtnCancelar()) {
			ventanaSeleccionarCliente.dispose();
			ventanaSeleccionarCliente = null;
		}

		else if (ventanaRemitos != null && e.getSource() == this.ventanaRemitos.getTxtNumeroRemito()) {

			numeros = this.ventanaRemitos.getTxtNumeroRemito().getText();
			ventanaRemitos.getTextRemitoConformado().setText(part1 + " - " + numeros);

		}

		else if (ventanaRemitos != null && e.getSource() == this.ventanaRemitos.getBtnVisualizarRemito()) {

			ventanaRemitos.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			int filas = this.ventanaRemitos.getModelEquiposParaRemito().getRowCount();
			int cont = 0;
			for (int i = 0; i < filas; i++) {

				if ((Boolean) this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 8) == true)
					cont++;

			}

			if (ventanaRemitos.getTextCantBultos().getText().isEmpty())
				JOptionPane.showMessageDialog(null, "Debe ingresar la 'CANTIDAD DE BULTOS'");
			else if (cont == 0)
				JOptionPane.showMessageDialog(null, "Debe agregar al menos un equipo al remito");

			else {
				List<RemitoDTO> lista = new ArrayList<RemitoDTO>();
				RemitoDTO rep = TomarDatos();
				lista.add(rep);
				ReporteRemitoSalida reporte = new ReporteRemitoSalida(rep, lista, agenda);
				reporte.mostrar();

			}

			ventanaRemitos.setCursor(Cursor.getDefaultCursor());
		}

		else if (ventanaRemitos != null && e.getSource() == this.ventanaRemitos.getBtnGuardarRemito()) {
			// System.out.println("entro");

			int filas = this.ventanaRemitos.getModelEquiposParaRemito().getRowCount();
			int cont = 0;
			for (int i = 0; i < filas; i++) {

				if ((Boolean) this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 8) == true)
					cont++;

			}

			if (ventanaRemitos.getTextCantBultos().getText().isEmpty())
				JOptionPane.showMessageDialog(null, "Debe ingresar la 'CANTIDAD DE BULTOS'");
			else if (cont == 0)
				JOptionPane.showMessageDialog(null, "Debe agregar al menos un equipo al remito");

			else {

				generarRemito(ventanaRemitos, filas);

			}

		}

		else if (ventanaRemitos != null && e.getSource() == this.ventanaRemitos.getBtnCambiarN()) {

			this.ventanaRemitos.getTxtNumeroRemito().setEditable(true);

		}
	}

	private void generarRemito(VentanaRemitos ventanaRemitos, int filas) {

		int seleccion = JOptionPane.showConfirmDialog(ventanaRemitos, "Desea generar un remito para este/os equipos?",
				"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (seleccion == JOptionPane.YES_OPTION) {

			JDialog popup = new JDialog();
			popup.setTitle("Procesando");
			popup.setModal(false);
			popup.setSize(300, 100);
			popup.setLocationRelativeTo(ventanaRemitos);
			popup.add(new JLabel("Generando Remito, espere...", SwingConstants.CENTER));

			// Ejecutar el envío del correo en un hilo separado para no bloquear el UI
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() {
					try {

						List<RemitoDTO> lista = new ArrayList<RemitoDTO>();
						RemitoDTO nuevoRemito = TomarDatos();
						lista.add(nuevoRemito);
						ReporteRemitoSalida reporte = new ReporteRemitoSalida(nuevoRemito, lista, agenda);
						reporte.mostrar();
						reporte.guardar();

						RemitoDTO nuevoRemitoTabla = TomarDatosParaTabla();
						agenda.agregarRemito(nuevoRemitoTabla);

						for (int i = 0; i < filas; i++) {

							Boolean agregar = (Boolean) ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 8);

							if (agregar != null) {
								if (agregar) {

									ReparacionDTO reparacionAeditar = TomarDatosPantalla(i);
									agenda.editarReparacionR(reparacionAeditar);

								}
							}

						}

						JOptionPane.showMessageDialog(null,
								"Se ha guardodo el remito " + ventanaRemitos.getTextRemitoConformado().getText());

						ventanaRemitos.getComboUbicacion().setEnabled(false);
						ventanaRemitos.getTxtNumeroRemito().setEnabled(false);
						ventanaRemitos.getBtnVisualizarRemito().setEnabled(false);
						ventanaRemitos.getBtnGuardarRemito().setEnabled(false);
						ventanaRemitos.getTextCantBultos().setEnabled(false);
						ventanaRemitos.getTblEquiposParaRemito().setEnabled(false);
						ventanaRemitos.getBtnCambiarN().setEnabled(false);

					} catch (Exception ex) {
						popup.dispose();
						ex.printStackTrace();
						// JOptionPane.showMessageDialog(null, "El correo NO ha sido enviado.", "Error
						// de envío", JOptionPane.WARNING_MESSAGE);
					}
					return null;
				}

				@Override
				protected void done() {
					// Cerrar el popup después de completar el envío
					popup.dispose();

				}
			};

			// Mostrar el popup y ejecutar el SwingWorker
			SwingUtilities.invokeLater(() -> {
				popup.setVisible(true);
				worker.execute();
			});

		}

	}

	public void agregarListenersVentanaRemitos() {

		this.ventanaRemitos.getComboUbicacion().addActionListener(this);
		this.ventanaRemitos.getComboUbicacion().addMouseListener(this);
		this.ventanaRemitos.getComboUbicacion().addItemListener(this);
		this.ventanaRemitos.getTxtNumeroRemito().addActionListener(this);
		this.ventanaRemitos.getBtnVisualizarRemito().addActionListener(this);
		this.ventanaRemitos.getBtnGuardarRemito().addActionListener(this);
		this.ventanaRemitos.getBtnCambiarN().addActionListener(this);
		this.ventanaRemitos.getTxtNumeroRemito().addKeyListener(this);

	}

	private int IDdeUbicacionRemiGenerado() {

		int ID = 0;

		if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 0)
			ID = 6;
		else if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 1)
			ID = 2;
		else if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 2)
			ID = 1;
		else if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 3)
			ID = 7;
		else if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 4)
			ID = 8;
		else if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 5)
			ID = 3;
		else if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 6)
			ID = 4;
		else if (ventanaSeleccionarRemito.getComboUbicacion().getSelectedIndex() == 7)
			ID = 5;

		return ID;

	}

	@SuppressWarnings("deprecation")
	private void cargarTablaReparacionesEnRemito(int IDremito) {

		if (btnMarcarEnviados && !btnDesvincularRemito) {
			this.ventanaRemitoGenerado.getModelEquiposParaRemito().setRowCount(0); // Para
			// vaciar
			// tabla
			this.ventanaRemitoGenerado.getModelEquiposParaRemito().setColumnCount(0);
			this.ventanaRemitoGenerado.getModelEquiposParaRemito()
					.setColumnIdentifiers(this.ventanaRemitoGenerado.getNombreColumnas());

			this.Reparaciones_en_tabla = (List<ReparacionDTO>) agenda.obtenerReparacionesXremito(IDremito);

			if (this.Reparaciones_en_tabla.isEmpty()) {

				JOptionPane.showMessageDialog(null, "Este remito fué anulado o está vacío");

			}

			else {

				String cliente = this.Reparaciones_en_tabla.get(0).getCliente();
				ventanaRemitoGenerado.getTxtCliente().setText(cliente);

				for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {

					Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(),

							this.Reparaciones_en_tabla.get(i).getNombreEquipo(),
							this.Reparaciones_en_tabla.get(i).getModelo(),
							this.Reparaciones_en_tabla.get(i).getNumeroDeSerie(),
							this.Reparaciones_en_tabla.get(i).getEnviado() };
					this.ventanaRemitoGenerado.getModelEquiposParaRemito().addRow(fila);
				}
				this.ventanaRemitoGenerado.show();
			}
		} else if (!btnMarcarEnviados && btnDesvincularRemito) {

			this.ventanaEliminarRemito.getModelEquiposParaRemito().setRowCount(0); // Para
			// vaciar
			// tabla
			this.ventanaEliminarRemito.getModelEquiposParaRemito().setColumnCount(0);
			this.ventanaEliminarRemito.getModelEquiposParaRemito()
					.setColumnIdentifiers(this.ventanaEliminarRemito.getNombreColumnas());

			this.Reparaciones_en_tabla = (List<ReparacionDTO>) agenda.obtenerReparacionesXremito(IDremito);

			if (this.Reparaciones_en_tabla.isEmpty()) {

				JOptionPane.showMessageDialog(null, "Este remito fué anulado o está vacío");

			}

			else {

				String cliente = this.Reparaciones_en_tabla.get(0).getCliente();
				ventanaEliminarRemito.getTxtCliente().setText(cliente);

				for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {

					Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(),

							this.Reparaciones_en_tabla.get(i).getNombreEquipo(),
							this.Reparaciones_en_tabla.get(i).getModelo(),
							this.Reparaciones_en_tabla.get(i).getNumeroDeSerie(),
							this.Reparaciones_en_tabla.get(i).getEnviado() };
					this.ventanaEliminarRemito.getModelEquiposParaRemito().addRow(fila);
				}
				this.ventanaEliminarRemito.show();

			}
		}
	}

	private ReparacionDTO TomarDatosPantalla(int i) {

		int ELS = Integer.parseInt(this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 0).toString());
		boolean agregadoAremito = (Boolean) this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 8);
		int idRemito = this.agenda.dameIDRemito();

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, agregadoAremito, idRemito);

		return reparacionAeditar;

	}

	private ReparacionDTO marcarEnviado(int i) {

		int ELS = Integer.parseInt(this.ventanaRemitoGenerado.getModelEquiposParaRemito().getValueAt(i, 0).toString());
		boolean enviado;
		enviado = (Boolean) this.ventanaRemitoGenerado.getModelEquiposParaRemito().getValueAt(i, 4);
		boolean agregadoAremito = true;
		String estadoFisico = "Enviado";

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, estadoFisico, enviado, agregadoAremito);

		return reparacionAeditar;

	}

	private ReparacionDTO quitarRemito(int i) {

		int ELS = Integer.parseInt(this.ventanaEliminarRemito.getModelEquiposParaRemito().getValueAt(i, 0).toString());
		boolean agregado = true;
		boolean generado = true;
		Integer idRemito = 0;
		boolean enviado = false;
		String estadoFisico = "BRC";

		ReparacionDTO reparacionAeditar = new ReparacionDTO(ELS, estadoFisico, generado, agregado, idRemito, enviado);
		return reparacionAeditar;
	}

	private RemitoDTO TomarDatos() {

		Integer IdUbicacion = IDdeUbicacion();
		Integer codigoUbicacion = CodigoDeUbicacion(ventanaRemitos.getTextRemitoConformado().getText());
		Integer IdRemito = this.agenda.dameIDRemito() + 1;
		Integer numeroRemitoSalida = Integer.parseInt((String) ventanaRemitos.getTxtNumeroRemito().getValue());

		List<String> descripcion = new ArrayList<String>();
		Boolean agregar = false;

		int filas = this.ventanaRemitos.getModelEquiposParaRemito().getRowCount();

		for (int i = 0; i < filas; i++) {
			String ELS = this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 0).toString();
			String equipo = this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 1).toString();
			String marca = this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 2).toString();
			String modelo = this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 3).toString();
			String serie = this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 4).toString();
			agregar = (Boolean) this.ventanaRemitos.getModelEquiposParaRemito().getValueAt(i, 8);

			if (agregar != null) {
				if (agregar) {
					descripcion.add("ELS: " + ELS + " - EQUIPO: " + equipo + " - MARCA: " + marca + " - MODELO: "
							+ modelo + " - N° SERIE: " + serie + "\n\n");

				}
			}

		}
	
		
		//String NombreCliente = this.ventanaRemitos.getTxtCliente().getText();
		
		String NombreCliente = "Hugo Rega";
		System.out.println(NombreCliente);
		
		
		
		
		String RemitoConformado = this.ventanaRemitos.getTextRemitoConformado().getText();
		int cantBultos = Integer.parseInt(this.ventanaRemitos.getTextCantBultos().getText());
		
		List<ClienteDTO> clientes =agenda.obtenerCliente();
		
		 Optional<ClienteDTO> clienteEncontrado = clientes.stream()
		            .filter(cliente -> cliente.getRazon_Social().equalsIgnoreCase(NombreCliente))
		            .findFirst();
		 

		

		RemitoDTO nuevoRemito = new RemitoDTO(IdUbicacion, codigoUbicacion, IdRemito, numeroRemitoSalida, descripcion,
				NombreCliente, RemitoConformado, cantBultos, clienteEncontrado.get().getCUIT(),clienteEncontrado.get().getDomicilio());

		return nuevoRemito;
	}

	@SuppressWarnings("unused")
	private RemitoDTO TomarDatosParaTabla() {

		Integer IdUbicacion = IDdeUbicacion();
		Integer codigoUbicacion = CodigoDeUbicacion(ventanaRemitos.getTextRemitoConformado().getText());
		Integer IdRemito = this.agenda.dameIDRemito() + 1;
		Integer numeroRemitoSalida = Integer.parseInt((String) ventanaRemitos.getTxtNumeroRemito().getValue());

		RemitoDTO nuevoRemito = new RemitoDTO(IdUbicacion, numeroRemitoSalida, IdRemito);

		return nuevoRemito;
	}

	@SuppressWarnings("deprecation")
	private void cargarTablaEquiposParaRemito(int idCliente, int idSucursal) {

		this.ventanaRemitos.getModelEquiposParaRemito().setRowCount(0); // Para
		// vaciar
		// tabla
		this.ventanaRemitos.getModelEquiposParaRemito().setColumnCount(0);
		this.ventanaRemitos.getModelEquiposParaRemito().setColumnIdentifiers(this.ventanaRemitos.getNombreColumnas());

		this.Reparaciones_en_tabla = (List<ReparacionDTO>) agenda.obtenerReparacionXIDclienteIDsucursal(idCliente,
				idSucursal);

		for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {

			Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(),
					this.Reparaciones_en_tabla.get(i).getNombreEquipo(), this.Reparaciones_en_tabla.get(i).getMarca(),
					this.Reparaciones_en_tabla.get(i).getModelo(), this.Reparaciones_en_tabla.get(i).getNumeroDeSerie(),
					this.Reparaciones_en_tabla.get(i).getAviso(), this.Reparaciones_en_tabla.get(i).getEstadoTecnico(),
					this.Reparaciones_en_tabla.get(i).getEstadoComercial(),
					this.Reparaciones_en_tabla.get(i).getAgregadoaremito()

			};
			this.ventanaRemitos.getModelEquiposParaRemito().addRow(fila);
		}

		ventanaRemitos.setCellRender(this.ventanaRemitos.getTblEquiposParaRemito());

		this.ventanaRemitos.show();

	}

	@SuppressWarnings("deprecation")
	public void cargarRemitoVisualizacion(int els) {

		ventanaRemitos = new VentanaRemitos(this);

		this.ventanaRemitos.getModelEquiposParaRemito().setRowCount(0); // Para
		// vaciar
		// tabla
		this.ventanaRemitos.getModelEquiposParaRemito().setColumnCount(0);
		this.ventanaRemitos.getModelEquiposParaRemito().setColumnIdentifiers(this.ventanaRemitos.getNombreColumnas());

		this.reparacion = agenda.dameReparacionXels(els);

		Object[] fila = { this.reparacion.getELS(), this.reparacion.getNombreEquipo(), this.reparacion.getMarca(),
				this.reparacion.getModelo(), this.reparacion.getNumeroDeSerie(), this.reparacion.getAviso(),
				this.reparacion.getEstadoTecnico(), this.reparacion.getEstadoComercial(),
				this.reparacion.getAgregadoaremito() };
		this.ventanaRemitos.getModelEquiposParaRemito().addRow(fila);

		ventanaRemitos.setCellRender(this.ventanaRemitos.getTblEquiposParaRemito());

		String Cliente = this.reparacion.getCliente();
		String Sucursal = this.reparacion.getSucursal();

		ventanaRemitos.getTxtCliente().setText(Cliente + " " + "(" + Sucursal + ")");

		this.ventanaRemitos.show();

		llenarComboUbicacion();

	}

	public void llenarComboUbicacion() {

		agenda.ListarUbicacion(ventanaRemitos.getComboUbicacion());

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

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

	@SuppressWarnings("unused")
	private int NumeroRemito(String ubicacion) {

		int numero;

		int codigo;

		String[] parts = ubicacion.split(" - ");
		part1 = parts[0]; // 123
		part2 = parts[1]; // 654321
		//
		// System.out.println(part1);
		// System.out.println(part2);

		codigo = Integer.parseInt(part1);

		numero = agenda.obtenerNumeroRemito(codigo) + 1;

		return numero;

	}

	@SuppressWarnings("unused")
	private int CodigoDeUbicacion(String ubicacion) {

		int numero;

		int codigo;

		String[] parts = ubicacion.split(" - ");
		part1 = parts[0]; // 123
		part2 = parts[1]; // 654321
		//
		// System.out.println(part1);
		// System.out.println(part2);

		codigo = Integer.parseInt(part1);

		numero = agenda.obtenerNumeroRemito(codigo) + 1;

		return codigo;

	}

	private int IDdeUbicacion() {

		int ID = 0;

		if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 0)
			ID = 6;
		else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 1)
			ID = 2;
		else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 2)
			ID = 1;
		else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 3)
			ID = 7;
		else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 4)
			ID = 8;
		else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 5)
			ID = 3;
		else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 6)
			ID = 4;
		else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 7)
			ID = 5;

		return ID;
	}

	private int IDUbicacion() {

		int ID = 0;

		int ubicacion = Integer.parseInt(ubicacionRemitoSeleccionado);

		if (ubicacion == 5)
			ID = 1;
		else if (ubicacion == 2)
			ID = 2;
		else if (ubicacion == 1000)
			ID = 3;
		else if (ubicacion == 2000)
			ID = 4;
		else if (ubicacion == 3000)
			ID = 5;
		else if (ubicacion == 6)
			ID = 7;
		else if (ubicacion == 7)
			ID = 8;
		else
			ID = 6;

		return ID;
	}

	private String tomarNumeroRemito(String ubicacion) {
		int numero;
		int numero2024;

		int codigo;

		String[] parts = ubicacion.split(" - ");
		part1 = parts[0]; // 123
		part2 = parts[1]; // 654321
		//
		// System.out.println(part1);
		// System.out.println(part2);

		codigo = Integer.parseInt(part1);

		numero = agenda.obtenerNumeroRemito(codigo) + 1;

//		if (codigo == 3000) {
//				numero2024 = numero + 668;				
//		}
//		
//		else 
		numero2024 = numero;

		if (numero2024 < 10) {
			numeros = "0000000" + numero2024;

		} else if (numero2024 >= 10 && numero2024 < 100) {
			numeros = "000000" + numero2024;

		} else if (numero2024 >= 100 && numero2024 < 1000) {
			numeros = "00000" + numero2024;

		} else if (numero2024 >= 1000 && numero2024 < 10000) {
			numeros = "0000" + numero2024;

		}

		ventanaRemitos.getTextRemitoConformado().setText(part1 + " - " + numeros);

		return numeros;

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (ventanaRemitos != null && e.getSource() == ventanaRemitos.getComboUbicacion()
				&& ventanaRemitos.getComboUbicacion().getSelectedItem() != null) {

			if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 0) {

				ventanaRemitos.getTextCantBultos().setText("");
				ventanaRemitos.getTxtNumeroRemito().setText("");
				ventanaRemitos.getTextTipoRemito().setText("");
				ventanaRemitos.getTextTipoRemito().setVisible(false);
				ventanaRemitos.getPanel_2().setVisible(false);

			}

			if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 1
					|| ventanaRemitos.getComboUbicacion().getSelectedIndex() == 2
					|| ventanaRemitos.getComboUbicacion().getSelectedIndex() == 3) {

				ventanaRemitos.getTextTipoRemito().setText("REMITO PREIMPRESO");
				ventanaRemitos.getTextTipoRemito().setVisible(true);
				ventanaRemitos.getPanel_2().setVisible(true);

			} else if (ventanaRemitos.getComboUbicacion().getSelectedIndex() == 4
					|| ventanaRemitos.getComboUbicacion().getSelectedIndex() == 5
					|| ventanaRemitos.getComboUbicacion().getSelectedIndex() == 6) {

				ventanaRemitos.getTextTipoRemito().setText("REMITO COMÚN");
				ventanaRemitos.getTextTipoRemito().setVisible(true);
				ventanaRemitos.getPanel_2().setVisible(true);

			}
			if (ventanaRemitos.getComboUbicacion().getSelectedIndex() != 0)
				ventanaRemitos.setTxtNumeroRemito(
						tomarNumeroRemito(ventanaRemitos.getComboUbicacion().getSelectedItem().toString()));
		}

		if (ventanaRemitos.getComboUbicacion().getSelectedItem() == null) {

			ventanaRemitos.getTextTipoRemito().setVisible(false);
			ventanaRemitos.getPanel_2().setVisible(false);
			ventanaRemitos.getTxtNumeroRemito().setText("");

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			ventanaRemitos.getTextRemitoConformado().setText(part1 + " - " + numeros);

		}

	}

}
