package pongchamp.pongchamp.model.entities;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;
import static pongchamp.pongchamp.model.ObstactleTypes.*;
import static pongchamp.pongchamp.model.CollisionTypes.*;

import java.util.ArrayList;

public class Ball extends Entity {
    private int radius;
    protected Vector speed,acceleration;
    private Boolean isVisible;
    Color ballColor;

    public Ball(Point location, int radius, Vector speed, Vector acceleration,Color ballColor) {
        super(location);
        isVisible=true;
        this.radius = radius;
        this.speed = speed;
        this.acceleration = acceleration;
        this.ballColor = ballColor;
    }

    /*
    Changes the ball speed (on both the x- and y-axis)
    */
    public void speedUp(float xSpeed,float ySpeed){
        float currentSpeedX = speed.getX();
        float currentSpeedY = speed.getY();

        //prevents the ball from accidentally stopping (on the x-axis) due to power ups.
        float modSpeedX = Math.abs(currentSpeedX)+xSpeed;
        if (currentSpeedX<0){
            modSpeedX *= -1;
        }

        //prevents the ball from accidentally stopping (on the y-axis) due to power ups.
        float modSpeedY = Math.abs(currentSpeedY)+ySpeed;
        if (currentSpeedX<0){
            modSpeedY *= -1;
        }

        Vector speedVector = new Vector(modSpeedX,modSpeedY);
        setSpeed(speedVector);
    }


    /*
    Moves the ball based its current location in addition to its speed, as well as adding acceleration.
    Then checks if the ball has collided with collidable obstacles and if so, calls the onCollision method with the
    collision passed through the method.
     */
    public void move(ArrayList<Collidable> obstacles) {
        getLocation().movePoint(speed);
        speed.addVector(acceleration);

        for (Collidable obstacle : obstacles){
            Collision collision = checkCollision(obstacle);
            onCollision(collision);
        }
    }

    /*
        Checks if the provided obstacle (wall or paddle) is currently colliding with the ball (returns a collision object that
        has the information obout the details of the collision).
    */
    public Collision checkCollision(Collidable obstacle){
        if (obstacle instanceof Wall){
            return checkCollision((Wall) obstacle);
        }
        else {
            return checkCollision((Paddle) obstacle);
        }
    }


    /*
    checks if the provided wall is currently being collided with the ball
    and returns a collision object that has the information about the type of the wall (upper, lower, left or right wall or
    a none if the collision did not occur).
    */
    public Collision checkCollision(Wall wall){
        LineSegment wallLine = wall.getWallLine();

        switch (wall.getWallType()){
            case RIGHT:
                if (getLocation().getX()+getRadius()>=wallLine.getStartPoint().getX()){
                    return new Collision(RIGHT,WALL);
                }
                break;
            case LEFT:
                if (getLocation().getX()- getRadius()<=wallLine.getStartPoint().getX()){
                    return new Collision(LEFT,WALL);
                }
                break;
            case UPPER:
                if (getLocation().getY()+getRadius()>=wallLine.getStartPoint().getY()){
                    return new Collision(LOWER,WALL);
                }
                break;
            case LOWER:
                if (getLocation().getY()- getRadius()<=wallLine.getStartPoint().getY()){
                    return new Collision(UPPER,WALL);
                }
                break;
        }
        return new Collision(NONE,WALL);
    }

    /*
    Checks for the collision of a ball with the paddles returning a Collision.
     */
    public Collision checkCollision(Paddle paddle){
        HitBox.hitBoxCollision collidedPart = paddle.paddleHitBox.checkBallIntersect(this);

        if (collidedPart == HitBox.hitBoxCollision.NONE){
            return new Collision(NONE,PADDLE);
        }


        CollisionTypes collisionType;
        /*
        Determines what type of collision is needed, based off of what paddle wall has hit the ball. (E.g if the
        collision was with the 'VERTICAL' wall then the ball direction should only change to 'LEFT' or 'RIGHT'.
         */
        if (collidedPart == HitBox.hitBoxCollision.VERTICAL) {
            if (paddle.paddleType == LEFT) {
                collisionType = LEFT;
            }
            else {
                collisionType = RIGHT;
            }
        }
        else if (collidedPart == HitBox.hitBoxCollision.HORIZONTAL) {
            if (getLocation().getY()< paddle.getLocation().getY()){
                collisionType = LOWER;
            }
            else {
                collisionType = UPPER;
            }
        }
        /*
        Else statement for if the ball has collided with any of the paddle corners.
         */
        else {
            if ( (getLocation().getY()< paddle.getLocation().getY() && paddle.paddleType == LEFT) ){
                collisionType = LEFT_LOWER;
            }
            else if ( (getLocation().getY()< paddle.getLocation().getY() && paddle.paddleType == RIGHT) ){
                collisionType = RIGHT_LOWER;
            }
            else if ( (paddle.paddleType == RIGHT) ){
                collisionType = RIGHT_UPPER;
            }
            else {
                collisionType = LEFT_UPPER;
            }
        }
        return new Collision(collisionType,PADDLE);
    }


    /*
    Determines the change of the ball speed vector based on the collision type.
     */
    public void onCollision(Collision collision) {
        CollisionTypes colType = collision.getColType();

        switch (colType){
            case NONE:
                return;
            case LEFT:
                speed.setX(Math.abs(speed.getX()));
                break;
            case RIGHT:
                speed.setX(-Math.abs(speed.getX()));
                break;
            case UPPER:
                speed.setY(Math.abs(speed.getY()));
                break;
            case LOWER:
                speed.setY(-Math.abs(speed.getY()));
                break;
            case LEFT_UPPER:
                speed.setX(Math.abs(speed.getX()));
                speed.setY(Math.abs(speed.getY()));
                break;
            case LEFT_LOWER:
                speed.setX(Math.abs(speed.getX()));
                speed.setY(-Math.abs(speed.getY()));
                break;
            case RIGHT_UPPER:
                speed.setX(-Math.abs(speed.getX()));
                speed.setY(Math.abs(speed.getY()));
                break;
            case RIGHT_LOWER:
                speed.setX(-Math.abs(speed.getX()));
                speed.setY(-Math.abs(speed.getY()));
                break;
        }
    }

    public void tick(ArrayList<Collidable> obstacles){
        move(obstacles);
    }

    public Boolean getVisibility() {
        return isVisible;
    }

    public void setVisibility(Boolean visible) {
        isVisible = visible;
    }

    public void tick(){};

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setBallColor(Color newBallColor){
        this.ballColor = newBallColor;
    }

    public Color getBallColor(){
        return ballColor;
    }
}
