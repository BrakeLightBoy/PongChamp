package pongchamp.pongchamp;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.GameModes;
import pongchamp.pongchamp.model.Properties;
import pongchamp.pongchamp.model.entities.powerups.PowerUp;
import pongchamp.pongchamp.model.math.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Facade {
    private final Board gameBoard;

    public Facade(GameModes gameMode, boolean withPowerUps) {
        this.gameBoard = new Board(gameMode,withPowerUps); //todo specify which game mode the user wants
    }


    public void updateBoardState(){
        if (!isPaused() && !getGameEnd()){
            gameBoard.run();
        }
    }

    public float[] getLeftPaddlePosition(){
        float[] leftPosition = new float[2];
        leftPosition[0] = gameBoard.getLeftPaddle().getLocation().getX();
        leftPosition[1] = gameBoard.getLeftPaddle().getLocation().getY();

        return leftPosition;
    }

    public float[] getLeftPaddleDimensions(){
        float[] leftDimensions = new float[2];
        leftDimensions[0] = gameBoard.getLeftPaddle().getWidth();
        leftDimensions[1] = gameBoard.getLeftPaddle().getHeight();

        return leftDimensions;
    }

    public float[] getRightPaddlePosition(){
        float[] rightPosition = new float[2];
        rightPosition[0] = gameBoard.getRightPaddle().getLocation().getX();
        rightPosition[1] = gameBoard.getRightPaddle().getLocation().getY();

        return rightPosition;
    }

    public float[] getRightPaddleDimensions(){
        float[] rightDimensions = new float[2];
        rightDimensions[0] = gameBoard.getRightPaddle().getWidth();
        rightDimensions[1] = gameBoard.getRightPaddle().getHeight();

        return rightDimensions;
    }

    public float[] getBallPosition(){
        float[] ballPosition = new float[2];
        ballPosition[0] = gameBoard.getBall().getLocation().getX();
        ballPosition[1] = gameBoard.getBall().getLocation().getY();

        return ballPosition;
    }

    public int getBallRadius(){
        return gameBoard.getBall().getRadius();
    }

    public int[] getScore(){
        int[] score = new int[2];

        score[0] = gameBoard.getLeftScore();
        score[1] = gameBoard.getRightScore();
        return score;
    }

    public void setRightPaddleController(PaddleController paddleController){
        gameBoard.getRightPaddle().setPaddleController(paddleController);
    }
    public void setLeftPaddleController(PaddleController paddleController){
        gameBoard.getLeftPaddle().setPaddleController(paddleController);
    }

    public HashMap<Class<? extends PowerUp>,ArrayList<Float[]>> returnPowerMap(){
        HashMap<Class<? extends PowerUp>,ArrayList<Float[]>> powerUpsMap = new HashMap<>();
        HashSet<Class<? extends PowerUp>> powerNames = new HashSet<>();

        for (PowerUp power : gameBoard.getSpawnedPowers()){
            powerNames.add(power.getClass());
        }
        for (Class<? extends  PowerUp> name : powerNames){
            powerUpsMap.put(name,new ArrayList<Float[]>());
        }

        for (PowerUp spawnedPower : gameBoard.getSpawnedPowers()){
            Float[] position = new Float[2];
            position[0] = spawnedPower.getLocation().getX();
            position[1] = spawnedPower.getLocation().getY();

            ArrayList<Float[]> specificPowerPosition = powerUpsMap.get(spawnedPower.getClass());

            specificPowerPosition.add(position);
        }
        return powerUpsMap;
    }

    public HashMap<Class<? extends PowerUp>,Color> returnPowerColors(){
        HashMap<Class<? extends PowerUp>,Color> powerColors = new HashMap<>();

        HashSet<Class<? extends PowerUp>> powerNames = new HashSet<>();
        for (PowerUp power : gameBoard.getSpawnedPowers()){
            if (!powerNames.contains(power.getClass())){
                powerColors.put(power.getClass(),power.getPowerColor());
            }
            powerNames.add(power.getClass());
        }
        return powerColors;
    }

    public Color getBallColor(){
        return gameBoard.getBall().getBallColor();
    }

    public Boolean isPaused() {
        return gameBoard.getPaused();
    }


    public Boolean getGameEnd() {
        return gameBoard.getHasEnded();
    }

    public String getGameWinner() {
        return gameBoard.getGameWinner();
    }

    public void pauseGame(){
        gameBoard.setPaused(true);
    }

    public void endGame(){
        gameBoard.setHasEnded(true);
    }

    public void resumeGame(){
        gameBoard.setPaused(false);
        setBallVisibility(true);
    }

    public void gameRestart(){
        gameBoard.restartGame();
        resumeGame();
        gameBoard.setTime(0);
    }

    public Color getBackgroundColor(){
        return gameBoard.getBackgroundColor();
    }


    public void setBallVisibility(boolean visibility){
        gameBoard.getBall().setVisibility(visibility);
    }

    public void resetPaddlePositions(){
        gameBoard.getRightPaddle().setLocation(new Point(1160,450));
        gameBoard.getLeftPaddle().setLocation(new Point(40,450));
    }

    public Color getPaddle1Color(){
        return gameBoard.getPaddle1Color();
    }

    public Color getPaddle2Color(){
        return gameBoard.getPaddle2Color();
    }

    public boolean getBallVisibility(){
        return gameBoard.getBall().getVisibility();
    }

    public String getTime(){
        int seconds = (int) gameBoard.getTime();

        int minutes = seconds/60;

        int secondsLeftover = seconds - minutes*60;

        String strMinutes = ""+minutes;
        String strSecondsLeftover = ""+secondsLeftover;

        if (strMinutes.length() == 1){
            strMinutes = "0"+strMinutes;
        }
        if(strSecondsLeftover.length() == 1){
            strSecondsLeftover = "0"+strSecondsLeftover;
        }

        return  strMinutes+":"+strSecondsLeftover;
    }

    public GameModes getGameMode(){
        return gameBoard.getGameMode();
    }


}