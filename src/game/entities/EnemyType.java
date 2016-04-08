package game.entities;


/**
 * 
 *  Die verschiedenen Monster-Typen werden mit je einer Instanz dieser Klasse definiert
 *  Wird ein neues Monster vom Typ Enemy erstellt, werden die Eigenschaften mit Hilfe von EnemyType parametrisiert.
 * 
 * @author  Martin Preuﬂer
 * @version 1.0
 */ 

public class EnemyType implements IEnemyType{

	private String mName;
	private int mHP;
	private int mSpeed;
	private int[] mSprite;
	private String mSound;
	private int mColor;
	private int mReward;
	private int mDamage;
	
	/**
	 * Konstruktor mit dem alle notwendigen Parameter direkt zugewiesen werden
	 * 
	 * @param Name
	 * @param HP
	 * @param Speed
	 * @param SpriteX
	 * @param SpriteY
	 * @param Sound
	 * @param color
	 * @param reward
	 * @param damage
	 */
	public EnemyType(String Name, int HP, int Speed, int SpriteX,int SpriteY, String Sound, int color, int reward, int damage)
	{
		mName = Name;
		mHP = HP;
		mSpeed = Speed;
		mSprite = new int[2];
		mSprite[0] = SpriteX;
		mSprite[1] = SpriteY;
		mSound = Sound;
		mColor = color;
		mReward = reward;
		mDamage = damage;
		
	}

	//Get/Set Methoden um die privaten Attribute auslesen/setzen
	
	public String getName() {
		return mName;
	}

	public int getHP() {
		return mHP;
	}

	public int getSpeed() {
		return mSpeed;
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
	
	public int getReward() {
		return mReward;
	}
	
	public int getDamage() {
		return mDamage;
	}
	
	public void setColor(int color) {
		this.mColor = color;
	}
	
	
	
}
