package pongchamp.pongchamp.model;
import pongchamp.pongchamp.model.entities.Ball;

public interface Collectible {
    void onCollect();
    Boolean checkIfCollected(Ball ball);
}