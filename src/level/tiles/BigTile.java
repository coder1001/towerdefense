package level.tiles;

import gfx.Colours;
import gfx.FontForGame;
import gfx.Screen;
import level.Level;
import level.Overlay;

public class BigTile extends Tile{

	protected int tileId;
	protected int tileColour;
	private int[] Sprite; 
	private String text;
	
	public BigTile(int id, int x, int y, int tileColour, int levelColour, String text) {
		super(id, false, false, levelColour);
		this.tileId = x+y;
		Sprite = new int[2];
		Sprite[0] = x;
		Sprite[1] = y;
		this.tileColour = tileColour;
		this.text = text;
	}

	
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColour, 0x00, 1);
	}
	
	public void render(Screen screen, Overlay overlay, int x, int y) {

		int scale = 1;
		int modifier = 8 * scale;
		int xOffset = x - modifier/2 -4;
		int yOffset = y - modifier/2 -4;
		int colour=this.tileColour;
		int xTile = Sprite[0];
		int yTile = Sprite[1];
		screen.render(xOffset , yOffset , xTile + yTile * 32, colour);
		screen.render(xOffset + modifier, yOffset , (xTile + 1) + yTile * 32, colour);
		screen.render(xOffset , yOffset + modifier , xTile + (yTile+1) * 32, colour);
		screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour);
		
		
		FontForGame.render(text, screen,-4*modifier+x-(text.length()*(8/2)),y-modifier/2, Colours.get(-1,-1,-1,000), 1); 
		
	}

}
