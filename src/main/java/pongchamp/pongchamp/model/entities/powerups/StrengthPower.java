package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.math.Point;

public class StrengthPower extends PowerUp{
    public StrengthPower(Point location){
        super(location);
    }
    public StrengthPower(Point location,int duration, int radius) {
        super(location, duration, radius);
    }
    public void onCollect(){
        System.out.println("Picked up!");
    }
}
