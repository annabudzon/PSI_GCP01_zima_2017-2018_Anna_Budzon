package layers;

import controllers.Controller;
import network.InputNeuron;

public class InputLayer extends NeuralLayer {

    /**
     * InputLayer Constructor
     */
    public InputLayer(Controller controller, int number_inputs, int number_neurons, int layer_id){
        super(controller, number_inputs, number_neurons, layer_id);
        this.previous = null;
        init();
    }

    /**
     * setNextLayer
     */
    @Override
    public void setNextLayer(NeuralLayer layer){
        this.next = layer;
        if(layer.previous != this)
            layer.setPreviousLayer(this);
    }

    /**
     * setPreviousLayer
     */
    @Override
    public void setPreviousLayer(NeuralLayer layer){
        previous = null;
    }

    /**
     * init
     */
    @Override
    public void init(){
        if(number_neurons >= 0){
            neurons = new InputNeuron[this.number_neurons];
            for(int i = 0; i < this.number_neurons; i++){
                neurons[i] = new InputNeuron(this.controller, number_inputs, this.layer_id, i);
            }
        }
    }

    /**
     * setInputs
     */
    @Override
    public void setInputs(double[] inputs){
        if(inputs.length == number_inputs){
            for(int i = 0; i < inputs.length; i++){
                this.inputs[i] = inputs[i];
            }
        }
    }

    /**
     * Evaluation
     */
    @Override
    public void calculate(){
        if(inputs != null && neurons != null){
            for(int i = 0; i < number_neurons; i++){
                neurons[i].setInputs(this.inputs);
                this.outputs[i] = neurons[i].calculate();
            }
        }
    }

    /**
     * Evaluation for test
     */
    @Override
    public void calculate_test(){
        if(inputs != null && neurons != null){
            for(int i = 0; i < number_neurons; i++){
                neurons[i].setInputs(this.inputs);
                this.outputs[i] = neurons[i].calculate();
            }
        }
    }


    @Override
    public void updateWeights(){
        for(int i = 0; i < number_neurons; i++){
            neurons[i].updateWeights();
        }

        next.updateWeights();
    }


}
