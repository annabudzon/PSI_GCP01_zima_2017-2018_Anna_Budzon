package emoticons;

public class Constants {

    public static double LEARNING_RATE = 0.1;

    public static int MAX_ITERATION = 10000;

    public static double FORGET_RATE = LEARNING_RATE/3;

    public static int NUM_INPUT_X = 100;

    public static int NUM_OUTPUTS= 6;

    public static int NUM_HIDDEN_LAYERS = 0;

    public static int NUM_LAYERS = NUM_OUTPUTS + NUM_HIDDEN_LAYERS + 1;

    //number of testing sets
    public static int NUM_TESTS = 6;

    public static int NUM_LEARNING = 6;

    public static int THRESHOLD = 0;

    public static int DIM = 10;

    public static double randomNumber(double min, double max) {

        return (min + Math.random() * (max - min));
    }

}
