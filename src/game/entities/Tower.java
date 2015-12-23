package game.entities;

import gfx.Colours;
import gfx.Screen;
import gfx.Sound;
import level.Level;

public class Tower extends Entity{
	
	protected int scale = 1;	
	private int colour = Colours.get(-1,111,500, 543); //black , darkgrey, lightgrey, white 543 -1,111,421, 543);
	Mob lockedEnemy, lockedEnemy2;
	Sound sound;
	private int range = 30;
	private double damage = 20;
	private int price = 100;
	public String name;
	private int[] Sprite;
	boolean inPlaceMode = false;
	
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
		
		this.inPlaceMode = false;
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
		
		
		//bereits gegner anvisiert ?
		if(lockedEnemy != null)
		{
			//found = true;
			if(IsInRange(lockedEnemy))
			{
				if(lockedEnemy.DoDamage(this.damage))
				{				
					lockedEnemy = null;
				}
			}
			else
			{
				//sound.Stop();
				lockedEnemy = null;
			}
		}
		else		
		//Alle Entitys Durchloopen
		for(Entity e : level.entities)
		{ 
		    //Es interessieren uns nur die Enemys	
			if(e.getClass() != game.entities.Tower.class && !e.equals(lockedEnemy2))
			{			
				//found = true;
				//Wenn noch kein Gegner eingeloggt -> Enemy speichern
				if(lockedEnemy == null && IsInRange(e))
				{
				  //sound.Start();
									
				  lockedEnemy = (Mob)e;
				} //Wenn gespeicherter nicht mehr in range -> löschen
				/*else if(!IsInRange(lockedEnemy))
				{
				  sound.Stop();
				  lockedEnemy = null;
				}
				else
				{
					//System.out.println(this.damage);
					if(lockedEnemy.DoDamage(this.damage))
					{				
						lockedEnemy = null;
					}
				}*/
				
			}

		}
		//if(!found)
		//	lockedEnemy=null;
		
		if(this.name=="Multi 1" ||
		   this.name=="Multi 2"	)
		{
		if(lockedEnemy2 != null)
		{
			//found = true;
			if(IsInRange(lockedEnemy2))
			{
				if(lockedEnemy2.DoDamage(this.damage))
				{				
					lockedEnemy2 = null;
				}
			}
			else
			{
				//sound.Stop();
				lockedEnemy2 = null;
			}
		}
		else		
		//Alle Entitys Durchloopen
		for(Entity e : level.entities)
		{ 
		    //Es interessieren uns nur die Enemys	
			if(e.getClass() != game.entities.Tower.class && !e.equals(lockedEnemy))
			{			
				//found = true;
				//Wenn noch kein Gegner eingeloggt -> Enemy speichern
				if(lockedEnemy2 == null && IsInRange(e))
				{
				  //sound.Start();
									
				  lockedEnemy2 = (Mob)e;
				} //Wenn gespeicherter nicht mehr in range -> löschen
				/*else if(!IsInRange(lockedEnemy))
				{
				  sound.Stop();
				  lockedEnemy = null;
				}
				else
				{
					//System.out.println(this.damage);
					if(lockedEnemy.DoDamage(this.damage))
					{				
						lockedEnemy = null;
					}
				}*/
				
			}

		}
		}
	}

	public void render(Screen screen) {
		int xTile = Sprite[0];
		int yTile = Sprite[1];		
	
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 -4;
		
		if(lockedEnemy != null)
		{
			screen.DrawLine(x+5, y-5, lockedEnemy.x,lockedEnemy.y,40);
		}
		
		if(lockedEnemy2 != null)
		{
			screen.DrawLine(x+5, y-5, lockedEnemy2.x,lockedEnemy2.y,40);
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


