package pongchamp.model.entities;


import pongchamp.controller.PaddleController;
import pongchamp.model.Board;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Location;

public abstract class Paddle extends Entity {
    protected LineSegment movementPath;
    protected PaddleController paddleController;
    protected Board board;
    protected float width;
    protected float height;

    public Paddle(Location location, float width, float height , LineSegment movementPath, PaddleController paddleController, Board board) {
        super(location);
        this.movementPath = movementPath;
        this.paddleController = paddleController;
        this.board = board;
        this.width =  width;
        this.height = height;
    }

    public abstract void onBallHit(Ball ball);

    public void setPaddleController(PaddleController paddleController) {
        this.paddleController = paddleController;
    }
}
