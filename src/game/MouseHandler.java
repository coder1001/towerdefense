package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

	public void mousePressed(MouseEvent e) {
	    mGame.gameround.OnMouseClick(e);
	  }

	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
