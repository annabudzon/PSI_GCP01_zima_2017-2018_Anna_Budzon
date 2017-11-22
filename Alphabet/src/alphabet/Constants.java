package alphabet;

public class Constants {

    public static int UPPERCASE = 0;

    public static int LOWERCASE = 1;

    //describing amount of rows and colums in one letter
    public static int ROWS = 6;

    public static int COLUMNS = 4;

    //number of learning sets
    public static int NUM_LETTERS = 20;

    public static double LEARNING_RATE = 0.001;

    public static int MAX_ITERATION = 100000000;

    //number of x in one learning set
    public static int NUM_X = ROWS * COLUMNS;

    //number od weights + bias
    public static int NUM_WEIGHTS = NUM_X + 1;

    //number of testing sets
    public static int NUM_TESTS = 20;

    public static int THRESHOLD = 0;

}
