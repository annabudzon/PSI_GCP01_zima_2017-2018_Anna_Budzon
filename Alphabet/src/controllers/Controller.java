package controllers;

import alphabet.Alphabet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alphabet alphabet = new Alphabet(this);
        alphabet.operation();
    }

    public void setText(String text){
        textArea.appendText(text);
    }
}
