package GUI;

import logic.Field;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


/**
 * Created by Константин on 05.03.2017.
 */
public class Game extends Canvas implements Runnable {

    public boolean running;

    private int x = 0;
    private int y = 0;
    private int mobX = 450;
    private int mobY = 300;
    private Player hero = new Player(x,y,image);
    private Player mob = new Player(mobX,mobY,image)

    String path = "maps/map.txt";
    Field playfield = new Field(path);


    public void run(){
        try {
            Thread.sleep(7);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        update();
    }

    public void update(){
        if (leftPressed && playfield.ifPossibleToMove(x-50,y)) {
            hero.moveX(x-50);
            if ((x<mobX && y<mobY && (mobX-x)>(mobY-y)) || (x<mobX && mobY==y) || (x<mobX && y>mobY && (mobX-x)>(y-mobY))) {
                mob.moveX(mobX-50);
            }
            if ((x>mobX && y<mobY && (x-mobX)>(mobY-y)) || (x>mobX && y==mobY) || (x>mobX && y>mobY && (x-mobX)>(y-mobY))) {
                mob.moveX(mobX+50);
            }
            if ((x<mobX && y<mobY && (mobX-x)<(mobY-y)) || (x==mobX && mobY>y) || (x>mobX && y<mobY && (x-mobX)<(mobY-y))) {
                mob.moveY(mobY-50);
            }
            if ((x<mobX && y>mobY && (mobX-x)>(y-mobY)) || (x==mobX && mobY<y) || (x>mobX && y>mobY && (x-mobX)<(y-mobY))) {
                mob.moveY(mobY+50);
            }
        }
        if (rightPressed && playfield.ifPossibleToMove(x+50,y)) {
            hero.moveX(x+50);
        }
        if (downPressed && playfield.ifPossibleToMove(x,y+50)) {
            hero.moveY(y+50);
        }
        if (upPressed && playfield.ifPossibleToMove(x,y-50)) {
            hero.moveY(y-50);
        }
    }

    public void init(){
        addKeyListener(new KeyInputHandler());
        hero = getSprite("man.png");
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        hero.draw(g, x, y);
        g.dispose();
        bs.show();
    }

    public void start(){
        running = true;
        new Thread(this).start();
    }

    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static String NAME = "Escape the Tomb";





//    public Player getSprite(String path) {
//        BufferedImage sourceImage = null;
//
//        try {
//            URL url = this.getClass().getClassLoader().getResource(path);
//            sourceImage = ImageIO.read(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Player sprite = new Player(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
//
//        return sprite;
//    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame(Game.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();
    }



    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private boolean upPressed = false;

    private KeyAdapter keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
        }
    };
}
