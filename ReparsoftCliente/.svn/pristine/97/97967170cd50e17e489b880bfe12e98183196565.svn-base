package tiposPropios;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class JTextAreaCustom extends JTextArea{

	Integer largo;
	
	public JTextAreaCustom(Integer largo){
		super(15,15);
		this.largo = largo;
		addKey();
	}
	public JTextAreaCustom(){
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
