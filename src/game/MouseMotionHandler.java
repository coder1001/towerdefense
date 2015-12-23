package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import game.entities.Entity;
import level.tiles.Tile;

public class MouseMotionHandler implements MouseMotionListener{

	private static final int VOID = 0;
	private static final int STONE = 1;
	private static final int GRASS = 2;
	private static final int SAND = 3;
	private static final int END = 4;
	
	private int xPos;
	private int yPos;
	private Game mGame;
	
	public MouseMotionHandler(Game game){
		game.addMouseMotionListener(this);
		game.requestFocus();
		mGame = game;
	}
	
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//level.getTile((this.x + x + xa)>>3, (this.y + y + ya)>> 3);
	public void mouseMoved(MouseEvent e) {
		
		if(mGame.gameround != null)      
			mGame.gameround.onMouseMove(e.getPoint());
		
		xPos = e.getX();
		yPos = e.getY();
		xPos = xPos  / (8*mGame.SCALE);
		yPos = (yPos  / (8*mGame.SCALE));
		/*double xCoord = (xPos>>3);
		double yCoord = (yPos)>>3;
		Tile newTile = mGame.level.getTile((int)xCoord, (int)yCoord); 
		//System.out.println(xPos+"|"+yPos);
		if(newTile.getId()== VOID)
			System.out.println("VOID "+xCoord+"|"+yCoord);
		if(newTile.getId()== STONE)
			System.out.println("STONE "+xCoord+"|"+yCoord);
		if(newTile.getId()== GRASS)
			System.out.println("GRASS "+xCoord+"|"+yCoord);
		if(newTile.getId()== SAND)
			System.out.println("SAND "+xCoord+"|"+yCoord);
		if(newTile.getId()== END)
			System.out.println("END "+xCoord+"|"+yCoord);  */
		//System.out.println("Pos: "+xPos+"|"+yPos); 
		
		/*for(Entity en : mGame.level.entities){
			for(int i=-9;i<9;i++){
				for(int k=-9;k<9;k++){
					if((xPos+i) == en.x && (yPos+k) == en.y && en.getClass() == game.entities.Tower.class)
						System.out.println("Tower bei X:"+(en.x>>3)+" und Y:"+ (en.y>>3));
				}
			}
		
				
		}
		*/
		
	}

}
