package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.math.Point;

public class InvisPower extends PowerUp {
    public InvisPower(Point location) {
        super(location);
    }

    public void onCollect(){
        System.out.println("Picked up!");
    }
}
