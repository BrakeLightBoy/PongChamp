package pongchamp.pongchamp.controller.ai;

import javafx.util.Pair;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

public class MediumAIPaddle extends AIPaddle{

    private int tick = 0;
    private boolean notMovedLastTick = false;
    private int nextTickToMove = 0;
    private final float[] lastYs = new float[3];
    public MediumAIPaddle(Point location, LineSegment movementPath, PaddleController paddleController, CollisionTypes paddleType, Ball target) {
        super(location, movementPath, paddleController, paddleType, target);
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
        double chanceOfNotMoving = target.getVisibility() ? 0.00:0.75;
        if (randomBoolean(chanceOfNotMoving)){
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
        double chanceOfNotMoving = target.getVisibility() ? 0.00:0.75;
        if (randomBoolean(chanceOfNotMoving)){
            notMovedLastTick = true;
            return false;
        }

        return move;

    }

    @Override
    public void tick() {
        super.tick();
        if (checkBounce()){
            nextTickToMove = tick + 17;
        }
        tick++;
    }

    private boolean checkBounce(){

        if (tick  == 0){
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
        if (bounce) System.out.println("bounce!");
        return bounce;
    }

    private static boolean oppositeSigns(float x, float y)
    {
        return (x > 0 && y < 0 || x < 0 && y > 0);
        //return ((x ^ y) < 0);
    }

}
