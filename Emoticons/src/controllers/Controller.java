package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import emoticons.EmoticonsRecognition;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmoticonsRecognition rastrigin = new EmoticonsRecognition(this);
        rastrigin.networkStart();
    }

    public void setText(String text){
        textArea.appendText(text);
    }
}
