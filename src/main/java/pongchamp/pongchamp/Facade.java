package pongchamp.pongchamp;
import pongchamp.pongchamp.model.Board;

public class Facade {
    Board gameboard;

    public void Facade(Board gameBoard) {
     this.gameboard = gameBoard;
    }


    public void updateBoardState(){
        gameboard.run();
    }

    public float[] getLeftPaddlePosition(){
        float[] leftPosition = new float[2];
        leftPosition[0] = gameboard.getLeftPaddle().getLocation().getX();
        leftPosition[1] = gameboard.getLeftPaddle().getLocation().getY();

        return leftPosition;
    }

    public float[] getLeftPaddleDimensions(){
        float[] leftDimensions = new float[2];
        leftDimensions[0] = gameboard.getLeftPaddle().getWidth();
        leftDimensions[1] = gameboard.getLeftPaddle().getHeight();

        return leftDimensions;
    }

    public float[] getRightPaddlePosition(){
        float[] rightPosition = new float[2];
        rightPosition[0] = gameboard.getRightPaddle().getLocation().getX();
        rightPosition[1] = gameboard.getRightPaddle().getLocation().getY();

        return rightPosition;
    }

    public float[] getRightPaddleDimensions(){
        float[] rightDimensions = new float[2];
        rightDimensions[0] = gameboard.getLeftPaddle().getWidth();
        rightDimensions[1] = gameboard.getLeftPaddle().getHeight();

        return rightDimensions;
    }

    public float[] getBallPosition(){
        float[] ballPosition = new float[2];
        ballPosition[0] = gameboard.getBall().getLocation().getX();
        ballPosition[1] = gameboard.getBall().getLocation().getY();

        return ballPosition;
    }

    public int getBallRadius(){
        return gameboard.getBall().getRadius();
    }

    public int[] getScore(){
        int[] score = new int[2];

        score[0] = gameboard.getLeftScore();
        score[1] = gameboard.getRightScore();
        return score;
    }




}
