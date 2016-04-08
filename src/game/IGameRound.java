package game;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import game.entities.Enemy;
import game.entities.EnemyType;
import game.entities.Tower;
import game.entities.TowerType;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.Overlay;
import level.tiles.Tile;

/**
 * In der Klasse GameRound werden alle Informationen �ber die aktuelle Spielrunde festgehalten.
 * 
 * @author Kai Fl�ck
 * @version 1.0
 */
public interface IGameRound {	
	
	/**
	 * Aktualisiert die Gameround
	 * 
	 * @author Kai Fl�ck
	 */
	public void tick();
	
	/**
	 * 	Aktualisiert das Overlay
	 * 		-> Gold
	 * 		-> Leben
	 * 		-> Aktuelle Welle
	 * 
	 *  @author Kai Fl�ck
	 */
	public void updateOverlay();
	
	
	public int getWidth();
	
	/**
	 * Zeichnet oben das Overlay und unten das Level
	 * 
	 * @author Kai Fl�ck
	 */
	public void renderTiles(Screen screen);
	
	/**
	 * Zeichnet alle Enemys und Tower
	 * 
	 * @author Kai Fl�ck
	 */
	public void renderEntities(Screen screen);
	
	/**
	 * �berpr�ft ob noch Monster einer Welle �brig sind
	 * 
	 * @author Kai Fl�ck
	 * @return true, falls noch Gegner �brig sind,false wenn keine Gegner mehr �brig sind
	 */
	public boolean enemyLeft();
	
	/**
	 * Wenn in einer Welle ein Gegner besiegt wird oder das Ziel erreicht, wird die Variable, welche die Anzahl
	 * der noch lebenden Gegner enth�lt um eins reduziert
	 * 
	 * @author Kai Fl�ck
	 */
	public void reduceEnemy();
	
	/**
	 * Wenn ein Gegner das Ziel erreicht, wird das Leben um den Schadenswert des Monsters verringert.
	 * Wenn das Leben des Spielersauf <= 0 f�llt, wird mode auf GAME_OVER gesetzt
	 * 
	 * @author Kai Fl�ck
	 */
	public void reduceLife(int damage);
	/**
	 * Aktualisiert/setzt Goldwert des Spielers
	 * @param reward Wert der gutgeschrieben werden soll
	 */
	public void setGold(int reward);
	
		  
	  /*
	   * Wird vom MouseHandler aufgerufen 
	   * Mausklick wird ben�tigt um Tower auszuw�hlen und anschlie�end zu platzieren
	   */
	public void OnMouseClick(MouseEvent e);
    
	public void onMouseMove(Point point);
	  
}
	
	


