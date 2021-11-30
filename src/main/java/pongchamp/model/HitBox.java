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

        float distX1 = Math.abs(bXpos-minX);
        float distX2 = Math.abs(bXpos-maxX);
        float circleXDistance = Math.min(distX1,distX2);

        float distY1 = Math.abs(bYpos-minY);
        float distY2 = Math.abs(bYpos-maxY);
        float circleYDistance = Math.min(distY1,distY2);

        return ((circleXDistance<=bRadius) && (circleYDistance <= bRadius));
    }

    public void moveBox(Vector vector){
        minX += vector.getX();
        maxX += vector.getX();
        minY += vector.getY();
        maxY += vector.getY();
    }

}
