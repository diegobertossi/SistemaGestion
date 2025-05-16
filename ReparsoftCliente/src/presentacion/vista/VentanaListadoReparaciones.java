package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import VistaPropias.CellRendererTablaListado;
import presentacion.controlador.ControladorListados;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class VentanaListadoReparaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tblListado;
	private DefaultTableModel modelReparaciones;

	private String[] nombreColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO",
			"N° SERIE", "AVISO", "REVISIÓN", "SALIDA", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS",
			"TÉCNICO", "UBIC. REM", "NUM REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO",
			"INGRESO" };
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

	private JPanel panelBotonera;
	private JPanel panelBotones;
	private JPanel panelColumnas;
	private JCheckBox chckbxSucursal;
	private JCheckBox chckbxModelo;
	private JCheckBox chckbxRevisión;
	private JCheckBox chckbxEntrada;
	private JCheckBox chckbxSalida;
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
	private JCheckBox chckbxPago;
	private JCheckBox chckbxPrecioPeso;
	private JLabel lbTitulo;
	private JButton btnEstadisticas;

	protected void this_windowOpened(WindowEvent e) {
		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = getSize();
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}
	
	
	private JButton btnOcultarColumnas;
    private JPopupMenu popupMenu;

    // Nombres de las columnas (ajusta según tus necesidades)
    private String[] nombresColumnas = {
        "ELS", "SUCURSAL", "MODELO", "REVISIÓN", "ESTADO COM", 
        "UBICACIÓN REMITO", "PAGO", "INGRESO", "ENTRADA", "EQUIPO",
        "SERIE", "SALIDA", "ESTADO FÍS", "NÚMERO REMITO", "PRECIO PESO",
        "PRESUPUESTO GEN", "CLIENTE", "MARCA", "AVISO", "TÉCNICO",
        "ESTADO TÉC", "CLIENTE/CLIENTE", "PRECIO DOLAR", "PRESUPUESTO ENV"
    };
	
	

	@SuppressWarnings("serial")
	public VentanaListadoReparaciones(ControladorListados controlador) {
		super();
		this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);

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
		panelFiltros.setBorder(new CompoundBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 128, 128)), new EmptyBorder(0, 5, 5, 10)));
		panelSuperior.add(panelFiltros, BorderLayout.CENTER);
				
						lbTitulo = new JLabel("OCULTAR COLUMNAS");
						lbTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
						lbTitulo.setHorizontalAlignment(SwingConstants.LEFT);
						lbTitulo.setFont(new Font("Cambria", Font.BOLD, 22));
		
				btnEstadisticas = new JButton("ESTADÍSTICAS");
				btnEstadisticas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnEstadisticas.setBorder(new LineBorder(new Color(0, 128, 128)));
				btnEstadisticas.setForeground(new Color(255, 255, 255));
				btnEstadisticas.setBackground(new Color(95, 158, 160));
				btnEstadisticas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnEstadisticas.setPreferredSize(new Dimension(150, 30));
				btnEstadisticas.setMargin(new Insets(0, 10, 0, 10));
				btnEstadisticas.setFont(new Font("Cambria", Font.BOLD, 22));
				GroupLayout gl_panelFiltros = new GroupLayout(panelFiltros);
				gl_panelFiltros.setHorizontalGroup(
					gl_panelFiltros.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panelFiltros.createSequentialGroup()
							.addContainerGap()
							.addComponent(lbTitulo, GroupLayout.PREFERRED_SIZE, 574, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
							.addComponent(btnEstadisticas, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
							.addGap(135))
				);
				gl_panelFiltros.setVerticalGroup(
					gl_panelFiltros.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelFiltros.createSequentialGroup()
							.addContainerGap(15, Short.MAX_VALUE)
							.addGroup(gl_panelFiltros.createParallelGroup(Alignment.LEADING)
								.addComponent(lbTitulo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addGroup(Alignment.TRAILING, gl_panelFiltros.createSequentialGroup()
									.addComponent(btnEstadisticas, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addGap(4)))
							.addGap(9))
				);
				panelFiltros.setLayout(gl_panelFiltros);

		panelTitulo = new JPanel();
		panelTitulo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 128)));
		panelTitulo.setBackground(new Color(176, 196, 222));
		panelSuperior.add(panelTitulo, BorderLayout.NORTH);
		panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbTitulo_1 = new JLabel("LISTADO DE EQUIPOS");
		lbTitulo_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lbTitulo_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbTitulo_1.setFont(new Font("Cambria", Font.BOLD, 30));
		panelTitulo.add(lbTitulo_1);

		panelBotonera = new JPanel();
		panelSuperior.add(panelBotonera, BorderLayout.SOUTH);
		panelBotonera.setLayout(new BorderLayout(0, 0));

		panelBotones = new JPanel();
		panelBotones
				.setBorder(new CompoundBorder(new LineBorder(new Color(0, 128, 128)), new EmptyBorder(5, 10, 5, 0)));
		panelBotones.setBackground(new Color(176, 196, 222));
		panelBotonera.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));

		panelColumnas = new JPanel();
		panelColumnas.setBorder(new CompoundBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 128, 128)),
				new EmptyBorder(5, 5, 5, 5)));
		panelColumnas.setBackground(new Color(176, 196, 222));
		panelBotonera.add(panelColumnas, BorderLayout.NORTH);
		panelColumnas.setLayout(new GridLayout(3, 6, 5, 5));

		// Checkboxes para ocultar columnas
		chckbxELS = new JCheckBox("ELS");
		darFormatoOcultarColumnas(chckbxELS);
		panelColumnas.add(chckbxELS);

		chckbxSucursal = new JCheckBox("SUCURSAL");
		darFormatoOcultarColumnas(chckbxSucursal);
		panelColumnas.add(chckbxSucursal);

		chckbxModelo = new JCheckBox("MODELO");
		darFormatoOcultarColumnas(chckbxModelo);
		panelColumnas.add(chckbxModelo);

		chckbxRevisión = new JCheckBox("REVISIÓN");
		darFormatoOcultarColumnas(chckbxRevisión);
		panelColumnas.add(chckbxRevisión);

		chckbxEstadoCom = new JCheckBox("ESTADO COM");
		darFormatoOcultarColumnas(chckbxEstadoCom);
		panelColumnas.add(chckbxEstadoCom);

		chckbxUbicacionRemito = new JCheckBox("UBICACIÓN REMITO");
		darFormatoOcultarColumnas(chckbxUbicacionRemito);
		panelColumnas.add(chckbxUbicacionRemito);

		chckbxPago = new JCheckBox("PAGO");
		darFormatoOcultarColumnas(chckbxPago);
		panelColumnas.add(chckbxPago);

		chckbxIngreso = new JCheckBox("INGRESO");
		darFormatoOcultarColumnas(chckbxIngreso);
		panelColumnas.add(chckbxIngreso);

		chckbxEntrada = new JCheckBox("ENTRADA");
		darFormatoOcultarColumnas(chckbxEntrada);
		panelColumnas.add(chckbxEntrada);

		chckbxEquipo = new JCheckBox("EQUIPO");
		darFormatoOcultarColumnas(chckbxEquipo);
		panelColumnas.add(chckbxEquipo);

		chckbxSerie = new JCheckBox("SERIE");
		darFormatoOcultarColumnas(chckbxSerie);
		panelColumnas.add(chckbxSerie);

		chckbxSalida = new JCheckBox("SALIDA");
		darFormatoOcultarColumnas(chckbxSalida);
		panelColumnas.add(chckbxSalida);

		chckbxEstadoFis = new JCheckBox("ESTADO FÍS");
		darFormatoOcultarColumnas(chckbxEstadoFis);
		panelColumnas.add(chckbxEstadoFis);

		chckbxNumeroRemito = new JCheckBox("NÚMERO REMITO");
		darFormatoOcultarColumnas(chckbxNumeroRemito);
		panelColumnas.add(chckbxNumeroRemito);

		chckbxPrecioPeso = new JCheckBox("PRECIO PESO");
		darFormatoOcultarColumnas(chckbxPrecioPeso);
		panelColumnas.add(chckbxPrecioPeso);

		chckbxPresupuestoGeneradoColumna = new JCheckBox("PRESUPUESTO GEN");
		darFormatoOcultarColumnas(chckbxPresupuestoGeneradoColumna);
		panelColumnas.add(chckbxPresupuestoGeneradoColumna);

		chckbxCliente = new JCheckBox("CLIENTE");
		darFormatoOcultarColumnas(chckbxCliente);
		panelColumnas.add(chckbxCliente);

		chckbxMarca = new JCheckBox("MARCA");
		darFormatoOcultarColumnas(chckbxMarca);
		panelColumnas.add(chckbxMarca);

		chckbxAviso = new JCheckBox("AVISO");
		darFormatoOcultarColumnas(chckbxAviso);
		panelColumnas.add(chckbxAviso);

		chckbxTecnico = new JCheckBox("TÉCNICO");
		darFormatoOcultarColumnas(chckbxTecnico);
		panelColumnas.add(chckbxTecnico);

		chckbxEstadoTec = new JCheckBox("ESTADO TÉC");
		darFormatoOcultarColumnas(chckbxEstadoTec);
		panelColumnas.add(chckbxEstadoTec);

		chckbxClienteCliente = new JCheckBox("CLIENTE/CLIENTE");
		darFormatoOcultarColumnas(chckbxClienteCliente);
		panelColumnas.add(chckbxClienteCliente);

		chckbxPrecioDolar = new JCheckBox("PRECIO DOLAR");
		darFormatoOcultarColumnas(chckbxPrecioDolar);
		panelColumnas.add(chckbxPrecioDolar);

		chckbxPresupuestoEnviadoColumna = new JCheckBox("PRESUPUESTO ENV");
		darFormatoOcultarColumnas(chckbxPresupuestoEnviadoColumna);
		panelColumnas.add(chckbxPresupuestoEnviadoColumna);
		
		
		
		// Botón para activar el menú desplegable
        btnOcultarColumnas = new JButton("OCULTAR COLUMNAS");
        add(btnOcultarColumnas, BorderLayout.NORTH);

        // Crear el menú emergente con JCheckBoxMenuItem
        popupMenu = new JPopupMenu();
        popupMenu.setLightWeightPopupEnabled(false); // Evita que se cierre al hacer clic
        
        // Añadir cada JCheckBoxMenuItem al menú
        for (String nombreColumna : nombresColumnas) {
        	JCheckBoxMenuItem item = new JCheckBoxMenuItem(nombreColumna, true);
            item.addActionListener(e -> {
                // Evita que el menú se cierre al hacer clic
                if (e.getSource() instanceof JCheckBoxMenuItem) {
                    SwingUtilities.invokeLater(() -> {
                        popupMenu.setVisible(true);
                    });
                }
            });
            item.addItemListener(e -> actualizarVisibilidadColumnas(nombreColumna));
            popupMenu.add(item);
        }

        // Mostrar el menú al hacer clic en el botón
        btnOcultarColumnas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(btnOcultarColumnas, 0, btnOcultarColumnas.getHeight());
            }
        });

		
		
		
		
	

		panelCentral = new JPanel();
		panelCentral.setBorder(new CompoundBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 128, 128)),
				new EmptyBorder(3, 5, 0, 5)));
		panelCentral.setBackground(new Color(176, 196, 222));
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));

		modelReparaciones = new DefaultTableModel(new Object[][] {}, nombreColumnas) {
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, String.class, Boolean.class,
					Boolean.class, double.class, double.class, double.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		tblListado = new JTable(modelReparaciones);

		tblListado.setCellSelectionEnabled(true);
		tblListado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		scrollPane = new JScrollPane(tblListado);
		scrollPane.setViewportView(tblListado);
		panelCentral.add(scrollPane, BorderLayout.CENTER);

		int[] anchos = { 60, 80, 150, 150, 200, 100, 150, 100, 100, 80, 80, 110, 120, 150, 100, 100, 100, 100, 80, 80,
				100, 100, 100, 80 };

		for (int i = 0; i < tblListado.getColumnCount(); i++) {
			tblListado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}

		this.setVisible(true);
	}

	public void setCellRender(JTable table) {
		Enumeration<TableColumn> en = table.getColumnModel().getColumns();
		while (en.hasMoreElements()) {
			TableColumn tc = en.nextElement();
			tc.setCellRenderer(new CellRendererTablaListado(table));
		}
	}

	public DefaultTableModel getModelReparaciones() {
		return modelReparaciones;
	}
	
	private void actualizarVisibilidadColumnas(String nombreColumna) {
		
		System.out.println(nombreColumna);
		
		
//        for (int i = 0; i < popupMenu.getComponentCount(); i++) {
//            Component comp = popupMenu.getComponent(i);
//            if (comp instanceof JCheckBoxMenuItem) {
//                JCheckBoxMenuItem item = (JCheckBoxMenuItem) comp;
//                table.getColumnModel().getColumn(i).setResizable(true);
//                table.getColumnModel().getColumn(i).setMinWidth(item.isSelected() ? 50 : 0);
//                table.getColumnModel().getColumn(i).setMaxWidth(item.isSelected() ? Integer.MAX_VALUE : 0);
//            }
//        }
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

	public JCheckBox getChckbxSalida() {
		return chckbxSalida;
	}

	public void setChckbxSalida(JCheckBox chckbxSalida) {
		this.chckbxSalida = chckbxSalida;
	}

}