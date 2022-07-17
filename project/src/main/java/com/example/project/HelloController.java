package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    /*include target inside the xml.. example:
    <Label fx:id="welcomeText"/> (above the button)

    on mouse press goes the method name */
}