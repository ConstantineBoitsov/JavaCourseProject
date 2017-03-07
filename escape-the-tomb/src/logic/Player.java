package logic;

import java.awt.*;

/**
 * Created by Константин on 05.03.2017.
 */
public class Player {
    private int x;
    private int y;

    private Image image;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player(int x,int y,Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public void draw(Graphics g,int x,int y) {
        g.drawImage(image,x,y,null);
    }

    public void moveX(int x) {
        this.x = x;
    }

    public void moveY(int y) {
        this.y = y;
    }
}
