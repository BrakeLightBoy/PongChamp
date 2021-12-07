package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.math.Point;

public class StrengthPower extends PowerUp{
    public StrengthPower(Point location){
        super(location);
    }

    public void onCollect(){
        System.out.println("Picked up!");
    }
}
