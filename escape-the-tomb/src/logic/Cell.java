package logic;

/**
 * Created by Константин on 05.03.2017.
 */
public class Cell {
    private int x;
    private int y;
    private boolean isBlocked;
    private boolean isExit;

    public Cell(int x, int y, boolean isBlocked, boolean isExit) {
        this.x = x;
        this.y = y;
        this.isBlocked = isBlocked;
        this.isExit = isExit;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean getIsBlocked() {
        return isBlocked;
    }
    public boolean getIsExit() {
        return isExit;
    }
}
