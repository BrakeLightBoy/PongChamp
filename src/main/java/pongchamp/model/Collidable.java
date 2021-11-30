package pongchamp.model;
import pongchamp.model.entities.Ball;
public interface Collidable {
    Collision checkBallCollision(Ball ball);
}
