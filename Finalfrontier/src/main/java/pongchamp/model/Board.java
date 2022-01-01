package pongchamp.model;
import pongchamp.model.entities.powerups.*;
import pongchamp.model.math.Vector;
import pongchamp.controller.ai.MediumAIPaddle;
import pongchamp.controller.ai.UnbeatableAIPaddle;
import pongchamp.model.entities.Ball;
import pongchamp.model.entities.Entity;
import pongchamp.model.entities.Paddle;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Point;

import java.util.*;

public class Board implements Runnable {

    private final float width = Properties.BOARD_WIDTH;
    private final float height = Properties.BOARD_HEIGHT;
    private final float paddleDistanceFromTheEdge = Properties.PADDLE_DISTANCE_FROM_THE_EDGE;
    private boolean hasEnded;
    private boolean isPaused;

    private final Wall upperWall,lowerWall;
    private final LineSegment leftPaddleMovementPath,rightPaddleMovementPath;
    private Paddle leftPaddle,rightPaddle;
    private String gameWinner;

    private Ball ball;

    private List<Entity> gameEntities;
    private ArrayList<Collidable> obstacles;
    private List<PowerUp> spawnedPowerUps;
    private List<PowerUp> activatedPowerUps;
    private List<PowerUp> maintainedPowerUps;
    private HashSet<PowerUp> toRemove;

    private boolean hasPowerUps;

    private UserSettings settings;


    protected int leftScore, rightScore;

    private Random random = new Random();

    public Board(OpponentType opponentType, Boolean hasPowerUps) {
        this.settings = new UserSettings();

        this.hasPowerUps = hasPowerUps;


        spawnedPowerUps = new ArrayList<>();
        activatedPowerUps = new ArrayList<>();
        maintainedPowerUps = new ArrayList<>();


        gameEntities = new ArrayList<>();
        obstacles = new ArrayList<>();
        toRemove = new HashSet<>();

        isPaused = false;
        gameWinner = "Ongoing";
        hasEnded = false;

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


        leftPaddle = new Paddle(new Point(40,450),leftPaddleMovementPath,CollisionTypes.LEFT);
        //rightPaddle = new NormalPaddle(new Point(1160,450),rightPaddleMovementPath,emptyController,CollisionTypes.RIGHT);
        ball = new Ball(new Point(width/2f,height/2f), Properties.BALL_RADIUS, Properties.INITIAL_SPEED,new Vector(0,0));

        rightPaddle = new Paddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT);

        switch (opponentType){

            case BEATABLE_AI_PADDLE -> {
                rightPaddle = new MediumAIPaddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT,ball);
            }
            case UNBEATABLE_AI_PADDLE -> {
                rightPaddle = new UnbeatableAIPaddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT,ball);
            }
            case PLAYER -> {
                rightPaddle = new Paddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT);
            }
        }

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

    public List<PowerUp> getSpawnedPowers() {
        return spawnedPowerUps;
    }

    public Ball getBall() {
        return ball;
    }

    @Override
    public void run() {

        if (hasPowerUps) {
            handleActivePowers();
            maintainPowerUps();
        }
        for (Entity entity : gameEntities) {
            entity.tick();
        }
        ball.tick(obstacles);

        checkScore();

        if (hasPowerUps) {
            spawnPowerUps();
            handleSpawnedPowers();
        }


        //todo some rendering whether in this thread or a new one
        //todo some TPS/FPS syncing


        /*try {
            Thread.sleep(10); //this is doing the tps syncing for now, but that's not how it's supposed to be done in the end
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    private void maintainPowerUps(){
        for (PowerUp maintainedPowerUp : maintainedPowerUps){
            maintainedPowerUp.tick();
        }
    }

    private void spawnPowerUps(){
        if (spawnedPowerUps.size()< Properties.MAXNUMBEROFPOWERUPS){
            PowerUp newPower = spawnPowerUp();
            if (newPower != null){
                spawnedPowerUps.add(newPower);
            }
        }
    }

    private void handleSpawnedPowers(){
        for (int i = 0; i<spawnedPowerUps.size();i++){
            if (spawnedPowerUps.get(i).decay()){
                toRemove.add(spawnedPowerUps.get(i));
            }

            if(spawnedPowerUps.get(i).checkIfCollected(ball) && !toRemove.contains(spawnedPowerUps.get(i))){
                spawnedPowerUps.get(i).onCollect();
                toRemove.add(spawnedPowerUps.get(i));
            }
        }

        for (PowerUp remPowerUp : toRemove){
//                System.out.println("Remove!");
            spawnedPowerUps.remove(remPowerUp);
        }
        toRemove.clear();
    }

    private void handleActivePowers(){
        for(int i = 0;i<activatedPowerUps.size();i++){
            if (activatedPowerUps.get(i).agePowerUp()){
                toRemove.add(activatedPowerUps.get(i));
            }
        }

        for (PowerUp remPowerUp : toRemove){
            System.out.println("Remove!");
            activatedPowerUps.remove(remPowerUp);
            maintainedPowerUps.remove(remPowerUp);
        }
        toRemove.clear();
    }

    public void checkScore() {
        if(!(this.getLeftScore() == 1 || this.getRightScore() == 1)) {
            if (ball.getLocation().getX() < 0) {

                rightGoal();
                System.out.println(this.getLeftScore() + " : " + this.getRightScore());
                newRound();
            } else if (ball.getLocation().getX() > 1200) {
                this.leftGoal();
                System.out.println(this.getLeftScore() + " : " + this.getRightScore());
                newRound();
            }
        } else if(getLeftScore() == 1){
            gameWinner = "Player 1";
            endGame();
        } else {
            gameWinner = "Player 2";
            endGame();
        }
    }

    private void newRound(){
        clearAllPowers();
        ball.getLocation().setX(Properties.BOARD_WIDTH/2);
        ball.getLocation().setY(Properties.BOARD_HEIGHT/2);
        ball.setSpeed(Properties.INITIAL_SPEED);
    }

    private void clearAllPowers(){
        for (PowerUp activePower : activatedPowerUps){
            activePower.deactivate();
        }
        activatedPowerUps.clear();
        spawnedPowerUps.clear();
        maintainedPowerUps.clear();
    }



    public void restartGame(){
        setLeftScore(0);
        setRightScore(0);
        newRound();
        hasEnded = false;
    }


    private PowerUp spawnPowerUp(){
        double spawnOutcome = Math.random()*100;

        double spawnThreshold = 99;

        float yRange = (float) Math.random()*(Properties.BOARD_HEIGHT-2* Properties.POWER_UP_RADIUS)+ Properties.POWER_UP_RADIUS;

        float xRange = (float) Math.random()*(Properties.BOARD_WIDTH-(2* Properties.PADDLE_DISTANCE_FROM_THE_EDGE+rightPaddle.getWidth()
                +leftPaddle.getWidth()+2* Properties.POWER_UP_RADIUS))
                + Properties.PADDLE_DISTANCE_FROM_THE_EDGE+leftPaddle.getWidth()+ Properties.POWER_UP_RADIUS;
        Point spawnPoint = new Point(xRange,yRange);

        if (spawnOutcome>= spawnThreshold){
            PowerUp spawnedPowerUp;
            double powerTypeOutcome = Math.random()*100;


            if (powerTypeOutcome<=10){
                spawnedPowerUp = new InvisPower(this,spawnPoint);
                System.out.println("Invisible Power Up Spawned!");
                //spawn invis power
            } else if (powerTypeOutcome <= 40){
                spawnedPowerUp = new ElongatePaddlePower(this,spawnPoint);
                System.out.println("Elongated Paddle Power Up Spawned!");
                //spawn elongated paddle
            } else if (powerTypeOutcome <= 70){
                spawnedPowerUp = new RandomSpeedPower(this,spawnPoint);
                System.out.println("Random Speed Power Up Spawned!");
                //spawn random speed power up
            } else {
                spawnedPowerUp = new StrengthPower(this,spawnPoint);
                System.out.println("Strengthened Paddle Power Up Spawned!");
                //spawn strengthened paddle
            }
            return spawnedPowerUp;
        }
        return null;
    }

    private void powerUpType(){

    }

    public void endGame(){
        hasEnded = true;
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

    public List<PowerUp> getSpawnedPowerUps() {
        return spawnedPowerUps;
    }

    public List<PowerUp> getMaintainedPowerUps() {
        return maintainedPowerUps;
    }

    public Boolean getPaused() {
        return isPaused;
    }

    public String getGameWinner() {
        return gameWinner;
    }

    public void setLeftScore(int newLeftScore){
        leftScore = newLeftScore;
    }

    public void setRightScore(int newRightScore){
        rightScore = newRightScore;
    }

    public void setPaused(boolean paused){
        this.isPaused = paused;
    }

    public void setHasEnded(boolean hasEnded){
        this.hasEnded = hasEnded;
    }

    public Boolean getHasEnded(){
        return hasEnded;
    }

    public UserSettings getSettings(){
        return settings;
    }
}