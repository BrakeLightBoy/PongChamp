package pongchamp.pongchamp.model;

import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;


public class HitBox {
    private float minX,minY,maxX,maxY,width,height;
    private Point center;

    public HitBox(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        center = new Point( (minX+maxX)/2, (minY+maxY)/2);
        width = maxX-minX;
        height = maxY-minY;
    }

    public String checkBallIntersect(Ball ball){
        //Code inspired by "eJames" the explanation from:
        //https://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
        int bRadius = ball.getRadius();
        float bXpos = ball.getLocation().getX();
        float bYpos = ball.getLocation().getY();

        float distX = Math.abs(bXpos-center.getX());
        float distY = Math.abs(bYpos-center.getY());


        if (distX>(width/2f)+bRadius || distY>(height/2f)+bRadius){
            return null;
        }

        if (distY<=height/2f)
        {
            return "vertical";
        }
        else if (distX<=width/2f){
            return "horizontal";
        }

        double cornerDistSquared = Math.pow((distX-width/2d),2) + Math.pow((distY-height/2d),2);

        if (cornerDistSquared <= Math.pow(bRadius,2)){
            return "corner";
        }
        else {
            return null;
        }
    }

    public void moveHitBox(Vector vector){
        minX += vector.getX();
        maxX += vector.getX();
        minY += vector.getY();
        maxY += vector.getY();
        center.movePoint(vector);
    }

}
