package pongchamp.model.math;

import pongchamp.model.Collidable;

public class LineSegment {
    private final Point startPoint;
    private final Point endPoint;

    public LineSegment(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    
}
