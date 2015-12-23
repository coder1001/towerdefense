package game.entities;



public class EnemyType {

	private String mName;
	private int mHP;
	private int mSpeed;
	private int[] mSprite;
	private String mSound;
	private int mColor;
	private int mReward;
	private int mDamage;
	
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
