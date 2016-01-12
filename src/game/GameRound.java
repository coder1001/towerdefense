package game;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import game.entities.Enemy;
import game.entities.EnemyType;
import game.entities.Tower;
import game.entities.TowerType;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.Overlay;
import level.tiles.Tile;

/**
 * In der Klasse GameRound werden alle Informationen über die aktuelle Spielgrunde festgehalten.
 * 
 * @author Marko Susic
 * @version 1.0
 */
public class GameRound {

	
	// Constants
	public static final int WAVE_SIZE = 10;
	public static final int TIME_NEXT_WAVE = 5;
	
	// Game Modes
	public static final int WAIT = 1;
	public static final int WAVE_ON = 2;
	public static final int GAME_OVER = 3;
	
	// Tower Effects
	public static final int NONE = 0;
	public static final int FREEZE = 1;
	
	
	
	
	
	public int mode;
	
	public int gold;	
	public int health;
	public int wave;
	public int minionsLeft;
	private int minionCounter;
	private int msNextMinion;
	
	private int newWaveCounter;
	
	Random rn;
	
	long lastTimer, nowTimer;
	boolean bnew;
	
	public Level level;
	private Overlay overlay;
	private int mScale;
	
	private int lastEnemyHP;
	private int lastEnemyReward;
	
	
	public ArrayList<TowerType> TowerTypes;// = new ArrayList<TowerTypes>();
	public ArrayList<EnemyType> EnemyTypes;// = new ArrayList<TowerTypes>();
	public ArrayList<Enemy> EnemyList;
	
	public GameRound(String levelPath, String overlayPath, InputHandler handler, int scale){
		mScale = scale;
		gold=500;
		health=20;
		wave=1;
		minionsLeft=0;
		minionCounter=0;
		bnew=false;
		msNextMinion=1000;
		level = new Level(levelPath, this);
		overlay = new Overlay(overlayPath);
		//player = new Player(level, 8,8,handler);
		//level.addEntity(player);
		rn = new Random();
		EnemyList = new ArrayList<Enemy>();
		
		int reward_mult;
		int hp_mult;
		int speed_mult;
		int damage_mult;
		EnemyTypes = new ArrayList<EnemyType>();
		//								Name,   HP,Speed, SpriteX,SpriteY, Sound,color,reward,damage
		
		EnemyTypes.add(new EnemyType("EnemyPlayer",500,1,0,28,"death1.wav", Colours.get(-1,111,540, 543),10,1));
		EnemyTypes.add(new EnemyType("Auge"       ,700,1,0,26,"death2.wav", Colours.get(-1,413,050, 543),10,1));
		EnemyTypes.add(new EnemyType("Ghost"      ,1000,1,8,28,"death3.wav", Colours.get(-1,413,050, 543),20,1));
		EnemyTypes.add(new EnemyType("Swarm"      ,900,2,8,26,"death4.wav", Colours.get(-1,111,222, 121),20,2));
		
		lastEnemyHP=EnemyTypes.get(0).getHP();
		lastEnemyReward=EnemyTypes.get(0).getReward();
		TowerTypes = new ArrayList<TowerType>();
		
		/*
		 *  Overlay Tower-Anzeige : Untere Reihe
		 */
		TowerTypes.add( new TowerType("Laser 1",50,40,200,4,22,"laser1.wav",Colours.get(-1,111,500, 543),NONE, 1000,10,1,Colours.getc(400)));
		TowerTypes.add( new TowerType("Multi 1",30, 8,300,0,22,"laser1.wav",Colours.get(-1,111,500, 543),NONE, 1,100,3,Colours.getc(405)));
		TowerTypes.add( new TowerType("Ice 1"  ,30, 30,300,6,22,"laser1.wav",Colours.get(-1,111,005, 543),FREEZE, 2000,5,1,Colours.getc(005)));
		
		
		/*
		 *  Overlay Tower Anzeige : Untere Reihe
		 */								
		//								Name Range DMG Price
		TowerTypes.add( new TowerType("Laser 2",65,50,450,2,22,"laser1.wav",Colours.get(-1,111,253, 543),NONE, 300,10,1,Colours.getc(400)));
		TowerTypes.add( new TowerType("Multi 2",35,20,600,0,22,"laser1.wav",Colours.get(-1,111,225, 533),NONE, 1,100,4,Colours.getc(405)));
		TowerTypes.add( new TowerType("Ice 2"  ,35,100,650,8,22,"laser1.wav",Colours.get(-1,111,005, 035),FREEZE, 1500,5,2,Colours.getc(005)));
		
		overlay.AddTowerTypes(TowerTypes);
		updateOverlay();
		
		createNewWave();
		mode=WAVE_ON;
	}
	
	
	
	
	/**
	 * Aktualisiert die Gameround
	 * 
	 * @author Marko Susic
	 */
	public void tick(){
		
		if(bnew == false)
		{
			lastTimer = System.currentTimeMillis();
			bnew = true;
		}
		nowTimer = System.currentTimeMillis();
		
		if(nowTimer-lastTimer > msNextMinion && mode==WAVE_ON){
			bnew = false;
			if(minionCounter<WAVE_SIZE)
				level.addEntity(EnemyList.get(minionCounter));
			minionCounter++;
		}
		
		if(minionsLeft<=0 && mode == WAVE_ON){
			mode=WAIT;
			wave++;
			this.updateOverlay();
			minionCounter=0;
			newWaveCounter=TIME_NEXT_WAVE;
			createNewWave();
		}
		
		if(nowTimer-lastTimer > 1000 && mode==WAIT){
			bnew = false;
			if(newWaveCounter>0)
			{
				newWaveCounter--;
				//overlay.setCounter(newWaveCounter);
				if(newWaveCounter==0)
					mode=WAVE_ON;
			}
		}
		
		level.tick();
	
		
	}
	
	/**
	 * 	Aktualisiert das Overlay
	 * 		-> Gold
	 * 		-> Leben
	 * 		-> Aktuelle Welle
	 * 
	 *  @author Marko Susic
	 */
	public void updateOverlay(){
		
		overlay.setGold(gold);
		overlay.setLifes(health);
		overlay.setWave(wave);
	}
	
	
	public int getWidth(){
		return level.width;
	}
	
	/**
	 * Zeichnet oben das Overlay und unten das Level
	 * 
	 * @author Marko Susic
	 */
	public void renderTiles(Screen screen){
		int xOffset = screen.width/2;
		int yOffset = screen.height/2;
		overlay.render(screen, 0, 0);
		level.renderTiles(screen,  xOffset, yOffset);
		
	}
	
	/**
	 * Zeichnet alle Enemys und Tower
	 * 
	 * @author Marko Susic
	 */
	public void renderEntities(Screen screen){
		level.renderEntities(screen);
	}
	
	/**
	 * Überprüft ob noch Monster einer Welle übrig sind
	 * 
	 * @author Marko Susic
	 * @return true, falls noch Gegner übrig sind,false wenn keine Gegner mehr übrig sind
	 */
	public boolean enemyLeft(){
		if(minionsLeft == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Wenn in einer Welle ein Gegner besiegt wird oder das Ziel erreicht, wird die Variable, welche die Anzahl
	 * der noch lebenden Gegner enthält um eins reduziert
	 * 
	 * @author Marko Susic
	 */
	public void reduceEnemy(){
		if(minionsLeft>0)
			minionsLeft--;	
	}
	
	/**
	 * Wenn ein Gegner das Ziel erreicht, wird das Leben um den Schadenswert des Monsters verringert.
	 * Wenn das Leben des Spielersauf <= 0 fällt, wird mode auf GAME_OVER gesetzt
	 * 
	 * @author Marko Susic
	 */
	public void reduceLife(int damage){
		
		health-=damage;
		if(health<=0)
			mode=GAME_OVER;
		
	}
	
	public void setGold(int reward){
		
		this.gold+=reward;
	
}
	
	/**
	 * Erstellt eine neue Welle von Enemy
	 * 1. Setze minionsLeft zurück auf die Wave Size
	 * 2. Lösche alle Enemy aus der EnemyList
	 * 3. Füge einen neuen Gegnertyp x-Mal in die Liste
	 * 
	 * @author Marko Susic
	 */
	private void createNewWave(){
		
		minionsLeft=WAVE_SIZE; 
		
		/*Erstellt fortlaufend eine neue Welle vom nächsten Gegnertyp. 
		 * Ist der Gegnertyp der letzte gewesen, wird wieder der erste erstellt
		 */
		EnemyType newEnemyType = EnemyTypes.get((wave-1)%EnemyTypes.size());
		//Die Farben sind alle zufällig gewählt
		newEnemyType.setColor(Colours.get(-1,rn.nextInt(556),rn.nextInt(556), rn.nextInt(556)));
		//Die alte Liste wird geleert. 
		EnemyList.clear();
		
		for(int i=0;i<WAVE_SIZE;i++){
			EnemyList.add(new Enemy(level,-20,20,newEnemyType));
			
			// HP und Goldbelohnunung werden dynamisch an die Welle angepasst. 
			// Auf die Werte HP und Reward in EnemyType wird hier nicht zurückgegriffen.
			EnemyList.get(i).setMaxLive((int)(lastEnemyHP*1.15));
			EnemyList.get(i).setReward((int)(lastEnemyReward+1));
		}
		
		
		lastEnemyHP = EnemyList.get(0).maxlive;
		lastEnemyReward = EnemyList.get(0).getReward();
	}
	
	
	
	Tower towerPlace = null;
	  
	  /*
	   * Wird vom MouseHandler aufgerufen 
	   * Mausklick wird benötigt um Tower auszuwählen und anschließend zu platzieren
	   */
	public void OnMouseClick(MouseEvent e)
    {
		System.out.println(e.getButton());
		//level.TileClicked(x, y);
		
		//Schauen ob oben einer der Towertypen aus der oberen Zeile angeklickt wurde
		int x = e.getX();
		int y = e.getY();
		TowerType type = overlay.TowerTypeClicked(x,y);
      
		//Mit rechtsklick bauen abbrechen und tower löschen
		if(e.getButton() == 3)
		{
			if(towerPlace != null)
	        {
	          level.removeEntity(towerPlace);
	          towerPlace = null;
	          level.SetRenderRaster(false);
	        }        
	    }
	      
		//Wenn ein towertype ausgewählt wurde und zurzeit kein tower platziert wird
		if(type != null && towerPlace == null)
		{    	
			towerPlace = new Tower(level, x/mScale-5, y/mScale-80, type);
			towerPlace.SetPlaceMode(true);
			level.addEntity(towerPlace);
		
			level.SetRenderRaster(true);
		}//wenn schon ein tower platziert wird, entgültig setzen
		else if(towerPlace != null)
		{			
			Point tilepos = level.TileClicked(x, y);
			//System.out.println("test bei: "+(tilepos.x+5));
			
			if(tilepos != null)
			{
				
				//testen ob noch platz frei ist
				if(level.getTower(tilepos.x+5, tilepos.y+5,0) != null //genau auf der stelle
						|| level.getTower(tilepos.x+5+8, tilepos.y+5,0) != null //links
						|| level.getTower(tilepos.x+5-8, tilepos.y+5,0) != null //rechts
						|| level.getTower(tilepos.x+5, tilepos.y+5+8,0) != null //oben
						|| level.getTower(tilepos.x+5, tilepos.y+5-8,0) != null //unten
					
						|| level.getTower(tilepos.x+5+8, tilepos.y+5+8,0) != null //oben rechts
						|| level.getTower(tilepos.x+5-8, tilepos.y+5+8,0) != null //oben links
						|| level.getTower(tilepos.x+5+8, tilepos.y+5-8,0) != null //unten rechts
						|| level.getTower(tilepos.x+5-8, tilepos.y+5-8,0) != null) //unten links
				{
					//System.out.println("TOWER");
					return;
				}				
				
				//Point tilepos = new Point(x,y);
				towerPlace.SetPosition(tilepos.x+5, tilepos.y+5);				
				System.out.println("geplaced bei: "+towerPlace.x);	
				System.out.println("geplaced bei: "+towerPlace.y);	
				towerPlace.SetPlaceMode(false);
				//überprüfen ob wiese
				level.SetRenderRaster(false);
				this.gold -= towerPlace.GetPrice();
				this.updateOverlay();
				towerPlace = null;    
			}
		}
    }
	  
	  Tower mouseOverTower = null;
	
	  public void onMouseMove(Point point)
	  {    
	    //Wenn gerade ein Tower platziert wird, die Position je nach Mausposition anpassen
	    if(towerPlace != null)
	    {
	      towerPlace.SetPosition((point.x/mScale-5),(point.y/mScale)-80);
	      level.SetMousePosition(point.x,point.y);
	    }
	    else
	    {
	    	//Wenn die Maus bei der letzten bewegung über einem Tower war
	    	if(mouseOverTower != null)
	    	{
	    		//schauen ob jetzt immernoch ein Tower unter der Maus ist
	    		Tower tmp = level.getTower((point.x/mScale), (point.y/mScale)-80,5);
	    		if(tmp == null)
	    		{
	    			//Wenn kein Tower mehr unter maus -> radius ausschalten und tower aus liste löschen
	    			mouseOverTower.SetShowRadiusMode(false);
	    			mouseOverTower = null;
	    		}
	    	}
	    	else 
	    	{	    		
	    		//Suchen ob ein Tower im Maus Radius ist
		    	mouseOverTower = level.getTower((point.x/mScale), (point.y/mScale)-80,5);
		    	if(mouseOverTower != null)
		    	{
		    		//wenn ja, radius anzeigen
		    		mouseOverTower.SetShowRadiusMode(true);
		    	}
	    	}
	    }
	  }
	
}
	
	


