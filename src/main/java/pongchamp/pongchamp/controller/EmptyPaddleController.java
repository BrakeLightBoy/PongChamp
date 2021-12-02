package pongchamp.pongchamp.controller;

public final class EmptyPaddleController implements PaddleController { //this is just a test class that is used for paddles that don't move

    @Override
    public boolean movingUp() {
        return false;
    }

    @Override
    public boolean movingDown() {
        return false;
    }
}
