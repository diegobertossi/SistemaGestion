package presentacion.controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import VistaPropias.AutoCompletarComboBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
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

import dto.FacturacionXclienteDTO;
import dto.RegistroPresupuestoDTO;
import dto.RegistroResumenTecnicoDTO;
import dto.ReparacionDTO;
import dto.RepuestosDTO;
import modelo.Agenda;
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

	// private int clickMax = 1;
	// private int clickMin = 1;

	public int NumeroELSSeleccionado;

	private List<ReparacionDTO> Reparaciones_en_tabla;

	@SuppressWarnings("unused")
	private ControladorUsuLogin controladorUsuLogin;

	private MonedaFormatter monedaFormatter;

	private int filtro;
	private String seleccionDetalleEstadisticas = "OCULTAR DETALLE";

	private int anio;
	private int mes;
	private int cantidadIngresosPorAnio;
	private int cantidadDiagnosticosPorAnio;
	private double facturacionPesoPorAnio;
	private double facturacionDolarPorAnio;

	private double porcentaje;
	private double facturacion;
	private List<FacturacionXclienteDTO> itemFacturacion_en_tabla;

	private CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
	private Font titleFont = new Font("Arial", Font.BOLD, 20); // Por ejemplo, Arial, negrita, tamaño 16
	private Font labelFont = new Font("Arial", Font.BOLD, 18); // Por ejemplo, Arial, tamaño 12
	private Font labelFontPie = new Font("Arial", Font.PLAIN, 12);
	private Color labelColor2 = Color.BLACK; // Cambiar color de la letra
	private Color labelColor = Color.darkGray; // Cambiar color de la letra

	private ItemLabelPosition positionNumeroGrande = new ItemLabelPosition(ItemLabelAnchor.CENTER, // Posición dentro de
																									// la barra
			TextAnchor.BASELINE_CENTER, // Alineación vertical
			TextAnchor.BASELINE_CENTER, // Alineación de texto
			//0
			-Math.PI / 2.0 // Rotación del texto (90 grados para vertical)
	);
	private ItemLabelPosition positionNumeroChico = new ItemLabelPosition(ItemLabelAnchor.CENTER, // Posición dentro de
																									// la barra
			TextAnchor.BASELINE_CENTER, // Alineación vertical
			TextAnchor.BASELINE_CENTER, // Alineación de texto
			0 // Rotación del texto (90 grados para vertical)
	);

	public ControladorListados(VentanaListadoReparaciones ventanaListadoReparaciones, Agenda modelo,
			ControladorUsuLogin controladorUsuLogin, ControladorReparacion controladorReparacion) {

		this.ventanaListadoReparaciones = ventanaListadoReparaciones;
		this.controladorUsuLogin = controladorUsuLogin;
		this.controladorReparacion = controladorReparacion;
		this.modelo = modelo;
		this.itemFacturacion_en_tabla = null;

		agregarListenerVentanaListados();

		cargarTablaListadoReparaciones();

	}

	public void inicializar() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if (this.ventanaListadoReparaciones != null
				&& arg0.getSource() == this.ventanaListadoReparaciones.getBtnEstadisticas()) {

			ventanaEstadisticas = new VentanaEstadisticas(this);
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

				ventanaCodigoSeguridad = new VentanaCodigoSeguridad(this);
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

				ReporteResumenTecnico reporteResumen = new ReporteResumenTecnico(resumenDatos, listaResumenTecnico);
				reporteResumen.mostrar();
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

		if (codigo.compareTo("0000") == 0) {

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

		/* con ese código no respeta las restricciones de los permisos */

//		this.ventanaListadoReparaciones.getModelReparaciones().setRowCount(0); // Para
//		// vaciar
//		// tabla
//		this.ventanaListadoReparaciones.getModelReparaciones().setColumnCount(0);
//		this.ventanaListadoReparaciones.getModelReparaciones()
//				.setColumnIdentifiers(this.ventanaListadoReparaciones.getNombreColumnas());

		this.Reparaciones_en_tabla = (List<ReparacionDTO>) modelo.obtenerReparacion();

		// for (int i = 0; i < this.Reparaciones_en_tabla.size(); i++) {
		for (int i = this.Reparaciones_en_tabla.size() - 1; i >= 0; i--) {

			Object[] fila = { this.Reparaciones_en_tabla.get(i).getELS(),
					this.Reparaciones_en_tabla.get(i).getFecha_Entrada(),
					this.Reparaciones_en_tabla.get(i).getCliente(), this.Reparaciones_en_tabla.get(i).getSucursal(),
					this.Reparaciones_en_tabla.get(i).getNombreEquipo(), this.Reparaciones_en_tabla.get(i).getMarca(),
					this.Reparaciones_en_tabla.get(i).getModelo(), this.Reparaciones_en_tabla.get(i).getNumeroDeSerie(),
					this.Reparaciones_en_tabla.get(i).getAviso(),
					this.Reparaciones_en_tabla.get(i).getFechadereparacion(),
					this.Reparaciones_en_tabla.get(i).getClienteCliente(),
					this.Reparaciones_en_tabla.get(i).getEstadoTecnico(),
					this.Reparaciones_en_tabla.get(i).getEstadoComercial(),
					this.Reparaciones_en_tabla.get(i).getEstadoFisico(),
					this.Reparaciones_en_tabla.get(i).getNombreUsuario(), this.Reparaciones_en_tabla.get(i).getCodigo(),
					this.Reparaciones_en_tabla.get(i).getNumeroRemitoSalida(),
					this.Reparaciones_en_tabla.get(i).getPresupuestoGenerado(),
					this.Reparaciones_en_tabla.get(i).getPresupuestoEnviado(),
					this.Reparaciones_en_tabla.get(i).getPrecioPeso(),
					this.Reparaciones_en_tabla.get(i).getPrecioDolar(), this.Reparaciones_en_tabla.get(i).getPago(),
					this.Reparaciones_en_tabla.get(i).getLugarDeIngreso() };

			this.ventanaListadoReparaciones.getModelReparaciones().addRow(fila);

		}

		ventanaListadoReparaciones.setCellRender(this.ventanaListadoReparaciones.getTblListado());

		agregarAutofiltrosATabla(this.ventanaListadoReparaciones.getTblListado());

		this.ventanaListadoReparaciones.setVisible(true);

	}

	public void agregarAutofiltrosATabla(JTable tabla) {

		Font fuenteFiltros = new Font("Cambria", Font.PLAIN, 11);
		Color fondoFiltros = new Color(255, 255, 150);
		Color fondoBusqueda = new Color(148, 255, 129); // Verde pastel claro

		// Obtener el modelo de la tabla
		DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		int columnCount = tabla.getColumnCount();
		JComboBox<String>[] filterCombos = new JComboBox[columnCount];

		// Crear un panel para los filtros
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(null); // Usar null layout para posicionar manualmente los filtros

		// Crear los JComboBox y agregarlos al panel de filtros
		int xPosition = 0; // Posición horizontal inicial
		for (int i = 0; i < columnCount; i++) {
			// Crear un combobox editable para cada columna
			filterCombos[i] = new JComboBox<>();
			filterCombos[i].setFont(fuenteFiltros);
			filterCombos[i].setEditable(true); // Permitir escribir en el combobox
			filterCombos[i].addItem("Todos"); // Opción por defecto

			// Cambiar el color de fondo del editor
			JTextField editor = (JTextField) filterCombos[i].getEditor().getEditorComponent();
			editor.setBackground(fondoFiltros);

			// Establecer un renderer personalizado
			filterCombos[i].setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					c.setBackground(isSelected ? fondoFiltros.darker() : fondoFiltros);
					c.setForeground(Color.BLACK);
					return c;
				}
			});

			// Capturar el índice de la columna en una variable final
			final int columnIndex = i;

			// Agregar ActionListener para filtrar la tabla
			filterCombos[i].addActionListener(e -> {
				String filterValue = (String) filterCombos[columnIndex].getSelectedItem();

				// Cambiar el color de fondo según el valor seleccionado
				if (filterValue != null && !(filterValue.equals("Todos") || filterValue.equals("") )) {
					editor.setBackground(fondoBusqueda);
				} else {
					editor.setBackground(fondoFiltros);
				}

				filtrarTabla(tabla, filterCombos);
			});

			// Llenar el combobox con valores únicos de la columna
			actualizarFiltroColumna(tabla, filterCombos[i], i);

			// Obtener el ancho de la columna correspondiente
			TableColumn column = tabla.getColumnModel().getColumn(columnIndex);
			int columnWidth = column.getWidth();

			// Ajustar la posición y el ancho del combobox
			filterCombos[i].setBounds(xPosition, 0, columnWidth, 25);
			filterPanel.add(filterCombos[i]);

			// Actualizar la posición horizontal para el siguiente combobox
			xPosition += columnWidth;
		}

		// Establecer la altura del panel de filtros según el tamaño de los combobox
		filterPanel.setPreferredSize(new Dimension(tabla.getWidth(), 25));

		// Crear un contenedor principal para los filtros y encabezados
		JPanel headerContainer = new JPanel(new BorderLayout());
		headerContainer.add(filterPanel, BorderLayout.NORTH);
		headerContainer.add(tabla.getTableHeader(), BorderLayout.SOUTH);

		// Reemplazar el encabezado del JScrollPane con el contenedor de filtros y
		// encabezados
		JScrollPane scrollPane = (JScrollPane) tabla.getParent().getParent();
		scrollPane.setColumnHeaderView(headerContainer);

		// Escuchar cambios en el ancho de las columnas para ajustar el tamaño y
		// posición de los filtros
		tabla.getColumnModel().addColumnModelListener(new javax.swing.event.TableColumnModelListener() {
			@Override
			public void columnAdded(javax.swing.event.TableColumnModelEvent e) {
			}

			@Override
			public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {
			}

			@Override
			public void columnMoved(javax.swing.event.TableColumnModelEvent e) {
			}

			@Override
			public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
				int xPosition = 0;
				for (int i = 0; i < columnCount; i++) {
					TableColumn column = tabla.getColumnModel().getColumn(i);
					int columnWidth = column.getWidth();
					filterCombos[i].setBounds(xPosition, 0, columnWidth, 25);
					filterCombos[i].revalidate();
					filterCombos[i].repaint();
					xPosition += columnWidth;
				}
			}

			@Override
			public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
			}
		});
	}

	private void actualizarFiltroColumna(JTable tabla, JComboBox<String> comboBox, int columnIndex) {
		DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		comboBox.removeAllItems();
		comboBox.addItem("Todos");

		Set<String> uniqueValues = new TreeSet<>((a, b) -> {

			// Si no son fechas, comparar como números
			if (esNumero(a) && esNumero(b)) {
				return Double.compare(Double.parseDouble(a), Double.parseDouble(b)); // Comparar como números
			}
			return a.compareTo(b); // Comparar cadenas alfabéticamente
		});

		// Recorrer las filas y agregar los valores únicos al TreeSet
		for (int row = 0; row < model.getRowCount(); row++) {
			Object value = model.getValueAt(row, columnIndex);
			if (value != null) {
				String valueString = value.toString();

				uniqueValues.add(valueString);

			}
		}

		// Agregar los valores ordenados al JComboBox
		for (String value : uniqueValues) {
			if (esFecha(value)) {
				value = formatearFecha(value);

			}

			comboBox.addItem(value);
		}
	}

	private void filtrarTabla(JTable tabla, JComboBox<String>[] filterCombos) {
		DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		tabla.setRowSorter(sorter);

		// Lista de filtros para todas las columnas
		List<RowFilter<Object, Object>> rowFilters = new ArrayList<>();

		// Recorrer cada JComboBox y agregar el filtro correspondiente
		for (int columnIndex = 0; columnIndex < filterCombos.length; columnIndex++) {
			// Verificar si el JComboBox no es null
			if (filterCombos[columnIndex] != null) {
				String filterValue = (String) filterCombos[columnIndex].getSelectedItem();

				if (filterValue != null && !filterValue.equals("Todos")) {
					// Si el filtro seleccionado es una fecha en formato DD/MM/AAAA, convertirla a
					// AAAAMMDD
					if (filterValue.contains("/")) {
						String[] parts = filterValue.split("/");
						filterValue = parts[2] + parts[1] + parts[0]; // Convertir a AAAAMMDD
					}

					// Convertir el valor de filtro en una expresión regular con comodín *
					String regex = filterValue.replace("*", ".*");
					// Hacer el cast a RowFilter<Object, Object> para que sea compatible
					RowFilter<Object, Object> rowFilter = RowFilter.regexFilter("(?i)" + regex, columnIndex);
					rowFilters.add(rowFilter); // Agregar el filtro de esta columna
				}
			}
		}

		// Aplicar todos los filtros a la vez
		if (rowFilters.isEmpty()) {
			sorter.setRowFilter(null); // Si no hay filtros activos, mostrar todas las filas
		} else {
			// Combinamos todos los filtros usando una lógica AND
			RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(rowFilters);
			sorter.setRowFilter(combinedFilter);
		}
	}

	private boolean esNumero(String value) {
		try {
			// Intentar parsear el valor a número (entero o decimal)
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Verifica si un valor es una fecha en formato AAAAMMDD
	private boolean esFecha(String value) {
		// Verifica si el valor tiene el formato AAAAMMDD (8 dígitos numéricos)
		return value.length() == 8 && value.matches("\\d{8}");
	}

	// Formatea una fecha en formato AAAAMMDD a DD/MM/AAAA
	private String formatearFecha(String fecha) {
		// Convertir la fecha de formato AAAAMMDD a DD/MM/AAAA
		String dia = fecha.substring(6, 8);
		String mes = fecha.substring(4, 6);
		String año = fecha.substring(0, 4);
		return dia + "/" + mes + "/" + año;
	}

	public void agregarListenerVentanaListados() {

		this.ventanaListadoReparaciones.getBtnEstadisticas().addActionListener(this);

		this.ventanaListadoReparaciones.getTblReparaciones().addMouseListener(this);
		this.ventanaListadoReparaciones.getTblReparaciones().addMouseMotionListener(this);

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
			porcentaje = this.itemFacturacion_en_tabla.get(i).getFacturacion() * 100 / facturacionPesoPorAnio;
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
			porcentaje = item.getFacturacion() * 100 / facturacionPesoPorAnio;

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
				int opcion = JOptionPane.showConfirmDialog(ventanaListadoReparaciones,
						"¿Desea salir de la ventana 'LISTADO'?", "Aviso", JOptionPane.YES_NO_OPTION);

				if (opcion == JOptionPane.YES_OPTION) {

					ventanaListadoReparaciones.dispose();
					ventanaListadoReparaciones = null;

					if (ventanaEquipos != null) {

						ventanaEquipos.dispose();
						ventanaEquipos = null;

					}

				}

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

				if (ventanaEstadisticas.getComboFiltro().getSelectedItem() != null) {

					filtro = ventanaEstadisticas.getComboFiltro().getSelectedIndex();
					// System.out.println(ventanaEstadisticas.getComboFiltro().getSelectedIndex());

					switch (filtro) {

					case 0:

						ventanaEstadisticas.getLblAnio().setVisible(false);
						ventanaEstadisticas.getComboAnio().setVisible(false);

						ventanaEstadisticas.getLblTecnico().setVisible(false);
						ventanaEstadisticas.getComboTecnico().setVisible(false);

						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);

						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();

						break;

					case 1:

						ventanaEstadisticas.getLblAnio().setVisible(true);
						ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
						ventanaEstadisticas.getComboAnio().setVisible(true);

						ventanaEstadisticas.getLblTecnico().setVisible(false);
						ventanaEstadisticas.getComboTecnico().setVisible(false);
						ventanaEstadisticas.getComboCliente().setVisible(false);
						ventanaEstadisticas.getPanel_Datos().setVisible(false);

						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();

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

						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();

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

						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();
						ventanaEstadisticas.repaint();

						break;

					default:
						break;
					}

				}

			}
		});

	}

	@SuppressWarnings("unchecked")
	private void llenarcomboAnio() {

		for (int i = 2024; i < 2030; i++) {

			ventanaEstadisticas.getComboAnio().addItem(i);

		}

		ventanaEstadisticas.getComboAnio().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaEstadisticas.getComboAnio().getSelectedItem() != null) {

					ventanaEstadisticas.getLblAnioDatos()
							.setText(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
					ventanaEstadisticas.getPanel_Datos().setVisible(true);

					llenarDatosAnuales();

					switch (filtro) {
					case 0:
						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();

						ventanaEstadisticas.getPanel_datosPorAnio().setVisible(false);
						ventanaEstadisticas.getPanel_datosPorCliente().setVisible(false);
						ventanaEstadisticas.getPanel_datosPorTecnico().setVisible(false);

						ventanaEstadisticas.getPanel_facturacionPorAnio().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorCliente().setVisible(false);
						ventanaEstadisticas.getPanel_facturacionPorTecnico().setVisible(false);

						break;
					case 1:

						llenarDatosPorAnio();

						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();

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

						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();

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

						ventanaEstadisticas.getPanel_Ingresos().removeAll();
						ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
						ventanaEstadisticas.getPanel_Facturacion().removeAll();

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

				if (ventanaEstadisticas.getComboTecnico().getSelectedItem() != null) {

					ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
					ventanaEstadisticas.getPanel_Ingresos().removeAll();
					ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
					ventanaEstadisticas.getPanel_Facturacion().removeAll();
					ventanaEstadisticas.repaint();

				}

			}

		});

	}

	private void llenarcomboClientes() {

		modelo.ListarCliente(ventanaEstadisticas.getComboCliente());

		ventanaEstadisticas.getComboCliente().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (ventanaEstadisticas.getComboCliente().getSelectedItem() != null) {

					ventanaEstadisticas.getComboAnio().setSelectedIndex(-1);
					ventanaEstadisticas.getPanel_Ingresos().removeAll();
					ventanaEstadisticas.getPanel_Diagnosticos().removeAll();
					ventanaEstadisticas.getPanel_Facturacion().removeAll();
					ventanaEstadisticas.repaint();

				}

			}

		});

	}

	private void llenarDatosAnuales() {

		anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		cantidadIngresosPorAnio = modelo.dameIngresosPorAnio(anio);
		cantidadDiagnosticosPorAnio = modelo.dameDiagnosticosPorAnio(anio);
		facturacionPesoPorAnio = modelo.dameFacturacionPesoPorAnio(anio);
		facturacionDolarPorAnio = modelo.dameFacturacionDolarPorAnio(anio);

		ventanaEstadisticas.getTextIngresosTotales().setText(Integer.toString(cantidadIngresosPorAnio));
		ventanaEstadisticas.getTextDiagnosticosTotales().setText(Integer.toString(cantidadDiagnosticosPorAnio));

		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String facturacionStrPeso = decimalFormat.format(facturacionPesoPorAnio);
		String facturacionStrDolar = decimalFormat.format(facturacionDolarPorAnio);

		ventanaEstadisticas.getTextFacTotalPesos().setText(monedaFormatter.formatPeso(facturacionStrPeso));

		ventanaEstadisticas.getTextFacTotalDolares().setText(monedaFormatter.formatDolar(facturacionStrDolar));

	}

	private void llenarDatosPorAnio() {

		int cantidadReparadosPorAnio = modelo.dameReparadosPorAnio(anio);
		int cantidadSinFallaPorAnio = modelo.dameSinFallaPorAnio(anio);
		int cantidadRepEnGtiaPorAnio = modelo.dameRepEnGtiaPorAnio(anio);
		int cantidadEnRepPorAnio = modelo.dameEnRepPorAnio(anio);
		int cantidadVentasPorAnio = modelo.dameVentasPorAnio(anio);
		int cantidadSinRepAnio = modelo.dameSinRepAnio(anio);
		int cantidadReparadosAceptradosPorAnio = modelo.dameRepAcepPorAnio(anio);
		int cantidadReparadosNoAceptradosPorAnio = modelo.dameRepNoAcepPorAnio(anio);
		int cantidadReparadosAlaEsperaPorAnio = modelo.dameRepEsperaPorAnio(anio);

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

		double porcentajeReparadosPorAnio = ((double) cantidadReparadosPorAnio / cantidadDiagnosticosPorAnio) * 100;
		String porcentajeReparados = String.format("%.1f %%", porcentajeReparadosPorAnio);
		ventanaEstadisticas.getTextPorcentajeReparados().setText(porcentajeReparados);

		double porcentajeRepEnGtiaPorAnio = ((double) cantidadRepEnGtiaPorAnio / cantidadDiagnosticosPorAnio) * 100;
		String porcentajeEnGtia = String.format("%.1f %%", porcentajeRepEnGtiaPorAnio);
		ventanaEstadisticas.getTextPorcentajeRepEnGtia().setText(porcentajeEnGtia);

		double porcentajeSinFallaPorAnio = ((double) cantidadSinFallaPorAnio / cantidadDiagnosticosPorAnio) * 100;
		String porcentajeSinFalla = String.format("%.1f %%", porcentajeSinFallaPorAnio);
		ventanaEstadisticas.getTextPorcentajeSinFalla().setText(porcentajeSinFalla);

		double porcentajeEnRePorAnio = ((double) cantidadEnRepPorAnio / cantidadDiagnosticosPorAnio) * 100;
		String porcentajeEnRep = String.format("%.1f %%", porcentajeEnRePorAnio);
		ventanaEstadisticas.getTextPorcentajeEnReparacion().setText(porcentajeEnRep);

		double porcentajeVentasPorAnio = ((double) cantidadVentasPorAnio / cantidadDiagnosticosPorAnio) * 100;
		String porcentajeVentas = String.format("%.1f %%", porcentajeVentasPorAnio);
		ventanaEstadisticas.getTextPorcentajeVentas().setText(porcentajeVentas);

		double porcentajeSinRepPorAnio = ((double) cantidadSinRepAnio / cantidadDiagnosticosPorAnio) * 100;
		String porcentajeSinRep = String.format("%.1f %%", porcentajeSinRepPorAnio);
		ventanaEstadisticas.getTextPorcentajeSinReparacion().setText(porcentajeSinRep);

		double porcentajeRepAcepPorAnio = ((double) cantidadReparadosAceptradosPorAnio / cantidadReparadosPorAnio)
				* 100;
		String porcentajeRepAcep = String.format("%.1f %%", porcentajeRepAcepPorAnio);
		ventanaEstadisticas.getTextPorcentajeReparadosAceptados().setText(porcentajeRepAcep);

		double porcentajeRepNoAcepPorAnio = ((double) cantidadReparadosNoAceptradosPorAnio / cantidadReparadosPorAnio)
				* 100;
		String porcentajeRepNoAcep = String.format("%.1f %%", porcentajeRepNoAcepPorAnio);
		ventanaEstadisticas.getTextPorcentajeReparadosNoAceptados().setText(porcentajeRepNoAcep);

		double porcentajeRepEsperaPorAnio = ((double) cantidadReparadosAlaEsperaPorAnio / cantidadReparadosPorAnio)
				* 100;
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

		double porcentajeIngresosPorCliente = ((double) totalIngresosXanioXcliente / cantidadIngresosPorAnio) * 100;
		String porcentajeIngresos = String.format("%.1f %%", porcentajeIngresosPorCliente);
		ventanaEstadisticas.getTextPorcIngresosPorCliente().setText(porcentajeIngresos);

		double porcentajeReparadosPorCliente = ((double) totalReparadosXanioXcliente / totalIngresosXanioXcliente)
				* 100;
		String porcentajeReparados = String.format("%.1f %%", porcentajeReparadosPorCliente);
		ventanaEstadisticas.getTextPorcRepPorCliente().setText(porcentajeReparados);

		double porcentajeRepEnGtiaPorCliente = ((double) totalGtiaXanioXcliente / totalIngresosXanioXcliente) * 100;
		String porcentajeEnGtia = String.format("%.1f %%", porcentajeRepEnGtiaPorCliente);
		ventanaEstadisticas.getTextPorcRepEnGtiaPorCliente().setText(porcentajeEnGtia);

		double porcentajeSinFallaPorCliente = ((double) totalSinFallaXanioXcliente / totalIngresosXanioXcliente) * 100;
		String porcentajeSinFalla = String.format("%.1f %%", porcentajeSinFallaPorCliente);
		ventanaEstadisticas.getTextPorcSinFallaPorCliente().setText(porcentajeSinFalla);

		double porcentajeEnRePorCliente = ((double) totalEnRepXanioXcliente / totalIngresosXanioXcliente) * 100;
		String porcentajeEnRep = String.format("%.1f %%", porcentajeEnRePorCliente);
		ventanaEstadisticas.getTextPorcEnRepPorCliente().setText(porcentajeEnRep);

		double porcentajeVentasPorCliente = ((double) totalVentaXanioXcliente / totalIngresosXanioXcliente) * 100;
		String porcentajeVentas = String.format("%.1f %%", porcentajeVentasPorCliente);
		ventanaEstadisticas.getTextPorcVentasPorCliente().setText(porcentajeVentas);

		double porcentajeSinRepPorCliente = ((double) totalSinRepXanioXcliente / totalIngresosXanioXcliente) * 100;
		String porcentajeSinRep = String.format("%.1f %%", porcentajeSinRepPorCliente);
		ventanaEstadisticas.getTextPorcSinRepPorCliente().setText(porcentajeSinRep);

		double porcentajeRepAcepPorCliente = ((double) TotalReparadosAceptradosXcliente / totalReparadosXanioXcliente)
				* 100;
		String porcentajeRepAcep = String.format("%.1f %%", porcentajeRepAcepPorCliente);
		ventanaEstadisticas.getTextPorcRepAcepPorCliente().setText(porcentajeRepAcep);

		double porcentajeRepNoAcepPorCliente = ((double) TotalReparadosNoAceptradosXcliente
				/ totalReparadosXanioXcliente) * 100;
		String porcentajeRepNoAcep = String.format("%.1f %%", porcentajeRepNoAcepPorCliente);
		ventanaEstadisticas.getTextPorcRepNoAcepPorCliente().setText(porcentajeRepNoAcep);

		double porcentajeRepEsperaPorCliente = ((double) TotalReparadosAlaEsperaXcliente / totalReparadosXanioXcliente)
				* 100;
		String porcentajeRepEspera = String.format("%.1f %%", porcentajeRepEsperaPorCliente);
		ventanaEstadisticas.getTextPorcRepEsperaPorCliente().setText(porcentajeRepEspera);

		ventanaEstadisticas.getTextFactClientePesos()
				.setText(monedaFormatter.formatPeso(Double.toString(facturacionPesoPorAnioPorCliente)));
		ventanaEstadisticas.getTextFactClienteDolar()
				.setText(monedaFormatter.formatDolar(Double.toString(facturacionDolarPorAnioPorCliente)));

		double porcentaFacturacionPesoPorCliente = (facturacionPesoPorAnioPorCliente / facturacionPesoPorAnio) * 100;
		String porcentaFacturacionpeso = String.format("%.1f %%", porcentaFacturacionPesoPorCliente);
		ventanaEstadisticas.getTextPorcFacturacionPesoCliente().setText(porcentaFacturacionpeso);

		double porcentaFacturacionDolarPorCliente = (facturacionDolarPorAnioPorCliente / facturacionDolarPorAnio) * 100;
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

		double porcentajeDiagnosticosPorTecnico = ((double) totalDiagnosticosXanioXtecnico
				/ cantidadDiagnosticosPorAnio) * 100;
		String porcentajeDiagnosticos = String.format("%.1f %%", porcentajeDiagnosticosPorTecnico);
		ventanaEstadisticas.getTextPorcentajeTotalRevisado().setText(porcentajeDiagnosticos);

		double porcentajeReparadosPorTecnico = ((double) totalReparadosXanioXtecnico / totalDiagnosticosXanioXtecnico)
				* 100;
		String porcentajeReparados = String.format("%.1f %%", porcentajeReparadosPorTecnico);
		ventanaEstadisticas.getTextPorcReparadosXTecnico().setText(porcentajeReparados);

		double porcentajeRepEnGtiaPorTecnico = ((double) totalGtiaXanioXtecnico / totalDiagnosticosXanioXtecnico) * 100;
		String porcentajeEnGtia = String.format("%.1f %%", porcentajeRepEnGtiaPorTecnico);
		ventanaEstadisticas.getTextPorcRepGtiaXtecnico().setText(porcentajeEnGtia);

		double porcentajeSinFallaPorTecnico = ((double) totalSinFallaXanioXtecnico / totalDiagnosticosXanioXtecnico)
				* 100;
		String porcentajeSinFalla = String.format("%.1f %%", porcentajeSinFallaPorTecnico);
		ventanaEstadisticas.getTextPorcSinFallasXtecnico().setText(porcentajeSinFalla);

		double porcentajeEnRePorTecnico = ((double) totalEnRepXanioXtecnico / totalDiagnosticosXanioXtecnico) * 100;
		String porcentajeEnRep = String.format("%.1f %%", porcentajeEnRePorTecnico);
		ventanaEstadisticas.getTextPorcEnRepXtecnico().setText(porcentajeEnRep);

		double porcentajeVentasPorTecnico = ((double) totalVentaXanioXtecnico / totalDiagnosticosXanioXtecnico) * 100;
		String porcentajeVentas = String.format("%.1f %%", porcentajeVentasPorTecnico);
		ventanaEstadisticas.getTextPorcVentasXtecnico().setText(porcentajeVentas);

		double porcentajeSinRepPorTecnico = ((double) totalSinRepXanioXtecnico / totalDiagnosticosXanioXtecnico) * 100;
		String porcentajeSinRep = String.format("%.1f %%", porcentajeSinRepPorTecnico);
		ventanaEstadisticas.getTextPorcSinRepXtecnico().setText(porcentajeSinRep);

		double porcentajeRepAcepPorTecnico = ((double) TotalReparadosAceptradosXtecnico
				/ totalDiagnosticosXanioXtecnico) * 100;
		String porcentajeRepAcep = String.format("%.1f %%", porcentajeRepAcepPorTecnico);
		ventanaEstadisticas.getTextPorcRepAcepXtecnico().setText(porcentajeRepAcep);

		double porcentajeRepNoAcepPorTecnico = ((double) TotalReparadosNoAceptradosXtecnico
				/ totalDiagnosticosXanioXtecnico) * 100;
		String porcentajeRepNoAcep = String.format("%.1f %%", porcentajeRepNoAcepPorTecnico);
		ventanaEstadisticas.getTextPorcRepNoAcepXtecnico().setText(porcentajeRepNoAcep);

		double porcentajeRepEsperaPorTecnico = ((double) TotalReparadosAlaEsperaXtecnico
				/ totalDiagnosticosXanioXtecnico) * 100;
		String porcentajeRepEspera = String.format("%.1f %%", porcentajeRepEsperaPorTecnico);
		ventanaEstadisticas.getTextPorcRepEsperaXtecnico().setText(porcentajeRepEspera);

		ventanaEstadisticas.getTextFacturacionTecnicoPesos()
				.setText(monedaFormatter.formatPeso(facturacionStrPesoXtecnico));
		ventanaEstadisticas.getTextFacturacionTecnicoDolares()
				.setText(monedaFormatter.formatPeso(facturacionStrDolarXtecnico));

		double porcentaFacturacionPesoPorTecnico = (facturacionPesoPorAnioPorTecnico / facturacionPesoPorAnio) * 100;
		String porcentaFacturacionPeso = String.format("%.1f %%", porcentaFacturacionPesoPorTecnico);
		ventanaEstadisticas.getTextPorcFacturacionTecnicoPesos().setText(porcentaFacturacionPeso);

		double porcentaFacturacionDolarPortecnico = (facturacionDolarPorAnioPorTecnico / facturacionDolarPorAnio) * 100;
		String porcentaFacturacionDolar = String.format("%.1f %%", porcentaFacturacionDolarPortecnico);
		ventanaEstadisticas.getTextPorcFacturacionTecnicoDolar().setText(porcentaFacturacionDolar);

	}

	private void mostrarGraficosPorAnio() {

		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());

		List<Integer> listaIngresos = modelo.dameIngresosPorAnioPorMes(anio);
		List<Integer> listaDiagnosticos = modelo.dameDiagnosticosPorAnioPorMes(anio);
		List<Double> listaFacturacion = modelo.dameFacturacionPorAnioPorMes(anio);

		DefaultCategoryDataset datosIngresos = new DefaultCategoryDataset();
		DefaultCategoryDataset datosDiagnosticos = new DefaultCategoryDataset();
		DefaultCategoryDataset datosFacturacion = new DefaultCategoryDataset();

		datosIngresos.setValue(listaIngresos.get(0), "Ingresos", "ENE");
		datosIngresos.setValue(listaIngresos.get(1), "Ingresos", "FEB");
		datosIngresos.setValue(listaIngresos.get(2), "Ingresos", "MAR");
		datosIngresos.setValue(listaIngresos.get(3), "Ingresos", "ABR");
		datosIngresos.setValue(listaIngresos.get(4), "Ingresos", "MAY");
		datosIngresos.setValue(listaIngresos.get(5), "Ingresos", "JUN");
		datosIngresos.setValue(listaIngresos.get(6), "Ingresos", "JUL");
		datosIngresos.setValue(listaIngresos.get(7), "Ingresos", "AGO");
		datosIngresos.setValue(listaIngresos.get(8), "Ingresos", "SEP");
		datosIngresos.setValue(listaIngresos.get(9), "Ingresos", "OCT");
		datosIngresos.setValue(listaIngresos.get(10), "Ingresos", "NOV");
		datosIngresos.setValue(listaIngresos.get(11), "Ingresos", "DIC");

		datosDiagnosticos.setValue(listaDiagnosticos.get(0), "Diagnósticos", "ENE");
		datosDiagnosticos.setValue(listaDiagnosticos.get(1), "Diagnósticos", "FEB");
		datosDiagnosticos.setValue(listaDiagnosticos.get(2), "Diagnósticos", "MAR");
		datosDiagnosticos.setValue(listaDiagnosticos.get(3), "Diagnósticos", "ABR");
		datosDiagnosticos.setValue(listaDiagnosticos.get(4), "Diagnósticos", "MAY");
		datosDiagnosticos.setValue(listaDiagnosticos.get(5), "Diagnósticos", "JUN");
		datosDiagnosticos.setValue(listaDiagnosticos.get(6), "Diagnósticos", "JUL");
		datosDiagnosticos.setValue(listaDiagnosticos.get(7), "Diagnósticos", "AGO");
		datosDiagnosticos.setValue(listaDiagnosticos.get(8), "Diagnósticos", "SEP");
		datosDiagnosticos.setValue(listaDiagnosticos.get(9), "Diagnósticos", "OCT");
		datosDiagnosticos.setValue(listaDiagnosticos.get(10), "Diagnósticos", "NOV");
		datosDiagnosticos.setValue(listaDiagnosticos.get(11), "Diagnósticos", "DIC");

		datosFacturacion.setValue(listaFacturacion.get(0), "Facturación", "ENE");
		datosFacturacion.setValue(listaFacturacion.get(1), "Facturación", "FEB");
		datosFacturacion.setValue(listaFacturacion.get(2), "Facturación", "MAR");
		datosFacturacion.setValue(listaFacturacion.get(3), "Facturación", "ABR");
		datosFacturacion.setValue(listaFacturacion.get(4), "Facturación", "MAY");
		datosFacturacion.setValue(listaFacturacion.get(5), "Facturación", "JUN");
		datosFacturacion.setValue(listaFacturacion.get(6), "Facturación", "JUL");
		datosFacturacion.setValue(listaFacturacion.get(7), "Facturación", "AGO");
		datosFacturacion.setValue(listaFacturacion.get(8), "Facturación", "SEP");
		datosFacturacion.setValue(listaFacturacion.get(9), "Facturación", "OCT");
		datosFacturacion.setValue(listaFacturacion.get(10), "Facturación", "NOV");
		datosFacturacion.setValue(listaFacturacion.get(11), "Facturación", "DIC");

		JFreeChart grafico_ingresos = ChartFactory.createBarChart("INGRESOS", "", "Cantidad", datosIngresos,
				PlotOrientation.VERTICAL, false, true, false);
		JFreeChart grafico_diagnosticos = ChartFactory.createBarChart("DIAGNÓSTICOS", "", "Cantidad", datosDiagnosticos,
				PlotOrientation.VERTICAL, false, true, false);
		JFreeChart grafico_facturacion = ChartFactory.createBarChart("FACTURACIÓN", null, "Pesos($)", datosFacturacion,
				PlotOrientation.VERTICAL, false, true, false);

		NumberAxis yAxisIngresos = (NumberAxis) grafico_ingresos.getCategoryPlot().getRangeAxis();
		yAxisIngresos.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		NumberAxis yAxisDiagnosticos = (NumberAxis) grafico_diagnosticos.getCategoryPlot().getRangeAxis();
		yAxisDiagnosticos.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		CategoryPlot plot_ingreso = (CategoryPlot) grafico_ingresos.getPlot();
		CategoryPlot plot_diagnostico = (CategoryPlot) grafico_diagnosticos.getPlot();
		CategoryPlot plot_facturacion = (CategoryPlot) grafico_facturacion.getPlot();

		BarRenderer renderer_ingreso = (BarRenderer) plot_ingreso.getRenderer();
		renderer_ingreso.setDrawBarOutline(false);
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(100, 149, 237), 0.0f, 0.0f,
				new Color(70, 130, 180));
		renderer_ingreso.setSeriesPaint(0, gp0);

		BarRenderer renderer_diagnostico = (BarRenderer) plot_diagnostico.getRenderer();
		renderer_diagnostico.setDrawBarOutline(false);
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(102, 205, 170), 0.0f, 0.0f,
				new Color(60, 179, 113));
		renderer_diagnostico.setSeriesPaint(0, gp1);

		BarRenderer renderer_facturacion = (BarRenderer) plot_facturacion.getRenderer();
		renderer_facturacion.setDrawBarOutline(false);
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(240, 128, 128), 0.0f, 0.0f, new Color(205, 92, 92));
		renderer_facturacion.setSeriesPaint(0, gp2);

		grafico_ingresos.getTitle().setFont(titleFont);
		grafico_ingresos.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		grafico_diagnosticos.getTitle().setFont(titleFont);
		grafico_diagnosticos.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		grafico_facturacion.getTitle().setFont(titleFont);
		grafico_facturacion.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		renderer_ingreso.setItemLabelGenerator(generator);
		renderer_diagnostico.setItemLabelGenerator(generator);
		renderer_facturacion.setItemLabelGenerator(generator);

		renderer_ingreso.setItemLabelFont(labelFont);
		renderer_ingreso.setItemLabelPaint(labelColor);

		renderer_diagnostico.setItemLabelFont(labelFont);
		renderer_diagnostico.setItemLabelPaint(labelColor);

		renderer_facturacion.setItemLabelFont(labelFont);
		renderer_facturacion.setItemLabelPaint(labelColor);

		renderer_ingreso.setItemLabelsVisible(true);
		renderer_diagnostico.setItemLabelsVisible(true);
		renderer_facturacion.setItemLabelsVisible(true);

		renderer_facturacion.setPositiveItemLabelPosition(positionNumeroGrande);
		renderer_diagnostico.setPositiveItemLabelPosition(positionNumeroChico);
		renderer_ingreso.setPositiveItemLabelPosition(positionNumeroChico);

		ChartPanel panelGraficoIngresos = new ChartPanel(grafico_ingresos);
		panelGraficoIngresos.setMouseWheelEnabled(true);

		ChartPanel panelGraficoDiagnosticos = new ChartPanel(grafico_diagnosticos);
		panelGraficoDiagnosticos.setMouseWheelEnabled(true);

		ChartPanel panelGraficoFacturacion = new ChartPanel(grafico_facturacion);
		panelGraficoFacturacion.setMouseWheelEnabled(true);

		panelGraficoIngresos.setMinimumDrawHeight(400);
		panelGraficoIngresos.setMaximumDrawWidth(1280);

		panelGraficoDiagnosticos.setMinimumDrawHeight(400);
		panelGraficoDiagnosticos.setMaximumDrawWidth(1280);

		panelGraficoFacturacion.setMinimumDrawHeight(400);
		panelGraficoFacturacion.setMaximumDrawWidth(1280);

		ventanaEstadisticas.getPanel_Ingresos().add(panelGraficoIngresos);
		ventanaEstadisticas.getPanel_Diagnosticos().add(panelGraficoDiagnosticos);
		ventanaEstadisticas.getPanel_Facturacion().add(panelGraficoFacturacion);

		if (seleccionDetalleEstadisticas.compareTo("MOSTRAR DETALLE") == 0) {

			ventanaEstadisticas.getPanel_Facturacion().setVisible(true);

		} else {

			ventanaEstadisticas.getPanel_Facturacion().setVisible(false);

		}

		ventanaEstadisticas.repaint();

	}

	private void mostrarGraficosPorTecnico() {

		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		String tecnico = ventanaEstadisticas.getComboTecnico().getSelectedItem().toString();

		int idTecnico = modelo.idUsuarioporNombre(tecnico);

		List<Integer> listaDiagnosticos = modelo.dameDiagnosticosPorAnioPorTecnico(anio, idTecnico);
		List<Integer> listaAceptaciones = modelo.dameAceptacionesPorAnioPorTecnico(anio, idTecnico);
		List<Double> listaFacturacion = modelo.dameFacturacionPorAnioPorTecnico(anio, idTecnico);

		DefaultCategoryDataset datosDiagnosticos = new DefaultCategoryDataset();
		DefaultCategoryDataset datosAceptaciones = new DefaultCategoryDataset();
		DefaultCategoryDataset datosFacturacion = new DefaultCategoryDataset();

		datosDiagnosticos.setValue(listaDiagnosticos.get(0), "Diagnósticos", "ENE");
		datosDiagnosticos.setValue(listaDiagnosticos.get(1), "Diagnósticos", "FEB");
		datosDiagnosticos.setValue(listaDiagnosticos.get(2), "Diagnósticos", "MAR");
		datosDiagnosticos.setValue(listaDiagnosticos.get(3), "Diagnósticos", "ABR");
		datosDiagnosticos.setValue(listaDiagnosticos.get(4), "Diagnósticos", "MAY");
		datosDiagnosticos.setValue(listaDiagnosticos.get(5), "Diagnósticos", "JUN");
		datosDiagnosticos.setValue(listaDiagnosticos.get(6), "Diagnósticos", "JUL");
		datosDiagnosticos.setValue(listaDiagnosticos.get(7), "Diagnósticos", "AGO");
		datosDiagnosticos.setValue(listaDiagnosticos.get(8), "Diagnósticos", "SEP");
		datosDiagnosticos.setValue(listaDiagnosticos.get(9), "Diagnósticos", "OCT");
		datosDiagnosticos.setValue(listaDiagnosticos.get(10), "Diagnósticos", "NOV");
		datosDiagnosticos.setValue(listaDiagnosticos.get(11), "Diagnósticos", "DIC");

		datosAceptaciones.setValue(listaAceptaciones.get(0), "Aceptaciones", "ENE");
		datosAceptaciones.setValue(listaAceptaciones.get(1), "Aceptaciones", "FEB");
		datosAceptaciones.setValue(listaAceptaciones.get(2), "Aceptaciones", "MAR");
		datosAceptaciones.setValue(listaAceptaciones.get(3), "Aceptaciones", "ABR");
		datosAceptaciones.setValue(listaAceptaciones.get(4), "Aceptaciones", "MAY");
		datosAceptaciones.setValue(listaAceptaciones.get(5), "Aceptaciones", "JUN");
		datosAceptaciones.setValue(listaAceptaciones.get(6), "Aceptaciones", "JUL");
		datosAceptaciones.setValue(listaAceptaciones.get(7), "Aceptaciones", "AGO");
		datosAceptaciones.setValue(listaAceptaciones.get(8), "Aceptaciones", "SEP");
		datosAceptaciones.setValue(listaAceptaciones.get(9), "Aceptaciones", "OCT");
		datosAceptaciones.setValue(listaAceptaciones.get(10), "Aceptaciones", "NOV");
		datosAceptaciones.setValue(listaAceptaciones.get(11), "Aceptaciones", "DIC");

		datosFacturacion.setValue(listaFacturacion.get(0), "Facturación", "ENE");
		datosFacturacion.setValue(listaFacturacion.get(1), "Facturación", "FEB");
		datosFacturacion.setValue(listaFacturacion.get(2), "Facturación", "MAR");
		datosFacturacion.setValue(listaFacturacion.get(3), "Facturación", "ABR");
		datosFacturacion.setValue(listaFacturacion.get(4), "Facturación", "MAY");
		datosFacturacion.setValue(listaFacturacion.get(5), "Facturación", "JUN");
		datosFacturacion.setValue(listaFacturacion.get(6), "Facturación", "JUL");
		datosFacturacion.setValue(listaFacturacion.get(7), "Facturación", "AGO");
		datosFacturacion.setValue(listaFacturacion.get(8), "Facturación", "SEP");
		datosFacturacion.setValue(listaFacturacion.get(9), "Facturación", "OCT");
		datosFacturacion.setValue(listaFacturacion.get(10), "Facturación", "NOV");
		datosFacturacion.setValue(listaFacturacion.get(11), "Facturación", "DIC");

		JFreeChart grafico_diagnosticos = ChartFactory.createBarChart("DIAGNÓSTICOS", "", "Cantidad", datosDiagnosticos,
				PlotOrientation.VERTICAL, false, true, false);
		JFreeChart grafico_aceptaciones = ChartFactory.createBarChart("ACEPTACIONES", "", "Cantidad", datosAceptaciones,
				PlotOrientation.VERTICAL, false, true, false);
		JFreeChart grafico_facturacion = ChartFactory.createBarChart("FACTURACIÓN", null, "Pesos($)", datosFacturacion,
				PlotOrientation.VERTICAL, false, true, false);

		NumberAxis yAxisaceptaciones = (NumberAxis) grafico_aceptaciones.getCategoryPlot().getRangeAxis();
		yAxisaceptaciones.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		NumberAxis yAxisDiagnosticos = (NumberAxis) grafico_diagnosticos.getCategoryPlot().getRangeAxis();
		yAxisDiagnosticos.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		CategoryPlot plot_aceptaciones = (CategoryPlot) grafico_aceptaciones.getPlot();
		CategoryPlot plot_diagnostico = (CategoryPlot) grafico_diagnosticos.getPlot();
		CategoryPlot plot_facturacion = (CategoryPlot) grafico_facturacion.getPlot();

		BarRenderer renderer_diagnostico = (BarRenderer) plot_diagnostico.getRenderer();
		renderer_diagnostico.setDrawBarOutline(false);
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(102, 205, 170), 0.0f, 0.0f,
				new Color(60, 179, 113));
		renderer_diagnostico.setSeriesPaint(0, gp1);

		BarRenderer renderer_aceptaciones = (BarRenderer) plot_aceptaciones.getRenderer();
		renderer_aceptaciones.setDrawBarOutline(false);
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(186, 85, 211), 0.0f, 0.0f, new Color(148, 0, 211));
		renderer_aceptaciones.setSeriesPaint(0, gp0);

		BarRenderer renderer_facturacion = (BarRenderer) plot_facturacion.getRenderer();
		renderer_facturacion.setDrawBarOutline(false);
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(240, 128, 128), 0.0f, 0.0f, new Color(205, 92, 92));
		renderer_facturacion.setSeriesPaint(0, gp2);

		grafico_aceptaciones.getTitle().setFont(titleFont);
		grafico_aceptaciones.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		grafico_diagnosticos.getTitle().setFont(titleFont);
		grafico_diagnosticos.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		grafico_facturacion.getTitle().setFont(titleFont);
		grafico_facturacion.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		renderer_aceptaciones.setItemLabelGenerator(generator);
		renderer_diagnostico.setItemLabelGenerator(generator);
		renderer_facturacion.setItemLabelGenerator(generator);

		renderer_aceptaciones.setItemLabelFont(labelFont);
		renderer_aceptaciones.setItemLabelPaint(labelColor);

		renderer_diagnostico.setItemLabelFont(labelFont);
		renderer_diagnostico.setItemLabelPaint(labelColor);

		renderer_facturacion.setItemLabelFont(labelFont);
		renderer_facturacion.setItemLabelPaint(labelColor);

		renderer_aceptaciones.setItemLabelsVisible(true);
		renderer_diagnostico.setItemLabelsVisible(true);
		renderer_facturacion.setItemLabelsVisible(true);

		renderer_facturacion.setPositiveItemLabelPosition(positionNumeroGrande);
		renderer_diagnostico.setPositiveItemLabelPosition(positionNumeroChico);
		renderer_aceptaciones.setPositiveItemLabelPosition(positionNumeroChico);

		ChartPanel panelGraficoAceptaciones = new ChartPanel(grafico_aceptaciones);
		panelGraficoAceptaciones.setMouseWheelEnabled(true);

		ChartPanel panelGraficoDiagnosticos = new ChartPanel(grafico_diagnosticos);
		panelGraficoDiagnosticos.setMouseWheelEnabled(true);

		ChartPanel panelGraficoFacturacion = new ChartPanel(grafico_facturacion);
		panelGraficoFacturacion.setMouseWheelEnabled(true);

		panelGraficoAceptaciones.setMinimumDrawHeight(400);
		panelGraficoAceptaciones.setMaximumDrawWidth(1280);

		panelGraficoDiagnosticos.setMinimumDrawHeight(400);
		panelGraficoDiagnosticos.setMaximumDrawWidth(1280);

		panelGraficoFacturacion.setMinimumDrawHeight(400);
		panelGraficoFacturacion.setMaximumDrawWidth(1280);

		ventanaEstadisticas.getPanel_Diagnosticos().add(panelGraficoAceptaciones, BorderLayout.CENTER);
		ventanaEstadisticas.getPanel_Ingresos().add(panelGraficoDiagnosticos, BorderLayout.CENTER);
		ventanaEstadisticas.getPanel_Facturacion().add(panelGraficoFacturacion, BorderLayout.CENTER);

		ventanaEstadisticas.repaint();

	}

	private void mostrarGraficosPorCliente() {

		int anio = Integer.parseInt(ventanaEstadisticas.getComboAnio().getSelectedItem().toString());
		String cliente = ventanaEstadisticas.getComboCliente().getSelectedItem().toString();

		int idCliente = modelo.idClienteporNombre(cliente);

		List<Integer> listaIngresos = modelo.dameIngresosPorAnioPorCliente(anio, idCliente);
		List<Integer> listaAceptaciones = modelo.dameAceptacionesPorAnioPorCliente(anio, idCliente);
		List<Double> listaFacturacion = modelo.dameFacturacionPorAnioPorCliente(anio, idCliente);

		DefaultCategoryDataset datosIngresos = new DefaultCategoryDataset();
		DefaultCategoryDataset datosAceptaciones = new DefaultCategoryDataset();
		DefaultCategoryDataset datosFacturacion = new DefaultCategoryDataset();
		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();

		datosIngresos.setValue(listaIngresos.get(0), "Ingresos", "ENE");
		datosIngresos.setValue(listaIngresos.get(1), "Ingresos", "FEB");
		datosIngresos.setValue(listaIngresos.get(2), "Ingresos", "MAR");
		datosIngresos.setValue(listaIngresos.get(3), "Ingresos", "ABR");
		datosIngresos.setValue(listaIngresos.get(4), "Ingresos", "MAY");
		datosIngresos.setValue(listaIngresos.get(5), "Ingresos", "JUN");
		datosIngresos.setValue(listaIngresos.get(6), "Ingresos", "JUL");
		datosIngresos.setValue(listaIngresos.get(7), "Ingresos", "AGO");
		datosIngresos.setValue(listaIngresos.get(8), "Ingresos", "SEP");
		datosIngresos.setValue(listaIngresos.get(9), "Ingresos", "OCT");
		datosIngresos.setValue(listaIngresos.get(10), "Ingresos", "NOV");
		datosIngresos.setValue(listaIngresos.get(11), "Ingresos", "DIC");

		datosAceptaciones.setValue(listaAceptaciones.get(0), "Aceptaciones", "ENE");
		datosAceptaciones.setValue(listaAceptaciones.get(1), "Aceptaciones", "FEB");
		datosAceptaciones.setValue(listaAceptaciones.get(2), "Aceptaciones", "MAR");
		datosAceptaciones.setValue(listaAceptaciones.get(3), "Aceptaciones", "ABR");
		datosAceptaciones.setValue(listaAceptaciones.get(4), "Aceptaciones", "MAY");
		datosAceptaciones.setValue(listaAceptaciones.get(5), "Aceptaciones", "JUN");
		datosAceptaciones.setValue(listaAceptaciones.get(6), "Aceptaciones", "JUL");
		datosAceptaciones.setValue(listaAceptaciones.get(7), "Aceptaciones", "AGO");
		datosAceptaciones.setValue(listaAceptaciones.get(8), "Aceptaciones", "SEP");
		datosAceptaciones.setValue(listaAceptaciones.get(9), "Aceptaciones", "OCT");
		datosAceptaciones.setValue(listaAceptaciones.get(10), "Aceptaciones", "NOV");
		datosAceptaciones.setValue(listaAceptaciones.get(11), "Aceptaciones", "DIC");

		datosFacturacion.setValue(listaFacturacion.get(0), "Facturación", "ENE");
		datosFacturacion.setValue(listaFacturacion.get(1), "Facturación", "FEB");
		datosFacturacion.setValue(listaFacturacion.get(2), "Facturación", "MAR");
		datosFacturacion.setValue(listaFacturacion.get(3), "Facturación", "ABR");
		datosFacturacion.setValue(listaFacturacion.get(4), "Facturación", "MAY");
		datosFacturacion.setValue(listaFacturacion.get(5), "Facturación", "JUN");
		datosFacturacion.setValue(listaFacturacion.get(6), "Facturación", "JUL");
		datosFacturacion.setValue(listaFacturacion.get(7), "Facturación", "AGO");
		datosFacturacion.setValue(listaFacturacion.get(8), "Facturación", "SEP");
		datosFacturacion.setValue(listaFacturacion.get(9), "Facturación", "OCT");
		datosFacturacion.setValue(listaFacturacion.get(10), "Facturación", "NOV");
		datosFacturacion.setValue(listaFacturacion.get(11), "Facturación", "DIC");

		JFreeChart grafico_Ingresos = ChartFactory.createBarChart("INGRESOS", "", "Cantidad", datosIngresos,
				PlotOrientation.VERTICAL, false, true, false);
		JFreeChart grafico_aceptaciones = ChartFactory.createBarChart("ACEPTACIONES", "", "Cantidad", datosAceptaciones,
				PlotOrientation.VERTICAL, false, true, false);
		JFreeChart grafico_facturacion = ChartFactory.createBarChart("FACTURACIÓN", null, "Pesos($)", datosFacturacion,
				PlotOrientation.VERTICAL, false, true, false);

		NumberAxis yAxisIngresos = (NumberAxis) grafico_Ingresos.getCategoryPlot().getRangeAxis();
		yAxisIngresos.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		NumberAxis yAxisAceptaciones = (NumberAxis) grafico_aceptaciones.getCategoryPlot().getRangeAxis();
		yAxisAceptaciones.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		CategoryPlot plot_ingreso = (CategoryPlot) grafico_Ingresos.getPlot();
		CategoryPlot plot_aceptaciones = (CategoryPlot) grafico_aceptaciones.getPlot();
		CategoryPlot plot_facturacion = (CategoryPlot) grafico_facturacion.getPlot();

		BarRenderer renderer_ingreso = (BarRenderer) plot_ingreso.getRenderer();
		renderer_ingreso.setDrawBarOutline(false);
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, new Color(100, 149, 237), 0.0f, 0.0f,
				new Color(70, 130, 180));
		renderer_ingreso.setSeriesPaint(0, gp1);

		BarRenderer renderer_aceptaciones = (BarRenderer) plot_aceptaciones.getRenderer();
		renderer_aceptaciones.setDrawBarOutline(false);
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(186, 85, 211), 0.0f, 0.0f, new Color(148, 0, 211));
		renderer_aceptaciones.setSeriesPaint(0, gp0);

		BarRenderer renderer_facturacion = (BarRenderer) plot_facturacion.getRenderer();
		renderer_facturacion.setDrawBarOutline(false);
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, new Color(240, 128, 128), 0.0f, 0.0f, new Color(205, 92, 92));
		renderer_facturacion.setSeriesPaint(0, gp2);

		grafico_aceptaciones.getTitle().setFont(titleFont);
		grafico_aceptaciones.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		grafico_Ingresos.getTitle().setFont(titleFont);
		grafico_Ingresos.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		grafico_facturacion.getTitle().setFont(titleFont);
		grafico_facturacion.getTitle().setPadding(new RectangleInsets(20, 0, 0, 0));

		renderer_aceptaciones.setItemLabelGenerator(generator);
		renderer_ingreso.setItemLabelGenerator(generator);
		renderer_facturacion.setItemLabelGenerator(generator);

		renderer_aceptaciones.setItemLabelFont(labelFont);
		renderer_aceptaciones.setItemLabelPaint(labelColor);

		renderer_ingreso.setItemLabelFont(labelFont);
		renderer_ingreso.setItemLabelPaint(labelColor);

		renderer_facturacion.setItemLabelFont(labelFont);
		renderer_facturacion.setItemLabelPaint(labelColor);

		renderer_aceptaciones.setItemLabelsVisible(true);
		renderer_ingreso.setItemLabelsVisible(true);
		renderer_facturacion.setItemLabelsVisible(true);

		renderer_facturacion.setPositiveItemLabelPosition(positionNumeroGrande);
		renderer_ingreso.setPositiveItemLabelPosition(positionNumeroChico);
		renderer_aceptaciones.setPositiveItemLabelPosition(positionNumeroChico);

		ChartPanel panelGraficoAceptaciones = new ChartPanel(grafico_aceptaciones);
		panelGraficoAceptaciones.setMouseWheelEnabled(true);

		ChartPanel panelGraficoIngresos = new ChartPanel(grafico_Ingresos);
		panelGraficoIngresos.setMouseWheelEnabled(true);

		ChartPanel panelGraficoFacturacion = new ChartPanel(grafico_facturacion);
		panelGraficoFacturacion.setMouseWheelEnabled(true);

		ventanaEstadisticas.getPanel_Ingresos().add(panelGraficoIngresos, BorderLayout.CENTER);
		ventanaEstadisticas.getPanel_Diagnosticos().add(panelGraficoAceptaciones, BorderLayout.CENTER);
		ventanaEstadisticas.getPanel_Facturacion().add(panelGraficoFacturacion, BorderLayout.CENTER);

		panelGraficoAceptaciones.setMinimumDrawHeight(400);
		panelGraficoAceptaciones.setMaximumDrawWidth(1280);

		panelGraficoIngresos.setMinimumDrawHeight(400);
		panelGraficoIngresos.setMaximumDrawWidth(1280);

		panelGraficoFacturacion.setMinimumDrawHeight(400);
		panelGraficoFacturacion.setMaximumDrawWidth(1280);

		ventanaEstadisticas.repaint();

	}

	private void mostrarGraficoFacturacionXcliente() {

		DefaultPieDataset dataset = createDataset();

		// Crear el gráfico de torta
		JFreeChart chart = ChartFactory.createPieChart("", dataset, false, // Incluir leyenda
				true, false);

		// Color labelBackColor = new Color(210, 210, 210);

		Color BackgrounColor = new Color(197, 202, 233);
		Color BorderLine = new Color(121, 134, 203);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(290);
		plot.setBackgroundPaint(BackgrounColor);
		// plot.setBaseSectionOutlinePaint(BorderLine);
		plot.setDirection(Rotation.CLOCKWISE);

		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));

		plot.setLabelFont(labelFontPie);
		plot.setLabelBackgroundPaint(BackgrounColor);
		plot.setLabelOutlinePaint(BackgrounColor);

		plot.setShadowPaint(Color.BLACK);
		plot.setSectionOutlinesVisible(false);

		ChartPanel chartGraficoFacturacion = new ChartPanel(chart);
		chartGraficoFacturacion.setMouseWheelEnabled(true);
		chartGraficoFacturacion.setBackground(BorderLine);

		ventanaFacturacionXcliente.getPanelGraficoCliente().add(chartGraficoFacturacion, BorderLayout.CENTER);

		// ventanaFacturacionXcliente.getPanelGraficoCliente().repaint();

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

		if (this.ventanaListadoReparaciones != null) {
			if (arg0.getSource() == this.ventanaListadoReparaciones.getTblReparaciones()) {

				int row = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedRow();
				int col = this.ventanaListadoReparaciones.getTblReparaciones().getSelectedColumn();

				int els = 0;
				if (col == 0) {

					els = Integer.parseInt(
							this.ventanaListadoReparaciones.getTblReparaciones().getValueAt(row, col).toString());

					NumeroELSSeleccionado = els;

					try {
						ventanaVisualizarEquipos = controladorReparacion
								.TomarDatosDeTablasListado(NumeroELSSeleccionado, this.ventanaVisualizarEquipos);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					controladorReparacion.agregarListenersVentanaVisualizarEquiposListado(ventanaVisualizarEquipos);

				}
			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxELS()) {
				if (this.ventanaListadoReparaciones.getChckbxELS().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(100);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEntrada()) {
				if (this.ventanaListadoReparaciones.getChckbxEntrada().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(100);
				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxSucursal()) {
				if (this.ventanaListadoReparaciones.getChckbxSucursal().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEquipo()) {
				if (this.ventanaListadoReparaciones.getChckbxEquipo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxMarca()) {
				if (this.ventanaListadoReparaciones.getChckbxMarca().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(250);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxModelo()) {
				if (this.ventanaListadoReparaciones.getChckbxModelo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxSerie()) {
				if (this.ventanaListadoReparaciones.getChckbxSerie().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxAviso()) {
				if (this.ventanaListadoReparaciones.getChckbxAviso().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxRevisión()) {
				if (this.ventanaListadoReparaciones.getChckbxRevisión().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(110);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxClienteCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxClienteCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoTec()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoTec().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoCom()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoCom().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(200);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoFis()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoFis().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxTecnico()) {
				if (this.ventanaListadoReparaciones.getChckbxTecnico().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxUbicacionRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxUbicacionRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxNumeroRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxNumeroRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(150);

				}

			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(80);

				}

			}
//
//			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioPeso()) {
//				if (this.ventanaListadoReparaciones.getChckbxPrecioPeso().isSelected()) {
//
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMinWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMaxWidth(0);
//
//				} else {
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMinWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMaxWidth(150);
//
//				}
//
//			}
//
//			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioDolar()) {
//				if (this.ventanaListadoReparaciones.getChckbxPrecioDolar().isSelected()) {
//
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMinWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMaxWidth(0);
//
//				} else {
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMinWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMaxWidth(150);
//
//				}
//
//			}
//
//			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxPago()) {
//				if (this.ventanaListadoReparaciones.getChckbxPago().isSelected()) {
//
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMinWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMaxWidth(0);
//
//				} else {
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMinWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMaxWidth(150);
//
//				}
//
//			}

			if (arg0.getSource() == this.ventanaListadoReparaciones.getChckbxIngreso()) {
				if (this.ventanaListadoReparaciones.getChckbxIngreso().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMaxWidth(80);

				}

			}

		}

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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

		int column = ventanaListadoReparaciones.getTblReparaciones().columnAtPoint(arg0.getPoint());

		// Verificar si el mouse est� sobre la celda deseada
		if (column == 0) {
			ventanaListadoReparaciones.getTblReparaciones().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			ventanaListadoReparaciones.getTblReparaciones().setCursor(Cursor.getDefaultCursor());
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (this.ventanaListadoReparaciones != null) {

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxELS()) {
				if (this.ventanaListadoReparaciones.getChckbxELS().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMinWidth(60);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(0))
							.setMaxWidth(100);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEntrada()) {
				if (this.ventanaListadoReparaciones.getChckbxEntrada().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(1))
							.setMaxWidth(100);
				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(2))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxSucursal()) {
				if (this.ventanaListadoReparaciones.getChckbxSucursal().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(3))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEquipo()) {
				if (this.ventanaListadoReparaciones.getChckbxEquipo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(4))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxMarca()) {
				if (this.ventanaListadoReparaciones.getChckbxMarca().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMinWidth(200);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(5))
							.setMaxWidth(250);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxModelo()) {
				if (this.ventanaListadoReparaciones.getChckbxModelo().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(6))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxSerie()) {
				if (this.ventanaListadoReparaciones.getChckbxSerie().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(7))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxAviso()) {
				if (this.ventanaListadoReparaciones.getChckbxMarca().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(8))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxRevisión()) {
				if (this.ventanaListadoReparaciones.getChckbxRevisión().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(9))
							.setMaxWidth(110);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxClienteCliente()) {
				if (this.ventanaListadoReparaciones.getChckbxClienteCliente().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMinWidth(110);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(10))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoTec()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoTec().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMinWidth(120);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(11))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoCom()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoCom().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMinWidth(150);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(12))
							.setMaxWidth(200);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxEstadoFis()) {
				if (this.ventanaListadoReparaciones.getChckbxEstadoFis().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(13))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxTecnico()) {
				if (this.ventanaListadoReparaciones.getChckbxTecnico().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(14))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxUbicacionRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxUbicacionRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(15))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxNumeroRemito()) {
				if (this.ventanaListadoReparaciones.getChckbxNumeroRemito().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMinWidth(100);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(16))
							.setMaxWidth(150);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoGeneradoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(17))
							.setMaxWidth(80);

				}

			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna()) {
				if (this.ventanaListadoReparaciones.getChckbxPresupuestoEnviadoColumna().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(18))
							.setMaxWidth(80);

				}

			}

//			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioPeso()) {
//				if (this.ventanaListadoReparaciones.getChckbxPrecioPeso().isSelected()) {
//
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMinWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMaxWidth(0);
//
//				} else {
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMinWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(19))
//							.setMaxWidth(150);
//
//				}
//
//			}
//
//			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPrecioDolar()) {
//				if (this.ventanaListadoReparaciones.getChckbxPrecioDolar().isSelected()) {
//
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMinWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMaxWidth(0);
//
//				} else {
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMinWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(20))
//							.setMaxWidth(150);
//
//				}
//
//			}
//
//			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxPago()) {
//				if (this.ventanaListadoReparaciones.getChckbxPago().isSelected()) {
//
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMinWidth(0);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMaxWidth(0);
//
//				} else {
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMinWidth(100);
//					this.ventanaListadoReparaciones.getTblReparaciones()
//							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(21))
//							.setMaxWidth(150);
//
//				}
//
//			}

			if (e.getSource() == this.ventanaListadoReparaciones.getChckbxIngreso()) {
				if (this.ventanaListadoReparaciones.getChckbxIngreso().isSelected()) {

					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMinWidth(0);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMaxWidth(0);

				} else {
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMinWidth(80);
					this.ventanaListadoReparaciones.getTblReparaciones()
							.getColumn(this.ventanaListadoReparaciones.getTblReparaciones().getColumnName(22))
							.setMaxWidth(80);

				}

			}

		}

	}

}
