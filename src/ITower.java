/**
 * @author Martin Preusser
 * 
 * 
 */
public interface ITower extends IGameObject {
	
	
	/**
	 * Zeichnet den Tower
	 */
	public void Draw();
	/**
	 * Sucht Radius nach Minions ab und erstellt ggf. neuen Schuss
	 */
	public void Simulate();

}
