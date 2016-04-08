package game.entities;

import java.util.ArrayList;

import gfx.Colours;
import gfx.Screen;
import level.Level;

/**
 * Die Tower Klasse erweitert die Entity Klasse
 * Es handelt sich um ein feststehendes Objekt, welches Gegner in reichweite sucht und auf diese schie�t
 * 
 * @author Martin
 *
 */
public interface ITower extends IEntity{
	
	
	/**
	 * Funktion zum setzen der Towerposition
	 * @param x
	 * @param y
	 */
	public void SetPosition(int x,int y);
	/**
	 * FUnktion um den Tower in den PLacemode zu setzen
	 * In diesem Modus kann der Tower nicht schie�en
	 * @param placemode
	 */
	public void SetPlaceMode(boolean placemode);
	/**
	 * Funktion um den Radius anzuzeigen, in dem der Tower schie�t
	 * Zum Beispiel wenn man mit der Maus dr�ber zeigt oder einen neuen Twoer zeichnet
	 * @param radiusmode
	 */
	public void SetShowRadiusMode(boolean radiusmode);
	
	/**
	 * Towerpreis ermitteln?
	 * @return
	 */
	public int GetPrice();
	
	
	/**
	 * Funktion in der die Physik Berechnungen durchgef�hrt werden
	 */
	public void tick();	
	/**
	 * Funktion zum zeichnen der Tower
	 */
	public void render(Screen screen);	
	/**
	 * Funktion f�r Kollissions�berpr�fung, wird hier nicht ben�tigt.
	 * @param xa
	 * @param ya
	 * @return
	 */
	public boolean hasCollided(int xa, int ya);

}


