package pongchamp.pongchamp;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pongchamp.pongchamp.model.Properties;

import static pongchamp.pongchamp.util.FrontendMethods.*;
import pongchamp.pongchamp.model.Properties;

import java.io.IOException;


    public class GameModeStarter extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {
            stage.setTitle("PONG");

            double[] gamemode1 = {(double) Properties.BOARD_WIDTH*0.40,(double) Properties.BOARD_HEIGHT*0.52};
            double[] gamemode2 = {(double) Properties.BOARD_WIDTH*0.50,(double) Properties.BOARD_HEIGHT*0.52};
            double[] gamemode3 = {(double) Properties.BOARD_WIDTH*0.55,(double) Properties.BOARD_HEIGHT*0.52};

            Button gamemode1Btn = createButton("btn1", "Gamemode 1", true, gamemode1,  e-> {

            });
            Button gamemode2Btn = createButton("btn2", "Gamemode 2", true, gamemode2, e-> {

            });
            Button gamemode3Btn = createButton("btn3", "Gamemode 3", true, gamemode3, e-> {

            });

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(gamemode1Btn, gamemode2Btn, gamemode3Btn);

            Scene scene = new Scene(stackPane, Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);
            stage.setScene(scene);
            stage.show();



        }

    }



