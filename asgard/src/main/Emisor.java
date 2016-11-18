package main;

import configuracion.Parametros;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hilo emisor del mensaje. Se encargará de enviar un mensaje por broadcast a
 * toda la red.
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public class Emisor implements Runnable{

    private String mensaje;
    private Thread hilo;
    private InetAddress hostReceptor;
    
    /**
     * Crea un hilo que lanzará el mensaje especificado por broadcast
     * 
     * @param mensaje mensaje a enviar
     */
    public Emisor(String mensaje){
        try {
            hostReceptor = InetAddress.getByName(Parametros.getInstance().getIpBroadCastLocal());
            this.mensaje = mensaje;
            this.hilo = new Thread(this);
            hilo.start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Emisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            //Mando el mensaje por el puerto 9090
            DatagramSocket socket = new DatagramSocket();
            byte[] datos = mensaje.getBytes();
            DatagramPacket paquete = new DatagramPacket(datos,datos.length, hostReceptor, 9090);
            socket.send(paquete);
            Thread.sleep(50); //Sin esta línea, esto no tira ¿Por qué? Misterios de la vida
            socket.close();
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(Emisor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Emisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
