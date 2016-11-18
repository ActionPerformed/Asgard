package main;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JComponent;

/**
 * Clase que contiene métodos usados por diferentes clases
 * 
 * @author ActionPerformed
 * @version 1.0, 6 Jun 2014
 */
public final class Libs {
    
    private static final Libs INSTANCE = new Libs();
    
    /**
     * Configura un <code>JComponent</code> con la fuente y el color de fondo
     * indicados por parametro.
     * 
     * @param componente <code>JComponent</code> a configurar
     * @param fuente <code>Font</code> a aplicar
     * @param fontSize Tamaño de fuente a aplicar
     */
    public void formatearComponente(JComponent componente, Font fuente, float fontSize){
        componente.setFont(fuente);
        componente.setFont (componente.getFont().deriveFont(Font.PLAIN,fontSize));
        componente.setBackground(Color.WHITE);
    }
        
    /**
     * Devuelve una instancia de <code>Libs></code> 
     * @return Instancia de <code>Libs></code>
     */
    
    public static Libs getInstance() {
        return INSTANCE;
    }
}
