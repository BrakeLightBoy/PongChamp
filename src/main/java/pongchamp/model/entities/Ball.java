package pongchamp.model.entities;

import pongchamp.model.*;
import pongchamp.model.math.Location;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Vector;

public abstract class Ball extends Entity {

    private int radius;
    protected Vector speed;
    protected Vector acceleration;
    protected LineSegment ballPath;
    protected Board board;


    public Ball(Location location, int radius, Vector speed, Vector acceleration, LineSegment ballPath, Board board) {
        super(location);
        this.radius = radius;
        this.speed = speed;
        this.acceleration = acceleration;
        this.ballPath = ballPath;
        this.board = board;
    }

    public void tick(){

        move();
    }

    public abstract void move();
    public abstract void onCollision(Collidable collidable);



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

    public LineSegment getBallPath() {
        return ballPath;
    }

    public void setBallPath(LineSegment ballPath) {
        this.ballPath = ballPath;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
