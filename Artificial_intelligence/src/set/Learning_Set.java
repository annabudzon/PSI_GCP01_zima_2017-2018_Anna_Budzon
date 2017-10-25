package set;

import static perceptron.Perceptron.NUM_X;

public class Learning_Set {

    //training set of input data and actual results
    private int x1[]; // input value of x1
    private int x2[]; // input value of x2
    private int actual_outputs[];

    public Learning_Set(){
        x1 = new int[NUM_X];
        x2 = new int[NUM_X];
        actual_outputs = new int[NUM_X];
    }

    public int getX1(int i){
        return x1[i];
    }

    public int getX2(int i){
        return x2[i];
    }

    public int getActualOutputs(int i){
        return actual_outputs[i];
    }

    public int[] getX1() {
        return x1;
    }

    public void setX1(int[] x1) {
        this.x1 = x1;
    }

    public int[] getX2() {
        return x2;
    }

    public void setX2(int[] x2) {
        this.x2 = x2;
    }

    public int[] getActual_outputs() {
        return actual_outputs;
    }

    public void setActual_outputs(int[] actual_outputs) {
        this.actual_outputs = actual_outputs;
    }
}
