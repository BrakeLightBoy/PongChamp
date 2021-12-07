package com.example.resizable_test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Stage stage = new Stage();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    /*
    public void lowerRes(){
        stage.setWidth(1280);
        stage.setHeight(720);
    }

    public void higherRes(){
        stage.setWidth(1920);
        stage.setHeight(1080);
    }

     */

    public static void main(String[] args) {
        launch();
    }
}