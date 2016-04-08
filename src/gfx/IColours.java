package gfx;

/**
 * Die Klasse packt alle 4 Farben im Spiel in eine int-Variable
 * 
 * @author Marko Susic
 * @version 1.0
 */
public interface IColours {
	
	/**
	 * Packt alle 4 Farben in eine Zahl
	 * 
	 * @param Hintergrundfarbe
	 * @param Farbe1
	 * @param Farbe2
	 * @param Farbe3
	 * @return Alle 4 Farben in einer Zahl, jeweils um 8 Bit nach links verschoben
	 */
	public static int get(int colour1, int colour2, int colour3, int colour4){
		return ( get(colour4)<<24)+ (get(colour3)<<16)+(get(colour2)<<8)+ get(colour1);
	}

	/**
	 * Wandelt die Farbe um
	 * 
	 * 000 -> 	0
	 * 555 -> 215 (maximale Anzahl verschiedener Farben)
	 * @param Farbe
	 * @return Farbe, 1 Byte groﬂ
	 * 
	 */
	public static int get(int colour) {
		if (colour<0) return 255;
		int r = colour/100%10;
		int g = colour/10%10;
		int b = colour%10;
		return r*36 + g*6 +b;
	}
	

}
