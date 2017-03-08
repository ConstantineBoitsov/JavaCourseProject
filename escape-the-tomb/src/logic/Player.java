package logic;

import GUI.Sprite;

import java.awt.*;
import javax.swing.ImageIcon;

/**
 * Created by Константин on 05.03.2017.
 */
public class Player extends Sprite {


    private Image image;

//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }

//    public Player(int x,int y,Image image) {
//        this.x = x;
//        this.y = y;
//        this.image = image;
//    }

    public Player(int x, int y, String path) {
        super(x, y);

        initPlayer(path);
    }

    private void initPlayer(String path) {

        loadImage(path);
        getImageDimensions();
    }

//    public int getWidth() {
//        return image.getWidth(null);
//    }
//
//    public int getHeight() {
//        return image.getHeight(null);
//    }

    public void draw(Graphics g,int x,int y) {
        g.drawImage(image,x,y,null);
    }

//    public Image getImage() {
//        return image;
//    }

    public void moveX(int x1) {
        x += x1;
    }

    public void moveY(int y1) {
        y += y1;
    }
}
