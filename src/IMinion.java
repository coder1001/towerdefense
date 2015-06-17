/**
 * @author Kai Flöck
 * 
 * 
 */
public interface IMinion extends IGameObject {
	
	/**
	 * Zeichnet den Minion
	 */
	public void Draw();
	/**
	 * Simuliert die Laufbahn der Minions 
	 */
	public void Simulate();
	/**
	 * Funktion wird aufgerufen, sobald ein Minion von einem Schuss getroffen wurde und fügt dem Minion Schade zu
	 */
	public void DoDamage();

}
