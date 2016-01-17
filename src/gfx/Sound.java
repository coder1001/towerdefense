package gfx;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

/**
 * Klasse für die Soundausgabe
 * 
 * 
 * @author Marko Susic
 * @version 1.0
 */

public class Sound {

	private Clip clip; 
	
	public Sound(String filename)
	{
		try
		{     
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/"+filename));
	        clip = AudioSystem.getClip();
            clip.open(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	/**
	 * Startet den Sound
	 */
	public void Start()
	{
	    clip.setFramePosition(0);
		clip.start();
	}
	
	/**
	 * Beendet den Sound
	 */
	public void Stop()
	{
	   clip.stop();	
	}
	

	
}
