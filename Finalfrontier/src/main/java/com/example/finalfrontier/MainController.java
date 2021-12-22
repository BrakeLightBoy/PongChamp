package com.example.finalfrontier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {


    @FXML
    BorderPane bp;

    Stage stage;


    public void startMode(MouseEvent mouseEvent) {
        loadPage("Main");
    }

    public void gameMode(MouseEvent mouseEvent) {
        loadPage("Game");
    }

    public void settingsPage(MouseEvent mouseEvent) {
        loadPage("Settings");
    }

    public void exitPage(ActionEvent event) {
        stage = (Stage) bp.getScene().getWindow();
        stage.close();
    }


    public void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);

    }

}