package VistaPropias;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class JTextString extends JTextField{

	Integer largo;
	
	public JTextString(Integer largo){
		super();
		this.largo = largo;
		addKey();
	}
	public JTextString(){
		super();
		this.largo = 255;
		addKey();
	}
	
	private void addKey(){
		super.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {

				  char c=e.getKeyChar(); 
		           

				  
				  
		          if( getText().length() >= largo) { 
			          getToolkit().beep();  
		              e.consume(); 
		              JOptionPane.showMessageDialog( null ,"Demasiado largo");

		          }
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
