package main;

import configuracion.Parametros;
import configuracion.Tileset;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * Nube que se mostrará como parte de los efectos atmosféricos del juego. Se trata 
 * de un <code>JLabel</code> con hilo propio, que se desplazará horizontalmente por
 * la pantalla
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public final class Nube extends JLabel implements Runnable{
    
    private static final int PERIODO_PROCESO = Parametros.getInstance().getMILISEGUNDOS_ENTRE_FRAMES();

    private int sentidoNube;
    
    private double posX;
    private double posY;
    
    private Thread hilo;
    
    /**
     * Constructor de <code>Nube</code>
     * 
     * @param tileset Clase que referencia las distintas imágenes
     * @param posY Posición Y (en pixeles) donde saldrá en el mapa
     */
    public Nube(Tileset tileset, int posY){
        try{
            this.setIcon(tileset.getNUBE());
            this.posX = Math.random()*Parametros.getInstance().getANCHO_PANTALLA_JUEGO();
            this.posY = posY;
            this.setBounds((int)posX,(int)posY,tileset.getNUBE().getIconWidth(),tileset.getNUBE().getIconHeight());
            this.sentidoNube = (int)(Math.random()*2);  
            this.hilo = new Thread(this);
        }catch(ClassCastException cce){
            //Si da error, la nube no sale
            Logger.getLogger(Nube.class.getName()).log(Level.SEVERE, null, cce);
        }
    }

    @Override
    public void run() {
        boolean juegoFuncionando = true;
        long ultimoProceso = System.currentTimeMillis();
        long ahora;
        while(juegoFuncionando){
            try {
                Thread.sleep(PERIODO_PROCESO);
            } catch (InterruptedException ex) {
                Logger.getLogger(Nube.class.getName()).log(Level.SEVERE, null, ex);
            }
            ahora = System.currentTimeMillis();
            double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
            ultimoProceso = ahora;
            // Actualizamos posiciones X e Y
            incrementaPos(retardo);
        }
    }
    
    /**
     * Método que simula la física de cada nube
     *
     * @param dt lapso de tiempo entre cada actualizacion
     */
    public void incrementaPos(double dt) {
        if (sentidoNube == 1) {
            posX += 0.3 * dt;
            if (posX>Parametros.getInstance().getANCHO_PANTALLA_JUEGO()) {
                posX=-Parametros.getInstance().getANCHO_PANTALLA_JUEGO();
            }           
        }else if (sentidoNube == 0){
            posX -= 0.3 * dt;
            if (posX<-Parametros.getInstance().getANCHO_PANTALLA_JUEGO()) {
                posX = Parametros.getInstance().getANCHO_PANTALLA_JUEGO();
            }
        } 
        setLocation(new Point((int)posX,(int)posY));
    }

    /**
     * Devuelve el <code>Thread</code> que controla el movimiento de la nube.
     * @return <code>Thread</code> que controla el movimiento de la nube.
     */
    public Thread getHilo() {
        return hilo;
    }
}
