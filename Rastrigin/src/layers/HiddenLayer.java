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

    /**
     * Updating Weights
     */
    @Override
    public void updateWeights(){
        for(int i = 0; i < number_neurons; i++){
            neurons[i].updateWeights(deltas[i]);
        }

        next.updateWeights();
    }

    /**
     * Updating Deltas for Last Hidden Layer in Case of One Output
     */
    @Override
    public void updateDeltas(double delta, double[] weights){
        double[][] w = new double[number_neurons][number_inputs];

        for(int i = 0; i < this.number_neurons; i++){
            this.deltas[i] = delta*weights[i];
        }

        for(int i = 0; i < number_neurons; i++){
            for(int j = 0; j < number_inputs; j++)
                w[i][j] = neurons[i].getWeight(j);
        }

        previous.updateDeltas(this.deltas, w);
    }

    /**
     * Updating Deltas for Hidden Layer Besides the Last One
     */
    @Override
    public void updateDeltas(double[] delta, double[][] weights){
        double[][]w = new double[number_neurons][number_inputs];

        for(int i = 0; i < number_neurons; i++){
            for(int j = 0; j < next.getNumberOfNeurons(); j++)
                deltas[i] += delta[j]*weights[j][i];
        }

        for(int i = 0; i < number_neurons; i++){
            for(int j = 0; j < number_inputs; j++)
                w[i][j] = neurons[i].getWeight(j);
        }

        previous.updateDeltas(this.deltas, w);
    }
}
