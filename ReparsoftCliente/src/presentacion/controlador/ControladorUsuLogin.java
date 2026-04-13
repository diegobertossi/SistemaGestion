package presentacion.controlador;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import dto.PermisoDTO;
import dto.UsuarioDTO;
import modelo.Permisos;
import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VistaPrincipal;

public class ControladorUsuLogin {

	private Permisos permisos;
	private UsuarioDTO usu_login;
	@SuppressWarnings("unused")
	private VentanaVisualizarEquipos ventanaVisualizarEquipos;

	@SuppressWarnings("unused")
	private DefaultTableModel modelReparaciones;
	@SuppressWarnings("unused")
	private TableColumn columna;

	public ControladorUsuLogin(Permisos permisos) {
		this.permisos = permisos;
		this.usu_login = null;
	}

	@SuppressWarnings("deprecation")
	public boolean validarSesion(VentanaLogin vistaLogin, VistaPrincipal vistaPrincipal) {

		usu_login = permisos.dameUsuario(vistaLogin.getTxtUsuLogin().getText(), vistaLogin.getTxtUsuPass().getText());

		if (usu_login == null || usu_login.getIdUsuario() == 1) {
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
					
			//vistaPrincipal.getTextUsuario().setText("BIENVENIDO/A: " + usu_login.getNombre());
			
			vistaPrincipal.getTextProgramador().setText("Diseñado por Diego H. Bertossi");
			vistaPrincipal.getTextVersionSoft().setText("Versión Reparsoft 2.0");

			return true;
		}
	}

	public void cerrarSesion() {
		this.usu_login = null;
	}

	@SuppressWarnings("unused")
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

	
	public void verificarPermisosVentanaListados(VentanaListadoReparaciones ventanaListadoReparaciones) {
	    if (usu_login != null) {
	        // Obtener la lista de permisos principales del usuario
	        List<PermisoDTO> permisos_principal = permisos.damePermisosPadres(usu_login.getIdRol());

	        if (permisos_principal != null && !permisos_principal.isEmpty()) {
	            // Verificar si tiene el permiso "Presupuestos"
	            boolean tienePermisoPresupuestos = permisos_principal.stream()
	                    .anyMatch(permiso -> "Presupuestos".equalsIgnoreCase(permiso.getNombrePantalla()));

	            if (!tienePermisoPresupuestos) {
	                System.out.println("NO TIENE PERMISO");

	                // Ocultar las columnas  20, 21, 22
	                int[] columnas = {20, 21, 22};
	                for (int columna : columnas) {
	                    ventanaListadoReparaciones.getTblReparaciones().getColumnModel().getColumn(columna).setMaxWidth(0);
	                    ventanaListadoReparaciones.getTblReparaciones().getColumnModel().getColumn(columna).setMinWidth(0);
	                    ventanaListadoReparaciones.getTblReparaciones().getColumnModel().getColumn(columna).setPreferredWidth(0);
	                }

	                // Ocultar los checkboxes correspondientes
	                ventanaListadoReparaciones.getChckbxPrecioPeso().setVisible(false);
	                ventanaListadoReparaciones.getChckbxPrecioDolar().setVisible(false);
	                ventanaListadoReparaciones.getChckbxPago().setVisible(false);
	                ventanaListadoReparaciones.getBtnEstadisticas().setVisible(false);
	                
	            }
	        } else {
	            System.out.println("La lista de permisos está vacía o es nula.");
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
	// contrase�a");
	// ventanaCambiarContra.dispose();
	// }
	// else
	// {
	// JOptionPane.showMessageDialog(null,"La contrase�a ingresada no es
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

	public void verificarPermisosVentanaVisualizacion(VentanaVisualizarEquipos ventanaVisualizarEquipos2) {
		// TODO Auto-generated method stub
		
	}

}
