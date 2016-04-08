package level.tiles;

import level.Level;
import level.Overlay;
import gfx.Colours;
import gfx.Screen;

/**
 * Jedes Tile ist 8x8 Pixelfeld. Diese Klasse verbindet die Farben der Level.pngs mit den Ingame-Farben
 * 
 * 
 * @author Marko Susic
 * @version 1.0
 */ 
public interface ITile {
	
	
	/**
	 * Gibt zurück, ob der Block eine Wand ist oder nicht
	 * @return
	 */
	public boolean isSolid();
	
	public boolean isEmitter();
	
	public int getLevelColour();
	
	public abstract void render(Screen screen, Level level, int x, int y);
	
	public abstract void render(Screen screen, Overlay overlay, int x, int y);
		
	

}
