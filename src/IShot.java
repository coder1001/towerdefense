/**
 * @author Kai Fl�ck
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
	 * Gibt den Schusstyp zur�ck 
	 * @return IShottype
	 */
	public IShottype getShottype();

}
