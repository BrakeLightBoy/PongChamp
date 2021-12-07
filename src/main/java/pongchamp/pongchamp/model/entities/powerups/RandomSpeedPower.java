package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.math.Point;

public class RandomSpeedPower extends PowerUp {
    public RandomSpeedPower(Point location){
        super(location);
    }

    public void onCollect(){
        System.out.println("Picked up!");
    }
}
