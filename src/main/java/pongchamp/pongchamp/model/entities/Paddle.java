package pongchamp.pongchamp.model.entities;


import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

import static pongchamp.pongchamp.model.CollisionTypes.*;

public class Paddle extends Entity implements Collidable {

    protected float platformSpeed = Properties.PLATFORM_SPEED;
    private static final int defaultPaddleWidth = 20;
    private static final int defaultPaddleHeight = 100;

    protected LineSegment movementPath;
    protected PaddleController paddleController;
    protected float width;
    protected float height;
    protected HitBox paddleHitBox;
    protected CollisionTypes paddleType;

    public Paddle (Point initialLocation, LineSegment movementPath, CollisionTypes paddleType){
        this(initialLocation, defaultPaddleWidth, defaultPaddleHeight, movementPath, new PaddleController() {
            @Override
            public boolean movingUp() {
                return false;
            }

            @Override
            public boolean movingDown() {
                return false;
            }
        }, paddleType);
    }

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

    @Override
    public void tick() {

        if (paddleController.movingDown()){
            float next = location.getY();
            if (next + platformSpeed + height / 2 > Properties.BOARD_HEIGHT)return;
            Vector movementVector = new Vector(0,platformSpeed);
            location.movePoint(movementVector);
            paddleHitBox.moveHitBox(movementVector);

        }
        else if (paddleController.movingUp()){
            float next = location.getY();
            if (next - platformSpeed - height / 2 < 0)return;
            Vector movementVector = new Vector(0,-platformSpeed);
            location.movePoint(movementVector);
            paddleHitBox.moveHitBox(movementVector);

        }
    }

    public float getPlatformSpeed() {
        return platformSpeed;
    }

    public void setPlatformSpeed(float platformSpeed) {
        this.platformSpeed = platformSpeed;
    }
}
