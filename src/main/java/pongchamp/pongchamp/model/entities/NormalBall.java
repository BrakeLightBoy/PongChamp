package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;
import static pongchamp.pongchamp.model.ObstactleTypes.*;
import static pongchamp.pongchamp.model.CollisionTypes.*;

import java.util.ArrayList;

public class NormalBall extends Ball{
    public NormalBall(Point location, int radius, Vector speed, Vector acceleration) {
        super(location, radius, speed, acceleration);
    }

    @Override
    public void move(ArrayList<Collidable> obstacles) {
        location.movePoint(speed);
        speed.addVector(acceleration);

        for (Collidable obstacle : obstacles){
            Collision collision = checkCollision(obstacle);
            onCollision(collision);
        }
    }

    public Collision checkCollision(Collidable obstacle){
        if (obstacle instanceof Wall){
            return checkCollision((Wall) obstacle);
        }
        else {
            return checkCollision((Paddle) obstacle);
        }
    }



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

    public Collision checkCollision(Paddle paddle){
        HitBox.hitBoxCollision collidedPart = paddle.paddleHitBox.checkBallIntersect(this);

        if (collidedPart == HitBox.hitBoxCollision.NONE){
            return new Collision(NONE,PADDLE);
        }

        CollisionTypes collisionType;
        if (collidedPart == HitBox.hitBoxCollision.VERTICAL) {
            if (paddle.paddleType == LEFT) {
                collisionType = LEFT;
            }
            else {
                collisionType = RIGHT;
            }
        }
        else if (collidedPart == HitBox.hitBoxCollision.HORIZONTAL) {
            if (location.getY()< paddle.location.getY()){
                collisionType = LOWER;
            }
            else {
                collisionType = UPPER;
            }
        }
        else {
            if ( (location.getY()< paddle.location.getY() && paddle.paddleType == LEFT) ){
                collisionType = LEFT_LOWER;
            }
            else if ( (location.getY()< paddle.location.getY() && paddle.paddleType == RIGHT) ){
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

    @Override
    public void onCollision(Collision collision) {
        CollisionTypes colType = collision.getColType();

        //temporarily switched lower and upper because of the render engine having the inverted y-axis
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
}
