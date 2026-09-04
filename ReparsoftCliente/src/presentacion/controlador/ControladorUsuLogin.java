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
            vistaPrincipal.getBtnModoPrueba().setVisible(esAdministradorProgramador());
            vistaLogin.dispose();

            vistaPrincipal.getTextUsuario().setText("BIENVENIDO/A: " + usu_login.getNombre());
            vistaPrincipal.getTextProgramador().setText("Diseñado por Diego H. Bertossi");
            vistaPrincipal.getTextVersionSoft().setText("Versión Reparsoft 2.0");

            return true;
        }
    }

    public void cerrarSesion() {
        this.usu_login = null;
    }

    /**
     * Verifica si el usuario logueado tiene permiso para acceder a un módulo.
     * Si no hay usuario logueado, devuelve false.
     *
     * @param nombreModulo Nombre del módulo tal como está registrado en la BD
     *                     (ej: "Equipos", "Presupuestos", "BackUp", etc.)
     * @return true si tiene permiso, false si no lo tiene.
     */
    public boolean tienePermiso(String nombreModulo) {
        if (usu_login == null) {
            return false;
        }
        List<PermisoDTO> permisos_principal = permisos.damePermisosPadres(usu_login.getIdRol());
        if (permisos_principal == null || permisos_principal.isEmpty()) {
            return false;
        }
        return permisos_principal.contains(new PermisoDTO(0, 0, 0, nombreModulo));
    }

    /**
     * Muestra el mensaje estándar de acceso denegado.
     */
    public void mostrarMensajeSinPermiso() {
        JOptionPane.showMessageDialog(
                null,
                "No tiene los permisos necesarios para acceder a este módulo.",
                "Acceso denegado",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Verifica permisos del menú principal.
     * AHORA solo habilita todos los botones (el control de acceso se hace en
     * ControladorPrincipal al momento de presionar cada botón).
     */
    @SuppressWarnings("unused")
    public void verificarPermisosMenu(VistaPrincipal vistaPrincipal) {
        // Los botones permanecen habilitados visualmente para todos los usuarios.
        // El control de acceso real se realiza en ControladorPrincipal#actionPerformed,
        // donde se llama a tienePermiso() antes de abrir cada módulo.
        // De esta manera, el usuario ve el botón disponible pero recibe un mensaje
        // claro si intenta acceder sin los permisos necesarios.
    }

    public void verificarPermisosVentanaListados(VentanaListadoReparaciones ventanaListadoReparaciones) {
        if (usu_login != null) {
            List<PermisoDTO> permisos_principal = permisos.damePermisosPadres(usu_login.getIdRol());

            if (permisos_principal != null && !permisos_principal.isEmpty()) {
                boolean tienePermisoPresupuestos = permisos_principal.stream()
                        .anyMatch(permiso -> "Presupuestos".equalsIgnoreCase(permiso.getNombrePantalla()));

                if (!tienePermisoPresupuestos) {
                    System.out.println("NO TIENE PERMISO");

                    int[] columnas = { 20, 21, 22 };
                    for (int columna : columnas) {
                        ventanaListadoReparaciones.getTblReparaciones().getColumnModel().getColumn(columna)
                                .setMaxWidth(0);
                        ventanaListadoReparaciones.getTblReparaciones().getColumnModel().getColumn(columna)
                                .setMinWidth(0);
                        ventanaListadoReparaciones.getTblReparaciones().getColumnModel().getColumn(columna)
                                .setPreferredWidth(0);
                    }

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

    public UsuarioDTO getUsu_login() {
        return usu_login;
    }

    public void verificarPermisosVentanaVisualizacion(VentanaVisualizarEquipos ventanaVisualizarEquipos2) {
        if (usu_login != null) {
            List<PermisoDTO> permisos_principal = permisos.damePermisosPadres(usu_login.getIdRol());

            if (!permisos_principal.contains(new PermisoDTO(0, 0, 0, "Presupuestos"))) {
                ventanaVisualizarEquipos2.getPanel_presupuesto().setVisible(false);
                ventanaVisualizarEquipos2.getLabelPresupuesto().setVisible(false);
            }
        }
    }
    
    
    //verificar si el usuario tiene el rol "Administrador Programador"
	public boolean esAdministradorProgramador() {
		if (usu_login == null) {
			return false;
		}
		String rol = usu_login.getNombreRol(usu_login.getIdRol());
		return "Administrador Programador".equalsIgnoreCase(rol);
	}
}