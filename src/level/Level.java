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
public class Level {
	private byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	
	
	private String imagePath;
	private BufferedImage image;
	private GameRound gameround;
	
	private boolean renderRaster = false;
	private int mousex;
	private int mousey;
	
	public Level(String imagePath, GameRound gameround){
		//lädt das Level ein
		this.gameround = gameround;
		if(imagePath != null){
			this.imagePath = imagePath;
			this.loadLevelFromFile();
			
		}else{
			// Wenn das Level nicht geladen wird, wird ein default-Level geladen
			this.width = 64;
			this.height = 64;
			tiles = new byte[width*height];
			this.generateLevel();
		}
	}
	
	/**
	 *  Lädt dsa Level von einer Datei ein
	 */
	private void loadLevelFromFile() {
		try{
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width*height];	
			this.loadTiles(); 
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  Ermittelt die einzelnen Tiles aus dem Level, welches zuvor eingelesen wurde
	 */
	private void loadTiles(){
		int[] tileColours = this.image.getRGB(0,0, width, height, null, 0 , width);
		for( int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tileCheck: for(Tile t : Tile.tiles){
					if(t != null && t.getLevelColour() == tileColours[x + y * width]){
						this.tiles[x+y * width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}
	/**
	 * 
	 * Speichert ein Level als png-File auf dem PC ab
	 */
	private void saveLevelToFile(){
		try{
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Tauscht ein Tile aus
	 */
	public void alterTile(int x, int y, Tile newTile){
		this.tiles[x+y*width] = newTile.getId();
		image.setRGB(x,y,newTile.getLevelColour());
		
	}

	/**
	 *  Alternativ zum Laden eines Bildes kann das Level auch durch einen Algorithmus generiert werden.
	 *  Wird nicht benutzt, nur eine Testklasse
	 */
	public void generateLevel(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(((x*y) % 10) < 8){
					tiles[x+y*width]=Tile.GRASS.getId();
				}else{
					tiles[x+y*width]=Tile.STONE.getId();	
				} 
			}
		}
	}
	
	/**
	 * Spielelogik des Levels:
	 * 
	 * Alle Entities werden angeschaut
	 * ->ist es ein Enemy und hat kein Leben mehr 
	 * ->ist es ein Enemy und hat das Ende erreicht
	 */
	public void tick(){
		Entity rem = null;
		for(Entity e : entities){
			e.tick();
			if (e.getClass() == game.entities.Enemy.class)
			{
				Mob test = (Mob)e;
				
				if(test.curlive <= 0){
					rem = e;
					gameround.setGold(test.getReward());
					gameround.reduceEnemy();
					gameround.updateOverlay();
				}
				else if (e.getClass() == game.entities.Enemy.class)
				{
					Enemy test2 = (Enemy)e;
					// Wurde das Ziel von einem Enemy erreicht?
					if(test2.reachedEnd==true){
						rem = e;
						gameround.reduceEnemy();
						gameround.reduceLife(test2.getDamage());
						gameround.updateOverlay();
					}
					
				}
			}
		}
		if(rem != null)
		{
			Mob temp = (Mob)rem;
			//temp.sound.Start();
			//temp.sound.Stop();
			entities.remove(rem);
		}
	}
	
	/**
	 * 
	 * Zeichnet alle Tiles des Levels
	 */
	public void renderTiles(Screen screen, int xOffset, int yOffset){
		if(xOffset<0) xOffset =0;
		if(xOffset>((width<<3)-screen.width)) xOffset = ((width<<3)-screen.width);
		if(yOffset<0) yOffset =0;
		if(yOffset>((height<<3)-screen.height)) yOffset = ((height<<3)-screen.height);
		
		screen.setOffset(xOffset,yOffset);
		
		for(int y = 0; y < height;y++){
			for(int x = 0; x < width;x++){
				getTile(x,y).render(screen,this,x<<3,y<<3);
			}
		}
		renderRaster(screen);
	}
	
	
	Tile tile1;
	Tile tile2;
	Tile tile3;
	Tile tile4;
	int tilex;
	int tiley;
	boolean draw = false;
	
	/**
	 * 
	 * Zeichnet alle Tiles des Levels
	 */
	public Point TileClicked(int posx, int posy)
	{
		for(int y = 0; y < height;y++)
		{
			int tiles = 0;
			for(int x = 0; x < width;x++)
			{
				//System.out.println("x: "+(y<<3)+"("+((posy/3)+60)+")");
				if(Math.abs((x<<3)-(posx/3)) <= 4 && Math.abs((y<<3)-((posy/3))+80) <= 4)
				{
					Tile tile1 = getTile(x,y);
					Tile tile2 = getTile(x+0,y+1);
					Tile tile3 = getTile(x+1,y);
					Tile tile4 = getTile(x+1,y+1);
					tilex = x;
					tiley = y;
					draw = true;
					
					if(tile1 == Tile.GRASS && tile2 == Tile.GRASS && tile3 == Tile.GRASS && tile4 == Tile.GRASS)
					{
						//return getTile(x,y);
						
						return new Point(x<<3,y<<3);
					}
					
					System.out.println(x+"/"+y);
				
					//screen.DrawLine(x<<3, y<<3, (x<<3)+dis, y<<3, 1); 
				}
				
			}
		}
		return null;
	}
	/**
	 * Wenn true, wir das Raster gezeichnet
	 */
	public void SetRenderRaster(boolean render)
	{
		this.renderRaster = render;
	}
	/**
	 * Ändert die Mausposition
	 */
	public void SetMousePosition(int x,int y)
	{
		this.mousex = x;
		this.mousey = y;
	}
	
	/**
	 * Zeichnet das Raster beim Platzieren eines Towers
	 * 
	 * @param Auf welchem Screen soll das Raster gezeichnet werden
	 */
	public void renderRaster(Screen screen)
	{
		if (renderRaster)
		{
			int dis = 8;
			for(int y = 0; y < height;y++)
			{
				int tiles = 0;
				for(int x = 0; x < width;x++)
				{	
					
					//System.out.println("x: "+(y<<3)+"("+((mousey/3)+60)+")");
					if(Math.abs((x<<3)-((mousex/3))) <= 3 && Math.abs((y<<3)-((mousey/3))+80) <= 3)
					{
						
						
						Tile tile1 = getTile(x,y);
						Tile tile2 = getTile(x+1,y);
						Tile tile3 = getTile(x,y+1);
						Tile tile4 = getTile(x+1,y+1);
						
						int dx = x;
						int dy = y;		
						int color1 = Colours.get(555, 000,000,000);
						int color2 = Colours.get(000, 000,000,000);
						int color = color1;
						if(tile1 != Tile.GRASS)
							color = color2;
						screen.DrawLine(dx<<3, dy<<3, (dx<<3)+dis, dy<<3, color); //oben
						screen.DrawLine((dx<<3), dy<<3, (dx<<3), (dy<<3)+dis, color); //links
						screen.DrawLine((dx<<3)+dis, y<<3, (dx<<3)+dis, (dy<<3)+dis, color); //rechts					
						screen.DrawLine(dx<<3, (dy<<3)+dis, (dx<<3)+dis, (dy<<3)+dis, color); //unten
						dx = x+1;
						dy = y;		
						color = color1;
						if(tile2 != Tile.GRASS)
							color =color2;
						screen.DrawLine(dx<<3, dy<<3, (dx<<3)+dis, dy<<3, color); //oben
						screen.DrawLine((dx<<3), dy<<3, (dx<<3), (dy<<3)+dis, color); //links
						screen.DrawLine((dx<<3)+dis, y<<3, (dx<<3)+dis, (dy<<3)+dis, color); //rechts					
						screen.DrawLine(dx<<3, (dy<<3)+dis, (dx<<3)+dis, (dy<<3)+dis, color); //unten
						dx = x;
						dy = y+1;
						color = color1;
						if(tile3 != Tile.GRASS)
							color = color2;
						screen.DrawLine(dx<<3, dy<<3, (dx<<3)+dis, dy<<3, color); //oben
						screen.DrawLine((dx<<3), dy<<3, (dx<<3), (dy<<3)+dis, color); //links
						screen.DrawLine((dx<<3)+dis, y<<3, (dx<<3)+dis, (dy<<3)+dis, color); //rechts					
						screen.DrawLine(dx<<3, (dy<<3)+dis, (dx<<3)+dis, (dy<<3)+dis, color); //unten
						dx = x+1;
						dy = y+1;		
						color = color1;
						if(tile4 != Tile.GRASS)
							color = color2;
						screen.DrawLine(dx<<3, dy<<3, (dx<<3)+dis, dy<<3, color); //oben
						screen.DrawLine((dx<<3), dy<<3, (dx<<3), (dy<<3)+dis, color); //links
						screen.DrawLine((dx<<3)+dis, y<<3, (dx<<3)+dis, (dy<<3)+dis, color); //rechts					
						screen.DrawLine(dx<<3, (dy<<3)+dis, (dx<<3)+dis, (dy<<3)+dis, color); //unten
						
					}
				}
			}
			
			
			/*int x = tilex;
			int y = tiley;
			int dis = 8;
			screen.DrawLine(x<<3, y<<3, (x<<3)+dis, y<<3, 1); //oben
			screen.DrawLine((x<<3), y<<3, (x<<3), (y<<3)+dis, 1); //links
			screen.DrawLine((x<<3)+dis, y<<3, (x<<3)+dis, (y<<3)+dis, 1); //rechts					
			screen.DrawLine(x<<3, (y<<3)+dis, (x<<3)+dis, (y<<3)+dis, 1); //unten
			x = tilex+1;
			y = tiley;
			screen.DrawLine(x<<3, y<<3, (x<<3)+dis, y<<3, 1); //oben
			screen.DrawLine((x<<3), y<<3, (x<<3), (y<<3)+dis, 1); //links
			screen.DrawLine((x<<3)+dis, y<<3, (x<<3)+dis, (y<<3)+dis, 1); //rechts					
			screen.DrawLine(x<<3, (y<<3)+dis, (x<<3)+dis, (y<<3)+dis, 1); //unten
			x = tilex;
			y = tiley+1;
			screen.DrawLine(x<<3, y<<3, (x<<3)+dis, y<<3, 1); //oben
			screen.DrawLine((x<<3), y<<3, (x<<3), (y<<3)+dis, 1); //links
			screen.DrawLine((x<<3)+dis, y<<3, (x<<3)+dis, (y<<3)+dis, 1); //rechts					
			screen.DrawLine(x<<3, (y<<3)+dis, (x<<3)+dis, (y<<3)+dis, 1); //unten
			x = tilex+1;
			y = tiley+1;
			screen.DrawLine(x<<3, y<<3, (x<<3)+dis, y<<3, 1); //oben
			screen.DrawLine((x<<3), y<<3, (x<<3), (y<<3)+dis, 1); //links
			screen.DrawLine((x<<3)+dis, y<<3, (x<<3)+dis, (y<<3)+dis, 1); //rechts					
			screen.DrawLine(x<<3, (y<<3)+dis, (x<<3)+dis, (y<<3)+dis, 1); //unten*/
		}
		/*for(int y = 0; y < height;y++){
			int tiles = 0;
			for(int x = 0; x < width;x++){
				
				
				
				Tile tile1 = getTile(x+0,y);
				Tile tile2 = getTile(x+0,y+1);
				Tile tile3 = getTile(x+1,y);
				Tile tile4 = getTile(x+1,y+1);
				
				
		
				
				if(tile1 == Tile.GRASS && tile2 == Tile.GRASS && tile3 == Tile.GRASS && tile4 == Tile.GRASS)
				{
				    int dis = 16;
				
					screen.DrawLine(x<<3, y<<3, (x<<3)+dis, y<<3, 1); //oben	
					//screen.DrawLine((x<<3), y<<3, (x<<3), (y<<3)+dis, 1); //links
					//screen.DrawLine((x<<3)+dis, y<<3, (x<<3)+dis, (y<<3)+dis, 1); //rechts					
					//screen.DrawLine(x<<3, (y<<3)+dis, (x<<3)+dis, (y<<3)+dis, 1); //unten
				}
				
				//getTile(x,y).render(screen,this,x<<3,y<<3);
			}
		}
		*/
	}
	
	/**
	 * Zeichnet alle Entities ( Tower, Enemys)
	 */
	public void renderEntities(Screen screen){
		for(Entity e : entities){
			e.render(screen);
		}
	}
/**
 * Gibt das Tile an einer bestimmten X/Y Position zurück
 * 
 * @param x Koordinate
 * @param y Koordinate
 * @return Gibt das Tile zurück
 */
	public Tile getTile(int x, int y) {
		if(0 > x || x >= width || 0 > y || y >= height) 
			return Tile.VOID;
		return Tile.tiles[tiles[x+y*width]];
	}

	/**
	 * Sucht an einer Position im Level nach einem Tower
	 * 
	 * @param x x-Koord
	 * @param y y-Koord
	 * @param radius Der Radius in dem anhand der X/Y Positon aus gesucht werden soll
	 * @return Wenn sich ein Tower an dieser Stelle befindet, wird dieser zurückgegeben. 
	 * Wenn nicht dann null.
	 */
	public Tower getTower(int x,int y, int radius)
	{
		for(Entity e : entities )
		{
			if (e.getClass() == game.entities.Tower.class)
			{
		    	//System.out.println("TOOWER x:"+e.x);
		    	//System.out.println("TOOWER y:"+e.y);
				if( Math.abs(e.x-x) <= radius && Math.abs(e.y-y)<=radius)
					return (Tower)e;
			}
		}
		return null;
	}
	/**
	 * Fügt ein Enemy dem Level hinzu
	 */
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
	
	/**
	 * Entfernt ein Enemy aus dem Level
	 */
	public void removeEntity(Entity entity) {
	    this.entities.remove(entity);
	    
	  }
}
