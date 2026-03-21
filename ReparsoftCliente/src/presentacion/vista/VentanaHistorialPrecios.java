package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingConstants;

import VistaPropias.CellRendererTablaListado;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.util.Enumeration;

public class VentanaHistorialPrecios extends JFrame {

    private static final long serialVersionUID = 1L;

    // ===== COMPONENTES PRINCIPALES =====
    private JPanel contentPane;
    private JPanel panelFiltros;
    private JPanel panelTabla;
    private JPanel panelDetalle;

    // ===== FILTROS =====
    private JTextField txtBuscar;
    private JLabel lblBuscar;
    private JLabel lblFiltrarPor;
    private JRadioButton rdbNombreEquipo;
    private JRadioButton rdbMarca;
    private JRadioButton rdbModelo;
    private ButtonGroup grupoFiltros;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    // ===== TABLA =====
    private JTable tablaHistorial;
    private DefaultTableModel modelHistorial;
    private JScrollPane spHistorial;

    private String[] nombreColumnas = {
        "ELS", "EQUIPO", "MARCA", "MODELO", "FECHA DIAGNÓSTICO", "PRECIO $", "PRECIO U$S"
    };

    // ===== DETALLE (fila seleccionada) =====
    private JTextField txtELS;
    private JTextField txtEquipo;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtFecha;
    private JTextField txtPrecioPeso;
    private JTextField txtPrecioDolar;

    // ===== BOTONES ACCIÓN =====
    private JButton btnUsarPrecios;
    private JButton btnCerrar;

    // ===== CONSTRUCTOR =====
    public VentanaHistorialPrecios() {
        super();
        setResizable(false);
        setTitle("Historial de Precios");

        // Ícono igual al resto de ventanas del sistema
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
        this.setIconImage(icon);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 820, 620);
        this.setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setAutoscrolls(true);
        contentPane.setAlignmentY(Component.TOP_ALIGNMENT);
        contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ─────────────────────────────────────────────────
        // PANEL PRINCIPAL (fondo inactiveCaption igual a VentanaClientes)
        // ─────────────────────────────────────────────────
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(SystemColor.inactiveCaption);
        panelPrincipal.setAlignmentY(Component.TOP_ALIGNMENT);
        panelPrincipal.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPrincipal.setBounds(0, 0, 804, 581);
        contentPane.add(panelPrincipal);
        panelPrincipal.setLayout(null);

        // Título
        JLabel lblTitulo = new JLabel("HISTORIAL DE PRECIOS");
        lblTitulo.setFont(new Font("Cambria", Font.BOLD, 22));
        lblTitulo.setBounds(20, 18, 350, 31);
        panelPrincipal.add(lblTitulo);

        // ─────────────────────────────────────────────────
        // PANEL FILTROS  (mismo estilo panel_2 de VentanaClientes: #778899)
        // ─────────────────────────────────────────────────
        panelFiltros = new JPanel();
        panelFiltros.setBackground(new Color(119, 136, 153));
        panelFiltros.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panelFiltros.setBounds(20, 60, 764, 85);
        panelPrincipal.add(panelFiltros);
        panelFiltros.setLayout(null);

        lblFiltrarPor = new JLabel("FILTRAR POR:");
        lblFiltrarPor.setFont(new Font("Cambria", Font.BOLD, 12));
        lblFiltrarPor.setForeground(new Color(240, 240, 240));
        lblFiltrarPor.setBounds(10, 10, 100, 20);
        panelFiltros.add(lblFiltrarPor);

        rdbNombreEquipo = new JRadioButton("NOMBRE DE EQUIPO");
        rdbNombreEquipo.setFont(new Font("Cambria", Font.BOLD, 12));
        rdbNombreEquipo.setBackground(new Color(119, 136, 153));
        rdbNombreEquipo.setForeground(new Color(240, 240, 240));
        rdbNombreEquipo.setBounds(10, 33, 180, 20);
        panelFiltros.add(rdbNombreEquipo);

        rdbMarca = new JRadioButton("MARCA");
        rdbMarca.setFont(new Font("Cambria", Font.BOLD, 12));
        rdbMarca.setBackground(new Color(119, 136, 153));
        rdbMarca.setForeground(new Color(240, 240, 240));
        rdbMarca.setBounds(200, 33, 90, 20);
        panelFiltros.add(rdbMarca);

        rdbModelo = new JRadioButton("MODELO");
        rdbModelo.setFont(new Font("Cambria", Font.BOLD, 12));
        rdbModelo.setBackground(new Color(119, 136, 153));
        rdbModelo.setForeground(new Color(240, 240, 240));
        rdbModelo.setBounds(300, 33, 90, 20);
        panelFiltros.add(rdbModelo);

        grupoFiltros = new ButtonGroup();
        grupoFiltros.add(rdbNombreEquipo);
        grupoFiltros.add(rdbMarca);
        grupoFiltros.add(rdbModelo);

        // Selección por defecto
        rdbNombreEquipo.setSelected(true);

        lblBuscar = new JLabel("BUSCAR:");
        lblBuscar.setFont(new Font("Cambria", Font.BOLD, 12));
        lblBuscar.setForeground(new Color(240, 240, 240));
        lblBuscar.setBounds(10, 58, 70, 20);
        panelFiltros.add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Cambria", Font.PLAIN, 12));
        txtBuscar.setBackground(new Color(240, 240, 240));
        txtBuscar.setBounds(80, 58, 440, 20);
        panelFiltros.add(txtBuscar);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBuscar.setFont(new Font("Cambria", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(240, 240, 240));
        btnBuscar.setBounds(534, 55, 100, 25);
        panelFiltros.add(btnBuscar);

        btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLimpiar.setFont(new Font("Cambria", Font.BOLD, 12));
        btnLimpiar.setBackground(new Color(240, 240, 240));
        btnLimpiar.setBounds(644, 55, 100, 25);
        panelFiltros.add(btnLimpiar);

        // ─────────────────────────────────────────────────
        // PANEL TABLA  (scroll con tabla de resultados)
        // ─────────────────────────────────────────────────
        panelTabla = new JPanel();
        panelTabla.setBackground(new Color(119, 136, 153));
        panelTabla.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panelTabla.setBounds(20, 155, 764, 250);
        panelPrincipal.add(panelTabla);
        panelTabla.setLayout(null);

        JLabel lblResultados = new JLabel("RESULTADOS");
        lblResultados.setFont(new Font("Cambria", Font.BOLD, 13));
        lblResultados.setForeground(new Color(240, 240, 240));
        lblResultados.setBounds(8, 5, 130, 20);
        panelTabla.add(lblResultados);

        // Configurar el look antes de crear la tabla
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Font fuenteCabecera = new Font("Cambria", Font.BOLD, 13);

        modelHistorial = new DefaultTableModel(new Object[][] {}, nombreColumnas) {
            private static final long serialVersionUID = 1L;

            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] {
                Integer.class,   // ELS
                String.class,    // EQUIPO
                String.class,    // MARCA
                String.class,    // MODELO
                String.class,    // FECHA DIAGNÓSTICO
                Double.class,    // PRECIO $
                Double.class     // PRECIO U$S
            };

            @SuppressWarnings({ "unchecked", "rawtypes" })
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };

        tablaHistorial = new JTable(modelHistorial);

        tablaHistorial.getTableHeader().setForeground(Color.BLACK);
        tablaHistorial.getTableHeader().setFont(fuenteCabecera);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
        tablaHistorial.getTableHeader().setBackground(new Color(169, 169, 169));

        ((DefaultTableCellRenderer) tablaHistorial.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        tablaHistorial.setShowGrid(true);
        tablaHistorial.setRowHeight(22);
        tablaHistorial.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Anchos de columnas
        tablaHistorial.getColumnModel().getColumn(0).setPreferredWidth(50);   // ELS
        tablaHistorial.getColumnModel().getColumn(1).setPreferredWidth(180);  // EQUIPO
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(110);  // MARCA
        tablaHistorial.getColumnModel().getColumn(3).setPreferredWidth(110);  // MODELO
        tablaHistorial.getColumnModel().getColumn(4).setPreferredWidth(110);  // FECHA
        tablaHistorial.getColumnModel().getColumn(5).setPreferredWidth(100);  // PRECIO $
        tablaHistorial.getColumnModel().getColumn(6).setPreferredWidth(100);  // PRECIO U$S

        tablaHistorial.setAutoCreateColumnsFromModel(false);

        spHistorial = new JScrollPane(tablaHistorial);
        spHistorial.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        spHistorial.setBounds(8, 28, 748, 212);
        panelTabla.add(spHistorial);

        // Restaurar look del sistema
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // ─────────────────────────────────────────────────
        // PANEL DETALLE  (mismos estilos que panel_1 de VentanaClientes)
        // ─────────────────────────────────────────────────
        panelDetalle = new JPanel();
        panelDetalle.setBackground(new Color(119, 136, 153));
        panelDetalle.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panelDetalle.setBounds(20, 415, 764, 150);
        panelPrincipal.add(panelDetalle);
        panelDetalle.setLayout(null);

        JLabel lblDetalle = new JLabel("DETALLE DEL REGISTRO SELECCIONADO");
        lblDetalle.setFont(new Font("Cambria", Font.BOLD, 13));
        lblDetalle.setForeground(new Color(240, 240, 240));
        lblDetalle.setBounds(8, 5, 300, 20);
        panelDetalle.add(lblDetalle);

        // --- ELS ---
        JLabel lblELS = new JLabel("ELS");
        lblELS.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
        lblELS.setFont(new Font("Cambria", Font.BOLD, 12));
        lblELS.setForeground(new Color(240, 240, 240));
        lblELS.setBounds(8, 32, 80, 20);
        panelDetalle.add(lblELS);

        txtELS = new JTextField();
        txtELS.setEditable(false);
        txtELS.setBackground(new Color(240, 240, 240));
        txtELS.setFont(new Font("Cambria", Font.BOLD, 12));
        txtELS.setBounds(95, 32, 60, 20);
        panelDetalle.add(txtELS);

        // --- EQUIPO ---
        JLabel lblEquipo = new JLabel("EQUIPO");
        lblEquipo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
        lblEquipo.setFont(new Font("Cambria", Font.BOLD, 12));
        lblEquipo.setForeground(new Color(240, 240, 240));
        lblEquipo.setBounds(170, 32, 80, 20);
        panelDetalle.add(lblEquipo);

        txtEquipo = new JTextField();
        txtEquipo.setEditable(false);
        txtEquipo.setBackground(new Color(240, 240, 240));
        txtEquipo.setFont(new Font("Cambria", Font.PLAIN, 12));
        txtEquipo.setBounds(258, 32, 180, 20);
        panelDetalle.add(txtEquipo);

        // --- MARCA ---
        JLabel lblMarca = new JLabel("MARCA");
        lblMarca.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
        lblMarca.setFont(new Font("Cambria", Font.BOLD, 12));
        lblMarca.setForeground(new Color(240, 240, 240));
        lblMarca.setBounds(8, 60, 80, 20);
        panelDetalle.add(lblMarca);

        txtMarca = new JTextField();
        txtMarca.setEditable(false);
        txtMarca.setBackground(new Color(240, 240, 240));
        txtMarca.setFont(new Font("Cambria", Font.PLAIN, 12));
        txtMarca.setBounds(95, 60, 150, 20);
        panelDetalle.add(txtMarca);

        // --- MODELO ---
        JLabel lblModelo = new JLabel("MODELO");
        lblModelo.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
        lblModelo.setFont(new Font("Cambria", Font.BOLD, 12));
        lblModelo.setForeground(new Color(240, 240, 240));
        lblModelo.setBounds(260, 60, 80, 20);
        panelDetalle.add(lblModelo);

        txtModelo = new JTextField();
        txtModelo.setEditable(false);
        txtModelo.setBackground(new Color(240, 240, 240));
        txtModelo.setFont(new Font("Cambria", Font.PLAIN, 12));
        txtModelo.setBounds(348, 60, 150, 20);
        panelDetalle.add(txtModelo);

        // --- FECHA DIAGNÓSTICO ---
        JLabel lblFecha = new JLabel("FECHA DIAG.");
        lblFecha.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
        lblFecha.setFont(new Font("Cambria", Font.BOLD, 12));
        lblFecha.setForeground(new Color(240, 240, 240));
        lblFecha.setBounds(8, 88, 85, 20);
        panelDetalle.add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setEditable(false);
        txtFecha.setBackground(new Color(240, 240, 240));
        txtFecha.setFont(new Font("Cambria", Font.PLAIN, 12));
        txtFecha.setBounds(95, 88, 100, 20);
        panelDetalle.add(txtFecha);

        // --- PRECIO $ ---
        JLabel lblPrecioPeso = new JLabel("PRECIO $");
        lblPrecioPeso.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
        lblPrecioPeso.setFont(new Font("Cambria", Font.BOLD, 12));
        lblPrecioPeso.setForeground(new Color(240, 240, 240));
        lblPrecioPeso.setBounds(8, 116, 80, 20);
        panelDetalle.add(lblPrecioPeso);

        txtPrecioPeso = new JTextField();
        txtPrecioPeso.setEditable(false);
        txtPrecioPeso.setBackground(new Color(240, 240, 240));
        txtPrecioPeso.setFont(new Font("Cambria", Font.BOLD, 12));
        txtPrecioPeso.setForeground(new Color(0, 100, 0));
        txtPrecioPeso.setBounds(95, 116, 130, 20);
        panelDetalle.add(txtPrecioPeso);

        // --- PRECIO U$S ---
        JLabel lblPrecioDolar = new JLabel("PRECIO U$S");
        lblPrecioDolar.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(47, 79, 79)));
        lblPrecioDolar.setFont(new Font("Cambria", Font.BOLD, 12));
        lblPrecioDolar.setForeground(new Color(240, 240, 240));
        lblPrecioDolar.setBounds(240, 116, 100, 20);
        panelDetalle.add(lblPrecioDolar);

        txtPrecioDolar = new JTextField();
        txtPrecioDolar.setEditable(false);
        txtPrecioDolar.setBackground(new Color(240, 240, 240));
        txtPrecioDolar.setFont(new Font("Cambria", Font.BOLD, 12));
        txtPrecioDolar.setForeground(new Color(0, 0, 160));
        txtPrecioDolar.setBounds(348, 116, 130, 20);
        panelDetalle.add(txtPrecioDolar);

        // --- BOTÓN USAR PRECIOS ---
        btnUsarPrecios = new JButton("<html><center>USAR ESTOS PRECIOS</center></html>");
        btnUsarPrecios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnUsarPrecios.setFont(new Font("Cambria", Font.BOLD, 13));
        btnUsarPrecios.setBackground(new Color(240, 240, 240));
        btnUsarPrecios.setBounds(530, 88, 210, 50);
        panelDetalle.add(btnUsarPrecios);

        // --- BOTÓN CERRAR ---
        btnCerrar = new JButton("<html><center>CERRAR</center></html>");
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.setFont(new Font("Cambria", Font.BOLD, 13));
        btnCerrar.setBackground(new Color(240, 240, 240));
        btnCerrar.setBounds(530, 32, 210, 40);
        panelDetalle.add(btnCerrar);

        this.setVisible(true);
    }

    // ═══════════════════════════════════════════════════════
    //  GETTERS Y SETTERS
    // ═══════════════════════════════════════════════════════

    // --- Filtros ---
    public JTextField getTxtBuscar() { return txtBuscar; }
    public void setTxtBuscar(JTextField txtBuscar) { this.txtBuscar = txtBuscar; }

    public JRadioButton getRdbNombreEquipo() { return rdbNombreEquipo; }
    public JRadioButton getRdbMarca()        { return rdbMarca;        }
    public JRadioButton getRdbModelo()       { return rdbModelo;       }

    public ButtonGroup getGrupoFiltros() { return grupoFiltros; }

    public JButton getBtnBuscar()  { return btnBuscar;  }
    public JButton getBtnLimpiar() { return btnLimpiar; }

    // --- Tabla ---
    public JTable getTablaHistorial()          { return tablaHistorial;  }
    public DefaultTableModel getModelHistorial(){ return modelHistorial;  }
    public String[] getNombreColumnas()         { return nombreColumnas;  }

    // --- Detalle ---
    public JTextField getTxtELS()        { return txtELS;        }
    public JTextField getTxtEquipo()     { return txtEquipo;     }
    public JTextField getTxtMarca()      { return txtMarca;      }
    public JTextField getTxtModelo()     { return txtModelo;     }
    public JTextField getTxtFecha()      { return txtFecha;      }
    public JTextField getTxtPrecioPeso() { return txtPrecioPeso; }
    public JTextField getTxtPrecioDolar(){ return txtPrecioDolar;}

    public void setTxtELS(String valor)         { txtELS.setText(valor);         }
    public void setTxtEquipo(String valor)      { txtEquipo.setText(valor);      }
    public void setTxtMarca(String valor)       { txtMarca.setText(valor);       }
    public void setTxtModelo(String valor)      { txtModelo.setText(valor);      }
    public void setTxtFecha(String valor)       { txtFecha.setText(valor);       }
    public void setTxtPrecioPeso(String valor)  { txtPrecioPeso.setText(valor);  }
    public void setTxtPrecioDolar(String valor) { txtPrecioDolar.setText(valor); }

    // --- Botones de acción ---
    public JButton getBtnUsarPrecios() { return btnUsarPrecios; }
    public JButton getBtnCerrar()      { return btnCerrar;      }

    // ═══════════════════════════════════════════════════════
    //  UTILIDADES
    // ═══════════════════════════════════════════════════════

    /**
     * Aplica el CellRenderer a todas las columnas de la tabla.
     * Se llamará desde el controlador al cargar datos.
     */
    public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            renderer.setFont(new Font("Cambria", Font.PLAIN, 12));
            tc.setCellRenderer(renderer);
        }
    }

    /**
     * Limpia el panel de detalle.
     * Útil para llamar desde el controlador cuando no hay fila seleccionada.
     */
    public void limpiarDetalle() {
        txtELS.setText("");
        txtEquipo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtFecha.setText("");
        txtPrecioPeso.setText("");
        txtPrecioDolar.setText("");
    }
}
