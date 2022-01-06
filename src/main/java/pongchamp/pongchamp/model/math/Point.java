package pongchamp.pongchamp.model.math;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    /*
    Determines new point based off of a given vector
     */
    public void movePoint(Vector v){
        this.x += v.getX();
        this.y += v.getY();
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        else if (o instanceof Point){
            Point comparedPoint = (Point) o;

            return x == comparedPoint.getX() && y == comparedPoint.getY();
        }
        return false;
    }

    @Override
    public String toString() {
        return "X:" + x
                + "\n" + "Y:" + y;
    }
}
