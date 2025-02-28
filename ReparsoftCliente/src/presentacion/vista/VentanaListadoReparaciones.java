package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import VistaPropias.CellRendererTablaListado;
import presentacion.controlador.ControladorListados;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

public class VentanaListadoReparaciones extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tblListado;
    private DefaultTableModel modelReparaciones;

    private String[] nombreColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO",
            "N° SERIE", "AVISO", "REVISIÓN", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS", "TÉCNICO",
            "UBIC. REM", "NUM REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO", "INGRESO" };
    private JButton btnMax;
    public static int est;

    private JPanel panelPrincipal;
    private JPanel panelFiltros;
    private JPanel panelSuperior;
    private JPanel panelTitulo;
    private JPanel panelInferior;
    private JPanel panelCentral;
    private JScrollPane scrollPane;

    @SuppressWarnings("unused")
    private ControladorListados controlador;

    private JComboBox<?> comboFiltroMarca;
    private JComboBox<?> comboFiltroCliente;
    private JComboBox<?> comboFiltroSucursal;
    private JComboBox<?> comboFiltroEstadoFis;
    private JComboBox<?> comboFiltroEstadoCom;
    private JComboBox<?> comboFiltroEstadoTec;
    private JComboBox<?> comboFiltroELS;
    private JComboBox<?> comboFiltroEquipo;
    private JComboBox<String> comboFiltroModelo;
    private JComboBox<?> comboFiltroAviso;
    private JComboBox<?> comboFiltroTecnico;
	
    private JRadioButton radioButtonMarca;
    private JRadioButton radioButtonCliente;
    private JRadioButton radioButtonSucursal;
    private JRadioButton radioButtonELS;
    private JRadioButton radioButtonEstadoFis;
    private JRadioButton radioButtonEstadoCom;
    private JRadioButton radioButtonEstadoTec;
    private JRadioButton radioButtonEquipo;
    private JRadioButton radioButtonModelo;
    private JRadioButton radioButtonAviso;
    private JRadioButton radioButtonPresupGenerado;
    private JRadioButton radioButtonPresupEnviado;
    private JRadioButton radioButtonTecnico;

    private JCheckBox chckbxPresupuestoGenerado;
    private JCheckBox chckbxPresupuestoEnviado;

    private JPanel panelBotonera;
    private JPanel panelBotones;
    private JButton btnFiltrar;
    private JButton btnMostrarTodo;
    private JButton btnEstadisticas;
    private JPanel panelColumnas;
    private JCheckBox chckbxSucursal;
    private JCheckBox chckbxModelo;
    private JCheckBox chckbxRevisión;
    private JCheckBox chckbxEntrada;
    private JCheckBox chckbxEquipo;
    private JCheckBox chckbxELS;
    private JCheckBox chckbxClienteCliente;
    private JCheckBox chckbxSerie;
    private JCheckBox chckbxCliente;
    private JCheckBox chckbxMarca;
    private JCheckBox chckbxAviso;
    private JCheckBox chckbxEstadoTec;
    private JCheckBox chckbxEstadoCom;
    private JCheckBox chckbxEstadoFis;
    private JCheckBox chckbxTecnico;
    private JCheckBox chckbxUbicacionRemito;
    private JCheckBox chckbxNumeroRemito;
    private JCheckBox chckbxPresupuestoGeneradoColumna;
    private JCheckBox chckbxPresupuestoEnviadoColumna;
    private JCheckBox chckbxIngreso;
    private JCheckBox chckbxPrecioDolar;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private JLabel lblNewLabel_5;
    private JLabel lblNewLabel_6;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_7;
    private JLabel lblNewLabel_10;
    private JComboBox<Object> comboFiltroIngreso;
    private JRadioButton radioButtonIngreso;
    private JCheckBox chckbxPago;
    private JCheckBox chckbxPrecioPeso;

    protected void this_windowOpened(WindowEvent e) {
        centrarVentana();
    }

    private void centrarVentana() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana = getSize();
        setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
    }

    @SuppressWarnings("serial")
    public VentanaListadoReparaciones(ControladorListados controlador) {
        super();
        this.controlador = controlador;

        this.this_windowOpened(null);
        setSize(1200, 680);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout(0, 0));

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new LineBorder(new Color(0, 128, 128)));
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.setLayout(new BorderLayout(0, 0));

        panelSuperior = new JPanel();
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelSuperior.setLayout(new BorderLayout(0, 0));

        panelFiltros = new JPanel();
        panelFiltros.setFont(new Font("Cambria", Font.PLAIN, 10));
        panelFiltros.setBackground(new Color(176, 196, 222));
        //panelFiltros.setPreferredSize(new Dimension(200, 15));
        panelFiltros.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128)), new EmptyBorder(10, 10, 10, 10)));
        panelSuperior.add(panelFiltros, BorderLayout.CENTER);
        panelFiltros.setLayout(new GridLayout(5, 9, 1, 2)); // Ajustado para más espacio

        // Filtros
        JLabel lblCliente = new JLabel("CLIENTE");    
        comboFiltroCliente = new JComboBox<>();        
        radioButtonCliente = new JRadioButton("");
        darFormatoFiltros(lblCliente, comboFiltroCliente, radioButtonCliente);  
        panelFiltros.add(lblCliente);
        panelFiltros.add(comboFiltroCliente);
        panelFiltros.add(radioButtonCliente);

        
        JLabel lblEquipo = new JLabel("EQUIPO");
        comboFiltroEquipo = new JComboBox<>();
        radioButtonEquipo = new JRadioButton("");
        darFormatoFiltros(lblEquipo, comboFiltroEquipo, radioButtonEquipo);  
        panelFiltros.add(lblEquipo);
        panelFiltros.add(comboFiltroEquipo);
        panelFiltros.add(radioButtonEquipo);
        

        JLabel lblMarca = new JLabel("MARCA");
        comboFiltroMarca = new JComboBox<>();
        radioButtonMarca = new JRadioButton("");
        darFormatoFiltros(lblMarca, comboFiltroMarca, radioButtonMarca);  
        panelFiltros.add(lblMarca);
        panelFiltros.add(comboFiltroMarca);
        panelFiltros.add(radioButtonMarca);
        
 

        JLabel lblSucursal = new JLabel("SUCURSAL");
        comboFiltroSucursal = new JComboBox<>();
        radioButtonSucursal = new JRadioButton("");
        darFormatoFiltros(lblSucursal, comboFiltroSucursal, radioButtonSucursal);  
        panelFiltros.add(lblSucursal);
        panelFiltros.add(comboFiltroSucursal);
        panelFiltros.add(radioButtonSucursal);
        
        
        
        JLabel lblEstadoTec = new JLabel("ESTADO TEC");
        comboFiltroEstadoTec = new JComboBox<>();
        radioButtonEstadoTec = new JRadioButton("");
        darFormatoFiltros(lblEstadoTec, comboFiltroEstadoTec, radioButtonEstadoTec);  
        panelFiltros.add(lblEstadoTec);
        panelFiltros.add(comboFiltroEstadoTec);
        panelFiltros.add(radioButtonEstadoTec);


        JLabel lblEstadoCom = new JLabel("ESTADO COM");
        comboFiltroEstadoCom = new JComboBox<>();
        radioButtonEstadoCom = new JRadioButton("");
        darFormatoFiltros(lblEstadoCom, comboFiltroEstadoCom, radioButtonEstadoCom);  
        panelFiltros.add(lblEstadoCom);
        panelFiltros.add(comboFiltroEstadoCom);
        panelFiltros.add(radioButtonEstadoCom);
 
        
        JLabel lblEstadoFis = new JLabel("ESTADO FIS");
        comboFiltroEstadoFis = new JComboBox<>();
        radioButtonEstadoFis = new JRadioButton("");
        darFormatoFiltros(lblEstadoFis, comboFiltroEstadoFis, radioButtonEstadoFis);  
        panelFiltros.add(lblEstadoFis);
        panelFiltros.add(comboFiltroEstadoFis);
        panelFiltros.add(radioButtonEstadoFis);

        
        JLabel lblELS = new JLabel("ELS");
        comboFiltroELS = new JComboBox<>();
        radioButtonELS = new JRadioButton("");
        darFormatoFiltros(lblELS, comboFiltroELS, radioButtonELS);  
        panelFiltros.add(lblELS);
        panelFiltros.add(comboFiltroELS);
        panelFiltros.add(radioButtonELS);
        
        
        
        
        JLabel lblTecnico = new JLabel("TÉCNICO");
        comboFiltroTecnico = new JComboBox<>();
        radioButtonTecnico = new JRadioButton("");
        darFormatoFiltros(lblTecnico, comboFiltroTecnico, radioButtonTecnico);  
        panelFiltros.add(lblTecnico);
        panelFiltros.add(comboFiltroTecnico);
        panelFiltros.add(radioButtonTecnico);
        
        
        JLabel lblAviso = new JLabel("AVISO");
        comboFiltroAviso = new JComboBox<>();
        radioButtonAviso = new JRadioButton("");
        darFormatoFiltros(lblAviso, comboFiltroAviso, radioButtonAviso);  
        panelFiltros.add(lblAviso);
        panelFiltros.add(comboFiltroAviso);
        panelFiltros.add(radioButtonAviso);

        JLabel lblModelo = new JLabel("AVISO");
        comboFiltroModelo = new JComboBox<>();
        radioButtonModelo = new JRadioButton("");
        darFormatoFiltros(lblModelo, comboFiltroModelo, radioButtonModelo);  
        panelFiltros.add(lblModelo);
        panelFiltros.add(comboFiltroModelo);
        panelFiltros.add(radioButtonModelo);
        
        JLabel lblIngreso = new JLabel("INGRESO");
        comboFiltroIngreso = new JComboBox<>();
        radioButtonIngreso = new JRadioButton("");
        darFormatoFiltros(lblIngreso, comboFiltroIngreso, radioButtonIngreso);  
        panelFiltros.add(lblIngreso);
        panelFiltros.add(comboFiltroIngreso);
        panelFiltros.add(radioButtonIngreso);
        
 
        JLabel lblPresupuestoEnv = new JLabel("PRESUPUESTO ENV");
        chckbxPresupuestoEnviado = new JCheckBox();
        chckbxPresupuestoEnviado.setMargin(new Insets(0, 0, 0, 0));
        chckbxPresupuestoEnviado.setAlignmentY(0.0f);
        chckbxPresupuestoEnviado.setHorizontalTextPosition(SwingConstants.LEFT);
        chckbxPresupuestoEnviado.setIgnoreRepaint(true);
        chckbxPresupuestoEnviado.setHorizontalAlignment(SwingConstants.LEFT);
        chckbxPresupuestoEnviado.setEnabled(false);
        chckbxPresupuestoEnviado.setBackground(new Color(176, 196, 222));
        radioButtonPresupEnviado = new JRadioButton("");
        darFormatoFiltros(lblPresupuestoEnv, null, radioButtonPresupEnviado);  
        panelFiltros.add(lblPresupuestoEnv);
        panelFiltros.add(chckbxPresupuestoEnviado);
        panelFiltros.add(radioButtonPresupEnviado);
		
        
        
        JLabel lblPresupuestoGen = new JLabel("PRESUPUESTO GEN");
        chckbxPresupuestoGenerado = new JCheckBox();
        chckbxPresupuestoGenerado.setMargin(new Insets(0, 0, 0, 0));
        chckbxPresupuestoGenerado.setEnabled(false);
        chckbxPresupuestoGenerado.setBackground(new Color(176, 196, 222));
        radioButtonPresupGenerado = new JRadioButton("");
        darFormatoFiltros(lblPresupuestoGen, null, radioButtonPresupGenerado);  
        panelFiltros.add(lblPresupuestoGen);
        panelFiltros.add(chckbxPresupuestoGenerado);
        panelFiltros.add(radioButtonPresupGenerado);
        
      	

        panelTitulo = new JPanel();
        panelTitulo.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 128, 128)));
        panelTitulo.setBackground(new Color(176, 196, 222));
        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelTitulo.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));

        JLabel lbTitulo_1 = new JLabel("LISTADO DE EQUIPOS");
        lbTitulo_1.setHorizontalTextPosition(SwingConstants.CENTER);
        lbTitulo_1.setHorizontalAlignment(SwingConstants.LEFT);
        lbTitulo_1.setFont(new Font("Cambria", Font.BOLD, 30));
        panelTitulo.add(lbTitulo_1);

        btnMax = new JButton("");
        btnMax.setVisible(false);
        btnMax.setPreferredSize(new Dimension(50, 30));
        btnMax.setFont(new Font("Cambria", Font.BOLD, 14));
        btnMax.setIcon(new ImageIcon(this.getClass().getResource("/maximizar.png")));
        panelTitulo.add(btnMax);

        panelBotonera = new JPanel();
        panelSuperior.add(panelBotonera, BorderLayout.SOUTH);
        panelBotonera.setLayout(new BorderLayout(0, 0));

        panelBotones = new JPanel();
        panelBotones.setBorder(new LineBorder(new Color(0, 128, 128)));
        panelBotones.setBackground(new Color(176, 196, 222));
        panelBotonera.add(panelBotones, BorderLayout.SOUTH);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 190, 5));

        btnFiltrar = new JButton("FILTRAR");
        btnFiltrar.setPreferredSize(new Dimension(150, 30));
        btnFiltrar.setFont(new Font("Cambria", Font.BOLD, 14));
        panelBotones.add(btnFiltrar);

        btnMostrarTodo = new JButton("MOSTRAR TODO");
        btnMostrarTodo.setPreferredSize(new Dimension(150, 30));
        btnMostrarTodo.setFont(new Font("Cambria", Font.BOLD, 14));
        panelBotones.add(btnMostrarTodo);

        btnEstadisticas = new JButton("ESTADÍSTICAS");
        btnEstadisticas.setPreferredSize(new Dimension(150, 30));
        btnEstadisticas.setFont(new Font("Cambria", Font.BOLD, 14));
        panelBotones.add(btnEstadisticas);

        panelColumnas = new JPanel();
        panelColumnas.setBorder(new CompoundBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 128, 128)), new EmptyBorder(5, 5, 5, 5)));
        panelColumnas.setBackground(new Color(176, 196, 222));
        panelBotonera.add(panelColumnas, BorderLayout.NORTH);
        panelColumnas.setLayout(new GridLayout(3, 6, 5, 5));

        // Checkboxes para ocultar columnas
        chckbxELS = new JCheckBox("ELS");
        darFormatoOcultarColumnas(chckbxELS);
        panelColumnas.add(chckbxELS);
        chckbxEquipo = new JCheckBox("EQUIPO");
        darFormatoOcultarColumnas(chckbxEquipo);
        panelColumnas.add(chckbxEquipo);
        chckbxRevisión = new JCheckBox("REVISIÓN");
        darFormatoOcultarColumnas(chckbxRevisión);
        panelColumnas.add(chckbxRevisión);
        chckbxEstadoFis = new JCheckBox("ESTADO FÍS");
        darFormatoOcultarColumnas(chckbxEstadoFis);
        panelColumnas.add(chckbxEstadoFis);
        chckbxNumeroRemito = new JCheckBox("NÚMERO REMITO");
        darFormatoOcultarColumnas(chckbxNumeroRemito);
        panelColumnas.add(chckbxNumeroRemito);
        chckbxPrecioDolar = new JCheckBox("PRECIO DOLAR");
        darFormatoOcultarColumnas(chckbxPrecioDolar);
        panelColumnas.add(chckbxPrecioDolar);

        chckbxEntrada = new JCheckBox("ENTRADA");
        darFormatoOcultarColumnas(chckbxEntrada);
        panelColumnas.add(chckbxEntrada);
        chckbxMarca = new JCheckBox("MARCA");
        darFormatoOcultarColumnas(chckbxMarca);
        panelColumnas.add(chckbxMarca);
        chckbxClienteCliente = new JCheckBox("CLIENTE/CLIENTE");
        darFormatoOcultarColumnas(chckbxClienteCliente);
        panelColumnas.add(chckbxClienteCliente);
        chckbxEstadoTec = new JCheckBox("ESTADO TÉC");
        darFormatoOcultarColumnas(chckbxEstadoTec);
        panelColumnas.add(chckbxEstadoTec);
        chckbxPresupuestoGeneradoColumna = new JCheckBox("PRESUPUESTO GEN");
        darFormatoOcultarColumnas(chckbxPresupuestoGeneradoColumna);
        panelColumnas.add(chckbxPresupuestoGeneradoColumna);
        chckbxPrecioPeso = new JCheckBox("PRECIO PESO");
        darFormatoOcultarColumnas(chckbxPrecioPeso);
        panelColumnas.add(chckbxPrecioPeso);

        chckbxCliente = new JCheckBox("CLIENTE");
        darFormatoOcultarColumnas(chckbxCliente);
        panelColumnas.add(chckbxCliente);
        chckbxModelo = new JCheckBox("MODELO");
        darFormatoOcultarColumnas(chckbxModelo);
        panelColumnas.add(chckbxModelo);
        chckbxAviso = new JCheckBox("AVISO");
        darFormatoOcultarColumnas(chckbxAviso);
        panelColumnas.add(chckbxAviso);
        chckbxTecnico = new JCheckBox("TÉCNICO");
        darFormatoOcultarColumnas(chckbxTecnico);
        panelColumnas.add(chckbxTecnico);
        chckbxPresupuestoEnviadoColumna = new JCheckBox("PRESUPUESTO ENV");
        darFormatoOcultarColumnas(chckbxPresupuestoEnviadoColumna);
        panelColumnas.add(chckbxPresupuestoEnviadoColumna);
        chckbxPago = new JCheckBox("PAGO");
        darFormatoOcultarColumnas(chckbxPago);
        panelColumnas.add(chckbxPago);

        chckbxSucursal = new JCheckBox("SUCURSAL");
        darFormatoOcultarColumnas(chckbxSucursal);
        panelColumnas.add(chckbxSucursal);
        chckbxSerie = new JCheckBox("SERIE");
        darFormatoOcultarColumnas(chckbxSerie);
        panelColumnas.add(chckbxSerie);
        chckbxEstadoCom = new JCheckBox("ESTADO COM");
        darFormatoOcultarColumnas(chckbxEstadoCom);
        panelColumnas.add(chckbxEstadoCom);
        chckbxUbicacionRemito = new JCheckBox("UBICACIÓN REMITO");
        darFormatoOcultarColumnas(chckbxUbicacionRemito);
        panelColumnas.add(chckbxUbicacionRemito);
        chckbxIngreso = new JCheckBox("INGRESO");
        darFormatoOcultarColumnas(chckbxIngreso);
        panelColumnas.add(chckbxIngreso);

//        panelInferior = new JPanel();
//        panelInferior.setBackground(new Color(176, 196, 222));
//        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        panelCentral = new JPanel();
        panelCentral.setBorder(new CompoundBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 128, 128)), new EmptyBorder(3, 5, 0, 5)));
        panelCentral.setBackground(new Color(176, 196, 222));
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(new BorderLayout(0, 0));

        modelReparaciones = new DefaultTableModel(new Object[][] {}, nombreColumnas) {
            private static final long serialVersionUID = 1L;
            Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
                    String.class, String.class, String.class, String.class, String.class, String.class, String.class,
                    String.class, String.class, String.class, String.class, String.class, Boolean.class, Boolean.class,
                    double.class, double.class, double.class, String.class };

            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false, false, false, false, false, false, false };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };
        
        
        try {

			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}


        tblListado = new JTable(modelReparaciones);
        tblListado.getTableHeader().setBorder(new LineBorder(Color.GRAY));
        tblListado.setShowGrid(true);
        tblListado.setGridColor(Color.GRAY);
        tblListado.setCellSelectionEnabled(true);
        tblListado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

       
        
        scrollPane = new JScrollPane(tblListado);
        scrollPane.setViewportView(tblListado);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        int[] anchos = { 60, 80, 150, 150, 200, 100, 150, 100, 100, 80, 110, 120, 150, 100, 100, 100, 100, 80, 80, 100,
                100, 100, 80 };

        for (int i = 0; i < tblListado.getColumnCount(); i++) {
            tblListado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        this.setVisible(true);
    }

   public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaListado());
		}
	}

	public DefaultTableModel getModelReparaciones() {
		return modelReparaciones;
	}

	
	 public static void darFormatoFiltros(JLabel label, JComboBox<?> comboBox, JRadioButton radioButton) {
	        // Formatear JLabel
	        if (label != null) {
	            label.setBackground(new Color(176, 196, 222));
	            label.setHorizontalAlignment(SwingConstants.LEFT);
	            label.setFont(new Font("Cambria", Font.BOLD, 12));
	        }

	        // Formatear JComboBox
	        if (comboBox != null) {
	            comboBox.setEnabled(false);
	            comboBox.setBackground(new Color(176, 196, 222));
	            comboBox.setFont(new Font("Cambria", Font.PLAIN, 12));
	            comboBox.setPreferredSize(new Dimension(150, 15));
	        }

	        // Formatear JRadioButton
	        if (radioButton != null) {
	            radioButton.setBackground(new Color(176, 196, 222));
	            radioButton.setFont(new Font("Cambria", Font.BOLD, 12));
	            radioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	            
	        }
	    }
	
	 
	 public static void darFormatoOcultarColumnas(JCheckBox check) {
	        // Formatear JCheckBox
	        if (check != null) {
	        	check.setOpaque(false);
	        	check.setFont(new Font("Cambria", Font.BOLD, 12));
	        	check.setHorizontalAlignment(SwingConstants.LEFT);
	        	check.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        }

	       
	    }
	
	
	
	public void setModelReparaciones(DefaultTableModel modelReparaciones) {
		this.modelReparaciones = modelReparaciones;
	}

	public JTable getTblReparaciones() {
		return tblListado;
	}

	public void setTblReparaciones(JTable tblReparaciones) {
		this.tblListado = tblReparaciones;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	public JComboBox<?> getComboFiltroCliente() {
		return comboFiltroCliente;
	}

	public void setComboFiltroCliente(JComboBox<?> comboFiltroCliente) {
		this.comboFiltroCliente = comboFiltroCliente;
	}

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public void setBtnFiltrar(JButton btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	public JButton getBtnMostrarTodo() {
		return btnMostrarTodo;
	}

	public void setBtnMostrarTodo(JButton btnMostrarTodo) {
		this.btnMostrarTodo = btnMostrarTodo;
	}

	public JComboBox<?> getComboFiltroMarca() {
		return comboFiltroMarca;
	}

	public void setComboFiltroMarca(JComboBox<?> comboFiltroMarca) {
		this.comboFiltroMarca = comboFiltroMarca;
	}

	public JRadioButton getRadioButtonMarca() {
		return radioButtonMarca;
	}

	public void setRadioButtonMarca(JRadioButton radioButtonMarca) {
		this.radioButtonMarca = radioButtonMarca;
	}

	public JRadioButton Button() {
		return radioButtonCliente;
	}

	public JRadioButton getRadioButtonCliente() {
		return radioButtonCliente;
	}

	public void setRadioButtonCliente(JRadioButton radioButtonCliente) {
		this.radioButtonCliente = radioButtonCliente;
	}

	public JComboBox<?> getComboFiltroSucursal() {
		return comboFiltroSucursal;
	}

	public void setComboFiltroSucursal(JComboBox<?> comboFiltroSucursal) {
		this.comboFiltroSucursal = comboFiltroSucursal;
	}

	public JRadioButton getRadioButtonSucursal() {
		return radioButtonSucursal;
	}

	public void setRadioButtonSucursal(JRadioButton radioButtonSucursal) {
		this.radioButtonSucursal = radioButtonSucursal;
	}

	public JComboBox<?> getComboFiltroEstadoFis() {
		return comboFiltroEstadoFis;
	}

	public void setComboFiltroEstadoFis(JComboBox<?> comboFiltroEstadoFis) {
		this.comboFiltroEstadoFis = comboFiltroEstadoFis;
	}

	public JComboBox<?> getComboFiltroEstadoCom() {
		return comboFiltroEstadoCom;
	}

	public void setComboFiltroEstadoCom(JComboBox<?> comboFiltroEstadoCom) {
		this.comboFiltroEstadoCom = comboFiltroEstadoCom;
	}

	public JComboBox<?> getComboFiltroEstadoTec() {
		return comboFiltroEstadoTec;
	}

	public void setComboFiltroEstadoTec(JComboBox<?> comboFiltroEstadoTec) {
		this.comboFiltroEstadoTec = comboFiltroEstadoTec;
	}

	public JRadioButton getRadioButtonEstadoFis() {
		return radioButtonEstadoFis;
	}

	public void setRadioButtonEstadoFis(JRadioButton radioButtonEstadoFis) {
		this.radioButtonEstadoFis = radioButtonEstadoFis;
	}

	public JRadioButton getRadioButtonEstadoCom() {
		return radioButtonEstadoCom;
	}

	public void setRadioButtonEstadoCom(JRadioButton radioButtonEstadoCom) {
		this.radioButtonEstadoCom = radioButtonEstadoCom;
	}

	public JRadioButton getRadioButtonEstadoTec() {
		return radioButtonEstadoTec;
	}

	public void setRadioButtonEstadoTec(JRadioButton radioButtonEstadoTec) {
		this.radioButtonEstadoTec = radioButtonEstadoTec;
	}

	public JCheckBox getChckbxPresupuestoGenerado() {
		return chckbxPresupuestoGenerado;
	}

	public void setChckbxPresupuestoGenerado(JCheckBox chckbxPresupuestoGenerado) {
		this.chckbxPresupuestoGenerado = chckbxPresupuestoGenerado;
	}

	public JCheckBox getChckbxPresupuestoEnviado() {
		return chckbxPresupuestoEnviado;
	}

	public void setChckbxPresupuestoEnviado(JCheckBox chckbxPresupuestoEnviado) {
		this.chckbxPresupuestoEnviado = chckbxPresupuestoEnviado;
	}

	public JComboBox<?> getComboFiltroEquipo() {
		return comboFiltroEquipo;
	}

	public void setComboFiltroEquipo(JComboBox<?> comboFiltroEquipo) {
		this.comboFiltroEquipo = comboFiltroEquipo;
	}

	public JComboBox<String> getComboFiltroModelo() {
		return comboFiltroModelo;
	}

	public void setComboFiltroModelo(JComboBox<String> comboFiltroModelo) {
		this.comboFiltroModelo = comboFiltroModelo;
	}

	public JComboBox<?> getRadio() {
		return comboFiltroAviso;
	}

	public void setComboFiltroAviso(JComboBox<?> comboFiltroAviso) {
		this.comboFiltroAviso = comboFiltroAviso;
	}

	public JRadioButton getRadioButtonEquipo() {
		return radioButtonEquipo;
	}

	public void setRadioButtonEquipo(JRadioButton radioButtonEquipo) {
		this.radioButtonEquipo = radioButtonEquipo;
	}

	public JRadioButton getRadioButtonModelo() {
		return radioButtonModelo;
	}

	public void setRadioButtonModelo(JRadioButton radioButtonModelo) {
		this.radioButtonModelo = radioButtonModelo;
	}

	public JRadioButton getRadioButtonAviso() {
		return radioButtonAviso;
	}

	public void setRadioButtonAviso(JRadioButton radioButtonAviso) {
		this.radioButtonAviso = radioButtonAviso;
	}

	public JRadioButton getRadioButtonPresupGenerado() {
		return radioButtonPresupGenerado;
	}

	public void setRadioButtonPresupGenerado(JRadioButton radioButtonPresupGenerado) {
		this.radioButtonPresupGenerado = radioButtonPresupGenerado;
	}

	public JRadioButton getRadioButtonPresupEnviado() {
		return radioButtonPresupEnviado;
	}

	public void setRadioButtonPresupEnviado(JRadioButton radioButtonPresupEnviado) {
		this.radioButtonPresupEnviado = radioButtonPresupEnviado;
	}

	public JComboBox<?> getComboFiltroAviso() {
		return comboFiltroAviso;
	}

	public JComboBox<?> getComboFiltroELS() {
		return comboFiltroELS;
	}

	public void setComboFiltroELS(JComboBox<?> comboFiltroELS) {
		this.comboFiltroELS = comboFiltroELS;
	}

	public JRadioButton getRadioButtonELS() {
		return radioButtonELS;
	}

	public void setRadioButtonELS(JRadioButton radioButtonELS) {
		this.radioButtonELS = radioButtonELS;
	}

	public JComboBox<?> getComboFiltroTecnico() {
		return comboFiltroTecnico;
	}

	public void setComboFiltroTecnico(JComboBox<?> comboFiltroTecnico) {
		this.comboFiltroTecnico = comboFiltroTecnico;
	}

	public JRadioButton getRadioButtonTecnico() {
		return radioButtonTecnico;
	}

	public void setRadioButtonTecnico(JRadioButton radioButtonTecnico) {
		this.radioButtonTecnico = radioButtonTecnico;
	}

	public JButton getBtnMax() {
		return btnMax;
	}

	public void setBtnMax(JButton btnMax) {
		this.btnMax = btnMax;
	}

	public JPanel getPanel() {
		return panelPrincipal;
	}

	public void setPanel(JPanel panel) {
		this.panelPrincipal = panel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JButton getBtnEstadisticas() {
		return btnEstadisticas;
	}

	public void setBtnEstadisticas(JButton btnEstadisticas) {
		this.btnEstadisticas = btnEstadisticas;
	}

	public JCheckBox getChckbxSucursal() {
		return chckbxSucursal;
	}

	public void setChckbxSucursal(JCheckBox chckbxSucursal) {
		this.chckbxSucursal = chckbxSucursal;
	}

	public JCheckBox getChckbxModelo() {
		return chckbxModelo;
	}

	public void setChckbxModelo(JCheckBox chckbxModelo) {
		this.chckbxModelo = chckbxModelo;
	}

	public JCheckBox getChckbxRevisión() {
		return chckbxRevisión;
	}

	public void setChckbxRevisión(JCheckBox chckbxRevisión) {
		this.chckbxRevisión = chckbxRevisión;
	}

	public JCheckBox getChckbxEntrada() {
		return chckbxEntrada;
	}

	public void setChckbxEntrada(JCheckBox chckbxEntrada) {
		this.chckbxEntrada = chckbxEntrada;
	}

	public JCheckBox getChckbxEquipo() {
		return chckbxEquipo;
	}

	public void setChckbxEquipo(JCheckBox chckbxEquipo) {
		this.chckbxEquipo = chckbxEquipo;
	}

	public JCheckBox getChckbxELS() {
		return chckbxELS;
	}

	public void setChckbxELS(JCheckBox chckbxELS) {
		this.chckbxELS = chckbxELS;
	}

	public JCheckBox getChckbxClienteCliente() {
		return chckbxClienteCliente;
	}

	public void setChckbxClienteCliente(JCheckBox chckbxClienteCliente) {
		this.chckbxClienteCliente = chckbxClienteCliente;
	}

	public JCheckBox getChckbxSerie() {
		return chckbxSerie;
	}

	public void setChckbxSerie(JCheckBox chckbxSerie) {
		this.chckbxSerie = chckbxSerie;
	}

	public JCheckBox getChckbxCliente() {
		return chckbxCliente;
	}

	public void setChckbxCliente(JCheckBox chckbxCliente) {
		this.chckbxCliente = chckbxCliente;
	}

	public JCheckBox getChckbxMarca() {
		return chckbxMarca;
	}

	public void setChckbxMarca(JCheckBox chckbxMarca) {
		this.chckbxMarca = chckbxMarca;
	}

	public JCheckBox getChckbxAviso() {
		return chckbxAviso;
	}

	public void setChckbxAviso(JCheckBox chckbxAviso) {
		this.chckbxAviso = chckbxAviso;
	}

	public JCheckBox getChckbxEstadoTec() {
		return chckbxEstadoTec;
	}

	public void setChckbxEstadoTec(JCheckBox chckbxEstadoTec) {
		this.chckbxEstadoTec = chckbxEstadoTec;
	}

	public JCheckBox getChckbxEstadoCom() {
		return chckbxEstadoCom;
	}

	public void setChckbxEstadoCom(JCheckBox chckbxEstadoCom) {
		this.chckbxEstadoCom = chckbxEstadoCom;
	}

	public JCheckBox getChckbxEstadoFis() {
		return chckbxEstadoFis;
	}

	public void setChckbxEstadoFis(JCheckBox chckbxEstadoFis) {
		this.chckbxEstadoFis = chckbxEstadoFis;
	}

	public JCheckBox getChckbxTecnico() {
		return chckbxTecnico;
	}

	public void setChckbxTecnico(JCheckBox chckbxTecnico) {
		this.chckbxTecnico = chckbxTecnico;
	}

	public JCheckBox getChckbxUbicacionRemito() {
		return chckbxUbicacionRemito;
	}

	public void setChckbxUbicacionRemito(JCheckBox chckbxUbicacionRemito) {
		this.chckbxUbicacionRemito = chckbxUbicacionRemito;
	}

	public JCheckBox getChckbxNumeroRemito() {
		return chckbxNumeroRemito;
	}

	public void setChckbxNumeroRemito(JCheckBox chckbxNumeroRemito) {
		this.chckbxNumeroRemito = chckbxNumeroRemito;
	}

	public JCheckBox getChckbxPresupuestoGeneradoColumna() {
		return chckbxPresupuestoGeneradoColumna;
	}

	public void setChckbxPresupuestoGeneradoColumna(JCheckBox chckbxPresupuestoGeneradoColumna) {
		this.chckbxPresupuestoGeneradoColumna = chckbxPresupuestoGeneradoColumna;
	}

	public JCheckBox getChckbxPresupuestoEnviadoColumna() {
		return chckbxPresupuestoEnviadoColumna;
	}

	public void setChckbxPresupuestoEnviadoColumna(JCheckBox chckbxPresupuestoEnviadoColumna) {
		this.chckbxPresupuestoEnviadoColumna = chckbxPresupuestoEnviadoColumna;
	}

	public JCheckBox getChckbxPago() {
		return chckbxPago;
	}

	public void setChckbxPago(JCheckBox chckbxPago) {
		this.chckbxPago = chckbxPago;
	}

	public JCheckBox getChckbxPrecioPeso() {
		return chckbxPrecioPeso;
	}

	public void setChckbxPrecioPeso(JCheckBox chckbxPrecioPeso) {
		this.chckbxPrecioPeso = chckbxPrecioPeso;
	}

	public JCheckBox getChckbxPrecioDolar() {
		return chckbxPrecioDolar;
	}

	public void setChckbxPrecioDolar(JCheckBox chckbxPrecioDolar) {
		this.chckbxPrecioDolar = chckbxPrecioDolar;
	}

	public JTable getTblListado() {
		return tblListado;
	}

	public void setTblListado(JTable tblListado) {
		this.tblListado = tblListado;
	}

	public JCheckBox getChckbxIngreso() {
		return chckbxIngreso;
	}

	public void setChckbxIngreso(JCheckBox chckbxIngreso) {
		this.chckbxIngreso = chckbxIngreso;
	}

	public JComboBox<Object> getComboFiltroIngreso() {
		return comboFiltroIngreso;
	}

	public void setComboFiltroIngreso(JComboBox<Object> comboFiltroIngreso) {
		this.comboFiltroIngreso = comboFiltroIngreso;
	}

	public JRadioButton getRadioButtonIngreso() {
		return radioButtonIngreso;
	}

	public void setRadioButtonIngreso(JRadioButton radioButtonIngreso) {
		this.radioButtonIngreso = radioButtonIngreso;
	}

}