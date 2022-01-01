package pongchamp.controller.ai;

import pongchamp.controller.PaddleController;
import pongchamp.model.CollisionTypes;
import pongchamp.model.entities.Paddle;
import pongchamp.model.entities.Ball;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Point;

public abstract class AIPaddle extends Paddle implements PaddleController {

    protected static final int defaultPaddleWidth = 20; //todo consider whether these sizes are good sizes in a 1200x900 board or not
    protected static final int defaultPaddleHeight = 100;

    protected Ball target;

    public AIPaddle(Point location, LineSegment movementPath, CollisionTypes paddleType, Ball target) {
        this(location, defaultPaddleWidth, defaultPaddleHeight, movementPath, paddleType,target);

    }

    public AIPaddle(Point location, float width, float height, LineSegment movementPath, CollisionTypes paddleType,Ball target) {
        super(location, width, height, movementPath, null, paddleType);
        this.target = target;
        setPaddleController(this);
    }

    protected boolean randomBoolean(double chance){ //number should be between 0 and 1
        return Math.random() < chance;
    }
}
