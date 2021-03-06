package game.entities;

import gfx.Screen;
import level.Level;


/**
 * Jedes Game-Objekt (Towers,Monster,..) basieren auf dieser Klasse
 * 
 * 
 * @author  Martin Preu�er
 * @version 1.0
 */ 

public abstract class Entity implements IEntity{
	public int x,y;
	protected Level level;
	
	/**
	 * Konstruktor, welcher ein neues Entity erstellt und das Level zuweist
	 * 
	 * @param level
	 */
	public Entity(Level level){
		this.level = level;
	}	

	/**
	 * Tick muss von jeder abgeleiteten Klasse implementiert werden
	 * Die Berechnungen sollen hierdrin statt finden
	 * 
	 */
	
}
