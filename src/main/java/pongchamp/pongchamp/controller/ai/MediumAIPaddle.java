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

    public MediumAIPaddle(Point location, LineSegment movementPath, CollisionTypes paddleType, Ball target, Color paddleColor) {
        super(location, movementPath, paddleType, target, paddleColor);
    }

    @Override
    public boolean movingUp() {

        if (tick < nextTickToMove)return false;

        if (notMovedLastTick){
            notMovedLastTick = false;
            return false;
        }
        if (tick % 4 == 0)return false;

        boolean move = location.getY() > target.getLocation().getY();
        if (notMoving()){
            notMovedLastTick = true;
            return false;
        }

        return move;
    }

    @Override
    public boolean movingDown() {

        if (tick < nextTickToMove)return false;

        if (notMovedLastTick){
            notMovedLastTick = false;
            return false;
        }
        if (tick % 4 == 2)return false;

        boolean move = location.getY() < target.getLocation().getY();
        if (notMoving()){
            notMovedLastTick = true;
            return false;
        }

        return move;

    }

    @Override
    public void tick() {
        super.tick();
        if (checkBounce()){
            float distance = Math.abs(target.getLocation().getX() - this.location.getX());
            if (distance < 200)
                nextTickToMove = tick + 12;
            else
                nextTickToMove = tick + 16;
        }
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
