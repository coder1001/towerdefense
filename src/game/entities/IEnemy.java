package game.entities;

import game.GameRound;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.tiles.Tile;

/**
 * Enemy Klasse, fügt die Pathfinding zu dem Mob hinzu, sodass automatisch der Weg über den Sand gefunden wird
 * @author Martin
 *
 */
public interface IEnemy extends IMob{

	
	
	
	/**
	 * Wird jeden Tick aufgerufen und führt die Berechnugen für die Bewegung durch
	 */
	public void tick();	

	public boolean hasCollided(int xa, int ya, int Tile, boolean neg) ;
	
	/**
	 * Überprüft ob das Monster nicht auf einem Weg ist
	 * Ruft dazu hasCollided mit vordefinierten Parametern auf
	 * @param xa
	 * @param ya
	 * @return
	 */
	public boolean NotOnRoad(int xa, int ya);
	
	/**
	 * Überprüft ob das Monster das Ende berührt
	 * Ruft dazu hasCollided mit vordefinierten Parametern auf
	 * @param xa
	 * @param ya
	 * @return
	 */
	public boolean hitTheEnd(int xa, int ya);
	
	/**
	 * Funktion die den Mob auf der Karte bewegt
	 * 
	 * @param xa X Koordiante
	 * @param ya Y Koordinate
	 */
	public void move(int xa, int ya);
	
}

