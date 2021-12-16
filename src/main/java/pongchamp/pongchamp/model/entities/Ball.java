package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

import java.util.ArrayList;

public abstract class Ball extends Entity {

    private int radius;
    protected Vector speed,acceleration;
    private Boolean isVisible;

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

    public abstract void move(ArrayList<Collidable> obstacles);

    public abstract void onCollision(Collision collision);

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
}
