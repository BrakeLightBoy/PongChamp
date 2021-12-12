package pongchamp.pongchamp;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pongchamp.pongchamp.FX.SoundEffects;

import javax.swing.*;

public class HelloApplication extends Application {
    SettingsController obj = new SettingsController();


    public void start(Stage stage) throws IOException {
        play();
        Parent root = FXMLLoader.load(getClass().getResource("Starter.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        /*
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.show();
         */

    }

    public void play() {
        obj.play();
    }


    public static void main(String[] args) {
        launch(new String[0]);
    }
}

