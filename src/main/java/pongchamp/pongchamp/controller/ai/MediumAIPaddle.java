package pongchamp.pongchamp.controller.ai;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;

public class MediumAIPaddle extends AIPaddle{

    private int tick;
    boolean decreaseSpeed;
    boolean onProcess = false;
    float previousSpeed;
    boolean restorePreviousSpeed = false;



    public MediumAIPaddle(Point location, LineSegment movementPath, CollisionTypes paddleType, Ball target, Color paddleColor) {
        super(location, movementPath, paddleType, target, paddleColor);
        setPlatformSpeed(6f);
    }

    public MediumAIPaddle(Point location, float width, float height, LineSegment movementPath, CollisionTypes paddleType, Ball target, Color paddleColor){
        super(location,width,height,movementPath,paddleType,target,paddleColor);
        setPlatformSpeed(6f);
    }

    @Override
    public boolean movingUp() {


        float targetY = target.getLocation().getY();

        boolean move = getLocation().getY() > targetY;


        if (move){
            if (Math.abs(targetY - getLocation().getY()) < getPlatformSpeed()) {
                previousSpeed = getPlatformSpeed();
                restorePreviousSpeed = true;
                setPlatformSpeed(getPlatformSpeed() / 2);
            }

        }

        return move;
    }

    @Override
    public boolean movingDown
            () {


        float targetY = target.getLocation().getY();

        boolean move = getLocation().getY() < targetY;

        if (move){
            if (Math.abs(targetY - getLocation().getY()) < getPlatformSpeed()) {
                previousSpeed = getPlatformSpeed();
                restorePreviousSpeed = true;
                setPlatformSpeed(getPlatformSpeed() / 2);
            }
        }

        return move;

    }

    @Override
    public void tick() {
        super.tick();

        if (restorePreviousSpeed){
            restorePreviousSpeed = false;
            setPlatformSpeed(previousSpeed);
        }

        if (onProcess) {
            if (getPlatformSpeed() <= 1) {
                decreaseSpeed = false;
            }
            if (decreaseSpeed) {
                setPlatformSpeed(getPlatformSpeed() - 0.5f);
            } else {
                setPlatformSpeed(getPlatformSpeed() + 0.5f);
                if (getPlatformSpeed() >= 6)
                    onProcess = false;
            }


        }
        if (tick % 16 == 0 && !onProcess) {
            if (randomBoolean(.4)) {
                onProcess = true;
                decreaseSpeed = true;
            }
        }

        tick++;
    }

}
