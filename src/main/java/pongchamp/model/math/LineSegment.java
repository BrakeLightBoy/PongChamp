package pongchamp.model.math;

import pongchamp.model.Collidable;

public class LineSegment implements Collidable {
    private final Location startPoint;
    private final Location endPoint;

    public LineSegment(Location startPoint, Location endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public Location getEndPoint() {
        return endPoint;
    }

    
}
