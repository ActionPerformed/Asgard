package main;

import configuracion.Parametros;
import java.awt.Color;
import java.io.File;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Contenedor <code>JPanel</code> que contiene el conjunto de casillas a mostrar, 
 * así como un <code>JLayeredPane</code> sobrepuesto en el cual se desplegarán 
 * dichas casillas.
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public final class PantallaJuego extends javax.swing.JPanel {

    private static final int numTilesHorizontal = Parametros.getInstance().getANCHO_PANTALLA_JUEGO()/32;
    private static final int numTilesVertical = Parametros.getInstance().getALTO_PANTALLA_JUEGO()/32;
    private static final int PERIODO_PROCESO = Parametros.getInstance().getMILISEGUNDOS_ENTRE_FRAMES();

    /**
     * @return the capaPantalla
     */
    public static JLayeredPane getCapaPantalla() {
        return capaPantalla;
    }

    /**
     * @return the panelNegro
     */
    public static JPanel getPanelNegro() {
        return panelNegro;
    }
      
    private CasillaMapa[][] casillaMapa;
    private final PanelJuego panelJuego;
    
    private final int id;
    private boolean mapaInterior;  
    
    private final File urlDatosMapa;

    private static JLayeredPane capaPantalla;
    private static JPanel panelNegro;
    
    /**
     * Constructor de <code>PantallaJuego</code>
     * 
     * @param id codigo del mapa a cargar
     * @param panelJuego Contenedor donde se agregará el panel actual
     * 
     * @see PanelJuego
     */
    public PantallaJuego(int id, PanelJuego panelJuego){
        initComponents();  
        this.panelJuego = panelJuego;
        this.id = id;
        urlDatosMapa = new File("src/data/"+this.id+".asg");  
        initMoreComponents();
    }
        
    /**
     * Configura el elemento <code>PntallaJuego</code>
     */
    public void initMoreComponents(){
        this.setVisible(true);
        //tileset = new Tileset();
        
        capaPantalla = new JLayeredPane();
        capaPantalla.setBounds(
                0,
                0,
                Parametros.getInstance().getANCHO_PANTALLA_JUEGO(),
                Parametros.getInstance().getALTO_PANTALLA_JUEGO()
            );
        add(capaPantalla);
        
        //Pantalla negra para los cambios de mapa
        panelNegro = new JPanel();
        panelNegro.setBounds(
                0,
                0,
                Parametros.getInstance().getANCHO_PANTALLA_JUEGO(),
                Parametros.getInstance().getALTO_PANTALLA_JUEGO()
            );
        panelNegro.setBackground(Color.BLACK);
        panelNegro.setVisible(false);
        capaPantalla.add(panelNegro,new Integer(150));
        
        //Inicializamos un mapa de casillas del editor
        editor.LibsEditor eLib =new editor.LibsEditor();
        editor.Casilla[][] casillaEditor = new editor.Casilla[numTilesVertical][numTilesHorizontal];
        
        for (int i = 0; i < numTilesVertical; i++) {
            for (int j = 0; j < numTilesHorizontal; j++) {
                try{
                    casillaEditor[i][j] = new editor.Casilla();
                    casillaEditor[i][j].setFondo(new editor.Tile());
                    casillaEditor[i][j].setObjeto(new editor.Objeto());
                    casillaEditor[i][j].getObjeto().setPuerta(new editor.Objeto.Puerta());
                }catch(ClassCastException cce){
                    //Exception in thread "Thread-5" java.lang.ClassCastException: 
                    //javax.swing.plaf.FontUIResource cannot be cast to java.awt.Color
                }
            }
        }
        
        //parseamos los datos con la herramienta del editor sobre el mapa de casillas de editor
        casillaEditor = eLib.cargaMapa(urlDatosMapa,casillaEditor);
         
        //inicializamos un mapa de casillas del juego
        casillaMapa = new CasillaMapa[numTilesVertical][numTilesHorizontal];
        
        //traducimos los datos leidos, del editor al juego
        mapaInterior = eLib.isMapaInterior();
        
        //En este for hay problemas cuando hago el JAR (no muestra la pantalla)
        //NullPointerException, posiblemente no me cargue el mapa desde la url en cargaMapa
        try{
            for (int i = 0; i < numTilesVertical; i++) {
                for (int j = 0; j < numTilesHorizontal; j++) {
                    //instancio la casilla y le paso los valores
                    casillaMapa[i][j] = new CasillaMapa(casillaEditor[i][j]);
                    colocaCasilla(i,j);
                }
            }
        }catch(NullPointerException npe){
            JOptionPane.showMessageDialog(this, "Error cargando "+urlDatosMapa);
        }
    }
    
    /**
     * Sitúa el elemento <code>CasillaMapa[i][j]</code> sobre <code>capaPantalla</code>
     * 
     * @param i
     * @param j 
     */
    public void colocaCasilla(int i, int j){
        //configuro el fondo y lo añado al layer
        casillaMapa[i][j].getFondo().setBounds(32*j, 32*i, 32, 32);
        capaPantalla.add(casillaMapa[i][j].getFondo(),new Integer(0));
        //configuro el objeto y lo añado al layer
        if (casillaMapa[i][j].getObjetoCod()!=null && 
                (casillaMapa[i][j].getObjetoCod().equals("arbol") || casillaMapa[i][j].getObjetoCod().equals("libreria"))) {
            casillaMapa[i][j].getObjeto().setBounds(32*j, 32*i-16, 32, 48);
            capaPantalla.add(casillaMapa[i][j].getObjeto(),new Integer(2*(i+1)));
        }else if (casillaMapa[i][j].getObjetoCod()!=null && 
                (casillaMapa[i][j].getObjetoCod().startsWith("rampa") ||
                casillaMapa[i][j].getObjetoCod().startsWith("borde_agua"))){
            casillaMapa[i][j].getObjeto().setBounds(32*j, 32*i, 32, 32);
            capaPantalla.add(casillaMapa[i][j].getObjeto(),new Integer(1));

        }else if (casillaMapa[i][j].getObjetoCod()!=null && 
                (casillaMapa[i][j].getObjetoCod().startsWith("npc_"))){
            casillaMapa[i][j].getObjeto().setBounds(32*j, 32*i-16, 32, 32);
            capaPantalla.add(casillaMapa[i][j].getObjeto(),new Integer(2*(i+1)));
        }else{
            casillaMapa[i][j].getObjeto().setBounds(32*j, 32*i, 32, 32);
            capaPantalla.add(casillaMapa[i][j].getObjeto(),new Integer(2*(i+1)));
        }
        //configuro la puerta
        if (casillaMapa[i][j].getPuerta() != null) {
            casillaMapa[i][j].getPuerta().setPanelJuego(panelJuego);
        }
    }
     
    /**
     * Determina si, dada una posición, la nueva posición incrementada sigue siendo
     * transitable 
     * @param posX posicion X inicial (en pixeles)
     * @param posY posicion Y inicial (en pixeles)
     * @param incrX incremento de posicion X (en pixeles)
     * @param incrY incremento de posicion Y (en pixeles)
     * @return 
     * <ul>
     *  <li><code>True</code>: Si la nueva casilla es transitable</li>
     *  <li><code>False</code>: Si la nueva casilla no es transitable</li>
     * </ul>
     */
    public boolean esCaminable(int posX, int posY, int incrX, int incrY){
        int i1,j1,i2,j2,i3,j3,i4,j4;
        
        j1 = (posX+incrX)/32;
        i1 = (posY+incrY+32)/32;
        
        j2 = (posX+incrX+24)/32;
        i2 = (posY+incrY+32)/32;
        
        j3 = (posX+incrX)/32;
        i3 = (posY+incrY+24)/32;
        
        j4 = (posX+incrX+24)/32;
        i4 = (posY+incrY+24)/32;
        
        //Exception in thread "Thread-3" java.lang.ArrayIndexOutOfBoundsException: 20
        //Sucede al salir el pj fuera de la cuadricula, a veces
        try{
            return casillaMapa[i1][j1].isTransitable() && casillaMapa[i2][j2].isTransitable()
                && casillaMapa[i3][j3].isTransitable() && casillaMapa[i4][j4].isTransitable();
        }catch(java.lang.ArrayIndexOutOfBoundsException oobe){
            return false;
        }
    }

    /**
     * Devuelve un elemento <code>CasillaMapa[][]</code>
     * @return Elemento <code>CasillaMapa[][]</code>
     * @see CasillaMapa
     */
    public CasillaMapa[][] getCasillaMapa() {
        return casillaMapa;
    }

    /**
     * Devuelve un elemento <code>PanelJuego</code>
     * @return Elemento <code>PanelJuego</code>
     * @see PanelJuego
     */
    public PanelJuego getPanelJuego() {
        return panelJuego;
    }

    /**
     * Devuelve el codigo del mapa
     * @return código del mapa
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve un <code>boolean</code> que indica si es un mapa de interior 
     * (sin efectos atmosféricos) o no
     * @return 
     * <ul>
     *  <li><code>True</code>: Si es un mapa de interior</li>
     *  <li><code>False</code>: Si es un mapa de exterior</li>
     * </ul>
     */
    public boolean isMapaInterior() {
        return mapaInterior;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));
        setMaximumSize(new java.awt.Dimension(640, 480));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
