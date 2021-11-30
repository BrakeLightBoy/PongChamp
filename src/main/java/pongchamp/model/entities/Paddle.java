package pongchamp.model.entities;


import pongchamp.controller.PaddleController;
import pongchamp.model.Collidable;
import pongchamp.model.Collision;
import pongchamp.model.HitBox;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Point;

public abstract class Paddle extends Entity implements Collidable {
    protected LineSegment movementPath;
    protected PaddleController paddleController;
    protected float width;
    protected float height;
    protected HitBox paddleCenterHitBox, paddleLowerHitBox, paddleUpperHitBox;
    protected String paddleType;

    public Paddle(Point location, float width, float height , LineSegment movementPath, PaddleController paddleController, String paddleType) {
        super(location);
        if (!(paddleType.equals("left")||paddleType.equals("right"))){
            throw new IllegalArgumentException("Wrong paddle type");
        }
        this.paddleType = paddleType;
        this.movementPath = movementPath;
        this.paddleController = paddleController;
        this.width =  width;
        this.height = height;
        paddleCenterHitBox = new HitBox(location.getX()-width/2,location.getY()-height*4/10, location.getX()+width/2, location.getY()+height*4/10);


        paddleUpperHitBox = new HitBox(location.getX()-width/2,location.getY()+height*4/10, location.getX()+width/2, location.getY()+height/2);
        paddleLowerHitBox = new HitBox(location.getX()-width/2,location.getY()-height/2, location.getX()+width/2, location.getY()-height*4/10);
    }

    public abstract Collision checkBallCollision(Ball ball);



    public void setPaddleController(PaddleController paddleController) {
        this.paddleController = paddleController;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
