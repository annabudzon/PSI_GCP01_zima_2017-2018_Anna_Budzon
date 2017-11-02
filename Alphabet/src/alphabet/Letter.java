package alphabet;


import static alphabet.Constants.COLUMNS;
import static alphabet.Constants.NUM_WEIGHTS;
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

    public double sum_function(double[] weights){
        double sum = 0;
        int k = 0;

        //sum
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (k < NUM_WEIGHTS) {
                    sum += x[i][j] * weights[k];
                }
                k++;
            }
        }

        //bias
        sum += weights[NUM_WEIGHTS - 1];

        return sum;
    }
}
