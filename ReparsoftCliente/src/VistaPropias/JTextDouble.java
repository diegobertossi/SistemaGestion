package VistaPropias;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class JTextDouble extends JTextField {

	private Integer largo;
	
	private NumberFormatter formato;
	private boolean aceptaNegativo;
	private final String PATTERN_NUMERICO = "^[0-9]?$";
	private final String PATTERN_DECIMAL = "^[0-9]+([.][0-9]{1,2})?$";
	private final String PATTERN_PUNTO = "^[0-9]+([.])?$";
	private final String PATTERN_SIGNO = "^[-]([0-9]*)([.])*(([0-9]{1,2}))?$";

	public JTextDouble(Integer largo) {
		super();
		this.largo = largo;
		addKey();
		this.aceptaNegativo = false;
	}

	
	public JTextDouble(NumberFormatter formatter,Integer largo) {
		super();
		this.largo = largo;
		this.formato = formatter;
		addKey();
		this.aceptaNegativo = false;
	}
	
	
	public JTextDouble(Integer largo, Boolean aceptaNegativo) {
		super();
		this.largo = largo;
		addKey();
		this.aceptaNegativo = aceptaNegativo;
	}

	public JTextDouble() {
		super();
		this.largo = 100;
		addKey();
		this.aceptaNegativo = false;
	}

	private void addKey() {
		super.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

				char c = e.getKeyChar();
				if (Character.isLetterOrDigit(c) || !Character.isUnicodeIdentifierPart(c)) {
					if (Character.isLetter(c) || (Character.isSpace(c) && !Character.isWhitespace(c))) {
					
//							if(Character.isWhitespace(c) ){
//														
//								System.out.println("enter");
//								
//							}
							
							JOptionPane.showMessageDialog(null, "Ingresar numero");
							getToolkit().beep();
							e.consume();
					
					} else if (!validacionDouble(getText() + c)) {
						getToolkit().beep();
						e.consume();

					} else if (getText().length() >= largo) {
						getToolkit().beep();
						e.consume();
					}
				} else if (getText().length() >= largo) {
					getToolkit().beep();
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_V || e.getKeyCode() == KeyEvent.VK_CONTROL) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
	}

	boolean validacionDouble(String email) {

		Pattern patternNumerico = Pattern.compile(PATTERN_NUMERICO);
		Pattern patternPunto = Pattern.compile(PATTERN_PUNTO);
		Pattern patternDecimal = Pattern.compile(PATTERN_DECIMAL);
		Pattern patternSigno = Pattern.compile(PATTERN_SIGNO);

		Matcher matcherNumerico = patternNumerico.matcher(email);
		Matcher matcherPunto = patternPunto.matcher(email);
		Matcher matcherDecimal = patternDecimal.matcher(email);
		Matcher matcherSigno = patternSigno.matcher(email);

		return matcherNumerico.matches() || (matcherPunto.matches() && email.charAt(email.length() - 1) == '.')
				|| (matcherDecimal.matches()) || (matcherSigno.matches() && this.aceptaNegativo);
	}
}
