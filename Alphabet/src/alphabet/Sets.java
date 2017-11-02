package alphabet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static alphabet.Constants.*;
import static alphabet.Constants.NUM_TESTS;

public class Sets {

    public Letter[] generateLearningSet() {

        Letter[] letters = new Letter[NUM_LETTERS];
        for(int i = 0; i < NUM_LETTERS; i++){
            letters[i] = new Letter();
        }
        //Letter tmp = new Letter();

        File file = new File("learning_set.txt");
        String line;
        int k = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String[] parts;
            while ((line = reader.readLine()) != null && k < NUM_LETTERS) {
                parts = line.split(" ");
                if (parts.length == 25) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            return null;
                        }
                    }
                }
                int l = 0;
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        letters[k].setX(i, j, Integer.parseInt(parts[l]));
                        l++;
                    }
                }

                letters[k].setActual_y(Integer.parseInt(parts[24]));
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return letters;
    }

    public Letter[] generateTestSet() {
        Letter[] letters = new Letter[NUM_TESTS];
        for(int i = 0; i < NUM_TESTS; i++){
            letters[i] = new Letter();
        }

        File file = new File("tests.txt");
        String line;
        int k = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String[] parts;
            while ((line = reader.readLine()) != null && k < NUM_TESTS) {
                parts = line.split(" ");
                if (parts.length == 24) {
                    for (String el : parts) {
                        if (el.isEmpty()) {
                            return null;
                        }
                    }
                }
                int l = 0;
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        letters[k].setX(i, j, Integer.parseInt(parts[l]));
                        l++;
                    }
                }

                letters[k].setActual_y(-1);
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return letters;
    }
}
