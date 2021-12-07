package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.Collectible;

public abstract class PowerUp implements Collectible {
    int duration,radius;
    Point location;

    public PowerUp(Point location) {

    }



    public Boolean checkIfCollected(Ball ball){

        return true;
    }
}
