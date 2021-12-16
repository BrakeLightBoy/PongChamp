package pongchamp.pongchamp.model;

import pongchamp.pongchamp.controller.ai.MediumAIPaddle;
import pongchamp.pongchamp.controller.ai.UnbeatableAIPaddle;
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

import java.util.*;

public class Board implements Runnable {

    private final float width = BOARD_WIDTH;
    private final float height = BOARD_HEIGHT;
    private final float paddleDistanceFromTheEdge = PADDLE_DISTANCE_FROM_THE_EDGE;
    private boolean running;

    private final Wall upperWall,lowerWall;
    private final LineSegment leftPaddleMovementPath,rightPaddleMovementPath;
    private Paddle leftPaddle,rightPaddle;

    private Ball ball;

    private List<Entity> gameEntities;
    private ArrayList<Collidable> obstacles;
    private List<Collectible> spawnedPowerUps;
    private List<PowerUp> activatedPowerUps;
    private HashSet<Integer> toRemove;


    private RenderEngine renderEngine;

    protected int leftScore, rightScore;

    private Random random = new Random();

    public Board(SimpleRenderEngine renderEngine) {
        this.renderEngine = renderEngine;
        gameEntities = new ArrayList<>();
        obstacles = new ArrayList<>();
        spawnedPowerUps = new ArrayList<>();
        activatedPowerUps = new ArrayList<>();
        toRemove = new HashSet<>();

        LineSegment wallSegment =  new LineSegment(new Point(0,0), new Point(width,0));
        lowerWall = new Wall(CollisionTypes.LOWER,wallSegment);

        wallSegment = new LineSegment(new Point(0,height),new Point(width,height));
        upperWall = new Wall(CollisionTypes.UPPER,wallSegment);

//I don't think the paddle movement paths are still necessary, given the current implementation. -WP
        leftPaddleMovementPath = new LineSegment(
                new Point(paddleDistanceFromTheEdge,0),
                new Point(paddleDistanceFromTheEdge,height)
        );
        rightPaddleMovementPath = new LineSegment(
                new Point(width-paddleDistanceFromTheEdge,0),
                new Point(width-paddleDistanceFromTheEdge,height)
        );

        PaddleController emptyController = new EmptyPaddleController(); //this is for test purposes, will be removed in the future

        leftPaddle = new NormalPaddle(new Point(40,450),leftPaddleMovementPath,emptyController,CollisionTypes.LEFT);
        //rightPaddle = new NormalPaddle(new Point(1160,450),rightPaddleMovementPath,emptyController,CollisionTypes.RIGHT); //todo fix before merge
        ball = new NormalBall(new Point(width/2f,height/2f),BALL_RADIUS,new Vector(2,4),new Vector(0,0));
        rightPaddle = new MediumAIPaddle(new Point(1160,450),rightPaddleMovementPath,emptyController,CollisionTypes.RIGHT,ball); //todo remove before merge
        gameEntities.add(leftPaddle);
        gameEntities.add(rightPaddle);




        obstacles.add(leftPaddle);
        obstacles.add(rightPaddle);
        obstacles.add(lowerWall);
        obstacles.add(upperWall);
    }

    public List<PowerUp> getActivatedPowerUps(){
        return activatedPowerUps;
    }

    public List<Entity> getGameEntities() {
        return gameEntities;
    }

    public List<Collectible> getSpawnedPowers() {
        return spawnedPowerUps;
    }

    public Ball getBall() {
        return ball;
    }

    @Override
    public void run() {
        while (running) {
            for(int i = 0;i<activatedPowerUps.size();i++){
                if (activatedPowerUps.get(i).agePowerUp()){
                    toRemove.add(i);
                }
            }

            for (int remIndex : toRemove){
//                System.out.println("Remove!");
                activatedPowerUps.remove(remIndex);
            }
            toRemove.clear();

            for (Entity entity : gameEntities) {
                entity.tick();
            }
            ball.tick(obstacles);

            checkScore();

            renderEngine.render(this);

            //todo some rendering whether in this thread or a new one
            //todo some TPS/FPS syncing


            if (spawnedPowerUps.size()<MAXNUMBEROFPOWERUPS){
                Collectible newPower = spawnPowerUp();
                if (newPower != null){
                    spawnedPowerUps.add(newPower);
                }
            }



            for (int i = 0; i<spawnedPowerUps.size();i++){
                if (spawnedPowerUps.get(i).decay()){
                    toRemove.add(i);
                }

                if(spawnedPowerUps.get(i).checkIfCollected(ball)){
                    spawnedPowerUps.get(i).onCollect();
                    toRemove.add(i);
                }
            }

            for (int remIndex : toRemove){
//                System.out.println("Remove!");
                spawnedPowerUps.remove(remIndex);
            }
            toRemove.clear();

            try {
                Thread.sleep(10); //this is doing the tps syncing for now, but that's not how it's supposed to be done in the end
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void checkScore() {
        if(!(this.getLeftScore() == 10 || this.getRightScore() == 10)) {
            if (ball.getLocation().getX() < 0) {
                ball.getLocation().setX(600);
                rightGoal();
                System.out.println(this.getLeftScore() + " : " + this.getRightScore());
            } else if (ball.getLocation().getX() > 1200) {
                ball.getLocation().setX(600);
                this.leftGoal();
                System.out.println(this.getLeftScore() + " : " + this.getRightScore());
            }
        } else endGame();
    }

    public void startGame(){
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    private Collectible spawnPowerUp(){
        double spawnOutcome = Math.random()*100;

        double spawnThreshold = 99;

        float yRange = (float) Math.random()*(BOARD_HEIGHT-2*POWER_UP_RADIUS)+POWER_UP_RADIUS;

        float xRange = (float) Math.random()*(BOARD_WIDTH-(2*PADDLE_DISTANCE_FROM_THE_EDGE+rightPaddle.getWidth()
                +leftPaddle.getWidth()+2*POWER_UP_RADIUS))
                +PADDLE_DISTANCE_FROM_THE_EDGE+leftPaddle.getWidth()+POWER_UP_RADIUS;
        Point spawnPoint = new Point(xRange,yRange);

        if (spawnOutcome>= spawnThreshold){
            PowerUp spawnedPowerUp;
            double powerTypeOutcome = Math.random()*100;


            if (powerTypeOutcome<=50){
                spawnedPowerUp = new RandomSpeedPower(this,spawnPoint);
//                System.out.println("Invisible Power Up Spawned!");
                //spawn invis power
//            } else if (powerTypeOutcome <= 40){
//                spawnedPowerUp = new ElongatePaddlePower(this,spawnPoint);
////                System.out.println("Elongated Paddle Power Up Spawned!");
//                //spawn elongated paddle
//            } else if (powerTypeOutcome <= 70){
//                spawnedPowerUp = new ElongatePaddlePower(this,spawnPoint);
////                System.out.println("Random Speed Power Up Spawned!");
//                //spawn random speed power up
            } else {
                spawnedPowerUp = new RandomSpeedPower(this,spawnPoint);
//                System.out.println("Strengthened Paddle Power Up Spawned!");
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

    public List<Collectible> getSpawnedPowerUps() {
        return spawnedPowerUps;
    }
}
