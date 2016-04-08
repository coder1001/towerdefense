package gfx;

/**
 * Die Klasse simuliert den Bildschirm
 * 
 * @author Marko Susic
 * @version 1.0
 */
public interface IScreen {
	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH-1;
	
	public static final byte BIT_MIRROR_X = 1;
	public static final byte BIT_MIRROR_Y = 2;
	
	/**
	 * Zeichnet eine Linie auf das Display
	 * 
	 * @param x1 Erster Punkt X
	 * @param y1 Erster Punkt Y
	 * @param x2 Zweiter Punkt  X
	 * @param y2 Zweiter Punkt  Y
	 * @param color Farbe des Striches
	 */
	public void DrawLine(int x1, int y1, int x2, int y2, int color);
	/**
	 * Zeichnet einen Kreis. Wird für den Schussradius eines Towers benötigt
	 * @param x x-Koord des Mittelpunktes
	 * @param y y-Koord des Mittelpunktes
	 * @param r Radius des Kreises
	 * @param color Farbe des Kreises
	 */
	public void DrawCircle(int x, int y, int r, int color);
	
	public void render(int xPos, int yPos, int tile, int colour);
	/**
	 * Zeichnet ein 4x4 Feld
	 * 
	 * @param xPos X-Position
	 * @param yPos Y-Position
	 * @param xTile X-Position im SpriteSheet
	 * @param yTile Y-Position im SpriteSheet
	 * @param colour Farbe des zu zeichnenden Objektes
	 * @param scale Skalierung
	 */
	public void renderBigTile(int xPos, int yPos, int xTile, int yTile, int colour, int scale);
	/**
	 * Zeichnet ein Feld aus dem SpriteSheet
	 * @param xPos X-Position des zu zeichnenden Objects
	 * @param yPos Y-Position des zu zeichnenden Objects
	 * @param tile Welches Tile aus dem Spritesheet soll gerendert werden
	 * @param colour Die Farbe
	 * @param mirrorDir Bei Gegnern: Je nach Richtung werden die Tiles umgedreht
	 * @param scale Skalierung
	 */
	public void render(int xPos, int yPos, int tile, int colour, int mirrorDir, int scale);
	/**
	 * Setzt einen neuen Offset. Der Offset ist dafür gedacht, größere Level ins Spiel zu laden.
	 * Dadurch wäre es möglich, in dem bestehenden Screen nur einen Teil des Levels zu sehen und
	 * durch Maus/TastatureEingaben sich im Level zu bewegen.
	 * 
	 * @param x
	 * @param yOffset
	 */
	public void setOffset(int xOffset, int yOffset);
}
