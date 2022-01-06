package pongchamp.pongchamp;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.GameModes;
import pongchamp.pongchamp.model.UserSettings;
import pongchamp.pongchamp.model.entities.powerups.PowerUp;
import pongchamp.pongchamp.controller.json.JsonAPI;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Facade {
    JsonAPI jsonAPI;
    private final Board gameBoard;

    public Facade(GameModes gameMode, boolean withPowerUps) throws IOException {
        this.jsonAPI = new JsonAPI();
        this.gameBoard = new Board(gameMode,withPowerUps,jsonAPI.loadSettings());
    }

    public Facade(Board board) {
        this.jsonAPI = new JsonAPI();
        this.gameBoard = board;
    }


    /*
    Makes the board class advance the simulation of the game by 1 tick.
    */
    public void updateBoardState(){
        if (!isPaused() && !getGameEnd()){
            gameBoard.run();
        }
    }

    /*
    returns x and y coordinates for the current position for the center of the left paddle.
    */
    public float[] getLeftPaddlePosition(){
        float[] leftPosition = new float[2];
        leftPosition[0] = gameBoard.getLeftPaddle().getLocation().getX();
        leftPosition[1] = gameBoard.getLeftPaddle().getLocation().getY();

        return leftPosition;
    }

    /*
    returns the current width and height of the left paddle.
    */
    public float[] getLeftPaddleDimensions(){
        float[] leftDimensions = new float[2];
        leftDimensions[0] = gameBoard.getLeftPaddle().getWidth();
        leftDimensions[1] = gameBoard.getLeftPaddle().getHeight();

        return leftDimensions;
    }

    /*
    returns x and y coordinates for the current position for the center of the right paddle.
    */
    public float[] getRightPaddlePosition(){
        float[] rightPosition = new float[2];
        rightPosition[0] = gameBoard.getRightPaddle().getLocation().getX();
        rightPosition[1] = gameBoard.getRightPaddle().getLocation().getY();

        return rightPosition;
    }

    /*
    returns the current width and height of the left paddle.
    */
    public float[] getRightPaddleDimensions(){
        float[] rightDimensions = new float[2];
        rightDimensions[0] = gameBoard.getRightPaddle().getWidth();
        rightDimensions[1] = gameBoard.getRightPaddle().getHeight();

        return rightDimensions;
    }

    /*
    returns x and y coordinates for current position for the center of the ball.
    */
    public float[] getBallPosition(){
        float[] ballPosition = new float[2];
        ballPosition[0] = gameBoard.getBall().getLocation().getX();
        ballPosition[1] = gameBoard.getBall().getLocation().getY();

        return ballPosition;
    }

    /*
    returns the current radius of the ball.
    */
    public int getBallRadius(){
        return gameBoard.getBall().getRadius();
    }

    /*
    returns the current score
    */
    public int[] getScore(){
        int[] score = new int[2];

        score[0] = gameBoard.getLeftScore();
        score[1] = gameBoard.getRightScore();
        return score;
    }

    /*

    */
    public void setRightPaddleController(PaddleController paddleController){
        gameBoard.getRightPaddle().setPaddleController(paddleController);
    }

    /*

    */
    public void setLeftPaddleController(PaddleController paddleController){
        gameBoard.getLeftPaddle().setPaddleController(paddleController);
    }

    /*
    Returns a HashMap containing x and y coordinates for all currently spawned power ups.
    The HashMap has the class name of the contained power as the key and an arrayList containing all
    x/y coordinate pairs of that type (so if there are 2 power ups of the same type currently spawned, the arrayList
    will have 2 x/y coordinate pairs stored). It would have been simpler to simply send the power up objects, but
    to prevent security issues, we "repackage" the information to achieve more thorough separation of the frontend and
    the backend.
    */
    public HashMap<Class<? extends PowerUp>,ArrayList<Float[]>> returnPowerMap(){
        HashMap<Class<? extends PowerUp>,ArrayList<Float[]>> powerUpsMap = new HashMap<>();
        HashSet<Class<? extends PowerUp>> powerNames = new HashSet<>();

        for (PowerUp power : gameBoard.getSpawnedPowers()){
            powerNames.add(power.getClass());
        }
        for (Class<? extends  PowerUp> name : powerNames){
            powerUpsMap.put(name,new ArrayList<>());
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

    /*
    Returns a HashMap containing color information for all currently spawned power ups.
    The HashMap has the class name of the contained power as the key and the corresponding power up color as the values.
    */
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

    /*
       returns the current color of the ball
    */
    public Color getBallColor(){
        return gameBoard.getBallColor();
    }

    /*
        returns true if the game is currently paused and false if not.
    */
    public Boolean isPaused() {
        return gameBoard.getPaused();
    }

    /*
    returns true if the game has been won/lost or was exited.
    */
    public Boolean getGameEnd() {
        return gameBoard.getHasEnded();
    }

    /*
    returns a String which says who won the game.
    */
    public String getGameWinner() {
        return gameBoard.getGameWinner();
    }

    /*
    causes the board to pause the game.
    */
    public void pauseGame(){
        gameBoard.setPaused(true);
    }

    /*
    causes the board to end the game.
    */
    public void endGame(){
        gameBoard.setHasEnded(true);
    }

    /*
    causes the board to resume the game.
    */
    public void resumeGame(){
        gameBoard.setPaused(false);
        setBallVisibility(true);
    }

    /*
    causes the board to restart the game/
    */
    public void gameRestart(){
        gameBoard.restartGame();
        resumeGame();
        gameBoard.setTime(0);
    }

    /*
    returns the current color of the background
    */
    public Color getBackgroundColor(){
        return gameBoard.getBackgroundColor();
    }

    /*
        sets the visibility of the ball to true or false.
    */
    public void setBallVisibility(boolean visibility){
        gameBoard.getBall().setVisibility(visibility);
    }

    /*
    gets the color of the left paddle
    */
    public Color getPaddle1Color(){
        return gameBoard.getPaddle1Color();
    }

    /*
    gets the color of the right paddle
    */
    public Color getPaddle2Color(){
        return gameBoard.getPaddle2Color();
    }

    /*
    returns true if the ball is visible and false if it's not.
    */
    public boolean getBallVisibility(){
        return gameBoard.getBall().getVisibility();
    }

    /*
    returns the time elapsed since the start of the round (time when the game was paused is not
    included) as a string of the format <minutes>:<seconds>
    */
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



    /*
    returns an enum that indicates what gamemode is currently being played.
    */
    public GameModes getGameMode(){
        return gameBoard.getGameMode();
    }

    /*
        saves the current state of the game to a JSON file.
    */
    public void saveGame() throws IOException {
        jsonAPI.saveGame(gameBoard);
    }
}