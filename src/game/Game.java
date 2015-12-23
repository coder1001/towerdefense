package game;

import game.entities.Enemy;
import game.entities.EnemyType;
import game.entities.Entity;
import game.entities.Player;
import game.entities.Tower;
import game.entities.TowerType;
import gfx.Colours;
import gfx.FontForGame;
import gfx.Screen;
import gfx.SpriteSheet;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.List;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;


import javax.swing.JFrame;

import level.Level;
import level.Overlay;

public class Game extends Canvas implements Runnable{

	
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH /20*15;
	public static final int SCALE = 3;
	public static final String NAME="Game";
	
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colours = new int[6*6*6];
	
	private Screen screen;
	public InputHandler input;
	
	public MouseHandler mouse;
	public MouseMotionHandler mouseMotion;
	
	public GameRound gameround;
	

	public Game(){
		
		
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
	
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this,BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init(){
		int index =0;
		for(int r=0;r<6;r++){
			for(int g=0;g<6;g++){
				for(int b=0;b<6;b++){
					int rr = (r*255/5);
					int gg = (g*255/5);
					int bb = (b*255/5);
					
					colours[index++]=rr << 16 | gg << 8 | bb;
				}
			}
		}
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);
		
		mouse = new MouseHandler(this);
		mouseMotion = new MouseMotionHandler(this);
		
		gameround = new GameRound("/levels/test_level_2.png","/levels/overlay.png", input, SCALE);
		
	
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
		
	}
	
public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/30D;
		
		int frames = 0;
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = true; //wenn false, limitierung auf 60 fps
			
			
			while(delta >= 1){
				ticks++;	
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(shouldRender){
				frames++;
				render();
			}
			
			if(System.currentTimeMillis()-lastTimer > 1000){
				lastTimer += 1000;
				System.out.println(ticks+" ticks, "+frames+" frames, "+getHeight()+" H, "+getWidth()+" W");
				frames = 0;
				ticks = 0;
			}	
		}
		
	}
	
	public void tick(){
		
		if(gameround.mode==3){			
			this.stop();
		}else{
			tickCount++;	
			gameround.tick();
		}
	}
	
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		} 
		if(gameround.mode==3){			
			//this.stop();
			String msg = "Game Over!!!";
			int scale=3;
			FontForGame.render(msg, screen,screen.xOffset+screen.width/2 - (msg.length()*(scale*8/2)),screen.yOffset+screen.height/2, Colours.get(-1,111,333,400), scale);
			//System.out.println("Game Over");
			
		}else{
			gameround.renderTiles(screen);
			gameround.renderEntities(screen);
		}		
		
		for(int y = 0; y <screen.height; y++){
			for(int x = 0; x <screen.width; x++){
				int colourCode = screen.pixels[x+y * screen.width];
				if(colourCode < 255) pixels[x + y * WIDTH] = colours[colourCode];
			}
		}

			
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		new Game().start();
	}



	

}
