package game;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import game.entities.Enemy;
import game.entities.EnemyType;
import game.entities.Player;
import game.entities.Tower;
import game.entities.TowerType;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.Overlay;

/*
 * In der Klasse GameRound werden alle Informationen �ber die aktuelle Spielgrunde festgehalten.
 * -> Der Aktuelle Goldstand
 * -> Das aktuelle Leben
 * -> Die aktuelle Welle
 * 
 * Jede Welle besteht aus 10 Enemys des gleichen Typs. Die Enemys jeder Welle werden immer st�rker
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
	private Player player;
	private int mScale;
	
	private int lastEnemyHP;
	private int lastEnemyReward;
	
	
	public ArrayList<TowerType> TowerTypes;// = new ArrayList<TowerTypes>();
	public ArrayList<EnemyType> EnemyTypes;// = new ArrayList<TowerTypes>();
	public ArrayList<Enemy> EnemyList;
	
	public GameRound(String levelPath, String overlayPath, InputHandler handler, int scale){
		mScale = scale;
		gold=400;
		health=20;
		wave=1;
		minionsLeft=0;
		minionCounter=0;
		bnew=false;
		msNextMinion=1000;
		level = new Level(levelPath, this);
		overlay = new Overlay(overlayPath);
		player = new Player(level, 8,8,handler);
		level.addEntity(player);
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
		TowerTypes.add( new TowerType("Laser 1",50,33,200,0,22,"laser1.wav",Colours.get(-1,111,500, 543),NONE, 1000,10,1));
		TowerTypes.add( new TowerType("Multi 1",30, 6,300,4,22,"laser1.wav",Colours.get(-1,111,500, 543),NONE, 1,100,3));
		TowerTypes.add( new TowerType("Ice 1"  ,30, 30,500,6,22,"laser1.wav",Colours.get(-1,111,005, 543),FREEZE, 2000,5,1));
		
		
		/*
		 *  Overlay Tower Anzeige : Untere Reihe
		 */								
		//								Name Range DMG Price
		TowerTypes.add( new TowerType("Laser 2",60,50,1000,0,22,"laser1.wav",Colours.get(-1,111,253, 543),NONE, 300,10,1));
		TowerTypes.add( new TowerType("Multi 2",30 ,15,1400,2,22,"laser1.wav",Colours.get(-1,111,225, 533),NONE, 1,100,4));
		TowerTypes.add( new TowerType("Ice 2"  ,30,40,2000,6,22,"laser1.wav",Colours.get(-1,111,035, 543),FREEZE, 2000,5,2));
		
		overlay.AddTowerTypes(TowerTypes);
		updateOverlay();
		
		createNewWave();
		mode=WAVE_ON;
	}
	
	
	
	
	
	
	
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
	
	/*
	 * 	Aktualisiert das Overlay
	 * 		-> Gold
	 * 		-> Leben
	 * 		-> Aktuelle Welle
	 */
	public void updateOverlay(){
		
		overlay.setGold(gold);
		overlay.setLifes(health);
		overlay.setWave(wave);
	}
	
	
	public int getWidth(){
		return level.width;
	}
	
	/*
	 *  Zeichnet oben das Overlay und unten das Level
	 */
	public void renderTiles(Screen screen){
		int xOffset = player.x-(screen.width/2);
		int yOffset = player.y-(screen.height/2);
		overlay.render(screen, 0, 0);
		level.renderTiles(screen,  xOffset, yOffset);
		
	}
	
	/*
	 *  Zeichnet alle Enemy und Tower
	 */
	public void renderEntities(Screen screen){
		level.renderEntities(screen);
	}
	
	
	public boolean enemyLeft(){
		if(minionsLeft == 0)
			return false;
		else
			return true;
	}
	
	/*
	 * Wenn ein Enemy stirbt oder das Ziel erreicht, wird minonsLeft um eins verringert
	 */
	public void reduceEnemy(){
		if(minionsLeft>0)
			minionsLeft--;
		
	}
	
	/*
	 * Wenn ein Enemy stirbt oder das Ziel erreicht, wird das Leben um den DAMAGE des Monsters verringert.
	 * Wenn das Leben auf <= 0 f�llt, wird mode auf GAME_OVER gesetzt
	 */
	public void reduceLife(int damage){
		
		health-=damage;
		if(health<=0)
			mode=GAME_OVER;
	}
	
	public void setGold(int reward){
		
		this.gold+=reward;
	
}
	
	
	/*
	 * Erstellt eine neue Welle von Enemy
	 * 1. Setze minionsLeft zur�ck auf die Wave Size
	 * 2. L�sche alle Enemy aus der EnemyList
	 * 3. F�ge einen neuen Gegnertyp x-Mal in die Liste
	 */
	private void createNewWave(){
		
		minionsLeft=WAVE_SIZE; 
		EnemyType newEnemyType = EnemyTypes.get((wave-1)%EnemyTypes.size());
		newEnemyType.setColor(Colours.get(-1,rn.nextInt(556),rn.nextInt(556), rn.nextInt(556)));
		EnemyList.clear();
		
		for(int i=0;i<WAVE_SIZE;i++){
			EnemyList.add(new Enemy(level,-20,20,newEnemyType));
			EnemyList.get(i).setMaxLive((int)(lastEnemyHP*1.15));
			EnemyList.get(i).setReward((int)(lastEnemyReward+1));
		}
		lastEnemyHP = EnemyList.get(0).maxlive;
		lastEnemyReward = EnemyList.get(0).getReward();
	}
	
	
	
	Tower towerPlace = null;
	  
	  /*
	   * Wird vom MouseHandler aufgerufen 
	   * Mausklick wird ben�tigt um Tower auszuw�hlen und anschlie�end zu platzieren
	   */
	public void OnMouseClick(MouseEvent e)
    {
    System.out.println(e.getButton());
      //level.TileClicked(x, y);
      //Schauen ob oben einer der Towertypen angeklickt wurde
    int x = e.getX();
    int y = e.getY();
      TowerType type = overlay.TowerTypeClicked(x,y);
      
      //Mit rechtsklick bauen abbrechen und tower l�schen
      if(e.getButton() == 3)
      {
        if(towerPlace != null)
        {
          level.removeEntity(towerPlace);
          towerPlace = null;
          level.SetRenderRaster(false);
        }        
      }
      
      //Wenn einer angeklickt wurde und zurzeit kein tower platziert wird
      if(type != null && towerPlace == null)
      {
    	
        towerPlace = new Tower(level, x/mScale-5, y/mScale-75, type);
        towerPlace.SetPlaceMode(true);
        level.addEntity(towerPlace);
		
        level.SetRenderRaster(true);
      }//wenn schon ein tower platziert wird, entg�ltig setzen
      else if(towerPlace != null)
      {
        Point tilepos = level.TileClicked(x, y);
        if(tilepos != null)
        {
          towerPlace.SetPosition(tilepos.x+5, tilepos.y+5);
          towerPlace.SetPlaceMode(false);
          //�berpr�fen ob wiese
          level.SetRenderRaster(false);
          this.gold -= towerPlace.GetPrice();
          this.updateOverlay();
          towerPlace = null;    
        }
      }
    }
	  
	  public void onMouseMove(Point point)
	  {    
	    //Wenn gerade ein Tower platziert wird, die Position je nach Mausposition anpassen
	    if(towerPlace != null)
	    {
	      towerPlace.SetPosition((point.x/mScale),(point.y/mScale)-75);
	      level.SetMousePosition(point.x,point.y);
	    }
	  }
	
}
	
	


