package editor;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * OBjeto define el elemento que se sitúa sobre el fondo de la casilla. Determinará la imagen 
 * del elemento que llevará en el editor y en el programa final
 * 
 * cod -> Codigo que identificará a la casilla cuando se mapee el archivo
 * 
 * transitable -> Indica si la casilla es transitable por el personaje o no.
 * Para determinar la transitabilidad final d ela casilla, este valor se comparará
 * con la del Fondo que también hay en ella
 * 
 * descripcion -> Pequela descripción de la casilla, se usará para interactuar con 
 * ella posteriormente, desde el juego
 * 
 * puerta -> Indica con qué casilla y de qué mapa se vincula la casilla actual
 * 
 * @author ActionPerformed
 */
public class Objeto extends JButton{
    
	private static final long serialVersionUID = 1594974129923270881L;
	private Puerta puerta;
    private String descripcion = "";
    private boolean transitable = true;
    private String cod;

    public Objeto(boolean transitable, String cod, Icon icon) {
        super(icon);
        this.cod = cod;
        this.transitable = transitable;
    }
    
    public Objeto(){}

    /**
     * @param cod the cod to set
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * Puerta
     * 
     * En el editor, la clase Puerta está compuesta por 3 atributos
     * 
     * mapaDestino -> el indice del mapa al que lleva la puerta
     * coordIDestino -> la coordenada i del mapa al que lleva la puerta
     * coordJDestino -> la coordenada j del mapa al que lleva la puerta
     * 
     */
    public static class Puerta{
        private int mapaDestino;
        private int coordIDestino;
        private int coordJDestino;

        public Puerta(int mapaDestino, int coordIDestino, int coordJDestino) {
            this.mapaDestino = mapaDestino;
            this.coordIDestino = coordIDestino;
            this.coordJDestino = coordJDestino;
        }
        
        public Puerta(){};
        
        /*
        * GETTERS & SETTERS DE PUERTA
        */
        public int getMapaDestino() {
            return mapaDestino;
        }

        public void setMapaDestino(int mapaDestino) {
            this.mapaDestino = mapaDestino;
        }

        public int getCoordIDestino() {
            return coordIDestino;
        }

        public void setCoordIDestino(int coordIDestino) {
            this.coordIDestino = coordIDestino;
        }

        public int getCoordJDestino() {
            return coordJDestino;
        }

        public void setCoordJDestino(int coordJDestino) {
            this.coordJDestino = coordJDestino;
        }
    }
    
    ////GETTERS & SETTERS DE OBJETO
    public Puerta getPuerta() {
        return puerta;
    }

    public void setPuerta(Puerta puerta) {
        this.puerta = puerta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isTransitable() {
        return transitable;
    }

    public void setTransitable(boolean transitable) {
        this.transitable = transitable;
    }

    public Objeto getObjeto(){
        return this;
    }
    
    public void setObjeto(Objeto objeto){
        this.setIcon(objeto.getIcon());
        this.setPuerta(objeto.getPuerta());
        this.setDescripcion(objeto.getDescripcion());
        this.setTransitable(objeto.isTransitable());
        this.setCod(objeto.getCod());
    }

    public String getCod() {
        return cod;
    }
}
