package pongchamp.pongchamp.model.entities.powerups;

import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Properties;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.entities.Entity;
import pongchamp.pongchamp.model.math.Point;

public abstract class PowerUp extends Entity {
    int activatedDuration,decayDuration,radius,currentDuration,currentDecay;
    Board gameBoard;
    Color powerColor;

    public PowerUp(Board gameBoard,Point location,Color powerColor){
        super(location);
        this.powerColor = powerColor;
        this.gameBoard = gameBoard;
        this.activatedDuration = 400; //the default duration (some random number just to test it)
        this.radius = Properties.POWER_UP_RADIUS; //again just random for test
        this.decayDuration = 500;
    }

    public PowerUp(Board gameBoard, Point location,int duration, int radius) {
        super(location);
        this.gameBoard = gameBoard;
        this.activatedDuration = duration;
        this.radius = radius;
    }

    public Color getPowerColor(){
        return powerColor;
    }

    public void tick(){
    }

    /*
    Method in order to check how long an activated power up has been active for and deactivate it after it exceeds the
    set activated duration.
     */
    public Boolean agePowerUp(){
        currentDuration++;
        if (activatedDuration<=currentDuration){
            deactivate();
            return true;
        }
        else {
            return false;
        }
    }

    /*
    Method in order to check how long a spawned power up has been out in the field for, and after it has exceeded the
    set decay duration return false.
     */
    public Boolean decay(){
        currentDecay++;
        if (decayDuration<=currentDecay){
            return true;
        }
        else {
            return false;
        }
    }

    /*
    When a power up is collected add it to the gameboards activated power ups list, activate the power up, and start the
    aging of the power up.
     */
    public void onCollect(){
        gameBoard.getActivatedPowerUps().add(this);
        activate();
        agePowerUp();
    }

    /*
    Abstract activate method implemented within each specific power up type
     */
    protected abstract void activate();

    /*
    Abstract deactivate method implemented within each specific power up type
     */
    public abstract void deactivate();

    /*
    Checks if the power up has been collected based on whether the distance between the ball and the power up is less
    than the radius of the ball + the radius of the power up
     */
    public Boolean checkIfCollected(Ball ball){
        float deltaX = ball.getLocation().getX() - getLocation().getX();
        float deltaY = ball.getLocation().getY() - getLocation().getY();

        double distanceSqr = Math.pow(deltaX,2)+Math.pow(deltaY,2);
        double radSumSqr = Math.pow((ball.getRadius()+radius),2);
        if (distanceSqr>radSumSqr){
            return false;
        }
        else {
            return true;
        }
    }

    public int getActivatedDuration() {
        return activatedDuration;
    }

    public int getRadius() {
        return radius;
    }
}
