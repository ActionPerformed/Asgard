/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.JLabel;

/**
 * Casilla del juego, traducida desde la <code>Casilla</code> del editor para ser 
 * así usada por el juego.
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public class CasillaMapa {
    
    private final JLabel fondo;
    private final JLabel objeto;
    private final boolean transitable;
    private final String descripcion;
    private Puerta puerta;
    private final String fondoCod;
    private final String objetoCod;
    
    /**
     * Construye un elemento del tipo <code>CasillaMapa</code> a partir de 
     * un <code>editor.Casilla</code> 
     * 
     * @param editorCasilla Elemento <code>editor.Casilla</code> que se usará
     * para la creaciónóel objeto.
     * @see editor.Casilla
     */
    public CasillaMapa(editor.Casilla editorCasilla){
        
        this.fondo = new JLabel();
        this.objeto = new JLabel();
        this.transitable = editorCasilla.getObjeto().isTransitable();
        this.descripcion = editorCasilla.getObjeto().getDescripcion();
        
        this.fondo.setIcon(editorCasilla.getFondo().getIcon());
        this.objeto.setIcon(editorCasilla.getObjeto().getIcon());
        
        this.fondoCod = editorCasilla.getFondo().getCod();
        this.objetoCod = editorCasilla.getObjeto().getCod();
        
        if (editorCasilla.getObjeto().getPuerta().getMapaDestino()>0) {
            puerta = new Puerta();
            this.puerta.setiDestino(editorCasilla.getObjeto().getPuerta().getCoordIDestino());
            this.puerta.setjDestino(editorCasilla.getObjeto().getPuerta().getCoordJDestino());
            this.puerta.setIdPantallaDestino(editorCasilla.getObjeto().getPuerta().getMapaDestino()); 
        }   
    }

    /**
     * Devuelve un <code>JLabel</code> que contiene el fondo de la casilla
     * @return <code>JLabel</code> que contiene el fondo de la casilla
     */
    public JLabel getFondo() {
        return fondo;
    }

    /**
     * Devuelve un <code>JLabel</code> que contiene el objeto de la casilla
     * @return <code>JLabel</code> que contiene el objeto de la casilla
     */
    public JLabel getObjeto() {
        return objeto;
    }

    /**
     * Devuelve la transitabilidad de la casilla
     * @return Transitabilidad de la casilla
     */
    public boolean isTransitable() {
        return transitable;
    }

    /**
     * Devuelve la descripcion de la casilla
     * @return Descripcion de la casilla
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve la <code>Puerta</code> de la casilla
     * @return <code>Puerta</code> de la casilla
     */
    public Puerta getPuerta() {
        return puerta;
    }

    /**
     * Devuelve el codigo del fondo de la casilla
     * @return Codigo del fondo de la casilla
     */
    public String getFondoCod() {
        return fondoCod;
    }

    /**
     * Devuelve el codigo del objeto de la casilla
     * @return Codigo del objeto de la casilla
     */
    public String getObjetoCod() {
        return objetoCod;
    }
}
