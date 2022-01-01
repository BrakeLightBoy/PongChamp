package pongchamp.model.entities.powerups;

import pongchamp.model.Board;
import pongchamp.model.entities.Ball;
import pongchamp.model.Properties;
import javafx.scene.paint.Color;
import pongchamp.model.math.Point;

public class InvisPower extends PowerUp {
    public InvisPower(Board gameBoard, Point location){
        super(gameBoard, location, Color.VIOLET);
    }
    public InvisPower(Board gameBoard, Point location, int duration, int radius) {
        super(gameBoard, location, duration, radius);
    }

    Ball ball = gameBoard.getBall();

    public void activate(){
        System.out.println("The ball is invisible! Get recked bruh!");
        ball.setBallColor(Color.BLACK);
        ball.setVisibility(false);
    }

    public void deactivate(){
        ball.setBallColor(Properties.BALL_DEFAULT_COLOR);
        ball.setVisibility(true);
    }
}
