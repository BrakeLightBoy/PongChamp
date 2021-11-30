package pongchamp.model.entities;

import pongchamp.controller.PaddleController;
import pongchamp.model.*;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Location;
import pongchamp.model.math.Vector;

public class NormalPaddle extends Paddle {

    private final float platformSpeed = Properties.PLATFORM_SPEED;

    public NormalPaddle(Location location, float width, float height, LineSegment movementPath, PaddleController paddleController, Board board) {
        super(location, width,height,movementPath,paddleController,board );
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
