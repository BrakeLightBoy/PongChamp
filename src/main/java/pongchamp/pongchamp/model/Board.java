package pongchamp.pongchamp.model;

import pongchamp.pongchamp.model.entities.*;
import pongchamp.pongchamp.controller.EmptyPaddleController;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;
import pongchamp.pongchamp.view.RenderEngine;
import pongchamp.pongchamp.view.SimpleRenderEngine;
import static pongchamp.pongchamp.model.Properties.*;
import pongchamp.pongchamp.model.entities.powerups.*;

import java.util.ArrayList;
import java.util.List;

public class Board implements Runnable {

    private final float width = BOARD_WIDTH;
    private final float height = BOARD_HEIGHT;
    private final float paddleDistanceFromTheEdge = PADDLE_DISTANCE_FROM_THE_EDGE;
    private boolean running;

    private final Wall upperWall,lowerWall;
    private final LineSegment leftPaddleMovementPath,rightPaddleMovementPath;
    private Paddle leftPaddle,rightPaddle;

    private ArrayList<Collidable> obstacles;

    private Ball ball;

    private List<Entity> gameEntities;
    private List<Collectible> spawnedPowerUps;

    private RenderEngine renderEngine;

    protected int leftScore, rightScore;



    public Board(SimpleRenderEngine renderEngine) {
        this.renderEngine = renderEngine;
        gameEntities = new ArrayList<>();
        obstacles = new ArrayList<>();
        spawnedPowerUps = new ArrayList<>();


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
        this.ball = new NormalBall(this,new Point(width/2f,height/2f),BALL_RADIUS,new Vector(2,4),new Vector(0,0));

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

            Collectible spawnedPower = spawnPowerUp();

            if (spawnedPower != null){
                spawnedPowerUps.add(spawnedPower);
                spawnedPower.onCollect();
            }

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

    private Collectible spawnPowerUp(){
        double spawnOutcome = Math.random()*100;

        double spawnThreshold = 99.9;

        float yRange = (float) Math.random()*(BOARD_HEIGHT-2*POWER_UP_RADIUS)+POWER_UP_RADIUS;

        float xRange = (float) Math.random()*(BOARD_WIDTH-(2*PADDLE_DISTANCE_FROM_THE_EDGE+rightPaddle.getWidth()
                +leftPaddle.getWidth()+2*POWER_UP_RADIUS))
                +PADDLE_DISTANCE_FROM_THE_EDGE+leftPaddle.getWidth()+POWER_UP_RADIUS;
        Point spawnPoint = new Point(xRange,yRange);

        if (spawnOutcome>= spawnThreshold){
            PowerUp spawnedPowerUp;
            double powerTypeOutcome = Math.random()*100;


            if (powerTypeOutcome<=10){
                spawnedPowerUp = new InvisPower(spawnPoint);
                System.out.println("Invisible Power Up Spawned!");
                //spawn invis power
            } else if (powerTypeOutcome <= 40){
                spawnedPowerUp = new ElongatePaddlePower(spawnPoint);
                System.out.println("Elongated Paddle Power Up Spawned!");
                //spawn elongated paddle
            } else if (powerTypeOutcome <= 70){
                spawnedPowerUp = new RandomSpeedPower(spawnPoint);
                System.out.println("Random Speed Power Up Spawned!");
                //spawn random speed power up
            } else {
                spawnedPowerUp = new StrengthPower(spawnPoint);
                System.out.println("Strengthened Paddle Power Up Spawned!");
                //spawn strengthened paddle
            }
            return spawnedPowerUp;
        }
        return null;
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

    public int getLeftScore() { return leftScore; }

    public void leftGoal() { ++leftScore; }

    public int getRightScore() { return rightScore; }

    public void rightGoal() { ++rightScore; }
}
