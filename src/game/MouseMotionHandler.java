package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import game.entities.Entity;
import level.tiles.Tile;
/**
 * In der Klasse MouseMotionHandler werden Mausaktionen ( Mausbewegungen (klick/move) abgefangen und verarbeitet
 * 
 * @author Kai Flöck
 * @version 1.0
*/
public class MouseMotionHandler implements MouseMotionListener{

	private Game mGame;
	
	public MouseMotionHandler(Game game){
		game.addMouseMotionListener(this);
		game.requestFocus();
		mGame = game;
	}
	
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Mausbewegungen an GameRound weiterreichen
	 */
	public void mouseMoved(MouseEvent e) {
		
		if(mGame.gameround != null)      
			mGame.gameround.onMouseMove(e.getPoint());
		
	}

}
