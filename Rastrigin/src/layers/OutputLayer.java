package layers;

import controllers.Controller;
import network.Network.LayerType;

public class OutputLayer extends NeuralLayer {

    private LayerType type = LayerType.O;

    private double delta;

    /**
     * OutputLayer constructor
     *
     */
    public OutputLayer(Controller controller, int number_inputs, int number_neurons, int layer_id){
        super(controller, number_inputs, number_neurons, layer_id);
        next = null;
        init();
    }

    /**
     * setNextLayer
     * This method prevents any attempt to link this layer to a next one,
     * provided that this should be always the last
     */
    @Override
    public void setNextLayer(NeuralLayer layer){
        next = null;
    }

    /**
     * setPreviousLayer
     */
    @Override
    public NeuralLayer getPreviousLayer(){
        return this.previous;
    }

    /**
     * setPreviousLayer
     * This method links this layer to the previous one
     */
    @Override
    public void setPreviousLayer(NeuralLayer layer){
        previous = layer;
        if(layer.next!=this)
            layer.setNextLayer(this);
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

    /**
     * Getting Final Output
     */
    public double getOutput(){
        return this.outputs[0];
    }

    /**
     * Updating Weights for Only One Output Neuron
     */
    @Override
    public void updateWeights(){

        for(int i = 0; i < number_neurons; i++){
            neurons[i].updateWeights(this.delta);
        }

        if(next != null)
            next.updateWeights();
    }

    @Override
    public void updateDeltas(double delta){
        this.delta = delta;

        double[] weights = new double[neurons[0].getNumber_weights()];

        for(int i = 0; i < neurons[0].getNumber_weights(); i++)
            weights[i] = neurons[0].getWeight(i);

        previous.updateDeltas(this.delta, weights);
    }


}
