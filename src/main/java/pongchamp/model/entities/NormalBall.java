package pongchamp.model.entities;

import pongchamp.model.*;
import pongchamp.model.math.Point;
import pongchamp.model.math.Vector;

public class NormalBall extends Ball{


    public NormalBall(Board board,Point location, int radius, Vector speed, Vector acceleration) {
        super(board,location, radius, speed, acceleration);
    }

    @Override
    public void move() {
        location.movePoint(speed);
        speed.addVector(acceleration);

        //use this to slow down the ball when testing corner shots
//        speed.multiplyY(.99f);

//        float friction = Properties.FRICTION;
//        acceleration.setX(acceleration.getX()*friction);
//        acceleration.setY(acceleration.getY()*friction);
    }

    @Override
    public void onCollision(Collision collision) {
        if (collision == null){
            return;
        }

        //Shows where a registered collision happened. Keep in mind that there are 3 hitboxes per paddle.
        System.out.print("X:"+location.getX());
        System.out.println("Y:"+location.getY());
        System.out.println();

        String collisionData = collision.getData();

        //temporarily switched lower and upper because of the render engine having the inverted y-axis

        System.out.println(collisionData);
        if (collisionData.contains("shortSegment")){
            if (collisionData.contains("higher")){
                speed.setY(-Math.abs(speed.getY()));
            }
            else {
                speed.setY(Math.abs(speed.getY()));
            }
            return;
        }

        if (collisionData.contains("corner")){
            if (collisionData.contains("higher")){
                speed.setY(-Math.abs(speed.getY()));
            }
            else {
                speed.setY(Math.abs(speed.getY()));
            }
        }

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
