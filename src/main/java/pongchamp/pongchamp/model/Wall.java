package pongchamp.pongchamp.model;


import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.LineSegment;



public class Wall implements Collidable {
    private final WallType wallType;
    private final LineSegment wallLine;

    enum WallType {
        LEFT,
        RIGHT,
        UPPER,
        LOWER
    }

    public Wall(WallType wallType, LineSegment wallLine) {

        if (! (wallType.equals(WallType.RIGHT) || wallType.equals(WallType.LEFT) || wallType.equals(WallType.UPPER) || wallType.equals(WallType.LOWER)) ){
                throw new IllegalArgumentException("invalid wall type");
        }
        this.wallType = wallType;
        this.wallLine = wallLine;

    }

    public Collision checkBallCollision(Ball ball) {
        switch (wallType){
            case RIGHT:
                if (ball.getLocation().getX()+ball.getRadius()>=wallLine.getStartPoint().getX()){
                    return new Collision("wall-right");
                }
                break;
            case LEFT:
                if (ball.getLocation().getX()- ball.getRadius()<=wallLine.getStartPoint().getX()){
                    return new Collision("wall-left");
                }
                break;
            case UPPER:
                if (ball.getLocation().getY()+ball.getRadius()>=wallLine.getStartPoint().getY()){
                    return new Collision("wall-upper");
                }
                break;
            case LOWER:
                if (ball.getLocation().getY()- ball.getRadius()<=wallLine.getStartPoint().getY()){
                    return new Collision("wall-lower");
                }
                break;
        }
        return null;
    }


    public WallType getWallType() {
        return wallType;
    }

    public LineSegment getWallLine() {
        return wallLine;
    }
}
