package pongchamp.pongchamp.controller;

import pongchamp.pongchamp.model.entities.Ball;
import pongchamp.pongchamp.model.entities.Paddle;

public class AIPaddleController implements PaddleController {

    private Ball target;
    private Paddle paddle;



    @Override
    public boolean movingUp() {
        return false;
    }

    @Override
    public boolean movingDown() {
        return false;
    }
}
