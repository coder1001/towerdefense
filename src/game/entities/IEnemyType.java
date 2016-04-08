package game.entities;


/**
 * 
 *  Die verschiedenen Monster-Typen werden mit je einer Instanz dieser Klasse definiert
 *  Wird ein neues Monster vom Typ Enemy erstellt, werden die Eigenschaften mit Hilfe von EnemyType parametrisiert.
 * 
 * @author  Martin Preuﬂer
 * @version 1.0
 */ 

public interface IEnemyType {

		//Get/Set Methoden um die privaten Attribute auslesen/setzen
	
	public String getName();

	public int getHP();

	public int getSpeed();

	public int[] getSprite();

	public String getSound();
	
	public int getColor();
	
	public int getReward();
	
	public int getDamage();
	
	public void setColor(int color);
	
	
	
}
