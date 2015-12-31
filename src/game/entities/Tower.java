package game.entities;

import java.util.ArrayList;

import gfx.Colours;
import gfx.Screen;
import gfx.Sound;
import level.Level;

public class Tower extends Entity{
	
	protected int scale = 1;	
	private int colour = Colours.get(-1,111,500, 543); //black , darkgrey, lightgrey, white 543 -1,111,421, 543);
	
	Sound sound;
	private int range = 30;
	private double damage = 20;
	private int price = 100;
	public String name;
	private int[] Sprite;
	boolean inPlaceMode = false;
	private int laserColor;
	
	
	/*
	 *  Wenn ein Gegner Schaden erleidet, wird neben dem Schaden auch der Effekt mitgegeben
	 *  F¸r Effekte wie z.B Freeze/Stun
	 */
	int effect;
	
	/*
	 *  reloadTime ist die Nachladezeit
	 *  WENN reloadStart-reloadNow > reloadTime -> Tower kann wieder schieﬂen -> readyToShot = true
	 */
	private int reloadTime;
	private long reloadStart;
	private long reloadNow;
	private boolean readyToShot;
	
	/*
	 *  shotTime ist die Anzahl der Ticks, welche ein Turm schieﬂen kann bevor er nachladen muss
	 *  shotTimeCounter z‰hlt von 0 an bis shotTime hoch
	 */
	private int shotTime;
	private int shotTimeCount;
	
	/*
	 *  Im ARRAY werden alle gelockten Gegner gespeichert
	 *  Im INT ist die Anzahl der maximal gleichzeitig gelockten Gegner gespeichert
	 */
	public ArrayList<Mob> LockedEnemys;
	private int lockedEnemyNumber;
	
	
	public Tower(Level level,  int x, int y,TowerType towertype) {
		super(level);		
		this.x = x;
		this.y = y;
		
		name = towertype.getName();
		range = towertype.getRange();
		damage = towertype.getDamage();
		Sprite = towertype.getSprite();
		sound = new Sound(towertype.getSound());
		colour = towertype.getColor();
		price = towertype.getPrice();
		effect = towertype.getEffect();
		reloadTime = towertype.getReloadTime();
		lockedEnemyNumber=towertype.getLockedEnemys();
		shotTime = towertype.getShotTime();
		this.inPlaceMode = false;
		this.readyToShot=true;
		this.laserColor=towertype.getLaserColor();
		LockedEnemys = new ArrayList<Mob>();
		
		
		shotTimeCount=0;
		
		
		
	}

	public void SetPosition(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void SetPlaceMode(boolean placemode)
	{
		this.inPlaceMode = placemode;
	}
	
	public int GetPrice()
	{
		return price;
	}
	
	private Boolean IsInRange(Entity e)
	{
		if(e == null)
			return false;
		double distance = Math.sqrt( ((e.x-x)*(e.x-x))+((e.y-y)*(e.y-y)) );
		if(distance <= range)
			return true;
		return false;		
	}
	
	
	long lastTime = System.nanoTime();
	
	public void tick() {		
		if (this.inPlaceMode)
    	  return;
      
		//long curTime = System.nanoTime();
		//System.out.println("Zeit: "+(curTime-lastTime));
		//lastTime = curTime;
		
		
		//Boolean found = false;
		
		
		//Wenn der Tower noch nicht seine Maximalanzahl an Gegner im Visier hat...
		if(LockedEnemys.size()<lockedEnemyNumber){
			for(Entity e : level.entities)
			{ 
				
			    //Es interessieren uns nur die Enemys, die noch nicht im Visier sind und in Reichweite sind
				if(e.getClass() != game.entities.Tower.class && !LockedEnemys.contains(e) && IsInRange(e))
				{		
					  //sound.Start();
					Mob tempo = (Mob) e;
					if(!(this.effect==1 && tempo.isFreezed()==true)){
						LockedEnemys.add((Mob)e);
						if(LockedEnemys.size()==lockedEnemyNumber)
							break;
					}
				}
			}
				
		}
		//Gehe alle im Visier befindlichen Gegner durch...
		for(Mob lockedEnemy : LockedEnemys){
		
			reloadNow=System.currentTimeMillis();
			//wenn der tower nachgeladen hat ist er wieder bereit zu schieﬂen
			if(reloadNow-reloadStart>reloadTime && readyToShot == false){
				readyToShot=true;
				shotTimeCount=0;
			}
			
			//Schieﬂe wenn Enemy in Reichweite ist, nicht gefreezed ist und reloadTime vorbei ist
			if(IsInRange(lockedEnemy) && readyToShot==true)
			{
				//true, wenn Gegner nach dem Schaden keine HP mehr hat
				if(lockedEnemy.DoDamage(this.damage, this.effect)){
					LockedEnemys.remove(lockedEnemy);
					break;
				}
				else{
					shotTimeCount++;
					//Wenn der Tower l‰nger als shotTime geschossen hat, muss er nachladen...
					if(shotTimeCount>=shotTime){
						readyToShot=false;
						reloadStart=System.currentTimeMillis();
						}
					}
			}
			else
			{
				// Entferne den Gegner, wenn er auﬂerhalb der Reichweite ist
				if(!IsInRange(lockedEnemy)){
					LockedEnemys.remove(lockedEnemy);
					break;		
				}
			}
		}	
	}//end tick
	
	
	public void render(Screen screen) {
		int xTile = Sprite[0];
		int yTile = Sprite[1];		
	
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 -4;
		
		for(Mob lockedEnemy : LockedEnemys){
			if(readyToShot == true)
				screen.DrawLine(x+5, y-5, lockedEnemy.x,lockedEnemy.y,this.laserColor);
		}
		
		
		if(this.inPlaceMode)
			screen.DrawCircle(x+3, y+1, this.range, 215);
		
		screen.render(xOffset , yOffset , xTile + yTile * 32, colour);
		screen.render(xOffset + modifier, yOffset , (xTile + 1) + yTile * 32, colour);
		screen.render(xOffset , yOffset + modifier , xTile + (yTile+1) * 32, colour);
		screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour);
	}
	
	public boolean hasCollided(int xa, int ya) {
		return false;
	}

}


