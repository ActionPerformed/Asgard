package configuracion;

import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ActionPerformed
 */
public class Tileset {
    
    final static boolean diaInicioJuego = true;
    
    private static Tileset INSTANCE = new Tileset(diaInicioJuego);
    //
    
    public static boolean dia = diaInicioJuego;
    
    Icon[] NUBE;
    Icon[] HIERBA;
    
    public Icon[] PERSONAJE_DER;
    public Icon[] PERSONAJE_IZQ;
    public Icon[] PERSONAJE_ARR;    
    public Icon[] PERSONAJE_ABA;
    
    // NPCs
    
    Icon NPC_HOMBRE;
    Icon NPC_MUJER;
    
    // CASILLAS DE FONDO
    
    Icon TIERRA; //32x32
    Icon BLANK; //32x32
    Icon TARIMA; //32x32
    Icon AGUA; //32x32
    Icon ARBOL; //32x48
    
    //TILESET DE CORNISAS
    
    Icon BORDE_TIERRA_AR_IZ;
    Icon BORDE_TIERRA_AR_DE;
    Icon BORDE_TIERRA_AB_IZ;
    Icon BORDE_TIERRA_AB_DE;
    
    Icon BORDE_TIERRA_AR_IZ_ESQUINA;
    Icon BORDE_TIERRA_AR_DE_ESQUINA;
    Icon BORDE_TIERRA_AB_IZ_ESQUINA;
    Icon BORDE_TIERRA_AB_DE_ESQUINA;
        
    Icon[] BORDE_TIERRA_AR;
    
    Icon[] BORDE_TIERRA_AB;
    Icon[] BORDE_TIERRA_DE;
    Icon[] BORDE_TIERRA_IZ;
    
    //TILESET DE ALFOMBRA
    
    private Icon ALFOMBRA_AR_IZ;
    private Icon ALFOMBRA_AR_DE;
    private Icon ALFOMBRA_AB_IZ;
    private Icon ALFOMBRA_AB_DE;
    private Icon ALFOMBRA_CENTRO;   
    private Icon ALFOMBRA_AR;    
    private Icon ALFOMBRA_AB;
    private Icon ALFOMBRA_DE;
    private Icon ALFOMBRA_IZ;
     
    //TILESET DE SILLAS
    
    private Icon SILLA_AB;    
    private Icon SILLA_IZ;
    private Icon SILLA_AR;
    private Icon SILLA_DE;
    
    //TILESET DE MESA
    private Icon MESA;
    
    //TILESET DE LIBRERIA
    private Icon LIBRERIA;
    
    //TILESET DE RAMPAS
    
    Icon RAMPA_AB;
    Icon RAMPA_AR;
    Icon RAMPA_DE;
    Icon RAMPA_IZ; 
        
    //TILESET DE COSTA de HIERBA
    
    Icon BORDE_AGUA_AR_IZ;
    Icon BORDE_AGUA_AR_DE;
    Icon BORDE_AGUA_AB_IZ;
    Icon BORDE_AGUA_AB_DE;
    Icon BORDE_AGUA_AR_IZ_ESQUINA;
    Icon BORDE_AGUA_AR_DE_ESQUINA;
    Icon BORDE_AGUA_AB_IZ_ESQUINA;
    Icon BORDE_AGUA_AB_DE_ESQUINA;
    Icon[] BORDE_AGUA_AR;
    Icon[] BORDE_AGUA_AB;
    Icon[] BORDE_AGUA_DE;
    Icon[] BORDE_AGUA_IZ;
    
    //TILESET DE COSTA DE ARENA
    
    Icon BORDE_AGUA2_AR_IZ;
    Icon BORDE_AGUA2_AR_DE;
    Icon BORDE_AGUA2_AB_IZ;
    Icon BORDE_AGUA2_AB_DE;
    Icon BORDE_AGUA2_AR_IZ_ESQUINA;
    Icon BORDE_AGUA2_AR_DE_ESQUINA;
    Icon BORDE_AGUA2_AB_IZ_ESQUINA;
    Icon BORDE_AGUA2_AB_DE_ESQUINA;
    Icon[] BORDE_AGUA2_AR;
    Icon[] BORDE_AGUA2_AB;
    Icon[] BORDE_AGUA2_DE;
    Icon[] BORDE_AGUA2_IZ;
    
    //TILESET DE CASA
    Icon[][] CASA;

    /**
     * Constructor
     */
    public Tileset(boolean diaNoche) {
        this.dia = diaNoche;
        
        this.BORDE_AGUA_AB_IZ_ESQUINA = crearSubimagen(diaNoche, 4, 3, 32, 32);
        this.CASA = generarCasa(diaNoche);
        this.BORDE_AGUA2_IZ = new Icon[]{
            crearSubimagen(diaNoche, 7, 2, 32, 32), 
            crearSubimagen(diaNoche, 7, 3, 32, 32)
        };
        this.BORDE_AGUA2_DE = new Icon[]{
            crearSubimagen(diaNoche, 10, 2, 32, 32), 
            crearSubimagen(diaNoche, 10, 3, 32, 32)
        };
        this.BORDE_AGUA2_AB = new Icon[]{
            crearSubimagen(diaNoche, 8, 4, 32, 32), 
            crearSubimagen(diaNoche, 8, 4, 32, 32)
        };
        this.BORDE_AGUA2_AR = new Icon[]{
            crearSubimagen(diaNoche, 8, 1, 32, 32), 
            crearSubimagen(diaNoche, 9, 1, 32, 32)
        };
        this.BORDE_AGUA2_AB_DE_ESQUINA = crearSubimagen(diaNoche, 9, 3, 32, 32);
        this.BORDE_AGUA2_AB_IZ_ESQUINA = crearSubimagen(diaNoche, 8, 3, 32, 32);
        this.BORDE_AGUA2_AR_DE_ESQUINA = crearSubimagen(diaNoche, 9, 2, 32, 32);
        this.BORDE_AGUA2_AR_IZ_ESQUINA = crearSubimagen(diaNoche, 8, 2, 32, 32);
        this.BORDE_AGUA2_AB_DE = crearSubimagen(diaNoche, 10, 4, 32, 32);
        this.BORDE_AGUA2_AB_IZ = crearSubimagen(diaNoche, 7, 4, 32, 32);
        this.BORDE_AGUA2_AR_DE = crearSubimagen(diaNoche, 10, 1, 32, 32);
        this.BORDE_AGUA2_AR_IZ = crearSubimagen(diaNoche, 7, 1, 32, 32);
        this.BORDE_AGUA_IZ = new Icon[]{
            crearSubimagen(diaNoche, 3, 2, 32, 32), 
            crearSubimagen(diaNoche, 3, 3, 32, 32)
        };
        this.BORDE_AGUA_DE = new Icon[]{
            crearSubimagen(diaNoche, 6, 2, 32, 32), 
            crearSubimagen(diaNoche, 6, 3, 32, 32)
        };
        this.BORDE_AGUA_AB = new Icon[]{
            crearSubimagen(diaNoche, 4, 4, 32, 32), 
            crearSubimagen(diaNoche, 5, 4, 32, 32)
        };
        this.BORDE_AGUA_AR = new Icon[]{
            crearSubimagen(diaNoche, 4, 1, 32, 32), 
            crearSubimagen(diaNoche, 5, 1, 32, 32)
        };
        this.BORDE_AGUA_AB_DE_ESQUINA = crearSubimagen(diaNoche, 5, 3, 32, 32);
        this.BORDE_AGUA_AR_DE_ESQUINA = crearSubimagen(diaNoche, 5, 2, 32, 32);
        this.BORDE_AGUA_AR_IZ_ESQUINA = crearSubimagen(diaNoche, 4, 2, 32, 32);
        this.BORDE_AGUA_AB_DE = crearSubimagen(diaNoche, 6, 4, 32, 32);
        this.BORDE_AGUA_AB_IZ = crearSubimagen(diaNoche, 3, 4, 32, 32);
        this.BORDE_AGUA_AR_DE = crearSubimagen(diaNoche, 6, 1, 32, 32);
        this.BORDE_AGUA_AR_IZ = crearSubimagen(diaNoche, 3, 1, 32, 32);
        this.RAMPA_IZ = crearSubimagen(diaNoche, 12, 2, 32, 32);
        this.RAMPA_DE = crearSubimagen(diaNoche, 11, 2, 32, 32);
        this.RAMPA_AR = crearSubimagen(diaNoche, 12, 1, 32, 32);
        this.RAMPA_AB = crearSubimagen(diaNoche, 11, 1, 32, 32);
        this.LIBRERIA = crearSubimagen(diaNoche, 11, 3, 32, 48);
        this.MESA = crearSubimagen(diaNoche, 0, 4, 32, 32);
        this.SILLA_DE = crearSubimagen(diaNoche, 2, 5, 32, 32);
        this.SILLA_AR = crearSubimagen(diaNoche, 1, 5, 32, 32);
        this.SILLA_IZ = crearSubimagen(diaNoche, 2, 4, 32, 32);
        this.SILLA_AB = crearSubimagen(diaNoche, 1, 4, 32, 32);
        this.ALFOMBRA_IZ = crearSubimagen(diaNoche, 0, 2, 32, 32);
        this.ALFOMBRA_DE = crearSubimagen(diaNoche, 2, 2, 32, 32);
        this.ALFOMBRA_AB = crearSubimagen(diaNoche, 1, 3, 32, 32);
        this.ALFOMBRA_AR = crearSubimagen(diaNoche, 1, 1, 32, 32);
        this.ALFOMBRA_CENTRO = crearSubimagen(diaNoche, 1, 2, 32, 32);
        this.ALFOMBRA_AB_DE = crearSubimagen(diaNoche, 2, 3, 32, 32);
        this.ALFOMBRA_AB_IZ = crearSubimagen(diaNoche, 0, 3, 32, 32);
        this.ALFOMBRA_AR_DE = crearSubimagen(diaNoche, 2, 1, 32, 32);
        this.ALFOMBRA_AR_IZ = crearSubimagen(diaNoche, 0, 1, 32, 32);
        this.BORDE_TIERRA_IZ = new Icon[]{
            crearSubimagen(diaNoche, 7, 6, 32, 32), 
            crearSubimagen(diaNoche, 7, 7, 32, 32)
        };
        this.BORDE_TIERRA_DE = new Icon[]{
            crearSubimagen(diaNoche, 10, 6, 32, 32), 
            crearSubimagen(diaNoche, 10, 7, 32, 32)
        };
        this.BORDE_TIERRA_AB = new Icon[]{
            crearSubimagen(diaNoche, 8, 8, 32, 32), 
            crearSubimagen(diaNoche, 9, 8, 32, 32)
        };
        this.BORDE_TIERRA_AR = new Icon[]{
            crearSubimagen(diaNoche, 8, 5, 32, 32), 
            crearSubimagen(diaNoche, 9, 5, 32, 32)
        };
        this.BORDE_TIERRA_AB_DE_ESQUINA = crearSubimagen(diaNoche, 9, 7, 32, 32);
        this.BORDE_TIERRA_AB_IZ_ESQUINA = crearSubimagen(diaNoche, 8, 7, 32, 32);
        this.BORDE_TIERRA_AR_DE_ESQUINA = crearSubimagen(diaNoche, 9, 6, 32, 32);
        this.BORDE_TIERRA_AR_IZ_ESQUINA = crearSubimagen(diaNoche, 8, 6, 32, 32);
        this.BORDE_TIERRA_AB_DE = crearSubimagen(diaNoche, 10, 8, 32, 32);
        this.BORDE_TIERRA_AB_IZ = crearSubimagen(diaNoche, 7, 8, 32, 32);
        this.BORDE_TIERRA_AR_DE = crearSubimagen(diaNoche, 10, 5, 32, 32);
        this.BORDE_TIERRA_AR_IZ = crearSubimagen(diaNoche, 7, 5, 32, 32);
        this.ARBOL = crearSubimagen(diaNoche, 12, 3, 32, 48);
        this.AGUA = crearSubimagen(diaNoche, 0, 0, 32, 32);
        this.TARIMA = crearSubimagen(diaNoche, 7, 0, 32, 32);
        this.BLANK = crearSubimagen(diaNoche, 1, 0, 32, 32);
        this.TIERRA = crearSubimagen(diaNoche, 2, 0, 32, 32);
        this.NPC_MUJER = crearSubimagen(diaNoche, 2, 6, 32, 32);
        this.NPC_HOMBRE = crearSubimagen(diaNoche, 1, 6, 32, 32);
        this.PERSONAJE_ABA = new Icon[]{
            crearSubimagen(diaNoche, 0, 9, 32, 32), 
            crearSubimagen(diaNoche, 1, 9, 32, 32), 
            crearSubimagen(diaNoche, 2, 9, 32, 32), 
            crearSubimagen(diaNoche, 1, 9, 32, 32)
        };
        this.PERSONAJE_ARR = new Icon[]{
            crearSubimagen(diaNoche, 6, 9, 32, 32), 
            crearSubimagen(diaNoche, 7, 9, 32, 32), 
            crearSubimagen(diaNoche, 8, 9, 32, 32), 
            crearSubimagen(diaNoche, 7, 9, 32, 32)
        };
        this.PERSONAJE_IZQ = new Icon[]{
            crearSubimagen(diaNoche, 9, 9, 32, 32), 
            crearSubimagen(diaNoche, 10, 9, 32, 32), 
            crearSubimagen(diaNoche, 11, 9, 32, 32), 
            crearSubimagen(diaNoche, 10, 9, 32, 32)
        };
        this.PERSONAJE_DER = new Icon[]{
            crearSubimagen(diaNoche, 4, 9, 32, 32), 
            crearSubimagen(diaNoche, 4, 9, 32, 32), 
            crearSubimagen(diaNoche, 5, 9, 32, 32), 
            crearSubimagen(diaNoche, 4, 9, 32, 32)
        };
        this.HIERBA = new Icon[]{
            crearSubimagen(diaNoche, 3, 0, 32, 32), 
            crearSubimagen(diaNoche, 4, 0, 32, 32), 
            crearSubimagen(diaNoche, 5, 0, 32, 32)};
        this.NUBE = new Icon[]{
            new javax.swing.ImageIcon(getClass().getResource("/img/nube1.png")), 
            new javax.swing.ImageIcon(getClass().getResource("/img/nube2.png")), 
            new javax.swing.ImageIcon(getClass().getResource("/img/nube3.png"))
        };
    }

    /*
    * METODOS VARIOS
    */
    
    private ImageIcon crearSubimagen(boolean dia, int x0, int y0, int ancho, int alto) {
        //Pasamos las coordenadas a pixeles
        x0*=32; 
        y0*=32;
        
        BufferedImage subImagen;
        //Recortamos la imagen que corresponda y la devolvemos
        if(dia){
            subImagen = Parametros.getInstance().getMapaTileset()[0].getSubimage(x0, y0, ancho, alto);
        }else{
            subImagen = Parametros.getInstance().getMapaTileset()[1].getSubimage(x0, y0, ancho, alto);
        }
        return new ImageIcon (subImagen);
    }  
    
    public Icon[][] generarCasa(boolean diaNoche){
        Icon[][] casa = new Icon[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                casa[i][j] = crearSubimagen(diaNoche, j+3,i+5,32,32);
            }
        }
        return casa;
    }
    
    /*
     * GETTERS
     */
    
    public Icon getNPC_HOMBRE() {
        return NPC_HOMBRE;
    }

    public Icon getNPC_MUJER() {
        return NPC_MUJER;
    }

    public Icon getBLANK() {
        return BLANK;
    }

    public Icon getTARIMA() {
        return TARIMA;
    }

    public Icon getAGUA() {
        return AGUA;
    }

    public Icon getARBOL() {
        return ARBOL;
    }
    
    public Icon getTIERRA() {
        return TIERRA;
    }

    public Icon getBORDE_TIERRA_AR_IZ() {
        return BORDE_TIERRA_AR_IZ;
    }

    public Icon getBORDE_TIERRA_AR_DE() {
        return BORDE_TIERRA_AR_DE;
    }

    public Icon getBORDE_TIERRA_AB_IZ() {
        return BORDE_TIERRA_AB_IZ;
    }

    public Icon getBORDE_TIERRA_AB_DE() {
        return BORDE_TIERRA_AB_DE;
    }

    public Icon getBORDE_TIERRA_AR_IZ_ESQUINA() {
        return BORDE_TIERRA_AR_IZ_ESQUINA;
    }

    public Icon getBORDE_TIERRA_AR_DE_ESQUINA() {
        return BORDE_TIERRA_AR_DE_ESQUINA;
    }

    public Icon getBORDE_TIERRA_AB_IZ_ESQUINA() {
        return BORDE_TIERRA_AB_IZ_ESQUINA;
    }

    public Icon getBORDE_TIERRA_AB_DE_ESQUINA() {
        return BORDE_TIERRA_AB_DE_ESQUINA;
    }

    public Icon[][] getCASA() {
        return CASA;
    }
            
    public Icon getHIERBA(){
        return HIERBA[(int)(Math.random()*HIERBA.length)];
    }
    
    public Icon getBORDE_TIERRA_AR(){
        return BORDE_TIERRA_AR[(int)(Math.random()*BORDE_TIERRA_AR.length)];
    }
    
    public Icon getBORDE_TIERRA_AB(){
        return BORDE_TIERRA_AB[(int)(Math.random()*BORDE_TIERRA_AB.length)];
    }
    
    public Icon getBORDE_TIERRA_DE(){
        return BORDE_TIERRA_DE[(int)(Math.random()*BORDE_TIERRA_DE.length)];
    }
    
    public Icon getBORDE_TIERRA_IZ(){
        return BORDE_TIERRA_IZ[(int)(Math.random()*BORDE_TIERRA_IZ.length)];
    }
    
    public Icon getNUBE(){
        return NUBE[(int)(Math.random()*NUBE.length)];
    }

    public Icon getBORDE_AGUA_AR_IZ() {
        return BORDE_AGUA_AR_IZ;
    }

    public Icon getBORDE_AGUA_AR_DE() {
        return BORDE_AGUA_AR_DE;
    }

    public Icon getBORDE_AGUA_AB_IZ() {
        return BORDE_AGUA_AB_IZ;
    }

    public Icon getBORDE_AGUA_AB_DE() {
        return BORDE_AGUA_AB_DE;
    }

    public Icon getBORDE_AGUA_AR_IZ_ESQUINA() {
        return BORDE_AGUA_AR_IZ_ESQUINA;
    }

    public Icon getBORDE_AGUA_AR_DE_ESQUINA() {
        return BORDE_AGUA_AR_DE_ESQUINA;
    }

    public Icon getBORDE_AGUA_AB_IZ_ESQUINA() {
        return BORDE_AGUA_AB_IZ_ESQUINA;
    }

    public Icon getBORDE_AGUA_AB_DE_ESQUINA() {
        return BORDE_AGUA_AB_DE_ESQUINA;
    }
    
    public Icon getBORDE_AGUA_AR(){
        return BORDE_AGUA_AR[(int)(Math.random()*BORDE_AGUA_AR.length)];
    }
    
    public Icon getBORDE_AGUA_AB(){
        return BORDE_AGUA_AB[(int)(Math.random()*BORDE_AGUA_AB.length)];
    }
    
    public Icon getBORDE_AGUA_DE(){
        return BORDE_AGUA_DE[(int)(Math.random()*BORDE_AGUA_DE.length)];
    }
    
    public Icon getBORDE_AGUA_IZ(){
        return BORDE_AGUA_IZ[(int)(Math.random()*BORDE_AGUA_IZ.length)];
    }
    
    public Icon getBORDE_AGUA2_AR_IZ() {
        return BORDE_AGUA2_AR_IZ;
    }

    public Icon getBORDE_AGUA2_AR_DE() {
        return BORDE_AGUA2_AR_DE;
    }

    public Icon getBORDE_AGUA2_AB_IZ() {
        return BORDE_AGUA2_AB_IZ;
    }

    public Icon getBORDE_AGUA2_AB_DE() {
        return BORDE_AGUA2_AB_DE;
    }

    public Icon getBORDE_AGUA2_AR_IZ_ESQUINA() {
        return BORDE_AGUA2_AR_IZ_ESQUINA;
    }

    public Icon getBORDE_AGUA2_AR_DE_ESQUINA() {
        return BORDE_AGUA2_AR_DE_ESQUINA;
    }

    public Icon getBORDE_AGUA2_AB_IZ_ESQUINA() {
        return BORDE_AGUA2_AB_IZ_ESQUINA;
    }

    public Icon getBORDE_AGUA2_AB_DE_ESQUINA() {
        return BORDE_AGUA2_AB_DE_ESQUINA;
    }
    
    public Icon getBORDE_AGUA2_AR(){
        return BORDE_AGUA2_AR[(int)(Math.random()*BORDE_AGUA2_AR.length)];
    }
    
    public Icon getBORDE_AGUA2_AB(){
        return BORDE_AGUA2_AB[(int)(Math.random()*BORDE_AGUA2_AB.length)];
    }
    
    public Icon getBORDE_AGUA2_DE(){
        return BORDE_AGUA2_DE[(int)(Math.random()*BORDE_AGUA2_DE.length)];
    }
    
    public Icon getBORDE_AGUA2_IZ(){
        return BORDE_AGUA2_IZ[(int)(Math.random()*BORDE_AGUA2_IZ.length)];
    }

    public Icon getRAMPA_AB() {
        return RAMPA_AB;
    }

    public Icon getRAMPA_AR() {
        return RAMPA_AR;
    }

    public Icon getRAMPA_DE() {
        return RAMPA_DE;
    }

    public Icon getRAMPA_IZ() {
        return RAMPA_IZ;
    }

    /**
     * @return the ALFOMBRA_AR_IZ
     */
    public Icon getALFOMBRA_AR_IZ() {
        return ALFOMBRA_AR_IZ;
    }

    /**
     * @return the ALFOMBRA_AR_DE
     */
    public Icon getALFOMBRA_AR_DE() {
        return ALFOMBRA_AR_DE;
    }

    /**
     * @return the ALFOMBRA_AB_IZ
     */
    public Icon getALFOMBRA_AB_IZ() {
        return ALFOMBRA_AB_IZ;
    }

    /**
     * @return the ALFOMBRA_AB_DE
     */
    public Icon getALFOMBRA_AB_DE() {
        return ALFOMBRA_AB_DE;
    }

    /**
     * @return the ALFOMBRA_CENTRO
     */
    public Icon getALFOMBRA_CENTRO() {
        return ALFOMBRA_CENTRO;
    }

    /**
     * @return the ALFOMBRA_AR
     */
    public Icon getALFOMBRA_AR() {
        return ALFOMBRA_AR;
    }

    /**
     * @return the ALFOMBRA_AB
     */
    public Icon getALFOMBRA_AB() {
        return ALFOMBRA_AB;
    }

    /**
     * @return the ALFOMBRA_DE
     */
    public Icon getALFOMBRA_DE() {
        return ALFOMBRA_DE;
    }

    /**
     * @return the ALFOMBRA_IZ
     */
    public Icon getALFOMBRA_IZ() {
        return ALFOMBRA_IZ;
    }

    /**
     * @return the SILLA_AB
     */
    public Icon getSILLA_AB() {
        return SILLA_AB;
    }

    /**
     * @return the SILLA_IZ
     */
    public Icon getSILLA_IZ() {
        return SILLA_IZ;
    }

    /**
     * @return the SILLA_AR
     */
    public Icon getSILLA_AR() {
        return SILLA_AR;
    }

    /**
     * @return the SILLA_DE
     */
    public Icon getSILLA_DE() {
        return SILLA_DE;
    }

    /**
     * @return the MESA
     */
    public Icon getMESA() {
        return MESA;
    }

    /**
     * @return the LIBRERIA
     */
    public Icon getLIBRERIA() {
        return LIBRERIA;
    }
    
    /**
     *  INSTANCIA DE LA CLASE
     * 
     * @return 
     */
    
    public static Tileset getInstance() {
        return INSTANCE;
    }
    
    public static void toggleInstance(){
        dia = !dia;
        INSTANCE = new Tileset(dia);
    }
    
    
}
