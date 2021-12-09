package pongchamp.pongchamp.controller;

public final class EmptyPaddleController implements PaddleController { //this is just a test class that is used for paddles that don't move


    public boolean movingUp() {
        return false;
    }


    public boolean movingDown() {
        return false;
    }
}
