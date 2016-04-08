package game.entities;

import java.awt.Point;

import gfx.Screen;
/**
 * Klasse zum definieren der verchiedenen Towertypes und deren Eigenschaften
 * Wird ebenfalls verwendet um die Icons im oberen Teil des Overlays zu zeichnen
 * @author Martin
 *
 */
public interface ITowerType {


	/**
	 * Position setzen (Bspw oben im Overlay Teil)
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y);
	/**
	 * Position abfragen
	 * @return
	 */
	public Point getPosition();

	public String getName() ;

	public int getRange();

	public int getDamage();
	
	public int getPrice();

	public int[] getSprite();

	public String getSound();
	
	public int getColor();
	
	public int getEffect();
	
	public int getReloadTime();
	
	public int getLockedEnemys();
	
	public int getShotTime() ;
	
	public int getLaserColor();
	
	/**
	 * Funktion zum zeichnen des Towertype Icons im Overlay)
	 * @param screen Screen auf das gezeichnet wird, overlay
	 * @param Active Gibt an ob man genug Geld zum bauen hat (zb genug Gold)
	 */
	public void render(Screen screen, boolean Active);	
	
}
