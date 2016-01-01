package game.entities;

import game.GameRound;
import gfx.Colours;
import gfx.Screen;
import gfx.Sound;
import level.Level;
import level.tiles.Tile;

public abstract class Mob extends Entity{
	
	
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
	public Sound sound;
	
	public boolean freeze;
	public int fTime;
	public long fTimeStart;
	public long fTimeNow;
	
	//true if a tower is already
	private boolean locked;
	
	
	
	
	private int colour = Colours.get(-1,111,540, 543); //black , darkgrey, lightgrey, white 543 -1,111,421, 543);

		
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
		this.sound = new Sound(sound);
		this.reward = reward;
		this.damage = damage;
		
		freeze=false;
		//1 sec freeze-time
		fTime=1000;
		
		
	}
	
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
	
	public void move(int xa, int ya){
		
		fTimeNow=System.currentTimeMillis();
		//ist der Mob länger als fTime gefreezed, dann setze freeze wieder auf falsch
		if(fTimeNow-fTimeStart>fTime)
			freeze=false;
		
		
		if(xa != 0 && ya != 0){
				move(xa, 0);
				move(0, ya);
				numSteps--;
				return;
		}
		
		
		numSteps++;
		if(!hasCollided(xa,ya)){
			if(ya < 0) movingDir = 0;
			if(ya > 0) movingDir = 1;
			if(xa < 0) movingDir = 2;
			if(xa > 0) movingDir = 3;
			
			x += xa * speed;
			y += ya * speed;
		
		}
	}
	
	public abstract boolean hasCollided(int xa, int ya);
	
	protected boolean isSolidTile(int xa, int ya, int x, int y){
		if(level == null) { return false;}
		Tile lastTile = level.getTile((this.x +x)>>3, (this.y +y)>>3);
		Tile newTile = level.getTile((this.x + x + xa)>>3, (this.y + y + ya)>> 3);
		if(!lastTile.equals(newTile) && newTile.isSolid()){
			return true;
		}
		
		return false;
	}
	
	
	public void render(Screen screen) {
	
		int xTile = TileX;
		int yTile = TileY;
		
		int walkingSpeed = 3;
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if(movingDir == 1){
			xTile += 2;
		} else if(movingDir >1){
			xTile += 4 + ((numSteps >> walkingSpeed) & 1)*2;
			flipTop = (movingDir - 1) % 2;
		}
		
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 -4;		
		
		
		int lx = x-4;
		screen.DrawLine(lx, y-10, lx+15, y-10,40);
		double max = (15.0*(curlive/maxlive));
		if (max >= 15)
			max = 15;
		screen.DrawLine(lx, y-10, (int)(lx+max)  , y-10,140);
		
		screen.render(xOffset + (modifier * flipTop) , yOffset , xTile + yTile * 32, colour, flipTop,scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset , (xTile + 1) + yTile * 32, colour, flipTop,scale);
		
		screen.render(xOffset + (modifier * flipBottom) , yOffset + modifier , xTile + (yTile+1) * 32, colour, flipBottom,scale);
		screen.render(xOffset + modifier - (modifier * flipBottom) , yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, flipBottom,scale);
	}
	
	
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
