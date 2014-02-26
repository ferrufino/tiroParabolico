/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiroparabolico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Image;
import java.awt.Toolkit;

public class Bloque extends Base {

    private int speed;

    public Bloque(int posX, int posY) {
        super(posX, posY);
        
        speed = 5;

    }
    //Variables Strings
    private static final String PAUSADO = "PAUSADO";
    private static final String DESAPARECE = "DESAPARECE";

    /**
     * Metodo para obtener el string PAUSADO
     */
    public static String getPausado() {
        return PAUSADO;
    }

    /**
     * Metodo para obtener el string DESAPARECE
     */
    public static String getGone() {
        return DESAPARECE;
    }

    /**
     * Metodo para obtener la velocidad Speed
     */
    public int getSpeed() {
        return speed;
    }
    /**
     * Metodo para definir la velocidad
     */
    public void setSpeed(int cant) {
        speed = cant;
    }

}
