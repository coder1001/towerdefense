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
public abstract class Tile {
	
	// Die ersten 8 Zeilen im Spritesheet sind für Tiles vorgesehen (8x32=256)
	public static final Tile[] tiles = new Tile[256];
	
	/*
	 * Hier werden die verschiedenen Tiles definiert. Ganz rechts die Farbe, welches der Block im 
	 * Level.png hat, und links daneben die 4 Farben ,welche der Block im Spiel hat.
	 * 
	 * Man unterscheidet zwischen BasicTile und BasicSolidTile. BasicSolidTiles sind Blöcke wie Wände,
	 * auf denen nichts stehen kann
	 */
	public static final Tile VOID = new BasicSolidTile(0,0,0, Colours.get(555, -1, -1, -1), 0xFF000000);
	public static final Tile STONE = new BasicSolidTile(1,1,0, Colours.get(-1, 333,-1,-1), 0xFF555555);
	public static final Tile GRASS = new BasicTile(2,2,0, Colours.get(-1, 131,141,-1), 0xFF00FF00);
	public static final Tile SAND = new BasicTile(3,3,0, Colours.get(-1, 431,431,-1), 0xFFCFCD2B);  //Farbe Ingame / Farbe im Bild
	public static final Tile END = new BasicTile(4,4,0, Colours.get(-1, 111,300,500), 0xFFFF0018);
	
	//public static final Tile MONEY = new BigTile(5,2,18, Colours.get(-1, 444,550,000), 0xFF0000FF, "Money");
	//public static final Tile LIFE = new BigTile(6,0,18, Colours.get(-1, 500,000,000), 0xFFFF0000, "Leben");
	
	
	/*
	 * Overlay Tiles für die 4 Seiten und die Mitte
	 */	
	public static final Tile oTOP = new BasicTile(8,6,0, Colours.get(3, 345,300,333), 0xFF8bc41d);
	public static final Tile oBOT = new BasicTile(14,12,0, Colours.get(-1, 345,300,333), 0xFFb0e152);
	public static final Tile oLEFT = new BasicTile(10,8,0, Colours.get(-1, 345,300,333), 0xFFc9f377);
	public static final Tile oRIGHT = new BasicTile(12,10,0, Colours.get(-1, 345,300,333), 0xFF9ed437);
	public static final Tile oMID = new BasicTile(11,9,0, Colours.get(-1, 345,555,555), 0xFFe3fb92);
	
	/*
	 * Overlay Tiles für die 4 Ecken
	 * 	TopLeft, TopRight, BottomLeft, BottomRight
	 */	
	public static final Tile oTL = new BasicTile(7,5,0, Colours.get(-1, 345,300,333), 0xFF82b81b);
	public static final Tile oTR = new BasicTile(9,7,0, Colours.get(-1, 345,300,333), 0xFF96cf29);
	public static final Tile oBL = new BasicTile(13,11,0, Colours.get(-1, 345,300,333), 0xFFbdec62);
	public static final Tile oBR = new BasicTile(15,13,0, Colours.get(-1, 345,300,333), 0xFFa7db42);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColour;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour){
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Duplicate tile id on"+id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColour = levelColour;
		
		tiles[id] = this;
	}
	
	public byte getId(){
		return id;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public boolean isEmitter(){
		return emitter;
	}
	
	public int getLevelColour(){
		return levelColour;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);
	
	public abstract void render(Screen screen, Overlay overlay, int x, int y);
		
	

}
