package pongchamp.pongchamp.model.math;

public class Vector {
    private float x;
    private float y;

    /*
    Representation of a mathematical 2D vector, in x and y coordinate system.
    */
    public Vector(float x, float y) {
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
    Adds a vector to a given vector, changing the x and y of the original vector
     */
    public void addVector(Vector v){
        this.x += v.x;
        this.y += v.y;
    }


    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Float.compare(vector.x, x) == 0 && Float.compare(vector.y, y) == 0;
    }
}
