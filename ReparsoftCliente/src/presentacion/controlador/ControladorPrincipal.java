package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

import modelo.Agenda;
import modelo.Permisos;
import persistencia.conexion.Conexion;
import presentacion.vista.VistaPrincipal;
import presentacion.vista.VentanaBackUp;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaConfiguracion;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaPresupuestos;
import presentacion.vista.VentanaRolesUsuarios;
import presentacion.vista.VentanaSalidas;
import presentacion.vista.VentanaUbicacionBaseDeDatos;
import presentacion.vista.VentanaBusqueda;

public class ControladorPrincipal implements ActionListener {

	private VistaPrincipal vistaPrincipal;
	private Agenda modelo;

	private VentanaEquipos ventanaEquipos;
	private VentanaClientes ventanaClientes;
	private VentanaSalidas ventanaSalidas;
	private VentanaListadoReparaciones ventanaListadoReparaciones;
	private VentanaConfiguracion ventanaConfiguracion;

	private VentanaBackUp ventanaBackUp;
	private VentanaBusqueda ventanaBusqueda;
	private VentanaPresupuestos ventanaPresupuestos;

	private VentanaRolesUsuarios ventanaRolesUsuarios;

	private ControladorCliente controladorCliente;
	private ControladorSalidas controladorSalidas;
	private ControladorListados controladorListados;
	private ControladorReparacion controladorReparacion;
	private ControladorBackup controladorBackup;
	private ControladorUsuLogin controladorUsuLogin;
	private ControladorUsuarios controladoUsuario;
	private ControladorBusquedas controladorBusqueda;
	private ControladorPresupuestos controladorPresupuestos;
	private ControladorConfiguraciones controladorconfiguraciones;

	private VentanaLogin vistaLogin;

	private String ubicacionDeBase;

	public ControladorPrincipal(VistaPrincipal v, String ubicacionBase) {
		this.vistaPrincipal = v;
		this.ubicacionDeBase = ubicacionBase;

		// Crear el modelo usando el modo actual (normal o antigua)
		this.modelo = crearAgendaActual(ubicacionBase);

		this.vistaPrincipal.getBtncerrarSesion().addActionListener(this);

		this.vistaPrincipal.getBotonEquipos().addActionListener(this);
		this.vistaPrincipal.getBtnSalir().addActionListener(this);
		this.vistaPrincipal.getBotonClientes().addActionListener(this);
		this.vistaPrincipal.getBotonBusquedas().addActionListener(this);
		this.vistaPrincipal.getBotonUsuarios().addActionListener(this);
		this.vistaPrincipal.getBotonSalidas().addActionListener(this);
		this.vistaPrincipal.getBotonListados().addActionListener(this);
		this.vistaPrincipal.getBotonBackUp().addActionListener(this);
		this.vistaPrincipal.getBotonPresupuestos().addActionListener(this);
		this.vistaPrincipal.getBotonConfiguracion().addActionListener(this);

		controladorUsuLogin = new ControladorUsuLogin(new Permisos(ubicacionBase));

		// Actualizar label con información de base actual
		String modo = Conexion.isModoAntigua() ? " - ANTIGUA" : " - NORMAL";
		vistaPrincipal.getTextLugarBaseDatos().setText(ubicacionBase.toUpperCase() + modo);
	}

	/**
	 * Crea una instancia de Agenda respetando siempre el modo actual (Normal / Antigua)
	 */
	private Agenda crearAgendaActual(String ubicacion) {
		return new Agenda(ubicacion, Conexion.isModoAntigua());
	}

	public void inicializar() {
		pedirInicioDeSesion();

		SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
		try {
			SpellChecker.registerDictionaries(new URL("file", null, "./Diccionario/"), "es");
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}

		this.vistaPrincipal.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int opcion = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Desea salir del sistema?", "Aviso",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	private void pedirInicioDeSesion() {
		if (controladorUsuLogin.getUsu_login() == null) {
			vistaLogin = new VentanaLogin();
			vistaLogin.getBtnAceptar().addActionListener(this);
			vistaLogin.getBtnCancelar().addActionListener(this);

			vistaLogin.getTxtUsuPass().addActionListener(e -> {
				controladorUsuLogin.validarSesion(vistaLogin, vistaPrincipal);
				controladorUsuLogin.verificarPermisosMenu(vistaPrincipal);
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == vistaLogin.getBtnAceptar()) {
			controladorUsuLogin.validarSesion(vistaLogin, this.vistaPrincipal);
			controladorUsuLogin.verificarPermisosMenu(vistaPrincipal);

		} else if (arg0.getSource() == vistaLogin.getBtnCancelar()) {
			int opcion = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", "Aviso",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				vistaLogin.getTxtUsuLogin().requestFocus();
			}

		} else if (controladorUsuLogin.getUsu_login() == null) {
			if (vistaLogin != null && vistaLogin.isShowing()) {
				vistaLogin.dispose();
				vistaLogin = null;
			}
			JOptionPane.showMessageDialog(null, "Tiene que iniciar Sesión");
			pedirInicioDeSesion();

		} else if (arg0.getSource() == vistaPrincipal.getBotonUsuarios()) {

			ventanaRolesUsuarios = new VentanaRolesUsuarios(controladoUsuario);
			controladoUsuario = new ControladorUsuarios(ventanaRolesUsuarios, controladorUsuLogin,
					crearAgendaActual(ubicacionDeBase));

		} else if (arg0.getSource() == this.vistaPrincipal.getBtncerrarSesion()) {

			this.controladorUsuLogin.cerrarSesion();
			inicializar();

		} else if (arg0.getSource() == vistaPrincipal.getBotonEquipos()) {

			modelo = crearAgendaActual(ubicacionDeBase);

			ventanaEquipos = new VentanaEquipos(controladorReparacion);
			ventanaPresupuestos = new VentanaPresupuestos(controladorReparacion);
			controladorPresupuestos = new ControladorPresupuestos(ventanaPresupuestos, modelo);

			ventanaSalidas = new VentanaSalidas(controladorSalidas);
			controladorSalidas = new ControladorSalidas(ventanaSalidas, modelo);

			ventanaClientes = new VentanaClientes(controladorCliente);
			controladorCliente = new ControladorCliente(ventanaClientes, modelo);

			controladorReparacion = new ControladorReparacion(ventanaEquipos, controladorUsuLogin, modelo,
					controladorPresupuestos, controladorSalidas, controladorCliente);

			ventanaSalidas.setVisible(false);
			ventanaPresupuestos.setVisible(false);
			ventanaClientes.setVisible(false);

		} else if (arg0.getSource() == vistaPrincipal.getBotonSalidas()) {

			modelo = crearAgendaActual(ubicacionDeBase);
			ventanaSalidas = new VentanaSalidas(controladorSalidas);
			controladorSalidas = new ControladorSalidas(ventanaSalidas, modelo);

		} else if (arg0.getSource() == vistaPrincipal.getBotonClientes()) {

			modelo = crearAgendaActual(ubicacionDeBase);
			ventanaClientes = new VentanaClientes(controladorCliente);
			controladorCliente = new ControladorCliente(ventanaClientes, modelo);

		} else if (arg0.getSource() == vistaPrincipal.getBotonListados()) {

			modelo = crearAgendaActual(ubicacionDeBase);

			ventanaClientes = new VentanaClientes(controladorCliente);
			controladorCliente = new ControladorCliente(ventanaClientes, modelo);

			ventanaListadoReparaciones = new VentanaListadoReparaciones(controladorListados);

			controladorUsuLogin.verificarPermisosVentanaListados(ventanaListadoReparaciones);

			ventanaPresupuestos = new VentanaPresupuestos(controladorReparacion);
			controladorPresupuestos = new ControladorPresupuestos(ventanaPresupuestos, modelo);

			ventanaEquipos = new VentanaEquipos(controladorReparacion);

			ventanaSalidas = new VentanaSalidas(controladorSalidas);

			controladorSalidas = new ControladorSalidas(ventanaSalidas, modelo);

			controladorReparacion = new ControladorReparacion(ventanaEquipos, controladorUsuLogin, modelo,
					controladorPresupuestos, controladorSalidas, controladorCliente);

			controladorListados = new ControladorListados(ventanaListadoReparaciones, modelo, controladorUsuLogin,
					controladorReparacion);
			controladorListados.cerraVentanaListadoEquipos();

			ventanaClientes.setVisible(false);
			ventanaPresupuestos.setVisible(false);
			ventanaEquipos.setVisible(false);
			ventanaSalidas.setVisible(false);

		} else if (arg0.getSource() == vistaPrincipal.getBotonBackUp()) {

			modelo = crearAgendaActual(ubicacionDeBase);
			ventanaBackUp = new VentanaBackUp(controladorBackup);
			controladorBackup = new ControladorBackup(ventanaBackUp, modelo);

		} else if (arg0.getSource() == vistaPrincipal.getBotonBusquedas()) {

			modelo = crearAgendaActual(ubicacionDeBase);

			ventanaClientes = new VentanaClientes(controladorCliente);
			controladorCliente = new ControladorCliente(ventanaClientes, modelo);

			ventanaBusqueda = new VentanaBusqueda(controladorBusqueda);

			controladorUsuLogin.verificarPermisosVentanaListados(ventanaListadoReparaciones);

			ventanaPresupuestos = new VentanaPresupuestos(controladorReparacion);
			controladorPresupuestos = new ControladorPresupuestos(ventanaPresupuestos, modelo);

			ventanaEquipos = new VentanaEquipos(controladorReparacion);

			ventanaSalidas = new VentanaSalidas(controladorSalidas);

			controladorSalidas = new ControladorSalidas(ventanaSalidas, modelo);

			controladorReparacion = new ControladorReparacion(ventanaEquipos, controladorUsuLogin, modelo,
					controladorPresupuestos, controladorSalidas, controladorCliente);

			controladorBusqueda = new ControladorBusquedas(ventanaBusqueda, controladorReparacion,
					crearAgendaActual(ubicacionDeBase));

			ventanaClientes.setVisible(false);
			ventanaPresupuestos.setVisible(false);
			ventanaEquipos.setVisible(false);
			ventanaSalidas.setVisible(false);

		} else if (arg0.getSource() == vistaPrincipal.getBotonPresupuestos()) {

			modelo = crearAgendaActual(ubicacionDeBase);
			ventanaPresupuestos = new VentanaPresupuestos(controladorReparacion);
			controladorPresupuestos = new ControladorPresupuestos(ventanaPresupuestos, modelo);

		} else if (arg0.getSource() == vistaPrincipal.getBtnSalir()) {

			int opcion = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Desea salir del sistema?", "Aviso",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				System.exit(0);
			}

		} else if (arg0.getSource() == vistaPrincipal.getBotonConfiguracion()) {

			ventanaConfiguracion = new VentanaConfiguracion(controladorconfiguraciones);
			controladorconfiguraciones = new ControladorConfiguraciones(ventanaConfiguracion, controladorUsuLogin,
					vistaPrincipal);

		}
	}
}