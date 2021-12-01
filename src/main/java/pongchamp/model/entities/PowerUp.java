package pongchamp.model.entities;

import pongchamp.model.Board;
import pongchamp.model.math.Point;

public abstract class PowerUp extends Entity {
    public PowerUp(Board board, Point location) {
        super(board, location);
    }

    public abstract void onBallHit(Ball ball);
}
