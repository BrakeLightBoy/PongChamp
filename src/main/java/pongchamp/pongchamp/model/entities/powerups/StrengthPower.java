package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collision;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Paddle;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.Point;

public class StrengthPower extends PowerUp{
    public StrengthPower(Board gameBoard, Point location){
        super(gameBoard, location, Color.CRIMSON);
    }
    public StrengthPower(Board gameBoard, Point location,int duration, int radius) {
        super(gameBoard, location, duration, radius);
    }

    private Paddle affectedPaddle;
    private Ball ball = gameBoard.getBall();

    @Override
    public void tick(){
        Collision paddleCollision = ball.checkCollision(affectedPaddle);

        if (paddleCollision.getColType() != CollisionTypes.NONE){
            ball.speedUp(1,0);
        }
    }

    public void onCollect(){
        System.out.println("OnCollect");
        gameBoard.getActivatedPowerUps().add(this);
        gameBoard.getMaintainedPowerUps().add(this);
        activate();
        agePowerUp();
    }

    public void activate(){
        if (gameBoard.getBall().getSpeed().getX()>0){
            //left paddle
            affectedPaddle = gameBoard.getLeftPaddle();
        } else {
            affectedPaddle = gameBoard.getRightPaddle();
        }
    }

    public void deactivate(){}
}
