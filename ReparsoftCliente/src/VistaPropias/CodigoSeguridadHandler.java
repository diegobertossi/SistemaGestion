package VistaPropias;

import javax.swing.*;

import presentacion.vista.VentanaCodigoSeguridad;

import java.awt.event.*;

public class CodigoSeguridadHandler {

	private VentanaCodigoSeguridad ventanaCodigoSeguridad;
	private String seleccionDetalleEstadisticas;
	private boolean acceso;

	public CodigoSeguridadHandler(String seleccionDetalleEstadisticas) {
		this.seleccionDetalleEstadisticas = seleccionDetalleEstadisticas;
	}

	// Configura y muestra la ventana de código de seguridad
	public void mostrarVentana() {

		ventanaCodigoSeguridad = new VentanaCodigoSeguridad();
		ventanaCodigoSeguridad.getBtnAceptar().addActionListener(this::accionAceptar);
		ventanaCodigoSeguridad.getBtnCancelar().addActionListener(e -> cerrarVentana());

		ventanaCodigoSeguridad.getTxtCodigoSeguridad().addActionListener(e -> {
			char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();
			String codigo = new String(codigoIngresado);

			if (verificarCodigoSeguridad(codigo)) {
				acceso = true;
				//System.out.println("entro");
				habitar();
			} else {
				acceso = false;
			}
		});

		ventanaCodigoSeguridad.setVisible(true);
	}

	// Verifica el código de seguridad
	private boolean verificarCodigoSeguridad(String codigo) {
		if (codigo.equals("0000")) {
			ventanaCodigoSeguridad.getPanelCodigo().setVisible(false);
			ventanaCodigoSeguridad.getPanelDetalle().setVisible(true);

			if ("MOSTRAR DETALLE".equals(seleccionDetalleEstadisticas)) {
				ventanaCodigoSeguridad.getRdbtnMostrar().setSelected(true);
			} else {
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

	// Habilita los detalles tras la verificación exitosa
	public boolean habitar() {
		
//		System.out.println("Entro  " + acceso);
		return acceso;
		
	}

	// Acciones al presionar el botón Aceptar
	private void accionAceptar(ActionEvent e) {
		char[] codigoIngresado = ventanaCodigoSeguridad.getTxtCodigoSeguridad().getPassword();
		String codigo = new String(codigoIngresado);

		if (verificarCodigoSeguridad(codigo)) {
			acceso = true;
		} else {
			acceso = false;
		}
	}

	// Cierra la ventana de código de seguridad
	private void cerrarVentana() {
		if (ventanaCodigoSeguridad != null) {
			ventanaCodigoSeguridad.dispose();
		}
	}
}
