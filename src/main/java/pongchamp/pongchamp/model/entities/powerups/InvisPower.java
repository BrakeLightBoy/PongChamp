package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.Point;

public class InvisPower extends PowerUp {
    public InvisPower(Board gameBoard, Point location){
        super(gameBoard, location);
    }
    public InvisPower(Board gameBoard, Point location, int duration, int radius) {
        super(gameBoard, location, duration, radius);
    }

    Ball ball = gameBoard.getBall();

    public void activate(){
        System.out.println("The ball is invisible! Get recked bruh!");
        ball.setVisibility(false);
    }

    public void deactivate(){
        ball.setVisibility(true);
    }
}
