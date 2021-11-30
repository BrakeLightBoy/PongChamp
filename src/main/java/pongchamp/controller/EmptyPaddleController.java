package pongchamp.controller;

public final class EmptyPaddleController implements PaddleController {

    @Override
    public boolean movingUp() {
        return false;
    }

    @Override
    public boolean movingDown() {
        return false;
    }
}
