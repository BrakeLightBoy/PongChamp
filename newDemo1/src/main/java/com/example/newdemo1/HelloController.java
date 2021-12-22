package com.example.newdemo1;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController  {


    @FXML
    BorderPane bp;

    @FXML
    AnchorPane ap;

    Stage stage;
    @FXML
    Pane slider;
    @FXML
    AnchorPane mainPane;

    public void startMode(MouseEvent mouseEvent) {
        loadPage("navBar");
    }

    public void gamemodesPage(MouseEvent mouseEvent) {
        loadPage("gamemodesPage");
    }

    public void settingsPage(MouseEvent mouseEvent) {
        loadPage("settingsPage");
    }

    public void exitPage(MouseEvent mouseEvent) {
        stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }

    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);

    }

    public void slider(Pane slider, AnchorPane mainPane) {
        slider.setOnMouseEntered(mouseEvent -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-200);

        });

        mainPane.setOnMouseClicked(mouseEvent -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(slider);

            slide.setToX(-199);
            slide.play();


        });
    }

}