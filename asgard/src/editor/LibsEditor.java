package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import configuracion.Tileset;
import utils.Constant;

/**
 * Clase con los métodos auxiliares usados por el editor
 *
 * @author ActionPerformed
 */
public class LibsEditor {
    
    private editor.Casilla[][] mapaCasillas;
    private boolean mapaInterior;
    
    /**
     * Metodo para generar un String en lenguaje XML que recoge el contenido de
     * la interfaz gráfica 
     * 
     * @param idMapa ID del mapa creado
     * @param casilla Matriz de casillas que conforma el mapa
     * @return un String en lenguaje XML
     */
    public static String generarXML(int idMapa, editor.Casilla[][] casilla){
        StringBuffer dataBuffer = new StringBuffer();
        Object[] infoCasilla;
        dataBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        dataBuffer.append("<mapa id=\"").append(idMapa).append("\">\n");
        for (int i = 0; i < casilla.length; i++) {
            for (int j = 0; j < casilla[i].length; j++) {
                
                infoCasilla = sacarInfoCasilla(casilla[i][j]);
                
                dataBuffer.append("\t<casilla i=\"").append(i).append("\" j=\"").append(j).append("\" ");
                if ((casilla[i][j].getObjeto().isTransitable() && casilla[i][j].getFondo().isTransitable())
                       || casilla[i][j].getObjeto().getPuerta() != null ) {
                    dataBuffer.append("transitable = \"true\">\n");
                }else{
                    dataBuffer.append("transitable = \"false\">\n");
                }
                
                dataBuffer.append("\t\t<fondo>").append(casilla[i][j].getFondo().getCod()).append("</fondo>\n");
                
                if (infoCasilla[1]!=null) {
                    dataBuffer.append("\t\t<objeto>").append(casilla[i][j].getObjeto().getCod()).append("</objeto>\n");
                }
                
                if (!casilla[i][j].getObjeto().getDescripcion().equals("")) {
                    dataBuffer.append("\t\t<descripcion>").append(casilla[i][j].getObjeto().getDescripcion()).append("</descripcion>\n");
                }
                
                if (casilla[i][j].getObjeto().getPuerta()!=null) {
                    dataBuffer.append("\t\t<puerta>\n");
                    dataBuffer.append("\t\t\t<mapa_destino>").append(casilla[i][j].getObjeto().getPuerta().getMapaDestino()).append("</mapa_destino>\n");
                    dataBuffer.append("\t\t\t<i_destino>").append(casilla[i][j].getObjeto().getPuerta().getCoordIDestino()).append("</i_destino>\n");
                    dataBuffer.append("\t\t\t<j_destino>").append(casilla[i][j].getObjeto().getPuerta().getCoordJDestino()).append("</j_destino>\n");
                    dataBuffer.append("\t\t</puerta>\n");
                }
                dataBuffer.append("\t</casilla>\n");
            }
        }
        dataBuffer.append("</mapa>");
        return dataBuffer.toString();
    }
    
    /**
     * El método recoge una Casilla y extrae su Fondo y su Objeto
     * 
     * @param casilla una casilla del editor
     * @return un object[] formado por {Fondo, Objeto}
     */
    public static Object[] sacarInfoCasilla(editor.Casilla casilla){
        Object[] infoCasilla = new Object[2];
        infoCasilla[0] = casilla.getFondo().getIcon().toString();
        if (casilla.getObjeto().getIcon()!=null) {
            infoCasilla[1] = casilla.getObjeto().getIcon().toString();
        }
        return infoCasilla;
    }
    
    /**
     * Devuelve una matriz de casillas a partir de un archivo XML
     * 
     * @param file
     * @param casilla
     * @return
     */
    public Casilla[][] cargaMapa(File file, editor.Casilla[][] casilla){
        try {
            setMapaCasillas(casilla);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(file.getAbsolutePath(), new SaxParserAsgard());
            return getMapaCasillas();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(LibsEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Devuelve una matriz de casillas a partir de una URL que apunte a un XML
     * 
     * @param url
     * @param casilla
     * @return
     */
    public Casilla[][] cargaMapa(URL url, editor.Casilla[][] casilla){
        try {
            setMapaCasillas(casilla);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(url.getPath(), new SaxParserAsgard());
            return getMapaCasillas();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(LibsEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     /**
     * Añade una linea de texto al archivo indicado por parámetro, usando BufferedWriter
     * 
     * @param file Fichero donde se escribirá la cadena de texto
     * @param cadena cadena de texto a guardar
     * @throws IOException
     */
    public static void anadirLinea(String cadena, File file) throws IOException{
        BufferedWriter flujoDatos = new BufferedWriter(new FileWriter(file,false));
        flujoDatos.write(cadena);
        flujoDatos.close(); //IMPORTANTE cerrar o esto no funciona
     }
 
    /**
     * @return the mapaCasillas
     */
    public editor.Casilla[][] getMapaCasillas() {
        return mapaCasillas;
    }

    /**
     * @param mapaCasillas the mapaCasillas to set
     */
    public void setMapaCasillas(editor.Casilla[][] mapaCasillas) {
        this.mapaCasillas = mapaCasillas;
    }

    public boolean isMapaInterior() {
        return mapaInterior;
    }

    public void setMapaInterior(boolean mapaInterior) {
        this.mapaInterior = mapaInterior;
    }
    
    
    /**
     * Clase que inicia la lectura del XML
     */
    private class SaxParserAsgard extends DefaultHandler {

        private boolean fondo=false;
        private boolean objeto=false;
        private boolean descripcion=false;
        private boolean mapa_destino=false;
        private boolean i_destino=false;
        private boolean j_destino=false;
        
        private int indice_i;
        private int indice_j;
 
        /**
         * Gestiona las ordenes a seguir cuando entramos en una etiqueta xml de apertura
         * 
         * @param uri
         * @param localName
         * @param qName nombre de la etiqueta xml
         * @param atrbts atributos de la etiqueta xml
         * @throws SAXException 
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
            switch (qName){
                case "mapa":
                    //Por ahora no guardamos la id del mapa (TODO)
                    
                    //atributo 1 --> mapa_interior (true/false)
                    if(atrbts.getValue(1)!=null && atrbts.getValue(1).equals("true")){
                        mapaInterior = true;
                    }else{
                        mapaInterior = false;
                    }
                    break;
                case "casilla":
                    //Guardo las coordenadas de la casilla
                    indice_i = Integer.parseInt(atrbts.getValue(0));
                    indice_j = Integer.parseInt(atrbts.getValue(1));
                    //Configuro la transitabilidad
                    if (atrbts.getValue(2).equals("true")) {
                        getMapaCasillas()[indice_i][indice_j].getFondo().setTransitable(true);
                        getMapaCasillas()[indice_i][indice_j].getObjeto().setTransitable(true);
                    }else{
                        getMapaCasillas()[indice_i][indice_j].getFondo().setTransitable(false);
                        getMapaCasillas()[indice_i][indice_j].getObjeto().setTransitable(false);
                    }
                    break;
                case "fondo":
                    setFondo(true);
                    break;
                case "objeto":
                    setObjeto(true);
                    break;
                case "puerta":
                	getMapaCasillas()[indice_i][indice_j].getObjeto().setPuerta(new Objeto.Puerta());
                    break;
                case "descripcion":
                    setDescripcion(true);
                    break;
                case "mapa_destino":
                    setMapa_destino(true);
                    break;   
                case "i_destino":
                    setI_destino(true);
                    break; 
                case "j_destino":
                    setJ_destino(true);
                    break;     
            }
        }

         /**
         * Gestiona las ordenes a seguir cuando entramos en una etiqueta xml de cierre
         * 
         * @param uri
         * @param localName
         * @param qName nombre de la etiqueta xml
         * @param atrbts
         * @throws SAXException 
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName){
                case "fondo":
                    setFondo(false);
                    break;
                case "objeto":
                    setObjeto(false);
                    break;
                case "descripcion":
                    setDescripcion(false);
                    break;
                case "mapa_destino":
                    setMapa_destino(false);
                    break;   
                case "i_destino":
                    setI_destino(false);
                    break; 
                case "j_destino":
                    setJ_destino(false);
                    break; 
            }
        }

        /**
         * Configura los valores de cada elemento
         * 
         * @param chars datos
         * @param start indica el principio de los datos
         * @param length indica la longitud de los datos
         * @throws SAXException 
         */
        @Override
        public void characters(char[] chars, int start, int length) throws SAXException {
            String data = "";
            for (int i = start; i < start + length; i++) {
                data+=chars[i];
            }          
            boolean isDatable = true;
            
            if (isFondo()) {
            	Tile fondoCasilla = getMapaCasillas()[indice_i][indice_j].getFondo();
                switch (data){
                    case Constant.HIERBA:
                    	fondoCasilla.setIcon(Tileset.getInstance().getHIERBA());
                        break;
                    case Constant.AGUA:
                    	fondoCasilla.setIcon(Tileset.getInstance().getAGUA());
                        break;
                    case Constant.TARIMA:
                    	fondoCasilla.setIcon(Tileset.getInstance().getTARIMA());
                        break;
                    case Constant.BLANK:
                    	fondoCasilla.setIcon(Tileset.getInstance().getBLANK());
                        break;
                    case Constant.TIERRA:
                    	fondoCasilla.setIcon(Tileset.getInstance().getTIERRA());
                        break;
                    case Constant.ALFOMBRA_AB:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_AB());
                        break;
                    case Constant.ALFOMBRA_AR:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_AR());
                        break;
                    case Constant.ALFOMBRA_DE:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_DE());
                        break;
                    case Constant.ALFOMBRA_IZ:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_IZ());
                        break;
                    case Constant.ALFOMBRA_AB_DE:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_AB_DE());
                        break;
                    case Constant.ALFOMBRA_AB_IZ:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_AB_IZ());
                        break;
                    case Constant.ALFOMBRA_AR_DE:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_AR_DE());
                        break;
                    case Constant.ALFOMBRA_AR_IZ:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_AR_IZ());
                        break;
                    case Constant.ALFOMBRA_CENTRO:
                    	fondoCasilla.setIcon(Tileset.getInstance().getALFOMBRA_CENTRO());
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "FONDO NO RECONOCIDO ["+indice_i+"]["+indice_j+"]");
                        isDatable = false;
                }
                if(isDatable){
                	fondoCasilla.setCod(data);
                }
            }else if (isObjeto()) {
            	Objeto objetoCasilla = getMapaCasillas()[indice_i][indice_j].getObjeto();
                switch (data){
                    //NPCs
                    case Constant.NPC_HOMBRE:
                        objetoCasilla.setIcon(Tileset.getInstance().getNPC_HOMBRE());
                        break;
                    case Constant.NPC_MUJER:
                        objetoCasilla.setIcon(Tileset.getInstance().getNPC_MUJER());
                        break;
                    //ELEMENTOS DE CORNISA
                    case Constant.BORDE_TIERRA_AB:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AB());
                        break;
                    case Constant.BORDE_TIERRA_AR:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AR());
                        break;
                    case Constant.BORDE_TIERRA_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_DE());
                        break;
                    case Constant.BORDE_TIERRA_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_IZ());
                        break;
                    case Constant.BORDE_TIERRA_AB_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AB_DE());
                        break;
                    case Constant.BORDE_TIERRA_AB_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AB_IZ());
                        break;
                    case Constant.BORDE_TIERRA_AR_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AR_DE());
                        break;
                    case Constant.BORDE_TIERRA_AR_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AR_IZ());
                        break;
                    case Constant.TIERRA_AB_DE_ESQUINA:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AB_DE_ESQUINA());
                        break;
                    case Constant.TIERRA_AB_IZ_ESQUINA:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AB_IZ_ESQUINA());
                        break;
                    case Constant.TIERRA_AR_DE_ESQUINA:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AR_DE_ESQUINA());
                        break;
                    case Constant.TIERRA_AR_IZ_ESQUINA:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_TIERRA_AR_IZ_ESQUINA());
                        break;
                    //BORDE HIERBA    
                    case Constant.BORDE_AGUA_AB:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AB());
                        break;
                    case Constant.BORDE_AGUA_AR:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AR());
                        break;
                    case Constant.BORDE_AGUA_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_DE());
                        break;
                    case Constant.BORDE_AGUA_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_IZ());
                        break;
                    case Constant.BORDE_AGUA_AB_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AB_DE());
                        break;
                    case Constant.BORDE_AGUA_AB_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AB_IZ());
                        break;
                    case Constant.BORDE_AGUA_AR_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AR_DE());
                        break;
                    case Constant.BORDE_AGUA_AR_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AR_IZ());
                        break;
                    case Constant.BORDE_AGUA_AB_DE_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AB_DE_ESQUINA());
                        break;
                    case Constant.BORDE_AGUA_AB_IZ_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AB_IZ_ESQUINA());
                        break;
                    case Constant.BORDE_AGUA_AR_DE_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AR_DE_ESQUINA());
                        break;
                    case Constant.BORDE_AGUA_AR_IZ_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA_AR_IZ_ESQUINA());
                        break;
                    //BORDE HIERBA    
                    case Constant.BORDE_AGUA2_AB:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AB());
                        break;
                    case Constant.BORDE_AGUA2_AR:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AR());
                        break;
                    case Constant.BORDE_AGUA2_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_DE());
                        break;
                    case Constant.BORDE_AGUA2_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_IZ());
                        break;
                    case Constant.BORDE_AGUA2_AB_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AB_DE());
                        break;
                    case Constant.BORDE_AGUA2_AB_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AB_IZ());
                        break;
                    case Constant.BORDE_AGUA2_AR_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AR_DE());
                        break;
                    case Constant.BORDE_AGUA2_AR_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AR_IZ());
                        break;
                    case Constant.BORDE_AGUA2_AB_DE_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AB_DE_ESQUINA());
                        break;
                    case Constant.BORDE_AGUA2_AB_IZ_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AB_IZ_ESQUINA());
                        break;
                    case Constant.BORDE_AGUA2_AR_DE_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AR_DE_ESQUINA());
                        break;
                    case Constant.BORDE_AGUA2_AR_IZ_ESQ:
                        objetoCasilla.setIcon(Tileset.getInstance().getBORDE_AGUA2_AR_IZ_ESQUINA());
                        break;
                    //RAMPAS    
                    case Constant.RAMPA_AR:
                        objetoCasilla.setIcon(Tileset.getInstance().getRAMPA_AR());
                        break;
                    case Constant.RAMPA_AB:
                        objetoCasilla.setIcon(Tileset.getInstance().getRAMPA_AB());
                        break;
                    case Constant.RAMPA_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getRAMPA_DE());
                        break;
                    case Constant.RAMPA_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getRAMPA_IZ());
                        break;    
                    //SILLAS    
                    case Constant.SILLA_AR:
                        objetoCasilla.setIcon(Tileset.getInstance().getSILLA_AR());
                        break;
                    case Constant.SILLA_AB:
                        objetoCasilla.setIcon(Tileset.getInstance().getSILLA_AB());
                        break;
                    case Constant.SILLA_DE:
                        objetoCasilla.setIcon(Tileset.getInstance().getSILLA_DE());
                        break;
                    case Constant.SILLA_IZ:
                        objetoCasilla.setIcon(Tileset.getInstance().getSILLA_IZ());
                        break;    
                    //OTROS    
                    case Constant.BORDE_CASA:
                        for (int k = indice_i; k < indice_i+3; k++) {
                            for (int l = indice_j; l < indice_j+4; l++) {
                                getMapaCasillas()[k][l].getObjeto().setIcon(Tileset.getInstance().getCASA()[k-indice_i][l-indice_j]);  //TODO: NullpointerException
                                getMapaCasillas()[k][l].getObjeto().setTransitable(false);
                            }
                        }
                        break;
                    case Constant.RESTO_CASA:
                    	isDatable = false;
                        break;
                    case Constant.ARBOL:
                        objetoCasilla.setIcon(Tileset.getInstance().getARBOL());
                        break;
                    case Constant.LIBRERIA:
                        objetoCasilla.setIcon(Tileset.getInstance().getLIBRERIA());
                        break;
                    case Constant.MESA:
                        objetoCasilla.setIcon(Tileset.getInstance().getMESA());
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, data+": OBJETO NO RECONOCIDO ["+indice_i+"]["+indice_j+"]");
                        isDatable = false;
                }
                if(isDatable){
                	objetoCasilla.setCod(data);
                }
            }else if (isDescripcion()) {
            	getMapaCasillas()[indice_i][indice_j].getObjeto().setDescripcion(data);
            }else if (isMapa_destino()) {
            	getMapaCasillas()[indice_i][indice_j].getObjeto().getPuerta().setMapaDestino(Integer.parseInt(data));
            }else if (isI_destino()) {
            	getMapaCasillas()[indice_i][indice_j].getObjeto().getPuerta().setCoordIDestino(Integer.parseInt(data));
            }else if (isJ_destino()) {
            	getMapaCasillas()[indice_i][indice_j].getObjeto().getPuerta().setCoordJDestino(Integer.parseInt(data));
            }
        }


        /**
         * @return the fondo
         */
        public boolean isFondo() {
            return fondo;
        }

        /**
         * @param fondo the fondo to set
         */
        public void setFondo(boolean fondo) {
            this.fondo = fondo;
        }

        /**
         * @return the objeto
         */
        public boolean isObjeto() {
            return objeto;
        }

        /**
         * @param objeto the objeto to set
         */
        public void setObjeto(boolean objeto) {
            this.objeto = objeto;
        }

        /**
         * @return the descripcion
         */
        public boolean isDescripcion() {
            return descripcion;
        }

        /**
         * @param descripcion the descripcion to set
         */
        public void setDescripcion(boolean descripcion) {
            this.descripcion = descripcion;
        }


        /**
         * @return the mapa_destino
         */
        public boolean isMapa_destino() {
            return mapa_destino;
        }

        /**
         * @param mapa_destino the mapa_destino to set
         */
        public void setMapa_destino(boolean mapa_destino) {
            this.mapa_destino = mapa_destino;
        }

        /**
         * @return the i_destino
         */
        public boolean isI_destino() {
            return i_destino;
        }

        /**
         * @param i_destino the i_destino to set
         */
        public void setI_destino(boolean i_destino) {
            this.i_destino = i_destino;
        }

        /**
         * @return the j_destino
         */
        public boolean isJ_destino() {
            return j_destino;
        }

        /**
         * @param j_destino the j_destino to set
         */
        public void setJ_destino(boolean j_destino) {
            this.j_destino = j_destino;
        }

    }
}