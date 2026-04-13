package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import persistencia.conexion.Conexion;
import presentacion.vista.VistaPrincipal;
import presentacion.vista.VentanaConfiguracion;
import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class ControladorConfiguraciones implements ActionListener {

	private VentanaConfiguracion vistaConfiguracion;
	private ControladorUsuLogin controladorUsuLogin;
	private VistaPrincipal vistaPrincipal;

	public ControladorConfiguraciones(VentanaConfiguracion vistaConfiguracion, 
	                                  ControladorUsuLogin controladorUsuLogin, 
	                                  VistaPrincipal vistaPrincipal) {

		this.vistaConfiguracion = vistaConfiguracion;
		this.controladorUsuLogin = controladorUsuLogin;
		this.vistaPrincipal = vistaPrincipal;
		
		// Agregar listeners para los tres botones
		this.vistaConfiguracion.getBtnUbicacionSistema().addActionListener(this);
		this.vistaConfiguracion.getBtnEquiposAntiguos().addActionListener(this);
		this.vistaConfiguracion.getBtnVolverBaseNormal().addActionListener(this);  // ← NUEVO
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		// ====================== BOTÓN CAMBIAR UBICACIÓN ======================
		if (arg0.getSource() == vistaConfiguracion.getBtnUbicacionSistema()) {
						
			int opcion = JOptionPane.showConfirmDialog(vistaConfiguracion, 
					"¿Desea cambiar la ubicación del sistema?", 
					"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				this.controladorUsuLogin.cerrarSesion();
				if (vistaPrincipal != null) vistaPrincipal.dispose();
				if (vistaConfiguracion != null) vistaConfiguracion.dispose();
				
				VentanaUbicacionBaseDeDatos ventanaUbicacionBaseDeDatos = new VentanaUbicacionBaseDeDatos();
				new ControladorUbicacionBase(ventanaUbicacionBaseDeDatos);
			}
		}
		
		// ====================== BOTÓN EQUIPOS ANTIGUOS ======================
		else if (arg0.getSource() == vistaConfiguracion.getBtnEquiposAntiguos()) {
			
			String ubicacionActual = Conexion.getUbicacionActualStatic();
			if (ubicacionActual == null || ubicacionActual.isEmpty()) {
				ubicacionActual = "Bariloche"; // fallback
			}

			String nombreBaseAntigua = ubicacionActual.equalsIgnoreCase("Bariloche") 
					? "ordenesbrcantiguas" : "ordenesbsascantiguas";

			int opcion = JOptionPane.showConfirmDialog(vistaConfiguracion, 
					"¿Desea cambiar a la base de datos de **Equipos Antiguos**?\n\n" +
					"Ubicación: " + ubicacionActual + "\n" +
					"Base de datos: " + nombreBaseAntigua + "\n\n" +
					"Esta acción NO cerrará ninguna ventana.",
					"Cambio a Base Antigua", 
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				
				Conexion.getConexion(ubicacionActual, true);   // activa modo antigua global

				JOptionPane.showMessageDialog(vistaConfiguracion, 
					"✅ Base de datos cambiada correctamente a:\n" + nombreBaseAntigua + "\n\n" +
					"Todas las ventanas y listados ahora usarán la base antigua.",
					"Cambio realizado", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		// ====================== BOTÓN VOLVER A BASE NORMAL (NUEVO) ======================
		else if (arg0.getSource() == vistaConfiguracion.getBtnVolverBaseNormal()) {
			
			String ubicacionActual = Conexion.getUbicacionActualStatic();
			if (ubicacionActual == null || ubicacionActual.isEmpty()) {
				ubicacionActual = "Bariloche"; // fallback
			}

			String nombreBaseNormal = ubicacionActual.equalsIgnoreCase("Bariloche") 
					? "ordenesbrc" : "ordenesbsas";

			int opcion = JOptionPane.showConfirmDialog(vistaConfiguracion, 
					"¿Desea volver a la base de datos **Normal**?\n\n" +
					"Ubicación: " + ubicacionActual + "\n" +
					"Base de datos: " + nombreBaseNormal + "\n\n" +
					"Esta acción NO cerrará ninguna ventana.",
					"Volver a Base Normal", 
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (opcion == JOptionPane.YES_OPTION) {
				
				Conexion.getConexion(ubicacionActual, false);   // activa modo normal global

				JOptionPane.showMessageDialog(vistaConfiguracion, 
					"✅ Base de datos cambiada correctamente a:\n" + nombreBaseNormal + "\n\n" +
					"Todas las ventanas y listados ahora usarán la base normal.",
					"Cambio realizado", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}