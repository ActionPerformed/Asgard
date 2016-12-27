package main;

import javax.swing.JLabel;

import configuracion.Parametros;
import utils.Constant;

/**
 * Define las casillas que actuarán como enlace entre dos mapas diferentes
 * 
 * <ul>
    * <li>panelJuego --> panel de Juego</li>
    * <li>idPantallaDestino --> id del mapa destino</li>
    * <li>pantallaDestino --> mapa destino</li>
    * <li>iDestino --> coordenada i de la pantalla destino</li>
    * <li>jDestino --> coordenada j de la pantalla destino</li>
 * </ul>
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public final class Puerta extends JLabel{
    
	private static final long serialVersionUID = 8648493133391048410L;
	private static final int numTilesHorizontal = Parametros.getInstance().getANCHO_PANTALLA_JUEGO()/32;
    private static final int numTilesVertical = Parametros.getInstance().getALTO_PANTALLA_JUEGO()/32;
    private PanelJuego panelJuego;
    private int idPantallaDestino;
    private int iDestino; 
    private int jDestino; 
    
    /**
     * Metodo que cambia la pantalla de juego actual y resitúa al personaje en una
     * nueva ubicaciónón función de los parámetros de la <code>Puerta</code>
     * 
     * @param personaje <code>Personaje</code> del juego
     * @see Personaje
     * @see Puerta
     */
    public void traspasar(Personaje personaje){
        
        PantallaJuego.getPanelNegro().setVisible(true);
        repaint();
        
        PantallaJuego pantallaNueva = new PantallaJuego(idPantallaDestino,panelJuego);
        pantallaNueva.setBounds(panelJuego.getPantallaActual().getBounds());
        panelJuego.remove(panelJuego.getPantallaActual());
       
        panelJuego.setPantallaActual(pantallaNueva);
        panelJuego.add(panelJuego.getPantallaActual(),new Integer(0));

        PantallaJuego.getCapaPantalla().removeAll();
        personaje.setPantallaJuego(panelJuego.getPantallaActual());
        personaje.getPantallaJuego().setVisible(false);        
        
        for (int i = 0; i < numTilesVertical; i++) {
            for (int j = 0; j < numTilesHorizontal; j++) {
                panelJuego.getPantallaActual().colocaCasilla(i,j);
            }
        }
        
        PantallaJuego.getCapaPantalla().add(panelJuego.getPersonaje(),new Integer((2*(((int)panelJuego.getPersonaje().getPosY()+32)/32))+1));
        panelJuego.getPersonaje().setPosX((jDestino)*32);
        panelJuego.getPersonaje().setPosY((iDestino)*32);
        
        //Mostramos u ocultamos efectos de Interior o exterior, segun corresponda, en el mapa inicial
        //NOTA: El ciclo dia/noche no va aqui al iniciar el mapa, sino en el propio personaje
        //que lo revisa continuamente.
        panelJuego.mostrarEfectos(!panelJuego.getPantallaActual().isMapaInterior());
        
        
        //Con este metodo desplazamos al personaje un pixel fuera del area de la posible puerta de retorno
        switch(panelJuego.getPersonaje().getUltimoMovimiento()){
            case Constant.ARRIBA:
                panelJuego.getPersonaje().setPosY(panelJuego.getPersonaje().getPosY() - 1);
                break;
            case Constant.ABAJO:
                panelJuego.getPersonaje().setPosY(panelJuego.getPersonaje().getPosY() + 1);
                break;
            case Constant.DERECHA:
                panelJuego.getPersonaje().setPosX(panelJuego.getPersonaje().getPosX() + 1);
                break;
            case Constant.IZQUIERDA:
                panelJuego.getPersonaje().setPosX(panelJuego.getPersonaje().getPosX() - 1);
                break;
        }
        
        personaje.getPantallaJuego().setVisible(true);
                
        for (int i = 0; i < panelJuego.getNube().size(); i++) {
            PantallaJuego.getCapaPantalla().add(panelJuego.getNube().get(i),new Integer(99));
        }
        
        PantallaJuego.getPanelNegro().setVisible(false);
        System.gc();
    }

    /**
     * Fija el <code>PanelJuego</code> usado
     * @param panelJuego <code>PanelJuego</code> usado
     */
    public void setPanelJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    /**
     * Fija el codigo del mapa destino.
     * @param idPantallaDestino Códigoóel mapa destino.
     */
    public void setIdPantallaDestino(int idPantallaDestino) {
        this.idPantallaDestino = idPantallaDestino;
    }

    /**
     * Fija la coordejada i (en casillas) del mapa destin, donde se mostrará
     * el personaje una vez traspase la puerta.
     * @param iDestino Coordejada i (en casillas) del mapa destin, donde se mostrará
     * el personaje una vez traspase la puerta.
     */
    public void setiDestino(int iDestino) {
        this.iDestino = iDestino;
    }

    /**
     * Fija la coordejada j (en casillas) del mapa destin, donde se mostrará
     * el personaje una vez traspase la puerta.
     * @param jDestino Coordejada j (en casillas) del mapa destin, donde se mostrará
     * el personaje una vez traspase la puerta.
     */
    public void setjDestino(int jDestino) {
        this.jDestino = jDestino;
    }
}
