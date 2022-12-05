package VistaPropias;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class JTextNum extends JTextField{

	Integer largo;
	
	public JTextNum(Integer largo){
		super();
		this.largo = largo;
		addKey();
	}
	public JTextNum(){
		super();
		this.largo = 100;
		addKey();
	}
	
	private void addKey(){
		super.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {

				  char c=e.getKeyChar(); 
				  
		          if(Character.isJavaLetter(c) || Character.isSpace(c) || Character.isLetter(c) || !Character.isUnicodeIdentifierPart(c)) { 
			          getToolkit().beep();  
		              e.consume(); 
		              JOptionPane.showMessageDialog( null ,"Ingresar numero");
		          }
		          else if (getText().length() >= largo){
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
				if (e.getKeyCode() == KeyEvent.VK_V ||
                        e.getKeyCode() == KeyEvent.VK_CONTROL ) {
					 getToolkit().beep();
					 	e.consume(); 
				}
			}
		});
	}
	
	
	
}
