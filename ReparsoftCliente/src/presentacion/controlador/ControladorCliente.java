package presentacion.controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

import VistaPropias.TablaFiltros;
import modelo.Agenda;
import presentacion.vista.VentanaAgregarCliente;
import presentacion.vista.VentanaAgregarSucursal;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaSucursales;
import dto.ClienteDTO;
import dto.SucursalDTO;

public class ControladorCliente implements ActionListener, MouseListener {
	private VentanaClientes ventanaClientes;
	private VentanaAgregarCliente ventanaAgregarClientes;
	private VentanaAgregarCliente ventanaEditarCliente;
	private VentanaAgregarSucursal ventanaAgregarSucursales;
	private VentanaAgregarSucursal ventanaEditarSucursales;
	private VentanaSucursales ventanaSucursales;
	private List<ClienteDTO> Clientes_en_tabla;
	private List<SucursalDTO> Sucursales_en_tabla;
	private Agenda agenda;
	private ClienteDTO clienteElegido;
	private SucursalDTO SucursalesEncliente;
	private TablaFiltros tablaFiltros = new TablaFiltros();

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	public ControladorCliente(VentanaClientes ventanaClientes, Agenda agenda) {

		this.ventanaClientes = ventanaClientes;

		agregarListenersVentanaCliente();

		this.agenda = agenda;
		this.Clientes_en_tabla = null;
		this.clienteElegido = null;

		llenarTabla();
	}

	public void llenarTabla() {
		this.ventanaClientes.getModelClientes().setRowCount(0); // Para vaciar
																// la tabla
		this.ventanaClientes.getModelClientes().setColumnCount(0);
		this.ventanaClientes.getModelClientes().setColumnIdentifiers(this.ventanaClientes.getNombreColumnas());

		this.Clientes_en_tabla = agenda.obtenerCliente();

		for (int i = 0; i < this.Clientes_en_tabla.size(); i++) {
			Object[] fila = { this.Clientes_en_tabla.get(i).getRazon_Social(), this.Clientes_en_tabla.get(i).getCUIT(),
					this.Clientes_en_tabla.get(i).getDomicilio(), this.Clientes_en_tabla.get(i).getContacto(),
					this.Clientes_en_tabla.get(i).getTelefonoContacto(),
					this.Clientes_en_tabla.get(i).getCorreoElectronico() };
			this.ventanaClientes.getModelClientes().addRow(fila);
		}

		ventanaClientes.setCellRender(this.ventanaClientes.getTablaClientes());

		tablaFiltros.agregarAutofiltros(this.ventanaClientes.getTablaClientes());

		this.ventanaClientes.setVisible(true);
	}

	private void llenarTablaSucursales(int idCliente) {
		this.ventanaSucursales.getModelSucursales().setRowCount(0); // Para
																	// vaciar
		// la tabla
		this.ventanaSucursales.getModelSucursales().setColumnCount(0);
		this.ventanaSucursales.getModelSucursales().setColumnIdentifiers(this.ventanaSucursales.getNombreColumnas());

		this.Sucursales_en_tabla = agenda.obtenerSucursalesxCliente(idCliente);

		for (int i = 0; i < this.Sucursales_en_tabla.size(); i++) {
			Object[] fila = { this.Sucursales_en_tabla.get(i).getNombreSucursal() };
			this.ventanaSucursales.getModelSucursales().addRow(fila);
		}
		this.ventanaSucursales.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventanaClientes.getBtnAgregar()) {

			agregarListenersVentanaAgregarCliente();

		}

		else if (e.getSource() == this.ventanaClientes.getBtnEditar()) {

			int fila = this.ventanaClientes.getTablaClientes().getSelectedRow();
			if (fila != -1) {

				this.ventanaEditarCliente = new VentanaAgregarCliente(this);

				this.ventanaEditarCliente.getTxtNombre().setText(clienteElegido.getRazon_Social());
				this.ventanaEditarCliente.getTxtCUIT().setText(clienteElegido.getCUIT());
				this.ventanaEditarCliente.getTxtDireccion().setText(clienteElegido.getDomicilio());
				this.ventanaEditarCliente.getTxtContacto().setText(clienteElegido.getContacto());
				this.ventanaEditarCliente.getTxtTelefonoContacto().setText(clienteElegido.getTelefonoContacto());
				this.ventanaEditarCliente.getTxtTelefonoEmpresa().setText(clienteElegido.getTelefonoEmpresa());
				this.ventanaEditarCliente.getTxtEmail().setText(clienteElegido.getCorreoElectronico());

				this.ventanaEditarCliente.getBtnAgregarCliente().addActionListener(this);
				this.ventanaEditarCliente.getBtnCancelar().addActionListener(this);

				performActionOnTextComponents(ventanaEditarCliente);

			} else {
				JOptionPane.showMessageDialog(null, "No hay ningun Cliente seleccionado", "Error al modificar Cliente",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		else if (e.getSource() == this.ventanaClientes.getBtnBorrar()) {

			int fila = this.ventanaClientes.getTablaClientes().getSelectedRow();

			if (fila != -1) {

				if (!ReparacionAsociadaACliente(clienteElegido.getId())) {

					if (cantidadSucursalesXCliente(clienteElegido.getId()) == 1) {

						SucursalesEncliente = this.agenda.obtenerSucursalesxCliente(clienteElegido.getId()).get(0);

						if (SucursalesEncliente.getNombreSucursal().compareTo("") == 0) {

							int seleccion = JOptionPane.showConfirmDialog(ventanaClientes,
									"Está seguro Eliminar este Cliente?", "Confirmación", JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);

							if (seleccion == JOptionPane.YES_OPTION) {

								String respuesta = JOptionPane.showInputDialog(null,
										"Ingrese la Contraseña de Seguridad : ", "");
								if (respuesta != null) {

									if (respuesta.compareTo("0000") == 0) {

										this.agenda.borrarSucursal(SucursalesEncliente);
										this.agenda.borrarCliente(clienteElegido);
										this.llenarTabla();
									} else {

										JOptionPane.showMessageDialog(null, "Contraseña Incorrecta. ",
												"Error al Eliminar Cliente", JOptionPane.ERROR_MESSAGE);

									}
								}

							}

						}

						else if (SucursalesEncliente.getNombreSucursal().compareTo("") != 0) {

							JOptionPane.showMessageDialog(null,
									"Este Cliente Posee Sucursales. No es posible Eliminarlo ",
									"Error al Eliminar Cliente", JOptionPane.INFORMATION_MESSAGE);

						}
					} else if (cantidadSucursalesXCliente(clienteElegido.getId()) > 1) {

						JOptionPane.showMessageDialog(null, "Este Cliente Posee Sucursales. No es posible Eliminarlo ",
								"Error al Eliminar Cliente", JOptionPane.INFORMATION_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Este Cliente Posee reparaciones asociadas. No es posible Eliminarlo ",
							"Error al Eliminar Cliente", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "No hay ningun Cliente seleccionado", "Error al modificar Cliente",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		else if (e.getSource() == this.ventanaClientes.getBtnGenerarSucursales()) {

			int fila = this.ventanaClientes.getTablaClientes().getSelectedRow();
			if (fila != -1) {
				this.ventanaAgregarSucursales = new VentanaAgregarSucursal(this);

				this.ventanaAgregarSucursales.getTxtCliente().setText(clienteElegido.getRazon_Social());
				this.ventanaAgregarSucursales.getBtnAgregarSucursal().addActionListener(this);
				this.ventanaAgregarSucursales.getBtnCancelar().addActionListener(this);

				performActionOnTextComponents(ventanaAgregarSucursales);

			} else {
				JOptionPane.showMessageDialog(null, "No hay ningun Cliente seleccionado", "Error al modificar Cliente",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		else if (e.getSource() == this.ventanaClientes.getBtnVisualizarSucursales()) {

			this.ventanaSucursales = new VentanaSucursales(this);
			this.ventanaSucursales.getTablaSucursales().addMouseListener(this);

			this.ventanaSucursales.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {

					ventanaSucursales.dispose();
					ventanaSucursales = null;

				}
			});

			this.ventanaSucursales.getTextCliente().setText(clienteElegido.getRazon_Social());
			this.ventanaSucursales.getBtnEditar().addActionListener(this);
			this.ventanaSucursales.getBtnAgregar().addActionListener(this);
			this.ventanaSucursales.getBtnBorrar().addActionListener(this);

			this.llenarTablaSucursales(clienteElegido.getId());

			tablaFiltros.agregarAutofiltros(this.ventanaSucursales.getTablaSucursales());

		}

		if (ventanaAgregarClientes != null) {

			if (e.getSource() == this.ventanaAgregarClientes.getBtnAgregarCliente()) {

				ClienteDTO nuevoCliente = null;
				SucursalDTO sucursalDefault = null;

				if (!ventanaAgregarClientes.getTxtEmail().getText().isEmpty()) {

					if (!validacionMail(ventanaAgregarClientes.getTxtEmail().getText())) {

						JOptionPane.showMessageDialog(null, "Escriba un email correcto",
								"Error al registrar una direccion de email", JOptionPane.ERROR_MESSAGE);
					} else {

						nuevoCliente = TomarDatosCliente();
						sucursalDefault = SucursalDefault(nuevoCliente.getId());

						this.agenda.agregarClientes(nuevoCliente);
						this.agenda.agregarSucursal(sucursalDefault);

						this.ventanaAgregarClientes.dispose();
						ventanaAgregarClientes = null;
						llenarTabla();

					}

				} else {

					nuevoCliente = TomarDatosCliente();
					sucursalDefault = SucursalDefault(nuevoCliente.getId());

					this.agenda.agregarClientes(nuevoCliente);
					this.agenda.agregarSucursal(sucursalDefault);

					this.ventanaAgregarClientes.dispose();
					ventanaAgregarClientes = null;
					llenarTabla();

				}

			} else if (e.getSource() == this.ventanaAgregarClientes.getBtnCancelar()) {

				this.ventanaAgregarClientes.dispose();
				ventanaAgregarClientes = null;

			}
		}

		if (ventanaEditarCliente != null) {
			if (e.getSource() == this.ventanaEditarCliente.getBtnAgregarCliente()) {

				if (!ventanaEditarCliente.getTxtEmail().getText().isEmpty()) {

					if (!validacionMail(ventanaEditarCliente.getTxtEmail().getText())) {

						JOptionPane.showMessageDialog(null, "Escriba un email correcto",
								"Error al registrar una direccion de email", JOptionPane.ERROR_MESSAGE);
					}

					else {

						ClienteDTO clienteElegidoeditado = new ClienteDTO(clienteElegido.getId(),
								ventanaEditarCliente.getTxtNombre().getText(),
								ventanaEditarCliente.getTxtCUIT().getText(),
								ventanaEditarCliente.getTxtDireccion().getText(),
								ventanaEditarCliente.getTxtTelefonoEmpresa().getText(),
								ventanaEditarCliente.getTxtContacto().getText(),
								ventanaEditarCliente.getTxtTelefonoContacto().getText(),
								ventanaEditarCliente.getTxtEmail().getText());
						this.agenda.editarClientes(clienteElegidoeditado);

						this.ventanaEditarCliente.dispose();
						ventanaEditarCliente = null;
						llenarTabla();
					}

				} else {

					ClienteDTO clienteElegidoeditado = new ClienteDTO(clienteElegido.getId(),
							ventanaEditarCliente.getTxtNombre().getText(), ventanaEditarCliente.getTxtCUIT().getText(),
							ventanaEditarCliente.getTxtDireccion().getText(),
							ventanaEditarCliente.getTxtTelefonoEmpresa().getText(),
							ventanaEditarCliente.getTxtContacto().getText(),
							ventanaEditarCliente.getTxtTelefonoContacto().getText(),
							ventanaEditarCliente.getTxtEmail().getText());
					this.agenda.editarClientes(clienteElegidoeditado);

					this.ventanaEditarCliente.dispose();
					ventanaEditarCliente = null;
					llenarTabla();

				}
			}

			else if (e.getSource() == this.ventanaEditarCliente.getBtnCancelar()) {

				this.ventanaEditarCliente.dispose();
				ventanaEditarCliente = null;

			}

		}

		if (ventanaAgregarSucursales != null) {
			if (e.getSource() == this.ventanaAgregarSucursales.getBtnAgregarSucursal()) {

				SucursalDTO nuevaSucursal = tomarDatosSucursal(clienteElegido.getId());

				if (cantidadSucursalesXCliente(clienteElegido.getId()) == 1) {

					SucursalesEncliente = this.agenda.obtenerSucursalesxCliente(clienteElegido.getId()).get(0);

					if (SucursalesEncliente.getNombreSucursal().compareTo("") == 0) {

						this.agenda.editarSucursal(nuevaSucursal);
					} else {

						this.agenda.agregarSucursal(nuevaSucursal);
					}

				} else {

					this.agenda.agregarSucursal(nuevaSucursal);
				}

				this.ventanaAgregarSucursales.dispose();
				ventanaAgregarSucursales = null;

				if (ventanaSucursales != null) {

					this.llenarTablaSucursales(clienteElegido.getId());

				} else {

					this.llenarTabla();
				}

			}

			else if (e.getSource() == this.ventanaAgregarSucursales.getBtnCancelar()) {

				this.ventanaAgregarSucursales.dispose();
				ventanaAgregarSucursales = null;
			}
		}

		if (ventanaSucursales != null) {
			if (e.getSource() == this.ventanaSucursales.getBtnEditar()) {

				int fila = this.ventanaSucursales.getTablaSucursales().getSelectedRow();
				if (fila != -1) {

					this.ventanaEditarSucursales = new VentanaAgregarSucursal(this);

					this.ventanaEditarSucursales.getTxtNombre().setText(SucursalesEncliente.getNombreSucursal());
					this.ventanaEditarSucursales.getTxtCliente().setText(clienteElegido.getRazon_Social());
					this.ventanaEditarSucursales.getTxtDireccion().setText(SucursalesEncliente.getDomicilioSucursal());
					this.ventanaEditarSucursales.getTxtContacto().setText(SucursalesEncliente.getContactoSucursal());
					this.ventanaEditarSucursales.getTxtTelefonoContacto()
							.setText(SucursalesEncliente.getTelefonoSucursal());
					this.ventanaEditarSucursales.getTxtEmail().setText(SucursalesEncliente.getCorreoElectronico());

					this.ventanaEditarSucursales.getBtnAgregarSucursal().addActionListener(this);
					this.ventanaEditarSucursales.getBtnCancelar().addActionListener(this);

					performActionOnTextComponents(ventanaEditarSucursales);

				} else {
					JOptionPane.showMessageDialog(null, "No hay ninguna Sucursal seleccionada",
							"Error al modificar Cliente", JOptionPane.ERROR_MESSAGE);
				}

			} else if (e.getSource() == this.ventanaSucursales.getBtnAgregar()) {

				this.ventanaAgregarSucursales = new VentanaAgregarSucursal(this);
				this.ventanaAgregarSucursales.getTxtCliente().setText(clienteElegido.getRazon_Social());
				this.ventanaAgregarSucursales.getBtnCancelar().addActionListener(this);
				this.ventanaAgregarSucursales.getBtnAgregarSucursal().addActionListener(this);
				performActionOnTextComponents(ventanaAgregarSucursales);
			}

			else if (e.getSource() == this.ventanaSucursales.getBtnBorrar()) {

				int fila = this.ventanaSucursales.getTablaSucursales().getSelectedRow();

				if (fila != -1) {

					if (!ReparacionAsociadaAsuc(SucursalesEncliente.getIdSucursal())) {

						int seleccion = JOptionPane.showConfirmDialog(ventanaSucursales,
								"Está seguro Eliminar esta Sucursal?", "Confirmación", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);

						if (seleccion == JOptionPane.YES_OPTION) {

							String respuesta = JOptionPane.showInputDialog(null,
									"Ingrese la Contraseña de Seguridad : ", "");
							if (respuesta != null) {

								if (respuesta.compareTo("0000") == 0) {

									if (cantidadSucursalesXCliente(clienteElegido.getId()) > 1) {

										this.agenda.borrarSucursal(SucursalesEncliente);
										this.llenarTablaSucursales(clienteElegido.getId());
									} else {
										int idcli = SucursalesEncliente.getIdClientesuc();
										int IDSucursal = SucursalesEncliente.getIdSucursal();
										String NombreSuc = "";
										String DomicilioSuc = "";
										String TelefonoSuc = "";
										String ContactoSuc = "";
										String CorreoSuc = "";

										SucursalDTO SucursalDefault2 = new SucursalDTO(IDSucursal, NombreSuc, idcli,
												DomicilioSuc, ContactoSuc, TelefonoSuc, CorreoSuc);

										this.agenda.editarSucursal(SucursalDefault2);

										JOptionPane.showMessageDialog(null, "Este cliente ya no posee Sucursales ",
												"Cliente Sin sucursales", JOptionPane.INFORMATION_MESSAGE);
										this.ventanaSucursales.dispose();
										this.llenarTabla();

									}

								} else {
									JOptionPane.showMessageDialog(null, "Contraseña Incorrecta. ",
											"Error al Eliminar Cliente", JOptionPane.ERROR_MESSAGE);

								}
							}

						}

					} else {

						JOptionPane.showMessageDialog(null,
								"Esta sucursal Posee reparaciones asociadas. No es posible Eliminarla ",
								"Error al Eliminar Sucursal", JOptionPane.INFORMATION_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "No hay ninguna Sucursal seleccionada",
							"Error al modificar Sucursal", JOptionPane.ERROR_MESSAGE);
				}

			}

		}

		if (ventanaSucursales != null) {

			if (ventanaEditarSucursales != null) {
				if (e.getSource() == this.ventanaEditarSucursales.getBtnAgregarSucursal()) {

					SucursalDTO sucursalElegidoeditado = new SucursalDTO(SucursalesEncliente.getIdSucursal(),
							ventanaEditarSucursales.getTxtNombre().getText(), SucursalesEncliente.getIdClientesuc(),
							ventanaEditarSucursales.getTxtDireccion().getText(),
							ventanaEditarSucursales.getTxtContacto().getText(),
							ventanaEditarSucursales.getTxtTelefonoContacto().getText(),
							ventanaEditarSucursales.getTxtEmail().getText());
					this.agenda.editarSucursal(sucursalElegidoeditado);

					this.ventanaEditarSucursales.dispose();
					ventanaEditarSucursales = null;
					llenarTablaSucursales(SucursalesEncliente.getIdClientesuc());

				} else if (e.getSource() == this.ventanaEditarSucursales.getBtnCancelar()) {
					this.ventanaEditarSucursales.dispose();
					ventanaEditarSucursales = null;

				}
			}

		}

	}

	boolean validacionMail(String email) {

		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean ReparacionAsociadaAsuc(int idsucursal) {

		boolean tieneRepacacionAsociada = false;

		tieneRepacacionAsociada = this.agenda.reparacionAsociada(idsucursal);

		return tieneRepacacionAsociada;
	}

	private boolean ReparacionAsociadaACliente(int idCliente) {

		boolean tieneRepacacionAsociada = false;

		tieneRepacacionAsociada = this.agenda.reparacionAsociadaCliente(idCliente);

		return tieneRepacacionAsociada;
	}

	private int cantidadSucursalesXCliente(int idcliente) {
		int cantidadSuc = 0;

		cantidadSuc = this.agenda.cantSucursalesXCliente(idcliente);

		return cantidadSuc;
	}

	private SucursalDTO SucursalDefault(int idcliente) {

		int idcli = idcliente;
		int IDSucursal = dameIDsucursal();
		String NombreSuc = "";
		String DomicilioSuc = "";
		String TelefonoSuc = "";
		String ContactoSuc = "";
		String CorreoSuc = "";

		SucursalDTO SucursalDefault = new SucursalDTO(IDSucursal, NombreSuc, idcli, DomicilioSuc, ContactoSuc,
				TelefonoSuc, CorreoSuc);

		return SucursalDefault;
	}

	private SucursalDTO tomarDatosSucursal(int idcliente) {

		int idcli = idcliente;
		int IDSucursal;
		SucursalesEncliente = this.agenda.obtenerSucursalesxCliente(idcliente).get(0);

		if (SucursalesEncliente.getNombreSucursal().compareTo("") == 0) {
			IDSucursal = SucursalesEncliente.getIdSucursal();
		} else {
			IDSucursal = dameIDsucursal();
		}

		String NombreSuc = this.ventanaAgregarSucursales.getTxtNombre().getText();
		String DomicilioSuc = this.ventanaAgregarSucursales.getTxtDireccion().getText();
		String TelefonoSuc = this.ventanaAgregarSucursales.getTxtTelefonoContacto().getText();
		String ContactoSuc = this.ventanaAgregarSucursales.getTxtContacto().getText();
		String CorreoSuc = this.ventanaAgregarSucursales.getTxtEmail().getText();

		SucursalDTO nuevaSucursal = new SucursalDTO(IDSucursal, NombreSuc, idcli, DomicilioSuc, ContactoSuc,
				TelefonoSuc, CorreoSuc);

		return nuevaSucursal;
	}

	private ClienteDTO TomarDatosCliente() {

		int IDcliente = dameIDcliente();
		String Nombre = this.ventanaAgregarClientes.getTxtNombre().getText();
		String CUIT = this.ventanaAgregarClientes.getTxtCUIT().getText();
		String Domicilio = this.ventanaAgregarClientes.getTxtDireccion().getText();
		String TelefonoEmpresa = this.ventanaAgregarClientes.getTxtTelefonoEmpresa().getText();
		String TelefonoContacto = this.ventanaAgregarClientes.getTxtTelefonoContacto().getText();
		String Contacto = this.ventanaAgregarClientes.getTxtContacto().getText();
		String CorreoElectronico = this.ventanaAgregarClientes.getTxtEmail().getText();

		ClienteDTO nuevoCliente = new ClienteDTO(IDcliente, Nombre, CUIT, Domicilio, TelefonoEmpresa, Contacto,
				TelefonoContacto, CorreoElectronico);

		return nuevoCliente;
	}

	public void agregarListenersVentanaAgregarCliente() {

		this.ventanaAgregarClientes = new VentanaAgregarCliente(this);
		this.ventanaAgregarClientes.getBtnCancelar().addActionListener(this);
		this.ventanaAgregarClientes.getBtnAgregarCliente().addActionListener(this);
		performActionOnTextComponents(ventanaAgregarClientes);

	}

	public void agregarListenersVentanaCliente() {

		this.ventanaClientes.getBtnAgregar().addActionListener(this);
		this.ventanaClientes.getBtnBorrar().addActionListener(this);
		this.ventanaClientes.getBtnEditar().addActionListener(this);
		this.ventanaClientes.getTablaClientes().addMouseListener(this);
		this.ventanaClientes.getBtnGenerarSucursales().addActionListener(this);
		this.ventanaClientes.getBtnVisualizarSucursales().addActionListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (this.ventanaClientes != null) {

			if (arg0.getSource() == this.ventanaClientes.getTablaClientes()) {
				int i = this.ventanaClientes.getTablaClientes().getSelectedRow();
				if (i != -1) {
					// Convertir índice de vista a índice del modelo
					int modelIndex = this.ventanaClientes.getTablaClientes().convertRowIndexToModel(i);
					if (!Clientes_en_tabla.isEmpty() && modelIndex < Clientes_en_tabla.size()) {
						clienteElegido = Clientes_en_tabla.get(modelIndex);

						this.ventanaClientes.getTxtNombreCliente().setText(clienteElegido.getRazon_Social());
						this.ventanaClientes.getTxtCUIT().setText(clienteElegido.getCUIT());
						this.ventanaClientes.getTxtDireccion().setText(clienteElegido.getDomicilio());
						this.ventanaClientes.getTxtDireccion().moveCaretPosition(0);
						this.ventanaClientes.getTxtContacto().setText(clienteElegido.getContacto());
						this.ventanaClientes.getTxtTelContacto().setText(clienteElegido.getTelefonoContacto());
						this.ventanaClientes.getTxtCorreo().setText(clienteElegido.getCorreoElectronico());
						this.ventanaClientes.getTxtTelEmpresa().setText(clienteElegido.getTelefonoEmpresa());
						this.ventanaClientes.getTxtCorreo().moveCaretPosition(0);

						if (cantidadSucursalesXCliente(clienteElegido.getId()) == 1) {
							SucursalesEncliente = this.agenda.obtenerSucursalesxCliente(clienteElegido.getId()).get(0);

							if (!SucursalesEncliente.getNombreSucursal().isEmpty()) {
								this.ventanaClientes.getBtnVisualizarSucursales().setVisible(true);
								this.ventanaClientes.getLblSucursales().setVisible(true);
							} else {
								this.ventanaClientes.getBtnVisualizarSucursales().setVisible(false);
								this.ventanaClientes.getLblSucursales().setVisible(false);
							}

						} else if (cantidadSucursalesXCliente(clienteElegido.getId()) > 1) {
							this.ventanaClientes.getBtnVisualizarSucursales().setVisible(true);
							this.ventanaClientes.getLblSucursales().setVisible(true);
						} else {
							this.ventanaClientes.getBtnVisualizarSucursales().setVisible(false);
							this.ventanaClientes.getLblSucursales().setVisible(false);
						}
					}
				}
			}
		}

		if (this.ventanaSucursales != null) {

			if (arg0.getSource() == this.ventanaSucursales.getTablaSucursales()) {
				int j = this.ventanaSucursales.getTablaSucursales().getSelectedRow();
				if (j != -1) {
					// Convertir índice de vista a índice del modelo
					int modelIndex = this.ventanaSucursales.getTablaSucursales().convertRowIndexToModel(j);
					if (!Sucursales_en_tabla.isEmpty() && modelIndex < Sucursales_en_tabla.size()) {
						SucursalesEncliente = Sucursales_en_tabla.get(modelIndex);

						this.ventanaSucursales.getTxtNombreSucursal().setText(SucursalesEncliente.getNombreSucursal());
						this.ventanaSucursales.getTxtDireccion().setText(SucursalesEncliente.getDomicilioSucursal());
						this.ventanaSucursales.getTxtDireccion().moveCaretPosition(0);
						this.ventanaSucursales.getTxtContacto().setText(SucursalesEncliente.getContactoSucursal());
						this.ventanaSucursales.getTxtTelContacto().setText(SucursalesEncliente.getTelefonoSucursal());
						this.ventanaSucursales.getTxtCorreo().setText(SucursalesEncliente.getCorreoElectronico());
					}
				}
			}
		}
	}

	private int dameIDcliente() {
		int idcliente = 0;
		idcliente = agenda.dameIDcliente() + 1;
		return idcliente;
	}

	private int dameIDsucursal() {
		int idsucursal = 0;
		idsucursal = agenda.dameIDsucursal() + 1;
		return idsucursal;
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

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "serial", "deprecation" })
	private static void configureUndoManager(JTextComponent textComponent) {
		UndoManager undoManager = new UndoManager();
		textComponent.getDocument().addUndoableEditListener(undoManager);

		// Crear una acción de deshacer
		AbstractAction undoAction = new AbstractAction("Deshacer") {
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canUndo()) {
					undoManager.undo();
				}
			}
		};

		// Asignar la tecla de acceso directo (Ctrl + Z) para la acción de deshacer
		undoAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Agregar la acción de deshacer al componente
		textComponent.getActionMap().put("Undo", undoAction);
		textComponent.getInputMap().put((KeyStroke) undoAction.getValue(Action.ACCELERATOR_KEY), "Undo");

		// Crear una acción de rehacer
		AbstractAction redoAction = new AbstractAction("Rehacer") {
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canRedo()) {
					undoManager.redo();
				}
			}
		};

		// Asignar la tecla de acceso directo (Ctrl + Y) para la acción de rehacer
		redoAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Agregar la acción de rehacer al componente
		textComponent.getActionMap().put("Redo", redoAction);
		textComponent.getInputMap().put((KeyStroke) redoAction.getValue(Action.ACCELERATOR_KEY), "Redo");
	}

	// Método para realizar una acción sobre todos los JTextField y JTextArea en un
	// JFrame
	private void performActionOnTextComponents(JFrame frame) {
		List<JTextComponent> textComponents = getAllTextComponents(frame);
		// Realiza la acción deseada sobre cada JTextComponent
		for (JTextComponent textComponent : textComponents) {
			configureUndoManager(textComponent);
		}
	}

	// Método para obtener todos los JTextField y JTextArea en un JFrame
	private List<JTextComponent> getAllTextComponents(Container container) {
		List<JTextComponent> textComponents = new ArrayList<>();
		Component[] components = container.getComponents();
		// Itera sobre los componentes y filtra los JTextField y JTextArea
		for (Component component : components) {
			if (component instanceof JTextComponent) {
				textComponents.add((JTextComponent) component);
			} else if (component instanceof Container) {
				textComponents.addAll(getAllTextComponents((Container) component));
			}
		}
		return textComponents;
	}

}
