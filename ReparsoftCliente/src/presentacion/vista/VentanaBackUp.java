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
import java.awt.Font;
import javax.swing.SwingConstants;

public class VentanaBackUp extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JButton btnGenerarB;
	private JButton btnImportarB;
	
	@SuppressWarnings("unused")
	private ControladorBackup controladorBackup;
	


	public VentanaBackUp(ControladorBackup controladorBackup) 
	{
		super();
		setResizable(false);
		//this.setDefaultCloseOperation(VentanaBackUp.DO_NOTHING_ON_CLOSE);
		this.controladorBackup = controladorBackup;
		setBounds(100, 100, 373, 172);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setBounds(0, 0, 370, 143);
		contentPane.add(panel);
		panel.setLayout(null);

		this.setVisible(true);

		@SuppressWarnings("unused")
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
		
		btnGenerarB = new JButton("<html><center>GENERAR BACKUP</html>");
		btnGenerarB.setForeground(new Color(0, 0, 0));
		btnGenerarB.setBackground(SystemColor.menu);
		btnGenerarB.setFont(new Font("Cambria", Font.BOLD, 14));
		btnGenerarB.setBounds(65, 61, 104, 55);
		panel.add(btnGenerarB);
		
		btnImportarB = new JButton("<html><center>IMPORTAR BACKUP</html>");
		btnImportarB.setForeground(new Color(0, 0, 0));
		btnImportarB.setBackground(SystemColor.menu);
		btnImportarB.setFont(new Font("Cambria", Font.BOLD, 14));
		btnImportarB.setBounds(199, 61, 104, 55);
		panel.add(btnImportarB);
		
		JLabel lblBackUp = new JLabel("BACKUP");
		lblBackUp.setForeground(new Color(0, 0, 0));
		lblBackUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackUp.setFont(new Font("Cambria", Font.BOLD, 22));
		lblBackUp.setBounds(135, 11, 104, 40);
		panel.add(lblBackUp);
		
	
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