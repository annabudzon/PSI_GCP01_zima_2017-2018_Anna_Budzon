package network;

import controllers.Controller;

public class InputNeuron extends SigmoidalNeuron {

    /**
     * InputNeuron constructor
     */
    public InputNeuron(Controller controller, int number_inputs, int layer_id, int id){
        super(controller, number_inputs, layer_id, id);
    }

    /**
     * init
     * Method for initialization of the input neuron, it just adds the weights
     * with 1's values and a 0 at the bias
     */
    @Override
    public void init(){
            this.weights[0] = 1.0;
            this.weights[1] = 1.0;
    }



}
