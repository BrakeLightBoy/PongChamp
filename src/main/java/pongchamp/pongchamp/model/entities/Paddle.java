package pongchamp.pongchamp.model.entities;


import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import static pongchamp.pongchamp.model.ObstactleTypes.*;
import static pongchamp.pongchamp.model.CollisionTypes.*;

public abstract class Paddle extends Entity implements Collidable {
    protected LineSegment movementPath;
    protected PaddleController paddleController;
    protected float width;
    protected float height;
    protected HitBox paddleHitBox;
    protected CollisionTypes paddleType;

    public Paddle(Point location, float width, float height , LineSegment movementPath, PaddleController paddleController, CollisionTypes paddleType) {
        super(location);
        if (! (paddleType == LEFT || paddleType == RIGHT) ){
            throw new IllegalArgumentException("Wrong paddle type");
        }
        this.paddleType = paddleType;
        this.movementPath = movementPath;
        this.paddleController = paddleController;
        this.width =  width;
        this.height = height;

        adjustHitBox();
    }

    private void adjustHitBox(){
        paddleHitBox = new HitBox(location.getX()-width/2f,location.getY()-height/2f, location.getX()+width/2f, location.getY()+height/2f);
    }

    public void setPaddleController(PaddleController paddleController) {
        this.paddleController = paddleController;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        adjustHitBox();
    }
}
