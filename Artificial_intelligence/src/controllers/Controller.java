package controllers;

import javafx.scene.control.TextArea;
import perceptron.Perceptron;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Perceptron perceptron = new Perceptron(this);
        perceptron.OR_operation();
    }

    public void setText(String text){
        textArea.appendText(text);
    }
}
