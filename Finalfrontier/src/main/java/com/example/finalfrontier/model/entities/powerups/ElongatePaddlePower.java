package com.example.finalfrontier.model.entities.powerups;

import com.example.finalfrontier.model.Board;
import com.example.finalfrontier.model.entities.Paddle;
import com.example.finalfrontier.model.math.Point;
import javafx.scene.paint.Color;


import static com.example.finalfrontier.model.entities.powerups.Affected.LEFT_PADDLE;
import static com.example.finalfrontier.model.entities.powerups.Affected.RIGHT_PADDLE;

public class ElongatePaddlePower extends PowerUp{
    Paddle rightPaddle = gameBoard.getRightPaddle();
    Paddle leftPaddle = gameBoard.getLeftPaddle();

    Affected player;

    public ElongatePaddlePower(Board gameBoard, Point location){
        super(gameBoard,location, Color.GOLD);
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
