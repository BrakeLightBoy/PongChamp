package pongchamp.pongchamp.model;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.ai.MediumAIPaddle;
import pongchamp.pongchamp.controller.ai.UnbeatableAIPaddle;
import pongchamp.pongchamp.controller.json.JsonAPI;
import pongchamp.pongchamp.model.entities.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;
import static pongchamp.pongchamp.model.Properties.*;
import pongchamp.pongchamp.model.entities.powerups.*;

import java.io.IOException;
import java.util.*;

public class Board implements Runnable {
    private final UserSettings settings;

    private final JsonAPI jsonAPI;

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

    public Board(GameModes gameMode, Boolean hasPowerUps,UserSettings settings) {
        time = 0;
        this.settings = settings;

        this.jsonAPI = new JsonAPI();

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

    public Board(BoardState state) throws IOException {
        time = state.getTime();
        jsonAPI = new JsonAPI();
        settings = jsonAPI.loadSettings();//todo should manucall load from the actual user settngs
        backgroundColor = settings.getBackgroundColor();
        hasPowerUps = state.hasPowerUps(); //should always be false at this moment
        gameMode = state.getGameModes();

        powerPoints = new Point[]{ new Point(BOARD_WIDTH * .25f, BOARD_HEIGHT * .25f), new Point(BOARD_WIDTH * .75f, BOARD_HEIGHT * .75f), new Point(BOARD_WIDTH * .25f, BOARD_HEIGHT * .75f), new Point(BOARD_WIDTH * .75f, BOARD_HEIGHT * .25f)};
        takenPoints = new boolean[]{false, false, false, false};

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

        leftPaddleMovementPath = new LineSegment(
                new Point(paddleDistanceFromTheEdge,0),
                new Point(paddleDistanceFromTheEdge,height)
        );
        rightPaddleMovementPath = new LineSegment(
                new Point(width-paddleDistanceFromTheEdge,0),
                new Point(width-paddleDistanceFromTheEdge,height)
        );

        this.rightScore = state.getRightScore();
        this.leftScore = state.getLeftScore();

        BoardState.PaddleState leftPaddleState = state.getLeftPaddleState();
        leftPaddle = new Paddle(leftPaddleState.getLocation(),leftPaddleState.getWidth(),leftPaddleState.getHeight(),leftPaddleMovementPath,null,CollisionTypes.LEFT, settings.getPaddle1Color());
        //rightPaddle = new NormalPaddle(new Point(1160,450),rightPaddleMovementPath,emptyController,CollisionTypes.RIGHT);
        BoardState.BallState ballState = state.getBallState();
        ball = new Ball(ballState.getLocation(), ballState.getRadius(), ballState.getSpeed(), ballState.getAcceleration(), settings.getBallColor());

        rightPaddle = new Paddle(new Point(1160,450),rightPaddleMovementPath,CollisionTypes.RIGHT,settings.getPaddle2Color());

        BoardState.PaddleState rightPaddleState = state.getRightPaddleState();
        switch (state.getRightPaddleState().getType()){
            case BEATABLE_AI -> {
                rightPaddle = new MediumAIPaddle(rightPaddleState.getLocation(), rightPaddleState.getWidth(),rightPaddleState.getHeight(),rightPaddleMovementPath,CollisionTypes.RIGHT,ball,settings.getPaddle2Color());
            }
            case UNBEATABLE_AI -> {
                rightPaddle = new UnbeatableAIPaddle(rightPaddleState.getLocation(), rightPaddleState.getWidth(),rightPaddleState.getHeight(),rightPaddleMovementPath,CollisionTypes.RIGHT,ball,settings.getPaddle2Color());
            }
            case PLAYER -> {
                rightPaddle = new Paddle(rightPaddleState.getLocation(),rightPaddleState.getWidth(),rightPaddleState.getHeight(),rightPaddleMovementPath,null,CollisionTypes.RIGHT,settings.getPaddle2Color());
            }

        }

        gameEntities.add(leftPaddle);
        gameEntities.add(rightPaddle);

        obstacles.add(leftPaddle);
        obstacles.add(rightPaddle);
        obstacles.add(lowerWall);
        obstacles.add(upperWall);

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

    /*
    From the list of maintained power ups (currently limited to the strength power) calls each power ups tick method
     */
    private void maintainPowerUps(){
        for (PowerUp maintainedPowerUp : maintainedPowerUps){
            maintainedPowerUp.tick();
        }
    }

    /*
    Checks to see if the max number of power ups has spawned, and if not tries to create a new power up, and add it to
    the spawned power ups list
     */
    private void spawnPowerUps(){
        if (spawnedPowerUps.size()<MAXNUMBEROFPOWERUPS){
            PowerUp newPower = spawnPowerUp();
            if (newPower != null){
                spawnedPowerUps.add(newPower);
            }
        }
    }

    /*
    As there are four dedicated spawning spots for power ups, here it checks to find the spot which a power up spawned
    at and then free it up (by changing the boolean to false from true)
     */
    private void freeSpawningSpace(Point powerUpPoint){
        for(int i = 0; i< takenPoints.length; i++){
            if(powerUpPoint.equals(powerPoints[i])){
                takenPoints[i] = false;
            }
        }
    }

    /*
    Clears all spots in the takenPoints list (for example if the game restarts or a goal is scored) in one go
     */
    private void purgeAllSpawningSpaces(){
        Arrays.fill(takenPoints, false);
    }

    /*
    Deals with spawned power ups. More detailed comments about for loops are described within the method
     */
    private void handleSpawnedPowers(){
        /*
        Deals with spawned power ups, checking if the power up decayed (and if so frees up the spawn spot and adds it to
        the toRemove list). If the power up has not decayed it checks to see if it has been collected by the ball
        (and if so calls the power ups collect method and frees up the spawn and adds it to a removed list).
         */
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

        /*
        Removes the power ups from the spawned power
         */
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
        if(gameMode == GameModes.END){
            if(this.getLeftScore() == ENDLESSMATCHPOINT || this.getRightScore() == ENDLESSMATCHPOINT){
                endGame();
            }
        }

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

    public boolean isPowerUpsEnabled(){
        return hasPowerUps;
    }

    public Color getBallColor() {
        return ball.getBallColor();
    }

    public List<PowerUp> getActivatedPowerUps(){
        return activatedPowerUps;
    }

    public List<PowerUp> getSpawnedPowers() {
        return spawnedPowerUps;
    }

    public Ball getBall() {
        return ball;
    }

    public GameModes getGameMode() {
        return gameMode;
    }
}
