package com.example.finalfrontier.model.entities;

import com.example.finalfrontier.model.*;
import com.example.finalfrontier.model.math.Vector;
import javafx.scene.paint.Color;
import com.example.finalfrontier.model.math.LineSegment;
import com.example.finalfrontier.model.math.Point;

import java.util.ArrayList;

public class Ball extends Entity {

    private int radius;
    protected Vector speed,acceleration;
    private Boolean isVisible;
    Color ballColor;

    public Ball(Point location, int radius, Vector speed, Vector acceleration) {
        super(location);
        isVisible=true;
        this.radius = radius;
        this.speed = speed;
        this.acceleration = acceleration;
        ballColor = Color.WHITE;
    }

    public void speedUp(float xSpeed,float ySpeed){
        float currentSpeedX = speed.getX();
        float currentSpeedY = speed.getY();

        float modSpeedX = Math.abs(currentSpeedX)+xSpeed;
        if (currentSpeedX<0){
            modSpeedX *= -1;
        }

        float modSpeedY = Math.abs(currentSpeedY)+ySpeed;
        if (currentSpeedX<0){
            modSpeedY *= -1;
        }

        Vector speedVector = new Vector(modSpeedX,modSpeedY);
        setSpeed(speedVector);
    }


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
                    return new Collision(CollisionTypes.RIGHT, ObstactleTypes.WALL);
                }
                break;
            case LEFT:
                if (getLocation().getX()- getRadius()<=wallLine.getStartPoint().getX()){
                    return new Collision(CollisionTypes.LEFT, ObstactleTypes.WALL);
                }
                break;
            case UPPER:
                if (getLocation().getY()+getRadius()>=wallLine.getStartPoint().getY()){
                    return new Collision(CollisionTypes.LOWER, ObstactleTypes.WALL);
                }
                break;
            case LOWER:
                if (getLocation().getY()- getRadius()<=wallLine.getStartPoint().getY()){
                    return new Collision(CollisionTypes.UPPER, ObstactleTypes.WALL);
                }
                break;
        }
        return new Collision(CollisionTypes.NONE, ObstactleTypes.WALL);
    }

    public Collision checkCollision(Paddle paddle){
        HitBox.hitBoxCollision collidedPart = paddle.paddleHitBox.checkBallIntersect(this);

        if (collidedPart == HitBox.hitBoxCollision.NONE){
            return new Collision(CollisionTypes.NONE, ObstactleTypes.PADDLE);
        }

        CollisionTypes collisionType;
        if (collidedPart == HitBox.hitBoxCollision.VERTICAL) {
            if (paddle.paddleType == CollisionTypes.LEFT) {
                collisionType = CollisionTypes.LEFT;
            }
            else {
                collisionType = CollisionTypes.RIGHT;
            }
        }
        else if (collidedPart == HitBox.hitBoxCollision.HORIZONTAL) {
            if (location.getY()< paddle.location.getY()){
                collisionType = CollisionTypes.LOWER;
            }
            else {
                collisionType = CollisionTypes.UPPER;
            }
        }
        else {
            if ( (location.getY()< paddle.location.getY() && paddle.paddleType == CollisionTypes.LEFT) ){
                collisionType = CollisionTypes.LEFT_LOWER;
            }
            else if ( (location.getY()< paddle.location.getY() && paddle.paddleType == CollisionTypes.RIGHT) ){
                collisionType = CollisionTypes.RIGHT_LOWER;
            }
            else if ( (paddle.paddleType == CollisionTypes.RIGHT) ){
                collisionType = CollisionTypes.RIGHT_UPPER;
            }
            else {
                collisionType = CollisionTypes.LEFT_UPPER;
            }
        }
        return new Collision(collisionType, ObstactleTypes.PADDLE);
    }


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

    public void setBallColor(Color ballColor){
        this.ballColor = ballColor;
    }

    public Color getBallColor(){
        return ballColor;
    }
}
