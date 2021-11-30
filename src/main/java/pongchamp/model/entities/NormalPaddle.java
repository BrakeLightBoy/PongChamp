package pongchamp.model.entities;

import pongchamp.controller.PaddleController;
import pongchamp.model.*;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Point;
import pongchamp.model.math.Vector;

public class NormalPaddle extends Paddle {

    private static final float platformSpeed = Properties.PLATFORM_SPEED;
    private static final int defaultPaddleWidth = 15; //todo consider whether these sizes are good sizes in a 1200x900 board or not
    private static final int defaultPaddleHeight = 100;


    public NormalPaddle (Point initialLocation, LineSegment movementPath, PaddleController paddleController,String paddleType){ //if you don't put a height and width while making a paddle, the default sizes will be used
        this(initialLocation,defaultPaddleWidth,defaultPaddleHeight,movementPath,paddleController,paddleType);
    }
    public NormalPaddle(Point initialLocation, float width, float height, LineSegment movementPath, PaddleController paddleController,String paddleType) {
        super(initialLocation,width,height,movementPath,paddleController,paddleType);
    }

    @Override
    public void tick() {

        if (paddleController.movingDown()){
            location.movePoint(new Vector(0,platformSpeed));
        }
        else if (paddleController.movingUp()){
            location.movePoint(new Vector(0,-platformSpeed));
        }
    }

    @Override
    public Collision checkBallCollision(Ball ball) {
        if (paddleHitBox.checkBallIntersect(ball)){
            if (paddleType.equals("left")){
                return new Collision("paddle-left");
            }
            else {
                return new Collision("paddle-right");
            }
        }
        return null;
    }
}
