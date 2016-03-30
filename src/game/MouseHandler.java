package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * In der Klasse MouseHandler werden Mausaktionen ( Mausklicks) abgefangen und verarbeitet
 * 
 * @author Kai Flöck
 * @version 1.0
*/
public class MouseHandler implements MouseListener{

	
	private Game mGame;	
	
	
	public MouseHandler(Game game){
		game.addMouseListener(this);
		game.requestFocus();
		mGame = game;
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
/**
 * Mausklick an Gameround weiterleiten
 */
	public void mousePressed(MouseEvent e) {
	    mGame.gameround.OnMouseClick(e);
	  }

	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
