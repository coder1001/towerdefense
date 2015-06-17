/**
 * @author Kai Fl�ck
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
	 * Liefert Typ des Schuss zur�ck
	 *
	 * @return slow stune normal
	 */
	public String getType();
}
