package pongchamp.pongchamp.controller.ai;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.entities.Paddle;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;

public abstract class AIPaddle extends Paddle implements PaddleController {

    protected static final int defaultPaddleWidth = 20; //todo consider whether these sizes are good sizes in a 1200x900 board or not
    protected static final int defaultPaddleHeight = 100;

    protected Ball target;


    public AIPaddle(Point location, LineSegment movementPath, CollisionTypes paddleType,Ball target, Color paddleColor) {
        this(location, defaultPaddleWidth, defaultPaddleHeight, movementPath, paddleType,target, paddleColor);

    }

    public AIPaddle(Point location, float width, float height, LineSegment movementPath, CollisionTypes paddleType, Ball target, Color paddleColor) {
        super(location, width, height, movementPath, null, paddleType, paddleColor);
        this.target = target;
        setPaddleController(this);
    }

    protected boolean randomBoolean(double chance){ //number should be between 0 and 1
        return Math.random() < chance;
    }
}
