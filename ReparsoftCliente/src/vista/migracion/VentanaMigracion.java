package vista.migracion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * VentanaMigracion.java
 *
 * Diálogo principal para la migración de datos Access (.accdb) → MySQL.
 * Paso 1: Permite seleccionar el archivo .accdb, definir el rango de ELS
 *         a migrar y lanzar el proceso mostrando progreso y log en tiempo real.
 *
 * Uso desde VistaPrincipal:
 *   VentanaMigracion ventana = new VentanaMigracion(framePadre);
 *   ventana.setVisible(true);
 *
 * Dependencias requeridas en el proyecto (mismo classpath que ConectorAccess):
 *   - ucanaccess-x.x.x.jar
 *   - jackcess-x.x.x.jar
 *   - commons-lang-x.x.jar
 *   - commons-logging-x.x.jar
 *   - hsqldb.jar
 */
public class VentanaMigracion extends JDialog {

    private static final long serialVersionUID = 1L;

    // ── Constantes por defecto ──────────────────────────────────────────────
    private static final int ELS_MIN_DEFAULT  = 1;
    private static final int ELS_MAX_DEFAULT  = 977;
    private static final String STAGING_HOST  = "localhost";
    private static final String STAGING_PORT  = "3306";
    private static final String STAGING_DB    = "reparsoft_staging";
    private static final String DESTINO_DB    = "ordenesbrc";

    // ── Componentes de la UI ────────────────────────────────────────────────
    // Panel: Archivo
    private JTextField txtRutaArchivo;
    private JButton    btnSeleccionarArchivo;

    // Panel: Rango ELS
    private JSpinner   spnElsDesde;
    private JSpinner   spnElsHasta;

    // Panel: Conexión MySQL Staging
    private JTextField txtStagingHost;
    private JTextField txtStagingPort;
    private JTextField txtStagingDB;
    private JTextField txtStagingUser;
    private JPasswordField txtStagingPass;

    // Panel: Conexión MySQL Destino
    private JTextField txtDestinoHost;
    private JTextField txtDestinoPort;
    private JTextField txtDestinoDB;
    private JTextField txtDestinoUser;
    private JPasswordField txtDestinoPass;

    // Panel: Progreso y log
    private JProgressBar progressBar;
    private JTextArea    txtLog;
    private JLabel       lblEstado;

    // Panel: Botones de acción
    private JButton btnMigrarStaging;   // Paso A: Access → Staging
    private JButton btnMergeDestino;    // Paso B: Staging → Destino
    private JButton btnMigrarCompleto;  // Pasos A+B de una vez
    private JButton btnLimpiarLog;
    private JButton btnCerrar;

    // Estado interno
    private File    archivoAccdb;
    private boolean migracionEnCurso = false;

    // ── Constructor ─────────────────────────────────────────────────────────
    public VentanaMigracion(Frame padre) {
        super(padre, "Migración Access → MySQL", true);
        initComponents();
        layoutComponents();
        initEventos();
        pack();
        setMinimumSize(new Dimension(780, 680));
        setLocationRelativeTo(padre);
    }

    // ════════════════════════════════════════════════════════════════════════
    // INICIALIZACIÓN DE COMPONENTES
    // ════════════════════════════════════════════════════════════════════════
    private void initComponents() {
        // Archivo
        txtRutaArchivo        = new JTextField(40);
        txtRutaArchivo.setEditable(false);
        txtRutaArchivo.setBackground(Color.WHITE);
        btnSeleccionarArchivo = new JButton("Seleccionar...");
        btnSeleccionarArchivo.setIcon(UIManager.getIcon("FileView.fileIcon"));

        // Rango ELS
        SpinnerNumberModel modeloDesde = new SpinnerNumberModel(ELS_MIN_DEFAULT, 1, 99999, 1);
        SpinnerNumberModel modeloHasta = new SpinnerNumberModel(ELS_MAX_DEFAULT, 1, 99999, 1);
        spnElsDesde = new JSpinner(modeloDesde);
        spnElsHasta = new JSpinner(modeloHasta);
        ((JSpinner.DefaultEditor) spnElsDesde.getEditor()).getTextField().setColumns(6);
        ((JSpinner.DefaultEditor) spnElsHasta.getEditor()).getTextField().setColumns(6);

        // Conexión Staging
        txtStagingHost = new JTextField(STAGING_HOST, 12);
        txtStagingPort = new JTextField(STAGING_PORT, 5);
        txtStagingDB   = new JTextField(STAGING_DB,   12);
        txtStagingUser = new JTextField("root",        10);
        txtStagingPass = new JPasswordField("",        10);

        // Conexión Destino
        txtDestinoHost = new JTextField(STAGING_HOST, 12);
        txtDestinoPort = new JTextField(STAGING_PORT,  5);
        txtDestinoDB   = new JTextField(DESTINO_DB,   12);
        txtDestinoUser = new JTextField("root",        10);
        txtDestinoPass = new JPasswordField("",        10);

        // Progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Listo");
        progressBar.setPreferredSize(new Dimension(0, 24));

        lblEstado = new JLabel("Seleccioná el archivo .accdb para comenzar.");
        lblEstado.setFont(lblEstado.getFont().deriveFont(Font.ITALIC));

        // Log
        txtLog = new JTextArea(12, 60);
        txtLog.setEditable(false);
        txtLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        txtLog.setBackground(new Color(30, 30, 30));
        txtLog.setForeground(new Color(180, 255, 180));
        txtLog.setCaretColor(Color.WHITE);

        // Botones de acción
        btnMigrarStaging  = new JButton("① Access → Staging");
        btnMergeDestino   = new JButton("② Staging → Destino");
        btnMigrarCompleto = new JButton("⚡ Migración Completa");
        btnLimpiarLog     = new JButton("Limpiar Log");
        btnCerrar         = new JButton("Cerrar");

        btnMigrarStaging.setToolTipText("Extrae datos del .accdb y los carga en reparsoft_staging");
        btnMergeDestino.setToolTipText("Toma los datos del staging y los inserta en la BD destino (sin duplicados)");
        btnMigrarCompleto.setToolTipText("Ejecuta ambos pasos de forma encadenada");

        btnMigrarStaging.setBackground(new Color(70, 130, 180));
        btnMigrarStaging.setForeground(Color.WHITE);
        btnMigrarStaging.setFocusPainted(false);

        btnMergeDestino.setBackground(new Color(60, 140, 60));
        btnMergeDestino.setForeground(Color.WHITE);
        btnMergeDestino.setFocusPainted(false);

        btnMigrarCompleto.setBackground(new Color(180, 80, 20));
        btnMigrarCompleto.setForeground(Color.WHITE);
        btnMigrarCompleto.setFocusPainted(false);
        btnMigrarCompleto.setFont(btnMigrarCompleto.getFont().deriveFont(Font.BOLD));

        // Estado inicial de botones
        setBotonesHabilitados(false);
    }

    // ════════════════════════════════════════════════════════════════════════
    // LAYOUT
    // ════════════════════════════════════════════════════════════════════════
    private void layoutComponents() {
        setLayout(new BorderLayout(8, 8));
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        // ── Panel superior: configuración ──
        JPanel panelConfig = new JPanel();
        panelConfig.setLayout(new BoxLayout(panelConfig, BoxLayout.Y_AXIS));

        panelConfig.add(crearPanelArchivo());
        panelConfig.add(Box.createVerticalStrut(6));
        panelConfig.add(crearPanelRangoELS());
        panelConfig.add(Box.createVerticalStrut(6));

        // Conexiones en fila
        JPanel panelConexiones = new JPanel(new GridLayout(1, 2, 6, 0));
        panelConexiones.add(crearPanelConexion("BD Staging (intermedia)",
                txtStagingHost, txtStagingPort, txtStagingDB, txtStagingUser, txtStagingPass));
        panelConexiones.add(crearPanelConexion("BD Destino (producción)",
                txtDestinoHost, txtDestinoPort, txtDestinoDB, txtDestinoUser, txtDestinoPass));
        panelConfig.add(panelConexiones);

        add(panelConfig, BorderLayout.NORTH);

        // ── Panel central: progreso + log ──
        JPanel panelCentro = new JPanel(new BorderLayout(4, 4));

        JPanel panelEstado = new JPanel(new BorderLayout(4, 2));
        panelEstado.add(lblEstado, BorderLayout.NORTH);
        panelEstado.add(progressBar, BorderLayout.CENTER);
        panelCentro.add(panelEstado, BorderLayout.NORTH);

        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Log de Migración",
                TitledBorder.LEFT, TitledBorder.TOP));
        panelCentro.add(scrollLog, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // ── Panel inferior: botones ──
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        panelBotones.add(btnMigrarStaging);
        panelBotones.add(btnMergeDestino);
        panelBotones.add(btnMigrarCompleto);
        panelBotones.add(Box.createHorizontalStrut(20));
        panelBotones.add(btnLimpiarLog);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelArchivo() {
        JPanel panel = new JPanel(new BorderLayout(6, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Archivo de base de datos Access (.accdb)"));

        JLabel lbl = new JLabel("Archivo:  ");
        panel.add(lbl, BorderLayout.WEST);
        panel.add(txtRutaArchivo, BorderLayout.CENTER);
        panel.add(btnSeleccionarArchivo, BorderLayout.EAST);
        return panel;
    }

    private JPanel crearPanelRangoELS() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Rango de ELS a migrar"));

        panel.add(new JLabel("Desde ELS:"));
        panel.add(spnElsDesde);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(new JLabel("Hasta ELS:"));
        panel.add(spnElsHasta);
        panel.add(Box.createHorizontalStrut(20));

        // Información rápida
        JLabel lblInfo = new JLabel(
                "<html><i style='color:gray'>Los ELS del sistema MySQL comienzan en 988. " +
                "El rango sugerido para Access es 1-977.</i></html>");
        panel.add(lblInfo);

        return panel;
    }

    private JPanel crearPanelConexion(String titulo,
            JTextField host, JTextField port, JTextField db,
            JTextField user, JPasswordField pass) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        GridBagConstraints c = new GridBagConstraints();
        c.insets  = new Insets(2, 4, 2, 4);
        c.anchor  = GridBagConstraints.WEST;

        String[][] campos = {{"Host:", null}, {"Puerto:", null}, {"BD:", null},
                             {"Usuario:", null}, {"Contraseña:", null}};
        JComponent[] comps = {host, port, db, user, pass};

        for (int i = 0; i < comps.length; i++) {
            c.gridx = 0; c.gridy = i; c.fill = GridBagConstraints.NONE; c.weightx = 0;
            panel.add(new JLabel(campos[i][0]), c);
            c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
            panel.add(comps[i], c);
        }
        return panel;
    }

    // ════════════════════════════════════════════════════════════════════════
    // EVENTOS
    // ════════════════════════════════════════════════════════════════════════
    private void initEventos() {

        // ── Seleccionar archivo .accdb ──────────────────────────────────────
        btnSeleccionarArchivo.addActionListener(e -> seleccionarArchivo());

        // ── Paso 1: Access → Staging ────────────────────────────────────────
        btnMigrarStaging.addActionListener(e -> {
            if (!validarFormulario()) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html>Se extraerán los datos del archivo Access<br>" +
                    "y se cargarán en la BD <b>" + txtStagingDB.getText() + "</b>.<br><br>" +
                    "ELS desde <b>" + spnElsDesde.getValue() +
                    "</b> hasta <b>" + spnElsHasta.getValue() + "</b><br><br>" +
                    "¿Continuar?</html>",
                    "Confirmar migración a Staging",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                ejecutarMigracionStaging();
            }
        });

        // ── Paso 2: Staging → Destino ───────────────────────────────────────
        btnMergeDestino.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html>Se tomarán los datos de <b>" + txtStagingDB.getText() + "</b><br>" +
                    "y se insertarán en <b>" + txtDestinoDB.getText() + "</b>.<br><br>" +
                    "<b>Solo se insertarán registros nuevos (sin duplicados).</b><br><br>" +
                    "¿Continuar?</html>",
                    "Confirmar merge a BD Destino",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                ejecutarMergeDestino();
            }
        });

        // ── Migración completa (A+B) ─────────────────────────────────────────
        btnMigrarCompleto.addActionListener(e -> {
            if (!validarFormulario()) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html><b>MIGRACIÓN COMPLETA</b><br><br>" +
                    "Se ejecutarán los dos pasos:<br>" +
                    "① Access → " + txtStagingDB.getText() + " (staging)<br>" +
                    "② " + txtStagingDB.getText() + " → " + txtDestinoDB.getText() + " (destino)<br><br>" +
                    "ELS desde <b>" + spnElsDesde.getValue() +
                    "</b> hasta <b>" + spnElsHasta.getValue() + "</b><br><br>" +
                    "<font color='red'>¿Estás seguro?</font></html>",
                    "Confirmar migración completa",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                ejecutarMigracionCompleta();
            }
        });

        // ── Limpiar log ─────────────────────────────────────────────────────
        btnLimpiarLog.addActionListener(e -> txtLog.setText(""));

        // ── Cerrar ──────────────────────────────────────────────────────────
        btnCerrar.addActionListener(e -> {
            if (migracionEnCurso) {
                JOptionPane.showMessageDialog(this,
                        "Hay una migración en curso. Esperá a que termine antes de cerrar.",
                        "Operación en curso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dispose();
        });

        // Evitar cierre con X durante migración
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!migracionEnCurso) dispose();
            }
        });
    }

    // ════════════════════════════════════════════════════════════════════════
    // LÓGICA DE SELECCIÓN DE ARCHIVO
    // ════════════════════════════════════════════════════════════════════════
    private void seleccionarArchivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar base de datos Access (.accdb)");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos Access (*.accdb, *.mdb)", "accdb", "mdb"));
        chooser.setAcceptAllFileFilterUsed(false);

        // Intentar abrir en la última ubicación usada (persistencia simple)
        String ultimaRuta = System.getProperty("migracion.ultimaRuta");
        if (ultimaRuta != null) {
            File dir = new File(ultimaRuta).getParentFile();
            if (dir != null && dir.exists()) chooser.setCurrentDirectory(dir);
        }

        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            archivoAccdb = chooser.getSelectedFile();
            txtRutaArchivo.setText(archivoAccdb.getAbsolutePath());
            System.setProperty("migracion.ultimaRuta", archivoAccdb.getAbsolutePath());
            setBotonesHabilitados(true);
            lblEstado.setText("Archivo seleccionado. Configurá el rango de ELS y las conexiones MySQL.");
            agregarLog("INFO", "Archivo seleccionado: " + archivoAccdb.getName() +
                       " (" + String.format("%.1f", archivoAccdb.length() / 1024.0 / 1024.0) + " MB)");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // VALIDACIÓN DEL FORMULARIO
    // ════════════════════════════════════════════════════════════════════════
    private boolean validarFormulario() {
        // Archivo seleccionado
        if (archivoAccdb == null || !archivoAccdb.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Primero seleccioná el archivo .accdb de Access.",
                    "Archivo no seleccionado", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Rango ELS válido
        int elsDesde = (Integer) spnElsDesde.getValue();
        int elsHasta = (Integer) spnElsHasta.getValue();
        if (elsDesde > elsHasta) {
            JOptionPane.showMessageDialog(this,
                    "El ELS inicial (" + elsDesde + ") no puede ser mayor al ELS final (" + elsHasta + ").",
                    "Rango inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Usuario de staging no vacío
        if (txtStagingUser.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completá el usuario de la BD Staging.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            txtStagingUser.requestFocus();
            return false;
        }

        // Usuario de destino no vacío
        if (txtDestinoUser.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completá el usuario de la BD Destino.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            txtDestinoUser.requestFocus();
            return false;
        }

        return true;
    }

    // ════════════════════════════════════════════════════════════════════════
    // EJECUCIÓN EN HILO SEPARADO (para no bloquear la UI)
    // ════════════════════════════════════════════════════════════════════════
    private void ejecutarMigracionStaging() {
        ConfigMigracion config = obtenerConfiguracion();
        setBotonesHabilitados(false);
        migracionEnCurso = true;

        new Thread(() -> {
            try {
                MigracionController controller = new MigracionController(config, this::agregarLog, this::actualizarProgreso);
                controller.migrarAccessAStaging();
            } catch (Exception ex) {
                agregarLog("ERROR", "Error inesperado: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                SwingUtilities.invokeLater(() -> {
                    migracionEnCurso = false;
                    setBotonesHabilitados(true);
                    progressBar.setValue(100);
                    progressBar.setString("Completado");
                    lblEstado.setText("Migración a Staging finalizada. Revisá el log.");
                });
            }
        }, "hilo-migracion-staging").start();
    }

    private void ejecutarMergeDestino() {
        ConfigMigracion config = obtenerConfiguracion();
        setBotonesHabilitados(false);
        migracionEnCurso = true;

        new Thread(() -> {
            try {
                MigracionController controller = new MigracionController(config, this::agregarLog, this::actualizarProgreso);
                controller.mergeStagingADestino();
            } catch (Exception ex) {
                agregarLog("ERROR", "Error inesperado: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                SwingUtilities.invokeLater(() -> {
                    migracionEnCurso = false;
                    setBotonesHabilitados(true);
                    progressBar.setValue(100);
                    progressBar.setString("Completado");
                    lblEstado.setText("Merge a BD Destino finalizado. Revisá el log.");
                });
            }
        }, "hilo-merge-destino").start();
    }    

    private void ejecutarMigracionCompleta() {
        ConfigMigracion config = obtenerConfiguracion();
        setBotonesHabilitados(false);
        migracionEnCurso = true;

        new Thread(() -> {
            try {
                MigracionController controller = new MigracionController(config, this::agregarLog, this::actualizarProgreso);
                controller.migrarAccessAStaging();
                agregarLog("INFO", "─────────── Iniciando Paso 2: Staging → Destino ───────────");
                controller.mergeStagingADestino();
            } catch (Exception ex) {
                agregarLog("ERROR", "Error inesperado: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                SwingUtilities.invokeLater(() -> {
                    migracionEnCurso = false;
                    setBotonesHabilitados(true);
                    progressBar.setValue(100);
                    progressBar.setString("Completado");
                    lblEstado.setText("Migración completa finalizada. Revisá el log.");
                });
            }
        }, "hilo-migracion-completa").start();
    }

    // ════════════════════════════════════════════════════════════════════════
    // MÉTODOS AUXILIARES PÚBLICOS (llamados desde MigracionController)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Agrega una línea al log con timestamp y nivel.
     * Thread-safe: puede llamarse desde hilos secundarios.
     */
    public void agregarLog(String nivel, String mensaje) {
        SwingUtilities.invokeLater(() -> {
            String timestamp = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
            String color;
            switch (nivel.toUpperCase()) {
                case "ERROR": color = "\u001B[31m"; break;  // rojo (sin efecto en JTextArea)
                case "WARN":  color = "\u001B[33m"; break;  // amarillo
                case "OK":    color = "\u001B[32m"; break;  // verde
                default:      color = "";
            }
            txtLog.append("[" + timestamp + "] [" + nivel + "] " + mensaje + "\n");
            txtLog.setCaretPosition(txtLog.getDocument().getLength());
        });
    }

    /**
     * Actualiza la barra de progreso y el label de estado.
     * Thread-safe: puede llamarse desde hilos secundarios.
     *
     * @param porcentaje 0-100
     * @param mensaje    Texto descriptivo del paso actual
     */
    public void actualizarProgreso(int porcentaje, String mensaje) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(porcentaje);
            progressBar.setString(porcentaje + "%  –  " + mensaje);
            lblEstado.setText(mensaje);
        });
    }

    // ════════════════════════════════════════════════════════════════════════
    // CONSTRUCCIÓN DE CONFIGURACIÓN
    // ════════════════════════════════════════════════════════════════════════
    private ConfigMigracion obtenerConfiguracion() {
        return new ConfigMigracion(
                archivoAccdb.getAbsolutePath(),
                (Integer) spnElsDesde.getValue(),
                (Integer) spnElsHasta.getValue(),
                // Staging
                txtStagingHost.getText().trim(),
                txtStagingPort.getText().trim(),
                txtStagingDB.getText().trim(),
                txtStagingUser.getText().trim(),
                new String(txtStagingPass.getPassword()),
                // Destino
                txtDestinoHost.getText().trim(),
                txtDestinoPort.getText().trim(),
                txtDestinoDB.getText().trim(),
                txtDestinoUser.getText().trim(),
                new String(txtDestinoPass.getPassword())
        );
    }

    // ════════════════════════════════════════════════════════════════════════
    // ESTADO DE BOTONES
    // ════════════════════════════════════════════════════════════════════════
    private void setBotonesHabilitados(boolean habilitado) {
        btnMigrarStaging.setEnabled(habilitado);
        btnMergeDestino.setEnabled(habilitado);
        btnMigrarCompleto.setEnabled(habilitado);
        btnSeleccionarArchivo.setEnabled(habilitado || archivoAccdb == null);
    }

    // ════════════════════════════════════════════════════════════════════════
    // MAIN PARA PRUEBA RÁPIDA (eliminar en producción)
    // ════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            VentanaMigracion v = new VentanaMigracion(null);
            v.setVisible(true);
        });
    }
}
