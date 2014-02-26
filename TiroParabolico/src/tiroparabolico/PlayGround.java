/*
 *Class AppletExamen1
 *
 *@Author Gustavo Ferrufino
 *@Matricula A00812572
 */
package tiroparabolico;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.LinkedList;
import java.awt.Point;
import javax.swing.JFrame;// 

public class PlayGround extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private SoundClip fail;    // Objeto AudioClip
    private SoundClip collide;    //Objeto AudioClip 
    private Bloque fireBasket;    // Objeto de la clase Elefante
    private Pelota basketBall;   //Objeto de la clase Raton
   //listas
    private LinkedList listaIzq;           //lista de basketBalls por la Izquierda
    private LinkedList listaDer;           //lista de perrosMalos por la derecha
    
    private int cantidad;               //cantidadidad de basketBalls
    private int timeRetard;    //Contador para retrazar aparicion de DESAPARECE
    private boolean IconPressed;
    private int coordenada_x;
    private int coordenada_y;
    private int off_x;
    private int off_y;
    private int VIDAS;
    //imagenes
    private Image gameover;
    private Image background;
    private Image chocan;
    
    private char teclaPresionada;
    private int posX;
    private int posY;
    private int SCORE;
    private int POINTS;
    private int xMayor;
    private int xMenor;
    private int yMayor;
    private int yMenor;
    private boolean pause; //Permite al usuario pausar el juego
    private boolean action;
    private long tiempoActual;
    private long tiempoInicial;
    private boolean BEGIN;
    private boolean crashed;
    private int CUADRANTE;

//constructor
    public PlayGround() {

        setSize(800, 600);
        crashed = false;
        BEGIN = true;
        pause = false;
        action = false;
        timeRetard = 0;
        teclaPresionada = 0;

        SCORE = 0;                    //puntaje inicial
        VIDAS = 1;                    //vida inicial
        xMayor = (getWidth() - getWidth() / 10);           //posicion máxima en x que tendrán los basketBalls
        xMenor = 0;           //posicion mínima en x que tendrán los basketBalls
        yMayor = (getHeight() - (getHeight() / 10));          //posicion máxima en y que tendrán los basketBalls
        yMenor = 10;        //posicion mínima en y que tendrán los basketBalls

        //Se cargan los sonidos.
        URL beURL = this.getClass().getResource("sounds/fail-buzzer-03.wav");
        fail = new SoundClip("sounds/fail-buzzer-03.wav");
        URL baURL = this.getClass().getResource("sounds/Choque.wav");
        collide = new SoundClip("sounds/Choque.wav");

        posX = 100;     //se generarán los basketBalls en posiciones aleatorias fuera del applet
        posY = 100;
 
        URL fbURL = this.getClass().getResource("images/fireBasket.gif");
        fireBasket = new Bloque((getWidth()-150),(getHeight()-80), Toolkit.getDefaultToolkit().getImage(fbURL));
 
        URL bbURL = this.getClass().getResource("images/basketBall.gif");
        basketBall = new Pelota(50, 300, Toolkit.getDefaultToolkit().getImage(bbURL));


       
        
      
        URL xuURL = this.getClass().getResource("images/gOVER.png");
        gameover = Toolkit.getDefaultToolkit().getImage(xuURL);

        URL bgURL = this.getClass().getResource("images/background.jpg");
        background = Toolkit.getDefaultToolkit().getImage(bgURL);

        URL cHURL = this.getClass().getResource("images/boom.png");
        chocan = Toolkit.getDefaultToolkit().getImage(cHURL);

        //Inicializadores 
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        /**
         * 
         */
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();

    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        tiempoActual = System.currentTimeMillis();
        while (VIDAS > 0) {
            if (!pause) {
                Actualiza();
                ChecaColision();
            }
            repaint();    // Se Actualiza el <code>Applet</code> repintando el POINTSenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para Actualizar la posicion de objetos elefante y basketBall.
     *
     */
    public void Actualiza() {
        


        //Actualiza la animacion creada de los objetos
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
       

        if (action) {
            switch (teclaPresionada) {
                case 1: {

                    fireBasket.setPosX(fireBasket.getPosX() - 1);
                    break; //se mueve hacia la izquierda
                }
                case 2: {

                    fireBasket.setPosX(fireBasket.getPosX() + 1);
                    break; //se mueve hacia la derecha
                }
            }
        }
        /*
        if (IconPressed) {
            fireBasket.setPosY(coordenada_y - off_y);
            fireBasket.setPosX(coordenada_x - off_x);

        } */
        //lista de Pelotas que se mueven de izq a derecha 
            if (basketBall.getPosY() < getHeight()) {
                basketBall.setPosX(basketBall.getPosX() + basketBall.getSpeedX());       
            }

        

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y basketBall
     * con las orillas del <code>Applet</code>.
     */
    public void ChecaColision() {

        //checa colision con el applet
        if (fireBasket.getPosY() < 0) {              //choca borde de arriba
            fireBasket.setPosY(0);
        }

        if (fireBasket.getPosY() + fireBasket.getAlto() > getHeight()) {       //si se pasa del borde de abajo
            fireBasket.setPosY(getHeight() - fireBasket.getAlto());
        }

        if (fireBasket.getPosX() < 0) {                             //si se pasa del borde de la izquierda
            fireBasket.setPosX(0);
        }

        if (fireBasket.getPosX() + fireBasket.getAncho() > getWidth()) {      //si se pasa del borde de la derecha
            fireBasket.setPosX(getWidth() - fireBasket.getAncho());
        }
        //Listas encadenada de malos de Izq a Derecha
     
            if (basketBall.getPosX() + basketBall.getAncho() > getWidth()) {    //basketBall colisiona a la derecha del applet
                fail.play();
                basketBall.setPosX(-10);                                           //se reposiciona en su posicion inicial
                basketBall.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);
            }


        //Colision entre objetos
        //Lista Izq
  
            if (fireBasket.intersecta(basketBall)) {
                //&&dragged up
                crashed = true;
                collide.play();
                SCORE += 100;
                basketBall.setConteo(basketBall.getConteo() + 1);
                basketBall.setPosX(-10);     // se reposiciona el basketBall
                basketBall.setPosY(((int) (Math.random() * (yMayor - yMenor))) + yMenor);

            }


       
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo lo que hace es Actualizar el Paint
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Update la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Update el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen Actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion Actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (VIDAS > 0) {
            if (fireBasket != null) {

                g.drawImage(background, 0, 0, this);

                if (pause) {

                    g.setFont(new Font("Avenir Black", Font.BOLD, 60));
                    g.setColor(Color.white);
                    g.drawString(fireBasket.getPausado(), 400, 400);

                } else {

                    //Dibuja string Score
                    g.setColor(Color.black);
                    g.setFont(new Font("Avenir Black", Font.BOLD, 18));
                    g.drawString("Score: " + basketBall.getConteo(), 850, 60);

                    //Dibuja la imagen en la posicion Actualizada
                    if (!crashed && (timeRetard < 20)) {
                        g.drawImage(fireBasket.getImagenI(), fireBasket.getPosX(), fireBasket.getPosY(), this);
                    } else {
                        g.drawString(fireBasket.getGone(), fireBasket.getPosX(), fireBasket.getPosY());
                        timeRetard++;
                        if (timeRetard == 19) {
                            crashed = false;
                            timeRetard = 0;
                        }
                    }
                    g.setColor(Color.white);
          
                  
                        g.drawImage(basketBall.getImagenI(), basketBall.getPosX(), basketBall.getPosY(), this);
                 
                   

                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(gameover, -200, 0, this);
        }
    }
   /*
     *Metodo keyPressed
     *Cuando una tecla esta apretada
     *recibe de param un evento, en este caso se busca que sea la p
     *para pausar el juego
     */
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {

            pause = !pause;

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            teclaPresionada = 1;

        } 
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            teclaPresionada = 2;

        }
        
        if (e.getKeyCode() == KeyEvent.VK_G) {
            
            
            
        }

        action = true;

    }

    public void keyTyped(KeyEvent e) { //metodo cuando una tecla fue typeada
    }

    public void keyReleased(KeyEvent e) {

        action = false; //Presiono flecha arriba

    }
    /*
     *Metodo mouseClicked
     *Cuando el mouse es apretado
     *recibe de param un evento, que ayudara a definir donde fue picado
     *dentro del applet
     */
    public void mouseClicked(MouseEvent e) {

        if ((e.getX() < getWidth() / 2) && (e.getY() < getHeight() / 2)) {

            CUADRANTE = 4; // El mouse fue presionado

        } else if ((e.getX() >= (getWidth() / 2)) && (e.getY() < getHeight() / 2)) {

            CUADRANTE = 1; // El mouse fue presionado

        } else if ((e.getX() < getWidth() / 2) && (e.getY() >= getHeight() / 2)) {

            CUADRANTE = 3; // El mouse fue presionado

        } else {

            CUADRANTE = 2; // El mouse fue presionado

        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
 /*
        if (fireBasket.intersectaPuntos(e.getX(), e.getY()) & !IconPressed) {
            coordenada_x = e.getX();
            coordenada_y = e.getY();
            off_x = e.getX() - fireBasket.getPosX();
            off_y = e.getY() - fireBasket.getPosY();
            IconPressed = true;
            //draggedUP=true;
        }
         */
    }

    public void mouseReleased(MouseEvent e) {//metodo cuando el mouse es soltado
        IconPressed = false;
    }

    public void mouseMoved(MouseEvent e) {  //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {   //metodos de MouseMotionListener

        if (IconPressed) {   //si la imagen está presionada y la imagen se mueve, se guardan posiciones
            coordenada_x = e.getX();
            coordenada_y = e.getY();
        }
    }

}
