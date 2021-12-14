package com.example.translationtest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    BorderPane bp;

    @FXML
    AnchorPane ap;


    public void gamemodesPage(MouseEvent mouseEvent) {
        loadPage("gamemodesPage");
    }

    public void settingsPage(MouseEvent mouseEvent) {
        loadPage("settingsPage");
    }

    public void exitPage(MouseEvent mouseEvent) {
        bp.setCenter(ap);
    }

    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bp.setCenter(root);
    }
}