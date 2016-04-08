package game.entities;

import game.GameRound;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.tiles.Tile;

/**
 * Enemy Klasse, fügt die Pathfinding zu dem Mob hinzu, sodass automatisch der Weg über den Sand gefunden wird
 * @author Martin
 *
 */
public class Enemy extends Mob implements IEnemy{

	
	public boolean reachedEnd = false;
	
	
	/**
	 * Konstruktor für die Enemy Klasse
	 * ruft den Konstruktor für Mob auf und übergibt die jeweiligen Parameter aus dem enemyType
	 * @param level
	 * @param x
	 * @param y
	 * @param enemytype
	 */
	public Enemy(Level level,  int x, int y, EnemyType enemytype) {		
		
		super(level, enemytype.getName(), x, y, enemytype.getSpeed(), enemytype.getSprite()[0],enemytype.getSprite()[1], enemytype.getColor(), enemytype.getSound(),enemytype.getHP(), enemytype.getReward(),enemytype.getDamage());
		movingDir = 3;
		
	}
	
	/**
	 * Wird jeden Tick aufgerufen und führt die Berechnugen für die Bewegung durch
	 */
	public void tick() {
		//Am Anfang keine änderung in X/Y Richtung
		int xa = 0;
		int ya = 0;

		//Je nach Bewegungsrichtung den X und Y Änderungswert anpassen
		if(movingDir == MOVE_TOP)
			ya--;
		else if(movingDir == MOVE_BOT)
			ya++;
		else if(movingDir == MOVE_LEFT)
			xa--;
		else{
			xa++;
		}		
		
		//Monster für eine bestimmte Zeit einfrieren und bewegungsunfähig machen
		fTimeNow=System.currentTimeMillis();		
		if(fTimeNow-fTimeStart>fTime)
			freeze=false;
		
		//Wenn in X oder Y Richtung bewegt werden soll und das Monster nicht eingefrorer ist -> Move
		if( (xa != 0 || ya != 0) && freeze==false){
			move(xa,ya);
			isMoving = true;
		}else{
			isMoving = false;
		}
	}
	

	public boolean hasCollided(int xa, int ya, int Tile, boolean neg) {
		
		
		int xMin = 0; // 8 width
		int xMax = 7; //
		
		int yMin = 4; // 4 height
		int yMax = 7; //
		
		//Erst die obere Reihe überprüfen
		for(int x = xMin; x < xMax; x++){   	//     x ------- x
 			if(isTile(xa,ya,x,yMin,Tile,neg)){  //     |		 |
				return true;					//
			}									//
		}										//
		//Untere Reihe überprüfen
		for(int x = xMin; x < xMax; x++){		//
			if(isTile(xa,ya,x,yMax,Tile,neg)){	//     |		 |
				return true;					//	   x ------- x
			}									//
		}										//
		//Linke Reihe überprüfen
		for(int y = yMin; y < yMax; y++){		//     x -----
			if(isTile(xa,ya,xMin,y,Tile,neg)){	//     |
				return true;					//     x -----
			}									//
		}										//
		//Rechte Reihe überprüfen
		for(int y = yMin; y < yMax; y++){  		//         ----- x
			if(isTile(xa,ya,xMax,y,Tile,neg)){	//               |
				return true;					//         ----- x
			}
		}
		
		return false;
	}
	
	/**
	 * Überprüft ob das Monster nicht auf einem Weg ist
	 * Ruft dazu hasCollided mit vordefinierten Parametern auf
	 * @param xa
	 * @param ya
	 * @return
	 */
	public boolean NotOnRoad(int xa, int ya) {		
		int tileIdSand = 3;
		return hasCollided(xa,ya,tileIdSand,true);
	}
	
	/**
	 * Überprüft ob das Monster das Ende berührt
	 * Ruft dazu hasCollided mit vordefinierten Parametern auf
	 * @param xa
	 * @param ya
	 * @return
	 */
	public boolean hitTheEnd(int xa, int ya) {		
		int tileIdEnd = 4;
		return hasCollided(xa,ya,tileIdEnd,false);
	}
	
	/**
	 * Funktion die den Mob auf der Karte bewegt
	 * 
	 * @param xa X Koordiante
	 * @param ya Y Koordinate
	 */
	public void move(int xa, int ya){
		
		fTimeNow=System.currentTimeMillis();
		
			
		numSteps++;
		
		
		//Wenn noch auf dem Weg, Richtung beibhalten
		if(!NotOnRoad(xa,ya)){
			if(ya < 0) movingDir = MOVE_TOP; // oben
			if(ya > 0) movingDir = MOVE_BOT; // unten
			if(xa < 0) movingDir = MOVE_LEFT; // links	
			if(xa > 0) movingDir = MOVE_RIGHT; // rechts
			
			x += xa * speed;
			y += ya * speed;
		}
		
		//Algorithmus um den Weg entlang zu laufen
		//Ende es wegs erreicht ?
		if(NotOnRoad(xa,ya)){
			
			
			if(movingDir==MOVE_TOP) //Wenn er gerade nach oben läuft 
			{
				if(!NotOnRoad(xa-16,ya+2)) //der nächste Weg ist links
					movingDir=MOVE_LEFT;
				else
					movingDir=MOVE_RIGHT; //Wenn nicht, ist der Weg rechts
			}
			else if(movingDir==MOVE_BOT) // ..
			{
				if(!NotOnRoad(xa-16,ya-2)) // .. analog zu oben
					movingDir=MOVE_LEFT;
				else
					movingDir=MOVE_RIGHT;
			}
			else if(movingDir==MOVE_LEFT)//links
			{
				if(!NotOnRoad(xa+2,ya-16))
					movingDir=MOVE_TOP;
				else
					movingDir=MOVE_BOT;
			}	
			else //rechts
			{
				if(!NotOnRoad(xa-2,ya-16))
					movingDir=MOVE_TOP;
				else
					movingDir=MOVE_BOT;
			}
		} 
		
		//Eine Ausnahme ist das letzte Feld
		if(hitTheEnd(xa, ya)==true)
			reachedEnd = true;
		
	}
	
	/**
	 * 
	 * @param xa Änderung X Richtung (um zu Berechnen auf welchem Feld man als nächstes steht)
	 * @param ya Änderung Y Richtung 
	 * @param x Aktuelle X Position  (Um zu überprüfen auf welchem Feld man aktuell steht)
	 * @param y Aktuelle Y Position
	 * @param tileId Das Feld auf welches überprüft werden soll
	 * @param negiert Es soll genau das angegebene Feld nicht sein 
	 * @return
	 */
	protected boolean isTile(int xa, int ya, int x, int y,int tileId,boolean negiert){
		
		if(level == null) { return false;}
		//Feld auf dem das Mob gerade steht
		Tile lastTile = level.getTile((this.x +x)>>3, (this.y +y)>>3);
		//Feld auf dem das Mob nach der Bewegung steht
		Tile newTile = level.getTile((this.x + x + xa)>>3, (this.y + y + ya)>> 3);
		//Wenn das neue Feld ungleich dem alten ist 
		if(!lastTile.equals(newTile)){
			if(negiert && newTile.getId() != tileId)
				return true;
			if((!negiert && newTile.getId() == tileId))
				return true;
		}
		return false;
	}
	

}

