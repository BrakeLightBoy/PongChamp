package pongchamp.model;


import pongchamp.model.math.LineSegment;

public class Wall implements Collidable {
    private final CollisionTypes wallType;
    private final LineSegment wallLine;



    public Wall(CollisionTypes wallType, LineSegment wallLine) {

        if (! (wallType == CollisionTypes.RIGHT || wallType == CollisionTypes.LEFT || wallType == CollisionTypes.UPPER || wallType == CollisionTypes.LOWER) ){
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
