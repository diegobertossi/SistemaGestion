package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaListadoReparacionesP extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	public VentanaListadoReparacionesP() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelPrincipal = new JPanel();
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		JPanel panelSuperior = new JPanel();
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setIcon(new ImageIcon("F:\\els\\ico\\Apps-Whatsapp-icon.png"));
		panelSuperior.add(lblImagen);
		
		JPanel panelLogin = new JPanel();
		panelLogin.setOpaque(false);
		panelSuperior.add(panelLogin, BorderLayout.EAST);
		panelLogin.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("    ");
		panelLogin.add(lblNewLabel, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel("    ");
		panelLogin.add(lblNewLabel_1, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_2 = new JLabel("                  ");
		panelLogin.add(lblNewLabel_2, BorderLayout.EAST);
		
		JPanel panelGridLogin = new JPanel();
		panelGridLogin.setOpaque(false);
		panelLogin.add(panelGridLogin, BorderLayout.CENTER);
		panelGridLogin.setLayout(new GridLayout(2, 4, 10, 4));
		
		JLabel lblNewLabel_4 = new JLabel("Tipo");
		panelGridLogin.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("Nombre");
		panelGridLogin.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("Contraseña");
		panelGridLogin.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("    ");
		panelGridLogin.add(lblNewLabel_6);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Juan", "Diego", "Pedro"}));
		panelGridLogin.add(comboBox);
		
		textField = new JTextField();
		panelGridLogin.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		panelGridLogin.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		panelGridLogin.add(btnNewButton);
		
		JPanel panelInferior = new JPanel();
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
		
		JPanel panelCentral = new JPanel();
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		
		
		
		
		

	}

}
