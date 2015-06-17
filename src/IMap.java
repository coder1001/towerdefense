/**
 * @author Martin Preusser
 * 
 * 
 */
public interface IMap {
	/**
	 * Erstellt eine Map, die aus einem Felderarray besteht
	 */
	public void GenerateMap();
	/**
	 * Zeichnet die Felder der erstellten Map
	 */
	public void Draw();
	/**
	 * Bei dem Plazieren des Towers wird die Karten in ein Raster eingeteilt
	 */
	public void SetPlaceMode();
	/**
	 * Ruft das Simulate der einzelnen Felder auf.
	 */
	public void Simulate();

}
