package game.entities;

import game.InputHandler;
import gfx.Colours;
import gfx.Screen;
import level.Level;

public class Player extends Mob{

	private InputHandler input;
	int normalSpeed;
	
	public Player(Level level,  int x, int y, InputHandler input) {
		super(level, "Player", x, y, 1,0,28, Colours.get(-1,111,500, 543),"laser1.wav",1000, 0,0);
		this.input = input;
		this.normalSpeed = speed;
	}

	public void tick() {
		int xa = 0;
		int ya = 0;
		
		if(input.up.isPressed()) ya--;	
		if(input.down.isPressed()) ya++;	
		if(input.left.isPressed()) xa--;	
		if(input.right.isPressed()) xa++;
	
		if(input.run.isPressed())
			speed=normalSpeed*2;
		else
			speed = normalSpeed;
		
		if(xa != 0 || ya != 0){
			move(xa,ya);
			isMoving = true;
		}else{
			isMoving = false;
		}
		this.scale = 1;
	}
	
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

}
