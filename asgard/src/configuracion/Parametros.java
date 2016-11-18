/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package configuracion; 

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.PanelPresentacion;

/**
 * Parametros tiene como función llevar un control centralizado de toda 
 * la configuración global.
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public class Parametros {
    
    private static final Parametros INSTANCE = new Parametros();
    
    private BufferedImage[] mapaTileset;
    private Locale localizacion = null;
    private final int ANCHO_PANTALLA_JUEGO = 640;
    private final int ALTO_PANTALLA_JUEGO = 480;
    private final int MILISEGUNDOS_ENTRE_FRAMES = 20;
    private final String rutaTilesetDia = "/img/tileset_dia.png";
    private final String rutaTilesetNoche = "/img/tileset_noche.png";
    private final String ipBroadCastLocal = obtenerBroadCastLocal();
    
    //FUENTE CC-BY --> http://fontstruct.com/fontstructions/show/pixel_unicode
    private final String FONT_PIXEL = "/fonts/Pixel-UniCode.ttf";
    
    /**
     * Constructor de Parametros. Dentro de él se instancia el mapa de imagenes
     * 
     */
    public Parametros(){
        try {
            mapaTileset = new BufferedImage[2];
            mapaTileset[0] = ImageIO.read(this.getClass().getResource(rutaTilesetDia));
            mapaTileset[1] = ImageIO.read(this.getClass().getResource(rutaTilesetNoche));
        } catch (IOException ex) {
            Logger.getLogger(Parametros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Devuelve una instancia de la clase
     * 
     * @return Instancia de la clase
     * @see Parametros
     */
    public static Parametros getInstance() {
        return INSTANCE;
    }

    /**
     * Devuelve un Locale con la localización de idioma
     * 
     * @return Un elemento Locale con la localización del idioma
     */
    public Locale getLocalizacion() {
        return localizacion;
    }

    /**
     * Asigna el Locale indicado a Parametros, para su posterior uso
     * 
     * @param localizacion Locale con el nuevo idioma que usará la aplicacion
     */
    public void setLocalizacion(Locale localizacion) {
        this.localizacion = localizacion;
    }

    /**
     * Devuelve el ancho de pantalla del juego
     * 
     * @return Un entero cuyo valor es el ancho de pantalla del juego
     */
    public int getANCHO_PANTALLA_JUEGO() {
        return ANCHO_PANTALLA_JUEGO;
    }

    /**
     * Devuelve el alto de pantalla del juego
     * 
     * @return Un entero cuyo valor es el alto de pantalla del juego
     */
    public int getALTO_PANTALLA_JUEGO() {
        return ALTO_PANTALLA_JUEGO;
    }
    
    /**
     * Devuelve el intervalo de refresco entre frames, del juego
     * 
     * @return Un entero, en milisegundos, que indica cada cuánto se refrescará
     * la pantalla de juego
     */
    public int getMILISEGUNDOS_ENTRE_FRAMES() {
        return MILISEGUNDOS_ENTRE_FRAMES;
    }

    /**
     * Devuelve el conjunto de Tilesets usado (dia/noche)
     * 
     * @return Los mapas de dibujos que usa el juego
     */
    public BufferedImage[] getMapaTileset() {
        return mapaTileset;
    }

    /**
     * Devuelve la dirección de broadcast de la red local
     * 
     * @return Un String que contiene la ip del broadcast local
     */
    public String getIpBroadCastLocal() {
        return ipBroadCastLocal;
    }
        
    /**
     * Obtiene la direccion de broadcast local, a partir de la ip
     * del dispositivo.
     * 
     * @return Un Strind con que contiene la ip del broadcast local
     */
    private String obtenerBroadCastLocal(){
        String redLocal = "";
        try {
            redLocal = InetAddress.getLocalHost().getHostAddress();
            redLocal = redLocal.substring(0,redLocal.lastIndexOf("."))+".255";
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(PanelPresentacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return redLocal;
    }

    /**
     * Obtiene el nombre de la fuente de texto guardado en FONT_PIXEL
     * 
     * @return Un String con el contenido de FONT_PIXEL (La fuente a usar)
     */
    public String getFONT_PIXEL() {
        return FONT_PIXEL;
    }
}
