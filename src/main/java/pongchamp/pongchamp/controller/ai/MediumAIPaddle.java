package pongchamp.pongchamp.controller.ai;

import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.CollisionTypes;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;

public class MediumAIPaddle extends AIPaddle{

    private int tick = 0;
    private boolean movedLastTick = false;

    public MediumAIPaddle(Point location, LineSegment movementPath, PaddleController paddleController, CollisionTypes paddleType, Ball target) {
        super(location, movementPath, paddleController, paddleType, target);
    }

    @Override
    public boolean movingUp() {

        if (tick % 2 == 0)return false;
        boolean move = location.getY() > target.getLocation().getY() +2;
        if(move && !movedLastTick){
            if (!randomBoolean(0.05)){
                movedLastTick = true;
                return true;
            }
        }
        else if (move) movedLastTick = false;
        return false;
    }

    @Override
    public boolean movingDown() {
        if (tick % 2 == 1)return false;
        boolean move = location.getY() <= target.getLocation().getY() -2;
        if(move && !movedLastTick){
            if (!randomBoolean(0.05)){
                movedLastTick = true;
                return true;
            }
        }
        else if (move) movedLastTick = false;
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        tick++;
    }

}
