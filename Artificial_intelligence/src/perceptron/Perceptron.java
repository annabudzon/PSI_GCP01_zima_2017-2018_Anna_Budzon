package perceptron;

import controllers.Controller;
import set.Learning_Set;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Perceptron {

    private static double LEARNING_RATE = 0.001;

    private static int MAX_ITERATION = 800;

    // number of learning sets
    public static int NUM_X = 20;

    //number od weights + bias
    private static int NUM_WEIGHTS = 3;

    //number of testing sets
    private static int NUM_TESTS = 10;

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

        //calculated output
        int output = 0;

        int iteration = 0;

        //Reading data from file
        Learning_Set  set = load_file();

        myController.setText("\n----------------------");
        myController.setText("-------LEARNING-------\n");

        do { // one epoch -> looping once over all instances x
            iteration++;
            global_error = 0;

            for (int i = 0; i < NUM_X; i++) {

                // calculate value of output
                output = activation_function(set.getX1(i), set.getX2(i), weights);

                //checking the difference between actual result and calculated value of output
                error = set.getActualOutputs(i) - output;

                //updating weights and bias
                weights[0] += LEARNING_RATE * error * set.getX1(i);
                weights[1] += LEARNING_RATE * error * set.getX2(i);
                weights[2] += LEARNING_RATE * error;

                //verify if error is 0 ( actual output = calculated_value )
                global_error += (error * error);
            }



            // loop'll be over if actual output is equal calculated value or loop achieves maximum of iterations
        } while (global_error != 0 && iteration <= MAX_ITERATION);

        myController.setText("\nIteration of learning: " + iteration);

        myController.setText("\n\nActivation function: \n" +
                String.format("%.6f", weights[0]) + "*x1 +" +
                String.format("%.6f", weights[1]) + "*x2 +" +
                String.format("%.6f", weights[2]) + "\n");

        return weights;
    }

    private void testing(double[] weights) {

        Random a = new Random();

        //testing set
        int x1, x2, output;

        myController.setText("\n-------Testing-------\n");

        for(int i = 0; i < NUM_TESTS; i++) {

            myController.setText("---->  TEST "+(i+1)+"  <----");

            //draw of x values
            x1 = a.nextInt(2);
            x2 = a.nextInt(2);

            //calculating the outputs of logical operation OR, based on learnt weights
            output = activation_function(x1, x2, weights);

            myController.setText("\nRandom input:\t x1 = " + x1 + "\t\tx2 = " + x2);
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

    private Learning_Set load_file(){
        Learning_Set set = new Learning_Set();
        File file = new File("learning_set.txt");
        String line;
        int x1[] = new int[NUM_X];
        int x2[] = new int[NUM_X];
        int y[] = new int[NUM_X];
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String[] parts;
            while ((line = reader.readLine()) != null && i < NUM_X) {
                System.out.println(line);
                parts = line.split(" ");
                if (parts.length == 3) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            return null;
                        }
                    }
                }
                x1[i] = Integer.parseInt(parts[0]);
                x2[i] =Integer.parseInt(parts[1]);
                y[i] = Integer.parseInt(parts[2]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        set.setX1(x1);
        set.setX2(x2);
        set.setActual_outputs(y);

        return set;
    }
}
