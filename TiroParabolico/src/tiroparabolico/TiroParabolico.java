/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiroparabolico;

import javax.swing.JFrame;

/**
 *
 * @author Ferrufino
 */
public class TiroParabolico {

 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PlayGround variable = new PlayGround();//creo un objeto
        variable.setVisible(true); //Aparezca mi codigo en clase AppletExamen1
        variable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
