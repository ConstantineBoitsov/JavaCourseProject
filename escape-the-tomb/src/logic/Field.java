package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Константин on 05.03.2017.
 */
public class Field {
    private int x;
    private int y;
    private int field[][] = new int[10][10];
    private Cell[][] cells = new Cell[10][10];

    public int getHorizontal() {
        return field.length;
    }
    public int getVertical() {
        return field[0].length;
    }

    public Field (String path) {
        try {
            Scanner sc = new Scanner(new File(path));
            for (int i = 0; i<10; i++) {
                for (int j = 0; j<10; j++) {
                    field[i][j] = sc.nextInt();

                    if (field[i][j] == 0) {
                        cells[i][j] = new Cell(i,j,false,false);
                    }
                    if (field[i][j] == 1) {
                        cells[i][j] = new Cell(i,j,true,false);
                    }
                    if (field[i][j] == 2) {
                        cells[i][j] = new Cell(i,j,false,true);
                    }
                }
            }
        }catch (FileNotFoundException exException) {
            System.out.println("File not found");
        }
    }

    public int[][] getField() {
        return field;
    }

    public boolean ifPossibleToMove(int x,int y) {
        return (!cells[x][x].getIsBlocked());
    }
}
