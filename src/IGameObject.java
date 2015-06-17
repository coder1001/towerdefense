/**
 * @author Kai Fl�ck
 * 
 * 
 */
public interface IGameObject {

	/**
	 * Liefert die aktuelle Position des Objekts zur�ck
	 * @return int x und y Koordinaten
	 */
	public int[] GetPosition();
	/**
	 * Zeichnet das Gameobject
	 */
	public void Draw();
	/**
	 * Simulation des Gameobject 
	 */
	public void Simulate();
}
