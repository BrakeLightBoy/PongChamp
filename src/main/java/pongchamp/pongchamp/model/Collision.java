package pongchamp.pongchamp.model;

public class Collision {
    //Working with Collision class for now, as I thought it might make more sense to pass a specific "info packet" instead of a whole collidable object.
//    private final int collisionAngle;
    private final String data;

    //should have metadata included later on.

    public String toString(){
        return "Data: "+data;
    }

    public Collision(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
