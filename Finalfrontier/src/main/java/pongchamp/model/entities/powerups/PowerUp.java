package pongchamp.model.entities.powerups;

import pongchamp.model.Board;
import pongchamp.model.entities.Ball;
import pongchamp.model.entities.Entity;
import javafx.scene.paint.Color;
import pongchamp.model.Properties;
import pongchamp.model.math.Point;
//import pongchamp.pongchamp.model.Collectible;

public abstract class PowerUp extends Entity {
    int activatedDuration,decayDuration,radius,currentDuration,currentDecay;
    Board gameBoard;
    Color powerColor;

    public PowerUp(Board gameBoard, Point location, Color powerColor){
        super(location);
        this.powerColor = powerColor;
        this.gameBoard = gameBoard;
        this.activatedDuration = 800; //the default duration (some random number just to test it)
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
//        agePowerUp();
    }

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

    public Boolean decay(){
        currentDecay++;
        if (decayDuration<=currentDecay){
//            System.out.println("Decayed");
            return true;
        }
        else {
            return false;
        }
    }

    public void onCollect(){
        System.out.println("OnCollect");
        gameBoard.getActivatedPowerUps().add(this);
        activate();
        agePowerUp();
    }

    protected abstract void activate();

    public abstract void deactivate();

    public Boolean checkIfCollected(Ball ball){
        float deltaX = ball.getLocation().getX() - location.getX();
        float deltaY = ball.getLocation().getY() - location.getY();

        double distanceSqr = Math.pow(deltaX,2)+Math.pow(deltaY,2);
        double radSumSqr = Math.pow((ball.getRadius()+radius),2);
        if (distanceSqr>radSumSqr){
            return false;
        }
        else {
            System.out.println("PICKED UP!");
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
