package level;

/**
 * Diese Klasse beinhaltet das Overlay, also den oberen Teil des Fensters. Hier sind 
 * Informationen wie Gold, Leben , Wave und die Tower, welche man bauen kann, zu sehen
 * 
 * @author Marko Susic
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

public class Overlay {
	private byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	private int Gold = 0;
	private int Lifes = 0;
    int posx = 220;
    int posy = 20;
    
    int Wave=1;
	
	private static final int GOLDXTILE = 2;
	private static final int GOLDYTILE = 18;
	private static final int GOLDCOLOR = Colours.get(-1, 444,550,000);
	private static final int LIFEXTILE = 0;
	private static final int LIFEYTILE = 18;
	private static final int LIFECOLOR = Colours.get(-1, 500,000,000);
	
	private String imagePath;
	private BufferedImage image;
	
	public List<TowerType> towertypes;
	
	public Overlay(String imagePath){
		
		if(imagePath != null){
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		}		
	}
	
	/**
	 * Fügt eine Liste von TowerTypes dem Overlay hinzu
	 * @param towertypes ArrayList von TowerTypes
	 */
	public void AddTowerTypes(ArrayList<TowerType> towertypes)
	{
		this.towertypes = towertypes;
	}
	
	public void setGold(int gold)
	{
		this.Gold = gold;
	}
	
	public void setWave(int wave)
	{
		this.Wave = wave;
	}
	
	public void setLifes(int lifes)
	{
		this.Lifes = lifes;
	}
	
	/**
	 *  Lädt dsa Overlay von einer Datei ein
	 */
	private void loadLevelFromFile() {
		try{
			this.image = ImageIO.read(Overlay.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width*height];	
			this.loadTiles(); 
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 *  Ermittelt die einzelnen Tiles aus dem Overlay, welches zuvor eingelesen wurde
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
	
	public void alterTile(int x, int y, Tile newTile){
		this.tiles[x+y*width] = newTile.getId();
		image.setRGB(x,y,newTile.getLevelColour());
	}

	/**
	 * 
	 * Zeichnet alle Tiles des Overlays
	 */
	
	public void renderTiles(Screen screen, int xOffset, int yOffset){
		/*if(xOffset<0) xOffset =0;
		if(xOffset>((width<<3)-screen.width)) xOffset = ((width<<3)-screen.width);
		if(yOffset<0) yOffset =0;
		if(yOffset>((height<<3)-screen.height)) yOffset = ((height<<3)-screen.height);
		*/
		xOffset = 0;
		yOffset = 0; 
		screen.setOffset(xOffset,yOffset); 
		
		for(int y = 0; y < height;y++){
			for(int x = 0; x < width;x++){
				getTile(x,y).render(screen,this,x<<3,y<<3);
			}
		}
		
	}
	
	/**
	 * 
	 * Zeichnet ein Tile des Overlays
	 */
	public void render(Screen screen, int xOffset, int yOffset)
	{
		renderTiles(screen, xOffset, yOffset);
		renderTowertypes(screen);
		//renderEntities(screen);
		renderStats(screen);
	}
	
	/**
	 * 
	 * Zeichnet alle TowerTypes des Overlays
	 */
	public void renderTowertypes(Screen screen){
		int i = 0;
		int k = 15;
		for(TowerType type : this.towertypes)
		{
			if(i == 210)
			{
				k+=30;
				i = 0;
			}
			Boolean enoughmoney = true;
			if( type.getPrice() > this.Gold)
				enoughmoney = false;
			type.setPosition(30+i,k);
			type.render(screen,enoughmoney);

			i+=70;
		}
	}	

	/**
	 * 
	 * Schaut bei einem Mausklick im Overlay, ob ein TowerType angeklickt wurde
	 * @param x Koordinate
	 * @param y Koordinate
	 * @return Gibt den TowerType an, welcher angeklickt wurde. Wenn nix angeklickt wurde, gibt
	 * die Funktion null zurück.
	 */
	public TowerType TowerTypeClicked(int x,int y)
	{
			System.out.println("click: ("+(x-15)+"/"+y+")");
			
			for(int i = 0;i<towertypes.size();i++)
			{
				TowerType towerType = towertypes.get(i);
				Point position = towerType.getPosition();
				position = new Point(position.x*3, position.y*3);
				if(Math.abs(position.x-(x-15)) <= 20 && Math.abs(position.y-y) <= 20  )
				{
					if(this.Gold >= towerType.getPrice())
						return towerType;
					//System.out.println("Hit bei: "+i);
				}
				//if( AbstractMethodError()position.x - (x-15)  )
			}
			return null;
			/*System.out.println(towertypes.get(0).getPosition().getX()*3+"/"+towertypes.get(0).getPosition().getY());
			System.out.println(towertypes.get(1).getPosition().getX()*3+"/"+towertypes.get(1).getPosition().getY());
			System.out.println(towertypes.get(2).getPosition().getX()*3+"/"+towertypes.get(2).getPosition().getY());
			System.out.println(towertypes.get(3).getPosition().getX()*3+"/"+towertypes.get(3).getPosition().getY());*/
	}
	/**
	 * 
	 * Zeichnet alle Statistiken im aktuellen Spiel:
	 * Gold, Leben und aktuelle Gegnerwelle
	 * @param screen
	 */
	public void renderStats(Screen screen)
	{        
		screen.renderBigTile(posx, posy, GOLDXTILE, GOLDYTILE, GOLDCOLOR, 1);
		gfx.FontForGame.render(String.valueOf(Gold), screen, posx+15, posy-5, Colours.get(-1, 000, 000, 000), 1);
		
		screen.renderBigTile(posx, posy+15, LIFEXTILE, LIFEYTILE, LIFECOLOR, 1);
		gfx.FontForGame.render(String.valueOf(Lifes), screen, posx+15, posy+15-5, Colours.get(-1, 000, 000, 000), 1);
		
		//screen.renderBigTile(posx, posy+30, LIFEXTILE, LIFEYTILE, LIFECOLOR, 1);
		gfx.FontForGame.render("Wave "+String.valueOf(Wave), screen, posx, posy+40-5, Colours.get(-1, 000, 000, 000), 1);
	}
	
	public void renderEntities(Screen screen){
		for(Entity e : entities){
			e.render(screen);
		}
	}

	public Tile getTile(int x, int y) {
		if(0 > x || x >= width || 0 > y || y >= height) 
			return Tile.VOID;
		return Tile.tiles[tiles[x+y*width]];
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
		
	}
}
