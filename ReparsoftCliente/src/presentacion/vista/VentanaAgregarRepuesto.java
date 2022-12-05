package presentacion.vista;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

import VistaPropias.JTextNum;
import presentacion.controlador.ControladorCliente;
import presentacion.controlador.ControladorReparacion;
import presentacion.controlador.ControladorUsuarios;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;

public class VentanaAgregarRepuesto extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField txtReferencia;
	private JTextField txtOriginal;
	private JTextField txtReemplazo;
	private ControladorReparacion controlador;
	private JButton btnAgregarRepuesto;
	private JButton btnCancelar;
	private JLabel lblNota;
	private JTextField txtNota;
	private JLabel lblRepuesto;
	
	public VentanaAgregarRepuesto(ControladorReparacion controlador) 
	{
		super();
		setResizable(false);
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 490, 251);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 0, 528, 212);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblOriginal = new JLabel("Original : ");
		lblOriginal.setFont(new Font("Cambria", Font.BOLD, 14));
		lblOriginal.setBounds(20, 80, 89, 18);
		panel.add(lblOriginal);
		
		JLabel lblReferencia = new JLabel("Referencia : ");
		lblReferencia.setFont(new Font("Cambria", Font.BOLD, 14));
		lblReferencia.setBounds(20, 59, 89, 18);
		panel.add(lblReferencia);
		
		JLabel lblReemplazo = new JLabel("Reemplazo : ");
		lblReemplazo.setFont(new Font("Cambria", Font.BOLD, 14));
		lblReemplazo.setBounds(20, 101, 89, 18);
		panel.add(lblReemplazo);
		
		txtOriginal = new JTextField();
		txtOriginal.setBackground(SystemColor.inactiveCaptionBorder);
		txtOriginal.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtOriginal.setBounds(113, 79, 340, 20);
		panel.add(txtOriginal);
		txtOriginal.setColumns(10);

		
		
		txtReferencia = new JTextField();
		txtReferencia.setBackground(SystemColor.inactiveCaptionBorder);
		txtReferencia.setFont(new Font("Cambria", Font.BOLD, 14));
		txtReferencia.setBounds(113, 58, 340, 20);
		panel.add(txtReferencia);
		txtReferencia.setColumns(10);
	
		
		txtReemplazo = new JTextField();
		txtReemplazo.setBackground(SystemColor.inactiveCaptionBorder);
		txtReemplazo.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtReemplazo.setBounds(113, 100, 340, 20);
		panel.add(txtReemplazo);
		txtReemplazo.setColumns(10);
		
		btnAgregarRepuesto = new JButton("ACEPTAR");
		btnAgregarRepuesto.setBackground(new Color(152, 251, 152));
		btnAgregarRepuesto.setFont(new Font("Cambria", Font.BOLD, 14));
		btnAgregarRepuesto.setBounds(229, 162, 107, 23);
			
		panel.add(btnAgregarRepuesto);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(140, 232, 113, -8);
		panel.add(horizontalStrut);
		
		lblNota = new JLabel("Nota : ");
		lblNota.setFont(new Font("Cambria", Font.BOLD, 14));
		lblNota.setBounds(20, 122, 89, 18);
		panel.add(lblNota);
		
		txtNota = new JTextField();
		txtNota.setBackground(SystemColor.inactiveCaptionBorder);
		txtNota.setFont(new Font("Cambria", Font.PLAIN, 14));
		txtNota.setColumns(10);
		txtNota.setBounds(113, 121, 340, 20);
		panel.add(txtNota);
		
		lblRepuesto = new JLabel("REPUESTO");
		lblRepuesto.setFont(new Font("Cambria", Font.BOLD, 18));
		lblRepuesto.setBounds(20, 11, 97, 23);
		panel.add(lblRepuesto);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setFont(new Font("Cambria", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(255, 0, 0));
		btnCancelar.setBounds(346, 163, 107, 23);
		panel.add(btnCancelar);

	
		this.setVisible(true);
	}


	public JTextField getTxtReferencia() {
		return txtReferencia;
	}


	public void setTxtReferencia(JTextField txtReferencia) {
		this.txtReferencia = txtReferencia;
	}


	public JTextField getTxtOriginal() {
		return txtOriginal;
	}


	public void setTxtOriginal(JTextField txtOriginal) {
		this.txtOriginal = txtOriginal;
	}


	public JTextField getTxtReemplazo() {
		return txtReemplazo;
	}


	public void setTxtReemplazo(JTextField txtReemplazo) {
		this.txtReemplazo = txtReemplazo;
	}




	public JButton getBtnAgregarRepuesto() {
		return btnAgregarRepuesto;
	}


	public void setBtnAgregarRepuesto(JButton btnAgregarCliente) {
		this.btnAgregarRepuesto = btnAgregarCliente;
	}


	public JButton getBtnCancelar() {
		return btnCancelar;
	}


	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}


	public JTextField getTxtNota() {
		return txtNota;
	}


	public void setTxtNota(JTextField txtNota) {
		this.txtNota = txtNota;
	}
}	