package pongchamp.pongchamp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import pongchamp.pongchamp.model.Properties;

import pongchamp.pongchamp.model.Board;

import java.io.IOException;
import java.util.Random;
import pongchamp.pongchamp.Facade;

//public class HelloApplication extends Application {
//
//
//    public void start(Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("Starter.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//        /*
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene((Parent)fxmlLoader.load());
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        String css = this.getClass().getResource("application.css").toExternalForm();
//        scene.getStylesheets().add(css);
//        stage.show();
//         */
//
//    }
//
//    public static void main(String[] args) {
//        launch(new String[0]);
//    }
//}
