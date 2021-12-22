package com.example.finalfrontier;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {
    SettingsController obj = new SettingsController();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PONG!");
        stage.setScene(scene);
        stage.show();
    }
    public void play() {

    }

    public static void main(String[] args) {
        launch();
    }

}

