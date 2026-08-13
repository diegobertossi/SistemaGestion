package vista.migracion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
//importar MigracionController
import vista.migracion.MigracionController;
import vista.migracion.ConfigMigracion;
import util.Config;

/**
 * VentanaMigracion.java
 *
 * CAMBIOS v3:
 * - Rango ELS "Hasta" default = 1
 * - Se eliminó la aclaración sobre ELS del sistema MySQL
 * - BD Staging: contraseña = "root", campos visibles pero NO editables ni habilitados
 * - BD Destino: 4 RadioButtons para elegir la BD
 *     · Datos de Bariloche Antiguos  → ordenesbrcantiguas
 *     · Datos de Buenos Aires Antiguos → ordenesbsasantiguas
 *     · Datos Bariloche Actuales     → ordenesbrc
 *     · Datos Buenos Aires Actuales  → ordenesbsas
 * - BD Destino: contraseña = "root"
 * - GridBagConstraints: instancia nueva por add() para compatibilidad con WindowBuilder
 * - Agregados botones "Vaciar" junto a cada RadioButton de BD destino
 */
public class VentanaMigracion extends JFrame  {

    private static final long serialVersionUID = 1L;
    
    private MigracionController controlador;
    private ConfigMigracion config;

    // ── Constantes por defecto ──────────────────────────────────────────────
    private static final int    ELS_MIN_DEFAULT   = 1;
    private static final int    ELS_MAX_DEFAULT   = 1;
    private static final String STAGING_HOST      = Config.get("migracion.staging.host", "localhost");
    private static final String STAGING_PORT      = Config.get("migracion.staging.port", "3306");
    private static final String STAGING_DB        = Config.get("migracion.staging.database", "reparsoft_staging");
    private static final String STAGING_USER      = Config.get("migracion.staging.user", "root");
    private static final String STAGING_PASS      = Config.get("migracion.staging.password", "root");

    private static final String DESTINO_HOST      = Config.get("migracion.destino.host", "localhost");
    private static final String DESTINO_PORT      = Config.get("migracion.destino.port", "3306");
    private static final String DESTINO_USER      = Config.get("migracion.destino.user", "root");
    private static final String DESTINO_PASS      = Config.get("migracion.destino.password", "root");

    // Nombres de las 4 BDs destino
    private static final String DB_BRC_ANT = "ordenesbrcantiguas";
    private static final String DB_BAS_ANT = "ordenesbsasantiguas";
    private static final String DB_BRC_ACT = "ordenesbrc";
    private static final String DB_BAS_ACT = "ordenesbsas";
    


    // ── Componentes de la UI ────────────────────────────────────────────────
    private JTextField     txtRutaArchivo;
    private JButton        btnSeleccionarArchivo;

    private JSpinner       spnElsDesde;
    private JSpinner       spnElsHasta;

    // Staging (no editables)
    private JTextField     txtStagingHost;
    private JTextField     txtStagingPort;
    private JTextField     txtStagingDB;
    private JTextField     txtStagingUser;
    private JPasswordField txtStagingPass;

    // Destino
    private JTextField     txtDestinoHost;
    private JTextField     txtDestinoPort;
    private JTextField     txtDestinoUser;
    private JPasswordField txtDestinoPass;

    // 4 RadioButtons para BD destino
    private JRadioButton   rbBrcAnt;
    private JRadioButton   rbBasAnt;
    private JRadioButton   rbBrcAct;
    private JRadioButton   rbBasAct;

    // Botones para vaciar bases de datos
    private JButton        btnVaciarBrcAnt;
    private JButton        btnVaciarBasAnt;
    private JButton        btnVaciarBrcAct;
    private JButton        btnVaciarBasAct;

    // Progreso y log
    private JProgressBar   progressBar;
    private JTextArea      txtLog;
    private JLabel         lblEstado;

    // Botones de acción
    private JButton        btnMigrarStaging;
    private JButton        btnMergeDestino;
    private JButton        btnMigrarCompleto;
    private JButton        btnLimpiarLog;
    private JButton        btnCerrar;

    // Estado interno
    private File    archivoAccdb;
    private boolean migracionEnCurso = false;
    private GridBagConstraints c_1;
    private GridBagConstraints c_2;
    private GridBagConstraints c_3;
    private GridBagConstraints c_4;
    private GridBagConstraints c_5;
    private GridBagConstraints c_6;
    private GridBagConstraints c_7;
    private GridBagConstraints c_8;
    private GridBagConstraints c_9;

    // ── Constructor ─────────────────────────────────────────────────────────
    public VentanaMigracion(MigracionController controlador) {
        super();
        this.controlador = controlador;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 720);
        setResizable(false);
        this.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
        this.setIconImage(icon);

        initComponents();
        layoutComponents();
        initEventos();

        this.setVisible(true);
    }

    // ════════════════════════════════════════════════════════════════════════
    // INICIALIZACIÓN DE COMPONENTES
    // ════════════════════════════════════════════════════════════════════════
    private void initComponents() {

        // Archivo
        txtRutaArchivo = new JTextField();
        txtRutaArchivo.setEditable(false);
        btnSeleccionarArchivo = new JButton("Seleccionar...");

        // Rango ELS
        spnElsDesde = new JSpinner(new SpinnerNumberModel(ELS_MIN_DEFAULT, 1, 99999, 1));
        spnElsHasta = new JSpinner(new SpinnerNumberModel(ELS_MAX_DEFAULT, 1, 99999, 1));

        // Destino — host/puerto/usuario fijos
        txtDestinoHost = new JTextField(DESTINO_HOST);
        txtDestinoHost.setEnabled(false);
        txtDestinoPort = new JTextField(DESTINO_PORT);
        txtDestinoPort.setEnabled(false);
        txtDestinoUser = new JTextField(DESTINO_USER);
        txtDestinoUser.setEnabled(false);
        txtDestinoPass = new JPasswordField(DESTINO_PASS);
        txtDestinoPass.setEnabled(false);

        // RadioButtons BD destino — grupo único (solo una selección posible)
        rbBrcAnt = new JRadioButton("Datos de Bariloche Antiguos");
        rbBasAnt = new JRadioButton("Datos de Buenos Aires Antiguos");
        rbBrcAct = new JRadioButton("Datos de Bariloche Actuales");
        rbBasAct = new JRadioButton("Datos de Buenos Aires Actuales");

        ButtonGroup grupoBD = new ButtonGroup();
        grupoBD.add(rbBrcAnt);
        grupoBD.add(rbBasAnt);
        grupoBD.add(rbBrcAct);
        grupoBD.add(rbBasAct);
        rbBrcAnt.setSelected(true); // selección por defecto

        // Botones para vaciar bases de datos
        btnVaciarBrcAnt = new JButton("Vaciar");
        btnVaciarBrcAnt.setToolTipText("Eliminar todos los datos de " + DB_BRC_ANT);
        btnVaciarBrcAnt.setFont(btnVaciarBrcAnt.getFont().deriveFont(9f));
        btnVaciarBrcAnt.setPreferredSize(new Dimension(70, 24));
        btnVaciarBrcAnt.setBackground(new Color(180, 60, 60));
        btnVaciarBrcAnt.setForeground(Color.WHITE);
        btnVaciarBrcAnt.setFocusPainted(false);

        btnVaciarBasAnt = new JButton("Vaciar");
        btnVaciarBasAnt.setToolTipText("Eliminar todos los datos de " + DB_BAS_ANT);
        btnVaciarBasAnt.setFont(btnVaciarBasAnt.getFont().deriveFont(9f));
        btnVaciarBasAnt.setPreferredSize(new Dimension(70, 24));
        btnVaciarBasAnt.setBackground(new Color(180, 60, 60));
        btnVaciarBasAnt.setForeground(Color.WHITE);
        btnVaciarBasAnt.setFocusPainted(false);

        btnVaciarBrcAct = new JButton("Vaciar");
        btnVaciarBrcAct.setToolTipText("Eliminar todos los datos de " + DB_BRC_ACT);
        btnVaciarBrcAct.setFont(btnVaciarBrcAct.getFont().deriveFont(9f));
        btnVaciarBrcAct.setPreferredSize(new Dimension(70, 24));
        btnVaciarBrcAct.setBackground(new Color(180, 60, 60));
        btnVaciarBrcAct.setForeground(Color.WHITE);
        btnVaciarBrcAct.setFocusPainted(false);

        btnVaciarBasAct = new JButton("Vaciar");
        btnVaciarBasAct.setToolTipText("Eliminar todos los datos de " + DB_BAS_ACT);
        btnVaciarBasAct.setFont(btnVaciarBasAct.getFont().deriveFont(9f));
        btnVaciarBasAct.setPreferredSize(new Dimension(70, 24));
        btnVaciarBasAct.setBackground(new Color(180, 60, 60));
        btnVaciarBasAct.setForeground(Color.WHITE);
        btnVaciarBasAct.setFocusPainted(false);

        // Progreso
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        lblEstado = new JLabel("Seleccioná el archivo .accdb para comenzar.");

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
        btnMigrarStaging.setForeground(Color.BLACK);
        btnMigrarStaging.setFocusPainted(false);

        btnMergeDestino.setBackground(new Color(60, 140, 60));
        btnMergeDestino.setForeground(Color.BLACK);
        btnMergeDestino.setFocusPainted(false);

        btnMigrarCompleto.setBackground(new Color(180, 80, 20));
        btnMigrarCompleto.setForeground(Color.BLACK);
        btnMigrarCompleto.setFocusPainted(false);
        btnMigrarCompleto.setFont(btnMigrarCompleto.getFont().deriveFont(Font.BOLD));
    }

    // ════════════════════════════════════════════════════════════════════════
    // LAYOUT
    // ════════════════════════════════════════════════════════════════════════
    private void layoutComponents() {
        // Panel superior
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        getContentPane().add(panelTop, BorderLayout.NORTH);

        // Archivo
        JPanel panelArchivo = new JPanel(new BorderLayout(6, 0));
        panelArchivo.setBorder(BorderFactory.createTitledBorder("Archivo Access"));
        panelTop.add(panelArchivo);

        panelArchivo.add(txtRutaArchivo, BorderLayout.CENTER);
        panelArchivo.add(btnSeleccionarArchivo, BorderLayout.EAST);

        // Rango
        JPanel panelRango = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRango.setBorder(BorderFactory.createTitledBorder("Rango ELS"));
        panelTop.add(panelRango);

        panelRango.add(new JLabel("Desde:"));
        panelRango.add(spnElsDesde);
        panelRango.add(new JLabel("Hasta:"));
        panelRango.add(spnElsHasta);

        // Conexiones
        JPanel panelConexiones = new JPanel(new GridLayout(1, 2, 6, 0));
        panelTop.add(panelConexiones);

        // Staging
        JPanel panelStaging = new JPanel(new GridBagLayout());
        panelStaging.setBorder(BorderFactory.createTitledBorder("Staging"));
        panelConexiones.add(panelStaging);

        GridBagConstraints c;

        c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel("Host:");
        panelStaging.add(label, c);

        // Staging — campos fijos
        txtStagingHost = new JTextField(STAGING_HOST);
        txtStagingHost.setEnabled(false);
        c_1 = new GridBagConstraints();
        c_1.insets = new Insets(0, 0, 5, 0);
        c_1.gridx = 1;
        c_1.gridy = 0;
        c_1.fill = GridBagConstraints.HORIZONTAL;
        c_1.weightx = 1;
        panelStaging.add(txtStagingHost, c_1);

        c_2 = new GridBagConstraints();
        c_2.insets = new Insets(0, 0, 5, 5);
        c_2.gridx = 0;
        c_2.gridy = 1;
        c_2.anchor = GridBagConstraints.WEST;
        JLabel label_1 = new JLabel("Puerto:");
        panelStaging.add(label_1, c_2);
        txtStagingPort = new JTextField(STAGING_PORT);
        txtStagingPort.setEnabled(false);
        c_3 = new GridBagConstraints();
        c_3.insets = new Insets(0, 0, 5, 0);
        c_3.gridx = 1;
        c_3.gridy = 1;
        c_3.fill = GridBagConstraints.HORIZONTAL;
        c_3.weightx = 1;
        panelStaging.add(txtStagingPort, c_3);

        c_6 = new GridBagConstraints();
        c_6.insets = new Insets(0, 0, 5, 5);
        c_6.gridx = 0;
        c_6.gridy = 2;
        c_6.anchor = GridBagConstraints.WEST;
        JLabel label_3 = new JLabel("Usuario:");
        panelStaging.add(label_3, c_6);
        txtStagingUser = new JTextField(STAGING_USER);
        txtStagingUser.setEnabled(false);
        c_7 = new GridBagConstraints();
        c_7.insets = new Insets(0, 0, 5, 0);
        c_7.gridx = 1;
        c_7.gridy = 2;
        c_7.fill = GridBagConstraints.HORIZONTAL;
        c_7.weightx = 1;
        panelStaging.add(txtStagingUser, c_7);

        c_8 = new GridBagConstraints();
        c_8.insets = new Insets(0, 0, 5, 5);
        c_8.gridx = 0;
        c_8.gridy = 3;
        c_8.anchor = GridBagConstraints.WEST;
        JLabel label_4 = new JLabel("Pass:");
        panelStaging.add(label_4, c_8);
        txtStagingPass = new JPasswordField(STAGING_PASS);
        txtStagingPass.setEnabled(false);
        c_9 = new GridBagConstraints();
        c_9.insets = new Insets(0, 0, 5, 0);
        c_9.gridx = 1;
        c_9.gridy = 3;
        c_9.fill = GridBagConstraints.HORIZONTAL;
        c_9.weightx = 1;
        panelStaging.add(txtStagingPass, c_9);

        c_4 = new GridBagConstraints();
        c_4.insets = new Insets(0, 0, 5, 5);
        c_4.gridx = 0;
        c_4.gridy = 4;
        c_4.anchor = GridBagConstraints.WEST;
        JLabel label_2 = new JLabel("BD:");
        panelStaging.add(label_2, c_4);
        txtStagingDB = new JTextField(STAGING_DB);
        txtStagingDB.setEnabled(false);
        c_5 = new GridBagConstraints();
        c_5.insets = new Insets(0, 0, 5, 0);
        c_5.gridx = 1;
        c_5.gridy = 4;
        c_5.fill = GridBagConstraints.HORIZONTAL;
        c_5.weightx = 1;
        panelStaging.add(txtStagingDB, c_5);

        // Destino
        JPanel panelDestino = new JPanel(new GridBagLayout());
        panelDestino.setBorder(BorderFactory.createTitledBorder("Destino"));
        panelConexiones.add(panelDestino);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        panelDestino.add(new JLabel("Host:"), c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(txtDestinoHost, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        panelDestino.add(new JLabel("Puerto:"), c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(txtDestinoPort, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        panelDestino.add(new JLabel("Usuario:"), c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(txtDestinoUser, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        panelDestino.add(new JLabel("Pass:"), c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(txtDestinoPass, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        panelDestino.add(new JLabel("BD:"), c);

        // Panel para rbBrcAnt con su botón Vaciar
        JPanel panelBrcAnt = new JPanel(new BorderLayout(5, 0));
        panelBrcAnt.add(rbBrcAnt, BorderLayout.CENTER);
        panelBrcAnt.add(btnVaciarBrcAnt, BorderLayout.EAST);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(panelBrcAnt, c);

        // Panel para rbBasAnt con su botón Vaciar
        JPanel panelBasAnt = new JPanel(new BorderLayout(5, 0));
        panelBasAnt.add(rbBasAnt, BorderLayout.CENTER);
        panelBasAnt.add(btnVaciarBasAnt, BorderLayout.EAST);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(panelBasAnt, c);

        // Panel para rbBrcAct con su botón Vaciar
        JPanel panelBrcAct = new JPanel(new BorderLayout(5, 0));
        panelBrcAct.add(rbBrcAct, BorderLayout.CENTER);
        panelBrcAct.add(btnVaciarBrcAct, BorderLayout.EAST);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(panelBrcAct, c);

        // Panel para rbBasAct con su botón Vaciar
        JPanel panelBasAct = new JPanel(new BorderLayout(5, 0));
        panelBasAct.add(rbBasAct, BorderLayout.CENTER);
        panelBasAct.add(btnVaciarBasAct, BorderLayout.EAST);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 7;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelDestino.add(panelBasAct, c);

        // Panel central
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

        getContentPane().add(panelCentro, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel();
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        panelBotones.add(btnMigrarStaging);
        panelBotones.add(btnMergeDestino);
        panelBotones.add(btnMigrarCompleto);
        panelBotones.add(btnLimpiarLog);
        panelBotones.add(btnCerrar);
    }

    // ════════════════════════════════════════════════════════════════════════
    // EVENTOS
    // ════════════════════════════════════════════════════════════════════════
    private void initEventos() {

        btnSeleccionarArchivo.addActionListener(e -> seleccionarArchivo());

        btnMigrarStaging.addActionListener(e -> {
            if (!validarFormulario()) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html>Se extraerán los datos del archivo Access<br>" +
                    "y se cargarán en la BD <b>" + STAGING_DB + "</b>.<br><br>" +
                    "ELS desde <b>" + spnElsDesde.getValue() +
                    "</b> hasta <b>" + spnElsHasta.getValue() + "</b><br><br>" +
                    "¿Continuar?</html>",
                    "Confirmar migración a Staging",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                ejecutarMigracionStaging();
            }
        });

        btnMergeDestino.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html>Se tomarán los datos de <b>" + STAGING_DB + "</b><br>" +
                    "y se insertarán en <b>" + getDestinoBDSeleccionada() + "</b>.<br><br>" +
                    "<b>Solo se insertarán registros nuevos (sin duplicados).</b><br><br>" +
                    "¿Continuar?</html>",
                    "Confirmar merge a BD Destino",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                ejecutarMergeDestino();
            }
        });

        btnMigrarCompleto.addActionListener(e -> {
            if (!validarFormulario()) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "<html><b>MIGRACIÓN COMPLETA</b><br><br>" +
                    "Se ejecutarán los dos pasos:<br>" +
                    "① Access → " + STAGING_DB + " (staging)<br>" +
                    "② " + STAGING_DB + " → <b>" + getDestinoBDSeleccionada() + "</b> (destino)<br><br>" +
                    "ELS desde <b>" + spnElsDesde.getValue() +
                    "</b> hasta <b>" + spnElsHasta.getValue() + "</b><br><br>" +
                    "<font color='red'>¿Estás seguro?</font></html>",
                    "Confirmar migración completa",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                ejecutarMigracionCompleta();
            }
        });

        btnLimpiarLog.addActionListener(e -> txtLog.setText(""));

        btnCerrar.addActionListener(e -> {
            if (migracionEnCurso) {
                JOptionPane.showMessageDialog(this,
                        "Hay una migración en curso. Esperá a que termine antes de cerrar.",
                        "Operación en curso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dispose();
        });

        // ActionListeners para los botones Vaciar
        btnVaciarBrcAnt.addActionListener(e -> vaciarBaseDatos(DB_BRC_ANT, "Bariloche Antiguos"));
        btnVaciarBasAnt.addActionListener(e -> vaciarBaseDatos(DB_BAS_ANT, "Buenos Aires Antiguos"));
        btnVaciarBrcAct.addActionListener(e -> vaciarBaseDatos(DB_BRC_ACT, "Bariloche Actuales"));
        btnVaciarBasAct.addActionListener(e -> vaciarBaseDatos(DB_BAS_ACT, "Buenos Aires Actuales"));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!migracionEnCurso) dispose();
            }
        });
    }

    // ════════════════════════════════════════════════════════════════════════
    // SELECCIÓN DE ARCHIVO
    // ════════════════════════════════════════════════════════════════════════
    private void seleccionarArchivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar base de datos Access (.accdb)");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos Access (*.accdb, *.mdb)", "accdb", "mdb"));
        chooser.setAcceptAllFileFilterUsed(false);

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
            lblEstado.setText("Archivo seleccionado. Configurá el rango de ELS y elegí la BD destino.");
            agregarLog("INFO", "Archivo seleccionado: " + archivoAccdb.getName() +
                       " (" + String.format("%.1f", archivoAccdb.length() / 1024.0 / 1024.0) + " MB)");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // VALIDACIÓN
    // ════════════════════════════════════════════════════════════════════════
    private boolean validarFormulario() {
        if (archivoAccdb == null || !archivoAccdb.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Primero seleccioná el archivo .accdb de Access.",
                    "Archivo no seleccionado", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        int elsDesde = (Integer) spnElsDesde.getValue();
        int elsHasta = (Integer) spnElsHasta.getValue();
        if (elsDesde > elsHasta) {
            JOptionPane.showMessageDialog(this,
                    "El ELS inicial (" + elsDesde + ") no puede ser mayor al ELS final (" + elsHasta + ").",
                    "Rango inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // ════════════════════════════════════════════════════════════════════════
    // VACIAR BASE DE DATOS
    // ════════════════════════════════════════════════════════════════════════
    private void vaciarBaseDatos(String nombreBD, String descripcion) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><font color='red'><b>¡ADVERTENCIA!</b></font><br><br>" +
                "Está por eliminar TODOS los datos de la base de datos:<br>" +
                "<b>" + nombreBD + "</b> (" + descripcion + ")<br><br>" +
                "Esta acción NO se puede deshacer.<br><br>" +
                "¿Está absolutamente seguro?</html>",
                "Vaciar Base de Datos",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            //setBotonesHabilitados(false);
            

            new Thread(() -> {
                MigracionController ctrl = new MigracionController(config, this::agregarLog, this::actualizarProgreso);
                ctrl.vaciarBaseDatos(nombreBD);
                // luego continuar con la migración...
            }, "hilo-vaciar-bd").start();
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // EJECUCIÓN EN HILO SEPARADO
    // ════════════════════════════════════════════════════════════════════════
    private void ejecutarMigracionStaging() {
        ConfigMigracion config = obtenerConfiguracion();
        setBotonesHabilitados(false);
        migracionEnCurso = true;
        new Thread(() -> {
            try {
                new MigracionController(config, this::agregarLog, this::actualizarProgreso)
                        .migrarAccessAStaging();
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
                new MigracionController(config, this::agregarLog, this::actualizarProgreso)
                        .mergeStagingADestino();
            } catch (Exception ex) {
                agregarLog("ERROR", "Error inesperado: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                SwingUtilities.invokeLater(() -> {
                    migracionEnCurso = false;
                    setBotonesHabilitados(true);
                    progressBar.setValue(100);
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
    // AUXILIARES
    // ════════════════════════════════════════════════════════════════════════

    /** Devuelve el nombre de BD destino según el RadioButton seleccionado */
    private String getDestinoBDSeleccionada() {
        if (rbBrcAnt.isSelected()) return DB_BRC_ANT;
        if (rbBasAnt.isSelected()) return DB_BAS_ANT;
        if (rbBrcAct.isSelected()) return DB_BRC_ACT;
        return DB_BAS_ACT; // rbBasAct
    }

    public void agregarLog(String nivel, String mensaje) {
        SwingUtilities.invokeLater(() -> {
            String ts = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
            txtLog.append("[" + ts + "] [" + nivel + "] " + mensaje + "\n");
            txtLog.setCaretPosition(txtLog.getDocument().getLength());
        });
    }

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
                STAGING_HOST, STAGING_PORT, STAGING_DB, STAGING_USER, STAGING_PASS,
                DESTINO_HOST, DESTINO_PORT, getDestinoBDSeleccionada(), DESTINO_USER, DESTINO_PASS
        );
    }

    private void setBotonesHabilitados(boolean habilitado) {
        btnMigrarStaging.setEnabled(habilitado);
        btnMergeDestino.setEnabled(habilitado);
        btnMigrarCompleto.setEnabled(habilitado);
        btnSeleccionarArchivo.setEnabled(habilitado || archivoAccdb == null);
        btnVaciarBrcAnt.setEnabled(habilitado);
        btnVaciarBasAnt.setEnabled(habilitado);
        btnVaciarBrcAct.setEnabled(habilitado);
        btnVaciarBasAct.setEnabled(habilitado);
    }
}