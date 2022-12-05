package presentacion.controlador;

import java.util.List;

import javax.swing.JOptionPane;

import dto.PermisoDTO;
import dto.UsuarioDTO;
import modelo.Permisos;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VistaPrincipal;

public class ControladorUsuLogin {

	private Permisos permisos;
	private UsuarioDTO usu_login;
	private VentanaVisualizarEquipos ventanaVisualizarEquipos;

	public ControladorUsuLogin(Permisos permisos) {
		this.permisos = permisos;
		this.usu_login = null;
	}

	public boolean validarSesion(VentanaLogin vistaLogin, VistaPrincipal vistaPrincipal) {

		usu_login = permisos.dameUsuario(vistaLogin.getTxtUsuLogin().getText(), vistaLogin.getTxtUsuPass().getText());

		if (usu_login == null) {
			vistaLogin.getTxtUsuLogin().setText("");
			vistaLogin.getTxtUsuPass().setText("");
			vistaLogin.getTxtUsuLogin().requestFocus();
			JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");

			return false;
		} else {
			vistaPrincipal.getPanel().setEnabled(true);
			vistaPrincipal.getBotonEquipos().setEnabled(true);
			vistaPrincipal.getBotonBusquedas().setEnabled(true);
			vistaPrincipal.getBotonClientes().setEnabled(true);
			vistaPrincipal.getBotonListados().setEnabled(true);
			vistaPrincipal.getBotonPresupuestos().setEnabled(true);
			vistaPrincipal.getBotonSalidas().setEnabled(true);
			vistaPrincipal.getBotonBackUp().setEnabled(true);
			vistaPrincipal.getBotonUsuarios().setEnabled(true);
			vistaPrincipal.getBotonConfiguracion().setEnabled(true);
			vistaLogin.dispose();

			vistaPrincipal.getTextUsuario().setText("BIENVENIDO/A: " + usu_login.getNombre());
			vistaPrincipal.getTextProgramador().setText("Diseñado por Diego H. Bertossi");
			vistaPrincipal.getTextVersionSoft().setText("Versión Reparsoft 3.1");

			return true;
		}
	}

	public void cerrarSesion() {
		this.usu_login = null;
	}

	public void verificarPermisosMenu(VistaPrincipal vistaPrincipal) {
		if (usu_login != null) {

			List<PermisoDTO> permisos_principal = permisos.damePermisosPadres(usu_login.getIdRol());
			int i = 0;
			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Equipos"))) {
				vistaPrincipal.getBotonEquipos().setEnabled(false);

			}

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Busquedas"))) {
				vistaPrincipal.getBotonBusquedas().setEnabled(false);

			}
			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Clientes"))) {
				vistaPrincipal.getBotonClientes().setEnabled(false);

			}

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Listados"))) {
				vistaPrincipal.getBotonListados().setEnabled(false);

			}

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Presupuestos"))) {
				vistaPrincipal.getBotonPresupuestos().setEnabled(false);

			}

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Salidas"))) {
				vistaPrincipal.getBotonSalidas().setEnabled(false);

			}

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Usuarios"))) {
				vistaPrincipal.getBotonUsuarios().setEnabled(false);

			}

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "BackUp"))) {
				vistaPrincipal.getBotonBackUp().setEnabled(false);

			}

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Configuracion"))) {
				vistaPrincipal.getBotonConfiguracion().setEnabled(false);

			}

		}

	}

	public void verificarPermisosVentanaVisualizacion(VentanaVisualizarEquipos ventanaVisualizarEquipos) {

		if (usu_login != null) {
			List<PermisoDTO> permisos_principal = permisos.damePermisosPadres(usu_login.getIdRol());

			if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Presupuestos"))) {

				ventanaVisualizarEquipos.getLabelPresupuesto().setVisible(false);
				ventanaVisualizarEquipos.getPanel_presupuesto().setVisible(false);

			}

		}
	}

	// public void verificarPermisosInternacion(VistaInternaciones vista)
	// {
	// if(usu_login != null)
	// {
	// List<PermisoDTO> permisos_principal =
	// permisos.damePermisosHijosD(usu_login.getIdRol(),"Internacion");
	//
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Insumos
	// Internacion"))){
	// vista.getBtnAgregarInsumos().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Lista
	// Internaciones"))){
	// vista.getBtnLisInt().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Egreso Real"))){
	// vista.getBtnEgresoReal().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Ingreso Real"))){
	// vista.getBtnIngresoReal().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Practicas
	// Internaciones"))){
	// vista.getBtnAgregarPracticas().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Estimada"))){
	// vista.getBtnInternacion().setVisible(false);
	// }
	// }
	// }
	//
	// public void verificarPermisosLiquidaciones(VistaLiquidaciones vista)
	// {
	// if(usu_login != null)
	// {
	// List<PermisoDTO> permisos_principal =
	// permisos.damePermisosHijosD(usu_login.getIdRol(),"Liquidaciones");
	//
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Liquidaciones
	// OS"))){
	// vista.getBtnLiqObraSociales().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Liquidaciones
	// Profesionales"))){
	// vista.getBtnLiqProfesionales().setVisible(false);
	// }
	// }
	//
	// }
	//
	// public void verificarPermisosDeposito(VistaDepositos vista)
	// {
	// if(usu_login != null)
	// {
	// List<PermisoDTO> permisos_principal =
	// permisos.damePermisosHijosD(usu_login.getIdRol(),"Stock");
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Deposito
	// Principal"))){
	// vista.getBtnDeposito1().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Deposito
	// Refrigerado"))){
	// vista.getBtnDepositoRefrigerado().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Aceptacion
	// Orden"))){
	// vista.getBtnAceptacionDeOrden().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Recepcion de
	// Insumo"))){
	// vista.getBtnRecepcionDeInsumos().setVisible(false);
	// }
	// }
	// }
	//
	// public void verificarPermisosProfesional(VistaProfesionales vista)
	// {
	// if(usu_login != null)
	// {
	//
	// }
	// }
	//
	// public void verificarPermisosAdministracion(VistaAdministracion vista)
	// {
	// if(usu_login != null)
	// {
	// List<PermisoDTO> permisos_principal =
	// permisos.damePermisosHijosD(usu_login.getIdRol(),"Administracion");
	//
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Agenda"))){
	// vista.getBtnAgendaProfesional().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Habitaciones"))){
	// vista.getBtnAdministrarHabitaciones().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Obra Social"))){
	// vista.getBtnObraSocial().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Cobertura"))){
	// vista.getBtnAdministrarCobertura().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Practica"))){
	// vista.getBtnAdministrarPracticas().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Roles"))){
	// vista.getBtnRolesUsuarios().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Especialidades"))){
	// vista.getBtnEspecialidad().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Diagnosticos"))){
	// vista.getBtnDiagnostico().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Medicos"))){
	// vista.getBtnMedicos().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Permisos"))){
	// vista.getBtnPermisos().setVisible(false);
	// }
	// }
	//
	// }
	//
	public UsuarioDTO getUsu_login() {
		return usu_login;
	}
	//
	// public void cambiarPass(VentanaCambContra ventanaCambiarContra) {
	// // TODO Auto-generated method stub
	// if(usu_login != null)
	// {
	// String oldPass = ventanaCambiarContra.getTxtUsuLogin().getText();
	// String newPass = ventanaCambiarContra.getTxtUsuPass().getText();
	//
	// if(oldPass.equals(usu_login.getPass()))
	// {
	// usu_login.setPass(newPass);
	// permisos.actualizarUsuario(usu_login);
	// JOptionPane.showMessageDialog(null,"se cambio correctamente la
	// contraseña");
	// ventanaCambiarContra.dispose();
	// }
	// else
	// {
	// JOptionPane.showMessageDialog(null,"La contraseña ingresada no es
	// valida");
	// }
	// }
	// else
	// {
	// JOptionPane.showMessageDialog(null,"No hay un usuario logeado");
	// }
	// }
	//
	// public void verificarPermisosGestionStock(VistaStock vista) {
	// // TODO Auto-generated method stub
	// if(usu_login != null)
	// {
	// List<PermisoDTO> permisos_principal =
	// permisos.damePermisosHijosD(usu_login.getIdRol(),"Gestion Stock");
	//
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Carga de Orden"))){
	// vista.getBtnCargarOrdenCompra().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Generar Orden"))){
	// vista.getBtnGenerarOrdenCompra().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Insumos"))){
	// vista.getBtnInsumos().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Proveedores"))){
	// vista.getBtnProveedores().setVisible(false);
	// }
	// if(!permisos_principal.contains(new PermisoDTO(0,0,0,"Lista de
	// Ordenes"))){
	// vista.getBtnVisualizacionDeOrdenes().setVisible(false);
	// }
	//
	// }
	// }
	//

}
