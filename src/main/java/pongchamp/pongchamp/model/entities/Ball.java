package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

public abstract class Ball extends Entity {

    private int radius;
    protected Vector speed,acceleration;

    public Ball(Board board, Point location, int radius, Vector speed, Vector acceleration) {
        super(board,location);
        this.radius = radius;
        this.speed = speed;
        this.acceleration = acceleration;
    }

    public void tick(){
        move();
    }

    public abstract void move();

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
