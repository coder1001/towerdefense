package game.entities;

import game.GameRound;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.tiles.Tile;

/**
 * Die Mob Klasse erweitert die Entity Klasse um Bewegungs und Schadens Behandlungs Routinen
 * 
 * 
 * @author  Martin Preußer
 * @version 1.0
 */ 


public abstract class Mob extends Entity implements IMob{
	
	//Richtungen
	public static final int MOVE_TOP = 0;
	public static final int MOVE_BOT = 1;
	public static final int MOVE_LEFT = 2;
	public static final int MOVE_RIGHT = 3;

	
	
	// Tower Effects
	public static final int NONE = 0;
	public static final int FREEZE = 1;
	public static final int MULTISHOT = 2;
		
	protected String name;
	protected int speed;
	protected int reward;
	protected int damage;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	public int maxlive = 800;
	public double curlive = 800;
	protected int TileX = 0;
	protected int TileY = 28;
	
	protected boolean freeze;
	public int fTime;
	public long fTimeStart;
	public long fTimeNow;
	
	//true if a tower is already
	private boolean locked;	
	
	
	private int colour = Colours.get(-1,111,540, 543); //black , darkgrey, lightgrey, white 543 -1,111,421, 543);

	/**
	 * Konstruktor mit dem alle notwendigen Parameter direkt zugewiesen werden
	 * 
	 * @param level
	 * @param name
	 * @param x
	 * @param y
	 * @param speed
	 * @param xtile
	 * @param ytile
	 * @param color
	 * @param sound
	 * @param HP
	 * @param reward
	 * @param damage
	 */
	public Mob(Level level, String name, int x, int y, int speed, int xtile, int ytile, int color, String sound, int HP, int reward, int damage) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.TileX = xtile;
		this.TileY = ytile;
		this.speed = speed;
		this.colour = color;
		this.maxlive = HP;
		this.curlive = this.maxlive;
		this.reward = reward;
		this.damage = damage;
		
		freeze=false;
		//1 sec freeze-time
		fTime=1000;
		
		
	}
	
	/**
	 * Wird vom Tower aufgerufen wenn Schaden an diesem Mob verursacht wird
	 * 
	 * @param damage Schaden der verursacht wird
	 * @param effect Zusätzlicher Effekt der ausgelöst wird, z.B Freeze
	 * @return True wenn der Mob durch den zugefügten Schaden stirbt
	 */
	public Boolean DoDamage(Double damage, int effect)
	{
		curlive -= damage;
		if(effect==FREEZE){
			//System.out.println(freeze);
			freeze=true;
			fTimeStart=System.currentTimeMillis();
		}
		
		if(curlive <= 0)
		{	
			return true;
		}
		return false;		
		
	}
	
	
	/**
	 * Funktion zum überprüfen obe eine Kollision statt finden (zB mit Wand)
	 * Muss von jeder KLasse selbst implementiert werden
	 * @param xa X Koordinate 
	 * @param ya Y Koordinate
	 * @param Tile Tiletyp auf das überprüft wird
	 * @param neg Gibt an ob es gleich oder ungleich dem Tile sein soll
	 * @return
	 */	
	public abstract boolean hasCollided(int xa, int ya, int Tile, boolean neg);
	
	
	/**
	 * Überprüft ob das nächste Tile ein begehbares Feld ist
	 * @param xa
	 * @param ya
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean isSolidTile(int xa, int ya, int x, int y){
		if(level == null) { return false;}
		Tile lastTile = level.getTile((this.x +x)>>3, (this.y +y)>>3);
		Tile newTile = level.getTile((this.x + x + xa)>>3, (this.y + y + ya)>> 3);
		if(!lastTile.equals(newTile) && newTile.isSolid()){
			return true;
		}		
		return false;
	}
	
	/**
	 * Implementiert die Funktion zum zeichnen des Mobs
	 * @param screen
	 */
	public void render(Screen screen) {
			
		//Jeder Mob besteht aus 4 Tiles (von hinten, von vorne, von der seite 1, von der seite 2)
		//Bei einer Bewegeung von links->rechts oder rechts->links werden immer abwechselnd die Seiten Bilder angezeigt
		//Bei einer Bewegung nach unten wird das Bild von vorne angezeigt und immer abwechselnd gespiegelt. Sodass es so aussieht als ob sich die Arme und Beine bewegen
		//Bei einer Bewegung nach oben wird das Bild von hinten angezeigt und wieder abwechselnd gespiegelt
		
		//Das Grund-Tile wird zwischen gespeichert
		int xTile = TileX;
		int yTile = TileY;
		
		
		//Walkingspeed gibt an wie oft das Bild beim bewegen geändert wird				
		int walkingSpeed = 3;
		
		//Bei walkingSpeed 3 ist flipTop und flipBottom immer abwechselnd 8 Steps 1 und 8 Steps 0
		//Mit FlipTop wird die obere Seite des Bilds gespiegelt (Kopf)
		//Mit FlipBot die untere Seite (Füße)
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if(movingDir == MOVE_BOT){ //nach unten bewegung
			xTile += 2; //Bild von vorne anzeigen
		} else if(movingDir >MOVE_BOT){ //bewegung nach links oder rechts
			//bei walkingspeed 3, jede 8 Schritte zwischen dem 3ten und 4ten Bild wechseln
			xTile += 4 + ((numSteps >> walkingSpeed) & 1)*2;
			
			//Wenn er nach links läuft muss der Kopf gedreht werden -> d.H Bei bewegung nach Links -> flipTop=1
			flipTop = (movingDir - 1) % 2;
		}
		
		//Skaliert den Abstand zwischen den Tiles
		int modifier = 8 * scale;
		//Abweichung der Position je nach Modifier		
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 -4;				
		
		//Balken mit Energie über dem Kopf zeichnen
		int lx = x-4;
		//Linie mit vollem Leben
		screen.DrawLine(lx, y-10, lx+15, y-10,40);
		double max = (15.0*(curlive/maxlive));
		if (max >= 15)
			max = 15;
		//Linie %-restlichen Leben drüber zeichnen
		screen.DrawLine(lx, y-10, (int)(lx+max)  , y-10,140);
		
		//Jeder Mob besteht aus 4 Tiles
		//Zwei oben für den Kopf
		//Zwei unten für den Rumpf
		
		//Tile oben links
		screen.render(xOffset + (modifier * flipTop) , yOffset , xTile + yTile * 32, colour, flipTop,scale);
		//Tile oben rechts
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset , (xTile + 1) + yTile * 32, colour, flipTop,scale);
		//Tile unten links
		screen.render(xOffset + (modifier * flipBottom) , yOffset + modifier , xTile + (yTile+1) * 32, colour, flipBottom,scale);
		//tile unten rechts
		screen.render(xOffset + modifier - (modifier * flipBottom) , yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, flipBottom,scale);
	}
	
	
	//Get/Setter Funktionen um die Parameter der Mobs zu verändert
	
	public String getName(){
		return name;
	}
	
	public int getReward(){
		return reward;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getHP(){
		return this.maxlive;
	}
	
	public int getSpeed(){
		return this.speed;
	}
	
	public boolean isFreezed(){
		return this.freeze;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}
	public void setReward(int reward){
		this.reward = reward;
	}
	public void setMaxLive(int maxlive){
		this.maxlive= maxlive;
		this.curlive=this.maxlive;
	}
}
