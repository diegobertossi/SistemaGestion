package presentacion.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import persistencia.conexion.Conexion;
import presentacion.vista.VentanaAgregarCliente;
import presentacion.vista.VentanaAgregarCorreo;
import presentacion.vista.VentanaAgregarSucursal;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaQuitarCorreo;
import presentacion.vista.VentanaSucursales;
import dto.ClienteDTO;
import dto.SucursalDTO;

public class ControladorCliente implements ActionListener, MouseListener {
	private VentanaClientes ventanaClientes;
	private VentanaAgregarSucursal ventanaAgregarSucursales;
	private VentanaAgregarCorreo ventanaAgregarCorreo;
	private VentanaSucursales ventanaSucursales;
	private List<ClienteDTO> Clientes_en_tabla;
	private List<SucursalDTO> Sucursales_en_tabla;
	private Agenda agenda;
	private ClienteDTO clienteElegido;
	private SucursalDTO sucursalElegida;
	private SucursalDTO SucursalesEncliente;

	private TablaFiltros tablaFiltros = new TablaFiltros();
	private boolean llamadoDesdeAgregarEquipo = false;
	private boolean llamadoDesdeVentanaCliente = false;
	private boolean editando = false;
	private boolean editandoSucursal = false;
	private boolean correosdeSucursal = false;
	private boolean quitarCorreosdeSucursal = false;
	
	// Referencia al GestorAgregarEquipo para actualizar combos después de guardar
	private Object gestorAgregarEquipo = null;

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	public ControladorCliente(VentanaClientes ventanaClientes, Agenda agenda) {

		this.ventanaClientes = ventanaClientes;

		agregarListenersVentanaCliente();

		this.agenda = agenda;
		this.Clientes_en_tabla = null;
		this.clienteElegido = null;

		llenarTabla();

		// Estado inicial: campos deshabilitados
		limpiarCampos();
		deshabilitarCampos(ventanaClientes);
	}

	// Método para setear el flag desde el controlador de reparaciones
	public void setLlamadoDesdeAgregarEquipo(boolean valor) {
		this.llamadoDesdeAgregarEquipo = valor;
	}
	
	// Método para recibir referencia del GestorAgregarEquipo
	public void setGestorAgregarEquipo(Object gestor) {
		this.gestorAgregarEquipo = gestor;
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

		// =================== BOTÓN EDITAR (CORREGIDO) ===================
		if (e.getSource() == this.ventanaClientes.getBtnEditar()) {
			int filaSeleccionada = this.ventanaClientes.getTablaClientes().getSelectedRow();

			if (filaSeleccionada != -1) {
				// Obtener cliente de la fila seleccionada
				int modelIndex = this.ventanaClientes.getTablaClientes().convertRowIndexToModel(filaSeleccionada);
				if (modelIndex < Clientes_en_tabla.size()) {
					clienteElegido = Clientes_en_tabla.get(modelIndex);

					// Configurar modo edición
					editando = true;

					// Cargar datos en los campos correctamente
					this.ventanaClientes.getTxtNombreCliente()
							.setText(clienteElegido.getRazon_Social() != null ? clienteElegido.getRazon_Social() : "");
					this.ventanaClientes.getTxtCUIT()
							.setText(clienteElegido.getCUIT() != null ? clienteElegido.getCUIT() : "");
					this.ventanaClientes.getTxtDireccion()
							.setText(clienteElegido.getDomicilio() != null ? clienteElegido.getDomicilio() : "");
					this.ventanaClientes.getTxtTelEmpresa().setText(
							clienteElegido.getTelefonoEmpresa() != null ? clienteElegido.getTelefonoEmpresa() : "");
					this.ventanaClientes.getTxtContacto()
							.setText(clienteElegido.getContacto() != null ? clienteElegido.getContacto() : "");
					this.ventanaClientes.getTxtTelContacto().setText(
							clienteElegido.getTelefonoContacto() != null ? clienteElegido.getTelefonoContacto() : "");

					// Cargar nuevos campos
					String tipoDocEdit = clienteElegido.getTipoDocumento();
					if (tipoDocEdit != null) {
						this.ventanaClientes.getCmbTipoDocumento().setSelectedItem(tipoDocEdit);
					} else {
						this.ventanaClientes.getCmbTipoDocumento().setSelectedItem("CUIT");
					}
					String condIvaEdit = clienteElegido.getCondicionIva();
					if (condIvaEdit != null && !condIvaEdit.isEmpty()) {
						this.ventanaClientes.getCmbCondicionIva().setSelectedItem(condIvaEdit);
					} else {
						this.ventanaClientes.getCmbCondicionIva().setSelectedIndex(-1);
					}
					if ("particular".equalsIgnoreCase(clienteElegido.getTipoPersona())) {
						this.ventanaClientes.getRdParticular().setSelected(true);
					} else {
						this.ventanaClientes.getRdEmpresa().setSelected(true);
					}

					// Al presionar Editar
					String correos = clienteElegido.getCorreoElectronico();
					if (correos != null && !correos.trim().isEmpty()) {
						// Reemplaza " ;" (con o sin espacios) por salto de línea
						correos = correos.replaceAll("\\s*;\\s*", "\n");
					}
					this.ventanaClientes.getTxtCorreo().setText(correos != null ? correos : "");

					// Configurar interfaz
					ventanaClientes.getBtnGuardar().setVisible(true);
					ventanaClientes.getBtnCancelar().setVisible(true);
					ventanaClientes.getBtnAgregar().setEnabled(false);
					ventanaClientes.getBtnBorrar().setEnabled(false);
					ventanaClientes.getBtnEditar().setEnabled(false);
					ventanaClientes.getBtnGenerarSucursales().setEnabled(false);
					ventanaClientes.getTablaClientes().setEnabled(false);
					ventanaClientes.getBtnVisualizarSucursales().setVisible(false);
					ventanaClientes.getLblSucursales().setVisible(false);

					habilitarCampos(ventanaClientes);
					tablaFiltros.deshabilitarAutofiltro(this.ventanaClientes.getTablaClientes());

					System.out.println("MODO EDICIÓN ACTIVADO - Cliente: " + clienteElegido.getRazon_Social());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione un cliente para editar", "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		// =================== BOTÓN AGREGAR ===================
		else if (e.getSource() == this.ventanaClientes.getBtnAgregar()) {
			// Limpiar campos y preparar para nuevo cliente
			limpiarCampos();
			clienteElegido = null;
			editando = false; // Modo agregar, no editar

			// Mostrar botones de guardar y cancelar
			ventanaClientes.getBtnGuardar().setVisible(true);
			ventanaClientes.getBtnCancelar().setVisible(true);

			// Deshabilitar otros botones
			ventanaClientes.getBtnAgregar().setEnabled(false);
			ventanaClientes.getBtnBorrar().setEnabled(false);
			ventanaClientes.getBtnEditar().setEnabled(false);
			ventanaClientes.getBtnGenerarSucursales().setEnabled(false);
			ventanaClientes.getTablaClientes().setEnabled(false);
			ventanaClientes.getTablaClientes().clearSelection();
			ventanaClientes.getBtnVisualizarSucursales().setVisible(false);
			ventanaClientes.getLblSucursales().setVisible(false);

			// Habilitar campos para edición
			habilitarCampos(ventanaClientes);
			tablaFiltros.deshabilitarAutofiltro(this.ventanaClientes.getTablaClientes());

			System.out.println("MODO AGREGAR ACTIVADO");
		}

		// =================== BOTÓN GUARDAR (CORREGIDO) ===================
		else if (e.getSource() == this.ventanaClientes.getBtnGuardar()) {
			String nombreTexto = this.ventanaClientes.getTxtNombreCliente().getText();

			if (!editando && clienteElegido == null) {
				// *** MODO AGREGAR CLIENTE ***
				// Validación mejorada
				if (nombreTexto == null || nombreTexto.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío",
							"Error al guardar Cliente", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// verificar si el cliente ya existe
				for (ClienteDTO c : this.Clientes_en_tabla) {
					if (c.getRazon_Social().equalsIgnoreCase(nombreTexto.trim())) {
						JOptionPane.showMessageDialog(null, "El cliente ya existe", "Error al guardar Cliente",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				String textoCorreos = ventanaClientes.getTxtCorreo().getText();
				String[] correos = textoCorreos.split("\\n");
				String emailTexto = String.join(" ;", correos);

				ClienteDTO nuevoCliente = null;
				SucursalDTO sucursalDefault = null;

				String tipoDoc = this.ventanaClientes.getCmbTipoDocumento().getSelectedItem() != null
					? this.ventanaClientes.getCmbTipoDocumento().getSelectedItem().toString() : "CUIT";
				String condIva = this.ventanaClientes.getCmbCondicionIva().getSelectedItem() != null
					? this.ventanaClientes.getCmbCondicionIva().getSelectedItem().toString() : "";
				String tipoPer = this.ventanaClientes.getRdEmpresa().isSelected() ? "empresa" : "particular";

				// Crear nuevo cliente
				nuevoCliente = new ClienteDTO(dameIDcliente(), nombreTexto.trim(),
						this.ventanaClientes.getTxtCUIT().getText().trim(),
						this.ventanaClientes.getTxtDireccion().getText().trim(),
						this.ventanaClientes.getTxtTelEmpresa().getText().trim(),
						this.ventanaClientes.getTxtContacto().getText().trim(),
						this.ventanaClientes.getTxtTelContacto().getText().trim(),
						emailTexto != null ? emailTexto.trim() : "",
						tipoDoc, condIva, tipoPer);

				sucursalDefault = SucursalDefault(nuevoCliente.getId());

				this.agenda.agregarClientes(nuevoCliente);
				this.agenda.agregarSucursal(sucursalDefault);

				// Sync a FacturaSoft
				sincronizarConFacturaSoft(nuevoCliente);

				// Finalizar operación
				finalizarOperacion();

			} else if (editando && clienteElegido != null) {
				// *** MODO EDITAR CLIENTE ***
				// Validación robusta para edición
				if (nombreTexto == null || nombreTexto.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío",
							"Error al guardar Cliente", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (clienteElegido == null) {
					JOptionPane.showMessageDialog(null, "Error: No se ha seleccionado un cliente válido",
							"Error al editar Cliente", JOptionPane.ERROR_MESSAGE);
					return;
				}

				String textoCorreos = ventanaClientes.getTxtCorreo().getText();
				String[] correos = textoCorreos.split("\\n");
				String emailTexto = String.join(";", correos);

				String tipoDoc = this.ventanaClientes.getCmbTipoDocumento().getSelectedItem() != null
					? this.ventanaClientes.getCmbTipoDocumento().getSelectedItem().toString() : "CUIT";
				String condIva = this.ventanaClientes.getCmbCondicionIva().getSelectedItem() != null
					? this.ventanaClientes.getCmbCondicionIva().getSelectedItem().toString() : "";
				String tipoPer = this.ventanaClientes.getRdEmpresa().isSelected() ? "empresa" : "particular";

				// Crear cliente editado
				ClienteDTO clienteEditado = new ClienteDTO(clienteElegido.getId(), nombreTexto.trim(),
						this.ventanaClientes.getTxtCUIT().getText().trim(),
						this.ventanaClientes.getTxtDireccion().getText().trim(),
						this.ventanaClientes.getTxtTelEmpresa().getText().trim(),
						this.ventanaClientes.getTxtContacto().getText().trim(),
						this.ventanaClientes.getTxtTelContacto().getText().trim(),
						emailTexto != null ? emailTexto.trim() : "",
						tipoDoc, condIva, tipoPer);

				this.agenda.editarClientes(clienteEditado);

				// Sync a FacturaSoft
				sincronizarConFacturaSoft(clienteEditado);

				// Finalizar edición
				finalizarOperacion();
			}
		}

		// =================== BOTÓN CANCELAR ===================
		else if (e.getSource() == this.ventanaClientes.getBtnCancelar()) {
			finalizarOperacion();
		}

		else if (e.getSource() == this.ventanaClientes.getBtnBorrar()) {

			int fila = this.ventanaClientes.getTablaClientes().getSelectedRow();

			if (fila != -1 && clienteElegido != null) {

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

										// Sync a FacturaSoft (baja)
										eliminarEnFacturaSoft(clienteElegido.getId());

										this.llenarTabla();
										limpiarCampos();

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
			if (fila != -1 && clienteElegido != null) {

				llamadoDesdeVentanaCliente = true;
				ventanaSucursales = agregarListenersVentanaSucursales();

			} else {
				JOptionPane.showMessageDialog(null, "No hay ningun Cliente seleccionado", "Error al modificar Cliente",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		else if (e.getSource() == this.ventanaClientes.getBtnVisualizarSucursales()) {

			this.ventanaSucursales = new VentanaSucursales(this);

			ventanaSucursales.getTextCliente().setText(clienteElegido.getRazon_Social());

			llenarTablaSucursales(clienteElegido.getId());
			agregarListenersVentanaSucursales();

			sucursalElegida = null;

		}

		else if (e.getSource() == this.ventanaClientes.getBtnAgregarCorreo()) {

			correosdeSucursal = false;
			agregarCorreo();

		}

		else if (e.getSource() == this.ventanaClientes.getBtnQuitarCorreo()) {

			correosdeSucursal = false;
			quitarCorreo();
		}

		if (ventanaAgregarCorreo != null) {
			// Manejo de eventos para ventanaAgregarCorreo

			if (e.getSource() == this.ventanaAgregarCorreo.getBtnAgregarCorreo()) {

				String emailTexto = this.ventanaAgregarCorreo.getTxtCorreo().getText();

				if (emailTexto == null || emailTexto.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo Correo no puede estar vacío",
							"Error al agregar correo", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!validacionMail(emailTexto.trim())) {
					JOptionPane.showMessageDialog(null, "Escriba un email correcto",
							"Error al registrar una dirección de email", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (correosdeSucursal) {
					// Obtener el texto actual y agregar el nuevo correo debajo
					String correosActuales = this.ventanaSucursales.getTxtCorreo().getText();
					if (correosActuales == null || correosActuales.trim().isEmpty()) {
						this.ventanaSucursales.getTxtCorreo().setText(emailTexto.trim());
					} else {
						this.ventanaSucursales.getTxtCorreo().setText(correosActuales + "\n" + emailTexto.trim());
					}
					ventanaSucursales.getBtnAgregarCorreo().setEnabled(true);
					ventanaSucursales.getBtnQuitarCorreo().setEnabled(true);

				} else {

					// Obtener el texto actual y contar los correos
					String correosActuales = this.ventanaClientes.getTxtCorreo().getText();

					// Si no excede, agregar el correo normalmente
					if (correosActuales == null || correosActuales.trim().isEmpty()) {
						this.ventanaClientes.getTxtCorreo().setText(emailTexto.trim());
					} else {
						this.ventanaClientes.getTxtCorreo().setText(correosActuales + "\n" + emailTexto.trim());
					}

					ventanaClientes.getBtnAgregarCorreo().setEnabled(true);
					ventanaClientes.getBtnQuitarCorreo().setEnabled(true);
				}

				ventanaAgregarCorreo.dispose();
				ventanaAgregarCorreo = null;
			}

			else if (e.getSource() == this.ventanaAgregarCorreo.getBtnCancelar()) {

				ventanaAgregarCorreo.dispose();
				ventanaAgregarCorreo = null;

				if (correosdeSucursal) {
					ventanaSucursales.getBtnAgregarCorreo().setEnabled(true);
					ventanaSucursales.getBtnQuitarCorreo().setEnabled(true);
				} else {
					ventanaClientes.getBtnAgregarCorreo().setEnabled(true);
					ventanaClientes.getBtnQuitarCorreo().setEnabled(true);
				}

			}
		}

		if (ventanaSucursales != null) {
			if (e.getSource() == this.ventanaSucursales.getBtnEditar()) {

				int filaSeleccionada = this.ventanaSucursales.getTablaSucursales().getSelectedRow();
				if (filaSeleccionada != -1 && SucursalesEncliente != null) {

					if (filaSeleccionada != -1) {
						// Obtener cliente de la fila seleccionada
						int modelIndex = this.ventanaSucursales.getTablaSucursales()
								.convertRowIndexToModel(filaSeleccionada);
						if (modelIndex < Sucursales_en_tabla.size()) {
							sucursalElegida = Sucursales_en_tabla.get(modelIndex);

							// Configurar modo edición
							editandoSucursal = true;

							// Cargar datos en los campos correctamente
							this.ventanaSucursales.getTxtNombreSucursal()
									.setText(sucursalElegida.getNombreSucursal() != null
											? sucursalElegida.getNombreSucursal()
											: "");

							this.ventanaSucursales.getTxtDireccion()
									.setText(sucursalElegida.getDomicilioSucursal() != null
											? sucursalElegida.getDomicilioSucursal()
											: "");
							this.ventanaSucursales.getTxtContacto()
									.setText(sucursalElegida.getContactoSucursal() != null
											? sucursalElegida.getContactoSucursal()
											: "");

							this.ventanaSucursales.getTxtCorreo()
									.setText(sucursalElegida.getCorreoElectronico() != null
											? sucursalElegida.getCorreoElectronico()
											: "");

							String correos = sucursalElegida.getCorreoElectronico();
							if (correos != null && !correos.trim().isEmpty()) {
								// Reemplaza " ;" (con o sin espacios) por salto de línea
								correos = correos.replaceAll("\\s*;\\s*", "\n");
							}
							this.ventanaSucursales.getTxtCorreo().setText(correos != null ? correos : "");

							this.ventanaSucursales.getTxtTelContacto()
									.setText(sucursalElegida.getTelefonoSucursal() != null
											? sucursalElegida.getTelefonoSucursal()
											: "");

							// Configurar interfaz
							ventanaSucursales.getBtnGuardarSucursal().setVisible(true);
							ventanaSucursales.getBtnCancelarSucursal().setVisible(true);
							ventanaSucursales.getBtnAgregar().setEnabled(false);
							ventanaSucursales.getBtnBorrar().setEnabled(false);
							ventanaSucursales.getBtnEditar().setEnabled(false);
							ventanaSucursales.getTablaSucursales().setEnabled(false);

							habilitarCamposSucursales(ventanaSucursales);

							System.out.println(
									"MODO EDICIÓN ACTIVADO - Sucursal: " + sucursalElegida.getNombreSucursal());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Seleccione una sucursal para editar", "Advertencia",
								JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "No hay ninguna Sucursal seleccionada",
							"Error al modificar Cliente", JOptionPane.ERROR_MESSAGE);
				}

			} else if (e.getSource() == this.ventanaSucursales.getBtnAgregar()) {

				// Limpiar campos y preparar para nuevo cliente
				limpiarCamposSucursal();
				sucursalElegida = null;
				editandoSucursal = false; // Modo agregar, no editar

				// Mostrar botones de guardar y cancelar
				ventanaSucursales.getBtnGuardarSucursal().setVisible(true);
				ventanaSucursales.getBtnCancelarSucursal().setVisible(true);
				ventanaSucursales.getBtnAgregar().setEnabled(false);
				ventanaSucursales.getBtnBorrar().setEnabled(false);
				ventanaSucursales.getBtnEditar().setEnabled(false);
				ventanaSucursales.getTablaSucursales().setEnabled(false);
				ventanaSucursales.getTablaSucursales().clearSelection();
				// Habilitar campos para edición
				habilitarCamposSucursales(ventanaSucursales);
				System.out.println("MODO AGREGAR ACTIVADO");

			}

			else if (e.getSource() == this.ventanaSucursales.getBtnAgregarCorreo()) {

				correosdeSucursal = true;
				agregarCorreo();

			}

			else if (e.getSource() == this.ventanaSucursales.getBtnQuitarCorreo()) {

				quitarCorreosdeSucursal = true;
				quitarCorreo();

			}

			else if (e.getSource() == this.ventanaSucursales.getBtnBorrar()) {

				int fila = this.ventanaSucursales.getTablaSucursales().getSelectedRow();

				if (fila != -1 && SucursalesEncliente != null) {

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

										agenda.borrarSucursal(SucursalesEncliente);
										llenarTablaSucursales(clienteElegido.getId());
										limpiarCamposSucursal();

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

			else if (e.getSource() == this.ventanaSucursales.getBtnCancelarSucursal()) {
				finalizarOperacionSucursal();
			}

			else if (e.getSource() == this.ventanaSucursales.getBtnGuardarSucursal()) {

				if (!editandoSucursal && sucursalElegida == null) {
					// *** MODO AGREGAR SUCURSAL ***
					// Validación mejorada
					String nombreTexto = this.ventanaSucursales.getTxtNombreSucursal().getText();
					if (nombreTexto == null || nombreTexto.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío",
								"Error al guardar Sucursal", JOptionPane.ERROR_MESSAGE);
						return;
					}

					SucursalDTO nuevaSucursal = new SucursalDTO(dameIDsucursal(), nombreTexto.trim(),
							clienteElegido.getId(), this.ventanaSucursales.getTxtDireccion().getText().trim(),
							this.ventanaSucursales.getTxtContacto().getText().trim(),
							this.ventanaSucursales.getTxtTelContacto().getText().trim(),
							this.ventanaSucursales.getTxtCorreo().getText().trim());

					this.agenda.agregarSucursal(nuevaSucursal);

					finalizarOperacionSucursal();

				} else if (editandoSucursal && sucursalElegida != null) {

					// Validación robusta para edición
					String nombreTexto = this.ventanaSucursales.getTxtNombreSucursal().getText();
					if (nombreTexto == null || nombreTexto.trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El campo Nombre no puede estar vacío",
								"Error al guardar Sucursal", JOptionPane.ERROR_MESSAGE);
						return;
					}

					SucursalDTO sucursalEditada = new SucursalDTO(sucursalElegida.getIdSucursal(), nombreTexto.trim(),
							sucursalElegida.getIdClientesuc(),
							this.ventanaSucursales.getTxtDireccion().getText().trim(),
							this.ventanaSucursales.getTxtContacto().getText().trim(),
							this.ventanaSucursales.getTxtTelContacto().getText().trim(),
							this.ventanaSucursales.getTxtCorreo().getText().trim());

					this.agenda.editarSucursal(sucursalEditada);

					finalizarOperacionSucursal();
				} else {
					JOptionPane.showMessageDialog(null, "Error: No se ha seleccionado una sucursal válida",
							"Error al editar Sucursal", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

	}

	private void quitarCorreo() {

		String correoTexto = "";
		String[] correosArray;
		String[] correos;

		if (quitarCorreosdeSucursal) {
			correoTexto = this.ventanaSucursales.getTxtCorreo().getText();
			if (correoTexto == null || correoTexto.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "El campo Correo está vacío", "Error al quitar correo",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			correosArray = correoTexto.split("\\n");
			if (correosArray.length == 0) {
				JOptionPane.showMessageDialog(null, "No hay correos para quitar", "Error al quitar correo",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			correos = ventanaSucursales.getTxtCorreo().getText().split("\\n");

		}

		else {
			correoTexto = this.ventanaClientes.getTxtCorreo().getText();
			if (correoTexto == null || correoTexto.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "El campo Correo está vacío", "Error al quitar correo",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			correosArray = correoTexto.split("\\n");
			if (correosArray.length == 0) {
				JOptionPane.showMessageDialog(null, "No hay correos para quitar", "Error al quitar correo",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			correos = ventanaClientes.getTxtCorreo().getText().split("\\n");
		}

		VentanaQuitarCorreo ventanaQuitarCorreo = new VentanaQuitarCorreo();

		// Obtener los correos del JTextArea de ventanaClientes

		// Asignar cada correo a su JTextField correspondiente en ventanaQuitarCorreo
		ventanaQuitarCorreo.getTxtCorreo1().setText(correos.length > 0 ? correos[0] : "");
		ventanaQuitarCorreo.getTxtCorreo2().setText(correos.length > 1 ? correos[1] : "");
		ventanaQuitarCorreo.getTxtCorreo3().setText(correos.length > 2 ? correos[2] : "");
		ventanaQuitarCorreo.getTxtCorreo4().setText(correos.length > 3 ? correos[3] : "");

		// Dentro del ActionListener del botón "Aceptar" de VentanaQuitarCorreo
		ventanaQuitarCorreo.getBtnQuitarCorreoSeleccionado().addActionListener(new ActionListener() {
			String[] correosActuales;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener los correos actuales

				if (quitarCorreosdeSucursal) {
					correosActuales = ventanaSucursales.getTxtCorreo().getText().split("\\n");
				} else {
					correosActuales = ventanaClientes.getTxtCorreo().getText().split("\\n");
				}

				List<String> correosAEliminar = new ArrayList<>();

				// Verificar qué checkboxes están seleccionados y agregar el correo
				// correspondiente a la lista de eliminación

				if (ventanaQuitarCorreo.getChkCorreo1().isSelected()) {
					correosAEliminar.add(ventanaQuitarCorreo.getTxtCorreo1().getText());
				}
				if (ventanaQuitarCorreo.getChkCorreo2().isSelected()) {
					correosAEliminar.add(ventanaQuitarCorreo.getTxtCorreo2().getText());
				}
				if (ventanaQuitarCorreo.getChkCorreo3().isSelected()) {
					correosAEliminar.add(ventanaQuitarCorreo.getTxtCorreo3().getText());
				}
				if (ventanaQuitarCorreo.getChkCorreo4().isSelected()) {
					correosAEliminar.add(ventanaQuitarCorreo.getTxtCorreo4().getText());
				}

				// Construir la nueva lista de correos, excluyendo los seleccionados
				StringBuilder nuevoTexto = new StringBuilder();
				for (String correo : correosActuales) {
					if (!correosAEliminar.contains(correo)) {
						if (nuevoTexto.length() > 0) {
							nuevoTexto.append("\n");
						}
						nuevoTexto.append(correo);
					}
				}

				// Actualizar el JTextArea en ventanaClientes
				if (quitarCorreosdeSucursal) {
					ventanaSucursales.getTxtCorreo().setText(nuevoTexto.toString());

				} else {
					ventanaClientes.getTxtCorreo().setText(nuevoTexto.toString());
				}
				ventanaQuitarCorreo.dispose();
			}
		});

		ventanaQuitarCorreo.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaQuitarCorreo.dispose();
			}
		});

	}

	private void agregarCorreo() {

		if (correosdeSucursal) {
			// Obtener el texto actual y contar los correos
			ventanaSucursales.getBtnAgregarCorreo().setEnabled(false);
			ventanaSucursales.getBtnQuitarCorreo().setEnabled(false);

			String correosActuales = this.ventanaSucursales.getTxtCorreo().getText();
			int cantidadCorreos = 0;
			if (correosActuales != null && !correosActuales.trim().isEmpty()) {
				cantidadCorreos = correosActuales.split("\\n").length;
			}
			if (cantidadCorreos >= 4) {
				JOptionPane.showMessageDialog(null, "Cantidad de correos excedida", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {

			ventanaClientes.getBtnAgregarCorreo().setEnabled(false);
			ventanaClientes.getBtnQuitarCorreo().setEnabled(false);
			// Comprobar cantidad de correos antes de abrir la ventana
			String correosActuales = this.ventanaClientes.getTxtCorreo().getText();
			int cantidadCorreos = 0;
			if (correosActuales != null && !correosActuales.trim().isEmpty()) {
				cantidadCorreos = correosActuales.split("\\n").length;
			}
			if (cantidadCorreos >= 4) {
				JOptionPane.showMessageDialog(null, "Cantidad de correos excedida", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		ventanaAgregarCorreo = new VentanaAgregarCorreo();
		ventanaAgregarCorreo.getBtnAgregarCorreo().addActionListener(this);
		ventanaAgregarCorreo.getBtnCancelar().addActionListener(this);

	}

	private void finalizarOperacionSucursal() {
		limpiarCamposSucursal();
		sucursalElegida = null;
		editandoSucursal = false;

		if (ventanaSucursales != null) {

			ventanaSucursales.getBtnGuardarSucursal().setVisible(false);
			ventanaSucursales.getBtnCancelarSucursal().setVisible(false);
			ventanaSucursales.getBtnAgregar().setEnabled(true);
			ventanaSucursales.getBtnBorrar().setEnabled(true);
			ventanaSucursales.getBtnEditar().setEnabled(true);
			ventanaSucursales.getTablaSucursales().setEnabled(true);
			deshabilitarCamposSucursales(ventanaSucursales);
			llenarTablaSucursales(clienteElegido.getId());
		}

	}

	private void deshabilitarCamposSucursales(VentanaSucursales ventanaSucursales2) {
		ventanaSucursales2.getTxtNombreSucursal().setEditable(false);
		ventanaSucursales2.getTxtDireccion().setEditable(false);
		ventanaSucursales2.getTxtContacto().setEditable(false);
		ventanaSucursales2.getTxtTelContacto().setEditable(false);
		ventanaSucursales2.getBtnAgregarCorreo().setEnabled(false);
		ventanaSucursales2.getBtnQuitarCorreo().setEnabled(false);

	}

	private void limpiarCamposSucursal() {
		this.ventanaSucursales.getTxtNombreSucursal().setText("");
		this.ventanaSucursales.getTxtDireccion().setText("");
		this.ventanaSucursales.getTxtContacto().setText("");
		this.ventanaSucursales.getTxtCorreo().setText("");
		this.ventanaSucursales.getTxtTelContacto().setText("");

	}

	private void habilitarCamposSucursales(VentanaSucursales ventanaSucursales) {
		ventanaSucursales.getTxtNombreSucursal().setEditable(true);
		ventanaSucursales.getTxtDireccion().setEditable(true);
		ventanaSucursales.getTxtContacto().setEditable(true);
		ventanaSucursales.getTxtTelContacto().setEditable(true);
		ventanaSucursales.getBtnAgregarCorreo().setEnabled(true);
		ventanaSucursales.getBtnQuitarCorreo().setEnabled(true);

	}

	// MÉTODO AUXILIAR PARA EVITAR DUPLICACIÓN DE CÓDIGO
	private void finalizarOperacion() {
		limpiarCampos();
		clienteElegido = null;
		editando = false;

		if (llamadoDesdeAgregarEquipo) {

			ventanaClientes.dispose();
			ventanaClientes = null;

			llamadoDesdeAgregarEquipo = false; // Resetea el flag
			
			// Actualizar el combo de clientes en la ventana de agregar equipo
			if (gestorAgregarEquipo != null) {
				try {
					java.lang.reflect.Method method = gestorAgregarEquipo.getClass().getMethod("actualizarComboClientes");
					method.invoke(gestorAgregarEquipo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return;
		}

		ventanaClientes.getBtnGuardar().setVisible(false);
		ventanaClientes.getBtnCancelar().setVisible(false);
		ventanaClientes.getBtnVisualizarSucursales().setVisible(false);
		ventanaClientes.getLblSucursales().setVisible(false);

		ventanaClientes.getBtnAgregar().setEnabled(true);
		ventanaClientes.getBtnBorrar().setEnabled(true);
		ventanaClientes.getBtnEditar().setEnabled(true);
		ventanaClientes.getBtnGenerarSucursales().setEnabled(true);
		ventanaClientes.getTablaClientes().setEnabled(true);

		tablaFiltros.habilitarAutofiltro(this.ventanaClientes.getTablaClientes());
		deshabilitarCampos(ventanaClientes);
		llenarTabla();
	}

	public void habilitarCampos(VentanaClientes ventanaClientes) {

		ventanaClientes.getTxtNombreCliente().setEditable(true);
		ventanaClientes.getTxtCUIT().setEditable(true);
		ventanaClientes.getTxtDireccion().setEditable(true);
		ventanaClientes.getTxtContacto().setEditable(true);
		ventanaClientes.getTxtTelContacto().setEditable(true);
		ventanaClientes.getTxtTelEmpresa().setEditable(true);
		ventanaClientes.getCmbTipoDocumento().setEditable(true);
		ventanaClientes.getCmbTipoDocumento().setEnabled(true);
		ventanaClientes.getCmbCondicionIva().setEditable(true);
		ventanaClientes.getCmbCondicionIva().setEnabled(true);
		ventanaClientes.getRdParticular().setEnabled(true);
		ventanaClientes.getRdEmpresa().setEnabled(true);
		ventanaClientes.getBtnAgregarCorreo().setEnabled(true);
		ventanaClientes.getBtnQuitarCorreo().setEnabled(true);
	}

	public void deshabilitarCampos(VentanaClientes ventanaClientes) {

		ventanaClientes.getTxtNombreCliente().setEditable(false);
		ventanaClientes.getTxtCUIT().setEditable(false);
		ventanaClientes.getTxtDireccion().setEditable(false);
		ventanaClientes.getTxtContacto().setEditable(false);
		ventanaClientes.getTxtTelContacto().setEditable(false);
		ventanaClientes.getTxtTelEmpresa().setEditable(false);
		ventanaClientes.getCmbTipoDocumento().setEditable(false);
		ventanaClientes.getCmbTipoDocumento().setEnabled(false);
		ventanaClientes.getCmbCondicionIva().setEditable(false);
		ventanaClientes.getCmbCondicionIva().setEnabled(false);
		ventanaClientes.getRdParticular().setEnabled(false);
		ventanaClientes.getRdEmpresa().setEnabled(false);
		ventanaClientes.getBtnAgregarCorreo().setEnabled(false);
		ventanaClientes.getBtnQuitarCorreo().setEnabled(false);

	}

	private void limpiarCampos() {

		this.ventanaClientes.getTxtNombreCliente().setText("");
		this.ventanaClientes.getTxtCUIT().setText("");
		this.ventanaClientes.getTxtDireccion().setText("");
		this.ventanaClientes.getTxtContacto().setText("");
		this.ventanaClientes.getTxtTelContacto().setText("");
		this.ventanaClientes.getTxtCorreo().setText("");
		this.ventanaClientes.getTxtTelEmpresa().setText("");
		this.ventanaClientes.getCmbTipoDocumento().setSelectedItem("CUIT");
		this.ventanaClientes.getCmbCondicionIva().setSelectedItem("");
		this.ventanaClientes.getRdEmpresa().setSelected(true);

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

	public VentanaClientes agregarListenersVentanaCliente() {

		if (llamadoDesdeAgregarEquipo) {

			this.ventanaClientes = new VentanaClientes(this);

			llenarTabla();
			limpiarCampos();
			clienteElegido = null;
			editando = false; // Modo agregar, no editar
//
//			// Mostrar botones de guardar y cancelar
			ventanaClientes.getBtnGuardar().setVisible(true);
			ventanaClientes.getBtnCancelar().setVisible(true);

//
//			// Deshabilitar otros botones
			ventanaClientes.getBtnAgregar().setEnabled(false);
			ventanaClientes.getBtnBorrar().setEnabled(false);
			ventanaClientes.getBtnEditar().setEnabled(false);
			ventanaClientes.getBtnGenerarSucursales().setEnabled(false);
			ventanaClientes.getTablaClientes().setEnabled(false);

			// Habilitar campos para edición
			habilitarCampos(ventanaClientes);
			tablaFiltros.deshabilitarAutofiltro(this.ventanaClientes.getTablaClientes());

			System.out.println("MODO AGREGAR ACTIVADO");
		}

		this.ventanaClientes.getBtnAgregar().addActionListener(this);
		this.ventanaClientes.getBtnBorrar().addActionListener(this);
		this.ventanaClientes.getBtnEditar().addActionListener(this);
		this.ventanaClientes.getBtnGuardar().addActionListener(this);
		this.ventanaClientes.getBtnCancelar().addActionListener(this);
		this.ventanaClientes.getTablaClientes().addMouseListener(this);
		this.ventanaClientes.getBtnGenerarSucursales().addActionListener(this);
		this.ventanaClientes.getBtnVisualizarSucursales().addActionListener(this);
		this.ventanaClientes.getBtnAgregarCorreo().addActionListener(this);
		this.ventanaClientes.getBtnQuitarCorreo().addActionListener(this);
		agregarListenerSeleccionTabla();

		return ventanaClientes;

	}

	public VentanaSucursales agregarListenersVentanaSucursales() {

		if (llamadoDesdeVentanaCliente) {

			this.ventanaSucursales = new VentanaSucursales(this);
			ventanaSucursales.getTextCliente().setText(clienteElegido.getRazon_Social());

			llenarTablaSucursales(clienteElegido.getId());

			limpiarCamposSucursal();

			if (cantidadSucursalesXCliente(clienteElegido.getId()) == 1) {

				SucursalesEncliente = this.agenda.obtenerSucursalesxCliente(clienteElegido.getId()).get(0);

				if (SucursalesEncliente.getNombreSucursal().compareTo("") == 0) {

					editandoSucursal = true; // Modo agregar, no editar
					sucursalElegida = SucursalesEncliente;
				} else {

					editandoSucursal = false; // Modo agregar, no editar
					sucursalElegida = null;
				}

			} else {

				editandoSucursal = false; // Modo agregar, no editar
				sucursalElegida = null;
			}

//
//			// Mostrar botones de guardar y cancelar
			ventanaSucursales.getBtnGuardarSucursal().setVisible(true);
			ventanaSucursales.getBtnCancelarSucursal().setVisible(true);

//			// Deshabilitar otros botones
			ventanaSucursales.getBtnAgregar().setEnabled(false);
			ventanaSucursales.getBtnBorrar().setEnabled(false);
			ventanaSucursales.getBtnEditar().setEnabled(false);
			ventanaSucursales.getTablaSucursales().setEnabled(false);

			// Habilitar campos para edición
			habilitarCamposSucursales(ventanaSucursales);
			// tablaFiltros.deshabilitarAutofiltro(this.ventanaSucursales.getTablaSucursales());
			llamadoDesdeVentanaCliente = false; // Resetea el flag

			System.out.println(editandoSucursal);
		}

		this.ventanaSucursales.getBtnEditar().addActionListener(this);
		this.ventanaSucursales.getBtnAgregar().addActionListener(this);
		this.ventanaSucursales.getBtnBorrar().addActionListener(this);
		this.ventanaSucursales.getBtnAgregarCorreo().addActionListener(this);
		this.ventanaSucursales.getBtnQuitarCorreo().addActionListener(this);

		this.ventanaSucursales.getBtnGuardarSucursal().addActionListener(this);
		this.ventanaSucursales.getBtnCancelarSucursal().addActionListener(this);
		this.ventanaSucursales.getTablaSucursales().addMouseListener(this);
		agregarListenerSeleccionTablaSucursal();

		return ventanaSucursales;

	}

	public void agregarListenerSeleccionTabla() {
		ventanaClientes.getTablaClientes().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int i = ventanaClientes.getTablaClientes().getSelectedRow();
				if (i != -1 && !Clientes_en_tabla.isEmpty()) {
					int modelIndex = ventanaClientes.getTablaClientes().convertRowIndexToModel(i);
					actualizarSeleccionCliente(modelIndex);
				}
			}
		});
	}

	private void actualizarSeleccionCliente(int modelIndex) {
		if (!Clientes_en_tabla.isEmpty() && modelIndex < Clientes_en_tabla.size()) {
			clienteElegido = Clientes_en_tabla.get(modelIndex);

			ventanaClientes.getTxtNombreCliente().setText(clienteElegido.getRazon_Social());
			ventanaClientes.getTxtCUIT().setText(clienteElegido.getCUIT());
			ventanaClientes.getTxtDireccion().setText(clienteElegido.getDomicilio());
			ventanaClientes.getTxtContacto().setText(clienteElegido.getContacto());
			ventanaClientes.getTxtTelContacto().setText(clienteElegido.getTelefonoContacto());

			String correos = clienteElegido.getCorreoElectronico();
			if (correos != null && !correos.trim().isEmpty()) {
				correos = correos.replaceAll("\\s*;\\s*", "\n");
			}
			ventanaClientes.getTxtCorreo().setText(correos != null ? correos : "");
			ventanaClientes.getTxtTelEmpresa().setText(clienteElegido.getTelefonoEmpresa());

			// Poblar nuevos campos
			String tipoDoc = clienteElegido.getTipoDocumento();
			if (tipoDoc != null) {
				ventanaClientes.getCmbTipoDocumento().setSelectedItem(tipoDoc);
			} else {
				ventanaClientes.getCmbTipoDocumento().setSelectedItem("CUIT");
			}
			String condIva = clienteElegido.getCondicionIva();
			if (condIva != null && !condIva.isEmpty()) {
				ventanaClientes.getCmbCondicionIva().setSelectedItem(condIva);
			} else {
				ventanaClientes.getCmbCondicionIva().setSelectedIndex(-1);
			}
			String tipoPer = clienteElegido.getTipoPersona();
			if ("particular".equalsIgnoreCase(tipoPer)) {
				ventanaClientes.getRdParticular().setSelected(true);
			} else {
				ventanaClientes.getRdEmpresa().setSelected(true);
			}

			// Lógica de visibilidad de sucursales
			int cantSuc = cantidadSucursalesXCliente(clienteElegido.getId());
			if (cantSuc == 1) {
				SucursalesEncliente = this.agenda.obtenerSucursalesxCliente(clienteElegido.getId()).get(0);
				// Si el cliente tiene una sucursal pero no tiene nombre o nombre == NULL, se asume que es una sucursal "default" y se ocultan los botones de sucursales
				if (SucursalesEncliente.getNombreSucursal() != null && !SucursalesEncliente.getNombreSucursal().trim().isEmpty()) {
					ventanaClientes.getBtnVisualizarSucursales().setVisible(true);
					ventanaClientes.getLblSucursales().setVisible(true);
				} else {
					ventanaClientes.getBtnVisualizarSucursales().setVisible(false);
					ventanaClientes.getLblSucursales().setVisible(false);
				}
			} else if (cantSuc > 1) {
				ventanaClientes.getBtnVisualizarSucursales().setVisible(true);
				ventanaClientes.getLblSucursales().setVisible(true);
			} else {
				ventanaClientes.getBtnVisualizarSucursales().setVisible(false);
				ventanaClientes.getLblSucursales().setVisible(false);
			}
		}
	}

	public void agregarListenerSeleccionTablaSucursal() {
		ventanaSucursales.getTablaSucursales().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int i = ventanaSucursales.getTablaSucursales().getSelectedRow();
				if (i != -1 && !Sucursales_en_tabla.isEmpty()) {
					int modelIndex = ventanaSucursales.getTablaSucursales().convertRowIndexToModel(i);
					if (modelIndex < Sucursales_en_tabla.size()) {
						SucursalDTO sucursal = Sucursales_en_tabla.get(modelIndex);
						ventanaSucursales.getTxtNombreSucursal().setText(sucursal.getNombreSucursal());
						ventanaSucursales.getTxtDireccion().setText(sucursal.getDomicilioSucursal());
						ventanaSucursales.getTxtContacto().setText(sucursal.getContactoSucursal());
						ventanaSucursales.getTxtTelContacto().setText(sucursal.getTelefonoSucursal());

						ventanaSucursales.getTxtCorreo().setText(sucursal.getCorreoElectronico());

					}
				}
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

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
		
		if (this.ventanaClientes != null) {

			if (arg0.getSource() == this.ventanaClientes.getTablaClientes()) {
				int i = this.ventanaClientes.getTablaClientes().getSelectedRow();
				if (i != -1) {
					// Convertir índice de vista a índice del modelo
					int modelIndex = this.ventanaClientes.getTablaClientes().convertRowIndexToModel(i);
					actualizarSeleccionCliente(modelIndex);

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

	// ── Sync con FacturaSoft ───────────────────────────────────────────────────

	/**
	 * Inserta o actualiza un cliente en la base FacturaSoft según ubicación.
	 * Si ya existe por els_referencia → UPDATE; si no → INSERT.
	 */
	private void sincronizarConFacturaSoft(ClienteDTO c) {
		String ubicacion = Conexion.getUbicacionActualStatic();
		String dbFactura = ubicacion.equalsIgnoreCase("Bariloche")
			? "facturacion_db_brc" : "facturacion_db_bsas";

		String host = "localhost";
		String port = "3306";
		String user = "root";
		String pass = "root";
		String opts = "useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbFactura + "?" + opts;

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			// Buscar por els_referencia
			PreparedStatement psSelect = conn.prepareStatement(
				"SELECT id FROM clientes WHERE els_referencia = ?");
			psSelect.setInt(1, c.getId());
			ResultSet rs = psSelect.executeQuery();

			if (rs.next()) {
				// UPDATE
				PreparedStatement psUpd = conn.prepareStatement(
					"UPDATE clientes SET tipo_documento=?, nro_documento=?, razon_social=?, "
					+ "condicion_iva=?, domicilio=?, telefono=?, telefono_contacto=?, email=?, "
					+ "tipo_persona=?, activo=1 WHERE els_referencia=?");
				psUpd.setString(1, c.getTipoDocumento());
				psUpd.setString(2, c.getCUIT());
				psUpd.setString(3, c.getRazon_Social());
				psUpd.setString(4, c.getCondicionIva());
				psUpd.setString(5, c.getDomicilio());
				psUpd.setString(6, c.getTelefonoEmpresa());
				psUpd.setString(7, c.getTelefonoContacto());
				psUpd.setString(8, c.getCorreoElectronico());
				psUpd.setString(9, c.getTipoPersona());
				psUpd.setInt(10, c.getId());
				psUpd.executeUpdate();
				psUpd.close();
				System.out.println("✅ Sync FacturaSoft: actualizado cliente #" + c.getId()
					+ " en " + dbFactura);
			} else {
				// INSERT
				PreparedStatement psIns = conn.prepareStatement(
					"INSERT INTO clientes (tipo_documento, nro_documento, razon_social, "
					+ "condicion_iva, domicilio, telefono, telefono_contacto, email, origen, "
					+ "els_referencia, activo, tipo_persona) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'reparsoft', ?, 1, ?)");
				psIns.setString(1, c.getTipoDocumento());
				psIns.setString(2, c.getCUIT());
				psIns.setString(3, c.getRazon_Social());
				psIns.setString(4, c.getCondicionIva());
				psIns.setString(5, c.getDomicilio());
				psIns.setString(6, c.getTelefonoEmpresa());
				psIns.setString(7, c.getTelefonoContacto());
				psIns.setString(8, c.getCorreoElectronico());
				psIns.setInt(9, c.getId());
				psIns.setString(10, c.getTipoPersona());
				psIns.executeUpdate();
				psIns.close();
				System.out.println("✅ Sync FacturaSoft: insertado cliente #" + c.getId()
					+ " en " + dbFactura);
			}

			rs.close();
			psSelect.close();

		} catch (SQLException e) {
			System.err.println("❌ Error sync FacturaSoft (cliente #" + c.getId() + "): "
				+ e.getMessage());
		}
	}

	/**
	 * Elimina un cliente de FacturaSoft por els_referencia (baja lógica: activo=0).
	 */
	private void eliminarEnFacturaSoft(int idCliente) {
		String ubicacion = Conexion.getUbicacionActualStatic();
		String dbFactura = ubicacion.equalsIgnoreCase("Bariloche")
			? "facturacion_db_brc" : "facturacion_db_bsas";

		String url = "jdbc:mysql://localhost:3306/" + dbFactura
			+ "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";

		try (Connection conn = DriverManager.getConnection(url, "root", "root")) {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE clientes SET activo=0 WHERE els_referencia=?");
			ps.setInt(1, idCliente);
			int affected = ps.executeUpdate();
			ps.close();
			if (affected > 0) {
				System.out.println("✅ Sync FacturaSoft: baja lógica cliente #"
					+ idCliente + " en " + dbFactura);
			} else {
				System.out.println("⚠️ Sync FacturaSoft: cliente #" + idCliente
					+ " no encontrado en " + dbFactura + " (baja omitida)");
			}
		} catch (SQLException e) {
			System.err.println("❌ Error baja FacturaSoft (cliente #" + idCliente + "): "
				+ e.getMessage());
		}
	}

}