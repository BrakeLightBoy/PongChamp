package pongchamp.pongchamp.model;


import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;
import static pongchamp.pongchamp.model.ObstactleTypes.*;
import static pongchamp.pongchamp.model.CollisionTypes.*;

public class Wall implements Collidable {
    private final CollisionTypes wallType;
    private final LineSegment wallLine;



    public Wall(CollisionTypes wallType, LineSegment wallLine) {

        if (! (wallType == RIGHT || wallType == LEFT || wallType == UPPER || wallType == LOWER) ){
                throw new IllegalArgumentException("invalid wall type");
        }
        this.wallType = wallType;
        this.wallLine = wallLine;
    }

    public CollisionTypes getWallType() {
        return wallType;
    }

    public LineSegment getWallLine() {
        return wallLine;
    }
}
