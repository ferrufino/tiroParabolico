/*
 *Class AppletExamen1
 *
 *@Author Gustavo Ferrufino
 *@Matricula A00812572
 */
package tiroparabolico;

import java.awt.Rectangle;
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
    // Se declaran las variables objetos. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private SoundClip fail;    // Objeto AudioClip
    private SoundClip collide;    //Objeto AudioClip 
    private Bloque fireBasket;    // Objeto de la clase Elefante
    private Pelota basketBall;   //Objeto de la clase Raton
    private Rectangle box;
   
    
    private int gravedad;
    private int cantidad;               //cantidadidad de basketBalls
    private int timeRetard;    //Contador para retrazar aparicion de DESAPARECE
    private boolean boxClicked;
    private int coordenada_x;
    private int coordenada_y;
    private int contPerdidas;
    private int difVel;
    private int vidas;
    private boolean boolTime;
    private double time;
    
    //imagenes
    private Image gameover;
    private Image background;
    private Image chocan;

    private double velXI;
    private double velYI;
    private int speed;
    private char teclaPresionada;
    private int posX;
    private int posY;
    private int score;
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
    private boolean instrucciones;
    private int CUADRANTE;

//constructor
    public PlayGround() {

        setSize(800, 600);
        crashed = false;
        BEGIN = true;
        pause = false;
        action = false;
        instrucciones = false;
        timeRetard = 0;
        teclaPresionada = 0;
        contPerdidas = 0;
        difVel=0;
       
        gravedad=5;
        time = 0;
        score = 0;                    //puntaje inicial
        vidas = 5;                    //vida inicial
        xMayor = (getWidth() - getWidth() / 10);           //posicion máxima en x que tendrán el basketBall
        xMenor = 0;           //posicion mínima en x que tendrá el basketBall
        yMayor = (getHeight() - (getHeight() / 10));          //posicion máxima en y que tendrán el basketBall
        yMenor = 10;        //posicion mínima en y que tendrá el basketBall
        velXI = 0;
        velYI = 0;
        //Se cargan los sonidos.
        URL beURL = this.getClass().getResource("sounds/fail-buzzer-03.wav");
        fail = new SoundClip("sounds/fail-buzzer-03.wav");
        URL baURL = this.getClass().getResource("sounds/Choque.wav");
        collide = new SoundClip("sounds/Choque.wav");

        posX = 100;     //se generarán los basketBalls en posiciones aleatorias fuera del applet
        posY = 100;

        URL fbURL = this.getClass().getResource("images/fireBasket.gif");
        fireBasket = new Bloque((getWidth() - 150), (getHeight() - 130), Toolkit.getDefaultToolkit().getImage(fbURL));

        URL bbURL = this.getClass().getResource("images/basketBall.gif");
        basketBall = new Pelota(50, 250, Toolkit.getDefaultToolkit().getImage(bbURL));

        box= new Rectangle(50,250, basketBall.getAncho(), basketBall.getAlto());
        URL xuURL = this.getClass().getResource("images/gOVER.png");
        gameover = Toolkit.getDefaultToolkit().getImage(xuURL);

        URL bgURL = this.getClass().getResource("images/background3.jpg");
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
        while (vidas > 0) {
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
     * Metodo usado para Actualizar la posicion de objetos fireBasket y
     * basketBall.
     *
     */
    public void Actualiza() {
        
         if (contPerdidas == 3) {
                contPerdidas = 0;
                difVel+=3;
                gravedad+=4;
                vidas--;
            }
  
        if (boxClicked) {
            
           time += 0.020;
           basketBall.setSpeedX(velXI);
           basketBall.setSpeedY( (velYI*-1) + gravedad *time);
           basketBall.setPosX(basketBall.getPosX() + (int) (basketBall.getSpeedX()));
           basketBall.setPosY(basketBall.getPosY() + (int) (basketBall.getSpeedY()));

        }
        
        if (action) {
            switch (teclaPresionada) {
                case 1: {

                    fireBasket.setPosX(fireBasket.getPosX() - 7);
                    break; //se mueve hacia la izquierda
                }
                case 2: {

                    fireBasket.setPosX(fireBasket.getPosX() + 7);
                    break; //se mueve hacia la derecha
                }
            }
        }

         
        if (basketBall.getPosY() < getHeight()) {
            basketBall.setPosX( (int) (basketBall.getPosX() + basketBall.getSpeedX()));
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

        if (fireBasket.getPosX() < 150) {                             //si se pasa del borde de la izquierda
            fireBasket.setPosX(150);
        }

        if (fireBasket.getPosX() + fireBasket.getAncho() > getWidth()) {      //si se pasa del borde de la derecha
            fireBasket.setPosX(getWidth() - fireBasket.getAncho());
        }
      
       //basketBall colisiona abajo
        if (basketBall.getPosY()+ basketBall.getAlto() > getHeight()) {  
            
            contPerdidas+=1;
            fail.play();
            boxClicked = false;
            time = 0;
            basketBall.setPosX(50);  //se reposiciona en su posicion inicial
            basketBall.setPosY(250);
            basketBall.setSpeedX(0);
            if (basketBall.getConteo()> 0) 
            basketBall.setConteo(basketBall.getConteo()-1);
        }

        //Colision entre objetos
        if (fireBasket.intersecta(basketBall)) {
            boxClicked=false;
            time=0;
        
            collide.play();
            basketBall.setConteo(basketBall.getConteo()+2);
          
            basketBall.setPosX(50);     // se reposiciona el basketBall
            basketBall.setPosY(250);
            basketBall.setSpeedX(0);

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
        if (vidas > 0) {
            if (fireBasket != null) {

                g.drawImage(background, 0, 0, this);
                
                if (pause) {

                    g.setFont(new Font("Avenir Black", Font.BOLD, 60));
                    g.setColor(Color.white);
                    g.drawString(fireBasket.getPausado(), 400, 400);

                    if (instrucciones) {
                        g.setColor(Color.black);
                        g.drawRect(getWidth() / 3, getHeight() / 5, 500, 450);
                        g.fillRect(getWidth() / 3, getHeight() / 5, 500, 450);
                        g.setColor(Color.white);
                        g.setFont(new Font("Avenir Black", Font.BOLD, 18));
                        g.drawString("Instrucciones", getWidth() / 3 + 10, getHeight() / 5 + 30);
                        g.setFont(new Font("Avenir Black", Font.BOLD, 16));
                        g.drawString("P - Pausar", getWidth() / 3 + 50, getHeight() / 5 + 50);
                        g.drawString("G - Guardar", getWidth() / 3 + 50, getHeight() / 5 + 70);
                        g.drawString("C - Cargar", getWidth() / 3 + 50, getHeight() / 5 + 90);
                        g.drawString("S - Activar/Desactivar Sonido", getWidth() / 3 + 50, getHeight() / 5 + 110);
                        g.drawString("I - Instrucciones", getWidth() / 3 + 50, getHeight() / 5 + 130);
                        g.drawString("Flechas (Derecha e Izquiera) - Movimiento canasta", getWidth() / 3 + 50, getHeight() / 5 + 150);
                        g.drawString("Click en pelota - Iniciar ruta parabolica", getWidth() / 3 + 50, getHeight() / 5 + 170);

                    }

                } else {
                    if (instrucciones) {
                        g.setColor(Color.black);
                        g.drawRect(getWidth() / 3, getHeight() / 5, 500, 500);
                        g.fillRect(getWidth() / 3, getHeight() / 5, 500, 500);
                        g.setColor(Color.white);
                        g.setFont(new Font("Avenir Black", Font.BOLD, 18));
                        g.drawString("Instrucciones", getWidth() / 3 + 10, getHeight() / 5 + 30);
                        g.setFont(new Font("Avenir Black", Font.BOLD, 16));
                        g.drawString("P - Pausar", getWidth() / 3 + 50, getHeight() / 5 + 50);
                        g.drawString("G - Guardar", getWidth() / 3 + 50, getHeight() / 5 + 70);
                        g.drawString("C - Cargar", getWidth() / 3 + 50, getHeight() / 5 + 90);
                        g.drawString("S - Activar/Desactivar Sonido", getWidth() / 3 + 50, getHeight() / 5 + 110);
                        g.drawString("I - Instrucciones", getWidth() / 3 + 50, getHeight() / 5 + 130);
                        g.drawString("Flechas (Derecha e Izquiera) - Movimiento canasta", getWidth() / 3 + 50, getHeight() / 5 + 150);
                        g.drawString("Click en pelota - Iniciar ruta parabolica", getWidth() / 3 + 50, getHeight() / 5 + 170);
                        pause = true;
                    } else {
                        //Dibuja string Score
                        g.setColor(Color.white);
                        g.setFont(new Font("Avenir Black", Font.ITALIC, 18));
      
                        g.drawString("Score: " + basketBall.getConteo(), 600, 60);
                        g.drawString("Life: " + vidas, 600, 80);

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

                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
            g.drawImage(gameover, 0, 0, this);
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
            action = true;
            teclaPresionada = 1;

        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            action = true;
            teclaPresionada = 2;

        }

        if (e.getKeyCode() == KeyEvent.VK_G) {

        }
        if (e.getKeyCode() == KeyEvent.VK_C) {

        }
        if (e.getKeyCode() == KeyEvent.VK_I) {

            if (instrucciones) {
                instrucciones = false;
                pause=false;
            } else {
                pause = true;
                instrucciones = true;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_S) {

        }
        

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
        
        if (!pause) {
            if (box.contains(e.getX(), e.getY()) && (box.getX() == basketBall.getPosX())) {
                boxClicked = true;
                speed = (int)((Math.random() *((5)-(2)))+ 2); 
                velXI = speed *  (Math.cos(Math.toRadians(45)));
                velYI = speed *  (Math.sin(Math.toRadians(45)));
                boolTime = true;
            }
        }
    
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {//metodo cuando el mouse es soltado

    }

    public void mouseMoved(MouseEvent e) {  //metodos de MouseMotionListener

    }

    public void mouseDragged(MouseEvent e) {   //metodos de MouseMotionListener

    }

}
