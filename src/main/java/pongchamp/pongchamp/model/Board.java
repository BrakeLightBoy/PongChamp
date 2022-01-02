package pongchamp.pongchamp.model;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.ai.MediumAIPaddle;
import pongchamp.pongchamp.controller.ai.UnbeatableAIPaddle;
import pongchamp.pongchamp.model.entities.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;
import static pongchamp.pongchamp.model.Properties.*;
import pongchamp.pongchamp.model.entities.powerups.*;

import java.util.*;

public class Board implements Runnable {
    private final UserSettings settings;

    private final Color backgroundColor;

    private final float width,height,paddleDistanceFromTheEdge;

    private boolean hasEnded,isPaused;

    private final Wall upperWall,lowerWall;
    private final LineSegment leftPaddleMovementPath,rightPaddleMovementPath;
    private Paddle leftPaddle,rightPaddle;
    private String gameWinner;

    private final Ball ball;

    private final List<Entity> gameEntities;
    private final ArrayList<Collidable> obstacles;
    private final List<PowerUp> spawnedPowerUps,activatedPowerUps,maintainedPowerUps;
    private final HashSet<PowerUp> toRemove;

    private final Point[] powerPoints;
    private final boolean[] takenPoints;

    private GameModes gameMode;


    private float time;

    private final boolean hasPowerUps;

    protected int leftScore, rightScore;

    private Vector initialSpeed;

    private Random random = new Random();

    public Board(GameModes gameMode, Boolean hasPowerUps) {
        time = 0;
        this.settings = new UserSettings();
        this.gameMode = gameMode;

        powerPoints = new Point[]{ new Point(BOARD_WIDTH * .25f, BOARD_HEIGHT * .25f), new Point(BOARD_WIDTH * .75f, BOARD_HEIGHT * .75f), new Point(BOARD_WIDTH * .25f, BOARD_HEIGHT * .75f), new Point(BOARD_WIDTH * .75f, BOARD_HEIGHT * .25f)};
        takenPoints = new boolean[]{false, false, false, false};

        backgroundColor = settings.getBackgroundColor();

        this.hasPowerUps = hasPowerUps;

        width = BOARD_WIDTH;
        height = BOARD_HEIGHT;
        paddleDistanceFromTheEdge = PADDLE_DISTANCE_FROM_THE_EDGE;

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

        leftPaddle = new Paddle(new Point(40,450),leftPaddleMovementPath,CollisionTypes.LEFT, settings.getPaddle1Color());

        reRollSpeed();

        ball = new Ball(new Point(width/2f,height/2f),BALL_RADIUS,initialSpeed,new Vector(0,0),settings.getBallColor());

        rightPaddle = new Paddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT,settings.getPaddle2Color());

        switch (gameMode){
            case V_AI -> {
                rightPaddle = new MediumAIPaddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT,ball,settings.getPaddle2Color());
            }
            case END -> {
                rightPaddle = new UnbeatableAIPaddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT,ball,settings.getPaddle2Color());
            }
            case V_1 -> {
                rightPaddle = new Paddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT,settings.getPaddle2Color());
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
        time += MILLISECONDS_PER_TICK/1000f;

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
    }

    private void maintainPowerUps(){
        for (PowerUp maintainedPowerUp : maintainedPowerUps){
            maintainedPowerUp.tick();
        }
    }

    private void spawnPowerUps(){
        if (spawnedPowerUps.size()<MAXNUMBEROFPOWERUPS){
            PowerUp newPower = spawnPowerUp();
            if (newPower != null){
                spawnedPowerUps.add(newPower);
            }
        }
    }

    private void freeSpawningSpace(Point powerUpPoint){
        for(int i = 0; i< takenPoints.length; i++){
            if(powerUpPoint.equals(powerPoints[i])){
                System.out.println("Before:"+takenPoints[i]);
                takenPoints[i] = false;
                System.out.println("After:"+takenPoints[i]);
            }
        }
    }

    private void purgeAllSpawningSpaces(){
        Arrays.fill(takenPoints, false);
    }

    private void handleSpawnedPowers(){
        for (int i = 0; i<spawnedPowerUps.size();i++){
            if (spawnedPowerUps.get(i).decay()){
                freeSpawningSpace(spawnedPowerUps.get(i).getLocation());
                toRemove.add(spawnedPowerUps.get(i));
            }

            if(spawnedPowerUps.get(i).checkIfCollected(ball) && !toRemove.contains(spawnedPowerUps.get(i))){
                spawnedPowerUps.get(i).onCollect();
                freeSpawningSpace(spawnedPowerUps.get(i).getLocation());
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

    public GameModes getGameMode() {
        return gameMode;
    }

    public void checkScore() {
        if(!(this.getLeftScore() == MATCHPOINT || this.getRightScore() == MATCHPOINT)) {
            if (ball.getLocation().getX() < 0) {

                rightGoal();
                System.out.println(this.getLeftScore() + " : " + this.getRightScore());
                newRound();
            } else if (ball.getLocation().getX() > 1200) {
                this.leftGoal();
                System.out.println(this.getLeftScore() + " : " + this.getRightScore());
                newRound();
            }
        } else if(getLeftScore() == MATCHPOINT){
            gameWinner = "Player 1";
            endGame();
        } else {
            gameWinner = "Player 2";
            endGame();
        }
    }

    private void reRollSpeed(){
        float initialX = 7*random.nextInt(2);
        float initialY = 2*(random.nextInt(8)-4);

        if (initialX == 0){
            initialX = -7;
        }
        if (initialY == 0){
            initialY = 2;
        }

        initialSpeed = new Vector(initialX,initialY);
    }

    private void newRound(){
        clearAllPowers();
        ball.getLocation().setX(BOARD_WIDTH/2);
        ball.getLocation().setY(BOARD_HEIGHT/2);

        purgeAllSpawningSpaces();

        reRollSpeed();

        ball.setSpeed(initialSpeed);
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


    private Point randomizeLocation(){
        int locationIdentifier;
        for (int i = 0; i<10; i++){
            locationIdentifier = random.nextInt(4);




//            System.out.println(!takenPoints[locationIdentifier]);

            if (!takenPoints[locationIdentifier]){

                takenPoints[locationIdentifier] = true;
                return powerPoints[locationIdentifier];
            }
        }
        return null;
    }


    private PowerUp spawnPowerUp(){
        double spawnOutcome = Math.random()*100;

        double spawnThreshold = POWERUPSPAWNTHRESHOLD;


        if (spawnOutcome >= spawnThreshold)  {

            Point spawnPoint = randomizeLocation();
            PowerUp spawnedPowerUp;
            double powerTypeOutcome = Math.random() * 100;

            if ((spawnPoint != null)){
                if (powerTypeOutcome <= 10) {
                    spawnedPowerUp = new InvisPower(this, spawnPoint);
                    System.out.println("Invisible Power Up Spawned!");
                    //spawn invis power
                } else if (powerTypeOutcome <= 40) {
                    spawnedPowerUp = new ElongatePaddlePower(this, spawnPoint);
                    System.out.println("Elongated Paddle Power Up Spawned!");
                    //spawn elongated paddle
                } else if (powerTypeOutcome <= 70) {
                    spawnedPowerUp = new RandomSpeedPower(this, spawnPoint);
                    System.out.println("Random Speed Power Up Spawned!");
                    //spawn random speed power up
                } else {
                    spawnedPowerUp = new StrengthPower(this, spawnPoint);
                    System.out.println("Strengthened Paddle Power Up Spawned!");
                    //spawn strengthened paddle
                }
                return spawnedPowerUp;
            }
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

    public Color getBackgroundColor(){
        return backgroundColor;
    }

    public Color getPaddle1Color(){
        return leftPaddle.getPaddleColor();
    }

    public Color getPaddle2Color(){
        return rightPaddle.getPaddleColor();
    }

    public UserSettings getSettings(){
        return settings;
    }

    public float getTime(){
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
