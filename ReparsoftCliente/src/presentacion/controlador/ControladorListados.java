package presentacion.controlador;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import VistaPropias.AutoCompletarComboBox;
import VistaPropias.TablaFiltros;
import VistaPropias.TableCopyHandler;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.IntervalCategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;

import dto.FacturacionXclienteDTO;
import dto.RegistroPresupuestoDTO;
import dto.RegistroResumenTecnicoDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import modelo.Agenda;
import persistencia.dao.mysql.LogDAO;
import presentacion.reportes.ReportePresupuesto;
import presentacion.vista.VentanaCodigoSeguridad;
import presentacion.vista.VentanaEquipos;
import presentacion.vista.VentanaEstadisticas;
import presentacion.vista.VentanaFacturacionXcliente;
import presentacion.vista.VentanaListadoReparaciones;
import presentacion.vista.VentanaResumenMensualTecnico;
import presentacion.vista.VentanaVisualizarEquipos;
import tiposPropios.MonedaFormatter;
import presentacion.reportes.ReporteResumenTecnico;
import util.Config;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.*;

public class ControladorListados
		implements ActionListener, MouseListener, KeyListener, ItemListener, MouseMotionListener {

	private Agenda modelo;

	private VentanaListadoReparaciones ventanaListadoReparaciones;

	private ControladorReparacion controladorReparacion;
	private VentanaEquipos ventanaEquipos;
	private VentanaEstadisticas ventanaEstadisticas;
	private VentanaCodigoSeguridad ventanaCodigoSeguridad;
	private VentanaResumenMensualTecnico ventanaResumenMensualTecnico;

	private VentanaFacturacionXcliente ventanaFacturacionXcliente;
	private VentanaVisualizarEquipos ventanaVisualizarEquipos;

	public int NumeroELSSeleccionado;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	@SuppressWarnings("unused")
	private ControladorUsuLogin controladorUsuLogin;

	private MonedaFormatter monedaFormatter;

	private int filtro;
	// AGREGAR junto a las otras variables de instancia
	private TablaFiltros tablaFiltros;
	private Map<Integer, String> filtrosActivos = new HashMap<>();
	
	// NUEVO: estado de paginación
	private static final int REGISTROS_POR_PAGINA = 100;
	private int paginaActual = 0;   // base 0
	private int totalPaginas  = 1;
	private boolean cargandoPagina = false;
	private int totalRegistros = 0;
	
	
	private String seleccionDetalleEstadisticas = "OCULTAR DETALLE";

	private int anio;
	private int mes;
	private int cantidadIngresosPorAnio;
	private int cantidadDiagnosticosPorAnio;
	private double facturacionPesoPorAnio;
	private double facturacionDolarPorAnio;
	private persistencia.dao.mysql.ReparacionEstadisticasManager.TotalesPorAnio totalesAnioActual;

	private double porcentaje;
	private double facturacion;
	private List<FacturacionXclienteDTO> itemFacturacion_en_tabla;

	private CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
	private Font titleFont = new Font("Arial", Font.BOLD, 20);
	private Font labelFont = new Font("Arial", Font.BOLD, 18);
	private Font labelFontPie = new Font("Arial", Font.PLAIN, 12);
	private Color labelColor = Color.darkGray;

	private ItemLabelPosition positionNumeroGrande = new ItemLabelPosition(ItemLabelAnchor.CENTER,
			TextAnchor.BASELINE_CENTER, TextAnchor.BASELINE_CENTER, -Math.PI / 2.0);
	private ItemLabelPosition positionNumeroChico = new ItemLabelPosition(ItemLabelAnchor.CENTER,
			TextAnchor.BASELINE_CENTER, TextAnchor.BASELINE_CENTER, 0);

	private ChartPanel chartPanelIngresos;
	private ChartPanel chartPanelDiagnosticos;
	private ChartPanel chartPanelFacturacion;

	private static final String[] MESES = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};

	private final Map<String, Object[]> cacheGraficosAnio = new HashMap<>();
	private final Map<String, Object[]> cacheGraficosTecnico = new HashMap<>();
	private final Map<String, Object[]> cacheGraficosCliente = new HashMap<>();

	private boolean cargandoGraficos = false;

	private String[] nombresColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO",
			"N° SERIE", "AVISO", "REVISIÓN", "SALIDA", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS",
			"TÉCNICO", "UBIC. REM", "NUM REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO",
			"INGRESO" };

	private AWTEventListener clickOutsideListener; // Mover a variable de clase

	private Map<Integer, Integer> anchosOriginales = new HashMap<>();

	public ControladorListados(VentanaListadoReparaciones ventanaListadoReparaciones, Agenda modelo,
			ControladorUsuLogin controladorUsuLogin, ControladorReparacion controladorReparacion) {

		this.ventanaListadoReparaciones = ventanaListadoReparaciones;
		this.controladorUsuLogin = controladorUsuLogin;
		this.controladorReparacion = controladorReparacion;
		this.modelo = modelo;
		this.itemFacturacion_en_tabla = null;

		agregarListenerVentanaListados();
		
		// NUEVO: obtener total de registros y primera página en segundo plano
		totalRegistros = 0;
		totalPaginas = 1;

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				totalRegistros = modelo.contarReparaciones();
				totalPaginas = (int) Math.ceil((double) totalRegistros / REGISTROS_POR_PAGINA);
				if (totalPaginas < 1) totalPaginas = 1;
				return null;
			}

			@Override
			protected void done() {
				try {
					get();

					SwingWorker<List<ReparacionDTO>, Void> workerPagina = new SwingWorker<List<ReparacionDTO>, Void>() {
						@Override
						protected List<ReparacionDTO> doInBackground() throws Exception {
							return modelo.obtenerReparacionPaginada(REGISTROS_POR_PAGINA, 0);
						}

						@Override
						protected void done() {
							try {
								cargarTablaConDatos(get());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					};
					workerPagina.execute();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		worker.execute();

		configurarVista();
		configurarEventos();

	}

	public void inicializar() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if (this.ventanaListadoReparaciones != null
				&& arg0.getSource() == this.ventanaListadoReparaciones.getBtnEstadisticas()) {

			ventanaEstadisticas = new VentanaEstadisticas(this);
			
			// Reset chart panels for new window
			chartPanelIngresos = null;
			chartPanelDiagnosticos = null;
			chartPanelFacturacion = null;
			
			agregarListenerAventanaEstadisticas();
			monedaFormatter = new MonedaFormatter();
			seleccionDetalleEstadisticas = "OCULTAR DETALLE";

			llenarcomboFiltro();

		}

		else if (this.ventanaEstadisticas != null
				&& arg0.getSource() == this.ventanaEstadisticas.getBtnConfiguracion()) {

			if (filtro == 0) {

				JOptionPane.showMessageDialog(null, "Debe seleccionar un FILTRO antes de setear la configuraración",
						"SELECCIONAR FILTRO", JOptionPane.INFORMATION_MESSAGE);

			}

			else {

				ventanaCodigoSeguridad = new VentanaCodigoSeguridad();
				ventanaCodigoSeguridad.getBtnAceptar().addActionListener(this);
				ventanaCodigoSeguridad.getBtnCancelar().addActionListener(this);

				ventanaCodigoSeguridad.getTxtCodigoSeguridad().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();

						// Convertir el array de caracteres a una cadena de texto
						String codigo = new String(codigoIngresado);

						if (verificarCodigoSeguridad(codigo)) {

							habitarDetalles();

						}

					}
				});
			}

		}

		else if (this.ventanaCodigoSeguridad != null
				&& arg0.getSource() == this.ventanaCodigoSeguridad.getBtnAceptar()) {

			char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();

			// Convertir el array de caracteres a una cadena de texto
			String codigo = new String(codigoIngresado);

			if (verificarCodigoSeguridad(codigo)) {

				habitarDetalles();

			}

		}

		else if (this.ventanaCodigoSeguridad != null
				&& arg0.getSource() == this.ventanaCodigoSeguridad.getBtnCancelar()) {

			this.ventanaCodigoSeguridad.dispose();
			this.ventanaCodigoSeguridad = null;

		}

		else if (this.ventanaEstadisticas != null
				&& arg0.getSource() == this.ventanaEstadisticas.getBtnResumenMensualTecnico()) {

			ventanaResumenMensualTecnico = new VentanaResumenMensualTecnico(this);
			this.ventanaResumenMensualTecnico.getBtnCalcularComisiones().addActionListener(this);
			this.ventanaResumenMensualTecnico.getBtnMostrarResumen().addActionListener(this);

			String nombreTecnicoYanio = ventanaEstadisticas.getTextNombreTecnico().getText() + " - "
					+ ventanaEstadisticas.getLblAnioDatos().getText();
			ventanaResumenMensualTecnico.getTextTecnicoAnio().setText(nombreTecnicoYanio);

			ventanaResumenMensualTecnico.getTextPorcComisiones().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						porcentaje = Double.parseDouble(ventanaResumenMensualTecnico.getTextPorcComisiones().getText());
						String porsentajeformateado = String.format("%.1f %%", porcentaje);
						ventanaResumenMensualTecnico.getTextPorcComisiones().setText((porsentajeformateado));
					} catch (NumberFormatException ex) {

						JOptionPane.showMessageDialog(null, "Ingrese un número válido", "Solo Numeros",
								JOptionPane.INFORMATION_MESSAGE);
					}

					calcularComisiones();

				}
			});

			llenarcomboMesResumen();

			ventanaResumenMensualTecnico.getComboMes().addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					ventanaResumenMensualTecnico.getTextPorcComisiones().setText("");
					ventanaResumenMensualTecnico.getTextTotalComisionesPesos().setText("");

					String nombreTecnico = ventanaEstadisticas.getTextNombreTecnico().getText();
					int idTecnico = modelo.idUsuarioporNombre(nombreTecnico);
					mes = ventanaResumenMensualTecnico.getComboMes().getSelectedIndex();

					int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());

					List<Integer> listaDiagnosticos = modelo.dameDiagnosticosPorAnioPorTecnico(anio, idTecnico);
					List<Integer> listaAceptaciones = modelo.dameAceptacionesPorAnioPorTecnico(anio, idTecnico);
					List<Double> listaFacturacion = modelo.dameFacturacionPorAnioPorTecnico(anio, idTecnico);
					List<Double> listaFacturacionDolar = modelo.dameFacturacionDolaresPorAnioPorTecnico(anio,
							idTecnico);

					List<Integer> listaReparadosXmesXtecnico = modelo.dameReparadosXmesXtecnico(anio, idTecnico);
					List<Integer> listaRepEnGtiaXmesXtecnico = modelo.dameRepEnGtiaXmesXtecnico(anio, idTecnico);
					List<Integer> listaSinFallaXmesXtecnico = modelo.dameSinFallaXmesXtecnico(anio, idTecnico);
					List<Integer> listaEnRepXmesXtecnico = modelo.dameEnRepXmesXtecnico(anio, idTecnico);
					List<Integer> listaVentasXmesXtecnico = modelo.dameVentasXmesXtecnico(anio, idTecnico);
					List<Integer> listaSinRepXmesXtecnico = modelo.dameSinRepXmesXtecnico(anio, idTecnico);
					List<Integer> listaRepAcepXmesXtecnico = modelo.dameRepAcepXmesXtecnico(anio, idTecnico);
					List<Integer> listaRepNoAcepXmesXtecnico = modelo.dameRepNoAcepXmesXtecnico(anio, idTecnico);
					List<Integer> listaRepEsperaXmesXtecnico = modelo.dameEsperaXmesXtecnico(anio, idTecnico);

					String revisados = Integer.toString(listaDiagnosticos.get(mes));
					String aceptadosDelMes = Integer.toString(listaAceptaciones.get(mes));
					String facturacionPesos = Double.toString(listaFacturacion.get(mes));
					String facturacionDolar = Double.toString(listaFacturacionDolar.get(mes));
					String reparados = Integer.toString(listaReparadosXmesXtecnico.get(mes));
					String repEnGtia = Integer.toString(listaRepEnGtiaXmesXtecnico.get(mes));
					String sinFalla = Integer.toString(listaSinFallaXmesXtecnico.get(mes));
					String enRep = Integer.toString(listaEnRepXmesXtecnico.get(mes));
					String ventas = Integer.toString(listaVentasXmesXtecnico.get(mes));
					String sinRep = Integer.toString(listaSinRepXmesXtecnico.get(mes));

					String repAcep = Integer.toString(listaRepAcepXmesXtecnico.get(mes));
					String repNoAcep = Integer.toString(listaRepNoAcepXmesXtecnico.get(mes));
					String repEspera = Integer.toString(listaRepEsperaXmesXtecnico.get(mes));

					ventanaResumenMensualTecnico.getTextRevisados().setText(revisados);
					ventanaResumenMensualTecnico.getTextAceptadosDelMes().setText(aceptadosDelMes);
					// ventanaResumenMensualTecnico.getTextFactTotalPesos().setText(monedaFormatter.formatPeso(facturacionPesos));
					ventanaResumenMensualTecnico.getTextFacturacionPesos()
							.setText(monedaFormatter.formatPeso(facturacionPesos));
					ventanaResumenMensualTecnico.getTextFacturacionDolar()
							.setText(monedaFormatter.formatDolar(facturacionDolar));
					ventanaResumenMensualTecnico.getTextReparados().setText(reparados);
					ventanaResumenMensualTecnico.getTextRepGtia().setText(repEnGtia);
					ventanaResumenMensualTecnico.getTextSinFalla().setText(sinFalla);
					ventanaResumenMensualTecnico.getTextEnRep().setText(enRep);
					ventanaResumenMensualTecnico.getTextVentas().setText(ventas);
					ventanaResumenMensualTecnico.getTextSinRep().setText(sinRep);
					ventanaResumenMensualTecnico.getTextRepAcep().setText(repAcep);
					ventanaResumenMensualTecnico.getTextRepNoAcep().setText(repNoAcep);
					ventanaResumenMensualTecnico.getTextRepEspera().setText(repEspera);

				}
			});

		}

		else if (this.ventanaEstadisticas != null
				&& arg0.getSource() == this.ventanaEstadisticas.getBtnFacturacionPorCliente()) {

			ventanaFacturacionXcliente = new VentanaFacturacionXcliente(this);

			ventanaFacturacionXcliente.getTextAnio().setText(ventanaEstadisticas.getLblAnioDatos().getText());
			cargarTablaFacturacionCliente();
			mostrarGraficoFacturacionXcliente();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			// Calcular las coordenadas para centrar la ventana
			int x = (screenSize.width - 1270) / 2;
			int y = (screenSize.height - 700) / 2;

			// Establecer la posición de la ventana
			// setLocation(x, y);
			ventanaFacturacionXcliente.setBounds(x, y, 1271, 701);
			ventanaFacturacionXcliente.setBounds(x, y, 1270, 700);

		}

		else if (this.ventanaResumenMensualTecnico != null
				&& arg0.getSource() == this.ventanaResumenMensualTecnico.getBtnCalcularComisiones()) {

			calcularComisiones();

		}

		else if (this.ventanaResumenMensualTecnico != null
				&& arg0.getSource() == this.ventanaResumenMensualTecnico.getBtnMostrarResumen()) {

			if (ventanaResumenMensualTecnico.getComboMes() == null) {

				Object mje = "Debe seleccionar un mes.";
				JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);

			} else {

				List<RegistroResumenTecnicoDTO> listaResumenTecnico = new ArrayList<RegistroResumenTecnicoDTO>();

				RegistroResumenTecnicoDTO resumenDatos = TomarDatosPantallaResumenTecnico();

				listaResumenTecnico.add(resumenDatos);

				presentacion.vista.VentanaProgreso progreso = new presentacion.vista.VentanaProgreso(
						"GENERANDO RESUMEN");
				progreso.mostrar();

				final ReporteResumenTecnico[] reporteResumen = new ReporteResumenTecnico[1];
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						reporteResumen[0] = new ReporteResumenTecnico(resumenDatos, listaResumenTecnico);
						return null;
					}

					@Override
					protected void done() {
						progreso.cerrar();
						try {
							get();
							reporteResumen[0].mostrar();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				};
				worker.execute();
			}

		}

	}

	private RegistroResumenTecnicoDTO TomarDatosPantallaResumenTecnico() {

		// TODO Auto-generated method stub

		// POR AÑO
		String NombreTecnico = this.ventanaEstadisticas.getTextNombreTecnico().getText();
		String anio = this.ventanaEstadisticas.getLblAnioDatos().getText();
		String mes = this.ventanaResumenMensualTecnico.getComboMes().getSelectedItem().toString();

		String revisadosAnio = this.ventanaEstadisticas.getTextTotalRevisados().getText();
		String reparadosAnio = this.ventanaEstadisticas.getTextReparadosXTecnico().getText();
		String reparadosEngtiaAnio = this.ventanaEstadisticas.getTextRepGtiaXtecnico().getText();
		String sinFallaAnio = this.ventanaEstadisticas.getTextSinFallasXtecnico().getText();
		String enReparacionAnio = this.ventanaEstadisticas.getTextEnRepXtecnico().getText();
		String ventasAnio = this.ventanaEstadisticas.getTextVentasXtecnico().getText();
		String sinReparacionAnio = this.ventanaEstadisticas.getTextSinRepXtecnico().getText();
		String repAceptadaAnio = this.ventanaEstadisticas.getTextRepAcepXtecnico().getText();
		String repNoAceptAnio = this.ventanaEstadisticas.getTextRepNoAcepXtecnico().getText();
		String repEsperaAnio = this.ventanaEstadisticas.getTextRepEsperaXtecnico().getText();

		// POR MES
		String revisadosMes = this.ventanaResumenMensualTecnico.getTextRevisados().getText();
		String reparadosMes = this.ventanaResumenMensualTecnico.getTextReparados().getText();
		String reparadosEngtiaMes = this.ventanaResumenMensualTecnico.getTextRepGtia().getText();
		String sinFallaMes = this.ventanaResumenMensualTecnico.getTextSinFalla().getText();
		String enReparacionMes = this.ventanaResumenMensualTecnico.getTextEnRep().getText();
		String ventasMes = this.ventanaResumenMensualTecnico.getTextVentas().getText();
		String sinReparacionMes = this.ventanaResumenMensualTecnico.getTextSinRep().getText();
		String repAceptadaMes = this.ventanaResumenMensualTecnico.getTextRepAcep().getText();
		String repNoAceptMes = this.ventanaResumenMensualTecnico.getTextRepNoAcep().getText();
		String repEsperaMes = this.ventanaResumenMensualTecnico.getTextRepEspera().getText();
		String aceptacionesDelMes = this.ventanaResumenMensualTecnico.getTextAceptadosDelMes().getText();

		RegistroResumenTecnicoDTO nuevoResumen = new RegistroResumenTecnicoDTO(NombreTecnico, anio, mes, revisadosAnio,
				reparadosAnio, reparadosEngtiaAnio, sinFallaAnio, enReparacionAnio, ventasAnio, sinReparacionAnio,
				repAceptadaAnio, repNoAceptAnio, repEsperaAnio, revisadosMes, reparadosMes, reparadosEngtiaMes,
				sinFallaMes, enReparacionMes, ventasMes, sinReparacionMes, repAceptadaMes, repNoAceptMes, repEsperaMes,
				aceptacionesDelMes);
		return nuevoResumen;

	}

	protected void habitarDetalles() {

		ventanaCodigoSeguridad.getRdbtnMostrar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ventanaEstadisticas.getLblFacTotalPesos().setVisible(true);
				ventanaEstadisticas.getLblFacTotalDolares().setVisible(true);
				ventanaEstadisticas.getTextFacTotalPesos().setVisible(true);
				ventanaEstadisticas.getTextFacTotalDolares().setVisible(true);

				switch (filtro) {
				case 0:

					ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
					ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);
					ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);
					ventanaEstadisticas.getPanel_Facturacion().setVisible(false);

					break;

				case 1:

					ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);
					ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(true);
					ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);
					ventanaEstadisticas.getPanel_Facturacion().setVisible(true);

					break;

				case 2:

					ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);
					ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
					ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(true);
					ventanaEstadisticas.getPanel_Facturacion().setVisible(true);

					break;

				case 3:

					ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
					ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(true);
					ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);
					ventanaEstadisticas.getPanel_Facturacion().setVisible(true);

					break;

				default:
					break;
				}

				seleccionDetalleEstadisticas = "MOSTRAR DETALLE";

				ventanaCodigoSeguridad.dispose();
				ventanaCodigoSeguridad = null;
			}
		});

		ventanaCodigoSeguridad.getRdbtnOcultar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ventanaEstadisticas.getLblFacTotalPesos().setVisible(false);
				ventanaEstadisticas.getLblFacTotalDolares().setVisible(false);
				ventanaEstadisticas.getTextFacTotalPesos().setVisible(false);
				ventanaEstadisticas.getTextFacTotalDolares().setVisible(false);

				ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
				ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);
				ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);
				ventanaEstadisticas.getPanel_Facturacion().setVisible(false);

				seleccionDetalleEstadisticas = "OCULTAR DETALLE";

				ventanaCodigoSeguridad.dispose();
				ventanaCodigoSeguridad = null;

			}
		});

	}

	protected boolean verificarCodigoSeguridad(String codigo) {

		if (codigo.compareTo(Config.get("security.codigo.acceso", "0000")) == 0) {

			ventanaCodigoSeguridad.getPanelCodigo().setVisible(false);
			ventanaCodigoSeguridad.getPanelDetalle().setVisible(true);

			if (seleccionDetalleEstadisticas.compareTo("MOSTRAR DETALLE") == 0) {

				ventanaCodigoSeguridad.getRdbtnMostrar().setSelected(true);

			}

			else {

				ventanaCodigoSeguridad.getRdbtnOcultar().setSelected(true);
			}

			return true;

		} else {

			JOptionPane.showMessageDialog(null, "Código Incorrecto!", "Acceso denegado",
					JOptionPane.INFORMATION_MESSAGE);
			ventanaCodigoSeguridad.getPanelCodigo().setVisible(true);
			ventanaCodigoSeguridad.getPanelDetalle().setVisible(false);
			return false;

		}
	}

	private void cargarTablaListadoReparaciones() {
	    if (ventanaListadoReparaciones == null) return;

	    // Ignorar clics mientras hay una carga en curso
	    if (cargandoPagina) return;
	    cargandoPagina = true;

	    // Guardar estado de filtros ANTES de limpiar la tabla
	    if (tablaFiltros != null) {
	        guardarEstadoFiltros();
	    }

	    final int offset = paginaActual * REGISTROS_POR_PAGINA;
	    ventanaListadoReparaciones.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

	    new SwingWorker<List<ReparacionDTO>, Void>() {
	        @Override
	        protected List<ReparacionDTO> doInBackground() throws Exception {
	            return modelo.obtenerReparacionPaginada(REGISTROS_POR_PAGINA, offset);
	        }

	        @Override
	        protected void done() {
	            cargandoPagina = false;
	            try {
	                cargarTablaConDatos(get());
	                ventanaListadoReparaciones.setCursor(Cursor.getDefaultCursor());
	            } catch (Exception ex) {
	                ventanaListadoReparaciones.setCursor(Cursor.getDefaultCursor());
	                LogDAO.error("Error al cargar página de reparaciones (offset " + offset + ")", ex);
	            }
	        }
	    }.execute();
	}

	private void cargarTablaConDatos(List<ReparacionDTO> reparaciones) {
	    DefaultTableModel modeloTabla =
	            (DefaultTableModel) ventanaListadoReparaciones.getModelReparaciones();
	    modeloTabla.setRowCount(0);

	    this.Reparaciones_en_tabla = reparaciones;

	    for (ReparacionDTO r : reparaciones) {
	        Object[] fila = {
	            r.getELS(), r.getFecha_Entrada(), r.getCliente(), r.getSucursal(),
	            r.getNombreEquipo(), r.getMarca(), r.getModelo(), r.getNumeroDeSerie(),
	            r.getAviso(), r.getFechadereparacion(), r.getFecha_Salida(),
	            r.getClienteCliente(), r.getEstadoTecnico(), r.getEstadoComercial(),
	            r.getEstadoFisico(), r.getNombreUsuario(), r.getCodigo(),
	            r.getNumeroRemitoSalida(), r.getPresupuestoGenerado(),
	            r.getPresupuestoEnviado(), r.getPrecioPeso(), r.getPrecioDolar(),
	            r.getPago(), r.getLugarDeIngreso()
	        };
	        modeloTabla.addRow(fila);
	    }

	    ventanaListadoReparaciones.setCellRender(
	            ventanaListadoReparaciones.getTblListado());

	    // Crear nuevos autofiltros y restaurar estado previo
	    tablaFiltros = new TablaFiltros();
	    tablaFiltros.agregarAutofiltros(ventanaListadoReparaciones.getTblListado());

	    if (!filtrosActivos.isEmpty()) {
	        restaurarFiltros();
	    }

	    actualizarControlsPaginacion();
	    ventanaListadoReparaciones.setVisible(true);
	}

	private void guardarEstadoFiltros() {
	    JComboBox<String>[] combos = tablaFiltros.getFilterCombos();
	    if (combos == null) return;

	    filtrosActivos.clear();
	    for (int i = 0; i < combos.length; i++) {
	        if (combos[i] != null) {
	            Object seleccionado = combos[i].getSelectedItem();
	            if (seleccionado != null && !"Todos".equals(seleccionado.toString())
	                    && !seleccionado.toString().isEmpty()) {
	                filtrosActivos.put(i, seleccionado.toString());
	            }
	        }
	    }
	}

	private void restaurarFiltros() {
	    JComboBox<String>[] combos = tablaFiltros.getFilterCombos();
	    if (combos == null) return;

	    for (Map.Entry<Integer, String> entry : filtrosActivos.entrySet()) {
	        int col = entry.getKey();
	        String valor = entry.getValue();
	        if (col < combos.length && combos[col] != null) {
	            combos[col].setSelectedItem(valor);
	        }
	    }

	    // Aplicar los filtros sobre la tabla recargada
	    tablaFiltros.filtrarTabla(
	            ventanaListadoReparaciones.getTblListado(), combos);
	}
	
	
	
	
	
	
	
	
	// NUEVO: actualiza label y estado de botones según página actual
	private void actualizarControlsPaginacion() {
	    int desde = paginaActual * REGISTROS_POR_PAGINA + 1;
	    int hasta = Math.min(desde + REGISTROS_POR_PAGINA - 1, totalRegistros);

	    ventanaListadoReparaciones.getLblInfoPagina().setText(
	        String.format("Página %d de %d  |  %d - %d de %d registros",
	            paginaActual + 1, totalPaginas, desde, hasta, totalRegistros));

	    boolean hayAnterior = paginaActual > 0;
	    boolean haySiguiente = paginaActual < totalPaginas - 1;

	    ventanaListadoReparaciones.getBtnPrimero().setEnabled(hayAnterior);
	    ventanaListadoReparaciones.getBtnAnterior().setEnabled(hayAnterior);
	    ventanaListadoReparaciones.getBtnSiguiente().setEnabled(haySiguiente);
	    ventanaListadoReparaciones.getBtnUltimo().setEnabled(haySiguiente);
	}
	
	


	public void agregarListenerVentanaListados() {

		this.ventanaListadoReparaciones.getBtnEstadisticas().addActionListener(this);

		this.ventanaListadoReparaciones.getTblReparaciones().addMouseListener(this);
		this.ventanaListadoReparaciones.getTblReparaciones().addMouseMotionListener(this);
		this.ventanaListadoReparaciones.getTblReparaciones().setTransferHandler(new TableCopyHandler());

		// Configurar atajo de teclado Ctrl+C
		this.ventanaListadoReparaciones.getTblReparaciones().getInputMap().put(KeyStroke.getKeyStroke("control C"),
				"copy");
		ventanaListadoReparaciones.getTblReparaciones().getActionMap().put("copy", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Copiar incluyendo cabeceras
				ventanaListadoReparaciones.getTblReparaciones().getTransferHandler().exportToClipboard(
						ventanaListadoReparaciones.getTblReparaciones(),
						ventanaListadoReparaciones.getTblReparaciones().getToolkit().getSystemClipboard(),
						TransferHandler.COPY);
			}
		});

		this.ventanaListadoReparaciones.getChckbxELS().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxELS().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxELS().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEntrada().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEntrada().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEntrada().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxCliente().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxCliente().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxCliente().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxSucursal().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxSucursal().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxSucursal().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEquipo().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEquipo().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEquipo().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxMarca().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxMarca().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxMarca().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxModelo().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxModelo().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxModelo().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxSerie().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxSerie().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxSerie().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxAviso().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxAviso().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxAviso().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxRevisión().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxRevisión().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxRevisión().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxSalida().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxSalida().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxSalida().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxClienteCliente().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxClienteCliente().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxClienteCliente().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEstadoTec().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoTec().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoTec().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEstadoCom().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoCom().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoCom().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxEstadoFis().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoFis().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxEstadoFis().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxTecnico().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxTecnico().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxTecnico().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxUbicacionRemito().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxUbicacionRemito().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxUbicacionRemito().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxNumeroRemito().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxNumeroRemito().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxNumeroRemito().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxIngreso().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxIngreso().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxIngreso().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPrecioPeso().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioPeso().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioPeso().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPrecioDolar().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioDolar().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPrecioDolar().addMouseListener(this);

		this.ventanaListadoReparaciones.getChckbxPago().addActionListener(this);
		this.ventanaListadoReparaciones.getChckbxPago().addItemListener(this);
		this.ventanaListadoReparaciones.getChckbxPago().addMouseListener(this);
		
		// NUEVO: listeners de paginación
		this.ventanaListadoReparaciones.getBtnPrimero().addActionListener(e -> {
		    paginaActual = 0;
		    cargarTablaListadoReparaciones();
		});

		this.ventanaListadoReparaciones.getBtnAnterior().addActionListener(e -> {
		    if (paginaActual > 0) {
		        paginaActual--;
		        cargarTablaListadoReparaciones();
		    }
		});

		this.ventanaListadoReparaciones.getBtnSiguiente().addActionListener(e -> {
		    if (paginaActual < totalPaginas - 1) {
		        paginaActual++;
		        cargarTablaListadoReparaciones();
		    }
		});

		this.ventanaListadoReparaciones.getBtnUltimo().addActionListener(e -> {
		    paginaActual = totalPaginas - 1;
		    cargarTablaListadoReparaciones();
		});
		
		

	}

	private void configurarVista() {
		ventanaListadoReparaciones.configurarOpcionesColumnas(nombresColumnas);

		// Asegurar que la ventana emergente sigue al botón al redimensionar
		ventanaListadoReparaciones.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (ventanaListadoReparaciones.windowOpciones.isVisible()) {
					ventanaListadoReparaciones.mostrarOpcionesColumnas(true);
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				if (ventanaListadoReparaciones.windowOpciones.isVisible()) {
					ventanaListadoReparaciones.mostrarOpcionesColumnas(true);
				}
			}
		});
	}

	private void configurarEventos() {
		// Evento para el botón toggle
		ventanaListadoReparaciones.toggleBtnOcultar.addActionListener(e -> {
			boolean seleccionado = ventanaListadoReparaciones.toggleBtnOcultar.isSelected();
			ventanaListadoReparaciones.mostrarOpcionesColumnas(seleccionado);
		});

		// Configurar listener para clic fuera (ahora como variable de clase)
		clickOutsideListener = new AWTEventListener() {
			public void eventDispatched(AWTEvent event) {
				if (ventanaListadoReparaciones == null || ventanaListadoReparaciones.windowOpciones == null)
					return; // Verificación crítica

				if (event.getID() == MouseEvent.MOUSE_PRESSED
						&& ventanaListadoReparaciones.windowOpciones.isVisible()) {

					MouseEvent me = (MouseEvent) event;
					Component source = me.getComponent();

					if (source != ventanaListadoReparaciones.toggleBtnOcultar
							&& !isChildOf(ventanaListadoReparaciones.windowOpciones, source)) {

						SwingUtilities.invokeLater(() -> {
							ventanaListadoReparaciones.mostrarOpcionesColumnas(false);
							ventanaListadoReparaciones.toggleBtnOcultar.setSelected(false);
						});
					}
				}
			}
		};
		Toolkit.getDefaultToolkit().addAWTEventListener(clickOutsideListener, AWTEvent.MOUSE_EVENT_MASK);

		// Limpiar al cerrar la ventana
		ventanaListadoReparaciones.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				limpiarRecursos();
			}
		});

	}

	private void limpiarRecursos() {
		if (clickOutsideListener != null) {
			Toolkit.getDefaultToolkit().removeAWTEventListener(clickOutsideListener);
		}
		ventanaListadoReparaciones = null; // Ayuda al GC
	}

	private boolean isChildOf(Component parent, Component child) {
		while (child != null) {
			if (child == parent) {
				return true;

			}
			child = child.getParent();
		}
		return false;
	}

	private void cargarTablaFacturacionCliente() {
		// Limpia la tabla
		this.ventanaFacturacionXcliente.getModelFacturacionClientes().setRowCount(0);
		this.ventanaFacturacionXcliente.getModelFacturacionClientes().setColumnCount(0);
		this.ventanaFacturacionXcliente.getModelFacturacionClientes()
				.setColumnIdentifiers(this.ventanaFacturacionXcliente.getNombreColumnas());

		double porcentaje = 0.0;
		double porcentajeOtros = 0.0;
		double facturacionOtros = 0.0;

		// Obtiene la facturación por cliente
		this.itemFacturacion_en_tabla = (List<FacturacionXclienteDTO>) modelo.dameFacturacionXcliente(anio);

		// Agrupa clientes pequeños
		for (int i = 0; i < this.itemFacturacion_en_tabla.size(); i++) {
			porcentaje = porcentaje(this.itemFacturacion_en_tabla.get(i).getFacturacion(), facturacionPesoPorAnio);
			if (porcentaje < 2.0) {
				porcentajeOtros += porcentaje;
				facturacionOtros += this.itemFacturacion_en_tabla.get(i).getFacturacion();
				this.itemFacturacion_en_tabla.get(i).setNombreCliente("Otros");
				this.itemFacturacion_en_tabla.get(i).setFacturacion(facturacionOtros);
			}
		}

		// Elimina duplicados manteniendo el último
		Set<String> stringsVistos = new HashSet<>();
		List<FacturacionXclienteDTO> nuevaLista = new ArrayList<>();
		for (int i = itemFacturacion_en_tabla.size() - 1; i >= 0; i--) {
			String str = itemFacturacion_en_tabla.get(i).getNombreCliente();
			if (!stringsVistos.contains(str)) {
				nuevaLista.add(itemFacturacion_en_tabla.get(i));
				stringsVistos.add(str);
			}
		}

		// Limpia y reconstruye la lista original
		itemFacturacion_en_tabla.clear();
		itemFacturacion_en_tabla.addAll(nuevaLista);
		Collections.reverse(itemFacturacion_en_tabla);

		// Ordena de mayor a menor por facturación
		Collections.sort(itemFacturacion_en_tabla, new Comparator<FacturacionXclienteDTO>() {
			@Override
			public int compare(FacturacionXclienteDTO t1, FacturacionXclienteDTO t2) {
				return Double.compare(t2.getFacturacion(), t1.getFacturacion());
			}
		});

		// Formato de números
		java.text.NumberFormat formatoNumero = java.text.NumberFormat.getNumberInstance();
		formatoNumero.setMaximumFractionDigits(2);
		formatoNumero.setGroupingUsed(true);

		// Llena la tabla
		for (FacturacionXclienteDTO item : this.itemFacturacion_en_tabla) {
			porcentaje = porcentaje(item.getFacturacion(), facturacionPesoPorAnio);

			// Formatear el número sin notación científica
			String facturacionFormateada = "$ " + formatoNumero.format(item.getFacturacion());
			String porcentajeFormateado = String.format("%.1f %%", porcentaje);

			Object[] fila = { item.getNombreCliente(), facturacionFormateada, porcentajeFormateado };
			this.ventanaFacturacionXcliente.getModelFacturacionClientes().addRow(fila);
		}

		this.ventanaFacturacionXcliente.show();
		ventanaFacturacionXcliente.setCellRender(this.ventanaFacturacionXcliente.getTblFacturacionClientes());
	}

	public void cerraVentanaListadoEquipos() {

		this.ventanaListadoReparaciones.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {

				ventanaListadoReparaciones.dispose();
				ventanaListadoReparaciones = null;

				if (ventanaEquipos != null) {

					ventanaEquipos.dispose();
					ventanaEquipos = null;

				}

				for (VentanaVisualizarEquipos ventana : controladorReparacion.getVentanasAbiertas()) {
					if (ventana != null) {
						ventana.dispose(); // Cierra la ventana
					}
				}
				controladorReparacion.getVentanasAbiertas().clear(); // Limpia la lista de ventanas abiertas

			}

		});

	}

	private void agregarListenerAventanaEstadisticas() {

		ventanaEstadisticas.getComboFiltro().addActionListener(this);
		ventanaEstadisticas.getComboAnio().addActionListener(this);
		ventanaEstadisticas.getComboTecnico().addActionListener(this);
		ventanaEstadisticas.getComboCliente().addActionListener(this);
		ventanaEstadisticas.getBtnConfiguracion().addActionListener(this);
		ventanaEstadisticas.getBtnFacturacionPorCliente().addActionListener(this);
		ventanaEstadisticas.getBtnResumenMensualTecnico().addActionListener(this);

		// Cleanup on window close
		ventanaEstadisticas.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				chartPanelIngresos = null;
				chartPanelDiagnosticos = null;
				chartPanelFacturacion = null;
				cacheGraficosAnio.clear();
				cacheGraficosTecnico.clear();
				cacheGraficosCliente.clear();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void llenarcomboFiltro() {

		ventanaEstadisticas.getComboFiltro().addItem("--Seleccionar filtro--");
		ventanaEstadisticas.getComboFiltro().addItem("POR AÑO");
		ventanaEstadisticas.getComboFiltro().addItem("POR TÉCNICO");
		ventanaEstadisticas.getComboFiltro().addItem("POR CLIENTE");

		llenarcomboAnio();

		ventanaEstadisticas.getComboFiltro().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() != ItemEvent.SELECTED || ventanaEstadisticas.getComboFiltro().getSelectedItem() == null) {
					return;
				}

				filtro = ventanaEstadisticas.getComboFiltro().getSelectedIndex();

				cacheGraficosAnio.clear();
				cacheGraficosTecnico.clear();
				cacheGraficosCliente.clear();

				switch (filtro) {

					case 0:

						ventanaEstadisticas.getLblAnio().setVisible(false);
						ventanaEstadisticas.getComboAnio().setVisible(false);

						ventanaEstadisticas.getLblTecnico().setVisible(false);
						ventanaEstadisticas.getComboTecnico().setVisible(false);

						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);

						inicializarChartPanelsSiNecesario();
						chartPanelIngresos.setChart(crearChartVacio(""));
						chartPanelDiagnosticos.setChart(crearChartVacio(""));
						chartPanelFacturacion.setChart(crearChartVacio(""));

						break;

					case 1:

						ventanaEstadisticas.getLblAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
						ventanaEstadisticas.getComboAnio().setVisible(true);

						ventanaEstadisticas.getLblTecnico().setVisible(false);
						ventanaEstadisticas.getComboTecnico().setVisible(false);
						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);

						break;

					case 2:

						ventanaEstadisticas.getLblAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
						ventanaEstadisticas.getComboAnio().setVisible(true);

						llenarcomboTecnico();
						ventanaEstadisticas.getComboTecnico().setSelectedIndex(-1);

						ventanaEstadisticas.getLblTecnico().setText("TÉCNICO");
						ventanaEstadisticas.getLblTecnico().setVisible(true);

						ventanaEstadisticas.getComboTecnico().setVisible(true);
						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);

						break;
					case 3:

						ventanaEstadisticas.getLblAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
						ventanaEstadisticas.getComboCliente().setVisible(true);
						llenarcomboClientes();

						ventanaEstadisticas.getLblTecnico().setText("CLIENTE");
						ventanaEstadisticas.getLblTecnico().setVisible(true);

						ventanaEstadisticas.getComboTecnico().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);

						break;

					default:
						break;
					}

			}
		});

	}

	@SuppressWarnings("unchecked")
	private void llenarcomboAnio() {

		for (int i = 2017; i < 2030; i++) {

			ventanaEstadisticas.getComboAnio().addItem(i);

		}

		ventanaEstadisticas.getComboAnio().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() != ItemEvent.SELECTED || ventanaEstadisticas.getComboAnio().getSelectedItem() == null) {
					return;
				}

				ventanaEstadisticas.getLblAnioDatos()
						.setText(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
				ventanaEstadisticas.getPanel_Datos().setVisible(true);

				cacheGraficosAnio.clear();
				cacheGraficosTecnico.clear();
				cacheGraficosCliente.clear();

				llenarDatosAnuales();

				switch (filtro) {
					case 0:

						inicializarChartPanelsSiNecesario();
						chartPanelIngresos.setChart(crearChartVacio(""));
						chartPanelDiagnosticos.setChart(crearChartVacio(""));
						chartPanelFacturacion.setChart(crearChartVacio(""));

						ventanaEstadisticas.getPanel_datosPorAnio().setVisible(false);
						ventanaEstadisticas.getPanel_datosPorCliente().setVisible(false);
						ventanaEstadisticas.getPanel_datosPorTecnico().setVisible(false);

						ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);

						break;
					case 1:

						llenarDatosPorAnio();

						ventanaEstadisticas.getPanel_datosPorCliente().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);

						ventanaEstadisticas.getPanel_datosPorTecnico().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);

						ventanaEstadisticas.getPanel_datosPorAnio().setVisible(true);

						if (seleccionDetalleEstadisticas.compareTo("MOSTRAR DETALLE") == 0) {

							ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(true);

						}

						mostrarGraficosPorAnio();

						break;

					case 2:

						llenarDatosPorTecnico();

						ventanaEstadisticas.getPanel_datosPorCliente().setVisible(false);
						ventanaEstadisticas.getPanel_datosPorAnio().setVisible(false);

						ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);

						ventanaEstadisticas.getPanel_datosPorTecnico().setVisible(true);

						if (seleccionDetalleEstadisticas.compareTo("MOSTRAR DETALLE") == 0) {

							ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(true);
						}

						if (ventanaEstadisticas.getComboTecnico() == null
								|| ventanaEstadisticas.getComboTecnico().getSelectedIndex() == -1) {

							Object mje = "Debe Seleccionar primero al técnico";
							JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo",
									JOptionPane.INFORMATION_MESSAGE);

						} else {
							mostrarGraficosPorTecnico();
						}
						break;

					case 3:

						llenarDatosPorCliente();

						ventanaEstadisticas.getPanel_datosPorAnio().setVisible(false);
						ventanaEstadisticas.getPanel_datosPorTecnico().setVisible(false);

						ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);

						ventanaEstadisticas.getPanel_datosPorCliente().setVisible(true);

						if (seleccionDetalleEstadisticas.compareTo("MOSTRAR DETALLE") == 0) {

							ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(true);

						}

						if (ventanaEstadisticas.getComboCliente() == null
								|| ventanaEstadisticas.getComboCliente().getSelectedIndex() == -1) {

							Object mje = "Debe Seleccionar primero al Cliente";
							JOptionPane.showMessageDialog(null, mje, "Mensaje Informativo",
									JOptionPane.INFORMATION_MESSAGE);

						} else {
							mostrarGraficosPorCliente();
						}
						break;

					default:
						break;
					}

			}
		});

	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void llenarcomboMesResumen() {

		String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };

		for (int i = 0; i < 12; i++) {

			ventanaResumenMensualTecnico.getComboMes().addItem(meses[i]);

		}

		ventanaResumenMensualTecnico.getComboMes().setSelectedIndex(-1);
	}

	private void llenarcomboTecnico() {

		modelo.ListarTecnicos(ventanaEstadisticas.getComboTecnico());

		ventanaEstadisticas.getComboTecnico().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}

				if (ventanaEstadisticas.getComboTecnico().getSelectedItem() != null) {

					ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
					cacheGraficosTecnico.clear();

				} else {
					// Selection cleared
					inicializarChartPanelsSiNecesario();
					chartPanelIngresos.setChart(crearChartVacio(""));
					chartPanelDiagnosticos.setChart(crearChartVacio(""));
					chartPanelFacturacion.setChart(crearChartVacio(""));
				}

			}

		});

	}

	private void llenarcomboClientes() {

		modelo.ListarCliente(ventanaEstadisticas.getComboCliente());

		ventanaEstadisticas.getComboCliente().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}

				if (ventanaEstadisticas.getComboCliente().getSelectedItem() != null) {

					ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
					cacheGraficosCliente.clear();

				} else {
					// Selection cleared
					inicializarChartPanelsSiNecesario();
					chartPanelIngresos.setChart(crearChartVacio(""));
					chartPanelDiagnosticos.setChart(crearChartVacio(""));
					chartPanelFacturacion.setChart(crearChartVacio(""));
				}

			}

		});

	}

	private double porcentaje(double parte, double total) {
		if (total <= 0) {
			return 0.0;
		}
		return (parte / total) * 100;
	}

	private void llenarDatosAnuales() {

		anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		totalesAnioActual = modelo.obtenerTotalesPorAnio(anio);
		cantidadIngresosPorAnio = totalesAnioActual.ingresos;
		cantidadDiagnosticosPorAnio = totalesAnioActual.diagnosticos;
		facturacionPesoPorAnio = totalesAnioActual.facturacionPeso;
		facturacionDolarPorAnio = totalesAnioActual.facturacionDolar;

		ventanaEstadisticas.getTextIngresosTotales().setText(Integer.toString(cantidadIngresosPorAnio));
		ventanaEstadisticas.getTextDiagnosticosTotales().setText(Integer.toString(cantidadDiagnosticosPorAnio));

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String facturacionStrPeso = decimalFormat.format(facturacionPesoPorAnio);
		String facturacionStrDolar = decimalFormat.format(facturacionDolarPorAnio);

		ventanaEstadisticas.getTextFacTotalPesos().setText(monedaFormatter.formatPeso(facturacionStrPeso));

		ventanaEstadisticas.getTextFacTotalDolares().setText(monedaFormatter.formatDolar(facturacionStrDolar));

	}

	private void llenarDatosPorAnio() {

		persistencia.dao.mysql.ReparacionEstadisticasManager.TotalesPorAnio totales = totalesAnioActual;
		if (totales == null) {
			totales = modelo.obtenerTotalesPorAnio(anio);
		}
		int cantidadReparadosPorAnio = totales.reparados;
		int cantidadSinFallaPorAnio = totales.sinFallas;
		int cantidadRepEnGtiaPorAnio = totales.enGtia;
		int cantidadEnRepPorAnio = totales.enRep;
		int cantidadVentasPorAnio = totales.ventas;
		int cantidadSinRepAnio = totales.sinRep;
		int cantidadReparadosAceptradosPorAnio = totales.repAcep;
		int cantidadReparadosNoAceptradosPorAnio = totales.repNoAcep;
		int cantidadReparadosAlaEsperaPorAnio = totales.repEspera;

		ventanaEstadisticas.getTextReparados().setText(Integer.toString(cantidadReparadosPorAnio));
		ventanaEstadisticas.getTextSinFalla().setText(Integer.toString(cantidadSinFallaPorAnio));
		ventanaEstadisticas.getTextRepEnGtia().setText(Integer.toString(cantidadRepEnGtiaPorAnio));
		ventanaEstadisticas.getTextEnReparacion().setText(Integer.toString(cantidadEnRepPorAnio));
		ventanaEstadisticas.getTextVentas().setText(Integer.toString(cantidadVentasPorAnio));
		ventanaEstadisticas.getTextSinReparacion().setText(Integer.toString(cantidadSinRepAnio));
		ventanaEstadisticas.getTextReparadosAceptados().setText(Integer.toString(cantidadReparadosAceptradosPorAnio));
		ventanaEstadisticas.getTextReparadosNoAceptados()
				.setText(Integer.toString(cantidadReparadosNoAceptradosPorAnio));
		ventanaEstadisticas.getTextRepEspera().setText(Integer.toString(cantidadReparadosAlaEsperaPorAnio));

		double porcentajeReparadosPorAnio = porcentaje(cantidadReparadosPorAnio, cantidadDiagnosticosPorAnio);
		String porcentajeReparados = String.format("%.1f %%", porcentajeReparadosPorAnio);
		ventanaEstadisticas.getTextPorcentajeReparados().setText(porcentajeReparados);

		double porcentajeRepEnGtiaPorAnio = porcentaje(cantidadRepEnGtiaPorAnio, cantidadDiagnosticosPorAnio);
		String porcentajeEnGtia = String.format("%.1f %%", porcentajeRepEnGtiaPorAnio);
		ventanaEstadisticas.getTextPorcentajeRepEnGtia().setText(porcentajeEnGtia);

		double porcentajeSinFallaPorAnio = porcentaje(cantidadSinFallaPorAnio, cantidadDiagnosticosPorAnio);
		String porcentajeSinFalla = String.format("%.1f %%", porcentajeSinFallaPorAnio);
		ventanaEstadisticas.getTextPorcentajeSinFalla().setText(porcentajeSinFalla);

		double porcentajeEnRePorAnio = porcentaje(cantidadEnRepPorAnio, cantidadDiagnosticosPorAnio);
		String porcentajeEnRep = String.format("%.1f %%", porcentajeEnRePorAnio);
		ventanaEstadisticas.getTextPorcentajeEnReparacion().setText(porcentajeEnRep);

		double porcentajeVentasPorAnio = porcentaje(cantidadVentasPorAnio, cantidadDiagnosticosPorAnio);
		String porcentajeVentas = String.format("%.1f %%", porcentajeVentasPorAnio);
		ventanaEstadisticas.getTextPorcentajeVentas().setText(porcentajeVentas);

		double porcentajeSinRepPorAnio = porcentaje(cantidadSinRepAnio, cantidadDiagnosticosPorAnio);
		String porcentajeSinRep = String.format("%.1f %%", porcentajeSinRepPorAnio);
		ventanaEstadisticas.getTextPorcentajeSinReparacion().setText(porcentajeSinRep);

		double porcentajeRepAcepPorAnio = porcentaje(cantidadReparadosAceptradosPorAnio, cantidadReparadosPorAnio);
		String porcentajeRepAcep = String.format("%.1f %%", porcentajeRepAcepPorAnio);
		ventanaEstadisticas.getTextPorcentajeReparadosAceptados().setText(porcentajeRepAcep);

		double porcentajeRepNoAcepPorAnio = porcentaje(cantidadReparadosNoAceptradosPorAnio, cantidadReparadosPorAnio);
		String porcentajeRepNoAcep = String.format("%.1f %%", porcentajeRepNoAcepPorAnio);
		ventanaEstadisticas.getTextPorcentajeReparadosNoAceptados().setText(porcentajeRepNoAcep);

		double porcentajeRepEsperaPorAnio = porcentaje(cantidadReparadosAlaEsperaPorAnio, cantidadReparadosPorAnio);
		String porcentajeRepEspera = String.format("%.1f %%", porcentajeRepEsperaPorAnio);
		ventanaEstadisticas.getTextPorcentajeRepEspera().setText(porcentajeRepEspera);

	}

	private void llenarDatosPorCliente() {

		String cliente = ventanaEstadisticas.getComboCliente().getSelectedItem().toString();

		int idCliente = modelo.idClienteporNombre(cliente);

		ventanaEstadisticas.getTextNombreCliente().setText(cliente);

		int totalIngresosXanioXcliente = modelo.dameTotalIngresosXanioXcliente(anio, idCliente);
		int totalReparadosXanioXcliente = modelo.dameTotalReparadosXanioXcliente(anio, idCliente);
		int totalSinFallaXanioXcliente = modelo.dameTotalSinFallaXanioXcliente(anio, idCliente);
		int totalGtiaXanioXcliente = modelo.dameTotalGtiaXanioXcliente(anio, idCliente);
		int totalEnRepXanioXcliente = modelo.dameTotalEnRepXanioXclientecliente(anio, idCliente);
		int totalVentaXanioXcliente = modelo.dameTotalVentasXanioXcliente(anio, idCliente);
		int totalSinRepXanioXcliente = modelo.dameTotalSinRepXanioXcliente(anio, idCliente);

		int TotalReparadosAceptradosXcliente = modelo.dameTotalRepAcepXcliente(anio, idCliente);
		int TotalReparadosNoAceptradosXcliente = modelo.dameTotalRepNoAcepXcliente(anio, idCliente);
		int TotalReparadosAlaEsperaXcliente = modelo.dameTotalRepEsperaXcliente(anio, idCliente);

		double facturacionPesoPorAnioPorCliente = modelo.dameFacturacionPesoPorAnioPorCliente(anio, idCliente);
		double facturacionDolarPorAnioPorCliente = modelo.dameFacturacionDolarPorAnioPorCliente(anio, idCliente);

		ventanaEstadisticas.getTextIngresosPorCliente().setText(Integer.toString(totalIngresosXanioXcliente));

		ventanaEstadisticas.getTextReparadosPorCliente().setText(Integer.toString(totalReparadosXanioXcliente));
		ventanaEstadisticas.getTextSinFallaPorCliente().setText(Integer.toString(totalSinFallaXanioXcliente));
		ventanaEstadisticas.getTextRepEnGtiaPorCliente().setText(Integer.toString(totalGtiaXanioXcliente));
		ventanaEstadisticas.getTextEnRepPorCLiente().setText(Integer.toString(totalEnRepXanioXcliente));
		ventanaEstadisticas.getTextVentasPorCliente().setText(Integer.toString(totalVentaXanioXcliente));
		ventanaEstadisticas.getTextSinRepPorCliente().setText(Integer.toString(totalSinRepXanioXcliente));

		ventanaEstadisticas.getTextRepAcepPorCliente().setText(Integer.toString(TotalReparadosAceptradosXcliente));
		ventanaEstadisticas.getTextRepNoAcepPorCliente().setText(Integer.toString(TotalReparadosNoAceptradosXcliente));
		ventanaEstadisticas.getTextRepEsperaPorCliente().setText(Integer.toString(TotalReparadosAlaEsperaXcliente));

		double porcentajeIngresosPorCliente = porcentaje(totalIngresosXanioXcliente, cantidadIngresosPorAnio);
		String porcentajeIngresos = String.format("%.1f %%", porcentajeIngresosPorCliente);
		ventanaEstadisticas.getTextPorcIngresosPorCliente().setText(porcentajeIngresos);

		double porcentajeReparadosPorCliente = porcentaje(totalReparadosXanioXcliente, totalIngresosXanioXcliente);
		String porcentajeReparados = String.format("%.1f %%", porcentajeReparadosPorCliente);
		ventanaEstadisticas.getTextPorcRepPorCliente().setText(porcentajeReparados);

		double porcentajeRepEnGtiaPorCliente = porcentaje(totalGtiaXanioXcliente, totalIngresosXanioXcliente);
		String porcentajeEnGtia = String.format("%.1f %%", porcentajeRepEnGtiaPorCliente);
		ventanaEstadisticas.getTextPorcRepEnGtiaPorCliente().setText(porcentajeEnGtia);

		double porcentajeSinFallaPorCliente = porcentaje(totalSinFallaXanioXcliente, totalIngresosXanioXcliente);
		String porcentajeSinFalla = String.format("%.1f %%", porcentajeSinFallaPorCliente);
		ventanaEstadisticas.getTextPorcSinFallaPorCliente().setText(porcentajeSinFalla);

		double porcentajeEnRePorCliente = porcentaje(totalEnRepXanioXcliente, totalIngresosXanioXcliente);
		String porcentajeEnRep = String.format("%.1f %%", porcentajeEnRePorCliente);
		ventanaEstadisticas.getTextPorcEnRepPorCliente().setText(porcentajeEnRep);

		double porcentajeVentasPorCliente = porcentaje(totalVentaXanioXcliente, totalIngresosXanioXcliente);
		String porcentajeVentas = String.format("%.1f %%", porcentajeVentasPorCliente);
		ventanaEstadisticas.getTextPorcVentasPorCliente().setText(porcentajeVentas);

		double porcentajeSinRepPorCliente = porcentaje(totalSinRepXanioXcliente, totalIngresosXanioXcliente);
		String porcentajeSinRep = String.format("%.1f %%", porcentajeSinRepPorCliente);
		ventanaEstadisticas.getTextPorcSinRepPorCliente().setText(porcentajeSinRep);

		double porcentajeRepAcepPorCliente = porcentaje(TotalReparadosAceptradosXcliente, totalReparadosXanioXcliente);
		String porcentajeRepAcep = String.format("%.1f %%", porcentajeRepAcepPorCliente);
		ventanaEstadisticas.getTextPorcRepAcepPorCliente().setText(porcentajeRepAcep);

		double porcentajeRepNoAcepPorCliente = porcentaje(TotalReparadosNoAceptradosXcliente, totalReparadosXanioXcliente);
		String porcentajeRepNoAcep = String.format("%.1f %%", porcentajeRepNoAcepPorCliente);
		ventanaEstadisticas.getTextPorcRepNoAcepPorCliente().setText(porcentajeRepNoAcep);

		double porcentajeRepEsperaPorCliente = porcentaje(TotalReparadosAlaEsperaXcliente, totalReparadosXanioXcliente);
		String porcentajeRepEspera = String.format("%.1f %%", porcentajeRepEsperaPorCliente);
		ventanaEstadisticas.getTextPorcRepEsperaPorCliente().setText(porcentajeRepEspera);

		ventanaEstadisticas.getTextFactClientePesos()
				.setText(monedaFormatter.formatPeso(Double.toString(facturacionPesoPorAnioPorCliente)));
		ventanaEstadisticas.getTextFactClienteDolar()
				.setText(monedaFormatter.formatDolar(Double.toString(facturacionDolarPorAnioPorCliente)));

		double porcentaFacturacionPesoPorCliente = porcentaje(facturacionPesoPorAnioPorCliente, facturacionPesoPorAnio);
		String porcentaFacturacionpeso = String.format("%.1f %%", porcentaFacturacionPesoPorCliente);
		ventanaEstadisticas.getTextPorcFacturacionPesoCliente().setText(porcentaFacturacionpeso);

		double porcentaFacturacionDolarPorCliente = porcentaje(facturacionDolarPorAnioPorCliente, facturacionDolarPorAnio);
		String porcentaFacturacionDolar = String.format("%.1f %%", porcentaFacturacionDolarPorCliente);
		ventanaEstadisticas.getTextPorcFacturacionDolarCliente().setText(porcentaFacturacionDolar);

	}

	private void llenarDatosPorTecnico() {

		String nombreTecnico = ventanaEstadisticas.getComboTecnico().getSelectedItem().toString();
		int idTecnico = modelo.idUsuarioporNombre(nombreTecnico);
		ventanaEstadisticas.getTextNombreTecnico().setText(nombreTecnico);

		int totalDiagnosticosXanioXtecnico = modelo.dameTotalDiagnosticosXanioXtecnico(anio, idTecnico);

		int totalReparadosXanioXtecnico = modelo.dameTotalReparadosXanioXtecnico(anio, idTecnico);
		int totalSinFallaXanioXtecnico = modelo.dameTotalSinFallaXanioXtecnico(anio, idTecnico);
		int totalGtiaXanioXtecnico = modelo.dameTotalGtiaXanioXtecnico(anio, idTecnico);
		int totalEnRepXanioXtecnico = modelo.dameTotalEnRepXanioXtecnico(anio, idTecnico);
		int totalVentaXanioXtecnico = modelo.dameTotalVentasXanioXtecnico(anio, idTecnico);
		int totalSinRepXanioXtecnico = modelo.dameTotalSinRepXanioXtecnico(anio, idTecnico);

		int TotalReparadosAceptradosXtecnico = modelo.dameTotalRepAcepXtecnico(anio, idTecnico);
		int TotalReparadosNoAceptradosXtecnico = modelo.dameTotalRepNoAcepXtecnico(anio, idTecnico);
		int TotalReparadosAlaEsperaXtecnico = modelo.dameTotalRepEsperaXtecnico(anio, idTecnico);

		double facturacionPesoPorAnioPorTecnico = modelo.dameFacturacionPesoPorAnioPorTecnico(anio, idTecnico);
		double facturacionDolarPorAnioPorTecnico = modelo.dameFacturacionDolarPorAnioPorTecnico(anio, idTecnico);

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String facturacionStrPesoXtecnico = decimalFormat.format(facturacionPesoPorAnioPorTecnico);
		String facturacionStrDolarXtecnico = decimalFormat.format(facturacionDolarPorAnioPorTecnico);

		ventanaEstadisticas.getTextTotalRevisados().setText(Integer.toString(totalDiagnosticosXanioXtecnico));

		ventanaEstadisticas.getTextReparadosXTecnico().setText(Integer.toString(totalReparadosXanioXtecnico));
		ventanaEstadisticas.getTextSinFallasXtecnico().setText(Integer.toString(totalSinFallaXanioXtecnico));
		ventanaEstadisticas.getTextRepGtiaXtecnico().setText(Integer.toString(totalGtiaXanioXtecnico));
		ventanaEstadisticas.getTextEnRepXtecnico().setText(Integer.toString(totalEnRepXanioXtecnico));
		ventanaEstadisticas.getTextVentasXtecnico().setText(Integer.toString(totalVentaXanioXtecnico));
		ventanaEstadisticas.getTextSinRepXtecnico().setText(Integer.toString(totalSinRepXanioXtecnico));

		ventanaEstadisticas.getTextRepAcepXtecnico().setText(Integer.toString(TotalReparadosAceptradosXtecnico));
		ventanaEstadisticas.getTextRepNoAcepXtecnico().setText(Integer.toString(TotalReparadosNoAceptradosXtecnico));
		ventanaEstadisticas.getTextRepEsperaXtecnico().setText(Integer.toString(TotalReparadosAlaEsperaXtecnico));

		double porcentajeDiagnosticosPorTecnico = porcentaje(totalDiagnosticosXanioXtecnico, cantidadDiagnosticosPorAnio);
		String porcentajeDiagnosticos = String.format("%.1f %%", porcentajeDiagnosticosPorTecnico);
		ventanaEstadisticas.getTextPorcentajeTotalRevisado().setText(porcentajeDiagnosticos);

		double porcentajeReparadosPorTecnico = porcentaje(totalReparadosXanioXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeReparados = String.format("%.1f %%", porcentajeReparadosPorTecnico);
		ventanaEstadisticas.getTextPorcReparadosXTecnico().setText(porcentajeReparados);

		double porcentajeRepEnGtiaPorTecnico = porcentaje(totalGtiaXanioXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeEnGtia = String.format("%.1f %%", porcentajeRepEnGtiaPorTecnico);
		ventanaEstadisticas.getTextPorcRepGtiaXtecnico().setText(porcentajeEnGtia);

		double porcentajeSinFallaPorTecnico = porcentaje(totalSinFallaXanioXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeSinFalla = String.format("%.1f %%", porcentajeSinFallaPorTecnico);
		ventanaEstadisticas.getTextPorcSinFallasXtecnico().setText(porcentajeSinFalla);

		double porcentajeEnRePorTecnico = porcentaje(totalEnRepXanioXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeEnRep = String.format("%.1f %%", porcentajeEnRePorTecnico);
		ventanaEstadisticas.getTextPorcEnRepXtecnico().setText(porcentajeEnRep);

		double porcentajeVentasPorTecnico = porcentaje(totalVentaXanioXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeVentas = String.format("%.1f %%", porcentajeVentasPorTecnico);
		ventanaEstadisticas.getTextPorcVentasXtecnico().setText(porcentajeVentas);

		double porcentajeSinRepPorTecnico = porcentaje(totalSinRepXanioXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeSinRep = String.format("%.1f %%", porcentajeSinRepPorTecnico);
		ventanaEstadisticas.getTextPorcSinRepXtecnico().setText(porcentajeSinRep);

		double porcentajeRepAcepPorTecnico = porcentaje(TotalReparadosAceptradosXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeRepAcep = String.format("%.1f %%", porcentajeRepAcepPorTecnico);
		ventanaEstadisticas.getTextPorcRepAcepXtecnico().setText(porcentajeRepAcep);

		double porcentajeRepNoAcepPorTecnico = porcentaje(TotalReparadosNoAceptradosXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeRepNoAcep = String.format("%.1f %%", porcentajeRepNoAcepPorTecnico);
		ventanaEstadisticas.getTextPorcRepNoAcepXtecnico().setText(porcentajeRepNoAcep);

		double porcentajeRepEsperaPorTecnico = porcentaje(TotalReparadosAlaEsperaXtecnico, totalDiagnosticosXanioXtecnico);
		String porcentajeRepEspera = String.format("%.1f %%", porcentajeRepEsperaPorTecnico);
		ventanaEstadisticas.getTextPorcRepEsperaXtecnico().setText(porcentajeRepEspera);

		ventanaEstadisticas.getTextFacturacionTecnicoPesos()
				.setText(monedaFormatter.formatPeso(facturacionStrPesoXtecnico));
		ventanaEstadisticas.getTextFacturacionTecnicoDolares()
				.setText(monedaFormatter.formatDolar(facturacionStrDolarXtecnico));

		double porcentaFacturacionPesoPorTecnico = porcentaje(facturacionPesoPorAnioPorTecnico, facturacionPesoPorAnio);
		String porcentaFacturacionPeso = String.format("%.1f %%", porcentaFacturacionPesoPorTecnico);
		ventanaEstadisticas.getTextPorcFacturacionTecnicoPesos().setText(porcentaFacturacionPeso);

		double porcentaFacturacionDolarPortecnico = porcentaje(facturacionDolarPorAnioPorTecnico, facturacionDolarPorAnio);
		String porcentaFacturacionDolar = String.format("%.1f %%", porcentaFacturacionDolarPortecnico);
		ventanaEstadisticas.getTextPorcFacturacionTecnicoDolar().setText(porcentaFacturacionDolar);

	}

	private void mostrarGraficosPorAnio() {
		if (cargandoGraficos) return;
		cargandoGraficos = true;

		inicializarChartPanelsSiNecesario();

		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		String cacheKey = "anio_" + anio;

		Object[] cached = cacheGraficosAnio.get(cacheKey);
		if (cached != null) {
			aplicarChartsDesdeCache(cached);
			cargandoGraficos = false;
			return;
		}

		// Show empty charts while loading
		chartPanelIngresos.setChart(crearChartVacio("INGRESOS"));
		chartPanelDiagnosticos.setChart(crearChartVacio("DIAGNÓSTICOS"));
		chartPanelFacturacion.setChart(crearChartVacio("FACTURACIÓN"));

		new SwingWorker<Object[], Void>() {
			@Override
			protected Object[] doInBackground() throws Exception {
				List<Integer> listaIngresos = modelo.dameIngresosPorAnioPorMes(anio);
				List<Integer> listaDiagnosticos = modelo.dameDiagnosticosPorAnioPorMes(anio);
				List<Double> listaFacturacion = modelo.dameFacturacionPorAnioPorMes(anio);
				return new Object[]{listaIngresos, listaDiagnosticos, listaFacturacion};
			}

			@Override
			protected void done() {
				try {
					Object[] data = get();
					cacheGraficosAnio.put(cacheKey, data);
					crearChartsPorAnio(data);
				} catch (Exception ex) {
					LogDAO.error("Error cargando gráficos por año", ex);
				} finally {
					cargandoGraficos = false;
				}
			}
		}.execute();
	}

	private void inicializarChartPanelsSiNecesario() {
		if (chartPanelIngresos != null) return;

		chartPanelIngresos = new ChartPanel(null);
		chartPanelIngresos.setMouseWheelEnabled(true);
		chartPanelIngresos.setMinimumDrawHeight(400);
		chartPanelIngresos.setMaximumDrawWidth(1280);

		chartPanelDiagnosticos = new ChartPanel(null);
		chartPanelDiagnosticos.setMouseWheelEnabled(true);
		chartPanelDiagnosticos.setMinimumDrawHeight(400);
		chartPanelDiagnosticos.setMaximumDrawWidth(1280);

		chartPanelFacturacion = new ChartPanel(null);
		chartPanelFacturacion.setMouseWheelEnabled(true);
		chartPanelFacturacion.setMinimumDrawHeight(400);
		chartPanelFacturacion.setMaximumDrawWidth(1280);

		ventanaEstadisticas.getPanel_Ingresos().add(chartPanelIngresos, "chart");
		ventanaEstadisticas.getPanel_Diagnosticos().add(chartPanelDiagnosticos, "chart");
		ventanaEstadisticas.getPanel_Facturacion().add(chartPanelFacturacion, "chart");
	}

	private JFreeChart crearChartVacio(String titulo) {
		DefaultCategoryDataset emptyDataset = new DefaultCategoryDataset();
		JFreeChart chart = ChartFactory.createBarChart(titulo, "", "Cantidad", emptyDataset,
				PlotOrientation.VERTICAL, false, true, false);
		
		Color fondoPastel = new Color(220, 228, 240);
		Color plotPastel = new Color(235, 240, 248);
		Color ejeColor = new Color(160, 170, 185);
		chart.setBackgroundPaint(fondoPastel);
		chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
		chart.getTitle().setPaint(new Color(60, 60, 60));
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(plotPastel);
		plot.setRangeGridlinePaint(new Color(200, 208, 220));
		plot.setDomainGridlinePaint(new Color(200, 208, 220));
		plot.setOutlineVisible(true);
		plot.setOutlinePaint(ejeColor);
		plot.setOutlineStroke(new BasicStroke(1.0f));
		plot.setInsets(new RectangleInsets(5, 10, 5, 10));
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
		domainAxis.setTickLabelPaint(new Color(70, 70, 70));
		domainAxis.setAxisLineVisible(true);
		domainAxis.setAxisLinePaint(ejeColor);
		domainAxis.setAxisLineStroke(new BasicStroke(1.0f));
		domainAxis.setTickMarksVisible(true);
		domainAxis.setTickMarkPaint(ejeColor);
		domainAxis.setTickMarkStroke(new BasicStroke(1.0f));
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
		rangeAxis.setTickLabelPaint(new Color(70, 70, 70));
		rangeAxis.setAxisLineVisible(true);
		rangeAxis.setAxisLinePaint(ejeColor);
		rangeAxis.setAxisLineStroke(new BasicStroke(1.0f));
		rangeAxis.setTickMarksVisible(true);
		rangeAxis.setTickMarkPaint(ejeColor);
		rangeAxis.setTickMarkStroke(new BasicStroke(1.0f));
		
		return chart;
	}

	private void crearChartsPorAnio(Object[] data) {
		List<Integer> listaIngresos = (List<Integer>) data[0];
		List<Integer> listaDiagnosticos = (List<Integer>) data[1];
		List<Double> listaFacturacion = (List<Double>) data[2];

		DefaultCategoryDataset datosIngresos = crearDataset(listaIngresos, "Ingresos");
		DefaultCategoryDataset datosDiagnosticos = crearDataset(listaDiagnosticos, "Diagnósticos");
		DefaultCategoryDataset datosFacturacion = crearDataset(listaFacturacion, "Facturación");

		// Colores estilo Aluminium L&F (azules/grises)
		Color azulClaro = new Color(180, 200, 230);
		Color azulOscuro = new Color(70, 110, 160);
		Color verdeClaro = new Color(160, 210, 180);
		Color verdeOscuro = new Color(60, 140, 90);
		Color rojoClaro = new Color(230, 160, 160);
		Color rojoOscuro = new Color(180, 80, 80);
		Color moradoClaro = new Color(200, 170, 230);
		Color moradoOscuro = new Color(130, 90, 170);

		JFreeChart graficoIngresos = crearBarChart("INGRESOS", "", "Cantidad", datosIngresos, azulClaro, azulOscuro);
		JFreeChart graficoDiagnosticos = crearBarChart("DIAGNÓSTICOS", "", "Cantidad", datosDiagnosticos, verdeClaro, verdeOscuro);
		JFreeChart graficoFacturacion = crearBarChart("FACTURACIÓN", null, "Pesos($)", datosFacturacion, rojoClaro, rojoOscuro);

		configurarEjeEntero(graficoIngresos);
		configurarEjeEntero(graficoDiagnosticos);

		aplicarEstiloComun(graficoIngresos, graficoDiagnosticos, graficoFacturacion);

		actualizarChartPanels(graficoIngresos, graficoDiagnosticos, graficoFacturacion);

		if (seleccionDetalleEstadisticas.compareTo("MOSTRAR DETALLE") == 0) {
			ventanaEstadisticas.getPanel_Facturacion().setVisible(true);
		} else {
			ventanaEstadisticas.getPanel_Facturacion().setVisible(false);
		}
		ventanaEstadisticas.repaint();
	}

	private void aplicarChartsDesdeCache(Object[] data) {
		List<Integer> listaIngresos = (List<Integer>) data[0];
		List<Integer> listaDiagnosticos = (List<Integer>) data[1];
		List<Double> listaFacturacion = (List<Double>) data[2];
		crearChartsPorAnio(data);
	}

	private void mostrarGraficosPorTecnico() {
		if (cargandoGraficos) return;
		if (ventanaEstadisticas.getComboTecnico() == null || ventanaEstadisticas.getComboTecnico().getSelectedIndex() == -1) {
			return;
		}
		cargandoGraficos = true;

		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		String tecnico = ventanaEstadisticas.getComboTecnico().getSelectedItem().toString();
		int idTecnico = modelo.idUsuarioporNombre(tecnico);
		String cacheKey = "tecnico_" + anio + "_" + idTecnico;

		Object[] cached = cacheGraficosTecnico.get(cacheKey);
		if (cached != null) {
			aplicarChartsTecnicoDesdeCache(cached);
			cargandoGraficos = false;
			return;
		}

		inicializarChartPanelsSiNecesario();

		chartPanelIngresos.setChart(crearChartVacio("ACEPTACIONES"));
		chartPanelDiagnosticos.setChart(crearChartVacio("DIAGNÓSTICOS"));
		chartPanelFacturacion.setChart(crearChartVacio("FACTURACIÓN"));

		new SwingWorker<Object[], Void>() {
			@Override
			protected Object[] doInBackground() throws Exception {
				List<Integer> listaDiagnosticos = modelo.dameDiagnosticosPorAnioPorTecnico(anio, idTecnico);
				List<Integer> listaAceptaciones = modelo.dameAceptacionesPorAnioPorTecnico(anio, idTecnico);
				List<Double> listaFacturacion = modelo.dameFacturacionPorAnioPorTecnico(anio, idTecnico);
				return new Object[]{listaDiagnosticos, listaAceptaciones, listaFacturacion};
			}

			@Override
			protected void done() {
				try {
					Object[] data = get();
					cacheGraficosTecnico.put(cacheKey, data);
					crearChartsPorTecnico(data);
				} catch (Exception ex) {
					LogDAO.error("Error cargando gráficos por técnico", ex);
				} finally {
					cargandoGraficos = false;
				}
			}
		}.execute();
	}

	private void crearChartsPorTecnico(Object[] data) {
		List<Integer> listaDiagnosticos = (List<Integer>) data[0];
		List<Integer> listaAceptaciones = (List<Integer>) data[1];
		List<Double> listaFacturacion = (List<Double>) data[2];

		DefaultCategoryDataset datosDiagnosticos = crearDataset(listaDiagnosticos, "Diagnósticos");
		DefaultCategoryDataset datosAceptaciones = crearDataset(listaAceptaciones, "Aceptaciones");
		DefaultCategoryDataset datosFacturacion = crearDataset(listaFacturacion, "Facturación");

		// Colores estilo Aluminium L&F
		Color azulClaro = new Color(180, 200, 230);
		Color azulOscuro = new Color(70, 110, 160);
		Color verdeClaro = new Color(160, 210, 180);
		Color verdeOscuro = new Color(60, 140, 90);
		Color rojoClaro = new Color(230, 160, 160);
		Color rojoOscuro = new Color(180, 80, 80);
		Color moradoClaro = new Color(200, 170, 230);
		Color moradoOscuro = new Color(130, 90, 170);

		JFreeChart graficoDiagnosticos = crearBarChart("DIAGNÓSTICOS", "", "Cantidad", datosDiagnosticos, verdeClaro, verdeOscuro);
		JFreeChart graficoAceptaciones = crearBarChart("ACEPTACIONES", "", "Cantidad", datosAceptaciones, moradoClaro, moradoOscuro);
		JFreeChart graficoFacturacion = crearBarChart("FACTURACIÓN", null, "Pesos($)", datosFacturacion, rojoClaro, rojoOscuro);

		configurarEjeEntero(graficoDiagnosticos);
		configurarEjeEntero(graficoAceptaciones);

		aplicarEstiloComun(graficoDiagnosticos, graficoAceptaciones, graficoFacturacion);

		actualizarChartPanels(graficoDiagnosticos, graficoAceptaciones, graficoFacturacion);

		ventanaEstadisticas.repaint();
	}

	private void aplicarChartsTecnicoDesdeCache(Object[] data) {
		crearChartsPorTecnico(data);
	}

	private void mostrarGraficosPorCliente() {
		if (cargandoGraficos) return;
		if (ventanaEstadisticas.getComboCliente() == null || ventanaEstadisticas.getComboCliente().getSelectedIndex() == -1) {
			return;
		}
		cargandoGraficos = true;

		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		String cliente = ventanaEstadisticas.getComboCliente().getSelectedItem().toString();
		int idCliente = modelo.idClienteporNombre(cliente);
		String cacheKey = "cliente_" + anio + "_" + idCliente;

		Object[] cached = cacheGraficosCliente.get(cacheKey);
		if (cached != null) {
			aplicarChartsClienteDesdeCache(cached);
			cargandoGraficos = false;
			return;
		}

		inicializarChartPanelsSiNecesario();

		chartPanelIngresos.setChart(crearChartVacio("INGRESOS"));
		chartPanelDiagnosticos.setChart(crearChartVacio("ACEPTACIONES"));
		chartPanelFacturacion.setChart(crearChartVacio("FACTURACIÓN"));

		new SwingWorker<Object[], Void>() {
			@Override
			protected Object[] doInBackground() throws Exception {
				List<Integer> listaIngresos = modelo.dameIngresosPorAnioPorCliente(anio, idCliente);
				List<Integer> listaAceptaciones = modelo.dameAceptacionesPorAnioPorCliente(anio, idCliente);
				List<Double> listaFacturacion = modelo.dameFacturacionPorAnioPorCliente(anio, idCliente);
				return new Object[]{listaIngresos, listaAceptaciones, listaFacturacion};
			}

			@Override
			protected void done() {
				try {
					Object[] data = get();
					cacheGraficosCliente.put(cacheKey, data);
					crearChartsPorCliente(data);
				} catch (Exception ex) {
					LogDAO.error("Error cargando gráficos por cliente", ex);
				} finally {
					cargandoGraficos = false;
				}
			}
		}.execute();
	}

	private void crearChartsPorCliente(Object[] data) {
		List<Integer> listaIngresos = (List<Integer>) data[0];
		List<Integer> listaAceptaciones = (List<Integer>) data[1];
		List<Double> listaFacturacion = (List<Double>) data[2];

		DefaultCategoryDataset datosIngresos = crearDataset(listaIngresos, "Ingresos");
		DefaultCategoryDataset datosAceptaciones = crearDataset(listaAceptaciones, "Aceptaciones");
		DefaultCategoryDataset datosFacturacion = crearDataset(listaFacturacion, "Facturación");

		// Colores estilo Aluminium L&F
		Color azulClaro = new Color(180, 200, 230);
		Color azulOscuro = new Color(70, 110, 160);
		Color verdeClaro = new Color(160, 210, 180);
		Color verdeOscuro = new Color(60, 140, 90);
		Color rojoClaro = new Color(230, 160, 160);
		Color rojoOscuro = new Color(180, 80, 80);
		Color moradoClaro = new Color(200, 170, 230);
		Color moradoOscuro = new Color(130, 90, 170);

		JFreeChart graficoIngresos = crearBarChart("INGRESOS", "", "Cantidad", datosIngresos, azulClaro, azulOscuro);
		JFreeChart graficoAceptaciones = crearBarChart("ACEPTACIONES", "", "Cantidad", datosAceptaciones, moradoClaro, moradoOscuro);
		JFreeChart graficoFacturacion = crearBarChart("FACTURACIÓN", null, "Pesos($)", datosFacturacion, rojoClaro, rojoOscuro);

		configurarEjeEntero(graficoIngresos);
		configurarEjeEntero(graficoAceptaciones);

		aplicarEstiloComun(graficoIngresos, graficoAceptaciones, graficoFacturacion);

		actualizarChartPanels(graficoIngresos, graficoAceptaciones, graficoFacturacion);

		ventanaEstadisticas.repaint();
	}

	private void aplicarChartsClienteDesdeCache(Object[] data) {
		crearChartsPorCliente(data);
	}

	private void mostrarGraficoFacturacionXcliente() {

		DefaultPieDataset dataset = createDataset();

		// Crear el gráfico de torta
		JFreeChart chart = ChartFactory.createPieChart("", dataset, false, true, false);
		
		Color fondoPastel = new Color(220, 228, 240);
		Color plotPastel = new Color(235, 240, 248);
		Color ejeColor = new Color(160, 170, 185);
		chart.setBackgroundPaint(fondoPastel);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(plotPastel);
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setOutlineVisible(true);
		plot.setOutlinePaint(ejeColor);
		plot.setOutlineStroke(new BasicStroke(1.0f));
		plot.setShadowPaint(null);
		plot.setSectionOutlinesVisible(false);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));
		plot.setLabelFont(labelFontPie);
		plot.setLabelBackgroundPaint(new Color(255, 255, 255, 200));
		plot.setLabelOutlinePaint(null);

		// Colores estilo Aluminium L&F para torta (tonos pastel más oscuros)
		Color[] colores = {
			new Color(140, 170, 200),  // azulClaro
			new Color(130, 180, 150),  // verdeClaro
			new Color(200, 130, 130),  // rojoClaro
			new Color(170, 140, 200),  // moradoClaro
			new Color(190, 170, 120),  // beige
			new Color(120, 170, 180),  // celeste
			new Color(180, 130, 130),  // coral suave
			new Color(140, 180, 150),  // verde menta
			new Color(180, 150, 180),  // lila
			new Color(146, 166, 192),  // Light Steel Blue
		};
		for (int i = 0; i < Math.min(dataset.getItemCount(), colores.length); i++) {
			plot.setSectionPaint(dataset.getKey(i), colores[i]);
		}

		ChartPanel chartGraficoFacturacion = new ChartPanel(chart);
		chartGraficoFacturacion.setMouseWheelEnabled(true);
		chartGraficoFacturacion.setBackground(Color.WHITE);

		ventanaFacturacionXcliente.getPanelGraficoCliente().removeAll();
		ventanaFacturacionXcliente.getPanelGraficoCliente().add(chartGraficoFacturacion, BorderLayout.CENTER);
		ventanaFacturacionXcliente.getPanelGraficoCliente().revalidate();
		ventanaFacturacionXcliente.getPanelGraficoCliente().repaint();

	}

	private DefaultPieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		// Obtener los datos de la tabla
		int rowCount = ventanaFacturacionXcliente.getTblFacturacionClientes().getRowCount();
		Map<String, Double> porcentajePorCliente = new HashMap<>();

		for (int i = 0; i < rowCount; i++) {
			String cliente = (String) ventanaFacturacionXcliente.getTblFacturacionClientes().getValueAt(i, 0);
			double porcentaje = monedaFormatter
					.parseAmount(ventanaFacturacionXcliente.getTblFacturacionClientes().getValueAt(i, 2).toString());

			// Sumar los porcentajes para el mismo cliente
			porcentajePorCliente.put(cliente, porcentajePorCliente.getOrDefault(cliente, 0.0) + porcentaje);
		}

		// Agregar los datos al dataset del gráfico de torta
		for (Map.Entry<String, Double> entry : porcentajePorCliente.entrySet()) {
			dataset.setValue(entry.getKey(), entry.getValue());
		}

		return dataset;
	}

	private void calcularComisiones() {

		if (monedaFormatter.tieneFormato(ventanaResumenMensualTecnico.getTextFacturacionPesos().getText())) {

			facturacion = monedaFormatter
					.parseAmountGuardar(ventanaResumenMensualTecnico.getTextFacturacionPesos().getText());

		} else {

			facturacion = monedaFormatter.parseAmount(ventanaResumenMensualTecnico.getTextFacturacionPesos().getText());

		}

		double porcentajeNumero = porcentaje / 100;

		double totalComisiones = facturacion * porcentajeNumero;
		ventanaResumenMensualTecnico.getTextTotalComisionesPesos()
				.setText(monedaFormatter.formatPeso(String.valueOf(totalComisiones)));

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (this.ventanaListadoReparaciones == null) {
			return;
		}

//		// Manejo de clic en la tabla
//		if (arg0.getSource() == this.ventanaListadoReparaciones.getTblReparaciones()) {
//			handleTableClick(arg0);
//			return;
//		}

		// Mapeo de checkboxes a columnas
		Map<JCheckBox, ColumnConfig> checkboxConfigMap = createCheckboxConfigMap();

		// Buscar el checkbox correspondiente y aplicar configuración
		for (Map.Entry<JCheckBox, ColumnConfig> entry : checkboxConfigMap.entrySet()) {
			if (arg0.getSource() == entry.getKey()) {
				configureColumn(entry.getKey(), entry.getValue());
				break;
			}
		}
	}

	// Clase auxiliar para configuración de columnas
	private static class ColumnConfig {
		final int columnIndex;
		final int defaultWidth;
		final int minWidth;
		final int maxWidth;

		ColumnConfig(int columnIndex, int defaultWidth, int minWidth, int maxWidth) {
			this.columnIndex = columnIndex;
			this.defaultWidth = defaultWidth;
			this.minWidth = minWidth;
			this.maxWidth = maxWidth;
		}
	}

	// Mapa de configuración de columnas
	private Map<JCheckBox, ColumnConfig> createCheckboxConfigMap() {
		Map<JCheckBox, ColumnConfig> map = new HashMap<>();

		// Configuración de cada checkbox y su columna correspondiente

		map.put(ventanaListadoReparaciones.getCheckBox(), new ColumnConfig(0, 60, 60, 100));

		map.put(ventanaListadoReparaciones.getChckbxELS(), new ColumnConfig(0, 60, 60, 100));
		map.put(ventanaListadoReparaciones.getChckbxEntrada(), new ColumnConfig(1, 80, 80, 100));
		map.put(ventanaListadoReparaciones.getChckbxCliente(), new ColumnConfig(2, 150, 150, 200));
		map.put(ventanaListadoReparaciones.getChckbxSucursal(), new ColumnConfig(3, 150, 150, 200));
		map.put(ventanaListadoReparaciones.getChckbxEquipo(), new ColumnConfig(4, 150, 150, 200));
		map.put(ventanaListadoReparaciones.getChckbxMarca(), new ColumnConfig(5, 200, 200, 250));
		map.put(ventanaListadoReparaciones.getChckbxModelo(), new ColumnConfig(6, 150, 150, 200));
		map.put(ventanaListadoReparaciones.getChckbxSerie(), new ColumnConfig(7, 100, 100, 150));
		map.put(ventanaListadoReparaciones.getChckbxAviso(), new ColumnConfig(8, 100, 100, 150));
		map.put(ventanaListadoReparaciones.getChckbxRevisión(), new ColumnConfig(9, 80, 80, 110));
		map.put(ventanaListadoReparaciones.getChckbxSalida(), new ColumnConfig(10, 80, 80, 110));
		map.put(ventanaListadoReparaciones.getChckbxClienteCliente(), new ColumnConfig(11, 110, 110, 150));
		map.put(ventanaListadoReparaciones.getChckbxEstadoTec(), new ColumnConfig(12, 120, 120, 150));
		map.put(ventanaListadoReparaciones.getChckbxEstadoCom(), new ColumnConfig(13, 150, 150, 200));
		map.put(ventanaListadoReparaciones.getChckbxEstadoFis(), new ColumnConfig(14, 100, 100, 150));
		map.put(ventanaListadoReparaciones.getChckbxTecnico(), new ColumnConfig(15, 100, 100, 150));
		map.put(ventanaListadoReparaciones.getChckbxUbicacionRemito(), new ColumnConfig(16, 100, 100, 150));
		map.put(ventanaListadoReparaciones.getChckbxNumeroRemito(), new ColumnConfig(17, 100, 100, 150));
		map.put(ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna(), new ColumnConfig(18, 80, 80, 80));
		map.put(ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna(), new ColumnConfig(19, 80, 80, 80));
		map.put(ventanaListadoReparaciones.getChckbxPrecioPeso(), new ColumnConfig(20, 100, 100, 100));
		map.put(ventanaListadoReparaciones.getChckbxPrecioDolar(), new ColumnConfig(21, 100, 100, 100));
		map.put(ventanaListadoReparaciones.getChckbxPago(), new ColumnConfig(22, 100, 100, 100));
		map.put(ventanaListadoReparaciones.getChckbxIngreso(), new ColumnConfig(23, 100, 100, 100));

		return map;
	}

	// Método para configurar una columna basada en el estado del checkbox
	private void configureColumn(JCheckBox checkbox, ColumnConfig config) {
		TableColumn column = ventanaListadoReparaciones.getTblReparaciones()
				.getColumn(ventanaListadoReparaciones.getTblReparaciones().getColumnName(config.columnIndex));

		if (checkbox.isSelected()) {
			column.setWidth(0);
			column.setMinWidth(0);
			column.setMaxWidth(0);
		} else {
			column.setWidth(config.defaultWidth);
			column.setMinWidth(config.minWidth);
			column.setMaxWidth(config.maxWidth);
		}
	}

	// Manejo del clic en la tabla
	private void handleTableClick(MouseEvent arg0) {
		int row = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedRow();
		int col = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedColumn();

		if (col == 0) {
			int els = Integer
					.parseInt(this.ventanaListadoReparaciones.getTblReparaciones().getValueAt(row, col).toString());

			NumeroELSSeleccionado = els;

			try {
				ventanaVisualizarEquipos = controladorReparacion.tomarDatosDeTablasListado(NumeroELSSeleccionado,
						this.ventanaVisualizarEquipos);
				ventanaVisualizarEquipos.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						actualizarTabla();
					}
				});

			} catch (ParseException e) {
				e.printStackTrace();
			}
			controladorReparacion.agregarListenersVentanaVisualizarEquiposListado(ventanaVisualizarEquipos);
		}
	}

	public void actualizarTabla() {
		cargarTablaListadoReparaciones();
	}

	// Los demás métodos del MouseListener pueden dejarse como están o implementarse
	// según necesidad
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		// Manejo de clic en la tabla
		if (arg0.getSource() == this.ventanaListadoReparaciones.getTblReparaciones()) {
			handleTableClick(arg0);
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (ventanaListadoReparaciones != null) {
			int column = ventanaListadoReparaciones.getTblReparaciones().columnAtPoint(arg0.getPoint());
			Cursor cursor = column == 0 ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor();
			ventanaListadoReparaciones.getTblReparaciones().setCursor(cursor);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (this.ventanaListadoReparaciones != null && e.getSource() instanceof JCheckBox) {
			Map<JCheckBox, ColumnConfig> checkboxConfigMap = createCheckboxConfigMap();
			JCheckBox sourceCheckbox = (JCheckBox) e.getSource();

			if (checkboxConfigMap.containsKey(sourceCheckbox)) {
				configureColumn(sourceCheckbox, checkboxConfigMap.get(sourceCheckbox));

			}
		}
	}

	private DefaultCategoryDataset crearDataset(List<? extends Number> valores, String seriesName) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < Math.min(valores.size(), 12); i++) {
			dataset.setValue(valores.get(i), seriesName, MESES[i]);
		}
		return dataset;
	}

private JFreeChart crearBarChart(String title, String categoryLabel, String valueLabel,
		DefaultCategoryDataset dataset, Color startColor, Color endColor) {
	JFreeChart chart = ChartFactory.createBarChart(title, categoryLabel, valueLabel, dataset,
			PlotOrientation.VERTICAL, false, true, false);
	
	Color fondoPastel = new Color(220, 228, 240);
	Color plotPastel = new Color(235, 240, 248);
	Color ejeColor = new Color(160, 170, 185);
	chart.setBackgroundPaint(fondoPastel);
	chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
	chart.getTitle().setPaint(new Color(60, 60, 60));
	
	CategoryPlot plot = (CategoryPlot) chart.getPlot();
	plot.setBackgroundPaint(plotPastel);
	plot.setRangeGridlinePaint(new Color(200, 208, 220));
	plot.setDomainGridlinePaint(new Color(200, 208, 220));
	plot.setOutlineVisible(true);
	plot.setOutlinePaint(ejeColor);
	plot.setOutlineStroke(new BasicStroke(1.0f));
	plot.setInsets(new RectangleInsets(5, 10, 5, 10));
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
		domainAxis.setTickLabelPaint(new Color(70, 70, 70));
		domainAxis.setAxisLineVisible(true);
		domainAxis.setAxisLinePaint(ejeColor);
		domainAxis.setAxisLineStroke(new BasicStroke(1.0f));
		domainAxis.setTickMarksVisible(true);
		domainAxis.setTickMarkPaint(ejeColor);
		domainAxis.setTickMarkStroke(new BasicStroke(1.0f));
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
		rangeAxis.setTickLabelPaint(new Color(70, 70, 70));
		rangeAxis.setAxisLineVisible(true);
		rangeAxis.setAxisLinePaint(ejeColor);
		rangeAxis.setAxisLineStroke(new BasicStroke(1.0f));
		rangeAxis.setTickMarksVisible(true);
		rangeAxis.setTickMarkPaint(ejeColor);
		rangeAxis.setTickMarkStroke(new BasicStroke(1.0f));
		
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setShadowVisible(false);
		renderer.setMaximumBarWidth(0.07);
		renderer.setItemMargin(0.03);
		GradientPaint gp = new GradientPaint(0.0f, 0.0f, startColor, 0.0f, 0.0f, endColor);
		renderer.setSeriesPaint(0, gp);
		
		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setItemLabelFont(new Font("Segoe UI", Font.BOLD, 12));
		renderer.setItemLabelPaint(new Color(60, 60, 60));
		renderer.setItemLabelsVisible(true);
		renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER));
		
		return chart;
	}

	private void configurarEjeEntero(JFreeChart chart) {
		NumberAxis yAxis = (NumberAxis) chart.getCategoryPlot().getRangeAxis();
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	}

	private void aplicarEstiloComun(JFreeChart... charts) {
		for (JFreeChart chart : charts) {
			chart.getTitle().setFont(titleFont);
			chart.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setItemLabelGenerator(generator);
			renderer.setItemLabelFont(labelFont);
			renderer.setItemLabelPaint(labelColor);
			renderer.setItemLabelsVisible(true);
		}
		if (charts.length >= 3) {
			((CategoryPlot) charts[2].getPlot()).getRenderer().setPositiveItemLabelPosition(positionNumeroGrande);
			((CategoryPlot) charts[0].getPlot()).getRenderer().setPositiveItemLabelPosition(positionNumeroChico);
			((CategoryPlot) charts[1].getPlot()).getRenderer().setPositiveItemLabelPosition(positionNumeroChico);
		}
	}

	private void actualizarChartPanels(JFreeChart chart1, JFreeChart chart2, JFreeChart chart3) {
		if (chartPanelIngresos == null) {
			chartPanelIngresos = new ChartPanel(chart1);
			chartPanelIngresos.setMouseWheelEnabled(true);
			chartPanelIngresos.setMinimumDrawHeight(400);
			chartPanelIngresos.setMaximumDrawWidth(1280);
			ventanaEstadisticas.getPanel_Ingresos().add(chartPanelIngresos);
		} else {
			chartPanelIngresos.setChart(chart1);
		}

		if (chartPanelDiagnosticos == null) {
			chartPanelDiagnosticos = new ChartPanel(chart2);
			chartPanelDiagnosticos.setMouseWheelEnabled(true);
			chartPanelDiagnosticos.setMinimumDrawHeight(400);
			chartPanelDiagnosticos.setMaximumDrawWidth(1280);
			ventanaEstadisticas.getPanel_Diagnosticos().add(chartPanelDiagnosticos);
		} else {
			chartPanelDiagnosticos.setChart(chart2);
		}

		if (chartPanelFacturacion == null) {
			chartPanelFacturacion = new ChartPanel(chart3);
			chartPanelFacturacion.setMouseWheelEnabled(true);
			chartPanelFacturacion.setMinimumDrawHeight(400);
			chartPanelFacturacion.setMaximumDrawWidth(1280);
			ventanaEstadisticas.getPanel_Facturacion().add(chartPanelFacturacion);
		} else {
			chartPanelFacturacion.setChart(chart3);
		}

		ventanaEstadisticas.getPanel_Ingresos().revalidate();
		ventanaEstadisticas.getPanel_Diagnosticos().revalidate();
		ventanaEstadisticas.getPanel_Facturacion().revalidate();
	}

}
