package presentacion.reportes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportePreloader {

    private static boolean preloaded = false;

    public static synchronized void preload() {
        if (preloaded) return;
        preloaded = true;

        new Thread(() -> {
            try {
                Class.forName("net.sf.jasperreports.view.JasperViewer");
                Class.forName("net.sf.jasperreports.view.JRViewer");
            } catch (ClassNotFoundException e) {
            }

            String[] paths = {
                "reportes\\ReporteRegistroEntrada2.jasper",
                "reportes\\RemitoPreImpreso.jasper",
                "reportes\\RemitoComun.jasper",
                "reportes\\Presupuesto.jasper",
                "reportes\\ResumenTecnico.jasper",
                "reportes\\ReporteAgenda.jasper"
            };

            for (String path : paths) {
                try {
                    JRLoader.loadObjectFromFile(path);
                } catch (JRException e) {
                }
            }
        }).start();
    }
}