package pongchamp.model.math;

public class Vector {
    private float x;
    private float y;

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

    public void apply(Vector v){
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
}
