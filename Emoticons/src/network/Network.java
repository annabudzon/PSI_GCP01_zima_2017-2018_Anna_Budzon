package network;

import controllers.Controller;
import emoticons.Emoticon;
import layers.HiddenLayer;
import layers.InputLayer;
import layers.OutputLayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static emoticons.Constants.*;

public class Network {

    public enum LayerType{ I, H, O}

    private Controller controller;

    private HiddenLayer[] hidden_layers;

    private InputLayer input_layer;

    private OutputLayer output_layer;

    private int number_hidden_layers;

    private int number_inputs;

    private int number_outputs;

    private int[] hidden_neurons;

    //variable determining if the error is reaching the value of 0, approximation (błąd średniokwadratowy)
    private double error_MSE;

    //percentage error
    private double error_MAPE;

    //variable determining difference between actual and calculated result
    private double delta;

    private double[] signal_y;

    private Emoticon[] emoticons;

    //private double[][] training_inputs;

    //private double[][] actual_outputs;

    private Emoticon[] test_emoticons;

    /**
     * Sigmoidal Rate
     */
    private double BETA = 0.5;

    public Network(Controller controller){

        this.controller = controller;

        this.number_hidden_layers = NUM_HIDDEN_LAYERS; // 0

        this.number_inputs = NUM_INPUT_X;

        this.number_outputs = NUM_OUTPUTS;

        this.hidden_neurons = new int[this.number_hidden_layers]; // 0

        //this.hidden_layers = new HiddenLayer[number_hidden_layers];

        this.input_layer = new InputLayer(this.controller, number_inputs, 100, 0);

         if(number_hidden_layers > 0) {
              this.output_layer = new OutputLayer(this.controller, hidden_neurons[number_hidden_layers-1], number_outputs,NUM_LAYERS-1);
        }else{
             this.output_layer = new OutputLayer(this.controller, number_inputs, number_outputs, NUM_LAYERS-1);
         }
        /*
        for(int i = 0; i < number_hidden_layers; i++){
            if(i == 0){
                hidden_layers[i] = new HiddenLayer(this.controller, number_inputs, hidden_neurons[i], 1);
                hidden_layers[i].setPreviousLayer(input_layer);
            }else {
                hidden_layers[i] = new HiddenLayer(this.controller, hidden_neurons[i - 1], hidden_neurons[i],(1+i));
                hidden_layers[i-1].setNextLayer(hidden_layers[i]);
                hidden_layers[i].setPreviousLayer(hidden_layers[i-1]);
            }
        }*/

        if(number_hidden_layers>0) {
            input_layer.setNextLayer(hidden_layers[0]);
            hidden_layers[number_hidden_layers - 1].setNextLayer(output_layer);
            output_layer.setPreviousLayer(hidden_layers[number_hidden_layers - 1]);
        }else{
            input_layer.setNextLayer(output_layer);
            output_layer.setPreviousLayer(input_layer);
        }

        this.error_MSE = 0.0;
        this.error_MAPE = 0.0;
        this.delta = 0.0;

        this.generateTrainingSet();
        this.generateTestSet();

    }

    private void calculate(int i){
        input_layer.setInputs(this.emoticons[i].getX());
        input_layer.calculate();

        output_layer.setInputs(output_layer.getPreviousLayer().getOutputs());
        output_layer.calculate();
        this.emoticons[i].setCalculated_y(output_layer.getOutput());
    }

    private void calculate_test(int i){
        input_layer.setInputs(this.test_emoticons[i].getX());
        input_layer.calculate_test();

        output_layer.setInputs(output_layer.getPreviousLayer().getOutputs());
        output_layer.calculate_test();
        this.test_emoticons[i].setCalculated_y(output_layer.getOutput());
    }

    private void updateWeights(){

        input_layer.updateWeights();
    }

    public void learn() {

        int epoch = 0;

        double temp = 0.0;

        double localError = 0.0;

        double globalError = 0.0;

        controller.setText("\n----------------------");
        controller.setText("-------LEARNING-------\n");

        do { // one epoch -> looping once over all instances x

            epoch++;
            error_MSE = 0.0;
            error_MAPE = 0.0;

            for (int i = 0; i < NUM_LEARNING; i++) {

                globalError = 0.0;

                for(int j = 0; j < NUM_OUTPUTS; j++) {

                    temp = emoticons[i].getCalculated_y(j);

                    this.calculate(i);

                    this.updateWeights();

                    this.delta = temp - this.emoticons[i].getCalculated_y(j);

                    if(localError == Math.abs(this.delta)) break;
                    localError = Math.abs(this.delta);
                    globalError = globalError + (localError*localError);

                }

                //defining errors
                this.error_MSE += (globalError * globalError);
                this.error_MSE /= NUM_LEARNING;
                this.error_MAPE += (Math.abs(globalError*10));
                this.error_MAPE /= NUM_LEARNING;
            }
            System.out.println("--------------------------"+error_MSE +"\tLiczba epok: "+ epoch);

        } while (this.error_MSE != 0 && epoch <= MAX_ITERATION);

        controller.setText("\nEpoch: " + epoch);
        controller.setText("\nMSE: "+ error_MSE);
        controller.setText("\nMAPE: "+ error_MAPE);
    }

    public void test() {

        this.generateTestSet();

        this.controller.setText("\n-------Testing-------\n");

        for (int i = 0; i < NUM_TESTS; i++) {

            controller.setText("\n\n---->  TEST " + (i + 1) + "  <----\n");

            this.calculate_test(i);

            int k = 0;
            for (int m = 0; m < DIM*DIM; m++) {
                    if(k%10 == 0)
                        this.controller.setText("\n");
                    if (test_emoticons[i].getX(m) == 1)
                        this.controller.setText(" * ");
                    else
                        this.controller.setText("    ");
                    k++;
            }

            controller.setText("\nOUTPUT: \n");
            for(int n = 0; n < NUM_OUTPUTS; n++){
                controller.setText(String.valueOf(test_emoticons[n].getCalculated_y(i)+"    "));
            }
            controller.setText("\n");

            if(test_emoticons[i].getCalculated_y() == emoticons[i].getActual_y()){
                controller.setText("\nTo jest emotikona numer "+i+"!\n");
            }else{
                controller.setText("\nNie rozpoznalem emotikkony :((\n");
            }


        }
    }

    private void generateTrainingSet(){
        System.out.println("PRINT TRAINING SET!\n");
        emoticons = new Emoticon[NUM_LEARNING];
        for(int i = 0; i < NUM_LEARNING; i++){
            emoticons[i] = new Emoticon();
        }
        double[] y = new double[NUM_LEARNING];
        double[] x = new double[this.number_inputs];

        File file = new File("dataset.txt");
        String line;
        int k = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String[] parts;
            while ((line = reader.readLine()) != null && k < NUM_LEARNING) {
                parts = line.split(";");
                if (parts.length == 106) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            System.out.println("FILE IS EMPTY!\n");
                            return;
                        }
                    }
                }

                for (int i = 0; i < 100; i++) {
                        x[i] = Double.parseDouble(parts[i]);
                }

                int n = 0;
                for(int i = 100; i < 106; i++){
                    y[n] = Double.parseDouble(parts[i]);
                    n++;
                }

                emoticons[k].setX(x);
                emoticons[k].setActual_y(y);

                controller.setText("Emotikon "+ k + ": ");
                 int s = 0;
                for(int l = 0; l< 6; l++){
                    controller.setText(String.valueOf(emoticons[k].getActual_y(l))+'\t');
            }
                controller.setText("\n");
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.setText("\n");

    }

    private void generateTestSet(){
        System.out.println("PRINT TESTING SET!\n");
        this.test_emoticons = new Emoticon[NUM_LEARNING];
        for(int i = 0; i < NUM_TESTS; i++){
            test_emoticons[i] = new Emoticon();
        }

        double[] x = new double[this.number_inputs];

        File file = new File("testset.txt");
        String line;
        int k = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String[] parts;
            while ((line = reader.readLine()) != null && k < NUM_TESTS) {
                parts = line.split(";");
                if (parts.length == 100) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            System.out.println("FILE IS EMPTY!\n");
                            return;
                        }
                    }
                }

                for (int i = 0; i < 100; i++) {
                    x[i] = Double.parseDouble(parts[i]);
                }

                test_emoticons[k].setX(x);
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n");
    }
}


