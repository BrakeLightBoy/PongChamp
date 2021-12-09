package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.entities.Entity;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.Collectible;

public abstract class PowerUp extends Entity implements Collectible {
    int duration,radius;


    public PowerUp(Board gameBoard,Point location){
        super(location);
        this.duration = 10000; //the default duration (some random number just to test it)
        this.radius = 10; //again just random for test
    }

    public PowerUp(Board gameBoard, Point location,int duration, int radius) {
        super(location);
        this.duration = duration;
        this.radius = radius;
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
        gameBoard.getActivatedPowerUps().add(this);
        activate();
        agePowerUp();
    }

    protected abstract void activate();

    protected abstract void deactivate();

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

    public int getDuration() {
        return duration;
    }

    public int getRadius() {
        return radius;
    }
}
