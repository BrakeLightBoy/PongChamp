package pongchamp.model.entities;

import pongchamp.controller.PaddleController;
import pongchamp.model.*;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Point;
import pongchamp.model.math.Vector;

public class NormalPaddle extends Paddle {

    private static final float platformSpeed = Properties.PLATFORM_SPEED;
    private static final int defaultPaddleWidth = 20; //todo consider whether these sizes are good sizes in a 1200x900 board or not
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
            Vector movementVector = new Vector(0,platformSpeed);
            location.movePoint(movementVector);
            paddleCenterHitBox.moveBox(movementVector);
            paddleLowerHitBox.moveBox(movementVector);
            paddleUpperHitBox.moveBox(movementVector);

        }
        else if (paddleController.movingUp()){
            Vector movementVector = new Vector(0,-platformSpeed);
            location.movePoint(movementVector);
            paddleCenterHitBox.moveBox(movementVector);
            paddleLowerHitBox.moveBox(movementVector);
            paddleUpperHitBox.moveBox(movementVector);
        }
    }

    @Override
    public Collision checkBallCollision(Ball ball) {
        Boolean center = paddleCenterHitBox.checkBallIntersect(ball);
        Boolean upper = paddleLowerHitBox.checkBallIntersect(ball);
        Boolean lower = paddleUpperHitBox.checkBallIntersect(ball);

        String data = "paddle-";
        if (paddleType.equals("left")){
            data += "left";
        }
        else {
            data += "right";
        }
        if (center){
            data +="-center";
            return new Collision(data);
        }
        else if (upper){
            System.out.println("Ucorner");
            data +="-uCorner";
            return new Collision(data);
        }
        else if (lower){
            System.out.println("lCorner");
            data +="-lCorner";
            return new Collision(data);
        }

        return null;
    }
}
