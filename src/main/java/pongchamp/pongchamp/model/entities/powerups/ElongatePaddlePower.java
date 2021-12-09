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

    public void onCollect(){
        System.out.println("Picked up!");
    }
}
