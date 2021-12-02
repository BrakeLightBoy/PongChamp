package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.math.Point;

public abstract class PowerUp extends Entity {
    public PowerUp(Board board, Point location) {
        super(board, location);
    }

    public abstract void onBallHit(Ball ball);
}
