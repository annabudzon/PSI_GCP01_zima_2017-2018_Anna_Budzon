package network;

import controllers.Controller;
import layers.HiddenLayer;
import layers.InputLayer;
import layers.OutputLayer;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static rastrigin.Constants.*;

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

    private double[][] training_inputs;

    private double[] actual_outputs;

    private double[] normalized_outputs;

    //calculated output
    private double output;

    private double[] calculated_outputs;

    private double average = 0.0;

    private double standard = 0.0;

    /**
     * Sigmoidal Rate
     */
    private double BETA = 0.5;

    public Network(Controller controller){

        this.controller = controller;

        this.number_hidden_layers = NUM_HIDDEN_LAYERS;

        this.number_inputs = NUM_INPUT_X;

        this.number_outputs = NUM_OUTPUT_NEURONS;

        this.hidden_neurons = new int[this.number_hidden_layers];

        this.hidden_neurons[0] = NUM_HIDDEN_NEURONS_1;

        this.hidden_neurons[1] = NUM_HIDDEN_NEURONS_2;

        this.hidden_layers = new HiddenLayer[number_hidden_layers];

        this.input_layer = new InputLayer(this.controller, number_inputs, 2, 0);

         if(number_hidden_layers > 0) {
              this.output_layer = new OutputLayer(this.controller, hidden_neurons[number_hidden_layers-1], number_outputs,NUM_LAYERS-1);
        }else{
             this.output_layer = new OutputLayer(this.controller, number_inputs, number_outputs, NUM_LAYERS-1);
         }

        for(int i = 0; i < number_hidden_layers; i++){
            if(i == 0){
                hidden_layers[i] = new HiddenLayer(this.controller, number_inputs, hidden_neurons[i], 1);
                hidden_layers[i].setPreviousLayer(input_layer);
            }else {
                hidden_layers[i] = new HiddenLayer(this.controller, hidden_neurons[i - 1], hidden_neurons[i],(1+i));
                hidden_layers[i-1].setNextLayer(hidden_layers[i]);
                hidden_layers[i].setPreviousLayer(hidden_layers[i-1]);
            }
        }

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
        this.output = 0.0;

        this.calculated_outputs = new double[NUM_LEARNING];

        this.generateTrainingSet();
    }

    private void calculate(int i){
        this.output = 0;
        input_layer.setInputs(this.training_inputs[i]);
        input_layer.calculate();

        for(int j = 0; j < number_hidden_layers; j++){
            hidden_layers[j].setInputs(hidden_layers[j].getPreviousLayer().getOutputs());
            hidden_layers[j].calculate();
        }

        output_layer.setInputs(output_layer.getPreviousLayer().getOutputs());
        output_layer.calculate();
        this.output = output_layer.getOutput();
    }

    private void calculate_test(int i){
        this.output = 0;
        input_layer.setInputs(this.training_inputs[i]);
        input_layer.calculate_test();

        for(int j = 0; j < number_hidden_layers; j++){
            hidden_layers[j].setInputs(hidden_layers[j].getPreviousLayer().getOutputs());
            hidden_layers[j].calculate_test();
        }
        output_layer.setInputs(output_layer.getPreviousLayer().getOutputs());
        output_layer.calculate_test();
        this.calculated_outputs[i] = output_layer.getOutput();
    }

    private void updateWeights(){

        //output_layer.updateWeights(this.delta);

        output_layer.updateDeltas(this.delta);

        input_layer.updateWeights();

    }

    public void learn() {

        int epoch = 0;

        controller.setText("\n----------------------");
        controller.setText("-------LEARNING-------\n");

        do { // one epoch -> looping once over all instances x
            System.out.println("Poczatek petli");
            epoch++;
            error_MSE = 0.0;
            error_MAPE = 0.0;

            for (int i = 0; i < NUM_LEARNING; i++) {

                this.calculate(i);

                //checking the difference between actual result and calculated value of output
                this.delta = (this.normalized_outputs[i] - this.output);

                System.out.println("DELTA: " + this.normalized_outputs[i] + " - " + this.output +" = " + delta);

                this.calculated_outputs[i] = (this.output*this.standard)+this.average;

                this.updateWeights();


                //defining errors
                this.error_MSE += (1.0/NUM_LEARNING)*(this.delta*this.delta);
                this.error_MAPE += (Math.abs(this.delta)/output);
                this.error_MSE /= NUM_LEARNING;
                this.error_MAPE /= NUM_LEARNING;
            }
            System.out.println("--------------------------"+error_MSE +"\tLiczba epok: "+ epoch);
            // loop'll be over if calculated output is approximation of actual value or loop achieves maximum of iterations
        } while (this.error_MSE > 0.000001 && epoch <= MAX_ITERATION);

        controller.setText("\nEpoch: " + epoch);
        controller.setText("\nMSE: "+ error_MSE);
        controller.setText("\nMAPE: "+ error_MAPE);
        for(int i = 0; i < NUM_LEARNING; i++) {
            controller.setText("\nActual output: " + this.actual_outputs[i]+" -------------- Calculated output: "+ calculated_outputs[i] );
        }
    }

    public void test() {

        this.controller.setText("\n-------Testing-------\n");

        for (int i = 0; i < NUM_TESTS; i++) {

            controller.setText("\n\n---->  TEST " + (i + 1) + "  <----\n");

            this.calculate_test(i);

            controller.setText("\nOUTPUT: "+ this.calculated_outputs[i] + "\t Delta: "+(this.actual_outputs[i] - this.calculated_outputs[i]));

        }
    }

    private void generateTrainingSet(){
        System.out.println("PRINT TRAINING SET!\n");
        this.training_inputs = new double[NUM_LEARNING][this.number_inputs];
        this.actual_outputs = new double[NUM_LEARNING];
        this.normalized_outputs = new double[NUM_LEARNING];
        double[] outputs = new double[NUM_LEARNING];
        double[] x = new double[this.number_inputs];

        for(int j = 0; j < NUM_LEARNING; j++) {
            System.out.println("Set "+ j + ": ");
            for(int i = 0; i < this.number_inputs; i++){
                x[i] = randomNumber(-2, 2);
                this.training_inputs[j][i] = ((x[i]+2)/4)*(-1)+1;
                System.out.println("x"+(i+1)+": "+ this.training_inputs[j][i] + "  ");
            }

            outputs[j] = rastrigin(x[0], x[1]);
            this.actual_outputs[j] = outputs[j];
            average += outputs[j];
            System.out.println("actual output: "+ this.actual_outputs[j] + "\n");
            System.out.println("\n");
            //this.actual_outputs[j] = activation_function_MCP(this.actual_outputs[j]);
        }

        average = average/NUM_LEARNING;

        for(int i = 0; i < NUM_LEARNING; i++){
            standard += (outputs[i]-average)*(outputs[i]-average);
        }

        standard = Math.sqrt(standard);

        System.out.println("average: "+ average +" --- standard: "+ standard + "\n");
        for(int i = 0; i < NUM_LEARNING; i++){
            this.normalized_outputs[i] = ((outputs[i] - average)/standard);
            System.out.println("normalized output: "+ this.normalized_outputs[i] + "\n");
        }

        System.out.println("\n");
    }

    private double rastrigin(double x1, double x2){
        double output = A*this.number_inputs;
        output += x1*x1 - A*cos(2*PI)*x1;
        output += x2*x2 - A*cos(2*PI)*x2;

        return output;
    }

}


