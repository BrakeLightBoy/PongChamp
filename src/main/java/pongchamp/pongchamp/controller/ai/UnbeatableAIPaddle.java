package pongchamp.pongchamp.controller.ai;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.Properties;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

public class UnbeatableAIPaddle extends AIPaddle{

    private Point previousLocation;

    public UnbeatableAIPaddle(Point location, LineSegment movementPath, CollisionTypes paddleType, Ball target, Color paddleColor) {
        super(location,defaultPaddleWidth , defaultPaddleHeight, movementPath, paddleType,target, paddleColor);
        setPaddleController(null);
    }


    @Override
    public void tick() {
        previousLocation = new Point(location.getX(), location.getY());
        float targetCord = target.getLocation().getY();
        if (targetCord < 0 + height / 2 || targetCord > Properties.BOARD_HEIGHT - height / 2)return;
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
