package game.entities;

import gfx.Screen;
import level.Level;


/**
 * Jedes Game-Objekt (Towers,Monster,..) basieren auf dieser Klasse
 * 
 * 
 * @author  Martin Preußer
 * @version 1.0
 */ 

public interface IEntity {
	

	/**
	 * Tick muss von jeder abgeleiteten Klasse implementiert werden
	 * Die Berechnungen sollen hierdrin statt finden
	 * 
	 */
	public void tick();
	
	/**
	 * Render muss von jeder abgeleiteten Klasse implementiert werden
	 * Die Zeichnungen werden hierdrin ausgeführt
	 * @param screen
	 */
	public void render(Screen screen);
}
