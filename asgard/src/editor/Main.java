package editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileNameExtensionFilter;

import configuracion.Parametros;
import utils.Constant;

/**
 * Frame principal del editor de niveles
 *
 * @author ActionPerformed
 */
public final class Main extends javax.swing.JFrame {

    private final int NUM_VERTICALES = Parametros.getInstance().getALTO_PANTALLA_JUEGO()/32;
    private final int NUM_HORIZONTALES = Parametros.getInstance().getANCHO_PANTALLA_JUEGO()/32;

    //private Tileset tileset;
    private Casilla[][] casilla;
   
    private Casilla casillaConFocus;
    private JLayeredPane layeredPane;
    
    private ArrayList<JLabel> tileSetFondo;
    private ArrayList<JButton> tileSetObjeto;
    
    private MouseListener listener;
    private TransferHandler thTile;
    private TransferHandler thObjeto;
    
    JFileChooser eligeFichero;
    
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        initMoreComponents();
    }

    public void initMoreComponents(){
        setLocationRelativeTo(null); //centro el frame en la pantalla
        
        eligeFichero = new JFileChooser("."); //Configuro el dialogo de guardar y cargar
        
        //setTileset(new Tileset());
        setListener(new DragMouseAdapter());
        
        setLayeredPane(new JLayeredPane());
        panelCasillas.add(getLayeredPane());
        getLayeredPane().setBounds(0,0,panelCasillas.getBounds().width, panelCasillas.getBounds().height);
        
        setCasilla(new Casilla[getNUM_VERTICALES()][getNUM_HORIZONTALES()]);
        
        rbFondo.setSelected(true);
        tfCoordI.setEnabled(false);
        tfCoordJ.setEnabled(false);
        tfMapaDestino.setEnabled(false);
        panelPropiedades.setVisible(false);
        
        //Configuro los transferHandler de los Tile y Objeto
        setThTile(new TransferHandler("tile"));
        setThObjeto(new TransferHandler("objeto"));
        
        generarMapaInicial();
        generarTileSet();
        
        setCasillaConFocus(getCasilla()[0][0]);
        
        rbObjetos.addActionListener(e -> mostrarSoloObjetos());
        rbFondo.addActionListener(e -> mostrarSoloFondo());
        cbEsPuerta.addActionListener(e -> {
            if (cbEsPuerta.isSelected()) {
                tfCoordI.setEnabled(true);
                tfCoordJ.setEnabled(true);
                tfMapaDestino.setEnabled(true);
                tfMapaDestino.requestFocus();
            }else{
                tfCoordI.setEnabled(false);
                tfCoordJ.setEnabled(false);
                tfMapaDestino.setEnabled(false);
            }
        });
    }
    
    /**
     * Muestra en pantalla SOLO el fondo de cada casilla, para su edicion.
     */
    public void mostrarSoloFondo(){
        for (int i = 0; i < getNUM_VERTICALES(); i++) {
            for (int j = 0; j < getNUM_HORIZONTALES(); j++) {
                getCasilla()[i][j].getObjeto().setVisible(false);
                getCasilla()[i][j].getFondo().setVisible(true);
            }
        }
        for (int i = 0; i < getTileSetObjeto().size(); i++) {
            getTileSetObjeto().get(i).setVisible(false);
        }
        for (int i = 0; i < getTileSetFondo().size(); i++) {
            getTileSetFondo().get(i).setVisible(true);
        }
        panelPropiedades.setVisible(false);
    }
    
    /**
     * Muestra en pantalla los objetos que hay sobre cada casilla, para su edicion. 
     * Además, dibuja las casas completamente a partir de su esquina superior izquierda
     */
    public void mostrarSoloObjetos(){
        for (int i = 0; i < getNUM_VERTICALES(); i++) {
            for (int j = 0; j < getNUM_HORIZONTALES(); j++) {
                getCasilla()[i][j].getFondo().setVisible(true);
                getCasilla()[i][j].getObjeto().setVisible(true);
                if (getCasilla()[i][j].getObjeto().getCod()!=null && getCasilla()[i][j].getObjeto().getCod().equals(Constant.BORDE_CASA)) {
                    for (int k = i; k < i+3; k++) {
                        for (int l = j; l < j+4; l++) {
                            getCasilla()[k][l].getObjeto().setIcon(configuracion.Tileset.getInstance().getCASA()[k-i][l-j]);
                            getCasilla()[k][l].getObjeto().setCod(Constant.RESTO_CASA);
                            if (getCasilla()[k][l].getObjeto().getPuerta() == null) {
                                getCasilla()[k][l].getObjeto().setTransitable(false);
                            }else{
                                getCasilla()[k][l].getObjeto().setTransitable(true);
                                getCasilla()[k][l].getFondo().setTransitable(true);
                            }
                        }
                    }
                    getCasilla()[i][j].getObjeto().setCod(Constant.BORDE_CASA);
                }
            }
        }
        for (int i = 0; i < getTileSetObjeto().size(); i++) {
            getTileSetObjeto().get(i).setVisible(true);
        }
        for (int i = 0; i < getTileSetFondo().size(); i++) {
            getTileSetFondo().get(i).setVisible(false);
        }
        panelPropiedades.setVisible(true);
    }
    
    public void generarMapaInicial(){
        getLayeredPane().removeAll();
        for (int i = 0; i < getNUM_VERTICALES(); i++) {
            for (int j = 0; j < getNUM_HORIZONTALES(); j++) {
                
                getCasilla()[i][j] = new Casilla(new ImageIcon("src/img/empty.png"));
                getLayeredPane().add(getCasilla()[i][j].getObjeto(), new Integer(1));
                getCasilla()[i][j].getObjeto().setBounds(1+j*33, 1+i*33, 32, 32);
                getCasilla()[i][j].getObjeto().setBorderPainted(false);
                getCasilla()[i][j].getObjeto().setFocusPainted(false);
                getCasilla()[i][j].getObjeto().setContentAreaFilled(false);
                getCasilla()[i][j].getObjeto().setTransferHandler(getThObjeto());
                getCasilla()[i][j].getObjeto().addMouseListener(getListener());
                getCasilla()[i][j].getObjeto().setVisible(false);
                
                getCasilla()[i][j].setFondo(new Tile(true,Constant.HIERBA,configuracion.Tileset.getInstance().getHIERBA()));
                getLayeredPane().add(getCasilla()[i][j].getFondo(), new Integer(0));
                getCasilla()[i][j].getFondo().setBounds(1+j*33, 1+i*33, 32, 32);
                getCasilla()[i][j].getFondo().setTransferHandler(getThTile());
                getCasilla()[i][j].getFondo().addMouseListener(getListener());
                getCasilla()[i][j].getFondo().setVisible(true);
            }
        }
    }
    
    /**
     * Método para generar los elementos de la barra de herramientas
     */
    public void generarTileSet(){
        
        tileSetFondo = new ArrayList<>();
        tileSetObjeto = new ArrayList<>();
        panelTileSet.removeAll();
        
        getTileSetFondo().add(new Tile(true,Constant.HIERBA,configuracion.Tileset.getInstance().getHIERBA()));
        getTileSetFondo().add(new Tile(false,Constant.AGUA,configuracion.Tileset.getInstance().getAGUA()));
        getTileSetFondo().add(new Tile(true,Constant.TARIMA,configuracion.Tileset.getInstance().getTARIMA()));
        getTileSetFondo().add(new Tile(false,Constant.BLANK,configuracion.Tileset.getInstance().getBLANK()));
        getTileSetFondo().add(new Tile(true,Constant.TIERRA,configuracion.Tileset.getInstance().getTIERRA()));
        
        //Alfombra
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_AR_IZ,configuracion.Tileset.getInstance().getALFOMBRA_AR_IZ()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_AR_DE,configuracion.Tileset.getInstance().getALFOMBRA_AR_DE()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_AB_IZ,configuracion.Tileset.getInstance().getALFOMBRA_AB_IZ()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_AB_DE,configuracion.Tileset.getInstance().getALFOMBRA_AB_DE()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_CENTRO,configuracion.Tileset.getInstance().getALFOMBRA_CENTRO()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_AR,configuracion.Tileset.getInstance().getALFOMBRA_AR()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_AB,configuracion.Tileset.getInstance().getALFOMBRA_AB()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_DE,configuracion.Tileset.getInstance().getALFOMBRA_DE()));
        getTileSetFondo().add(new Tile(true,Constant.ALFOMBRA_IZ,configuracion.Tileset.getInstance().getALFOMBRA_IZ()));
        
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_AB,configuracion.Tileset.getInstance().getBORDE_TIERRA_AB()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_AR,configuracion.Tileset.getInstance().getBORDE_TIERRA_AR()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_DE,configuracion.Tileset.getInstance().getBORDE_TIERRA_DE()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_IZ,configuracion.Tileset.getInstance().getBORDE_TIERRA_IZ()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_AB_DE,configuracion.Tileset.getInstance().getBORDE_TIERRA_AB_DE()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_AB_IZ,configuracion.Tileset.getInstance().getBORDE_TIERRA_AB_IZ()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_AR_DE,configuracion.Tileset.getInstance().getBORDE_TIERRA_AR_DE()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_TIERRA_AR_IZ,configuracion.Tileset.getInstance().getBORDE_TIERRA_AR_IZ()));
        getTileSetObjeto().add(new Objeto(false,Constant.TIERRA_AB_DE_ESQUINA,configuracion.Tileset.getInstance().getBORDE_TIERRA_AB_DE_ESQUINA()));
        getTileSetObjeto().add(new Objeto(false,Constant.TIERRA_AB_IZ_ESQUINA,configuracion.Tileset.getInstance().getBORDE_TIERRA_AB_IZ_ESQUINA()));
        getTileSetObjeto().add(new Objeto(false,Constant.TIERRA_AR_DE_ESQUINA,configuracion.Tileset.getInstance().getBORDE_TIERRA_AR_DE_ESQUINA()));
        getTileSetObjeto().add(new Objeto(false,Constant.TIERRA_AR_IZ_ESQUINA,configuracion.Tileset.getInstance().getBORDE_TIERRA_AR_IZ_ESQUINA()));
        
        //NPCs
        getTileSetObjeto().add(new Objeto(false,Constant.NPC_HOMBRE,configuracion.Tileset.getInstance().getNPC_HOMBRE()));
        getTileSetObjeto().add(new Objeto(false,Constant.NPC_MUJER,configuracion.Tileset.getInstance().getNPC_MUJER()));
                
        //Borde de hierba
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AB,configuracion.Tileset.getInstance().getBORDE_AGUA_AB()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AR,configuracion.Tileset.getInstance().getBORDE_AGUA_AR()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_DE,configuracion.Tileset.getInstance().getBORDE_AGUA_DE()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_IZ,configuracion.Tileset.getInstance().getBORDE_AGUA_IZ()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AB_DE,configuracion.Tileset.getInstance().getBORDE_AGUA_AB_DE()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AB_IZ,configuracion.Tileset.getInstance().getBORDE_AGUA_AB_IZ()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AR_DE,configuracion.Tileset.getInstance().getBORDE_AGUA_AR_DE()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AR_IZ,configuracion.Tileset.getInstance().getBORDE_AGUA_AR_IZ()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AB_DE_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA_AB_DE_ESQUINA()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AB_IZ_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA_AB_IZ_ESQUINA()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AR_DE_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA_AR_DE_ESQUINA()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA_AR_IZ_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA_AR_IZ_ESQUINA()));
        
        //Borde de arena
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AB,configuracion.Tileset.getInstance().getBORDE_AGUA2_AB()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AR,configuracion.Tileset.getInstance().getBORDE_AGUA2_AR()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_DE,configuracion.Tileset.getInstance().getBORDE_AGUA2_DE()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_IZ,configuracion.Tileset.getInstance().getBORDE_AGUA2_IZ()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AB_DE,configuracion.Tileset.getInstance().getBORDE_AGUA2_AB_DE()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AB_IZ,configuracion.Tileset.getInstance().getBORDE_AGUA2_AB_IZ()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AR_DE,configuracion.Tileset.getInstance().getBORDE_AGUA2_AR_DE()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AR_IZ,configuracion.Tileset.getInstance().getBORDE_AGUA2_AR_IZ()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AB_DE_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA2_AB_DE_ESQUINA()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AB_IZ_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA2_AB_IZ_ESQUINA()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AR_DE_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA2_AR_DE_ESQUINA()));
        getTileSetObjeto().add(new Objeto(true,Constant.BORDE_AGUA2_AR_IZ_ESQ,configuracion.Tileset.getInstance().getBORDE_AGUA2_AR_IZ_ESQUINA()));
                
        //Rampas
        getTileSetObjeto().add(new Objeto(true,Constant.RAMPA_AB,configuracion.Tileset.getInstance().getRAMPA_AB()));
        getTileSetObjeto().add(new Objeto(true,Constant.RAMPA_AR,configuracion.Tileset.getInstance().getRAMPA_AR()));
        getTileSetObjeto().add(new Objeto(true,Constant.RAMPA_DE,configuracion.Tileset.getInstance().getRAMPA_DE()));
        getTileSetObjeto().add(new Objeto(true,Constant.RAMPA_IZ,configuracion.Tileset.getInstance().getRAMPA_IZ()));
        
        //Sillas
        getTileSetObjeto().add(new Objeto(false,Constant.SILLA_AB,configuracion.Tileset.getInstance().getSILLA_AB()));
        getTileSetObjeto().add(new Objeto(false,Constant.SILLA_AR,configuracion.Tileset.getInstance().getSILLA_AR()));
        getTileSetObjeto().add(new Objeto(false,Constant.SILLA_DE,configuracion.Tileset.getInstance().getSILLA_DE()));
        getTileSetObjeto().add(new Objeto(false,Constant.SILLA_IZ,configuracion.Tileset.getInstance().getSILLA_IZ()));
        
        getTileSetObjeto().add(new Objeto(false,Constant.LIBRERIA,configuracion.Tileset.getInstance().getLIBRERIA()));
        getTileSetObjeto().add(new Objeto(false,Constant.MESA,configuracion.Tileset.getInstance().getMESA()));
        getTileSetObjeto().add(new Objeto(false,Constant.BORDE_CASA,configuracion.Tileset.getInstance().getCASA()[0][0]));
        getTileSetObjeto().add(new Objeto(false,Constant.ARBOL,configuracion.Tileset.getInstance().getARBOL()));
                
        for (int i = 0; i < getTileSetFondo().size(); i++) {
            panelTileSet.add(getTileSetFondo().get(i));
            getTileSetFondo().get(i).setBounds(8+(i%(getNUM_HORIZONTALES()-1))*33, 21+(i/(getNUM_HORIZONTALES()-1))*33, 32, 32);
            getTileSetFondo().get(i).setTransferHandler(getThTile());
            getTileSetFondo().get(i).addMouseListener(getListener());
            getTileSetFondo().get(i).setVisible(true);
        }
        
        for (int i = 0; i < getTileSetObjeto().size(); i++) {
            panelTileSet.add(getTileSetObjeto().get(i));
            getTileSetObjeto().get(i).setBounds(8+(i%(getNUM_HORIZONTALES()-1))*33, 21+(i/(getNUM_HORIZONTALES()-1))*33, 32, 32);
            getTileSetObjeto().get(i).setTransferHandler(getThObjeto());
            getTileSetObjeto().get(i).addMouseListener(getListener());
            getTileSetObjeto().get(i).setBorderPainted(false);
            getTileSetObjeto().get(i).setFocusPainted(false);
            getTileSetObjeto().get(i).setContentAreaFilled(false);
            getTileSetObjeto().get(i).setVisible(false);
        }
    }

    /**
     * @return the NUM_VERTICALES
     */
    public int getNUM_VERTICALES() {
        return NUM_VERTICALES;
    }

    /**
     * @return the NUM_HORIZONTALES
     */
    public int getNUM_HORIZONTALES() {
        return NUM_HORIZONTALES;
    }

    /**
     * @return the casilla
     */
    public Casilla[][] getCasilla() {
        return casilla;
    }

    /**
     * @param casilla the casilla to set
     */
    public void setCasilla(Casilla[][] casilla) {
        this.casilla = casilla;
    }

    /**
     * @return the casillaConFocus
     */
    public Casilla getCasillaConFocus() {
        return casillaConFocus;
    }

    /**
     * @param casillaConFocus the casillaConFocus to set
     */
    public void setCasillaConFocus(Casilla casillaConFocus) {
        this.casillaConFocus = casillaConFocus;
    }

    /**
     * @return the layeredPane
     */
    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    /**
     * @param layeredPane the layeredPane to set
     */
    public void setLayeredPane(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane;
    }

    /**
     * @return the tileSetFondo
     */
    public ArrayList<JLabel> getTileSetFondo() {
        return tileSetFondo;
    }

    /**
     * @param tileSetFondo the tileSetFondo to set
     */
    public void setTileSetFondo(ArrayList<JLabel> tileSetFondo) {
        this.tileSetFondo = tileSetFondo;
    }

    /**
     * @return the tileSetObjeto
     */
    public ArrayList<JButton> getTileSetObjeto() {
        return tileSetObjeto;
    }

    /**
     * @param tileSetObjeto the tileSetObjeto to set
     */
    public void setTileSetObjeto(ArrayList<JButton> tileSetObjeto) {
        this.tileSetObjeto = tileSetObjeto;
    }

    /**
     * @return the listener
     */
    public MouseListener getListener() {
        return listener;
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(MouseListener listener) {
        this.listener = listener;
    }

    /**
     * @return the thTile
     */
    public TransferHandler getThTile() {
        return thTile;
    }

    /**
     * @param thTile the thTile to set
     */
    public void setThTile(TransferHandler thTile) {
        this.thTile = thTile;
    }

    /**
     * @return the thObjeto
     */
    public TransferHandler getThObjeto() {
        return thObjeto;
    }

    /**
     * @param thObjeto the thObjeto to set
     */
    public void setThObjeto(TransferHandler thObjeto) {
        this.thObjeto = thObjeto;
    }
    
    class DragMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            for (int i = 0; i < getNUM_VERTICALES(); i++) {
                for (int j = 0; j < getNUM_HORIZONTALES(); j++) {
                    if (e.getSource().equals(getCasilla()[i][j].getObjeto())) {
                        setCasillaConFocus(getCasilla()[i][j]);
                        panelPropiedades.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Casilla["+i+","+j+"]"));
                        lbCodFondo.setText(getCasilla()[i][j].getFondo().getCod());
                        lbCodObjeto.setText(getCasilla()[i][j].getObjeto().getCod());
                        if (getCasilla()[i][j].getFondo().isTransitable() && getCasilla()[i][j].getObjeto().isTransitable()) {
                            lbTransitable.setText("TRANSITABLE");
                        }else{
                            lbTransitable.setText("NO TRANSITABLE");
                        }
                        lbDatosGuardados.setText("");
                    }
                }
            }
            
            try{
                tfCoordI.setText(String.valueOf(getCasillaConFocus().getObjeto().getPuerta().getCoordIDestino()));
            }catch(NullPointerException npe){
                tfCoordI.setText("");
            }
            
            try{
                tfCoordJ.setText(String.valueOf(getCasillaConFocus().getObjeto().getPuerta().getCoordJDestino()));
            }catch(NullPointerException npe){
                tfCoordJ.setText("");
            } 
            
            try{
                tfMapaDestino.setText(String.valueOf(getCasillaConFocus().getObjeto().getPuerta().getMapaDestino()));
            }catch(NullPointerException npe){
                tfMapaDestino.setText("");
            }
            
            try{
                tfDialogo.setText(String.valueOf(getCasillaConFocus().getObjeto().getDescripcion()));
            }catch(NullPointerException npe){
                tfDialogo.setText("");
            }
            
            try{
                if (getCasillaConFocus().getObjeto().getPuerta()!=null) {
                    cbEsPuerta.setSelected(true);
                }else{
                    cbEsPuerta.setSelected(false);
                }
            }catch(NullPointerException npe){
                cbEsPuerta.setSelected(false);    
            }
            
            if (e.getSource().getClass().getName().equals("editor.Objeto")) {
                Objeto c = (Objeto) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
            }else{
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelCasillas = new javax.swing.JPanel();
        panelTileSet = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        rbFondo = new javax.swing.JRadioButton();
        rbObjetos = new javax.swing.JRadioButton();
        panelPropiedades = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jpPuerta = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfMapaDestino = new javax.swing.JTextField();
        tfCoordJ = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfCoordI = new javax.swing.JTextField();
        cbEsPuerta = new javax.swing.JCheckBox();
        btGuardar = new javax.swing.JButton();
        lbTransitable = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbCodFondo = new javax.swing.JLabel();
        lbCodObjeto = new javax.swing.JLabel();
        tfDialogo = new javax.swing.JTextField();
        btGenerarXML = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        spinnerIdMapa = new javax.swing.JSpinner();
        lbDatosGuardados = new javax.swing.JLabel();
        btImportarMapa = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuItemRefrescarInterfaz = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Eitr");

        panelCasillas.setBackground(new java.awt.Color(0, 0, 0));
        panelCasillas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelCasillas.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelCasillasLayout = new javax.swing.GroupLayout(panelCasillas);
        panelCasillas.setLayout(panelCasillasLayout);
        panelCasillasLayout.setHorizontalGroup(
            panelCasillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
        );
        panelCasillasLayout.setVerticalGroup(
            panelCasillasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        panelTileSet.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tileset"));

        javax.swing.GroupLayout panelTileSetLayout = new javax.swing.GroupLayout(panelTileSet);
        panelTileSet.setLayout(panelTileSetLayout);
        panelTileSetLayout.setHorizontalGroup(
            panelTileSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 649, Short.MAX_VALUE)
        );
        panelTileSetLayout.setVerticalGroup(
            panelTileSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Capa"));

        buttonGroup1.add(rbFondo);
        rbFondo.setText("Fondo");

        buttonGroup1.add(rbObjetos);
        rbObjetos.setText("Objetos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbFondo)
                    .addComponent(rbObjetos))
                .addGap(0, 143, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbFondo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbObjetos)
                .addGap(23, 23, 23))
        );

        panelPropiedades.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Propiedades"));

        jLabel3.setText("Diálogo:");

        jpPuerta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Mapa destino:");

        jLabel2.setText("Coord:");

        javax.swing.GroupLayout jpPuertaLayout = new javax.swing.GroupLayout(jpPuerta);
        jpPuerta.setLayout(jpPuertaLayout);
        jpPuertaLayout.setHorizontalGroup(
            jpPuertaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPuertaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPuertaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpPuertaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPuertaLayout.createSequentialGroup()
                        .addComponent(tfCoordI, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfCoordJ, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfMapaDestino, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jpPuertaLayout.setVerticalGroup(
            jpPuertaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPuertaLayout.createSequentialGroup()
                .addGroup(jpPuertaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfMapaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpPuertaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfCoordI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCoordJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        cbEsPuerta.setText("Puerta");

        btGuardar.setText("Guardar datos");
        btGuardar.addActionListener(e -> btGuardarActionPerformed());

        lbTransitable.setText("---");

        jLabel5.setText("Fondo:");

        jLabel6.setText("Objeto");

        lbCodFondo.setText("--");

        lbCodObjeto.setText("--");

        javax.swing.GroupLayout panelPropiedadesLayout = new javax.swing.GroupLayout(panelPropiedades);
        panelPropiedades.setLayout(panelPropiedadesLayout);
        panelPropiedadesLayout.setHorizontalGroup(
            panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPuerta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelPropiedadesLayout.createSequentialGroup()
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropiedadesLayout.createSequentialGroup()
                        .addComponent(cbEsPuerta)
                        .addGap(18, 18, 18)
                        .addComponent(lbTransitable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPropiedadesLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(tfDialogo))
                    .addGroup(panelPropiedadesLayout.createSequentialGroup()
                        .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(16, 16, 16)
                        .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCodFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbCodObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelPropiedadesLayout.setVerticalGroup(
            panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPropiedadesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbCodFondo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbCodObjeto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfDialogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addGroup(panelPropiedadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEsPuerta)
                    .addComponent(lbTransitable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpPuerta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btGenerarXML.setText("Generar XML");
        btGenerarXML.addActionListener(e -> btGenerarXMLActionPerformed());
        

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Mapa"));

        jLabel4.setText("Id_Mapa:");

        spinnerIdMapa.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spinnerIdMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinnerIdMapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        lbDatosGuardados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDatosGuardados.setText("---");

        btImportarMapa.setText("Importar mapa");
        btImportarMapa.addActionListener(e -> btImportarMapaActionPerformed());

        jMenu1.setText("Archivo");

        menuItemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuItemSalir.setText("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        jMenu1.add(menuItemSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ventana");

        menuItemRefrescarInterfaz.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menuItemRefrescarInterfaz.setText("Reiniciar interfaz");
        menuItemRefrescarInterfaz.addActionListener(e -> generarTileSet());
        jMenu2.add(menuItemRefrescarInterfaz);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTileSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCasillas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(lbDatosGuardados, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btImportarMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelPropiedades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btGenerarXML, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelPropiedades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbDatosGuardados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btImportarMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btGenerarXML, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelTileSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCasillas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );

        pack();
    }

    private void btGuardarActionPerformed() {
        getCasillaConFocus().getObjeto().setDescripcion(tfDialogo.getText());
        
        if (cbEsPuerta.isSelected()){
            try{
                int mapaDestino = Integer.parseInt(tfMapaDestino.getText());
                int coordenadaI = Integer.parseInt(tfCoordI.getText());
                int coordenadaJ = Integer.parseInt(tfCoordJ.getText());
                Objeto.Puerta puerta = new Objeto.Puerta(mapaDestino,coordenadaI,coordenadaJ);
                getCasillaConFocus().getObjeto().setPuerta(puerta);
                getCasillaConFocus().getObjeto().setTransitable(true);
                getCasillaConFocus().getFondo().setTransitable(true);
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(this,"Campos inválidos");
            }
        }
        
        lbDatosGuardados.setText("Datos Guardados");
    }


    private void btGenerarXMLActionPerformed() {
        File fichero;
        FileNameExtensionFilter filter;
               
        filter = new FileNameExtensionFilter("Mapa de Proyecto Asgard (.asg)", "asg");
        eligeFichero.setFileFilter(filter);
        int seleccion = eligeFichero.showSaveDialog(null);
        String data = LibsEditor.generarXML((int)spinnerIdMapa.getValue(), getCasilla());
        
        if (seleccion == JFileChooser.APPROVE_OPTION){
            fichero = eligeFichero.getSelectedFile();
            if (!fichero.getName().endsWith(".asg")) {
               JOptionPane.showMessageDialog(this, "Debe guardar el fichero en un formato válido (.asg)");
            }else{
                try {
                    LibsEditor.anadirLinea(data,fichero);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex){
                    //Aqui deberia añadir algo y eso
                }
            }
        }
    }

    private void btImportarMapaActionPerformed() {
        FileNameExtensionFilter filter;
        File fichero;
        
        filter = new FileNameExtensionFilter("Mapas de Proyecto Asgard (.asg)", "asg");
        eligeFichero.setFileFilter(filter);
        int seleccion = eligeFichero.showOpenDialog(null);
        
        if (seleccion == JFileChooser.APPROVE_OPTION){            
            fichero = eligeFichero.getSelectedFile();
            LibsEditor lb = new LibsEditor();
            generarMapaInicial(); //Limpio el mapa
            setCasilla(lb.cargaMapa(fichero, getCasilla()));
            repaint();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton btGenerarXML;
    private javax.swing.JButton btGuardar;
    private javax.swing.JButton btImportarMapa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbEsPuerta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jpPuerta;
    private javax.swing.JLabel lbCodFondo;
    private javax.swing.JLabel lbCodObjeto;
    private javax.swing.JLabel lbDatosGuardados;
    private javax.swing.JLabel lbTransitable;
    private javax.swing.JMenuItem menuItemRefrescarInterfaz;
    private javax.swing.JMenuItem menuItemSalir;
    private javax.swing.JPanel panelCasillas;
    private javax.swing.JPanel panelPropiedades;
    private javax.swing.JPanel panelTileSet;
    private javax.swing.JRadioButton rbFondo;
    private javax.swing.JRadioButton rbObjetos;
    private javax.swing.JSpinner spinnerIdMapa;
    private javax.swing.JTextField tfCoordI;
    private javax.swing.JTextField tfCoordJ;
    private javax.swing.JTextField tfDialogo;
    private javax.swing.JTextField tfMapaDestino;
    // End of variables declaration
}
