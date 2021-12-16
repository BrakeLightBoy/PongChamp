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
        lastVelocity = target.getSpeed();
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
        if (tick < 3){
            lastVelocity = target.getSpeed();
        }
        else {
            checkBounce();
        }
        super.tick();
        tick++;
    }

    private boolean checkBounce(){
        //?
        int previous = (int)lastVelocity.getY();
        //?
        int current = (int)target.getSpeed().getY();
        System.out.println(previous + " - " + current);
        boolean bounce = oppositeSigns(previous,current);
        lastVelocity = target.getSpeed();
        if (bounce) System.out.println("BOUNCE!");
        return bounce;
    }

    private static boolean oppositeSigns(int x, int y)
    {
        return !(x > 0 && y > 0 || x < 0 && y < 0);
        //return ((x ^ y) < 0);
    }

}
