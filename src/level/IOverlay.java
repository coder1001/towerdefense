package level;

/**
 * Diese Klasse beinhaltet das Overlay, also den oberen Teil des Fensters. Hier sind 
 * Informationen wie Gold, Leben , Wave und die Tower, welche man bauen kann, zu sehen
 * 
 * @author Martin Preußer
 * @version 1.0
 */
import game.entities.Entity;
import game.entities.Mob;
import game.entities.Tower;
import game.entities.TowerType;
import gfx.Colours;
import gfx.Screen;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

//import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import level.tiles.Tile;

public interface IOverlay {

	/**
	 * Fügt eine Liste von TowerTypes dem Overlay hinzu
	 * @param towertypes ArrayList von TowerTypes
	 */
	public void AddTowerTypes(ArrayList<TowerType> towertypes);
	
	public void setGold(int gold);
	
	public void setWave(int wave);
	
	public void setLifes(int lifes);
	
	
	public void alterTile(int x, int y, Tile newTile);

	/**
	 * 
	 * Zeichnet alle Tiles des Overlays
	 */
	
	public void renderTiles(Screen screen);
	
	/**
	 * 
	 * Zeichnet ein Tile des Overlays
	 */
	public void render(Screen screen);
	
	/**
	 * 
	 * Zeichnet alle TowerTypes des Overlays
	 */
	public void renderTowertypes(Screen screen);
	
	/**
	 * 
	 * Schaut bei einem Mausklick im Overlay, ob ein TowerType angeklickt wurde
	 * @param x Koordinate
	 * @param y Koordinate
	 * @return Gibt den TowerType an, welcher angeklickt wurde. Wenn nix angeklickt wurde, gibt
	 * die Funktion null zurück.
	 */
	public TowerType TowerTypeClicked(int x,int y);
	
	/**
	 * 
	 * Zeichnet alle Statistiken im aktuellen Spiel:
	 * Gold, Leben und aktuelle Gegnerwelle
	 * @param screen
	 */
	public void renderStats(Screen screen);
	
	public void renderEntities(Screen screen);

	public Tile getTile(int x, int y);

	public void addEntity(Entity entity);
	
}
