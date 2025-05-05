package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import presentacion.controlador.ControladorBackup;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;

public class VentanaBackUp extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	@SuppressWarnings("unused")
	private ControladorBackup controladorBackup;
	private JButton btnGenerarB;
	private JButton btnImportarB;
	


	public VentanaBackUp(ControladorBackup controladorBackup) 
	{
		super();
		setResizable(false);
		//this.setDefaultCloseOperation(VentanaBackUp.DO_NOTHING_ON_CLOSE);
		this.controladorBackup = controladorBackup;
		setBounds(100, 100, 306, 155);
		this.setLocationRelativeTo(null);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setAutoscrolls(true);
		contentPane.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGenerarB = new JButton("<html><center>GENERAR BACKUP</html>");
		btnGenerarB.setForeground(Color.BLACK);
		btnGenerarB.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGenerarB.setBackground(SystemColor.menu);
		btnGenerarB.setBounds(30, 33, 100, 50);;
		contentPane.add(btnGenerarB);
		
		btnImportarB = new JButton("<html><center>IMPORTAR BACKUP</html>");
		btnImportarB.setForeground(Color.BLACK);
		btnImportarB.setFont(new Font("Cambria", Font.BOLD, 14));
		btnImportarB.setBackground(SystemColor.menu);
		btnImportarB.setBounds(160, 33, 100, 50);
		contentPane.add(btnImportarB);

		this.setVisible(true);

		@SuppressWarnings("unused")
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
	
		this.setVisible(true);
	}
	
	

	public JButton getBtnGenerarB() {
		return btnGenerarB;
	}


	public void setBtnGenerarB(JButton btnGenerarB) {
		this.btnGenerarB = btnGenerarB;
	}


	public JButton getBtnImportarB() {
		return btnImportarB;
	}


	public void setBtnImportarB(JButton btnImportarB) {
		this.btnImportarB = btnImportarB;
	}

	


}