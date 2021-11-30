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
    public abstract void onCollusion(Collidable collidable);

    public Collidable checkCollusion(){ //it's either another entity or a line segment

        //check for collusion with the 2 walls at the top and the bottom. either y <= 0 or y >= 900

        if (location.getY() - radius <=0){
            return board.getUpperWall();
        }
        else if (location.getY() + radius >= 900){
            return board.getLowerWall();
        }

        //check for collusion with the paddles' movement path. it'll then check if the paddle is actually there

        else if (location.getX() - radius <=board.getPaddleDistanceFromTheEdge()){
            Paddle leftPaddle = board.getLeftPaddle();
        }
        else if (location.getX() + radius >= board.getWidth()- board.getPaddleDistanceFromTheEdge()){
            Paddle rightPaddle = board.getRightPaddle();
        }


        return null;
    }

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
