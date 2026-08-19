package presentacion.reportes;

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

            ReportePresupuesto.precargar();
            ReporteRegistroEntrada.precargar();
            ReporteAgenda.precargar();
            ReporteRemitoSalida.precargar();
            ReporteResumenTecnico.precargar();
        }).start();
    }
}