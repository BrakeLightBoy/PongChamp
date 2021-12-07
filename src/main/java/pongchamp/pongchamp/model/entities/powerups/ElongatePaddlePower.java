package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.math.Point;

public class ElongatePaddlePower extends PowerUp{
    public ElongatePaddlePower(Point location) {
        super(location);
    }

    public void onCollect(){
        System.out.println("Picked up!");
    }
}
