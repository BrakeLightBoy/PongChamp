package pongchamp.pongchamp.model.entities.powerups;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

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

    /*
    Creates a new randomized speed which is added to the ball (this also gives it a randomized direction)
    If the randomized speed ends up being 0 then it is automatically set to 3 (to avoid the ball completely stopping)
     */
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

    /*
    Resets the ball speed to its prior speed
     */
    public void deactivate(){
        ball.speedUp(-extraSpeedX,-extraSpeedY);
    }
}
