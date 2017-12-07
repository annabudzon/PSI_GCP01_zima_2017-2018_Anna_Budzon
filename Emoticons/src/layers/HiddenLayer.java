package layers;

import controllers.Controller;

public class HiddenLayer extends NeuralLayer {

    /**
     * HiddenLayer constructor
     */
    public HiddenLayer(Controller controller, int number_inputs, int number_neurons, int layer_id){
        super(controller, number_inputs, number_neurons, layer_id);
        init();
    }

    /**
     * setPreviousLayer
     */
    @Override
    public void setPreviousLayer(NeuralLayer previous){
        this.previous = previous;
        if(previous.next != this)
            previous.setNextLayer(this);
    }

    /**
     * setPreviousLayer
     */
    @Override
    public NeuralLayer getPreviousLayer(){
        return this.previous;
    }

    /**
     * setNextLayer
     */
    @Override
    public void setNextLayer(NeuralLayer next){
        this.next = next;
        if(next.previous != this)
            next.setPreviousLayer(this);
    }

    /**
     * Setting Inputs
     */
    @Override
    public void setInputs(double[] inputs){

        this.number_inputs = inputs.length;
        for(int i = 0; i < inputs.length; i++){
            this.inputs[i] = inputs[i];
        }
    }
}
