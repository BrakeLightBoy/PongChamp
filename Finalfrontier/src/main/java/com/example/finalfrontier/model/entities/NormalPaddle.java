package com.example.finalfrontier.model.entities;

import com.example.finalfrontier.controller.PaddleController;
import com.example.finalfrontier.model.CollisionTypes;
import com.example.finalfrontier.model.math.LineSegment;
import com.example.finalfrontier.model.math.Point;
import com.example.finalfrontier.model.*;

public class NormalPaddle extends Paddle {


    private static final int defaultPaddleWidth = 20; //todo consider whether these sizes are good sizes in a 1200x900 board or not
    private static final int defaultPaddleHeight = 100;


    public NormalPaddle (Point initialLocation, LineSegment movementPath, PaddleController paddleController, CollisionTypes paddleType){ //if you don't put a height and width while making a paddle, the default sizes will be used
        this(initialLocation,defaultPaddleWidth,defaultPaddleHeight,movementPath,paddleController,paddleType);
    }
    public NormalPaddle(Point initialLocation, float width, float height, LineSegment movementPath, PaddleController paddleController,CollisionTypes paddleType) {
        super(initialLocation,width,height,movementPath,paddleController,paddleType);
    }





}
