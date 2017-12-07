package emoticons;

import static emoticons.Constants.*;

public class Emoticon {

    private double x[]; // input value of x (10x10)

    private  double actual_y[];

    private double calculated_y[];

    public Emoticon(){
        x = new double[NUM_INPUT_X];
        actual_y = new double[NUM_OUTPUTS];
        calculated_y = new double[NUM_OUTPUTS];

        for(int i =0; i< NUM_OUTPUTS; i++)
            calculated_y[i] = 1.0;
    }

    public double getX(int i){
        return x[i];
    }

    public double getActual_y(int i){
        return actual_y[i];
    }

    public void setX(int i, double value){
        this.x[i] = value;
    }

    public double[] getX() {
        return x;
    }

    public double[] getActual_y() {
        return actual_y;
    }

    public void setX(double[] x) {
        for(int i = 0; i < NUM_INPUT_X; i++ )
            this.x[i] = x[i];
    }

    public void setActual_y(double[] actual_y) {
        for(int i = 0; i < NUM_OUTPUTS; i++ )
            this.actual_y[i] = actual_y[i];
    }

    public void setCalculated_y(double[] y) {
        for(int i = 0; i < NUM_OUTPUTS; i++ )
            this.calculated_y[i] = y[i];
    }

    public double[] getCalculated_y() {
        return this.calculated_y;
    }

    public double getCalculated_y(int i) {
        return this.calculated_y[i];
    }

}
