package VistaPropias;

import javax.swing.*;

import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorUsuarios;
import presentacion.vista.VentanaCodigoSeguridad;

import java.awt.event.*;

public class CodigoSeguridadHandler {

	private VentanaCodigoSeguridad ventanaCodigoSeguridad;
	
	
	private String nombreVentana;
	private boolean acceso;

	public CodigoSeguridadHandler(String nombreVentana) {
		this.nombreVentana = nombreVentana;
	}

	// Configura y muestra la ventana de código de seguridad
	public void mostrarVentana() {

		ventanaCodigoSeguridad = new VentanaCodigoSeguridad();
		ventanaCodigoSeguridad.getBtnAceptar().addActionListener(this::accionAceptar);
		ventanaCodigoSeguridad.getBtnCancelar().addActionListener(e -> cerrarVentana());

		ventanaCodigoSeguridad.getTxtCodigoSeguridad().addActionListener(e -> {
			char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();
			String codigo = new String(codigoIngresado);

			verificarCodigoSeguridad(codigo);
		});


	}

	// Verifica el código de seguridad
	protected boolean verificarCodigoSeguridad(String codigo) {
		if (codigo.compareTo("0000") == 0) {
			
			
			acceso=true;
			return true;
			

		} else {
			JOptionPane.showMessageDialog(null, "Código Incorrecto!", "Acceso denegado",
					JOptionPane.INFORMATION_MESSAGE);
			acceso=false;
			return false;
		}
	}
	
	public boolean acceso() {
		
		return acceso;
		
	}

	// Acciones al presionar el botón Aceptar
	private void accionAceptar(ActionEvent e) {
			
		
		char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();
		String codigo = new String(codigoIngresado);

		verificarCodigoSeguridad(codigo);
				
		cerrarVentana();

	}

	// Cierra la ventana de código de seguridad
	private void cerrarVentana() {
		if (ventanaCodigoSeguridad != null) {
			ventanaCodigoSeguridad.dispose();
		}
	}
}
