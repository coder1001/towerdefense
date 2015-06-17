/**
 * @author Martin Preusser
 * 
 * 
 */
public interface ITowertype {
	/**
	 * liefert den Suchradius des Towers zurück
	 * @return Reichwerte
	 */
	public int getRange();

	/**
	 * Liefert die Feuergeschwindigkeit des Towers zurück
	 * @return
	 */
	public int getFirerate();
	/**
	 * Liefert die Kosten des Towers zurück
	 * @return
	 */
	public int getGoldcost();
	
	/**
	 * Liefert den Shottype zurück
	 * @return
	 */
	public IShottype getShottype();
	

	

}
