package com.example.finalfrontier;

import com.example.finalfrontier.controller.FXKeyHandler;
import com.example.finalfrontier.controller.InGameKeyListener;
import com.example.finalfrontier.model.Properties;
import com.example.finalfrontier.model.entities.powerups.PowerUp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController extends MainController {


    private Facade facade = new Facade();
    private Boolean p1Up, p1Down, p2Up, p2Down;

    @FXML
    Canvas canvas;
    @FXML
    GraphicsContext gc;
    Stage stage = new Stage();




    @Override
    public void exitPage(ActionEvent event)  {
        super.exitPage(event);
    }
    @Override
    public void gameMode(MouseEvent event) {
        super.gameMode(event);
    }
    @Override
    public void settingsPage(MouseEvent event) {
        super.settingsPage(event);
    }
    @Override
    public void playMode(MouseEvent mouseEvent) {
        super.playMode(mouseEvent);
    }


    public void start(ActionEvent actionEvent ) throws IOException {
        canvas = new Canvas(Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        //JavaFX Timeline = free form animation defined by KeyFrames and their duration
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(25), e -> run(gc)));
        //number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);
        //mouse control (move and click)
        Scene scene = new Scene(new StackPane(canvas));
        InGameKeyListener keyListener = new InGameKeyListener();
        scene.setOnKeyPressed(keyListener);
        scene.setOnKeyReleased(keyListener);
        FXKeyHandler rightKeyHandler = new FXKeyHandler(KeyCode.UP, KeyCode.DOWN);
        FXKeyHandler leftKeyHandler = new FXKeyHandler(KeyCode.W, KeyCode.S);
        keyListener.registerKeyListener(rightKeyHandler);
        keyListener.registerKeyListener(leftKeyHandler);
        facade.setLeftPaddleController(leftKeyHandler);
        facade.setRightPaddleController(rightKeyHandler);
            stage.setTitle("PONGCHAMP");
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


}
