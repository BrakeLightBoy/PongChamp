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
public class HelloApplication extends Application {
    private Facade facade = new Facade();
    private Boolean p1Up,p1Down,p2Up,p2Down;


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

        facade.setLeftPaddleController(leftKeyHandler);
        facade.setRightPaddleController(rightKeyHandler);

        stage.setScene(scene);
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        //update gameBoard state
        facade.updateBoardState();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);

        //draw the ball
        gc.setFill(facade.getBallColor());
        float[] ballPos = facade.getBallPosition();
        int ballRadius= facade.getBallRadius();
        int ballDiameter = 2*ballRadius;
        gc.fillOval(ballPos[0]-ballRadius, ballPos[1]-ballRadius, ballDiameter, ballDiameter);

        HashMap<Class<? extends PowerUp>, ArrayList<Float[]>> powerMap = facade.returnPowerMap();

        HashMap<Class<? extends PowerUp>, Color> powerColors = facade.returnPowerColors();

        int powerRadius = Properties.POWER_UP_RADIUS;
        int powerDiameter = powerRadius*2;

        gc.setFill(Color.RED);
        powerMap.forEach((p,pm) ->{
            gc.setFill(powerColors.get(p));
            for (Float[] position : pm){
                gc.fillOval(position[0]-powerRadius, position[1]-powerRadius, powerDiameter, powerDiameter);
            }
        });
        gc.setFill(Color.WHITE);


        //draw score
        gc.setFont(Properties.FONT_SIZE);
        int[] score = facade.getScore();
        gc.fillText(String.valueOf(score[0]), Properties.BOARD_WIDTH*1/4, Properties.BOARD_HEIGHT*1/10);
        gc.fillText(String.valueOf(score[1]), Properties.BOARD_WIDTH*3/4 , Properties.BOARD_HEIGHT*1/10);


        //draw player paddles
        float[] leftPaddlePos = facade.getLeftPaddlePosition();
        float[] leftPaddleDim = facade.getLeftPaddleDimensions();
        gc.fillRect(leftPaddlePos[0]-leftPaddleDim[0]/2, leftPaddlePos[1]-leftPaddleDim[1]/2, leftPaddleDim[0], leftPaddleDim[1]);

        float[] rightPaddlePos = facade.getRightPaddlePosition();
        float[] rightPaddleDim = facade.getRightPaddleDimensions();
        gc.fillRect((int)(rightPaddlePos[0]-rightPaddleDim[0]/2),(int)( rightPaddlePos[1]-rightPaddleDim[1]/2), rightPaddleDim[0], rightPaddleDim[1]);
    }
    // start the application
    public static void main(String[] args) {
        launch(args);
    }
}
