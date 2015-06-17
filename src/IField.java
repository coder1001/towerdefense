/**
 * @author Martin Preusser
 * 
 * 
 */
public interface IField {
	
	/**
	 * Zeichnet das einzelne Feld 
	 */
	public void Draw();
	/**
	 * Ruft die Simulate Funktion von Tower auf
	 */
	public void Simulate();
	/**
	 * Wird aufgerufen, wenn auf das Feld geklickt wird
	 */
	public void onClick();


}
