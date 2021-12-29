package pongchamp.pongchamp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import pongchamp.pongchamp.controller.FXKeyHandler;
import pongchamp.pongchamp.controller.InGameKeyListener;
import pongchamp.pongchamp.model.Properties;

import pongchamp.pongchamp.model.Board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import pongchamp.pongchamp.Facade;
import pongchamp.pongchamp.model.entities.powerups.PowerUp;

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


/**
 *
 */
public class GameRenderer extends Application {
    private Facade facade;
    private Button gameRestart,gameExit,gameResume;
    private boolean gamePaused;
    private List<Button> buttons = new ArrayList<>();

    public GameRenderer(){
        facade = new Facade();
    }

    public void start(Stage stage) {
        stage.setTitle("PONGCHAMP");
        //background size
        Canvas canvas = new Canvas(Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);



        GraphicsContext gc = canvas.getGraphicsContext2D();

        //JavaFX Timeline = free form animation defined by KeyFrames and their duration
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(25), e -> run(gc)));
        //number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);

        //mouse control (move and click)

        Scene scene = new Scene(new StackPane(canvas));



        InGameKeyListener keyListener = new InGameKeyListener();
        scene.setOnKeyPressed(keyListener);
        scene.setOnKeyReleased(keyListener);

        FXKeyHandler rightKeyHandler = new FXKeyHandler(KeyCode.UP,KeyCode.DOWN);
        FXKeyHandler leftKeyHandler = new FXKeyHandler(KeyCode.W,KeyCode.S);

        keyListener.registerKeyListener(rightKeyHandler);
        keyListener.registerKeyListener(leftKeyHandler);
        keyListener.registerKeyListener(keyEvent -> {
            if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED && keyEvent.getCode() == KeyCode.ESCAPE){
                if (gamePaused){
                    gamePaused = false;

                    gameResume.setVisible(false);
                    gameExit.setVisible(false);
                    gameRestart.setVisible(false);
                    facade.resumeGame();

                }
                else {
                    gamePaused = true;
                    facade.pauseGame();
                }
            }
        });

        facade.setLeftPaddleController(leftKeyHandler);
        //facade.setRightPaddleController(rightKeyHandler);

        stage.setScene(scene);
        stage.show();
        tl.play();
    }

    private void hideButtons(){
        for (Button button : buttons) {
            button.setVisible(false);
        }
    }

    private void run(GraphicsContext gc) {
        //update gameBoard state
        facade.updateBoardState();

        drawBoard(gc);
        drawScore(gc);
        drawPaddles(gc);

        boolean gameRunning = facade.getRunning();
        boolean gameEnded = facade.getGameEnd();

        if(gameEnded) {
            gameEnd(gc);
        }
        else {
            drawBall(gc);
            drawPowerUps(gc);

            if (gamePaused){
                gamePause(gc);
            }
        }

       }
    // start the application
    public static void main(String[] args) {
        launch(args);
    }
}
