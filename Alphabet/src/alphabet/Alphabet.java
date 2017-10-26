package alphabet;

import controllers.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static alphabet.Constants.*;

public class Alphabet {

    private Controller myController;

    public Alphabet(Controller controller) {
        this.myController = controller;
    }

    private static double randomNumber(double min, double max) {

        return (min + Math.random() * (max - min));
    }

    public void operation() {

        myController.setText("****** Defining lower and uppercases\n");

        //defining weights for each input x + bias
        double weights[] = new double[NUM_WEIGHTS];

        //variable determining if the error is reaching the value of 0, approximation (błąd średniokwadratowy)
        double error_MSE = 0.0;

        //błąd procentowy
        double error_MAPE = 0.0;

        //variable determining difference between actual and calculated result
        double delta = 0.0;

        //calculated output
        double output = 0;

        //initially weights have random value in the range from 0 to 1
        for (int i = 0; i < NUM_WEIGHTS; i++) {
            weights[i] = randomNumber(0, 1);
        }

        //learning
        weights = learning(weights, error_MSE, error_MAPE, delta, output);

        //testing
        testing(weights, output);
    }

    private double[] learning(double[] weights, double error_MSE, double error_MAPE, double delta, double output) {

        double sum = 0.0;

        int epoch = 0;

        int k = 0;

        //Reading data from file
        Letter[] letters = generateLearningSet();
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
                sum = sum_function(letters[i], weights);

                // calculate value of output - adaline
                //output = activation_function(sum);

                // calculate value of output - MCP
                output = activation_function_MCP(sum);

                //checking the difference between actual result and calculated value of output
                delta = letters[i].getActual_y() - output;

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
                error_MAPE += (Math.abs(delta)/output);
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

    private void testing(double[] weights, double output) {

        //testing set
        Letter letters[] = generateTestSet();

        double sum;

        myController.setText("\n-------Testing-------\n");

        for (int i = 0; i < NUM_TESTS; i++) {

            myController.setText("---->  TEST " + (i + 1) + "  <----\n");

            // sumator
            sum = sum_function(letters[i], weights);

            //calculating the outputs of differentiating upper and lowercases, based on learnt weights
            output = activation_function(sum);

            int k = 0;
            for (int m = 0; m < ROWS; m++) {
                for (int n = 0; n < COLUMNS; n++) {
                    if(k%4 == 0)
                        myController.setText("\n");
                    if (letters[i].getX(m, n) == 1)
                        myController.setText(" * ");
                    else
                        myController.setText("   ");
                    k++;
                }
            }
            if(output == LOWERCASE){
                myController.setText("\nLETTER IS LOWERCASE\n");
            }else{

                myController.setText("\nLETTER IS UPPERCASE\n");
            }
        }

    }

    private double sum_function(Letter letter, double[] weights) {

        double sum = 0;
        int k = 0;

        //sum
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (k < NUM_WEIGHTS) {
                    sum += letter.getX(i, j) * weights[k];
                }
                k++;
            }
        }

        //bias
        sum += weights[NUM_WEIGHTS - 1];

        return sum;
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

    private double activation_function_MCP(double sum){
         return ( 1 / ( 1 + Math.exp(-0.5 * sum)));
    }

    private Letter[] generateLearningSet() {

        Letter[] letters = new Letter[NUM_LETTERS];
        for(int i = 0; i < NUM_LETTERS; i++){
            letters[i] = new Letter();
        }
        //Letter tmp = new Letter();

        File file = new File("learning_set.txt");
        String line;
        int k = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String[] parts;
            while ((line = reader.readLine()) != null && k < NUM_LETTERS) {
                System.out.println(line);
                parts = line.split(" ");
                if (parts.length == 25) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            return null;
                        }
                    }
                }
                int l = 0;
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        letters[k].setX(i, j, Integer.parseInt(parts[l]));
                        l++;
                    }
                }

                letters[k].setActual_y(Integer.parseInt(parts[24]));
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return letters;
    }

    private Letter[] generateTestSet() {
        Letter[] letters = new Letter[NUM_TESTS];
        for(int i = 0; i < NUM_TESTS; i++){
            letters[i] = new Letter();
        }

        File file = new File("tests.txt");
        String line;
        int k = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String[] parts;
            while ((line = reader.readLine()) != null && k < NUM_TESTS) {
                parts = line.split(" ");
                if (parts.length == 24) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            return null;
                        }
                    }
                }
                int l = 0;
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        letters[k].setX(i, j, Integer.parseInt(parts[l]));
                        l++;
                    }
                }

                letters[k].setActual_y(-1);
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return letters;
    }
}
