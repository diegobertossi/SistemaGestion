package presentacion.controlador.gestores;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Agenda;
import presentacion.controlador.ControladorReparacion;
import presentacion.vista.VentanaExcel;
import presentacion.vista.VentanaVisualizarEquipos;
import tiposPropios.MonedaFormatter;
import com.inet.jortho.SpellChecker;

//import presentacion.controlador.gestores.GestorArchivosExcel;

/**
 * GestorListadoEquipos Responsable de: - Gestionar múltiples ventanas abiertas
 * de visualización - Cargar datos en ventanas de listado - Actualizar datos en
 * ventanas de listado - Agregar listeners específicos para ventanas de listado
 * - Mantener coherencia entre ventanas múltiples
 */
public class GestorListadoEquipos {

	private ControladorReparacion controlador;
	private Agenda agenda;

	private List<VentanaVisualizarEquipos> ventanasAbiertas;
	private boolean actualizarEnListado = false;
	private MonedaFormatter monedaFormatter;
	private VentanaExcel ventanaExcel;
	private GestorArchivosExcel gestorExcel;

	/**
	 * Constructor
	 */
	public GestorListadoEquipos(ControladorReparacion controlador, Agenda agenda) {
		this.controlador = controlador;
		this.agenda = agenda;
		this.ventanasAbiertas = new ArrayList<>();
		this.monedaFormatter = new MonedaFormatter();
	}

	/**
	 * Carga datos en una ventana de listado por primera vez
	 */
	public VentanaVisualizarEquipos tomarDatosDeTablasListado(int numeroELS) throws ParseException {
		VentanaVisualizarEquipos ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
		ventanaVisualizarEquipos.setTitle(String.valueOf(numeroELS));
		ventanasAbiertas.add(ventanaVisualizarEquipos);

		cerrarVentanaVisualizarEquipoListado(ventanaVisualizarEquipos);

		monedaFormatter = new MonedaFormatter();
		controlador.getControladorUsuLogin().verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
		SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

		ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELS));

		// Cargar datos en la ventana
		controlador.getGestorVisualizacion().cargarDatosEquipo(ventanaVisualizarEquipos, numeroELS);

		// Agregar listeners específicos para listado
		// agregarListenersVentanaVisualizarEquiposListado(ventanaVisualizarEquipos);

		return ventanaVisualizarEquipos;
	}

	/**
	 * Actualiza datos en una ventana de listado ya abierta
	 */
	public VentanaVisualizarEquipos actualizarDatosDeTablasListado(int numeroELS,
			VentanaVisualizarEquipos ventanaVisualizarEquipos) throws ParseException {
		// Si es la primera vez, crear nueva ventana
		if (!actualizarEnListado) {
			ventanaVisualizarEquipos = new VentanaVisualizarEquipos(controlador);
			ventanaVisualizarEquipos.setTitle(String.valueOf(numeroELS));
			ventanasAbiertas.add(ventanaVisualizarEquipos);
			cerrarVentanaVisualizarEquipoListado(ventanaVisualizarEquipos);
		}

		monedaFormatter = new MonedaFormatter();
		controlador.getControladorUsuLogin().verificarPermisosVentanaVisualizacion(ventanaVisualizarEquipos);
		SpellChecker.register(ventanaVisualizarEquipos.getTextInformeCliente());

		ventanaVisualizarEquipos.setTextELS(Integer.toString(numeroELS));

		// Cargar datos en la ventana
		controlador.getGestorVisualizacion().cargarDatosEquipo(ventanaVisualizarEquipos, numeroELS);

		return ventanaVisualizarEquipos;
	}

	/**
	 * Agrega listeners específicos para ventanas de listado
	 */
	public void agregarListenersVentanaVisualizarEquiposListado(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		actualizarEnListado = true;

		// Edición
		ventanaVisualizarEquipos.getBtnEditar()
				.addActionListener(e -> controlador.getGestorVisualizacion().editar(ventanaVisualizarEquipos));

		ventanaVisualizarEquipos.getBtnGuardarCambios()
				.addActionListener(e -> controlador.getGestorVisualizacion().guardarCambios(ventanaVisualizarEquipos));

		// Presupuesto
		ventanaVisualizarEquipos.getBotonPresupuestar()
				.addActionListener(e -> controlador.getGestorPresupuesto().abrirPresupuesto(ventanaVisualizarEquipos));

		// Excel
		ventanaVisualizarEquipos.getBtnabrirExcel().addActionListener(e -> abrirExcel(ventanaVisualizarEquipos));

		// Facturación
		ventanaVisualizarEquipos.getBtnfacturar()
				.addActionListener(e -> controlador.getGestorPresupuesto().abrirFacturacion(ventanaVisualizarEquipos));

		// Registro de ingreso
		ventanaVisualizarEquipos.getBotonRegistroIngreso()
				.addActionListener(e -> generarRegistroIngreso(ventanaVisualizarEquipos));

		// Correo WSP
		ventanaVisualizarEquipos.getBtnenviarCorreoOwsp()
				.addActionListener(e -> abrirEnviarCorreoWSP(ventanaVisualizarEquipos));

		// Estados
		ventanaVisualizarEquipos.getBotonEditarEstados().addActionListener(e -> {
			controlador.getGestorVisualizacion().abrirVentanaEstados(ventanaVisualizarEquipos);
		});

		// Remito
		ventanaVisualizarEquipos.getBtnGenerarRemito()
				.addActionListener(e -> controlador.getGestorPresupuesto().generarRemito(ventanaVisualizarEquipos));

		// Repuestos
		ventanaVisualizarEquipos.getBtnRepuestos().addActionListener(
				e -> controlador.getGestorRepuestos().abrirVentanaRepuestos(ventanaVisualizarEquipos));

		ventanaVisualizarEquipos.getBtnEliminarRepuesto()
				.addActionListener(e -> controlador.getGestorRepuestos().eliminarRepuesto(ventanaVisualizarEquipos));

		// Avisos
		ventanaVisualizarEquipos.getBotonAvisoInforme().addActionListener(
				e -> controlador.getGestorPresupuesto().enviarAvisoInforme(ventanaVisualizarEquipos));

		ventanaVisualizarEquipos.getBotonAvisoEquipoListo().addActionListener(
				e -> controlador.getGestorPresupuesto().enviarAvisoEquipoListo(ventanaVisualizarEquipos));

		ventanaVisualizarEquipos.getBotonRespuestaAlTecnico().addActionListener(
				e -> controlador.getGestorPresupuesto().enviarRespuestaCliente(ventanaVisualizarEquipos));

		// boton "copiar presupuesto"
		ventanaVisualizarEquipos.getBtnCopiarPresupuesto()
				.addActionListener(e -> controlador.getGestorVisualizacion().copiarPago(ventanaVisualizarEquipos));

		// boton copiar factura
		ventanaVisualizarEquipos.getBtnCopiarFactura()
				.addActionListener(e -> controlador.getGestorPresupuesto().abrirVentanaCopiarFactura(ventanaVisualizarEquipos));

			
		// Precios
		controlador.getGestorInterfaz().agregarListenersPrecios(ventanaVisualizarEquipos);
	}

	/**
	 * Genera registro de ingreso
	 */
	/**
	 * Genera registro de ingreso desde ventana de visualización
	 */
	private void generarRegistroIngreso(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		try {
			List<dto.RegistroEntradaReporteDTO> lista = new ArrayList<>();

			// Extraer datos de la reparación actual
			int els = Integer.parseInt(ventanaVisualizarEquipos.getTextELS());
			String falla = ventanaVisualizarEquipos.getTextFalla().getText();
			String remito = ventanaVisualizarEquipos.getTextRemitoCliente().getText();
			String nombreEquipo = ventanaVisualizarEquipos.getTextNombreEquipo().getText();
			String modelo = ventanaVisualizarEquipos.getTextModelo().getText();
			String marca = ventanaVisualizarEquipos.getTextMarca().getText();
			String serie = ventanaVisualizarEquipos.getTextNSerie().getText();
			String aviso = ventanaVisualizarEquipos.getTextAvisoCliente().getText();
			String clienteCliente = ventanaVisualizarEquipos.getTextClienteCliente().getText();
			String cliente = ventanaVisualizarEquipos.getTextCliente().getText();
			String sucursal = ventanaVisualizarEquipos.getTextSucursal().getText();

			// Obtener IDs
			int idCliente = agenda.idClienteporNombre(cliente);
			int idSucursal = agenda.idSucursalporNombre(sucursal, idCliente);
			int idEquipo = agenda.dameIDequipo();

			// Parsear fecha
			java.util.Date fechaEntradaVisual = ventanaVisualizarEquipos.getFechaEntrada().getDate();
			String fechaEntrada = null;
			if (fechaEntradaVisual != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				fechaEntrada = dateFormat.format(fechaEntradaVisual);
			}

			// Obtener estado físico
			String estadoFisico = ventanaVisualizarEquipos.getTextEstadoFisico().getText();
			String estadoTecnico = "Sin Revisar";

			// Crear DTO
			dto.RegistroEntradaReporteDTO rep = new dto.RegistroEntradaReporteDTO(els, fechaEntrada, falla,
					estadoFisico, estadoTecnico, remito, idEquipo, nombreEquipo, modelo, marca, serie, aviso,
					clienteCliente, idCliente, idSucursal, cliente, sucursal);

			if (rep != null) {
				lista.add(rep);
				presentacion.reportes.ReporteRegistroEntrada reporte = new presentacion.reportes.ReporteRegistroEntrada(
						rep, lista, agenda);
				reporte.mostrar();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al generar registro: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	/**
	 * Abre Excel
	 */
	private void abrirExcel(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		// Implementación apertura Excel correspondiente, vía GestorArchivosExcel
		gestorExcel = new GestorArchivosExcel(agenda.getUbicacionBase());
		ventanaExcel = new VentanaExcel(gestorExcel);

		agregarListenersExcel(ventanaExcel);
	}

	private void agregarListenersExcel(VentanaExcel ventanaExcel2) {
		ventanaExcel2.getBtnRepar().addActionListener(e -> gestorExcel.abrirReparaciones());
		ventanaExcel2.getBtnCaja().addActionListener(e -> gestorExcel.abrirCaja());
		ventanaExcel2.getBtnDetalleGastos().addActionListener(e -> gestorExcel.abrirDetalleGastosAnioActual());
		ventanaExcel2.getBtnAbrirTodos().addActionListener(e -> gestorExcel.abrirTodosLosArchivos());
	}

	/**
	 * Abre ventana de enviar correo por WSP
	 */
	private void abrirEnviarCorreoWSP(VentanaVisualizarEquipos ventanaVisualizarEquipos) {
		presentacion.vista.VentanaEnviarCorreoOwsp ventanaEnviarCorreoOwsp = new presentacion.vista.VentanaEnviarCorreoOwsp(
				controlador);

//        ventanaEnviarCorreoOwsp.getBtnEnviarWST().addActionListener(e -> 
//            controlador.getGestorClientesWSP().abrirVentanaWSP());
//        
//        ventanaEnviarCorreoOwsp.setVisible(true);
	}

	/**
	 * Cierra ventana de listado
	 */
	private void cerrarVentanaVisualizarEquipoListado(VentanaVisualizarEquipos ventana) {
		ventana.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				boolean guardado = controlador.getGestorVisualizacion().isGuardado();

				if (!guardado) {
					int opcion = JOptionPane.showConfirmDialog(ventana,
							"Hay cambios sin guardar. ¿Desea guardar antes de salir?", "Aviso",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (opcion == JOptionPane.YES_OPTION) {
						// Intentar guardar cambios
						controlador.getGestorVisualizacion().guardarCambios(ventana);

						// Verificar si realmente se guardó (si no hay caracteres inválidos)
						boolean guardadoDespuesDeIntentar = controlador.getGestorVisualizacion().isGuardado();

						if (!guardadoDespuesDeIntentar) {
							// No se pudo guardar (hay caracteres inválidos)
							// El gestorDatos ya mostró el popup con los caracteres inválidos
							// No cerrar la ventana, permitir al usuario corregir
							return;
						}
						// Si llegamos aquí, se guardó correctamente, continuar con el cierre

					} else if (opcion == JOptionPane.NO_OPTION) {
						// Usuario no quiere guardar, continuar con el cierre
						// (no hacer nada aquí, el código continúa abajo)

					} else if (opcion == JOptionPane.CANCEL_OPTION) {
						// Usuario canceló, no cerrar la ventana
						return;
					}
				}

				// Si llegamos aquí, proceder con el cierre de la ventana
				ventanasAbiertas.remove(ventana);
				ventana.dispose();

				if (ventanasAbiertas.isEmpty()) {
					actualizarEnListado = false;
				}
			}
		});
	}

	/**
	 * Procesa eventos delegados
	 */
	public void procesarEventos(ActionEvent e) {
		// Delegación de eventos específicos de listado
	}

	/**
	 * Getters y Setters
	 */
	public List<VentanaVisualizarEquipos> getVentanasAbiertas() {
		return ventanasAbiertas;
	}

	public void agregarVentanaAbierta(VentanaVisualizarEquipos ventana) {
		if (!ventanasAbiertas.contains(ventana)) {
			ventanasAbiertas.add(ventana);
		}
	}

	public void removerVentanaAbierta(VentanaVisualizarEquipos ventana) {
		ventanasAbiertas.remove(ventana);
	}

	public int cantidadVentanasAbiertas() {
		return ventanasAbiertas.size();
	}

	public boolean isActualizarEnListado() {
		return actualizarEnListado;
	}

	public void setActualizarEnListado(boolean actualizar) {
		this.actualizarEnListado = actualizar;
	}
}
