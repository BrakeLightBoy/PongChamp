package com.example.finalfrontier;

import com.example.finalfrontier.controller.FXKeyHandler;
import com.example.finalfrontier.controller.InGameKeyListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.example.finalfrontier.model.Properties;
import com.example.finalfrontier.model.entities.powerups.PowerUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class GameRenderer extends MainController {

    private Facade facade;
    private Button gameRestart, gameExit, gameResume;
    private List<Button> buttons = new ArrayList<>();

    public GameRenderer() {
        facade = new Facade();
    }

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



    public void start(ActionEvent actionEvent) throws IOException {
        stage.setTitle("PONGCHAMP");
        //background size

        AnchorPane anchorPane = new AnchorPane();

         canvas = new Canvas(Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);

        anchorPane.getChildren().add(canvas);

         gc = canvas.getGraphicsContext2D();

        //JavaFX Timeline = free form animation defined by KeyFrames and their duration
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(25), e -> run(gc)));
        //number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);

        //mouse control (move and click)

        Scene scene = new Scene(anchorPane);

        double[] restartPosition = {(double) Properties.BOARD_WIDTH * 0.50, (double) Properties.BOARD_HEIGHT * 0.52};

        gameRestart = createButton("RestartBtn", "Restart", false, restartPosition, e -> {
                    hideButtons();
                    facade.gameRestart();
                }
        );


        double[] resumePosition = {(double) Properties.BOARD_WIDTH * 0.45, (double) Properties.BOARD_HEIGHT * 0.52};
        gameResume = createButton("ResumeBtn", "Resume", false, resumePosition, e -> {

                    hideButtons();


                    facade.resumeGame();
                }
        );

        double[] exitPosition = {(double) Properties.BOARD_WIDTH * 0.55, (double) Properties.BOARD_HEIGHT * 0.52};
        gameExit = createButton("ExitBtn", "Exit", false
                , exitPosition, e -> exitGame(stage));

        buttons.add(gameResume);
        buttons.add(gameRestart);
        buttons.add(gameExit);
        anchorPane.getChildren().addAll(gameRestart, gameResume, gameExit);

        InGameKeyListener keyListener = new InGameKeyListener();
        scene.setOnKeyPressed(keyListener);
        scene.setOnKeyReleased(keyListener);

        FXKeyHandler rightKeyHandler = new FXKeyHandler(KeyCode.UP, KeyCode.DOWN);
        FXKeyHandler leftKeyHandler = new FXKeyHandler(KeyCode.W, KeyCode.S);

        keyListener.registerKeyListener(rightKeyHandler);
        keyListener.registerKeyListener(leftKeyHandler);

        keyListener.registerKeyListener(keyEvent -> {
            if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED && keyEvent.getCode() == KeyCode.ESCAPE) {
                if (!facade.getGameEnd()) {
                    if (facade.isPaused()) {
                        unPauseGame();
                    } else {
                        pauseGame();
                    }
                }
            }
        });

        facade.setLeftPaddleController(leftKeyHandler);
        //facade.setRightPaddleController(rightKeyHandler);

        stage.setScene(scene);
        stage.show();
        tl.play();
    }

    private void hideButtons() {
        for (Button button : buttons) {
            button.setVisible(false);
        }
    }

    private Button createButton(String Id, String text, Boolean isVisible, double[] position, EventHandler<ActionEvent> action) {
        Button button = new Button();

        button.setId(Id);
        button.setText(text);
        button.setVisible(isVisible);
        button.setLayoutX(position[0]);
        button.setLayoutY(position[1]);
        button.setOnAction(action);

        return button;
    }

    private void drawBoard(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);
    }

    private void drawBall(GraphicsContext gc) {
        gc.setFill(facade.getBallColor());
        float[] ballPos = facade.getBallPosition();
        int ballRadius = facade.getBallRadius();
        int ballDiameter = 2 * ballRadius;
        gc.fillOval(ballPos[0] - ballRadius, ballPos[1] - ballRadius, ballDiameter, ballDiameter);
    }

    private void drawPaddles(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        float[] leftPaddlePos = facade.getLeftPaddlePosition();
        float[] leftPaddleDim = facade.getLeftPaddleDimensions();
        gc.fillRect(leftPaddlePos[0] - leftPaddleDim[0] / 2, leftPaddlePos[1] - leftPaddleDim[1] / 2, leftPaddleDim[0], leftPaddleDim[1]);

        float[] rightPaddlePos = facade.getRightPaddlePosition();
        float[] rightPaddleDim = facade.getRightPaddleDimensions();
        gc.fillRect((int) (rightPaddlePos[0] - rightPaddleDim[0] / 2), (int) (rightPaddlePos[1] - rightPaddleDim[1] / 2), rightPaddleDim[0], rightPaddleDim[1]);

    }

    private void drawScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Properties.FONT_SIZE);
        int[] score = facade.getScore();
        gc.fillText(String.valueOf(score[0]), Properties.BOARD_WIDTH * 1 / 4, Properties.BOARD_HEIGHT * 1 / 10);
        gc.fillText(String.valueOf(score[1]), Properties.BOARD_WIDTH * 3 / 4, Properties.BOARD_HEIGHT * 1 / 10);
    }

    private void drawPowerUps(GraphicsContext gc) {
        HashMap<Class<? extends PowerUp>, ArrayList<Float[]>> powerMap = facade.returnPowerMap();
        HashMap<Class<? extends PowerUp>, Color> powerColors = facade.returnPowerColors();

        int powerRadius = Properties.POWER_UP_RADIUS;
        int powerDiameter = powerRadius * 2;

        gc.setFill(Color.RED);
        powerMap.forEach((p, pm) -> {
            gc.setFill(powerColors.get(p));
            for (Float[] position : pm) {
                gc.fillOval(position[0] - powerRadius, position[1] - powerRadius, powerDiameter, powerDiameter);
            }
        });
    }

    private void pauseGame() {
        gameExit.setVisible(true);
        gameResume.setVisible(true);
        gameRestart.setVisible(true);
        gameExit.setLayoutX(Properties.BOARD_WIDTH * 0.55);
        gameRestart.setLayoutX(Properties.BOARD_WIDTH * 0.50);
        facade.pauseGame();

    }

    private void unPauseGame() {
        gameExit.setVisible(false);
        gameResume.setVisible(false);
        gameRestart.setVisible(false);

        facade.resumeGame();
    }

    private void endGame(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillText(facade.getGameWinner() + " wins!", Properties.BOARD_WIDTH * 9 / 20, Properties.BOARD_HEIGHT * 1 / 2);
        gameRestart.setVisible(true);
        gameExit.setVisible(true);
        facade.endGame();
        gameExit.setLayoutX(Properties.BOARD_WIDTH * 0.55);
        gameRestart.setLayoutX(Properties.BOARD_WIDTH * 0.45);
    }

    private void exitGame(Stage stage) {
        System.out.println("GAME CLOSED!");
        stage.close();
    }

    private void run(GraphicsContext gc) {
        //update gameBoard state
        facade.updateBoardState();

        drawBoard(gc);
        drawScore(gc);
        drawPaddles(gc);


        boolean gameEnded = facade.getGameEnd();

        if (gameEnded) {
            endGame(gc);
        } else {
            drawBall(gc);
            drawPowerUps(gc);
        }

    }
}
