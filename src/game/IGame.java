package game;


import gfx.Colours;
import gfx.FontForGame;
import gfx.Screen;
import gfx.SpriteSheet;

import java.awt.BorderLayout;
import java.awt.Canvas;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;



import javax.swing.JFrame;

/**
 * In der Klasse Game wird das Fenster erzeugt. Hier befindet sich die Hauptschleife des Spiels
 * 
 * @author Kai Fl�ck
 * @version 1.0
*/
public interface IGame{

	
	
	
	/**
	 * Init Funktion, die das Overlay erstellt und die Karte einlie�t
	 * @author Kai Fl�ck
	 */
	
	
	public void init();
	/**
	 * Startet das Spiel-> Startet einen Thread. Ab hier l�uft die run-Methode des Threads
	 * 
	 * @author Kai Fl�ck
	 * @version 1.0
	*/
	public void start();
	/**
	 * Beendet das Spiel
	 * 
	 * @author Kai Fl�ck
	 * @version 1.0
	*/
	public void stop();
	/**
	 * Hauptschleife des Spiels, hier werden alle 
	 * ticks() - Updaten der Variablen / Spielmechanik
	 * und
	 * render() - Neuzeichnen des Bildschirms
	 * Methoden aufgerufen
	 * 
	 * @author Kai Fl�ck
	 * @version 1.0
	*/
	public void run();
	/**
	 * Beinhaltet die Spielelogik. Updatet Tower, Enemys, Sch�sse
	 * 
	 * @author Kai Fl�ck
	 * @version 1.0
	*/
	public void tick();
	
	/**
	 * Neuzeichnen des Bildschirms. Durch die BufferStrategy wird das Bild weicher gezichnet.
	 * Es entsteht kein Flimmern
	 * 
	 * @author Kai Fl�ck
	 * @version 1.0
	*/
	public void render();

	

}
