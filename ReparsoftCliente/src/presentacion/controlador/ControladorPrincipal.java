package presentacion.controlador;

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
import dto.UsuarioDTO;
import modelo.Agenda;
import modelo.Permisos;
import presentacion.vista.VistaPrincipal;
import presentacion.vista.VentanaBackUp;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaListadoReparacionesPresupuestos;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaPresupuestos;
import presentacion.vista.VentanaRolesUsuarios;
import presentacion.vista.VentanaSalidas;
import presentacion.vista.VentanaVisualizarEquipos;
import presentacion.vista.VentanaClientes;
import presentacion.vista.VentanaBusqueda;

public class ControladorPrincipal implements ActionListener {

	private VistaPrincipal vistaPrincipal;
	private Agenda modelo;

	private VentanaEquipos ventanaEquipos;
	private VentanaClientes ventanaClientes;
	private VentanaSalidas ventanaSalidas;
	private VentanaListadoReparaciones ventanaListadoReparaciones;
	private VentanaListadoReparacionesPresupuestos ventanaListadoReparacionesPresupuestos;
	private VentanaBackUp ventanaBackUp;
	private VentanaBusqueda ventanaBusqueda;
	private VentanaVisualizarEquipos ventanaVisualizarEquipos;
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
	
	
	
	private VentanaLogin vistaLogin;

	private ClienteDTO Cliente;

	private SucursalDTO Sucursal;
	
	private Permisos permisos;
	private UsuarioDTO usu_login;
	
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

	private TableRowSorter<DefaultTableModel> sorter;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	public ControladorPrincipal(VistaPrincipal v) {

		this.vistaPrincipal = v;
		this.modelo = new Agenda();
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
		
		controladorUsuLogin = new ControladorUsuLogin(new Permisos());

		
		
		
	}

	public void inicializar() {

		pedirInicioDeSesion();

		SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
		try {
			SpellChecker.registerDictionaries(new URL("file", null, "./Diccionario/"), "es");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
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
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if (arg0.getSource() == vistaLogin.getBtnAceptar()) {
			controladorUsuLogin.validarSesion(vistaLogin, this.vistaPrincipal);
			controladorUsuLogin.verificarPermisosMenu(vistaPrincipal);
			
		
		
		} else if (arg0.getSource() == vistaLogin.getBtnCancelar()) {

			int opcion = 0;

			opcion = JOptionPane.showConfirmDialog(null, "¿Desea salir del sistema?", "Aviso",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			switch (opcion) {
			case JOptionPane.YES_OPTION: {
				System.exit(0);
				break;
			}
			case JOptionPane.NO_OPTION:

				vistaLogin.getTxtUsuLogin().requestFocus();

				break;

			}

		}

		else if (controladorUsuLogin.getUsu_login() == null) {
			if (vistaLogin.isShowing()) {
				vistaLogin.dispose();
				vistaLogin = null;
			}
			JOptionPane.showMessageDialog(null, "Tiene que iniciar Sesión");
			pedirInicioDeSesion();
		} else if (arg0.getSource() == vistaPrincipal.getBotonUsuarios()) {

			ventanaRolesUsuarios = new VentanaRolesUsuarios(controladoUsuario);
			controladoUsuario = new ControladorUsuarios(ventanaRolesUsuarios, new Agenda());

		}

		else if (arg0.getSource() == vistaPrincipal.getBotonEquipos()) {

			ventanaEquipos = new VentanaEquipos();
	
			controladorReparacion = new ControladorReparacion(ventanaEquipos, controladorUsuLogin, new Agenda());

			
		}

		else if (arg0.getSource() == vistaPrincipal.getBotonSalidas()) {

			ventanaSalidas = new VentanaSalidas(controladorSalidas);
			controladorSalidas = new ControladorSalidas(ventanaSalidas, new Agenda());
			
		}

		else if (arg0.getSource() == vistaPrincipal.getBotonClientes()) {

			ventanaClientes = new VentanaClientes(controladorCliente);
			controladorCliente = new ControladorCliente(ventanaClientes, new Agenda());

		}

		else if (arg0.getSource() == vistaPrincipal.getBotonListados()) {

			ventanaListadoReparaciones = new VentanaListadoReparaciones(controladorListados);
			controladorUsuLogin.verificarPermisosVentanaListados(ventanaListadoReparaciones);
			ventanaEquipos = new VentanaEquipos();
			ventanaEquipos.setVisible(false);
			
			
			controladorReparacion = new ControladorReparacion(ventanaEquipos, controladorUsuLogin, modelo);
			controladorListados = new ControladorListados(ventanaListadoReparaciones,ventanaListadoReparacionesPresupuestos,modelo,controladorUsuLogin,controladorReparacion);
			controladorListados.cerraVentanaListadoEquipos();
			
			
			
		}

		
		else if (arg0.getSource() == vistaPrincipal.getBotonBackUp()) {

			ventanaBackUp = new VentanaBackUp(controladorBackup);
			controladorBackup = new ControladorBackup(ventanaBackUp);
			
		}
		
		else if (arg0.getSource() == vistaPrincipal.getBotonBusquedas()) {

			ventanaBusqueda = new VentanaBusqueda(controladorBusqueda);
			controladorBusqueda = new ControladorBusquedas(ventanaBusqueda, new Agenda());
			
		}
		
		else if (arg0.getSource() == vistaPrincipal.getBotonPresupuestos()) {
			
			
			ventanaPresupuestos = new VentanaPresupuestos(controladorReparacion);
			controladorPresupuestos = new ControladorPresupuestos(ventanaPresupuestos, modelo);
			
		}
		
		
		
		
		
		else if (arg0.getSource() == vistaPrincipal.getBtnSalir()) {

			int opcion = JOptionPane.showConfirmDialog(vistaPrincipal, "¿Desea salir del sistema?", "Aviso",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				System.exit(0);

			}

			
			
		}
		
		
	}


}
