package editor;

import javax.swing.ImageIcon;

/**
 * Casilla
 * 
 * Los elementos casilla están formados por dos Object: uno tipo Objeto y uno tipo Tile
 * 
 * El primero extenderá de Button y guardará el objeto que se sitúe en la casilla (un arbol,
 * una cornisa,...) y los datos referentes a esa casilla (si hay puerta, si hay descripcion, etc)
 * 
 * El segundo extenderá de JLabel y guardará la imagen de fondo de la casilla
 *
 * @author ActionPerformed
 */
public final class Casilla{
    
    private Objeto objeto;
    private Tile fondo;

    Casilla(ImageIcon imageIcon) {
        fondo = new Tile();
        fondo.setIcon(imageIcon);
        objeto = new Objeto();
    }
    
    public Casilla(){};

    /*
     * GETTERS & SETTERS
     */
    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }
        
    public Tile getFondo() {
        return fondo;
    }

    public void setFondo(Tile fondo) {
        this.fondo = fondo;
    }
}
