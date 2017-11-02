package alphabet;

import algorithms.Adaline;
import algorithms.Sigmoidal;
import controllers.Controller;
import static alphabet.Constants.*;

public class Alphabet {

    private Controller myController;

    //defining weights for each input x + bias
    private double weights[];

    public Alphabet(Controller controller) {

        this.myController = controller;
    }

    public void upper_or_lower() {

        this.myController.setText("****** Defining lower and uppercases\n");

        this.weights = new double[NUM_WEIGHTS];

        //initially weights have random value in the range from 0 to 1
        for (int i = 0; i < NUM_WEIGHTS; i++) {
            this.weights[i] = randomNumber(0, 1);
        }

        Adaline adaline = new Adaline(weights, myController);
        Sigmoidal sigmoidal = new Sigmoidal(weights, myController);

        //adaline learning
       // this.weights = adaline.learning();

        //sigmoidal learning
        this.weights = sigmoidal.learning();

        //testing
        this.testing();
    }

    private void testing() {

        Sets set = new Sets();
        //testing set
        Letter letters[] = set.generateTestSet();

        double sum;

        int output = 0;

        this.myController.setText("\n-------Testing-------\n");

        for (int i = 0; i < NUM_TESTS; i++) {

            myController.setText("---->  TEST " + (i + 1) + "  <----\n");

            // sumator
            sum = letters[i].sum_function(this.weights);

            //calculating the outputs of differentiating upper and lowercases, based on learnt weights
            output = activation_function(sum);

            int k = 0;
            for (int m = 0; m < ROWS; m++) {
                for (int n = 0; n < COLUMNS; n++) {
                    if(k%4 == 0)
                        this.myController.setText("\n");
                    if (letters[i].getX(m, n) == 1)
                        this.myController.setText(" * ");
                    else
                        this.myController.setText("   ");
                    k++;
                }
            }
            if(output == LOWERCASE){
                myController.setText("\nLETTER IS LOWERCASE\n");
            }else{

                myController.setText("\nLETTER IS UPPERCASE\n");
            }
        }
    }

    private int activation_function(double sum){

        int output;

        //assign results to adequate classes (0 or 1)
        if (sum >= THRESHOLD)
            output = LOWERCASE;
        else
            output = -1;

        return output;
    }

    private static double randomNumber(double min, double max) {

        return (min + Math.random() * (max - min));
    }
}
