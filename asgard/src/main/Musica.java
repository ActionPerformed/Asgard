package main;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Clase que controla el hilo musical del juego. Para las melodías se ha optado
 * por los siguientes recursos, los cuales se hallan bajo licencia CC-BY 3.0
 * 
 * <ul>
 *  <li>dia: http://opengameart.org/content/lively-meadow-victory-fanfare-and-song</li>
 *  <li>grassy world: http://opengameart.org/content/grassy-world-overture-8bitorchestral</li>
 *  <li>noche: http://opengameart.org/content/pleasant-creek </li>
 * </ul>
 * 
 * Attribution Instructions: http://www.matthewpablo.com/services
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public class Musica implements Runnable{
    
    private Clip clip;
    private final Thread hiloMusica;
    private final String nombreCancion;
    
    /**
     * Constructor de <code>Musica</code>
     * 
     * @param nombreCancion Nombre de la canción que sonará
     */
    public Musica(String nombreCancion){
        this.nombreCancion = nombreCancion;
        hiloMusica = new Thread(this);
    }
    
    @Override
    public void run() {
        try {
           URL url = getClass().getClassLoader().getResource("snd/"+nombreCancion+".wav");
           if(url != null) {
	           AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	           clip = AudioSystem.getClip();
	           clip.open(audioIn);
	           clip.loop(Clip.LOOP_CONTINUOUSLY);
	           clip.start();
           }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            clip.close();
        }
    }
    
    /**
     * Cambia la musica que suena en el hilo musical por la indicada bajo parámetro
     * 
     * @param nombreCancion Nombre de la canción que va a sonar
     */
    public void cambiarMusica(String nombreCancion){
        try {
           URL url = getClass().getClassLoader().getResource("snd/"+nombreCancion+".wav");
           if(url != null) {
	           AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	           clip.close();
	           clip.open(audioIn);
	           clip.loop(Clip.LOOP_CONTINUOUSLY);
	           clip.start();
           }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
           clip.close();
        }
    }

    /**
     * Devuelve el <code>Thread</code> que controla la música
     * @return <code>Thread</code> que controla la música
     */
    public Thread getHiloMusica() {
        return hiloMusica;
    }
}
