package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

public class NormalBall extends Ball{


    public NormalBall(Board board,Point location, int radius, Vector speed, Vector acceleration) {
        super(board,location, radius, speed, acceleration);
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

        //Shows where a registered collision happened. Keep in mind that there are 3 hitboxes per paddle.
        System.out.println(location + "\n" + collisionData + "\n");

        //temporarily switched lower and upper because of the render engine having the inverted y-axis
        if (collisionData.contains("shortSegment")){
            onShortPaddleSegmentCollision(collisionData);
            return;
        }

        if (collisionData.contains("corner")){
            onCornerCollision(collisionData);
            return;
        }

        onPaddleVerticalOrWallCollision(collisionData);
    }

    public void onCornerCollision(String collisionData){
        if (collisionData.contains("higher")){
            speed.setY(-Math.abs(speed.getY()));
        }
        else {
            speed.setY(Math.abs(speed.getY()));
        }
    }

    public void onShortPaddleSegmentCollision(String collisionData){
        if (collisionData.contains("higher")){
            speed.setY(-Math.abs(speed.getY()));
        }
        else {
            speed.setY(Math.abs(speed.getY()));
        }
        return;
    }

    public void onPaddleVerticalOrWallCollision(String collisionData){
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
