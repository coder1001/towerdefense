package level;


import game.GameRound;
import game.entities.Enemy;
import game.entities.Entity;
import game.entities.Mob;
import game.entities.Tower;
import gfx.Colours;
import gfx.Screen;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import level.tiles.Tile;
/**
 * Diese Klasse beinhaltet das Level, also das Spielfeld auf dem Tower platziert werden können.
 * 
 * @author Marko Susic
 * @version 1.0
 */
public interface ILevel {

	/**
	 * 
	 * Tauscht ein Tile aus
	 */
	public void alterTile(int x, int y, Tile newTile);

	/**
	 *  Alternativ zum Laden eines Bildes kann das Level auch durch einen Algorithmus generiert werden.
	 *  Wird nicht benutzt, nur eine Testklasse
	 */
	public void generateLevel();
	/**
	 * Spielelogik des Levels:
	 * 
	 * Alle Entities werden angeschaut
	 * ->ist es ein Enemy und hat kein Leben mehr 
	 * ->ist es ein Enemy und hat das Ende erreicht
	 */
	public void tick();
	/**
	 * 
	 * Zeichnet alle Tiles des Levels
	 */
	public void renderTiles(Screen screen);
	/**
	 * 
	 * Zeichnet alle Tiles des Levels
	 */
	
	public Point TileClicked(int posx, int posy);
	/**
	 * Wenn true, wir das Raster gezeichnet
	 */
	
	public void SetRenderRaster(boolean render);
	
	/**
	 * Ändert die Mausposition
	 */
	public void SetMousePosition(int x,int y);
	
	/**
	 * Zeichnet das Raster beim Platzieren eines Towers
	 * 
	 * @param Auf welchem Screen soll das Raster gezeichnet werden
	 */
	public void renderRaster(Screen screen);
			
			
	/**
	 * Zeichnet alle Entities ( Tower, Enemys)
	 */
	public void renderEntities(Screen screen);
/**
 * Gibt das Tile an einer bestimmten X/Y Position zurück
 * 
 * @param x Koordinate
 * @param y Koordinate
 * @return Gibt das Tile zurück
 */
	public Tile getTile(int x, int y);

	/**
	 * Sucht an einer Position im Level nach einem Tower
	 * 
	 * @param x x-Koord
	 * @param y y-Koord
	 * @param radius Der Radius in dem anhand der X/Y Positon aus gesucht werden soll
	 * @return Wenn sich ein Tower an dieser Stelle befindet, wird dieser zurückgegeben. 
	 * Wenn nicht dann null.
	 */
	public Tower getTower(int x,int y, int radius);
	
	/**
	 * Fügt ein Enemy dem Level hinzu
	 */
	public void addEntity(Entity entity);
	
	
	/**
	 * Entfernt ein Enemy aus dem Level
	 */
	public void removeEntity(Entity entity);
}
