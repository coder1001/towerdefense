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
 * In der Klasse GameRound werden alle Informationen über die aktuelle Spielgrunde festgehalten.
 * -> Der Aktuelle Goldstand
 * -> Das aktuelle Leben
 * -> Die aktuelle Welle
 * 
 * Jede Welle besteht aus 10 Enemys des gleichen Typs. Die Enemys jeder Welle werden immer stärker
 */
public class GameRound {

	
	// Constants
	public static final int WAVE_SIZE = 10;
	public static final int TIME_NEXT_WAVE = 5;
	
	// Game Modes
	public static final int WAIT = 1;
	public static final int WAVE_ON = 2;
	public static final int GAME_OVER = 3;
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
	
	
	public ArrayList<TowerType> TowerTypes;// = new ArrayList<TowerTypes>();
	public ArrayList<EnemyType> EnemyTypes;// = new ArrayList<TowerTypes>();
	public ArrayList<Enemy> EnemyList;
	
	public GameRound(String levelPath, String overlayPath, InputHandler handler){
		gold=500;
		health=1;
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
		
		
		TowerTypes = new ArrayList<TowerType>();
		
		/*
		 *  Overlay Tower-Anzeige : Untere Reihe
		 */
		TowerTypes.add( new TowerType("Laser 1",50,15,200,0,22,"laser1.wav",Colours.get(-1,111,500, 543)) );
		TowerTypes.add( new TowerType("Multi 1",30,30,300,4,22,"laser1.wav",Colours.get(-1,111,500, 543)) );
		TowerTypes.add( new TowerType("Ice 1",30,40,400,6,22,"laser1.wav",Colours.get(-1,111,005, 543)) );
		
		
		/*
		 *  Overlay Tower Anzeige : Untere Reihe
		 */
		TowerTypes.add( new TowerType("Laser 2",60,100,1500,0,22,"laser1.wav",Colours.get(-1,111,253, 543)) );
		TowerTypes.add( new TowerType("Multi 2",50,150,2000,2,22,"laser1.wav",Colours.get(-1,111,225, 533)) );
		TowerTypes.add( new TowerType("Ice 2",40,200,3000,6,22,"laser1.wav",Colours.get(-1,111,035, 543)) );
		
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
	 * Wenn das Leben auf <= 0 fällt, wird mode auf GAME_OVER gesetzt
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
	 * 1. Setze minionsLeft zurück auf die Wave Size
	 * 2. Lösche alle Enemy aus der EnemyList
	 * 3. Füge einen neuen Gegnertyp x-Mal in die Liste
	 */
	private void createNewWave(){
		
		minionsLeft=WAVE_SIZE;
		EnemyType newEnemyType = EnemyTypes.get((wave-1)%EnemyTypes.size());
		
		newEnemyType.setColor(Colours.get(-1,rn.nextInt(556),rn.nextInt(556), rn.nextInt(556)));
		EnemyList.clear();
		for(int i=0;i<WAVE_SIZE;i++){
			EnemyList.add(new Enemy(level,-20,20,newEnemyType));
			EnemyList.get(i).setMaxLive((int)(EnemyList.get(i).maxlive+(EnemyList.get(i).maxlive*wave*0.1)));
			EnemyList.get(i).setReward((int)(EnemyList.get(i).getReward()+(EnemyList.get(i).getReward()*wave*0.05)));
		}
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
      //Schauen ob oben einer der Towertypen angeklickt wurde
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
      
      //Wenn einer angeklickt wurde und zurzeit kein tower platziert wird
      if(type != null && towerPlace == null)
      {
        towerPlace = new Tower(level, x/3-5, y/3-75, type);
        towerPlace.SetPlaceMode(true);
        level.addEntity(towerPlace);
        level.SetRenderRaster(true);
      }//wenn schon ein tower platziert wird, entgültig setzen
      else if(towerPlace != null)
      {
        Point tilepos = level.TileClicked(x, y);
        if(tilepos != null)
        {
          towerPlace.SetPosition(tilepos.x+5, tilepos.y+5);
          towerPlace.SetPlaceMode(false);
          //überprüfen ob wiese
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
	      towerPlace.SetPosition((point.x/3),(point.y/3)-75);
	      level.SetMousePosition(point.x,point.y);
	    }
	  }
	
}
	
	


