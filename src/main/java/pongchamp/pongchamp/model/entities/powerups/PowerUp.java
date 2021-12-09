package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.entities.Entity;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.Collectible;

public abstract class PowerUp extends Entity implements Collectible {
    int duration,radius;


    public PowerUp(Point location){
        super(location);
        this.duration = 10000; //the default duration (some random number just to test it)
        this.radius = 10; //again just random for test
    }

    public PowerUp(Point location,int duration, int radius) {
        super(location);
        this.duration = duration;
        this.radius = radius;
    }

    public void tick(){

    }



    public Boolean checkIfCollected(Ball ball){

        return true;
    }

    public int getDuration() {
        return duration;
    }

    public int getRadius() {
        return radius;
    }
}
