package pongchamp.model;

import pongchamp.model.entities.*;
import pongchamp.controller.EmptyPaddleController;
import pongchamp.controller.PaddleController;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Location;
import pongchamp.model.math.Vector;

import java.util.ArrayList;
import java.util.List;

public class Board implements Runnable {

    private final float width = Properties.BOARD_WIDTH;
    private final float height = Properties.BOARD_HEIGHT;
    private final float paddleDistanceFromTheEdge = Properties.PADDLE_DISTANCE_FROM_THE_EDGE;
    private boolean running;

    private final LineSegment upperWall;
    private final LineSegment lowerWall;

    private final LineSegment leftPaddleMovementPath;
    private final LineSegment rightPaddleMovementPath;

    private Paddle leftPaddle;
    private Paddle rightPaddle;

    private Ball ball;


    private List<Entity> entities;

    public Board() {
        this(new ArrayList<>());
    }

    public Board(List<Entity> entities) {
        this.entities = new ArrayList<>(entities);

        this.upperWall = new LineSegment(
                new Location(0,0),
                new Location(width,0)
        );
        this.lowerWall = new LineSegment(
                new Location(0,height),
                new Location(width,height));


        this.leftPaddleMovementPath = new LineSegment(
                new Location(paddleDistanceFromTheEdge,0),
                new Location(paddleDistanceFromTheEdge,height)
        );
        this.rightPaddleMovementPath = new LineSegment(
                new Location(width-paddleDistanceFromTheEdge,0),
                new Location(width-paddleDistanceFromTheEdge,height)
        );

        PaddleController emptyController = new EmptyPaddleController(); //this is for test purposes, will be removed in the future

        this.leftPaddle = new NormalPaddle(new Location(42,450),leftPaddleMovementPath,emptyController,this);
        this.rightPaddle = new NormalPaddle(new Location(1158,450),rightPaddleMovementPath,emptyController,this);
        this.ball = new NormalBall(new Location(width/2f,height/2f),10,new Vector(-5,0),new Vector(0,0),this);

        this.entities.add(leftPaddle);
        this.entities.add(rightPaddle);

    }

    public List<Entity> getEntities() {
        return entities;
    }


    @Override
    public void run() {
        while (running) {
            for (Entity entity : entities) {
                entity.tick();
            }
            //todo some rendering whether in this thread or a new one
            //todo some TPS/FPS syncing

            try {
                Thread.sleep(10); //this is doing the tps syncing for now, but that's not how it's supposed to be done in the end
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void checkCollision(){

       //if collision happens it calls something like ball.onCollision(Collidable)

    }

    public void startGame(){
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void endGame(){
        running = false;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getPaddleDistanceFromTheEdge() {
        return paddleDistanceFromTheEdge;
    }

    public LineSegment getUpperWall() {
        return upperWall;
    }

    public LineSegment getLowerWall() {
        return lowerWall;
    }

    public LineSegment getLeftPaddleMovementPath() {
        return leftPaddleMovementPath;
    }

    public LineSegment getRightPaddleMovementPath() {
        return rightPaddleMovementPath;
    }

    public Paddle getLeftPaddle() {
        return leftPaddle;
    }

    public Paddle getRightPaddle() {
        return rightPaddle;
    }
}
