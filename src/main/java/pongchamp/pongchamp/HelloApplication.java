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


/**
 *
 */
public class HelloApplication extends Application {
    private Facade facade = new Facade();
    private Boolean p1Up,p1Down,p2Up,p2Down;

//    //variable
//    private static final int width = 800;
//    private static final int height = 600;
//    private static final int PLAYER_HEIGHT = 100;
//    private static final int PLAYER_WIDTH = 15;
//    private static final double BALL_R = 15;
//    private int ballYSpeed = 1;
//    private int ballXSpeed = 1;
//    private double playerOneYPos = height / 2;
//    private double playerTwoYPos = height / 2;
//    private double ballXPos = width / 2;
//    private double ballYPos = height / 2;
//    private int scoreP1 = 0;
//    private int scoreP2 = 0;
//    private boolean gameStarted;
//    private int playerOneXPos = 0;
//    private double playerTwoXPos = width - PLAYER_WIDTH;

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
        //set graphics
        //set background color
        facade.updateBoardState();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);

        //set text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));


            //set ball movement


            //simple computer opponent who is following the ball

            //draw the ball

            float[] ballPos = facade.getBallPosition();
            int ballRadius= facade.getBallRadius();
            int ballDiameter = 2*ballRadius;
            gc.fillOval(ballPos[0]-ballRadius, ballPos[1]-ballRadius, ballDiameter, ballDiameter);

      //pause code
//            //set the start text
//            gc.setStroke(Color.WHITE);
//            gc.setTextAlign(TextAlignment.CENTER);
//            gc.strokeText("Click", width / 2, height / 2);
//
//            //reset the ball start position
//            ballXPos = width / 2;
//            ballYPos = height / 2;
//
//            //reset the ball speed and the direction
//            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
//            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;

        //draw score
        int[] score = facade.getScore();
        gc.fillText(score[0] + "\t\t\t\t\t\t\t\t" + score[1], Properties.BOARD_WIDTH / 2, 100);


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
