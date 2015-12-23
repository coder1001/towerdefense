package game.entities;

import java.awt.Point;

import gfx.Screen;

public class TowerType {

	private int posx;
	private int posy;
	private String mName;
	private int mRange;
	private int mDamage;
	private int[] mSprite;
	private String mSound;
	private int mColor;
	private int mPrice;
	
	public TowerType(String Name, int Range, int Damage,int Price, int SpriteX,int SpriteY, String Sound, int Color)
	{
		mName = Name;
		mRange = Range;
		mDamage = Damage;
		mSprite = new int[2];
		mSprite[0] = SpriteX;
		mSprite[1] = SpriteY;
		mSound = Sound;
		mColor = Color;
		mPrice = Price;
	}

	public void setPosition(int x, int y)
	{
		posx = x;
		posy = y;
	}
	
	public Point getPosition()
	{
		return new Point(posx,posy);
	}
	
	public String getName() {
		return mName;
	}

	public int getRange() {
		return mRange;
	}

	public int getDamage() {
		return mDamage;
	}
	
	public int getPrice() {
		return mPrice;
	}

	public int[] getSprite() {
		return mSprite;
	}

	public String getSound() {
		return mSound;
	}
	public int getColor() {
		return mColor;
	}
	
	public void render(Screen screen, boolean Active) {
		
		int x = posx;
		int y  = posy;
		
		int xTile = mSprite[0];
		int yTile = mSprite[1];		
	    int scale = 1;
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 -4;
		
		//colour = mColor;
		int colour = mColor;
		if(!Active)
		   colour = gfx.Colours.get(-1, 333, 444, 222);
		
	
		screen.render(xOffset , yOffset , xTile + yTile * 32, colour);
		screen.render(xOffset + modifier, yOffset , (xTile + 1) + yTile * 32, colour);
		screen.render(xOffset , yOffset + modifier , xTile + (yTile+1) * 32, colour);
		screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour);
		
		gfx.FontForGame.render(mName, screen, x-(mName.length()*(8/2)), y+10, gfx.Colours.get(-1, 000, 000, 000), 1);
		
	}
	
	
}
