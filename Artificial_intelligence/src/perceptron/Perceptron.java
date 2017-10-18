package perceptron;

import controllers.Controller;

import java.util.Random;

public class Perceptron {

    private static double LEARNING_RATE = 0.1;

    private static int MAX_ITERATION = 100;

    // number of possible way of proceeding
    private static int NUM_X = 4;

    //number od weights + bias
    private static int NUM_WEIGHTS = 3;

    private static int threshold = 0;

   private Controller myController;

    public Perceptron(Controller controller){
        this.myController = controller;
    }

    public void OR_operation() {

        myController.setText("****** OR logical function by perceptron\n");

        //defining weights for each input x
        double weights[] = new double[3];

        //variable determining difference between actual and calculated result
        double error = 0;

        //variable determining if the error reached the value of 0
        double global_error = 0;

        //initially weights have random value in the range from 0 to 1
        for (int i = 0; i < NUM_WEIGHTS; i++) {
            weights[i] = randomNumber(0, 1);
        }

        //learning
        weights = learning(weights, error, global_error);

        //testing
        testing(weights);
    }

    private double[] learning(double[] weights, double error, double global_error) {

        //training set of input data and actual results
        int x1[] = new int[NUM_X]; // input value of x1
        int x2[] = new int[NUM_X]; // input value of x2
        int actual_outputs[] = new int[NUM_X];

        //calculated output
        int output = 0;
        int iteration = 0;

        //defining training set
        x1[0] = 0;
        x2[0] = 0;
        actual_outputs[0] = 0;

        x1[1] = 1;
        x2[1] = 0;
        actual_outputs[1] = 1;

        x1[2] = 0;
        x2[2] = 1;
        actual_outputs[2] = 1;

        x1[3] = 1;
        x2[3] = 1;
        actual_outputs[3] = 1;

        myController.setText("\n----------------------");
        myController.setText("-------LEARNING-------\n");

        do { // one epoch -> looping once over all instances x
            iteration++;
            global_error = 0;

            for (int i = 0; i < NUM_X; i++) {

                // calculate value of output
                output = activation_function(x1[i], x2[i], weights);

                //checking the difference between actual result and calculated value of output
                error = actual_outputs[i] - output;

                //updating weights and bias
                weights[0] += LEARNING_RATE * error * x1[i];
                weights[1] += LEARNING_RATE * error * x2[i];
                weights[2] += LEARNING_RATE * error;

                //verify if error is 0 ( actual output = calculated_value )
                global_error += (error * error);
            }

            myController.setText("\nIteration of learning: " + iteration);

            // loop'll be over if actual output is equal calculated value or loop achieves maximum of iterations
        } while (global_error != 0 && iteration <= MAX_ITERATION);

        myController.setText("\n\nActivation function: \n" +
                                weights[0] + "*x1 +" +
                                weights[1] + "*x2 +" +
                                weights[2] + "\n");

        return weights;
    }

    private void testing(double[] weights) {

        Random a = new Random();

        //testing set
        int x1, x2, output;

        myController.setText("\n----------------------\n");
        myController.setText("-------Testing-------\n");

        for(int i = 0; i < 10; i++) {

            myController.setText("\n---->  TEST "+(i+1)+"  <----");

            //draw of x values
            x1 = a.nextInt(2);
            x2 = a.nextInt(2);

            //calculating the outputs of logical operation OR, based on learnt weights
            output = activation_function(x1, x2, weights);

            myController.setText("\nRandom input:\n x1 = " + x1 + "\t\tx2 = " + x2);
            myController.setText("\nOutput y = " + output+"\n");
        }

    }

    private int activation_function(int x1, int x2, double[] weights) {

        double sum = 0;
        int output = -1;

        //activation function
        sum = x1 * weights[0] + x2 * weights[1] + weights[2];

        //assign results to adequate classes (0 or 1)
        if (sum >= threshold)
            output = 1;
        else
            output = 0;

        return output;
    }

    private static double randomNumber(double min, double max) {

        return (min + Math.random() * (max - min));
    }
}
