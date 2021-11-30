package pongchamp.model.entities;

import pongchamp.model.Collidable;
import pongchamp.model.Collision;
import pongchamp.model.HitBox;
import pongchamp.model.math.LineSegment;



public class Wall implements Collidable {
    private final String wallType;
    private final LineSegment wallLine;
    private HitBox wallHitBox;

    public Wall(String wallType, LineSegment wallLine) {

        if (! (wallType.equals("right") || wallType.equals("left") || wallType.equals("upper") || wallType.equals("lower")) ){
                throw new IllegalArgumentException("invalid wall type");
        }
        this.wallType = wallType;
        this.wallLine = wallLine;

        switch (wallType) {
            case "right" -> wallHitBox = new HitBox(wallLine.getStartPoint().getX(), wallLine.getStartPoint().getY(), Integer.MAX_VALUE, wallLine.getEndPoint().getY());
            case "left" -> wallHitBox = new HitBox(Integer.MIN_VALUE, wallLine.getStartPoint().getY(), wallLine.getEndPoint().getX(), wallLine.getEndPoint().getY());
            case "upper" -> wallHitBox = new HitBox(wallLine.getStartPoint().getX(), wallLine.getStartPoint().getY(), wallLine.getEndPoint().getX(), Integer.MAX_VALUE);
            case "lower" -> wallHitBox = new HitBox(wallLine.getStartPoint().getX(), Integer.MIN_VALUE, wallLine.getEndPoint().getX(), wallLine.getEndPoint().getY());
        }

    }

    public Collision checkBallCollision(Ball ball) {
        if (wallHitBox.checkBallIntersect(ball)){
            if (wallType.equals("left")){
                return new Collision("wall-left");
            }
            else if (wallType.equals("right")){
                return new Collision("paddle-right");
            }
            else if (wallType.equals("upper")){
                return new Collision("paddle-upper");
            }
            else {
                return new Collision("paddle-lower");
            }
        }
        return null;
    }


    public String getWallType() {
        return wallType;
    }

    public LineSegment getWallLine() {
        return wallLine;
    }
}
