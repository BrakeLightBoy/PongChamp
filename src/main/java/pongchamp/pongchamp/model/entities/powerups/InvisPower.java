package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.math.Point;

public class InvisPower extends PowerUp {
    public InvisPower(Point location){
        super(location);
    }
    public InvisPower(Point location,int duration, int radius) {
        super(location, duration, radius);
    }

    public void onCollect(){
        System.out.println("Picked up!");
    }
}
