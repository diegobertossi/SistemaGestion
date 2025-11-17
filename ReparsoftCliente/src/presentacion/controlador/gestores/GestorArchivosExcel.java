package presentacion.controlador.gestores;

import java.awt.Desktop;
import java.awt.Window;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Clase para gestionar la apertura de archivos Excel con manejo de contraseñas,
 * vínculos y dependencias
 */
public class GestorArchivosExcel {
	
	

	private String ubicacionBase;// "Bariloche" o "Buenos Aires"

	/**
	 * Constructor
	 * 
	 * @param ubicacionBase "Bariloche" o "Buenos Aires"
	 */
	public GestorArchivosExcel(String ubicacionBase) {
		this.ubicacionBase = ubicacionBase;
		
	}

	
	/**
	 * Muestra un JOptionPane que siempre queda en primer plano
	 */
	private int mostrarMensajeAlFrente(String mensaje, String titulo, int tipoMensaje) {
		JOptionPane pane = new JOptionPane(mensaje, tipoMensaje);
		JDialog dialog = pane.createDialog(null, titulo);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(true);
		dialog.setVisible(true);

		Object valor = pane.getValue();
		if (valor == null || valor.equals(JOptionPane.UNINITIALIZED_VALUE)) {
			return JOptionPane.CLOSED_OPTION;
		}
		return ((Integer) valor).intValue();
	}

	/**
	 * Muestra un JOptionPane de confirmación que siempre queda en primer plano
	 */
	private int mostrarConfirmacionAlFrente(String mensaje, String titulo, int tipoOpcion, int tipoMensaje) {
		JOptionPane pane = new JOptionPane(mensaje, tipoMensaje, tipoOpcion);
		JDialog dialog = pane.createDialog(null, titulo);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(true);
		dialog.setVisible(true);

		Object valor = pane.getValue();
		if (valor == null || valor.equals(JOptionPane.UNINITIALIZED_VALUE)) {
			return JOptionPane.CLOSED_OPTION;
		}
		return ((Integer) valor).intValue();
	}

	/**
	 * Muestra un JOptionPane con opciones personalizadas que siempre queda en
	 * primer plano
	 */
	private int mostrarOpcionesAlFrente(String mensaje, String titulo, int tipoOpcion, int tipoMensaje,
			Object[] opciones, Object valorPorDefecto) {
		JOptionPane pane = new JOptionPane(mensaje, tipoMensaje, tipoOpcion, null, opciones, valorPorDefecto);
		JDialog dialog = pane.createDialog(null, titulo);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(true);
		dialog.setVisible(true);

		Object valor = pane.getValue();
		if (valor == null || valor.equals(JOptionPane.UNINITIALIZED_VALUE)) {
			return JOptionPane.CLOSED_OPTION;
		}

		// Buscar el índice de la opción seleccionada
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i].equals(valor)) {
				return i;
			}
		}
		return JOptionPane.CLOSED_OPTION;
	}

	
	//verificar funcionamiento
	/**
	 * Muestra un JOptionPane de input que siempre queda en primer plano
	 */
	private Object mostrarInputAlFrente(String mensaje, String titulo, int tipoMensaje, Object[] opciones,
			Object valorPorDefecto) {
		JOptionPane pane = new JOptionPane(mensaje, tipoMensaje, JOptionPane.OK_CANCEL_OPTION, null, null, null);
		pane.setWantsInput(true);
		pane.setSelectionValues(opciones);
		pane.setInitialSelectionValue(valorPorDefecto);

		JDialog dialog = pane.createDialog(null, titulo);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(true);
		dialog.setVisible(true);

		return pane.getInputValue();
	}

	/**
	 * Abre el archivo Excel de Reparaciones
	 */
	public void abrirReparaciones() {
		String rutaArchivo = construirRutaReparaciones();
		abrirArchivoExcel(rutaArchivo);
	
	}

	/**
	 * Abre el archivo Excel de Caja Ofrece actualizar primero ReparBRC_Mysql porque
	 * Caja depende de él
	 */
	public void abrirCaja() {
		String rutaCaja = construirRutaCaja();
		String rutaReparaciones = construirRutaReparaciones();

		// Preguntar si quiere actualizar primero
		Object[] opciones = { "Actualizar primero", "Abrir directamente", "Cancelar" };
		int seleccion = mostrarOpcionesAlFrente(
				"El archivo Caja BRC tiene vínculos a ReparBRC_Mysql.\n\n"
						+ "• Actualizar primero: Abre ReparBRC_Mysql para actualizar datos\n"
						+ "  (recomendado para tener información actualizada)\n\n"
						+ "• Abrir directamente: Abre solo Caja BRC\n" + "  (los datos pueden no estar actualizados)",
				"Abrir Caja BRC", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, opciones,
				opciones[0]);

		if (seleccion == 2 || seleccion == JOptionPane.CLOSED_OPTION) {
			return; // Cancelar
		}

		if (seleccion == 0) {
			// Actualizar primero
			abrirArchivoExcel(rutaReparaciones);
			mostrarMensajeAlFrente("Se ha abierto ReparBRC_Mysql.\n\n" + "Por favor:\n" + "1. Ingrese la contraseña\n"
					+ "2. Espere a que se actualice\n" + "3. Cierre el archivo (guarde si es necesario)\n"
					+ "4. Presione OK para abrir Caja BRC", "Instrucciones", JOptionPane.INFORMATION_MESSAGE);
		}

		// Abrir Caja BRC
		abrirArchivoExcel(rutaCaja);
	}

	/**
	 * Abre el archivo de Detalle de Gastos del año especificado
	 * 
	 * @param anio Año del archivo a abrir (null para mostrar selector)
	 */
	public void abrirDetalleGastos(Integer anio) {
		Integer anioSeleccionado = anio;

		// Si no se especificó año, mostrar selector
		if (anioSeleccionado == null) {
			anioSeleccionado = mostrarSelectorAnio();
			if (anioSeleccionado == null) {
				return; // Usuario canceló
			}
		}

		String nombreArchivo = "Detalle gastos " + anioSeleccionado;
		String rutaArchivo = construirRutaBase() + nombreArchivo;

		abrirArchivoExcel(rutaArchivo);
	}

	/**
	 * Abre el archivo de Detalle de Gastos del año actual
	 */
	public void abrirDetalleGastosAnioActual() {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		abrirDetalleGastos(anioActual);
	}

	/**
	 * Muestra un selector de años y retorna el año seleccionado
	 */
	private Integer mostrarSelectorAnio() {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);

		// Crear lista de años (actual y 4 años anteriores)
		Integer[] anios = new Integer[5];
		for (int i = 0; i < 5; i++) {
			anios[i] = anioActual - i;
		}

		Object resultado = mostrarInputAlFrente("Seleccione el año:", "Detalle de Gastos", JOptionPane.QUESTION_MESSAGE,
				anios, anioActual);

		if (resultado == null || resultado.equals(JOptionPane.UNINITIALIZED_VALUE)) {
			return null;
		}

		return (Integer) resultado;
	}

	/**
	 * Construye la ruta base según la ubicación
	 */
	private String construirRutaBase() {
		if (ubicacionBase.equals("Bariloche")) {
			return "F:\\els\\Bariloche\\Administracion\\Sistema\\Excels\\";
		} else if (ubicacionBase.equals("Buenos Aires")) {
			return "F:\\els\\Administracion\\Sistema\\Excels\\";
		}
		return "";
	}

	/**
	 * Construye la ruta del archivo de Reparaciones
	 */
	private String construirRutaReparaciones() {

		if (ubicacionBase.equals("Buenos Aires")) {
			
			return construirRutaBase() + "ReparBSAS_Mysql";
			
		} else if(ubicacionBase.equals("Bariloche")) {
			
			return construirRutaBase() + "ReparBRC_Mysql";
		}
		return "";
	}

	/**
	 * Construye la ruta del archivo de Caja
	 */
	private String construirRutaCaja() {
		return construirRutaBase() + "Caja BRC";
	}

	/**
	 * Abre un archivo Excel buscando automáticamente la extensión correcta
	 * 
	 * @param rutaBase Ruta del archivo sin extensión
	 */
	private void abrirArchivoExcel(String rutaBase) {
		try {
			File archivo = buscarArchivoConExtension(rutaBase);

			if (archivo == null || !archivo.exists()) {
				mostrarMensajeAlFrente(
						"El archivo no existe en: " + rutaBase + "\n"
								+ "Verifique que el archivo exista con extensión .xlsx, .xls o .xlsm",
						"Error - Archivo no encontrado", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Abrir con la aplicación predeterminada (Excel)
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(archivo);
			} else {
				mostrarMensajeAlFrente(
						"No se puede abrir el archivo en este sistema.\n"
								+ "El sistema no soporta la apertura automática de archivos.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IOException e) {
			mostrarMensajeAlFrente(
					"Error al abrir el archivo: " + e.getMessage() + "\n\n" + "Verifique que:\n"
							+ "- El archivo no esté abierto en otro programa\n"
							+ "- Tenga permisos para acceder al archivo\n" + "- La ruta sea correcta",
					"Error de E/S", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			mostrarMensajeAlFrente("Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Busca un archivo con diferentes extensiones de Excel
	 * 
	 * @param rutaBase Ruta sin extensión
	 * @return File encontrado o null
	 */
	private File buscarArchivoConExtension(String rutaBase) {
		// Extensiones a probar en orden de preferencia
		String[] extensiones = { ".xlsx", ".xlsm", ".xls" };

		for (String ext : extensiones) {
			File archivo = new File(rutaBase + ext);
			if (archivo.exists()) {
				return archivo;
			}
		}

		// Intentar sin extensión (por si ya la tiene)
		File archivo = new File(rutaBase);
		if (archivo.exists()) {
			return archivo;
		}

		return null;
	}

	/**
	 * Cambia la ubicación base
	 * 
	 * @param nuevaUbicacion "Bariloche" o "Buenos Aires"
	 */
	public void setUbicacionBase(String nuevaUbicacion) {
		this.ubicacionBase = nuevaUbicacion;
	}

	/**
	 * Obtiene la ubicación base actual
	 */
	public String getUbicacionBase() {
		return ubicacionBase;
	}

	/**
	 * Abre todos los archivos Excel en secuencia Orden: ReparBRC_Mysql -> Caja BRC
	 * -> Detalle Gastos Este orden asegura que los vínculos estén actualizados
	 */
	public void abrirTodosLosArchivos() {
		// Confirmar que quiere abrir todos
		int confirmacion = mostrarConfirmacionAlFrente(
				"Se abrirán los siguientes archivos en orden:\n\n" + "1. ReparBRC_Mysql (con contraseña)\n"
						+ "2. Caja BRC (con contraseña - depende de Reparaciones)\n"
						+ "3. Detalle Gastos (año actual)\n\n"
						+ "Deberá ingresar las contraseñas cuando se soliciten.\n" + "¿Desea continuar?",
				"Abrir todos los archivos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirmacion != JOptionPane.YES_OPTION) {
			return;
		}

		// Paso 1: Abrir ReparBRC_Mysql
		String rutaReparaciones = construirRutaReparaciones();
		abrirArchivoExcel(rutaReparaciones);

		int continuar = mostrarConfirmacionAlFrente(
				"✓ Se ha abierto ReparBRC_Mysql\n\n" + "Por favor:\n" + "1. Ingrese la contraseña\n"
						+ "2. Espere a que cargue completamente\n\n"
						+ "Presione OK cuando esté listo para abrir Caja BRC",
				"Paso 1 de 3 - ReparBRC_Mysql", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

		if (continuar != JOptionPane.OK_OPTION) {
			return;
		}

		// Paso 2: Abrir Caja BRC
		String rutaCaja = construirRutaCaja();
		abrirArchivoExcel(rutaCaja);

		continuar = mostrarConfirmacionAlFrente(
				"✓ Se ha abierto Caja BRC\n\n" + "Por favor:\n" + "1. Ingrese la contraseña\n"
						+ "2. Actualice los vínculos si se solicita\n" + "3. Espere a que cargue completamente\n\n"
						+ "Presione OK cuando esté listo para abrir Detalle Gastos",
				"Paso 2 de 3 - Caja BRC", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

		if (continuar != JOptionPane.OK_OPTION) {
			return;
		}

		// Paso 3: Abrir Detalle Gastos (año actual)
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);

		// Preguntar si quiere el año actual u otro
		Object[] opciones = { "Año actual (" + anioActual + ")", "Elegir año", "Omitir" };
		int seleccion = mostrarOpcionesAlFrente("Seleccione qué archivo de Detalle Gastos abrir:",
				"Paso 3 de 3 - Detalle Gastos", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				opciones, opciones[0]);

		if (seleccion == 0) {
			// Abrir año actual
			abrirDetalleGastos(anioActual);
		} else if (seleccion == 1) {
			// Elegir año
			abrirDetalleGastos(null);
		}

		// Mensaje final
		mostrarMensajeAlFrente("✓ Proceso completado\n\n" + "Todos los archivos han sido abiertos.", "Finalizado",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Abre todos los archivos Excel de forma automática con pausas Esta versión
	 * abre los archivos con delays automáticos
	 */
	public void abrirTodosLosArchivosAutomatico() {
		// Confirmar que quiere abrir todos
		int confirmacion = mostrarConfirmacionAlFrente(
				"MODO AUTOMÁTICO\n\n" + "Se abrirán automáticamente:\n" + "1. ReparBRC_Mysql\n"
						+ "2. Caja BRC (después de 5 segundos)\n"
						+ "3. Detalle Gastos (después de otros 5 segundos)\n\n"
						+ "Deberá ingresar las contraseñas cuando se soliciten.\n" + "¿Desea continuar?",
				"Abrir todos los archivos - Automático", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (confirmacion != JOptionPane.YES_OPTION) {
			return;
		}

		// Crear un thread para manejar las pausas
		Thread hiloApertura = new Thread(() -> {
			try {
				// Paso 1: Abrir ReparBRC_Mysql
				String rutaReparaciones = construirRutaReparaciones();
				abrirArchivoExcel(rutaReparaciones);

				mostrarMensajeEnHilo("Abriendo ReparBRC_Mysql...\nEspere 5 segundos...", 1);
				Thread.sleep(5000);

				// Paso 2: Abrir Caja BRC
				String rutaCaja = construirRutaCaja();
				abrirArchivoExcel(rutaCaja);

				mostrarMensajeEnHilo("Abriendo Caja BRC...\nEspere 5 segundos...", 2);
				Thread.sleep(5000);

				// Paso 3: Preguntar por Detalle Gastos
				int anioActual = Calendar.getInstance().get(Calendar.YEAR);

				Object anioSeleccionado = mostrarInputAlFrente("Seleccione el año de Detalle Gastos:",
						"Paso 3 de 3 - Detalle Gastos", JOptionPane.QUESTION_MESSAGE, crearArrayAnios(5), anioActual);

				if (anioSeleccionado != null && !anioSeleccionado.equals(JOptionPane.UNINITIALIZED_VALUE)) {
					abrirDetalleGastos((Integer) anioSeleccionado);
				}

				// Mensaje final
				mostrarMensajeAlFrente("✓ Proceso completado\n\n" + "Todos los archivos han sido abiertos.",
						"Finalizado", JOptionPane.INFORMATION_MESSAGE);

			} catch (InterruptedException e) {
				mostrarMensajeAlFrente("Proceso interrumpido", "Cancelado", JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
		});

		hiloApertura.start();
	}

	/**
	 * Muestra un mensaje en un hilo separado con JDialog siempre visible
	 */
	private void mostrarMensajeEnHilo(String mensaje, int paso) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			mostrarMensajeAlFrente(mensaje, "Paso " + paso + " de 3", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	/**
	 * Crea un array de años para el selector
	 */
	private Integer[] crearArrayAnios(int cantidad) {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		Integer[] anios = new Integer[cantidad];
		for (int i = 0; i < cantidad; i++) {
			anios[i] = anioActual - i;
		}
		return anios;
	}
}