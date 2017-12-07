package network;

import controllers.Controller;

import static emoticons.Constants.*;

public class Neuron {

    /**
     * Weights
     * Defining weights for each input x + bias
     */
    protected double[] weights;

    /**
     * Neuron Inputs
     */
    private double[] inputs;

    /**
     * Evaluated Neuron Output
     */
    private double calculated_output = 0.0;

    /**
     * Number of Inputs
     */
    private int number_inputs = 0;

    /**
     * Number of Weights = number_inputs + 1
     */
    private int number_weights = 0;

    /**
     * Testing output
     */
    private double test_y = 0.0;

    /**
     * Sigmoidal Rate
     */
    private double BETA = 0.5;

    /**
     * Handler for GUI
     */
    private Controller myController;

    /**
     * Neuron ID
     */
    private int id = 0;

    /**
     * Layer ID
     */
    private int layer_id;

    /**
     * Neuron Constructor
     */
    public Neuron(Controller controller, int number_inputs, int layer_id, int id) {

        this.myController = controller;

        this.number_inputs = number_inputs;

        this.inputs = new double[number_inputs];

        this.number_weights = number_inputs + 1;

        this.weights = new double[this.number_weights];

        this.id = id;

        this.layer_id = layer_id;

        this.init();
    }

    /**
     * Initialization
     */
    protected void init() {
        if (number_inputs > 0){
            for (int i = 0; i < this.number_weights; i++) {
                this.weights[i] = randomNumber(0, 1);
            }
            this.weights[number_weights-1] = 1.0;
        }else{
            System.out.println("\nNeuron init error!\n");
        }
    }

    /**
     * Output Evaluation
     */
    public double calculate(){
        double sum = 0.0;
        if(number_inputs > 0) {
            sum = sum_function();
        }
        this.calculated_output = activation_function_MCP(sum);

        return this.calculated_output;
    }

    /**
     * Test Output Evaluation
     */
    public double calculate_test(){
        double sum = sum_function();
        this.test_y = activation_function(sum);
        return this.test_y;
    }

    /**
     * Sum function
     */
    private double sum_function(){
        double sum = 0;

        //sum
        for (int i = 0; i < (this.number_weights-1); i++) {
            sum += inputs[i] * weights[i];
        }

        return sum;
    }

    /**
     * Sigmoidal Activation Function
     */
    private double activation_function_MCP(double sum){

        return ( 1 / ( 1 + Math.exp(-BETA * sum)));
    }

    /**
     * Step Activation Function
     */
    private int activation_function(double sum){

        int output;

        //assign results to adequate classes (0 or 1)
        if (sum >= THRESHOLD)
            output = 1;
        else
            output = 0;

        return output;
    }

    /**
     * Updating Weights
     */
    public void updateWeights(){
        //updating weights
        for (int i = 0; i < number_inputs; i++) {

            weights[i] = weights[i]*FORGET_RATE + LEARNING_RATE * calculated_output * inputs[i];

        }
    }

    /**
     * Setting Inputs
     */
    public void setInputs(double[] inputs){
        if(inputs.length == number_inputs) {
            for (int i = 0; i < this.number_inputs; i++) {
                this.inputs[i] = inputs[i];
            }
        }
    }

    /**
     * Getting Inputs
     */
    public double[] getInputs(){
        return this.inputs;
    }

    /**
     * Setting Calculated Output
     */
    public void setCalculatedOutput(double y){
        this.calculated_output = y;
    }

    /**
     * Getting Calculated Output
     */
    public double getCalculatedOutput(){
        return calculated_output;
    }

    /**
     * Getting Weights
     */
    public double[] getWeights() {
        return weights;
    }

    /**
     * Getting Weight with Index = i
     */
    public double getWeight(int i){ return weights[i]; }

    /**
     * Setting Weights
     */
    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    /**
     * Getting Weights Number
     */
    public int getNumber_weights() {
        return number_weights;
    }

    /**
     * Setting Weights Number
     */
    public void setNumber_weights(int number_weights) {
        this.number_weights = number_weights;
    }
}
