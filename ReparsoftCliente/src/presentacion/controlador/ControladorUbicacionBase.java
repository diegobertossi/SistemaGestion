package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import presentacion.vista.VistaPrincipal;

import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class ControladorUbicacionBase implements ActionListener {

	private VentanaUbicacionBaseDeDatos vistaUbicacionBase;
	private String UbicacionBase;

	public ControladorUbicacionBase(VentanaUbicacionBaseDeDatos vistaUbicacionBaseDatos) {

		this.vistaUbicacionBase = vistaUbicacionBaseDatos;

		this.vistaUbicacionBase.getBtnBuenosAires().addActionListener(this);
		this.vistaUbicacionBase.getBtnBariloche().addActionListener(this);

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if (arg0.getSource() == vistaUbicacionBase.getBtnBariloche()) {

			UbicacionBase = "Bariloche";
			vistaUbicacionBase.dispose();
			vistaUbicacionBase = null;

		}

		else if (arg0.getSource() == vistaUbicacionBase.getBtnBuenosAires()) {

			UbicacionBase = "Buenos Aires";
			vistaUbicacionBase.dispose();
			vistaUbicacionBase = null;

		}

		VistaPrincipal vista = new VistaPrincipal();

		ControladorPrincipal controlador = new ControladorPrincipal(vista, UbicacionBase);

		controlador.inicializar();

	}

}
