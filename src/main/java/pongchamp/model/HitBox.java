package pongchamp.model;

import pongchamp.model.entities.Ball;
import pongchamp.model.math.Vector;


public class HitBox {
    private float minX,minY,maxX,maxY;

    public HitBox(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public Boolean checkBallIntersect(Ball ball){
        int bRadius = ball.getRadius();
        float bXpos = ball.getLocation().getX();
        float bYpos = ball.getLocation().getY();

        Boolean inRangeX = (bXpos > (minX-bRadius)) && (bXpos <= (maxX+bRadius));
        Boolean inRangeY = (bYpos > (minY-bRadius)) && (bYpos <= (maxY+bRadius));
        return inRangeX && inRangeY;
    }

    public void moveBox(Vector vector){
        minX += vector.getX();
        maxX += vector.getX();
        minY += vector.getY();
        maxY += vector.getY();
    }

}
