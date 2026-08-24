package presentacion.controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

import VistaPropias.CodigoSeguridadHandler;
import dto.PermisoDTO;
import dto.ReparacionDTO;
import util.Config;
import util.CryptoUtil;
import dto.RolDTO;
import dto.UsuarioDTO;
import modelo.Agenda;
import modelo.Permisos;
import persistencia.dao.mysql.LogDAO;
import presentacion.vista.VentanaRolesUsuarios;
import presentacion.vista.VentanaCodigoSeguridad;
import presentacion.vista.VentanaPermisos;

public class ControladorUsuarios implements ActionListener, MouseListener {

	private VentanaPermisos ventanaPermisos;
	private Permisos permisos;
	private VentanaRolesUsuarios ventanaRolesUsuarios;
	// private List<UsuarioDTO> profesionales_en_tabla;
	private List<PermisoDTO> permisos_en_tabla;
	private List<PermisoDTO> permisosFaltantes_en_tabla;
	private List<RolDTO> roles_en_tabla;
	private List<UsuarioDTO> usuarios_en_tabla;
	private Agenda agenda;
	// private UsuarioDTO user;
	private RolDTO rolElegido;
	private UsuarioDTO usuarioElegido;
    private UsuarioDTO usuariologueado = null;
    private int idRolActual = -1;
    
	private List<ReparacionDTO> Reparaciones;
	private boolean passwordVisible = false;
	private static final String PASSWORD_PLACEHOLDER = "\u2022\u2022\u2022\u2022\u2022\u2022"; // "••••••"

	/**
	 * Devuelve la contraseña escrita por el usuario, o cadena vacía si el campo
	 * está vacío o contiene el placeholder visual de seis puntos (no es una
	 * contraseña real). Se usa al crear/editar usuarios para no guardar los
	 * puntos como contraseña literal.
	 */
	static String passwordReal(String valorCampo) {
		if (valorCampo == null) {
			return "";
		}
		String limpio = valorCampo.trim();
		return limpio.equals(PASSWORD_PLACEHOLDER) ? "" : limpio;
	}
	private ControladorUsuLogin controladorUsuLogin; 
	private List<RolDTO> rolesMostradosEnCombo;

	private VentanaCodigoSeguridad ventanaCodigoSeguridad;

//	private final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[com]{2,})$";

	private final String PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

	public ControladorUsuarios(VentanaRolesUsuarios ventanaRolesUsuarios,ControladorUsuLogin controladorUsuLogin, Agenda agenda) {
		this.ventanaRolesUsuarios = ventanaRolesUsuarios;

		this.agenda = agenda;
		this.permisos = new Permisos(agenda.getUbicacionBase());
		// this.user = null;
		this.rolElegido = null;
		this.usuarioElegido = null;
		this.controladorUsuLogin = controladorUsuLogin;

		rolElegido = null;
		usuarioElegido = null;
		
		usuariologueado = controladorUsuLogin.getUsu_login();

	    idRolActual = usuariologueado != null ? usuariologueado.getIdRol() : 0;	
	    
	    rolesMostradosEnCombo = new ArrayList<>();

		this.ventanaRolesUsuarios.getBtnAgregarUsuario().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnEditarUsuario().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnGuardarEdicion().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnCancelarEdicion().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnGuardarNuevo().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnCancelarNuevo().addActionListener(this);
		this.ventanaRolesUsuarios.getTablaUsuarios().addMouseListener(this);
		this.ventanaRolesUsuarios.getComboRoles().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnEliminarUsuario().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnPermisosXrol().addActionListener(this);
		this.ventanaRolesUsuarios.getBtnMostrarContraseña().addActionListener(this);
		
		

		performActionOnTextComponents(ventanaRolesUsuarios);

		llenarComboRoles();
		llenarTablaUsuarios();

	}

	// devuelve la fecha del dia
	public String fechaActual() {
		java.util.Date fecha = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechas = dateFormat.format(fecha);
		return fechas;
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {

		if (ventanaRolesUsuarios != null) {

			if (e.getSource() == this.ventanaRolesUsuarios.getComboRoles()) {

				int i = this.ventanaRolesUsuarios.getComboRoles().getSelectedIndex() + 1;
				rolElegido = roles_en_tabla.get(i);

			}

			else if (e.getSource() == this.ventanaRolesUsuarios.getBtnGuardarNuevo()) {
				usuarioElegido = null;

				if (rolElegido == null) {
					this.ventanaRolesUsuarios.getErrorMsj("Seleccione un rol");
				} else if (this.ventanaRolesUsuarios.getTxtNombreUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtApellidoUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtDNI().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtTelefonoUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtEmailUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtLogin().getText().equals("")) {
					this.ventanaRolesUsuarios.getErrorMsj("Todos los campos son obligatorios");
				} else if (!validacionMail(ventanaRolesUsuarios.getTxtEmailUsuario().getText())) {

					JOptionPane.showMessageDialog(null, "Escriba un email correcto",
							"Error al registrar una dirección de email", JOptionPane.ERROR_MESSAGE);
				} else if (existeUsuario(Integer.parseInt(this.ventanaRolesUsuarios.getTxtDNI().getText()))) {
					JOptionPane.showMessageDialog(null,
							"Los campos DNI y Login no pueden estar repetidos con otro usuario", "CAMPOS REPETIDOS CON OTRO USUARIO",
							JOptionPane.ERROR_MESSAGE);
				} else {

					String nuevaPass = passwordReal(this.ventanaRolesUsuarios.getTxtPass().getText());
					if (nuevaPass.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"La contraseña es obligatoria para nuevos usuarios", "CAMPO VACÍO",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					UsuarioDTO nuevoUsuario = new UsuarioDTO(0, rolElegido.getIdRol() + 1,
							Integer.parseInt(this.ventanaRolesUsuarios.getTxtDNI().getText()),
							this.ventanaRolesUsuarios.getTxtNombreUsuario().getText(),
							this.ventanaRolesUsuarios.getTxtApellidoUsuario().getText(),
							this.ventanaRolesUsuarios.getTxtTelefonoUsuario().getText(),
							this.ventanaRolesUsuarios.getTxtEmailUsuario().getText(),
							this.ventanaRolesUsuarios.getTxtLogin().getText(),
							nuevaPass);

					agenda.agregarUsuario(nuevoUsuario);
					llenarTablaUsuarios();
					
				
					this.ventanaRolesUsuarios.getBtnGuardarNuevo().setVisible(false);
					this.ventanaRolesUsuarios.getBtnPermisosXrol().setVisible(true);
					this.ventanaRolesUsuarios.getBtnCancelarNuevo().setVisible(false);

					this.ventanaRolesUsuarios.getBtnEliminarUsuario().setEnabled(true);
					this.ventanaRolesUsuarios.getBtnAgregarUsuario().setEnabled(true);
					this.ventanaRolesUsuarios.getBtnEditarUsuario().setEnabled(true);

					this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(false);
					this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(false);
					this.ventanaRolesUsuarios.getTxtDNI().setEditable(false);
					this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(false);
					this.ventanaRolesUsuarios.getTxtLogin().setEditable(false);
					this.ventanaRolesUsuarios.getTxtPass().setEditable(false);
					this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(false);
					this.ventanaRolesUsuarios.getComboRoles().setVisible(false);
					
					
					this.ventanaRolesUsuarios.getTxtNombreUsuario().setText("");
					this.ventanaRolesUsuarios.getTxtApellidoUsuario().setText("");
					this.ventanaRolesUsuarios.getTxtDNI().setText("");
					this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setText("");
					this.ventanaRolesUsuarios.getTxtEmailUsuario().setText("");
					this.ventanaRolesUsuarios.getTxtLogin().setText("");
					this.ventanaRolesUsuarios.getTxtPass().setText("••••••");
					this.ventanaRolesUsuarios.getTextRol().setText("");

					usuarioElegido = null;
					rolElegido = null;
				}
			}

			else if (e.getSource() == this.ventanaRolesUsuarios.getBtnCancelarNuevo()) {

				this.ventanaRolesUsuarios.getTxtNombreUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtApellidoUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtDNI().setText("");
				this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtEmailUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtLogin().setText("");
				this.ventanaRolesUsuarios.getTxtPass().setText("••••••");
				this.ventanaRolesUsuarios.getTextRol().setText("");
				usuarioElegido = null;
				rolElegido = null;

				this.ventanaRolesUsuarios.getBtnGuardarNuevo().setVisible(false);
				this.ventanaRolesUsuarios.getBtnPermisosXrol().setVisible(true);
				this.ventanaRolesUsuarios.getBtnCancelarNuevo().setVisible(false);

				this.ventanaRolesUsuarios.getBtnEliminarUsuario().setEnabled(true);
				this.ventanaRolesUsuarios.getBtnAgregarUsuario().setEnabled(true);
				this.ventanaRolesUsuarios.getBtnEditarUsuario().setEnabled(true);

				this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtDNI().setEditable(false);
				this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtLogin().setEditable(false);
				this.ventanaRolesUsuarios.getTxtPass().setEditable(false);
				this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getComboRoles().setVisible(false);

			}

			else if (e.getSource() == this.ventanaRolesUsuarios.getBtnAgregarUsuario()) {

				this.ventanaRolesUsuarios.getBtnGuardarNuevo().setVisible(true);
				this.ventanaRolesUsuarios.getBtnCancelarNuevo().setVisible(true);
				this.ventanaRolesUsuarios.getBtnPermisosXrol().setVisible(false);
				this.ventanaRolesUsuarios.getBtnEliminarUsuario().setEnabled(false);
				this.ventanaRolesUsuarios.getBtnEditarUsuario().setEnabled(false);
				this.ventanaRolesUsuarios.getBtnAgregarUsuario().setEnabled(false);

				this.ventanaRolesUsuarios.getTxtNombreUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtApellidoUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtDNI().setText("");
				this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtEmailUsuario().setText("");
				this.ventanaRolesUsuarios.getTxtLogin().setText("");
				// Campo vacío para que el usuario escriba: con visualización
				// deshabilitada se escribe en puntos (echoChar), con visualización
				// habilitada se ven los caracteres reales.
				this.ventanaRolesUsuarios.getTxtPass().setText("");
				this.ventanaRolesUsuarios.getTxtPass().setEchoChar(passwordVisible ? (char) 0 : '•');
				this.ventanaRolesUsuarios.getTextRol().setText("");

				usuarioElegido = null;
				rolElegido = null;

				this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(true);
				this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(true);
				this.ventanaRolesUsuarios.getTxtDNI().setEditable(true);
				this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(true);
				this.ventanaRolesUsuarios.getTxtLogin().setEditable(true);
				this.ventanaRolesUsuarios.getTxtPass().setEditable(true);
				this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(true);
				this.ventanaRolesUsuarios.getComboRoles().setEnabled(true);
				this.ventanaRolesUsuarios.getComboRoles().setVisible(true);
				this.ventanaRolesUsuarios.getComboRoles().setSelectedItem(null);

				this.ventanaRolesUsuarios.getTxtNombreUsuario().requestFocus();

			}

			else if (e.getSource() == this.ventanaRolesUsuarios.getBtnGuardarEdicion()) {

				if (this.ventanaRolesUsuarios.getTxtNombreUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtApellidoUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtDNI().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtTelefonoUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtEmailUsuario().getText().equals("")
						|| this.ventanaRolesUsuarios.getTxtLogin().getText().equals("")) {
					this.ventanaRolesUsuarios.getErrorMsj("Todos los campos son obligatorios");
				} else if (!validacionMail(ventanaRolesUsuarios.getTxtEmailUsuario().getText())) {

					JOptionPane.showMessageDialog(null, "Escriba un email correcto",
							"Error al registrar una dirección de email", JOptionPane.ERROR_MESSAGE);
				} else if (existeUsuarioEditar(Integer.parseInt(this.ventanaRolesUsuarios.getTxtDNI().getText()))) {
					JOptionPane.showMessageDialog(null,
							"Los campos, DNI, Login no pueden estar repetidos con otro usuario", "El dni ya existe",
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (usuarioElegido != null) {
						usuarioElegido.setIdRol(rolElegido.getIdRol() + 1);
						usuarioElegido.setNombre(this.ventanaRolesUsuarios.getTxtNombreUsuario().getText());
						usuarioElegido.setApellido(this.ventanaRolesUsuarios.getTxtApellidoUsuario().getText());
						usuarioElegido.setDni(Integer.parseInt(this.ventanaRolesUsuarios.getTxtDNI().getText()));
						usuarioElegido.setTelefono(this.ventanaRolesUsuarios.getTxtTelefonoUsuario().getText());
						usuarioElegido.setEmail(this.ventanaRolesUsuarios.getTxtEmailUsuario().getText());
						usuarioElegido.setLogin(this.ventanaRolesUsuarios.getTxtLogin().getText());
						String nuevaPass = passwordReal(this.ventanaRolesUsuarios.getTxtPass().getText());
						if (!nuevaPass.isEmpty()) {
							usuarioElegido.setPass(nuevaPass); // se hasheará en DAO
						}

						agenda.editarUsuario(usuarioElegido);
						llenarTablaUsuarios();

						this.ventanaRolesUsuarios.getBtnGuardarEdicion().setVisible(false);
						this.ventanaRolesUsuarios.getBtnCancelarEdicion().setVisible(false);
						this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(false);
						this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(false);
						this.ventanaRolesUsuarios.getTxtDNI().setEditable(false);
						this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(false);
						this.ventanaRolesUsuarios.getTxtLogin().setEditable(false);
						this.ventanaRolesUsuarios.getTxtPass().setEditable(false);
						this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(false);
						String nombreRol = this.ventanaRolesUsuarios.getComboRoles().getSelectedItem().toString();
						this.ventanaRolesUsuarios.getTextRol().setText(nombreRol);
						this.ventanaRolesUsuarios.getTextRol().setEditable(false);
						this.ventanaRolesUsuarios.getComboRoles().setEnabled(false);
						this.ventanaRolesUsuarios.getComboRoles().setVisible(false);
						this.ventanaRolesUsuarios.getBtnPermisosXrol().setVisible(true);
						this.ventanaRolesUsuarios.getBtnAgregarUsuario().setEnabled(true);

						this.ventanaRolesUsuarios.getBtnEliminarUsuario().setEnabled(true);
						this.ventanaRolesUsuarios.getBtnEditarUsuario().setEnabled(true);

						usuarioElegido = null;
						rolElegido = null;

						JOptionPane.showMessageDialog(null, new JLabel("Usuario Editado"), "Edición Exitosa",
								JOptionPane.INFORMATION_MESSAGE);

					}
				}
			} else if (e.getSource() == this.ventanaRolesUsuarios.getBtnEditarUsuario()) {

				if (usuarioElegido == null) {
					this.ventanaRolesUsuarios.getErrorMsj("Seleccione un usuario");
				} else if (usuarioElegido.getIdRol() == 1) {

					JOptionPane.showMessageDialog(null,
							"No se puede Editar al usuario cliente 'Administrador Programador'", "Error de edición",
							JOptionPane.INFORMATION_MESSAGE);
				} else {

					this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(true);
					this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(true);
					this.ventanaRolesUsuarios.getTxtDNI().setEditable(true);
					this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(true);
					this.ventanaRolesUsuarios.getTxtLogin().setEditable(true);
					this.ventanaRolesUsuarios.getTxtPass().setEditable(true);
					this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(true);
					this.ventanaRolesUsuarios.getComboRoles().setVisible(true);
					this.ventanaRolesUsuarios.getComboRoles().setEnabled(true);
					this.ventanaRolesUsuarios.getComboRoles().setForeground(Color.BLACK);
				    
					this.ventanaRolesUsuarios.getComboRoles().setSelectedIndex(usuarioElegido.getIdRol()-2);

					this.ventanaRolesUsuarios.getBtnGuardarEdicion().setVisible(true);
					this.ventanaRolesUsuarios.getBtnCancelarEdicion().setVisible(true);
					this.ventanaRolesUsuarios.getBtnPermisosXrol().setVisible(false);
					this.ventanaRolesUsuarios.getBtnAgregarUsuario().setEnabled(false);
					this.ventanaRolesUsuarios.getBtnEliminarUsuario().setEnabled(false);

				}
			} else if (e.getSource() == this.ventanaRolesUsuarios.getBtnCancelarEdicion()) {

				this.ventanaRolesUsuarios.getBtnGuardarEdicion().setVisible(false);
				this.ventanaRolesUsuarios.getBtnCancelarEdicion().setVisible(false);

				this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtDNI().setEditable(false);
				this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtLogin().setEditable(false);
				this.ventanaRolesUsuarios.getTxtPass().setEditable(false);
				this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getComboRoles().setEnabled(false);
				this.ventanaRolesUsuarios.getComboRoles().setVisible(false);
				this.ventanaRolesUsuarios.getBtnPermisosXrol().setVisible(true);

				this.ventanaRolesUsuarios.getBtnEliminarUsuario().setEnabled(true);
				this.ventanaRolesUsuarios.getBtnAgregarUsuario().setEnabled(true);

			}

			else if (e.getSource() == this.ventanaRolesUsuarios.getBtnEliminarUsuario()) {
				if (usuarioElegido == null) {
					this.ventanaRolesUsuarios.getErrorMsj("Seleccione un usuario");
				}

				else if (usuarioElegido.getIdRol() == 1) {

					JOptionPane.showMessageDialog(null,
							"No se puede eliminar al usuario cliente 'Administrador Programador'", "Error de edición",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int seleccion = JOptionPane.showConfirmDialog(ventanaRolesUsuarios,
							"¿Está seguro de realizar la operación?", "Confirmación", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (seleccion == JOptionPane.YES_OPTION) {

						int[] filas_seleccionadas = this.ventanaRolesUsuarios.getTablaUsuarios().getSelectedRows();
						for (int fila : filas_seleccionadas) {

							int idTecnico = usuarios_en_tabla.get(fila).getIdUsuario();

							editarReparacionesSinTecnico(idTecnico);
							agenda.borrarUsuario(usuarios_en_tabla.get(fila));

						}

						llenarTablaUsuarios();
						this.ventanaRolesUsuarios.getTxtNombreUsuario().setText("");
						this.ventanaRolesUsuarios.getTxtApellidoUsuario().setText("");
						this.ventanaRolesUsuarios.getTxtDNI().setText("");
						this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setText("");
						this.ventanaRolesUsuarios.getTxtEmailUsuario().setText("");
						this.ventanaRolesUsuarios.getTextRol().setText("");
						this.ventanaRolesUsuarios.getTxtLogin().setText("");
						this.ventanaRolesUsuarios.getTxtPass().setText("••••••");

						usuarioElegido = null;
						rolElegido = null;
					}
				}
			}

			else if (e.getSource() == this.ventanaRolesUsuarios.getBtnPermisosXrol()) {

				this.ventanaRolesUsuarios.getBtnGuardarEdicion().setVisible(false);
				this.ventanaRolesUsuarios.getBtnCancelarEdicion().setVisible(false);
				this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtDNI().setEditable(false);
				this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getTxtLogin().setEditable(false);
				this.ventanaRolesUsuarios.getTxtPass().setEditable(false);
				this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(false);
				this.ventanaRolesUsuarios.getComboRoles().setEnabled(false);

				ventanaPermisos = new VentanaPermisos(this);

				this.ventanaPermisos.getCmbRoles().addActionListener(this);
				this.ventanaPermisos.getBtnAgregar().addActionListener(this);
				this.ventanaPermisos.getBtnRemover().addActionListener(this);

				llenarCombosRoles();

			}

			else if (e.getSource() == this.ventanaRolesUsuarios.getBtnMostrarContraseña()) {

				if (usuarioElegido != null && usuarioElegido.getIdRol() == 1) {

					ventanaCodigoSeguridad = new VentanaCodigoSeguridad();
					ventanaCodigoSeguridad.getBtnAceptar().addActionListener(this::accionAceptar);
					ventanaCodigoSeguridad.getBtnCancelar().addActionListener(f -> cerrarVentana());

					ventanaCodigoSeguridad.getTxtCodigoSeguridad().addActionListener(f -> {
						char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();
						String codigo = new String(codigoIngresado);

						if (verificarCodigoSeguridad(codigo)) {

							mostrarOcultar();

						}
					});

				} else {
					mostrarOcultar();

				}

			}

			if (ventanaPermisos != null) {
				if (e.getSource() == ventanaPermisos.getCmbRoles()) {

					cargarTablaPermisos();

				} else if (e.getSource() == ventanaPermisos.getBtnAgregar()) {
					if (ventanaPermisos.getCmbRoles().getSelectedIndex() != 0) {
						guardarPermisos();
					}

				} else if (e.getSource() == ventanaPermisos.getBtnRemover()) {

					if (ventanaPermisos.getCmbRoles().getSelectedIndex() != 0) {
						borrarPermiso();
					}

				}
			}

		}

	}

	public void mostrarOcultar() {

		if (passwordVisible) {
			// Si la contraseña es visible, ocultarla (puntos)
			ventanaRolesUsuarios.getTxtPass().setEchoChar('\u2022');
			ventanaRolesUsuarios.getTxtPass().setText("\u2022\u2022\u2022\u2022\u2022\u2022");
			passwordVisible = false;

		} else {
			// Si la contraseña está oculta, mostrar la real (desencriptada)
			if (usuarioElegido != null && usuarioElegido.getPass() != null && !usuarioElegido.getPass().isEmpty()) {
				String stored = usuarioElegido.getPass();
				String realPass;
				// Desencriptar según formato
				if (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
					// BCrypt legacy - no se puede desencriptar
					realPass = "********";
				} else if (CryptoUtil.isLegacyBcrypt(stored)) {
					realPass = "********";
				} else {
					// AES encriptado
					realPass = CryptoUtil.decrypt(stored);
				}
				ventanaRolesUsuarios.getTxtPass().setText(realPass);
			}
			ventanaRolesUsuarios.getTxtPass().setEchoChar((char) 0);
			passwordVisible = true;
		}

	}

	// Verifica el código de seguridad
	protected boolean verificarCodigoSeguridad(String codigo) {
		if (codigo.compareTo(Config.get("security.codigo.acceso", "0000")) == 0) {

			ventanaCodigoSeguridad.dispose();
			ventanaCodigoSeguridad = null;
			return true;

		} else {
			JOptionPane.showMessageDialog(null, "Código Incorrecto!", "Acceso denegado",
					JOptionPane.INFORMATION_MESSAGE);

			return false;
		}
	}

	// Acciones al presionar el botón Aceptar
	private void accionAceptar(ActionEvent e) {

		char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();
		String codigo = new String(codigoIngresado);

		verificarCodigoSeguridad(codigo);

		cerrarVentana();

	}

	// Cierra la ventana de código de seguridad
	private void cerrarVentana() {
		if (ventanaCodigoSeguridad != null) {
			ventanaCodigoSeguridad.dispose();
			ventanaCodigoSeguridad = null;
		}
	}

	private void editarReparacionesSinTecnico(int idTecnico) {

		int idUsuarioBorrado = 1;
		int actualizadas = this.agenda.reasignarReparacionesDeUsuario(idUsuarioBorrado, idTecnico);
		LogDAO.info("Reasignadas " + actualizadas + " reparaciones del técnico eliminado " + idTecnico
				+ " al usuario " + idUsuarioBorrado);

	}

	private void borrarPermiso() {

		// TODO Auto-generated method stub
		int pos = ventanaPermisos.getModelPermisosTenidos().getRowCount();

		if (pos > 0) {
			for (int i = 0; i < permisos_en_tabla.size(); i++) {
				boolean valor = (boolean) ventanaPermisos.getModelPermisosTenidos().getValueAt(i, 0);
				if (valor)
					permisos.borrarPermiso(permisos_en_tabla.get(i));
			}
		}
		cargarTablaPermisos();
	}

	private void guardarPermisos() {
		// TODO Auto-generated method stub

		int pos = ventanaPermisos.getModelPermisosFaltantes().getRowCount();

		if (pos > 0) {
			for (int i = 0; i < permisosFaltantes_en_tabla.size(); i++) {
				boolean valor = (boolean) ventanaPermisos.getModelPermisosFaltantes().getValueAt(i, 0);
				if (valor)
					permisos.agregarPermiso(permisosFaltantes_en_tabla.get(i));
			}
		}
		cargarTablaPermisos();
	}

	private void cargarTablaPermisos() {
	    int pos = ventanaPermisos.getCmbRoles().getSelectedIndex();

	    if (pos >= 0 && pos < rolesMostradosEnCombo.size()) {
	        while (ventanaPermisos.getModelPermisosTenidos().getRowCount() > 0) {
	            ventanaPermisos.getModelPermisosTenidos().removeRow(0);
	        }
	        while (ventanaPermisos.getModelPermisosFaltantes().getRowCount() > 0) {
	            ventanaPermisos.getModelPermisosFaltantes().removeRow(0);
	        }

	        int idRolSeleccionado = rolesMostradosEnCombo.get(pos).getIdRol();
	        permisos_en_tabla = permisos.damePermisos(idRolSeleccionado);
	        permisosFaltantes_en_tabla = permisos.damePermisosFaltantes(idRolSeleccionado);

	        for (PermisoDTO per : permisos_en_tabla) {
	            Object[] fila = { false, per.getNombrePantalla(), per.getNombrePantallaPadre() };
	            ventanaPermisos.getModelPermisosTenidos().addRow(fila);
	        }
	        for (PermisoDTO per : permisosFaltantes_en_tabla) {
	            Object[] fila = { false, per.getNombrePantalla(), per.getNombrePantallaPadre() };
	            ventanaPermisos.getModelPermisosFaltantes().addRow(fila);
	        }
	    }

	    ventanaPermisos.setCellRender(this.ventanaPermisos.getTblPermisosFaltantes());
	    ventanaPermisos.setCellRender(this.ventanaPermisos.getTblPermisosTenidos());
	    }

	
	
	@SuppressWarnings("unchecked")
	private void llenarCombosRoles() {
	    ventanaPermisos.getCmbRoles().removeAllItems();
	    roles_en_tabla = agenda.obtenerRoles();
	    rolesMostradosEnCombo.clear();

	    if (idRolActual == 1) {
	        // Mostrar todos los roles
	        for (RolDTO rol : roles_en_tabla) {
	            ventanaPermisos.getCmbRoles().addItem(rol.getNombre());
	            rolesMostradosEnCombo.add(rol);
	        }
	    } else {
	        // Omitir los dos primeros roles
	        for (int i = 2; i < roles_en_tabla.size(); i++) {
	            ventanaPermisos.getCmbRoles().addItem(roles_en_tabla.get(i).getNombre());
	            rolesMostradosEnCombo.add(roles_en_tabla.get(i));
	        }
	    }
	    System.out.println("Usuario logueado: " + usuariologueado.getNombre());
    }

	@SuppressWarnings("unused")
	private boolean existeRol(String s) {

		if (roles_en_tabla.size() == 0)
			return false;
		else if (rolElegido != null && rolElegido.getNombre().equals(s)) {
			return false;
		} else {
			for (int i = 0; i < roles_en_tabla.size(); i++) {
				if (roles_en_tabla.get(i).getNombre().equals(s))
					return true;
			}
			return false;
		}
	}

	private boolean existeUsuario(int dni) {
		if (usuarios_en_tabla == null) {
			return false;
		} else if (usuarios_en_tabla.size() == 0)
			return false;

		for (int i = 0; i < usuarios_en_tabla.size(); i++) {
			if (usuarios_en_tabla.get(i).getDni() == dni)
				return true;
			else if (usuarios_en_tabla.get(i).getLogin().equals(this.ventanaRolesUsuarios.getTxtLogin().getText()))
				return true;
		}

		return false;

	}

	private boolean existeUsuarioEditar(int dni) {

		if (usuarios_en_tabla == null)
			return false;
		else if (usuarios_en_tabla.size() == 0)
			return false;
		else if (usuarioElegido.getDni() == dni)
			return false;
		else if (usuarioElegido.getLogin().equals(this.ventanaRolesUsuarios.getTxtLogin().getText()))
			return false;

		for (int i = 0; i < usuarios_en_tabla.size(); i++) {
			if (usuarios_en_tabla.get(i).getDni() == dni)
				return true;
			else if (usuarios_en_tabla.get(i).getLogin().equals(this.ventanaRolesUsuarios.getTxtLogin().getText()))
				return true;
		}

		return false;

	}

	boolean validacionMail(String email) {

		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@SuppressWarnings("unused")
	private String carregirFecha(String fechaTurno2) {
		String fechacorrecta = "";
		String dia = "";
		String mes = "";
		String anio = "";

		for (int i = 0; i < 4; i++) {
			anio = anio + fechaTurno2.charAt(i);
		}
		for (int i = 4; i < 6; i++) {
			mes = mes + fechaTurno2.charAt(i);
		}
		for (int i = 6; i < 8; i++) {
			dia = dia + fechaTurno2.charAt(i);
		}

		fechacorrecta = dia + "/" + mes + "/" + anio;
		return fechacorrecta;

	}

	@SuppressWarnings("unchecked")
	public void llenarComboRoles() {

		this.ventanaRolesUsuarios.getComboRoles().removeAllItems();

		this.roles_en_tabla = agenda.obtenerRoles();

		for (int i = 0; i < this.roles_en_tabla.size(); i++) {

			if (roles_en_tabla.get(i).getIdRol() != 0 && roles_en_tabla.get(i).getIdRol() != 1) {

				this.ventanaRolesUsuarios.getComboRoles().addItem(this.roles_en_tabla.get(i).getNombre());

			}
		}

	}

	@SuppressWarnings("deprecation")
	public void llenarTablaUsuarios() {

		this.ventanaRolesUsuarios.getModelUsuarios().setRowCount(0); // Para
																		// vaciar
																		// la
																		// tabla
		this.ventanaRolesUsuarios.getModelUsuarios().setColumnCount(0);
		this.ventanaRolesUsuarios.getModelUsuarios()
				.setColumnIdentifiers(this.ventanaRolesUsuarios.getNombreColumnasUsuarios());

		this.usuarios_en_tabla = agenda.obtenerUsuarios();

		for (int i = 0; i < this.usuarios_en_tabla.size(); i++) {

			Object[] fila = {
					this.usuarios_en_tabla.get(i).getApellido() + ", " + this.usuarios_en_tabla.get(i).getNombre(),
					this.usuarios_en_tabla.get(i).getDni() };

			this.ventanaRolesUsuarios.getModelUsuarios().addRow(fila);
		}

		ventanaRolesUsuarios.setCellRender(this.ventanaRolesUsuarios.getTablaUsuarios());

		this.ventanaRolesUsuarios.show();
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub


	}

	@SuppressWarnings("unused")
	private void cargarUsuarios() {

		int y = 0;
		while (ventanaRolesUsuarios.getModelUsuarios().getRowCount() > 0) {
			ventanaRolesUsuarios.getModelUsuarios().removeRow(y);
		}
		if (rolElegido != null) {
			usuarios_en_tabla = agenda.obtenerUsuariosXrol(rolElegido.getIdRol());
			for (int i = 0; i < usuarios_en_tabla.size(); i++) {

				Object[] fila = { rolElegido.getNombre(),
						usuarios_en_tabla.get(i).getApellido() + ", " + usuarios_en_tabla.get(i).getNombre(),
						usuarios_en_tabla.get(i).getDni() };
				this.ventanaRolesUsuarios.getModelUsuarios().addRow(fila);
			}
		}

		this.ventanaRolesUsuarios.getTxtNombreUsuario().setText("");
		this.ventanaRolesUsuarios.getTxtApellidoUsuario().setText("");
		this.ventanaRolesUsuarios.getTxtDNI().setText("");
		this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setText("");
		this.ventanaRolesUsuarios.getTxtEmailUsuario().setText("");

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
		// Rol y usuarios
		if (this.ventanaRolesUsuarios != null) {
			if (arg0.getSource() == this.ventanaRolesUsuarios.getTablaUsuarios()) {
				int i = this.ventanaRolesUsuarios.getTablaUsuarios().getSelectedRow();
				if (i != -1) {
					int modelIndex = this.ventanaRolesUsuarios.getTablaUsuarios().convertRowIndexToModel(i);
					if (!usuarios_en_tabla.isEmpty()&& modelIndex < usuarios_en_tabla.size()) {
						usuarioElegido = usuarios_en_tabla.get(modelIndex);
						
						int indiceRol = usuarioElegido.getIdRol();

						this.ventanaRolesUsuarios.getComboRoles().setVisible(false);
						this.ventanaRolesUsuarios.getTxtNombreUsuario().setText(usuarioElegido.getNombre());
						this.ventanaRolesUsuarios.getTxtApellidoUsuario().setText(usuarioElegido.getApellido());
						this.ventanaRolesUsuarios.getTxtDNI().setText("" + usuarioElegido.getDni());
						this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setText(usuarioElegido.getTelefono());
						this.ventanaRolesUsuarios.getTxtEmailUsuario().setText(usuarioElegido.getEmail());
						this.ventanaRolesUsuarios.getTxtLogin().setText(usuarioElegido.getLogin());
						// Cargar 6 puntos fijos por defecto
						this.ventanaRolesUsuarios.getTxtPass().setText("••••••");
						ventanaRolesUsuarios.getTxtPass().setEchoChar('\u2022');
						passwordVisible = false;

						this.ventanaRolesUsuarios.getTextRol().setText(agenda.obtenerRolXid(indiceRol));

						this.ventanaRolesUsuarios.getBtnGuardarEdicion().setVisible(false);
						this.ventanaRolesUsuarios.getBtnCancelarEdicion().setVisible(false);
						this.ventanaRolesUsuarios.getBtnGuardarNuevo().setVisible(false);
						this.ventanaRolesUsuarios.getBtnCancelarNuevo().setVisible(false);

						this.ventanaRolesUsuarios.getBtnEliminarUsuario().setEnabled(true);
						this.ventanaRolesUsuarios.getBtnEditarUsuario().setEnabled(true);
						this.ventanaRolesUsuarios.getBtnAgregarUsuario().setEnabled(true);

						this.ventanaRolesUsuarios.getTxtNombreUsuario().setEditable(false);
						this.ventanaRolesUsuarios.getTxtApellidoUsuario().setEditable(false);
						this.ventanaRolesUsuarios.getTxtDNI().setEditable(false);
						this.ventanaRolesUsuarios.getTxtEmailUsuario().setEditable(false);
						this.ventanaRolesUsuarios.getTxtLogin().setEditable(false);
						this.ventanaRolesUsuarios.getTxtPass().setEditable(false);
						this.ventanaRolesUsuarios.getTxtTelefonoUsuario().setEditable(false);

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

}