package gfx;

/**
 * Ermöglicht das Schreiben von Texten im Spiel. Die Klasse greift auf die letzten beiden
 * Zeilen des SpriteSheet zu
 * 
 * @author Marko Susic
 * @version 1.0
 */

public class FontForGame {
	
	private static String chars=""+"ABCDEFGHIJKLMNOPQRSTUVWXYZ      "+"0123456789.,:;'\"!?$%()-=+/      ";
	
	public static void render(String msg, Screen screen, int x, int y, int colour, int scale){
		msg = msg.toUpperCase();
		
		for(int i=0;i<msg.length();i++){
			int charIndex = chars.indexOf(msg.charAt(i));
			if(charIndex>=0) screen.render(x+(i*(8*scale)),y,charIndex + 30*32, colour, 0x00, scale);
			
		}
		
	}
}
