package emoticons;

import network.Network;
import controllers.Controller;

import static emoticons.Constants.*;


public class EmoticonsRecognition {

    private Controller myController;

    public EmoticonsRecognition(Controller myController){

        this.myController = myController;
    }

    public void networkStart(){

        Network network = new Network(myController);

        // learning
        network.learn();

        //testing
        network.test();

    }

}
