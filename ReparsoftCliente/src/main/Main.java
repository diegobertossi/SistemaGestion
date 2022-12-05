package main;

import javax.swing.UIManager;


import presentacion.controlador.ControladorPrincipal;
import presentacion.vista.VistaPrincipal;

public class Main {

	public static void main(String[] args) {
		try {
			// Correcion hecha por Chuster Boy ;)

			// UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			//**UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			//UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel");
			//com.jtattoo.plaf.mcwin.McWinLookAndFeel	
						
//			UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
//            Synthetica a = new Synthetica(); 
//            a.setVisible(true);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		VistaPrincipal vista = new VistaPrincipal();

		ControladorPrincipal controlador = new ControladorPrincipal(vista);
		controlador.inicializar();

	}
}
