package com.example.finalfrontier.model.entities.powerups;

import com.example.finalfrontier.model.Board;
import com.example.finalfrontier.model.entities.Ball;
import javafx.scene.paint.Color;
import com.example.finalfrontier.model.math.Point;

import java.util.Random;

public class RandomSpeedPower extends PowerUp {
    int extraSpeedX,extraSpeedY;
    Ball ball = gameBoard.getBall();


    public RandomSpeedPower(Board gameBoard, Point location){
        super(gameBoard, location, Color.DARKGREEN);
    }
    public RandomSpeedPower(Board gameBoard, Point location,int duration, int radius){
        super(gameBoard, location,duration,radius);
    }

//    public void onCollect(){
//        System.out.println("Picked up!");
//    }

    public void activate(){
        Random random = new Random();
        extraSpeedX = random.nextInt(2);

        if (extraSpeedX==0){
            extraSpeedX =3;
        }

        extraSpeedY = random.nextInt(4)-2;
        if (extraSpeedY==0){
            extraSpeedY =3;
        }

        ball.speedUp(extraSpeedX,extraSpeedY);
    }

    public void deactivate(){
        ball.speedUp(-extraSpeedX,-extraSpeedY);
    }
}
