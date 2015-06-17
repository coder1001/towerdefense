/**
 * @author Marko Susic
 * 
 * 
 */
public interface IOverlay {
	/**
	 *Zeichnet das Overlay 
	 */
	public void Draw();
	
	/**
	 * Wird aufgerufen, sobald ein Tower/Button gedrückt wird
	 */
	public void onClick();

}
