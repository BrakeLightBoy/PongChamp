package pongchamp.pongchamp.model.entities.powerups;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.math.Point;

public class InvisPower extends PowerUp {
    public InvisPower(Board gameBoard, Point location){
        super(gameBoard, location);
    }
    public InvisPower(Board gameBoard, Point location, int duration, int radius) {
        super(gameBoard, location, duration, radius);
    }

//    public void onCollect(){
//        System.out.println("Picked up!");
//    }

    public void activate(){

    }

    public void deactivate(){

    }
}
