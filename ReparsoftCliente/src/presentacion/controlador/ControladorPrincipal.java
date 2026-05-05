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

        String modo = Conexion.isModoAntigua() ? " - ANTIGUA" : " - ACTUAL";
        vistaPrincipal.getTextLugarBaseDatos().setText(ubicacionBase.toUpperCase() + modo);
    }

    private Agenda crearAgendaActual(String ubicacion) {
        return new Agenda(ubicacion, Conexion.isModoAntigua());
    }

    public void inicializar() {
        pedirInicioDeSesion();

        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        try {
            SpellChecker.registerDictionaries(new URL("file", null, "./Diccionario/"), "es");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.vistaPrincipal.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(vistaPrincipal,
                        "¿Desea salir del sistema?", "Aviso",
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

    // ── Helper: crea el stack completo de ventanas/controladores para módulos
    //    que necesitan Equipos + Salidas + Clientes + Presupuestos
    private void crearStackReparacion() {
        modelo = crearAgendaActual(ubicacionDeBase);

        ventanaClientes    = new VentanaClientes(null);
        controladorCliente = new ControladorCliente(ventanaClientes, modelo);

        ventanaSalidas    = new VentanaSalidas(null);
        controladorSalidas = new ControladorSalidas(ventanaSalidas, modelo);

        ventanaPresupuestos    = new VentanaPresupuestos(null);
        controladorPresupuestos = new ControladorPresupuestos(ventanaPresupuestos, modelo);

        ventanaEquipos = new VentanaEquipos(null);

        // ControladorReparacion se crea ÚLTIMO porque necesita todos los anteriores
        controladorReparacion = new ControladorReparacion(
                ventanaEquipos, controladorUsuLogin, modelo,
                controladorPresupuestos, controladorSalidas, controladorCliente);

        // Ocultar las ventanas auxiliares (se abren desde ControladorReparacion)
        ventanaClientes.setVisible(false);
        ventanaPresupuestos.setVisible(false);
        ventanaSalidas.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        // ── Login ────────────────────────────────────────────────────────────
        if (arg0.getSource() == vistaLogin.getBtnAceptar()) {
            controladorUsuLogin.validarSesion(vistaLogin, this.vistaPrincipal);
            controladorUsuLogin.verificarPermisosMenu(vistaPrincipal);

        } else if (arg0.getSource() == vistaLogin.getBtnCancelar()) {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "¿Desea salir del sistema?", "Aviso",
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

        // ── Cerrar sesión ────────────────────────────────────────────────────
        } else if (arg0.getSource() == this.vistaPrincipal.getBtncerrarSesion()) {
        	//cerrarConfiguracionSiEstaAbierta(); // AGREGAR
            this.controladorUsuLogin.cerrarSesion();
            inicializar();

        // ── Salir ────────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBtnSalir()) {
            int opcion = JOptionPane.showConfirmDialog(vistaPrincipal,
                    "¿Desea salir del sistema?", "Aviso",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

        // ── Equipos ──────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonEquipos()) {
            if (!controladorUsuLogin.tienePermiso("Equipos")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            crearStackReparacion();
            // ventanaEquipos ya fue creada dentro de crearStackReparacion, solo mostrarla
            traerAlFrente(ventanaEquipos);

        // ── Salidas ──────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonSalidas()) {
            if (!controladorUsuLogin.tienePermiso("Salidas")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            if (controladorUsuLogin.getUsu_login().getIdRol() != 1 && !verificarModoNormal()) return;

            modelo          = crearAgendaActual(ubicacionDeBase);
            ventanaSalidas  = new VentanaSalidas(null);
            controladorSalidas = new ControladorSalidas(ventanaSalidas, modelo);
            traerAlFrente(ventanaSalidas);

        // ── Clientes ─────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonClientes()) {
            if (!controladorUsuLogin.tienePermiso("Clientes")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            if (controladorUsuLogin.getUsu_login().getIdRol() != 1 && !verificarModoNormal()) return;

            modelo             = crearAgendaActual(ubicacionDeBase);
            ventanaClientes    = new VentanaClientes(null);
            controladorCliente = new ControladorCliente(ventanaClientes, modelo);
            traerAlFrente(ventanaClientes);

        // ── Listados ─────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonListados()) {
            if (!controladorUsuLogin.tienePermiso("Listados")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            // Crear todo el stack de reparación (necesario para abrir equipos desde listado)
            crearStackReparacion();

            ventanaListadoReparaciones = new VentanaListadoReparaciones(null);
            controladorUsuLogin.verificarPermisosVentanaListados(ventanaListadoReparaciones);

            // controladorReparacion ya existe y está completo gracias a crearStackReparacion()
            controladorListados = new ControladorListados(
                    ventanaListadoReparaciones, modelo, controladorUsuLogin, controladorReparacion);
            controladorListados.cerraVentanaListadoEquipos();

            ventanaEquipos.setVisible(false);
            traerAlFrente(ventanaListadoReparaciones);

        // ── Busquedas ────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonBusquedas()) {
            if (!controladorUsuLogin.tienePermiso("Busquedas")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            crearStackReparacion();

            ventanaBusqueda = new VentanaBusqueda(null);

            // Aplicar permisos sobre ventanaListadoReparaciones solo si existe
            if (ventanaListadoReparaciones != null) {
                controladorUsuLogin.verificarPermisosVentanaListados(ventanaListadoReparaciones);
            }

            controladorBusqueda = new ControladorBusquedas(
                    ventanaBusqueda, controladorReparacion, crearAgendaActual(ubicacionDeBase));

            ventanaEquipos.setVisible(false);
            traerAlFrente(ventanaBusqueda);

        // ── Presupuestos ─────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonPresupuestos()) {
            if (!controladorUsuLogin.tienePermiso("Presupuestos")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            if (controladorUsuLogin.getUsu_login().getIdRol() != 1 && !verificarModoNormal()) return;

            modelo                  = crearAgendaActual(ubicacionDeBase);
            ventanaPresupuestos     = new VentanaPresupuestos(null);
            controladorPresupuestos = new ControladorPresupuestos(ventanaPresupuestos, modelo);

            traerAlFrente(ventanaPresupuestos);
        // ── BackUp ───────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonBackUp()) {
            if (!controladorUsuLogin.tienePermiso("BackUp")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            modelo        = crearAgendaActual(ubicacionDeBase);
            ventanaBackUp = new VentanaBackUp(null);
            controladorBackup = new ControladorBackup(ventanaBackUp, modelo);

            // Solo admin puede hacer backup en modo antiguo
            if (controladorUsuLogin.getUsu_login().getIdRol() != 1 && Conexion.isModoAntigua()) {
                ventanaBackUp.getRdbtnLocal().setEnabled(false);
                ventanaBackUp.getRdbtnRemoto().setEnabled(false);
            }

            traerAlFrente(ventanaBackUp);
        // ── Usuarios ─────────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonUsuarios()) {
            if (!controladorUsuLogin.tienePermiso("Usuarios")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            if (controladorUsuLogin.getUsu_login().getIdRol() != 1 && !verificarModoNormal()) return;

            ventanaRolesUsuarios = new VentanaRolesUsuarios(null);
            controladoUsuario    = new ControladorUsuarios(
                    ventanaRolesUsuarios, controladorUsuLogin, crearAgendaActual(ubicacionDeBase));
            
            traerAlFrente(ventanaRolesUsuarios);

        // ── Configuración ────────────────────────────────────────────────────
        } else if (arg0.getSource() == vistaPrincipal.getBotonConfiguracion()) {
            if (!controladorUsuLogin.tienePermiso("Configuracion")) {
                controladorUsuLogin.mostrarMensajeSinPermiso();
                return;
            }
            ventanaConfiguracion = new VentanaConfiguracion(null);

            if (!controladorUsuLogin.esAdministradorProgramador()) {
                ventanaConfiguracion.getBtnMigracion().setVisible(false);
            }

            controladorconfiguraciones = new ControladorConfiguraciones(
                    ventanaConfiguracion, controladorUsuLogin, vistaPrincipal);
            traerAlFrente(ventanaConfiguracion);
        }
       
    }

    public boolean verificarModoNormal() {
        if (Conexion.isModoAntigua()) {
            JOptionPane.showMessageDialog(vistaPrincipal,
                    "NO ES POSIBLE ACCEDER A ESTE MÓDULO CON DATOS ANTIGUOS.",
                    "Módulo no disponible", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    
    private void traerAlFrente(javax.swing.JFrame ventana) {
        if (ventana == null) return;
        ventana.setVisible(true);
        javax.swing.SwingUtilities.invokeLater(() -> {
            ventana.toFront();
            ventana.requestFocus();
        });
    }
    
    
    private void cerrarConfiguracionSiEstaAbierta() {
        if (ventanaConfiguracion != null && ventanaConfiguracion.isShowing()) {
            ventanaConfiguracion.dispose();
            ventanaConfiguracion = null;
            controladorconfiguraciones = null;
        }
    }
    
    
}