package com.example.finalfrontier;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        play();
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("PONG!");
        stage.setScene(scene);
        stage.show();
    }

    public void play() { //Empty method?
    }

    public static void main(String[] args) {
        launch();
    }

}

