package pongchamp.pongchamp.controller.ai;

import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

public class UnbeatableAIPaddle extends AIPaddle{




    private Point previousLocation;


    public UnbeatableAIPaddle(Point location, LineSegment movementPath, PaddleController paddleController, CollisionTypes paddleType, Ball target) {
        super(location,defaultPaddleWidth , defaultPaddleHeight, movementPath, paddleController, paddleType,target);
        setPaddleController(null);
    }


    @Override
    public void tick() {
        previousLocation = new Point(location.getX(), location.getY());
        this.location.setY(target.getLocation().getY());
        Vector dif = new Vector(location.getX() -previousLocation.getX() ,location.getY()- previousLocation.getY());
        this.paddleHitBox.moveHitBox(dif);
    }


    @Override
    public boolean movingUp() {
        return false;
    }

    @Override
    public boolean movingDown() {
        return false;
    }
}
