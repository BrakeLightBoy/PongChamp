package pongchamp.model.entities;

import pongchamp.controller.PaddleController;
import pongchamp.model.*;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Location;
import pongchamp.model.math.Vector;

public class NormalPaddle extends Paddle {

    private static final float platformSpeed = Properties.PLATFORM_SPEED;
    private static final int defaultPaddleWidth = 15; //todo consider whether these sizes are good sizes in a 1200x900 board or not
    private static final int defaultPaddleHeight = 100;

    public NormalPaddle (Location initialLocation, LineSegment movementPath, PaddleController paddleController, Board board){ //if you don't put a height and width while making a paddle, the default sizes will be used
        this(initialLocation,defaultPaddleWidth,defaultPaddleHeight,movementPath,paddleController,board);
    }
    public NormalPaddle(Location initialLocation, float width, float height, LineSegment movementPath, PaddleController paddleController, Board board) {
        super(initialLocation,width,height,movementPath,paddleController,board);
    }

    @Override
    public void tick() {

        if (paddleController.movingDown()){
            location.add(new Vector(0,platformSpeed));
        }
        else if (paddleController.movingUp()){
            location.add(new Vector(0,platformSpeed * -1));
        }
    }

    @Override
    public void onBallHit(Ball ball) {

    }
}
