package pongchamp.model.entities;

import pongchamp.model.*;
import pongchamp.model.math.Point;
import pongchamp.model.math.Vector;

public class NormalBall extends Ball{


    public NormalBall(Point location, int radius, Vector speed, Vector acceleration) {
        super(location, radius, speed, acceleration);
    }

    @Override
    public void move() {
        location.movePoint(speed);
        speed.addVector(acceleration);

//        float friction = Properties.FRICTION;
//        acceleration.setX(acceleration.getX()*friction);
//        acceleration.setY(acceleration.getY()*friction);
    }

    @Override
    public void onCollision(Collision collision) {
        if (collision == null){
            return;
        }

        String collisionData = collision.getData();
        if (collisionData.contains("left")){
            speed.setX(Math.abs(speed.getX()));
        }
        else if (collisionData.contains("right")){
            speed.setX(-Math.abs(speed.getX()));
        }
        else if (collisionData.contains("upper")){
            speed.setY(-Math.abs(speed.getY()));
        }
        else if (collisionData.contains("lower")){
            speed.setY(Math.abs(speed.getY()));
        }

    }

}
