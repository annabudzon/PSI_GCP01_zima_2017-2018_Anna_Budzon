package rastrigin;

import network.Network;
import controllers.Controller;

import static rastrigin.Constants.*;


public class RastriginFunction {

    private Controller myController;

    private double weights[];

    public RastriginFunction(Controller myController){

        this.myController = myController;
        this.weights = new double[NUM_WEIGHTS];
    }

    public void networkStart(){

        Network network = new Network(myController);

        //sigmoidal learning
        network.learn();

        //testing
        network.test();

    }

}
