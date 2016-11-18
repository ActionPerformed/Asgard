package main;

import configuracion.Parametros;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * <code>JPanel</code> que muestra los diferentes diálogos que mantiene el personaje
 * al interactuar con los diversos objetos y NPCs.
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
final class PanelDialogo extends JPanel{
    
    private static final int CARACTERES_POR_DIALOGO = 45;
    private JTextArea taDialogo = new JTextArea();

    /**
     * Constructor de <code>PanelDialogo</code>. 
     */
    public PanelDialogo(){
        try {
            this.setBounds(
                    0,
                    PantallaJuego.getCapaPantalla().getY()+PantallaJuego.getCapaPantalla().getHeight()-120,
                    PantallaJuego.getCapaPantalla().getWidth(),
                    120);
            this.setOpaque(false);
            this.setVisible(false);

            taDialogo.setVisible(true);
            
            taDialogo.setSize(this.getWidth()-40,this.getHeight()-32);
            taDialogo.setLocation(20,PantallaJuego.getCapaPantalla().getY()+PantallaJuego.getCapaPantalla().getHeight()-104);
            taDialogo.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            InputStream is = getClass().getResourceAsStream(Parametros.getInstance().getFONT_PIXEL());
            Font fuenteDialogo = Font.createFont(Font.TRUETYPE_FONT, is);
            taDialogo.setFont(fuenteDialogo.deriveFont(Font.BOLD,30.0f));

            taDialogo.setLineWrap(true);
            taDialogo.setEditable(false);
            taDialogo.setVisible(false);

        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(PanelJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setVisible(boolean esVisible){
        super.setVisible(esVisible);
        taDialogo.setVisible(esVisible);
        if (esVisible) {
            PantallaJuego.getCapaPantalla().add(this,new Integer(100));
            PantallaJuego.getCapaPantalla().add(taDialogo,new Integer(101));
        }else{
            PantallaJuego.getCapaPantalla().remove(this);
            PantallaJuego.getCapaPantalla().remove(taDialogo);
        }
    }

    /**
     * Dibuja el fondo sobre el que se situarán los diálogos
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(this.getClass().getResource("/img/panel_dialogo.png")).getImage(), 0, 0, null);
    }

    /**
     * Muestra por diálogo un trozo del texto, ajustándolo a ésta
     * 
     * @param caracterInicial Caracter inicial de donde empezaremos a escribir
     * @param id_dialogo Código del diálogo a mostrar
     * @return Carácter final donde se cortará el texto obtenido a partir de <code>id_dialogo</code>
     */
    public int escribeDialogo (int caracterInicial, String id_dialogo){
        String dialogo;
        try{
            dialogo = ResourceBundle.getBundle("localizacion.Menus", configuracion.Parametros.getInstance().getLocalizacion()).getString(id_dialogo);
        }catch(java.util.MissingResourceException mre){
            dialogo = "-- ERROR EN EL DIALOGO (Missing resource)--";
        }catch(java.lang.StringIndexOutOfBoundsException sioobe){ //Sucede al leer un interactuar con un segundo dialogo sin haber terminado el primero
            dialogo = "-- ERROR EN EL DIALOGO (String Out of bounds)--";
        }
        
        this.setVisible(true);
        
        //LOGRO DE SARKIS (TODO: Mover esto a un sitio mas elegante)
        if(id_dialogo.equals("npc102001") && caracterInicial==0){
            new Emisor("Logro3");
        }
        
//        int numCaracteresPrimeraLinea = divideDialogo(caracterInicial,dialogo,true);
//        return divideDialogo(numCaracteresPrimeraLinea + caracterInicial,dialogo,false);
        
        int indice1 = procesarCadena(dialogo, caracterInicial, true);
        int indice2 = procesarCadena(dialogo, caracterInicial+indice1, false);
        return indice1+indice2;
    }
    
    public int procesarCadena(String cadena, int caracterInicial, boolean primeraLinea){
        String subcadena = "";
        try{
            subcadena = cadena.substring(caracterInicial,caracterInicial+CARACTERES_POR_DIALOGO);
           int indiceUltimoEspacio = subcadena.lastIndexOf(" ");
           subcadena = subcadena.substring(0,indiceUltimoEspacio).trim();
           if(primeraLinea){
               taDialogo.setText(subcadena);
           }else{
               taDialogo.setText(taDialogo.getText()+"\n"+subcadena);
           }
           return indiceUltimoEspacio;
        }catch(StringIndexOutOfBoundsException sioobe){
            try{
                subcadena = cadena.substring(caracterInicial).trim();
            }catch(StringIndexOutOfBoundsException sioobe2){
                subcadena = "";
            }    
            if(primeraLinea){
               taDialogo.setText(subcadena);
           }else{
               taDialogo.setText(taDialogo.getText()+"\n"+subcadena);
           }
            return -10000;
        }
    }
    
    /**
     * Muestra por diálogo el texto del lanzamiento que se ha recibido 
     * desde el dispositivo móvil
     * 
     * @param distanciaLanzamiento Distancia del lanzamiento realizado
     * @param pesoPez Peso del pez pescado
     * @return posición donde se corta el diálogo en caso de no entrar en pantalla
     */
    public int escribeLanzamiento(String distanciaLanzamiento, String pesoPez){
        String dialogo = ResourceBundle.getBundle("localizacion.Menus", configuracion.Parametros.getInstance().getLocalizacion()).getString("lanzamiento_inicio");
        dialogo+=distanciaLanzamiento;
        dialogo += ResourceBundle.getBundle("localizacion.Menus", configuracion.Parametros.getInstance().getLocalizacion()).getString("lanzamiento_fin");
        
        if(!pesoPez.equals("0.0")){
            dialogo += ResourceBundle.getBundle("localizacion.Menus", configuracion.Parametros.getInstance().getLocalizacion()).getString("pez_inicio");
            dialogo+=pesoPez;
            dialogo += ResourceBundle.getBundle("localizacion.Menus", configuracion.Parametros.getInstance().getLocalizacion()).getString("pez_fin");
        }
        
        this.setVisible(true);
        
        int numCaracteresPrimeraLinea = procesarCadena(dialogo,0,true);
        return procesarCadena(dialogo,numCaracteresPrimeraLinea,false);
    }
    
    /**
     * Divide un texto en trozos para su mejor visión en pantalla. 
     * A partir del <code>caracterInicial</code> indicado, tomará tantas palabras
     * enteras como pueda sin sobrepasar la cantidad indicada por
     * <code>CARACTERES_POR_DIALOGO</code> y la mostrará en pantalla para psoteriormente devolver 
     * la nueva posición de corte del diálogo.
     * 
     * @param caracterInicial Posicion del texto donde empezamos a leer
     * @param dialogo Texto a procesar
     * @param esPrimeraLinea 
     * <ul>
     *  <li><code>True</code>: si es la primera línea del cuadro de diálogos.</li>
     *  <li><code>False</code>: si es la segunda línea del cuadro de diálogos.</li>
     * </ul>
     * @return Indice del carácter final mostrado
     */
    @Deprecated
    public int divideDialogo(int caracterInicial, String dialogo, boolean esPrimeraLinea){
        try{
            dialogo = dialogo.substring(caracterInicial);
            int indiceDeCorte = dialogo.lastIndexOf(" ",CARACTERES_POR_DIALOGO);

            if(esPrimeraLinea){
                if (CARACTERES_POR_DIALOGO > dialogo.substring(0,dialogo.length()).trim().length()) {
                    taDialogo.setText(dialogo.trim());
                    return -10000;
                }else{
                    taDialogo.setText(dialogo.substring(0,indiceDeCorte).trim());
                    return indiceDeCorte;
                }
            }else{
                if (CARACTERES_POR_DIALOGO > dialogo.substring(0,dialogo.length()).trim().length()) {
                    taDialogo.setText(taDialogo.getText()+"\n"+dialogo.trim());
                    return -10000;
                }else{
                    taDialogo.setText(taDialogo.getText()+"\n"+dialogo.substring(0,indiceDeCorte).trim());
                    return indiceDeCorte;
                }
            }
        }catch(java.lang.StringIndexOutOfBoundsException sioobe){ //Sucede al leer un interactuar con un segundo dialogo sin haber terminado el primero
            taDialogo.setText(dialogo.trim());
            return -10000;
        }
    }
}
