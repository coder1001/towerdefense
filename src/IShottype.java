/**
 * @author Kai Flöck
 * 
 * 
 */
public interface IShottype {
	
	/**
	 * Gibt an wie viel Schaden der Schuss erzeugt
	 * @return int Damage
	 */
	public int getDamage();
	/**
	 * Bei einem Treffer kann die Geschwindigkeit des Minions beeinflusst werden
	 * @return Faktor der Bremssgeschwindigkeit
	 */
	public int getSlowrate();
	/**
	 * Liefert Typ des Schuss zurück
	 *
	 * @return slow stune normal
	 */
	public String getType();
}
