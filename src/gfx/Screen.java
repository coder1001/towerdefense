package gfx;

public class Screen {
	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH-1;
	
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;
	
	public int[] pixels;
	
	public int xOffset = 0;
	public int yOffset = 0;
	
	public int width;
	public int height;
	
	public SpriteSheet sheet;
	
	public Screen(int width,int height, SpriteSheet sheet){
		this.width=width;
		this.height=height;
		this.sheet=sheet;
		
		pixels = new int[width*height];
	}
	
	

	
	
	public void DrawLine(int x1, int y1, int x2, int y2, int color)
	{
		x1 -= xOffset;
		y1 -= yOffset;
		x2 -= xOffset;
		y2 -= yOffset;
		
		int x = x2-x1;
		int y = y2-y1;
		double len = (int)Math.sqrt(x*x+y*y);
        //int color = 40;//blau
		
        //Abstand der einzelnen Pixel
        double xdif = x/len;
        double ydif = y/len;        
        
        int pixelende = x2+y2*width;	        
            
		for(int i = 0;i<len;i++)
		{		
			int pixelX = x1+(int)Math.floor(xdif*i);
			int pixelY = y1+(int)Math.floor(ydif*i);
			
			if(pixelX>0 && pixelX<this.width && pixelY >0 && pixelY < this.height){
					int pixel = pixelX+pixelY*width;		
					pixels[pixel] = color;
			}
		}
		//pixels[x1+y1*width] = 140; //anfang zeichnen
		//pixels[pixelende] = 140; //ende zeichnen
	}
	
	public void DrawCircle(int x, int y, int r, int color)
	{
		for(int i = 0;i<360;i++)
		{
			int xk = x+(int)(r*Math.cos(i))-xOffset;
			int yk = y+(int)(r*Math.sin(i))-yOffset;
			
			if(xk>0 && xk<this.width && yk >0 && yk < this.height){
			int pixel = xk+yk*width;		
			pixels[pixel] = color;
			}
		}
	}
	
	public void render(int xPos, int yPos, int tile, int colour){
		render(xPos,yPos,tile,colour,0x00,1);
	}
	
	public void renderBigTile(int xPos, int yPos, int xTile, int yTile, int colour, int scale)
	{
		int modifier = 8 * scale;
		int xOffset = xPos - modifier/2;
		int yOffset = yPos - modifier/2 -4;
		
		render(xOffset , yOffset , xTile + yTile * 32, colour);
		render(xOffset + modifier, yOffset , (xTile + 1) + yTile * 32, colour);
		render(xOffset , yOffset + modifier , xTile + (yTile+1) * 32, colour);
		render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour);
	}
	
	public void render(int xPos, int yPos, int tile, int colour, int mirrorDir, int scale){
		xPos -= xOffset;
		yPos -= yOffset;
		
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
        
		int scaleMap = scale-1 ;
		int xTile = tile % 32; //Anzahl der Spalten im SpriteSheet
		int yTile = tile / 32; //Anzahl der Zeilen im SpriteSheet
		int tileOffset  = (xTile << 3) + (yTile << 3) * sheet.width;
		for(int y = 0; y < 8; y++){
			int ySheet = y;
			if(mirrorY) 
				ySheet = 7 - y;
			int yPixel = (y + yPos) + (y * scaleMap) - ((scaleMap << 3)/2);
			
			for(int x = 0; x < 8; x++){
				
				int xSheet = x;
				if(mirrorX) 
					xSheet = 7 - x;
				int xPixel =(x + xPos) + (x * scaleMap) - ((scaleMap << 3)/2);
				int col = (colour >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset]*8)) & 255;
				if(col < 255) {
					for(int yScale = 0; yScale < scale; yScale ++){
						if(yPixel+yScale < 0 || yPixel + yScale >= height) 
							continue;
						for(int xScale = 0; xScale < scale; xScale ++){
							if(xPixel + xScale < 0 || xPixel + xScale >= width) 
								continue;
							pixels[(xPixel + xScale)+(yPixel + yScale)*width] = col;
							
						}
					}
					
					
					
				}
			}
		}
		
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;	
	}
}
