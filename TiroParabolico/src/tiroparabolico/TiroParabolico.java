/**
 *
 * @author Gustavo Ferrufino
 */
package tiroparabolico; //Paquete importado

import javax.swing.JFrame;

public class TiroParabolico {

    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PlayGround variable = new PlayGround();//creo un objeto
        variable.setVisible(true); //Aparezca mi codigo en clase AppletExamen1
        variable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
