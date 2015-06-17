/**
 * @author Marko Susic
 * 
 * 
 */

import java.util.ArrayList;


public interface IGameRound {
	
	
	/**
	 * L�uft in einer Endlosschleife und ruft die einzelnen Simulate und Draw Funktionen auf 
	 */
	public void GameLoop();
	/**
	 * liefert alle Minions zur�ck, die sich auf dem Feld befinden
	 * @return Arraylist von Minions 
	 */
	public ArrayList<IMinion> GetMinions();
	/**
	 * F�gt dem Gameobject Array ein neues Object hinzu
	 * @param gameobject
	 */
	public void AddObject(IGameObject gameobject);

}
