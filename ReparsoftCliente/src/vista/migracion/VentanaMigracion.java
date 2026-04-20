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
 */
public class VentanaMigracion extends JDialog {

    private static final long serialVersionUID = 1L;

    // ── Constantes por defecto ──────────────────────────────────────────────
    private static final int    ELS_MIN_DEFAULT   = 1;
    private static final int    ELS_MAX_DEFAULT   = 1;
    private static final String STAGING_HOST      = "localhost";
    private static final String STAGING_PORT      = "3306";
    private static final String STAGING_DB        = "reparsoft_staging";
    private static final String STAGING_USER      = "root";
    private static final String STAGING_PASS      = "root";

    private static final String DESTINO_HOST      = "localhost";
    private static final String DESTINO_PORT      = "3306";
    private static final String DESTINO_USER      = "root";
    private static final String DESTINO_PASS      = "root";

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

    // ── Constructor ─────────────────────────────────────────────────────────
    public VentanaMigracion(Frame padre) {
        super(padre, "Migración Access → MySQL", true);
        initComponents();
        layoutComponents();
        initEventos();
        pack();
        setMinimumSize(new Dimension(780, 720));
        setLocationRelativeTo(padre);
    }

    // ════════════════════════════════════════════════════════════════════════
    // INICIALIZACIÓN DE COMPONENTES
    // ════════════════════════════════════════════════════════════════════════
    private void initComponents() {

        // Archivo
        txtRutaArchivo = new JTextField(40);
        txtRutaArchivo.setEditable(false);
        txtRutaArchivo.setBackground(Color.WHITE);
        btnSeleccionarArchivo = new JButton("Seleccionar...");
        btnSeleccionarArchivo.setIcon(UIManager.getIcon("FileView.fileIcon"));

        // Rango ELS
        spnElsDesde = new JSpinner(new SpinnerNumberModel(ELS_MIN_DEFAULT, 1, 99999, 1));
        spnElsHasta = new JSpinner(new SpinnerNumberModel(ELS_MAX_DEFAULT, 1, 99999, 1));
        ((JSpinner.DefaultEditor) spnElsDesde.getEditor()).getTextField().setColumns(6);
        ((JSpinner.DefaultEditor) spnElsHasta.getEditor()).getTextField().setColumns(6);

        // Staging — campos fijos
        txtStagingHost = crearCampoFijo(STAGING_HOST, 12);
        txtStagingPort = crearCampoFijo(STAGING_PORT, 5);
        txtStagingDB   = crearCampoFijo(STAGING_DB,   14);
        txtStagingUser = crearCampoFijo(STAGING_USER,  8);
        txtStagingPass = crearPassFijo(STAGING_PASS,   8);

        // Destino — host/puerto/usuario fijos
        txtDestinoHost = crearCampoFijo(DESTINO_HOST, 12);
        txtDestinoPort = crearCampoFijo(DESTINO_PORT,  5);
        txtDestinoUser = crearCampoFijo(DESTINO_USER,  8);
        txtDestinoPass = crearPassFijo(DESTINO_PASS,   8);

        // RadioButtons BD destino — grupo único (solo una selección posible)
        rbBrcAnt = new JRadioButton("Datos de Bariloche Antiguos    → " + DB_BRC_ANT);
        rbBasAnt = new JRadioButton("Datos de Buenos Aires Antiguos → " + DB_BAS_ANT);
        rbBrcAct = new JRadioButton("Datos Bariloche Actuales       → " + DB_BRC_ACT);
        rbBasAct = new JRadioButton("Datos Buenos Aires Actuales    → " + DB_BAS_ACT);

        ButtonGroup grupoBD = new ButtonGroup();
        grupoBD.add(rbBrcAnt);
        grupoBD.add(rbBasAnt);
        grupoBD.add(rbBrcAct);
        grupoBD.add(rbBasAct);
        rbBrcAnt.setSelected(true); // selección por defecto

        // Progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Listo");
        progressBar.setPreferredSize(new Dimension(0, 24));

        lblEstado = new JLabel("Seleccioná el archivo .accdb para comenzar.");
        lblEstado.setFont(lblEstado.getFont().deriveFont(Font.ITALIC));

        // Log oscuro
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

        setBotonesHabilitados(false);
    }

    // ── Helpers para campos fijos ───────────────────────────────────────────
    private JTextField crearCampoFijo(String valor, int cols) {
        JTextField tf = new JTextField(valor, cols);
        tf.setEditable(false);
        tf.setEnabled(false);
        tf.setDisabledTextColor(new Color(50, 50, 50));
        tf.setBackground(new Color(230, 230, 230));
        return tf;
    }

    private JPasswordField crearPassFijo(String valor, int cols) {
        JPasswordField pf = new JPasswordField(valor, cols);
        pf.setEditable(false);
        pf.setEnabled(false);
        pf.setDisabledTextColor(new Color(50, 50, 50));
        pf.setBackground(new Color(230, 230, 230));
        return pf;
    }

    // ════════════════════════════════════════════════════════════════════════
    // LAYOUT
    // ════════════════════════════════════════════════════════════════════════
    private void layoutComponents() {
        setLayout(new BorderLayout(8, 8));
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel superior: configuración
        JPanel panelConfig = new JPanel();
        panelConfig.setLayout(new BoxLayout(panelConfig, BoxLayout.Y_AXIS));
        panelConfig.add(crearPanelArchivo());
        panelConfig.add(Box.createVerticalStrut(6));
        panelConfig.add(crearPanelRangoELS());
        panelConfig.add(Box.createVerticalStrut(6));

        JPanel panelConexiones = new JPanel(new GridLayout(1, 2, 6, 0));
        panelConexiones.add(crearPanelStaging());
        panelConexiones.add(crearPanelDestino());
        panelConfig.add(panelConexiones);

        add(panelConfig, BorderLayout.NORTH);

        // Panel central: progreso + log
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

        // Panel inferior: botones
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
        panel.add(new JLabel("Archivo:  "), BorderLayout.WEST);
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
        return panel;
    }

    private JPanel crearPanelStaging() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("BD Staging (intermedia)"));

        JLabel lblStagHost = new JLabel("Host:");
        JLabel lblStagPort = new JLabel("Puerto:");
        JLabel lblStagDB   = new JLabel("BD:");
        JLabel lblStagUser = new JLabel("Usuario:");
        JLabel lblStagPass = new JLabel("Contrase\u00f1a:");

        GridBagConstraints sLH = new GridBagConstraints(); sLH.insets=new Insets(2,4,2,4); sLH.anchor=GridBagConstraints.WEST; sLH.gridx=0; sLH.gridy=0; sLH.fill=GridBagConstraints.NONE;       sLH.weightx=0;
        GridBagConstraints sFH = new GridBagConstraints(); sFH.insets=new Insets(2,4,2,4); sFH.anchor=GridBagConstraints.WEST; sFH.gridx=1; sFH.gridy=0; sFH.fill=GridBagConstraints.HORIZONTAL; sFH.weightx=1;
        GridBagConstraints sLP = new GridBagConstraints(); sLP.insets=new Insets(2,4,2,4); sLP.anchor=GridBagConstraints.WEST; sLP.gridx=0; sLP.gridy=1; sLP.fill=GridBagConstraints.NONE;       sLP.weightx=0;
        GridBagConstraints sFP = new GridBagConstraints(); sFP.insets=new Insets(2,4,2,4); sFP.anchor=GridBagConstraints.WEST; sFP.gridx=1; sFP.gridy=1; sFP.fill=GridBagConstraints.HORIZONTAL; sFP.weightx=1;
        GridBagConstraints sLD = new GridBagConstraints(); sLD.insets=new Insets(2,4,2,4); sLD.anchor=GridBagConstraints.WEST; sLD.gridx=0; sLD.gridy=2; sLD.fill=GridBagConstraints.NONE;       sLD.weightx=0;
        GridBagConstraints sFD = new GridBagConstraints(); sFD.insets=new Insets(2,4,2,4); sFD.anchor=GridBagConstraints.WEST; sFD.gridx=1; sFD.gridy=2; sFD.fill=GridBagConstraints.HORIZONTAL; sFD.weightx=1;
        GridBagConstraints sLU = new GridBagConstraints(); sLU.insets=new Insets(2,4,2,4); sLU.anchor=GridBagConstraints.WEST; sLU.gridx=0; sLU.gridy=3; sLU.fill=GridBagConstraints.NONE;       sLU.weightx=0;
        GridBagConstraints sFU = new GridBagConstraints(); sFU.insets=new Insets(2,4,2,4); sFU.anchor=GridBagConstraints.WEST; sFU.gridx=1; sFU.gridy=3; sFU.fill=GridBagConstraints.HORIZONTAL; sFU.weightx=1;
        GridBagConstraints sLPw= new GridBagConstraints(); sLPw.insets=new Insets(2,4,2,4);sLPw.anchor=GridBagConstraints.WEST;sLPw.gridx=0;sLPw.gridy=4;sLPw.fill=GridBagConstraints.NONE;       sLPw.weightx=0;
        GridBagConstraints sFPw= new GridBagConstraints(); sFPw.insets=new Insets(2,4,2,4);sFPw.anchor=GridBagConstraints.WEST;sFPw.gridx=1;sFPw.gridy=4;sFPw.fill=GridBagConstraints.HORIZONTAL; sFPw.weightx=1;

        panel.add(lblStagHost,    sLH);
        panel.add(txtStagingHost, sFH);
        panel.add(lblStagPort,    sLP);
        panel.add(txtStagingPort, sFP);
        panel.add(lblStagDB,      sLD);
        panel.add(txtStagingDB,   sFD);
        panel.add(lblStagUser,    sLU);
        panel.add(txtStagingUser, sFU);
        panel.add(lblStagPass,    sLPw);
        panel.add(txtStagingPass, sFPw);

        return panel;
    }

    private JPanel crearPanelDestino() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("BD Destino (producci\u00f3n)"));

        JLabel lblDestHost  = new JLabel("Host:");
        JLabel lblDestPort  = new JLabel("Puerto:");
        JLabel lblDestBD    = new JLabel("BD:");
        JLabel lblDestBD2   = new JLabel("");
        JLabel lblDestBD3   = new JLabel("");
        JLabel lblDestBD4   = new JLabel("");
        JLabel lblDestUser  = new JLabel("Usuario:");
        JLabel lblDestPass  = new JLabel("Contrase\u00f1a:");

        GridBagConstraints dLH  = new GridBagConstraints(); dLH.insets=new Insets(2,4,2,4);  dLH.anchor=GridBagConstraints.WEST;  dLH.gridx=0;  dLH.gridy=0;  dLH.fill=GridBagConstraints.NONE;       dLH.weightx=0;
        GridBagConstraints dFH  = new GridBagConstraints(); dFH.insets=new Insets(2,4,2,4);  dFH.anchor=GridBagConstraints.WEST;  dFH.gridx=1;  dFH.gridy=0;  dFH.fill=GridBagConstraints.HORIZONTAL; dFH.weightx=1;
        GridBagConstraints dLP  = new GridBagConstraints(); dLP.insets=new Insets(2,4,2,4);  dLP.anchor=GridBagConstraints.WEST;  dLP.gridx=0;  dLP.gridy=1;  dLP.fill=GridBagConstraints.NONE;       dLP.weightx=0;
        GridBagConstraints dFP  = new GridBagConstraints(); dFP.insets=new Insets(2,4,2,4);  dFP.anchor=GridBagConstraints.WEST;  dFP.gridx=1;  dFP.gridy=1;  dFP.fill=GridBagConstraints.HORIZONTAL; dFP.weightx=1;
        GridBagConstraints dLB  = new GridBagConstraints(); dLB.insets=new Insets(2,4,2,4);  dLB.anchor=GridBagConstraints.WEST;  dLB.gridx=0;  dLB.gridy=2;  dLB.fill=GridBagConstraints.NONE;       dLB.weightx=0;
        GridBagConstraints dFB  = new GridBagConstraints(); dFB.insets=new Insets(2,4,2,4);  dFB.anchor=GridBagConstraints.WEST;  dFB.gridx=1;  dFB.gridy=2;  dFB.fill=GridBagConstraints.HORIZONTAL; dFB.weightx=1;
        GridBagConstraints dLB2 = new GridBagConstraints(); dLB2.insets=new Insets(2,4,2,4); dLB2.anchor=GridBagConstraints.WEST; dLB2.gridx=0; dLB2.gridy=3; dLB2.fill=GridBagConstraints.NONE;       dLB2.weightx=0;
        GridBagConstraints dFB2 = new GridBagConstraints(); dFB2.insets=new Insets(2,4,2,4); dFB2.anchor=GridBagConstraints.WEST; dFB2.gridx=1; dFB2.gridy=3; dFB2.fill=GridBagConstraints.HORIZONTAL; dFB2.weightx=1;
        GridBagConstraints dLB3 = new GridBagConstraints(); dLB3.insets=new Insets(2,4,2,4); dLB3.anchor=GridBagConstraints.WEST; dLB3.gridx=0; dLB3.gridy=4; dLB3.fill=GridBagConstraints.NONE;       dLB3.weightx=0;
        GridBagConstraints dFB3 = new GridBagConstraints(); dFB3.insets=new Insets(2,4,2,4); dFB3.anchor=GridBagConstraints.WEST; dFB3.gridx=1; dFB3.gridy=4; dFB3.fill=GridBagConstraints.HORIZONTAL; dFB3.weightx=1;
        GridBagConstraints dLB4 = new GridBagConstraints(); dLB4.insets=new Insets(2,4,2,4); dLB4.anchor=GridBagConstraints.WEST; dLB4.gridx=0; dLB4.gridy=5; dLB4.fill=GridBagConstraints.NONE;       dLB4.weightx=0;
        GridBagConstraints dFB4 = new GridBagConstraints(); dFB4.insets=new Insets(2,4,2,4); dFB4.anchor=GridBagConstraints.WEST; dFB4.gridx=1; dFB4.gridy=5; dFB4.fill=GridBagConstraints.HORIZONTAL; dFB4.weightx=1;
        GridBagConstraints dLU  = new GridBagConstraints(); dLU.insets=new Insets(2,4,2,4);  dLU.anchor=GridBagConstraints.WEST;  dLU.gridx=0;  dLU.gridy=6;  dLU.fill=GridBagConstraints.NONE;       dLU.weightx=0;
        GridBagConstraints dFU  = new GridBagConstraints(); dFU.insets=new Insets(2,4,2,4);  dFU.anchor=GridBagConstraints.WEST;  dFU.gridx=1;  dFU.gridy=6;  dFU.fill=GridBagConstraints.HORIZONTAL; dFU.weightx=1;
        GridBagConstraints dLPw = new GridBagConstraints(); dLPw.insets=new Insets(2,4,2,4); dLPw.anchor=GridBagConstraints.WEST; dLPw.gridx=0; dLPw.gridy=7; dLPw.fill=GridBagConstraints.NONE;       dLPw.weightx=0;
        GridBagConstraints dFPw = new GridBagConstraints(); dFPw.insets=new Insets(2,4,2,4); dFPw.anchor=GridBagConstraints.WEST; dFPw.gridx=1; dFPw.gridy=7; dFPw.fill=GridBagConstraints.HORIZONTAL; dFPw.weightx=1;

        panel.add(lblDestHost,    dLH);
        panel.add(txtDestinoHost, dFH);
        panel.add(lblDestPort,    dLP);
        panel.add(txtDestinoPort, dFP);
        panel.add(lblDestBD,      dLB);
        panel.add(rbBrcAnt,       dFB);
        panel.add(lblDestBD2,     dLB2);
        panel.add(rbBasAnt,       dFB2);
        panel.add(lblDestBD3,     dLB3);
        panel.add(rbBrcAct,       dFB3);
        panel.add(lblDestBD4,     dLB4);
        panel.add(rbBasAct,       dFB4);
        panel.add(lblDestUser,    dLU);
        panel.add(txtDestinoUser, dFU);
        panel.add(lblDestPass,    dLPw);
        panel.add(txtDestinoPass, dFPw);

        return panel;
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
    }
}