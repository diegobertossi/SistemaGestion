package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import presentacion.vista.VistaPrincipal;
import presentacion.vista.VentanaConfiguracion;
import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class ControladorConfiguraciones implements ActionListener {

	private VentanaConfiguracion vistaConfiguracion;
	private String UbicacionBase;
	private ControladorUsuLogin controladorUsuLogin;
	private VistaPrincipal vistaPrincipal;

	public ControladorConfiguraciones(VentanaConfiguracion vistaConfiguracion,ControladorUsuLogin controladorUsuLogin,VistaPrincipal vistaPrincipal ) {

		this.vistaConfiguracion = vistaConfiguracion;
		this.controladorUsuLogin = controladorUsuLogin;
		this.vistaPrincipal = vistaPrincipal;
		this.vistaConfiguracion.getBtnUbicacionSistema().addActionListener(this);
		
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == vistaConfiguracion.getBtnUbicacionSistema()) {
						
		
				int opcion = JOptionPane.showConfirmDialog(vistaConfiguracion, "¿Desea cambiar la ubicación del sistema?", "Aviso",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (opcion == JOptionPane.YES_OPTION) {
					this.controladorUsuLogin.cerrarSesion();
					vistaPrincipal.dispose();
					vistaPrincipal = null;
				
					VentanaUbicacionBaseDeDatos ventanaUbicacionBaseDeDatos = new VentanaUbicacionBaseDeDatos();
					ControladorUbicacionBase controlador = new ControladorUbicacionBase(ventanaUbicacionBaseDeDatos);

			}
			


		}

	}



}
