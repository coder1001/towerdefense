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
 * In der Klasse GameRound werden alle Informationen über die aktuelle Spielrunde festgehalten.
 * 
 * @author Kai Flöck
 * @version 1.0
 */
public interface IGameRound {	
	
	/**
	 * Aktualisiert die Gameround
	 * 
	 * @author Kai Flöck
	 */
	public void tick();
	
	/**
	 * 	Aktualisiert das Overlay
	 * 		-> Gold
	 * 		-> Leben
	 * 		-> Aktuelle Welle
	 * 
	 *  @author Kai Flöck
	 */
	public void updateOverlay();
	
	
	public int getWidth();
	
	/**
	 * Zeichnet oben das Overlay und unten das Level
	 * 
	 * @author Kai Flöck
	 */
	public void renderTiles(Screen screen);
	
	/**
	 * Zeichnet alle Enemys und Tower
	 * 
	 * @author Kai Flöck
	 */
	public void renderEntities(Screen screen);
	
	/**
	 * Überprüft ob noch Monster einer Welle übrig sind
	 * 
	 * @author Kai Flöck
	 * @return true, falls noch Gegner übrig sind,false wenn keine Gegner mehr übrig sind
	 */
	public boolean enemyLeft();
	
	/**
	 * Wenn in einer Welle ein Gegner besiegt wird oder das Ziel erreicht, wird die Variable, welche die Anzahl
	 * der noch lebenden Gegner enthält um eins reduziert
	 * 
	 * @author Kai Flöck
	 */
	public void reduceEnemy();
	
	/**
	 * Wenn ein Gegner das Ziel erreicht, wird das Leben um den Schadenswert des Monsters verringert.
	 * Wenn das Leben des Spielersauf <= 0 fällt, wird mode auf GAME_OVER gesetzt
	 * 
	 * @author Kai Flöck
	 */
	public void reduceLife(int damage);
	/**
	 * Aktualisiert/setzt Goldwert des Spielers
	 * @param reward Wert der gutgeschrieben werden soll
	 */
	public void setGold(int reward);
	
		  
	  /*
	   * Wird vom MouseHandler aufgerufen 
	   * Mausklick wird benötigt um Tower auszuwählen und anschließend zu platzieren
	   */
	public void OnMouseClick(MouseEvent e);
    
	public void onMouseMove(Point point);
	  
}
	
	


