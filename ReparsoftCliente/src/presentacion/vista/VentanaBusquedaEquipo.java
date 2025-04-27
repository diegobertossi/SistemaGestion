package presentacion.vista;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;

import presentacion.controlador.ControladorReparacion;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class VentanaBusquedaEquipo extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public JComboBox<String> comboBuscador;
    public JTextField textField;
    public JTextPane textPane;
    public JScrollPane scrollPane;
    public JButton btnBuscar;
    
    private ControladorReparacion controlador;
    private JPanel panel;

    public VentanaBusquedaEquipo(ControladorReparacion controlador) {
        super();
        setResizable(false);
        this.controlador = controlador;

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/multimetro.png"));
		this.setIconImage(icon);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 447, 252);

        setAlwaysOnTop(true);
        
        //this.setLocationRelativeTo(null);
        setLocation(360, 190);
        
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.setBorder(new LineBorder(new Color(0, 128, 128)));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCampoAbuscar = new JLabel("Campo a buscar:");
        lblCampoAbuscar.setForeground(new Color(0, 0, 0));
        lblCampoAbuscar.setFont(new Font("Cambria", Font.BOLD, 16));
        lblCampoAbuscar.setBounds(18, 15, 132, 23);
        contentPane.add(lblCampoAbuscar);

        String[] searchFields = {"Falla", "Diagnóstico", "Informe Cliente"};

        comboBuscador = new JComboBox<>(searchFields);
        comboBuscador.setFont(new Font("Cambria", Font.PLAIN, 13));
        comboBuscador.setBounds(160, 15, 156, 23);
        contentPane.add(comboBuscador);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Cambria", Font.BOLD, 13));
        btnBuscar.setBounds(326, 26, 89, 23);
        contentPane.add(btnBuscar);

        JLabel lblTextoAbuscar = new JLabel("Texto a buscar:");
        lblTextoAbuscar.setForeground(new Color(0, 0, 0));
        lblTextoAbuscar.setFont(new Font("Cambria", Font.BOLD, 16));
        lblTextoAbuscar.setBounds(18, 43, 132, 23);
        contentPane.add(lblTextoAbuscar);

        textField = new JTextField();
        textField.setFont(new Font("Cambria", Font.PLAIN, 13));
        textField.setBounds(160, 43, 156, 23);
        contentPane.add(textField);
        textField.setColumns(10);
        
        panel = new JPanel();
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(12, 77, 409, 125);
        contentPane.add(panel);
        panel.setLayout(null);

        textPane = new JTextPane();
        textPane.setMargin(new Insets(5, 5, 5, 5));
        textPane.setAlignmentY(Component.TOP_ALIGNMENT);
        textPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        textPane.setFont(new Font("Cambria", Font.BOLD, 12));
        textPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        textPane.setEditable(false);
        textPane.setBackground(SystemColor.inactiveCaption);

        scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(new Rectangle(5, 5, 5, 5));
        scrollPane.setFont(new Font("Cambria", Font.PLAIN, 13));
        scrollPane.setBounds(188, 11, 83, 102);
        panel.add(scrollPane);
        scrollPane.setBorder(null);

        JLabel lblEquiposEncontrados = new JLabel("Equipos encontrados:");
        lblEquiposEncontrados.setBounds(10, 51, 172, 23);
        panel.add(lblEquiposEncontrados);
        lblEquiposEncontrados.setForeground(Color.BLACK);
        lblEquiposEncontrados.setFont(new Font("Cambria", Font.BOLD, 16));

        this.setVisible(true);
    }

	public JComboBox<String> getComboBuscador() {
		return comboBuscador;
	}

	public void setComboBuscador(JComboBox<String> comboBuscador) {
		this.comboBuscador = comboBuscador;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	
}