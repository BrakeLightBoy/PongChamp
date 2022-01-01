package pongchamp.model;

public class Collision {
    //Working with Collision class for now, as I thought it might make more sense to pass a specific "info packet" instead of a whole collidable object.
//    private final int collisionAngle;

    private CollisionTypes collisionType;
    private ObstactleTypes obstacleType;

    public Collision(CollisionTypes collisionType,ObstactleTypes obstacleType) {
        this.collisionType = collisionType;
        this.obstacleType = obstacleType;
    }




    //should have metadata included later on.

    public String toString(){
        return "Data: "+collisionType+"-"+obstacleType;
    }

    public CollisionTypes getColType() {
        return collisionType;
    }
    public ObstactleTypes getObstType() {
        return obstacleType;
    }
}
