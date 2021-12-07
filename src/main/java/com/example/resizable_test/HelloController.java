package com.example.resizable_test;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class HelloController {
    @FXML
    BorderPane BP;

    boolean isHD = true;
    int preferedWidth;
    int preferedHeight;

    @FXML
    private Label welcomeText;

    @FXML
    protected void changeResolution() {
        if (isHD) {
            preferedWidth = 1920;
            preferedHeight = 1080;
            isHD = false;
            //HelloApplication.higherRes();
        } else {
            preferedWidth = 1280;
            preferedHeight = 720;
            isHD = true;
            //HelloApplication.lowerRes();
        }
        BP.setPrefHeight(preferedHeight);
        BP.setPrefWidth(preferedWidth);
    }
}