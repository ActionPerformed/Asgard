package editor;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Tile define el elemento de fondo de la casilla. Determinará la imagen de fondo 
 * que llevará en el editor y en el programa final
 * 
 * cod -> Codigo que identificará a la casilla cuando se mapee el archivo
 * 
 * transitable -> Indica si la casilla es transitable por el personaje o no.
 * Para determinar la transitabilidad final d ela casilla, este valor se comparará
 * con la del Objeto que también hay en ella
 * 
 * @author ActionPerformed
 */
public class Tile extends JLabel{
    
    private String cod;
    private boolean transitable;

    /*
    * CONSTRUCTORES
    */
    
    public Tile(boolean transitable, String cod, Icon icon) {
        super(icon);
        this.cod = cod;
        this.transitable = transitable;
    }
    
    public Tile(){};
    
    /*
    * GETTERS Y SETTERS
    */
    
    public Tile getTile(){
        return this;
    }
    
    public void setTile(Tile tile){
        this.setCod(tile.getCod());
        this.setIcon(tile.getIcon());
        this.setTransitable(tile.isTransitable());
    }

    public boolean isTransitable() {
        return transitable;
    }

    public void setTransitable(boolean transitable) {
        this.transitable = transitable;
    }

    public String getCod() {
        return cod;
    }

    /**
     * @param cod the cod to set
     */
    public void setCod(String cod) {
        this.cod = cod;
    }
}