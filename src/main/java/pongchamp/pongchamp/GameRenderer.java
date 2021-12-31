package pongchamp.pongchamp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import pongchamp.pongchamp.controller.json.JsonLoader;
import pongchamp.pongchamp.model.Properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static pongchamp.pongchamp.util.FrontendMethods.*;

import pongchamp.pongchamp.model.entities.powerups.PowerUp;


public class GameRenderer extends Application {
    private Facade facade;
    private Button gameRestart,gameExit,gameResume;
    private List<Button> buttons = new ArrayList<>();

    public GameRenderer(){
        //facade = new Facade();
        facade = new Facade("{\"settings\":{\"ballColor\":\"0xffffffff\",\"paddle1Color\":\"0xffffffff\",\"paddle2Color\":\"0xffffffff\",\"backgroundColor\":\"0x000000ff\"},\"backgroundColor\":\"0x000000ff\",\"width\":1200.0,\"height\":700.0,\"paddleDistanceFromTheEdge\":40.0,\"hasEnded\":false,\"isPaused\":false,\"upperWall\":{\"wallType\":\"UPPER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":700.0},\"endPoint\":{\"x\":1200.0,\"y\":700.0}}},\"lowerWall\":{\"wallType\":\"LOWER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":0.0},\"endPoint\":{\"x\":1200.0,\"y\":0.0}}},\"leftPaddleMovementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"rightPaddleMovementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"leftPaddle\":{\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":30.0,\"minY\":600.0,\"maxX\":50.0,\"maxY\":700.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":40.0,\"y\":650.0}},\"paddleType\":\"LEFT\",\"paddleColor\":\"0xffffffff\",\"uuid\":\"12696301-9828-4ecc-ab7e-36ec4fb0372e\",\"location\":{\"x\":40.0,\"y\":650.0},\"metadata\":{\"map\":{}},\"className\":\"Paddle\"},\"rightPaddle\":{\"tick\":393,\"notMovedLastTick\":false,\"nextTickToMove\":332,\"lastYs\":[606.0,614.0,622.0],\"target\":{\"radius\":10,\"speed\":{\"x\":-4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":\"0xffffffff\",\"uuid\":\"0e087efa-da9e-4c81-9068-ab23e26bc190\",\"location\":{\"x\":116.0,\"y\":630.0},\"metadata\":{\"map\":{}}},\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":1150.0,\"minY\":530.0,\"maxX\":1170.0,\"maxY\":630.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":1160.0,\"y\":580.0}},\"paddleType\":\"RIGHT\",\"paddleColor\":\"0xffffffff\",\"uuid\":\"21e2b380-65dc-4197-9ad5-e6e750f0ef5c\",\"location\":{\"x\":1160.0,\"y\":580.0},\"metadata\":{\"map\":{}},\"className\":\"MediumAIPaddle\"},\"gameWinner\":\"Ongoing\",\"ball\":{\"radius\":10,\"speed\":{\"x\":-4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":\"0xffffffff\",\"uuid\":\"0e087efa-da9e-4c81-9068-ab23e26bc190\",\"location\":{\"x\":116.0,\"y\":630.0},\"metadata\":{\"map\":{}}},\"paddles\":[{\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":30.0,\"minY\":600.0,\"maxX\":50.0,\"maxY\":700.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":40.0,\"y\":650.0}},\"paddleType\":\"LEFT\",\"paddleColor\":\"0xffffffff\",\"uuid\":\"12696301-9828-4ecc-ab7e-36ec4fb0372e\",\"location\":{\"x\":40.0,\"y\":650.0},\"metadata\":{\"map\":{}},\"className\":\"Paddle\"},{\"tick\":393,\"notMovedLastTick\":false,\"nextTickToMove\":332,\"lastYs\":[606.0,614.0,622.0],\"target\":{\"radius\":10,\"speed\":{\"x\":-4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":\"0xffffffff\",\"uuid\":\"0e087efa-da9e-4c81-9068-ab23e26bc190\",\"location\":{\"x\":116.0,\"y\":630.0},\"metadata\":{\"map\":{}}},\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":1150.0,\"minY\":530.0,\"maxX\":1170.0,\"maxY\":630.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":1160.0,\"y\":580.0}},\"paddleType\":\"RIGHT\",\"paddleColor\":\"0xffffffff\",\"uuid\":\"21e2b380-65dc-4197-9ad5-e6e750f0ef5c\",\"location\":{\"x\":1160.0,\"y\":580.0},\"metadata\":{\"map\":{}},\"className\":\"MediumAIPaddle\"}],\"obstacles\":[{\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":30.0,\"minY\":600.0,\"maxX\":50.0,\"maxY\":700.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":40.0,\"y\":650.0}},\"paddleType\":\"LEFT\",\"paddleColor\":\"0xffffffff\",\"uuid\":\"12696301-9828-4ecc-ab7e-36ec4fb0372e\",\"location\":{\"x\":40.0,\"y\":650.0},\"metadata\":{\"map\":{}},\"className\":\"Paddle\"},{\"tick\":393,\"notMovedLastTick\":false,\"nextTickToMove\":332,\"lastYs\":[606.0,614.0,622.0],\"target\":{\"radius\":10,\"speed\":{\"x\":-4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":\"0xffffffff\",\"uuid\":\"0e087efa-da9e-4c81-9068-ab23e26bc190\",\"location\":{\"x\":116.0,\"y\":630.0},\"metadata\":{\"map\":{}}},\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":1150.0,\"minY\":530.0,\"maxX\":1170.0,\"maxY\":630.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":1160.0,\"y\":580.0}},\"paddleType\":\"RIGHT\",\"paddleColor\":\"0xffffffff\",\"uuid\":\"21e2b380-65dc-4197-9ad5-e6e750f0ef5c\",\"location\":{\"x\":1160.0,\"y\":580.0},\"metadata\":{\"map\":{}},\"className\":\"MediumAIPaddle\"},{\"wallType\":\"LOWER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":0.0},\"endPoint\":{\"x\":1200.0,\"y\":0.0}},\"className\":\"Wall\"},{\"wallType\":\"UPPER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":700.0},\"endPoint\":{\"x\":1200.0,\"y\":700.0}},\"className\":\"Wall\"}],\"spawnedPowerUps\":[],\"activatedPowerUps\":[],\"maintainedPowerUps\":[],\"toRemove\":[],\"hasPowerUps\":false,\"leftScore\":0,\"rightScore\":0}\n");
    }

    public void start(Stage stage) {
        stage.setTitle("PONGCHAMP");
        //background size

        AnchorPane anchorPane = new AnchorPane();

        Canvas canvas = new Canvas(Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);

        anchorPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //JavaFX Timeline = free form animation defined by KeyFrames and their duration
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(25), e -> run(gc)));
        //number of cycles in animation INDEFINITE = repeat indefinitely
        tl.setCycleCount(Timeline.INDEFINITE);

        //mouse control (move and click)

        Scene scene = new Scene(anchorPane);

        double[] restartPosition = {(double) Properties.BOARD_WIDTH*0.50,(double) Properties.BOARD_HEIGHT*0.52};

        gameRestart = createButton("RestartBtn", "Restart", false, restartPosition, e -> {
                hideButtons();
                facade.gameRestart();
            }
        );


        double[] resumePosition = {(double) Properties.BOARD_WIDTH*0.45,(double) Properties.BOARD_HEIGHT*0.52};
        gameResume = createButton("ResumeBtn","Resume",false,resumePosition,e -> {

            hideButtons();


            facade.resumeGame();
            }
        );

        double[] exitPosition = {(double) Properties.BOARD_WIDTH*0.55,(double) Properties.BOARD_HEIGHT*0.52};
        gameExit = createButton("ExitBtn","Exit",false
                ,exitPosition,e -> exitGame(stage));

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
        //facade.setRightPaddleController(rightKeyHandler);




        Thread runlater = new Thread(() ->{
            try {
                Thread.sleep(10000);


                String setj = facade.saveUserSettings();
                String bs = facade.saveBoardState();


                System.out.println("-----");

                System.out.println(setj);
                System.out.println(bs);

                System.out.println("-----");

                JsonLoader loader = new JsonLoader();
                System.out.println(loader.loadBoard(bs));
                System.out.println(loader.loadUserSettings(setj));


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        //runlater.start();

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
        gameExit.setLayoutX(Properties.BOARD_WIDTH*0.55);
        gameRestart.setLayoutX(Properties.BOARD_WIDTH*0.50);
        facade.pauseGame();

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

    private void exitGame(Stage stage){
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

        if(gameEnded) {
            endGame(gc);
        }
        else {
            drawBall(gc);
            drawPowerUps(gc);
        }

       }
    // start the application
    public static void main(String[] args) {
        launch(args);
    }
}
