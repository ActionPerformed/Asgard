package main;

import configuracion.Parametros;
import configuracion.Tileset;
import utils.Constant;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


/**
 * Clase que define al personaje que maneja el jugador
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public final class Personaje extends JLabel implements Runnable{
     
	private static final long serialVersionUID = -5743345877571430783L;
	private final static int PERIODO_PROCESO = Parametros.getInstance().getMILISEGUNDOS_ENTRE_FRAMES();
    private final static int velocidad = 7; //cuanto más alto, mas lenta se moverá la animacion
    
    private Thread hilo;
    private PantallaJuego pantallaJuego;
    
    private boolean finDialogo;
    private boolean hayDialogo;
    
    private int caracterInicial;
    
    private double posX;
    private double posY;
    
    private boolean movDerecha;
    private boolean movIzquierda;
    private boolean movArriba;
    private boolean movAbajo;
    private String ultimoMovimiento = "ABAJO"; //Inicializamos para evitar error si se da a Space al empezar
    
    private int indiceAnimacion;
    
    /**
     * Constructor que genera un nuevo personaje en la pantalla de juego indicada, 
     * en una posicion prefijada mediante el propio codigo del constructor
     * 
     * @param pantallaJuego Contenedor que gestionará la situación del personaje
     * @see PantallaJuego
     */
    public Personaje (PantallaJuego pantallaJuego){
        this.pantallaJuego = pantallaJuego;
        this.indiceAnimacion = 0;
        this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_ABA()[indiceAnimacion]);
        this.posX = 200;
        this.posY = 350;
        setBounds((int)posX,(int)posY,32,32);  
        this.caracterInicial = 0;
        
        this.hilo = new Thread(this);
    }
    
    /**
     * Gestiona los diferentes eventos de tecla que hacen reaccionar al personaje
     * 
     * @param evt 
     */
    public void movimientoPJ(java.awt.event.KeyEvent evt){
        int keyCode = evt.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_RIGHT:
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_DER()[indiceAnimacion/velocidad]);
                movDerecha=true && !hayDialogo;
                movIzquierda=false;
                movArriba=false;
                movAbajo=false;
                break;
            case KeyEvent.VK_LEFT:
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_IZQ()[indiceAnimacion/velocidad]);
                movDerecha=false;
                movIzquierda=true && !hayDialogo;
                movArriba=false;
                movAbajo=false;
                break;
            case KeyEvent.VK_UP:
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_ARR()[indiceAnimacion/velocidad]);
                movDerecha=false;
                movIzquierda=false;
                movArriba=true && !hayDialogo;
                movAbajo=false;
                break;
            case KeyEvent.VK_DOWN:
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_ABA()[indiceAnimacion/velocidad]);
                movDerecha=false;
                movIzquierda=false;
                movArriba=false;
                movAbajo=true && !hayDialogo;
                break;
            case KeyEvent.VK_SPACE:
                if (finDialogo) {
                    cerrarTexto();
                    hayDialogo = false;
                }else{
                    mostrarTexto(caracterInicial);
                    
                }
                break;
         }
    }
    
    /**
     * Gestiona la parada del personaje
     *
     * @param evt
     */
    public void pararPJ(java.awt.event.KeyEvent evt){
        int keyCode = evt.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_RIGHT:
                movDerecha=false;
                ultimoMovimiento = Constant.DERECHA;
                break;
            case KeyEvent.VK_LEFT:
                movIzquierda=false;
                ultimoMovimiento = Constant.IZQUIERDA;
                break;
            case KeyEvent.VK_UP:
                movArriba=false;
                ultimoMovimiento = Constant.ARRIBA;
                break;
            case KeyEvent.VK_DOWN:
                movAbajo=false;
                ultimoMovimiento = Constant.ABAJO;
                break;
         }
    }

    /**
     * Hilo de ejecución del personaje
     */
    @Override
    public void run() {
        int coordI;
        int coordJ;
        PantallaJuego.getCapaPantalla().add(this, new Integer(1));
        
        Date date;
        Calendar calendar = GregorianCalendar.getInstance();
        
        boolean juegoFuncionando = true;
        while (juegoFuncionando) {
            //Comprobamos el ciclo dia/noche
            date = new Date();
            calendar.setTime(date);
            comprobarHora(calendar);
            
            //Control de movimientos
            if (movDerecha && pantallaJuego.esCaminable((int)getLocation().getX(),(int)getLocation().getY(),2,0)) {
                posX+=2;
                indiceAnimacion = (indiceAnimacion+1)%(4*velocidad);
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_DER()[indiceAnimacion/velocidad]);
            }
            if (movIzquierda && pantallaJuego.esCaminable((int)getLocation().getX(),(int)getLocation().getY(),-2,0)) {
                posX-=2;
                indiceAnimacion = (indiceAnimacion+1)%(4*velocidad);
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_IZQ()[indiceAnimacion/velocidad]);
            }
            if (movArriba && pantallaJuego.esCaminable((int)getLocation().getX(),(int)getLocation().getY(),0,-2)) {
                posY-=2;
                PantallaJuego.getCapaPantalla().setLayer(this, (2*(((int)posY+32)/32))+1);
                indiceAnimacion = (indiceAnimacion+1)%(4*velocidad);
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_ARR()[indiceAnimacion/velocidad]);
            }
            if (movAbajo && pantallaJuego.esCaminable((int)getLocation().getX(),(int)getLocation().getY(),0,2)) {
                posY+=2;
                PantallaJuego.getCapaPantalla().setLayer(this, (2*(((int)posY+32)/32))+1);
                indiceAnimacion = (indiceAnimacion+1)%(4*velocidad);
                this.setIcon(configuracion.Tileset.getInstance().getPERSONAJE_ABA()[indiceAnimacion/velocidad]);
            }
            
            //Impedimos que el personaje se salga de los bordes
            posX = Math.max(0, posX);
            posX = Math.min(640, posX);
            posY = Math.max(-16, posY); //Necesario para que las puertas norte funcionen
            posY = Math.min(480, posY);
            
            //Calculamos las coordenadas de la rejilla
            coordI=((int)posY+32)/32;
            coordJ=((int)posX+16)/32;
            
            //Si hay puerta en su nueva casilla, lo cambiamos de mapa
            //Comprobamos la hora tambien, por si hay que cambiar colores
            if (pantallaJuego.getCasillaMapa()[coordI][coordJ].getPuerta() != null) {
                pantallaJuego.getCasillaMapa()[coordI][coordJ].getPuerta().traspasar(this);
            }
            
            //Fijamos la nueva localizacion del personaje
            setLocation(new Point((int)posX, (int)posY));  
            
            //Dejamos un tiempo de refresco entre proceso y proceso
            try {
                 Thread.sleep(PERIODO_PROCESO);
            } catch (InterruptedException ex) {
                Logger.getLogger(Personaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Método que muestra un diálogo en pantalla, recogiendo el texto del .properties
     * y comenzando su lectura en el caracter indicado por parametro
     * 
     * @param caracterInicial Caracter a partir del cual se empezará a mostrar el texto
     */
    public void mostrarTexto(int caracterInicial){
        int coordI=((int)posY+32)/32;
        int coordJ=((int)posX+16)/32;
        switch (ultimoMovimiento){ //Aqui a veces salta exception de forma aleatoria
            case Constant.ARRIBA:
                if (!pantallaJuego.getCasillaMapa()[coordI-1][coordJ].getDescripcion().equals("")) {
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, pantallaJuego.getCasillaMapa()[coordI-1][coordJ].getDescripcion());
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                }else if(pantallaJuego.getCasillaMapa()[coordI-1][coordJ].getFondoCod().equals("agua")){
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, "agua");
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                    new Emisor("Agua");
                }
                break;
            case Constant.ABAJO:
                if (!pantallaJuego.getCasillaMapa()[coordI+1][coordJ].getDescripcion().equals("")) {
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, pantallaJuego.getCasillaMapa()[coordI+1][coordJ].getDescripcion());
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                }else if(pantallaJuego.getCasillaMapa()[coordI+1][coordJ].getFondoCod().equals("agua")){
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, "agua");
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                    new Emisor("Agua");
                }
                break;
            case Constant.DERECHA:
                if (!pantallaJuego.getCasillaMapa()[coordI][coordJ+1].getDescripcion().equals("")) {
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, pantallaJuego.getCasillaMapa()[coordI][coordJ+1].getDescripcion());
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                }else if(pantallaJuego.getCasillaMapa()[coordI][coordJ+1].getFondoCod().equals("agua")){
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, "agua");
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                    new Emisor("Agua");
                }
                break;
            case Constant.IZQUIERDA:
                if (!pantallaJuego.getCasillaMapa()[coordI][coordJ-1].getDescripcion().equals("")) {
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, pantallaJuego.getCasillaMapa()[coordI][coordJ-1].getDescripcion());
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                }else if(pantallaJuego.getCasillaMapa()[coordI][coordJ-1].getFondoCod().equals("agua")){
                    hayDialogo = true;
                    this.caracterInicial += PanelJuego.getPanelDialogo().escribeDialogo(caracterInicial, "agua");
                    if (this.caracterInicial <= 0) {
                        finDialogo = true;
                    }
                    new Emisor("Agua");
                }
                break;
        } 
        this.caracterInicial = Math.max(this.caracterInicial, 0);
    }
    
    /**
     * Metodo que oculta el cuadro de diálogo
     */
    public void cerrarTexto(){
        PanelJuego.getPanelDialogo().setVisible(false);
        finDialogo = false;
        this.pantallaJuego.repaint();
    }
    
    /**
     * Método que gestiona el ciclo dia/noche del juego en función de la hora que recibe
     * como parámetro 
     * @param calendar 
     */
    public void comprobarHora(Calendar calendar){
        boolean dia;
        if (this.pantallaJuego.isMapaInterior()) {
            dia=true;
        }else{
            dia = calendar.get(Calendar.HOUR_OF_DAY) >= 8 && calendar.get(Calendar.HOUR_OF_DAY) < 20;
        }
        
        if(dia != Tileset.dia){
            
            Tileset.toggleInstance();
            
            int residuoX = ((int)posX)%32;
            int residuoY = ((int)posY)%32;
            
            Puerta cambioHora = new Puerta();
            cambioHora.setPanelJuego(this.pantallaJuego.getPanelJuego());
            cambioHora.setiDestino(((int)posY)/32);
            cambioHora.setjDestino(((int)posX)/32);
            cambioHora.setIdPantallaDestino(this.pantallaJuego.getId());
            cambioHora.traspasar(this);
            
            this.posX += residuoX;
            this.posY += residuoY;            
        }
    }

    /**
     * Devuelve el hilo del personaje
     * @return <code>Thread</code> del personaje
     */
    public Thread getHilo() {
        return hilo;
    }

    /**
     * Devuelve el <code>PantallaJuego</code> donde se sitúa el personaje
     * @return <code>PantallaJuego</code> donde se sitúa el personaje
     * @see PantallaJuego
     */
    public PantallaJuego getPantallaJuego() {
        return pantallaJuego;
    }

    /**
     * Fija el <code>PantallaJuego</code> donde se sitúa el personaje
     * @param pantallaJuego <code>PantallaJuego</code> donde se sitúa el personaje
     * @see PantallaJuego
     */
    public void setPantallaJuego(PantallaJuego pantallaJuego) {
        this.pantallaJuego = pantallaJuego;
    }

    /**
     * Fija el fin o no fin del diálogo del personaje
     * @param finDialogo 
     * <ul>
     *  <li><code>True</code>: Si el dialogo debe terminar</li>
     *  <li><code>False</code>: Si el diálogo aún continúa</li>
     * </ul>
     */
    public void setFinDialogo(boolean finDialogo) {
        this.finDialogo = finDialogo;
    }

    /**
     * Indica si el diálogo del personaje se debe mostrar o no
     * @param hayDialogo 
     * <ul>
     *  <li><code>True</code>: Si el dialogo debe terminar</li>
     *  <li><code>False</code>: Si el diálogo aún continúa</li>
     * </ul>
     */
    public void setHayDialogo(boolean hayDialogo) {
        this.hayDialogo = hayDialogo;
    }

    /**
     * Devuelve la coordenada X del personaje (en pixeles)
     * @return Coordenada X del personaje (en pixeles)
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Fija la coordenada X del personaje (en pixeles)
     * @param posX Coordenada X del personaje (en pixeles)
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * Devuelve la coordenada Y del personaje (en pixeles)
     * @return Coordenada Y del personaje (en pixeles)
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Fija la coordenada Y del personaje (en pixeles)
     * @param posY Coordenada Y del personaje (en pixeles)
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Devuelve una cadena diferente en funcion del último movimiento realizado
     * por el personaje
     * @return
     * <ul>
     *  <li><code>Constant.ARRIBA</code>: Si el último movimiento ha sido hacia arriba</li>
     *  <li><code>Constant.ABAJO</code>: Si el último movimiento ha sido hacia abajo</li>
     *  <li><code>Constant.IZQUIERDA</code>: Si el último movimiento ha sido hacia la izquierda</li>
     *  <li><code>Constant.DERECHA</code>: Si el último movimiento ha sido hacia la derecha</li>
     * </ul>
     */
    public String getUltimoMovimiento() {
        return ultimoMovimiento;
    }
}
