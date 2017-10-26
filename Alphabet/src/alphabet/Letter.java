package alphabet;


import static alphabet.Constants.COLUMNS;
import static alphabet.Constants.ROWS;

public class Letter {

    //training set of input data and actual results
    private int x[][]; // input value of x (4x6)

    private  int actual_y;

    public Letter(){
        x = new int[ROWS][COLUMNS];
    }

    public int getX(int i, int j){
        return x[i][j];
    }

    public void setX(int i, int j, int value){
        this.x[i][j] = value;
    }

    public int[][] getX() {
        return x;
    }

    public int getActual_y() {
        return actual_y;
    }

    public void setX(int[][] x) {
        this.x = x;
    }

    public void setActual_y(int actual_y) {
        this.actual_y = actual_y;
    }
}
