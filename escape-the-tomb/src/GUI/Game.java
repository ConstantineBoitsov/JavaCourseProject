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
public class Game extends JPanel implements Runnable {

    public boolean running;


    private int x = 0;
    private int y = 0;
    private int mobX = 450;
    private int mobY = 100;

    ImageIcon ii = new ImageIcon("assets/hero.png");
    private Image image = ii.getImage();

    public Player hero = new Player(x,y,"assets/hero.png");
    public Player mob = new Player(mobX,mobY,"assets/mummy.png");

    String path = "src/maps/map.txt";
    Field playfield = new Field(path);


    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private boolean upPressed = false;


    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static String NAME = "Escape the Tomb";

    public void run(){
        while(running) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.err.println(e);
            }

            update();
        }
    }

    public void update(){
        if (leftPressed && playfield.ifPossibleToMove((hero.getX()-50)/50,hero.getY()/50)) {
            isGameOver();
            hero.moveX(-50);
            isWin();
            moveForMob();
        }
        if (rightPressed && playfield.ifPossibleToMove((hero.getX()+50)/50,hero.getY()/50)) {
            isGameOver();
            hero.moveX(50);
            isWin();
            moveForMob();
        }
        if (downPressed && playfield.ifPossibleToMove(hero.getX()/50,(hero.getY()+50)/50)) {
            isGameOver();
            hero.moveY(50);
            isWin();
            moveForMob();
        }
        if (upPressed && playfield.ifPossibleToMove(hero.getX()/50,(hero.getY()-50)/50)) {
            isGameOver();
            hero.moveY(-50);
            isWin();
            moveForMob();
        }

        repaint();
    }

    public void init(){
        setFocusable(true);
        addKeyListener(keyListener);
//        addKeyListener(new TAdapter());

        setBackground(Color.WHITE);
    }

//    public void render(){
//        BufferStrategy bs = getBufferStrategy();
//        if (bs == null){
//            createBufferStrategy(2);
//            requestFocus();
//            return;
//        }
//
//        Graphics g = bs.getDrawGraphics();
//        g.setColor(Color.black);
//        g.fillRect(0, 0, getWidth(), getHeight());
//        hero.draw(g, x, y);
//        mob.draw(g, mobX, mobY);
//        g.dispose();
//        bs.show();
//    }

    Graphics g;
    public void start(){
        running = true;
//        playfield.drawBoard(g);
        new Thread(this).start();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {

            drawObjects(g);
            if (hero.getX()==0 && hero.getY()==450) {
                drawCongratulations(g);
            }

        } else {

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        if (hero.isVisible()) {
            g.drawImage(hero.getImage(), hero.getX(), hero.getY(),this);
        }


        if (mob.isVisible()) {
            g.drawImage(mob.getImage(), mob.getX(), mob.getY(), this);
        }


    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (WIDTH - fm.stringWidth(msg)) / 2, HEIGHT / 2);
    }
    private void drawCongratulations(Graphics g) {

        String msg = "You won!";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (WIDTH - fm.stringWidth(msg)) / 2, HEIGHT / 2);
    }



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

        game.init();
//        game.render();
        game.start();
    }


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

    private void isGameOver() {
        if (hero.getX()==mob.getX() && hero.getY()==mob.getY()) {
            running = false;
        }
    }

    private void isWin() {
        if (hero.getX()==0 && hero.getY()==450) {
            running = false;
        }
    }

    public void moveForMob() {
        if (((hero.getX()<mob.getX() && hero.getY()<mob.getY() && (mob.getX()-hero.getX())>(mob.getY()-hero.getY())) || (hero.getX()<mob.getX() && mob.getY()==hero.getY()) || (hero.getX()<mob.getX() && hero.getY()>mob.getY() && (mob.getX()-hero.getX())>(hero.getY()-mob.getY())) && playfield.ifPossibleToMove((mob.getX()-50)/50,mob.getY()/50))) {
            mob.moveX(-50);
        }
        if (((hero.getX()>mob.getX() && hero.getY()<mob.getY() && (hero.getX()-mob.getX())>(mob.getY()-hero.getY())) || (hero.getX()>mob.getX() && hero.getY()==mob.getY()) || (hero.getX()>mob.getX() && hero.getY()>mob.getY() && (hero.getX()-mob.getX())>(hero.getY()-mob.getY()))) && playfield.ifPossibleToMove((mob.getX()+50)/50,mob.getY()/50)) {
            mob.moveX(50);
        }
        if (((hero.getX()<mob.getX() && hero.getY()<mob.getY() && (mob.getX()-hero.getX())<(mob.getY()-hero.getY())) || (hero.getX()==mob.getX() && mob.getY()>hero.getY()) || (hero.getX()>mob.getX() && hero.getY()<mob.getY() && (hero.getX()-mob.getX())<(mob.getY()-hero.getY()))) && playfield.ifPossibleToMove(mob.getX()/50,(mob.getY()-50)/50)) {
            mob.moveY(-50);
        }
        if (((hero.getX()<mob.getX() && hero.getY()>mob.getY() && (mob.getX()-hero.getX())<(hero.getY()-mob.getY())) || (hero.getX()==mob.getX() && mob.getY()<hero.getY()) || (hero.getX()>mob.getX() && hero.getY()>mob.getY() && (hero.getX()-mob.getX())<(hero.getY()-mob.getY()))) && playfield.ifPossibleToMove(mob.getX()/50,(mob.getY()+50)/50)) {
            mob.moveY(50);
        }

        if ((hero.getX()<mob.getX() && hero.getY()<mob.getY() && (mob.getX()-hero.getX())==(mob.getY()-hero.getY())) && playfield.ifPossibleToMove((mob.getX()-50)/50,(mob.getY()-50)/50)) {
            mob.moveX(-50);
            mob.moveY(-50);
        }
        if ((hero.getX()>mob.getX() && hero.getY()<mob.getY() && (hero.getX()-mob.getX())==(mob.getY()-hero.getY())) && playfield.ifPossibleToMove((mob.getX()+50)/50,(mob.getY()-50)/50)) {
            mob.moveX(50);
            mob.moveY(-50);
        }
        if ((hero.getX()<mob.getX() && hero.getY()>mob.getY() && (mob.getX()-hero.getX())==(hero.getY()-mob.getY())) && playfield.ifPossibleToMove((mob.getX()-50)/50,(mob.getY()+50)/50)) {
            mob.moveX(-50);
            mob.moveY(50);
        }
        if ((hero.getX()>mob.getX() && hero.getY()>mob.getY() && (hero.getX()-mob.getX())==(hero.getY()-mob.getY())) && playfield.ifPossibleToMove((mob.getX()+50)/50,(mob.getY()+50)/50)) {
            mob.moveX(50);
            mob.moveY(50);
        }
    }
}
