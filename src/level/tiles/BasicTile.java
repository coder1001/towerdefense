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

public class BasicTile extends Tile{

	protected int tileId;
	protected int tileColour;
	
	public BasicTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, false, false, levelColour);
		this.tileId = x+y;
		this.tileColour = tileColour;
	}

	/**
	 * Funktion zum Zeichnen der BasicTiles im Level
	 */
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColour, 0x00, 1);
	}
	/**
	 * Funktion zum Zeichnen der BasicTiles im Overlay
	 */
	public void render(Screen screen, Overlay overlay, int x, int y) {
		screen.render(x, y, tileId, tileColour, 0x00, 1);
	}

}
