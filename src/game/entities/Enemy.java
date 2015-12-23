package game.entities;

import game.GameRound;
import gfx.Colours;
import gfx.Screen;
import level.Level;
import level.tiles.Tile;

public class Enemy extends Mob{

	
	public boolean reachedEnd = false;
	public Enemy(Level level,  int x, int y, EnemyType enemytype) {
		
		
		super(level, enemytype.getName(), x, y, enemytype.getSpeed(), enemytype.getSprite()[0],enemytype.getSprite()[1], enemytype.getColor(), enemytype.getSound(),enemytype.getHP(), enemytype.getReward(),enemytype.getDamage());
		movingDir = 3;
		
	}

	
	public void tick() {
		int xa = 0;
		int ya = 0;


		//if(this.x == )
		if(movingDir == 0)
			ya--;
		else if(movingDir == 1)
			ya++;
		else if(movingDir == 2)
			xa--;
		else{
			xa++;
		}
		
	
		//xa--;
		
		/*if(input.up.isPressed()) ya--;	
		if(input.down.isPressed()) ya++;	
		if(input.left.isPressed()) xa--;	
		if(input.right.isPressed()) xa++; */
		
		if(xa != 0 || ya != 0){
			move(xa,ya);
			isMoving = true;
		}else{
			isMoving = false;
		}
		
	}


	
	/* Überprüft ob eine Kollision stattfindet */
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0; // 8 width
		int xMax = 7; //
		
		int yMin = 4; // 4 height
		int yMax = 7; //
		
		for(int x = xMin; x < xMax; x++){   //     x ------- x
 			if(isSolidTile(xa,ya,x,yMin)){  //     |		 |
				return true;				//
			}								//
		}									//
		for(int x = xMin; x < xMax; x++){	//
			if(isSolidTile(xa,ya,x,yMax)){	//     |		 |
				return true;				//	   x ------- x
			}								//
		}									//
		for(int y = yMin; y < yMax; y++){	//     x -----
			if(isSolidTile(xa,ya,xMin,y)){	//     |
				return true;				//     x -----
			}								//
		}									//
		for(int y = yMin; y < yMax; y++){  	//         ----- x
			if(isSolidTile(xa,ya,xMax,y)){	//               |
				return true;				//         ----- x
			}
		}
		
		return false;
	}
	
	public boolean NotOnRoad(int xa, int ya) {
		int xMin = 0; // 8 width
		int xMax = 7; //
		
		int yMin = 4; // 4 height
		int yMax = 7; //
		
		for(int x = xMin; x < xMax; x++){   //     x ------- x
 			if(isNoSandTile(xa,ya,x,yMin)){  //     |		 |
				return true;				//
			}								//
		}									//
		for(int x = xMin; x < xMax; x++){	//
			if(isNoSandTile(xa,ya,x,yMax)){	//     |		 |
				return true;				//	   x ------- x
			}								//
		}									//
		for(int y = yMin; y < yMax; y++){	//     x -----
			if(isNoSandTile(xa,ya,xMin,y)){	//     |
				return true;				//     x -----
			}								//
		}									//
		for(int y = yMin; y < yMax; y++){  	//         ----- x
			if(isNoSandTile(xa,ya,xMax,y)){	//               |
				return true;				//         ----- x
			}
		}
		
		return false;
	}
	
	public boolean hitTheEnd(int xa, int ya) {
		int xMin = 0; // 8 width
		int xMax = 7; //
		
		int yMin = 4; // 4 height
		int yMax = 7; //
		
		for(int x = xMin; x < xMax; x++){   //     x ------- x
 			if(isEndTile(xa,ya,x,yMin)){  //     |		 |
				return true;				//
			}								//
		}									//
		for(int x = xMin; x < xMax; x++){	//
			if(isEndTile(xa,ya,x,yMax)){	//     |		 |
				return true;				//	   x ------- x
			}								//
		}									//
		for(int y = yMin; y < yMax; y++){	//     x -----
			if(isEndTile(xa,ya,xMin,y)){	//     |
				return true;				//     x -----
			}								//
		}									//
		for(int y = yMin; y < yMax; y++){  	//         ----- x
			if(isEndTile(xa,ya,xMax,y)){	//               |
				return true;				//         ----- x
			}
		}
		
		return false;
	}
	
	
	public void move(int xa, int ya){
		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		
		
		
		if(!NotOnRoad(xa,ya)){
			if(ya < 0) movingDir = 0; // oben
			if(ya > 0) movingDir = 1; // unten
			if(xa < 0) movingDir = 2; // links	
			if(xa > 0) movingDir = 3; // rechts
			
			x += xa * speed;
			y += ya * speed;
		}
		
		if(NotOnRoad(xa,ya)==true){
			
			//wenn du nach oben
			if(movingDir==0)
			{
				if(!NotOnRoad(xa-16,ya+2))
					movingDir=2;
				else
					movingDir=3;
			}
			else if(movingDir==1)//unten
			{
				if(!NotOnRoad(xa-16,ya-2))
					movingDir=2;
				else
					movingDir=3;
			}
			else if(movingDir==2)//links
			{
				if(!NotOnRoad(xa+2,ya-16))
					movingDir=0;
				else
					movingDir=1;
			}	
			else //rechts
			{
				if(!NotOnRoad(xa-2,ya-16))
					movingDir=0;
				else
					movingDir=1;
			}
		} 
		
		if(hitTheEnd(xa, ya)==true)
			reachedEnd = true;
	}
	
	
	protected boolean isNoSandTile(int xa, int ya, int x, int y){
		if(level == null) { return false;}
		Tile lastTile = level.getTile((this.x +x)>>3, (this.y +y)>>3);
		Tile newTile = level.getTile((this.x + x + xa)>>3, (this.y + y + ya)>> 3);
		if(!lastTile.equals(newTile) && newTile.getId()!=3 ){
			return true;
		}
		
		return false;
	}
	
	
	protected boolean isEndTile(int xa, int ya, int x, int y){
		if(level == null) { return false;}
		Tile lastTile = level.getTile((this.x +x)>>3, (this.y +y)>>3);
		Tile newTile = level.getTile((this.x + x + xa)>>3, (this.y + y + ya)>> 3);
		if(!lastTile.equals(newTile) && newTile.getId()==4 ){
			return true;
		}
		
		return false;
	}
	

}

