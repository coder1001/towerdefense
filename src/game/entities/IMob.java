package game.entities;

import game.GameRound;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.tiles.Tile;

/**
 * Die Mob Klasse erweitert die Entity Klasse um Bewegungs und Schadens Behandlungs Routinen
 * 
 * 
 * @author  Martin Preußer
 * @version 1.0
 */ 


public interface IMob extends IEntity{
	
	//Richtungen
	
	/**
	 * Wird vom Tower aufgerufen wenn Schaden an diesem Mob verursacht wird
	 * 
	 * @param damage Schaden der verursacht wird
	 * @param effect Zusätzlicher Effekt der ausgelöst wird, z.B Freeze
	 * @return True wenn der Mob durch den zugefügten Schaden stirbt
	 */
	public Boolean DoDamage(Double damage, int effect);
	
	/**
	 * Funktion zum überprüfen obe eine Kollision statt finden (zB mit Wand)
	 * Muss von jeder KLasse selbst implementiert werden
	 * @param xa X Koordinate 
	 * @param ya Y Koordinate
	 * @param Tile Tiletyp auf das überprüft wird
	 * @param neg Gibt an ob es gleich oder ungleich dem Tile sein soll
	 * @return
	 */	
	public abstract boolean hasCollided(int xa, int ya, int Tile, boolean neg);
	

	
	/**
	 * Implementiert die Funktion zum zeichnen des Mobs
	 * @param screen
	 */
	public void render(Screen screen);
	
	//Get/Setter Funktionen um die Parameter der Mobs zu verändert
	
	public String getName();
	
	public int getReward();
	
	public int getDamage();
	
	public int getHP();
	
	public int getSpeed();
	
	public boolean isFreezed();
	
	public void setSpeed(int speed);
	
	public void setDamage(int damage);
	
	public void setReward(int reward);
	
	public void setMaxLive(int maxlive);
}
