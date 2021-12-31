package pongchamp.pongchamp.model;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.ai.AIPaddle;
import pongchamp.pongchamp.controller.ai.MediumAIPaddle;
import pongchamp.pongchamp.controller.ai.UnbeatableAIPaddle;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.entities.Paddle;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;



public class BoardState {

    private UserSettings settings;
    private PaddleState leftPaddleState;
    private PaddleState rightPaddleState;
    private BallState ballState;
    private boolean hasPowerUps;
    private int rightScore,leftScore;

    public BoardState (Board board){
        this.settings = board.getSettings();
        Paddle leftPaddle = board.getLeftPaddle();
        this.leftPaddleState = new PaddleState(PaddleState.Type.PLAYER,leftPaddle.getLocation(),leftPaddle.getWidth(),leftPaddle.getHeight());
        Paddle rightPaddle = board.getRightPaddle();
        PaddleState.Type type;
        if (rightPaddle instanceof MediumAIPaddle){
                type = PaddleState.Type.BEATABLE_AI;
            }
        else if (rightPaddle instanceof UnbeatableAIPaddle){
            type = PaddleState.Type.UNBEATABLE_AI;
        }
        else  {
            type = PaddleState.Type.PLAYER;
        }
        this.rightPaddleState = new PaddleState(type, rightPaddle.getLocation(),rightPaddle.getWidth(),rightPaddle.getHeight());
        Ball ball = board.getBall();
        this.ballState = new BallState(ball.getRadius(), ball.getSpeed(), ball.getAcceleration(), ball.getVisibility(), ball.getLocation());
        this.hasPowerUps = board.isPowerUpsEnabled();
        this.leftScore = board.getLeftScore();
        this.rightScore = board.getRightScore();
    }

    public UserSettings getSettings() {
        return settings;
    }

    public PaddleState getLeftPaddleState() {
        return leftPaddleState;
    }

    public PaddleState getRightPaddleState() {
        return rightPaddleState;
    }

    public BallState getBallState() {
        return ballState;
    }

    public boolean hasPowerUps() {
        return hasPowerUps;
    }

    public int getRightScore() {
        return rightScore;
    }

    public int getLeftScore() {
        return leftScore;
    }

    public class PaddleState{

        Type type;
        Point location;
        float width, height;

        public Type getType() {
            return type;
        }

        public Point getLocation() {
            return location;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }



        public PaddleState(Type type, Point location, float width, float height ) {
            this.type = type;
            this.location = location;
            this.width = width;
            this.height = height;

        }

        enum Type {
            PLAYER,
            BEATABLE_AI,
            UNBEATABLE_AI
        }
    }

    public class BallState{
        int radius;
        Vector speed, acceleration;
        boolean isVisible;
        Point location;


        public BallState(int radius, Vector speed, Vector acceleration, boolean isVisible, Point location) {
            this.radius = radius;
            this.speed = speed;
            this.acceleration = acceleration;
            this.isVisible = isVisible;
            this.location = location;

        }

        public int getRadius() {
            return radius;
        }

        public Vector getSpeed() {
            return speed;
        }

        public Vector getAcceleration() {
            return acceleration;
        }

        public boolean isVisible() {
            return isVisible;
        }

        public Point getLocation() {
            return location;
        }
    }





}
