package VistaPropias;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ventanaArch extends JFrame {
	private JLabel lblIcon;
	public ventanaArch() {
		super();
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 546, 437);
		lblIcon = new JLabel("");
		lblIcon.setBounds(0, 0, 800, 800);
		getContentPane().add(lblIcon);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 2, 2);
		//getContentPane().add(scrollPane);
		
		//scrollPane.setViewportView(lblIcon);

	}
	public JLabel getLblIcon() {
		return lblIcon;
	}
	public void setLblIcon(JLabel lblIcon) {
		this.lblIcon = lblIcon;
	}
}
