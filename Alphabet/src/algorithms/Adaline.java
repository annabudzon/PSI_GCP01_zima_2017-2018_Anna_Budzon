package algorithms;

import alphabet.Letter;
import alphabet.Sets;
import controllers.Controller;

import static alphabet.Constants.*;

public class Adaline {

    //defining weights for each input x + bias
    private double[] weights;

    //variable determining if the error is reaching the value of 0, approximation (błąd średniokwadratowy)
    private double error_MSE;

    //percentage error
    private double error_MAPE;

    //variable determining difference between actual and calculated result
    private double delta;

    //calculated output
    private double output;

    private Controller myController;

    public Adaline(double[] weights, Controller myController) {

        this.myController = myController;
        this.weights = new double[NUM_WEIGHTS];
        for (int i = 0; i < NUM_WEIGHTS; i++) {
            this.weights[i] = weights[i];
        }
        this.error_MSE = 0.0;
        this.error_MAPE = 0.0;
        this.delta = 0.0;
        this.output = 0;

    }

    public double[] learning() {

        weights[NUM_WEIGHTS-1] = 1;

        double sum = 0.0;

        int epoch = 0;

        int k = 0;

        Sets set = new Sets();

        //Reading data from file
        Letter[] letters = set.generateLearningSet();
        if (letters == null) {
            System.out.println("LoadFile error!");
            return null;
        }

        myController.setText("\n----------------------");
        myController.setText("-------LEARNING-------\n");

        do { // one epoch -> looping once over all instances x

            epoch++;
            error_MSE = 0.0;
            error_MAPE = 0.0;

            for (int i = 0; i < NUM_LETTERS; i++) {

                // calculating sum of x*w(x)
                sum = letters[i].sum_function(weights);

                // calculate value of output - adaline
                output = activation_function(sum);

                //checking the difference between actual result and calculated value of output
                delta = letters[i].getActual_y() - sum;

                System.out.println(delta);

                k = 0;

                //updating weights
                for (int m = 0; m < ROWS; m++) {
                    for (int n = 0; n < COLUMNS; n++) {
                        weights[k] += LEARNING_RATE * delta * letters[i].getX(m, n);
                        k++;
                    }
                }

                //updating bias
                weights[NUM_WEIGHTS - 1] += LEARNING_RATE * delta;

                //defining errors
                error_MSE += (delta*delta);
                error_MAPE += (Math.abs(delta)/sum);
                error_MSE /= NUM_X;
                error_MAPE /= NUM_X;
            }

            // loop'll be over if calculated output is approximation of actual value or loop achieves maximum of iterations
        } while (error_MSE > 0.00000001 && epoch <= MAX_ITERATION);

        myController.setText("\nEpoch: " + epoch);
        myController.setText("\nMSE: "+ error_MSE);
        myController.setText("\nMAPE: "+ error_MAPE);
        myController.setText("\n\nActivation function: \n");
        for (int i = 0; i < NUM_WEIGHTS - 1; i++) {
            myController.setText(String.format("%.6f", weights[i]) + "*x + ");
        }
        myController.setText(String.format("%.6f", weights[NUM_WEIGHTS - 1]) + "\n");

        return weights;
    }

    private int activation_function(double sum){

        int output;

        //assign results to adequate classes (0 or 1)
        if (sum >= THRESHOLD)
            output = LOWERCASE;
        else
            output = UPPERCASE;

        return output;
    }
}
