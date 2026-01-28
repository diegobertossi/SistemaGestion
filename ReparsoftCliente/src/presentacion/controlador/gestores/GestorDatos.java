package presentacion.controlador.gestores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

import dto.ReparacionDTO;
import dto.RegistroEntradaReporteDTO;
import modelo.Agenda;
import presentacion.vista.VentanaAgregarEquipo;
import presentacion.vista.VentanaBusquedaEquipo;
import presentacion.vista.VentanaVisualizarEquipos;
import tiposPropios.MonedaFormatter;

/**
 * GestorDatos Responsable de: - Extraer datos de pantallas - Convertir a DTOs -
 * Validar caracteres especiales - Parsear valores monetarios
 */
public class GestorDatos {

	private Agenda agenda;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private MonedaFormatter monedaFormatter = new MonedaFormatter();
	private List<String> caracteresNoValidos = new ArrayList<>();

	/**
	 * Constructor
	 */
	public GestorDatos(Agenda agenda) {
		this.agenda = agenda;
	}

	/**
	 * Extrae datos de visualización en DTO
	 */
	public ReparacionDTO extraerDatos(VentanaVisualizarEquipos ventana, ReparacionDTO reparacionActual) {

		int els = Integer.parseInt(ventana.getTextELS());

		String falla = ventana.getTextFalla().getText();
		String solucion = ventana.getTextDiagnostico().getText();
		String informe = ventana.getTextInformeCliente().getText();
		String remito = ventana.getTextRemitoCliente().getText();
		String nombreEquipo = ventana.getTextNombreEquipo().getText();
		String modelo = ventana.getTextModelo().getText();
		String marca = ventana.getTextMarca().getText();
		String serie = ventana.getTextNSerie().getText();
		String aviso = ventana.getTextAvisoCliente().getText();
		String clienteCliente = ventana.getTextClienteCliente().getText();

		// Validar caracteres
		if (verificarCaracteresPermitidos(falla, solucion, informe, nombreEquipo, modelo, marca, serie, remito, aviso, clienteCliente)) {
			mostrarErrorCaracteresInvalidos(ventana);
			return null;
		}

		// Parsear fechas
		String fechaEntrada = null;
		java.util.Date fechaEntradaVisual = ventana.getFechaEntrada().getDate();
		if (fechaEntradaVisual != null) {
			fechaEntrada = dateFormat.format(fechaEntradaVisual);
		}

		String fechaReparacion = null;
		java.util.Date fechaReparacionVisual = ventana.getFechaReparacion().getDate();
		if (fechaReparacionVisual != null) {
			fechaReparacion = dateFormat.format(fechaReparacionVisual);
		}

		String fechaAceptacion = null;
		java.util.Date fechaAceptacionVisual = ventana.getFechaRespuesta().getDate();
		if (fechaAceptacionVisual != null) {
			fechaAceptacion = dateFormat.format(fechaAceptacionVisual);
		}

		String fechaSalida = null;
		java.util.Date fechaSalidaVisual = ventana.getFechaSalida().getDate();
		if (fechaSalidaVisual != null) {
			fechaSalida = dateFormat.format(fechaSalidaVisual);
		}

		String fechaFabr = null;
		java.util.Date fechaFabrVisual = ventana.getFechaFabr().getDate();
		if (fechaFabrVisual != null) {
			fechaFabr = dateFormat.format(fechaFabrVisual);
		}

		// Obtener valores monetarios
		double presupuesto = parsearMoneda(ventana.getTextPresupuesto().getText());
		double presupuestoDolar = parsearMoneda(ventana.getTextPresupuestoDolar().getText());
		double pago = parsearMoneda(ventana.getTextPago().getText());

		// Obtener valores de combos con validación null
		String clienteCombo = ventana.getComboClientes().getSelectedItem() != null ? 
			ventana.getComboClientes().getSelectedItem().toString() : "";
		String sucursalCombo = ventana.getComboSucursal().getSelectedItem() != null ? 
			ventana.getComboSucursal().getSelectedItem().toString() : "";
		String tecnicoCombo = ventana.getComboTecnico().getSelectedItem() != null ? 
			ventana.getComboTecnico().getSelectedItem().toString() : "";
		
		ventana.getTextCliente().setText(clienteCombo);
		ventana.getTextSucursal().setText(sucursalCombo);
		ventana.getTextNombreTecnico().setText(tecnicoCombo);

		// Obtener IDs
		String cliente = ventana.getTextCliente().getText();
		String sucursal = ventana.getTextSucursal().getText();
		String nombreTecnico = ventana.getTextNombreTecnico().getText();

		int idCliente = agenda.idClienteporNombre(cliente);
		int idSucursal = agenda.idSucursalporNombre(sucursal, idCliente);
		// int idUsuario = agenda.idUsuarioporNombre(nombreTecnico);

		int idUsuario;
		int idUsuarioAux = IDUsuarioPorNombre(nombreTecnico);

		if (idUsuarioAux == 0) {

			idUsuario = 1;
		}

		else {
			idUsuario = idUsuarioAux;

		}
		// Estados
		String estadoFisico = ventana.getTextEstadoFisico().getText();
		String estadoTecnico = ventana.getTextEstadoTecnico().getText();
		String estadoComercial = ventana.getTextEstadoComercial().getText();
		String lugarIngreso = ventana.getTextLugarDeIngreso().getText();

		// Checkboxes
		boolean presupuestoGenerado = ventana.getChckPDFGenerado();
		boolean presupuestoEnviado = ventana.getChckPDFEnviado();
		boolean avisoEnviado = ventana.getChckbxAvisoEnviado();
		boolean wordGenerado = ventana.getChckWORDGenerado();
		boolean wordEnviado = ventana.getChckWORDEnviado();

		// OC
		String ordenCompra = ventana.getTextOC().getText();

		// Remito
		int idRemito = reparacionActual.getidRemito();
		boolean agregadoAremito = reparacionActual.getAgregadoaremito();
		boolean remitoGenerado = reparacionActual.getRemitoGenerado();

		
		return new ReparacionDTO(els, fechaEntrada, fechaReparacion, falla, solucion, informe, estadoFisico,
				estadoTecnico, estadoComercial, remito, reparacionActual.getIDEquipo(), idRemito, cliente, sucursal,
				fechaAceptacion, nombreEquipo, modelo, marca, serie, aviso, clienteCliente, idCliente, idSucursal,
				fechaFabr, idUsuario, nombreTecnico, presupuesto, presupuestoDolar, pago, presupuestoGenerado,
				avisoEnviado, presupuestoEnviado, wordGenerado, wordEnviado, ordenCompra, agregadoAremito,
				remitoGenerado, lugarIngreso, fechaSalida);
	}



	private int IDUsuarioPorNombre(String nombreTecnico) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Extrae datos de agregar equipo
	 */
	public ReparacionDTO extraerDatosAgregar(VentanaAgregarEquipo ventana, int idCliente, int idSucursal) {
		int els = Integer.parseInt(ventana.getTextELS());
		String falla = ventana.getTextFalla().getText();
		String remito = ventana.getTextRemitoCliente().getText();
		int idEquipo = agenda.dameIDequipo() + 1;
		String nombreEquipo = ventana.getComboNombreEquipo().getSelectedItem().toString();
		String modelo = ventana.getComboModelo().getSelectedItem().toString();
		String marca = ventana.getComboMarca().getSelectedItem().toString();
		String serie = ventana.getComboSerie().getSelectedItem().toString();
		String aviso = ventana.getTextAvisoCliente().getText();
		String clienteCliente = ventana.getTextClienteCliente().getText();

		// Validar caracteres
		if (verificarCaracteresPermitidos(nombreEquipo, falla, modelo, marca, serie, remito, aviso, clienteCliente)) {
			mostrarErrorCaracteresInvalidos(ventana);
			return null;
		}

		// Parsear fecha
		String fechaEntrada = null;
		java.util.Date fecha = ventana.getFechaEntrada().getDate();
		if (fecha != null) {
			fechaEntrada = dateFormat.format(fecha);
		}

		String fechaFabr = null;
		java.util.Date fechaFabrObj = ventana.getTextFechafabricacion().getDate();
		if (fechaFabrObj != null) {
			fechaFabr = dateFormat.format(fechaFabrObj);
		}

		// Estado físico (lugar de ingreso)
		String estadoFisico = obtenerEstadoFisico(ventana);
		String lugarDeIngreso = estadoFisico;

		String estadoTecnico = "Sin Revisar";
		String estadoComercial = "A la Espera de Aceptación";
		String cliente = ventana.getComboClientes().getSelectedItem().toString();
		String sucursal = ventana.getComboSucursal().getSelectedItem().toString();
		int idUsuario = 1;

		return new ReparacionDTO(els, fechaEntrada, falla, estadoFisico, estadoTecnico, estadoComercial, remito,
				idEquipo, idUsuario, nombreEquipo, modelo, marca, serie, aviso, clienteCliente, idCliente, idSucursal,
				fechaFabr, lugarDeIngreso);
	}

	/**
	 * Extrae datos para registro de ingreso
	 */
	public RegistroEntradaReporteDTO extraerRegistroIngreso(VentanaAgregarEquipo ventana, int idCliente,
			int idSucursal) {
		int els = Integer.parseInt(ventana.getTextELS());
		String falla = ventana.getTextFalla().getText();
		String remito = ventana.getTextRemitoCliente().getText();
		int idEquipo = agenda.dameIDequipo() + 1;
		String nombreEquipo = ventana.getComboNombreEquipo().getSelectedItem().toString();
		String modelo = ventana.getComboModelo().getSelectedItem().toString();
		String marca = ventana.getComboMarca().getSelectedItem().toString();
		String serie = ventana.getComboSerie().getSelectedItem().toString();
		String aviso = ventana.getTextAvisoCliente().getText();
		String clienteCliente = ventana.getTextClienteCliente().getText();
		String cliente = ventana.getComboClientes().getSelectedItem().toString();
		String sucursal = ventana.getComboSucursal().getSelectedItem().toString();

		String fechaEntrada = null;
		java.util.Date fecha = ventana.getFechaEntrada().getDate();
		if (fecha != null) {
			fechaEntrada = dateFormat.format(fecha);
		}

		String estadoFisico = obtenerEstadoFisico(ventana);
		String estadoTecnico = "Sin Revisar";

		return new RegistroEntradaReporteDTO(els, fechaEntrada, falla, estadoFisico, estadoTecnico, remito, idEquipo,
				nombreEquipo, modelo, marca, serie, aviso, clienteCliente, idCliente, idSucursal, cliente, sucursal);
	}

	/**
	 * Obtiene estado físico de radio buttons
	 */
	private String obtenerEstadoFisico(VentanaAgregarEquipo ventana) {
		Enumeration<?> elements = ventana.getGrupoEstadoFisico().getElements();
		while (elements.hasMoreElements()) {
			AbstractButton button = (AbstractButton) elements.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}
		return "";
	}

	public RegistroEntradaReporteDTO extraerRegistroIngreso(VentanaVisualizarEquipos ventana, int idCliente,
			int idSucursal) {
		int els = Integer.parseInt(ventana.getTextELS());
		String falla = ventana.getTextFalla().getText();
		String remito = ventana.getTextRemitoCliente().getText();
		int idEquipo = agenda.dameIDequipo() + 1;
		String nombreEquipo = ventana.getTextNombreEquipo().getText();
		String modelo = ventana.getTextModelo().getText();
		String marca = ventana.getTextMarca().getText();
		String serie = ventana.getTextNSerie().getText();
		String aviso = ventana.getTextAvisoCliente().getText();
		String clienteCliente = ventana.getTextClienteCliente().getText();
		String cliente = ventana.getTextCliente().getText();
		String sucursal = ventana.getTextSucursal().getText();

		String fechaEntrada = null;
		java.util.Date fecha = ventana.getFechaEntrada().getDate();
		if (fecha != null) {
			fechaEntrada = dateFormat.format(fecha);
		}

		String estadoFisico = "BS";
		String estadoTecnico = "Sin Revisar";

		return new RegistroEntradaReporteDTO(els, fechaEntrada, falla, estadoFisico, estadoTecnico, remito, idEquipo,
				nombreEquipo, modelo, marca, serie, aviso, clienteCliente, idCliente, idSucursal, cliente, sucursal);
	}

	/**
	 * Verifica caracteres no permitidos
	 * @return true si encuentra caracteres no válidos, false si todos son válidos
	 */
	private boolean verificarCaracteresPermitidos(String... textos) {
		caracteresNoValidos.clear();
		
		System.out.println("=== INICIANDO VERIFICACIÓN DE CARACTERES ===");

		for (int i = 0; i < textos.length; i++) {
			String texto = textos[i];
			
			// Saltar textos nulos o vacíos
			if (texto == null || texto.trim().isEmpty()) {
				System.out.println("Texto " + i + ": null o vacío - saltando");
				continue;
			}
			
			System.out.println("Verificando texto " + i + ": [" + texto + "]");

			// Verificar comilla simple
			if (texto.contains("'")) {
				System.out.println("  -> Encontrado: comilla simple (')");
				if (!caracteresNoValidos.contains("'")) {
					caracteresNoValidos.add("'");
				}
			}

			// Verificar punto y coma
			if (texto.contains(";")) {
				System.out.println("  -> Encontrado: punto y coma (;)");
				if (!caracteresNoValidos.contains(";")) {
					caracteresNoValidos.add(";");
				}
			}

			// Verificar barra invertida
			if (texto.contains("\\")) {
				System.out.println("  -> Encontrado: barra invertida (\\)");
				if (!caracteresNoValidos.contains("\\")) {
					caracteresNoValidos.add("\\");
				}
			}

			// Verificar comentario de bloque inicio
			if (texto.contains("/*")) {
				System.out.println("  -> Encontrado: comentario de bloque (/* )");
				if (!caracteresNoValidos.contains("/*")) {
					caracteresNoValidos.add("/*");
				}
			}

			// Verificar comentario de bloque fin
			if (texto.contains("*/")) {
				System.out.println("  -> Encontrado: comentario de bloque (*/)");
				if (!caracteresNoValidos.contains("*/")) {
					caracteresNoValidos.add("*/");
				}
			}
		}

		boolean hayCaracteresInvalidos = !caracteresNoValidos.isEmpty();
		
		if (hayCaracteresInvalidos) {
			System.out.println("=== CARACTERES NO VÁLIDOS ENCONTRADOS: " + caracteresNoValidos + " ===");
		} else {
			System.out.println("=== TODOS LOS CARACTERES SON VÁLIDOS ===");
		}
		
		return hayCaracteresInvalidos;
	}

	/**
	 * Muestra un popup con los caracteres inválidos detectados
	 */
	private void mostrarErrorCaracteresInvalidos(Object ventana) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("Se detectaron caracteres no válidos en los campos:\n\n");
		
		for (String caracter : caracteresNoValidos) {
			// Mostrar el caracter de forma visible
			String caracterMostrar = caracter;
			if (caracter.equals("\\")) {
				caracterMostrar = "\\ (barra invertida)";
			} else if (caracter.equals("'")) {
				caracterMostrar = "' (comilla simple)";
			} else if (caracter.equals(";")) {
				caracterMostrar = "; (punto y coma)";
			} else if (caracter.equals("/*")) {
				caracterMostrar = "/* (inicio de comentario)";
			} else if (caracter.equals("*/")) {
				caracterMostrar = "*/ (fin de comentario)";
			}
			
			mensaje.append("  • ").append(caracterMostrar).append("\n");
		}
		
		mensaje.append("\nPor favor, elimine estos caracteres antes de guardar.");
		
		JOptionPane.showMessageDialog(
			null,
			mensaje.toString(),
			"Error: Caracteres no válidos",
			JOptionPane.ERROR_MESSAGE
		);
	}

	/**
	 * Parsea valor monetario
	 */
	private double parsearMoneda(String valor) {
		try {
			if (valor == null || valor.isEmpty()) {
				return 0.0;
			}

			if (monedaFormatter.tieneFormato(valor)) {
				return monedaFormatter.parseAmountGuardar(valor);
			} else {
				return monedaFormatter.parseAmount(valor);
			}
		} catch (Exception ex) {
			return 0.0;
		}
	}

	/**
	 * Realiza búsqueda
	 */
	public void realizarBusqueda(VentanaBusquedaEquipo ventanaBusqueda) {
		String campo = ventanaBusqueda.getComboBuscador().getSelectedItem().toString();
		String texto = ventanaBusqueda.getTextField().getText();

		List<Integer> resultados = agenda.buscarEnCampos(campo, texto);
		// Procesar resultados en gestor de búsqueda

	}

	/**
	 * Getters
	 */
	public List<String> getCaracteresNoValidos() {
		return caracteresNoValidos;
	}
}