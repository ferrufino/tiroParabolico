/*
 *Class Base
 *
 *@Author Gustavo Ferrufino
 *@Matricula A00812572
 */
package tiroparabolico;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Base {

    private int posX;    //posicion en x.       
    private int posY;	//posicion en y.
    private ImageIcon icono;    //icono.

    public Base(int posX, int posY, Image image) {
        this.posX = posX;
        this.posY = posY;
        icono = new ImageIcon(image);

    }

    /*
     *Clase setPosX
     *Sirve para definir la posX
     * recibe de parametro un entero
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /*
     *Clase getPosx
     *regresa la posicion en X
     * 
     */
    public int getPosX() {
        return posX;
    }

    /*
     *Clase setPosY
     *Sirve para definir la posicion en Y
     * recibe de parametro un entero
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /*
     *Clase getPosY
     *regresa la posicion en Y
     * 
     */
    public int getPosY() {
        return posY;
    }

    /*
     *Clase getAncho
     *regresa lo ancho de un objeto ImageIcon
     *en este caso de la animacion creada. Malo/Bueno
     */
    public void setImageIcon(ImageIcon icono) {
        this.icono = icono;
    }

    /**
     * Metodo de acceso que regresa el icono del objeto
     *
     * @return icono es el <code>icono</code> del objeto.
     */
    public ImageIcon getImageIcon() {
        return icono;
    }

    /**
     * Metodo de acceso que regresa el ancho del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * icono.
     */
    public int getAncho() {
        return icono.getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del
     * icono.
     */
    public int getAlto() {
        return icono.getIconHeight();
    }

    /**
     * Metodo de acceso que regresa la imagen del icono
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * icono.
     */
    public Image getImagenI() {
        return icono.getImage();
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    /*
     *Clase getPerimetro
     *regresa el perimetro de un objeto formado por 
     *la altura, ancho, posX, posY
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /*
     *Clase instersecta2
     *recibe un objeto tipo base 
     *regresa un boleano si intersecta dentro de un permetro definido
     */
    public boolean intersecta2(Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    /*
     *Clase instersectaPuntos
     *recibe dos enteros
     *regresa un boleano si intersecta dentro la coordenadas dadas como dato
     *de entrada
     */
    public boolean intersectaPuntos(int posX, int posY) {
        return getPerimetro().contains(posX, posY);
    }

    /*
     *Clase intersecta
     *recibe onjeto malo
     *Metodo que sirve para saber si un objeto malo
     *Intersecta con un perimetro
     */
    public boolean intersecta(Base obj) {
        // return getPerimRec().intersects(obj.getPerimetro());
        return getPerimetro().intersects(obj.getPerimetro());
    }
    /*
     *Clase updates
     *recibe un long
     *Actualiza la animacion
     */

}
