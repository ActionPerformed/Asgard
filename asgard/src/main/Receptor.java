package main;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Hilo receptor, que queda a la escucha de los diversos mensajes recibidos
 * desde el dispositivo móvil (caña de pescar)
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public class Receptor implements Runnable{

    private final int MAX_LEN=256;
    private final Thread hilo;
    
    public Receptor(){
        this.hilo =new Thread(this);        
        hilo.start();
    }
    
    @Override
    public void run() {
        try {
            byte buffer[] = new byte[MAX_LEN];
            DatagramPacket paquete = new DatagramPacket(buffer,MAX_LEN);
            while(true){
                try{
                    //mantenemos la escucha en el puerto 9090
                    DatagramSocket socket = new DatagramSocket(9091);
                    socket.receive(paquete);
                    muestraMensaje(paquete);
                    socket.close();
                }catch(BindException ex){ //Si el puerto está en uso, el programa no se inicia
                    Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "El programa ya esta en funcionamiento");
                    System.exit(0);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Muestra el mensaje recibido por pantalla.
     * 
     * @param paquete Mensaje recibido
     */
    public void muestraMensaje(DatagramPacket paquete){
        String mensaje  = new String(paquete.getData(),0,paquete.getLength());
        StringTokenizer st = new StringTokenizer(mensaje,";");
        String distancia = st.nextToken();
        String pesoPez = st.nextToken();
        PanelJuego.getPanelDialogo().escribeLanzamiento(distancia, pesoPez);
    }
}
