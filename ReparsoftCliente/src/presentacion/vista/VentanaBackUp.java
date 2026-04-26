package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import presentacion.controlador.ControladorBackup;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.Box;
import javax.swing.border.LineBorder;

public class VentanaBackUp extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	@SuppressWarnings("unused")
	private ControladorBackup controladorBackup;
	
	private ButtonGroup GrupoUbicacionServidor;
	



	private JRadioButton rdbtnLocal;
	private JRadioButton rdbtnRemoto;
	
	
	private JButton btnGenerarB;
	private JButton btnImportarB;
	


	public VentanaBackUp(ControladorBackup controladorBackup) 
	{
		super();
		setResizable(false);
		//this.setDefaultCloseOperation(VentanaBackUp.DO_NOTHING_ON_CLOSE);
		this.controladorBackup = controladorBackup;
		setBounds(100, 100, 336, 208);
		this.setLocationRelativeTo(null);
		
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconosoft.png"));
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
		btnGenerarB.setBounds(40, 99, 100, 50);;
		contentPane.add(btnGenerarB);
		
		btnImportarB = new JButton("<html><center>IMPORTAR BACKUP</html>");
		btnImportarB.setForeground(Color.BLACK);
		btnImportarB.setFont(new Font("Cambria", Font.BOLD, 14));
		btnImportarB.setBackground(SystemColor.menu);
		btnImportarB.setBounds(180, 99, 100, 50);
		contentPane.add(btnImportarB);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(112, 128, 144));
		panel.setOpaque(false);

		Font fuenteTitulo = new Font("Cambria", Font.BOLD, 12);
		Color colorTitulo = Color.GRAY;
		int grosorBorde = 1; // Espesor del borde
		Color colorBorde = Color.GRAY; // Color del borde

		panel.setBorder(new TitledBorder(BorderFactory.createLineBorder(colorBorde, grosorBorde),
				"SELECCIONAR UBICACIÓN DEL SERVIDOR", TitledBorder.CENTER, TitledBorder.TOP, fuenteTitulo,
				colorTitulo));

		panel.setBounds(31, 19, 259, 63);
		contentPane.add(panel);
		panel.setLayout(null);
		
		rdbtnLocal = new JRadioButton("LOCAL");
		rdbtnLocal.setName("BACKUP");
		rdbtnLocal.setSelected(true);
		rdbtnLocal.setBounds(24, 30, 70, 23);
		panel.add(rdbtnLocal);
		rdbtnLocal.setFont(new Font("Cambria", Font.BOLD, 12));
		rdbtnLocal.setOpaque(false);
		
		rdbtnRemoto = new JRadioButton("REMOTO (beta)");
		rdbtnRemoto.setBounds(118, 30, 115, 23);
		panel.add(rdbtnRemoto);
		rdbtnRemoto.setFont(new Font("Cambria", Font.BOLD, 12));
		rdbtnRemoto.setOpaque(false);
		
		
		
		GrupoUbicacionServidor = new ButtonGroup();
		GrupoUbicacionServidor.add(rdbtnLocal);
		GrupoUbicacionServidor.add(rdbtnRemoto);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBorder(new LineBorder(new Color(128, 128, 128)));
		verticalBox.setBounds(10, 11, 310, 158);
		contentPane.add(verticalBox);
	
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
	
	
	public ButtonGroup getGrupoUbicacionServidor() {
		return GrupoUbicacionServidor;
	}



	public void setGrupoUbicacionServidor(ButtonGroup grupoUbicacionServidor) {
		GrupoUbicacionServidor = grupoUbicacionServidor;
	}



	public JRadioButton getRdbtnLocal() {
		return rdbtnLocal;
	}



	public void setRdbtnLocal(JRadioButton rdbtnLocal) {
		this.rdbtnLocal = rdbtnLocal;
	}



	public JRadioButton getRdbtnRemoto() {
		return rdbtnRemoto;
	}



	public void setRdbtnRemoto(JRadioButton rdbtnRemoto) {
		this.rdbtnRemoto = rdbtnRemoto;
	}
}