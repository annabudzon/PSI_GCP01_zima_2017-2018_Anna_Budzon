package layers;

import controllers.Controller;
import network.Neuron;

import static emoticons.Constants.NUM_TESTS;

public class NeuralLayer {

    /**
     * Number of Neurons in this Layer
     */
    protected int number_neurons;
    /**
     * List of Neurons of this Layer
     */
    protected Neuron[] neurons;

    /**
     * Previous Layer that feeds values to this Layer
     */
    protected NeuralLayer previous;
    /**
     * Next Layer which this Layer will feed values to
     */
    protected NeuralLayer next;

    /**
     * List of input values that are fed to this Layer
     */
    protected double[] inputs;
    /**
     * List of output values this Layer will produce
     */
    protected double[] outputs;

    /**
     * Delta
     */
    protected double[] deltas;

    /**
     * Number of Inputs this Layer can receive
     */
    protected int number_inputs;;

    /**
     * Layer ID
     */
    protected int layer_id = 0;

    /**
     * Testing Outputs
     */
    protected double[] test_outputs;

    /**
     * Handler for GUI
     */
    protected Controller controller;

    /**
     * NeuralLayer Constructor
     */
    public NeuralLayer(Controller controller, int number_inputs, int number_neurons, int layer_id){

        this.controller = controller;

        this.layer_id = layer_id;

        this.number_inputs = number_inputs;

        this.number_neurons = number_neurons;

        this.inputs = new double[this.number_inputs];

        this.outputs =  new double[this.number_neurons];

        this.test_outputs = new double[NUM_TESTS];

        this.deltas = new double[this.number_neurons];
    }

    /**
     * Setting Next Layer
     */
    protected void setNextLayer(NeuralLayer next){
        this.next = next;
    }

    /**
     * Setting Previous Layer
     */
    protected void setPreviousLayer(NeuralLayer previous){
        this.previous = previous;
    }

    /**
     * Getting Next Layer
     */
    protected NeuralLayer getNextLayer(){
        return this.next;
    }

    /**
     * Getting Previous Layer
     */
    protected NeuralLayer getPreviousLayer(){
        return this.previous;
    }

    /**
     * Setting Inputs
     */
    protected void setInputs(double[] inputs){

        this.number_inputs = inputs.length;
        for(int i = 0; i < inputs.length; i++){
            this.inputs[i] = inputs[i];
        }
    }

    /**
     * Getting Outputs
     */
    public double[] getOutputs(){
        return this.outputs;
    }

    /**
     * Getting Number of Neurons in Layer
     */
    public int getNumberOfNeurons(){
        return number_neurons;
    }

    /**
     * Getting List of Neurons
     */
    public Neuron[] getNeurons(){
        return neurons;
    }

    /**
     * Getting Specified Neuron with Certain ID
     */
    protected Neuron getNeuron(int i){
        return neurons[i];
    }

    /**
     * Setting Specified Neuron with Certain ID
     */
    protected void setNeuron(int i, Neuron neuron){

        this.neurons[i] = neuron;

    }

    /**
     * Initialization
     */
    protected void init(){
        if(number_neurons >= 0){
            neurons = new Neuron[this.number_neurons];
            for(int i = 0; i < this.number_neurons; i++){
                neurons[i] = new Neuron(this.controller, number_inputs, this.layer_id, i);
            }
        }
    }

    /**
     * Output Evaluation
     */
    public void calculate(){
        if(inputs!=null && neurons!=null) {
            for (int i = 0; i < number_neurons; i++) {
                    neurons[i].setInputs(this.inputs);
                    this.outputs[i] = neurons[i].calculate();
            }
        }else{

            System.out.println("CalculateLayer Problem!");
        }
    }

    /**
     * Output Evaluation for Test
     */
    public void calculate_test(){

        if(inputs!=null && neurons!=null) {
            for (int i = 0; i < number_neurons; i++) {
                neurons[i].setInputs(this.inputs);
                this.test_outputs[i] = neurons[i].calculate_test();
            }
        }
    }

    public void updateWeights(){}

}
