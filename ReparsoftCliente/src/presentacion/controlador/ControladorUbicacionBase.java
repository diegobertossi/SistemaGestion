package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import presentacion.vista.VistaPrincipal;

import presentacion.vista.VentanaUbicacionBaseDeDatos;

public class ControladorUbicacionBase implements ActionListener {

	private VentanaUbicacionBaseDeDatos vistaUbicacionBase;
	private String UbicacionBase;

	public ControladorUbicacionBase(VentanaUbicacionBaseDeDatos vistaUbicacionBaseDatos) {

		this.vistaUbicacionBase = vistaUbicacionBaseDatos;
		this.vistaUbicacionBase.getBtnAcceder().addActionListener(this);
		this.vistaUbicacionBase.getComboUbicacion().addActionListener(this);
		llenarComboUbicacionBase();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == vistaUbicacionBase.getBtnAcceder()) {
						
			if (vistaUbicacionBase.getComboUbicacion().getSelectedItem()!= null) {
				
				
				UbicacionBase = vistaUbicacionBase.getComboUbicacion().getSelectedItem().toString();
				vistaUbicacionBase.dispose();
				vistaUbicacionBase = null;

				VistaPrincipal vista = new VistaPrincipal();

				ControladorPrincipal controlador = new ControladorPrincipal(vista, UbicacionBase);

				controlador.inicializar();
				
				
			}
			else
				
				JOptionPane.showMessageDialog(null, "Debe seleccionar una UBICACIÓN para acceder",
						"SELECCIONAR UBICACIÓN", JOptionPane.INFORMATION_MESSAGE);


		}

	}

	@SuppressWarnings("unchecked")
	private void llenarComboUbicacionBase() {

		vistaUbicacionBase.getComboUbicacion().addItem("Buenos Aires");
		vistaUbicacionBase.getComboUbicacion().addItem("Bariloche");
		vistaUbicacionBase.getComboUbicacion().setSelectedIndex(-1);

	}

}
