package pongchamp.pongchamp.controller.ai;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;

public class MediumAIPaddle extends AIPaddle{

    private int tick = 0;
    private boolean notMovedLastTick = false;
    private int nextTickToMove = 0;
    private final float[] lastYs = new float[3];
    private final static double chanceOfNotMovingWithVisibleBall = 0.01;
    private final static double chanceOfNotMovingWithInvisibleBall = 0.75;
    private int movingUp;
    private int movingDown;
    float x = 5f;
    int counter = 0;
    int speedUp;
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

        boolean move = location.getY() > targetY;


        if (move){
            if (Math.abs(targetY - location.getY()) < getPlatformSpeed()) {
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

        boolean move = location.getY() < targetY;

        if (move){
            if (Math.abs(targetY - location.getY()) < getPlatformSpeed()) {
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

        System.out.println(getPlatformSpeed());

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
        /*if (getPlatformSpeed() == 0) {
            counter++;
            if (counter == 1) {
                x=8;
                counter = 0;
                speedUp = tick + 4;
            }
        }*/
       /* if (checkBounce()) {
            if (randomBoolean(0.3)) {
                float distance = Math.abs(target.getLocation().getX() - this.location.getX());
                if (distance < 200)
                    nextTickToMove = tick + 20;
                else
                    nextTickToMove = tick + 24;
            }
        }*/
        tick++;
    }

    private boolean checkBounce(){

        if (tick  == 0){  //change to switch maybe
            lastYs[0] = target.getLocation().getY();
        }
        else if (tick  == 1){
            lastYs[1] = target.getLocation().getY();
        }
        else if (tick == 2){
            lastYs[2] = target.getLocation().getY();
        }
        else {
            lastYs[0] = lastYs[1];
            lastYs[1] = lastYs[2];
            lastYs[2] = target.getLocation().getY();
        }
        if (tick < 3)return false;
        boolean bounce = false;
        if (lastYs[0] < lastYs[1] && lastYs[1] > lastYs[2])
            bounce = true;
        else if (lastYs[0] > lastYs[1] && lastYs[1] < lastYs[2])
            bounce = true;
        return bounce;
    }

    private boolean notMoving(){
        double chanceOfNotMoving = target.getVisibility() ? chanceOfNotMovingWithVisibleBall:chanceOfNotMovingWithInvisibleBall;
        return randomBoolean(chanceOfNotMoving);
    }

}
