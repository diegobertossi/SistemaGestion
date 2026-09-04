package presentacion.reportes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;

import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Customiza el visor interno de JasperReports usado por el sistema:
 * titulo e icono propios (identificables en Alt+Tab), zoom inicial a
 * "pagina completa" y sin el boton Guardar del toolbar (el PDF ya lo
 * guarda la aplicacion en su carpeta; ese boton permite guardar formatos
 * ajenos al sistema en cualquier carpeta).
 */
public final class VisorReportes {

	private VisorReportes() {
	}

	public static void configurar(JasperViewer viewer, String titulo) {
		viewer.setTitle(titulo);
		Image icon = Toolkit.getDefaultToolkit().getImage(VisorReportes.class.getResource("/Iconosoft.png"));
		viewer.setIconImage(icon);
		ocultarBotonGuardar(viewer);
		// El zoom "pagina completa" solo puede calcularse cuando la ventana ya
		// esta visible y diagramada; por eso se aplica al abrirse la ventana,
		// simulando el click en el toggle del toolbar para que el combo de zoom
		// y el estado del boton queden consistentes.
		viewer.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				ajustarPaginaCompleta(viewer);
			}
		});
	}

	private static void ajustarPaginaCompleta(JasperViewer viewer) {
		try {
			JRViewerToolbar toolbar = buscarComponente(viewer.getContentPane(), JRViewerToolbar.class);
			if (toolbar != null) {
				javax.swing.JToggleButton segundoToggle = null;
				int cont = 0;
				for (Component c : toolbar.getComponents()) {
					if (c instanceof javax.swing.JToggleButton) {
						cont++;
						String tip = ((javax.swing.JToggleButton) c).getToolTipText();
						if (tip != null) {
							String t = tip.toLowerCase(java.util.Locale.ROOT);
							if (t.contains("página") || t.contains("pagina") || t.contains("page")) {
								((javax.swing.JToggleButton) c).doClick();
								return;
							}
						}
						if (cont == 2) {
							segundoToggle = (javax.swing.JToggleButton) c;
						}
					}
				}
				// Los toggles del toolbar son: Tamaño real, Pagina completa, Ajustar ancho
				if (segundoToggle != null) {
					segundoToggle.doClick();
					return;
				}
			}
			viewer.setFitPageZoomRatio();
		} catch (Exception e) {
			try {
				viewer.setFitPageZoomRatio();
			} catch (Exception ignored) {
			}
		}
	}

	private static void ocultarBotonGuardar(JasperViewer viewer) {
		try {
			JRViewer panel = buscarComponente(viewer.getContentPane(), JRViewer.class);
			if (panel == null) {
				return;
			}
			JRViewerToolbar toolbar = buscarComponente(panel, JRViewerToolbar.class);
			if (toolbar == null) {
				return;
			}
			JButton primerBoton = null;
			for (Component c : toolbar.getComponents()) {
				if (c instanceof JButton) {
					JButton btn = (JButton) c;
					String tip = btn.getToolTipText();
					if (tip != null && (tip.equalsIgnoreCase("save") || tip.equalsIgnoreCase("guardar"))) {
						btn.setVisible(false);
						return;
					}
					if (primerBoton == null) {
						primerBoton = btn;
					}
				}
			}
			// En JasperReports 6.x el primer boton del toolbar es "Guardar"
			if (primerBoton != null) {
				primerBoton.setVisible(false);
			}
		} catch (Exception e) {
			// Si la estructura interna cambia en otra version, el boton queda visible
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T buscarComponente(Container cont, Class<T> tipo) {
		for (Component c : cont.getComponents()) {
			if (tipo.isInstance(c)) {
				return (T) c;
			}
			if (c instanceof Container) {
				T hallado = buscarComponente((Container) c, tipo);
				if (hallado != null) {
					return hallado;
				}
			}
		}
		return null;
	}
}
