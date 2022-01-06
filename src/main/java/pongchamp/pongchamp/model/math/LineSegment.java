package pongchamp.pongchamp.model.math;

public class LineSegment {
    private final Point startPoint;
    private final Point endPoint;

    /*
    This class is a representation of a line segment with coordinates representing a start- and an endpoint.
    */

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