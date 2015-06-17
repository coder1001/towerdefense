/**
 * @author Kai Flöck
 * 
 * 
 */
public interface IShot extends IGameObject {
	/**
	 * Zeichnet den Schuss
	 */
	public void Draw();
	/**
	 * Simuliert den Schuss
	 */
	public void Simulate();
	/**
	 * Gibt den Schusstyp zurück 
	 * @return IShottype
	 */
	public IShottype getShottype();

}
