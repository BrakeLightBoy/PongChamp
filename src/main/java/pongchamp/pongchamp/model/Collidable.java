package pongchamp.pongchamp.model;
import pongchamp.pongchamp.model.entities.Ball;
public interface Collidable {
    Collision checkBallCollision(Ball ball);
}
