package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class VentanaListadoReparaciones2 extends JFrame {
	
	
	
	private JTable tblReparaciones_1;
	private DefaultTableModel modelReparaciones;

	private String[] nombreColumnas = { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO",
			"N° SERIE", "AVISO", "REVISIÓN", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS", "TÉCNICO",
			"UBIC. REM", "NUM REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO" };


	
	public VentanaListadoReparaciones2() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelPrincipal = new JPanel();
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		JPanel panelSuperior = new JPanel();
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFiltros = new JPanel();
		panelFiltros.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		panelFiltros.setBounds(35, 50, 994, 134);
		panelSuperior.add(panelFiltros, BorderLayout.CENTER);
		GridBagLayout gbl_panelFiltros = new GridBagLayout();
		gbl_panelFiltros.columnWidths = new int[] {30, 70, 130, 50, 70, 130, 50, 80, 130, 50, 100, 50, 30};
		gbl_panelFiltros.rowHeights = new int[]{5, 0, 23, 0, 0, 5, 0};
		gbl_panelFiltros.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panelFiltros.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panelFiltros.setLayout(gbl_panelFiltros);
		
		JLabel lblNewLabel_1 = new JLabel("CLIENTE");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelFiltros.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JComboBox comboBox_3_1_6 = new JComboBox();
		comboBox_3_1_6.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_6 = new GridBagConstraints();
		gbc_comboBox_3_1_6.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_6.gridx = 2;
		gbc_comboBox_3_1_6.gridy = 1;
		panelFiltros.add(comboBox_3_1_6, gbc_comboBox_3_1_6);
		
		JRadioButton rdbtnNewRadioButton_10 = new JRadioButton("");
		rdbtnNewRadioButton_10.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_10 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_10.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_10.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_10.gridx = 3;
		gbc_rdbtnNewRadioButton_10.gridy = 1;
		panelFiltros.add(rdbtnNewRadioButton_10, gbc_rdbtnNewRadioButton_10);
		
		JLabel lblNewLabel_4_1 = new JLabel("EQUIPO");
		lblNewLabel_4_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_4_1 = new GridBagConstraints();
		gbc_lblNewLabel_4_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4_1.gridx = 4;
		gbc_lblNewLabel_4_1.gridy = 1;
		panelFiltros.add(lblNewLabel_4_1, gbc_lblNewLabel_4_1);
		
		JComboBox comboBox_3_1_1 = new JComboBox();
		comboBox_3_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_1 = new GridBagConstraints();
		gbc_comboBox_3_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_1.gridx = 5;
		gbc_comboBox_3_1_1.gridy = 1;
		panelFiltros.add(comboBox_3_1_1, gbc_comboBox_3_1_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("");
		rdbtnNewRadioButton_2.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_2 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_2.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_2.fill = GridBagConstraints.VERTICAL;
		gbc_rdbtnNewRadioButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_2.gridx = 6;
		gbc_rdbtnNewRadioButton_2.gridy = 1;
		panelFiltros.add(rdbtnNewRadioButton_2, gbc_rdbtnNewRadioButton_2);
		
		JLabel lblNewLabel_9_1 = new JLabel("ESTADO COM");
		lblNewLabel_9_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_9_1 = new GridBagConstraints();
		gbc_lblNewLabel_9_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_9_1.gridx = 7;
		gbc_lblNewLabel_9_1.gridy = 1;
		panelFiltros.add(lblNewLabel_9_1, gbc_lblNewLabel_9_1);
		
		JComboBox comboBox_3_1_4_1 = new JComboBox();
		comboBox_3_1_4_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_4_1 = new GridBagConstraints();
		gbc_comboBox_3_1_4_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_4_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_4_1.gridx = 8;
		gbc_comboBox_3_1_4_1.gridy = 1;
		panelFiltros.add(comboBox_3_1_4_1, gbc_comboBox_3_1_4_1);
		
		JRadioButton rdbtnNewRadioButton_9_2 = new JRadioButton("");
		rdbtnNewRadioButton_9_2.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_9_2 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_9_2.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_9_2.fill = GridBagConstraints.VERTICAL;
		gbc_rdbtnNewRadioButton_9_2.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_9_2.gridx = 9;
		gbc_rdbtnNewRadioButton_9_2.gridy = 1;
		panelFiltros.add(rdbtnNewRadioButton_9_2, gbc_rdbtnNewRadioButton_9_2);
		
		JCheckBox chckbxNewCheckBox_1_1 = new JCheckBox("PRESUPUESTO ENVIADO");
		chckbxNewCheckBox_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_chckbxNewCheckBox_1_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1_1.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox_1_1.gridx = 10;
		gbc_chckbxNewCheckBox_1_1.gridy = 1;
		panelFiltros.add(chckbxNewCheckBox_1_1, gbc_chckbxNewCheckBox_1_1);
		
		JRadioButton rdbtnNewRadioButton_8_1 = new JRadioButton("");
		rdbtnNewRadioButton_8_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_8_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_8_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_8_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_8_1.gridx = 11;
		gbc_rdbtnNewRadioButton_8_1.gridy = 1;
		panelFiltros.add(rdbtnNewRadioButton_8_1, gbc_rdbtnNewRadioButton_8_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("SUCURSAL");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1_1.gridx = 1;
		gbc_lblNewLabel_1_1.gridy = 2;
		panelFiltros.add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		
		JComboBox comboBox_3_1_7 = new JComboBox();
		comboBox_3_1_7.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_7 = new GridBagConstraints();
		gbc_comboBox_3_1_7.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_7.gridx = 2;
		gbc_comboBox_3_1_7.gridy = 2;
		panelFiltros.add(comboBox_3_1_7, gbc_comboBox_3_1_7);
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("");
		rdbtnNewRadioButton_4.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_4 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_4.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_4.fill = GridBagConstraints.VERTICAL;
		gbc_rdbtnNewRadioButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_4.gridx = 3;
		gbc_rdbtnNewRadioButton_4.gridy = 2;
		panelFiltros.add(rdbtnNewRadioButton_4, gbc_rdbtnNewRadioButton_4);
		
		JLabel lblNewLabel_2_1 = new JLabel("MARCA");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2_1.gridx = 4;
		gbc_lblNewLabel_2_1.gridy = 2;
		panelFiltros.add(lblNewLabel_2_1, gbc_lblNewLabel_2_1);
		
		JComboBox comboBox_3_1_1_1 = new JComboBox();
		comboBox_3_1_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_1_1 = new GridBagConstraints();
		gbc_comboBox_3_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_1_1.gridx = 5;
		gbc_comboBox_3_1_1_1.gridy = 2;
		panelFiltros.add(comboBox_3_1_1_1, gbc_comboBox_3_1_1_1);
		
		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("");
		rdbtnNewRadioButton_5.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_5 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_5.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_5.fill = GridBagConstraints.VERTICAL;
		gbc_rdbtnNewRadioButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_5.gridx = 6;
		gbc_rdbtnNewRadioButton_5.gridy = 2;
		panelFiltros.add(rdbtnNewRadioButton_5, gbc_rdbtnNewRadioButton_5);
		
		JLabel lblNewLabel_8_1 = new JLabel("ESTADO TEC");
		lblNewLabel_8_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_8_1 = new GridBagConstraints();
		gbc_lblNewLabel_8_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_8_1.gridx = 7;
		gbc_lblNewLabel_8_1.gridy = 2;
		panelFiltros.add(lblNewLabel_8_1, gbc_lblNewLabel_8_1);
		
		JComboBox comboBox_3_1_4_2 = new JComboBox();
		comboBox_3_1_4_2.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_4_2 = new GridBagConstraints();
		gbc_comboBox_3_1_4_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_4_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_4_2.gridx = 8;
		gbc_comboBox_3_1_4_2.gridy = 2;
		panelFiltros.add(comboBox_3_1_4_2, gbc_comboBox_3_1_4_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("");
		rdbtnNewRadioButton_3.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_3 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_3.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_3.fill = GridBagConstraints.VERTICAL;
		gbc_rdbtnNewRadioButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_3.gridx = 9;
		gbc_rdbtnNewRadioButton_3.gridy = 2;
		panelFiltros.add(rdbtnNewRadioButton_3, gbc_rdbtnNewRadioButton_3);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("PRESUPUESTO GENERADO");
		chckbxNewCheckBox_2.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_2.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox_2.gridx = 10;
		gbc_chckbxNewCheckBox_2.gridy = 2;
		panelFiltros.add(chckbxNewCheckBox_2, gbc_chckbxNewCheckBox_2);
		
		JRadioButton rdbtnNewRadioButton_8_1_1 = new JRadioButton("");
		rdbtnNewRadioButton_8_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_8_1_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_8_1_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_8_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_8_1_1.gridx = 11;
		gbc_rdbtnNewRadioButton_8_1_1.gridy = 2;
		panelFiltros.add(rdbtnNewRadioButton_8_1_1, gbc_rdbtnNewRadioButton_8_1_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("ELS");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.gridx = 1;
		gbc_lblNewLabel_3_1.gridy = 3;
		panelFiltros.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
		
		JComboBox comboBox_3_1_7_1 = new JComboBox();
		comboBox_3_1_7_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_7_1 = new GridBagConstraints();
		gbc_comboBox_3_1_7_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_7_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_7_1.gridx = 2;
		gbc_comboBox_3_1_7_1.gridy = 3;
		panelFiltros.add(comboBox_3_1_7_1, gbc_comboBox_3_1_7_1);
		
		JRadioButton rdbtnNewRadioButton_7 = new JRadioButton("");
		rdbtnNewRadioButton_7.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_7 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_7.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_7.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_7.gridx = 3;
		gbc_rdbtnNewRadioButton_7.gridy = 3;
		panelFiltros.add(rdbtnNewRadioButton_7, gbc_rdbtnNewRadioButton_7);
		
		JLabel lblNewLabel_7_1 = new JLabel("TÉCNICO");
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_7_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_7_1 = new GridBagConstraints();
		gbc_lblNewLabel_7_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7_1.gridx = 4;
		gbc_lblNewLabel_7_1.gridy = 3;
		panelFiltros.add(lblNewLabel_7_1, gbc_lblNewLabel_7_1);
		
		JComboBox comboBox_3_1_2_1 = new JComboBox();
		comboBox_3_1_2_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_2_1 = new GridBagConstraints();
		gbc_comboBox_3_1_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_2_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_2_1.gridx = 5;
		gbc_comboBox_3_1_2_1.gridy = 3;
		panelFiltros.add(comboBox_3_1_2_1, gbc_comboBox_3_1_2_1);
		
		JRadioButton rdbtnNewRadioButton_6 = new JRadioButton("");
		rdbtnNewRadioButton_6.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_6 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_6.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_6.fill = GridBagConstraints.VERTICAL;
		gbc_rdbtnNewRadioButton_6.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_6.gridx = 6;
		gbc_rdbtnNewRadioButton_6.gridy = 3;
		panelFiltros.add(rdbtnNewRadioButton_6, gbc_rdbtnNewRadioButton_6);
		
		JLabel lblNewLabel_10_1 = new JLabel("ESTADO FIS");
		lblNewLabel_10_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_10_1 = new GridBagConstraints();
		gbc_lblNewLabel_10_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_10_1.gridx = 7;
		gbc_lblNewLabel_10_1.gridy = 3;
		panelFiltros.add(lblNewLabel_10_1, gbc_lblNewLabel_10_1);
		
		JComboBox comboBox_3_1_4_2_1 = new JComboBox();
		comboBox_3_1_4_2_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_4_2_1 = new GridBagConstraints();
		gbc_comboBox_3_1_4_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_4_2_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_4_2_1.gridx = 8;
		gbc_comboBox_3_1_4_2_1.gridy = 3;
		panelFiltros.add(comboBox_3_1_4_2_1, gbc_comboBox_3_1_4_2_1);
		
		JRadioButton rdbtnNewRadioButton_3_1 = new JRadioButton("");
		rdbtnNewRadioButton_3_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_3_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_3_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_3_1.gridx = 9;
		gbc_rdbtnNewRadioButton_3_1.gridy = 3;
		panelFiltros.add(rdbtnNewRadioButton_3_1, gbc_rdbtnNewRadioButton_3_1);
		
		JLabel lblNewLabel_6_1 = new JLabel("AVISO");
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_6_1 = new GridBagConstraints();
		gbc_lblNewLabel_6_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6_1.gridx = 1;
		gbc_lblNewLabel_6_1.gridy = 4;
		panelFiltros.add(lblNewLabel_6_1, gbc_lblNewLabel_6_1);
		
		JComboBox comboBox_3_1_8_1 = new JComboBox();
		comboBox_3_1_8_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_8_1 = new GridBagConstraints();
		gbc_comboBox_3_1_8_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_8_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_8_1.gridx = 2;
		gbc_comboBox_3_1_8_1.gridy = 4;
		panelFiltros.add(comboBox_3_1_8_1, gbc_comboBox_3_1_8_1);
		
		JRadioButton rdbtnNewRadioButton_9_1_1 = new JRadioButton("");
		rdbtnNewRadioButton_9_1_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_9_1_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_9_1_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_9_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_9_1_1.gridx = 3;
		gbc_rdbtnNewRadioButton_9_1_1.gridy = 4;
		panelFiltros.add(rdbtnNewRadioButton_9_1_1, gbc_rdbtnNewRadioButton_9_1_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("MODELO");
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_1.gridx = 4;
		gbc_lblNewLabel_5_1.gridy = 4;
		panelFiltros.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);
		
		JComboBox comboBox_3_1_3_1 = new JComboBox();
		comboBox_3_1_3_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_comboBox_3_1_3_1 = new GridBagConstraints();
		gbc_comboBox_3_1_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3_1_3_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3_1_3_1.gridx = 5;
		gbc_comboBox_3_1_3_1.gridy = 4;
		panelFiltros.add(comboBox_3_1_3_1, gbc_comboBox_3_1_3_1);
		
		JRadioButton rdbtnNewRadioButton_6_1 = new JRadioButton("");
		rdbtnNewRadioButton_6_1.setFont(new Font("Cambria", Font.BOLD, 14));
		GridBagConstraints gbc_rdbtnNewRadioButton_6_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_6_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNewRadioButton_6_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_6_1.gridx = 6;
		gbc_rdbtnNewRadioButton_6_1.gridy = 4;
		panelFiltros.add(rdbtnNewRadioButton_6_1, gbc_rdbtnNewRadioButton_6_1);
		
		JPanel panelTitulo = new JPanel();
		FlowLayout fl_panelTitulo = (FlowLayout) panelTitulo.getLayout();
		fl_panelTitulo.setAlignment(FlowLayout.LEFT);
		panelSuperior.add(panelTitulo, BorderLayout.NORTH);
		
		JLabel lblNewLabel_11 = new JLabel("      ");
		panelTitulo.add(lblNewLabel_11);
		
		JLabel lbTitulo_1 = new JLabel("LISTADO DE EQUIPOS");
		lbTitulo_1.setFont(new Font("Cambria", Font.BOLD, 30));
		panelTitulo.add(lbTitulo_1);
		
		JPanel panelBotones = new JPanel();
		panelSuperior.add(panelBotones, BorderLayout.SOUTH);
		FlowLayout fl_panelBotones = new FlowLayout(FlowLayout.CENTER, 190, 15);
		panelBotones.setLayout(fl_panelBotones);
		
		JButton btnFiltrar = new JButton("FILTRAR");
		btnFiltrar.setPreferredSize(new Dimension(150,30));
		btnFiltrar.setFont(new Font("Cambria", Font.BOLD, 14));
		
		panelBotones.add(btnFiltrar);
		
		JButton btnMostrarTodo = new JButton("MOSTRAR TODO");
		btnMostrarTodo.setFont(new Font("Cambria", Font.BOLD, 14));
		btnMostrarTodo.setPreferredSize(new Dimension(150,30));
		panelBotones.add(btnMostrarTodo);
		
		JButton btnEstadisticas = new JButton("ESTADÍSTICAS");
		btnEstadisticas.setFont(new Font("Cambria", Font.BOLD, 14));
		btnEstadisticas.setPreferredSize(new Dimension(150,30));
		panelBotones.add(btnEstadisticas);
		
		JPanel panelInferior = new JPanel();
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("       ");
		panelInferior.add(lblNewLabel);
		
		JPanel panelCentral = new JPanel();
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelCentral.add(scrollPane, BorderLayout.CENTER);
		
		
		
		
		
		modelReparaciones = new DefaultTableModel(null, nombreColumnas);
		tblReparaciones_1 = new JTable(modelReparaciones);

		modelReparaciones = new DefaultTableModel(new Object[][] {},
				new String[] { "ELS", "ENTRADA", "CLIENTE", "SUCURSAL", "EQUIPO", "MARCA", "MODELO", "N° SERIE",
						"AVISO", "REVISIÓN", "CLIENTE/CLIENTE", "ESTADO TEC", "ESTADO COM", "ESTADO FIS", "TÉCNICO",
						"UBIC. REM", "N° REM", "PRESUP. GEN", "PRESUP. ENV", "PRECIO $", "PRECIO U$$", "PAGO" }) {

			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, String.class, String.class,
					String.class, String.class, String.class, String.class, String.class, Boolean.class, Boolean.class,
					double.class, double.class, double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false,
					false, false, false, false, false, false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};

		try {
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}

		Font fuenteCabecera = new Font("Cambria", Font.BOLD, 14);
		Font fuenteCeldas = new Font("Cambria", Font.PLAIN, 12);

		tblReparaciones_1 = new JTable(modelReparaciones);
		// tblReparaciones_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		tblReparaciones_1.setFont(fuenteCeldas);

		tblReparaciones_1.getTableHeader().setForeground(Color.BLACK);
		tblReparaciones_1.getTableHeader().setFont(fuenteCabecera);
		tblReparaciones_1.getTableHeader().setReorderingAllowed(false);

		((DefaultTableCellRenderer) tblReparaciones_1.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		tblReparaciones_1.setShowGrid(true);
		tblReparaciones_1.setCellSelectionEnabled(true);

		// tblReparaciones_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// tblReparaciones_1.doLayout();

		scrollPane.setViewportView(tblReparaciones_1);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tblReparaciones_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblReparaciones_1.setAutoCreateColumnsFromModel(false);

		//DimTblReparaciones = tblReparaciones_1.getSize();

		tblReparaciones_1.getTableHeader().setReorderingAllowed(false);

		int[] anchos = { 60, 80, 150, 150, 200, 100, 200, 100, 100, 80, 100, 120, 150, 100, 100, 100, 100, 100, 100,
				100, 100, 100 };

		for (int i = 0; i < tblReparaciones_1.getColumnCount(); i++) {

			tblReparaciones_1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

		}
		
		
		
		
		
		
		
		
		
		

	}

}
