package rastrigin;

public class Constants {

    public static double LEARNING_RATE = 0.05;

    public static int MAX_ITERATION = 10000;

    public static int NUM_INPUT_X = 2;

    public static int NUM_HIDDEN_NEURONS_1 = 3;

    public static int NUM_HIDDEN_NEURONS_2 = 2;

    public static int NUM_OUTPUT_NEURONS = 1;

    public static int NUM_NEURONS = NUM_HIDDEN_NEURONS_1 + NUM_HIDDEN_NEURONS_2 + NUM_OUTPUT_NEURONS + 1;

    public static int NUM_HIDDEN_LAYERS = 2;

    public static int NUM_LAYERS = NUM_OUTPUT_NEURONS + NUM_HIDDEN_LAYERS + 1;

    //number od weights + bias
    public static int NUM_WEIGHTS = NUM_NEURONS + 1;

    //number of testing sets
    public static int NUM_TESTS = 10;

    public static int NUM_LEARNING = 20;

    public static int THRESHOLD = 0;

    public static int A = 10;

    public static double randomNumber(double min, double max) {

        return (min + Math.random() * (max - min));
    }

}
