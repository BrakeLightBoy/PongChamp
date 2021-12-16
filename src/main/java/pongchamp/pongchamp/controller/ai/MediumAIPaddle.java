package pongchamp.pongchamp.controller.ai;

import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

public class MediumAIPaddle extends AIPaddle{

    private int tick = 0;
    private boolean notMovedLastTick = false;
    private Vector lastVelocity;

    private int nextTickToMove = 0;

    private float lastYs [] = new float[3];

    public MediumAIPaddle(Point location, LineSegment movementPath, PaddleController paddleController, CollisionTypes paddleType, Ball target) {
        super(location, movementPath, paddleController, paddleType, target);
        lastVelocity = new Vector(0,0);
    }

    @Override
    public boolean movingUp() {

        if (notMovedLastTick){
            notMovedLastTick = false;
            return false;
        }
        if (tick % 2 == 0)return false;

        boolean move = location.getY() > target.getLocation().getY();
        if (randomBoolean(0.15)){
            notMovedLastTick = true;
            return false;
        }

        return move;
    }

    @Override
    public boolean movingDown() {


        if (notMovedLastTick){
            notMovedLastTick = false;
            return false;
        }
        if (tick % 2 == 1)return false;

        boolean move = location.getY() < target.getLocation().getY();
        if (randomBoolean(0.15)){
            notMovedLastTick = true;
            return false;
        }

        return move;

    }

    @Override
    public void tick() {

        checkBounce();
        super.tick();
        tick++;
    }

    private boolean checkBounce(){
        //?
        float previous = lastVelocity.getY();
        //?
        float current = target.getSpeed().getY();
        //System.out.println(previous + " - " + current);
        boolean bounce = oppositeSigns(previous,current);
        lastVelocity = target.getSpeed();
        if (bounce) System.out.println("BOUNCE!");
        return bounce;
    }

    private static boolean oppositeSigns(float x, float y)
    {
        return (x > 0 && y < 0 || x < 0 && y > 0);
        //return ((x ^ y) < 0);
    }

}
