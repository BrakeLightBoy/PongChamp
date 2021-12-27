package com.example.finalfrontier.model.entities;

import com.example.finalfrontier.model.math.Vector;
import javafx.scene.paint.Color;
import com.example.finalfrontier.model.*;
import com.example.finalfrontier.model.math.Point;

import java.util.ArrayList;

public abstract class Ball extends Entity {

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

    public void setBallColor(Color ballColor){
        this.ballColor = ballColor;
    }
    public Color getBallColor(){
        return ballColor;
    }
}
