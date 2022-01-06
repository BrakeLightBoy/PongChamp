package pongchamp.pongchamp.model.entities.powerups;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collision;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.entities.Paddle;
import pongchamp.pongchamp.model.math.Point;

import static pongchamp.pongchamp.model.entities.powerups.Affected.LEFT_PADDLE;
import static pongchamp.pongchamp.model.entities.powerups.Affected.RIGHT_PADDLE;

public class StrengthPower extends PowerUp{
    public StrengthPower(Board gameBoard, Point location){
        super(gameBoard, location, Color.CRIMSON);
    }
    public StrengthPower(Board gameBoard, Point location,int duration, int radius) {
        super(gameBoard, location, duration, radius);
    }

    private Paddle affectedPaddle;

    private Ball ball = gameBoard.getBall();


    /*
    Checks if ball has collided with the affected paddle and if it has the ball is sped up
     */
    @Override
    public void tick(){
        Collision paddleCollision = ball.checkCollision(affectedPaddle);
        if (paddleCollision.getColType() != CollisionTypes.NONE){
            ball.speedUp(1,0);
        }
    }

    /*
    Adds the power up to the activated power up list AS well as maintained power up list, activates, and ages the power
    up
     */
    public void onCollect(){
        gameBoard.getActivatedPowerUps().add(this);
        gameBoard.getMaintainedPowerUps().add(this);
        activate();
        agePowerUp();
    }

    /*
    Sets the affected paddle based on the ball direction
     */
    public void activate(){
        if (gameBoard.getBall().getSpeed().getX()>0){
            affectedPaddle = gameBoard.getLeftPaddle();
        } else {
            affectedPaddle = gameBoard.getRightPaddle();
        }
    }

    public void deactivate(){}
}
