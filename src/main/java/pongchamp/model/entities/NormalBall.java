package pongchamp.model.entities;

import pongchamp.model.*;
import pongchamp.model.math.Location;
import pongchamp.model.math.LineSegment;
import pongchamp.model.math.Vector;

public class NormalBall extends Ball{


    public NormalBall(Location location, int radius, Vector speed, Vector acceleration, LineSegment ballPath, Board board) {
        super(location, radius, speed, acceleration, ballPath,board);
    }

    @Override
    public void move() {

        location.add(speed);
        speed.apply(acceleration);
        float friction = Properties.FRICTION;
        acceleration.setX(acceleration.getX()*friction);
        acceleration.setY(acceleration.getY()*friction);

    }

    @Override
    public void onCollusion(Collidable collidable) {

    }


}
