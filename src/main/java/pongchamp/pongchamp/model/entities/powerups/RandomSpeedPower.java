package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

import java.util.Random;

public class RandomSpeedPower extends PowerUp {
    int extraSpeedX,extraSpeedY;
    Ball ball = gameBoard.getBall();


    public RandomSpeedPower(Board gameBoard, Point location){
        super(gameBoard, location);
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

        float currentSpeedX = ball.getSpeed().getX();
        float currentSpeedY = ball.getSpeed().getY();
        Vector speedVector = new Vector(currentSpeedX+extraSpeedX,currentSpeedY+extraSpeedY);
        ball.setSpeed(speedVector);
    }

    public void deactivate(){
        float currentSpeedX = ball.getSpeed().getX();
        float currentSpeedY = ball.getSpeed().getY();
        Vector speedVector = new Vector(currentSpeedX-extraSpeedX,currentSpeedY-extraSpeedY);
        ball.setSpeed(speedVector);
    }
}
