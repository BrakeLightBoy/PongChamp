package pongchamp.pongchamp.model.entities.powerups;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Properties;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.Point;

import java.awt.*;

public class InvisPower extends PowerUp {
    public InvisPower(Board gameBoard, Point location){
        super(gameBoard, location, Color.VIOLET);
    }
    public InvisPower(Board gameBoard, Point location, int duration, int radius) {
        super(gameBoard, location, duration, radius);
    }

    Ball ball = gameBoard.getBall();

    /*
    Sets the visibility to false, causing the rendering of the ball to be stopped
     */
    public void activate(){
        ball.setVisibility(false);
    }

    /*
    Resets the ball visibility to true, causing the rendering of the ball to continue
     */
    public void deactivate(){
        ball.setVisibility(true);
    }
}
