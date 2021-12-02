package pongchamp.pongchamp.model;

import pongchamp.pongchamp.model.entities.*;
import pongchamp.pongchamp.controller.EmptyPaddleController;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;
import pongchamp.pongchamp.view.RenderEngine;
import pongchamp.pongchamp.view.SimpleRenderEngine;

import java.util.ArrayList;
import java.util.List;

public class Board implements Runnable {

    private final float width = Properties.BOARD_WIDTH;
    private final float height = Properties.BOARD_HEIGHT;
    private final float paddleDistanceFromTheEdge = Properties.PADDLE_DISTANCE_FROM_THE_EDGE;
    private boolean running;

    private final Wall upperWall,lowerWall;
    private final LineSegment leftPaddleMovementPath,rightPaddleMovementPath;
    private Paddle leftPaddle,rightPaddle;

    private ArrayList<Collidable> obstacles;

    private Ball ball;

    private List<Entity> gameEntities;

    private RenderEngine renderEngine;



    public Board(SimpleRenderEngine renderEngine) {
        this.renderEngine = renderEngine;
        gameEntities = new ArrayList<>();
        obstacles = new ArrayList<>();


        LineSegment wallSegment =  new LineSegment(new Point(0,0), new Point(width,0));
        lowerWall = new Wall(Wall.WallType.LOWER,wallSegment);

        wallSegment = new LineSegment(new Point(0,height),new Point(width,height));
        upperWall = new Wall(Wall.WallType.UPPER,wallSegment);

//I don't think the paddle movement paths are still necessary, given the current implementation. -WP
        this.leftPaddleMovementPath = new LineSegment(
                new Point(paddleDistanceFromTheEdge,0),
                new Point(paddleDistanceFromTheEdge,height)
        );
        this.rightPaddleMovementPath = new LineSegment(
                new Point(width-paddleDistanceFromTheEdge,0),
                new Point(width-paddleDistanceFromTheEdge,height)
        );

        PaddleController emptyController = new EmptyPaddleController(); //this is for test purposes, will be removed in the future

        this.leftPaddle = new NormalPaddle(this,new Point(40,450),leftPaddleMovementPath,emptyController,"left");
        this.rightPaddle = new NormalPaddle(this,new Point(1160,450),rightPaddleMovementPath,emptyController,"right");
        this.ball = new NormalBall(this,new Point(width/2f,height/2f),20,new Vector(2,4),new Vector(0,0));

        this.gameEntities.add(leftPaddle);
        this.gameEntities.add(rightPaddle);
        this.gameEntities.add(ball);

        obstacles.add(leftPaddle);
        obstacles.add(rightPaddle);
        obstacles.add(leftPaddle);
        obstacles.add(lowerWall);
        obstacles.add(upperWall);
    }

    public List<Entity> getGameEntities() {
        return gameEntities;
    }


    @Override
    public void run() {
        while (running) {
            for (Entity entity : gameEntities) {
                entity.tick();
                if (entity instanceof Collidable){
                     Collision collision = ((Collidable) entity).checkBallCollision(ball);
                     ball.onCollision(collision);
                }
            }
            for (Collidable obstacle : obstacles){
                Collision collision = obstacle.checkBallCollision(ball);
                ball.onCollision(collision);
            }

            renderEngine.render(this);

            //todo some rendering whether in this thread or a new one
            //todo some TPS/FPS syncing


            try {
                Thread.sleep(10); //this is doing the tps syncing for now, but that's not how it's supposed to be done in the end
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
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

    public Wall getUpperWall() {
        return upperWall;
    }

    public Wall getLowerWall() {
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
