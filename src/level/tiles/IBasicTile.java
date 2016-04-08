package level.tiles;

import gfx.Screen;
import level.Level;
import level.Overlay;

/**
 * Die Klasse für Tiles wie Gras, Sand
 * 
 * @author Marko Susic
 * @version 1.0
 */

public interface IBasicTile extends ITile{


	/**
	 * Funktion zum Zeichnen der BasicTiles im Level
	 */
	public void render(Screen screen, Level level, int x, int y);
	/**
	 * Funktion zum Zeichnen der BasicTiles im Overlay
	 */
	public void render(Screen screen, Overlay overlay, int x, int y);

}
