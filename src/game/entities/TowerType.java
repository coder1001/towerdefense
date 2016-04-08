package game.entities;

import java.awt.Point;

import gfx.Screen;
/**
 * Klasse zum definieren der verchiedenen Towertypes und deren Eigenschaften
 * Wird ebenfalls verwendet um die Icons im oberen Teil des Overlays zu zeichnen
 * @author Martin
 *
 */
public class TowerType implements ITowerType{

	private int posx;
	private int posy;
	private String mName;
	private int mRange;
	private int mDamage;
	private int[] mSprite;
	private String mSound;
	private int mColor;
	private int mPrice;
	private int mEffect;
	private int mReloadTime;
	private int mLockedEnemys;
	private int mShotTime;
	private int mLaserColor;
	
	/**
	 * KOnstruktor mit dem die Eigenschaften des Towers zugewiesen werden
	 * @param Name
	 * @param Range
	 * @param Damage
	 * @param Price
	 * @param SpriteX
	 * @param SpriteY
	 * @param Sound
	 * @param Color
	 * @param effect
	 * @param reloadTime
	 * @param shotTime
	 * @param lockedEnemys
	 * @param laserColor
	 */
	public TowerType(String Name, int Range, int Damage,int Price, int SpriteX,int SpriteY, String Sound, int Color, int effect, int reloadTime, int shotTime, int lockedEnemys, int laserColor)
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
		mEffect = effect;
		mReloadTime = reloadTime;
		mLockedEnemys = lockedEnemys;
		mShotTime = shotTime;
		mLaserColor = laserColor;
	}

	/**
	 * Position setzen (Bspw oben im Overlay Teil)
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y)
	{
		posx = x;
		posy = y;
	}
	
	/**
	 * Position abfragen
	 * @return
	 */
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
	
	public int getEffect() {
		return mEffect;
	}
	
	public int getReloadTime() {
		return mReloadTime;
	}
	
	public int getLockedEnemys() {
		return mLockedEnemys;
	}
	
	public int getShotTime() {
		return mShotTime;
	}
	
	public int getLaserColor() {
		return mLaserColor;
	}
	
	/**
	 * Funktion zum zeichnen des Towertype Icons im Overlay)
	 * @param screen Screen auf das gezeichnet wird, overlay
	 * @param Active Gibt an ob man genug Geld zum bauen hat (zb genug Gold)
	 */
	public void render(Screen screen, boolean Active) {
		
		int x = posx;
		int y  = posy;
		
		int xTile = mSprite[0];
		int yTile = mSprite[1];		
	    int scale = 1;
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 -4;
		
		int colour = mColor;
		if(!Active)
		   colour = gfx.Colours.get(-1, 333, 444, 222);
		
		//DIe 4 Tiles zeichnen, aus denen das Towerbild besteht
		screen.render(xOffset , yOffset , xTile + yTile * 32, colour);
		screen.render(xOffset + modifier, yOffset , (xTile + 1) + yTile * 32, colour);
		screen.render(xOffset , yOffset + modifier , xTile + (yTile+1) * 32, colour);
		screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour);
		
		//Name des Towers drunter zeichnen
		gfx.FontForGame.render(mName, screen, x-(mName.length()*(8/2)), y+10, gfx.Colours.get(-1, 000, 000, 000), 1);
		
	}
	
	
}
