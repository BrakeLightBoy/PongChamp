package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.math.Point;

public class RandomSpeedPower extends PowerUp {
    public RandomSpeedPower(Point location){
        super(location);
    }
    public RandomSpeedPower(Point location,int duration, int radius){
        super(location,duration,radius);
    }

    public void onCollect(){
        System.out.println("Picked up!");
    }
}
