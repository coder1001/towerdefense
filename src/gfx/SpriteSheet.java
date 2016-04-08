package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * Lädt das SpriteSheet und beschreibt die einzelnen Pixel
 * 
 * @author Marko Susic
 * @version 1.0
 */

public class SpriteSheet implements ISpriteSheet{
	public String path;
	public int width;
	public int height;
	
	public int[] pixels;
	
	public SpriteSheet(String path){
		BufferedImage image = null;
		//Lädt das Spritesheet in 'image'
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(image == null){
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0,0,width,height,null, 0, width);
		
		/* Ermittelt die RGB-Werte jedes Pixels und sortiert diese in die 4 Farben ein
		 * 
		 * schwarz, dunkelgrau, hellgrau, weiß
		 *  
		 * schwarz    ist   0   0   0  (RGB) ->0xff ->  0/64=0
		 * dunkelgrau ist  85  85  85  (RGB) ->0xff -> 85/64 = 1
		 * hellgrau   ist 170 170 170  (RGB) ->0xff -> 170/64 = 2
		 * weiß       ist 255 255 255  (RGB) ->0xff -> 255/64 = 3
		 */
		for(int i=0; i<pixels.length;i++)	
			pixels[i] = (pixels[i] & 0xff)/ 64; 
	}
}
