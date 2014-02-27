/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiroparabolico;

import java.awt.Image;
import java.awt.Toolkit;

public class Pelota extends Base {

    private static int conteo;
    private double speedX;
    private double speedY;
    

    public Pelota(int posX, int posY, Image image) {
        super(posX, posY,image);
        speedX = 0;
        speedY = 0;

    }

    /*
     *Clase getConteo
     *Sirve para regresar el conteo del score
     * 
     */
    public static int getConteo() {
        return conteo;
    }

    /*
     *Clase setConteo
     *Sirve para definir el conteo
     * recibe de parametro un entero
     */
    public static void setConteo(int cont) {
        conteo = cont;
    }

    /*
     *Clase setConteo
     *Sirve para definir el conteo
     * recibe de parametro un entero
     */
    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    /*
     *Clase setSpeed
     *Sirve para definir la velocidad
     * recibe de parametro un entero
     */
    public void setSpeedX(double cant) {
        speedX = cant;
    }

    public void setSpeedY(double cant) {
        speedY = cant;
    }
}
