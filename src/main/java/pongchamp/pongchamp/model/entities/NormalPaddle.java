package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

public class NormalPaddle extends Paddle {


    private static final int defaultPaddleWidth = 20; //todo consider whether these sizes are good sizes in a 1200x900 board or not
    private static final int defaultPaddleHeight = 100;


    public NormalPaddle (Point initialLocation, LineSegment movementPath, PaddleController paddleController,CollisionTypes paddleType){ //if you don't put a height and width while making a paddle, the default sizes will be used
        this(initialLocation,defaultPaddleWidth,defaultPaddleHeight,movementPath,paddleController,paddleType);
    }
    public NormalPaddle(Point initialLocation, float width, float height, LineSegment movementPath, PaddleController paddleController,CollisionTypes paddleType) {
        super(initialLocation,width,height,movementPath,paddleController,paddleType);
    }





}
