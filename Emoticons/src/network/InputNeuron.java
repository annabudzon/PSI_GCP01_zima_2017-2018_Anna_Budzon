package network;

import controllers.Controller;

public class InputNeuron extends Neuron {

    /**
     * InputNeuron constructor
     */
    public InputNeuron(Controller controller, int number_inputs, int layer_id, int id){
        super(controller, number_inputs, layer_id, id);
    }

    /**
     * init
     */
    @Override
    public void init(){
        for(int i = 0; i < 100; i++) {
            this.weights[i] = 1.0;
        }
    }



}
