package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.entities.Paddle;
import pongchamp.pongchamp.model.math.Point;
import static pongchamp.pongchamp.model.entities.powerups.Affected.*;

public class ElongatePaddlePower extends PowerUp{
    Paddle rightPaddle = gameBoard.getRightPaddle();
    Paddle leftPaddle = gameBoard.getLeftPaddle();

    Affected player;

    public ElongatePaddlePower(Board gameBoard,Point location){
        super(gameBoard,location);
    }
    public ElongatePaddlePower(Board gameBoard,Point location,int duration, int radius) {
        super(gameBoard,location, duration, radius);
    }
    public void activate(){
        if (gameBoard.getBall().getSpeed().getX()>0){
        //left paddle
            player = LEFT_PADDLE;
            leftPaddle.setHeight(leftPaddle.getHeight()*1.5f);
        }
        else {
            //right paddle
            player = RIGHT_PADDLE;
            rightPaddle.setHeight(rightPaddle.getHeight()*1.5f);
        }
    }

    public void deactivate(){
        if (player == LEFT_PADDLE){
            leftPaddle.setHeight(leftPaddle.getHeight()*2/3);
        }
        else {
            rightPaddle.setHeight(rightPaddle.getHeight()*2/3);
        }
    }

}
