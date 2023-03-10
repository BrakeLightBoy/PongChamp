package pongchamp.pongchamp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import pongchamp.pongchamp.controller.FXKeyHandler;
import pongchamp.pongchamp.controller.InGameKeyListener;
import pongchamp.pongchamp.model.GameModes;
import pongchamp.pongchamp.model.Properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static pongchamp.pongchamp.util.FrontendMethods.*;

import pongchamp.pongchamp.model.entities.powerups.PowerUp;


public class GameRenderer extends Application{
    private Facade facade;
    private Button gameRestart,gameExit,gameResume;
    private List<Button> buttons = new ArrayList<>();
//    GameModeStarter gameModeStarter;
    MainController mainController;
    GameModes chosenGameMode;

    public GameRenderer(GameModes gameMode,boolean withPowerUps, MainController mainController){
        facade = new Facade(gameMode, withPowerUps);
        this.chosenGameMode = gameMode;
        this.mainController = mainController;
    }

    public void start(Stage stage) {
        stage.setTitle("PONGCHAMP");
        //background size

        AnchorPane anchorPane = new AnchorPane();

        Canvas canvas = new Canvas(Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);

        anchorPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //JavaFX Timeline = free form animation defined by KeyFrames and their duration
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(Properties.MILLISECONDS_PER_TICK), e -> run(gc)));
        //number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);

        //mouse control (move and click)

        Scene scene = new Scene(anchorPane);

        double[] restartPosition = {(double) Properties.BOARD_WIDTH*0.50,(double) Properties.BOARD_HEIGHT*0.52};

        gameRestart = createButton("RestartBtn", "Restart", false, restartPosition, 2, e -> {
                hideButtons();
                facade.gameRestart();
            }
        );


        double[] resumePosition = {(double) Properties.BOARD_WIDTH*0.45-97,(double) Properties.BOARD_HEIGHT*0.52};
        gameResume = createButton("ResumeBtn","Resume",false,resumePosition, 2, e -> {

            hideButtons();


            facade.resumeGame();
            }
        );

        double[] exitPosition = {(double) Properties.BOARD_WIDTH*0.45+200,(double) Properties.BOARD_HEIGHT*0.52};
        gameExit = createButton("ExitBtn","Exit",false, exitPosition,2, e -> {
                    try {
                        exitGame(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        buttons.add(gameResume);
        buttons.add(gameRestart);
        buttons.add(gameExit);

        anchorPane.getChildren().addAll(gameRestart, gameResume, gameExit);

        InGameKeyListener keyListener = new InGameKeyListener();
        scene.setOnKeyPressed(keyListener);
        scene.setOnKeyReleased(keyListener);

        FXKeyHandler rightKeyHandler = new FXKeyHandler(KeyCode.UP,KeyCode.DOWN);
        FXKeyHandler leftKeyHandler = new FXKeyHandler(KeyCode.W,KeyCode.S);

        keyListener.registerKeyListener(rightKeyHandler);
        keyListener.registerKeyListener(leftKeyHandler);

        keyListener.registerKeyListener(keyEvent -> {
            if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED && keyEvent.getCode() == KeyCode.ESCAPE){
                if (!facade.getGameEnd()){
                    if (facade.isPaused()){
                        unPauseGame();
                    }
                    else {
                        pauseGame();
                    }
                }
            }
        });

        facade.setLeftPaddleController(leftKeyHandler);
        if (facade.getGameMode() == GameModes.V_1)
            facade.setRightPaddleController(rightKeyHandler);

        stage.setScene(scene);
        stage.show();
        tl.play();
    }

    private void hideButtons(){
        for (Button button : buttons) {
            button.setVisible(false);
        }
    }



    private void drawBoard(GraphicsContext gc){
        gc.setFill(facade.getBackgroundColor());
        gc.fillRect(0, 0, Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);
    }

    private void drawBall(GraphicsContext gc){
        if (facade.getBallVisibility()){
            gc.setFill(facade.getBallColor());
            float[] ballPos = facade.getBallPosition();
            int ballRadius= facade.getBallRadius();
            int ballDiameter = 2*ballRadius;
            gc.fillOval(ballPos[0]-ballRadius, ballPos[1]-ballRadius, ballDiameter, ballDiameter);
        }
    }

    private void drawPaddles(GraphicsContext gc){
        gc.setFill(facade.getPaddle1Color());
        float[] leftPaddlePos = facade.getLeftPaddlePosition();
        float[] leftPaddleDim = facade.getLeftPaddleDimensions();
        gc.fillRect(leftPaddlePos[0]-leftPaddleDim[0]/2, leftPaddlePos[1]-leftPaddleDim[1]/2, leftPaddleDim[0], leftPaddleDim[1]);

        gc.setFill(facade.getPaddle2Color());
        float[] rightPaddlePos = facade.getRightPaddlePosition();
        float[] rightPaddleDim = facade.getRightPaddleDimensions();
        gc.fillRect((int)(rightPaddlePos[0]-rightPaddleDim[0]/2),(int)( rightPaddlePos[1]-rightPaddleDim[1]/2), rightPaddleDim[0], rightPaddleDim[1]);

    }

    private void drawScore(GraphicsContext gc){
        gc.setFill(Properties.FONT_COLOR);
        gc.setFont(Properties.FONT_SIZE);
        int[] score = facade.getScore();
        gc.fillText(String.valueOf(score[0]), Properties.BOARD_WIDTH*1/4, Properties.BOARD_HEIGHT*1/10);
        gc.fillText(String.valueOf(score[1]), Properties.BOARD_WIDTH*3/4, Properties.BOARD_HEIGHT*1/10);
    }

    private void drawTimer(GraphicsContext gc){
        gc.setFill(Properties.FONT_COLOR);
        gc.setFont(Properties.FONT_SIZE);
        String time = facade.getTime();
        gc.fillText(time,Properties.BOARD_WIDTH*1/2,Properties.BOARD_HEIGHT*1/10);
    }

    private void drawPowerUps(GraphicsContext gc){
        HashMap<Class<? extends PowerUp>, ArrayList<Float[]>> powerMap = facade.returnPowerMap();
        HashMap<Class<? extends PowerUp>, Color> powerColors = facade.returnPowerColors();

        int powerRadius = Properties.POWER_UP_RADIUS;
        int powerDiameter = powerRadius*2;

        powerMap.forEach((p,pm) ->{
            gc.setFill(powerColors.get(p));
            for (Float[] position : pm){
                gc.fillOval(position[0]-powerRadius, position[1]-powerRadius, powerDiameter, powerDiameter);
            }
        });
    }

    private void pauseGame(){
        gameExit.setVisible(true);
        gameResume.setVisible(true);
        gameRestart.setVisible(true);
        facade.pauseGame();
        gameExit.setStyle("-fx-background-color: #900000; ");
        gameExit.setTextFill(Color.WHITE);
        gameExit.setOpacity(.9);
        gameResume.setStyle("-fx-background-color: #219000; ");
        gameResume.setTextFill(Color.WHITE);
        gameResume.setOpacity(.9);
        gameRestart.setStyle("-fx-background-color: #907C00; ");
        gameRestart.setTextFill(Color.WHITE);
        gameRestart.setOpacity(.9);
    }

    private void unPauseGame(){
        gameExit.setVisible(false);
        gameResume.setVisible(false);
        gameRestart.setVisible(false);

        facade.resumeGame();
    }

    private void endGame(GraphicsContext gc){
        gc.setFill(Properties.FONT_COLOR);
        gc.setFont(Properties.FONT_SIZE);
        gc.fillText(facade.getGameWinner() + " wins!", Properties.BOARD_WIDTH*9/20, Properties.BOARD_HEIGHT*1/2);
        gameRestart.setVisible(true);
        gameExit.setVisible(true);
        facade.endGame();
        gameExit.setLayoutX(Properties.BOARD_WIDTH*0.55);
        gameRestart.setLayoutX(Properties.BOARD_WIDTH*0.45);
    }

    private void exitGame(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GameModeSelection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void run(GraphicsContext gc) {
        //update gameBoard state
        facade.updateBoardState();

        drawBoard(gc);

        drawPaddles(gc);
        drawTimer(gc);

        boolean gameEnded = facade.getGameEnd();

        if(gameEnded) {
            endGame(gc);
        }
        else {
            drawBall(gc);
            drawPowerUps(gc);
        }

        if(chosenGameMode != GameModes.END){
            drawScore(gc);
        }

   }
    // start the application
    public static void main(String[] args) {
        launch(args);
    }
}
