package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import persistencia.conexion.Conexion;
import presentacion.vista.VistaPrincipal;
import vista.migracion.MigracionController;
import vista.migracion.VentanaMigracion;
import presentacion.vista.VentanaConfiguracion;
import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class ControladorConfiguraciones implements ActionListener {

	private VentanaConfiguracion vistaConfiguracion;
	private MigracionController controladormigracion;
	private ControladorUsuLogin controladorUsuLogin;
	private VistaPrincipal vistaPrincipal;
	private vista.migracion.VentanaMigracion ventanaMigracion;

	public ControladorConfiguraciones(VentanaConfiguracion vistaConfiguracion, ControladorUsuLogin controladorUsuLogin,
			VistaPrincipal vistaPrincipal) {

		this.vistaConfiguracion = vistaConfiguracion;
		this.controladorUsuLogin = controladorUsuLogin;
		this.vistaPrincipal = vistaPrincipal;

		// Agregar listeners
		this.vistaConfiguracion.getBtnUbicacionSistema().addActionListener(this);
		this.vistaConfiguracion.getBtnEquiposAntiguos().addActionListener(this);
		this.vistaConfiguracion.getBtnVolverBaseNormal().addActionListener(this);
		this.vistaConfiguracion.getBtnMigracion().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		// ====================== BOTÓN CAMBIAR UBICACIÓN ======================
		if (arg0.getSource() == vistaConfiguracion.getBtnUbicacionSistema()) {

			int opcion = JOptionPane.showConfirmDialog(vistaConfiguracion, "¿Desea cambiar la ubicación del sistema?",
					"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				this.controladorUsuLogin.cerrarSesion();
				if (vistaPrincipal != null)
					vistaPrincipal.dispose();
				if (vistaConfiguracion != null)
					vistaConfiguracion.dispose();

				VentanaUbicacionBaseDeDatos ventanaUbicacionBaseDeDatos = new VentanaUbicacionBaseDeDatos();
				new ControladorUbicacionBase(ventanaUbicacionBaseDeDatos);
			}
		}

		// ====================== BOTÓN EQUIPOS ANTIGUOS ======================
		else if (arg0.getSource() == vistaConfiguracion.getBtnEquiposAntiguos()) {

			String ubicacion = obtenerUbicacionSegura();
			String nombreBaseAntigua = ubicacion.equalsIgnoreCase("Bariloche") ? "ordenesbrcantiguas"
					: "ordenesbsasantiguas";

			int opcion = JOptionPane.showConfirmDialog(vistaConfiguracion, "¿Desea ver los equipos antiguos?\n\n",
					"Cambio a Base Antigua", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {

				Conexion.getConexion(ubicacion, true);
				actualizarEtiquetaPrincipal(ubicacion, "ANTIGUA");
				// cerrar ventana de configuración para evitar confusión
				if (vistaConfiguracion != null)
					vistaConfiguracion.dispose();

			}
		}

		// ====================== BOTÓN VOLVER A BASE NORMAL ======================
		else if (arg0.getSource() == vistaConfiguracion.getBtnVolverBaseNormal()) {

			String ubicacion = obtenerUbicacionSegura();
			String nombreBaseNormal = ubicacion.equalsIgnoreCase("Bariloche") ? "ordenesbrc" : "ordenesbsas";

			int opcion = JOptionPane.showConfirmDialog(vistaConfiguracion,
					"¿Desea volver a ver los equipos actuales?\n\n", "Volver a Base Actual", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {

				Conexion.getConexion(ubicacion, false);
				actualizarEtiquetaPrincipal(ubicacion, "ACTUAL");
				// cerrar ventana de configuración para evitar confusión
				if (vistaConfiguracion != null)
					vistaConfiguracion.dispose();
			}
		}

		// ====================== BOTÓN MIGRACIÓN ACCESS -> MYSQL ======================
		else if (arg0.getSource() == vistaConfiguracion.getBtnMigracion()) {

			VentanaMigracion v = new VentanaMigracion(controladormigracion);

		}

	}

	/**
	 * Obtiene la ubicación de forma segura evitando el fallback a "Bariloche"
	 * cuando la instancia de Conexion aún no está completamente inicializada.
	 */
	private String obtenerUbicacionSegura() {
		String ubicacion = Conexion.getUbicacionActualStatic();

		if (ubicacion == null || ubicacion.trim().isEmpty()) {
			// Último recurso: intentar recuperar desde la vista principal (si ya tenía
			// texto)
			String textoActual = vistaPrincipal != null && vistaPrincipal.getTextLugarBaseDatos() != null
					? vistaPrincipal.getTextLugarBaseDatos().getText()
					: "";

			if (textoActual.contains("BUENOS AIRES") || textoActual.contains("Buenos Aires")) {
				return "Buenos Aires";
			}
			if (textoActual.contains("BARILOCHE") || textoActual.contains("Bariloche")) {
				return "Bariloche";
			}

			// Si tampoco hay nada útil → fallback real (solo la primera vez)
			return "Bariloche";
		}

		return ubicacion.trim();
	}

	/**
	 * Actualiza la etiqueta en VistaPrincipal de forma centralizada
	 */
	private void actualizarEtiquetaPrincipal(String ubicacion, String modo) {
		if (vistaPrincipal != null && vistaPrincipal.getTextLugarBaseDatos() != null) {
			String textoFinal = ubicacion.toUpperCase() + " - " + modo;
			vistaPrincipal.getTextLugarBaseDatos().setText(textoFinal);

			// Forzar refresco visual en Swing
			vistaPrincipal.getTextLugarBaseDatos().repaint();
			if (vistaPrincipal.getTextLugarBaseDatos().getParent() != null) {
				vistaPrincipal.getTextLugarBaseDatos().getParent().revalidate();
			}
		}
	}
}