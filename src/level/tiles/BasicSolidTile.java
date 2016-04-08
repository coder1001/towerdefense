package level.tiles;
/**
 * Diese Klasse ist identsich mit BasicTile bis auf das die Tiles "solid" sind, also eine Wand,
 * durch die kein Gegner laufen kann
 * 
 * @author Marko Susic
 * @version 1.0
 */

public class BasicSolidTile extends BasicTile implements IBasicSolidTile{

	public BasicSolidTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, x, y, tileColour, levelColour);
		this.solid = true;
	}

}
