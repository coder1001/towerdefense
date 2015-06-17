/**
 * @author Martin Preusser
 * 
 * 
 */
public interface ITowertype {
	/**
	 * liefert den Suchradius des Towers zur�ck
	 * @return Reichwerte
	 */
	public int getRange();

	/**
	 * Liefert die Feuergeschwindigkeit des Towers zur�ck
	 * @return
	 */
	public int getFirerate();
	/**
	 * Liefert die Kosten des Towers zur�ck
	 * @return
	 */
	public int getGoldcost();
	
	/**
	 * Liefert den Shottype zur�ck
	 * @return
	 */
	public IShottype getShottype();
	

	

}
